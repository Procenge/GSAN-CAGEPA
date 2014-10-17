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

package gcom.micromedicao.bean;

/**
 * @author Raphael Rossiter
 * @date 03/03/2007
 */
public class ComparativoLeiturasEAnormalidadesRelatorioHelper {

	private Integer idLocalidade;

	private Integer idSetorComercial;

	private Integer codigoSetorComercial;

	private Integer idGrupoFaturamento;

	private Integer idEmpresa;

	private String nomeEmpresa;

	private Integer registrosRecebidos;

	private Integer registrosComLeitura;

	private Integer registrosComAnormalidade;

	private Integer registrosComLeituraEAnormalidade;

	public ComparativoLeiturasEAnormalidadesRelatorioHelper() {

	}

	/**
	 * @return Retorna o campo codigoSetorComercial.
	 */
	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	/**
	 * @param codigoSetorComercial
	 *            O codigoSetorComercial a ser setado.
	 */
	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	/**
	 * @return Retorna o campo idEmpresa.
	 */
	public Integer getIdEmpresa(){

		return idEmpresa;
	}

	/**
	 * @param idEmpresa
	 *            O idEmpresa a ser setado.
	 */
	public void setIdEmpresa(Integer idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return Retorna o campo idGrupoFaturamento.
	 */
	public Integer getIdGrupoFaturamento(){

		return idGrupoFaturamento;
	}

	/**
	 * @param idGrupoFaturamento
	 *            O idGrupoFaturamento a ser setado.
	 */
	public void setIdGrupoFaturamento(Integer idGrupoFaturamento){

		this.idGrupoFaturamento = idGrupoFaturamento;
	}

	/**
	 * @return Retorna o campo idLocalidade.
	 */
	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	/**
	 * @param idLocalidade
	 *            O idLocalidade a ser setado.
	 */
	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return Retorna o campo idSetorComercial.
	 */
	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	/**
	 * @param idSetorComercial
	 *            O idSetorComercial a ser setado.
	 */
	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	/**
	 * @return Retorna o campo nomeEmpresa.
	 */
	public String getNomeEmpresa(){

		return nomeEmpresa;
	}

	/**
	 * @param nomeEmpresa
	 *            O nomeEmpresa a ser setado.
	 */
	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	/**
	 * @return Retorna o campo registroComAnormalidade.
	 */
	public Integer getRegistrosComAnormalidade(){

		return registrosComAnormalidade;
	}

	/**
	 * @param registroComAnormalidade
	 *            O registroComAnormalidade a ser setado.
	 */
	public void setRegistrosComAnormalidade(Integer registrosComAnormalidade){

		this.registrosComAnormalidade = registrosComAnormalidade;
	}

	/**
	 * @return Retorna o campo registrosComLeitura.
	 */
	public Integer getRegistrosComLeitura(){

		return registrosComLeitura;
	}

	/**
	 * @param registrosComLeitura
	 *            O registrosComLeitura a ser setado.
	 */
	public void setRegistrosComLeitura(Integer registrosComLeitura){

		this.registrosComLeitura = registrosComLeitura;
	}

	/**
	 * @return Retorna o campo registrosComLeituraEAnormalidade.
	 */
	public Integer getRegistrosComLeituraEAnormalidade(){

		return registrosComLeituraEAnormalidade;
	}

	/**
	 * @param registrosComLeituraEAnormalidade
	 *            O registrosComLeituraEAnormalidade a ser setado.
	 */
	public void setRegistrosComLeituraEAnormalidade(Integer registrosComLeituraEAnormalidade){

		this.registrosComLeituraEAnormalidade = registrosComLeituraEAnormalidade;
	}

	/**
	 * @return Retorna o campo registrosRecebidos.
	 */
	public Integer getRegistrosRecebidos(){

		return registrosRecebidos;
	}

	/**
	 * @param registrosRecebidos
	 *            O registrosRecebidos a ser setado.
	 */
	public void setRegistrosRecebidos(Integer registrosRecebidos){

		this.registrosRecebidos = registrosRecebidos;
	}

}
