# CodeInsights-Metrics

# Manual :
> Insight Metrics es una herramienta que busca ayudar a los desarrolladores a comprender mejor su código, por medio de métricas y visualización de flujo que permitan análizar el costo computacional de sus funciones, legibilidad del código, buenas prácticas, entre otras funcionalidades.

El usuario tiene en su poder la InsightMetrics/v1. En esta versión beta se implemento una interfaz sencilla donde se disponen diferentes métricas que se calculan al recorrer el árbol sintáctico.

### Métricas que cálcula el programa
> - Cantidad de lineas de código
> - Cantidad de funciones
> - Cantidad de condicionales
> - Cantidad de ciclos for
> - Cantidad de ciclos while
> - Cantidad de variables globales
> - Métricas de Halsted
> - Complejidad Ciclomática
> - Variables no usadas
> - Visualización del diagrama de flujo del programa
> - Visualización del diagrama de herencia del programa
> - Dependencias usadas
> - Duplicidad del código

### Herramientas Usadas
> - ANTLR como principal herramienta para la lectura y procesamiento del código a analizar
> - Interfaz realizada con javaFX y Scene Builder
> - Para los grafos se hizo uso de la librería GraphStream


> [!IMPORTANT]  
> Las funcionalidades de InsightMetrics/v1 están soportadas para Python, algunas de ellas también en Java.

---


## Manual de Compilación y  Uso
1. Una vez descargado el codigo fuente, se debe hacer la insercion de distintas librerias usadas para el funcionamiento del mismo.
   2. Antlr4
   3. Apache-commons
   4. Graphstream
      5. gs-algo 2.0
      6. gs-core 2.0
      7. gs-ui.javafx 2.0
\
Para la insercion de estas se puede hacer ingresando a "modul settings" del proyecto y agregando las dependencias .jar.
      8. javafx 21
\
En este caso se deben descargar los archivos correpondientes a la libreria y agregarlos en el apartado de "module setting" librerias. Así mismo, se sugiere que en las configuraciones de ejecucion se agregue la opcion de VM
         --module-path "PATH-TO-LIBRARY" --add-modules javafx.fxml,javafx.controls,javafx.graphics
2. Ejecute la aplicacion desde el main de javaFx.
3. Una vez en la interfaz, copie el código que desea analizar en el panel del lado izquierdo de la ventana inicial.
2. Seleccione el lenguaje en que está escrito su programa. De click en Analizar.
3. Encontrará una ventana con diferentes métricas. A partir de este punto puede realizar dos búsquedas extra.
   1. Visualizar grafo de clases del codigo fuente ingresado
   2. Ingresar a estadisticas de funciones personalizadas.
      1. En este apartado encontrara distintas metricas orientada a cada una de las funciones y se podra visualizar un grafo con el flujo de ejecucion de la funcion.


# Desarrolladores

- Juan Sebastian Sarmiento
- Venus Baquero
- Juan Carlos Prieto
