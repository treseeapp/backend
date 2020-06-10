# Tresee - Backend Java EE + SpringBoot
###### Trabajo de final de grado (TFG).

Tresee es una plataforma que intenta unir mas que nunca el profesorado 
con el alumnado en la durada de las practicas en la empresa. Dando 
una mayor facilidad a la hora del estudiante reivsar sus horas trabajadas 
y el profesor poder controlar el estado de dichas de cada estudiante en 
todo momento.


### Como usar la aplicación
Si quieres ver la aplicacion desplegada, solo tienes que acceder a [nuestra web](https://tresee.app).

Si lo que quieres, es ejecutarla en tu ordenador en modo de desarrollo tendras que seguir el manual de instalacion que tenemos mas abajo.

###Manual de instalacion
Lo primero que tenemos que hacer es obivamente clonar este repositorio, podreis 
encontrar instrucciones en bitbucket, en la pagina principal de este repositorio.

#### 1 · Añadimos las variables 
Tendremos que añadir un archivo dentro de del directorio ``src/main/resources (Linux)`` y ``src\main\resources (Windows)`` que se llamara `application.properties`. Dentro de ese
directorio, encontraremos un ``application.properties-sample``, este tendra la estructura
 que tendrá que cumplir el que 
añadamos con todas las variables que tendremos que preparar.

Para que el profesorado pueda evaluar este proyecto, daremos un properties con las 
api keys que usamos en esta app. Todo lo demas como pueda ser nombre de la BBDD + 
usuario de la BBDD, se tendrá que añadir a parte.

#### 2 · Arrancar el proyecto

Para ello, tendremos que arrancar el punto de entrada de nuestro proyecto, el cual 
encontraremos en el metodo ``public static void main()`` que encontraremos en archivo ``src\main\java\com\tresee\backend\BackendApplication.java (windows)``
``src/main/java/com/tresee/backend/BackendApplication.java (Linux)`` 