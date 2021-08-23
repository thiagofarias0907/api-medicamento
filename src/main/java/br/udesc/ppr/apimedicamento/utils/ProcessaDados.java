package br.udesc.ppr.apimedicamento.utils;

import br.udesc.ppr.apimedicamento.controller.ClasseTerapeuticaController;
import br.udesc.ppr.apimedicamento.controller.FabricanteController;
import br.udesc.ppr.apimedicamento.controller.MedicamentoController;
import br.udesc.ppr.apimedicamento.controller.ProdutoController;
import br.udesc.ppr.apimedicamento.entities.ClasseTerapeutica;
import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.entities.Produto;
import br.udesc.ppr.apimedicamento.repositories.MedicamentoRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProcessaDados {


    //    @Autowired
//    MedicamentoRepository medicamentoRepository;
    @Autowired
    MedicamentoController medicamentoController = new MedicamentoController();
    @Autowired
    FabricanteController fabricanteController = new FabricanteController();
    @Autowired
    ProdutoController produtoController = new ProdutoController();
    @Autowired
    ClasseTerapeuticaController classeTerapeuticaController = new ClasseTerapeuticaController();

    public void saveData() {


//        mainFile();

        secondaryFile();


    }

    private void mainFile() {


        List<Medicamento> medicamentoList = new ArrayList<>();
        List<JSONObject> mainFileData = CapturaDados.readMainFile(false);
        for (JSONObject jsonObject : mainFileData) {
            if (jsonObject.getAsString(MedicamentoColumnMapper.PRINCIPIO_ATIVO.getNomeChave()).isBlank())
                continue;
            Medicamento medicamento = new Medicamento(jsonObject.getAsString(MedicamentoColumnMapper.NUMERO_PROCESSO.getNomeChave()),
                    jsonObject.getAsString(MedicamentoColumnMapper.PRINCIPIO_ATIVO.getNomeChave()));
//            medicamentoRepository.save(medicamento);
            medicamentoList.add(medicamento);

//            medicamentoController.insertOne(medicamento);
        }
        medicamentoController.insertAll(medicamentoList);
    }

    private void secondaryFile() {

        List<Medicamento> medicamentoList = new ArrayList<>();
        List<Produto> produtoList = new ArrayList<>();
        List<ClasseTerapeutica> classeTerapeuticaList = new ArrayList<>();
        List<Fabricante> fabricanteList = new ArrayList<>();

        List<JSONObject> fileData = CapturaDados.readSecondaryFile(false);
        for (JSONObject jsonObject : fileData) {

            // List all Medicamentos
            Medicamento medicamento = new Medicamento(jsonObject.getAsString(PrecoMedicamentoColumnMapper.REGISTRO.getNomeChave()),
                    jsonObject.getAsString(PrecoMedicamentoColumnMapper.SUBSTANCIA.getNomeChave()));
            medicamentoList.add(medicamento);


            // List all Fabricantes
            Fabricante fabricante = new Fabricante(jsonObject.getAsString(PrecoMedicamentoColumnMapper.CNPJ.getNomeChave()), jsonObject.getAsString(PrecoMedicamentoColumnMapper.LABORATORIO.getNomeChave()));
            if (!fabricanteList.contains(fabricante))
                fabricanteList.add(fabricante);

            // List All Classes Terapêuticas and parse the code and name for each one
            String originalClasseTerapeutica = jsonObject.getAsString(PrecoMedicamentoColumnMapper.CLASSE_TERAPEUTICA.getNomeChave());
            String[] splitClasse = originalClasseTerapeutica.split("-");
            String codigoClasse = "";
            String nomeClasse = "";
            if (splitClasse.length > 1) {
                codigoClasse = splitClasse[0].trim();
                nomeClasse = splitClasse[1].trim();
            } else {
                nomeClasse = originalClasseTerapeutica;
            }
            ClasseTerapeutica classeTerapeutica = new ClasseTerapeutica(codigoClasse, nomeClasse);
            if (!classeTerapeuticaList.contains(classeTerapeutica))
                classeTerapeuticaList.add(classeTerapeutica);


            String apresentacao = jsonObject.getAsString(PrecoMedicamentoColumnMapper.APRESENTACAO.getNomeChave());
            String dosagem = apresentacao;
            Pattern pattern = Pattern.compile("(\\d+)(\\s+)*(\\w+)(\\s+)(.*)");
            Matcher matcher = pattern.matcher(jsonObject.getAsString(PrecoMedicamentoColumnMapper.APRESENTACAO.getNomeChave()));
            if (matcher.find())
                dosagem = matcher.group(1) + " " + matcher.group(3);


            boolean restricaoHospitalar = jsonObject.getAsString(PrecoMedicamentoColumnMapper.RESTRICAO_HOSPITALAR.getNomeChave()).equalsIgnoreCase("sim");
            boolean cap = jsonObject.getAsString(PrecoMedicamentoColumnMapper.CAP.getNomeChave()).equalsIgnoreCase("sim");
            boolean confaz87 = jsonObject.getAsString(PrecoMedicamentoColumnMapper.CONFAZ.getNomeChave()).equalsIgnoreCase("sim");
            boolean icms = jsonObject.getAsString(PrecoMedicamentoColumnMapper.ICMS_0.getNomeChave()).equalsIgnoreCase("sim");
            boolean listaConcessao = jsonObject.getAsString(PrecoMedicamentoColumnMapper.LISTA_CONCESSSAO.getNomeChave()).equalsIgnoreCase("Positiva");

            float preco = 0f;
            String precoOriginal = jsonObject.getAsString(PrecoMedicamentoColumnMapper.PRECO_FINAL_SEM_IMPOSTOS.getNomeChave()).replace(',','.');
            try {
                preco = Float.parseFloat(precoOriginal);
            } catch (NumberFormatException ex){
                System.out.println("Erro em parse do preço: " + precoOriginal);
            }

            Long registro = 0l;
            String registroOriginal = jsonObject.getAsString(PrecoMedicamentoColumnMapper.REGISTRO.getNomeChave());
            if (registroOriginal.trim().equalsIgnoreCase("-"))
                registroOriginal = jsonObject.getAsString(PrecoMedicamentoColumnMapper.CODIGO_GGREM.getNomeChave());

            try {
                registro = Long.parseLong(registroOriginal);
            } catch (NumberFormatException ex){
                System.out.println("Erro em parse do registro: " + registroOriginal);
            }


            Produto produto = new Produto(
                    jsonObject.getAsString(PrecoMedicamentoColumnMapper.PRODUTO.getNomeChave()),
                    jsonObject.getAsString(PrecoMedicamentoColumnMapper.TIPO_DE_PRODUTO.getNomeChave()),
                    preco,
                    registro,
                    jsonObject.getAsString(PrecoMedicamentoColumnMapper.EAN1.getNomeChave()),
                    apresentacao,
                    dosagem,
                    jsonObject.getAsString(PrecoMedicamentoColumnMapper.TARJA.getNomeChave()),
                    restricaoHospitalar,
                    jsonObject.getAsString(PrecoMedicamentoColumnMapper.REGIME_DE_PRECO.getNomeChave()),
                    cap,
                    confaz87,
                    icms,
                    listaConcessao,
                    fabricante,
                    medicamento,
                    classeTerapeutica
                    );

            if (!produtoList.contains(produto))
                produtoList.add(produto);
        }
        //Insert all data in database
        medicamentoController.insertAll(medicamentoList);
        fabricanteController.insertAll(fabricanteList);
        classeTerapeuticaController.insertAll(classeTerapeuticaList);
        produtoController.insertAll(produtoList);



//        Produto produto = new Produto (
//                nome =  jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                categoria = jsonObject.getAsString(MedicamentoColumnMapper.CATEGORIA.getNomeChave()),
//                preco = jsonObject.getAsString(MedicamentoColumnMapper.getNomeChave()),
//                registro = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                codigoEAN = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                apresentacao = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                dosagem = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                tarja = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                restricao = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                regimePreco = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                cap = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                confaz87 = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                icms = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                listaConcessao = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                fabricante = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//                classeTerapeutica = jsonObject.getAsString(MedicamentoColumnMapper.NOME.getNomeChave()),
//
//                );
    }

}
