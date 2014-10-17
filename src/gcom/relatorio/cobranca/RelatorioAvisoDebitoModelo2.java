
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cobranca.bean.EmitirRelatorioAvisoDebitoHelper;
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

public class RelatorioAvisoDebitoModelo2
				extends TarefaRelatorio {

	public RelatorioAvisoDebitoModelo2(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_AVISO_DEBITO_MODELO_2);
	}

	public RelatorioAvisoDebitoModelo2() {

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

		List<EmitirRelatorioAvisoDebitoHelper> colecaoHelperOrdenada = (ArrayList) getParametro("colecaoHelperOrdenada");

		List<RelatorioAvisoDebitoModelo2Bean> collRelatorioAvisoDebitoBean = new ArrayList<RelatorioAvisoDebitoModelo2Bean>();

		EmitirRelatorioAvisoDebitoHelper primeiroHelper = null;
		EmitirRelatorioAvisoDebitoHelper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;
		int totalPaginas = colecaoHelperOrdenada.size();

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(EmitirRelatorioAvisoDebitoHelper helper : colecaoHelperOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina(pagina1++);
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina(pagina2++);
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collRelatorioAvisoDebitoBean.add(new RelatorioAvisoDebitoModelo2Bean(primeiroHelper,
				 segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){

			collRelatorioAvisoDebitoBean.add(new RelatorioAvisoDebitoModelo2Bean(primeiroHelper, segundoHelper));
		}

		Map parametros = new HashMap();
		RelatorioDataSource ds = new RelatorioDataSource(collRelatorioAvisoDebitoBean);
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_DEBITO_MODELO_2, parametros, ds, tipoFormatoRelatorio);

		try{

			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_AVISO_DEBITO, idFuncionalidadeIniciada, descricaoArquivo);
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

		AgendadorTarefas.agendarTarefa("RelatorioAvisoDebitoModelo2", this);
	}
}
