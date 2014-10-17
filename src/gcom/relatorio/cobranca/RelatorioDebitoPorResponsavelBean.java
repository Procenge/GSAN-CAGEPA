
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioDebitoPorResponsavelBean
				implements RelatorioBean {

	private String idResponsavel;

	private String nomeResponsavel;

	private String enderecoResponsavel;

	private String tipoResponsavel;

	private Integer matricula;

	private String inscricao;

	private String idLigacaoAguaSituacao;

	private String idLigacaoEsgotoSituacao;

	private String clienteUsuario;

	private String enderecoImovel;

	private String referenciaDebito;

	private String dataVencimento;

	private Integer consumoAgua;

	private Integer consumoEsgoto;

	private BigDecimal valorNominalConsumidor;

	private BigDecimal valorCorrecaoConsumidor;

	private BigDecimal valorJurosConsumidor;

	private BigDecimal valorMultaConsumidor;

	private BigDecimal valorTotalConsumidor;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLocalidade;

	private JRBeanCollectionDataSource beansTotalizadores;

	private ArrayList arrayRelatorioDebitoPorResponsavelDetailBean;

	private String esferaPoder;

	public String getIdResponsavel(){

		return idResponsavel;
	}

	public void setIdResponsavel(String idResponsavel){

		this.idResponsavel = idResponsavel;
	}

	public String getNomeResponsavel(){

		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel){

		this.nomeResponsavel = nomeResponsavel;
	}

	public String getEnderecoResponsavel(){

		return enderecoResponsavel;
	}

	public void setEnderecoResponsavel(String enderecoResponsavel){

		this.enderecoResponsavel = enderecoResponsavel;
	}

	public String getTipoResponsavel(){

		return tipoResponsavel;
	}

	public void setTipoResponsavel(String tipoResponsavel){

		this.tipoResponsavel = tipoResponsavel;
	}

	public Integer getMatricula(){

		return matricula;
	}

	public void setMatricula(Integer matricula){

		this.matricula = matricula;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(String idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public String getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(String idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public String getClienteUsuario(){

		return clienteUsuario;
	}

	public void setClienteUsuario(String clienteUsuario){

		this.clienteUsuario = clienteUsuario;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getReferenciaDebito(){

		return referenciaDebito;
	}

	public void setReferenciaDebito(String referenciaDebito){

		this.referenciaDebito = referenciaDebito;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public Integer getConsumoAgua(){

		return consumoAgua;
	}

	public void setConsumoAgua(Integer consumoAgua){

		this.consumoAgua = consumoAgua;
	}

	public Integer getConsumoEsgoto(){

		return consumoEsgoto;
	}

	public void setConsumoEsgoto(Integer consumoEsgoto){

		this.consumoEsgoto = consumoEsgoto;
	}

	public BigDecimal getValorNominalConsumidor(){

		return valorNominalConsumidor;
	}

	public void setValorNominalConsumidor(BigDecimal valorNominalConsumidor){

		this.valorNominalConsumidor = valorNominalConsumidor;
	}

	public BigDecimal getValorCorrecaoConsumidor(){

		return valorCorrecaoConsumidor;
	}

	public void setValorCorrecaoConsumidor(BigDecimal valorCorrecaoConsumidor){

		this.valorCorrecaoConsumidor = valorCorrecaoConsumidor;
	}

	public BigDecimal getValorJurosConsumidor(){

		return valorJurosConsumidor;
	}

	public void setValorJurosConsumidor(BigDecimal valorJurosConsumidor){

		this.valorJurosConsumidor = valorJurosConsumidor;
	}

	public BigDecimal getValorMultaConsumidor(){

		return valorMultaConsumidor;
	}

	public void setValorMultaConsumidor(BigDecimal valorMultaConsumidor){

		this.valorMultaConsumidor = valorMultaConsumidor;
	}

	public BigDecimal getValorTotalConsumidor(){

		return valorTotalConsumidor;
	}

	public void setValorTotalConsumidor(BigDecimal valorTotalConsumidor){

		this.valorTotalConsumidor = valorTotalConsumidor;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public JRBeanCollectionDataSource getBeansTotalizadores(){

		return beansTotalizadores;
	}

	public void setBeansTotalizadores(JRBeanCollectionDataSource beansTotalizadores){

		this.beansTotalizadores = beansTotalizadores;
	}

	public ArrayList getArrayRelatorioDebitoPorResponsavelDetailBean(){

		return arrayRelatorioDebitoPorResponsavelDetailBean;
	}

	public void setArrayRelatorioDebitoPorResponsavelDetailBean(ArrayList arrayRelatorioDebitoPorResponsavelDetailBean){

		this.arrayRelatorioDebitoPorResponsavelDetailBean = arrayRelatorioDebitoPorResponsavelDetailBean;
	}

	public void setarBeansTotalizadores(Collection colecaoDetail){

		this.arrayRelatorioDebitoPorResponsavelDetailBean = new ArrayList();
		this.arrayRelatorioDebitoPorResponsavelDetailBean.addAll(colecaoDetail);
		this.beansTotalizadores = new JRBeanCollectionDataSource(this.arrayRelatorioDebitoPorResponsavelDetailBean);
	}

	public String getEsferaPoder(){

		return esferaPoder;
	}

	public void setEsferaPoder(String esferaPoder){

		this.esferaPoder = esferaPoder;
	}

}
