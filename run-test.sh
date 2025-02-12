#!/bin/bash

# Default values
ENV="qa"
PLATFORM="android"
TAGS="@regression"
REPORT_TYPE="allure"

# Help function
show_help() {
    echo "Usage: ./run-test.sh [options]"
    echo
    echo "Options:"
    echo "  -e, --env        Environment (qa/staging/prod) [default: qa]"
    echo "  -p, --platform   Platform (android/ios) [default: android]"
    echo "  -t, --tags       Cucumber tags to run [default: @regression]"
    echo "  -r, --report     Report type (allure/extent) [default: allure]"
    echo "  -h, --help       Show this help message"
    echo
    echo "Examples:"
    echo "  ./run-test.sh -e staging -p ios"
    echo "  ./run-test.sh -t \"@smoke\""
    echo "  ./run-test.sh -e prod -p android -t \"@login\""
    exit 1
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    key="$1"
    case $key in
        -e|--env)
            ENV="$2"
            shift
            shift
            ;;
        -p|--platform)
            PLATFORM="$2"
            shift
            shift
            ;;
        -t|--tags)
            TAGS="$2"
            shift
            shift
            ;;
        -r|--report)
            REPORT_TYPE="$2"
            shift
            shift
            ;;
        -h|--help)
            show_help
            ;;
        *)
            echo "Unknown option: $1"
            show_help
            ;;
    esac
done

# Validate environment
if [[ ! "$ENV" =~ ^(qa|staging|prod)$ ]]; then
    echo "Error: Invalid environment. Use qa, staging, or prod"
    exit 1
fi

# Validate platform
if [[ ! "$PLATFORM" =~ ^(android|ios)$ ]]; then
    echo "Error: Invalid platform. Use android or ios"
    exit 1
fi

# Validate report type
if [[ ! "$REPORT_TYPE" =~ ^(allure|extent)$ ]]; then
    echo "Error: Invalid report type. Use allure or extent"
    exit 1
fi

echo "Running tests with configuration:"
echo "Environment: $ENV"
echo "Platform: $PLATFORM"
echo "Tags: $TAGS"
echo "Report Type: $REPORT_TYPE"
echo

# Clean previous test results
echo "Cleaning previous test results..."
rm -rf target/test-output
rm -rf allure-results
rm -rf extent-reports

# Create necessary directories
echo "Creating required directories..."
mkdir -p target/test-output
mkdir -p target/allure-results
mkdir -p target/extent-reports
mkdir -p target/site/allure-maven-plugin

# Run tests with Maven
mvn clean test \
    -Denv="$ENV" \
    -Dplatform="$PLATFORM" \
    -Dcucumber.filter.tags="$TAGS" \
    -Dtest.report.type="$REPORT_TYPE"

# Generate reports
if [ "$REPORT_TYPE" = "allure" ]; then
    echo "Generating Allure report..."
    mvn allure:report
    echo "Report generated at: target/site/allure-maven-plugin/index.html"
else
    echo "Report generated at: target/extent-reports/index.html"
fi 