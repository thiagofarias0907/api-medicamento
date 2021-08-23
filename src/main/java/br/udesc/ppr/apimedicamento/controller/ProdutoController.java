package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Produto;
import br.udesc.ppr.apimedicamento.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto insertProduto(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Produto> insertAll(@RequestBody List<Produto> produtoList){
        return produtoRepository.saveAll(produtoList);
    }


}
