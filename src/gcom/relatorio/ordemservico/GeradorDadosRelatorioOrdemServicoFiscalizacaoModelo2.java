
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.OrdemServicoFiscalizacaoModelo2Helper;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.*;
import gcom.cadastro.imovel.bean.ImovelDadosMedicaoHistoricoHelper;
import gcom.fachada.Fachada;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * [UC0458] Imprimir Ordem de Serviço
 * Gera os Relatórios das Ordens de Serviço de Fiscalização Modelo2
 * 
 * @author Carlos Chrystian
 * @date 10/04/2013
 */
public class GeradorDadosRelatorioOrdemServicoFiscalizacaoModelo2
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioImovel repositorioImovel = null;

	public GeradorDadosRelatorioOrdemServicoFiscalizacaoModelo2() {

		super();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> collOrdemServicoFiscalizacaoModelo2Helper = new ArrayList<DadosRelatorioOrdemServico>();

		List<OrdemServicoFiscalizacaoModelo2Helper> colecaoOrdemServicoFiscalizacaoModelo2Helper = this
						.ordemServicoFiscalizacaoModelo2Helper(ordensServicos);

		int totalPaginas = colecaoOrdemServicoFiscalizacaoModelo2Helper.size();

		List<OrdemServicoFiscalizacaoModelo2Helper> colecaoOrdenada = (List<OrdemServicoFiscalizacaoModelo2Helper>) this
						.dividirColecaoOrdenando(colecaoOrdemServicoFiscalizacaoModelo2Helper);

		OrdemServicoFiscalizacaoModelo2Helper primeiroHelper = null;
		OrdemServicoFiscalizacaoModelo2Helper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(OrdemServicoFiscalizacaoModelo2Helper helper : colecaoOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina("" + pagina1);
				pagina1++;
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina("" + pagina2);
				pagina2++;
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collOrdemServicoFiscalizacaoModelo2Helper.add(new DadosRelatorioOrdemServico(primeiroHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collOrdemServicoFiscalizacaoModelo2Helper.add(new DadosRelatorioOrdemServico(primeiroHelper));
		}

		return collOrdemServicoFiscalizacaoModelo2Helper;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		Collection<OrdemServico> ordensServicos = new ArrayList<OrdemServico>();

		ordensServicos.add(os);

		return this.gerarDados(ordensServicos);
	}

	/**
	 * [UC0458] Imprimir Ordem de Serviço
	 * Gera os Relatórios das Ordens de Serviço de Fiscalização Modelo2
	 * 
	 * @author Carlos Chrystian
	 * @date 10/04/2013
	 * @param listPesquisaOrdemServico
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public List<OrdemServicoFiscalizacaoModelo2Helper> ordemServicoFiscalizacaoModelo2Helper(
					Collection<OrdemServico> listPesquisaOrdemServico){

		List<OrdemServicoFiscalizacaoModelo2Helper> colecaoOrdemServicoFiscalizacaoModelo2Helper = new ArrayList<OrdemServicoFiscalizacaoModelo2Helper>();

		if(listPesquisaOrdemServico != null && !listPesquisaOrdemServico.isEmpty()){

			for(OrdemServico ordemServico : listPesquisaOrdemServico){

				OrdemServicoFiscalizacaoModelo2Helper ordemServicoFiscalizacaoModelo2Helper = new OrdemServicoFiscalizacaoModelo2Helper();

				// ****************************************************

				// COBRANCA SITUACAO
				Integer tipoCobrancaSituacao = 4;// 4 = Cobrança Administrativa
				FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
				filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, ordemServico
								.getImovel().getId()));
				filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
								tipoCobrancaSituacao));
				filtroImovelCobrancaSituacao.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));
				Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = Fachada.getInstancia().pesquisar(
								filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class.getName());
				if(!colecaoImovelCobrancaSituacao.isEmpty()){
					ordemServicoFiscalizacaoModelo2Helper.setCobrancaSituacao(1);
				}else{
					ordemServicoFiscalizacaoModelo2Helper.setCobrancaSituacao(0);
				}

				// LOCAL
				ordemServicoFiscalizacaoModelo2Helper.setLocal(Util.completarStringZeroEsquerda(ordemServico.getImovel().getLocalidade()
								.getId().toString(), 3));

				// MATRÍCULA
				ordemServicoFiscalizacaoModelo2Helper.setMatricula(Util.retornaMatriculaImovelParametrizada(ordemServico.getImovel()
								.getId()));

				// SETOR
				ordemServicoFiscalizacaoModelo2Helper.setSetor(Util.completarStringZeroEsquerda(ordemServico.getImovel()
								.getSetorComercial().getCodigo()
								+ "", 2));

				// QUADRA
				ordemServicoFiscalizacaoModelo2Helper.setQuadra(Util.completarStringZeroEsquerda(ordemServico.getImovel().getQuadra()
								.getNumeroQuadra()
								+ "", 4));

				// LOTE
				ordemServicoFiscalizacaoModelo2Helper.setLote(Util.completarStringZeroEsquerda(ordemServico.getImovel().getLote() + "", 4));

				// SUBLOTE
				ordemServicoFiscalizacaoModelo2Helper.setSublote(Util.completarStringZeroEsquerda(ordemServico.getImovel().getSubLote()
								+ "", 4));

				// CONTROLE
				ordemServicoFiscalizacaoModelo2Helper.setControle(Util.completarStringZeroEsquerda(ordemServico.getId().toString(), 9));

				// Obter dados do cliente
				Collection<ClienteImovel> colecaoClienteImovel = null;
				try{
					colecaoClienteImovel = repositorioImovel.pesquisarClientesImovel(ordemServico.getImovel().getId());
				}catch(ErroRepositorioException e2){
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

					for(Object objeto : colecaoClienteImovel){

						Object[] clienteImovel = (Object[]) objeto;

						if(clienteImovel[8].equals(ClienteRelacaoTipo.USUARIO)){
							// CONSUMIDOR
							ordemServicoFiscalizacaoModelo2Helper.setConsumidor(String.valueOf(clienteImovel[1]));

							// NOME CLIENTE
							ordemServicoFiscalizacaoModelo2Helper.setClienteNome(String.valueOf(clienteImovel[1]));

							// RG
							ordemServicoFiscalizacaoModelo2Helper.setClienteRG(String.valueOf(clienteImovel[13] != null ? Util
											.completarStringZeroEsquerda(String.valueOf(clienteImovel[13]), 13) : ""));

							// CPF/CNPJ
							int identificadorPessoaJuridica = Integer.valueOf((Short) clienteImovel[12]).intValue();

							if(identificadorPessoaJuridica == 1){
								// CPF
								ordemServicoFiscalizacaoModelo2Helper.setClienteCPFCNPJ(clienteImovel[6] != null ? Util
												.completarStringZeroEsquerda(String.valueOf(clienteImovel[6]), 14) : "");
							}else{
								// CNPJ
								ordemServicoFiscalizacaoModelo2Helper.setClienteCPFCNPJ(clienteImovel[5] != null ? Util
												.completarStringZeroEsquerda(String.valueOf(clienteImovel[5]), 14) : "");
							}

							// DDD/TELEFONE
							String ddd = String.valueOf(clienteImovel[7] != null ? String.valueOf(clienteImovel[7]) : "");
							String telefone = String.valueOf(clienteImovel[4] != null ? String.valueOf(clienteImovel[4]) : "");

							ordemServicoFiscalizacaoModelo2Helper.setClienteTelefone(ddd + " " + telefone);
						}
					}

				}

				// DATA EMISSÃO
				ordemServicoFiscalizacaoModelo2Helper.setDataEmissao(Util.formatarData(new Date()));

				// Obter endereço completo
				String endereco = "";

				try{
					endereco = this.getControladorOrdemServico().obterEnderecoCompletoOS(ordemServico.getId());
				}catch(ControladorException e1){
					e1.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(e1.getMessage(), e1);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// ENDEREÇO LOCALIDADE
				ordemServicoFiscalizacaoModelo2Helper.setEnderecoLocalidade(endereco);

				// ECONOMIAS QUANTIDADE
				ordemServicoFiscalizacaoModelo2Helper.setEconomiasQuantidade(Util.completarStringZeroEsquerda(ordemServico.getImovel()
								.getQuantidadeEconomias() + "", 3));

				/*
				 * Obter a principal categoria do imóvel - (<<Inclui>> [UC0306] - Obter Principal
				 * Categoria do Imóvel, passando como parâmetro o imóvel
				 */
				Categoria categoria = null;
				try{
					categoria = getControladorImovel().obterPrincipalCategoriaImovel(ordemServico.getImovel().getId());
				}catch(ControladorException e1){
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// ECONOMIAS TIPO
				ordemServicoFiscalizacaoModelo2Helper.setEconomiasTipo(categoria.getDescricao());

				// DADOS HIDRÔMETRO
				// NÚMERO DO HIDRÔMETRO
				if(ordemServico.getImovel().getLigacaoAgua() != null
								&& ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){

					if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

						ordemServicoFiscalizacaoModelo2Helper.setNumeroHidrometro(ordemServico.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getNumeroHidrometro());
					}else if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null){

						ordemServicoFiscalizacaoModelo2Helper.setNumeroHidrometro(ordemServico.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
					}else{
						try{
							HidrometroInstalacaoHistorico hidi = (HidrometroInstalacaoHistorico) getControladorUtil().pesquisar(
											ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getId(),
											HidrometroInstalacaoHistorico.class, false);
							ordemServicoFiscalizacaoModelo2Helper.setNumeroHidrometro(hidi.getNumeroHidrometro());
						}catch(ControladorException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					ordemServicoFiscalizacaoModelo2Helper.setNumeroHidrometro("");
				}

				// [SB8003] - Obter Dados de Medição
				ImovelDadosMedicaoHistoricoHelper imovelDadosMedicaoHistoricoHelper = null;
				try{
					imovelDadosMedicaoHistoricoHelper = getControladorMicromedicao().obterDadosMedicaoMaiorReferenciaLeitura(
									ordemServico.getImovel(), MedicaoTipo.LIGACAO_AGUA);
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(imovelDadosMedicaoHistoricoHelper != null){
					String leituraAtual = imovelDadosMedicaoHistoricoHelper.getLeituraAtual() != null ? imovelDadosMedicaoHistoricoHelper
									.getLeituraAtual().toString() : null;

					if(!Util.isVazioOuBranco(leituraAtual)){
						String leituraAtualAux = leituraAtual.toString();
						if(leituraAtualAux.length() < 4){
							leituraAtual = Util.completarStringZeroEsquerda(leituraAtual, 4);
						}
					}

					// LEITURA ATUAL
					ordemServicoFiscalizacaoModelo2Helper.setLeituraAtualHidrometro(leituraAtual != null ? leituraAtual.toString() : "");
				}

				// NÚMERO LACRE
				if(ordemServico.getImovel() != null && ordemServico.getImovel().getLigacaoAgua() != null){
					ordemServicoFiscalizacaoModelo2Helper.setNumeroLacre(ordemServico.getImovel().getLigacaoAgua().getNumeroLacre());
				}
				// SITUAÇÃO LIGAÇÃO ÁGUA
				if(ordemServico.getImovel() != null && ordemServico.getImovel().getLigacaoAguaSituacao() != null){
					ordemServicoFiscalizacaoModelo2Helper.setSituacaoLigacaoAgua(ordemServico.getImovel().getLigacaoAguaSituacao().getId()
									.toString());
				}

				// SITUAÇÃO LIGAÇÃO ESGOTO
				if(ordemServico.getImovel() != null && ordemServico.getImovel().getLigacaoEsgotoSituacao() != null){
					ordemServicoFiscalizacaoModelo2Helper.setSituacaoLigacaoEsgoto(ordemServico.getImovel().getLigacaoEsgotoSituacao()
									.getId().toString());
				}

				// [SB9000 - Obter Data de Corte ou Supressao]
				Date dataCorte = null;
				Date dataSupressao = null;
				try{
					dataCorte = getControladorCobranca().obterDataCorteOuSupressao(ordemServico, ConstantesSistema.INDICADOR_DATA_CORTE);
					dataSupressao = getControladorCobranca().obterDataCorteOuSupressao(ordemServico,
									ConstantesSistema.INDICADOR_DATA_SUPRESSAO);
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// DATA CORTE
				ordemServicoFiscalizacaoModelo2Helper.setDataCorte(dataCorte != null ? Util.formatarData(dataCorte) : "");

				// DATA SUPRESSÃO
				ordemServicoFiscalizacaoModelo2Helper.setDataSuspensao(dataSupressao != null ? Util.formatarData(dataSupressao) : "");

				// [SB9001 – Obter Contas Vencidas do Imóvel]
				Object[] retorno = new Object[2];
				try{
					retorno = getControladorCobranca().obterContasVencidasDoImovel(ordemServico.getImovel().getId(), false);
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Informações das Contas Vencidas
				// Quantidade de contas vencidas
				Integer quantidadeContasVencidas = (Integer) retorno[0];
				ordemServicoFiscalizacaoModelo2Helper.setQuantidadeContasVencidas(Util.adicionarZerosEsquedaNumero(3,
								quantidadeContasVencidas.toString()));

				// Valor de contas vencidas
				BigDecimal valorContasVencidas = (BigDecimal) retorno[1];
				ordemServicoFiscalizacaoModelo2Helper.setValorContasVencidas(Util.formatarMoedaReal(valorContasVencidas));

				// [SB9002 – Obter Dados Últimos Consumos]

				// Dados dos Últimos 12 Consumos
				Collection<DadosUltimosConsumosHelper> dadosUltimos12Consumos = new ArrayList<DadosUltimosConsumosHelper>();

				try{
					dadosUltimos12Consumos = getControladorCobranca().obterDadosUltimosConsumos(ordemServico.getImovel().getId(), 12);
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Quebrar coleção em 4 grupos para exibir no relatório
				Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos1 = new ArrayList<DadosUltimosConsumosHelper>();
				Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos2 = new ArrayList<DadosUltimosConsumosHelper>();
				Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos3 = new ArrayList<DadosUltimosConsumosHelper>();
				Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos4 = new ArrayList<DadosUltimosConsumosHelper>();

				int contador = 0;
				for(DadosUltimosConsumosHelper dadosUltimosConsumosHelper : dadosUltimos12Consumos){
					contador++;
					if(contador == 1 || contador == 5 || contador == 9){
						dadosUltimosConsumos1.add(dadosUltimosConsumosHelper);
					}else if(contador == 2 || contador == 6 || contador == 10){
						dadosUltimosConsumos2.add(dadosUltimosConsumosHelper);
					}else if(contador == 3 || contador == 7 || contador == 11){
						dadosUltimosConsumos3.add(dadosUltimosConsumosHelper);
					}else if(contador == 4 || contador == 8 || contador == 12){
						dadosUltimosConsumos4.add(dadosUltimosConsumosHelper);
					}
				}

				// Completa coleção para exibir tabela vazia no relatório
				// caso o mesmo tenha menos de 12 registros
				completarColecao(dadosUltimosConsumos1);
				completarColecao(dadosUltimosConsumos2);
				completarColecao(dadosUltimosConsumos3);
				completarColecao(dadosUltimosConsumos4);

				ordemServicoFiscalizacaoModelo2Helper.setDadosUltimosConsumos1(dadosUltimosConsumos1);
				ordemServicoFiscalizacaoModelo2Helper.setDadosUltimosConsumos2(dadosUltimosConsumos2);
				ordemServicoFiscalizacaoModelo2Helper.setDadosUltimosConsumos3(dadosUltimosConsumos3);
				ordemServicoFiscalizacaoModelo2Helper.setDadosUltimosConsumos4(dadosUltimosConsumos4);

				// **********************************************************

				colecaoOrdemServicoFiscalizacaoModelo2Helper.add(ordemServicoFiscalizacaoModelo2Helper);

				ordemServicoFiscalizacaoModelo2Helper = null;
			}

		}
		return colecaoOrdemServicoFiscalizacaoModelo2Helper;
	}

	/**
	 * @author Carlos Chrystian
	 * @date 10/04/2013
	 * @param dadosUltimosConsumos
	 */
	private void completarColecao(Collection<DadosUltimosConsumosHelper> dadosUltimosConsumos){

		DadosUltimosConsumosHelper dadosUltimosConsumosHelper = null;

		if(dadosUltimosConsumos != null && dadosUltimosConsumos.size() < 3){
			if(dadosUltimosConsumos.size() == 0){
				for(int i = 0; i < 3; i++){
					dadosUltimosConsumosHelper = new DadosUltimosConsumosHelper();
					dadosUltimosConsumosHelper.setReferenciaConsumo("");
					dadosUltimosConsumosHelper.setConsumoFaturado("");
					dadosUltimosConsumosHelper.setLeituraFaturada("");

					dadosUltimosConsumos.add(dadosUltimosConsumosHelper);
				}
			}else if(dadosUltimosConsumos.size() == 1){
				for(int i = 0; i < 2; i++){
					dadosUltimosConsumosHelper = new DadosUltimosConsumosHelper();
					dadosUltimosConsumosHelper.setReferenciaConsumo("");
					dadosUltimosConsumosHelper.setConsumoFaturado("");
					dadosUltimosConsumosHelper.setLeituraFaturada("");

					dadosUltimosConsumos.add(dadosUltimosConsumosHelper);
				}
			}else if(dadosUltimosConsumos.size() == 2){
				dadosUltimosConsumosHelper = new DadosUltimosConsumosHelper();
				dadosUltimosConsumosHelper.setReferenciaConsumo("");
				dadosUltimosConsumosHelper.setConsumoFaturado("");
				dadosUltimosConsumosHelper.setLeituraFaturada("");

				dadosUltimosConsumos.add(dadosUltimosConsumosHelper);
			}
		}
	}
}
