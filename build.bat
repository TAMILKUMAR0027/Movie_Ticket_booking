@echo off
echo Building Ticket Booking System...
call mvn clean package -DskipTests
echo.
echo Build complete! WAR file created in target folder.
echo.
echo To deploy to Render.com:
echo 1. Push to GitHub
echo 2. Go to render.com
echo 3. Connect repo and deploy
pause
