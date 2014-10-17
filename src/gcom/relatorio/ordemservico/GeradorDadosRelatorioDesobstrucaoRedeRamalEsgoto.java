/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.IRepositorioAtendimentoPublico;
import gcom.atendimentopublico.RepositorioAtendimentoPublicoHBM;
import gcom.atendimentopublico.ordemservico.IRepositorioOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoUnidade;
import gcom.atendimentopublico.ordemservico.RepositorioOrdemServicoHBM;
import gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade;
import gcom.atendimentopublico.registroatendimento.IRepositorioRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.RepositorioRegistroAtendimentoHBM;
import gcom.atendimentopublico.registroatendimento.Tramite;
import gcom.cadastro.cliente.ClienteImovel;
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

/**
 * @author vmelo
 */
public class GeradorDadosRelatorioDesobstrucaoRedeRamalEsgoto
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioRegistroAtendimento repositorioRegistroAtendimento = null;

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioAtendimentoPublico repositorioAtendimentoPublico = null;

	private IRepositorioOrdemServico repositorioOrdemServico = null;

	private static final String CAMPO_TP_LIGACAO_NAO_HIDROMETRADO = "Não Hidrometrado";

	private static final String CAMPO_TP_LIGACAO_HIDROMETRADO = "Hidrometrado";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioDesobstrucaoRedeRamalEsgoto() {

		super();
		repositorioRegistroAtendimento = RepositorioRegistroAtendimentoHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioAtendimentoPublico = RepositorioAtendimentoPublicoHBM.getInstancia();
		repositorioOrdemServico = RepositorioOrdemServicoHBM.getInstancia();
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

		dados = new ArrayList<DadosRelatorioOrdemServico>();
		dadosRelatorio = new DadosRelatorioOrdemServico();

		dadosRelatorio.setNumeroOS(Util.adicionarZerosEsqueda(ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO, os
						.getId().toString()));

		try{
			// Preenchendo os dados necessários para o relatório

			// Dados empresa - rodapé
			String[] dadosEmpresa = getControladorCadastro().obterDadosEmpresa();
			dadosRelatorio.setEnderecoEmpresa(dadosEmpresa[0]);
			dadosRelatorio.setEmailEmpresa(dadosEmpresa[1]);
			dadosRelatorio.setTelefoneEmpresa(dadosEmpresa[2]);
			dadosRelatorio.setSiteEmpresa(dadosEmpresa[4]);

			// Campos setados a partir da OS ->

			// Campo Observacao
			dadosRelatorio.setObservacao(os.getObservacao());

			// Campo Prioridade
			ServicoTipoPrioridade servicoTipoPrioridade = repositorioAtendimentoPublico.pesquisarServicoTipoPrioridade(os
							.getServicoTipoPrioridadeAtual().getId());
			if(servicoTipoPrioridade != null){
				dadosRelatorio.setPrioridade(servicoTipoPrioridade.getDescricao());
			}

			// Unidade da OS
			String unidade = "";
			OrdemServicoUnidade osUnidade = repositorioOrdemServico.pesquisarOrdemServicoUnidade(os.getId());
			if(osUnidade != null && osUnidade.getUnidadeOrganizacional() != null){
				unidade = String.valueOf(osUnidade.getUnidadeOrganizacional().getId());

				if(osUnidade.getUnidadeOrganizacional().getSigla() != null && !osUnidade.getUnidadeOrganizacional().getSigla().equals("")){
					unidade = unidade + "-" + osUnidade.getUnidadeOrganizacional().getSigla();
				}
				dadosRelatorio.setUnidade(unidade);
			}

			// Campos Registro Atendimento
			if(os.getRegistroAtendimento() != null){

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
					unidade = String.valueOf(tramite.getUnidadeOrganizacionalDestino().getId());
					if(tramite.getUnidadeOrganizacionalDestino().getSigla() != null
									&& !tramite.getUnidadeOrganizacionalDestino().getSigla().equals("")){
						unidade = unidade + "-" + tramite.getUnidadeOrganizacionalDestino().getSigla();
					}

					dadosRelatorio.setUnidade(unidade);
				}

				// Campo Prazo
				dadosRelatorio.setPrazo(Util.formatarData(registroAtendimento.getDataPrevistaAtual()));

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

					// Endereço e Bairro
					if(raSolicitante.getLogradouroBairro() != null){
						dadosRelatorio.setEndereco(raSolicitante.getLogradouroBairro().getLogradouro().getNome());
						dadosRelatorio.setBairro(raSolicitante.getLogradouroBairro().getBairro().getNome());
					}

					// Número
					dadosRelatorio.setNumero(raSolicitante.getNumeroImovel());

					// Complemento
					dadosRelatorio.setComplemento(raSolicitante.getComplementoEndereco());

					// Referência
					dadosRelatorio.setReferencia(raSolicitante.getPontoReferencia());

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

				// Caso o ponto de ref da raSolicitante esteja vazio, pegar da própria RA
				if(dadosRelatorio.getReferencia() == null || "".equals(dadosRelatorio.getReferencia())){
					dadosRelatorio.setReferencia(registroAtendimento.getPontoReferencia());
				}

				// Quarteirão - Informação inexistente.

				// Quadra
				if(registroAtendimento.getQuadra() != null){
					dadosRelatorio.setQuadra(String.valueOf(registroAtendimento.getQuadra().getId()));
				}
			}

			// Campos Imóvel
			if(os.getImovel() != null){

				Imovel imovel = repositorioImovel.pesquisarImovel(os.getImovel().getId());

				// Campo Proprietário
				ClienteImovel clienteImovel = repositorioImovel.pesquisarClienteProprietarioImovel(os.getImovel().getId());
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

				// Numero imovel
				dadosRelatorio.setNumero(imovel.getNumeroImovel());

				// Quadra
				dadosRelatorio.setQuadra(String.valueOf(imovel.getQuadra().getId()));

				// Lote
				dadosRelatorio.setLote(String.valueOf(imovel.getLote()));

				// Endereço Entrega
				String enderecoEntrega = getControladorEndereco().pesquisarEndereco(imovel.getId());
				dadosRelatorio.setEnderecoEntrega(enderecoEntrega);

				// Endereço do Imóvel
				if(imovel.getLogradouroBairro() != null){

					// Logradouro
					if(imovel.getLogradouroBairro().getLogradouro() != null){
						dadosRelatorio.setEndereco(imovel.getLogradouroBairro().getLogradouro().getNome());
					}

					// Bairro
					if(imovel.getLogradouroBairro().getBairro() != null){
						dadosRelatorio.setBairro(imovel.getLogradouroBairro().getBairro().getNome());
					}

					// Complemento
					dadosRelatorio.setComplemento(imovel.getComplementoEndereco());

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

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		// TODO Auto-generated method stub
		return null;
	}

}
