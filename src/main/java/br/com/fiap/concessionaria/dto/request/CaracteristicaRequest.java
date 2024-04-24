package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CaracteristicaRequest(

        AbstractRequest veiculo,

        @Size(min = 3, max = 30)
        @NotNull(message = "O nome é obrigatório")
        String nome,

        @Size(min = 3, max = 20)
        @NotNull(message = "O nome é obrigatório")
        String descricao
) {
}
