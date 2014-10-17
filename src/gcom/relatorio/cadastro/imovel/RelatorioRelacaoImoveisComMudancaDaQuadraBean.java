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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.relatorio.cadastro.imovel;

import gcom.relatorio.RelatorioBean;

public class RelatorioRelacaoImoveisComMudancaDaQuadraBean
				implements RelatorioBean {

	// [UC0074] Alterar Inscri��o de Im�vel
	// [SB0002] - Emitir Relat�rio dos Im�veis da Inscri��o Origem com Mudan�a da Quadra
	// 1.1.3.
	private String idLocalidade;

	private String nomeLocalidade;

	private String cdSetorComercial;

	private String nomeSetorComercial;

	private String numeroQuadra;

	// 1.2. Dados dos Im�veis:
	// 1.2.1. Matr�cula
	private String matricula;

	// 1.2.2. Endere�o do Im�vel
	private String endereco;

	// 1.2.3. Inscri��o
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
	// ******** M�todos Getters e Setters ***********************
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
