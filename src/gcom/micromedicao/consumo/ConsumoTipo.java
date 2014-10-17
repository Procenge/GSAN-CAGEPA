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

package gcom.micromedicao.consumo;

import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hibernate CodeGenerator
 */
public class ConsumoTipo
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigoConstante;

	// ## Atributos estão na super classe TabelaAuxiliarAbreviada

	// --CONSTANTES

	/** Constantes que são carregados os id´s a partir da base de dados. */
	public static Integer INDEFINIDO;// = Integer.valueOf(0);

	public static Integer REAL;// = Integer.valueOf(1);

	public static Integer CONSUMO_MEDIO_AJUSTADO;// = Integer.valueOf(2);

	public static Integer MEDIA_HIDROMETRO;// = Integer.valueOf(3);

	public static Integer INFORMADO;// = Integer.valueOf(4);

	public static Integer NAO_MEDIDO;// = Integer.valueOf(5);

	public static Integer ESTIMADO;// = Integer.valueOf(6);

	public static Integer CONSUMO_MINIMO_FIXADO;// = Integer.valueOf(7);

	public static Integer SEM_CONSUMO;// = Integer.valueOf(8);

	public static Integer MEDIA_IMOVEL;// = Integer.valueOf(9);

	public static Integer EXCEDENTE;// = Integer.valueOf(10);

	public static Integer MEDIA_INFORMADO;// = Integer.valueOf(11);

	public static Integer CONSUMO_FIXO;

	/**
	 * Default constructor.
	 */
	public ConsumoTipo() {
	}

	public ConsumoTipo(Integer id) {
		super();
		this.id = id;
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @return Descrição do retorno
	 */
	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}
	
	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 */
	public static void inicializarConstantes(){

		INDEFINIDO = ConsumoTipoEnum.INDEFINIDO.getId();
		REAL = ConsumoTipoEnum.REAL.getId();
		CONSUMO_MEDIO_AJUSTADO = ConsumoTipoEnum.CONSUMO_MEDIO_AJUSTADO.getId();
		MEDIA_HIDROMETRO = ConsumoTipoEnum.MEDIA_HIDROMETRO.getId();
		INFORMADO = ConsumoTipoEnum.INFORMADO.getId();
		NAO_MEDIDO = ConsumoTipoEnum.NAO_MEDIDO.getId();
		ESTIMADO = ConsumoTipoEnum.ESTIMADO.getId();
		CONSUMO_MINIMO_FIXADO = ConsumoTipoEnum.CONSUMO_MINIMO_FIXADO.getId();
		SEM_CONSUMO = ConsumoTipoEnum.SEM_CONSUMO.getId();
		MEDIA_IMOVEL = ConsumoTipoEnum.MEDIA_IMOVEL.getId();
		EXCEDENTE = ConsumoTipoEnum.EXCEDENTE.getId();
		MEDIA_INFORMADO = ConsumoTipoEnum.MEDIA_INFORMADO.getId();
		CONSUMO_FIXO = ConsumoTipoEnum.CONSUMO_FIXO.getId();
	}


	public static enum ConsumoTipoEnum {
		INDEFINIDO, NAO_MEDIDO, REAL, ESTIMADO, MEDIA_HIDROMETRO, SEM_CONSUMO, CONSUMO_MINIMO_FIXADO, CONSUMO_MEDIO_AJUSTADO, INFORMADO,
		MEDIA_IMOVEL, EXCEDENTE, MEDIA_INFORMADO, CONSUMO_FIXO;

		private Integer id = -1;

		private ConsumoTipoEnum() {

			try{
				ConsumoTipo consumoTipo = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(), ConsumoTipo.class);

				if(consumoTipo != null){

					id = consumoTipo.getId();

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
