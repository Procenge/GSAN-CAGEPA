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
 * 
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

package gcom.acquagis.atendimento;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.cadastro.unidade.UnidadeOrganizacional;
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
 * Classe a ser utilizada para retornar dados de uma ou mais RA's
 * 
 * @author Andre.Lopes
 */
public class GetRAWebservice
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

		Collection<Integer> situacoes = recuperarParametroColecaoInteiro("situacoes", "Situações", false, false, request);
		Collection<Integer> especificacoes = recuperarParametroColecaoInteiro("especificacoes", "Especificações", false, false, request);

		BigDecimal coordenadaNorteBaixa = recuperarParametroBigDecimal("coordenadaNorteBaixa", "Coordenada Norte Baixa", true, false,
						request);
		BigDecimal coordenadaNorteAlta = recuperarParametroBigDecimal("coordenadaNorteAlta", "Coordenada Norte Alta", true, false, request);

		BigDecimal coordenadaLesteEsquerda = recuperarParametroBigDecimal("coordenadaLesteEsquerda", "Coordenada Leste Esquerda", true,
						false, request);
		BigDecimal coordenadaLesteDireita = recuperarParametroBigDecimal("coordenadaLesteDireita", "Coordenada Leste Direita", true, false,
						request);

		FiltrarRegistroAtendimentoHelper filtroRA = new FiltrarRegistroAtendimentoHelper();
		
		filtroRA.setDataAtendimentoInicial(Util.converteStringSemBarraParaDate(dataInicial));
		filtroRA.setDataAtendimentoFinal(Util.converteStringSemBarraParaDate(dataFinal));
		
		filtroRA.setUnidadeAtual(new UnidadeOrganizacional());
		filtroRA.getUnidadeAtual().setId(codigoUnidadeNegocio);

		filtroRA.setUnidadeExecutora(new UnidadeOrganizacional());
		filtroRA.getUnidadeExecutora().setId(codigoUnidadeExecutora);
		
		filtroRA.setColecaoSituacoes(situacoes);
		filtroRA.setColecaoTipoSolicitacaoEspecificacao(especificacoes);
		
		filtroRA.setCoordenadaNorteBaixa(coordenadaNorteBaixa);
		filtroRA.setCoordenadaNorteAlta(coordenadaNorteAlta);
		filtroRA.setCoordenadaLesteEsquerda(coordenadaLesteEsquerda);
		filtroRA.setCoordenadaLesteDireita(coordenadaLesteDireita);
		
		try{

			Collection<RegistroAtendimentoJSONHelper> resultadoConsulta = ServiceLocator.getInstancia().getControladorRegistroAtendimento()
							.filtrarRegistroAtendimentoWebService(filtroRA);

			if(resultadoConsulta.isEmpty()){
				informarStatus(STATUS_TIPO_ALERTA,
								MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.filtrar_ra_consulta_vazia"));
			}

			adicionarListaAoBody("RAs", resultadoConsulta);

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
