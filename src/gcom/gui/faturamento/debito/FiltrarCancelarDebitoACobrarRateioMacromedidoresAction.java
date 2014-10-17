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

package gcom.gui.faturamento.debito;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtro Cancelar Debito A Cobrar Rateio Macromedidores.
 * 
 * @author Josenildo Neves
 * @date 19/08/2013
 */
public class FiltrarCancelarDebitoACobrarRateioMacromedidoresAction
				extends GcomAction {

	/**
	 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor
	 * 5. O sistema seleciona os imóveis vinculados ao imóvel condomínio (a partir da tabela IMOVEL
	 * com IMOV_IDIMOVELCONDOMINIO=IMOV_ID do imóvel condomínio).
	 * 6. O sistema apresenta os dados do imóvel Condomínio.
	 * 
	 * @author Josenildo Neves
	 * @date 19/08/2013
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirCancelarDebitoACobrarRateioMacromedidoresAction");

		FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm form = (FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		validarReferenciaFaturamento(form, fachada);

		if(Util.isNaoNuloBrancoZero(form.getMatriculaImovel())){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getMatriculaImovel()));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			if(Util.isNaoNuloBrancoZero(imovel)){
				// 6.1. Inscrição
				form.setInscricaoImovel(imovel.getInscricaoFormatada());
				// 6.2. Descrição da Situação da Ligação de Água
				form.setDescricaoLigacaoAguaSituacao(imovel.getLigacaoAguaSituacao().getDescricao());
				// 6.3. Descrição da Situação da Ligação de Esgoto
				form.setDescricaoLigacaoEsgotoSituacao(imovel.getLigacaoEsgotoSituacao().getDescricao());
			}else{
				throw new ActionServletException("atencao.imovel.inexistente");
			}

			FiltroImovel filtroImoveisVinculados = new FiltroImovel();
			filtroImoveisVinculados.adicionarParametro(new ParametroSimples(FiltroImovel.IMOVEL_CONDOMINIO_ID, form.getMatriculaImovel()));
			
			httpServletRequest.setAttribute("filtroImoveisVinculados", filtroImoveisVinculados);
			
		}

		return retorno;
	}

	/**
	 * [FS0003] - Validar mês e ano de referência
	 * [FS0007] - Compara mês e ano de referência com data atual
	 * [FS0004] - Verifica ano e mês do faturamento
	 * 
	 * @param form
	 * @param fachada
	 * @throws ActionServletException
	 * @throws NumberFormatException
	 */
	private void validarReferenciaFaturamento(FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm form, Fachada fachada)
					throws ActionServletException, NumberFormatException{

		// [FS0003] - Validar mês e ano de referência
		if(Util.isVazioOuBranco(form.getMesAnoReferenciaFaturamento()) || !Util.validarMesAno(form.getMesAnoReferenciaFaturamento())){
			throw new ActionServletException("atencao.mes.ano.invalido");
		}

		// [FS0007] - Compara mês e ano de referência com data atual
		Date data = Util.converteStringParaDate("01/" + form.getMesAnoReferenciaFaturamento(), false);
		if(Util.compararDiaMesAno(data, new Date()) == ConstantesSistema.ZERO.intValue()){
			throw new ActionServletException("atencao.mes.ano.faturamento.maior.data.atual");
		}

		// [FS0004] - Verifica ano e mês do faturamento
		String anoMesReferencia = Util.formatarMesAnoParaAnoMesSemBarra(form.getMesAnoReferenciaFaturamento());
		FaturamentoGrupo faturamentoGrupo = fachada.pesquisarGrupoImovel(Integer.parseInt(form.getMatriculaImovel()));
		if(Util.isNaoNuloBrancoZero(faturamentoGrupo)){
			if(Util.compararAnoMesReferencia(Integer.parseInt(anoMesReferencia), faturamentoGrupo.getAnoMesReferencia(), ">")){
				throw new ActionServletException("atencao.ano_mes.referencia.superior");
			}
		}else{
			throw new ActionServletException("atencao.nao.existe.grupo.faturamento.imovel");
		}
	}
}
