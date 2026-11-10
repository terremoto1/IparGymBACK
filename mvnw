#!/bin/sh
set -eu
BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
PROPERTIES="$BASE_DIR/.mvn/wrapper/maven-wrapper.properties"
distribution_url=$(sed -n 's/^[[:space:]]*distributionUrl=//p' "$PROPERTIES" | sed 's/\\:/:/g')
distribution_name=${distribution_url##*/}
distribution_version=${distribution_name#apache-maven-}
distribution_version=${distribution_version%.zip}
MAVEN_USER_HOME=${MAVEN_USER_HOME:-${M2_HOME:+$(dirname "$M2_HOME")} ${HOME}/.m2}
MAVEN_USER_HOME=$(printf '%s' "$MAVEN_USER_HOME" | awk '{$1=$1};1')
distribution_dir="$MAVEN_USER_HOME/wrapper/dists/apache-maven-$distribution_version"
maven_home="$distribution_dir/apache-maven-$distribution_version"
if [ ! -x "$maven_home/bin/mvn" ]; then
  mkdir -p "$distribution_dir"
  archive="$distribution_dir/$distribution_name"
  if command -v curl >/dev/null 2>&1; then curl -fsSL "$distribution_url" -o "$archive"
  elif command -v wget >/dev/null 2>&1; then wget -q "$distribution_url" -O "$archive"
  else echo "Maven Wrapper needs curl or wget to download Maven." >&2; exit 1; fi
  unzip -q -o "$archive" -d "$distribution_dir"
  rm -f "$archive"
fi
exec "$maven_home/bin/mvn" "$@"
