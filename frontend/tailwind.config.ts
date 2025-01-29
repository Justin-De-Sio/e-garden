import type { Config } from 'tailwindcss'

export default <Partial<Config>>{
  theme: {
    extend: {
      colors: {
        brandPrimary: {
          50: '#f3f8ef',
          100: '#e0edd7',
          200: '#c8dfb8',
          300: '#afd199',
          400: '#95bd75', // couleur principale
          500: '#7ea55e',
          600: '#668749',
          700: '#506936',
          800: '#3a4c25',
          900: '#283618',
          950: '#1a240f'
        }
      }
    }
  },
};
