#!/usr/bin/env python
# coding: utf-8

# In[16]:


import requests
import sys
from bs4 import BeautifulSoup

# Получаем входные значения
arguments = sys.argv

#if arguments[1] == "cny":
valuta = "Китайские Юани"
#elif arguments[1] == "aed":
#valuta = "Валюта Арабских эмиратов дирхамы"
#else:
#    print("Нет входных аргументов")
#    quit()

# URL веб-сайтов, с которых вы хотите спарсить новости
url_lenta_1 = 'https://lenta.ru/rubrics/economics/markets'
url_lenta_2 = 'https://lenta.ru/rubrics/economics/finance'

# Пустая строка для сохранения заголовков новостей
all_news = ""

# Отправка GET запросов на веб-сайты
response1 = requests.get(url_lenta_1)
response2 = requests.get(url_lenta_2)

# Проверка успешности запросов
if response1.status_code == 200:
    # Использование Beautiful Soup для парсинга HTML
    soup1 = BeautifulSoup(response1.text, 'html.parser')
    
    # Нахождение всех заголовков новостей на первом сайте
    news_headlines1 = soup1.find_all('h3', class_='card-full-news__title')  # Пример класса или тега, содержащего заголовки новостей

    # Добавление заголовков новостей с первого сайта в общую строку
    all_news += f"Новости с {url_lenta_1}:\n"
    for headline in news_headlines1:
        all_news += headline.text + "\n"
else:
    print('Ошибка при запросе веб-сайта', url_lenta_1)
    
if response2.status_code == 200:
    # Использование Beautiful Soup для парсинга HTML
    soup2 = BeautifulSoup(response2.text, 'html.parser')
    
    # Нахождение всех заголовков новостей на втором сайте
    news_headlines2 = soup2.find_all('h3', class_='card-full-news__title')  # Пример класса или тега, содержащего заголовки новостей

    # Добавление заголовков новостей со второго сайта в общую строку
    all_news += f"\nНовости с {url_lenta_2}:\n"
    for headline in news_headlines2:
        all_news += headline.text + "\n"
else:
    print('Ошибка при запросе веб-сайта', url_lenta_2)

# Печать всех заголовков новостей в общей строке
print(all_news)
    


# In[23]:


# Используем Гигачат
client_id = "6a45ddd9-e7b9-4ebe-b99b-717aaa35a449"
auth = "NmE0NWRkZDktZTdiOS00ZWJlLWI5OWItNzE3YWFhMzVhNDQ5OmRkOGJhNTBmLWNlNmItNDkwYy1iMDRkLWUxY2VmYTljZjBjYQ=="


# In[24]:


import requests
import uuid

def get_token(auth_token, scope='GIGACHAT_API_PERS'):
    """
      Выполняет POST-запрос к эндпоинту, который выдает токен.

      Параметры:
      - auth_token (str): токен авторизации, необходимый для запроса.
      - область (str): область действия запроса API. По умолчанию — «GIGACHAT_API_PERS».

      Возвращает:
      - ответ API, где токен и срок его "годности".
      """
    # Создадим идентификатор UUID (36 знаков)
    rq_uid = str(uuid.uuid4())

    # API URL
    url = "https://ngw.devices.sberbank.ru:9443/api/v2/oauth"

    # Заголовки
    headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Accept': 'application/json',
        'RqUID': rq_uid,
        'Authorization': f'Basic {auth_token}'
    }

    # Тело запроса
    payload = {
        'scope': scope
    }

    try:
        # Делаем POST запрос с отключенной SSL верификацией
        # (можно скачать сертификаты Минцифры, тогда отключать проверку не надо)
        response = requests.post(url, headers=headers, data=payload, verify=False)
        return response
    except requests.RequestException as e:
        print(f"Ошибка: {str(e)}")
        return -1


# In[25]:


response = get_token(auth)
if response != 1:
  print(response.text)
  giga_token = response.json()['access_token']


# In[26]:


import requests

url = "https://gigachat.devices.sberbank.ru/api/v1/models"

payload={}
headers = {
  'Accept': 'application/json',
  'Authorization': f'Bearer {giga_token}'
}

response = requests.request("GET", url, headers=headers, data=payload, verify=False)

print(response.text)


# In[35]:


import requests
import json

def get_chat_completion(auth_token, user_message):
    """
    Отправляет POST-запрос к API чата для получения ответа от модели GigaChat.

    Параметры:
    - auth_token (str): Токен для авторизации в API.
    - user_message (str): Сообщение от пользователя, для которого нужно получить ответ.

    Возвращает:
    - str: Ответ от API в виде текстовой строки.
    """
    # URL API, к которому мы обращаемся
    url = "https://gigachat.devices.sberbank.ru/api/v1/chat/completions"

    # Подготовка данных запроса в формате JSON
    payload = json.dumps({
        "model": "GigaChat",  # Используемая модель
        "messages": [
            {
                "role": "user",  # Роль отправителя (пользователь)
                "content": user_message  # Содержание сообщения
            }
        ],
        "temperature": 1,  # Температура генерации
        "top_p": 0.1,  # Параметр top_p для контроля разнообразия ответов
        "n": 1,  # Количество возвращаемых ответов
        "stream": False,  # Потоковая ли передача ответов
        "max_tokens": 512,  # Максимальное количество токенов в ответе
        "repetition_penalty": 1,  # Штраф за повторения
        "update_interval": 0  # Интервал обновления (для потоковой передачи)
    })

    # Заголовки запроса
    headers = {
        'Content-Type': 'application/json',  # Тип содержимого - JSON
        'Accept': 'application/json',  # Принимаем ответ в формате JSON
        'Authorization': f'Bearer {auth_token}'  # Токен авторизации
    }

    # Выполнение POST-запроса и возвращение ответа
    try:
        response = requests.request("POST", url, headers=headers, data=payload, verify=False)
        return response
    except requests.RequestException as e:
        # Обработка исключения в случае ошибки запроса
        print(f"Произошла ошибка: {str(e)}")
        return -1


     

answer = get_chat_completion(giga_token, "Пожалуйста, проанализируйте следующие новостные заголовки и дайте мне четкий ответ: можно ли в данный момент покупать" + valuta +"? Состоятельность данной инвестиции зависит от текущей ситуации, макроэкономических трендов и финансовых аспектов. Ответь чётко по делу. Вот заголовки новостей для вашего анализа:" + all_news)
     

answer.json()
     
# Открываем файл для записи ('w' означает режим записи)
with open('./gigachat.out/out.txt', 'w', encoding='utf-8') as file:
    file.write(text)

print("Текст успешно сохранен в файл ./gigachat.out/out.txt")

print(answer.json()['choices'][0]['message']['content'])
     

from IPython.display import display, Markdown
display(Markdown(answer.json()['choices'][0]['message']['content']))


# In[ ]:





# In[ ]:




