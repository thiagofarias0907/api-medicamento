package br.udesc.ppr.apimedicamento.controller;

import br.udesc.ppr.apimedicamento.entities.ClasseTerapeutica;
import br.udesc.ppr.apimedicamento.entities.Fabricante;
import br.udesc.ppr.apimedicamento.entities.Medicamento;
import br.udesc.ppr.apimedicamento.entities.Produto;
import br.udesc.ppr.apimedicamento.repositories.ClasseTerapeuticaRepository;
import br.udesc.ppr.apimedicamento.repositories.FabricanteRepository;
import br.udesc.ppr.apimedicamento.repositories.MedicamentoRepository;
import br.udesc.ppr.apimedicamento.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto insertProduto(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Produto> insertAll(@RequestBody List<Produto> produtoList){
        return produtoRepository.saveAll(produtoList);
    }



}
