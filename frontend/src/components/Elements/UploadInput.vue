<template>
  <div class="row">
    <div class="col-md-5 offset-md-1">
      <form>
        <div class="form-group">
          <div v-if="!preview" class="input-file" @click="selectFiles">
            <font-awesome-icon icon="upload" style="color: #ffffff" />
            <input
              ref="fileInput"
              required
              type="file"
              @change="previewImage"
              accept="image/*"
              class="form-control-file"
              id="my-file"
            />
          </div>
          <div>
            <template v-if="preview">
              <img :src="preview" class="img-fluid rounded w-60" />
            </template>
          </div>
        </div>
      </form>
    </div>

    <div class="w-100"></div>
    <div v-if="preview" class="col-12 mt-3 text-center">
      <button @click="resetImage">Убрать</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineEmits } from "vue";
const preview = ref(null);
const image = ref(null);
const fileInput = ref(null);
const emit = defineEmits(["image-preview"]);
const sendImage = () => {
  emit("image-preview", preview.value);
};
const selectFiles = () => {
  fileInput.value.click();
};
const previewImage = (event) => {
  var input = event.target;
  if (input.files) {
    var reader = new FileReader();
    reader.onload = (e) => {
      preview.value = e.target.result;
      sendImage();
    };
    image.value = input.files[0];
    reader.readAsDataURL(input.files[0]);
  }
};
const resetImage = () => {
  preview.value = null;
  image.value = null;
};
</script>