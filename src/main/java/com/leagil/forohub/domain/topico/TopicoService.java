package com.leagil.forohub.domain.topico;

import com.leagil.forohub.domain.curso.CursoRepository;
import com.leagil.forohub.domain.usuario.UsuarioRepository;
import com.leagil.forohub.infra.errores.ValidacionDeIntegridad;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    public ResponseEntity<Page<DatosRespuestaTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacion){
        var page = topicoRepository.findByBorradoFalse(paginacion).map(DatosRespuestaTopico::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriBuilder){
        if(usuarioRepository.findById(datos.idUsuario()).isEmpty()){
            throw new ValidacionDeIntegridad("el id del usuario no fue encontrado");
        }
        if(cursoRepository.findById(datos.idCurso()).isEmpty()){
            throw new ValidacionDeIntegridad("el id del curso no fue encontrado");
        }
        var topicoRepetido = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());

        if( !topicoRepetido.isEmpty() ){
            throw new ValidacionDeIntegridad("topico duplicado: ya existe un topico con igual titulo y mensaje");
        }
        var usuario = usuarioRepository.findById(datos.idUsuario()).get();
        var curso = cursoRepository.findById(datos.idCurso()).get();
        var datosNuevoTopico = new DatosNuevoTopico(datos.titulo(), datos.mensaje(), usuario, curso);
        Topico topico = new Topico(datosNuevoTopico);
        topicoRepository.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    public ResponseEntity<DatosRetornarTopico> retornarTopico(Long id){
        var topico  = topicoRepository.getReferenceById(id);
        var datosRetornarTopico = new DatosRetornarTopico(topico);
        return ResponseEntity.ok(datosRetornarTopico);
    }

    public ResponseEntity<DatosRetornarTopico> actualizarTopico(@PathVariable("id") Long id, @RequestBody @Valid DatosActualizarTopico datos){
        Optional<Topico> opcionalTopico = topicoRepository.findById(id);
        if(opcionalTopico.isPresent()) {
            Topico topico = opcionalTopico.get();
            if (usuarioRepository.findById(datos.idUsuario()).isEmpty()) {
                throw new ValidacionDeIntegridad("el id del usuario que se desea guardar no fue encontrado");
            }
            if (cursoRepository.findById(datos.idCurso()).isEmpty()) {
                throw new ValidacionDeIntegridad("el id del curso que se desea guardar no fue encontrado");
            }
            var topicoRepetido = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
            if (!topicoRepetido.isEmpty()) {
                throw new ValidacionDeIntegridad("topico duplicado: ya existe un topico con igual titulo y mensaje a los que se desean guardar");
            }
            topico.setTitulo(datos.titulo());
            topico.setMensaje(datos.mensaje());
            topico.setUsuario(usuarioRepository.findById(datos.idUsuario()).get());
            topico.setCurso(cursoRepository.findById(datos.idCurso()).get());
            topicoRepository.save(topico);
            return ResponseEntity.ok().body(new DatosRetornarTopico(topico));
        } else {
            throw new ValidacionDeIntegridad("No existe un topico cuyo id sea el  ingresado como path parameter");
        }
    }

    public ResponseEntity eliminarTopico(@PathVariable("id") Long id){
        Optional<Topico> opcionalTopico = topicoRepository.findById(id);
        if(opcionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw new ValidacionDeIntegridad("No existe un topico cuyo id sea el  ingresado como path parameter");
        }
    }

}
