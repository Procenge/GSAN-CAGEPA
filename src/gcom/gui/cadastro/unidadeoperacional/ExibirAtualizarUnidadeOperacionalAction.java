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

package gcom.gui.cadastro.unidadeoperacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidadeoperacional.FiltroUnidadeOperacional;
import gcom.cadastro.unidadeoperacional.UnidadeOperacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Péricles Tavares
 */

public class ExibirAtualizarUnidadeOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarUnidadeOperacional");

		AtualizarUnidadeOperacionalActionForm atualizarUnidadeOperacionalActionForm = (AtualizarUnidadeOperacionalActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		Collection<SistemaAbastecimento> sistemasAbastecimento = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class
						.getName());
		// [FS0001] - Verificar existência de dados
		if(sistemasAbastecimento == null || sistemasAbastecimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Sistema Abastecimento");
		}
		sessao.setAttribute("colecaoSistemaAbastecimento", sistemasAbastecimento);

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		Collection<Localidade> localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
		// [FS0001] - Verificar existência de dados
		if(localidades == null || localidades.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Localidade");
		}
		sessao.setAttribute("colecaoLocalidade", localidades);

		UnidadeOperacional unidadeOperacional = null;

		String idUnidadeOperacional = null;

		// atribui os valores q vem pelo request as variaveis
		String idLocalidade = (String) atualizarUnidadeOperacionalActionForm.getLocalidade();
		FiltroLocalidade filtroLocalidadeTela = new FiltroLocalidade();
		// cria a colecao para receber a pesquisa
		Collection localidadesTela = new HashSet();
		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
			filtroLocalidadeTela.limparListaParametros();
			// coloca parametro no filtro
			filtroLocalidadeTela.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidadeTela.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidade)));
			// pesquisa
			localidadesTela = fachada.pesquisar(filtroLocalidadeTela, Localidade.class.getName());
			if(localidadesTela != null && !localidadesTela.isEmpty() && httpServletRequest.getParameter("recarregar") != null){
				// Localidade foi encontrada
				atualizarUnidadeOperacionalActionForm.setLocalidade(Util.adicionarZerosEsquedaNumero(3, Integer.valueOf(
								((Localidade) ((List) localidadesTela).get(0)).getId().toString()).toString()));
				atualizarUnidadeOperacionalActionForm.setDescricaoLocalidade(((Localidade) ((List) localidadesTela).get(0)).getDescricao());
			}else if(httpServletRequest.getParameter("recarregar") != null){
				httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "true");
				atualizarUnidadeOperacionalActionForm.setLocalidade("");
			}
		}

		if(httpServletRequest.getParameter("idUnidadeOperacional") != null){
			// tela do manter
			idUnidadeOperacional = (String) httpServletRequest.getParameter("idUnidadeOperacional");
			sessao.setAttribute("idUnidadeOperacional", idUnidadeOperacional);
		}else if(sessao.getAttribute("idUnidadeOperacional") != null){
			// tela do filtrar
			idUnidadeOperacional = (String) sessao.getAttribute("idUnidadeOperacional");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir
			idUnidadeOperacional = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarMunicipioAction.do?menu=sim");
		}

		if(idUnidadeOperacional == null){

			if(sessao.getAttribute("idUnidadeOperacional") != null){
				unidadeOperacional = (UnidadeOperacional) sessao.getAttribute("idUnidadeOperacional");
			}else if(httpServletRequest.getParameter("idUnidadeOperacional") != null){
				idUnidadeOperacional = (String) httpServletRequest.getParameter("idUnidadeOperacional").toString();
			}
		}

		// ------Inicio da parte que verifica se vem da página de unidade_operacional_manter.jsp

		if(unidadeOperacional == null){

			if(idUnidadeOperacional != null && !idUnidadeOperacional.equals("")){
				FiltroUnidadeOperacional filtroUnidadeOperacional = new FiltroUnidadeOperacional(
								FiltroUnidadeOperacional.CODIGO_UNIDADE_OPERACIONAL);

				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("sistemaAbastecimento");
				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("logradouro");
				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("bairro");
				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("bairro.municipio");
				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("bairro.municipio.unidadeFederacao");
				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
				filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade("cep");

				filtroUnidadeOperacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOperacional.ID, idUnidadeOperacional));

				Collection colecaoUnidadeOperacional = fachada.pesquisar(filtroUnidadeOperacional, UnidadeOperacional.class.getName());

				if(colecaoUnidadeOperacional != null && !colecaoUnidadeOperacional.isEmpty()){

					unidadeOperacional = (UnidadeOperacional) Util.retonarObjetoDeColecao(colecaoUnidadeOperacional);
					if(!unidadeOperacional.getEnderecoFormatado().equals("")){
						if(httpServletRequest.getParameter("recarregar") == null){
							sessao.setAttribute("colecaoEnderecos", colecaoUnidadeOperacional);
						}
					}
				}
			}
		}

		// ------ Unidade Operacional foi encontrado

		if(unidadeOperacional != null){
			if(httpServletRequest.getParameter("recarregar") == null){
				atualizarUnidadeOperacionalActionForm.setId(unidadeOperacional.getId().toString());

				atualizarUnidadeOperacionalActionForm.setCodigoUnidadeOperacional(unidadeOperacional.getCodigoUnidadeOperacional() + "");
				atualizarUnidadeOperacionalActionForm.setDescricao(unidadeOperacional.getDescricao());
				atualizarUnidadeOperacionalActionForm.setDescricaoAbreviada(unidadeOperacional.getDescricaoAbreviada());
				atualizarUnidadeOperacionalActionForm.setSistemaAbastecimento(unidadeOperacional.getSistemaAbastecimento().getId()
								.toString());
				atualizarUnidadeOperacionalActionForm.setLocalidade(unidadeOperacional.getLocalidade().getId().toString());
				atualizarUnidadeOperacionalActionForm.setDescricaoLocalidade(unidadeOperacional.getLocalidade().getDescricao());
				if(unidadeOperacional.getTelefone() != null){
					atualizarUnidadeOperacionalActionForm.setTelefone(unidadeOperacional.getTelefone().toString());
				}
				if(unidadeOperacional.getRamal() != null){
					atualizarUnidadeOperacionalActionForm.setRamal(unidadeOperacional.getRamal().toString());
				}
				if(unidadeOperacional.getFax() != null){
					atualizarUnidadeOperacionalActionForm.setFax(unidadeOperacional.getFax().toString());
				}
				atualizarUnidadeOperacionalActionForm.setEmail(unidadeOperacional.getEmail());

				atualizarUnidadeOperacionalActionForm.setDescricaoTipoInstalacao(unidadeOperacional.getDescricaoTipoInstalacao());

				if(unidadeOperacional.getNumeroCota() != null){
					atualizarUnidadeOperacionalActionForm.setNumeroCota(unidadeOperacional.getNumeroCota().toString());
				}
				if(unidadeOperacional.getLatitude() != null){
					atualizarUnidadeOperacionalActionForm.setLatitude(unidadeOperacional.getLatitude().toString());
				}
				if(unidadeOperacional.getLongitude() != null){
					atualizarUnidadeOperacionalActionForm.setLongitude(unidadeOperacional.getLongitude().toString());
				}
				atualizarUnidadeOperacionalActionForm.setIndicadorUso(unidadeOperacional.getIndicadorAtivo() + "");

			}
			sessao.setAttribute("unidadeOperacional", unidadeOperacional);
		}

		return retorno;
	}

}
