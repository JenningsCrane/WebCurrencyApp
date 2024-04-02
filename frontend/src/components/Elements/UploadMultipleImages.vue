<template>
  <div class="mt-2 flex gap-3 flex-wrap">
    <div
      @click="selectFiles"
      class="border border-opacity-10 border-white rounded-md p-3 flex items-center justify-center w-24 h-24 cursor-pointer"
    >
      <input
        type="file"
        @change="onFileChanged"
        accept="image/*"
        ref="fileInput"
        class="hidden"
        multiple
        required
      />
      <font-awesome-icon icon="upload" style="color: #ffffff" size="2xl" />
    </div>
    <div v-if="files.length > 0">
      <ul class="flex gap-3">
        <li
          v-for="(file, index) in files"
          :key="index"
          class="flex flex-col items-center gap-2"
        >
          <img
            class="rounded h-24 w-24"
            :src="getFilePreview(file)"
            alt="Preview"
          />
          <button @click="removeImage(index)">Убрать</button>
        </li>
      </ul>
    </div>
  </div>
</template>
  
<script setup lang="ts">
import { ref } from "vue";

const maxImages = 10;
const fileInput = ref<HTMLInputElement | null>(null);
const files = ref<File[]>([]);
const selectFiles = () => {
  fileInput.value.click();
};
function onFileChanged(event: Event) {
  const target = event.target as HTMLInputElement;
  if (target && target.files) {
    const newFiles = Array.from(target.files).slice(
      0,
      maxImages - files.value.length
    );
    files.value = [...files.value, ...newFiles];
  }
}

function removeImage(index: number) {
  files.value.splice(index, 1);
}

async function saveImages() {
  if (files.value.length > 0) {
    try {
      // Save each file in the array
      for (const file of files.value) {
        console.log("File saved:", file.name);
      }
      // Clear the selected files after saving
      files.value = [];
    } catch (error) {
      console.error(error);
      // Handle error if needed
    }
  }
}

function getFilePreview(file: File): string | null {
  return file.type.startsWith("image/") ? URL.createObjectURL(file) : null;
}
</script>