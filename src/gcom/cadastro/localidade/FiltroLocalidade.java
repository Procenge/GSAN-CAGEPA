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

package gcom.cadastro.localidade;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 19 de Maio de 2005
 */
public class FiltroLocalidade
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Usado para consultar localidades. */
	private Integer idLocalidade;

	private String nomeLocalidade;

	private Short indicadorNomeIniciandoOuContendo;

	private Integer concessionaria;

	private Integer gerenciaRegional;

	private Integer unidadeNegocio;

	private Short indicadorUso;
	/* Fim Usado para consulta Filtro. */

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroLocalidade(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroLocalidade
	 */
	public FiltroLocalidade() {

	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricao";

	public final static String GERENCIAREGIONAL = "gerenciaRegional";

	/**
	 * Description of the Field
	 */
	public final static String ID_GERENCIA = "gerenciaRegional.id";

	public final static String UNIDADENEGOCIO = "unidadeNegocio";

	/**
	 * Description of the Field
	 */
	public final static String ID_UNIDADE_NEGOCIO = "unidadeNegocio.id";

	/**
	 * Description of the Field
	 */
	public final static String ID_ELO = "localidade";

	/**
	 * Description of the Field
	 */
	public final static String INDICADORUSO = "indicadorUso";

	public final static String LOCALIDADEPORTE = "localidadePorte";

	public final static String LOCALIDADEPORTE_ID = "localidadePorte.id";

	public final static String ID_ELO_ID = "localidade.id";

	public final static String ENDERECOREFERENCIA = "enderecoReferencia";

	public final static String ENDERECOREFERENCIA_ID = "enderecoReferencia.id";

	public final static String LOCALIDADECLASSE = "localidadeClasse";

	public final static String LOCALIDADECLASSE_ID = "localidadeClasse.id";

	public final static String LOGRADOUROCEP = "logradouroCep";

	public final static String LOGRADOUROCEP_ID = "logradouroCep.id";

	public final static String LOGRADOUROBAIRRO = "logradouroBairro";

	public final static String LOGRADOUROBAIRRO_ID = "logradouroBairro.id";

	public final static String FUNCIONARIO = "funcionario";

	public final static String FUNCIONARIO_ID = "funcionario.id";

	public final static String HIDROMETROLOCALARMAZENAGEM = "hidrometroLocalArmazenagem";

	public final static String HIDROMETROLOCALARMAZENAGEM_ID = "hidrometroLocalArmazenagem.id";

	public final static String MUNICIPIO = "municipio";

	public final static String MUNICIPIO_ID = "municipio.id";

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public Short getIndicadorNomeIniciandoOuContendo(){

		return indicadorNomeIniciandoOuContendo;
	}

	public void setIndicadorNomeIniciandoOuContendo(Short indicadorNomeIniciandoOuContendo){

		this.indicadorNomeIniciandoOuContendo = indicadorNomeIniciandoOuContendo;
	}

	public Integer getConcessionaria(){

		return concessionaria;
	}

	public void setConcessionaria(Integer concessionaria){

		this.concessionaria = concessionaria;
	}

	public Integer getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(Integer gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public Integer getUnidadeNegocio(){

		return unidadeNegocio;
	}

	public void setUnidadeNegocio(Integer unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

}
