package com.leagil.forohub.controller;

import com.leagil.forohub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacion){
        return service.listarTopicos(paginacion);
    }

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistrarTopico datosRegistrarTopico, UriComponentsBuilder uriComponentsBuilder){
        var response = service.registrarTopico(datosRegistrarTopico, uriComponentsBuilder);
        return response;
    }

    @GetMapping("/{id:\\d+}")  //the {id:\\d+} part of the @GetMapping annotation defines the path variable id and enforces it to only accept digits. The \\d+ regex pattern ensures that only one or more digits are allowed.
    public ResponseEntity<DatosRetornarTopico> retornarTopico(@PathVariable("id") Long id) {
        return service.retornarTopico(id);
    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public ResponseEntity<DatosRetornarTopico> actualizarTopico(@PathVariable("id") Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        return service.actualizarTopico(id, datos);
    }

    @DeleteMapping("/{id:\\d+}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable("id") Long id) {
        return service.eliminarTopico(id);
    }

}
