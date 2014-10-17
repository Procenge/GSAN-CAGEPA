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

/**
 * Classe que ir� auxiliar no formato do retorno da pesquisa dos itens de um
 * determinado movimento do arrecadador
 * 
 * @author Raphael Rossiter
 * @date 20/03/2006
 */
public class ArrecadadorMovimentoItemHelper {

	public static final String TIPO_MOVIMENTO_DEBITO_AUTOMATICO = "D.AUT";

	public static final String TIPO_MOVIMENTO_CODIGO_BARRAS = "C.BAR";

	public static final String TIPO_MOVIMENTO_COBRANCA_BANCARIA = "C.BAN";

	public static final String TIPO_PAGAMENTO_GUIA_PAGAMENTO = "Guia de Pagamento";

	public static final String TIPO_PAGAMENTO_CONTA = "Conta";

	public static final String TIPO_PAGAMENTO_GUIA_PAGAMENTO_IMOVEL = "Guia de Pagamento de Im�vel";

	public static final String TIPO_PAGAMENTO_GUIA_PAGAMENTO_CLIENTE = "Guia de Pagamento de Cliente";

	public static final String TIPO_PAGAMENTO_DOCUMENTO_COBRANCA = "Documento de Cobran�a";

	public static final String TIPO_PAGAMENTO_DOCUMENTO_COBRANCA_IMOVEL = "Documento de Cobran�a de Im�vel";

	public static final String TIPO_PAGAMENTO_DOCUMENTO_COBRANCA_CLIENTE = "Documento de Cobran�a de Cliente";

	public static final String TIPO_PAGAMENTO_FATURA_CLIENTE_RESPONSAVEL = "Fatura do Cliente Respons�vel";

	public static final String TIPO_PAGAMENTO_NOTA_RECEBIMENTO = "Nota de Recebimento";

	public static final String TIPO_PAGAMENTO_PRE_PARCELAMENTO = "Pr�-Parcelamento";

	public static final String TIPO_PAGAMENTO_NAO_IDENTIFICADO = "N�o identificado";

	private Integer id;

	private String codigoRegistro;

	private String identificacao;

	private String tipoMovimento;

	private String tipoPagamento;

	private String ocorrencia;

	private Short indicadorAceitacao;

	private String descricaoIndicadorAceitacao;

	private RegistroHelperCodigoB registroHelperCodigoB;

	private RegistroHelperCodigoC registroHelperCodigoC;

	private RegistroHelperCodigoE registroHelperCodigoE;

	private RegistroHelperCodigoF registroHelperCodigoF;

	private RegistroHelperCodigoG registroHelperCodigoG;

	private RegistroHelperCodigo3T registroHelperCodigo3T;

	private RegistroHelperCodigo3U registroHelperCodigo3U;

	private String ajustado;

	private String nvDescricaoOcorrencia;

	private String vlMovimento;

	private String vlPagamento;

	public RegistroHelperCodigoB getRegistroHelperCodigoB(){

		return registroHelperCodigoB;
	}

	public void setRegistroHelperCodigoB(RegistroHelperCodigoB registroHelperCodigoB){

		this.registroHelperCodigoB = registroHelperCodigoB;
	}

	public RegistroHelperCodigoC getRegistroHelperCodigoC(){

		return registroHelperCodigoC;
	}

	public void setRegistroHelperCodigoC(RegistroHelperCodigoC registroHelperCodigoC){

		this.registroHelperCodigoC = registroHelperCodigoC;
	}

	public RegistroHelperCodigoE getRegistroHelperCodigoE(){

		return registroHelperCodigoE;
	}

	public void setRegistroHelperCodigoE(RegistroHelperCodigoE registroHelperCodigoE){

		this.registroHelperCodigoE = registroHelperCodigoE;
	}

	public RegistroHelperCodigoF getRegistroHelperCodigoF(){

		return registroHelperCodigoF;
	}

	public void setRegistroHelperCodigoF(RegistroHelperCodigoF registroHelperCodigoF){

		this.registroHelperCodigoF = registroHelperCodigoF;
	}

	public RegistroHelperCodigoG getRegistroHelperCodigoG(){

		return registroHelperCodigoG;
	}

	public void setRegistroHelperCodigoG(RegistroHelperCodigoG registroHelperCodigoG){

		this.registroHelperCodigoG = registroHelperCodigoG;
	}

	public ArrecadadorMovimentoItemHelper() {

	}

	public ArrecadadorMovimentoItemHelper(String codigoRegistro, String identificacao, String tipoPagamento, String ocorrencia,
											Short indicadorAceitacao, String descricaoIndicadorAceitacao) {

		this.codigoRegistro = codigoRegistro;
		this.identificacao = identificacao;
		this.tipoPagamento = tipoPagamento;
		this.ocorrencia = ocorrencia;
		this.indicadorAceitacao = indicadorAceitacao;
		this.descricaoIndicadorAceitacao = descricaoIndicadorAceitacao;
	}

	public ArrecadadorMovimentoItemHelper(String codigoRegistro, String identificacao, String tipoPagamento, String ocorrencia,
											Short indicadorAceitacao, String descricaoIndicadorAceitacao, String ajustado,
											String nvDescricaoOcorrencia) {

		this.codigoRegistro = codigoRegistro;
		this.identificacao = identificacao;
		this.tipoPagamento = tipoPagamento;
		this.ocorrencia = ocorrencia;
		this.indicadorAceitacao = indicadorAceitacao;
		this.descricaoIndicadorAceitacao = descricaoIndicadorAceitacao;
		this.ajustado = ajustado;
		this.nvDescricaoOcorrencia = nvDescricaoOcorrencia;
	}

	public String getCodigoRegistro(){

		return codigoRegistro;
	}

	public void setCodigoRegistro(String codigoRegistro){

		this.codigoRegistro = codigoRegistro;
	}

	public String getDescricaoIndicadorAceitacao(){

		return descricaoIndicadorAceitacao;
	}

	public void setDescricaoIndicadorAceitacao(String descricaoIndicadorAceitacao){

		this.descricaoIndicadorAceitacao = descricaoIndicadorAceitacao;
	}

	public String getIdentificacao(){

		return identificacao;
	}

	public void setIdentificacao(String identificacao){

		this.identificacao = identificacao;
	}

	public Short getIndicadorAceitacao(){

		return indicadorAceitacao;
	}

	public void setIndicadorAceitacao(Short indicadorAceitacao){

		this.indicadorAceitacao = indicadorAceitacao;
	}

	public String getOcorrencia(){

		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia){

		this.ocorrencia = ocorrencia;
	}

	public String getTipoPagamento(){

		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento){

		this.tipoPagamento = tipoPagamento;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getAjustado(){

		return ajustado;
	}

	public void setAjustado(String ajustado){

		this.ajustado = ajustado;
	}

	public String getNvDescricaoOcorrencia(){

		return nvDescricaoOcorrencia;
	}

	public void setNvDescricaoOcorrencia(String nvDescricaoOcorrencia){

		this.nvDescricaoOcorrencia = nvDescricaoOcorrencia;
	}

	public String getVlMovimento(){

		return vlMovimento;
	}

	public void setVlMovimento(String vlMovimento){

		this.vlMovimento = vlMovimento;
	}

	public String getVlPagamento(){

		return vlPagamento;
	}

	public void setVlPagamento(String vlPagamento){

		this.vlPagamento = vlPagamento;
	}

	public RegistroHelperCodigo3T getRegistroHelperCodigo3T(){

		return registroHelperCodigo3T;
	}

	public void setRegistroHelperCodigo3T(RegistroHelperCodigo3T registroHelperCodigo3T){

		this.registroHelperCodigo3T = registroHelperCodigo3T;
	}

	public RegistroHelperCodigo3U getRegistroHelperCodigo3U(){

		return registroHelperCodigo3U;
	}

	public void setRegistroHelperCodigo3U(RegistroHelperCodigo3U registroHelperCodigo3U){

		this.registroHelperCodigo3U = registroHelperCodigo3U;
	}

	public String getTipoMovimento(){

		return tipoMovimento;
	}

	public void setTipoMovimento(String tipoMovimento){

		this.tipoMovimento = tipoMovimento;
	}

}
