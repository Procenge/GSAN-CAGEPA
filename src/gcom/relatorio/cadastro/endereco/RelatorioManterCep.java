package gcom.relatorio.cadastro.endereco;

import gcom.batch.Relatorio;
import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;


public class RelatorioManterCep
				extends TarefaRelatorio {

	public RelatorioManterCep(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_CEP);
	}

	public RelatorioManterCep() {

		super(null, "");
	}

	@Override
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroCep filtroCep = (FiltroCep) getParametro("filtroCep");
		Cep cepParametros = (Cep) getParametro("cepParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterCepBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroCep.setConsultaSemLimites(true);

		Collection colecaoCeps = fachada.pesquisar(filtroCep, Cep.class.getName());

		if(colecaoCeps != null && !colecaoCeps.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoCepsIterator = colecaoCeps.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoCepsIterator.hasNext()){

				Cep cep = (Cep) colecaoCepsIterator.next();

				String logradouro = "";
				if(cep.getDescricaoLogradouroFormatada() != null){

					logradouro = cep.getDescricaoLogradouroFormatada();
				}

				String bairro = "";
				if(cep.getBairro() != null){

					bairro = cep.getBairro();
				}

				String municipio = "";
				if(cep.getMunicipio() != null){
					municipio = cep.getMunicipio();

				}

				String uf = "";
				if(cep.getSigla() != null){
					uf = cep.getSigla();

				}

				String codigoCep = "";
				if(cep.getCodigo() != null){
					codigoCep = cep.getCodigo().toString();

				}

				String faixa = "";
				if(cep.getNumeroFaixaFinal() != null && cep.getNumeroFaixaFinal() != null){
					faixa = String.valueOf(cep.getNumeroFaixaIncial()) + "-" + String.valueOf(cep.getNumeroFaixaFinal());
				}

				relatorioBean = new RelatorioManterCepBean(logradouro,

				bairro,

				municipio,

				uf,

				codigoCep,

				faixa);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);

			}

		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(cepParametros.getMunicipio() != null){

			parametros.put("municipio", cepParametros.getMunicipio());

		}else{
			parametros.put("municipio", "");

		}

		if(cepParametros.getBairro() != null){

			parametros.put("bairro", cepParametros.getBairro());

		}else{
			parametros.put("bairro", "");

		}

		if(cepParametros.getLogradouro() != null){

			parametros.put("logradouro", cepParametros.getLogradouro());

		}else{
			parametros.put("logradouro", "");

		}

		String indicadorUso = "";
		
		if(cepParametros.getIndicadorUso() != null && !cepParametros.getIndicadorUso().equals("")){
			if(cepParametros.getIndicadorUso().equals(new Short("1"))){
				indicadorUso = "Ativo";
			}else if(cepParametros.getIndicadorUso().equals(new Short("2"))){
				indicadorUso = "Inativo";
			}else{
				indicadorUso = "Todos";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_MANTER_CEP, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MANTER_CEP, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroCep) getParametro("filtroCep"), Cep.class.getName());

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterCep", this);

	}

}
