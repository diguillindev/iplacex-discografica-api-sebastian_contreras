package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
/*inyección de dependencias de manera automática. Específicamente,
permite que Spring encuentre e inyecte un bean apropiado en el lugar donde está declarado.
Si encuentra un bean que coincide con el tipo o nombre, lo asigna automáticamente a la variable artistarepo.*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/*habilitar CORS para un controlador específico, @CrossOriginpuede agregarlo en el nivel de clase del controlador.*/
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
/*se utiliza para asignar solicitudes web a métodos de Spring Controller.
Spring sirve contenido en la ruta de contexto raíz (“/”) de manera predeterminada.*/


import org.springframework.web.bind.annotation.RestController;
/*versión especializada del controlador. Incluye las anotaciones @Controller y @ResponseBody y,
como resultado, simplifica la implementación del controlador: Cada método de manejo de solicitudes
de la clase controladora serializa automáticamente los objetos de retorno en HttpResponse .*/


@RestController
@RequestMapping("/api")
@CrossOrigin

public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;
    //mapeo hacia peticion post
    // Maneja solicitudes POST hacia "/artista" en formato JSON. 
    // Consume y produce datos en formato JSON para insertar un objeto Artista. 
    // Utiliza ResponseEntity para devolver el objeto insertado con el estado HTTP 201 (CREATED). 
    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE 
    )
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista temp = artistaRepo.insert(artista);
        return new ResponseEntity<>(temp, null, HttpStatus.CREATED); 

    }

    // Maneja solicitudes GET hacia "/artistas", devolviendo datos en formato JSON. 
    // Recupera todos los objetos Artista de la base de datos. 
    // Devuelve la lista de artistas con el estado HTTP 200 (OK). 
    @GetMapping(
        value = "/artistas",
        produces =MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest(){
        List<Artista> artistas = artistaRepo.findAll();
        return new ResponseEntity<>(artistas, null, HttpStatus.OK);
    }

    // Maneja solicitudes GET hacia "/artistas/{id}", devolviendo datos en formato JSON. 
    // Busca un objeto Artista por su ID en la base de datos. 
    // Devuelve el Artista encontrado con estado HTTP 200 (OK) o 404 (NOT FOUND) si no existe. 
    @SuppressWarnings("null")
    @GetMapping(
        value = "/artistas/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleGetArtistasRequest(@PathVariable("id") String id){
        Optional<Artista> temp = artistaRepo.findById(id);

        if (!temp.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); 
        }

        return new ResponseEntity<>(temp.get(), null, HttpStatus.OK);
    }

    // Maneja solicitudes PUT hacia "/artista/{id}", consumiendo y produciendo datos en formato JSON. 
    // Actualiza un objeto Artista identificado por su ID si existe en la base de datos. 
    // Devuelve el Artista actualizado con estado HTTP 200 (OK) o 404 (NOT FOUND) si no se encuentra el ID. 
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
        @PathVariable("id") String id,
        @RequestBody Artista artista     
    ) {
        if(!artistaRepo.existsById(id)){
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        artista._id = id; 
        Artista temp = artistaRepo.save(artista);

        return new ResponseEntity<>(temp, null, HttpStatus.OK ); 
    }

    // Maneja solicitudes DELETE hacia "/artista/{id}". 
    // Elimina un objeto Artista identificado por su ID si existe en la base de datos. 
    // Devuelve el Artista eliminado con estado HTTP 200 (OK) o 404 (NOT FOUND) si no se encuentra el ID. 
    @SuppressWarnings("null")
    @DeleteMapping(        
        value = "/artista/{id}"
    )
    public ResponseEntity<Artista> HandleDeleteArtistaRequest(@PathVariable("id")String id) {
        if (!artistaRepo.existsById(id)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Artista temp = artistaRepo.findById(id).get();
        artistaRepo.deleteById(id);

        return new ResponseEntity<>(temp, null, HttpStatus.OK); //tmb puede ser httpStatus.Gone 

    }
}
