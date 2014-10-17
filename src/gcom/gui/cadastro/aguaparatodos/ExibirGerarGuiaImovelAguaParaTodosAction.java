/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ExibirGerarGuiaImovelAguaParaTodosAction
				extends GcomAction {

	/**
	 * Este caso de uso permite alterar um valor de Agência Bancária
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 26/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarGuiaImovelAguaParaTodos");

		GerarGuiaImovelAguaParaTodosActionForm form = (GerarGuiaImovelAguaParaTodosActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		form.setFlagOperacao(form.NENHUM);

		// Desfazer a operação
		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equals("S")){
			form.setMatricula("");
			sessao.removeAttribute("colecaoContaValores");
		}

		if(form != null && httpServletRequest.getParameter("matriculaImovel") != null
						&& !httpServletRequest.getParameter("matriculaImovel").equals("")){

			form.setFlagOperacao(form.GERAR);

			Imovel imovel = null;
			try{
				imovel = fachada.pesquisarImovel(Integer.valueOf((String) httpServletRequest.getParameter("matriculaImovel")));
			}catch(NumberFormatException e){
				throw new ActionServletException("atencao.nao.numerico", null, form.getMatricula());
			}

			if(imovel != null){

				form.setId(imovel.getId().toString());

				//
				// Dados do Imóvel
				//

				// Inscrição
				String inscricaoFormatadaImovel = imovel.getInscricaoFormatada();
				form.setInscricaoFormatadaImovel(inscricaoFormatadaImovel);

				// Situação de Água
				String descricaoLigacaoAguaSituacao = "";
				LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();

				if(ligacaoAguaSituacao != null){
					descricaoLigacaoAguaSituacao = ligacaoAguaSituacao.getDescricao();
				}

				form.setDescricaoLigacaoAguaSituacao(descricaoLigacaoAguaSituacao);

				// Situação de Esgoto
				String descricaoLigacaoEsgotoSituacao = "";
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();

				if(ligacaoEsgotoSituacao != null){
					descricaoLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getDescricao();
				}

				form.setDescricaoLigacaoEsgotoSituacao(descricaoLigacaoEsgotoSituacao);

				// Perfil do Imóvel
				String descricaoImovelPerfil = "";
				ImovelPerfil imovelPerfil = imovel.getImovelPerfil();

				if(imovelPerfil != null){
					descricaoImovelPerfil = imovelPerfil.getDescricao();
				}

				form.setDescricaoImovelPerfil(descricaoImovelPerfil);

				// Endereço do Imóvel
				String enderecoImovel = fachada.pesquisarEndereco(imovel.getId());
				form.setEnderecoImovel(enderecoImovel);

				// Informações do Cliente
				Cliente clienteUsuario = null;

				Collection<ClienteImovel> colecaoClientesImovel = Fachada.getInstancia().pesquisarClientesImovel(imovel.getId());

				if((colecaoClientesImovel != null) && (!colecaoClientesImovel.isEmpty())){
					for(ClienteImovel clienteImovel : colecaoClientesImovel){
						if(clienteImovel.getClienteRelacaoTipo() != null){
							FiltroClienteRelacaoTipo filtroRelacaoTipo = new FiltroClienteRelacaoTipo();
							filtroRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.CLIENTE_RELACAO_TIPO_ID,
											clienteImovel.getClienteRelacaoTipo().getId()));

							ClienteRelacaoTipo clienteRelacaoTipo = (ClienteRelacaoTipo) Fachada.getInstancia().pesquisar(
											filtroRelacaoTipo, ClienteRelacaoTipo.class.getName()).iterator().next();

							if((clienteRelacaoTipo.getId().equals(ClienteRelacaoTipo.USUARIO))
											&& (clienteImovel.getDataFimRelacao() == null)){

								form.setClienteRelacaoTipo(clienteRelacaoTipo.getDescricao());

								if(clienteImovel.getCliente() != null){

									FiltroCliente filtroCliente = new FiltroCliente();
									filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteImovel.getCliente()
													.getId()));

									clienteUsuario = (Cliente) Fachada.getInstancia().pesquisar(filtroCliente, Cliente.class.getName())
													.iterator().next();
									break;
								}

							}

						}
					}
				}

				if(clienteUsuario != null){
					form.setClienteNome(clienteUsuario.getNome());
				}

				if(clienteUsuario.getClienteTipo() != null){

					FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
					filtroClienteTipo
									.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, clienteUsuario.getClienteTipo().getId()));
					ClienteTipo clienteTipo = (ClienteTipo) fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName()).iterator()
									.next();

					if(clienteTipo.getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
						form.setClienteCpf(clienteUsuario.getCpfFormatado());
						form.setClienteCnpj(null);
					}else{
						form.setClienteCnpj(clienteUsuario.getCnpjFormatado());
						form.setClienteCpf(null);
					}
				}

				DebitoTipo debitoTipo = fachada.consultarDebitoTipoAguaParaTodos();

				if(debitoTipo != null){
					form.setTipoDebitoId(debitoTipo.getId().toString());
					form.setTipoDebito(debitoTipo.getDescricaoFormatada());
					form.setValorPadraoTipoDebito(Util.bigDecimalParaString(debitoTipo.getValorPadrao().setScale(2,
									BigDecimal.ROUND_HALF_UP), 2));
					form.setValorLimiteTipoDebito(Util.bigDecimalParaString(debitoTipo.getValorLimite().setScale(2,
									BigDecimal.ROUND_HALF_UP), 2));
				}

				Collection<ContaValoresHelper> dadosConta = fachada.obterContasProgramaAguaParaTodos(imovel);

				if((dadosConta != null) && (!dadosConta.isEmpty())){

					BigDecimal valorContas = calcularValorTotalContas(dadosConta, form);

					BigDecimal valorGuia = fachada.calcularValorGuiaAguaParaTodos(valorContas);
					form.setValorGuia(Util.bigDecimalParaString(valorGuia.setScale(2, BigDecimal.ROUND_HALF_UP), 2));

					form.setQuantidadeContas(String.valueOf(dadosConta.size()));

					// Manda a colecao pelo request
					if(dadosConta != null && !dadosConta.isEmpty()){
						ComparatorChain sortConta = new ComparatorChain();
						sortConta.addComparator(new BeanComparator("conta.referencia"), true);
						Collections.sort((List) dadosConta, sortConta);
					}

					sessao.setAttribute("colecaoContaValores", dadosConta);

				}else{
					throw new ActionServletException("atencao.aguaparatodos.pesquisa.conta.inexistente", null, form.getMatricula());
				}

			}else{
				throw new ActionServletException("atencao.pesquisa.imovel.inexistente", null, form.getMatricula());
			}

		}

		return retorno;
	}

	private BigDecimal calcularValorTotalContas(Collection<ContaValoresHelper> dadosConta, GerarGuiaImovelAguaParaTodosActionForm form){

		int CASAS_DECIMAIS = 2;
		int TIPO_ARREDONDAMENTO = BigDecimal.ROUND_HALF_DOWN;

		BigDecimal valorTotalAgua = BigDecimal.ZERO;
		BigDecimal valorTotalEsgoto = BigDecimal.ZERO;
		BigDecimal valorTotalDebito = BigDecimal.ZERO;
		BigDecimal valorTotalCredito = BigDecimal.ZERO;
		BigDecimal valorTotalImposto = BigDecimal.ZERO;
		BigDecimal valorTotalConta = BigDecimal.ZERO;

		// Totaliza o valor de todas as contas da lista de contas
		// (ValorAgua + ValorEsgoto + ValorDebitos - ValorCreditos - ValorImpostos)
		for(ContaValoresHelper dadoConta : dadosConta){

			valorTotalAgua = valorTotalAgua.add(dadoConta.getConta().getValorAgua().setScale(CASAS_DECIMAIS, TIPO_ARREDONDAMENTO));

			valorTotalEsgoto = valorTotalEsgoto.add(dadoConta.getConta().getValorEsgoto().setScale(CASAS_DECIMAIS, TIPO_ARREDONDAMENTO));

			valorTotalDebito = valorTotalDebito.add(dadoConta.getConta().getDebitos().setScale(CASAS_DECIMAIS, TIPO_ARREDONDAMENTO));

			valorTotalCredito = valorTotalCredito
							.add(dadoConta.getConta().getValorCreditos().setScale(CASAS_DECIMAIS, TIPO_ARREDONDAMENTO));

			valorTotalImposto = valorTotalImposto.add(dadoConta.getConta().getValorImposto().setScale(CASAS_DECIMAIS, TIPO_ARREDONDAMENTO));

			valorTotalConta = valorTotalConta.add(dadoConta.getValorTotalConta().setScale(CASAS_DECIMAIS, TIPO_ARREDONDAMENTO));
			if(dadoConta.getConta().getValorImposto() != null){
				valorTotalConta = valorTotalConta.add(dadoConta.getConta().getValorImposto().setScale(CASAS_DECIMAIS, TIPO_ARREDONDAMENTO));
			}
		}

		form.setValorTotalAgua(Util.bigDecimalParaString(valorTotalAgua, CASAS_DECIMAIS));
		form.setValorTotalEsgoto(Util.bigDecimalParaString(valorTotalEsgoto, CASAS_DECIMAIS));
		form.setValorTotalDebito(Util.bigDecimalParaString(valorTotalDebito, CASAS_DECIMAIS));
		form.setValorTotalCredito(Util.bigDecimalParaString(valorTotalCredito, CASAS_DECIMAIS));
		form.setValorTotalImposto(Util.bigDecimalParaString(valorTotalImposto, CASAS_DECIMAIS));
		form.setValorTotalConta(Util.bigDecimalParaString(valorTotalConta, CASAS_DECIMAIS));

		return valorTotalConta;
	}

}
