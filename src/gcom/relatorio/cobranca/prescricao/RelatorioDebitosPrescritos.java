
package gcom.relatorio.cobranca.prescricao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * [UC3137] Comandar Prescri��o de D�bito
 * 
 * @author Anderson Italo
 * @date 27/02/2014
 */
public class RelatorioDebitosPrescritos
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioDebitosPrescritos(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_DEBITOS_PRESCRITOS);
	}

	@Deprecated
	public RelatorioDebitosPrescritos() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		int tipoFormatoRelatorio = TarefaRelatorio.TIPO_PDF;
		String parametrosFiltro = (String) getParametro("parametrosFiltro");
		Collection<DadosRelatorioPrescicaoHelper> colecaoDadosRelatorioPrescricaoHelper = (Collection<DadosRelatorioPrescicaoHelper>) getParametro("colecaoDadosRelatorioPrescricaoHelper");

		List<RelatorioDebitosPrescritosBean> colecaoBeansRelatorioDebitosPrescritos = new ArrayList<RelatorioDebitosPrescritosBean>();
		List<SubRelatorioDebitosPrescritosContaBean> colecaoBeansContasRelatorioDebitosPrescritos = null;
		List<SubRelatorioDebitosPrescritosGuiaPagamentoBean> colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos = null;

		for(DadosRelatorioPrescicaoHelper helperRelatorio : colecaoDadosRelatorioPrescricaoHelper){

			RelatorioDebitosPrescritosBean beanPrincipalImovel = new RelatorioDebitosPrescritosBean();
			colecaoBeansContasRelatorioDebitosPrescritos = new ArrayList<SubRelatorioDebitosPrescritosContaBean>();
			colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos = new ArrayList<SubRelatorioDebitosPrescritosGuiaPagamentoBean>();

			// Localidade
			beanPrincipalImovel.setIdLocalidade(helperRelatorio.getIdLocalidade());
			beanPrincipalImovel.setNomeLocalidade(helperRelatorio.getNomeLocalidade());

			// Inscri��o
			beanPrincipalImovel.setInscricao(helperRelatorio.getInscricao());

			// Matr�cula
			beanPrincipalImovel.setMatriculaFormatada(helperRelatorio.getMatriculaFormatada());
			beanPrincipalImovel.setIdImovel(helperRelatorio.getIdImovel());

			// Endere�o
			beanPrincipalImovel.setEnderecoImovel(helperRelatorio.getEnderecoImovel());

			// Situa��o de �gua
			beanPrincipalImovel.setIdLigacaoAguaSituacao(helperRelatorio.getIdLigacaoAguaSituacao());

			// Situa��o de Esgoto
			beanPrincipalImovel.setIdLigacaoEsgotoSituacao(helperRelatorio.getIdLigacaoEsgotoSituacao());

			// Categoria
			beanPrincipalImovel.setCategoria(helperRelatorio.getCategoria());
			
			// Para cada grupo de contas do im�vel, exibir os dados das contas
			if(!Util.isVazioOrNulo(helperRelatorio.getColecaoContasPrescricao())){
				
				for(Conta conta : helperRelatorio.getColecaoContasPrescricao()){

					SubRelatorioDebitosPrescritosContaBean beanSubConta = new SubRelatorioDebitosPrescritosContaBean();

					// Refer�ncia da Conta
					beanSubConta.setReferencia(conta.getReferenciaFormatada());

					// Data de Vencimento da Conta
					beanSubConta.setDataVencimento(Util.formatarData(conta.getDataVencimentoConta()));

					// Valor da Conta
					beanSubConta.setValor(conta.getValorTotalContaBigDecimal());

					colecaoBeansContasRelatorioDebitosPrescritos.add(beanSubConta);
				}

				beanPrincipalImovel.setarBeansContas(colecaoBeansContasRelatorioDebitosPrescritos);
			}
			

			// Totais de Contas do Im�vel
			// Qtde.Contas
			beanPrincipalImovel.setValorTotalContas(helperRelatorio.getValorTotalContas());
			
			// Vl.Contas
			beanPrincipalImovel.setQuantidadeContas(helperRelatorio.getQuantidadeContas());

			// Para cada grupo de presta��es de guias de pagamento do im�vel, exibir os dados das
			// presta��es
			
			if(!Util.isVazioOrNulo(helperRelatorio.getColecaoDadosGuiasPagamentoPrescricao())){
				
				for(Object[] guiaPagamentoPrestacao : helperRelatorio.getColecaoDadosGuiasPagamentoPrescricao()){

					SubRelatorioDebitosPrescritosGuiaPagamentoBean beanSubGuiaPagamento = new SubRelatorioDebitosPrescritosGuiaPagamentoBean();

					// Guia
					beanSubGuiaPagamento.setIdGuia(guiaPagamentoPrestacao[0].toString());

					// Presta��o
					beanSubGuiaPagamento.setPrestacao(guiaPagamentoPrestacao[2].toString());

					// Data de Vencimento da Presta��o
					beanSubGuiaPagamento.setDataVencimento(Util.formatarData((Date) guiaPagamentoPrestacao[4]));

					// Valor da Presta��o
					beanSubGuiaPagamento.setValor(new BigDecimal(guiaPagamentoPrestacao[3].toString()));

					colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos.add(beanSubGuiaPagamento);
				}

				beanPrincipalImovel.setarBeansGuias(colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos);
			}

			// Totais de Guias do Im�vel
			// Qtde.Guias
			beanPrincipalImovel.setValorTotalGuias(helperRelatorio.getValorTotalGuias());

			// Vl.Presta��es
			beanPrincipalImovel.setQuantidadeGuias(helperRelatorio.getQuantidadeGuias());

			colecaoBeansRelatorioDebitosPrescritos.add(beanPrincipalImovel);
		}

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		Map parametros = new HashMap();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("parametrosFiltro", parametrosFiltro);

		RelatorioDataSource ds = new RelatorioDataSource(colecaoBeansRelatorioDebitosPrescritos);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_DEBITOS_PRESCRITOS, parametros, ds, tipoFormatoRelatorio);

		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_DEBITOS_PRESCRITOS, idFuncionalidadeIniciada,
							"RELA��O DOS IM�VEIS COM D�BITO PRESCRITO");
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}

		return retorno;
	}


	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioDebitosPrescritos", this);
	}
}