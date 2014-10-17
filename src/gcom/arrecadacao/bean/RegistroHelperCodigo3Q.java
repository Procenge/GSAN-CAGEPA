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
 * Data: 04/10/2011
 */
public class RegistroHelperCodigo3Q
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public RegistroHelperCodigo3Q() {

	}

	private String codigoBancoCompensacao;

	private String loteServico;

	private String codigoRegistro;

	private String numeroSequencialRegistroLote;

	private String codigoSegmentoRegistroDetalhe;

	private String usoExclusivoCampo6;

	private String codigoMovimento;

	private String tipoInscricaoCampo8;

	private String numeroInscricaoCampo9;

	private String nome;

	private String endereco;

	private String bairro;

	private String cep;

	private String sufixoCep;

	private String cidade;

	private String Uf;

	private String tipoInscricaoCampo17;

	private String numeroInscricaoInscricaoCampo18;

	private String nomeSacadorAvalista;

	private String codigoBancoCorrespondenteCompensacao;

	private String nossoNumeroBancoCorrespondenteCompensacao;

	private String usoExclusivoCampo22;

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

	public String getTipoInscricaoCampo8(){

		return tipoInscricaoCampo8;
	}

	public void setTipoInscricaoCampo8(String tipoInscricaoCampo8){

		this.tipoInscricaoCampo8 = tipoInscricaoCampo8;
	}

	public String getNumeroInscricaoCampo9(){

		return numeroInscricaoCampo9;
	}

	public void setNumeroInscricaoCampo9(String numeroInscricaoCampo9){

		this.numeroInscricaoCampo9 = numeroInscricaoCampo9;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public String getSufixoCep(){

		return sufixoCep;
	}

	public void setSufixoCep(String sufixoCep){

		this.sufixoCep = sufixoCep;
	}

	public String getCidade(){

		return cidade;
	}

	public void setCidade(String cidade){

		this.cidade = cidade;
	}

	public String getUf(){

		return Uf;
	}

	public void setUf(String uf){

		Uf = uf;
	}

	public String getTipoInscricaoCampo17(){

		return tipoInscricaoCampo17;
	}

	public void setTipoInscricaoCampo17(String tipoInscricaoCampo17){

		this.tipoInscricaoCampo17 = tipoInscricaoCampo17;
	}

	public String getNumeroInscricaoInscricaoCampo18(){

		return numeroInscricaoInscricaoCampo18;
	}

	public void setNumeroInscricaoInscricaoCampo18(String numeroInscricaoInscricaoCampo18){

		this.numeroInscricaoInscricaoCampo18 = numeroInscricaoInscricaoCampo18;
	}

	public String getNomeSacadorAvalista(){

		return nomeSacadorAvalista;
	}

	public void setNomeSacadorAvalista(String nomeSacadorAvalista){

		this.nomeSacadorAvalista = nomeSacadorAvalista;
	}

	public String getCodigoBancoCorrespondenteCompensacao(){

		return codigoBancoCorrespondenteCompensacao;
	}

	public void setCodigoBancoCorrespondenteCompensacao(String codigoBancoCorrespondenteCompensacao){

		this.codigoBancoCorrespondenteCompensacao = codigoBancoCorrespondenteCompensacao;
	}

	public String getNossoNumeroBancoCorrespondenteCompensacao(){

		return nossoNumeroBancoCorrespondenteCompensacao;
	}

	public void setNossoNumeroBancoCorrespondenteCompensacao(String nossoNumeroBancoCorrespondenteCompensacao){

		this.nossoNumeroBancoCorrespondenteCompensacao = nossoNumeroBancoCorrespondenteCompensacao;
	}

	public String getUsoExclusivoCampo22(){

		return usoExclusivoCampo22;
	}

	public void setUsoExclusivoCampo22(String usoExclusivoCampo22){

		this.usoExclusivoCampo22 = usoExclusivoCampo22;
	}

}
