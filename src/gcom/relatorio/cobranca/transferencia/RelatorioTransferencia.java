
package gcom.relatorio.cobranca.transferencia;

import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.transferencia.TransferenciaDebitos;
import gcom.cobranca.transferencia.TransferenciaDebitosItem;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class RelatorioTransferencia
				extends TarefaRelatorio {

	public RelatorioTransferencia(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_TRANSFERENCIA_TERMO_ASSUNCAO_MODELO_1);
	}

	public RelatorioTransferencia() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	private List<RelatorioTransferenciaBean> inicializarBeanRelatorio(TransferenciaDebitos transferenciaDebitos){

		// / Obter Dados do Cliente Destino
		Integer idClienteInteressado = null;
		String nomeClienteInteressado = null;
		String cpfCnpjClienteInteressado = null;
		String enderecoClienteInteressado = null;
		String telefoneClienteInteressado = null;
		Integer idMatriculaImovelDestino = null;
		String inscricaoImovelDestino = null;
		Integer idMatriculaImovelOrigem = null;
		String inscricaoImovelOrigem = null;
		Integer indicadorTitularDebitoImovel = null;
		String cepClienteInteressado = null;

		try{
			indicadorTitularDebitoImovel = Integer.valueOf(ParametroFaturamento.P_TIPO_RELACAO_ATUAL_TITULAR_DEBITO_IMOVEL.executar());
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio", e);
		}

		idMatriculaImovelOrigem = transferenciaDebitos.getImovelOrigem().getId();

		if(transferenciaDebitos.getClienteDestino() != null){
			idClienteInteressado = transferenciaDebitos.getClienteDestino().getId();

			idMatriculaImovelDestino = transferenciaDebitos.getImovelOrigem().getId();
		}else{
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, transferenciaDebitos
							.getImovelDestino().getId()));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							indicadorTitularDebitoImovel));

			ClienteImovel clienteImovelDestino = (ClienteImovel) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroClienteImovel, ClienteImovel.class.getName()));

			idClienteInteressado = clienteImovelDestino.getCliente().getId();

			idMatriculaImovelDestino = transferenciaDebitos.getImovelDestino().getId();
		}

		if(idMatriculaImovelOrigem != null){
			Imovel imovelOrigem = Fachada.getInstancia().pesquisarImovel(idMatriculaImovelOrigem);

			inscricaoImovelOrigem = imovelOrigem.getInscricaoFormatada();
		}

		if(idMatriculaImovelDestino != null){
			Imovel imovelDestino = Fachada.getInstancia().pesquisarImovel(idMatriculaImovelDestino);

			inscricaoImovelDestino = imovelDestino.getInscricaoFormatada();
		}

		if(idClienteInteressado != null){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idClienteInteressado));

			Cliente clienteOrigem = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente,
							Cliente.class.getName()));

			nomeClienteInteressado = clienteOrigem.getNome();

			if(clienteOrigem.getCpfFormatado() != null){
				cpfCnpjClienteInteressado = clienteOrigem.getCpfFormatado();
			}else{
				if(clienteOrigem.getCnpjFormatado() != null){
					cpfCnpjClienteInteressado = clienteOrigem.getCnpjFormatado();
				}else{
					cpfCnpjClienteInteressado = null;
				}
			}

			FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, idClienteInteressado));
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.INDICADOR_ENDERECO_CORRESPONDENCIA, 1));

			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");

			ClienteEndereco clienteEnderecoOrigem = (ClienteEndereco) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroClienteEndereco, ClienteEndereco.class.getName()));

			if(clienteEnderecoOrigem != null){
				enderecoClienteInteressado = clienteEnderecoOrigem.getEnderecoFormatado();
				cepClienteInteressado = clienteEnderecoOrigem.getLogradouroCep().getCep().getCepFormatado();
			}else{
				enderecoClienteInteressado = "";
				cepClienteInteressado = "";
			}

			FiltroClienteFone filtroClienteFone = new FiltroClienteFone(FiltroClienteFone.INDICADOR_TELEFONE_PADRAO);
			filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, idClienteInteressado));

			ClienteFone clienteFone = (ClienteFone) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroClienteFone,
							ClienteFone.class.getName()));

			if(clienteFone != null && clienteFone.getDddTelefone() != null){
				if(clienteFone.getRamal() != null){
					telefoneClienteInteressado = clienteFone.getDddTelefone() + " - " + clienteFone.getRamal();
				}else{
					telefoneClienteInteressado = clienteFone.getDddTelefone();
				}
			}else{
				telefoneClienteInteressado = "";
			}
		}


		List<RelatorioTransferenciaBean> retorno = new ArrayList<RelatorioTransferenciaBean>();

		for(TransferenciaDebitosItem transferenciaDebitosItem : transferenciaDebitos.getTransferenciaDebitosItens()){
			BigDecimal valor = BigDecimal.ZERO;

			if(transferenciaDebitosItem.getConta() != null){
				valor = transferenciaDebitosItem.getConta().getValorTotalContaBigDecimal();
			}else if(transferenciaDebitosItem.getDebitoACobrar() != null){
				valor = transferenciaDebitosItem.getDebitoACobrar().getValorTotal();
			}else if(transferenciaDebitosItem.getGuiaPagamento() != null){
				valor = transferenciaDebitosItem.getGuiaPagamento().getValorDebito();
			}else if(transferenciaDebitosItem.getCreditoARealizar() != null){
				valor = transferenciaDebitosItem.getCreditoARealizar().getValorTotal();
			}

			Integer idClienteOrigem = transferenciaDebitosItem.getClienteOrigem().getId();

			this.atualizarRelatorioTransferenciaBean(retorno, idClienteOrigem, idMatriculaImovelOrigem, inscricaoImovelOrigem,
							idClienteInteressado, nomeClienteInteressado, cpfCnpjClienteInteressado, enderecoClienteInteressado,
							telefoneClienteInteressado, cepClienteInteressado, idMatriculaImovelDestino, inscricaoImovelDestino, valor);
		}

		return retorno;

	}

	private List<RelatorioTransferenciaBean> atualizarRelatorioTransferenciaBean(
					List<RelatorioTransferenciaBean> colecaoRelatorioTransferenciaBean, Integer idClienteOrigem,
					Integer idMatriculaImovelOrigem, String inscricaoImovelOrigem, Integer idClienteInteressado,
					String nomeClienteInteressado, String cpfCnpjClienteInteressado, String enderecoClienteDestino,
					String telefoneClienteInteressado, String cepInteressado, Integer idMatriculaImovelDestino,
					String inscricaoImovelDestino, BigDecimal valor){

		Boolean bEncontrouCliente = false;

		for(RelatorioTransferenciaBean relatorioTransferenciaBean : colecaoRelatorioTransferenciaBean){
			if(relatorioTransferenciaBean.getIdClienteDevedorOrigem().equals(idClienteOrigem)){

				relatorioTransferenciaBean.setValorTranferencia(relatorioTransferenciaBean.getValorTranferencia().add(valor));

				bEncontrouCliente = true;
			}
		}
		
		if (!bEncontrouCliente) {
			RelatorioTransferenciaBean relatorioTransferenciaBean = new RelatorioTransferenciaBean();
			
			// Dados do Cliente Origem
			relatorioTransferenciaBean.setIdClienteDevedorOrigem(idClienteOrigem);

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idClienteOrigem));

			Cliente clienteOrigem = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente,
							Cliente.class.getName()));

			relatorioTransferenciaBean.setNomeDevedorOrigem(clienteOrigem.getNome());
			
			if(clienteOrigem.getCpfFormatado() != null){
				relatorioTransferenciaBean.setCpfCnpjDevedorOrigem(clienteOrigem.getCpfFormatado());
			}else{
				if(clienteOrigem.getCnpjFormatado() != null){
					relatorioTransferenciaBean.setCpfCnpjDevedorOrigem(clienteOrigem.getCnpjFormatado());
				}else{
					relatorioTransferenciaBean.setCpfCnpjDevedorOrigem("");
				}
			}

			FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, idClienteOrigem));
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.INDICADOR_ENDERECO_CORRESPONDENCIA, 1));
			
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			
			ClienteEndereco clienteEnderecoOrigem = (ClienteEndereco) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroClienteEndereco,
							ClienteEndereco.class.getName()));
			
			if(clienteEnderecoOrigem != null){
				relatorioTransferenciaBean.setEnderecoDevedorOrigem(clienteEnderecoOrigem.getEnderecoFormatado());
				relatorioTransferenciaBean.setCepDevedorOrigem(clienteEnderecoOrigem.getLogradouroCep().getCep().getCepFormatado());
			}else{
				relatorioTransferenciaBean.setEnderecoDevedorOrigem("Endereço não cadastrado");
				relatorioTransferenciaBean.setCepDevedorOrigem("");
			}

			FiltroClienteFone filtroClienteFone = new FiltroClienteFone(FiltroClienteFone.INDICADOR_TELEFONE_PADRAO);
			filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, idClienteOrigem));

			ClienteFone clienteFone = (ClienteFone) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroClienteFone,
							ClienteFone.class.getName()));

			if(clienteFone != null && clienteFone.getDddTelefone() != null){
				if(clienteFone.getRamal() != null){
					relatorioTransferenciaBean.setTelefoneDevedorOrigem(clienteFone.getDddTelefone() + " - " + clienteFone.getRamal());
				}else{
					relatorioTransferenciaBean.setTelefoneDevedorOrigem(clienteFone.getDddTelefone());
				}
			}else{
				relatorioTransferenciaBean.setTelefoneDevedorOrigem("");
			}

			relatorioTransferenciaBean.setMatriculaDevedorOrigem(idMatriculaImovelOrigem.toString());
			relatorioTransferenciaBean.setInscricaoDevedorOrigem(inscricaoImovelOrigem);
			
			
			// Dados do Cliente Destino
			relatorioTransferenciaBean.setIdClienteInteressado(idClienteInteressado);
			relatorioTransferenciaBean.setNomeInteressado(nomeClienteInteressado);

			if(cpfCnpjClienteInteressado != null){
				relatorioTransferenciaBean.setCpfCnpjInteressado(cpfCnpjClienteInteressado);
			}else{
				relatorioTransferenciaBean.setCpfCnpjInteressado("");
			}

			relatorioTransferenciaBean.setEnderecoInteressado(enderecoClienteDestino);

			if(telefoneClienteInteressado != null){
				relatorioTransferenciaBean.setTelefoneInteressado(telefoneClienteInteressado);
			}else{
				relatorioTransferenciaBean.setTelefoneInteressado("");
			}

			relatorioTransferenciaBean.setMatriculaInteressado(idMatriculaImovelDestino.toString());
			relatorioTransferenciaBean.setInscricaoInteressado(inscricaoImovelDestino);
			relatorioTransferenciaBean.setCepInteressado(cepInteressado);

			// Valor transferencia
			relatorioTransferenciaBean.setValorTranferencia(valor);

			colecaoRelatorioTransferenciaBean.add(relatorioTransferenciaBean);
		}

		return colecaoRelatorioTransferenciaBean;
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		List<RelatorioTransferenciaBean> colecaoTransfBean = this
						.inicializarBeanRelatorio((TransferenciaDebitos) getParametro("transferenciaDebitos"));

		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		try{
			parametros.put("inscricaoEstadualEmpresa",
							(String) Fachada.getInstancia().obterValorDoParametroPorCodigo(
											ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio", e1);
		}

		try{
			parametros.put("enderecoEmpresa", (String) Fachada.getInstancia().pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			throw new TarefaException("Erro ao gerar dados para o relatorio", e1);
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("nomeEmpresa", sistemaParametro.getNomeEmpresa());
		parametros.put("siglaEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());
		parametros.put("cnpjEmpresa", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));

		RelatorioDataSource ds = new RelatorioDataSource(colecaoTransfBean);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_TRANSFERENCIA_TERMO_ASSUNCAO_MODELO_1, parametros, ds, tipoFormatoRelatorio);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioTransferencia", this);
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer qtdeContas = (Integer) getParametro("qtdeContas");

		return qtdeContas;
	}

	public byte[] concatenarPFDs(List<byte[]> pdfsGerados) throws TarefaException{

		int pageOffset = 0;
		int f = 0;
		Document document = null;
		PdfCopy writer = null;
		byte[] pdfGerado = null;
		byte[] novoPDF = null;
		ByteArrayOutputStream baos = null;

		try{
			if(pdfsGerados != null && !pdfsGerados.isEmpty()){
				Iterator<byte[]> iterator = pdfsGerados.iterator();
				baos = new ByteArrayOutputStream();
				while(iterator.hasNext()){
					pdfGerado = (byte[]) iterator.next();
					PdfReader reader = new PdfReader(pdfGerado);
					int n = reader.getNumberOfPages();
					pageOffset += n;
					if(f == 0){
						document = new Document(reader.getPageSizeWithRotation(1));
						writer = new PdfCopy(document, baos);
						document.open();
					}
					PdfImportedPage page;
					for(int i = 0; i < n;){
						++i;
						page = writer.getImportedPage(reader, i);
						writer.addPage(page);
					}
					PRAcroForm form = reader.getAcroForm();
					if(form != null){
						writer.copyAcroForm(reader);
					}
					f++;
				}
				document.close();
			}
		}catch(Exception e){
			throw new TarefaException(e.getMessage(), e);
		}

		novoPDF = baos.toByteArray();

		return novoPDF;
	}

}
