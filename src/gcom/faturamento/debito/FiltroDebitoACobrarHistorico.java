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
 * O filtro serve para armazenar os crit�rios de busca de um D�bito a Cobrar Hist�rico
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
