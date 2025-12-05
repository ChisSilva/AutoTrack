# AutoTrack
Mini-Gerenciador de frota e veículos pessoais com um sistema de cadastro de veículos




# 1. Compilação e Execução (Usando PowerShell)

⚠️ Certifique-se de que a pasta 'bin' existe antes de compilar.

## 1.1. Compilar o Projeto (Cria os arquivos .class na pasta bin)
### Uso de Get-ChildItem para garantir a compilação recursiva no PowerShell.
* mkdir bin
* javac -d bin @(Get-ChildItem -Path "src" -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName)

## 1.2. Executar o Programa
### O comando roda a classe App do pacote app.
java -cp bin app.App 




# 2. Compilação e Execução (Usando Linux/macOS - Bash)

Nestes ambientes, o compilador suporta a sintaxe de *wildcard* recursivo, simplificando a compilação:

## 2.1. Compilar o Projeto
mkdir -p bin
javac -d bin src/**/*.java

## 2.2. Executar o Programa
java -cp bin app.App
