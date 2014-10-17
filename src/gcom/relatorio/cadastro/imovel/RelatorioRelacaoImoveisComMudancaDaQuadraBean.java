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

public class RelatorioRelacaoImoveisComMudancaDaQuadraBean
				implements RelatorioBean {

	// [UC0074] Alterar Inscrição de Imóvel
	// [SB0002] - Emitir Relatório dos Imóveis da Inscrição Origem com Mudança da Quadra
	// 1.1.3.
	private String idLocalidade;

	private String nomeLocalidade;

	private String cdSetorComercial;

	private String nomeSetorComercial;

	private String numeroQuadra;

	// 1.2. Dados dos Imóveis:
	// 1.2.1. Matrícula
	private String matricula;

	// 1.2.2. Endereço do Imóvel
	private String endereco;

	// 1.2.3. Inscrição
	// 1.2.3.1. Localidade
	private String idLocalidadeInscricao;

	// 1.2.3.2. Setor Comercial
	private String cdSetorComercialIncricao;

	// 1.2.3.3. Quadra
	private String numeroQuadraInscricao;

	// 1.2.3.4. Lote
	private String numeroLoteInscricao;

	// 1.2.3.5. Sublote
	private String numeroSubLoteInscricao;

	// 1.2.4. Rota
	private String codigoRotaInscricao;

	// ==========================================================
	// ******** Métodos Getters e Setters ***********************
	// ==========================================================

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCdSetorComercial(){

		return cdSetorComercial;
	}

	public void setCdSetorComercial(String cdSetorComercial){

		this.cdSetorComercial = cdSetorComercial;
	}

	public String getNomeSetorComercial(){

		return nomeSetorComercial;
	}

	public void setNomeSetorComercial(String nomeSetorComercial){

		this.nomeSetorComercial = nomeSetorComercial;
	}

	public String getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(String numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getIdLocalidadeInscricao(){

		return idLocalidadeInscricao;
	}

	public void setIdLocalidadeInscricao(String idLocalidadeInscricao){

		this.idLocalidadeInscricao = idLocalidadeInscricao;
	}

	public String getCdSetorComercialIncricao(){

		return cdSetorComercialIncricao;
	}

	public void setCdSetorComercialIncricao(String cdSetorComercialIncricao){

		this.cdSetorComercialIncricao = cdSetorComercialIncricao;
	}

	public String getNumeroQuadraInscricao(){

		return numeroQuadraInscricao;
	}

	public void setNumeroQuadraInscricao(String numeroQuadraInscricao){

		this.numeroQuadraInscricao = numeroQuadraInscricao;
	}

	public String getNumeroLoteInscricao(){

		return numeroLoteInscricao;
	}

	public void setNumeroLoteInscricao(String numeroLoteInscricao){

		this.numeroLoteInscricao = numeroLoteInscricao;
	}

	public String getNumeroSubLoteInscricao(){

		return numeroSubLoteInscricao;
	}

	public void setNumeroSubLoteInscricao(String numeroSubLoteInscricao){

		this.numeroSubLoteInscricao = numeroSubLoteInscricao;
	}

	public String getCodigoRotaInscricao(){

		return codigoRotaInscricao;
	}

	public void setCodigoRotaInscricao(String codigoRotaInscricao){

		this.codigoRotaInscricao = codigoRotaInscricao;
	}

}
