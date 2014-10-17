/**
 * 
 */
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

package gcom.gui.faturamento.debito;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.ClienteImovelCondominioHelper;
import gcom.faturamento.debito.DebitosACobrarRateioImoveisVinculadosHelper;
import gcom.faturamento.debito.HistoricoMedicaoIndividualizadaHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Tela de filtrar Cancelar Debito A Cobrar Rateio Macromedidores.
 * 
 * @author Josenildo Neves
 * @date 20/08/2013
 */
public class ExibirCancelarDebitoACobrarRateioMacromedidoresAction
				extends GcomAction {

	/**
	 * [UC3103] Cancelar D�bito a Cobrar de Rateio por Macromedidor.
	 * 6. O sistema apresenta os dados do im�vel Condom�nio.
	 * 
	 * @author Josenildo Neves
	 * @date 20/08/2013
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("cancelarDebitoACobrarRateioMacromedidores");

		FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm form = (FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroImovel filtroImovel = (FiltroImovel) httpServletRequest.getAttribute("filtroImoveisVinculados");

		Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		// 6.4. O sistema apresenta a lista dos clientes im�vel condom�nio
		List<ClienteImovelCondominioHelper> listaClienteImovelCondominio;
		if(Util.isNaoNuloBrancoZero(form.getMatriculaImovel())){
			listaClienteImovelCondominio = fachada.pesquisarClienteImovelCondominioHelper(Integer.valueOf(form.getMatriculaImovel()),
							form.getIdTipoLigacao(),
						Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoReferenciaFaturamento()));
			httpServletRequest.setAttribute("listaClienteImovelCondominio", listaClienteImovelCondominio);

			// 6.4.6. Endere�o do Im�vel <<Inclui>> [UC0085 - Obter Endere�o];
			try{
				String enderecoImovel = fachada.pesquisarEnderecoFormatado(Integer.valueOf(form.getMatriculaImovel()));
				form.setEnderecoImovel(enderecoImovel);
			}catch(ControladorException e){
				throw new ActionServletException("atencao.imovel_endereco.nao_cadastrado");
			}
			
			HistoricoMedicaoIndividualizadaHelper historicoMedicaoIndividualizadaHelper = fachada
							.pesquisarHistoricoMedicaoIndividualizadaHelper(Integer.valueOf(form.getMatriculaImovel()), form.getIdTipoLigacao(),
											Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoReferenciaFaturamento()));
			Imovel imovel;
			List<Integer> listaIdImoveis;
			if(Util.isNaoNuloBrancoZero(colecaoImovel)){
				if(Util.isNaoNuloBrancoZero(historicoMedicaoIndividualizadaHelper)){
					historicoMedicaoIndividualizadaHelper.setQuantidadeImoveisVinculados(colecaoImovel.size());
				}

				listaIdImoveis = new ArrayList<Integer>();
				for(Object object : colecaoImovel){
					imovel = (Imovel) object;
					listaIdImoveis.add(imovel.getId());
				}

				List<DebitosACobrarRateioImoveisVinculadosHelper> listaDebitosACobrarRateioImoveisVinculadosHelper = fachada
								.pesquisarDebitosACobrarRateioImoveisVinculadosHelper(listaIdImoveis, form.getIdTipoLigacao(),
												Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoReferenciaFaturamento()));
				if(listaDebitosACobrarRateioImoveisVinculadosHelper == null || listaDebitosACobrarRateioImoveisVinculadosHelper.isEmpty()){
					throw new ActionServletException("atencao.imovel.condominio.nao.possui_debito_rateio", form.getMatriculaImovel(),
									form.getMesAnoReferenciaFaturamento());
				}
				httpServletRequest.setAttribute("listaDebitosACobrarRateioImoveisVinculadosHelper",
								listaDebitosACobrarRateioImoveisVinculadosHelper);
			}

			form.setHistoricoMedicaoIndividualizadaHelper(historicoMedicaoIndividualizadaHelper);

		}else{

			throw new ActionServletException("atencao.imovel_endereco.nao_cadastrado");

		}

		return retorno;

	}

}
