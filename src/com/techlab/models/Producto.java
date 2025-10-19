package com.techlab.models;

public class Producto {
  private int id;
  private String name;
  private String description;
  private int stock;
  private double price;

  // 🔹 Constructores completo
  public Producto(int id, String name, String description, int stock, double price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.stock = stock;
    this.price = price;
  }

  public Producto(int id, String name, String description) {
    this(id, name, description, 0, 0.0);
  }

  // Métodos
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public int getStock() { return stock; }
  public void setStock(int stock) { this.stock = stock; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }

  @Override
  public String toString() {
    return String.format("%-5d %-30s", id, name);
  }
}
