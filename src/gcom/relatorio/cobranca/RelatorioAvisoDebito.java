
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.bean.EmitirAvisoCobrancaHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioAvisoDebito
				extends TarefaRelatorio {

	public RelatorioAvisoDebito(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_AVISO_DEBITO);
	}

	public RelatorioAvisoDebito() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		String descricaoArquivo = null;
		if(getParametro("descricaoArquivo") != null){
			descricaoArquivo = (String) getParametro("descricaoArquivo");
		}

		List<EmitirAvisoCobrancaHelper> colecaoEmitirAvisoCobrancaHelper = (ArrayList) getParametro("colecaoEmitirAvisoCobrancaHelper");
		int totalPaginas = colecaoEmitirAvisoCobrancaHelper.size();

		List<RelatorioAvisoDebitoBean> collRelatorioAvisoDebitoBean = new ArrayList<RelatorioAvisoDebitoBean>();
		List<EmitirAvisoCobrancaHelper> colecaoOrdenada = this.dividirColecaoOrdenando(colecaoEmitirAvisoCobrancaHelper);

		EmitirAvisoCobrancaHelper primeiroHelper = null;
		EmitirAvisoCobrancaHelper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(EmitirAvisoCobrancaHelper helper : colecaoOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina(pagina1++);
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina(pagina2++);
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collRelatorioAvisoDebitoBean.add(new RelatorioAvisoDebitoBean(primeiroHelper, segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collRelatorioAvisoDebitoBean.add(new RelatorioAvisoDebitoBean(primeiroHelper, segundoHelper));
		}
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// Carregar Dados para Menssagem do Documento
		parametros.put("descricaoMensagemDocumento",
						Fachada.getInstancia().pesquisarMensagemDocumentoCobrancaAcao(CobrancaAcao.AVISO_DEBITO));

		RelatorioDataSource ds = new RelatorioDataSource(collRelatorioAvisoDebitoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_DEBITO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_AVISO_DEBITO, idFuncionalidadeIniciada, descricaoArquivo);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAvisoDebito", this);
	}

	private List<EmitirAvisoCobrancaHelper> dividirColecaoOrdenando(List<EmitirAvisoCobrancaHelper> colecao){

		List<EmitirAvisoCobrancaHelper> retorno = new ArrayList<EmitirAvisoCobrancaHelper>();
		List<EmitirAvisoCobrancaHelper> colecaoTmp = colecao;
		int quantidadeItens = 0;
		int tamanhoColecao = colecaoTmp.size();
		int metadeColecao = 0;
		if(tamanhoColecao % 2 == 0){
			metadeColecao = tamanhoColecao / 2;
		}else{
			metadeColecao = (tamanhoColecao / 2) + 1;
		}
		while(quantidadeItens < metadeColecao){
			retorno.add((EmitirAvisoCobrancaHelper) colecaoTmp.get(quantidadeItens));
			if(metadeColecao + quantidadeItens < tamanhoColecao){
				retorno.add((EmitirAvisoCobrancaHelper) colecaoTmp.get(metadeColecao + quantidadeItens));
			}
			quantidadeItens++;
		}
		tamanhoColecao = 0;

		return retorno;
	}
}
