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

package gcom.gui.faturamento.conta;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Alteração no ActionForm para inclusão de atributos de Leitura da Referência da Conta.
 * 
 * @author eduardo henrique
 * @date 15/02/2009
 */
public class RetificarContaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String consumoAgua;

	private String consumoEsgoto;

	private String idImovel;

	private String inscricaoImovel;

	private String mesAnoConta;

	private String motivoRetificacaoID;

	private String nomeClienteUsuario;

	private String nomeClienteResponsavel;

	private String percentualEsgoto;

	private String situacaoAguaConta;

	private String situacaoAguaImovel;

	private String situacaoEsgotoConta;

	private String situacaoEsgotoImovel;

	private String valorAgua;

	private String valorEsgoto;

	private String vencimentoConta;

	private String valorDebito;

	private String valorCredito;

	private String valorTotal;

	private String mesAnoMedicaoHistoricoAgua;

	private String numeroLeituraAtualMedicaoHistoricoAgua;

	private String numeroLeituraAnteriorMedicaoHistoricoAgua;

	private String dataLeituraAtualMedicaoHistoricoAgua;

	private String leituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua;

	private String mesAnoMedicaoHistoricoEsgoto;

	private String numeroLeituraAtualMedicaoHistoricoEsgoto;

	private String numeroLeituraAnteriorMedicaoHistoricoEsgoto;

	private String dataLeituraAtualMedicaoHistoricoEsgoto;

	private String leituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto;

	private String dataLeituraAnteriorMedicaoHistoricoAgua;

	private String fixoEsgoto;

	private String ligacaoEsgotoPerfilId;

	private String indicadorEsgotoFaturavel;

	private String indicadorAguaFaturavel;

	private String vencimentoContaAnterior;

	private String indicadorPermissaoAlterarPercentualEsgoto;

	private String consumoTarifaId;

	private String idCliente;

	private String nomeCliente;

	public String getValorCredito(){

		return valorCredito;
	}

	public void setValorCredito(String valorCredito){

		this.valorCredito = valorCredito;
	}

	public String getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	public String getValorTotal(){

		return valorTotal;
	}

	public void setValorTotal(String valorTotal){

		this.valorTotal = valorTotal;
	}

	public String getConsumoAgua(){

		return consumoAgua;
	}

	public void setConsumoAgua(String consumoAgua){

		this.consumoAgua = consumoAgua;
	}

	public String getConsumoEsgoto(){

		return consumoEsgoto;
	}

	public void setConsumoEsgoto(String consumoEsgoto){

		this.consumoEsgoto = consumoEsgoto;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getMesAnoConta(){

		return mesAnoConta;
	}

	public void setMesAnoConta(String mesAnoConta){

		this.mesAnoConta = mesAnoConta;
	}

	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	public String getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(String percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	public String getSituacaoAguaConta(){

		return situacaoAguaConta;
	}

	public void setSituacaoAguaConta(String situacaoAguaConta){

		this.situacaoAguaConta = situacaoAguaConta;
	}

	public String getSituacaoAguaImovel(){

		return situacaoAguaImovel;
	}

	public void setSituacaoAguaImovel(String situacaoAguaImovel){

		this.situacaoAguaImovel = situacaoAguaImovel;
	}

	public String getSituacaoEsgotoConta(){

		return situacaoEsgotoConta;
	}

	public void setSituacaoEsgotoConta(String situacaoEsgotoConta){

		this.situacaoEsgotoConta = situacaoEsgotoConta;
	}

	public String getSituacaoEsgotoImovel(){

		return situacaoEsgotoImovel;
	}

	public void setSituacaoEsgotoImovel(String situacaoEsgotoImovel){

		this.situacaoEsgotoImovel = situacaoEsgotoImovel;
	}

	public String getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(String valorAgua){

		this.valorAgua = valorAgua;
	}

	public String getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(String valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public String getVencimentoConta(){

		return vencimentoConta;
	}

	public void setVencimentoConta(String vencimentoConta){

		this.vencimentoConta = vencimentoConta;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		/** @todo: finish this method, this is just the skeleton. */
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getMotivoRetificacaoID(){

		return motivoRetificacaoID;
	}

	public void setMotivoRetificacaoID(String motivoRetificacaoID){

		this.motivoRetificacaoID = motivoRetificacaoID;
	}

	public String getMesAnoMedicaoHistoricoAgua(){

		return mesAnoMedicaoHistoricoAgua;
	}

	public void setMesAnoMedicaoHistoricoAgua(String mesAnoMedicaoHistoricoAgua){

		this.mesAnoMedicaoHistoricoAgua = mesAnoMedicaoHistoricoAgua;
	}

	public String getNumeroLeituraAtualMedicaoHistoricoAgua(){

		return numeroLeituraAtualMedicaoHistoricoAgua;
	}

	public void setNumeroLeituraAtualMedicaoHistoricoAgua(String numeroLeituraAtualMedicaoHistoricoAgua){

		this.numeroLeituraAtualMedicaoHistoricoAgua = numeroLeituraAtualMedicaoHistoricoAgua;
	}

	public String getNumeroLeituraAnteriorMedicaoHistoricoAgua(){

		return numeroLeituraAnteriorMedicaoHistoricoAgua;
	}

	public void setNumeroLeituraAnteriorMedicaoHistoricoAgua(String numeroLeituraAnteriorMedicaoHistoricoAgua){

		this.numeroLeituraAnteriorMedicaoHistoricoAgua = numeroLeituraAnteriorMedicaoHistoricoAgua;
	}

	public String getDataLeituraAtualMedicaoHistoricoAgua(){

		return dataLeituraAtualMedicaoHistoricoAgua;
	}

	public void setDataLeituraAtualMedicaoHistoricoAgua(String dataLeituraAtualMedicaoHistoricoAgua){

		this.dataLeituraAtualMedicaoHistoricoAgua = dataLeituraAtualMedicaoHistoricoAgua;
	}

	public String getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua(){

		return leituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua;
	}

	public void setLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua(String leituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua){

		this.leituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua = leituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua;
	}

	public String getMesAnoMedicaoHistoricoEsgoto(){

		return mesAnoMedicaoHistoricoEsgoto;
	}

	public void setMesAnoMedicaoHistoricoEsgoto(String mesAnoMedicaoHistoricoEsgoto){

		this.mesAnoMedicaoHistoricoEsgoto = mesAnoMedicaoHistoricoEsgoto;
	}

	public String getNumeroLeituraAtualMedicaoHistoricoEsgoto(){

		return numeroLeituraAtualMedicaoHistoricoEsgoto;
	}

	public void setNumeroLeituraAtualMedicaoHistoricoEsgoto(String numeroLeituraAtualMedicaoHistoricoEsgoto){

		this.numeroLeituraAtualMedicaoHistoricoEsgoto = numeroLeituraAtualMedicaoHistoricoEsgoto;
	}

	public String getNumeroLeituraAnteriorMedicaoHistoricoEsgoto(){

		return numeroLeituraAnteriorMedicaoHistoricoEsgoto;
	}

	public void setNumeroLeituraAnteriorMedicaoHistoricoEsgoto(String numeroLeituraAnteriorMedicaoHistoricoEsgoto){

		this.numeroLeituraAnteriorMedicaoHistoricoEsgoto = numeroLeituraAnteriorMedicaoHistoricoEsgoto;
	}

	public String getDataLeituraAtualMedicaoHistoricoEsgoto(){

		return dataLeituraAtualMedicaoHistoricoEsgoto;
	}

	public void setDataLeituraAtualMedicaoHistoricoEsgoto(String dataLeituraAtualMedicaoHistoricoEsgoto){

		this.dataLeituraAtualMedicaoHistoricoEsgoto = dataLeituraAtualMedicaoHistoricoEsgoto;
	}

	public String getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto(){

		return leituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto;
	}

	public void setLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto(String leituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto){

		this.leituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto = leituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto;
	}

	public String getDataLeituraAnteriorMedicaoHistoricoAgua(){

		return dataLeituraAnteriorMedicaoHistoricoAgua;
	}

	public void setDataLeituraAnteriorMedicaoHistoricoAgua(String dataLeituraAnteriorMedicaoHistoricoAgua){

		this.dataLeituraAnteriorMedicaoHistoricoAgua = dataLeituraAnteriorMedicaoHistoricoAgua;
	}

	public void setNomeClienteResponsavel(String nomeClienteResponsavel){

		this.nomeClienteResponsavel = nomeClienteResponsavel;
	}

	public String getNomeClienteResponsavel(){

		return nomeClienteResponsavel;
	}

	public void setFixoEsgoto(String fixoEsgoto){

		this.fixoEsgoto = fixoEsgoto;
	}

	public String getFixoEsgoto(){

		return fixoEsgoto;
	}

	public String getLigacaoEsgotoPerfilId(){

		return ligacaoEsgotoPerfilId;
	}

	public void setLigacaoEsgotoPerfilId(String ligacaoEsgotoPerfilId){

		this.ligacaoEsgotoPerfilId = ligacaoEsgotoPerfilId;
	}

	public String getIndicadorEsgotoFaturavel(){

		return indicadorEsgotoFaturavel;
	}

	public void setIndicadorEsgotoFaturavel(String indicadorEsgotoFaturavel){

		this.indicadorEsgotoFaturavel = indicadorEsgotoFaturavel;
	}

	public String getVencimentoContaAnterior(){

		return vencimentoContaAnterior;
	}

	public void setVencimentoContaAnterior(String vencimentoContaAnterior){

		this.vencimentoContaAnterior = vencimentoContaAnterior;
	}

	public String getIndicadorPermissaoAlterarPercentualEsgoto(){

		return indicadorPermissaoAlterarPercentualEsgoto;
	}

	public void setIndicadorPermissaoAlterarPercentualEsgoto(String indicadorPermissaoAlterarPercentualEsgoto){

		this.indicadorPermissaoAlterarPercentualEsgoto = indicadorPermissaoAlterarPercentualEsgoto;
	}

	public String getConsumoTarifaId(){

		return consumoTarifaId;
	}

	public void setConsumoTarifaId(String consumoTarifaId){

		this.consumoTarifaId = consumoTarifaId;
	}

	public String getIndicadorAguaFaturavel(){

		return indicadorAguaFaturavel;
	}

	public void setIndicadorAguaFaturavel(String indicadorAguaFaturavel){

		this.indicadorAguaFaturavel = indicadorAguaFaturavel;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

}
