/**
 * 
 */
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
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.faturamento.conta;

import gcom.faturamento.conta.FaturaContasPreFaturadasHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite consultar as contas Pré-Faturadas
 * [UC3037] Filtrar Contas Pré-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 09/02/2012
 *          Exibir Contas Pré-Faturadas.
 */
public class FiltrarContasPreFaturadaAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultaFaturamentoContasPreFaturadas");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Objeto a ser repassado para a exibição da tela com os dados
		FaturaContasPreFaturadasHelper faturaContasPreFaturadasHelper = new FaturaContasPreFaturadasHelper();

		// Recupera as informações do formulário
		FiltrarContasPreFaturadasActionForm filtrarContasPreFaturadasActionForm = (FiltrarContasPreFaturadasActionForm) actionForm;

		String localidadeOrigemID = filtrarContasPreFaturadasActionForm.getLocalidadeOrigemID();
		String nomeLocalidadeOrigem = filtrarContasPreFaturadasActionForm.getNomeLocalidadeOrigem();
		String setorComercialOrigemCD = filtrarContasPreFaturadasActionForm.getSetorComercialOrigemCD();
		String nomeSetorComercialOrigem = filtrarContasPreFaturadasActionForm.getNomeSetorComercialOrigem();
		String localidadeDestinoID = filtrarContasPreFaturadasActionForm.getLocalidadeDestinoID();
		String nomeLocalidadeDestino = filtrarContasPreFaturadasActionForm.getNomeLocalidadeDestino();
		String setorComercialDestinoCD = filtrarContasPreFaturadasActionForm.getSetorComercialDestinoCD();
		String nomeSetorComercialDestino = filtrarContasPreFaturadasActionForm.getNomeSetorComercialDestino();
		String rotaOrigemID = filtrarContasPreFaturadasActionForm.getRotaOrigemID();
		String rotaDestinoID = filtrarContasPreFaturadasActionForm.getRotaDestinoID();
		String imovelID = filtrarContasPreFaturadasActionForm.getImovelID();
		String matriculaImovel = filtrarContasPreFaturadasActionForm.getMatriculaImovel();
		String faturamentoGrupoID = filtrarContasPreFaturadasActionForm.getFaturamentoGrupoID();
		String dataReferenciaContaInicial = filtrarContasPreFaturadasActionForm.getDataReferenciaContaInicial();
		String dataReferenciaContaFinal = filtrarContasPreFaturadasActionForm.getDataReferenciaContaFinal();
		String dataVencimentoContaInicial = filtrarContasPreFaturadasActionForm.getDataVencimentoContaInicial();
		String dataVencimentoContaFinal = filtrarContasPreFaturadasActionForm.getDataVencimentoContaFinal();
		String indicadorAtualizar = filtrarContasPreFaturadasActionForm.getIndicadorAtualizar();

		boolean peloMenosUmParametroInformado = false;

		if(!Util.isVazioOuBranco(dataReferenciaContaInicial)){
			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setDataReferenciaContaInicial(Util.formatarMesAnoComBarraParaAnoMes(dataReferenciaContaInicial));

			if(!Util.isVazioOuBranco(dataReferenciaContaFinal)){

				faturaContasPreFaturadasHelper.setDataReferenciaContaFinal(Util.formatarMesAnoComBarraParaAnoMes(dataReferenciaContaFinal));
			}else{

				faturaContasPreFaturadasHelper.setDataReferenciaContaFinal(Util
								.formatarMesAnoComBarraParaAnoMes(dataReferenciaContaInicial));
			}

			if(faturaContasPreFaturadasHelper.getDataReferenciaContaFinal().intValue() < faturaContasPreFaturadasHelper
							.getDataReferenciaContaInicial().intValue()){

				throw new ActionServletException("atencao.intervalo_referencia_invalido", "Período de Referência do Faturamento");
			}
		}

		if(!Util.isVazioOuBranco(dataVencimentoContaInicial)){

			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setDataVencimentoContaInicial(Util.converteStringParaDate(dataVencimentoContaInicial));

			if(!Util.isVazioOuBranco(dataVencimentoContaFinal)){

				faturaContasPreFaturadasHelper.setDataVencimentoContaFinal(Util.converteStringParaDate(dataVencimentoContaFinal));
			}else{

				faturaContasPreFaturadasHelper.setDataVencimentoContaFinal(Util.converteStringParaDate(dataVencimentoContaInicial));
			}

			if((dataVencimentoContaInicial.trim().length() == 10) && (dataVencimentoContaFinal.trim().length() == 10)){

				if(faturaContasPreFaturadasHelper.getDataVencimentoContaFinal().compareTo(
								faturaContasPreFaturadasHelper.getDataVencimentoContaInicial()) < 0){

					throw new ActionServletException("atencao.intervalo_data_invalido", "Período de Vencimento");
				}
			}
		}

		if(!Util.isVazioOuBranco(imovelID)){
			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setImovelID(Integer.valueOf(imovelID));
		}

		if(!Util.isVazioOuBranco(matriculaImovel)){
			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setMatriculaImovel(matriculaImovel);
		}

		if(!Util.isVazioOuBranco(faturamentoGrupoID)){
			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setFaturamentoGrupoID(Integer.valueOf(faturamentoGrupoID));
		}

		if(!Util.isVazioOuBranco(localidadeOrigemID)){

			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setLocalidadeOrigemID(Integer.valueOf(localidadeOrigemID));

			if(!Util.isVazioOuBranco(localidadeDestinoID)){

				faturaContasPreFaturadasHelper.setLocalidadeDestinoID(Integer.valueOf(localidadeDestinoID));
			}else{

				faturaContasPreFaturadasHelper.setLocalidadeDestinoID(Integer.valueOf(localidadeOrigemID));
			}

			if(!Util.isVazioOuBranco(nomeLocalidadeDestino)){

				faturaContasPreFaturadasHelper.setNomeLocalidadeDestino(nomeLocalidadeDestino);
			}

			if(!Util.isVazioOuBranco(nomeLocalidadeOrigem)){

				faturaContasPreFaturadasHelper.setNomeLocalidadeOrigem(nomeLocalidadeOrigem);
			}
		}

		if(!Util.isVazioOuBranco(setorComercialOrigemCD)){

			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setSetorComercialOrigemCD(Integer.valueOf(setorComercialOrigemCD));

			if(!Util.isVazioOuBranco(setorComercialDestinoCD)){

				faturaContasPreFaturadasHelper.setSetorComercialDestinoCD(Integer.valueOf(setorComercialDestinoCD));
			}else{

				faturaContasPreFaturadasHelper.setSetorComercialDestinoCD(Integer.valueOf(setorComercialOrigemCD));
			}

			if(!Util.isVazioOuBranco(nomeSetorComercialDestino)){

				faturaContasPreFaturadasHelper.setNomeSetorComercialDestino(nomeSetorComercialDestino);
			}

			if(!Util.isVazioOuBranco(nomeSetorComercialOrigem)){

				faturaContasPreFaturadasHelper.setNomeSetorComercialOrigem(nomeSetorComercialOrigem);
			}

		}

		if(!Util.isVazioOuBranco(rotaOrigemID)){
			peloMenosUmParametroInformado = true;
			faturaContasPreFaturadasHelper.setRotaOrigemID(Integer.valueOf(rotaOrigemID));

			if(!Util.isVazioOuBranco(rotaDestinoID)){

				faturaContasPreFaturadasHelper.setRotaDestinoID(Integer.valueOf(rotaDestinoID));
			}else{

				faturaContasPreFaturadasHelper.setRotaDestinoID(Integer.valueOf(rotaOrigemID));
			}
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("faturaContasPreFaturadasHelper", faturaContasPreFaturadasHelper);

		// Indicador Atualizar
		if(indicadorAtualizar != null && !indicadorAtualizar.equals("") && indicadorAtualizar.equals("1")){

			sessao.setAttribute("indicadorAtualizar", "1");
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}
		sessao.setAttribute("filtrarContasPreFaturadasActionForm", filtrarContasPreFaturadasActionForm);

		return retorno;
	}

}
