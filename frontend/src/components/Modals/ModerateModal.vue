<template>
  <base-modal
    :title="'Модерировать выбранный элемент'"
    :modalStatus="deleteModalStatus"
    @closeModal="toggleModal"
  >
    <template v-slot:modal-body>
      <h1 class="text-2xl font-bold">
        Вы действительно хотите модерировать выбранный элемент?
      </h1>
    </template>
    <template v-slot:modal-footer>
      <div class="flex mx-auto mt-10 justify-between w-9/12">
        <button
          @click="toggleModal"
          class="h-12 flex items-center justify-center rounded-md p-4 w-52 bg-gray"
        >
          Отмена
        </button>
        <button
          @click="toggleModal"
          class="h-12 flex items-center justify-center rounded-md p-4 w-52 bg-amber-600"
        >
          Модерировать
        </button>
      </div>
    </template>
  </base-modal>
</template>
  
  <script setup lang="ts">
import { defineProps, defineEmits } from "vue";
const props = defineProps<{
  deleteModalStatus?: boolean;
}>();
const emit = defineEmits(["closeDeleteModal"]);
const toggleModal = () => {
  emit("closeDeleteModal");
};
</script>
  