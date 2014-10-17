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

package gcom.gui.operacional.localidadedadooperacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.LocalidadeDadoOperacional;
import gcom.seguranca.acesso.usuario.Usuario;
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
 * @author isilva
 * @date 28/01/2011
 */
public class AtualizarLocalidadeDadoOperacionalAction
				extends GcomAction {

	// Obt�m a inst�ncia da fachada
	Fachada fachada = Fachada.getInstancia();

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		AtualizarLocalidadeDadoOperacionalActionForm form = (AtualizarLocalidadeDadoOperacionalActionForm) actionForm;

		// Valida o Formul�rio e Retorna a Localidade
		Localidade localidade = this.validarForm(form);

		LocalidadeDadoOperacional localidadeDadoOperacional = (LocalidadeDadoOperacional) sessao.getAttribute("localidadeDadoOperacional");
		localidadeDadoOperacional.setLocalidade(localidade);
		localidadeDadoOperacional = form.setLocalidadeDadoOperacionalValues(localidadeDadoOperacional);

		fachada.atualizarLocalidadeDadoOperacional(localidadeDadoOperacional, usuario);

		montarPaginaSucesso(httpServletRequest, "Dados Operacionais com refer�ncia " + form.getMesAnoReferencia()
						+ " atualizado com sucesso.", " Realizar outra Manuten��o de Dados Operacionais",
						"exibirFiltrarLocalidadeDadoOperacionalAction.do?menu=sim");

		sessao.removeAttribute("localidadeDadoOperacionalID");
		sessao.removeAttribute("localidadeDadoOperacional");

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Localidade validarForm(AtualizarLocalidadeDadoOperacionalActionForm form){

		// *********************** Valida Refer�ncia *************************
		if(Util.isVazioOuBranco(form.getMesAnoReferencia())){
			throw new ActionServletException("atencao.required", null, "Refer�ncia");
		}

		boolean mesAnoValido = Util.validarMesAno(form.getMesAnoReferencia());

		if(mesAnoValido == false){
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Refer�ncia");
		}

		try{
			Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoReferencia());
		}catch(Exception e){
			e.printStackTrace();
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Refer�ncia");
		}

		if(Util.isVazioOuBranco(form.getIdLocalidade())){
			throw new ActionServletException("atencao.required", null, "Localidade");
		}

		Integer idLocalidade = null;
		try{
			idLocalidade = Integer.valueOf(form.getIdLocalidade());
		}catch(Exception e){
			e.printStackTrace();
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Localidade");
		}

		// Cria Filtros
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
		// filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<Localidade> localidades = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(localidades == null || localidades.isEmpty()){
			throw new ActionServletException("atencao.localidade.inexistente");
		}

		Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(localidades);

		/*
		 * Verifica se o usu�rio informou ao menos um campo com o valor diferente de branco ou Zero
		 */
		boolean informouAlgo = false;

		if(!isVazioOuZero(form.getVolumeProduzido(), "Volume Produzido")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getExtensaoRedeAgua(), "Extens�o da Rede de �gua")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getExtensaoRedeEsgoto(), "Extens�o da Rede de Esgoto")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosAdministracao(), "Num. Funcion�rios da Adm.")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosAdministracaoTercerizados(), "Num. Funcion�rios da Adm. Terceiros")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosOperacao(), "Num. Funcion�rios da Oper.")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosOperacaoTercerizados(), "Num. Funcion�rios da Oper. Terceiros")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdSulfatoAluminio(), "Qtd. Sulfato de Aluminio")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdCloroGasoso(), "Qtd. Cloro Gasoso")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdHipocloritoSodio(), "Qtd. Hipocl. De Sodio")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQuantidadeFluor(), "Qtd. Fluor")){
			informouAlgo = true;
		}

		if(!Util.isVazioOuBranco(form.getQtdConsumoEnergia())){
			informouAlgo = true;
		}

		if(!Util.isVazioOuBranco(form.getQtdHorasParalisadas())){
			informouAlgo = true;
		}

		if(!informouAlgo){
			throw new ActionServletException("atencao.nenhum.dado.parametro.foi.informado");
		}

		if(Util.isVazioOuBranco(form.getIndicadorUso())){
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Indicador de Uso");
		}

		// ****************************************************************
		return localidade;
	}

	/**
	 * @author isilva
	 * @param campo
	 * @param nomeCampo
	 * @return
	 */
	private boolean isVazioOuZero(String campo, String nomeCampo){

		boolean retorno = false;

		if(Util.isVazioOuBranco(campo)){
			return true;
		}

		try{
			Integer valorInteiro = Integer.valueOf(campo);

			if(valorInteiro == 0){
				retorno = true;
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new ActionServletException("atencao.campo.informado.invalido", null, nomeCampo);
		}

		return retorno;
	}
}
