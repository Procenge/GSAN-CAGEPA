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