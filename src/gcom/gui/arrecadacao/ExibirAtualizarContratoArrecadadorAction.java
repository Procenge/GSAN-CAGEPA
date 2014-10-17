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

import gcom.arrecadacao.*;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.banco.FiltroContaBancaria;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroMotivoCancelamento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0510] ATUALIZAR CONTRATO DE ARRECADADOR
 * 
 * @author Marcio Roberto
 * @date 11/04/2007
 */

public class ExibirAtualizarContratoArrecadadorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarContratoArrecadador");

		AtualizarContratoArrecadadorActionForm atualizarContratoArrecadadorActionForm = (AtualizarContratoArrecadadorActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		if(httpServletRequest.getParameter("removerArrecadacaoForma") != null){
			Collection<ArrecadadorContratoTarifa> colecaoAux = (Collection<ArrecadadorContratoTarifa>) sessao
							.getAttribute("colecaoArrecadadorContratoTarifa");

			Iterator<ArrecadadorContratoTarifa> it = colecaoAux.iterator();

			Collection<ArrecadadorContratoTarifa> colecaoArrecadadorContratoTarifa = new Vector<ArrecadadorContratoTarifa>();

			while(it.hasNext()){
				ArrecadadorContratoTarifa aux = it.next();
				if(!aux.getComp_id().getArrecadacaoFormaId()
								.equals(Util.obterInteger(httpServletRequest.getParameter("idRegistrosRemocao")))){
					colecaoArrecadadorContratoTarifa.add(aux);
				}
			}

			sessao.setAttribute("colecaoArrecadadorContratoTarifa", colecaoArrecadadorContratoTarifa);

		}else if(httpServletRequest.getParameter("adicionarFormaArrecadacao") != null){

			Integer idFormaArrecadacao = Util.obterInteger(httpServletRequest.getParameter("idFormaArrecadacao"));

			ArrecadacaoForma arrecadacaoForma = (ArrecadacaoForma) fachada.pesquisar(idFormaArrecadacao, ArrecadacaoForma.class);
			ArrecadadorContratoTarifa arrecadadorContratoTarifa = new ArrecadadorContratoTarifa();

			Integer idContratoArrecadador = Util.obterInteger((String) sessao.getAttribute("idRegistroAtualizacao"));
			ArrecadadorContrato arrecadadorContrato = new ArrecadadorContrato();
			arrecadadorContrato.setId(idContratoArrecadador);

			arrecadadorContratoTarifa.setComp_id(new ArrecadadorContratoTarifaPK(idContratoArrecadador, arrecadacaoForma.getId()));
			arrecadadorContratoTarifa.setArrecadacaoForma(arrecadacaoForma);
			arrecadadorContratoTarifa.setArrecadadorContrato(arrecadadorContrato);
			arrecadadorContratoTarifa.setNumeroDiaFloat(Util.obterShort(httpServletRequest.getParameter("qtdDiasFloat")));
			BigDecimal percentualTarifa = null;
			if(httpServletRequest.getParameter("percentualTarifa") != ""){
				percentualTarifa = new BigDecimal(httpServletRequest.getParameter("percentualTarifa").replace(",", "."));
				if(percentualTarifa.compareTo(new BigDecimal("100")) > 0 || percentualTarifa.equals(BigDecimal.ZERO)){
					throw new ActionServletException("atencao.percentual_informado_calculo_tarifa_invalido");
				}
			}
			BigDecimal valorTarifa = null;
			if(httpServletRequest.getParameter("valorTarifa") != ""){
				valorTarifa = new BigDecimal(httpServletRequest.getParameter("valorTarifa").replace(",", "."));
				if(valorTarifa.compareTo(BigDecimal.ZERO) <= 0){
					throw new ActionServletException("atencao.valor_monetario_tarifa_invalido");
				}
			}
			arrecadadorContratoTarifa.setPercentualTarifa(percentualTarifa);
			arrecadadorContratoTarifa.setValorTarifa(valorTarifa);
			arrecadadorContratoTarifa.setUltimaAlteracao(new Date());
			Collection<ArrecadadorContratoTarifa> colecaoArrecadadorContratoTarifa = (Collection<ArrecadadorContratoTarifa>) sessao
							.getAttribute("colecaoArrecadadorContratoTarifa");
			if(colecaoArrecadadorContratoTarifa == null){
				colecaoArrecadadorContratoTarifa = new Vector<ArrecadadorContratoTarifa>();
			}

			Iterator<ArrecadadorContratoTarifa> it = colecaoArrecadadorContratoTarifa.iterator();

			while(it.hasNext()){
				ArrecadadorContratoTarifa aux = it.next();
				if(aux.getComp_id().getArrecadacaoFormaId().equals(arrecadadorContratoTarifa.getComp_id().getArrecadacaoFormaId())){
					throw new ActionServletException("atencao.forma_ja_informada_contrato");
				}
			}

			colecaoArrecadadorContratoTarifa.add(arrecadadorContratoTarifa);
			sessao.setAttribute("colecaoArrecadadorContratoTarifa", colecaoArrecadadorContratoTarifa);
		}else{

			if(httpServletRequest.getParameter("menu") != null){
				sessao.removeAttribute("colecaoArrecadadorContratoTarifa");
			}

			String idContratoArrecadador = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
			if(Util.isNaoNuloBrancoZero(idContratoArrecadador)){
				sessao.setAttribute("idRegistroAtualizacao", idContratoArrecadador);
			}

			if(idContratoArrecadador == null){
				if(httpServletRequest.getAttribute("idRegistroAtualizacao") == null){
					idContratoArrecadador = (String) sessao.getAttribute("idRegistroAtualizacao");
				}else{
					idContratoArrecadador = (String) httpServletRequest.getAttribute("idRegistroAtualizacao").toString();
				}
			}else{
				sessao.setAttribute("i", true);
			}

			// Arrecadador
			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			// Ordena filtro de arrecadador por id do cliente
			filtroArrecadador.setCampoOrderBy(FiltroArrecadador.CLIENTE_ID);
			// Inclui a objeto de cliente no filtro de arrecadador
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			// Preenche colecao de arrecadador
			Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());
			if(colecaoArrecadador == null || colecaoArrecadador.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Arrecadador");
			}else{
				FiltroCliente filtroCliente = new FiltroCliente();
				Iterator iteratorColecaoArrecadador = colecaoArrecadador.iterator();
				Cliente cliente = new Cliente();
				while(iteratorColecaoArrecadador.hasNext()){
					Arrecadador arrecadador = (Arrecadador) iteratorColecaoArrecadador.next();
					cliente = arrecadador.getCliente();
					if(Util.isNaoNuloBrancoZero(cliente)){
						filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId(),
										ParametroSimples.CONECTOR_OR));
					}
				}
				Collection colecaoClienteArrecadador = fachada.pesquisar(filtroCliente, Cliente.class.getName());
				sessao.setAttribute("colecaoClienteArrecadador", colecaoClienteArrecadador);
			}

			FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();

			filtroArrecadacaoForma.setCampoOrderBy(FiltroArrecadacaoForma.DESCRICAO);

			Collection<ArrecadacaoForma> colecaoFormaArrecadacao = fachada.pesquisar(filtroArrecadacaoForma,
							ArrecadacaoForma.class.getName());

			if(colecaoFormaArrecadacao != null && !colecaoFormaArrecadacao.isEmpty()){
				sessao.setAttribute("colecaoFormaArrecadacao", colecaoFormaArrecadacao);
			}else{
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "ARRECADACAO_FORMA");
			}

			Collection collectionArrecadadorContrato = (Collection) httpServletRequest.getAttribute("colecaoArrecadadorContrato");
			ArrecadadorContrato arrecadadorContrato = (ArrecadadorContrato) Util.retonarObjetoDeColecao(collectionArrecadadorContrato);

			String idCliente = null;

			// ///////////////////// VALIDACAO DE CLIENTE ///////////////////
			idCliente = httpServletRequest.getParameter("idCliente");
			if(idCliente != null && !idCliente.trim().equals("")){
				// Filtro para descobrir id do Cliente
				FiltroCliente filtroCliente = new FiltroCliente();

				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

				Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());
				if(colecaoCliente == null || colecaoCliente.isEmpty()){
					atualizarContratoArrecadadorActionForm.setIdCliente("");
					atualizarContratoArrecadadorActionForm.setNomeCliente("");
					httpServletRequest.setAttribute("existeCliente", "exception");
					throw new ActionServletException("atencao.cliente.inexistente");
				}else{
					Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
					atualizarContratoArrecadadorActionForm.setIdCliente(cliente.getId().toString());
					atualizarContratoArrecadadorActionForm.setNomeCliente(cliente.getNome());
					httpServletRequest.setAttribute("nomeCampo", "idCliente");
				}
			}
			// ///////////////////// FIM VALIDACAO DE CLIENTE ///////////////////

			if(idContratoArrecadador != null && !idContratoArrecadador.trim().equals("") && Integer.parseInt(idContratoArrecadador) > 0){

				FiltroArrecadadorContrato filtroArrecadadorContrato = new FiltroArrecadadorContrato();
				// Adiciona entidade estrangeira para carregamento do objeto
				// "CLIENTE"
				// (ou seja, em ARRECADADOR existe um atributo do tipo Cliente,
				// então é preciso carregar o cliente)
				// o mesmo para Imovel.
				filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("arrecadador.cliente");
				filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("cliente");
				filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadadorContrato.CONCESSIONARIA);

				filtroArrecadadorContrato.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.ID, idContratoArrecadador));
				Collection<ArrecadadorContrato> colecaoArrecadadorContrato = fachada.pesquisar(filtroArrecadadorContrato,
								ArrecadadorContrato.class.getName());

				if(colecaoArrecadadorContrato != null && !colecaoArrecadadorContrato.isEmpty()){
					arrecadadorContrato = (ArrecadadorContrato) Util.retonarObjetoDeColecao(colecaoArrecadadorContrato);

					FiltroArrecadadorContratoTarifa filtroArrecadadorContratoTarifa = new FiltroArrecadadorContratoTarifa();

					filtroArrecadadorContratoTarifa.adicionarParametro(new ParametroSimples(
									FiltroArrecadadorContratoTarifa.ID_ARRECADADOR_CONTRATO, arrecadadorContrato.getId()));

					filtroArrecadadorContratoTarifa.adicionarCaminhoParaCarregamentoEntidade("arrecadacaoForma");
					filtroArrecadadorContratoTarifa.adicionarCaminhoParaCarregamentoEntidade("arrecadadorContrato");

					Collection<ArrecadadorContratoTarifa> colecaoArrecadadorContratoTarifa = fachada.pesquisar(
									filtroArrecadadorContratoTarifa, ArrecadadorContratoTarifa.class.getName());

					sessao.setAttribute("colecaoArrecadadorContratoTarifa", colecaoArrecadadorContratoTarifa);

					atualizarContratoArrecadadorActionForm.setIdClienteCombo(arrecadadorContrato.getArrecadador().getCliente().getId()
									.toString());
					atualizarContratoArrecadadorActionForm.setIdArrecadador(arrecadadorContrato.getArrecadador().getId().toString());
					atualizarContratoArrecadadorActionForm.setNomeCliente(arrecadadorContrato.getArrecadador().getCliente().getNome());

					try{
						String indicadorConcessionaria = ParametroCadastro.P_INDICADOR_CONCESSIONARIA.executar();

						if(!ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){
							atualizarContratoArrecadadorActionForm.setConcessionaria(arrecadadorContrato.getConcessionaria());
							sessao.setAttribute("indicadorConcessionaria", indicadorConcessionaria);
						}

					}catch(ControladorException e){
						throw new IllegalArgumentException("erro.sistema");
					}

				}
			}

			atualizarContratoArrecadadorActionForm.setNumeroContrato(arrecadadorContrato.getNumeroContrato());

			ContaBancaria contaBancariaArrecadacao = arrecadadorContrato.getContaBancariaDepositoArrecadacao();
			if(contaBancariaArrecadacao != null){
				String idContaBancaria = contaBancariaArrecadacao.getId().toString();
				FiltroContaBancaria filtroContaBancaria = new FiltroContaBancaria();
				filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, idContaBancaria));
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia");
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia.banco");
				Collection coll = Fachada.getInstancia().pesquisar(filtroContaBancaria, ContaBancaria.class.getName());

				contaBancariaArrecadacao = (ContaBancaria) coll.iterator().next();
				atualizarContratoArrecadadorActionForm.setBcoArrecadadorConta(contaBancariaArrecadacao.getAgencia().getBanco().getId()
								.toString());
				atualizarContratoArrecadadorActionForm.setAgArrecadadorConta(contaBancariaArrecadacao.getAgencia().getCodigoAgencia());
				atualizarContratoArrecadadorActionForm.setNumeroArrecadadorConta(contaBancariaArrecadacao.getNumeroConta());
			}

			ContaBancaria contaBancariaTarifa = arrecadadorContrato.getContaBancariaDepositoTarifa();
			if(contaBancariaTarifa != null){
				String idContaBancaria = contaBancariaTarifa.getId().toString();
				FiltroContaBancaria filtroContaBancaria = new FiltroContaBancaria();
				filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, idContaBancaria));
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia");
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia.banco");
				Collection coll = Fachada.getInstancia().pesquisar(filtroContaBancaria, ContaBancaria.class.getName());

				contaBancariaTarifa = (ContaBancaria) coll.iterator().next();
				atualizarContratoArrecadadorActionForm.setBcoTarifaConta(contaBancariaTarifa.getAgencia().getBanco().getId().toString());
				atualizarContratoArrecadadorActionForm.setAgTarifaConta(contaBancariaTarifa.getAgencia().getCodigoAgencia());
				atualizarContratoArrecadadorActionForm.setNumeroTarifaConta(contaBancariaTarifa.getNumeroConta());
			}

			atualizarContratoArrecadadorActionForm.setIdConvenioBoletoBancario(arrecadadorContrato.getCodigoConvenioBoletoBancario());

			ContaBancaria contaBancariaArrecadacaoBoleto = arrecadadorContrato.getContaBancariaDepositoArrecadacaoBoleto();
			if(contaBancariaArrecadacaoBoleto != null){
				String idContaBancaria = contaBancariaArrecadacaoBoleto.getId().toString();
				FiltroContaBancaria filtroContaBancaria = new FiltroContaBancaria();
				filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, idContaBancaria));
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia");
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia.banco");
				Collection coll = Fachada.getInstancia().pesquisar(filtroContaBancaria, ContaBancaria.class.getName());

				contaBancariaArrecadacao = (ContaBancaria) coll.iterator().next();
				atualizarContratoArrecadadorActionForm.setIdDepositoArrecadacaoBoleto(contaBancariaArrecadacao.getId().toString());
				atualizarContratoArrecadadorActionForm.setBcoArrecadadorContaBoletoBancario(contaBancariaArrecadacao.getAgencia()
								.getBanco().getId().toString());
				atualizarContratoArrecadadorActionForm.setAgArrecadadorContaBoletoBancario(contaBancariaArrecadacao.getAgencia()
								.getCodigoAgencia());
				atualizarContratoArrecadadorActionForm.setNumeroArrecadadorContaBoletoBancario(contaBancariaArrecadacao.getNumeroConta());
			}

			ContaBancaria contaBancariaTarifaBoleto = arrecadadorContrato.getContaBancariaDepositoTarifaBoleto();
			if(contaBancariaTarifaBoleto != null){
				String idContaBancaria = contaBancariaTarifaBoleto.getId().toString();
				FiltroContaBancaria filtroContaBancaria = new FiltroContaBancaria();
				filtroContaBancaria.adicionarParametro(new ParametroSimples(FiltroContaBancaria.ID, idContaBancaria));
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia");
				filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia.banco");
				Collection coll = Fachada.getInstancia().pesquisar(filtroContaBancaria, ContaBancaria.class.getName());

				contaBancariaTarifa = (ContaBancaria) coll.iterator().next();
				atualizarContratoArrecadadorActionForm.setIdDepositoTarifaBoleto(contaBancariaTarifa.getId().toString());
				atualizarContratoArrecadadorActionForm.setBcoTarifaContaBoletoBancario(contaBancariaTarifa.getAgencia().getBanco().getId()
								.toString());
				atualizarContratoArrecadadorActionForm.setAgTarifaContaBoletoBancario(contaBancariaTarifa.getAgencia().getCodigoAgencia());
				atualizarContratoArrecadadorActionForm.setNumeroTarifaContaBoletoBancario(contaBancariaTarifa.getNumeroConta());
			}

			if(idCliente == null || idCliente.trim().equals("")){
				atualizarContratoArrecadadorActionForm.setIdCliente(arrecadadorContrato.getCliente().getId().toString());
				atualizarContratoArrecadadorActionForm.setNomeCliente(arrecadadorContrato.getCliente().getNome());
			}

			// E-mail
			atualizarContratoArrecadadorActionForm.setDescricaoEmail(arrecadadorContrato.getDescricaoEmail());

			atualizarContratoArrecadadorActionForm.setIdConvenio(arrecadadorContrato.getCodigoConvenio());

			atualizarContratoArrecadadorActionForm.setIdConvenioDebitoAutomatico(arrecadadorContrato.getCodigoConvenioDebitoAutomatico());

			atualizarContratoArrecadadorActionForm.setIdConvenioFichaCompensacao(arrecadadorContrato.getCodigoConvenioFichaCompensacao());

			atualizarContratoArrecadadorActionForm.setIdConvenioParcelamentoResponsavel(arrecadadorContrato
							.getCodigoConvenioParcelamentoResposavel());

			atualizarContratoArrecadadorActionForm.setNomePrimeiroResponsavel(arrecadadorContrato.getNomePrimeiroResposavel());

			atualizarContratoArrecadadorActionForm.setCpfPrimeiroResponsavel(arrecadadorContrato.getCpfPrimeiroResposavel());

			atualizarContratoArrecadadorActionForm.setNomeSegundoResponsavel(arrecadadorContrato.getNomeSegundoResposavel());

			atualizarContratoArrecadadorActionForm.setCpfSegundoResponsavel(arrecadadorContrato.getCpfSegundoResposavel());

			// Formato para a conversão de datas
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

			if(arrecadadorContrato.getIndicadorCobrancaIss() != null
							&& !arrecadadorContrato.getIndicadorCobrancaIss().toString().trim().equals("")){
				atualizarContratoArrecadadorActionForm.setIndicadorCobranca(arrecadadorContrato.getIndicadorCobrancaIss().toString());
			}
			if(arrecadadorContrato.getDataContratoInicio() != null
							&& !arrecadadorContrato.getDataContratoInicio().toString().trim().equals("")){
				atualizarContratoArrecadadorActionForm.setDtInicioContrato(formatoData.format(arrecadadorContrato.getDataContratoInicio()));
			}
			if(arrecadadorContrato.getDataContratoFim() != null && !arrecadadorContrato.getDataContratoFim().toString().trim().equals("")){
				atualizarContratoArrecadadorActionForm.setDtFimContrato(formatoData.format(arrecadadorContrato.getDataContratoFim()));
			}
			if(arrecadadorContrato.getDataContratoEncerramento() != null){
				atualizarContratoArrecadadorActionForm.setDataContratoEncerramento(formatoData.format(arrecadadorContrato
								.getDataContratoEncerramento()));
			}

			// Motivo Cancelamento
			FiltroMotivoCancelamento filtroMotivoCancelamento = new FiltroMotivoCancelamento();

			// Ordena filtro de motivo cancelamento
			filtroMotivoCancelamento.setCampoOrderBy(FiltroMotivoCancelamento.INDICADOR_USO);

			// Criado para comparação
			if(arrecadadorContrato.getContratoMotivoCancelamento() != null){
				atualizarContratoArrecadadorActionForm.setIdContratoMotivoCancelamento(""
								+ arrecadadorContrato.getContratoMotivoCancelamento().getId());
			}

			// Preenche colecao de motivos
			Collection<FiltroMotivoCancelamento> colecaoFiltroMotivoCancelamento = fachada.pesquisar(filtroMotivoCancelamento,
							ContratoMotivoCancelamento.class.getName());

			if(colecaoFiltroMotivoCancelamento == null || colecaoFiltroMotivoCancelamento.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Motivo Cancelamento");
			}else{
				ContratoMotivoCancelamento contratoMotivoCancelamento = (ContratoMotivoCancelamento) Util
								.retonarObjetoDeColecao(colecaoFiltroMotivoCancelamento);
				atualizarContratoArrecadadorActionForm.setContratoMotivoCancelamento(contratoMotivoCancelamento
								.getDescricaoMotivoCancelContrato());
				sessao.setAttribute("colecaoFiltroMotivoCancelamento", colecaoFiltroMotivoCancelamento);
			}

			sessao.setAttribute("arrecadadorContrato", arrecadadorContrato);
		}

		return retorno;
	}

}
