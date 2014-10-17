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

package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * @author Rodrigo
 */
public class InserirClientePessoaAction
				extends GcomAction {

	/**
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ j� cadastrado desde que seja igual ao do cliente
	 *       respons�vel superior
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");

		// Pega o actionform da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		DynaValidatorForm form = (DynaValidatorForm) sessao.getAttribute("ClienteActionForm");

		Fachada fachada = Fachada.getInstancia();

		// Verifica se o usu�rio informou o CPF/RG - CNPJ
		String cpf = (String) form.get("cpf");
		String rg = (String) form.get("rg");
		String cnpj = (String) form.get("cnpj");

		// Seta o clienteTipo
		ClienteTipo clienteTipo = new ClienteTipo();

		FiltroClienteTipo filtroClienteTipo1 = new FiltroClienteTipo();
		filtroClienteTipo1.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, Integer.valueOf((Short) form.get("tipoPessoa"))
						.intValue()));

		clienteTipo = (ClienteTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteTipo1, ClienteTipo.class.getName()));

		// O usu�rio � pessoa f�sica
		if(cpf != null && !cpf.trim().equalsIgnoreCase("")){

			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, cpf));

			Collection<Cliente> colecaoClienteComCpf = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(!colecaoClienteComCpf.isEmpty()){
				Cliente clienteComCpf = colecaoClienteComCpf.iterator().next();
				throw new ActionServletException("atencao.cpf.cliente.ja_cadastrado", null, "" + clienteComCpf.getId());
			}
		}

		// if(rg != null && !rg.trim().equalsIgnoreCase("")){
		// FiltroCliente filtroCliente = new FiltroCliente();
		//
		// filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.RG, rg));
		//
		// Integer idOrgaoExpedidor = new Integer(form.get("idOrgaoExpedidor").toString());
		// // filtroCliente.adicionarCaminhoParaCarregamentoEntidade("orgaoExpedidorRg");
		// filtroCliente.adicionarParametro(new
		// ParametroSimples(FiltroCliente.ORGAO_EXPEDIDOR_RG_ID, idOrgaoExpedidor.intValue()));
		//
		// Integer idUnidadeFederacao = new Integer(form.get("idUnidadeFederacao").toString());
		// filtroCliente.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacao");
		// filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.UNIDADE_FEDERACAO_ID,
		// idUnidadeFederacao.intValue()));
		//
		// Collection<Cliente> colecaoClienteComRg = fachada.pesquisar(filtroCliente,
		// Cliente.class.getName());
		//
		// if(!colecaoClienteComRg.isEmpty()){
		// Cliente clienteComRg = colecaoClienteComRg.iterator().next();
		// throw new ActionServletException("atencao.rg.cliente.ja_cadastrado", null, "" +
		// clienteComRg.getId());
		// }
		// }

		// Pega o codigo do cliente(que vem de pessoa jur�dica)
		String codigoClienteResponsavel = (String) form.get("codigoClienteResponsavel");

		// Verificar se a data de emissao do rg � menor que a data atual do sistema
		String dataEmissaoRg = (String) form.get("dataEmissao");

		if(dataEmissaoRg != null && !dataEmissaoRg.trim().equals("")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			try{
				if(!verificarDataMenorQueDataCorrente(dateFormat.parse(dataEmissaoRg))){
					throw new ActionServletException("atencao.data_menor_que_atual", null, "emiss�o do RG");
				}
			}catch(ParseException ex){
				throw new ActionServletException("erro.sistema");
			}
		}

		// Verificar se a data de nascimento � menor que a data atual do sistema
		String dataNascimento = (String) form.get("dataNascimento");

		if(dataNascimento != null && !dataNascimento.trim().equals("")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			try{
				if(!verificarDataMenorQueDataCorrente(dateFormat.parse(dataNascimento))){
					throw new ActionServletException("atencao.data_menor_que_atual", null, "nascimento");
				}
			}catch(ParseException ex){
				throw new ActionServletException("erro.sistema");
			}
		}

		// Verificar se a data de nascimento � maior que a data de emiss�o de rg
		if(dataNascimento != null && !dataNascimento.trim().equals("") && dataEmissaoRg != null && !dataEmissaoRg.trim().equals("")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			try{
				if(dateFormat.parse(dataNascimento).after(dateFormat.parse(dataEmissaoRg))){
					throw new ActionServletException("atencao.data_nascimento_maior_que_data_emissao");
				}
			}catch(ParseException ex){
				throw new ActionServletException("erro.sistema");
			}
		}

		// Verifica o c�digo se ele estiver presente
		Cliente encontrado = null;
		if(codigoClienteResponsavel != null && !codigoClienteResponsavel.trim().equals("")){
			FiltroCliente filtro = new FiltroCliente();

			filtro.adicionarParametro(new ParametroSimples(FiltroCliente.ID, codigoClienteResponsavel));
			filtro.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");

			// Pesquisa o c�digo para verificar a exist�ncia dele
			Collection<Cliente> clienteEncontrado = fachada.pesquisar(filtro, Cliente.class.getName());

			// Se o c�digo n�o existir ent�o o usu�rio passou um c�digo que n�o foi pesquisado na
			// p�gina
			if(clienteEncontrado.isEmpty()){
				throw new ActionServletException("atencao.cliente.responsavel.inexistente");
			}

			// O cliente foi encontrado
			encontrado = clienteEncontrado.iterator().next();

			if(encontrado.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){

				// O usu�rio digitou uma pessoa f�sica limpa a sele��o do usu�rio do form
				throw new ActionServletException("atencao.responsavel.pessoa_juridica");
			}
		}

		// O usu�rio � pessoa jur�dica
		if(cnpj != null && !cnpj.trim().equalsIgnoreCase("")){

			boolean permiteDuplicidade = false;

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, cnpj));
			Collection<Cliente> colecaoClienteComCnpj = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(!colecaoClienteComCnpj.isEmpty()){
				Cliente clienteComCnpj = colecaoClienteComCnpj.iterator().next();
				try{
					if(ParametroCadastro.P_PERMITE_DUPLICIDADE_CNPJ.executar().equals(ConstantesSistema.SIM.toString())
									&& !EsferaPoder.PARTICULAR.toString().equals(
													clienteTipo.getEsferaPoder().getId().toString())){

						permiteDuplicidade = true;

					}
				}catch(ControladorException e){

					throw new ActionServletException(e.getMessage(), e);
				}

				if((encontrado == null || !clienteComCnpj.getCnpj().equals(encontrado.getCnpj())) && !permiteDuplicidade){

					throw new ActionServletException("atencao.cnpj.cliente.ja_cadastrado", null, "" + clienteComCnpj.getId());
				}
			}
		}

		return retorno;
	}
}
