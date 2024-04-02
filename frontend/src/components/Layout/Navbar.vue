<template>
  <div
    class="navbar flex items-center justify-between h-20 w-full mx-auto px-6 bg-dark-gray relative"
  >
    <h1 class="navbar__title text-white text-2xl font-semibold">{{ title }}</h1>
    <font-awesome-icon
      @click="toggleSidebar"
      v-if="mobile"
      size="2xl"
      icon="bars"
    />
    <div v-if="!mobile">
      <!-- Profile -->
      <img
        v-if="avatarImage"
        @click="toggleUserMenu"
        class="w-9 h-9 rounded-full cursor-pointer"
        src="https://flowbite.com/docs/images/people/profile-picture-5.jpg"
        alt="user photo"
      />
      <!-- Burger menu -->
      <font-awesome-icon
        v-else
        @click="toggleUserMenu"
        size="xl"
        icon="user"
        class="cursor-pointer"
      />
      <!--  ==== User Content === -->
      <div
        v-if="isUserMenu"
        class="navbar__menu absolute top-14 right-0 z-50 my-4 w-56 text-base list-none rounded divide-y divide-gray-100 shadow bg-gray dark:divide-gray-600"
        id="dropdown"
      >
        <div class="py-3 px-4">
          <span
            class="block text-sm font-semibold text-gray-900 dark:text-white"
          >
            Brendan Schut
          </span>
          <span class="block text-sm text-gray-500 truncate dark:text-gray-400">
            name@flowbite.com
          </span>
        </div>
        <ul
          class="py-1 text-gray-500 dark:text-gray-400"
          aria-labelledby="dropdown"
        >
          <li
            @click="pushRoute('profile')"
            class="flex items-center gap-2 block py-2 px-4 text-md cursor-pointer"
          >
            <font-awesome-icon icon="address-card" size="md" />
            Профиль
          </li>
        </ul>
        <ul
          class="py-1 text-gray-500 dark:text-gray-400"
          aria-labelledby="dropdown"
        >
          <li
            @click="pushRoute('login')"
            class="flex items-center gap-2 block py-2 px-4 text-md cursor-pointer"
          >
            <font-awesome-icon icon="arrow-right-from-bracket" size="md" />
            Выйти
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, defineEmits } from "vue";
import { useRoute, useRouter } from "vue-router";

const title = ref(null);
const route = useRoute();
const router = useRouter();
const isUserMenu = ref(false);
const avatarImage = ref(true);
const emit = defineEmits(["toggleSidebar"]);
const toggleSidebar = () => {
  emit("toggleSidebar");
};

const toggleUserMenu = () => {
  isUserMenu.value = !isUserMenu.value;
};
const pushRoute = (path) => {
  isUserMenu.value = false;
  router.push(`${path}`);
};
onMounted(() => {
  title.value = route.name;
});

watch(route, (to, from) => {
  title.value = to.name;
});
</script>
