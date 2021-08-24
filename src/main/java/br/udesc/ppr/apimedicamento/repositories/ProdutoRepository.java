package br.udesc.ppr.apimedicamento.repositories;


import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    List<Produto> findByFabricante_Cnpj(String cnpj);

    List<Produto> findByFabricante_Nome(String nome);
//    List<Produto> findByFabricante(Fabricante fabricante);
    List<Produto> findAllByFabricante(Fabricante fabricante);

    List<Produto> findAllByCategoria(String categoria);

    List<Produto> findAllByClasseTerapeutica_Codigo(String codigo);
    List<Produto> findAllByClasseTerapeutica_Nome(String codigo);

    List<Produto> findAllByMedicamento_PrincipioAtivo(String substancia);
    List<Produto> findAllByMedicamento_Codigo(String codigo);
    List<Produto> findAllByMedicamento(Medicamento medicamento);
}
