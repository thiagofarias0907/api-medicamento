package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.Produto;
import br.udesc.ppr.apimedicamento.utils.CapturaDados;
import br.udesc.ppr.apimedicamento.utils.ProcessaDados;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/atualiza")
public class AtualizaDadosController implements Controller {

    @Autowired
    ProcessaDados processaDados;

    @GetMapping("/0")
    public List<String[]> readTestFile(){
        return CapturaDados.readTestFile();
    }


    @GetMapping("/1")
    public List<JSONObject> readMainFile(){
        return CapturaDados.readMainFile(true);
    }

    @GetMapping("/2")
    public List<JSONObject> readSecondaryFile(){
        return CapturaDados.readSecondaryFile(true);
    }

    @GetMapping("/3")
    public void processFiles(){
        processaDados.saveData();
    }

    @GetMapping("")
    @Override
    public List getAll() {
        List filesNames = new ArrayList<String>();
        filesNames.add("1 - DADOS_ABERTOS_MEDICAMENTOS");
        filesNames.add("2 - TA_PRECO_MEDICAMENTO");
        return filesNames;
    }
}
