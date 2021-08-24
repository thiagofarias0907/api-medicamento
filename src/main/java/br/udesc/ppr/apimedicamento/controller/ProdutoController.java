package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.ClasseTerapeutica;
import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.entities.Produto;
import br.udesc.ppr.apimedicamento.repositories.ClasseTerapeuticaRepository;
import br.udesc.ppr.apimedicamento.repositories.FabricanteRepository;
import br.udesc.ppr.apimedicamento.repositories.MedicamentoRepository;
import br.udesc.ppr.apimedicamento.repositories.ProdutoRepository;
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
@RequestMapping("/produtos")
public class ProdutoController implements  Controller {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    FabricanteRepository fabricanteRepository;
    @Autowired
    MedicamentoRepository medicamentoRepository;
    @Autowired
    ClasseTerapeuticaRepository classeTerapeuticaRepository;

    @GetMapping("")
    @Override
    public List<Produto> getAll(){
        return produtoRepository.findAll();
    }


    @GetMapping("/fabricante/cnpj/{cnpj}")
    public List<Produto> getByCnpj(@PathVariable("cnpj") String cnpj){
        return produtoRepository.findByFabricante_Cnpj(cnpj);
    }
    @GetMapping("/fabricante/nome/{nome}")
    public List<Produto> getByNomeFabricante(@PathVariable("nome") String nome){
        return produtoRepository.findByFabricante_Nome(nome);
    }
    @GetMapping("/fabricante/{id}")
    public List<Produto> getByIdFabricante(@PathVariable("id") Long id){
        if (!fabricanteRepository.existsById(id))
                return null;
        Fabricante fabricante = fabricanteRepository.findById(id).get();

        return produtoRepository.findAllByFabricante(fabricante);
    }

    @GetMapping("/tarja/{tarja}")
    public List<Produto> getByTarja(@PathVariable("tarja") String tarja){ return produtoRepository.findAllByTarja(tarja);}
    @GetMapping("/categoria/{categoria}")
    public List<Produto> getByCategoria(@PathVariable("categoria") String categoria){ return produtoRepository.findAllByCategoria(categoria);}
    @GetMapping("/classe/codigo/{codigo}")
    public List<Produto> getByClasseCodigo(@PathVariable("codigo") String codigo){return produtoRepository.findAllByClasseTerapeutica_Codigo(codigo);}
    @GetMapping("/classe/nome/{nome}")
    public List<Produto> getByClasseNome(@PathVariable("nome") String nome){return produtoRepository.findAllByClasseTerapeutica_Nome(nome);}

    @GetMapping("/medicamento/principioAtivo/{substancia}")
    public List<Produto> getByMedicamentoPrincipoAtivo(@PathVariable("substancia") String substancia) {return produtoRepository.findAllByMedicamento_PrincipioAtivo(substancia);}
    @GetMapping("/medicamento/codigo/{codigo}")
    public List<Produto> getByMedicamentoCodigo(@PathVariable("codigo") String codigo){return produtoRepository.findAllByMedicamento_Codigo(codigo);}
    @GetMapping("/medicamento/{id}")
    public List<Produto>  getByIdMedicamento(@PathVariable("id") Long id){
        if (!medicamentoRepository.existsById(id))
            return null;
        Medicamento medicamento = medicamentoRepository.findById(id).get();
        return produtoRepository.findAllByMedicamento(medicamento);
    }

    @GetMapping("/categoria/{categoria}/estatistica")
    public JSONObject getEstatisticaByCategoria(@PathVariable("categoria") String categoria) {
        List<Produto> produtoList = produtoRepository.findAllByCategoria(categoria);
        JSONObject resposta = new JSONObject();
        resposta.put("categoria",categoria);
        resposta.put("descritiva",statisticData(produtoList));
        return resposta;
    }

    @GetMapping("/cnpj/{cnpj}/estatistica")
    public JSONObject getEstatisticaByCnpj(@PathVariable("cnpj") String cnpj) {
        List<Produto> produtoList = produtoRepository.findByFabricante_Cnpj(cnpj);
        Fabricante fabricante = fabricanteRepository.findByCnpj(cnpj);
        JSONObject resposta = new JSONObject();
        JSONObject empresa = new JSONObject();
        empresa.put("codigo",fabricante.getCnpj());
        empresa.put("nome",fabricante.getNome());
        resposta.put("empresa",empresa);
        resposta.put("descritiva",statisticData(produtoList));
        return resposta;
    }

    @GetMapping("/classeTerapeutica/{codigo}/estatistica")
    public JSONObject getEstatisticaByClasseCodigo(@PathVariable("codigo") String codigo) {
        List<Produto> produtoList = produtoRepository.findAllByClasseTerapeutica_Codigo(codigo);
        ClasseTerapeutica classeTerapeutica = classeTerapeuticaRepository.findByCodigo(codigo);
        JSONObject resposta = new JSONObject();
        JSONObject classe = new JSONObject();
        classe.put("codigo",classeTerapeutica.getCodigo());
        classe.put("nome",classeTerapeutica.getNome());
        resposta.put("classeTerapeutica",classe);
        resposta.put("descritiva",statisticData(produtoList));
        return resposta;
    }


    @GetMapping("/tarja/{tarja}/estatistica")
    public JSONObject getEstatisticaByTarja(@PathVariable("tarja") String tarja) {
        List<Produto> produtoList = produtoRepository.findAllByTarja(tarja);
        JSONObject resposta = new JSONObject();
        resposta.put("tarja",tarja);
        resposta.put("descritiva",statisticData(produtoList));
        return resposta;
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto insertProduto(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Produto> insertAll(@RequestBody List produtoList){
        return produtoRepository.saveAll(produtoList);
    }

    @Override
    @GetMapping("/estatisticas")
    public JSONObject getEstatisticas() {

        List<Produto> produtoList = produtoRepository.findAll();
        List<String> produtos = new ArrayList();
        for(Produto classe : produtoList){
            produtos.add(classe.getNome());
        }
        Map<String,Float> estatisticas = EstatisticaCategoria.getEstatisticas(produtos);

        JSONObject resposta = new JSONObject();
        resposta.put("total",produtoList.size());
        estatisticas.forEach(resposta::put);
        return resposta;
    }


    private Map<String,Float> statisticData(List<Produto> produtoList){

        double[] pricesArray = new double[produtoList.size()];
        int i = 0;
        for(Produto produto : produtoList){
            pricesArray[i] = produto.getPreco();
            i++;
        }
        Map<String,Float> descritiva = EstatisticaDescritiva.getEstatisticas(pricesArray);
        return  descritiva;
    }

}
