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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroAreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroPiscinaVolumeFaixa;
import gcom.cadastro.imovel.FiltroRendaFamiliarFaixa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class InserirImovelConclusaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Instanciando o ActionForm de InserirImovelLocalidadeActionForm
		DynaValidatorForm inserirImovelConclusaoActionForm = (DynaValidatorForm) sessao.getAttribute("InserirImovelActionForm");

		// Cria variaveis
		String iptu = (String) inserirImovelConclusaoActionForm.get("numeroIptu");
		Integer idMunicipio = null;
		String idSetorComercial = (String) inserirImovelConclusaoActionForm.get("idSetorComercial");
		String contratoCelpe = (String) inserirImovelConclusaoActionForm.get("numeroContratoCelpe");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		FiltroRendaFamiliarFaixa filtroRendaFamiliar = new FiltroRendaFamiliarFaixa();

		// if (areaConstruida != null && !areaConstruida.trim().equalsIgnoreCase("")) {
		/*
		 * filtroAreaConstruida.adicionarParametro(new
		 * MaiorQue(FiltroAreaConstruidaFaixa.MENOR_FAIXA
		 * ,Util.formatarMoedaRealparaBigDecimal(areaConstruida)));
		 * filtroAreaConstruida.adicionarParametro(new MenorQue(
		 * FiltroAreaConstruidaFaixa.MAIOR_FAIXA
		 * ,Util.formatarMoedaRealparaBigDecimal(areaConstruida)));
		 * Collection areaConstruidas = fachada.pesquisar(filtroAreaConstruida,
		 * AreaConstruidaFaixa.class.getName());
		 * if (areaConstruidas != null && !areaConstruidas.isEmpty()) {
		 * inserirImovelCaracteristicasActionForm.set("faixaAreaConstruida",((AreaConstruidaFaixa)
		 * ((List) areaConstruidas).get(0)).getId());
		 * }
		 */
		// }
		if(iptu != null && !iptu.trim().equalsIgnoreCase("")){
			// Cria Filtros
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			FiltroImovel filtroImovel = new FiltroImovel();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, new Integer(
							idSetorComercial)));
			Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
			if(!setorComerciais.isEmpty()){
				SetorComercial setorComercial = (SetorComercial) setorComerciais.iterator().next();
				idMunicipio = setorComercial.getMunicipio().getId();

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO_ID, idMunicipio));
				// filtroImovel.adicionarParametro(new
				// ParametroSimples(FiltroImovel.SETOR_COMERCIAL_ID, new
				// Integer(idSetorComercial)));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.IPTU, iptu));

				Collection iptus = fachada.pesquisar(filtroImovel, Imovel.class.getName());
				if(!iptus.isEmpty()){
					Imovel imovelIptu = (Imovel) iptus.iterator().next();
					throw new ActionServletException("atencao.imovel.iptu_jacadastrado", null, imovelIptu.getId().toString());
				}
			}
		}

		if(contratoCelpe != null && !contratoCelpe.trim().equalsIgnoreCase("")){
			FiltroImovel filtroImovelCelpe = new FiltroImovel();
			filtroImovelCelpe.adicionarParametro(new ParametroSimples(FiltroImovel.NUMERO_CELPE, contratoCelpe));

			Collection imoveisCelpe = fachada.pesquisar(filtroImovelCelpe, Imovel.class.getName());
			if(!imoveisCelpe.isEmpty()){
				Imovel imovelCelpe = (Imovel) imoveisCelpe.iterator().next();
				throw new ActionServletException("atencao.imovel.numero_celpe_jacadastrado", null, imovelCelpe.getId().toString());
			}
		}

		// Valida n�mero contrato celpe
		if(contratoCelpe != null && contratoCelpe.trim().equalsIgnoreCase("")){
			// fazer valida��o
		}

		return retorno;
	}
}