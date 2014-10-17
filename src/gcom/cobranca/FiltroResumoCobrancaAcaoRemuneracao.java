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
 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais.
 * 
 * @author Josenildo Neves
 * @created 28 de Agosto de 2012
 */

public class FiltroResumoCobrancaAcaoRemuneracao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroResumoCobrancaAcaoRemuneracao object
	 */
	public FiltroResumoCobrancaAcaoRemuneracao() {

	}

	/**
	 * Constructor for the FiltroResumoCobrancaAcaoRemuneracao object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroResumoCobrancaAcaoRemuneracao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String RESUMO_COBRANCA_ACAO_EVENTUAL = "resumoCobrancaAcaoEventual";

	public final static String RESUMO_COBRANCA_ACAO_EVENTUAL_ID = "resumoCobrancaAcaoEventual.id";

	public final static String RESUMO_COBRANCA_ACAO_EVENTUAL_ACAO_COBRANCA = "resumoCobrancaAcaoEventual.cobrancaAcao";

	public final static String RESUMO_COBRANCA_ACAO_EVENTUAL_ACAO_COBRANCA_ID = "resumoCobrancaAcaoEventual.cobrancaAcao.id";

	public final static String DOCUMENTO_TIPO = "documentoTipo";

	public final static String DOCUMENTO_TIPO_ID = "documentoTipo.id";

	public final static String DOCUMENTO_TIPO_DESCRICAO = "documentoTipo.descricaoDocumentoTipo";

	public final static String PERCENTUAL_REMUNERACAO = "percentualRemuneracao";

}
