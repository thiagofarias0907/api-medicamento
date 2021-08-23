package br.udesc.ppr.apimedicamento.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "fabricate")
public class Fabricante {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfabricante;
    private String cnpj;
    private String nome;

    public Fabricante(String cnpj, String nome) {
        this.cnpj = cnpj.replaceAll("\\D+","");
        this.nome = nome;
    }

    @OneToMany(mappedBy = "fabricante")
    List<Produto> produtoList;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fabricante that = (Fabricante) o;
        return Objects.equals(cnpj, that.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }
}
