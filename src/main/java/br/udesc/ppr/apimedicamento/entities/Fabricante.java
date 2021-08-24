package br.udesc.ppr.apimedicamento.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "fabricante")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfabricante;
    @Column(unique = true)
    private String cnpj;
    private String nome;


    public Fabricante() {
    }

    public Fabricante(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    private  List<Produto> produtoList;

    public Long getIdfabricante() {
        return idfabricante;
    }

    public void setIdfabricante(Long idfabricante) {
        this.idfabricante = idfabricante;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fabricante)) return false;
        Fabricante that = (Fabricante) o;
        return Objects.equals(cnpj, that.cnpj) &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, nome);
    }
}
