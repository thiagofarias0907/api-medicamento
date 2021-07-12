package br.udesc.ppr.apimedicamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiMedicamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMedicamentoApplication.class, args);
	}

//	@GetMapping("")
//	public String helloWorld(){
//		return ("{\"project\":\"api-medicamentos\", \"status\":\"running\"}");
//	}


}
