package br.udesc.ppr.apimedicamento.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "classeterapeutica")
public class ClasseTerapeutica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idclasseterapeutica;
    private String codigo;
    private String nome;

    @OneToMany(mappedBy = "classeTerapeutica")
    @JsonIgnore
    private List<Produto> produtoList;

    ClasseTerapeutica(){

    };

    public ClasseTerapeutica(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasseTerapeutica that = (ClasseTerapeutica) o;
        return Objects.equals(codigo, that.codigo) &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nome);
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    public Long getIdclasseterapeutica() {
        return idclasseterapeutica;
    }

    public void setIdclasseterapeutica(Long idclasseterapeutica) {
        this.idclasseterapeutica = idclasseterapeutica;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
