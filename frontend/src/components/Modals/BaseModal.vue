<template>
  <div
    class="modal fixed w-full h-full bg-black bg-opacity-50 top-0 left-0 right-0 bottom-0 z-50 flex justify-end"
    v-if="modalStatus"
  >
    <div
      class="modal__content scrollbar bg-dark-gray w-700 h-full p-6 text-white overflow-auto mobile:p-4"
    >
      <div class="modal-head flex items-center justify-between">
        <h1 class="w-5/6 opacity-50 text-lg">{{ title }}</h1>
        <font-awesome-icon
          @click="toggleModal"
          icon="xmark"
          size="xl"
          class="cursor-pointer"
        />
      </div>
      <div class="modal__content-body mt-6">
        <slot name="modal-body"></slot>
      </div>
      <div class="modal__content-footer">
        <slot name="modal-footer"></slot>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps<{
  title?: string;
  modalStatus?: boolean;
}>();
const emit = defineEmits(['closeModal']);
const toggleModal = () => {
  emit('closeModal', false);
};
</script>
