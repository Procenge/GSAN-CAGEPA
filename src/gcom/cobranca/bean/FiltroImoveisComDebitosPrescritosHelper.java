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

package gcom.cobranca.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * [UC3141] Filtrar Imóveis com Débitos Prescritos
 * 
 * @author Anderson Italo
 * @date 01/04/2014
 */
public class FiltroImoveisComDebitosPrescritosHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Short MARCADO = Short.valueOf("1");

	public static final Short DESMARCADO = Short.valueOf("2");

	public static final Short TODOS = Short.valueOf("3");

	private Integer idPrescricaoComando;

	private Integer idImovel;

	private Short indicadorSituacaoDebito;

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

	public FiltroImoveisComDebitosPrescritosHelper() {

	}

	public Integer getIdPrescricaoComando(){

		return idPrescricaoComando;
	}

	public void setIdPrescricaoComando(Integer idPrescricaoComando){

		this.idPrescricaoComando = idPrescricaoComando;
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

	
	public Short getIndicadorSituacaoDebito(){

		return indicadorSituacaoDebito;
	}

	
	public void setIndicadorSituacaoDebito(Short indicadorSituacaoDebito){

		this.indicadorSituacaoDebito = indicadorSituacaoDebito;
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

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

}