@echo off
setlocal EnableDelayedExpansion

:: Default values
set "ENV=qa"
set "PLATFORM=android"
set "TAGS=@regression"
set "REPORT_TYPE=allure"

:: Parse command line arguments
:parse_args
if "%~1"=="" goto :validate
if /i "%~1"=="-e" (
    set "ENV=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="--env" (
    set "ENV=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="-p" (
    set "PLATFORM=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="--platform" (
    set "PLATFORM=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="-t" (
    set "TAGS=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="--tags" (
    set "TAGS=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="-r" (
    set "REPORT_TYPE=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="--report" (
    set "REPORT_TYPE=%~2"
    shift
    shift
    goto :parse_args
)
if /i "%~1"=="-h" (
    goto :show_help
)
if /i "%~1"=="--help" (
    goto :show_help
)
echo Unknown option: %~1
goto :show_help

:validate
:: Validate environment
if not "%ENV%"=="qa" if not "%ENV%"=="staging" if not "%ENV%"=="prod" (
    echo Error: Invalid environment. Use qa, staging, or prod
    exit /b 1
)

:: Validate platform
if not "%PLATFORM%"=="android" if not "%PLATFORM%"=="ios" (
    echo Error: Invalid platform. Use android or ios
    exit /b 1
)

:: Validate report type
if not "%REPORT_TYPE%"=="allure" if not "%REPORT_TYPE%"=="extent" (
    echo Error: Invalid report type. Use allure or extent
    exit /b 1
)

echo Running tests with configuration:
echo Environment: %ENV%
echo Platform: %PLATFORM%
echo Tags: %TAGS%
echo Report Type: %REPORT_TYPE%
echo.

:: Create necessary directories
echo Creating required directories...
if not exist "target\test-output" mkdir "target\test-output"
if not exist "target\allure-results" mkdir "target\allure-results"
if not exist "target\extent-reports" mkdir "target\extent-reports"
if not exist "target\site\allure-maven-plugin" mkdir "target\site\allure-maven-plugin"

:: Clean previous test results
echo Cleaning previous test results...
if exist "target\test-output" rmdir /s /q "target\test-output"
if exist "allure-results" rmdir /s /q "allure-results"
if exist "extent-reports" rmdir /s /q "extent-reports"

:: Run tests with Maven
call mvn clean test ^
    -Denv="%ENV%" ^
    -Dplatform="%PLATFORM%" ^
    -Dcucumber.filter.tags="%TAGS%" ^
    -Dtest.report.type="%REPORT_TYPE%"

:: Generate reports
if "%REPORT_TYPE%"=="allure" (
    echo Generating Allure report...
    call mvn allure:report
    echo Report generated at: target/site/allure-maven-plugin/index.html
) else (
    echo Report generated at: target/extent-reports/index.html
)
exit /b 0

:show_help
echo Usage: run-tests.bat [options]
echo.
echo Options:
echo   -e, --env        Environment (qa/staging/prod) [default: qa]
echo   -p, --platform   Platform (android/ios) [default: android]
echo   -t, --tags       Cucumber tags to run [default: @regression]
echo   -r, --report     Report type (allure/extent) [default: allure]
echo   -h, --help       Show this help message
echo.
echo Examples:
echo   run-tests.bat -e staging -p ios
echo   run-tests.bat -t "@smoke"
echo   run-tests.bat -e prod -p android -t "@login"
exit /b 1 