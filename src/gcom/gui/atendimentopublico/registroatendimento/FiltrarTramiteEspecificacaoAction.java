
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
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SISTEMA_ABASTECIMENTO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.DISTRITO_OPERACIONAL);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.ZONA_ABASTECIMENTO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SETOR_ABASTECIMENTO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SISTEMA_ESGOTO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SUBSISTEMA_ESGOTO);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.BACIA);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SUBBACIA);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_ORIGEM);
		filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_DESTINO);

		// Veirifica os valores definidos na tela
		FiltrarTramiteEspecificacaoActionForm form = (FiltrarTramiteEspecificacaoActionForm) actionForm;

		String idSolicitacaoTipo = form.getIdSolicitacaoTipo();
		String idSolicitacaoTipoEspecificacao = form.getIdSolicitacaoTipoEspecificacao();
		String idLocalidade = form.getIdLocalidade();
		String codigoSetorComercial = form.getCodigoSetorComercial();
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

		if(!Util.isVazioOuBranco(idSistemaAbastecimento) && !idSistemaAbastecimento.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.SISTEMA_ABASTECIMENTO_ID,
							idSistemaAbastecimento));
		}

		if(!Util.isVazioOuBranco(idDistritoOperacional) && !idDistritoOperacional.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.DISTRITO_OPERACIONAL_ID,
							idDistritoOperacional));
		}

		if(!Util.isVazioOuBranco(idZonaAbastecimento) && !idZonaAbastecimento.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.ZONA_ABASTECIMENTO_ID,
							idZonaAbastecimento));
		}

		if(!Util.isVazioOuBranco(idSetorAbastecimento) && !idSetorAbastecimento.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.SETOR_ABASTECIMENTO_ID,
							idSetorAbastecimento));
		}

		if(!Util.isVazioOuBranco(idSistemaEsgoto) && !idSistemaEsgoto.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.SISTEMA_ESGOTO_ID,
							idSistemaEsgoto));
		}

		if(!Util.isVazioOuBranco(idSubsistemaEsgoto) && !idSubsistemaEsgoto.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.SUBSISTEMA_ESGOTO_ID,
							idSubsistemaEsgoto));
		}

		if(!Util.isVazioOuBranco(idBacia) && !idBacia.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.BACIA_ID, idBacia));
		}

		if(!Util.isVazioOuBranco(idSubBacia) && !idSubBacia.equals(numeroNaoInformadoStr)){
			peloMenosUmParametroInformado = true;
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.SUBBACIA_ID, idSubBacia));
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

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroTramiteEspecificacao", filtroTramiteEspecificacao);
		sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);

		return retorno;
	}
}