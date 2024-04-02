<template>
  <base-modal
    :title="'Удалить выбранный элемент'"
    :modal-status="deleteModalStatus"
    @close-modal="toggleModal"
  >
    <template #modal-body>
      <h1 class="text-2xl font-bold mobile:text-xl">
        Вы действительно хотите удалить выбранный элемент?
      </h1>
    </template>
    <template #modal-footer>
      <div class="flex mx-auto mt-10 gap-8 justify-center">
        <button
          @click="toggleModal"
          class="h-12 flex items-center justify-center rounded-md p-4 w-52 bg-gray"
        >
          Отмена
        </button>
        <button
          @click="toggleModal"
          class="h-12 flex items-center justify-center rounded-md p-4 w-52 bg-light-red"
        >
          Удалить
        </button>
      </div>
    </template>
  </base-modal>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps<{
  deleteModalStatus?: boolean;
}>();
const emit = defineEmits(['closeDeleteModal']);
const toggleModal = () => {
  emit('closeDeleteModal');
};
</script>
