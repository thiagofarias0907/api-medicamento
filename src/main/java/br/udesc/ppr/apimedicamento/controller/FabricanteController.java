package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.repositories.FabricanteRepository;
import br.udesc.ppr.apimedicamento.utils.EstatisticaDescritiva;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fabricantes")
public class FabricanteController implements  Controller  {

    @Autowired
    FabricanteRepository fabricanteRepository;

    @GetMapping("")
    @Override
    public List<Fabricante> getAll(){return  fabricanteRepository.findAll();}

    @GetMapping("/cnpj/{cnpj}")
    public List<Fabricante> getByCnpj(@PathVariable("cnpj") String cnpj) {return fabricanteRepository.findAllByCnpj(cnpj);}

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Fabricante> insertAll(@RequestBody List<Fabricante> fabricanteList) {return  fabricanteRepository.saveAll(fabricanteList);}

}
