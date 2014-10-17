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

package gcom.cadastro.geografico;

import gcom.arrecadacao.banco.Agencia;
import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os critérios de busca de uma municipio
 * 
 * @author Sávio Luiz
 * @created 22 de Abril de 2005
 */

public class FiltroMunicipio
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroMunicipio object
	 */
	public FiltroMunicipio() {

	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroMunicipio(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Código do municipio
	 */
	public final static String ID = "id";

	/**
	 * Nome do municipio
	 */
	public final static String NOME = "nome";

	/**
	 * Código da Regiao de Desenvolvimento
	 */
	public final static String REGIAO_DESENVOLVOMENTO_ID = "regiaoDesenvolvimento.id";

	public final static String REGIAO_DESENVOLVOMENTO = "regiaoDesenvolvimento";

	/**
	 * Description of the Field
	 */
	public final static String REGIAO_ID = "microrregiao.regiao.id";

	public final static String REGIAO = "microrregiao.regiao";

	/**
	 * Código da Microrregiao
	 */
	public final static String MICRORREGICAO_ID = "microrregiao.id";

	public final static String MICRORREGICAO = "microrregiao";

	/**
	 * Código da Unidade Federação
	 */
	public final static String UNIDADE_FEDERACAO_ID = "unidadeFederacao.id";

	public final static String UNIDADE_FEDERACAO = "unidadeFederacao";

	/**
	 * CEP Inicial
	 */
	public final static String CEPINICIO = "cepInicio";

	/**
	 * CEP Final
	 */
	public final static String CEPFIM = "cepFim";

	/**
	 * Indicador de Uso
	 */
	public final static String INDICADOR_USO = "indicadorUso";

	/**
	 * Código do DDD
	 */
	public final static String DDD = "ddd";

	/**
	 * Código do NOMEPREFEITURA
	 */
	public final static String NOMEPREFEITURA = "nomePrefeitura";

	/**
	 * Código do ENDERECOPREFEITURA
	 */
	public final static String ENDERECOPREFEITURA = "enderecoPrefeitura";

	/**
	 * Código do NUMEROCNPJPREFEITURA
	 */
	public final static String NUMEROCNPJPREFEITURA = "numeroCnpjPrefeitura";

	/**
	 * Código do AGENCIA
	 */
	public final static String AGENCIA = "agencia.id";

	public final static String AG = "agencia";

	/**
	 * Código do NUMEROCONTABANCARIA
	 */
	public final static String NUMEROCONTABANCARIA = "numeroContaBancaria";

	/**
	 * Código do NOMEPREFEITO
	 */
	public final static String NOMEPREFEITO = "nomePrefeito";

	/**
	 * Código do NUMEROCPFPREFEITO
	 */
	public final static String NUMEROCPFPREFEITO = "numeroCpfPrefeito";

	/**
	 * Código do NOMEPARTIDOPREFEITO
	 */
	public final static String NOMEPARTIDOPREFEITO = "nomePartidoPrefeito";

	/**
	 * Código do NACIONALIDADEPREFEITO
	 */
	public final static String NACIONALIDADEPREFEITO = "nacionalidadePrefeito";

	/**
	 * Código do ESTADOCIVILPREFEITO
	 */
	public final static String ESTADOCIVILPREFEITO = "estadoCivilPrefeito";

}
