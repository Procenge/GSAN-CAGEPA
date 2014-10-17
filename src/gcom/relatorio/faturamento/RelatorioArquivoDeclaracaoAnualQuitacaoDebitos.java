
package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

/**
 * [UC3014] Emitir Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 27/04/2013
 */
public class RelatorioArquivoDeclaracaoAnualQuitacaoDebitos
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public static String P_IC_BATCH = "";

	public RelatorioArquivoDeclaracaoAnualQuitacaoDebitos(Usuario usuario) throws ControladorException {

		super(usuario, (String) ParametroFaturamento.P_MODELO_RELATORIO_DECLARACAO_QUITACAO_ANUAL.executar());
	}

	public RelatorioArquivoDeclaracaoAnualQuitacaoDebitos() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Fachada fachada = Fachada.getInstancia();

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		String nomeArquivo = (String) getParametro("nomeArquivo");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer anoBaseDeclaracaoQuitacaoDebitoAnual = (Integer) getParametro("anoBaseDeclaracaoQuitacaoDebitoAnual");

		byte[] retorno = null;

		List<DeclaracaoAnualQuitacaoDebitosHelper> colecaoDeclaracaoAnualQuitacaoDebitosHelper = (ArrayList) getParametro("colecaoDeclaracaoAnualQuitacaoDebitosHelper");

		if(!Util.isVazioOrNulo(colecaoDeclaracaoAnualQuitacaoDebitosHelper)){
			
			List<RelatorioArquivoDeclaracaoAnualQuitacaoDebitosBean> colecaoRelatorioArquivoDeclaracaoAnualQuitacaoDebitosBean = new ArrayList<RelatorioArquivoDeclaracaoAnualQuitacaoDebitosBean>();
			
			for(DeclaracaoAnualQuitacaoDebitosHelper declaracaoAnualQuitacaoDebitosHelper : colecaoDeclaracaoAnualQuitacaoDebitosHelper){
				RelatorioArquivoDeclaracaoAnualQuitacaoDebitosBean relatorioArquivoDeclaracaoAnualQuitacaoDebitosBean = new RelatorioArquivoDeclaracaoAnualQuitacaoDebitosBean(declaracaoAnualQuitacaoDebitosHelper);

				colecaoRelatorioArquivoDeclaracaoAnualQuitacaoDebitosBean.add(relatorioArquivoDeclaracaoAnualQuitacaoDebitosBean);			
			}

			Map parametros = new HashMap();

			SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
			String modeloRelatorio = null;
			try{
				modeloRelatorio = (String) ParametroFaturamento.P_MODELO_RELATORIO_DECLARACAO_QUITACAO_ANUAL.executar();
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
			try{
				parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
			}catch(ControladorException e1){
				e1.printStackTrace();
				throw new TarefaException("Erro ao gerar dados para o relatorio");
			}
			parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
			try{
				parametros.put("P_INSC_EST",
								(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
				parametros.put("P_JUCOR",
								(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
			}catch(NegocioException e1){
				e1.printStackTrace();
				throw new TarefaException("Erro ao gerar dados para o relatorio");
			}

			String msg = "";
			String dataPorExtenso = "";

			Calendar dataCalendar = new GregorianCalendar();

			Integer ano = dataCalendar.get(Calendar.YEAR);
			Integer mes = dataCalendar.get(Calendar.MONTH) + 1;
			Integer dia = dataCalendar.get(Calendar.DAY_OF_MONTH);

			String diaStr = Integer.toString(dia);
			diaStr = Util.adicionarZerosEsquedaNumero(2, diaStr);

			if(modeloRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS)){
				msg = "A Companhia de Saneamento de Sergipe – DESO, declara, para os fins da Lei 12.007, 29 de julho de 2009, que o ";
				msg += "imóvel com o número de matrícula acima identificado está quite quanto às faturas relativas à prestação dos serviços de ";
				msg += "fornecimento de água e esgotamento sanitário com vencimento no ano de " + anoBaseDeclaracaoQuitacaoDebitoAnual
								+ ".";

				dataPorExtenso = "ARACAJU, " + diaStr + " DE " + Util.retornaDescricaoMes(mes).toUpperCase() + " DE " + ano + ".";

				parametros.put("imagemAssinatura", "./imagens/AssinaturaSuperintendenciaDESO.jpg");
			}else if(modeloRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MODELO_2)){
				
				msg = "Em conformidade com art. 3º da lei 12.007/2009, informamos que não há pendencia ou fatura vencida para "
								+ "esta matricula. Esta declaração substitui a comprovação da quitação das faturas mensais dos débitos "
								+ "até 31/12/" + anoBaseDeclaracaoQuitacaoDebitoAnual + ", não abrangendo os parcelamentos pendentes nas "
								+ "faturas de " + anoBaseDeclaracaoQuitacaoDebitoAnual + ".";

				dataPorExtenso = sistemaParametro.getCep().getMunicipio()+", " + diaStr + " DE " + Util.retornaDescricaoMes(mes).toUpperCase() + " DE " + ano + ".";
			}

			parametros.put("dataPorExtenso", dataPorExtenso);

			parametros.put("msg", msg);

			parametros.put("imagem", sistemaParametro.getImagemRelatorio());

			parametros.put("municipioEmpresa", sistemaParametro.getCep().getMunicipio());

			RelatorioDataSource ds = new RelatorioDataSource(colecaoRelatorioArquivoDeclaracaoAnualQuitacaoDebitosBean);

			retorno = gerarRelatorio(modeloRelatorio, parametros, ds,
							tipoFormatoRelatorio);
		}

		try{

			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS,
							idFuncionalidadeIniciada, nomeArquivo);

		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(Exception e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		String indicadorBatch = (String) getParametro(P_IC_BATCH);
		int retorno;
		if(Util.isVazioOuBranco(indicadorBatch) || indicadorBatch.equals(ConstantesSistema.ATIVO)){
			retorno = -1;
		}else{
			retorno = 0;
		}

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioArquivoDeclaracaoAnualQuitacaoDebitos", this);
	}

}
