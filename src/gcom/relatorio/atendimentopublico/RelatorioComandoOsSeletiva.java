package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.bean.OrdemServicoSeletivaComandoHelper;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;

import java.util.*;

public class RelatorioComandoOsSeletiva
				extends TarefaRelatorio {

	public RelatorioComandoOsSeletiva(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_COMANDO_OS_SELETIVA);
		// TODO Auto-generated constructor stub
	}
	
	@Deprecated
	public RelatorioComandoOsSeletiva() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object executar() throws TarefaException{

		// ------------------------------------
		// Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		OrdemServicoSeletivaComandoHelper ordemServicoSeletivaComandoHelper = (OrdemServicoSeletivaComandoHelper) getParametro("ordemServicoSeletivaComandoHelper");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioComandoOsSeletivaBean relatorioBean = null;

		Collection<Object[]> colecaoComandos = fachada.filtrarDadosRelatorioComandoOSSeletiva(ordemServicoSeletivaComandoHelper);

		Object[] dadosRetorno = null;

		if(colecaoComandos != null && !colecaoComandos.isEmpty()){
			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoComandosIterator = colecaoComandos.iterator();
			
			// laço para criar a coleção de parâmetros da analise
			while(colecaoComandosIterator.hasNext()){
				
				dadosRetorno = (Object[]) colecaoComandosIterator.next();
				
				// titulo
				String titulo = "";
				if(dadosRetorno[0] != null){
					titulo = dadosRetorno[0].toString();
				}

				// empresa
				String empresa = "";
				if(dadosRetorno[1] != null){
					empresa = dadosRetorno[1].toString();
				}

				// servicoTipoId
				String servicoTipoId = "";
				if(dadosRetorno[2] != null){
					servicoTipoId = dadosRetorno[2].toString();
				}

				// servicoTipo
				String servicoTipo = "";
				if(dadosRetorno[3] != null){
					servicoTipo = dadosRetorno[3].toString();
				}

				// dataHoraComando
				String dataHoraComando = "";
				if(dadosRetorno[4] != null){
					dataHoraComando = dadosRetorno[4].toString();
				}

				// dataHoraRealizacao
				String dataHoraRealizacao = "";
				if(dadosRetorno[5] != null){
					dataHoraRealizacao = dadosRetorno[5].toString();
				}

				// qtdeOsGerada
				String qtdeOsGerada = "";
				if(dadosRetorno[6] != null){
					qtdeOsGerada = dadosRetorno[6].toString();
				}

				// qtdeOsPendente
				String qtdeOsPendente = "";
				if(dadosRetorno[7] != null){
					qtdeOsPendente = dadosRetorno[7].toString();
				}

				// qtdeOsCancelada
				String qtdeOsCancelada = "";
				if(dadosRetorno[8] != null){
					qtdeOsCancelada = dadosRetorno[8].toString();
				}

				// qtdeOsExecutada
				String qtdeOsExecutada = "";
				if(dadosRetorno[9] != null){
					qtdeOsExecutada = dadosRetorno[9].toString();
				}

				relatorioBean = new RelatorioComandoOsSeletivaBean(
				// titulo
								titulo,

								// empresa
								empresa,

								// servicoTipoId
								servicoTipoId,

								// servicoTipo
								servicoTipo,

								// dataComanto
								dataHoraComando,

								// dataRealizacao
								dataHoraRealizacao,

								// qtdeOsGerada
								qtdeOsGerada,

								// qtdeOsPendente
								qtdeOsPendente,

								// qtdeOsCancelada
								qtdeOsCancelada,

								// qtdeOsExecutada
								qtdeOsExecutada);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);

			}

		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("titulo", ordemServicoSeletivaComandoHelper.getTitulo());
		parametros.put("dataHoraComandoInicial", ordemServicoSeletivaComandoHelper.getDataComandoInicial());
		parametros.put("dataHoraComandoFinal", ordemServicoSeletivaComandoHelper.getDataComandoFinal());
		parametros.put("dataHoraRealizacaoInicial", ordemServicoSeletivaComandoHelper.getDataRealizacaoComandoInicial());
		parametros.put("dataHoraRealizacaoFinal", ordemServicoSeletivaComandoHelper.getDataRealizacaoComandoFinal());
		parametros.put("situacaoComando", ordemServicoSeletivaComandoHelper.getSituacaoComando());

		Map parametrosFormatados = this.formatarParametros(fachada, ordemServicoSeletivaComandoHelper);

		parametros.put("empresa", parametrosFormatados.get("empresa"));
		parametros.put("servicoTipo", parametrosFormatados.get("servicoTipo"));
		parametros.put("elo", parametrosFormatados.get("elo"));
		parametros.put("grupoFaturamento", parametrosFormatados.get("faturamentoGrupo"));
		parametros.put("gerenciaRegional", parametrosFormatados.get("gerenciaRegional"));
		parametros.put("localidade", parametrosFormatados.get("localidade"));
		parametros.put("setor", parametrosFormatados.get("setor"));
		parametros.put("quadra", parametrosFormatados.get("quadra"));
		parametros.put("rota", parametrosFormatados.get("rota"));
		parametros.put("lote", parametrosFormatados.get("lote"));
		parametros.put("bairro", parametrosFormatados.get("bairro"));
		parametros.put("logradouro", parametrosFormatados.get("logradouro"));
		parametros.put("perfilImovel", parametrosFormatados.get("perfilImovel"));
		parametros.put("categoria", parametrosFormatados.get("categoria"));
		parametros.put("subcategoria", parametrosFormatados.get("subcategoria"));
		parametros.put("ligacaoAguaSituacao", parametrosFormatados.get("ligacaoAguaSituacao"));
		parametros.put("ligacaoEsgotoSituacao", parametrosFormatados.get("ligacaoEsgotoSituacao"));

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_COMANDO_OS_SELETIVA, parametros, ds, tipoFormatoRelatorio);

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub

	}

	
	/**
	 * Concatena, em String, os parametros que vêm como array, para ser exibido no relatório.
	 * 
	 * @author ialmeida
	 * @param Fachada
	 * @param OrdemServicoSeletivaComandoHelper
	 * @return Map
	 */
	public Map formatarParametros(Fachada fachada, OrdemServicoSeletivaComandoHelper ordemServicoSeletivaComandoHelper){
	
		Map parametrosFormatados = new HashMap<String, String>();

		// Parametro empresa
		String empresas = "";
		Collection colEmpresasId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getFirma() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getFirma().length; i++){
				colEmpresasId.add(ordemServicoSeletivaComandoHelper.getFirma()[i]);
			}
			Collection colEmpresa = fachada.pesquisar(colEmpresasId, new FiltroEmpresa(), Empresa.class.getName());
			for(Object empresa : colEmpresa){
				empresas = empresas + ((Empresa) empresa).getDescricaoAbreviada() + ", ";
			}
			empresas = empresas.substring(0, empresas.length() - 2);
			parametrosFormatados.put("empresa", empresas);
		}

		// Parametro servicoTipo
		String servicoTipos = "";
		Collection colServicoTiposId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getServicoTipo() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getServicoTipo().length; i++){
				colServicoTiposId.add(ordemServicoSeletivaComandoHelper.getServicoTipo()[i]);
			}
			Collection colServicoTipo = fachada.pesquisar(colServicoTiposId, new FiltroServicoTipo(), ServicoTipo.class.getName());
			for(Object servicoTipo : colServicoTipo){
				servicoTipos = servicoTipos + ((ServicoTipo) servicoTipo).getDescricao() + ", ";
			}
			servicoTipos = servicoTipos.substring(0, servicoTipos.length() - 2);
			parametrosFormatados.put("servicoTipo", servicoTipos);
		}

		// Parametro elo
		String elos = "";
		Collection colElosId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getElo() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getElo().length; i++){
				colElosId.add(ordemServicoSeletivaComandoHelper.getElo()[i]);
			}
			Collection colElo = fachada.pesquisar(colElosId, new FiltroLocalidade(), Localidade.class.getName());
			for(Object elo : colElo){
				elos = elos + ((Localidade) elo).getDescricao() + ", ";
			}
			elos = elos.substring(0, elos.length() - 2);
			parametrosFormatados.put("elo", elos);
		}

		// Parametro grupo faturamento
		String faturamentoGrupos = "";
		Collection colFaturamentoGruposId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getFaturamentoGrupo() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getFaturamentoGrupo().length; i++){
				colFaturamentoGruposId.add(ordemServicoSeletivaComandoHelper.getFaturamentoGrupo()[i]);
			}
			Collection colGrupoFaturamento = fachada.pesquisar(colFaturamentoGruposId, new FiltroFaturamentoGrupo(),
							FaturamentoGrupo.class.getName());
			for(Object faturamentoGrupo : colGrupoFaturamento){
				faturamentoGrupos = faturamentoGrupos + ((FaturamentoGrupo) faturamentoGrupo).getDescricao() + ", ";
			}
			faturamentoGrupos = faturamentoGrupos.substring(0, faturamentoGrupos.length() - 2);
			parametrosFormatados.put("faturamentoGrupo", faturamentoGrupos);
		}
		
		// Parametro gerencia regional
		String gerenciasRegionais = "";
		Collection colGerenciasRegionaisId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getGerenciaRegional() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getFaturamentoGrupo().length; i++){
				colGerenciasRegionaisId.add(ordemServicoSeletivaComandoHelper.getFaturamentoGrupo()[i]);
			}
			Collection colGerenciaRegional = fachada.pesquisar(colGerenciasRegionaisId, new FiltroGerenciaRegional(),
							GerenciaRegional.class.getName());
			for(Object gerenciaRegional : colGerenciaRegional){
				gerenciasRegionais = gerenciasRegionais + ((GerenciaRegional) gerenciaRegional).getNome() + ", ";
			}
			gerenciasRegionais = gerenciasRegionais.substring(0, gerenciasRegionais.length() - 2);
			parametrosFormatados.put("gerenciaRegional", gerenciasRegionais);
		}

		// Parametro localidade
		String localidades = "";
		Collection colLocalidadesId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getLocalidade() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getLocalidade().length; i++){
				colLocalidadesId.add(ordemServicoSeletivaComandoHelper.getLocalidade()[i]);
			}
			Collection colLocalidade = fachada.pesquisar(colLocalidadesId, new FiltroLocalidade(), Localidade.class.getName());
			for(Object localidade : colLocalidade){
				localidades = localidades + ((Localidade) localidade).getDescricao() + ", ";
			}
			localidades = localidades.substring(0, localidades.length() - 2);
			parametrosFormatados.put("localidade", localidades);
		}

		// Parametro setor
		String setores = "";
		Collection colSetoresId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getSetor() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getSetor().length; i++){
				colSetoresId.add(ordemServicoSeletivaComandoHelper.getSetor()[i]);
			}
			Collection colSetor = fachada.pesquisar(colSetoresId, new FiltroSetorComercial(), SetorComercial.class.getName());
			for(Object setor : colSetor){
				setores = setores + ((SetorComercial) setor).getDescricao() + ", ";
			}
			setores = setores.substring(0, setores.length() - 2);
			parametrosFormatados.put("setor", setores);
		}

		// Parametro quadra
		String quadras = "";
		Collection colQuadrasId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getQuadra() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getQuadra().length; i++){
				colQuadrasId.add(ordemServicoSeletivaComandoHelper.getQuadra()[i]);
			}
			Collection colQuadra = fachada.pesquisar(colQuadrasId, new FiltroQuadra(), Quadra.class.getName());
			for(Object quadra : colQuadra){
				quadras = quadras + ((Quadra) quadra).getDescricao() + ", ";
			}
			quadras = quadras.substring(0, quadras.length() - 2);
			parametrosFormatados.put("quadra", quadras);
		}

		// Parametro rota
		String rotas = "";
		Collection colRotasId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getRota() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getRota().length; i++){
				colRotasId.add(ordemServicoSeletivaComandoHelper.getRota()[i]);
			}
			Collection colRota = fachada.pesquisar(colRotasId, new FiltroRota(), Rota.class.getName());
			for(Object rota : colRota){
				rotas = rotas + ((Rota) rota).getDescricao() + ", ";
			}
			rotas = rotas.substring(0, rotas.length() - 2);
			parametrosFormatados.put("rota", rotas);
		}

		// Parametro lote
		String lotes = "";
		if(ordemServicoSeletivaComandoHelper.getLote() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getLote().length; i++){
				lotes = lotes + ordemServicoSeletivaComandoHelper.getLote()[i].toString() + ", ";
			}
			lotes = lotes.substring(0, lotes.length() - 2);
			parametrosFormatados.put("lote", lotes);
		}

		// Parametro bairro
		String bairros = "";
		Collection colBairrosId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getBairro() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getBairro().length; i++){
				colBairrosId.add(ordemServicoSeletivaComandoHelper.getBairro()[i]);
			}
			Collection colBairro = fachada.pesquisar(colBairrosId, new FiltroBairro(), Bairro.class.getName());
			for(Object bairro : colBairro){
				bairros = bairros + ((Bairro) bairro).getNome() + ", ";
			}
			bairros = bairros.substring(0, bairros.length() - 2);
			parametrosFormatados.put("bairro", bairros);
		}

		// Parametro logradouro
		String logradouros = "";
		Collection colLogradourosId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getLogradouro() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getLogradouro().length; i++){
				colLogradourosId.add(ordemServicoSeletivaComandoHelper.getLogradouro()[i]);
			}
			Collection colLogradouro = fachada.pesquisar(colLogradourosId, new FiltroLogradouro(), Logradouro.class.getName());
			for(Object logradouro : colLogradouro){
				logradouros = logradouros + ((Logradouro) logradouro).getNome() + ", ";
			}
			logradouros = logradouros.substring(0, logradouros.length() - 2);
			parametrosFormatados.put("logradouro", logradouros);
		}

		// Parametro perfil imovel
		String perfilImoveis = "";
		Collection colPerfilImoveisId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getPerfilImovel() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getPerfilImovel().length; i++){
				colPerfilImoveisId.add(ordemServicoSeletivaComandoHelper.getPerfilImovel()[i]);
			}
			Collection colPerfilImovel = fachada.pesquisar(colPerfilImoveisId, new FiltroImovelPerfil(), ImovelPerfil.class.getName());
			for(Object perfilImovel : colPerfilImovel){
				perfilImoveis = perfilImoveis + ((ImovelPerfil) perfilImovel).getDescricao() + ", ";
			}
			perfilImoveis = perfilImoveis.substring(0, perfilImoveis.length() - 2);
			parametrosFormatados.put("perfilImovel", perfilImoveis);
		}

		// Parametro categoria
		String categorias = "";
		Collection colCategoriasId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getCategoria() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getCategoria().length; i++){
				colCategoriasId.add(ordemServicoSeletivaComandoHelper.getCategoria()[i]);
			}
			Collection colCategoria = fachada.pesquisar(colCategoriasId, new FiltroCategoria(), Categoria.class.getName());
			for(Object categoria : colCategoria){
				categorias = categorias + ((Categoria) categoria).getDescricao() + ", ";
			}
			categorias = categorias.substring(0, categorias.length() - 2);
			parametrosFormatados.put("categoria", categorias);
		}

		// Parametro subcategoria
		String subCategorias = "";
		Collection colSubCategoriasId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getSubCategoria() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getSubCategoria().length; i++){
				colSubCategoriasId.add(ordemServicoSeletivaComandoHelper.getSubCategoria()[i]);
			}
			Collection colSubCategoria = fachada.pesquisar(colSubCategoriasId, new FiltroSubCategoria(), Subcategoria.class.getName());
			for(Object subCategoria : colSubCategoria){
				subCategorias = subCategorias + ((Subcategoria) subCategoria).getDescricao() + ", ";
			}
			subCategorias = subCategorias.substring(0, subCategorias.length() - 2);
			parametrosFormatados.put("subCategoria", subCategorias);
		}

		// Parametro ligacao agua situacao
		String ligacaoAguaSituacoes = "";
		Collection colLigacaoAguaSituacoesId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getLigacaoAguaSituacao() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getLigacaoAguaSituacao().length; i++){
				colLigacaoAguaSituacoesId.add(ordemServicoSeletivaComandoHelper.getLigacaoAguaSituacao()[i]);
			}
			Collection colLigacaoAguaSituacao = fachada.pesquisar(colLigacaoAguaSituacoesId, new FiltroLigacaoAguaSituacao(),
							LigacaoAguaSituacao.class.getName());
			for(Object ligacaoAguaSituacao : colLigacaoAguaSituacao){
				ligacaoAguaSituacoes = ligacaoAguaSituacoes + ((LigacaoAguaSituacao) ligacaoAguaSituacao).getDescricao() + ", ";
			}
			ligacaoAguaSituacoes = ligacaoAguaSituacoes.substring(0, ligacaoAguaSituacoes.length() - 2);
			parametrosFormatados.put("ligacaoAguaSituacao", ligacaoAguaSituacoes);
		}

		// Parametro ligacao esgoto situacao
		String ligacaoEsgotoSituacoes = "";
		Collection colLigacaoEsgotoSituacoesId = new ArrayList();
		if(ordemServicoSeletivaComandoHelper.getLigacaoEsgotoSituacao() != null){
			for(int i = 0; i < ordemServicoSeletivaComandoHelper.getLigacaoEsgotoSituacao().length; i++){
				colLigacaoAguaSituacoesId.add(ordemServicoSeletivaComandoHelper.getLigacaoEsgotoSituacao()[i]);
			}
			Collection colLigacaoEsgotoSituacao = fachada.pesquisar(colLigacaoEsgotoSituacoesId, new FiltroLigacaoEsgotoSituacao(),
							LigacaoEsgotoSituacao.class.getName());
			for(Object ligacaoEsgotoSituacao : colLigacaoEsgotoSituacao){
				ligacaoEsgotoSituacoes = ligacaoEsgotoSituacoes + ((LigacaoEsgotoSituacao) ligacaoEsgotoSituacao).getDescricao() + ", ";
			}
			ligacaoEsgotoSituacoes = ligacaoEsgotoSituacoes.substring(0, ligacaoEsgotoSituacoes.length() - 2);
			parametrosFormatados.put("ligacaoEsgotoSituacao", ligacaoEsgotoSituacoes);
		}

		return parametrosFormatados;

	}

}
