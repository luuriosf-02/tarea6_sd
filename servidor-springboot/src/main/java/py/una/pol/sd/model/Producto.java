package py.una.pol.sd.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producto {
    @Id
    private Integer id;
  private String nombre;
  private Integer stock;
  private Integer precio;

  protected Producto() {}

  public Producto(Integer id, String nombre, Integer precio, Integer stock) {
    this.id = id;
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
  }

  @Override
  public String toString() {
    return String.format(
        "Producto[id=%d, nombre='%s', precio='%d', stock='%d']",
        id, nombre, precio, stock);
  }

public Integer getId() {
    return id;
}

public String getNombre() {
    return nombre;
}

public Integer getPrecio() {
    return precio;
}

public Integer getStock() {
    return stock;
}

public void setId(Integer id) {
    this.id = id;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public void setPrecio(Integer precio) {
    this.precio = precio;
}

public void setStock(Integer stock) {
    this.stock = stock;
}
}
