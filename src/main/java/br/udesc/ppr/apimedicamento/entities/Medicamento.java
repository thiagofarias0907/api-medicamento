package br.udesc.ppr.apimedicamento.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "medicamento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmedicamento;
    private String codigo;
    @Column( length = 1000 )
    private String principioAtivo;


    public Medicamento() {
    }

    public Medicamento(String codigo, String principioAtivo) {
        this.codigo = codigo;
        this.principioAtivo = principioAtivo;
    }

    @OneToOne(mappedBy = "medicamento")
    Produto produto;

    public Long getIdmedicamento() {
        return idmedicamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }
}
