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

package gcom.atendimentopublico.ligacaoagua.bean;


/**
 * Helper com as informa��es que s�o exibidas na lista de Hist�rico de Manuten��o de Liga��o de �gua
 * 
 * @author Luciano Galv�o
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
			retorno = retorno + "Tipo de Servi�o: " + this.descricaoServicoTipoItemAssociado + "; ";
		}

		if(this.dataEmissaoItemAssociado != null){
			retorno = retorno + "Data de Emiss�o: " + this.dataEmissaoItemAssociado + "; ";
		}

		if(this.descricaoSituacaoOSAssociado != null){
			retorno = retorno + "Situa��o: " + this.descricaoSituacaoOSAssociado + "; ";
		}

		if(this.numeroLeituraExecucaoAssociado != null){
			retorno = retorno + "Leitura do Servi�o: " + this.numeroLeituraExecucaoAssociado + "; ";
		}

		if(this.descricaoCorteTipoAssociado != null){
			retorno = retorno + "Tipo de Corte: " + this.descricaoCorteTipoAssociado + "; ";
		}

		if(this.loginUsuarioAssociado != null){
			retorno = retorno + "Usu�rio que Informou a Execu��o da OS: " + this.loginUsuarioAssociado + "; ";
		}

		return retorno;
	}

	public void setHintServicoAssociado(String valor){
		// nothing to do
	}

	public String getHintSituacaoOS(){

		String retorno = "";
		if(this.descricaoSituacaoOS != null){
			retorno = retorno + "Situa��o: " + this.descricaoSituacaoOS + "; ";
		}

		if(this.numeroLeituraExecucao != null){
			retorno = retorno + "Leitura do Servi�o: " + this.numeroLeituraExecucao + "; ";
		}

		if(this.descricaoCorteTipo != null){
			retorno = retorno + "Tipo de Corte: " + this.descricaoCorteTipo + "; ";
		}

		if(this.loginUsuario != null){
			retorno = retorno + "Usu�rio que Informou a Execu��o da OS: " + this.loginUsuario + "; ";
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
