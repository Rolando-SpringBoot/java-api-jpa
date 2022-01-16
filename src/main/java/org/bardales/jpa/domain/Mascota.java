package org.bardales.jpa.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
@Entity(name = "Mascota")
@Table(name = "mascota", schema = "test")
@NamedQueries({
        @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m ORDER BY m.idMascota, m.guarderia.idGuarderia"),
        @NamedQuery(name = "Mascota.findByIdMascotaAndIdGuarderia", query = "SELECT m FROM Mascota m WHERE m.idMascota = :idMascota and m.guarderia.idGuarderia = :idGuarderia ORDER BY m.idMascota, m.guarderia.idGuarderia"),
        @NamedQuery(name = "Mascota.findByNombre", query = "SELECT m FROM Mascota m WHERE m.nombre = :nombre ORDER BY m.idMascota, m.guarderia.idGuarderia")
})
@IdClass(MascotaId.class)
public class Mascota {

    @Id
    @SequenceGenerator(schema = "test", name = "SEQ_MASCOTA", sequenceName = "seq_mascota_id_mascota", initialValue = 1, allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MASCOTA")
    @Column(name = "id_mascota")
    private Integer idMascota;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotEmpty
    @Size(max = 45)
    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;

    @Id
    @JoinColumn(name = "id_guarderia", referencedColumnName = "id_guarderia", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Guarderia guarderia;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "mascota", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<MascotaPersona> mascotaPersonaList;

}
