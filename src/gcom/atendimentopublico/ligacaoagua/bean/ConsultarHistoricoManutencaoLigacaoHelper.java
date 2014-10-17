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

package gcom.atendimentopublico.ligacaoagua.bean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Helper com as informações que são exibidas na lista de Histórico de Manutenção de Ligação de Água
 * 
 * @author Luciano Galvão
 * @created 18/09/2012
 */
public class ConsultarHistoricoManutencaoLigacaoHelper {
	
	private Integer imovelId;
	
	private Integer localidadeIdInicial;

	private Integer localidadeIdFinal;

	private Integer setorComercialIdInicial;

	private Integer setorComercialIdFinal;

	private Collection<Integer> perfisImovel;

	private Collection<Integer> formasEmissao;

	private Collection<Integer> tiposDocumento;

	private Collection<Integer> tiposServico;

	private BigDecimal valorDebitoInicial;

	private BigDecimal valorDebitoFinal;

	private Date periodoEmissaoInicial;

	private Date periodoEmissaoFinal;

	public Integer getImovelId(){

		return imovelId;
	}

	public void setImovelId(Integer imovelId){

		this.imovelId = imovelId;
	}

	public Integer getLocalidadeIdInicial(){

		return localidadeIdInicial;
	}

	public void setLocalidadeIdInicial(Integer localidadeIdInicial){

		this.localidadeIdInicial = localidadeIdInicial;
	}

	public Integer getLocalidadeIdFinal(){

		return localidadeIdFinal;
	}

	public void setLocalidadeIdFinal(Integer localidadeIdFinal){

		this.localidadeIdFinal = localidadeIdFinal;
	}

	public Integer getSetorComercialIdInicial(){

		return setorComercialIdInicial;
	}

	public void setSetorComercialIdInicial(Integer setorComercialIdInicial){

		this.setorComercialIdInicial = setorComercialIdInicial;
	}

	public Integer getSetorComercialIdFinal(){

		return setorComercialIdFinal;
	}

	public void setSetorComercialIdFinal(Integer setorComercialIdFinal){

		this.setorComercialIdFinal = setorComercialIdFinal;
	}

	public Collection<Integer> getPerfisImovel(){

		return perfisImovel;
	}

	public void setPerfisImovel(Collection<Integer> perfisImovel){

		this.perfisImovel = perfisImovel;
	}

	public Collection<Integer> getFormasEmissao(){

		return formasEmissao;
	}

	public void setFormasEmissao(Collection<Integer> formasEmissao){

		this.formasEmissao = formasEmissao;
	}

	public Collection<Integer> getTiposDocumento(){

		return tiposDocumento;
	}

	public void setTiposDocumento(Collection<Integer> tiposDocumento){

		this.tiposDocumento = tiposDocumento;
	}

	public Collection<Integer> getTiposServico(){

		return tiposServico;
	}

	public void setTiposServico(Collection<Integer> tiposServico){

		this.tiposServico = tiposServico;
	}

	public BigDecimal getValorDebitoInicial(){

		return valorDebitoInicial;
	}

	public void setValorDebitoInicial(BigDecimal valorDebitoInicial){

		this.valorDebitoInicial = valorDebitoInicial;
	}

	public BigDecimal getValorDebitoFinal(){

		return valorDebitoFinal;
	}

	public void setValorDebitoFinal(BigDecimal valorDebitoFinal){

		this.valorDebitoFinal = valorDebitoFinal;
	}

	public Date getPeriodoEmissaoInicial(){

		return periodoEmissaoInicial;
	}

	public void setPeriodoEmissaoInicial(Date periodoEmissaoInicial){

		this.periodoEmissaoInicial = periodoEmissaoInicial;
	}

	public Date getPeriodoEmissaoFinal(){

		return periodoEmissaoFinal;
	}

	public void setPeriodoEmissaoFinal(Date periodoEmissaoFinal){

		this.periodoEmissaoFinal = periodoEmissaoFinal;
	}
	
}
