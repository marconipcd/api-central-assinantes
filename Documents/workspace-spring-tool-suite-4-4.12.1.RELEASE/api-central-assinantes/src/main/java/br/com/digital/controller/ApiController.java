package br.com.digital.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.digital.dao.ApiDAO;
import br.com.digital.domain.AcessoCliente;
import br.com.digital.domain.Cliente;
import br.com.digital.domain.ContaBancaria;
import br.com.digital.domain.ContasReceber;


@RestController
public class ApiController {
	
	@Autowired
	ApiDAO dao;
	
	@PostMapping("/api-central-assinantes/login")
	public Object fazerLogin(@RequestParam("cpf") String cpf){
		
		List<Cliente> clientes = dao.fazerLogin(cpf);		
		if(clientes != null) {
			return clientes.get(0);
		}				
		return null;		
	}	
	@PostMapping("/api-central-assinantes/buscarBoletos")
	public List<ContasReceber> pegarBoletos(Integer cod_contrato){		
		return dao.buscarBoletos(cod_contrato);		
	}	
	@PostMapping("/api-central-assinantes/buscarContratos")
	public List<AcessoCliente> pegarContratos(Integer cod_cliente){
		return dao.buscarContratos(cod_cliente);
	}
	@PostMapping("/api-central-assinantes/buscarIndicacoes")
	public List<Cliente> pegarIndicados(Integer cod_cliente){
		return dao.buscarIndicados(cod_cliente); 
	}
	
	
	@PostMapping("/api-central-assinantes/buscarContaBancaria")
	public ContaBancaria pegarContaBancaria(Integer cod_empresa){
		return dao.buscarContaBancaria(cod_empresa);
	}
	
	@PostMapping("/api-central-assinantes/cadastrarIndicado")
	public void cadastrarIndicado(String cod_cliente, String nome, String cpf, String ddd1, String telefone1,
			String ddd2, String telefone2, String endereco, String numero, String bairro, String cidade){
		
		Cliente c = new Cliente(null, cpf, null, null, nome, null, null, null, null, telefone1, telefone2, null, null, null, 
				null,null, null, null, "EM ANALISE", null, null, null, null, ddd1, ddd2, null, null, null, new Date(), new Date());
	
		c.setIndicador_por(Integer.parseInt(cod_cliente)); 
		c.setEmpresa(1);
		
		dao.cadastrarIndicado(c, endereco,numero,bairro,cidade);
		
	//	return null;
	}
	
	@PostMapping("/api-central-assinantes/desbloqueio72Horas")
	public boolean desbloqueio72Horas(Integer cod_boleto, Integer cod_acesso){
		return dao.desbloquear72horas(cod_boleto, cod_acesso);
	}
	
	@PostMapping("/api-central-assinantes/cadastrarToken")
	public boolean cadastrarToken(Integer cod_contrato, String token){
		return dao.cadastrarToken(cod_contrato, token);
	}
	
	
	
	
}
