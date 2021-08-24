package br.udesc.ppr.apimedicamento.repositories;

import br.udesc.ppr.apimedicamento.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicamentoRepository  extends JpaRepository<Medicamento,Long> {
    List<Medicamento> findAllByCodigo(String codigo);

    List<Medicamento> findAllByPrincipioAtivo(String substancia);
}
