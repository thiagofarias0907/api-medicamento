package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.repositories.MedicamentoRepository;
import br.udesc.ppr.apimedicamento.utils.EstatisticaCategoria;
import br.udesc.ppr.apimedicamento.utils.EstatisticaDescritiva;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @GetMapping("/principioAtivo/{substancia}")
    public List<Medicamento> getByPrincipoAtivo(@PathVariable("substancia") String substancia) {return medicamentoRepository.findAllByPrincipioAtivo(substancia);}
    @GetMapping("/codigo/{codigo}")
    public List<Medicamento> getByCodigo(@PathVariable("codigo") String codigo){return medicamentoRepository.findAllByCodigo(codigo);}
    @GetMapping("/{id}")
    public Medicamento getById(@PathVariable("id") Long id){
        return medicamentoRepository.getById(id);
    }

    @GetMapping("/principioAtivo/{substancia}/estatistica")
    public JSONObject getEstatisticaGeral(@PathVariable("substancia") String substancia){
        List<Medicamento> medicamentoList = medicamentoRepository.findAllByPrincipioAtivo(substancia);
        double[] pricesArray = new double[medicamentoList.size()];
        int i = 0;
        for(Medicamento medicamento : medicamentoList){
            pricesArray[i] = medicamento.getProduto().getPreco();
            i++;
        }
        Map<String,Float> descritiva = EstatisticaDescritiva.getEstatisticas(pricesArray);
        JSONObject resposta = new JSONObject();
        resposta.put("descritiva",descritiva);
        return  resposta;
    }
    @Override
    @GetMapping("/principioAtivo/estatistica")
    public JSONObject getEstatisticas(){
        List<Medicamento> medicamentoList = medicamentoRepository.findAll();
        List<String> principioAtivoList = new ArrayList();
        for(Medicamento medicamento : medicamentoList){
            principioAtivoList.add(medicamento.getPrincipioAtivo());
        }
        Map<String,Float> estatisticaPrincipioAtivo = EstatisticaCategoria.getEstatisticas(principioAtivoList);

        JSONObject resposta = new JSONObject();
        resposta.put("total",medicamentoList.size());
        estatisticaPrincipioAtivo.forEach(resposta::put);
        return resposta;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Medicamento insertOne(@RequestBody Medicamento medicamento) {return  medicamentoRepository.save(medicamento);}


    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Medicamento> insertAll(@RequestBody List medicamentoList) {return  medicamentoRepository.saveAll(medicamentoList);}




}
