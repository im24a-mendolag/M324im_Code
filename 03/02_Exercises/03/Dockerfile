# Base image is node LTS (22.x); the skeleton suggested 18.17.1, which is end-of-life
FROM node:22
# update OS, install bash and clean all installation files
RUN apt-get update \
    && apt-get install -y bash \
    && apt-get clean
# create directory server and ...
RUN mkdir /server
# set it as working directory
WORKDIR /server
# copy all local files in the backend into
# working directory
# the trailing slash is required: with several source files the
# destination must be a directory (Docker 29 refuses a bare ".")
COPY backend/* ./
# install all nodejs dependencies
RUN npm install
# expose port 3000 of the app
EXPOSE 3000
# run server
CMD ["npm", "run", "server"]
