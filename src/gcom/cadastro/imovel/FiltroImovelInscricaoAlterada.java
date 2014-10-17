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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro de consulta da entidade ImovelInscricaoAlterada
 * 
 * @author Luciano Galvao
 * @date 18/01/2013
 */
public class FiltroImovelInscricaoAlterada
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCliente object
	 */
	public FiltroImovelInscricaoAlterada() {

	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroImovelInscricaoAlterada(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String FATURAMENTO_GRUPO = "faturamentoGrupo";

	public final static String FATURAMENTO_GRUPO_ID = "faturamentoGrupo.id";

	public final static String LOCALIDADE_ANTERIOR = "localidadeAnterior";

	public final static String LOCALIDADE_ANTERIOR_ID = "localidadeAnterior.id";

	public final static String SETOR_COMERCIAL_ANTERIOR = "setorComercialAnterior";

	public final static String SETOR_COMERCIAL_ANTERIOR_ID = "setorComercialAnterior.id";

	public final static String QUADRA_ANTERIOR = "quadraAnterior";

	public final static String QUADRA_ANTERIOR_ID = "quadraAnterior.id";

	public final static String LOTE_ANTERIOR = "loteAnterior";

	public final static String SUBLOTE_ANTERIOR = "subLoteAnterior";

	public final static String TESTADA_LOTE_ANTERIOR = "testadaLoteAnterior";

	public final static String ROTA_ANTERIOR = "rotaAnterior";

	public final static String ROTA_ANTERIOR_ID = "rotaAnterior.id";

	public final static String LOCALIDADE_ATUAL = "localidadeAtual";

	public final static String LOCALIDADE_ATUAL_ID = "localidadeAtual.id";

	public final static String SETOR_COMERCIAL_ATUAL = "setorComercialAtual";

	public final static String SETOR_COMERCIAL_ATUAL_ID = "setorComercialAtual.id";

	public final static String QUADRA_ATUAL = "quadraAtual";

	public final static String QUADRA_ATUAL_ID = "quadraAtual.id";

	public final static String LOTE_ATUAL = "loteAtual";

	public final static String SUBLOTE_ATUAL = "subLoteAtual";

	public final static String TESTADA_LOTE_ATUAL = "testadaLoteAtual";

	public final static String ROTA_ATUAL = "rotaAtual";

	public final static String ROTA_ATUAL_ID = "rotaAtual.id";

	public final static String INDICADOR_ATUALIZADO = "indicadorAtualizado";

	public final static String INDICADOR_ALTERACAO_EXCLUIDA = "indicadorAlteracaoExcluida";

	public final static String INDICADOR_IMOVEL_EXCLUIDO = "indicadorImovelExcluido";

	public final static String INDICADOR_ERRO_ALTERACAO = "indicadorErroAlteracao";

	public final static String DATA_ALTERACAO_ONLINE = "dataAlteracaoOnline";

	public final static String DATA_ALTERACAO_BATCH = "dataAlteracaoBatch";

	public final static String ULTIMA_ALTERACAO = "ultimaAlteracao";
}
