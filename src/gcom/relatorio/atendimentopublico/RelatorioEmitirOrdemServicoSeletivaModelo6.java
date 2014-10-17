
package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.EmitirContaHelper;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;

import java.util.*;

public class RelatorioEmitirOrdemServicoSeletivaModelo6
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEmitirOrdemServicoSeletivaModelo6(Usuario usuario, String nomeRelatorio) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_6);
	}

	@Deprecated
	public RelatorioEmitirOrdemServicoSeletivaModelo6() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Map<Integer, Integer> mapOdensServico = (Map<Integer, Integer>) getParametro("mapOdensServico");
		OrdemServicoSeletivaHelper helper = (OrdemServicoSeletivaHelper) getParametro("helper");

		byte[] retornoRelatorio = null;
		Fachada fachada = Fachada.getInstancia();

		/**
		 * Converte e Ordena Map para Helper
		 */
		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		// cria colecoes
		List<Integer> chaves = new ArrayList(mapOdensServico.keySet());

		List<Integer> valores = new ArrayList();
		valores.addAll(mapOdensServico.values());

		List<Integer> chavesOrdenadas = this.dividirColecaoOrdenando(chaves);

		List<Integer> valoresOrdenados = this.dividirColecaoOrdenando(valores);

		Integer[] chavesArray = null;
		Integer[] valoresArray = null;

		// converte colecao para array para possibilitar o preenchimento do helper
		chavesArray = this.converterListsParaArray(chavesOrdenadas);
		valoresArray = this.converterListsParaArray(valoresOrdenados);

		// alimenta colecao de helpers
		Collection<OrdemServicoModeloHelper> ordensSeletivasOrdenadas = new ArrayList();
		OrdemServicoModeloHelper osmh = new OrdemServicoModeloHelper();
		for(int i = 0; i < chavesArray.length; i++){
			osmh = new OrdemServicoModeloHelper();
			osmh.setIdImovel(chavesArray[i]);
			osmh.setIdOrdemServico(valoresArray[i]);
			ordensSeletivasOrdenadas.add(osmh);
			osmh = null;
		}
		/**
		 * =======================================
		 */

		RelatorioEmitirOrdemServicoSeletivaModelo6Bean primeiroHelper = null;
		RelatorioEmitirOrdemServicoSeletivaModelo6Bean segundoHelper = null;

		// regra para dividir em duas páginas
		int pagina1 = 1;
		int pagina2 = 0;
		int totalPaginas = ordensSeletivasOrdenadas.size();

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		// Caso o helper não possua a informação da descrição do tipo de serviço
		if(Util.isVazioOuBranco(helper.getServicoTipoDescricao())){
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, Integer.valueOf(helper.getServicoTipo())));

			Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

			helper.setServicoTipoDescricao(servicoTipo.getDescricao());
		}

		// seta os beans na coleção
		for(OrdemServicoModeloHelper oShelper : ordensSeletivasOrdenadas){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = this.preencherHelper(oShelper, helper, fachada);
				primeiroHelper.setNumeroPagina(pagina1++);
			}else{
				segundoHelper = this.preencherHelper(oShelper, helper, fachada);
				segundoHelper.setNumeroPagina(pagina2++);
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				relatorioBeans.add(new RelatorioEmitirOrdemServicoSeletivaModelo6Bean(primeiroHelper, segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			relatorioBeans.add(new RelatorioEmitirOrdemServicoSeletivaModelo6Bean(primeiroHelper, segundoHelper));
		}

		// ------------------------------------------------------------------------------------------------------------

		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		String enderecoCompleto = fachada.retornarEnderecoCompletoEmpresa(sistemaParametros);
		String inscricaoEstadual = "";
		String cnpj = sistemaParametros.getCnpjEmpresa();

		try{
			inscricaoEstadual = ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.executar();
		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		if(!inscricaoEstadual.equals("")){
			inscricaoEstadual = Util.formatarInscricaoEstadual(inscricaoEstadual);
		}
		if(!cnpj.equals("")){
			cnpj = Util.formatarCnpj(cnpj);

		}
		

		parametros.put("imagem", sistemaParametros.getImagemRelatorio());
		parametros.put("telefone", Util.formatarFone(sistemaParametros.getNumeroTelefone()));
		parametros.put("cnpj", cnpj);
		parametros.put("inscricaoEstadual", inscricaoEstadual);
		parametros.put("enderecoCompleto", enderecoCompleto);
		parametros.put("nome", sistemaParametros.getNomeEmpresa());

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		if(helper.getTipoFormatoRelatorio().equals(TarefaRelatorio.TIPO_ZIP)){
			helper.setTipoFormatoRelatorio(TarefaRelatorio.TIPO_PDF);
		}
		retornoRelatorio = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_6, parametros, ds,
						helper.getTipoFormatoRelatorio());

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retornoRelatorio, Relatorio.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA,
							this.getIdFuncionalidadeIniciada(), null);
		}catch(ControladorException e){
			throw new SistemaException(e, "Erro Batch Relatório");
		}
		// ------------------------------------
		// retorna o relatório gerado

		return retornoRelatorio;
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletivaModelo6", this);
	}

	private List<Integer> dividirColecaoOrdenando(List<Integer> colecao){

		List<Integer> retorno = new ArrayList<Integer>();
		List<Integer> colecaoTmp = colecao;
		int quantidadeItens = 0;
		int tamanhoColecao = colecaoTmp.size();
		int metadeColecao = 0;
		if(tamanhoColecao % 2 == 0){
			metadeColecao = tamanhoColecao / 2;
		}else{
			metadeColecao = (tamanhoColecao / 2) + 1;
		}
		while(quantidadeItens < metadeColecao){
			retorno.add(colecaoTmp.get(quantidadeItens));
			if(metadeColecao + quantidadeItens < tamanhoColecao){
				retorno.add(colecaoTmp.get(metadeColecao + quantidadeItens));
			}
			quantidadeItens++;
		}
		tamanhoColecao = 0;

		return retorno;
	}

	public Integer[] converterListsParaArray(List<Integer> lista){

		Integer[] retorno = new Integer[lista.size()];
		int i = 0;

		for(Integer valor : lista){
			retorno[i] = valor;
			i++;
		}

		return retorno;

	}

	public RelatorioEmitirOrdemServicoSeletivaModelo6Bean preencherHelper(OrdemServicoModeloHelper ordensSeletivaOrdenada,
					OrdemServicoSeletivaHelper helper, Fachada fachada){

		Integer idOrdemServico = null;
		Integer idImovel = null;
		Integer[] tiposLigacaoMedicao = null;
		Integer idTipoMedicao = null;
		Categoria categoria = null;
		Imovel imovel = null;
		int quantidadeEconomias = 0;

		idImovel = ordensSeletivaOrdenada.getIdImovel();
		idOrdemServico = ordensSeletivaOrdenada.getIdOrdemServico();

		imovel = fachada.pesquisarImovel(idImovel);

		RelatorioEmitirOrdemServicoSeletivaModelo6Bean modelo6Bean = new RelatorioEmitirOrdemServicoSeletivaModelo6Bean();

		if(idOrdemServico != null){
			modelo6Bean.setIdOrdemServico(idOrdemServico.toString());
		}else{
			modelo6Bean.setIdOrdemServico("");
		}
		modelo6Bean.setDataGeracao(Util.formatarData(new Date()));

		if(imovel.getId() != null){
			modelo6Bean.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(imovel.getId()));
		}else{
			modelo6Bean.setMatriculaImovel("");
		}

		if(imovel.getInscricaoFormatada() != null){
			modelo6Bean.setInscricao(imovel.getInscricaoFormatada());
		}else{
			modelo6Bean.setInscricao("");
		}
		String nomeCliente = fachada.pesquisarNomeClientePorImovel(idImovel);
		if(nomeCliente != null){
			modelo6Bean.setNomeCliente(nomeCliente.trim());
		}else{
			modelo6Bean.setNomeCliente("");
		}
		try{
			String endereco = fachada.pesquisarEnderecoFormatado(idImovel);
			modelo6Bean.setEndereco(endereco);

		}catch(ControladorException e){
			throw new SistemaException(e, "Erro Batch Relatório");
		}
		if(helper.getServicoTipoDescricao() != null){
			modelo6Bean.setDescricaoTipoServico(helper.getServicoTipo().toString() + " - " + helper.getServicoTipoDescricao());
		}else{
			modelo6Bean.setDescricaoTipoServico("");
		}

		// Pesquisa a Categoria
		categoria = fachada.obterPrincipalCategoriaImovel(imovel.getId());
		Subcategoria subcategoria = null;
		if(categoria != null){

			FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
			filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, categoria.getId()));
			Collection<Subcategoria> colecaoSubCategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

			subcategoria = (Subcategoria) Util.retonarObjetoDeColecao(colecaoSubCategoria);
		}

		// Pesquisa a quantidade de economias
		quantidadeEconomias = fachada.obterQuantidadeEconomias(imovel);
		if(subcategoria != null){
			modelo6Bean.setCatSubCatQtdeEconomias(categoria.getId() + "/" + subcategoria.getCodigo() + "/"
							+ Integer.toString(quantidadeEconomias));
		}else{
			modelo6Bean.setCatSubCatQtdeEconomias(categoria.getDescricaoAbreviada() + "/" + "" + "/"
							+ Integer.toString(quantidadeEconomias));
		}

		modelo6Bean.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
		modelo6Bean.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());

		// Rotina utilizada para obter os tipos de ligação e de medição
		EmitirContaHelper emitirContaHelper = new EmitirContaHelper();
		emitirContaHelper.setIdLigacaoAguaSituacao(imovel.getLigacaoAguaSituacao().getId());
		emitirContaHelper.setIdLigacaoEsgotoSituacao(imovel.getLigacaoEsgotoSituacao().getId());
		tiposLigacaoMedicao = fachada.determinarTipoLigacaoMedicao(emitirContaHelper);

		// Dados obtidos a partir do tipo da medição
		idTipoMedicao = tiposLigacaoMedicao[1];

		HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = null;

		if(!Util.isVazioOuBranco(idTipoMedicao)){
			hidrometroRelatorioOSHelper = fachada.obterDadosHidrometroPorTipoMedicao(idImovel, idTipoMedicao);
		}

		if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper)){
			if(hidrometroRelatorioOSHelper.getHidrometroNumero() != null){
				modelo6Bean.setNumeroHidrometro(hidrometroRelatorioOSHelper.getHidrometroNumero());
			}else{
				modelo6Bean.setNumeroHidrometro("");
			}
			if(hidrometroRelatorioOSHelper.getHidrometroCapacidade() != null){
				modelo6Bean.setCapacidade(hidrometroRelatorioOSHelper.getHidrometroCapacidade());
			}else{
				modelo6Bean.setCapacidade("");
			}
			if(hidrometroRelatorioOSHelper.getHidrometroNumeroDigitos() != null){
				modelo6Bean.setLeitura(hidrometroRelatorioOSHelper.getHidrometroNumeroDigitos());
			}else{
				modelo6Bean.setLeitura("0");
			}
			if(hidrometroRelatorioOSHelper.getHidrometroCapacidade() != null){
				modelo6Bean.setCapacidade(hidrometroRelatorioOSHelper.getHidrometroCapacidade());
			}else{
				modelo6Bean.setCapacidade("");
			}
			if(hidrometroRelatorioOSHelper.getHidrometroMarca() != null){
				modelo6Bean.setMarca(hidrometroRelatorioOSHelper.getHidrometroMarca());
			}else{
				modelo6Bean.setMarca("");
			}

			if(hidrometroRelatorioOSHelper.getHidrometroDiametro() != null){
				modelo6Bean.setDiametro(hidrometroRelatorioOSHelper.getHidrometroDiametro());
			}else{
				modelo6Bean.setDiametro("");
			}
			if(hidrometroRelatorioOSHelper.getIdTipoHidrometro() != null){
				modelo6Bean.setTipo(hidrometroRelatorioOSHelper.getIdTipoHidrometro());
			}else{
				modelo6Bean.setTipo("");
			}
			if(hidrometroRelatorioOSHelper.getIdProtecaoHidrometro() != null){
				modelo6Bean.setProtecao(hidrometroRelatorioOSHelper.getIdProtecaoHidrometro());
			}else{
				modelo6Bean.setProtecao("");
			}
			if(hidrometroRelatorioOSHelper.getIndicadorCavalete() != null){
				modelo6Bean.setCavalete(hidrometroRelatorioOSHelper.getIndicadorCavalete());
			}else{
				modelo6Bean.setCavalete("");
			}
			if(hidrometroRelatorioOSHelper.getHidrometroLocal() != null){
				modelo6Bean.setLocal(hidrometroRelatorioOSHelper.getHidrometroLocal());
			}else{
				modelo6Bean.setLocal("");
			}

		}

		return modelo6Bean;
	}
}
