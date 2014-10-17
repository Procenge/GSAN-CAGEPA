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

package gcom.gui.atendimentopublico.ligacaoagua;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar Registro Atendimento
 * 
 * @author Luciano Galvao
 * @date 30/08/2012
 */
public class FiltrarHistoricoManutencaoLigacaoAguaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String localidadeInicial;

	private String localidadeFinal;

	private String nomeLocalidadeInicial;

	private String nomeLocalidadeFinal;

	private String setorComercialInicial;

	private String setorComercialFinal;

	private String nomeSetorComercialInicial;

	private String nomeSetorComercialFinal;

	private String imovel;

	private String inscricaoImovel;

	private String[] perfisImovel;

	private String[] formasEmissao;

	private String[] tiposDocumento;

	private String[] tiposServico;

	private String valorDebitoInicial;

	private String valorDebitoFinal;

	private String periodoEmissaoInicial;

	private String periodoEmissaoFinal;

	public String getImovel(){

		return imovel;
	}

	public void setImovel(String imovel){

		this.imovel = imovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getNomeLocalidadeInicial(){

		return nomeLocalidadeInicial;
	}

	public void setNomeLocalidadeInicial(String nomeLocalidadeInicial){

		this.nomeLocalidadeInicial = nomeLocalidadeInicial;
	}

	public String getNomeLocalidadeFinal(){

		return nomeLocalidadeFinal;
	}

	public void setNomeLocalidadeFinal(String nomeLocalidadeFinal){

		this.nomeLocalidadeFinal = nomeLocalidadeFinal;
	}

	public String getNomeSetorComercialInicial(){

		return nomeSetorComercialInicial;
	}

	public void setNomeSetorComercialInicial(String nomeSetorComercialInicial){

		this.nomeSetorComercialInicial = nomeSetorComercialInicial;
	}

	public String getNomeSetorComercialFinal(){

		return nomeSetorComercialFinal;
	}

	public void setNomeSetorComercialFinal(String nomeSetorComercialFinal){

		this.nomeSetorComercialFinal = nomeSetorComercialFinal;
	}


	public static long getSerialversionuid(){

		return serialVersionUID;
	}


	public String getLocalidadeInicial(){

		return localidadeInicial;
	}

	
	public void setLocalidadeInicial(String localidadeInicial){

		this.localidadeInicial = localidadeInicial;
	}


	public String getLocalidadeFinal(){

		return localidadeFinal;
	}

	
	public void setLocalidadeFinal(String localidadeFinal){

		this.localidadeFinal = localidadeFinal;
	}


	public String getSetorComercialInicial(){

		return setorComercialInicial;
	}


	public void setSetorComercialInicial(String setorComercialInicial){

		this.setorComercialInicial = setorComercialInicial;
	}


	public String getSetorComercialFinal(){

		return setorComercialFinal;
	}


	public void setSetorComercialFinal(String setorComercialFinal){

		this.setorComercialFinal = setorComercialFinal;
	}


	public String getPeriodoEmissaoInicial(){

		return periodoEmissaoInicial;
	}


	public void setPeriodoEmissaoInicial(String periodoEmissaoInicial){

		this.periodoEmissaoInicial = periodoEmissaoInicial;
	}


	public String getPeriodoEmissaoFinal(){

		return periodoEmissaoFinal;
	}


	public void setPeriodoEmissaoFinal(String periodoEmissaoFinal){

		this.periodoEmissaoFinal = periodoEmissaoFinal;
	}

	public String[] getPerfisImovel(){

		return perfisImovel;
	}

	public void setPerfisImovel(String[] perfisImovel){

		this.perfisImovel = perfisImovel;
	}

	public String[] getFormasEmissao(){

		return formasEmissao;
	}

	public void setFormasEmissao(String[] formasEmissao){

		this.formasEmissao = formasEmissao;
	}

	public String[] getTiposDocumento(){

		return tiposDocumento;
	}

	public void setTiposDocumento(String[] tiposDocumento){

		this.tiposDocumento = tiposDocumento;
	}

	public String[] getTiposServico(){

		return tiposServico;
	}

	public void setTiposServico(String[] tiposServico){

		this.tiposServico = tiposServico;
	}

	public String getValorDebitoInicial(){

		return valorDebitoInicial;
	}

	public void setValorDebitoInicial(String valorDebitoInicial){

		this.valorDebitoInicial = valorDebitoInicial;
	}

	public String getValorDebitoFinal(){

		return valorDebitoFinal;
	}

	public void setValorDebitoFinal(String valorDebitoFinal){

		this.valorDebitoFinal = valorDebitoFinal;
	}

}