/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.micromedicao.Rota;
import gcom.micromedicao.bean.AnaliseConsumoRelatorioOSHelper;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author isilva
 */
public class OSRelatorioEstruturaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idOrdemServico;

	private Date dataGeracao;

	private String inscricaoImovel;

	private Integer idImovel;

	private String descricaoAbreviadaCategoria;

	private String descricaoCategoria;

	private int quantidadeEconomias;

	private String unidadeGeracao;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private Integer esgotoFixo;

	private String pavimentoRua;

	private String pavimentoCalcada;

	private String meio;

	private String nomeAtendente;

	private Integer idAtendente;

	private String endereco;

	private String pontoReferencia;

	private String telefone;

	private Integer idServicoSolicitado;

	private String descricaoServicoSolicitado;

	private String valorServicoSolicitado;

	private String localOcorrencia;

	private Date previsao;

	private Integer idRA;

	private String observacaoRA;

	private String observacaoOS;

	private Integer idSolicitante;

	private String nomeSolicitante;

	private Integer idUnidade;

	private String descricaoUnidade;

	private String especificao;

	private Short tempoMedioExecucao;

	private String origem;

	private Integer idLocalidade;

	private Integer sequencialRota;

	private Short codigoRota;

	private Integer idServicoTipoReferencia;

	private String descricaoServicoTipoReferencia;

	private AnaliseConsumoRelatorioOSHelper analiseConsumoRelatorioOSHelper;

	private HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper;

	private Date dataHoraSolicitacao;

	private Integer idSolicitacao;

	private String descricaoSolicitacao;

	private Date dataRoteiroProgramacao;

	private Integer idEquipe;

	private String nomeEquipe;

	private String codigoDescricaoBairro;

	/**
	 * Este construtor é usado para auxiliar na consulta e montagem do relatório.
	 * 
	 * @author isilva
	 * @param dataGeracao
	 * @param idLocalidade
	 * @param codigoSetorComercial
	 * @param numeroQuadra
	 * @param lote
	 * @param subLote
	 * @param idImovel
	 * @param descricaoAbreviadaLigacaoAguaSituacao
	 * @param descricaoAbreviadoLigacaoEsgotoSituacao
	 * @param consumoMinimo
	 * @param descricaoAbreviadaPavimentoRua
	 * @param descricaoAbreviadaPavimentoCalcada
	 * @param descricaoMeioSolicitacao
	 * @param nomeUsuario
	 * @param idFuncionario
	 * @param pontoReferencia
	 * @param idServicoTipo
	 * @param descricaoServicoTipo
	 * @param descricaoLocalOcorrencia
	 * @param dataPrevistaAtualRA
	 * @param observacaoRA
	 * @param observacaoOS
	 * @param idRA
	 * @param descricaoSolicitacaoTipoEspecificacao
	 * @param idOrdemServico
	 * @param tempoMedioExecucao
	 * @param descricaoUnidadeAtendimento
	 * @param numeroSequencialRota
	 * @param codigoRota
	 * @param idServicoTipoReferencia
	 * @param descricaoServicoTipoReferencia
	 * @param valorServicoTipo
	 * @param dataSolicitacao
	 * @param idSolicitacaoTipo
	 * @param descricaoSolicitacaoTipo
	 * @param idRotaImovel
	 * @param segmento
	 * @param idLigacaoAguaSituacao
	 * @param descricaoLigacaoAguaSituacao
	 * @param idLigacaoEsgotoSituacao
	 * @param descricaoLigacaoEsgotoSituacao
	 * @param idPavimentoRua
	 * @param descricaoPavimentoRua
	 * @param idPavimentoCalcada
	 * @param descricaoPavimentoCalcada
	 * @param idSolicitacaoTipoEspecificacao
	 */
	public OSRelatorioEstruturaHelper(Date dataGeracao, Integer idLocalidade, Integer codigoSetorComercial, Integer numeroQuadra,
										Short lote, Short subLote, Integer idImovel, String descricaoAbreviadaLigacaoAguaSituacao,
										String descricaoAbreviadoLigacaoEsgotoSituacao, Integer consumoMinimo,
										String descricaoAbreviadaPavimentoRua, String descricaoAbreviadaPavimentoCalcada,
										String descricaoMeioSolicitacao, String nomeUsuario, Integer idFuncionario, String pontoReferencia,
										Integer idServicoTipo, String descricaoServicoTipo, String descricaoLocalOcorrencia,
										Date dataPrevistaAtualRA, String observacaoRA, String observacaoOS, Integer idRA,
										String descricaoSolicitacaoTipoEspecificacao, Integer idOrdemServico, Short tempoMedioExecucao,
										String descricaoUnidadeAtendimento, Integer numeroSequencialRota, Short codigoRota,
										Integer idServicoTipoReferencia, String descricaoServicoTipoReferencia,
										BigDecimal valorServicoTipo, Date dataSolicitacao, Integer idSolicitacaoTipo,
										String descricaoSolicitacaoTipo, Integer idRotaImovel, Short segmento,
										Integer idLigacaoAguaSituacao, String descricaoLigacaoAguaSituacao,
										Integer idLigacaoEsgotoSituacao, String descricaoLigacaoEsgotoSituacao, Integer idPavimentoRua,
										String descricaoPavimentoRua, Integer idPavimentoCalcada, String descricaoPavimentoCalcada,
										Integer idSolicitacaoTipoEspecificacao, Integer idUnidadeAtendimento) {

		super();

		// Data da Geração
		if(dataGeracao != null){ // 0
			this.dataGeracao = dataGeracao;
		}

		Imovel imovel = new Imovel();

		// Localidade
		if(idLocalidade != null){ // 1
			Localidade localidade = new Localidade();
			localidade.setId(idLocalidade);
			this.idLocalidade = idLocalidade;
			imovel.setLocalidade(localidade);
		}

		// Setor Comercial
		if(codigoSetorComercial != null){ // 2
			SetorComercial setorComercial = new SetorComercial();
			setorComercial.setCodigo(codigoSetorComercial);
			imovel.setSetorComercial(setorComercial);
		}

		// Quadra
		if(numeroQuadra != null){ // 3
			Quadra quadra = new Quadra();
			quadra.setNumeroQuadra(numeroQuadra);
			imovel.setQuadra(quadra);
		}

		// Lote
		if(lote != null){ // 4
			imovel.setLote(lote);
		}

		// SubLote
		if(subLote != null){ // 5
			imovel.setSubLote(subLote);
		}

		// Rota
		if(idRotaImovel != null){ // 35
			Rota rota = new Rota();
			rota.setId(idRotaImovel);

			imovel.setRota(rota);
		}

		// Segmento
		if(segmento != null){ // 36
			imovel.setNumeroSegmento(segmento);
		}

		// Inscrição do Imóvel
		String inscricao = "";

		if(imovel.getLocalidade() != null){
			inscricao = imovel.getInscricaoFormatada();
		}
		this.setInscricaoImovel(inscricao);

		// Matrícula do Imóvel
		if(idImovel != null){ // 6
			// Seta o id no objeto imóvel para pesquisar,
			// posteriormente, a
			// descrição da categoria e a quantidade de economias
			this.idImovel = idImovel;
		}

		// Situação da Ligação de Água
		if(descricaoLigacaoAguaSituacao != null){ // 38,39
			this.situacaoAgua = idLigacaoAguaSituacao.toString() + " - " + descricaoLigacaoAguaSituacao;
		}

		// Situação da Ligação de Esgoto
		if(descricaoLigacaoEsgotoSituacao != null){ // 40,41
			this.situacaoEsgoto = idLigacaoEsgotoSituacao.toString() + " - " + descricaoLigacaoEsgotoSituacao;
		}

		// Esgoto Fixo
		if(consumoMinimo != null){ // 9
			this.esgotoFixo = consumoMinimo;
		}

		// Pavimento Rua
		if(descricaoPavimentoRua != null){ // 10
			this.pavimentoRua = idPavimentoRua + " - " + descricaoPavimentoRua;
		}

		// Pavimento Calçada
		if(descricaoPavimentoCalcada != null){ // 11
			this.pavimentoCalcada = idPavimentoCalcada + " - " + descricaoPavimentoCalcada;
		}

		// Meio de Solicitação
		if(descricaoMeioSolicitacao != null){ // 12
			this.meio = descricaoMeioSolicitacao;
		}

		// Nome do Atendente
		if(nomeUsuario != null){ // 13
			this.nomeAtendente = nomeUsuario;
		}

		// Matrícula do Atendente
		if(idFuncionario != null){ // 14
			this.idAtendente = idFuncionario;
		}

		// Ponto de Referência
		if(pontoReferencia != null){ // 15
			this.pontoReferencia = pontoReferencia;
		}

		// Id Serviço Solicitado
		if(idServicoTipo != null){ // 16
			this.idServicoSolicitado = idServicoTipo;
		}

		// Descrição Serviço Solicitado
		if(descricaoServicoTipo != null){ // 17
			this.descricaoServicoSolicitado = descricaoServicoTipo;
		}

		// Valor Serviço Solicitado
		if(valorServicoTipo != null){ // 31
			this.valorServicoSolicitado = Util.formatarMoedaReal(valorServicoTipo);
		}

		// Local Ocorrência
		if(descricaoLocalOcorrencia != null){ // 18
			this.localOcorrencia = descricaoLocalOcorrencia;
		}

		// Data Previsão Atual
		if(dataPrevistaAtualRA != null){ // 19
			this.previsao = dataPrevistaAtualRA;
		}

		// Observação do RA
		if(observacaoRA != null){ // 20
			this.observacaoRA = observacaoRA;
		}

		// Observação da OS
		if(observacaoOS != null){ // 21
			this.observacaoOS = observacaoOS;
		}

		// Id do RA
		if(idRA != null){ // 22
			this.idRA = idRA;
		}

		// Especificação
		if(descricaoSolicitacaoTipoEspecificacao != null){ // 23
			this.especificao = idSolicitacaoTipoEspecificacao + " - " + descricaoSolicitacaoTipoEspecificacao;
		}

		// Id da OS
		if(idOrdemServico != null){ // 24
			this.idOrdemServico = idOrdemServico;
		}

		// Tempo Médio Execução
		if(tempoMedioExecucao != null){ // 25
			this.tempoMedioExecucao = tempoMedioExecucao;
		}

		// Origem
		if(descricaoUnidadeAtendimento != null){ // 26
			this.unidadeGeracao = idUnidadeAtendimento.toString() + "-" + descricaoUnidadeAtendimento;
		}

		// Sequencial Rota
		if(numeroSequencialRota != null){ // 27
			this.sequencialRota = numeroSequencialRota;
		}

		// Rota
		if(codigoRota != null){ // 28
			this.codigoRota = codigoRota;
		}

		// Id do Serviço Tipo Referência
		if(idServicoTipoReferencia != null){ // 29
			this.idServicoTipoReferencia = idServicoTipoReferencia;
		}

		// Data e Hora da Solicitação
		if(dataSolicitacao != null){ // 32
			this.dataHoraSolicitacao = dataSolicitacao;
		}

		// Id Solicitação
		if(idSolicitacaoTipo != null){ // 33
			this.idSolicitacao = idSolicitacaoTipo;
		}

		// Descrição Solicitação
		if(descricaoSolicitacaoTipo != null){ // 34
			this.descricaoSolicitacao = descricaoSolicitacaoTipo;
		}

	}

	public Short getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(Short codigoRota){

		this.codigoRota = codigoRota;
	}

	public Integer getSequencialRota(){

		return sequencialRota;
	}

	public void setSequencialRota(Integer sequencialRota){

		this.sequencialRota = sequencialRota;
	}

	public String getOrigem(){

		return origem;
	}

	public void setOrigem(String origem){

		this.origem = origem;
	}

	public Short getTempoMedioExecucao(){

		return tempoMedioExecucao;
	}

	public void setTempoMedioExecucao(Short tempoMedioExecucao){

		this.tempoMedioExecucao = tempoMedioExecucao;
	}

	public OSRelatorioEstruturaHelper() {

	}

	public Date getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao){

		this.dataGeracao = dataGeracao;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public Integer getEsgotoFixo(){

		return esgotoFixo;
	}

	public void setEsgotoFixo(Integer esgotoFixo){

		this.esgotoFixo = esgotoFixo;
	}

	public Integer getIdAtendente(){

		return idAtendente;
	}

	public void setIdAtendente(Integer idAtendente){

		this.idAtendente = idAtendente;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public Integer getIdSolicitante(){

		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante){

		this.idSolicitante = idSolicitante;
	}

	public String getObservacaoOS(){

		return observacaoOS;
	}

	public void setObservacaoOS(String observacaoOS){

		this.observacaoOS = observacaoOS;
	}

	public String getObservacaoRA(){

		return observacaoRA;
	}

	public void setObservacaoRA(String observacaoRA){

		this.observacaoRA = observacaoRA;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getLocalOcorrencia(){

		return localOcorrencia;
	}

	public void setLocalOcorrencia(String localOcorrencia){

		this.localOcorrencia = localOcorrencia;
	}

	public String getMeio(){

		return meio;
	}

	public void setMeio(String meio){

		this.meio = meio;
	}

	public String getNomeAtendente(){

		return nomeAtendente;
	}

	public void setNomeAtendente(String nomeAtendente){

		this.nomeAtendente = nomeAtendente;
	}

	public String getNomeSolicitante(){

		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante){

		this.nomeSolicitante = nomeSolicitante;
	}

	public String getPavimentoCalcada(){

		return pavimentoCalcada;
	}

	public void setPavimentoCalcada(String pavimentoCalcada){

		this.pavimentoCalcada = pavimentoCalcada;
	}

	public String getPavimentoRua(){

		return pavimentoRua;
	}

	public void setPavimentoRua(String pavimentoRua){

		this.pavimentoRua = pavimentoRua;
	}

	public String getPontoReferencia(){

		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia){

		this.pontoReferencia = pontoReferencia;
	}

	public Date getPrevisao(){

		return previsao;
	}

	public void setPrevisao(Date previsao){

		this.previsao = previsao;
	}

	public int getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(int quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	public String getDescricaoServicoSolicitado(){

		return descricaoServicoSolicitado;
	}

	public void setDescricaoServicoSolicitado(String descricaoServicoSolicitado){

		this.descricaoServicoSolicitado = descricaoServicoSolicitado;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getUnidadeGeracao(){

		return unidadeGeracao;
	}

	public void setUnidadeGeracao(String unidadeGeracao){

		this.unidadeGeracao = unidadeGeracao;
	}

	public Integer getIdServicoSolicitado(){

		return idServicoSolicitado;
	}

	public void setIdServicoSolicitado(Integer idServicoSolicitado){

		this.idServicoSolicitado = idServicoSolicitado;
	}

	public Integer getIdRA(){

		return idRA;
	}

	public void setIdRA(Integer idRA){

		this.idRA = idRA;
	}

	/**
	 * @return Retorna o campo descricaoUnidade.
	 */
	public String getDescricaoUnidade(){

		return descricaoUnidade;
	}

	/**
	 * @param descricaoUnidade
	 *            O descricaoUnidade a ser setado.
	 */
	public void setDescricaoUnidade(String descricaoUnidade){

		this.descricaoUnidade = descricaoUnidade;
	}

	/**
	 * @return Retorna o campo idUnidade.
	 */
	public Integer getIdUnidade(){

		return idUnidade;
	}

	/**
	 * @param idUnidade
	 *            O idUnidade a ser setado.
	 */
	public void setIdUnidade(Integer idUnidade){

		this.idUnidade = idUnidade;
	}

	public String getEspecificao(){

		return especificao;
	}

	public void setEspecificao(String especificao){

		this.especificao = especificao;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return Retorna o campo descricaoServicoTipoReferencia.
	 */
	public String getDescricaoServicoTipoReferencia(){

		return descricaoServicoTipoReferencia;
	}

	/**
	 * @param descricaoServicoTipoReferencia
	 *            O descricaoServicoTipoReferencia a ser setado.
	 */
	public void setDescricaoServicoTipoReferencia(String descricaoServicoTipoReferencia){

		this.descricaoServicoTipoReferencia = descricaoServicoTipoReferencia;
	}

	/**
	 * @return Retorna o campo idServicoTipoReferencia.
	 */
	public Integer getIdServicoTipoReferencia(){

		return idServicoTipoReferencia;
	}

	/**
	 * @param idServicoTipoReferencia
	 *            O idServicoTipoReferencia a ser setado.
	 */
	public void setIdServicoTipoReferencia(Integer idServicoTipoReferencia){

		this.idServicoTipoReferencia = idServicoTipoReferencia;
	}

	public AnaliseConsumoRelatorioOSHelper getAnaliseConsumoRelatorioOSHelper(){

		return analiseConsumoRelatorioOSHelper;
	}

	public void setAnaliseConsumoRelatorioOSHelper(AnaliseConsumoRelatorioOSHelper analiseConsumoRelatorioOSHelper){

		this.analiseConsumoRelatorioOSHelper = analiseConsumoRelatorioOSHelper;
	}

	public HidrometroRelatorioOSHelper getHidrometroRelatorioOSHelper(){

		return hidrometroRelatorioOSHelper;
	}

	public void setHidrometroRelatorioOSHelper(HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper){

		this.hidrometroRelatorioOSHelper = hidrometroRelatorioOSHelper;
	}

	public String getValorServicoSolicitado(){

		return valorServicoSolicitado;
	}

	public void setValorServicoSolicitado(String valorServicoSolicitado){

		this.valorServicoSolicitado = valorServicoSolicitado;
	}

	public Date getDataHoraSolicitacao(){

		return dataHoraSolicitacao;
	}

	public void setDataHoraSolicitacao(Date dataHoraSolicitacao){

		this.dataHoraSolicitacao = dataHoraSolicitacao;
	}

	public Integer getIdSolicitacao(){

		return idSolicitacao;
	}

	public void setIdSolicitacao(Integer idSolicitacao){

		this.idSolicitacao = idSolicitacao;
	}

	public String getDescricaoSolicitacao(){

		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao){

		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public Date getDataRoteiroProgramacao(){

		return dataRoteiroProgramacao;
	}

	public void setDataRoteiroProgramacao(Date dataRoteiroProgramacao){

		this.dataRoteiroProgramacao = dataRoteiroProgramacao;
	}

	public Integer getIdEquipe(){

		return idEquipe;
	}

	public void setIdEquipe(Integer idEquipe){

		this.idEquipe = idEquipe;
	}

	public String getNomeEquipe(){

		return nomeEquipe;
	}

	public void setNomeEquipe(String nomeEquipe){

		this.nomeEquipe = nomeEquipe;
	}

	public String getCodigoDescricaoBairro(){

		return codigoDescricaoBairro;
	}

	public void setCodigoDescricaoBairro(String codigoDescricaoBairro){

		this.codigoDescricaoBairro = codigoDescricaoBairro;
	}

	public String getDescricaoAbreviadaCategoria(){

		return descricaoAbreviadaCategoria;
	}

	public void setDescricaoAbreviadaCategoria(String descricaoAbreviadaCategoria){

		this.descricaoAbreviadaCategoria = descricaoAbreviadaCategoria;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

}
