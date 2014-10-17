
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.EmitirArquivoPdfAvisoCorteHelper;
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

/**
 * @author Josenildo Neves
 * @date 23/05/2013
 */
public class RelatorioAvisoCorteModelo5
				extends TarefaRelatorio {

	public RelatorioAvisoCorteModelo5(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_AVISO_CORTE_ARQUIVO_PDF_MODELO_5);
	}

	public RelatorioAvisoCorteModelo5() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{


		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = TarefaRelatorio.TIPO_PDF;
		String descricaoArquivo = (String) getParametro("descricaoArquivo");

		List<EmitirArquivoPdfAvisoCorteHelper> colecaoHelperOrdenada = (ArrayList) getParametro("colecaoHelperOrdenada");

		List<RelatorioAvisoCorteModelo5Bean> collRelatorioAvisoCorteBean = new ArrayList<RelatorioAvisoCorteModelo5Bean>();

		EmitirArquivoPdfAvisoCorteHelper primeiroHelper = null;
		EmitirArquivoPdfAvisoCorteHelper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;
		int totalPaginas = colecaoHelperOrdenada.size();

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(EmitirArquivoPdfAvisoCorteHelper helper : colecaoHelperOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina(pagina1++);
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina(pagina2++);
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				 collRelatorioAvisoCorteBean.add(new RelatorioAvisoCorteModelo5Bean(primeiroHelper,
				 segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collRelatorioAvisoCorteBean.add(new RelatorioAvisoCorteModelo5Bean(primeiroHelper, segundoHelper));
		}

		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource(collRelatorioAvisoCorteBean);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_CORTE_ARQUIVO_PDF_MODELO_5, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_AVISO_CORTE_ARQUIVO_TXT, idFuncionalidadeIniciada, descricaoArquivo);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}


	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAvisoCorteModelo5", this);
	}
}