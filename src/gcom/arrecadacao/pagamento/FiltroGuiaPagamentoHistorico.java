/* This file is part of GSAN, an integrated service management system for Sanitation
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
 *
 * GSANPCG
 * Eduardo Henrique
 * 
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

package gcom.arrecadacao.pagamento;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author Eduardo Henrique
 * @created 17/12/2008
 */
public class FiltroGuiaPagamentoHistorico
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroGuiaPagamento object
	 */
	public FiltroGuiaPagamentoHistorico() {

	}

	/**
	 * Constructor for the FiltroGuiaPagamento object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroGuiaPagamentoHistorico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String IMOVEL_ID = "imovel.id";

	public final static String IMOVEL = "imovel";

	/**
	 * Description of the Field
	 */
	public final static String CLIENTE_ID = "cliente.id";

	public final static String CLIENTE = "cliente";

	public final static String DEBITO_CREDITO_SITUACAO_ATUAL = "debitoCreditoSituacaoAtual";

	public final static String DEBITO_CREDITO_SITUACAO_ATUAL_ID = "debitoCreditoSituacaoAtual.id";

	public final static String REGISTRO_ATENDIMENTO_ID = "registroAtendimento.id";

	public final static String REGISTRO_ATENDIMENTO = "registroAtendimento";

	public final static String PARCELAMENTO_ID = "parcelamento.id";

	public final static String GUIAS_PAGAMENTO_CATEGORIA_HISTORICO = "guiasPagamentoCategoriaHistorico";

	public final static String NUMERO_PRESTACAO_TOTAL = "numeroPrestacaoTotal";

	public final static String VALOR_DEBITO = "valorDebito";

	public final static String PARCELAMENTO = "parcelamento";

	public final static String SERVICO_TIPO = "servicoTipo";

	public final static String ID_SERVICO_TIPO = "servicoTipo.id";

	public final static String DEBITO_TIPO = "servicoTipo.debitoTipo";

	public final static String ID_DEBITO_TIPO = "servicoTipo.debitoTipo.id";

	public final static String ORDEM_SERVICO_ID = "ordemServico.id";

	public final static String ORDEM_SERVICO = "ordemServico";

	public final static String ORDEM_SERVICO_SERVICO_TIPO = "ordemServico.servicoTipo";

	public final static String ORDEM_SERVICO_SERVICO_TIPO_DEBITO_ID = "ordemServico.servicoTipo.debitoTipo.id";

}
