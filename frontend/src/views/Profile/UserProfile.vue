<template>
  <div class="profile mt-3 mobile:flex mobile:flex-col mobile:items-center">
    <div
      v-if="userInfo"
      class="profile__form mt-2 w-2/5 flex flex-col gap-5 mobile:w-11/12"
    >
      <font-awesome-icon
        icon="user"
        class="cursor-pointer mb-12 h-36 w-36 mx-auto"
      />
      <input
        readonly
        v-model="userInfo.username"
        type="text"
        class="input-custom"
      />
      <input
        readonly
        v-model="userInfo.email"
        type="email"
        class="input-custom"
      />
      <input
        readonly
        v-model="userInfo.phoneNumber"
        type="number"
        class="input-custom"
      />
      <div class="flex justify-between gap-3">
        <div
          class="w-1/3 bg-dark-gray flex flex-col rounded-lg pt-6 pb-6 items-center justify-center gap-2"
          v-for="account in userInfo.accounts"
          :key="account"
        >
          <span class="text-sm font-light">
            {{ account.currency }}
          </span>
          <p class="text-2xl font-bold">{{ account.balance }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  inject: ["axios"],
  data() {
    return {
      userInfo: null,
    };
  },
  methods: {
    getTransactions(id) {
      this.axios
        .get(`transactions/${id}`)
        .then((response) => {})
        .catch((error) => {});
    },
    getUserInfo(id) {
      this.axios
        .get(`profile/${id}`)
        .then((response) => {
          this.userInfo = response.data;
        })
        .catch((error) => {});
    },
    getUser() {
      this.axios
        .get("auth/me")
        .then((response) => {
          this.getTransactions(response.data);
          this.getUserInfo(response.data);
        })
        .catch((error) => {});
    },
  },
  mounted() {
    this.getUser();
  },
};
</script>
