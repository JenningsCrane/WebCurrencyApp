<template>
  <Sidebar
    v-if="!isLoginPage"
    :sidebar-status="showSidebar"
    @sidebar-close="toggleSidebar"
  />
  <div class="content">
    <Navbar v-if="!isLoginPage" @toggle-sidebar="toggleSidebar" />
    <router-view class="content-view" />
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { computed, ref, onMounted, onUnmounted } from 'vue';

const route = useRoute();
const isMobileScreen = ref(true);
const showSidebar = ref(false);
const toggleSidebar = () => {
  showSidebar.value = !showSidebar.value;
};
const isLoginPage = computed(() => route.path === '/login');
const checkScreenWidth = () => {
  isMobileScreen.value = window.innerWidth < 500;
  if (isMobileScreen.value) {
    showSidebar.value = false;
  } else {
    showSidebar.value = true;
  }
};
onMounted(() => {
  window.addEventListener('resize', checkScreenWidth);
  checkScreenWidth();
});

onUnmounted(() => {
  window.removeEventListener('resize', checkScreenWidth);
});
</script>
