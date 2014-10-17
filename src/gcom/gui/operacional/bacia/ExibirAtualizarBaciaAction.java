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

package gcom.gui.operacional.bacia;

import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.operacional.FiltroBacia;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @date 03/02/2011
 */
public class ExibirAtualizarBaciaAction
				extends GcomAction {

	/**
	 * [UC????] Atualizar Bacia
	 * 
	 * @author isilva
	 * @date 03/02/2011
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarBacia");

		AtualizarBaciaActionForm form = (AtualizarBaciaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String removerEndereco = (String) httpServletRequest.getParameter("removerEndereco");

		if(removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase("")){
			// Remove o endereço informado.
			if(sessao.getAttribute("colecaoEnderecos") != null){
				Collection<Bacia> enderecos = (Collection<Bacia>) sessao.getAttribute("colecaoEnderecos");

				if(!enderecos.isEmpty()){
					enderecos.remove(enderecos.iterator().next());
				}
			}
		}else{
			// Verifica se veio do filtrar ou do manter
			String atualizouPagina = (String) httpServletRequest.getParameter("atualizouPagina");

			if(httpServletRequest.getParameter("manter") != null){
				sessao.setAttribute("manter", true);
			}else if(httpServletRequest.getParameter("filtrar") != null){
				sessao.removeAttribute("manter");
			}

			String baciaID = null;

			if(httpServletRequest.getParameter("baciaID") != null
							&& !"".equalsIgnoreCase((String) httpServletRequest.getParameter("baciaID"))){
				baciaID = httpServletRequest.getParameter("baciaID");
			}

			if(baciaID == null){
				if(httpServletRequest.getAttribute("baciaID") != null
								&& !"".equalsIgnoreCase((String) httpServletRequest.getAttribute("baciaID"))){
					baciaID = (String) httpServletRequest.getAttribute("baciaID");
				}
			}

			if(baciaID == null){
				if(sessao.getAttribute("baciaID") != null && !"".equalsIgnoreCase((String) sessao.getAttribute("baciaID"))){
					baciaID = (String) sessao.getAttribute("baciaID");
				}
			}

			sessao.setAttribute("baciaID", baciaID);
			httpServletRequest.setAttribute("baciaID", baciaID);
			sessao.setAttribute("baciaID", baciaID);
			form.setIdBacia(baciaID);

			Bacia bacia = null;

			// if (sessao.getAttribute("bacia") == null) {
			FiltroBacia filtroBacia = new FiltroBacia();
			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.ID, Integer.valueOf(baciaID)));
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.SUBSISTEMA_ESGOTO);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.SUBSISTEMA_ESGOTO_SISTEMA_ESGOTO);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_CEP);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_BAIRRO);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.ENDERECO_REFERENCIA);

			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_CEP_LOGRADOURO);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_CEP_LOGRADOURO_LOGRADOURO_TIPO);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_CEP_LOGRADOURO_LOGRADOURO_TITULO);

			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_BAIRRO_BAIRRO);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_BAIRRO_BAIRRO_MUNICIPIO);
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_BAIRRO_BAIRRO_MUNICIPIO_UNIDADE_FEDERACAO);

			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.LOGRADOURO_CEP_CEP);

			bacia = (Bacia) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroBacia, Bacia.class.getName()));

			// } else {
			// bacia = (Bacia) sessao.getAttribute("bacia");
			// }

			if(bacia == null){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			sessao.setAttribute("baciaID", String.valueOf(bacia.getId()));
			sessao.setAttribute("bacia", bacia);

			if((httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S"))){
				form.setFormValues(bacia);

				Collection<Bacia> colecaoEnderecos = montaColecaoEndereco(bacia);
				sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
			}else if(!Util.isVazioOuBranco(atualizouPagina) && ConstantesSistema.SIM.toString().equalsIgnoreCase(atualizouPagina)){
				// Guardar campos quando o usuario Atualizar a Página
			}else{
				form.setFormValues(bacia);

				Collection<Bacia> colecaoEnderecos = montaColecaoEndereco(bacia);
				sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
			}
		}

		return retorno;
	}

	private Collection<Bacia> montaColecaoEndereco(final Bacia bacia){

		Collection<Bacia> colecaoEnderecos = new ArrayList<Bacia>();

		if(bacia.getEnderecoFormatado() != null){
			Bacia baciaEndereco = new Bacia();

			LogradouroCep logradouroCep = bacia.getLogradouroCep();
			LogradouroBairro logradouroBairro = bacia.getLogradouroBairro();
			String complementoEndereco = bacia.getComplementoEndereco();
			String numeroImovel = bacia.getNumeroImovel();
			EnderecoReferencia enderecoReferencia = bacia.getEnderecoReferencia();

			baciaEndereco.setLogradouroCep(logradouroCep);
			baciaEndereco.setLogradouroBairro(logradouroBairro);
			baciaEndereco.setComplementoEndereco(complementoEndereco);
			baciaEndereco.setNumeroImovel(numeroImovel);
			baciaEndereco.setEnderecoReferencia(enderecoReferencia);

			colecaoEnderecos.add(baciaEndereco);
		}

		return colecaoEnderecos;
	}

}
