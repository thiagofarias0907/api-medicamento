package br.udesc.ppr.apimedicamento.entities;

import com.opencsv.bean.CsvBindByName;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmedicamento;
    private String codigo;
//    @CsvBindByName(column = "PRINCIPIO_ATIVO")
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
