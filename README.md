# CodeInsights-Metrics

## Overview

**Insight Metrics** is a tool designed to help developers better understand their code through a variety of metrics and visualizations. It provides insights into the computational cost of functions, code readability, best practices, and other aspects of code quality.

The current version, **InsightMetrics/v1**, is a beta release featuring a simple interface that provides different metrics calculated by traversing the program's syntax tree.

### Metrics

The application provides the following metrics and visualizations:

* Lines of code
* Number of functions
* Number of conditional statements
* Number of `for` loops
* Number of `while` loops
* Number of global variables
* Halstead metrics
* Cyclomatic complexity
* Unused variables
* Program flow diagram visualization
* Class inheritance diagram visualization
* Dependencies used
* Code duplication

### Technologies

* **ANTLR** — Used as the main tool for reading and processing the source code being analyzed.
* **JavaFX & Scene Builder** — Used to build the graphical user interface.
* **GraphStream** — Used for graph generation and visualization.

> [!IMPORTANT]
> The functionalities in **InsightMetrics/v1** are primarily supported for **Python**, with some features also supporting **Java**.

---

## Build and Usage

### Requirements

Before running the application, the following libraries must be added to the project:

* ANTLR4
* Apache Commons
* GraphStream:

  * `gs-algo 2.0`
  * `gs-core 2.0`
  * `gs-ui-javafx 2.0`
* JavaFX 21

### Setup

1. Download the source code and add the required `.jar` libraries through the project's **Module Settings**.

2. For JavaFX, download the corresponding library files and add them under the project's libraries in **Module Settings**.

3. It is recommended to add the following VM options to the run configuration:

   ```text
   --module-path "PATH-TO-LIBRARY" --add-modules javafx.fxml,javafx.controls,javafx.graphics
   ```

4. Run the application from the JavaFX `main` method.

### Using the Application

1. Once the application starts, paste the source code you want to analyze into the panel on the left side of the main window.

2. Select the programming language in which the source code is written.

3. Click **Analyze**.

4. The application will display a window containing the different metrics calculated from the source code.

5. From the metrics window, you can access two additional features:

   * **Class diagram:** Visualize the class and inheritance structure of the analyzed source code.
   * **Function statistics:** View detailed metrics for individual functions and visualize their execution flow through a graph.

## Developers

* Juan Sebastian Sarmiento
* Venus Baquero
* Juan Carlos Prieto
