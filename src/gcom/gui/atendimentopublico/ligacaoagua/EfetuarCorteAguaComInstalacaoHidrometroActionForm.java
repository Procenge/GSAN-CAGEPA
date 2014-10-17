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

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class EfetuarCorteAguaComInstalacaoHidrometroActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idOrdemServico;

	private String nomeOrdemServico;

	// Im�vel
	private String matriculaImovel;

	private String inscricaoImovel;

	private String clienteUsuario;

	private String cpfCnpjCliente;

	private String situacaoLigacaoAgua;

	private String situacaoLigacaoEsgoto;

	// Dados da Liga��o
	// private String dataReligacao;
	private String dataCorte;

	private String motivoCorte;

	private String tipoCorte;

	private String numLeituraCorte;

	private String numSeloCorte;

	// Dados do Hidr�metro
	private String numeroHidrometro;

	private String dataInstalacao;

	private String localInstalacao;

	private String protecao;

	private String leituraInstalacao;

	private String numeroSelo;

	private String situacaoCavalete;

	private String medicaoTipo;

	private String dataRetirada;

	// Dados da Gera��o do D�bito
	private String idTipoDebito;

	private String descricaoTipoDebito;

	private String valorServico;

	private String valorDebito;

	private String motivoNaoCobranca;

	private String percentualCobranca;

	private String quantidadeParcelas;

	private String valorParcelas;

	private String quantidade;

	private String unidadeMedida;

	private String qtdeMaxParcelas;

	/*
	 * Colocado por Isilva em 16/12/2010
	 * [FS - Altera��o de Valor]
	 */
	private String alteracaoValor;

	// Controle
	private String veioEncerrarOS;

	private Date dataConcorrencia;

	/**
	 * @return the idOrdemServico
	 */
	public String getIdOrdemServico(){

		return idOrdemServico;
	}

	/**
	 * @param idOrdemServico
	 *            the idOrdemServico to set
	 */
	public void setIdOrdemServico(String idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	/**
	 * @return the nomeOrdemServico
	 */
	public String getNomeOrdemServico(){

		return nomeOrdemServico;
	}

	/**
	 * @param nomeOrdemServico
	 *            the nomeOrdemServico to set
	 */
	public void setNomeOrdemServico(String nomeOrdemServico){

		this.nomeOrdemServico = nomeOrdemServico;
	}

	/**
	 * @return the matriculaImovel
	 */
	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	/**
	 * @param matriculaImovel
	 *            the matriculaImovel to set
	 */
	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	/**
	 * @return the inscricaoImovel
	 */
	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	/**
	 * @param inscricaoImovel
	 *            the inscricaoImovel to set
	 */
	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	/**
	 * @return the clienteUsuario
	 */
	public String getClienteUsuario(){

		return clienteUsuario;
	}

	/**
	 * @param clienteUsuario
	 *            the clienteUsuario to set
	 */
	public void setClienteUsuario(String clienteUsuario){

		this.clienteUsuario = clienteUsuario;
	}

	/**
	 * @return the cpfCnpjCliente
	 */
	public String getCpfCnpjCliente(){

		return cpfCnpjCliente;
	}

	/**
	 * @param cpfCnpjCliente
	 *            the cpfCnpjCliente to set
	 */
	public void setCpfCnpjCliente(String cpfCnpjCliente){

		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	/**
	 * @return the situacaoLigacaoAgua
	 */
	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	/**
	 * @param situacaoLigacaoAgua
	 *            the situacaoLigacaoAgua to set
	 */
	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	/**
	 * @return the situacaoLigacaoEsgoto
	 */
	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	/**
	 * @param situacaoLigacaoEsgoto
	 *            the situacaoLigacaoEsgoto to set
	 */
	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	/**
	 * @return the dataCorte
	 */
	public String getDataCorte(){

		return dataCorte;
	}

	/**
	 * @param dataCorte
	 *            the dataCorte to set
	 */
	public void setDataCorte(String dataCorte){

		this.dataCorte = dataCorte;
	}

	/**
	 * @return the motivoCorte
	 */
	public String getMotivoCorte(){

		return motivoCorte;
	}

	/**
	 * @param motivoCorte
	 *            the motivoCorte to set
	 */
	public void setMotivoCorte(String motivoCorte){

		this.motivoCorte = motivoCorte;
	}

	/**
	 * @return the tipoCorte
	 */
	public String getTipoCorte(){

		return tipoCorte;
	}

	/**
	 * @param tipoCorte
	 *            the tipoCorte to set
	 */
	public void setTipoCorte(String tipoCorte){

		this.tipoCorte = tipoCorte;
	}

	/**
	 * @return the numLeituraCorte
	 */
	public String getNumLeituraCorte(){

		return numLeituraCorte;
	}

	/**
	 * @param numLeituraCorte
	 *            the numLeituraCorte to set
	 */
	public void setNumLeituraCorte(String numLeituraCorte){

		this.numLeituraCorte = numLeituraCorte;
	}

	/**
	 * @return the numSeloCorte
	 */
	public String getNumSeloCorte(){

		return numSeloCorte;
	}

	/**
	 * @param numSeloCorte
	 *            the numSeloCorte to set
	 */
	public void setNumSeloCorte(String numSeloCorte){

		this.numSeloCorte = numSeloCorte;
	}

	/**
	 * @return the numeroHidrometro
	 */
	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	/**
	 * @param numeroHidrometro
	 *            the numeroHidrometro to set
	 */
	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	/**
	 * @return the dataInstalacao
	 */
	public String getDataInstalacao(){

		return dataInstalacao;
	}

	/**
	 * @param dataInstalacao
	 *            the dataInstalacao to set
	 */
	public void setDataInstalacao(String dataInstalacao){

		this.dataInstalacao = dataInstalacao;
	}

	/**
	 * @return the localInstalacao
	 */
	public String getLocalInstalacao(){

		return localInstalacao;
	}

	/**
	 * @param localInstalacao
	 *            the localInstalacao to set
	 */
	public void setLocalInstalacao(String localInstalacao){

		this.localInstalacao = localInstalacao;
	}

	/**
	 * @return the protecao
	 */
	public String getProtecao(){

		return protecao;
	}

	/**
	 * @param protecao
	 *            the protecao to set
	 */
	public void setProtecao(String protecao){

		this.protecao = protecao;
	}

	/**
	 * @return the leituraInstalacao
	 */
	public String getLeituraInstalacao(){

		return leituraInstalacao;
	}

	/**
	 * @param leituraInstalacao
	 *            the leituraInstalacao to set
	 */
	public void setLeituraInstalacao(String leituraInstalacao){

		this.leituraInstalacao = leituraInstalacao;
	}

	/**
	 * @return the numeroSelo
	 */
	public String getNumeroSelo(){

		return numeroSelo;
	}

	/**
	 * @param numeroSelo
	 *            the numeroSelo to set
	 */
	public void setNumeroSelo(String numeroSelo){

		this.numeroSelo = numeroSelo;
	}

	/**
	 * @return the situacaoCavalete
	 */
	public String getSituacaoCavalete(){

		return situacaoCavalete;
	}

	/**
	 * @param situacaoCavalete
	 *            the situacaoCavalete to set
	 */
	public void setSituacaoCavalete(String situacaoCavalete){

		this.situacaoCavalete = situacaoCavalete;
	}

	/**
	 * @return the medicaoTipo
	 */
	public String getMedicaoTipo(){

		return medicaoTipo;
	}

	/**
	 * @param medicaoTipo
	 *            the medicaoTipo to set
	 */
	public void setMedicaoTipo(String medicaoTipo){

		this.medicaoTipo = medicaoTipo;
	}

	/**
	 * @return the dataRetirada
	 */
	public String getDataRetirada(){

		return dataRetirada;
	}

	/**
	 * @param dataRetirada
	 *            the dataRetirada to set
	 */
	public void setDataRetirada(String dataRetirada){

		this.dataRetirada = dataRetirada;
	}

	/**
	 * @return the idTipoDebito
	 */
	public String getIdTipoDebito(){

		return idTipoDebito;
	}

	/**
	 * @param idTipoDebito
	 *            the idTipoDebito to set
	 */
	public void setIdTipoDebito(String idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	/**
	 * @return the descricaoTipoDebito
	 */
	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	/**
	 * @param descricaoTipoDebito
	 *            the descricaoTipoDebito to set
	 */
	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	/**
	 * @return the valorServico
	 */
	public String getValorServico(){

		return valorServico;
	}

	/**
	 * @param valorServico
	 *            the valorServico to set
	 */
	public void setValorServico(String valorServico){

		this.valorServico = valorServico;
	}

	/**
	 * @return the valorDebito
	 */
	public String getValorDebito(){

		return valorDebito;
	}

	/**
	 * @param valorDebito
	 *            the valorDebito to set
	 */
	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	/**
	 * @return the motivoNaoCobranca
	 */
	public String getMotivoNaoCobranca(){

		return motivoNaoCobranca;
	}

	/**
	 * @param motivoNaoCobranca
	 *            the motivoNaoCobranca to set
	 */
	public void setMotivoNaoCobranca(String motivoNaoCobranca){

		this.motivoNaoCobranca = motivoNaoCobranca;
	}

	/**
	 * @return the percentualCobranca
	 */
	public String getPercentualCobranca(){

		return percentualCobranca;
	}

	/**
	 * @param percentualCobranca
	 *            the percentualCobranca to set
	 */
	public void setPercentualCobranca(String percentualCobranca){

		this.percentualCobranca = percentualCobranca;
	}

	/**
	 * @return the quantidadeParcelas
	 */
	public String getQuantidadeParcelas(){

		return quantidadeParcelas;
	}

	/**
	 * @param quantidadeParcelas
	 *            the quantidadeParcelas to set
	 */
	public void setQuantidadeParcelas(String quantidadeParcelas){

		this.quantidadeParcelas = quantidadeParcelas;
	}

	/**
	 * @return the valorParcelas
	 */
	public String getValorParcelas(){

		return valorParcelas;
	}

	/**
	 * @param valorParcelas
	 *            the valorParcelas to set
	 */
	public void setValorParcelas(String valorParcelas){

		this.valorParcelas = valorParcelas;
	}

	/**
	 * @return the quantidade
	 */
	public String getQuantidade(){

		return quantidade;
	}

	/**
	 * @param quantidade
	 *            the quantidade to set
	 */
	public void setQuantidade(String quantidade){

		this.quantidade = quantidade;
	}

	/**
	 * @return the unidadeMedida
	 */
	public String getUnidadeMedida(){

		return unidadeMedida;
	}

	/**
	 * @param unidadeMedida
	 *            the unidadeMedida to set
	 */
	public void setUnidadeMedida(String unidadeMedida){

		this.unidadeMedida = unidadeMedida;
	}

	/**
	 * @return the qtdeMaxParcelas
	 */
	public String getQtdeMaxParcelas(){

		return qtdeMaxParcelas;
	}

	/**
	 * @param qtdeMaxParcelas
	 *            the qtdeMaxParcelas to set
	 */
	public void setQtdeMaxParcelas(String qtdeMaxParcelas){

		this.qtdeMaxParcelas = qtdeMaxParcelas;
	}

	/**
	 * @return the alteracaoValor
	 */
	public String getAlteracaoValor(){

		return alteracaoValor;
	}

	/**
	 * @param alteracaoValor
	 *            the alteracaoValor to set
	 */
	public void setAlteracaoValor(String alteracaoValor){

		this.alteracaoValor = alteracaoValor;
	}

	/**
	 * @return the veioEncerrarOS
	 */
	public String getVeioEncerrarOS(){

		return veioEncerrarOS;
	}

	/**
	 * @param veioEncerrarOS
	 *            the veioEncerrarOS to set
	 */
	public void setVeioEncerrarOS(String veioEncerrarOS){

		this.veioEncerrarOS = veioEncerrarOS;
	}

	/**
	 * @return the dataConcorrencia
	 */
	public Date getDataConcorrencia(){

		return dataConcorrencia;
	}

	/**
	 * @param dataConcorrencia
	 *            the dataConcorrencia to set
	 */
	public void setDataConcorrencia(Date dataConcorrencia){

		this.dataConcorrencia = dataConcorrencia;
	}
}
