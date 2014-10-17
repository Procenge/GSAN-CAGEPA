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

package gcom.cobranca;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

@ControleAlteracao
public class ResolucaoDiretoriaLayout
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	private String nomeRelatorio;

	private String nomeClasse;

	private Integer quantidadeVias;

	private Integer indicadorPadrao;

	private Integer indicadorImpressaoDoisPorPagina;

	private Integer indicadorUso;

	private Date ultimaAlteracao;

	private Integer indicadorSolicitaTestemunhas;

	/**
	 * @param id
	 * @param descricao
	 * @param numeroRelatorio
	 * @param numeroClasse
	 * @param quantidadeVias
	 * @param indicadorPadrao
	 * @param indicadorImpressao
	 * @param ultimaAlteracao
	 * @param indicadorUso
	 */
	public ResolucaoDiretoriaLayout(Integer id, String descricao, String nomeRelatorio, String nomeClasse, Integer quantidadeVias,
									Integer indicadorPadrao, Integer indicadorImpressaoDoisPorPagina, Date ultimaAlteracao,
									Integer indicadorUso) {

		this.id = id;
		this.descricao = descricao;
		this.nomeRelatorio = nomeRelatorio;
		this.nomeClasse = nomeClasse;
		this.quantidadeVias = quantidadeVias;
		this.indicadorPadrao = indicadorPadrao;
		this.indicadorImpressaoDoisPorPagina = indicadorImpressaoDoisPorPagina;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorUso = indicadorUso;
	}

	public ResolucaoDiretoriaLayout() {

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
	 * @return the nomeRelatorio
	 */
	public String getNomeRelatorio(){

		return nomeRelatorio;
	}

	/**
	 * @param nomeRelatorio
	 *            the nomeRelatorio to set
	 */
	public void setNomeRelatorio(String nomeRelatorio){

		this.nomeRelatorio = nomeRelatorio;
	}

	/**
	 * @return the numeroClasse
	 */
	public String getNomeClasse(){

		return nomeClasse;
	}

	/**
	 * @param nomeClasse
	 *            the nomeClasse to set
	 */
	public void setNomeClasse(String nomeClasse){

		this.nomeClasse = nomeClasse;
	}

	/**
	 * @return the quantidadeVias
	 */
	public Integer getQuantidadeVias(){

		return quantidadeVias;
	}

	/**
	 * @param quantidadeVias
	 *            the quantidadeVias to set
	 */
	public void setQuantidadeVias(Integer quantidadeVias){

		this.quantidadeVias = quantidadeVias;
	}

	/**
	 * @return the indicadorPadrao
	 */
	public Integer getIndicadorPadrao(){

		return indicadorPadrao;
	}

	/**
	 * @param indicadorPadrao
	 *            the indicadorPadrao to set
	 */
	public void setIndicadorPadrao(Integer indicadorPadrao){

		this.indicadorPadrao = indicadorPadrao;
	}

	/**
	 * @return the indicadorImpressaoDoisPorPagina
	 */
	public Integer getIndicadorImpressaoDoisPorPagina(){

		return indicadorImpressaoDoisPorPagina;
	}

	/**
	 * @param indicadorImpressaoDoisPorPagina
	 *            the indicadorImpressaoDoisPorPagina to set
	 */
	public void setIndicadorImpressaoDoisPorPagina(Integer indicadorImpressaoDoisPorPagina){

		this.indicadorImpressaoDoisPorPagina = indicadorImpressaoDoisPorPagina;
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

		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();

		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.ID, this.getId()));

		return filtroResolucaoDiretoriaLayout;
	}
	
	@Override
	public String getDescricaoParaRegistroTransacao(){

		return this.getDescricao();
	}

	public Integer getIndicadorSolicitaTestemunhas(){

		return indicadorSolicitaTestemunhas;
	}

	public void setIndicadorSolicitaTestemunhas(Integer indicadorSolicitaTestemunhas){

		this.indicadorSolicitaTestemunhas = indicadorSolicitaTestemunhas;
	}

}
