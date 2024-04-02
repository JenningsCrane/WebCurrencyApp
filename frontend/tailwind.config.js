/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    screens: {
      mobile: { max: '550px' },

      tablet: '640px',
      // => @media (min-width: 640px) { ... }

      laptop: '1024px',
      // => @media (min-width: 1024px) { ... }

      desktop: '1280px',
      // => @media (min-width: 1280px) { ... }
    },
    extend: {
      fontFamily: {
        main: ['Gotham', 'Rimma', 'sans-serif'],
        title: ['Rimma', 'Gotham', 'sans-serif'],
      },
      colors: {
        primary: {
          DEFAULT: '#000000',
          light: '#8b8d9f',
        },
        secondary: {
          DEFAULT: '#a7e907',
          light: '#aef306',
        },
        background: {
          DEFAULT: '#ffffff',
          dim: '#f5f6f8',
        },
        red: '#df1b3e',
        green: '#2ec734',
        stroke: '#e2e4ec',
        gray: '#22252D',
        'dark-gray': '#1a1c24',
        'light-red': '#ED5E69',
      },
      borderRadius: {
        DEFAULT: '10px',
      },
      spacing: {
        4.5: '1.1rem',
      },
    },
  },
  plugins: [],
};
