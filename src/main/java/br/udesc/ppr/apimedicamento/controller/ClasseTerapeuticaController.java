package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.ClasseTerapeutica;
import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.repositories.ClasseTerapeuticaRepository;
import br.udesc.ppr.apimedicamento.utils.EstatisticaCategoria;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    @PostMapping("/all")
    public List<ClasseTerapeutica> insertAll(@RequestBody List classeTerapeuticaList){ return  classeTerapeuticaRepository.saveAll(classeTerapeuticaList);}

    @Override
    @GetMapping("/estatisticas")
    public JSONObject getEstatisticas() {
        List<ClasseTerapeutica> classeTerapeuticaList = classeTerapeuticaRepository.findAll();
        List<String> classes = new ArrayList();
        for(ClasseTerapeutica classe : classeTerapeuticaList){
            classes.add(classe.getNome());
        }
        Map<String,Float> estatisticas = EstatisticaCategoria.getEstatisticas(classes);

        JSONObject resposta = new JSONObject();
        resposta.put("total",classeTerapeuticaList.size());
        estatisticas.forEach(resposta::put);
        return resposta;
    }
}
