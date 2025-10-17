package py.una.pol.sd.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producto {
    @Id
    private Integer id;
  private String nombre;
  private Integer stock;

  protected Producto() {}

  public Producto(Integer id, String nombre, Integer stock) {
    this.id = id;
    this.nombre = nombre;
    this.stock = stock;
  }

  @Override
  public String toString() {
    return String.format(
        "Producto[id=%d, nombre='%s', stock='%d']",
        id, nombre, stock);
  }

public Integer getid() {
    return id;
}

public String getNombre() {
    return nombre;
}

public Integer getstock() {
    return stock;
}

public void setid(Integer id) {
    this.id = id;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public void setstock(Integer stock) {
    this.stock = stock;
}
}
