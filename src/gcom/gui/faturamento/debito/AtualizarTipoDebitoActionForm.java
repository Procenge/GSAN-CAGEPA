/**
 * 
 */
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

package gcom.gui.faturamento.debito;

import gcom.cadastro.localidade.Localidade;
import gcom.faturamento.debito.DebitoTipoValorLocalidade;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 14/03/2007
 * @author eduardo henrique
 * @date 08/07/2008
 *       Inclus�o dos Atributos indicadorIncidenciaMulta, indicadorIncidenciaJurosMora
 */
public class AtualizarTipoDebitoActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String idTipoDebito;

	private String descricao;

	private String descricaoAbreviada;

	private String lancamentoItemContabil;

	private String financiamentoTipo;

	private String indicadorGeracaoDebitoAutomatica;

	private String indicadorGeracaoDebitoConta;

	private String indicadorUso;

	private String valorLimiteDebito;

	private String indicadorIncidenciaMulta;

	private String indicadorIncidenciaJurosMora;

	private String indicadorValorCalcular;

	private String valorPadrao;

	private String valorLocalidadeId;

	private String debitoTipoValorLocalidadeId;

	private String debitoTipoValorLocalidadeDescricao;

	private String debitoTipoValorLocalidadeValor;

	Collection<DebitoTipoValorLocalidade> debitoTipoValorLocalidade = new ArrayList<DebitoTipoValorLocalidade>();

	private String method;

	/**
	 * @return Retorna o campo descricao.
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao
	 *            O descricao a ser setado.
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return Retorna o campo descricaoAbreviada.
	 */
	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	/**
	 * @param descricaoAbreviada
	 *            O descricaoAbreviada a ser setado.
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	/**
	 * @return Retorna o campo financiamentoTipo.
	 */
	public String getFinanciamentoTipo(){

		return financiamentoTipo;
	}

	/**
	 * @param financiamentoTipo
	 *            O financiamentoTipo a ser setado.
	 */
	public void setFinanciamentoTipo(String financiamentoTipo){

		this.financiamentoTipo = financiamentoTipo;
	}

	/**
	 * @return Retorna o campo indicadorGeracaoDebitoAutomatica.
	 */
	public String getIndicadorGeracaoDebitoAutomatica(){

		return indicadorGeracaoDebitoAutomatica;
	}

	/**
	 * @param indicadorGeracaoDebitoAutomatica
	 *            O indicadorGeracaoDebitoAutomatica a ser setado.
	 */
	public void setIndicadorGeracaoDebitoAutomatica(String indicadorGeracaoDebitoAutomatica){

		this.indicadorGeracaoDebitoAutomatica = indicadorGeracaoDebitoAutomatica;
	}

	/**
	 * @return Retorna o campo indicadorGeracaoDebitoConta.
	 */
	public String getIndicadorGeracaoDebitoConta(){

		return indicadorGeracaoDebitoConta;
	}

	/**
	 * @param indicadorGeracaoDebitoConta
	 *            O indicadorGeracaoDebitoConta a ser setado.
	 */
	public void setIndicadorGeracaoDebitoConta(String indicadorGeracaoDebitoConta){

		this.indicadorGeracaoDebitoConta = indicadorGeracaoDebitoConta;
	}

	/**
	 * @return Retorna o campo lancamentoItemContabil.
	 */
	public String getLancamentoItemContabil(){

		return lancamentoItemContabil;
	}

	/**
	 * @param lancamentoItemContabil
	 *            O lancamentoItemContabil a ser setado.
	 */
	public void setLancamentoItemContabil(String lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	/**
	 * @return Retorna o campo valorLimiteDebito.
	 */
	public String getValorLimiteDebito(){

		return valorLimiteDebito;
	}

	/**
	 * @param valorLimiteDebito
	 *            O valorLimiteDebito a ser setado.
	 */
	public void setValorLimiteDebito(String valorLimiteDebito){

		this.valorLimiteDebito = valorLimiteDebito;
	}

	/**
	 * @return Retorna o campo codigo.
	 */
	public String getCodigo(){

		return codigo;
	}

	/**
	 * @param codigo
	 *            O codigo a ser setado.
	 */
	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	/**
	 * @return Retorna o campo indicadorUso.
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            O indicadorUso a ser setado.
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return Retorna o campo idTipoDebito.
	 */
	public String getIdTipoDebito(){

		return idTipoDebito;
	}

	/**
	 * @param idTipoDebito
	 *            O idTipoDebito a ser setado.
	 */
	public void setIdTipoDebito(String idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	public String getIndicadorIncidenciaMulta(){

		return indicadorIncidenciaMulta;
	}

	public void setIndicadorIncidenciaMulta(String indicadorIncidenciaMulta){

		this.indicadorIncidenciaMulta = indicadorIncidenciaMulta;
	}

	public String getIndicadorIncidenciaJurosMora(){

		return indicadorIncidenciaJurosMora;
	}

	public void setIndicadorIncidenciaJurosMora(String indicadorIncidenciaJurosMora){

		this.indicadorIncidenciaJurosMora = indicadorIncidenciaJurosMora;
	}

	public String getIndicadorValorCalcular(){

		return indicadorValorCalcular;
	}

	public void setIndicadorValorCalcular(String indicadorValorCalcular){

		this.indicadorValorCalcular = indicadorValorCalcular;
	}

	public String getValorPadrao(){

		return valorPadrao;
	}

	public void setValorPadrao(String valorPadrao){

		this.valorPadrao = valorPadrao;
	}

	public String getValorLocalidadeId(){

		return valorLocalidadeId;
	}

	public void setValorLocalidadeId(String valorLocalidadeId){

		this.valorLocalidadeId = valorLocalidadeId;
	}

	public String getDebitoTipoValorLocalidadeId(){

		return debitoTipoValorLocalidadeId;
	}

	public void setDebitoTipoValorLocalidadeId(String debitoTipoValorLocalidadeId){

		this.debitoTipoValorLocalidadeId = debitoTipoValorLocalidadeId;
	}

	public String getDebitoTipoValorLocalidadeDescricao(){

		return debitoTipoValorLocalidadeDescricao;
	}

	public void setDebitoTipoValorLocalidadeDescricao(String debitoTipoValorLocalidadeDescricao){

		this.debitoTipoValorLocalidadeDescricao = debitoTipoValorLocalidadeDescricao;
	}

	public String getDebitoTipoValorLocalidadeValor(){

		return debitoTipoValorLocalidadeValor;
	}

	public void setDebitoTipoValorLocalidadeValor(String debitoTipoValorLocalidadeValor){

		this.debitoTipoValorLocalidadeValor = debitoTipoValorLocalidadeValor;
	}

	public Collection<DebitoTipoValorLocalidade> getDebitoTipoValorLocalidade(){

		return debitoTipoValorLocalidade;
	}

	public void setDebitoTipoValorLocalidade(Collection<DebitoTipoValorLocalidade> debitoTipoValorLocalidade){

		this.debitoTipoValorLocalidade = debitoTipoValorLocalidade;
	}

	public String getMethod(){

		return method;
	}

	public void setMethod(String method){

		this.method = method;
	}

	public void addDebitoTipoValorLocalidade(){

		Localidade loca = new Localidade();
		DebitoTipoValorLocalidade dtvl = new DebitoTipoValorLocalidade();

		loca.setId(Util.converterStringParaInteger(getValorLocalidadeId()));
		loca.setDescricao(getDebitoTipoValorLocalidadeDescricao());
		dtvl.setValorDebitoTipo(Util.formatarStringParaBigDecimal(getDebitoTipoValorLocalidadeValor(), 2, true));
		dtvl.setLocalidade(loca);
		dtvl.setIndicadorUso(ConstantesSistema.SIM);
		dtvl.setUltimaAlteracao(new Date());

		debitoTipoValorLocalidade.add(dtvl);
	}

	public void removeDebitoTipoValorLocalidade(){

		for(Iterator iter = getDebitoTipoValorLocalidade().iterator(); iter.hasNext();){
			DebitoTipoValorLocalidade dtvl = (DebitoTipoValorLocalidade) iter.next();
			String string = dtvl.getLocalidade().getId().toString();
			if(string.equals(getValorLocalidadeId())){
				iter.remove();
			}
		}
	}
}
