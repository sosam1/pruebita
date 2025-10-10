@echo off
echo Iniciando servidor de servicios web SOAP...
mvn exec:java -Dexec.mainClass=presentacion.TestWebServiceServer
pause
