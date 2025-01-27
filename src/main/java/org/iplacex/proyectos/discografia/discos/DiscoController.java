package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiscoController {
    
    @Autowired
    private IDiscoRepository discoRepo; 

    @PostMapping(value = "/disco", consumes = "application/json", produces = "application/json")
    public Disco handlePostDiscoRequest(@RequestBody Disco disco) {
        return discoRepo.save(disco);
    }

    @GetMapping(value = "/discos", produces = "application/json")
    public List<Disco> handleGetDiscosRequest() {
        return discoRepo.findAll();
    }

    @GetMapping(value = "/disco/{id}", produces = "application/json")
    public Disco handleGetDiscoRequest(@PathVariable String id) {
        return discoRepo.findById(id).orElse(null);
    }

    @GetMapping(value = "/artista/{id}/discos", produces = "application/json")
    public List<Disco> handleGetDiscosByArtistaRequest(@PathVariable String id) {
        return discoRepo.findDiscosByIdArtista(id);
    }

}
