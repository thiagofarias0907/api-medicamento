package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.repositories.FabricanteRepository;
import br.udesc.ppr.apimedicamento.utils.EstatisticaCategoria;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Override
    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Fabricante> insertAll(@RequestBody List fabricanteList) {return  fabricanteRepository.saveAll(fabricanteList);}

    @Override
    @GetMapping("/estatisticas")
    public JSONObject getEstatisticas() {
        List<Fabricante> fabricanteList = fabricanteRepository.findAll();
        List<String> fabricantes = new ArrayList();
        for(Fabricante fabricante : fabricanteList){
            fabricantes.add(fabricante.getCnpj());
        }
        Map<String,Float> estatisticaPrincipioAtivo = EstatisticaCategoria.getEstatisticas(fabricantes);

        JSONObject resposta = new JSONObject();
        resposta.put("total",fabricanteList.size());
        estatisticaPrincipioAtivo.forEach(resposta::put);
        return resposta;
    }

}
