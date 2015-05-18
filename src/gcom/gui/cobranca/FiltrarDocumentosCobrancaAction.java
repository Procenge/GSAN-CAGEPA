/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cobranca;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cobranca.*;
import gcom.cobranca.bean.CobrancaDocumentoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created
 */
/**
 * Descrição da classe
 * 
 * @author Administrador
 * @date 07/03/2006
 */
public class FiltrarDocumentosCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarDocumentosCobrancaResultado");

		// DynaValidatorActionForm pesquisarActionForm =
		// (DynaValidatorActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// Parte que pega as coleções da sessão

		FiltrarDocumentosCobrancaActionForm filtrarDocumentosCobrancaActionForm = (FiltrarDocumentosCobrancaActionForm) actionForm;

		FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();

		if(httpServletRequest.getParameter("idGerenciaRegional") == null){
			filtrarDocumentosCobrancaActionForm.setIdGerenciaRegional(null);
		}
		if(httpServletRequest.getParameter("idUnidadeNegocio") == null){
			filtrarDocumentosCobrancaActionForm.setIdUnidadeNegocio(null);
		}

		boolean informUm = false;

		// Período de Data de Emissão
		// ==============================================================================
		String dataInicial = filtrarDocumentosCobrancaActionForm.getDataEmissaoInicio();
		String dataFinal = filtrarDocumentosCobrancaActionForm.getDataEmissaoFim();

		if((dataInicial.trim().length() == 10) && (dataFinal.trim().length() == 10)){

			Calendar calendarInicio = new GregorianCalendar();
			Calendar calendarFim = new GregorianCalendar();

			calendarInicio.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dataInicial.substring(0, 2)).intValue());
			calendarInicio.set(Calendar.MONTH, Integer.valueOf(dataInicial.substring(3, 5)).intValue() - 1);
			calendarInicio.set(Calendar.YEAR, Integer.valueOf(dataInicial.substring(6, 10)).intValue());
			calendarInicio.set(Calendar.HOUR_OF_DAY, 00);
			calendarInicio.set(Calendar.MINUTE, 00);
			calendarInicio.set(Calendar.SECOND, 00);

			calendarFim.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dataFinal.substring(0, 2)).intValue());
			calendarFim.set(Calendar.MONTH, Integer.valueOf(dataFinal.substring(3, 5)).intValue() - 1);
			calendarFim.set(Calendar.YEAR, Integer.valueOf(dataFinal.substring(6, 10)).intValue());
			calendarFim.set(Calendar.HOUR_OF_DAY, 23);
			calendarFim.set(Calendar.MINUTE, 59);
			calendarFim.set(Calendar.SECOND, 59);

			if(calendarFim.compareTo(calendarInicio) < 0){
				throw new ActionServletException("atencao.data_fim_menor_inicio");
			}

			filtroCobrancaDocumento.adicionarParametro(new Intervalo(FiltroCobrancaDocumento.DATA_EMISSAO, calendarInicio.getTime(),
							calendarFim.getTime()));
		}
		// ===============================================================================

		// Intervalo de Valor do Documento
		// ===============================================================================
		// String valorInicioSTR = null;
		// String valorFimSTR = null;
		BigDecimal valorInicio = null;
		BigDecimal valorFim = null;

		if(filtrarDocumentosCobrancaActionForm.getValorInicial() != null
						&& !filtrarDocumentosCobrancaActionForm.getValorInicial().equals("")){
			/*
			 * valorInicioSTR =
			 * filtrarDocumentosCobrancaActionForm.getValorInicial().replace(".","");
			 * valorInicioSTR = valorInicioSTR.replace(",",".");
			 * valorInicio = new BigDecimal(valorInicioSTR);
			 */

			valorInicio = Util.formatarMoedaRealparaBigDecimal(filtrarDocumentosCobrancaActionForm.getValorInicial());
		}

		if(filtrarDocumentosCobrancaActionForm.getValorFinal() != null && !filtrarDocumentosCobrancaActionForm.getValorFinal().equals("")){

			/*
			 * valorFimSTR = filtrarDocumentosCobrancaActionForm.getValorFinal().replace(".","");
			 * valorFimSTR = valorFimSTR.replace(",",".");
			 * valorFim = new BigDecimal(valorFimSTR);
			 */

			valorFim = Util.formatarMoedaRealparaBigDecimal(filtrarDocumentosCobrancaActionForm.getValorFinal());

		}

		if((valorInicio != null) && (valorFim != null)){

			if(valorInicio.compareTo(valorFim) > 0){
				throw new ActionServletException("atencao.valor_fim_menor_inicio");
			}else{

				filtroCobrancaDocumento.adicionarParametro(new Intervalo(FiltroCobrancaDocumento.VALOR_DOCUMENTO, valorInicio, valorFim));
			}
		}else if(valorInicio != null && valorFim == null){

			filtroCobrancaDocumento.adicionarParametro(new Intervalo(FiltroCobrancaDocumento.VALOR_DOCUMENTO, valorInicio, valorInicio));
		}
		// ===================================================================================

		if(!Util.isVazioOuBranco(filtrarDocumentosCobrancaActionForm.getNumeroSequencialDocumento())){

			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.NUMERO_SEQUENCIAL, Util
							.obterInteger(filtrarDocumentosCobrancaActionForm.getNumeroSequencialDocumento())));
			informUm = true;
		}

		if(filtrarDocumentosCobrancaActionForm.getIdImovel() != null && !filtrarDocumentosCobrancaActionForm.getIdImovel().equals("")){

			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.IMOVEL_ID,
							filtrarDocumentosCobrancaActionForm.getIdImovel()));
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("imovel");
			informUm = true;
		}

		if(filtrarDocumentosCobrancaActionForm.getIdGerenciaRegional() != null
						&& !filtrarDocumentosCobrancaActionForm.getIdGerenciaRegional().equals("")
						&& !filtrarDocumentosCobrancaActionForm.getIdGerenciaRegional().equals(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.GERENCIA_REGIONAL,
							filtrarDocumentosCobrancaActionForm.getIdGerenciaRegional()));

			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("localidade.gerenciaRegional");
			informUm = true;
		}

		// Unidade Negocio
		if(filtrarDocumentosCobrancaActionForm.getIdUnidadeNegocio() != null
						&& !filtrarDocumentosCobrancaActionForm.getIdUnidadeNegocio().equals("")
						&& !filtrarDocumentosCobrancaActionForm.getIdUnidadeNegocio().equals(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_UNIDADE_NEGOCIO,
							filtrarDocumentosCobrancaActionForm.getIdUnidadeNegocio()));

			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("localidade.unidadeNegocio");
			informUm = true;
		}

		// Forma de emissão
		if(filtrarDocumentosCobrancaActionForm.getDocumentoEmissaoForma() != null
						&& filtrarDocumentosCobrancaActionForm.getDocumentoEmissaoForma().length > 0){

			String[] idsFormaEmissao = filtrarDocumentosCobrancaActionForm.getDocumentoEmissaoForma();

			for(int i = 0; i < idsFormaEmissao.length; i++){

				if(!idsFormaEmissao[i].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(idsFormaEmissao.length == 1){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.DOCUMENTO_EMISSAO_FORMA,
										idsFormaEmissao[i]));

					}else if(i == 0){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.DOCUMENTO_EMISSAO_FORMA,
										idsFormaEmissao[i], ParametroSimples.CONECTOR_OR, idsFormaEmissao.length));

					}else if(i == (idsFormaEmissao.length - 1)){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.DOCUMENTO_EMISSAO_FORMA,
										idsFormaEmissao[i]));

					}else{
						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.DOCUMENTO_EMISSAO_FORMA,
										idsFormaEmissao[i], ParametroSimples.CONECTOR_OR));
					}
				}
			}
		}

		// Cobrança Ação
		if(filtrarDocumentosCobrancaActionForm.getCobrancaAcao() != null
						&& filtrarDocumentosCobrancaActionForm.getCobrancaAcao().length > 0){

			String[] idsCobrancaAcao = filtrarDocumentosCobrancaActionForm.getCobrancaAcao();

			for(int i = 0; i < idsCobrancaAcao.length; i++){

				if(!idsCobrancaAcao[i].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(idsCobrancaAcao.length == 1){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO,
										idsCobrancaAcao[i]));

					}else if(i == 0){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO,
										idsCobrancaAcao[i], ParametroSimples.CONECTOR_OR, idsCobrancaAcao.length));

					}else if(i == (idsCobrancaAcao.length - 1)){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO,
										idsCobrancaAcao[i]));

					}else{
						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO,
										idsCobrancaAcao[i], ParametroSimples.CONECTOR_OR));
					}
				}
			}
		}

		// Motivo da Não Entrega do Documento
		if(filtrarDocumentosCobrancaActionForm.getMotivoNaoEntregaDocumento() != null
						&& filtrarDocumentosCobrancaActionForm.getMotivoNaoEntregaDocumento().length > 0){

			String[] idsMotivoNaoEntregaDocumento = filtrarDocumentosCobrancaActionForm.getMotivoNaoEntregaDocumento();

			for(int i = 0; i < idsMotivoNaoEntregaDocumento.length; i++){

				if(!idsMotivoNaoEntregaDocumento[i].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(idsMotivoNaoEntregaDocumento.length == 1){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.MOTIVO_NAO_ENTREGA_ID,
										idsMotivoNaoEntregaDocumento[i]));

					}else if(i == 0){

						filtroCobrancaDocumento
										.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.MOTIVO_NAO_ENTREGA_ID,
														idsMotivoNaoEntregaDocumento[i], ParametroSimples.CONECTOR_OR,
														idsMotivoNaoEntregaDocumento.length));

					}else if(i == (idsMotivoNaoEntregaDocumento.length - 1)){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.MOTIVO_NAO_ENTREGA_ID,
										idsMotivoNaoEntregaDocumento[i]));

					}else{
						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.MOTIVO_NAO_ENTREGA_ID,
										idsMotivoNaoEntregaDocumento[i], ParametroSimples.CONECTOR_OR));
					}
				}
			}
		}

		// Perfil do Imóvel
		if(filtrarDocumentosCobrancaActionForm.getImovelPerfil() != null
						&& filtrarDocumentosCobrancaActionForm.getImovelPerfil().length > 0){

			String[] idsImovelPerfil = filtrarDocumentosCobrancaActionForm.getImovelPerfil();

			for(int i = 0; i < idsImovelPerfil.length; i++){

				if(!idsImovelPerfil[i].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(idsImovelPerfil.length == 1){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.PERFIL_IMOVEL_ID,
										idsImovelPerfil[i]));

					}else if(i == 0){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.PERFIL_IMOVEL_ID,
										idsImovelPerfil[i], ParametroSimples.CONECTOR_OR, idsImovelPerfil.length));

					}else if(i == (idsImovelPerfil.length - 1)){

						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.PERFIL_IMOVEL_ID,
										idsImovelPerfil[i]));

					}else{
						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.PERFIL_IMOVEL_ID,
										idsImovelPerfil[i], ParametroSimples.CONECTOR_OR));
					}
				}
			}
		}

		// Buscando por localidade, Setor Comercial e Quadra, fazendo validacoes
		// de intervalos

		if((filtrarDocumentosCobrancaActionForm.getLocalidadeOrigemID() != null)
						&& (!filtrarDocumentosCobrancaActionForm.getLocalidadeOrigemID().equals(""))
						&& (filtrarDocumentosCobrancaActionForm.getLocalidadeDestinoID() != null)
						&& (!filtrarDocumentosCobrancaActionForm.getLocalidadeDestinoID().equals(""))){

			informUm = true;
			Integer localidadeInicial = Integer.valueOf(filtrarDocumentosCobrancaActionForm.getLocalidadeOrigemID());
			Integer localidadeFinal = Integer.valueOf(filtrarDocumentosCobrancaActionForm.getLocalidadeDestinoID());
			int resultado = localidadeInicial.compareTo(localidadeFinal);

			if(resultado == 0){
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.LOCALIDADE_ID, localidadeInicial
								.toString()));
				filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("localidade");

				if((filtrarDocumentosCobrancaActionForm.getSetorComercialOrigemCD() != null)
								&& (!filtrarDocumentosCobrancaActionForm.getSetorComercialOrigemCD().equals(""))
								&& (filtrarDocumentosCobrancaActionForm.getSetorComercialDestinoCD() != null)
								&& (!filtrarDocumentosCobrancaActionForm.getSetorComercialDestinoCD().equals(""))){
					Integer setorInicial = Integer.valueOf(filtrarDocumentosCobrancaActionForm.getSetorComercialOrigemCD());
					Integer setorFinal = Integer.valueOf(filtrarDocumentosCobrancaActionForm.getSetorComercialDestinoCD());
					int resultadoSetor = setorInicial.compareTo(setorFinal);

					if(resultadoSetor == 0){
						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.SETOR_COMERCIAL,
										setorInicial.toString()));

						if((filtrarDocumentosCobrancaActionForm.getQuadraOrigemNM() != null)
										&& (!filtrarDocumentosCobrancaActionForm.getQuadraOrigemNM().equals(""))
										&& (filtrarDocumentosCobrancaActionForm.getQuadraDestinoNM() != null)
										&& (!filtrarDocumentosCobrancaActionForm.getQuadraDestinoNM().equals(""))){
							Integer quadraInicial = Integer.valueOf(filtrarDocumentosCobrancaActionForm.getQuadraOrigemNM());
							Integer quadraFinal = Integer.valueOf(filtrarDocumentosCobrancaActionForm.getQuadraDestinoNM());
							int resultadoQuadra = quadraInicial.compareTo(quadraFinal);

							if(resultadoQuadra == 0){
								filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.QUADRA_NM,
												quadraInicial.toString()));
								filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("quadra");
							}else if(resultadoQuadra > 0){
								throw new ActionServletException("atencao.valor_fim_menor_inicio");
							}else{
								filtroCobrancaDocumento.adicionarParametro(new Intervalo(FiltroCobrancaDocumento.QUADRA_NM, quadraInicial
												.toString(), quadraFinal.toString()));
								filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("quadra");
							}
						}

					}else if(resultadoSetor > 0){
						throw new ActionServletException("atencao.valor_fim_menor_inicio");
					}else{
						filtroCobrancaDocumento.adicionarParametro(new Intervalo(FiltroCobrancaDocumento.SETOR_COMERCIAL, setorInicial
										.toString(), setorFinal.toString()));
					}
				}

			}else if(resultado > 0){
				throw new ActionServletException("atencao.valor_fim_menor_inicio");
			}else{
				filtroCobrancaDocumento.adicionarParametro(new Intervalo(FiltroCobrancaDocumento.LOCALIDADE_ID, localidadeInicial
								.toString(), localidadeFinal.toString()));
				filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("localidade");
			}
		}
		// verifica se campos obrigatorios foram preenchidos
		if(!informUm && httpServletRequest.getParameter("page.offset") == null){
			throw new ActionServletException("atencao.informe_matricula_gerencia_localidade");
		}

		filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("documentoEmissaoForma");
		filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroCobrancaDocumento
						.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.cobrancaAcao");
		filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoAtividadeComando.cobrancaAcao");
		filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcao.cobrancaAcaoPredecessora");
		filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
		filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.COBRANCA_ACAO_SITUACAO);
		filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.COBRANCA_DEBITO_SITUACAO);

		filtroCobrancaDocumento.setCampoOrderBy(filtroCobrancaDocumento.NUMERO_SEQUENCIAL + " DESC");

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroCobrancaDocumento, CobrancaDocumento.class.getName());

		Collection colecaoDocumentoCobranca = (Collection) resultado.get("colecaoRetorno");

		retorno = (ActionForward) resultado.get("destinoActionForward");

		// Collection colecaoDocumentoCobranca = fachada.pesquisar(
		// filtroCobrancaDocumento, CobrancaDocumento.class.getName());

		if(colecaoDocumentoCobranca == null || colecaoDocumentoCobranca.isEmpty()){
			// [FS0010] Nenhum registro encontrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "");
		}

		FiltroCobrancaDebitoSituacao filtroCobrancaDebitoSituacao = new FiltroCobrancaDebitoSituacao();
		filtroCobrancaDebitoSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaDebitoSituacao.ID, CobrancaDebitoSituacao.PAGO));
		CobrancaDebitoSituacao cobrancaDebitoSituacaoPago = (CobrancaDebitoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroCobrancaDebitoSituacao, CobrancaDebitoSituacao.class.getName()));

		Iterator colecaoDocumentoCobrancaIterator = colecaoDocumentoCobranca.iterator();
		CobrancaDocumentoHelper cobrancaDocumentoHelper = null;
		CobrancaDocumento cobrancaDocumento = null;
		FiltroCobrancaDocumentoItem filtroCobrancaDocumentoItem = new FiltroCobrancaDocumentoItem();
		Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoItem = null;
		Collection<CobrancaDocumentoHelper> colecaoCobrancaDocumentoHelper = new ArrayList();

		while(colecaoDocumentoCobrancaIterator.hasNext()){

			cobrancaDocumento = (CobrancaDocumento) colecaoDocumentoCobrancaIterator.next();
			filtroCobrancaDocumentoItem.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumentoItem.COBRANCA_DOCUMENTO_ID,
							cobrancaDocumento.getId()));

			colecaoCobrancaDocumentoItem = fachada.pesquisar(filtroCobrancaDocumentoItem, CobrancaDocumentoItem.class.getName());
			cobrancaDocumentoHelper = new CobrancaDocumentoHelper();
			cobrancaDocumentoHelper.setCobrancaDocumento(cobrancaDocumento);
			cobrancaDocumentoHelper.setDescricaoCobrancaAcao(this.obterDescricaoCobrancaAcao(cobrancaDocumento));
			cobrancaDocumentoHelper.setCobrancaDocumentoAcaoCobranca(Fachada.getInstancia().obterCobrancaDocumentoGeradoAcaoCobranca(
							cobrancaDocumento));

			Object[] dadosOrdemServico = fachada.pesquisarDadosOrdemServicoDocumentoCobranca(cobrancaDocumento.getId());
			if(dadosOrdemServico != null){
				if(dadosOrdemServico[0] != null){
					cobrancaDocumentoHelper.setIdOrdemServico((Integer) dadosOrdemServico[0]);
				}
				if(dadosOrdemServico[1] != null){
					Short situacaoOS = (Short) dadosOrdemServico[1];
					if(situacaoOS.equals(OrdemServico.SITUACAO_PENDENTE)){
						cobrancaDocumentoHelper.setSituacaoOrdemServico(OrdemServico.SITUACAO_DESC_ABREV_PENDENTE);
					}
					if(situacaoOS.equals(OrdemServico.SITUACAO_ENCERRADO)){
						cobrancaDocumentoHelper.setSituacaoOrdemServico(OrdemServico.SITUACAO_DESC_ABREV_ENCERRADO);
					}
					if(situacaoOS.equals(OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO)){
						cobrancaDocumentoHelper.setSituacaoOrdemServico(OrdemServico.SITUACAO_DESC_ABREV_EXECUCAO_EM_ANDAMENTO);
					}
					if(situacaoOS.equals(OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO)){
						cobrancaDocumentoHelper.setSituacaoOrdemServico(OrdemServico.SITUACAO_DESC_ABREV_AGUARDANDO_LIBERACAO);
					}
				}
			}

			if(colecaoCobrancaDocumentoItem == null || colecaoCobrancaDocumentoItem.isEmpty()){
				cobrancaDocumentoHelper.setQuantidadeItensCobrancaDocumento(0);
			}else{
				cobrancaDocumentoHelper.setQuantidadeItensCobrancaDocumento(colecaoCobrancaDocumentoItem.size());
			}
			colecaoCobrancaDocumentoHelper.add(cobrancaDocumentoHelper);

			filtroCobrancaDocumentoItem.limparListaParametros();

			// [SB0003] Verificar Documento de Cobrança do Tipo Extrato de Débito
			if(cobrancaDocumento.getDocumentoTipo().getId().equals(DocumentoTipo.EXTRATO_DE_DEBITO)){
				boolean todosItensPagos = true;
				Date maiorDataDebitoSituacao = null;
				for(CobrancaDocumentoItem cobrancaDocumentoItem : colecaoCobrancaDocumentoItem){
					if(!cobrancaDocumentoItem.getCobrancaDebitoSituacao().getId().equals(CobrancaDebitoSituacao.PAGO)){
						todosItensPagos = false;
						break;
					}

					if((maiorDataDebitoSituacao == null) || (maiorDataDebitoSituacao.before(cobrancaDocumentoItem.getDataSituacaoDebito()))){
						maiorDataDebitoSituacao = cobrancaDocumentoItem.getDataSituacaoDebito();
					}
				}

				if(todosItensPagos){
					cobrancaDocumento.setCobrancaDebitoSituacao(cobrancaDebitoSituacaoPago);
					cobrancaDocumento.setDataSituacaoDebito(maiorDataDebitoSituacao);
				}

			}
		}

		sessao.setAttribute("colecaoDocumentoCobranca", colecaoCobrancaDocumentoHelper);

		return retorno;

	}

	private String obterDescricaoCobrancaAcao(CobrancaDocumento cobrancaDocumento){

		String retorno = "";

		if(cobrancaDocumento.getDocumentoEmissaoForma() != null && cobrancaDocumento.getCobrancaAcao() != null){

			if(cobrancaDocumento.getDocumentoEmissaoForma().getId().intValue() == DocumentoEmissaoForma.CRONOGRAMA.intValue()){

				retorno = cobrancaDocumento.getCobrancaAcao().getDescricaoCobrancaAcao();

			}else if(cobrancaDocumento.getDocumentoEmissaoForma().getId().intValue() == DocumentoEmissaoForma.EVENTUAL.intValue()){

				retorno = cobrancaDocumento.getCobrancaAcao().getDescricaoCobrancaAcao();

			}

		}

		return retorno;

	}

}
