
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.EspecificacaoTramite;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
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
 * Atualizar Trâmite Especificação
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class AtualizarTramiteEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarTramiteEspecificacaoActionForm form = (AtualizarTramiteEspecificacaoActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Valida o Formulário
		this.validarForm(form);

		EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) sessao.getAttribute("especificacaoTramite");

		// Popula a entidade
		form.setEspecificacaoTramiteValues(especificacaoTramite);

		fachada.atualizarTramiteEspecificacao(especificacaoTramite);

		montarPaginaSucesso(httpServletRequest, "Trâmite por Especificação de id " + form.getId() + " atualizada com sucesso.",
						"Realizar outra Manutenção de Trâmite por Especificação", "exibirFiltrarTramiteEspecificacaoAction.do?menu=sim");

		sessao.removeAttribute("especificacaoTramite");

		return retorno;
	}

	/**
	 * Validar campos do form
	 */
	private void validarForm(AtualizarTramiteEspecificacaoActionForm form){

		String idLocalidade = form.getIdLocalidade();
		String codigoSetorComercial = form.getCodigoSetorComercial();
		String idMunicipio = form.getIdMunicipio();
		String codigoBairro = form.getCodigoBairro();
		String idSistemaAbastecimento = form.getIdSistemaAbastecimento();
		String idDistritoOperacional = form.getIdDistritoOperacional();
		String idZonaAbastecimento = form.getIdZonaAbastecimento();
		String idSetorAbastecimento = form.getIdSetorAbastecimento();
		String idSistemaEsgoto = form.getIdSistemaEsgoto();
		String idSubsistemaEsgoto = form.getIdSubsistemaEsgoto();
		String idBacia = form.getIdBacia();
		String idSubBacia = form.getIdSubBacia();
		String idUnidadeOrganizacionalOrigem = form.getIdUnidadeOrganizacionalOrigem();
		String idUnidadeOrganizacionalDestino = form.getIdUnidadeOrganizacionalDestino();
		String indicadorUso = form.getIndicadorUso();

		String numeroNaoInformadoStr = Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO);

		Fachada fachada = Fachada.getInstancia();

		// Localidade
		if(!Util.isVazioOuBranco(idLocalidade)){
			FiltroLocalidade filtro = new FiltroLocalidade();
			filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

			Collection<Localidade> colecao = fachada.pesquisar(filtro, Localidade.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}
		}

		// Setor Comercial
		if(Util.isVazioOuBranco(idLocalidade) && !Util.isVazioOuBranco(codigoSetorComercial)){
			throw new ActionServletException("atencao.required", null, "Localidade");
		}else if(!Util.isVazioOuBranco(idLocalidade) && !Util.isVazioOuBranco(codigoSetorComercial)){
			FiltroSetorComercial filtro = new FiltroSetorComercial();
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));

			Collection<SetorComercial> colecao = fachada.pesquisar(filtro, SetorComercial.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial");
			}
		}

		// Bairro
		if(Util.isVazioOuBranco(idMunicipio) && !Util.isVazioOuBranco(codigoBairro)){
			throw new ActionServletException("atencao.required", null, "Município");
		}else if(!Util.isVazioOuBranco(idMunicipio) && !Util.isVazioOuBranco(codigoBairro)){
			FiltroBairro filtro = new FiltroBairro();
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, codigoBairro));

			Collection<Bairro> colecao = fachada.pesquisar(filtro, Bairro.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
			}
		}

		// Sistema de Abastecimento
		if(!Util.isVazioOuBranco(idSistemaAbastecimento) && !idSistemaAbastecimento.equals(numeroNaoInformadoStr)){
			FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
			filtro
							.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.ID, idSistemaAbastecimento));

			Collection<SistemaAbastecimento> colecao = fachada.pesquisar(filtro, SistemaAbastecimento.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Sistema de Abastecimento");
			}
		}

		// Unidade Operacional
		if(!Util.isVazioOuBranco(idDistritoOperacional) && !idDistritoOperacional.equals(numeroNaoInformadoStr)){
			FiltroDistritoOperacional filtro = new FiltroDistritoOperacional();
			filtro.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, idDistritoOperacional));

			Collection<DistritoOperacional> colecao = fachada.pesquisar(filtro, DistritoOperacional.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Operacional");
			}
		}

		// Zona de Abastecimento
		if(!Util.isVazioOuBranco(idZonaAbastecimento) && !idZonaAbastecimento.equals(numeroNaoInformadoStr)){
			FiltroZonaAbastecimento filtro = new FiltroZonaAbastecimento();
			filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, idZonaAbastecimento));

			Collection<ZonaAbastecimento> colecao = fachada.pesquisar(filtro, ZonaAbastecimento.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Zona de Abastecimento");
			}
		}

		// Setor de Abastecimento
		if(!Util.isVazioOuBranco(idSetorAbastecimento) && !idSetorAbastecimento.equals(numeroNaoInformadoStr)){
			FiltroSetorAbastecimento filtro = new FiltroSetorAbastecimento();
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ID, idSetorAbastecimento));

			Collection<SetorAbastecimento> colecao = fachada.pesquisar(filtro, SetorAbastecimento.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor de Abastecimento");
			}
		}

		// Sistema de Esgoto
		if(!Util.isVazioOuBranco(idSistemaEsgoto) && !idSistemaEsgoto.equals(numeroNaoInformadoStr)){
			FiltroSistemaEsgoto filtro = new FiltroSistemaEsgoto();
			filtro.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.ID, idSistemaEsgoto));

			Collection<SistemaEsgoto> colecao = fachada.pesquisar(filtro, SistemaEsgoto.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Sistema de Esgoto");
			}
		}

		// Subsistema de Esgoto
		if(!Util.isVazioOuBranco(idSubsistemaEsgoto) && !idSubsistemaEsgoto.equals(numeroNaoInformadoStr)){
			FiltroSubsistemaEsgoto filtro = new FiltroSubsistemaEsgoto();
			filtro.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.ID, idSubsistemaEsgoto));

			Collection<SubsistemaEsgoto> colecao = fachada.pesquisar(filtro, SubsistemaEsgoto.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Subsistema de Esgoto");
			}
		}

		// Bacia
		if(!Util.isVazioOuBranco(idBacia) && !idBacia.equals(numeroNaoInformadoStr)){
			FiltroBacia filtro = new FiltroBacia();
			filtro.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroBacia.ID, idBacia));

			Collection<Bacia> colecao = fachada.pesquisar(filtro, Bacia.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bacia");
			}
		}

		// Subbacia
		if(!Util.isVazioOuBranco(idSubBacia) && !idSubBacia.equals(numeroNaoInformadoStr)){
			FiltroSubBacia filtro = new FiltroSubBacia();
			filtro.adicionarParametro(new ParametroSimples(FiltroSubBacia.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSubBacia.ID, idSubBacia));

			Collection<SubBacia> colecao = fachada.pesquisar(filtro, SubBacia.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Subbacia");
			}
		}

		// Unidade Origem
		if(!Util.isVazioOuBranco(idUnidadeOrganizacionalOrigem)){
			FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
			filtro
							.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalOrigem));

			Collection<UnidadeOrganizacional> colecao = fachada.pesquisar(filtro, UnidadeOrganizacional.class.getName());
			if(colecao == null || colecao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Origem");
			}
		}

		// Unidade Destino
		if(Util.isVazioOuBranco(idUnidadeOrganizacionalDestino)){
			throw new ActionServletException("atencao.required", null, "Unidade Destino");
		}else{
			FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
			filtro
							.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalDestino));

			Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
							.getName());
			if(colecaoUnidadeOrganizacional == null || colecaoUnidadeOrganizacional.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Destino");
			}
		}

		// Indicador de Uso
		if(Util.isVazioOuBranco(indicadorUso)){
			throw new ActionServletException("atencao.required", null, "Indicador de Uso");
		}
	}

}
