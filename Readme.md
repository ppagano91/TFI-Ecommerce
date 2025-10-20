# Gestión de Productos y Pedidos en Java

## Descripción

Este proyecto es una aplicación de consola desarrollada en **Java**, cuyo objetivo es realizar el CRUD de productos. Utiliza **POO (Programación Orientada a Objetos)**, colecciones (`ArrayList`), manejo de excepciones y archivos **JSON** como fuente de datos.

El sistema permite registrar, listar, buscar producto por ID, buscar producto por nombre, actualizar y eliminar productos.

## Funcionalidades principales

- **Gestión de Productos**
    - Cargar productos desde un archivo JSON.
    - Listar productos con ID, nombre, descripción, stock y precio.
    - Agregar productos nuevos con ID automático, stock y precio.
    - Buscar productos por ID o por nombre
    - Actualizar productos existentes (nombre, descripción, stock, precio).
    - Eliminar productos por ID.


- **Gestión de Pedidos**
    - En proceso

## Estructura del proyecto

- `com.techlab.app` → Clase principal (`Main`) y menú de interacción.
- `com.techlab.modelos` → Clase `Producto` y posibles clases futuras.
- `data` → Archivo `data.json` con los productos iniciales.

## Tecnologías utilizadas

- Java
- [GSON](https://github.com/google/gson) para lectura de JSON
- Colecciones (`ArrayList`, `HashMap`)
- Programación Orientada a Objetos
- Manejo de excepciones (`try/catch`)
- Consola de interacción con el usuario