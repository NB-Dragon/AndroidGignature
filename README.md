# Introduce
> A tool to extract apk signature information.

# Guide

## Generate Library

### Step 1: Archive Android Source Code
```shell
CURRENT_DIRECTORY="path/to/current/project"
git clone https://android.googlesource.com/platform/tools/apksig
```

### Step 2: Build It As Gradle Project
```shell
CURRENT_DIRECTORY="path/to/current/project"
cp "path/to/your/gradle" "${CURRENT_DIRECTORY}/apksig"
# Normally gradle is a directory
cp "path/to/your/gradlew" "${CURRENT_DIRECTORY}/apksig"
# Normally gradlew is an executable file
cd "${CURRENT_DIRECTORY}/apksig" && "${CURRENT_DIRECTORY}/apksig/gradlew" jar
```

## Copy Library
```shell
CURRENT_DIRECTORY="path/to/current/project"
cp "${CURRENT_DIRECTORY}/apksig/build/libs/"* "${CURRENT_DIRECTORY}/sample/libs"
```
