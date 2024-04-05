import axios from 'axios';
axios.interceptors.request.use(function(config) {
    const token = store.getters.authToken
    if (token != null) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
}, function(err) {
    return Promise.reject(err)
})
const token = JSON.parse(localStorage.getItem('token'))
if (token) {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`
}
const axiosInstance = axios.create({
    baseURL: 'http://37.228.117.58:8090/'
});


export default axiosInstance;