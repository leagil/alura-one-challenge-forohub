package com.leagil.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
    @NotBlank
    String titulo,
    @NotBlank
    String mensaje,
    @NotNull
    Long idUsuario,
    @NotNull
    Long idCurso){
}