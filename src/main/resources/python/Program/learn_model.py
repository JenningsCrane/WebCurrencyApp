#!/usr/bin/env python
# coding: utf-8

# In[127]:


import tensorflow as tf

import matplotlib as mpl
import matplotlib.pyplot as plt
import numpy as np
import os
import csv
import sys
import pandas as pd
from tensorflow.keras.layers import Input, LSTM, Dense
from tensorflow.keras.models import Model

mpl.rcParams['figure.figsize'] = (8, 6)
mpl.rcParams['axes.grid'] = False

# Получаем входные значения
arguments = sys.argv

#if arguments[1] == "cny":
dataset_path = "..\\dataset\\uan20years.csv"
    # граница тренировочных и валидационных данных
TRAIN_SPLIT = 4450
    # кол-во обучающих данных
EVALUATION_INTERVAL = 4000
model_save = "..\\model\cny_model.keras"
#elif arguments[1] == "aed":
#    dataset_path = "..\\dataset\\aed20years.csv"
    # граница тренировочных и валидационных данных
#    TRAIN_SPLIT = 400
    # кол-во обучающих данных
#    EVALUATION_INTERVAL = 390
#    model_save = "..\\model\aed_model.keras"
#else:
#    print("Нет входных аргументов")
#    quit()


# кол-во эпох обучения
EPOCHS = 100
# настройки для обучения
BATCH_SIZE = 256
BUFFER_SIZE = 10000

        
# Загрузка данных из CSV файла
df = pd.read_csv(dataset_path, on_bad_lines='skip', sep=';')

# Преобразование значений столбца 'curs' из строки в число с корректным разделителем
df['curs'] = df['curs'].str.replace(',', '.').astype(float)
# Применение логики для разделения значений столбцов 'nominal' и 'curs'
df['curs'] = df.apply(lambda row: row['curs'] / 10.0 if row['nominal'] == 10 else row['curs'], axis=1)

df['nominal'] = df['nominal'].apply(lambda x: x / 10 if x == 10 else x)

# Переворачивание порядка столбцов, кроме первого
df.iloc[:, 1:] = df.iloc[:, 1:][::-1].values
df.head()


# In[128]:


# Функция подготовки данных для входа в рекурентную сеть
def univariate_data(dataset, start_index, end_index, history_size,
                      target_size, step, single_step=False):
  data = []
  labels = []

  start_index = start_index + history_size
  if end_index is None:
    end_index = len(dataset) - target_size

  for i in range(start_index, end_index):
    indices = range(i-history_size, i, step)
    data.append(np.reshape(dataset[indices], (history_size, 1)))

    labels.append(dataset[i:i+target_size])

  return np.array(data), np.array(labels)


# In[129]:


print(df.columns)


# In[130]:


# извлекаем курс и индексируем по дате
uni_data = df['curs']
uni_data.index = df['data']
uni_data.head()


# In[131]:


# график курса за всё время измерений
uni_data.plot(subplots=True)
uni_data = uni_data.values


# In[132]:


# номализация данных
uni_train_mean = uni_data[:TRAIN_SPLIT].mean()
uni_train_std = uni_data[:TRAIN_SPLIT].std()
uni_data = (uni_data-uni_train_mean)/uni_train_std


# In[133]:


past_history = 30
future_target = 7
STEP = 1

x_train_uni, y_train_uni = univariate_data(uni_data, 0,
                                                 TRAIN_SPLIT, past_history,
                                                 future_target, STEP)
x_val_uni, y_val_uni = univariate_data(uni_data,
                                             TRAIN_SPLIT, None, past_history,
                                             future_target, STEP)


# In[134]:


print ('Single window of past history : {}'.format(x_train_uni[0].shape))
print ('\n Target curs to predict : {}'.format(y_train_uni[0].shape))


# In[135]:


# подготовка данных для модели
train_data_uni = tf.data.Dataset.from_tensor_slices((x_train_uni, y_train_uni))
train_data_uni = train_data_uni.cache().shuffle(BUFFER_SIZE).batch(BATCH_SIZE).repeat()

val_data_uni = tf.data.Dataset.from_tensor_slices((x_val_uni, y_val_uni))
val_data_uni = val_data_uni.batch(BATCH_SIZE).repeat()


# In[138]:


# сборка слоёв модели
uni_step_model = tf.keras.models.Sequential()
uni_step_model.add(tf.keras.layers.LSTM(32,
                                          return_sequences=True,
                                          input_shape=x_train_uni.shape[-2:]))
uni_step_model.add(tf.keras.layers.LSTM(16, activation='relu'))
uni_step_model.add(tf.keras.layers.Dense(7))

uni_step_model.compile(optimizer=tf.keras.optimizers.RMSprop(clipvalue=1.0), loss='mae')


# In[139]:


# Проверка формы данных после преобразования
print(x_train_uni.shape)
for x, y in val_data_uni.take(1):
  print (uni_step_model.predict(x).shape)


# In[140]:


# обучение модели
uni_step_history = uni_step_model.fit(train_data_uni, epochs=EPOCHS,
                                          steps_per_epoch=EVALUATION_INTERVAL,
                                          validation_data=val_data_uni,
                                          validation_steps=50)


# In[141]:


for x, y in val_data_uni.take(1):
  print((uni_step_model.predict(x)[0]* uni_train_std) + uni_train_mean)


# In[142]:


def plot_train_history(history, title):
  loss = history.history['loss']
  val_loss = history.history['val_loss']

  epochs = range(len(loss))

  plt.figure()

  plt.plot(epochs, loss, 'b', label='Training loss')
  plt.plot(epochs, val_loss, 'r', label='Validation loss')
  plt.title(title)
  plt.legend()

  plt.show()

plot_train_history(uni_step_history, 'Training and validation loss')


# In[143]:


# функция для отрисовки графика
def create_time_steps(length):
  return list(range(-length, 0))

def uni_step_plot(history, true_future, prediction):
  plt.figure(figsize=(12, 6))
  num_in = create_time_steps(len(history))
  num_out = len(true_future)

  plt.plot(num_in, np.array(history), label='History')
  plt.plot(np.arange(num_out)/STEP, np.array(true_future), 'bo',
           label='True Future')
  if prediction.any():
    plt.plot(np.arange(num_out)/STEP, np.array(prediction), 'ro',
             label='Predicted Future')
  plt.legend(loc='upper left')
  plt.show()


# In[144]:


for x, y in val_data_uni.take(1):
  uni_step_plot(x[0], y[0], uni_step_model.predict(x)[0])


# In[145]:


# Сохранение модели
uni_step_model.save(model_save)


# In[ ]:




