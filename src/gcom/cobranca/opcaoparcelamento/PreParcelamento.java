/**
 * 
 */

package gcom.cobranca.opcaoparcelamento;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.parcelamento.ParcelamentoSituacao;
import gcom.interceptor.ObjetoGcom;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bruno Ferreira dos Santos
 */
public class PreParcelamento
				extends ObjetoGcom {

	private Integer id;

	private CobrancaDocumento documentoCobranca;

	private Date preParcelamento;

	private ParcelamentoSituacao situacaoParcelamento;

	private Integer anoMesReferenciaFaturamento;

	private BigDecimal valorConta;

	private BigDecimal valorGuiaPapagamento;

	private BigDecimal valorServicosACobrar;

	private BigDecimal valorParcelamentosACobrar;

	private BigDecimal valorCreditoARealizar;

	private BigDecimal valorAtualizacaoMonetaria;

	private BigDecimal valorJurosMora;

	private BigDecimal valorMulta;

	private BigDecimal valorDebitoAtualizado;

	private BigDecimal valorDescontoAcrescimos;

	private BigDecimal valorDescontoAntiguidade;

	private BigDecimal valorDescontoInatividade;

	private Short indicadorRestabelecimento;

	private Short indicadorContasRevisao;

	private Short indicadorGuiasPagamento;

	private Short indicadorAcrescimosImpontualdade;

	private Short indicadorDebitoACobrar;

	private Short indicadorCreditoARealizar;

	private Date ultimaAlteracao;

	private Imovel imovel;

	private ImovelPerfil imovelPerfil;

	private RegistroAtendimento registroAtendimento;

	private Funcionario funcionario;

	private LigacaoAguaSituacao ligacaoAguaSituacao;

	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	private Localidade localidade;

	private Quadra quadra;

	private Integer codigoSetorComercial;

	private Integer numeroQuadra;

	private CobrancaForma cobrancaForma;

	private Short indicadorConfirmacaoParcelamento;

	private Cliente cliente;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public CobrancaDocumento getDocumentoCobranca(){

		return documentoCobranca;
	}

	public void setDocumentoCobranca(CobrancaDocumento documentoCobranca){

		this.documentoCobranca = documentoCobranca;
	}

	public Date getPreParcelamento(){

		return preParcelamento;
	}

	public void setPreParcelamento(Date preParcelamento){

		this.preParcelamento = preParcelamento;
	}

	public ParcelamentoSituacao getSituacaoParcelamento(){

		return situacaoParcelamento;
	}

	public void setSituacaoParcelamento(ParcelamentoSituacao situacaoParcelamento){

		this.situacaoParcelamento = situacaoParcelamento;
	}

	public Integer getAnoMesReferenciaFaturamento(){

		return anoMesReferenciaFaturamento;
	}

	public void setAnoMesReferenciaFaturamento(Integer anoMesReferenciaFaturamento){

		this.anoMesReferenciaFaturamento = anoMesReferenciaFaturamento;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public BigDecimal getValorGuiaPapagamento(){

		return valorGuiaPapagamento;
	}

	public void setValorGuiaPapagamento(BigDecimal valorGuiaPapagamento){

		this.valorGuiaPapagamento = valorGuiaPapagamento;
	}

	public BigDecimal getValorServicosACobrar(){

		return valorServicosACobrar;
	}

	public void setValorServicosACobrar(BigDecimal valorServicosACobrar){

		this.valorServicosACobrar = valorServicosACobrar;
	}

	public BigDecimal getValorParcelamentosACobrar(){

		return valorParcelamentosACobrar;
	}

	public void setValorParcelamentosACobrar(BigDecimal valorParcelamentosACobrar){

		this.valorParcelamentosACobrar = valorParcelamentosACobrar;
	}

	public BigDecimal getValorCreditoARealizar(){

		return valorCreditoARealizar;
	}

	public void setValorCreditoARealizar(BigDecimal valorCreditoARealizar){

		this.valorCreditoARealizar = valorCreditoARealizar;
	}

	public BigDecimal getValorAtualizacaoMonetaria(){

		return valorAtualizacaoMonetaria;
	}

	public void setValorAtualizacaoMonetaria(BigDecimal valorAtualizacaoMonetaria){

		this.valorAtualizacaoMonetaria = valorAtualizacaoMonetaria;
	}

	public BigDecimal getValorJurosMora(){

		return valorJurosMora;
	}

	public void setValorJurosMora(BigDecimal valorJurosMora){

		this.valorJurosMora = valorJurosMora;
	}

	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	public BigDecimal getValorDebitoAtualizado(){

		return valorDebitoAtualizado;
	}

	public void setValorDebitoAtualizado(BigDecimal valorDebitoAtualizado){

		this.valorDebitoAtualizado = valorDebitoAtualizado;
	}

	public BigDecimal getValorDescontoAcrescimos(){

		return valorDescontoAcrescimos;
	}

	public void setValorDescontoAcrescimos(BigDecimal valorDescontoAcrescimos){

		this.valorDescontoAcrescimos = valorDescontoAcrescimos;
	}

	public BigDecimal getValorDescontoAntiguidade(){

		return valorDescontoAntiguidade;
	}

	public void setValorDescontoAntiguidade(BigDecimal valorDescontoAntiguidade){

		this.valorDescontoAntiguidade = valorDescontoAntiguidade;
	}

	public BigDecimal getValorDescontoInatividade(){

		return valorDescontoInatividade;
	}

	public void setValorDescontoInatividade(BigDecimal valorDescontoInatividade){

		this.valorDescontoInatividade = valorDescontoInatividade;
	}

	public Short getIndicadorRestabelecimento(){

		return indicadorRestabelecimento;
	}

	public void setIndicadorRestabelecimento(Short indicadorRestabelecimento){

		this.indicadorRestabelecimento = indicadorRestabelecimento;
	}

	public Short getIndicadorContasRevisao(){

		return indicadorContasRevisao;
	}

	public void setIndicadorContasRevisao(Short indicadorContasRevisao){

		this.indicadorContasRevisao = indicadorContasRevisao;
	}

	public Short getIndicadorGuiasPagamento(){

		return indicadorGuiasPagamento;
	}

	public void setIndicadorGuiasPagamento(Short indicadorGuiasPagamento){

		this.indicadorGuiasPagamento = indicadorGuiasPagamento;
	}

	public Short getIndicadorAcrescimosImpontualdade(){

		return indicadorAcrescimosImpontualdade;
	}

	public void setIndicadorAcrescimosImpontualdade(Short indicadorAcrescimosImpontualdade){

		this.indicadorAcrescimosImpontualdade = indicadorAcrescimosImpontualdade;
	}

	public Short getIndicadorDebitoACobrar(){

		return indicadorDebitoACobrar;
	}

	public void setIndicadorDebitoACobrar(Short indicadorDebitoACobrar){

		this.indicadorDebitoACobrar = indicadorDebitoACobrar;
	}

	public Short getIndicadorCreditoARealizar(){

		return indicadorCreditoARealizar;
	}

	public void setIndicadorCreditoARealizar(Short indicadorCreditoARealizar){

		this.indicadorCreditoARealizar = indicadorCreditoARealizar;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public ImovelPerfil getImovelPerfil(){

		return imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public Funcionario getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public Quadra getQuadra(){

		return quadra;
	}

	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public CobrancaForma getCobrancaForma(){

		return cobrancaForma;
	}

	public void setCobrancaForma(CobrancaForma cobrancaForma){

		this.cobrancaForma = cobrancaForma;
	}

	public Short getIndicadorConfirmacaoParcelamento(){

		return indicadorConfirmacaoParcelamento;
	}

	public void setIndicadorConfirmacaoParcelamento(Short indicadorConfirmacaoParcelamento){

		this.indicadorConfirmacaoParcelamento = indicadorConfirmacaoParcelamento;
	}

	public Cliente getCliente(){

		return cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

}
