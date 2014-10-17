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
 * GSANPCG
 * Virg�nia Melo
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

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAtividade;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaAtividade;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por exibir a p�gina Inserir Cronograma Cobran�a.
 * 
 * @author Fernanda Paiva
 *         Altera��es realizadas para a v0.05
 * @author Virg�nia Melo
 * @date 09/09/2008
 *       Desfazer altera��es para a v0.06
 * @author Virg�nia Melo
 * @date 03/11/2008
 */
public class ExibirInserirCronogramaCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta a a��o de retorno
		ActionForward retorno = actionMapping.findForward("inserirCronogramaCobranca");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);

		Collection gruposCobranca = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
		sessao.setAttribute("gruposCobranca", gruposCobranca);

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoPredecessora");
		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_CRONOGRAMA,
						CobrancaAcao.INDICADOR_CRONOGRAMA_ATIVO));
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.ORDEM_REALIZACAO);

		Collection acoesCobranca = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
		sessao.setAttribute("acoesCobranca", acoesCobranca);

		FiltroCobrancaAtividade filtroCobrancaAtividade = new FiltroCobrancaAtividade();
		filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividadePredecessora");
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_CRONOGRAMA,
						CobrancaAtividade.ATIVO_CRONOGRAMA));
		filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.ORDEM_REALIZACAO);

		Collection atividadesCobranca = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
		sessao.setAttribute("atividadesCobranca", atividadesCobranca);

		filtroCobrancaAtividade = new FiltroCobrancaAtividade();
		filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividadePredecessora");
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_CRONOGRAMA,
						CobrancaAtividade.ATIVO_CRONOGRAMA));
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_OBRIGATORIEDADE,
						CobrancaAtividade.INDICADOR_OBRIGATORIEDADE_ATIVO));
		filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.ORDEM_REALIZACAO);

		Collection atividadesCobrancaObrigatoriedadeAtivo = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
		sessao.setAttribute("atividadesCobrancaObrigatoriedadeAtivo", atividadesCobrancaObrigatoriedadeAtivo);

		return retorno;
	}
}
