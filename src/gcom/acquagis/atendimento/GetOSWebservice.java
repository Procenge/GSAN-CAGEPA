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
 * 
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

package gcom.acquagis.atendimento;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.atendimentopublico.ordemservico.bean.FiltrarOrdemServicoHelper;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Classe a ser utilizada para retornar dados de uma ou mais OS's
 * 
 * @author Andre.Lopes
 */
public class GetOSWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice#preencherJSONBody(org.apache.struts
	 * .action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws NegocioException{

		String dataInicial = recuperarParametroStringObrigatorio("dataInicial", "Data Inicial", false, request);
		String dataFinal = recuperarParametroStringObrigatorio("dataFinal", "Data Final", false, request);

		Integer codigoUnidadeNegocio = recuperarParametroInteiro("codigoUnidadeNegocio", "Unidade Negocio", false, false, request);
		Integer codigoUnidadeExecutora = recuperarParametroInteiro("codigoUnidadeExecutora", "Unidade Executora", false, false, request);

		Collection<Integer> situacoes   = recuperarParametroColecaoInteiro("situacoes", "Situa��es", false, false, request);
		Collection<Integer> servicos    = recuperarParametroColecaoInteiro("servicos", "Especifica��es", false, false, request);
		Collection<Integer> prioridades = recuperarParametroColecaoInteiro("prioridades", "Especifica��es", false, false, request);

		BigDecimal coordenadaNorteBaixa = recuperarParametroBigDecimal("coordenadaNorteBaixa", "Coordenada Norte Baixa", true, false,
						request);
		BigDecimal coordenadaNorteAlta = recuperarParametroBigDecimal("coordenadaNorteAlta", "Coordenada Norte Alta", true, false, request);

		BigDecimal coordenadaLesteEsquerda = recuperarParametroBigDecimal("coordenadaLesteEsquerda", "Coordenada Leste Esquerda", true,
						false, request);
		BigDecimal coordenadaLesteDireita = recuperarParametroBigDecimal("coordenadaLesteDireita", "Coordenada Leste Direita", true, false,
						request);

		FiltrarOrdemServicoHelper filtroOS = new FiltrarOrdemServicoHelper();

		filtroOS.setDataInicial(Util.converteStringSemBarraParaDate(dataInicial));
		filtroOS.setDataFinal(Util.converteStringSemBarraParaDate(dataFinal));
		
		filtroOS.setCodigoUnidadeNegocio(codigoUnidadeNegocio);
		filtroOS.setUnidadeExecutora(codigoUnidadeExecutora);
		
		filtroOS.setColecaoSituacoes(situacoes);
		filtroOS.setColecaoServicos(servicos);
		filtroOS.setColecaoPrioridades(prioridades);
		
		filtroOS.setCoordenadaNorteBaixa(coordenadaNorteBaixa);
		filtroOS.setCoordenadaNorteAlta(coordenadaNorteAlta);
		filtroOS.setCoordenadaLesteEsquerda(coordenadaLesteEsquerda);
		filtroOS.setCoordenadaLesteDireita(coordenadaLesteDireita);
		
		try{

			Collection<OrdemServicoJSONHelper> resultadoConsulta = ServiceLocator.getInstancia().getControladorOrdemServico()
							.pesquisarOrdemServicoWebService(filtroOS);

			if(resultadoConsulta.isEmpty()){
				informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.colecao_vazia"));
			}

			adicionarListaAoBody("OSs", resultadoConsulta);

		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NegocioException("atencao.erro.consultar.ra", e);
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NegocioException("atencao.erro.consultar.ra", e);
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}

}
