
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.IRepositorioClienteImovel;
import gcom.cadastro.cliente.RepositorioClienteImovelHBM;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GeradorDadosRelatorioSupressaoLigacao
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioCobranca repositorioCobranca = null;

	private IRepositorioClienteImovel repositorioClienteImovel = null;

	public GeradorDadosRelatorioSupressaoLigacao() {

		super();
		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico ordemServico) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> retorno = new ArrayList<DadosRelatorioOrdemServico>();

		DadosRelatorioOrdemServico bean = new DadosRelatorioOrdemServico();

		Fachada fachada = Fachada.getInstancia();

		Imovel imovel = null;

		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

		String telefoneEmpresa = sistemaParametros.getNumeroTelefone();
		bean.setTelefoneEmpresa(telefoneEmpresa);

		// Imovel
		imovel = fachada.pesquisarImovel(ordemServico.getImovel().getId());

		// Ligacao Agua
		LigacaoAgua ligacaoAgua = null;
		ligacaoAgua = fachada.pesquisarLigacaoAgua(imovel.getId());

		// nome do cliente
		String nomeCliente = "";
		Cliente cliente = null;

		cliente = fachada.pesquisarClienteUsuarioImovel(imovel.getId());
		if(cliente != null){
			nomeCliente = cliente.getNome();
		}
		bean.setNomeCliente(nomeCliente);

		// endereço do cliente
		String endereco = fachada.obterEnderecoAbreviadoOS(ordemServico.getId());

		String logradouroTipo = fachada.obterLogradouroTipoImovel(imovel.getId());

		bean.setEndereco(logradouroTipo + " " + endereco);

		// bairro do cliente
		LogradouroBairro logBair = fachada.pesquisarLogradouroBairro(imovel.getLogradouroBairro().getId());
		bean.setBairro(logBair.getBairro().getNome());

		// Número da OS
		bean.setNumeroOS(Util.adicionarZerosEsquedaNumero(6, String.valueOf(ordemServico.getId())));

		// RA
		if(ordemServico.getRegistroAtendimento() != null){
			bean.setNumeroRA(String.valueOf(ordemServico.getRegistroAtendimento().getId()));
		}

		// matricula
		bean.setMatricula(imovel.getIdParametrizado());

		// inscricao
		String inscricao = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
		bean.setInscricao(inscricao);

		// hidrometro
		HidrometroInstalacaoHistorico hidrometro = null;
		if(ligacaoAgua != null){
			hidrometro = ligacaoAgua.getHidrometroInstalacaoHistorico();
		}
		if(hidrometro == null){
			bean.setNumeroHidrometro("");
		}else{
			bean.setNumeroHidrometro(hidrometro.getNumeroHidrometro());
		}
		// data Solicitacao
		bean.setDataSolicitacao(Util.formatarData(ordemServico.getDataGeracao()));

		// Descricao Servico
		bean.setDescricaoServico(ordemServico.getServicoTipo().getDescricao());
		// Cobranca Documento
		CobrancaDocumento cobrancaDocumento = ordemServico.getCobrancaDocumento();

		if(cobrancaDocumento != null){
			FiltroCobrancaDocumento filtro = new FiltroCobrancaDocumento();
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID, cobrancaDocumento.getId()));
			Collection collection = fachada.pesquisar(filtro, CobrancaDocumento.class.getName());
			if(collection != null && !collection.isEmpty()){
				cobrancaDocumento = (CobrancaDocumento) collection.iterator().next();
			}
			bean.setDataPermanenciaRegistro(Util.formatarData(cobrancaDocumento.getEmissao()));
			Collection colecaoCobrancaDocumentoItemConta = null;
			// Collection colecaoCobrancaDocumentoItemGuiaPagamento = null;
			// Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoDebitoACobrar = null;
			BigDecimal valorTotal = BigDecimal.ZERO;
			BigDecimal debitosAnteriores = BigDecimal.ZERO;

			if(cobrancaDocumento != null){
				try{
					// pesquisa todas as contas, debitos e guias
					colecaoCobrancaDocumentoItemConta = this.repositorioCobranca
									.selecionarCobrancaDocumentoItemReferenteConta(cobrancaDocumento);
					// colecaoCobrancaDocumentoItemGuiaPagamento =
					// this.repositorioCobranca.selecionarDadosCobrancaDocumentoItemReferenteGuiaPagamento(cobrancaDocumento);
					// colecaoCobrancaDocumentoDebitoACobrar =
					// this.repositorioCobranca.selecionarCobrancaDocumentoItemReferenteDebitoACobrar(cobrancaDocumento);

				}catch(ErroRepositorioException ex){
					ex.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(ex.getMessage(), ex);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// contas
				int limitador15Itens = 1;
				Collection<String> mesAno = new ArrayList<String>();
				Collection<String> vencimento = new ArrayList<String>();
				Collection<BigDecimal> valor = new ArrayList<BigDecimal>();

				Iterator<CobrancaDocumentoItem> itContas = colecaoCobrancaDocumentoItemConta.iterator();
				Integer mesAnoMaisAnterior = 300012;
				boolean teveConta = false;
				while(itContas.hasNext()){
					CobrancaDocumentoItem cobrancaDocumentoItem = itContas.next();
					teveConta = true;
					if(limitador15Itens <= 15){
						mesAno.add(Util.formatarAnoMesSemBarraParaMesAnoComBarra(cobrancaDocumentoItem.getContaGeral().getConta()
										.getReferencia()));
						vencimento.add(Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta()));
						valor.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
					}else{
						debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
					}
					if(Util.compararAnoMesReferencia(cobrancaDocumentoItem.getContaGeral().getConta().getReferencia(), mesAnoMaisAnterior,
									"<")){
						mesAnoMaisAnterior = cobrancaDocumentoItem.getContaGeral().getConta().getReferencia();
					}
					valorTotal = valorTotal.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
					limitador15Itens++;
				}

				if(teveConta){
					bean.setDataDebitoAnterior(Util.formatarAnoMesSemBarraParaMesAnoComBarra(mesAnoMaisAnterior));
				}
				bean.setMesAno(mesAno);
				bean.setVencimento(vencimento);
				bean.setValor(valor);
				bean.gerarDetail1();
				// // guias
				// Iterator<CobrancaDocumentoItem> itGuias =
				// colecaoCobrancaDocumentoItemGuiaPagamento.iterator();
				// while (itGuias.hasNext()) {
				// CobrancaDocumentoItem cobrancaDocumentoItem = itGuias.next();
				// valorTotal = valorTotal.add(cobrancaDocumentoItem.getValorItemCobrado());
				// debitosAnteriores =
				// debitosAnteriores.add(cobrancaDocumentoItem.getValorItemCobrado());
				// }
				//
				// //debitos a cobrar
				// Iterator<CobrancaDocumentoItem> itDebACob =
				// colecaoCobrancaDocumentoDebitoACobrar.iterator();
				// while (itDebACob.hasNext()) {
				// CobrancaDocumentoItem cobrancaDocumentoItem = itGuias.next();
				// valorTotal =
				// valorTotal.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar().getValorTotal());
				// debitosAnteriores =
				// debitosAnteriores.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar().getValorTotal());
				// }
			}
			bean.setValorTotalDebito1(Util.formatarMoedaReal(debitosAnteriores));
			bean.setValorTotalDebito(Util.formatarMoedaReal(valorTotal));
		}
		if(bean.getValorTotalDebito1() == null){
			bean.setValorTotalDebito1("0,00");
		}
		if(bean.getValorTotalDebito() == null){
			bean.setValorTotalDebito("0,00");
		}
		if(bean.getDataDebitoAnterior() == null){
			bean.setDataDebitoAnterior("00/0000");
		}
		if(bean.getDataPermanenciaRegistro() == null){
			bean.setDataPermanenciaRegistro("00/00/0000");
		}
		retorno.add(bean);
		return retorno;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		return null;
	}

}
