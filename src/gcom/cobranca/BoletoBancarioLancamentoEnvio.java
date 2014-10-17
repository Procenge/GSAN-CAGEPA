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

package gcom.cobranca;

import java.io.Serializable;
import java.util.Date;

public class BoletoBancarioLancamentoEnvio
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final BoletoBancarioLancamentoEnvio ENTRADA_DE_TITULOS = new BoletoBancarioLancamentoEnvio(1);

	public static final BoletoBancarioLancamentoEnvio PEDIDO_BAIXA = new BoletoBancarioLancamentoEnvio(2);

	public static final BoletoBancarioLancamentoEnvio SUSTAR_PROTESTO_BAIXAR_TITUL = new BoletoBancarioLancamentoEnvio(10);

	public static final Integer ENTRADA_TITULOS = Integer.valueOf("1");

	public static final Integer PEDIDO_DE_BAIXA = Integer.valueOf("2");

	public static final Integer SUSTAR_PROTESTO_E_BAIXAR_TITUL = Integer.valueOf("10");

	private Integer id;

	private String descricaoLancamentoEnvio;

	private Short indicadorUso;

	private Short indicadorComando;

	private Short indicadorAlterarDados;

	private Date ultimaAlteracao;

	private BoletoBancarioSituacao situacaoAtualObrigatoria;

	public BoletoBancarioLancamentoEnvio() {

	}

	public BoletoBancarioLancamentoEnvio(Integer id, String descricaoLancamentoEnvio, Short indicadorUso, Short indicadorComando,
											Short indicadorAlterarDados, Date ultimaAlteracao,
											BoletoBancarioSituacao situacaoAtualObrigatoria) {

		this.id = id;
		this.descricaoLancamentoEnvio = descricaoLancamentoEnvio;
		this.indicadorUso = indicadorUso;
		this.indicadorComando = indicadorComando;
		this.indicadorAlterarDados = indicadorAlterarDados;
		this.ultimaAlteracao = ultimaAlteracao;
		this.situacaoAtualObrigatoria = situacaoAtualObrigatoria;
	}

	public BoletoBancarioLancamentoEnvio(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricaoLancamentoEnvio(){

		return descricaoLancamentoEnvio;
	}

	public void setDescricaoLancamentoEnvio(String descricaoLancamentoEnvio){

		this.descricaoLancamentoEnvio = descricaoLancamentoEnvio;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Short getIndicadorComando(){

		return indicadorComando;
	}

	public void setIndicadorComando(Short indicadorComando){

		this.indicadorComando = indicadorComando;
	}

	public Short getIndicadorAlterarDados(){

		return indicadorAlterarDados;
	}

	public void setIndicadorAlterarDados(Short indicadorAlterarDados){

		this.indicadorAlterarDados = indicadorAlterarDados;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public BoletoBancarioSituacao getSituacaoAtualObrigatoria(){

		return situacaoAtualObrigatoria;
	}

	public void setSituacaoAtualObrigatoria(BoletoBancarioSituacao situacaoAtualObrigatoria){

		this.situacaoAtualObrigatoria = situacaoAtualObrigatoria;
	}

	public String getDescricaoComId(){

		String descricaoComId = null;

		Integer id = this.getId();
		String descricao = this.getDescricaoLancamentoEnvio();

		if(id != null && id.compareTo(10) == -1){
			descricaoComId = "0" + id + " - " + descricao;
		}else if(id != null){
			descricaoComId = id + " - " + descricao;
		}else{
			descricaoComId = " - " + descricao;
		}

		return descricaoComId;
	}

}
