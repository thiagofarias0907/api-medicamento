package br.udesc.ppr.apimedicamento.utils;

public enum MedicamentoColumnMapper {

    TIPO("TIPO_PRODUTO", "tipoProduto"),
    NOME("NOME_PRODUTO","nomeProduto"),
    DATA_PROCESSO("DATA_FINALIZACAO_PROCESSO","dataProcesso"),
    CATEGORIA("CATEGORIA_REGULATORIA","categoria"),
    NUMERO_REGISTRO("NUMERO_REGISTRO_PRODUTO","numeroRegistro"),
    DATA_VENCIMENTO("DATA_VENCIMENTO_REGISTRO","dataVencimento"),
    NUMERO_PROCESSO("NUMERO_PROCESSO","numeroProcesso"),
    CLASSE_TERAPEUTICA("CLASSE_TERAPEUTICA","classeTerapeutica"),
    EMPRESA("EMPRESA_DETENTORA_REGISTRO","empresa"),
    PRINCIPIO_ATIVO("PRINCIPIO_ATIVO","principioAtivo")
    ;


    private String nomeColuna;
    private String nomeChave;

    MedicamentoColumnMapper(String nomeColuna, String nomeChave) {
        this.nomeColuna = nomeColuna;
        this.nomeChave = nomeChave;
    }

    public String getNomeColuna() {
        return nomeColuna;
    }

    public String getNomeChave() {
        return nomeChave;
    }
}
