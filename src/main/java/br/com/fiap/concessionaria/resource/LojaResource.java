package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.AbstractRequest;
import br.com.fiap.concessionaria.dto.request.CaracteristicaRequest;
import br.com.fiap.concessionaria.dto.request.LojaRequest;
import br.com.fiap.concessionaria.dto.response.CaracteristicaResponse;
import br.com.fiap.concessionaria.dto.response.LojaResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.entity.Caracteristica;
import br.com.fiap.concessionaria.entity.Loja;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.repository.VeiculoRepository;
import br.com.fiap.concessionaria.service.CaracteristicaService;
import br.com.fiap.concessionaria.service.LojaSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping(value = "/loja")
public class LojaResource implements ResourceDTO<Loja, LojaRequest, LojaResponse> {

    @Autowired
    private LojaSevice service;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public ResponseEntity<Collection<LojaResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome
    ) {

        Loja loja = Loja.builder()
                .nome( nome )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Loja> example = Example.of( loja, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<LojaResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<LojaResponse> save(@RequestBody @Valid LojaRequest r) {
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }


    @Transactional
    @PostMapping(value = "/{id}/veiculos")
    public LojaResponse save(@PathVariable Long id, @RequestBody @Valid VeiculoResponse veiculos) {
        if (Objects.isNull(veiculos)) return null;
        Loja loja = service.findById(id);
        Veiculo veiculoEntity = null;
        if (Objects.nonNull(veiculos.id())) {
            veiculoEntity = veiculoRepository.findById(veiculos.id()).orElseThrow();
        }
        loja.getVeiculosComercializados().add(veiculoEntity);
        return service.toResponse(loja);
    }
}
