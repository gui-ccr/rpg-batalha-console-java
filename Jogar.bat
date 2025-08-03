@echo off
title RPG Batalha Console

echo.
echo ===============================================
echo           RPG BATALHA CONSOLE
echo.
echo        AVENTURA EPICA EM JAVA
echo.
echo      Crie seu heroi - Lute - Evolua
echo ===============================================
echo.

cd /d "%~dp0"

echo Verificando Java...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERRO: Java nao encontrado!
    pause
    exit /b 1
)

echo Java OK!
echo Compilando jogo...

if not exist "target\classes" mkdir target\classes >nul 2>&1
javac -d target\classes -cp "lib/*" src\main\java\com\guiccr\rpg\*.java >nul 2>&1
if errorlevel 1 (
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo Jogo preparado!
echo.
echo ===============================================
echo           INICIANDO JOGO
echo ===============================================
echo.

java -cp "target\classes;lib/*" com.guiccr.rpg.Main

echo.
echo ===============================================
echo         JOGO ENCERRADO
echo ===============================================
echo.
echo Obrigado por jogar!
pause
