ARG VARIANT=11
FROM mcr.microsoft.com/vscode/devcontainers/java:${VARIANT}

ARG MAVEN_VERSION=""
RUN su vscode -c "source /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""
