package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Loja;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VeiculoService implements ServiceDTO<Veiculo, VeiculoRequest, VeiculoResponse>{

    @Autowired
    private VeiculoRepository repo;

    @Autowired
    private FabricanteService fabricanteService;

    @Autowired
    private TipoVeiculoService tipoVeiculoService;

    @Autowired
    private AcessorioService acessorioService;

    @Override
    public Veiculo toEntity(VeiculoRequest v) {
        return Veiculo.builder()
                .nome( v.nome() )
                .cor(v.cor())
                .preco(v.preco())
                .cilindradas(v.cilindradas())
                .modelo(v.modelo())
                .palavraDeEfeito(v.palavraDeEfeito())
                .build();
    }
    @Override
    public VeiculoResponse toResponse(Veiculo e) {

        return VeiculoResponse.builder()
                .id( e.getId() )
                .nome(e.getNome())
                .cor(e.getCor())
                .preco(e.getPreco())
                .cilindradas(e.getCilindradas())
                .modelo(e.getModelo())
                .palvaraDeEfeito(e.getPalavraDeEfeito())
                .fabricante(fabricanteService.toResponse( e.getFabricante() ))
                .tipo(tipoVeiculoService.toResponse( e.getTipo() ))
                .build();
    }
    @Override
    public Collection<Veiculo> findAll(Example<Veiculo> example) {
        return repo.findAll( example );
    }
    @Override
    public Veiculo findById(Long id) {
        return repo.findById( id ).orElse( null );
    }
    @Override
    public Veiculo save(Veiculo e) {
        return repo.save( e );
    }

}
