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

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioManterCepBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroCep.setConsultaSemLimites(true);

		Collection colecaoCeps = fachada.pesquisar(filtroCep, Cep.class.getName());

		if(colecaoCeps != null && !colecaoCeps.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoCepsIterator = colecaoCeps.iterator();

			// laço para criar a coleção de parâmetros da analise
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

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);

			}

		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
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

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_MANTER_CEP, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MANTER_CEP, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
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
