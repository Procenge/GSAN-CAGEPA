
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.EmitirDocumentoOrdemCorteModelo4e5Helper;
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
 * @author André Lopes
 * @date 21/05/2013
 */
public class RelatorioOrdemCorteModelo4
				extends TarefaRelatorio {

	public RelatorioOrdemCorteModelo4(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_4);
	}

	public RelatorioOrdemCorteModelo4() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String descricaoArquivo = "";// (String) getParametro("descricaoArquivo");

		List<EmitirDocumentoOrdemCorteModelo4e5Helper> colecaoOrdenada = (ArrayList) getParametro("colecaoHelperOrdenada");

		List<RelatorioOrdemCorteModelo4Bean> collRelatorioBean = new ArrayList<RelatorioOrdemCorteModelo4Bean>();

		EmitirDocumentoOrdemCorteModelo4e5Helper primeiroHelper = null;
		EmitirDocumentoOrdemCorteModelo4e5Helper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;
		int totalPaginas = colecaoOrdenada.size();

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(EmitirDocumentoOrdemCorteModelo4e5Helper helper : colecaoOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina(pagina1++);
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina(pagina2++);
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collRelatorioBean.add(new RelatorioOrdemCorteModelo4Bean(primeiroHelper, segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collRelatorioBean.add(new RelatorioOrdemCorteModelo4Bean(primeiroHelper, segundoHelper));
		}

		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource(collRelatorioBean);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_4, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ORDEM_CORTE_ARQUIVO_TXT, idFuncionalidadeIniciada,
							descricaoArquivo);
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

		AgendadorTarefas.agendarTarefa("RelatorioOrdemCorteModelo4", this);
	}
}
