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
 * Thiago Silva Toscano de Brito
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
 * 
 * GSANPCG
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 * Vitor Cavalcante Hora
 */

package gcom.micromedicao.bean;

import java.io.Serializable;

public class RelatorioResumoLeituraFaturamentoImediatoHelper
				implements Serializable {

	private static final long serialVersionUID = 4711284798056314127L;

	private Integer idLocalidade;

	private String descricaoLocalidade;

	private Integer idSetorComercial;

	private String descricaoSetorComercial;

	private Integer idAnormalidade;

	private String descricaoAnormalidade;

	private Integer idLeitura;

	/**
	 * Construtor Default da classe
	 */
	public RelatorioResumoLeituraFaturamentoImediatoHelper() {

		super();
	}

	public RelatorioResumoLeituraFaturamentoImediatoHelper(Integer idLocalidade, String descricaoLocalidade, Integer idSetorComercial,
															String descricaoSetorComercial, Integer idAnormalidade,
															String descricaoAnormalidade, Integer idLeitura) {

		super();
		this.idLocalidade = idLocalidade;
		this.descricaoLocalidade = descricaoLocalidade;
		this.idSetorComercial = idSetorComercial;
		this.descricaoSetorComercial = descricaoSetorComercial;
		this.idAnormalidade = idAnormalidade;
		this.descricaoAnormalidade = descricaoAnormalidade;
		this.idLeitura = idLeitura;
	}

	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdLeitura(){

		return idLeitura;
	}

	public void setIdLeitura(Integer idLeitura){

		this.idLeitura = idLeitura;
	}

	public Integer getIdAnormalidade(){

		return idAnormalidade;
	}

	public void setIdAnormalidade(Integer idAnormalidade){

		this.idAnormalidade = idAnormalidade;
	}

	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getDescricaoAnormalidade(){

		return descricaoAnormalidade;
	}

	public void setDescricaoAnormalidade(String descricaoAnormalidade){

		this.descricaoAnormalidade = descricaoAnormalidade;
	}

}
