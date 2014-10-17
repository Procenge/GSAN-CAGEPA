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

package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

/**
 * Gerar e Emitir Extrato de Débito por Cliente
 * 
 * @author Ana Maria
 * @date 09/04/2007
 */

public class RelatorioExtratoDebitoClienteDetailBean
				implements RelatorioBean {

	private String mesAno;

	private String matricula;

	private String vencimentoFatura;

	private String valorFatura;

	public RelatorioExtratoDebitoClienteDetailBean(String mesAno, String matricula, String vencimentoFatura, String valorFatura) {

		this.mesAno = mesAno;
		this.matricula = matricula;
		this.vencimentoFatura = vencimentoFatura;
		this.valorFatura = valorFatura;
	}

	/**
	 * @return Retorna o campo matricula.
	 */
	public String getMatricula(){

		return matricula;
	}

	/**
	 * @param matricula
	 *            O matricula a ser setado.
	 */
	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	/**
	 * @return Retorna o campo mesAno.
	 */
	public String getMesAno(){

		return mesAno;
	}

	/**
	 * @param mesAno
	 *            O mesAno a ser setado.
	 */
	public void setMesAno(String mesAno){

		this.mesAno = mesAno;
	}

	/**
	 * @return Retorna o campo valorFatura.
	 */
	public String getValorFatura(){

		return valorFatura;
	}

	/**
	 * @param valorFatura
	 *            O valorFatura a ser setado.
	 */
	public void setValorFatura(String valorFatura){

		this.valorFatura = valorFatura;
	}

	/**
	 * @return Retorna o campo vencimentoFatura.
	 */
	public String getVencimentoFatura(){

		return vencimentoFatura;
	}

	/**
	 * @param vencimentoFatura
	 *            O vencimentoFatura a ser setado.
	 */
	public void setVencimentoFatura(String vencimentoFatura){

		this.vencimentoFatura = vencimentoFatura;
	}

}
