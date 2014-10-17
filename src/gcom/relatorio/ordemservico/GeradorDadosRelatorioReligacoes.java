
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.IRepositorioClienteImovel;
import gcom.cadastro.cliente.RepositorioClienteImovelHBM;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.fachada.Fachada;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Andre Nishimura
 * @date 18 de setembro de 2010
 *       Gerador do relatorio de religaçoes. Solicitações de ADA
 */
public class GeradorDadosRelatorioReligacoes
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioClienteImovel repositorioClienteImovel = null;

	private IRepositorioImovel repositorioImovel = null;

	public GeradorDadosRelatorioReligacoes() {

		super();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		return null;
	}

	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> colecaoOs) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioReligacoesHelper> colecaoEmitirOrdemCobrancaHelper = new ArrayList<DadosRelatorioReligacoesHelper>();

		DadosRelatorioReligacoesHelper bean;

		Fachada fachada = Fachada.getInstancia();

		if(colecaoOs != null && !colecaoOs.isEmpty()){
			for(OrdemServico ordemServico : colecaoOs){

				try{
					bean = new DadosRelatorioReligacoesHelper();

					if(ordemServico != null && ordemServico.getServicoTipo() != null){
						FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
						filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServico.getServicoTipo()
										.getId()));

						ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroServicoTipo,
										ServicoTipo.class.getName()));

						if(servicoTipo != null){
							bean.setTipoServico(servicoTipo.getIdDescricao());
						}
					}else{
						bean.setTipoServico("");
					}

					Imovel imovel = null;
					// Imovel
					imovel = fachada.pesquisarImovel(ordemServico.getImovel().getId());

					// Ligacao Agua
					LigacaoAgua ligacaoAgua = null;
					ligacaoAgua = fachada.pesquisarLigacaoAgua(imovel.getId());

					// Tipo Relação Cliente Imóvel
					String tipoRelacao = null;
					if(ordemServico != null && ordemServico.getServicoTipo() != null
									&& ordemServico.getServicoTipo().getOrdemServicoLayout() != null){
						tipoRelacao = fachada.recuperaRelacaoOSClienteImovel(ordemServico.getServicoTipo().getOrdemServicoLayout().getId());
						bean.setTipoRelacao(tipoRelacao);

					}

					// nome do cliente
					if(imovel != null && imovel.getId() != null){
						Collection<ClienteImovel> colecaoClienteImovel = repositorioImovel.pesquisarClientesImovel(imovel.getId());
						if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

							Object[] clienteImovel = (Object[]) Util.retonarObjetoDeColecao(colecaoClienteImovel);
							if(clienteImovel != null){
								bean.setCliente(String.valueOf(clienteImovel[1]));
								bean.setTipoRelacao(String.valueOf(clienteImovel[2]));
							}

							for(Object objeto : colecaoClienteImovel){

								Object[] clienteImovelAux = (Object[]) objeto;

								if(clienteImovelAux[2].equals(tipoRelacao)){
									bean.setCliente(String.valueOf(clienteImovelAux[1]));
									bean.setTipoRelacao(String.valueOf(clienteImovelAux[2]));
								}
							}

						}
					}else if(ordemServico.getRegistroAtendimento() != null){
						Collection<RegistroAtendimentoSolicitante> colecaoRaSolicitante = getControladorRegistroAtendimento()
										.obterRASolicitante(ordemServico.getRegistroAtendimento().getId());
						if(colecaoRaSolicitante != null && !colecaoRaSolicitante.isEmpty()){
							RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) Util
											.retonarObjetoDeColecao(colecaoRaSolicitante);
							bean.setCliente(getControladorRegistroAtendimento().obterNomeSolicitanteRA(
											registroAtendimentoSolicitante.getID()));
						}
					}

					// endereço do cliente
					String endereco = fachada.obterEnderecoAbreviadoOS(ordemServico.getId());
					bean.setEndereco(endereco);

					// bairro do cliente
					LogradouroBairro logBair = fachada.pesquisarLogradouroBairro(imovel.getLogradouroBairro().getId());
					bean.setBairro(logBair.getBairro().getNome());

					// categoria do imovel
					Categoria categoria = fachada.obterPrincipalCategoriaImovel(imovel.getId());
					bean.setCategoria(categoria.getDescricao());

					// Número da OS
					bean.setNumeroOS(Util.adicionarZerosEsquedaNumero(8, String.valueOf(ordemServico.getId())));

					// RA
					if(ordemServico.getRegistroAtendimento() != null){
						bean.setNumeroRA(String.valueOf(ordemServico.getRegistroAtendimento().getId()));
					}

					// matricula
					bean.setMatricula(imovel.getIdParametrizado());

					// inscricao
					String inscricao = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
					bean.setInscricao(inscricao);

					HidrometroInstalacaoHistorico hidrometro = null;
					if(ligacaoAgua != null){
						hidrometro = ligacaoAgua.getHidrometroInstalacaoHistorico();
					}

					// hidrometro
					if(hidrometro == null){
						bean.setNumeroHidrometro("");
					}else{
						bean.setNumeroHidrometro(hidrometro.getNumeroHidrometro());
					}

					// tipo da ligaçao -> Estimada (sem Hidrometro) Medida (Com Hidrometro)
					String tipoLigacao = "";
					if(hidrometro == null){
						tipoLigacao = "ESTIMADA";
					}else{
						tipoLigacao = "MEDIDA";
					}
					bean.setTipoLigacao(tipoLigacao);
					colecaoEmitirOrdemCobrancaHelper.add(bean);
				}catch(ControladorException e){
					throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
				}catch(ErroRepositorioException e){
					throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
				}
			}
		}

		List<DadosRelatorioOrdemServico> collRelatorioReligacoesBean = new ArrayList<DadosRelatorioOrdemServico>();

		int totalPaginas = colecaoEmitirOrdemCobrancaHelper.size();

		List<DadosRelatorioReligacoesHelper> colecaoOrdenada = this.dividirColecaoOrdenando(colecaoEmitirOrdemCobrancaHelper);

		DadosRelatorioReligacoesHelper primeiroHelper = null;
		DadosRelatorioReligacoesHelper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(DadosRelatorioReligacoesHelper helper : colecaoOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina("" + pagina1);
				pagina1++;
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina("" + pagina2);
				pagina2++;
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collRelatorioReligacoesBean.add(new DadosRelatorioOrdemServico(primeiroHelper, segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collRelatorioReligacoesBean.add(new DadosRelatorioOrdemServico(primeiroHelper, segundoHelper));
		}

		return collRelatorioReligacoesBean;

	}

	private List<DadosRelatorioReligacoesHelper> dividirColecaoOrdenando(List<DadosRelatorioReligacoesHelper> colecao){

		List<DadosRelatorioReligacoesHelper> retorno = new ArrayList<DadosRelatorioReligacoesHelper>();
		List<DadosRelatorioReligacoesHelper> colecaoTmp = colecao;
		int quantidadeItens = 0;
		int tamanhoColecao = colecaoTmp.size();
		int metadeColecao = 0;
		if(tamanhoColecao % 2 == 0){
			metadeColecao = tamanhoColecao / 2;
		}else{
			metadeColecao = (tamanhoColecao / 2) + 1;
		}
		while(quantidadeItens < metadeColecao){
			retorno.add((DadosRelatorioReligacoesHelper) colecaoTmp.get(quantidadeItens));
			if(metadeColecao + quantidadeItens < tamanhoColecao){
				retorno.add((DadosRelatorioReligacoesHelper) colecaoTmp.get(metadeColecao + quantidadeItens));
			}
			quantidadeItens++;
		}
		tamanhoColecao = 0;

		return retorno;
	}

}
