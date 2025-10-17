package py.una.pol.sd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(value = "/productos", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} )
    public ResponseEntity<List<Producto>> getproductos() 
	{
		
		List<Producto> r = productoservice.getProductos();

		return new ResponseEntity<>(r, HttpStatus.OK);
    }

     // 🔹 Muestra detalles de un producto específico por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producto> getProductoPorId(@PathVariable("id") Long id) {
        Producto p = productoservice.getProductoPorId(id);

        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 🔹 Permite buscar productos por nombre o palabra clave
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


		if(p != null && p.getid() != null) {
			System.out.println("Producto recepcionado "+ p.getNombre());
			
			productoservice.crear(p); 

			return new ResponseEntity<>("Se creó correctamente el producto: " + p.toString(), HttpStatus.OK);
		}else{

			System.out.println("Datos mal enviados");
			return new ResponseEntity<>("Debe enviar el campo id ", HttpStatus.BAD_REQUEST);
		}


        
    }



}

