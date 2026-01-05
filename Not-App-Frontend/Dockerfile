# ======================
# BUILD STAGE
# ======================
FROM node:20-alpine AS build

WORKDIR /app

# Copy package files first (better caching)
COPY package.json ./
COPY package-lock.json ./

# Install dependencies
RUN npm install

# Copy rest of the application
COPY . .

# Build the application
RUN npm run build

# ======================
# RUNTIME STAGE (NGINX)
# ======================
FROM nginx:alpine

WORKDIR /usr/share/nginx/html

# Copy build output from build stage
COPY --from=build /app/dist .

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]