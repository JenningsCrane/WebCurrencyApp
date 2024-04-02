#!/usr/bin/env python
# coding: utf-8

# In[84]:


import tensorflow as tf

import matplotlib as mpl
import matplotlib.pyplot as plt
import numpy as np
import os
import csv
import json
import sys
import pandas as pd
from tensorflow.keras.layers import Input, LSTM, Dense
from tensorflow.keras.models import Model

mpl.rcParams['figure.figsize'] = (8, 6)
mpl.rcParams['axes.grid'] = False

# граница валидационных данных
VAL_SPLIT = 0

# Получаем входные значения
arguments = sys.argv

#if arguments[1] == "cny":
    # Путь к директории с моделью
    directory = "..\\model\cny_model.keras"
    dir_new_dataset = '..\\new_dataset\\cny.csv'
    # Путь к директории для сохранения файла json
    json_dir = "..\\json"
    # Имя файла json
    filename = "cny_data.json"
#elif arguments[1] == "aed":
    # Путь к директории с моделью
    directory = "..\\model\aed_model.keras"
    dir_new_dataset = '..\\new_dataset\\aed.csv'
    # Путь к директории для сохранения файла json
    json_dir = "..\\json"
    # Имя файла json
    filename = "aed_data.json"
#else:
#    print("Нет входных аргументов")
#    quit()

# настройки для обучения
BATCH_SIZE = 256
BUFFER_SIZE = 10000

def check_csv_file_size(file_path):
    global VAL_SPLIT
    if os.path.exists(file_path):
        df = pd.read_csv(file_path)
        num_records = len(df)
        if (num_records >= 60):
            VAL_SPLIT = int(num_records * 0.1)
            return num_records
    else:
        return False
        
# Загрузка данных из CSV файла
df = pd.read_csv(dir_new_dataset, on_bad_lines='skip', sep=';')

# Проверка размера файла CSV
if (not check_csv_file_size(dir_new_dataset)):
    print("Размер файла CSV меньше 60 записей. Дополните выборку.")
    quit()

print(VAL_SPLIT)
print(EVALUATION_INTERVAL)
# Преобразование значений столбца 'curs' из строки в число с корректным разделителем
df['curs'] = df['curs'].str.replace(',', '.').astype(float)
# Применение логики для разделения значений столбцов 'nominal' и 'curs'
df['curs'] = df.apply(lambda row: row['curs'] / 10.0 if row['nominal'] == 10 else row['curs'], axis=1)

df['nominal'] = df['nominal'].apply(lambda x: x / 10 if x == 10 else x)

# Переворачивание порядка столбцов, кроме первого
df.iloc[:, 1:] = df.iloc[:, 1:][::-1].values
df.head()


# In[85]:


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


# In[86]:


# извлекаем курс и индексируем по дате
uni_data = df['curs']
uni_data.index = df['data']
uni_data.head()
uni_data = uni_data.values


# In[87]:


# номализация данных
uni_train_mean = uni_data[:VAL_SPLIT].mean()
uni_train_std = uni_data[:VAL_SPLIT].std()
uni_data = (uni_data-uni_train_mean)/uni_train_std


# In[88]:


past_history = 30
future_target = 7
STEP = 1

x_val_uni, y_val_uni = univariate_data(uni_data, VAL_SPLIT, None, past_history, future_target, STEP)


# In[89]:


# подготовка данных для модели
val_data_uni = tf.data.Dataset.from_tensor_slices((x_val_uni, y_val_uni))
val_data_uni = val_data_uni.batch(BATCH_SIZE).repeat()


# In[90]:


def load_model_from_keras_format(directory):
    model = tf.keras.models.load_model(directory, custom_objects={'mae': tf.keras.losses.mean_absolute_error})
    return model

model = load_model_from_keras_format(directory)

model.compile(optimizer=tf.keras.optimizers.RMSprop(clipvalue=1.0), loss='mae')
for x, y in val_data_uni.take(1):
  print((model.predict(x)[0]* uni_train_std) + uni_train_mean)


# In[91]:


predictions = []  # Создание пустого списка для предсказаний

# Получение предсказаний и добавление их в список
for x, y in val_data_uni.take(1):
    prediction = (model.predict(x)[0] * uni_train_std) + uni_train_mean
    predictions.append(prediction.tolist())  # Преобразование массива numpy в список и добавление в список предсказаний

json_data = json.dumps(predictions)  # Сериализация списка в JSON

# Сохранение JSON-данных в файл
with open(json_dir + "/" + filename, "w") as file:
    file.write(json_data)

print("JSON-файл сохранен по пути:", json_dir + "/" + filename)


# In[ ]:




