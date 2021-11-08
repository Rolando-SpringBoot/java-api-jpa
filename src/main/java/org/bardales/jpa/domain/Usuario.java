package org.bardales.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Entity(name = "Usuario")
@Table(name = "usuario", schema = "test")
@NamedQueries({
        @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u ORDER BY u.idUsuario"),
        @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario ORDER BY u.idUsuario"),
        @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username ORDER BY u.idUsuario"),
        @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password ORDER BY u.idUsuario")
})
public class Usuario {

    @Id
    @SequenceGenerator(schema = "test", name = "SEQ_USUARIO", sequenceName = "seq_usuario_id_usuario", initialValue = 1, allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Size(max = 45)
    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Size(max = 45)
    @Column(name = "password", length = 45, nullable = false)
    private String password;

    /*
        @JoinColumn: similar a @Column, solo que este se ha de colocar en la columna que vendra como foranea

        Fetch:
            EAGER (ansioso): Indica que la relación debe de ser cargada al momento de cargar la entidad.
            LAZY (perezoso): Indica que la relación solo se cargará cuando la propiedad sea leída por primera vez.

        Buenas practicas:
         - debe existir una relacion bidireccional, es decir un @ManyToOne y un @OneToMany en otra entidad
     */

    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Persona persona;

}
