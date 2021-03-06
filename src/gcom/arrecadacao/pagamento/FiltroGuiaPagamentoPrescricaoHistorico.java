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


public class FiltroGuiaPagamentoPrescricaoHistorico
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ID = "id";

	public final static String GUIA_PAGAMENTO_GERAL = "guiaPagamentoGeral";

	public final static String GUIA_PAGAMENTO_GERAL_ID = "guiaPagamentoGeral.id";

	public final static String NUMERO_PRESTACAO = "numeroPrestacao";

	public final static String CODIGO_EVENTO = "codigoEvento";

	public final static String DATA_EVENTO = "dataEvento";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String PRESCRICAO_COMANDO = "prescricaoComando";

	public final static String PRESCRICAO_COMANDO_ID = "prescricaoComando.id";

	public final static String USUARIO = "usuario";

	public final static String USUARIO_ID = "usuario.id";

	public FiltroGuiaPagamentoPrescricaoHistorico() {

	}

	public FiltroGuiaPagamentoPrescricaoHistorico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}
