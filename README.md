# abmlSQLite
Ejemplo de SQLite + Java - Para ejercicio del curso de Java / OOP del CFP 34 

Este ejemplo usa un Driver SQLite que pueden bajar de https://bitbucket.org/xerial/sqlite-jdbc/downloads/ y copiarlo en el mismo directorio del abmSQLite.class

Ej. en tu directorio deberían quedar luego de compilar abmSQLite.java y copia el driver:

abmSQLite.java
abmSQLite.class
sqlite-jdbc-3.23.1.jar

* Para ejecutarlo: java -classpath ".:sqlite-jdbc-3.23.1.jar" abmlSQLite  (Reemplazar 3.23.1 por la versión que bajaron)

El código funciona...pero está sin comentar y todo en un único método!

Como ejercicio entonces les propongo:

* "Separar" el código en métodos. 
* Mejorar el manejo de errores.
* Mejorar la funcionalidad (?) 
* Comentar el código resultante
