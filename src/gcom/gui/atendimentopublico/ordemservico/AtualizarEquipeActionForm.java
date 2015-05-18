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

import org.apache.struts.action.ActionForm;

public class AtualizarEquipeActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idEquipe;

	private String nomeEquipe;

	private String placaVeiculo;

	private String cargaTrabalhoDia;

	private String idUnidade;

	private String nomeUnidade;

	private String idServicoPerfilTipo;

	private String descricaoServicoPerfilTipo;

	private String qtdeComponentesEquipe;

	private String indicadorUso;

	private String tamanhoColecao;

	private String indicadorResponsavel;

	private String idEquipeComponente;

	private String idFuncionario;

	private String nomeFuncionario;

	private String nomeComponente;

	private Integer idEquipeTipo;

	/**
	 * @return Retorna o campo qtdeComponentesEquipe.
	 */
	public String getQtdeComponentesEquipe(){

		return qtdeComponentesEquipe;
	}

	/**
	 * @param qtdeComponentesEquipe
	 *            O qtdeComponentesEquipe a ser setado.
	 */
	public void setQtdeComponentesEquipe(String qtdeComponentesEquipe){

		this.qtdeComponentesEquipe = qtdeComponentesEquipe;
	}

	/**
	 * @return Retorna o campo cargaTrabalhoDia.
	 */
	public String getCargaTrabalhoDia(){

		return cargaTrabalhoDia;
	}

	/**
	 * @param cargaTrabalhoDia
	 *            O cargaTrabalhoDia a ser setado.
	 */
	public void setCargaTrabalhoDia(String cargaTrabalhoDia){

		this.cargaTrabalhoDia = cargaTrabalhoDia;
	}

	/**
	 * @return Retorna o campo descricaoServicoPerfilTipo.
	 */
	public String getDescricaoServicoPerfilTipo(){

		return descricaoServicoPerfilTipo;
	}

	/**
	 * @param descricaoServicoPerfilTipo
	 *            O descricaoServicoPerfilTipo a ser setado.
	 */
	public void setDescricaoServicoPerfilTipo(String descricaoServicoPerfilTipo){

		this.descricaoServicoPerfilTipo = descricaoServicoPerfilTipo;
	}

	/**
	 * @return Retorna o campo idEquipe.
	 */
	public String getIdEquipe(){

		return idEquipe;
	}

	/**
	 * @param idEquipe
	 *            O idEquipe a ser setado.
	 */
	public void setIdEquipe(String idEquipe){

		this.idEquipe = idEquipe;
	}

	/**
	 * @return Retorna o campo idServicoPerfilTipo.
	 */
	public String getIdServicoPerfilTipo(){

		return idServicoPerfilTipo;
	}

	/**
	 * @param idServicoPerfilTipo
	 *            O idServicoPerfilTipo a ser setado.
	 */
	public void setIdServicoPerfilTipo(String idServicoPerfilTipo){

		this.idServicoPerfilTipo = idServicoPerfilTipo;
	}

	/**
	 * @return Retorna o campo idUnidade.
	 */
	public String getIdUnidade(){

		return idUnidade;
	}

	/**
	 * @param idUnidade
	 *            O idUnidade a ser setado.
	 */
	public void setIdUnidade(String idUnidade){

		this.idUnidade = idUnidade;
	}

	/**
	 * @return Retorna o campo nomeEquipe.
	 */
	public String getNomeEquipe(){

		return nomeEquipe;
	}

	/**
	 * @param nomeEquipe
	 *            O nomeEquipe a ser setado.
	 */
	public void setNomeEquipe(String nomeEquipe){

		this.nomeEquipe = nomeEquipe;
	}

	/**
	 * @return Retorna o campo nomeUnidade.
	 */
	public String getNomeUnidade(){

		return nomeUnidade;
	}

	/**
	 * @param nomeUnidade
	 *            O nomeUnidade a ser setado.
	 */
	public void setNomeUnidade(String nomeUnidade){

		this.nomeUnidade = nomeUnidade;
	}

	/**
	 * @return Retorna o campo placaVeiculo.
	 */
	public String getPlacaVeiculo(){

		return placaVeiculo;
	}

	/**
	 * @param placaVeiculo
	 *            O placaVeiculo a ser setado.
	 */
	public void setPlacaVeiculo(String placaVeiculo){

		this.placaVeiculo = placaVeiculo;
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
	 * @return Retorna o campo idFuncionario.
	 */
	public String getIdFuncionario(){

		return idFuncionario;
	}

	/**
	 * @param idFuncionario
	 *            O idFuncionario a ser setado.
	 */
	public void setIdFuncionario(String idFuncionario){

		this.idFuncionario = idFuncionario;
	}

	/**
	 * @return Retorna o campo indicadorResponsavel.
	 */
	public String getIndicadorResponsavel(){

		return indicadorResponsavel;
	}

	/**
	 * @param indicadorResponsavel
	 *            O indicadorResponsavel a ser setado.
	 */
	public void setIndicadorResponsavel(String indicadorResponsavel){

		this.indicadorResponsavel = indicadorResponsavel;
	}

	/**
	 * @return Retorna o campo nomeComponente.
	 */
	public String getNomeComponente(){

		return nomeComponente;
	}

	/**
	 * @param nomeComponente
	 *            O nomeComponente a ser setado.
	 */
	public void setNomeComponente(String nomeComponente){

		this.nomeComponente = nomeComponente;
	}

	/**
	 * @return Retorna o campo nomeFuncionario.
	 */
	public String getNomeFuncionario(){

		return nomeFuncionario;
	}

	/**
	 * @param nomeFuncionario
	 *            O nomeFuncionario a ser setado.
	 */
	public void setNomeFuncionario(String nomeFuncionario){

		this.nomeFuncionario = nomeFuncionario;
	}

	/**
	 * @return Retorna o campo tamanhoColecao.
	 */
	public String getTamanhoColecao(){

		return tamanhoColecao;
	}

	/**
	 * @param tamanhoColecao
	 *            O tamanhoColecao a ser setado.
	 */
	public void setTamanhoColecao(String tamanhoColecao){

		this.tamanhoColecao = tamanhoColecao;
	}

	/**
	 * @return Retorna o campo idEquipeComponente.
	 */
	public String getIdEquipeComponente(){

		return idEquipeComponente;
	}

	/**
	 * @param idEquipeComponente
	 *            O idEquipeComponente a ser setado.
	 */
	public void setIdEquipeComponente(String idEquipeComponente){

		this.idEquipeComponente = idEquipeComponente;
	}

	public Integer getIdEquipeTipo(){

		return idEquipeTipo;
	}

	public void setIdEquipeTipo(Integer idEquipeTipo){

		this.idEquipeTipo = idEquipeTipo;
	}

}