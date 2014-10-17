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
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Classe a ser utilizada para retornar detalhes de uma RA's
 * 
 * @author Andre.Lopes
 */
public class GetDetalhesRAWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice#preencherJSONBody(org.apache.struts
	 * .action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws NegocioException{

		Integer numeroRA = recuperarParametroInteiroObrigatorio("numeroRA", "Número RA", true, request);

		try{

			RegistroAtendimentoJSONHelper helper = ServiceLocator.getInstancia().getControladorRegistroAtendimento()
							.buscarDetalheRAWebService(numeroRA);

			if(helper == null){
				informarStatus(STATUS_TIPO_ALERTA,
								MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.filtrar_ra_consulta_vazia"));
			}

			adicionarObjetoAoBody("RA", helper);

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
