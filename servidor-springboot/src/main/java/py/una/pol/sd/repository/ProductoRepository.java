package py.una.pol.sd.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import py.una.pol.sd.model.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    List<Producto> findAll();

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    Producto findById(Integer id);
}