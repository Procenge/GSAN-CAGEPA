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

package gcom.gui.cobranca;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.FiltroResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroGrupo;
import gcom.seguranca.acesso.Grupo;
import gcom.util.ConstantesSistema;
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
 * Permite filtrar resolu��es de diretoria [UC0219] Filtrar Resolu��o de
 * Diretoria
 * 
 * @author Rafael Corr�a
 * @since 31/03/2006
 */
public class ExibirFiltrarResolucaoDiretoriaAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		FiltrarResolucaoDiretoriaActionForm filtrarResolucaoDiretoriaActionForm = (FiltrarResolucaoDiretoriaActionForm) actionForm;

		filtrarResolucaoDiretoriaActionForm.setAtualizar("1");

		// Vari�vel que indica quando o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Pesquisa os Termos de Confiss�o de Divida
		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();
		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoResolucaoDiretoriaLayout = Fachada.getInstancia().pesquisar(filtroResolucaoDiretoriaLayout,
						ResolucaoDiretoriaLayout.class.getName());
		sessao.setAttribute("colecaoResolucaoDiretoriaLayout", colecaoResolucaoDiretoriaLayout);

		// Pesquisa os grupos
		FiltroGrupo filtroGrupo = new FiltroGrupo();
		filtroGrupo.adicionarParametro(new ParametroSimples(FiltroGrupo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoGrupo = Fachada.getInstancia().pesquisar(filtroGrupo, Grupo.class.getName());
		sessao.setAttribute("colecaoGrupo", colecaoGrupo);

		sessao.removeAttribute("filtroResolucaoDiretoria");
		if(httpServletRequest.getParameter("desfazer") != null){
			filtrarResolucaoDiretoriaActionForm.reset(actionMapping, httpServletRequest);
		}

		if(httpServletRequest.getParameter("paginacao") != null){

			filtrarResolucaoDiretoriaActionForm.setNumero((String) sessao.getAttribute("numero"));
			filtrarResolucaoDiretoriaActionForm.setAssunto((String) sessao.getAttribute("assunto"));
			filtrarResolucaoDiretoriaActionForm.setDataInicio((String) sessao.getAttribute("dataInicio"));
			filtrarResolucaoDiretoriaActionForm.setDataFim((String) sessao.getAttribute("dataFim"));
			filtrarResolucaoDiretoriaActionForm.setGrupo((String) sessao.getAttribute("grupo"));
			filtrarResolucaoDiretoriaActionForm.setResolucaoDiretoriaLayout((String) sessao.getAttribute("resolucaoDiretoriaLayout"));
			filtrarResolucaoDiretoriaActionForm.setIndicadorUsoRDParcImovel((String) sessao.getAttribute("indicadorUsoRDParcImovel"));
			filtrarResolucaoDiretoriaActionForm.setIndicadorUsoRDUsuarios((String) sessao.getAttribute("indicadorUsoRDUsuarios"));
			filtrarResolucaoDiretoriaActionForm.setIndicadorUsoRDDebitoCobrar((String) sessao.getAttribute("indicadorUsoRDDebitoCobrar"));
			filtrarResolucaoDiretoriaActionForm.setIndicadorEmissaoAssuntoConta((String) sessao
							.getAttribute("indicadorEmissaoAssuntoConta"));
			filtrarResolucaoDiretoriaActionForm.setIndicadorTrataMediaAtualizacaoMonetaria((String) sessao
							.getAttribute("indicadorTrataMediaAtualizacaoMonetaria"));
			filtrarResolucaoDiretoriaActionForm.setIndicadorCobrarDescontosArrasto((String) sessao
							.getAttribute("indicadorCobrarDescontosArrasto"));
			filtrarResolucaoDiretoriaActionForm.setIndicadorArrasto((String) sessao.getAttribute("indicadorArrasto"));
			filtrarResolucaoDiretoriaActionForm.setIdLocalidade((String) sessao.getAttribute("idLocalidade"));
			filtrarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio((String) sessao
							.getAttribute("anoMesReferenciaDebitoInicio"));
			filtrarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim((String) sessao.getAttribute("anoMesReferenciaDebitoInicio"));
		}

		// Localidade
		if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1"))
						|| !Util.isVazioOuBranco(filtrarResolucaoDiretoriaActionForm.getIdLocalidade())){

			// [UC0023 - Pesquisar Localidade]
			this.pesquisarLocalidade(filtrarResolucaoDiretoriaActionForm, httpServletRequest);
		}

		if(filtrarResolucaoDiretoriaActionForm.getTipoPesquisa() == null
						|| filtrarResolucaoDiretoriaActionForm.getTipoPesquisa().equals("")){
			filtrarResolucaoDiretoriaActionForm.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
		}

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirFiltrarResolucaoDiretoria");

		return retorno;

	}

	private void pesquisarLocalidade(FiltrarResolucaoDiretoriaActionForm form, HttpServletRequest request){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getIdLocalidade())));

		Collection<Localidade> colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// [FS0008 � Verificar exist�ncia da localidade]
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setIdLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());
			request.setAttribute("idLocalidadeEncontrado", true);

		}else{

			form.setIdLocalidade("");
			form.setNomeLocalidade("Localidade Inexistente");
		}
	}

}
