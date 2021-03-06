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

package gcom.gui.relatorio.arrecadacao;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pr�-processamento da primeira p�gina de [UC0345] Gerar Relat�rio de Resumo do
 * Arrecadacao
 * 
 * @author Vivianne Sousa
 */
public class ExibirGerarRelatorioResumoArrecadacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioResumoArrecadacao");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		Collection<GerenciaRegional> gerenciasRegionais = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		sessao.setAttribute("colecaoGerenciaRegional", gerenciasRegionais);

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);
		Collection<UnidadeNegocio> colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);

		// Pega os codigos que o usuario digitou para a pesquisa direta de
		// localidade
		String codigoLocalidade = httpServletRequest.getParameter("codigoLocalidade");

		if(codigoLocalidade != null && !codigoLocalidade.trim().equals("")){
			pesquisarLocalidade(codigoLocalidade, httpServletRequest);

			// Setor Comercial
			String codigoSetorComercial = httpServletRequest.getParameter("codigoSetorComercial");

			if(!Util.isVazioOuBranco(codigoSetorComercial)){
				pesquisarSetorComercial(codigoSetorComercial, codigoLocalidade, httpServletRequest);
			}
		}

		// if (sistemaParametro.getCodigoEmpresaFebraban().
		// equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAERN)){
		//			
		// httpServletRequest.setAttribute("unidadeNegocio",true);
		// }else{
		// httpServletRequest.setAttribute("unidadeNegocio",false);
		// }

		return retorno;
	}

	/**
	 * Pesquisa uma localidade informada e prepara os dados para exibi��o na
	 * tela
	 */
	private void pesquisarLocalidade(String idLocalidade, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		// Pesquisa a localidade na base
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

		Collection<Localidade> localidadePesquisada = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		// Se nenhuma localidade for encontrada a mensagem � enviada para a
		// p�gina
		if(localidadePesquisada == null || localidadePesquisada.isEmpty()){
			// [FS0001 - Verificar exist�ncia de dados]
			httpServletRequest.setAttribute("codigoLocalidade", "");
			httpServletRequest.setAttribute("descricaoLocalidade", "Localidade Inexistente".toUpperCase());
		}

		// obtem a localidade pesquisada
		if(localidadePesquisada != null && !localidadePesquisada.isEmpty()){
			Localidade localidade = localidadePesquisada.iterator().next();
			// Manda a Localidade pelo request
			httpServletRequest.setAttribute("codigoLocalidade", idLocalidade);
			httpServletRequest.setAttribute("descricaoLocalidade", localidade.getDescricao());
		}

	}

	private void pesquisarSetorComercial(String codigoSetorComercial, String idLocalidade, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));

		Collection<SetorComercial> setorComercialPesquisado = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(Util.isVazioOrNulo(setorComercialPesquisado)){
			httpServletRequest.setAttribute("codigoSetorComercial", "");
			httpServletRequest.setAttribute("descricaoSetorComercial", "Setor Comercial Inexistente".toUpperCase());
		}else{
			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(setorComercialPesquisado);

			httpServletRequest.setAttribute("codigoSetorComercial", codigoSetorComercial);
			httpServletRequest.setAttribute("descricaoSetorComercial", setorComercial.getDescricao());
		}
	}

}
