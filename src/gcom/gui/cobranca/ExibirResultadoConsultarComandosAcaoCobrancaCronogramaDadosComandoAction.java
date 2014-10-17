/**
 * 
 */
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
 * Permite consultar comandos de ação de cobrança
 * [UC0325] Consultar Comandos de Ação de Conbrança
 * 
 * @author Rafael Santos
 * @since 11/05/2006
 * @author Virgínia Melo
 * @date 11/11/2008
 *       Alterações no [UC0325] para a v0.06
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
			dadosActionForm.setSituacaoComando("Não Comandado");
		}else{
			dadosActionForm.setSituacaoComando("Comandado");
		}

		if(cobrancaAcaoAtividadeCronograma.getRealizacao() == null){
			dadosActionForm.setSituacaoCronograma("Não Realizado");
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
