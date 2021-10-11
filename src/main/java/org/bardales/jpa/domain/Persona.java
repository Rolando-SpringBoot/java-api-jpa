package org.bardales.jpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Persona")
@Table(name = "persona", schema = "test")
public class Persona implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private int idPersona;

    @NonNull
    @Column(name = "nombre")
    private String nombre;

    @NonNull
    @Column(name = "apellido")
    private String apellido;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "telefono")
    private String telefono;

}
