/**
 * 
 */
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

package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaAcaoAtividadeImovel;
import gcom.cobranca.CobrancaAtividade;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeImovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.cobranca.RelatorioImoveisAcaoCobranca;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesAplicacao;
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
 * Permite consultar comandos de a��o de cobran�a
 * [UC0325] Consultar Comandos de A��o de Conbran�a
 * 
 * @author Rafael Santos
 * @since 11/05/2006
 * @author Virg�nia Melo
 * @date 11/11/2008
 *       Altera��es no [UC0325] para a v0.06
 */
public class ExibirResultadoConsultarComandosAcaoCobrancaCronogramaDadosComandoAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirComandosAcaoCobrancaCronogramaDadosComando");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String idCobrancaAcaoAtividadeCronograma = httpServletRequest.getParameter("idCobrancaAcaoAtividadeCronograma");
		String idCobrancaAcaoAtividadeCronogramaListaImoveis = httpServletRequest
						.getParameter("idCobrancaAcaoAtividadeCronogramaListaImoveis");

		if(!Util.isVazioOuBranco(idCobrancaAcaoAtividadeCronogramaListaImoveis)){
			FiltroCobrancaAcaoAtividadeImovel filtroCobrancaAcaoAtividadeImovel = new FiltroCobrancaAcaoAtividadeImovel();
			filtroCobrancaAcaoAtividadeImovel.setCampoDistinct(FiltroCobrancaAcaoAtividadeImovel.IMOVEL_ID);
			filtroCobrancaAcaoAtividadeImovel.adicionarParametro(new ParametroSimples(
							FiltroCobrancaAcaoAtividadeImovel.COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID,
							idCobrancaAcaoAtividadeCronogramaListaImoveis));

			Collection colCobrancaAcaoAtvImovel = getFachada().pesquisar(filtroCobrancaAcaoAtividadeImovel,
							CobrancaAcaoAtividadeImovel.class.getName());

			if(!Util.isVazioOrNulo(colCobrancaAcaoAtvImovel)){
				Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

				RelatorioImoveisAcaoCobranca relatorio = new RelatorioImoveisAcaoCobranca(usuarioLogado);
				relatorio.setIdFuncionalidadeIniciada(Funcionalidade.GERAR_ARQUIVO_IMOVEIS_COBRANCA);
				relatorio.addParametro("idCobrancaAcaoAtividadeComando", ConstantesSistema.NUMERO_NAO_INFORMADO);
				relatorio.addParametro("idCobrancaAcaoAtividadeCronograma", Integer.valueOf(idCobrancaAcaoAtividadeCronogramaListaImoveis));
				relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);

				fachada.iniciarProcessoRelatorio(relatorio);

				retorno = actionMapping.findForward("telaSucessoPopup");
				montarPaginaSucesso(httpServletRequest, ConstantesAplicacao.get("atencao.execucao.relatorio.batch"), "", "");

				return retorno;
			}else{
				throw new ActionServletException("atencao.nao_ha_imovel_para_cobranca", null, "");
			}
		}

		httpServletRequest.setAttribute("idCobrancaAcaoAtividadeCronogramaListaImoveis", idCobrancaAcaoAtividadeCronograma);

		CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = fachada
						.obterCobrancaAcaoAtividadeCronograma(idCobrancaAcaoAtividadeCronograma);

		ExibirResultadoConsultarComandosAcaoCobrancaCronogramaDadosComandoActionForm dadosActionForm = (ExibirResultadoConsultarComandosAcaoCobrancaCronogramaDadosComandoActionForm) actionForm;

		if(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma() != null
						&& cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes() != null
						&& cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes().getCobrancaGrupo() != null){
			dadosActionForm.setGrupoCobranca(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes()
							.getCobrancaGrupo().getDescricao());
		}

		if(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma() != null
						&& cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes() != null){
			dadosActionForm.setReferenciaCobranca(Integer.valueOf(
							cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes()
											.getAnoMesReferencia()).toString());
		}
		if(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma() != null
						&& cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaAcao() != null){
			dadosActionForm.setAcaoCobranca(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaAcao()
							.getDescricaoCobrancaAcao());
		}
		if(cobrancaAcaoAtividadeCronograma.getCobrancaAtividade() != null){
			dadosActionForm.setAtividadeCobranca(cobrancaAcaoAtividadeCronograma.getCobrancaAtividade().getDescricaoCobrancaAtividade());
		}
		dadosActionForm.setDataPrevistaCronograma(Util.formatarData(cobrancaAcaoAtividadeCronograma.getDataPrevista()));
		if(cobrancaAcaoAtividadeCronograma.getComando() != null){
			dadosActionForm.setDataComando(Util.formatarData(cobrancaAcaoAtividadeCronograma.getComando()));
			dadosActionForm.setHoraComando(Util.formatarHoraSemData(cobrancaAcaoAtividadeCronograma.getComando()));
		}
		if(cobrancaAcaoAtividadeCronograma.getRealizacao() != null){
			dadosActionForm.setDataRealizacao(Util.formatarData(cobrancaAcaoAtividadeCronograma.getRealizacao()));
			dadosActionForm.setHoraRealizacao(Util.formatarHoraSemData(cobrancaAcaoAtividadeCronograma.getRealizacao()));
		}
		if(cobrancaAcaoAtividadeCronograma.getValorDocumentos() != null){
			dadosActionForm.setValorDocumentos(cobrancaAcaoAtividadeCronograma.getValorDocumentos().toString());
		}
		if(cobrancaAcaoAtividadeCronograma.getQuantidadeDocumentos() != null){
			dadosActionForm.setQuantidadeDocumentos(cobrancaAcaoAtividadeCronograma.getQuantidadeDocumentos().toString());
		}
		if(cobrancaAcaoAtividadeCronograma.getQuantidadeItensCobrados() != null){
			dadosActionForm.setQuantidadeItensDocumentos(cobrancaAcaoAtividadeCronograma.getQuantidadeItensCobrados().toString());
		}

		if(cobrancaAcaoAtividadeCronograma.getComando() == null){
			dadosActionForm.setSituacaoComando("N�o Comandado");
		}else{
			dadosActionForm.setSituacaoComando("Comandado");
		}

		if(cobrancaAcaoAtividadeCronograma.getRealizacao() == null){
			dadosActionForm.setSituacaoCronograma("N�o Realizado");
		}else{
			dadosActionForm.setSituacaoCronograma("Realizado");
			if(cobrancaAcaoAtividadeCronograma.getCobrancaAtividade().getId().equals(CobrancaAtividade.EMITIR)){
				httpServletRequest.setAttribute("emitir", "sim");
			}
		}

		httpServletRequest.setAttribute("idCobrancaAcaoAtividadeCronograma", idCobrancaAcaoAtividadeCronograma);
		sessao.setAttribute("cobrancaAcaoAtividadeCronograma", cobrancaAcaoAtividadeCronograma);

		return retorno;
	}

}
