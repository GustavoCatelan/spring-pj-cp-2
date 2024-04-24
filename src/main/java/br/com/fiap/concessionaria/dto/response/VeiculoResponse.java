package br.com.fiap.concessionaria.dto.response;

import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import lombok.Builder;

import java.time.Year;
import java.util.Set;

@Builder
public record VeiculoResponse(

        String modelo,

        Year anoDeFabricacao,

        TipoVeiculoResponse tipo,

        String cor,

        FabricanteResponse fabricante,

        Double preco,

        String nome,

        String palvaraDeEfeito,

        Short cilindradas,

        Long id
) {
}
