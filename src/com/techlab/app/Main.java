package com.techlab.app;

import com.techlab.models.Producto;
import com.techlab.utils.JsonUtils;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.Normalizer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);
  //private static ArrayList<Map<String, String>> productosDB = new ArrayList<>();
  private static ArrayList<Producto> productosDB = new ArrayList<>();


  public static void main(String[] args) {
    boolean salir = false;
    // ArrayList<String> productosDB = new ArrayList<>();
    // ArrayList<String> productosDB = cargarProductosIniciales();
    // productosDB = cargarProductosIniciales();
    productosDB = cargarProductosDesdeJSON();

    while (!salir) {
      mostrarMenuPrincipal();
      System.out.print("Seleccione una opción: ");
      int opcion = leerEntero();

      switch (opcion) {
        case 1 -> menuProductos();
        case 2 -> menuPedidos();
        case 3 -> {
          System.out.println("\nSaliendo del sistema...");
          salir = true;
        }
        default -> System.out.println("\n⚠️  Opción inválida. Intente nuevamente.\n");
      }
    }

    scanner.close();
  }

  private static void mostrarMenuPrincipal() {
    System.out.println("\n==============================");
    System.out.println("     MENÚ PRINCIPAL - APP     ");
    System.out.println("==============================");
    System.out.println("1. Gestionar Productos");
    System.out.println("2. Gestionar Pedidos");
    System.out.println("3. Salir");
    System.out.println("==============================");
  }

  private static void menuProductos() {
    boolean volver = false;

    while (!volver) {
      System.out.println("\n----- GESTIÓN DE PRODUCTOS -----");
      System.out.println("1. Listar productos");
      System.out.println("2. Agregar producto");
      System.out.println("3. Buscar producto por nombre");
      System.out.println("4. Buscar producto por ID");
      System.out.println("5. Actualizar producto");
      System.out.println("6. Eliminar producto");
      System.out.println("7. Volver al menú principal");
      System.out.print("Seleccione una opción: ");

      int opcion = leerEntero();

      switch (opcion) {
        case 1 -> {
          System.out.println("\n[Simulación] Listando productos...");
          listarProductos(productosDB);
          pausa();
        }
        case 2 -> {
          crearProducto(productosDB);
        }
        case 3 -> {
          ArrayList<Producto> productosEncontrados = buscarProductoPorNombre(productosDB);
          listarProductos(productosEncontrados);
          pausa();
        }
        case 4 -> {
          Producto productoEncontrado = buscarProductoPorId(productosDB);
          ArrayList<Producto> productosEncontrados = new ArrayList<>();
          if (productoEncontrado != null){
            productosEncontrados.add(productoEncontrado);
          }
          listarProductos(productosEncontrados);
          pausa();
        }
        case 5 -> {
          System.out.println("\n[Simulación] Actualizando producto...");
          actualizarProducto(productosDB);
        }
        case 6 -> {
          System.out.println("\n[Simulación] Eliminando producto...");
          eliminarProducto(productosDB);
        }
        case 7 -> volver = true;
        default -> System.out.println("⚠️  Opción inválida. Intente nuevamente.");
      }
    }
  }

  private static void menuPedidos() {
    boolean volver = false;

    while (!volver) {
      System.out.println("\n----- GESTIÓN DE PEDIDOS -----");
      System.out.println("1. Crear nuevo pedido");
      System.out.println("2. Listar pedidos existentes");
      System.out.println("3. Volver al menú principal");
      System.out.print("Seleccione una opción: ");

      int opcion = leerEntero();

      switch (opcion) {
        case 1 -> System.out.println("\n[Simulación] Creando nuevo pedido...");
        case 2 -> System.out.println("\n[Simulación] Listando pedidos...");
        case 3 -> volver = true;
        default -> System.out.println("⚠️  Opción inválida. Intente nuevamente.");
      }
    }
  }

  private static int leerEntero() {
    while (true) {
      try {
        return Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.print("Ingrese un número válido: ");
      }
    }
  }

  public static ArrayList<Producto> cargarProductosDesdeJSON() {
    ArrayList<Producto> productos = new ArrayList<>();

    try (FileReader reader = new FileReader("src/data/data.json")) {
      Gson gson = new Gson();
      Type tipoLista = new TypeToken<ArrayList<Map<String, Object>>>() {}.getType();

      ArrayList<Map<String, Object>> listaJSON = gson.fromJson(reader, tipoLista);

      for (Map<String, Object> prod : listaJSON) {
        // Convertir id (puede venir como Double o String)
        Object idObj = prod.get("id");
        int id = (idObj instanceof Number)
            ? ((Number) idObj).intValue()
            : Integer.parseInt(idObj.toString());

        String nombre = prod.get("nombre").toString();
        String descripcion = prod.get("descripcion").toString();

        double precio = JsonUtils.parseDouble(prod.get("precio"), 0.0);
        int stock = JsonUtils.parseInt(prod.get("stock"), 0);

        Producto p = new Producto(id, nombre, descripcion, stock, precio);
        productos.add(p);
      }

    } catch (IOException e) {
      System.out.println("⚠️ Error al leer el archivo JSON: " + e.getMessage());
    }

    return productos;
  }

  public static void crearProducto(ArrayList<Producto> productos) {

    // Determinar el ID más alto actual
    int maxId = 0;
    for (Producto p : productos) {
      if (p.getId() > maxId) {
        maxId = p.getId();
      }
    }
    int nuevoId = maxId + 1;

    System.out.print("Ingrese el nombre del producto: ");
    String nombre = obtenerEntrada();

    System.out.print("Ingrese la descripción del producto: ");
    String descripcion = obtenerEntrada();

    // Leer stock (entero)
    int stock = 0;
    while (true) {
      System.out.print("Ingrese el stock disponible (entero): ");
      try {
        stock = Integer.parseInt(obtenerEntrada());
        if (stock < 0) {
          System.out.println("⚠️ El stock no puede ser negativo.");
          continue;
        }
        break;
      } catch (NumberFormatException e) {
        System.out.println("⚠️ Ingrese un número válido para el stock.");
      }
    }

    // Leer precio (float/double)
    double precio = 0.0;
    while (true) {
      System.out.print("Ingrese el precio del producto: ");
      try {
        precio = Double.parseDouble(obtenerEntrada());
        if (precio < 0) {
          System.out.println("⚠️ El precio no puede ser negativo.");
          continue;
        }
        break;
      } catch (NumberFormatException e) {
        System.out.println("⚠️ Ingrese un valor numérico válido para el precio.");
      }
    }

    // Crear el nuevo producto
    Producto nuevoProducto = new Producto(nuevoId, nombre, descripcion, stock, precio);
    productos.add(nuevoProducto);

    System.out.println("✅ Producto agregado con ID: " + nuevoId);
  }

  public static Producto buscarProductoPorId(ArrayList<Producto> productos){
    int idBuscado;
    Producto productoEncontrado = null;
    System.out.print("Ingrese el ID del producto a buscar: ");
    idBuscado = leerEntero();

    for (Producto p : productos) {
      if (p.getId() == idBuscado) {
        productoEncontrado = p;
        break;
      }
    }

    return productoEncontrado;
  }


  public static void actualizarProducto(ArrayList<Producto> productos) {
    Producto productoEncontrado = buscarProductoPorId(productos);

    if (productoEncontrado == null) {
      System.out.println("⚠️ No se encontró un producto con el ID ingresado");
      return;
    }

    System.out.println("Producto encontrado:");
    System.out.println("Nombre actual: " + productoEncontrado.getName());
    System.out.println("Descripción actual: " + productoEncontrado.getDescription());
    System.out.println("Stock actual: " + productoEncontrado.getStock());
    System.out.println("Precio actual: $" + String.format("%.2f", productoEncontrado.getPrice()));
    System.out.println("-----------------------------------------------------------------------------------------------------------------------------");


    System.out.print("Dejar en blanco los campos que NO desee modificar");
    System.out.print("Ingrese nuevo nombre: ");
    String nuevoNombre = obtenerEntrada();
    if (!nuevoNombre.isEmpty()) {
      productoEncontrado.setName(nuevoNombre);
    }

    System.out.print("Ingrese nueva descripción: ");
    String nuevaDescripcion = obtenerEntrada();
    if (!nuevaDescripcion.isEmpty()) {
      productoEncontrado.setDescription(nuevaDescripcion);
    }

    // Actualizar stock
    System.out.print("Ingrese nuevo stock: ");
    String stockStr = obtenerEntrada();
    if (!stockStr.isEmpty()) {
      try {
        int nuevoStock = Integer.parseInt(stockStr);
        if (nuevoStock >= 0) {
          productoEncontrado.setStock(nuevoStock);
        } else {
          System.out.println("⚠️ Stock no puede ser negativo. Se mantiene el anterior.");
        }
      } catch (NumberFormatException e) {
        System.out.println("⚠️ Stock inválido. Se mantiene el anterior.");
      }
    }

    // Actualizar precio
    System.out.print("Ingrese nuevo precio: ");
    String precioStr = obtenerEntrada();
    if (!precioStr.isEmpty()) {
      try {
        double nuevoPrecio = Double.parseDouble(precioStr);
        if (nuevoPrecio >= 0) {
          productoEncontrado.setPrice(nuevoPrecio);
        } else {
          System.out.println("⚠️ Precio no puede ser negativo. Se mantiene el anterior.");
        }
      } catch (NumberFormatException e) {
        System.out.println("⚠️ Precio inválido. Se mantiene el anterior.");
      }
    }

    System.out.println("✅ Producto actualizado correctamente.");
  }


  private static void eliminarProducto(ArrayList<Producto> productos) {
    System.out.print("Ingrese el ID del producto a eliminar: ");
    int idEliminar = leerEntero();

    boolean encontrado = false;

    Iterator<Producto> iterator = productos.iterator();
    while (iterator.hasNext()) {
      Producto producto = iterator.next();
      int id = producto.getId();
      if (id == idEliminar) {
        iterator.remove();
        encontrado = true;
        System.out.println("✅ Producto eliminado correctamente.");
        break;
      }
    }

    if (!encontrado) {
      System.out.println("⚠️ No se encontró un producto con ese ID.");
    }
  }


  public static void listarProductos(ArrayList<Producto> productos) {
    System.out.println("\n===============================");
    System.out.println("       LISTA DE PRODUCTOS      ");
    System.out.println("===============================");

    if (productos.isEmpty()) {
      System.out.println("⚠️  No hay productos cargados.");
    } else {
      System.out.printf("%-5s %-40s %-10s %-50s%n", "ID", "NOMBRE", "PRECIO", "DESCRIPCIÓN");
      System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
      for (Producto producto : productos) {
        int id = producto.getId();

        String nombre = producto.getName();
        String descripcion = producto.getDescription();
        int stock = producto.getStock();
        double precio = producto.getPrice();

        System.out.printf("%-5d %-40s $ %-8.2f %-60s%n", id, nombre, precio, descripcion);

      }
    }

    System.out.println("=============================================================================================================================");
    System.out.println("Total de productos: " + productos.size());
  }

  public static void pausa() {
    System.out.println("----------------------------------------");
    Scanner entrada = new Scanner(System.in);
    System.out.println();
    System.out.println("Presione ENTER para continuar...");
    entrada.nextLine();
    System.out.println("----------------------------------------");
  }

  public static String obtenerEntrada() {
    Scanner entrada = new Scanner(System.in);
    String texto = entrada.nextLine();
    return texto.trim();
  }

  public static ArrayList<Producto> buscarProductoPorNombre(ArrayList<Producto> productos) {
    ArrayList<Producto> productosEncontrados = new ArrayList<>();
    System.out.print("Ingrese nombre de producto a buscar: ");
    String busqueda = normalizar(obtenerEntrada());

    for (Producto producto : productos) {
      String nombre = normalizar(producto.getName());
      if (nombre.contains(busqueda)) {
        productosEncontrados.add(producto);
      }
    }

    return productosEncontrados;
  }



  /* UTILIDADES */
  public static String normalizar(String texto) {
    if (texto == null) return "";
    // Elimina espacios al inicio y al final
    texto = texto.trim().toLowerCase();

    // Quita tildes y caracteres especiales (á -> a, ñ -> n)
    texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
    texto = texto.replaceAll("\\p{M}", ""); // elimina marcas diacríticas

    return texto;
  }
}
