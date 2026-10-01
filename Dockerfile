FROM registry.access.redhat.com/ubi9/openjdk-21:latest as builder

COPY src .

RUN pwd && ls -lart

RUN mvn clean package