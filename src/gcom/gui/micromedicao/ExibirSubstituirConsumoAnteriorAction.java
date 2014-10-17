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

package gcom.gui.micromedicao;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ImovelMicromedicao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.util.ControladorException;
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

public class ExibirSubstituirConsumoAnteriorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("substituirConsumoAnterior");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		LeituraConsumoActionForm leituraConsumoActionForm = (LeituraConsumoActionForm) actionForm;

		String codigoImovel = httpServletRequest.getParameter("idImovel");

		sessao.removeAttribute("colecaoConsumoHistorico");

		if(codigoImovel != null && !codigoImovel.trim().equalsIgnoreCase("")){
			try{
				String enderecoFormatadoImovel = fachada.pesquisarEnderecoFormatado(new Integer(codigoImovel));

				httpServletRequest.setAttribute("enderecoImovel", enderecoFormatadoImovel);

			}catch(NumberFormatException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(httpServletRequest.getParameter("peloMenu") != null){
			sessao.setAttribute("peloMenu", true);
		}

		if(codigoImovel != null || leituraConsumoActionForm.getIdImovelSubstituirConsumo() != null){
			// FiltroConsumoHistorico filtroConsumoHistoricoAgua = new FiltroConsumoHistorico();
			// FiltroConsumoHistorico filtroConsumoHistoricoEsgoto = new FiltroConsumoHistorico();

			// Collection colecaoConsumoHistorico = new ArrayList();

			// ImovelMicromedicao imovelMicromedicao = new ImovelMicromedicao();
			Imovel imovel = new Imovel();
			if(codigoImovel != null){
				imovel.setId(new Integer(codigoImovel));
			}else if(httpServletRequest.getParameter("tipoConsulta") != null){
				imovel.setId(new Integer(leituraConsumoActionForm.getIdImovelSubstituirConsumo()));
			}

			try{
				String endereceoFormatado = fachada.pesquisarEnderecoFormatado(imovel.getId());
				String inscricaoFormatada = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
				if(endereceoFormatado == null || endereceoFormatado.trim().equals("")){
					leituraConsumoActionForm.setInscricaoImovel("Matrícula inexistente.");
					leituraConsumoActionForm.setIdImovelSubstituirConsumo(null);
					httpServletRequest.setAttribute("corTexto", "#ff0000");
				}else{
					// leituraConsumoActionForm.setEnderecoFormatado(endereceoFormatado);
					httpServletRequest.setAttribute("enderecoImovel", endereceoFormatado);
					leituraConsumoActionForm.setInscricaoImovel(inscricaoFormatada);
					httpServletRequest.setAttribute("corTexto", "#000000");
					sessao.setAttribute("idImovelSelecionado", imovel.getId());

					SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

					Integer anoMesFaturamento = fachada.retornaAnoMesFaturamentoGrupo(imovel.getId());

					Short mesesMediaConsumo = sistemaParametro.getMesesMediaConsumo();

					int anoMesInicial = Util.subtraiAteSeisMesesAnoMesReferencia(anoMesFaturamento, new Integer(mesesMediaConsumo));

					int anoMesFinal = Util.subtraiAteSeisMesesAnoMesReferencia(anoMesFaturamento, 1);

					Collection colecaoGeral = fachada.pesquisaConsumoHistoricoSubstituirConsumo(imovel.getId(), anoMesInicial, anoMesFinal);

					if(colecaoGeral != null && !colecaoGeral.isEmpty()){

						Iterator iteratorImovelMicromedicao = colecaoGeral.iterator();

						while(iteratorImovelMicromedicao.hasNext()){

							ImovelMicromedicao imovelMicromedicao = (ImovelMicromedicao) iteratorImovelMicromedicao.next();

							if(imovelMicromedicao.getConsumoHistorico() != null){
								ConsumoHistorico consumoHistorico = imovelMicromedicao.getConsumoHistorico();

								if(consumoHistorico != null){
									imovelMicromedicao.setConsumoHistorico(obterConsumoHistorico(consumoHistorico));
								}
							}
							if(imovelMicromedicao.getConsumoHistoricoEsgoto() != null){
								ConsumoHistorico consumoHistoricoEsgoto = imovelMicromedicao.getConsumoHistoricoEsgoto();

								if(consumoHistoricoEsgoto != null){
									imovelMicromedicao.setConsumoHistoricoEsgoto(obterConsumoHistorico(consumoHistoricoEsgoto));
								}
							}

						}

						// joga as colecoes na sessao
						sessao.setAttribute("colecaoConsumoHistorico", colecaoGeral);
						// ConsumoHistorico consumoHistorico = null;
						// ConsumoHistorico consumoHistoricoJuncao = null;
						// Iterator iteratorConsumoHistorico = colecaoGeral.iterator();
						// Iterator iteratorConsumoHistoricoJuncao = null;
						// while(iteratorConsumoHistorico.hasNext()){
						// ImovelMicromedicao imovelMicromedicao = new ImovelMicromedicao();
						// imovelMicromedicao.setImovel(imovel);
						// //
						// iteratorConsumoHistoricoJuncao = colecaoGeral.iterator();
						//
						// consumoHistorico = (ConsumoHistorico)iteratorConsumoHistorico.next();
						// if(consumoHistorico.getLigacaoTipo() != null &&
						// consumoHistorico.getLigacaoTipo().getId().equals(LigacaoTipo.LIGACAO_AGUA)){
						// imovelMicromedicao.setConsumoHistorico(consumoHistorico);
						// colecaoConsumoHistorico.add(imovelMicromedicao);
						// }else if(consumoHistorico.getLigacaoTipo() != null &&
						// consumoHistorico.getLigacaoTipo().getId().equals(LigacaoTipo.LIGACAO_ESGOTO)){
						// imovelMicromedicao.setConsumoHistoricoEsgoto(consumoHistorico);
						// colecaoConsumoHistorico.add(imovelMicromedicao);
						// }
						// while(iteratorConsumoHistoricoJuncao.hasNext()){
						// consumoHistoricoJuncao =
						// (ConsumoHistorico)iteratorConsumoHistoricoJuncao.next();
						//					
						// if(consumoHistoricoJuncao.getMesAno().equals(consumoHistorico.getMesAno())
						// &&
						// !consumoHistorico.getLigacaoTipo().getId().equals(consumoHistoricoJuncao.getLigacaoTipo().getId())){
						//						
						// if(consumoHistoricoJuncao.getLigacaoTipo() != null &&
						// consumoHistoricoJuncao.getLigacaoTipo().getId().equals(LigacaoTipo.LIGACAO_AGUA)){
						// imovelMicromedicao.setConsumoHistorico(consumoHistoricoJuncao);
						// colecaoConsumoHistorico.add(imovelMicromedicao);
						// iteratorConsumoHistorico.remove();
						// break;
						// }else if(consumoHistoricoJuncao.getLigacaoTipo() != null &&
						// consumoHistoricoJuncao.getLigacaoTipo().getId().equals(LigacaoTipo.LIGACAO_ESGOTO)){
						// imovelMicromedicao.setConsumoHistoricoEsgoto(consumoHistoricoJuncao);
						// colecaoConsumoHistorico.add(imovelMicromedicao);
						// iteratorConsumoHistorico.remove();
						// break;
						// }
						//						
						// }
						// }
						// }
					}else{
						throw new ActionServletException("atencao.nenhum_consumo_substituir");
					}
				}
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// imovelMicromedicao.setImovel(imovel);
		}

		httpServletRequest.setAttribute("nomeCampo", "idImovelSubstituirConsumo");
		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * @author Isaac Silva
	 * @date 10/06/2011
	 * @param consumoHistorico
	 * @return
	 */
	private ConsumoHistorico obterConsumoHistorico(ConsumoHistorico consumoHistorico){

		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.ID, consumoHistorico.getId()));
		filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoHistorico.CONSUMO_TIPO);
		filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoHistorico.CONSUMO_ANORMALIDADE);

		ConsumoHistorico consumoHistoricoNovo = (ConsumoHistorico) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
						filtroConsumoHistorico, ConsumoHistorico.class.getName()));
		return consumoHistoricoNovo;
	}
}
