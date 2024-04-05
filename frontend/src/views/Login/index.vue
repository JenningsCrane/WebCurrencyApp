<template>
  <form
    v-if="loginType === 'signIn'"
    @submit.prevent="userLogin"
    class="login w-420 mx-auto my-auto p-6 shadow-xl bg-dark-gray text-white absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-md"
  >
    <h1 class="login__logo text-4xl font-semibold text-center">Войти</h1>

    <div class="login__inputs mt-12 flex flex-col gap-6">
      <div class="login__inputs-block flex flex-col gap-3">
        <label for="login" class="font-semibold"> Имя пользователя</label>
        <input
          required
          v-model="login.username"
          type="text"
          class="outline-none h-14 rounded-md bg-gray p-4"
        />
      </div>
      <div class="login__inputs-block flex flex-col gap-3">
        <label for="login" class="font-semibold">Пароль</label>
        <input
          required
          v-model="login.password"
          type="password"
          class="outline-none h-14 rounded-md bg-gray p-4"
        />
      </div>
    </div>
    <button
      type="submit"
      class="login__submit mt-12 w-full bg-white text-black flex justify-center items-center h-16 rounded-lg font-semibold text-xl"
    >
      Войти
    </button>
    <div class="mt-8 text-center">
      <p class="text-sm">
        У вас нет аккаунта?
        <span
          class="text-sky-500 font-semibold cursor-pointer"
          @click="handleRegister"
          >Зарегистрироваться</span
        >
      </p>
    </div>
  </form>
  <!-- Register -->
  <form
    v-if="loginType === 'signUp'"
    @submit.prevent="userRegister"
    class="login w-550 mx-auto my-auto p-6 shadow-xl bg-dark-gray text-white absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-md"
  >
    <h1 class="login__logo text-4xl font-semibold text-center">Регистрация</h1>

    <div class="login__inputs mt-12 flex flex-col gap-6">
      <div class="login__inputs-block flex flex-col gap-3">
        <label for="login" class="font-semibold"> Имя пользователя</label>
        <input
          required
          v-model="register.username"
          type="text"
          class="outline-none h-14 rounded-md bg-gray p-4"
        />
      </div>
      <div class="login__inputs-block flex flex-col gap-3">
        <label for="login" class="font-semibold">Email</label>
        <input
          required
          v-model="register.email"
          type="text"
          class="outline-none h-14 rounded-md bg-gray p-4"
        />
      </div>
      <div class="login__inputs-block flex flex-col gap-3">
        <label for="login" class="font-semibold">Номер телефона</label>
        <input
          v-model="register.phoneNumber"
          type="text"
          class="outline-none h-14 rounded-md bg-gray p-4"
        />
      </div>
      <div class="login__inputs-block flex flex-col gap-3">
        <label for="login" class="font-semibold">Пароль</label>
        <input
          required
          v-model="register.password"
          type="password"
          class="outline-none h-14 rounded-md bg-gray p-4"
        />
      </div>
    </div>
    <button
      type="submit"
      class="login__submit mt-12 w-full bg-white text-black flex justify-center items-center h-16 rounded-lg font-semibold text-xl"
    >
      Зарегистрироваться
    </button>
    <div class="mt-8 text-center">
      <p class="text-sm">
        Уже есть аккаунт?
        <span
          class="text-sky-500 font-semibold cursor-pointer"
          @click="handleLogin"
          >Войти</span
        >
      </p>
    </div>
  </form>
</template>
<script>
export default {
  inject: ["axios"],
  data() {
    return {
      register: { username: "", email: "", phoneNumber: "", password: "" },
      login: { username: "", password: "" },
      loginType: "signIn",
    };
  },
  methods: {
    handleRegister() {
      this.loginType = "signUp";
    },
    handleLogin() {
      this.loginType = "signIn";
    },
    userRegister() {
      this.axios
        .post("auth/sign-up", {
          ...this.register,
        })
        .then((response) => {
          if (response.data?.token) {
            localStorage.setItem("token", JSON.stringify(response.data?.token));
            const token = JSON.parse(localStorage.getItem("token"));
            if (token) {
              this.$toast.open({
                message: "Успешно  авторизирован!",
                type: "success",
                position: "top-right",
              });
              this.$router.push("/profile");
            }
          }
        })
        .catch((error) => {
          this.$toast.open({
            message: "Что-то пошло не так",
            type: "error",
            position: "top-right",
          });
        });
    },
    userLogin() {
      this.axios
        .post("auth/sign-in", {
          ...this.login,
        })
        .then((response) => {
          if (response.data?.token) {
            localStorage.setItem("token", JSON.stringify(response.data?.token));
            const token = JSON.parse(localStorage.getItem("token"));
            if (token) {
              this.$toast.open({
                message: "Успешно  авторизирован!",
                type: "success",
                position: "top-right",
              });
              this.$router.push("/profile");
            }
          }
        })
        .catch((error) => {
          this.$toast.open({
            message: "Что-то пошло не так",
            type: "error",
            position: "top-right",
          });
        });
    },
  },
};
</script>
