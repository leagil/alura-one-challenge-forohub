package com.leagil.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuesta(String mensaje, LocalDateTime fechaCreacion, String solucion) {
}
