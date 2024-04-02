#!/usr/bin/env python
# coding: utf-8

# In[16]:


import tensorflow as tf

import matplotlib as mpl
import matplotlib.pyplot as plt
import numpy as np
import os
import csv
import pandas as pd
from tensorflow.keras.layers import Input, LSTM, Dense
from tensorflow.keras.models import Model

# граница тренировочных и валидационных данных
TRAIN_SPLIT = 0
# кол-во обучающих данных
EVALUATION_INTERVAL = 0
# кол-во эпох обучения
EPOCHS = 10
# настройки для обучения
BATCH_SIZE = 256
BUFFER_SIZE = 10000

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

def load_model_from_keras_format(directory):
    model = tf.keras.models.load_model(directory)
    return model

def fine_tune_model(model, train_data, val_data):
    model.compile(optimizer=tf.keras.optimizers.RMSprop(clipvalue=1.0), loss='mae')
    model.fit(train_data, epochs=EPOCHS,
                                          steps_per_epoch=EVALUATION_INTERVAL,
                                          validation_data=val_data,
                                          validation_steps=50)
    return model

def check_csv_file_size(file_path):
    if os.path.exists(file_path):
        df = pd.read_csv(file_path)
        num_records = len(df)
        if (num_records >= 50):
            TRAIN_SPLIT = int(num_records * 0.9)
            EVALUATION_INTERVAL = num_records - 40
            return num_records
    else:
        return False

# Путь к директории с моделью
directory = "..\\model\\model.keras"

# Путь к файлу CSV
csv_file_path = "..\\new_dataset\\uans.csv"


# In[17]:


# Проверка размера файла CSV
if check_csv_file_size(csv_file_path):
    # Загрузка модели из .keras файла
    if os.path.exists(directory):
        # Загрузка данных из CSV файла
        df = pd.read_csv('..\\dataset\\uan20years.csv', on_bad_lines='skip', sep=';')

        # Преобразование значений столбца 'curs' из строки в число с корректным разделителем
        df['curs'] = df['curs'].str.replace(',', '.').astype(float)
        # Применение логики для разделения значений столбцов 'nominal' и 'curs'
        df['curs'] = df.apply(lambda row: row['curs'] / 10.0 if row['nominal'] == 10 else row['curs'], axis=1)

        df['nominal'] = df['nominal'].apply(lambda x: x / 10 if x == 10 else x)

        # Переворачивание порядка столбцов, кроме первого
        df.iloc[:, 1:] = df.iloc[:, 1:][::-1].values
        
        # извлекаем курс и индексируем по дате
        uni_data = df['curs']
        uni_data.index = df['data']
        uni_data = uni_data.values
        
        # нормализация данных
        uni_train_mean = uni_data[:TRAIN_SPLIT].mean()
        uni_train_std = uni_data[:TRAIN_SPLIT].std()
        uni_data = (uni_data-uni_train_mean)/uni_train_std
        
        past_history = 30
        future_target = 7
        STEP = 1

        x_train_uni, y_train_uni = univariate_data(uni_data, 0,
                                                 TRAIN_SPLIT, past_history,
                                                 future_target, STEP)
        x_val_uni, y_val_uni = univariate_data(uni_data,
                                             TRAIN_SPLIT, None, past_history,
                                             future_target, STEP)
        
        # подготовка данных для модели
        train_data = tf.data.Dataset.from_tensor_slices((x_train_uni, y_train_uni))
        train_data = train_data.cache().shuffle(BUFFER_SIZE).batch(BATCH_SIZE).repeat()

        val_data = tf.data.Dataset.from_tensor_slices((x_val_uni, y_val_uni))
        val_data = val_data.batch(BATCH_SIZE).repeat()
        
        
        model = load_model_from_keras_format(directory)
        model = fine_tune_model(model, train_data, val_data)
    else:
        print("Файл .keras не найден в указанной директории.")
else:
    print("Размер файла CSV меньше 50 записей. Переобучение не требуется.")


# In[ ]:




