<template>
  <div class="flex mt-6 flex-col">
    <div class="-m-1.5 overflow-x-auto">
      <div class="p-2 min-w-full inline-block align-middle">
        <div class="overflow-hidden">
          <table
            class="min-w-full divide-y divide-gray-200 dark:divide-gray-700 mobile:hidden"
          >
            <thead>
              <tr class="px-6 py-3text-xs font-medium text-gray-500 uppercase">
                <slot name="column"></slot>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
              <slot name="row"></slot>
            </tbody>
          </table>
          <div v-if="mobile">
            <slot name="mobile"></slot>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style lang="scss">
table {
  background: #1a1c24;
  border-radius: 0.5rem;
  padding: 2rem;
  thead {
    height: 3.5rem;
    tr {
      th {
        padding: 1rem;
        font-size: 0.8rem;
        text-align: start;
        &:last-child {
          text-align: end;
        }
      }
    }
  }
  tbody {
    tr {
      height: 3.5rem;
      td {
        text-align: start;
        padding: 1rem;
        font-size: 0.8rem;
        &:last-child {
          text-align: end;
        }
      }
    }
  }
}
.actions {
  font-size: 1.2rem;
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  cursor: pointer;
  height: 2rem;
}
.trash {
  color: red;
}
.edit {
  color: #ff8c00;
}
</style>
