package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;

public record TipoVeiculoRequest(

        @NotNull(message = "O nome é obrigatório")
        String nome
) {
}
