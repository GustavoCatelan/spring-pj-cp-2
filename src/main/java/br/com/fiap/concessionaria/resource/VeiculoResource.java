package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.AbstractRequest;
import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.repository.AcessorioRepository;
import br.com.fiap.concessionaria.service.AcessorioService;
import br.com.fiap.concessionaria.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoResource implements ResourceDTO<Veiculo, VeiculoRequest, VeiculoResponse>{

    @Autowired
    private VeiculoService service;

    @Autowired
    private AcessorioRepository acessorioRepository;

    @GetMapping
    public ResponseEntity<Collection<VeiculoResponse>> findAll(
            @RequestParam(name = "fabricante.nome", required = false) String nome,
            @RequestParam(name = "fabricante.anoFabricacao", required = false) Year anoFabricacao,
            @RequestParam(name = "fabricante.cor", required = false) String cor,
            @RequestParam(name = "fabricante.preco", required = false) Double preco,
            @RequestParam(name = "fabricante.cilindradas", required = false) Short cilindradas,
            @RequestParam(name = "fabricante.modelo", required = false) String modelo,
            @RequestParam(name = "fabricante.palavraDeEfeito", required = false) String palavraDeEfeito,
            @RequestParam(name = "fabricante.fabricante", required = false) Fabricante fabricante,
            @RequestParam(name = "fabricante.tipo", required = false) TipoVeiculo tipo
            ) {

        Veiculo veiculo = Veiculo.builder()
                .nome(nome)
                .anoDeFabricacao(anoFabricacao)
                .cor(cor)
                .preco(preco)
                .cilindradas(cilindradas)
                .modelo(modelo)
                .palavraDeEfeito(palavraDeEfeito)
                .fabricante(fabricante)
                .tipo(tipo)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Veiculo> example = Example.of( veiculo, matcher );

        var encontrados = service.findAll( example );

        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();

        return ResponseEntity.ok( resposta );
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoResponse> findById(Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<VeiculoResponse> save(@RequestBody @Valid VeiculoRequest r) {
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

   @GetMapping(value = "{id}/acessorio")
   public ResponseEntity<List<VeiculoResponse>> findByAcessorioId(@PathVariable Long id) {
       var entity = service.findByAcessorioId( id );
       if (Objects.isNull( entity )) return ResponseEntity.notFound().build();
       var response = entity.stream().map( service::toResponse ).toList();
       return ResponseEntity.ok( response );
   }

    @Transactional
    @PostMapping(value = "{id}/acessorio")
    public VeiculoResponse save(@PathVariable Long id, @RequestBody @Valid AbstractRequest acessorio) {
        if (Objects.isNull(acessorio)) return null;
        Veiculo veiculo = service.findById(id);
        Acessorio acessorioEntity = null;
        if (Objects.nonNull(acessorio.id())) {
            acessorioEntity = acessorioRepository.findById(acessorio.id()).orElseThrow();
        }
        veiculo.getAcessorios().add(acessorioEntity);
        return service.toResponse(veiculo);
    }
}