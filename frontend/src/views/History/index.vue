<template>
  <div class="reviews flex flex-col gap-6">
    <div v-if="transactions?.length" class="flex flex-col gap-6">
      <div
        class="bg-dark-gray flex flex-col rounded-lg p-4"
        v-for="action in transactions"
        :key="action"
      >
        <p class="font-bold text-3xl flex items-center gap-2">
          <span v-if="action.operationType === 'SELL'">-</span>
          <span v-else-if="action.operationType === 'BUY'">+</span>

          {{ action.amount }} {{ action.currencyType }}
        </p>
        <div class="flex justify-between items-end">
          <span class="text-lg opacity-50">
            {{ moment(action.date).format("LL") }}
          </span>
          <div
            class="p-4 font-bold mt-4 bg-light-red rounded-lg"
            v-if="action.operationType === 'SELL'"
          >
            Продажа
          </div>
          <div
            class="p-4 font-bold mt-4 bg-green rounded-lg"
            v-else-if="action.operationType === 'BUY'"
          >
            Покупка
          </div>
        </div>
      </div>
    </div>
    <div v-else class="flex justify-center mt-72">
      <p class="font-bold">Нет транзакции</p>
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  inject: ["axios"],
  data() {
    return {
      transactions: null,
      moment,
    };
  },
  methods: {
    getUser() {
      this.axios
        .get("auth/me")
        .then((response) => {
          this.getTransactions(response.data);
        })
        .catch((error) => {});
    },
    getTransactions(id) {
      this.axios
        .get(`transactions/${id}`)
        .then((response) => {
          this.transactions = response.data;
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
