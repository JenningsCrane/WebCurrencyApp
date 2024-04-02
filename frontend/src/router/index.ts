import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: "/",
    redirect: "/login"
},
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login/index.vue'),
  },
  {
    path: '/history',
    name: 'История операций',
    component: () => import('@/views/History/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Профиль',
    component: () => import('@/views/Profile/UserProfile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/graph',
    name: 'График рынка',
    component: () => import('@/views/Market/index.vue'),
    meta: { requiresAuth: true }
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
router.beforeEach((to, from, next) => {

  if (to.matched.some(record => record.meta.requiresAuth)) {
      function checkIfUserIsLoggedIn() {
          const userToken = JSON.parse(localStorage.getItem('token'));
          const isLoggedIn = Boolean(userToken);

          return isLoggedIn;
      }
      const isLoggedIn = checkIfUserIsLoggedIn()
      if (!isLoggedIn) {
          next('/login');
      } else {
          next();
      }
  } else {
      next();
  }
})
export default router;