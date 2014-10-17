
package gcom.atendimentopublico.ordemservico.bean;

import gcom.relatorio.ordemservico.DadosUltimosConsumosHelper;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Carlos Chrystian
 * @date 10/04/2013
 */
public class OrdemServicoFiscalizacaoModelo2Helper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	// Dados Gerais
	private String numeroPagina;

	private Integer cobrancaSituacao;

	private String local;

	private String matricula;

	private String setor;

	private String quadra;

	private String lote;

	private String sublote;

	private String controle;

	private String consumidor;

	private String dataEmissao;

	private String enderecoLocalidade;

	private String clienteCPFCNPJ;

	private String clienteRG;

	// Conferências Cadastrais
	private String clienteNome;

	private String clienteTelefone;

	private String economiasQuantidade;

	private String economiasTipo;

	// Dados Hidrômetro
	private String numeroHidrometro;

	private String leituraAtualHidrometro;

	private String numeroLacre;

	private String situacaoLigacaoAgua;

	private String situacaoLigacaoEsgoto;

	private String dataCorte;

	private String dataSuspensao;

	private String quantidadeContasVencidas;

	private String valorContasVencidas;

	private Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos1;

	private Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos2;

	private Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos3;

	private Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos4;

	public String getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(String numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public Integer getCobrancaSituacao(){

		return cobrancaSituacao;
	}

	public void setCobrancaSituacao(Integer cobrancaSituacao){

		this.cobrancaSituacao = cobrancaSituacao;
	}

	public String getLocal(){

		return local;
	}

	public void setLocal(String local){

		this.local = local;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getSetor(){

		return setor;
	}

	public void setSetor(String setor){

		this.setor = setor;
	}

	public String getQuadra(){

		return quadra;
	}

	public void setQuadra(String quadra){

		this.quadra = quadra;
	}

	public String getLote(){

		return lote;
	}

	public void setLote(String lote){

		this.lote = lote;
	}

	public String getSublote(){

		return sublote;
	}

	public void setSublote(String sublote){

		this.sublote = sublote;
	}

	public String getControle(){

		return controle;
	}

	public void setControle(String controle){

		this.controle = controle;
	}

	public String getConsumidor(){

		return consumidor;
	}

	public void setConsumidor(String consumidor){

		this.consumidor = consumidor;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getEnderecoLocalidade(){

		return enderecoLocalidade;
	}

	public void setEnderecoLocalidade(String enderecoLocalidade){

		this.enderecoLocalidade = enderecoLocalidade;
	}

	public String getClienteCPFCNPJ(){

		return clienteCPFCNPJ;
	}

	public void setClienteCPFCNPJ(String clienteCPFCNPJ){

		this.clienteCPFCNPJ = clienteCPFCNPJ;
	}

	public String getClienteRG(){

		return clienteRG;
	}

	public void setClienteRG(String clienteRG){

		this.clienteRG = clienteRG;
	}

	public String getClienteNome(){

		return clienteNome;
	}

	public void setClienteNome(String clienteNome){

		this.clienteNome = clienteNome;
	}

	public String getClienteTelefone(){

		return clienteTelefone;
	}

	public void setClienteTelefone(String clienteTelefone){

		this.clienteTelefone = clienteTelefone;
	}

	public String getEconomiasQuantidade(){

		return economiasQuantidade;
	}

	public void setEconomiasQuantidade(String economiasQuantidade){

		this.economiasQuantidade = economiasQuantidade;
	}

	public String getEconomiasTipo(){

		return economiasTipo;
	}

	public void setEconomiasTipo(String economiasTipo){

		this.economiasTipo = economiasTipo;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getLeituraAtualHidrometro(){

		return leituraAtualHidrometro;
	}

	public void setLeituraAtualHidrometro(String leituraAtualHidrometro){

		this.leituraAtualHidrometro = leituraAtualHidrometro;
	}

	public String getNumeroLacre(){

		return numeroLacre;
	}

	public void setNumeroLacre(String numeroLacre){

		this.numeroLacre = numeroLacre;
	}

	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	public String getDataCorte(){

		return dataCorte;
	}

	public void setDataCorte(String dataCorte){

		this.dataCorte = dataCorte;
	}

	public String getDataSuspensao(){

		return dataSuspensao;
	}

	public void setDataSuspensao(String dataSuspensao){

		this.dataSuspensao = dataSuspensao;
	}

	public String getQuantidadeContasVencidas(){

		return quantidadeContasVencidas;
	}

	public void setQuantidadeContasVencidas(String quantidadeContasVencidas){

		this.quantidadeContasVencidas = quantidadeContasVencidas;
	}

	public String getValorContasVencidas(){

		return valorContasVencidas;
	}

	public void setValorContasVencidas(String valorContasVencidas){

		this.valorContasVencidas = valorContasVencidas;
	}

	public Collection<DadosUltimosConsumosHelper> getDadosUltimosConsumos1(){

		return dadosUltimosConsumos1;
	}

	public void setDadosUltimosConsumos1(Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos1){

		this.dadosUltimosConsumos1 = dadosUltimosConsumos1;
	}

	public Collection<DadosUltimosConsumosHelper> getDadosUltimosConsumos2(){

		return dadosUltimosConsumos2;
	}

	public void setDadosUltimosConsumos2(Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos2){

		this.dadosUltimosConsumos2 = dadosUltimosConsumos2;
	}

	public Collection<DadosUltimosConsumosHelper> getDadosUltimosConsumos3(){

		return dadosUltimosConsumos3;
	}

	public void setDadosUltimosConsumos3(Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos3){

		this.dadosUltimosConsumos3 = dadosUltimosConsumos3;
	}

	public Collection<DadosUltimosConsumosHelper> getDadosUltimosConsumos4(){

		return dadosUltimosConsumos4;
	}

	public void setDadosUltimosConsumos4(Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos4){

		this.dadosUltimosConsumos4 = dadosUltimosConsumos4;
	}

}
