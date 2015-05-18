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

package gcom.atendimentopublico.ordemservico;

import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.cadastro.imovel.PavimentoCalcada;
import gcom.cadastro.imovel.PavimentoRua;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrdemServicoValaPavimento
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer numeroVala;

	private BigDecimal numeroComprimento;

	private BigDecimal numeroLargura;

	private BigDecimal numeroProfundidade;

	private LocalOcorrencia localOcorrencia;

	private PavimentoRua pavimentoRua;

	private PavimentoCalcada pavimentoCalcada;

	private OrdemServico ordemServico;

	private int indicadorAterro;

	private int indicadorEntulho;

	private Date ultimaAlteracao;

	private EntulhoMedida entulhoMedida;

	private BigDecimal numeroComprimentoTutulacaoAguaPluvial;

	private BigDecimal numeroDiametroTutulacaoAguaPluvial;

	private Integer quantidadeEntulho;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getNumeroVala(){

		return numeroVala;
	}

	public void setNumeroVala(Integer numeroVala){

		this.numeroVala = numeroVala;
	}

	public BigDecimal getNumeroComprimento(){

		return numeroComprimento;
	}

	public void setNumeroComprimento(BigDecimal numeroComprimento){

		this.numeroComprimento = numeroComprimento;
	}

	public BigDecimal getNumeroLargura(){

		return numeroLargura;
	}

	public void setNumeroLargura(BigDecimal numeroLargura){

		this.numeroLargura = numeroLargura;
	}

	public BigDecimal getNumeroProfundidade(){

		return numeroProfundidade;
	}

	public void setNumeroProfundidade(BigDecimal numeroProfundidade){

		this.numeroProfundidade = numeroProfundidade;
	}

	public LocalOcorrencia getLocalOcorrencia(){

		return localOcorrencia;
	}

	public void setLocalOcorrencia(LocalOcorrencia localOcorrencia){

		this.localOcorrencia = localOcorrencia;
	}

	public PavimentoRua getPavimentoRua(){

		return pavimentoRua;
	}

	public void setPavimentoRua(PavimentoRua pavimentoRua){

		this.pavimentoRua = pavimentoRua;
	}

	public PavimentoCalcada getPavimentoCalcada(){

		return pavimentoCalcada;
	}

	public void setPavimentoCalcada(PavimentoCalcada pavimentoCalcada){

		this.pavimentoCalcada = pavimentoCalcada;
	}

	public OrdemServico getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	public int getIndicadorAterro(){

		return indicadorAterro;
	}

	public void setIndicadorAterro(int indicadorAterro){

		this.indicadorAterro = indicadorAterro;
	}

	public int getIndicadorEntulho(){

		return indicadorEntulho;
	}

	public void setIndicadorEntulho(int indicadorEntulho){

		this.indicadorEntulho = indicadorEntulho;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public EntulhoMedida getEntulhoMedida(){

		return entulhoMedida;
	}

	public void setEntulhoMedida(EntulhoMedida entulhoMedida){

		this.entulhoMedida = entulhoMedida;
	}

	public BigDecimal getNumeroComprimentoTutulacaoAguaPluvial(){

		return numeroComprimentoTutulacaoAguaPluvial;
	}

	public void setNumeroComprimentoTutulacaoAguaPluvial(BigDecimal numeroComprimentoTutulacaoAguaPluvial){

		this.numeroComprimentoTutulacaoAguaPluvial = numeroComprimentoTutulacaoAguaPluvial;
	}

	public BigDecimal getNumeroDiametroTutulacaoAguaPluvial(){

		return numeroDiametroTutulacaoAguaPluvial;
	}

	public void setNumeroDiametroTutulacaoAguaPluvial(BigDecimal numeroDiametroTutulacaoAguaPluvial){

		this.numeroDiametroTutulacaoAguaPluvial = numeroDiametroTutulacaoAguaPluvial;
	}

	public Integer getQuantidadeEntulho(){

		return quantidadeEntulho;
	}

	public void setQuantidadeEntulho(Integer quantidadeEntulho){

		this.quantidadeEntulho = quantidadeEntulho;
	}

}
