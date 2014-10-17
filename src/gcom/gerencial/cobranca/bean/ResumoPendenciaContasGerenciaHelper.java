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
 * Classe responsável por ajudar o caso de uso [UC0335] Gerar Resumo da Pendencia
 * [SB0001A]
 * 
 * @author Bruno Barros
 * @date 19/09/2007
 */
public class ResumoPendenciaContasGerenciaHelper
				extends ResumoPendenciaGerenciaHelper {

	private Integer idReferenciaVencimentoConta;

	private Integer quantidadeLigacoes;

	private Integer quantidadeDocumentos = 1;

	private BigDecimal valorPendenteAgua = new BigDecimal(0);

	private BigDecimal valorPendenteEsgoto = new BigDecimal(0);

	private BigDecimal valorPendenteDebito = new BigDecimal(0);

	private BigDecimal valorPendenteCredito = new BigDecimal(0);

	private BigDecimal valorPendenteImposto = new BigDecimal(0);

	private Integer idTipoTarifaConsumo;

	public Integer getIdReferenciaVencimentoConta(){

		return idReferenciaVencimentoConta;
	}

	public void setIdReferenciaVencimentoConta(Integer idReferenciaVencimentoConta){

		this.idReferenciaVencimentoConta = idReferenciaVencimentoConta;
	}

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

	public BigDecimal getValorPendenteAgua(){

		return valorPendenteAgua;
	}

	public void setValorPendenteAgua(BigDecimal valorPendenteAgua){

		if(valorPendenteAgua != null){
			this.valorPendenteAgua = valorPendenteAgua;
		}
	}

	public BigDecimal getValorPendenteCredito(){

		return valorPendenteCredito;
	}

	public void setValorPendenteCredito(BigDecimal valorPendenteCredito){

		if(valorPendenteCredito != null){
			this.valorPendenteCredito = valorPendenteCredito;
		}
	}

	public BigDecimal getValorPendenteDebito(){

		return valorPendenteDebito;
	}

	public void setValorPendenteDebito(BigDecimal valorPendenteDebito){

		if(valorPendenteDebito != null){
			this.valorPendenteDebito = valorPendenteDebito;
		}
	}

	public BigDecimal getValorPendenteEsgoto(){

		return valorPendenteEsgoto;
	}

	public void setValorPendenteEsgoto(BigDecimal valorPendenteEsgoto){

		if(valorPendenteEsgoto != null){
			this.valorPendenteEsgoto = valorPendenteEsgoto;
		}
	}

	public BigDecimal getValorPendenteImposto(){

		return valorPendenteImposto;
	}

	public void setValorPendenteImposto(BigDecimal valorPendenteImposto){

		if(valorPendenteImposto != null){
			this.valorPendenteImposto = valorPendenteImposto;
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
	 * @param idReferenciaVencimentoConta
	 * @param quantidadeLigacoes
	 */
	public ResumoPendenciaContasGerenciaHelper(Integer idGerenciaRegional, Integer idUnidadeNegocio, Integer idElo, Integer idLocalidade,
												Integer idSetorComercial, Integer idRota, Integer idQuadra, Integer codigoSetorComercial,
												Integer numeroQuadra, Integer idPerfilImovel, Integer idSituacaoLigacaoAgua,
												Integer idSituacaoLigacaoEsgoto, Integer idPerfilLigacaoAgua,
												Integer idPerfilLigacaoEsgoto, Integer idHidrometro, Integer idVolFixadoAgua,
												Integer idVolFixadoEsgoto, Integer idTipoDocumento, Integer anoMesReferenciaDocumento,
												Integer idReferenciaVencimentoConta, BigDecimal valorPendenteAgua,
												BigDecimal valorPendenteEsgoto, BigDecimal valorPendenteDebito,
												BigDecimal valorPendenteCredito, BigDecimal valorPendenteImposto,
												Integer idTipoTarifaConsumo, Integer quantidadeLigacoes) {

		super(idGerenciaRegional, idUnidadeNegocio, idElo, idLocalidade, idSetorComercial, idRota, idQuadra, codigoSetorComercial,
						numeroQuadra, idPerfilImovel, idSituacaoLigacaoAgua, idSituacaoLigacaoEsgoto, idPerfilLigacaoAgua,
						idPerfilLigacaoEsgoto, idHidrometro, idVolFixadoAgua, idVolFixadoEsgoto, idTipoDocumento);

		super.setAnoMesReferenciaDocumento(anoMesReferenciaDocumento);
		this.idReferenciaVencimentoConta = idReferenciaVencimentoConta;
		this.valorPendenteAgua = (valorPendenteAgua == null ? new BigDecimal(0) : valorPendenteAgua);
		this.valorPendenteEsgoto = (valorPendenteEsgoto == null ? new BigDecimal(0) : valorPendenteEsgoto);
		this.valorPendenteDebito = (valorPendenteDebito == null ? new BigDecimal(0) : valorPendenteDebito);
		this.valorPendenteCredito = (valorPendenteCredito == null ? new BigDecimal(0) : valorPendenteCredito);
		this.valorPendenteImposto = (valorPendenteImposto == null ? new BigDecimal(0) : valorPendenteImposto);
		this.idTipoTarifaConsumo = idTipoTarifaConsumo;
		this.quantidadeLigacoes = quantidadeLigacoes;
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

		if(!(obj instanceof ResumoPendenciaContasGerenciaHelper)){
			return false;
		}else{
			ResumoPendenciaContasGerenciaHelper resumoTemp = (ResumoPendenciaContasGerenciaHelper) obj;

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
							&& propriedadesIguais(this.getAnoMesReferenciaDocumento(), resumoTemp.getAnoMesReferenciaDocumento())
							&& propriedadesIguais(this.idReferenciaVencimentoConta, resumoTemp.idReferenciaVencimentoConta)
							&& propriedadesIguais(this.idTipoTarifaConsumo, resumoTemp.idTipoTarifaConsumo); //
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

		// Este método foi implementado com corpo vazio, pois para Pendências do tipo Conta não faz
		// sentido valor de longo prazo

	}
}
