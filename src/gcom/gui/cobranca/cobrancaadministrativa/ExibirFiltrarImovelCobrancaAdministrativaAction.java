
package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelCobrancaMotivoRetirada;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaMotivoRetirada;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * [UC3070] Filtrar Imóvel Cobrança Administrativa
 * 
 * @author Anderson Italo
 * @date 07/09/2012
 */
public class ExibirFiltrarImovelCobrancaAdministrativaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarImovelCobrancaAdministrativa");
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		FiltrarImovelCobrancaAdministrativaActionForm form = (FiltrarImovelCobrancaAdministrativaActionForm) actionForm;

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("mostrarCampos"))){

			sessao.setAttribute("mostrarCampos", httpServletRequest.getParameter("mostrarCampos").toString());
		}

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("reload"))){
			this.habilitaDesabilitaCampos(form, fachada, httpServletRequest, sessao, httpServletResponse, retorno);

		}else{

			if(httpServletRequest.getParameter("menu") != null && httpServletRequest.getParameter("menu").toString().equals("sim")){

				form.setIndicadorSituacaoCobrancaAdministrativa(ConstantesSistema.TODOS.toString());
			}

			// Comando de Cobrança
			if((!Util.isVazioOuBranco(form.getIdComando()) && Util.isInteger(form.getIdComando()))
							|| ((!Util.isVazioOuBranco(httpServletRequest.getParameter("idComandoValidar")) && Util
											.isInteger(httpServletRequest.getParameter("idComandoValidar"))))){

				CobrancaAcaoAtividadeComando comando = null;

				if(!Util.isVazioOuBranco(httpServletRequest.getParameter("idComandoValidar"))){

					comando = (CobrancaAcaoAtividadeComando) fachada.pesquisar(Util.obterInteger(httpServletRequest
									.getParameter("idComandoValidar")), CobrancaAcaoAtividadeComando.class);
				}else{

					comando = (CobrancaAcaoAtividadeComando) fachada.pesquisar(Util.obterInteger(form.getIdComando()),
									CobrancaAcaoAtividadeComando.class);
				}

				if(comando != null){

					// [FS0001 – Verificar seleção do comando]
					if(!comando.getCobrancaAcao().getId().equals(CobrancaAcao.COBRANCA_ADMINISTRATIVA)){

						form.setIdComando(null);
						form.setDescricaoComando(null);

						ActionServletException ex = new ActionServletException("atencao.comando_nao_cobranca_administrativa");
						ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
						throw ex;
					}

					if(comando.getRealizacao() == null){

						form.setIdComando(null);
						form.setDescricaoComando(null);

						ActionServletException ex = new ActionServletException("atencao.comando_nao_realizado");
						ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
						throw ex;
					}

					form.setDescricaoComando(comando.getDescricaoTitulo());
					form.setIdComando(comando.getId().toString());
					httpServletRequest.setAttribute("comandoExistente", "true");
				}else{

					httpServletRequest.setAttribute("comandoInexistente", "true");
					form.setDescricaoComando("COMANDO INEXISTENTE");
				}
			}else{

				form.setIdComando(null);
				form.setDescricaoComando(null);
				httpServletRequest.setAttribute("comandoExistente", "true");
			}

			// Empresa de Cobrança Administrativa
			Collection<Empresa> colecaoEmpresa = fachada.pesquisarEmpresaCobrancaAdministrativa(null);

			// [FS0002] - Verificar existência de empresa de cobrança administrativa
			if(!Util.isVazioOrNulo(colecaoEmpresa)){

				sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);
			}else{

				ActionServletException ex = new ActionServletException("atencao.empresa_cobranca_administrativa_nao_existe");
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;
			}

			// Imóvel
			this.pesquisarImovel(form, fachada, httpServletRequest);

			// Cliente
			this.pesquisarCliente(form, fachada, httpServletRequest);

			// Gerência Regional
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);

			Collection<GerenciaRegional> colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class
							.getName());

			// [FS0005] - Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoGerenciaRegional)){

				sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
			}else{

				ActionServletException ex = new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,
								GerenciaRegional.class.getName());
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;
			}

			// Unidade de Negócio
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

			if(!Util.isVazioOrNulo(form.getIdsGerenciaRegionalSelecionadas()) && form.getIdsGerenciaRegionalSelecionadas().length > 0
							&& !form.getIdsGerenciaRegionalSelecionadas()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() > 0 && form.getIdsGerenciaRegionalSelecionadas().length == 1){

					filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID_GERENCIA, form
									.getIdsGerenciaRegionalSelecionadas()[0]));
					httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
				}else{

					if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() == 0
									&& form.getIdsGerenciaRegionalSelecionadas().length == 2){

						filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID_GERENCIA, form
										.getIdsGerenciaRegionalSelecionadas()[1]));

						httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
					}else if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() == 0
									&& form.getIdsGerenciaRegionalSelecionadas().length == 1){

						httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
					}else{

						httpServletRequest.setAttribute("habilitaUnidadeNegocio", "false");
						form.setIdsUnidadeNegocioSelecionadas(null);
					}
				}
			}else{

				httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
			}

			filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);

			Collection<UnidadeNegocio> colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			// [FS0005] - Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoUnidadeNegocio)){

				sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
			}else{

				ActionServletException ex = new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,
								UnidadeNegocio.class.getName());
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;

			}

			// Dados de Inscrição
			String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");
			String inscricaoTipo = (String) httpServletRequest.getParameter("inscricaoTipo");

			if(!Util.isVazioOuBranco(objetoConsulta) && !Util.isVazioOuBranco(inscricaoTipo)){

				switch(Integer.parseInt(objetoConsulta)){

					case 1:

						pesquisarLocalidade(inscricaoTipo, form, fachada, httpServletRequest);

						break;
					case 2:

						pesquisarLocalidade(inscricaoTipo, form, fachada, httpServletRequest);

						pesquisarSetorComercial(inscricaoTipo, form, fachada, httpServletRequest);

						break;
					case 3:

						pesquisarLocalidade(inscricaoTipo, form, fachada, httpServletRequest);

						pesquisarSetorComercial(inscricaoTipo, form, fachada, httpServletRequest);

						pesquisarQuadra(inscricaoTipo, form, fachada, httpServletRequest);

						break;

					default:
						break;
				}
			}

			if(form.getDescricaoLocalidadeInicial() != null && form.getDescricaoLocalidadeInicial().contains("INEXISTENTE")){

				httpServletRequest.setAttribute("localidadeInicialInexistente", "true");
			}else{

				httpServletRequest.setAttribute("localidadeInicialExistente", "true");
			}

			if(form.getDescricaoLocalidadeFinal() != null && form.getDescricaoLocalidadeFinal().contains("INEXISTENTE")){

				httpServletRequest.setAttribute("localidadeFinalInexistente", "true");
			}else{

				httpServletRequest.setAttribute("localidadeFinalExistente", "true");
			}

			if(form.getDescricaoSetorComercialInicial() != null && form.getDescricaoSetorComercialInicial().contains("INEXISTENTE")){

				httpServletRequest.setAttribute("setorComercialInicialInexistente", "true");
			}else{

				httpServletRequest.setAttribute("setorComercialInicialExistente", "true");
			}

			if(form.getDescricaoSetorComercialFinal() != null && form.getDescricaoSetorComercialFinal().contains("INEXISTENTE")){

				httpServletRequest.setAttribute("setorComercialFinalInexistente", "true");
			}else{

				httpServletRequest.setAttribute("setorComercialFinalExistente", "true");
			}

			if(form.getMensagemQuadraInicialInexistente() != null && form.getMensagemQuadraInicialInexistente().contains("INEXISTENTE")){

				httpServletRequest.setAttribute("quadraInicialInexistente", "true");
			}else{

				httpServletRequest.setAttribute("quadraInicialExistente", "true");
			}

			if(form.getMensagemQuadraFinalInexistente() != null && form.getMensagemQuadraFinalInexistente().contains("INEXISTENTE")){

				httpServletRequest.setAttribute("quadraFinalInexistente", "true");
			}else{

				httpServletRequest.setAttribute("quadraFinalExistente", "true");
			}

			this.preparaArquivo(form, fachada, httpServletRequest, httpServletResponse, retorno, sessao);

			// Categoria
			FiltroCategoria filtroCategoria = new FiltroCategoria();
			filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

			Collection<Categoria> colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

			// [FS0005] - Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoCategoria)){

				sessao.setAttribute("colecaoCategoria", colecaoCategoria);
			}else{

				ActionServletException ex = new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, Categoria.class
								.getName());
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;
			}

			// SubCategoria
			FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();

			if(!Util.isVazioOrNulo(form.getIdsCategoriaSelecionadas()) && form.getIdsCategoriaSelecionadas().length > 0
							&& !form.getIdsCategoriaSelecionadas()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				if(form.getIdsCategoriaSelecionadas()[0].intValue() > 0 && form.getIdsCategoriaSelecionadas().length == 1){

					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, form
									.getIdsCategoriaSelecionadas()[0]));
					httpServletRequest.setAttribute("habilitaSubCategoria", "true");
				}else{

					if(form.getIdsCategoriaSelecionadas()[0].intValue() == 0 && form.getIdsCategoriaSelecionadas().length == 2){

						filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, form
										.getIdsCategoriaSelecionadas()[1]));

						httpServletRequest.setAttribute("habilitaSubCategoria", "true");
					}else if(form.getIdsCategoriaSelecionadas()[0].intValue() == 0 && form.getIdsCategoriaSelecionadas().length == 1){

						httpServletRequest.setAttribute("habilitaSubCategoria", "true");
					}else{

						httpServletRequest.setAttribute("habilitaSubCategoria", "false");
						form.setIdsSubcategoriaSelecionadas(null);
					}
				}
			}else{

				httpServletRequest.setAttribute("habilitaSubCategoria", "true");
			}

			filtroSubCategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);

			Collection<Subcategoria> colecaoSubCategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

			// [FS0005] - Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoSubCategoria)){

				sessao.setAttribute("colecaoSubCategoria", colecaoSubCategoria);
			}else{

				ActionServletException ex = new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, Subcategoria.class
								.getName());
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;
			}

			// Situação da Ligação de Água
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
			filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

			Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,
							LigacaoAguaSituacao.class.getName());

			// [FS0005] - Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoLigacaoAguaSituacao)){

				sessao.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);
			}else{

				ActionServletException ex = new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,
								LigacaoAguaSituacao.class.getName());
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;
			}

			// Situação da Ligação de Esgoto
			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
			filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

			Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
							LigacaoEsgotoSituacao.class.getName());

			// [FS0005] - Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoSituacao)){

				sessao.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);
			}else{

				ActionServletException ex = new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,
								LigacaoEsgotoSituacao.class.getName());
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;
			}

			// Motivo da Retirada
			FiltroImovelCobrancaMotivoRetirada filtroImovelCobrancaMotivoRetirada = new FiltroImovelCobrancaMotivoRetirada();
			filtroImovelCobrancaMotivoRetirada.setCampoOrderBy(FiltroImovelCobrancaMotivoRetirada.DESCRICAO);

			Collection<ImovelCobrancaMotivoRetirada> colecaoImovelCobrancaMotivoRetirada = fachada.pesquisar(
							filtroImovelCobrancaMotivoRetirada, ImovelCobrancaMotivoRetirada.class.getName());

			// [FS0005] - Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoImovelCobrancaMotivoRetirada)){

				sessao.setAttribute("colecaoImovelCobrancaMotivoRetirada", colecaoImovelCobrancaMotivoRetirada);
			}else{

				ActionServletException ex = new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,
								ImovelCobrancaMotivoRetirada.class.getName());
				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
				throw ex;
			}
		}

		return retorno;
	}

	private void pesquisarLocalidade(String inscricaoTipo, FiltrarImovelCobrancaAdministrativaActionForm formulario, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		Collection<Object> colecaoLocalidade = null;

		if(inscricaoTipo.equalsIgnoreCase("origem")){

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Util.obterInteger(formulario
							.getIdLocalidadeInicial())));

			colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(Util.isVazioOrNulo(colecaoLocalidade)){

				httpServletRequest.setAttribute("localidadeInicialInexistente", "true");
				formulario.setDescricaoLocalidadeInicial("LOCALIDADE INEXISTENTE");
			}else{

				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				formulario.setIdLocalidadeInicial(localidade.getId().toString());
				formulario.setDescricaoLocalidadeInicial(localidade.getDescricao());

				formulario.setIdLocalidadeFinal(localidade.getId().toString());
				formulario.setDescricaoLocalidadeFinal(localidade.getDescricao());
				httpServletRequest.setAttribute("localidadeInicialExistente", "true");
				httpServletRequest.setAttribute("localidadeFinalExistente", "true");
			}
		}else{

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Util.obterInteger(formulario
							.getIdLocalidadeFinal())));

			colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(Util.isVazioOrNulo(colecaoLocalidade)){

				httpServletRequest.setAttribute("localidadeFinalInexistente", "true");
				formulario.setDescricaoLocalidadeFinal("LOCALIDADE INEXISTENTE");
			}else{

				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				formulario.setIdLocalidadeFinal(localidade.getId().toString());
				formulario.setDescricaoLocalidadeFinal(localidade.getDescricao());
				httpServletRequest.setAttribute("localidadeFinalExistente", "true");
			}
		}
	}

	private void pesquisarSetorComercial(String inscricaoTipo, FiltrarImovelCobrancaAdministrativaActionForm formulario, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		Collection<Object> colecaoSetorComercial = null;

		if(inscricaoTipo.equalsIgnoreCase("origem")){

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Util.obterInteger(formulario
							.getIdLocalidadeInicial())));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Util
							.obterShort(formulario.getCodigoSetorComercialInicial())));

			colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(Util.isVazioOrNulo(colecaoSetorComercial)){

				httpServletRequest.setAttribute("setorComercialInicialInexistente", "true");
				formulario.setDescricaoSetorComercialInicial("SETOR COMERCIAL INEXISTENTE");
			}else{

				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				formulario.setCodigoSetorComercialInicial(String.valueOf(setorComercial.getCodigo()));
				formulario.setDescricaoSetorComercialInicial(setorComercial.getDescricao());

				formulario.setCodigoSetorComercialFinal(String.valueOf(setorComercial.getCodigo()));
				formulario.setDescricaoSetorComercialFinal(setorComercial.getDescricao());
				httpServletRequest.setAttribute("setorComercialInicialExistente", "true");
				httpServletRequest.setAttribute("setorComercialFinalExistente", "true");
			}
		}else{

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Util.obterInteger(formulario
							.getIdLocalidadeFinal())));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Util
							.obterShort(formulario.getCodigoSetorComercialFinal())));

			colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(Util.isVazioOrNulo(colecaoSetorComercial)){

				httpServletRequest.setAttribute("setorComercialFinalInexistente", "true");
				formulario.setDescricaoSetorComercialFinal("SETOR COMERCIAL INEXISTENTE");
			}else{

				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				formulario.setCodigoSetorComercialFinal(String.valueOf(setorComercial.getCodigo()));
				formulario.setDescricaoSetorComercialFinal(setorComercial.getDescricao());
				httpServletRequest.setAttribute("setorComercialFinalExistente", "true");
			}
		}
	}

	private void pesquisarQuadra(String inscricaoTipo, FiltrarImovelCobrancaAdministrativaActionForm formulario, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		Collection<Object> colecaoQuadra = null;

		if(inscricaoTipo.equalsIgnoreCase("origem")){

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, Util.obterInteger(formulario
							.getIdLocalidadeInicial())));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, Util.obterShort(formulario
							.getCodigoSetorComercialInicial())));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util.obterInteger(formulario
							.getNumeroQuadraInicial())));

			colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(Util.isVazioOrNulo(colecaoQuadra)){

				httpServletRequest.setAttribute("quadraInicialInexistente", "true");
				formulario.setMensagemQuadraInicialInexistente("QUADRA INEXISTENTE");
			}else{

				Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				formulario.setNumeroQuadraInicial(String.valueOf(quadra.getNumeroQuadra()));
				formulario.setMensagemQuadraInicialInexistente("");

				formulario.setNumeroQuadraFinal(String.valueOf(quadra.getNumeroQuadra()));
				formulario.setMensagemQuadraFinalInexistente("");
				httpServletRequest.setAttribute("quadraInicialExistente", "true");
				httpServletRequest.setAttribute("quadraFinalExistente", "true");
			}
		}else{

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, Util.obterInteger(formulario
							.getIdLocalidadeFinal())));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, Util.obterShort(formulario
							.getCodigoSetorComercialFinal())));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util.obterInteger(formulario
							.getNumeroQuadraFinal())));

			colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(Util.isVazioOrNulo(colecaoQuadra)){

				httpServletRequest.setAttribute("quadraFinalInexistente", "true");
				formulario.setMensagemQuadraFinalInexistente("QUADRA INEXISTENTE");
			}else{

				Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				formulario.setNumeroQuadraFinal(String.valueOf(quadra.getNumeroQuadra()));
				formulario.setMensagemQuadraFinalInexistente("");
				httpServletRequest.setAttribute("quadraFinalExistente", "true");
			}
		}
	}

	private void pesquisarImovel(FiltrarImovelCobrancaAdministrativaActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		// Imóvel
		if(!Util.isVazioOuBranco(form.getIdImovel()) && Util.isInteger(form.getIdImovel())){

			Imovel imovel = fachada.pesquisarImovel(Util.obterInteger(form.getIdImovel()));

			if(imovel != null){

				form.setInscricaoImovel(imovel.getInscricaoFormatada());
				httpServletRequest.setAttribute("imovelExistente", "true");
			}else{

				httpServletRequest.setAttribute("imovelInexistente", "true");
				form.setInscricaoImovel("IMÓVEL INEXISTENTE");
			}
		}else{

			form.setIdImovel(null);
			form.setInscricaoImovel(null);
			httpServletRequest.setAttribute("imovelExistente", "true");
		}
	}

	private void pesquisarCliente(FiltrarImovelCobrancaAdministrativaActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		// Cliente
		if(!Util.isVazioOuBranco(form.getIdCliente()) && Util.isInteger(form.getIdCliente())){

			Cliente cliente = (Cliente) fachada.pesquisar(Util.obterInteger(form.getIdCliente()), Cliente.class);

			if(cliente != null){

				form.setNomeCliente(cliente.getNome());
				httpServletRequest.setAttribute("clienteExistente", "true");
			}else{

				httpServletRequest.setAttribute("clienteInexistente", "true");
				form.setNomeCliente("CLIENTE INEXISTENTE");
			}
		}else{

			form.setIdCliente(null);
			form.setNomeCliente(null);
			httpServletRequest.setAttribute("clienteExistente", "true");
		}
	}

	private void preparaArquivo(FiltrarImovelCobrancaAdministrativaActionForm form, Fachada fachada, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, ActionForward retorno, HttpSession sessao){

		// Arquivo de Imóveis
		if(form.getArquivoMatricula() != null && !form.getArquivoMatricula().getFileName().equals("")){

			try{

				FormFile formFile = form.getArquivoMatricula();
				Scanner scanner = new Scanner(formFile.getInputStream());
				Collection colecaoImoveis = new ArrayList();
				Collection<String> devolucao = new ArrayList<String>();
				String matricula;
				Imovel imovel;
				int contador = 0;

				if(formFile != null){
					String[] fileName = formFile.getFileName().split("\\.");
					String name = fileName[fileName.length - 1];

					if(!name.equalsIgnoreCase("CSV") && !name.equalsIgnoreCase("txt")){

						throw new ActionServletException("atencao.arquivo.enviado.tem.que.ser.extensaocvstxt", null, "CSVTXT");
					}
				}

				while(scanner.hasNext()){

					matricula = scanner.next();
					try{

						contador++;

						imovel = fachada.pesquisarImovel(Integer.valueOf(matricula));
						if(imovel == null){

							devolucao.add(matricula + " MATRÍCULA INEXISTENTE");
						}else if(colecaoImoveis.contains(imovel)){

							devolucao.add(matricula + " MATRICULA REPETIDA NO ARQUIVO");
						}else{

							colecaoImoveis.add(imovel);
						}
					}catch(NumberFormatException e){

						devolucao.add(matricula + " NÃO É UMA MATRÍCULA VALIDA");
					}

					if(contador > 1000){

						form.setArquivoMatricula(null);
						ActionServletException ex = new ActionServletException("atencao.quantidade_registros_arquivo_matriculas_excedente");
						ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
						throw ex;
					}
				}
				// Seta os imoveis para a pesquisa
				Collection<Integer> idsImoveis = new ArrayList();
				for(Imovel item : (Collection<Imovel>) colecaoImoveis){

					idsImoveis.add(item.getId());
				}

				// Prepara arquivo com erros
				File arquivoErro = new File("ArquivoErro.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoErro
								.getAbsolutePath())));

				if(devolucao.isEmpty()){

					form.setArquivoDownload("arquivo validado com sucesso. Foram encontrados " + colecaoImoveis.size()
									+ " imóveis válidos.");
					form.setArquivoCarregado(form.getArquivoMatricula().getFileName());
				}else{

					for(String linha : devolucao){

						bufferedWriter.write(linha);
						bufferedWriter.newLine();
					}
					bufferedWriter.flush();
					bufferedWriter.close();
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					FileInputStream in = new FileInputStream(arquivoErro);
					int b;

					while((b = in.read()) > -1){

						byteArrayOutputStream.write(b);
					}

					form.setArquivoDownload("Foram encontradas " + devolucao.size()
									+ " falhas no arquivo. Clique aqui para baixar o arquivo com a descrição das falhas. ");
					form.setEnderecoArquivoDownload(arquivoErro.getPath());

					if(httpServletRequest.getParameter("download") != null && httpServletRequest.getParameter("download").equals("true")){

						httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + arquivoErro.getName());
						String mimeType = "application/txt";
						httpServletResponse.setContentType(mimeType);
						OutputStream out = httpServletResponse.getOutputStream();
						out.write(byteArrayOutputStream.toByteArray());
						out.flush();
						out.close();
						retorno = null;
						sessao.removeAttribute("arquivo");
						form.setArquivoDownload(null);
						form.setArquivoCarregado("");
						form.setEnderecoArquivoDownload(null);
					}
				}
				if(idsImoveis.size() > 0){

					form.setIdsImoveis(idsImoveis);
				}else{

					form.setArquivoDownload("Nenhum imóvel foi encontrado no arquivo.");
					form.setArquivoCarregado("");
				}
			}catch(FileNotFoundException e){

				throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
			}catch(IOException e){

				throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
			}
		}
	}

	private void habilitaDesabilitaCampos(FiltrarImovelCobrancaAdministrativaActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest, HttpSession sessao, HttpServletResponse httpServletResponse,
					ActionForward retorno){

		// Cliente
		pesquisarCliente(form, fachada, httpServletRequest);

		// Imóvel
		pesquisarImovel(form, fachada, httpServletRequest);

		// Dados de Inscrição
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");
		String inscricaoTipo = (String) httpServletRequest.getParameter("inscricaoTipo");

		if(!Util.isVazioOuBranco(objetoConsulta) && !Util.isVazioOuBranco(inscricaoTipo)){

			switch(Integer.parseInt(objetoConsulta)){

				case 1:

					pesquisarLocalidade(inscricaoTipo, form, fachada, httpServletRequest);

					break;
				case 2:

					pesquisarLocalidade(inscricaoTipo, form, fachada, httpServletRequest);

					pesquisarSetorComercial(inscricaoTipo, form, fachada, httpServletRequest);

					break;
				case 3:

					pesquisarLocalidade(inscricaoTipo, form, fachada, httpServletRequest);

					pesquisarSetorComercial(inscricaoTipo, form, fachada, httpServletRequest);

					pesquisarQuadra(inscricaoTipo, form, fachada, httpServletRequest);

					break;

				default:
					break;
			}
		}

		if(form.getInscricaoImovel() != null && form.getInscricaoImovel().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("imovelInexistente", "true");
		}else{

			httpServletRequest.setAttribute("imovelExistente", "true");
		}

		if(form.getNomeCliente() != null && form.getNomeCliente().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("clienteInexistente", "true");
		}else{

			httpServletRequest.setAttribute("clienteExistente", "true");
		}

		if(form.getDescricaoComando() != null && form.getDescricaoComando().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("comandoInexistente", "true");
		}else{

			httpServletRequest.setAttribute("comandoExistente", "true");
		}

		if(form.getDescricaoLocalidadeInicial() != null && form.getDescricaoLocalidadeInicial().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("localidadeInicialInexistente", "true");
		}else{

			httpServletRequest.setAttribute("localidadeInicialExistente", "true");
		}

		if(form.getDescricaoLocalidadeFinal() != null && form.getDescricaoLocalidadeFinal().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("localidadeFinalInexistente", "true");
		}else{

			httpServletRequest.setAttribute("localidadeFinalExistente", "true");
		}

		if(form.getDescricaoSetorComercialInicial() != null && form.getDescricaoSetorComercialInicial().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("setorComercialInicialInexistente", "true");
		}else{

			httpServletRequest.setAttribute("setorComercialInicialExistente", "true");
		}

		if(form.getDescricaoSetorComercialFinal() != null && form.getDescricaoSetorComercialFinal().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("setorComercialFinalInexistente", "true");
		}else{

			httpServletRequest.setAttribute("setorComercialFinalExistente", "true");
		}

		if(form.getMensagemQuadraInicialInexistente() != null && form.getMensagemQuadraInicialInexistente().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("quadraInicialInexistente", "true");
		}else{

			httpServletRequest.setAttribute("quadraInicialExistente", "true");
		}

		if(form.getMensagemQuadraFinalInexistente() != null && form.getMensagemQuadraFinalInexistente().contains("INEXISTENTE")){

			httpServletRequest.setAttribute("quadraFinalInexistente", "true");
		}else{

			httpServletRequest.setAttribute("quadraFinalExistente", "true");
		}

		this.preparaArquivo(form, fachada, httpServletRequest, httpServletResponse, retorno, sessao);

		if(!Util.isVazioOuBranco(form.getIdImovel()) || !Util.isVazioOuBranco(form.getIdCliente())){

			httpServletRequest.setAttribute("habilitaGerenciaRegional", "false");
			httpServletRequest.setAttribute("habilitaUnidadeNegocio", "false");
			httpServletRequest.setAttribute("habilitaInscricao", "false");
			httpServletRequest.setAttribute("habilitaArquivo", "false");

		}else{

			httpServletRequest.setAttribute("habilitaGerenciaRegional", "true");
			httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
			httpServletRequest.setAttribute("habilitaInscricao", "true");
			httpServletRequest.setAttribute("habilitaArquivo", "true");
		}

		// Empresa de Cobrança
		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsEmpresaSelecionadas())){

			Collection colecaoIdsEmpresaSelecionadas = new ArrayList();
			Integer[] arrayEmpresa = form.getIdsEmpresaSelecionadas();
			for(int i = 0; i < arrayEmpresa.length; i++){

				Integer idEmpresa = arrayEmpresa[i];
				colecaoIdsEmpresaSelecionadas.add(idEmpresa);
			}

			Collection<Empresa> colecaoEmpresa = fachada.pesquisarEmpresaCobrancaAdministrativa(colecaoIdsEmpresaSelecionadas);

			sessao.setAttribute("colecaoIdsEmpresaSelecionadas", colecaoEmpresa);

		}else{
			sessao.setAttribute("colecaoIdsEmpresaSelecionadas", null);
		}

		// Gerência Regional
		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsGerenciaRegionalSelecionadas())){

			httpServletRequest.setAttribute("habilitaImovel", "false");
			httpServletRequest.setAttribute("habilitaCliente", "false");
			httpServletRequest.setAttribute("habilitaInscricao", "false");
			httpServletRequest.setAttribute("habilitaArquivo", "false");

			if(form.getIdsGerenciaRegionalSelecionadas().length > 1){

				httpServletRequest.setAttribute("habilitaUnidadeNegocio", "false");
			}

			Collection colecaoIdsGerenciaRegionalSelecionados = new ArrayList();
			Integer[] arrayGerenciaRegional = form.getIdsGerenciaRegionalSelecionadas();
			for(int i = 0; i < arrayGerenciaRegional.length; i++){

				Integer idGerenciaRegional = arrayGerenciaRegional[i];
				colecaoIdsGerenciaRegionalSelecionados.add(idGerenciaRegional);
			}

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimplesColecao(FiltroGerenciaRegional.ID,
							colecaoIdsGerenciaRegionalSelecionados));
			filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
			Collection<GerenciaRegional> colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class
							.getName());
			sessao.setAttribute("colecaoGerenciaRegionalSelecionadas", colecaoGerenciaRegional);

		}else{
			sessao.setAttribute("colecaoGerenciaRegionalSelecionadas", null);
		}

		// Unidade negócio
		FiltroUnidadeNegocio filtroUnidadeNegocio = null;
		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsUnidadeNegocioSelecionadas())){

			httpServletRequest.setAttribute("habilitaImovel", "false");
			httpServletRequest.setAttribute("habilitaCliente", "false");
			httpServletRequest.setAttribute("habilitaInscricao", "false");
			httpServletRequest.setAttribute("habilitaArquivo", "false");

			Collection colecaoIdsUnidadeNegocioSelecionados = new ArrayList();
			Integer[] arrayUnidadeNegocio = form.getIdsUnidadeNegocioSelecionadas();
			for(int i = 0; i < arrayUnidadeNegocio.length; i++){

				Integer idUnidadeNegocio = arrayUnidadeNegocio[i];
				colecaoIdsUnidadeNegocioSelecionados.add(idUnidadeNegocio);
			}

			filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimplesColecao(FiltroUnidadeNegocio.ID,
							colecaoIdsUnidadeNegocioSelecionados));

			if(!Util.isVazioOrNulo(form.getIdsGerenciaRegionalSelecionadas()) && form.getIdsGerenciaRegionalSelecionadas().length > 0
							&& !form.getIdsGerenciaRegionalSelecionadas()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() > 0 && form.getIdsGerenciaRegionalSelecionadas().length == 1){

					filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.GERENCIA, form
									.getIdsGerenciaRegionalSelecionadas()[0]));
					httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
				}else{

					if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() == 0
									&& form.getIdsGerenciaRegionalSelecionadas().length == 2){

						filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.GERENCIA, form
										.getIdsGerenciaRegionalSelecionadas()[1]));

						httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
					}else if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() == 0
									&& form.getIdsGerenciaRegionalSelecionadas().length == 1){

						httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
					}else{

						httpServletRequest.setAttribute("habilitaUnidadeNegocio", "false");
						form.setIdsSubcategoriaSelecionadas(null);
					}
				}
			}else{

				httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
			}

			filtroUnidadeNegocio.setCampoOrderBy(FiltroGerenciaRegional.NOME);
			Collection<UnidadeNegocio> colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			sessao.setAttribute("colecaoIdsUnidadeNegocioSelecionados", colecaoUnidadeNegocio);

		}else{
			sessao.setAttribute("colecaoIdsUnidadeNegocioSelecionados", null);
		}

		filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		if(!Util.isVazioOrNulo(form.getIdsGerenciaRegionalSelecionadas()) && form.getIdsGerenciaRegionalSelecionadas().length > 0
						&& !form.getIdsGerenciaRegionalSelecionadas()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() > 0 && form.getIdsGerenciaRegionalSelecionadas().length == 1){

				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.GERENCIA, form
								.getIdsGerenciaRegionalSelecionadas()[0]));
				httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
			}else{

				if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() == 0 && form.getIdsGerenciaRegionalSelecionadas().length == 2){

					filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.GERENCIA, form
									.getIdsGerenciaRegionalSelecionadas()[1]));

					httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
				}else if(form.getIdsGerenciaRegionalSelecionadas()[0].intValue() == 0
								&& form.getIdsGerenciaRegionalSelecionadas().length == 1){

					httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
				}else{

					httpServletRequest.setAttribute("habilitaUnidadeNegocio", "false");
					form.setIdsSubcategoriaSelecionadas(null);
				}
			}
		}else{

			httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
		}

		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);

		Collection<UnidadeNegocio> colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());
		sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);

		// Categoria
		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsCategoriaSelecionadas())){

			Collection colecaoIdsCategoriaSelecionadas = new ArrayList();
			Integer[] arrayCategoria = form.getIdsCategoriaSelecionadas();
			for(int i = 0; i < arrayCategoria.length; i++){

				Integer idCategoria = arrayCategoria[i];
				colecaoIdsCategoriaSelecionadas.add(idCategoria);
			}

			FiltroCategoria filtroCategoria = new FiltroCategoria();
			filtroCategoria.adicionarParametro(new ParametroSimplesColecao(FiltroCategoria.CODIGO, colecaoIdsCategoriaSelecionadas));
			filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

			Collection<Categoria> colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

			sessao.setAttribute("colecaoIdsCategoriaSelecionadas", colecaoCategoria);

		}else{
			sessao.setAttribute("colecaoIdsCategoriaSelecionadas", null);
		}

		// SubCategoria
		FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();

		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsSubcategoriaSelecionadas())){

			Collection colecaoIdsSubcategoriaSelecionadas = new ArrayList();
			Integer[] arraySubcategoria = form.getIdsSubcategoriaSelecionadas();
			for(int i = 0; i < arraySubcategoria.length; i++){

				Integer idSubcategoria = arraySubcategoria[i];
				colecaoIdsSubcategoriaSelecionadas.add(idSubcategoria);
			}

			filtroSubCategoria
							.adicionarParametro(new ParametroSimplesColecao(FiltroSubCategoria.CODIGO, colecaoIdsSubcategoriaSelecionadas));

			if(!Util.isVazioOrNulo(form.getIdsCategoriaSelecionadas()) && form.getIdsCategoriaSelecionadas().length > 0
							&& !form.getIdsCategoriaSelecionadas()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				if(form.getIdsCategoriaSelecionadas()[0].intValue() > 0 && form.getIdsCategoriaSelecionadas().length == 1){

					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, form
									.getIdsCategoriaSelecionadas()[0]));
					httpServletRequest.setAttribute("habilitaSubCategoria", "true");
				}else{

					if(form.getIdsCategoriaSelecionadas()[0].intValue() == 0 && form.getIdsCategoriaSelecionadas().length == 2){

						filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, form
										.getIdsCategoriaSelecionadas()[1]));

						httpServletRequest.setAttribute("habilitaSubCategoria", "true");
					}else if(form.getIdsCategoriaSelecionadas()[0].intValue() == 0 && form.getIdsCategoriaSelecionadas().length == 1){

						httpServletRequest.setAttribute("habilitaSubCategoria", "true");
					}else{

						httpServletRequest.setAttribute("habilitaSubCategoria", "false");
						form.setIdsSubcategoriaSelecionadas(null);
					}
				}
			}else{

				httpServletRequest.setAttribute("habilitaSubCategoria", "true");
			}

			filtroSubCategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);

			Collection<Subcategoria> colecaoSubcategoriaSelecionada = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

			sessao.setAttribute("colecaoIdsSubcategoriaSelecionadas", colecaoSubcategoriaSelecionada);

		}else{
			sessao.setAttribute("colecaoIdsSubcategoriaSelecionadas", null);
		}

		filtroSubCategoria = new FiltroSubCategoria();

		if(!Util.isVazioOrNulo(form.getIdsCategoriaSelecionadas()) && form.getIdsCategoriaSelecionadas().length > 0
						&& !form.getIdsCategoriaSelecionadas()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			if(form.getIdsCategoriaSelecionadas()[0].intValue() > 0 && form.getIdsCategoriaSelecionadas().length == 1){

				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, form
								.getIdsCategoriaSelecionadas()[0]));
				httpServletRequest.setAttribute("habilitaSubCategoria", "true");
			}else{

				if(form.getIdsCategoriaSelecionadas()[0].intValue() == 0 && form.getIdsCategoriaSelecionadas().length == 2){

					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, form
									.getIdsCategoriaSelecionadas()[1]));
					httpServletRequest.setAttribute("habilitaSubCategoria", "true");
				}else if(form.getIdsCategoriaSelecionadas()[0].intValue() == 0 && form.getIdsCategoriaSelecionadas().length == 1){

					httpServletRequest.setAttribute("habilitaSubCategoria", "true");
				}else{

					httpServletRequest.setAttribute("habilitaSubCategoria", "false");
					form.setIdsSubcategoriaSelecionadas(null);
				}
			}
		}else{

			httpServletRequest.setAttribute("habilitaSubCategoria", "true");
		}

		filtroSubCategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);

		Collection<Subcategoria> colecaoSubCategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());
		sessao.setAttribute("colecaoSubCategoria", colecaoSubCategoria);

		// Situação da Ligação de Água
		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsLigacaoAguaSituacaoSelecionadas())){

			Collection colecaoIdsLigacaoAguaSituacaoSelecionadas = new ArrayList();
			Integer[] arrayLigacaoAguaSituacao = form.getIdsLigacaoAguaSituacaoSelecionadas();
			for(int i = 0; i < arrayLigacaoAguaSituacao.length; i++){

				Integer idLigacaoAguaSituacao = arrayLigacaoAguaSituacao[i];
				colecaoIdsLigacaoAguaSituacaoSelecionadas.add(idLigacaoAguaSituacao);
			}

			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimplesColecao(FiltroLigacaoAguaSituacao.ID,
							colecaoIdsLigacaoAguaSituacaoSelecionadas));
			filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

			Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,
							LigacaoAguaSituacao.class.getName());

			sessao.setAttribute("colecaoIdsLigacaoAguaSituacaoSelecionadas", colecaoLigacaoAguaSituacao);

		}else{
			sessao.setAttribute("colecaoIdsLigacaoAguaSituacaoSelecionadas", null);
		}

		// Situação da Ligação de Esgoto
		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsLigacaoEsgotoSituacaoSelecionadas())){

			Collection colecaoIdsLigacaoEsgotoSituacaoSelecionadas = new ArrayList();
			Integer[] arrayLigacaoEsgotoSituacao = form.getIdsLigacaoEsgotoSituacaoSelecionadas();
			for(int i = 0; i < arrayLigacaoEsgotoSituacao.length; i++){

				Integer idLigacaoEsgotoSituacao = arrayLigacaoEsgotoSituacao[i];
				colecaoIdsLigacaoEsgotoSituacaoSelecionadas.add(idLigacaoEsgotoSituacao);
			}

			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimplesColecao(FiltroLigacaoEsgotoSituacao.ID,
							colecaoIdsLigacaoEsgotoSituacaoSelecionadas));
			filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

			Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
							LigacaoEsgotoSituacao.class.getName());

			sessao.setAttribute("colecaoIdsLigacaoEsgotoSituacaoSelecionadas", colecaoLigacaoEsgotoSituacao);

		}else{
			sessao.setAttribute("colecaoIdsLigacaoEsgotoSituacaoSelecionadas", null);
		}

		// Motivo da Retirada
		if(!Util.isVazioOrNuloOuMenosUm(form.getIdsMotivoRetiradaSelecionados())){

			Collection colecaoIdsMotivoRetiradaSelecionadas = new ArrayList();
			Integer[] arrayMotivoRetirada = form.getIdsMotivoRetiradaSelecionados();
			for(int i = 0; i < arrayMotivoRetirada.length; i++){

				Integer idMotivoRetirada = arrayMotivoRetirada[i];
				colecaoIdsMotivoRetiradaSelecionadas.add(idMotivoRetirada);
			}

			FiltroImovelCobrancaMotivoRetirada filtroImovelCobrancaMotivoRetirada = new FiltroImovelCobrancaMotivoRetirada();
			filtroImovelCobrancaMotivoRetirada.adicionarParametro(new ParametroSimplesColecao(FiltroImovelCobrancaMotivoRetirada.ID,
							colecaoIdsMotivoRetiradaSelecionadas));
			filtroImovelCobrancaMotivoRetirada.setCampoOrderBy(FiltroImovelCobrancaMotivoRetirada.DESCRICAO);

			Collection<ImovelCobrancaMotivoRetirada> colecaoImovelCobrancaMotivoRetirada = fachada.pesquisar(
							filtroImovelCobrancaMotivoRetirada, ImovelCobrancaMotivoRetirada.class.getName());

			sessao.setAttribute("colecaoIdsMotivoRetiradaSelecionados", colecaoImovelCobrancaMotivoRetirada);

		}else{
			sessao.setAttribute("colecaoIdsMotivoRetiradaSelecionados", null);
		}

		// Dados da Inscrição
		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) || !Util.isVazioOuBranco(form.getCodigoSetorComercialInicial())
						|| !Util.isVazioOuBranco(form.getNumeroQuadraInicial()) || !Util.isVazioOuBranco(form.getIdLocalidadeFinal())
						|| !Util.isVazioOuBranco(form.getCodigoSetorComercialFinal()) || !Util.isVazioOuBranco(form.getNumeroQuadraFinal())){

			httpServletRequest.setAttribute("habilitaImovel", "false");
			httpServletRequest.setAttribute("habilitaCliente", "false");
			httpServletRequest.setAttribute("habilitaGerenciaRegional", "false");
			httpServletRequest.setAttribute("habilitaUnidadeNegocio", "false");
			httpServletRequest.setAttribute("habilitaArquivo", "false");

			if(!Util.isVazioOuBranco(form.getIdLocalidadeFinal())){

				if(Util.obterInteger(form.getIdLocalidadeFinal()).intValue() > Util.obterInteger(form.getIdLocalidadeInicial()).intValue()){

					httpServletRequest.setAttribute("habilitaSetor", "false");
					httpServletRequest.setAttribute("habilitaQuadra", "false");
				}else{

					httpServletRequest.setAttribute("habilitaSetor", "true");
					httpServletRequest.setAttribute("habilitaQuadra", "true");
				}

				if(!Util.isVazioOuBranco(form.getCodigoSetorComercialFinal())){

					if(Util.obterInteger(form.getCodigoSetorComercialFinal()).intValue() > Util.obterInteger(
									form.getCodigoSetorComercialInicial()).intValue()){

						httpServletRequest.setAttribute("habilitaQuadra", "false");
					}else{

						httpServletRequest.setAttribute("habilitaQuadra", "true");
					}
				}
			}else{

				httpServletRequest.setAttribute("habilitaSetor", "true");
				httpServletRequest.setAttribute("habilitaQuadra", "true");
			}
		}else{

			httpServletRequest.setAttribute("habilitaSetor", "true");
			httpServletRequest.setAttribute("habilitaQuadra", "true");
		}

		String limpar = (String) httpServletRequest.getParameter("limpar");
		if(limpar != null && limpar.equals("true")){

			form.reset();
			httpServletRequest.setAttribute("limpar", "true");
			httpServletRequest.setAttribute("habilitaGerenciaRegional", "true");
			httpServletRequest.setAttribute("habilitaUnidadeNegocio", "true");
			httpServletRequest.setAttribute("habilitaInscricao", "true");
			httpServletRequest.setAttribute("habilitaArquivo", "true");
			httpServletRequest.setAttribute("habilitaImovel", "true");
			httpServletRequest.setAttribute("habilitaCliente", "true");
			httpServletRequest.setAttribute("habilitaSubCategoria", "true");
		}else{

			FormFile formFile = form.getArquivoMatricula();

			if(formFile != null && !formFile.getFileName().toString().equals("")){
				httpServletRequest.setAttribute("habilitaImovel", "false");
				httpServletRequest.setAttribute("habilitaCliente", "false");
				httpServletRequest.setAttribute("habilitaGerenciaRegional", "false");
				httpServletRequest.setAttribute("habilitaUnidadeNegocio", "false");
				httpServletRequest.setAttribute("habilitaInscricao", "false");
			}
		}

	}
}
