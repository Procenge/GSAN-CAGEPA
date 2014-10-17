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

package gcom.gui.cadastro.localidade;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AtualizarLocalidadeActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String classeID;

	private String eloID;

	private String email;

	private String fax;

	// private String gerenciaID;

	private String idUnidadeNegocio;

	private String indicadorUso;

	private String localidadeID;

	private String localidadeNome;

	private String menorConsumo;

	private String porteID;

	private String ramal;

	private String telefone;

	private String icms;

	private String centroCusto;

	private String informatizada;

	private String gerenteLocalidade;

	private String nomeGerente;

	private String gerenciaRegionalID;

	private String municipioID;

	private String localidadeCEF;

	private boolean indicadorAbastecimentoSuspenso;

	private boolean indicadorAbastecimentoMinimo;

	private String ultimaQuadra;

	private String localidadeContabil;

	private String[] localidadeSelectID;

	private Integer concessionariaId;

	private Integer concessionariaIdOriginal;

	private String dataVigenciaInicioConcessionaria;

	private String dataVigenciaInicioConcessionariaOriginal;

	public String getClasseID(){

		return classeID;
	}

	public void setClasseID(String classeID){

		this.classeID = classeID;
	}

	public String getEloID(){

		return eloID;
	}

	public void setEloID(String eloID){

		this.eloID = eloID;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public String getFax(){

		return fax;
	}

	public void setFax(String fax){

		this.fax = fax;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getLocalidadeID(){

		return localidadeID;
	}

	public void setLocalidadeID(String localidadeID){

		this.localidadeID = localidadeID;
	}

	public String getLocalidadeNome(){

		return localidadeNome;
	}

	public void setLocalidadeNome(String localidadeNome){

		this.localidadeNome = localidadeNome;
	}

	public String getMenorConsumo(){

		return menorConsumo;
	}

	public void setMenorConsumo(String menorConsumo){

		this.menorConsumo = menorConsumo;
	}

	public String getPorteID(){

		return porteID;
	}

	public void setPorteID(String porteID){

		this.porteID = porteID;
	}

	public String getRamal(){

		return ramal;
	}

	public void setRamal(String ramal){

		this.ramal = ramal;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		/** @todo: finish this method, this is just the skeleton. */
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}

	public String[] getLocalidadeSelectID(){

		return localidadeSelectID;
	}

	public void setLocalidadeSelectID(String[] localidadeSelectID){

		this.localidadeSelectID = localidadeSelectID;
	}

	/**
	 * @return Retorna o campo centroCusto.
	 */
	public String getCentroCusto(){

		return centroCusto;
	}

	/**
	 * @param centroCusto
	 *            O centroCusto a ser setado.
	 */
	public void setCentroCusto(String centroCusto){

		this.centroCusto = centroCusto;
	}

	/**
	 * @return Retorna o campo icms.
	 */
	public String getIcms(){

		return icms;
	}

	/**
	 * @param icms
	 *            O icms a ser setado.
	 */
	public void setIcms(String icms){

		this.icms = icms;
	}

	/**
	 * @return Retorna o campo idUnidadeNegocio.
	 */
	public String getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	/**
	 * @param idUnidadeNegocio
	 *            O idUnidadeNegocio a ser setado.
	 */
	public void setIdUnidadeNegocio(String idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public String getGerenteLocalidade(){

		return gerenteLocalidade;
	}

	public void setGerenteLocalidade(String gerenteLocalidade){

		this.gerenteLocalidade = gerenteLocalidade;
	}

	public String getInformatizada(){

		return informatizada;
	}

	public void setInformatizada(String informatizada){

		this.informatizada = informatizada;
	}

	public String getNomeGerente(){

		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente){

		this.nomeGerente = nomeGerente;
	}

	/**
	 * @return the gerenciaRegionalID
	 */
	public String getGerenciaRegionalID(){

		return gerenciaRegionalID;
	}

	/**
	 * @param gerenciaRegionalID
	 *            the gerenciaRegionalID to set
	 */
	public void setGerenciaRegionalID(String gerenciaRegionalID){

		this.gerenciaRegionalID = gerenciaRegionalID;
	}

	/**
	 * @return the municipioID
	 */
	public String getMunicipioID(){

		return municipioID;
	}

	/**
	 * @param municipioID
	 *            the municipioID to set
	 */
	public void setMunicipioID(String municipioID){

		this.municipioID = municipioID;
	}

	/**
	 * @return the localidadeCEF
	 */
	public String getLocalidadeCEF(){

		return localidadeCEF;
	}

	/**
	 * @param localidadeCEF
	 *            the localidadeCEF to set
	 */
	public void setLocalidadeCEF(String localidadeCEF){

		this.localidadeCEF = localidadeCEF;
	}

	/**
	 * @return the indicadorAbastecimentoSuspenso
	 */
	public boolean isIndicadorAbastecimentoSuspenso(){

		return indicadorAbastecimentoSuspenso;
	}

	/**
	 * @param indicadorAbastecimentoSuspenso
	 *            the indicadorAbastecimentoSuspenso to set
	 */
	public void setIndicadorAbastecimentoSuspenso(boolean indicadorAbastecimentoSuspenso){

		this.indicadorAbastecimentoSuspenso = indicadorAbastecimentoSuspenso;
	}

	/**
	 * @return the indicadorAbastecimentoMinimo
	 */
	public boolean isIndicadorAbastecimentoMinimo(){

		return indicadorAbastecimentoMinimo;
	}

	/**
	 * @param indicadorAbastecimentoMinimo
	 *            the indicadorAbastecimentoMinimo to set
	 */
	public void setIndicadorAbastecimentoMinimo(boolean indicadorAbastecimentoMinimo){

		this.indicadorAbastecimentoMinimo = indicadorAbastecimentoMinimo;
	}

	/**
	 * @return the ultimaQuadra
	 */
	public String getUltimaQuadra(){

		return ultimaQuadra;
	}

	/**
	 * @param ultimaQuadra
	 *            the ultimaQuadra to set
	 */
	public void setUltimaQuadra(String ultimaQuadra){

		this.ultimaQuadra = ultimaQuadra;
	}

	/**
	 * @return the localidadeContabil
	 */
	public String getLocalidadeContabil(){

		return localidadeContabil;
	}

	/**
	 * @param localidadeContabil
	 *            the localidadeContabil to set
	 */
	public void setLocalidadeContabil(String localidadeContabil){

		this.localidadeContabil = localidadeContabil;
	}

	public Integer getConcessionariaId(){

		return concessionariaId;
	}

	public void setConcessionariaId(Integer concessionariaId){

		this.concessionariaId = concessionariaId;
	}

	public Integer getConcessionariaIdOriginal(){

		return concessionariaIdOriginal;
	}

	public void setConcessionariaIdOriginal(Integer concessionariaIdOriginal){

		this.concessionariaIdOriginal = concessionariaIdOriginal;
	}

	public String getDataVigenciaInicioConcessionaria(){

		return dataVigenciaInicioConcessionaria;
	}

	public void setDataVigenciaInicioConcessionaria(String dataVigenciaInicioConcessionaria){

		this.dataVigenciaInicioConcessionaria = dataVigenciaInicioConcessionaria;
	}

	public String getDataVigenciaInicioConcessionariaOriginal(){

		return dataVigenciaInicioConcessionariaOriginal;
	}

	public void setDataVigenciaInicioConcessionariaOriginal(String dataVigenciaInicioConcessionariaOriginal){

		this.dataVigenciaInicioConcessionariaOriginal = dataVigenciaInicioConcessionariaOriginal;
	}

}
