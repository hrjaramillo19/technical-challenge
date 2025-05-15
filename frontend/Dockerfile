# Etapa de build
FROM node:18-alpine as build

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

# Etapa de producción con nginx
FROM nginx:alpine
COPY --from=build /app/dist/<nombre-proyecto-angular> /usr/share/nginx/html

# Copia configuración personalizada de nginx si la tienes
# COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
