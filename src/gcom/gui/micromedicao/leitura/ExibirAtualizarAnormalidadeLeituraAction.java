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

package gcom.gui.micromedicao.leitura;

import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
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
 * Descrição da classe
 * 
 * @author Thiago Tenório
 * @date 30/10/2006
 * @author eduardo henrique [UC0191]
 * @date 25/06/2008
 *       Inclusão dos atributos: Mensagem de Leitura , Mensagem de manutenção, Mensagem de prevenção
 *       de acidentes
 *       Incidência da anormalidade para a geração da ordem de serviço,
 *       Emissão automática de documento, Indicador de que a ocorrência aceita leitura,
 *       Indicador de que a ocorrência deve ser listada nos relatórios de crítica/fiscalização de
 *       leitura
 *       Indicador de retenção de conta , Indicador de concessão de crédito de consumo
 *       Indicador de isenção de cobrança de água, Indicador de isenção de cobrança de esgoto
 */
public class ExibirAtualizarAnormalidadeLeituraAction
				extends GcomAction {

	/**
	 * [UC0191] Atualizar Anormalidade de Leitura
	 * Este caso de uso permite alterar uma Anormalidade de Leitura
	 * 
	 * @author Thiago Tenório
	 * @date 31/10/2006
	 * @author eduardo henrique [UC0191]
	 * @date 25/06/2008
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarAnormalidadeLeitura");

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarAnormalidadeLeituraActionForm atualizarAnormalidadeLeituraActionForm = (AtualizarAnormalidadeLeituraActionForm) actionForm;

		if(httpServletRequest.getParameter("menu") != null){
			atualizarAnormalidadeLeituraActionForm.setTipoServico("");
		}

		Fachada fachada = Fachada.getInstancia();

		String id = null;

		String idLeituraAnormalidade = null;

		if(httpServletRequest.getParameter("idRegistroAtualizacao") != null
						&& !httpServletRequest.getParameter("idRegistroAtualizacao").equals("")){

			sessao.removeAttribute("objetoLeituraAnormalidade");
			sessao.removeAttribute("colecaoLeituraAnormalidadeTela");

		}

		// Verifica se veio do filtrar ou do manter

		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		// Verifica se o servicoCobrancaValor já está na sessão, em caso
		// afirmativo
		// significa que o usuário já entrou na tela e apenas selecionou algum
		// item que deu um reload na tela e em caso negativo significa que ele
		// está entrando pela primeira vez

		if(sessao.getAttribute("colecaoLeituraAnormalidadeTela") == null){

			if(sessao.getAttribute("objetoLeituraAnormalidade") != null){

				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) sessao.getAttribute("objetoLeituraAnormalidade");

				sessao.setAttribute("idLeituraAnormalidade", leituraAnormalidade.getId().toString());

				sessao.setAttribute("leituraAnormalidade", leituraAnormalidade);

				atualizarAnormalidadeLeituraActionForm.setDescricao(leituraAnormalidade.getDescricao());

				atualizarAnormalidadeLeituraActionForm.setAbreviatura(leituraAnormalidade.getDescricaoAbreviada());

				atualizarAnormalidadeLeituraActionForm.setIndicadorRelativoHidrometro(leituraAnormalidade.getIndicadorRelativoHidrometro()
								.toString());

				atualizarAnormalidadeLeituraActionForm.setOsAutomatico(leituraAnormalidade.getIndicadorEmissaoOrdemServico().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraNaoInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraNaoturaInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setDataUltimaAlteracao(Util.formatarData(leituraAnormalidade.getUltimaAlteracao()));

				atualizarAnormalidadeLeituraActionForm.setIndicadorUso(leituraAnormalidade.getIndicadorUso().toString());

				if(leituraAnormalidade.getIndicadorImovelSemHidrometro() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorImovelSemHidrometro(leituraAnormalidade
									.getIndicadorImovelSemHidrometro().toString());
				}

				if(leituraAnormalidade.getIndicadorSistema() != null){
					atualizarAnormalidadeLeituraActionForm.setUsoRestritoSistema(leituraAnormalidade.getIndicadorSistema().toString());
				}

				if(leituraAnormalidade.getIndicadorPerdaTarifaSocial() != null){
					atualizarAnormalidadeLeituraActionForm.setPerdaTarifaSocial(leituraAnormalidade.getIndicadorPerdaTarifaSocial()
									.toString());
				}

				if(leituraAnormalidade.getServicoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoServico(leituraAnormalidade.getServicoTipo().getId().toString());
				}

				// Adição dos novos atributos

				atualizarAnormalidadeLeituraActionForm.setMensagemImpressaoConta(leituraAnormalidade.getMensagemContaLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgenteManutencao(leituraAnormalidade
								.getMensagemSugestaoManutencaoLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgentePrevencao(leituraAnormalidade
								.getMensagemSugestaoPrevencaoLeituraAnormalidade());

				if(leituraAnormalidade.getNumeroIncidenciasGeracaoOrdemServico() != null){
					atualizarAnormalidadeLeituraActionForm.setNumeroIncidenciasGeracaoOS(leituraAnormalidade
									.getNumeroIncidenciasGeracaoOrdemServico().toString());
				}

				if(leituraAnormalidade.getDocumentoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoDocumento(leituraAnormalidade.getDocumentoTipo().getId().toString());
				}

				if(leituraAnormalidade.getIndicadorAceiteLeitura() != null){
					atualizarAnormalidadeLeituraActionForm.setAceiteLeitura(leituraAnormalidade.getIndicadorAceiteLeitura().toString());
				}

				if(leituraAnormalidade.getIndicadorListagemAnormalidadeRelatorios() != null){
					atualizarAnormalidadeLeituraActionForm.setImpressaoRelatorioCriticaFiscalizacao(leituraAnormalidade
									.getIndicadorListagemAnormalidadeRelatorios().toString());
				}

				if(leituraAnormalidade.getIndicadorRetencaoConta() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorRetencaoConta(leituraAnormalidade.getIndicadorRetencaoConta()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorCreditoConsumo() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorConcessaoCreditoConsumo(leituraAnormalidade
									.getIndicadorCreditoConsumo().toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoAgua() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaAgua(leituraAnormalidade.getIndicadorIsencaoAgua()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoEsgoto() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaEsgoto(leituraAnormalidade
									.getIndicadorIsencaoEsgoto().toString());
				}

				id = leituraAnormalidade.getId().toString();

				sessao.setAttribute("leituraAnormalidadeAtualizar", leituraAnormalidade);
				sessao.removeAttribute("objetoLeituraAnormalidade");

			}else{

				LeituraAnormalidade leituraAnormalidade = null;

				idLeituraAnormalidade = null;

				if(httpServletRequest.getParameter("idRegistroAtualizacao") == null
								|| httpServletRequest.getParameter("idRegistroAtualizacao").equals("")){
					leituraAnormalidade = (LeituraAnormalidade) sessao.getAttribute("leituraAnormalidade");
				}else{
					idLeituraAnormalidade = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
					sessao.setAttribute("idRegistroAtualizacao", idLeituraAnormalidade);
				}

				if(idLeituraAnormalidade != null){

					id = idLeituraAnormalidade;

					FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
					filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoSemleitura");
					filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoComleitura");
					filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeLeituraSemleitura");
					filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeLeituraComleitura");

					filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, idLeituraAnormalidade));

					Collection<LeituraAnormalidade> colecaoLeituraAnormalidade = fachada.pesquisar(filtroLeituraAnormalidade,
									LeituraAnormalidade.class.getName());

					if(colecaoLeituraAnormalidade == null || colecaoLeituraAnormalidade.isEmpty()){
						throw new ActionServletException("atencao.atualizacao.timestamp");
					}

					httpServletRequest.setAttribute("colecaoLeituraAnormalidade", colecaoLeituraAnormalidade);

					leituraAnormalidade = (LeituraAnormalidade) colecaoLeituraAnormalidade.iterator().next();

				}

				if(idLeituraAnormalidade == null){
					if(sessao.getAttribute("idRegistroAtualizacao") != null){
						idLeituraAnormalidade = (String) sessao.getAttribute("idRegistroAtualizacao");
					}else{
						leituraAnormalidade = (LeituraAnormalidade) sessao.getAttribute("leituraAnormalidade");
						idLeituraAnormalidade = leituraAnormalidade.getId().toString();
					}
				}

				atualizarAnormalidadeLeituraActionForm.setDescricao(leituraAnormalidade.getDescricao());

				atualizarAnormalidadeLeituraActionForm.setAbreviatura(leituraAnormalidade.getDescricaoAbreviada());

				atualizarAnormalidadeLeituraActionForm.setIndicadorRelativoHidrometro(leituraAnormalidade.getIndicadorRelativoHidrometro()
								.toString());

				atualizarAnormalidadeLeituraActionForm.setOsAutomatico(leituraAnormalidade.getIndicadorEmissaoOrdemServico().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraNaoInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraNaoturaInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setDataUltimaAlteracao(Util.formatarData(leituraAnormalidade.getUltimaAlteracao()));

				atualizarAnormalidadeLeituraActionForm.setIndicadorUso(leituraAnormalidade.getIndicadorUso().toString());

				if(leituraAnormalidade.getIndicadorImovelSemHidrometro() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorImovelSemHidrometro(leituraAnormalidade
									.getIndicadorImovelSemHidrometro().toString());
				}

				if(leituraAnormalidade.getIndicadorSistema() != null){
					atualizarAnormalidadeLeituraActionForm.setUsoRestritoSistema(leituraAnormalidade.getIndicadorSistema().toString());
				}

				if(leituraAnormalidade.getIndicadorPerdaTarifaSocial() != null){
					atualizarAnormalidadeLeituraActionForm.setPerdaTarifaSocial(leituraAnormalidade.getIndicadorPerdaTarifaSocial()
									.toString());
				}

				if(leituraAnormalidade.getServicoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoServico(leituraAnormalidade.getServicoTipo().getId().toString());
				}else{
					atualizarAnormalidadeLeituraActionForm.setTipoServico("");
				}

				// Adição dos novos atributos

				atualizarAnormalidadeLeituraActionForm.setMensagemImpressaoConta(leituraAnormalidade.getMensagemContaLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgenteManutencao(leituraAnormalidade
								.getMensagemSugestaoManutencaoLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgentePrevencao(leituraAnormalidade
								.getMensagemSugestaoPrevencaoLeituraAnormalidade());

				if(leituraAnormalidade.getNumeroIncidenciasGeracaoOrdemServico() != null){
					atualizarAnormalidadeLeituraActionForm.setNumeroIncidenciasGeracaoOS(leituraAnormalidade
									.getNumeroIncidenciasGeracaoOrdemServico().toString());
				}

				if(leituraAnormalidade.getDocumentoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoDocumento(leituraAnormalidade.getDocumentoTipo().getId().toString());
				}

				if(leituraAnormalidade.getIndicadorAceiteLeitura() != null){
					atualizarAnormalidadeLeituraActionForm.setAceiteLeitura(leituraAnormalidade.getIndicadorAceiteLeitura().toString());
				}

				if(leituraAnormalidade.getIndicadorListagemAnormalidadeRelatorios() != null){
					atualizarAnormalidadeLeituraActionForm.setImpressaoRelatorioCriticaFiscalizacao(leituraAnormalidade
									.getIndicadorListagemAnormalidadeRelatorios().toString());
				}

				if(leituraAnormalidade.getIndicadorRetencaoConta() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorRetencaoConta(leituraAnormalidade.getIndicadorRetencaoConta()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorCreditoConsumo() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorConcessaoCreditoConsumo(leituraAnormalidade
									.getIndicadorCreditoConsumo().toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoAgua() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaAgua(leituraAnormalidade.getIndicadorIsencaoAgua()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoEsgoto() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaEsgoto(leituraAnormalidade
									.getIndicadorIsencaoEsgoto().toString());
				}

				sessao.setAttribute("leituraAnormalidadeAtualizar", leituraAnormalidade);

			}
		}

		// -------------- bt DESFAZER ---------------

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			sessao.removeAttribute("colecaoLeituraAnormalidadeTela");

			String leituraAnormalidadeID = null;

			if(sessao.getAttribute("idRegistroAtualizacao") != null && !sessao.getAttribute("idRegistroAtualizacao").equals("")){
				leituraAnormalidadeID = (String) sessao.getAttribute("idRegistroAtualizacao");
			}

			if(leituraAnormalidadeID.equalsIgnoreCase("")){
				leituraAnormalidadeID = null;
			}

			if((leituraAnormalidadeID == null) && (id == null)){

				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) sessao.getAttribute("objetoLeituraAnormalidade");

				atualizarAnormalidadeLeituraActionForm.setDescricao(leituraAnormalidade.getDescricao());

				atualizarAnormalidadeLeituraActionForm.setAbreviatura(leituraAnormalidade.getDescricaoAbreviada());

				atualizarAnormalidadeLeituraActionForm.setIndicadorRelativoHidrometro(leituraAnormalidade.getIndicadorRelativoHidrometro()
								.toString());

				atualizarAnormalidadeLeituraActionForm.setOsAutomatico(leituraAnormalidade.getIndicadorEmissaoOrdemServico().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraNaoInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraNaoturaInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setDataUltimaAlteracao(Util.formatarData(leituraAnormalidade.getUltimaAlteracao()));

				atualizarAnormalidadeLeituraActionForm.setIndicadorUso(leituraAnormalidade.getIndicadorUso().toString());

				if(leituraAnormalidade.getIndicadorImovelSemHidrometro() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorImovelSemHidrometro(leituraAnormalidade
									.getIndicadorImovelSemHidrometro().toString());
				}

				if(leituraAnormalidade.getIndicadorSistema() != null){
					atualizarAnormalidadeLeituraActionForm.setUsoRestritoSistema(leituraAnormalidade.getIndicadorSistema().toString());
				}

				if(leituraAnormalidade.getIndicadorPerdaTarifaSocial() != null){
					atualizarAnormalidadeLeituraActionForm.setPerdaTarifaSocial(leituraAnormalidade.getIndicadorPerdaTarifaSocial()
									.toString());
				}

				if(leituraAnormalidade.getServicoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoServico(leituraAnormalidade.getServicoTipo().getId().toString());
				}
				// Adição dos novos atributos

				atualizarAnormalidadeLeituraActionForm.setMensagemImpressaoConta(leituraAnormalidade.getMensagemContaLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgenteManutencao(leituraAnormalidade
								.getMensagemSugestaoManutencaoLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgentePrevencao(leituraAnormalidade
								.getMensagemSugestaoPrevencaoLeituraAnormalidade());

				if(leituraAnormalidade.getNumeroIncidenciasGeracaoOrdemServico() != null){
					atualizarAnormalidadeLeituraActionForm.setNumeroIncidenciasGeracaoOS(leituraAnormalidade
									.getNumeroIncidenciasGeracaoOrdemServico().toString());
				}

				if(leituraAnormalidade.getDocumentoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoDocumento(leituraAnormalidade.getDocumentoTipo().getId().toString());
				}

				if(leituraAnormalidade.getIndicadorAceiteLeitura() != null){
					atualizarAnormalidadeLeituraActionForm.setAceiteLeitura(leituraAnormalidade.getIndicadorAceiteLeitura().toString());
				}

				if(leituraAnormalidade.getIndicadorListagemAnormalidadeRelatorios() != null){
					atualizarAnormalidadeLeituraActionForm.setImpressaoRelatorioCriticaFiscalizacao(leituraAnormalidade
									.getIndicadorListagemAnormalidadeRelatorios().toString());
				}

				if(leituraAnormalidade.getIndicadorRetencaoConta() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorRetencaoConta(leituraAnormalidade.getIndicadorRetencaoConta()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorCreditoConsumo() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorConcessaoCreditoConsumo(leituraAnormalidade
									.getIndicadorCreditoConsumo().toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoAgua() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaAgua(leituraAnormalidade.getIndicadorIsencaoAgua()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoEsgoto() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaEsgoto(leituraAnormalidade
									.getIndicadorIsencaoEsgoto().toString());
				}

				sessao.setAttribute("leituraAnormalidadeAtualizar", leituraAnormalidade);
				sessao.removeAttribute("leituraAnormalidade");
			}

			if((idLeituraAnormalidade == null) && (id != null)){

				idLeituraAnormalidade = id;
			}

			if(idLeituraAnormalidade != null){

				FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
				filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoSemleitura");

				filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, idLeituraAnormalidade));

				Collection<LeituraAnormalidade> colecaoLeituraAnormalidade = fachada.pesquisar(filtroLeituraAnormalidade,
								LeituraAnormalidade.class.getName());

				if(colecaoLeituraAnormalidade == null || colecaoLeituraAnormalidade.isEmpty()){
					throw new ActionServletException("atencao.atualizacao.timestamp");
				}

				httpServletRequest.setAttribute("colecaoLeituraAnormalidade", colecaoLeituraAnormalidade);

				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) colecaoLeituraAnormalidade.iterator().next();

				atualizarAnormalidadeLeituraActionForm.setDescricao(leituraAnormalidade.getDescricao());

				atualizarAnormalidadeLeituraActionForm.setAbreviatura(leituraAnormalidade.getDescricaoAbreviada());

				atualizarAnormalidadeLeituraActionForm.setIndicadorRelativoHidrometro(leituraAnormalidade.getIndicadorRelativoHidrometro()
								.toString());

				atualizarAnormalidadeLeituraActionForm.setOsAutomatico(leituraAnormalidade.getIndicadorEmissaoOrdemServico().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraNaoInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setConsumoLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeConsumoComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraNaoturaInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraSemleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setLeituraLeituraInformado(leituraAnormalidade
								.getLeituraAnormalidadeLeituraComleitura().getId().toString());

				atualizarAnormalidadeLeituraActionForm.setDataUltimaAlteracao(Util.formatarData(leituraAnormalidade.getUltimaAlteracao()));

				atualizarAnormalidadeLeituraActionForm.setIndicadorUso(leituraAnormalidade.getIndicadorUso().toString());

				if(leituraAnormalidade.getIndicadorImovelSemHidrometro() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorImovelSemHidrometro(leituraAnormalidade
									.getIndicadorImovelSemHidrometro().toString());
				}

				if(leituraAnormalidade.getIndicadorSistema() != null){
					atualizarAnormalidadeLeituraActionForm.setUsoRestritoSistema(leituraAnormalidade.getIndicadorSistema().toString());
				}

				if(leituraAnormalidade.getIndicadorPerdaTarifaSocial() != null){
					atualizarAnormalidadeLeituraActionForm.setPerdaTarifaSocial(leituraAnormalidade.getIndicadorPerdaTarifaSocial()
									.toString());
				}

				if(leituraAnormalidade.getServicoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoServico(leituraAnormalidade.getServicoTipo().getId().toString());
				}

				// Adição dos novos atributos

				atualizarAnormalidadeLeituraActionForm.setMensagemImpressaoConta(leituraAnormalidade.getMensagemContaLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgenteManutencao(leituraAnormalidade
								.getMensagemSugestaoManutencaoLeituraAnormalidade());
				atualizarAnormalidadeLeituraActionForm.setSugestaoAgentePrevencao(leituraAnormalidade
								.getMensagemSugestaoPrevencaoLeituraAnormalidade());

				if(leituraAnormalidade.getNumeroIncidenciasGeracaoOrdemServico() != null){
					atualizarAnormalidadeLeituraActionForm.setNumeroIncidenciasGeracaoOS(leituraAnormalidade
									.getNumeroIncidenciasGeracaoOrdemServico().toString());
				}

				if(leituraAnormalidade.getDocumentoTipo() != null){
					atualizarAnormalidadeLeituraActionForm.setTipoDocumento(leituraAnormalidade.getDocumentoTipo().getId().toString());
				}

				if(leituraAnormalidade.getIndicadorAceiteLeitura() != null){
					atualizarAnormalidadeLeituraActionForm.setAceiteLeitura(leituraAnormalidade.getIndicadorAceiteLeitura().toString());
				}

				if(leituraAnormalidade.getIndicadorListagemAnormalidadeRelatorios() != null){
					atualizarAnormalidadeLeituraActionForm.setImpressaoRelatorioCriticaFiscalizacao(leituraAnormalidade
									.getIndicadorListagemAnormalidadeRelatorios().toString());
				}

				if(leituraAnormalidade.getIndicadorRetencaoConta() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorRetencaoConta(leituraAnormalidade.getIndicadorRetencaoConta()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorCreditoConsumo() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorConcessaoCreditoConsumo(leituraAnormalidade
									.getIndicadorCreditoConsumo().toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoAgua() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaAgua(leituraAnormalidade.getIndicadorIsencaoAgua()
									.toString());
				}

				if(leituraAnormalidade.getIndicadorIsencaoEsgoto() != null){
					atualizarAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaEsgoto(leituraAnormalidade
									.getIndicadorIsencaoEsgoto().toString());
				}

				httpServletRequest.setAttribute("idLeituraAnormalidade", idLeituraAnormalidade);
				sessao.setAttribute("leituraAnormalidadeAtualizar", leituraAnormalidade);

			}
		}
		// -------------- bt DESFAZER ---------------

		httpServletRequest.setAttribute("colecaoLeituraAnormalidadeTela", sessao.getAttribute("colecaoLeituraAnormalidadeTipoValorTela"));

		return retorno;

	}

}
