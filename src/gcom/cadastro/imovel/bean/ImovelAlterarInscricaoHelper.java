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

package gcom.cadastro.imovel.bean;

import java.io.Serializable;

/**
 * @author Hibernate CodeGenerator
 * @created 29 de Janeiro de 2013
 */
public class ImovelAlterarInscricaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** * Campos internos ** */

	// Constantes

	private static final Integer INTEGER_ZERO = new Integer(0);

	private static final Short SHORT_ZERO = new Short("0");

	// Títulos utilizados na emissão do Relatório de Imóveis com Inscrição Alterada
	public static final String TITULO_RELACAO_LISTA_IMOVEIS_ALTERACAO_AGENDADA_ENCERRAMENTO = "Relação dos Imóveis com Alteração Agendada para o Encerramento do Faturamento";

	public static final String TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS = "Relação dos Imóveis Com Inscrição Alteração, Com Mudança da Quadra, Com Mudança da Rota e Com Mudança do Grupo de Faturamento";

	public static final String TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS_SEM_MUDANCA_GRUPO_FATURAMENTO = "Relação dos Imóveis Com Inscrição Alteração, Com Mudança da Quadra, Com Mudança da Rota e Sem Mudança do Grupo de Faturamento";

	public static final String TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS_SEM_MUDANCA_ROTA_GRUPO_FATURAMENTO = "Relação dos Imóveis Com Inscrição Alteração, Com Mudança da Quadra, Sem Mudança da Rota e Sem Mudança do Grupo de Faturamento";

	public static final String TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS_SEM_MUDANCA_QUADRA_ROTA_GRUPO_FATURAMENTO = "Relação dos Imóveis Com Inscrição Alteração, Sem Mudança da Quadra, Sem Mudança da Rota e Sem Mudança do Grupo de Faturamento";

	// Atributos
	private String tituloRelacaoLista;

	private String enderecoImovel;

	private Integer idImovel;

	private Integer idLocalidadeAnterior;

	private Integer idSetorComercialAnterior;

	private Integer codigoSetorComercialAnterior;

	private Integer idQuadraAnterior;

	private Integer numeroQuadraAnterior;

	private short loteAnterior;

	private short subLoteAnterior;

	private Short testadaLoteAnterior;

	private Integer idRotaAnterior;

	private Short codigoRotaAnterior;

	private Integer idGrupoFaturamentoAnterior;

	private Integer idLocalidadeAtual;

	private Integer idSetorComercialAtual;

	private Integer codigoSetorComercialAtual;

	private Integer idQuadraAtual;

	private Integer numeroQuadraAtual;

	private short loteAtual;

	private short subLoteAtual;

	private Short testadaLoteAtual;

	private Integer idRotaAtual;

	private Short codigoRotaAtual;

	private Integer idGrupoFaturamentoAtual;

	public ImovelAlterarInscricaoHelper(String tituloRelacaoLista) {

		this.tituloRelacaoLista = tituloRelacaoLista;
		
		// Atribuindo os valores Default
		this.idImovel = INTEGER_ZERO;
		this.idLocalidadeAnterior = INTEGER_ZERO;
		this.idSetorComercialAnterior = INTEGER_ZERO;
		this.codigoSetorComercialAnterior = INTEGER_ZERO;
		this.idQuadraAnterior = INTEGER_ZERO;
		this.numeroQuadraAnterior = INTEGER_ZERO;
		this.loteAnterior = 0;
		this.subLoteAnterior = 0;
		this.testadaLoteAnterior = SHORT_ZERO;
		this.idRotaAnterior = INTEGER_ZERO;
		this.codigoRotaAnterior = SHORT_ZERO;
		this.idGrupoFaturamentoAnterior = INTEGER_ZERO;
		this.idLocalidadeAtual = INTEGER_ZERO;
		this.idSetorComercialAtual = INTEGER_ZERO;
		this.codigoSetorComercialAtual = INTEGER_ZERO;
		this.idQuadraAtual = INTEGER_ZERO;
		this.numeroQuadraAtual = INTEGER_ZERO;
		this.loteAtual = 0;
		this.subLoteAtual = 0;
		this.testadaLoteAtual = SHORT_ZERO;
		this.idRotaAtual = INTEGER_ZERO;
		this.codigoRotaAtual = SHORT_ZERO;
	}



	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdLocalidadeAnterior(){

		return idLocalidadeAnterior;
	}

	public void setIdLocalidadeAnterior(Integer idLocalidadeAnterior){

		this.idLocalidadeAnterior = idLocalidadeAnterior;
	}

	public Integer getIdSetorComercialAnterior(){

		return idSetorComercialAnterior;
	}

	public void setIdSetorComercialAnterior(Integer idSetorComercialAnterior){

		this.idSetorComercialAnterior = idSetorComercialAnterior;
	}

	public Integer getCodigoSetorComercialAnterior(){

		return codigoSetorComercialAnterior;
	}

	public void setCodigoSetorComercialAnterior(Integer codigoSetorComercialAnterior){

		this.codigoSetorComercialAnterior = codigoSetorComercialAnterior;
	}

	public Integer getIdQuadraAnterior(){

		return idQuadraAnterior;
	}

	public void setIdQuadraAnterior(Integer idQuadraAnterior){

		this.idQuadraAnterior = idQuadraAnterior;
	}

	public Integer getNumeroQuadraAnterior(){

		return numeroQuadraAnterior;
	}

	public void setNumeroQuadraAnterior(Integer numeroQuadraAnterior){

		this.numeroQuadraAnterior = numeroQuadraAnterior;
	}

	public short getLoteAnterior(){

		return loteAnterior;
	}

	public void setLoteAnterior(short loteAnterior){

		this.loteAnterior = loteAnterior;
	}

	public short getSubLoteAnterior(){

		return subLoteAnterior;
	}

	public void setSubLoteAnterior(short subLoteAnterior){

		this.subLoteAnterior = subLoteAnterior;
	}

	public Short getTestadaLoteAnterior(){

		return testadaLoteAnterior;
	}

	public void setTestadaLoteAnterior(Short testadaLoteAnterior){

		this.testadaLoteAnterior = testadaLoteAnterior;
	}

	public Integer getIdRotaAnterior(){

		return idRotaAnterior;
	}

	public void setIdRotaAnterior(Integer idRotaAnterior){

		this.idRotaAnterior = idRotaAnterior;
	}

	public Short getCodigoRotaAnterior(){

		return codigoRotaAnterior;
	}

	public void setCodigoRotaAnterior(Short codigoRotaAnterior){

		this.codigoRotaAnterior = codigoRotaAnterior;
	}

	public Integer getIdGrupoFaturamentoAnterior(){

		return idGrupoFaturamentoAnterior;
	}

	public void setIdGrupoFaturamentoAnterior(Integer idGrupoFaturamentoAnterior){

		this.idGrupoFaturamentoAnterior = idGrupoFaturamentoAnterior;
	}

	public Integer getIdLocalidadeAtual(){

		return idLocalidadeAtual;
	}

	public void setIdLocalidadeAtual(Integer idLocalidadeAtual){

		this.idLocalidadeAtual = idLocalidadeAtual;
	}

	public Integer getIdSetorComercialAtual(){

		return idSetorComercialAtual;
	}

	public void setIdSetorComercialAtual(Integer idSetorComercialAtual){

		this.idSetorComercialAtual = idSetorComercialAtual;
	}

	public Integer getCodigoSetorComercialAtual(){

		return codigoSetorComercialAtual;
	}

	public void setCodigoSetorComercialAtual(Integer codigoSetorComercialAtual){

		this.codigoSetorComercialAtual = codigoSetorComercialAtual;
	}

	public Integer getIdQuadraAtual(){

		return idQuadraAtual;
	}

	public void setIdQuadraAtual(Integer idQuadraAtual){

		this.idQuadraAtual = idQuadraAtual;
	}

	public Integer getNumeroQuadraAtual(){

		return numeroQuadraAtual;
	}

	public void setNumeroQuadraAtual(Integer numeroQuadraAtual){

		this.numeroQuadraAtual = numeroQuadraAtual;
	}

	public short getLoteAtual(){

		return loteAtual;
	}

	public void setLoteAtual(short loteAtual){

		this.loteAtual = loteAtual;
	}

	public short getSubLoteAtual(){

		return subLoteAtual;
	}

	public void setSubLoteAtual(short subLoteAtual){

		this.subLoteAtual = subLoteAtual;
	}

	public Short getTestadaLoteAtual(){

		return testadaLoteAtual;
	}

	public void setTestadaLoteAtual(Short testadaLoteAtual){

		this.testadaLoteAtual = testadaLoteAtual;
	}

	public Integer getIdRotaAtual(){

		return idRotaAtual;
	}

	public void setIdRotaAtual(Integer idRotaAtual){

		this.idRotaAtual = idRotaAtual;
	}

	public Short getCodigoRotaAtual(){

		return codigoRotaAtual;
	}

	public void setCodigoRotaAtual(Short codigoRotaAtual){

		this.codigoRotaAtual = codigoRotaAtual;
	}

	public Integer getIdGrupoFaturamentoAtual(){

		return idGrupoFaturamentoAtual;
	}

	public void setIdGrupoFaturamentoAtual(Integer idGrupoFaturamentoAtual){

		this.idGrupoFaturamentoAtual = idGrupoFaturamentoAtual;
	}

	public String getTituloRelacaoLista(){

		return tituloRelacaoLista;
	}

	public void setTituloRelacaoLista(String tituloRelacaoLista){

		this.tituloRelacaoLista = tituloRelacaoLista;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

}
