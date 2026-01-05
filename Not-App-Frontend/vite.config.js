import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,   // React uygulaması hep 5173 portunda çalışacak
    strictPort: true, // Eğer port doluysa hata versin
    open: true, // Uygulama açıldığında tarayıcıyı otomatik aç
  },
})
