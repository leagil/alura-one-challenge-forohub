package com.leagil.forohub.domain.topico;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leagil.forohub.domain.curso.Curso;
import com.leagil.forohub.domain.respuesta.Respuesta;
import com.leagil.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String titulo;
    @Setter
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean status; // topico abierto (true) o cerrado (false)
    private Boolean borrado; // para implementar borrado logico
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Setter
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    @Setter
    private Curso curso;
    @JsonIgnore
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    public Topico(DatosNuevoTopico datosNuevoTopico){
        this.titulo = datosNuevoTopico.titulo();
        this.mensaje = datosNuevoTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = true; // topico abierto
        this.borrado = false; // topico no borrado logico
        this.usuario = datosNuevoTopico.Usuario();
        this.curso = datosNuevoTopico.Curso();
        this.respuestas = null;
    }

    public void borrarTopico() {
        this.borrado = true; // topico borrado logico
    }

    public void desactivarTopico() {
        this.status = false; // topico cerrado
    }

}
