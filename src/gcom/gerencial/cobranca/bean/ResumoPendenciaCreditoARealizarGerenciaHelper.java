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

package gcom.gerencial.cobranca.bean;

import java.math.BigDecimal;

/**
 * Classe respons�vel por ajudar o caso de uso [UC0335] Gerar Resumo da Pendencia
 * [SB0004A]
 * 
 * @author Bruno Barros
 * @date 07/08/2007
 */
public class ResumoPendenciaCreditoARealizarGerenciaHelper
				extends ResumoPendenciaGerenciaHelper {

	private Integer quantidadeLigacoes = 1;

	private Integer quantidadeDocumentos = 1;

	private BigDecimal valorPendenteCredito = new BigDecimal(0);

	private Integer idTipoTarifaConsumo;

	public Integer getQuantidadeDocumentos(){

		return quantidadeDocumentos;
	}

	public void setQuantidadeDocumentos(Integer quantidadeDocumentos){

		this.quantidadeDocumentos = quantidadeDocumentos;
	}

	public Integer getQuantidadeLigacoes(){

		return quantidadeLigacoes;
	}

	public void setQuantidadeLigacoes(Integer quantidadeLigacoes){

		this.quantidadeLigacoes = quantidadeLigacoes;
	}

	public BigDecimal getValorPendenteCredito(){

		return valorPendenteCredito;
	}

	public void setValorPendenteCredito(BigDecimal valorPendenteCredito){

		if(valorPendenteCredito != null){
			this.valorPendenteCredito = valorPendenteCredito;
		}
	}

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
	public ResumoPendenciaCreditoARealizarGerenciaHelper(Integer idGerenciaRegional, // 0
															Integer idUnidadeNegocio, // 1
															Integer idElo, // 2
															Integer idLocalidade,// 3
															Integer idSetorComercial,// 4
															Integer idRota, // 5
															Integer idQuadra, // 6
															Integer codigoSetorComercial,// 7
															Integer numeroQuadra, // 8
															Integer idPerfilImovel, // 9
															Integer idSituacaoLigacaoAgua,// 10
															Integer idSituacaoLigacaoEsgoto, // 11
															Integer idPerfilLigacaoAgua, // 12
															Integer idPerfilLigacaoEsgoto, // 13
															Integer idHidrometro,// 14
															Integer idVolFixadoAgua, // 15
															Integer idVolFixadoEsgoto, // 16
															Integer idTipoDocumento, // 17
															Integer anoMesReferenciaDocumento,// 18
															BigDecimal valorPendenteCredito,// 19
															Integer idTipoTarifaConsumo) { // 20

		super(idGerenciaRegional, idUnidadeNegocio, idElo, idLocalidade, idSetorComercial, idRota, idQuadra, codigoSetorComercial,
						numeroQuadra, idPerfilImovel, idSituacaoLigacaoAgua, idSituacaoLigacaoEsgoto, idPerfilLigacaoAgua,
						idPerfilLigacaoEsgoto, idHidrometro, idVolFixadoAgua, idVolFixadoEsgoto, idTipoDocumento);

		super.setAnoMesReferenciaDocumento(anoMesReferenciaDocumento);
		this.valorPendenteCredito = (valorPendenteCredito == null ? new BigDecimal(0) : valorPendenteCredito);
		this.idTipoTarifaConsumo = idTipoTarifaConsumo;
	}

	/**
	 * Compara duas properiedades Integereiras, comparando
	 * seus valores para descobrirmos se sao iguais
	 * 
	 * @param pro1
	 *            Primeira propriedade
	 * @param pro2
	 *            Segunda propriedade
	 * @return Se iguais, retorna true
	 */
	private boolean propriedadesIguais(Integer pro1, Integer pro2){

		if(pro2 != null){
			if(!pro2.equals(pro1)){
				return false;
			}
		}else if(pro1 != null){
			return false;
		}

		// Se chegou ate aqui quer dizer que as propriedades sao iguais
		return true;
	}

	/**
	 * Compara dois objetos levando em consideracao apenas as propriedades
	 * que identificam o agrupamento
	 * 
	 * @param obj
	 *            Objeto a ser comparado com a instancia deste objeto
	 */
	public boolean equals(Object obj){

		if(!(obj instanceof ResumoPendenciaCreditoARealizarGerenciaHelper)){
			return false;
		}else{
			ResumoPendenciaCreditoARealizarGerenciaHelper resumoTemp = (ResumoPendenciaCreditoARealizarGerenciaHelper) obj;

			// Verificamos se todas as propriedades que identificam o objeto sao iguais
			return propriedadesIguais(this.getIdGerenciaRegional(), resumoTemp.getIdGerenciaRegional())
							&& propriedadesIguais(this.getIdUnidadeNegocio(), resumoTemp.getIdUnidadeNegocio())
							&& propriedadesIguais(this.getIdLocalidade(), resumoTemp.getIdLocalidade())
							&& propriedadesIguais(this.getIdElo(), resumoTemp.getIdElo())
							&& propriedadesIguais(this.getIdSetorComercial(), resumoTemp.getIdSetorComercial())
							&& propriedadesIguais(this.getIdRota(), resumoTemp.getIdRota())
							&& propriedadesIguais(this.getIdQuadra(), resumoTemp.getIdQuadra())
							&& propriedadesIguais(this.getCodigoSetorComercial(), resumoTemp.getCodigoSetorComercial())
							&& propriedadesIguais(this.getNumeroQuadra(), resumoTemp.getNumeroQuadra())
							&& propriedadesIguais(this.getIdPerfilImovel(), resumoTemp.getIdPerfilImovel())
							&& propriedadesIguais(this.getIdEsferaPoder(), resumoTemp.getIdEsferaPoder())
							&& propriedadesIguais(this.getIdTipoClienteResponsavel(), resumoTemp.getIdTipoClienteResponsavel())
							&& propriedadesIguais(this.getIdSituacaoLigacaoAgua(), resumoTemp.getIdSituacaoLigacaoAgua())
							&& propriedadesIguais(this.getIdSituacaoLigacaoEsgoto(), resumoTemp.getIdSituacaoLigacaoEsgoto())
							&& propriedadesIguais(this.getIdPrincipalCategoriaImovel(), resumoTemp.getIdPrincipalCategoriaImovel())
							&& propriedadesIguais(this.getIdPrincipalSubCategoriaImovel(), resumoTemp.getIdPrincipalSubCategoriaImovel())
							&& propriedadesIguais(this.getIdPerfilLigacaoAgua(), resumoTemp.getIdPerfilLigacaoAgua())
							&& propriedadesIguais(this.getIdPerfilLigacaoEsgoto(), resumoTemp.getIdPerfilLigacaoEsgoto())
							&& propriedadesIguais(this.getIdHidrometro(), resumoTemp.getIdHidrometro())
							&& propriedadesIguais(this.getIdVolFixadoAgua(), resumoTemp.getIdVolFixadoAgua())
							&& propriedadesIguais(this.getIdVolFixadoEsgoto(), resumoTemp.getIdVolFixadoEsgoto())
							&& propriedadesIguais(this.getIdTipoDocumento(), resumoTemp.getIdTipoDocumento())
							&& propriedadesIguais(this.getIdValorCurtoPrazo(), resumoTemp.getIdValorCurtoPrazo())
							&& propriedadesIguais(this.getAnoMesReferenciaDocumento(), resumoTemp.getAnoMesReferenciaDocumento())
							&& propriedadesIguais(this.idTipoTarifaConsumo, resumoTemp.idTipoTarifaConsumo);
		}
	}

	public Integer getIdTipoTarifaConsumo(){

		return idTipoTarifaConsumo;
	}

	public void setIdTipoTarifaConsumo(Integer idTipoTarifaConsumo){

		this.idTipoTarifaConsumo = idTipoTarifaConsumo;
	}

	@Override
	public void setValorLongoPrazo(BigDecimal valorLongoPrazo){

		// TODO Auto-generated method stub

	}
}
