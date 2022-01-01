package org.bardales.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Entity(name = "MascotaPersona")
@Table(name = "mascota_persona", schema = "test")
@NamedQueries({
        @NamedQuery(name = "MascotaPersona.findAll", query = "SELECT mp FROM MascotaPersona mp ORDER BY mp.mascota.idMascota, mp.mascota.guarderia.idGuarderia, mp.persona.idPersona"),
        @NamedQuery(name = "MascotaPersona.findByIdMascotaAndIdGuarderiaAndIdPersona", query = "SELECT mp FROM MascotaPersona mp WHERE mp.mascota.idMascota = :idMascota "
                + "and mp.mascota.guarderia.idGuarderia = :idGuarderia and mp.persona.idPersona = :idPersona ORDER BY mp.mascota.idMascota, mp.mascota.guarderia.idGuarderia, mp.persona.idPersona"),
        @NamedQuery(name = "MascotaPersona.findByPrecio", query = "SELECT mp FROM MascotaPersona mp WHERE mp.precio = :precio ORDER BY mp.mascota.idMascota, mp.mascota.guarderia.idGuarderia, mp.persona.idPersona")
})
@IdClass(MascotaPersonaId.class)
public class MascotaPersona {

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Column(name = "fecha_adopcion", nullable = false)
    private LocalDateTime fechaAdopcion;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    @Column(name = "precio", nullable = false, scale = 6, precision = 2)
    private BigDecimal precio;

    @Id
    @JoinColumns({
            @JoinColumn(name = "id_mascota", referencedColumnName = "id_mascota", nullable = false),
            @JoinColumn(name = "id_guarderia", referencedColumnName = "id_guarderia", nullable = false)
    })
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Mascota mascota;

    @Id
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Persona persona;

}
