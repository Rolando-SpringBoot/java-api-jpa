package org.bardales.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MascotaPersonaId {

    private MascotaId mascota;

    private Integer persona;

}
