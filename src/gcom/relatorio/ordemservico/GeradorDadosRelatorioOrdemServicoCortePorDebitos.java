/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoCortePorDebitosBean;
import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoCortePorDebitosDetailBean;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author isilva
 */
public class GeradorDadosRelatorioOrdemServicoCortePorDebitos
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "CORTE POR DÉBITOS";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoCortePorDebitos() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_CORTE_POR_DEBITOS);
		addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO);
	}

	/**
	 * @author isilva
	 * @param os
	 *            A ordem de serviço
	 * @return Uma coleção com os dados para o detalhe dos relatórios de acordo com o tipo de
	 *         servico
	 * @throws GeradorRelatorioOrdemServicoException
	 */
	public void gerarDetalhes(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		Collection<RelatorioOrdemServicoCortePorDebitosBean> colecao = new ArrayList<RelatorioOrdemServicoCortePorDebitosBean>();

		Integer QTD_MAXIMA_OCORRENCIA = 99;
		Integer QTD_MAXIMA_LINHA = 5;

		if(dadosRelatorio.getIdImovel() != null){
			try{
				RelatorioOrdemServicoCortePorDebitosBean relatorioOrdemServicoCortePorDebitosBean = new RelatorioOrdemServicoCortePorDebitosBean();

				Date dataAtual = new Date();
				Integer anoMesVencimentoFinal = Util.formataAnoMes(dataAtual);

				SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

				ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = getControladorCobranca().obterDebitoImovelOuCliente(
								ConstantesSistema.SIM.intValue(), dadosRelatorio.getIdImovel(), null, null, "190001",
								anoMesVencimentoFinal.toString(), Util.criarData(1, 1, 1900), dataAtual, ConstantesSistema.SIM.intValue(),
								ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(),
								ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(), true,
								sistemaParametro, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);

				Integer qtdeDebitos = Integer.valueOf("0");
				BigDecimal valorDebitos = new BigDecimal("0.00");

				String REF_ANT = "";
				BigDecimal valorSomatorioDebitos = new BigDecimal("0.00");

				int qtd = 0;

				Collection<RelatorioOrdemServicoCortePorDebitosDetailBean> colecaoDetailBean = new ArrayList<RelatorioOrdemServicoCortePorDebitosDetailBean>();

				// Contas
				if(obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel() != null
								&& !obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().isEmpty()){
					qtdeDebitos = qtdeDebitos + obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().size();

					Iterator colecaoContaValoresIterator = obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().iterator();

					while(colecaoContaValoresIterator.hasNext()){

						ContaValoresHelper contaValoresHelper = (ContaValoresHelper) colecaoContaValoresIterator.next();

						qtd++;

						if(qtd == (QTD_MAXIMA_OCORRENCIA + 1)){

							REF_ANT = "REF. ANT";
						}

						if(qtd > QTD_MAXIMA_OCORRENCIA){

							valorSomatorioDebitos = valorSomatorioDebitos.add(contaValoresHelper.getValorTotalConta());
						}

						valorDebitos = valorDebitos.add(contaValoresHelper.getValorTotalConta());

					}

					relatorioOrdemServicoCortePorDebitosBean.setQtdeDebitosVencidos(qtdeDebitos.toString());
					relatorioOrdemServicoCortePorDebitosBean.setValorDebitosVencidos(Util.formatarMoedaReal(valorDebitos));

					int qtdBlocosConsultas = Util.dividirArredondarResultado(obterDebitoImovelOuClienteHelper
									.getColecaoContasValoresImovel().size(), QTD_MAXIMA_LINHA, BigDecimal.ROUND_UP);

					int quantidadeInserida = 0;

					for(int i = 0; i < qtdBlocosConsultas; i++){

						boolean sair = false;

						List<ContaValoresHelper> blocoIds = new ArrayList<ContaValoresHelper>();
						blocoIds.addAll(Util.obterSubListParaPaginacao((List) obterDebitoImovelOuClienteHelper
										.getColecaoContasValoresImovel(), i, QTD_MAXIMA_LINHA));

						RelatorioOrdemServicoCortePorDebitosDetailBean relatorioDetailBean = new RelatorioOrdemServicoCortePorDebitosDetailBean();
						if(!Util.isVazioOuBranco(blocoIds)){

							int posicao = 1;

							for(ContaValoresHelper contaValoresHelper : blocoIds){

								if(quantidadeInserida == QTD_MAXIMA_OCORRENCIA){
									setarDadosDetailBean(relatorioDetailBean, REF_ANT, valorSomatorioDebitos, posicao);

									sair = true;
									break;
								}else{
									// SubRelatorio do SubRelatorio
									setarDadosDetailBean(relatorioDetailBean, contaValoresHelper.getFormatarAnoMesParaMesAno(),
													contaValoresHelper.getValorTotalConta(), posicao);
								}
								quantidadeInserida++;
								posicao++;
							}

							colecaoDetailBean.add(relatorioDetailBean);
						}

						if(sair){
							break;
						}
					}

				}

				relatorioOrdemServicoCortePorDebitosBean.setarBeansDetalheCortePorDebitos(colecaoDetailBean);
				colecao.add(relatorioOrdemServicoCortePorDebitosBean);

			}catch(NumberFormatException e){
				e.printStackTrace();
				throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e, e.getMessage());
			}catch(ControladorException e){
				e.printStackTrace();
				throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e, e.getMessage());
			}
		}

		if(Util.isVazioOrNulo(colecao)){
			RelatorioOrdemServicoCortePorDebitosBean relatorioOrdemServicoCortePorDebitosBean = new RelatorioOrdemServicoCortePorDebitosBean();
			colecao.add(relatorioOrdemServicoCortePorDebitosBean);
		}

		dadosRelatorio.setarBeansDetalheEstrutura(colecao);
	}

	private void setarDadosDetailBean(RelatorioOrdemServicoCortePorDebitosDetailBean relatorioDetailBean, String referencia,
					BigDecimal valor, int coluna){

		switch(coluna){
			case 1:
				if(!Util.isVazioOuBranco(referencia)){
					relatorioDetailBean.setReferencia1(referencia);
				}

				if(valor != null){
					relatorioDetailBean.setValor1(Util.formatarMoedaReal(valor));
				}
				break;
			case 2:
				if(!Util.isVazioOuBranco(referencia)){
					relatorioDetailBean.setReferencia2(referencia);
				}

				if(valor != null){
					relatorioDetailBean.setValor2(Util.formatarMoedaReal(valor));
				}
				break;
			case 3:
				if(!Util.isVazioOuBranco(referencia)){
					relatorioDetailBean.setReferencia3(referencia);
				}

				if(valor != null){
					relatorioDetailBean.setValor3(Util.formatarMoedaReal(valor));
				}
				break;
			case 4:
				if(!Util.isVazioOuBranco(referencia)){
					relatorioDetailBean.setReferencia4(referencia);
				}

				if(valor != null){
					relatorioDetailBean.setValor4(Util.formatarMoedaReal(valor));
				}
				break;
			case 5:
				if(!Util.isVazioOuBranco(referencia)){
					relatorioDetailBean.setReferencia5(referencia);
				}

				if(valor != null){
					relatorioDetailBean.setValor5(Util.formatarMoedaReal(valor));
				}
				break;

			default:
				break;
		}
	}

	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirCodigoBarras("false");
	}
}
