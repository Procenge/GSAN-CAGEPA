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

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteFone;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
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
 * [UC3106] Inserir Check List de Documenta��o do Im�vel
 * Action respons�vel pela exibi��o da tela de Inserir Check List
 * 
 * @author Vicente Zarga
 * @created 06 de Setembro de 2013
 */
public class ExibirInserirImovelCheckListDocumentacaoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirImovelCheckListDocumentacao");

		Fachada fachada = Fachada.getInstancia();

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ImovelCheckListDocumentacaoForm imovelCheckListDocumentacaoForm = (ImovelCheckListDocumentacaoForm) actionForm;

		String limparForm = httpServletRequest.getParameter("limparForm");
		String idImovel = imovelCheckListDocumentacaoForm.getIdImovel();
		Imovel imovel = null;

		if(limparForm != null && !limparForm.equals("")){
			this.limparForm(imovelCheckListDocumentacaoForm, httpServletRequest);
		}

		if(imovelCheckListDocumentacaoForm.getIdImovel() != null && !imovelCheckListDocumentacaoForm.getIdImovel().equals("")){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelCheckListDocumentacaoForm.getIdImovel()));

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel != null && !colecaoImovel.isEmpty()){

				imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			}

			if(imovel != null){

				imovelCheckListDocumentacaoForm.setIdImovel(idImovel);

				imovelCheckListDocumentacaoForm.setMatriculaImovelDados(fachada.pesquisarInscricaoImovel(
								Integer.valueOf(idImovel.trim()), true));

				imovelCheckListDocumentacaoForm.setNomeLocalidade(imovel.getLocalidade().getDescricao());
				imovelCheckListDocumentacaoForm.setCodigoSituacaoAgua(imovel.getLigacaoAguaSituacao().getId().toString());
				imovelCheckListDocumentacaoForm.setDescricaoSituacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
				imovelCheckListDocumentacaoForm.setCodigoSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId().toString());
				imovelCheckListDocumentacaoForm.setDescricaoSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());
				imovelCheckListDocumentacaoForm.setNumeroRota(imovel.getRota().getCodigo().toString());

				if(imovel.getNumeroSegmento() != null){
					imovelCheckListDocumentacaoForm.setNumeroSegmento(imovel.getNumeroSegmento().toString());
				}

				String endereco = fachada.pesquisarEndereco(imovel.getId());
				imovelCheckListDocumentacaoForm.setEndereco(endereco);

				Collection colecaoClienteImovel = fachada.pesquisarClientesImovel(imovel.getId());

				Iterator iterator = colecaoClienteImovel.iterator();

				while(iterator.hasNext()){

					ClienteImovel clienteImovel = (ClienteImovel) iterator.next();

					if(clienteImovel.getDataFimRelacao() == null
									&& clienteImovel.getClienteRelacaoTipo().getId().equals(ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO)){

						Cliente cliente = clienteImovel.getCliente();

						imovelCheckListDocumentacaoForm.setNomeCliente(cliente.getNome());

						FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
						filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, cliente.getId()));
						filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.FONE_TIPO_ID, ConstantesSistema.SIM));
						Collection colecaoTelefones = fachada.pesquisar(filtroClienteFone, ClienteFone.class.getName());

						if(colecaoTelefones != null && !colecaoTelefones.isEmpty()){

							sessao.setAttribute("colecaoTelefones", colecaoTelefones);
						}
					}
				}

				FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();
				filtroImovelSubCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.CATEGORIA);
				filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));
				filtroImovelSubCategoria.setCampoOrderBy(FiltroImovelSubCategoria.CATEGORIA);
				Collection colecaoImovelSubcategoria = fachada.pesquisar(filtroImovelSubCategoria, ImovelSubcategoria.class.getName());

				sessao.setAttribute("colecaoImovelSubcategoria", colecaoImovelSubcategoria);
			}else{

				httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", "true");
				imovelCheckListDocumentacaoForm.setMatriculaImovelDados("IM�VEL INEXISTENTE");

			}
		}

		return retorno;
	}

	private void limparForm(ImovelCheckListDocumentacaoForm imovelCheckListDocumentacaoForm,
					HttpServletRequest httpServletRequest){

		// limpar os dados
		httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", null);
		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("colecaoTelefones");
		sessao.removeAttribute("colecaoImovelSubcategoria");

		imovelCheckListDocumentacaoForm.setIdImovel(null);
		imovelCheckListDocumentacaoForm.setMatriculaImovelDados(null);
		imovelCheckListDocumentacaoForm.setNomeLocalidade(null);
		imovelCheckListDocumentacaoForm.setCodigoSituacaoAgua(null);
		imovelCheckListDocumentacaoForm.setDescricaoSituacaoAgua(null);
		imovelCheckListDocumentacaoForm.setCodigoSituacaoEsgoto(null);
		imovelCheckListDocumentacaoForm.setDescricaoSituacaoEsgoto(null);
		imovelCheckListDocumentacaoForm.setNumeroRota(null);
		imovelCheckListDocumentacaoForm.setNumeroSegmento(null);
		imovelCheckListDocumentacaoForm.setNomeCliente(null);
		imovelCheckListDocumentacaoForm.setEndereco(null);
		imovelCheckListDocumentacaoForm.setIndicadorContrato(null);
		imovelCheckListDocumentacaoForm.setIndicadorCpf(null);
		imovelCheckListDocumentacaoForm.setIndicadorRg(null);
		imovelCheckListDocumentacaoForm.setIndicadorDocImovel(null);
		imovelCheckListDocumentacaoForm.setIndicadorCorrespondencia(null);
		imovelCheckListDocumentacaoForm.setIndicadorTemoConfissaoDivida(null);

	}
}
