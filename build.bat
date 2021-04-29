@echo off
rem ソースファイルのコンパイル
javac -encoding UTF-8 -sourcepath src -d bin src\wordapp\gui\MainFrame.java
rem java -cp .\jdbc\postgresql-42.2.9.jar;.\bin wordapp.gui.MainFrame
rem jarファイルの作成
jar cvfm wordapp.jar MANIFEST.MF -C bin .
