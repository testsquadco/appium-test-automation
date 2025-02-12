#!/bin/bash

# Check if running on Windows
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
    echo "Please use run-tests.bat on Windows systems"
    exit 1
fi

PLATFORM=$1
ENV=$2
TAGS=$3
EXECUTION_PLATFORM=${4:-local}  # default to local if not specified

# Check if required parameters are provided
if [ -z "$PLATFORM" ]; then
    echo "Error: Platform parameter is required"
    echo "Usage: ./run-tests.sh <platform> [env] [tags] [execution_platform]"
    echo "Example: ./run-tests.sh android qa @smoke local"
    exit 1
fi

# Function to check if Appium is installed
check_appium_installation() {
    if ! command -v appium &> /dev/null; then
        echo "Appium is not installed. Installing Appium..."
        if ! command -v npm &> /dev/null; then
            echo "Error: npm is not installed. Please install Node.js first."
            exit 1
        fi
        npm install -g appium || { echo "Error installing Appium"; exit 1; }
        appium driver install uiautomator2 || { echo "Error installing uiautomator2"; exit 1; }
        appium driver install xcuitest || { echo "Error installing xcuitest"; exit 1; }
    fi
}

# Function to check if Node.js is installed
check_nodejs() {
    if ! command -v node &> /dev/null; then
        echo "Error: Node.js is not installed"
        echo "Please install Node.js from https://nodejs.org/"
        exit 1
    fi
}

# Check if running locally and install/setup Appium if needed
if [ "$EXECUTION_PLATFORM" = "local" ]; then
    check_nodejs
    check_appium_installation
fi

# Run tests
echo "Running tests on $EXECUTION_PLATFORM..."
mvn clean test -Dplatform=$PLATFORM -Denv=$ENV -Dcucumber.filter.tags="$TAGS" -Dtest.execution.platform=$EXECUTION_PLATFORM || {
    echo "Error: Test execution failed"
    exit 1
}

# Generate Allure report
echo "Generating Allure report..."
mvn allure:report || {
    echo "Error: Failed to generate Allure report"
    exit 1
}

echo "Test execution completed successfully!" 