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
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. 
 * Consulte a Licença Pública Geral GNU para obter mais detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;

import java.util.List;
import java.util.Map;

/**
 * Helper utilizado para manter os dados de autorização das Ordens de Serviço Associadas.
 * 
 * @author Virgínia Melo
 */
public class AutorizarServicoAssociadoHelper {

	private String caminhoAutorizacao;

	private String caminhoRetorno;

	private Map<String, Object> parametrosCaminhoRetorno;

	private Map<String, Object> parametrosCaminhoAutorizacao;

	private List<ServicoAssociadoAutorizacaoHelper> servicosParaAutorizacao;

	private Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados;

	private Map<Integer, OSEncerramentoHelper> mapaServicosAssociadosEncerrados;

	public AutorizarServicoAssociadoHelper() {

		super();
	}

	/**
	 * @param caminhoAutorizacao
	 * @param caminhoRetorno
	 * @param parametrosCaminhoRetorno
	 * @param parametrosCaminhoAutorizacao
	 * @param servicosParaAutorizacao
	 * @param mapServicosAutorizados
	 * @param mapaServicosAssociadosEncerrados
	 */
	private AutorizarServicoAssociadoHelper(String caminhoAutorizacao, String caminhoRetorno, Map<String, Object> parametrosCaminhoRetorno,
											Map<String, Object> parametrosCaminhoAutorizacao,
											List<ServicoAssociadoAutorizacaoHelper> servicosParaAutorizacao,
											Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados,
											Map<Integer, OSEncerramentoHelper> mapaServicosAssociadosEncerrados) {

		super();
		this.caminhoAutorizacao = caminhoAutorizacao;
		this.caminhoRetorno = caminhoRetorno;
		this.parametrosCaminhoRetorno = parametrosCaminhoRetorno;
		this.parametrosCaminhoAutorizacao = parametrosCaminhoAutorizacao;
		this.servicosParaAutorizacao = servicosParaAutorizacao;
		this.mapServicosAutorizados = mapServicosAutorizados;
		this.mapaServicosAssociadosEncerrados = mapaServicosAssociadosEncerrados;
	}

	/**
	 * @return the caminhoAutorizacao
	 */
	public String getCaminhoAutorizacao(){

		return caminhoAutorizacao;
	}

	/**
	 * @param caminhoAutorizacao
	 *            the caminhoAutorizacao to set
	 */
	public void setCaminhoAutorizacao(String caminhoAutorizacao){

		this.caminhoAutorizacao = caminhoAutorizacao;
	}

	/**
	 * @return the caminhoRetorno
	 */
	public String getCaminhoRetorno(){

		return caminhoRetorno;
	}

	/**
	 * @param caminhoRetorno
	 *            the caminhoRetorno to set
	 */
	public void setCaminhoRetorno(String caminhoRetorno){

		this.caminhoRetorno = caminhoRetorno;
	}

	/**
	 * @return the parametrosCaminhoRetorno
	 */
	public Map<String, Object> getParametrosCaminhoRetorno(){

		return parametrosCaminhoRetorno;
	}

	/**
	 * @param parametrosCaminhoRetorno
	 *            the parametrosCaminhoRetorno to set
	 */
	public void setParametrosCaminhoRetorno(Map<String, Object> parametrosCaminhoRetorno){

		this.parametrosCaminhoRetorno = parametrosCaminhoRetorno;
	}

	/**
	 * @return the parametrosCaminhoAutorizacao
	 */
	public Map<String, Object> getParametrosCaminhoAutorizacao(){

		return parametrosCaminhoAutorizacao;
	}

	/**
	 * @param parametrosCaminhoAutorizacao
	 *            the parametrosCaminhoAutorizacao to set
	 */
	public void setParametrosCaminhoAutorizacao(Map<String, Object> parametrosCaminhoAutorizacao){

		this.parametrosCaminhoAutorizacao = parametrosCaminhoAutorizacao;
	}

	/**
	 * @return the servicosParaAutorizacao
	 */
	public List<ServicoAssociadoAutorizacaoHelper> getServicosParaAutorizacao(){

		return servicosParaAutorizacao;
	}

	/**
	 * @param servicosParaAutorizacao
	 *            the servicosParaAutorizacao to set
	 */
	public void setServicosParaAutorizacao(List<ServicoAssociadoAutorizacaoHelper> servicosParaAutorizacao){

		this.servicosParaAutorizacao = servicosParaAutorizacao;
	}

	/**
	 * @return the mapServicosAutorizados
	 */
	public Map<Integer, ServicoAssociadoAutorizacaoHelper> getMapServicosAutorizados(){

		return mapServicosAutorizados;
	}

	/**
	 * @param mapServicosAutorizados
	 *            the mapServicosAutorizados to set
	 */
	public void setMapServicosAutorizados(Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados){

		this.mapServicosAutorizados = mapServicosAutorizados;
	}

	/**
	 * @return the mapaServicosAssociadosEncerrados
	 */
	public Map<Integer, OSEncerramentoHelper> getMapaServicosAssociadosEncerrados(){

		return mapaServicosAssociadosEncerrados;
	}

	/**
	 * @param mapaServicosAssociadosEncerrados
	 *            the mapaServicosAssociadosEncerrados to set
	 */
	public void setMapaServicosAssociadosEncerrados(Map<Integer, OSEncerramentoHelper> mapaServicosAssociadosEncerrados){

		this.mapaServicosAssociadosEncerrados = mapaServicosAssociadosEncerrados;
	}

}
