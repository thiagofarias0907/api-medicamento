package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.ClasseTerapeutica;
import br.udesc.ppr.apimedicamento.repositories.ClasseTerapeuticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
