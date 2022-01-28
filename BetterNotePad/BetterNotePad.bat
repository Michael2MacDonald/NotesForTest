@echo off
set d=%~dp0
start javaw -jar "%d%BetterNotePad.jar" %*
EXIT /b 0
