package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record VeiculoRequest(

        @NotNull(message = "O preço é obrigatório")
        Double preco,

        @NotNull(message = "O modelo é obrigatório")
        String modelo,

        Year anoDeFabricacao,

        @NotNull(message = "O nome é obrigatório")
        String nome,

        AbstractRequest tipo,

        AbstractRequest fabricante,

        String cor,

        @Size(min = 2, max = 15)
        String palavraDeEfeito,

        Short cilindradas
) {
}
