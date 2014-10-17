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

package gcom.faturamento;

import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author isilva
 */
@ControleAlteracao
public class SetorComercialVencimento
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_INSERIR = 43457;

	public static final int ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR = 43975;

	public static final int ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_REMOVER = 44493;

	private static final long serialVersionUID = 1L;

	private Integer id;

	@ControleAlteracao(value = FiltroSetorComercialVencimento.LOCALIDADE, funcionalidade = {ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_INSERIR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_REMOVER})
	private Localidade localidade;

	@ControleAlteracao(value = FiltroSetorComercialVencimento.SETOR_COMERCIAL, funcionalidade = {ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_INSERIR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_REMOVER})
	private SetorComercial setorComercial;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_INSERIR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_REMOVER})
	private Short diaVencimento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_INSERIR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR, ATRIBUTOS_SETOR_COMERCIAL_VENCIMENTO_REMOVER})
	private Short indicadorUso;

	private Date ultimaAlteracao;

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the localidade
	 */
	public Localidade getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade
	 *            the localidade to set
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * @return the setorComercial
	 */
	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	/**
	 * @param setorComercial
	 *            the setorComercial to set
	 */
	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	/**
	 * @return the diaVencimento
	 */
	public Short getDiaVencimento(){

		return diaVencimento;
	}

	/**
	 * @param diaVencimento
	 *            the diaVencimento to set
	 */
	public void setDiaVencimento(Short diaVencimento){

		this.diaVencimento = diaVencimento;
	}

	/**
	 * @return the indicadorUso
	 */
	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "diaVencimento", "localidade.id", "setorComercial.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"ID", "Dia Vencimento", "Localidade", "Setor Comercial"};
		return labels;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroSetorComercialVencimento filtroSetorComercialVencimento = new FiltroSetorComercialVencimento();

		filtroSetorComercialVencimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorComercialVencimento.LOCALIDADE);
		filtroSetorComercialVencimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorComercialVencimento.SETOR_COMERCIAL);
		filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.ID, this.getId()));
		return filtroSetorComercialVencimento;
	}

	// m�todo para exibir a descricao de um Setor comercial no registrar transacao
	public String getDescricaoParaRegistroTransacao(){

		return getLocalidade().getDescricaoComId() + " - " + getDiaVencimento();
	}

	@Override
	public void initializeLazy(){

		retornaCamposChavePrimaria();
		if(getLocalidade() != null){
			getLocalidade().getDescricao();
		}
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		SetorComercialVencimento other = (SetorComercialVencimento) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}
}
