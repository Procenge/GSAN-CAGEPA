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

package gcom.gui.cobranca;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0218] Manter Resolução de Diretoria
 * 
 * @author Rafael Corrêa
 * @since 10/04/2006
 */
public class AtualizarResolucaoDiretoriaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String numero;

	private String assunto;

	private String dataInicio;

	private String dataFim;

	private String[] idsResolucaoDiretoriaLayout = {};

	private String[] idsGruposAcessoDisponiveis = {};

	private String[] idsGruposDeAcessoHabilitados = {};

	private String indicadorUsoRDParcImovel;

	private String indicadorUsoRDUsuarios;

	private String indicadorUsoRDDebitoCobrar;

	private String indicadorEmissaoAssuntoConta;

	private String indicadorTrataMediaAtualizacaoMonetaria;

	private String anoMesReferenciaDebitoInicio;

	private String anoMesReferenciaDebitoFim;

	private String idLocalidade;

	private String nomeLocalidade;

	private String dataPagamentoInicio;

	private String dataPagamentoFinal;

	private String percentualDescontoMulta;

	private String percentualDescontoJurosMora;

	private String percentualDescontoCorrecaoMonetaria;

	private String descricaoMensagemExtrato;

	private String indicadorCobrarDescontosArrasto;

	private String indicadorArrasto;

	private String dataNegociacaoInicio;

	private String dataNegociacaoFinal;

	private String percentualMinimoEntrada;

	public String getDataPagamentoInicio(){

		return dataPagamentoInicio;
	}

	public void setDataPagamentoInicio(String dataPagamentoInicio){

		this.dataPagamentoInicio = dataPagamentoInicio;
	}

	public String getDataPagamentoFinal(){

		return dataPagamentoFinal;
	}

	public void setDataPagamentoFinal(String dataPagamentoFinal){

		this.dataPagamentoFinal = dataPagamentoFinal;
	}

	public String getPercentualDescontoMulta(){

		return percentualDescontoMulta;
	}

	public void setPercentualDescontoMulta(String percentualDescontoMulta){

		this.percentualDescontoMulta = percentualDescontoMulta;
	}

	public String getPercentualDescontoJurosMora(){

		return percentualDescontoJurosMora;
	}

	public void setPercentualDescontoJurosMora(String percentualDescontoJurosMora){

		this.percentualDescontoJurosMora = percentualDescontoJurosMora;
	}

	public String getPercentualDescontoCorrecaoMonetaria(){

		return percentualDescontoCorrecaoMonetaria;
	}

	public void setPercentualDescontoCorrecaoMonetaria(String percentualDescontoCorrecaoMonetaria){

		this.percentualDescontoCorrecaoMonetaria = percentualDescontoCorrecaoMonetaria;
	}

	public String getDescricaoMensagemExtrato(){

		return descricaoMensagemExtrato;
	}

	public void setDescricaoMensagemExtrato(String descricaoMensagemExtrato){

		this.descricaoMensagemExtrato = descricaoMensagemExtrato;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		/** @todo: finish this method, this is just the skeleton. */
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		assunto = null;
		dataInicio = null;
		dataFim = null;
		idsResolucaoDiretoriaLayout = null;
	}

	public String getAssunto(){

		return assunto;
	}

	public void setAssunto(String assunto){

		this.assunto = assunto;
	}

	public String getDataFim(){

		return dataFim;
	}

	public void setDataFim(String dataFim){

		this.dataFim = dataFim;
	}

	public String getDataInicio(){

		return dataInicio;
	}

	public void setDataInicio(String dataInicio){

		this.dataInicio = dataInicio;
	}

	public String getNumero(){

		return numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	/**
	 * @return the idsResolucaoDiretoriaLayout
	 */
	public String[] getIdsResolucaoDiretoriaLayout(){

		return idsResolucaoDiretoriaLayout;
	}

	/**
	 * @param idsResolucaoDiretoriaLayout
	 *            the idsResolucaoDiretoriaLayout to set
	 */
	public void setIdsResolucaoDiretoriaLayout(String[] idsResolucaoDiretoriaLayout){

		this.idsResolucaoDiretoriaLayout = idsResolucaoDiretoriaLayout;
	}

	public String[] getIdsGruposAcessoDisponiveis(){

		return idsGruposAcessoDisponiveis;
	}

	public void setIdsGruposAcessoDisponiveis(String[] idsGruposAcessoDisponiveis){

		this.idsGruposAcessoDisponiveis = idsGruposAcessoDisponiveis;
	}

	public String[] getIdsGruposDeAcessoHabilitados(){

		return idsGruposDeAcessoHabilitados;
	}

	public void setIdsGruposDeAcessoHabilitados(String[] idsGruposDeAcessoHabilitados){

		this.idsGruposDeAcessoHabilitados = idsGruposDeAcessoHabilitados;
	}

	public String getIndicadorUsoRDParcImovel(){

		return indicadorUsoRDParcImovel;
	}

	public void setIndicadorUsoRDParcImovel(String indicadorUsoRDParcImovel){

		this.indicadorUsoRDParcImovel = indicadorUsoRDParcImovel;
	}

	public String getIndicadorUsoRDUsuarios(){

		return indicadorUsoRDUsuarios;
	}

	public void setIndicadorUsoRDUsuarios(String indicadorUsoRDUsuarios){

		this.indicadorUsoRDUsuarios = indicadorUsoRDUsuarios;
	}

	public String getIndicadorUsoRDDebitoCobrar(){

		return indicadorUsoRDDebitoCobrar;
	}

	public void setIndicadorUsoRDDebitoCobrar(String indicadorUsoRDDebitoCobrar){

		this.indicadorUsoRDDebitoCobrar = indicadorUsoRDDebitoCobrar;
	}

	public String getAnoMesReferenciaDebitoInicio(){

		return anoMesReferenciaDebitoInicio;
	}

	public void setAnoMesReferenciaDebitoInicio(String anoMesReferenciaDebitoInicio){

		this.anoMesReferenciaDebitoInicio = anoMesReferenciaDebitoInicio;
	}

	public String getAnoMesReferenciaDebitoFim(){

		return anoMesReferenciaDebitoFim;
	}

	public void setAnoMesReferenciaDebitoFim(String anoMesReferenciaDebitoFim){

		this.anoMesReferenciaDebitoFim = anoMesReferenciaDebitoFim;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getIndicadorEmissaoAssuntoConta(){

		return indicadorEmissaoAssuntoConta;
	}

	public void setIndicadorEmissaoAssuntoConta(String indicadorEmissaoAssuntoConta){

		this.indicadorEmissaoAssuntoConta = indicadorEmissaoAssuntoConta;
	}

	public String getIndicadorTrataMediaAtualizacaoMonetaria(){

		return indicadorTrataMediaAtualizacaoMonetaria;
	}

	public void setIndicadorTrataMediaAtualizacaoMonetaria(String indicadorTrataMediaAtualizacaoMonetaria){

		this.indicadorTrataMediaAtualizacaoMonetaria = indicadorTrataMediaAtualizacaoMonetaria;
	}

	public String getIndicadorCobrarDescontosArrasto(){

		return indicadorCobrarDescontosArrasto;
	}

	public void setIndicadorCobrarDescontosArrasto(String indicadorCobrarDescontosArrasto){

		this.indicadorCobrarDescontosArrasto = indicadorCobrarDescontosArrasto;
	}

	public String getDataNegociacaoInicio(){

		return dataNegociacaoInicio;
	}

	public void setDataNegociacaoInicio(String dataNegociacaoInicio){

		this.dataNegociacaoInicio = dataNegociacaoInicio;
	}

	public String getDataNegociacaoFinal(){

		return dataNegociacaoFinal;
	}

	public void setDataNegociacaoFinal(String dataNegociacaoFinal){

		this.dataNegociacaoFinal = dataNegociacaoFinal;
	}

	public String getPercentualMinimoEntrada(){

		return percentualMinimoEntrada;
	}

	public void setPercentualMinimoEntrada(String percentualMinimoEntrada){

		this.percentualMinimoEntrada = percentualMinimoEntrada;
	}

	public void setIndicadorArrasto(String indicadorArrasto){

		this.indicadorArrasto = indicadorArrasto;
	}

	public String getIndicadorArrasto(){

		return indicadorArrasto;
	}

}
