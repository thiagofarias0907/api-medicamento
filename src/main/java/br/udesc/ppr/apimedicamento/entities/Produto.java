package br.udesc.ppr.apimedicamento.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import javax.persistence.*;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idproduto;

    //    @CsvBindByName(column = "NOME_PRODUTO")
    private String nome;
    //    @CsvBindByName(column = "CATEGORIA_REGULATORIA")
    private String categoria;
    //    @CsvIgnore
    private Float preco;
    //    @CsvBindByName(column = "NUMERO_REGISTRO_PRODUTO")
    private Long registro;
    //    @CsvBindByName(column = "NOME_PRODUTO")
//    @CsvIgnore
    private String codigoEAN;
    //    @CsvBindByName(column = "NOME_PRODUTO")
//    @CsvIgnore
    private String apresentacao;
    //    @CsvBindByName(column = "NOME_PRODUTO")
//    @CsvIgnore
    private String dosagem;
    //    @CsvBindByName(column = "NOME_PRODUTO")
//    @CsvIgnore
    private String tarja;
    //    @CsvBindByName(column = "NOME_PRODUTO")
//    @CsvIgnore
    private Boolean restricao;

    private String regimePreco;
    private Boolean cap;
    private Boolean confaz87;
    private Boolean icms;
    private Boolean listaConcessao;

    @Transient
    private Map<String, Boolean> detalhestributarios;

    public Produto() {
    }

    public Produto(String nome, String categoria, Long registro) {
        this.nome = nome;
        this.categoria = categoria;
        this.registro = registro;
    }

    public Produto(String nome, String categoria, Float preco, Long registro, String codigoEAN, String apresentacao, String dosagem, String tarja, Boolean restricao, String regimePreco, Boolean cap, Boolean confaz87, Boolean icms, Boolean listaConcessao, Fabricante fabricante, Medicamento medicamento, ClasseTerapeutica classeTerapeutica) {        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.registro = registro;
        this.codigoEAN = codigoEAN;
        this.apresentacao = apresentacao;
        this.dosagem = dosagem;
        this.tarja = tarja;
        this.restricao = restricao;
        this.regimePreco = regimePreco;
        this.cap = cap;
        this.confaz87 = confaz87;
        this.icms = icms;
        this.listaConcessao = listaConcessao;
        this.fabricante = fabricante;
        this.medicamento = medicamento;
        this.classeTerapeutica = classeTerapeutica;
    }

    @JoinColumn(name = "idfabricante", referencedColumnName = "idfabricante")
    @ManyToOne( cascade=CascadeType.ALL)
    private Fabricante fabricante;

    @OneToOne( cascade=CascadeType.ALL)
    @JoinColumn(name = "idmedicamento", referencedColumnName = "idmedicamento")
    private Medicamento medicamento;

    @OneToOne( cascade=CascadeType.ALL)
    @JoinColumn(name = "idclasseterapeutica", referencedColumnName = "idclasseterapeutica")
    private ClasseTerapeutica classeTerapeutica;

    public Long getRegistro() {
        return registro;
    }

    public String getCodigoEAN() {
        return codigoEAN;
    }

    public String getNome() {
        return nome;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDosagem() {
        return dosagem;
    }

    public String getTarja() {
        return tarja;
    }

    public float getPreco() {
        return preco;
    }

    public Boolean getRestricao() {
        return restricao;
    }

    public String getRegimePreco() {
        return regimePreco;
    }

    public Map<String, Boolean> getDetalhestributarios() {
        detalhestributarios.put("cap", cap);
        detalhestributarios.put("confaz87", confaz87);
        detalhestributarios.put("icms", icms);
        detalhestributarios.put("listaConcessao", listaConcessao);

        return detalhestributarios;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(nome, produto.nome) &&
                Objects.equals(preco, produto.preco) &&
                Objects.equals(registro, produto.registro) &&
                Objects.equals(codigoEAN, produto.codigoEAN) &&
                Objects.equals(apresentacao, produto.apresentacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, preco, registro, codigoEAN, apresentacao);
    }
}
