package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController implements  Controller  {


    @Autowired
    MedicamentoRepository medicamentoRepository;

    @GetMapping("")
    @Override
    public List<Medicamento> getAll(){
        return  medicamentoRepository.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Medicamento insertOne(@RequestBody Medicamento medicamento) {return  medicamentoRepository.save(medicamento);}


    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Medicamento> insertAll(@RequestBody List<Medicamento> medicamentoList) {return  medicamentoRepository.saveAll(medicamentoList);}


}
