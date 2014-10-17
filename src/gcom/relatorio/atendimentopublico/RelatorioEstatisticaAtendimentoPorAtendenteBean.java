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
 * Ivan Sérgio Virginio da Silva Júnior
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

import gcom.relatorio.RelatorioBean;

/**
 * [UC0732] - Gerar Relatório Estatística de Atendimento por Atendente.
 * 
 * @author isilva
 * @date 23/03/2011
 */
/**
 * @author isilva
 */
public class RelatorioEstatisticaAtendimentoPorAtendenteBean
				implements RelatorioBean {

	// dados do detalhe
	private String idUnidadeAtendimento;

	private String descricaoUnidadeAtendimento;

	private String idAtendente;

	private String nomeAtendente;

	private String quantidadePorAtendente;

	private String minutosPorAtendente;

	private String percentualPorAtendente;

	private String mediaAtendimento;

	private String quantidadePorAtendenteTotal;

	private String minutosPorAtendenteTotal;

	private String percentualPorAtendenteTotal;

	private String mediaAtendimentoTotal;

	private String imprimirUnidadeAtendimento;

	/**
	 * @return the idUnidadeAtendimento
	 */
	public String getIdUnidadeAtendimento(){

		return idUnidadeAtendimento;
	}

	/**
	 * @param idUnidadeAtendimento
	 *            the idUnidadeAtendimento to set
	 */
	public void setIdUnidadeAtendimento(String idUnidadeAtendimento){

		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	/**
	 * @return the descricaoUnidadeAtendimento
	 */
	public String getDescricaoUnidadeAtendimento(){

		return descricaoUnidadeAtendimento;
	}

	/**
	 * @param descricaoUnidadeAtendimento
	 *            the descricaoUnidadeAtendimento to set
	 */
	public void setDescricaoUnidadeAtendimento(String descricaoUnidadeAtendimento){

		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
	}

	/**
	 * @return the idAtendente
	 */
	public String getIdAtendente(){

		return idAtendente;
	}

	/**
	 * @param idAtendente
	 *            the idAtendente to set
	 */
	public void setIdAtendente(String idAtendente){

		this.idAtendente = idAtendente;
	}

	/**
	 * @return the nomeAtendente
	 */
	public String getNomeAtendente(){

		return nomeAtendente;
	}

	/**
	 * @param nomeAtendente
	 *            the nomeAtendente to set
	 */
	public void setNomeAtendente(String nomeAtendente){

		this.nomeAtendente = nomeAtendente;
	}

	/**
	 * @return the quantidadePorAtendente
	 */
	public String getQuantidadePorAtendente(){

		return quantidadePorAtendente;
	}

	/**
	 * @param quantidadePorAtendente
	 *            the quantidadePorAtendente to set
	 */
	public void setQuantidadePorAtendente(String quantidadePorAtendente){

		this.quantidadePorAtendente = quantidadePorAtendente;
	}

	/**
	 * @return the minutosPorAtendente
	 */
	public String getMinutosPorAtendente(){

		return minutosPorAtendente;
	}

	/**
	 * @param minutosPorAtendente
	 *            the minutosPorAtendente to set
	 */
	public void setMinutosPorAtendente(String minutosPorAtendente){

		this.minutosPorAtendente = minutosPorAtendente;
	}

	/**
	 * @return the percentualPorAtendente
	 */
	public String getPercentualPorAtendente(){

		return percentualPorAtendente;
	}

	/**
	 * @param percentualPorAtendente
	 *            the percentualPorAtendente to set
	 */
	public void setPercentualPorAtendente(String percentualPorAtendente){

		this.percentualPorAtendente = percentualPorAtendente;
	}

	/**
	 * @return the mediaAtendimento
	 */
	public String getMediaAtendimento(){

		return mediaAtendimento;
	}

	/**
	 * @param mediaAtendimento
	 *            the mediaAtendimento to set
	 */
	public void setMediaAtendimento(String mediaAtendimento){

		this.mediaAtendimento = mediaAtendimento;
	}

	/**
	 * @return the quantidadePorAtendenteTotal
	 */
	public String getQuantidadePorAtendenteTotal(){

		return quantidadePorAtendenteTotal;
	}

	/**
	 * @param quantidadePorAtendenteTotal
	 *            the quantidadePorAtendenteTotal to set
	 */
	public void setQuantidadePorAtendenteTotal(String quantidadePorAtendenteTotal){

		this.quantidadePorAtendenteTotal = quantidadePorAtendenteTotal;
	}

	/**
	 * @return the minutosPorAtendenteTotal
	 */
	public String getMinutosPorAtendenteTotal(){

		return minutosPorAtendenteTotal;
	}

	/**
	 * @param minutosPorAtendenteTotal
	 *            the minutosPorAtendenteTotal to set
	 */
	public void setMinutosPorAtendenteTotal(String minutosPorAtendenteTotal){

		this.minutosPorAtendenteTotal = minutosPorAtendenteTotal;
	}

	/**
	 * @return the percentualPorAtendenteTotal
	 */
	public String getPercentualPorAtendenteTotal(){

		return percentualPorAtendenteTotal;
	}

	/**
	 * @param percentualPorAtendenteTotal
	 *            the percentualPorAtendenteTotal to set
	 */
	public void setPercentualPorAtendenteTotal(String percentualPorAtendenteTotal){

		this.percentualPorAtendenteTotal = percentualPorAtendenteTotal;
	}

	/**
	 * @return the mediaAtendimentoTotal
	 */
	public String getMediaAtendimentoTotal(){

		return mediaAtendimentoTotal;
	}

	/**
	 * @param mediaAtendimentoTotal
	 *            the mediaAtendimentoTotal to set
	 */
	public void setMediaAtendimentoTotal(String mediaAtendimentoTotal){

		this.mediaAtendimentoTotal = mediaAtendimentoTotal;
	}

	/**
	 * @return the imprimirUnidadeAtendimento
	 */
	public String getImprimirUnidadeAtendimento(){

		return imprimirUnidadeAtendimento;
	}

	/**
	 * @param imprimirUnidadeAtendimento
	 *            the imprimirUnidadeAtendimento to set
	 */
	public void setImprimirUnidadeAtendimento(String imprimirUnidadeAtendimento){

		this.imprimirUnidadeAtendimento = imprimirUnidadeAtendimento;
	}
}
