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

package gcom.faturamento.consumotarifa;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.consumo.CalculoTipo;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()
public class ConsumoTarifaVigencia
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_INSERIR_CONSUMO_TARIFA = 367;

	public static final int ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA = 382;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private Date dataVigencia;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	private Set consumoTarifaCategorias;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private String descricaoAtoAdministrativo;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private ConsumoTarifa consumoTarifa;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private CalculoTipo calculoTipo;

	/** full constructor */
	public ConsumoTarifaVigencia(Date dataVigencia, Date ultimaAlteracao, gcom.faturamento.consumotarifa.ConsumoTarifa consumoTarifa) {

		this.dataVigencia = dataVigencia;
		this.ultimaAlteracao = ultimaAlteracao;
		this.consumoTarifa = consumoTarifa;
	}

	/** default constructor */
	public ConsumoTarifaVigencia() {

	}

	/** minimal constructor */
	public ConsumoTarifaVigencia(gcom.faturamento.consumotarifa.ConsumoTarifa consumoTarifa) {

		this.consumoTarifa = consumoTarifa;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataVigencia(){

		return this.dataVigencia;
	}

	public void setDataVigencia(Date dataVigencia){

		this.dataVigencia = dataVigencia;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.faturamento.consumotarifa.ConsumoTarifa getConsumoTarifa(){

		return this.consumoTarifa;
	}

	public void setConsumoTarifa(gcom.faturamento.consumotarifa.ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Set getConsumoTarifaCategorias(){

		return consumoTarifaCategorias;
	}

	public void setConsumoTarifaCategorias(Set consumoTarifaCategorias){

		this.consumoTarifaCategorias = consumoTarifaCategorias;
	}

	public CalculoTipo getCalculoTipo(){

		return calculoTipo;
	}

	public void setCalculoTipo(CalculoTipo calculoTipo){

		this.calculoTipo = calculoTipo;
	}

	public String getDescricaoAtoAdministrativo(){

		return descricaoAtoAdministrativo;
	}

	public void setDescricaoAtoAdministrativo(String descricaoAtoAdministrativo){

		this.descricaoAtoAdministrativo = descricaoAtoAdministrativo;
	}

	public String getDataVigenciaFormatada(){

		return Util.formatarData(this.dataVigencia);
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroConsumoTarifaVigencia filtro = new FiltroConsumoTarifaVigencia();

		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaVigencia.CONSUMO_TARIFA);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaVigencia.CALCULO_TIPO);

		filtro.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaVigencia.ID, this.getId()));
		return filtro;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"consumoTarifa.descricao", "dataVigenciaFormatada"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Tarifa", "Data de Vigencia"};
		return labels;
	}

	public String getDescricaoAtoAdministrativoFormatado(){

		String retorno = Util.completaString("", 30);

		if(this.descricaoAtoAdministrativo != null){

			retorno = Util.completaString(descricaoAtoAdministrativo, 30);
		}

		return retorno;
	}

}
