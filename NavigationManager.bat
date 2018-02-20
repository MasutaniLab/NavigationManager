@echo off
set CLASSPATH=.\bin;%RTM_JAVA_ROOT%\jar\OpenRTM-aist-1.1.2.jar;%RTM_JAVA_ROOT%\jar\commons-cli-1.1.jar
pushd  %~dp0
start "%~n0" java %~n0Comp -f rtc.conf -o manager.os.hostname:%COMPUTERNAME% %*
popd
