@echo off
echo "�������ݿ����Ϊ1 ��ֹͣ���ݿ����Ϊ2" 
set /p var=������Ҫ���в�������ţ�

if "%var%"=="1" goto net1
if "%var%"=="2" goto net2

:net1
start "" "G:\Learning\songtie-kuangkuang\Navicat\navicat.exe"
@net start MySQL
goto end

:net2
@net stop MySQL
goto end
:end