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

package gcom.cobranca.parcelamento;

import gcom.cobranca.parcelamento.Parcelamento;

import java.util.Date;

public class ParcelamentoTermoTestemunhas {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Parcelamento parcelamento;

	private String nomeTestemunha1;

	private String cpfTestemunha1;

	private String nomeTestemunha2;

	private String cpfTestemunha2;

	private Date ultimaAlteracao;


	public ParcelamentoTermoTestemunhas() {

	}

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Parcelamento getParcelamento(){

		return parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	public String getNomeTestemunha1(){

		return nomeTestemunha1;
	}

	public void setNomeTestemunha1(String nomeTestemunha1){

		this.nomeTestemunha1 = nomeTestemunha1;
	}

	public String getCpfTestemunha1(){

		return cpfTestemunha1;
	}

	public void setCpfTestemunha1(String cpfTestemunha1){

		this.cpfTestemunha1 = cpfTestemunha1;
	}

	public String getNomeTestemunha2(){

		return nomeTestemunha2;
	}

	public void setNomeTestemunha2(String nomeTestemunha2){

		this.nomeTestemunha2 = nomeTestemunha2;
	}

	public String getCpfTestemunha2(){

		return cpfTestemunha2;
	}

	public void setCpfTestemunha2(String cpfTestemunha2){

		this.cpfTestemunha2 = cpfTestemunha2;
	}

	public Date getUltimaAlteracao(){
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){
		this.ultimaAlteracao = ultimaAlteracao;
	}

}
