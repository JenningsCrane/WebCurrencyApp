export default {
    data() {
        return {
            mobile: window.innerWidth < 500
        }
    },
    mounted() {
        window.addEventListener('resize', this.handleWindowResize);
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.handleWindowResize);
    },
    methods: {
        handleWindowResize() {
            this.mobile = window.innerWidth < 500;
        }
    }
}