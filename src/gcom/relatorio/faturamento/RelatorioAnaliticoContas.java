package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.RelatorioAnaliticoContasHelper;
import gcom.relatorio.ConstantesExecucaoRelatorios;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

public class RelatorioAnaliticoContas
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAnaliticoContas(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ANALITICO_CONTAS);
	}

	@Deprecated
	public RelatorioAnaliticoContas() {

		super(null, "");
	}

	
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer idGerenciaRegional = (Integer) getParametro("idGerenciaRegional");
		Integer idLocalidade = (Integer) getParametro("idLocalidade");
		Integer idCategoria = (Integer) getParametro("idCategoria");
		Integer idCliente = (Integer) getParametro("idCliente");
		Integer IdImovel = (Integer) getParametro("idImovel");
		Integer idSituacao = (Integer) getParametro("idSituacao");
		Integer motivoRetificacao = (Integer) getParametro("motivoRetificacao");
		Integer referencia = (Integer) getParametro("referencia");// mesAno
		Integer faturamentoGrupo = (Integer) getParametro("grupoFaturamento");
		Integer setorComercial = (Integer) getParametro("setorComercial");
		Integer quadra = (Integer) getParametro("idQuadra");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioAnaliticoContasBean relatorioBean = null;

		Collection<RelatorioAnaliticoContasHelper> colecaoRelatorioAnaliticoContasHelper = fachada.pesquisarDadosRelatorioAnaliticoContas(
						idGerenciaRegional, idLocalidade, idCategoria, idCliente, IdImovel, idSituacao, motivoRetificacao, referencia,
						faturamentoGrupo, setorComercial, quadra);

		// se a cole��o de par�metros da analise n�o for vazia
		if(!Util.isVazioOrNulo(colecaoRelatorioAnaliticoContasHelper)){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator it = colecaoRelatorioAnaliticoContasHelper.iterator();


			// la�o para criar a cole��o de par�metros da analise
			while(it.hasNext()){

				
				
				RelatorioAnaliticoContasHelper relatorioAnaliticoContasHelper = (RelatorioAnaliticoContasHelper) it.next();


				relatorioBean = new RelatorioAnaliticoContasBean(
								//Origem
								relatorioAnaliticoContasHelper.getOrigem(),
								//Matricula
								relatorioAnaliticoContasHelper.getIdImovel().toString(),
								//Refer�ncia mes/ano
								Util.formatarAnoMesSemBarraParaMesAnoComBarra(Integer.parseInt(relatorioAnaliticoContasHelper
												.getReferencia())),
								// titularConta
								relatorioAnaliticoContasHelper.getTitularConta(),
								//hora
								relatorioAnaliticoContasHelper.getHora(),
								//valor atual
								Util.formatarMoedaReal(relatorioAnaliticoContasHelper.getValorAtual()).toString(),
								// valor anterio
								Util.formatarMoedaReal(relatorioAnaliticoContasHelper.getValorAnterior()).toString(),
								// Ultima altera��o
								Util.formatarData(relatorioAnaliticoContasHelper.getUltimaAlteracao().toString().replace("-", "")),
								//motivo
								relatorioAnaliticoContasHelper.getMotivo(),
								// usuarioGsan
								relatorioAnaliticoContasHelper.getUsuarioGsan(),
								// nenhumResultado
								relatorioAnaliticoContasHelper.getNenhumResultado());

			

				
				relatorioBeans.add(relatorioBean);

			}
		}else{

			// Caso n�o retorne nenhum resultado - Devido a demora da query
			relatorioBean = new RelatorioAnaliticoContasBean(
							// Origem
							null,
							// Matricula
							null,
							// Refer�ncia mes/ano
							null,
							// titularConta
							null,
							// hora
							null,
							// valor atual
							null,
							// valor anterio
							null,
							// Ultima altera��o
							null,
							// motivo
							null,
							// usuarioGsan
							null,
							// nenhumResultado
							"Nenhum resultado Encontrado");

			relatorioBeans.add(relatorioBean);
			// throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// parametros.put("referencia", Util.formatarAnoMesParaMesAno(referencia));

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ANALITICO_CONTAS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ANALITICO_CONTAS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = ConstantesExecucaoRelatorios.QUANTIDADE_NAO_INFORMADA;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAnaliticoContas", this);
	}
}