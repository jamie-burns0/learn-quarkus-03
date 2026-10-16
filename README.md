# learn-quarkus-03

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/learn-quarkus-03-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

## OpenShift builds

All build strategies live on `main`; no per-strategy branches are needed.

| Strategy | Files | How to run |
|---|---|---|
| s2i (in-cluster) | `.s2i/environment`, `openshift/buildconfig-s2i.yaml` | `oc apply -f openshift/buildconfig-s2i.yaml && oc start-build learn-quarkus-03-s2i` |
| Docker, JVM (in-cluster) | `openshift/Dockerfile.jvm`, `openshift/buildconfig-docker-jvm.yaml` | `oc apply -f openshift/buildconfig-docker-jvm.yaml && oc start-build learn-quarkus-03-docker-jvm` |
| Docker, native (local podman) | `openshift/Dockerfile.native`, `openshift/build-native.sh` | `openshift/build-native.sh` (set `TLS_VERIFY=false` for a self-signed registry cert) |

The native build runs locally because the cluster does not support the multi-stage Mandrel build.
