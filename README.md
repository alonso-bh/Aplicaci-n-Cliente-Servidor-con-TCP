# Aplicación Cliente/Servidor sobre sockets TCP en Java
Resolución de la práctica 2 sobre desarrollo de aplicaciones C/S sencillas de la asignatura Fundamentos de Redes del Grado en Ingeniería Informática de la Universidad de Granada. 

## Autores

* Alonso Bueno Herrero
* Manuel Castellón Reguero

Calificación de la práctica: 10/10

## ¿Cómo probar la aplicación? 
Hay que seguir unos sencillos pasos:
1. En primer lugar, descargar el repositorio,
2. En primer lugar, vamos a la carpeta (proyecto Netbeans) llamada `ServidorCitasMedicasTCP`, vamos a la carpeta `dist`, y ejecutamos el archivo `.jar` que hay. Este archivo es el servidor, que ha de arrancarse lo primero de todo, y se queda en segundo plano esperando que le lleguen peticiones.
3. Vamos a arrancar el cliente. Para ello, y de forma análoga a lo que hicimos con el servidor, vamos a la carpeta del repositorio llamada `ClienteTCP`, entramos en el directorio `dist` y ejecutamos el fichero `.jar` que es el programa cliente con la interfaz gráfica incluida. 
4. Ya podemos hacer las pruebas que queramos. 

## Notas sobre la "infraestructura" que hay tras la aplicación

* Sobre la **"base de datos"**, se ha simulado mediante una clase Java, para simplificar el proyecto, debido a que el objetivo no era implementar una aplicación de gestión de base de datos. Se dan más detalles en la memoria de la práctica en PDF de este repositorio.
* Sobre el uso de la **interfaz gráfica**, se aconseja consultar la memoria de la práctica, ya que pide, tanto para solicitar una cita como para anularla, la fecha en cuestión, y no se pueden consultar las fechas disponibles desde dicha interfaz, por lo que en dicha memoria hay varias fechas con las que se pueden hacer las pruebas. De cualquier forma, en el fichero `BaseDatos.java` (carpeta `Servidor`) están (como es lógico) todas las fechas que se han registrado en la "base de datos" para hacer las pruebas. 
