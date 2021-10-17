package org.bardales.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Entity(name = "Persona")
@Table(name = "persona", schema = "test")
@NamedQueries({
        @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p ORDER BY p.idPersona")
})
public class Persona implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    @SequenceGenerator(schema = "test", name = "SEQ_PERSONA", sequenceName = "seq_persona_id_persona", initialValue = 1, allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSONA")
    @Column(name = "id_persona")
    private int idPersona;

    @NonNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

}
