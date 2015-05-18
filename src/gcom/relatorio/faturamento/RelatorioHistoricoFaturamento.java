
package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

public class RelatorioHistoricoFaturamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioHistoricoFaturamento(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		String tipoFormatoRelatorio = (String) getParametro("tipoFormatoRelatorio");
		Integer idImovel = Integer.parseInt((String) getParametro("idImovel"));

		Collection colecaoCompleta = (Collection) getParametro("colecaoCompleta");

		String nomeCliente = (String) getParametro("nomeCliente");

		byte[] retorno = null;

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Imovel imovel = fachada.pesquisarImovelParaEndereco(idImovel);

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("matricula", String.valueOf(idImovel));
		parametros.put("inscricaoImovel", fachada.pesquisarInscricaoImovel(idImovel, true));
		parametros.put("enderecoImovel", imovel.getEnderecoFormatado());
		parametros.put("nomeCliente", nomeCliente);
		parametros.put("tipoFormatoRelatorio", tipoFormatoRelatorio);

		List<RelatorioHistoricoFaturamentoBean> beans = new ArrayList<RelatorioHistoricoFaturamentoBean>();

		this.addColecaoContasOrHistoricoToBeans(colecaoCompleta, beans);

		RelatorioDataSource ds = new RelatorioDataSource(beans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_HISTORICO_FATURAMENTO_IMOVEL, parametros, ds,
						Integer.parseInt(tipoFormatoRelatorio));

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_HISTORICO_FATURAMENTO_IMOVEL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	private void addColecaoContasOrHistoricoToBeans(Collection colecaoCompleta, List<RelatorioHistoricoFaturamentoBean> beans){

		if(!Util.isVazioOrNulo(colecaoCompleta)){
			for(Object object : colecaoCompleta){
				if(object instanceof Conta){
					this.addContaToBeans(object, beans);
				}else if(object instanceof ContaHistorico){
					this.addContaHistoricoToBeans(object, beans);
				}
			}
		}
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioHistoricoFaturamento", this);

	}

	/**
	 * Adiciona as contas do imóvel
	 * 
	 * @param idImovel
	 * @param beans
	 * @return
	 */
	private void addContaToBeans(Object object, List<RelatorioHistoricoFaturamentoBean> beans){

		// Collection colecaoContas = Fachada.getInstancia().consultarContasImovel(idImovel, false);

		// if(!Util.isVazioOrNulo(colecaoContas)){
		RelatorioHistoricoFaturamentoBean bean = null;

		// Conta conta = null;
		// for(Object object : colecaoContas){
		Conta conta = (Conta) object;

		String mesAno = "";
		if(!Util.isVazioOuBrancoOuZero(conta.getReferencia())){
			mesAno = Util.formatarMesAnoReferencia(conta.getReferencia());
		}

		String vencimento = "";
		if(conta.getDataVencimentoConta() != null){
			vencimento = Util.formatarData(conta.getDataVencimentoConta());
		}

		String valorAgua = "";
		if(conta.getValorAgua() != null){
			valorAgua = Util.formatarMoedaReal(conta.getValorAgua());
		}

		String valorEsgoto = "";
		if(conta.getValorEsgoto() != null){
			valorEsgoto = Util.formatarMoedaReal(conta.getValorEsgoto());
		}

		String valorDebitos = "";
		if(conta.getDebitos() != null){
			valorDebitos = Util.formatarMoedaReal(conta.getDebitos());
		}

		String valorCreditos = "";
		if(conta.getValorCreditos() != null){
			valorCreditos = Util.formatarMoedaReal(conta.getValorCreditos());
		}

		String valorImpostos = "";
		if(conta.getValorImposto() != null){
			valorImpostos = Util.formatarMoedaReal(conta.getValorImposto());
		}

		String valorTotal = "";
		if(conta.getValorTotal() != null){
			valorTotal = Util.formatarMoedaReal(conta.getValorTotal());
		}

		String situacao = "";
		if(conta.getDebitoCreditoSituacaoAtual() != null && conta.getDebitoCreditoSituacaoAtual().getDescricaoAbreviada() != null){
			situacao = conta.getDebitoCreditoSituacaoAtual().getDescricaoAbreviada();
		}

		bean = new RelatorioHistoricoFaturamentoBean(mesAno, vencimento, valorAgua, valorEsgoto, valorDebitos, valorCreditos,
						valorImpostos, valorTotal, situacao);

		beans.add(bean);
	}

	// }
	// }

	/**
	 * Adiciona as contas do histórico do imóvel
	 * 
	 * @param colecaoCompleta
	 * @param beans
	 * @return
	 */
	private void addContaHistoricoToBeans(Object object, List<RelatorioHistoricoFaturamentoBean> beans){

		// Collection colecaoContasHistorico =
		// Fachada.getInstancia().consultarContasHistoricosImovel(colecaoCompleta);
		// if(!Util.isVazioOrNulo(colecaoContasHistorico)){
		RelatorioHistoricoFaturamentoBean bean = null;

		// ContaHistorico contaHistorico = null;
		// for(Object object : colecaoContasHistorico){
		ContaHistorico contaHistorico = (ContaHistorico) object;

		String mesAno = "";
		if(!Util.isVazioOuBrancoOuZero(contaHistorico.getAnoMesReferenciaContabil())){
			mesAno = Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaContabil());
		}

		String vencimento = "";
		if(contaHistorico.getDataVencimentoConta() != null){
			vencimento = Util.formatarData(contaHistorico.getDataVencimentoConta());
		}

		String valorAgua = "";
		if(contaHistorico.getValorAgua() != null){
			valorAgua = Util.formatarMoedaReal(contaHistorico.getValorAgua());
		}

		String valorEsgoto = "";
		if(contaHistorico.getValorEsgoto() != null){
			valorEsgoto = Util.formatarMoedaReal(contaHistorico.getValorEsgoto());
		}

		String valorDebitos = "";
		if(contaHistorico.getValorDebitos() != null){
			valorDebitos = Util.formatarMoedaReal(contaHistorico.getValorDebitos());
		}

		String valorCreditos = "";
		if(contaHistorico.getValorCreditos() != null){
			valorCreditos = Util.formatarMoedaReal(contaHistorico.getValorCreditos());
		}

		String valorImpostos = "";
		if(contaHistorico.getValorImposto() != null){
			valorImpostos = Util.formatarMoedaReal(contaHistorico.getValorImposto());
		}

		String valorTotal = "";
		if(contaHistorico.getValorTotal() != null){
			valorTotal = Util.formatarMoedaReal(contaHistorico.getValorTotal());
		}

		String situacao = "";
		if(contaHistorico.getDebitoCreditoSituacaoAtual() != null
						&& contaHistorico.getDebitoCreditoSituacaoAtual().getDescricaoAbreviada() != null){
			situacao = contaHistorico.getDebitoCreditoSituacaoAtual().getDescricaoAbreviada();
		}

		bean = new RelatorioHistoricoFaturamentoBean(mesAno, vencimento, valorAgua, valorEsgoto, valorDebitos, valorCreditos,
						valorImpostos, valorTotal, situacao);

		beans.add(bean);
	}

	// }
	// }

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int i = 0;

		if(getParametro("colecaoCompleta") != null){
			Collection colecaoCompleta = (Collection) getParametro("colecaoCompleta");
			if(!Util.isVazioOrNulo(colecaoCompleta)){
				i = colecaoCompleta.size();
			}
		}

		return i;
	}

}
