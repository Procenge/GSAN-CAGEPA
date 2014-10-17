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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.RendaFamiliarFaixa;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class AtualizarImovelConclusaoAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author eduardo henrique
	 * @date 12/03/2009
	 *       Adicionado tratamento para Renda Familiar somente ser definido, caso tenha sido
	 *       escolhido no combo.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Instanciando o ActionForm de InserirImovelLocalidadeActionForm
		DynaValidatorForm inserirImovelConclusaoActionForm = (DynaValidatorForm) actionForm;

		// Cria variaveis
		String iptu = (String) inserirImovelConclusaoActionForm.get("numeroIptu");
		Integer idMunicipio = null;
		String idSetorComercial = (String) inserirImovelConclusaoActionForm.get("idSetorComercial");
		String contratoCelpe = (String) inserirImovelConclusaoActionForm.get("numeroContratoCelpe");
		String idRendaFamiliarFaixa = (String) inserirImovelConclusaoActionForm.get("faixaRendaFamiliar");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");

		if(imovel.getNumeroIptu() != null && !imovel.getNumeroIptu().toString().trim().equalsIgnoreCase(iptu)){
			if(iptu != null && !iptu.trim().equalsIgnoreCase("")){
				// Cria Filtros
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				FiltroImovel filtroImovel = new FiltroImovel();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, new Integer(idSetorComercial)));

				Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(!setorComerciais.isEmpty()){
					SetorComercial setorComercial = (SetorComercial) setorComerciais.iterator().next();

					idMunicipio = setorComercial.getMunicipio().getId();

					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO_ID, idMunicipio));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_ID, new Integer(idSetorComercial)));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.IPTU, iptu));

					Collection iptus = fachada.pesquisar(filtroImovel, Imovel.class.getName());

					if(!iptus.isEmpty()){
						throw new ActionServletException("atencao.imovel.iptu_jacadastrado");
					}
				}
			}
		}

		if(imovel.getNumeroCelpe() != null && !imovel.getNumeroCelpe().toString().trim().equalsIgnoreCase(contratoCelpe)){
			if(contratoCelpe != null && !contratoCelpe.trim().equalsIgnoreCase("")){

				FiltroImovel filtroImovelCelpe = new FiltroImovel();
				filtroImovelCelpe.adicionarParametro(new ParametroSimples(FiltroImovel.NUMERO_CELPE, contratoCelpe));
				Collection imoveisCelpe = fachada.pesquisar(filtroImovelCelpe, Imovel.class.getName());

				if(!imoveisCelpe.isEmpty()){
					Imovel imovelCelpe = (Imovel) imoveisCelpe.iterator().next();
					throw new ActionServletException("atencao.imovel.numero_celpe_jacadastrado", null, imovelCelpe.getId().toString());
				}
			}
		}

		// Valida número contrato celpe
		if(contratoCelpe != null && contratoCelpe.trim().equalsIgnoreCase("")){
			// fazer validação
		}
		imovel.setRendaFamiliarFaixa(null);
		if(idRendaFamiliarFaixa != null
						&& !idRendaFamiliarFaixa.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			RendaFamiliarFaixa rendaFamiliarFaixa = new RendaFamiliarFaixa();
			rendaFamiliarFaixa.setId(new Integer(idRendaFamiliarFaixa));
			imovel.setRendaFamiliarFaixa(rendaFamiliarFaixa);
		}

		return retorno;
	}
}
