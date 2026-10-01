FROM registry.access.redhat.com/ubi9/openjdk-21:latest as builder

COPY . .

RUN ls -lart

RUN mvn clean package