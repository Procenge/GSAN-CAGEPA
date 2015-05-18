
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoTipoTramite;
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
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pesquisar Servi�o Tipo Tr�mite
 * [UC0410] Inserir Servi�o Tipo
 * [UC0412] Manter Tipo de Servi�o
 * 
 * @author Hebert Falc�o
 * @date 11/02/2012
 */
public class PesquisarServicoTipoTramiteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("pesquisarServicoTipoTramiteAction");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		PesquisarServicoTipoTramiteActionForm form = (PesquisarServicoTipoTramiteActionForm) actionForm;

		// Par�metros recebidos para adicionar um servi�o
		String idStr = form.getId();
		String idLocalidadeStr = form.getIdLocalidade();
		String codigoSetorComercialStr = form.getCodigoSetorComercial();
		String idUnidadeOrganizacionalOrigemStr = form.getIdUnidadeOrganizacionalOrigem();
		String idUnidadeOrganizacionalDestinoStr = form.getIdUnidadeOrganizacionalDestino();

		String idMunicipioStr = form.getIdMunicipio();
		String cdBairroStr = form.getCodigoBairro();
		String indicadorPrimeiroTramite = form.getIndicadorPrimeiroTramite();

		// Gerando o objeto ServicoTipoTramite que ser� inserido na cole��o
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

		// Bairro
		if(!Util.isVazioOuBranco(idMunicipioStr) && !Util.isVazioOuBranco(cdBairroStr)){
			FiltroBairro filtro = new FiltroBairro();
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, cdBairroStr));
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipioStr));

			Collection<Bairro> colecaoBairro = fachada.pesquisar(filtro, Bairro.class.getName());

			if(!Util.isVazioOrNulo(colecaoBairro)){
				Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

				servicoTipoTramite.setBairro(bairro);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
			}
		}

		servicoTipoTramite.setIndicadorPrimeiroTramite(Util.converterStringParaShort(indicadorPrimeiroTramite));

		// Colocando o objeto gerado na cole��o que ficar� na sess�o
		Collection<ServicoTipoTramite> colecaoServicoTipoTramite = (ArrayList<ServicoTipoTramite>) sessao
						.getAttribute("colecaoServicoTipoTramite");

		if(colecaoServicoTipoTramite == null){
			colecaoServicoTipoTramite = new ArrayList<ServicoTipoTramite>();
		}

		String idServicoTipoTramiteEditar = httpServletRequest.getParameter("idServicoTipoTramiteEditar");

		if(!Util.isVazioOuBranco(idServicoTipoTramiteEditar)){
			// Editar

			if(colecaoServicoTipoTramite.isEmpty()){
				throw new ActionServletException("atencao.campo.informado", null, "Servi�o Tramite");
			}else{
				// [FS0020] - Criticar duplicidade de configura��o de tr�mite
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
				// [FS0020] - Criticar duplicidade de configura��o de tr�mite
				this.validarAdicionarServicoTipoTramite(colecaoServicoTipoTramite, servicoTipoTramite);

				colecaoServicoTipoTramite.add(servicoTipoTramite);

				// Ordenar por um �nico campo
				BeanComparator comparador = new BeanComparator("id");
				Collections.sort((List) colecaoServicoTipoTramite, comparador);
			}

			sessao.setAttribute("colecaoServicoTipoTramite", colecaoServicoTipoTramite);
		}

		httpServletRequest.setAttribute("inclusaoConfirmada", "true");

		return retorno;
	}

	// [FS0020] - Criticar duplicidade de configura��o de tr�mite
	private void validarAdicionarServicoTipoTramite(Collection<ServicoTipoTramite> colecaoServicoTipoTramite,
					ServicoTipoTramite servicoTipoTramite){

		// Id - Novo
		// Integer id = servicoTipoTramite.getId();
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

		// Bairro
		Integer idBairro = null;
		Bairro bairro = servicoTipoTramite.getBairro();

		if(bairro != null){
			idBairro = bairro.getId();
		}

		// OrganizacionalDestino
		Integer idUnidadeOrganizacionalDestino = null;
		UnidadeOrganizacional unidadeOrganizacionalDestino = null;
		if(servicoTipoTramite.getUnidadeOrganizacionalDestino() != null){
			unidadeOrganizacionalDestino = servicoTipoTramite.getUnidadeOrganizacionalDestino();
		}


		if(unidadeOrganizacionalDestino != null){
			idUnidadeOrganizacionalDestino = unidadeOrganizacionalDestino.getId();
		}

		Short indicadorPrimeiroTramite = null;

		indicadorPrimeiroTramite = servicoTipoTramite.getIndicadorPrimeiroTramite();

		boolean localidadeIgual = false;
		boolean setorComercialIgual = false;
		boolean unidadeOrganizacionalOrigemIgual = false;
		boolean bairroIgual = false;
		boolean unidadeOrganizacionalDestinoIgual = false;
		boolean indicadorPrimeiroTramiteIgual = false;

		Integer idAux = null;

		Integer idLocalidadeAux = null;
		Localidade localidadeAux = null;

		Integer idSetorComercialAux = null;
		SetorComercial setorComercialAux = null;

		Integer idUnidadeOrganizacionalOrigemAux = null;
		UnidadeOrganizacional unidadeOrganizacionalOrigemAux = null;

		Integer idBairroAux = null;
		Bairro bairroAux = null;

		Integer idUnidadeOrganizacionalDestinoAux = null;
		UnidadeOrganizacional unidadeOrganizacionalDestinoAux = null;

		Short indicadorPrimeiroTramiteAux = null;

		for(ServicoTipoTramite servicoTipoTramiteAux : colecaoServicoTipoTramite){
			// Id - Existente
			// idAux = servicoTipoTramiteAux.getId();
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

			bairroAux = servicoTipoTramiteAux.getBairro();

			if(bairroAux != null){
				idBairroAux = bairroAux.getId();
			}

			// Unidade Organizacional Destino - Existente
			unidadeOrganizacionalDestinoAux = servicoTipoTramiteAux.getUnidadeOrganizacionalDestino();

			if(unidadeOrganizacionalDestinoAux != null){
				idUnidadeOrganizacionalDestinoAux = unidadeOrganizacionalDestinoAux.getId();
			}

			indicadorPrimeiroTramiteAux = servicoTipoTramiteAux.getIndicadorPrimeiroTramite();

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

			// Compara Bairro
			if((idBairroAux == null && idBairro == null) || (idBairroAux != null && idBairroAux.equals(idBairro))){
				bairroIgual = true;
			}else{
				bairroIgual = false;
			}

			// Compara unidadeOrganizacionalDestino
			if((idUnidadeOrganizacionalDestinoAux == null && idUnidadeOrganizacionalDestino == null)
							|| (idUnidadeOrganizacionalDestinoAux != null && idUnidadeOrganizacionalDestinoAux
											.equals(idUnidadeOrganizacionalDestino))){
				unidadeOrganizacionalDestinoIgual = true;
			}else{
				unidadeOrganizacionalDestinoIgual = false;
			}

			if(indicadorPrimeiroTramiteAux.equals(indicadorPrimeiroTramite) && indicadorPrimeiroTramite.equals(ConstantesSistema.SIM)){
				indicadorPrimeiroTramiteIgual = true;
			}else{
				indicadorPrimeiroTramiteIgual = false;
			}


			// [FS0020] - Criticar duplicidade de configura��o de tr�mite
			// . Caso a combina��o Tipo de Servi�o/Localidade/Setor Comercial/Bairro/Unidade
			// Origem/Unidade Destino j� tenha sido informada na cole��o ou j� exista na tabela
			// SERVICO_TIPO_TRAMITE, exibir a mensagem "Configura��o de tr�mite j� informado" e
			// retornar para o passo correspondente no fluxo principal.
			// . Caso a associa��o seja do primeiro tr�mite para o servi�o (campo
			// "Unidade do Primeiro Tr�mite?" com a op��o "Sim" selecionada):
			// . Caso a combina��o Tipo de Servi�o/Localidade/Setor Comercial/Bairro/Unidade
			// Origem/Primeiro Tr�mite com o valor "Sim" j� tenha sido informada na cole��o ou j�
			// exista na tabela SERVICO_TIPO_TRAMITE (ESTR_ICPRIMEIROTRAMITE=1 (um)), exibir a
			// mensagem "Configura��o de primeiro tr�mite j� informado" e retornar para o passo
			// correspondente no fluxo principal.


			// Compara o objeto
			if(!idAux.equals(id) && localidadeIgual && setorComercialIgual && bairroIgual && unidadeOrganizacionalOrigemIgual
							&& unidadeOrganizacionalDestinoIgual){
				throw new ActionServletException("atencao.configuracao.tramite.ja.informado");
			}

			if(indicadorPrimeiroTramiteIgual){
				if(!idAux.equals(id) && localidadeIgual && setorComercialIgual && bairroIgual && unidadeOrganizacionalOrigemIgual
								&& indicadorPrimeiroTramiteIgual){
					throw new ActionServletException("atencao.configuracao.primeiro.tramite.ja.informado");
				}
			}

		}
	}
}
