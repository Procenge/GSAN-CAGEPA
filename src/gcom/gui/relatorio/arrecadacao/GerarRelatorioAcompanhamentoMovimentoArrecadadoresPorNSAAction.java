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

package gcom.gui.relatorio.arrecadacao;

import gcom.arrecadacao.bean.MovimentoArrecadadoresPorNSAHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioAcompanhamentoMovimentoArrecadadoresPorNSA;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioAcompanhamentoMovimentoArrecadadoresPorNSAAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		Integer codigoFormaArrecadacao = null;
		String idConcessionaria = null;
		idConcessionaria = (String) httpServletRequest.getAttribute("idConcessionaria");

		if(httpServletRequest.getAttribute("idFormaArrecadacao") != null
						&& !httpServletRequest.getAttribute("idFormaArrecadacao").equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			codigoFormaArrecadacao = new Integer((String) httpServletRequest.getAttribute("idFormaArrecadacao"));

		}

		// AcompanharMovimentoArrecadadoresActionForm acompanharMovimentoArrecadadoresActionForm =
		// (AcompanharMovimentoArrecadadoresActionForm)
		// actionForm;

		// cria uma instância da classe do relatório
		RelatorioAcompanhamentoMovimentoArrecadadoresPorNSA relatorio = new RelatorioAcompanhamentoMovimentoArrecadadoresPorNSA(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<Integer> colecaoIdsMovimentoArrecadador = (Collection) sessao.getAttribute("colecaoMovimentoArrecadador");
		Collection<MovimentoArrecadadoresPorNSAHelper> colecaoMovimentoArrecadadoresPorNSA = null;

		if(colecaoIdsMovimentoArrecadador != null && !colecaoIdsMovimentoArrecadador.isEmpty()){
			colecaoMovimentoArrecadadoresPorNSA = new ArrayList<MovimentoArrecadadoresPorNSAHelper>();
			if(colecaoIdsMovimentoArrecadador.size() > 1000){
				Collection colecaoAuxiliar = new ArrayList<Integer>();



				for(Integer id : colecaoIdsMovimentoArrecadador){
					colecaoAuxiliar.add(id);

					if(colecaoAuxiliar.size() == 1000){
						colecaoMovimentoArrecadadoresPorNSA.addAll(fachada.gerarMovimentoArrecadadoresNSA(colecaoAuxiliar,
										codigoFormaArrecadacao, idConcessionaria));
						colecaoAuxiliar.clear();
					}
				}
				if(!Util.isVazioOrNulo(colecaoAuxiliar)){
					colecaoMovimentoArrecadadoresPorNSA.addAll(fachada.gerarMovimentoArrecadadoresNSA(colecaoAuxiliar,
									codigoFormaArrecadacao, idConcessionaria));

				}
			}else{
				colecaoMovimentoArrecadadoresPorNSA.addAll(fachada.gerarMovimentoArrecadadoresNSA(colecaoIdsMovimentoArrecadador,
								codigoFormaArrecadacao, idConcessionaria));
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		if(colecaoMovimentoArrecadadoresPorNSA != null && !colecaoMovimentoArrecadadoresPorNSA.isEmpty()){
			relatorio.addParametro("colecaoMovimentoArrecadadoresPorNSA", colecaoMovimentoArrecadadoresPorNSA);
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}
		if(idConcessionaria == null){
			relatorio.addParametro("indConcessionaria", new Boolean(false));
		}else{
			relatorio.addParametro("indConcessionaria", new Boolean(true));
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		/*
		 * // Inicio da parte que vai mandar os parametros para o relatório
		 * String mesAnoReferencia =
		 * acompanharMovimentoArrecadadoresActionForm.getMesAnoReferencia();
		 * String idArrecadador = acompanharMovimentoArrecadadoresActionForm.getIdArrecadador();
		 * Arrecadador arrecadador = null;
		 * if (idArrecadador != null && !idArrecadador.equals("")) {
		 * FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
		 * filtroArrecadador.adicionarParametro(new ParametroSimples(
		 * FiltroArrecadador.ID, idArrecadador));
		 * filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
		 * Collection colecaoArrecadadores = fachada.pesquisar(filtroArrecadador,
		 * Arrecadador.class.getName());
		 * if (colecaoArrecadadores != null && !colecaoArrecadadores.isEmpty()) {
		 * // O arrecadador foi encontrado
		 * arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadadores);
		 * } else {
		 * throw new ActionServletException(
		 * "atencao.pesquisa_inexistente", null, "Arrecadador");
		 * }
		 * } else {
		 * arrecadador = new Arrecadador();
		 * }
		 * String idArrecadacaoForma =
		 * acompanharMovimentoArrecadadoresActionForm.getIdFormaArrecadacao();
		 * ArrecadacaoForma arrecadacaoForma = null;
		 * if (idArrecadacaoForma != null
		 * && !idArrecadacaoForma.equals("")
		 * && !idArrecadacaoForma.equals(""
		 * + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
		 * FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();
		 * filtroArrecadacaoForma.adicionarParametro(new ParametroSimples(
		 * FiltroArrecadacaoForma.CODIGO, idArrecadacaoForma));
		 * Collection colecaoArrecadacaoForma = fachada.pesquisar(filtroArrecadacaoForma,
		 * ArrecadacaoForma.class.getName());
		 * if (colecaoArrecadacaoForma != null && !colecaoArrecadacaoForma.isEmpty()) {
		 * // O arrecadador foi encontrado
		 * arrecadacaoForma = (ArrecadacaoForma)
		 * Util.retonarObjetoDeColecao(colecaoArrecadacaoForma);
		 * } else {
		 * throw new ActionServletException(
		 * "atencao.pesquisa_inexistente", null, "Forma de Arrecadação");
		 * }
		 * } else {
		 * arrecadacaoForma = new ArrecadacaoForma();
		 * }
		 * fachada.gerarResumoAcompanhamentoMovimentoArrecadadores((Usuario)(httpServletRequest.
		 * getSession(false)).getAttribute("usuarioLogado"), mesAnoReferencia, arrecadador,
		 * arrecadacaoForma);
		 * // montando página de sucesso
		 * montarPaginaSucesso(httpServletRequest,
		 * "Movimento Arrecadadores Enviado para Processamento", "Voltar",
		 * "/exibirAcompanharMovimentoArrecadadoresAction.do");
		 */

		// devolve o mapeamento contido na variável retorno
		try{

			retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		}catch(SistemaException ex){

			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){

			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na variável retorno
		return retorno;

	}

}
