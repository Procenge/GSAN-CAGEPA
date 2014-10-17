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

package gcom.gerencial.cobranca.bean;

import java.math.BigDecimal;

/**
 * Classe responsável por ajudar o caso de uso [UC0335] Gerar Resumo da Pendencia.
 * É a classe pai dos Helpers de cada tipo de pendência processada
 * 
 * @author Luciano Galvão
 * @date 05/03/2012
 */
public abstract class ResumoPendenciaGerenciaHelper
				implements Cloneable {

	public static final Integer VALOR_CURTO_PRAZO = new Integer(1);

	public static final Integer VALOR_LONGO_PRAZO = new Integer(2);

	private Integer idGerenciaRegional;

	private Integer idUnidadeNegocio;

	private Integer idElo;

	private Integer idLocalidade;

	private Integer idSetorComercial;

	private Integer idRota;

	private Integer idQuadra;

	private Integer codigoSetorComercial;

	private Integer numeroQuadra;

	private Integer idPerfilImovel;

	private Integer idSituacaoLigacaoAgua;

	private Integer idSituacaoLigacaoEsgoto;

	private Integer idPrincipalCategoriaImovel;

	private Integer idPrincipalSubCategoriaImovel;

	private Integer idEsferaPoder;

	private Integer idTipoClienteResponsavel;

	private Integer idPerfilLigacaoAgua;

	private Integer idPerfilLigacaoEsgoto;

	private Integer idHidrometro;

	private Integer idVolFixadoAgua;

	private Integer idVolFixadoEsgoto;

	private Integer idTipoDocumento;

	private Integer anoMesReferenciaDocumento;

	private Integer idValorCurtoPrazo;

	/**
	 * Construtor com os campos iniciais do objeto
	 * 
	 * @param idGerenciaRegional
	 * @param idUnidadeNegocio
	 * @param idElo
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idRota
	 * @param idQuadra
	 * @param codigoSetorComericial
	 * @param numeroQuadra
	 * @param idPerfilImovel
	 * @param idSituacaoLigacaoAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param idPerfilLigacaoAgua
	 * @param idPerfilLigacaoEsgoto
	 * @param idVolFixadoAgua
	 * @param idVolFixadoEsgoto
	 * @param idTipoDocumento
	 * @param anoMesReferenciaDocumento
	 */
	public ResumoPendenciaGerenciaHelper(Integer idGerenciaRegional, Integer idUnidadeNegocio, Integer idElo, Integer idLocalidade,
											Integer idSetorComercial, Integer idRota, Integer idQuadra, Integer codigoSetorComercial,
											Integer numeroQuadra, Integer idPerfilImovel, Integer idSituacaoLigacaoAgua,
											Integer idSituacaoLigacaoEsgoto, Integer idPerfilLigacaoAgua, Integer idPerfilLigacaoEsgoto,
											Integer idHidrometro, Integer idVolFixadoAgua, Integer idVolFixadoEsgoto,
											Integer idTipoDocumento) {

		this.idGerenciaRegional = idGerenciaRegional;
		this.idUnidadeNegocio = idUnidadeNegocio;
		this.idElo = idElo;
		this.idLocalidade = idLocalidade;
		this.idSetorComercial = idSetorComercial;
		this.idRota = idRota;
		this.idQuadra = idQuadra;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.idPerfilImovel = idPerfilImovel;
		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
		this.idPerfilLigacaoAgua = idPerfilLigacaoAgua;
		this.idPerfilLigacaoEsgoto = idPerfilLigacaoEsgoto;
		this.idHidrometro = idHidrometro;
		this.idVolFixadoAgua = idVolFixadoAgua;
		this.idVolFixadoEsgoto = idVolFixadoEsgoto;
		this.idTipoDocumento = idTipoDocumento;
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

	public Integer getIdElo(){

		return idElo;
	}

	public void setIdElo(Integer idElo){

		this.idElo = idElo;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public Integer getIdRota(){

		return idRota;
	}

	public void setIdRota(Integer idRota){

		this.idRota = idRota;
	}

	public Integer getIdQuadra(){

		return idQuadra;
	}

	public void setIdQuadra(Integer idQuadra){

		this.idQuadra = idQuadra;
	}

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Integer getIdPerfilImovel(){

		return idPerfilImovel;
	}

	public void setIdPerfilImovel(Integer idPerfilImovel){

		this.idPerfilImovel = idPerfilImovel;
	}

	public Integer getIdSituacaoLigacaoAgua(){

		return idSituacaoLigacaoAgua;
	}

	public void setIdSituacaoLigacaoAgua(Integer idSituacaoLigacaoAgua){

		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
	}

	public Integer getIdSituacaoLigacaoEsgoto(){

		return idSituacaoLigacaoEsgoto;
	}

	public void setIdSituacaoLigacaoEsgoto(Integer idSituacaoLigacaoEsgoto){

		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
	}

	public Integer getIdPrincipalCategoriaImovel(){

		return idPrincipalCategoriaImovel;
	}

	public void setIdPrincipalCategoriaImovel(Integer idPrincipalCategoriaImovel){

		this.idPrincipalCategoriaImovel = idPrincipalCategoriaImovel;
	}

	public Integer getIdPrincipalSubCategoriaImovel(){

		return idPrincipalSubCategoriaImovel;
	}

	public void setIdPrincipalSubCategoriaImovel(Integer idPrincipalSubCategoriaImovel){

		this.idPrincipalSubCategoriaImovel = idPrincipalSubCategoriaImovel;
	}

	public Integer getIdEsferaPoder(){

		return idEsferaPoder;
	}

	public void setIdEsferaPoder(Integer idEsferaPoder){

		this.idEsferaPoder = idEsferaPoder;
	}

	public Integer getIdTipoClienteResponsavel(){

		return idTipoClienteResponsavel;
	}

	public void setIdTipoClienteResponsavel(Integer idTipoClienteResponsavel){

		this.idTipoClienteResponsavel = idTipoClienteResponsavel;
	}

	public Integer getIdPerfilLigacaoAgua(){

		return idPerfilLigacaoAgua;
	}

	public void setIdPerfilLigacaoAgua(Integer idPerfilLigacaoAgua){

		this.idPerfilLigacaoAgua = idPerfilLigacaoAgua;
	}

	public Integer getIdPerfilLigacaoEsgoto(){

		return idPerfilLigacaoEsgoto;
	}

	public void setIdPerfilLigacaoEsgoto(Integer idPerfilLigacaoEsgoto){

		this.idPerfilLigacaoEsgoto = idPerfilLigacaoEsgoto;
	}

	public Integer getIdHidrometro(){

		return idHidrometro;
	}

	public void setIdHidrometro(Integer idHidrometro){

		this.idHidrometro = idHidrometro;
	}

	public Integer getIdVolFixadoAgua(){

		return idVolFixadoAgua;
	}

	public void setIdVolFixadoAgua(Integer idVolFixadoAgua){

		this.idVolFixadoAgua = idVolFixadoAgua;
	}

	public Integer getIdVolFixadoEsgoto(){

		return idVolFixadoEsgoto;
	}

	public void setIdVolFixadoEsgoto(Integer idVolFixadoEsgoto){

		this.idVolFixadoEsgoto = idVolFixadoEsgoto;
	}

	public Integer getIdTipoDocumento(){

		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento){

		this.idTipoDocumento = idTipoDocumento;
	}

	public Integer getAnoMesReferenciaDocumento(){

		return anoMesReferenciaDocumento;
	}

	public void setAnoMesReferenciaDocumento(Integer anoMesReferenciaDocumento){

		this.anoMesReferenciaDocumento = anoMesReferenciaDocumento;
	}

	public void setIdValorCurtoPrazo(Integer idValorCurtoPrazo){

		this.idValorCurtoPrazo = idValorCurtoPrazo;
	}

	public Integer getIdValorCurtoPrazo(){

		return idValorCurtoPrazo;
	}

	@Override
	public Object clone() throws CloneNotSupportedException{

		// TODO Auto-generated method stub
		return super.clone();
	}

	public ResumoPendenciaGerenciaHelper getCloneLongoPrazo(BigDecimal valorLongoPrazo) throws CloneNotSupportedException{

		// Cria um novo objeto Helper idêntico ao primeiro
		ResumoPendenciaGerenciaHelper helperLongoPrazo = (ResumoPendenciaGerenciaHelper) this.clone();

		// Seta o indicador e o valor de longo prazo
		helperLongoPrazo.setIdValorCurtoPrazo(ResumoPendenciaGuiasPagamentoGerenciaHelper.VALOR_LONGO_PRAZO);
		helperLongoPrazo.setValorLongoPrazo(valorLongoPrazo);

		return helperLongoPrazo;
	}

	/**
	 * Método abstrato. Cada classe que herda de ResumoPendenciaGerenciaHelper deve implementar este
	 * método
	 * 
	 * @param valorLongoPrazo
	 */
	public abstract void setValorLongoPrazo(BigDecimal valorLongoPrazo);

}
