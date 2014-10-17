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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.campanhapremiacao.Campanha;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.CampanhaCadastroFone;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Hiroshi Goncalves
 * @date 09/09/2013
 */
public class EfetuarInscricaoCampanhaPremiacaoAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		EfetuarInscricaoCampanhaPremiacaoActionForm form = (EfetuarInscricaoCampanhaPremiacaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Collection colecaoClienteFone = new ArrayList();

		if(sessao.getAttribute("colecaoClienteFone") != null){
			colecaoClienteFone = (Collection) sessao.getAttribute("colecaoClienteFone");
		}

		Campanha campanha = (Campanha) sessao.getAttribute("campanha");
		Short indicadorResidencial = (Short) sessao.getAttribute("indicadorResidencial");

		CampanhaCadastro campanhaCadastro = new CampanhaCadastro();

		campanhaCadastro.setCampanha(campanha);

		campanhaCadastro.setImovel(new Imovel(Integer.valueOf(form.getIdImovel())));

		if(form.getTipoRelacao() != null){
			campanhaCadastro.setCodigoTipoRelacaoClienteImovel(Integer.valueOf(form.getTipoRelacao()));
		}

		campanhaCadastro.setNomeCliente(form.getNomeCliente());
		campanhaCadastro.setNomeMae(form.getNomeMae());

		if(indicadorResidencial != null && indicadorResidencial.equals(ConstantesSistema.SIM)){

			campanhaCadastro.setNumeroCPF(form.getNuCPF());
			campanhaCadastro.setNumeroRG(form.getNuRG());
			campanhaCadastro.setNumeroCPF(form.getNuCPF());
			campanhaCadastro.setDataRGEmissao(Util.converteStringParaDate(form.getDtEmissaoRG(), false));

			OrgaoExpedidorRg orgaoExpedidorRG = new OrgaoExpedidorRg();
			orgaoExpedidorRG.setId(Integer.valueOf(form.getOrgaoExpedidorRG()));
			campanhaCadastro.setOrgaoExpedidorRG(orgaoExpedidorRG);
			
			campanhaCadastro.setUnidadeFederacao(new UnidadeFederacao(Integer.valueOf(form.getEstado())));

			campanhaCadastro.setDataNascimento(Util.converteStringParaDate(form.getDtNascimento(), false));
			campanhaCadastro.setNomeMae(form.getNomeMae());

		}else{
			campanhaCadastro.setNumeroCNPJ(form.getNuCNPJ());
		}

		if(form.getEmail() != null && !form.getEmail().equals("")){
			campanhaCadastro.setDsEmail(form.getEmail());
		}

		

		campanhaCadastro = fachada.efetuarInscricaoCampanhaPremiacaoAction(usuarioLogado, campanhaCadastro,
						this.montarCollectionCampanhaCadastroFone(colecaoClienteFone));
		
		 // [FS0014] - Verificar sucesso da transa��o
		 if(campanhaCadastro.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.NAO)){

			// Utilizado na EmitirComprovanteInscricaoCampanhaPremiacao
			sessao.setAttribute("campanhaCadastro", campanhaCadastro);
		
			 String mensagemSucesso = "A inscri��o do im�vel " + form.getIdImovel() +
								" na campanha " + campanhaCadastro.getCampanha().getDsTituloCampanha() +
								" foi realizada com sucesso. O N�mero de Inscri��o � " + campanhaCadastro.getNumeroInscricao() + 
								". Solicite a emiss�o do comprovante de inscri��o.";

			if(campanhaCadastro.getDsEmail() != null && !campanhaCadastro.equals("")){
				// Com Link de Email
				montarPaginaSucesso(httpServletRequest, mensagemSucesso, "Enviar Comprovante para E-mail",
								"emitirComprovanteInscricaoCampanhaPremiacaoAction.do?indicadorEnvioComprovanteEmail=1&idCampanhaCadastro="
												+ campanhaCadastro.getId(),
								"emitirComprovanteInscricaoCampanhaPremiacaoAction.do?indicadorEnvioComprovanteEmail=2&idCampanhaCadastro="
												+ campanhaCadastro.getId(),
								"Emitir Comprovante de Inscri��o", "Efetuar Nova Inscri��o",
								"exibirEfetuarInscricaoCampanhaPremiacaoAction.do?menu=sim");
			}else{

				// Sem Link de Email
				montarPaginaSucesso(httpServletRequest, mensagemSucesso, "Emitir Comprovante de Inscri��o",
								"emitirComprovanteInscricaoCampanhaPremiacaoAction.do?indicadorEnvioComprovanteEmail=2&idCampanhaCadastro="
												+ campanhaCadastro.getId(),
								"exibirEfetuarInscricaoCampanhaPremiacaoAction.do?menu=sim", "Efetuar Nova Inscri��o");
			}
		 } else{

			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADOR_EMPRESA_PRINCIPAL, ConstantesSistema.SIM));

			Collection colEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			Empresa empresa = (Empresa) Util.retonarObjetoDeColecao(colEmpresa);
		
			String mensagem = "Esta matr�cula apresenta d�bito, solicitamos comparecer ao Escrit�rio Local da "
							+ empresa.getDescricaoAbreviada();

			montarPaginaSucesso(httpServletRequest, mensagem, "Efetuar Nova Inscri��o",
							"exibirEfetuarInscricaoCampanhaPremiacaoAction.do?menu=sim");
		 }

		sessao.removeAttribute("foneTipos");
		sessao.removeAttribute("colDdds");
		sessao.removeAttribute("colOrgaoExpedidorRg");
		sessao.removeAttribute("colecaoClienteFone");
		sessao.removeAttribute("telefoneJaExistente");
		sessao.removeAttribute("indicadorResidencial");
		sessao.removeAttribute("colEstados");
		sessao.removeAttribute("campanhaCadastro");
		sessao.setAttribute("indicadorConsulta", ConstantesSistema.SIM);
		sessao.removeAttribute("colecaoClienteFone");

		return retorno;
	}

	private Collection<CampanhaCadastroFone> montarCollectionCampanhaCadastroFone(Collection<ClienteFone> colClienteFone){

		Collection colCampanhaCadastroFone = new ArrayList();

		if(colClienteFone != null){
			for(ClienteFone clienteFone : colClienteFone){
				CampanhaCadastroFone campanhaCadastroFone = new CampanhaCadastroFone();

				campanhaCadastroFone.setFoneTipo(clienteFone.getFoneTipo());
				campanhaCadastroFone.setCodigoDDD(clienteFone.getDdd());
				campanhaCadastroFone.setNumeroFone(clienteFone.getTelefone());
				campanhaCadastroFone.setUltimaAlteracao(clienteFone.getUltimaAlteracao());

				if(clienteFone.getRamal() != null){
					campanhaCadastroFone.setNumeroFoneRamal(clienteFone.getRamal());
				}

				colCampanhaCadastroFone.add(campanhaCadastroFone);

			}
		}

		return colCampanhaCadastroFone;
	}

}
