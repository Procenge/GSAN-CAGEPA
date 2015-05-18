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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.FiltroImovelContaEnvio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelContaEnvio;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.FiltroNomeConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action que inicializa a primeira p�gina do processo de inserir imovel cliente
 * 
 * @author S�vio Luiz
 * @created 24 de Maio de 2004
 */
public class ExibirAtualizarImovelClienteAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarImovelCliente");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm inserirImovelClienteActionForm = (DynaValidatorForm) actionForm;

		// cria variaveis
		String idCliente = null;

		Collection colecaoImovelEnnvioConta = null;

		FiltroImovelContaEnvio filtroImovelContaEnvio = new FiltroImovelContaEnvio();
		filtroImovelContaEnvio
						.adicionarParametro(new ParametroSimples(FiltroNomeConta.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroImovelContaEnvio.setCampoOrderBy(FiltroImovelContaEnvio.DESCRICAO);

		colecaoImovelEnnvioConta = this.getFachada().pesquisar(filtroImovelContaEnvio, ImovelContaEnvio.class.getName());

		if(!colecaoImovelEnnvioConta.isEmpty()){

			// Coloca a cole�ao da pesquisa na sessao
			sessao.setAttribute("colecaoImovelEnnvioConta", colecaoImovelEnnvioConta);
		}

		// atribui os valores q vem pelo request as variaveis
		idCliente = (String) inserirImovelClienteActionForm.get("idCliente");

		// cria filtro
		FiltroCliente filtroCliente = new FiltroCliente();
		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();

		// limpa o cliente da sessao
		sessao.removeAttribute("clienteObj");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		if(httpServletRequest.getParameter("confirmado") != null && httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){
			sessao.setAttribute("confirmou", "sim");
		}

		Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");

		Collection tipoClientesImoveis = null;

		SimpleDateFormat dataFormatoAtual = new SimpleDateFormat("dd/MM/yyyy");
		// joga em dataInicial a parte da data
		String dataAtual = dataFormatoAtual.format(new Date());

		inserirImovelClienteActionForm.set("dataInicioClienteImovelRelacao", dataAtual);
		inserirImovelClienteActionForm.set("dataFimClienteImovelRelacao", "");
		inserirImovelClienteActionForm.set("idMotivoFimClienteImovelRelacao", String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));

		if(imovel != null && imovel.getImovelPerfil() != null
						&& imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL)){
			filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.CLIENTE_RELACAO_TIPO_ID,
							ConstantesSistema.CLIENTE_IMOVEL_TIPO_RESPONSAVEL));
		}
		filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		tipoClientesImoveis = fachada.pesquisar(filtroClienteRelacaoTipo, ClienteRelacaoTipo.class.getName());
		// a cole��o de clientesImoveisTipos � obrigat�rio
		if(tipoClientesImoveis == null || tipoClientesImoveis.equals("")){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "tipo cliente imovel");
		}

		sessao.setAttribute("tipoClientesImoveis", tipoClientesImoveis);
		if(inserirImovelClienteActionForm.get("textoSelecionado") == null
						|| inserirImovelClienteActionForm.get("textoSelecionado").equals("")){
			inserirImovelClienteActionForm.set("textoSelecionado", ((ClienteRelacaoTipo) ((List) tipoClientesImoveis).get(0))
							.getDescricao());
		}

		// informar o cliente conta para o imovel
		String indicadorNomeConta = httpServletRequest.getParameter("indicadorNomeConta");

		if(indicadorNomeConta != null && !indicadorNomeConta.equals("")){

			// String[] ids =
			// (String[])inserirImovelClienteActionForm.get("idNomeConta");

			if(sessao.getAttribute("imovelClientesNovos") != null){
				Collection clientesImoveis = (Collection) sessao.getAttribute("imovelClientesNovos");

				if(!clientesImoveis.isEmpty()){
					Iterator iteratorClientesImoveis = clientesImoveis.iterator();
					// int i = 0;

					String nomeContaSelecionado = (String) inserirImovelClienteActionForm.get("nomeContaSelecionado");
					while(iteratorClientesImoveis.hasNext()){

						// String nomeConta = ids[i];
						ClienteImovel clienteImovel = (ClienteImovel) iteratorClientesImoveis.next();

						// if(nomeConta.substring(0,7).equals("USUARIO"))
						if((clienteImovel.getClienteRelacaoTipo().getDescricao() + "-" + clienteImovel.getCliente().getId().toString())
										.equals(nomeContaSelecionado)){
							clienteImovel.setIndicadorNomeConta(Short.valueOf("1"));
						}else{
							clienteImovel.setIndicadorNomeConta(Short.valueOf("2"));
						}

						// i++;
					}
				}

			}

		}else{

			// -------Parte que trata do c�digo quando o usu�rio tecla enter
			// se o id do cliente for diferente de nulo
			if(idCliente != null && !idCliente.toString().trim().equalsIgnoreCase("")){
				Collection clientes = null;
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(idCliente)));
				clientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());
				if(clientes != null && !clientes.isEmpty()){
					// O cliente foi encontrado
					inserirImovelClienteActionForm.set("idCliente", ((Cliente) ((List) clientes).get(0)).getId().toString());
					inserirImovelClienteActionForm.set("nomeCliente", ((Cliente) ((List) clientes).get(0)).getNome());

					Cliente cliente = new Cliente();

					cliente = (Cliente) clientes.iterator().next();
					sessao.setAttribute("clienteObj", cliente);
				}else{
					httpServletRequest.setAttribute("codigoClienteNaoEncontrado", "true");
					inserirImovelClienteActionForm.set("nomeCliente", "");
				}

			}
			// fim da parte que trata do codigo do usuario que tecla enter
		}

		Collection clientesImoveis = (Collection) sessao.getAttribute("imovelClientesNovos");
		if(clientesImoveis != null && !clientesImoveis.isEmpty()){
			Iterator iteratorClientesImoveis = clientesImoveis.iterator();
			while(iteratorClientesImoveis.hasNext()){
				ClienteImovel clienteImovel = (ClienteImovel) iteratorClientesImoveis.next();
				if(clienteImovel.getIndicadorNomeConta() == null){
					clienteImovel.setIndicadorNomeConta(ConstantesSistema.NAO);
				}
			}
		}

		FiltroClienteImovelFimRelacaoMotivo filtroClienteImovelFimRelacaoMotivo = new FiltroClienteImovelFimRelacaoMotivo();

		filtroClienteImovelFimRelacaoMotivo.adicionarParametro(new ParametroSimples(FiltroClienteImovelFimRelacaoMotivo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection clienteImoveisFimRelacaoMotivo = fachada.pesquisar(filtroClienteImovelFimRelacaoMotivo,
						ClienteImovelFimRelacaoMotivo.class.getName());

		sessao.setAttribute("clienteImoveisFimRelacaoMotivo", clienteImoveisFimRelacaoMotivo);

		try{
			if(ParametroCadastro.P_INDICADOR_INFORMAR_DATA_RELACAO_FIM_INSERIR_CLIENTE_IMOVEL.executar().equals(
							ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorDataRelacaoFimInserir", "S");
			}else{
				sessao.removeAttribute("indicadorDataRelacaoFimInserir");
			}
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

		inserirImovelClienteActionForm.set("dataCorrenteServidor", formatDate.format(new Date()).toString());

		// Collection clientesImoveis = (Collection) sessao.getAttribute("imovelClientesNovos");
		// if (!clientesImoveis.isEmpty()) {
		// Iterator iteratorClientesImoveis = clientesImoveis.iterator();
		// while (iteratorClientesImoveis.hasNext()) {
		// ClienteImovel clienteImovel = (ClienteImovel) iteratorClientesImoveis.next();
		//
		// ImovelContaEnvio imovelContaEnvio = null;
		// //adiciona uma imovelContaEnvio caso esteja null
		// if (clienteImovel.getImovelContaEnvio() == null) {
		// imovelContaEnvio = new ImovelContaEnvio();
		// imovelContaEnvio.setId(ConstantesSistema.NUMERO_NAO_INFORMADO);
		// clienteImovel.setImovelContaEnvio(imovelContaEnvio);
		// }
		//
		// //verifica o tipo do indicador de emiss�o
		// if
		// (clienteImovel.getClienteRelacaoTipo().getId().equals(ConstantesSistema.CLIENTE_IMOVEL_TIPO_RESPONSAVEL)
		// && imovel.getIndicadorEmissaoExtratoFaturamento().equals(ConstantesSistema.SIM)) {
		// clienteImovel.setIndicadorEmissaoExtratoFaturamento(ConstantesSistema.SIM);
		// } else {
		// clienteImovel.setIndicadorEmissaoExtratoFaturamento(ConstantesSistema.NAO);
		// }
		// }
		// }

		return retorno;
	}

}
