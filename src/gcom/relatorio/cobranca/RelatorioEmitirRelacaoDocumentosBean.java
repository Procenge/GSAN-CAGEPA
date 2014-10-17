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

package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

/**
 * [UC3032] Emitir Relação Documentos de Cobrança
 * 
 * @author Cinthya Cavalcanti
 * @created 23/12/2011
 */

public class RelatorioEmitirRelacaoDocumentosBean
				implements RelatorioBean {

	private String descricaoAcaoCobranca;

	private String idLocalidade;

	private String nomeLocalidade;

	private String inscricaoFormatada;

	private String enderecoImovel;

	private String matriculaImovel;

	private String nomeCliente;

	private String quantidadeItens;

	private String valorDebito;

	private String totalDocumentosCobrancaPorLocalidade;

	private String totalDocumentosCobrancaPorSetor;

	private String dataHoraEmissao;

	private String numeroSequenciaDocumento;

	private String numeroOrdemServico;

	private String categoriaImovel;

	private String qtdEconomias;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private String cdSetorComercial;

	private String nomeSetorComercial;

	private String totalOcorrenciasPorSetor;

	private String totalOcorrenciasPorLocalidade;

	public RelatorioEmitirRelacaoDocumentosBean(String descricaoAcaoCobranca, String idLocalidade, String nomeLocalidade,
												String inscricaoFormatada, String enderecoImovel, String matriculaImovel,
												String nomeCliente, String quantidadeItens, String valorDebito,
												String totalDocumentosCobrancaPorLocalidade, String dataHoraEmissao) {

		this.descricaoAcaoCobranca = descricaoAcaoCobranca;
		this.idLocalidade = idLocalidade;
		this.nomeLocalidade = nomeLocalidade;
		this.inscricaoFormatada = inscricaoFormatada;
		this.enderecoImovel = enderecoImovel;
		this.matriculaImovel = matriculaImovel;
		this.nomeCliente = nomeCliente;
		this.quantidadeItens = quantidadeItens;
		this.valorDebito = valorDebito;
		this.totalDocumentosCobrancaPorLocalidade = totalDocumentosCobrancaPorLocalidade;
		this.dataHoraEmissao = dataHoraEmissao;
	}

	public RelatorioEmitirRelacaoDocumentosBean(String descricaoAcaoCobranca, String idLocalidade, String nomeLocalidade,
												String inscricaoFormatada, String enderecoImovel, String matriculaImovel,
												String nomeCliente, String quantidadeItens, String valorDebito,
												String totalDocumentosCobrancaPorLocalidade, String dataHoraEmissao,
												String numeroSequenciaDocumento, String numeroOrdemServico, String categoriaImovel,
												String qtdEconomias, String situacaoAgua, String situacaoEsgoto, String cdSetorComercial,
												String nomeSetorComercial, String totalDocumentosCobrancaPorSetor,
												String totalOcorrenciasPorSetor, String totalOcorrenciasPorLocalidade) {

		this.descricaoAcaoCobranca = descricaoAcaoCobranca;
		this.idLocalidade = idLocalidade;
		this.nomeLocalidade = nomeLocalidade;
		this.inscricaoFormatada = inscricaoFormatada;
		this.enderecoImovel = enderecoImovel;
		this.matriculaImovel = matriculaImovel;
		this.nomeCliente = nomeCliente;
		this.quantidadeItens = quantidadeItens;
		this.valorDebito = valorDebito;
		this.totalDocumentosCobrancaPorLocalidade = totalDocumentosCobrancaPorLocalidade;
		this.dataHoraEmissao = dataHoraEmissao;
		this.numeroSequenciaDocumento = numeroSequenciaDocumento;
		this.numeroOrdemServico = numeroOrdemServico;
		this.categoriaImovel = categoriaImovel;
		this.qtdEconomias = qtdEconomias;
		this.situacaoAgua = situacaoAgua;
		this.situacaoEsgoto = situacaoEsgoto;
		this.cdSetorComercial = cdSetorComercial;
		this.nomeSetorComercial = nomeSetorComercial;
		this.totalDocumentosCobrancaPorSetor = totalDocumentosCobrancaPorSetor;
		this.totalOcorrenciasPorLocalidade = totalOcorrenciasPorLocalidade;
		this.totalOcorrenciasPorSetor = totalOcorrenciasPorSetor;
	}

	public RelatorioEmitirRelacaoDocumentosBean() {

	}

	public String getDescricaoAcaoCobranca(){

		return descricaoAcaoCobranca;
	}

	public void setDescricaoAcaoCobranca(String descricaoAcaoCobranca){

		this.descricaoAcaoCobranca = descricaoAcaoCobranca;
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

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getQuantidadeItens(){

		return quantidadeItens;
	}

	public void setQuantidadeItens(String quantidadeItens){

		this.quantidadeItens = quantidadeItens;
	}

	public String getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	public String getTotalDocumentosCobrancaPorLocalidade(){

		return totalDocumentosCobrancaPorLocalidade;
	}

	public void setTotalDocumentosCobrancaPorLocalidade(String totalDocumentosCobrancaPorLocalidade){

		this.totalDocumentosCobrancaPorLocalidade = totalDocumentosCobrancaPorLocalidade;
	}

	public String getInscricaoFormatada(){

		return inscricaoFormatada;
	}

	public void setInscricaoFormatada(String inscricaoFormatada){

		this.inscricaoFormatada = inscricaoFormatada;
	}

	public String getDataHoraEmissao(){

		return dataHoraEmissao;
	}

	public void setDataHoraEmissao(String dataHoraEmissao){

		this.dataHoraEmissao = dataHoraEmissao;
	}

	public String getNumeroSequenciaDocumento(){

		return numeroSequenciaDocumento;
	}

	public void setNumeroSequenciaDocumento(String numeroSequenciaDocumento){

		this.numeroSequenciaDocumento = numeroSequenciaDocumento;
	}

	public String getNumeroOrdemServico(){

		return numeroOrdemServico;
	}

	public void setNumeroOrdemServico(String numeroOrdemServico){

		this.numeroOrdemServico = numeroOrdemServico;
	}

	public String getCategoriaImovel(){

		return categoriaImovel;
	}

	public void setCategoriaImovel(String categoriaImovel){

		this.categoriaImovel = categoriaImovel;
	}

	public String getQtdEconomias(){

		return qtdEconomias;
	}

	public void setQtdEconomias(String qtdEconomias){

		this.qtdEconomias = qtdEconomias;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}
	
	public String getCdSetorComercial(){

		return cdSetorComercial;
	}

	public void setCdSetorComercial(String cdSetorComercial){

		this.cdSetorComercial = cdSetorComercial;
	}

	public String getNomeSetorComercial(){

		return nomeSetorComercial;
	}

	public void setNomeSetorComercial(String noemSetorComercial){

		this.nomeSetorComercial = noemSetorComercial;
	}

	public String getTotalDocumentosCobrancaPorSetor(){

		return totalDocumentosCobrancaPorSetor;
	}

	public void setTotalDocumentosCobrancaPorSetor(String totalDocumentosCobrancaPorSetor){

		this.totalDocumentosCobrancaPorSetor = totalDocumentosCobrancaPorSetor;
	}

	public String getTotalOcorrenciasPorSetor(){

		return totalOcorrenciasPorSetor;
	}

	public void setTotalOcorrenciasPorSetor(String totalOcorrenciasPorSetor){

		this.totalOcorrenciasPorSetor = totalOcorrenciasPorSetor;
	}

	public String getTotalOcorrenciasPorLocalidade(){

		return totalOcorrenciasPorLocalidade;
	}

	public void setTotalOcorrenciasPorLocalidade(String totalOcorrenciasPorLocalidade){

		this.totalOcorrenciasPorLocalidade = totalOcorrenciasPorLocalidade;
	}

}
