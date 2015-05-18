
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
 * Atualizar Tr�mite Especifica��o
 * 
 * @author Hebert Falc�o
 * @date 25/03/2011
 */
public class AtualizarTramiteEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarTramiteEspecificacaoActionForm form = (AtualizarTramiteEspecificacaoActionForm) actionForm;

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Valida o Formul�rio
		this.validarForm(form);

		EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) sessao.getAttribute("especificacaoTramite");

		// Popula a entidade
		form.setEspecificacaoTramiteValues(especificacaoTramite);

		this.verificarExistenciaPrimeiroTramiteEspecificacao(especificacaoTramite, fachada);

		this.verificarExistenciaTramiteEspecificacao(especificacaoTramite, fachada);

		fachada.atualizarTramiteEspecificacao(especificacaoTramite);

		montarPaginaSucesso(httpServletRequest, "Tr�mite por Especifica��o de id " + form.getId() + " atualizada com sucesso.",
						"Realizar outra Manuten��o de Tr�mite por Especifica��o", "exibirFiltrarTramiteEspecificacaoAction.do?menu=sim");

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

		String idUnidadeOrganizacionalOrigem = form.getIdUnidadeOrganizacionalOrigem();
		String idUnidadeOrganizacionalDestino = form.getIdUnidadeOrganizacionalDestino();
		String indicadorUso = form.getIndicadorUso();
		String indicadorPrimeiroTramite = form.getIndicadorPrimeiroTramite();

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
			throw new ActionServletException("atencao.required", null, "Munic�pio");
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

		// Indicador de Uso
		if(Util.isVazioOuBranco(indicadorPrimeiroTramite)){
			throw new ActionServletException("atencao.required", null, "Indicador de Primeiro Tramite");
		}

	}



	// [FS0001] - Verificar exist�ncia de tr�mite para a especifica��o
	private void verificarExistenciaTramiteEspecificacao(EspecificacaoTramite tramiteEspecificacao, Fachada fachada){

		// . Caso a unidade destino informada j� exista para a especifica��o (existe ocorr�ncia na
		// tabela ESPECIFICACAO_TRAMITE com STEP_ID=Id da especifica��o selecionada e (LOCA_ID=Id da
		// Localidade pesquisada, caso tenha pesquisado alguma localidade; caso contr�rio, LOCA_ID
		// com o valor nulo) e (STCM_ID=Id do Setor Comercial pesquisado, caso tenha pesquisado
		// algum setor comercial; caso contr�rio, STCM_ID com o valor nulo) e (BAIR_ID=Id do Bairro
		// pesquisado, caso tenha pesquisado algum bairro; caso contr�rio, BAIR_ID com o valor nulo)
		// e (UNID_IDORIGEM=Id da Unidade Origem pesquisada, caso tenha pesquisado alguma unidade
		// origem; caso contr�rio, UNID_IDORIGEM com o valor nulo) e UNID_IDDESTINO=Id da unidade
		// destino pesquisada):

		if(!Util.isVazioOuBranco(tramiteEspecificacao.getUnidadeOrganizacionalDestino())){

			Collection<UnidadeOrganizacional> colecao = fachada.obterUnidadeDestinoPorEspecificacao(tramiteEspecificacao, false);

			if(!Util.isVazioOrNulo(colecao) && colecao.size() > 1){
				// // . Exibir a mensagem "Tr�mite por Especifica��o j� existe no cadastro"
				// // . Retornar para o passo correspondente no fluxo.
				throw new ActionServletException("atencao.especificacao_tramite_ja_existente");

			}

		}

	}

	// [FS0002] - Verificar exist�ncia de primeiro tr�mite para a especifica��o

	private void verificarExistenciaPrimeiroTramiteEspecificacao(EspecificacaoTramite tramiteEspecificacao, Fachada fachada){

		// . Caso a associa��o seja do primeiro tr�mite para a especifica��o (campo "Unidade do
		// Primeiro
		// Tr�mite?" com a op��o "Sim" selecionada):

		UnidadeOrganizacional unidadeDestino = null;

		if(!Util.isVazioOuBranco(tramiteEspecificacao.getIndicadorPrimeiroTramite())
						&& tramiteEspecificacao.getIndicadorPrimeiroTramite().equals(ConstantesSistema.SIM)){

			unidadeDestino = tramiteEspecificacao.getUnidadeOrganizacionalDestino();

			tramiteEspecificacao.setUnidadeOrganizacionalDestino(null);

			Collection<UnidadeOrganizacional> colecao = fachada.obterUnidadeDestinoPorEspecificacao(tramiteEspecificacao, true);

			if(!Util.isVazioOrNulo(colecao)){

				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecao);

				if(!unidadeOrganizacional.getId().equals(unidadeDestino.getId())){
					throw new ActionServletException("atencao.indidador.primeiro.tramite.ja.definido", null, unidadeOrganizacional.getId()
									.toString());
				}

			}

			tramiteEspecificacao.setUnidadeOrganizacionalDestino(unidadeDestino);

		}

	}



}
