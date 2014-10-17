
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.FiltroImovelSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cobranca.CobrancaAcaoAtividadeImovel;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeImovel;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipOutputStream;

public class RelatorioImoveisAcaoCobranca
				extends TarefaRelatorio {

	public RelatorioImoveisAcaoCobranca(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEIS_ACAO_COBRANCA);
	}

	public RelatorioImoveisAcaoCobranca() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		Integer idCobrancaAcaoAtividadeComando = (Integer) getParametro("idCobrancaAcaoAtividadeComando");
		Integer idCobrancaAcaoAtividadeCronograma = (Integer) getParametro("idCobrancaAcaoAtividadeCronograma");

		Integer tipoImovel = Integer.valueOf(1);
		Integer indicadorPagamento = Integer.valueOf(1);
		Integer indicadorConta = Integer.valueOf(1);
		Integer indicadorDebito = Integer.valueOf(1);
		Integer indicadorCredito = Integer.valueOf(1);
		Integer indicadorNotas = Integer.valueOf(1);
		Integer indicadorGuias = Integer.valueOf(1);
		Integer indicadorAtualizar = Integer.valueOf(1);

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		StringBuffer sb = new StringBuffer();
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		byte[] retorno = null;

		try{
			// Lógica removida do OnLine para o Batch
			Fachada fachada = Fachada.getInstancia();

			FiltroCobrancaAcaoAtividadeImovel filtroCobrancaAcaoAtividadeImovel1 = new FiltroCobrancaAcaoAtividadeImovel();

			FiltroCobrancaAcaoAtividadeImovel filtroCobrancaAcaoAtividadeImovel = new FiltroCobrancaAcaoAtividadeImovel();
			filtroCobrancaAcaoAtividadeImovel.setCampoDistinct(FiltroCobrancaAcaoAtividadeImovel.IMOVEL_ID);

			if(!idCobrancaAcaoAtividadeComando.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				filtroCobrancaAcaoAtividadeImovel.adicionarParametro(new ParametroSimples(
								FiltroCobrancaAcaoAtividadeImovel.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, idCobrancaAcaoAtividadeComando));
			}else{
				filtroCobrancaAcaoAtividadeImovel
								.adicionarParametro(new ParametroSimples(
												FiltroCobrancaAcaoAtividadeImovel.COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID,
												idCobrancaAcaoAtividadeCronograma));
			}

			Collection colCobrancaAcaoAtvImovel = fachada.pesquisar(filtroCobrancaAcaoAtividadeImovel, CobrancaAcaoAtividadeImovel.class
							.getName());

			Iterator it = colCobrancaAcaoAtvImovel.iterator();

			FiltroImovelSubCategoria filtroSubCategoria = new FiltroImovelSubCategoria();
			filtroSubCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.SUBCATEGORIA);

			while(it.hasNext()){
				Integer idImovel = (Integer) it.next();
				filtroCobrancaAcaoAtividadeImovel1.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeImovel.IMOVEL_ID,
								idImovel));

				if(!idCobrancaAcaoAtividadeComando.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
					filtroCobrancaAcaoAtividadeImovel1.adicionarParametro(new ParametroSimples(
									FiltroCobrancaAcaoAtividadeImovel.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, idCobrancaAcaoAtividadeComando));
				}else{
					filtroCobrancaAcaoAtividadeImovel1.adicionarParametro(new ParametroSimples(
									FiltroCobrancaAcaoAtividadeImovel.COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID,
									idCobrancaAcaoAtividadeCronograma));
				}

				CobrancaAcaoAtividadeImovel cobrancaAcaoAtividadeImovel = (CobrancaAcaoAtividadeImovel) fachada.pesquisar(
								filtroCobrancaAcaoAtividadeImovel1, CobrancaAcaoAtividadeImovel.class.getName()).iterator().next();
				filtroCobrancaAcaoAtividadeImovel1.limparListaParametros();

				Imovel imovel = fachada.pesquisarImovel(idImovel);
				sb.append(imovel.getId() + ";");
				String inscricaoFormatada = fachada.pesquisarInscricaoImovel(idImovel, true);

				sb.append("'" + inscricaoFormatada + "'" + ";");
				sb.append(cobrancaAcaoAtividadeImovel.getValorDocumento() + ";");
				sb.append(cobrancaAcaoAtividadeImovel.getQuantidadeDocumentoItem() + ";");

				Collection colecaoClienteImovel = fachada.pesquisarClientesImovel(imovel.getId());

				Iterator<ClienteImovel> itClie = colecaoClienteImovel.iterator();
				String cpf = "";
				while(itClie.hasNext()){
					ClienteImovel clienteImovel = itClie.next();
					if(clienteImovel.getDataFimRelacao() == null
									&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.USUARIO.intValue())){
						sb.append(clienteImovel.getCliente().getNome() + ";");
						if(clienteImovel.getCliente().getCpf() != null){
							cpf = clienteImovel.getCliente().getCpf();
						}
						break;
					}
				}
				sb.append(imovel.getRota().getFaturamentoGrupo().getId() + ";");
				sb.append(imovel.getSetorComercial().getId() + ";");
				sb.append(imovel.getEnderecoFormatado() + ";");
				sb.append(imovel.getLogradouroBairro().getBairro().getNome() + ";");
				if(imovel.getLigacaoAguaSituacao() != null){
					sb.append(imovel.getLigacaoAguaSituacao().getDescricao() + ";");
				}
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));

				Collection colecaoImovelSubCategoria = fachada.pesquisar(filtroSubCategoria, ImovelSubcategoria.class.getName());
				filtroSubCategoria.limparListaParametros();
				if(colecaoImovelSubCategoria.size() > 1){
					sb.append("MISTA;");
				}else{
					ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) Util.retonarObjetoDeColecao(colecaoImovelSubCategoria);
					sb.append(imovelSubcategoria.getComp_id().getSubcategoria().getDescricao() + ";");
				}

				sb.append(cpf + ";");
				if(imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
					sb.append(imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getNumero() + ";");
				}else{
					sb.append(";");
				}
				// Obtendo os débitos do imovel
				ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = null;
				try{
					colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(tipoImovel.intValue(), imovel.getId().toString(), null, null,
									"000101", "999912", formatoData.parse("01/01/0001"), formatoData.parse("31/12/9999"),
									indicadorPagamento.intValue(), indicadorConta.intValue(), indicadorDebito.intValue(), indicadorCredito
													.intValue(), indicadorNotas.intValue(), indicadorGuias.intValue(), indicadorAtualizar
.intValue(), null, null, null, null, null, ConstantesSistema.SIM,
									ConstantesSistema.SIM, ConstantesSistema.SIM);
				}catch(ParseException e){
				}

				Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();

				ContaValoresHelper dadosConta = null;

				BigDecimal valorConta = BigDecimal.ZERO;
				BigDecimal valorAcrescimo = BigDecimal.ZERO;
				BigDecimal valorAgua = BigDecimal.ZERO;
				BigDecimal valorEsgoto = BigDecimal.ZERO;
				BigDecimal valorDebito = BigDecimal.ZERO;
				BigDecimal valorCredito = BigDecimal.ZERO;
				BigDecimal valorImposto = BigDecimal.ZERO;

				if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){
					java.util.Iterator<ContaValoresHelper> colecaoContaValoresIterator = colecaoContaValores.iterator();
					// percorre a colecao de conta somando o valor para obter um valor
					// total
					while(colecaoContaValoresIterator.hasNext()){

						dadosConta = (ContaValoresHelper) colecaoContaValoresIterator.next();
						valorConta = valorConta.add(dadosConta.getConta().getValorTotal());
						valorAcrescimo = valorAcrescimo.add(dadosConta.getValorTotalContaValores());
						valorAgua = valorAgua.add(dadosConta.getConta().getValorAgua());
						valorEsgoto = valorEsgoto.add(dadosConta.getConta().getValorEsgoto());
						valorDebito = valorDebito.add(dadosConta.getConta().getDebitos());
						valorCredito = valorCredito.add(dadosConta.getConta().getValorCreditos());
						valorImposto = valorImposto.add(dadosConta.getConta().getValorImposto());
					}
				}

				Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoImovel.getColecaoDebitoACobrar();

				BigDecimal valorDebitoACobrar = BigDecimal.ZERO;
				DebitoACobrar dadosDebito = null;

				if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
					java.util.Iterator<DebitoACobrar> colecaoDebitoACobrarIterator = colecaoDebitoACobrar.iterator();

					// percorre a colecao de debito a cobrar somando o valor para obter um valor
					// total
					while(colecaoDebitoACobrarIterator.hasNext()){

						dadosDebito = (DebitoACobrar) colecaoDebitoACobrarIterator.next();
						valorDebitoACobrar = valorDebitoACobrar.add(dadosDebito.getValorTotal());
					}
				}

				Collection<CreditoARealizar> colecaoCreditoARealizar = colecaoDebitoImovel.getColecaoCreditoARealizar();

				BigDecimal valorCreditoARealizar = BigDecimal.ZERO;
				CreditoARealizar dadosCredito = null;

				if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
					java.util.Iterator<CreditoARealizar> colecaoCreditoARealizarIterator = colecaoCreditoARealizar.iterator();

					// percorre a colecao de credito a realizar somando o valor para obter um valor
					// total
					while(colecaoCreditoARealizarIterator.hasNext()){

						dadosCredito = (CreditoARealizar) colecaoCreditoARealizarIterator.next();
						valorCreditoARealizar = valorCreditoARealizar.add(dadosCredito.getValorTotal());
					}
				}

				Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoImovel.getColecaoGuiasPagamentoValores();

				BigDecimal valorGuiaPagamento = BigDecimal.ZERO;

				if(colecaoGuiaPagamentoValores != null && !colecaoGuiaPagamentoValores.isEmpty()){

					Iterator<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperIterator = colecaoGuiaPagamentoValores.iterator();

					// Percorre a colecao de Prestações da Guia de Pagamento somando o valor para
					// obter o
					// total em aberto
					while(colecaoGuiaPagamentoValoresHelperIterator.hasNext()){

						GuiaPagamentoValoresHelper dadosGuiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) colecaoGuiaPagamentoValoresHelperIterator
										.next();
						valorGuiaPagamento = valorGuiaPagamento.add(dadosGuiaPagamentoValoresHelper.getValorTotalPrestacao().setScale(
										Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
					}
				}

				sb.append(((valorConta.add(valorDebitoACobrar.add(valorGuiaPagamento))).subtract(valorCreditoARealizar)).toString() + ";");
				Integer qtd = 0;
				if(colecaoContaValores != null){
					qtd += colecaoContaValores.size();
				}
				if(colecaoDebitoACobrar != null){
					qtd += colecaoDebitoACobrar.size();
				}
				if(colecaoGuiaPagamentoValores != null){
					qtd += colecaoGuiaPagamentoValores.size();
				}
				if(colecaoCreditoARealizar != null){
					qtd += colecaoCreditoARealizar.size();
				}

				sb.append(qtd + "\n");
			}

			retorno = this.getBytesFromFileZip(sb, "RelatorioImoveisAcaoCobranca.csv");
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_IMOVEIS_ACAO_COBRANCA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(IOException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	private byte[] getBytesFromFileZip(StringBuffer sb, String nomeArquivo) throws IOException{

		byte[] retorno = null;
		ZipOutputStream zos = null;
		BufferedWriter out = null;
		File leituraTipo = new File(nomeArquivo);
		File compactado = new File(nomeArquivo + ".zip");

		zos = new ZipOutputStream(new FileOutputStream(compactado));
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leituraTipo.getAbsolutePath())));

		out.write(sb.toString());
		out.flush();
		out.close();

		ZipUtil.adicionarArquivo(zos, leituraTipo);

		zos.close();
		leituraTipo.delete();
		retorno = this.getBytesFromFile(compactado);
		compactado.delete();

		return retorno;
	}

	private byte[] getBytesFromFile(File file) throws IOException{

		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if(length > Integer.MAX_VALUE){
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0){
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if(offset < bytes.length){
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioImoveisAcaoCobranca", this);
	}

}
