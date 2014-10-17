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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.contabil;

import gcom.contabil.bean.LancamentoContabilSinteticoConsultaHelper;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Genival Barbosa
 * @date 07/07/2011
 * @author Genival Barbosa
 */
public class ConsultarLancamentoSinteticoActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String dtInicio;

	private String dtFim;

	private String idUnidadeAgrupamento;

	private String idModulo;

	private String idEventoComercial;

	private String idComplementoLancamentoItemContabil;

	private String idComplementoImposto;

	private String idCategoria;

	private String localidadeInicial;

	private String localidadeFinal;

	private String nomeLocalidadeInicial;

	private String nomeLocalidadeFinal;

	private String contaContabilInicial;

	private String contaContabilFinal;

	private String nomeContaContabilInicial;

	private String nomeContaContabilFinal;

	private String contaAuxiliarInicial;

	private String contaAuxiliarFinal;

	private String nomeContaAuxiliarInicial;

	private String nomeContaAuxiliarFinal;

	private String referenciaContabilInicial;

	private String referenciaContabilFinal;

	private String regionalInicial;

	private String regionalFinal;

	private String nomeRegionalInicial;

	private String nomeRegionalFinal;

	private String unidadeNegocioInicial;

	private String unidadeNegocioFinal;

	private String nomeUnidadeNegocioInicial;

	private String nomeUnidadeNegocioFinal;

	private LancamentoContabilSinteticoConsultaHelper lancamentoContabilSinteticoConsultaHelper;

	public void setDtInicio(String dtInicio){

		this.dtInicio = dtInicio;
	}

	public String getDtInicio(){

		return dtInicio;
	}

	public void setDtFim(String dtFim){

		this.dtFim = dtFim;
	}

	public String getDtFim(){

		return dtFim;
	}

	public void setIdModulo(String idModulo){

		this.idModulo = idModulo;
	}

	public String getIdModulo(){

		return idModulo;
	}

	public void setIdEventoComercial(String idEventoComercial){

		this.idEventoComercial = idEventoComercial;
	}

	public String getIdEventoComercial(){

		return idEventoComercial;
	}

	public void setIdCategoria(String idCategoria){

		this.idCategoria = idCategoria;
	}

	public String getIdCategoria(){

		return idCategoria;
	}

	public void setIdComplementoLancamentoItemContabil(String idComplementoLancamentoItemContabil){

		this.idComplementoLancamentoItemContabil = idComplementoLancamentoItemContabil;
	}

	public String getIdComplementoLancamentoItemContabil(){

		return idComplementoLancamentoItemContabil;
	}

	public void setIdComplementoImposto(String idComplementoImposto){

		this.idComplementoImposto = idComplementoImposto;
	}

	public String getIdComplementoImposto(){

		return idComplementoImposto;
	}

	public void setLancamentoContabilSinteticoConsultaHelper(
					LancamentoContabilSinteticoConsultaHelper lancamentoContabilSinteticoConsultaHelper){

		this.lancamentoContabilSinteticoConsultaHelper = lancamentoContabilSinteticoConsultaHelper;
	}

	public LancamentoContabilSinteticoConsultaHelper getLancamentoContabilSinteticoConsultaHelper(){

		return lancamentoContabilSinteticoConsultaHelper;
	}

	public void setIdUnidadeAgrupamento(String idUnidadeAgrupamento){

		this.idUnidadeAgrupamento = idUnidadeAgrupamento;
	}

	public String getIdUnidadeAgrupamento(){

		return idUnidadeAgrupamento;
	}


	/**
	 * @return the localidadeInicial
	 */
	public String getLocalidadeInicial(){

		return localidadeInicial;
	}


	/**
	 * @param localidadeInicial
	 *            the localidadeInicial to set
	 */
	public void setLocalidadeInicial(String localidadeInicial){

		this.localidadeInicial = localidadeInicial;
	}


	/**
	 * @return the localidadeFinal
	 */
	public String getLocalidadeFinal(){

		return localidadeFinal;
	}


	/**
	 * @param localidadeFinal
	 *            the localidadeFinal to set
	 */
	public void setLocalidadeFinal(String localidadeFinal){

		this.localidadeFinal = localidadeFinal;
	}

	/**
	 * @return the nomeLocalidadeInicial
	 */
	public String getNomeLocalidadeInicial(){

		return nomeLocalidadeInicial;
	}

	/**
	 * @param nomeLocalidadeInicial
	 *            the nomeLocalidadeInicial to set
	 */
	public void setNomeLocalidadeInicial(String nomeLocalidadeInicial){

		this.nomeLocalidadeInicial = nomeLocalidadeInicial;
	}

	/**
	 * @return the nomeLocalidadeFinal
	 */
	public String getNomeLocalidadeFinal(){

		return nomeLocalidadeFinal;
	}

	/**
	 * @param nomeLocalidadeFinal
	 *            the nomeLocalidadeFinal to set
	 */
	public void setNomeLocalidadeFinal(String nomeLocalidadeFinal){

		this.nomeLocalidadeFinal = nomeLocalidadeFinal;
	}


	/**
	 * @return the contaContabilInicial
	 */
	public String getContaContabilInicial(){

		return contaContabilInicial;
	}


	/**
	 * @param contaContabilInicial
	 *            the contaContabilInicial to set
	 */
	public void setContaContabilInicial(String contaContabilInicial){

		this.contaContabilInicial = contaContabilInicial;
	}


	/**
	 * @return the contaContabilFinal
	 */
	public String getContaContabilFinal(){

		return contaContabilFinal;
	}


	/**
	 * @param contaContabilFinal
	 *            the contaContabilFinal to set
	 */
	public void setContaContabilFinal(String contaContabilFinal){

		this.contaContabilFinal = contaContabilFinal;
	}

	/**
	 * @return the nomeContaContabilInicial
	 */
	public String getNomeContaContabilInicial(){

		return nomeContaContabilInicial;
	}

	/**
	 * @param nomeContaContabilInicial
	 *            the nomeContaContabilInicial to set
	 */
	public void setNomeContaContabilInicial(String nomeContaContabilInicial){

		this.nomeContaContabilInicial = nomeContaContabilInicial;
	}

	/**
	 * @return the nomeContaContabilFinal
	 */
	public String getNomeContaContabilFinal(){

		return nomeContaContabilFinal;
	}

	/**
	 * @param nomeContaContabilFinal
	 *            the nomeContaContabilFinal to set
	 */
	public void setNomeContaContabilFinal(String nomeContaContabilFinal){

		this.nomeContaContabilFinal = nomeContaContabilFinal;
	}


	/**
	 * @return the contaAuxiliarInicial
	 */
	public String getContaAuxiliarInicial(){

		return contaAuxiliarInicial;
	}


	/**
	 * @param contaAuxiliarInicial
	 *            the contaAuxiliarInicial to set
	 */
	public void setContaAuxiliarInicial(String contaAuxiliarInicial){

		this.contaAuxiliarInicial = contaAuxiliarInicial;
	}


	/**
	 * @return the contaAuxiliarFinal
	 */
	public String getContaAuxiliarFinal(){

		return contaAuxiliarFinal;
	}


	/**
	 * @param contaAuxiliarFinal
	 *            the contaAuxiliarFinal to set
	 */
	public void setContaAuxiliarFinal(String contaAuxiliarFinal){

		this.contaAuxiliarFinal = contaAuxiliarFinal;
	}

	/**
	 * @return the nemoContaAuxiliarInicial
	 */
	public String getNomeContaAuxiliarInicial(){

		return nomeContaAuxiliarInicial;
	}

	/**
	 * @param nemoContaAuxiliarInicial
	 *            the nemoContaAuxiliarInicial to set
	 */
	public void setNomeContaAuxiliarInicial(String nomeContaAuxiliarInicial){

		this.nomeContaAuxiliarInicial = nomeContaAuxiliarInicial;
	}

	/**
	 * @return the nomeContaAuxiliarFinal
	 */
	public String getNomeContaAuxiliarFinal(){

		return nomeContaAuxiliarFinal;
	}

	/**
	 * @param nomeContaAuxiliarFinal
	 *            the nomeContaAuxiliarFinal to set
	 */
	public void setNomeContaAuxiliarFinal(String nomeContaAuxiliarFinal){

		this.nomeContaAuxiliarFinal = nomeContaAuxiliarFinal;
	}


	/**
	 * @return the referenciaContabilInicial
	 */
	public String getReferenciaContabilInicial(){

		return referenciaContabilInicial;
	}


	/**
	 * @param referenciaContabilInicial
	 *            the referenciaContabilInicial to set
	 */
	public void setReferenciaContabilInicial(String referenciaContabilInicial){

		this.referenciaContabilInicial = referenciaContabilInicial;
	}


	/**
	 * @return the referenciaContabilFinal
	 */
	public String getReferenciaContabilFinal(){

		return referenciaContabilFinal;
	}


	/**
	 * @param referenciaContabilFinal
	 *            the referenciaContabilFinal to set
	 */
	public void setReferenciaContabilFinal(String referenciaContabilFinal){

		this.referenciaContabilFinal = referenciaContabilFinal;
	}


	/**
	 * @return the regionalInicial
	 */
	public String getRegionalInicial(){

		return regionalInicial;
	}


	/**
	 * @param regionalInicial
	 *            the regionalInicial to set
	 */
	public void setRegionalInicial(String regionalInicial){

		this.regionalInicial = regionalInicial;
	}


	/**
	 * @return the regionalFinal
	 */
	public String getRegionalFinal(){

		return regionalFinal;
	}


	/**
	 * @param regionalFinal
	 *            the regionalFinal to set
	 */
	public void setRegionalFinal(String regionalFinal){

		this.regionalFinal = regionalFinal;
	}

	/**
	 * @return the nomeRegionalInicial
	 */
	public String getNomeRegionalInicial(){

		return nomeRegionalInicial;
	}

	/**
	 * @param nomeRegionalInicial
	 *            the nomeRegionalInicial to set
	 */
	public void setNomeRegionalInicial(String nomeRegionalInicial){

		this.nomeRegionalInicial = nomeRegionalInicial;
	}

	/**
	 * @return the nomeRegionalFinal
	 */
	public String getNomeRegionalFinal(){

		return nomeRegionalFinal;
	}

	/**
	 * @param nomeRegionalFinal
	 *            the nomeRegionalFinal to set
	 */
	public void setNomeRegionalFinal(String nomeRegionalFinal){

		this.nomeRegionalFinal = nomeRegionalFinal;
	}


	/**
	 * @return the unidadeNegocioInicial
	 */
	public String getUnidadeNegocioInicial(){

		return unidadeNegocioInicial;
	}


	/**
	 * @param unidadeNegocioInicial
	 *            the unidadeNegocioInicial to set
	 */
	public void setUnidadeNegocioInicial(String unidadeNegocioInicial){

		this.unidadeNegocioInicial = unidadeNegocioInicial;
	}


	/**
	 * @return the unidadeNegocioFinal
	 */
	public String getUnidadeNegocioFinal(){

		return unidadeNegocioFinal;
	}


	/**
	 * @param unidadeNegocioFinal
	 *            the unidadeNegocioFinal to set
	 */
	public void setUnidadeNegocioFinal(String unidadeNegocioFinal){

		this.unidadeNegocioFinal = unidadeNegocioFinal;
	}

	/**
	 * @return the nomeUnidadeNegocioInicial
	 */
	public String getNomeUnidadeNegocioInicial(){

		return nomeUnidadeNegocioInicial;
	}

	/**
	 * @param nomeUnidadeNegocioInicial
	 *            the nomeUnidadeNegocioInicial to set
	 */
	public void setNomeUnidadeNegocioInicial(String nomeUnidadeNegocioInicial){

		this.nomeUnidadeNegocioInicial = nomeUnidadeNegocioInicial;
	}

	/**
	 * @return the nomeUnidadeNegocioFinal
	 */
	public String getNomeUnidadeNegocioFinal(){

		return nomeUnidadeNegocioFinal;
	}

	/**
	 * @param nomeUnidadeNegocioFinal
	 *            the nomeUnidadeNegocioFinal to set
	 */
	public void setNomeUnidadeNegocioFinal(String nomeUnidadeNegocioFinal){

		this.nomeUnidadeNegocioFinal = nomeUnidadeNegocioFinal;
	}


}
