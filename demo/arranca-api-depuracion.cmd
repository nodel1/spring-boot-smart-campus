@echo off
rem Arranca la API de la PR1 (sin tocar su codigo) con el depurador abierto en el puerto 5005, contra el MySQL de la
rem demo (puerto 3309). VS Code se engancha con "Adjuntar a la API (Taller D)".
title API Taller D (no cerrar)
cd /d "%~dp0..\smart-campus-api"
call .\mvnw.cmd -q spring-boot:run "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" "-Dspring-boot.run.arguments=--spring.datasource.url=jdbc:mysql://localhost:3309/smart_campus"
