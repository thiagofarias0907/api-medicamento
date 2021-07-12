package br.udesc.ppr.apimedicamento.entities;

import javax.persistence.*;
import java.nio.channels.FileLock;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idproduto;
    private String nome;
    private String categoria;
    private Float preco;

    @ManyToOne
    @JoinColumn(name = "idfabricante", referencedColumnName = "idfabricante")
    private Fabricante fabricante;


    @OneToOne
    @JoinColumn(name = "idmedicamento", referencedColumnName = "idmedicamento")
    private Medicamento medicamento;

    @OneToOne
    @JoinColumn(name = "idclasseterapeutica", referencedColumnName = "idclasseterapeutica")
    private ClasseTerapeutica classeTerapeutica;

}
