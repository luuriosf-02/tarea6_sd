package py.una.pol.sd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.una.pol.sd.model.Producto;
import py.una.pol.sd.repository.ProductoRepository;

@Service
public class ProductoService {


    @Autowired
    ProductoRepository repository;
  
    public List<Producto> getProductos(){

        return (List<Producto>) repository.findAll();
    }
    
    public Producto crear(Producto p){

        return repository.save(p);
    }
    public Producto getProductoPorId(Integer id) {
        return repository.findById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }
    public Producto actualizarProducto(Integer id, Producto datosNuevos) {
        Producto existente = repository.findById(id);
        if (existente != null) {
            existente.setNombre(datosNuevos.getNombre());
            existente.setStock(datosNuevos.getStock());
            return repository.save(existente);
        }
        return null;
    }

    public boolean eliminarProducto(Integer id) {
        Producto existente = repository.findById(id);
        if (existente != null) {
            repository.delete(existente);
            return true;
        }
        return false;
    }
}