package org.bardales.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MascotaId {

    private Integer idMascota;

    private Integer guarderia;

}
