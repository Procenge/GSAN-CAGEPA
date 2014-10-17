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

package gcom.relatorio.cadastro.imovel;

import java.io.Serializable;

/**
 * [UC3135] Gerar Relatório de Materiais Aplicados
 * 
 * @author Felipe Rosacruz
 * @date 31/01/2014
 */
public class RelatorioMateriaisAplicadosHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idLocalidade;

	private Integer cdSetorComercial;

	private Integer idServicoTipo;

	private Integer idEquipe;

	private String tmExecucao;

	private Integer idOrdemServico;

	private String dsMaterial;

	private String qtMaterial;

	private String nmLocalidade;

	private String nmSetorComercial;

	private String dsServicoTipo;

	private String nmEquipe;

	private Integer idMaterial;

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getCdSetorComercial(){

		return cdSetorComercial;
	}

	public void setCdSetorComercial(Integer cdSetorComercial){

		this.cdSetorComercial = cdSetorComercial;
	}

	public Integer getIdServicoTipo(){

		return idServicoTipo;
	}

	public void setIdServicoTipo(Integer idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	public Integer getIdEquipe(){

		return idEquipe;
	}

	public void setIdEquipe(Integer idEquipe){

		this.idEquipe = idEquipe;
	}

	public String getTmExecucao(){

		return tmExecucao;
	}

	public void setTmExecucao(String tmExecucao){

		this.tmExecucao = tmExecucao;
	}

	public Integer getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public String getDsMaterial(){

		return dsMaterial;
	}

	public void setDsMaterial(String dsMaterial){

		this.dsMaterial = dsMaterial;
	}

	public String getQtMaterial(){

		return qtMaterial;
	}

	public void setQtMaterial(String qtMaterial){

		this.qtMaterial = qtMaterial;
	}

	public String getNmLocalidade(){

		return nmLocalidade;
	}

	public void setNmLocalidade(String nmLocalidade){

		this.nmLocalidade = nmLocalidade;
	}

	public String getNmSetorComercial(){

		return nmSetorComercial;
	}

	public void setNmSetorComercial(String nmSetorComercial){

		this.nmSetorComercial = nmSetorComercial;
	}

	public String getDsServicoTipo(){

		return dsServicoTipo;
	}

	public void setDsServicoTipo(String dsServicoTipo){

		this.dsServicoTipo = dsServicoTipo;
	}

	public String getNmEquipe(){

		return nmEquipe;
	}

	public void setNmEquipe(String nmEquipe){

		this.nmEquipe = nmEquipe;
	}

	public Integer getIdMaterial(){

		return idMaterial;
	}

	public void setIdMaterial(Integer idMaterial){

		this.idMaterial = idMaterial;
	}

}