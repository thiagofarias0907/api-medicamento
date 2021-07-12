package br.udesc.ppr.apimedicamento.repositories;

import br.udesc.ppr.apimedicamento.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository  extends JpaRepository<Medicamento,Long> {
}
