# Application Binaries

This directory contains the mobile application binaries for testing:

## Android
Place your Android APK files in the `android` directory:
- Debug build: `android/app-debug.apk`
- Release build: `android/app-release.apk`

## iOS
Place your iOS app files in the `ios` directory:
- Simulator build: `ios/app.app`
- Device build: `ios/app.ipa`

Note: These files are not committed to version control. Contact the development team to obtain the latest builds. 

# Run Android tests with @smoke tag in QA environment
./run-tests.sh android qa @smoke

# Run iOS tests with @regression tag in staging environment
./run-tests.sh ios staging @regression

# Run all tests
./run-tests.sh android qa 