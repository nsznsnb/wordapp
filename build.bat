@echo off
javac -encoding UTF-8 -sourcepath src -d bin src\wordapp\gui\MainFrame.java
rem java -cp .\jdbc\postgresql-42.2.20.jar;.\bin wordapp.gui.MainFrame
jar cvfm wordapp.jar MANIFEST.MF -C bin .
