/*
] * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.relatorio.financeiro;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioPosicaoContasAReceberContabilBean
				implements RelatorioBean {

	private String idGerencia;

	private String descricaoGerencia;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String imprimirDebitosACobrar;

	private JRBeanCollectionDataSource beansDebitosACobrar;

	private ArrayList arrayDebitosACobrarDetailBean;

	private JRBeanCollectionDataSource beansTotal;

	private ArrayList arrayTotalDetailBean;

	private BigDecimal valorContasAVencer;

	private BigDecimal valorContasAtrasoAte30Dias;

	private BigDecimal valorContasAtraso30A60Dias;

	private BigDecimal valorContasAtraso60A90Dias;

	private BigDecimal valorContasAtrasoMaisDe90Dias;

	private BigDecimal valorContasTotal;

	private String tipoLancamento;

	private String imprimirCabecalhoGerencia;

	private String imprimirCabecalhoLocalidade;

	private String idCategoria;

	private String descricaoCategoria;

	private Integer quantidadeContasAVencer;

	private Integer quantidadeContasAtrasoAte30Dias;

	private Integer quantidadeContasAtraso30A60Dias;

	private Integer quantidadeContasAtraso60A90Dias;

	private Integer quantidadeContasAtrasoMaisDe90Dias;

	private Integer quantidadeContasTotal;

	private String imprimirCabecalhoEstado;

	private String imprimirRodapeEstado;

	private String imprimirRodapeGerencia;

	private String imprimirRodapeLocalidade;

	public String getIdGerencia(){

		return idGerencia;
	}

	public void setIdGerencia(String idGerencia){

		this.idGerencia = idGerencia;
	}

	public String getDescricaoGerencia(){

		return descricaoGerencia;
	}

	public void setDescricaoGerencia(String descricaoGerencia){

		this.descricaoGerencia = descricaoGerencia;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public JRBeanCollectionDataSource getBeansDebitosACobrar(){

		return beansDebitosACobrar;
	}

	public void setBeansDebitosACobrar(JRBeanCollectionDataSource beansDebitosACobrar){

		this.beansDebitosACobrar = beansDebitosACobrar;
	}

	public ArrayList getArrayDebitosACobrarDetailBean(){

		return arrayDebitosACobrarDetailBean;
	}

	public void setArrayDebitosACobrarDetailBean(ArrayList arrayDebitosACobrarDetailBean){

		this.arrayDebitosACobrarDetailBean = arrayDebitosACobrarDetailBean;
	}

	public void setarBeansTotalizadores(Collection colecaoDetail){

		this.arrayDebitosACobrarDetailBean = new ArrayList();
		this.arrayDebitosACobrarDetailBean.addAll(colecaoDetail);
		this.beansDebitosACobrar = new JRBeanCollectionDataSource(this.arrayDebitosACobrarDetailBean);
	}

	public String getImprimirDebitosACobrar(){

		return imprimirDebitosACobrar;
	}

	public void setImprimirDebitosACobrar(String imprimirDebitosACobrar){

		this.imprimirDebitosACobrar = imprimirDebitosACobrar;
	}

	public String getTipoLancamento(){

		return tipoLancamento;
	}

	public void setTipoLancamento(String tipoLancamento){

		this.tipoLancamento = tipoLancamento;
	}

	public String getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(String idCategoria){

		this.idCategoria = idCategoria;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public BigDecimal getValorContasAVencer(){

		return valorContasAVencer;
	}

	public void setValorContasAVencer(BigDecimal valorContasAVencer){

		this.valorContasAVencer = valorContasAVencer;
	}

	public BigDecimal getValorContasAtrasoAte30Dias(){

		return valorContasAtrasoAte30Dias;
	}

	public void setValorContasAtrasoAte30Dias(BigDecimal valorContasAtrasoAte30Dias){

		this.valorContasAtrasoAte30Dias = valorContasAtrasoAte30Dias;
	}

	public BigDecimal getValorContasAtraso30A60Dias(){

		return valorContasAtraso30A60Dias;
	}

	public void setValorContasAtraso30A60Dias(BigDecimal valorContasAtraso30A60Dias){

		this.valorContasAtraso30A60Dias = valorContasAtraso30A60Dias;
	}

	public BigDecimal getValorContasAtraso60A90Dias(){

		return valorContasAtraso60A90Dias;
	}

	public void setValorContasAtraso60A90Dias(BigDecimal valorContasAtraso60A90Dias){

		this.valorContasAtraso60A90Dias = valorContasAtraso60A90Dias;
	}

	public BigDecimal getValorContasAtrasoMaisDe90Dias(){

		return valorContasAtrasoMaisDe90Dias;
	}

	public void setValorContasAtrasoMaisDe90Dias(BigDecimal valorContasAtrasoMaisDe90Dias){

		this.valorContasAtrasoMaisDe90Dias = valorContasAtrasoMaisDe90Dias;
	}

	public BigDecimal getValorContasTotal(){

		return valorContasTotal;
	}

	public void setValorContasTotal(BigDecimal valorContasTotal){

		this.valorContasTotal = valorContasTotal;
	}

	public Integer getQuantidadeContasAVencer(){

		return quantidadeContasAVencer;
	}

	public void setQuantidadeContasAVencer(Integer quantidadeContasAVencer){

		this.quantidadeContasAVencer = quantidadeContasAVencer;
	}

	public Integer getQuantidadeContasAtrasoAte30Dias(){

		return quantidadeContasAtrasoAte30Dias;
	}

	public void setQuantidadeContasAtrasoAte30Dias(Integer quantidadeContasAtrasoAte30Dias){

		this.quantidadeContasAtrasoAte30Dias = quantidadeContasAtrasoAte30Dias;
	}

	public Integer getQuantidadeContasAtraso30A60Dias(){

		return quantidadeContasAtraso30A60Dias;
	}

	public void setQuantidadeContasAtraso30A60Dias(Integer quantidadeContasAtraso30A60Dias){

		this.quantidadeContasAtraso30A60Dias = quantidadeContasAtraso30A60Dias;
	}

	public Integer getQuantidadeContasAtraso60A90Dias(){

		return quantidadeContasAtraso60A90Dias;
	}

	public void setQuantidadeContasAtraso60A90Dias(Integer quantidadeContasAtraso60A90Dias){

		this.quantidadeContasAtraso60A90Dias = quantidadeContasAtraso60A90Dias;
	}

	public Integer getQuantidadeContasAtrasoMaisDe90Dias(){

		return quantidadeContasAtrasoMaisDe90Dias;
	}

	public void setQuantidadeContasAtrasoMaisDe90Dias(Integer quantidadeContasAtrasoMaisDe90Dias){

		this.quantidadeContasAtrasoMaisDe90Dias = quantidadeContasAtrasoMaisDe90Dias;
	}

	public Integer getQuantidadeContasTotal(){

		return quantidadeContasTotal;
	}

	public void setQuantidadeContasTotal(Integer quantidadeContasTotal){

		this.quantidadeContasTotal = quantidadeContasTotal;
	}

	public String getImprimirRodapeEstado(){

		return imprimirRodapeEstado;
	}

	public void setImprimirRodapeEstado(String imprimirRodapeEstado){

		this.imprimirRodapeEstado = imprimirRodapeEstado;
	}

	public String getImprimirRodapeGerencia(){

		return imprimirRodapeGerencia;
	}

	public void setImprimirRodapeGerencia(String imprimirRodapeGerencia){

		this.imprimirRodapeGerencia = imprimirRodapeGerencia;
	}

	public String getImprimirCabecalhoGerencia(){

		return imprimirCabecalhoGerencia;
	}

	public void setImprimirCabecalhoGerencia(String imprimirCabecalhoGerencia){

		this.imprimirCabecalhoGerencia = imprimirCabecalhoGerencia;
	}

	public String getImprimirCabecalhoLocalidade(){

		return imprimirCabecalhoLocalidade;
	}

	public void setImprimirCabecalhoLocalidade(String imprimirCabecalhoLocalidade){

		this.imprimirCabecalhoLocalidade = imprimirCabecalhoLocalidade;
	}

	public String getImprimirCabecalhoEstado(){

		return imprimirCabecalhoEstado;
	}

	public void setImprimirCabecalhoEstado(String imprimirCabecalhoEstado){

		this.imprimirCabecalhoEstado = imprimirCabecalhoEstado;
	}

	public String getImprimirRodapeLocalidade(){

		return imprimirRodapeLocalidade;
	}

	public void setImprimirRodapeLocalidade(String imprimirRodapeLocalidade){

		this.imprimirRodapeLocalidade = imprimirRodapeLocalidade;
	}

	public JRBeanCollectionDataSource getBeansTotal(){

		return beansTotal;
	}

	public void setBeansTotal(JRBeanCollectionDataSource beansTotal){

		this.beansTotal = beansTotal;
	}

	public ArrayList getArrayTotalDetailBean(){

		return arrayTotalDetailBean;
	}

	public void setArrayTotalDetailBean(ArrayList arrayTotalDetailBean){

		this.arrayTotalDetailBean = arrayTotalDetailBean;
	}

	public void setarBeansTotal(Collection colecaoDetail){

		this.arrayTotalDetailBean = new ArrayList();
		this.arrayTotalDetailBean.addAll(colecaoDetail);
		this.beansTotal = new JRBeanCollectionDataSource(this.arrayTotalDetailBean);
	}

}
