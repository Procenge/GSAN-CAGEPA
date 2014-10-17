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
 * Filtro utilizado na pesquisa de GuiaPagamentoPrestacao
 * 
 * @author eduardo henrique
 * @date 24/07/2008
 */
public class FiltroGuiaPagamentoPrestacao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Description of the Field
	 */
	public final static String ID = "comp_id";

	/**
	 * Description of the Field
	 */
	public final static String GUIA_PAGAMENTO = "guiaPagamento";

	/**
	 * Description of the Field
	 */
	public final static String GUIA_PAGAMENTO_ID = "guiaPagamento.id";

	/**
	 * Description of the Field
	 */
	public final static String NUMERO_PRESTACAO = "comp_id.numeroPrestacao";

	/**
	 * Description of the Field
	 */
	public final static String DEBITO_TIPO_ID = "debitoTipo.id";

	/**
	 * Description of the Field
	 */
	public final static String ITEM_LANCAMENTO_CONTABIL_ID = "lancamentoItemContabil.id";

	/**
	 * Description of the Field
	 */
	public final static String GUIA_PAGAMENTO_IMOVEL_ID = "guiaPagamento.imovel.id";

	public final static String GUIA_PAGAMENTO_IMOVEL = "guiaPagamento.imovel";

	/**
	 * Description of the Field
	 */
	public final static String GUIA_PAGAMENTO_DATA_INCLUSAO = "guiaPagamento.dataInclusao";

	/**
	 * Description of the Field
	 */
	public final static String GUIA_PAGAMENTO_CLIENTE_ID = "guiaPagamento.cliente.id";

	/**
	 * Description of the Field
	 */
	public final static String DEBITO_CREDITO_SITUACAO_ATUAL_ID = "guiaPagamento.debitoCreditoSituacaoAtual.id";

	/**
	 * Description of the Field
	 */
	public final static String DEBITO_CREDITO_SITUACAO_ATUAL = "debitoCreditoSituacao";

	public final static String DEBITO_CREDITO_SITUACAO_ANTERIOR = "debitoCreditoSituacaoAnterior";

	/**
	 * Description of the Field
	 */
	public final static String DATA_EMISSAO = "dataEmissao";

	/**
	 * Description of the Field
	 */
	public final static String DATA_VENCIMENTO = "dataVencimento";

	/**
	 * Description of the Field
	 */
	public final static String DEBITO_TIPO = "debitoTipo";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_REMUNERA_COBRANCA_ADM = "indicadorRemuneraCobrancaAdministrativa";

	public final static String DEBITO_CREDITO_SITUACAO_ID = "debitoCreditoSituacao.id";

	/**
	 * Constructor for the FiltroGuiaPagamentoPrestacao object
	 */
	public FiltroGuiaPagamentoPrestacao() {

	}

	/**
	 * Constructor for the FiltroGuiaPagamentoPrestacao object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroGuiaPagamentoPrestacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}
