package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioRemuneracaoCobrancaAdministrativaModelo2Bean
				implements RelatorioBean {

	private String idEmpresa;

	private String descricaoEmpresa;

	private String referenciaArrecadacao;

	private String idLote;

	// private String dataPagamento;
	//
	// private String matricula;
	//
	// private String cliente;
	//
	// private String cpfCnpj;

	// private String conta;

	// private String guiaPrestacao;

	// private String debito;

	// private String valorArrecadadoLote;

	private String valorArrecadadoLoteAcumulado;

	// private String valorBaseRemuneracao;

	private String valorRemuneracao;

	private String percentualDesempenho;

	private String percentualRemuneracao;

	private String valorArrecadadoLoteLote;

	private String valorBaseRemuneracaoLote;

	private String valorRemuneracaoLote;

	private String valorLote;

	private JRBeanCollectionDataSource arrayJRDetail;

	private ArrayList arrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean;

	public RelatorioRemuneracaoCobrancaAdministrativaModelo2Bean() {

		arrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean = new ArrayList();
	}

	public JRBeanCollectionDataSource getArrayJRDetail(){

		return arrayJRDetail;
	}

	public void setArrayJRDetail(JRBeanCollectionDataSource arrayJRDetail){

		this.arrayJRDetail = arrayJRDetail;
	}

	public ArrayList getArrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean(){

		return arrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean;
	}

	public void setArrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean(
					ArrayList arrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean){

		this.arrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean = arrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean;
	}

	public String getIdEmpresa(){

		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	public String getDescricaoEmpresa(){

		return descricaoEmpresa;
	}

	public void setDescricaoEmpresa(String descricaoEmpresa){

		this.descricaoEmpresa = descricaoEmpresa;
	}

	public String getReferenciaArrecadacao(){

		return referenciaArrecadacao;
	}

	public void setReferenciaArrecadacao(String referenciaArrecadacao){

		this.referenciaArrecadacao = referenciaArrecadacao;
	}

	public String getIdLote(){

		return idLote;
	}

	public void setIdLote(String idLote){

		this.idLote = idLote;
	}

	public String getDescricaoTituloLote(){

		return descricaoTituloLote;
	}

	public void setDescricaoTituloLote(String descricaoTituloLote){

		this.descricaoTituloLote = descricaoTituloLote;
	}

	private String descricaoTituloLote;

	public String getValorRemuneracaoLote(){

		return valorRemuneracaoLote;
	}

	public void setValorRemuneracaoLote(String valorRemuneracaoLote){

		this.valorRemuneracaoLote = valorRemuneracaoLote;
	}

	public String getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(String valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public String getValorArrecadadoLoteAcumulado(){

		return valorArrecadadoLoteAcumulado;
	}

	public void setValorArrecadadoLoteAcumulado(String valorArrecadadoLoteAcumulado){

		this.valorArrecadadoLoteAcumulado = valorArrecadadoLoteAcumulado;
	}

	public String getPercentualDesempenho(){

		return percentualDesempenho;
	}

	public void setPercentualDesempenho(String percentualDesempenho){

		this.percentualDesempenho = percentualDesempenho;
	}

	public String getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(String percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public String getValorArrecadadoLoteLote(){

		return valorArrecadadoLoteLote;
	}

	public void setValorArrecadadoLoteLote(String valorArrecadadoLoteLote){

		this.valorArrecadadoLoteLote = valorArrecadadoLoteLote;
	}

	public String getValorBaseRemuneracaoLote(){

		return valorBaseRemuneracaoLote;
	}

	public void setValorBaseRemuneracaoLote(String valorBaseRemuneracaoLote){

		this.valorBaseRemuneracaoLote = valorBaseRemuneracaoLote;
	}

	public void inicializarArrayJRDetail(ArrayList arrayList){

		arrayJRDetail = new JRBeanCollectionDataSource(arrayList);
	}

	public String getValorLote(){

		return valorLote;
	}

	public void setValorLote(String valorLote){

		this.valorLote = valorLote;
	}

}
