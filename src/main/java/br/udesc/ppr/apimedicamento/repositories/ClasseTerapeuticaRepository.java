package br.udesc.ppr.apimedicamento.repositories;

import br.udesc.ppr.apimedicamento.entities.ClasseTerapeutica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasseTerapeuticaRepository extends JpaRepository<ClasseTerapeutica,Long> {
}
