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

package gcom.atendimentopublico.ordemservico.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Classe que irá auxiliar no filtrar de ordem de serviço
 * 
 * @author André Lopes
 * @date 13/06/2013
 */
public class FiltrarOrdemServicoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numeroOS = null;

	private Integer codigoServicoOs = null;

	private Integer codigoUnidadeNegocio = null;

	private Integer unidadeExecutora = null;

	private Date dataInicial = null;

	private Date dataFinal = null;

	private Collection<Integer> colecaoSituacoes = null;

	private Collection<Integer> colecaoServicos = null;

	private Collection<Integer> colecaoPrioridades = null;

	private BigDecimal coordenadaNorteBaixa;

	private BigDecimal coordenadaNorteAlta;

	private BigDecimal coordenadaLesteEsquerda;

	private BigDecimal coordenadaLesteDireita;

	boolean pesquisarMaxTramite = true;

	public FiltrarOrdemServicoHelper() {

	}

	public Integer getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(Integer numeroOS){

		this.numeroOS = numeroOS;
	}

	public Integer getCodigoServicoOs(){

		return codigoServicoOs;
	}

	public void setCodigoServicoOs(Integer codigoServicoOs){

		this.codigoServicoOs = codigoServicoOs;
	}

	public Integer getCodigoUnidadeNegocio(){

		return codigoUnidadeNegocio;
	}

	public void setCodigoUnidadeNegocio(Integer codigoUnidadeNegocio){

		this.codigoUnidadeNegocio = codigoUnidadeNegocio;
	}

	public Integer getUnidadeExecutora(){

		return unidadeExecutora;
	}

	public void setUnidadeExecutora(Integer unidadeExecutora){

		this.unidadeExecutora = unidadeExecutora;
	}

	public Date getDataInicial(){

		return dataInicial;
	}

	public void setDataInicial(Date dataInicial){

		this.dataInicial = dataInicial;
	}

	public Date getDataFinal(){

		return dataFinal;
	}

	public void setDataFinal(Date dataFinal){

		this.dataFinal = dataFinal;
	}

	public Collection<Integer> getColecaoSituacoes(){

		return colecaoSituacoes;
	}

	public void setColecaoSituacoes(Collection<Integer> colecaoSituacoes){

		this.colecaoSituacoes = colecaoSituacoes;
	}

	public Collection<Integer> getColecaoServicos(){

		return colecaoServicos;
	}

	public void setColecaoServicos(Collection<Integer> colecaoServicos){

		this.colecaoServicos = colecaoServicos;
	}

	public Collection<Integer> getColecaoPrioridades(){

		return colecaoPrioridades;
	}

	public void setColecaoPrioridades(Collection<Integer> colecaoPrioridades){

		this.colecaoPrioridades = colecaoPrioridades;
	}

	public BigDecimal getCoordenadaNorteBaixa(){

		return coordenadaNorteBaixa;
	}

	public void setCoordenadaNorteBaixa(BigDecimal coordenadaNorteBaixa){

		this.coordenadaNorteBaixa = coordenadaNorteBaixa;
	}

	public BigDecimal getCoordenadaNorteAlta(){

		return coordenadaNorteAlta;
	}

	public void setCoordenadaNorteAlta(BigDecimal coordenadaNorteAlta){

		this.coordenadaNorteAlta = coordenadaNorteAlta;
	}

	public BigDecimal getCoordenadaLesteEsquerda(){

		return coordenadaLesteEsquerda;
	}

	public void setCoordenadaLesteEsquerda(BigDecimal coordenadaLesteEsquerda){

		this.coordenadaLesteEsquerda = coordenadaLesteEsquerda;
	}

	public BigDecimal getCoordenadaLesteDireita(){

		return coordenadaLesteDireita;
	}

	public void setCoordenadaLesteDireita(BigDecimal coordenadaLesteDireita){

		this.coordenadaLesteDireita = coordenadaLesteDireita;
	}

	public boolean isPesquisarMaxTramite(){

		return pesquisarMaxTramite;
	}

	public void setPesquisarMaxTramite(boolean pesquisarMaxTramite){

		this.pesquisarMaxTramite = pesquisarMaxTramite;
	}

}
