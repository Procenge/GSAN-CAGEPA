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
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.faturamento.FiltroFaturamentoAtividade;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupoCronogramaMensal;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarFaturamentoCronogramaAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarFaturamentoCronograma");

		FaturamentoActionForm faturamentoActionForm = (FaturamentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		String idFaturamentoGrupoCronogramaMensal = null;
		if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
			idFaturamentoGrupoCronogramaMensal = httpServletRequest.getParameter("idRegistroAtualizacao");
		}else{
			idFaturamentoGrupoCronogramaMensal = (String) sessao.getAttribute("idRegistroAtualizacao");
		}
		// joga faturamentogrupocronogramamensal na sessao para comparacao de concorrencia
		FiltroFaturamentoGrupoCronogramaMensal filtroFaturamentoGrupoCronogramaMensalSessao = new FiltroFaturamentoGrupoCronogramaMensal();
		filtroFaturamentoGrupoCronogramaMensalSessao.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupoCronogramaMensal.ID,
						idFaturamentoGrupoCronogramaMensal));
		Collection colecaoFaturamentoGrupoCronogramaMensal = fachada.pesquisar(filtroFaturamentoGrupoCronogramaMensalSessao,
						FaturamentoGrupoCronogramaMensal.class.getName());

		if(!colecaoFaturamentoGrupoCronogramaMensal.isEmpty()){
			FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensalSessao = (FaturamentoGrupoCronogramaMensal) colecaoFaturamentoGrupoCronogramaMensal
							.iterator().next();

			sessao.setAttribute("faturamentoGrupoCronogramaMensalSessao", faturamentoGrupoCronogramaMensalSessao);
		}

		sessao.setAttribute("idGrupoFaturamento", idFaturamentoGrupoCronogramaMensal);

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection faturamentoGrupos = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
		if(!faturamentoGrupos.isEmpty()){
			sessao.setAttribute("faturamentoGrupos", faturamentoGrupos);
		}else{
			throw new ActionServletException("erro.naocadastrado", null, "grupo de faturamento");
		}

		FiltroFaturamentoGrupoCronogramaMensal filtroFaturamentoGrupoCronogramaMensal = new FiltroFaturamentoGrupoCronogramaMensal();
		FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new FiltroFaturamentoAtividadeCronograma();
		Collection faturamentoGrupoCronogramaMensais = null;
		Collection faturamentoAtividadeCronogramas = new ArrayList();

		if(idFaturamentoGrupoCronogramaMensal != null && !idFaturamentoGrupoCronogramaMensal.trim().equalsIgnoreCase("")){
			filtroFaturamentoGrupoCronogramaMensal.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupoCronogramaMensal.ID,
							idFaturamentoGrupoCronogramaMensal));
			// Pesquisa Faturamento Grupo Cronograma Mensal
			faturamentoGrupoCronogramaMensais = fachada.pesquisar(filtroFaturamentoGrupoCronogramaMensal,
							FaturamentoGrupoCronogramaMensal.class.getName());
			if(!faturamentoGrupoCronogramaMensais.isEmpty()){
				// Pesquisa Faturamento Atividade Cronograma do Faturamento
				// Grupo Cronograma Mensal
				filtroFaturamentoAtividadeCronograma.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ID,
								idFaturamentoGrupoCronogramaMensal));

				filtroFaturamentoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("faturamentoAtividade");

				faturamentoAtividadeCronogramas = fachada.pesquisar(filtroFaturamentoAtividadeCronograma,
								FaturamentoAtividadeCronograma.class.getName());
				if(!faturamentoAtividadeCronogramas.isEmpty()){
					FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal = (FaturamentoGrupoCronogramaMensal) faturamentoGrupoCronogramaMensais
									.iterator().next();
					// Seta os valores no form
					faturamentoActionForm.setIdFaturamentoGrupoCronogramaMensal(idFaturamentoGrupoCronogramaMensal);
					faturamentoActionForm.setIdGrupoFaturamento(faturamentoGrupoCronogramaMensal.getFaturamentoGrupo().getId().toString());
					faturamentoActionForm.setMesAno(faturamentoGrupoCronogramaMensal.getMesAno());
					sessao.setAttribute("mesAno", faturamentoGrupoCronogramaMensal.getMesAno());

				}
			}

		}
		// Pega as atividade q ainda nao estao associadas aum faturamento
		// cronograma atividade
		FiltroFaturamentoAtividade filtroFaturamentoAtividade = new FiltroFaturamentoAtividade();
		filtroFaturamentoAtividade.adicionarParametro(new ParametroSimples(FiltroFaturamentoAtividade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroFaturamentoAtividade.setCampoOrderBy(FiltroFaturamentoAtividade.ORDEM_REALIZACAO);
		Iterator iteratorFaturamentoAtividadeCronogramas = faturamentoAtividadeCronogramas.iterator();
		FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = null;
		while(iteratorFaturamentoAtividadeCronogramas.hasNext()){
			faturamentoAtividadeCronograma = (FaturamentoAtividadeCronograma) iteratorFaturamentoAtividadeCronogramas.next();
			filtroFaturamentoAtividade.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroFaturamentoAtividade.ID,
							faturamentoAtividadeCronograma.getFaturamentoAtividade().getId()));
		}
		// pesquisa as atividades q ainda nao fazem parte do cronograma
		Collection faturamentoAtividades = fachada.pesquisar(filtroFaturamentoAtividade, FaturamentoAtividade.class.getName());
		// intera a colecao montando objetos do tipo FaturamentoAtividadeCronograma
		// e os coloca em uma coleção nova
		Iterator iteratorFaturamentoAtividades = faturamentoAtividades.iterator();
		Collection colecaoFaturamentoAtividadeCronogramaNovo = new ArrayList();
		FaturamentoAtividadeCronograma faturamentoAtividadeCronogramaNovo = null;
		while(iteratorFaturamentoAtividades.hasNext()){
			faturamentoAtividadeCronogramaNovo = new FaturamentoAtividadeCronograma();

			faturamentoAtividadeCronogramaNovo.setFaturamentoAtividade((FaturamentoAtividade) iteratorFaturamentoAtividades.next());

			colecaoFaturamentoAtividadeCronogramaNovo.add(faturamentoAtividadeCronogramaNovo);
		}
		// junção das coleções para ordenação
		colecaoFaturamentoAtividadeCronogramaNovo.addAll(faturamentoAtividadeCronogramas);
		// ordenação através do sort
		Collections.sort((List) colecaoFaturamentoAtividadeCronogramaNovo, new Comparator() {

			public int compare(Object a, Object b){

				Integer faturamentoAtividadeCronograma1 = new Integer(((FaturamentoAtividadeCronograma) a).getFaturamentoAtividade()
								.getOrdemRealizacao());
				Integer faturamentoAtividadeCronograma2 = new Integer(((FaturamentoAtividadeCronograma) b).getFaturamentoAtividade()
								.getOrdemRealizacao());

				return faturamentoAtividadeCronograma1.compareTo(faturamentoAtividadeCronograma2);
			}
		});

		// Joga os faturamentos atividades na sessao para carregar
		// os text q sao dinamicos
		// sessao.setAttribute("faturamentoAtividadeCronogramas",
		// faturamentoAtividadeCronogramas);
		//        
		// sessao.setAttribute("faturamentoAtividades", faturamentoAtividades);
		// --------
		sessao.setAttribute("colecaoFaturamentoAtividadeCronograma", colecaoFaturamentoAtividadeCronogramaNovo);

		return retorno;

	}

}