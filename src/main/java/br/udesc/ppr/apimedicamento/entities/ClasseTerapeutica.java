package br.udesc.ppr.apimedicamento.entities;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classeterapeutica")
public class ClasseTerapeutica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idclasseterapeutica;
    private String codigo;
    private String nome;

    @OneToMany(mappedBy = "classeTerapeutica")
    List<Produto> produtoList;

    ClasseTerapeutica(){

    };
}
