package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.batch.Relatorio;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.FiltroClienteFone;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.bean.ItensRemuradosHelper;
import gcom.fachada.Fachada;
import gcom.gui.cobranca.CobrancaAdministrativaHelper;
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

import java.math.BigDecimal;
import java.util.*;

public class RelatorioAnaliticoImoveisEmCobrancaAdministrativa
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioImoveisEmCobrancaAdministrativa
	 */
	public RelatorioAnaliticoImoveisEmCobrancaAdministrativa(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ANALITICO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA);
	}

	@Deprecated
	public RelatorioAnaliticoImoveisEmCobrancaAdministrativa() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();


		// Parâmetros da tela de filtro
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		// Valor total do documento
		BigDecimal valorTotalDocumento = BigDecimal.ZERO;

		// Recupera a coleção de imóveis em cobrança administrativa.
		Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = (Collection<ImovelCobrancaSituacao>) getParametro("colecaoImovelCobrancaSituacao");

		if(!Util.isVazioOrNulo(colecaoImovelCobrancaSituacao)){


			// Bean do relatório e sub-relatório
			RelatorioImoveisEmCobrancaAdministrativaBean relatorioImoveisEmCobrancaAdministrativaBean = null;


			// Coleção do sub-relatório de contas
			Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheContaBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean = null;

			String matricula=null;
			String nome=null;
			String cpfCnpj=null;
			String telefone=null;
			String endereco=null;
			String valorTotal=null;

			for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacao){
				
				
				
				// IdImovel
				matricula = imovelCobrancaSituacao.getImovel().getId().toString();

				if(!Util.isVazioOuBranco(imovelCobrancaSituacao)){

					if(!Util.isVazioOuBranco(imovelCobrancaSituacao.getCliente().getClienteTipo())){
						if(imovelCobrancaSituacao.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ConstantesSistema.INDICADOR_PESSOA_FISICA)){

							// cpf
							cpfCnpj = imovelCobrancaSituacao.getCliente().getCpfFormatado();

						}else if(imovelCobrancaSituacao.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ConstantesSistema.INDICADOR_PESSOA_JURIDICA)){

							// cnpj
							cpfCnpj =imovelCobrancaSituacao.getCliente().getCnpjFormatado();

						}

						// Nome
						nome=imovelCobrancaSituacao.getCliente().getNomeAbreviado();

					}

					// Endereco
					if(!Util.isVazioOuBranco(imovelCobrancaSituacao.getImovel())){
						FiltroImovel filtroImovel = new FiltroImovel();
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelCobrancaSituacao.getImovel().getId()));
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TIPO);
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TITULO);
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ENDERECO_REFERENCIA);
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.UNIDADE_FEDERACAO);
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CEP);
						Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroImovel, Imovel.class.getName()));

						endereco=imovel.getEnderecoFormatadoAbreviado().toString();

					}
					
					// telefone
					if(!Util.isVazioOuBranco(imovelCobrancaSituacao.getCliente())){
						FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
						filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, imovelCobrancaSituacao.getCliente().getId()));
						ClienteFone clienteFone = (ClienteFone) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteFone, ClienteFone.class.getName()));

						if(!Util.isVazioOuBranco(clienteFone)){
							telefone=clienteFone.getDddTelefoneFormatado();
						}
						
					}
					// 1.3.2. Empresa
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = imovelCobrancaSituacao.getCobrancaAcaoAtividadeComando();

					

				
					// 1.3.10. Qtde. Itens Remuneráveis
					List<ItensRemuradosHelper> listaItensRemunerados = fachada.selecionarItensRemureraveis(imovelCobrancaSituacao);


					// 1.3.13. Dados dos Itens Vinculados à Cobrança Administrativa
					// 1.3.11. Dados dos Itens de Débito
					// 1.3.11.1. Para cada grupo de itens com o mesmo tipo de documento (DOTP_ID)
					Integer idCobrancaAcaoAtividadeComando = null;
					Integer idImovel = imovelCobrancaSituacao.getImovel().getId();

					if(cobrancaAcaoAtividadeComando != null && idImovel != null){
						idCobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando.getId();
					}

					// 1.3.11.1.1. Exibir a descrição do tipo de documento

					// Totalizador Débito Conta
					BigDecimal valorParcialDebitoConta = BigDecimal.ZERO;
					BigDecimal valorTotalDebitoConta = BigDecimal.ZERO;
					int quantidadeRegistros = 0;
					if(idCobrancaAcaoAtividadeComando != null && idImovel != null){
						// 1.3.11.1.2.1. Caso o tipo de documento corresponda à “CONTA”
						// Collection<CobrancaAdministrativaContaHelper>
						// colecaoCobrancaAdministrativaContaHelper = fachada
						// .pesquisarContasPeloComandoParaCobrancaAdministrativa(idCobrancaAcaoAtividadeComando,
						// idImovel);

						List<CobrancaAdministrativaHelper> colecaoCobrancaAdministrativaHelper = fachada
										.obterDadosDaConta(listaItensRemunerados);
						if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaHelper)){


							for(CobrancaAdministrativaHelper cobrancaAdministrativaHelper : colecaoCobrancaAdministrativaHelper){
								// Contador de registros processados
								quantidadeRegistros = quantidadeRegistros + 1;

								valorTotalDebitoConta = valorTotalDebitoConta.add(cobrancaAdministrativaHelper.getValorDebito());

							}
							// Soma o valor total das contas com o valor total dos documentos.
							valorTotalDocumento = valorTotalDocumento.add(valorTotalDebitoConta);
							// Insere valor parcial débito conta no Bean

						}
					}
					relatorioImoveisEmCobrancaAdministrativaBean = new RelatorioImoveisEmCobrancaAdministrativaBean(
									matricula,
									"R$ "+ Util.formatarMoedaReal(valorTotalDebitoConta),
									nome,
									cpfCnpj,
									telefone,
									endereco);

				// Adiciona ítem na coleção
				relatorioBeans.add(relatorioImoveisEmCobrancaAdministrativaBean);
			}

			}
		}

		// valor de retorno
		byte[] retorno = null;

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("comando", (String) getParametro("comando"));

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ANALITICO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ANALITICO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;

		// ---------------------------
		// Parâmetros do relatório
		// ---------------------------

		
	}

	public int calcularTotalRegistrosRelatorio(){

		return 10;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAnaliticoRelacaoImoveisEmCobrancaAdministrativa", this);
	}

}
