<template>
  <div class="pagination absolute bottom-4 right-6">
    <vue-awesome-paginate
      :total-items="50"
      :items-per-page="5"
      :max-pages-shown="5"
      v-model="currentPage"
      :on-click="onClickHandler"
    >
      <template #prev-button>
        <span>
          <font-awesome-icon icon="left-long" />
        </span>
      </template>
      <template #next-button>
        <span>
          <font-awesome-icon icon="right-long" />
        </span>
      </template>
    </vue-awesome-paginate>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
const currentPage = ref(1);
const onClickHandler = () => {};
</script>
<style lang="scss">
.pagination-container {
  display: flex;
  column-gap: 10px;
}
.paginate-buttons {
  height: 42px;
  width: 42px;
  border-radius: 0.5rem;
  cursor: pointer;
  background-color: #1a1c24;
  color: white;
}
.active-page {
  background-color: white;
  color: black;
}
</style>