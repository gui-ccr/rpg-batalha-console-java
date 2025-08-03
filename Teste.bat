@echo off
echo Teste simples do RPG
echo.

echo 1. Verificando diretorio atual...
echo %CD%

echo.
echo 2. Verificando se Java funciona...
java -version
if errorlevel 1 (
    echo ERRO: Java nao funciona!
    pause
    exit
)

echo.
echo 3. Verificando arquivos...
if exist "src\main\java\com\guiccr\rpg\Main.java" (
    echo OK: Arquivo Main.java encontrado
) else (
    echo ERRO: Main.java nao encontrado!
    pause
    exit
)

echo.
echo 4. Testando compilacao simples...
mkdir target\classes 2>nul
javac -d target\classes -cp lib\* src\main\java\com\guiccr\rpg\*.java
if errorlevel 1 (
    echo ERRO: Compilacao falhou!
    pause
    exit
)

echo.
echo 5. Testando execucao do jogo...
java -cp "target\classes;lib\*" com.guiccr.rpg.Main

echo.
echo Teste concluido!
pause
