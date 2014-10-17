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

package gcom.arrecadacao.bean;

/**
 * Classe que irá auxiliar no formato do retorno da pesquisa dos itens de um
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

	public static final String TIPO_PAGAMENTO_GUIA_PAGAMENTO_IMOVEL = "Guia de Pagamento de Imóvel";

	public static final String TIPO_PAGAMENTO_GUIA_PAGAMENTO_CLIENTE = "Guia de Pagamento de Cliente";

	public static final String TIPO_PAGAMENTO_DOCUMENTO_COBRANCA = "Documento de Cobrança";

	public static final String TIPO_PAGAMENTO_DOCUMENTO_COBRANCA_IMOVEL = "Documento de Cobrança de Imóvel";

	public static final String TIPO_PAGAMENTO_DOCUMENTO_COBRANCA_CLIENTE = "Documento de Cobrança de Cliente";

	public static final String TIPO_PAGAMENTO_FATURA_CLIENTE_RESPONSAVEL = "Fatura do Cliente Responsável";

	public static final String TIPO_PAGAMENTO_NOTA_RECEBIMENTO = "Nota de Recebimento";

	public static final String TIPO_PAGAMENTO_PRE_PARCELAMENTO = "Pré-Parcelamento";

	public static final String TIPO_PAGAMENTO_NAO_IDENTIFICADO = "Não identificado";

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
