package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.ClasseTerapeutica;
import br.udesc.ppr.apimedicamento.repositories.ClasseTerapeuticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClasseTerapeuticaController implements  Controller  {

    @Autowired
    ClasseTerapeuticaRepository classeTerapeuticaRepository;

    @Override
    @GetMapping("")
    public List<ClasseTerapeutica> getAll() {
        return null;
    }

    @PostMapping("/all")
    public List<ClasseTerapeutica> insertAll(@RequestBody List<ClasseTerapeutica> classeTerapeuticaList){ return  classeTerapeuticaRepository.saveAll(classeTerapeuticaList);}
}
