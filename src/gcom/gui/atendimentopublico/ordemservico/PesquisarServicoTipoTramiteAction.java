
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoTipoTramite;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pesquisar Serviço Tipo Trâmite
 * [UC0410] Inserir Serviço Tipo
 * [UC0412] Manter Tipo de Serviço
 * 
 * @author Hebert Falcão
 * @date 11/02/2012
 */
public class PesquisarServicoTipoTramiteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("pesquisarServicoTipoTramiteAction");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Instância do formulário que está sendo utilizado
		PesquisarServicoTipoTramiteActionForm form = (PesquisarServicoTipoTramiteActionForm) actionForm;

		// Parâmetros recebidos para adicionar um serviço
		String idStr = form.getId();
		String idLocalidadeStr = form.getIdLocalidade();
		String codigoSetorComercialStr = form.getCodigoSetorComercial();
		String idUnidadeOrganizacionalOrigemStr = form.getIdUnidadeOrganizacionalOrigem();
		String idUnidadeOrganizacionalDestinoStr = form.getIdUnidadeOrganizacionalDestino();

		// Gerando o objeto ServicoTipoTramite que será inserido na coleção
		ServicoTipoTramite servicoTipoTramite = new ServicoTipoTramite();

		// Id
		if(!Util.isVazioOuBranco(idStr)){
			Integer id = Integer.valueOf(idStr);

			servicoTipoTramite.setId(id);
		}else{
			Date dataAtual = new Date();
			Long idL = dataAtual.getTime();
			Integer id = idL.intValue();

			servicoTipoTramite.setId(id);
		}

		// Localidade
		if(!Util.isVazioOuBranco(idLocalidadeStr)){
			FiltroLocalidade filtro = new FiltroLocalidade();
			filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeStr));

			Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoLocalidade)){
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				servicoTipoTramite.setLocalidade(localidade);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}
		}

		// Setor Comercial
		if(!Util.isVazioOuBranco(codigoSetorComercialStr) && !Util.isVazioOuBranco(idLocalidadeStr)){
			FiltroSetorComercial filtro = new FiltroSetorComercial();
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidadeStr));
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercialStr));

			Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtro, SetorComercial.class.getName());

			if(!Util.isVazioOrNulo(colecaoSetorComercial)){
				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				servicoTipoTramite.setSetorComercial(setorComercial);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial");
			}
		}else if(!Util.isVazioOuBranco(codigoSetorComercialStr) && Util.isVazioOuBranco(idLocalidadeStr)){
			throw new ActionServletException("atencao.required", null, "Localidade");
		}

		// Unidade Organizacional Origem
		if(!Util.isVazioOuBranco(idUnidadeOrganizacionalOrigemStr)){
			FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
			filtro
							.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalOrigemStr));

			Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
							.getName());

			if(!Util.isVazioOrNulo(colecaoUnidadeOrganizacional)){
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
								.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

				servicoTipoTramite.setUnidadeOrganizacionalOrigem(unidadeOrganizacional);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Organizacional Origem");
			}
		}

		// Unidade Organizacional Destino
		if(!Util.isVazioOuBranco(idUnidadeOrganizacionalDestinoStr)){
			FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
			filtro
							.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalDestinoStr));

			Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
							.getName());

			if(!Util.isVazioOrNulo(colecaoUnidadeOrganizacional)){
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
								.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

				servicoTipoTramite.setUnidadeOrganizacionalDestino(unidadeOrganizacional);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Organizacional Destino");
			}
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Unidade Organizacional Destino");
		}

		// Colocando o objeto gerado na coleção que ficará na sessão
		Collection<ServicoTipoTramite> colecaoServicoTipoTramite = (ArrayList<ServicoTipoTramite>) sessao
						.getAttribute("colecaoServicoTipoTramite");

		if(colecaoServicoTipoTramite == null){
			colecaoServicoTipoTramite = new ArrayList<ServicoTipoTramite>();
		}

		String idServicoTipoTramiteEditar = httpServletRequest.getParameter("idServicoTipoTramiteEditar");

		if(!Util.isVazioOuBranco(idServicoTipoTramiteEditar)){
			// Editar

			if(colecaoServicoTipoTramite.isEmpty()){
				throw new ActionServletException("atencao.campo.informado", null, "Serviço Tramite");
			}else{
				// [FS0020] - Criticar duplicidade de configuração de trâmite
				this.validarAdicionarServicoTipoTramite(colecaoServicoTipoTramite, servicoTipoTramite);

				Integer idServicoTipoTramiteAux = null;

				for(ServicoTipoTramite servicoTipoTramiteAux : colecaoServicoTipoTramite){
					idServicoTipoTramiteAux = servicoTipoTramiteAux.getId();

					if(idServicoTipoTramiteAux.toString().equals(idServicoTipoTramiteEditar)){
						try{
							PropertyUtils.copyProperties(servicoTipoTramiteAux, servicoTipoTramite);
						}catch(IllegalAccessException e){
							throw new ActionServletException("erro.sistema", e);
						}catch(InvocationTargetException e){
							throw new ActionServletException("erro.sistema", e);
						}catch(NoSuchMethodException e){
							throw new ActionServletException("erro.sistema", e);
						}

						break;
					}
				}

				sessao.setAttribute("colecaoServicoTipoTramite", colecaoServicoTipoTramite);
			}
		}else{
			// Inserir

			if(colecaoServicoTipoTramite.isEmpty()){
				colecaoServicoTipoTramite.add(servicoTipoTramite);
			}else{
				// [FS0020] - Criticar duplicidade de configuração de trâmite
				this.validarAdicionarServicoTipoTramite(colecaoServicoTipoTramite, servicoTipoTramite);

				colecaoServicoTipoTramite.add(servicoTipoTramite);

				// Ordenar por um único campo
				BeanComparator comparador = new BeanComparator("id");
				Collections.sort((List) colecaoServicoTipoTramite, comparador);
			}

			sessao.setAttribute("colecaoServicoTipoTramite", colecaoServicoTipoTramite);
		}

		httpServletRequest.setAttribute("inclusaoConfirmada", "true");

		return retorno;
	}

	// [FS0020] - Criticar duplicidade de configuração de trâmite
	private void validarAdicionarServicoTipoTramite(Collection<ServicoTipoTramite> colecaoServicoTipoTramite,
					ServicoTipoTramite servicoTipoTramite){

		// Id - Novo
		Integer id = servicoTipoTramite.getId();

		// Localidade - Novo
		Integer idLocalidade = null;
		Localidade localidade = servicoTipoTramite.getLocalidade();

		if(localidade != null){
			idLocalidade = localidade.getId();
		}

		// Setor Comercial - Novo
		Integer idSetorComercial = null;
		SetorComercial setorComercial = servicoTipoTramite.getSetorComercial();

		if(setorComercial != null){
			idSetorComercial = setorComercial.getId();
		}

		// Unidade Organizacional Origem - Novo
		Integer idUnidadeOrganizacionalOrigem = null;
		UnidadeOrganizacional unidadeOrganizacionalOrigem = servicoTipoTramite.getUnidadeOrganizacionalOrigem();

		if(unidadeOrganizacionalOrigem != null){
			idUnidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem.getId();
		}

		boolean localidadeIgual = false;
		boolean setorComercialIgual = false;
		boolean unidadeOrganizacionalOrigemIgual = false;

		Integer idAux = null;

		Integer idLocalidadeAux = null;
		Localidade localidadeAux = null;

		Integer idSetorComercialAux = null;
		SetorComercial setorComercialAux = null;

		Integer idUnidadeOrganizacionalOrigemAux = null;
		UnidadeOrganizacional unidadeOrganizacionalOrigemAux = null;

		for(ServicoTipoTramite servicoTipoTramiteAux : colecaoServicoTipoTramite){
			// Id - Existente
			idAux = servicoTipoTramiteAux.getId();

			// Localidade - Existente
			localidadeAux = servicoTipoTramiteAux.getLocalidade();

			if(localidadeAux != null){
				idLocalidadeAux = localidadeAux.getId();
			}

			// Setor Comercial - Existente
			setorComercialAux = servicoTipoTramiteAux.getSetorComercial();

			if(setorComercialAux != null){
				idSetorComercialAux = setorComercialAux.getId();
			}

			// Unidade Organizacional Origem - Existente
			unidadeOrganizacionalOrigemAux = servicoTipoTramiteAux.getUnidadeOrganizacionalOrigem();

			if(unidadeOrganizacionalOrigemAux != null){
				idUnidadeOrganizacionalOrigemAux = unidadeOrganizacionalOrigemAux.getId();
			}

			// Compara Localidade
			if((idLocalidadeAux == null && idLocalidade == null) || (idLocalidadeAux != null && idLocalidadeAux.equals(idLocalidade))){
				localidadeIgual = true;
			}else{
				localidadeIgual = false;
			}

			// Compara Setor Comercial
			if((idSetorComercialAux == null && idSetorComercial == null)
							|| (idSetorComercialAux != null && idSetorComercialAux.equals(idSetorComercial))){
				setorComercialIgual = true;
			}else{
				setorComercialIgual = false;
			}

			// Compara Unidade Organizacional
			if((idUnidadeOrganizacionalOrigemAux == null && idUnidadeOrganizacionalOrigem == null)
							|| (idUnidadeOrganizacionalOrigemAux != null && idUnidadeOrganizacionalOrigemAux
											.equals(idUnidadeOrganizacionalOrigem))){
				unidadeOrganizacionalOrigemIgual = true;
			}else{
				unidadeOrganizacionalOrigemIgual = false;
			}

			// Compara o objeto
			if(!idAux.equals(id) && localidadeIgual && setorComercialIgual && unidadeOrganizacionalOrigemIgual){
				throw new ActionServletException("atencao.configuracao.tramite.ja.informado");
			}
		}
	}
}
