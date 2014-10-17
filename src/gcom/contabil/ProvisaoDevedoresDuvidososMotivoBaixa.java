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

package gcom.contabil;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;

import java.util.Date;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author Diogo Monteiro
 * @created 08 de Junho de 2012
 * @version 1.0
 */
public class ProvisaoDevedoresDuvidososMotivoBaixa
				extends ObjetoTransacao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3531961234356691933L;

	private int id;

	private String descricaoMotivoBaixa;

	private String codigoConstante;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	// Constantes

	public static Integer RECEBIMENTO;

	public static Integer ALTERACAO_VENCIMENTO;

	public static Integer CANCELAMENTO;

	public static Integer PARCELAMENTO;

	public ProvisaoDevedoresDuvidososMotivoBaixa() {

	}

	public ProvisaoDevedoresDuvidososMotivoBaixa(Integer id) {

		this.id = id;
	}

	@Override
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;

	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

	public void setId(int id){

		this.id = id;
	}

	public int getId(){

		return id;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @return the descricaoMotivoBaixa
	 */
	public String getDescricaoMotivoBaixa(){

		return descricaoMotivoBaixa;
	}

	/**
	 * @param descricaoMotivoBaixa
	 *            the descricaoMotivoBaixa to set
	 */
	public void setDescricaoMotivoBaixa(String descricaoMotivoBaixa){

		this.descricaoMotivoBaixa = descricaoMotivoBaixa;
	}

	/**
	 * @return the codigoConstante
	 */
	public String getCodigoConstante(){

		return codigoConstante;
	}

	/**
	 * @param codigoConstante
	 *            the codigoConstante to set
	 */
	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	/**
	 * Método criado para inicializar as constantes.
	 * 
	 * @author Hugo Lima
	 * @date 26/06/2012
	 */
	public static void inicializarConstantes(){

		RECEBIMENTO = ProvisaoDevedoresDuvidososMotivoBaixaEnum.RECEBIMENTO.getId();
		ALTERACAO_VENCIMENTO = ProvisaoDevedoresDuvidososMotivoBaixaEnum.ALTERACAO_VENCIMENTO.getId();
		CANCELAMENTO = ProvisaoDevedoresDuvidososMotivoBaixaEnum.CANCELAMENTO.getId();
		PARCELAMENTO = ProvisaoDevedoresDuvidososMotivoBaixaEnum.PARCELAMENTO.getId();
	}

	/**
	 * Enumeração criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução.Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Hugo Lima
	 * @date 26/06/2012
	 */
	public static enum ProvisaoDevedoresDuvidososMotivoBaixaEnum {
		RECEBIMENTO, ALTERACAO_VENCIMENTO, CANCELAMENTO, PARCELAMENTO;

		private Integer id = -1;

		private ProvisaoDevedoresDuvidososMotivoBaixaEnum() {

			try{
				ProvisaoDevedoresDuvidososMotivoBaixa provisaoDevedoresDuvidososMotivoBaixa = RepositorioUtilHBM.getInstancia()
								.pesquisarPorCodigo(name(), ProvisaoDevedoresDuvidososMotivoBaixa.class);

				if(provisaoDevedoresDuvidososMotivoBaixa != null){
					id = provisaoDevedoresDuvidososMotivoBaixa.getId();
				}
			}catch(ErroRepositorioException e){
				e.printStackTrace();
				throw new SistemaException(e, e.getMessage());
			}
		}

		public Integer getId(){

			return id;
		}
	}

}
