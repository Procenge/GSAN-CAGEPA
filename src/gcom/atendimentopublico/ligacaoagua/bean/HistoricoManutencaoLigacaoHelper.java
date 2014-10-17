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


/**
 * Helper com as informações que são exibidas na lista de Histórico de Manutenção de Ligação de Água
 * 
 * @author Luciano Galvão
 * @created 18/09/2012
 */
public class HistoricoManutencaoLigacaoHelper {
	
	private String imovelId;
	
	private String dataEmissao;
	
	private String docOS;
	
	private String documentoCobranca;
	
	private String situacaoDocumento;
	
	private String dataSituacaoDocumento;
	
	private String servicoAssociado;
	
	private String ordemServico;
	
	private String situacaoOS;

	// Atributos auxiliares

	private String descricaoDocumentoTipo;

	private String descricaoSituacaoAcao;

	private String descricaoServicoTipoItemAssociado;

	private String dataEmissaoItemAssociado;

	private String descricaoServicoTipo;

	private String numeroLeituraExecucao;

	private String descricaoCorteTipo;

	private String descricaoSituacaoOS;

	private String loginUsuario;

	private String descricaoSituacaoOSAssociado;

	private String numeroLeituraExecucaoAssociado;

	private String descricaoCorteTipoAssociado;

	private String loginUsuarioAssociado;

	public String getImovelId(){

		return imovelId;
	}

	public void setImovelId(String imovelId){

		this.imovelId = imovelId;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getDocOS(){

		return docOS;
	}

	public void setDocOS(String docOS){

		this.docOS = docOS;
	}

	public String getDocumentoCobranca(){

		return documentoCobranca;
	}

	public void setDocumentoCobranca(String documentoCobranca){

		this.documentoCobranca = documentoCobranca;
	}

	public String getSituacaoDocumento(){

		return situacaoDocumento;
	}

	public void setSituacaoDocumento(String situacaoDocumento){

		this.situacaoDocumento = situacaoDocumento;
	}

	public String getDataSituacaoDocumento(){

		return dataSituacaoDocumento;
	}

	public void setDataSituacaoDocumento(String dataSituacaoDocumento){

		this.dataSituacaoDocumento = dataSituacaoDocumento;
	}

	public String getServicoAssociado(){

		return servicoAssociado;
	}
	
	public void setServicoAssociado(String servicoAssociado){

		this.servicoAssociado = servicoAssociado;
	}

	public String getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(String ordemServico){

		this.ordemServico = ordemServico;
	}

	public String getSituacaoOS(){

		return situacaoOS;
	}

	public void setSituacaoOS(String situacaoOS){

		this.situacaoOS = situacaoOS;
	}

	public String getDescricaoDocumentoTipo(){

		return descricaoDocumentoTipo;
	}

	public void setDescricaoDocumentoTipo(String descricaoDocumentoTipo){

		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
	}

	public String getDescricaoSituacaoAcao(){

		return descricaoSituacaoAcao;
	}

	public void setDescricaoSituacaoAcao(String descricaoSituacaoAcao){

		this.descricaoSituacaoAcao = descricaoSituacaoAcao;
	}

	public String getDescricaoServicoTipoItemAssociado(){

		return descricaoServicoTipoItemAssociado;
	}

	public void setDescricaoServicoTipoItemAssociado(String descricaoServicoTipoItemAssociado){

		this.descricaoServicoTipoItemAssociado = descricaoServicoTipoItemAssociado;
	}

	public String getDataEmissaoItemAssociado(){

		return dataEmissaoItemAssociado;
	}

	public void setDataEmissaoItemAssociado(String dataEmissaoItemAssociado){

		this.dataEmissaoItemAssociado = dataEmissaoItemAssociado;
	}

	public String getDescricaoServicoTipo(){

		return descricaoServicoTipo;
	}

	public void setDescricaoServicoTipo(String descricaoServicoTipo){

		this.descricaoServicoTipo = descricaoServicoTipo;
	}

	public String getNumeroLeituraExecucao(){

		return numeroLeituraExecucao;
	}

	public void setNumeroLeituraExecucao(String numeroLeituraExecucao){

		this.numeroLeituraExecucao = numeroLeituraExecucao;
	}

	public String getDescricaoCorteTipo(){

		return descricaoCorteTipo;
	}

	public void setDescricaoCorteTipo(String descricaoCorteTipo){

		this.descricaoCorteTipo = descricaoCorteTipo;
	}

	public String getLoginUsuario(){

		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario){

		this.loginUsuario = loginUsuario;
	}

	public String getDescricaoSituacaoOS(){

		return descricaoSituacaoOS;
	}

	public void setDescricaoSituacaoOS(String descricaoSituacaoOS){

		this.descricaoSituacaoOS = descricaoSituacaoOS;
	}

	public String getHintServicoAssociado(){

		String retorno = "";
		if(this.descricaoServicoTipoItemAssociado != null){
			retorno = retorno + "Tipo de Serviço: " + this.descricaoServicoTipoItemAssociado + "; ";
		}

		if(this.dataEmissaoItemAssociado != null){
			retorno = retorno + "Data de Emissão: " + this.dataEmissaoItemAssociado + "; ";
		}

		if(this.descricaoSituacaoOSAssociado != null){
			retorno = retorno + "Situação: " + this.descricaoSituacaoOSAssociado + "; ";
		}

		if(this.numeroLeituraExecucaoAssociado != null){
			retorno = retorno + "Leitura do Serviço: " + this.numeroLeituraExecucaoAssociado + "; ";
		}

		if(this.descricaoCorteTipoAssociado != null){
			retorno = retorno + "Tipo de Corte: " + this.descricaoCorteTipoAssociado + "; ";
		}

		if(this.loginUsuarioAssociado != null){
			retorno = retorno + "Usuário que Informou a Execução da OS: " + this.loginUsuarioAssociado + "; ";
		}

		return retorno;
	}

	public void setHintServicoAssociado(String valor){
		// nothing to do
	}

	public String getHintSituacaoOS(){

		String retorno = "";
		if(this.descricaoSituacaoOS != null){
			retorno = retorno + "Situação: " + this.descricaoSituacaoOS + "; ";
		}

		if(this.numeroLeituraExecucao != null){
			retorno = retorno + "Leitura do Serviço: " + this.numeroLeituraExecucao + "; ";
		}

		if(this.descricaoCorteTipo != null){
			retorno = retorno + "Tipo de Corte: " + this.descricaoCorteTipo + "; ";
		}

		if(this.loginUsuario != null){
			retorno = retorno + "Usuário que Informou a Execução da OS: " + this.loginUsuario + "; ";
		}

		return retorno;
	}

	public void setHintSituacaoOS(String valor){

		// nothing to do
	}

	public String getDescricaoSituacaoOSAssociado(){

		return descricaoSituacaoOSAssociado;
	}

	public void setDescricaoSituacaoOSAssociado(String descricaoSituacaoOSAssociado){

		this.descricaoSituacaoOSAssociado = descricaoSituacaoOSAssociado;
	}

	public String getNumeroLeituraExecucaoAssociado(){

		return numeroLeituraExecucaoAssociado;
	}

	public void setNumeroLeituraExecucaoAssociado(String numeroLeituraExecucaoAssociado){

		this.numeroLeituraExecucaoAssociado = numeroLeituraExecucaoAssociado;
	}

	public String getDescricaoCorteTipoAssociado(){

		return descricaoCorteTipoAssociado;
	}

	public void setDescricaoCorteTipoAssociado(String descricaoCorteTipoAssociado){

		this.descricaoCorteTipoAssociado = descricaoCorteTipoAssociado;
	}

	public String getLoginUsuarioAssociado(){

		return loginUsuarioAssociado;
	}

	public void setLoginUsuarioAssociado(String loginUsuarioAssociado){

		this.loginUsuarioAssociado = loginUsuarioAssociado;
	}
}
