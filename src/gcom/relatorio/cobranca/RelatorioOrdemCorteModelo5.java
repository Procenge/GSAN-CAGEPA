
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
import gcom.util.parametrizacao.ParametroGeral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.procenge.comum.exception.NegocioException;

/**
 * @author André Lopes
 * @date 21/05/2013
 */
public class RelatorioOrdemCorteModelo5
				extends TarefaRelatorio {

	public RelatorioOrdemCorteModelo5(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_5);
	}

	public RelatorioOrdemCorteModelo5() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String descricaoArquivo = getParametro("descricaoArquivo").toString();

		List<EmitirDocumentoOrdemCorteModelo4e5Helper> colecaoOrdenada = (ArrayList) getParametro("colecaoHelperOrdenada");

		List<RelatorioOrdemCorteModelo5Bean> collRelatorioBean = new ArrayList<RelatorioOrdemCorteModelo5Bean>();

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

				collRelatorioBean.add(new RelatorioOrdemCorteModelo5Bean(primeiroHelper, segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collRelatorioBean.add(new RelatorioOrdemCorteModelo5Bean(primeiroHelper, segundoHelper));
		}

		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("P_NN_FONE", sistemaParametro.getNumeroTelefone());

		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR", (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		RelatorioDataSource ds = new RelatorioDataSource(collRelatorioBean);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_5, parametros, ds, tipoFormatoRelatorio);

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

		AgendadorTarefas.agendarTarefa("RelatorioOrdemCorteModelo5", this);
	}
}
