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

package gcom.arrecadacao.bean;

import java.io.Serializable;

/**
 * [UC0721] - Distribuir dados do Registro de Movimento do Arrecadador da Ficha de Compensa��o
 * Autor: Anderson Italo
 * Data: 03/10/2011
 */
public class RegistroHelperCodigo3R
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public RegistroHelperCodigo3R() {

	}

	private String codigoBancoCompensacao;

	private String loteServico;

	private String codigoRegistro;

	private String numeroSequencialRegistroLote;

	private String codigoSegmentoRegistroDetalhe;

	private String usoExclusivoCampo6;

	private String codigoMovimento;

	private String codigoDesconto2;

	private String dataDesconto2;

	private String valorPercentualConcedidoDesconto2;

	private String codigoDesconto3;

	private String dataDesconto3;

	private String valorPercentualConcedidoDesconto3;

	private String codigoMulta;

	private String dataMulta;

	private String valorPercentualAplicadoMulta;

	private String informacaoSacado;

	private String mensagem3;

	private String mensagem4;

	private String usoExclusivoCampo20;

	private String codigoOcorrenciaSacado;

	private String codigoBancoContaDebito;

	private String codigoAgenciaDebito;

	private String digitoVerificadorAgenciaDebito;

	private String contaCorrenteDebito;

	private String digitoVerificadorContaDebito;

	private String digitoVerificadorAgenciaContaDebito;

	private String avisoDebitoAutomatico;

	private String usoExclusivoCampo29;

	public String getCodigoBancoCompensacao(){

		return codigoBancoCompensacao;
	}

	public void setCodigoBancoCompensacao(String codigoBancoCompensacao){

		this.codigoBancoCompensacao = codigoBancoCompensacao;
	}

	public String getLoteServico(){

		return loteServico;
	}

	public void setLoteServico(String loteServico){

		this.loteServico = loteServico;
	}

	public String getCodigoRegistro(){

		return codigoRegistro;
	}

	public void setCodigoRegistro(String codigoRegistro){

		this.codigoRegistro = codigoRegistro;
	}

	public String getNumeroSequencialRegistroLote(){

		return numeroSequencialRegistroLote;
	}

	public void setNumeroSequencialRegistroLote(String numeroSequencialRegistroLote){

		this.numeroSequencialRegistroLote = numeroSequencialRegistroLote;
	}

	public String getCodigoSegmentoRegistroDetalhe(){

		return codigoSegmentoRegistroDetalhe;
	}

	public void setCodigoSegmentoRegistroDetalhe(String codigoSegmentoRegistroDetalhe){

		this.codigoSegmentoRegistroDetalhe = codigoSegmentoRegistroDetalhe;
	}

	public String getUsoExclusivoCampo6(){

		return usoExclusivoCampo6;
	}

	public void setUsoExclusivoCampo6(String usoExclusivoCampo6){

		this.usoExclusivoCampo6 = usoExclusivoCampo6;
	}

	public String getCodigoMovimento(){

		return codigoMovimento;
	}

	public void setCodigoMovimento(String codigoMovimento){

		this.codigoMovimento = codigoMovimento;
	}

	public String getCodigoDesconto2(){

		return codigoDesconto2;
	}

	public void setCodigoDesconto2(String codigoDesconto2){

		this.codigoDesconto2 = codigoDesconto2;
	}

	public String getDataDesconto2(){

		return dataDesconto2;
	}

	public void setDataDesconto2(String dataDesconto2){

		this.dataDesconto2 = dataDesconto2;
	}

	public String getValorPercentualConcedidoDesconto2(){

		return valorPercentualConcedidoDesconto2;
	}

	public void setValorPercentualConcedidoDesconto2(String valorPercentualConcedidoDesconto2){

		this.valorPercentualConcedidoDesconto2 = valorPercentualConcedidoDesconto2;
	}

	public String getCodigoDesconto3(){

		return codigoDesconto3;
	}

	public void setCodigoDesconto3(String codigoDesconto3){

		this.codigoDesconto3 = codigoDesconto3;
	}

	public String getDataDesconto3(){

		return dataDesconto3;
	}

	public void setDataDesconto3(String dataDesconto3){

		this.dataDesconto3 = dataDesconto3;
	}

	public String getValorPercentualConcedidoDesconto3(){

		return valorPercentualConcedidoDesconto3;
	}

	public void setValorPercentualConcedidoDesconto3(String valorPercentualConcedidoDesconto3){

		this.valorPercentualConcedidoDesconto3 = valorPercentualConcedidoDesconto3;
	}

	public String getCodigoMulta(){

		return codigoMulta;
	}

	public void setCodigoMulta(String codigoMulta){

		this.codigoMulta = codigoMulta;
	}

	public String getDataMulta(){

		return dataMulta;
	}

	public void setDataMulta(String dataMulta){

		this.dataMulta = dataMulta;
	}

	public String getValorPercentualAplicadoMulta(){

		return valorPercentualAplicadoMulta;
	}

	public void setValorPercentualAplicadoMulta(String valorPercentualAplicadoMulta){

		this.valorPercentualAplicadoMulta = valorPercentualAplicadoMulta;
	}

	public String getInformacaoSacado(){

		return informacaoSacado;
	}

	public void setInformacaoSacado(String informacaoSacado){

		this.informacaoSacado = informacaoSacado;
	}

	public String getMensagem3(){

		return mensagem3;
	}

	public void setMensagem3(String mensagem3){

		this.mensagem3 = mensagem3;
	}

	public String getMensagem4(){

		return mensagem4;
	}

	public void setMensagem4(String mensagem4){

		this.mensagem4 = mensagem4;
	}

	public String getUsoExclusivoCampo20(){

		return usoExclusivoCampo20;
	}

	public void setUsoExclusivoCampo20(String usoExclusivoCampo20){

		this.usoExclusivoCampo20 = usoExclusivoCampo20;
	}

	public String getCodigoOcorrenciaSacado(){

		return codigoOcorrenciaSacado;
	}

	public void setCodigoOcorrenciaSacado(String codigoOcorrenciaSacado){

		this.codigoOcorrenciaSacado = codigoOcorrenciaSacado;
	}

	public String getCodigoBancoContaDebito(){

		return codigoBancoContaDebito;
	}

	public void setCodigoBancoContaDebito(String codigoBancoContaDebito){

		this.codigoBancoContaDebito = codigoBancoContaDebito;
	}

	public String getCodigoAgenciaDebito(){

		return codigoAgenciaDebito;
	}

	public void setCodigoAgenciaDebito(String codigoAgenciaDebito){

		this.codigoAgenciaDebito = codigoAgenciaDebito;
	}

	public String getDigitoVerificadorAgenciaDebito(){

		return digitoVerificadorAgenciaDebito;
	}

	public void setDigitoVerificadorAgenciaDebito(String digitoVerificadorAgenciaDebito){

		this.digitoVerificadorAgenciaDebito = digitoVerificadorAgenciaDebito;
	}

	public String getContaCorrenteDebito(){

		return contaCorrenteDebito;
	}

	public void setContaCorrenteDebito(String contaCorrenteDebito){

		this.contaCorrenteDebito = contaCorrenteDebito;
	}

	public String getDigitoVerificadorContaDebito(){

		return digitoVerificadorContaDebito;
	}

	public void setDigitoVerificadorContaDebito(String digitoVerificadorContaDebito){

		this.digitoVerificadorContaDebito = digitoVerificadorContaDebito;
	}

	public String getDigitoVerificadorAgenciaContaDebito(){

		return digitoVerificadorAgenciaContaDebito;
	}

	public void setDigitoVerificadorAgenciaContaDebito(String digitoVerificadorAgenciaContaDebito){

		this.digitoVerificadorAgenciaContaDebito = digitoVerificadorAgenciaContaDebito;
	}

	public String getAvisoDebitoAutomatico(){

		return avisoDebitoAutomatico;
	}

	public void setAvisoDebitoAutomatico(String avisoDebitoAutomatico){

		this.avisoDebitoAutomatico = avisoDebitoAutomatico;
	}

	public String getUsoExclusivoCampo29(){

		return usoExclusivoCampo29;
	}

	public void setUsoExclusivoCampo29(String usoExclusivoCampo29){

		this.usoExclusivoCampo29 = usoExclusivoCampo29;
	}

}
