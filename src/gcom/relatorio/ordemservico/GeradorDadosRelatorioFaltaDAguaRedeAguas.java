
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.IRepositorioAtendimentoPublico;
import gcom.atendimentopublico.RepositorioAtendimentoPublicoHBM;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.IRepositorioClienteEndereco;
import gcom.cadastro.cliente.RepositorioClienteEnderecoHBM;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GeradorDadosRelatorioFaltaDAguaRedeAguas
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioRegistroAtendimento repositorioRegistroAtendimento = null;

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioAtendimentoPublico repositorioAtendimentoPublico = null;

	private IRepositorioOrdemServico repositorioOrdemServico = null;

	private IRepositorioClienteEndereco repositorioClienteEndereco = null;

	private static final String CAMPO_TP_LIGACAO_NAO_HIDROMETRADO = "Não Hidrometrado";

	private static final String CAMPO_TP_LIGACAO_HIDROMETRADO = "Hidrometrado";

	public GeradorDadosRelatorioFaltaDAguaRedeAguas() {

		super();
		repositorioRegistroAtendimento = RepositorioRegistroAtendimentoHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioAtendimentoPublico = RepositorioAtendimentoPublicoHBM.getInstancia();
		repositorioOrdemServico = RepositorioOrdemServicoHBM.getInstancia();
		repositorioClienteEndereco = RepositorioClienteEnderecoHBM.getInstancia();
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = null;
		DadosRelatorioOrdemServico dadosRelatorio = null;

		dados = new ArrayList<DadosRelatorioOrdemServico>();
		dadosRelatorio = new DadosRelatorioOrdemServico();
		boolean enderecoOcorrenciaRA = false;

		try{
			// Preenchendo os dados necessários para o relatório

			// Dados empresa - rodapé
			String[] dadosEmpresa = getControladorCadastro().obterDadosEmpresa();
			dadosRelatorio.setEnderecoEmpresa(dadosEmpresa[0]);
			dadosRelatorio.setEmailEmpresa(dadosEmpresa[1]);
			dadosRelatorio.setTelefoneEmpresa(dadosEmpresa[2]);

			// Campos setados a partir da OS ->

			Empresa agente = repositorioOrdemServico.recuperarAgenteOS(os);
			if(agente != null){
				dadosRelatorio.setAgenteAssociado(agente.getDescricao());
			}

			// Campo Observacao
			dadosRelatorio.setObservacaoOS(os.getObservacao());

			OrdemServicoProgramacao ordemServicoProgramacao = getControladorOrdemServico().pesquisarDataEquipeOSProgramacao(os.getId());
			if(ordemServicoProgramacao != null && ordemServicoProgramacao.getEquipe() != null){
				dadosRelatorio.setEquipe(ordemServicoProgramacao.getEquipe().getNome());
			}

			// Campo Prioridade
			ServicoTipoPrioridade servicoTipoPrioridade = repositorioAtendimentoPublico.pesquisarServicoTipoPrioridade(os
							.getServicoTipoPrioridadeAtual().getId());
			if(servicoTipoPrioridade != null){
				dadosRelatorio.setPrioridade(servicoTipoPrioridade.getDescricao());
			}

			// Campo Número OS
			dadosRelatorio.setNumeroOS(Util.adicionarZerosEsqueda(ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO,
							os.getId().toString()));

			// Unidade da OS
			StringBuilder unidade = new StringBuilder();
			OrdemServicoUnidade osUnidade = repositorioOrdemServico.pesquisarOrdemServicoUnidade(os.getId());
			if(osUnidade != null && osUnidade.getUnidadeOrganizacional() != null){
				unidade.append(osUnidade.getUnidadeOrganizacional().getId());

				if(osUnidade.getUnidadeOrganizacional().getSigla() != null && !osUnidade.getUnidadeOrganizacional().getSigla().equals("")){
					unidade.append("-").append(osUnidade.getUnidadeOrganizacional().getSigla());
				}
				dadosRelatorio.setUnidade(unidade.toString());
			}

			// Id do serviço
			dadosRelatorio.setIdServico(String.valueOf(os.getServicoTipo().getId()));

			// Descrição do serviço
			dadosRelatorio.setDescricaoServico(os.getServicoTipo().getDescricao());

			// Campos Registro Atendimento
			if(os.getRegistroAtendimento() != null){
				os.setRegistroAtendimento(getControladorRegistroAtendimento().pesquisarRegistroAtendimento(
								os.getRegistroAtendimento().getId()));
				dadosRelatorio.setObservacaoRA(os.getRegistroAtendimento().getObservacao());

				Integer idRA = os.getRegistroAtendimento().getId();

				// Campo Número RA
				dadosRelatorio.setNumeroRA(String.valueOf(idRA));

				// Campo Solicitacao
				RegistroAtendimento registroAtendimento = repositorioRegistroAtendimento.pesquisarRegistroAtendimento(idRA);
				dadosRelatorio.setSolicitacao(registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getDescricao());

				Tramite tramite = repositorioRegistroAtendimento.recuperarTramiteMaisAtualPorRA(os.getRegistroAtendimento().getId());

				if(tramite != null){
					// Campo Unidade Origem
					dadosRelatorio.setOrigem(tramite.getUnidadeOrganizacionalOrigem().getDescricao());

					// Campo Unidade -> Destino
					unidade.append(tramite.getUnidadeOrganizacionalDestino().getId());
					if(tramite.getUnidadeOrganizacionalDestino().getSigla() != null
									&& !tramite.getUnidadeOrganizacionalDestino().getSigla().equals("")){
						unidade.append("-").append(tramite.getUnidadeOrganizacionalDestino().getSigla());
					}

					dadosRelatorio.setUnidade(unidade.toString());
				}

				// Campo Prazo
				dadosRelatorio.setPrazo(Util.formatarData(registroAtendimento.getDataPrevistaAtual()));

				// Endereço da ocorrência (Prioridade 1-RA; 2-Imóvel; 3-Solicitante 3.1 LogBairro,
				// 3.2 Cliente resid.)
				if(registroAtendimento.getLogradouroBairro() != null){

					// Se conseguir obter o endereço através da RA, não precisa mais ver em imovel
					// ou raSolicitante.
					enderecoOcorrenciaRA = true;

					// Logradouro
					String logradouro = obterLogradouroCompleto(registroAtendimento.getLogradouroBairro().getLogradouro());
					dadosRelatorio.setEndereco(logradouro);

					// Bairro
					dadosRelatorio.setBairro(registroAtendimento.getLogradouroBairro().getBairro().getNome());

					// Número
					dadosRelatorio.setNumero(registroAtendimento.getNumeroImovel());

					// Complemento
					dadosRelatorio.setComplemento(registroAtendimento.getComplementoEndereco());

					// Referência
					dadosRelatorio.setReferencia(registroAtendimento.getPontoReferencia());

					// Quarteirão - Informação inexistente.

					// Quadra
					if(registroAtendimento.getQuadra() != null){
						dadosRelatorio.setQuadra(String.valueOf(registroAtendimento.getQuadra().getId()));
					}

				}

				// Especificação
				dadosRelatorio.setEspecificacao(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());

				// Data solicitação
				dadosRelatorio.setDataSolicitacao(Util.formatarData(registroAtendimento.getRegistroAtendimento()));

				// -> Obtendo RegistroAtendimentoSolicitante
				RegistroAtendimentoSolicitante raSolicitante = repositorioRegistroAtendimento
								.obterRegistroAtendimentoSolicitanteRelatorioOS(idRA);

				if(raSolicitante != null){

					// Solicitante
					if(raSolicitante.getSolicitante() != null){
						dadosRelatorio.setSolicitante(raSolicitante.getSolicitante());
					}else if(raSolicitante.getCliente() != null){
						dadosRelatorio.setSolicitante(raSolicitante.getCliente().getNome());
					}else if(raSolicitante.getUnidadeOrganizacional() != null){
						dadosRelatorio.setSolicitante(raSolicitante.getUnidadeOrganizacional().getDescricao());
					}

					// Se os campos de endereço da OCORRÊNCIA não foram setados a partir da RA,
					// setar a partir da raSolicitante.
					if(!enderecoOcorrenciaRA){

						// Endereço da raSolicitante
						if(raSolicitante.getLogradouroBairro() != null){

							// Logradouro
							String logradouro = obterLogradouroCompleto(raSolicitante.getLogradouroBairro().getLogradouro());
							dadosRelatorio.setEndereco(logradouro);

							// dadosRelatorio.setEndereco(raSolicitante.getLogradouroBairro().getLogradouro().getNome());

							// Bairro
							dadosRelatorio.setBairro(raSolicitante.getLogradouroBairro().getBairro().getNome());

							// Número
							dadosRelatorio.setNumero(raSolicitante.getNumeroImovel());

							// Complemento
							dadosRelatorio.setComplemento(raSolicitante.getComplementoEndereco());

							// Referência
							dadosRelatorio.setReferencia(raSolicitante.getPontoReferencia());

						}else if(raSolicitante.getCliente() != null){

							// obter endereço residencial desse cliente
							ClienteEndereco clienteEndereco = repositorioClienteEndereco.pesquisarClienteEnderecoResidencial(raSolicitante
											.getCliente().getId());

							String logradouro = "";
							String bairro = "";
							String numero = "";
							String complemento = "";
							String enderecoReferencia = "";

							if(Util.isNaoNuloBrancoZero(clienteEndereco)){

								if(Util.isNaoNuloBrancoZero(clienteEndereco.getLogradouroBairro())){

									// Logradouro
									if(Util.isNaoNuloBrancoZero(clienteEndereco.getLogradouroBairro().getLogradouro())){
										logradouro = obterLogradouroCompleto(clienteEndereco.getLogradouroBairro().getLogradouro());
									}

									// Bairro
									if(Util.isNaoNuloBrancoZero(clienteEndereco.getLogradouroBairro().getBairro())){
										bairro = clienteEndereco.getLogradouroBairro().getBairro().getNome();
									}
								}

								// Número
								numero = clienteEndereco.getNumero();

								// Complemento
								complemento = clienteEndereco.getComplemento();

								// Referência
								if(Util.isNaoNuloBrancoZero(clienteEndereco.getEnderecoReferencia())){
									enderecoReferencia = clienteEndereco.getEnderecoReferencia().getDescricao();
								}
							}

							dadosRelatorio.setEndereco(logradouro);
							dadosRelatorio.setBairro(bairro);
							dadosRelatorio.setNumero(numero);
							dadosRelatorio.setComplemento(complemento);
							dadosRelatorio.setReferencia(enderecoReferencia);
						}
					}

					// Setando campo de endereço do CLIENTE - antes chamado de entrega.
					String enderecoCliente = getControladorRegistroAtendimento().obterEnderecoSolicitanteRA(raSolicitante.getID());
					dadosRelatorio.setEnderecoEntrega(enderecoCliente);

					// Telefone
					String telefone = "";
					if(raSolicitante.getCliente() != null){
						// buscar telefone de clienteFone
						telefone = getControladorCliente().pesquisarClienteFonePrincipal(raSolicitante.getCliente().getId());
					}else{
						// buscar telefone da proria RA
						telefone = getControladorRegistroAtendimento().pesquisarSolicitanteFonePrincipal(registroAtendimento.getId());
					}
					dadosRelatorio.setTelefone(telefone);

				}
			}

			// Campos Imóvel
			Imovel imovel = null;

			if(os.getRegistroAtendimento() == null){
				if(os.getImovel() != null){
					imovel = repositorioImovel.pesquisarImovel(os.getImovel().getId());
				}
			}else if(os.getRegistroAtendimento().getImovel() != null){
				imovel = repositorioImovel.pesquisarImovel(os.getRegistroAtendimento().getImovel().getId());
			}

			if(imovel != null){

				// Campo Proprietário
				ClienteImovel clienteImovel = repositorioImovel.pesquisarClienteProprietarioImovel(imovel.getId());
				if(clienteImovel != null){
					dadosRelatorio.setProprietario(clienteImovel.getDescricao());

				}

				// Tipo Faturamento
				if(imovel.getFaturamentoTipo() != null){
					dadosRelatorio.setTipoFaturamento(imovel.getFaturamentoTipo().getDescricao());
				}

				// Grupo
				Integer grupo = imovel.getRota().getFaturamentoGrupo().getId();
				dadosRelatorio.setGrupo(String.valueOf(grupo));

				// Tipo Ligação
				if(imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
					dadosRelatorio.setTipoLigacao(CAMPO_TP_LIGACAO_HIDROMETRADO);
				}else{
					dadosRelatorio.setTipoLigacao(CAMPO_TP_LIGACAO_NAO_HIDROMETRADO);
				}

				// ME - Manaus Energia - Número Contrato Energia
				if(imovel.getNumeroCelpe() != null){
					dadosRelatorio.setMe(imovel.getNumeroCelpe().toString());
				}

				// Ligação - Mat. do Imóvel
				dadosRelatorio.setLigacao(String.valueOf(imovel.getId()));

				// Inscrição do imóvel
				dadosRelatorio.setInscricao(getControladorImovel().pesquisarInscricaoImovel(imovel.getId(), true));

				// Lote
				dadosRelatorio.setLote(String.valueOf(imovel.getLote()));

				dadosRelatorio.setImagemFaltaAgua("./imagens/imagemFaltaAgua.jpg");

				// Endereço do Imóvel - caso não tenha sido recuperado através da RA.
				if(!enderecoOcorrenciaRA && imovel.getLogradouroBairro() != null){

					// Logradouro
					if(imovel.getLogradouroBairro().getLogradouro() != null){
						String logradouro = obterLogradouroCompleto(imovel.getLogradouroBairro().getLogradouro());
						dadosRelatorio.setEndereco(logradouro);
						// dadosRelatorio.setEndereco(imovel.getLogradouroBairro().getLogradouro().getNome());
					}

					// Bairro
					if(imovel.getLogradouroBairro().getBairro() != null){
						dadosRelatorio.setBairro(imovel.getLogradouroBairro().getBairro().getNome());
					}

					// Complemento
					dadosRelatorio.setComplemento(imovel.getComplementoEndereco());

					// Numero imovel
					dadosRelatorio.setNumero(imovel.getNumeroImovel());

					// Quadra
					dadosRelatorio.setQuadra(String.valueOf(imovel.getQuadra().getId()));
				}
			}

		}catch(ErroRepositorioException e){
			throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
		}catch(ControladorException e){
			throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
		}

		dados.add(dadosRelatorio);

		return dados;
	}

	private String obterLogradouroCompleto(Logradouro logradouro){

		String logradouroCompleto = "";

		if(logradouro != null){

			// Obter Tipo
			if(logradouro.getLogradouroTipo() != null && logradouro.getLogradouroTipo().getDescricao() != null){
				logradouroCompleto = logradouro.getLogradouroTipo().getDescricao().trim() + " ";
			}

			// Obter Titulo
			if(logradouro.getLogradouroTitulo() != null && logradouro.getLogradouroTitulo().getDescricao() != null){
				logradouroCompleto = logradouroCompleto + logradouro.getLogradouroTitulo().getDescricao().trim() + " ";
			}

			// Obter Logradouro
			if(logradouro.getNome() != null){
				logradouroCompleto = logradouroCompleto + logradouro.getNome().trim();
			}

		}
		return logradouroCompleto;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		return null;
	}
}
