package br.com.digital.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.digital.domain.AcessoCliente;
import br.com.digital.domain.AgendamentoBloqueioDesbloqueio;
import br.com.digital.domain.AlteracoesContrato;
import br.com.digital.domain.Cliente;
import br.com.digital.domain.ContaBancaria;
import br.com.digital.domain.ContasReceber;
import br.com.digital.domain.Endereco;
import br.com.digital.domain.HistoricoDesbloqueio72Horas;
import br.com.digital.domain.RadReply;
import br.com.digital.domain.RadUserGgroup;
import br.com.digital.domain.TokenClientes;
import br.com.digital.util.HuaweiUtil;


@Repository
public class ApiDAO {

	@Autowired
	private EntityManager em;
	
	@Autowired
	private HuaweiUtil huaweiUtil;
	
	@Autowired
	private IttvDAO ittvDAO;

	public List<Cliente> fazerLogin(String cpf) {
		
		Query q = em.createQuery("select a from Cliente a where a.doc_cpf_cnpj =:cpf and a.empresa=1", Cliente.class);
		q.setParameter("cpf", cpf);
		
		return q.getResultList();
	}	
	public List<ContasReceber> buscarBoletos(Integer codAcesso){
				
		try{
			
			String regexNova = "^"+codAcesso.toString()+"/[0-9]{2}-[0-9]{2}/[0-9]{2}";
			String regexAntiga = "^"+codAcesso.toString()+"/[0-9]{2}/[0-9]{2}";
			String rProrata = "^"+codAcesso.toString()+"/PRORATA";
			
			
			Query qn = em.createNativeQuery("select * from contas_receber cr where " +				
					"cr.status_2 ='ABERTO' " +				
					"and cr.n_doc REGEXP :rNova " +
					
					"or cr.status_2 ='ABERTO' " +				
					"and cr.n_doc REGEXP :rAntiga " +
					
					"or cr.status_2 ='ABERTO' " +				
					"and cr.n_doc REGEXP :rProrata " 
					
					+ "ORDER BY cr.data_vencimento ASC ",
					
					ContasReceber.class);
			
			qn.setParameter("rNova", regexNova);
			qn.setParameter("rAntiga", regexAntiga);
			qn.setParameter("rProrata", rProrata);
			
			if(qn.getResultList().size()>0){
				return qn.getResultList();
			}
			
			return null;
		
		}catch (Exception e){
			e.printStackTrace();			
			return null;
		}
	}
	public ContaBancaria buscarContaBancaria(Integer codEmpresa){
		
		try{
			
			Query qn = em.createNativeQuery("select c from Contabancaria c where c.empresa_id=:e",		
					ContaBancaria.class);
						
			qn.setParameter("e", codEmpresa);
				
			if(qn.getResultList().size()>0) {
				return (ContaBancaria)qn.getResultList().get(0);
			}else {
				return null;
			}
		
		}catch (Exception e){
			e.printStackTrace();			
			return null;
		}
	}
	public List<AcessoCliente> buscarContratos(Integer cod_cliente) {
		
		Query q = em.createQuery("select a from AcessoCliente a where a.cliente=:c", AcessoCliente.class);
		q.setParameter("c", cod_cliente);
		
		return q.getResultList();
	}
	public List<Cliente> buscarIndicados(Integer cod_cliente) {
		
		Query q = em.createQuery("select a from Cliente a where a.indicado_por=:c", Cliente.class);
		q.setParameter("c", cod_cliente);
		
		return q.getResultList();
	}
	@Transactional
	public void cadastrarIndicado(Cliente c, String endereco,String numero, String bairro, String cidade) {
		
		
		
		Cliente c2 = c;
		c2.setAgendar_crm("NAO");
		c2.setContato(c2.getNome_razao());
		c2.setData_nascimento(new Date());
		c2.setDoc_rg_insc_estadual("0");
		c2.setOperador_cadastro("central");
		c2.setSexo("MASCULINO");
		c2.setTratamento("Sr");
		
		em.persist(c2);
		
		
		
		Endereco end = new Endereco();
		end.setClientes(c2);
		end.setEndereco(endereco);
		end.setNumero(numero);
		end.setBairro(bairro);
		end.setCidade(cidade);
		end.setCep("00000000");
		end.setPais("Brasil");
		end.setReferencia("0");
		end.setUf("PE");
		end.setStatus("ATIVO");
		
		em.persist(end);
		
		
	}
	
	@Transactional
	public boolean desbloquear72horas(Integer cod_boleto, Integer codAcesso) {
		
		boolean c = checarSeBoletoPodeSerDesbloqueado(cod_boleto);
		if(c) {
			try{
				final AcessoCliente ac = em.find(AcessoCliente.class, codAcesso);
				ac.setDesbloqueio_tmp(new Date());
								
				AgendamentoBloqueioDesbloqueio agendBloqueioDesbloqueio = new AgendamentoBloqueioDesbloqueio();
				agendBloqueioDesbloqueio.setContrato(ac);
				agendBloqueioDesbloqueio.setData_cadastro(new Date());
				agendBloqueioDesbloqueio.setStatus("PENDENTE");
				agendBloqueioDesbloqueio.setUsuario("app");
				
				DateTime dt48 = new DateTime().plusDays(4);
				agendBloqueioDesbloqueio.setData_agendado(dt48.toDate());
				agendBloqueioDesbloqueio.setTipo("BLOQUEIO");
				
				HistoricoDesbloqueio72Horas histDesbloqueio = new HistoricoDesbloqueio72Horas();
				histDesbloqueio.setContrato_id(codAcesso);
				histDesbloqueio.setData(new Date());
				histDesbloqueio.setUsuario_id(248);
				histDesbloqueio.setBoleto(cod_boleto); 
				
				em.merge(agendBloqueioDesbloqueio);			
				em.merge(ac);
				em.persist(histDesbloqueio);
				
				em.persist(new AlteracoesContrato(null, "DESBLOQUEIO 72h", ac, 248, new Date()));				
				DesbloquearContrato(codAcesso);					
				
				return true;
			}catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}
	
	public boolean checarSeBoletoPodeSerDesbloqueado(Integer cod_boleto){
				
		Query q  = em.createQuery("select h from HistoricoDesbloqueio72Horas h where h.boleto=:b", HistoricoDesbloqueio72Horas.class);
		q.setParameter("b", cod_boleto);
		
		if(q.getResultList().size() > 0){
			return false;
		}
		
		return true;
	}
	
	public boolean DesbloquearContrato(Integer codContrato){
		
	
		AcessoCliente acesso = em.find(AcessoCliente.class,codContrato);			
	
			
		if(acesso != null 
				&& acesso.getStatus_2() != null
				&& acesso.getStatus_2().equals("BLOQUEADO")){
			
				//Atualiza Status do Acesso para Ativo
				acesso.setStatus_2("ATIVO");
				em.merge(acesso);
				
				//Remove marcações RadReply
				Query qrr = em.createQuery("select rr from RadReply rr where rr.username = :usuario and rr.attribute = 'Framed-Pool' and rr.value = 'BLOQUEADO'", RadReply.class);
				qrr.setParameter("usuario", acesso.getLogin());						
				if(qrr.getResultList().size() >0){
							
					List<RadReply> marcacoes = qrr.getResultList(); 
					for(RadReply rr:marcacoes){
						em.remove(rr);
					}
				}
				
				
				//possivelmente remover depois
				Query qrr2 = em.createQuery("select rr from RadReply rr where rr.username = :usuario and rr.attribute = 'Framed-Pool' and rr.value = 'BLOQUEADO_TOTAL'", RadReply.class);
				qrr2.setParameter("usuario", acesso.getLogin());						
				if(qrr2.getResultList().size() >0){
							
					List<RadReply> marcacoes2 = qrr2.getResultList(); 
					for(RadReply rr:marcacoes2){
						em.remove(rr);
					}
				}
				
				//Remove planos antigos
				Query qrr3 = em.createQuery("select rug from RadUserGgroup rug where rug.username = :usuario", RadUserGgroup.class);
				qrr3.setParameter("usuario", acesso.getLogin());
				if(qrr3.getResultList().size()>0){
					List<RadUserGgroup> marcacoes_planos_antigas = qrr3.getResultList();
					for (RadUserGgroup rug : marcacoes_planos_antigas) {
						em.remove(rug);
					}
				}
				
				//Cria planos originais novamente
				String groupName = acesso.getPlano().getContrato_acesso().getId().toString()+"_"+acesso.getPlano().getNome();
				em.persist(new RadUserGgroup(null, acesso.getLogin(), groupName, "1"));
										
								
				if(acesso.getEndereco_ip() != null && !acesso.getEndereco_ip().equals("")){
					em.persist(new RadReply(null, acesso.getLogin(), "Framed-IP-Address", "=", acesso.getEndereco_ip()));
				}
				
				if(acesso != null && acesso.getIttv_id() != null){
					try{
						String s = ittvDAO.atualizarStatus(acesso.getIttv_id(), "ACTIVE");
						System.out.println(s);
					}catch(Exception e){
						e.printStackTrace();
					}					
				}
				
				if(acesso.getBase().getTipo().equals("huawei")){
					huaweiUtil.desconectarCliente(acesso.getLogin());
				}
									
				return true;										
		}
		
		return false;
	}
	
	
	@Transactional
	public boolean cadastrarToken(Integer codContrato, String token){
		
		
	
		try {
			Query q = em.createQuery("select t from TokenClientes t where t.contrato=:c and t.token=:t", TokenClientes.class);
			q.setParameter("c", codContrato);
			q.setParameter("t", token);
			
			if(q.getResultList().size() == 0) {				
				em.persist(new TokenClientes(null, codContrato, token));
				return true;
			}
			
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	 
	
	}
	
}
