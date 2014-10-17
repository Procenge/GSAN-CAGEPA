/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.CartaHidrometroModelo1Helper;
import gcom.atendimentopublico.ordemservico.bean.OrdemServicoFiscalizacaoModelo2Helper;
import gcom.cobranca.bean.EmitirOrdemCorteCobrancaHelper;
import gcom.cobranca.bean.EmitirOrdemFiscalizacaoCorteCobrancaHelper;
import gcom.cobranca.bean.EmitirOrdemFiscalizacaoSupressaoCobrancaHelper;
import gcom.faturamento.conta.Conta;
import gcom.relatorio.cobranca.RelatorioDadosContaDetailBean;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Essa classe representa um bean de preenchimendo do relatório de ordem de serviço
 * 
 * @author gmatos
 */
public class DadosRelatorioOrdemServico {

	private static final int TAMANHO_NUMERO_OS = 6;

	private static final int RELATORIO_DETAIL_3_COLUNAS = 3;

	private String numeroPagina1;

	private String numeroPagina2;

	private String numeroOS;

	private String numeroRA;

	private String solicitacao;

	private String prioridade;

	private String origem;

	private String prazo;

	private String endereco;

	private String numero;

	private String complemento;

	private String me;

	private String bairro;

	private String referencia;

	private String quarteirao;

	private String unidade;

	private String lote;

	private String quadra;

	private String inscricao;

	private String solicitante;

	private String telefone;

	private String proprietario;

	private String ligacao;

	private String grupo;

	private String enderecoEntrega;

	private String tipoFaturamento;

	private String tipoLigacao;

	private String observacao;

	private String tipoLigacao2;

	private String enderecoEmpresa;

	private String emailEmpresa;

	private String telefoneEmpresa;

	private String siteEmpresa;
	
	private String localidadeEmpresa;

	private String dataSolicitacao;

	private String horaSolicitacao;

	private String idServico;

	private String descricaoServico;

	private String idSolicitacao;

	private String descricaoSolicitacao;

	private String dataRoteiroProgramacao;

	private String idEquipe;

	private String nomeEquipe;

	private String tipoRelacao;

	// Relatorio Padrão
	private String idOrdemServico;

	private String dataGeracao;

	private String inscricaoImovel;

	private String idImovel;

	private String categoriaQtdeEconomias;

	private String unidadeGeracao;

	private String unidadeAtendimento;

	private String situacaoAguaEsgoto;

	private String esgotoFixo;

	private String pavimentoRua;

	private String pavimentoCalcada;

	private String meio;

	private String nomeAtendente;

	private String idAtendente;

	private String pontoReferencia;

	private String servicoSolicitado;

	private String localOcorrencia;

	private String previsao;

	private String observacaoRA;

	private String observacaoOS;

	private String idRA;

	private String tipoSolicitanteUsuario;

	private String tipoSolicitanteEmpresa;

	private String especificacao;

	private String numeroHidrometro;

	private String marcaHidrometro;

	private String imagem;

	private String equipe;

	// Relatorio Instalacao de Hidrometro
	private String setorHidraulico;

	private String situacao;

	private String categoria;

	private String categoria2;

	// Relatorio Substituicao de Hidrometro
	private String codigoLeitura;

	private String mesMedido1;

	private String mesMedido2;

	private String mesMedido3;

	private String qtdMedido1;

	private String qtdMedido2;

	private String qtdMedido3;

	private String mediaMedido;

	private String mesFaturamento1;

	private String mesFaturamento2;

	private String mesFaturamento3;

	private String qtdFaturado1;

	private String qtdFaturado2;

	private String qtdFaturado3;

	private String mediaFaturado;

	private String agenteAssociado;

	private String qtdEconomiasResidencial;

	// Relatorio Verificação de Ligação de Agua CCI
	private String quantidadeFaturasDebito;

	private String debitosValorTotal;

	// Inicio de campos utilizados no Relatório Ordem de Corte, Relatorio Fiscalização das Ordens de
	// Corte, Ordens de Supressão

	// Primeira Aba

	private JRBeanCollectionDataSource arrayJRDetail1;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail1Bean;

	private ArrayList<Object> arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean;

	private String inscricao1;

	private String matricula1;

	private String roteiro1;

	private String hm1;

	private String programa1;

	private String cliente1;

	private String endereco1;

	private String bairro1;

	private String numero1;

	private String dataComparecimento1;

	private String dataDebitoAnterior1;

	private String debitosAnteriores1;

	private String valorTotalDebito1;

	private String representacaoNumericaCodBarraFormatada1;

	private String representacaoNumericaCodBarraSemDigito1;

	private String dataPermanenciaRegistro1;

	private String tipoRelacao1;

	// Segunda Aba

	private JRBeanCollectionDataSource arrayJRDetail2;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail2Bean;

	private ArrayList<Object> arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean;

	private String inscricao2;

	private String matricula2;

	private String roteiro2;

	private String hm2;

	private String programa2;

	private String cliente2;

	private String endereco2;

	private String bairro2;

	private String numero2;

	private String dataComparecimento2;

	private String dataDebitoAnterior2;

	private String debitosAnteriores2;

	private String valorTotalDebito2;

	private String representacaoNumericaCodBarraFormatada2;

	private String representacaoNumericaCodBarraSemDigito2;

	private String dataPermanenciaRegistro2;

	private String matricula;

	private String nomeCliente;

	private String hidrometro;

	private String acaoCobranca;

	private String roteiro;

	private String horaImpressao;

	private String dataImpressao;

	private String dataComparecimento;

	private String dataDebitoAnterior;

	private BigDecimal valorDebitosAnteriores;

	private BigDecimal valorTotal;

	private Collection<String> mesAno;

	private Collection<String> vencimento;

	private Collection<BigDecimal> valor;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String ra1;

	private String codDescricaoServico1;

	private String ra2;

	private String codDescricaoServico2;

	private String tipoServico1;

	private String tipoServico2;

	private String tipoRelacao2;

	// Supressao
	private String dataPermanenciaRegistro;

	private String valorTotalDebito;

	private String imagemFaltaAgua;

	// Fim de campos utilizados no Relatório Ordem de Corte, Relatorio Fiscalização das Ordens de
	// Corte, Ordens de Supressão

	private JRBeanCollectionDataSource beansDetalheEstrutura;

	private ArrayList arrayRelatorioOrdemServicoEstruturaDetailBean;

	// Campos Utilizados na geração de relatórios de Ordem de Serviço Estrutura
	private String exibirRodapePadrao;

	private String exibirCodigoBarras;

	// Carta Hidrômetro Modelo1
	private String ordemServico;

	// Ordem Servico Fiscalizacao Modelo2
	private Integer cobrancaSituacao;

	private String local;

	private String setor;

	private String sublote;

	private String controle;

	private String consumidor;

	private String dataEmissao;

	private String enderecoLocalidade;

	private String clienteCPFCNPJ;

	private String clienteRG;

	private String clienteNome;

	private String clienteTelefone;

	private String economiasQuantidade;

	private String economiasTipo;

	private String leituraAtualHidrometro;

	private String numeroLacre;

	private String situacaoLigacaoAgua;

	private String situacaoLigacaoEsgoto;

	private String dataCorte;

	private String dataSuspensao;

	private Collection<Conta> contasVencidas;

	private String quantidadeContasVencidas;

	private String valorContasVencidas;

	private JRBeanCollectionDataSource colecaoDadosUltimosConsumosBean1;

	private JRBeanCollectionDataSource colecaoDadosUltimosConsumosBean2;

	private JRBeanCollectionDataSource colecaoDadosUltimosConsumosBean3;

	private JRBeanCollectionDataSource colecaoDadosUltimosConsumosBean4;

	private ArrayList<DadosUltimosConsumosHelper> arrayDadosUltimosConsumosBean1;

	private ArrayList<DadosUltimosConsumosHelper> arrayDadosUltimosConsumosBean2;

	private ArrayList<DadosUltimosConsumosHelper> arrayDadosUltimosConsumosBean3;

	private ArrayList<DadosUltimosConsumosHelper> arrayDadosUltimosConsumosBean4;

	// ************************* Construtor padrão da classe
	public DadosRelatorioOrdemServico() {

	}

	// ************************* Construtor para o Relatorio Ordem de Corte
	// ***********************************

	/**
	 * @author isilva
	 * @param emitirAvisoCobrancaHelper1
	 * @param emitirAvisoCobrancaHelper2
	 */
	public DadosRelatorioOrdemServico(EmitirOrdemCorteCobrancaHelper emitirAvisoCobrancaHelper1,
										EmitirOrdemCorteCobrancaHelper emitirAvisoCobrancaHelper2) {

		if(emitirAvisoCobrancaHelper1 != null){
			this.inscricao1 = emitirAvisoCobrancaHelper1.getInscricao() != null ? emitirAvisoCobrancaHelper1.getInscricao() : "";
			this.matricula1 = emitirAvisoCobrancaHelper1.getMatricula() != null ? emitirAvisoCobrancaHelper1.getMatricula() : "";
			this.hm1 = emitirAvisoCobrancaHelper1.getHidrometro() != null ? emitirAvisoCobrancaHelper1.getHidrometro() : "";
			this.numero1 = emitirAvisoCobrancaHelper1.getNumero() != null ? emitirAvisoCobrancaHelper1.getNumero().trim() : "000000".trim();
			this.ra1 = emitirAvisoCobrancaHelper1.getRa() != null ? emitirAvisoCobrancaHelper1.getRa() : "";
			this.codDescricaoServico1 = emitirAvisoCobrancaHelper1.getCodDescricaoServico() != null ? emitirAvisoCobrancaHelper1
							.getCodDescricaoServico() : "";
			this.cliente1 = emitirAvisoCobrancaHelper1.getNomeCliente() != null ? emitirAvisoCobrancaHelper1.getNomeCliente() : "";
			this.endereco1 = emitirAvisoCobrancaHelper1.getEndereco() != null ? emitirAvisoCobrancaHelper1.getEndereco() : "";
			this.bairro1 = emitirAvisoCobrancaHelper1.getBairro() != null ? emitirAvisoCobrancaHelper1.getBairro() : "";
			this.dataDebitoAnterior1 = emitirAvisoCobrancaHelper1.getDataDebitoAnterior() != null ? emitirAvisoCobrancaHelper1
							.getDataDebitoAnterior() : "";
			this.debitosAnteriores1 = emitirAvisoCobrancaHelper1.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirAvisoCobrancaHelper1.getValorDebitosAnteriores()) : "0,00";
			this.valorTotalDebito1 = emitirAvisoCobrancaHelper1.getValorTotal() != null ? Util.formatarMoedaReal(emitirAvisoCobrancaHelper1
							.getValorTotal()) : "0,00";
			this.representacaoNumericaCodBarraFormatada1 = emitirAvisoCobrancaHelper1.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirAvisoCobrancaHelper1
							.getRepresentacaoNumericaCodBarraFormatada().trim()
							: "000000".trim();
			this.representacaoNumericaCodBarraSemDigito1 = emitirAvisoCobrancaHelper1.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirAvisoCobrancaHelper1
							.getRepresentacaoNumericaCodBarraSemDigito().trim()
							: "000000".trim();
			this.arrayRelatorioOrdemCorteDetail1Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail1Bean.addAll(this.gerarDetail(emitirAvisoCobrancaHelper1.getMesAno(),
							emitirAvisoCobrancaHelper1.getVencimento(), emitirAvisoCobrancaHelper1.getValor()));
			this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail1Bean);
			this.dataPermanenciaRegistro1 = emitirAvisoCobrancaHelper1.getDataImpressao() != null ? emitirAvisoCobrancaHelper1
							.getDataImpressao() : "";
			this.numeroPagina1 = emitirAvisoCobrancaHelper1.getNumeroPagina() != null ? emitirAvisoCobrancaHelper1.getNumeroPagina() : "";
		}

		if(emitirAvisoCobrancaHelper2 != null){
			this.inscricao2 = emitirAvisoCobrancaHelper2.getInscricao() != null ? emitirAvisoCobrancaHelper2.getInscricao() : "";
			this.matricula2 = emitirAvisoCobrancaHelper2.getMatricula() != null ? emitirAvisoCobrancaHelper2.getMatricula() : "";
			this.hm2 = emitirAvisoCobrancaHelper2.getHidrometro() != null ? emitirAvisoCobrancaHelper2.getHidrometro() : "";
			this.numero2 = emitirAvisoCobrancaHelper2.getNumero() != null ? emitirAvisoCobrancaHelper2.getNumero().trim() : "000000".trim();
			this.ra2 = emitirAvisoCobrancaHelper2.getRa() != null ? emitirAvisoCobrancaHelper2.getRa() : "";
			this.codDescricaoServico2 = emitirAvisoCobrancaHelper2.getCodDescricaoServico() != null ? emitirAvisoCobrancaHelper2
							.getCodDescricaoServico() : "";
			this.cliente2 = emitirAvisoCobrancaHelper2.getNomeCliente() != null ? emitirAvisoCobrancaHelper2.getNomeCliente() : "";
			this.endereco2 = emitirAvisoCobrancaHelper2.getEndereco() != null ? emitirAvisoCobrancaHelper2.getEndereco() : "";
			this.bairro2 = emitirAvisoCobrancaHelper2.getBairro() != null ? emitirAvisoCobrancaHelper2.getBairro() : "";
			this.dataDebitoAnterior2 = emitirAvisoCobrancaHelper2.getDataDebitoAnterior() != null ? emitirAvisoCobrancaHelper2
							.getDataDebitoAnterior() : "";
			this.debitosAnteriores2 = emitirAvisoCobrancaHelper2.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirAvisoCobrancaHelper2.getValorDebitosAnteriores()) : "0,00";
			this.valorTotalDebito2 = emitirAvisoCobrancaHelper2.getValorTotal() != null ? Util.formatarMoedaReal(emitirAvisoCobrancaHelper2
							.getValorTotal()) : "0,00";
			this.representacaoNumericaCodBarraFormatada2 = emitirAvisoCobrancaHelper2.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirAvisoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraFormatada().trim()
							: "000000".trim();
			this.representacaoNumericaCodBarraSemDigito2 = emitirAvisoCobrancaHelper2.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirAvisoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraSemDigito().trim()
							: "000000".trim();
			this.arrayRelatorioOrdemCorteDetail2Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail2Bean.addAll(this.gerarDetail(emitirAvisoCobrancaHelper2.getMesAno(),
							emitirAvisoCobrancaHelper2.getVencimento(), emitirAvisoCobrancaHelper2.getValor()));
			this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail2Bean);
			this.dataPermanenciaRegistro2 = emitirAvisoCobrancaHelper2.getDataImpressao() != null ? emitirAvisoCobrancaHelper2
							.getDataImpressao() : "";
			this.numeroPagina2 = emitirAvisoCobrancaHelper2.getNumeroPagina() != null ? emitirAvisoCobrancaHelper2.getNumeroPagina() : "";
		}
	}

	// ********************************************************************************************************

	// ************************* Construtor para o Relatorio Fiscalização das Ordens de Cortes
	// ****************

	/**
	 * @author isilva
	 * @param emitirOrdemFiscalizacaoCorteCobrancaHelper1
	 * @param emitirOrdemFiscalizacaoCorteCobrancaHelper2
	 */
	public DadosRelatorioOrdemServico(EmitirOrdemFiscalizacaoCorteCobrancaHelper emitirOrdemFiscalizacaoCorteCobrancaHelper1,
										EmitirOrdemFiscalizacaoCorteCobrancaHelper emitirOrdemFiscalizacaoCorteCobrancaHelper2) {

		if(emitirOrdemFiscalizacaoCorteCobrancaHelper1 != null){
			this.inscricao1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getInscricao() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getInscricao()
							: "";
			this.matricula1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getMatricula() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getMatricula()
							: "";
			this.hm1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getHidrometro() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getHidrometro() : "";
			this.numero1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getNumero() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getNumero() : "";
			this.ra1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getRa() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1.getRa()
							: "";
			this.codDescricaoServico1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getCodDescricaoServico() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getCodDescricaoServico()
							: "";
			this.cliente1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getNomeCliente() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getNomeCliente()
							: "";
			this.endereco1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getEndereco() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getEndereco()
							: "";
			this.bairro1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getBairro() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getBairro() : "";
			this.dataDebitoAnterior1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getDataDebitoAnterior() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getDataDebitoAnterior()
							: "";
			this.debitosAnteriores1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirOrdemFiscalizacaoCorteCobrancaHelper1.getValorDebitosAnteriores()) : "0,00";
			this.valorTotalDebito1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getValorTotal() != null ? Util
							.formatarMoedaReal(emitirOrdemFiscalizacaoCorteCobrancaHelper1.getValorTotal()) : "0,00";
			this.representacaoNumericaCodBarraFormatada1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getRepresentacaoNumericaCodBarraFormatada() : "000000";
			this.representacaoNumericaCodBarraSemDigito1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getRepresentacaoNumericaCodBarraSemDigito() : "000000";
			this.arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean = new ArrayList<Object>();
			this.arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean.addAll(this.gerarDetail(emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getMesAno(), emitirOrdemFiscalizacaoCorteCobrancaHelper1.getVencimento(),
							emitirOrdemFiscalizacaoCorteCobrancaHelper1.getValor()));
			this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean);
			this.dataPermanenciaRegistro1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getDataImpressao() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getDataImpressao()
							: "";
			this.numeroPagina1 = emitirOrdemFiscalizacaoCorteCobrancaHelper1.getNumeroPagina() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper1
							.getNumeroPagina()
							: "";
		}

		if(emitirOrdemFiscalizacaoCorteCobrancaHelper2 != null){
			this.inscricao2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getInscricao() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getInscricao()
							: "";
			this.matricula2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getMatricula() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getMatricula()
							: "";
			this.hm2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getHidrometro() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getHidrometro() : "";
			this.numero2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getNumero() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getNumero() : "";
			this.ra2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getRa() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2.getRa()
							: "";
			this.codDescricaoServico2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getCodDescricaoServico() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getCodDescricaoServico()
							: "";
			this.cliente2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getNomeCliente() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getNomeCliente()
							: "";
			this.endereco2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getEndereco() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getEndereco()
							: "";
			this.bairro2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getBairro() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getBairro() : "";
			this.dataDebitoAnterior2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getDataDebitoAnterior() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getDataDebitoAnterior()
							: "";
			this.debitosAnteriores2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirOrdemFiscalizacaoCorteCobrancaHelper2.getValorDebitosAnteriores()) : "0,00";
			this.valorTotalDebito2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getValorTotal() != null ? Util
							.formatarMoedaReal(emitirOrdemFiscalizacaoCorteCobrancaHelper2.getValorTotal()) : "0,00";
			this.representacaoNumericaCodBarraFormatada2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getRepresentacaoNumericaCodBarraFormatada() : "000000";
			this.representacaoNumericaCodBarraSemDigito2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getRepresentacaoNumericaCodBarraSemDigito() : "000000";
			this.arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean = new ArrayList<Object>();
			this.arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean.addAll(this.gerarDetail(emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getMesAno(), emitirOrdemFiscalizacaoCorteCobrancaHelper2.getVencimento(),
							emitirOrdemFiscalizacaoCorteCobrancaHelper2.getValor()));
			this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean);
			this.dataPermanenciaRegistro2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getDataImpressao() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getDataImpressao()
							: "";
			this.numeroPagina2 = emitirOrdemFiscalizacaoCorteCobrancaHelper2.getNumeroPagina() != null ? emitirOrdemFiscalizacaoCorteCobrancaHelper2
							.getNumeroPagina()
							: "";
		}
	}

	// ********************************************************************************************************

	// relatorio ordens religacoes
	public DadosRelatorioOrdemServico(DadosRelatorioReligacoesHelper bean1, DadosRelatorioReligacoesHelper bean2) {

		if(bean1 != null){
			this.numero1 = bean1.getNumeroOS();
			this.ra1 = bean1.getNumeroRA();
			this.matricula1 = bean1.getMatricula();
			this.inscricao1 = bean1.getInscricao();
			this.hm1 = bean1.getNumeroHidrometro();
			this.cliente1 = bean1.getCliente();
			this.endereco1 = bean1.getEndereco();
			this.bairro1 = bean1.getBairro();
			this.tipoLigacao = bean1.getTipoLigacao();
			this.categoria = bean1.getCategoria();
			this.tipoServico1 = bean1.getTipoServico();
			this.numeroPagina1 = bean1.getNumeroPagina() != null ? bean1.getNumeroPagina() : "";
			this.tipoRelacao1 = bean1.getTipoRelacao();
		}
		if(bean2 != null){
			this.numero2 = bean2.getNumeroOS();
			this.ra2 = bean2.getNumeroRA();
			this.matricula2 = bean2.getMatricula();
			this.inscricao2 = bean2.getInscricao();
			this.hm2 = bean2.getNumeroHidrometro();
			this.cliente2 = bean2.getCliente();
			this.endereco2 = bean2.getEndereco();
			this.bairro2 = bean2.getBairro();
			this.tipoLigacao2 = bean2.getTipoLigacao();
			this.setCategoria2(bean2.getCategoria());
			this.tipoServico2 = bean2.getTipoServico();
			this.numeroPagina2 = bean2.getNumeroPagina() != null ? bean2.getNumeroPagina() : "";
			this.tipoRelacao2 = bean2.getTipoRelacao();
		}
	}

	// ************************* Construtor para o Relatorio Fiscalização das Ordens de Supressão
	// ****************

	/**
	 * @author isilva
	 * @param emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
	 * @param emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
	 */
	public DadosRelatorioOrdemServico(EmitirOrdemFiscalizacaoSupressaoCobrancaHelper emitirOrdemFiscalizacaoSupressaoCobrancaHelper1,
										EmitirOrdemFiscalizacaoSupressaoCobrancaHelper emitirOrdemFiscalizacaoSupressaoCobrancaHelper2) {

		if(emitirOrdemFiscalizacaoSupressaoCobrancaHelper1 != null){
			this.inscricao1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getInscricao() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getInscricao()
							: "";
			this.matricula1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getMatricula() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getMatricula()
							: "";
			this.hm1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getHidrometro() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getHidrometro()
							: "";
			this.numero1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getNumero() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getNumero()
							: "";
			this.ra1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getRa() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getRa() : "";
			this.codDescricaoServico1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getCodDescricaoServico() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getCodDescricaoServico()
							: "";
			this.cliente1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getNomeCliente() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getNomeCliente()
							: "";
			this.endereco1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getEndereco() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getEndereco()
							: "";
			this.bairro1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getBairro() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getBairro()
							: "";
			this.representacaoNumericaCodBarraFormatada1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getRepresentacaoNumericaCodBarraFormatada() : "000000";
			this.representacaoNumericaCodBarraSemDigito1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getRepresentacaoNumericaCodBarraSemDigito() : "000000";
			this.dataPermanenciaRegistro1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getDataImpressao() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getDataImpressao()
							: "";
			this.numeroPagina1 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper1.getNumeroPagina() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper1
							.getNumeroPagina()
							: "";
		}

		if(emitirOrdemFiscalizacaoSupressaoCobrancaHelper2 != null){
			this.inscricao2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getInscricao() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getInscricao()
							: "";
			this.matricula2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getMatricula() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getMatricula()
							: "";
			this.hm2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getHidrometro() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getHidrometro()
							: "";
			this.numero2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getNumero() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getNumero()
							: "";
			this.ra2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getRa() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getRa() : "";
			this.codDescricaoServico2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getCodDescricaoServico() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getCodDescricaoServico()
							: "";
			this.cliente2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getNomeCliente() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getNomeCliente()
							: "";
			this.endereco2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getEndereco() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getEndereco()
							: "";
			this.bairro2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getBairro() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getBairro()
							: "";
			this.representacaoNumericaCodBarraFormatada2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraFormatada() : "000000";
			this.representacaoNumericaCodBarraSemDigito2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraSemDigito() : "000000";
			this.dataPermanenciaRegistro2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getDataImpressao() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getDataImpressao()
							: "";
			this.numeroPagina2 = emitirOrdemFiscalizacaoSupressaoCobrancaHelper2.getNumeroPagina() != null ? emitirOrdemFiscalizacaoSupressaoCobrancaHelper2
							.getNumeroPagina()
							: "";
		}
	}

	// ********************************************************************************************************

	// ********* Construtor para o Relatorio Ordem de Serviço de Fiscalização Modelo2 *******

	/**
	 * [UC0458] Imprimir Ordem de Serviço
	 * Gera os Relatórios das Ordens de Serviço de Fiscalização Modelo2
	 * 
	 * @author Carlos Chrystian
	 * @date 10/04/2013
	 */
	public DadosRelatorioOrdemServico(CartaHidrometroModelo1Helper cartaHidrometroModelo1Helper) {

		if(!Util.isVazioOuBranco(cartaHidrometroModelo1Helper)){

			this.clienteNome = cartaHidrometroModelo1Helper.getClienteNome() != null ? cartaHidrometroModelo1Helper.getClienteNome() : "";
			this.endereco = cartaHidrometroModelo1Helper.getEndereco() != null ? cartaHidrometroModelo1Helper.getEndereco() : "";
			this.matricula = cartaHidrometroModelo1Helper.getMatricula() != null ? cartaHidrometroModelo1Helper.getMatricula() : "";
			this.ordemServico = cartaHidrometroModelo1Helper.getOrdemServico() != null ? cartaHidrometroModelo1Helper.getOrdemServico()
							: "";
		}
	}

	// ********************************************************************************************************

	// ********* Construtor para o Relatorio Ordem de Serviço de Fiscalização Modelo2 *******

	/**
	 * [UC0458] Imprimir Ordem de Serviço
	 * Gera os Relatórios das Ordens de Serviço de Fiscalização Modelo2
	 * 
	 * @author Carlos Chrystian
	 * @date 10/04/2013
	 */
	public DadosRelatorioOrdemServico(OrdemServicoFiscalizacaoModelo2Helper ordemServicoFiscalizacaoModelo2Helper) {

		if(!Util.isVazioOuBranco(ordemServicoFiscalizacaoModelo2Helper)){
			this.cobrancaSituacao = ordemServicoFiscalizacaoModelo2Helper.getCobrancaSituacao() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getCobrancaSituacao() : 0;
			this.local = ordemServicoFiscalizacaoModelo2Helper.getLocal() != null ? ordemServicoFiscalizacaoModelo2Helper.getLocal() : "";
			this.matricula = ordemServicoFiscalizacaoModelo2Helper.getMatricula() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getMatricula() : "";
			this.setor = ordemServicoFiscalizacaoModelo2Helper.getSetor() != null ? ordemServicoFiscalizacaoModelo2Helper.getSetor() : "";
			this.quadra = ordemServicoFiscalizacaoModelo2Helper.getQuadra() != null ? ordemServicoFiscalizacaoModelo2Helper.getQuadra()
							: "";
			this.lote = ordemServicoFiscalizacaoModelo2Helper.getLote() != null ? ordemServicoFiscalizacaoModelo2Helper.getLote() : "";
			this.sublote = ordemServicoFiscalizacaoModelo2Helper.getSublote() != null ? ordemServicoFiscalizacaoModelo2Helper.getSublote()
							: "";
			this.controle = ordemServicoFiscalizacaoModelo2Helper.getControle() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getControle() : "";
			this.consumidor = ordemServicoFiscalizacaoModelo2Helper.getConsumidor() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getConsumidor() : "";
			this.dataEmissao = ordemServicoFiscalizacaoModelo2Helper.getDataEmissao() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getDataEmissao() : "";
			this.enderecoLocalidade = ordemServicoFiscalizacaoModelo2Helper.getEnderecoLocalidade() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getEnderecoLocalidade() : "";
			this.clienteCPFCNPJ = ordemServicoFiscalizacaoModelo2Helper.getClienteCPFCNPJ() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getClienteCPFCNPJ() : "";
			this.clienteRG = ordemServicoFiscalizacaoModelo2Helper.getClienteRG() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getClienteRG() : "";
			this.clienteNome = ordemServicoFiscalizacaoModelo2Helper.getClienteNome() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getClienteNome() : "";
			this.clienteTelefone = ordemServicoFiscalizacaoModelo2Helper.getClienteTelefone() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getClienteTelefone() : "";
			this.economiasQuantidade = ordemServicoFiscalizacaoModelo2Helper.getEconomiasQuantidade() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getEconomiasQuantidade() : "";
			this.economiasTipo = ordemServicoFiscalizacaoModelo2Helper.getEconomiasTipo() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getEconomiasTipo() : "";
			this.numeroHidrometro = ordemServicoFiscalizacaoModelo2Helper.getNumeroHidrometro() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getNumeroHidrometro() : "";
			this.leituraAtualHidrometro = ordemServicoFiscalizacaoModelo2Helper.getLeituraAtualHidrometro() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getLeituraAtualHidrometro() : "";
			this.numeroLacre = ordemServicoFiscalizacaoModelo2Helper.getNumeroLacre() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getNumeroLacre() : "";
			this.situacaoLigacaoAgua = ordemServicoFiscalizacaoModelo2Helper.getSituacaoLigacaoAgua() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getSituacaoLigacaoAgua() : "";
			this.situacaoLigacaoEsgoto = ordemServicoFiscalizacaoModelo2Helper.getSituacaoLigacaoEsgoto() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getSituacaoLigacaoEsgoto() : "";
			this.dataCorte = ordemServicoFiscalizacaoModelo2Helper.getDataCorte() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getDataCorte() : "";
			this.dataSuspensao = ordemServicoFiscalizacaoModelo2Helper.getDataSuspensao() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getDataSuspensao() : "";

			this.quantidadeContasVencidas = ordemServicoFiscalizacaoModelo2Helper.getQuantidadeContasVencidas() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getQuantidadeContasVencidas() : "";
			this.valorContasVencidas = ordemServicoFiscalizacaoModelo2Helper.getValorContasVencidas() != null ? ordemServicoFiscalizacaoModelo2Helper
							.getValorContasVencidas() : "";

			this.arrayDadosUltimosConsumosBean1 = new ArrayList<DadosUltimosConsumosHelper>();
			this.arrayDadosUltimosConsumosBean1.addAll(ordemServicoFiscalizacaoModelo2Helper.getDadosUltimosConsumos1());

			this.arrayDadosUltimosConsumosBean2 = new ArrayList<DadosUltimosConsumosHelper>();
			this.arrayDadosUltimosConsumosBean2.addAll(ordemServicoFiscalizacaoModelo2Helper.getDadosUltimosConsumos2());

			this.arrayDadosUltimosConsumosBean3 = new ArrayList<DadosUltimosConsumosHelper>();
			this.arrayDadosUltimosConsumosBean3.addAll(ordemServicoFiscalizacaoModelo2Helper.getDadosUltimosConsumos3());

			this.arrayDadosUltimosConsumosBean4 = new ArrayList<DadosUltimosConsumosHelper>();
			this.arrayDadosUltimosConsumosBean4.addAll(ordemServicoFiscalizacaoModelo2Helper.getDadosUltimosConsumos4());

			this.colecaoDadosUltimosConsumosBean1 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean1);
			this.colecaoDadosUltimosConsumosBean2 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean2);
			this.colecaoDadosUltimosConsumosBean3 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean3);
			this.colecaoDadosUltimosConsumosBean4 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean4);
		}
	}

	// ********************************************************************************************************


	public void gerarDetail1(){

		this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.gerarDetail(getMesAno(), getVencimento(), getValor()));
	}

	/**
	 * @return the numeroPagina1
	 */
	public String getNumeroPagina1(){

		return numeroPagina1;
	}

	/**
	 * @param numeroPagina1
	 *            the numeroPagina1 to set
	 */
	public void setNumeroPagina1(String numeroPagina1){

		this.numeroPagina1 = numeroPagina1;
	}

	/**
	 * @return the numeroPagina2
	 */
	public String getNumeroPagina2(){

		return numeroPagina2;
	}

	/**
	 * @param numeroPagina2
	 *            the numeroPagina2 to set
	 */
	public void setNumeroPagina2(String numeroPagina2){

		this.numeroPagina2 = numeroPagina2;
	}

	public String getEquipe(){

		return equipe;
	}

	public void setEquipe(String equipe){

		this.equipe = equipe;
	}

	/**
	 * @return the numeroOS
	 */
	public String getNumeroOS(){

		return numeroOS;
	}

	/**
	 * @param numeroOS
	 *            the numeroOS to set
	 */
	public void setNumeroOS(String numeroOS){

		if(numeroOS != null){
			numeroOS = Util.formatarParaCodigoBarrasInt2Of5(numeroOS);
		}
		this.numeroOS = numeroOS;
	}

	public String getSolicitacao(){

		return solicitacao;
	}

	public void setSolicitacao(String solicitacao){

		this.solicitacao = solicitacao;
	}

	public String getPrioridade(){

		return prioridade;
	}

	public void setPrioridade(String prioridade){

		this.prioridade = prioridade;
	}

	public String getOrigem(){

		return origem;
	}

	public void setOrigem(String origem){

		this.origem = origem;
	}

	public String getPrazo(){

		return prazo;
	}

	public void setPrazo(String prazo){

		this.prazo = prazo;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getNumero(){

		return numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	public String getComplemento(){

		return complemento;
	}

	public void setComplemento(String complemento){

		this.complemento = complemento;
	}

	public String getMe(){

		return me;
	}

	public void setMe(String me){

		this.me = me;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getQuarteirao(){

		return quarteirao;
	}

	public void setQuarteirao(String quarteirao){

		this.quarteirao = quarteirao;
	}

	public String getUnidade(){

		return unidade;
	}

	public void setUnidade(String unidade){

		this.unidade = unidade;
	}

	public String getLote(){

		return lote;
	}

	public void setLote(String lote){

		this.lote = lote;
	}

	public String getQuadra(){

		return quadra;
	}

	public void setQuadra(String quadra){

		this.quadra = quadra;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getSolicitante(){

		return solicitante;
	}

	public void setSolicitante(String solicitante){

		this.solicitante = solicitante;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getProprietario(){

		return proprietario;
	}

	public void setProprietario(String proprietario){

		this.proprietario = proprietario;
	}

	public String getLigacao(){

		return ligacao;
	}

	public void setLigacao(String ligacao){

		this.ligacao = ligacao;
	}

	public String getGrupo(){

		return grupo;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	public String getEnderecoEntrega(){

		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega){

		this.enderecoEntrega = enderecoEntrega;
	}

	public String getTipoFaturamento(){

		return tipoFaturamento;
	}

	public void setTipoFaturamento(String tipoFaturamento){

		this.tipoFaturamento = tipoFaturamento;
	}

	public String getTipoLigacao(){

		return tipoLigacao;
	}

	public void setTipoLigacao(String tipoLigacao){

		this.tipoLigacao = tipoLigacao;
	}

	public String getObservacao(){

		return observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getEnderecoEmpresa(){

		return enderecoEmpresa;
	}

	public void setEnderecoEmpresa(String enderecoEmpresa){

		this.enderecoEmpresa = enderecoEmpresa;
	}

	public String getEmailEmpresa(){

		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa){

		this.emailEmpresa = emailEmpresa;
	}

	public String getTelefoneEmpresa(){

		return telefoneEmpresa;
	}

	public void setTelefoneEmpresa(String telefoneEmpresa){

		this.telefoneEmpresa = telefoneEmpresa;
	}

	public String getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(String idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public String getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(String dataGeracao){

		this.dataGeracao = dataGeracao;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getCategoriaQtdeEconomias(){

		return categoriaQtdeEconomias;
	}

	public void setCategoriaQtdeEconomias(String categoriaQtdeEconomias){

		this.categoriaQtdeEconomias = categoriaQtdeEconomias;
	}

	public String getUnidadeGeracao(){

		return unidadeGeracao;
	}

	public void setUnidadeGeracao(String unidadeGeracao){

		this.unidadeGeracao = unidadeGeracao;
	}

	public String getUnidadeAtendimento(){

		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(String unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}

	public String getSituacaoAguaEsgoto(){

		return situacaoAguaEsgoto;
	}

	public void setSituacaoAguaEsgoto(String situacaoAguaEsgoto){

		this.situacaoAguaEsgoto = situacaoAguaEsgoto;
	}

	public String getEsgotoFixo(){

		return esgotoFixo;
	}

	public void setEsgotoFixo(String esgotoFixo){

		this.esgotoFixo = esgotoFixo;
	}

	public String getPavimentoRua(){

		return pavimentoRua;
	}

	public void setPavimentoRua(String pavimentoRua){

		this.pavimentoRua = pavimentoRua;
	}

	public String getPavimentoCalcada(){

		return pavimentoCalcada;
	}

	public void setPavimentoCalcada(String pavimentoCalcada){

		this.pavimentoCalcada = pavimentoCalcada;
	}

	public String getMeio(){

		return meio;
	}

	public void setMeio(String meio){

		this.meio = meio;
	}

	public String getNomeAtendente(){

		return nomeAtendente;
	}

	public void setNomeAtendente(String nomeAtendente){

		this.nomeAtendente = nomeAtendente;
	}

	public String getIdAtendente(){

		return idAtendente;
	}

	public void setIdAtendente(String idAtendente){

		this.idAtendente = idAtendente;
	}

	public String getPontoReferencia(){

		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia){

		this.pontoReferencia = pontoReferencia;
	}

	public String getServicoSolicitado(){

		return servicoSolicitado;
	}

	public void setServicoSolicitado(String servicoSolicitado){

		this.servicoSolicitado = servicoSolicitado;
	}

	public String getLocalOcorrencia(){

		return localOcorrencia;
	}

	public void setLocalOcorrencia(String localOcorrencia){

		this.localOcorrencia = localOcorrencia;
	}

	public String getPrevisao(){

		return previsao;
	}

	public void setPrevisao(String previsao){

		this.previsao = previsao;
	}

	public String getObservacaoRA(){

		return observacaoRA;
	}

	public void setObservacaoRA(String observacaoRA){

		this.observacaoRA = observacaoRA;
	}

	public String getObservacaoOS(){

		return observacaoOS;
	}

	public void setObservacaoOS(String observacaoOS){

		this.observacaoOS = observacaoOS;
	}

	public String getIdRA(){

		return idRA;
	}

	public void setIdRA(String idRA){

		this.idRA = idRA;
	}

	public String getTipoSolicitanteUsuario(){

		return tipoSolicitanteUsuario;
	}

	public void setTipoSolicitanteUsuario(String tipoSolicitanteUsuario){

		this.tipoSolicitanteUsuario = tipoSolicitanteUsuario;
	}

	public String getTipoSolicitanteEmpresa(){

		return tipoSolicitanteEmpresa;
	}

	public void setTipoSolicitanteEmpresa(String tipoSolicitanteEmpresa){

		this.tipoSolicitanteEmpresa = tipoSolicitanteEmpresa;
	}

	public String getEspecificacao(){

		return especificacao;
	}

	public void setEspecificacao(String especificacao){

		this.especificacao = especificacao;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}


	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getMarcaHidrometro(){

		return marcaHidrometro;
	}

	public void setMarcaHidrometro(String marcaHidrometro){

		this.marcaHidrometro = marcaHidrometro;
	}
	public String getImagem(){

		return imagem;
	}

	public void setImagem(String imagem){

		this.imagem = imagem;
	}

	public String getDataSolicitacao(){

		return dataSolicitacao;
	}

	public void setDataSolicitacao(String dataSolicitacao){

		this.dataSolicitacao = dataSolicitacao;
	}

	public String getHoraSolicitacao(){

		return horaSolicitacao;
	}

	public void setHoraSolicitacao(String horaSolicitacao){

		this.horaSolicitacao = horaSolicitacao;
	}

	public String getIdServico(){

		return idServico;
	}

	public void setIdServico(String idServico){

		this.idServico = idServico;
	}

	public String getDescricaoServico(){

		return descricaoServico;
	}

	public void setDescricaoServico(String descricaoServico){

		this.descricaoServico = descricaoServico;
	}

	public String getIdSolicitacao(){

		return idSolicitacao;
	}

	public void setIdSolicitacao(String idSolicitacao){

		this.idSolicitacao = idSolicitacao;
	}

	public String getDescricaoSolicitacao(){

		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao){

		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public String getDataRoteiroProgramacao(){

		return dataRoteiroProgramacao;
	}

	public void setDataRoteiroProgramacao(String dataRoteiroProgramacao){

		this.dataRoteiroProgramacao = dataRoteiroProgramacao;
	}

	public String getIdEquipe(){

		return idEquipe;
	}

	public void setIdEquipe(String idEquipe){

		this.idEquipe = idEquipe;
	}

	public String getNomeEquipe(){

		return nomeEquipe;
	}

	public void setNomeEquipe(String nomeEquipe){

		this.nomeEquipe = nomeEquipe;
	}

	public String getSetorHidraulico(){

		return setorHidraulico;
	}

	public void setSetorHidraulico(String setorHidraulico){

		this.setorHidraulico = setorHidraulico;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getCodigoLeitura(){

		return codigoLeitura;
	}

	public void setCodigoLeitura(String codigoLeitura){

		this.codigoLeitura = codigoLeitura;
	}

	public String getMesMedido1(){

		return mesMedido1;
	}

	public void setMesMedido1(String mesMedido1){

		this.mesMedido1 = mesMedido1;
	}

	public String getMesMedido2(){

		return mesMedido2;
	}

	public void setMesMedido2(String mesMedido2){

		this.mesMedido2 = mesMedido2;
	}

	public String getMesMedido3(){

		return mesMedido3;
	}

	public void setMesMedido3(String mesMedido3){

		this.mesMedido3 = mesMedido3;
	}

	public String getQtdMedido1(){

		return qtdMedido1;
	}

	public void setQtdMedido1(String qtdMedido1){

		this.qtdMedido1 = qtdMedido1;
	}

	public String getQtdMedido2(){

		return qtdMedido2;
	}

	public void setQtdMedido2(String qtdMedido2){

		this.qtdMedido2 = qtdMedido2;
	}

	public String getQtdMedido3(){

		return qtdMedido3;
	}

	public void setQtdMedido3(String qtdMedido3){

		this.qtdMedido3 = qtdMedido3;
	}

	public String getMediaMedido(){

		return mediaMedido;
	}

	public void setMediaMedido(String mediaMedido){

		this.mediaMedido = mediaMedido;
	}

	public String getQtdFaturado1(){

		return qtdFaturado1;
	}

	public void setQtdFaturado1(String qtdFaturado1){

		this.qtdFaturado1 = qtdFaturado1;
	}

	public String getQtdFaturado2(){

		return qtdFaturado2;
	}

	public void setQtdFaturado2(String qtdFaturado2){

		this.qtdFaturado2 = qtdFaturado2;
	}

	public String getQtdFaturado3(){

		return qtdFaturado3;
	}

	public void setQtdFaturado3(String qtdFaturado3){

		this.qtdFaturado3 = qtdFaturado3;
	}

	public String getMediaFaturado(){

		return mediaFaturado;
	}

	public void setMediaFaturado(String mediaFaturado){

		this.mediaFaturado = mediaFaturado;
	}

	public String getMesFaturamento1(){

		return mesFaturamento1;
	}

	public void setMesFaturamento1(String mesFaturamento1){

		this.mesFaturamento1 = mesFaturamento1;
	}

	public String getMesFaturamento2(){

		return mesFaturamento2;
	}

	public void setMesFaturamento2(String mesFaturamento2){

		this.mesFaturamento2 = mesFaturamento2;
	}

	public String getMesFaturamento3(){

		return mesFaturamento3;
	}

	public void setMesFaturamento3(String mesFaturamento3){

		this.mesFaturamento3 = mesFaturamento3;
	}

	public String getDebitosValorTotal(){

		return debitosValorTotal;
	}

	public void setDebitosValorTotal(String debitosValorTotal){

		this.debitosValorTotal = debitosValorTotal;
	}

	public String getQuantidadeFaturasDebito(){

		return quantidadeFaturasDebito;
	}

	public void setQuantidadeFaturasDebito(String quantidadeFaturasDebito){

		this.quantidadeFaturasDebito = quantidadeFaturasDebito;
	}

	public String getAgenteAssociado(){

		return agenteAssociado;
	}

	public void setAgenteAssociado(String agenteAssociado){

		this.agenteAssociado = agenteAssociado;
	}

	/**
	 * @return the arrayJRDetail1
	 */
	public JRBeanCollectionDataSource getArrayJRDetail1(){

		return arrayJRDetail1;
	}

	/**
	 * @param arrayJRDetail1
	 *            the arrayJRDetail1 to set
	 */
	public void setArrayJRDetail1(JRBeanCollectionDataSource arrayJRDetail1){

		this.arrayJRDetail1 = arrayJRDetail1;
	}

	/**
	 * @return the arrayRelatorioOrdemCorteDetail1Bean
	 */
	public ArrayList<Object> getArrayRelatorioOrdemCorteDetail1Bean(){

		return arrayRelatorioOrdemCorteDetail1Bean;
	}

	/**
	 * @param arrayRelatorioOrdemCorteDetail1Bean
	 *            the arrayRelatorioOrdemCorteDetail1Bean to set
	 */
	public void setArrayRelatorioOrdemCorteDetail1Bean(ArrayList<Object> arrayRelatorioOrdemCorteDetail1Bean){

		this.arrayRelatorioOrdemCorteDetail1Bean = arrayRelatorioOrdemCorteDetail1Bean;
	}

	/**
	 * @return the inscricao1
	 */
	public String getInscricao1(){

		return inscricao1;
	}

	/**
	 * @param inscricao1
	 *            the inscricao1 to set
	 */
	public void setInscricao1(String inscricao1){

		this.inscricao1 = inscricao1;
	}

	/**
	 * @return the matricula1
	 */
	public String getMatricula1(){

		return matricula1;
	}

	/**
	 * @param matricula1
	 *            the matricula1 to set
	 */
	public void setMatricula1(String matricula1){

		this.matricula1 = matricula1;
	}

	/**
	 * @return the roteiro1
	 */
	public String getRoteiro1(){

		return roteiro1;
	}

	/**
	 * @param roteiro1
	 *            the roteiro1 to set
	 */
	public void setRoteiro1(String roteiro1){

		this.roteiro1 = roteiro1;
	}

	/**
	 * @return the hm1
	 */
	public String getHm1(){

		return hm1;
	}

	/**
	 * @param hm1
	 *            the hm1 to set
	 */
	public void setHm1(String hm1){

		this.hm1 = hm1;
	}

	/**
	 * @return the programa1
	 */
	public String getPrograma1(){

		return programa1;
	}

	/**
	 * @param programa1
	 *            the programa1 to set
	 */
	public void setPrograma1(String programa1){

		this.programa1 = programa1;
	}

	/**
	 * @return the cliente1
	 */
	public String getCliente1(){

		return cliente1;
	}

	/**
	 * @param cliente1
	 *            the cliente1 to set
	 */
	public void setCliente1(String cliente1){

		this.cliente1 = cliente1;
	}

	/**
	 * @return the endereco1
	 */
	public String getEndereco1(){

		return endereco1;
	}

	/**
	 * @param endereco1
	 *            the endereco1 to set
	 */
	public void setEndereco1(String endereco1){

		this.endereco1 = endereco1;
	}

	/**
	 * @return the bairro1
	 */
	public String getBairro1(){

		return bairro1;
	}

	/**
	 * @param bairro1
	 *            the bairro1 to set
	 */
	public void setBairro1(String bairro1){

		this.bairro1 = bairro1;
	}

	/**
	 * @return the numero1
	 */
	public String getNumero1(){

		return numero1;
	}

	/**
	 * @param numero1
	 *            the numero1 to set
	 */
	public void setNumero1(String numero1){

		this.numero1 = numero1;
	}

	/**
	 * @return the dataComparecimento1
	 */
	public String getDataComparecimento1(){

		return dataComparecimento1;
	}

	/**
	 * @param dataComparecimento1
	 *            the dataComparecimento1 to set
	 */
	public void setDataComparecimento1(String dataComparecimento1){

		this.dataComparecimento1 = dataComparecimento1;
	}

	/**
	 * @return the dataDebitoAnterior1
	 */
	public String getDataDebitoAnterior1(){

		return dataDebitoAnterior1;
	}

	/**
	 * @param dataDebitoAnterior1
	 *            the dataDebitoAnterior1 to set
	 */
	public void setDataDebitoAnterior1(String dataDebitoAnterior1){

		this.dataDebitoAnterior1 = dataDebitoAnterior1;
	}

	/**
	 * @return the debitosAnteriores1
	 */
	public String getDebitosAnteriores1(){

		return debitosAnteriores1;
	}

	/**
	 * @param debitosAnteriores1
	 *            the debitosAnteriores1 to set
	 */
	public void setDebitosAnteriores1(String debitosAnteriores1){

		this.debitosAnteriores1 = debitosAnteriores1;
	}

	/**
	 * @return the valorTotalDebito1
	 */
	public String getValorTotalDebito1(){

		return valorTotalDebito1;
	}

	/**
	 * @param valorTotalDebito1
	 *            the valorTotalDebito1 to set
	 */
	public void setValorTotalDebito1(String valorTotalDebito1){

		this.valorTotalDebito1 = valorTotalDebito1;
	}

	/**
	 * @return the representacaoNumericaCodBarraFormatada1
	 */
	public String getRepresentacaoNumericaCodBarraFormatada1(){

		return representacaoNumericaCodBarraFormatada1;
	}

	/**
	 * @param representacaoNumericaCodBarraFormatada1
	 *            the representacaoNumericaCodBarraFormatada1 to set
	 */
	public void setRepresentacaoNumericaCodBarraFormatada1(String representacaoNumericaCodBarraFormatada1){

		this.representacaoNumericaCodBarraFormatada1 = representacaoNumericaCodBarraFormatada1;
	}

	/**
	 * @return the representacaoNumericaCodBarraSemDigito1
	 */
	public String getRepresentacaoNumericaCodBarraSemDigito1(){

		return representacaoNumericaCodBarraSemDigito1;
	}

	/**
	 * @param representacaoNumericaCodBarraSemDigito1
	 *            the representacaoNumericaCodBarraSemDigito1 to set
	 */
	public void setRepresentacaoNumericaCodBarraSemDigito1(String representacaoNumericaCodBarraSemDigito1){

		this.representacaoNumericaCodBarraSemDigito1 = representacaoNumericaCodBarraSemDigito1;
	}

	/**
	 * @return the dataPermanenciaRegistro1
	 */
	public String getDataPermanenciaRegistro1(){

		return dataPermanenciaRegistro1;
	}

	/**
	 * @param dataPermanenciaRegistro1
	 *            the dataPermanenciaRegistro1 to set
	 */
	public void setDataPermanenciaRegistro1(String dataPermanenciaRegistro1){

		this.dataPermanenciaRegistro1 = dataPermanenciaRegistro1;
	}

	/**
	 * @return the arrayJRDetail2
	 */
	public JRBeanCollectionDataSource getArrayJRDetail2(){

		return arrayJRDetail2;
	}

	/**
	 * @param arrayJRDetail2
	 *            the arrayJRDetail2 to set
	 */
	public void setArrayJRDetail2(JRBeanCollectionDataSource arrayJRDetail2){

		this.arrayJRDetail2 = arrayJRDetail2;
	}

	/**
	 * @return the arrayRelatorioOrdemCorteDetail2Bean
	 */
	public ArrayList<Object> getArrayRelatorioOrdemCorteDetail2Bean(){

		return arrayRelatorioOrdemCorteDetail2Bean;
	}

	/**
	 * @param arrayRelatorioOrdemCorteDetail2Bean
	 *            the arrayRelatorioOrdemCorteDetail2Bean to set
	 */
	public void setArrayRelatorioOrdemCorteDetail2Bean(ArrayList<Object> arrayRelatorioOrdemCorteDetail2Bean){

		this.arrayRelatorioOrdemCorteDetail2Bean = arrayRelatorioOrdemCorteDetail2Bean;
	}

	/**
	 * @return the inscricao2
	 */
	public String getInscricao2(){

		return inscricao2;
	}

	/**
	 * @param inscricao2
	 *            the inscricao2 to set
	 */
	public void setInscricao2(String inscricao2){

		this.inscricao2 = inscricao2;
	}

	/**
	 * @return the matricula2
	 */
	public String getMatricula2(){

		return matricula2;
	}

	/**
	 * @param matricula2
	 *            the matricula2 to set
	 */
	public void setMatricula2(String matricula2){

		this.matricula2 = matricula2;
	}

	/**
	 * @return the roteiro2
	 */
	public String getRoteiro2(){

		return roteiro2;
	}

	/**
	 * @param roteiro2
	 *            the roteiro2 to set
	 */
	public void setRoteiro2(String roteiro2){

		this.roteiro2 = roteiro2;
	}

	/**
	 * @return the hm2
	 */
	public String getHm2(){

		return hm2;
	}

	/**
	 * @param hm2
	 *            the hm2 to set
	 */
	public void setHm2(String hm2){

		this.hm2 = hm2;
	}

	/**
	 * @return the programa2
	 */
	public String getPrograma2(){

		return programa2;
	}

	/**
	 * @param programa2
	 *            the programa2 to set
	 */
	public void setPrograma2(String programa2){

		this.programa2 = programa2;
	}

	/**
	 * @return the cliente2
	 */
	public String getCliente2(){

		return cliente2;
	}

	/**
	 * @param cliente2
	 *            the cliente2 to set
	 */
	public void setCliente2(String cliente2){

		this.cliente2 = cliente2;
	}

	/**
	 * @return the endereco2
	 */
	public String getEndereco2(){

		return endereco2;
	}

	/**
	 * @param endereco2
	 *            the endereco2 to set
	 */
	public void setEndereco2(String endereco2){

		this.endereco2 = endereco2;
	}

	/**
	 * @return the bairro2
	 */
	public String getBairro2(){

		return bairro2;
	}

	/**
	 * @param bairro2
	 *            the bairro2 to set
	 */
	public void setBairro2(String bairro2){

		this.bairro2 = bairro2;
	}

	/**
	 * @return the numero2
	 */
	public String getNumero2(){

		return numero2;
	}

	/**
	 * @param numero2
	 *            the numero2 to set
	 */
	public void setNumero2(String numero2){

		this.numero2 = numero2;
	}

	/**
	 * @return the dataComparecimento2
	 */
	public String getDataComparecimento2(){

		return dataComparecimento2;
	}

	/**
	 * @param dataComparecimento2
	 *            the dataComparecimento2 to set
	 */
	public void setDataComparecimento2(String dataComparecimento2){

		this.dataComparecimento2 = dataComparecimento2;
	}

	/**
	 * @return the dataDebitoAnterior2
	 */
	public String getDataDebitoAnterior2(){

		return dataDebitoAnterior2;
	}

	/**
	 * @param dataDebitoAnterior2
	 *            the dataDebitoAnterior2 to set
	 */
	public void setDataDebitoAnterior2(String dataDebitoAnterior2){

		this.dataDebitoAnterior2 = dataDebitoAnterior2;
	}

	/**
	 * @return the debitosAnteriores2
	 */
	public String getDebitosAnteriores2(){

		return debitosAnteriores2;
	}

	/**
	 * @param debitosAnteriores2
	 *            the debitosAnteriores2 to set
	 */
	public void setDebitosAnteriores2(String debitosAnteriores2){

		this.debitosAnteriores2 = debitosAnteriores2;
	}

	/**
	 * @return the valorTotalDebito2
	 */
	public String getValorTotalDebito2(){

		return valorTotalDebito2;
	}

	/**
	 * @param valorTotalDebito2
	 *            the valorTotalDebito2 to set
	 */
	public void setValorTotalDebito2(String valorTotalDebito2){

		this.valorTotalDebito2 = valorTotalDebito2;
	}

	/**
	 * @return the representacaoNumericaCodBarraFormatada2
	 */
	public String getRepresentacaoNumericaCodBarraFormatada2(){

		return representacaoNumericaCodBarraFormatada2;
	}

	/**
	 * @param representacaoNumericaCodBarraFormatada2
	 *            the representacaoNumericaCodBarraFormatada2 to set
	 */
	public void setRepresentacaoNumericaCodBarraFormatada2(String representacaoNumericaCodBarraFormatada2){

		this.representacaoNumericaCodBarraFormatada2 = representacaoNumericaCodBarraFormatada2;
	}

	/**
	 * @return the representacaoNumericaCodBarraSemDigito2
	 */
	public String getRepresentacaoNumericaCodBarraSemDigito2(){

		return representacaoNumericaCodBarraSemDigito2;
	}

	/**
	 * @param representacaoNumericaCodBarraSemDigito2
	 *            the representacaoNumericaCodBarraSemDigito2 to set
	 */
	public void setRepresentacaoNumericaCodBarraSemDigito2(String representacaoNumericaCodBarraSemDigito2){

		this.representacaoNumericaCodBarraSemDigito2 = representacaoNumericaCodBarraSemDigito2;
	}

	/**
	 * @return the dataPermanenciaRegistro2
	 */
	public String getDataPermanenciaRegistro2(){

		return dataPermanenciaRegistro2;
	}

	/**
	 * @param dataPermanenciaRegistro2
	 *            the dataPermanenciaRegistro2 to set
	 */
	public void setDataPermanenciaRegistro2(String dataPermanenciaRegistro2){

		this.dataPermanenciaRegistro2 = dataPermanenciaRegistro2;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula(){

		return matricula;
	}

	/**
	 * @param matricula
	 *            the matricula to set
	 */
	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente(){

		return nomeCliente;
	}

	/**
	 * @param nomeCliente
	 *            the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	/**
	 * @return the hidrometro
	 */
	public String getHidrometro(){

		return hidrometro;
	}

	/**
	 * @param hidrometro
	 *            the hidrometro to set
	 */
	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	/**
	 * @return the acaoCobranca
	 */
	public String getAcaoCobranca(){

		return acaoCobranca;
	}

	/**
	 * @param acaoCobranca
	 *            the acaoCobranca to set
	 */
	public void setAcaoCobranca(String acaoCobranca){

		this.acaoCobranca = acaoCobranca;
	}

	/**
	 * @return the roteiro
	 */
	public String getRoteiro(){

		return roteiro;
	}

	/**
	 * @param roteiro
	 *            the roteiro to set
	 */
	public void setRoteiro(String roteiro){

		this.roteiro = roteiro;
	}

	/**
	 * @return the horaImpressao
	 */
	public String getHoraImpressao(){

		return horaImpressao;
	}

	/**
	 * @param horaImpressao
	 *            the horaImpressao to set
	 */
	public void setHoraImpressao(String horaImpressao){

		this.horaImpressao = horaImpressao;
	}

	/**
	 * @return the dataImpressao
	 */
	public String getDataImpressao(){

		return dataImpressao;
	}

	/**
	 * @param dataImpressao
	 *            the dataImpressao to set
	 */
	public void setDataImpressao(String dataImpressao){

		this.dataImpressao = dataImpressao;
	}

	/**
	 * @return the dataComparecimento
	 */
	public String getDataComparecimento(){

		return dataComparecimento;
	}

	/**
	 * @param dataComparecimento
	 *            the dataComparecimento to set
	 */
	public void setDataComparecimento(String dataComparecimento){

		this.dataComparecimento = dataComparecimento;
	}

	/**
	 * @return the dataDebitoAnterior
	 */
	public String getDataDebitoAnterior(){

		return dataDebitoAnterior;
	}

	/**
	 * @param dataDebitoAnterior
	 *            the dataDebitoAnterior to set
	 */
	public void setDataDebitoAnterior(String dataDebitoAnterior){

		this.dataDebitoAnterior = dataDebitoAnterior;
	}

	/**
	 * @return the valorDebitosAnteriores
	 */
	public BigDecimal getValorDebitosAnteriores(){

		return valorDebitosAnteriores;
	}

	/**
	 * @param valorDebitosAnteriores
	 *            the valorDebitosAnteriores to set
	 */
	public void setValorDebitosAnteriores(BigDecimal valorDebitosAnteriores){

		this.valorDebitosAnteriores = valorDebitosAnteriores;
	}

	/**
	 * @return the valorTotal
	 */
	public BigDecimal getValorTotal(){

		return valorTotal;
	}

	/**
	 * @param valorTotal
	 *            the valorTotal to set
	 */
	public void setValorTotal(BigDecimal valorTotal){

		this.valorTotal = valorTotal;
	}

	/**
	 * @return the mesAno
	 */
	public Collection<String> getMesAno(){

		return mesAno;
	}

	/**
	 * @param mesAno
	 *            the mesAno to set
	 */
	public void setMesAno(Collection<String> mesAno){

		this.mesAno = mesAno;
	}

	/**
	 * @return the vencimento
	 */
	public Collection<String> getVencimento(){

		return vencimento;
	}

	/**
	 * @param vencimento
	 *            the vencimento to set
	 */
	public void setVencimento(Collection<String> vencimento){

		this.vencimento = vencimento;
	}

	/**
	 * @return the valor
	 */
	public Collection<BigDecimal> getValor(){

		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(Collection<BigDecimal> valor){

		this.valor = valor;
	}

	/**
	 * @return the representacaoNumericaCodBarraFormatada
	 */
	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	/**
	 * @param representacaoNumericaCodBarraFormatada
	 *            the representacaoNumericaCodBarraFormatada to set
	 */
	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	/**
	 * @return the representacaoNumericaCodBarraSemDigito
	 */
	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	/**
	 * @param representacaoNumericaCodBarraSemDigito
	 *            the representacaoNumericaCodBarraSemDigito to set
	 */
	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	private Collection<Object> gerarDetail(Collection<String> collMesAno, Collection<String> collVencimento,
					Collection<BigDecimal> collValor){

		Collection<Object> colecaoDetail = new ArrayList<Object>();
		Collection<String> collStringValor = new ArrayList<String>();

		if(collValor != null && !collValor.isEmpty()){
			for(BigDecimal valor : collValor){
				collStringValor.add(Util.formatarMoedaReal(valor));
			}
		}

		Object[] linhasMesAno = this.gerarLinhasDetail(collMesAno).toArray();
		Object[] linhasVencimento = this.gerarLinhasDetail(collVencimento).toArray();
		Object[] linhasValor = this.gerarLinhasDetail(collStringValor).toArray();

		for(int i = 0; i < linhasMesAno.length; i++){

			String[] linhaMesAno = (String[]) linhasMesAno[i];
			String[] linhaVencimento = (String[]) linhasVencimento[i];
			String[] linhaValor = (String[]) linhasValor[i];

			Object relatorio = new RelatorioDadosContaDetailBean(linhaMesAno, linhaVencimento, linhaValor);
			colecaoDetail.add(relatorio);

		}

		return colecaoDetail;
	}

	private Collection<String[]> gerarLinhasDetail(Collection<String> collString){

		Collection<String[]> retorno = new ArrayList<String[]>();
		String[] item = new String[RELATORIO_DETAIL_3_COLUNAS];
		int countLine = 0;
		boolean preencheuTodos = false;

		if(collString != null && !collString.isEmpty()){
			for(String valor : collString){
				if((countLine + 1) <= RELATORIO_DETAIL_3_COLUNAS){
					item[countLine] = valor;
				}

				if((countLine + 1) == RELATORIO_DETAIL_3_COLUNAS){
					String[] linhaValor = new String[RELATORIO_DETAIL_3_COLUNAS];

					for(int i = 1; i <= RELATORIO_DETAIL_3_COLUNAS; i++){
						linhaValor[i - 1] = item[i - 1];
					}

					retorno.add(linhaValor);
					item[0] = null;
					item[1] = null;
					item[2] = null;
					countLine = 0;
					preencheuTodos = true;
				}else{
					countLine++;
				}
			}
		}

		if(countLine != 0){

			if(item[0] == null){
				item[0] = "";
			}
			if(item[1] == null){
				item[1] = "";
			}
			if(item[2] == null){
				item[2] = "";
			}
			retorno.add(item);
		}

		return retorno;
	}

	/**
	 * @return the ra1
	 */
	public String getRa1(){

		return ra1;
	}

	/**
	 * @param ra1
	 *            the ra1 to set
	 */
	public void setRa1(String ra1){

		this.ra1 = ra1;
	}

	/**
	 * @return the codDescricaoServico1
	 */
	public String getCodDescricaoServico1(){

		return codDescricaoServico1;
	}

	/**
	 * @param codDescricaoServico1
	 *            the codDescricaoServico1 to set
	 */
	public void setCodDescricaoServico1(String codDescricaoServico1){

		this.codDescricaoServico1 = codDescricaoServico1;
	}

	/**
	 * @return the ra2
	 */
	public String getRa2(){

		return ra2;
	}

	/**
	 * @param ra2
	 *            the ra2 to set
	 */
	public void setRa2(String ra2){

		this.ra2 = ra2;
	}

	/**
	 * @return the codDescricaoServico2
	 */
	public String getCodDescricaoServico2(){

		return codDescricaoServico2;
	}

	/**
	 * @param codDescricaoServico2
	 *            the codDescricaoServico2 to set
	 */
	public void setCodDescricaoServico2(String codDescricaoServico2){

		this.codDescricaoServico2 = codDescricaoServico2;
	}

	/**
	 * @return the arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean
	 */
	public ArrayList<Object> getArrayRelatorioFiscalizacaoOrdemCorteDetail1Bean(){

		return arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean;
	}

	/**
	 * @param arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean
	 *            the arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean to set
	 */
	public void setArrayRelatorioFiscalizacaoOrdemCorteDetail1Bean(ArrayList<Object> arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean){

		this.arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean = arrayRelatorioFiscalizacaoOrdemCorteDetail1Bean;
	}

	/**
	 * @return the arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean
	 */
	public ArrayList<Object> getArrayRelatorioFiscalizacaoOrdemCorteDetail2Bean(){

		return arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean;
	}

	/**
	 * @param arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean
	 *            the arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean to set
	 */
	public void setArrayRelatorioFiscalizacaoOrdemCorteDetail2Bean(ArrayList<Object> arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean){

		this.arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean = arrayRelatorioFiscalizacaoOrdemCorteDetail2Bean;
	}

	public void setTipoLigacao2(String tipoLigacao2){

		this.tipoLigacao2 = tipoLigacao2;
	}

	public String getTipoLigacao2(){

		return tipoLigacao2;
	}

	public void setCategoria2(String categoria2){

		this.categoria2 = categoria2;
	}

	public String getCategoria2(){

		return categoria2;
	}

	public void setDataPermanenciaRegistro(String dataPermanenciaRegistro){

		this.dataPermanenciaRegistro = dataPermanenciaRegistro;
	}

	public String getDataPermanenciaRegistro(){

		return dataPermanenciaRegistro;
	}

	public void setValorTotalDebito(String valorTotalDebito){

		this.valorTotalDebito = valorTotalDebito;
	}

	public String getValorTotalDebito(){

		return valorTotalDebito;
	}

	public void setImagemFaltaAgua(String imagemMacaco){

		this.imagemFaltaAgua = imagemMacaco;
	}

	public String getImagemFaltaAgua(){

		return imagemFaltaAgua;
	}

	public JRBeanCollectionDataSource getBeansDetalheEstrutura(){

		return beansDetalheEstrutura;
	}

	public void setBeansDetalheEstrutura(JRBeanCollectionDataSource beansDetalheEstrutura){

		this.beansDetalheEstrutura = beansDetalheEstrutura;
	}

	public ArrayList getArrayRelatorioOrdemServicoEstruturaDetailBean(){

		return arrayRelatorioOrdemServicoEstruturaDetailBean;
	}

	public void setArrayRelatorioOrdemServicoEstruturaDetailBean(ArrayList arrayRelatorioOrdemServicoEstruturaDetailBean){

		this.arrayRelatorioOrdemServicoEstruturaDetailBean = arrayRelatorioOrdemServicoEstruturaDetailBean;
	}

	public void setarBeansDetalheEstrutura(Collection colecaoDetail){

		this.arrayRelatorioOrdemServicoEstruturaDetailBean = new ArrayList();
		this.arrayRelatorioOrdemServicoEstruturaDetailBean.addAll(colecaoDetail);
		this.beansDetalheEstrutura = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemServicoEstruturaDetailBean);
	}

	public String getExibirRodapePadrao(){

		return exibirRodapePadrao;
	}

	public void setExibirRodapePadrao(String exibirRodapePadrao){

		this.exibirRodapePadrao = exibirRodapePadrao;
	}

	public String getExibirCodigoBarras(){

		return exibirCodigoBarras;
	}

	public void setExibirCodigoBarras(String exibirCodigoBarras){

		this.exibirCodigoBarras = exibirCodigoBarras;
	}

	public String getTipoServico1(){

		return tipoServico1;
	}

	public void setTipoServico1(String tipoServico1){

		this.tipoServico1 = tipoServico1;
	}

	public String getTipoServico2(){

		return tipoServico2;
	}

	public void setTipoServico2(String tipoServico2){

		this.tipoServico2 = tipoServico2;
	}

	public String getTipoRelacao(){

		return tipoRelacao;
	}

	public void setTipoRelacao(String tipoRelacao){

		this.tipoRelacao = tipoRelacao;
	}

	public String getTipoRelacao1(){

		return tipoRelacao1;
	}

	public void setTipoRelacao1(String tipoRelacao1){

		this.tipoRelacao1 = tipoRelacao1;
	}

	public String getTipoRelacao2(){

		return tipoRelacao2;
	}

	public void setTipoRelacao2(String tipoRelacao2){

		this.tipoRelacao2 = tipoRelacao2;
	}

	public String getSiteEmpresa(){

		return siteEmpresa;
	}

	public void setSiteEmpresa(String siteEmpresa){

		this.siteEmpresa = siteEmpresa;
	}

	public String getLocalidadeEmpresa(){

		return localidadeEmpresa;
	}

	public void setLocalidadeEmpresa(String localidadeEmpresa){

		this.localidadeEmpresa = localidadeEmpresa;
	}

	public String getLocal(){

		return local;
	}

	public void setLocal(String local){

		this.local = local;
	}

	public Integer getCobrancaSituacao(){

		return cobrancaSituacao;
	}

	public void setCobrancaSituacao(Integer cobrancaSituacao){

		this.cobrancaSituacao = cobrancaSituacao;
	}

	public String getSetor(){

		return setor;
	}
	
	public void setSetor(String setor){
	
		this.setor = setor;
	}

	public String getSublote(){

		return sublote;
	}

	public void setSublote(String sublote){

		this.sublote = sublote;
	}

	public String getControle(){

		return controle;
	}

	public void setControle(String controle){

		this.controle = controle;
	}

	public String getConsumidor(){

		return consumidor;
	}

	public void setConsumidor(String consumidor){

		this.consumidor = consumidor;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getEnderecoLocalidade(){

		return enderecoLocalidade;
	}

	public void setEnderecoLocalidade(String enderecoLocalidade){

		this.enderecoLocalidade = enderecoLocalidade;
	}

	public String getClienteCPFCNPJ(){

		return clienteCPFCNPJ;
	}

	public void setClienteCPFCNPJ(String clienteCPFCNPJ){

		this.clienteCPFCNPJ = clienteCPFCNPJ;
	}

	public String getClienteRG(){

		return clienteRG;
	}

	public void setClienteRG(String clienteRG){

		this.clienteRG = clienteRG;
	}

	public String getClienteNome(){

		return clienteNome;
	}

	public void setClienteNome(String clienteNome){

		this.clienteNome = clienteNome;
	}

	public String getClienteTelefone(){

		return clienteTelefone;
	}

	public void setClienteTelefone(String clienteTelefone){

		this.clienteTelefone = clienteTelefone;
	}

	public String getEconomiasQuantidade(){

		return economiasQuantidade;
	}

	public void setEconomiasQuantidade(String economiasQuantidade){

		this.economiasQuantidade = economiasQuantidade;
	}

	public String getEconomiasTipo(){

		return economiasTipo;
	}

	public void setEconomiasTipo(String economiasTipo){

		this.economiasTipo = economiasTipo;
	}

	public String getLeituraAtualHidrometro(){

		return leituraAtualHidrometro;
	}

	public void setLeituraAtualHidrometro(String leituraAtualHidrometro){

		this.leituraAtualHidrometro = leituraAtualHidrometro;
	}

	public String getNumeroLacre(){

		return numeroLacre;
	}

	public void setNumeroLacre(String numeroLacre){

		this.numeroLacre = numeroLacre;
	}

	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	public String getDataCorte(){

		return dataCorte;
	}

	public void setDataCorte(String dataCorte){

		this.dataCorte = dataCorte;
	}

	public String getDataSuspensao(){

		return dataSuspensao;
	}

	public void setDataSuspensao(String dataSuspensao){

		this.dataSuspensao = dataSuspensao;
	}

	public Collection<Conta> getContasVencidas(){

		return contasVencidas;
	}

	public void setContasVencidas(Collection<Conta> contasVencidas){

		this.contasVencidas = contasVencidas;
	}

	public String getQuantidadeContasVencidas(){

		return quantidadeContasVencidas;
	}

	public void setQuantidadeContasVencidas(String quantidadeContasVencidas){

		this.quantidadeContasVencidas = quantidadeContasVencidas;
	}

	public String getValorContasVencidas(){

		return valorContasVencidas;
	}

	public void setValorContasVencidas(String valorContasVencidas){

		this.valorContasVencidas = valorContasVencidas;
	}

	public String getOrdemServico(){

		return ordemServico;
	}

	public String getQtdEconomiasResidencial(){

		return qtdEconomiasResidencial;
	}

	public void setQtdEconomiasResidencial(String qtdEconomiasResidencial){

		this.qtdEconomiasResidencial = qtdEconomiasResidencial;
	}

	public void setOrdemServico(String ordemServico){

		this.ordemServico = ordemServico;
	}

	public JRBeanCollectionDataSource getColecaoDadosUltimosConsumosBean1(){

		return colecaoDadosUltimosConsumosBean1;
	}

	public void setColecaoDadosUltimosConsumosBean1(Collection<DadosUltimosConsumosHelper> colecaoDadosUltimosConsumosBean1){

		this.arrayDadosUltimosConsumosBean1 = new ArrayList();
		this.arrayDadosUltimosConsumosBean1.addAll(colecaoDadosUltimosConsumosBean1);
		this.colecaoDadosUltimosConsumosBean1 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean1);
	}

	public JRBeanCollectionDataSource getColecaoDadosUltimosConsumosBean2(){

		return colecaoDadosUltimosConsumosBean2;
	}

	public void setColecaoDadosUltimosConsumosBean2(Collection<DadosUltimosConsumosHelper> colecaoDadosUltimosConsumosBean2){

		this.arrayDadosUltimosConsumosBean2 = new ArrayList();
		this.arrayDadosUltimosConsumosBean2.addAll(colecaoDadosUltimosConsumosBean2);
		this.colecaoDadosUltimosConsumosBean2 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean2);
	}

	public JRBeanCollectionDataSource getColecaoDadosUltimosConsumosBean3(){

		return colecaoDadosUltimosConsumosBean3;
	}

	public void setColecaoDadosUltimosConsumosBean(Collection<DadosUltimosConsumosHelper> colecaoDadosUltimosConsumosBean3){

		this.arrayDadosUltimosConsumosBean3 = new ArrayList();
		this.arrayDadosUltimosConsumosBean3.addAll(colecaoDadosUltimosConsumosBean3);
		this.colecaoDadosUltimosConsumosBean3 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean3);
	}

	public JRBeanCollectionDataSource getColecaoDadosUltimosConsumosBean4(){

		return colecaoDadosUltimosConsumosBean4;
	}

	public void setColecaoDadosUltimosConsumosBean4(Collection<DadosUltimosConsumosHelper> colecaoDadosUltimosConsumosBean4){

		this.arrayDadosUltimosConsumosBean4 = new ArrayList();
		this.arrayDadosUltimosConsumosBean4.addAll(colecaoDadosUltimosConsumosBean4);
		this.colecaoDadosUltimosConsumosBean4 = new JRBeanCollectionDataSource(this.arrayDadosUltimosConsumosBean4);
	}
}
