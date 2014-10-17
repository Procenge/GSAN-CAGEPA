
package gcom.relatorio.cobranca.prescricao;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.FiltroImovelSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.FiltroImoveisComDebitosPrescritosHelper;
import gcom.cobranca.bean.ImovelComDebitosPrescritosHelper;
import gcom.cobranca.bean.PrescricaoContaHelper;
import gcom.cobranca.bean.PrescricaoGuiaPrestacaoHelper;
import gcom.fachada.Fachada;
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

import java.util.*;

/**
 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
 * [SB0006] - Imprimir Relação dos Imóveis com Itens de Débitos Prescritos
 * 
 * @author Anderson Italo
 * @date 06/04/2014
 */
public class RelatorioAcompanhamentoDebitosPrescritos
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAcompanhamentoDebitosPrescritos(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS);
	}

	@Deprecated
	public RelatorioAcompanhamentoDebitosPrescritos() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		int tipoFormatoRelatorio = TarefaRelatorio.TIPO_PDF;
		String parametrosFiltro = (String) getParametro("parametrosFiltro");
		FiltroImoveisComDebitosPrescritosHelper filtroImoveisComDebitosPrescritosHelper = (FiltroImoveisComDebitosPrescritosHelper) getParametro("filtroImoveisComDebitosPrescritosHelper");

		Fachada fachada = Fachada.getInstancia();

		Collection<ImovelComDebitosPrescritosHelper> colecaoImovelComDebitosPrescritosHelper = fachada.pesquisarImoveisComDebitoPrescrito(
						filtroImoveisComDebitosPrescritosHelper, ConstantesSistema.NUMERO_NAO_INFORMADO);

		List<RelatorioAcompanhamentoDebitosPrescritosBean> colecaoBeansRelatorioDebitosPrescritos = new ArrayList<RelatorioAcompanhamentoDebitosPrescritosBean>();
		List<SubRelatorioAcompanhamentoDebitosPrescritosCategoriaBean> colecaoBeansCategoriasRelatorioDebitosPrescritos = null;
		List<SubRelatorioAcompanhamentoDebitosPrescritosContaBean> colecaoBeansContasRelatorioDebitosPrescritos = null;
		List<SubRelatorioAcompanhamentoDebitosPrescritosGuiaPagamentoBean> colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos = null;
		Imovel imovel = null;
		Localidade localidade = null;
		SetorComercial setorComercial = null;
		Collection<PrescricaoContaHelper> colecaoPrescricaoContaHelper = null;
		Collection<PrescricaoGuiaPrestacaoHelper> colecaoPrescricaoGuiaPrestacaoHelper = null;
		FiltroImovelSubCategoria filtroImovelSubcategoria = new FiltroImovelSubCategoria();
		Collection<ImovelSubcategoria> colecaoImovelSubcategorias = null;

		for(ImovelComDebitosPrescritosHelper helperRelatorio : colecaoImovelComDebitosPrescritosHelper){

			RelatorioAcompanhamentoDebitosPrescritosBean beanPrincipalImovel = new RelatorioAcompanhamentoDebitosPrescritosBean();
			colecaoBeansCategoriasRelatorioDebitosPrescritos = new ArrayList<SubRelatorioAcompanhamentoDebitosPrescritosCategoriaBean>();
			colecaoBeansContasRelatorioDebitosPrescritos = new ArrayList<SubRelatorioAcompanhamentoDebitosPrescritosContaBean>();
			colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos = new ArrayList<SubRelatorioAcompanhamentoDebitosPrescritosGuiaPagamentoBean>();

			// Imóvel
			imovel = new Imovel();
			imovel.setId(helperRelatorio.getIdImovel());

			// Localidade
			localidade = new Localidade(helperRelatorio.getIdLocalidade());
			imovel.setLocalidade(localidade);

			// Setor Comercial
			setorComercial = new SetorComercial();
			setorComercial.setCodigo(helperRelatorio.getCodigoSetorComercial());
			imovel.setSetorComercial(setorComercial);

			// Quadra
			imovel.setQuadra(new Quadra(0));
			imovel.getQuadra().setNumeroQuadra(helperRelatorio.getNumeroQuadra());

			// Lote
			imovel.setLote(helperRelatorio.getLote());

			// Sublote
			imovel.setSubLote(helperRelatorio.getSublote());

			// Inscrição
			beanPrincipalImovel.setInscricao(imovel.getInscricaoFormatada());

			// Matrícula
			beanPrincipalImovel.setMatriculaFormatada(helperRelatorio.getIdImovel().toString()
							.substring(0, helperRelatorio.getIdImovel().toString().toString().length() - 1)
							+ "."
							+ helperRelatorio.getIdImovel().toString().toString()
											.substring(helperRelatorio.getIdImovel().toString().toString().length() - 1));
			beanPrincipalImovel.setIdImovel(helperRelatorio.getIdImovel());

			// Endereço
			beanPrincipalImovel.setEnderecoImovel(fachada.obterEnderecoAbreviadoImovel(helperRelatorio.getIdImovel()));

			// Situação de Água
			beanPrincipalImovel.setDescricaoLigacaoAguaSituacao(helperRelatorio.getDescricaoLigacaoAguaSituacao());

			// Situação de Esgoto
			beanPrincipalImovel.setDescricaoLigacaoEsgotoSituacao(helperRelatorio.getDescricaoLigacaoEsgotoSituacao());

			// Perfil do Imóvel
			beanPrincipalImovel.setDescricaoImovelPerfil(helperRelatorio.getDescricaoImovelPerfil());

			// Categoria
			filtroImovelSubcategoria.limparListaParametros();
			filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, helperRelatorio
							.getIdImovel()));
			filtroImovelSubcategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.CATEGORIA);
			filtroImovelSubcategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.SUBCATEGORIA);
			filtroImovelSubcategoria.setCampoOrderBy(FiltroImovelSubCategoria.CATEGORIA_ID, FiltroImovelSubCategoria.SUBCATEGORIA_ID);
			colecaoImovelSubcategorias = (Collection<ImovelSubcategoria>) fachada.pesquisar(filtroImovelSubcategoria,
							ImovelSubcategoria.class.getName());

			for(ImovelSubcategoria imovelSubcategoria : colecaoImovelSubcategorias){

				SubRelatorioAcompanhamentoDebitosPrescritosCategoriaBean beanSubCategoria = new SubRelatorioAcompanhamentoDebitosPrescritosCategoriaBean();

				// Categoria
				beanSubCategoria.setDescricaoCategoria(imovelSubcategoria.getCategoria().getDescricao());

				// Subcategoria
				beanSubCategoria.setDescricaoSubcategoria(imovelSubcategoria.getComp_id().getSubcategoria().getDescricao());

				// Economias
				beanSubCategoria.setQuantidadeEconomias(String.valueOf(imovelSubcategoria.getQuantidadeEconomias()));

				colecaoBeansCategoriasRelatorioDebitosPrescritos.add(beanSubCategoria);
			}
			
			beanPrincipalImovel.setarBeansCategorias(colecaoBeansCategoriasRelatorioDebitosPrescritos);

			// Contas Prescritas
			beanPrincipalImovel.setQuantidadeContasMarcadas(helperRelatorio.getQuantidadeContasMarcadas());
			beanPrincipalImovel.setValorTotalContasMarcadas(helperRelatorio.getValorContasMarcadas());
			beanPrincipalImovel.setQuantidadeContasDesmarcadas(helperRelatorio.getQuantidadeContasDesmarcadas());
			beanPrincipalImovel.setValorTotalContasDesmarcadas(helperRelatorio.getValorContasDesmarcadas());

			// Para cada grupo de contas, da lista dos itens de débitos prescritos
			colecaoPrescricaoContaHelper = fachada.pesquisarContasPrescritas(helperRelatorio.getIdImovel(),
							filtroImoveisComDebitosPrescritosHelper);

			if(!Util.isVazioOrNulo(colecaoPrescricaoContaHelper)){

				for(PrescricaoContaHelper prescricaoContaHelper : colecaoPrescricaoContaHelper){

					SubRelatorioAcompanhamentoDebitosPrescritosContaBean beanSubConta = new SubRelatorioAcompanhamentoDebitosPrescritosContaBean();

					// Referência da Conta
					beanSubConta.setReferencia(prescricaoContaHelper.getReferenciaFormatada());

					// Data de Vencimento da Conta
					beanSubConta.setDataVencimento(Util.formatarData(prescricaoContaHelper.getDataVencimentoConta()));

					// Valor de Água
					beanSubConta.setValorAgua(prescricaoContaHelper.getValorAgua());

					// Valor de Esgoto
					beanSubConta.setValorEsgoto(prescricaoContaHelper.getValorEsgoto());

					// Valor de Débitos
					beanSubConta.setValorDebitos(prescricaoContaHelper.getValorDebitos());

					// Valor de Créditos
					beanSubConta.setValorCreditos(prescricaoContaHelper.getValorCreditos());

					// Valor de Impostos
					beanSubConta.setValorImpostos(prescricaoContaHelper.getValorImpostos());

					// Valor da Conta
					beanSubConta.setValorTotalConta(prescricaoContaHelper.getValorConta());

					// Situação da Conta
					beanSubConta.setDescricaoDebitoCreditoSituacao(prescricaoContaHelper.getDescricaoDebitoCreditoSituacao());

					// Paga
					beanSubConta.setIndicadorPaga(prescricaoContaHelper.getIndicadorPaga());

					colecaoBeansContasRelatorioDebitosPrescritos.add(beanSubConta);
				}

				beanPrincipalImovel.setarBeansContas(colecaoBeansContasRelatorioDebitosPrescritos);
			}

			// Prestações de Guia Prescritas
			beanPrincipalImovel.setQuantidadeGuiasPrestacaoMarcadas(helperRelatorio.getQuantidadeGuiasPrestacaoMarcadas());
			beanPrincipalImovel.setValorTotalGuiasPrestacaoMarcadas(helperRelatorio.getValorGuiasPrestacaoMarcadas());
			beanPrincipalImovel.setQuantidadeGuiasPrestacaoDesmarcadas(helperRelatorio.getQuantidadeGuiasPrestacaoDesmarcadas());
			beanPrincipalImovel.setValorTotalGuiasPrestacaoDesmarcadas(helperRelatorio.getValorGuiasPrestacaoDesmarcadas());

			// Para cada grupo de prestações de guia de pagamento, da lista dos itens de débitos
			// prescritos
			colecaoPrescricaoGuiaPrestacaoHelper = fachada.pesquisarGuiasPrestacaoPrescritas(helperRelatorio.getIdImovel(),
							filtroImoveisComDebitosPrescritosHelper);

			if(!Util.isVazioOrNulo(colecaoPrescricaoGuiaPrestacaoHelper)){
				
				for(PrescricaoGuiaPrestacaoHelper prescricaoGuiaPrestacaoHelper : colecaoPrescricaoGuiaPrestacaoHelper){

					SubRelatorioAcompanhamentoDebitosPrescritosGuiaPagamentoBean beanSubGuiaPagamento = new SubRelatorioAcompanhamentoDebitosPrescritosGuiaPagamentoBean();

					// Guia
					beanSubGuiaPagamento.setIdGuia(prescricaoGuiaPrestacaoHelper.getIdGuiaPagamento().toString());

					// Prestação
					beanSubGuiaPagamento.setPrestacao(prescricaoGuiaPrestacaoHelper.getNumeroPrestacao().toString());

					// Tipo do Débito
					beanSubGuiaPagamento.setDescricaoTipoDebito(prescricaoGuiaPrestacaoHelper.getDescricaoTipoDebito());

					// Data de Emissão
					beanSubGuiaPagamento.setDataEmissao(Util.formatarData(prescricaoGuiaPrestacaoHelper.getDataEmissao()));

					// Data de Vencimento da Prestação
					beanSubGuiaPagamento.setDataVencimento(Util.formatarData(prescricaoGuiaPrestacaoHelper.getDataVencimento()));

					// Valor da Prestação
					beanSubGuiaPagamento.setValorPrestacao(prescricaoGuiaPrestacaoHelper.getValorPrestacao());

					// Situação da Prestação
					beanSubGuiaPagamento.setDescricaoDebitoCreditoSituacao(prescricaoGuiaPrestacaoHelper
									.getDescricaoDebitoCreditoSituacao());

					// Paga
					beanSubGuiaPagamento.setIndicadorPaga(prescricaoGuiaPrestacaoHelper.getIndicadorPaga());

					colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos.add(beanSubGuiaPagamento);
				}

				beanPrincipalImovel.setarBeansGuias(colecaoBeansGuiasPagamentoRelatorioDebitosPrescritos);
			}

			colecaoBeansRelatorioDebitosPrescritos.add(beanPrincipalImovel);
		}

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		Map parametros = new HashMap();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("parametrosFiltro", parametrosFiltro);

		RelatorioDataSource ds = new RelatorioDataSource(colecaoBeansRelatorioDebitosPrescritos);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS, parametros, ds, tipoFormatoRelatorio);

		try{

			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS, idFuncionalidadeIniciada,
							"RELAÇÃO DOS IMÓVEIS COM ITENS DE DÉBITOS PRESCRITOS");
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}


	public int calcularTotalRegistrosRelatorio(){

		FiltroImoveisComDebitosPrescritosHelper filtroImoveisComDebitosPrescritosHelper = (FiltroImoveisComDebitosPrescritosHelper) getParametro("filtroImoveisComDebitosPrescritosHelper");
		int totalRegistros = Fachada.getInstancia().pesquisarQuantidadeImoveisComDebitosPrescritos(filtroImoveisComDebitosPrescritosHelper);

		return totalRegistros;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAcompanhamentoDebitosPrescritos", this);
	}
}