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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimento;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.arrecadacao.ParametroArrecadacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre- processamento para gerar movimento de d�bito autom�tico para o banco
 * 
 * @author S�vio Luiz
 * @date 17/04/2006
 */
public class ExibirGerarMovimentoDebitoAutomaticoBancoAction
				extends GcomAction {

	@SuppressWarnings("unused")
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("gerarMovimentoDebitoAutomatico");

		GerarMovimentoDebitoAutomaticoBancoActionForm gerarMovimentoDebitoAutomaticoBancoActionForm = (GerarMovimentoDebitoAutomaticoBancoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		Boolean habilitarCampoDebitoAutomaticoPrefeitura;
		Boolean habilitarCampoGrupoFaturamento;
		Boolean habilitarCampoReferencia;

		Integer campoDebitoAutomaticoPrefeitura = null;
		Integer campoGrupoFaturamento = null;
		Integer campoReferencia = null;

		try{
			campoDebitoAutomaticoPrefeitura = Util.obterInteger(ParametroArrecadacao.P_GERAR_DEBITO_AUTOMATICO_PREFEITURA.executar());
			campoGrupoFaturamento = Util.obterInteger(ParametroArrecadacao.P_GERAR_DEBITO_AUTOMATICO_POR_GRUPO.executar());
			campoReferencia = Util.obterInteger(ParametroArrecadacao.P_GERAR_DEBITO_AUTOMATICO_POR_REFERENCIA.executar());
		}catch(ControladorException e){
			e.printStackTrace();
		}

		if(campoDebitoAutomaticoPrefeitura == null){
			throw new ActionServletException("atencao.geracao_debito_automatico_prefeitura_sem_parametrizacao");
		}else{
			if(campoDebitoAutomaticoPrefeitura == 1){
				habilitarCampoDebitoAutomaticoPrefeitura = true;
			}else if(campoDebitoAutomaticoPrefeitura == 2){
				habilitarCampoDebitoAutomaticoPrefeitura = false;
			}else{
				throw new ActionServletException("atencao.parametro_debito_automatico_prefeitura_invalido");
			}
		}

		if(campoGrupoFaturamento == null){
			throw new ActionServletException("atencao.geracao_debito_automatico_grupo_faturamento_sem_parametrizacao");
		}else{
			if(campoGrupoFaturamento == 1){
				habilitarCampoGrupoFaturamento = true;
			}else if(campoGrupoFaturamento == 2){
				habilitarCampoGrupoFaturamento = false;
			}else{
				throw new ActionServletException("atencao.parametro_debito_automatico_grupo_faturamento_invalido");
			}
		}

		if(campoReferencia == null){
			throw new ActionServletException("atencao.geracao_debito_automatico_referencia_sem_parametrizacao");
		}else{
			if(campoReferencia == 1){
				habilitarCampoReferencia = true;
			}else if(campoReferencia == 2){
				habilitarCampoReferencia = false;
			}else{
				throw new ActionServletException("atencao.parametro_debito_automatico_referencia_invalido");
			}
		}

		gerarMovimentoDebitoAutomaticoBancoActionForm.setHabilitarCampoDebitoAutomaticoPrefeitura(habilitarCampoDebitoAutomaticoPrefeitura);
		gerarMovimentoDebitoAutomaticoBancoActionForm.setHabilitarCampoGrupoFaturamento(habilitarCampoGrupoFaturamento);
		gerarMovimentoDebitoAutomaticoBancoActionForm.setHabilitarCampoReferencia(habilitarCampoReferencia);

		if(httpServletRequest.getParameter("listarBancos") != null && !httpServletRequest.getParameter("listarBancos").equals("")){

			Integer anoMesReferencia = null;

			// Se informado pelo usu�rio, captura o M�s/Ano Faturamento
			if(!Util.isVazioOuBranco(gerarMovimentoDebitoAutomaticoBancoActionForm.getMesAnoFaturamento())){

				boolean mesAnoValido = Util.validarMesAno(gerarMovimentoDebitoAutomaticoBancoActionForm.getMesAnoFaturamento());
				if(!mesAnoValido){
					throw new ActionServletException("atencao.ano_mes.invalido");
				}
				int ano = Integer.parseInt(gerarMovimentoDebitoAutomaticoBancoActionForm.getMesAnoFaturamento().substring(3, 7));
				if(ano < 2005){
					throw new ActionServletException("atencao.movimento.maior.2005");
				}

				anoMesReferencia = Util.formatarMesAnoComBarraParaAnoMes(gerarMovimentoDebitoAutomaticoBancoActionForm
								.getMesAnoFaturamento());
			}

			Collection colecaoIdsFaturamentoGrupo = null;

			// Se informado pelo usu�rio, captura a cole��o de grupos de faturamento selecionados
			if(httpServletRequest.getParameter("criaColecaoBanco") != null
							&& !httpServletRequest.getParameter("criaColecaoBanco").equals("")){

				/*
				 * Colocado por Raphael Rossiter em 03/07/2007 (Analista: Rosana Carvalgo)
				 * Objetivo: Selecionar um conjunto de grupos de faturamento
				 */

				// GRUPOS SELECIONADOS
				String dadosFaturamentoGrupo = httpServletRequest.getParameter("criaColecaoBanco");

				// GERANDO COLE��O DE GRUPOS
				colecaoIdsFaturamentoGrupo = this.obterGruposSelecionados(dadosFaturamentoGrupo, anoMesReferencia);

				// RETORNANDO COLE��O DE GRUPOS SELECIONADOS
				Collection colecaoFaturamentoGrupoSelecionado = new ArrayList<Object>();

				if(!Util.isVazioOrNulo(colecaoIdsFaturamentoGrupo)){

					for(Object idsFaturamentoGrupo : colecaoIdsFaturamentoGrupo){
						FiltroFaturamentoGrupo filtroFaturamentoGrupoSelecionados = new FiltroFaturamentoGrupo(
										FiltroFaturamentoGrupo.DESCRICAO);

						filtroFaturamentoGrupoSelecionados.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						filtroFaturamentoGrupoSelecionados.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID,
										idsFaturamentoGrupo));

						Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupoSelecionados,
										FaturamentoGrupo.class.getName());
						if(!Util.isVazioOrNulo(colecaoFaturamentoGrupo)){
							FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo);
							colecaoFaturamentoGrupoSelecionado.add(faturamentoGrupo);
						}
					}
					sessao.setAttribute("colecaoFaturamentoGrupoSelecionado", colecaoFaturamentoGrupoSelecionado);

					// ATUALIZA GRUPOS N�O SELECIONADOS
					FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.DESCRICAO);
					filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

					// colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo,
					// FaturamentoGrupo.class.getName());

					Collection colecaoFaturamentoGrupoDisponivelNovo = new ArrayList<Object>();
					colecaoFaturamentoGrupoDisponivelNovo.addAll(colecaoFaturamentoGrupo);

					for(Object faturamentoGrupoSelecionado : colecaoFaturamentoGrupoSelecionado){
						for(Object faturamentoGrupoDisponivel : colecaoFaturamentoGrupoDisponivelNovo){
							FaturamentoGrupo grupoDisponivel = (FaturamentoGrupo) faturamentoGrupoDisponivel;
							FaturamentoGrupo grupoSelecionado = (FaturamentoGrupo) faturamentoGrupoSelecionado;
							if(grupoDisponivel.getId().equals(grupoSelecionado.getId())){
								colecaoFaturamentoGrupo.remove(faturamentoGrupoDisponivel);
							}
						}
					}
					sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
				}

			}

			String opcaoDebitoAutomatico = gerarMovimentoDebitoAutomaticoBancoActionForm.getOpcaoMovimentoDebitoAutomatico();

			// OBTENDO OS D�BITOS AUTOM�TICOS
			Map<Banco, Collection<DebitoAutomaticoMovimento>> debitosAutomaticoBancosMap = fachada.pesquisaDebitoAutomaticoMovimento(
							colecaoIdsFaturamentoGrupo, anoMesReferencia, opcaoDebitoAutomatico);

			if(debitosAutomaticoBancosMap != null && !debitosAutomaticoBancosMap.isEmpty()){
				sessao.setAttribute("debitosAutomaticoBancosMap", debitosAutomaticoBancosMap);
			}else{
				sessao.removeAttribute("debitosAutomaticoBancosMap");
				throw new ActionServletException("atencao.nao.movimento.debito.automatico");
			}

		}

		// RESETA CAMPOS REFERENTES A "Gerar Movimento de D�bito Autom�tico"
		if(httpServletRequest.getParameter("limpaColecao") != null && !httpServletRequest.getParameter("limpaColecao").equals("")){
			sessao.removeAttribute("debitosAutomaticoBancosMap");
			sessao.removeAttribute("colecaoFaturamentoGrupoSelecionado");
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.DESCRICAO);
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}

		if(httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").equals("")){
			gerarMovimentoDebitoAutomaticoBancoActionForm.reset(actionMapping, httpServletRequest);
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.DESCRICAO);

			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}

		return retorno;
	}

	private Collection obterGruposSelecionados(String faturamentoGrupoStringBuffer, Integer anoMesReferencia){

		Collection retorno = null;

		if(faturamentoGrupoStringBuffer != null && !faturamentoGrupoStringBuffer.equalsIgnoreCase("")){

			retorno = new ArrayList();

			String[] arrayFaturamentoGrupo = faturamentoGrupoStringBuffer.split(":");
			String[] arrayGrupoEReferencia = null;

			Integer idFaturamentoGrupo = null;
			Integer anoMesFaturamentoGrupo = null;

			for(int x = 0; x < arrayFaturamentoGrupo.length; x++){

				arrayGrupoEReferencia = arrayFaturamentoGrupo[x].split(";");

				idFaturamentoGrupo = new Integer(arrayGrupoEReferencia[0]);

				if(arrayGrupoEReferencia.length > 1){
					anoMesFaturamentoGrupo = new Integer(arrayGrupoEReferencia[1]);
				}

				if((anoMesReferencia != null) && (anoMesFaturamentoGrupo != null) && (anoMesReferencia > anoMesFaturamentoGrupo)){
					throw new ActionServletException("atencao.faturamento.posterior.faturamento.grupo");
				}

				retorno.add(idFaturamentoGrupo);
			}
		}

		return retorno;
	}
}
