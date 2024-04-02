FROM node:20-alpine as base


FROM base as deps
COPY package.json /app/package.json
WORKDIR /app
RUN yarn

FROM base as build
WORKDIR /app
COPY --from=deps /app/node_modules /app/node_modules
COPY . /app
RUN yarn build

FROM scratch AS ui
COPY --from=build /app/dist /app

FROM halverneus/static-file-server
ENV PORT 8080
ENV FOLDER /app
COPY --from=ui /app /app

EXPOSE 8080