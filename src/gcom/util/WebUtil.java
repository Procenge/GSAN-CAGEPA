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

package gcom.util;

import gcom.gui.StatusWizard;
import gcom.gui.WizardAction;
import gcom.util.exception.GcomSystemException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.config.ConfigHelper;
import org.apache.struts.config.ConfigHelperInterface;

/**
 * Esta classe reúne funções que manipulam recursos web e configurações web.
 * 
 * @author Fabio Marconi
 */
public class WebUtil {

	public static final String IMAGEM_CAB_DIREITA_DEFAULT = "./imagens/GSAN/logoCabDirRel.gif";

	public static final String IMAGEM_CAB_ESQUERDA_DEFAULT = "./imagens/GSAN/logoCabEsqRel.gif";

	public static final String IMAGEM_PRINCIPAL_DEFAULT = "./imagens/GSAN/logoPrinc.gif";

	public static final String IMAGEM_SECUNDARIA_DEFAULT = "./imagens/GSAN/logoSecun.gif";

	public static final String IMAGEM_RELATORIO_DEFAULT = "./imagens/GSAN/logoRel.gif";

	public static final String IMAGEM_REL_CONTA_DEFAULT = "./imagens/GSAN/logoRel.gif";

	private static WebUtil instancia = new WebUtil();

	public static WebUtil getInstancia(){

		return instancia;
	}

	public String retornarUrlDestinoWizard(ServletContext context, String enderecoURL, String destino, HttpServletRequest requestPagina,
					HttpServletResponse response){

		int idxDotDo = enderecoURL.indexOf(".do");
		if(idxDotDo != -1){
			enderecoURL = enderecoURL.substring(0, idxDotDo);
		}
		if(!enderecoURL.startsWith("/")){
			enderecoURL = "/" + enderecoURL;
		}
		ConfigHelperInterface config = new ConfigHelper(context, requestPagina, response);
		String className = config.getActionMapping(enderecoURL).getType();
		WizardAction wizardAction = null;
		config = null;
		try{
			wizardAction = (WizardAction) Class.forName(className).newInstance();
		}catch(Exception e){
			throw new GcomSystemException(e);
		}
		String nomeStatusWizard = wizardAction.getNomeStatusWizard();
		StatusWizard statusWizard = (StatusWizard) requestPagina.getSession().getAttribute(nomeStatusWizard);
		StatusWizard.StatusWizardItem itemStatusWizard = statusWizard.retornarItemWizard(Integer.parseInt(destino));
		enderecoURL = itemStatusWizard.getCaminhoActionFinal();
		if(StringUtils.isEmpty(enderecoURL)){
			enderecoURL = itemStatusWizard.getCaminhoActionInicial();
		}

		return enderecoURL;
	}

}
