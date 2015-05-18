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

package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.FiltroCep;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usuário os CEPs que serão retornados
 * a partir dos parâmetros que foram passados pelo filtro
 * 
 * @author Raphael Rossiter
 * @date 03/05/2006
 */
public class PesquisarSelecionarCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirSelecionarCep");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		SelecionarCepActionForm selecionarCepActionForm = (SelecionarCepActionForm) actionForm;

		String nomeMunicipio = selecionarCepActionForm.getNomeMunicipio();
		String faixaInicial = selecionarCepActionForm.getFaixaInicial();
		String codigoLado = selecionarCepActionForm.getCodigoLado();
		String faixaInicialFormatada = null;
		Integer controlePaginacao = 4;

		if(faixaInicial != null && !faixaInicial.equals("")){

			faixaInicialFormatada = retornarFaixa(faixaInicial).toString();

		}

		FiltroCep filtroCep = new FiltroCep();
		filtroCep.setConsultaSemLimites(true);

		Collection colecaoCep = null;

		colecaoCep = fachada.pesquisarCepFiltro(selecionarCepActionForm.getNomeLogradouro(), codigoLado, faixaInicialFormatada,
						controlePaginacao, nomeMunicipio);

		if(colecaoCep == null || colecaoCep.isEmpty()){

			/*
			 * Nenhum CEP cadastrado de acordo com os parâmetros passados
			 * Será disponibilizado para o usuário a opção de CEP Padrão
			 */
			httpServletRequest.setAttribute("cepPadrao", "OK");
			sessao.removeAttribute("colecaoCepSelecionados");

		}else{
			sessao.setAttribute("colecaoCepSelecionados", colecaoCep);
		}

		httpServletRequest.setAttribute("nomeCampo", "botaoSelecionar");

		return retorno;
	}

	private static Integer retornarFaixa(String faixa){

		Integer saidaFaixaInt = null;

		String saida = faixa.replaceAll("\\D", "");

		if(saida != null && !saida.equals("")){

			saidaFaixaInt = Integer.parseInt(saida);
		}



		return saidaFaixaInt;

	}

}
