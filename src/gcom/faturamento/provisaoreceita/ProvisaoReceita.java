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

package gcom.faturamento.provisaoreceita;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProvisaoReceita
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer anoMesReferenciaProvisao;

	private Integer idGerenciaRegional;

	private Integer idLocalidade;

	private Integer idImovel;

	private Date dataLeituraMesProvisao;

	private Short numeroDiasConsumoMes;

	private Integer consumoMedio;

	private Short numeroDiasFaturar;

	private BigDecimal consumoDia;

	private Integer consumoFaturar;

	private Integer quantidadeEconomias;

	private BigDecimal valorConsumoMinimoDia;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorFaturar;

	private Date ultimaAlteracao;

	private Short idCategoria;

	/**
	 * ProvisaoReceita
	 * <p>
	 * Esse m�todo Constroi uma inst�ncia de ProvisaoReceita.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 15/03/2013
	 */
	public ProvisaoReceita() {

		super();
	}

	public ProvisaoReceita(Integer anoMesReferenciaProvisao, Integer idGerenciaRegional, Integer idLocalidade, Integer idImovel,
							Date dataLeituraMesProvisao, Short numeroDiasConsumoMes, Integer consumoMedio, Short numeroDiasFaturar,
							BigDecimal consumoDia, Integer consumoFaturar, Integer quantidadeEconomias, BigDecimal valorConsumoMinimoDia,
							BigDecimal valorAgua, BigDecimal valorEsgoto, BigDecimal valorFaturar, Date ultimaAlteracao, Short idCategoria) {

		super();
		this.anoMesReferenciaProvisao = anoMesReferenciaProvisao;
		this.idGerenciaRegional = idGerenciaRegional;
		this.idLocalidade = idLocalidade;
		this.idImovel = idImovel;
		this.dataLeituraMesProvisao = dataLeituraMesProvisao;
		this.numeroDiasConsumoMes = numeroDiasConsumoMes;
		this.consumoMedio = consumoMedio;
		this.numeroDiasFaturar = numeroDiasFaturar;
		this.consumoDia = consumoDia;
		this.consumoFaturar = consumoFaturar;
		this.quantidadeEconomias = quantidadeEconomias;
		this.valorConsumoMinimoDia = valorConsumoMinimoDia;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.valorFaturar = valorFaturar;
		this.ultimaAlteracao = ultimaAlteracao;
		this.idCategoria = idCategoria;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getAnoMesReferenciaProvisao(){

		return anoMesReferenciaProvisao;
	}

	public void setAnoMesReferenciaProvisao(Integer anoMesReferenciaProvisao){

		this.anoMesReferenciaProvisao = anoMesReferenciaProvisao;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Date getDataLeituraMesProvisao(){

		return dataLeituraMesProvisao;
	}

	public void setDataLeituraMesProvisao(Date dataLeituraMesProvisao){

		this.dataLeituraMesProvisao = dataLeituraMesProvisao;
	}

	public Short getNumeroDiasConsumoMes(){

		return numeroDiasConsumoMes;
	}

	public void setNumeroDiasConsumoMes(Short numeroDiasConsumoMes){

		this.numeroDiasConsumoMes = numeroDiasConsumoMes;
	}

	public Integer getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(Integer consumoMedio){

		this.consumoMedio = consumoMedio;
	}

	public Short getNumeroDiasFaturar(){

		return numeroDiasFaturar;
	}

	public void setNumeroDiasFaturar(Short numeroDiasFaturar){

		this.numeroDiasFaturar = numeroDiasFaturar;
	}

	public BigDecimal getConsumoDia(){

		return consumoDia;
	}

	public void setConsumoDia(BigDecimal consumoDia){

		this.consumoDia = consumoDia;
	}

	public Integer getConsumoFaturar(){

		return consumoFaturar;
	}

	public void setConsumoFaturar(Integer consumoFaturar){

		this.consumoFaturar = consumoFaturar;
	}

	public Integer getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(Integer quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	public BigDecimal getValorConsumoMinimoDia(){

		return valorConsumoMinimoDia;
	}

	public void setValorConsumoMinimoDia(BigDecimal valorConsumoMinimoDia){

		this.valorConsumoMinimoDia = valorConsumoMinimoDia;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getValorFaturar(){

		return valorFaturar;
	}

	public void setValorFaturar(BigDecimal valorFaturar){

		this.valorFaturar = valorFaturar;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(Short idCategoria){

		this.idCategoria = idCategoria;
	}

}
