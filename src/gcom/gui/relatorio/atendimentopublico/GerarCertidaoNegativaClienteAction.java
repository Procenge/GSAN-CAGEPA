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

package gcom.gui.relatorio.atendimentopublico;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.GerarCertidaoNegativaClienteActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioCertidaoNegativaCliente;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC00725] Gerar Relat�rio de Im�veis por Situa��o da Liga��o de �gua
 * 
 * @author Rafael Pinto
 * @date 28/11/2007
 */

public class GerarCertidaoNegativaClienteAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = null;

		// Form
		GerarCertidaoNegativaClienteActionForm form = (GerarCertidaoNegativaClienteActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Usuario usuarioLogado = this.getUsuarioLogado(httpServletRequest);

		// Imovel que foi informado
		Collection<Integer> idsTodosClientes = new ArrayList<Integer>();
		Cliente clienteInformado = new Cliente();

		if(form.getIdCliente() != null && !form.getIdCliente().trim().equals("")){

			Integer idClienteInformado = Integer.valueOf(form.getIdCliente());

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idClienteInformado));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteTipo.esferaPoder");

			Collection colecaoClientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoClientes == null || colecaoClientes.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cliente");
			}else{
				clienteInformado = (Cliente) Util.retonarObjetoDeColecao(colecaoClientes);

				if(clienteInformado.getClienteTipo().getEsferaPoder().getIndicadorPermiteCertidaoNegativaDebitosParaCliente().equals(
								ConstantesSistema.NAO)){
					throw new ActionServletException("atencao.esfera_poder_nao_permite_geracao_certidao_negativa");
				}
			}

			boolean temPermissaoEmitirCertidaoNegativaComClienteSuperior = fachada
							.verificarPermissaoEmitirCertidaoNegativaComClienteSuperior(usuarioLogado);

			if(!temPermissaoEmitirCertidaoNegativaComClienteSuperior){

				// Verifica se o cliente informado tem algum superior, caso
				// tenha informa ao usu�rio
				filtroCliente.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCliente.CLIENTE_RESPONSAVEL_ID, idClienteInformado));

				colecaoClientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());

				if(colecaoClientes != null && !colecaoClientes.isEmpty()){
					throw new ActionServletException("atencao.existe_cliente_superior");
				}

			}

			Integer tipoCliente = null;

			if(form.getResponsavel() != null && form.getResponsavel().equals("1")){
				tipoCliente = new Integer(3);
			}else if(form.getResponsavel() != null && form.getResponsavel().equals("2")){
				tipoCliente = new Integer(4);
			}else{
				tipoCliente = new Integer(2);
			}

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			Collection<Integer> idsClientes = fachada.pesquisarClientesAssociadosResponsavel(idClienteInformado);
			idsClientes.add(idClienteInformado);

			Collection<Integer> idsClientesAdicionados = new ArrayList<Integer>();

			// data inicio vencimento debito
			Calendar dataInicioVencimentoDebito = new GregorianCalendar();
			dataInicioVencimentoDebito.set(Calendar.YEAR, new Integer("0001").intValue());
			dataInicioVencimentoDebito.set(Calendar.MONTH, 0);
			dataInicioVencimentoDebito.set(Calendar.DATE, new Integer("01").intValue());

			// data final de vencimento de debito
			Calendar dataFimVencimentoDebito = new GregorianCalendar();
			dataFimVencimentoDebito.add(Calendar.DATE, -sistemaParametro.getNumeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos());

			while(idsClientes != null && !idsClientes.isEmpty()){

				idsClientesAdicionados = new ArrayList<Integer>();

				for(Integer idCliente : idsClientes){

					if(idsTodosClientes != null && !idsTodosClientes.contains(idCliente)){

						Collection<Integer> idsClientesNovos = fachada.pesquisarClientesAssociadosResponsavel(idCliente);

						ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = fachada.obterDebitoImovelOuCliente(tipoCliente,
										null, idCliente.toString(), null, "000101", "999912", dataInicioVencimentoDebito.getTime(),
										dataFimVencimentoDebito.getTime(), 1, 2, 2, 2, 1, 1, 2, null, null, null, null, null,
										ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);

						if(obterDebitoImovelOuClienteHelper != null){
							if((obterDebitoImovelOuClienteHelper.getColecaoContasValores() != null && !obterDebitoImovelOuClienteHelper
											.getColecaoContasValores().isEmpty())
											|| (obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar() != null && !obterDebitoImovelOuClienteHelper
															.getColecaoDebitoACobrar().isEmpty())
											|| (obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores() != null && !obterDebitoImovelOuClienteHelper
															.getColecaoGuiasPagamentoValores().isEmpty())){

								throw new ActionServletException("atencao.cliente_com_debitos");
							}
						}

						idsClientesAdicionados.addAll(idsClientesNovos);
						idsTodosClientes.add(idCliente);
					}
				}

				// if (idsTodosClientes == null || idsTodosClientes.isEmpty()) {
				// idsTodosClientes = idsClientes;
				// }

				// idsTodosClientes.addAll(idsClientesAdicionados);
				idsClientes = idsClientesAdicionados;

			}
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		TarefaRelatorio relatorio = new RelatorioCertidaoNegativaCliente(usuarioLogado);

		relatorio.addParametro("idsClientes", idsTodosClientes);
		relatorio.addParametro("clienteInformado", clienteInformado);
		relatorio.addParametro("usuarioLogado", usuarioLogado);

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}