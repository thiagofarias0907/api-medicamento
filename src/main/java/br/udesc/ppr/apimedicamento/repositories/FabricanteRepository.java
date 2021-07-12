package br.udesc.ppr.apimedicamento.repositories;

import br.udesc.ppr.apimedicamento.entities.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FabricanteRepository extends JpaRepository<Fabricante,Long> {
}
