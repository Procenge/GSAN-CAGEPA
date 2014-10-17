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

package gcom.gui.arrecadacao.aviso;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.FiltroAvisoAcerto;
import gcom.arrecadacao.FiltroDevolucao;
import gcom.arrecadacao.FiltroDevolucaoHistorico;
import gcom.arrecadacao.aviso.AvisoAcerto;
import gcom.arrecadacao.pagamento.FiltroPagamento;
import gcom.arrecadacao.pagamento.FiltroPagamentoHistorico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0611] Movimentar Pagamentos/ Devoluções entre Avisos Bancários
 * 
 * @author Ana Maria
 * @date 07/06/2007
 */
public class SelecionarPagamentosAvisoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirMovimentarPagamentosDevolucoesAvisoBancario");

		HttpSession sessao = httpServletRequest.getSession(false);

		SelecionarPagamentosAvisoBancarioActionForm form = (SelecionarPagamentosAvisoBancarioActionForm) actionForm;

		// Recupera os parâmetros do form
		Integer avisoBancarioO = new Integer(form.getAvisoBancarioO());
		Integer avisoBancarioD = new Integer(form.getAvisoBancarioD());
		String dataDevolucaoInicial = form.getDataDevolucaoInicial();
		String dataDevolucaoFinal = form.getDataDevolucaoFinal();
		String dataPagamentoInicial = form.getDataPagamentoInicial();
		String dataPagamentoFinal = form.getDataPagamentoFinal();
		Integer idSituacaoDevolucao = Util.obterInteger(form.getIdSituacaoDevolucao());
		Integer idSituacaoPagamento = Util.obterInteger(form.getIdSituacaoPagamento());
		Integer[] idArrecadacaoForma = new Integer[0];
		if(form.getIdArrecadacaoForma() != null){
			idArrecadacaoForma = new Integer[form.getIdArrecadacaoForma().length];
			for(int i = 0; i < form.getIdArrecadacaoForma().length; i++){
				idArrecadacaoForma[i] = new Integer(form.getIdArrecadacaoForma()[i]);
			}
		}
		Integer[] idTipoDocumentoPagamento = new Integer[0];
		if(form.getIdTipoDocumentoPagamento() != null){
			idTipoDocumentoPagamento = new Integer[form.getIdTipoDocumentoPagamento().length];
			for(int i = 0; i < form.getIdTipoDocumentoPagamento().length; i++){
				idTipoDocumentoPagamento[i] = new Integer(form.getIdTipoDocumentoPagamento()[i]);
			}
		}

		// [UC0611][FS0005] Verificar aviso bancário de arrecadadores distintos
		if(!form.getCodigoAgenteArrecadadorO().equals(form.getCodigoAgenteArrecadadorD())){
			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, Util.obterInteger(form
							.getCodigoAgenteArrecadadorO())));

			Arrecadador arrecadadorO = (Arrecadador) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroArrecadador,
							Arrecadador.class.getName()));

			filtroArrecadador.limparListaParametros();
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, Util.obterInteger(form
							.getCodigoAgenteArrecadadorD())));

			Arrecadador arrecadadorD = (Arrecadador) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroArrecadador,
							Arrecadador.class.getName()));

			throw new ActionServletException("atencao.avisos_bancarios_devem_ser_mesmo_arrecadador", arrecadadorO.getCodigoAgente() + " - "
							+ arrecadadorO.getCliente().getNome(), arrecadadorD.getCodigoAgente() + " - "
							+ arrecadadorD.getCliente().getNome());
		}

		// [UC0611][FS0006] Verificar existência de acertos para o aviso bancário de origem
		FiltroAvisoAcerto filtroAvisoAcerto = new FiltroAvisoAcerto();
		filtroAvisoAcerto.adicionarParametro(new ParametroSimples(FiltroAvisoAcerto.AVISO_BANCARIO_ID, avisoBancarioO));

		Collection<AvisoAcerto> colecaoAvisoAcertos = getFachada().pesquisar(filtroAvisoAcerto, AvisoAcerto.class.getName());

		if(colecaoAvisoAcertos != null && !colecaoAvisoAcertos.isEmpty()){
			throw new ActionServletException("atencao.ha_acertos_aviso_bancario_origem");
		}

		boolean peloMenosUmParametroInformado = false;

		FiltroDevolucao filtroDevolucao = null;
		FiltroDevolucaoHistorico filtroDevolucaoHistorico = null;

		Date dataInicialDevolucao = null;
		Date dataFinalDevolucao = null;

		if(dataDevolucaoInicial != null && !dataDevolucaoInicial.equals("") && dataDevolucaoFinal != null && !dataDevolucaoFinal.equals("")){
			peloMenosUmParametroInformado = true;

			if(!Util.validaDataLinear(dataDevolucaoInicial) || !Util.validaDataLinear(dataDevolucaoFinal)){
				throw new ActionServletException("atencao.data.inicio.invalida");
			}

			dataInicialDevolucao = Util.converteStringParaDate(dataDevolucaoInicial);
			dataFinalDevolucao = Util.converteStringParaDate(dataDevolucaoFinal);

			if(Util.compararData(dataInicialDevolucao, new Date()) > 0 || Util.compararData(dataFinalDevolucao, new Date()) > 0){
				throw new ActionServletException("atencao.data_posterior_data_corrente", null, Util.formatarData(new Date()));
			}

			// [UC0611][FS0007] Verificar data final menor que inicial
			if(Util.compararData(dataInicialDevolucao, dataFinalDevolucao) > 0){
				throw new ActionServletException("atencao.data_final_periodo_anterior_data_inicial");
			}
		}

		boolean usaDevolucao = false;
		boolean usaHistoricoDevolucao = false;
		switch(idSituacaoDevolucao){
			case 0:
				usaHistoricoDevolucao = true;
				break;
			case 1:
				usaDevolucao = true;
				break;
			case 2:
				usaHistoricoDevolucao = true;
				usaDevolucao = true;
				break;
			default:
				break;
		}

		if(usaDevolucao){
			filtroDevolucao = new FiltroDevolucao();
			filtroDevolucao.adicionarParametro(new ParametroSimples(FiltroDevolucao.AVISO_BANCARIO_ID, avisoBancarioO));

			if(dataInicialDevolucao != null && !dataInicialDevolucao.equals("") && dataFinalDevolucao != null
							&& !dataFinalDevolucao.equals("")){
				filtroDevolucao.adicionarParametro(new Intervalo(FiltroDevolucao.DATA_DEVOLUCAO, dataInicialDevolucao, dataFinalDevolucao));
			}
			filtroDevolucao.setCampoOrderBy(FiltroDevolucao.DATA_DEVOLUCAO);
		}
		if(usaHistoricoDevolucao){
			filtroDevolucaoHistorico = new FiltroDevolucaoHistorico();
			filtroDevolucaoHistorico.adicionarParametro(new ParametroSimples(FiltroDevolucaoHistorico.AVISO_BANCARIO_ID, avisoBancarioO));
			filtroDevolucaoHistorico.adicionarParametro(new Intervalo(FiltroDevolucaoHistorico.DATA_DEVOLUCAO, dataInicialDevolucao,
							dataFinalDevolucao));
			filtroDevolucaoHistorico.setCampoOrderBy(FiltroDevolucaoHistorico.DATA_DEVOLUCAO);
		}

		Filtro[] colecaoFiltroDevolucao = new Filtro[2];
		colecaoFiltroDevolucao[0] = filtroDevolucao;
		colecaoFiltroDevolucao[1] = filtroDevolucaoHistorico;

		FiltroPagamento filtroPagamento = null;
		FiltroPagamentoHistorico filtroPagamentoHistorico = null;

		peloMenosUmParametroInformado = true;

		boolean usaPagamento = false;
		boolean usaPagamentoHistorico = false;

		switch(idSituacaoPagamento){
			case 0:
				usaPagamentoHistorico = true;
				break;
			case 1:
				usaPagamento = true;
				break;
			case 2:
				usaPagamentoHistorico = true;
				usaPagamento = true;
				break;
			default:
				break;
		}

		if(usaPagamento){
			filtroPagamento = new FiltroPagamento();
			filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.AVISO_BANCARIO_ID, avisoBancarioO));
			filtroPagamento.setCampoOrderBy(FiltroPagamento.DATA_PAGAMENTO);
		}
		if(usaPagamentoHistorico){
			filtroPagamentoHistorico = new FiltroPagamentoHistorico();
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.AVISO_BANCARIO_ID, avisoBancarioO));
			filtroPagamentoHistorico.setCampoOrderBy(FiltroPagamentoHistorico.DATA_PAGAMENTO);
		}
		if(dataPagamentoInicial != null && !dataPagamentoInicial.equals("") && dataPagamentoFinal != null && !dataPagamentoFinal.equals("")){
			if(!Util.validaDataLinear(dataPagamentoInicial) || !Util.validaDataLinear(dataPagamentoFinal)){
				throw new ActionServletException("atencao.data.inicio.invalida");
			}
			Date dataInicial = Util.converteStringParaDate(dataPagamentoInicial);
			Date dataFinal = Util.converteStringParaDate(dataPagamentoFinal);

			if(Util.compararData(dataInicial, new Date()) > 0 || Util.compararData(dataFinal, new Date()) > 0){
				throw new ActionServletException("atencao.data_posterior_data_corrente", null, Util.formatarData(new Date()));
			}

			// [UC0611][FS0007] Verificar data final menor que inicial
			if(Util.compararData(dataInicial, dataFinal) > 0){
				throw new ActionServletException("atencao.data_final_periodo_anterior_data_inicial");
			}
			if(usaPagamento){
				filtroPagamento.adicionarParametro(new Intervalo(FiltroPagamento.DATA_PAGAMENTO, dataInicial, dataFinal));
			}
			if(usaPagamentoHistorico){
				filtroPagamentoHistorico.adicionarParametro(new Intervalo(FiltroPagamentoHistorico.DATA_PAGAMENTO, dataInicial, dataFinal));
			}
		}
		if(idArrecadacaoForma != null && idArrecadacaoForma.length > 0){
			for(int i = 0; i < idArrecadacaoForma.length; i++){
				if(usaPagamento){
					if(i == idArrecadacaoForma.length - 1){
						filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.PAGAMENTO_ARRECADACAO_FORMA,
										idArrecadacaoForma[i]));
					}else{
						filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.PAGAMENTO_ARRECADACAO_FORMA,
										idArrecadacaoForma[i], ConectorOr.CONECTOR_OR));
					}
				}
				if(usaPagamentoHistorico){
					if(i == idArrecadacaoForma.length - 1){
						filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(
										FiltroPagamentoHistorico.PAGAMENTO_ARRECADACAO_FORMA, idArrecadacaoForma[i]));
					}else{
						filtroPagamentoHistorico
										.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.PAGAMENTO_ARRECADACAO_FORMA,
														idArrecadacaoForma[i], ConectorOr.CONECTOR_OR));
					}
				}
			}
		}
		if(idTipoDocumentoPagamento != null && idTipoDocumentoPagamento.length > 0){
			for(int i = 0; i < idTipoDocumentoPagamento.length; i++){
				if(usaPagamento){
					if(i == idTipoDocumentoPagamento.length - 1){
						filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.DOCUMENTO_TIPO_ID,
										idTipoDocumentoPagamento[i]));
					}else{
						filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.DOCUMENTO_TIPO_ID,
										idTipoDocumentoPagamento[i], ConectorOr.CONECTOR_OR));
					}
				}
				if(usaPagamentoHistorico){
					if(i == idTipoDocumentoPagamento.length - 1){
						filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.DOCUMENTO_TIPO_ID,
										idTipoDocumentoPagamento[i]));
					}else{
						filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.DOCUMENTO_TIPO_ID,
										idTipoDocumentoPagamento[i], ConectorOr.CONECTOR_OR));
					}
				}
			}
		}

		Filtro[] colecaoFiltroPagamento = new Filtro[2];
		colecaoFiltroPagamento[0] = filtroPagamento;
		colecaoFiltroPagamento[1] = filtroPagamentoHistorico;

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// Manda o filtro pela sessão
		sessao.setAttribute("filtroDevolucao", colecaoFiltroDevolucao);
		sessao.setAttribute("filtroPagamento", colecaoFiltroPagamento);

		sessao.setAttribute("avisoBancarioO", avisoBancarioO);
		sessao.setAttribute("avisoBancarioD", avisoBancarioD);

		String descricaoABOrigem = form.getCodigoAgenteArrecadadorO() + " - " + form.getDataLancamentoAvisoO() + " - "
						+ form.getNumeroSequencialAvisoO();
		String descricaoABDestino = form.getCodigoAgenteArrecadadorD() + " - " + form.getDataLancamentoAvisoD() + " - "
						+ form.getNumeroSequencialAvisoD();

		sessao.setAttribute("descricaoABOrigem", descricaoABOrigem);
		sessao.setAttribute("descricaoABDestino", descricaoABDestino);

		return retorno;

	}
}