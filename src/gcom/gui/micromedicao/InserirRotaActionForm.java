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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.micromedicao;

import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.*;

/**
 * Form utilizado no Inserir Rota e no Atualizar Rota
 * 
 * @author Vivianne Sousa
 * @created 21/03/2006
 * @author eduardo henrique
 * @date 25/08/2008
 *       Altera��o para obrigatoriedade do Leiturista/Agente Comercial no cadastro da Rota
 */
public class InserirRotaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idLocalidade;

	private String localidadeDescricao;

	private String codigoSetorComercial;

	private String setorComercialDescricao;

	private String codigoRota;

	private String cobrancaGrupo;

	private String faturamentoGrupo;

	private String leituraTipo;

	private String empresaLeituristica;

	private String cobrancaCriterio;

	private String dataAjusteLeitura;

	private String indicadorAjusteConsumo;

	private String indicadorFiscalizarCortado;

	private String indicadorFiscalizarSuprimido;

	private String indicadorGerarFalsaFaixa;

	private String percentualGeracaoFaixaFalsa;

	private String indicadorGerarFiscalizacao;

	private String percentualGeracaoFiscalizacao;

	private String indicadorUso;

	private String ultimaAlteracao;

	private String codigoLeiturista;

	private String nomeLeiturista;

	private String cobrancaAcao;

	private String idCobrancaCriterio;

	private String descricaoCobrancaCriterio;

	public String getCobrancaAcao(){

		return cobrancaAcao;
	}

	public void setCobrancaAcao(String cobrancaAcao){

		this.cobrancaAcao = cobrancaAcao;
	}

	public String getDescricaoCobrancaCriterio(){

		return descricaoCobrancaCriterio;
	}

	public void setDescricaoCobrancaCriterio(String descricaoCobrancaCriterio){

		this.descricaoCobrancaCriterio = descricaoCobrancaCriterio;
	}

	public String getIdCobrancaCriterio(){

		return idCobrancaCriterio;
	}

	public void setIdCobrancaCriterio(String idCobrancaCriterio){

		this.idCobrancaCriterio = idCobrancaCriterio;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getCobrancaGrupo(){

		return cobrancaGrupo;
	}

	public void setCobrancaGrupo(String cobrancaGrupo){

		this.cobrancaGrupo = cobrancaGrupo;
	}

	public String getEmpresaLeituristica(){

		return empresaLeituristica;
	}

	public void setEmpresaLeituristica(String empresaLeituristica){

		this.empresaLeituristica = empresaLeituristica;
	}

	public String getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(String faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getIndicadorFiscalizarCortado(){

		return indicadorFiscalizarCortado;
	}

	public void setIndicadorFiscalizarCortado(String indicadorFiscalizarCortado){

		this.indicadorFiscalizarCortado = indicadorFiscalizarCortado;
	}

	public String getIndicadorFiscalizarSuprimido(){

		return indicadorFiscalizarSuprimido;
	}

	public void setIndicadorFiscalizarSuprimido(String indicadorFiscalizarSuprimido){

		this.indicadorFiscalizarSuprimido = indicadorFiscalizarSuprimido;
	}

	public String getIndicadorGerarFalsaFaixa(){

		return indicadorGerarFalsaFaixa;
	}

	public void setIndicadorGerarFalsaFaixa(String indicadorGerarFalsaFaixa){

		this.indicadorGerarFalsaFaixa = indicadorGerarFalsaFaixa;
	}

	public String getIndicadorGerarFiscalizacao(){

		return indicadorGerarFiscalizacao;
	}

	public void setIndicadorGerarFiscalizacao(String indicadorGerarFiscalizacao){

		this.indicadorGerarFiscalizacao = indicadorGerarFiscalizacao;
	}

	public String getLeituraTipo(){

		return leituraTipo;
	}

	public void setLeituraTipo(String leituraTipo){

		this.leituraTipo = leituraTipo;
	}

	public String getLocalidadeDescricao(){

		return localidadeDescricao;
	}

	public void setLocalidadeDescricao(String localidadeDescricao){

		this.localidadeDescricao = localidadeDescricao;
	}

	public String getPercentualGeracaoFaixaFalsa(){

		return percentualGeracaoFaixaFalsa;
	}

	public void setPercentualGeracaoFaixaFalsa(String percentualGeracaoFaixaFalsa){

		this.percentualGeracaoFaixaFalsa = percentualGeracaoFaixaFalsa;
	}

	public String getPercentualGeracaoFiscalizacao(){

		return percentualGeracaoFiscalizacao;
	}

	public void setPercentualGeracaoFiscalizacao(String percentualGeracaoFiscalizacao){

		this.percentualGeracaoFiscalizacao = percentualGeracaoFiscalizacao;
	}

	public String getSetorComercialDescricao(){

		return setorComercialDescricao;
	}

	public void setSetorComercialDescricao(String setorComercialDescricao){

		this.setorComercialDescricao = setorComercialDescricao;
	}

	public String getCobrancaCriterio(){

		return cobrancaCriterio;
	}

	public void setCobrancaCriterio(String cobrancaCriterio){

		this.cobrancaCriterio = cobrancaCriterio;
	}

	public String getDataAjusteLeitura(){

		return dataAjusteLeitura;
	}

	public void setDataAjusteLeitura(String dataAjusteLeitura){

		this.dataAjusteLeitura = dataAjusteLeitura;
	}

	public String getIndicadorAjusteConsumo(){

		return indicadorAjusteConsumo;
	}

	public void setIndicadorAjusteConsumo(String indicadorAjusteConsumo){

		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
	}

	public String getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(String codigoRota){

		this.codigoRota = codigoRota;
	}

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 */
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		idLocalidade = null;
		localidadeDescricao = null;
		codigoSetorComercial = null;
		setorComercialDescricao = null;
		cobrancaGrupo = null;
		faturamentoGrupo = null;
		leituraTipo = null;
		empresaLeituristica = null;
		cobrancaCriterio = null;
		dataAjusteLeitura = null;
		indicadorAjusteConsumo = null;
		indicadorFiscalizarCortado = null;
		indicadorFiscalizarSuprimido = null;
		indicadorGerarFalsaFaixa = null;
		percentualGeracaoFaixaFalsa = null;
		indicadorGerarFiscalizacao = null;
		percentualGeracaoFiscalizacao = null;
		indicadorUso = ConstantesSistema.INDICADOR_USO_ATIVO.toString();
		ultimaAlteracao = null;
		codigoLeiturista = null;
		nomeLeiturista = null;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the idLeiturista
	 */
	public String getCodigoLeiturista(){

		return codigoLeiturista;
	}

	/**
	 * @param idLeiturista
	 *            the idLeiturista to set
	 */
	public void setCodigoLeiturista(String codigoLeiturista){

		this.codigoLeiturista = codigoLeiturista;
	}

	/**
	 * @return the nomeLeiturista
	 */
	public String getNomeLeiturista(){

		return nomeLeiturista;
	}

	/**
	 * @param nomeLeiturista
	 *            the nomeLeiturista to set
	 */
	public void setNomeLeiturista(String nomeLeiturista){

		this.nomeLeiturista = nomeLeiturista;
	}

}