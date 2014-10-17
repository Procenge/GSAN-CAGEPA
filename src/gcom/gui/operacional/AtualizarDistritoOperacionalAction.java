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

package gcom.gui.operacional;

import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.operacional.DadoDistritoOperacional;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.TipoUnidadeOperacional;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0522] Manter Distrito Operacional [SB0001]Atualizar Distrito Operacional
 * 
 * @author P�ricles Tavares
 * @date 28/03/2011
 */

public class AtualizarDistritoOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarDistritoOperacionalActionForm atualizarDistritoOperacionalActionForm = (AtualizarDistritoOperacionalActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		DistritoOperacional distritoOperacional = (DistritoOperacional) sessao.getAttribute("distritoOperacional");

		String[] idDistritoOperacional = new String[1];

		idDistritoOperacional[0] = distritoOperacional.getId().toString();

		// Atualiza a entidade com os valores do formul�rio
		distritoOperacional.setDescricao(atualizarDistritoOperacionalActionForm.getDescricao());
		distritoOperacional.setDescricaoAbreviada(atualizarDistritoOperacionalActionForm.getDescricaoAbreviada());
		distritoOperacional.setId(new Integer(atualizarDistritoOperacionalActionForm.getCodigoDistritoOperacional()));
		distritoOperacional.setIndicadorUso(new Short(atualizarDistritoOperacionalActionForm.getIndicadorUso()));
		// distritoOperacional.setUltimaAlteracao(Util.converteStringParaDateHora(atualizarDistritoOperacionalActionForm.getUltimaAlteracao()));
		if(atualizarDistritoOperacionalActionForm.getTelefone() != null && !atualizarDistritoOperacionalActionForm.getTelefone().equals("")){
			distritoOperacional.setTelefone(Integer.parseInt(atualizarDistritoOperacionalActionForm.getTelefone()));
		}else{
			distritoOperacional.setTelefone(null);
		}
		if(atualizarDistritoOperacionalActionForm.getRamal() != null && !atualizarDistritoOperacionalActionForm.getRamal().equals("")){
			distritoOperacional.setRamal(Integer.parseInt(atualizarDistritoOperacionalActionForm.getRamal()));
		}else{
			distritoOperacional.setRamal(null);
		}
		if(atualizarDistritoOperacionalActionForm.getFax() != null && !atualizarDistritoOperacionalActionForm.getFax().equals("")){
			distritoOperacional.setFax(Integer.parseInt(atualizarDistritoOperacionalActionForm.getFax()));
		}else{
			distritoOperacional.setFax(null);
		}
		distritoOperacional.setEmail(atualizarDistritoOperacionalActionForm.getEmail());
		if(atualizarDistritoOperacionalActionForm.getNumeroCota() != null
						&& !atualizarDistritoOperacionalActionForm.getNumeroCota().equals("")){
			distritoOperacional.setNumeroCota(Integer.parseInt(atualizarDistritoOperacionalActionForm.getNumeroCota()));
		}else{
			distritoOperacional.setNumeroCota(null);
		}
		if(atualizarDistritoOperacionalActionForm.getLatitude() != null && !atualizarDistritoOperacionalActionForm.getLatitude().equals("")){
			distritoOperacional.setLatitude(Integer.parseInt(atualizarDistritoOperacionalActionForm.getLatitude()));
		}else{
			distritoOperacional.setLatitude(null);
		}
		if(atualizarDistritoOperacionalActionForm.getLongitude() != null
						&& !atualizarDistritoOperacionalActionForm.getLongitude().equals("")){
			distritoOperacional.setLongitude(Integer.parseInt(atualizarDistritoOperacionalActionForm.getLongitude()));
		}else{
			distritoOperacional.setLongitude(null);
		}
		if(sessao.getAttribute("colecaoEnderecos") != null){
			Collection colecaoEndereco = (Collection) sessao.getAttribute("colecaoEnderecos");
			if(!colecaoEndereco.isEmpty()){
				DistritoOperacional distritoEndereco = (DistritoOperacional) colecaoEndereco.iterator().next();
				distritoOperacional.setCep(distritoEndereco.getCep());
				distritoOperacional.setBairro(distritoEndereco.getBairro());
				distritoOperacional.setEnderecoReferencia(distritoEndereco.getEnderecoReferencia());
				distritoOperacional.setNumeroImovel(distritoEndereco.getNumeroImovel());
				distritoOperacional.setComplementoEndereco(distritoEndereco.getComplementoEndereco());
			}
		}else{
			distritoOperacional.setCep(null);
			distritoOperacional.setBairro(null);
			distritoOperacional.setEnderecoReferencia(null);
			distritoOperacional.setNumeroImovel(null);
			distritoOperacional.setComplementoEndereco(null);
		}

		TipoUnidadeOperacional tipoUnidadeOperacionalID = new TipoUnidadeOperacional();
		tipoUnidadeOperacionalID.setId(Util.obterInteger(atualizarDistritoOperacionalActionForm.getTipoUnidadeOperacional()));
		distritoOperacional.setTipoUnidadeOperacional(tipoUnidadeOperacionalID);

		Localidade localidadeId = new Localidade();
		localidadeId.setId(Util.obterInteger(atualizarDistritoOperacionalActionForm.getLocalidade()));
		distritoOperacional.setLocalidade(localidadeId);

		SistemaAbastecimento sistemaAbastecimentoId = new SistemaAbastecimento();
		sistemaAbastecimentoId.setId(Util.obterInteger(atualizarDistritoOperacionalActionForm.getSistemaAbastecimento()));
		distritoOperacional.setSistemaAbastecimento(sistemaAbastecimentoId);
		TreeSet<DadoDistritoOperacional> dadosDistritoOperacional = (TreeSet<DadoDistritoOperacional>) sessao
						.getAttribute("dadosDistritoOperacional");
		if(dadosDistritoOperacional != null){
			distritoOperacional.setDadosDistritoOperacional(dadosDistritoOperacional);
		}else{
			distritoOperacional.setDadosDistritoOperacional(new HashSet<DadoDistritoOperacional>());
		}
		// atualiza na base de dados de Municipio
		fachada.atualizarDistritoOperacional(distritoOperacional, usuarioLogado);

		// Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Distrito Operacional de c�digo " + idDistritoOperacional[0] + " atualizado com sucesso.",
						"Realizar outra Manuten��o de Distrito Operacional", "exibirFiltrarDistritoOperacionalAction.do?menu=sim");

		return retorno;
	}

}
