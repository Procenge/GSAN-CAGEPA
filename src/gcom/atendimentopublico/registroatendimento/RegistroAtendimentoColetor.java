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