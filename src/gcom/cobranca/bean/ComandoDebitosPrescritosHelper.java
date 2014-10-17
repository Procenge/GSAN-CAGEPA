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

package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * [UC1015] Relat�rio Resumo de Ordens de Servi�o Pendentes
 * 
 * @author Anderson Italo
 * @date 07/06/2011
 */
public class ComandoDebitosPrescritosHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;


	private String titulo;

	private String descricaoSolicitacao;

	private Short indicadorSimulacao;

	private Integer idGerenciaRegional;

	private Integer idUnidadeNegocio;
	
	private Integer idElo;

	private Integer idLocalidadeInicial;

	private Integer idSetorComercialInicial;

	private Integer codigoSetorComercialInicial;

	private Integer idLocalidadeFinal;

	private Integer idSetorComercialFinal;

	private Integer codigoSetorComercialFinal;

	private Integer idQuadraInicial;

	private Integer numeroQuadraInicial;

	private Integer idQuadraFinal;

	private Integer numeroQuadraFinal;

	private Integer idCliente;

	private Integer idClienteRelacaoTipo;

	private Date periodoRelacionamentoInicial;

	private Date periodoRelacionamentoFinal;

	private byte[] arquivoImoveis;

	private Collection<Integer> colecaoIdsImoveis;

	private Collection<Integer> colecaoIdsCategorias;

	private Collection<Integer> colecaoIdsSubcategorias;

	private Collection<Integer> colecaoIdsLigacaoAguaSituacao;

	private Collection<Integer> colecaoIdsLigacaoEsgotoSituacao;

	private Collection<Integer> colecaoIdsCobrancaSituacao;

	private Integer periodoReferenciaDebitoInicial;

	private Integer periodoReferenciaDebitoFinal;

	private Date periodoVencimentoDebitoInicial;

	private Date periodoVencimentoDebitoFinal;

	private Integer idUsuario;

	private Short comandoPrescricaoAutomatico;

	private Integer quantidadeImoveis;

	private Integer quantidadeContas;

	private Integer quantidadeGuias;

	private BigDecimal valorTotalContas;

	private BigDecimal valorTotalGuias;

	public ComandoDebitosPrescritosHelper() {

	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(Integer idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public Integer getIdLocalidadeInicial(){

		return idLocalidadeInicial;
	}

	public void setIdLocalidadeInicial(Integer idLocalidadeInicial){

		this.idLocalidadeInicial = idLocalidadeInicial;
	}

	public Integer getIdLocalidadeFinal(){

		return idLocalidadeFinal;
	}

	public void setIdLocalidadeFinal(Integer idLocalidadeFinal){

		this.idLocalidadeFinal = idLocalidadeFinal;
	}

	public Integer getIdSetorComercialInicial(){

		return idSetorComercialInicial;
	}

	public void setIdSetorComercialInicial(Integer idSetorComercialInicial){

		this.idSetorComercialInicial = idSetorComercialInicial;
	}

	public Integer getCodigoSetorComercialInicial(){

		return codigoSetorComercialInicial;
	}

	public void setCodigoSetorComercialInicial(Integer codigoSetorComercialInicial){

		this.codigoSetorComercialInicial = codigoSetorComercialInicial;
	}

	public Integer getIdSetorComercialFinal(){

		return idSetorComercialFinal;
	}

	public void setIdSetorComercialFinal(Integer idSetorComercialFinal){

		this.idSetorComercialFinal = idSetorComercialFinal;
	}

	public Integer getCodigoSetorComercialFinal(){

		return codigoSetorComercialFinal;
	}

	public void setCodigoSetorComercialFinal(Integer codigoSetorComercialFinal){

		this.codigoSetorComercialFinal = codigoSetorComercialFinal;
	}


	public Integer getIdQuadraInicial(){

		return idQuadraInicial;
	}


	public void setIdQuadraInicial(Integer idQuadraInicial){

		this.idQuadraInicial = idQuadraInicial;
	}

	public Integer getNumeroQuadraInicial(){

		return numeroQuadraInicial;
	}

	public void setNumeroQuadraInicial(Integer numeroQuadraInicial){

		this.numeroQuadraInicial = numeroQuadraInicial;
	}


	public Integer getIdQuadraFinal(){

		return idQuadraFinal;
	}


	public void setIdQuadraFinal(Integer idQuadraFinal){

		this.idQuadraFinal = idQuadraFinal;
	}


	public Integer getNumeroQuadraFinal(){

		return numeroQuadraFinal;
	}


	public void setNumeroQuadraFinal(Integer numeroQuadraFinal){

		this.numeroQuadraFinal = numeroQuadraFinal;
	}

	public String getTitulo(){

		return titulo;
	}

	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	public String getDescricaoSolicitacao(){

		return descricaoSolicitacao;
	}

	
	public void setDescricaoSolicitacao(String descricaoSolicitacao){

		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public Short getIndicadorSimulacao(){

		return indicadorSimulacao;
	}

	public void setIndicadorSimulacao(Short indicadorSimulacao){

		this.indicadorSimulacao = indicadorSimulacao;
	}

	public Integer getIdClienteRelacaoTipo(){

		return idClienteRelacaoTipo;
	}

	public void setIdClienteRelacaoTipo(Integer idClienteRelacaoTipo){

		this.idClienteRelacaoTipo = idClienteRelacaoTipo;
	}

	public Integer getIdElo(){

		return idElo;
	}

	public void setIdElo(Integer idElo){

		this.idElo = idElo;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public Date getPeriodoRelacionamentoInicial(){

		return periodoRelacionamentoInicial;
	}

	public void setPeriodoRelacionamentoInicial(Date periodoRelacionamentoInicial){

		this.periodoRelacionamentoInicial = periodoRelacionamentoInicial;
	}

	public Date getPeriodoRelacionamentoFinal(){

		return periodoRelacionamentoFinal;
	}

	public void setPeriodoRelacionamentoFinal(Date periodoRelacionamentoFinal){

		this.periodoRelacionamentoFinal = periodoRelacionamentoFinal;
	}

	public Collection<Integer> getColecaoIdsImoveis(){

		return colecaoIdsImoveis;
	}

	public void setColecaoIdsImoveis(Collection<Integer> colecaoIdsImoveis){

		this.colecaoIdsImoveis = colecaoIdsImoveis;
	}

	public Collection<Integer> getColecaoIdsCategorias(){

		return colecaoIdsCategorias;
	}

	public void setColecaoIdsCategorias(Collection<Integer> colecaoIdsCategorias){

		this.colecaoIdsCategorias = colecaoIdsCategorias;
	}

	

	public Collection<Integer> getColecaoIdsLigacaoAguaSituacao(){

		return colecaoIdsLigacaoAguaSituacao;
	}

	public void setColecaoIdsLigacaoAguaSituacao(Collection<Integer> colecaoIdsLigacaoAguaSituacao){

		this.colecaoIdsLigacaoAguaSituacao = colecaoIdsLigacaoAguaSituacao;
	}

	public Collection<Integer> getColecaoIdsCobrancaSituacao(){

		return colecaoIdsCobrancaSituacao;
	}

	public void setColecaoIdsCobrancaSituacao(Collection<Integer> colecaoIdsCobrancaSituacao){

		this.colecaoIdsCobrancaSituacao = colecaoIdsCobrancaSituacao;
	}

	public Integer getPeriodoReferenciaDebitoInicial(){

		return periodoReferenciaDebitoInicial;
	}

	public void setPeriodoReferenciaDebitoInicial(Integer periodoReferenciaDebitoInicial){

		this.periodoReferenciaDebitoInicial = periodoReferenciaDebitoInicial;
	}

	public Integer getPeriodoReferenciaDebitoFinal(){

		return periodoReferenciaDebitoFinal;
	}

	public void setPeriodoReferenciaDebitoFinal(Integer periodoReferenciaDebitoFinal){

		this.periodoReferenciaDebitoFinal = periodoReferenciaDebitoFinal;
	}

	public byte[] getArquivoImoveis(){

		return arquivoImoveis;
	}

	public void setArquivoImoveis(byte[] arquivoImoveis){

		this.arquivoImoveis = arquivoImoveis;
	}

	public Collection<Integer> getColecaoIdsLigacaoEsgotoSituacao(){

		return colecaoIdsLigacaoEsgotoSituacao;
	}

	public void setColecaoIdsLigacaoEsgotoSituacao(Collection<Integer> colecaoIdsLigacaoEsgotoSituacao){

		this.colecaoIdsLigacaoEsgotoSituacao = colecaoIdsLigacaoEsgotoSituacao;
	}

	public Date getPeriodoVencimentoDebitoInicial(){

		return periodoVencimentoDebitoInicial;
	}

	public void setPeriodoVencimentoDebitoInicial(Date periodoVencimentoDebitoInicial){

		this.periodoVencimentoDebitoInicial = periodoVencimentoDebitoInicial;
	}

	public Date getPeriodoVencimentoDebitoFinal(){

		return periodoVencimentoDebitoFinal;
	}

	public void setPeriodoVencimentoDebitoFinal(Date periodoVencimentoDebitoFinal){

		this.periodoVencimentoDebitoFinal = periodoVencimentoDebitoFinal;
	}

	public Collection<Integer> getColecaoIdsSubcategorias(){

		return colecaoIdsSubcategorias;
	}

	public void setColecaoIdsSubcategorias(Collection<Integer> colecaoIdsSubcategorias){

		this.colecaoIdsSubcategorias = colecaoIdsSubcategorias;
	}

	public Integer getIdUsuario(){

		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario){

		this.idUsuario = idUsuario;
	}

	public Short getComandoPrescricaoAutomatico(){

		return comandoPrescricaoAutomatico;
	}

	public void setComandoPrescricaoAutomatico(Short comandoPrescricaoAutomatico){

		this.comandoPrescricaoAutomatico = comandoPrescricaoAutomatico;
	}

	public Integer getQuantidadeImoveis(){

		return quantidadeImoveis;
	}

	public void setQuantidadeImoveis(Integer quantidadeImoveis){

		this.quantidadeImoveis = quantidadeImoveis;
	}

	public Integer getQuantidadeContas(){

		return quantidadeContas;
	}

	public void setQuantidadeContas(Integer quantidadeContas){

		this.quantidadeContas = quantidadeContas;
	}

	public Integer getQuantidadeGuias(){

		return quantidadeGuias;
	}

	public void setQuantidadeGuias(Integer quantidadeGuias){

		this.quantidadeGuias = quantidadeGuias;
	}

	public BigDecimal getValorTotalContas(){

		return valorTotalContas;
	}

	public void setValorTotalContas(BigDecimal valorTotalContas){

		this.valorTotalContas = valorTotalContas;
	}

	public BigDecimal getValorTotalGuias(){

		return valorTotalGuias;
	}

	public void setValorTotalGuias(BigDecimal valorTotalGuias){

		this.valorTotalGuias = valorTotalGuias;
	}

}