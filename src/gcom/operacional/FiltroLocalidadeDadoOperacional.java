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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.operacional;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os critérios de busca do Dados Operacional da Localidade
 * 
 * @author isilva
 * @date 25/01/2011
 */
public class FiltroLocalidadeDadoOperacional
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCliente object
	 */
	public FiltroLocalidadeDadoOperacional() {

	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroLocalidadeDadoOperacional(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String ULTIMA_ALTERACAO = "ultimaAlteracao";

	public final static String ANOMES_REFERENCIA = "anoMesReferencia";

	public final static String LOCALIDADE = "localidade";

	public final static String LOCALIDADE_ID = "localidade.id";

	public final static String VOLUME_PRODUZIDO = "volumeProduzido";

	public final static String EXTENSAO_REDEAGUA = "extensaoRedeAgua";

	public final static String EXTENSAO_REDEESGOTO = "extensaoRedeEsgoto";

	public final static String QTD_FUNCIONARIOS_ADMINISTRACAO = "qtdFuncionariosAdministracao";

	public final static String QTD_FUNCIONARIOS_ADMINISTRACAO_TERCERIZADOS = "qtdFuncionariosAdministracaoTercerizados";

	public final static String QTD_FUNCIONARIOS_OPERACAO = "qtdFuncionariosOperacao";

	public final static String QTD_FUNCIONARIOS_OPERACAO_TERCERIZADOS = "qtdFuncionariosOperacaoTercerizados";

	public final static String QTD_SULFATO_ALUMINIO = "qtdSulfatoAluminio";

	public final static String QTD_CLORO_GASOSO = "qtdCloroGasoso";

	public final static String QTD_HIPOCLORITO_SODIO = "qtdHipocloritoSodio";

	public final static String QUANTIDADE_FLUOR = "quantidadeFluor";

	public final static String QTD_CONSUMO_ENERGIA = "qtdConsumoEnergia";

	public final static String QTD_HORAS_PARALISADAS = "qtdHorasParalisadas";

}
