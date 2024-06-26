package br.com.fiap.concessionaria.dto.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record LojaResponse(

        String nome,

        Set<VeiculoResponse> veiculosComercializados,

        Long id
) {
}
