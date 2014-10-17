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
 * Vitor Hora
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
 * GSANPCG
 Vitor Hora
 */

package gcom.cadastro.imovel;

import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.aguaparatodos.ImovelAguaParaTodos;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.CobrancaSituacaoTipo;
import gcom.cobranca.QuitacaoDebitoAnual;
import gcom.faturamento.FaturamentoSituacaoMotivo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FaturamentoTipo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.integracao.acquagis.TabelaIntegracaoContaAtualizada;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.Rota;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SubBacia;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.IoUtil;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.Hibernate;

/**
 * @author Hibernate CodeGenerator
 * @created 1 de Junho de 2004
 * @author eduardo henrique
 * @date 06/11/2008
 *       Alteração na Entidade para retirada da associação 'direta' com Hidrometro (já estava
 *       comentada no hbm).
 */
@ControleAlteracao()
public class Imovel
				extends ObjetoTransacao
				implements Comparable<Imovel> {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_IMOVEL_ATUALIZAR = 17; // Operacao.OPERACAO_IMOVEL_ATUALIZAR

	public static final int ATRIBUTOS_IMOVEL_REMOVER = 292; // Operacao.OPERACAO_IMOVEL_REMOVER

	public static final int ATRIBUTOS_IMOVEL_INSERIR = 9; // Operacao.OPERACAO_IMOVEL_INSERIR

	public static final int ATRIBUTOS_VALIDAR_SITUACAO_ESPECIAL_FATURAMENTO = 435; // Operacao.OPERACAO_VALIDAR_SITUACAO_ESPECIAL_FATURAMENTO

	/**
	 * identifier field
	 */
	private Integer id;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private short lote;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private short subLote;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short testadaLote;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroReparcelamentoConsecutivos;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private String numeroImovel;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private String nomeImovel;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private String complementoEndereco;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private byte[] fotoFachada;

	private Blob fotoFachadaBlob;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private BigDecimal areaConstruida;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short indicadorImovelCondominio;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private BigDecimal volumeReservatorioSuperior;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private BigDecimal volumeReservatorioInferior;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private BigDecimal volumePiscina;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Date dataSupressaoParcial;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Date dataInfracao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroPontosUtilizacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroQuarto;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroBanheiro;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroAdulto;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroCrianca;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroMoradorTrabalhador;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroMorador;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroRetificacao;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroLeituraExcecao;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroParcelamento;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroReparcelamento;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short diaVencimento;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private BigDecimal numeroIptu;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Long numeroCelpe;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private Short indicadorConta;

	/** persistent field */
	@ControleAlteracao(value = FiltroImovel.IMOVEL_CONTA_ENVIO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private ImovelContaEnvio imovelContaEnvio;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short indicadorEmissaoExtratoFaturamento;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR, DebitoAutomatico.ATRIBUTOS_DEBITO_AUTOMATICO_EXCLUIR})
	private Short indicadorDebitoConta;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short indicadorExclusao;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private BigDecimal coordenadaX;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private BigDecimal coordenadaY;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.LIGACAO_ESGOTO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private LigacaoEsgoto ligacaoEsgoto;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.LIGACAO_AGUA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private LigacaoAgua ligacaoAgua;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.IMOVEL_ENDERECO_ANTERIOR, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private ImovelEnderecoAnterior imovelEnderecoAnterior;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.IMOVEL_PRINCIPAL, funcionalidade = ATRIBUTOS_IMOVEL_ATUALIZAR)
	private gcom.cadastro.imovel.Imovel imovelPrincipal;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.Imovel imovelCondominio;

	/**
	 * persistent field
	 */

	@ControleAlteracao(value = FiltroImovel.LIGACAO_ESGOTO_SITUACAO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.HIDROMETRO_INSTALACAO_HISTORICO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.LEITURA_ANORMALIDADE, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private LeituraAnormalidade leituraAnormalidade;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.ELO_ANORMALIDADE, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.EloAnormalidade eloAnormalidade;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.SETOR_COMERCIAL, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private SetorComercial setorComercial;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.AREA_CONSTRUIDA_FAIXA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.AreaConstruidaFaixa areaConstruidaFaixa;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.RENDA_FAMILIAR_FAIXA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.RendaFamiliarFaixa rendaFamiliarFaixa;

	/**
	 * 7
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.PAVIMENTO_CALCADA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.IMOVEL_PERFIL, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.ImovelPerfil imovelPerfil;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.RESERVATORIO_VOLUME_FAIXA_SUPERIOR, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaSuperior;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.RESERVATORIO_VOLUME_FAIXA_INFERIOR, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaInferior;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.LOCALIDADE, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Localidade localidade;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.QUADRA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Quadra quadra;

	/**
	 * persistent field
	 */
	private Date dataValidadeTarifaTemporaria;

	/**
	 * persistent field
	 */
	private ImovelAguaParaTodos imovelAguaParaTodos;

	/**
	 * persistent field
	 */
	/*
	 * @ControleAlteracao(value=FiltroImovel.HIDROMETRO,funcionalidade={ATRIBUTOS_IMOVEL_ATUALIZAR,
	 * ATRIBUTOS_IMOVEL_REMOVER
	 * ,ATRIBUTOS_IMOVEL_INSERIR})
	 * private Hidrometro hidrometro;
	 */

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.PADRAO_CONSTRUCAO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private PadraoConstrucao padraoConstrucao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.ESGOTAMENTO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Esgotamento esgotamento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.COBRANCA_SITUACAO_TIPO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private CobrancaSituacaoTipo cobrancaSituacaoTipo;

	/**
	 * 
	 */
	@ControleAlteracao(value = FiltroImovel.COBRANCA_SITUACAO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private CobrancaSituacao cobrancaSituacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.PAVIMENTO_RUA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.PavimentoRua pavimentoRua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.ENDERECO_REFERENCIA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private EnderecoReferencia enderecoReferencia;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.CADASTRO_OCORRENCIA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.POCO_TIPO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.PocoTipo pocoTipo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.LIGACAO_AGUA_SITUACAO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private LigacaoAguaSituacao ligacaoAguaSituacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.DESPEJO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.Despejo despejo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.FATURAMENTO_SITUACAO_TIPO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR, ATRIBUTOS_VALIDAR_SITUACAO_ESPECIAL_FATURAMENTO})
	private FaturamentoSituacaoTipo faturamentoSituacaoTipo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.FONTE_ABASTECIMENTO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.PISCINA_VOLUME_FAIXA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.CONSUMO_TARIFA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private gcom.faturamento.consumotarifa.ConsumoTarifa consumoTarifa;

	private ConsumoTarifa consumoTarifaTemporaria;

	/**
	 * nullable persistent field
	 */
	private Date ultimaAlteracao;

	// @ControleAlteracao(FiltroImovel.IMOVEL_SUBCATEGORIAS)
	private Set imovelSubcategorias;

	// @ControleAlteracao(FiltroImovel.CLIENTES_IMOVEIS)
	private Set<ClienteImovel> clienteImoveis;

	private Set medicaoHistoricos;

	private Set imovelCobrancaSituacoes;

	private Set cobrancaSituacaoHistorico;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short quantidadeEconomias;

	@ControleAlteracao(value = FiltroImovel.FATURAMENTO_TIPO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private FaturamentoTipo faturamentoTipo;

	@ControleAlteracao(value = FiltroImovel.FATURAMENTO_SITUACAO_MOTIVO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private FaturamentoSituacaoMotivo faturamentoSituacaoMotivo;

	private Set consumosHistoricos;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short indicadorSuspensaoAbastecimento;

	@ControleAlteracao(value = FiltroImovel.BAIRRO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private LogradouroBairro logradouroBairro;

	@ControleAlteracao(value = FiltroImovel.CEP, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private LogradouroCep logradouroCep;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short indicadorJardim;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Integer numeroSequencialRota;

	@ControleAlteracao(value = FiltroImovel.FUNCIONARIO, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Funcionario funcionario;

	private Set contas;

	private Set cobrancasDocumentos;

	private Set faturamentosSituacaoHistorico;

	private Set movimentosRoteiroEmpresa;

	private Set<QuitacaoDebitoAnual> quitacaoDebitoAnual;

	private Set<TabelaIntegracaoContaAtualizada> contasAtualizadas;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.ROTA, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Rota rota;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short numeroSegmento;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private Short indicadorContratoConsumo;

	private SetorAbastecimento setorAbastecimento;

	private SubBacia subBacia;

	private Integer codigoSetorComercial;

	private Integer numeroQuadra;

	/**
	 * persistent field
	 */
	@ControleAlteracao(value = FiltroImovel.DISTRITO_OPERACIONAL, funcionalidade = {ATRIBUTOS_IMOVEL_ATUALIZAR, ATRIBUTOS_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_INSERIR})
	private DistritoOperacional distritoOperacional;

	private Integer numeroContratoEmissao;

	/**
	 * Constante do indicador de agua para todos
	 */
	public final static Short INDICADOR_AGUA_PARA_TODOS_ATIVADO = Short.valueOf("1");

	/**
	 * Constante do indicador de agua para todos
	 */
	public final static Short INDICADOR_AGUA_PARA_TODOS_DESATIVADO = Short.valueOf("0");

	/**
	 * Constante do indicador de conta quando o indicador for para o responsavel
	 */
	public final static Short INDICADOR_CONTA_RESPONSAVEL = Short.valueOf("1");

	/**
	 * Constante do indicador de conta o indicador quando for para o imovel
	 */
	public final static Short INDICADOR_CONTA_IMOVEL = Short.valueOf("2");

	/**
	 * Constante do indicador de conta quando o indicador for nao pagavel para o
	 * imovel e pagavel para o responsavel
	 */
	public final static Short INDICADOR_CONTA_NAO_PAGAVEL_PARA_IMOVEL_PAGAVEL_PARA_RESPONSAVEL = Short.valueOf("3");

	/**
	 * Description of the Field
	 */
	public final static Short IMOVEL_CONDOMINIO = Short.valueOf("1");

	public final static Short IMOVEL_NAO_CONDOMINIO = Short.valueOf("2");

	/**
	 * Situação do Imovel Excluído
	 */
	public final static Short IMOVEL_EXCLUIDO = Short.valueOf("1");

	/**
	 * Indicador do Jardim
	 */
	public final static Short INDICADOR_JARDIM_SIM = Short.valueOf("1");

	public final static Short INDICADOR_DEBITO_AUTOMATICO = Short.valueOf("1");

	public final static Short INDICADOR_NAO_DEBITO_AUTOMATICO = Short.valueOf("2");

	// ---------Construtores Antigos
	/*
	 * public Imovel( short lote, short subLote, short testadaLote, String
	 * numeroImovel, String complementoEndereco, BigDecimal areaConstruida,
	 * Short indicadorImovelCondominio, BigDecimal volumeReservatorioSuperior,
	 * BigDecimal volumeReservatorioInferior, BigDecimal volumePiscina, Date
	 * dataSupressaoParcial, Date dataInfracao, Short numeroPontosUtilizacao,
	 * Short numeroMorador, Short numeroRetificacao, Short numeroLeituraExcecao,
	 * Short numeroParcelamento, Short numeroReparcelamento, Short
	 * diaVencimento, BigDecimal numeroIptu, Long numeroCelpe, Short
	 * indicadorConta, Short indicadorEmissaoExtratoFaturamento, Short
	 * indicadorDebitoConta, Short indicadorExclusao, BigDecimal coordenadaX,
	 * BigDecimal coordenadaY, LigacaoEsgoto ligacaoEsgoto, TarifaSocialDado
	 * tarifaSocialDado, LigacaoAgua ligacaoAgua, ImovelEnderecoAnterior
	 * imovelEnderecoAnterior, gcom.cadastro.imovel.Imovel imovelPrincipal,
	 * gcom.cadastro.imovel.Imovel imovelCondominio, LigacaoEsgotoSituacao
	 * ligacaoEsgotoSituacao, Logradouro logradouro,
	 * HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
	 * LeituraAnormalidade leituraAnormalidade,
	 * gcom.cadastro.imovel.EloAnormalidade eloAnormalidade, SetorComercial
	 * setorComercial, gcom.cadastro.imovel.AreaConstruidaFaixa
	 * areaConstruidaFaixa, gcom.cadastro.imovel.PavimentoCalcada
	 * pavimentoCalcada, gcom.cadastro.imovel.ImovelPerfil imovelPerfil,
	 * gcom.cadastro.imovel.ReservatorioVolumeFaixa
	 * reservatorioVolumeFaixaSuperior,
	 * gcom.cadastro.imovel.ReservatorioVolumeFaixa
	 * reservatorioVolumeFaixaInferior, Localidade localidade, Quadra quadra,
	 * CobrancaSituacaoTipo cobrancaSituacaoTipo,
	 * gcom.cadastro.imovel.PavimentoRua pavimentoRua, EnderecoReferencia
	 * enderecoReferencia, gcom.faturamento.conta.NomeConta nomeConta,
	 * gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia,
	 * gcom.cadastro.imovel.PocoTipo pocoTipo, LigacaoAguaSituacao
	 * ligacaoAguaSituacao, gcom.cadastro.imovel.Despejo despejo, Cep cep,
	 * FaturamentoSituacaoTipo faturamentoSituacaoTipo,
	 * gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento,
	 * gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa, Date
	 * ultimaAlteracao) { this.lote = lote; this.subLote = subLote;
	 * this.testadaLote = testadaLote; this.numeroImovel = numeroImovel;
	 * this.complementoEndereco = complementoEndereco; this.areaConstruida =
	 * areaConstruida; this.indicadorImovelCondominio =
	 * indicadorImovelCondominio; this.volumeReservatorioSuperior =
	 * volumeReservatorioSuperior; this.volumeReservatorioInferior =
	 * volumeReservatorioInferior; this.volumePiscina = volumePiscina;
	 * this.dataSupressaoParcial = dataSupressaoParcial; this.dataInfracao =
	 * dataInfracao; this.numeroPontosUtilizacao = numeroPontosUtilizacao;
	 * this.numeroMorador = numeroMorador; this.numeroRetificacao =
	 * numeroRetificacao; this.numeroLeituraExcecao = numeroLeituraExcecao;
	 * this.numeroParcelamento = numeroParcelamento; this.numeroReparcelamento =
	 * numeroReparcelamento; this.diaVencimento = diaVencimento; this.numeroIptu =
	 * numeroIptu; this.numeroCelpe = numeroCelpe; this.indicadorConta =
	 * indicadorConta; this.indicadorEmissaoExtratoFaturamento =
	 * indicadorEmissaoExtratoFaturamento; this.indicadorDebitoConta =
	 * indicadorDebitoConta; this.indicadorExclusao = indicadorExclusao;
	 * this.coordenadaX = coordenadaX; this.coordenadaY = coordenadaY;
	 * this.ligacaoEsgoto = ligacaoEsgoto; this.tarifaSocialDado =
	 * tarifaSocialDado; this.ligacaoAgua = ligacaoAgua;
	 * this.imovelEnderecoAnterior = imovelEnderecoAnterior;
	 * this.imovelPrincipal = imovelPrincipal; this.imovelCondominio =
	 * imovelCondominio; this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	 * this.logradouro = logradouro; this.hidrometroInstalacaoHistorico =
	 * hidrometroInstalacaoHistorico; this.leituraAnormalidade =
	 * leituraAnormalidade; this.eloAnormalidade = eloAnormalidade;
	 * this.setorComercial = setorComercial; this.areaConstruidaFaixa =
	 * areaConstruidaFaixa; this.pavimentoCalcada = pavimentoCalcada;
	 * this.imovelPerfil = imovelPerfil; this.reservatorioVolumeFaixaSuperior =
	 * reservatorioVolumeFaixaSuperior; this.reservatorioVolumeFaixaInferior =
	 * reservatorioVolumeFaixaInferior; this.localidade = localidade;
	 * this.quadra = quadra; this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
	 * this.pavimentoRua = pavimentoRua; this.enderecoReferencia =
	 * enderecoReferencia; this.nomeConta = nomeConta; this.cadastroOcorrencia =
	 * cadastroOcorrencia; this.pocoTipo = pocoTipo; this.ligacaoAguaSituacao =
	 * ligacaoAguaSituacao; this.despejo = despejo; this.cep = cep;
	 * this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
	 * this.fonteAbastecimento = fonteAbastecimento; this.piscinaVolumeFaixa =
	 * piscinaVolumeFaixa;
	 * this.ultimaAlteracao = ultimaAlteracao; }
	 * /** Constructor for the Imovel object
	 * @param id Description of the Parameter @param numeroImovel Description of
	 * the Parameter @param logradouro Description of the Parameter @param
	 * quadra Description of the Parameter @param enderecoReferencia Description
	 * of the Parameter @param cep Description of the Parameter @param
	 * complementoEndereco Description of the Parameter
	 * public Imovel(Integer id, String numeroImovel, Logradouro logradouro,
	 * Quadra quadra, EnderecoReferencia enderecoReferencia, Cep cep, String
	 * complementoEndereco) { this.id = id; this.numeroImovel = numeroImovel;
	 * this.quadra = quadra; this.enderecoReferencia = enderecoReferencia;
	 * this.cep = cep; this.logradouro = logradouro; this.complementoEndereco =
	 * complementoEndereco; } // ---------Fim Construtores Antigos
	 * /** full constructor
	 * @param lote Description of the Parameter @param subLote Description of
	 * the Parameter @param testadaLote Description of the Parameter @param
	 * numeroImovel Description of the Parameter @param complementoEndereco
	 * Description of the Parameter @param areaConstruida Description of the
	 * Parameter @param indicadorImovelCondominio Description of the Parameter
	 * @param volumeReservatorioSuperior Description of the Parameter @param
	 * volumeReservatorioInferior Description of the Parameter @param
	 * volumePiscina Description of the Parameter @param dataSupressaoParcial
	 * Description of the Parameter @param dataInfracao Description of the
	 * Parameter @param numeroPontosUtilizacao Description of the Parameter
	 * @param numeroMorador Description of the Parameter @param
	 * numeroRetificacao Description of the Parameter @param
	 * numeroLeituraExcecao Description of the Parameter @param
	 * numeroParcelamento Description of the Parameter @param
	 * numeroReparcelamento Description of the Parameter @param diaVencimento
	 * Description of the Parameter @param numeroIptu Description of the
	 * Parameter @param numeroCelpe Description of the Parameter @param
	 * indicadorConta Description of the Parameter @param
	 * indicadorEmissaoExtratoFaturamento Description of the Parameter @param
	 * indicadorDebitoConta Description of the Parameter @param
	 * indicadorExclusao Description of the Parameter @param coordenadaX
	 * Description of the Parameter @param coordenadaY Description of the
	 * Parameter @param ligacaoEsgoto Description of the Parameter @param
	 * tarifaSocialDado Description of the Parameter @param ligacaoAgua
	 * Description of the Parameter @param imovelEnderecoAnterior Description of
	 * the Parameter @param imovelPrincipal Description of the Parameter @param
	 * imovelCondominio Description of the Parameter @param
	 * ligacaoEsgotoSituacao Description of the Parameter @param logradouro
	 * Description of the Parameter @param hidrometroInstalacaoHistorico
	 * Description of the Parameter @param leituraAnormalidade Description of
	 * the Parameter @param eloAnormalidade Description of the Parameter @param
	 * setorComercial Description of the Parameter @param areaConstruidaFaixa
	 * Description of the Parameter @param pavimentoCalcada Description of the
	 * Parameter @param imovelPerfil Description of the Parameter @param
	 * reservatorioVolumeFaixaSuperior Description of the Parameter @param
	 * reservatorioVolumeFaixaInferior Description of the Parameter @param
	 * localidade Description of the Parameter @param quadra Description of the
	 * Parameter @param cobrancaSituacaoTipo Description of the Parameter @param
	 * pavimentoRua Description of the Parameter @param enderecoReferencia
	 * Descrição do parâmetro @param nomeConta Descrição do parâmetro @param
	 * cadastroOcorrencia Description of the Parameter @param pocoTipo
	 * Description of the Parameter @param ligacaoAguaSituacao Description of
	 * the Parameter @param despejo Description of the Parameter @param cep
	 * Description of the Parameter @param faturamentoSituacaoTipo Description
	 * of the Parameter @param fonteAbastecimento Description of the Parameter
	 * @param piscinaVolumeFaixa Description of the Parameter @param
	 * cobrancaSituacao Description of the Parameter @param ultimaAlteracao
	 * Descrição do parâmetro
	 * public Imovel( short lote, short subLote, short testadaLote, Short
	 * numeroReparcelamentoConsecutivos, String numeroImovel, String
	 * complementoEndereco, BigDecimal areaConstruida, Short
	 * indicadorImovelCondominio, BigDecimal volumeReservatorioSuperior,
	 * BigDecimal volumeReservatorioInferior, BigDecimal volumePiscina, Date
	 * dataSupressaoParcial, Date dataInfracao, Short numeroPontosUtilizacao,
	 * Short numeroMorador, Short numeroRetificacao, Short numeroLeituraExcecao,
	 * Short numeroParcelamento, Short numeroReparcelamento, Short
	 * diaVencimento, BigDecimal numeroIptu, Long numeroCelpe, Short
	 * indicadorConta, Short indicadorEmissaoExtratoFaturamento, Short
	 * indicadorDebitoConta, Short indicadorExclusao, BigDecimal coordenadaX,
	 * BigDecimal coordenadaY, LigacaoEsgoto ligacaoEsgoto, TarifaSocialDado
	 * tarifaSocialDado, LigacaoAgua ligacaoAgua, ImovelEnderecoAnterior
	 * imovelEnderecoAnterior, gcom.cadastro.imovel.Imovel imovelPrincipal,
	 * gcom.cadastro.imovel.Imovel imovelCondominio, LigacaoEsgotoSituacao
	 * ligacaoEsgotoSituacao, Logradouro logradouro,
	 * HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
	 * LeituraAnormalidade leituraAnormalidade,
	 * gcom.cadastro.imovel.EloAnormalidade eloAnormalidade, SetorComercial
	 * setorComercial, gcom.cadastro.imovel.AreaConstruidaFaixa
	 * areaConstruidaFaixa, gcom.cadastro.imovel.PavimentoCalcada
	 * pavimentoCalcada, gcom.cadastro.imovel.ImovelPerfil imovelPerfil,
	 * gcom.cadastro.imovel.ReservatorioVolumeFaixa
	 * reservatorioVolumeFaixaSuperior,
	 * gcom.cadastro.imovel.ReservatorioVolumeFaixa
	 * reservatorioVolumeFaixaInferior, Localidade localidade, Quadra quadra,
	 * CobrancaSituacaoTipo cobrancaSituacaoTipo,
	 * gcom.cadastro.imovel.PavimentoRua pavimentoRua, EnderecoReferencia
	 * enderecoReferencia, gcom.faturamento.conta.NomeConta nomeConta,
	 * gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia,
	 * gcom.cadastro.imovel.PocoTipo pocoTipo, LigacaoAguaSituacao
	 * ligacaoAguaSituacao, gcom.cadastro.imovel.Despejo despejo, Cep cep,
	 * FaturamentoSituacaoTipo faturamentoSituacaoTipo,
	 * gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento,
	 * gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa,
	 * FaturamentoTipo faturamentoTipo, FaturamentoSituacaoMotivo
	 * faturamentoSituacaoMotivo, Short indicadorSuspensaoAbastecimento, Date
	 * ultimaAlteracao) { this.lote = lote; this.subLote = subLote;
	 * this.testadaLote = testadaLote; this.numeroImovel = numeroImovel;
	 * this.numeroReparcelamentoConsecutivos = numeroReparcelamentoConsecutivos;
	 * this.complementoEndereco = complementoEndereco; this.areaConstruida =
	 * areaConstruida; this.indicadorImovelCondominio =
	 * indicadorImovelCondominio; this.volumeReservatorioSuperior =
	 * volumeReservatorioSuperior; this.volumeReservatorioInferior =
	 * volumeReservatorioInferior; this.volumePiscina = volumePiscina;
	 * this.dataSupressaoParcial = dataSupressaoParcial; this.dataInfracao =
	 * dataInfracao; this.numeroPontosUtilizacao = numeroPontosUtilizacao;
	 * this.numeroMorador = numeroMorador; this.numeroRetificacao =
	 * numeroRetificacao; this.numeroLeituraExcecao = numeroLeituraExcecao;
	 * this.numeroParcelamento = numeroParcelamento; this.numeroReparcelamento =
	 * numeroReparcelamento; this.diaVencimento = diaVencimento; this.numeroIptu =
	 * numeroIptu; this.numeroCelpe = numeroCelpe; this.indicadorConta =
	 * indicadorConta; this.indicadorEmissaoExtratoFaturamento =
	 * indicadorEmissaoExtratoFaturamento; this.indicadorDebitoConta =
	 * indicadorDebitoConta; this.indicadorExclusao = indicadorExclusao;
	 * this.coordenadaX = coordenadaX; this.coordenadaY = coordenadaY;
	 * this.ligacaoEsgoto = ligacaoEsgoto; this.tarifaSocialDado =
	 * tarifaSocialDado; this.ligacaoAgua = ligacaoAgua;
	 * this.imovelEnderecoAnterior = imovelEnderecoAnterior;
	 * this.imovelPrincipal = imovelPrincipal; this.imovelCondominio =
	 * imovelCondominio; this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	 * this.logradouro = logradouro; this.hidrometroInstalacaoHistorico =
	 * hidrometroInstalacaoHistorico; this.leituraAnormalidade =
	 * leituraAnormalidade; this.eloAnormalidade = eloAnormalidade;
	 * this.setorComercial = setorComercial; this.areaConstruidaFaixa =
	 * areaConstruidaFaixa; this.pavimentoCalcada = pavimentoCalcada;
	 * this.imovelPerfil = imovelPerfil; this.reservatorioVolumeFaixaSuperior =
	 * reservatorioVolumeFaixaSuperior; this.reservatorioVolumeFaixaInferior =
	 * reservatorioVolumeFaixaInferior; this.localidade = localidade;
	 * this.quadra = quadra; this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
	 * this.pavimentoRua = pavimentoRua; this.enderecoReferencia =
	 * enderecoReferencia; this.nomeConta = nomeConta; this.cadastroOcorrencia =
	 * cadastroOcorrencia; this.pocoTipo = pocoTipo; this.ligacaoAguaSituacao =
	 * ligacaoAguaSituacao; this.despejo = despejo; this.cep = cep;
	 * this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
	 * this.fonteAbastecimento = fonteAbastecimento; this.piscinaVolumeFaixa =
	 * piscinaVolumeFaixa; this.faturamentoTipo = faturamentoTipo;
	 * this.faturamentoSituacaoMotivo = faturamentoSituacaoMotivo;
	 * this.indicadorSuspensaoAbastecimento = indicadorSuspensaoAbastecimento;
	 * this.ultimaAlteracao = ultimaAlteracao; }
	 * /** Constructor for the Imovel object
	 * @param id Description of the Parameter @param numeroImovel Description of
	 * the Parameter @param logradouro Description of the Parameter @param
	 * quadra Description of the Parameter @param enderecoReferencia Description
	 * of the Parameter @param cep Description of the Parameter @param
	 * complementoEndereco Description of the Parameter
	 * public Imovel(Integer id, String numeroImovel, Logradouro logradouro,
	 * Quadra quadra, EnderecoReferencia enderecoReferencia, Cep cep, String
	 * complementoEndereco, FaturamentoTipo faturamentoTipo) { this.id = id;
	 * this.numeroImovel = numeroImovel; this.quadra = quadra;
	 * this.enderecoReferencia = enderecoReferencia; this.cep = cep;
	 * this.logradouro = logradouro; this.complementoEndereco =
	 * complementoEndereco; this.faturamentoTipo = faturamentoTipo; }
	 * /** default constructor
	 * public Imovel() { }
	 * /** minimal constructor
	 * @param lote Description of the Parameter @param subLote Description of
	 * the Parameter @param testadaLote Description of the Parameter @param
	 * numeroImovel Description of the Parameter @param complementoEndereco
	 * Description of the Parameter @param numeroPontosUtilizacao Description of
	 * the Parameter @param numeroMorador Description of the Parameter @param
	 * imovelPrincipal Description of the Parameter @param imovelCondominio
	 * Description of the Parameter @param ligacaoEsgotoSituacao Description of
	 * the Parameter @param logradouro Description of the Parameter @param
	 * hidrometroInstalacaoHistorico Description of the Parameter @param
	 * leituraAnormalidade Description of the Parameter @param eloAnormalidade
	 * Description of the Parameter @param setorComercial Description of the
	 * Parameter @param areaConstruidaFaixa Description of the Parameter @param
	 * pavimentoCalcada Description of the Parameter @param imovelPerfil
	 * Description of the Parameter @param reservatorioVolumeFaixaSuperior
	 * Description of the Parameter @param reservatorioVolumeFaixaInferior
	 * Description of the Parameter @param localidade Description of the
	 * Parameter @param quadra Description of the Parameter @param
	 * cobrancaSituacaoTipo Description of the Parameter @param pavimentoRua
	 * Description of the Parameter @param enderecoImovelReferencia Description
	 * of the Parameter @param nomeConta Descrição do parâmetro @param
	 * cadastroOcorrencia Description of the Parameter @param pocoTipo
	 * Description of the Parameter @param ligacaoAguaSituacao Description of
	 * the Parameter @param despejo Description of the Parameter @param cep
	 * Description of the Parameter @param faturamentoSituacaoTipo Description
	 * of the Parameter @param fonteAbastecimento Description of the Parameter
	 * @param piscinaVolumeFaixa Description of the Parameter @param
	 * cobrancaSituacao Description of the Parameter
	 * public Imovel( short lote, short subLote, short testadaLote, String
	 * numeroImovel, String complementoEndereco, Short numeroPontosUtilizacao,
	 * Short numeroMorador, gcom.cadastro.imovel.Imovel imovelPrincipal,
	 * gcom.cadastro.imovel.Imovel imovelCondominio, LigacaoEsgotoSituacao
	 * ligacaoEsgotoSituacao, Logradouro logradouro,
	 * HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
	 * LeituraAnormalidade leituraAnormalidade,
	 * gcom.cadastro.imovel.EloAnormalidade eloAnormalidade, SetorComercial
	 * setorComercial, gcom.cadastro.imovel.AreaConstruidaFaixa
	 * areaConstruidaFaixa, gcom.cadastro.imovel.PavimentoCalcada
	 * pavimentoCalcada, gcom.cadastro.imovel.ImovelPerfil imovelPerfil,
	 * gcom.cadastro.imovel.ReservatorioVolumeFaixa
	 * reservatorioVolumeFaixaSuperior,
	 * gcom.cadastro.imovel.ReservatorioVolumeFaixa
	 * reservatorioVolumeFaixaInferior, Localidade localidade, Quadra quadra,
	 * CobrancaSituacaoTipo cobrancaSituacaoTipo,
	 * gcom.cadastro.imovel.PavimentoRua pavimentoRua, EnderecoReferencia
	 * enderecoImovelReferencia, gcom.faturamento.conta.NomeConta nomeConta,
	 * gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia,
	 * gcom.cadastro.imovel.PocoTipo pocoTipo, LigacaoAguaSituacao
	 * ligacaoAguaSituacao, gcom.cadastro.imovel.Despejo despejo, Cep cep,
	 * FaturamentoSituacaoTipo faturamentoSituacaoTipo,
	 * gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento,
	 * gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa) { this.lote =
	 * lote; this.subLote = subLote; this.testadaLote = testadaLote;
	 * this.numeroImovel = numeroImovel; this.complementoEndereco =
	 * complementoEndereco; this.numeroPontosUtilizacao =
	 * numeroPontosUtilizacao; this.numeroMorador = numeroMorador;
	 * this.imovelPrincipal = imovelPrincipal; this.imovelCondominio =
	 * imovelCondominio; this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	 * this.logradouro = logradouro; this.hidrometroInstalacaoHistorico =
	 * hidrometroInstalacaoHistorico; this.leituraAnormalidade =
	 * leituraAnormalidade; this.eloAnormalidade = eloAnormalidade;
	 * this.setorComercial = setorComercial; this.areaConstruidaFaixa =
	 * areaConstruidaFaixa; this.pavimentoCalcada = pavimentoCalcada;
	 * this.imovelPerfil = imovelPerfil; this.reservatorioVolumeFaixaSuperior =
	 * reservatorioVolumeFaixaSuperior; this.reservatorioVolumeFaixaInferior =
	 * reservatorioVolumeFaixaInferior; this.localidade = localidade;
	 * this.quadra = quadra; this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
	 * this.pavimentoRua = pavimentoRua; this.enderecoReferencia =
	 * enderecoImovelReferencia; this.nomeConta = nomeConta;
	 * this.cadastroOcorrencia = cadastroOcorrencia; this.pocoTipo = pocoTipo;
	 * this.ligacaoAguaSituacao = ligacaoAguaSituacao; this.despejo = despejo;
	 * this.cep = cep; this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
	 * this.fonteAbastecimento = fonteAbastecimento; this.piscinaVolumeFaixa =
	 * piscinaVolumeFaixa; }
	 * public Imovel(Integer id, gcom.cadastro.localidade.Localidade localidade,
	 * gcom.cadastro.localidade.SetorComercial setorComercial,
	 * gcom.cadastro.localidade.Quadra quadra, short lote, short subLote,
	 * FaturamentoSituacaoTipo faturamentoSituacaoTipo) {
	 * this.id = id; this.localidade = localidade; this.setorComercial =
	 * setorComercial; this.quadra = quadra; this.lote = lote; this.subLote =
	 * subLote; this.faturamentoSituacaoTipo = faturamentoSituacaoTipo; }
	 */

	public Imovel(short lote, short subLote, Short testadaLote, String numeroImovel, String complementoEndereco, byte[] fotoFachada,
					BigDecimal areaConstruida, Short indicadorImovelCondominio, BigDecimal volumeReservatorioSuperior,
					BigDecimal volumeReservatorioInferior, BigDecimal volumePiscina, Date dataSupressaoParcial, Date dataInfracao,
					Short numeroPontosUtilizacao, Short numeroQuarto, Short numeroBanheiro, Short numeroAdulto, Short numeroCrianca,
					Short numeroMoradorTrabalhador, Short numeroMorador, Short numeroRetificacao, Short numeroLeituraExcecao,
					Short numeroParcelamento, Short numeroReparcelamento, Short diaVencimento, BigDecimal numeroIptu, Long numeroCelpe,
					Short indicadorConta, Short indicadorEmissaoExtratoFaturamento, Short indicadorDebitoConta, Short indicadorExclusao,
					BigDecimal coordenadaX, BigDecimal coordenadaY, LigacaoEsgoto ligacaoEsgoto, LigacaoAgua ligacaoAgua,
					ImovelEnderecoAnterior imovelEnderecoAnterior, gcom.cadastro.imovel.Imovel imovelPrincipal,
					gcom.cadastro.imovel.Imovel imovelCondominio, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico, LeituraAnormalidade leituraAnormalidade,
					gcom.cadastro.imovel.EloAnormalidade eloAnormalidade, SetorComercial setorComercial,
					gcom.cadastro.imovel.AreaConstruidaFaixa areaConstruidaFaixa,
					gcom.cadastro.imovel.RendaFamiliarFaixa rendaFamiliarFaixa, gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada,
					gcom.cadastro.imovel.ImovelPerfil imovelPerfil,
					gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaSuperior,
					gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaInferior, Localidade localidade, Quadra quadra,
					Hidrometro hidormetro, PadraoConstrucao padraoConstrucao, Esgotamento esgotamento,
					gcom.cadastro.imovel.PavimentoRua pavimentoRua, EnderecoReferencia enderecoReferencia,
					gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia, gcom.cadastro.imovel.PocoTipo pocoTipo,
					LigacaoAguaSituacao ligacaoAguaSituacao, gcom.cadastro.imovel.Despejo despejo,
					FaturamentoSituacaoTipo faturamentoSituacaoTipo, gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento,
					gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa, Date ultimaAlteracao, LogradouroCep logradouroCep) {

		this.lote = lote;
		this.subLote = subLote;
		this.testadaLote = testadaLote;
		this.numeroImovel = numeroImovel;
		this.complementoEndereco = complementoEndereco;
		this.fotoFachada = fotoFachada;
		this.areaConstruida = areaConstruida;
		this.indicadorImovelCondominio = indicadorImovelCondominio;
		this.volumeReservatorioSuperior = volumeReservatorioSuperior;
		this.volumeReservatorioInferior = volumeReservatorioInferior;
		this.volumePiscina = volumePiscina;
		this.dataSupressaoParcial = dataSupressaoParcial;
		this.dataInfracao = dataInfracao;
		this.numeroPontosUtilizacao = numeroPontosUtilizacao;
		this.numeroQuarto = numeroQuarto;
		this.numeroBanheiro = numeroBanheiro;
		this.numeroAdulto = numeroAdulto;
		this.numeroCrianca = numeroCrianca;
		this.numeroMoradorTrabalhador = numeroMoradorTrabalhador;
		this.numeroMorador = numeroMorador;
		this.numeroRetificacao = numeroRetificacao;
		this.numeroLeituraExcecao = numeroLeituraExcecao;
		this.numeroParcelamento = numeroParcelamento;
		this.numeroReparcelamento = numeroReparcelamento;
		this.diaVencimento = diaVencimento;
		this.numeroIptu = numeroIptu;
		this.numeroCelpe = numeroCelpe;
		this.indicadorConta = indicadorConta;
		this.indicadorEmissaoExtratoFaturamento = indicadorEmissaoExtratoFaturamento;
		this.indicadorDebitoConta = indicadorDebitoConta;
		this.indicadorExclusao = indicadorExclusao;
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		this.ligacaoEsgoto = ligacaoEsgoto;
		this.ligacaoAgua = ligacaoAgua;
		this.imovelEnderecoAnterior = imovelEnderecoAnterior;
		this.imovelPrincipal = imovelPrincipal;
		this.imovelCondominio = imovelCondominio;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
		this.leituraAnormalidade = leituraAnormalidade;
		this.eloAnormalidade = eloAnormalidade;
		this.setorComercial = setorComercial;
		this.areaConstruidaFaixa = areaConstruidaFaixa;
		this.rendaFamiliarFaixa = rendaFamiliarFaixa;
		this.pavimentoCalcada = pavimentoCalcada;
		this.imovelPerfil = imovelPerfil;
		this.reservatorioVolumeFaixaSuperior = reservatorioVolumeFaixaSuperior;
		this.reservatorioVolumeFaixaInferior = reservatorioVolumeFaixaInferior;
		this.localidade = localidade;
		this.quadra = quadra;
		// this.hidrometro = hidormetro;
		this.padraoConstrucao = padraoConstrucao;
		this.esgotamento = esgotamento;
		this.pavimentoRua = pavimentoRua;
		this.enderecoReferencia = enderecoReferencia;
		this.cadastroOcorrencia = cadastroOcorrencia;
		this.pocoTipo = pocoTipo;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.despejo = despejo;
		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
		this.fonteAbastecimento = fonteAbastecimento;
		this.piscinaVolumeFaixa = piscinaVolumeFaixa;
		this.ultimaAlteracao = ultimaAlteracao;
		this.logradouroCep = logradouroCep;
	}

	/**
	 * Constructor for the Imovel object
	 * 
	 * @param id
	 *            Description of the Parameter
	 * @param numeroImovel
	 *            Description of the Parameter
	 * @param logradouro
	 *            Description of the Parameter
	 * @param quadra
	 *            Description of the Parameter
	 * @param enderecoReferencia
	 *            Description of the Parameter
	 * @param cep
	 *            Description of the Parameter
	 * @param complementoEndereco
	 *            Description of the Parameter
	 */
	public Imovel(Integer id, String numeroImovel, LogradouroCep logradouroCep, Quadra quadra, Hidrometro hidrometro,
					EnderecoReferencia enderecoReferencia, String complementoEndereco, SetorComercial setorComercial, Localidade localidade) {

		this.id = id;
		this.numeroImovel = numeroImovel;
		this.logradouroCep = logradouroCep;
		this.quadra = quadra;
		// this.hidrometro = hidrometro;
		this.enderecoReferencia = enderecoReferencia;
		this.complementoEndereco = complementoEndereco;
		this.setorComercial = setorComercial;
		this.localidade = localidade;
	}

	// Construido por Sávio Luiz para setar o id no objeto imóvel
	public Imovel(Integer id) {

		this.id = id;
	}

	public Imovel(Integer id, String numeroImovel, LogradouroCep logradouroCep, LogradouroBairro logradouroBairro, Quadra quadra,
					Hidrometro hidrometro, EnderecoReferencia enderecoReferencia, String complementoEndereco,
					SetorComercial setorComercial, Localidade localidade) {

		this.id = id;
		this.numeroImovel = numeroImovel;
		this.logradouroCep = logradouroCep;
		this.logradouroBairro = logradouroBairro;
		this.quadra = quadra;
		// this.hidrometro = hidrometro;
		this.enderecoReferencia = enderecoReferencia;
		this.complementoEndereco = complementoEndereco;
		this.setorComercial = setorComercial;
		this.localidade = localidade;
	}

	public Imovel(Integer id, String numeroImovel, LogradouroCep logradouroCep, LogradouroBairro logradouroBairro, Quadra quadra,
					Hidrometro hidrometro, EnderecoReferencia enderecoReferencia, String complementoEndereco,
					SetorComercial setorComercial, Localidade localidade, Date ultimaAlteracao) {

		this.id = id;
		this.numeroImovel = numeroImovel;
		this.logradouroCep = logradouroCep;
		this.logradouroBairro = logradouroBairro;
		this.quadra = quadra;
		// this.hidrometro = hidrometro;
		this.enderecoReferencia = enderecoReferencia;
		this.complementoEndereco = complementoEndereco;
		this.setorComercial = setorComercial;
		this.localidade = localidade;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	// ---------Fim Construtores Antigos

	/**
	 * full constructor
	 * 
	 * @param lote
	 *            Description of the Parameter
	 * @param subLote
	 *            Description of the Parameter
	 * @param testadaLote
	 *            Description of the Parameter
	 * @param numeroImovel
	 *            Description of the Parameter
	 * @param complementoEndereco
	 *            Description of the Parameter
	 * @param areaConstruida
	 *            Description of the Parameter
	 * @param indicadorImovelCondominio
	 *            Description of the Parameter
	 * @param volumeReservatorioSuperior
	 *            Description of the Parameter
	 * @param volumeReservatorioInferior
	 *            Description of the Parameter
	 * @param volumePiscina
	 *            Description of the Parameter
	 * @param dataSupressaoParcial
	 *            Description of the Parameter
	 * @param dataInfracao
	 *            Description of the Parameter
	 * @param numeroPontosUtilizacao
	 *            Description of the Parameter
	 * @param numeroMorador
	 *            Description of the Parameter
	 * @param numeroRetificacao
	 *            Description of the Parameter
	 * @param numeroLeituraExcecao
	 *            Description of the Parameter
	 * @param numeroParcelamento
	 *            Description of the Parameter
	 * @param numeroReparcelamento
	 *            Description of the Parameter
	 * @param diaVencimento
	 *            Description of the Parameter
	 * @param numeroIptu
	 *            Description of the Parameter
	 * @param numeroCelpe
	 *            Description of the Parameter
	 * @param indicadorConta
	 *            Description of the Parameter
	 * @param indicadorEmissaoExtratoFaturamento
	 *            Description of the Parameter
	 * @param indicadorDebitoConta
	 *            Description of the Parameter
	 * @param indicadorExclusao
	 *            Description of the Parameter
	 * @param coordenadaX
	 *            Description of the Parameter
	 * @param coordenadaY
	 *            Description of the Parameter
	 * @param ligacaoEsgoto
	 *            Description of the Parameter
	 * @param tarifaSocialDado
	 *            Description of the Parameter
	 * @param ligacaoAgua
	 *            Description of the Parameter
	 * @param imovelEnderecoAnterior
	 *            Description of the Parameter
	 * @param imovelPrincipal
	 *            Description of the Parameter
	 * @param imovelCondominio
	 *            Description of the Parameter
	 * @param ligacaoEsgotoSituacao
	 *            Description of the Parameter
	 * @param logradouro
	 *            Description of the Parameter
	 * @param hidrometroInstalacaoHistorico
	 *            Description of the Parameter
	 * @param leituraAnormalidade
	 *            Description of the Parameter
	 * @param eloAnormalidade
	 *            Description of the Parameter
	 * @param setorComercial
	 *            Description of the Parameter
	 * @param areaConstruidaFaixa
	 *            Description of the Parameter
	 * @param pavimentoCalcada
	 *            Description of the Parameter
	 * @param imovelPerfil
	 *            Description of the Parameter
	 * @param reservatorioVolumeFaixaSuperior
	 *            Description of the Parameter
	 * @param reservatorioVolumeFaixaInferior
	 *            Description of the Parameter
	 * @param localidade
	 *            Description of the Parameter
	 * @param quadra
	 *            Description of the Parameter
	 * @param cobrancaSituacaoTipo
	 *            Description of the Parameter
	 * @param pavimentoRua
	 *            Description of the Parameter
	 * @param enderecoReferencia
	 *            Descrição do parâmetro
	 * @param nomeConta
	 *            Descrição do parâmetro
	 * @param cadastroOcorrencia
	 *            Description of the Parameter
	 * @param pocoTipo
	 *            Description of the Parameter
	 * @param ligacaoAguaSituacao
	 *            Description of the Parameter
	 * @param despejo
	 *            Description of the Parameter
	 * @param cep
	 *            Description of the Parameter
	 * @param faturamentoSituacaoTipo
	 *            Description of the Parameter
	 * @param fonteAbastecimento
	 *            Description of the Parameter
	 * @param piscinaVolumeFaixa
	 *            Description of the Parameter
	 * @param cobrancaSituacao
	 *            Description of the Parameter
	 * @param ultimaAlteracao
	 *            Descrição do parâmetro
	 */
	public Imovel(	short lote,
					short subLote,
					Short testadaLote,
					Short numeroReparcelamentoConsecutivos,
					String numeroImovel,
					String complementoEndereco,
					// byte[] fotoFachada,
					BigDecimal areaConstruida, Short indicadorImovelCondominio, BigDecimal volumeReservatorioSuperior,
					BigDecimal volumeReservatorioInferior, BigDecimal volumePiscina, Date dataSupressaoParcial, Date dataInfracao,
					Short numeroPontosUtilizacao, Short numeroQuarto, Short numeroBanheiro, Short numeroAdulto, Short numeroCrianca,
					Short numeroMoradorTrabalhador, Short numeroMorador, Short numeroRetificacao, Short numeroLeituraExcecao,
					Short numeroParcelamento, Short numeroReparcelamento, Short diaVencimento, BigDecimal numeroIptu, Long numeroCelpe,
					Short indicadorConta, Short indicadorEmissaoExtratoFaturamento, Short indicadorDebitoConta, Short indicadorExclusao,
					BigDecimal coordenadaX, BigDecimal coordenadaY, LigacaoEsgoto ligacaoEsgoto, LigacaoAgua ligacaoAgua,
					ImovelEnderecoAnterior imovelEnderecoAnterior, gcom.cadastro.imovel.Imovel imovelPrincipal,
					gcom.cadastro.imovel.Imovel imovelCondominio, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico, LeituraAnormalidade leituraAnormalidade,
					gcom.cadastro.imovel.EloAnormalidade eloAnormalidade, SetorComercial setorComercial,
					gcom.cadastro.imovel.AreaConstruidaFaixa areaConstruidaFaixa,
					gcom.cadastro.imovel.RendaFamiliarFaixa rendaFamiliarFaixa, gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada,
					gcom.cadastro.imovel.ImovelPerfil imovelPerfil,
					gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaSuperior,
					gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaInferior, Localidade localidade, Quadra quadra,
					Hidrometro hidrometro, CobrancaSituacaoTipo cobrancaSituacaoTipo, gcom.cadastro.imovel.PavimentoRua pavimentoRua,
					EnderecoReferencia enderecoReferencia, gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia,
					gcom.cadastro.imovel.PocoTipo pocoTipo, LigacaoAguaSituacao ligacaoAguaSituacao, gcom.cadastro.imovel.Despejo despejo,
					FaturamentoSituacaoTipo faturamentoSituacaoTipo, gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento,
					gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa, FaturamentoTipo faturamentoTipo, Date ultimaAlteracao,
					LogradouroCep logradouroCep) {

		this.lote = lote;
		this.subLote = subLote;
		this.testadaLote = testadaLote;
		this.numeroImovel = numeroImovel;
		this.numeroReparcelamentoConsecutivos = numeroReparcelamentoConsecutivos;
		this.complementoEndereco = complementoEndereco;
		// this.fotoFachada = fotoFachada;
		this.areaConstruida = areaConstruida;
		this.indicadorImovelCondominio = indicadorImovelCondominio;
		this.volumeReservatorioSuperior = volumeReservatorioSuperior;
		this.volumeReservatorioInferior = volumeReservatorioInferior;
		this.volumePiscina = volumePiscina;
		this.dataSupressaoParcial = dataSupressaoParcial;
		this.dataInfracao = dataInfracao;
		this.numeroPontosUtilizacao = numeroPontosUtilizacao;
		this.numeroQuarto = numeroQuarto;
		this.numeroBanheiro = numeroBanheiro;
		this.numeroAdulto = numeroAdulto;
		this.numeroCrianca = numeroCrianca;
		this.numeroMoradorTrabalhador = numeroMoradorTrabalhador;
		this.numeroMorador = numeroMorador;
		this.numeroRetificacao = numeroRetificacao;
		this.numeroLeituraExcecao = numeroLeituraExcecao;
		this.numeroParcelamento = numeroParcelamento;
		this.numeroReparcelamento = numeroReparcelamento;
		this.diaVencimento = diaVencimento;
		this.numeroIptu = numeroIptu;
		this.numeroCelpe = numeroCelpe;
		this.indicadorConta = indicadorConta;
		this.indicadorEmissaoExtratoFaturamento = indicadorEmissaoExtratoFaturamento;
		this.indicadorDebitoConta = indicadorDebitoConta;
		this.indicadorExclusao = indicadorExclusao;
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		this.ligacaoEsgoto = ligacaoEsgoto;
		this.ligacaoAgua = ligacaoAgua;
		this.imovelEnderecoAnterior = imovelEnderecoAnterior;
		this.imovelPrincipal = imovelPrincipal;
		this.imovelCondominio = imovelCondominio;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
		this.leituraAnormalidade = leituraAnormalidade;
		this.eloAnormalidade = eloAnormalidade;
		this.setorComercial = setorComercial;
		this.areaConstruidaFaixa = areaConstruidaFaixa;
		this.rendaFamiliarFaixa = rendaFamiliarFaixa;
		this.pavimentoCalcada = pavimentoCalcada;
		this.imovelPerfil = imovelPerfil;
		this.reservatorioVolumeFaixaSuperior = reservatorioVolumeFaixaSuperior;
		this.reservatorioVolumeFaixaInferior = reservatorioVolumeFaixaInferior;
		this.localidade = localidade;
		this.quadra = quadra;
		// this.hidrometro = hidrometro;
		this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
		this.pavimentoRua = pavimentoRua;
		this.enderecoReferencia = enderecoReferencia;

		this.cadastroOcorrencia = cadastroOcorrencia;
		this.pocoTipo = pocoTipo;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.despejo = despejo;
		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
		this.fonteAbastecimento = fonteAbastecimento;
		this.piscinaVolumeFaixa = piscinaVolumeFaixa;
		this.faturamentoTipo = faturamentoTipo;
		this.ultimaAlteracao = ultimaAlteracao;
		this.logradouroCep = logradouroCep;
	}

	/**
	 * Constructor for the Imovel object
	 * 
	 * @param id
	 *            Description of the Parameter
	 * @param numeroImovel
	 *            Description of the Parameter
	 * @param logradouro
	 *            Description of the Parameter
	 * @param quadra
	 *            Description of the Parameter
	 * @param enderecoReferencia
	 *            Description of the Parameter
	 * @param cep
	 *            Description of the Parameter
	 * @param complementoEndereco
	 *            Description of the Parameter
	 */
	public Imovel(Integer id, String numeroImovel, Quadra quadra, Hidrometro hidrometro, EnderecoReferencia enderecoReferencia,
					String complementoEndereco, FaturamentoTipo faturamentoTipo) {

		this.id = id;
		this.numeroImovel = numeroImovel;
		this.quadra = quadra;
		// this.hidrometro = hidrometro;
		this.enderecoReferencia = enderecoReferencia;
		this.complementoEndereco = complementoEndereco;
		this.faturamentoTipo = faturamentoTipo;
	}

	/**
	 * default constructor
	 */
	public Imovel() {

	}

	/**
	 * minimal constructor
	 * 
	 * @param lote
	 *            Description of the Parameter
	 * @param subLote
	 *            Description of the Parameter
	 * @param testadaLote
	 *            Description of the Parameter
	 * @param numeroImovel
	 *            Description of the Parameter
	 * @param complementoEndereco
	 *            Description of the Parameter
	 * @param numeroPontosUtilizacao
	 *            Description of the Parameter
	 * @param numeroMorador
	 *            Description of the Parameter
	 * @param imovelPrincipal
	 *            Description of the Parameter
	 * @param imovelCondominio
	 *            Description of the Parameter
	 * @param ligacaoEsgotoSituacao
	 *            Description of the Parameter
	 * @param logradouro
	 *            Description of the Parameter
	 * @param hidrometroInstalacaoHistorico
	 *            Description of the Parameter
	 * @param leituraAnormalidade
	 *            Description of the Parameter
	 * @param eloAnormalidade
	 *            Description of the Parameter
	 * @param setorComercial
	 *            Description of the Parameter
	 * @param areaConstruidaFaixa
	 *            Description of the Parameter
	 * @param pavimentoCalcada
	 *            Description of the Parameter
	 * @param imovelPerfil
	 *            Description of the Parameter
	 * @param reservatorioVolumeFaixaSuperior
	 *            Description of the Parameter
	 * @param reservatorioVolumeFaixaInferior
	 *            Description of the Parameter
	 * @param localidade
	 *            Description of the Parameter
	 * @param quadra
	 *            Description of the Parameter
	 * @param cobrancaSituacaoTipo
	 *            Description of the Parameter
	 * @param pavimentoRua
	 *            Description of the Parameter
	 * @param enderecoImovelReferencia
	 *            Description of the Parameter
	 * @param nomeConta
	 *            Descrição do parâmetro
	 * @param cadastroOcorrencia
	 *            Description of the Parameter
	 * @param pocoTipo
	 *            Description of the Parameter
	 * @param ligacaoAguaSituacao
	 *            Description of the Parameter
	 * @param despejo
	 *            Description of the Parameter
	 * @param cep
	 *            Description of the Parameter
	 * @param faturamentoSituacaoTipo
	 *            Description of the Parameter
	 * @param fonteAbastecimento
	 *            Description of the Parameter
	 * @param piscinaVolumeFaixa
	 *            Description of the Parameter
	 * @param cobrancaSituacao
	 *            Description of the Parameter
	 */
	public Imovel(short lote, short subLote, Short testadaLote, String numeroImovel, String complementoEndereco,
					Short numeroPontosUtilizacao, Short numeroQuarto, Short numeroBanheiro, Short numeroAdulto, Short numeroCrianca,
					Short numeroMoradorTrabalhador, Short numeroMorador, gcom.cadastro.imovel.Imovel imovelPrincipal,
					gcom.cadastro.imovel.Imovel imovelCondominio, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico, LeituraAnormalidade leituraAnormalidade,
					gcom.cadastro.imovel.EloAnormalidade eloAnormalidade, SetorComercial setorComercial,
					gcom.cadastro.imovel.AreaConstruidaFaixa areaConstruidaFaixa,
					gcom.cadastro.imovel.RendaFamiliarFaixa rendaFamiliarFaixa, gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada,
					gcom.cadastro.imovel.ImovelPerfil imovelPerfil,
					gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaSuperior,
					gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaInferior, Localidade localidade, Quadra quadra,
					Hidrometro hidrometro, CobrancaSituacaoTipo cobrancaSituacaoTipo, gcom.cadastro.imovel.PavimentoRua pavimentoRua,
					EnderecoReferencia enderecoImovelReferencia, gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia,
					gcom.cadastro.imovel.PocoTipo pocoTipo, LigacaoAguaSituacao ligacaoAguaSituacao, gcom.cadastro.imovel.Despejo despejo,
					FaturamentoSituacaoTipo faturamentoSituacaoTipo, gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento,
					gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa) {

		this.lote = lote;
		this.subLote = subLote;
		this.testadaLote = testadaLote;
		this.numeroImovel = numeroImovel;
		this.complementoEndereco = complementoEndereco;
		this.numeroPontosUtilizacao = numeroPontosUtilizacao;
		this.numeroQuarto = numeroQuarto;
		this.numeroBanheiro = numeroBanheiro;
		this.numeroAdulto = numeroAdulto;
		this.numeroCrianca = numeroCrianca;
		this.numeroMoradorTrabalhador = numeroMoradorTrabalhador;
		this.numeroMorador = numeroMorador;
		this.imovelPrincipal = imovelPrincipal;
		this.imovelCondominio = imovelCondominio;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
		this.leituraAnormalidade = leituraAnormalidade;
		this.eloAnormalidade = eloAnormalidade;
		this.setorComercial = setorComercial;
		this.rendaFamiliarFaixa = rendaFamiliarFaixa;
		this.pavimentoCalcada = pavimentoCalcada;
		this.imovelPerfil = imovelPerfil;
		this.reservatorioVolumeFaixaSuperior = reservatorioVolumeFaixaSuperior;
		this.reservatorioVolumeFaixaInferior = reservatorioVolumeFaixaInferior;
		this.localidade = localidade;
		this.quadra = quadra;
		// this.hidrometro = hidrometro;
		this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
		this.pavimentoRua = pavimentoRua;
		this.enderecoReferencia = enderecoImovelReferencia;
		this.cadastroOcorrencia = cadastroOcorrencia;
		this.pocoTipo = pocoTipo;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.despejo = despejo;
		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
		this.fonteAbastecimento = fonteAbastecimento;
		this.piscinaVolumeFaixa = piscinaVolumeFaixa;

	}

	public Imovel(Integer id, gcom.cadastro.localidade.Localidade localidade, gcom.cadastro.localidade.SetorComercial setorComercial,
					gcom.cadastro.localidade.Quadra quadra, Hidrometro hidrometro, short lote, short subLote,
					FaturamentoSituacaoTipo faturamentoSituacaoTipo) {

		this.id = id;
		this.localidade = localidade;
		this.setorComercial = setorComercial;
		this.quadra = quadra;
		// this.hidrometro = hidrometro;
		this.lote = lote;
		this.subLote = subLote;
		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
	}

	/**
	 * Gets the id attribute of the Imovel object
	 * 
	 * @return The id value
	 */
	public Integer getId(){

		return this.id;
	}

	/**
	 * Gets the id attribute of the Imovel object
	 * Utilizado para documentos emitidos
	 * 
	 * @return The id value
	 */
	public String getIdParametrizado(){

		// Caso a matrícula tenha a quantidade de dígitos menor do que a cadastrada no parâmetro
		// P_NUMERO_DIGITOS_MATRICULA_IMOVEL adiciona zeros a esquerda do número
		String matriculaImovel = Util.retornaMatriculaImovelParametrizada(this.id);

		return matriculaImovel;
	}

	/**
	 * Sets the id attribute of the Imovel object
	 * 
	 * @param id
	 *            The new id value
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * Gets the lote attribute of the Imovel object
	 * 
	 * @return The lote value
	 */
	public short getLote(){

		return this.lote;
	}

	/**
	 * Sets the lote attribute of the Imovel object
	 * 
	 * @param lote
	 *            The new lote value
	 */
	public void setLote(short lote){

		this.lote = lote;
	}

	/**
	 * Gets the subLote attribute of the Imovel object
	 * 
	 * @return The subLote value
	 */
	public short getSubLote(){

		return this.subLote;
	}

	/**
	 * Sets the subLote attribute of the Imovel object
	 * 
	 * @param subLote
	 *            The new subLote value
	 */
	public void setSubLote(short subLote){

		this.subLote = subLote;
	}

	/**
	 * Gets the testadaLote attribute of the Imovel object
	 * 
	 * @return The testadaLote value
	 */
	public Short getTestadaLote(){

		return this.testadaLote;
	}

	/**
	 * Sets the testadaLote attribute of the Imovel object
	 * 
	 * @param testadaLote
	 *            The new testadaLote value
	 */
	public void setTestadaLote(Short testadaLote){

		this.testadaLote = testadaLote;
	}

	/**
	 * Gets the numeroImovel attribute of the Imovel object
	 * 
	 * @return The numeroImovel value
	 */
	public String getNumeroImovel(){

		return this.numeroImovel;
	}

	/**
	 * Sets the numeroImovel attribute of the Imovel object
	 * 
	 * @param numeroImovel
	 *            The new numeroImovel value
	 */
	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	/**
	 * Gets the complementoEndereco attribute of the Imovel object
	 * 
	 * @return The complementoEndereco value
	 */
	public String getComplementoEndereco(){

		return this.complementoEndereco;
	}

	/**
	 * Gets the ultimaAlteracao attribute of the Imovel object
	 * 
	 * @return The ultimaAlteracao value
	 */

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	/**
	 * Sets the ultimaAlteracao attribute of the Imovel object
	 * 
	 * @param ultimaAlteracao
	 *            The new complementoEndereco value
	 */

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * Sets the complementoEndereco attribute of the Imovel object
	 * 
	 * @param complementoEndereco
	 *            The new complementoEndereco value
	 */
	public void setComplementoEndereco(String complementoEndereco){

		this.complementoEndereco = complementoEndereco;
	}

	/**
	 * @return the fotoFachada
	 */
	public byte[] getFotoFachada(){

		return fotoFachada;
	}

	/**
	 * @return the fotoFachadaBlob
	 */
	public byte[] getFotoFachadaBlob(){

		return this.fotoFachada;
	}

	/**
	 * @param the
	 *            fotoFachada to set
	 */
	public void setFotoFachada(byte[] fotoFachada){

		this.fotoFachada = fotoFachada;
		this.popularFotoFachadaBlob();
	}

	/**
	 * @param fotoFachadaBlob
	 *            the fotoFachadaBlob to set
	 *            Apenas para o Tratamento de Blobs do Hibernate, não invocar.
	 */
	public void setFotoFachadaBlob(Blob fotoFachadaBlob){

		this.fotoFachada = IoUtil.toByteArray(fotoFachadaBlob);
	}

	private void popularFotoFachadaBlob(){

		if(this.getFotoFachadaBlob() != null){
			this.fotoFachadaBlob = Hibernate.createBlob(this.getFotoFachadaBlob());
		}else{
			this.fotoFachadaBlob = null;
		}
	}

	/**
	 * Gets the areaConstruida attribute of the Imovel object
	 * 
	 * @return The areaConstruida value
	 */
	public BigDecimal getAreaConstruida(){

		return this.areaConstruida;
	}

	/**
	 * Sets the areaConstruida attribute of the Imovel object
	 * 
	 * @param areaConstruida
	 *            The new areaConstruida value
	 */
	public void setAreaConstruida(BigDecimal areaConstruida){

		this.areaConstruida = areaConstruida;
	}

	/**
	 * Gets the indicadorImovelCondominio attribute of the Imovel object
	 * 
	 * @return The indicadorImovelCondominio value
	 */
	public Short getIndicadorImovelCondominio(){

		return this.indicadorImovelCondominio;
	}

	/**
	 * Sets the indicadorImovelCondominio attribute of the Imovel object
	 * 
	 * @param indicadorImovelCondominio
	 *            The new indicadorImovelCondominio value
	 */
	public void setIndicadorImovelCondominio(Short indicadorImovelCondominio){

		this.indicadorImovelCondominio = indicadorImovelCondominio;
	}

	/**
	 * Gets the volumeReservatorioSuperior attribute of the Imovel object
	 * 
	 * @return The volumeReservatorioSuperior value
	 */
	public BigDecimal getVolumeReservatorioSuperior(){

		return this.volumeReservatorioSuperior;
	}

	/**
	 * Sets the volumeReservatorioSuperior attribute of the Imovel object
	 * 
	 * @param volumeReservatorioSuperior
	 *            The new volumeReservatorioSuperior value
	 */
	public void setVolumeReservatorioSuperior(BigDecimal volumeReservatorioSuperior){

		this.volumeReservatorioSuperior = volumeReservatorioSuperior;
	}

	/**
	 * Gets the volumeReservatorioInferior attribute of the Imovel object
	 * 
	 * @return The volumeReservatorioInferior value
	 */
	public BigDecimal getVolumeReservatorioInferior(){

		return this.volumeReservatorioInferior;
	}

	/**
	 * Sets the volumeReservatorioInferior attribute of the Imovel object
	 * 
	 * @param volumeReservatorioInferior
	 *            The new volumeReservatorioInferior value
	 */
	public void setVolumeReservatorioInferior(BigDecimal volumeReservatorioInferior){

		this.volumeReservatorioInferior = volumeReservatorioInferior;
	}

	/**
	 * Gets the volumePiscina attribute of the Imovel object
	 * 
	 * @return The volumePiscina value
	 */
	public BigDecimal getVolumePiscina(){

		return this.volumePiscina;
	}

	/**
	 * Sets the volumePiscina attribute of the Imovel object
	 * 
	 * @param volumePiscina
	 *            The new volumePiscina value
	 */
	public void setVolumePiscina(BigDecimal volumePiscina){

		this.volumePiscina = volumePiscina;
	}

	/**
	 * Gets the dataSupressaoParcial attribute of the Imovel object
	 * 
	 * @return The dataSupressaoParcial value
	 */
	public Date getDataSupressaoParcial(){

		return this.dataSupressaoParcial;
	}

	/**
	 * Sets the dataSupressaoParcial attribute of the Imovel object
	 * 
	 * @param dataSupressaoParcial
	 *            The new dataSupressaoParcial value
	 */
	public void setDataSupressaoParcial(Date dataSupressaoParcial){

		this.dataSupressaoParcial = dataSupressaoParcial;
	}

	/**
	 * Gets the dataInfracao attribute of the Imovel object
	 * 
	 * @return The dataInfracao value
	 */
	public Date getDataInfracao(){

		return this.dataInfracao;
	}

	/**
	 * Sets the dataInfracao attribute of the Imovel object
	 * 
	 * @param dataInfracao
	 *            The new dataInfracao value
	 */
	public void setDataInfracao(Date dataInfracao){

		this.dataInfracao = dataInfracao;
	}

	/**
	 * Gets the numeroPontosUtilizacao attribute of the Imovel object
	 * 
	 * @return The numeroPontosUtilizacao value
	 */
	public Short getNumeroPontosUtilizacao(){

		return this.numeroPontosUtilizacao;
	}

	/**
	 * Sets the numeroPontosUtilizacao attribute of the Imovel object
	 * 
	 * @param numeroPontosUtilizacao
	 *            The new numeroPontosUtilizacao value
	 */
	public void setNumeroPontosUtilizacao(Short numeroPontosUtilizacao){

		this.numeroPontosUtilizacao = numeroPontosUtilizacao;
	}

	/**
	 * @return the numeroQuarto
	 */
	public Short getNumeroQuarto(){

		return numeroQuarto;
	}

	/**
	 * @param numeroQuarto
	 *            the numeroQuarto to set
	 */
	public void setNumeroQuarto(Short numeroQuarto){

		this.numeroQuarto = numeroQuarto;
	}

	/**
	 * @return the numeroBanheiro
	 */
	public Short getNumeroBanheiro(){

		return numeroBanheiro;
	}

	/**
	 * @param numeroBanheiro
	 *            the numeroBanheiro to set
	 */
	public void setNumeroBanheiro(Short numeroBanheiro){

		this.numeroBanheiro = numeroBanheiro;
	}

	/**
	 * @return the numeroAdulto
	 */
	public Short getNumeroAdulto(){

		return numeroAdulto;
	}

	/**
	 * @param numeroAdulto
	 *            the numeroAdulto to set
	 */
	public void setNumeroAdulto(Short numeroAdulto){

		this.numeroAdulto = numeroAdulto;
	}

	/**
	 * @return the numeroCrianca
	 */
	public Short getNumeroCrianca(){

		return numeroCrianca;
	}

	/**
	 * @param numeroCrianca
	 *            the numeroCrianca to set
	 */
	public void setNumeroCrianca(Short numeroCrianca){

		this.numeroCrianca = numeroCrianca;
	}

	/**
	 * @return the numeroMoradorTrabalhador
	 */
	public Short getNumeroMoradorTrabalhador(){

		return numeroMoradorTrabalhador;
	}

	/**
	 * @param numeroMoradorTrabalhador
	 *            the numeroMoradorTrabalhador to set
	 */
	public void setNumeroMoradorTrabalhador(Short numeroMoradorTrabalhador){

		this.numeroMoradorTrabalhador = numeroMoradorTrabalhador;
	}

	/**
	 * Gets the numeroMorador attribute of the Imovel object
	 * 
	 * @return The numeroMorador value
	 */
	public Short getNumeroMorador(){

		return this.numeroMorador;
	}

	/**
	 * Sets the numeroMorador attribute of the Imovel object
	 * 
	 * @param numeroMorador
	 *            The new numeroMorador value
	 */
	public void setNumeroMorador(Short numeroMorador){

		this.numeroMorador = numeroMorador;
	}

	/**
	 * Gets the numeroRetificacao attribute of the Imovel object
	 * 
	 * @return The numeroRetificacao value
	 */
	public Short getNumeroRetificacao(){

		return this.numeroRetificacao;
	}

	/**
	 * Sets the numeroRetificacao attribute of the Imovel object
	 * 
	 * @param numeroRetificacao
	 *            The new numeroRetificacao value
	 */
	public void setNumeroRetificacao(Short numeroRetificacao){

		this.numeroRetificacao = numeroRetificacao;
	}

	/**
	 * Gets the numeroLeituraExcecao attribute of the Imovel object
	 * 
	 * @return The numeroLeituraExcecao value
	 */
	public Short getNumeroLeituraExcecao(){

		return this.numeroLeituraExcecao;
	}

	/**
	 * Sets the numeroLeituraExcecao attribute of the Imovel object
	 * 
	 * @param numeroLeituraExcecao
	 *            The new numeroLeituraExcecao value
	 */
	public void setNumeroLeituraExcecao(Short numeroLeituraExcecao){

		this.numeroLeituraExcecao = numeroLeituraExcecao;
	}

	/**
	 * Gets the numeroParcelamento attribute of the Imovel object
	 * 
	 * @return The numeroParcelamento value
	 */
	public Short getNumeroParcelamento(){

		return this.numeroParcelamento;
	}

	/**
	 * Sets the numeroParcelamento attribute of the Imovel object
	 * 
	 * @param numeroParcelamento
	 *            The new numeroParcelamento value
	 */
	public void setNumeroParcelamento(Short numeroParcelamento){

		this.numeroParcelamento = numeroParcelamento;
	}

	/**
	 * Gets the numeroReparcelamento attribute of the Imovel object
	 * 
	 * @return The numeroReparcelamento value
	 */
	public Short getNumeroReparcelamento(){

		return this.numeroReparcelamento;
	}

	/**
	 * Sets the numeroReparcelamento attribute of the Imovel object
	 * 
	 * @param numeroReparcelamento
	 *            The new numeroReparcelamento value
	 */
	public void setNumeroReparcelamento(Short numeroReparcelamento){

		this.numeroReparcelamento = numeroReparcelamento;
	}

	/**
	 * Gets the diaVencimento attribute of the Imovel object
	 * 
	 * @return The diaVencimento value
	 */
	public Short getDiaVencimento(){

		return this.diaVencimento;
	}

	/**
	 * Sets the diaVencimento attribute of the Imovel object
	 * 
	 * @param diaVencimento
	 *            The new diaVencimento value
	 */
	public void setDiaVencimento(Short diaVencimento){

		this.diaVencimento = diaVencimento;
	}

	/**
	 * Gets the numeroIptu attribute of the Imovel object
	 * 
	 * @return The numeroIptu value
	 */
	public BigDecimal getNumeroIptu(){

		return this.numeroIptu;
	}

	/**
	 * Sets the numeroIptu attribute of the Imovel object
	 * 
	 * @param numeroIptu
	 *            The new numeroIptu value
	 */
	public void setNumeroIptu(BigDecimal numeroIptu){

		this.numeroIptu = numeroIptu;
	}

	/**
	 * Gets the numeroCelpe attribute of the Imovel object
	 * 
	 * @return The numeroCelpe value
	 */
	public Long getNumeroCelpe(){

		return this.numeroCelpe;
	}

	/**
	 * Sets the numeroCelpe attribute of the Imovel object
	 * 
	 * @param numeroCelpe
	 *            The new numeroCelpe value
	 */
	public void setNumeroCelpe(Long numeroCelpe){

		this.numeroCelpe = numeroCelpe;
	}

	/**
	 * Gets the indicadorConta attribute of the Imovel object
	 * 
	 * @return The indicadorConta value
	 */
	public Short getIndicadorConta(){

		return this.indicadorConta;
	}

	/**
	 * Sets the indicadorConta attribute of the Imovel object
	 * 
	 * @param indicadorConta
	 *            The new indicadorConta value
	 */
	public void setIndicadorConta(Short indicadorConta){

		this.indicadorConta = indicadorConta;
	}

	/**
	 * Gets the indicadorEmissaoExtratoFaturamento attribute of the Imovel
	 * object
	 * 
	 * @return The indicadorEmissaoExtratoFaturamento value
	 */
	public Short getIndicadorEmissaoExtratoFaturamento(){

		return this.indicadorEmissaoExtratoFaturamento;
	}

	/**
	 * Sets the indicadorEmissaoExtratoFaturamento attribute of the Imovel
	 * object
	 * 
	 * @param indicadorEmissaoExtratoFaturamento
	 *            The new indicadorEmissaoExtratoFaturamento value
	 */
	public void setIndicadorEmissaoExtratoFaturamento(Short indicadorEmissaoExtratoFaturamento){

		this.indicadorEmissaoExtratoFaturamento = indicadorEmissaoExtratoFaturamento;
	}

	/**
	 * Gets the indicadorDebitoConta attribute of the Imovel object
	 * 
	 * @return The indicadorDebitoConta value
	 */
	public Short getIndicadorDebitoConta(){

		return this.indicadorDebitoConta;
	}

	/**
	 * Sets the indicadorDebitoConta attribute of the Imovel object
	 * 
	 * @param indicadorDebitoConta
	 *            The new indicadorDebitoConta value
	 */
	public void setIndicadorDebitoConta(Short indicadorDebitoConta){

		this.indicadorDebitoConta = indicadorDebitoConta;
	}

	/**
	 * Gets the indicadorExclusao attribute of the Imovel object
	 * 
	 * @return The indicadorExclusao value
	 */
	public Short getIndicadorExclusao(){

		return this.indicadorExclusao;
	}

	/**
	 * Sets the indicadorExclusao attribute of the Imovel object
	 * 
	 * @param indicadorExclusao
	 *            The new indicadorExclusao value
	 */
	public void setIndicadorExclusao(Short indicadorExclusao){

		this.indicadorExclusao = indicadorExclusao;
	}

	/**
	 * Gets the coordenadaX attribute of the Imovel object
	 * 
	 * @return The coordenadaX value
	 */
	public BigDecimal getCoordenadaX(){

		return this.coordenadaX;
	}

	/**
	 * Sets the coordenadaX attribute of the Imovel object
	 * 
	 * @param coordenadaX
	 *            The new coordenadaX value
	 */
	public void setCoordenadaX(BigDecimal coordenadaX){

		this.coordenadaX = coordenadaX;
	}

	/**
	 * Gets the coordenadaY attribute of the Imovel object
	 * 
	 * @return The coordenadaY value
	 */
	public BigDecimal getCoordenadaY(){

		return this.coordenadaY;
	}

	/**
	 * Sets the coordenadaY attribute of the Imovel object
	 * 
	 * @param coordenadaY
	 *            The new coordenadaY value
	 */
	public void setCoordenadaY(BigDecimal coordenadaY){

		this.coordenadaY = coordenadaY;
	}

	/**
	 * Gets the ligacaoEsgoto attribute of the Imovel object
	 * 
	 * @return The ligacaoEsgoto value
	 */
	public LigacaoEsgoto getLigacaoEsgoto(){

		return this.ligacaoEsgoto;
	}

	/**
	 * Sets the ligacaoEsgoto attribute of the Imovel object
	 * 
	 * @param ligacaoEsgoto
	 *            The new ligacaoEsgoto value
	 */
	public void setLigacaoEsgoto(LigacaoEsgoto ligacaoEsgoto){

		this.ligacaoEsgoto = ligacaoEsgoto;
	}

	/**
	 * Gets the ligacaoAgua attribute of the Imovel object
	 * 
	 * @return The ligacaoAgua value
	 */
	public LigacaoAgua getLigacaoAgua(){

		return this.ligacaoAgua;
	}

	/**
	 * Sets the ligacaoAgua attribute of the Imovel object
	 * 
	 * @param ligacaoAgua
	 *            The new ligacaoAgua value
	 */
	public void setLigacaoAgua(LigacaoAgua ligacaoAgua){

		this.ligacaoAgua = ligacaoAgua;
	}

	/**
	 * Gets the imovelEnderecoAnterior attribute of the Imovel object
	 * 
	 * @return The imovelEnderecoAnterior value
	 */
	public ImovelEnderecoAnterior getImovelEnderecoAnterior(){

		return this.imovelEnderecoAnterior;
	}

	/**
	 * Sets the imovelEnderecoAnterior attribute of the Imovel object
	 * 
	 * @param imovelEnderecoAnterior
	 *            The new imovelEnderecoAnterior value
	 */
	public void setImovelEnderecoAnterior(ImovelEnderecoAnterior imovelEnderecoAnterior){

		this.imovelEnderecoAnterior = imovelEnderecoAnterior;
	}

	/**
	 * Gets the imovelPrincipal attribute of the Imovel object
	 * 
	 * @return The imovelPrincipal value
	 */
	public gcom.cadastro.imovel.Imovel getImovelPrincipal(){

		return this.imovelPrincipal;
	}

	/**
	 * Sets the imovelPrincipal attribute of the Imovel object
	 * 
	 * @param imovelPrincipal
	 *            The new imovelPrincipal value
	 */
	public void setImovelPrincipal(gcom.cadastro.imovel.Imovel imovelPrincipal){

		this.imovelPrincipal = imovelPrincipal;
	}

	/**
	 * Gets the imovelCondominio attribute of the Imovel object
	 * 
	 * @return The imovelCondominio value
	 */
	public gcom.cadastro.imovel.Imovel getImovelCondominio(){

		return this.imovelCondominio;
	}

	/**
	 * Sets the imovelCondominio attribute of the Imovel object
	 * 
	 * @param imovelCondominio
	 *            The new imovelCondominio value
	 */
	public void setImovelCondominio(gcom.cadastro.imovel.Imovel imovelCondominio){

		this.imovelCondominio = imovelCondominio;
	}

	/**
	 * Gets the ligacaoEsgotoSituacao attribute of the Imovel object
	 * 
	 * @return The ligacaoEsgotoSituacao value
	 */
	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return this.ligacaoEsgotoSituacao;
	}

	/**
	 * Sets the ligacaoEsgotoSituacao attribute of the Imovel object
	 * 
	 * @param ligacaoEsgotoSituacao
	 *            The new ligacaoEsgotoSituacao value
	 */
	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	/**
	 * Gets the hidrometroInstalacaoHistorico attribute of the Imovel object
	 * 
	 * @return The hidrometroInstalacaoHistorico value
	 */
	public HidrometroInstalacaoHistorico getHidrometroInstalacaoHistorico(){

		return this.hidrometroInstalacaoHistorico;
	}

	/**
	 * Sets the hidrometroInstalacaoHistorico attribute of the Imovel object
	 * 
	 * @param hidrometroInstalacaoHistorico
	 *            The new hidrometroInstalacaoHistorico value
	 */
	public void setHidrometroInstalacaoHistorico(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico){

		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
	}

	/**
	 * Gets the leituraAnormalidade attribute of the Imovel object
	 * 
	 * @return The leituraAnormalidade value
	 */
	public LeituraAnormalidade getLeituraAnormalidade(){

		return this.leituraAnormalidade;
	}

	/**
	 * Sets the leituraAnormalidade attribute of the Imovel object
	 * 
	 * @param leituraAnormalidade
	 *            The new leituraAnormalidade value
	 */
	public void setLeituraAnormalidade(LeituraAnormalidade leituraAnormalidade){

		this.leituraAnormalidade = leituraAnormalidade;
	}

	/**
	 * Gets the eloAnormalidade attribute of the Imovel object
	 * 
	 * @return The eloAnormalidade value
	 */
	public gcom.cadastro.imovel.EloAnormalidade getEloAnormalidade(){

		return this.eloAnormalidade;
	}

	/**
	 * Sets the eloAnormalidade attribute of the Imovel object
	 * 
	 * @param eloAnormalidade
	 *            The new eloAnormalidade value
	 */
	public void setEloAnormalidade(gcom.cadastro.imovel.EloAnormalidade eloAnormalidade){

		this.eloAnormalidade = eloAnormalidade;
	}

	/**
	 * Gets the setorComercial attribute of the Imovel object
	 * 
	 * @return The setorComercial value
	 */
	public SetorComercial getSetorComercial(){

		return this.setorComercial;
	}

	/**
	 * Sets the setorComercial attribute of the Imovel object
	 * 
	 * @param setorComercial
	 *            The new setorComercial value
	 */
	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	/**
	 * Gets the areaConstruidaFaixa attribute of the Imovel object
	 * 
	 * @return The areaConstruidaFaixa value
	 */
	public gcom.cadastro.imovel.AreaConstruidaFaixa getAreaConstruidaFaixa(){

		return this.areaConstruidaFaixa;
	}

	/**
	 * Sets the areaConstruidaFaixa attribute of the Imovel object
	 * 
	 * @param areaConstruidaFaixa
	 *            The new areaConstruidaFaixa value
	 */
	public void setAreaConstruidaFaixa(gcom.cadastro.imovel.AreaConstruidaFaixa areaConstruidaFaixa){

		this.areaConstruidaFaixa = areaConstruidaFaixa;
	}

	/**
	 * @return the rendaFamiliarFaixa
	 */
	public gcom.cadastro.imovel.RendaFamiliarFaixa getRendaFamiliarFaixa(){

		return rendaFamiliarFaixa;
	}

	/**
	 * @param rendaFamiliarFaixa
	 *            the rendaFamiliarFaixa to set
	 */
	public void setRendaFamiliarFaixa(gcom.cadastro.imovel.RendaFamiliarFaixa rendaFamiliarFaixa){

		this.rendaFamiliarFaixa = rendaFamiliarFaixa;
	}

	/**
	 * Gets the pavimentoCalcada attribute of the Imovel object
	 * 
	 * @return The pavimentoCalcada value
	 */
	public gcom.cadastro.imovel.PavimentoCalcada getPavimentoCalcada(){

		return this.pavimentoCalcada;
	}

	/**
	 * Sets the pavimentoCalcada attribute of the Imovel object
	 * 
	 * @param pavimentoCalcada
	 *            The new pavimentoCalcada value
	 */
	public void setPavimentoCalcada(gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada){

		this.pavimentoCalcada = pavimentoCalcada;
	}

	/**
	 * Gets the imovelPerfil attribute of the Imovel object
	 * 
	 * @return The imovelPerfil value
	 */
	public gcom.cadastro.imovel.ImovelPerfil getImovelPerfil(){

		return this.imovelPerfil;
	}

	/**
	 * Sets the imovelPerfil attribute of the Imovel object
	 * 
	 * @param imovelPerfil
	 *            The new imovelPerfil value
	 */
	public void setImovelPerfil(gcom.cadastro.imovel.ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	/**
	 * Gets the reservatorioVolumeFaixaSuperior attribute of the Imovel object
	 * 
	 * @return The reservatorioVolumeFaixaSuperior value
	 */
	public gcom.cadastro.imovel.ReservatorioVolumeFaixa getReservatorioVolumeFaixaSuperior(){

		return this.reservatorioVolumeFaixaSuperior;
	}

	/**
	 * Sets the reservatorioVolumeFaixaSuperior attribute of the Imovel object
	 * 
	 * @param reservatorioVolumeFaixaSuperior
	 *            The new reservatorioVolumeFaixaSuperior value
	 */
	public void setReservatorioVolumeFaixaSuperior(gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaSuperior){

		this.reservatorioVolumeFaixaSuperior = reservatorioVolumeFaixaSuperior;
	}

	/**
	 * Gets the reservatorioVolumeFaixaInferior attribute of the Imovel object
	 * 
	 * @return The reservatorioVolumeFaixaInferior value
	 */
	public gcom.cadastro.imovel.ReservatorioVolumeFaixa getReservatorioVolumeFaixaInferior(){

		return this.reservatorioVolumeFaixaInferior;
	}

	/**
	 * Sets the reservatorioVolumeFaixaInferior attribute of the Imovel object
	 * 
	 * @param reservatorioVolumeFaixaInferior
	 *            The new reservatorioVolumeFaixaInferior value
	 */
	public void setReservatorioVolumeFaixaInferior(gcom.cadastro.imovel.ReservatorioVolumeFaixa reservatorioVolumeFaixaInferior){

		this.reservatorioVolumeFaixaInferior = reservatorioVolumeFaixaInferior;
	}

	/**
	 * Gets the localidade attribute of the Imovel object
	 * 
	 * @return The localidade value
	 */
	public Localidade getLocalidade(){

		return this.localidade;
	}

	/**
	 * Sets the localidade attribute of the Imovel object
	 * 
	 * @param localidade
	 *            The new localidade value
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * Gets the quadra attribute of the Imovel object
	 * 
	 * @return The quadra value
	 */
	public Quadra getQuadra(){

		return this.quadra;
	}

	/**
	 * Sets the quadra attribute of the Imovel object
	 * 
	 * @param quadra
	 *            The new quadra value
	 */
	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	/**
	 * @return the hidrometro
	 */
	/*
	 * public Hidrometro getHidrometro() {
	 * return hidrometro;
	 * }
	 * /**
	 * @param hidrometro the hidrometro to set
	 * public void setHidrometro(Hidrometro hidrometro) {
	 * this.hidrometro = hidrometro;
	 * }
	 */

	/**
	 * @return the padraoConstrucao
	 */
	public PadraoConstrucao getPadraoConstrucao(){

		return padraoConstrucao;
	}

	/**
	 * @param padraoConstrucao
	 *            the padraoConstrucao to set
	 */
	public void setPadraoConstrucao(PadraoConstrucao padraoConstrucao){

		this.padraoConstrucao = padraoConstrucao;
	}

	/**
	 * @return the esgotamento
	 */
	public Esgotamento getEsgotamento(){

		return esgotamento;
	}

	/**
	 * @param esgotamento
	 *            the esgotamento to set
	 */
	public void setEsgotamento(Esgotamento esgotamento){

		this.esgotamento = esgotamento;
	}

	/**
	 * Gets the cobrancaSituacaoTipo attribute of the Imovel object
	 * 
	 * @return The cobrancaSituacaoTipo value
	 */
	public CobrancaSituacaoTipo getCobrancaSituacaoTipo(){

		return this.cobrancaSituacaoTipo;
	}

	/**
	 * Sets the cobrancaSituacaoTipo attribute of the Imovel object
	 * 
	 * @param cobrancaSituacaoTipo
	 *            The new cobrancaSituacaoTipo value
	 */
	public void setCobrancaSituacaoTipo(CobrancaSituacaoTipo cobrancaSituacaoTipo){

		this.cobrancaSituacaoTipo = cobrancaSituacaoTipo;
	}

	/**
	 * Gets the pavimentoRua attribute of the Imovel object
	 * 
	 * @return The pavimentoRua value
	 */
	public gcom.cadastro.imovel.PavimentoRua getPavimentoRua(){

		return this.pavimentoRua;
	}

	/**
	 * Sets the pavimentoRua attribute of the Imovel object
	 * 
	 * @param pavimentoRua
	 *            The new pavimentoRua value
	 */
	public void setPavimentoRua(gcom.cadastro.imovel.PavimentoRua pavimentoRua){

		this.pavimentoRua = pavimentoRua;
	}

	/**
	 * Gets the enderecoImovelReferencia attribute of the Imovel object
	 * 
	 * @return The enderecoImovelReferencia value
	 */
	public EnderecoReferencia getEnderecoReferencia(){

		return this.enderecoReferencia;
	}

	/**
	 * Sets the enderecoImovelReferencia attribute of the Imovel object
	 * 
	 * @param enderecoReferencia
	 *            O novo valor de enderecoImovelReferencia
	 */
	public void setEnderecoReferencia(EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	/**
	 * Gets the cadastroOcorrencia attribute of the Imovel object
	 * 
	 * @return The cadastroOcorrencia value
	 */
	public gcom.cadastro.imovel.CadastroOcorrencia getCadastroOcorrencia(){

		return this.cadastroOcorrencia;
	}

	/**
	 * Sets the cadastroOcorrencia attribute of the Imovel object
	 * 
	 * @param cadastroOcorrencia
	 *            The new cadastroOcorrencia value
	 */
	public void setCadastroOcorrencia(gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia){

		this.cadastroOcorrencia = cadastroOcorrencia;
	}

	/**
	 * Gets the pocoTipo attribute of the Imovel object
	 * 
	 * @return The pocoTipo value
	 */
	public gcom.cadastro.imovel.PocoTipo getPocoTipo(){

		return this.pocoTipo;
	}

	/**
	 * Sets the pocoTipo attribute of the Imovel object
	 * 
	 * @param pocoTipo
	 *            The new pocoTipo value
	 */
	public void setPocoTipo(gcom.cadastro.imovel.PocoTipo pocoTipo){

		this.pocoTipo = pocoTipo;
	}

	/**
	 * Gets the ligacaoAguaSituacao attribute of the Imovel object
	 * 
	 * @return The ligacaoAguaSituacao value
	 */
	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return this.ligacaoAguaSituacao;
	}

	/**
	 * Sets the ligacaoAguaSituacao attribute of the Imovel object
	 * 
	 * @param ligacaoAguaSituacao
	 *            The new ligacaoAguaSituacao value
	 */
	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	/**
	 * Gets the despejo attribute of the Imovel object
	 * 
	 * @return The despejo value
	 */
	public gcom.cadastro.imovel.Despejo getDespejo(){

		return this.despejo;
	}

	/**
	 * Sets the despejo attribute of the Imovel object
	 * 
	 * @param despejo
	 *            The new despejo value
	 */
	public void setDespejo(gcom.cadastro.imovel.Despejo despejo){

		this.despejo = despejo;
	}

	/**
	 * Gets the faturamentoSituacaoTipo attribute of the Imovel object
	 * 
	 * @return The faturamentoSituacaoTipo value
	 */
	public FaturamentoSituacaoTipo getFaturamentoSituacaoTipo(){

		return this.faturamentoSituacaoTipo;
	}

	/**
	 * Sets the faturamentoSituacaoTipo attribute of the Imovel object
	 * 
	 * @param faturamentoSituacaoTipo
	 *            The new faturamentoSituacaoTipo value
	 */
	public void setFaturamentoSituacaoTipo(FaturamentoSituacaoTipo faturamentoSituacaoTipo){

		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
	}

	/**
	 * Gets the fonteAbastecimento attribute of the Imovel object
	 * 
	 * @return The fonteAbastecimento value
	 */
	public gcom.cadastro.imovel.FonteAbastecimento getFonteAbastecimento(){

		return this.fonteAbastecimento;
	}

	/**
	 * Sets the fonteAbastecimento attribute of the Imovel object
	 * 
	 * @param fonteAbastecimento
	 *            The new fonteAbastecimento value
	 */
	public void setFonteAbastecimento(gcom.cadastro.imovel.FonteAbastecimento fonteAbastecimento){

		this.fonteAbastecimento = fonteAbastecimento;
	}

	/**
	 * Gets the piscinaVolumeFaixa attribute of the Imovel object
	 * 
	 * @return The piscinaVolumeFaixa value
	 */
	public gcom.cadastro.imovel.PiscinaVolumeFaixa getPiscinaVolumeFaixa(){

		return this.piscinaVolumeFaixa;
	}

	/**
	 * Sets the piscinaVolumeFaixa attribute of the Imovel object
	 * 
	 * @param piscinaVolumeFaixa
	 *            The new piscinaVolumeFaixa value
	 */
	public void setPiscinaVolumeFaixa(gcom.cadastro.imovel.PiscinaVolumeFaixa piscinaVolumeFaixa){

		this.piscinaVolumeFaixa = piscinaVolumeFaixa;
	}

	public FaturamentoSituacaoMotivo getFaturamentoSituacaoMotivo(){

		return this.faturamentoSituacaoMotivo;
	}

	public void setFaturamentoSituacaoMotivo(FaturamentoSituacaoMotivo faturamentoSituacaoMotivo){

		this.faturamentoSituacaoMotivo = faturamentoSituacaoMotivo;
	}

	public Short getIndicadorSuspensaoAbastecimento(){

		return this.indicadorSuspensaoAbastecimento;
	}

	public void setIndicadorSuspensaoAbastecimento(Short indicadorSuspensaoAbastecimento){

		this.indicadorSuspensaoAbastecimento = indicadorSuspensaoAbastecimento;
	}

	/**
	 * Gets the endereco attribute of the Imovel object
	 * 
	 * @return The endereco value
	 */
	public String getEnderecoFormatado(){

		String[] enderecoParticionado = obterEnderecoFormatado(0);
		return enderecoParticionado[0];
	}

	/**
	 * Retorna o endereço formatado do imóvel, podendo ter particionado em duas partes, de acordo
	 * com o tamanho limite informado. É informado também uma flag indicando se o endereço deve ser
	 * particionado
	 * 
	 * @param limiteTamanhoEndereco
	 *            Tamanho de limite dos 2 Strings que compõem o endereço
	 * @return Uma String de duas posições. Se o endereço não for particionado, o endereço estará
	 *         apenas na primeira posição
	 */
	public String[] obterEnderecoFormatado(int limiteTamanhoEndereco){

		String[] enderecoParticionado = new String[3];
		String enderecoParte1 = "";
		String enderecoParte2 = "";
		String enderecoSemReferencia = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf("0"))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				// concatena o logradouro tipo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao() != null){

					String logradouroTipo = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, logradouroTipo)){
						enderecoParte2 = enderecoParte2 + logradouroTipo;

					}else{
						enderecoParte1 = enderecoParte1 + logradouroTipo;
						enderecoSemReferencia = enderecoSemReferencia + logradouroTipo;
					}
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				// concatena o logradouro titulo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao() != null){

					String logradouroTitulo = " " + this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, logradouroTitulo)){
						enderecoParte2 = enderecoParte2 + logradouroTitulo;

					}else{
						enderecoParte1 = enderecoParte1 + logradouroTitulo;
						enderecoSemReferencia = enderecoSemReferencia + logradouroTitulo;
					}
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){

				String nomeLogradouro = " " + this.getLogradouroCep().getLogradouro().getNome().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, nomeLogradouro)){
					enderecoParte2 = enderecoParte2 + nomeLogradouro;

				}else{
					enderecoParte1 = enderecoParte1 + nomeLogradouro;
					enderecoSemReferencia = enderecoSemReferencia + nomeLogradouro;
				}
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricao() != null && !this.getEnderecoReferencia().getDescricao().equals("")){

					String referencia = " - " + this.getEnderecoReferencia().getDescricao().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, referencia)){
						enderecoParte2 = enderecoParte2 + referencia;

					}else{
						enderecoParte1 = enderecoParte1 + referencia;
					}
				}
			}
			if(this.getNumeroImovel() != null && !this.getNumeroImovel().equals("")){
				// concate o numero do imovel
				String numeroImovel = " - " + this.getNumeroImovel().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, numeroImovel)){
					enderecoParte2 = enderecoParte2 + numeroImovel;

				}else{
					enderecoParte1 = enderecoParte1 + numeroImovel;
					enderecoSemReferencia = enderecoSemReferencia + numeroImovel;
				}
			}

			if(!Util.isVazioOuBranco(this.getComplementoEndereco())){

				String complemento = " - " + this.getComplementoEndereco().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, complemento)){
					enderecoParte2 = enderecoParte2 + complemento;

				}else{
					enderecoParte1 = enderecoParte1 + complemento;
					enderecoSemReferencia = enderecoSemReferencia + complemento;
				}
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
				if(this.getLogradouroBairro().getBairro().getNome() != null){

					String nomeBairro = " - " + this.getLogradouroBairro().getBairro().getNome().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, nomeBairro)){
						enderecoParte2 = enderecoParte2 + nomeBairro;

					}else{
						enderecoParte1 = enderecoParte1 + nomeBairro;
						enderecoSemReferencia = enderecoSemReferencia + nomeBairro;
					}
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getNome() != null){

						String nomeMunicipio = " " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();

						if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, nomeMunicipio)){
							enderecoParte2 = enderecoParte2 + nomeMunicipio;

						}else{
							enderecoParte1 = enderecoParte1 + nomeMunicipio;
							enderecoSemReferencia = enderecoSemReferencia + nomeMunicipio;
						}
					}
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){

						String unidadeFederacao = " "
										+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();

						if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, unidadeFederacao)){
							enderecoParte2 = enderecoParte2 + unidadeFederacao;

						}else{
							enderecoParte1 = enderecoParte1 + unidadeFederacao;
							enderecoSemReferencia = enderecoSemReferencia + unidadeFederacao;
						}
					}
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getLogradouroCep().getCep().getCepFormatado() != null){

					String cepFormatado = " " + this.getLogradouroCep().getCep().getCepFormatado().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, cepFormatado)){
						enderecoParte2 = enderecoParte2 + cepFormatado;

					}else{
						enderecoParte1 = enderecoParte1 + cepFormatado;
						enderecoSemReferencia = enderecoSemReferencia + cepFormatado;
					}
				}
			}

		}

		enderecoParticionado[0] = enderecoParte1;

		if(enderecoParte2.length() <= limiteTamanhoEndereco){
			enderecoParticionado[1] = enderecoParte2.substring(0, enderecoParte2.length());
		}else{
			enderecoParticionado[1] = enderecoParte2.substring(0, limiteTamanhoEndereco);
		}

		enderecoParticionado[2] = enderecoSemReferencia;

		return enderecoParticionado;
	}

	// Formatação para o termo de parcelamente e para o contrato de prestação de serviço
	public String[] obterEnderecoFormatadoComDetalhamento(int limiteTamanhoEndereco){

		String[] enderecoParticionado = new String[2];
		String enderecoParte1 = "";
		String enderecoParte2 = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf("0"))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				// concatena o logradouro tipo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao() != null){

					String logradouroTipo = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, logradouroTipo)){
						enderecoParte2 = enderecoParte2 + logradouroTipo;

					}else{
						enderecoParte1 = enderecoParte1 + logradouroTipo;
					}
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				// concatena o logradouro titulo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao() != null){

					String logradouroTitulo = " " + this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, logradouroTitulo)){
						enderecoParte2 = enderecoParte2 + logradouroTitulo;

					}else{
						enderecoParte1 = enderecoParte1 + logradouroTitulo;
					}
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){

				String nomeLogradouro = " " + this.getLogradouroCep().getLogradouro().getNome().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, nomeLogradouro)){
					enderecoParte2 = enderecoParte2 + nomeLogradouro;

				}else{
					enderecoParte1 = enderecoParte1 + nomeLogradouro;
				}
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricao() != null && !this.getEnderecoReferencia().getDescricao().equals("")){

					String referencia = " - " + this.getEnderecoReferencia().getDescricao().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, referencia)){
						enderecoParte2 = enderecoParte2 + referencia;

					}else{
						enderecoParte1 = enderecoParte1 + referencia;
					}
				}
			}
			if(this.getNumeroImovel() != null && !this.getNumeroImovel().equals("")){
				// concate o numero do imovel
				String numeroImovel = ", n° " + this.getNumeroImovel().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, numeroImovel)){
					enderecoParte2 = enderecoParte2 + numeroImovel;

				}else{
					enderecoParte1 = enderecoParte1 + numeroImovel;
				}
			}

			if(!Util.isVazioOuBranco(this.getComplementoEndereco())){

				String complemento = " - " + this.getComplementoEndereco().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, complemento)){
					enderecoParte2 = enderecoParte2 + complemento;

				}else{
					enderecoParte1 = enderecoParte1 + complemento;
				}
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
				if(this.getLogradouroBairro().getBairro().getNome() != null){

					String nomeBairro = " - " + this.getLogradouroBairro().getBairro().getNome().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, nomeBairro)){
						enderecoParte2 = enderecoParte2 + nomeBairro;

					}else{
						enderecoParte1 = enderecoParte1 + nomeBairro;
					}
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getNome() != null){

						String nomeMunicipio = ", Cidade de " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();

						if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, nomeMunicipio)){
							enderecoParte2 = enderecoParte2 + nomeMunicipio;

						}else{
							enderecoParte1 = enderecoParte1 + nomeMunicipio;
						}
					}
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){

						String unidadeFederacao = " - "
										+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();

						if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, unidadeFederacao)){
							enderecoParte2 = enderecoParte2 + unidadeFederacao;

						}else{
							enderecoParte1 = enderecoParte1 + unidadeFederacao;
						}
					}
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getLogradouroCep().getCep().getCepFormatado() != null){

					String cepFormatado = ", CEP: " + this.getLogradouroCep().getCep().getCepFormatado().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, cepFormatado)){
						enderecoParte2 = enderecoParte2 + cepFormatado;

					}else{
						enderecoParte1 = enderecoParte1 + cepFormatado;
					}
				}
			}

		}

		enderecoParticionado[0] = enderecoParte1;

		if(enderecoParte2.length() <= limiteTamanhoEndereco){
			enderecoParticionado[1] = enderecoParte2.substring(0, enderecoParte2.length());
		}else{
			enderecoParticionado[1] = enderecoParte2.substring(0, limiteTamanhoEndereco);
		}

		return enderecoParticionado;
	}


	/**
	 * Retorna True se atingiu o limite de tamanho do endereço e False, caso contrário
	 * 
	 * @param limiteTamanhoEndereco
	 * @param enderecoParte1
	 * @param enderecoParte2
	 * @param novoValor
	 * @return
	 */
	private boolean verificarLimiteTamanhoEndereco(int limiteTamanhoEndereco, String enderecoParte1, String enderecoParte2, String novoValor){

		boolean retorno = false;
		int tamanhoEnderecoParte1;

		if(limiteTamanhoEndereco > 0){

			if(enderecoParte2.length() > 0){
				retorno = true;

			}else{

				// tamanho do endereço com a nova informação
				tamanhoEnderecoParte1 = enderecoParte1.length() + novoValor.length();

				if(tamanhoEnderecoParte1 > limiteTamanhoEndereco){
					retorno = true;
				}
			}
		}
		return retorno;
	}

	/**
	 * Gets the endereco attribute of the Imovel object
	 * 
	 * @return The endereco value
	 */
	public String getEnderecoFormatadoAbreviado(){

		String[] enderecoParticionado = obterEnderecoFormatadoAbreviado(ConstantesSistema.ZERO);
		return enderecoParticionado[ConstantesSistema.ZERO];
	}

	/**
	 * Retorna o endereço formatado e abreviado p
	 * 
	 * @return The endereco value
	 */
	public String[] obterEnderecoFormatadoAbreviado(int limiteTamanhoEndereco){

		String[] enderecoParticionado = new String[2];
		String enderecoParte1 = "";
		String enderecoParte2 = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf(0))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro tipo do imovel
					String logradouroTipo = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, logradouroTipo)){
						enderecoParte2 = logradouroTipo;

					}else{
						enderecoParte1 = logradouroTipo;
					}
				}
			}

			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro titulo do imovel
					String logradouroTitulo = " "
									+ this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, logradouroTitulo)){
						enderecoParte2 = enderecoParte2 + logradouroTitulo;

					}else{
						enderecoParte1 = enderecoParte1 + logradouroTitulo;
					}
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){
				String logradouroNome = " " + this.getLogradouroCep().getLogradouro().getNome().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, logradouroNome)){
					enderecoParte2 = enderecoParte2 + logradouroNome;

				}else{
					enderecoParte1 = enderecoParte1 + logradouroNome;
				}
			}
			String referencia = null;
			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricaoAbreviada() != null
								&& !this.getEnderecoReferencia().getDescricaoAbreviada().equals("")){

					referencia = ", " + this.getEnderecoReferencia().getDescricaoAbreviada().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, referencia)){
						enderecoParte2 = enderecoParte2 + referencia;

					}else{
						enderecoParte1 = enderecoParte1 + referencia;
					}
				}
			}

			// concate o numero do imovel
			if(this.getNumeroImovel() != null){
				String numeroImovel = null;
				if(referencia == null){
					numeroImovel = ", " + this.getNumeroImovel().trim();
				}else{
					numeroImovel = " " + this.getNumeroImovel().trim();
				}

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, numeroImovel)){
					enderecoParte2 = enderecoParte2 + numeroImovel;

				}else{
					enderecoParte1 = enderecoParte1 + numeroImovel;
				}
			}

			if(!Util.isVazioOuBranco(this.getComplementoEndereco())){
				String complemento = " - " + this.getComplementoEndereco().trim();

				if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, complemento)){
					enderecoParte2 = enderecoParte2 + complemento;

				}else{
					enderecoParte1 = enderecoParte1 + complemento;
				}
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){

				if(this.getLogradouroBairro().getBairro().getNome() != null){

					String bairro = " - " + this.getLogradouroBairro().getBairro().getNome().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, bairro)){
						enderecoParte2 = enderecoParte2 + bairro;

					}else{
						enderecoParte1 = enderecoParte1 + bairro;
					}
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getNome() != null){

						String municipio = " " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();

						if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, municipio)){
							enderecoParte2 = enderecoParte2 + municipio;

						}else{
							enderecoParte1 = enderecoParte1 + municipio;
						}
					}
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){

						String siglaUF = " "
										+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();

						if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, siglaUF)){
							enderecoParte2 = enderecoParte2 + siglaUF;

						}else{
							enderecoParte1 = enderecoParte1 + siglaUF;
						}
					}
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getLogradouroCep().getCep().getCepFormatado() != null){
					String cepFormatado = " " + this.getLogradouroCep().getCep().getCepFormatado().trim();

					if(verificarLimiteTamanhoEndereco(limiteTamanhoEndereco, enderecoParte1, enderecoParte2, cepFormatado)){
						enderecoParte2 = enderecoParte2 + cepFormatado;

					}else{
						enderecoParte1 = enderecoParte1 + cepFormatado;
					}
				}
			}
		}

		enderecoParticionado[0] = enderecoParte1;

		if(enderecoParte2.length() <= limiteTamanhoEndereco){
			enderecoParticionado[1] = enderecoParte2.substring(0, enderecoParte2.length());
		}else{
			enderecoParticionado[1] = enderecoParte2.substring(0, limiteTamanhoEndereco);
		}

		return enderecoParticionado;
	}

	public String getEnderecoFormatadoAbreviadoSemBairro(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf("0"))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				// concatena o logradouro tipo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao() != null){
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				// concatena o logradouro titulo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao() != null){
					endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao().trim();
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){
				endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricao() != null && !this.getEnderecoReferencia().getDescricao().equals("")){
					endereco = endereco + " - " + this.getEnderecoReferencia().getDescricao().trim();
				}
			}
			if(this.getNumeroImovel() != null && !this.getNumeroImovel().equals("")){
				// concate o numero do imovel
				endereco = endereco + " - " + this.getNumeroImovel().trim();
			}

			if(this.getComplementoEndereco() != null && !this.getComplementoEndereco().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplementoEndereco().trim();
			}
		}

		return endereco;
	}

	/**
	 * Retorna a inscrição do imóvel.
	 * 
	 * @throws ControladorException
	 */
	public String getInscricaoFormatada(){

		// Recuperando o tamanho a ser considerado para o número da quadra
		int tamanhoNumeroQuadra = 3;
		try{
			String paramNumeroQuadra = ParametroCadastro.P_NUMERO_QUADRA_COM_4_DIGITOS.executar();
			if(!Util.isVazioOuBranco(paramNumeroQuadra) && paramNumeroQuadra.equals(ConstantesSistema.SIM.toString())){
				tamanhoNumeroQuadra = 4;
			}
		}catch(ControladorException ex){
			throw new RuntimeException("erro.parametro.sistema.inscricao.imovel", ex);
		}

		String localidade = Util.completarStringZeroEsquerda(this.getLocalidade().getId().toString(), 3);
		String setorComercial = Util.completarStringZeroEsquerda(String.valueOf(this.getSetorComercial().getCodigo()), 3);
		String quadra = Util.completarStringZeroEsquerda(String.valueOf(this.getQuadra().getNumeroQuadra()), tamanhoNumeroQuadra);
		String lote = Util.completarStringZeroEsquerda(String.valueOf(this.getLote()), 4);
		String subLote = Util.completarStringZeroEsquerda(String.valueOf(this.getSubLote()), 3);

		String inscricao = localidade + "." + setorComercial + "." + quadra + "." + lote + "." + subLote;

		return inscricao;
	}

	public void setInscricaoFormatada(Integer idLocalidade, Integer codigoSetorComercial, Integer numeroQuadra, short lote, short subLote){

		setLocalidade(new Localidade());
		getLocalidade().setId(idLocalidade);

		setSetorComercial(new SetorComercial());
		getSetorComercial().setCodigo(codigoSetorComercial);

		setQuadra(new Quadra());
		getQuadra().setNumeroQuadra(numeroQuadra);

		setLote(lote);
		setSubLote(subLote);
	}

	/**
	 * Description of the Method
	 * 
	 * @return Description of the Return Value
	 */
	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * Retorna o valor de imovelSubcategorias
	 * 
	 * @return O valor de imovelSubcategorias
	 */
	public Set getImovelSubcategorias(){

		return imovelSubcategorias;
	}

	/**
	 * Seta o valor de imovelSubcategorias
	 * 
	 * @param imovelSubcategorias
	 *            O novo valor de imovelSubcategorias
	 */
	public void setImovelSubcategorias(Set imovelSubcategorias){

		this.imovelSubcategorias = imovelSubcategorias;
	}

	/**
	 * Retorna o valor de quitacaoDebitoAnual
	 * 
	 * @return O valor de quitacaoDebitoAnual
	 */
	public Set<QuitacaoDebitoAnual> getQuitacaoDebitoAnual(){

		return quitacaoDebitoAnual;
	}

	/**
	 * Seta o valor de quitacaoDebitoAnual
	 * 
	 * @param quitacaoDebitoAnual
	 *            O novo valor de quitacaoDebitoAnual
	 */
	public void setQuitacaoDebitoAnual(Set<QuitacaoDebitoAnual> quitacaoDebitoAnual){

		this.quitacaoDebitoAnual = quitacaoDebitoAnual;
	}

	/**
	 * Gets the quantidadeEconomias attribute of the Imovel object
	 * 
	 * @return The quantidadeEconomias value
	 */
	public Short getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	/**
	 * Sets the quantidadeEconomias attribute of the Imovel object
	 * 
	 * @param quantidadeEconomias
	 *            The new quantidadeEconomias value
	 */
	public void setQuantidadeEconomias(Short quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	/**
	 * Gets the consumoTarifa attribute of the Imovel object
	 * 
	 * @return The consumoTarifa value
	 */
	public gcom.faturamento.consumotarifa.ConsumoTarifa getConsumoTarifa(){

		return consumoTarifa;
	}

	/**
	 * Sets the consumoTarifa attribute of the Imovel object
	 * 
	 * @param consumoTarifa
	 *            The new consumoTarifa value
	 */
	public void setConsumoTarifa(gcom.faturamento.consumotarifa.ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	public Set getMedicaoHistoricos(){

		return medicaoHistoricos;
	}

	public void setMedicaoHistoricos(Set medicaoHistoricos){

		this.medicaoHistoricos = medicaoHistoricos;
	}

	/**
	 * @return the imovelCobrancaSituacoes
	 */
	public Set getImovelCobrancaSituacoes(){

		return imovelCobrancaSituacoes;
	}

	/**
	 * @param imovelCobrancaSituacoes
	 *            the imovelCobrancaSituacoes to set
	 */
	public void setImovelCobrancaSituacoes(Set imovelCobrancaSituacoes){

		this.imovelCobrancaSituacoes = imovelCobrancaSituacoes;
	}

	/**
	 * @return the cobrancaSituacaoHistorico
	 */
	public Set getCobrancaSituacaoHistorico(){

		return cobrancaSituacaoHistorico;
	}

	/**
	 * @param cobrancaSituacaoHistorico
	 *            the cobrancaSituacaoHistorico to set
	 */
	public void setCobrancaSituacaoHistorico(Set cobrancaSituacaoHistorico){

		this.cobrancaSituacaoHistorico = cobrancaSituacaoHistorico;
	}

	public FaturamentoTipo getFaturamentoTipo(){

		return faturamentoTipo;
	}

	public void setFaturamentoTipo(FaturamentoTipo faturamentoTipo){

		this.faturamentoTipo = faturamentoTipo;
	}

	public Short getNumeroReparcelamentoConsecutivos(){

		return numeroReparcelamentoConsecutivos;
	}

	public void setNumeroReparcelamentoConsecutivos(Short numeroReparcelamentoConsecutivos){

		this.numeroReparcelamentoConsecutivos = numeroReparcelamentoConsecutivos;
	}

	public Set getConsumosHistoricos(){

		return consumosHistoricos;
	}

	public void setConsumosHistoricos(Set consumosHistoricos){

		this.consumosHistoricos = consumosHistoricos;
	}

	public LogradouroBairro getLogradouroBairro(){

		return logradouroBairro;
	}

	public void setLogradouroBairro(LogradouroBairro logradouroBairro){

		this.logradouroBairro = logradouroBairro;
	}

	public LogradouroCep getLogradouroCep(){

		return logradouroCep;
	}

	public void setLogradouroCep(LogradouroCep logradouroCep){

		this.logradouroCep = logradouroCep;
	}

	/**
	 * Retorna o valor de clienteImoveis
	 * 
	 * @return O valor de clienteImoveis
	 */
	public Set<ClienteImovel> getClienteImoveis(){

		return clienteImoveis;
	}

	/**
	 * Seta o valor de clienteImoveis
	 * 
	 * @param clienteImoveis
	 *            O novo valor de clienteImoveis
	 */
	public void setClienteImoveis(Set<ClienteImovel> clienteImoveis){

		this.clienteImoveis = clienteImoveis;
	}

	public Filtro retornaFiltro(){

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, this.getId()));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO);
		// filtroImovel
		// .adicionarCaminhoParaCarregamentoEntidade(FiltroImovel."tarifaSocialDado");
		// filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_ENDERECO_ANTERIOR);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_PRINCIPAL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_CONDOMINIO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.HIDROMETRO_INSTALACAO_HISTORICO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LEITURA_ANORMALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ELO_ANORMALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.AREA_CONSTRUIDA_FAIXA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.RENDA_FAMILIAR_FAIXA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.PAVIMENTO_CALCADA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_PERFIL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.RESERVATORIO_VOLUME_FAIXA_SUPERIOR);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.RESERVATORIO_VOLUME_FAIXA_INFERIOR);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		// filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel."hidrometro");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.PADRAO_CONSTRUCAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.COBRANCA_SITUACAO_TIPO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.PAVIMENTO_RUA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ENDERECO_REFERENCIA);
		// filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel."nomeConta");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CADASTRO_OCORRENCIA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.POCO_TIPO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.DESPEJO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.FATURAMENTO_SITUACAO_TIPO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.FONTE_ABASTECIMENTO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.PISCINA_VOLUME_FAIXA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CONSUMO_TARIFA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.FATURAMENTO_TIPO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.FATURAMENTO_SITUACAO_MOTIVO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_BAIRRO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_CEP);
		// filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel."imovelDoacao");
		return filtroImovel;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public ImovelContaEnvio getImovelContaEnvio(){

		return imovelContaEnvio;
	}

	public void setImovelContaEnvio(ImovelContaEnvio imovelContaEnvio){

		this.imovelContaEnvio = imovelContaEnvio;
	}

	public CobrancaSituacao getCobrancaSituacao(){

		return cobrancaSituacao;
	}

	public void setCobrancaSituacao(CobrancaSituacao cobrancaSituacao){

		this.cobrancaSituacao = cobrancaSituacao;
	}

	public Short getIndicadorJardim(){

		return indicadorJardim;
	}

	public void setIndicadorJardim(Short indicadorJardim){

		this.indicadorJardim = indicadorJardim;
	}

	public Integer getNumeroSequencialRota(){

		return numeroSequencialRota;
	}

	public void setNumeroSequencialRota(Integer numeroSequencialRota){

		this.numeroSequencialRota = numeroSequencialRota;
	}

	public String getNomeImovel(){

		return nomeImovel;
	}

	public void setNomeImovel(String nomeImovel){

		this.nomeImovel = nomeImovel;
	}

	public Set getContas(){

		return contas;
	}

	public void setContas(Set contas){

		this.contas = contas;
	}

	@Override
	public void initializeLazy(){

		initilizarCollectionLazy(this.getImovelSubcategorias());
		initilizarCollectionLazy(this.getClienteImoveis());
		if(logradouroCep != null) logradouroCep.initializeLazy();
		if(logradouroBairro != null) logradouroBairro.initializeLazy();
		if(areaConstruidaFaixa != null) areaConstruidaFaixa.initializeLazy();
		if(rendaFamiliarFaixa != null) rendaFamiliarFaixa.initializeLazy();
		if(imovelPerfil != null) imovelPerfil.initializeLazy();
		if(imovelContaEnvio != null) imovelContaEnvio.initializeLazy();
		if(pavimentoRua != null) pavimentoRua.initializeLazy();
		if(pocoTipo != null) pocoTipo.initializeLazy();
		if(fonteAbastecimento != null) fonteAbastecimento.initializeLazy();
		if(consumoTarifa != null) consumoTarifa.initializeLazy();
	}

	@Override
	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] labels = {"localidade.descricao", "inscricaoFormatada"};
		return labels;
	}

	@Override
	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Localidade", "Inscricao"};
		return labels;
	}

	public Funcionario getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

	// ------- Fim Registra transação ---------------

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	/**
	 * @return the cobrancasDocumentos
	 */
	public Set getCobrancasDocumentos(){

		return cobrancasDocumentos;
	}

	/**
	 * @param cobrancasDocumentos
	 *            the cobrancasDocumentos to set
	 */
	public void setCobrancasDocumentos(Set cobrancasDocumentos){

		this.cobrancasDocumentos = cobrancasDocumentos;
	}

	/**
	 * @return the faturamentosSituacaoHistorico
	 */
	public Set getFaturamentosSituacaoHistorico(){

		return faturamentosSituacaoHistorico;
	}

	/**
	 * @param faturamentosSituacaoHistorico
	 *            the faturamentosSituacaoHistorico to set
	 */
	public void setFaturamentosSituacaoHistorico(Set faturamentosSituacaoHistorico){

		this.faturamentosSituacaoHistorico = faturamentosSituacaoHistorico;
	}

	/**
	 * @return the movimentosRoteiroEmpresa
	 */
	public Set getMovimentosRoteiroEmpresa(){

		return movimentosRoteiroEmpresa;
	}

	/**
	 * @param movimentosRoteiroEmpresa
	 *            the movimentosRoteiroEmpresa to set
	 */
	public void setMovimentosRoteiroEmpresa(Set movimentosRoteiroEmpresa){

		this.movimentosRoteiroEmpresa = movimentosRoteiroEmpresa;
	}

	/**
	 * @return the rota
	 */
	public Rota getRota(){

		return rota;
	}

	/**
	 * @param rota
	 *            the rota to set
	 */
	public void setRota(Rota rota){

		this.rota = rota;
	}

	/**
	 * @return the numeroSegmento
	 */
	public Short getNumeroSegmento(){

		return numeroSegmento;
	}

	/**
	 * @param numeroSegmento
	 *            the numeroSegmento to set
	 */
	public void setNumeroSegmento(Short numeroSegmento){

		this.numeroSegmento = numeroSegmento;
	}

	/**
	 * @return the indicadorContratoConsumo
	 */
	public Short getIndicadorContratoConsumo(){

		return indicadorContratoConsumo;
	}

	/**
	 * @param indicadorContratoConsumo
	 *            the indicadorContratoConsumo to set
	 */
	public void setIndicadorContratoConsumo(Short indicadorContratoConsumo){

		this.indicadorContratoConsumo = indicadorContratoConsumo;
	}

	public SetorAbastecimento getSetorAbastecimento(){

		return setorAbastecimento;
	}

	public void setSetorAbastecimento(SetorAbastecimento setorAbastecimento){

		this.setorAbastecimento = setorAbastecimento;
	}

	public SubBacia getSubBacia(){

		return subBacia;
	}

	public void setSubBacia(SubBacia subBacia){

		this.subBacia = subBacia;
	}

	/**
	 * @author eduardo henrique
	 * @date
	 *       Equals Imovel
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Imovel)) return false;
		final Imovel other = (Imovel) obj;
		if(this.id == null){
			if(other.id != null) return false;
		}else if(!this.id.equals(other.id)) return false;
		return true;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * @author eduardo henrique
	 * @date
	 *       Equals Imovel
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	// public String getInscricaoSemLocalidade(){
	//
	// String inscricao = "";
	// String zeroUm = "0";
	// String zeroDois = "00";
	// String zeroTres = "000";
	//
	// if(String.valueOf(this.getLote()).length() < 4 && String.valueOf(this.getLote()).length() >
	// 2){
	// inscricao = zeroUm + this.getLote();
	// }else if(String.valueOf(this.getLote()).length() < 3 &&
	// String.valueOf(this.getLote()).length() > 1){
	// inscricao = zeroDois + this.getLote();
	// }else if(String.valueOf(this.getLote()).length() < 2){
	// inscricao = zeroTres + this.getLote();
	// }
	//
	// if(String.valueOf(this.getSubLote()).length() < 3 &&
	// String.valueOf(this.getSubLote()).length() > 1){
	// inscricao += zeroUm + this.getSubLote();
	// }else if(String.valueOf(this.getSubLote()).length() < 3){
	// inscricao += zeroDois + this.getSubLote();
	// }
	//
	// if(this.getRota() != null && String.valueOf(this.getRota()).length() < 2){
	// inscricao += zeroUm + this.getRota();
	// }
	//
	// if(this.getNumeroSegmento() != null && String.valueOf(this.getNumeroSegmento()).length() <
	// 2){
	// inscricao += zeroUm + this.getNumeroSegmento();
	// }
	//
	// return inscricao;
	// }

	public ConsumoTarifa getConsumoTarifaTemporaria(){

		return consumoTarifaTemporaria;
	}

	public void setConsumoTarifaTemporaria(ConsumoTarifa consumoTarifaTemporaria){

		this.consumoTarifaTemporaria = consumoTarifaTemporaria;
	}

	public Date getDataValidadeTarifaTemporaria(){

		return dataValidadeTarifaTemporaria;
	}

	public void setDataValidadeTarifaTemporaria(Date dataValidadeTarifaTemporaria){

		this.dataValidadeTarifaTemporaria = dataValidadeTarifaTemporaria;
	}

	public ImovelAguaParaTodos getImovelAguaParaTodos(){

		return imovelAguaParaTodos;
	}

	public void setImovelAguaParaTodos(ImovelAguaParaTodos imovelAguaParaTodos){

		this.imovelAguaParaTodos = imovelAguaParaTodos;
	}

	/**
	 * Retorna a inscrição do imóvel.
	 */
	public String getInscricaoNaoFormatada(){

		String inscricao = null;

		String zeroUm = "0";
		String zeroDois = "00";
		String zeroTres = "000";

		// Recuperando o tamanho a ser considerado para o número da quadra
		int tamanhoNumeroQuadra = 3;
		try{
			String paramNumeroQuadra = ParametroCadastro.P_NUMERO_QUADRA_COM_4_DIGITOS.executar();
			if(!Util.isVazioOuBranco(paramNumeroQuadra) && paramNumeroQuadra.equals(ConstantesSistema.SIM)){
				tamanhoNumeroQuadra = 4;
			}
		}catch(ControladorException ex){
			throw new RuntimeException("erro.parametro.sistema.inscricao.imovel", ex);
		}

		String localidade, setorComercial, quadra, lote, subLote;

		localidade = String.valueOf(this.getLocalidade().getId().intValue());
		setorComercial = String.valueOf(this.getSetorComercial().getCodigo());
		quadra = String.valueOf(this.getQuadra().getNumeroQuadra());
		lote = String.valueOf(this.getLote());
		subLote = String.valueOf(this.getSubLote());

		if(String.valueOf(this.getLocalidade().getId().intValue()).length() < 3
						&& String.valueOf(this.getLocalidade().getId().intValue()).length() > 1){
			localidade = zeroUm + this.getLocalidade().getId().intValue();
		}else if(String.valueOf(this.getLocalidade().getId().intValue()).length() < 3){
			localidade = zeroDois + this.getLocalidade().getId().intValue();
		}

		if(String.valueOf(this.getSetorComercial().getCodigo()).length() < 3
						&& String.valueOf(this.getSetorComercial().getCodigo()).length() > 1){
			setorComercial = zeroUm + this.getSetorComercial().getCodigo();
		}else if(String.valueOf(this.getSetorComercial().getCodigo()).length() < 3){
			setorComercial = zeroDois + this.getSetorComercial().getCodigo();
		}

		quadra = Util.completarStringZeroEsquerda(quadra, tamanhoNumeroQuadra);

		if(String.valueOf(this.getQuadra().getNumeroQuadra()).length() < 2
						&& String.valueOf(this.getQuadra().getNumeroQuadra()).length() > 1){
			localidade = zeroDois + this.getLocalidade().getId().intValue();
		}

		if(String.valueOf(this.getLocalidade().getId().intValue()).length() < 3){
			localidade = zeroDois + this.getLocalidade().getId().intValue();
		}

		if(String.valueOf(this.getLote()).length() < 4 && String.valueOf(this.getLote()).length() > 2){
			lote = zeroUm + this.getLote();
		}else if(String.valueOf(this.getLote()).length() < 3 && String.valueOf(this.getLote()).length() > 1){
			lote = zeroDois + this.getLote();
		}else if(String.valueOf(this.getLote()).length() < 2){
			lote = zeroTres + this.getLote();
		}

		if(String.valueOf(this.getSubLote()).length() < 3 && String.valueOf(this.getSubLote()).length() > 1){
			subLote = zeroUm + this.getSubLote();
		}else if(String.valueOf(this.getSubLote()).length() < 3){
			subLote = zeroDois + this.getSubLote();
		}

		inscricao = localidade + setorComercial + quadra + lote + subLote;

		return inscricao;
	}

	public DistritoOperacional getDistritoOperacional(){

		return distritoOperacional;
	}

	public void setDistritoOperacional(DistritoOperacional distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public void setEnderecoFormatado(String enderecoFormatado){

		// do nothing
	}

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	/**
	 * @return the contasAtualizadas
	 */
	public Set<TabelaIntegracaoContaAtualizada> getContasAtualizadas(){

		return contasAtualizadas;
	}

	/**
	 * @param contasAtualizadas
	 *            the contasAtualizadas to set
	 */
	public void setContasAtualizadas(Set<TabelaIntegracaoContaAtualizada> contasAtualizadas){

		this.contasAtualizadas = contasAtualizadas;
	}

	public Integer getNumeroContratoEmissao(){

		return numeroContratoEmissao;
	}

	public void setNumeroContratoEmissao(Integer numeroContratoEmissao){

		this.numeroContratoEmissao = numeroContratoEmissao;
	}

	public int compareTo(Imovel o){

		int x = localidade.getId().compareTo(o.getLocalidade().getId());
		if(x == 0){
			if(setorComercial.getCodigo() > o.getSetorComercial().getCodigo()){
				x = 1;
			}else if(setorComercial.getCodigo() < o.getSetorComercial().getCodigo()){
				x = -1;
			}
			if(x == 0){
				if(quadra.getNumeroQuadra() > o.getQuadra().getNumeroQuadra()){
					x = 1;
				}else if(quadra.getNumeroQuadra() < o.getQuadra().getNumeroQuadra()){
					x = -1;
				}
				if(x == 0){
					if(lote > o.getLote()){
						x = 1;
					}else if(lote < o.getLote()){
						x = -1;
					}
					if(x == 0){
						if(subLote > o.getSubLote()){
							x = 1;
						}else if(subLote > o.getSubLote()){
							x = -1;
						}
					}
				}
			}
		}
		return x;
	}

}
