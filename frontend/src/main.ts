import { createApp } from 'vue';
import { createPinia } from 'pinia';
import moment from 'moment';
import 'moment/dist/locale/ru';
import './css/style.scss';
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import { setupCalendar, Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';
import VueAwesomePaginate from 'vue-awesome-paginate';
import 'vue-awesome-paginate/dist/style.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import VueUploadComponent from 'vue-upload-component';
import mobileMixin from './consts/mobileMixin';
import { icons } from './consts/Icons';
import router from './router';
import axiosInstance from './plugins/axios'


import App from './App.vue';
import Sidebar from '@/components/Layout/Sidebar.vue';
import Table from '@/components/Layout/Table.vue';
import Filters from '@/components/Layout/FiltersTable.vue';
import Navbar from '@/components/Layout/Navbar.vue';
import BaseModal from '@/components/Modals/BaseModal.vue';
import EditModal from '@/components/Modals/EditModal.vue';
import CreateModal from '@/components/Modals/CreateModal.vue';
import DeleteModal from '@/components/Modals/DeleteModal.vue';
import ModerateModal from '@/components/Modals/ModerateModal.vue';
import Pagination from '@/components/Layout/PaginationTable.vue';
import UploadInput from '@/components/Elements/UploadInput.vue';

library.add(Object.values(icons));
const app = createApp(App);
app.config.globalProperties.$filters = {
  timeAgo(date) {
    return moment(date).locale('ru').format('LL');
  },
};
app
  .component('FileUpload', VueUploadComponent)
  .component('font-awesome-icon', FontAwesomeIcon)
  .component('Sidebar', Sidebar)
  .component('Table', Table)
  .component('Filters', Filters)
  .component('Navbar', Navbar)
  .component('BaseModal', BaseModal)
  .component('DeleteModal', DeleteModal)
  .component('ModerateModal', ModerateModal)
  .component('EditModal', EditModal)
  .component('CreateModal', CreateModal)
  .component('QuillEditor', QuillEditor)
  .component('Pagination', Pagination)
  .component('UploadInput', UploadInput)
  .mixin(mobileMixin)
  .use(createPinia())
  .use(VueAwesomePaginate)
  .provide('axios', axiosInstance)
  .use(setupCalendar)
  .component('VCalendar', Calendar)
  .component('VDatePicker', DatePicker)
  .use(router)
  .mount('#app');
