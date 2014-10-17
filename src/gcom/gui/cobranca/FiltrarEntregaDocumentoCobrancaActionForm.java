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

import org.apache.struts.action.ActionForm;

/**
 * FiltrarDocumentoCobrancaInformarEntregaActionForm
 * [UC3028] Filtrar Documento Cobrança para Informar Entrega
 * 
 * @author Cinthya Cavalcanti
 * @created 12 de Dezembro de 2011
 */

public class FiltrarEntregaDocumentoCobrancaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String indicadorDocumentos;

	private String indicadorOrdenacaoDocumentos;

	private String cobrancaAcaoAtividadeComando;

	private String cobrancaAcaoAtividadeComandoDescricao;

	private String empresa;

	private String empresaCriterioPeloComando;

	private String sequencialInicialDocumentos;

	private String sequencialFinalDocumentos;

	private String localidadeInicial;

	private String descricaoLocalidadeInicial;

	private String setorComercialInicial;

	private String setorComercialInicialCodigo;

	private String descricaoSetorComercialInicial;

	private String quadraInicial;

	private String localidadeFinal;

	private String descricaoLocalidadeFinal;

	private String setorComercialFinal;

	private String setorComercialFinalCodigo;

	private String descricaoSetorComercialFinal;

	private String quadraFinal;

	private String[] cobrancaAcao;

	private String imovel;

	private String inscricaoImovel;

	private String dataInicialGeracao;

	private String dataFinalGeracao;

	private String atualizarFiltro;

	public String getIndicadorDocumentos(){

		return indicadorDocumentos;
	}

	public void setIndicadorDocumentos(String indicadorDocumentos){

		this.indicadorDocumentos = indicadorDocumentos;
	}

	public String getIndicadorOrdenacaoDocumentos(){

		return indicadorOrdenacaoDocumentos;
	}

	public void setIndicadorOrdenacaoDocumentos(String indicadorOrdenacaoDocumentos){

		this.indicadorOrdenacaoDocumentos = indicadorOrdenacaoDocumentos;
	}

	public String getCobrancaAcaoAtividadeComando(){

		return cobrancaAcaoAtividadeComando;
	}

	public void setCobrancaAcaoAtividadeComando(String cobrancaAcaoAtividadeComando){

		this.cobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando;
	}

	public String getCobrancaAcaoAtividadeComandoDescricao(){

		return cobrancaAcaoAtividadeComandoDescricao;
	}

	public void setCobrancaAcaoAtividadeComandoDescricao(String cobrancaAcaoAtividadeComandoDescricao){

		this.cobrancaAcaoAtividadeComandoDescricao = cobrancaAcaoAtividadeComandoDescricao;
	}

	public String getEmpresa(){

		return empresa;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public String getEmpresaCriterioPeloComando(){

		return empresaCriterioPeloComando;
	}

	public void setEmpresaCriterioPeloComando(String empresaCriterioPeloComando){

		this.empresaCriterioPeloComando = empresaCriterioPeloComando;
	}

	public String getSequencialInicialDocumentos(){

		return sequencialInicialDocumentos;
	}

	public void setSequencialInicialDocumentos(String sequencialInicialDocumentos){

		this.sequencialInicialDocumentos = sequencialInicialDocumentos;
	}

	public String getSequencialFinalDocumentos(){

		return sequencialFinalDocumentos;
	}

	public void setSequencialFinalDocumentos(String sequencialFinalDocumentos){

		this.sequencialFinalDocumentos = sequencialFinalDocumentos;
	}

	public String getLocalidadeInicial(){

		return localidadeInicial;
	}

	public void setLocalidadeInicial(String localidadeInicial){

		this.localidadeInicial = localidadeInicial;
	}

	public String getDescricaoLocalidadeInicial(){

		return descricaoLocalidadeInicial;
	}

	public void setDescricaoLocalidadeInicial(String descricaoLocalidadeInicial){

		this.descricaoLocalidadeInicial = descricaoLocalidadeInicial;
	}

	public String getSetorComercialInicial(){

		return setorComercialInicial;
	}

	public void setSetorComercialInicial(String setorComercialInicial){

		this.setorComercialInicial = setorComercialInicial;
	}

	public String getDescricaoSetorComercialInicial(){

		return descricaoSetorComercialInicial;
	}

	public void setDescricaoSetorComercialInicial(String descricaoSetorComercialInicial){

		this.descricaoSetorComercialInicial = descricaoSetorComercialInicial;
	}

	public String getQuadraInicial(){

		return quadraInicial;
	}

	public void setQuadraInicial(String quadraInicial){

		this.quadraInicial = quadraInicial;
	}

	public String getLocalidadeFinal(){

		return localidadeFinal;
	}

	public void setLocalidadeFinal(String localidadeFinal){

		this.localidadeFinal = localidadeFinal;
	}

	public String getDescricaoLocalidadeFinal(){

		return descricaoLocalidadeFinal;
	}

	public void setDescricaoLocalidadeFinal(String descricaoLocalidadeFinal){

		this.descricaoLocalidadeFinal = descricaoLocalidadeFinal;
	}

	public String getSetorComercialFinal(){

		return setorComercialFinal;
	}

	public void setSetorComercialFinal(String setorComercialFinal){

		this.setorComercialFinal = setorComercialFinal;
	}

	public String getDescricaoSetorComercialFinal(){

		return descricaoSetorComercialFinal;
	}

	public void setDescricaoSetorComercialFinal(String descricaoSetorComercialFinal){

		this.descricaoSetorComercialFinal = descricaoSetorComercialFinal;
	}

	public String getQuadraFinal(){

		return quadraFinal;
	}

	public void setQuadraFinal(String quadraFinal){

		this.quadraFinal = quadraFinal;
	}

	public String[] getCobrancaAcao(){

		return cobrancaAcao;
	}

	public void setCobrancaAcao(String[] cobrancaAcao){

		this.cobrancaAcao = cobrancaAcao;
	}

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

	public String getDataInicialGeracao(){

		return dataInicialGeracao;
	}

	public void setDataInicialGeracao(String dataInicialGeracao){

		this.dataInicialGeracao = dataInicialGeracao;
	}

	public String getDataFinalGeracao(){

		return dataFinalGeracao;
	}

	public void setDataFinalGeracao(String dataFinalGeracao){

		this.dataFinalGeracao = dataFinalGeracao;
	}

	public String getAtualizarFiltro(){

		return atualizarFiltro;
	}

	public void setAtualizarFiltro(String atualizarFiltro){

		this.atualizarFiltro = atualizarFiltro;
	}

	public String getSetorComercialInicialCodigo(){

		return setorComercialInicialCodigo;
	}

	public void setSetorComercialInicialCodigo(String setorComercialInicialCodigo){

		this.setorComercialInicialCodigo = setorComercialInicialCodigo;
	}

	public String getSetorComercialFinalCodigo(){

		return setorComercialFinalCodigo;
	}

	public void setSetorComercialFinalCodigo(String setorComercialFinalCodigo){

		this.setorComercialFinalCodigo = setorComercialFinalCodigo;
	}

}
