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

package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UCXXXX] INSERIR TRAMITE POR ESPECIFICACAO
 * 
 * @author Ailton Sousa
 * @date 28/03/2011
 */
public class InserirTramiteEspecificacaoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String tipoSolicitacao;

	private String solicitacaoTipoEspecificacao;

	private String localidade;

	private String nomeLocalidade;

	private String codigoSetorComercial;

	private String idSetorComercial;

	private String nomeSetorComercial;

	private String municipio;

	private String nomeMunicipio;

	private String codigoBairro;

	private String idBairro;

	private String nomeBairro;

	private String sistemaAbastecimento;

	private String distritoOperacional;

	private String zonaAbastecimento;

	private String setorAbastecimento;

	private String sistemaEsgoto;

	private String subsistemaEsgoto;

	private String bacia;

	private String subBacia;

	private String unidadeOrganizacionalOrigem;

	private String nomeUnidadeOrganizacionalOrigem;

	private String unidadeOrganizacionalDestino;

	private String nomeUnidadeOrganizacionalDestino;

	public String getTipoSolicitacao(){

		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String tipoSolicitacao){

		this.tipoSolicitacao = tipoSolicitacao;
	}

	public String getSolicitacaoTipoEspecificacao(){

		return solicitacaoTipoEspecificacao;
	}

	public void setSolicitacaoTipoEspecificacao(String solicitacaoTipoEspecificacao){

		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(String idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public String getNomeSetorComercial(){

		return nomeSetorComercial;
	}

	public void setNomeSetorComercial(String nomeSetorComercial){

		this.nomeSetorComercial = nomeSetorComercial;
	}

	public String getMunicipio(){

		return municipio;
	}

	public void setMunicipio(String municipio){

		this.municipio = municipio;
	}

	public String getNomeMunicipio(){

		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio){

		this.nomeMunicipio = nomeMunicipio;
	}

	public String getCodigoBairro(){

		return codigoBairro;
	}

	public void setCodigoBairro(String codigoBairro){

		this.codigoBairro = codigoBairro;
	}

	public String getIdBairro(){

		return idBairro;
	}

	public void setIdBairro(String idBairro){

		this.idBairro = idBairro;
	}

	public String getNomeBairro(){

		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro){

		this.nomeBairro = nomeBairro;
	}

	public String getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(String sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public String getDistritoOperacional(){

		return distritoOperacional;
	}

	public void setDistritoOperacional(String distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public String getZonaAbastecimento(){

		return zonaAbastecimento;
	}

	public void setZonaAbastecimento(String zonaAbastecimento){

		this.zonaAbastecimento = zonaAbastecimento;
	}

	public String getSetorAbastecimento(){

		return setorAbastecimento;
	}

	public void setSetorAbastecimento(String setorAbastecimento){

		this.setorAbastecimento = setorAbastecimento;
	}

	public String getSistemaEsgoto(){

		return sistemaEsgoto;
	}

	public void setSistemaEsgoto(String sistemaEsgoto){

		this.sistemaEsgoto = sistemaEsgoto;
	}

	public String getSubsistemaEsgoto(){

		return subsistemaEsgoto;
	}

	public void setSubsistemaEsgoto(String subsistemaEsgoto){

		this.subsistemaEsgoto = subsistemaEsgoto;
	}

	public String getBacia(){

		return bacia;
	}

	public void setBacia(String bacia){

		this.bacia = bacia;
	}

	public String getSubBacia(){

		return subBacia;
	}

	public void setSubBacia(String subBacia){

		this.subBacia = subBacia;
	}

	public String getUnidadeOrganizacionalOrigem(){

		return unidadeOrganizacionalOrigem;
	}

	public void setUnidadeOrganizacionalOrigem(String unidadeOrganizacionalOrigem){

		this.unidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem;
	}

	public String getUnidadeOrganizacionalDestino(){

		return unidadeOrganizacionalDestino;
	}

	public void setUnidadeOrganizacionalDestino(String unidadeOrganizacionalDestino){

		this.unidadeOrganizacionalDestino = unidadeOrganizacionalDestino;
	}

	public String getNomeUnidadeOrganizacionalOrigem(){

		return nomeUnidadeOrganizacionalOrigem;
	}

	public void setNomeUnidadeOrganizacionalOrigem(String nomeUnidadeOrganizacionalOrigem){

		this.nomeUnidadeOrganizacionalOrigem = nomeUnidadeOrganizacionalOrigem;
	}

	public String getNomeUnidadeOrganizacionalDestino(){

		return nomeUnidadeOrganizacionalDestino;
	}

	public void setNomeUnidadeOrganizacionalDestino(String nomeUnidadeOrganizacionalDestino){

		this.nomeUnidadeOrganizacionalDestino = nomeUnidadeOrganizacionalDestino;
	}
}
