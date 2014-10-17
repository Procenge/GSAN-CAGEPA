package gcom.relatorio.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.FiltroMovimentoRoteiroEmpresa;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.MovimentoRoteiroEmpresa;
import gcom.micromedicao.Rota;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;

import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

public class RelatorioAuditoriaLeitura
				extends TarefaRelatorio {

	public RelatorioAuditoriaLeitura(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_AUDITORIA_LEITURA);
	}

	public RelatorioAuditoriaLeitura() {

		super(null, "");
	}

	@Override
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Collection<Integer> colecaoIdsRotas = (Collection<Integer>) getParametro("colecaoIdsRotas");
		Integer idFaturamentoGrupo = (Integer) getParametro("idFaturamentoGrupo");
		Integer idLocalidade = (Integer) getParametro("idLocalidade");
		Integer cdSetorComercial = (Integer) getParametro("cdSetorComercial");
		Integer cdRota = (Integer) getParametro("cdRota");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioAuditoriaLeitura relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		for(Integer idRota : colecaoIdsRotas){

			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples("id", idRota));
			Rota rota = (Rota) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroRota, Rota.class.getName()));

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ROTA_ID, idRota));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.setCampoOrderBy(FiltroImovel.LOCALIDADE_ID, FiltroImovel.SETOR_COMERCIAL_CODIGO, FiltroImovel.QUADRA_NUMERO,
							FiltroImovel.LOTE, FiltroImovel.SUBLOTE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.HIDROMETRO_INSTALACAO_HISTORICO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico.hidrometro");

			Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			double porcentual = rota.getPercentualGeracaoFiscalizacao() == null ? 0 : rota.getPercentualGeracaoFiscalizacao().doubleValue();

			int quantidadeImoveisSelecionados = (int) Math.round(colecaoImovel.size()
 * porcentual);

			Collection<Imovel> colecaoImoveisSelecionados = new ArrayList<Imovel>();

			Random random = new Random();

			for(int i = 0; i < quantidadeImoveisSelecionados; i++){

				Imovel imovel = ((ArrayList<Imovel>) colecaoImovel).get(random.nextInt(colecaoImovel.size()));
				colecaoImoveisSelecionados.add(imovel);
				colecaoImovel.remove(imovel);
			}

			RelatorioAuditoriaLeituraBean bean;

			Collections.sort((List<Imovel>) colecaoImoveisSelecionados);
			for(Imovel imovel : colecaoImoveisSelecionados){
				bean = new RelatorioAuditoriaLeituraBean();

				bean.setIdRota(idRota.toString());
				bean.setLocalidade(imovel.getLocalidade().getDescricaoComId().toString());
				bean.setSetorComercial(Integer.toString(imovel.getSetorComercial().getCodigo()));
				bean.setQuadra(Integer.toString(imovel.getQuadra().getNumeroQuadra()));
				
				bean.setInscricao(imovel.getInscricaoFormatada());
				bean.setMatricula(imovel.getId().toString());
				bean.setEndereco(fachada.pesquisarEndereco(imovel.getId()));
				bean.setNomeCliente(fachada.pesquisarClienteUsuarioImovel(imovel.getId()).getNome());
				bean.setSituacaoLigacaoAgua(imovel.getLigacaoAguaSituacao().getId().toString());
				bean.setSituacaoLigacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId().toString());
				
				SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
				FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.IMOVEL_ID, imovel
								.getId()));
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO, sistemaParametro.getAnoMesFaturamento()));
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade(filtroMovimentoRoteiroEmpresa.LEITURA_ANORMALIDADE);
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("leiturista");
				Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresas = fachada.pesquisar(filtroMovimentoRoteiroEmpresa, MovimentoRoteiroEmpresa.class.getName());
				
				if(!Util.isVazioOrNulo(colecaoMovimentoRoteiroEmpresas)){
					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) Util.retonarObjetoDeColecao(colecaoMovimentoRoteiroEmpresas) ;
					
					
					bean.setNumeroHidrometro(movimentoRoteiroEmpresa.getNumeroHidrometro());
					bean.setLocalInstalacao(movimentoRoteiroEmpresa.getLocalInstalacaoHidrometro());
					bean.setDataInstalacao(Util.formatarData(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro()));
					if(movimentoRoteiroEmpresa.getNumeroConsumoMedio() != null){
						bean.setConsumoMedio(movimentoRoteiroEmpresa.getNumeroConsumoMedio().toString());
					}
					Integer mediaConsumo = movimentoRoteiroEmpresa.getNumeroConsumoMedio() != null ? movimentoRoteiroEmpresa.getNumeroConsumoMedio() : 0;
					Integer leituraAnterior = movimentoRoteiroEmpresa.getNumeroLeituraAnterior() != null ? movimentoRoteiroEmpresa.getNumeroLeituraAnterior() : 0;
					if(imovel.getHidrometroInstalacaoHistorico() != null){
					int[] faixaEsperada = fachada.calcularFaixaLeituraEsperada(mediaConsumo, null, imovel
									.getHidrometroInstalacaoHistorico().getHidrometro(), leituraAnterior);
					bean.setLimiteInferior(Integer.toString(faixaEsperada[0]));
					bean.setLimiteSuperior(Integer.toString(faixaEsperada[1]));
					}
					if(movimentoRoteiroEmpresa.getNumeroLeituraAnterior() != null){
					bean.setLimiteAnterior(movimentoRoteiroEmpresa.getNumeroLeituraAnterior().toString());
					}
					if(movimentoRoteiroEmpresa.getNumeroLeitura() != null){
						bean.setLimiteAtual(movimentoRoteiroEmpresa.getNumeroLeitura().toString());
					}

					if(movimentoRoteiroEmpresa.getLeituraAnormalidade() != null){
						bean.setAnormalidadeLeitura(movimentoRoteiroEmpresa.getLeituraAnormalidade().getId().toString());
					}
					if(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior() != null){
						bean.setSituacaoLeitura(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().toString());
					}
					bean.setDataLeitura(Util.formatarData(movimentoRoteiroEmpresa.getDataLeituraFaturada()));
					if(movimentoRoteiroEmpresa.getLeiturista() != null){
						bean.setLeiturista(movimentoRoteiroEmpresa.getLeiturista().getId().toString());
					}

				}

				relatorioBeans.add(bean);
			}

		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		try{
			parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("P_FONE", sistemaParametro.getNumeroTelefone());
		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR", (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		if(idLocalidade != null){

			parametros.put("P_LOCALIDADE", idLocalidade.toString());

		}else{
			parametros.put("P_LOCALIDADE", "");

		}

		if(idFaturamentoGrupo != null){

			parametros.put("P_GRUPO_FATURAMENTO", idFaturamentoGrupo.toString());

		}else{
			parametros.put("P_GRUPO_FATURAMENTO", "");

		}

		if(cdSetorComercial != null){

			parametros.put("P_SETOR_COMERCIAL", cdSetorComercial.toString());

		}else{
			parametros.put("P_SETOR_COMERCIAL", "");

		}
		if(cdRota != null){

			parametros.put("P_ROTA", cdRota.toString());

		}else{
			parametros.put("P_ROTA", "");

		}

		Integer tipoFormatoRelatorio = Integer.parseInt(ConstantesSistema.PDF);

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AUDITORIA_LEITURA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_AUDITORIA_LEITURA, idFuncionalidadeIniciada, null);
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

		Collection<Integer> colecaoIdsRotas = (Collection<Integer>) getParametro("colecaoIdsRotas");

		for(Integer idRota : colecaoIdsRotas){

			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples("id", idRota));
			Rota rota = (Rota) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroRota, Rota.class.getName()));

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ROTA_ID, idRota));

			double porcentual = rota.getPercentualGeracaoFiscalizacao() == null ? 0 : rota.getPercentualGeracaoFiscalizacao().doubleValue();

			retorno += Math.round(Fachada.getInstancia().totalRegistrosPesquisa(filtroImovel, Imovel.class.getName())
 * porcentual);

		}

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAuditoriaLeitura", this);

	}

}
