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

package gcom.gui.cobranca;

import gcom.cadastro.cliente.*;
import gcom.cadastro.cliente.bean.ClienteImovelRelacaoHelper;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Fernanda Paiva
 * @created 12 de Janeiro de 2006
 */
public class ExibirConsultarDebitoClienteAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirDebitoCliente");

		// Mudar isso quando tiver esquema de segurança
		// HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarDebitoClienteActionForm consultarDebitoClienteActionForm = (ConsultarDebitoClienteActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Pega uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		boolean temPermissaoEmitirExtratoDeDebitosSemAcrescimo = fachada
						.verificarPermissaoEmitirExtratoDeDebitosoSemAcrescimo(usuarioLogado);
		String pIndicadorEmissaoExtratoDebitoSemAcrescimo = ConstantesSistema.NAO.toString();
		if(temPermissaoEmitirExtratoDeDebitosSemAcrescimo){
			pIndicadorEmissaoExtratoDebitoSemAcrescimo = ConstantesSistema.SIM.toString();
		}

		consultarDebitoClienteActionForm.setIndicadorIncluirAcrescimosImpontualidade(pIndicadorEmissaoExtratoDebitoSemAcrescimo);

		String codigoClienteSuperior = (String) consultarDebitoClienteActionForm.getCodigoClienteSuperior();
		String codigoCliente = (String) consultarDebitoClienteActionForm.getCodigoCliente();
		Integer tipoRelacao = null;

		if(!Util.isVazioOuBranco(codigoCliente)){

			if(!Util.isVazioOuBranco(consultarDebitoClienteActionForm.getTipoRelacao())
							&& !consultarDebitoClienteActionForm.getTipoRelacao().trim().equals("-1")){
				tipoRelacao = Integer.valueOf(consultarDebitoClienteActionForm.getTipoRelacao());
			}

			sessao.setAttribute("tipoPesquisa", "cliente");

		}else{
			sessao.setAttribute("tipoPesquisa", "clienteSuperior");
		}

		String referenciaInicial;
		String referenciaFinal;
		String dataVencimentoInicial;
		String dataVencimentoFinal;

		// seta os dados das datas digitadas ou informa datas padrão
		// no caso de não ter sido digitado nenhum dado para esses campos
		if(!Util.isVazioOuBranco(consultarDebitoClienteActionForm.getReferenciaInicial())){
			referenciaInicial = consultarDebitoClienteActionForm.getReferenciaInicial();
		}else{
			referenciaInicial = "01/0001";
		}

		if(!Util.isVazioOuBranco(consultarDebitoClienteActionForm.getReferenciaFinal())){
			referenciaFinal = consultarDebitoClienteActionForm.getReferenciaFinal();
		}else{
			referenciaFinal = "12/9999";
		}

		if(!Util.isVazioOuBranco(consultarDebitoClienteActionForm.getDataVencimentoInicial())){
			dataVencimentoInicial = consultarDebitoClienteActionForm.getDataVencimentoInicial();
		}else{
			dataVencimentoInicial = "01/01/0001";
		}

		if(!Util.isVazioOuBranco(consultarDebitoClienteActionForm.getDataVencimentoFinal())){
			dataVencimentoFinal = consultarDebitoClienteActionForm.getDataVencimentoFinal();
		}else{
			dataVencimentoFinal = "31/12/9999";
		}
		// Para auxiliar na formatação de uma data
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		String mesInicial = referenciaInicial.substring(0, 2);
		String anoInicial = referenciaInicial.substring(3, referenciaInicial.length());

		String anoMesInicial = anoInicial + mesInicial;

		String mesFinal = referenciaFinal.substring(0, 2);
		String anoFinal = referenciaFinal.substring(3, referenciaFinal.length());

		String anoMesFinal = anoFinal + mesFinal;

		Date dataVencimentoDebitoI;
		Date dataVencimentoDebitoF;

		try{
			dataVencimentoDebitoI = formatoData.parse(dataVencimentoInicial);
		}catch(ParseException ex){
			dataVencimentoDebitoI = null;
		}

		try{
			dataVencimentoDebitoF = formatoData.parse(dataVencimentoFinal);
		}catch(ParseException ex){
			dataVencimentoDebitoF = null;
		}


		Integer tipoCliente = null;
		if (consultarDebitoClienteActionForm.getCodigoCliente() != null && !consultarDebitoClienteActionForm.getCodigoCliente().equals("")) {
			if (consultarDebitoClienteActionForm.getResponsavel() != null && consultarDebitoClienteActionForm.getResponsavel().equals("1")) {
				tipoCliente = Integer.valueOf(3);
			} else {
				tipoCliente = Integer.valueOf(2);
			}
		} else {
			tipoCliente = Integer.valueOf(4);
		}
		
		// Se a pesquisa foi pelo cliente superior seta o valor 99 no tipo da
		// relação para identificar posteriormente este tipo de pesquisa (o
		// código 99 é apenas um identificador)
		if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){
			tipoRelacao = Integer.valueOf(99);
		}

		// seta valores constantes para chamar o metodo que consulta debitos do imovel
		// Integer tipoCliente = Integer.valueOf(2);
		Integer indicadorPagamento = Integer.valueOf(1);
		Integer indicadorConta = Integer.valueOf(1);
		Integer indicadorDebito = Integer.valueOf(1);
		Integer indicadorCredito = Integer.valueOf(1);
		Integer indicadorNotas = Integer.valueOf(1);
		Integer indicadorGuias = Integer.valueOf(1);
		Integer indicadorAtualizar = Integer.valueOf(1);
		Integer indicadorCalcularAcrescimosSucumbenciaAnterior = Integer.valueOf(2);

		// Obtendo dados do cliente
		if((!Util.isVazioOuBranco(codigoCliente) && Integer.parseInt(codigoCliente) > 0)
						|| (!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){

			FiltroCliente filtroCliente = new FiltroCliente();
			if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, codigoClienteSuperior));
			}else{
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, codigoCliente));
			}
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("profissao");
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("ramoAtividade");

			Collection<Cliente> colecaoClientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(!colecaoClientes.isEmpty()){
				Cliente cliente = colecaoClientes.iterator().next();

				if(cliente.getId() != null){
					if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){
						consultarDebitoClienteActionForm.setCodigoClienteSuperior("" + cliente.getId());
						consultarDebitoClienteActionForm.setCodigoCliente(null);
					}else{
						consultarDebitoClienteActionForm.setCodigoCliente("" + cliente.getId());
					}
				}
				if(cliente.getNome() != null){
					if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){
						consultarDebitoClienteActionForm.setNomeClienteSuperior(cliente.getNome());
						consultarDebitoClienteActionForm.setNomeCliente(null);
					}else{
						consultarDebitoClienteActionForm.setNomeCliente(cliente.getNome());
					}
				}
				if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() == 1){
					if(cliente.getCpfFormatado() != null){
						consultarDebitoClienteActionForm.setCpfCnpj(cliente.getCpfFormatado());
					}
				}else{
					if(cliente.getCnpjFormatado() != null){
						consultarDebitoClienteActionForm.setCpfCnpj(cliente.getCnpjFormatado());
					}
				}
				if(cliente.getProfissao() != null){
					consultarDebitoClienteActionForm.setProfissao(cliente.getProfissao().getDescricao());
				}
				if(cliente.getRamoAtividade() != null){
					consultarDebitoClienteActionForm.setRamoAtividade(cliente.getRamoAtividade().getDescricao());
				}
				if(cliente.getClienteTipo() != null){
					consultarDebitoClienteActionForm.setDescricaoTipoRelacao(cliente.getClienteTipo().getDescricao());
				}else{
					consultarDebitoClienteActionForm.setDescricaoTipoRelacao("");
				}
			}else{
				throw new ActionServletException("atencao.cliente.inexistente");
			}

			FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();

			if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){
				filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, codigoClienteSuperior));
			}else{
				filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, codigoCliente));
			}
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");

			ClienteEndereco enderecoCliente = (ClienteEndereco) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteEndereco,
							ClienteEndereco.class.getName()));

			if(enderecoCliente != null && !Util.isVazioOuBranco(enderecoCliente.getEnderecoFormatado())){

				// Seta os dados do cliente encontrado no formulario
				consultarDebitoClienteActionForm.setEnderecoCliente(enderecoCliente.getEnderecoFormatado());
			}else{
				consultarDebitoClienteActionForm.setEnderecoCliente("");
			}

			FiltroClienteFone filtroClienteFone = new FiltroClienteFone();

			if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){
				filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, codigoClienteSuperior));
			}else{
				filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, codigoCliente));
			}

			ClienteFone clienteFone = (ClienteFone) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteFone, ClienteFone.class
							.getName()));

			if(clienteFone != null && !Util.isVazioOuBranco(clienteFone.getTelefone())){
				// O telefone foi encontrado
				consultarDebitoClienteActionForm.setClienteFone(clienteFone.getTelefone());
			}else{
				consultarDebitoClienteActionForm.setClienteFone("");
			}
		}

		// Obtem os relacionamentos entre o cliente e os imóveis
		Collection<ClienteImovelRelacaoHelper> colecaoClienteImovelRelacaoHelper = null;

		if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){
			colecaoClienteImovelRelacaoHelper = fachada.obterDadosImoveisClienteRelacao(Integer.valueOf(codigoClienteSuperior));
		}else{
			colecaoClienteImovelRelacaoHelper = fachada.obterDadosImoveisClienteRelacao(Integer.valueOf(codigoCliente));
		}

		// Obtendo os débitos do cliente
		ObterDebitoImovelOuClienteHelper colecaoDebitoCliente = null;

		if((!Util.isVazioOuBranco(codigoClienteSuperior) && Integer.parseInt(codigoClienteSuperior) > 0)){

			colecaoDebitoCliente = fachada.obterDebitoImovelOuCliente(tipoCliente.intValue(), null, codigoClienteSuperior, tipoRelacao,
							anoMesInicial, anoMesFinal, dataVencimentoDebitoI, dataVencimentoDebitoF, indicadorPagamento.intValue(),
							indicadorConta.intValue(), indicadorDebito.intValue(), indicadorCredito.intValue(), indicadorNotas.intValue(),
							indicadorGuias.intValue(), indicadorAtualizar.intValue(), null, null, new Date(), ConstantesSistema.SIM, null,
							ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
							indicadorCalcularAcrescimosSucumbenciaAnterior.intValue(), null);

		}else{

			colecaoDebitoCliente = fachada.obterDebitoImovelOuCliente(tipoCliente.intValue(), null, codigoCliente, tipoRelacao,
							anoMesInicial, anoMesFinal, dataVencimentoDebitoI, dataVencimentoDebitoF, indicadorPagamento.intValue(),
							indicadorConta.intValue(), indicadorDebito.intValue(), indicadorCredito.intValue(), indicadorNotas.intValue(),
							indicadorGuias.intValue(), indicadorAtualizar.intValue(), null, null, new Date(), ConstantesSistema.SIM, null,
							ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
							indicadorCalcularAcrescimosSucumbenciaAnterior.intValue(), null);
		}

		Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoCliente.getColecaoContasValores();

		ContaValoresHelper dadosConta = null;

		BigDecimal valorConta = new BigDecimal("0.00");
		BigDecimal valorAcrescimo = new BigDecimal("0.00");
		BigDecimal valorAgua = new BigDecimal("0.00");
		BigDecimal valorEsgoto = new BigDecimal("0.00");
		BigDecimal valorDebito = new BigDecimal("0.00");
		BigDecimal valorCredito = new BigDecimal("0.00");
		BigDecimal valorImposto = new BigDecimal("0.00");

		if(!Util.isVazioOrNulo(colecaoContaValores)){
			Iterator<ContaValoresHelper> colecaoContaValoresIterator = colecaoContaValores.iterator();

			// percorre a colecao de conta somando o valor para obter um valor total
			while(colecaoContaValoresIterator.hasNext()){

				dadosConta = (ContaValoresHelper) colecaoContaValoresIterator.next();
				valorConta = valorConta.add(dadosConta.getConta().getValorTotal());
				valorAcrescimo = valorAcrescimo.add(dadosConta.getValorTotalContaValores());
				valorAgua = valorAgua.add(dadosConta.getConta().getValorAgua());
				valorEsgoto = valorEsgoto.add(dadosConta.getConta().getValorEsgoto());
				valorDebito = valorDebito.add(dadosConta.getConta().getDebitos());
				valorCredito = valorCredito.add(dadosConta.getConta().getValorCreditos());
				valorImposto = valorImposto.add(dadosConta.getConta().getValorImposto());
			}
		}

		Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoCliente.getColecaoDebitoACobrar();

		BigDecimal valorDebitoACobrar = new BigDecimal("0.00");
		DebitoACobrar dadosDebito = null;

		if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){
			Iterator<DebitoACobrar> colecaoDebitoACobrarIterator = colecaoDebitoACobrar.iterator();

			// percorre a colecao de debito a cobrar somando o valor para obter um valor total
			while(colecaoDebitoACobrarIterator.hasNext()){

				dadosDebito = (DebitoACobrar) colecaoDebitoACobrarIterator.next();
				valorDebitoACobrar = valorDebitoACobrar.add(dadosDebito.getValorTotal());
			}
		}

		Collection<CreditoARealizar> colecaoCreditoARealizar = colecaoDebitoCliente.getColecaoCreditoARealizar();

		BigDecimal valorCreditoARealizar = new BigDecimal("0.00");
		CreditoARealizar dadosCredito = null;

		if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){
			Iterator<CreditoARealizar> colecaoCreditoARealizarIterator = colecaoCreditoARealizar.iterator();

			// percorre a colecao de credito a realizar somando o valor para obter um valor total
			while(colecaoCreditoARealizarIterator.hasNext()){

				dadosCredito = (CreditoARealizar) colecaoCreditoARealizarIterator.next();
				valorCreditoARealizar = valorCreditoARealizar.add(dadosCredito.getValorTotal());
			}
		}

		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoCliente.getColecaoGuiasPagamentoValores();

		BigDecimal valorGuiaPagamento = new BigDecimal("0.00");

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){
			Iterator<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperIterator = colecaoGuiaPagamentoValores.iterator();

			// Percorre a colecao de Prestações da Guia de Pagamento somando o valor para obter o
			// total em aberto
			while(colecaoGuiaPagamentoValoresHelperIterator.hasNext()){

				GuiaPagamentoValoresHelper dadosGuiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) colecaoGuiaPagamentoValoresHelperIterator
								.next();
				valorGuiaPagamento = valorGuiaPagamento.add(dadosGuiaPagamentoValoresHelper.getValorTotalPrestacao().setScale(
								Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
			}
		}

		if((colecaoContaValores == null) && (Util.isVazioOrNulo(colecaoDebitoACobrar)) && (Util.isVazioOrNulo(colecaoCreditoARealizar))
						&& (colecaoGuiaPagamentoValores == null)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// Manda a colecao e os valores total de conta pelo request
		sessao.setAttribute("colecaoContaValores", colecaoContaValores);
		sessao.setAttribute("valorConta", Util.formatarMoedaReal(valorConta));
		sessao.setAttribute("valorAcrescimo", Util.formatarMoedaReal(valorAcrescimo));
		sessao.setAttribute("valorAgua", Util.formatarMoedaReal(valorAgua));
		sessao.setAttribute("valorEsgoto", Util.formatarMoedaReal(valorEsgoto));
		sessao.setAttribute("valorDebito", Util.formatarMoedaReal(valorDebito));
		sessao.setAttribute("valorCredito", Util.formatarMoedaReal(valorCredito));
		sessao.setAttribute("valorImposto", Util.formatarMoedaReal(valorImposto));
		sessao.setAttribute("valorContaAcrescimo", Util.formatarMoedaReal(valorConta.add(valorAcrescimo)));

		try{

			colecaoDebitoACobrar = Fachada.getInstancia().agruparDebitoACobrar(colecaoDebitoACobrar, Boolean.TRUE);
			String valorParametro = ParametroAtendimentoPublico.P_TRATAR_DEBITO_TIPO_PARCELAMENTO_AGRUPADO.executar();
			httpServletRequest.setAttribute(ConstantesSistema.DEBITOS_AGRUPADOS, valorParametro);

		}catch(ControladorException e){

			throw new ActionServletException("erro.sistema");

		}

		// Manda a colecao e o valor total de DebitoACobrar pelo request
		sessao.setAttribute("colecaoDebitoACobrar", colecaoDebitoACobrar);
		sessao.setAttribute("valorDebitoACobrar", Util.formatarMoedaReal(valorDebitoACobrar));

		// Manda a colecao e o valor total de CreditoARealizar pelo request
		sessao.setAttribute("colecaoCreditoARealizar", colecaoCreditoARealizar);
		sessao.setAttribute("valorCreditoARealizar", Util.formatarMoedaReal(valorCreditoARealizar));

		// Manda a colecao e o valor total de GuiaPagamento pelo request
		sessao.setAttribute("colecaoGuiaPagamentoValores", colecaoGuiaPagamentoValores);
		sessao.setAttribute("valorGuiaPagamento", Util.formatarMoedaReal(valorGuiaPagamento));
		sessao.setAttribute("valorTotalDebitoACobrarGuiaPagamento", Util.formatarMoedaReal(valorGuiaPagamento.add(valorDebitoACobrar)));
		sessao.setAttribute("valorTotalDocumento", Util.formatarMoedaReal(valorConta.add(valorGuiaPagamento.add(valorDebitoACobrar))));

		// Soma o valor total dos debitos e subtrai dos creditos
		BigDecimal valorTotalSemAcrescimo = valorConta.add(valorDebitoACobrar);
		valorTotalSemAcrescimo = valorTotalSemAcrescimo.add(valorGuiaPagamento);
		valorTotalSemAcrescimo = valorTotalSemAcrescimo.subtract(valorCreditoARealizar);

		BigDecimal valorTotalComAcrescimo = valorTotalSemAcrescimo.add(valorAcrescimo);

		sessao.setAttribute("valorTotalSemAcrescimo", Util.formatarMoedaReal(valorTotalSemAcrescimo));
		sessao.setAttribute("valorTotalComAcrescimo", Util.formatarMoedaReal(valorTotalComAcrescimo));

		// Manda a colecao pelo request
		sessao.setAttribute("colecaoDebitoCliente", colecaoDebitoCliente);
		sessao.setAttribute("colecaoClienteImovelRelacaoHelper", colecaoClienteImovelRelacaoHelper);

		return retorno;
	}
}
