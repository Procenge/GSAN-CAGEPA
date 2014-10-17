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

package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarComandoAtividadeFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarComandoAtividadeFaturamento");

		String atualizar = httpServletRequest.getParameter("atualizar");

		FiltrarComandoAtividadeFaturamentoActionForm pesquisarActionForm = (FiltrarComandoAtividadeFaturamentoActionForm) actionForm;

		String idFaturamentoGrupo = pesquisarActionForm.getIdGrupoFaturamento();

		String mesAno = pesquisarActionForm.getMesAno();

		Integer idFatuGrupo = null;
		Integer anoMes = null;

		boolean parametroInformado = false;

		if(idFaturamentoGrupo != null && !idFaturamentoGrupo.trim().equalsIgnoreCase("")
						&& !idFaturamentoGrupo.trim().equalsIgnoreCase("-1")){

			idFatuGrupo = Integer.valueOf(idFaturamentoGrupo);

			parametroInformado = true;

		}

		if(mesAno != null && !mesAno.trim().equals("")){

			String mes = mesAno.substring(0, 2);
			String ano = mesAno.substring(3, 7);
			anoMes = Integer.valueOf(ano + mes);

			parametroInformado = true;

		}

		if(!parametroInformado){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");

		}

		Integer totalRegistros = Fachada.getInstancia().buscarAtividadeComandadaNaoRealizadaCount(idFatuGrupo, anoMes);

		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		Integer numeroPaginas = (Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa");

		Collection collDados = Fachada.getInstancia().buscarAtividadeComandadaNaoRealizada(numeroPaginas, idFatuGrupo, anoMes);
		Collection<FaturamentoAtividadeCronograma> collFatAtividadeCronograma = new ArrayList<FaturamentoAtividadeCronograma>();

		for(Object[] arrayConteudoAtividade : (Collection<Object[]>) collDados){

			FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = new FaturamentoAtividadeCronograma();
			faturamentoAtividadeCronograma.setId(new Integer(String.valueOf(arrayConteudoAtividade[0])));
			faturamentoAtividadeCronograma.setFaturamentoAtividade((FaturamentoAtividade) arrayConteudoAtividade[1]);
			faturamentoAtividadeCronograma.setComando((Date) arrayConteudoAtividade[2]);
			faturamentoAtividadeCronograma.setDataPrevista((Date) arrayConteudoAtividade[3]);
			faturamentoAtividadeCronograma
							.setFaturamentoGrupoCronogramaMensal((FaturamentoGrupoCronogramaMensal) arrayConteudoAtividade[4]);

			collFatAtividadeCronograma.add(faturamentoAtividadeCronograma);

		}

		if(collFatAtividadeCronograma == null || collFatAtividadeCronograma.isEmpty()){
			// [FS0009] Nenhum registro encontrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "comando de atividade");
		}else{

			if(atualizar != null && totalRegistros == 1){

				sessao.setAttribute("faturamentoAtividadeCronogramaID", String
								.valueOf(collFatAtividadeCronograma.iterator().next().getId()));

				retorno = actionMapping.findForward("atualizarComandoAtividadeForward");
			}else{

				httpServletRequest.setAttribute("colecaoAtividadesAtualizacao", collFatAtividadeCronograma);

			}
		}

		return retorno;

	}

}
