package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Produto;
import br.udesc.ppr.apimedicamento.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController implements  Controller {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("")
    @Override
    public List<Produto> getAll(){
        return produtoRepository.findAll();
    }
}
