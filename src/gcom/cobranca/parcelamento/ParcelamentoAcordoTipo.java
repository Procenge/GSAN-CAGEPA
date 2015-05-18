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

package gcom.cobranca.parcelamento;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

@ControleAlteracao
public class ParcelamentoAcordoTipo
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	private String descricaoLayoutProcurador;

	private String nomeRelatorioProcurador;

	private String nomeClasseProcurador;

	private Integer indicadorParcelamentoNormal;

	private Integer indicadorUso;

	private Date ultimaAlteracao;


	/**
	 * @param id
	 * @param descricao
	 * @param descricaoLayoutComProcurador
	 * @param nomeRelatorioComProcurador
	 * @param nomeClasseComProcurador
	 * @param descricaoLayoutSemProcurador
	 * @param nomeRelatorioSemProcurador
	 * @param nomeClasseSemProcurador
	 * @param indicadorParcelamentoNormal
	 * @param ultimaAlteracao
	 * @param indicadorUso
	 */
	public ParcelamentoAcordoTipo(Integer id, String descricao, String descricaoLayoutProcurador, String nomeRelatorioProcurador,
									String nomeClasseProcurador, Integer indicadorParcelamentoNormal, Date ultimaAlteracao,
									Integer indicadorUso) {


		this.id = id;
		this.descricao = descricao;
		this.descricaoLayoutProcurador = descricaoLayoutProcurador;
		this.nomeRelatorioProcurador = nomeRelatorioProcurador;
		this.nomeClasseProcurador = nomeClasseProcurador;
		this.indicadorParcelamentoNormal = indicadorParcelamentoNormal;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorUso = indicadorUso;
	}

	public ParcelamentoAcordoTipo() {

	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return the descricaoLayoutProcurador
	 */
	public String getDescricaoLayoutProcurador(){

		return descricaoLayoutProcurador;
	}

	/**
	 * @param descricaoLayoutProcurador
	 *            the descricaoLayoutComProcurador to set
	 */
	public void setDescricaoLayoutProcurador(String descricaoLayoutProcurador){

		this.descricaoLayoutProcurador = descricaoLayoutProcurador;
	}

	/**
	 * @return the nomeRelatorioProcurador
	 */
	public String getNomeRelatorioProcurador(){

		return nomeRelatorioProcurador;
	}

	/**
	 * @param nomeRelatorioProcurador
	 *            the nomeRelatorio to set
	 */
	public void setNomeRelatorioProcurador(String nomeRelatorioProcurador){

		this.nomeRelatorioProcurador = nomeRelatorioProcurador;
	}


	/**
	 * @return the numeroClasseProcurador
	 */
	public String getNomeClasseProcurador(){

		return nomeClasseProcurador;
	}

	/**
	 * @param nomeClasseProcurador
	 *            the nomeClasseComProcurador to set
	 */
	public void setNomeClasseProcurador(String nomeClasseProcurador){

		this.nomeClasseProcurador = nomeClasseProcurador;
	}

	/**
	 * @return the indicadorParcelamentoNormal
	 */
	public Integer getIndicadorParcelamentoNormal(){

		return indicadorParcelamentoNormal;
	}

	/**
	 * @param indicadorParcelamentoNormal
	 *            the indicadorParcelamentoNormal to set
	 */
	public void setIndicadorParcelamentoNormal(Integer indicadorParcelamentoNormal){

		this.indicadorParcelamentoNormal = indicadorParcelamentoNormal;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the indicadorUso
	 */
	public Integer getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Integer indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroParcelamentoAcordoTipo filtroParcelamentoAcordoTipo = new FiltroParcelamentoAcordoTipo();

		filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.ID, this.getId()));

		return filtroParcelamentoAcordoTipo;
	}
	
	@Override
	public String getDescricaoParaRegistroTransacao(){

		return this.getDescricao();
	}

}
