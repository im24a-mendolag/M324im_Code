#!/bin/bash
#
# Set variable for target node (your AWS EC2 instance)
# the public DNS changes on every stop/start, so it can be passed as argument 1
DEST="${1:-ubuntu@<ec2-public-dns>}"
# Set variable for target directory where the app should be stored
TRG="~/03"
# Set private key
PRIVATE_KEY="$HOME/.ssh/M324-mendolag.pem"
# check if private key is set
if [ ! -f ${PRIVATE_KEY} ]; then
  echo ${PRIVATE_KEY} does not exist!
  exit 2
fi
# remove (with ssh) target directory for the app
ssh -i ${PRIVATE_KEY} ${DEST} "rm -rf ${TRG}"
# create (with ssh) target directory for the app
ssh -i ${PRIVATE_KEY} ${DEST} "mkdir -p ${TRG}"
# copy (with scp) all bash script to destination
scp -i ${PRIVATE_KEY} 0{1..5}*.sh ${DEST}:${TRG}
# copy (with scp) the backend to destination
# only the sources: node_modules is installed by the Dockerfile (npm install)
ssh -i ${PRIVATE_KEY} ${DEST} "mkdir -p ${TRG}/backend"
scp -i ${PRIVATE_KEY} backend/*.js backend/package.json ${DEST}:${TRG}/backend
# copy (with scp) dockerfile to destination
scp -i ${PRIVATE_KEY} Dockerfile ${DEST}:${TRG}
# copy (with scp) unit testing files to destination
scp -i ${PRIVATE_KEY} package.json ${DEST}:${TRG}
scp -i ${PRIVATE_KEY} trafficlight.test.js ${DEST}:${TRG}
