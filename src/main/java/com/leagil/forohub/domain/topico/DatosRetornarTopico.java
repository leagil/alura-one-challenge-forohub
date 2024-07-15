package com.leagil.forohub.domain.topico;

import java.time.LocalDateTime;


public record DatosRetornarTopico(String titulo, String mensaje, LocalDateTime fechaCreacion, Boolean status, Long usuarioId, Long cursoId) {
    public DatosRetornarTopico(Topico topico) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(), topico.getUsuario().getId(), topico.getCurso().getId());
    }
}
