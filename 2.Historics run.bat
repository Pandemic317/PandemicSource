@echo off
title BeastPs
echo Loading Up Files...
"c:\Program Files\Java\jdk1.8.0_101\bin\java.exe" -Xmx2048m -cp bin;deps/Motivote-server.jar;deps/commons-io-2.4.jar;deps/gson-2.2.4.jar;deps/GTLVote.jar;deps/json-simple-1.1.1.jar;deps/jython.jar;deps/log4j-1.2.15.jar;deps/mina.jar;deps/mysql.jar;deps/poi.jar;deps/slf4j-nop.jar;deps/slf4j.jar;deps/voteHandler.jar;deps/xstream.jar; server.Server
pause