
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
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

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class RelatorioContasReceberValoresCorrigidos
				extends TarefaRelatorio {

	public RelatorioContasReceberValoresCorrigidos(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);

	}

	public RelatorioContasReceberValoresCorrigidos() {

		super(null, ConstantesRelatorios.RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS);

	}

	public RelatorioContasReceberValoresCorrigidos(Usuario usuario) {

		super(usuario, null);
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		String imovel = (String) getParametro("matriculaImovel");

		Integer referencia = (Integer) getParametro("referencia");

		Integer matriculaImovel = null;
		if(imovel != null && !imovel.trim().equals("")){
			matriculaImovel = Integer.valueOf(imovel);
		}

		Long quantidade = Fachada.getInstancia().quantidadeRegistrosRelatorioContasReceberValoresCorrigidos(matriculaImovel, referencia);

		return quantidade.intValue();
	}

	@Override
	public Object executar() throws TarefaException{

		String imovel = (String) getParametro("matriculaImovel");

		Integer matriculaImovel = null;
		if(imovel != null && !imovel.trim().equals("")){
			matriculaImovel = Integer.valueOf(imovel);
		}

		Integer referenciaConta = (Integer) getParametro("referencia");

		List<Object[]> listaObjetos = Fachada.getInstancia().gerarRelatorioContasReceberValoresCorrigidos(matriculaImovel, referenciaConta);

		List<RelatorioContasReceberValoresCorrigidosBean> lista = new ArrayList<RelatorioContasReceberValoresCorrigidosBean>();

		String referenciaInicial = "01/0001";
		String referenciaFinal = "12/9999";
		String dataVencimentoInicial = "01/01/0001";
		String dataVencimentoFinal = "31/12/9999";

		// Para auxiliar na formatação de uma data
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		String mesInicial = referenciaInicial.substring(0, 2);
		String anoInicial = referenciaInicial.substring(3, referenciaInicial.length());
		String anoMesInicial = anoInicial + mesInicial;
		String mesFinal = referenciaFinal.substring(0, 2);
		String anoFinal = referenciaFinal.substring(3, referenciaFinal.length());
		String anoMesFinal = anoFinal + mesFinal;

		int indicadorCalcularAcrescimosSucumbenciaAnterior = 2;

		Date dataVencimentoDebitoI = null;
		Date dataVencimentoDebitoF = null;

		try{
			dataVencimentoDebitoI = formatoData.parse(dataVencimentoInicial);
			dataVencimentoDebitoF = formatoData.parse(dataVencimentoFinal);

		}catch(ParseException ex){
		}
		// seta valores constantes para chamar o metodo que consulta debitos do imovel
		Integer tipoImovel = Integer.valueOf(1);
		Integer indicadorPagamento = Integer.valueOf(1);
		Integer indicadorConta = Integer.valueOf(1);
		Integer indicadorDebito = Integer.valueOf(1);
		Integer indicadorCredito = Integer.valueOf(1);
		Integer indicadorNotas = Integer.valueOf(1);
		Integer indicadorGuias = Integer.valueOf(1);
		Integer indicadorAtualizar = Integer.valueOf(1);
		Short indicadorConsiderarPagamentoNaoClassificado = 1;

		for(Object obj : listaObjetos){

			Object[] arrayObj = (Object[]) obj;

			String cdRegional = (arrayObj[0] != null ? arrayObj[0].toString() : "");

			String cdLocal = (arrayObj[1] != null ? arrayObj[1].toString() : "");
			String idImovel = (arrayObj[2] != null ? arrayObj[2].toString() : "");
			String abreviacaoLogadouro = (arrayObj[3] != null ? arrayObj[3].toString() : "");
			String abreviacaoTituloLogradouro = (arrayObj[4] != null ? arrayObj[4].toString() : "");
			String numeroLogradouroString = (arrayObj[5] != null ? arrayObj[5].toString() : "");
			String numeroImovel = (arrayObj[6] != null ? arrayObj[6].toString() : "");
			String numeroBairro = (arrayObj[7] != null ? arrayObj[7].toString() : "");
			String numeroMunicipio = (arrayObj[8] != null ? arrayObj[8].toString() : "");
			String numeroCep = (arrayObj[9] != null ? arrayObj[9].toString() : "");
			String descricaoComplementoEndereco = (arrayObj[10] != null ? arrayObj[10].toString() : "");
			String descricaoLigacaoAguaSituacao = (arrayObj[11] != null ? arrayObj[11].toString() : "");
			String quantidade = (arrayObj[12] != null ? arrayObj[12].toString() : "");
			String valorConta = (arrayObj[13] != null ? arrayObj[13].toString() : "");

			String referencia = (arrayObj[14] != null ? arrayObj[14].toString() : "");

			Imovel im = (Imovel) Fachada.getInstancia().pesquisar(Integer.valueOf(idImovel), Imovel.class);

			RelatorioContasReceberValoresCorrigidosBean relatorio = new RelatorioContasReceberValoresCorrigidosBean(cdRegional, cdLocal,
							idImovel, abreviacaoLogadouro, abreviacaoTituloLogradouro, numeroLogradouroString, numeroImovel, numeroBairro,
							numeroMunicipio, numeroCep, descricaoComplementoEndereco, descricaoLigacaoAguaSituacao, quantidade, valorConta,
							im, referencia);

			ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = Fachada.getInstancia().obterDebitoImovelOuCliente(tipoImovel.intValue(),
							idImovel, null, null, anoMesInicial, anoMesFinal, dataVencimentoDebitoI, dataVencimentoDebitoF,
							indicadorPagamento.intValue(), indicadorConta.intValue(), indicadorDebito.intValue(),
							indicadorCredito.intValue(), indicadorNotas.intValue(), indicadorGuias.intValue(),
							indicadorAtualizar.intValue(), null, null, new Date(), ConstantesSistema.SIM,
							indicadorConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM, ConstantesSistema.SIM,
							ConstantesSistema.SIM, indicadorCalcularAcrescimosSucumbenciaAnterior, null);

			Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();

			BigDecimal valorMulta = BigDecimal.ZERO;
			BigDecimal valorJurosMora = BigDecimal.ZERO;
			BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;

			for(ContaValoresHelper contaHelper : colecaoContaValores){
				if(contaHelper.getConta().getReferencia() == Integer.parseInt(referencia)){

					valorMulta = valorMulta.add(contaHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
					valorJurosMora = valorJurosMora.add(contaHelper.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
					valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaHelper.getValorAtualizacaoMonetaria().setScale(
									Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

				}

			}

			BigDecimal valorTotal = new BigDecimal(Double.valueOf(relatorio.getValorConta()));

			valorTotal = valorTotal.add(valorMulta);
			valorTotal = valorTotal.add(valorJurosMora);
			valorTotal = valorTotal.add(valorAtualizacaoMonetaria);

			NumberFormat decimal = DecimalFormat.getNumberInstance();
			decimal.setMaximumFractionDigits(Parcelamento.CASAS_DECIMAIS);

			relatorio.setValorConta(decimal.format(valorTotal.doubleValue()));

			lista.add(relatorio);

		}

		RelatorioDataSource ds = new RelatorioDataSource(lista);

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("nomeEmpresa", sistemaParametro.getNomeEmpresa());

		parametros.put("tipoFormatoRelatorio", "R0610");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// exporta o relatório em pdf e retorna o array de bytes

		if(tipoFormatoRelatorio == TarefaRelatorio.TIPO_XLS){
			File template = (File) getParametro("template");
			Map beans = new HashMap();
			ByteArrayOutputStream retorno = new ByteArrayOutputStream();

			beans.put("dataHoraGeracao", Util.formatarData(new Date()));

			beans.put("colecaoRetorno", this.getListaFormatada(lista));

			try{
				FileInputStream fileInput = new FileInputStream(template);

				XLSTransformer transformer = new XLSTransformer();
				HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, beans);
				this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioContasReceberValoresCorrigidosTemplate.xls");

				FileInputStream inputStream = new FileInputStream("relatorioContasReceberValoresCorrigidosTemplate.xls");
				byte[] temp = new byte[1024];
				int numBytesRead = 0;

				while((numBytesRead = inputStream.read(temp, 0, 1024)) != -1){
					retorno.write(temp, 0, numBytesRead);
				}

				inputStream.close();
				inputStream = null;

			}catch(FileNotFoundException e){
				throw new TarefaException("atencao.planilha_template_nao_encontrado ", e);
			}catch(IOException e1){
				throw new TarefaException("", e1);
			}
			try{
				persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS,
								getIdFuncionalidadeIniciada(), null);
			}catch(ControladorException e){
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relatório no sistema", e);
			}

			return retorno.toByteArray();
		}else{
			byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS, parametros, ds,
							tipoFormatoRelatorio);

			try{
				persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS, getIdFuncionalidadeIniciada(),
								null);
			}catch(ControladorException e){
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relatório no sistema", e);
			}

			return retorno;
		}
	}

	private List<RelatorioContasReceberValoresCorrigidosBean> getListaFormatada(List<RelatorioContasReceberValoresCorrigidosBean> lista){

		List<RelatorioContasReceberValoresCorrigidosBean> beans = new ArrayList<RelatorioContasReceberValoresCorrigidosBean>();

		for(RelatorioContasReceberValoresCorrigidosBean bean : lista){
			bean.setLogradouroFormatado(bean.getAbreviacaoLogradouro() + " " + bean.getNumeroLogradouroString());
			bean.setCpfOrCnpj(bean.getCpf() != null ? bean.getCpf() : bean.getCnpj());

			beans.add(bean);
		}

		return beans;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContasReceberValoresCorrigidos", this);

	}

	public void novaPlanilhaTipoHSSF(HSSFWorkbook hSSFWorkbook, String destFileName) throws IOException{

		Workbook wb = (Workbook) hSSFWorkbook;
		FileOutputStream fileOut = new FileOutputStream(destFileName);
		wb.write(fileOut);
		fileOut.close();
	}

}
