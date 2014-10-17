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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cobranca.programacobranca.ProgramaCobranca;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
/**
 * @author ebandeira henrique
 * @date 29/08/2008
 *       Inclus�o do atributo ProgramaCobranca para v0.04
 */
public class CobrancaAcaoAtividadeCronograma
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer quantidadeMaximaDocumentos;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Date dataPrevista;

	/** nullable persistent field */
	private Date dataPrazoAcao;

	/** nullable persistent field */
	private Date realizacao;

	/** nullable persistent field */
	private Date comando;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Integer quantidadeDocumentos;

	/** nullable persistent field */
	private BigDecimal valorDocumentos;

	/** nullable persistent field */
	private Integer quantidadeItensCobrados;

	/** persistent field */
	private gcom.cobranca.CobrancaAcaoCronograma cobrancaAcaoCronograma;

	/** persistent field */
	private gcom.cobranca.CobrancaAtividade cobrancaAtividade;

	/** nullable persistent field */
	private gcom.cobranca.programacobranca.ProgramaCobranca programaCobranca;

	/** nullable persistent field */
	private gcom.cadastro.empresa.Empresa empresa;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronograma = new FiltroCobrancaAcaoAtividadeCronograma();
		filtroCobrancaAcaoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoCronograma");
		filtroCobrancaAcaoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividade");
		filtroCobrancaAcaoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("programaCobranca");
		filtroCobrancaAcaoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("empresa");
		filtroCobrancaAcaoAtividadeCronograma.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeCronograma.ID, this
						.getId()));

		return filtroCobrancaAcaoAtividadeCronograma;
	}

	/**
	 * full constructor
	 * 
	 * @param programaCobranca
	 *            TODO
	 * @param dataPrazoAcai
	 *            TODO
	 * @param empresa
	 *            TODO
	 */
	public CobrancaAcaoAtividadeCronograma(Date dataPrevista, Date realizacao, Date comando, Date ultimaAlteracao,
											Integer quantidadeDocumentos, BigDecimal valorDocumentos, Integer quantidadeItensCobrados,
											CobrancaAcaoCronograma cobrancaAcaoCronograma, CobrancaAtividade cobrancaAtividade,
											ProgramaCobranca programaCobranca, Date dataPrazoAcao, Empresa empresa) {

		this.dataPrevista = dataPrevista;
		this.realizacao = realizacao;
		this.comando = comando;
		this.ultimaAlteracao = ultimaAlteracao;
		this.quantidadeDocumentos = quantidadeDocumentos;
		this.valorDocumentos = valorDocumentos;
		this.quantidadeItensCobrados = quantidadeItensCobrados;
		this.cobrancaAcaoCronograma = cobrancaAcaoCronograma;
		this.cobrancaAtividade = cobrancaAtividade;
		this.programaCobranca = programaCobranca;
		this.dataPrazoAcao = dataPrazoAcao;
		this.empresa = empresa;
	}

	/**
	 * default constructor
	 * 
	 * @param id
	 */
	public CobrancaAcaoAtividadeCronograma(Integer id) {

		this.id = id;
	}

	/** minimal constructor */
	public CobrancaAcaoAtividadeCronograma(gcom.cobranca.CobrancaAcaoCronograma cobrancaAcaoCronograma,
											gcom.cobranca.CobrancaAtividade cobrancaAtividade) {

		this.cobrancaAcaoCronograma = cobrancaAcaoCronograma;
		this.cobrancaAtividade = cobrancaAtividade;
	}

	public CobrancaAcaoAtividadeCronograma() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataPrevista(){

		return this.dataPrevista;
	}

	public void setDataPrevista(Date dataPrevista){

		this.dataPrevista = dataPrevista;
	}

	public Date getRealizacao(){

		return this.realizacao;
	}

	public void setRealizacao(Date realizacao){

		this.realizacao = realizacao;
	}

	public Date getComando(){

		return this.comando;
	}

	public void setComando(Date comando){

		this.comando = comando;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getQuantidadeDocumentos(){

		return this.quantidadeDocumentos;
	}

	public void setQuantidadeDocumentos(Integer quantidadeDocumentos){

		this.quantidadeDocumentos = quantidadeDocumentos;
	}

	public BigDecimal getValorDocumentos(){

		return this.valorDocumentos;
	}

	public void setValorDocumentos(BigDecimal valorDocumentos){

		this.valorDocumentos = valorDocumentos;
	}

	public Integer getQuantidadeItensCobrados(){

		return this.quantidadeItensCobrados;
	}

	public void setQuantidadeItensCobrados(Integer quantidadeItensCobrados){

		this.quantidadeItensCobrados = quantidadeItensCobrados;
	}

	public gcom.cobranca.CobrancaAcaoCronograma getCobrancaAcaoCronograma(){

		return this.cobrancaAcaoCronograma;
	}

	public void setCobrancaAcaoCronograma(gcom.cobranca.CobrancaAcaoCronograma cobrancaAcaoCronograma){

		this.cobrancaAcaoCronograma = cobrancaAcaoCronograma;
	}

	public gcom.cobranca.CobrancaAtividade getCobrancaAtividade(){

		return this.cobrancaAtividade;
	}

	public void setCobrancaAtividade(gcom.cobranca.CobrancaAtividade cobrancaAtividade){

		this.cobrancaAtividade = cobrancaAtividade;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return the programaCobranca
	 */
	public gcom.cobranca.programacobranca.ProgramaCobranca getProgramaCobranca(){

		return programaCobranca;
	}

	/**
	 * @param programaCobranca
	 *            the programaCobranca to set
	 */
	public void setProgramaCobranca(gcom.cobranca.programacobranca.ProgramaCobranca programaCobranca){

		this.programaCobranca = programaCobranca;
	}

	/**
	 * @return the dataPrazoAcao
	 */
	public Date getDataPrazoAcao(){

		return dataPrazoAcao;
	}

	/**
	 * @param dataPrazoAcao
	 *            the dataPrazoAcao to set
	 */
	public void setDataPrazoAcao(Date dataPrazoAcao){

		this.dataPrazoAcao = dataPrazoAcao;
	}

	/**
	 * @return the empresa
	 */
	public gcom.cadastro.empresa.Empresa getEmpresa(){

		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(gcom.cadastro.empresa.Empresa empresa){

		this.empresa = empresa;
	}

	public void setQuantidadeMaximaDocumentos(Integer quantidadeMaximaDocumentos){

		this.quantidadeMaximaDocumentos = quantidadeMaximaDocumentos;
	}

	public Integer getQuantidadeMaximaDocumentos(){

		return quantidadeMaximaDocumentos;
	}
}
