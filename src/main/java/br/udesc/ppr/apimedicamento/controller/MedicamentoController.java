package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.repositories.MedicamentoRepository;
import br.udesc.ppr.apimedicamento.utils.EstatisticaDescritiva;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


//    @GetMapping("/estatistica")
//    public JSONArray getEstatisticaGeral(){
//        List<Medicamento> medicamentoList = medicamentoRepository.findAll();
//
//        JSONObject estatisticaPrincipioAtivo = EstatisticaCategoria.getEstatisticas();
//
//        return  new JSONArray().appendElement(estatisticaPrincipioAtivo);
//    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Medicamento insertOne(@RequestBody Medicamento medicamento) {return  medicamentoRepository.save(medicamento);}


    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Medicamento> insertAll(@RequestBody List<Medicamento> medicamentoList) {return  medicamentoRepository.saveAll(medicamentoList);}




}
