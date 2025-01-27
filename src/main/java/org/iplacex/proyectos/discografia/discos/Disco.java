package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//suprimir la advertencia agregando la anotación 
//@SuppressWarnings("unused") a la clase o a los campos específicos. 
//Esto le indica al IDE que ignores la advertencia para esos campos.
@Document("discos")
public class Disco {
    @Id
    public String _id;
    public String idArtista;
    public String nombre;
    public int anioLanzamiento;
    public List<String> canciones; 

}

