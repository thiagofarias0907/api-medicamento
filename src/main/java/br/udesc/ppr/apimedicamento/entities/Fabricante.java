package br.udesc.ppr.apimedicamento.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fabricate")
public class Fabricante {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfabricante;
    private String cnpj;
    private String nome;

    @OneToMany(mappedBy = "fabricante")
    List<Produto> produtoList;
}
