package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.repositories.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabricantes")
public class FabricanteController implements  Controller  {

    @Autowired
    FabricanteRepository fabricanteRepository;

    @GetMapping("")
    @Override
    public List<Fabricante> getAll(){return  fabricanteRepository.findAll();}

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Fabricante> insertAll(@RequestBody List<Fabricante> fabricanteList) {return  fabricanteRepository.saveAll(fabricanteList);}
}
