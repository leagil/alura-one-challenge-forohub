package com.leagil.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarTopico(
    @NotBlank
    String titulo,
    @NotBlank
    String mensaje,
//    @NotNull
//    LocalDateTime fechaCreacion,
    @NotNull
    Long idUsuario,
    @NotNull
    Long idCurso){
}
