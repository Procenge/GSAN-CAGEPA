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
	 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor.
	 * 6. O sistema apresenta os dados do imóvel Condomínio.
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

		// 6.4. O sistema apresenta a lista dos clientes imóvel condomínio
		List<ClienteImovelCondominioHelper> listaClienteImovelCondominio;
		if(Util.isNaoNuloBrancoZero(form.getMatriculaImovel())){
			listaClienteImovelCondominio = fachada.pesquisarClienteImovelCondominioHelper(Integer.valueOf(form.getMatriculaImovel()),
							form.getIdTipoLigacao(),
						Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoReferenciaFaturamento()));
			httpServletRequest.setAttribute("listaClienteImovelCondominio", listaClienteImovelCondominio);

			// 6.4.6. Endereço do Imóvel <<Inclui>> [UC0085 - Obter Endereço];
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
