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

package gcom.gui.arrecadacao.banco;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Action form de manter aviso bancario
 * 
 * @author thiago
 * @date 10/03/2006
 */
public class AvisoBancarioActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String acao = "";

	private String idAvisoBancario = "";

	private String posicao = "";

	private String idContaBancaria = "";

	private String tipoAcesso = "";

	private String acerto = "";

	private String dataAcerto = "";

	private String valorAcerto = "";

	private String idTipoDeducao = "";

	private String valorDeducao = "";

	private String valorDeducoes = "";

	private String valorAviso = "";

	private String numeroDocumento = "";

	private String dataRealizacao = "";

	private String valorArrecadacao = "";

	private String valorDevolucao = "";

	private String tipoAviso = "";

	private String valorAcertosArrecadacao = "";

	private String valorCalculadoArrecadacao = "";

	private String valorDiferencaArrecadacao = "";

	private String valorAcertosDevolucao = "";

	private String valorCalculadoDevolucao = "";

	private String valorDiferencaDevolucao = "";

	private String situacaoAvisoCredito = "";

	private String situacaoAvisoDebito = "";

	/**
	 * Método que limpa os atributos
	 * 
	 * @author Administrador
	 * @date 10/03/2006
	 * @param arg0
	 *            mapeamento
	 * @param arg1
	 *            request
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1){

		// TODO Auto-generated method stub
		super.reset(arg0, arg1);

		this.acao = "";
		this.idAvisoBancario = "";
		this.posicao = "";
		this.idContaBancaria = "";
		this.tipoAcesso = "";
		this.acerto = "";
		this.dataAcerto = "";
		this.valorAcerto = "";
		this.idTipoDeducao = "";
		this.valorDeducao = "";
		this.valorDeducoes = "";
		this.valorAviso = "";

		this.numeroDocumento = "";
		this.dataRealizacao = "";
		this.valorArrecadacao = "";
		this.valorDevolucao = "";

	}

	public String getDataRealizacao(){

		return dataRealizacao;
	}

	public void setDataRealizacao(String dataRealizacao){

		this.dataRealizacao = dataRealizacao;
	}

	public String getNumeroDocumento(){

		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento){

		this.numeroDocumento = numeroDocumento;
	}

	public String getValorArrecadacao(){

		return valorArrecadacao;
	}

	public void setValorArrecadacao(String valorArrecadacao){

		this.valorArrecadacao = valorArrecadacao;
	}

	public String getValorDevolucao(){

		return valorDevolucao;
	}

	public void setValorDevolucao(String valorDevolucao){

		this.valorDevolucao = valorDevolucao;
	}

	public String getAcao(){

		return acao;
	}

	public void setAcao(String acao){

		this.acao = acao;
	}

	public String getDataAcerto(){

		return dataAcerto;
	}

	public void setDataAcerto(String dataAcerto){

		this.dataAcerto = dataAcerto;
	}

	public String getPosicao(){

		return posicao;
	}

	public void setPosicao(String posicao){

		this.posicao = posicao;
	}

	public String getIdAvisoBancario(){

		return idAvisoBancario;
	}

	public void setIdAvisoBancario(String idAvisoBancario){

		this.idAvisoBancario = idAvisoBancario;
	}

	public String getIdContaBancaria(){

		return idContaBancaria;
	}

	public void setIdContaBancaria(String idContaBancaria){

		this.idContaBancaria = idContaBancaria;
	}

	public String getIdTipoDeducao(){

		return idTipoDeducao;
	}

	public void setIdTipoDeducao(String idTipoDeducao){

		this.idTipoDeducao = idTipoDeducao;
	}

	public String getTipoAcesso(){

		return tipoAcesso;
	}

	public void setTipoAcesso(String tipoAcesso){

		this.tipoAcesso = tipoAcesso;
	}

	public String getValorAcerto(){

		return valorAcerto;
	}

	public void setValorAcerto(String valorAcerto){

		this.valorAcerto = valorAcerto;
	}

	public String getValorDeducao(){

		return valorDeducao;
	}

	public void setValorDeducao(String valorDeducao){

		this.valorDeducao = valorDeducao;
	}

	/**
	 * @return Retorna o campo valorAviso.
	 */
	public String getValorAviso(){

		return valorAviso;
	}

	/**
	 * @param valorAviso
	 *            O valorAviso a ser setado.
	 */
	public void setValorAviso(String valorAviso){

		this.valorAviso = valorAviso;
	}

	/**
	 * @return Retorna o campo valorDeducoes.
	 */
	public String getValorDeducoes(){

		return valorDeducoes;
	}

	/**
	 * @param valorDeducoes
	 *            O valorDeducoes a ser setado.
	 */
	public void setValorDeducoes(String valorDeducoes){

		this.valorDeducoes = valorDeducoes;
	}

	/**
	 * @return Retorna o campo acerto.
	 */
	public String getAcerto(){

		return acerto;
	}

	/**
	 * @param acerto
	 *            O acerto a ser setado.
	 */
	public void setAcerto(String acerto){

		this.acerto = acerto;
	}

	public String getTipoAviso(){

		return tipoAviso;
	}

	public void setTipoAviso(String tipoAviso){

		this.tipoAviso = tipoAviso;
	}

	public String getSituacaoAvisoCredito(){

		return situacaoAvisoCredito;
	}

	public void setSituacaoAvisoCredito(String situacaoAvisoCredito){

		this.situacaoAvisoCredito = situacaoAvisoCredito;
	}

	public String getSituacaoAvisoDebito(){

		return situacaoAvisoDebito;
	}

	public void setSituacaoAvisoDebito(String situacaoAvisoDebito){

		this.situacaoAvisoDebito = situacaoAvisoDebito;
	}

	public String getValorAcertosArrecadacao(){

		return valorAcertosArrecadacao;
	}

	public void setValorAcertosArrecadacao(String valorAcertosArrecadacao){

		this.valorAcertosArrecadacao = valorAcertosArrecadacao;
	}

	public String getValorCalculadoArrecadacao(){

		return valorCalculadoArrecadacao;
	}

	public void setValorCalculadoArrecadacao(String valorCalculadoArrecadacao){

		this.valorCalculadoArrecadacao = valorCalculadoArrecadacao;
	}

	public String getValorDiferencaArrecadacao(){

		return valorDiferencaArrecadacao;
	}

	public void setValorDiferencaArrecadacao(String valorDiferencaArrecadacao){

		this.valorDiferencaArrecadacao = valorDiferencaArrecadacao;
	}

	public String getValorAcertosDevolucao(){

		return valorAcertosDevolucao;
	}

	public void setValorAcertosDevolucao(String valorAcertosDevolucao){

		this.valorAcertosDevolucao = valorAcertosDevolucao;
	}

	public String getValorCalculadoDevolucao(){

		return valorCalculadoDevolucao;
	}

	public void setValorCalculadoDevolucao(String valorCalculadoDevolucao){

		this.valorCalculadoDevolucao = valorCalculadoDevolucao;
	}

	public String getValorDiferencaDevolucao(){

		return valorDiferencaDevolucao;
	}

	public void setValorDiferencaDevolucao(String valorDiferencaDevolucao){

		this.valorDiferencaDevolucao = valorDiferencaDevolucao;
	}

}
