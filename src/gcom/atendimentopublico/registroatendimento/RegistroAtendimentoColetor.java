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

package gcom.atendimentopublico.registroatendimento;

import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.Leiturista;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author isilva
 */

public class RegistroAtendimentoColetor
				extends ObjetoTransacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Imovel imovel;

	private SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao;

	private Leiturista leiturista;

	private Date dataRegistroAtendimento;

	private Date dataGeracaoRegistroAtendimento;

	private BigDecimal coordenadaLeste;

	private BigDecimal coordenadaNorte;

	private String observacao;

	private Date ultimaAlteracao;

	public RegistroAtendimentoColetor() {

	}

	public RegistroAtendimentoColetor(Integer id) {

		this.id = id;
	}

	public RegistroAtendimentoColetor(Integer id, Imovel imovel, SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao,
										Leiturista leiturista, Date dataRegistroAtendimento, Date dataGeracaoRegistroAtendimento,
										BigDecimal coordenadaLeste, BigDecimal coordenadaNorte, String observacao, Date ultimaAlteracao) {

		this.id = id;
		this.imovel = imovel;
		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
		this.leiturista = leiturista;
		this.dataRegistroAtendimento = dataRegistroAtendimento;
		this.dataGeracaoRegistroAtendimento = dataGeracaoRegistroAtendimento;
		this.coordenadaLeste = coordenadaLeste;
		this.coordenadaNorte = coordenadaNorte;
		this.observacao = observacao;
		this.ultimaAlteracao = ultimaAlteracao;
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
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the solicitacaoTipoEspecificacao
	 */
	public SolicitacaoTipoEspecificacao getSolicitacaoTipoEspecificacao(){

		return solicitacaoTipoEspecificacao;
	}

	/**
	 * @param solicitacaoTipoEspecificacao
	 *            the solicitacaoTipoEspecificacao to set
	 */
	public void setSolicitacaoTipoEspecificacao(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

	/**
	 * @return the leiturista
	 */
	public Leiturista getLeiturista(){

		return leiturista;
	}

	/**
	 * @param leiturista
	 *            the leiturista to set
	 */
	public void setLeiturista(Leiturista leiturista){

		this.leiturista = leiturista;
	}

	/**
	 * @return the dataRegistroAtendimento
	 */
	public Date getDataRegistroAtendimento(){

		return dataRegistroAtendimento;
	}

	/**
	 * @param dataRegistroAtendimento
	 *            the dataRegistroAtendimento to set
	 */
	public void setDataRegistroAtendimento(Date dataRegistroAtendimento){

		this.dataRegistroAtendimento = dataRegistroAtendimento;
	}

	/**
	 * @return the dataGeracaoRegistroAtendimento
	 */
	public Date getDataGeracaoRegistroAtendimento(){

		return dataGeracaoRegistroAtendimento;
	}

	/**
	 * @param dataGeracaoRegistroAtendimento
	 *            the dataGeracaoRegistroAtendimento to set
	 */
	public void setDataGeracaoRegistroAtendimento(Date dataGeracaoRegistroAtendimento){

		this.dataGeracaoRegistroAtendimento = dataGeracaoRegistroAtendimento;
	}

	/**
	 * @return the coordenadaLeste
	 */
	public BigDecimal getCoordenadaLeste(){

		return coordenadaLeste;
	}

	/**
	 * @param coordenadaLeste
	 *            the coordenadaLeste to set
	 */
	public void setCoordenadaLeste(BigDecimal coordenadaLeste){

		this.coordenadaLeste = coordenadaLeste;
	}

	/**
	 * @return the coordenadaNorte
	 */
	public BigDecimal getCoordenadaNorte(){

		return coordenadaNorte;
	}

	/**
	 * @param coordenadaNorte
	 *            the coordenadaNorte to set
	 */
	public void setCoordenadaNorte(BigDecimal coordenadaNorte){

		this.coordenadaNorte = coordenadaNorte;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao(){

		return observacao;
	}

	/**
	 * @param observacao
	 *            the observacao to set
	 */
	public void setObservacao(String observacao){

		this.observacao = observacao;
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

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Filtro retornaFiltro(){

		FiltroRegistroAtendimentoColetor filtroRegistroAtendimentoColetor = new FiltroRegistroAtendimentoColetor();
		filtroRegistroAtendimentoColetor.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.IMOVEL);
		filtroRegistroAtendimentoColetor
						.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.SOLICITACAO_TIPO_ESPECIFICACAO);
		filtroRegistroAtendimentoColetor.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.LEITURISTA);
		filtroRegistroAtendimentoColetor.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimentoColetor.ID, this.getId()));
		return filtroRegistroAtendimentoColetor;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}
}
