/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.CobrancaDocumentoItem;
import gcom.cobranca.CobrancaNegociacaoAtendimento;
import gcom.cobranca.bean.CobrancaDocumentoHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Visualiza em PopUp dos dados do Documento de Cobranca
 * [UC0472] Consultar Imovel
 * Aba 8� - Documentos de Cobran�a
 * 
 * @author Rafael Santos
 * @created 19/09/2006
 * @author Saulo Lima
 * @date 05/02/2009
 *       Apresentar o nome da empresa na tela.
 */
public class ExibirDocumentoCobrancaItemAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirDocumentoCobrancaItem");

		Fachada fachada = Fachada.getInstancia();

		DocumentoCobrancaItemActionForm documentoCobrancaItemActionForm = (DocumentoCobrancaItemActionForm) actionForm;

		String cobrancaDocumentoID = httpServletRequest.getParameter("cobrancaDocumentosID");

		// Re-enviar documento de negocia��o
		String reEnviarDocumento = httpServletRequest.getParameter("reEnviarDocumento");

		if(!Util.isVazioOuBranco(reEnviarDocumento) && reEnviarDocumento.equals("S")){
			String idCobrancaNegociacaoAtendimento = documentoCobrancaItemActionForm.getIdCobrancaNegociacaoAtendimento();
			String emailNegociacao = documentoCobrancaItemActionForm.getEmailNegociacao();

			fachada.reEnviarDocumentoNegociacaoAtendimento(idCobrancaNegociacaoAtendimento, emailNegociacao);

			retorno = actionMapping.findForward("telaSucessoPopup");

			montarPaginaSucesso(httpServletRequest, "Altera��o do e-mail efetuada com sucesso.", "", "");

			return retorno;
		}

		CobrancaDocumento cobrancaDocumento = new CobrancaDocumento();
		cobrancaDocumento.setId(new Integer(cobrancaDocumentoID));

		CobrancaDocumentoHelper cobrancaDocumentoHelper = fachada.apresentaItensDocumentoCobranca(cobrancaDocumento);
		if(httpServletRequest.getParameter("numeroOS") != null && !httpServletRequest.getParameter("numeroOS").equals("")){
			cobrancaDocumentoHelper.setIdOrdemServico(new Integer((String) httpServletRequest.getParameter("numeroOS")));
		}

		// loop respons�vel por colocar o valor ZERO nos itens JUROS_SOBRE_PARCELAMENTO quando foi
		// emitido uma antecipa��o de parcelas
		Collection<CobrancaDocumentoItem> coll = cobrancaDocumentoHelper.getColecaoCobrancaDocumentoItemDebitoACobrar();
		if(coll != null){
			Iterator<CobrancaDocumentoItem> it = coll.iterator();
			while(it.hasNext()){
				CobrancaDocumentoItem cdi = it.next();
				if(cdi != null
								&& cdi.getDebitoACobrarGeral() != null
								&& cdi.getDebitoACobrarGeral().getDebitoACobrar() != null
								&& cdi.getDebitoACobrarGeral().getDebitoACobrar().getDebitoTipo() != null
								&& cdi.getDebitoACobrarGeral().getDebitoACobrar().getDebitoTipo().getId().equals(
												DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){
					if(cdi.getValorJurosParcelaAntecipada() != null && cdi.getNumeroParcelaAntecipada() != null){
						cdi.setValorItemCobrado(cdi.getValorJurosParcelaAntecipada());
					}
				}
			}
		}

		documentoCobrancaItemActionForm.setMatriculaImovel(cobrancaDocumentoHelper.getCobrancaDocumento().getImovel().getId().toString());
		documentoCobrancaItemActionForm.setInscricaoImovel(cobrancaDocumentoHelper.getCobrancaDocumento().getImovel()
						.getInscricaoFormatada());
		documentoCobrancaItemActionForm.setSituacaoAguaImovel(cobrancaDocumentoHelper.getCobrancaDocumento().getImovel()
						.getLigacaoAguaSituacao().getDescricao());
		documentoCobrancaItemActionForm.setSituacaoEsgotoImovel(cobrancaDocumentoHelper.getCobrancaDocumento().getImovel()
						.getLigacaoEsgotoSituacao().getDescricao());
		documentoCobrancaItemActionForm.setSequencial(String.valueOf(cobrancaDocumentoHelper.getCobrancaDocumento()
						.getNumeroSequenciaDocumento()));

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getEmpresa() != null){
			documentoCobrancaItemActionForm.setEmpresaNome(cobrancaDocumentoHelper.getCobrancaDocumento().getEmpresa().getDescricao());
		}else{
			documentoCobrancaItemActionForm.setEmpresaNome("");
		}

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getValorDocumento() != null){
			documentoCobrancaItemActionForm.setValorDocumento(Util.formatarMoedaReal(cobrancaDocumentoHelper.getCobrancaDocumento()
							.getValorDocumento()));
		}

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getValorDesconto() != null){
			documentoCobrancaItemActionForm.setValorDesconto(Util.formatarMoedaReal(cobrancaDocumentoHelper.getCobrancaDocumento()
							.getValorDesconto()));
		}

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getDocumentoEmissaoForma() != null){
			documentoCobrancaItemActionForm.setFormaEmissao(cobrancaDocumentoHelper.getCobrancaDocumento().getDocumentoEmissaoForma()
							.getDescricaoDocumentoEmissaoForma());
		}

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getEmissao() != null){
			documentoCobrancaItemActionForm.setDataHoraEmissao(Util.formatarDataComHora(cobrancaDocumentoHelper.getCobrancaDocumento()
							.getEmissao()));
		}

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getMotivoNaoEntregaDocumento() != null){
			documentoCobrancaItemActionForm.setMotivoNaoEntregaDocumento(cobrancaDocumentoHelper.getCobrancaDocumento()
							.getMotivoNaoEntregaDocumento().getDescricao());
		}

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getDescricaoParecer() != null){
			documentoCobrancaItemActionForm.setDescricaoParecer(cobrancaDocumentoHelper.getCobrancaDocumento().getDescricaoParecer());
		}else{
			documentoCobrancaItemActionForm.setDescricaoParecer("");
		}

		documentoCobrancaItemActionForm.setQtdItens(cobrancaDocumentoHelper.getQuantidadeItensCobrancaDocumento().toString());

		httpServletRequest.setAttribute("imovel", cobrancaDocumentoHelper.getCobrancaDocumento().getImovel().getId().toString());
		httpServletRequest.setAttribute("cobrancaDocumentoHelper", cobrancaDocumentoHelper);

		// Negocia��o de d�bito
		CobrancaNegociacaoAtendimento cobrancaNegociacaoAtendimento = fachada.obterCobrancaNegociacaoAtendimento(Integer
						.valueOf(cobrancaDocumentoID));

		String idCobrancaNegociacaoAtendimentoForm = "";
		String tipoDocumentoNegociacaoForm = "";
		String idRegistroAtendimentoNegociacaoForm = "";
		String emailNegociacaoForm = "";
		String habilitarReEnviarDocumento = Integer.toString(ConstantesSistema.NAO);

		if(cobrancaNegociacaoAtendimento != null){
			CobrancaDocumento cobrancaDocumentoSelecionado = cobrancaDocumentoHelper.getCobrancaDocumento();
			Integer idCobrancaDocumento = cobrancaDocumentoSelecionado.getId();

			// Id
			Integer idCobrancaNegociacaoAtendimento = cobrancaNegociacaoAtendimento.getId();
			idCobrancaNegociacaoAtendimentoForm = Integer.toString(idCobrancaNegociacaoAtendimento);

			// Tipo do Documento
			CobrancaDocumento cobrancaDocumentoNegociacao = cobrancaNegociacaoAtendimento.getCobrancaDocumento();

			if(cobrancaDocumentoNegociacao != null){
				tipoDocumentoNegociacaoForm = CobrancaNegociacaoAtendimento.EXTRATO_DE_DEBITO;
			}else{
				tipoDocumentoNegociacaoForm = CobrancaNegociacaoAtendimento.PRE_PARCELAMENTO;
			}

			// Registro de Atendimento
			RegistroAtendimento registroAtendimentoNegociacao = cobrancaNegociacaoAtendimento.getRegistroAtendimento();

			if(registroAtendimentoNegociacao != null){
				Integer idRegistroAtendimentoNegociacao = registroAtendimentoNegociacao.getId();
				idRegistroAtendimentoNegociacaoForm = Integer.toString(idRegistroAtendimentoNegociacao);
			}

			// E-mail
			String emailNegociacao = cobrancaNegociacaoAtendimento.getEmail();

			if(!Util.isVazioOuBranco(emailNegociacao)){
				emailNegociacaoForm = emailNegociacao;
			}

			// Re-envio de Documento
			Date dataVencimentoNegociacao = cobrancaNegociacaoAtendimento.getDataVencimento();

			if(dataVencimentoNegociacao != null){
				Date dataAtual = new Date();

				int comparacaoData = Util.compararData(dataVencimentoNegociacao, dataAtual);

				if(comparacaoData >= 0){
					habilitarReEnviarDocumento = Integer.toString(ConstantesSistema.SIM);
				}
			}

			// Verifica se existe �tens pagos
			if(habilitarReEnviarDocumento.equals(Integer.toString(ConstantesSistema.SIM))){
				Integer quantidade = fachada.pesquisarQuantidadeDeItensPagos(idCobrancaDocumento);

				if(quantidade != 0){
					habilitarReEnviarDocumento = Integer.toString(ConstantesSistema.NAO);
				}
			}
		}

		documentoCobrancaItemActionForm.setIdCobrancaNegociacaoAtendimento(idCobrancaNegociacaoAtendimentoForm);
		documentoCobrancaItemActionForm.setTipoDocumentoNegociacao(tipoDocumentoNegociacaoForm);
		documentoCobrancaItemActionForm.setIdRegistroAtendimentoNegociacao(idRegistroAtendimentoNegociacaoForm);
		documentoCobrancaItemActionForm.setEmailNegociacao(emailNegociacaoForm);
		documentoCobrancaItemActionForm.setHabilitarReEnviarDocumento(habilitarReEnviarDocumento);

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getDataSituacaoAcaoFormatada() != null){
			documentoCobrancaItemActionForm.setDataEntrega(cobrancaDocumentoHelper.getCobrancaDocumento().getDataSituacaoAcaoFormatada());
		}

		if(cobrancaDocumentoHelper.getCobrancaDocumento().getCobrancaAcaoSituacao() != null){
			documentoCobrancaItemActionForm.setDescricaoCobrancaAcaoSituacao(cobrancaDocumentoHelper.getCobrancaDocumento()
							.getCobrancaAcaoSituacao().getDescricaoComId());
		}else{
			documentoCobrancaItemActionForm.setDescricaoCobrancaAcaoSituacao("");
		}

		return retorno;

	}
}
