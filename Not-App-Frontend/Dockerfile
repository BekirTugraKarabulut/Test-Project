# ======================
# BUILD STAGE
# ======================
FROM node:20-alpine AS build
WORKDIR /app

# package.json ve package-lock.json kopyala
COPY ./package.json ./package-lock.json ./
RUN npm install

# Tüm frontend kaynaklarını kopyala
COPY . .
RUN npm run build

# ======================
# RUNTIME STAGE (NGINX)
# ======================
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Vite build çıktısı
COPY --from=build /app/dist .

# Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
