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

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class TramitarOrdemServicoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String numeroOS;

	// Dados da Tramita��o
	private String unidadeDestinoId;

	private String unidadeDestinoDescricao;

	private String unidadeDestinoIndicadorTramite;

	private String unidadeDestinoCodigoTipo;

	private String usuarioResponsavelId;

	private String usuarioResponsavelNome;

	private String dataTramite;

	private String horaTramite;

	private String parecerTramite;

	// Login do Usu�rio
	private String usuarioRegistroId;

	private String usuarioRegistroNome;

	private String usuarioRegistroUnidade;

	private String usuarioRegistroUnidadeIndicadorTarifaSocial;

	// Controle
	private String validaUnidadeDestino = "false";

	private String validaUsuarioResponsavel = "false";

	private String resetarTramitacao = "false";

	private Date dataConcorrenciaOS;

	public void resetarTramitacao(){

		this.setUnidadeDestinoId(null);
		this.setUnidadeDestinoDescricao(null);
		this.setUnidadeDestinoIndicadorTramite(null);
		this.setUnidadeDestinoCodigoTipo(null);
		this.setUsuarioResponsavelId(null);
		this.setUsuarioResponsavelNome(null);
		this.setDataTramite(null);
		this.setHoraTramite(null);
		this.setParecerTramite(null);
		this.setValidaUnidadeDestino("false");
		this.setValidaUsuarioResponsavel("false");
		this.setResetarTramitacao("false");
	}

	public String getResetarTramitacao(){

		return resetarTramitacao;
	}

	public void setResetarTramitacao(String resetarTramitacao){

		this.resetarTramitacao = resetarTramitacao;
	}

	public String getDataTramite(){

		return dataTramite;
	}

	public void setDataTramite(String dataTramite){

		this.dataTramite = dataTramite;
	}

	public String getHoraTramite(){

		return horaTramite;
	}

	public void setHoraTramite(String horaTramite){

		this.horaTramite = horaTramite;
	}

	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		this.numeroOS = numeroOS;
	}

	public String getParecerTramite(){

		return parecerTramite;
	}

	public void setParecerTramite(String parecerTramite){

		this.parecerTramite = parecerTramite;
	}

	public String getUnidadeDestinoDescricao(){

		return unidadeDestinoDescricao;
	}

	public void setUnidadeDestinoDescricao(String unidadeDestinoDescricao){

		this.unidadeDestinoDescricao = unidadeDestinoDescricao;
	}

	public String getUnidadeDestinoId(){

		return unidadeDestinoId;
	}

	public void setUnidadeDestinoId(String unidadeDestinoId){

		this.unidadeDestinoId = unidadeDestinoId;
	}

	public String getUsuarioResponsavelId(){

		return usuarioResponsavelId;
	}

	public void setUsuarioResponsavelId(String usuarioResponsavelId){

		this.usuarioResponsavelId = usuarioResponsavelId;
	}

	public String getUsuarioResponsavelNome(){

		return usuarioResponsavelNome;
	}

	public void setUsuarioResponsavelNome(String usuarioResponsavelNome){

		this.usuarioResponsavelNome = usuarioResponsavelNome;
	}

	public String getValidaUnidadeDestino(){

		return validaUnidadeDestino;
	}

	public void setValidaUnidadeDestino(String validaUnidadeDestino){

		this.validaUnidadeDestino = validaUnidadeDestino;
	}

	public String getValidaUsuarioResponsavel(){

		return validaUsuarioResponsavel;
	}

	public void setValidaUsuarioResponsavel(String validaUsuarioResponsavel){

		this.validaUsuarioResponsavel = validaUsuarioResponsavel;
	}

	public String getUsuarioRegistroId(){

		return usuarioRegistroId;
	}

	public void setUsuarioRegistroId(String usuarioRegistroId){

		this.usuarioRegistroId = usuarioRegistroId;
	}

	public String getUsuarioRegistroNome(){

		return usuarioRegistroNome;
	}

	public void setUsuarioRegistroNome(String usuarioRegistroNome){

		this.usuarioRegistroNome = usuarioRegistroNome;
	}

	public String getUsuarioRegistroUnidadeIndicadorTarifaSocial(){

		return usuarioRegistroUnidadeIndicadorTarifaSocial;
	}

	public void setUsuarioRegistroUnidadeIndicadorTarifaSocial(String usuarioRegistroUnidadeIndicadorTarifaSocial){

		this.usuarioRegistroUnidadeIndicadorTarifaSocial = usuarioRegistroUnidadeIndicadorTarifaSocial;
	}

	public String getUsuarioRegistroUnidade(){

		return usuarioRegistroUnidade;
	}

	public void setUsuarioRegistroUnidade(String usuarioRegistroUnidade){

		this.usuarioRegistroUnidade = usuarioRegistroUnidade;
	}

	public String getUnidadeDestinoIndicadorTramite(){

		return unidadeDestinoIndicadorTramite;
	}

	public void setUnidadeDestinoIndicadorTramite(String unidadeDestinoIndicadorTramite){

		this.unidadeDestinoIndicadorTramite = unidadeDestinoIndicadorTramite;
	}

	public String getUnidadeDestinoCodigoTipo(){

		return unidadeDestinoCodigoTipo;
	}

	public void setUnidadeDestinoCodigoTipo(String unidadeDestinoCodigoTipo){

		this.unidadeDestinoCodigoTipo = unidadeDestinoCodigoTipo;
	}

	public Date getDataConcorrenciaOS(){

		return dataConcorrenciaOS;
	}

	public void setDataConcorrenciaOS(Date dataConcorrenciaOS){

		this.dataConcorrenciaOS = dataConcorrenciaOS;
	}

}