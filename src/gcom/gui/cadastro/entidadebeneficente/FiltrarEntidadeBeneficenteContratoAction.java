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
 *
 ** GSANPCG
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

package gcom.gui.cadastro.entidadebeneficente;

import gcom.cadastro.imovel.FiltroEntidadeBeneficenteContrato;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarEntidadeBeneficenteContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterEntidadeBeneficenteContrato");
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarEntidadeBeneficenteContratoActionForm form = (FiltrarEntidadeBeneficenteContratoActionForm) actionForm;

		String idEntidadeBeneficente = (String) form.getIdEntidadeBeneficente();
		String numeroContrato = (String) form.getNumeroContrato();
		String dataInicialInicioContrato = (String) form.getDataInicialInicioContrato();
		String dataFinalInicioContrato = (String) form.getDataFinalInicioContrato();
		String dataInicioFimContrato = (String) form.getDataInicioFimContrato();
		String dataFinalFimContrato = (String) form.getDataFinalFimContrato();

		FiltroEntidadeBeneficenteContrato filtroEntidadeBeneficenteContrato = new FiltroEntidadeBeneficenteContrato();
		boolean peloMenosUmParametroInformado = false;
		
		filtroEntidadeBeneficenteContrato.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficenteContrato.ENTIDADE_BENEFICIENTE);
		filtroEntidadeBeneficenteContrato
						.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficenteContrato.CLIENTE_ENTIDADE_BENEFICIENTE);

		// -> Inserindo os parâmetros informados no filtro

		// Verifica se idEntidadeBeneficente foi informado.
		if(idEntidadeBeneficente != null && !idEntidadeBeneficente.trim().equalsIgnoreCase(ConstantesSistema.VALOR_NAO_INFORMADO)
						&& !idEntidadeBeneficente.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEntidadeBeneficenteContrato.adicionarParametro(new ParametroSimples(
							FiltroEntidadeBeneficenteContrato.ID_ENTIDADE_BENEFICIENTE, idEntidadeBeneficente));
		}

		// Verifica se Numero do Contrato foi informado.
		if(numeroContrato != null && !numeroContrato.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEntidadeBeneficenteContrato.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficenteContrato.NUMERO_CONTRATO,
							numeroContrato));
		}

		// Filtrando data do inicio do contrato.
		if(!checarCampoVazioNulo(dataInicialInicioContrato)){
			peloMenosUmParametroInformado = true;
			if(checarCampoVazioNulo(dataFinalInicioContrato)){
				dataFinalInicioContrato = dataInicialInicioContrato;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataInicialInicioContrato),
							Util.converteStringParaDate(dataFinalInicioContrato));

			filtroEntidadeBeneficenteContrato.adicionarParametro(new Intervalo(FiltroEntidadeBeneficenteContrato.DATA_INICIO_CONTRATO,
							dataInicialInicioContrato, dataFinalInicioContrato));

		}
		
		// Filtrando data do fim do contrato.
		if(!checarCampoVazioNulo(dataInicioFimContrato)){
			peloMenosUmParametroInformado = true;

			if(checarCampoVazioNulo(dataFinalFimContrato)){
				dataFinalFimContrato = dataInicioFimContrato;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataInicioFimContrato), Util.converteStringParaDate(dataFinalFimContrato));

			filtroEntidadeBeneficenteContrato.adicionarParametro(new Intervalo(FiltroEntidadeBeneficenteContrato.DATA_FIM_CONTRATO,
							dataInicioFimContrato, dataFinalFimContrato));

		}	

		filtroEntidadeBeneficenteContrato.setCampoOrderBy(FiltroEntidadeBeneficenteContrato.NOME_CLIENTE_ENTIDADE_BENEFICIENTE);
		filtroEntidadeBeneficenteContrato.setCampoOrderBy(FiltroEntidadeBeneficenteContrato.DATA_FIM_CONTRATO);

		// Caso o usuário não tenha informado nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar está marcado e em caso
		 * afirmativo manda pelo um request uma variável para o
		 * ExibirManterEntidadeBeneficenteAction e nele verificar se irá para o
		 * atualizar ou para o manter
		 */
		if(form.getCheckAtualizar() != null && form.getCheckAtualizar().toString().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", form.getCheckAtualizar());
		}

		// Manda o filtro pelo request para o ExibirManterEntidadeBeneficenteAction
		httpServletRequest.setAttribute("filtroEntidadeBeneficenteContrato", filtroEntidadeBeneficenteContrato); // ?
		sessao.setAttribute("filtroEntidadeBeneficenteContrato", filtroEntidadeBeneficenteContrato);

		return retorno;

	}

	private boolean checarCampoVazioNulo(String campo){

		boolean retorno = false;
		if(campo == null || campo.trim().equals("") || campo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			retorno = true;

		}
		return retorno;

	}

	private void validaComparaDatas(Date dataInicial, Date dataFinal){

		String dataInicio = Util.formatarData(dataInicial);
		String dataFim = Util.formatarData(dataFinal);

		// Se data inicio maior que data fim
		if(Util.compararData(dataInicial, dataFinal) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", dataInicio, dataFim);
		}

	}

}
