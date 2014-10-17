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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.relatorio.cadastro.imovel;

import java.io.Serializable;

/**
 * [UC3135] Gerar Relat�rio de Materiais Aplicados
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