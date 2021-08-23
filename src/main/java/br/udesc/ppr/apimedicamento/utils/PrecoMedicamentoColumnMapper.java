package br.udesc.ppr.apimedicamento.utils;

public enum PrecoMedicamentoColumnMapper {

    SUBSTANCIA("SUBSTÂNCIA", "substancia"),
    CNPJ("CNPJ", "cnpj"),
    LABORATORIO("LABORATÓRIO", "laboratorio"),
    CODIGO_GGREM("CÓDIGO GGREM","codigoGgrem"),
    REGISTRO("REGISTRO", "codigoRegistro"),
    EAN1("EAN 1", "ean1"),
    EAN2("EAN 2", "ean2"),
    EAN3("EAN 3", "ean3"),
    PRODUTO("PRODUTO", "nomeProduto"),
    APRESENTACAO("APRESENTAÇÃO", "nomeApresentacao"),
    CLASSE_TERAPEUTICA("CLASSE TERAPÊUTICA", "classeTerapeutica"),
    TIPO_DE_PRODUTO("TIPO DE PRODUTO (STATUS DO PRODUTO)","tipoProduto"),
    REGIME_DE_PRECO("REGIME DE PREÇO","regimePreco"),
    PRECO_FINAL_SEM_IMPOSTOS("PF Sem Impostos","precoSemImpostos"),
    RESTRICAO_HOSPITALAR("RESTRIÇÃO HOSPITALAR","restricaoHospitalar"),
    CAP("CAP","cap"),
    CONFAZ("CONFAZ 87","confaz87"),
    ICMS_0("ICMS 0%","icms"),
    LISTA_CONCESSSAO("LISTA DE CONCESSÃO DE CRÉDITO TRIBUTÁRIO (PIS/COFINS)","listaConcessao"),
    TARJA("TARJA","tarja")
    ;


    private String nomeColuna;
    private String nomeChave;

    PrecoMedicamentoColumnMapper(String nomeColuna, String nomeChave) {
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
