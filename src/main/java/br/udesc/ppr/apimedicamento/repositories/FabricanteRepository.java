package br.udesc.ppr.apimedicamento.repositories;

import br.udesc.ppr.apimedicamento.entities.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FabricanteRepository extends JpaRepository<Fabricante,Long> {

    List<Fabricante> findAllByCnpj(String cnpj);

    Fabricante findByCnpj(String cnpj);
}
