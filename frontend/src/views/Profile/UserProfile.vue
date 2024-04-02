<template>
  <div class="profile mt-3 mobile:flex mobile:flex-col mobile:items-center">
    <div>
      <div @click="selectFiles">
        <input
          ref="fileInput"
          required
          type="file"
          @change="previewImage"
          accept="image/*"
          class="form-control-file hidden"
          id="my-file"
        />
      </div>
    </div>
    <div class="profile__form mt-8 w-2/5 flex flex-col gap-5 mobile:w-11/12">
      <input v-model="userInfo.firstname" type="text" class="input-custom" />
      <input v-model="userInfo.lastname" type="text" class="input-custom" />
      <input v-model="userInfo.email" type="email" class="input-custom" />
      <VDatePicker
        v-model="userInfo.dateBirth"
        :popover="popover"
        :is-dark="true"
      >
        <template #default="{ inputValue, inputEvents }">
          <input
            required
            :value="inputValue"
            v-on="inputEvents"
            class="input-custom"
          />
        </template>
      </VDatePicker>
      <button class="light-button h-12">Сохранить</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const userInfo = ref({
  firstname: "Brendan",
  lastname: "Schut",
  email: "brendanschut@gmai.com",
  dateBirth: new Date(),
});
const preview = ref(null);
const image = ref(null);
const fileInput = ref(null);
const selectFiles = () => {
  fileInput.value.click();
};
const previewImage = (event) => {
  const input = event.target;
  if (input.files) {
    const reader = new FileReader();
    reader.onload = (e) => {
      preview.value = e.target.result;
    };
    image.value = input.files[0];
    reader.readAsDataURL(input.files[0]);
  }
};
</script>
