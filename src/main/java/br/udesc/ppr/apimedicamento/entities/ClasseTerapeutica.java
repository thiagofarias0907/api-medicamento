package br.udesc.ppr.apimedicamento.entities;

import com.opencsv.bean.CsvBindByName;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

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
    @CsvBindByName(column = "CLASSE_TERAPEUTICA")
    private String nome;

    @OneToMany(mappedBy = "classeTerapeutica")
    List<Produto> produtoList;

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
}
