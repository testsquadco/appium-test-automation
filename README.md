<p align="center">
  <img src="https://testsquad.co/wp-content/uploads/2022/04/testsquad-opt2-01-scaled-300x71.jpg" width="400"/>
</p>

# TestSquad Mobile Automation Framework (Boiler-plate)

## About TestSquad

TestSquad is a software testing company specializing in manual and automated testing solutions, ensuring high-quality software for global clients. We provide expert QA services, including mobile automation, to enhance product reliability and performance.

📩 Contact us: info@testsquad.co | 🌐 Website: https://testsquad.co

---

A robust test automation framework for mobile applications supporting both Android and iOS platforms, with capabilities to run tests locally or on LambdaTest cloud platform.

## 🚀 Features

- **Cross-Platform Support**
  - Android (Native & Hybrid apps)
  - iOS (Native & Hybrid apps)
  - React Native applications

- **Framework Components**
  - Appium for mobile automation
  - Cucumber for BDD
  - JUnit for test execution
  - Allure for reporting
  - Maven for dependency management
  - Support for parallel execution

- **Key Capabilities**
  - Page Object Model implementation
  - Data-driven testing
  - Keyword-driven approach
  - Screenshot capture on failure
  - Video recording (on LambdaTest)
  - Detailed logging
  - Multiple environment support
  - Parallel test execution

## 🚀 Quick Start

### Using run-test.sh

The framework includes a shell script to simplify test execution:

```bash
# Give execute permission
chmod +x run-test.sh

# Run tests with default configuration (QA environment, Android platform)
./run-test.sh

# Run with specific environment and platform
./run-test.sh -e staging -p ios

# Run with additional options
./run-test.sh -e prod -p android -t "@smoke" -r "allure"
```

### Script Options

| Option | Description | Default Value |
|--------|-------------|---------------|
| -e, --env | Environment (qa/staging/prod) | qa |
| -p, --platform | Platform (android/ios) | android |
| -t, --tags | Cucumber tags to run | @regression |
| -r, --report | Report type (allure/extent) | allure |
| -h, --help | Show help message | - |

### Examples

```bash
# Run smoke tests on QA
./run-test.sh -t "@smoke"

# Run regression on staging iOS
./run-test.sh -e staging -p ios -t "@regression"

# Run specific feature on prod
./run-test.sh -e prod -t "@login"
```

## 🛠️ Prerequisites

### Required Software
- Java JDK 11 or higher
- Maven 3.8 or higher
- Node.js and npm
- Appium 2.0 or higher
- Android Studio (for Android testing)
- Xcode (for iOS testing)
- Git

### Environment Setup

1. **Java Setup**
   ```bash
   # Verify Java installation
   java -version
   ```

2. **Android Setup**
   - Install Android Studio
   - Set ANDROID_HOME environment variable
   - Add platform-tools to PATH
   ```bash
   # Verify ADB installation
   adb devices
   ```

3. **iOS Setup** (Mac only)
   - Install Xcode
   - Install Xcode Command Line Tools
   ```bash
   xcode-select --install
   ```

4. **Appium Setup**
   ```bash
   # Install Appium
   npm install -g appium

   # Install Appium Drivers
   appium driver install uiautomator2
   appium driver install xcuitest
   ```

## 📁 Project Structure

```
mobile-test-automation/
├── apps/
│   ├── android/
│   │   └── app-debug.apk
│   └── ios/
│       └── app.app
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/framework/
│   │   │       ├── config/
│   │   │       ├── core/
│   │   │       ├── pages/
│   │   │       └── utils/
│   │   └── resources/
│   │       └── config/
│   └── test/
│       ├── java/
│       │   └── com/framework/
│       │       ├── runners/
│       │       ├── steps/
│       │       └── tests/
│       └── resources/
│           ├── features/
│           └── testdata/
└── pom.xml
```

## ⚙️ Configuration

### Local Execution Setup
1. Update `src/main/resources/config/qa.properties`:
   ```properties
   # Android Configuration
   android.deviceName=Pixel_4_API_30
   android.app.path=apps/android/app-debug.apk
   android.app.package=com.example.app
   android.app.activity=com.example.app.MainActivity

   # iOS Configuration
   ios.deviceName=iPhone 12
   ios.version=14.5
   ios.app.path=apps/ios/app.app
   ios.bundleId=com.example.app
   ```

### LambdaTest Setup
1. Set environment variables:
   ```bash
   export LT_USERNAME=your_username
   export LT_ACCESS_KEY=your_access_key
   ```

2. Update LambdaTest configuration in properties file:
   ```properties
   lambdatest.build=Mobile Automation Build
   lambdatest.project=Mobile Test Framework
   lambdatest.testing.type=app
   lambdatest.isRealMobile=true
   ```

## 🚀 Running Tests

### On Windows:
```batch
# Run Android tests with smoke tag in QA environment
run-tests.bat android qa @smoke

# Run iOS tests with regression tag on LambdaTest
run-tests.bat ios staging @regression lambdatest

# Run all tests
run-tests.bat android qa
```

### On Unix-like systems (Linux/MacOS):
```bash
# Make script executable
chmod +x run-tests.sh

# Run Android tests
./run-tests.sh android qa @smoke

# Run iOS tests on LambdaTest
./run-tests.sh ios staging @regression lambdatest
```

### Command Line Parameters:
1. `platform`: android/ios (required)
2. `env`: qa/staging/prod (optional, defaults to qa)
3. `tags`: @smoke, @regression, etc. (optional)
4. `execution_platform`: local/lambdatest (optional, defaults to local)

## 📊 Reports

### Allure Reports
Reports are automatically generated after test execution:
```bash
# Open Allure report
mvn allure:serve
```

Report includes:
- Test execution summary
- Step-by-step test details
- Screenshots of failures
- Environment information
- Test execution timeline

## 🔍 Writing Tests

### Feature File Example
```gherkin
Feature: Login Functionality

  @smoke @regression
  Scenario: User can login with valid credentials
    Given the app is launched
    When user enters username "testuser"
    And user enters password "password123"
    And user taps on login button
    Then user should be logged in successfully
```

### Step Definition Example
```java
@Given("the app is launched")
public void theAppIsLaunched() {
    loginPage.verifyLoginPageDisplayed();
}

@When("user enters username {string}")
public void userEntersUsername(String username) {
    loginPage.enterUsername(username);
}
```

## 🔧 Troubleshooting

### Common Issues
1. **Appium Connection Issues**
   - Verify Appium is running
   - Check port availability (default: 4723)
   - Ensure device/emulator is connected

2. **Device Detection Issues**
   - For Android: Run `adb devices`
   - For iOS: Check Xcode Devices window

3. **App Installation Issues**
   - Verify app path in properties file
   - Check app signing for iOS
   - Ensure correct app package name

## 📝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 🤝 Support

Need help implementing this framework or looking for custom automation solutions? Contact TestSquad:

- 📧 Email: info@testsquad.co
- 🌐 Website: https://testsquad.co
- 💼 Services: Mobile Testing, Automation Solutions, QA Consulting

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

<p align="center">Powered by <a href="https://testsquad.co">TestSquad</a> - Your Quality Assurance Partner</p> 
