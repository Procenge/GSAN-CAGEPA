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

package gcom.integracao.acquagis;

/**
 * Classe que representa os dados recebidos pelo AcquaGis.
 * 
 * @author Virgínia Melo
 * @created 07 de Julho de 2009
 * @author Saulo Lima
 * @date 21/09/2009
 *       Novo atributo idImovel
 */
public class DadosAcquaGis {

	private String loginUsuario;

	private String idLogradouroBairro;

	private String idLogradouroCep;

	private String descricaoPontoReferencia;

	private String descricaoComplemento;

	private String numeroImovel;

	private String numeroCoordenadaNorte;

	private String numeroCoordenadaLeste;

	private String idEdificacaoReferencia;

	private String idImovel;

	public DadosAcquaGis() {

	}

	public DadosAcquaGis(String loginUsuario, String idLogradouroBairro, String idLogradouroCep, String descricaoPontoReferencia,
							String descricaoComplemento, String numeroImovel, String numeroCoordenadaNorte, String numeroCoordenadaLeste,
							String idEdificacaoReferencia, String idImovel) {

		this.loginUsuario = loginUsuario;
		this.idLogradouroBairro = idLogradouroBairro;
		this.idLogradouroCep = idLogradouroCep;
		this.descricaoPontoReferencia = descricaoPontoReferencia;
		this.descricaoComplemento = descricaoComplemento;
		this.numeroImovel = numeroImovel;
		this.numeroCoordenadaNorte = numeroCoordenadaNorte;
		this.numeroCoordenadaLeste = numeroCoordenadaLeste;
		this.idEdificacaoReferencia = idEdificacaoReferencia;
		this.idImovel = idImovel;
	}

	public String getLoginUsuario(){

		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario){

		this.loginUsuario = loginUsuario;
	}

	public String getIdLogradouroBairro(){

		return idLogradouroBairro;
	}

	public void setIdLogradouroBairro(String idLogradouroBairro){

		this.idLogradouroBairro = idLogradouroBairro;
	}

	public String getIdLogradouroCep(){

		return idLogradouroCep;
	}

	public void setIdLogradouroCep(String idLogradouroCep){

		this.idLogradouroCep = idLogradouroCep;
	}

	public String getDescricaoPontoReferencia(){

		return descricaoPontoReferencia;
	}

	public void setDescricaoPontoReferencia(String descricaoPontoReferencia){

		this.descricaoPontoReferencia = descricaoPontoReferencia;
	}

	public String getDescricaoComplemento(){

		return descricaoComplemento;
	}

	public void setDescricaoComplemento(String descricaoComplemento){

		this.descricaoComplemento = descricaoComplemento;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getNumeroCoordenadaNorte(){

		return numeroCoordenadaNorte;
	}

	public void setNumeroCoordenadaNorte(String numeroCoordenadaNorte){

		this.numeroCoordenadaNorte = numeroCoordenadaNorte;
	}

	public String getNumeroCoordenadaLeste(){

		return numeroCoordenadaLeste;
	}

	public void setNumeroCoordenadaLeste(String numeroCoordenadaLeste){

		this.numeroCoordenadaLeste = numeroCoordenadaLeste;
	}

	public String getIdEdificacaoReferencia(){

		return idEdificacaoReferencia;
	}

	public void setIdEdificacaoReferencia(String idEdificacaoReferencia){

		this.idEdificacaoReferencia = idEdificacaoReferencia;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}
}
