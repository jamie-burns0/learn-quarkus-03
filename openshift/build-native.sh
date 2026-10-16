#!/usr/bin/env bash
# Builds the native image locally with podman, pushes it to the OpenShift
# internal registry and creates the app on first run.
set -euo pipefail

cd "$(dirname "$0")/.."

APP=learn-quarkus-03
NS=$(oc project -q)
REG=$(oc get route default-route -n openshift-image-registry -o jsonpath='{.spec.host}')
IMG=$REG/$NS/$APP:native

podman build -f openshift/Dockerfile.native -t "$IMG" .
podman login --tls-verify="${TLS_VERIFY:-true}" -u "$(oc whoami)" -p "$(oc whoami -t)" "$REG"
podman push --tls-verify="${TLS_VERIFY:-true}" "$IMG"

if ! oc get deployment "$APP" >/dev/null 2>&1; then
  oc new-app --image-stream="$APP:native" --name="$APP"
fi
