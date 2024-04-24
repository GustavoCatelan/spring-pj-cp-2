package br.com.fiap.concessionaria.dto.response;

public record CaracteristicaResponse(

        Long id,

        String nome,

        String descricao,

        VeiculoResponse veiculo
) {
}
