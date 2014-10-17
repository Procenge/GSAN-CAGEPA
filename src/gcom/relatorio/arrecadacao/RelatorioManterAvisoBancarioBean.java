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

package gcom.relatorio.arrecadacao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Bean do [UC0227] Gerar Relação de Débitos
 * 
 * @author Rafael Santos
 * @date 25/05/2006
 */
public class RelatorioManterAvisoBancarioBean
				implements RelatorioBean {

	private String idAvisoBancario;

	private String arrecadador;

	private String dataLancamento;

	private String sequencial;

	private String tipo;

	private String numeroDocumento;

	private String banco;

	private String agencia;

	private String numeroConta;

	private String dataRealizacao;

	private BigDecimal totalArrecadacao;

	private BigDecimal totalDeducao;

	private BigDecimal totalDevolucoes;

	private BigDecimal valorAviso;

	private JRBeanCollectionDataSource arrayJrResumo;

	private ArrayList arrayRelatorioResumoBean;

	private String dataPagamentoAvisoBancario;

	private BigDecimal valorArrecadacaoCalculado;

	private BigDecimal valorDevolucaoCalculado;

	private BigDecimal valorLiquido;

	private BigDecimal valorAcerto;

	private BigDecimal valorDiferenca;

	private BigDecimal valorDiferencaArrecadacao;

	private BigDecimal valorDiferencaDevolucao;

	private String situacao;

	/**
	 * Construtor de RelatorioGerarRelacaoDebitosBean
	 */
	public RelatorioManterAvisoBancarioBean(String idAvisoBancario, String arrecadador, String dataLancamento, String sequencial,
											String tipo, String numeroDocumento, String banco, String agencia, String numeroConta,
											String dataRealizacao, BigDecimal totalArrecadacao, BigDecimal totalDeducao,
											BigDecimal totalDevolucoes, BigDecimal valorAviso) {

		this.idAvisoBancario = idAvisoBancario;
		this.arrecadador = arrecadador;
		this.dataLancamento = dataLancamento;
		this.sequencial = sequencial;
		this.tipo = tipo;
		this.numeroDocumento = numeroDocumento;
		this.banco = banco;
		this.agencia = agencia;
		this.numeroConta = numeroConta;
		this.dataRealizacao = dataRealizacao;
		this.totalArrecadacao = totalArrecadacao;
		this.totalDeducao = totalDeducao;
		this.totalDevolucoes = totalDevolucoes;
		this.valorAviso = valorAviso;

	}

	public String getAgencia(){

		return agencia;
	}

	public void setAgencia(String agencia){

		this.agencia = agencia;
	}

	public JRBeanCollectionDataSource getArrayJrResumo(){

		return arrayJrResumo;
	}

	public void setArrayJrResumo(JRBeanCollectionDataSource arrayJrResumo){

		this.arrayJrResumo = arrayJrResumo;
	}

	public ArrayList getArrayRelatorioManterAvisoBancarioResumoBean(){

		return arrayRelatorioResumoBean;
	}

	public void setArrayRelatorioManterAvisoBancarioResumoBean(ArrayList arrayRelatorioManterAvisoBancarioResumoBean){

		this.arrayRelatorioResumoBean = arrayRelatorioManterAvisoBancarioResumoBean;
		this.arrayJrResumo = new JRBeanCollectionDataSource(this.arrayRelatorioResumoBean);
	}

	public String getArrecadador(){

		return arrecadador;
	}

	public void setArrecadador(String arrecadador){

		this.arrecadador = arrecadador;
	}

	public String getBanco(){

		return banco;
	}

	public void setBanco(String banco){

		this.banco = banco;
	}

	public String getDataLancamento(){

		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento){

		this.dataLancamento = dataLancamento;
	}

	public String getDataRealizacao(){

		return dataRealizacao;
	}

	public void setDataRealizacao(String dataRealizacao){

		this.dataRealizacao = dataRealizacao;
	}

	public String getNumeroConta(){

		return numeroConta;
	}

	public void setNumeroConta(String numeroConta){

		this.numeroConta = numeroConta;
	}

	public String getNumeroDocumento(){

		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento){

		this.numeroDocumento = numeroDocumento;
	}

	public String getSequencial(){

		return sequencial;
	}

	public void setSequencial(String sequencial){

		this.sequencial = sequencial;
	}

	public String getTipo(){

		return tipo;
	}

	public void setTipo(String tipo){

		this.tipo = tipo;
	}

	public BigDecimal getTotalArrecadacao(){

		return totalArrecadacao;
	}

	public void setTotalArrecadacao(BigDecimal totalArrecadacao){

		this.totalArrecadacao = totalArrecadacao;
	}

	public BigDecimal getTotalDeducao(){

		return totalDeducao;
	}

	public void setTotalDeducao(BigDecimal totalDeducao){

		this.totalDeducao = totalDeducao;
	}

	public BigDecimal getTotalDevolucoes(){

		return totalDevolucoes;
	}

	public void setTotalDevolucoes(BigDecimal totalDevolucoes){

		this.totalDevolucoes = totalDevolucoes;
	}

	public BigDecimal getValorAviso(){

		return valorAviso;
	}

	public void setValorAviso(BigDecimal valorAviso){

		this.valorAviso = valorAviso;
	}

	public String getIdAvisoBancario(){

		return idAvisoBancario;
	}

	public void setIdAvisoBancario(String idAvisoBancario){

		this.idAvisoBancario = idAvisoBancario;
	}

	public String getDataPagamentoAvisoBancario(){

		return dataPagamentoAvisoBancario;
	}

	public void setDataPagamentoAvisoBancario(String dataPagamentoAvisoBancario){

		this.dataPagamentoAvisoBancario = dataPagamentoAvisoBancario;
	}

	public BigDecimal getValorArrecadacaoCalculado(){

		return valorArrecadacaoCalculado;
	}

	public void setValorArrecadacaoCalculado(BigDecimal valorArrecadacaoCalculado){

		this.valorArrecadacaoCalculado = valorArrecadacaoCalculado;
	}

	public BigDecimal getValorDevolucaoCalculado(){

		return valorDevolucaoCalculado;
	}

	public void setValorDevolucaoCalculado(BigDecimal valorDevolucaoCalculado){

		this.valorDevolucaoCalculado = valorDevolucaoCalculado;
	}

	public BigDecimal getValorLiquido(){

		return valorLiquido;
	}

	public void setValorLiquido(BigDecimal valorLiquido){

		this.valorLiquido = valorLiquido;
	}

	public BigDecimal getValorAcerto(){

		return valorAcerto;
	}

	public void setValorAcerto(BigDecimal valorAcerto){

		this.valorAcerto = valorAcerto;
	}

	public BigDecimal getValorDiferenca(){

		return valorDiferenca;
	}

	public void setValorDiferenca(BigDecimal valorDiferenca){

		this.valorDiferenca = valorDiferenca;
	}

	public BigDecimal getValorDiferencaArrecadacao(){

		return valorDiferencaArrecadacao;
	}

	public void setValorDiferencaArrecadacao(BigDecimal valorDiferencaArrecadacao){

		this.valorDiferencaArrecadacao = valorDiferencaArrecadacao;
	}

	public BigDecimal getValorDiferencaDevolucao(){

		return valorDiferencaDevolucao;
	}

	public void setValorDiferencaDevolucao(BigDecimal valorDiferencaDevolucao){

		this.valorDiferencaDevolucao = valorDiferencaDevolucao;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

}