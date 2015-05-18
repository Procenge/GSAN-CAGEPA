
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroTramiteEspecificacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

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
public class FiltrarTramiteEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterTramiteEspecificacaoAction");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroTramiteEspecificacao filtroTramiteEspecificacao = new FiltroTramiteEspecificacao();
		filtroTramiteEspecificacao.setCampoOrderBy(FiltroTramiteEspecificacao.SOLICITACAO_TIPO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SOLICITACAO_TIPO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.LOCALIDADE);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SETOR_COMERCIAL);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.BAIRRO);

		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_ORIGEM);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_DESTINO);

		// Veirifica os valores definidos na tela
		FiltrarTramiteEspecificacaoActionForm form = (FiltrarTramiteEspecificacaoActionForm) actionForm;

		String idSolicitacaoTipo = form.getIdSolicitacaoTipo();
		String idSolicitacaoTipoEspecificacao = form.getIdSolicitacaoTipoEspecificacao();
		String idLocalidade = form.getIdLocalidade();
		String codigoSetorComercial = form.getCodigoSetorComercial();
		String codigoBairro = form.getCodigoBairro();

		String idUnidadeOrganizacionalOrigem = form.getIdUnidadeOrganizacionalOrigem();
		String idUnidadeOrganizacionalDestino = form.getIdUnidadeOrganizacionalDestino();
		String indicadorPrimeiroTramite = form.getIndicadorPrimeiroTramite();
		boolean indicadorAtualizar = form.isAtualizar();

		boolean peloMenosUmParametroInformado = false;

		String numeroNaoInformadoStr = String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO);

		// Seta o filtro
		if(!Util.isVazioOuBranco(idSolicitacaoTipo) && !idSolicitacaoTipo.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.SOLICITACAO_TIPO_ID,
							idSolicitacaoTipo));
		}

		if(!Util.isVazioOuBranco(idSolicitacaoTipoEspecificacao) && !idSolicitacaoTipoEspecificacao.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(
							FiltroTramiteEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_ID, idSolicitacaoTipoEspecificacao));
		}

		if(!Util.isVazioOuBranco(idLocalidade)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.LOCALIDADE_ID, idLocalidade));
		}

		if(!Util.isVazioOuBranco(codigoSetorComercial)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.SETOR_COMERCIAL_CODIGO,
							codigoSetorComercial));
		}

		if(!Util.isVazioOuBranco(codigoBairro)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.BAIRRO_CODIGO, codigoBairro));
		}



		if(!Util.isVazioOuBranco(idUnidadeOrganizacionalOrigem)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_ORIGEM_ID,
							idUnidadeOrganizacionalOrigem));
		}

		if(!Util.isVazioOuBranco(idUnidadeOrganizacionalDestino)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_DESTINO_ID,
							idUnidadeOrganizacionalDestino));
		}

		if(!Util.isVazioOuBranco(indicadorPrimeiroTramite)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.INDICADOR_PRIMEIRO_TRAMITE,
							indicadorPrimeiroTramite));
		}


		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroTramiteEspecificacao", filtroTramiteEspecificacao);
		sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);

		return retorno;
	}
}