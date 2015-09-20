@echo off
echo "启动数据库服务为1 ，停止数据库服务为2" 
set /p var=请输入要进行操作的序号：

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