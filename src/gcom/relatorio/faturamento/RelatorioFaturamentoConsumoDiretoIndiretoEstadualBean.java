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

/**
 * [UC3114] - Gerar Relatórios Faturamento Consumo Direto Indireto Estadual
 * 
 * @author Victon Santos
 * @date 26/09/2013
 */
public class RelatorioFaturamentoConsumoDiretoIndiretoEstadualBean
				implements RelatorioBean {

	private String referencia;//

	private String responsavel;// 1

	private String tipoResponsavel;// 1

	private String nomeLocalidade;//

	private String matricula;//

	private String usuario;//

	private String tipoAgua;//

	private String tipoEsgoto;//

	private String categoria;//

	private String economia;//

	private String endereco;//

	private Integer valorLeituraAnterior;

	private Integer valorLeituraAtual;

	private Integer consumoMedido;

	private Integer consumoFaturado;//

	private Integer consumoMedio;//

	public RelatorioFaturamentoConsumoDiretoIndiretoEstadualBean(String referencia, String responsavel, String tipoResponsavel,
																	String nomeLocalidade, String matricula, String usuario,
																	String tipoAgua, String tipoEsgoto, String categoria, String economia,
																	String endereco, Integer valorLeituraAnterior,
																	Integer valorLeituraAtual, Integer consumoMedido,
																	Integer consumoFaturado, Integer consumoMedio) {
		this.referencia = referencia; 
		this.responsavel=responsavel; 
		this.tipoResponsavel=tipoResponsavel;
		this.nomeLocalidade=nomeLocalidade; 
		this.matricula = matricula;
		this.usuario = usuario;
		this.tipoAgua = tipoAgua;
		this.tipoEsgoto = tipoEsgoto;
		this.categoria = categoria;
		this.economia = economia;
		this.endereco = endereco;
		this.valorLeituraAnterior = valorLeituraAnterior;
		this.valorLeituraAtual = valorLeituraAtual;
		this.consumoMedido = consumoMedido;
		this.consumoFaturado = consumoFaturado;
		this.consumoMedio = consumoMedio;
	}
	public RelatorioFaturamentoConsumoDiretoIndiretoEstadualBean() {
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}
	public String getResponsavel(){

		return responsavel;
	}

	public void setResponsavel(String responsavel){

		this.responsavel = responsavel;
	}

	public String getTipoResponsavel(){

		return tipoResponsavel;
	}

	public void setTipoResponsavel(String tipoResponsavel){

		this.tipoResponsavel = tipoResponsavel;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getUsuario(){

		return usuario;
	}

	public void setUsuario(String usuario){

		this.usuario = usuario;
	}

	public String getTipoAgua(){

		return tipoAgua;
	}

	public void setTipoAgua(String tipoAgua){

		this.tipoAgua = tipoAgua;
	}

	public String getTipoEsgoto(){

		return tipoEsgoto;
	}

	public void setTipoEsgoto(String tipoEsgoto){

		this.tipoEsgoto = tipoEsgoto;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getEconomia(){

		return economia;
	}

	public void setEconomia(String economia){

		this.economia = economia;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public Integer getValorLeituraAnterior(){

		return valorLeituraAnterior;
	}

	public void setValorLeituraAnterior(Integer valorLeituraAnterior){

		this.valorLeituraAnterior = valorLeituraAnterior;
	}

	public Integer getValorLeituraAtual(){

		return valorLeituraAtual;
	}

	public void setValorLeituraAtual(Integer valorLeituraAtual){

		this.valorLeituraAtual = valorLeituraAtual;
	}

	public Integer getConsumoMedido(){

		return consumoMedido;
	}

	public void setConsumoMedido(Integer consumoMedido){

		this.consumoMedido = consumoMedido;
	}

	public Integer getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(Integer consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}

	public Integer getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(Integer consumoMedio){

		this.consumoMedio = consumoMedio;
	}

}
