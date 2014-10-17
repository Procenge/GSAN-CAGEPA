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
 * Esta classe re�ne fun��es que manipulam recursos web e configura��es web.
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
