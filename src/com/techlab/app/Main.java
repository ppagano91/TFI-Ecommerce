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
      System.out.println("3. Buscar producto");
      System.out.println("4. Actualizar producto");
      System.out.println("5. Eliminar producto");
      System.out.println("6. Volver al menú principal");
      System.out.print("Seleccione una opción: ");

      int opcion = leerEntero();

      switch (opcion) {
        case 1 -> {
          System.out.println("\n[Simulación] Listando productos...");
          listarProductos(productosDB);
          pausa();
        }
        case 2 -> {
          System.out.println("\n[Simulación] Agregando producto...");
          //crearProducto(productosDB);
        }
        case 3 -> {
          System.out.println("\n[Simulación] Buscando producto...");
          ArrayList<Producto> productosEncontrados = buscarProductoPorNombre(productosDB);
          listarProductos(productosEncontrados);
          pausa();
        }
        case 4 -> {
          System.out.println("\n[Simulación] Actualizando producto...");
          //actualizarProducto(productosDB);
        }
        case 5 -> {
          System.out.println("\n[Simulación] Eliminando producto...");
          //eliminarProducto(productosDB);
        }
        case 6 -> volver = true;
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

  public static void crearProducto(ArrayList<Map<String, String>> productos) {
    System.out.println("\n=== Nuevo Producto ===");
    System.out.print("Ingrese el nombre del producto: ");
    String nombre = obtenerEntrada();

    System.out.print("Ingrese la descripción del producto: ");
    String descripcion = obtenerEntrada();

    // Generar un ID automático (por ejemplo, el siguiente número)
    String id = String.valueOf(productos.size() + 1);

    // Crear el Map con los datos del producto
    Map<String, String> nuevoProducto = new HashMap<>();
    nuevoProducto.put("id", id);
    nuevoProducto.put("nombre", nombre);
    nuevoProducto.put("descripcion", descripcion);

    // Agregar a la lista
    productos.add(nuevoProducto);

    System.out.println("✅ Producto agregado con ID: " + id);
  }

  public static void actualizarProducto(ArrayList<Map<String, String>> productos) {
    System.out.print("Ingrese el ID del producto a actualizar: ");
    String idBuscado = obtenerEntrada().trim();

    boolean encontrado = false;

    for (Map<String, String> producto : productos) {
      if (producto.get("id").equals(idBuscado)) {
        System.out.println("Producto encontrado:");
        System.out.println("Nombre actual: " + producto.get("nombre"));
        System.out.println("Descripción actual: " + producto.get("descripcion"));

        System.out.print("Ingrese nuevo nombre (o Enter para mantener): ");
        String nuevoNombre = obtenerEntrada();
        if (!nuevoNombre.isEmpty()) {
          producto.put("nombre", nuevoNombre);
        }

        System.out.print("Ingrese nueva descripción (o Enter para mantener): ");
        String nuevaDescripcion = obtenerEntrada();
        if (!nuevaDescripcion.isEmpty()) {
          producto.put("descripcion", nuevaDescripcion);
        }

        System.out.println("✅ Producto actualizado correctamente.");
        encontrado = true;
        break;
      }
    }

    if (!encontrado) {
      System.out.println("⚠️ No se encontró un producto con ID " + idBuscado);
    }
  }

  private static void eliminarProducto(ArrayList<Map<String, String>> productosDB) {
    System.out.print("Ingrese el ID del producto a eliminar: ");
    int idEliminar = leerEntero();

    boolean encontrado = false;

    Iterator<Map<String, String>> iterator = productosDB.iterator();
    while (iterator.hasNext()) {
      Map<String, String> producto = iterator.next();
      int id = Integer.parseInt(producto.get("id"));
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

  public static ArrayList<String> cargarProductosIniciales() {
    ArrayList<String> productos = new ArrayList<>();

    productos.add("Notebook HP Pavilion 15");
    productos.add("Monitor Samsung 24'' Curvo");
    productos.add("Teclado Mecánico Redragon Kumara");
    productos.add("Mouse Logitech MX Master 3");
    productos.add("Auriculares Sony WH-1000XM4");
    productos.add("Impresora HP DeskJet Ink Advantage 2775");
    productos.add("Tablet Samsung Galaxy Tab S6 Lite");
    productos.add("Smartphone Motorola Edge 40");
    productos.add("SSD Kingston 1TB NVMe");
    productos.add("Parlante Bluetooth JBL Flip 6");

    return productos;
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
