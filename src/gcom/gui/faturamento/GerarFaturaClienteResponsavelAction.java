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

package gcom.gui.faturamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.Fatura;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarFaturaClienteResponsavelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o formul�rio
		GerarFaturaClienteResponsavelActionForm gerarFaturaClienteResponsavelActionForm = (GerarFaturaClienteResponsavelActionForm) actionForm;

		// Inicio - Transforma os campos em objetos
		Date dataVencimento = null;
		Map<Cliente, Collection<Conta>> mapClienteContas = null;

		// Data de vencimento da fatura
		// if(!Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getDataVencimento())){
		// dataVencimento =
		// Util.converterStringParaData(gerarFaturaClienteResponsavelActionForm.getDataVencimento());
		// }else{
		// throw new ActionServletException("atencao.data_vencimento.nao.informado");
		// }

		// Map de Clientes e suas respectivas Contas (contas selecionadas pelo usu�rio)
		if(!Util.isVazioOrNulo(gerarFaturaClienteResponsavelActionForm.getContas())){

			mapClienteContas = new HashMap<Cliente, Collection<Conta>>();
			String[] arrayClienteConta = null;
			Cliente cliente = null;
			Conta conta = null;
			Collection<Conta> contas = null;

			for(String clienteConta : gerarFaturaClienteResponsavelActionForm.getContas()){

				arrayClienteConta = clienteConta.split("-");
				if(!Util.isVazioOuBranco(arrayClienteConta[0])){
					cliente = new Cliente(Integer.valueOf(arrayClienteConta[0]));
				}
				if(!Util.isVazioOuBranco(arrayClienteConta[1])){
					conta = new Conta(Integer.valueOf(arrayClienteConta[1]));
				}

				if(cliente != null && conta != null){
					if(mapClienteContas.containsKey(cliente)){
						conta = carregarConta(conta);
						contas = mapClienteContas.get(cliente);
						contas.add(conta);
					}else{
						cliente = carregarCliente(cliente);
						conta = carregarConta(conta);
						contas = new ArrayList<Conta>();
						contas.add(conta);
						mapClienteContas.put(cliente, contas);
					}
				}
			}
		}else{
			throw new ActionServletException("atencao.conta.nao.selecionada");
		}
		// Fim - Transforma os campos em objetos
		Collection<Collection<Conta>> contasTotdas = mapClienteContas.values();
		Date maiorDataVencimento = null;
		for(Collection<Conta> collection : contasTotdas){
			for(Conta conta : collection){
				if(dataVencimento == null){
					dataVencimento = conta.getDataVencimentoConta();
				}
				maiorDataVencimento = conta.getDataVencimentoConta();
				if(dataVencimento.compareTo(maiorDataVencimento) < 0){
					dataVencimento = maiorDataVencimento;
				}
			}
		}
		//System.out.println("MAIOR DATA VENCIMENTO = " + Util.formatarData(dataVencimento));
		// Gerar Faturas para os Clientes Selecionados
		Collection<Fatura> faturas = fachada.gerarFaturaClienteResponsavel(mapClienteContas, dataVencimento);

		// P�gina Sucesso e Emiss�o
		if(faturas != null && !faturas.isEmpty()){
			Collection<Integer> idsFaturas = new ArrayList<Integer>();
			Iterator<Fatura> faturasIterator = faturas.iterator();
			while(faturasIterator.hasNext()){
				Fatura fatura = faturasIterator.next();
				idsFaturas.add(fatura.getId());
			}
			sessao.setAttribute("idsFaturas", idsFaturas);
			montarPaginaSucesso(httpServletRequest, "" + mapClienteContas.size() + " Cliente(s) selecionado(s) e " + faturas.size()
							+ " Fatura(s) gerada(s).", "Imprimir Fatura(s) gerada(s)", "emitirRelatorioFaturaClienteResponsavelAction.do",
							"exibirGerarFaturaClienteResponsavelAction.do", "Gerar novas Faturas");
		}else{
			montarPaginaSucesso(httpServletRequest, "Nenhuma fatura gerada.", "Voltar", "exibirGerarFaturaClienteResponsavelAction.do");
		}

		return retorno;

	}

	private Conta carregarConta(Conta conta){

		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, conta.getId()));
		filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LIGACAO_AGUA_SITUACAO);
		filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LIGACAO_ESGOTO_SITUACAO);
		filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
		return (Conta) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroConta, Conta.class.getName()));
	}

	private Cliente carregarCliente(Cliente cliente){

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId()));
		return (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente, Cliente.class.getName()));
	}

}