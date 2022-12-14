package br.com.digital.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;



@Entity
@Table(name="acesso_cliente")
@Cacheable(value=false)
public class AcessoCliente {
	
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=false)
	private Integer id;	
	@Column(name="CODIGO_CARTAO", nullable=true, unique=true)
	private String codigo_cartao;	
	@Column(name="EMPRESA_ID", nullable=false, unique=false)
	private Integer empresa_id;
	
	@OneToOne
	@JoinColumn(name="PLANOS_ACESSO_ID", nullable=false)
	private PlanoAcesso plano;	
	@OneToOne
	@JoinColumn(name="BASES_ID", nullable=true)
	private Concentrador base;
	@Column(name="MATERIAL_ACESSO_ID", nullable=true)
	private Integer material;	
	@OneToOne
	@JoinColumn(name="CONTRATOS_ACESSO_ID")
	private ContratosAcesso contrato;
	@Column(name="CLIENTES_ID")
	private Integer cliente;	
	@Column(name="FIADOR_ID", nullable=true)
	private Integer fiador;
	
	@OneToOne
	@JoinColumn(name="ENDERECO_ID", nullable=true)
	private Endereco endereco;	
	@Column(name="LOGIN", nullable=true, length=100)
	private String login;
	@Column(name="SENHA", nullable=true, length=100)
	private String senha;
	@Column(name="ENDERECO_IP", nullable=true, length=100)
	private String endereco_ip;
	@Column(name="ENDERECO_MAC", nullable=true, length=100)
	private String endereco_mac;		
	@Column(name="ONU_ID", nullable=true)
	private Integer onu;	
	@Column(name="ONU_SERIAL", nullable=true)
	private String onu_serial;
	@Column(name="GPON", nullable=true)
	private String gpon;	
	@Column(name="ULTIMO_ENDERECO_MAC", nullable=true, length=100)
	private String ultimo_endereco_mac;
	@Column(name="CARENCIA", nullable=false, length=3)
	private String carencia;
	@Column(name="DATA_VENC_CONTRATO", nullable=true)
	private Date data_venc_contrato;
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CADASTRO", nullable=false)
	private Date data_cadastro;
	@Column(name="DATA_RENOVACAO", nullable=true)
	private Date data_renovacao;
	@Column(name="STATUS_2", nullable=false, length=50)
	private String status_2;
	@Column(name="SIGNAL_STRENGTH", nullable=true)
	private String signal_strength;
	@Column(name="INTERFACE", nullable=true)
	private String interfaces;	
	@Column(name="DATA_INSTALACAO", nullable=false)
	private Date data_instalacao;	
	@Column(name="DATA_ALTERACAO_PLANO", nullable=true)
	private Date data_alteracao_plano;	
	@Column(name="REGIME", nullable=true)
	private String regime;	
	@Column(name="EMITIR_NFE", nullable=true)
	private String emitir_nfe;	
	@Column(name="CFOP_NFE", nullable=true)
	private Integer cfop_nfe;	
	@Column(name="EMITIR_NFE_C_BOLETO_ABERTO", nullable=true)
	private String emitir_nfe_c_boleto_aberto;	
	@Column(name="EMITIR_NFE_AUTOMATICO", nullable=true)
	private String emitir_nfe_automatico;	
	@Column(name="BENEFICIO_COMODATO")
	private boolean beneficio_comodato;	
	@Column(name="VALOR_BENEFICIO_ADESAO")
	private double valor_beneficio_adesao;	
	@Column(name="VALOR_BENEFICIO_COMODATO")
	private double valor_beneficio_comodato;
	@Column(name="ISENCAO_TAXA_INSTALACAO")
	private double isencao_taxa_instalacao;
	@Column(name="ISENCAO_PREST_SERV_MANUTENCAO")
	private double isencao_prest_serv_manutencao;
	@Column(name="SIGNAL_DB")
	private String sinal_db;	
	@Column(name="INSTALACAO_GRATIS")
	private String instalacao_gratis;	
	@Column(name="NAO_CONTROLA_VLR_PLANO")
	private boolean n_controla_vlr_plano;	
	@Column(name="TEM_PENDENCIA")
	private String tem_pendencia;
	@Column(name="ULTIMA_PENDENCIA")
	private String ultima_pendencia;	
	@Column(name="VALOR_ADESAO_POS_ENCERRAMENTO")
	private String valor_adesao_pos_encerramento;
	@Column(name="VALOR_EQUIPAMENTO_POS_ENCERRAMENTO")
	private String valor_equipamento_pos_encerramento;
	@Column(name="VALOR_INSTALACAO_POS_ENCERRAMENTO")
	private String valor_instalacao_pos_encerramento;
	@Column(name="VALOR_MULTA_POS_ENCERRAMENTO")
	private String valor_multa_pos_encerramento;
	@Column(name="VALOR_TOTAL_POS_ENCERRAMENTO")
	private String valor_total_pos_encerramento;	
	@Temporal(TemporalType.TIMESTAMP)
	private Date desbloqueio_tmp;	
	@Column(name="SWITH_ID", nullable=true)
	private Integer swith;	
	@Transient
	private String coluna;
	@Transient
	private Date coluna_date;
	@Transient
	private Long qtd;	
	@Column(name="ITTV_ID")
	private String ittv_id;	
	@Column(name="U_ITTV")
	private String u_ittv;	
	@Column(name="S_ITTV")
	private String s_ittv;	
	@Column(name="ARQUIVO_UPLOAD")
	private String arquivo_upload;	
	@Column(name="PREST_SERV_MANUTENCAO")
	private String prest_serv_manutecao;	
	@Column(name="PENDENCIA_UPLOAD")
	private boolean pendencia_upload;	
	@Column(name="VENDEDOR")
	private Integer vendedor;
	
	@Transient
	private List<ContasReceber> boletos;
	
	
	public AcessoCliente(String coluna, Long qtd) {		
		this.coluna = coluna;
		this.qtd = qtd;
	}
	
	public AcessoCliente(Date coluna_date, Long qtd) {		
		this.coluna_date = coluna_date;
		this.qtd = qtd;
	}
	
	public String getColuna() {
		return coluna;
	}
	public void setColuna(String coluna) {
		this.coluna = coluna;
	}
	public Date getColuna_date() {
		return coluna_date;
	}
	public void setColuna_date(Date coluna_date) {
		this.coluna_date = coluna_date;
	}
	public Long getQtd() {
		return qtd;
	}
	public void setQtd(Long qtd) {
		this.qtd = qtd;
	}
	public AcessoCliente(){
		
	}
	
	public Integer getSwith() {
		return swith;
	}

	public void setSwith(Integer swith) {
		this.swith = swith;
	}

	public AcessoCliente(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PlanoAcesso getPlano() {
		return plano;
	}

	public void setPlano(PlanoAcesso plano) {
		this.plano = plano;
	}

	public Concentrador getBase() {
		return base;
	}

	public void setBase(Concentrador base) {
		this.base = base;
	}

	public Integer getEmpresa_id() {
		return empresa_id;
	}

	public void setEmpresa_id(Integer empresa_id) {
		this.empresa_id = empresa_id;
	}

	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEndereco_ip() {
		return endereco_ip;
	}

	public void setEndereco_ip(String endereco_ip) {
		this.endereco_ip = endereco_ip;
	}

	public String getUltimo_endereco_mac() {
		return ultimo_endereco_mac;
	}

	public void setUltimo_endereco_mac(String ultimo_endereco_mac) {
		this.ultimo_endereco_mac = ultimo_endereco_mac;
	}

	public String getEndereco_mac() {
		return endereco_mac;
	}

	public void setEndereco_mac(String endereco_mac) {
		this.endereco_mac = endereco_mac;
	}

	public String getCarencia() {
		return carencia;
	}

	public void setCarencia(String carencia) {
		this.carencia = carencia;
	}

	public Date getData_venc_contrato() {
		return data_venc_contrato;
	}

	public void setData_venc_contrato(Date data_venc_contrato) {
		this.data_venc_contrato = data_venc_contrato;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Date getData_renovacao() {
		return data_renovacao;
	}

	public void setData_renovacao(Date data_renovacao) {
		this.data_renovacao = data_renovacao;
	}

	public String getStatus_2() {
		return status_2;
	}

	public void setStatus_2(String status_2) {
		this.status_2 = status_2;
	}

	public Integer getMaterial() {
		return material;
	}

	public void setMaterial(Integer material) {
		this.material = material;
	}

	public ContratosAcesso getContrato() {
		return contrato;
	}

	public void setContrato(ContratosAcesso contrato) {
		this.contrato = contrato;
	}

	public String getSignal_strength() {
		return signal_strength;
	}

	public void setSignal_strength(String signal_strength) {
		this.signal_strength = signal_strength;
	}

	public String getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}

	public Date getData_instalacao() {
		return data_instalacao;
	}

	public void setData_instalacao(Date data_instalacao) {
		this.data_instalacao = data_instalacao;
	}

	public Date getData_alteracao_plano() {
		return data_alteracao_plano;
	}

	public void setData_alteracao_plano(Date data_alteracao_plano) {
		this.data_alteracao_plano = data_alteracao_plano;
	}

	

	public Integer getFiador() {
		return fiador;
	}

	public void setFiador(Integer fiador) {
		this.fiador = fiador;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	

	public String getEmitir_nfe() {
		return emitir_nfe;
	}

	public void setEmitir_nfe(String emitir_nfe) {
		this.emitir_nfe = emitir_nfe;
	}

	public Integer getCfop_nfe() {
		return cfop_nfe;
	}

	public void setCfop_nfe(Integer cfop_nfe) {
		this.cfop_nfe = cfop_nfe;
	}

	public String getEmitir_nfe_c_boleto_aberto() {
		return emitir_nfe_c_boleto_aberto;
	}

	public void setEmitir_nfe_c_boleto_aberto(String emitir_nfe_c_boleto_aberto) {
		this.emitir_nfe_c_boleto_aberto = emitir_nfe_c_boleto_aberto;
	}

	public String getEmitir_nfe_automatico() {
		return emitir_nfe_automatico;
	}

	public void setEmitir_nfe_automatico(String emitir_nfe_automatico) {
		this.emitir_nfe_automatico = emitir_nfe_automatico;
	}

	public boolean isBeneficio_comodato() {
		return beneficio_comodato;
	}

	public void setBeneficio_comodato(boolean beneficio_comodato) {
		this.beneficio_comodato = beneficio_comodato;
	}

	public String getOnu_serial() {
		return onu_serial;
	}

	public void setOnu_serial(String onu_serial) {
		this.onu_serial = onu_serial;
	}

	
	
	public Integer getOnu() {
		return onu;
	}

	public void setOnu(Integer onu) {
		this.onu = onu;
	}

	public String getGpon() {
		return gpon;
	}

	public void setGpon(String gpon) {
		this.gpon = gpon;
	}

	public double getValor_beneficio_adesao() {
		return valor_beneficio_adesao;
	}

	public void setValor_beneficio_adesao(double valor_beneficio_adesao) {
		this.valor_beneficio_adesao = valor_beneficio_adesao;
	}

	public double getValor_beneficio_comodato() {
		return valor_beneficio_comodato;
	}

	public void setValor_beneficio_comodato(double valor_beneficio_comodato) {
		this.valor_beneficio_comodato = valor_beneficio_comodato;
	}

	public String getSinal_db() {
		return sinal_db;
	}

	public void setSinal_db(String sinal_db) {
		this.sinal_db = sinal_db;
	}

	public String getInstalacao_gratis() {
		return instalacao_gratis;
	}

	public void setInstalacao_gratis(String instalacao_gratis) {
		this.instalacao_gratis = instalacao_gratis;
	}

	public boolean isN_controla_vlr_plano() {
		return n_controla_vlr_plano;
	}

	public void setN_controla_vlr_plano(boolean n_controla_vlr_plano) {
		this.n_controla_vlr_plano = n_controla_vlr_plano;
	}

	public String getTem_pendencia() {
		return tem_pendencia;
	}

	public void setTem_pendencia(String tem_pendencia) {
		this.tem_pendencia = tem_pendencia;
	}

	public String getUltima_pendencia() {
		return ultima_pendencia;
	}

	public void setUltima_pendencia(String ultima_pendencia) {
		this.ultima_pendencia = ultima_pendencia;
	}

	public double getIsencao_taxa_instalacao() {
		return isencao_taxa_instalacao;
	}

	public void setIsencao_taxa_instalacao(double isencao_taxa_instalacao) {
		this.isencao_taxa_instalacao = isencao_taxa_instalacao;
	}

	public Date getDesbloqueio_tmp() {
		return desbloqueio_tmp;
	}

	public void setDesbloqueio_tmp(Date desbloqueio_tmp) {
		this.desbloqueio_tmp = desbloqueio_tmp;
	}


	public String getValor_multa_pos_encerramento() {
		return valor_multa_pos_encerramento;
	}

	public void setValor_multa_pos_encerramento(String valor_multa_pos_encerramento) {
		this.valor_multa_pos_encerramento = valor_multa_pos_encerramento;
	}

	public String getValor_adesao_pos_encerramento() {
		return valor_adesao_pos_encerramento;
	}

	public void setValor_adesao_pos_encerramento(
			String valor_adesao_pos_encerramento) {
		this.valor_adesao_pos_encerramento = valor_adesao_pos_encerramento;
	}

	public String getValor_equipamento_pos_encerramento() {
		return valor_equipamento_pos_encerramento;
	}

	public void setValor_equipamento_pos_encerramento(
			String valor_equipamento_pos_encerramento) {
		this.valor_equipamento_pos_encerramento = valor_equipamento_pos_encerramento;
	}

	public String getValor_instalacao_pos_encerramento() {
		return valor_instalacao_pos_encerramento;
	}

	public void setValor_instalacao_pos_encerramento(
			String valor_instalacao_pos_encerramento) {
		this.valor_instalacao_pos_encerramento = valor_instalacao_pos_encerramento;
	}

	public String getValor_total_pos_encerramento() {
		return valor_total_pos_encerramento;
	}

	public void setValor_total_pos_encerramento(String valor_total_pos_encerramento) {
		this.valor_total_pos_encerramento = valor_total_pos_encerramento;
	}

	public String getIttv_id() {
		return ittv_id;
	}

	public void setIttv_id(String ittv_id) {
		this.ittv_id = ittv_id;
	}

	public String getU_ittv() {
		return u_ittv;
	}

	public void setU_ittv(String u_ittv) {
		this.u_ittv = u_ittv;
	}

	public String getS_ittv() {
		return s_ittv;
	}

	public void setS_ittv(String s_ittv) {
		this.s_ittv = s_ittv;
	}

	public String getArquivo_upload() {
		return arquivo_upload;
	}

	public void setArquivo_upload(String arquivo_upload) {
		this.arquivo_upload = arquivo_upload;
	}

	public String getCodigo_cartao() {
		return codigo_cartao;
	}

	public void setCodigo_cartao(String codigo_cartao) {
		this.codigo_cartao = codigo_cartao;
	}

	public String getPrest_serv_manutecao() {
		return prest_serv_manutecao;
	}

	public void setPrest_serv_manutecao(String prest_serv_manutecao) {
		this.prest_serv_manutecao = prest_serv_manutecao;
	}

	public double getIsencao_prest_serv_manutencao() {
		return isencao_prest_serv_manutencao;
	}

	public void setIsencao_prest_serv_manutencao(
			double isencao_prest_serv_manutencao) {
		this.isencao_prest_serv_manutencao = isencao_prest_serv_manutencao;
	}

	public boolean isPendencia_upload() {
		return pendencia_upload;
	}

	public void setPendencia_upload(boolean pendencia_upload) {
		this.pendencia_upload = pendencia_upload;
	}

	public Integer getVendedor() {
		return vendedor;
	}

	public void setVendedor(Integer vendedor) {
		this.vendedor = vendedor;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public List<ContasReceber> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<ContasReceber> boletos) {
		
		
		
		this.boletos = boletos;
		
		
		
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	
}
