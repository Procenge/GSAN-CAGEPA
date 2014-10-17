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

package gcom.relatorio.cadastro.imovel;

import gcom.relatorio.RelatorioBean;

import java.io.Serializable;

/**
 * [UC0XXX] Gerar Relat�rio de Macromedidores e Micromedidos associados com os �ltimos 36 consumos
 * ordem decrescente de refer�ncia.
 * 
 * @author P�ricles Tavares
 * @date 17/02/2011
 */
public class RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean
				implements RelatorioBean, Serializable {

	private String matriculaImovel;

	private String numeroInscricao;

	private String nomeClienteUsuario;

	private String situacaoLigacaoAgua;

	private String situacaoLigacaoEsgoto;

	private String indicadorMedido;

	private String media;

	private String macromedidor;

	private String nomeClienteMacromedidor;

	private String numeroFilhosMacromedidor;

	private String localidadeMacromedidor;

	private String setorComercialMacromedidor;

	private String numeroRotaMacromedidor;

	private String numeroSegmentoMacromedidor;

	private String numeroLoteMacromedidor;

	private String numeroSubLoteMacromedidor;

	private String referencia;

	private String leitura;

	private String dataLeitura;

	private String anormalidadeConsumo;

	private String anormalidadeLeitura;

	private String qtidadeConsumo;

	private String numeroDiasConsumo;

	private String numeroQuadraMacromedidor;

	private String codigoRota;

	private String numeroSegmento;

	public String getNumeroQuadraMacromedidor(){

		return numeroQuadraMacromedidor;
	}

	public void setNumeroQuadraMacromedidor(String numeroQuadraMacromedidor){

		this.numeroQuadraMacromedidor = numeroQuadraMacromedidor;
	}

	public String getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(String codigoRota){

		this.codigoRota = codigoRota;
	}

	public String getNumeroSegmento(){

		return numeroSegmento;
	}

	public void setNumeroSegmento(String numeroSegmento){

		this.numeroSegmento = numeroSegmento;
	}

	public String getMacromedidor(){

		return macromedidor;
	}

	public void setMacromedidor(String macromedidor){

		this.macromedidor = macromedidor;
	}

	public String getNomeClienteMacromedidor(){

		return nomeClienteMacromedidor;
	}

	public void setNomeClienteMacromedidor(String nomeClienteMacromedidor){

		this.nomeClienteMacromedidor = nomeClienteMacromedidor;
	}

	public String getNumeroFilhosMacromedidor(){

		return numeroFilhosMacromedidor;
	}

	public void setNumeroFilhosMacromedidor(String numeroFilhosMacromedidor){

		this.numeroFilhosMacromedidor = numeroFilhosMacromedidor;
	}

	public String getLocalidadeMacromedidor(){

		return localidadeMacromedidor;
	}

	public void setLocalidadeMacromedidor(String localidadeMacromedidor){

		this.localidadeMacromedidor = localidadeMacromedidor;
	}

	public String getSetorComercialMacromedidor(){

		return setorComercialMacromedidor;
	}

	public void setSetorComercialMacromedidor(String setorComercialMacromedidor){

		this.setorComercialMacromedidor = setorComercialMacromedidor;
	}

	public String getNumeroRotaMacromedidor(){

		return numeroRotaMacromedidor;
	}

	public void setNumeroRotaMacromedidor(String numeroRotaMacromedidor){

		this.numeroRotaMacromedidor = numeroRotaMacromedidor;
	}

	public String getNumeroSegmentoMacromedidor(){

		return numeroSegmentoMacromedidor;
	}

	public void setNumeroSegmentoMacromedidor(String numeroSegmentoMacromedidor){

		this.numeroSegmentoMacromedidor = numeroSegmentoMacromedidor;
	}

	public String getNumeroLoteMacromedidor(){

		return numeroLoteMacromedidor;
	}

	public void setNumeroLoteMacromedidor(String numeroLoteMacromedidor){

		this.numeroLoteMacromedidor = numeroLoteMacromedidor;
	}

	public String getNumeroSubLoteMacromedidor(){

		return numeroSubLoteMacromedidor;
	}

	public void setNumeroSubLoteMacromedidor(String numeroSubLoteMacromedidor){

		this.numeroSubLoteMacromedidor = numeroSubLoteMacromedidor;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getNumeroInscricao(){

		return numeroInscricao;
	}

	public void setNumeroInscricao(String numeroInscricao){

		this.numeroInscricao = numeroInscricao;
	}

	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	public String getIndicadorMedido(){

		return indicadorMedido;
	}

	public void setIndicadorMedido(String indicadorMedido){

		this.indicadorMedido = indicadorMedido;
	}

	public String getMedia(){

		return media;
	}

	public void setMedia(String media){

		this.media = media;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getLeitura(){

		return leitura;
	}

	public void setLeitura(String leitura){

		this.leitura = leitura;
	}

	public String getDataLeitura(){

		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura){

		this.dataLeitura = dataLeitura;
	}

	public String getAnormalidadeConsumo(){

		return anormalidadeConsumo;
	}

	public void setAnormalidadeConsumo(String anormalidadeConsumo){

		this.anormalidadeConsumo = anormalidadeConsumo;
	}

	public String getAnormalidadeLeitura(){

		return anormalidadeLeitura;
	}

	public void setAnormalidadeLeitura(String anormalidadeLeitura){

		this.anormalidadeLeitura = anormalidadeLeitura;
	}

	public String getQtidadeConsumo(){

		return qtidadeConsumo;
	}

	public void setQtidadeConsumo(String qtidadeConsumo){

		this.qtidadeConsumo = qtidadeConsumo;
	}

	public String getNumeroDiasConsumo(){

		return numeroDiasConsumo;
	}

	public void setNumeroDiasConsumo(String numeroDiasConsumo){

		this.numeroDiasConsumo = numeroDiasConsumo;
	}

}