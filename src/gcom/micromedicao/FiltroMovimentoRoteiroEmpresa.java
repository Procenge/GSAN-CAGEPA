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

package gcom.micromedicao;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author eduardo henrique
 * @date 16/09/2008
 */
public class FiltroMovimentoRoteiroEmpresa
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroMovimentoRoteiroEmpresa object
	 */
	public FiltroMovimentoRoteiroEmpresa() {

	}

	/**
	 * Constructor for the FiltroMovimentoRoteiroEmpresa object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroMovimentoRoteiroEmpresa(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String ANO_MES_MOVIMENTO = "anoMesMovimento";

	/**
	 * Description of the Field
	 */
	public final static String IMOVEL_ID = "imovel.id";

	public final static String IMOVEL = "imovel";

	public final static String HIDROMETRO = "imovel.hidrometroInstalacaoHistorico.hidrometro";

	public final static String ROTA_ID = "rota.id";

	public final static String FATURAMENTO_GRUPO_ID = "faturamentoGrupo.id";

	public final static String EMPRESA_ID = "empresa.id";

	public final static String LEITURISTA_ID = "leiturista.id";

	public final static String MEDICAO_TIPO = "medicaoTipo";

	public final static String MEDICAO_TIPO_ID = "medicaoTipo.id";

	public final static String LEITURA_ANORMALIDADE = "leituraAnormalidade";

	public final static String NUMERO_SEQUENCIAL_ROTA = "imovel.numeroSequencialRota";

	public final static String LIGACAO_AGUA = "imovel.ligacaoAgua";

	public final static String INDICADOR_EMISSAO = "indicadorEmissao";

	public final static String LIGACAO_AGUA_SITUACAO = "ligacaoAguaSituacao";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_FASE = "indicadorFase";
}
