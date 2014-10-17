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

package gcom.cobranca;

import gcom.cadastro.imovel.Imovel;
import gcom.seguranca.acesso.usuario.Usuario;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CobrancaSituacaoHistorico
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Integer anoMesCobrancaSituacaoInicio;

	/** nullable persistent field */
	private Integer anoMesCobrancaSituacaoFim;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private gcom.cobranca.CobrancaSituacaoTipo cobrancaSituacaoTipo;

	/** persistent field */
	private gcom.cobranca.CobrancaSituacaoMotivo cobrancaSituacaoMotivo;

	/** persistent field */
	private Integer anoMesCobrancaRetirada;

	private String numeroProcesso;

	private OrgaoExterno orgaoExterno;

	/** nullable persistent field */
	private Usuario usuarioLogadoInclusao;

	/** nullable persistent field */
	private Date dataHoraInclusao;

	/** nullable persistent field */
	private Usuario usuarioLogadoExclusao;

	/** nullable persistent field */
	private Date dataHoraExclusao;

	public String getNumeroProcesso(){

		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso){

		this.numeroProcesso = numeroProcesso;
	}

	public OrgaoExterno getOrgaoExterno(){

		return orgaoExterno;
	}

	public void setOrgaoExterno(OrgaoExterno orgaoExterno){

		this.orgaoExterno = orgaoExterno;
	}

	/**
	 * @return Retorna o campo anoMesCobrancaRetirada.
	 */
	public Integer getAnoMesCobrancaRetirada(){

		return anoMesCobrancaRetirada;
	}

	/** full constructor */
	public CobrancaSituacaoHistorico(Integer anoMesCobrancaSituacaoInicio, Integer anoMesCobrancaSituacaoFim,
										Imovel imovel, gcom.cobranca.CobrancaSituacaoTipo cobrancaSituacaoTipo,
										gcom.cobranca.CobrancaSituacaoMotivo cobrancaSituacaoMotivo, Integer anoMesCobrancaRetirada) {

		this.anoMesCobrancaSituacaoInicio = anoMesCobrancaSituacaoInicio;
		this.anoMesCobrancaSituacaoFim = anoMesCobrancaSituacaoFim;
		this.imovel = imovel;
		this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
		this.cobrancaSituacaoMotivo = cobrancaSituacaoMotivo;
		this.anoMesCobrancaRetirada = anoMesCobrancaRetirada;
	}

	/** default constructor */
	public CobrancaSituacaoHistorico() {

	}

	/** minimal constructor */
	public CobrancaSituacaoHistorico(Integer anoMesCobrancaSituacaoInicio, Imovel imovel,
										gcom.cobranca.CobrancaSituacaoTipo cobrancaSituacaoTipo,
										gcom.cobranca.CobrancaSituacaoMotivo cobrancaSituacaoMotivo) {

		this.anoMesCobrancaSituacaoInicio = anoMesCobrancaSituacaoInicio;
		this.imovel = imovel;
		this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
		this.cobrancaSituacaoMotivo = cobrancaSituacaoMotivo;
	}

	public CobrancaSituacaoHistorico(Integer anoMesCobrancaSituacaoInicio, Integer anoMesCobrancaSituacaoFim, Imovel imovel,
										CobrancaSituacaoTipo cobrancaSituacaoTipo, CobrancaSituacaoMotivo cobrancaSituacaoMotivo,
										Integer anoMesCobrancaRetirada, Usuario usuarioLogadoInclusao,
										Date dataHoraInclusao, Usuario usuarioLogadoExclusao, Date dataHoraExclusao) {

		this.anoMesCobrancaSituacaoInicio = anoMesCobrancaSituacaoInicio;
		this.anoMesCobrancaSituacaoFim = anoMesCobrancaSituacaoFim;
		this.imovel = imovel;
		this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
		this.cobrancaSituacaoMotivo = cobrancaSituacaoMotivo;
		this.anoMesCobrancaRetirada = anoMesCobrancaRetirada;
		this.usuarioLogadoInclusao = usuarioLogadoInclusao;
		this.dataHoraInclusao = dataHoraInclusao;
		this.usuarioLogadoExclusao = usuarioLogadoExclusao;
		this.dataHoraExclusao = dataHoraExclusao;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public gcom.cobranca.CobrancaSituacaoTipo getCobrancaSituacaoTipo(){

		return this.cobrancaSituacaoTipo;
	}

	public void setCobrancaSituacaoTipo(gcom.cobranca.CobrancaSituacaoTipo cobrancaSituacaoTipo){

		this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
	}

	public gcom.cobranca.CobrancaSituacaoMotivo getCobrancaSituacaoMotivo(){

		return this.cobrancaSituacaoMotivo;
	}

	public void setCobrancaSituacaoMotivo(gcom.cobranca.CobrancaSituacaoMotivo cobrancaSituacaoMotivo){

		this.cobrancaSituacaoMotivo = cobrancaSituacaoMotivo;
	}

	/**
	 * @param anoMesCobrancaRetirada
	 *            O anoMesCobrancaRetirada a ser setado.
	 */
	public void setAnoMesCobrancaRetirada(Integer anoMesCobrancaRetirada){

		this.anoMesCobrancaRetirada = anoMesCobrancaRetirada;
	}

	/**
	 * @return Retorna o campo anoMesCobrancaSituacaoFim.
	 */
	public Integer getAnoMesCobrancaSituacaoFim(){

		return anoMesCobrancaSituacaoFim;
	}

	/**
	 * @param anoMesCobrancaSituacaoFim
	 *            O anoMesCobrancaSituacaoFim a ser setado.
	 */
	public void setAnoMesCobrancaSituacaoFim(Integer anoMesCobrancaSituacaoFim){

		this.anoMesCobrancaSituacaoFim = anoMesCobrancaSituacaoFim;
	}

	/**
	 * @return Retorna o campo anoMesCobrancaSituacaoInicio.
	 */
	public Integer getAnoMesCobrancaSituacaoInicio(){

		return anoMesCobrancaSituacaoInicio;
	}

	/**
	 * @param anoMesCobrancaSituacaoInicio
	 *            O anoMesCobrancaSituacaoInicio a ser setado.
	 */
	public void setAnoMesCobrancaSituacaoInicio(Integer anoMesCobrancaSituacaoInicio){

		this.anoMesCobrancaSituacaoInicio = anoMesCobrancaSituacaoInicio;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Usuario getUsuarioLogadoInclusao(){

		return usuarioLogadoInclusao;
	}

	public void setUsuarioLogadoInclusao(Usuario usuarioLogadoInclusao){

		this.usuarioLogadoInclusao = usuarioLogadoInclusao;
	}

	public Date getDataHoraInclusao(){

		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(Date dataHoraInclusao){

		this.dataHoraInclusao = dataHoraInclusao;
	}

	public Usuario getUsuarioLogadoExclusao(){

		return usuarioLogadoExclusao;
	}

	public void setUsuarioLogadoExclusao(Usuario usuarioLogadoExclusao){

		this.usuarioLogadoExclusao = usuarioLogadoExclusao;
	}

	public Date getDataHoraExclusao(){

		return dataHoraExclusao;
	}

	public void setDataHoraExclusao(Date dataHoraExclusao){

		this.dataHoraExclusao = dataHoraExclusao;
	}

}
