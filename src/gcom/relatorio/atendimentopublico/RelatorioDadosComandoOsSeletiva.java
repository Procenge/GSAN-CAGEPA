package gcom.relatorio.atendimentopublico;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.atendimentopublico.ordemservico.ConsultarComandoOsSeletivaActionForm;
import gcom.gui.atendimentopublico.ordemservico.DadosOrdemServicoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;

import java.util.*;

public class RelatorioDadosComandoOsSeletiva
				extends TarefaRelatorio {

	public RelatorioDadosComandoOsSeletiva(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_DADOS_COMANDO_OS_SELETIVA);
		// TODO Auto-generated constructor stub
	}

	@Deprecated
	public RelatorioDadosComandoOsSeletiva() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object executar() throws TarefaException{

		ConsultarComandoOsSeletivaActionForm consultarComandoOsSeletivaActionForm = (ConsultarComandoOsSeletivaActionForm) getParametro("consultarComandoOsSeletivaActionForm");
		ArrayList<DadosOrdemServicoHelper> relacaoDadosOrdemServico = (ArrayList<DadosOrdemServicoHelper>) consultarComandoOsSeletivaActionForm
						.getColecaoDadosOrdemServico();
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		ArrayList<Integer> idImoveis = (ArrayList<Integer>) getParametro("colecaoImoveis");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioDadosComandoOsSeletivaBean relatorioBean = null;

		DadosOrdemServicoHelper dadosOrdemServico = null;

		String os = "";
		String servicoTipoOs = "";
		String imovelOs = "";
		String localidadeOs = "";
		String setorOs = "";
		String quadraOs = "";
		String situacaoOs = "";
		String dataGeracaoOs = "";
		String unidadeGeracaoOs = "";
		String dataEncerramentoOs = "";
		String unidadeEncerramentoOs = "";
		String motivoEncerramentoOs = "";

		if(relacaoDadosOrdemServico != null && !relacaoDadosOrdemServico.isEmpty()){
			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoDadosOrdemServicoIterator = relacaoDadosOrdemServico.iterator();
						
			// laço para criar a coleção de parâmetros da analise
			while(colecaoDadosOrdemServicoIterator.hasNext()){

				dadosOrdemServico = (DadosOrdemServicoHelper) colecaoDadosOrdemServicoIterator.next();

				// os
				if(dadosOrdemServico.getIdOrdemServico() != null){
					os = dadosOrdemServico.getIdOrdemServico().toString();
				}

				// servicoTipoOs
				if(dadosOrdemServico.getIdServicoTipo() != null){
					servicoTipoOs = dadosOrdemServico.getIdServicoTipo().toString();
				}

				// imovelOs
				if(dadosOrdemServico.getIdImovel() != null){
					imovelOs = dadosOrdemServico.getIdImovel().toString();
				}

				// localidadeOs
				if(dadosOrdemServico.getIdLocalidade() != null){
					localidadeOs = dadosOrdemServico.getIdLocalidade().toString();
				}

				// setorOs
				if(dadosOrdemServico.getCodigoSetor() != null){
					setorOs = dadosOrdemServico.getCodigoSetor().toString();
				}

				// quadraOs
				if(dadosOrdemServico.getNumeroQuadra() != null){
					quadraOs = dadosOrdemServico.getNumeroQuadra().toString();
				}

				// situacaoOs
				if(dadosOrdemServico.getDescricaoAbreviada() != null){
					situacaoOs = dadosOrdemServico.getDescricaoAbreviada();
				}

				// dataGeracaoOs
				if(dadosOrdemServico.getDataGeracao() != null){
					dataGeracaoOs = dadosOrdemServico.getDataGeracao();
				}

				// unidadeGeracaoOs
				if(dadosOrdemServico.getIdUnidadeGeracao() != null){
					unidadeGeracaoOs = dadosOrdemServico.getIdUnidadeGeracao().toString();
				}

				// dataEncerramentoOs
				if(dadosOrdemServico.getDataEncerramento() != null){
					dataEncerramentoOs = dadosOrdemServico.getDataEncerramento();
				}

				// unidadeEncerramentoOs
				if(dadosOrdemServico.getIdUnidadeEncerramento() != null){
					unidadeEncerramentoOs = dadosOrdemServico.getIdUnidadeEncerramento().toString();
				}

				// motivoEncerramentoOs
				if(dadosOrdemServico.getIdMotivoEncerramento() != null){
					motivoEncerramentoOs = dadosOrdemServico.getIdMotivoEncerramento().toString();
				}

				relatorioBean = new RelatorioDadosComandoOsSeletivaBean(os, servicoTipoOs, imovelOs, localidadeOs, setorOs, quadraOs,
								situacaoOs, dataGeracaoOs, unidadeGeracaoOs, dataEncerramentoOs, unidadeEncerramentoOs,
								motivoEncerramentoOs);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);

			}
		}else{
			relatorioBean = new RelatorioDadosComandoOsSeletivaBean(os, servicoTipoOs, imovelOs, localidadeOs, setorOs, quadraOs,
							situacaoOs, dataGeracaoOs, unidadeGeracaoOs, dataEncerramentoOs, unidadeEncerramentoOs, motivoEncerramentoOs);

			// adiciona o bean a coleção
			relatorioBeans.add(relatorioBean);
		}


		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(consultarComandoOsSeletivaActionForm != null){

			// titulo
			String titulo = "";
			if(consultarComandoOsSeletivaActionForm.getTitulo() != null){
				titulo = consultarComandoOsSeletivaActionForm.getTitulo();
			}

			// empresa
			String empresa = "";
			if(consultarComandoOsSeletivaActionForm.getIdFirma() != null
							&& consultarComandoOsSeletivaActionForm.getDescricaoFirma() != null){
				empresa = consultarComandoOsSeletivaActionForm.getIdFirma() + " - "
								+ consultarComandoOsSeletivaActionForm.getDescricaoFirma();
			}

			// servicoTipo
			String servicoTipo = "";
			if(consultarComandoOsSeletivaActionForm.getIdServicoTipo() != null
							&& consultarComandoOsSeletivaActionForm.getDescricaoServicoTipo() != null){
				servicoTipo = consultarComandoOsSeletivaActionForm.getIdServicoTipo() + " - "
								+ consultarComandoOsSeletivaActionForm.getDescricaoServicoTipo();
			}

			// qtdeMaxima
			String qtdeMaxima = "";
			if(consultarComandoOsSeletivaActionForm.getQuantidadeMaximaOrdens() != null){
				qtdeMaxima = consultarComandoOsSeletivaActionForm.getQuantidadeMaximaOrdens();
			}

			// imoveis
			String imoveis = "";
			if(idImoveis != null){
				for(Integer idImovel : idImoveis){

					imoveis = imoveis + idImovel + ", ";
				}
				imoveis = imoveis.substring(0, imoveis.length() - 2);
			}

			// elo
			String elo = "";
			if(consultarComandoOsSeletivaActionForm.getIdElo() != null && consultarComandoOsSeletivaActionForm.getDescricaoElo() != null){
				elo = consultarComandoOsSeletivaActionForm.getIdElo() + " - " + consultarComandoOsSeletivaActionForm.getDescricaoElo();
			}

			// faturamentoGrupo
			String faturamentoGrupo = "";
			if(consultarComandoOsSeletivaActionForm.getIdFaturamentoGrupo() != null
							&& consultarComandoOsSeletivaActionForm.getDescricaoFaturamentoGrupo() != null){
				faturamentoGrupo = consultarComandoOsSeletivaActionForm.getIdFaturamentoGrupo() + " - "
								+ consultarComandoOsSeletivaActionForm.getDescricaoFaturamentoGrupo();
			}

			parametros.put("titulo", titulo);
			parametros.put("empresa", empresa);
			parametros.put("servicoTipo", servicoTipo);
			parametros.put("qtdeMaxima", qtdeMaxima);
			parametros.put("imoveis", imoveis);
			parametros.put("elo", elo);
			parametros.put("faturamentoGrupo", faturamentoGrupo);
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_DADOS_COMANDO_OS_SELETIVA, parametros, ds, tipoFormatoRelatorio);

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub

	}

}
