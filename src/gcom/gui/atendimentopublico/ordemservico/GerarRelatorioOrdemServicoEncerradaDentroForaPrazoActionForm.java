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

package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Victon Santos
 * @date 20/12/2013
 */
public class GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	public static final String ENCERRADAS = "4";

	private String origemServico;

	private String[] tipoServico;

	private String[] tipoServicoSelecionados;

	private String idUnidadeAtendimento;

	private String descricaoUnidadeAtendimento;

	private String idUnidadeAtual;

	private String descricaoUnidadeAtual;

	private String idUnidadeEncerramento;

	private String descricaoUnidadeEncerramento;

	private String periodoAtendimentoInicial;

	private String periodoAtendimentoFinal;

	private String periodoEncerramentoInicial;

	private String periodoEncerramentoFinal;

	private String tipoOrdenacao;

	private String relatorioTipo;

	private String localidadeDescricaoFiltro;

	private String idLocalidadeFiltro;

	private String titulo;

	public void resetOS(){

		origemServico = null;

		idUnidadeAtendimento = null;
		descricaoUnidadeAtendimento = null;

		idUnidadeAtual = null;
		descricaoUnidadeAtual = null;

		idUnidadeEncerramento = null;
		descricaoUnidadeEncerramento = null;

		periodoAtendimentoInicial = null;
		periodoAtendimentoFinal = null;
		periodoEncerramentoInicial = null;
		periodoEncerramentoFinal = null;

		tipoOrdenacao = null;

	}

	/**
	 * @return Retorna o campo descricaoUnidadeAtendimento.
	 */
	public String getDescricaoUnidadeAtendimento(){

		return descricaoUnidadeAtendimento;
	}

	/**
	 * @param descricaoUnidadeAtendimento
	 *            O descricaoUnidadeAtendimento a ser setado.
	 */
	public void setDescricaoUnidadeAtendimento(String descricaoUnidadeAtendimento){

		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
	}

	/**
	 * @return Retorna o campo descricaoUnidadeAtual.
	 */
	public String getDescricaoUnidadeAtual(){

		return descricaoUnidadeAtual;
	}

	/**
	 * @param descricaoUnidadeAtual
	 *            O descricaoUnidadeAtual a ser setado.
	 */
	public void setDescricaoUnidadeAtual(String descricaoUnidadeAtual){

		this.descricaoUnidadeAtual = descricaoUnidadeAtual;
	}

	/**
	 * @return Retorna o campo descricaoUnidadeEncerramento.
	 */
	public String getDescricaoUnidadeEncerramento(){

		return descricaoUnidadeEncerramento;
	}

	/**
	 * @param descricaoUnidadeEncerramento
	 *            O descricaoUnidadeEncerramento a ser setado.
	 */
	public void setDescricaoUnidadeEncerramento(String descricaoUnidadeEncerramento){

		this.descricaoUnidadeEncerramento = descricaoUnidadeEncerramento;
	}

	/**
	 * @return Retorna o campo idUnidadeAtendimento.
	 */
	public String getIdUnidadeAtendimento(){

		return idUnidadeAtendimento;
	}

	/**
	 * @param idUnidadeAtendimento
	 *            O idUnidadeAtendimento a ser setado.
	 */
	public void setIdUnidadeAtendimento(String idUnidadeAtendimento){

		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	/**
	 * @return Retorna o campo idUnidadeAtual.
	 */
	public String getIdUnidadeAtual(){

		return idUnidadeAtual;
	}

	/**
	 * @param idUnidadeAtual
	 *            O idUnidadeAtual a ser setado.
	 */
	public void setIdUnidadeAtual(String idUnidadeAtual){

		this.idUnidadeAtual = idUnidadeAtual;
	}

	/**
	 * @return Retorna o campo idUnidadeEncerramento.
	 */
	public String getIdUnidadeEncerramento(){

		return idUnidadeEncerramento;
	}

	/**
	 * @param idUnidadeEncerramento
	 *            O idUnidadeEncerramento a ser setado.
	 */
	public void setIdUnidadeEncerramento(String idUnidadeEncerramento){

		this.idUnidadeEncerramento = idUnidadeEncerramento;
	}

	/**
	 * @return Retorna o campo origemServico.
	 */
	public String getOrigemServico(){

		return origemServico;
	}

	/**
	 * @param origemServico
	 *            O origemServico a ser setado.
	 */
	public void setOrigemServico(String origemServico){

		this.origemServico = origemServico;
	}

	/**
	 * @return Retorna o campo periodoAtendimentoFinal.
	 */
	public String getPeriodoAtendimentoFinal(){

		return periodoAtendimentoFinal;
	}

	/**
	 * @param periodoAtendimentoFinal
	 *            O periodoAtendimentoFinal a ser setado.
	 */
	public void setPeriodoAtendimentoFinal(String periodoAtendimentoFinal){

		this.periodoAtendimentoFinal = periodoAtendimentoFinal;
	}

	/**
	 * @return Retorna o campo periodoAtendimentoInicial.
	 */
	public String getPeriodoAtendimentoInicial(){

		return periodoAtendimentoInicial;
	}

	/**
	 * @param periodoAtendimentoInicial
	 *            O periodoAtendimentoInicial a ser setado.
	 */
	public void setPeriodoAtendimentoInicial(String periodoAtendimentoInicial){

		this.periodoAtendimentoInicial = periodoAtendimentoInicial;
	}

	/**
	 * @return Retorna o campo periodoEncerramentoFinal.
	 */
	public String getPeriodoEncerramentoFinal(){

		return periodoEncerramentoFinal;
	}

	/**
	 * @param periodoEncerramentoFinal
	 *            O periodoEncerramentoFinal a ser setado.
	 */
	public void setPeriodoEncerramentoFinal(String periodoEncerramentoFinal){

		this.periodoEncerramentoFinal = periodoEncerramentoFinal;
	}

	/**
	 * @return Retorna o campo periodoEncerramentoInicial.
	 */
	public String getPeriodoEncerramentoInicial(){

		return periodoEncerramentoInicial;
	}

	/**
	 * @param periodoEncerramentoInicial
	 *            O periodoEncerramentoInicial a ser setado.
	 */
	public void setPeriodoEncerramentoInicial(String periodoEncerramentoInicial){

		this.periodoEncerramentoInicial = periodoEncerramentoInicial;
	}

	/**
	 * @return Retorna o campo tipoOrdenacao.
	 */
	public String getTipoOrdenacao(){

		return tipoOrdenacao;
	}

	/**
	 * @param tipoOrdenacao
	 *            O tipoOrdenacao a ser setado.
	 */
	public void setTipoOrdenacao(String tipoOrdenacao){

		this.tipoOrdenacao = tipoOrdenacao;
	}

	/**
	 * @return Retorna o campo tipoServico.
	 */
	public String[] getTipoServico(){

		return tipoServico;
	}

	/**
	 * @param tipoServico
	 *            O tipoServico a ser setado.
	 */
	public void setTipoServico(String[] tipoServico){

		this.tipoServico = tipoServico;
	}

	/**
	 * @return Retorna o campo tipoServicoSelecionados.
	 */
	public String[] getTipoServicoSelecionados(){

		return tipoServicoSelecionados;
	}

	/**
	 * @param tipoServicoSelecionados
	 *            O tipoServicoSelecionados a ser setado.
	 */
	public void setTipoServicoSelecionados(String[] tipoServicoSelecionados){

		this.tipoServicoSelecionados = tipoServicoSelecionados;
	}

	/**
	 * @param tipoRelatorio
	 *            the tipoRelatorio to set
	 */
	public void setRelatorioTipo(String relatorioTipo){

		this.relatorioTipo = relatorioTipo;
	}

	/**
	 * @return the tipoRelatorio
	 */
	public String getRelatorioTipo(){

		return relatorioTipo;
	}

	public String getLocalidadeDescricaoFiltro(){

		return localidadeDescricaoFiltro;
	}

	public void setLocalidadeDescricaoFiltro(String localidadeDescricaoFiltro){

		this.localidadeDescricaoFiltro = localidadeDescricaoFiltro;
	}

	public String getIdLocalidadeFiltro(){

		return idLocalidadeFiltro;
	}

	public void setIdLocalidadeFiltro(String idLocalidadeFiltro){

		this.idLocalidadeFiltro = idLocalidadeFiltro;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo(){

		return titulo;
	}

}