package br.udesc.ppr.apimedicamento.repositories;


import br.udesc.ppr.apimedicamento.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
