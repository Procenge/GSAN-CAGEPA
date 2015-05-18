/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Layout de Ordem de Serviço Seletiva de Aferição de Hidrômetro Modelo 1 (Modelo Ordem de Serviço
 * de Aferição (SOROCABA))
 * 
 * @author Anderson Italo
 * @date 16/09/2014
 */
public class GeradorDadosRelatorioOrdemServicoSeletivaAfericaoHidrometroModelo1
				extends GeradorDadosRelatorioOrdemServico {

	public GeradorDadosRelatorioOrdemServicoSeletivaAfericaoHidrometroModelo1() {

		super();
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		Collection<OrdemServico> ordensServicos = new ArrayList<OrdemServico>();

		ordensServicos.add(os);

		return this.gerarDados(ordensServicos);
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> colecaoOrdemServicoSeletivaAfericaoHidrometro = new ArrayList<DadosRelatorioOrdemServico>();
		DadosRelatorioOrdemServico dadosRelatorio = null;
		OSRelatorioEstruturaHelper helper = null;
		Collection<Integer> colecaoIdOs = null;
		Fachada fachada = Fachada.getInstancia();
		SistemaParametro sistemaParametro = null;
		
		try{

			sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		}catch(ControladorException e){

			throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
		}

		for(OrdemServico ordemServico : ordensServicos){

			dadosRelatorio = new DadosRelatorioOrdemServico();
			colecaoIdOs = new ArrayList<Integer>();
			colecaoIdOs.add(ordemServico.getId());

			try{

				Collection<OSRelatorioEstruturaHelper> colecaoOSRelatorioEstruturaHelper = getControladorOrdemServico()
								.pesquisarOSRelatorioEstruturaCabecalho(colecaoIdOs);

				if(colecaoOSRelatorioEstruturaHelper != null && !colecaoOSRelatorioEstruturaHelper.isEmpty()){

					Iterator colecaoOSRelatorioHelperIterator = colecaoOSRelatorioEstruturaHelper.iterator();

					while(colecaoOSRelatorioHelperIterator.hasNext()){

						// Prepara os campos na formatação correta para serem colocados no Bean do
						// relatório
						helper = (OSRelatorioEstruturaHelper) colecaoOSRelatorioHelperIterator.next();

						// Inscrição do Imóvel
						String inscricao = helper.getInscricaoImovel();
						dadosRelatorio.setInscricaoImovel(inscricao);

						// Matrícula do Imóvel
						String idImovel = "";
						if(helper.getIdImovel() != null){

							idImovel = Util.retornaMatriculaImovelParametrizada(helper.getIdImovel());
							dadosRelatorio.setIdImovel(idImovel);
						}

						// Categoria/Quantidade Economias
						String categoriaQtdeEconomias = "";
						if(helper.getDescricaoCategoria() != null && !helper.getDescricaoCategoria().equals("")){

							categoriaQtdeEconomias = helper.getDescricaoAbreviadaCategoria() + "/" + helper.getQuantidadeEconomias();
							dadosRelatorio.setCategoriaQtdeEconomias(categoriaQtdeEconomias);
							dadosRelatorio.setCategoria(helper.getDescricaoCategoria());
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
								dadosRelatorio.setTipoLigacao("Hidrometrado");

							}else if(imovel.getHidrometroInstalacaoHistorico() != null){

								// HIDI_ID do imovel
								numeroHidrometro = imovel.getHidrometroInstalacaoHistorico().getNumeroHidrometro();
								dadosRelatorio.setTipoLigacao("Hidrometrado");
							}else{

								dadosRelatorio.setTipoLigacao("Não Hidrometrado");
							}
						}

						dadosRelatorio.setNumeroHidrometro(numeroHidrometro);

						// Data e Hora da Solicitação
						String dataSolicitacao = "";
						String horaSolicitacao = "";
						if(helper.getDataHoraSolicitacao() != null){

							dataSolicitacao = Util.formatarData(helper.getDataHoraSolicitacao());
							horaSolicitacao = Util.formatarHoraSemData(helper.getDataHoraSolicitacao());
						}

						dadosRelatorio.setDataSolicitacao(dataSolicitacao);
						dadosRelatorio.setHoraSolicitacao(horaSolicitacao);


						// Endereceo
						dadosRelatorio.setEndereco(helper.getEndereco());

						// Ponto Referencia
						dadosRelatorio.setPontoReferencia(helper.getPontoReferencia());

						// Telefone
						dadosRelatorio.setTelefone(helper.getTelefone());

						// Local ocorrencia
						dadosRelatorio.setLocalOcorrencia(helper.getLocalOcorrencia());

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

						// Descrição do Tipo de Serviço
						dadosRelatorio.setDescricaoServico(ordemServico.getServicoTipo().getId().toString() + " - "
										+ ordemServico.getServicoTipo().getDescricao());

						// Número da Ordem de Serviço
						dadosRelatorio.setNumeroOS(ordemServico.getId().toString());

						// Caminho Imagem Relatório
						dadosRelatorio.setImagem(sistemaParametro.getImagemRelatorio());
					}

					colecaoOrdemServicoSeletivaAfericaoHidrometro.add(dadosRelatorio);
				}

			}catch(ControladorException e){

				throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
			}
		}

		return colecaoOrdemServicoSeletivaAfericaoHidrometro;
	}
}