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
@Entity(name = "Guarderia")
@Table(name = "guarderia", schema = "test")
@NamedQueries({
        @NamedQuery(name = "Guarderia.findAll", query = "SELECT g FROM Guarderia g ORDER BY g.idGuarderia"),
        @NamedQuery(name = "Guarderia.findByIdGuarderia", query = "SELECT g FROM Guarderia g WHERE g.idGuarderia = :idGuarderia ORDER BY g.idGuarderia"),
        @NamedQuery(name = "Guarderia.findByDesGuarderia", query = "SELECT g FROM Guarderia g WHERE g.desGuarderia = :desGuarderia ORDER BY g.idGuarderia")
})
public class Guarderia {

    @Id
    @SequenceGenerator(schema = "test", name = "SEQ_GUARDERIA", sequenceName = "seq_guarderia_id_guarderia", initialValue = 1, allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUARDERIA")
    @Column(name = "id_guarderia")
    private Integer idGuarderia;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotEmpty
    @Size(max = 45)
    @Column(name = "des_guarderia", length = 45, nullable = false)
    private String desGuarderia;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "guarderia", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Mascota> mascotaList;

}
