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

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.BoletoBancarioMotivoCancelamento;
import gcom.cobranca.BoletoBancarioSituacao;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroBoletoBancarioMotivoCancelamento;
import gcom.cobranca.FiltroBoletoBancarioSituacao;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3022] Filtrar Boleto Banc�rio
 * Pr�-processamento para a p�gina de filtro de boleto bancario
 * 
 * @author Cinthya Cavalcanti
 * @created 20/09/2011
 */
public class ExibirFiltrarBoletoBancarioAction
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

		ActionForward retorno = actionMapping.findForward("filtrarBoletoBancario");

		FiltrarBoletoBancarioActionForm filtrarBoletoBancarioActionForm = (FiltrarBoletoBancarioActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		if(filtrarBoletoBancarioActionForm.getAtualizarFiltro() == null || filtrarBoletoBancarioActionForm.getAtualizarFiltro().equals("")){
			filtrarBoletoBancarioActionForm.setAtualizarFiltro("1");
		}

		if(filtrarBoletoBancarioActionForm.getClienteBoletoBancarioFiltro() != null
						&& !filtrarBoletoBancarioActionForm.getClienteBoletoBancarioFiltro().trim().equalsIgnoreCase("")){

			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("cliente");

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, filtrarBoletoBancarioActionForm
							.getClienteBoletoBancarioFiltro()));

			Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoCliente == null || colecaoCliente.isEmpty()){

				httpServletRequest.setAttribute("idClienteNaoEncontrado", "exception");

				filtrarBoletoBancarioActionForm.setClienteBoletoBancarioFiltro("");

				filtrarBoletoBancarioActionForm.setNomeClienteBoletoBancarioFiltro("CLIENTE INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "banco");

			}else{
				Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

				filtrarBoletoBancarioActionForm.setClienteBoletoBancarioFiltro(String.valueOf(cliente.getId().toString()));

				filtrarBoletoBancarioActionForm.setNomeClienteBoletoBancarioFiltro(cliente.getNome());

			}
		}

		if(filtrarBoletoBancarioActionForm.getImovelBoletoBancarioFiltro() != null
						&& !filtrarBoletoBancarioActionForm.getImovelBoletoBancarioFiltro().trim().equalsIgnoreCase("")){

			FiltroImovel filtroImovel = new FiltroImovel();

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, filtrarBoletoBancarioActionForm
							.getImovelBoletoBancarioFiltro()));

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel == null || colecaoImovel.isEmpty()){

				httpServletRequest.setAttribute("corInscricao", "exception");

				filtrarBoletoBancarioActionForm.setImovelBoletoBancarioFiltro("");

				filtrarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioFiltro("IM�VEL INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "banco");

			}else{
				Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

				filtrarBoletoBancarioActionForm.setImovelBoletoBancarioFiltro(String.valueOf(imovel.getId().toString()));

				filtrarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioFiltro(imovel.getInscricaoFormatada());

			}
		}

		if(filtrarBoletoBancarioActionForm.getBancoBoletoBancarioFiltro() != null
						&& !filtrarBoletoBancarioActionForm.getBancoBoletoBancarioFiltro().equals("")){

			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();

			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");

			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, filtrarBoletoBancarioActionForm
							.getBancoBoletoBancarioFiltro()));

			Collection colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

			if(colecaoArrecadador == null || colecaoArrecadador.isEmpty()){

				httpServletRequest.setAttribute("corBanco", "exception");

				filtrarBoletoBancarioActionForm.setBancoBoletoBancarioFiltro("");

				filtrarBoletoBancarioActionForm.setDescricaoBancoBoletoBancarioFiltro("ARRECADADOR INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "banco");

			}else{
				Arrecadador arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);

				filtrarBoletoBancarioActionForm.setBancoBoletoBancarioFiltro(String.valueOf(arrecadador.getCodigoAgente().toString()));

				filtrarBoletoBancarioActionForm.setDescricaoBancoBoletoBancarioFiltro(arrecadador.getCliente().getNome());

			}
		}

		// Preenche o campo de Situa��es de Cobran�a
		FiltroBoletoBancarioSituacao filtroBoletoBancarioSituacao = new FiltroBoletoBancarioSituacao();

		filtroBoletoBancarioSituacao.setCampoOrderBy(FiltroBoletoBancarioSituacao.DESCRICAO);

		preencherColecao(filtroBoletoBancarioSituacao, BoletoBancarioSituacao.class.getName(), "colecaoBoletoBancarioSituacao", sessao,
						filtrarBoletoBancarioActionForm, fachada, filtrarBoletoBancarioActionForm.getIdsSituacaoBoletoBancarioFiltro(),
						BoletoBancarioSituacao.class);

		if(sessao.getAttribute("colecaoBoletoBancarioSituacao") == null){
			httpServletRequest.setAttribute("colecaoAusente", "");
		}

		// Preenche o campo de DocumentoTipo
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();

		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);

		preencherColecao(filtroDocumentoTipo, DocumentoTipo.class.getName(), "colecaoDocumentoTipo", sessao,
						filtrarBoletoBancarioActionForm, fachada,
						filtrarBoletoBancarioActionForm.getIdsTipoDocumentoBoletoBancarioFiltro(), DocumentoTipo.class);

		// Preenche o campo de Motivo Cancelamento
		FiltroBoletoBancarioMotivoCancelamento filtroBoletoBancarioMotivoCancelamento = new FiltroBoletoBancarioMotivoCancelamento();

		filtroBoletoBancarioMotivoCancelamento.setCampoOrderBy(FiltroBoletoBancarioMotivoCancelamento.DESCRICAO);

		preencherColecao(filtroBoletoBancarioMotivoCancelamento, BoletoBancarioMotivoCancelamento.class.getName(),
						"colecaoBoletoBancarioMotivoCancelamento", sessao, filtrarBoletoBancarioActionForm, fachada,
						filtrarBoletoBancarioActionForm.getIdsMotivoCancelamentoBoletoBancarioFiltro(),
						BoletoBancarioMotivoCancelamento.class);

		return retorno;
	}

	public String[] preencherColecao(Filtro filtro, String nomeClasse, String nomeAtributo, HttpSession sessao,
					FiltrarBoletoBancarioActionForm filtrarBoletoBancarioActionForm, Fachada fachada, String[] Ids, Class object){

		Collection colecao = fachada.pesquisar(filtro, nomeClasse);
		sessao.setAttribute(nomeAtributo, colecao);

		String[] idsRetorno = null;

		if(Ids == null || Ids.length == 0){
			Iterator iter = colecao.iterator();
			idsRetorno = new String[colecao.size()];
			int i = 0;
			while(iter.hasNext()){
				Object obj = (Object) iter.next();
				if(obj instanceof BoletoBancarioMotivoCancelamento){
					idsRetorno[i++] = ((BoletoBancarioMotivoCancelamento) obj).getId() + "";
				}else if(obj instanceof BoletoBancarioSituacao){
					idsRetorno[i++] = ((BoletoBancarioSituacao) obj).getId() + "";
				}else if(obj instanceof DocumentoTipo){
					idsRetorno[i++] = ((DocumentoTipo) obj).getId() + "";
				}
			}
		}
		return idsRetorno;
	}

}
