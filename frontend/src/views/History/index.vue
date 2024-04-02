<template>
  <div class="reviews flex flex-col gap-6">
    <h1>History</h1>
  </div>
</template>

<script>
export default {
  inject: ["axios"],
  methods: {
    getUser() {
      const token = JSON.parse(localStorage.getItem("token"));

      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      this.axios
        .get("auth/me", config)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {});
    },
  },
  mounted() {
    this.getUser();
  },
};
</script>

<style>
.active-tab {
  background: #1a1c24;
  font-weight: 600;
  opacity: 1 !important;
}
</style>
