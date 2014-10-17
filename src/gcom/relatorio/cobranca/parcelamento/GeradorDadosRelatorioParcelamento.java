package gcom.relatorio.cobranca.parcelamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.FiltroIndicesAcrescimosImpontualidade;
import gcom.cobranca.IndicesAcrescimosImpontualidade;
import gcom.cobranca.bean.ParcelamentoRelatorioHelper;
import gcom.cobranca.parcelamento.FiltroParcelamento;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoSituacaoEspecial;
import gcom.cobranca.parcelamento.ParcelamentoTermoTestemunhas;
import gcom.fachada.Fachada;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.util.*;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * [UC0214] Efetuar Parcelamento de Débitos
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

		// coleção de beans do relatório
		List relatorioParcelamentosBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioParcelamentoResolucaoDiretoriaLayoutBean relatorioParcelamentoResolucaoDiretoriaLayoutBean = null;

		ParcelamentoRelatorioHelper parcelamentoRelatorioHelper = fachada.pesquisarParcelamentoRelatorio(parcelamento.getId());

		ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = parcelamento.getParcelamentoTermoTestemunhas();

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

		// Endereço Abreviado
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

		// Inicio do Período de Fornecimento dos Serviços
		String inicioPeriodoFornecimento = "";
		if(parcelamentoRelatorioHelper.getInicioPeriodoFornecimento() != null){
			inicioPeriodoFornecimento = parcelamentoRelatorioHelper.getInicioPeriodoFornecimento();
		}

		// Fim do Período de Fornecimento dos Serviços
		String fimPeriodoFornecimento = "";
		if(parcelamentoRelatorioHelper.getFimPeriodoFornecimento() != null){
			fimPeriodoFornecimento = parcelamentoRelatorioHelper.getFimPeriodoFornecimento();
		}

		// Indicador de Pessoa Física ou Jurídica
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

		// se a coleção de parâmetros da analise não for vazia
		if(parcelamentoRelatorioHelper != null){

			// Pega a Data Atual formatada da seguinte forma: dd de mês(por extenso) de aaaa
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
			if(parcelamento.getResolucaoDiretoria() != null){
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

			relatorioParcelamentoResolucaoDiretoriaLayoutBean = new RelatorioParcelamentoResolucaoDiretoriaLayoutBean(

			// Matrícula do Imóvel
							matriculaImovel,

							// Nome Cliente
							nomeCliente,

							// Endereço
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

							// Total Débitos
							totalDebitos,

							// Total Débitos Extenso
							totalDebitosExtenso,

							// Total Descontos
							totalDescontos,

							// Valor Entrada
							valorEntrada,

							// Número de Parcelas
							numeroParcelas,

							// Número de Parcelas Extenso
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
							// Inscrição
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

							// Endereço abreviado
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
							// Inicio Cobrança
							inicioCobranca,
							// Terminio Cobrança
							terminioCobranca,
							// Descrição Orgão expedidor
							descOrgaoExpRgCliente,
							// Cidade e Data Atual
							dataParcelamento,
							// Indicador de Pessoa Física ou Jurídica
							indicadorPessoaFisicaJuridica,
							// Nome da Testemunha 1
							nomeTestemunha1,
							// CPF da Testemunha 1
							cpfTestemunha1,
							// Nome da Testemunha 2
							nomeTestemunha2,
							// CPF da Testemunha 2
							cpfTestemunha2,
							// Profissão Cliente
							profissaoCliente,
							// Junta Comercial Empresa - JUCER
							juntaComercialEmpresa,
							// Inscricao Estadual Empresa
							inscricaoEstadualEmpresa,
							// Cidade do Imóvel
							cidadeImovelCadastrado, totalNegociado, totalNegociadoExtenso);


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

			// adiciona o bean a coleção
			relatorioParcelamentosBeans.add(relatorioParcelamentoResolucaoDiretoriaLayoutBean);

		}

		// retorna o relatório gerado
		return relatorioParcelamentosBeans;
	}

	@Override
	public List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> gerarDados(Collection<Parcelamento> colecaoParcelamento,
					int idFuncionalidadeIniciada) throws GeradorRelatorioParcelamentoException{

		return null;
	}

}
