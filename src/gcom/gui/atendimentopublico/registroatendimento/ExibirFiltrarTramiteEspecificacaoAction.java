
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroBacia;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroSetorAbastecimento;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubBacia;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubBacia;
import gcom.operacional.SubsistemaEsgoto;
import gcom.operacional.ZonaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtrar Trâmite Especificação
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class ExibirFiltrarTramiteEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarTramiteEspecificacao");

		FiltrarTramiteEspecificacaoActionForm form = (FiltrarTramiteEspecificacaoActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String limpar = httpServletRequest.getParameter("limpar");
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(limpar != null && limpar.trim().equals("S")){
			String numeroNaoInformadoStr = String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO);

			form.setIdSolicitacaoTipo(numeroNaoInformadoStr);
			form.setIdSolicitacaoTipoEspecificacao(numeroNaoInformadoStr);
			form.setIdLocalidade("");
			form.setDescricaoLocalidade("");
			form.setCodigoSetorComercial("");
			form.setDescricaoSetorComercial("");
			form.setIdMunicipio("");
			form.setNomeMunicipio("");
			form.setCodigoBairro("");
			form.setNomeBairro("");
			form.setIdSistemaAbastecimento(numeroNaoInformadoStr);
			form.setIdDistritoOperacional(numeroNaoInformadoStr);
			form.setIdZonaAbastecimento(numeroNaoInformadoStr);
			form.setIdSetorAbastecimento(numeroNaoInformadoStr);
			form.setIdSistemaEsgoto(numeroNaoInformadoStr);
			form.setIdSubsistemaEsgoto(numeroNaoInformadoStr);
			form.setIdBacia(numeroNaoInformadoStr);
			form.setIdSubBacia(numeroNaoInformadoStr);
			form.setIdUnidadeOrganizacionalOrigem("");
			form.setDescricaoUnidadeOrganizacionalOrigem("");
			form.setIdUnidadeOrganizacionalDestino("");
			form.setDescricaoUnidadeOrganizacionalDestino("");
			form.setAtualizar(true);

			sessao.removeAttribute("colecaoSolicitacaoTipoFiltro");
			sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacaoFiltro");
			sessao.removeAttribute("colecaoSistemaAbastecimentoFiltro");
			sessao.removeAttribute("colecaoDistritoOperacionalFiltro");
			sessao.removeAttribute("colecaoZonaAbastecimentoFiltro");
			sessao.removeAttribute("colecaoSetorAbastecimentoFiltro");
			sessao.removeAttribute("colecaoSistemaEsgotoFiltro");
			sessao.removeAttribute("colecaoSubsistemaEsgotoFiltro");
			sessao.removeAttribute("colecaoBaciaFiltro");
			sessao.removeAttribute("colecaoSubBaciaFiltro");
		}

		if(objetoConsulta != null && !objetoConsulta.trim().equals("")){
			String idLocalidade = form.getIdLocalidade();
			String codigoSetorComercial = form.getCodigoSetorComercial();
			String idMunicipio = form.getIdMunicipio();
			String codigoBairro = form.getCodigoBairro();
			String idUnidadeOrganizacionalOrigem = form.getIdUnidadeOrganizacionalOrigem();
			String idUnidadeOrganizacionalDestino = form.getIdUnidadeOrganizacionalDestino();

			// Limpando campos de descrição quando o id/código é vazio
			if(idLocalidade != null && idLocalidade.trim().equals("")){
				form.setDescricaoLocalidade("");
			}

			if(codigoSetorComercial != null && codigoSetorComercial.trim().equals("")){
				form.setDescricaoSetorComercial("");
			}

			if(idMunicipio != null && idMunicipio.trim().equals("")){
				form.setNomeMunicipio("");
			}

			if(codigoBairro != null && codigoBairro.trim().equals("")){
				form.setNomeBairro("");
			}

			if(idUnidadeOrganizacionalOrigem != null && idUnidadeOrganizacionalOrigem.trim().equals("")){
				form.setDescricaoUnidadeOrganizacionalOrigem("");
			}

			if(idUnidadeOrganizacionalDestino != null && idUnidadeOrganizacionalDestino.trim().equals("")){
				form.setDescricaoUnidadeOrganizacionalDestino("");
			}

			if(objetoConsulta.equals("1")){
				// Localidade
				if(idLocalidade != null && !idLocalidade.trim().equals("")){
					FiltroLocalidade filtro = new FiltroLocalidade();
					filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

					Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());

					if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
						Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

						String id = Util.adicionarZerosEsqueda(3, idLocalidade);
						form.setIdLocalidade(id);

						String descricao = localidade.getDescricao();
						form.setDescricaoLocalidade(descricao);
					}else{
						httpServletRequest.setAttribute("localidadeNaoEncontrada", "true");

						form.setIdLocalidade("");
						form.setDescricaoLocalidade("");

						form.setCodigoSetorComercial("");
						form.setDescricaoSetorComercial("");

						httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
					}
				}
			}else if(objetoConsulta.equals("2")){
				// Setor Comercial
				if(codigoSetorComercial != null && !codigoSetorComercial.trim().equals("")){
					if(idLocalidade != null && !idLocalidade.trim().equals("")){
						FiltroSetorComercial filtro = new FiltroSetorComercial();
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));

						Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtro, SetorComercial.class.getName());

						if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){
							SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

							String codigo = Util.adicionarZerosEsqueda(3, codigoSetorComercial);
							form.setCodigoSetorComercial(codigo);

							String descricao = setorComercial.getDescricao();
							form.setDescricaoSetorComercial(descricao);
						}else{
							httpServletRequest.setAttribute("setorComercialNaoEncontrado", "true");

							form.setCodigoSetorComercial("");
							form.setDescricaoSetorComercial("");

							httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
						}
					}

				}else{
					form.setCodigoSetorComercial("");
					form.setDescricaoSetorComercial("");
				}
			}else if(objetoConsulta.equals("3")){
				// Município
				if(idMunicipio != null && !idMunicipio.trim().equals("")){
					FiltroMunicipio filtro = new FiltroMunicipio();
					filtro.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));

					Collection<Municipio> colecaoMunicipio = fachada.pesquisar(filtro, Municipio.class.getName());

					if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){
						Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);

						String id = Util.adicionarZerosEsqueda(4, idMunicipio);
						form.setIdMunicipio(id);

						String nome = municipio.getNome();
						form.setNomeMunicipio(nome);

						httpServletRequest.setAttribute("nomeCampo", "codigoBairro");
					}else{
						httpServletRequest.setAttribute("municipioNaoEncontrado", "true");

						form.setIdMunicipio("");
						form.setNomeMunicipio("");

						form.setCodigoBairro("");
						form.setNomeBairro("");

						httpServletRequest.setAttribute("nomeCampo", "idMunicipio");
					}
				}
			}else if(objetoConsulta.equals("4")){
				// Bairro
				if(codigoBairro != null && !codigoBairro.trim().equals("")){
					if(idMunicipio != null && !idMunicipio.trim().equals("")){
						FiltroBairro filtro = new FiltroBairro();
						filtro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
						filtro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));
						filtro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, codigoBairro));

						Collection<Bairro> colecaoBairro = fachada.pesquisar(filtro, Bairro.class.getName());

						if(colecaoBairro != null && !colecaoBairro.isEmpty()){
							Bairro setorComercial = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

							String codigo = Util.adicionarZerosEsqueda(4, codigoBairro);
							form.setCodigoBairro(codigo);

							String nome = setorComercial.getNome();
							form.setNomeBairro(nome);
						}else{
							httpServletRequest.setAttribute("bairroNaoEncontrado", "true");

							form.setCodigoBairro("");
							form.setNomeBairro("");

							httpServletRequest.setAttribute("nomeCampo", "codigoBairro");
						}
					}

				}else{
					form.setCodigoBairro("");
					form.setNomeBairro("");
				}
			}else if(objetoConsulta.equals("5")){
				// Unidade Organizacional Origem
				if(idUnidadeOrganizacionalOrigem != null && !idUnidadeOrganizacionalOrigem.trim().equals("")){
					FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalOrigem));

					Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
									.getName());

					if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){
						UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
										.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

						String id = Util.adicionarZerosEsqueda(8, idUnidadeOrganizacionalOrigem);
						form.setIdUnidadeOrganizacionalOrigem(id);

						String descricao = unidadeOrganizacional.getDescricao();
						form.setDescricaoUnidadeOrganizacionalOrigem(descricao);
					}else{
						httpServletRequest.setAttribute("unidadeOrganizacionalOrigemNaoEncontrado", "true");

						form.setIdUnidadeOrganizacionalOrigem("");
						form.setDescricaoUnidadeOrganizacionalOrigem("");

						httpServletRequest.setAttribute("nomeCampo", "idUnidadeOrganizacionalOrigem");
					}
				}
			}else if(objetoConsulta.equals("6")){
				// Unidade Organizacional Destino
				if(idUnidadeOrganizacionalDestino != null && !idUnidadeOrganizacionalDestino.trim().equals("")){
					FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalDestino));

					Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
									.getName());

					if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){
						UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
										.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

						String id = Util.adicionarZerosEsqueda(8, idUnidadeOrganizacionalDestino);
						form.setIdUnidadeOrganizacionalDestino(id);

						String descricao = unidadeOrganizacional.getDescricao();
						form.setDescricaoUnidadeOrganizacionalDestino(descricao);
					}else{
						httpServletRequest.setAttribute("unidadeOrganizacionalDestinoNaoEncontrado", "true");

						form.setIdUnidadeOrganizacionalDestino("");
						form.setDescricaoUnidadeOrganizacionalDestino("");

						httpServletRequest.setAttribute("nomeCampo", "idUnidadeOrganizacionalDestino");
					}
				}
			}
		}else{
			// Tipo de Solicitação
			Collection<SolicitacaoTipo> colecaoSolicitacaoTipoFiltro = (Collection<SolicitacaoTipo>) sessao
							.getAttribute("colecaoSolicitacaoTipoFiltro");

			if(colecaoSolicitacaoTipoFiltro == null){
				colecaoSolicitacaoTipoFiltro = getColecaoSolicitacaoTipo();
				sessao.setAttribute("colecaoSolicitacaoTipoFiltro", colecaoSolicitacaoTipoFiltro);
			}

			// Especificação
			Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacaoFiltro = (Collection<SolicitacaoTipoEspecificacao>) sessao
							.getAttribute("colecaoSolicitacaoTipoEspecificacaoFiltro");

			if(colecaoSolicitacaoTipoEspecificacaoFiltro == null){
				colecaoSolicitacaoTipoEspecificacaoFiltro = getColecaoSolicitacaoTipoEspecificacao();
				sessao.setAttribute("colecaoSolicitacaoTipoEspecificacaoFiltro", colecaoSolicitacaoTipoEspecificacaoFiltro);
			}

			// Sistema de Abastecimento
			Collection<SistemaAbastecimento> colecaoSistemaAbastecimentoFiltro = (Collection<SistemaAbastecimento>) sessao
							.getAttribute("colecaoSistemaAbastecimentoFiltro");

			if(colecaoSistemaAbastecimentoFiltro == null){
				colecaoSistemaAbastecimentoFiltro = getColecaoSistemaAbastecimento();
				sessao.setAttribute("colecaoSistemaAbastecimentoFiltro", colecaoSistemaAbastecimentoFiltro);
			}

			// Unidade Operacional
			Collection<DistritoOperacional> colecaoDistritoOperacionalFiltro = (Collection<DistritoOperacional>) sessao
							.getAttribute("colecaoDistritoOperacionalFiltro");

			if(colecaoDistritoOperacionalFiltro == null){
				colecaoDistritoOperacionalFiltro = getColecaoDistritoOperacional();
				sessao.setAttribute("colecaoDistritoOperacionalFiltro", colecaoDistritoOperacionalFiltro);
			}

			// Zona de Abastecimento
			Collection<ZonaAbastecimento> colecaoZonaAbastecimentoFiltro = (Collection<ZonaAbastecimento>) sessao
							.getAttribute("colecaoZonaAbastecimentoFiltro");

			if(colecaoZonaAbastecimentoFiltro == null){
				colecaoZonaAbastecimentoFiltro = getColecaoZonaAbastecimento();
				sessao.setAttribute("colecaoZonaAbastecimentoFiltro", colecaoZonaAbastecimentoFiltro);
			}

			// Setor de Abastecimento
			Collection<SetorAbastecimento> colecaoSetorAbastecimentoFiltro = (Collection<SetorAbastecimento>) sessao
							.getAttribute("colecaoSetorAbastecimentoFiltro");

			if(colecaoSetorAbastecimentoFiltro == null){
				colecaoSetorAbastecimentoFiltro = getColecaoSetorAbastecimento();
				sessao.setAttribute("colecaoSetorAbastecimentoFiltro", colecaoSetorAbastecimentoFiltro);
			}

			// Sistema de Esgoto
			Collection<SistemaEsgoto> colecaoSistemaEsgotoFiltro = (Collection<SistemaEsgoto>) sessao
							.getAttribute("colecaoSistemaEsgotoFiltro");

			if(colecaoSistemaEsgotoFiltro == null){
				colecaoSistemaEsgotoFiltro = getColecaoSistemaEsgoto();
				sessao.setAttribute("colecaoSistemaEsgotoFiltro", colecaoSistemaEsgotoFiltro);
			}

			// Subsitema de Esgoto
			Collection<SubsistemaEsgoto> colecaoSubsistemaEsgotoFiltro = (Collection<SubsistemaEsgoto>) sessao
							.getAttribute("colecaoSubsistemaEsgotoFiltro");

			if(colecaoSubsistemaEsgotoFiltro == null){
				colecaoSubsistemaEsgotoFiltro = getColecaoSubsistemaEsgoto();
				sessao.setAttribute("colecaoSubsistemaEsgotoFiltro", colecaoSubsistemaEsgotoFiltro);
			}

			// Bacia
			Collection<Bacia> colecaoBaciaFiltro = (Collection<Bacia>) sessao.getAttribute("colecaoBaciaFiltro");

			if(colecaoBaciaFiltro == null){
				colecaoBaciaFiltro = getColecaoBacia();
				sessao.setAttribute("colecaoBaciaFiltro", colecaoBaciaFiltro);
			}

			// SubBacia
			Collection<SubBacia> colecaoSubBaciaFiltro = (Collection<SubBacia>) sessao.getAttribute("colecaoSubBaciaFiltro");

			if(colecaoSubBaciaFiltro == null){
				colecaoSubBaciaFiltro = getColecaoSubBacia();
				sessao.setAttribute("colecaoSubBaciaFiltro", colecaoSubBaciaFiltro);
			}
		}

		return retorno;
	}

	/**
	 * Retorna Coleção de Solicitação Tipo
	 */

	private Collection<SolicitacaoTipo> getColecaoSolicitacaoTipo(){

		FiltroSolicitacaoTipo filtro = new FiltroSolicitacaoTipo();
		filtro.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SolicitacaoTipo> colecao = fachada.pesquisar(filtro, SolicitacaoTipo.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Solicitação");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Solicitação Tipo Especificação
	 */

	private Collection<SolicitacaoTipoEspecificacao> getColecaoSolicitacaoTipoEspecificacao(){

		FiltroSolicitacaoTipoEspecificacao filtro = new FiltroSolicitacaoTipoEspecificacao();
		filtro.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SolicitacaoTipoEspecificacao> colecao = fachada.pesquisar(filtro, SolicitacaoTipoEspecificacao.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Especificação");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Sistema de Abastecimento
	 */

	private Collection<SistemaAbastecimento> getColecaoSistemaAbastecimento(){

		FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
		filtro.setCampoOrderBy(FiltroSistemaAbastecimento.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SistemaAbastecimento> colecao = fachada.pesquisar(filtro, SistemaAbastecimento.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Sistema de Abastecimento");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Distrito Operacional
	 */

	private Collection<DistritoOperacional> getColecaoDistritoOperacional(){

		FiltroDistritoOperacional filtro = new FiltroDistritoOperacional();
		filtro.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<DistritoOperacional> colecao = fachada.pesquisar(filtro, DistritoOperacional.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Distrito Operacional");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Zona de Abastecimento
	 */

	private Collection<ZonaAbastecimento> getColecaoZonaAbastecimento(){

		FiltroZonaAbastecimento filtro = new FiltroZonaAbastecimento();
		filtro.setCampoOrderBy(FiltroZonaAbastecimento.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<ZonaAbastecimento> colecao = fachada.pesquisar(filtro, ZonaAbastecimento.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Zona de Abastecimento");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Setor de Abastecimento
	 */

	private Collection<SetorAbastecimento> getColecaoSetorAbastecimento(){

		FiltroSetorAbastecimento filtro = new FiltroSetorAbastecimento();
		filtro.setCampoOrderBy(FiltroSetorAbastecimento.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SetorAbastecimento> colecao = fachada.pesquisar(filtro, SetorAbastecimento.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Setor de Abastecimento");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Sistema de Esgoto
	 */

	private Collection<SistemaEsgoto> getColecaoSistemaEsgoto(){

		FiltroSistemaEsgoto filtro = new FiltroSistemaEsgoto();
		filtro.setCampoOrderBy(FiltroSistemaEsgoto.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SistemaEsgoto> colecao = fachada.pesquisar(filtro, SistemaEsgoto.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Sistema de Esgoto");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Subsistema de Esgoto
	 */

	private Collection<SubsistemaEsgoto> getColecaoSubsistemaEsgoto(){

		FiltroSubsistemaEsgoto filtro = new FiltroSubsistemaEsgoto();
		filtro.setCampoOrderBy(FiltroSubsistemaEsgoto.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SubsistemaEsgoto> colecao = fachada.pesquisar(filtro, SubsistemaEsgoto.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Subsistema de Esgoto");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Bacia
	 */

	private Collection<Bacia> getColecaoBacia(){

		FiltroBacia filtro = new FiltroBacia();
		filtro.setCampoOrderBy(FiltroBacia.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<Bacia> colecao = fachada.pesquisar(filtro, Bacia.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Bacia");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de SubBacia
	 */

	private Collection<SubBacia> getColecaoSubBacia(){

		FiltroSubBacia filtro = new FiltroSubBacia();
		filtro.setCampoOrderBy(FiltroSubBacia.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSubBacia.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SubBacia> colecao = fachada.pesquisar(filtro, SubBacia.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Subbacia");
		}

		return colecao;
	}

}