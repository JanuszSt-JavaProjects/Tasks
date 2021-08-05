call runcrud
if "%ERRORLEVEL%" == "0" goto openbrowser
goto fail

:openbrowser
call "C:\Program Files\Mozilla Firefox\firefox.exe"  "http://localhost:8080/crud/v1/task/getTasks"
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.