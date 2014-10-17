
package gcom.relatorio.cobranca;

import gcom.cobranca.bean.EmitirCartaOpcaoParcelamentoDetailHelper;
import gcom.cobranca.bean.EmitirCartaOpcaoParcelamentoHelper;
import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

//UC01101 - Emitir Carta com Opção de parcelamento
public class RelatorioCartaOpcaoParcelamentoBean
				implements RelatorioBean {

	private String matricula;

	private String nomeCliente;

	private String textoNomeEMatriculaCliente;

	private String inscricao;

	private String roteiro;

	private String Hm;

	private String endereco;

	private String bairro;

	private String saldoPrincipal;

	private String juros;

	private String multa;

	private String dividaTotal;

	private List<RelatorioCartaOpcaoParcelamentoDetailBean> opcoesDeParcelamento;

	private ArrayList arrayRelatorioCartaOpcaoParcelamentoDetailBean;

	private JRBeanCollectionDataSource arrayJRDetail;

	public RelatorioCartaOpcaoParcelamentoBean(EmitirCartaOpcaoParcelamentoHelper emitirCartaOpcaoParcelamentoHelper) {

		if(emitirCartaOpcaoParcelamentoHelper != null){
			this.matricula = emitirCartaOpcaoParcelamentoHelper.getMatricula();
			this.nomeCliente = emitirCartaOpcaoParcelamentoHelper.getNomeCliente();
			this.textoNomeEMatriculaCliente = emitirCartaOpcaoParcelamentoHelper.getTextoNomeEMatriculaCliente();
			this.inscricao = emitirCartaOpcaoParcelamentoHelper.getInscricao();
			this.roteiro = emitirCartaOpcaoParcelamentoHelper.getRoteiro();
			this.Hm = emitirCartaOpcaoParcelamentoHelper.getHm();
			this.endereco = emitirCartaOpcaoParcelamentoHelper.getEndereco();
			this.bairro = emitirCartaOpcaoParcelamentoHelper.getBairro();
			this.saldoPrincipal = emitirCartaOpcaoParcelamentoHelper.getSaldoPrincipal();
			this.juros = emitirCartaOpcaoParcelamentoHelper.getJuros();
			this.multa = emitirCartaOpcaoParcelamentoHelper.getMulta();
			this.dividaTotal = emitirCartaOpcaoParcelamentoHelper.getDividaTotal();
			this.opcoesDeParcelamento = gerarDetail(emitirCartaOpcaoParcelamentoHelper.getOpcoesDeParcelamento());

			this.arrayRelatorioCartaOpcaoParcelamentoDetailBean = new ArrayList();
			this.arrayRelatorioCartaOpcaoParcelamentoDetailBean.addAll(opcoesDeParcelamento);
			this.arrayJRDetail = new JRBeanCollectionDataSource(this.arrayRelatorioCartaOpcaoParcelamentoDetailBean);
		}
	}

	private List<RelatorioCartaOpcaoParcelamentoDetailBean> gerarDetail(
					List<EmitirCartaOpcaoParcelamentoDetailHelper> opcoesParcelamentoDetailHelper){

		List<RelatorioCartaOpcaoParcelamentoDetailBean> opcoesParcelamentoDetailBean = new ArrayList<RelatorioCartaOpcaoParcelamentoDetailBean>();

		if(opcoesParcelamentoDetailHelper != null && opcoesParcelamentoDetailHelper.size() >= 1){
			RelatorioCartaOpcaoParcelamentoDetailBean detailBean = null;
			for(Iterator iterator = opcoesParcelamentoDetailHelper.iterator(); iterator.hasNext();){
				EmitirCartaOpcaoParcelamentoDetailHelper detailHelper = (EmitirCartaOpcaoParcelamentoDetailHelper) iterator.next();
				detailBean = new RelatorioCartaOpcaoParcelamentoDetailBean();

				detailBean.setNumeroOpcao(detailHelper.getNumeroOpcao());
				detailBean.setTextoValorPrimeiraParcela(detailHelper.getTextoValorPrimeiraParcela());
				detailBean.setDesconto(detailHelper.getDesconto());
				detailBean.setaPagar(detailHelper.getaPagar());
				detailBean.setTextoPropostoNegociacao(detailHelper.getTextoPropostoNegociacao());
				detailBean.setCondicoesPagamento(detailHelper.getCondicoesPagamento());
				detailBean.setRepresentacaoNumericaCodBarraFormatada(detailHelper.getRepresentacaoNumericaCodBarraFormatada());
				detailBean.setRepresentacaoNumericaCodBarraSemDigito(detailHelper.getRepresentacaoNumericaCodBarraSemDigito());

				opcoesParcelamentoDetailBean.add(detailBean);
			}
		}

		return opcoesParcelamentoDetailBean;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public List<RelatorioCartaOpcaoParcelamentoDetailBean> getOpcoesDeParcelamento(){

		return opcoesDeParcelamento;
	}

	public void setOpcoesDeParcelamento(List<RelatorioCartaOpcaoParcelamentoDetailBean> opcoesDeParcelamento){

		this.opcoesDeParcelamento = opcoesDeParcelamento;
	}

	public ArrayList getArrayRelatorioCartaOpcaoParcelamentoDetailBean(){

		return arrayRelatorioCartaOpcaoParcelamentoDetailBean;
	}

	public void setArrayRelatorioCartaOpcaoParcelamentoDetailBean(ArrayList arrayRelatorioCartaOpcaoParcelamentoDetailBean){

		this.arrayRelatorioCartaOpcaoParcelamentoDetailBean = arrayRelatorioCartaOpcaoParcelamentoDetailBean;
	}

	public JRBeanCollectionDataSource getArrayJRDetail(){

		return arrayJRDetail;
	}

	public void setArrayJRDetail(JRBeanCollectionDataSource arrayJRDetail){

		this.arrayJRDetail = arrayJRDetail;
	}

	public String getTextoNomeEMatriculaCliente(){

		return textoNomeEMatriculaCliente;
	}

	public void setTextoNomeEMatriculaCliente(String textoNomeEMatriculaCliente){

		this.textoNomeEMatriculaCliente = textoNomeEMatriculaCliente;
	}

	public String getSaldoPrincipal(){

		return saldoPrincipal;
	}

	public void setSaldoPrincipal(String saldoPrincipal){

		this.saldoPrincipal = saldoPrincipal;
	}

	public String getJuros(){

		return juros;
	}

	public void setJuros(String juros){

		this.juros = juros;
	}

	public String getMulta(){

		return multa;
	}

	public void setMulta(String multa){

		this.multa = multa;
	}

	public String getDividaTotal(){

		return dividaTotal;
	}

	public void setDividaTotal(String dividaTotal){

		this.dividaTotal = dividaTotal;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getRoteiro(){

		return roteiro;
	}

	public void setRoteiro(String roteiro){

		this.roteiro = roteiro;
	}

	public String getHm(){

		return Hm;
	}

	public void setHm(String hm){

		Hm = hm;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

}
