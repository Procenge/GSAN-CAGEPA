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

package gcom.gui.relatorio.atendimentopublico;

import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.ordemservico.GeradorRelatorioOrdemServico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela exibição do relatório de ordem de serviço.
 * 
 * @author Sávio Luiz
 * @created 11 de Julho de 2005
 * @author Virgínia Melo
 * @date 12/06/2009
 *       Alteração para a versão 1.0.5 - Impressão de vários tipos de OS.
 */
public class GerarRelatorioOrdemServicoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws Exception{

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		String idOrdemServico = null;
		String telaOSSeletiva = "";

		if(Util.isVazioOuBranco(httpServletRequest.getParameter("idsOS"))){
			throw new ActionServletException("atencao.required", null, "alguma(s) Ordem(s) de Serviço(s)");
		}else{
			idOrdemServico = httpServletRequest.getParameter("idsOS");
		}

		String[] idsOS = idOrdemServico.split("\\$");

		Collection<Integer> idsOrdemServico = new ArrayList<Integer>();

		for(String idOS : idsOS){
			idsOrdemServico.add(Integer.valueOf(idOS));
		}

		// ******************************************************************************************
		TarefaRelatorio tarefaRelatorio = new GeradorRelatorioOrdemServico((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"), ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_COBRANCA);
		tarefaRelatorio.addParametro("idsOrdemServico", idsOrdemServico);
		tarefaRelatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_PDF));

		retorno = processarExibicaoRelatorioOrdemServico(tarefaRelatorio, TarefaRelatorio.TIPO_PDF, httpServletRequest,
						httpServletResponse, actionMapping);
		// ******************************************************************************************

		if(retorno == null){
			return retorno;
		}else if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			// Monta a página de sucesso
			super.montarPaginaSucesso(httpServletRequest, "Ordens de Serviço Seletivas geradas com sucesso.", "", "");
		}

		return retorno;
	}
}
