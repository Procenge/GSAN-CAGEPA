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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.arrecadacao.pagamento.bean.AutenticarPagamentosHelper;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3094] Autenticar Pagamento
 * Este caso de uso gera uma autentica��o do pagamento.
 * 
 * @author Josenildo Neves
 * @created 08 de abril de 2013
 */
public class ExibirAutenticarPagamentoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("autenticarPagamentos");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		AutenticarPagamentosActionForm form = (AutenticarPagamentosActionForm) actionForm;
		form.reset(actionMapping, httpServletRequest);

		Pagamento pagamentoAtualizado = (Pagamento) sessao.getAttribute("pagamento");

		Collection<Object> colecaoObjetosConsultadosOuInseridos = (Collection<Object>) sessao.getAttribute("colecaoPagamento");
		// Collection<Pagamento> colecaoPagamentosInseridos = (Collection<Pagamento>)
		// sessao.getAttribute("colecaoPagamento");

		Collection<AutenticarPagamentosHelper> colecaoPagamentos = null;

		if(Util.isNaoNuloBrancoZero(pagamentoAtualizado)){

			colecaoPagamentos = new ArrayList<AutenticarPagamentosHelper>();
			AutenticarPagamentosHelper helper = new AutenticarPagamentosHelper();

			helper.setIdPagamento(pagamentoAtualizado.getId().toString());

			if(Util.isNaoNuloBrancoZero(pagamentoAtualizado.getImovel())){
				helper.setIdImovel(pagamentoAtualizado.getImovel().getId().toString());
			}
			if(Util.isNaoNuloBrancoZero(pagamentoAtualizado.getCliente())){
				helper.setIdCliente(pagamentoAtualizado.getCliente().getId().toString());
			}

			if(Util.isNaoNuloBrancoZero(pagamentoAtualizado.getDocumentoTipo())){
				helper.setDescricaoTipoDocimento(pagamentoAtualizado.getDocumentoTipo().getDescricaoDocumentoTipo());
			}

			helper.setReferenciaPagamento(Util.formatarAnoMesParaMesAno(pagamentoAtualizado.getAnoMesReferenciaPagamento()));
			helper.setValorPagamento(Util.formatarMoedaReal(pagamentoAtualizado.getValorPagamento()));
			helper.setDataPagamento(Util.formatarDataAAAAMMDD(pagamentoAtualizado.getDataPagamento()));

			colecaoPagamentos.add(helper);

		}else{
			if(!Util.isVazioOrNulo(colecaoObjetosConsultadosOuInseridos)){
				colecaoPagamentos = new ArrayList<AutenticarPagamentosHelper>();

				for(Object objeto : colecaoObjetosConsultadosOuInseridos){
					AutenticarPagamentosHelper helper = new AutenticarPagamentosHelper();

					String idPagamento = null;
					String idImovel = null;
					String idCliente = null;
					String descricaoDocumentoTipo = null;
					Integer idDocumentoTipo = null;
					Integer anoMesRef = null;
					BigDecimal valorPgto = null;
					Date dataPgto = null;

					if(objeto instanceof Pagamento){
						Pagamento pagamento = (Pagamento) objeto;
						idPagamento = pagamento.getId().toString();
						if(Util.isNaoNuloBrancoZero(pagamento.getImovel())){
							idImovel = pagamento.getImovel().getId().toString();
						}
						if(Util.isNaoNuloBrancoZero(pagamento.getCliente())){
							idCliente = pagamento.getCliente().getId().toString();
						}
						DocumentoTipo documentoTipo = pagamento.getDocumentoTipo();
						idDocumentoTipo = documentoTipo.getId();
						if(Util.isNaoNuloBrancoZero(documentoTipo) && Util.isNaoNuloBrancoZero(documentoTipo.getDescricaoDocumentoTipo())){
							descricaoDocumentoTipo = documentoTipo.getDescricaoDocumentoTipo();
						}
						anoMesRef = pagamento.getAnoMesReferenciaPagamento();
						valorPgto = pagamento.getValorPagamento();
						dataPgto = pagamento.getDataPagamento();
					}else if(objeto instanceof PagamentoHistorico){
						PagamentoHistorico pagamento = (PagamentoHistorico) objeto;
						idPagamento = pagamento.getId().toString();
						if(Util.isNaoNuloBrancoZero(pagamento.getImovel())){
							idImovel = pagamento.getImovel().getId().toString();
						}
						if(Util.isNaoNuloBrancoZero(pagamento.getCliente())){
							idCliente = pagamento.getCliente().getId().toString();
						}
						DocumentoTipo documentoTipo = pagamento.getDocumentoTipo();
						idDocumentoTipo = documentoTipo.getId();
						if(Util.isNaoNuloBrancoZero(documentoTipo)
										&& Util.isNaoNuloBrancoZero(documentoTipo.getDescricaoDocumentoTipo())){
							descricaoDocumentoTipo = documentoTipo.getDescricaoDocumentoTipo();
						}
						anoMesRef = pagamento.getAnoMesReferenciaPagamento();
						valorPgto = pagamento.getValorPagamento();
						dataPgto = pagamento.getDataPagamento();
					}

					helper.setIdPagamento(idPagamento);

					if(Util.isNaoNuloBrancoZero(idImovel)){
						helper.setIdImovel(idImovel);
					}
					if(Util.isNaoNuloBrancoZero(idCliente)){
						helper.setIdCliente(idCliente);
					}

					if(Util.isNaoNuloBrancoZero(descricaoDocumentoTipo)){
						helper.setDescricaoTipoDocimento(descricaoDocumentoTipo);
					}else{

						FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
						filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, idDocumentoTipo));
						Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

						if(!Util.isVazioOrNulo(colecaoDocumentoTipo)){
							DocumentoTipo documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);
							helper.setDescricaoTipoDocimento(documentoTipo.getDescricaoDocumentoTipo());
						}
					}
					if(Util.isNaoNuloBrancoZero(anoMesRef)){
						helper.setReferenciaPagamento(Util.formatarAnoMesParaMesAno(anoMesRef));
					}
					helper.setValorPagamento(Util.formatarMoedaReal(valorPgto));
					helper.setDataPagamento(Util.formatarDataAAAAMMDD(dataPgto));

					colecaoPagamentos.add(helper);

				}
			}

		}

		sessao.setAttribute("colecaoPagamentos", colecaoPagamentos);
		if(colecaoPagamentos != null){
			sessao.setAttribute("totalRegistros", colecaoPagamentos.size());
		}else{
			sessao.setAttribute("totalRegistros", 0);
		}

		return retorno;
	}
}
