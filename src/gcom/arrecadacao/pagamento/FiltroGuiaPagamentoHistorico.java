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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 *
 * GSANPCG
 * Eduardo Henrique
 * 
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

	public final static String PARCELAMENTO_ID = "parcelamento.id";

	public final static String GUIAS_PAGAMENTO_CATEGORIA_HISTORICO = "guiasPagamentoCategoriaHistorico";

	public final static String NUMERO_PRESTACAO_TOTAL = "numeroPrestacaoTotal";

	public final static String VALOR_DEBITO = "valorDebito";

	public final static String PARCELAMENTO = "parcelamento";

}
