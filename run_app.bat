@echo off
setlocal enabledelayedexpansion

echo =====================================================================
echo  Vehicle Service Management and Maintenance Analysis System (VCET)
echo =====================================================================

REM Detect Java Compiler and Runtime
set "JAVA_BIN=java"
set "JAVAC_BIN=javac"

where javac >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    if exist "C:\Program Files\Android\Android Studio\jbr\bin\javac.exe" (
        set "JAVAC_BIN=C:\Program Files\Android\Android Studio\jbr\bin\javac.exe"
        set "JAVA_BIN=C:\Program Files\Android\Android Studio\jbr\bin\java.exe"
    ) else (
        echo [ERROR] JDK / javac was not found on your system PATH.
        echo Please ensure JDK 11+ is installed.
        pause
        exit /b 1
    )
)

echo [*] Using Java Compiler: "!JAVAC_BIN!"
echo [*] Using Java Runtime : "!JAVA_BIN!"

REM Compile Java Sources
if not exist "java\bin" mkdir "java\bin"
echo [*] Generating sources list...
python -c "import glob; files = [p.replace('\\', '/') for p in glob.glob('java/src/**/*.java', recursive=True)]; open('java/sources.txt', 'w', encoding='utf-8').write('\n'.join(files))"

echo [*] Compiling Java classes...
"!JAVAC_BIN!" -encoding UTF-8 -cp "java/lib/*" -d "java/bin" "@java/sources.txt"
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo [✔] Compilation successful!
echo [*] Launching Application...
echo.

"!JAVA_BIN!" -cp "java/bin;java/lib/*" com.vcet.vehicleservice.main.VehicleServiceApp

pause
