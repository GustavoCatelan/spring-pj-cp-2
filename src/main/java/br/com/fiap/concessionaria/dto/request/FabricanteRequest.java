package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;

public record FabricanteRequest(

        @NotNull(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "O nome fantasia é obrigatório")
        String nomeFantasia
) {
}
