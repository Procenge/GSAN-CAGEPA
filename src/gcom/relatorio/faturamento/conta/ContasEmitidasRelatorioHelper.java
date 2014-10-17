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

package gcom.relatorio.faturamento.conta;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;

import java.math.BigDecimal;

public class ContasEmitidasRelatorioHelper {

	private String idContaImpressao;

	private String idClienteResponsavel;

	private String nomeClienteResponsavel;

	private String idLocalidade;

	private String descLocalidade;

	private String dataVencimentoConta;

	private String idImovel;

	private String codigoSetorComercial;

	private String numeroQuadra;

	private String numeroLote;

	private String numeroSubLote;

	private String nomeUsuario;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorDebitos;

	private BigDecimal valorCreditos;

	private String endereco;

	private String idEsferaPoder;

	private String descEsferaPoder;

	private String idGrupoFaturamento;

	private String mesAnoReferencia;

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getDataVencimentoConta(){

		return dataVencimentoConta;
	}

	public void setDataVencimentoConta(String dataVencimentoConta){

		this.dataVencimentoConta = dataVencimentoConta;
	}

	public String getDescLocalidade(){

		return descLocalidade;
	}

	public void setDescLocalidade(String descLocalidade){

		this.descLocalidade = descLocalidade;
	}

	public String getIdClienteResponsavel(){

		return idClienteResponsavel;
	}

	public void setIdClienteResponsavel(String idClienteResponsavel){

		this.idClienteResponsavel = idClienteResponsavel;
	}

	public String getIdContaImpressao(){

		return idContaImpressao;
	}

	public void setIdContaImpressao(String idContaImpressao){

		this.idContaImpressao = idContaImpressao;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public String getNomeClienteResponsavel(){

		return nomeClienteResponsavel;
	}

	public void setNomeClienteResponsavel(String nomeClienteResponsavel){

		this.nomeClienteResponsavel = nomeClienteResponsavel;
	}

	public String getNumeroLote(){

		return numeroLote;
	}

	public void setNumeroLote(String numeroLote){

		this.numeroLote = numeroLote;
	}

	public String getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(String numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public String getNumeroSubLote(){

		return numeroSubLote;
	}

	public void setNumeroSubLote(String numeroSubLote){

		this.numeroSubLote = numeroSubLote;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getInscricaoFormatada(){

		Imovel imovel = new Imovel();
		imovel.setLocalidade(new Localidade(Integer.valueOf(this.idLocalidade)));
		imovel.setSetorComercial(new SetorComercial(Integer.parseInt(this.codigoSetorComercial)));
		imovel.setQuadra(new Quadra(Integer.parseInt(this.numeroQuadra)));
		imovel.setLote(Short.parseShort(this.numeroLote));
		imovel.setSubLote(Short.parseShort(this.numeroSubLote));

		return imovel.getInscricaoFormatada();
	}

	/**
	 * Este m�todo retorna o valor total da conta
	 * (VALOR_AGUA + VALOR_ESGOTO + VALOR_DEBITOS) - VALOR_CREDITOS
	 * OBS - Este m�todo foi alterado por Raphael pois n�o estava refletindo corretamente o valor da
	 * conta
	 * 
	 * @author Raphael Rossiter
	 * @date 14/03/2006
	 */
	public BigDecimal getValorTotal(){

		BigDecimal valorTotalConta = new BigDecimal("0.00");

		// Valor de �gua
		if(this.getValorAgua() != null){
			valorTotalConta = valorTotalConta.add(this.getValorAgua());
		}

		// Valor de esgoto
		if(this.getValorEsgoto() != null){
			valorTotalConta = valorTotalConta.add(this.getValorEsgoto());
		}

		// Valor dos d�bitos
		if(this.getValorDebitos() != null){
			valorTotalConta = valorTotalConta.add(this.getValorDebitos());
		}

		// Valor dos cr�ditos
		if(this.getValorCreditos() != null){
			valorTotalConta = valorTotalConta.subtract(this.getValorCreditos());
		}

		valorTotalConta = valorTotalConta.setScale(2, BigDecimal.ROUND_HALF_UP);

		return valorTotalConta;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getDescEsferaPoder(){

		return descEsferaPoder;
	}

	public void setDescEsferaPoder(String descEsferaPoder){

		this.descEsferaPoder = descEsferaPoder;
	}

	public String getIdEsferaPoder(){

		return idEsferaPoder;
	}

	public void setIdEsferaPoder(String idEsferaPoder){

		this.idEsferaPoder = idEsferaPoder;
	}

	public String getIdGrupoFaturamento(){

		return idGrupoFaturamento;
	}

	public void setIdGrupoFaturamento(String idGrupoFaturamento){

		this.idGrupoFaturamento = idGrupoFaturamento;
	}

	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

}
