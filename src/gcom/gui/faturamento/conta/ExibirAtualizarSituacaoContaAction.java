
package gcom.gui.faturamento.conta;

import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.cliente.FiltroClienteConta;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaCategoria;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaCategoria;
import gcom.faturamento.conta.FiltroMotivoCancelamentoConta;
import gcom.faturamento.conta.FiltroMotivoRetificacaoConta;
import gcom.faturamento.conta.FiltroMotivoRevisaoConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroLeituraSituacao;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.consumo.FiltroConsumoAnormalidade;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoTipo;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite atualizar as contas Pré-Faturadas
 * [UC3035] Concluir Faturamento Contas Pré-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 22/02/2012
 */
public class ExibirAtualizarSituacaoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("atualizarSituacaoConta");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarSituacaoContaActionForm form = (AtualizarSituacaoContaActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String idContaStr = (String) sessao.getAttribute("idConta");

		Collection<Integer> colecaoIdcontas = (Collection<Integer>) sessao.getAttribute("colecaoIdcontas");

		if(Util.isVazioOuBranco(idContaStr)){
			idContaStr = httpServletRequest.getParameter("idConta");

			if(Util.isVazioOuBranco(idContaStr)){
				idContaStr = form.getIdConta();
			}
		}

		// Desabilitar Botão Anterior e Posterior
		Integer idContaAnterior = null;
		Integer idContaPosterior = null;
		Integer idContaAtual = null;
		Integer primeiroIdConta = null;
		boolean existeIDgual = false;

		if(!Util.isVazioOrNulo(colecaoIdcontas) && colecaoIdcontas.size() > 1){
			for(Integer idConta : colecaoIdcontas){
				if(idContaAnterior == null){
					primeiroIdConta = idConta;
					idContaAnterior = idConta;
					idContaAtual = idConta;

					if(Util.converterStringParaInteger(idContaStr).equals(idContaAtual)){
						existeIDgual = true;
					}
				}else{
					idContaAtual = idConta;

					if(Util.converterStringParaInteger(idContaStr).equals(idContaAtual)){
						existeIDgual = true;
					}

					if(!Util.converterStringParaInteger(idContaStr).equals(idContaAtual)){
						if(!idContaAtual.equals(idContaAnterior) && existeIDgual){
							idContaPosterior = idConta;
							break;
						}else{
							idContaAnterior = idConta;
							idContaAtual = idConta;
						}
					}
				}
			}
		}

		sessao.setAttribute("idContaAnterior", idContaAnterior);
		sessao.setAttribute("idContaPosterior", idContaPosterior);

		if(Util.isVazioOuBranco(primeiroIdConta) || Util.converterStringParaInteger(idContaStr).equals(primeiroIdConta)){
			sessao.removeAttribute("desabilitaBotaoAnterior");
		}else{
			sessao.setAttribute("desabilitaBotaoAnterior", "S");
		}

		if(Util.isVazioOuBranco(idContaPosterior)){
			sessao.removeAttribute("desabilitaBotaoProximo");
		}else{
			sessao.setAttribute("desabilitaBotaoProximo", "S");
		}

		if(Util.isVazioOuBranco(idContaStr)){
			throw new ActionServletException("atencao.conta.nao.localizada");
		}

		form.setIdConta(idContaStr);

		Integer idConta = Integer.valueOf(idContaStr);

		// Apagando variáveis da sessão
		sessao.removeAttribute("colecaoImovelSubcategorias");
		sessao.removeAttribute("colecaoClientesImovel");
		sessao.removeAttribute("colecaoClienteConta");
		sessao.removeAttribute("colecaoContaCategoria");

		// Pesquisa o Conta pelo Id
		Conta conta = (Conta) fachada.pesquisarContaPeloID(idConta);

		if(conta == null){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		sessao.setAttribute("conta", conta);

		// 1.1. Imóvel
		form.setImovel(conta.getImovel().getId().toString());
		// 1.2. Referência da Conta
		form.setDataReferenciaConta(Util.formatarMesAnoReferencia(conta.getReferencia()));
		// 1.3. Grupo Faturamento
		if(!Util.isVazioOuBranco(conta.getRota())){
			if(!Util.isVazioOuBranco(conta.getRota().getFaturamentoGrupo())){
				form.setFaturamentoGrupoDsAbreviado(conta.getRota().getFaturamentoGrupo().getDescricaoAbreviada());
			}else{
				form.setFaturamentoGrupoDsAbreviado("");
			}
		}

		// 1.4. Vencimento da Conta
		form.setDataVencimentoConta(Util.formatarData(conta.getDataVencimentoConta()));
		// 1.5. Valor da Conta
		form.setValorConta(Util.formatarMoedaReal(conta.getValorTotal()));
		// 1.6. Situação Atual
		form.setSituacaoConta(conta.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao());
		// 1.7. Dados do Imóvel
		Imovel imovel = conta.getImovel();
		if(!Util.isVazioOuBranco(imovel)){
			// 1.7.1. Inscrição
			form.setInscricaoFormatadaImovel(imovel.getInscricaoFormatada());

			if(!Util.isVazioOuBranco(conta.getRota())){
				// 1.7.2. Rota (ROTA_ID da tabela IMOVEL).
				form.setRota(conta.getRota().getId().toString());
			}else{
				form.setRota("");
			}

			// 1.7.3. Segmento (IMOV_NNSEGMENTO da tabela IMOVEL).
			if(!Util.isVazioOuBranco(conta.getImovel().getNumeroSegmento())){
				form.setSegmento(conta.getImovel().getNumeroSegmento().toString());
			}else{
				form.setSegmento("");
			}

			// 1.7.4. Situação de Água
			if(!Util.isVazioOuBranco(imovel.getLigacaoAguaSituacao())){
				form.setDescricaoLigacaoAguaSituacao(imovel.getLigacaoAguaSituacao().getDescricao());
			}else{
				form.setDescricaoLigacaoAguaSituacao("");
			}
			// 1.7.5. Situação de Esgoto
			if(!Util.isVazioOuBranco(imovel.getLigacaoEsgotoSituacao())){
				form.setDescricaoLigacaoEsgotoSituacao(imovel.getLigacaoEsgotoSituacao().getDescricao());
			}else{
				form.setDescricaoLigacaoEsgotoSituacao("");
			}
			// 1.7.6. Perfil do Imóvel
			if(!Util.isVazioOuBranco(imovel.getImovelPerfil())){
				form.setDescricaoImovelPerfil(imovel.getImovelPerfil().getDescricao());
			}else{
				form.setDescricaoImovelPerfil("");
			}
			// 1.7.7. Endereço do Imóvel
			form.setEnderecoImovel(fachada.pesquisarEndereco(imovel.getId()));
			// 1.7.8. Lista das Subcategorias e Quantidades de Economias do Imóvel
			Collection colecaoImovelSubcategorias = fachada.pesquisarCategoriasImovel(imovel.getId());
			sessao.setAttribute("colecaoImovelSubcategorias", colecaoImovelSubcategorias);
			// 1.7.9. Lista dos Clientes do Imóvel
			Collection colecaoClientesImovel = fachada.pesquisarClientesImovel(imovel.getId());
			sessao.setAttribute("colecaoClientesImovel", colecaoClientesImovel);
		}

		// 1.8. Dados da Conta
		// 1.8.1. Dados Gerais da Conta
		// 1.8.1.1. Situação da Conta
		form.setDsSitucaoDebitoCredito(conta.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao());
		// 1.8.1.2. Mês/ano de Referência da Conta
		form.setRefereciaConta(Util.formatarMesAnoReferencia(conta.getReferencia()));
		// 1.8.1.3. Mês/ano de Referência Contábil
		form.setReferenciaContabil(Util.formatarMesAnoReferencia(conta.getReferenciaContabil()));
		// 1.8.1.4. Situação da Ligação de Água
		form.setDsLigacaoAguaSituacao(conta.getLigacaoAguaSituacao().getDescricao());
		// 1.8.1.5. Situação da Ligação de Esgoto
		form.setDsLigacaoEsgotoSituacao(conta.getLigacaoEsgotoSituacao().getDescricao());
		// 1.8.1.6. Perfil do Imóvel
		form.setDsImovelPerfil(conta.getImovelPerfil().getDescricao());
		// 1.8.1.7. Vencimento da Conta
		form.setDtVencimentoConta(Util.formatarData(conta.getDataVencimentoConta()));
		// 1.8.1.8. Validade da Conta
		form.setDtValidadeConta(Util.formatarData(conta.getDataValidadeConta()));
		// 1.8.1.9. Percentual de Esgoto
		form.setPcEsgoto(conta.getPercentualEsgoto().toString());
		// 1.8.1.10. Fixo de Esgoto
		if(!Util.isVazioOuBranco(conta.getConsumoMinimoEsgoto())){
			form.setNnConsumoMinimoEsgoto(conta.getConsumoMinimoEsgoto().toString());
		}else{
			form.setNnConsumoMinimoEsgoto("");
		}

		// 1.8.1.11. Tarifa
		form.setDsConsumoTarifa(conta.getConsumoTarifa().getDescricao());
		// 1.8.1.12. Valor de Água
		form.setVlAgua(Util.formatarMoedaReal(conta.getValorAgua()));
		// 1.8.1.13. Valor de Esgoto
		form.setVlEsgoto(Util.formatarMoedaReal(conta.getValorEsgoto()));
		// 1.8.1.14. Valor dos Débitos
		form.setVlDebitos(Util.formatarMoedaReal(conta.getDebitos()));
		// 1.8.1.15. Valor dos Créditos
		form.setVlCreditos(Util.formatarMoedaReal(conta.getValorCreditos()));
		// 1.8.1.16. Valor dos Impostos
		form.setVlImpostos(Util.formatarMoedaReal(conta.getValorImposto()));
		// 1.8.1.17. Valor Total da Conta
		form.setVlTotalConta(Util.formatarMoedaReal(conta.getValorTotal()));
		// 1.8.1.18. Débito em Conta (caso a conta seja débito automático (CNTA_ICDEBITOCONTA com o
		// valor 1 (sim) na tabela CONTA), exibir com o valor “Sim”; caso contrário, exibir com o
		// valor “Não”).
		if(conta.getIndicadorDebitoConta().shortValue() == 1){
			form.setIcDebitoConta("SIM");
		}else{
			form.setIcDebitoConta("NÃO");
		}

		// 1.8.1.19. Caso a conta seja débito automático
		if(conta.getIndicadorDebitoConta().shortValue() == 1){
			DebitoAutomatico debitoAutomatico = fachada.obterObjetoDebitoAutomatico(conta.getImovel().getId());
			if(debitoAutomatico != null){
				// 1.8.1.19.1. Banco
				if(debitoAutomatico.getAgencia() != null && debitoAutomatico.getAgencia().getBanco() != null){
					form.setNmBanco(debitoAutomatico.getAgencia().getBanco().getDescricao());
				}
				// 1.8.1.19.2. Agência
				if(debitoAutomatico.getAgencia() != null){
					form.setNmAgencia(debitoAutomatico.getAgencia().getNomeAgencia());
				}
				// 1.8.1.19.3. Conta
				form.setDsIdentificacaoClienteBCO(debitoAutomatico.getIdentificacaoClienteBanco());
			}

		}

		// 1.8.2. Dados Retificação/Cancelamento/Revisão
		// 1.8.2.1. Data da Retificação
		if(!Util.isVazioOuBranco(conta.getDataRetificacao())){
			form.setDtRetificacao(Util.formatarData(conta.getDataRetificacao()));
		}else{
			form.setDtRetificacao("");
		}
		// 1.8.2.2. Motivo da Retificação
		String dsMotivoRetificacaoConta = "";

		if(!Util.isVazioOuBranco(conta.getContaMotivoRetificacao())){
			FiltroMotivoRetificacaoConta filtroMotivoRetificacaoConta = new FiltroMotivoRetificacaoConta();

			filtroMotivoRetificacaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRetificacaoConta.ID, conta
							.getContaMotivoRetificacao().getId()));
			filtroMotivoRetificacaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRetificacaoConta.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoMotivoRetificacaoConta = fachada.pesquisar(filtroMotivoRetificacaoConta, ContaMotivoRetificacao.class
							.getName());

			ContaMotivoRetificacao contaMotivoRetificacaoConta = null;

			if(!Util.isVazioOrNulo(colecaoMotivoRetificacaoConta)){
				contaMotivoRetificacaoConta = (ContaMotivoRetificacao) Util.retonarObjetoDeColecao(colecaoMotivoRetificacaoConta);

				dsMotivoRetificacaoConta = contaMotivoRetificacaoConta.getDescricaoMotivoRetificacaoConta();
			}else{
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_RETIFICACAO_CONTA");
			}
		}

		form.setDsMotivoRetificacaoConta(dsMotivoRetificacaoConta);

		// 1.8.2.3. Data do Cancelamento
		if(!Util.isVazioOuBranco(conta.getDataCancelamento())){
			form.setDtCancelamento(Util.formatarData(conta.getDataCancelamento()));
		}else{
			form.setDtCancelamento("");
		}

		// 1.8.2.4. Motivo do Cancelamento
		String dsMotivoCancelamentoConta = "";

		if(!Util.isVazioOuBranco(conta.getContaMotivoCancelamento())){
			FiltroMotivoCancelamentoConta filtroMotivoCancelamentoConta = new FiltroMotivoCancelamentoConta();

			filtroMotivoCancelamentoConta.adicionarParametro(new ParametroSimples(FiltroMotivoCancelamentoConta.ID, conta
							.getContaMotivoCancelamento().getId()));
			filtroMotivoCancelamentoConta.adicionarParametro(new ParametroSimples(FiltroMotivoCancelamentoConta.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoMotivoCancelamentoConta = fachada.pesquisar(filtroMotivoCancelamentoConta, ContaMotivoCancelamento.class
							.getName());

			ContaMotivoCancelamento contaMotivoCancelamento = null;

			if(!Util.isVazioOrNulo(colecaoMotivoCancelamentoConta)){
				contaMotivoCancelamento = (ContaMotivoCancelamento) Util.retonarObjetoDeColecao(colecaoMotivoCancelamentoConta);

				dsMotivoCancelamentoConta = contaMotivoCancelamento.getDescricaoMotivoCancelamentoConta();
			}else{
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_CANCELAMENTO_CONTA");
			}

		}

		form.setDsMotivoCancelamentoConta(dsMotivoCancelamentoConta);

		// 1.8.2.5. Data da Revisão
		if(!Util.isVazioOuBranco(conta.getDataRevisao())){
			form.setDtRevisao(Util.formatarData(conta.getDataRevisao()));
		}else{
			form.setDtRevisao("");
		}

		// 1.8.2.6. Motivo da Revisão
		String dsMotivoRevisaoConta = "";

		if(!Util.isVazioOuBranco(conta.getContaMotivoRevisao())){
			FiltroMotivoRevisaoConta filtroMotivoRevisaoConta = new FiltroMotivoRevisaoConta();

			filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRevisaoConta.ID, conta.getContaMotivoRevisao()
							.getId()));
			filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRevisaoConta.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoMotivoRevisaoConta = fachada.pesquisar(filtroMotivoRevisaoConta, ContaMotivoRevisao.class.getName());

			ContaMotivoRevisao contaMotivoRevisao = null;

			if(!Util.isVazioOrNulo(colecaoMotivoRevisaoConta)){
				contaMotivoRevisao = (ContaMotivoRevisao) Util.retonarObjetoDeColecao(colecaoMotivoRevisaoConta);

				dsMotivoRevisaoConta = contaMotivoRevisao.getDescricaoMotivoRevisaoConta();
			}else{
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_REVISAO_CONTA");
			}
		}

		form.setDsMotivoCancelamentoConta(dsMotivoRevisaoConta);

		// 1.8.2.7. Usuário Responsável pela Revisão
		// caso o usuário que efetuou a revisão seja um funcionário
		// exibir a matrícula e o nome do funcionário
		// caso contrário, ou seja, o usuário que efetuou a revisão não seja um funcionário,
		// exibir o login e o nome do usuário.
		if(!Util.isVazioOuBranco(conta.getUsuario()) && !Util.isVazioOuBranco(conta.getUsuario().getFuncionario())){
			if(conta.getUsuario().getFuncionario().getId().intValue() != 0){
				form.setUsuarioResposavelRevisao(conta.getUsuario().getFuncionario().getId().toString());
				form.setNomeUsuarioResposavelRevisao(conta.getUsuario().getFuncionario().getNome());
			}else{
				form.setUsuarioResposavelRevisao(conta.getUsuario().getLogin());
				form.setNomeUsuarioResposavelRevisao(conta.getUsuario().getNomeUsuario());
			}
		}else{
			form.setUsuarioResposavelRevisao("");
			form.setNomeUsuarioResposavelRevisao("");
		}

		// 1.8.3. Dados do Último Faturamento

		FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();

		filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, conta.getImovel().getId()));

		// Setar o mês anterior da referência da conta
		Integer anoMesReferenciaFaturamentoAnteriorMedicaoHistorico = null;

		anoMesReferenciaFaturamentoAnteriorMedicaoHistorico = fachada.obterMaiorAnoMesReferenciaAnteriorMedicaoHistorico(conta.getImovel()
						.getId(), conta.getReferencia());

		filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO,
						anoMesReferenciaFaturamentoAnteriorMedicaoHistorico));

		filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeFaturamento");
		filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraSituacaoAtual");

		Collection colecaoMedicaoHistorio = (Collection) fachada.pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class.getName());

		MedicaoHistorico medicaoHistorico = null;

		if(!Util.isVazioOrNulo(colecaoMedicaoHistorio)){
			medicaoHistorico = (MedicaoHistorico) Util.retonarObjetoDeColecao(colecaoMedicaoHistorio);

			// 1.8.3.1. Leitura Anterior
			if(medicaoHistorico.getLeituraAtualFaturamento() != null){

				form.setNnLeituraAnteriorFaturamento(medicaoHistorico.getLeituraAtualFaturamento().toString());

			}
			// 1.8.3.2. Data da Leitura Anterior
			form.setDtLeiruraAnteriorFaturamento(Util.formatarData(medicaoHistorico.getDataLeituraAtualFaturamento()));
			// 1.8.3.3. Anormalidade de Leitura Anterior
			String dsLeituraAnormalidade = "";

			if(!Util.isVazioOuBranco(medicaoHistorico.getLeituraAnormalidadeFaturamento())){
				dsLeituraAnormalidade = medicaoHistorico.getLeituraAnormalidadeFaturamento().getDescricao();
			}
			form.setDsLeituraAnormalidade(dsLeituraAnormalidade);

			// 1.8.3.4. Dias de Consumo Anterior
			String diasConsumoAnterior = "";

			if(!Util.isVazioOuBranco(medicaoHistorico.getDataLeituraAnteriorFaturamento())
							&& !Util.isVazioOuBranco(medicaoHistorico.getDataLeituraAtualFaturamento())){
				diasConsumoAnterior = String.valueOf(Util.obterQuantidadeDiasEntreDuasDatas(medicaoHistorico
								.getDataLeituraAnteriorFaturamento(), medicaoHistorico.getDataLeituraAtualFaturamento()));
			}

			form.setDiasConsumoAnterior(diasConsumoAnterior);

			// 1.8.3.6. Tipo de Leitura Anterior
			String dsLeituraSituacao = "";

			if(!Util.isVazioOuBranco(medicaoHistorico.getLeituraSituacaoAtual())){
				FiltroLeituraSituacao filtroLeituraSituacao = new FiltroLeituraSituacao();

				filtroLeituraSituacao.adicionarParametro(new ParametroSimples(FiltroLeituraSituacao.ID, medicaoHistorico
								.getLeituraSituacaoAtual().getId()));

				filtroLeituraSituacao.adicionarParametro(new ParametroSimples(FiltroLeituraSituacao.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection colecaoLeituraSituacaoAtual = fachada.pesquisar(filtroLeituraSituacao, LeituraSituacao.class.getName());

				LeituraSituacao leituraSituacao = null;

				if(!Util.isVazioOrNulo(colecaoLeituraSituacaoAtual)){
					leituraSituacao = (LeituraSituacao) Util.retonarObjetoDeColecao(colecaoLeituraSituacaoAtual);
					dsLeituraSituacao = leituraSituacao.getDescricao();
				}
			}

			form.setDsLeituraSituacao(dsLeituraSituacao);

		}

		// 1.8.3.5. Anormalidade de Consumo Anterior
		// Setar o mês anterior da referência da conta
		Integer anoMesReferenciaFaturamentoAnteriorConsumoHistorico = null;

		anoMesReferenciaFaturamentoAnteriorConsumoHistorico = fachada.obterMaiorAnoMesReferenciaAnteriorConsumoHistorico(conta.getImovel()
						.getId(), conta.getReferencia());

		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, imovel.getId()));
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.ANO_MES_FATURAMENTO,
						anoMesReferenciaFaturamentoAnteriorConsumoHistorico));
		filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("consumoAnormalidade");

		Collection colecaoConsumoHistorico = fachada.pesquisar(filtroConsumoHistorico, ConsumoHistorico.class.getName());

		ConsumoHistorico consumoHistorico = new ConsumoHistorico();
		String dsConsumoAnormalidade = "";

		if(!Util.isVazioOrNulo(colecaoConsumoHistorico)){
			consumoHistorico = (ConsumoHistorico) Util.retonarObjetoDeColecao(colecaoConsumoHistorico);

			FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();

			filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID, consumoHistorico.getId()));

			filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection<ConsumoAnormalidade> consumoAnormalidades = fachada.pesquisar(filtroConsumoAnormalidade, ConsumoAnormalidade.class
							.getName());

			ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade();

			if(!Util.isVazioOrNulo(consumoAnormalidades)){
				consumoAnormalidade = (ConsumoAnormalidade) Util.retonarObjetoDeColecao(consumoAnormalidades);
				if(!Util.isVazioOuBranco(consumoAnormalidade.getDescricao())){
					dsConsumoAnormalidade = consumoAnormalidade.getDescricao();
				}
			}
		}

		form.setDsConsumoAnormalidade(dsConsumoAnormalidade);

		// 1.8.3.7. Tipo de Consumo Anterior
		String dsConsumoTipo = "";

		if(!Util.isVazioOrNulo(colecaoConsumoHistorico)){
			consumoHistorico = (ConsumoHistorico) Util.retonarObjetoDeColecao(colecaoConsumoHistorico);
			FiltroConsumoTipo filtroConsumoTipo = new FiltroConsumoTipo();
			filtroConsumoTipo.adicionarParametro(new ParametroSimples(FiltroConsumoTipo.CODIGO, consumoHistorico.getConsumoTipo().getId()));

			Collection colecaoConsumoTipo = this.getFachada().pesquisar(filtroConsumoTipo, ConsumoTipo.class.getName());

			ConsumoTipo consumoTipo = null;

			if(!Util.isVazioOrNulo(colecaoConsumoTipo)){
				consumoTipo = (ConsumoTipo) Util.retonarObjetoDeColecao(colecaoConsumoTipo);
				dsConsumoTipo = consumoTipo.getDescricao();
			}
		}

		form.setDsConsumoTipo(dsConsumoTipo);

		// 1.8.4. Clientes da Conta
		// seleciona a partir da tabela CLIENTE_CONTA com CNTA_ID=CNTA_ID da tabela CONTA,
		// ordenando pelo tipo de relação (CRTP_ID) em ordem crescente):

		FiltroClienteConta filtroClienteConta = new FiltroClienteConta();

		filtroClienteConta.adicionarParametro(new ParametroSimples(FiltroClienteConta.CONTA_ID, conta.getId()));
		filtroClienteConta.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteConta.CLIENTE);
		filtroClienteConta.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteConta.CLIENTE_TIPO);
		filtroClienteConta.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteConta.CLIENTE_RELACAO_TIPO);
		filtroClienteConta.setCampoOrderBy(FiltroClienteConta.CLIENTE_RELACAO_TIPO);

		Collection<ClienteConta> colecaoClienteConta = fachada.pesquisar(filtroClienteConta, ClienteConta.class.getName());

		sessao.setAttribute("colecaoClienteConta", colecaoClienteConta);

		// 1.8.5. Categorias da Conta
		FiltroContaCategoria filtroContaCategoria = new FiltroContaCategoria();

		filtroContaCategoria.adicionarParametro(new ParametroSimples(FiltroContaCategoria.CONTA_ID, conta.getId()));
		filtroContaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroContaCategoria.CATEGORIA);
		filtroContaCategoria.setCampoOrderBy(FiltroContaCategoria.CATEGORIA_ID);

		Collection<ContaCategoria> colecaoContaCategoria = fachada.pesquisar(filtroContaCategoria, ContaCategoria.class.getName());

		sessao.setAttribute("colecaoContaCategoria", colecaoContaCategoria);

		return retorno;
	}
}
