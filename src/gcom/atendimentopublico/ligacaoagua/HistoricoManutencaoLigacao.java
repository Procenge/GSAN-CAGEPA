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

package gcom.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcaoSituacao;
import gcom.cobranca.CobrancaDebitoSituacao;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.DocumentoEmissaoForma;
import gcom.cobranca.DocumentoTipo;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class HistoricoManutencaoLigacao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final short EFETUAR_CORTE_LIGACAO_AGUA = 355;

	public static final short EFETUAR_RELIGACAO_AGUA = 357;

	public static final short EFETUAR_RESTABELECIMENTO_AGUA = 359;

	public static final short EFETUAR_SUPRESSAO_LIGACAO_AGUA = 360;

	public static final short EFETUAR_CORTE_ADMINISTRATIVO_LIGACAO_AGUA = 369;

	public static final short EFETUAR_RESTABELECIMENTO_AGUA_COM_INSTALACAO_HIDROMETRO = 540;

	public static final short EFETUAR_CORTE_COM_INSTALACAO_HIDROMETRO = 944;

	public static final short EFETUAR_RELIGACAO_AGUA_COM_SUBSTITUICAO_HIDROMETRO = 945;

	public static final short EFETUAR_RESTABELECIMENTO_AGUA_COM_SUBSTITUICAO_HIDROMETRO = 946;

	public static final short EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO = 747;

	public static final short ENCERRAR_ORDEM_SERVICO = 457;

	public static final short ENCERRAR_ORDEM_SERVICO_ATUALIZAR_SITUACAO = 4572;

	public static final short INFORMAR_ENTREGA_DEVOLUCAO_DOCUMENTOS = 3044;

	public static final short GERAR_RESUMO_ACOES_COBRANCA_EVENTUAIS = 614;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Integer idAssociado;

	/** nullable persistent field */
	private String descricaoParecer;

	/** nullable persistent field */
	private Short codigoSituacaoOS;

	/** nullable persistent field */
	private Integer numeroLeituraExecucao;

	/** nullable persistent field */
	private BigDecimal valorDebito;

	/** nullable persistent field */
	private Date dataSituacaoDebito;

	/** nullable persistent field */
	private Date dataSituacaoDocumento;

	/** nullable persistent field */
	private Date dataEmissao;

	/** nullable persistent field */
	private Date dataInclusao;

	/** persistent field */
	private Integer codigoSetorComercial;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private SetorComercial setorComercial;

	/** persistent field */
	private ImovelPerfil imovelPerfil;

	/** nullable persistent field */
	private OrdemServico ordemServico;

	/** nullable persistent field */
	private CobrancaDocumento cobrancaDocumentoOrigemServico;

	/** nullable persistent field */
	private RegistroAtendimento registroAtendimentoOrigemServico;

	/** nullable persistent field */
	private ServicoTipo servicoTipo;

	/** nullable persistent field */
	private AtendimentoMotivoEncerramento atendimentoMotivoEncerramento;

	/** nullable persistent field */
	private Usuario usuarioGeracao;

	/** nullable persistent field */
	private Usuario usuarioEncerramento;

	/** nullable persistent field */
	private Usuario usuarioExecucao;

	/** nullable persistent field */
	private CorteTipo corteTipo;

	/** nullable persistent field */
	private CobrancaDocumento cobrancaDocumento;

	/** nullable persistent field */
	private DocumentoEmissaoForma documentoEmissaoForma;

	/** nullable persistent field */
	private DocumentoTipo documentoTipo;

	/** nullable persistent field */
	private CobrancaDebitoSituacao cobrancaDebitoSituacao;

	/** nullable persistent field */
	private CobrancaAcaoSituacao cobrancaAcaoSituacao;

	/** nullable persistent field */
	private MotivoNaoEntregaDocumento motivoNaoEntregaDocumento;

	/** nullable persistent field */
	private FiscalizacaoSituacao fiscalizacaoSituacao;

	/** persistent field */
	private Date ultimaAlteracao;

	public HistoricoManutencaoLigacao() {

	}

	/**
	 * Construtor que recebe todos os atributos obrigatórios da entidade
	 * 
	 * @param codigoSetorComercial
	 * @param imovel
	 * @param localidade
	 * @param setorComercial
	 * @param imovelPerfil
	 */
	public HistoricoManutencaoLigacao(Integer codigoSetorComercial, Imovel imovel, Localidade localidade, SetorComercial setorComercial,
										ImovelPerfil imovelPerfil) {

		this.codigoSetorComercial = codigoSetorComercial;
		this.imovel = imovel;
		this.localidade = localidade;
		this.setorComercial = setorComercial;
		this.imovelPerfil = imovelPerfil;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroHistoricoManutencaoLigacao filtroHistoricoLigacaoAgua = new FiltroHistoricoManutencaoLigacao();
		filtroHistoricoLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroHistoricoManutencaoLigacao.ID, this.getId()));
		return filtroHistoricoLigacaoAgua;
	}

	public Integer getIdAssociado(){

		return idAssociado;
	}

	public void setIdAssociado(Integer idAssociado){

		this.idAssociado = idAssociado;
	}

	public String getDescricaoParecer(){

		return descricaoParecer;
	}

	public void setDescricaoParecer(String descricaoParecer){

		this.descricaoParecer = descricaoParecer;
	}

	public Short getCodigoSituacaoOS(){

		return codigoSituacaoOS;
	}

	public void setCodigoSituacaoOS(Short codigoSituacaoOS){

		this.codigoSituacaoOS = codigoSituacaoOS;
	}

	public Integer getNumeroLeituraExecucao(){

		return numeroLeituraExecucao;
	}

	public void setNumeroLeituraExecucao(Integer numeroLeituraExecucao){

		this.numeroLeituraExecucao = numeroLeituraExecucao;
	}

	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public Date getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	public void setDataSituacaoDebito(Date dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
	}

	public Date getDataSituacaoDocumento(){

		return dataSituacaoDocumento;
	}

	public void setDataSituacaoDocumento(Date dataSituacaoDocumento){

		this.dataSituacaoDocumento = dataSituacaoDocumento;
	}

	public Date getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public Date getDataInclusao(){

		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao){

		this.dataInclusao = dataInclusao;
	}

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public ImovelPerfil getImovelPerfil(){

		return imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public OrdemServico getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	public CobrancaDocumento getCobrancaDocumentoOrigemServico(){

		return cobrancaDocumentoOrigemServico;
	}

	public void setCobrancaDocumentoOrigemServico(CobrancaDocumento cobrancaDocumentoOrigemServico){

		this.cobrancaDocumentoOrigemServico = cobrancaDocumentoOrigemServico;
	}

	public RegistroAtendimento getRegistroAtendimentoOrigemServico(){

		return registroAtendimentoOrigemServico;
	}

	public void setRegistroAtendimentoOrigemServico(RegistroAtendimento registroAtendimentoOrigemServico){

		this.registroAtendimentoOrigemServico = registroAtendimentoOrigemServico;
	}

	public ServicoTipo getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public AtendimentoMotivoEncerramento getAtendimentoMotivoEncerramento(){

		return atendimentoMotivoEncerramento;
	}

	public void setAtendimentoMotivoEncerramento(AtendimentoMotivoEncerramento atendimentoMotivoEncerramento){

		this.atendimentoMotivoEncerramento = atendimentoMotivoEncerramento;
	}

	public Usuario getUsuarioGeracao(){

		return usuarioGeracao;
	}

	public void setUsuarioGeracao(Usuario usuarioGeracao){

		this.usuarioGeracao = usuarioGeracao;
	}

	public Usuario getUsuarioEncerramento(){

		return usuarioEncerramento;
	}

	public void setUsuarioEncerramento(Usuario usuarioEncerramento){

		this.usuarioEncerramento = usuarioEncerramento;
	}

	public Usuario getUsuarioExecucao(){

		return usuarioExecucao;
	}

	public void setUsuarioExecucao(Usuario usuarioExecucao){

		this.usuarioExecucao = usuarioExecucao;
	}

	public CorteTipo getCorteTipo(){

		return corteTipo;
	}

	public void setCorteTipo(CorteTipo corteTipo){

		this.corteTipo = corteTipo;
	}

	public CobrancaDocumento getCobrancaDocumento(){

		return cobrancaDocumento;
	}

	public void setCobrancaDocumento(CobrancaDocumento cobrancaDocumento){

		this.cobrancaDocumento = cobrancaDocumento;
	}

	public DocumentoEmissaoForma getDocumentoEmissaoForma(){

		return documentoEmissaoForma;
	}

	public void setDocumentoEmissaoForma(DocumentoEmissaoForma documentoEmissaoForma){

		this.documentoEmissaoForma = documentoEmissaoForma;
	}

	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public CobrancaDebitoSituacao getCobrancaDebitoSituacao(){

		return cobrancaDebitoSituacao;
	}

	public void setCobrancaDebitoSituacao(CobrancaDebitoSituacao cobrancaDebitoSituacao){

		this.cobrancaDebitoSituacao = cobrancaDebitoSituacao;
	}

	public CobrancaAcaoSituacao getCobrancaAcaoSituacao(){

		return cobrancaAcaoSituacao;
	}

	public void setCobrancaAcaoSituacao(CobrancaAcaoSituacao cobrancaAcaoSituacao){

		this.cobrancaAcaoSituacao = cobrancaAcaoSituacao;
	}

	public MotivoNaoEntregaDocumento getMotivoNaoEntregaDocumento(){

		return motivoNaoEntregaDocumento;
	}

	public void setMotivoNaoEntregaDocumento(MotivoNaoEntregaDocumento motivoNaoEntregaDocumento){

		this.motivoNaoEntregaDocumento = motivoNaoEntregaDocumento;
	}

	public FiscalizacaoSituacao getFiscalizacaoSituacao(){

		return fiscalizacaoSituacao;
	}

	public void setFiscalizacaoSituacao(FiscalizacaoSituacao fiscalizacaoSituacao){

		this.fiscalizacaoSituacao = fiscalizacaoSituacao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
