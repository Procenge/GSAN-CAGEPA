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

package gcom.gui.relatorio.cadastro.endereco;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

/**
 * [UC0XXX] Gerar Relatório Logradouro Geral
 * 
 * @author Anderson Italo
 * @date 26/01/2011
 */
public class GerarRelatorioLogradouroGeralAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;
		GerarRelatorioLogradouroGeralActionForm formulario = (GerarRelatorioLogradouroGeralActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		if(formulario.getLocalidadeInicial() != null && formulario.getLocalidadeFinal() != null
						&& !formulario.getLocalidadeInicial().equals("") && !formulario.getLocalidadeFinal().equals("")){

			// [FS0001] – Verificar existência da localidade
			Localidade localidadeInicial = null;
			Localidade localidadeFinal = null;
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(formulario.getLocalidadeInicial())));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Inicial");
			}else{
				localidadeInicial = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
			}

			filtroLocalidade = null;
			colecaoLocalidade = null;

			filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(formulario.getLocalidadeFinal())));

			colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Final");
			}else{
				localidadeFinal = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
			}

			// [FS0002] – Verificar Localidade final menor que Localidade Inicial
			if(localidadeInicial.getId().intValue() > localidadeFinal.getId().intValue()){
				throw new ActionServletException("atencao.localidafinal.menor.localidadeinicial", null, "");
			}

			// 3. O sistema apresenta a quantidade de logradouros selecionados e a quantidade de
			// páginas que
			// serão geradas e solicita confirmação da geração do relatório em formato PDF.
			// faz a consulta da quantidade de logradouros e quantidade de páginas

			colecaoLocalidade = null;
			colecaoLocalidade = fachada.pesquisarLocalidadePorIdIntervalo(localidadeInicial.getId(), localidadeFinal.getId());

			int quantidadeLogradouros = 0;
			for(Iterator iterator = colecaoLocalidade.iterator(); iterator.hasNext();){

				Object[] localidade = (Object[]) iterator.next();
				// obtém todos os logradouros do município associado a localidade
				quantidadeLogradouros += fachada.calcularTotalLogradourosPorMunicipio(new Integer(localidade[2].toString()));
			}

			formulario.setLocalidadeInicial(localidadeInicial.getId().toString());
			formulario.setLocalidadeFinal(localidadeFinal.getId().toString());
			formulario.setNomeLocalidadeInicial(localidadeInicial.getDescricao());
			formulario.setNomeLocalidadeFinal(localidadeFinal.getDescricao());
			formulario.setTotalRegistrosRelatorio(String.valueOf(quantidadeLogradouros));
			httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/processarGerarRelatorioLogradouroGeralAction.do");

			return montarPaginaConfirmacao("atencao.confirmacao.relatoriologradouro", httpServletRequest, actionMapping, String
							.valueOf(quantidadeLogradouros));

		}

		return retorno;
	}
}
