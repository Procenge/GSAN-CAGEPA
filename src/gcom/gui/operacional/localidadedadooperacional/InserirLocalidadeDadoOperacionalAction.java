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
 * 
 * GSANPCG
 * Virgínia Melo
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
 * @date 26/01/2011
 */
public class InserirLocalidadeDadoOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		InserirLocalidadeDadoOperacionalActionForm form = (InserirLocalidadeDadoOperacionalActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Valida o Formulário e Retorna a Localidade
		Localidade localidade = this.validarForm(form);

		LocalidadeDadoOperacional localidadeDadoOperacional = new LocalidadeDadoOperacional();

		form.setFormValues(localidadeDadoOperacional, localidade);

		Integer idLocalidadeDadoOperacional = fachada.inserirLocalidadeDadoOperacional(localidadeDadoOperacional, usuario);

		montarPaginaSucesso(httpServletRequest, "Dados Operacionais com referência " + form.getMesAnoReferencia()
						+ " inserido com sucesso.", "Inserir outro dado Operacional",
						"exibirInserirLocalidadeDadoOperacionalAction.do?menu=sim",
						"exibirAtualizarLocalidadeDadoOperacionalAction.do?localidadeDadoOperacionalID="
										+ String.valueOf(idLocalidadeDadoOperacional), "Atualizar os Dados operacionais inseridos");

		return retorno;

	}

	/**
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Localidade validarForm(InserirLocalidadeDadoOperacionalActionForm form){

		// *********************** Valida Referência *************************
		if(Util.isVazioOuBranco(form.getMesAnoReferencia())){
			throw new ActionServletException("atencao.required", null, "Referência");
		}

		boolean mesAnoValido = Util.validarMesAno(form.getMesAnoReferencia());

		if(mesAnoValido == false){
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Referência");
		}

		Integer anoMesInformado = null;

		try{
			anoMesInformado = Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoReferencia());
		}catch(Exception e){
			e.printStackTrace();
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Referência");
		}

		// *****************************************************************

		// *****************************************************************
		/*
		 * Integer anoMesCorrente = Util.getAnoMesComoInteger(new Date());
		 * if(Util.compararAnoMesReferencia(anoMesInformado, anoMesCorrente, "<")){
		 * throw new ActionServletException("atencao.mes.ou.ano.menor.que.corrente");
		 * }
		 */
		// **************************************************************

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
		 * Verifica se o usuário informou ao menos um campo com o valor diferente de branco ou Zero
		 */
		boolean informouAlgo = false;

		if(!isVazioOuZero(form.getVolumeProduzido(), "Volume Produzido")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getExtensaoRedeAgua(), "Extensão da Rede de Água")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getExtensaoRedeEsgoto(), "Extensão da Rede de Esgoto")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosAdministracao(), "Num. Funcionários da Adm.")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosAdministracaoTercerizados(), "Num. Funcionários da Adm. Terceiros")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosOperacao(), "Num. Funcionários da Oper.")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(form.getQtdFuncionariosOperacaoTercerizados(), "Num. Funcionários da Oper. Terceiros")){
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