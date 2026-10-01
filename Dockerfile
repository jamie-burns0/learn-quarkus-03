FROM registry.access.redhat.com/ubi9/openjdk-21:latest as builder

ADD /tmp/src ~/src

WORKDIR ~/src

RUN mvn clean package