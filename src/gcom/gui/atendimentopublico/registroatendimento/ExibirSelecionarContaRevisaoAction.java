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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Abrangencia;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author isilva
 */
public class ExibirSelecionarContaRevisaoAction
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

		// Seta a a��o de retorno
		ActionForward retorno = actionMapping.findForward("exibirSelecionarContaRevisao");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		sessao.removeAttribute("closePage");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm = (SelecionarContaRevisaoActionForm) actionForm;

		String codigoImovel = null;

		if(!Util.isVazioOuBranco((String) httpServletRequest.getParameter("codigoImovel"))){
			codigoImovel = (String) httpServletRequest.getParameter("codigoImovel");
			selecionarContaRevisaoActionForm.setCodigoImovel(codigoImovel);
		}else{
			codigoImovel = (String) selecionarContaRevisaoActionForm.getCodigoImovel();
		}

		if(!Util.isVazioOuBranco((String) httpServletRequest.getParameter("especificacao"))){
			selecionarContaRevisaoActionForm.setEspecificacao((String) httpServletRequest.getParameter("especificacao"));
		}

		pesquisarContasImovel(sessao, fachada, codigoImovel);

		pesquisarContaMotivoRevisao(sessao, fachada);

		if(!Util.isVazioOuBranco((String) httpServletRequest.getParameter("abrirPopUpEnviarContaRevisao"))){
			selecionarContaRevisaoActionForm.setAbrirPopUpEnviarContaRevisao((String) httpServletRequest
							.getParameter("abrirPopUpEnviarContaRevisao"));
		}

		if(selecionarContaRevisaoActionForm.getContaSelectID() != null && selecionarContaRevisaoActionForm.getContaSelectID().length > 0){

			StringBuffer select = new StringBuffer("");
			selecionarContaRevisaoActionForm.setContaSelectAuxID("");

			for(String selecionado : selecionarContaRevisaoActionForm.getContaSelectID()){

				if(!Util.isVazioOuBranco(select.toString())){
					select.append(",");
				}

				select.append(selecionado);
			}

			selecionarContaRevisaoActionForm.setContaSelectAuxID(select.toString());
		}

		return retorno;
	}

	/**
	 * @author isilva
	 * @param sessao
	 * @param fachada
	 */
	@SuppressWarnings("unchecked")
	private void pesquisarContaMotivoRevisao(HttpSession sessao, Fachada fachada){

		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao();
		filtroContaMotivoRevisao.adicionarParametro(new ParametroSimples(FiltroContaMotivoRevisao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<ContaMotivoRevisao> colecaoContaMotivoRevisao = (ArrayList<ContaMotivoRevisao>) fachada.pesquisar(
						filtroContaMotivoRevisao, ContaMotivoRevisao.class.getName());

		sessao.setAttribute("colecaoContaMotivoRevisao", colecaoContaMotivoRevisao);
	}

	/**
	 * @author isilva
	 * @param sessao
	 * @param fachada
	 * @param codigoImovel
	 */
	@SuppressWarnings("unchecked")
	private void pesquisarContasImovel(HttpSession sessao, Fachada fachada, String codigoImovel){

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		Imovel imovel = fachada.pesquisarImovelRegistroAtendimento(Integer.valueOf(codigoImovel));

		if(imovel == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Im�vel");
		}

		Collection<Conta> colecaoContaImovelRetornoConsulta = fachada.obterContasImovelManter(imovel);
		Collection<Conta> colecaoContaImovel = new ArrayList<Conta>();

		if(colecaoContaImovelRetornoConsulta != null && !colecaoContaImovelRetornoConsulta.isEmpty()){
			for(Conta conta : colecaoContaImovelRetornoConsulta){
				if(conta.getDataRevisao() == null){
					colecaoContaImovel.add(conta);
				}
			}
		}

		// ------------ CONTROLE DE ABRANGENCIA ----------------
		Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovel);

		if(!fachada.verificarAcessoAbrangencia(abrangencia)){
			throw new ActionServletException("atencao.acesso.negado.abrangencia");
		}
		// ------------ FIM CONTROLE DE ABRANGENCIA ------------

		// O sistema exibe uma lista das contas do im�vel com situa��o atual normal, retificada ou
		// inclu�da

		if((colecaoContaImovel == null || colecaoContaImovel.isEmpty())){
			// [FS0047] � Verificar exist�ncia de alguma conta
			sessao.removeAttribute("colecaoContaImovel");
			throw new ActionServletException("atencao.imovel.matricula.nao.possui.nenhuma.conta", null, codigoImovel);
		}

		// Ordenando a cole��o por m�s/ano de refer�ncia
		Collections.sort((List<Conta>) colecaoContaImovel, new Comparator() {

			public int compare(Object a, Object b){

				int retorno = 0;

				String anoMesReferencia1 = String.valueOf(((Conta) a).getReferencia());
				String anoMesReferencia2 = String.valueOf(((Conta) b).getReferencia());

				Integer ano1 = new Integer(anoMesReferencia1.substring(0, 4));
				Integer ano2 = new Integer(anoMesReferencia2.substring(0, 4));
				Integer mes1 = new Integer(anoMesReferencia1.substring(4, 6));
				Integer mes2 = new Integer(anoMesReferencia2.substring(4, 6));

				if(ano1 > ano2){
					retorno = 1;
				}else if(ano1 < ano2){
					retorno = -1;
				}else if(mes1 > mes2){
					retorno = 1;
				}else if(mes1 < mes2){
					retorno = -1;
				}

				return retorno;
			}
		});

		// Coloca na sess�o a cole��o com as contas do im�vel selecionado
		sessao.setAttribute("colecaoContaImovel", colecaoContaImovel);
	}
}
