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

package gcom.relatorio.cadastro.imovel;

import gcom.relatorio.RelatorioBean;

/**
 * Bean utilizado na construção do Relatório de Imóveis com inscrição alterada
 * 
 * @author Luciano Galvão
 * @date 14/02/2013
 */
public class RelatorioImoveisInscricaoAlteradaBean
				implements RelatorioBean {

	private String tituloRelacaoLista;

	private String idImovel;

	private String enderecoImovel;

	private String idLocalidadeAnterior;

	private String codigoSetorComercialAnterior;

	private String numeroQuadraAnterior;

	private String loteAnterior;

	private String codigoRotaAnterior;

	private String idGrupoFaturamentoAnterior;

	private String idLocalidadeAtual;

	private String codigoSetorComercialAtual;

	private String numeroQuadraAtual;

	private String loteAtual;

	private String codigoRotaAtual;

	private String idGrupoFaturamentoAtual;

	/**
	 * Construtor recebendo todos os parametros da classe
	 */

	public RelatorioImoveisInscricaoAlteradaBean(String tituloRelacaoLista, String idImovel, String enderecoImovel,
													String idLocalidadeAnterior, String codigoSetorComercialAnterior,
													String numeroQuadraAnterior, String loteAnterior, String codigoRotaAnterior,
													String idGrupoFaturamentoAnterior, String idLocalidadeAtual,
													String codigoSetorComercialAtual, String numeroQuadraAtual, String loteAtual,
													String codigoRotaAtual, String idGrupoFaturamentoAtual) {

		super();
		this.tituloRelacaoLista = tituloRelacaoLista;
		this.idImovel = idImovel;
		this.enderecoImovel = enderecoImovel;
		this.idLocalidadeAnterior = idLocalidadeAnterior;
		this.codigoSetorComercialAnterior = codigoSetorComercialAnterior;
		this.numeroQuadraAnterior = numeroQuadraAnterior;
		this.loteAnterior = loteAnterior;
		this.codigoRotaAnterior = codigoRotaAnterior;
		this.idGrupoFaturamentoAnterior = idGrupoFaturamentoAnterior;
		this.idLocalidadeAtual = idLocalidadeAtual;
		this.codigoSetorComercialAtual = codigoSetorComercialAtual;
		this.numeroQuadraAtual = numeroQuadraAtual;
		this.loteAtual = loteAtual;
		this.codigoRotaAtual = codigoRotaAtual;
		this.idGrupoFaturamentoAtual = idGrupoFaturamentoAtual;
	}

	public String getTituloRelacaoLista(){

		return tituloRelacaoLista;
	}

	public void setTituloRelacaoLista(String tituloRelacaoLista){

		this.tituloRelacaoLista = tituloRelacaoLista;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getIdLocalidadeAnterior(){

		return idLocalidadeAnterior;
	}

	public void setIdLocalidadeAnterior(String idLocalidadeAnterior){

		this.idLocalidadeAnterior = idLocalidadeAnterior;
	}

	public String getCodigoSetorComercialAnterior(){

		return codigoSetorComercialAnterior;
	}

	public void setCodigoSetorComercialAnterior(String codigoSetorComercialAnterior){

		this.codigoSetorComercialAnterior = codigoSetorComercialAnterior;
	}

	public String getNumeroQuadraAnterior(){

		return numeroQuadraAnterior;
	}

	public void setNumeroQuadraAnterior(String numeroQuadraAnterior){

		this.numeroQuadraAnterior = numeroQuadraAnterior;
	}

	public String getLoteAnterior(){

		return loteAnterior;
	}

	public void setLoteAnterior(String loteAnterior){

		this.loteAnterior = loteAnterior;
	}

	public String getCodigoRotaAnterior(){

		return codigoRotaAnterior;
	}

	public void setCodigoRotaAnterior(String codigoRotaAnterior){

		this.codigoRotaAnterior = codigoRotaAnterior;
	}

	public String getIdGrupoFaturamentoAnterior(){

		return idGrupoFaturamentoAnterior;
	}

	public void setIdGrupoFaturamentoAnterior(String idGrupoFaturamentoAnterior){

		this.idGrupoFaturamentoAnterior = idGrupoFaturamentoAnterior;
	}

	public String getIdLocalidadeAtual(){

		return idLocalidadeAtual;
	}

	public void setIdLocalidadeAtual(String idLocalidadeAtual){

		this.idLocalidadeAtual = idLocalidadeAtual;
	}

	public String getCodigoSetorComercialAtual(){

		return codigoSetorComercialAtual;
	}

	public void setCodigoSetorComercialAtual(String codigoSetorComercialAtual){

		this.codigoSetorComercialAtual = codigoSetorComercialAtual;
	}

	public String getNumeroQuadraAtual(){

		return numeroQuadraAtual;
	}

	public void setNumeroQuadraAtual(String numeroQuadraAtual){

		this.numeroQuadraAtual = numeroQuadraAtual;
	}

	public String getLoteAtual(){

		return loteAtual;
	}

	public void setLoteAtual(String loteAtual){

		this.loteAtual = loteAtual;
	}

	public String getCodigoRotaAtual(){

		return codigoRotaAtual;
	}

	public void setCodigoRotaAtual(String codigoRotaAtual){

		this.codigoRotaAtual = codigoRotaAtual;
	}

	public String getIdGrupoFaturamentoAtual(){

		return idGrupoFaturamentoAtual;
	}

	public void setIdGrupoFaturamentoAtual(String idGrupoFaturamentoAtual){

		this.idGrupoFaturamentoAtual = idGrupoFaturamentoAtual;
	}

}
