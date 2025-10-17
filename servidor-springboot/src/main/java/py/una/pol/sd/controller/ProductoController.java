package py.una.pol.sd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.una.pol.sd.model.Producto;
import py.una.pol.sd.service.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	ProductoService productoservice;

	@GetMapping("/saludo")
	public String index() {
		return "Hola mundo caluroso de Springboot";
	}
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Producto>> getproductos() 
	{
		List<Producto> r = productoservice.getProductos();
		return ResponseEntity.ok(r);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producto> getProductoPorId(@PathVariable("id") Integer id) {
        Producto p = productoservice.getProductoPorId(id);

        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Permite buscar productos por nombre
    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam("nombre") String nombre) {
        List<Producto> lista = productoservice.buscarPorNombre(nombre);

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }
	@PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Producto p) {
		if(p != null && p.getId() != null) {
			System.out.println("Producto recepcionado "+ p.getNombre());
			
			productoservice.crear(p); 

			return new ResponseEntity<>("Se creó correctamente el producto: " + p.toString(), HttpStatus.OK);
		}else{
			System.out.println("Datos mal enviados");
			return new ResponseEntity<>("Debe enviar el campo id ", HttpStatus.BAD_REQUEST);
		}
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> agregarProducto(@RequestBody Producto nuevoProducto) {
		try {
			productoservice.crear(nuevoProducto);
			return new ResponseEntity<>("Producto agregado exitosamente: " + nuevoProducto.getNombre(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al agregar el producto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// Actualizar Producto
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizarProducto(@PathVariable("id") Integer id, @RequestBody Producto productoActualizado) {
		try {
			Producto actualizado = productoservice.actualizarProducto(id, productoActualizado);
			if (actualizado!=null) {
				return new ResponseEntity<>("Producto actualizado correctamente.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Producto no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error al actualizar el producto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    //Eliminar producto
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> eliminarProducto(@PathVariable("id") Integer id) {
		try {
			boolean eliminado = productoservice.eliminarProducto(id);

			if (eliminado) {
				return new ResponseEntity<>("Producto eliminado correctamente.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró un producto con ID: " + id, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error al eliminar el producto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}


    }
}

