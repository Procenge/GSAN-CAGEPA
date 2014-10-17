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

package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * [] - Gerar Relatório Maiores Consumidores
 * 
 * @author Victon Santos
 * @date 13/11/2013
 */
public class RelatorioMaioresConsumidoresBean
				implements RelatorioBean {

	private String referencia;//

	private String localidade;//

	private String setor;//

	private String quadra;//

	private String tipo;//

	private String matricula;//

	private String nome;//

	private Integer consumo;//

	private BigDecimal valorConta;

	public RelatorioMaioresConsumidoresBean(String referencia, String localidade, String setor, String quadra, String tipo,
											String matricula, String nome, Integer consumo, BigDecimal valorConta) {

		this.referencia = referencia;
		this.localidade = localidade;
		this.setor = setor;
		this.quadra = quadra;
		this.tipo = tipo;
		this.matricula = matricula;
		this.nome = nome;
		this.consumo = consumo;
		this.valorConta = valorConta;
	}
	public RelatorioMaioresConsumidoresBean() {
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getSetor(){

		return setor;
	}

	public void setSetor(String setor){

		this.setor = setor;
	}

	public String getQuadra(){

		return quadra;
	}

	public void setQuadra(String quadra){

		this.quadra = quadra;
	}

	public String getTipo(){

		return tipo;
	}

	public void setTipo(String tipo){

		this.tipo = tipo;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public Integer getConsumo(){

		return consumo;
	}

	public void setConsumo(Integer consumo){

		this.consumo = consumo;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

}
