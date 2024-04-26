package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.CaracteristicaRequest;
import br.com.fiap.concessionaria.dto.request.LojaRequest;
import br.com.fiap.concessionaria.dto.response.CaracteristicaResponse;
import br.com.fiap.concessionaria.dto.response.LojaResponse;
import br.com.fiap.concessionaria.entity.Caracteristica;
import br.com.fiap.concessionaria.entity.Loja;
import br.com.fiap.concessionaria.repository.CaracteristicaRepository;
import br.com.fiap.concessionaria.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CaracteristicaService implements ServiceDTO<Caracteristica, CaracteristicaRequest, CaracteristicaResponse>{

    @Autowired
    private CaracteristicaRepository repo;

    @Autowired
    private VeiculoService veiculoService;
    @Override
    public Caracteristica toEntity(CaracteristicaRequest r) {

        var veiculo = veiculoService.findById( r.veiculo().id() );

        return Caracteristica.builder()
                .nome( r.nome() )
                .descricao(r.descricao())
                .veiculo(veiculo)
                .build();
    }
    @Override
    public CaracteristicaResponse toResponse(Caracteristica e) {
        return CaracteristicaResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .descricao(e.getDescricao())
                .veiculo(veiculoService.toResponse( e.getVeiculo() ))
                .build();
    }
    @Override
    public Collection<Caracteristica> findAll(Example<Caracteristica> example) {
        return repo.findAll( example );
    }
    @Override
    public Caracteristica findById(Long id) {
        return repo.findById( id ).orElse( null );
    }
    @Override
    public Caracteristica save(Caracteristica e) {
        return repo.save( e );
    }
}
