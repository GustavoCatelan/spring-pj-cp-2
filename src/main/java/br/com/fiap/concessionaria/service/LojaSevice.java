package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.LojaRequest;
import br.com.fiap.concessionaria.dto.response.LojaResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Loja;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LojaSevice implements ServiceDTO<Loja, LojaRequest, LojaResponse>{

    @Autowired
    private LojaRepository repo;

    @Autowired
    private VeiculoService veiculoService;

    @Override
    public Loja toEntity(LojaRequest r) {

        return Loja.builder()
                .nome( r.nome() )
                .build();
    }
    @Override
    public LojaResponse toResponse(Loja e) {

        Set<VeiculoResponse> veiculoResponses = e.getVeiculosComercializados()
                .stream()
                .map(veiculoService::toResponse)
                .collect(Collectors.toSet());


        return LojaResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .veiculosComercializados( veiculoResponses )
                .build();
    }
    @Override
    public Collection<Loja> findAll(Example<Loja> example) {
        return repo.findAll( example );
    }
    @Override
    public Loja findById(Long id) {
        return repo.findById( id ).orElse( null );
    }
    @Override
    public Loja save(Loja e) {
        return repo.save( e );
    }

}
