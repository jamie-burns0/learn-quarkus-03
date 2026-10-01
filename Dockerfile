FROM registry.access.redhat.com/ubi9/openjdk-21:latest as builder

WORKDIR /tmp/src

RUN mvn clean package