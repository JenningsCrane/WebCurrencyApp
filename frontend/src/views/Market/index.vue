<template>
  <div>
    <base-modal
      :title="modalTitle"
      :modal-status="modalStatus"
      @close-modal="modalStatus = false"
    >
      <template #modal-body>
        <form
          v-if="actionType === 'buy'"
          @submit.prevent="handleBuy"
          class="flex flex-col gap-4"
        >
          <div class="flex flex-col gap-2">
            <label for="from"> Из валюты</label>
            <select v-model="buy.fromCurrency" class="input-outline" required>
              <option value="RUB" selected>Рубль</option>
            </select>
          </div>
          <div class="flex flex-col gap-2">
            <label for="from">В валюту</label>
            <select v-model="buy.toCurrency" class="input-outline" required>
              <option value="AED">Дирьхам</option>
              <option value="CNY">Юань</option>
            </select>
          </div>
          <div class="flex flex-col gap-2">
            <label for="amount">Сумма</label>
            <input
              v-model="buy.amount"
              placeholder="Введите сумму"
              class="input-outline"
              type="number"
              required
            />
          </div>
          <div>
            <button type="submit" class="save-button bg-green mt-4">
              Купить
            </button>
          </div>
        </form>
        <form
          v-if="actionType === 'sell'"
          @submit.prevent="handleSell"
          class="flex flex-col gap-4"
        >
          <div class="flex flex-col gap-2">
            <label for="from"> Из валюты</label>
            <select v-model="sell.fromCurrency" class="input-outline" required>
              <option value="AED">Дирьхам</option>
              <option value="CNY">Юань</option>
            </select>
          </div>
          <div class="flex flex-col gap-2">
            <label for="from">В валюту</label>
            <select v-model="sell.toCurrency" class="input-outline" required>
              <option value="RUB" selected>Рубль</option>
            </select>
          </div>
          <div class="flex flex-col gap-2">
            <label for="amount">Сумма</label>
            <input
              v-model="sell.amount"
              placeholder="Введите сумму"
              class="input-outline"
              type="number"
              required
            />
          </div>
          <div>
            <button type="submit" class="save-button bg-light-red mt-4">
              Продать
            </button>
          </div>
        </form>
      </template>
    </base-modal>
    <div class="flex justify-between w-full items-center">
      <div class="flex w-4/5 gap-6">
        <div class="flex flex-col w-2/5 gap-2">
          <label for="start">Дата начала</label>
          <input v-model="startDate" type="date" class="input-custom" />
        </div>
        <div class="flex flex-col w-2/5 gap-2">
          <label for="end">Дата окончания</label>
          <input v-model="endDate" type="date" class="input-custom" />
        </div>
      </div>
      <div class="flex items-center mt-5 gap-4">
        <button @click="toggleButton('sell')" class="save-button bg-light-red">
          Продать
        </button>
        <button @click="toggleButton('buy')" class="save-button bg-green">
          Купить
        </button>
      </div>
    </div>
    <div class="mt-16">
      <Line ref="chart" :data="chartData" :options="chartOptions" />
    </div>
  </div>
</template>

<script>
import moment from "moment";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "vue-chartjs";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export default {
  inject: ["axios"],
  name: "BarChart",
  components: { Line },
  data() {
    return {
      startDate: this.getCurrentDate(),
      endDate: this.getCurrentDate(),
      graphVal: [],
      graphValAed: [],
      modalStatus: false,
      userId: null,
      actionType: null,
      buy: {
        fromCurrency: "RUB",
        toCurrency: null,
        amount: 0,
      },
      sell: {
        fromCurrency: null,
        toCurrency: "RUB",
        amount: 0,
      },
      chartOptions: {
        responsive: true,
      },
    };
  },
  computed: {
    modalTitle() {
      if (this.actionType === "sell") {
        return "Продать";
      } else if (this.actionType === "buy") {
        return "Купить";
      }
    },
    chartData() {
      return {
        labels: [
          "Январь",
          "Февраль",
          "Март",
          "Апрель",
          "Май",
          "Июнь",
          "Июль",
          "Август",
          "Сентябрь",
          "Октябрь",
          "Ноябрь",
          "Декабрь",
        ],
        datasets: [
          {
            label: "Дирьхам",
            backgroundColor: "green",
            data: this.graphValAed,
          },
          {
            label: "Юань",
            backgroundColor: "red",
            data: this.graphVal,
          },
        ],
      };
    },
  },
  watch: {
    startDate() {
      this.getValue();
      this.getValueAed();
    },
    endDate() {
      this.getValue();
      this.getValueAed();
    },
  },
  methods: {
    toggleButton(type) {
      this.actionType = type;
      this.modalStatus = true;
    },

    getUser() {
      this.axios
        .get("auth/me")
        .then((response) => {
          this.userId = response.data;
        })
        .catch((error) => {});
    },
    handleSell() {
      this.axios
        .post(
          `currencies/sell?userId=${this.userId}&fromCurrency=${this.sell.fromCurrency}&toCurrency=${this.sell.toCurrency}&amount=${this.sell.amount}`
        )
        .then((response) => {
          this.$toast.open({
            message: "Успешная продажа!",
            type: "success",
            position: "top-right",
          });
          this.modalStatus = false;
        })
        .catch((error) => {
          this.$toast.open({
            message: error?.response?.data?.message,
            type: "error",
            position: "top-right",
          });
        });
    },
    handleBuy() {
      this.axios
        .post(
          `currencies/buy?userId=${this.userId}&fromCurrency=${this.buy.fromCurrency}&toCurrency=${this.buy.toCurrency}&amount=${this.buy.amount}`
        )
        .then((response) => {
          this.$toast.open({
            message: "Успешная покупка!",
            type: "success",
            position: "top-right",
          });
          this.modalStatus = false;
        })
        .catch((error) => {
          this.$toast.open({
            message: error?.response?.data?.message,
            type: "error",
            position: "top-right",
          });
        });
    },
    getCurrentDate() {
      const today = new Date();
      return today.toISOString().slice(0, 10);
    },
    getValue() {
      this.axios
        .get(
          `graph/rub-cny?start_date=${moment(this.startDate).format("DD-MM-YYYY")}&end_date=${moment(this.endDate).format("DD-MM-YYYY")}`
        )
        .then((response) => {
          const newArr = [];
          response.data.forEach((item) => {
            newArr.push(item.value);
          });
          this.graphVal = newArr;
          this.$refs.chart.render();
        })
        .catch((error) => {});
    },
    getValueAed() {
      this.axios
        .get(
          `graph/rub-aed?start_date=${moment(this.startDate).format("DD-MM-YYYY")}&end_date=${moment(this.endDate).format("DD-MM-YYYY")}`
        )
        .then((response) => {
          const newArr = [];
          response.data.forEach((item) => {
            newArr.push(item.value);
          });
          this.graphValAed = newArr;
          this.$refs.chart.render();
        })
        .catch((error) => {});
    },
  },
  mounted() {
    this.getValue();
    this.getValueAed();
    this.getUser();
    console.log(moment().subtract(1, "").startOf("month").format("MMMM"));
  },
};
</script>
