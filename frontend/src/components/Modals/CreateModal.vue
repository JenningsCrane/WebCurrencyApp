<template>
  <base-modal
    :title="modalTitle"
    :modal-status="createModalStatus"
    @close-modal="toggleModal"
  >
    <!-- EVENTS -->
    <template v-if="typeModal === 'events'" #modal-body>
      <form @submit.prevent="handleSubmit" class="edit-form">
        <!-- Title -->
        <div class="modal-section p-4 gap-3">
          <label for="title">Название</label>
          <input
            required
            placeholder="Введите название"
            class="input-outline"
            type="text"
          />
        </div>
        <!-- Description -->
        <div class="modal-section p-4 gap-3">
          <label for="descriptione">Описание</label>
          <QuillEditor
            required
            content-type="html"
            placeholder="Введите описание"
          />
        </div>
        <!-- Date and time -->
        <div class="flex justify-between gap-4">
          <div class="modal-section p-4 gap-3 basis-3/5">
            <label for="date">Дата мероприятия</label>
            <VDatePicker v-model="date" :popover="popover" :is-dark="true">
              <template #default="{ inputValue, inputEvents }">
                <input
                  required
                  :value="inputValue"
                  v-on="inputEvents"
                  class="input-outline"
                />
              </template>
            </VDatePicker>
          </div>
          <div class="modal-section p-4 gap-3 basis-3/5">
            <label for="date">Время проведение</label>
            <input required class="input-outline" type="time" />
          </div>
        </div>
        <!-- Speakers -->
        <form @submit.prevent="handleSpeaker" class="modal-section p-4 gap-3">
          <label for="speakers" class="font-bold text-lg">Спикеры</label>
          <!-- Name, Photo -->
          <div class="flex justify-between gap-4 mt-2">
            <div class="basis-3/5">
              <div class="flex flex-col gap-2">
                <label for="date">Аватарка</label>
                <UploadInput
                  @image-preview="onChildDataUpdated"
                  :key="imageKey"
                />
              </div>
            </div>
            <div class="basis-3/5 flex flex-col gap-4">
              <div class="flex flex-col gap-2">
                <label for="date">Фамилия</label>
                <input
                  required
                  class="input-outline"
                  type="text"
                  placeholder="Введите фамилию"
                  v-model="speaker.lastname"
                />
              </div>
              <div class="flex flex-col gap-2">
                <label for="date">Имя</label>
                <input
                  required
                  class="input-outline"
                  type="text"
                  placeholder="Введите имя"
                  v-model="speaker.firstname"
                />
              </div>
              <div class="flex flex-col gap-2">
                <label for="date">Роль на мероприятии</label>
                <input
                  required
                  class="input-outline"
                  type="text"
                  placeholder="Введите роль"
                  v-model="speaker.role"
                />
              </div>
            </div>
          </div>
          <!-- Bio -->
          <div class="modal-section gap-3">
            <label for="speakers">Био</label>
            <QuillEditor
              ref="speakerBio"
              required
              content-type="html"
              placeholder="Введите описание"
              v-model:content="speaker.bio"
            />
          </div>
          <div class="modal-section">
            <button type="submit" class="save-button">Добавить</button>
          </div>
          <div class="modal-section gap-3">
            <div v-for="item in speakers" :key="item" class="relative">
              <div class="flex flex-row gap-3 items-center">
                <img class="h-16 rounded-full" :src="item.imgUrl" />
                <div class="flex flex-col">
                  <p class="text-lg font-bold">
                    {{ item.lastname }}
                    {{ item.firstname }}
                  </p>
                  <span class="text-sm opacity-60">
                    {{ item.role }}
                  </span>
                </div>
                <font-awesome-icon
                  class="absolute top-4 right-4 text-xl cursor-pointer"
                  icon="circle-xmark"
                  @click="deleteSpeaker"
                />
              </div>
              <p class="mt-3" v-if="item.bio" v-html="item.bio"></p>
            </div>
          </div>
        </form>
        <!-- After party -->
        <div class="modal-section p-4 gap-3">
          <label for="afterparty">Афтепати</label>
          <QuillEditor
            required
            content-type="html"
            placeholder="Введите афтепати"
          />
        </div>
        <button type="submit" class="mt-6 save-button">Сохранить</button>
      </form>
    </template>

    <!-- EMPLOYEE -->
    <template v-if="typeModal === 'employee'" #modal-body>
      <form @submit.prevent="handleSubmit" class="edit-form">
        <div class="modal-section p-4 gap-3">
          <label for="title">Введите фамилию</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите фамилию"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Введите имя</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите имя"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Номер телефона</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите номер телефона"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Дата рождения</label>
          <VDatePicker v-model="date" :popover="popover" :is-dark="true">
            <template #default="{ inputValue, inputEvents }">
              <input
                required
                :value="inputValue"
                v-on="inputEvents"
                class="input-outline"
              />
            </template>
          </VDatePicker>
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Роль</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите роль"
          />
        </div>
        <button type="submit" class="mt-6 save-button">Сохранить</button>
      </form>
    </template>

    <!-- CLIENTS -->
    <template v-if="typeModal === 'clients'" #modal-body>
      <form @submit.prevent="handleSubmit" class="edit-form">
        <div class="modal-section p-4 gap-3">
          <label for="title">Введите фамилию</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите фамилию"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Введите имя</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите имя"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Номер телефона</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите номер телефона"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Дата рождения</label>
          <VDatePicker v-model="date" :popover="popover" :is-dark="true">
            <template #default="{ inputValue, inputEvents }">
              <input
                required
                :value="inputValue"
                v-on="inputEvents"
                class="input-outline"
              />
            </template>
          </VDatePicker>
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Роль</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите роль"
          />
        </div>
        <button type="submit" class="mt-6 save-button">Сохранить</button>
      </form>
    </template>

    <!-- TARIFFS -->
    <template v-if="typeModal === 'tariffs'" #modal-body>
      <form @submit.prevent="handleSubmit" class="edit-form">
        <div class="modal-section p-4 gap-3">
          <label for="title">Название</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите название"
          />
        </div>
        <form @submit.prevent="addServices" class="modal-section p-4 gap-3">
          <label for="title">Услуги</label>
          <div class="flex justify-between gap-4">
            <input
              required
              v-model="service"
              class="input-outline w-10/12"
              type="text"
              placeholder="Введите услуги"
            />
            <button
              type="submit"
              class="bg-white text-sm font-bold rounded text-black flex-1"
            >
              Добавить
            </button>
          </div>
          <ul v-if="services.length" class="list-disc list-inside">
            <li v-for="(service, index) in services" :key="index">
              {{ service }}
              <font-awesome-icon
                @click="removeServices(index)"
                icon="xmark"
                class="text-red ml-2"
              />
            </li>
          </ul>
        </form>
        <div class="modal-section p-4 gap-3">
          <label for="title">Цена</label>
          <input
            required
            class="input-outline"
            type="number"
            placeholder="Введите цену"
          />
        </div>
        <button type="submit" class="mt-6 save-button">Сохранить</button>
      </form>
    </template>

    <!-- SERVICES -->
    <template v-if="typeModal === 'services'" #modal-body>
      <form @submit.prevent="handleSubmit" class="edit-form">
        <div class="modal-section p-4 gap-3">
          <label for="title">Название</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите название"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Теги</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите теги"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Описание</label>
          <QuillEditor
            required
            content-type="html"
            placeholder="Введите описание"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Макс количество участников</label>
          <input
            required
            class="input-outline"
            type="number"
            placeholder="Введите количество"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Цена</label>
          <input
            required
            class="input-outline"
            type="number"
            placeholder="Введите цену"
          />
        </div>
        <button type="submit" class="mt-6 save-button">Сохранить</button>
      </form>
    </template>

    <!-- BLOG -->
    <template v-if="typeModal === 'blog'" #modal-body>
      <form @submit.prevent="handleSubmit" class="edit-form">
        <div class="modal-section p-4 gap-3">
          <label for="title">Название</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите название"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Описание</label>
          <QuillEditor
            required
            content-type="html"
            placeholder="Введите описание"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="photo">Выбрать фото</label>
          <UploadMultipleImages />
        </div>
        <button type="submit" class="mt-6 save-button">Сохранить</button>
      </form>
    </template>

    <!-- LEAGUES -->
    <template v-if="typeModal === 'leagues'" #modal-body>
      <form @submit.prevent="handleSubmit" class="edit-form">
        <div class="modal-section p-4 gap-3">
          <label for="title">Название</label>
          <input
            required
            class="input-outline"
            type="text"
            placeholder="Введите название"
          />
        </div>
        <div class="modal-section p-4 gap-3">
          <label for="title">Макс количество участников</label>
          <input
            required
            class="input-outline"
            type="number"
            placeholder="Введите количество"
          />
        </div>
        <button type="submit" class="mt-6 save-button">Сохранить</button>
      </form>
    </template>
  </base-modal>
</template>

<script setup lang="ts">
import { ref, reactive, defineProps, defineEmits } from 'vue';
import UploadMultipleImages from '@/components/Elements/UploadMultipleImages.vue';

const emit = defineEmits(['closeEditModal']);
const date = ref(new Date());
const speaker = reactive({
  firstname: null,
  lastname: null,
  role: null,
  bio: null,
  imgUrl: null,
});
const speakerBio = ref();
const speakers = ref([]);
const imageKey = ref(0);
const service = ref(null);
const services = ref([]);
const popover = ref({
  visibility: 'click',
});
const props = defineProps<{
  typeModal?: string;
  createModalStatus?: boolean;
  modalTitle?: string;
}>();
const addServices = () => {
  if (service.value) {
    services.value.push(service.value);
    service.value = null;
  }
};
const removeServices = (index) => {
  services.value.splice(index);
};

const onChildDataUpdated = (data) => {
  speaker.imgUrl = data;
};

const deleteSpeaker = () => {
  speakers.value.pop();
};
const handleSpeaker = () => {
  speakers.value.push({ ...speaker });
  clearInput();
};
const clearInput = () => {
  speaker.firstname = null;
  speaker.lastname = null;
  speaker.role = null;
  speaker.bio = null;
  speaker.imgUrl = null;
  speakerBio.value.setHTML('');
  imageKey.value++;
};
const toggleModal = () => {
  emit('closeEditModal');
};
const handleSubmit = () => {};
</script>
