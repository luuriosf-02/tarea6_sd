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

        return repository.findAll();
    }
    
    public Producto crear(Producto p){

        return repository.save(p);
    }
    public Producto getProductoPorId(Long id) {
        return repository.findById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

}