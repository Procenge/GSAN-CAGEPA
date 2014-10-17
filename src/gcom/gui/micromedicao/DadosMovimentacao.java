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

package gcom.gui.micromedicao;

import java.util.Date;

/**
 * @author Thiago Nascimento
 */
public class DadosMovimentacao {

	private Integer imovel;

	private Integer leituraHidrometro;

	private Integer anormalidade;

	private Date data;

	private Long imei;

	private Byte indicador;

	public DadosMovimentacao(Integer imovel, Integer leitura, Integer anormalidade, Date data, Long imei, Byte indicador) {

		this.anormalidade = anormalidade;
		this.data = data;
		this.leituraHidrometro = leitura;
		this.imovel = imovel;
		this.imei = imei;
		this.indicador = indicador;
	}

	/**
	 * @return Returns the anormalidade.
	 */
	public Integer getAnormalidade(){

		return anormalidade;
	}

	/**
	 * @param anormalidade
	 *            The anormalidade to set.
	 */
	public void setAnormalidade(Integer anormalidade){

		this.anormalidade = anormalidade;
	}

	/**
	 * @return Returns the data.
	 */
	public Date getData(){

		return data;
	}

	/**
	 * @param data
	 *            The data to set.
	 */
	public void setData(Date data){

		this.data = data;
	}

	/**
	 * @return Returns the imovel.
	 */
	public Integer getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            The imovel to set.
	 */
	public void setImovel(Integer imovel){

		this.imovel = imovel;
	}

	/**
	 * @return Returns the leituraHridometro.
	 */
	public Integer getLeituraHidrometro(){

		return leituraHidrometro;
	}

	/**
	 * @param leituraHridometro
	 *            The leituraHridometro to set.
	 */
	public void setLeituraHidrometro(Integer leituraHridometro){

		this.leituraHidrometro = leituraHridometro;
	}

	/**
	 * @return Returns the imei.
	 */
	public Long getImei(){

		return imei;
	}

	/**
	 * @param imei
	 *            The imei to set.
	 */
	public void setImei(Long imei){

		this.imei = imei;
	}

	/**
	 * @return Returns the indicador.
	 */
	public Byte getIndicador(){

		return indicador;
	}

	/**
	 * @param indicador
	 *            The indicador to set.
	 */
	public void setIndicador(Byte indicador){

		this.indicador = indicador;
	}

}
