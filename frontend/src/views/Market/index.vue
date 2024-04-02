<template>
  <div class="flex justify-between w-4/5 mx-auto">
    <div class="flex flex-col w-2/5 gap-2">
      <label for="start">Дата начала</label>
      <input v-model="startDate" type="date" />
    </div>
    <div class="flex flex-col w-2/5 gap-2">
      <label for="end">Дата начала</label>
      <input v-model="endDate" type="date" />
    </div>
  </div>
  <Line ref="chart" :data="chartData" :options="chartOptions" />
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
      startDate: "01-01-2023",
      endDate: "12-01-20233",
      graphVal: [],
      graphValAed: [],
      chartOptions: {
        responsive: true,
      },
    };
  },
  computed: {
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
    getValue() {
      const token = JSON.parse(localStorage.getItem("token"));

      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      this.axios
        .get(
          `graph/rub-cny?start_date=${moment(this.startDate).format("DD-MM-YYYY")}&end_date=${moment(this.endDate).format("DD-MM-YYYY")}`,
          config
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
      const token = JSON.parse(localStorage.getItem("token"));

      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      this.axios
        .get(
          `graph/rub-aed?start_date=${moment(this.startDate).format("DD-MM-YYYY")}&end_date=${moment(this.endDate).format("DD-MM-YYYY")}`,
          config
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
  },
};
</script>
