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

package gcom.gui;

import gcom.cadastro.DbVersaoImplementada;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CarregarParametrosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaLogin");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Instacia a variavel de aplicacao tituloPagina com o valor cadastrado
		// em sistema parametro.
		if(servlet.getServletContext().getAttribute("tituloPagina") == null
						|| servlet.getServletContext().getAttribute("tituloPagina").equals("")){

			// Recupera o objeto que contém os parâmetros do sistema
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			servlet.getServletContext().setAttribute("tituloPagina", sistemaParametro.getTituloPagina());
			servlet.getServletContext().setAttribute("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());

			String pastaEmpresa = sistemaParametro.getNomeAbreviadoEmpresa() + "/";

			String logoMarca = pastaEmpresa + sistemaParametro.getImagemLogomarca();
			String logoCabecalho = pastaEmpresa + sistemaParametro.getImagemCabecalho();
			String imagemPrincipal = pastaEmpresa + sistemaParametro.getImagemPrincipal();
			String imagemSecundaria = pastaEmpresa + sistemaParametro.getImagemSecundaria();

			servlet.getServletContext().setAttribute("logoMarca", logoMarca);
			servlet.getServletContext().setAttribute("logoCabecalho", logoCabecalho);
			servlet.getServletContext().setAttribute("imagemPrincipal", imagemPrincipal);
			servlet.getServletContext().setAttribute("imagemSecundaria", imagemSecundaria);

			String realPath = servlet.getServletContext().getRealPath("imagens/" + logoMarca);
			if(!new File(realPath).exists()){
				servlet.getServletContext().setAttribute("logoMarca", "GSAN/logoCabEsq.gif");
			}
			realPath = servlet.getServletContext().getRealPath("imagens/" + logoCabecalho);
			if(!new File(realPath).exists()){
				servlet.getServletContext().setAttribute("logoCabecalho", "GSAN/logoCabDir.gif");
			}
			realPath = servlet.getServletContext().getRealPath("imagens/" + imagemPrincipal);
			if(!new File(realPath).exists()){
				servlet.getServletContext().setAttribute("imagemPrincipal", "GSAN/logoPrinc.gif");
			}
			realPath = servlet.getServletContext().getRealPath("imagens/" + imagemSecundaria);
			if(!new File(realPath).exists()){
				servlet.getServletContext().setAttribute("imagemSecundaria", "GSAN/logoSecun.gif");
			}

		}

		if(servlet.getServletContext().getAttribute("rodapePagina") == null
						|| servlet.getServletContext().getAttribute("rodapePagina").equals("")){

			// Recupera o objeto que contém as datas de Implementacao e
			// alteracao do Banco

			DbVersaoImplementada dbVersaoImplementada = fachada.pesquisarDbVersaoImplementada();

			if(dbVersaoImplementada != null && dbVersaoImplementada.getultimaAlteracao() != null){
				String dataUltimaAlteracao = Util.formatarDataComHora(dbVersaoImplementada.getultimaAlteracao());

				dataUltimaAlteracao = dataUltimaAlteracao.substring(0, 10) + " - " + dataUltimaAlteracao.substring(11, 19);

				// servlet.getServletContext().setAttribute("dataImplementacao",
				// dataImplementacao);
				servlet.getServletContext().setAttribute("dataUltimaAlteracao", dataUltimaAlteracao);
			}
		}

		return retorno;

	}

}
