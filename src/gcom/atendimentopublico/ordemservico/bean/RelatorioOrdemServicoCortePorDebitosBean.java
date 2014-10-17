
package gcom.atendimentopublico.ordemservico.bean;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author isilva
 */
public class RelatorioOrdemServicoCortePorDebitosBean
				extends RelatorioOrdemServicoEstruturaBean {

	private JRBeanCollectionDataSource beansDetalheCortePorDebitos;

	private ArrayList arrayRelatorioOrdemServicoCortePorDebitosDetailBean;

	// Dados do Débitos do Imóvel
	private String qtdeDebitosVencidos;

	private String valorDebitosVencidos;

	// Retirar Dados do Hidrômetro
	private String numeroHidrometro;

	private String marcaHidrometro;

	private String tipoHidrometro;

	private String dataInstalacaoHidrometro;

	private String localInstalacaoHidrometro;

	private String capacidadeHidrometro;

	private String tipoProtecaoHidrometro;

	private String diametroHidrometro;

	// Dados do Corte

	private String dataCorte;

	private String numeroSeloCorte;

	private String numeroLeituraCorte;

	private String idDescricaoCorteTipo;

	private String qtdEconomiasResidencial;

	public JRBeanCollectionDataSource getBeansDetalheCortePorDebitos(){

		return beansDetalheCortePorDebitos;
	}

	public void setBeansDetalheCortePorDebitos(JRBeanCollectionDataSource beansDetalheCortePorDebitos){

		this.beansDetalheCortePorDebitos = beansDetalheCortePorDebitos;
	}

	public ArrayList getArrayRelatorioOrdemServicoCortePorDebitosDetailBean(){

		return arrayRelatorioOrdemServicoCortePorDebitosDetailBean;
	}

	public void setArrayRelatorioOrdemServicoCortePorDebitosDetailBean(ArrayList arrayRelatorioOrdemServicoCortePorDebitosDetailBean){

		this.arrayRelatorioOrdemServicoCortePorDebitosDetailBean = arrayRelatorioOrdemServicoCortePorDebitosDetailBean;
	}

	public String getQtdeDebitosVencidos(){

		return qtdeDebitosVencidos;
	}

	public void setQtdeDebitosVencidos(String qtdeDebitosVencidos){

		this.qtdeDebitosVencidos = qtdeDebitosVencidos;
	}

	public String getValorDebitosVencidos(){

		return valorDebitosVencidos;
	}

	public void setValorDebitosVencidos(String valorDebitosVencidos){

		this.valorDebitosVencidos = valorDebitosVencidos;
	}

	public String getMarcaHidrometro(){

		return marcaHidrometro;
	}

	public void setMarcaHidrometro(String marcaHidrometro){

		this.marcaHidrometro = marcaHidrometro;
	}

	public String getTipoHidrometro(){

		return tipoHidrometro;
	}

	public void setTipoHidrometro(String tipoHidrometro){

		this.tipoHidrometro = tipoHidrometro;
	}

	public String getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	public void setDataInstalacaoHidrometro(String dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	public String getLocalInstalacaoHidrometro(){

		return localInstalacaoHidrometro;
	}

	public void setLocalInstalacaoHidrometro(String localInstalacaoHidrometro){

		this.localInstalacaoHidrometro = localInstalacaoHidrometro;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	public String getTipoProtecaoHidrometro(){

		return tipoProtecaoHidrometro;
	}

	public void setTipoProtecaoHidrometro(String tipoProtecaoHidrometro){

		this.tipoProtecaoHidrometro = tipoProtecaoHidrometro;
	}

	public String getDiametroHidrometro(){

		return diametroHidrometro;
	}

	public void setDiametroHidrometro(String diametroHidrometro){

		this.diametroHidrometro = diametroHidrometro;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getDataCorte(){

		return dataCorte;
	}

	public void setDataCorte(String dataCorte){

		this.dataCorte = dataCorte;
	}

	public String getNumeroSeloCorte(){

		return numeroSeloCorte;
	}

	public void setNumeroSeloCorte(String numeroSeloCorte){

		this.numeroSeloCorte = numeroSeloCorte;
	}

	public String getNumeroLeituraCorte(){

		return numeroLeituraCorte;
	}

	public void setNumeroLeituraCorte(String numeroLeituraCorte){

		this.numeroLeituraCorte = numeroLeituraCorte;
	}

	public String getIdDescricaoCorteTipo(){

		return idDescricaoCorteTipo;
	}

	public void setIdDescricaoCorteTipo(String idDescricaoCorteTipo){

		this.idDescricaoCorteTipo = idDescricaoCorteTipo;
	}

	public String getQtdEconomiasResidencial(){

		return qtdEconomiasResidencial;
	}

	public void setQtdEconomiasResidencial(String qtdEconomiasResidencial){

		this.qtdEconomiasResidencial = qtdEconomiasResidencial;
	}

	public void setarBeansDetalheCortePorDebitos(Collection colecaoDetail){

		this.arrayRelatorioOrdemServicoCortePorDebitosDetailBean = new ArrayList();
		this.arrayRelatorioOrdemServicoCortePorDebitosDetailBean.addAll(colecaoDetail);
		this.beansDetalheCortePorDebitos = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemServicoCortePorDebitosDetailBean);
	}

}
