package org.bardales.jpa.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Entity(name = "Direccion")
@Table(name = "direccion", schema = "test")
@NamedQueries({
        @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d ORDER BY d.idDireccion"),
        @NamedQuery(name = "Direccion.findByIdDireccion", query = "SELECT d FROM Direccion d WHERE d.idDireccion = :idDireccion ORDER BY d.idDireccion"),
        @NamedQuery(name = "Direccion.findByDesDistrito", query = "SELECT d FROM Direccion d WHERE d.desDistrito = :desDistrito ORDER BY d.idDireccion"),
        @NamedQuery(name = "Direccion.findByDesCalle", query = "SELECT d FROM Direccion d WHERE d.desCalle = :desCalle ORDER BY d.idDireccion")
})
public class Direccion {

    @Id
    @SequenceGenerator(schema = "test", name = "SEQ_DIRECCION", sequenceName = "sq_direccion_id_direccion", initialValue = 1, allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIRECCION")
    @Column(name = "id_direccion")
    private Integer idDireccion;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotEmpty
    @Size(max = 45)
    @Column(name = "des_distrito", length = 45, nullable = false)
    private String desDistrito;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotEmpty
    @Size(max = 45)
    @Column(name = "des_calle", length = 45, nullable = false)
    private String desCalle;

    /*
        Buenas practicas:
        - en la relacion onetoone debe haber bidireccionalidad
    */

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "direccion")
    private Persona persona;

}
