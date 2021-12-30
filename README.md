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

# Usage
```shell
java -jar android-signature.jar "${apk_path}"
###################################
# Sample output
# {"success":true,"detail":{"V1":{"SHA256":"CD:FA:62:F8:C7:C1:F1:B4:BD:13:34:8E:23:90:7E:B8:BF:E1:D1:91:49:D3:91:F8:9C:81:9F:60:38:7D:0C:EF","Owner":"C=123456","SHA1":"46:F8:3A:AF:3D:E4:22:04:D7:35:AB:EC:61:BA:17:4D:86:FE:CE:CF","Algorithm":"SHA256withRSA","MD5":"70:6A:7F:C3:A0:9D:04:87:BB:21:10:BE:DD:40:88:65"},"V2":{"SHA256":"CD:FA:62:F8:C7:C1:F1:B4:BD:13:34:8E:23:90:7E:B8:BF:E1:D1:91:49:D3:91:F8:9C:81:9F:60:38:7D:0C:EF","Owner":"C=123456","SHA1":"46:F8:3A:AF:3D:E4:22:04:D7:35:AB:EC:61:BA:17:4D:86:FE:CE:CF","Algorithm":"SHA256withRSA","MD5":"70:6A:7F:C3:A0:9D:04:87:BB:21:10:BE:DD:40:88:65"}}}
###################################
```