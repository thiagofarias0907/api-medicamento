package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.repositories.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fabricantes")
public class FabricanteController implements  Controller  {

    @Autowired
    FabricanteRepository fabricanteRepository;

    @GetMapping("")
    @Override
    public List<Fabricante> getAll(){return  fabricanteRepository.findAll();}
}
