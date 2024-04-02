<template>
  <div class="tariffs flex flex-col gap-6">
    <delete-modal
      :delete-modal-status="deleteModalStatus"
      @close-delete-modal="deleteModalStatus = false"
    ></delete-modal>
    <edit-modal
      :edit-modal-status="editModalStatus"
      @close-edit-modal="editModalStatus = null"
    ></edit-modal>
    <Filters
      :modal-type="'tariffs'"
      :modal-title="'Создать новый тариф'"
      :title="{
        isShow: true,
      }"
      :create="{
        isShow: true,
      }"
    />
    <div
      v-for="tarif in tariffs"
      :key="tarif"
      class="bg-dark-gray p-6 rounded-md mobile:p-4"
    >
      <div class="flex items-center justify-between">
        <h1 class="text-2xl font-bold uppercase mobile:text-xl">
          {{ tarif.title }}
        </h1>
        <div class="flex gap-3">
          <button class="edit text-xl" @click="editItem()">
            <font-awesome-icon icon="pen-to-square" />
          </button>
          <button class="trash text-xl" @click="deleteItem()">
            <font-awesome-icon icon="trash" />
          </button>
        </div>
      </div>
      <ul class="list-disc list-inside flex flex-col gap-4 mt-6">
        <li v-for="service in tarif.services" :key="service">
          {{ service }}
        </li>
      </ul>
      <div class="flex justify-between items-center mt-10">
        <p class="flex items-center gap-2 opacity-70">*фуршет включён</p>
        <p class="font-bold text-2xl">{{ tarif.price }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const editModalStatus = ref();
const deleteModalStatus = ref(false);
const deleteItem = () => {
  deleteModalStatus.value = true;
};
const editItem = () => {
  editModalStatus.value = 'tariffs';
};
const tariffs = ref([
  {
    title: 'СТАНДАРТ',
    price: '1 500 р',
    services: ['Посещение мероприятия', 'Нетворкинг', 'Участие в активностях'],
  },
  {
    title: 'БИЗНЕС',
    price: '3 500 р',
    services: [
      'Посещение мероприятия',
      'Нетворкинг',
      'Участие в активностях',
      'Запись выступлений всех спикеров',
      'Места ближе к сцене',
      'Отдельный чат участников',
      'Возможность присутствовать на онлайн-мастермайнде VIP-тарифа',
    ],
  },
]);
</script>
