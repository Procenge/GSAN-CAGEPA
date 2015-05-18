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

import gcom.cadastro.imovel.FiltroImovelContaEnvio;
import gcom.cadastro.imovel.ImovelContaEnvio;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.FiltroNomeConta;
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
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirInserirImovelEnderecoAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirImovelEndereco");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltroImovelContaEnvio filtroImovelContaEnvio = new FiltroImovelContaEnvio();
		Collection colecaoImovelEnnvioConta = null;

		String removeEndereco = httpServletRequest.getParameter("removeEndereco");

		if(removeEndereco != null && !removeEndereco.equals("")){
			sessao.removeAttribute("colecaoEnderecos");
		}

		if(httpServletRequest.getAttribute("confirmou") != null){
			sessao.setAttribute("confirmou", "sim");
		}

		Collection colecaoEndereco = (Collection) sessao.getAttribute("colecaoEnderecos");

		DynaValidatorForm inserirImovelLocalidadeActionForm = (DynaValidatorForm) sessao.getAttribute("InserirImovelActionForm");

		String codigoSetorComercial = (String) inserirImovelLocalidadeActionForm.get("idSetorComercial");
		String idLocalidade = (String) inserirImovelLocalidadeActionForm.get("idLocalidade");

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorComercial.MUNICIPIO);

		// coloca parametro no filtro
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, new Integer(idLocalidade)));
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, new Integer(
						codigoSetorComercial)));

		filtroImovelContaEnvio
						.adicionarParametro(new ParametroSimples(FiltroNomeConta.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroImovelContaEnvio.setCampoOrderBy(FiltroImovelContaEnvio.DESCRICAO);

		colecaoImovelEnnvioConta = fachada.pesquisar(filtroImovelContaEnvio, ImovelContaEnvio.class.getName());

		if(!colecaoImovelEnnvioConta.isEmpty()){

			// Coloca a cole�ao da pesquisa na sessao
			sessao.setAttribute("colecaoImovelEnnvioConta", colecaoImovelEnnvioConta);

			/*
			 * Alterado por Raphael Rossiter em 10/09/2007 (Analista: Aryed Lins)
			 * OBJ: Marcar o indicador de emiss�o de extrato de faturamento para NAO EMITIR
			 */
			inserirImovelLocalidadeActionForm.set("extratoResponsavel", ConstantesSistema.NAO.toString());

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Imovel Conta Envio");
		}

		if(inserirImovelLocalidadeActionForm.get("indicadorEnvioCorreio") != null
						&& !inserirImovelLocalidadeActionForm.get("indicadorEnvioCorreio").equals("")
						&& inserirImovelLocalidadeActionForm.get("indicadorEnvioCorreio").equals(ConstantesSistema.SIM.toString())){

			inserirImovelLocalidadeActionForm.set("indicadorEnvioCorreio", ConstantesSistema.SIM.toString());
		}else{
			inserirImovelLocalidadeActionForm.set("indicadorEnvioCorreio", ConstantesSistema.NAO.toString());
		}

		// Obt�m a inst�ncia da Fachada
		// Fachada fachada = Fachada.getInstancia();

		// pesquisa
		// Collection setorComerciais = fachada.pesquisar(filtroSetorComercial,
		// SetorComercial.class.getName());
		//
		// if (colecaoEndereco != null && !colecaoEndereco.isEmpty()) {
		//
		// Imovel imovel = (Imovel) colecaoEndereco.iterator().next();
		//
		// System.out
		// .println("imovel.getLogradouroBairro().getLogradouro().getMunicipio().getId().toString()="
		// + imovel.getLogradouroBairro().getLogradouro()
		// .getMunicipio().getId().intValue());
		// System.out
		// .println("( ((SetorComercial) ((List) setorComerciais).get(0)).getMunicipio().getId()="
		// + (((SetorComercial) ((List) setorComerciais)
		// .get(0)).getMunicipio().getId().intValue()));
		// System.out
		// .println("boolean="
		// + (!(imovel.getLogradouroBairro().getLogradouro()
		// .getMunicipio().getId().intValue() == (((SetorComercial) ((List) setorComerciais)
		// .get(0)).getMunicipio().getId().intValue()))));
		//
		// if (imovel.getLogradouroBairro() != null
		// && imovel.getLogradouroBairro().getLogradouro() != null
		// && imovel.getLogradouroBairro().getLogradouro()
		// .getMunicipio() != null
		// && (!(imovel.getLogradouroBairro().getLogradouro()
		// .getMunicipio().getId().intValue() == (((SetorComercial) ((List) setorComerciais)
		// .get(0)).getMunicipio().getId().intValue())))) {
		//
		// Usuario usuario = (Usuario) sessao
		// .getAttribute("usuarioLogado");
		//
		// if (!fachada
		// .verificarPermissaoInserirImovelMunicipioLogradouroDiferenteSetor(usuario)) {
		//
		// throw new ActionServletException(
		// "atencao.usuario.sem.permissao.inserir_logradouro_municipio_diferente_setor_comercial");
		//
		// } else {
		// if (sessao.getAttribute("confirmou") == null) {
		// httpServletRequest.setAttribute("destino",
		// actionMapping
		// .findForward("inserirImovelEndereco"));
		//
		// // httpServletRequest.setAttribute("confirmou", "sim");
		//
		// // retorno = actionMapping
		// // .findForward("gerenciado");
		// String destino ="inserirImovelEndereco" ;
		// actionMapping.setParameter(destino);
		//
		// retorno = montarPaginaConfirmacaoWizard(
		// "atencao.usuario.sem.permissao.inserir_logradouro_municipio_diferente_setor_comercial",
		// httpServletRequest, actionMapping, "");
		// }
		// }

		// httpServletRequest.setAttribute("atencao","O munic�pio do
		// logradouro n�o � o mesmo do setor comercial");

		// URL da pr�xima ABA
		// httpServletRequest
		// .setAttribute(
		// "proximaAba",
		// "/gsan/inserirImovelWizardAction.do?destino=3&action=inserirImovelEnderecoAction");
		//
		// // URL da ABA anterior
		// httpServletRequest
		// .setAttribute(
		// "voltarAba",
		// "/gsan/inserirImovelWizardAction.do?removeEndereco=true&destino=2&action=inserirImovelLocalidadeAction");
		//
		// retorno = actionMapping.findForward("telaOpcaoConsultar");

		// sessao.removeAttribute("colecaoEnderecos");
		// throw new
		// ActionServletException("atencao.municipio.diferente.setor_comercial.logradouro");

		return retorno;
	}

}
