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
 * GSANPCG
 * Virg�nia Melo
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

package gcom.cobranca.campanhapremiacao;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro Campanha Premiacao
 * 
 * @author Felipe Rosacruz
 * @date 11/09/2013
 */
public class FiltroCampanhaPremiacao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroCampanhaPremiacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public FiltroCampanhaPremiacao() {

	}

	public final static String ID = "id";

	public final static String ULTIMA_ALTERACAO = "ultimaAlteracao";

	public final static String CAMPANHA_PREMIO = "campanhaPremio";

	public final static String CAMPANHA_PREMIO_ID = "campanhaPremio.id";

	public final static String CAMPANHA_SORTEIO = "campanhaSorteio";

	public final static String CAMPANHA_SORTEIO_ID = "campanhaSorteio.id";

	public final static String CAMPANHA_CADASTRO = "campanhaCadastro";

	public final static String PREMIO_GERENCIA_REGIONAL = "campanhaPremio.gerenciaRegional";

	public final static String PREMIO_UNIDADE_NEGOCIO = "campanhaPremio.unidadeNegocio";

	public final static String PREMIO_ELO = "campanhaPremio.eloPremio";

	public final static String PREMIO_LOCALIDADE = "campanhaPremio.localidade";

	public final static String USUARIO_ENTREGA_PREMIO = "usuarioEntregaPremio";

	public final static String USUARIO_CANCELAMENTO_PREMIACAO = "usuarioCancelamentoPremiacao";

	public final static String CAMPANHA_CADASTRO_ORGAO_EXPEDIDOR = "campanhaCadastro.orgaoExpedidorRG";

	public final static String CAMPANHA_CADASTRO_UF = "campanhaCadastro.unidadeFederacao";

	public final static String CAMPANHA_PREMIACAO_MOT_CANCEL = "campanhaPremiacaoMotCancel";

}
