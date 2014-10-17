/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 * 
 * GSANPCG 
 * Eduardo Henrique
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.cadastro.sistemaparametro;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class SistemaParametro
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR = 733; // Operacao.OPERACAO_SISTEMA_PARAMETROS_INFORMAR

	/** identifier field */
	private Integer parmId;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer anoMesFaturamento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer anoMesArrecadacao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String nomeEstado;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String nomeEmpresa;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String nomeAbreviadoEmpresa;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String cnpjEmpresa;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String numeroImovel;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String complementoEndereco;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String numeroTelefone;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String numeroRamal;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String numeroFax;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String descricaoEmail;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer menorConsumoGrandeUsuario;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer areaMaximaTarifaSocial;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer consumoEnergiaMaximoTarifaSocial;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal valorMinimoEmissaoConta;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal valorSalarioMinimo;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short menorEconomiasGrandeUsuario;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short mesesMediaConsumo;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short mesesMediaLeitura;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorFaixaFalsa;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualFaixaFalsa;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorUsoFaixaFalsa;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorUsoFiscalizadorLeitura;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualFiscalizacaoLeitura;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorPercentualFiscalizacaoLeitura;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer incrementoMaximoConsumoRateio;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer decrementoMaximoConsumoRateio;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualToleranciaRateio;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMinimoDiasEmissaoVencimento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroDiasAdicionaisCorreios;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMesesValidadeConta;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMaximoParcelasFinanciamento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMaximoParcelaCredito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualTaxaJurosFinanciamento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualMaximoAbatimento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualFinanciamentoEntradaMinima;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMesesMinimoAlteracaoVencimento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroDiasExpiracaoAcesso;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroDiasMensagemExpiracao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMaximoLoginFalho;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroLayoutFebraban;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroDiasVencimentoCobranca;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroMaximoFavorito;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Logradouro logradouro;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Bairro bairro;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private EnderecoReferencia enderecoReferencia;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private HidrometroCapacidade hidrometroCapacidade;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorSugestaoTramite;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer diasMaximoAlterarOS;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short diasReativacao;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Cep cep;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short codigoEmpresaFebraban;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String imagemLogomarca;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String imagemRelatorio;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String imagemConta;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short ultimoRAManual;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String tituloPagina;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String mensagemSistema;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private ContaBancaria contaBancaria;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualMediaIndice;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMaximoMesesSancoes;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String ipServidorSmtp;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String dsEmailResponsavel;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal valorSegundaVia;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroContratoPrestacaoServico;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Cliente clientePresidenteCompesa;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private UnidadeOrganizacional unidadeOrganizacionalIdPresidencia;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Cliente clienteDiretorComercialCompesa;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private short indicadorCobrarTaxaExtrato;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private short indicadorAtualizacaoTarifaria;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer anoMesAtualizacaoTarifaria;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorRoteiroEmpresa;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorFaturamentoAntecipado;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Date dataHoraDadosDiariosArrecadacao;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short codigoPeriodicidadeNegativacao;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroMaximoTiposDebitoEmissaoDocumento;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short diaVencimentoPublico;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroExecucaoResumoNegativacao;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short numeroDiasEsperaExtratoDebito;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer solicitacaoTipoEspecificacaoDefault;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer solicitacaoTipoEspecificacaoReiteracao;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorClienteAtualFatura;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer anoReferenciaDebitoConta;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroMaxDiasVencimentoEntradaParc;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String numeroMaxDiasVencimentoAlternativo;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String percentualCobranca;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String mensagemCartaOpcaoParcelamento;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private ConsumoTarifa consumoTarifaDefault;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer quantidadeDiasVencimentoSetor;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private short indicadorZeraCreditoClienteSubsHid;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private short indicadorZeraCreditoEmpresaSubsHid;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private short indicadorAjusteTarifaLeituraProjetada;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private short indicadorConsumoNormalInstalacaoHidrometro;

	// Este atributo não está mapeado no Hibernate para evitar que a consulta pela entidade
	// SistemaParametros retorne este arquivo de imagem. Isto seria desnecessário, pois esta imagem
	// é utilizada apenas pelo Crystal Report, na geração dos relatórios. A persistência desta
	// coluna acontece através de um Update realizado via SQL, em um método do IRepositorioCadastro.
	private byte[] arquivoImagemRelatorio;

	public static Short INDICADOR_USO_FAIXA_FALSA_SISTEMA_PARAMETRO = Short.valueOf("1");

	public static Short INDICADOR_USO_FAIXA_FALSA_ROTA = Short.valueOf("2");

	public static Short INDICADOR_FAIXA_FALSA_NAO_USO = Short.valueOf("2");

	public static Short INDICADOR_FAIXA_FALSA_ROTA = Short.valueOf("3");

	public static Short INDICADOR_PERCENTUAL_FISCALIZACAO_LEITURA_ROTA = Short.valueOf("2");

	public static Short INDICADOR_USO_FISCALIZADOR_GERA_FISCALIZACAO_SISTEMA_PARAMETRO = Short.valueOf("1");

	public static Short INDICADOR_USO_FISCALIZADOR_LEITURA_SISTEMA_PARAMETRO = Short.valueOf("2");

	public static Short INDICADOR_USO_FISCALIZADOR_LEITURA_ROTA = Short.valueOf("3");

	public static Short CODIGO_EMPRESA_FEBRABAN_CAERN = Short.valueOf("6");

	public static Short CODIGO_EMPRESA_FEBRABAN_CAER = Short.valueOf("4");

	public static Short CODIGO_EMPRESA_FEBRABAN_COMPESA = Short.valueOf("18");

	public static String EMPRESA_COMPESA = "COMPESA";

	public static String EMPRESA_CAER = "CAER";

	public static String EMPRESA_CAERN = "CAERN";

	public static Short CODIGO_PERIODICIDADE_NEGATIVACAO_SEMANAL = Short.valueOf("1");

	public static Short CODIGO_PERIODICIDADE_NEGATIVACAO_QUINZENAL = Short.valueOf("2");

	public static Short CODIGO_PERIODICIDADE_NEGATIVACAO_MENSAL = Short.valueOf("3");

	public static Short CODIGO_EMPRESA_FEBRABAN_CAEMA = new Short("2");

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorDebitoACobrarValidoCertidaoNegativa;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private short indicadorCertidaoNegativaEfeitoPositivo;

	// Indicador de Uso Pela Empresa ADA
	public static Short INDICADOR_EMPRESA_ADA = Short.valueOf("1");

	// Indicador de Uso Pela Empresa DESO
	public static Short INDICADOR_EMPRESA_DESO = Short.valueOf("2");

	// Indicador de Uso Pela Empresa CASAL
	public static Short INDICADOR_EMPRESA_CASAL = Short.valueOf("3");

	public static String SISTEMA_PARAMETRO = "sistemaParametro";

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroDiasSuspensaoCobrancaInfracao;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroMesesCalculoInfracao;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String imagemCabecalho;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String imagemPrincipal;

	/** persistent field */
	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private String imagemSecundaria;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroMinDebitosAguaParaTodos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer codMotivoExclusaoAguaParaTodos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroConsumoMinAguaParaTodos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroConsumoExcedidoAguaParaTodos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer codMotExclusaoConsumoAguaParaTodos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer codTarifaAguaParaTodos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer numeroMaxDiasVigenciaTarifaAguaParaTodos;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Integer codigoLimiteAceitavelAnormalidades;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private BigDecimal percentualAnormalidadeAceitavel;

	@ControleAlteracao(funcionalidade = ATRIBUTOS_SISTEMA_PARAMETROS_INFORMAR)
	private Short indicadorLayoutArquivoLeituraPadrao;

	/**
	 * Método criado para adequar ao padrão de getId() do Interceptador.
	 * [Workaround] Não é possível substituir o getParmId() por getId() pois não seria possível
	 * reiniciar processos Batch.
	 * 
	 * @author Saulo Lima
	 * @date 27/03/2012
	 * @return Integer
	 */
	// public Integer getId(){
	//
	// return this.getParmId();
	// }

	/**
	 * @return Retorna o campo imagemConta.
	 */
	public String getImagemConta(){

		return imagemConta;
	}

	/**
	 * @param imagemConta
	 *            O imagemConta a ser setado.
	 */
	public void setImagemConta(String imagemConta){

		this.imagemConta = imagemConta;
	}

	/**
	 * @return Retorna o campo imagemLogomarca.
	 */
	public String getImagemLogomarca(){

		return imagemLogomarca;
	}

	/**
	 * @param imagemLogomarca
	 *            O imagemLogomarca a ser setado.
	 */
	public void setImagemLogomarca(String imagemLogomarca){

		this.imagemLogomarca = imagemLogomarca;
	}

	/**
	 * @return Retorna o campo imagemRelatorio.
	 */
	public String getImagemRelatorio(){

		return imagemRelatorio;
	}

	public Short getNumeroDiasEsperaExtratoDebito(){

		return numeroDiasEsperaExtratoDebito;
	}

	public void setNumeroDiasEsperaExtratoDebito(Short numeroDiasEsperaExtratoDebito){

		this.numeroDiasEsperaExtratoDebito = numeroDiasEsperaExtratoDebito;
	}

	/**
	 * @param imagemRelatorio
	 *            O imagemRelatorio a ser setado.
	 */
	public void setImagemRelatorio(String imagemRelatorio){

		this.imagemRelatorio = imagemRelatorio;
	}

	/** default constructor */
	public SistemaParametro() {

	}

	//
	// /** minimal constructor */
	// public SistemaParametro(Logradouro logradouro, Bairro bairro,
	// EnderecoReferencia enderecoReferencia,
	// HidrometroCapacidade hidrometroCapacidade, Cep cep) {
	// this.logradouro = logradouro;
	// this.bairro = bairro;
	// this.enderecoReferencia = enderecoReferencia;
	// this.hidrometroCapacidade = hidrometroCapacidade;
	// this.cep = cep;
	// }

	public Integer getDiasMaximoAlterarOS(){

		return diasMaximoAlterarOS;
	}

	public void setDiasMaximoAlterarOS(Integer diasMaximoAlterarOS){

		this.diasMaximoAlterarOS = diasMaximoAlterarOS;
	}

	public Short getDiasReativacao(){

		return diasReativacao;
	}

	public void setDiasReativacao(Short diasReativacao){

		this.diasReativacao = diasReativacao;
	}

	public Short getIndicadorSugestaoTramite(){

		return indicadorSugestaoTramite;
	}

	public void setIndicadorSugestaoTramite(Short indicadorSugestaoTramite){

		this.indicadorSugestaoTramite = indicadorSugestaoTramite;
	}

	public Integer getParmId(){

		return this.parmId;
	}

	public void setParmId(Integer parmId){

		this.parmId = parmId;
	}

	public Integer getAnoMesFaturamento(){

		return this.anoMesFaturamento;
	}

	public void setAnoMesFaturamento(Integer anoMesFaturamento){

		this.anoMesFaturamento = anoMesFaturamento;
	}

	public Integer getAnoMesArrecadacao(){

		return this.anoMesArrecadacao;
	}

	public void setAnoMesArrecadacao(Integer anoMesArrecadacao){

		this.anoMesArrecadacao = anoMesArrecadacao;
	}

	public String getNomeEstado(){

		return this.nomeEstado;
	}

	public void setNomeEstado(String nomeEstado){

		this.nomeEstado = nomeEstado;
	}

	public String getNomeEmpresa(){

		return this.nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	public String getNomeAbreviadoEmpresa(){

		return this.nomeAbreviadoEmpresa;
	}

	public void setNomeAbreviadoEmpresa(String nomeAbreviadoEmpresa){

		this.nomeAbreviadoEmpresa = nomeAbreviadoEmpresa;
	}

	public String getCnpjEmpresa(){

		return this.cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa){

		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getNumeroImovel(){

		return this.numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getComplementoEndereco(){

		return this.complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco){

		this.complementoEndereco = complementoEndereco;
	}

	public String getNumeroTelefone(){

		return this.numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone){

		this.numeroTelefone = numeroTelefone;
	}

	public String getNumeroRamal(){

		return this.numeroRamal;
	}

	public void setNumeroRamal(String numeroRamal){

		this.numeroRamal = numeroRamal;
	}

	public String getNumeroFax(){

		return this.numeroFax;
	}

	public void setNumeroFax(String numeroFax){

		this.numeroFax = numeroFax;
	}

	public String getDescricaoEmail(){

		return this.descricaoEmail;
	}

	public void setDescricaoEmail(String descricaoEmail){

		this.descricaoEmail = descricaoEmail;
	}

	public Integer getMenorConsumoGrandeUsuario(){

		return this.menorConsumoGrandeUsuario;
	}

	public void setMenorConsumoGrandeUsuario(Integer menorConsumoGrandeUsuario){

		this.menorConsumoGrandeUsuario = menorConsumoGrandeUsuario;
	}

	public Integer getAreaMaximaTarifaSocial(){

		return this.areaMaximaTarifaSocial;
	}

	public void setAreaMaximaTarifaSocial(Integer areaMaximaTarifaSocial){

		this.areaMaximaTarifaSocial = areaMaximaTarifaSocial;
	}

	public Integer getConsumoEnergiaMaximoTarifaSocial(){

		return this.consumoEnergiaMaximoTarifaSocial;
	}

	public void setConsumoEnergiaMaximoTarifaSocial(Integer consumoEnergiaMaximoTarifaSocial){

		this.consumoEnergiaMaximoTarifaSocial = consumoEnergiaMaximoTarifaSocial;
	}

	public BigDecimal getValorMinimoEmissaoConta(){

		return this.valorMinimoEmissaoConta;
	}

	public void setValorMinimoEmissaoConta(BigDecimal valorMinimoEmissaoConta){

		this.valorMinimoEmissaoConta = valorMinimoEmissaoConta;
	}

	public BigDecimal getValorSalarioMinimo(){

		return this.valorSalarioMinimo;
	}

	public void setValorSalarioMinimo(BigDecimal valorSalarioMinimo){

		this.valorSalarioMinimo = valorSalarioMinimo;
	}

	public Short getMenorEconomiasGrandeUsuario(){

		return this.menorEconomiasGrandeUsuario;
	}

	public void setMenorEconomiasGrandeUsuario(Short menorEconomiasGrandeUsuario){

		this.menorEconomiasGrandeUsuario = menorEconomiasGrandeUsuario;
	}

	public Short getMesesMediaConsumo(){

		return this.mesesMediaConsumo;
	}

	public void setMesesMediaConsumo(Short mesesMediaConsumo){

		this.mesesMediaConsumo = mesesMediaConsumo;
	}

	public Short getIndicadorFaixaFalsa(){

		return this.indicadorFaixaFalsa;
	}

	public void setIndicadorFaixaFalsa(Short indicadorFaixaFalsa){

		this.indicadorFaixaFalsa = indicadorFaixaFalsa;
	}

	public BigDecimal getPercentualFaixaFalsa(){

		return this.percentualFaixaFalsa;
	}

	public void setPercentualFaixaFalsa(BigDecimal percentualFaixaFalsa){

		this.percentualFaixaFalsa = percentualFaixaFalsa;
	}

	public Short getIndicadorUsoFaixaFalsa(){

		return this.indicadorUsoFaixaFalsa;
	}

	public void setIndicadorUsoFaixaFalsa(Short indicadorUsoFaixaFalsa){

		this.indicadorUsoFaixaFalsa = indicadorUsoFaixaFalsa;
	}

	public Short getIndicadorUsoFiscalizadorLeitura(){

		return this.indicadorUsoFiscalizadorLeitura;
	}

	public void setIndicadorUsoFiscalizadorLeitura(Short indicadorUsoFiscalizadorLeitura){

		this.indicadorUsoFiscalizadorLeitura = indicadorUsoFiscalizadorLeitura;
	}

	public BigDecimal getPercentualFiscalizacaoLeitura(){

		return this.percentualFiscalizacaoLeitura;
	}

	public void setPercentualFiscalizacaoLeitura(BigDecimal percentualFiscalizacaoLeitura){

		this.percentualFiscalizacaoLeitura = percentualFiscalizacaoLeitura;
	}

	public Short getIndicadorPercentualFiscalizacaoLeitura(){

		return this.indicadorPercentualFiscalizacaoLeitura;
	}

	public void setIndicadorPercentualFiscalizacaoLeitura(Short indicadorPercentualFiscalizacaoLeitura){

		this.indicadorPercentualFiscalizacaoLeitura = indicadorPercentualFiscalizacaoLeitura;
	}

	public Integer getIncrementoMaximoConsumoRateio(){

		return this.incrementoMaximoConsumoRateio;
	}

	public void setIncrementoMaximoConsumoRateio(Integer incrementoMaximoConsumoRateio){

		this.incrementoMaximoConsumoRateio = incrementoMaximoConsumoRateio;
	}

	public Integer getDecrementoMaximoConsumoRateio(){

		return this.decrementoMaximoConsumoRateio;
	}

	public void setDecrementoMaximoConsumoRateio(Integer decrementoMaximoConsumoRateio){

		this.decrementoMaximoConsumoRateio = decrementoMaximoConsumoRateio;
	}

	public BigDecimal getPercentualToleranciaRateio(){

		return this.percentualToleranciaRateio;
	}

	public void setPercentualToleranciaRateio(BigDecimal percentualToleranciaRateio){

		this.percentualToleranciaRateio = percentualToleranciaRateio;
	}

	public Short getNumeroMinimoDiasEmissaoVencimento(){

		return this.numeroMinimoDiasEmissaoVencimento;
	}

	public void setNumeroMinimoDiasEmissaoVencimento(Short numeroMinimoDiasEmissaoVencimento){

		this.numeroMinimoDiasEmissaoVencimento = numeroMinimoDiasEmissaoVencimento;
	}

	public Short getNumeroDiasAdicionaisCorreios(){

		return this.numeroDiasAdicionaisCorreios;
	}

	public void setNumeroDiasAdicionaisCorreios(Short numeroDiasAdicionaisCorreios){

		this.numeroDiasAdicionaisCorreios = numeroDiasAdicionaisCorreios;
	}

	public Short getNumeroMesesValidadeConta(){

		return this.numeroMesesValidadeConta;
	}

	public void setNumeroMesesValidadeConta(Short numeroMesesValidadeConta){

		this.numeroMesesValidadeConta = numeroMesesValidadeConta;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getNumeroMaximoParcelasFinanciamento(){

		return this.numeroMaximoParcelasFinanciamento;
	}

	public void setNumeroMaximoParcelasFinanciamento(Short numeroMaximoParcelasFinanciamento){

		this.numeroMaximoParcelasFinanciamento = numeroMaximoParcelasFinanciamento;
	}

	public BigDecimal getPercentualTaxaJurosFinanciamento(){

		return this.percentualTaxaJurosFinanciamento;
	}

	public void setPercentualTaxaJurosFinanciamento(BigDecimal percentualTaxaJurosFinanciamento){

		this.percentualTaxaJurosFinanciamento = percentualTaxaJurosFinanciamento;
	}

	public BigDecimal getPercentualMaximoAbatimento(){

		return this.percentualMaximoAbatimento;
	}

	public void setPercentualMaximoAbatimento(BigDecimal percentualMaximoAbatimento){

		this.percentualMaximoAbatimento = percentualMaximoAbatimento;
	}

	public BigDecimal getPercentualFinanciamentoEntradaMinima(){

		return this.percentualFinanciamentoEntradaMinima;
	}

	public void setPercentualFinanciamentoEntradaMinima(BigDecimal percentualFinanciamentoEntradaMinima){

		this.percentualFinanciamentoEntradaMinima = percentualFinanciamentoEntradaMinima;
	}

	public Short getNumeroMesesMinimoAlteracaoVencimento(){

		return this.numeroMesesMinimoAlteracaoVencimento;
	}

	public void setNumeroMesesMinimoAlteracaoVencimento(Short numeroMesesMinimoAlteracaoVencimento){

		this.numeroMesesMinimoAlteracaoVencimento = numeroMesesMinimoAlteracaoVencimento;
	}

	public Short getNumeroDiasExpiracaoAcesso(){

		return this.numeroDiasExpiracaoAcesso;
	}

	public void setNumeroDiasExpiracaoAcesso(Short numeroDiasExpiracaoAcesso){

		this.numeroDiasExpiracaoAcesso = numeroDiasExpiracaoAcesso;
	}

	public Short getNumeroDiasMensagemExpiracao(){

		return this.numeroDiasMensagemExpiracao;
	}

	public void setNumeroDiasMensagemExpiracao(Short numeroDiasMensagemExpiracao){

		this.numeroDiasMensagemExpiracao = numeroDiasMensagemExpiracao;
	}

	public Short getNumeroMaximoLoginFalho(){

		return this.numeroMaximoLoginFalho;
	}

	public void setNumeroMaximoLoginFalho(Short numeroMaximoLoginFalho){

		this.numeroMaximoLoginFalho = numeroMaximoLoginFalho;
	}

	public Short getNumeroLayoutFebraban(){

		return this.numeroLayoutFebraban;
	}

	public void setNumeroLayoutFebraban(Short numeroLayoutFebraban){

		this.numeroLayoutFebraban = numeroLayoutFebraban;
	}

	public Short getNumeroDiasVencimentoCobranca(){

		return this.numeroDiasVencimentoCobranca;
	}

	public void setNumeroDiasVencimentoCobranca(Short numeroDiasVencimentoCobranca){

		this.numeroDiasVencimentoCobranca = numeroDiasVencimentoCobranca;
	}

	public Logradouro getLogradouro(){

		return this.logradouro;
	}

	public void setLogradouro(Logradouro logradouro){

		this.logradouro = logradouro;
	}

	public Bairro getBairro(){

		return this.bairro;
	}

	public void setBairro(Bairro bairro){

		this.bairro = bairro;
	}

	public EnderecoReferencia getEnderecoReferencia(){

		return this.enderecoReferencia;
	}

	public void setEnderecoReferencia(EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	public HidrometroCapacidade getHidrometroCapacidade(){

		return this.hidrometroCapacidade;
	}

	public void setHidrometroCapacidade(HidrometroCapacidade hidrometroCapacidade){

		this.hidrometroCapacidade = hidrometroCapacidade;
	}

	public Cep getCep(){

		return this.cep;
	}

	public void setCep(Cep cep){

		this.cep = cep;
	}

	/**
	 * @return Returns the codigoEmpresaFebraban.
	 */
	public Short getCodigoEmpresaFebraban(){

		return codigoEmpresaFebraban;
	}

	/**
	 * @param codigoEmpresaFebraban
	 *            The codigoEmpresaFebraban to set.
	 */
	public void setCodigoEmpresaFebraban(Short codigoEmpresaFebraban){

		this.codigoEmpresaFebraban = codigoEmpresaFebraban;
	}

	public String toString(){

		return new ToStringBuilder(this).append("parmId", getParmId()).toString();
	}

	/**
	 * Descrição do método>>
	 * 
	 * @param other
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof SistemaParametro)){
			return false;
		}
		SistemaParametro castOther = (SistemaParametro) other;

		return new EqualsBuilder().append(this.getAnoMesFaturamento(), castOther.getAnoMesFaturamento())
						.append(this.getAnoMesArrecadacao(), castOther.getAnoMesArrecadacao())
						.append(this.getNomeEstado(), castOther.getNomeEstado()).append(this.getNomeEmpresa(), castOther.getNomeEmpresa())
						.append(this.getNomeAbreviadoEmpresa(), castOther.getNomeAbreviadoEmpresa())
						.append(this.getCnpjEmpresa(), castOther.getCnpjEmpresa())
						.append(this.getNumeroImovel(), castOther.getNumeroImovel())
						.append(this.getComplementoEndereco(), castOther.getComplementoEndereco())
						.append(this.getNumeroTelefone(), castOther.getNumeroTelefone())
						.append(this.getNumeroRamal(), castOther.getNumeroRamal()).append(this.getNumeroFax(), castOther.getNumeroFax())
						.append(this.getDescricaoEmail(), castOther.getDescricaoEmail())
						.append(this.getMenorConsumoGrandeUsuario(), castOther.getMenorConsumoGrandeUsuario())
						.append(this.getValorMinimoEmissaoConta(), castOther.getValorMinimoEmissaoConta())
						.append(this.getMenorEconomiasGrandeUsuario(), castOther.getMenorEconomiasGrandeUsuario())
						.append(this.getMesesMediaConsumo(), castOther.getMesesMediaConsumo())
						.append(this.getIndicadorFaixaFalsa(), castOther.getIndicadorFaixaFalsa())
						.append(this.getPercentualFaixaFalsa(), castOther.getPercentualFaixaFalsa())
						.append(this.getIndicadorUsoFaixaFalsa(), castOther.getIndicadorUsoFaixaFalsa())
						.append(this.getIndicadorUsoFiscalizadorLeitura(), castOther.getIndicadorUsoFiscalizadorLeitura())
						.append(this.getPercentualFiscalizacaoLeitura(), castOther.getPercentualFiscalizacaoLeitura())
						.append(this.getIndicadorPercentualFiscalizacaoLeitura(), castOther.getIndicadorPercentualFiscalizacaoLeitura())
						.append(this.getIncrementoMaximoConsumoRateio(), castOther.getIncrementoMaximoConsumoRateio())
						.append(this.getDecrementoMaximoConsumoRateio(), castOther.getDecrementoMaximoConsumoRateio())
						.append(this.getPercentualToleranciaRateio(), castOther.getPercentualToleranciaRateio())
						.append(this.getNumeroMinimoDiasEmissaoVencimento(), castOther.getNumeroMinimoDiasEmissaoVencimento())
						.append(this.getNumeroDiasAdicionaisCorreios(), castOther.getNumeroDiasAdicionaisCorreios())
						.append(this.getNumeroMesesValidadeConta(), castOther.getNumeroMesesValidadeConta())
						.append(this.getUltimaAlteracao(), castOther.getUltimaAlteracao())
						.append(this.getNumeroMaximoParcelasFinanciamento(), castOther.numeroMaximoParcelasFinanciamento)
						.append(this.getPercentualTaxaJurosFinanciamento(), castOther.percentualTaxaJurosFinanciamento)
						.append(this.getPercentualMaximoAbatimento(), castOther.percentualMaximoAbatimento)
						.append(this.getPercentualFinanciamentoEntradaMinima(), castOther.percentualFinanciamentoEntradaMinima)
						.append(this.getIndicadorClienteAtualFatura(), castOther.getIndicadorClienteAtualFatura())

						.isEquals();
	}

	/**
	 * Descrição do método>>
	 * 
	 * @return Descrição do retorno
	 */
	public int hashCode(){

		return new HashCodeBuilder().append(getAnoMesFaturamento()).append(getAnoMesArrecadacao()).append(getNomeEstado())
						.append(getNomeEmpresa()).append(getNomeAbreviadoEmpresa()).append(getCnpjEmpresa()).append(getNumeroImovel())
						.append(getComplementoEndereco()).append(getNumeroTelefone()).append(getNumeroRamal()).append(getNumeroFax())
						.append(getDescricaoEmail()).append(getMenorConsumoGrandeUsuario()).append(getValorMinimoEmissaoConta())
						.append(getMenorEconomiasGrandeUsuario()).append(getMesesMediaConsumo()).append(getIndicadorFaixaFalsa())
						.append(getPercentualFaixaFalsa()).append(getIndicadorUsoFaixaFalsa()).append(getIndicadorUsoFiscalizadorLeitura())
						.append(getPercentualFiscalizacaoLeitura()).append(getIndicadorPercentualFiscalizacaoLeitura())
						.append(getIncrementoMaximoConsumoRateio()).append(getDecrementoMaximoConsumoRateio())
						.append(getPercentualToleranciaRateio()).append(getNumeroMinimoDiasEmissaoVencimento())
						.append(getNumeroDiasAdicionaisCorreios()).append(getNumeroMesesValidadeConta()).append(getUltimaAlteracao())
						.append(getNumeroMaximoParcelasFinanciamento()).append(getPercentualTaxaJurosFinanciamento())
						.append(getPercentualMaximoAbatimento()).append(getPercentualFinanciamentoEntradaMinima())
						.append(getIndicadorClienteAtualFatura()).toHashCode();
	}

	/**
	 * @return Retorna o campo numeroMaximoFavorito.
	 */
	public Integer getNumeroMaximoFavorito(){

		return numeroMaximoFavorito;
	}

	/**
	 * @param numeroMaximoFavorito
	 *            O numeroMaximoFavorito a ser setado.
	 */
	public void setNumeroMaximoFavorito(Integer numeroMaximoFavorito){

		this.numeroMaximoFavorito = numeroMaximoFavorito;
	}

	/**
	 * @return Retorna o campo contaBancaria.
	 */
	public ContaBancaria getContaBancaria(){

		return contaBancaria;
	}

	/**
	 * @param contaBancaria
	 *            O contaBancaria a ser setado.
	 */
	public void setContaBancaria(ContaBancaria contaBancaria){

		this.contaBancaria = contaBancaria;
	}

	public BigDecimal getPercentualMediaIndice(){

		return percentualMediaIndice;
	}

	public void setPercentualMediaIndice(BigDecimal percentualMediaIndice){

		this.percentualMediaIndice = percentualMediaIndice;
	}

	public Short getUltimoRAManual(){

		return ultimoRAManual;
	}

	public void setUltimoRAManual(Short ultimoRAManual){

		this.ultimoRAManual = ultimoRAManual;
	}

	public String getTituloPagina(){

		return tituloPagina;
	}

	public void setTituloPagina(String tituloPagina){

		this.tituloPagina = tituloPagina;
	}

	/**
	 * @return Retorna o campo numeroMaximoParcelaCredito.
	 */
	public Short getNumeroMaximoParcelaCredito(){

		return numeroMaximoParcelaCredito;
	}

	/**
	 * @param numeroMaximoParcelaCredito
	 *            O numeroMaximoParcelaCredito a ser setado.
	 */
	public void setNumeroMaximoParcelaCredito(Short numeroMaximoParcelaCredito){

		this.numeroMaximoParcelaCredito = numeroMaximoParcelaCredito;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"parmId"};
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();

		filtroSistemaParametro.adicionarParametro(new ParametroSimples(FiltroSistemaParametro.PARM_ID, this.getParmId()));

		filtroSistemaParametro.adicionarCaminhoParaCarregamentoEntidade("logradouro");
		filtroSistemaParametro.adicionarCaminhoParaCarregamentoEntidade("bairro");
		filtroSistemaParametro.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
		filtroSistemaParametro.adicionarCaminhoParaCarregamentoEntidade("hidrometroCapacidade");
		filtroSistemaParametro.adicionarCaminhoParaCarregamentoEntidade("cep");
		filtroSistemaParametro.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacionalIdPresidencia");

		return filtroSistemaParametro;
	}

	public String getMensagemSistema(){

		return mensagemSistema;
	}

	public void setMensagemSistema(String mensagemSistema){

		this.mensagemSistema = mensagemSistema;
	}

	public Short getNumeroMaximoMesesSancoes(){

		return numeroMaximoMesesSancoes;
	}

	public void setNumeroMaximoMesesSancoes(Short numeroMaximoMesesSancoes){

		this.numeroMaximoMesesSancoes = numeroMaximoMesesSancoes;
	}

	public String getDsEmailResponsavel(){

		return dsEmailResponsavel;
	}

	public void setDsEmailResponsavel(String dsEmailResponsavel){

		this.dsEmailResponsavel = dsEmailResponsavel;
	}

	public String getIpServidorSmtp(){

		return ipServidorSmtp;
	}

	public void setIpServidorSmtp(String ipServidorSmtp){

		this.ipServidorSmtp = ipServidorSmtp;
	}

	public BigDecimal getValorSegundaVia(){

		return valorSegundaVia;
	}

	public void setValorSegundaVia(BigDecimal valorSegundaVia){

		this.valorSegundaVia = valorSegundaVia;
	}

	public Cliente getClienteDiretorComercialCompesa(){

		return clienteDiretorComercialCompesa;
	}

	public void setClienteDiretorComercialCompesa(Cliente clienteDiretorComercialCompesa){

		this.clienteDiretorComercialCompesa = clienteDiretorComercialCompesa;
	}

	public Cliente getClientePresidenteCompesa(){

		return clientePresidenteCompesa;
	}

	public void setClientePresidenteCompesa(Cliente clientePresidenteCompesa){

		this.clientePresidenteCompesa = clientePresidenteCompesa;
	}

	public Integer getNumeroContratoPrestacaoServico(){

		return numeroContratoPrestacaoServico;
	}

	public void setNumeroContratoPrestacaoServico(Integer numeroContratoPrestacaoServico){

		this.numeroContratoPrestacaoServico = numeroContratoPrestacaoServico;
	}

	public short getIndicadorAtualizacaoTarifaria(){

		return indicadorAtualizacaoTarifaria;
	}

	public void setIndicadorAtualizacaoTarifaria(short indicadorAtualizacaoTarifaria){

		this.indicadorAtualizacaoTarifaria = indicadorAtualizacaoTarifaria;
	}

	public short getIndicadorCobrarTaxaExtrato(){

		return indicadorCobrarTaxaExtrato;
	}

	public void setIndicadorCobrarTaxaExtrato(short indicadorCobrarTaxaExtrato){

		this.indicadorCobrarTaxaExtrato = indicadorCobrarTaxaExtrato;
	}

	public Integer getAnoMesAtualizacaoTarifaria(){

		return anoMesAtualizacaoTarifaria;
	}

	public void setAnoMesAtualizacaoTarifaria(Integer anoMesAtualizacaoTarifaria){

		this.anoMesAtualizacaoTarifaria = anoMesAtualizacaoTarifaria;
	}

	public Short getIndicadorRoteiroEmpresa(){

		return indicadorRoteiroEmpresa;
	}

	public void setIndicadorRoteiroEmpresa(Short indicadorRoteiroEmpresa){

		this.indicadorRoteiroEmpresa = indicadorRoteiroEmpresa;
	}

	public Short getIndicadorFaturamentoAntecipado(){

		return indicadorFaturamentoAntecipado;
	}

	public void setIndicadorFaturamentoAntecipado(Short indicadorFaturamentoAntecipado){

		this.indicadorFaturamentoAntecipado = indicadorFaturamentoAntecipado;
	}

	public Date getDataHoraDadosDiariosArrecadacao(){

		return dataHoraDadosDiariosArrecadacao;
	}

	public void setDataHoraDadosDiariosArrecadacao(Date dataHoraDadosDiariosArrecadacao){

		this.dataHoraDadosDiariosArrecadacao = dataHoraDadosDiariosArrecadacao;
	}

	public Short getCodigoPeriodicidadeNegativacao(){

		return codigoPeriodicidadeNegativacao;
	}

	public void setCodigoPeriodicidadeNegativacao(Short codigoPeriodicidadeNegativacao){

		this.codigoPeriodicidadeNegativacao = codigoPeriodicidadeNegativacao;
	}

	public UnidadeOrganizacional getUnidadeOrganizacionalIdPresidencia(){

		return unidadeOrganizacionalIdPresidencia;
	}

	public void setUnidadeOrganizacionalIdPresidencia(UnidadeOrganizacional unidadeOrganizacionalIdPresidencia){

		this.unidadeOrganizacionalIdPresidencia = unidadeOrganizacionalIdPresidencia;
	}

	/**
	 * @return the numeroMaximoTiposDebitoEmissaoDocumento
	 */
	public Short getNumeroMaximoTiposDebitoEmissaoDocumento(){

		return numeroMaximoTiposDebitoEmissaoDocumento;
	}

	/**
	 * @param numeroMaximoTiposDebitoEmissaoDocumento
	 *            the numeroMaximoTiposDebitoEmissaoDocumento to set
	 */
	public void setNumeroMaximoTiposDebitoEmissaoDocumento(Short numeroMaximoTiposDebitoEmissaoDocumento){

		this.numeroMaximoTiposDebitoEmissaoDocumento = numeroMaximoTiposDebitoEmissaoDocumento;
	}

	/**
	 * @return the diaVencimentoPublico
	 */
	public Short getDiaVencimentoPublico(){

		return diaVencimentoPublico;
	}

	/**
	 * @param diaVencimentoPublico
	 *            the diaVencimentoPublico to set
	 */
	public void setDiaVencimentoPublico(Short diaVencimentoPublico){

		this.diaVencimentoPublico = diaVencimentoPublico;
	}

	/**
	 * @return Retorna o campo numeroExecucaoResumoNegativacao.
	 */
	public Integer getNumeroExecucaoResumoNegativacao(){

		return numeroExecucaoResumoNegativacao;
	}

	/**
	 * @return Retorna o campo numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos.
	 */
	public Short getNumeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos(){

		return numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos;
	}

	/**
	 * @param numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos
	 *            O numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos a ser setado.
	 */
	public void setNumeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos(Short numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos){

		this.numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos = numeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos;
	}

	/**
	 * @param numeroExecucaoResumoNegativacao
	 *            O numeroExecucaoResumoNegativacao a ser setado.
	 */
	public void setNumeroExecucaoResumoNegativacao(Integer numeroExecucaoResumoNegativacao){

		this.numeroExecucaoResumoNegativacao = numeroExecucaoResumoNegativacao;
	}

	@Override
	public void initializeLazy(){

		if(logradouro != null){
			logradouro.initializeLazy();
		}

		if(bairro != null){
			bairro.initializeLazy();
		}
	}

	public short getIndicadorCertidaoNegativaEfeitoPositivo(){

		return indicadorCertidaoNegativaEfeitoPositivo;
	}

	public void setIndicadorCertidaoNegativaEfeitoPositivo(short indicadorCertidaoNegativaEfeitoPositivo){

		this.indicadorCertidaoNegativaEfeitoPositivo = indicadorCertidaoNegativaEfeitoPositivo;
	}

	/**
	 * @return Retorna o campo indicadorDebitoACobrarValidoCertidaoNegativa.
	 */
	public Short getIndicadorDebitoACobrarValidoCertidaoNegativa(){

		return indicadorDebitoACobrarValidoCertidaoNegativa;
	}

	/**
	 * @param indicadorDebitoACobrarValidoCertidaoNegativa
	 *            O indicadorDebitoACobrarValidoCertidaoNegativa a ser setado.
	 */
	public void setIndicadorDebitoACobrarValidoCertidaoNegativa(Short indicadorDebitoACobrarValidoCertidaoNegativa){

		this.indicadorDebitoACobrarValidoCertidaoNegativa = indicadorDebitoACobrarValidoCertidaoNegativa;
	}

	public Integer getSolicitacaoTipoEspecificacaoDefault(){

		return solicitacaoTipoEspecificacaoDefault;
	}

	public void setSolicitacaoTipoEspecificacaoDefault(Integer solicitacaoTipoEspecificacaoDefault){

		this.solicitacaoTipoEspecificacaoDefault = solicitacaoTipoEspecificacaoDefault;
	}

	public Integer getSolicitacaoTipoEspecificacaoReiteracao(){

		return solicitacaoTipoEspecificacaoReiteracao;
	}

	public void setSolicitacaoTipoEspecificacaoReiteracao(Integer solicitacaoTipoEspecificacaoReiteracao){

		this.solicitacaoTipoEspecificacaoReiteracao = solicitacaoTipoEspecificacaoReiteracao;
	}

	public Short getIndicadorClienteAtualFatura(){

		return indicadorClienteAtualFatura;
	}

	public void setIndicadorClienteAtualFatura(Short indicadorClienteAtualFatura){

		this.indicadorClienteAtualFatura = indicadorClienteAtualFatura;
	}

	public Integer getAnoReferenciaDebitoConta(){

		return anoReferenciaDebitoConta;
	}

	public void setAnoReferenciaDebitoConta(Integer anoReferenciaDebitoConta){

		this.anoReferenciaDebitoConta = anoReferenciaDebitoConta;
	}

	public Integer getNumeroMaxDiasVencimentoEntradaParc(){

		return numeroMaxDiasVencimentoEntradaParc;
	}

	public void setNumeroMaxDiasVencimentoEntradaParc(Integer numeroMaxDiasVencimentoEntradaParc){

		this.numeroMaxDiasVencimentoEntradaParc = numeroMaxDiasVencimentoEntradaParc;
	}

	public String getNumeroMaxDiasVencimentoAlternativo(){

		return numeroMaxDiasVencimentoAlternativo;
	}

	public void setNumeroMaxDiasVencimentoAlternativo(String numeroMaxDiasVencimentoAlternativo){

		this.numeroMaxDiasVencimentoAlternativo = numeroMaxDiasVencimentoAlternativo;
	}

	/**
	 * @return the percentualCobranca
	 */
	public String getPercentualCobranca(){

		return percentualCobranca;
	}

	/**
	 * @param percentualCobranca
	 *            the percentualCobranca to set
	 */
	public void setPercentualCobranca(String percentualCobranca){

		this.percentualCobranca = percentualCobranca;
	}

	public String getMensagemCartaOpcaoParcelamento(){

		return mensagemCartaOpcaoParcelamento;
	}

	public void setMensagemCartaOpcaoParcelamento(String mensagemCartaOpcaoParcelamento){

		this.mensagemCartaOpcaoParcelamento = mensagemCartaOpcaoParcelamento;
	}

	/**
	 * @return the consumoTarifaDefault
	 */
	public ConsumoTarifa getConsumoTarifaDefault(){

		return consumoTarifaDefault;
	}

	/**
	 * @param consumoTarifaDefault
	 *            the consumoTarifaDefault to set
	 */
	public void setConsumoTarifaDefault(ConsumoTarifa consumoTarifaDefault){

		this.consumoTarifaDefault = consumoTarifaDefault;
	}

	/**
	 * @return the quantidadeDiasVencimentoSetor
	 */
	public Integer getQuantidadeDiasVencimentoSetor(){

		return quantidadeDiasVencimentoSetor;
	}

	/**
	 * @param quantidadeDiasVencimentoSetor
	 *            the quantidadeDiasVencimentoSetor to set
	 */
	public void setQuantidadeDiasVencimentoSetor(Integer quantidadeDiasVencimentoSetor){

		this.quantidadeDiasVencimentoSetor = quantidadeDiasVencimentoSetor;
	}

	public Short getMesesMediaLeitura(){

		return mesesMediaLeitura;
	}

	public void setMesesMediaLeitura(Short mesesMediaLeitura){

		this.mesesMediaLeitura = mesesMediaLeitura;
	}

	public Integer getNumeroDiasSuspensaoCobrancaInfracao(){

		return numeroDiasSuspensaoCobrancaInfracao;
	}

	public void setNumeroDiasSuspensaoCobrancaInfracao(Integer numeroDiasSuspensaoCobrancaInfracao){

		this.numeroDiasSuspensaoCobrancaInfracao = numeroDiasSuspensaoCobrancaInfracao;
	}

	public Integer getNumeroMesesCalculoInfracao(){

		return numeroMesesCalculoInfracao;
	}

	public void setNumeroMesesCalculoInfracao(Integer numeroMesesCalculoInfracao){

		this.numeroMesesCalculoInfracao = numeroMesesCalculoInfracao;
	}

	public short getIndicadorZeraCreditoClienteSubsHid(){

		return indicadorZeraCreditoClienteSubsHid;
	}

	public void setIndicadorZeraCreditoClienteSubsHid(short indicadorZeraCreditoClienteSubsHid){

		this.indicadorZeraCreditoClienteSubsHid = indicadorZeraCreditoClienteSubsHid;
	}

	public short getIndicadorZeraCreditoEmpresaSubsHid(){

		return indicadorZeraCreditoEmpresaSubsHid;
	}

	public void setIndicadorZeraCreditoEmpresaSubsHid(short indicadorZeraCreditoEmpresaSubsHid){

		this.indicadorZeraCreditoEmpresaSubsHid = indicadorZeraCreditoEmpresaSubsHid;
	}

	public short getIndicadorAjusteTarifaLeituraProjetada(){

		return indicadorAjusteTarifaLeituraProjetada;
	}

	public void setIndicadorAjusteTarifaLeituraProjetada(short indicadorAjusteTarifaLeituraProjetada){

		this.indicadorAjusteTarifaLeituraProjetada = indicadorAjusteTarifaLeituraProjetada;
	}

	public short getIndicadorConsumoNormalInstalacaoHidrometro(){

		return indicadorConsumoNormalInstalacaoHidrometro;
	}

	public void setIndicadorConsumoNormalInstalacaoHidrometro(short indicadorConsumoNormalInstalacaoHidrometro){

		this.indicadorConsumoNormalInstalacaoHidrometro = indicadorConsumoNormalInstalacaoHidrometro;
	}

	public String getImagemCabecalho(){

		return imagemCabecalho;
	}

	public void setImagemCabecalho(String imagemCabecalho){

		this.imagemCabecalho = imagemCabecalho;
	}

	public String getImagemPrincipal(){

		return imagemPrincipal;
	}

	public void setImagemPrincipal(String imagemPrincipal){

		this.imagemPrincipal = imagemPrincipal;
	}

	public String getImagemSecundaria(){

		return imagemSecundaria;
	}

	public void setImagemSecundaria(String imagemSecundaria){

		this.imagemSecundaria = imagemSecundaria;
	}

	public Integer getNumeroMinDebitosAguaParaTodos(){

		return numeroMinDebitosAguaParaTodos;
	}

	public void setNumeroMinDebitosAguaParaTodos(Integer numeroMinDebitosAguaParaTodos){

		this.numeroMinDebitosAguaParaTodos = numeroMinDebitosAguaParaTodos;
	}

	public Integer getCodMotivoExclusaoAguaParaTodos(){

		return codMotivoExclusaoAguaParaTodos;
	}

	public void setCodMotivoExclusaoAguaParaTodos(Integer codMotivoExclusaoAguaParaTodos){

		this.codMotivoExclusaoAguaParaTodos = codMotivoExclusaoAguaParaTodos;
	}

	public Integer getNumeroConsumoMinAguaParaTodos(){

		return numeroConsumoMinAguaParaTodos;
	}

	public void setNumeroConsumoMinAguaParaTodos(Integer numeroConsumoMinAguaParaTodos){

		this.numeroConsumoMinAguaParaTodos = numeroConsumoMinAguaParaTodos;
	}

	public Integer getNumeroConsumoExcedidoAguaParaTodos(){

		return numeroConsumoExcedidoAguaParaTodos;
	}

	public void setNumeroConsumoExcedidoAguaParaTodos(Integer numeroConsumoExcedidoAguaParaTodos){

		this.numeroConsumoExcedidoAguaParaTodos = numeroConsumoExcedidoAguaParaTodos;
	}

	public Integer getCodMotExclusaoConsumoAguaParaTodos(){

		return codMotExclusaoConsumoAguaParaTodos;
	}

	public void setCodMotExclusaoConsumoAguaParaTodos(Integer codMotExclusaoConsumoAguaParaTodos){

		this.codMotExclusaoConsumoAguaParaTodos = codMotExclusaoConsumoAguaParaTodos;
	}

	public Integer getCodTarifaAguaParaTodos(){

		return codTarifaAguaParaTodos;
	}

	public void setCodTarifaAguaParaTodos(Integer codTarifaAguaParaTodos){

		this.codTarifaAguaParaTodos = codTarifaAguaParaTodos;
	}

	public Integer getNumeroMaxDiasVigenciaTarifaAguaParaTodos(){

		return numeroMaxDiasVigenciaTarifaAguaParaTodos;
	}

	public void setNumeroMaxDiasVigenciaTarifaAguaParaTodos(Integer numeroMaxDiasVigenciaTarifaAguaParaTodos){

		this.numeroMaxDiasVigenciaTarifaAguaParaTodos = numeroMaxDiasVigenciaTarifaAguaParaTodos;
	}

	public Integer getCodigoLimiteAceitavelAnormalidades(){

		return codigoLimiteAceitavelAnormalidades;
	}

	public void setCodigoLimiteAceitavelAnormalidades(Integer codigoLimiteAceitavelAnormalidades){

		this.codigoLimiteAceitavelAnormalidades = codigoLimiteAceitavelAnormalidades;
	}

	public BigDecimal getPercentualAnormalidadeAceitavel(){

		return percentualAnormalidadeAceitavel;
	}

	public void setPercentualAnormalidadeAceitavel(BigDecimal percentualAnormalidadeAceitavel){

		this.percentualAnormalidadeAceitavel = percentualAnormalidadeAceitavel;
	}

	public Short getIndicadorLayoutArquivoLeituraPadrao(){

		return indicadorLayoutArquivoLeituraPadrao;
	}

	public void setIndicadorLayoutArquivoLeituraPadrao(Short indicadorLayoutArquivoLeituraPadrao){

		this.indicadorLayoutArquivoLeituraPadrao = indicadorLayoutArquivoLeituraPadrao;
	}

	public byte[] getArquivoImagemRelatorio(){

		return arquivoImagemRelatorio;
	}

	public void setArquivoImagemRelatorio(byte[] arquivoImagemRelatorio){

		this.arquivoImagemRelatorio = arquivoImagemRelatorio;
	}

}
