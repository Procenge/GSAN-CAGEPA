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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.ArrecadadorContratoTarifa;
import gcom.arrecadacao.ContratoMotivoCancelamento;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.banco.FiltroContaBancaria;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroContratoMotivoCancelamento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0510] Manter Contrato Arrecadador [SB0001]Atualizar Contrato de Arrecadador
 * 
 * @author Saulo Lima (atualização)
 * @date 19/06/2008
 */

public class AtualizarContratoArrecadadorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarContratoArrecadadorActionForm atualizarContratoArrecadadorActionForm = (AtualizarContratoArrecadadorActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		ArrecadadorContrato arrecadadorContrato = (ArrecadadorContrato) sessao.getAttribute("arrecadadorContrato");

		String[] idArrecadador = new String[1];

		idArrecadador[0] = arrecadadorContrato.getArrecadador().getId().toString();

		if(sessao.getAttribute("colecaoArrecadadorContratoTarifa") == null
						|| ((Collection<ArrecadadorContratoTarifa>) sessao.getAttribute("colecaoArrecadadorContratoTarifa")).isEmpty()){
			throw new ActionServletException("atencao.informe_dados_tarifa_contrato_uma_forma");
		}

		// Atualiza a entidade com os valores do formulário

		// Arrecadador
		if(atualizarContratoArrecadadorActionForm.getIdClienteCombo() != null
						&& !atualizarContratoArrecadadorActionForm.getIdClienteCombo().equals("")){
			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			// Inclui a obejeto de cliente no filtro de arrecadador
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			// filtra arrecadador pelo cliente
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CLIENTE_ID, atualizarContratoArrecadadorActionForm
							.getIdClienteCombo()));
			// Preenche colecao de arrecadador baseado no cliente escolhido
			Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

			if(colecaoArrecadador != null && !colecaoArrecadador.isEmpty()){
				Iterator iteratorColecaoArrecadador = colecaoArrecadador.iterator();
				while(iteratorColecaoArrecadador.hasNext()){
					Arrecadador arrecadador = (Arrecadador) iteratorColecaoArrecadador.next();
					arrecadadorContrato.setArrecadador(arrecadador);
				}
			}else{
				arrecadadorContrato.setArrecadador(null);
			}
		}

		arrecadadorContrato.setNumeroContrato(atualizarContratoArrecadadorActionForm.getNumeroContrato());
		// Conta Deposito Arrecadador
		FiltroContaBancaria filtroContaBancaria = new FiltroContaBancaria();
		if(atualizarContratoArrecadadorActionForm.getIdContaBancariaArrecadador() != null
						&& !atualizarContratoArrecadadorActionForm.getIdContaBancariaArrecadador().equals("")){
			filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, atualizarContratoArrecadadorActionForm
							.getIdContaBancariaArrecadador()));
			Collection<ContaBancaria> colecaoContaBancariaArrecadador = fachada.pesquisar(filtroContaBancaria, ContaBancaria.class
							.getName());
			if(colecaoContaBancariaArrecadador != null && !colecaoContaBancariaArrecadador.isEmpty()){
				Iterator iteratorColecaoContaBancariaArrecadador = colecaoContaBancariaArrecadador.iterator();
				while(iteratorColecaoContaBancariaArrecadador.hasNext()){
					ContaBancaria contaBancariaArrecadador = (ContaBancaria) iteratorColecaoContaBancariaArrecadador.next();
					arrecadadorContrato.setContaBancariaDepositoArrecadacao(contaBancariaArrecadador);
				}
			}else{
				arrecadadorContrato.setContaBancariaDepositoArrecadacao(null);
			}
		}

		// Conta Deposito Tarifa
		filtroContaBancaria = new FiltroContaBancaria();
		if(atualizarContratoArrecadadorActionForm.getIdContaBancariaTarifa() != null
						&& !atualizarContratoArrecadadorActionForm.getIdContaBancariaTarifa().equals("")){
			filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, atualizarContratoArrecadadorActionForm
							.getIdContaBancariaTarifa()));
			Collection<ContaBancaria> colecaoContaBancariaTarifa = fachada.pesquisar(filtroContaBancaria, ContaBancaria.class.getName());
			if(colecaoContaBancariaTarifa != null && !colecaoContaBancariaTarifa.isEmpty()){

				Iterator iteratorColecaoContaBancariaTarifa = colecaoContaBancariaTarifa.iterator();
				while(iteratorColecaoContaBancariaTarifa.hasNext()){
					ContaBancaria contaBancariaTarifa = (ContaBancaria) iteratorColecaoContaBancariaTarifa.next();
					arrecadadorContrato.setContaBancariaDepositoTarifa(contaBancariaTarifa);
				}
			}else{
				arrecadadorContrato.setContaBancariaDepositoTarifa(null);
			}
		}
		// Cliente
		Cliente cliente = new Cliente();
		cliente.setId(new Integer(atualizarContratoArrecadadorActionForm.getIdCliente()));

		// [FS0005]-Verificar se pessoa física
		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId()));
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");
		Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());
		Cliente clientePesq = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

		if(clientePesq.getClienteTipo().getIndicadorPessoaFisicaJuridica() != null
						&& clientePesq.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(new Short("2"))){
			throw new ActionServletException("atencao.cliente_arrecadador_pessoa_fisica");
		}

		arrecadadorContrato.setCliente(cliente);

		// E-mail
		arrecadadorContrato.setDescricaoEmail(atualizarContratoArrecadadorActionForm.getEmailCliente());

		// Código Convenio - Código de Barras
		arrecadadorContrato.setCodigoConvenio(atualizarContratoArrecadadorActionForm.getIdConvenio());

		// Código Convenio - Débito Automático
		arrecadadorContrato.setCodigoConvenioDebitoAutomatico(atualizarContratoArrecadadorActionForm.getIdConvenioDebitoAutomatico());

		// Código Convenio - Ficha de Compensação
		arrecadadorContrato.setCodigoConvenioFichaCompensacao(atualizarContratoArrecadadorActionForm.getIdConvenioFichaCompensacao());

		// Código Convenio - Boleto Bancário
		arrecadadorContrato.setCodigoConvenioBoletoBancario(atualizarContratoArrecadadorActionForm.getIdConvenioBoletoBancario());

		// Conta Deposito Arrecadador
		filtroContaBancaria = new FiltroContaBancaria();
		if(atualizarContratoArrecadadorActionForm.getIdDepositoArrecadacaoBoleto() != null
						&& !atualizarContratoArrecadadorActionForm.getIdDepositoArrecadacaoBoleto().equals("")){
			filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, atualizarContratoArrecadadorActionForm
							.getIdDepositoArrecadacaoBoleto()));
			Collection<ContaBancaria> colecaoContaBancariaArrecadador = fachada.pesquisar(filtroContaBancaria, ContaBancaria.class
							.getName());
			if(colecaoContaBancariaArrecadador != null && !colecaoContaBancariaArrecadador.isEmpty()){
				Iterator iteratorColecaoContaBancariaArrecadador = colecaoContaBancariaArrecadador.iterator();
				while(iteratorColecaoContaBancariaArrecadador.hasNext()){
					ContaBancaria contaBancariaArrecadador = (ContaBancaria) iteratorColecaoContaBancariaArrecadador.next();
					arrecadadorContrato.setContaBancariaDepositoArrecadacaoBoleto(contaBancariaArrecadador);
				}
			}else{
				arrecadadorContrato.setContaBancariaDepositoArrecadacao(null);
			}
		}

		// Conta Deposito Tarifa
		filtroContaBancaria = new FiltroContaBancaria();
		if(atualizarContratoArrecadadorActionForm.getIdDepositoTarifaBoleto() != null
						&& !atualizarContratoArrecadadorActionForm.getIdDepositoTarifaBoleto().equals("")){
			filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, atualizarContratoArrecadadorActionForm
							.getIdDepositoTarifaBoleto()));
			Collection<ContaBancaria> colecaoContaBancariaTarifa = fachada.pesquisar(filtroContaBancaria, ContaBancaria.class.getName());
			if(colecaoContaBancariaTarifa != null && !colecaoContaBancariaTarifa.isEmpty()){

				Iterator iteratorColecaoContaBancariaTarifa = colecaoContaBancariaTarifa.iterator();
				while(iteratorColecaoContaBancariaTarifa.hasNext()){
					ContaBancaria contaBancariaTarifa = (ContaBancaria) iteratorColecaoContaBancariaTarifa.next();
					arrecadadorContrato.setContaBancariaDepositoTarifaBoleto(contaBancariaTarifa);
				}
			}else{
				arrecadadorContrato.setContaBancariaDepositoTarifaBoleto(null);
			}
		}

		// arrecadadorContrato.setNumeroSequencialArquivoEnvioBoleto(0);
		// arrecadadorContrato.setNumeroSequencialArquivoRetornoBoleto(0);
		// arrecadadorContrato.setNumeroSequencialBoleto(0);

		// Código Convenio - Parcelamento por Responsável
		arrecadadorContrato.setCodigoConvenioParcelamentoResposavel(atualizarContratoArrecadadorActionForm
						.getIdConvenioParcelamentoResponsavel());

		// Nome do primeiro responsável
		arrecadadorContrato.setNomePrimeiroResposavel(atualizarContratoArrecadadorActionForm.getNomePrimeiroResponsavel());

		// CPF do primeiro responsável
		arrecadadorContrato.setCpfPrimeiroResposavel(atualizarContratoArrecadadorActionForm.getCpfPrimeiroResponsavel());

		// Nome do segundo responsável
		arrecadadorContrato.setNomeSegundoResposavel(atualizarContratoArrecadadorActionForm.getNomeSegundoResponsavel());

		// CPF do segundo responsável
		arrecadadorContrato.setCpfSegundoResposavel(atualizarContratoArrecadadorActionForm.getCpfSegundoResponsavel());

		// IndicadorCobrancaISS
		if(atualizarContratoArrecadadorActionForm.getIndicadorCobranca() != null){
			arrecadadorContrato.setIndicadorCobrancaIss(new Short(atualizarContratoArrecadadorActionForm.getIndicadorCobranca()));
		}else{
			arrecadadorContrato.setIndicadorCobrancaIss(null);
		}

		// Intervalo de Datas do Contrato
		arrecadadorContrato.setDataContratoInicio(Util.converteStringParaDate(atualizarContratoArrecadadorActionForm.getDtInicioContrato(),
						false));
		arrecadadorContrato
						.setDataContratoFim(Util.converteStringParaDate(atualizarContratoArrecadadorActionForm.getDtFimContrato(), false));

		String dataContratoEncerramentoString = atualizarContratoArrecadadorActionForm.getDataContratoEncerramento().trim();
		String contratoMotivoCancelamentoIdString = atualizarContratoArrecadadorActionForm.getIdContratoMotivoCancelamento().trim();

		// [FS0005] – Validar Data
		String dataInicioContrato = atualizarContratoArrecadadorActionForm.getDtInicioContrato().trim();

		if(dataInicioContrato.equals("") || dataInicioContrato == null){
			throw new ActionServletException("atencao.contrato_arrecadador_data_inicio");
		}

		// [FS0009] - Validar informação de Data de Encerramento e Motivo de Cancelamento
		if(!dataContratoEncerramentoString.equals("") && contratoMotivoCancelamentoIdString.equals("")){
			throw new ActionServletException("atencao.contrato_arrecadador_motivo_cancelamento");
		}else if(dataContratoEncerramentoString.equals("") && !contratoMotivoCancelamentoIdString.equals("")
						&& !contratoMotivoCancelamentoIdString.equals("-1")){
			throw new ActionServletException("atencao.contrato_arrecadador_data_encerramento");
		}else if(!dataContratoEncerramentoString.equals("") && !contratoMotivoCancelamentoIdString.equals("")){
			arrecadadorContrato.setDataContratoEncerramento(Util.converteStringParaDate(dataContratoEncerramentoString));

			// Contrato Motivo Cancelamento
			ContratoMotivoCancelamento contratoMotivoCancelamento = new ContratoMotivoCancelamento();
			contratoMotivoCancelamento.setId(Integer.parseInt(contratoMotivoCancelamentoIdString));

			FiltroContratoMotivoCancelamento filtroContratoMotivoCancelamento = new FiltroContratoMotivoCancelamento();
			filtroContratoMotivoCancelamento.adicionarParametro(new ParametroSimples(FiltroContratoMotivoCancelamento.ID,
							contratoMotivoCancelamento.getId()));
			Collection colecaoContratoMotivoCancelamento = fachada.pesquisar(filtroContratoMotivoCancelamento,
							ContratoMotivoCancelamento.class.getName());
			ContratoMotivoCancelamento contratoMotivoCancelamentoPesq = (ContratoMotivoCancelamento) Util
							.retonarObjetoDeColecao(colecaoContratoMotivoCancelamento);

			if(contratoMotivoCancelamentoPesq == null){
				throw new ActionServletException("atencao.contrato_arrecadador_motivo_cancelamento");
			}
			arrecadadorContrato.setContratoMotivoCancelamento(contratoMotivoCancelamentoPesq);
		}else{
			arrecadadorContrato.setDataContratoEncerramento(null);
			arrecadadorContrato.setContratoMotivoCancelamento(null);
		}

		// atualiza na base de dados de Arrecadador
		fachada.atualizarContratoArrecadador(arrecadadorContrato, (Collection<ArrecadadorContratoTarifa>) sessao
						.getAttribute("colecaoArrecadadorContratoTarifa"), usuarioLogado);

		// Monta a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Contrato de Arrecadador " + atualizarContratoArrecadadorActionForm.getNumeroContrato()
						+ " atualizado com sucesso.", "Realizar outra Manutenção de Contrato de Arrecadador",
						"exibirFiltrarContratoArrecadadorAction.do?menu=sim");

		return retorno;
	}

}
