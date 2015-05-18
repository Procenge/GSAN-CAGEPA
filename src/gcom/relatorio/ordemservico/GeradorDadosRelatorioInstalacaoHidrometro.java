
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.IRepositorioAtendimentoPublico;
import gcom.atendimentopublico.RepositorioAtendimentoPublicoHBM;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.IRepositorioOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.RepositorioOrdemServicoHBM;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.Quadra;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andre R Nishimura
 * @date 17 de Dezembro de 2009
 */

public class GeradorDadosRelatorioInstalacaoHidrometro
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioRegistroAtendimento repositorioRegistroAtendimento = null;

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioAtendimentoPublico repositorioAtendimentoPublico = null;

	private IRepositorioOrdemServico repositorioOrdemServico = null;

	public GeradorDadosRelatorioInstalacaoHidrometro() {

		super();
		repositorioRegistroAtendimento = RepositorioRegistroAtendimentoHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioAtendimentoPublico = RepositorioAtendimentoPublicoHBM.getInstancia();
		repositorioOrdemServico = RepositorioOrdemServicoHBM.getInstancia();
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = null;
		DadosRelatorioOrdemServico dadosRelatorio = null;

		dados = new ArrayList<DadosRelatorioOrdemServico>();
		dadosRelatorio = new DadosRelatorioOrdemServico();
		dadosRelatorio.setNumeroOS(Util.adicionarZerosEsqueda(ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO, os
						.getId().toString()));

		try{
			// adicionando os dados do relatorio...

			// Tipo Relação Cliente Imóvel
			String tipoRelacao = null;
			if(os != null && os.getServicoTipo() != null && os.getServicoTipo().getOrdemServicoLayout() != null){
				tipoRelacao = getControladorOrdemServico().recuperaRelacaoOSClienteImovel(
								os.getServicoTipo().getOrdemServicoLayout().getId());
			}

			// Proprietário
			if(os.getImovel() != null){
				Collection<ClienteImovel> colecaoClienteImovel = repositorioImovel.pesquisarClientesImovel(os.getImovel().getId());
				if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

					Object[] clienteImovel = (Object[]) Util.retonarObjetoDeColecao(colecaoClienteImovel);
					if(clienteImovel != null){
						dadosRelatorio.setProprietario(String.valueOf(clienteImovel[1]));
						dadosRelatorio.setTipoRelacao(String.valueOf(clienteImovel[2]));
					}

					for(Object objeto : colecaoClienteImovel){

						Object[] clienteImovelAux = (Object[]) objeto;

						if(clienteImovelAux[2].equals(tipoRelacao)){
							dadosRelatorio.setProprietario(String.valueOf(clienteImovelAux[1]));
							dadosRelatorio.setTipoRelacao(String.valueOf(clienteImovelAux[2]));
						}
					}

				}
			}else if(os.getRegistroAtendimento() != null){
				Collection<RegistroAtendimentoSolicitante> colecaoRaSolicitante = getControladorRegistroAtendimento().obterRASolicitante(
								os.getRegistroAtendimento().getId());
				if(colecaoRaSolicitante != null && !colecaoRaSolicitante.isEmpty()){
					RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) Util
									.retonarObjetoDeColecao(colecaoRaSolicitante);
					dadosRelatorio.setProprietario(getControladorRegistroAtendimento().obterNomeSolicitanteRA(
									registroAtendimentoSolicitante.getID()));
				}
			}

			// Campos Imóvel
			if(os.getImovel() != null){

				Imovel imovel = repositorioImovel.pesquisarImovel(os.getImovel().getId());

				// Matricula
				dadosRelatorio.setIdImovel(imovel.getIdParametrizado());

				// Grupo
				Integer grupo = imovel.getRota().getFaturamentoGrupo().getId();
				dadosRelatorio.setGrupo(String.valueOf(grupo));

				// Inscrição do imóvel
				dadosRelatorio.setInscricao(getControladorImovel().pesquisarInscricaoImovel(imovel.getId(), true));

				// ME - Manaus Energia - Número Contrato Energia
				if(imovel.getNumeroCelpe() != null){
					dadosRelatorio.setMe(imovel.getNumeroCelpe().toString());
				}

				// Numero imovel
				dadosRelatorio.setNumero(imovel.getNumeroImovel());

				// Endereço do Imóvel
				if(imovel.getLogradouroBairro() != null){

					// Logradouro
					if(imovel.getLogradouroBairro().getLogradouro() != null){
						if(!imovel.getLogradouroBairro().getLogradouro().getNome().trim().equals("")){
							dadosRelatorio.setEndereco(imovel.getLogradouroBairro().getLogradouro().getNome());
						}
					}

					// Bairro
					if(imovel.getLogradouroBairro().getBairro() != null){
						if(!imovel.getLogradouroBairro().getBairro().getNome().trim().equals("")){
							dadosRelatorio.setBairro(imovel.getLogradouroBairro().getBairro().getNome());
						}
					}

					// Complemento
					if(imovel.getComplementoEndereco() != null){
						if(!imovel.getComplementoEndereco().trim().equals("")){
							dadosRelatorio.setComplemento(", " + imovel.getComplementoEndereco());
						}else{
							dadosRelatorio.setComplemento("");
						}
					}else{
						dadosRelatorio.setComplemento("");
					}
				}
				// Setor Hidraulico
				if(imovel.getQuadra() != null){
					FiltroQuadra filtroQuadra = new FiltroQuadra();
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, imovel.getQuadra().getId()));
					filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("distritoOperacional");
					Collection<Quadra> colecaoQuadra = getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

					if(colecaoQuadra != null && !colecaoQuadra.isEmpty()){
						Quadra quadra = colecaoQuadra.iterator().next();
						if(quadra != null && quadra.getDistritoOperacional() != null) dadosRelatorio.setSetorHidraulico(String
										.valueOf(quadra.getDistritoOperacional().getId()));
					}
				}

				// Categoria
				FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();
				filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));
				Collection<ImovelSubcategoria> colecaoImovelSubCategoria = getControladorUtil().pesquisar(filtroImovelSubCategoria,
								ImovelSubcategoria.class.getName());

				String categoria = "";
				if(colecaoImovelSubCategoria != null && !colecaoImovelSubCategoria.isEmpty()){
					for(Iterator<ImovelSubcategoria> colecaoIterator = colecaoImovelSubCategoria.iterator(); colecaoIterator.hasNext();){
						ImovelSubcategoria imovelSubcategoria = colecaoIterator.next();
						if(imovelSubcategoria != null && imovelSubcategoria.getComp_id() != null
										&& imovelSubcategoria.getComp_id().getSubcategoria() != null){
							Subcategoria subcategoria = imovelSubcategoria.getComp_id().getSubcategoria();
							FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
							filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, String.valueOf(subcategoria
											.getId())));
							filtroSubCategoria.adicionarCaminhoParaCarregamentoEntidade("categoria");
							Collection<Subcategoria> colecaoSubcategoria = getControladorUtil().pesquisar(filtroSubCategoria,
											Subcategoria.class.getName());

							if(colecaoSubcategoria != null && !colecaoSubcategoria.isEmpty()){
								subcategoria = colecaoSubcategoria.iterator().next();
								if(subcategoria != null){
									if(categoria.equals("")){
										categoria = subcategoria.getCategoria().getDescricaoAbreviada();
									}else{
										categoria = "/" + subcategoria.getCategoria().getDescricaoAbreviada();
									}
								}
							}
						}
					}
				}
				dadosRelatorio.setCategoria(categoria);

				// Situacao
				String situacao = "";
				LigacaoAguaSituacao ligacaoAguaSituacao = getControladorImovel().pesquisarLigacaoAguaSituacao(imovel.getId());
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = getControladorImovel().pesquisarLigacaoEsgotoSituacao(imovel.getId());
				if(ligacaoAguaSituacao != null){
					situacao = ligacaoAguaSituacao.getDescricaoAbreviado();
				}
				situacao = situacao + "/";
				if(ligacaoEsgotoSituacao != null){
					situacao = situacao + ligacaoEsgotoSituacao.getDescricaoAbreviado();
				}
				dadosRelatorio.setSituacao(situacao);

				if(!Util.isVazioOuBranco(os.getRegistroAtendimento())){
					RegistroAtendimento ra = getControladorRegistroAtendimento().pesquisarRegistroAtendimento(
									os.getRegistroAtendimento().getId());

					if(!Util.isVazioOuBranco(ra)){
						// -- Especificação do Tipo de Solicitação
						String descricaoEspecificacaoTipoSolicitacao = "";

						if(!Util.isVazioOuBranco(ra.getSolicitacaoTipoEspecificacao())){
							descricaoEspecificacaoTipoSolicitacao = ra.getSolicitacaoTipoEspecificacao().getDescricao();
						}

						dadosRelatorio.setEspecificacao(descricaoEspecificacaoTipoSolicitacao);

						// -- Tipo de Solicitação
						String descricaoTipoSolicitacao = "";

						if(!Util.isVazioOuBranco(ra.getSolicitacaoTipoEspecificacao())
										&& !Util.isVazioOuBranco(ra.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo())){
							descricaoTipoSolicitacao = ra.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getDescricao();
						}

						dadosRelatorio.setSolicitacao(descricaoTipoSolicitacao);

						// -- Data e Hora da Solicitação
						String dataSolicitacao = "";

						if(!Util.isVazioOuBranco(ra.getRegistroAtendimento())){
							dataSolicitacao = Util.formatarDataComHora(ra.getRegistroAtendimento());
						}

						dadosRelatorio.setDataSolicitacao(dataSolicitacao);

						// -- Observação da RA
						String observacaoRA = "";

						if(!Util.isVazioOuBranco(ra.getObservacao())){
							observacaoRA = ra.getObservacao();
						}

						dadosRelatorio.setObservacaoRA(observacaoRA);

						// --Ponto de Referência do Endereço
						String pontoReferencia = "";

						if(!Util.isVazioOuBranco(ra.getPontoReferencia())){
							pontoReferencia = ra.getPontoReferencia();
						}

						dadosRelatorio.setPontoReferencia(pontoReferencia);

						// -- Unidade de Atendimento
						String unidadeAtendimento = "";

						if(!Util.isVazioOuBranco(ra)){

							RegistroAtendimentoUnidade registroAtendimentoUnidade = repositorioRegistroAtendimento.pesquisarRAUnidade(ra
											.getId());

							unidadeAtendimento = registroAtendimentoUnidade.getUnidadeOrganizacional().getId().toString();
						}

						dadosRelatorio.setUnidadeAtendimento(unidadeAtendimento);
					}
				}

				// *********Número do Hidrômetro
				if(!Util.isVazioOuBranco(os.getImovel())){
					String numeroHidrometro = "";
					HidrometroInstalacaoHistorico hidrometro = repositorioImovel.pesquisarHidrometroPorLigacaoAgua(os.getImovel().getId());

					if(hidrometro != null){

						if(!Util.isVazioOuBranco(hidrometro.getNumeroHidrometro())){
							numeroHidrometro = hidrometro.getNumeroHidrometro();
						}

					}

					dadosRelatorio.setNumeroHidrometro(numeroHidrometro);

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
