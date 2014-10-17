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
 * 
 * GSANPCG
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 */

package gcom.faturamento.debito;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os critérios de busca de um Débito a Cobrar Histórico
 * 
 * @author Saulo Lima
 * @date 12/01/2009
 */

public class FiltroDebitoACobrarHistorico
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroDebitoACobrarHistorico() {

	}

	public FiltroDebitoACobrarHistorico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String DEBITO_TIPO = "debitoTipo";

	public final static String DEBITO_TIPO_ID = "debitoTipo.id";

	public final static String ID_ORIGINAL = "idOriginal";

	public final static String NUMERO_PARCELA = "numeroParcela";

	public final static String REFERENCIA_DEBITO = "anoMesReferenciaDebito";

	public final static String FINANCIAMENTO_TIPO = "financiamentoTipo";

	public final static String FINANCIAMENTO_TIPO_ID = "financiamentoTipo.id";

	public final static String LOCALIDADE = "localidade";

	public final static String DEBITO_GERADO_REALIZAR = "debitoGeradoRealizar";

	public final static String ORDEM_SERVICO = "ordemServico";

	public final static String ORDEM_SERVICO_ID = "ordemServico.id";
}
