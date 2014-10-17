/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.OSRelatorioHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author isilva
 */
public class GeradorDadosRelatorioPadraoComOcorrencia
				extends GeradorDadosRelatorioOrdemServico {

	private static final int POSICAO_LOGO_EMPRESA = 3;

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioPadraoComOcorrencia() {

		super();

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * 
	 * gcom.relatorio.ordemservico.GeradorDadosRelatorioOrdemServico#gerarDados(gcom.atendimentopublico
	 * .ordemservico.OrdemServico)
	 */
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = null;
		DadosRelatorioOrdemServico dadosRelatorio = null;
		OSRelatorioHelper helper = null;

		dados = new ArrayList<DadosRelatorioOrdemServico>();
		dadosRelatorio = new DadosRelatorioOrdemServico();
		dadosRelatorio.setNumeroOS(Util.adicionarZerosEsquedaNumero(ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO,
						os.getId().toString()));

		Fachada fachada = Fachada.getInstancia();

		Collection<Integer> colecaoIdOs = new ArrayList<Integer>();
		colecaoIdOs.add(os.getId());

		try{

			// Dados empresa - rodapé
			String[] dadosEmpresa = getControladorCadastro().obterDadosEmpresa();
			dadosRelatorio.setImagem(dadosEmpresa[POSICAO_LOGO_EMPRESA]);

			Collection<OSRelatorioPadraoComOcorrenciaHelper> colecaoOSRelatorioHelper = getControladorOrdemServico().pesquisarOSRelatorioPadraoComOcorrencia(
							colecaoIdOs);

			if(colecaoOSRelatorioHelper != null && !colecaoOSRelatorioHelper.isEmpty()){

				Iterator colecaoOSRelatorioHelperIterator = colecaoOSRelatorioHelper.iterator();

				while(colecaoOSRelatorioHelperIterator.hasNext()){

					helper = (OSRelatorioHelper) colecaoOSRelatorioHelperIterator.next();

					// Prepara os campos na formatação correta ou passando-os para String para ser
					// colocado no Bean do relatório

					// Inscrição do Imóvel
					String inscricao = helper.getInscricaoImovel();
					// inscricao = inscricao.replace(".", "");
					dadosRelatorio.setInscricaoImovel(inscricao);

					// Matrícula do Imóvel
					String idImovel = "";
					if(helper.getIdImovel() != null){
						idImovel = Util.retornaMatriculaImovelParametrizada(helper.getIdImovel());
						dadosRelatorio.setIdImovel(idImovel);
					}

					// Categoria/Quantidade Economias
					String categoriaQtdeEconomias = "";
					if(helper.getCategoria() != null && !helper.getCategoria().equals("")){
						categoriaQtdeEconomias = helper.getCategoria() + "/" + helper.getQuantidadeEconomias();
						dadosRelatorio.setCategoriaQtdeEconomias(categoriaQtdeEconomias);
					}

					// Recuperar número do hidrômetro
					String numeroHidrometro = "";

					if(idImovel != null && !idImovel.equals("")){

						Imovel imovel = fachada.pesquisarImovel(Integer.valueOf(idImovel));

						if(imovel != null && imovel.getLigacaoAgua() != null
										&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
										&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

							// HIDI_ID da ligação de água
							numeroHidrometro = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro();

						}else if(imovel.getHidrometroInstalacaoHistorico() != null){

							// HIDI_ID do imovel
							numeroHidrometro = imovel.getHidrometroInstalacaoHistorico().getNumeroHidrometro();
						}
					}
					dadosRelatorio.setNumeroHidrometro(numeroHidrometro);

					// Situação Água/Esgoto
					String situacaoAguaEsgoto = "";
					if(helper.getSituacaoAgua() != null && !helper.getSituacaoAgua().equals("")){
						situacaoAguaEsgoto = helper.getSituacaoAgua() + "/" + helper.getSituacaoEsgoto();
						dadosRelatorio.setSituacaoAguaEsgoto(situacaoAguaEsgoto);
					}

					// Esgoto Fixo
					String esgotoFixo = "";
					if(helper.getEsgotoFixo() != null){
						esgotoFixo = String.valueOf(helper.getEsgotoFixo());
						dadosRelatorio.setEsgotoFixo(esgotoFixo);
					}

					// Pavimento Rua
					String pavimentoRua = "";
					if(helper.getPavimentoRua() != null){
						pavimentoRua = helper.getPavimentoRua();
						dadosRelatorio.setPavimentoRua(pavimentoRua);
					}

					// Pavimento Calçada
					String pavimentoCalcada = "";
					if(helper.getPavimentoCalcada() != null){
						pavimentoCalcada = helper.getPavimentoCalcada();
					}
					dadosRelatorio.setPavimentoCalcada(pavimentoCalcada);

					// Data e Hora da Solicitação
					String dataSolicitacao = "";
					String horaSolicitacao = "";
					if(helper.getDataHoraSolicitacao() != null){
						dataSolicitacao = Util.formatarData(helper.getDataHoraSolicitacao());
						horaSolicitacao = Util.formatarHoraSemData(helper.getDataHoraSolicitacao());
					}

					dadosRelatorio.setDataSolicitacao(dataSolicitacao);
					dadosRelatorio.setHoraSolicitacao(horaSolicitacao);

					// Id da Solicitacao
					String idSolicitacao = "";
					if(helper.getIdSolicitacao() != null){
						idSolicitacao = helper.getIdSolicitacao().toString();
					}
					dadosRelatorio.setIdSolicitacao(idSolicitacao);

					// Descricao da Solicitacao
					String descricaoSolicitacao = "";
					if(helper.getDescricaoSolicitacao() != null){
						descricaoSolicitacao = helper.getDescricaoSolicitacao();
					}
					dadosRelatorio.setDescricaoSolicitacao(descricaoSolicitacao);

					// Data da Programação
					String dataRoteiroProgramacao = "";
					if(helper.getDataRoteiroProgramacao() != null){
						dataRoteiroProgramacao = Util.formatarData(helper.getDataRoteiroProgramacao());
					}
					dadosRelatorio.setDataRoteiroProgramacao(dataRoteiroProgramacao);

					// Id Equipe
					String idEquipe = "";
					if(helper.getIdEquipe() != null){
						idEquipe = helper.getIdEquipe().toString();
					}
					dadosRelatorio.setIdEquipe(idEquipe);

					// Nome Equipe
					String nomeEquipe = "";
					if(helper.getNomeEquipe() != null){
						nomeEquipe = helper.getNomeEquipe();
					}
					dadosRelatorio.setNomeEquipe(nomeEquipe);

					// Nome Bairro
					String bairro = "";
					if(helper.getCodigoDescricaoBairro() != null){
						bairro = helper.getCodigoDescricaoBairro();
					}
					dadosRelatorio.setBairro(bairro);

					// Unidade Geração
					String unidadeGeracao = "";
					if(helper.getUnidadeGeracao() != null){
						unidadeGeracao = helper.getUnidadeGeracao();
						dadosRelatorio.setUnidadeGeracao(unidadeGeracao);
					}

					// Endereceo
					dadosRelatorio.setEndereco(helper.getEndereco());

					// Ponto Referencia
					dadosRelatorio.setPontoReferencia(helper.getPontoReferencia());

					// Telefone
					dadosRelatorio.setTelefone(helper.getTelefone());

					// Meio
					dadosRelatorio.setMeio(helper.getMeio());

					// Especificacao
					dadosRelatorio.setEspecificacao(helper.getEspecificao());

					// Local ocorrencia
					dadosRelatorio.setLocalOcorrencia(helper.getLocalOcorrencia());

					// Previsao
					String previsao = "";
					if(helper.getPrevisao() != null){
						previsao = Util.formatarData(helper.getPrevisao());
						dadosRelatorio.setPrevisao(previsao);
					}

					// Serviço Solicitado
					String servicoSolicitado = "";
					if(helper.getIdServicoSolicitado() != null){
						servicoSolicitado = helper.getIdServicoSolicitado() + " - " + helper.getDescricaoServicoSolicitado();
						dadosRelatorio.setServicoSolicitado(servicoSolicitado);
					}

					// Tipo Solicitante
					String unidade = "";
					String tipoSolicitanteUsuario = "";
					String tipoSolicitanteEmpresa = "";
					if(helper.getIdUnidade() != null){
						unidade = helper.getIdUnidade().toString() + " - " + helper.getDescricaoUnidade();
						tipoSolicitanteEmpresa = "X";
						dadosRelatorio.setUnidade(unidade);
						dadosRelatorio.setTipoSolicitanteEmpresa(tipoSolicitanteEmpresa);
					}else{
						tipoSolicitanteUsuario = "X";
						dadosRelatorio.setTipoSolicitanteUsuario(tipoSolicitanteUsuario);
					}

					// Obs RA
					dadosRelatorio.setObservacaoRA(helper.getObservacaoRA());

					// Solicitante
					String solicitante = "";
					if(helper.getNomeSolicitante() != null && !helper.getNomeSolicitante().equals("")){
						if(helper.getIdSolicitante() != null){
							solicitante = solicitante + helper.getIdSolicitante().toString() + " - ";
						}

						solicitante = solicitante + helper.getNomeSolicitante().trim();
						dadosRelatorio.setSolicitante(solicitante);
					}

					// Obs OS
					dadosRelatorio.setObservacaoOS(helper.getObservacaoOS());

					// Unidade
					if(helper.getIdUnidade() != null){
						dadosRelatorio.setUnidade(String.valueOf(helper.getDescricaoUnidade()));
					}

					// ID Atendente
					String idAtendente = "";
					if(helper.getIdAtendente() != null){
						idAtendente = helper.getIdAtendente().toString();
						dadosRelatorio.setIdAtendente(idAtendente);
					}

					// Nome Atendente
					dadosRelatorio.setNomeAtendente(helper.getNomeAtendente());

					// Data da Geração
					String dataGeracao = "";
					if(helper.getDataGeracao() != null){
						dataGeracao = Util.formatarData(helper.getDataGeracao());
						dadosRelatorio.setDataGeracao(dataGeracao);
					}

					// Id do RA
					String idRA = "";
					if(helper.getIdRA() != null){
						idRA = helper.getIdRA().toString();
						dadosRelatorio.setIdRA(idRA);
					}

				}
			}

		}catch(ControladorException e){
			throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
		}

		dados.add(dadosRelatorio);

		return dados;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		// TODO Auto-generated method stub
		return null;
	}
}
