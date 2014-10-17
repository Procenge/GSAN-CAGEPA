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

package gcom.gui.arrecadacao.pagamento;

import java.util.Collection;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action reponsável pela exibição da tela com informações do pagamento.
 * 
 * @date 17/08/2009
 * @author vmelo
 * @author Saulo Lima
 * @date 28/09/2009
 *       Exibir a prestação de um Débito A Cobrar, quando existir.
 */
public class ExibirPagamentoPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirPagamentoPopup");
		Fachada fachada = Fachada.getInstancia();
		ConsultarPagamentoPopupActionForm form = (ConsultarPagamentoPopupActionForm) actionForm;
		String idPagamentoHistorico = httpServletRequest.getParameter("idPagamentoHistorico");

		if(idPagamentoHistorico != null){

			// Dados do pagamento
			String codigoAgenteArregacador = "";
			String dataLancamento = "";
			String sequencial = "";
			String formaArrecadacao = "";
			String tipoDocumento = "";
			String idLocalidade = "";
			String descricaoLocalidade = "";
			String matriculaImovel = "";
			String codigoCliente = "";
			String nomeCliente = "";
			String valorPagamento = "";
			String dataPagamento = "";
			String situacaoPagamento = "";

			String tipoDebito = "";
			String idConta = "";
			String referenciaConta = "";
			String idGuiaPagamento = "";
			String numeroPrestacao = "";
			String idDebitoCobrar = "";
			String quantidadeParcelas = "";

			PagamentoHistorico pagamentoHistorico = fachada.consultarPagamentoHistorico(Integer.valueOf(idPagamentoHistorico));

			if(pagamentoHistorico != null){ // pesquisou no historico

				if(pagamentoHistorico.getAvisoBancario() != null){
					codigoAgenteArregacador = String.valueOf(pagamentoHistorico.getAvisoBancario().getArrecadador().getCodigoAgente());
					dataLancamento = Util.formatarData(pagamentoHistorico.getAvisoBancario().getDataLancamento());
					sequencial = String.valueOf(pagamentoHistorico.getAvisoBancario().getNumeroSequencial());
				}

				if(pagamentoHistorico.getArrecadacaoForma() != null && pagamentoHistorico.getArrecadacaoForma().getDescricao() != null){
					formaArrecadacao = pagamentoHistorico.getArrecadacaoForma().getDescricao();
				}

				if(pagamentoHistorico.getDocumentoTipo().getDescricaoDocumentoTipo() != null){
					tipoDocumento = pagamentoHistorico.getDocumentoTipo().getDescricaoDocumentoTipo();
				}

				if(pagamentoHistorico.getImovel() != null){
					matriculaImovel = String.valueOf(pagamentoHistorico.getImovel().getId());
				}
				if(pagamentoHistorico.getCliente() != null){
					codigoCliente = String.valueOf(pagamentoHistorico.getCliente().getId());
					if(pagamentoHistorico.getCliente().getNome() != null){
						nomeCliente = pagamentoHistorico.getCliente().getNome();
					}
				}

				if(pagamentoHistorico.getPagamentoSituacaoAtual() != null){
					situacaoPagamento = pagamentoHistorico.getPagamentoSituacaoAtual().getDescricao();
				}

				valorPagamento = String.valueOf(pagamentoHistorico.getValorPagamento());
				dataPagamento = Util.formatarData(pagamentoHistorico.getDataPagamento());
				idLocalidade = String.valueOf(pagamentoHistorico.getLocalidade().getId());
				descricaoLocalidade = String.valueOf(pagamentoHistorico.getLocalidade().getDescricao());
				if(pagamentoHistorico.getNumeroPrestacao() != null){
					quantidadeParcelas = pagamentoHistorico.getNumeroPrestacao().toString();
				}
				// Verificando qual o tipo do documento para poder exibir informações referentes a
				// ele
				if(pagamentoHistorico.getConta() != null){

					idConta = String.valueOf(pagamentoHistorico.getConta().getId());
					referenciaConta = Util.formatarAnoMesParaMesAno(pagamentoHistorico.getConta().getAnoMesReferenciaConta());

				}else if(pagamentoHistorico.getGuiaPagamentoGeral() != null){

					idGuiaPagamento = String.valueOf(pagamentoHistorico.getGuiaPagamentoGeral().getId());
					numeroPrestacao = String.valueOf(pagamentoHistorico.getNumeroPrestacao());

					if(pagamentoHistorico.getDebitoTipo() != null){
						tipoDebito = pagamentoHistorico.getDebitoTipo().getDescricao();
					}

				}else if(pagamentoHistorico.getDebitoACobrar() != null){

					idDebitoCobrar = String.valueOf(pagamentoHistorico.getDebitoACobrar().getId());
					tipoDebito = pagamentoHistorico.getDebitoTipo().getDescricao();

					if(pagamentoHistorico.getDebitoTipo() != null){
						tipoDebito = pagamentoHistorico.getDebitoTipo().getDescricao();
					}

					if(pagamentoHistorico.getNumeroPrestacao() != null){
						numeroPrestacao = String.valueOf(pagamentoHistorico.getNumeroPrestacao());
					}
				}
			}else{ // historico veio vazio, entao buscar no pagamento atual.

				Collection<Pagamento> pagamentos = fachada.pesquisarPagamentoPeloId(Integer.valueOf(idPagamentoHistorico));
				if(pagamentos != null && !pagamentos.isEmpty()){
					Pagamento pagamento = pagamentos.iterator().next();

					if(pagamento.getAvisoBancario() != null){
						codigoAgenteArregacador = String.valueOf(pagamento.getAvisoBancario().getArrecadador().getCodigoAgente());
						dataLancamento = Util.formatarData(pagamento.getAvisoBancario().getDataLancamento());
						sequencial = String.valueOf(pagamento.getAvisoBancario().getNumeroSequencial());
					}

					if(pagamento.getArrecadacaoForma() != null && pagamento.getArrecadacaoForma().getDescricao() != null){
						formaArrecadacao = pagamento.getArrecadacaoForma().getDescricao();
					}

					if(pagamento.getDocumentoTipo().getDescricaoDocumentoTipo() != null){
						tipoDocumento = pagamento.getDocumentoTipo().getDescricaoDocumentoTipo();
					}

					if(pagamento.getImovel() != null){
						matriculaImovel = String.valueOf(pagamento.getImovel().getId());
					}
					if(pagamento.getCliente() != null){
						codigoCliente = String.valueOf(pagamento.getCliente().getId());
						if(pagamento.getCliente().getNome() != null){
							nomeCliente = pagamento.getCliente().getNome();
						}
					}

					if(pagamento.getPagamentoSituacaoAtual() != null){
						situacaoPagamento = pagamento.getPagamentoSituacaoAtual().getDescricao();
					}

					valorPagamento = String.valueOf(pagamento.getValorPagamento());
					dataPagamento = Util.formatarData(pagamento.getDataPagamento());
					idLocalidade = String.valueOf(pagamento.getLocalidade().getId());
					descricaoLocalidade = String.valueOf(pagamento.getLocalidade().getDescricao());
					quantidadeParcelas = pagamento.getNumeroPrestacao().toString();

					// Verificando qual o tipo do documento para poder exibir informações referentes
					// a ele
					if(pagamento.getConta() != null){

						idConta = String.valueOf(pagamento.getConta().getId());
						referenciaConta = Util.formatarAnoMesParaMesAno(pagamento.getConta().getReferencia());

					}else if(pagamento.getGuiaPagamentoGeral() != null){

						idGuiaPagamento = String.valueOf(pagamento.getGuiaPagamentoGeral().getId());
						numeroPrestacao = String.valueOf(pagamento.getNumeroPrestacao());

						if(pagamento.getDebitoTipo() != null){
							tipoDebito = pagamento.getDebitoTipo().getDescricao();
						}

					}else if(pagamento.getDebitoACobrar() != null){

						idDebitoCobrar = String.valueOf(pagamento.getDebitoACobrar().getId());
						tipoDebito = pagamento.getDebitoTipo().getDescricao();

						if(pagamento.getDebitoTipo() != null){
							tipoDebito = pagamento.getDebitoTipo().getDescricao();
						}
					}
				}
			}

			form.setCodigoAgenteArrecadador(codigoAgenteArregacador);
			form.setDataLancamento(dataLancamento);
			form.setSequencial(sequencial);
			form.setFormaArrecadacao(formaArrecadacao);
			form.setTipoDocumento(tipoDocumento);
			form.setCodigoLocalidade(idLocalidade);
			form.setDescricaoLocalidade(descricaoLocalidade);
			form.setMatriculaImovel(matriculaImovel);
			form.setCodigoCliente(codigoCliente);
			form.setCodigoCliente(nomeCliente);
			form.setValorPagamento(valorPagamento);
			form.setDataPagamento(dataPagamento);
			form.setSituacaoPagamento(situacaoPagamento);

			form.setTipoDebito(tipoDebito);
			form.setIdConta(idConta);
			form.setReferenciaConta(referenciaConta);
			form.setIdGuiaPagamento(idGuiaPagamento);
			form.setNumeroPrestacao(numeroPrestacao);
			form.setIdDebito(idDebitoCobrar);
			form.setQuantidadeParcelas(quantidadeParcelas);

		}
		return retorno;
	}
}
