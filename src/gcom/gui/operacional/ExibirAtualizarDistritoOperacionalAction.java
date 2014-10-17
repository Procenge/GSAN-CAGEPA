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

package gcom.gui.operacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.FiltroTipoUnidadeOperacional;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.TipoUnidadeOperacional;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
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
 * [UC0522] Manter Distrito Operacional [SB0001]Atualizar Distrito Operacional
 * 
 * @author Eduardo Bianchi
 * @date 09/02/2007
 */

public class ExibirAtualizarDistritoOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarDistritoOperacional");

		AtualizarDistritoOperacionalActionForm atualizarDistritoOperacionalActionForm = (AtualizarDistritoOperacionalActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		if(httpServletRequest.getParameter("pesquisarSetor") == null){

			DistritoOperacional distritoOperacional = null;

			String idDistritoOperacional = null;

			// atribui os valores q vem pelo request as variaveis
			String idLocalidade = (String) atualizarDistritoOperacionalActionForm.getLocalidade();
			FiltroLocalidade filtroLocalidadeTela = new FiltroLocalidade();
			// cria a colecao para receber a pesquisa
			Collection localidadesTela = new HashSet();
			if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
				filtroLocalidadeTela.limparListaParametros();
				// coloca parametro no filtro
				filtroLocalidadeTela.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroLocalidadeTela.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));
				// pesquisa
				localidadesTela = fachada.pesquisar(filtroLocalidadeTela, Localidade.class.getName());
				if(localidadesTela != null && !localidadesTela.isEmpty() && httpServletRequest.getParameter("recarregar") != null){
					// Localidade foi encontrada
					atualizarDistritoOperacionalActionForm.setLocalidade(Util.adicionarZerosEsquedaNumero(3, new Integer(
									((Localidade) ((List) localidadesTela).get(0)).getId().toString()).toString()));
					atualizarDistritoOperacionalActionForm.setDescricaoLocalidade(((Localidade) ((List) localidadesTela).get(0))
									.getDescricao());
				}else if(httpServletRequest.getParameter("recarregar") != null){
					httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "true");
					atualizarDistritoOperacionalActionForm.setLocalidade("");
				}
			}

			if(httpServletRequest.getParameter("idDistritoOperacional") != null){
				// tela do manter
				idDistritoOperacional = (String) httpServletRequest.getParameter("idDistritoOperacional");
				sessao.setAttribute("idDistritoOperacional", idDistritoOperacional);
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterDistritoOperacionalAction.do");
			}else if(sessao.getAttribute("idDistritoOperacional") != null){
				// tela do filtrar
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarDistritoOperacionalAction.do?voltar=filtrar");
				idDistritoOperacional = (String) sessao.getAttribute("idDistritoOperacional");
			}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
				// link na tela de sucesso do inserir municipio
				idDistritoOperacional = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarDistritoOperacionalAction.do?menu=sim");
			}

			if(idDistritoOperacional == null){

				if(sessao.getAttribute("idRegistroAtualizar") != null){
					distritoOperacional = (DistritoOperacional) sessao.getAttribute("idRegistroAtualizar");
					sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarDistritoOperacionalAction.do?voltar=filtrar");
				}else if(httpServletRequest.getParameter("idDistritoOperacional") != null){
					idDistritoOperacional = (String) httpServletRequest.getParameter("idDistritoOperacional").toString();
				}
			}

			// ------Inicio da parte que verifica se vem da página de
			// distrito_Operacional_manter.jsp
			if(distritoOperacional == null){
				if(idDistritoOperacional != null && !idDistritoOperacional.equals("")){

					FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("sistemaAbastecimento");
					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("localidade");
					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("bairro.bairro");
					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("bairro.bairro.municipio");
					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("bairro.bairro.municipio.unidadeFederacao");
					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("cep.cep");
					filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("tipoUnidadeOperacional");
					filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, idDistritoOperacional));

					Collection colecaoDistritoOperacional = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class
									.getName());

					if(colecaoDistritoOperacional != null && !colecaoDistritoOperacional.isEmpty()){
						distritoOperacional = (DistritoOperacional) Util.retonarObjetoDeColecao(colecaoDistritoOperacional);
						if(!distritoOperacional.getEnderecoFormatado().equals("")){
							if(httpServletRequest.getParameter("recarregar") == null){
								sessao.setAttribute("colecaoEnderecos", colecaoDistritoOperacional);
							}
						}
					}
				}
			}
			if(distritoOperacional != null){
				if(httpServletRequest.getParameter("recarregar") == null){

					// ------ O Distrito Operacional foi encontrado
					atualizarDistritoOperacionalActionForm.setCodigoDistritoOperacional(distritoOperacional.getId().toString());
					atualizarDistritoOperacionalActionForm.setDescricao(distritoOperacional.getDescricao());
					atualizarDistritoOperacionalActionForm.setDescricaoAbreviada(distritoOperacional.getDescricaoAbreviada());
					atualizarDistritoOperacionalActionForm.setIndicadorUso("" + distritoOperacional.getIndicadorUso());
					atualizarDistritoOperacionalActionForm.setUltimaAlteracao(Util.formatarDataComHora(distritoOperacional
									.getUltimaAlteracao()));

					atualizarDistritoOperacionalActionForm.setSistemaAbastecimento(distritoOperacional.getSistemaAbastecimento().getId()
									.toString());
					atualizarDistritoOperacionalActionForm.setLocalidade(distritoOperacional.getLocalidade().getId().toString());
					atualizarDistritoOperacionalActionForm.setDescricaoLocalidade(distritoOperacional.getLocalidade().getDescricao());
					if(distritoOperacional.getTelefone() != null){
						atualizarDistritoOperacionalActionForm.setTelefone(distritoOperacional.getTelefone().toString());
					}
					if(distritoOperacional.getRamal() != null){
						atualizarDistritoOperacionalActionForm.setRamal(distritoOperacional.getRamal().toString());
					}
					if(distritoOperacional.getFax() != null){
						atualizarDistritoOperacionalActionForm.setFax(distritoOperacional.getFax().toString());
					}
					atualizarDistritoOperacionalActionForm.setEmail(distritoOperacional.getEmail());
					;
					atualizarDistritoOperacionalActionForm.setTipoUnidadeOperacional(distritoOperacional.getTipoUnidadeOperacional()
									.getId().toString());
					;
					;
					if(distritoOperacional.getNumeroCota() != null){
						atualizarDistritoOperacionalActionForm.setNumeroCota(distritoOperacional.getNumeroCota().toString());
					}
					if(distritoOperacional.getLatitude() != null){
						atualizarDistritoOperacionalActionForm.setLatitude(distritoOperacional.getLatitude().toString());
					}
					if(distritoOperacional.getLongitude() != null){
						atualizarDistritoOperacionalActionForm.setLongitude(distritoOperacional.getLongitude().toString());
					}
					if(!distritoOperacional.getEnderecoFormatado().equals("")){
						if(httpServletRequest.getParameter("recarregar") == null){
							ArrayList<DistritoOperacional> colecaoDistritoOperacional = new ArrayList<DistritoOperacional>();
							colecaoDistritoOperacional.add(distritoOperacional);
							sessao.setAttribute("colecaoEnderecos", colecaoDistritoOperacional);
						}
					}
					sessao.setAttribute("distritoOperacional", distritoOperacional);
					sessao.removeAttribute("dadosDistritoOperacional");
				}
			}

		}

		// ------ Fim da parte que verifica se vem da página de distrito_operacional_manter.jsp

		// Sistema Abastecimento
		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoSistemaAbastecimento = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

		if(colecaoSistemaAbastecimento == null || colecaoSistemaAbastecimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Sistema Abastecimento");
		}

		httpServletRequest.setAttribute("colecaoSistemaAbastecimento", colecaoSistemaAbastecimento);

		// Tipo Unidade Operacional
		FiltroTipoUnidadeOperacional filtroTipoUnidadeOperacional = new FiltroTipoUnidadeOperacional();
		filtroTipoUnidadeOperacional.adicionarParametro(new ParametroSimples(FiltroTipoUnidadeOperacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoTipoUnidadeOperacional = fachada.pesquisar(filtroTipoUnidadeOperacional, TipoUnidadeOperacional.class.getName());

		if(colecaoTipoUnidadeOperacional == null || colecaoTipoUnidadeOperacional.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tipo Unidade Operacional");
		}

		// Recupera os parâmetros do sistema
		SistemaParametro sistemaParametro = getFachada().pesquisarParametrosDoSistema();

		// Recupera o nome abreviado empresa
		// sistema
		String nomeEmpresa = sistemaParametro.getNomeAbreviadoEmpresa();

		if(nomeEmpresa.equalsIgnoreCase("DESO")){

			sessao.setAttribute("nomeEmpresaDistrito", nomeEmpresa);

		}

		httpServletRequest.setAttribute("colecaoTipoUnidadeOperacional", colecaoTipoUnidadeOperacional);

		return retorno;
	}

}
