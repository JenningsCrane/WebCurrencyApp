<template>
  <div
    class="filters bg-dark-gray p-3 rounded-md flex justify-between flex-wrap mobile:flex-col mobile:gap-6"
  >
    <create-modal
      :create-modal-status="createModalStatus"
      :type-modal="modalType"
      :modal-title="modalTitle"
      @close-edit-modal="createModalStatus = false"
    ></create-modal>
    <div
      class="filters__inputs flex items-center gap-4 flex-wrap mobile:justify-center mobile:w-full"
    >
      <input
        v-if="title.isShow"
        v-model="title.value"
        class="input-outline w-72 mobile:w-full"
        type="text"
        placeholder="Поиск по названию"
      />
      <p
        @click="clearFilter"
        class="text-sm opacity-50 cursor-pointer font-semibold mt-1"
      >
        Сбросить
      </p>
    </div>
    <button
      @click="createModalStatus = true"
      v-if="create.isShow"
      class="light-button"
    >
      + Добавить
    </button>
  </div>
</template>
<script setup lang="ts">
import { defineProps, ref } from 'vue';

const props = withDefaults(
  defineProps<{
    modalType?: string;
    title?: object;
    speaker?: object;
    date?: object;
    time?: object;
    create?: object;
    modalTitle?: string;
  }>(),
  {
    modalType: '',
    title: {
      isShow: false,
      value: null,
    },
    create: {
      isShow: false,
    },
  },
);

const createModalStatus: Ref<boolean> = ref(false);

const clearFilter = (): void => {
  props.title.value = null;
};
</script>
