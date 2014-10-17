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

package gcom.relatorio.gerencial.micromedicao;

import gcom.relatorio.RelatorioBean;

public class RelatorioResumoAnormalidadeLeituraBean
				implements RelatorioBean {

	// Campos Genericos que serão usado para quebra!.
	private Integer idCampoQuebra;

	private String descricaoCampoQuebra;

	// Fim campos genericos

	private String tipoLigacao;

	private String codigoAnormalidade;

	private String descricaoAnormalidade;

	private String qtdeLigacoes;

	private String percentual;

	/**
	 * Construtor padrão com todos os campos.
	 * 
	 * @param idCampoQuebra
	 * @param descricaoCampoQuebra
	 * @param tipoLigacao
	 * @param codigoAnormalidade
	 * @param descricaoAnormalidade
	 * @param qtdeLigacoes
	 * @param percentual
	 */
	public RelatorioResumoAnormalidadeLeituraBean(Integer idCampoQuebra, String descricaoCampoQuebra, String tipoLigacao,
													String codigoAnormalidade, String descricaoAnormalidade, String qtdeLigacoes,
													String percentual) {

		super();
		this.idCampoQuebra = idCampoQuebra;
		this.descricaoCampoQuebra = descricaoCampoQuebra;
		this.tipoLigacao = tipoLigacao;
		this.codigoAnormalidade = codigoAnormalidade;
		this.descricaoAnormalidade = descricaoAnormalidade;
		this.qtdeLigacoes = qtdeLigacoes;
		this.percentual = percentual;
	}

	/**
	 * Construtor default.
	 */
	public RelatorioResumoAnormalidadeLeituraBean() {

	}

	/**
	 * @return the idCampoQuebra
	 */
	public Integer getIdCampoQuebra(){

		return idCampoQuebra;
	}

	/**
	 * @param idCampoQuebra
	 *            the idCampoQuebra to set
	 */
	public void setIdCampoQuebra(Integer idCampoQuebra){

		this.idCampoQuebra = idCampoQuebra;
	}

	/**
	 * @return the descricaoCampoQuebra
	 */
	public String getDescricaoCampoQuebra(){

		return descricaoCampoQuebra;
	}

	/**
	 * @param descricaoCampoQuebra
	 *            the descricaoCampoQuebra to set
	 */
	public void setDescricaoCampoQuebra(String descricaoCampoQuebra){

		this.descricaoCampoQuebra = descricaoCampoQuebra;
	}

	/**
	 * @return the tipoLigacao
	 */
	public String getTipoLigacao(){

		return tipoLigacao;
	}

	/**
	 * @param tipoLigacao
	 *            the tipoLigacao to set
	 */
	public void setTipoLigacao(String tipoLigacao){

		this.tipoLigacao = tipoLigacao;
	}

	/**
	 * @return the codigoAnormalidade
	 */
	public String getCodigoAnormalidade(){

		return codigoAnormalidade;
	}

	/**
	 * @param codigoAnormalidade
	 *            the codigoAnormalidade to set
	 */
	public void setCodigoAnormalidade(String codigoAnormalidade){

		this.codigoAnormalidade = codigoAnormalidade;
	}

	/**
	 * @return the descricaoAnormalidade
	 */
	public String getDescricaoAnormalidade(){

		return descricaoAnormalidade;
	}

	/**
	 * @param descricaoAnormalidade
	 *            the descricaoAnormalidade to set
	 */
	public void setDescricaoAnormalidade(String descricaoAnormalidade){

		this.descricaoAnormalidade = descricaoAnormalidade;
	}

	/**
	 * @return the qtdeLigacoes
	 */
	public String getQtdeLigacoes(){

		return qtdeLigacoes;
	}

	/**
	 * @param qtdeLigacoes
	 *            the qtdeLigacoes to set
	 */
	public void setQtdeLigacoes(String qtdeLigacoes){

		this.qtdeLigacoes = qtdeLigacoes;
	}

	/**
	 * @return the percentual
	 */
	public String getPercentual(){

		return percentual;
	}

	/**
	 * @param percentual
	 *            the percentual to set
	 */
	public void setPercentual(String percentual){

		this.percentual = percentual;
	}
}
