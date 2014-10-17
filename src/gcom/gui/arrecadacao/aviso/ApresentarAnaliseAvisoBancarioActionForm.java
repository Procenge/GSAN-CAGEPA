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

package gcom.gui.arrecadacao.aviso;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade gerar o formulário que irá apresentar a análise do aviso bancário
 * e os
 * pagamentos/devoluções associados
 * 
 * @author Raphael Rossiter
 * @date 23/03/2006
 */
public class ApresentarAnaliseAvisoBancarioActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idArrecadador;

	private String codigoNomeArrecadador;

	private String dataLancamento;

	private String sequencial;

	private String numeroDocumento;

	private String situacao;

	private String dataPrevistaCredito;

	private String dataRealCredito;

	private String anoMesArrecadacao;

	private String tipoAviso;

	private String contaBancaria;

	private String idAvisoBancario;

	private String bancoContaBancaria;

	private String agenciaContaBancaria;

	private String numeroContaBancaria;

	private String valorArrecadacaoInformado;

	private String valorDevolucaoInformado;

	private String valorSomatorioDeducoes;

	private String valorRealizado;

	private String valorInformado;

	private String valorAcertos;

	private String valorCalculado;

	private String valorDiferenca;

	private String descricaoValorTotalPagamentos;

	private String relatorioTipo;

	public String getCodigoNomeArrecadador(){

		return codigoNomeArrecadador;
	}

	public void setCodigoNomeArrecadador(String codigoNomeArrecadador){

		this.codigoNomeArrecadador = codigoNomeArrecadador;
	}

	public String getContaBancaria(){

		return contaBancaria;
	}

	public void setContaBancaria(String contaBancaria){

		this.contaBancaria = contaBancaria;
	}

	public String getDataLancamento(){

		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento){

		this.dataLancamento = dataLancamento;
	}

	public String getDataPrevistaCredito(){

		return dataPrevistaCredito;
	}

	public void setDataPrevistaCredito(String dataPrevistaCredito){

		this.dataPrevistaCredito = dataPrevistaCredito;
	}

	public String getDataRealCredito(){

		return dataRealCredito;
	}

	public void setDataRealCredito(String dataRealCredito){

		this.dataRealCredito = dataRealCredito;
	}

	public String getIdArrecadador(){

		return idArrecadador;
	}

	public void setIdArrecadador(String idArrecadador){

		this.idArrecadador = idArrecadador;
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

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getTipoAviso(){

		return tipoAviso;
	}

	public void setTipoAviso(String tipoAviso){

		this.tipoAviso = tipoAviso;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		/** @todo: finish this method, this is just the skeleton. */
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}

	public String getAnoMesArrecadacao(){

		return anoMesArrecadacao;
	}

	public void setAnoMesArrecadacao(String anoMesArrecadacao){

		this.anoMesArrecadacao = anoMesArrecadacao;
	}

	public String getAgenciaContaBancaria(){

		return agenciaContaBancaria;
	}

	public void setAgenciaContaBancaria(String agenciaContaBancaria){

		this.agenciaContaBancaria = agenciaContaBancaria;
	}

	public String getBancoContaBancaria(){

		return bancoContaBancaria;
	}

	public void setBancoContaBancaria(String bancoContaBancaria){

		this.bancoContaBancaria = bancoContaBancaria;
	}

	public String getNumeroContaBancaria(){

		return numeroContaBancaria;
	}

	public void setNumeroContaBancaria(String numeroContaBancaria){

		this.numeroContaBancaria = numeroContaBancaria;
	}

	public String getIdAvisoBancario(){

		return idAvisoBancario;
	}

	public void setIdAvisoBancario(String idAvisoBancario){

		this.idAvisoBancario = idAvisoBancario;
	}

	public String getRelatorioTipo(){

		return relatorioTipo;
	}

	public void setRelatorioTipo(String relatorioTipo){

		this.relatorioTipo = relatorioTipo;
	}

	public String getValorAcertos(){

		return valorAcertos;
	}

	public void setValorAcertos(String valorAcertos){

		this.valorAcertos = valorAcertos;
	}

	public String getValorCalculado(){

		return valorCalculado;
	}

	public void setValorCalculado(String valorCalculado){

		this.valorCalculado = valorCalculado;
	}

	public String getValorInformado(){

		return valorInformado;
	}

	public void setValorInformado(String valorInformado){

		this.valorInformado = valorInformado;
	}

	public String getValorDiferenca(){

		return valorDiferenca;
	}

	public void setValorDiferenca(String valorDiferenca){

		this.valorDiferenca = valorDiferenca;
	}

	public String getValorArrecadacaoInformado(){

		return valorArrecadacaoInformado;
	}

	public void setValorArrecadacaoInformado(String valorArrecadacaoInformado){

		this.valorArrecadacaoInformado = valorArrecadacaoInformado;
	}

	public String getValorDevolucaoInformado(){

		return valorDevolucaoInformado;
	}

	public void setValorDevolucaoInformado(String valorDevolucaoInformado){

		this.valorDevolucaoInformado = valorDevolucaoInformado;
	}

	public String getValorSomatorioDeducoes(){

		return valorSomatorioDeducoes;
	}

	public void setValorSomatorioDeducoes(String valorSomatorioDeducoes){

		this.valorSomatorioDeducoes = valorSomatorioDeducoes;
	}

	public String getValorRealizado(){

		return valorRealizado;
	}

	public void setValorRealizado(String valorRealizado){

		this.valorRealizado = valorRealizado;
	}

	public String getDescricaoValorTotalPagamentos(){

		return descricaoValorTotalPagamentos;
	}

	public void setDescricaoValorTotalPagamentos(String descricaoValorTotalPagamentos){

		this.descricaoValorTotalPagamentos = descricaoValorTotalPagamentos;
	}

}
