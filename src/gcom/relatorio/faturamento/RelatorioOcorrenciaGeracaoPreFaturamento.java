
package gcom.relatorio.faturamento;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 * Relatório de Ocorrência da Geração do Pré-Faturamento
 * 
 * @author Hebert Falcão
 * @date 06/04/2012
 */
public class RelatorioOcorrenciaGeracaoPreFaturamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = -6421051804181251492L;

	public RelatorioOcorrenciaGeracaoPreFaturamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO);
	}

	@Deprecated
	public RelatorioOcorrenciaGeracaoPreFaturamento() {

		super(null, "");
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioOcorrenciaGeracaoPreFaturamento", this);
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		String imagemRelatorio = sistemaParametro.getImagemRelatorio();

		String idGrupoFaturamento = (String) this.getParametro("idGrupoFaturamento");

		String anoMesReferencia = (String) this.getParametro("anoMesReferencia");

		Collection<Imovel> colecaoOcorrenciaGeracaoPreFaturamento = (Collection<Imovel>) this
						.getParametro("colecaoOcorrenciaGeracaoPreFaturamento");

		String dataVencimento = (String) this.getParametro("dataVencimento");

		String referenciaTarifa = (String) this.getParametro("referenciaTarifa");

		// Parâmetros do relatório
		Map<String, String> parametros = new HashMap();

		parametros.put("imagem", imagemRelatorio);
		parametros.put("referenciaFaturamento", anoMesReferencia);
		parametros.put("grupoFaturamento", idGrupoFaturamento);
		parametros.put("dataVencimento", dataVencimento);
		parametros.put("referenciaTarifa", referenciaTarifa);
		parametros.put("tipoFormatoRelatorio", "PDF");

		Collection<RelatorioOcorrenciaGeracaoPreFaturamentoBean> colecaoBean = this
						.inicializarBeanRelatorio(colecaoOcorrenciaGeracaoPreFaturamento);

		if(Util.isVazioOrNulo(colecaoBean)){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((java.util.List) colecaoBean);

		byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO, parametros, ds,
						TarefaRelatorio.TIPO_PDF);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	private Collection<RelatorioOcorrenciaGeracaoPreFaturamentoBean> inicializarBeanRelatorio(
					Collection<Imovel> colecaoOcorrenciaGeracaoPreFaturamento){

		Fachada fachada = Fachada.getInstancia();

		Collection<RelatorioOcorrenciaGeracaoPreFaturamentoBean> colecaoBean = new ArrayList<RelatorioOcorrenciaGeracaoPreFaturamentoBean>();
		Collection<RelatorioOcorrenciaGeracaoPreFaturamentoBean> colecaoBeanOrdenada = new ArrayList<RelatorioOcorrenciaGeracaoPreFaturamentoBean>();

		if(!Util.isVazioOrNulo(colecaoOcorrenciaGeracaoPreFaturamento)){
			RelatorioOcorrenciaGeracaoPreFaturamentoBean bean = null;

			String descricaoLocalidade = "";
			String matricula = "";
			String inscricao = "";
			String endereco = "";
			String situacaoAgua = "";
			String situacaoEsgoto = "";
			String categoriasEconomias = "";

			Localidade localidade = null;
			Integer idLocalidade = null;

			Integer idImovel = null;

			LigacaoAguaSituacao ligacaoAguaSituacao = null;
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

			Collection colecaoCategoria = null;
			Categoria categoria = null;

			for(Imovel imovel : colecaoOcorrenciaGeracaoPreFaturamento){
				bean = new RelatorioOcorrenciaGeracaoPreFaturamentoBean();

				// Localidade
				localidade = imovel.getLocalidade();

				if(localidade != null){
					idLocalidade = localidade.getId();

					localidade = fachada.pesquisarLocalidadeDigitada(idLocalidade);

					descricaoLocalidade = localidade.getDescricaoComId();
				}else{
					descricaoLocalidade = "";
				}

				bean.setLocalidade(descricaoLocalidade);

				// Matrícula
				idImovel = imovel.getId();

				matricula = Integer.toString(idImovel);

				bean.setMatricula(matricula);

				// Inscrição
				inscricao = imovel.getInscricaoFormatada();

				bean.setInscricao(inscricao);

				// Endereço
				try{
					endereco = fachada.pesquisarEnderecoFormatado(idImovel);
				}catch(ControladorException ex){
					throw new TarefaException(ex.getMessage(), ex);
				}

				bean.setEndereco(endereco);

				// Situação da Ligação

				// Água
				ligacaoAguaSituacao = fachada.pesquisarLigacaoAguaSituacao(idImovel);

				if(ligacaoAguaSituacao != null){
					situacaoAgua = ligacaoAguaSituacao.getDescricaoComId();
				}else{
					situacaoAgua = "";
				}

				bean.setSituacaoAgua(situacaoAgua);

				// Esgoto
				ligacaoEsgotoSituacao = fachada.pesquisarLigacaoEsgotoSituacao(idImovel);

				if(ligacaoEsgotoSituacao != null){
					situacaoEsgoto = ligacaoEsgotoSituacao.getDescricaoComId();
				}else{
					situacaoEsgoto = "";
				}

				bean.setSituacaoEsgoto(situacaoEsgoto);

				// Categoria - Economias
				// R-000 C-000 I-000 P-000, onde o R – Categoria Residencial, C – Categoria Comercial,
				// I – Categoria Industrial e P – Categoria Pública e 000 – Número de economias da
				// Categoria
				colecaoCategoria = fachada.obterQuantidadeEconomiasCategoria(imovel);
				Iterator colecaoCategoriaIterator = colecaoCategoria.iterator();
				categoriasEconomias = "";

				while(colecaoCategoriaIterator.hasNext()){

					categoria = (Categoria) colecaoCategoriaIterator.next();
					String quantidadeEconomias = categoria.getQuantidadeEconomiasCategoria().toString();

					if(categoria.getId().intValue() == Categoria.COMERCIAL_INT){

						categoriasEconomias += " C-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
					}else if(categoria.getId().intValue() == Categoria.PUBLICO_INT){

						categoriasEconomias += " P-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
					}else if(categoria.getId().intValue() == Categoria.RESIDENCIAL_INT){

						categoriasEconomias += " R-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
					}else if(categoria.getId().intValue() == Categoria.INDUSTRIAL_INT){

						categoriasEconomias += " I-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
					}
				}

				bean.setCategoria(categoriasEconomias);
				

				// Mensagem
				bean.setMensagem("PARCELAMENTO DESCUMPRIDO");

				colecaoBean.add(bean);
			}
		}

		colecaoBeanOrdenada.addAll(colecaoBean);

		// Ordenação
		List sortFields = new ArrayList();
		sortFields.add(new BeanComparator("localidade"));
		sortFields.add(new BeanComparator("matricula"));

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort((List) colecaoBeanOrdenada, multiSort);

		return colecaoBeanOrdenada;
	}

}
