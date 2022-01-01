package org.bardales.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

    /*
      Tanto el metodo equals como hashCode, se ha de aplicar solo al atributo
      de la llave primaria y la llave padre si es que se hace referencia a una foranea en la tabla
     */
    @EqualsAndHashCode.Exclude
    @NonNull
    @NotEmpty
    @Size(max = 45)
    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotEmpty
    @Size(max = 45)
    @Column(name = "password", length = 45, nullable = false)
    private String password;

    /*
        @JoinColumn: indica que esta entidad es la propietaria de la relación
        (es decir: la tabla correspondiente tiene una columna con una clave externa a la tabla referenciada)

        Fetch:
            EAGER (ansioso): Indica que la relación debe de ser cargada al momento de cargar la entidad.
            LAZY (perezoso): Indica que la relación solo se cargará cuando la propiedad sea leída por primera vez.

        Buenas practicas:
         - debe existir una relacion bidireccional, es decir un @ManyToOne y un @OneToMany en otra entidad
     */

    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Persona persona;

}
