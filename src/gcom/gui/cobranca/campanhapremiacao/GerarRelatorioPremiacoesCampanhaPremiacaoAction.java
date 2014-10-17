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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.cobranca.campanhapremiacao.CampanhaPremiacao;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremiacao;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar Relat�rio Premia��es da Campanha de Premia��o
 * 
 * @author Hiroshi Gon�alves
 * @date 09/10/2013
 */

public class GerarRelatorioPremiacoesCampanhaPremiacaoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		Collection<CampanhaPremiacao> colCampanhaPremiacao = (Collection<CampanhaPremiacao>) sessao.getAttribute("colCampanhaPremiacao");

		if(colCampanhaPremiacao == null || colCampanhaPremiacao.isEmpty()){

			String idCampanhaSorteio = (String) httpServletRequest.getParameter("idCampanhaSorteio");

			// Consulta as campanhas premia��es sorteadas
			FiltroCampanhaPremiacao filtro = new FiltroCampanhaPremiacao();
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_PREMIO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_SORTEIO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_CADASTRO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.USUARIO_ENTREGA_PREMIO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.USUARIO_CANCELAMENTO_PREMIACAO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_CADASTRO_ORGAO_EXPEDIDOR);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_CADASTRO_UF);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_PREMIACAO_MOT_CANCEL);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_GERENCIA_REGIONAL);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_UNIDADE_NEGOCIO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_ELO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_LOCALIDADE);

			filtro.adicionarParametro(new ParametroSimples(FiltroCampanhaPremiacao.CAMPANHA_SORTEIO_ID, idCampanhaSorteio));

			colCampanhaPremiacao = fachada.pesquisar(filtro, CampanhaPremiacao.class.getName());
		}

		RelatorioPremiacoesCampanhaPremiacao relatorio = new RelatorioPremiacoesCampanhaPremiacao(usuarioLogado);
		

		int tipoRelatorio = TarefaRelatorio.TIPO_PDF;

		relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);
		relatorio.addParametro("colCampanhaPremiacao", colCampanhaPremiacao);
		try{
			retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}

		return retorno;
	}

}
