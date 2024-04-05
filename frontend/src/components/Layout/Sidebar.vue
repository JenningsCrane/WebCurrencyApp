<template>
  <div
    class="sidebar overflow-hidden bg-dark-gray w-96 relative bottom-0 top-0 left-0 w-56 text-gray-300"
    v-if="sidebarStatus"
  >
    <div
      class="sidebar__logo text-3xl font-semibold text-left h-20 border-b border-gray-300 px-4 flex items-center justify-between"
    >
      <h1>Web Currency</h1>
      <font-awesome-icon
        @click="toggleSidebar"
        v-if="mobile"
        icon="xmark"
        size="lg"
      />
    </div>
    <div class="sidebar__links mt-8">
      <div
        class="mt-4"
        v-for="item in listItems"
        :key="item"
        @click="handleListItem(item)"
      >
        <!-- Parent -->
        <div
          class="sidebar__links-parent w-95 mx-auto flex items-center justify-between gap-4 font-semibold cursor-pointer h-14 px-2 rounded"
          :class="{ 'active-link': selectedLink === item.slug }"
        >
          <div class="flex gap-3 items-center">
            <font-awesome-icon :height="24" :width="24" :icon="item.icon" />
            <span>{{ item.title }}</span>
          </div>
          <font-awesome-icon
            :height="12"
            :width="12"
            v-if="
              item.children && item.children.length && dropdownOpen[item.slug]
            "
            icon="chevron-up"
          />
          <font-awesome-icon
            :height="12"
            :width="12"
            v-else-if="item.children && item.children.length"
            icon="chevron-down"
          />
        </div>
        <!-- Children -->
        <div
          v-for="child in item.children"
          :key="child"
          v-show="dropdownOpen[item.slug]"
          @click.stop="pushRoute(child)"
          :class="{ 'active-link': selectedLink === child.slug }"
          class="sidebar__links-children w-11/12 mx-auto flex items-center gap-4 font-semibold cursor-pointer h-14 px-2 rounded"
        >
          <font-awesome-icon :height="20" :width="20" :icon="child.icon" />
          <span>{{ child.title }}</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { useRouter } from "vue-router";
import { ref, onMounted, onUnmounted, defineProps, defineEmits } from "vue";
import listItems from "@/consts/SidebarLinks";

defineProps<{
  sidebarStatus?: boolean;
}>();
const emits = defineEmits(["sidebarClose"]);
const router = useRouter();
const selectedLink = ref("history");
const dropdownOpen = ref({});
const isMobileScreen = ref(false);
const checkScreenWidth = () => {
  isMobileScreen.value = window.innerWidth < 500;
};
const toggleSidebar = () => {
  if (isMobileScreen.value) {
    emits("sidebarClose", false);
  }
};
const handleListItem = (item) => {
  selectedLink.value = item.slug;
  localStorage.setItem("sidebarLink", selectedLink.value);
  if (item.router_path) {
    router.push({ path: item.router_path, params: { title: item.slug } });
  }
  if (item.children) {
    dropdownOpen.value[item.slug] = !dropdownOpen.value[item.slug];
    return;
  }
  dropdownOpen.value = {};

  toggleSidebar();
};
const pushRoute = (item) => {
  router.push(item.router_path);
  selectedLink.value = item.slug;
  toggleSidebar();
};
onMounted(() => {
  selectedLink.value = localStorage.getItem("sidebarLink");
  window.addEventListener("resize", checkScreenWidth);
  checkScreenWidth();
});

onUnmounted(() => {
  window.removeEventListener("resize", checkScreenWidth);
});
</script>
<style lang="scss">
.active-link {
  background: #f3f4f6;
  color: black;
}
</style>
