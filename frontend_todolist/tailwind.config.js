/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts}'],
  theme: {
    extend: {
      colors: {
        bar: '#f8f8f8',
        grey: '#808080',
        LoginView: '#7b68ee',
        RegisterView: '#556b2f',
        ChangePasswordView: '#2f4f4f'
      }
    }
  },
  plugins: []
}
