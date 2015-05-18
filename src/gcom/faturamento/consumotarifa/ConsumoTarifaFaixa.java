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

package gcom.faturamento.consumotarifa;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()
public class ConsumoTarifaFaixa
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_INSERIR_CONSUMO_TARIFA = 367;

	public static final int ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA = 382;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private Integer numeroConsumoFaixaInicio;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private Integer numeroConsumoFaixaIFim;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private BigDecimal valorConsumoTarifa;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private BigDecimal valorUsoEsgotoTarifa;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_INSERIR_CONSUMO_TARIFA, ATRIBUTOS_ATUALIZAR_CONSUMO_TARIFA})
	private ConsumoTarifaCategoria consumoTarifaCategoria;

	/** full constructor */
	public ConsumoTarifaFaixa(Integer numeroConsumoFaixaInicio, Integer numeroConsumoFaixaIFim, BigDecimal valorConsumoTarifa,
								Date ultimaAlteracao, gcom.faturamento.consumotarifa.ConsumoTarifaCategoria consumoTarifaCategoria) {

		this.numeroConsumoFaixaInicio = numeroConsumoFaixaInicio;
		this.numeroConsumoFaixaIFim = numeroConsumoFaixaIFim;
		this.valorConsumoTarifa = valorConsumoTarifa;
		this.ultimaAlteracao = ultimaAlteracao;
		this.consumoTarifaCategoria = consumoTarifaCategoria;
	}

	/** default constructor */
	public ConsumoTarifaFaixa() {

	}

	/** minimal constructor */
	public ConsumoTarifaFaixa(gcom.faturamento.consumotarifa.ConsumoTarifaCategoria consumoTarifaCategoria) {

		this.consumoTarifaCategoria = consumoTarifaCategoria;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getNumeroConsumoFaixaInicio(){

		return this.numeroConsumoFaixaInicio;
	}

	public void setNumeroConsumoFaixaInicio(Integer numeroConsumoFaixaInicio){

		this.numeroConsumoFaixaInicio = numeroConsumoFaixaInicio;
	}

	public Integer getNumeroConsumoFaixaIFim(){

		return this.numeroConsumoFaixaIFim;
	}

	public void setNumeroConsumoFaixaIFim(Integer numeroConsumoFaixaIFim){

		this.numeroConsumoFaixaIFim = numeroConsumoFaixaIFim;
	}

	public BigDecimal getValorConsumoTarifa(){

		return this.valorConsumoTarifa;
	}

	public void setValorConsumoTarifa(BigDecimal valorConsumoTarifa){

		this.valorConsumoTarifa = valorConsumoTarifa;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.faturamento.consumotarifa.ConsumoTarifaCategoria getConsumoTarifaCategoria(){

		return this.consumoTarifaCategoria;
	}

	public void setConsumoTarifaCategoria(gcom.faturamento.consumotarifa.ConsumoTarifaCategoria consumoTarifaCategoria){

		this.consumoTarifaCategoria = consumoTarifaCategoria;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public BigDecimal getValorUsoEsgotoTarifa(){

		return valorUsoEsgotoTarifa;
	}

	public void setValorUsoEsgotoTarifa(BigDecimal valorUsoEsgotoTarifa){

		this.valorUsoEsgotoTarifa = valorUsoEsgotoTarifa;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroConsumoTarifaFaixa filtro = new FiltroConsumoTarifaFaixa();

		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaFaixa.CONSUMO_TARIFA_CATEGORIA);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaFaixa.CONSUMO_TARIFA_CATEGORIA_CONSUMO_TARIFA_VIGENCIA);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaFaixa.CONSUMO_TARIFA_CATEGORIA_CONSUMO_TARIFA_VIGENCIA_CONSUMO_TARIFA);

		filtro.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaFaixa.ID, this.getId()));
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

		String[] atributos = {"consumoTarifaCategoria.consumoTarifaVigencia.consumoTarifa.descricao", "consumoTarifaCategoria.categoria.id", "consumoTarifaCategoria.consumoTarifaVigencia.dataVigenciaFormatada", "numeroConsumoFaixaInicio", "numeroConsumoFaixaFim"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Tarifa", "Categoria", "Data de Vigencia", "Faixa Inicial", "Faixa Final"};
		return labels;
	}
}
