package com.leagil.forohub.domain.topico;

import com.leagil.forohub.domain.usuario.Usuario;
import com.leagil.forohub.domain.curso.Curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DatosNuevoTopico(
    @NotBlank
    String titulo,
    @NotBlank
    String mensaje,
//    @NotNull
//    LocalDateTime fechaCreacion,
    @NotNull
    Usuario Usuario,
    @NotNull
    Curso Curso){
}
