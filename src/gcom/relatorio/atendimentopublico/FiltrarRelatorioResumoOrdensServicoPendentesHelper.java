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

package gcom.relatorio.atendimentopublico;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
 * 
 * @author Anderson Italo
 * @date 07/06/2011
 */
public class FiltrarRelatorioResumoOrdensServicoPendentesHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idGerenciaRegional;

	private Integer idUnidadeNegocio;

	private Integer idLocalidadeInicial;

	private Integer codigoSetorComercialInicial;

	private Integer idLocalidadeFinal;

	private Integer codigoSetorComercialFinal;

	private Date dataGeracaoInicial;

	private Date dataGeracaoFinal;

	private Collection<Integer> servicoTipo;

	public FiltrarRelatorioResumoOrdensServicoPendentesHelper() {

	}

	public Collection<Integer> getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(Collection<Integer> servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(Integer idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public Integer getIdLocalidadeInicial(){

		return idLocalidadeInicial;
	}

	public void setIdLocalidadeInicial(Integer idLocalidadeInicial){

		this.idLocalidadeInicial = idLocalidadeInicial;
	}

	public Integer getCodigoSetorComercialInicial(){

		return codigoSetorComercialInicial;
	}

	public void setCodigoSetorComercialInicial(Integer codigoSetorComercialInicial){

		this.codigoSetorComercialInicial = codigoSetorComercialInicial;
	}

	public Integer getIdLocalidadeFinal(){

		return idLocalidadeFinal;
	}

	public void setIdLocalidadeFinal(Integer idLocalidadeFinal){

		this.idLocalidadeFinal = idLocalidadeFinal;
	}

	public Integer getCodigoSetorComercialFinal(){

		return codigoSetorComercialFinal;
	}

	public void setCodigoSetorComercialFinal(Integer codigoSetorComercialFinal){

		this.codigoSetorComercialFinal = codigoSetorComercialFinal;
	}

	public Date getDataGeracaoInicial(){

		return dataGeracaoInicial;
	}

	public void setDataGeracaoInicial(Date dataGeracaoInicial){

		this.dataGeracaoInicial = dataGeracaoInicial;
	}

	public Date getDataGeracaoFinal(){

		return dataGeracaoFinal;
	}

	public void setDataGeracaoFinal(Date dataGeracaoFinal){

		this.dataGeracaoFinal = dataGeracaoFinal;
	}

}