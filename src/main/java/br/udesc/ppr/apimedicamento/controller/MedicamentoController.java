package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
