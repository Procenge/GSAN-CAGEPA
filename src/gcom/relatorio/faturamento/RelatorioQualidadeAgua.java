
package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroQualidadeAgua;
import gcom.faturamento.QualidadeAgua;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioQualidadeAgua
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioQualidadeAgua(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_BAIRRO_MANTER);
	}

	@Deprecated
	public RelatorioQualidadeAgua() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroQualidadeAgua filtroQualidadeAgua = (FiltroQualidadeAgua) getParametro("filtroQualidadeAgua");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioQualidadeAguaBean relatorioBean = null;

		filtroQualidadeAgua.setConsultaSemLimites(true);

		if(filtroQualidadeAgua.getColecaoCaminhosParaCarregamentoEntidades() == null
						|| filtroQualidadeAgua.getColecaoCaminhosParaCarregamentoEntidades().isEmpty()){
			filtroQualidadeAgua.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroQualidadeAgua.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		}

		Collection colecaoQualidade = fachada.pesquisar(filtroQualidadeAgua, QualidadeAgua.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoQualidade != null && !colecaoQualidade.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator qualidadeIterator = colecaoQualidade.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(qualidadeIterator.hasNext()){
				QualidadeAgua qualidadeAgua = (QualidadeAgua) qualidadeIterator.next();

				String descricaoFonteCaptacao = qualidadeAgua.getDescricaoFonteCaptacao() != null ? qualidadeAgua
								.getDescricaoFonteCaptacao()
								+ "" : "";

				// 3.1.5. N�mero de Amostras Realizadas:
				// 3.1.5.1. Turbidez (QLAG_NNREALIZADATURBIDEZ).
				String numeroAmostrasRealizadasTurbidez = qualidadeAgua.getNumeroAmostrasRealizadasTurbidez() != null ? qualidadeAgua
								.getNumeroAmostrasRealizadasTurbidez()
								+ "" : "";
				// 3.1.5.2. Cor (QLAG_NNREALIZADACOR).
				String numeroAmostrasRealizadasCor = qualidadeAgua.getNumeroAmostrasRealizadasCor() != null ? qualidadeAgua
								.getNumeroAmostrasRealizadasCor()
								+ "" : "";
				// 3.1.5.3. Cloro (QLAG_NNREALIZADACLORO).
				String numeroAmostrasRealizadasCloro = qualidadeAgua.getNumeroAmostrasRealizadasCloro() != null ? qualidadeAgua
								.getNumeroAmostrasRealizadasCloro()
								+ "" : "";
				// 3.1.5.4. PH (QLAG_NNREALIZADAPH).
				String numeroAmostrasRealizadasPH = qualidadeAgua.getNumeroAmostrasRealizadasPH() != null ? qualidadeAgua
								.getNumeroAmostrasRealizadasPH()
								+ "" : "";
				// 3.1.5.5. Fl�or (QLAG_NNREALIZADAFLUOR).
				String numeroAmostrasRealizadasFluor = qualidadeAgua.getNumeroAmostrasRealizadasFluor() != null ? qualidadeAgua
								.getNumeroAmostrasRealizadasFluor()
								+ "" : "";
				// 3.1.5.6. Coliformes Totais (QLAG_NNREALIZADACOLIFORMESTOTAIS).
				String numeroAmostrasRealizadasColiformesTotais = qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais() != null ? qualidadeAgua
								.getNumeroAmostrasRealizadasColiformesTotais()
								+ ""
								: "";
				// 3.1.5.7. Coliformes Temotolerantes (QLAG_NNREALIZADACOLIFORMESTERMOTOLERANTES).
				String numeroAmostrasRealizadasColiformesTermotolerantes = qualidadeAgua
								.getNumeroAmostrasRealizadasColiformesTermotolerantes() != null ? qualidadeAgua
								.getNumeroAmostrasRealizadasColiformesTermotolerantes()
								+ "" : "";

				// 3.1.6. N�mero de Amostras Conformes:
				// 3.1.6.1. Turbidez (QLAG_NNCONFORMETURBIDEZ).
				String numeroAmostrasConformesTurbidez = qualidadeAgua.getNumeroAmostrasConformesTurbidez() != null ? qualidadeAgua
								.getNumeroAmostrasConformesTurbidez()
								+ "" : "";
				// 3.1.6.2. Cor (QLAG_NNCONFORMECOR).
				String numeroAmostrasConformesCor = qualidadeAgua.getNumeroAmostrasConformesCor() != null ? qualidadeAgua
								.getNumeroAmostrasConformesCor()
								+ "" : "";
				// 3.1.6.3. Cloro (QLAG_NNCONFORMECLORO).
				String numeroAmostrasConformesCloro = qualidadeAgua.getNumeroAmostrasConformesCloro() != null ? qualidadeAgua
								.getNumeroAmostrasConformesCloro()
								+ "" : "";
				// 3.1.6.4. PH (QLAG_NNCONFORMEPH).
				String numeroAmostrasConformesPH = qualidadeAgua.getNumeroAmostrasConformesPH() != null ? qualidadeAgua
								.getNumeroAmostrasConformesPH()
								+ "" : "";
				// 3.1.6.5. Fl�or (QLAG_NNCONFORMEFLUOR).
				String numeroAmostrasConformesFluor = qualidadeAgua.getNumeroAmostrasConformesFluor() != null ? qualidadeAgua
								.getNumeroAmostrasConformesFluor()
								+ "" : "";
				// 3.1.6.6. Coliformes Totais (QLAG_NNCONFORMECOLIFORMESTOTAIS).
				String numeroAmostrasConformesColiformesTotais = qualidadeAgua.getNumeroAmostrasConformesColiformesTotais() != null ? qualidadeAgua
								.getNumeroAmostrasConformesColiformesTotais()
								+ ""
								: "";
				// 3.1.6.7. Coliformes Temotolerantes (QLAG_NNCONFORMECOLIFORMESTERMOTOLERANTES).
				String numeroAmostrasConformesColiformesTermotolerantes = qualidadeAgua
								.getNumeroAmostrasConformesColiformesTermotolerantes() != null ? qualidadeAgua
								.getNumeroAmostrasConformesColiformesTermotolerantes()
								+ "" : "";

				// 3.1.7. M�dia de Resultados:
				// 3.1.7.1. Turbidez (QLAG_NNMEDIATURBIDEZ).
				String numeroAmostrasMediaTurbidez = qualidadeAgua.getNumeroAmostrasMediaTurbidez() != null ? qualidadeAgua
								.getNumeroAmostrasMediaTurbidez()
								+ "" : "";
				// 3.1.7.2. Cor (QLAG_NNMEDIACOR).
				String numeroAmostrasMediaCor = qualidadeAgua.getNumeroAmostrasMediaCor() != null ? qualidadeAgua
								.getNumeroAmostrasMediaCor()
								+ "" : "";
				// 3.1.7.3. Cloro (QLAG_NNMEDIACLORO).
				String numeroAmostrasMediaCloro = qualidadeAgua.getNumeroAmostrasMediaCloro() != null ? qualidadeAgua
								.getNumeroAmostrasMediaCloro()
								+ "" : "";
				// 3.1.7.4. PH (opcional).
				String numeroAmostrasMediaPH = qualidadeAgua.getNumeroAmostrasMediaPH() != null ? qualidadeAgua.getNumeroAmostrasMediaPH()
								+ "" : "";
				// 3.1.7.5. Fl�or (QLAG_NNMEDIAFLUOR).
				String numeroAmostrasMediaFluor = qualidadeAgua.getNumeroAmostrasMediaFluor() != null ? qualidadeAgua
								.getNumeroAmostrasMediaFluor()
								+ "" : "";
				// 3.1.7.6. Coliformes Totais (QLAG_NNMEDIACOLIFORMESTOTAIS).
				String numeroAmostrasMediaColiformesTotais = qualidadeAgua.getNumeroAmostrasMediaColiformesTotais() != null ? qualidadeAgua
								.getNumeroAmostrasMediaColiformesTotais()
								+ "" : "";
				// 3.1.7.7. Coliformes Temotolerantes (QLAG_NNMEDIACOLIFORMESTERMOTOLERANTES).
				String numeroAmostrasMediaColiformesTermotolerantes = qualidadeAgua.getNumeroAmostrasMediaColiformesTermotolerantes() != null ? qualidadeAgua
								.getNumeroAmostrasMediaColiformesTermotolerantes()
								+ ""
								: "";

				relatorioBean = new RelatorioQualidadeAguaBean(Util.formatarAnoMesParaMesAno(qualidadeAgua.getAnoMesReferencia()),
								qualidadeAgua.getLocalidade() != null ? qualidadeAgua.getLocalidade().getId() + "" : "", qualidadeAgua
												.getSetorComercial() != null ? qualidadeAgua.getSetorComercial().getCodigo() + "" : "",
								descricaoFonteCaptacao, numeroAmostrasRealizadasTurbidez, numeroAmostrasRealizadasCor,
								numeroAmostrasRealizadasCloro, numeroAmostrasRealizadasPH, numeroAmostrasRealizadasFluor,
								numeroAmostrasRealizadasColiformesTotais, numeroAmostrasRealizadasColiformesTermotolerantes,
								numeroAmostrasConformesTurbidez, numeroAmostrasConformesCor, numeroAmostrasConformesCloro,
								numeroAmostrasConformesPH, numeroAmostrasConformesFluor, numeroAmostrasConformesColiformesTotais,
								numeroAmostrasConformesColiformesTermotolerantes, numeroAmostrasMediaTurbidez, numeroAmostrasMediaCor,
								numeroAmostrasMediaCloro, numeroAmostrasMediaPH, numeroAmostrasMediaFluor,
								numeroAmostrasMediaColiformesTotais, numeroAmostrasMediaColiformesTermotolerantes);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GERAR_QUALIDADE_AGUA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.QUALIDADE_AGUA, idFuncionalidadeIniciada, null);
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

		int retorno = 0;

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroQualidadeAgua) getParametro("filtroQualidadeAgua"),
						QualidadeAgua.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioQualidadeAgua", this);

	}

}