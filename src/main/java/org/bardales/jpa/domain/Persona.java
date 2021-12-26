package org.bardales.jpa.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Entity(name = "Persona")
@Table(name = "persona", schema = "test")
@NamedQueries({
        @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p ORDER BY p.idPersona"),
        @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona ORDER BY p.idPersona"),
        @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre ORDER BY p.idPersona"),
        @NamedQuery(name = "Persona.findByApellido", query = "SELECT p FROM Persona p WHERE p.apellido = :apellido ORDER BY p.idPersona"),
        @NamedQuery(name = "Persona.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email ORDER BY p.idPersona"),
        @NamedQuery(name = "Persona.findByTelefono", query = "SELECT p FROM Persona p WHERE p.telefono = :telefono ORDER BY p.idPersona")
})
public class Persona {

    @Id
    /*
        La anotacion @SequenceGenerator llama una secuencia ya creada en la base de dato
        allocationSize = N significa que vaya y busque el siguiente valor de la secuencia en la base de datos
        una vez cada N llamadas persistentes.
        Es decir, que no significar que aumentará de 10 en 10 con cada registro, sino que primero buscará el valor de la secuencia
        en la base de datos y luego aumentará el valor buscado en 1 por cada objeto persisitido, hasta cuando supere los 10 objetos,
        volver a consultar la base de datos el alor de la secuencia. En otras palabras, colocar un allocationSize = 1,
        provocará el costo que se consulte a la base de datos cada vez que se persista un objeto.
     */
    @SequenceGenerator(schema = "test", name = "SEQ_PERSONA", sequenceName = "seq_persona_id_persona", initialValue = 1, allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSONA")
    @Column(name = "id_persona")
    private Integer idPersona;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Size(max = 45)
    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Size(max = 45)
    @Column(name = "apellido", length = 45, nullable = false)
    private String apellido;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Size(max = 45)
    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String email;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Size(max = 45)
    @Column(name = "telefono", length = 45, nullable = false)
    private String telefono;

    /*
        CascadeType.PERSIST : significa que las operaciones de guardar () o persistir () se conectan en cascada a entidades relacionadas.
        CascadeType.MERGE : significa que las entidades relacionadas se fusionan cuando se fusiona la entidad propietaria.
        CascadeType.REFRESH : hace lo mismo para la operación refresh ().
        CascadeType.REMOVE : elimina todas las asociaciones de entidades relacionadas con esta configuración cuando se elimina la entidad propietaria.
        CascadeType.DETACH : separa todas las entidades relacionadas si se produce una " desconexión manual".
        CascadeType.ALL : es la abreviatura de todas las operaciones en cascada anteriores.

        Fetch:
            EAGER (ansioso): Indica que la relación debe de ser cargada al momento de cargar la entidad.
            LAZY (perezoso): Indica que la relación solo se cargará cuando la propiedad sea leída por primera vez.

        orphanRemoval: es un concepto de ORM, indica si el niño es huérfano, también debe eliminarse de la base de datos.
                       Un niño queda huérfano cuando no se puede acceder a él desde su padre

        Buenas practicas:
         - debe existir una relacion bidireccional, es decir un @OneToMany y un @ManyToOne en otra entidad
         - el atributo cascade debe estar la relacion @OneToMany y no en @ManyToOne
     */

    /*
     Por buenas practicas, no es bueno llamar a la lista que surge de la relacion OneToMany.
     Ademas la lista debe set Set, para evitar indices repetidos
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Usuario> usuarioList;

}
