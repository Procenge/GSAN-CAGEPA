
package gcom.gui.faturamento.guiapagamento;

import gcom.fachada.Fachada;
import gcom.faturamento.bean.FiltroGuiaPagamentoHelper;
import gcom.faturamento.bean.GuiaPagamentoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.faturamento.RelatorioManterGuiaPagamentoParametrosHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3027] Filtrar Guia de Pagamento
 * 
 * @author Anderson Italo
 * @date 26/10/2011
 */
public class FiltrarGuiaPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterGuiaPagamento");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarGuiaPagamentoActionForm formulario = (FiltrarGuiaPagamentoActionForm) actionForm;
		RelatorioManterGuiaPagamentoParametrosHelper relatorioManterGuiaPagamentoParametrosHelper = new RelatorioManterGuiaPagamentoParametrosHelper();

		// Verifica se o usuário marcou o checkBox Atualizar
		String indicadorAtualizar = formulario.getIndicadorAtualizar();
		if(!Util.isVazioOuBranco(indicadorAtualizar) && indicadorAtualizar.equals(ConstantesSistema.SIM.toString())){

			sessao.setAttribute("indicadorAtualizar", ConstantesSistema.SIM.toString());
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		boolean peloAoMenosUmParametroInformado = false;

		// Variáveis de parâmetros da consulta
		String idsClienteRelacaoTipo = "";
		String idsDocumentoTipo = "";
		String idsDebitoTipo = "";
		FiltroGuiaPagamentoHelper filtro = new FiltroGuiaPagamentoHelper();

		/*
		 * Cliente da Guia
		 * (caso informe o imóvel da guia, não pode ser preenchido; caso contrário, o
		 * preenchimento é opcional)
		 */
		if(!Util.isVazioOuBranco(formulario.getCodigoClienteGuia())){

			peloAoMenosUmParametroInformado = true;
			filtro.setCodigoClienteGuia(Util.obterInteger(formulario.getCodigoClienteGuia()));
			relatorioManterGuiaPagamentoParametrosHelper.setCodigoClienteGuia(formulario.getCodigoClienteGuia().toString());
			relatorioManterGuiaPagamentoParametrosHelper.setNomeClienteGuia(formulario.getNomeClienteGuia());
		}

		// No. Contrato Parcelamento Órgão Público
		if(!Util.isVazioOuBranco(formulario.getNumeroContratoParcelOrgaoPublico())){

			peloAoMenosUmParametroInformado = true;
			filtro.setNumeroContratoParcelOrgaoPublico(Util.obterInteger(formulario.getNumeroContratoParcelOrgaoPublico()));
			relatorioManterGuiaPagamentoParametrosHelper.setNumeroContratoParcelOrgaoPublico(formulario.getNumeroContratoParcelOrgaoPublico());
		}

		/*
		 * Imóvel da Guia
		 * (caso informe o cliente da guia, não pode ser preenchido; caso contrário, o
		 * preenchimento é opcional)
		 */
		if(!Util.isVazioOuBranco(formulario.getMatriculaImovel())){

			peloAoMenosUmParametroInformado = true;
			filtro.setIdImovel(Util.obterInteger(formulario.getMatriculaImovel()));
			relatorioManterGuiaPagamentoParametrosHelper.setMatriculaImovel(formulario.getMatriculaImovel().toString());
			relatorioManterGuiaPagamentoParametrosHelper.setInscricaoImovel(formulario.getInscricaoImovel().toString());
		}

		/*
		 * Cliente Responsável
		 * (caso informe o cliente da guia, não pode ser preenchido; caso contrário, o
		 * preenchimento é opcional)
		 */
		if(!Util.isVazioOuBranco(formulario.getCodigoClienteResponsavel())){

			peloAoMenosUmParametroInformado = true;
			filtro.setCodigoClienteResponsavel(Util.obterInteger(formulario.getCodigoClienteResponsavel()));
			relatorioManterGuiaPagamentoParametrosHelper.setCodigoClienteResponsavel(formulario.getCodigoClienteResponsavel().toString());
			relatorioManterGuiaPagamentoParametrosHelper.setNomeClienteResponsavel(formulario.getNomeClienteResponsavel());
		}

		/*
		 * Tipo da Relação do Cliente com o Imóvel
		 * (caso informe o cliente da guia ou o cliente responsável, não pode ser
		 * selecionado; caso contrário, a seleção é opcional)
		 */
		if(!Util.isVazioOrNulo(formulario.getIdTipoRelacao())
						&& !(Util.obterInteger(formulario.getIdTipoRelacao()[0]).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO)){

			String[] idsTipoRelacao = formulario.getIdTipoRelacao();

			peloAoMenosUmParametroInformado = true;
			if(idsTipoRelacao.length > 1){

				for(int i = 0; i < idsTipoRelacao.length; i++){

					if(i == 0){

						idsClienteRelacaoTipo += idsTipoRelacao[i];
					}else{

						idsClienteRelacaoTipo += "," + idsTipoRelacao[i];
					}
				}
			}else{

				idsClienteRelacaoTipo += idsTipoRelacao[0];
			}

			filtro.setIdsClienteRelacaoTipo(idsClienteRelacaoTipo);
		}

		// Localidade
		if(!Util.isVazioOuBranco(formulario.getIdLocalidade())){

			peloAoMenosUmParametroInformado = true;
			filtro.setIdLocalidade(Util.obterInteger(formulario.getIdLocalidade()));
			relatorioManterGuiaPagamentoParametrosHelper.setIdLocalidade(formulario.getIdLocalidade());
			relatorioManterGuiaPagamentoParametrosHelper.setNomeLocalidade(formulario.getNomeLocalidade());
		}

		// Período de Referência do Faturamento
		if(!Util.isVazioOuBranco(formulario.getPeriodoReferenciaFaturamentoInicial())){

			peloAoMenosUmParametroInformado = true;
			filtro.setAnoMesReferenciaInicial(Util.formatarMesAnoComBarraParaAnoMes(formulario.getPeriodoReferenciaFaturamentoInicial()));

			if(!Util.isVazioOuBranco(formulario.getPeriodoReferenciaFaturamentoFinal())){

				filtro.setAnoMesReferenciaFinal(Util.formatarMesAnoComBarraParaAnoMes(formulario.getPeriodoReferenciaFaturamentoFinal()));
				relatorioManterGuiaPagamentoParametrosHelper.setPeriodoReferenciaFaturamento(formulario
								.getPeriodoReferenciaFaturamentoInicial()
								+ " a " + formulario.getPeriodoReferenciaFaturamentoFinal());
			}else{

				filtro.setAnoMesReferenciaFinal(filtro.getAnoMesReferenciaInicial());
				relatorioManterGuiaPagamentoParametrosHelper.setPeriodoReferenciaFaturamento(formulario
								.getPeriodoReferenciaFaturamentoInicial());
			}
		}

		// Período de Emissão
		if(!Util.isVazioOuBranco(formulario.getPeriodoEmissaoInicial())){

			peloAoMenosUmParametroInformado = true;
			filtro.setDataEmissaoInicial(Util.converteStringParaDate(formulario.getPeriodoEmissaoInicial()));

			if(!Util.isVazioOuBranco(formulario.getPeriodoEmissaoFinal())){

				filtro.setDataEmissaoFinal(Util.converteStringParaDate(formulario.getPeriodoEmissaoFinal()));
				relatorioManterGuiaPagamentoParametrosHelper.setPeriodoEmissao(formulario.getPeriodoEmissaoInicial() + " a "
								+ formulario.getPeriodoEmissaoFinal());
			}else{
				filtro.setDataEmissaoFinal(filtro.getDataEmissaoInicial());
				relatorioManterGuiaPagamentoParametrosHelper.setPeriodoEmissao(formulario.getPeriodoEmissaoInicial());
			}
		}

		// Período de Vencimento
		if(!Util.isVazioOuBranco(formulario.getPeriodoVencimentoInicial())){

			peloAoMenosUmParametroInformado = true;
			filtro.setDataVencimentoInicial(Util.converteStringParaDate(formulario.getPeriodoVencimentoInicial()));

			if(!Util.isVazioOuBranco(formulario.getPeriodoVencimentoFinal())){

				filtro.setDataVencimentoFinal(Util.converteStringParaDate(formulario.getPeriodoVencimentoFinal()));
				relatorioManterGuiaPagamentoParametrosHelper.setPeriodoVencimento(formulario.getPeriodoVencimentoInicial() + " a "
								+ formulario.getPeriodoVencimentoFinal());
			}else{
				filtro.setDataVencimentoFinal(filtro.getDataVencimentoInicial());
				relatorioManterGuiaPagamentoParametrosHelper.setPeriodoVencimento(formulario.getPeriodoVencimentoInicial());
			}
		}

		// Tipo do Documento
		if(!Util.isVazioOrNulo(formulario.getIdTipoDocumento())
						&& !(Util.obterInteger(formulario.getIdTipoDocumento()[0]).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO)){

			String[] idsTipoDocumento = formulario.getIdTipoDocumento();

			peloAoMenosUmParametroInformado = true;
			if(idsTipoDocumento.length > 1){

				for(int i = 0; i < idsTipoDocumento.length; i++){

					if(i == 0){

						idsDocumentoTipo += idsTipoDocumento[i];
					}else{

						idsDocumentoTipo += "," + idsTipoDocumento[i];
					}
				}
			}else{

				idsDocumentoTipo += idsTipoDocumento[0];
			}

			filtro.setIdsDocumentoTipo(idsDocumentoTipo);
		}

		// Tipo do Debito
		if(!Util.isVazioOrNulo(formulario.getIdTipoDebitoSelecionados())
						&& !(Util.obterInteger(formulario.getIdTipoDebitoSelecionados()[0]).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO)){

			String[] idsTipoDebito = formulario.getIdTipoDebitoSelecionados();

			peloAoMenosUmParametroInformado = true;
			if(idsTipoDebito.length > 1){

				for(int i = 0; i < idsTipoDebito.length; i++){

					if(i == 0){

						idsDebitoTipo += idsTipoDebito[i];
					}else{

						idsDebitoTipo += "," + idsTipoDebito[i];
					}
				}
			}else{

				idsDebitoTipo += idsTipoDebito[0];
			}

			filtro.setIdsDebitoTipo(idsDebitoTipo);
		}

		// Numero RA
		if(!Util.isVazioOuBranco(formulario.getNumeroRA())){

			peloAoMenosUmParametroInformado = true;
			filtro.setNumeroRA(Util.obterInteger(formulario.getNumeroRA()));

			relatorioManterGuiaPagamentoParametrosHelper.setNumeroRA(formulario.getNumeroRA());
			relatorioManterGuiaPagamentoParametrosHelper.setDescricaoRA(formulario.getDescricaoRA());
		}

		/*
		 * Caso o usuário informe o Número da Guia, desconsiderar os demais
		 * parâmetros do filtro
		 */
		if(!Util.isVazioOuBranco(formulario.getNumeroGuia())){

			peloAoMenosUmParametroInformado = true;
			// Já que desconsidera os demais parâmetros, um novo filtro é instanciado para limpar o
			// estado anterior
			filtro = new FiltroGuiaPagamentoHelper();
			filtro.setIdGuiaPagamento(Util.obterInteger(formulario.getNumeroGuia()));
			relatorioManterGuiaPagamentoParametrosHelper.setIdGuiaPagamento(formulario.getNumeroGuia());
		}

		// [FS0010 – Verificar preenchimento dos campos]
		if(peloAoMenosUmParametroInformado == false){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// [FS0011 – Muitos registros encontrados]
		Integer totalRegistros = fachada.pesquisarTotalRegistrosManterGuiaPagamento(filtro);

		if(totalRegistros.intValue() > ConstantesSistema.QUANTIDADE_LIMITE_ITEM_CONSULTA){

			throw new ActionServletException("atencao.pesquisa.muitosregistros");
		}

		filtro.setNumeroPagina(ConstantesSistema.NUMERO_NAO_INFORMADO);

		Collection<GuiaPagamentoHelper> colecaoGuiaPagamento = fachada.pesquisarRegistrosManterGuiaPagamento(filtro);

		// [FS0012] - Verificar existência de guias de pagamento
		if(Util.isVazioOrNulo(colecaoGuiaPagamento)){

			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Guia de Pagamento");
		}

		sessao.setAttribute("filtroGuiaPagamentoHelper", filtro);
		sessao.setAttribute("relatorioManterGuiaPagamentoParametrosHelper", relatorioManterGuiaPagamentoParametrosHelper);
		GuiaPagamentoHelper guiaPagamento = new GuiaPagamentoHelper();
		guiaPagamento = (GuiaPagamentoHelper) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
		String idRegistroAtualizacao = guiaPagamento.getNumeroGuia().toString();
		sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);

		return retorno;

	}
}