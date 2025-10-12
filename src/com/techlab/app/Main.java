package com.techlab.app;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.Normalizer;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);
  private static ArrayList<String> productosDB = new ArrayList<>();


  public static void main(String[] args) {
    boolean salir = false;
    // ArrayList<String> productosDB = new ArrayList<>();
    // ArrayList<String> productosDB = cargarProductosIniciales();
    productosDB = cargarProductosIniciales();

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
      System.out.println("4. Eliminar producto");
      System.out.println("5. Volver al menú principal");
      System.out.print("Seleccione una opción: ");

      int opcion = leerEntero();

      switch (opcion) {
        case 1 -> {
          System.out.println("\n[Simulación] Listando productos...");
          listarProductos(productosDB);
        }
        case 2 -> {
          System.out.println("\n[Simulación] Agregando producto...");
          crearProducto(productosDB);
        }
        case 3 -> {
          System.out.println("\n[Simulación] Buscando producto...");
          ArrayList<String> productosEncontrados = buscarProductoPorNombre(productosDB);
          listarProductos(productosEncontrados);
        }
        case 4 -> System.out.println("\n[Simulación] Eliminando producto...");
        case 5 -> volver = true;
        default -> System.out.println("⚠️  Opción inválida. Intente nuevamente.");
      }
      pausa();
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

  public static void crearProducto(ArrayList<String> productos){
    System.out.println("Nuevo Producto");
    System.out.println("Ingrese el nuevo producto: ");
    String nombre = obtenerEntrada();

    productos.add(nombre);
  }

  public static void listarProductos(ArrayList<String> productos) {
    System.out.println("\n===============================");
    System.out.println("       LISTA DE PRODUCTOS      ");
    System.out.println("===============================");

    if (productos.isEmpty()) {
      System.out.println("⚠️  No hay productos cargados.");
    } else {
      for (int i = 0; i < productos.size(); i++) {
        System.out.printf("%2d) %-40s%n", (i + 1), productos.get(i));
      }
    }

    System.out.println("===============================");
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

  public static ArrayList<String> buscarProductoPorNombre(ArrayList<String> productos){
    ArrayList<String> productosEncontrados = new ArrayList<>();
    System.out.println("Ingrese nombre de producto: ");
    String busqueda = normalizar(obtenerEntrada());

    for (String producto : productos){
      if (normalizar(producto).contains(busqueda.trim().toLowerCase())){
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
