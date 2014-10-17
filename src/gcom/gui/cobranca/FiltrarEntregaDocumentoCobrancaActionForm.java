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

package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

/**
 * FiltrarDocumentoCobrancaInformarEntregaActionForm
 * [UC3028] Filtrar Documento Cobran�a para Informar Entrega
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
