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

package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author Pedro Alexandre
 * @created 08 de Fevereiro de 2006
 */

public class FiltroCobrancaDocumentoItem
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCobrancaDocumentoItem object
	 */
	public FiltroCobrancaDocumentoItem() {

	}

	/**
	 * Constructor for the FiltroCobrancaDocumentoItem object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroCobrancaDocumentoItem(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String COBRANCA_DOCUMENTO_ID = "cobrancaDocumento.id";

	public final static String COBRANCA_DOCUMENTO = "cobrancaDocumento";

	public final static String COBRANCA_DOCUMENTO_COBRANCA_ACAO = "cobrancaDocumento.cobrancaAcao";

	public final static String COBRANCA_DOCUMENTO_COBRANCA_ACAO_ID = "cobrancaDocumento.cobrancaAcao.id";

	public final static String COBRANCA_DOCUMENTO_ATIVIDADE_COMANDO = "cobrancaDocumento.cobrancaAcaoAtividadeComando";

	public final static String COBRANCA_DOCUMENTO_ATIVIDADE_COMANDO_COBRANCA_ACAO = "cobrancaDocumento.cobrancaAcaoAtividadeComando.cobrancaAcao";

	public final static String COBRANCA_DOCUMENTO_ATIVIDADE_CRONOGRAMA = "cobrancaDocumento.cobrancaAcaoAtividadeCronograma";

	public final static String CONTA_GERAL_ID = "contaGeral.id";

	public final static String CONTA_GERAL = "contaGeral";

	public final static String CONTA_GERAL_CONTA_HISTORICO = "contaGeral.contaHistorico";

	public final static String CONTA_GERAL_CONTA = "contaGeral.conta";

	public final static String CONTA_GERAL_CONTA_MOTIVO_REVISAO = "contaGeral.conta.contaMotivoRevisao";

	/**
	 * Description of the Field
	 */
	public final static String DEBITO_A_COBRAR_GERAL_ID = "debitoACobrarGeral.id";

	public final static String DEBITO_A_COBRAR_GERAL = "debitoACobrarGeral";

	public final static String DEBITO_A_COBRAR_GERAL_DEBITO_A_COBRAR_HISTORICO = "debitoACobrarGeral.debitoACobrarHistorico";

	public final static String DEBITO_A_COBRAR_GERAL_DEBITO_A_COBRAR = "debitoACobrarGeral.debitoACobrar";

	/**
	 * Description of the Field
	 */
	public final static String IMOVEL_ID = "cobrancaDocumento.imovel.id";

	/**
	 * Description of the Field
	 */
	public final static String NUMERO_SEQUENCIAL_DOCUMENTO = "cobrancaDocumento.numeroSequenciaDocumento";

	public final static String GUIA_PAGAMENTO_ID = "guiaPagamentoGeral.id";

	public final static String CREDITO_A_REALIZAR_GERAL = "creditoARealizarGeral";

	public final static String CREDITO_A_REALIZAR_GERAL_CREDITO_A_REALIZAR = "creditoARealizarGeral.creditoARealizar";

	public final static String CREDITO_A_REALIZAR_GERAL_CREDITO_A_REALIZAR_HISTORICO = "creditoARealizarGeral.creditoARealizarHistorico";

	public final static String CREDITO_A_REALIZAR_GERAL_ID = "creditoARealizarGeral.id";

	public final static String GUIA_PAGAMENTO_GERAL = "guiaPagamentoGeral";

	public final static String GUIA_PAGAMENTO_GERAL_ID = "guiaPagamentoGeral.id";

	public final static String GUIA_PAGAMENTO_GERAL_GUIA_PAGAMENTO_HISTORICO = "guiaPagamentoGeral.guiaPagamentoHistorico";

	public final static String GUIA_PAGAMENTO_GERAL_GUIA_PAGAMENTO = "guiaPagamentoGeral.guiaPagamento";

	public final static String GUIA_PAGAMENTO_GERAL_GUIA_PAGAMENTO_GUIAS_PAGAMENTO_PRESTACAO_HISTORICO = "guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao";

	public final static String GUIA_PAGAMENTO_GERAL_GUIA_PAGAMENTO_GUIAS_PAGAMENTO_PRESTACAO = "guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico";

	public final static String DOCUMENTO_TIPO_ID = "documentoTipo.id";

	public final static String DOCUMENTO_TIPO = "documentoTipo";

	public final static String NUMERO_DA_PRESTACAO = "numeroDaPrestacao";

	public final static String COBRANCA_DEBITO_SITUACAO = "cobrancaDebitoSituacao";

	public final static String COBRANCA_DEBITO_SITUACAO_ID = "cobrancaDebitoSituacao.id";

	public final static String INDICADOR_ATUALIZADO = "indicadorAtualizado";

}
