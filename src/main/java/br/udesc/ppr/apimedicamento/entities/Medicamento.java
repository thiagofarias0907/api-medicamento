package br.udesc.ppr.apimedicamento.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmedicamento;
    private String codigo;
    private String principioativo;


    @OneToOne(mappedBy = "medicamento")
    Produto produto;
}
