package gcom.relatorio.cobranca.parcelamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.endereco.FiltroEnderecoTipo;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.FiltroIndicesAcrescimosImpontualidade;
import gcom.cobranca.IndicesAcrescimosImpontualidade;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ParcelamentoRelatorioHelper;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * [UC0214] Efetuar Parcelamento de D�bitos
 * 
 * @author Cinthya
 * @date 14/10/2011
 */
public class GeradorDadosRelatorioParcelamento
				extends GeradorDadosRelatorioResolucaoDiretoria {

	public GeradorDadosRelatorioParcelamento() {

		super();
	}

	@Override
	public List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> gerarDados(Parcelamento parcelamento, int idFuncionalidadeIniciada)
					throws GeradorRelatorioParcelamentoException{

		// cole��o de beans do relat�rio
		List relatorioParcelamentosBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioParcelamentoResolucaoDiretoriaLayoutBean relatorioParcelamentoResolucaoDiretoriaLayoutBean = null;

		ParcelamentoRelatorioHelper parcelamentoRelatorioHelper = fachada.pesquisarParcelamentoRelatorio(parcelamento.getId());

		ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = parcelamento.getParcelamentoTermoTestemunhas();
		ParcelamentoDadosTermo parcelamentoDadosTermo = parcelamento.getParcelamentoDadosTermo();

		// Matricula Imovel
		String matriculaImovel = "";
		String inscricaoEstadual = "";
		if(parcelamentoRelatorioHelper.getIdImovel() != null){
			matriculaImovel = parcelamentoRelatorioHelper.getIdImovel().toString();
			inscricaoEstadual = fachada.pesquisarInscricaoImovel(parcelamentoRelatorioHelper.getIdImovel(), true);
		}

		// Nome Cliente
		String nomeCliente = "";
		if(parcelamentoRelatorioHelper.getNomeClienteParcelamento() != null
						&& !parcelamentoRelatorioHelper.getNomeClienteParcelamento().equals("")){

			nomeCliente = parcelamentoRelatorioHelper.getNomeClienteParcelamento();
		}else{
			nomeCliente = parcelamentoRelatorioHelper.getNomeCliente();
		}

		nomeCliente = nomeCliente.trim();

		// Endereco
		String endereco = "";
		if(parcelamentoRelatorioHelper.getEndereco() != null && parcelamentoRelatorioHelper.getIdImovel() != null){
			endereco = fachada.pesquisarEnderecoComDetalhamento(parcelamentoRelatorioHelper.getIdImovel());
		}

		// Endere�o Abreviado
		String enderecoAbreviado = "";
		if(parcelamentoRelatorioHelper.getEnderecoAbreviado() != null && parcelamentoRelatorioHelper.getIdImovel() != null){
			enderecoAbreviado = fachada.pesquisarEnderecoClienteAbreviadoComDetalhemento(parcelamentoRelatorioHelper.getIdImovel());
		}

		// CEP
		String cep = "";
		if(parcelamentoRelatorioHelper.getCep() != null){
			cep = parcelamentoRelatorioHelper.getCep();
		}

		// Telefone
		String telefone = "";
		if(parcelamentoRelatorioHelper.getTelefone() != null && !parcelamentoRelatorioHelper.getTelefone().equals("")){
			telefone = parcelamentoRelatorioHelper.getTelefone();
		}

		// Cpf ou Cnpj
		String cpfCnpjCliente = "";
		if(parcelamentoRelatorioHelper.getCpfClienteParcelamento() != null
						&& !parcelamentoRelatorioHelper.getCpfClienteParcelamento().equals("")
						&& parcelamentoRelatorioHelper.getIndicadorPessoaFisicaJuridica() == 1){

			cpfCnpjCliente = parcelamentoRelatorioHelper.getCpfClienteParcelamento();
		}else{
			cpfCnpjCliente = parcelamentoRelatorioHelper.getCpfCnpj();
		}

		// RG
		String rgCliente = "";
		if(parcelamentoRelatorioHelper.getRgClienteParcelamento() == null
						|| parcelamentoRelatorioHelper.getRgClienteParcelamento().equals("")){
			rgCliente = parcelamentoRelatorioHelper.getRgCliente();
		}else{
			rgCliente = parcelamentoRelatorioHelper.getRgClienteParcelamento();
		}

		if(rgCliente == null || rgCliente.equals("0")){
			rgCliente = "";
		}

		// Taxa Juros Mora e Taxa Juros Mora Extenso
		String taxaJurosMora = "";
		String taxaJurosMoraExtenso = "";
		if(parcelamentoRelatorioHelper.getTaxaJuros() != null || !parcelamentoRelatorioHelper.getTaxaJuros().equals("")){

			taxaJurosMora = parcelamentoRelatorioHelper.getTaxaJuros();

			taxaJurosMoraExtenso = Util.numero(new Double(parcelamentoRelatorioHelper.getTaxaJuros().replace(",", ".")).longValue());

		}

		// Total Debitos
		String totalDebitos = "";
		String totalDebitosExtenso = "";
		if(parcelamentoRelatorioHelper.getValorTotalDebitos() != null){
			totalDebitosExtenso = Util.valorExtenso(parcelamentoRelatorioHelper.getValorTotalDebitos());
			totalDebitos = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorTotalDebitos());
		}

		// Total Descontos
		String totalDescontos = "";
		if(parcelamentoRelatorioHelper.getValorTotalDescontos() != null){
			totalDescontos = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorTotalDescontos());
		}

		// Valor Entrada e Valor Entrada Extenso
		String valorEntrada = "";
		String valorEntradaExtenso = "";
		if(parcelamentoRelatorioHelper.getValorEntrada() != null){
			valorEntradaExtenso = Util.valorExtenso(parcelamentoRelatorioHelper.getValorEntrada());
			valorEntrada = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorEntrada());
		}

		// Numero Parcelas e Numero Parcelas Extenso
		String numeroParcelas = "";
		String numeroParcelasExtenso = "";
		if(parcelamentoRelatorioHelper.getNumeroParcelas() != null){
			numeroParcelas = parcelamentoRelatorioHelper.getNumeroParcelas().toString();
			numeroParcelasExtenso = Util.numero(new Double(parcelamentoRelatorioHelper.getNumeroParcelas()).longValue());
		}

		// Valor Parcela e Valor Parcela Extenso
		String valorParcela = "";
		String valorParcelaExtenso = "";
		if(parcelamentoRelatorioHelper.getValorParcela() != null){
			valorParcelaExtenso = Util.valorExtenso(parcelamentoRelatorioHelper.getValorParcela());
			valorParcela = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorParcela());
		}

		// Descricao Localidade
		String descricaoLocalidade = "";
		if(parcelamentoRelatorioHelper.getDescricaoLocalidade() != null){
			descricaoLocalidade = parcelamentoRelatorioHelper.getDescricaoLocalidade().trim();
		}

		// Descricao colecao ano mes ref

		String colecaoAnoMesReferencia = "";
		if(parcelamentoRelatorioHelper.getColecaoAnoMesReferencia() != null){
			colecaoAnoMesReferencia = parcelamentoRelatorioHelper.getColecaoAnoMesReferencia();
		}

		// Descricao colecao ano mes ref sobra
		String colecaoAnoMesReferenciaSobra = "";
		if(parcelamentoRelatorioHelper.getColecaoAnoMesReferenciaSobra() != null){
			colecaoAnoMesReferenciaSobra = parcelamentoRelatorioHelper.getColecaoAnoMesReferenciaSobra();
		}

		// DetalhamentoGuiasPrestacoes
		String detalhamentoGuiasPrestacoes = "";
		if(parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoes() != null){
			detalhamentoGuiasPrestacoes = parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoes();
		}

		// DetalhamentoGuiasPrestacoesSobra
		String detalhamentoGuiasPrestacoesSobra = "";
		if(parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoesSobra() != null){
			detalhamentoGuiasPrestacoesSobra = parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoesSobra();
		}

		// Imovel Dia Vencimento
		String imovelDiaVencimento = "";
		if(parcelamentoRelatorioHelper.getImovelDiaVencimento() != null){
			imovelDiaVencimento = parcelamentoRelatorioHelper.getImovelDiaVencimento().toString();
		}

		// Valor Multas
		String valorMultas = "";
		if(parcelamentoRelatorioHelper.getValorMultas() != null){
			valorMultas = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorMultas());
		}

		// Valor Juros Mora
		String valorJurosMora = "";
		if(parcelamentoRelatorioHelper.getValorJurosMora() != null){
			valorJurosMora = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorJurosMora());
		}

		// Valor Juros Parcelamento
		String valorJurosParcelamento = "";
		if(parcelamentoRelatorioHelper.getValorJurosParcelamento() != null){
			valorJurosParcelamento = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorJurosParcelamento());
		}

		// Inicio do Per�odo de Fornecimento dos Servi�os
		String inicioPeriodoFornecimento = "";
		if(parcelamentoRelatorioHelper.getInicioPeriodoFornecimento() != null){
			inicioPeriodoFornecimento = parcelamentoRelatorioHelper.getInicioPeriodoFornecimento();
		}

		// Fim do Per�odo de Fornecimento dos Servi�os
		String fimPeriodoFornecimento = "";
		if(parcelamentoRelatorioHelper.getFimPeriodoFornecimento() != null){
			fimPeriodoFornecimento = parcelamentoRelatorioHelper.getFimPeriodoFornecimento();
		}

		// Indicador de Pessoa F�sica ou Jur�dica
		String indicadorPessoaFisicaJuridica = "";
		if(parcelamentoRelatorioHelper.getIndicadorPessoaFisicaJuridica() != null){
			indicadorPessoaFisicaJuridica = parcelamentoRelatorioHelper.getIndicadorPessoaFisicaJuridica().toString();
		}

		// Total de contas
		String numeroContas = parcelamentoRelatorioHelper.getNumeroContas().toString();

		String taxaMulta = "0";
		String taxaMultaExtenso = "";

		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, parcelamento.getId()));
		Collection<Parcelamento> colecaoParcelamento = fachada.pesquisar(filtroParcelamento, Parcelamento.class.getName());
		Parcelamento parcelamentoAux = null;
		if(colecaoParcelamento != null && !colecaoParcelamento.isEmpty()){
			parcelamentoAux = colecaoParcelamento.iterator().next();
		}

		if(parcelamentoAux != null){
			FiltroIndicesAcrescimosImpontualidade filtroIndicesAcrescimosImpontualidade = new FiltroIndicesAcrescimosImpontualidade();
			filtroIndicesAcrescimosImpontualidade.adicionarParametro(new ParametroSimples(
							FiltroIndicesAcrescimosImpontualidade.ANO_MES_REFERENCIA, parcelamentoAux.getAnoMesReferenciaFaturamento()));
			Collection<IndicesAcrescimosImpontualidade> colecaoIndicesAcrescimosImpontualidade = fachada.pesquisar(
							filtroIndicesAcrescimosImpontualidade, IndicesAcrescimosImpontualidade.class.getName());
			IndicesAcrescimosImpontualidade indicesAcrescimosImpontualidade = null;
			if(colecaoIndicesAcrescimosImpontualidade != null && !colecaoIndicesAcrescimosImpontualidade.isEmpty()){
				indicesAcrescimosImpontualidade = colecaoIndicesAcrescimosImpontualidade.iterator().next();
			}
			if(indicesAcrescimosImpontualidade != null){
				if(indicesAcrescimosImpontualidade.getPercentualMulta() != null){
					taxaMulta = indicesAcrescimosImpontualidade.getPercentualMulta().toString();
					taxaMultaExtenso = Util.numero(new Double(taxaMulta.replace(",", ".")).longValue());
				}
			}
		}

		// se a cole��o de par�metros da analise n�o for vazia
		if(parcelamentoRelatorioHelper != null){

			// Pega a Data Atual formatada da seguinte forma: dd de m�s(por extenso) de aaaa
			// Ex: 23 de maio de 1985
			String idImovel = parcelamentoRelatorioHelper.getIdImovel().toString();
			String idCliente = parcelamentoRelatorioHelper.getIdCliente().toString();

			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, idCliente));

			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_PROFISSAO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RELACAO_TIPO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RG_ORGAO_EXPEDIDOR);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_UNIDADE_FEDERACAO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.LOGRADOURO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CEP);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.LOCALIDADE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.SETOR_COMERCIAL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.QUADRA);

			Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			String tipoCliente = "";
			String profissaoCliente = "";
			String cidadeImovelCadastrado = "";

			for(Iterator iterator = colecaoClienteImovel.iterator(); iterator.hasNext();){
				ClienteImovel clienteImovel = (ClienteImovel) iterator.next();
				if(clienteImovel.getClienteRelacaoTipo() != null
								&& !clienteImovel.getClienteRelacaoTipo().getId().toString().trim().equals("")){
					tipoCliente = clienteImovel.getClienteRelacaoTipo().getId().toString();
				}

				if(clienteImovel.getCliente().getProfissao() != null){
					profissaoCliente = clienteImovel.getCliente().getProfissao().getDescricao();
				}

				if(clienteImovel.getImovel().getLogradouroCep() != null){
					cidadeImovelCadastrado = clienteImovel.getImovel().getLogradouroCep().getCep().getMunicipio();
				}

			}

			String funcionario = "";
			String cpfUsuarioLogado = "";
			String nomeFuncionario = "";
			if(parcelamento.getUsuario() != null){
				FiltroUsuario filtoUsuario = new FiltroUsuario();
				filtoUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, parcelamento.getUsuario().getId()));
				filtoUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.FUNCIONARIO);
				Collection colecaoUsuario = fachada.pesquisar(filtoUsuario, Usuario.class.getName());
				Usuario usuario = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuario);
				if(usuario != null && usuario.getFuncionario() != null){
					nomeFuncionario = usuario.getFuncionario().getNome();
					String matriculaFuncionario = usuario.getFuncionario().getId().toString();
					cpfUsuarioLogado = Util.formatarCpf(usuario.getCpf());
					funcionario = matriculaFuncionario + "-" + nomeFuncionario;
				}else{
					nomeFuncionario = usuario.getNomeUsuario();
					String matriculaFuncionario = usuario.getId().toString();
					cpfUsuarioLogado = matriculaFuncionario;
					funcionario = matriculaFuncionario + "-" + nomeFuncionario;
				}
			}

			String intervaloPeriodoDebito = "";
			if(parcelamento.getResolucaoDiretoria() != null && parcelamento.getResolucaoDiretoria().getId() != null){
				Collection collecaoParcelamentoSituacaoEspecial = fachada.verificarRDComRestricao(parcelamento.getResolucaoDiretoria()
								.getId());
				if(collecaoParcelamentoSituacaoEspecial != null && !collecaoParcelamentoSituacaoEspecial.isEmpty()){
					ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) Util
									.retonarObjetoDeColecao(collecaoParcelamentoSituacaoEspecial);
					intervaloPeriodoDebito = parcelamentoSituacaoEspecial.getFormatarAnoMesParaMesAnoReferenciaInicio() + " - "
									+ parcelamentoSituacaoEspecial.getFormatarAnoMesParaMesAnoReferenciaFim();
				}
			}

			String nomeEmpresa = "";
			String nomeEmpresaAbreviado = "";
			String enderecoEmpresa = "";
			String cnpjEmpresa = "";
			String cepEmpresa = "";
			String emailEmpresa = "";
			String dataParcelamento = "";

			SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

			if(sistemaParametros != null){

				nomeEmpresa = sistemaParametros.getNomeEmpresa();
				nomeEmpresaAbreviado = sistemaParametros.getNomeAbreviadoEmpresa();
				cnpjEmpresa = Util.formatarCnpj(sistemaParametros.getCnpjEmpresa());
				cepEmpresa = sistemaParametros.getCep().getCepFormatado();
				emailEmpresa = sistemaParametros.getDescricaoEmail();
			}

			if(sistemaParametros.getLogradouro() != null){

				FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
				filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, sistemaParametros.getLogradouro().getId()));
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio");
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio.unidadeFederacao");
				Collection colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

				Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

				String bairro = sistemaParametros.getBairro().getNome();
				String numero = sistemaParametros.getNumeroImovel();

				if(logradouro != null){
				
					String descricaoLogradouroTipo = "";

					if(!Util.isVazioOuBranco(logradouro.getLogradouroTipo())
									&& !Util.isVazioOuBranco(logradouro.getLogradouroTipo().getDescricao())){
						descricaoLogradouroTipo = logradouro.getLogradouroTipo().getDescricao();
					}

					String descricaoLogradouroTitulo = "";

					if(!Util.isVazioOuBranco(logradouro.getLogradouroTitulo())
									&& !Util.isVazioOuBranco(logradouro.getLogradouroTitulo().getDescricao())){
						descricaoLogradouroTitulo = logradouro.getLogradouroTitulo().getDescricao();
					}

					String descricaoLogradouro = "";

					if(!Util.isVazioOuBranco(logradouro.getNome())){
						descricaoLogradouro = logradouro.getNome();
					}

					String descricaoLogradouroMunicipio = "";

					if(!Util.isVazioOuBranco(logradouro.getMunicipio()) && !Util.isVazioOuBranco(logradouro.getMunicipio().getNome())){
						descricaoLogradouroMunicipio = logradouro.getMunicipio().getNome();
					}

					if(!Util.isVazioOuBranco(logradouro.getMunicipio().getUnidadeFederacao())
									&& !Util.isVazioOuBranco(logradouro.getMunicipio().getUnidadeFederacao().getSigla())){
						descricaoLogradouroMunicipio = descricaoLogradouroMunicipio + " - "
										+ logradouro.getMunicipio().getUnidadeFederacao().getSigla();
					}

					enderecoEmpresa = descricaoLogradouroTipo + " " + descricaoLogradouroTitulo + " " + descricaoLogradouro + ", " + numero
									+ " - " + bairro + " - " + descricaoLogradouroMunicipio;

					Date dataAtual = new Date();

					String dado = "";
					String P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO;
					try{
						P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO = ParametroCobranca.P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO
										.executar();
					}catch(ControladorException e){
						throw new GeradorRelatorioParcelamentoException(e.getMessage());
					}
					if(P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO.equals(ConstantesSistema.ATIVO)){
						dado = Util.recuperaDiaMesAnoDaData(parcelamento.getDataEntradaParcelamento());

					}else{
						dado = Util.recuperaDiaMesAnoDaData(dataAtual);

					}
					String dia = dado.substring(0, 2);
					String mes = Util.retornaDescricaoMes(Integer.parseInt(dado.substring(2, 4)));
					String ano = dado.substring(4, 8);

					dataParcelamento = descricaoLogradouroMunicipio + ", " + dia + " de " + mes + " de " + ano + ".";

				}
			}

			String nomeRepresentante = "";
			String cargoRepresentante = "";
			String dataNomeacaoRepresentante = "";
			String matriculaRepresentante = "";
			String nomeSignatario = "";
			String cargoSignatario = "";
			String dataNomeacaoSignatario = "";
			String matriculaSignatario = "";
			String siteEmpresa = "";
			String nomeTestemunha1 = "";
			String cpfTestemunha1 = "";
			String nomeTestemunha2 = "";
			String cpfTestemunha2 = "";
			String juntaComercialEmpresa = "";
			String inscricaoEstadualEmpresa = "";

			try{
				nomeRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_NOME_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				cargoRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_CARGO_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				dataNomeacaoRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_DATA_NOMEACAO_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				matriculaRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_MATRICULA_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				nomeSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_NOME_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				cargoSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_CARGO_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				dataNomeacaoSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_DATA_NOMEACAO_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				matriculaSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_MATRICULA_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				siteEmpresa = String
								.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema("P_SITE_EMPRESA")).getValor());

				try{
					juntaComercialEmpresa = (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA
									.getCodigo());
					if(juntaComercialEmpresa != null && !juntaComercialEmpresa.equals("")
									&& !juntaComercialEmpresa.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
						juntaComercialEmpresa = Util.formatarJuntaComercial(juntaComercialEmpresa);
					}else{
						juntaComercialEmpresa = null;
					}
				}catch(NegocioException e1){
					e1.printStackTrace();
					throw new TarefaException("Erro ao gerar dados para o relatorio");
				}

				try{
					inscricaoEstadualEmpresa = Util.formatarInscricaoEstadual((String) fachada
									.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
				}catch(NegocioException e1){
					e1.printStackTrace();
					throw new TarefaException("Erro ao gerar dados para o relatorio");
				}

			}catch(PCGException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			String inicioCobranca = "";
			if(parcelamento.getDataEntradaParcelamento() != null){

				inicioCobranca = Util.formataMesAno(parcelamento.getDataEntradaParcelamento());

			}

			String terminioCobranca = "";
			if(inicioCobranca != null && !inicioCobranca.equalsIgnoreCase("") && numeroParcelas != null
							&& !numeroParcelas.equalsIgnoreCase("")){


				terminioCobranca = Util.somaMesMesAnoComBarra(inicioCobranca, Integer.parseInt(numeroParcelas) - 1).toString();
			}
			
			
			String descOrgaoExpRgCliente = "";
			if(parcelamentoRelatorioHelper.getDescOrgaoExpRgCliente() != null){
				descOrgaoExpRgCliente = parcelamentoRelatorioHelper.getDescOrgaoExpRgCliente();

			}

			if(parcelamentoTermoTestemunhas != null){
				nomeTestemunha1 = parcelamentoTermoTestemunhas.getNomeTestemunha1();
				cpfTestemunha1 = Util.formatarCpf(parcelamentoTermoTestemunhas.getCpfTestemunha1());
				nomeTestemunha2 = parcelamentoTermoTestemunhas.getNomeTestemunha2();
				cpfTestemunha2 = Util.formatarCpf(parcelamentoTermoTestemunhas.getCpfTestemunha2());
			}

			// Total negociado
			String totalNegociado = "";
			String totalNegociadoExtenso = "";
			if(parcelamentoRelatorioHelper.getTotalNegociado() != null){
				totalNegociado = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getTotalNegociado());
				totalNegociadoExtenso = Util.valorExtenso(parcelamentoRelatorioHelper.getTotalNegociado());
			}

			String valorParcelado = "";
			String valorParceladoExtenso = "";
			if(parcelamentoRelatorioHelper.getValorTotalDebitos() != null && parcelamentoRelatorioHelper.getValorEntrada() != null){

				BigDecimal valorSucubenciaEntrada = BigDecimal.ZERO;
				if(parcelamento.getValorSucumbenciaAtualEP() != null){
					valorSucubenciaEntrada = parcelamento.getValorSucumbenciaAtualEP();
				}

				valorParcelado = Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorTotalDebitos()
								.subtract(parcelamentoRelatorioHelper.getValorEntrada()).add(valorSucubenciaEntrada));
				valorParceladoExtenso = Util.valorExtenso(parcelamentoRelatorioHelper.getValorTotalDebitos()
								.subtract(parcelamentoRelatorioHelper.getValorEntrada()).add(valorSucubenciaEntrada));
			}

			relatorioParcelamentoResolucaoDiretoriaLayoutBean = new RelatorioParcelamentoResolucaoDiretoriaLayoutBean(

			// Matr�cula do Im�vel
							matriculaImovel,

							// Nome Cliente
							nomeCliente,

							// Endere�o
							endereco,

							// CPF/CNPJ
							cpfCnpjCliente,

							// Taxa Juros
							taxaJurosMora,

							// Taxa Juros por Extenso
							// Util.numero(Long.parseLong(parcelamentoRelatorioHelper.getTaxaJuros())),
							taxaJurosMoraExtenso,

							// Taxa Multa
							taxaMulta,

							// Taxa Multa por Extenso
							taxaMultaExtenso,

							// Total D�bitos
							totalDebitos,

							// Total D�bitos Extenso
							totalDebitosExtenso,

							// Total Descontos
							totalDescontos,

							// Valor Entrada
							valorEntrada,

							// N�mero de Parcelas
							numeroParcelas,

							// N�mero de Parcelas Extenso
							numeroParcelasExtenso,

							// Valor da Parcela
							valorParcela,

							// Descricao Localidade
							descricaoLocalidade,

							// Colecao ano mes ref
							colecaoAnoMesReferencia,

							// Colecao ano mes ref sobra
							colecaoAnoMesReferenciaSobra,

							// Detalhamento Guias Prestacoes
							detalhamentoGuiasPrestacoes,

							// Detalhamento Guias Prestacoes Sobra
							detalhamentoGuiasPrestacoesSobra,

							// Imovel Dia Vencimento
							imovelDiaVencimento,

							// Tipo Cliente
							tipoCliente,

							// RG
							rgCliente,

							// Valor Multas
							valorMultas,

							// Valor Juros/Mora
							valorJurosMora,

							// Valor Juros/Parcelamento
							valorJurosParcelamento,
							// Inscri��o
							inscricaoEstadual,
							// Funcionario
							funcionario,
							// Intervalo Periodo Debito
							intervaloPeriodoDebito,

							// Telefone
							telefone,

							// Valor Parcela Extenso
							valorParcelaExtenso,

							// Valor Entrada Extenso
							valorEntradaExtenso,

							// CEP
							cep,

							// Inicio do Periodo de Fornecimento
							inicioPeriodoFornecimento,

							// Fim do Periodo de Fornecimento
							fimPeriodoFornecimento,

							// Numero de Contas
							numeroContas,

							// Endere�o abreviado
							enderecoAbreviado,
							// Nome Empresa
							nomeEmpresa,
							// Nome Empresa Abreviado
							nomeEmpresaAbreviado,
							// Endereco Empresa
							enderecoEmpresa,
							// Cnpj Empresa
							cnpjEmpresa,
							// Nome Representante
							nomeRepresentante,
							// Data nomeacao Representante
							dataNomeacaoRepresentante,
							// Matricula Representante
							matriculaRepresentante,
							// Nome Signatario
							nomeSignatario,
							// Data Nomeacao Signatario
							dataNomeacaoSignatario,
							// Matricula Signatario
							matriculaSignatario,
							// Cep empresa
							cepEmpresa,
							// Emeail Empresa
							emailEmpresa,
							// Site Empresa
							siteEmpresa,
							// Cargo Representante
							cargoRepresentante,
							// Cargo Signatario
							cargoSignatario,
							// Nome Funcionario
							nomeFuncionario,
							// Cpf Usuario Logado
							cpfUsuarioLogado,
							// Inicio Cobran�a
							inicioCobranca,
							// Terminio Cobran�a
							terminioCobranca,
							// Descri��o Org�o expedidor
							descOrgaoExpRgCliente,
							// Cidade e Data Atual
							dataParcelamento,
							// Indicador de Pessoa F�sica ou Jur�dica
							indicadorPessoaFisicaJuridica,
							// Nome da Testemunha 1
							nomeTestemunha1,
							// CPF da Testemunha 1
							cpfTestemunha1,
							// Nome da Testemunha 2
							nomeTestemunha2,
							// CPF da Testemunha 2
							cpfTestemunha2,
							// Profiss�o Cliente
							profissaoCliente,
							// Junta Comercial Empresa - JUCER
							juntaComercialEmpresa,
							// Inscricao Estadual Empresa
							inscricaoEstadualEmpresa,
							// Cidade do Im�vel
							cidadeImovelCadastrado, totalNegociado, totalNegociadoExtenso, valorParcelado, valorParceladoExtenso);


			if(parcelamentoRelatorioHelper.getIdImovel() != null){

				Cliente cliente = fachada.retornarDadosClienteProprietario(parcelamentoRelatorioHelper.getIdImovel());

				if(cliente != null){

					if(cliente.getNome() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeProprietarioImovel(cliente.getNome().trim());
					}

					if(cliente.getCpf() != null && cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() == 1){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setCpfProprietarioImovel(cliente.getCpfFormatado().trim());

					}else if(cliente.getCnpjFormatado() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setCpfProprietarioImovel(cliente.getCnpjFormatado().trim());
					}

					if(cliente.getRg() != null){
						String rgProprietarioImovel = cliente.getRg().trim();
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setRgProprietarioImovel(rgProprietarioImovel);
					}

					if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorPessoaFisicaJuridicaProprietarioImovel(cliente
										.getClienteTipo().getIndicadorPessoaFisicaJuridica().toString());
					}

					if(cliente.getNacionalidade() != null && cliente.getNacionalidade().getDescricao() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNacionaliadeProprietarioImovel(cliente.getNacionalidade()
										.getDescricao().trim());
					}

					FiltroClienteEndereco filtroImovelEndereco = new FiltroClienteEndereco();
					filtroImovelEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, cliente.getId()));

					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");

					Collection<ClienteEndereco> colecaoImovelEndereco = fachada.pesquisar(filtroImovelEndereco,
									ClienteEndereco.class.getName());
					for(ClienteEndereco imovelEndereco : colecaoImovelEndereco){
						if(imovelEndereco.getIndicadorEnderecoCorrespondencia().intValue() == 1){
							relatorioParcelamentoResolucaoDiretoriaLayoutBean.setEnderecoProprietarioImovel(imovelEndereco
											.getEnderecoFormatado());
						}
					}


				}

				Categoria categoria = fachada.obterPrincipalCategoriaImovel(parcelamento.getImovel().getId());
				if(categoria != null){
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoCategoriaImovel(categoria.getDescricao());
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setEnderecoImovel(endereco);

				if(parcelamento.getImovel() != null && parcelamento.getImovel().getId() != null){
					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, parcelamento.getImovel().getId()));
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");

					Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

					if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
						Imovel imovel = imovelPesquisado.iterator().next();
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setInscricaoImovel(imovel.getInscricaoFormatada());
					}
				}

			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeReponsavelParcelamento(parcelamentoDadosTermo.getNomeCliente());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNacionalidadeCliente() != null
							&& parcelamentoDadosTermo.getNacionalidadeCliente().getId() != null){
				FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.ID, parcelamentoDadosTermo
								.getNacionalidadeCliente().getId()));

				Collection<Nacionalidade> nacionalidade = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoNacionalidadeReponsavelParcelamento(nacionalidade.iterator()
								.next().getDescricao());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroRgCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroRgReponsavelParcelamento(parcelamentoDadosTermo
								.getNumeroRgCliente());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroCpfCliente() != null){
				String cpfFormatado = parcelamentoDadosTermo.getNumeroCpfCliente();
				if(cpfFormatado != null && cpfFormatado.length() == 11){

					cpfFormatado = cpfFormatado.substring(0, 3) + "." + cpfFormatado.substring(3, 6) + "." + cpfFormatado.substring(6, 9)
									+ "-" + cpfFormatado.substring(9, 11);
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroCpfReponsavelParcelamento(cpfFormatado);				
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getDescricaoEnderecoCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setEnderecoReponsavelParcelamento(parcelamentoDadosTermo
								.getDescricaoEnderecoCliente());
			}

			if((parcelamentoRelatorioHelper.getValorTotalDebitos() != null) && (parcelamento.getValorPrestacao() != null)
							&& (parcelamento.getNumeroPrestacoes() != null)){

				BigDecimal valoDiferencaParcelas = parcelamentoRelatorioHelper.getValorTotalDebitos().subtract(
								(parcelamento.getValorPrestacao().multiply(new BigDecimal(parcelamento.getNumeroPrestacoes()))));

				if(parcelamento != null && parcelamento.getValorAtualizacaoMonetariaSucumbenciaAnterior() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorAtualizacaoMonetariaSucumbenciaAnterior());
				}

				if(parcelamento != null && parcelamento.getValorJurosMoraSucumbenciaAnterior() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorJurosMoraSucumbenciaAnterior());
				}

				if(parcelamento != null && parcelamento.getValorSucumbenciaAnterior() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorSucumbenciaAnterior());
				}

				BigDecimal valorSucubenciaEntrada = BigDecimal.ZERO;
				if(parcelamento.getValorSucumbenciaAtualEP() != null){
					valorSucubenciaEntrada = parcelamento.getValorSucumbenciaAtualEP();
				}

				if(parcelamento.getValorEntrada() != null){
					BigDecimal valorEntradaAtualizada = parcelamento.getValorEntrada();

					if(valorSucubenciaEntrada.compareTo(BigDecimal.ZERO) != 0){
						valorEntradaAtualizada = valorEntradaAtualizada.subtract(valorSucubenciaEntrada);
					}

					valoDiferencaParcelas = valoDiferencaParcelas.subtract(valorEntradaAtualizada);
				}

				if(parcelamento.getValorDesconto() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorDesconto());
				}

				if(parcelamento.getValorCreditoARealizar() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorCreditoARealizar());
				}

				if(valoDiferencaParcelas.doubleValue() != 0){
					valoDiferencaParcelas = valoDiferencaParcelas.add(parcelamento.getValorPrestacao());

					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorParcelaDiferenca(Util
									.formatarMoedaReal(valoDiferencaParcelas).toString());
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorParcelaDiferencaExtenso(Util
									.valorExtenso((valoDiferencaParcelas.abs())));
				}
			}
			
			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getTituloPosse() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setTituloPosseParcelamento(parcelamentoDadosTermo.getTituloPosse());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeExecutado() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeExecutadoParcelamento(parcelamentoDadosTermo.getNomeExecutado());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroVara() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroVaraParcelamento(parcelamentoDadosTermo.getNumeroVara()
								.toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroProcesso() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroProcessoParcelamento(parcelamentoDadosTermo.getNumeroProcesso());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getProfissaoCliente() != null
							&& parcelamentoDadosTermo.getProfissaoCliente().getId() != null){
				FiltroProfissao filtroProfissao = new FiltroProfissao();
				filtroProfissao.adicionarParametro(new ParametroSimples(FiltroProfissao.ID, parcelamentoDadosTermo.getProfissaoCliente()
								.getId()));
				Collection<Profissao> colecaoProfissao = fachada.pesquisar(filtroProfissao, Profissao.class.getName());

				if(colecaoProfissao.size() > 0){
					Profissao profissao = colecaoProfissao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoProfissaoReponsavelParcelamento(profissao.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getEstadoCivilCliente() != null
							&& parcelamentoDadosTermo.getEstadoCivilCliente().getId() != null){
				FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.ID, parcelamentoDadosTermo
								.getEstadoCivilCliente().getId()));
				Collection<EstadoCivil> colecaoEstadoCivil = fachada.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName());

				if(colecaoEstadoCivil.size() > 0){
					EstadoCivil estadoCivil = colecaoEstadoCivil.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEstadoCivilReponsavelParcelamento(estadoCivil
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getIndicadorEnderecoCorrespondenciaCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean
								.setIndicadorEnderecoCorrespondenciaReponsavelParcelamento(parcelamentoDadosTermo
												.getIndicadorEnderecoCorrespondenciaCliente().toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getEnderecoTipoCliente() != null
							&& parcelamentoDadosTermo.getEnderecoTipoCliente().getId() != null){
				FiltroEnderecoTipo filtroEnderecoTipo = new FiltroEnderecoTipo();
				filtroEnderecoTipo.adicionarParametro(new ParametroSimples(FiltroEnderecoTipo.ID, parcelamentoDadosTermo
								.getEnderecoTipoCliente().getId()));
				Collection<EnderecoTipo> colecaoEnderecoTipo = fachada.pesquisar(filtroEnderecoTipo, EnderecoTipo.class.getName());

				if(colecaoEnderecoTipo.size() > 0){
					EnderecoTipo enderecoTipo = colecaoEnderecoTipo.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoTipoEnderecoReponsavelParcelamento(enderecoTipo
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getIndicadorProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorPossuiProcurador(parcelamentoDadosTermo
								.getIndicadorProcurador().toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getOrgaoExpedidorRgCliente() != null
							&& parcelamentoDadosTermo.getOrgaoExpedidorRgCliente().getId() != null){
				FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
				filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, parcelamentoDadosTermo
								.getOrgaoExpedidorRgCliente().getId()));
				Collection<OrgaoExpedidorRg> colecaoOrgaoExpedidorRg = fachada.pesquisar(filtroOrgaoExpedidorRg,
								OrgaoExpedidorRg.class.getName());

				if(colecaoOrgaoExpedidorRg.size() > 0){
					OrgaoExpedidorRg orgaoExpedidorRg = colecaoOrgaoExpedidorRg.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoOrgaoExpedidorReponsavelParcelamento(orgaoExpedidorRg
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getUnidadeFederacaoCliente() != null
							&& parcelamentoDadosTermo.getUnidadeFederacaoCliente().getId() != null){
				FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
				filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, parcelamentoDadosTermo
								.getUnidadeFederacaoCliente().getId()));
				Collection<UnidadeFederacao> colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao,
								UnidadeFederacao.class.getName());

				if(colecaoUnidadeFederacao.size() > 0){
					UnidadeFederacao unidadeFederacao = colecaoUnidadeFederacao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoUnidadeFederacaoReponsavelParcelamento(unidadeFederacao
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeClienteEmpresa() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeEmpresaReponsavelParcelamento(parcelamentoDadosTermo
								.getNomeClienteEmpresa());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa() != null
							&& parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa().getId() != null){
				FiltroRamoAtividade filtroRamoAtividade = new FiltroRamoAtividade();
				filtroRamoAtividade.adicionarParametro(new ParametroSimples(FiltroRamoAtividade.ID, parcelamentoDadosTermo
								.getRamoAtividadeClienteEmpresa().getId()));
				Collection<RamoAtividade> colecaoRamoAtividade = fachada.pesquisar(filtroRamoAtividade, RamoAtividade.class.getName());

				if(colecaoRamoAtividade.size() > 0){
					RamoAtividade ramoAtividade = colecaoRamoAtividade.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoRamoAtividadeEmpresaReponsavelParcelamento(ramoAtividade
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroCnpjClienteEmpresa() != null){
				String cnpjFormatado = parcelamentoDadosTermo.getNumeroCnpjClienteEmpresa();
				String zeros = "";

				for(int a = 0; a < (14 - cnpjFormatado.length()); a++){
					zeros = zeros.concat("0");
				}
				// concatena os zeros ao numero
				// caso o numero seja diferente de nulo
				cnpjFormatado = zeros.concat(cnpjFormatado);
				cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8)
								+ "/" + cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroCnpjEmpresaReponsavelParcelamento(cnpjFormatado);
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getIndicadorEnderecoCorrespondenciaClienteEmpresa() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean
								.setIndicadorEnderecoCorrespondenciaEmpresaReponsavelParcelamento(parcelamentoDadosTermo
												.getIndicadorEnderecoCorrespondenciaClienteEmpresa().toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getDescricaoEnderecoClienteEmpresa() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEnderecoEmpresaReponsavelParcelamento(parcelamentoDadosTermo
								.getDescricaoEnderecoClienteEmpresa());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getDescricaoEnderecoProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEnderecoProcuradorParcelamento(parcelamentoDadosTermo
								.getDescricaoEnderecoProcurador());
			}
			
			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroCpfProcurador() != null){
				String cpfFormatado = parcelamentoDadosTermo.getNumeroCpfProcurador();
				if(cpfFormatado != null && cpfFormatado.length() == 11){

					cpfFormatado = cpfFormatado.substring(0, 3) + "." + cpfFormatado.substring(3, 6) + "." + cpfFormatado.substring(6, 9)
									+ "-" + cpfFormatado.substring(9, 11);
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroCpfProcuradorParcelamento(cpfFormatado);
			}
			
			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeProcuradorParcelamento(parcelamentoDadosTermo.getNomeProcurador());
			}
			
			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroRgProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroRgProcuradorParcelamento(parcelamentoDadosTermo
								.getNumeroRgProcurador());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getOrgaoExpedidorRgProcurador() != null
							&& parcelamentoDadosTermo.getOrgaoExpedidorRgProcurador().getId() != null){
				FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
				filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, parcelamentoDadosTermo
								.getOrgaoExpedidorRgProcurador().getId()));
				Collection<OrgaoExpedidorRg> colecaoOrgaoExpedidorRg = fachada.pesquisar(filtroOrgaoExpedidorRg,
								OrgaoExpedidorRg.class.getName());

				if(colecaoOrgaoExpedidorRg.size() > 0){
					OrgaoExpedidorRg orgaoExpedidorRg = colecaoOrgaoExpedidorRg.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoOrgaoExpedidorProcuradorParcelamento(orgaoExpedidorRg
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getUnidadeFederacaoProcurador() != null
							&& parcelamentoDadosTermo.getUnidadeFederacaoProcurador().getId() != null){
				FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
				filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, parcelamentoDadosTermo
								.getUnidadeFederacaoProcurador().getId()));
				Collection<UnidadeFederacao> colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao,
								UnidadeFederacao.class.getName());

				if(colecaoUnidadeFederacao.size() > 0){
					UnidadeFederacao unidadeFederacao = colecaoUnidadeFederacao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoUnidadeFederacaoProcuradorParcelamento(unidadeFederacao
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getProfissaoProcurador() != null
							&& parcelamentoDadosTermo.getProfissaoProcurador().getId() != null){
				FiltroProfissao filtroProfissao = new FiltroProfissao();
				filtroProfissao.adicionarParametro(new ParametroSimples(FiltroProfissao.ID, parcelamentoDadosTermo.getProfissaoProcurador()
								.getId()));
				Collection<Profissao> colecaoProfissao = fachada.pesquisar(filtroProfissao, Profissao.class.getName());

				if(colecaoProfissao.size() > 0){
					Profissao profissao = colecaoProfissao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoProfissaoProcuradorParcelamento(profissao.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getEstadoCivilProcurador() != null
							&& parcelamentoDadosTermo.getEstadoCivilProcurador().getId() != null){
				FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.ID, parcelamentoDadosTermo
								.getEstadoCivilProcurador().getId()));
				Collection<EstadoCivil> colecaoEstadoCivil = fachada.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName());

				if(colecaoEstadoCivil.size() > 0){
					EstadoCivil estadoCivil = colecaoEstadoCivil.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEstadoCivilProcuradorParcelamento(estadoCivil
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNacionalidadeProcurador() != null
							&& parcelamentoDadosTermo.getNacionalidadeProcurador().getId() != null){
				FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.ID, parcelamentoDadosTermo
								.getNacionalidadeProcurador().getId()));
				Collection<Nacionalidade> colecaoNacionalidadel = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());

				if(colecaoNacionalidadel.size() > 0){
					Nacionalidade nacionalidade = colecaoNacionalidadel.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoNacionalidadeProcuradorParcelamento(nacionalidade
									.getDescricao());
				}
			}

			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoDatasParcelamento(parcelamentoRelatorioHelper
							.getColecaoDatasParcelamento());
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoDatasParcelamentoSobra(parcelamentoRelatorioHelper
							.getColecaoDatasParcelamentoSobra());

			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosParcelamento(parcelamentoRelatorioHelper
							.getColecaoServicosParcelamento());
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosParcelamentoSobra(parcelamentoRelatorioHelper
							.getColecaoServicosParcelamentoSobra());

			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosDebitoACobrar(parcelamentoRelatorioHelper
							.getColecaoServicosDebitoACobrar());
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosDebitoACobrarSobra(parcelamentoRelatorioHelper
							.getColecaoServicosDebitoACobrarSobra());

			if(parcelamentoRelatorioHelper != null && parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia() != null){
				int quantidadeParcelasSucumbencia;

				quantidadeParcelasSucumbencia = parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia().intValue() - 1;
				if(quantidadeParcelasSucumbencia >= 1){
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setQuantidadeParcelasSucumbencia(String
									.valueOf(quantidadeParcelasSucumbencia));
				}else{
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setQuantidadeParcelasSucumbencia("");
				}
			}

			BigDecimal valorTotalSucumbencia = BigDecimal.ZERO;
			if(parcelamentoRelatorioHelper != null && parcelamentoRelatorioHelper.getValorSucumbenciaAtual() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamentoRelatorioHelper.getValorSucumbenciaAtual());
			}

			if(parcelamentoRelatorioHelper != null && parcelamentoRelatorioHelper.getValorAtualizacaoMonetariaSucumbenciaAnterior() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamentoRelatorioHelper.getValorAtualizacaoMonetariaSucumbenciaAnterior());
			}

			if(parcelamentoRelatorioHelper != null && parcelamentoRelatorioHelper.getValorJurosMoraSucumbenciaAnterior() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamentoRelatorioHelper.getValorJurosMoraSucumbenciaAnterior());
			}

			if(parcelamentoRelatorioHelper != null && parcelamentoRelatorioHelper.getValorSucumbenciaAnterior() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamentoRelatorioHelper.getValorSucumbenciaAnterior());
			}

			if(valorTotalSucumbencia != BigDecimal.ZERO && !valorTotalSucumbencia.toString().equals("0.00")){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorTotalSucumbencia(valorTotalSucumbencia.toString());
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorTotalSucumbenciaExtenso(Util.valorExtenso(valorTotalSucumbencia));
			}
			
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroProcessosExecucao(parcelamentoRelatorioHelper.getColecaoProcessosExecucaoFiscal());

			if(parcelamentoRelatorioHelper != null && parcelamentoRelatorioHelper.getIdCobrancaForma() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorCobrancaParcelamento(parcelamentoRelatorioHelper
								.getIdCobrancaForma()
								.toString());
			}
			
			
			String indicadorValorEntradaPorGuia = "";
			FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, parcelamento.getId()));
			Collection<GuiaPagamento> colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());
			if(colecaoGuiaPagamento.size() > 0){
				indicadorValorEntradaPorGuia = "1";

			}else{
				indicadorValorEntradaPorGuia = "2";
			}
			
			
			BigDecimal valorEntradaSucumbencia = BigDecimal.ZERO;
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorValorEntradaGuia(indicadorValorEntradaPorGuia);
			if(indicadorValorEntradaPorGuia.equals("1") && !parcelamentoRelatorioHelper.getValorEntrada().equals(BigDecimal.ZERO)
							&& !parcelamentoRelatorioHelper.getValorEntrada().toString().equals("0.00")
							&& !valorTotalSucumbencia.equals(BigDecimal.ZERO)
							&& !valorTotalSucumbencia.equals("0.00")){
				BigDecimal valorEntradaMenosSucumbencia = parcelamentoRelatorioHelper.getValorEntrada();

				if(parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia() != null
								&& !parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia().equals(BigDecimal.ZERO)
								&& !parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia().toString().equals("0.00")
								&& !parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia().toString().equals("0")){
					valorEntradaMenosSucumbencia = valorEntradaMenosSucumbencia.subtract(valorTotalSucumbencia.divide(new BigDecimal(
									parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia()), 2, RoundingMode.HALF_UP));
					valorEntradaSucumbencia = valorTotalSucumbencia.divide(
									new BigDecimal(parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia()), 2,
									RoundingMode.HALF_UP);
				}else{
					valorEntradaMenosSucumbencia = valorEntradaMenosSucumbencia.subtract(valorTotalSucumbencia);
					valorEntradaSucumbencia = valorTotalSucumbencia;
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbencia(Util
								.formatarMoedaReal(valorEntradaMenosSucumbencia));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbenciaExtenso(Util
								.valorExtenso(valorEntradaMenosSucumbencia.abs()));

			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbencia(valorEntrada);
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbenciaExtenso(valorEntradaExtenso);
			}

			if(parcelamentoRelatorioHelper.getValorEntrada() != null
							&& !parcelamentoRelatorioHelper.getValorEntrada().equals(BigDecimal.ZERO)
							&& !parcelamentoRelatorioHelper.getValorEntrada().toString().equals("0.00")){

				BigDecimal valorPrestacaoEntradaSucumbencia = BigDecimal.ZERO;
				if(parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia() != null
								&& !parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia().equals(Short.valueOf("0"))){
					valorPrestacaoEntradaSucumbencia = valorTotalSucumbencia.divide(
									new BigDecimal(parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia()), 2, RoundingMode.HALF_UP);
					if(valorPrestacaoEntradaSucumbencia.compareTo(parcelamentoRelatorioHelper.getValorEntrada()) == 1){
						valorPrestacaoEntradaSucumbencia = parcelamentoRelatorioHelper.getValorEntrada();
					}
				}else{
					if(parcelamentoRelatorioHelper.getValorEntrada().compareTo(valorTotalSucumbencia) == 1){
						valorPrestacaoEntradaSucumbencia = valorTotalSucumbencia;
					}else{
						valorPrestacaoEntradaSucumbencia = parcelamentoRelatorioHelper.getValorEntrada();
					}
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbencia(Util
								.formatarMoedaReal(valorPrestacaoEntradaSucumbencia));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbenciaExtenso(Util
								.valorExtenso(valorPrestacaoEntradaSucumbencia));
			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbencia(Util.formatarMoedaReal(BigDecimal.ZERO));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbenciaExtenso(Util.valorExtenso(BigDecimal.ZERO));
			}

			
			if(!parcelamentoRelatorioHelper.getIdCobrancaForma().toString()
							.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_DEBITO_A_COBRAR.toString())){

				Calendar calendar = Calendar.getInstance();
				int ano = 0;
				int mes = 0;
				int dia = 0;
				String dataPorExtenso = "";
				
				if(parcelamentoRelatorioHelper.getDataVencimentoPrimeiraGuiaPagamento() != null
								&& !parcelamentoRelatorioHelper.getDataVencimentoPrimeiraGuiaPagamento().equals("")){
					calendar.setTime(parcelamentoRelatorioHelper.getDataVencimentoPrimeiraGuiaPagamento());
					ano = calendar.get(Calendar.YEAR);
					mes = calendar.get(Calendar.MONTH) + 1;
					dia = calendar.get(Calendar.DAY_OF_MONTH);

					dataPorExtenso = dia + " de " + Util.retornaDescricaoMes(mes).toUpperCase() + " de " + ano + "";
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDataVecimentoPrimeiraGuiaPagamento(dataPorExtenso.toString());
				}

				if(parcelamentoRelatorioHelper.getDataVencimentoPrimeiraGuiaPagamentoSucumbencia() != null
								&& !parcelamentoRelatorioHelper.getDataVencimentoPrimeiraGuiaPagamentoSucumbencia().equals("")){
					calendar.setTime(parcelamentoRelatorioHelper.getDataVencimentoPrimeiraGuiaPagamentoSucumbencia());
					ano = calendar.get(Calendar.YEAR);
					mes = calendar.get(Calendar.MONTH) + 1;
					dia = calendar.get(Calendar.DAY_OF_MONTH);

					dataPorExtenso = dia + " de " + Util.retornaDescricaoMes(mes).toUpperCase() + " de " + ano + "";
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDataVecimentoPrimeiraGuiaPagamentoSucumbencia(dataPorExtenso
									.toString());
				}
				
				if(parcelamentoRelatorioHelper.getDataVencimentoSegundaGuiaPagamento() != null
								&& !parcelamentoRelatorioHelper.getDataVencimentoSegundaGuiaPagamento().equals("")){
					calendar.setTime(parcelamentoRelatorioHelper.getDataVencimentoSegundaGuiaPagamento());
					ano = calendar.get(Calendar.YEAR);
					mes = calendar.get(Calendar.MONTH) + 1;
					dia = calendar.get(Calendar.DAY_OF_MONTH);

					dataPorExtenso = dia + " de " + Util.retornaDescricaoMes(mes).toUpperCase() + " de " + ano + "";
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDataVecimentoSegundaGuiaPagamento(dataPorExtenso.toString());
				}
			}

			if(parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia() != null
							&& !parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia().equals(Short.valueOf("0"))){
				valorEntradaSucumbencia = valorTotalSucumbencia.divide(
								new BigDecimal(parcelamentoRelatorioHelper.getNumeroParcelasSucumbencia()), 2,
								RoundingMode.HALF_UP);

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbencia(Util
								.formatarMoedaReal(valorEntradaSucumbencia));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbenciaExtenso(Util
								.valorExtenso(valorEntradaSucumbencia));
			}else{
				if(valorTotalSucumbencia != null && !valorTotalSucumbencia.equals(BigDecimal.ZERO) && !valorTotalSucumbencia.equals("0.00")){
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setQuantidadeParcelasSucumbencia("");

					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbencia(Util
									.formatarMoedaReal(valorTotalSucumbencia));
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbenciaExtenso(Util
									.valorExtenso(valorTotalSucumbencia));

				}

			}

			Map<String, Integer> periodoDebito = fachada.obterPeriodoDebitoParcelmento(parcelamento.getId());

			if(periodoDebito.get("menorReferencia") != null){

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoInicial(Util.formatarAnoMesParaMesAno(String
								.valueOf(periodoDebito.get("menorReferencia"))));

			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoInicial(" - ");
			}

			if(periodoDebito.get("maiorReferencia") != null){

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoFinal(Util.formatarAnoMesParaMesAno(String
								.valueOf(periodoDebito.get("maiorReferencia"))));

			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoFinal(" - ");
			}

			// adiciona o bean a cole��o
			relatorioParcelamentosBeans.add(relatorioParcelamentoResolucaoDiretoriaLayoutBean);

		}

		// retorna o relat�rio gerado
		return relatorioParcelamentosBeans;
	}

	@Override
	public List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> gerarDados(Parcelamento parcelamento,
					Collection<ContaValoresHelper> colecaoContaValores,Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores, 
					Collection<DebitoACobrar> colecaoDebitoACobrar,	Collection<CreditoARealizar> colecaoCreditoARealizar,
					Integer numeroDiasVencimentoEntrada)
					throws GeradorRelatorioParcelamentoException{

		// cole��o de beans do relat�rio
		List relatorioParcelamentosBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioParcelamentoResolucaoDiretoriaLayoutBean relatorioParcelamentoResolucaoDiretoriaLayoutBean = null;

		ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = parcelamento.getParcelamentoTermoTestemunhas();
		ParcelamentoDadosTermo parcelamentoDadosTermo = parcelamento.getParcelamentoDadosTermo();

		// Matricula Imovel
		String matriculaImovel = "";
		String inscricaoEstadual = "";
		if(parcelamento.getImovel() != null && parcelamento.getImovel().getId() != null){
			matriculaImovel = parcelamento.getImovel().getId().toString();
			inscricaoEstadual = fachada.pesquisarInscricaoImovel(parcelamento.getImovel().getId(), true);
		}

		// Nome Cliente
		String nomeCliente = "";
		if(parcelamento.getCliente() != null && parcelamento.getCliente().getNome() != null
						&& !parcelamento.getCliente().getNome().equals("")){

			nomeCliente = parcelamento.getCliente().getNome();
		}
		nomeCliente = nomeCliente.trim();

		// Endereco
		String endereco = "";
		if(parcelamento.getImovel() != null && parcelamento.getImovel().getId() != null){
			endereco = fachada.pesquisarEnderecoComDetalhamento(parcelamento.getImovel().getId());
		}

		// Endere�o abreviado
		String enderecoAbreviadoCliente = fachada.pesquisarEnderecoClienteAbreviado(parcelamento.getCliente().getId(), true);
		if(enderecoAbreviadoCliente != null && !enderecoAbreviadoCliente.equals("")){
			enderecoAbreviadoCliente = enderecoAbreviadoCliente.replace("-", "");
		}

		// Endere�o Abreviado
		String enderecoAbreviado = "";
		if(enderecoAbreviadoCliente != null && parcelamento.getImovel() != null && parcelamento.getImovel().getId() != null){
			enderecoAbreviado = fachada.pesquisarEnderecoClienteAbreviadoComDetalhemento(parcelamento.getImovel().getId());
		}

		// CEP
		String cep = null;
		if(parcelamento.getImovel() != null && parcelamento.getImovel().getId() != null){
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CEP);
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, parcelamento.getImovel().getId()));
			Collection colecaoImovelAux = fachada.pesquisar(filtroImovel, Imovel.class.getName());
			Imovel imovelAux = (Imovel) Util.retonarObjetoDeColecao(colecaoImovelAux);
			cep = imovelAux.getLogradouroCep().getCep().getCepFormatado();
		}

		// Telefone
		Collection colecaoClienteFone = null;
		ClienteFone clienteFone = null;
		String telefone = "";

		if(parcelamento.getCliente() != null && parcelamento.getCliente().getId() != null){
			colecaoClienteFone = fachada.pesquisarClienteFone(parcelamento.getCliente().getId());
		}

		if(colecaoClienteFone != null && !colecaoClienteFone.isEmpty()){
			Iterator colecaoClienteFoneIterator = colecaoClienteFone.iterator();

			while(colecaoClienteFoneIterator.hasNext()){
				clienteFone = (ClienteFone) colecaoClienteFoneIterator.next();

				if(clienteFone.getIndicadorTelefonePadrao() != null
								&& clienteFone.getIndicadorTelefonePadrao().equals(ClienteFone.INDICADOR_FONE_PADRAO)){
					break;
				}
			}

			telefone = clienteFone.getDddTelefone();

		}

		// Cpf ou Cnpj
		String cpfCnpjCliente = "";
		if(parcelamento.getCliente() != null && parcelamento.getCliente().getCpf() != null
						&& !parcelamento.getCliente().getCpf().equals("") && parcelamento.getCliente().getClienteTipo() != null
						&& parcelamento.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica() == 1){

			cpfCnpjCliente = parcelamento.getCliente().getCpfFormatado();
		}else{
			cpfCnpjCliente = parcelamento.getCliente().getCnpjFormatado();
		}

		// RG
		String rgCliente = "";
		if(parcelamento.getCliente() != null && parcelamento.getCliente().getRg() == null || parcelamento.getCliente().getRg().equals("")){
			rgCliente = parcelamento.getCliente().getRg();
		}else{
			rgCliente = parcelamento.getCliente().getRg();
		}

		if(rgCliente == null || rgCliente.equals("0")){
			rgCliente = "";
		}

		// Taxa Juros Mora e Taxa Juros Mora Extenso
		String taxaJurosMora = "";
		String taxaJurosMoraExtenso = "";
		if(parcelamento.getTaxaJuros() != null || !parcelamento.getTaxaJuros().equals("")){

			taxaJurosMora = parcelamento.getTaxaJuros().toString();

			taxaJurosMoraExtenso = Util.numero(new Double(parcelamento.getTaxaJuros().toString().replace(",", ".")).longValue());

		}

		// Total Debitos
		BigDecimal totalDebitosImovel = BigDecimal.ZERO;
		if(parcelamento.getValorConta() != null){// 3
			// Valor das Faturas em Aberto
			totalDebitosImovel = totalDebitosImovel.add(parcelamento.getValorConta());
		}

		if(parcelamento.getValorServicosACobrar() != null){// 4
			// Valor dos Servi�os A Cobrar
			totalDebitosImovel = totalDebitosImovel.add(parcelamento.getValorServicosACobrar());
		}

		if(parcelamento.getValorAtualizacaoMonetaria() != null){// 5
			// Valor das Atualiza��es Monet�rias
			totalDebitosImovel = totalDebitosImovel.add(parcelamento.getValorAtualizacaoMonetaria());
		}

		if(parcelamento.getValorJurosMora() != null){// 6
			// Valor dos Juros/Mora
			totalDebitosImovel = totalDebitosImovel.add(parcelamento.getValorJurosMora());
		}

		if(parcelamento.getValorMulta() != null){// 7
			// Valor das Multas
			totalDebitosImovel = totalDebitosImovel.add(parcelamento.getValorMulta());
		}

		if(parcelamento.getValorGuiaPapagamento() != null){// 8
			// Valor das Guais de Pagamento
			totalDebitosImovel = totalDebitosImovel.add(parcelamento.getValorGuiaPapagamento());
		}

		if(parcelamento.getValorParcelamentosACobrar() != null){// 9
			// Valor do Parcelamento a Cobrar
			totalDebitosImovel = totalDebitosImovel.add(parcelamento.getValorParcelamentosACobrar());
		}

		String totalDebitos = "";
		String totalDebitosExtenso = "";
		if(totalDebitosImovel != null){
			totalDebitosExtenso = Util.valorExtenso(totalDebitosImovel);
			totalDebitos = Util.formatarMoedaReal(totalDebitosImovel);
		}

		// Total Descontos
		BigDecimal totalDescontosImovel = BigDecimal.ZERO;

		if(parcelamento.getValorDescontoAcrescimos() != null){// 10
			// Valor dos Descontos de Acr�scimo
			totalDescontosImovel = totalDescontosImovel.add(parcelamento.getValorDescontoAcrescimos());
		}

		if(parcelamento.getValorDescontoAntiguidade() != null){// 11
			// Valor dos Descontos de Antiguidade
			totalDescontosImovel = totalDescontosImovel.add(parcelamento.getValorDescontoAntiguidade());
		}

		if(parcelamento.getValorDescontoInatividade() != null){// 12
			// Valor dos Descontos de Inatividade
			totalDescontosImovel = totalDescontosImovel.add(parcelamento.getValorDescontoInatividade());
		}

		if(parcelamento.getValorCreditoARealizar() != null){// 13
			// Valor dos Cr�ditos a Realizar
			totalDescontosImovel = totalDescontosImovel.add(parcelamento.getValorCreditoARealizar());
		}

		if(parcelamento.getValorDescontoSancao() != null){// 24
			// Valor Desconto de San��es Regulamentares
			totalDescontosImovel = totalDescontosImovel.add(parcelamento.getValorDescontoSancao());
		}

		if(parcelamento.getValorDescontoTarifaSocial() != null){// 25
			// Valor Desconto de San��es Regulamentares
			totalDescontosImovel = totalDescontosImovel.add(parcelamento.getValorDescontoTarifaSocial());

		}

		String totalDescontos = "";
		if(totalDescontosImovel != null){
			totalDescontos = Util.formatarMoedaReal(totalDescontosImovel);
		}

		// Valor Entrada e Valor Entrada Extenso
		String valorEntrada = "";
		String valorEntradaExtenso = "";
		if(parcelamento.getValorEntrada() != null){
			valorEntradaExtenso = Util.valorExtenso(parcelamento.getValorEntrada());
			valorEntrada = Util.formatarMoedaReal(parcelamento.getValorEntrada());
		}

		// Numero Parcelas e Numero Parcelas Extenso
		String numeroParcelas = "";
		String numeroParcelasExtenso = "";
		if(parcelamento.getNumeroPrestacoes() != null){
			numeroParcelas = parcelamento.getNumeroPrestacoes().toString();
			numeroParcelasExtenso = Util.numero(new Double(parcelamento.getNumeroPrestacoes()).longValue());
		}

		// Valor Parcela e Valor Parcela Extenso
		String valorParcela = "";
		String valorParcelaExtenso = "";
		if(parcelamento.getValorPrestacao() != null){
			valorParcelaExtenso = Util.valorExtenso(parcelamento.getValorPrestacao());
			valorParcela = Util.formatarMoedaReal(parcelamento.getValorPrestacao());
		}

		// Descricao Localidade
		String descricaoLocalidade = "";
		if(parcelamento.getLocalidade() != null && parcelamento.getLocalidade().getId() != null){
			Localidade localidade = fachada.pesquisarLocalidadeDigitada(parcelamento.getLocalidade().getId());
			descricaoLocalidade = localidade.getDescricao().trim();
		}

		
		String indicadorValorEntradaPorGuia="1";
		Collection<ContaHistorico> colecaoAnoMesReferenciasContaHistorico = new ArrayList<ContaHistorico>();
		for(ContaValoresHelper conta : colecaoContaValores){
			if(conta.getIndicadorContasDebito() != null && conta.getIndicadorContasDebito().equals(Integer.valueOf(1))){
				indicadorValorEntradaPorGuia = "2";
			}

			ContaHistorico contaHistorico = new ContaHistorico();
			contaHistorico.setAnoMesReferenciaConta(conta.getConta().getAnoMesReferenciaConta());
			colecaoAnoMesReferenciasContaHistorico.add(contaHistorico);
		}

		String colecaoAnoMesReferencia = "";
		String colecaoAnoMesReferenciaSobra = "";
		String inicioPeriodoFornecimento = "";
		String fimPeriodoFornecimento = "";
		int contadorConta = 0;
		int primeiroConta = 0;
		int contadorContaSobra = 0;

		// Para todas as contas parceladas
		if(!colecaoAnoMesReferenciasContaHistorico.isEmpty()){

			// ordenar colecao de forma decrescente
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator(FiltroContaHistorico.ANO_MES_REFERENCIA));
			ComparatorChain multiSort = new ComparatorChain(sortFields);
			multiSort.setReverseSort(0);
			Collections.sort((List) colecaoAnoMesReferenciasContaHistorico, multiSort);

			for(ContaHistorico contaHistorico : colecaoAnoMesReferenciasContaHistorico){
				contadorConta++;
				primeiroConta++;
				Integer anoMes = contaHistorico.getAnoMesReferenciaConta();

				// AnoMes Inicial do Fornecimento
				inicioPeriodoFornecimento = Util.formatarAnoMesParaMesAno(anoMes);

				// Caso tenha mais de 60 ano mes reg, adiciona nessa lista auxiliar se tiver menos,
				// adiciona na lista principal.
				if(contadorConta <= 60){

					// Se tem mais elementos, adiciona a virgula
					if(primeiroConta == 1){
						colecaoAnoMesReferencia = Util.formatarAnoMesParaMesAno(anoMes);
						// AnoMes final do Fornecimento
						fimPeriodoFornecimento = Util.formatarAnoMesParaMesAno(anoMes);
					}else{
						colecaoAnoMesReferencia += ", " + Util.formatarAnoMesParaMesAno(anoMes);
					}
				}else{
					contadorContaSobra++;
				}
			}

			// Se houver mais de 60 ano mes ref, adicionar esse coment�rio no final do relat�rio com
			// o somat�rio da sobra.
			if(contadorConta > 60){
				colecaoAnoMesReferenciaSobra = "e mais " + contadorContaSobra + " d�bitos.";
			}
		}

		List<String> colecaoServicosPrestados = new ArrayList<String>();
		List<Date> colecaoDatasParcelamento = new ArrayList<Date>();

		String detalhamentoGuiasPrestacoes = "";
		String detalhamentoGuiasPrestacoesSobra = "";
		int contadorGuia = 0;
		int contadorGuiaSobra = 0;

		// Para todas as Guias parceladas
		if(colecaoGuiaPagamentoValores != null && colecaoGuiaPagamentoValores.size() > 0){

			// ordenar colecao de forma decrescente
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("idGuiaPagamento"));
			sortFields.add(new BeanComparator("numeroPrestacao"));
			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort((List) colecaoGuiaPagamentoValores, Collections.reverseOrder(multiSort));

			for(GuiaPagamentoValoresHelper guiaPagamentoValor : colecaoGuiaPagamentoValores){
				contadorGuia++;
				String identificadorPrestacao = guiaPagamentoValor.getIdGuiaPagamento().toString() + "/"
								+ guiaPagamentoValor.getNumeroPrestacao().toString();

				// Caso tenha mais de 60 presta��es, n�o exibir
				if(contadorGuia <= 60){

					// Se tem mais elementos, adiciona a virgula
					if(contadorGuia == 1){
						detalhamentoGuiasPrestacoes = identificadorPrestacao;
					}else{
						detalhamentoGuiasPrestacoes += ", " + identificadorPrestacao;
					}
				}else{
					contadorGuiaSobra++;
				}
				
				// Consula a Guia para saber se tem Parcelamento
				FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
				filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
								guiaPagamentoValor.getIdGuiaPagamento()));
				filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
								guiaPagamentoValor.getNumeroPrestacao()));
				filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);
				Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(filtroGuiaPagamentoPrestacao,
								GuiaPagamentoPrestacao.class.getName());

				for(GuiaPagamentoPrestacao guiaPagamentoPrestacao : colecaoGuiaPagamentoPrestacao){
					if(!colecaoServicosPrestados.contains(guiaPagamentoPrestacao.getDebitoTipo().getDescricao())){
						colecaoServicosPrestados.add(guiaPagamentoPrestacao.getDebitoTipo().getDescricao());
					}
				}

			}

			// Se houver mais de 60 presta��es de guia
			if(contadorGuia > 60){
				detalhamentoGuiasPrestacoesSobra = " e mais " + contadorGuiaSobra + "presta��es de guias de pagamento.";
			}			
		}

		// Para os debitos a cobar parcelamento
		String colecaoDebitosACobrar = "";
		String colecaoDebitosACobrarSobra = "";
		int contadorDebitoACobrar = 0;
		int contadorDebitoACobrarSobra = 0;
		int primeiroDebitoACobrar = 0;

		if(colecaoDebitoACobrar != null){

			// ordenar colecao de forma decrescente
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("debitoTipo.descricao"));
			ComparatorChain multiSort = new ComparatorChain(sortFields);
			multiSort.setReverseSort(0);
			Collections.sort((List) colecaoDebitoACobrar, multiSort);

			// DebitoACobrar colecaoDebitoACobrar

			for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){
				contadorDebitoACobrar++;
				primeiroDebitoACobrar++;
				String descricaoServico = debitoACobrar.getDebitoTipo().getDescricao();

				// Caso tenha mais de 60 ano mes reg, adiciona nessa lista auxiliar se tiver menos,
				// adiciona na lista principal.
				if(contadorDebitoACobrar <= 60){

					// Se tem mais elementos, adiciona a virgula
					if(primeiroDebitoACobrar == 1){
						colecaoDebitosACobrar = descricaoServico;
					}else{
						colecaoDebitosACobrar += ", " + descricaoServico;
					}
				}else{
					contadorDebitoACobrarSobra++;
				}
				
				// Consula a Guia para saber se tem Parcelamento
				if(!colecaoServicosPrestados.contains(debitoACobrar.getDebitoTipo().getDescricao())){
					colecaoServicosPrestados.add(debitoACobrar.getDebitoTipo().getDescricao());
				}
			}

			// Se houver mais de 60 ano mes ref, adicionar esse coment�rio no final do relat�rio com
			// o somat�rio da sobra.
			if(contadorDebitoACobrar > 60){
				colecaoDebitosACobrarSobra = "e mais " + contadorDebitoACobrarSobra + " d�bitos.";
			}

		}

		// Listas de Servicos
		String stringColecaoServicosParcelamento = "";
		String stringColecaoServicosParcelamentoSobra = "";
		if(colecaoServicosPrestados != null && colecaoServicosPrestados.size() > 0){
			// ordenar colecao de forma decrescente
			Collections.sort(colecaoServicosPrestados);

			// DebitoACobrar colecaoDebitoACobrar
			Integer contadorServicosPrestados = 0;
			Integer primeiroServicosPrestados = 0;
			Integer contadorServicosPrestadosSobra = 0;

			for(String servicosPrestados : colecaoServicosPrestados){
				contadorServicosPrestados++;
				primeiroServicosPrestados++;

				// Caso tenha mais de 60 ano mes reg, adiciona nessa lista auxiliar se tiver menos,
				// adiciona na lista principal.
				if(contadorServicosPrestados <= 60){

					// Se tem mais elementos, adiciona a virgula
					if(primeiroServicosPrestados == 1){
						stringColecaoServicosParcelamento = servicosPrestados;
					}else{
						stringColecaoServicosParcelamento += ", " + servicosPrestados;
					}
				}else{
					contadorServicosPrestadosSobra++;
				}

			}

			// Se houver mais de 60 ano mes ref, adicionar esse coment�rio no final do relat�rio com
			// o somat�rio da sobra.
			if(contadorServicosPrestadosSobra > 60){
				stringColecaoServicosParcelamentoSobra = "e mais " + contadorServicosPrestadosSobra + " d�bitos.";
			}

		}

		String stringColecaoDatasParcelamento = "";
		String stringColecaoDatasParcelamentoSobra = "";
		if(colecaoDatasParcelamento != null && colecaoDatasParcelamento.size() > 0){
			// ordenar colecao de forma decrescente
			Collections.sort(colecaoDatasParcelamento);

			// DebitoACobrar colecaoDebitoACobrar
			Integer contadorDatasParcelamento = 0;
			Integer primeiroDatasParcelamento = 0;
			Integer contadorDatasParcelamentoSobra = 0;

			for(Date datasParcelamento : colecaoDatasParcelamento){
				contadorDatasParcelamento++;
				primeiroDatasParcelamento++;

				// Caso tenha mais de 60 ano mes reg, adiciona nessa lista auxiliar se tiver menos,
				// adiciona na lista principal.
				if(contadorDatasParcelamento <= 60){

					// Se tem mais elementos, adiciona a virgula
					if(primeiroDatasParcelamento == 1){
						stringColecaoDatasParcelamento = Util.formatarData(datasParcelamento);
					}else{
						stringColecaoDatasParcelamento += ", " + Util.formatarData(datasParcelamento);
					}
				}else{
					contadorDatasParcelamentoSobra++;
				}

			}

			// Se houver mais de 60 ano mes ref, adicionar esse coment�rio no final do relat�rio com
			// o somat�rio da sobra.
			if(primeiroDatasParcelamento > 60){
				stringColecaoDatasParcelamentoSobra = "e mais " + contadorDatasParcelamentoSobra + " d�bitos.";
			}

		}

		// Imovel Dia Vencimento
		String imovelDiaVencimento = "";
		if(parcelamento.getImovel() != null && parcelamento.getImovel().getDiaVencimento() != null){
			imovelDiaVencimento = parcelamento.getImovel().getDiaVencimento().toString();
		}

		// Valor Multas
		String valorMultas = "";
		if(parcelamento.getValorMulta() != null){
			valorMultas = Util.formatarMoedaReal(parcelamento.getValorMulta());
		}

		// Valor Juros Mora
		String valorJurosMora = "";
		if(parcelamento.getValorJurosMora() != null){
			valorJurosMora = Util.formatarMoedaReal(parcelamento.getValorJurosMora());
		}

		// Valor Juros Parcelamento
		String valorJurosParcelamento = "";
		if(parcelamento.getValorJurosParcelamento() != null){
			valorJurosParcelamento = Util.formatarMoedaReal(parcelamento.getValorJurosParcelamento());
		}

		// Indicador de Pessoa F�sica ou Jur�dica
		String indicadorPessoaFisicaJuridica = "";
		if(parcelamento.getCliente() != null && parcelamento.getCliente().getClienteTipo() != null
						&& parcelamento.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica() != null){
			indicadorPessoaFisicaJuridica = parcelamento.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica().toString();
		}

		// Total de contas
		String numeroContas = String.valueOf(contadorConta + contadorContaSobra);

		String taxaMulta = "0";
		String taxaMultaExtenso = "";

		Parcelamento parcelamentoAux = null;
		parcelamentoAux = parcelamento;

		if(parcelamentoAux != null){
			FiltroIndicesAcrescimosImpontualidade filtroIndicesAcrescimosImpontualidade = new FiltroIndicesAcrescimosImpontualidade();
			filtroIndicesAcrescimosImpontualidade.adicionarParametro(new ParametroSimples(
							FiltroIndicesAcrescimosImpontualidade.ANO_MES_REFERENCIA, parcelamentoAux.getAnoMesReferenciaFaturamento()));
			Collection<IndicesAcrescimosImpontualidade> colecaoIndicesAcrescimosImpontualidade = fachada.pesquisar(
							filtroIndicesAcrescimosImpontualidade, IndicesAcrescimosImpontualidade.class.getName());
			IndicesAcrescimosImpontualidade indicesAcrescimosImpontualidade = null;
			if(colecaoIndicesAcrescimosImpontualidade != null && !colecaoIndicesAcrescimosImpontualidade.isEmpty()){
				indicesAcrescimosImpontualidade = colecaoIndicesAcrescimosImpontualidade.iterator().next();
			}
			if(indicesAcrescimosImpontualidade != null){
				if(indicesAcrescimosImpontualidade.getPercentualMulta() != null){
					taxaMulta = indicesAcrescimosImpontualidade.getPercentualMulta().toString();
					taxaMultaExtenso = Util.numero(new Double(taxaMulta.replace(",", ".")).longValue());
				}
			}
		}

		// se a cole��o de par�metros da analise n�o for vazia
		if(parcelamento != null){

			// Pega a Data Atual formatada da seguinte forma: dd de m�s(por extenso) de aaaa
			// Ex: 23 de maio de 1985
			String idImovel = parcelamento.getImovel().getId().toString();
			String idCliente = parcelamento.getCliente().getId().toString();

			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, idCliente));

			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_PROFISSAO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RELACAO_TIPO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RG_ORGAO_EXPEDIDOR);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_UNIDADE_FEDERACAO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.LOGRADOURO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CEP);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.LOCALIDADE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.SETOR_COMERCIAL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.QUADRA);

			Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			String tipoCliente = "";
			String profissaoCliente = "";
			String cidadeImovelCadastrado = "";

			for(Iterator iterator = colecaoClienteImovel.iterator(); iterator.hasNext();){
				ClienteImovel clienteImovel = (ClienteImovel) iterator.next();
				if(clienteImovel.getClienteRelacaoTipo() != null
								&& !clienteImovel.getClienteRelacaoTipo().getId().toString().trim().equals("")){
					tipoCliente = clienteImovel.getClienteRelacaoTipo().getId().toString();
				}

				if(clienteImovel.getCliente().getProfissao() != null){
					profissaoCliente = clienteImovel.getCliente().getProfissao().getDescricao();
				}

				if(clienteImovel.getImovel().getLogradouroCep() != null){
					cidadeImovelCadastrado = clienteImovel.getImovel().getLogradouroCep().getCep().getMunicipio();
				}

			}

			String funcionario = "";
			String cpfUsuarioLogado = "";
			String nomeFuncionario = "";
			if(parcelamento.getUsuario() != null){
				FiltroUsuario filtoUsuario = new FiltroUsuario();
				filtoUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, parcelamento.getUsuario().getId()));
				filtoUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.FUNCIONARIO);
				Collection colecaoUsuario = fachada.pesquisar(filtoUsuario, Usuario.class.getName());
				Usuario usuario = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuario);
				if(usuario != null && usuario.getFuncionario() != null){
					nomeFuncionario = usuario.getFuncionario().getNome();
					String matriculaFuncionario = usuario.getFuncionario().getId().toString();
					cpfUsuarioLogado = Util.formatarCpf(usuario.getCpf());
					funcionario = matriculaFuncionario + "-" + nomeFuncionario;
				}else{
					nomeFuncionario = usuario.getNomeUsuario();
					String matriculaFuncionario = usuario.getId().toString();
					cpfUsuarioLogado = matriculaFuncionario;
					funcionario = matriculaFuncionario + "-" + nomeFuncionario;
				}
			}

			String intervaloPeriodoDebito = "";
			if(parcelamento.getResolucaoDiretoria() != null && parcelamento.getResolucaoDiretoria().getId() != null){
				Collection collecaoParcelamentoSituacaoEspecial = fachada.verificarRDComRestricao(parcelamento.getResolucaoDiretoria()
								.getId());
				if(collecaoParcelamentoSituacaoEspecial != null && !collecaoParcelamentoSituacaoEspecial.isEmpty()){
					ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) Util
									.retonarObjetoDeColecao(collecaoParcelamentoSituacaoEspecial);
					intervaloPeriodoDebito = parcelamentoSituacaoEspecial.getFormatarAnoMesParaMesAnoReferenciaInicio() + " - "
									+ parcelamentoSituacaoEspecial.getFormatarAnoMesParaMesAnoReferenciaFim();
				}
			}

			String nomeEmpresa = "";
			String nomeEmpresaAbreviado = "";
			String enderecoEmpresa = "";
			String cnpjEmpresa = "";
			String cepEmpresa = "";
			String emailEmpresa = "";
			String dataParcelamento = "";

			SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

			if(sistemaParametros != null){

				nomeEmpresa = sistemaParametros.getNomeEmpresa();
				nomeEmpresaAbreviado = sistemaParametros.getNomeAbreviadoEmpresa();
				cnpjEmpresa = Util.formatarCnpj(sistemaParametros.getCnpjEmpresa());
				cepEmpresa = sistemaParametros.getCep().getCepFormatado();
				emailEmpresa = sistemaParametros.getDescricaoEmail();
			}

			if(sistemaParametros.getLogradouro() != null){

				FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
				filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, sistemaParametros.getLogradouro().getId()));
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio");
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio.unidadeFederacao");
				Collection colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

				Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

				String bairro = sistemaParametros.getBairro().getNome();
				String numero = sistemaParametros.getNumeroImovel();

				if(logradouro != null){

					String descricaoLogradouroTipo = "";

					if(!Util.isVazioOuBranco(logradouro.getLogradouroTipo())
									&& !Util.isVazioOuBranco(logradouro.getLogradouroTipo().getDescricao())){
						descricaoLogradouroTipo = logradouro.getLogradouroTipo().getDescricao();
					}

					String descricaoLogradouroTitulo = "";

					if(!Util.isVazioOuBranco(logradouro.getLogradouroTitulo())
									&& !Util.isVazioOuBranco(logradouro.getLogradouroTitulo().getDescricao())){
						descricaoLogradouroTitulo = logradouro.getLogradouroTitulo().getDescricao();
					}

					String descricaoLogradouro = "";

					if(!Util.isVazioOuBranco(logradouro.getNome())){
						descricaoLogradouro = logradouro.getNome();
					}

					String descricaoLogradouroMunicipio = "";

					if(!Util.isVazioOuBranco(logradouro.getMunicipio()) && !Util.isVazioOuBranco(logradouro.getMunicipio().getNome())){
						descricaoLogradouroMunicipio = logradouro.getMunicipio().getNome();
					}

					if(!Util.isVazioOuBranco(logradouro.getMunicipio().getUnidadeFederacao())
									&& !Util.isVazioOuBranco(logradouro.getMunicipio().getUnidadeFederacao().getSigla())){
						descricaoLogradouroMunicipio = descricaoLogradouroMunicipio + " - "
										+ logradouro.getMunicipio().getUnidadeFederacao().getSigla();
					}

					enderecoEmpresa = descricaoLogradouroTipo + " " + descricaoLogradouroTitulo + " " + descricaoLogradouro + ", " + numero
									+ " - " + bairro + " - " + descricaoLogradouroMunicipio;

					Date dataAtual = new Date();

					String dado = "";
					String P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO;
					try{
						P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO = ParametroCobranca.P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO
										.executar();
					}catch(ControladorException e){
						throw new GeradorRelatorioParcelamentoException(e.getMessage());
					}
					if(P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO.equals(ConstantesSistema.ATIVO)){
						dado = Util.recuperaDiaMesAnoDaData(parcelamento.getDataEntradaParcelamento());

					}else{
						dado = Util.recuperaDiaMesAnoDaData(dataAtual);

					}
					String dia = dado.substring(0, 2);
					String mes = Util.retornaDescricaoMes(Integer.parseInt(dado.substring(2, 4)));
					String ano = dado.substring(4, 8);

					dataParcelamento = descricaoLogradouroMunicipio + ", " + dia + " de " + mes + " de " + ano + ".";

				}
			}

			String nomeRepresentante = "";
			String cargoRepresentante = "";
			String dataNomeacaoRepresentante = "";
			String matriculaRepresentante = "";
			String nomeSignatario = "";
			String cargoSignatario = "";
			String dataNomeacaoSignatario = "";
			String matriculaSignatario = "";
			String siteEmpresa = "";
			String nomeTestemunha1 = "";
			String cpfTestemunha1 = "";
			String nomeTestemunha2 = "";
			String cpfTestemunha2 = "";
			String juntaComercialEmpresa = "";
			String inscricaoEstadualEmpresa = "";

			try{
				nomeRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_NOME_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				cargoRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_CARGO_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				dataNomeacaoRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_DATA_NOMEACAO_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				matriculaRepresentante = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_MATRICULA_REPRESENTANTE_TERMO_PARCELAMENTO")).getValor());

				nomeSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_NOME_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				cargoSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_CARGO_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				dataNomeacaoSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_DATA_NOMEACAO_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				matriculaSignatario = String.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_MATRICULA_SIGNATARIO_TERMO_PARCELAMENTO")).getValor());

				siteEmpresa = String
								.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema("P_SITE_EMPRESA")).getValor());

				try{
					juntaComercialEmpresa = (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA
									.getCodigo());
					if(juntaComercialEmpresa != null && !juntaComercialEmpresa.equals("")
									&& !juntaComercialEmpresa.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
						juntaComercialEmpresa = Util.formatarJuntaComercial(juntaComercialEmpresa);
					}else{
						juntaComercialEmpresa = null;
					}
				}catch(NegocioException e1){
					e1.printStackTrace();
					throw new TarefaException("Erro ao gerar dados para o relatorio");
				}

				try{
					inscricaoEstadualEmpresa = Util.formatarInscricaoEstadual((String) fachada
									.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
				}catch(NegocioException e1){
					e1.printStackTrace();
					throw new TarefaException("Erro ao gerar dados para o relatorio");
				}

			}catch(PCGException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			String inicioCobranca = "";
			if(parcelamento.getDataEntradaParcelamento() != null){

				inicioCobranca = Util.formataMesAno(parcelamento.getDataEntradaParcelamento());

			}

			String terminioCobranca = "";
			if(inicioCobranca != null && !inicioCobranca.equalsIgnoreCase("") && numeroParcelas != null
							&& !numeroParcelas.equalsIgnoreCase("")){


				terminioCobranca = Util.somaMesMesAnoComBarra(inicioCobranca, Integer.parseInt(numeroParcelas)).toString();
			}

			String descOrgaoExpRgCliente = "";
			if(parcelamento.getCliente() != null && parcelamento.getCliente().getOrgaoExpedidorRg() != null
							&& parcelamento.getCliente().getOrgaoExpedidorRg().getDescricao() != null){
				descOrgaoExpRgCliente = parcelamento.getCliente().getOrgaoExpedidorRg().getDescricao();

			}

			if(parcelamentoTermoTestemunhas != null){
				nomeTestemunha1 = parcelamentoTermoTestemunhas.getNomeTestemunha1();
				cpfTestemunha1 = Util.formatarCpf(parcelamentoTermoTestemunhas.getCpfTestemunha1());
				nomeTestemunha2 = parcelamentoTermoTestemunhas.getNomeTestemunha2();
				cpfTestemunha2 = Util.formatarCpf(parcelamentoTermoTestemunhas.getCpfTestemunha2());
			}

			// Total negociado
			BigDecimal totalNegociadoParcelamento = null;
			if(parcelamento.getValorEntrada() != null && parcelamento.getNumeroPrestacoes() != null
							&& parcelamento.getValorPrestacao() != null){

				// Total Negociado = vlParcela * nuParcelas + vlEntrada
				totalNegociadoParcelamento = parcelamento.getValorPrestacao()
								.multiply(BigDecimal.valueOf(parcelamento.getNumeroPrestacoes())).add(parcelamento.getValorEntrada());

			}

			String totalNegociado = "";
			String totalNegociadoExtenso = "";
			if(totalNegociadoParcelamento != null){
				totalNegociado = Util.formatarMoedaReal(totalNegociadoParcelamento);
				totalNegociadoExtenso = Util.valorExtenso(totalNegociadoParcelamento);
			}

			String valorParcelado = "";
			String valorParceladoExtenso = "";
			if(totalDebitosImovel != null && parcelamento.getValorEntrada() != null){

				BigDecimal valorSucubenciaEntrada = BigDecimal.ZERO;
				if(parcelamento.getValorSucumbenciaAtualEP() != null){
					valorSucubenciaEntrada = parcelamento.getValorSucumbenciaAtualEP();
				}

				valorParcelado = Util.formatarMoedaReal(totalDebitosImovel.subtract(parcelamento.getValorEntrada()).add(
								valorSucubenciaEntrada));
				valorParceladoExtenso = Util.valorExtenso(totalDebitosImovel.subtract(parcelamento.getValorEntrada()).add(
								valorSucubenciaEntrada));
			}

			relatorioParcelamentoResolucaoDiretoriaLayoutBean = new RelatorioParcelamentoResolucaoDiretoriaLayoutBean(

			// Matr�cula do Im�vel
							matriculaImovel,

							// Nome Cliente
							nomeCliente,

							// Endere�o
							endereco,

							// CPF/CNPJ
							cpfCnpjCliente,

							// Taxa Juros
							taxaJurosMora,

							// Taxa Juros por Extenso
							// Util.numero(Long.parseLong(parcelamentoRelatorioHelper.getTaxaJuros())),
							taxaJurosMoraExtenso,

							// Taxa Multa
							taxaMulta,

							// Taxa Multa por Extenso
							taxaMultaExtenso,

							// Total D�bitos
							totalDebitos,

							// Total D�bitos Extenso
							totalDebitosExtenso,

							// Total Descontos
							totalDescontos,

							// Valor Entrada
							valorEntrada,

							// N�mero de Parcelas
							numeroParcelas,

							// N�mero de Parcelas Extenso
							numeroParcelasExtenso,

							// Valor da Parcela
							valorParcela,

							// Descricao Localidade
							descricaoLocalidade,

							// Colecao ano mes ref
							colecaoAnoMesReferencia,

							// Colecao ano mes ref sobra
							colecaoAnoMesReferenciaSobra,

							// Detalhamento Guias Prestacoes
							detalhamentoGuiasPrestacoes,

							// Detalhamento Guias Prestacoes Sobra
							detalhamentoGuiasPrestacoesSobra,

							// Imovel Dia Vencimento
							imovelDiaVencimento,

							// Tipo Cliente
							tipoCliente,

							// RG
							rgCliente,

							// Valor Multas
							valorMultas,

							// Valor Juros/Mora
							valorJurosMora,

							// Valor Juros/Parcelamento
							valorJurosParcelamento,
							// Inscri��o
							inscricaoEstadual,
							// Funcionario
							funcionario,
							// Intervalo Periodo Debito
							intervaloPeriodoDebito,

							// Telefone
							telefone,

							// Valor Parcela Extenso
							valorParcelaExtenso,

							// Valor Entrada Extenso
							valorEntradaExtenso,

							// CEP
							cep,

							// Inicio do Periodo de Fornecimento
							inicioPeriodoFornecimento,

							// Fim do Periodo de Fornecimento
							fimPeriodoFornecimento,

							// Numero de Contas
							numeroContas,

							// Endere�o abreviado
							enderecoAbreviado,
							// Nome Empresa
							nomeEmpresa,
							// Nome Empresa Abreviado
							nomeEmpresaAbreviado,
							// Endereco Empresa
							enderecoEmpresa,
							// Cnpj Empresa
							cnpjEmpresa,
							// Nome Representante
							nomeRepresentante,
							// Data nomeacao Representante
							dataNomeacaoRepresentante,
							// Matricula Representante
							matriculaRepresentante,
							// Nome Signatario
							nomeSignatario,
							// Data Nomeacao Signatario
							dataNomeacaoSignatario,
							// Matricula Signatario
							matriculaSignatario,
							// Cep empresa
							cepEmpresa,
							// Emeail Empresa
							emailEmpresa,
							// Site Empresa
							siteEmpresa,
							// Cargo Representante
							cargoRepresentante,
							// Cargo Signatario
							cargoSignatario,
							// Nome Funcionario
							nomeFuncionario,
							// Cpf Usuario Logado
							cpfUsuarioLogado,
							// Inicio Cobran�a
							inicioCobranca,
							// Terminio Cobran�a
							terminioCobranca,
							// Descri��o Org�o expedidor
							descOrgaoExpRgCliente,
							// Cidade e Data Atual
							dataParcelamento,
							// Indicador de Pessoa F�sica ou Jur�dica
							indicadorPessoaFisicaJuridica,
							// Nome da Testemunha 1
							nomeTestemunha1,
							// CPF da Testemunha 1
							cpfTestemunha1,
							// Nome da Testemunha 2
							nomeTestemunha2,
							// CPF da Testemunha 2
							cpfTestemunha2,
							// Profiss�o Cliente
							profissaoCliente,
							// Junta Comercial Empresa - JUCER
							juntaComercialEmpresa,
							// Inscricao Estadual Empresa
							inscricaoEstadualEmpresa,
							// Cidade do Im�vel
							cidadeImovelCadastrado, totalNegociado, totalNegociadoExtenso, valorParcelado, valorParceladoExtenso);

			if(parcelamento.getImovel() != null && parcelamento.getImovel().getId() != null){

				Cliente cliente = fachada.retornarDadosClienteProprietario(parcelamento.getImovel().getId());

				if(cliente != null){

					if(cliente.getNome() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeProprietarioImovel(cliente.getNome().trim());
					}

					if(cliente.getCpf() != null && cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() == 1){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setCpfProprietarioImovel(cliente.getCpfFormatado().trim());

					}else if(cliente.getCnpjFormatado() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setCpfProprietarioImovel(cliente.getCnpjFormatado().trim());
					}

					if(cliente.getRg() != null){
						String rgProprietarioImovel = cliente.getRg().trim();
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setRgProprietarioImovel(rgProprietarioImovel);
					}

					if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorPessoaFisicaJuridicaProprietarioImovel(cliente
										.getClienteTipo().getIndicadorPessoaFisicaJuridica().toString());
					}
					
					if(cliente.getNacionalidade() != null && cliente.getNacionalidade().getDescricao() != null){
						relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNacionaliadeProprietarioImovel(cliente.getNacionalidade()
										.getDescricao().trim());
					}

					FiltroClienteEndereco filtroImovelEndereco = new FiltroClienteEndereco();
					filtroImovelEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, cliente.getId()));

					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTipo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTitulo");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio");
					filtroImovelEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");

					Collection<ClienteEndereco> colecaoImovelEndereco = fachada.pesquisar(filtroImovelEndereco,
									ClienteEndereco.class.getName());
					for(ClienteEndereco imovelEndereco : colecaoImovelEndereco){
						if(imovelEndereco.getIndicadorEnderecoCorrespondencia().intValue() == 1){
							relatorioParcelamentoResolucaoDiretoriaLayoutBean.setEnderecoProprietarioImovel(imovelEndereco
											.getEnderecoFormatadoSemCep());
						}
					}
				}

				Categoria categoria = fachada.obterPrincipalCategoriaImovel(parcelamento.getImovel().getId());
				if (categoria != null) {
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoCategoriaImovel(categoria.getDescricao());
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setEnderecoImovel(endereco);
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setInscricaoImovel(parcelamento.getImovel().getInscricaoFormatada());

			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeReponsavelParcelamento(parcelamentoDadosTermo.getNomeCliente());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNacionalidadeCliente() != null
							&& parcelamentoDadosTermo.getNacionalidadeCliente().getId() != null){
				FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.ID, parcelamentoDadosTermo
								.getNacionalidadeCliente().getId()));

				Collection<Nacionalidade> nacionalidade = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoNacionalidadeReponsavelParcelamento(nacionalidade.iterator()
								.next().getDescricao());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroRgCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroRgReponsavelParcelamento(parcelamentoDadosTermo
								.getNumeroRgCliente());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroCpfCliente() != null){
				String cpfFormatado = parcelamentoDadosTermo.getNumeroCpfCliente();
				if(cpfFormatado != null && cpfFormatado.length() == 11){

					cpfFormatado = cpfFormatado.substring(0, 3) + "." + cpfFormatado.substring(3, 6) + "." + cpfFormatado.substring(6, 9)
									+ "-" + cpfFormatado.substring(9, 11);
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroCpfReponsavelParcelamento(cpfFormatado);
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getDescricaoEnderecoCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setEnderecoReponsavelParcelamento(parcelamentoDadosTermo
								.getDescricaoEnderecoCliente());
			}

			if((totalDebitosImovel != null) && (parcelamento.getValorPrestacao() != null)
							&& (parcelamento.getNumeroPrestacoes() != null)){

				BigDecimal valoDiferencaParcelas = totalDebitosImovel.subtract((parcelamento.getValorPrestacao().multiply(new BigDecimal(
								parcelamento.getNumeroPrestacoes()))));

				// if(parcelamento != null &&
				// parcelamento.getValorAtualizacaoMonetariaSucumbenciaAnterior() != null){
				// valoDiferencaParcelas =
				// valoDiferencaParcelas.subtract(parcelamento.getValorAtualizacaoMonetariaSucumbenciaAnterior());
				// }
				//
				// if(parcelamento != null && parcelamento.getValorJurosMoraSucumbenciaAnterior() !=
				// null){
				// valoDiferencaParcelas =
				// valoDiferencaParcelas.subtract(parcelamento.getValorJurosMoraSucumbenciaAnterior());
				// }

				if(parcelamento != null && parcelamento.getValorSucumbenciaAnterior() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorSucumbenciaAnterior());
				}

				BigDecimal valorSucubenciaEntrada = BigDecimal.ZERO;
				if(parcelamento.getValorSucumbenciaAtualEP() != null){
					valorSucubenciaEntrada = parcelamento.getValorSucumbenciaAtualEP();
				}
				
				if(parcelamento.getValorEntrada() != null){
					BigDecimal valorEntradaAtualizada = parcelamento.getValorEntrada();

					if(valorSucubenciaEntrada.compareTo(BigDecimal.ZERO) != 0){
						valorEntradaAtualizada = valorEntradaAtualizada.subtract(valorSucubenciaEntrada);
					}

					valoDiferencaParcelas = valoDiferencaParcelas.subtract(valorEntradaAtualizada);
				}

				if(parcelamento.getValorDesconto() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorDesconto());
				}

				if(parcelamento.getValorCreditoARealizar() != null){
					valoDiferencaParcelas = valoDiferencaParcelas.subtract(parcelamento.getValorCreditoARealizar());
				}

				if(valoDiferencaParcelas.doubleValue() != 0){
					valoDiferencaParcelas = valoDiferencaParcelas.add(parcelamento.getValorPrestacao());
					
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorParcelaDiferenca(Util
									.formatarMoedaReal(valoDiferencaParcelas).toString());
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorParcelaDiferencaExtenso(Util
									.valorExtenso((valoDiferencaParcelas.abs())));
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getTituloPosse() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setTituloPosseParcelamento(parcelamentoDadosTermo.getTituloPosse());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeExecutado() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeExecutadoParcelamento(parcelamentoDadosTermo.getNomeExecutado());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroVara() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroVaraParcelamento(parcelamentoDadosTermo.getNumeroVara()
								.toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroProcesso() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroProcessoParcelamento(parcelamentoDadosTermo.getNumeroProcesso());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getProfissaoCliente() != null
							&& parcelamentoDadosTermo.getProfissaoCliente().getId() != null){
				FiltroProfissao filtroProfissao = new FiltroProfissao();
				filtroProfissao.adicionarParametro(new ParametroSimples(FiltroProfissao.ID, parcelamentoDadosTermo.getProfissaoCliente()
								.getId()));
				Collection<Profissao> colecaoProfissao = fachada.pesquisar(filtroProfissao, Profissao.class.getName());

				if(colecaoProfissao.size() > 0){
					Profissao profissao = colecaoProfissao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoProfissaoReponsavelParcelamento(profissao.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getEstadoCivilCliente() != null
							&& parcelamentoDadosTermo.getEstadoCivilCliente().getId() != null){
				FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.ID, parcelamentoDadosTermo
								.getEstadoCivilCliente().getId()));
				Collection<EstadoCivil> colecaoEstadoCivil = fachada.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName());

				if(colecaoEstadoCivil.size() > 0){
					EstadoCivil estadoCivil = colecaoEstadoCivil.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEstadoCivilReponsavelParcelamento(estadoCivil
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getIndicadorEnderecoCorrespondenciaCliente() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean
								.setIndicadorEnderecoCorrespondenciaReponsavelParcelamento(parcelamentoDadosTermo
												.getIndicadorEnderecoCorrespondenciaCliente().toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getEnderecoTipoCliente() != null
							&& parcelamentoDadosTermo.getEnderecoTipoCliente().getId() != null){
				FiltroEnderecoTipo filtroEnderecoTipo = new FiltroEnderecoTipo();
				filtroEnderecoTipo.adicionarParametro(new ParametroSimples(FiltroEnderecoTipo.ID, parcelamentoDadosTermo
								.getEnderecoTipoCliente().getId()));
				Collection<EnderecoTipo> colecaoEnderecoTipo = fachada.pesquisar(filtroEnderecoTipo, EnderecoTipo.class.getName());

				if(colecaoEnderecoTipo.size() > 0){
					EnderecoTipo enderecoTipo = colecaoEnderecoTipo.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoTipoEnderecoReponsavelParcelamento(enderecoTipo
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getIndicadorProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorPossuiProcurador(parcelamentoDadosTermo
								.getIndicadorProcurador().toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getOrgaoExpedidorRgCliente() != null
							&& parcelamentoDadosTermo.getOrgaoExpedidorRgCliente().getId() != null){
				FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
				filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, parcelamentoDadosTermo
								.getOrgaoExpedidorRgCliente().getId()));
				Collection<OrgaoExpedidorRg> colecaoOrgaoExpedidorRg = fachada.pesquisar(filtroOrgaoExpedidorRg,
								OrgaoExpedidorRg.class.getName());

				if(colecaoOrgaoExpedidorRg.size() > 0){
					OrgaoExpedidorRg orgaoExpedidorRg = colecaoOrgaoExpedidorRg.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoOrgaoExpedidorReponsavelParcelamento(orgaoExpedidorRg
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getUnidadeFederacaoCliente() != null
							&& parcelamentoDadosTermo.getUnidadeFederacaoCliente().getId() != null){
				FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
				filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, parcelamentoDadosTermo
								.getUnidadeFederacaoCliente().getId()));
				Collection<UnidadeFederacao> colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao,
								UnidadeFederacao.class.getName());

				if(colecaoUnidadeFederacao.size() > 0){
					UnidadeFederacao unidadeFederacao = colecaoUnidadeFederacao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoUnidadeFederacaoReponsavelParcelamento(unidadeFederacao
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeClienteEmpresa() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeEmpresaReponsavelParcelamento(parcelamentoDadosTermo
								.getNomeClienteEmpresa());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa() != null
							&& parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa().getId() != null){
				FiltroRamoAtividade filtroRamoAtividade = new FiltroRamoAtividade();
				filtroRamoAtividade.adicionarParametro(new ParametroSimples(FiltroRamoAtividade.ID, parcelamentoDadosTermo
								.getRamoAtividadeClienteEmpresa().getId()));
				Collection<RamoAtividade> colecaoRamoAtividade = fachada.pesquisar(filtroRamoAtividade, RamoAtividade.class.getName());

				if(colecaoRamoAtividade.size() > 0){
					RamoAtividade ramoAtividade = colecaoRamoAtividade.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoRamoAtividadeEmpresaReponsavelParcelamento(ramoAtividade
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroCnpjClienteEmpresa() != null){
				String cnpjFormatado = parcelamentoDadosTermo.getNumeroCnpjClienteEmpresa();
				String zeros = "";

				for(int a = 0; a < (14 - cnpjFormatado.length()); a++){
					zeros = zeros.concat("0");
				}
				// concatena os zeros ao numero
				// caso o numero seja diferente de nulo
				cnpjFormatado = zeros.concat(cnpjFormatado);
				cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8)
								+ "/" + cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroCnpjEmpresaReponsavelParcelamento(cnpjFormatado);
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getIndicadorEnderecoCorrespondenciaClienteEmpresa() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean
								.setIndicadorEnderecoCorrespondenciaEmpresaReponsavelParcelamento(parcelamentoDadosTermo
												.getIndicadorEnderecoCorrespondenciaClienteEmpresa().toString());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getDescricaoEnderecoClienteEmpresa() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEnderecoEmpresaReponsavelParcelamento(parcelamentoDadosTermo
								.getDescricaoEnderecoClienteEmpresa());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getDescricaoEnderecoProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEnderecoProcuradorParcelamento(parcelamentoDadosTermo
								.getDescricaoEnderecoProcurador());
			}
			
			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroCpfProcurador() != null){
				String cpfFormatado = parcelamentoDadosTermo.getNumeroCpfProcurador();
				if(cpfFormatado != null && cpfFormatado.length() == 11){

					cpfFormatado = cpfFormatado.substring(0, 3) + "." + cpfFormatado.substring(3, 6) + "." + cpfFormatado.substring(6, 9)
									+ "-" + cpfFormatado.substring(9, 11);
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroCpfProcuradorParcelamento(cpfFormatado);
			}
			
			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNomeProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNomeProcuradorParcelamento(parcelamentoDadosTermo.getNomeProcurador());
			}
			
			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNumeroRgProcurador() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroRgProcuradorParcelamento(parcelamentoDadosTermo
								.getNumeroRgProcurador());
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getOrgaoExpedidorRgProcurador() != null
							&& parcelamentoDadosTermo.getOrgaoExpedidorRgProcurador().getId() != null){
				FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
				filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, parcelamentoDadosTermo
								.getOrgaoExpedidorRgProcurador().getId()));
				Collection<OrgaoExpedidorRg> colecaoOrgaoExpedidorRg = fachada.pesquisar(filtroOrgaoExpedidorRg,
								OrgaoExpedidorRg.class.getName());

				if(colecaoOrgaoExpedidorRg.size() > 0){
					OrgaoExpedidorRg orgaoExpedidorRg = colecaoOrgaoExpedidorRg.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoOrgaoExpedidorProcuradorParcelamento(orgaoExpedidorRg
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getUnidadeFederacaoProcurador() != null
							&& parcelamentoDadosTermo.getUnidadeFederacaoProcurador().getId() != null){
				FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
				filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, parcelamentoDadosTermo
								.getUnidadeFederacaoProcurador().getId()));
				Collection<UnidadeFederacao> colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao,
								UnidadeFederacao.class.getName());

				if(colecaoUnidadeFederacao.size() > 0){
					UnidadeFederacao unidadeFederacao = colecaoUnidadeFederacao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoUnidadeFederacaoProcuradorParcelamento(unidadeFederacao
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getProfissaoProcurador() != null
							&& parcelamentoDadosTermo.getProfissaoProcurador().getId() != null){
				FiltroProfissao filtroProfissao = new FiltroProfissao();
				filtroProfissao.adicionarParametro(new ParametroSimples(FiltroProfissao.ID, parcelamentoDadosTermo.getProfissaoProcurador()
								.getId()));
				Collection<Profissao> colecaoProfissao = fachada.pesquisar(filtroProfissao, Profissao.class.getName());

				if(colecaoProfissao.size() > 0){
					Profissao profissao = colecaoProfissao.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoProfissaoProcuradorParcelamento(profissao.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getEstadoCivilProcurador() != null
							&& parcelamentoDadosTermo.getEstadoCivilProcurador().getId() != null){
				FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.ID, parcelamentoDadosTermo
								.getEstadoCivilProcurador().getId()));
				Collection<EstadoCivil> colecaoEstadoCivil = fachada.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName());

				if(colecaoEstadoCivil.size() > 0){
					EstadoCivil estadoCivil = colecaoEstadoCivil.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoEstadoCivilProcuradorParcelamento(estadoCivil
									.getDescricao());
				}
			}

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getNacionalidadeProcurador() != null
							&& parcelamentoDadosTermo.getNacionalidadeProcurador().getId() != null){
				FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.ID, parcelamentoDadosTermo
								.getNacionalidadeProcurador().getId()));
				Collection<Nacionalidade> colecaoNacionalidadel = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());

				if(colecaoNacionalidadel.size() > 0){
					Nacionalidade nacionalidade = colecaoNacionalidadel.iterator().next();
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDescricaoNacionalidadeProcuradorParcelamento(nacionalidade
									.getDescricao());
				}
			}

			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoDatasParcelamento(stringColecaoDatasParcelamento);
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoDatasParcelamentoSobra(stringColecaoDatasParcelamentoSobra);

			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosParcelamento(stringColecaoServicosParcelamento);
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosParcelamentoSobra(stringColecaoServicosParcelamentoSobra);

			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosDebitoACobrar(colecaoDebitosACobrar);
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setColecaoServicosDebitoACobrarSobra(colecaoDebitosACobrarSobra);

			if(parcelamento != null && parcelamento.getNumeroParcelasSucumbencia() != null){
				int quantidadeParcelasSucumbencia;
				
				quantidadeParcelasSucumbencia = parcelamento.getNumeroParcelasSucumbencia().intValue() - 1;
				if(quantidadeParcelasSucumbencia >= 1){
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setQuantidadeParcelasSucumbencia(String
									.valueOf(quantidadeParcelasSucumbencia));
				}else{
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setQuantidadeParcelasSucumbencia("");
				}
			}

			BigDecimal valorTotalSucumbencia = BigDecimal.ZERO;
			if(parcelamento != null && parcelamento.getValorSucumbenciaAtual() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamento.getValorSucumbenciaAtual());
			}

			if(parcelamento != null && parcelamento.getValorAtualizacaoMonetariaSucumbenciaAnterior() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamento.getValorAtualizacaoMonetariaSucumbenciaAnterior());
			}

			if(parcelamento != null && parcelamento.getValorJurosMoraSucumbenciaAnterior() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamento.getValorJurosMoraSucumbenciaAnterior());
			}

			if(parcelamento != null && parcelamento.getValorSucumbenciaAnterior() != null){
				valorTotalSucumbencia = valorTotalSucumbencia.add(parcelamento.getValorSucumbenciaAnterior());
			}

			if(valorTotalSucumbencia != BigDecimal.ZERO && !valorTotalSucumbencia.toString().equals("0.00")){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorTotalSucumbencia(valorTotalSucumbencia.toString());
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorTotalSucumbenciaExtenso(Util.valorExtenso(valorTotalSucumbencia));
			}

			String numerosProcessos = "";
			if(parcelamento.getImovel() != null && parcelamento.getImovel().getId() != null){

				// TODO Saulo Lima - Ver com Gil
				Map<Integer, BigDecimal> mapProcessos = fachada.determinarValorSucumbenciaAtual(
								Integer.valueOf(parcelamento.getImovel().getId()), colecaoContaValores, colecaoGuiaPagamentoValores, null,
								null);
				String separador = "";
				Collection<Integer> chavesProcessos = mapProcessos.keySet();
				for(Integer numeroProcesso : chavesProcessos){
					numerosProcessos = numeroProcesso + separador + numerosProcessos;
					separador = ", ";
				}

			}
			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setNumeroProcessosExecucao(numerosProcessos);
			
			if(parcelamento != null && parcelamento.getCobrancaForma() != null && parcelamento.getCobrancaForma().getId() != null){
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorCobrancaParcelamento(parcelamento.getCobrancaForma().getId()
								.toString());
			}	
			
			BigDecimal valorEntradaSucumbencia = BigDecimal.ZERO;

			relatorioParcelamentoResolucaoDiretoriaLayoutBean.setIndicadorValorEntradaGuia(indicadorValorEntradaPorGuia);
			if(indicadorValorEntradaPorGuia.equals("1") && !parcelamento.getValorEntrada().equals(BigDecimal.ZERO)
							&& !parcelamento.getValorEntrada().toString().equals("0.00") && !valorTotalSucumbencia.equals(BigDecimal.ZERO)
							&& !valorTotalSucumbencia.equals("0.00")){
				BigDecimal valorEntradaMenosSucumbencia = parcelamento.getValorEntrada();

				if(parcelamento.getNumeroParcelasSucumbencia() != null
								&& !parcelamento.getNumeroParcelasSucumbencia().equals(Short.valueOf("0"))){

					valorEntradaMenosSucumbencia = valorEntradaMenosSucumbencia.subtract(valorTotalSucumbencia.divide(new BigDecimal(
									parcelamento.getNumeroParcelasSucumbencia()), 2, RoundingMode.HALF_UP));
					valorEntradaSucumbencia = valorTotalSucumbencia.divide(new BigDecimal(parcelamento.getNumeroParcelasSucumbencia()), 2,
									RoundingMode.HALF_UP);
				}else{
					valorEntradaMenosSucumbencia = valorEntradaMenosSucumbencia.subtract(valorTotalSucumbencia);
					valorEntradaSucumbencia = valorTotalSucumbencia;
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbencia(Util
								.formatarMoedaReal(valorEntradaMenosSucumbencia));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbenciaExtenso(Util
								.valorExtenso(valorEntradaMenosSucumbencia.abs()));

			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbencia(valorEntrada);
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaMenosGuiaSucumbenciaExtenso(valorEntradaExtenso);
			}

			if(parcelamento.getValorEntrada() != null && !parcelamento.getValorEntrada().equals(BigDecimal.ZERO)
							&& !parcelamento.getValorEntrada().toString().equals("0.00")){

				BigDecimal valorPrestacaoEntradaSucumbencia = BigDecimal.ZERO;
				if(parcelamento.getNumeroParcelasSucumbencia() != null
								&& !parcelamento.getNumeroParcelasSucumbencia().equals(Short.valueOf("0"))){

					valorPrestacaoEntradaSucumbencia = valorTotalSucumbencia.divide(
									new BigDecimal(parcelamento.getNumeroParcelasSucumbencia()), 2, RoundingMode.HALF_UP);
					if(valorPrestacaoEntradaSucumbencia.compareTo(parcelamento.getValorEntrada()) == 1){
						valorPrestacaoEntradaSucumbencia = parcelamento.getValorEntrada();
					}
				}else{
					if(parcelamento.getValorEntrada().compareTo(valorTotalSucumbencia) == 1){
						valorPrestacaoEntradaSucumbencia = valorTotalSucumbencia;
					}else{
						valorPrestacaoEntradaSucumbencia = parcelamento.getValorEntrada();
					}
				}

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbencia(Util
								.formatarMoedaReal(valorPrestacaoEntradaSucumbencia));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbenciaExtenso(Util
								.valorExtenso(valorPrestacaoEntradaSucumbencia));
			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbencia(Util.formatarMoedaReal(BigDecimal.ZERO));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorEntradaSucumbenciaExtenso(Util.valorExtenso(BigDecimal.ZERO));
			}

			if(!parcelamento.getCobrancaForma().getId().equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_DEBITO_A_COBRAR)){
				Calendar calendar = Calendar.getInstance();

				if(parcelamento.getValorEntrada() != null && !parcelamento.getValorEntrada().equals(BigDecimal.ZERO)
								&& !parcelamento.getValorEntrada().equals(new BigDecimal("0.00"))){
					calendar.setTime(Util.somaMesData(parcelamento.getDataEntradaParcelamento(), 1));
				}else{
					Date dataVencimentoCalculada = null;
					Integer numeroDias = null;

					if(numeroDiasVencimentoEntrada != null && !numeroDiasVencimentoEntrada.equals(0)){
						numeroDias = numeroDiasVencimentoEntrada;
					}else{
						try{
							numeroDias = Integer.parseInt((String) ParametroCobranca.P_NUMERO_DIAS_CALCULO_VENCIMENTO_PARCELA
											.executar(ExecutorParametrosCobranca.getInstancia()));
						}catch(NumberFormatException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch(ControladorException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					dataVencimentoCalculada = fachada.obterDataVencimentoGuiaEntradaParcelamento(new Date(), numeroDias);
					calendar.setTime(dataVencimentoCalculada);
				}

				int ano = calendar.get(Calendar.YEAR);
				int mes = calendar.get(Calendar.MONTH) + 1;
				int dia = calendar.get(Calendar.DAY_OF_MONTH);

				String dataPorExtenso = dia + " de " + Util.retornaDescricaoMes(mes).toUpperCase() + " de " + ano + "";
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDataVecimentoPrimeiraGuiaPagamento(dataPorExtenso.toString());

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDataVecimentoPrimeiraGuiaPagamentoSucumbencia(dataPorExtenso
								.toString());

				calendar.add(Calendar.MONTH, 1);
				ano = calendar.get(Calendar.YEAR);
				mes = calendar.get(Calendar.MONTH) + 1;
				dia = calendar.get(Calendar.DAY_OF_MONTH);

				dataPorExtenso = dia + " de " + Util.retornaDescricaoMes(mes).toUpperCase() + " de " + ano + "";
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setDataVecimentoSegundaGuiaPagamento(dataPorExtenso.toString());
			}

			if(parcelamento.getNumeroParcelasSucumbencia() != null
							&& !parcelamento.getNumeroParcelasSucumbencia().equals(Short.valueOf("0"))){
				valorEntradaSucumbencia = valorTotalSucumbencia.divide(new BigDecimal(parcelamento.getNumeroParcelasSucumbencia()), 2,
								RoundingMode.HALF_UP);

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbencia(Util
								.formatarMoedaReal(valorEntradaSucumbencia));
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbenciaExtenso(Util
								.valorExtenso(valorEntradaSucumbencia));
			}else{
				if(valorTotalSucumbencia != null && !valorTotalSucumbencia.equals(BigDecimal.ZERO) && !valorTotalSucumbencia.equals("0.00")){
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setQuantidadeParcelasSucumbencia("");

					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbencia(Util
									.formatarMoedaReal(valorTotalSucumbencia));
					relatorioParcelamentoResolucaoDiretoriaLayoutBean.setValorPrestacaoSucumbenciaExtenso(Util
									.valorExtenso(valorTotalSucumbencia));

				}

			}

			Map<String, Integer> periodoDebito = fachada.obterPeriodoDebitoParcelmento(parcelamento.getId());

			if(periodoDebito.get("menorReferencia") != null){

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoInicial(Util.formatarAnoMesParaMesAno(String
								.valueOf(periodoDebito.get("menorReferencia"))));

			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoInicial(" - ");
			}

			if(periodoDebito.get("maiorReferencia") != null){

				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoFinal(Util.formatarAnoMesParaMesAno(String
								.valueOf(periodoDebito.get("maiorReferencia"))));

			}else{
				relatorioParcelamentoResolucaoDiretoriaLayoutBean.setAnoMesReferenciaDebitoFinal(" - ");
			}

			// adiciona o bean a cole��o
			relatorioParcelamentosBeans.add(relatorioParcelamentoResolucaoDiretoriaLayoutBean);
		}

		// retorna o relat�rio gerado
		return relatorioParcelamentosBeans;
	}

	@Override
	public List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> gerarDados(Collection<Parcelamento> colecaoParcelamento,
					int idFuncionalidadeIniciada) throws GeradorRelatorioParcelamentoException{

		return null;
	}

	public String gerarTextoHtml(RelatorioParcelamentoResolucaoDiretoriaLayoutBean dadosRelatorioParcelameto, String nomeRelatorio,
					Parcelamento parcelamento)
					throws GeradorRelatorioParcelamentoException{

		final String nomeRelatorioParcelamentoSOROCABA = "relatorioParcelamentoSOROCABA.jasper".trim().toUpperCase();
		final String nomeRelatorioParcelamentoExecucaoFiscal = "relatorioParcelamentoExecucaoFiscal.jasper".trim().toUpperCase();

		nomeRelatorio = nomeRelatorio.trim().toUpperCase();
		String stringHtml = "<span style='font-size:9pt'>";

		if(nomeRelatorio.equals(nomeRelatorioParcelamentoSOROCABA)) {

			// 1 Paragrafo
			stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Eu, ");
			stringHtml = stringHtml.concat(dadosRelatorioParcelameto.getNomeProprietarioImovel() + ", ");

			if(dadosRelatorioParcelameto.getNacionaliadeProprietarioImovel() != null
							&& !dadosRelatorioParcelameto.getNacionaliadeProprietarioImovel().equals("")){
				stringHtml = stringHtml.concat(dadosRelatorioParcelameto.getNacionaliadeProprietarioImovel() + " , ");
			}
			
			if(dadosRelatorioParcelameto.getRgProprietarioImovel() != null
							&& !dadosRelatorioParcelameto.getRgProprietarioImovel().equals("")){
				stringHtml = stringHtml.concat(" portador do R.G. n� " + dadosRelatorioParcelameto.getRgProprietarioImovel() + " , ");
			}

			if(dadosRelatorioParcelameto.getCpfProprietarioImovel() != null
							&& !dadosRelatorioParcelameto.getCpfProprietarioImovel().equals("")){
				if(dadosRelatorioParcelameto.getCpfProprietarioImovel().length() <= 14){
					stringHtml = stringHtml.concat(" inscrito no  CPF/MF sob n� " + dadosRelatorioParcelameto.getCpfProprietarioImovel());
				}else{
					stringHtml = stringHtml.concat(" inscrito no  CNPJ sob n� " + dadosRelatorioParcelameto.getCpfProprietarioImovel());
				}
			}

			stringHtml = stringHtml.concat(", residente � " + dadosRelatorioParcelameto.getEnderecoProprietarioImovel());
			stringHtml = stringHtml.concat(", na qualidade de PROPRIET�RIO do im�vel "
							+ dadosRelatorioParcelameto.getDescricaoCategoriaImovel());
			stringHtml = stringHtml.concat(" sito � " + dadosRelatorioParcelameto.getEnderecoImovel());
			stringHtml = stringHtml.concat(", Matr�cula " + dadosRelatorioParcelameto.getMatriculaImovel());
			stringHtml = stringHtml.concat(", inscri��o: " + dadosRelatorioParcelameto.getInscricaoImovel());
			stringHtml = stringHtml.concat(", atrav�s do presente termo e � luz do inciso I do artigo 136 do C�digo Civil Brasileiro");
			stringHtml = stringHtml.concat(", confessa ser devedor da import�ncia de R$ " + dadosRelatorioParcelameto.getTotalDebitos()
							+ " ("
							+ dadosRelatorioParcelameto.getTotalDebitosExtenso() + ") ");
			stringHtml = stringHtml.concat(" ao " + dadosRelatorioParcelameto.getNomeEmpresa());

			if(dadosRelatorioParcelameto.getColecaoAnoMesReferencia() != null
							&& !dadosRelatorioParcelameto.getColecaoAnoMesReferencia().equals("")){
				stringHtml = stringHtml.concat(", relativa  ao pre�o dos servi�os p�blicos de �gua e esgoto nos meses de "
								+ dadosRelatorioParcelameto.getColecaoAnoMesReferencia());
			}else{
				stringHtml = stringHtml.concat(", relativa ");
			}

			if(dadosRelatorioParcelameto.getColecaoServicosParcelamento() != null
							&& !dadosRelatorioParcelameto.getColecaoServicosParcelamento().equals("")){
				if(dadosRelatorioParcelameto.getColecaoAnoMesReferencia() != null
								&& !dadosRelatorioParcelameto.getColecaoAnoMesReferencia().equals("")){
					stringHtml = stringHtml.concat(" e aos d�bitos de presta��o de: "
									+ dadosRelatorioParcelameto.getColecaoServicosParcelamento());
				}else{
					stringHtml = stringHtml.concat(" aos d�bitos de presta��o de: "
									+ dadosRelatorioParcelameto.getColecaoServicosParcelamento());
				}
			}
			stringHtml = stringHtml.concat(".");

			// 2 Paragrafo
			stringHtml = stringHtml
							.concat("<p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Desta forma, reconhecendo a d�vida fiscal, compromete-se em quit�-la com ");

			String valorEntrada = "";
			if(dadosRelatorioParcelameto.getValorEntrada() != null && !dadosRelatorioParcelameto.getValorEntrada().equals("")){
				valorEntrada = " entrada de R$" + dadosRelatorioParcelameto.getValorEntrada() + " ("
								+ dadosRelatorioParcelameto.getValorEntradaExtenso() + ") ";
			}

			// if(dadosRelatorioParcelameto.getValorParcelaDiferenca() != null
			// && !dadosRelatorioParcelameto.getValorParcelaDiferenca().equals("")){
			// stringHtml = stringHtml.concat("01 parcela de " +
			// dadosRelatorioParcelameto.getValorParcelaDiferenca() + " ("
			// + dadosRelatorioParcelameto.getValorParcelaDiferencaExtenso() + ")" + valorEntrada
			// + " e "
			// + String.valueOf(Integer.parseInt(dadosRelatorioParcelameto.getNumeroParcelas()) - 1)
			// + " parcela(s) de R$ " + dadosRelatorioParcelameto.getValorParcela() + " ("
			// + dadosRelatorioParcelameto.getValorParcelaExtenso() + ") cada ");
			// }else{
			// stringHtml = stringHtml.concat(dadosRelatorioParcelameto.getNumeroParcelas() +
			// " parcela(s) de R$ "
			// + dadosRelatorioParcelameto.getValorParcela() + " (" +
			// dadosRelatorioParcelameto.getValorParcelaExtenso()
			// + ") cada " + valorEntrada);
			// }

			stringHtml = stringHtml.concat(valorEntrada + "e R$ " + dadosRelatorioParcelameto.getValorParcelado() + " ("
							+ dadosRelatorioParcelameto.getValorParceladoExtenso() + ") em "
							+ dadosRelatorioParcelameto.getNumeroParcelas()
							+ " parcela(s), sendo a diferen�a de centavos cobrada na �ltima parcela, ");

			stringHtml = stringHtml
							.concat("tendo como certo que o pagamento das parcelas dever� se operar MENSALMENTE, e vencimento em CONTAS MENSAIS.");

			// 3 Paragrafo
			stringHtml = stringHtml
							.concat("<p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; O n�o pagamento de qualquer das parcelas no prazo aven�ado, ou a n�o quita��o das ");
			stringHtml = stringHtml
							.concat("contas mensais de consumo do im�vel beneficiado com o presente parcelamento, no caso das parcelas n�o estarem inclu�das nas referidas contas, ");
			stringHtml = stringHtml
							.concat("acarretar� na autom�tica rescis�o deste termo, quando a totalidade da d�vida tornar-se-� exig�vel de imediato e o fornecimento de �gua suprimido, na forma da legisla��o municipal vigente, ");
			stringHtml = stringHtml.concat("sem preju�zo das demais san��es previstas em Lei.");

			// 4 Paragrafo
			stringHtml = stringHtml
							.concat("<p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Assina tamb�m este instrumento, na condi��o de USU�RIO ou LOCAT�RIO do im�vel devedor, o(a) Sr(a). ");
			stringHtml = stringHtml.concat(dadosRelatorioParcelameto.getNomeReponsavelParcelamento() + " , "
							+ dadosRelatorioParcelameto.getDescricaoNacionalidadeReponsavelParcelamento());
			stringHtml = stringHtml.concat(" portador do R.G. n.� " + dadosRelatorioParcelameto.getNumeroRgReponsavelParcelamento());
			stringHtml = stringHtml.concat(" , inscrito no CPF/MF sob n.� "
							+ dadosRelatorioParcelameto.getNumeroCpfReponsavelParcelamento());
			stringHtml = stringHtml.concat(" , residente na " + dadosRelatorioParcelameto.getEnderecoReponsavelParcelamento());
			stringHtml = stringHtml.concat(" , o qual declara reconhecer o d�bito fiscal objeto do presente parcelamento e comprometer-se");
			stringHtml = stringHtml
							.concat(" , nos termos das disposi��es do C�digo Tribut�rio Nacional pertinentes � responsabilidade solid�ria");
			stringHtml = stringHtml.concat(" , nas mesmas obriga��es assumidas pelo usu�rio dos servi�os p�blicos de �gua e esgoto.");


		}else if(nomeRelatorio.equals(nomeRelatorioParcelamentoExecucaoFiscal)){
			ParcelamentoDadosTermo parcelamentoDadosTermo = parcelamento.getParcelamentoDadosTermo();
			ParcelamentoAcordoTipo parcelamentoAcordoTipo = null;

			if(parcelamentoDadosTermo != null && parcelamentoDadosTermo.getParcelamentoAcordoTipo() != null){
				parcelamentoAcordoTipo = parcelamentoDadosTermo.getParcelamentoAcordoTipo();
			}
			
			if (parcelamentoAcordoTipo != null) {
				if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(1)) || parcelamentoAcordoTipo.getId().equals(Integer.valueOf(2))
								|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(3)) || parcelamentoAcordoTipo.getId().equals(Integer.valueOf(4))
								|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(5)) || parcelamentoAcordoTipo.getId().equals(Integer.valueOf(6))
								|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(7)) || parcelamentoAcordoTipo.getId().equals(Integer.valueOf(8))
								|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(9))
								|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(10))
								|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(11))){

					// 1 Paragrafo
					stringHtml = stringHtml
									.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Pelo presente Termo de Confiss�o e Parcelamento de D�vida, de um lado o SERVI�O AUT�NOMO DE �GUA E ESGOTO DE SOROCABA, ");
					stringHtml = stringHtml
									.concat(" autarquia municipal criada pela Lei n� 1.390, de 31 de dezembro de 1965, com sede nesta cidade de Sorocaba, na Avenida Pereira da Silva, n� 1.285, Santa Ros�lia, inscrita no CNPJ sob o n� 71.560.480/0001-39, doravante denominado simplesmente SAAE, ");

					if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(1)) || parcelamentoAcordoTipo.getId().equals(Integer.valueOf(2))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(3))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(4))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(5))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(6))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(7))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(9))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(10))
									|| parcelamentoAcordoTipo.getId().equals(Integer.valueOf(11))){
						stringHtml = stringHtml.concat(" e de outro lado " + dadosRelatorioParcelameto.getNomeReponsavelParcelamento() + " , " 
											+ dadosRelatorioParcelameto.getDescricaoNacionalidadeReponsavelParcelamento() + " , "
											+ dadosRelatorioParcelameto.getDescricaoEstadoCivilReponsavelParcelamento() + " , " 
											+ dadosRelatorioParcelameto.getDescricaoProfissaoReponsavelParcelamento());
						stringHtml = stringHtml.concat(" , portador(a) do CPF " + dadosRelatorioParcelameto.getNumeroCpfReponsavelParcelamento());
						stringHtml = stringHtml.concat(" e da C�dula de Identidade RG sob o n� " + dadosRelatorioParcelameto.getNumeroRgReponsavelParcelamento());
						stringHtml = stringHtml.concat(", residente no(a) " +  dadosRelatorioParcelameto.getEnderecoReponsavelParcelamento());

						if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(3))){
							stringHtml = stringHtml
											.concat(" , na qualidade de atual propriet�rio do im�vel, conforme formal de partilha e/ou matr�cula do im�vel anexa ");
						}
						if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(4))){
							stringHtml = stringHtml
											.concat(" , na qualidade de atual propriet�rio do im�vel, conforme comprovam os documentos anexos ");
						}
						if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(5))){
							stringHtml = stringHtml.concat(" , na qualidade de inventariante nomeado nos autos do processo n� "
											+ dadosRelatorioParcelameto.getNumeroProcessoParcelamento());
							stringHtml = stringHtml.concat(" , em tr�mite pela " + dadosRelatorioParcelameto.getNumeroVaraParcelamento()
											+ " Vara C�vil ou de Fam�lia e Sucess�es da Comarca de Sorocaba,");
							stringHtml = stringHtml.concat(" do esp�lio do falecimento executado ("
											+ dadosRelatorioParcelameto.getNomeExecutadoParcelamento() + "), ");
							stringHtml = stringHtml.concat(" conforme comprovate o incluso termo de nomea��o e compromisso ");

						}
						if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(6))){
							stringHtml = stringHtml
											.concat(" , na qualidade administrador provis�rio, nos termos dos artigos 985 e 986 do CPC ");
							stringHtml = stringHtml.concat(", dos bens deixados pelo falecido executado - "
											+ dadosRelatorioParcelameto.getNomeExecutadoParcelamento() + " - (certid�o de �bito anexa) ");
						} if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(7))){
							stringHtml = stringHtml.concat(" , na qualidade de (" + dadosRelatorioParcelameto.getTituloPosseParcelamento());
							stringHtml = stringHtml
											.concat(") , do im�vel devedor, devidamente autorizado pelo Propriet�rio, conforme autoriza��o anexa ");
						}
						if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(10))){
							stringHtml = stringHtml.concat(" , na qualidade de c�njuge do executado(a) - "
											+ dadosRelatorioParcelameto.getNomeExecutadoParcelamento()
											+ " (certid�o de casamento anexo) e co-propriet�ria(o) do im�vel devedor ");
						}
						if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(11))){
							stringHtml = stringHtml
											.concat(" , na qualidade de c�njuge do executado(a) - "
															+ dadosRelatorioParcelameto.getNomeExecutadoParcelamento()
															+ " - (certid�o de casamento anexo) nos termos do artigo 1642, inciso VI c.c. artigos 1643, inciso I e 1644, todos do C�digo Civil ");
						}

						stringHtml = stringHtml.concat(" , doravante denominado simplesmente DEVEDOR(A) ");

					}else if(parcelamentoAcordoTipo.getId().equals(Integer.valueOf(8))){
						stringHtml = stringHtml.concat(" e de outro lado "
										+ dadosRelatorioParcelameto.getNomeEmpresaReponsavelParcelamento() + " , pessoa jur�dica do tipo ("
										+ dadosRelatorioParcelameto.getDescricaoRamoAtividadeEmpresaReponsavelParcelamento()
										+ ") , incrita no CNPJ n� "
										+ dadosRelatorioParcelameto.getNumeroCnpjEmpresaReponsavelParcelamento() + " , estabelecida no(a) "
										+ dadosRelatorioParcelameto.getDescricaoEnderecoEmpresaReponsavelParcelamento());
						
						stringHtml = stringHtml
										.concat(" por meio de seu representante legal ("
														+ dadosRelatorioParcelameto.getNomeReponsavelParcelamento()
														+ " , "
														+ dadosRelatorioParcelameto.getDescricaoNacionalidadeReponsavelParcelamento()
														+ " , "
														+ dadosRelatorioParcelameto.getDescricaoEstadoCivilReponsavelParcelamento()
														+ " , "
														+ dadosRelatorioParcelameto.getDescricaoProfissaoReponsavelParcelamento()
														+ " , portador da c�dula de identidade RG n� "
														+ dadosRelatorioParcelameto.getNumeroRgReponsavelParcelamento()
														+ " e inscrito no Minist�rio da Fazenda no CPF n� "
														+ dadosRelatorioParcelameto.getNumeroCpfReponsavelParcelamento()
														+ "), conforme comprovam os documentos anexos (ato constitutivo - estatuto ou contrato social e ata da assembl�ia de elei��o do administrador) ");

						stringHtml = stringHtml.concat(" , doravante denominado simplesmente DEVEDOR(A) ");
					}
					
					// Dados do Procurador
					if (dadosRelatorioParcelameto.getIndicadorPossuiProcurador() != null && dadosRelatorioParcelameto.getIndicadorPossuiProcurador().equals("1")) {
						stringHtml = stringHtml.concat(", neste ato representado por seu procurador " + dadosRelatorioParcelameto.getNomeProcuradorParcelamento() + " , ");
						stringHtml = stringHtml.concat(dadosRelatorioParcelameto.getDescricaoNacionalidadeProcuradorParcelamento() + " , ");
						stringHtml = stringHtml.concat(dadosRelatorioParcelameto.getDescricaoProfissaoProcuradorParcelamento());
						stringHtml = stringHtml.concat(" , portador do CPF " + dadosRelatorioParcelameto.getNumeroCpfProcuradorParcelamento());
						stringHtml = stringHtml.concat(" e da C�dula de Identidade RG sob o n� " + dadosRelatorioParcelameto.getNumeroRgProcuradorParcelamento());
						stringHtml = stringHtml.concat(" , residente no(a) " + dadosRelatorioParcelameto.getDescricaoEnderecoProcuradorParcelamento());
						
					}
					
					stringHtml = stringHtml.concat(" , t�m entre si por justo e acordado o reconhecimento e parcelamento do d�bito fiscal e processual abaixo discriminado mediante as cl�usulas e condi��es a seguir enumeradas:");

					// 2 Paragrafo		
					stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 1. A D�VIDA. O devedor reconhece como leg�timo, l�quido, ");
					stringHtml = stringHtml.concat(" certo e exig�vel o d�bito no valor de R$ " +  dadosRelatorioParcelameto.getTotalDebitos()
									+ " ("+ dadosRelatorioParcelameto.getTotalDebitosExtenso() + ") ");
					stringHtml = stringHtml.concat(" , referente � utiliza��o dos servi�os p�blicos de fornecimento de �gua e coleta de esgotos sanit�rios ");
					stringHtml = stringHtml.concat(" pelo im�vel situado no(a)  " + dadosRelatorioParcelameto.getEnderecoImovel());
					stringHtml = stringHtml.concat(" , Matr�cula " + dadosRelatorioParcelameto.getMatriculaImovel());
					
					
					if (dadosRelatorioParcelameto.getValorTotalSucumbencia() != null) {
						if (dadosRelatorioParcelameto.getNumeroProcessosExecucao() != null && dadosRelatorioParcelameto.getNumeroProcessosExecucao() != "" ) {
							stringHtml = stringHtml.concat(" , bem como os honor�rios advocat�cios fixados nos autos da Execu��o Fiscal n� " 
												+ dadosRelatorioParcelameto.getNumeroProcessosExecucao());
							stringHtml = stringHtml.concat(" , em tr�mite perante a Vara da Fazenda P�blica da Comarca de Sorocaba, ");
							stringHtml = stringHtml.concat(" no importe de R$ " + dadosRelatorioParcelameto.getValorTotalSucumbencia() 
											+ " (" + dadosRelatorioParcelameto.getValorTotalSucumbenciaExtenso() + ")");
						} else {
							stringHtml = stringHtml.concat(" , bem como os honor�rios advocat�cios no importe de R$ " 
											+ dadosRelatorioParcelameto.getValorTotalSucumbencia()  + " ("
											+ dadosRelatorioParcelameto.getValorTotalSucumbenciaExtenso() + ")");
						}
			
					}
					stringHtml = stringHtml.concat(". ");

					// 3 Paragrafo	
					stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 2. O PAGAMENTO. O pagamento do d�bito referido na cl�usula anterior ser� efetuado da seguinte forma: ");
					
					
					String valorEntrada = "";
					/*
					 * if(dadosRelatorioParcelameto.getIndicadorValorEntradaGuia() != null &&
					 * dadosRelatorioParcelameto.getIndicadorValorEntradaGuia().equals("1")){
					 * valorEntrada = " entrada de R$ " +
					 * dadosRelatorioParcelameto.getValorEntradaMenosGuiaSucumbencia() + " ("
					 * + dadosRelatorioParcelameto.getValorEntradaMenosGuiaSucumbenciaExtenso() +
					 * ") ";
					 * } else {
					 */
						valorEntrada = " entrada de R$ " + dadosRelatorioParcelameto.getValorEntrada() + " ("
										+ dadosRelatorioParcelameto.getValorEntradaExtenso() + ") ";						
					// }
				

					// if(dadosRelatorioParcelameto.getValorParcelaDiferenca() != null
					// && !dadosRelatorioParcelameto.getValorParcelaDiferenca().equals("")){
					// stringHtml = stringHtml.concat(valorEntrada + " mais 01 parcela de "
					// + dadosRelatorioParcelameto.getValorParcelaDiferenca() + " ("
					// + dadosRelatorioParcelameto.getValorParcelaDiferencaExtenso() + ")"
					// + " e "
					// +
					// String.valueOf(Integer.parseInt(dadosRelatorioParcelameto.getNumeroParcelas())
					// - 1)
					// + " parcela(s) de R$ " + dadosRelatorioParcelameto.getValorParcela() + " ("
					// + dadosRelatorioParcelameto.getValorParcelaExtenso() + ") cada ");
					// }else{
					// stringHtml = stringHtml.concat(valorEntrada + " e "
					// + dadosRelatorioParcelameto.getNumeroParcelas() + " parcela(s) de R$ "
					// + dadosRelatorioParcelameto.getValorParcela() + " ("
					// + dadosRelatorioParcelameto.getValorParcelaExtenso() + ") cada ");
					// }

					stringHtml = stringHtml.concat(valorEntrada + "e R$ " + dadosRelatorioParcelameto.getValorParcelado() + " ("
									+ dadosRelatorioParcelameto.getValorParceladoExtenso() + ") em "
									+ dadosRelatorioParcelameto.getNumeroParcelas()
									+ " parcela(s), sendo a diferen�a de centavos cobrada na �ltima parcela, ");

					// stringHtml = stringHtml.concat(" , ");

					if (dadosRelatorioParcelameto.getIndicadorCobrancaParcelamento().equals("1")) {
						stringHtml = stringHtml.concat(" parcelas essas que ser�o lan�adas em contas futuras, independentemente de outros avisos ou notifica��es");
					} else {
					
						if (dadosRelatorioParcelameto.getIndicadorValorEntradaGuia().equals("1") && dadosRelatorioParcelameto.getValorEntrada() != null 
								&& !dadosRelatorioParcelameto.getValorEntrada().equals("") 
								&& !dadosRelatorioParcelameto.getValorEntrada().equals("0,00") ) {
							if (dadosRelatorioParcelameto.getNumeroParcelas() != null) {
								if (dadosRelatorioParcelameto.getNumeroParcelas().equals("1")) {
									stringHtml = stringHtml.concat(" venc�vel no dia " + dadosRelatorioParcelameto.getDataVecimentoSegundaGuiaPagamento());
								} else {
									stringHtml = stringHtml.concat(" venc�vel a primeira no dia " + dadosRelatorioParcelameto.getDataVecimentoSegundaGuiaPagamento()
													+ " , e as seguintes no mesmo dia dos meses subseq�entes");
								}
							} else {
								stringHtml = stringHtml.concat(" venc�vel no dia " + dadosRelatorioParcelameto.getDataVecimentoSegundaGuiaPagamento());
							}
						} else {
							if (dadosRelatorioParcelameto.getNumeroParcelas() != null) {
								if (dadosRelatorioParcelameto.getNumeroParcelas().equals("1")) {
									stringHtml = stringHtml.concat(" venc�vel no dia " + dadosRelatorioParcelameto.getDataVecimentoPrimeiraGuiaPagamento());
								} else {
									stringHtml = stringHtml.concat(" venc�vel a primeira no dia " + dadosRelatorioParcelameto.getDataVecimentoPrimeiraGuiaPagamento()
													+ " , e as seguintes no mesmo dia dos meses subseq�entes");
								}
							} else {
								stringHtml = stringHtml.concat(" venc�vel no dia " + dadosRelatorioParcelameto.getDataVecimentoPrimeiraGuiaPagamento());
							}
						}
					}

					stringHtml = stringHtml.concat(". ");	
					
					String descricaoCustasJudiciaisParcelasDetalhe="";
					if (dadosRelatorioParcelameto.getQuantidadeParcelasSucumbencia() != null && !dadosRelatorioParcelameto.getQuantidadeParcelasSucumbencia().equals("")) {
						if (dadosRelatorioParcelameto.getIndicadorCobrancaParcelamento().equals("1")) {
							
							if (dadosRelatorioParcelameto.getIndicadorValorEntradaGuia().equals("1") 
											&&	dadosRelatorioParcelameto.getValorEntrada() != null 
											&& !dadosRelatorioParcelameto.getValorEntrada().equals("") 
											&& !dadosRelatorioParcelameto.getValorEntrada().equals("0,00")) {
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe
												.concat(" , e o saldo remanescente em "
																+ dadosRelatorioParcelameto.getQuantidadeParcelasSucumbencia());
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe.concat(" parcela(s), de igual valor, venc�vel a entrada no dia " + dadosRelatorioParcelameto.getDataVecimentoPrimeiraGuiaPagamento());
								// descricaoCustasJudiciaisParcelasDetalhe =
								// descricaoCustasJudiciaisParcelasDetalhe.concat(" e as demais parcelas ser�o lan�adas nas futuras contas de consumo. Eventual diferen�a entre os valores das presta��es e o valor total ser� cobrada na �ltima parcela");
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe
												.concat(" e as demais parcelas ser�o lan�adas nas futuras contas de consumo. ");
							} else {
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe.concat(", entrada que ser� lan�ada na pr�xima conta de consumo, ");
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe.concat(" e o saldo remanescente em " + dadosRelatorioParcelameto.getQuantidadeParcelasSucumbencia() + " parcela(s), ");
								// descricaoCustasJudiciaisParcelasDetalhe =
								// descricaoCustasJudiciaisParcelasDetalhe.concat(" de igual valor, parcelas essas que ser�o lan�adas nas futuras contas de consumo. Eventual diferen�a entre os valores das presta��es e o valor total ser� cobrada na �ltima parcela");
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe
												.concat(" de igual valor, parcelas essas que ser�o lan�adas nas futuras contas de consumo. ");
							}
					
						} else {

							if (dadosRelatorioParcelameto.getIndicadorValorEntradaGuia().equals("1") 
											&&	dadosRelatorioParcelameto.getValorEntrada() != null 
											&& !dadosRelatorioParcelameto.getValorEntrada().equals("") 
											&& !dadosRelatorioParcelameto.getValorEntrada().equals("0,00")) {
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe.concat(" , e o saldo remanescente em " + dadosRelatorioParcelameto.getQuantidadeParcelasSucumbencia());
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe.concat(" parcela(s), de igual valor, venc�vel a entrada no dia " + dadosRelatorioParcelameto.getDataVecimentoPrimeiraGuiaPagamento());
								// descricaoCustasJudiciaisParcelasDetalhe =
								// descricaoCustasJudiciaisParcelasDetalhe.concat(" e as demais no mesmo dia dos meses subsequentes. Eventual diferen�a entre os valores das presta��es e o valor total ser� cobrada na �ltima parcela");
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe
												.concat(" e as demais no mesmo dia dos meses subsequentes. ");
							} else {
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe.concat(" , e o saldo remanescente em " + dadosRelatorioParcelameto.getQuantidadeParcelasSucumbencia());
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe.concat(" parcela(s), de igual valor, venc�vel a entrada no dia " + dadosRelatorioParcelameto.getDataVecimentoPrimeiraGuiaPagamentoSucumbencia());
								// descricaoCustasJudiciaisParcelasDetalhe =
								// descricaoCustasJudiciaisParcelasDetalhe.concat(" e as demais no mesmo dia dos meses subsequentes. Eventual diferen�a entre os valores das presta��es e o valor total ser� cobrada na �ltima parcela");
								descricaoCustasJudiciaisParcelasDetalhe = descricaoCustasJudiciaisParcelasDetalhe
												.concat(" e as demais no mesmo dia dos meses subsequentes. ");

							}
						}
					}
					
					
					if (dadosRelatorioParcelameto.getValorTotalSucumbencia() != null) {
						stringHtml = stringHtml.concat("E a t�tulo de honor�rios advocat�cios, uma entrada de R$ " 
															+ dadosRelatorioParcelameto.getValorPrestacaoSucumbencia() + " (" 
															+ dadosRelatorioParcelameto.getValorPrestacaoSucumbenciaExtenso() + ") " 
										+ descricaoCustasJudiciaisParcelasDetalhe + ", j� cobrado na entrada.");
					}
					
								
					// 4 Paragrafo	
					stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 3. AS CUSTAS PROCESSUAIS. As custas processuais eventualmente em aberto dever�o ser quitadas pelo devedor diretamente junto � Vara da Fazenda P�blica da Comarca de Sorocaba, ");
					stringHtml = stringHtml.concat(" competindo-lhe ainda a comprova��o desse recolhimento nos autos judiciais.");											
				
		
					// 5 Paragrafo	
					stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 4. AS CONTAS FUTURAS. O pagamento das parcelas aqui aven�adas n�o desobriga o devedor de quitar, nos respectivos vencimentos, ");
					stringHtml = stringHtml.concat(" as futuras contas mensais de consumo de �gua no im�vel.");
					
					// 6 Paragrafo		
					stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 5. A INADIMPL�NCIA. O atraso ou n�o pagamento de qualquer parcela aven�ada neste acordo, independentemente de pr�vio aviso ou qualquer outra ");
					stringHtml = stringHtml.concat(" formalidade, ensejar� o vencimento autom�tico e antecipado da d�vida transacionada que, acrescida de multa de 2% (dois por cento) sobre o saldo em aberto, ");
					stringHtml = stringHtml.concat(" poder� ser exigida de imediato e de uma s� vez pelo SAAE, prosseguindo-se a execu��o judicial nesse sentido com a inclus�o do devedor no p�lo passivo do feito, ");
					stringHtml = stringHtml.concat(" na condi��o de respons�vel solid�rio pelo d�bito, se por ventura n�o for o propriet�rio do im�vel servido. O descumprimento, pelo devedor, ");
					stringHtml = stringHtml.concat(" das condi��es aven�adas neste parcelamento tambem ensejar�, independentemente de pr�vio aviso ou notifica��o, a imediata retirada do ramal distribuidor ");
					stringHtml = stringHtml.concat(" de �gua no pr�dio e recolhimento do hidr�metro pelo SAAE, sendo que o restabelecimento do servi�o somente ocorrer� ap�s o pagamento integral ");
					stringHtml = stringHtml.concat(" do saldo devedor em aberto e mediante novo pedido de liga��o de �gua.");
					
					// 7 Paragrafo		
					stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 6. A RESPONSABILIDADE TRIBUT�RIA. O devedor, na hip�tese de n�o ser o propriet�rio do im�vel (respons�vel tribut�rio por for�a do inciso II do artigo 121, ");
					stringHtml = stringHtml.concat(" do C�digo Tribut�rio Nacional), reconhece e assume nesta oportunidade sua condi��o de contribuinte, nos termos do inciso I do artigo retro citado, ");
					stringHtml = stringHtml.concat("  passando a responder solidariamente pela d�vida nessa qualidade. A confiss�o ora transacionada n�o surtir� efeitos relativamente ao propriet�rio do pr�dio devedor, ");
					stringHtml = stringHtml.concat(" e tampouco o eximir� de sua responsabilidade tribut�ria origin�ria.");
					
					// 8 Paragrafo						
					stringHtml = stringHtml.concat("<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 7. O FORO. Fica eleito o foro da comarca de Sorocaba para dirimir quaisquer quest�es decorrentes deste instrumento.");
					
					// 9 Paragrafo
					stringHtml = stringHtml
									.concat("<p><p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; E por estarem dessa forma justos e combinados, assinam este Termo em 03 (tr�s) vias de igual teor e forma, ao lado das testemunhas abaixo identificadas, para que surtam os efeitos de fato e direito desejados.");
				}
			}
		}

		stringHtml = stringHtml + "</span>";

		return stringHtml;

	}

}
