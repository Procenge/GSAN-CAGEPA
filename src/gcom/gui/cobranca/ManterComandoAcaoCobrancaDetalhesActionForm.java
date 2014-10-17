/*
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

package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * [UC0244] Manter Comando de Ação de Cobrança
 * 
 * @author Rafael Santos
 * @since 24/01/2006
 * @author eduardo henrique
 * @date 01/09/2008
 * @author Virgínia Melo
 * @date 04/08/2009
 *       Adicionado campo valorLimiteEmissao
 *       Retirados campos periodoInicialConta, periodoFinalConta, periodoVencimentoContaInicial,
 *       periodoVencimentoContaFinal.
 */
public class ManterComandoAcaoCobrancaDetalhesActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String descricaoAcaoCobranca;

	private String gerenciaRegional;

	private String unidadeNegocio;

	private String localidadeOrigemID;

	private String localidadeDestinoID;

	private String idClienteCombo;

	private String identificadorInformacaoArquivo;

	private FormFile arquivoImoveis;

	private String inscricaoTipo;

	private String nomeLocalidadeOrigem;

	private String nomeLocalidadeDestino;

	private String setorComercialOrigemCD;

	private String setorComercialOrigemID;

	private String nomeSetorComercialOrigem;

	private String setorComercialDestinoCD;

	private String setorComercialDestinoID;

	private String nomeSetorComercialDestino;

	private String quadraInicial;

	private String quadraFinal;

	private String rotaInicial;

	private String rotaFinal;

	private String idCliente;

	private String nomeCliente;

	private String clienteRelacaoTipo;

	/*
	 * private String periodoInicialConta;
	 * private String periodoFinalConta;
	 * private String periodoVencimentoContaInicial;
	 * private String periodoVencimentoContaFinal;
	 */

	private String cobrancaAcao;

	private String cobrancaAtividade;

	private String cobrancaGrupo;

	private String indicadorCriterioComando;

	private String cobrancaAtividadeIndicadorExecucao;

	private String idCriterioCobranca;

	private String titulo;

	private String descricaoSolicitacao;

	private String prazoExecucao;

	private String quantidadeMaximaDocumentos;

	private String indicadorImoveisDebito;

	private String indicadorGerarBoletimCadastro;

	private String dataRealizacao;

	private String codigoClienteSuperior;

	private String nomeClienteSuperior;

	private String idAcao;

	private String descricaoAcao;

	private String idPrograma;

	private String descricaoPrograma;

	private String idEmpresa;

	private String dataPrevista;

	private String dataPrazo;

	private String cobrancaCriterio;

	private String nomeCobrancaCriterio;

	public static String DESABILITARPARAMETROSADICIONAIS;

	private String tituloComandoPrecedente;

	private String tituloCompletoComandoPrecedente;

	private String indicadorGerarRelacaoDocumento;

	private String formatoArquivo;

	/**
	 * @return Returns the gerenciaRegional.
	 */
	public String getGerenciaRegional(){

		return gerenciaRegional;
	}

	/**
	 * @param gerenciaRegional
	 *            The gerenciaRegional to set.
	 */
	public void setGerenciaRegional(String gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	/**
	 * @return Returns the localidadeOrigemID.
	 */
	public String getLocalidadeOrigemID(){

		return localidadeOrigemID;
	}

	/**
	 * @param localidadeOrigemID
	 *            The localidadeOrigemID to set.
	 */
	public void setLocalidadeOrigemID(String localidadeOrigemID){

		this.localidadeOrigemID = localidadeOrigemID;
	}

	/**
	 * @return Returns the inscricaoTipo.
	 */
	public String getInscricaoTipo(){

		return inscricaoTipo;
	}

	/**
	 * @param inscricaoTipo
	 *            The inscricaoTipo to set.
	 */
	public void setInscricaoTipo(String inscricaoTipo){

		this.inscricaoTipo = inscricaoTipo;
	}

	/**
	 * @return Returns the nomeLocalidadeOrigem.
	 */
	public String getNomeLocalidadeOrigem(){

		return nomeLocalidadeOrigem;
	}

	/**
	 * @param nomeLocalidadeOrigem
	 *            The nomeLocalidadeOrigem to set.
	 */
	public void setNomeLocalidadeOrigem(String nomeLocalidadeOrigem){

		this.nomeLocalidadeOrigem = nomeLocalidadeOrigem;
	}

	/**
	 * @return Returns the localidadeDestinoID.
	 */
	public String getLocalidadeDestinoID(){

		return localidadeDestinoID;
	}

	/**
	 * @param localidadeDestinoID
	 *            The localidadeDestinoID to set.
	 */
	public void setLocalidadeDestinoID(String localidadeDestinoID){

		this.localidadeDestinoID = localidadeDestinoID;
	}

	/**
	 * @return Returns the nomeLocalidadeDestino.
	 */
	public String getNomeLocalidadeDestino(){

		return nomeLocalidadeDestino;
	}

	/**
	 * @param nomeLocalidadeDestino
	 *            The nomeLocalidadeDestino to set.
	 */
	public void setNomeLocalidadeDestino(String nomeLocalidadeDestino){

		this.nomeLocalidadeDestino = nomeLocalidadeDestino;
	}

	/**
	 * @return Returns the setorComercialOrigemCD.
	 */
	public String getSetorComercialOrigemCD(){

		return setorComercialOrigemCD;
	}

	/**
	 * @param setorComercialOrigemCD
	 *            The setorComercialOrigemCD to set.
	 */
	public void setSetorComercialOrigemCD(String setorComercialOrigemCD){

		this.setorComercialOrigemCD = setorComercialOrigemCD;
	}

	/**
	 * @return Returns the setorComercialOrigemID.
	 */
	public String getSetorComercialOrigemID(){

		return setorComercialOrigemID;
	}

	/**
	 * @param setorComercialOrigemID
	 *            The setorComercialOrigemID to set.
	 */
	public void setSetorComercialOrigemID(String setorComercialOrigemID){

		this.setorComercialOrigemID = setorComercialOrigemID;
	}

	/**
	 * @return Returns the nomeSetorComercialOrigem.
	 */
	public String getNomeSetorComercialOrigem(){

		return nomeSetorComercialOrigem;
	}

	/**
	 * @param nomeSetorComercialOrigem
	 *            The nomeSetorComercialOrigem to set.
	 */
	public void setNomeSetorComercialOrigem(String nomeSetorComercialOrigem){

		this.nomeSetorComercialOrigem = nomeSetorComercialOrigem;
	}

	/**
	 * @return Returns the nomeSetorComercialDestino.
	 */
	public String getNomeSetorComercialDestino(){

		return nomeSetorComercialDestino;
	}

	/**
	 * @param nomeSetorComercialDestino
	 *            The nomeSetorComercialDestino to set.
	 */
	public void setNomeSetorComercialDestino(String nomeSetorComercialDestino){

		this.nomeSetorComercialDestino = nomeSetorComercialDestino;
	}

	/**
	 * @return Returns the setorComercialDestinoCD.
	 */
	public String getSetorComercialDestinoCD(){

		return setorComercialDestinoCD;
	}

	/**
	 * @param setorComercialDestinoCD
	 *            The setorComercialDestinoCD to set.
	 */
	public void setSetorComercialDestinoCD(String setorComercialDestinoCD){

		this.setorComercialDestinoCD = setorComercialDestinoCD;
	}

	/**
	 * @return Returns the setorComercialDestinoID.
	 */
	public String getSetorComercialDestinoID(){

		return setorComercialDestinoID;
	}

	/**
	 * @param setorComercialDestinoID
	 *            The setorComercialDestinoID to set.
	 */
	public void setSetorComercialDestinoID(String setorComercialDestinoID){

		this.setorComercialDestinoID = setorComercialDestinoID;
	}

	public String getQuadraInicial(){

		return quadraInicial;
	}

	public void setQuadraInicial(String quadraInicial){

		this.quadraInicial = quadraInicial;
	}

	public String getQuadraFinal(){

		return quadraFinal;
	}

	public void setQuadraFinal(String quadraFinal){

		this.quadraFinal = quadraFinal;
	}

	/**
	 * @return Returns the rota.
	 */
	public String getRotaInicial(){

		return rotaInicial;
	}

	/**
	 * @param rota
	 *            The rota to set.
	 */
	public void setRotaInicial(String rota){

		this.rotaInicial = rota;
	}

	/**
	 * @return Returns the rotaFinal.
	 */
	public String getRotaFinal(){

		return rotaFinal;
	}

	/**
	 * @param rotaFinal
	 *            The rotaFinal to set.
	 */
	public void setRotaFinal(String rotaFinal){

		this.rotaFinal = rotaFinal;
	}

	/**
	 * @return Returns the idCliente.
	 */
	public String getIdCliente(){

		return idCliente;
	}

	/**
	 * @param idCliente
	 *            The idCliente to set.
	 */
	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	/**
	 * @return Returns the nomeCliente.
	 */
	public String getNomeCliente(){

		return nomeCliente;
	}

	/**
	 * @param nomeCliente
	 *            The nomeCliente to set.
	 */
	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	/**
	 * @return Returns the clienteRelacaoTipo.
	 */
	public String getClienteRelacaoTipo(){

		return clienteRelacaoTipo;
	}

	/**
	 * @param clienteRelacaoTipo
	 *            The clienteRelacaoTipo to set.
	 */
	public void setClienteRelacaoTipo(String clienteRelacaoTipo){

		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

	/**
	 * @return Returns the cobrancaAcao.
	 */
	public String getCobrancaAcao(){

		return cobrancaAcao;
	}

	/**
	 * @param cobrancaAcao
	 *            The cobrancaAcao to set.
	 */
	public void setCobrancaAcao(String cobrancaAcao){

		this.cobrancaAcao = cobrancaAcao;
	}

	/**
	 * @return Returns the cobrancaAtividade.
	 */
	public String getCobrancaAtividade(){

		return cobrancaAtividade;
	}

	/**
	 * @param cobrancaAtividade
	 *            The cobrancaAtividade to set.
	 */
	public void setCobrancaAtividade(String cobrancaAtividade){

		this.cobrancaAtividade = cobrancaAtividade;
	}

	/**
	 * @return Returns the cobrancaGrupo.
	 */
	public String getCobrancaGrupo(){

		return cobrancaGrupo;
	}

	/**
	 * @param cobrancaGrupo
	 *            The cobrancaGrupo to set.
	 */
	public void setCobrancaGrupo(String cobrancaGrupo){

		this.cobrancaGrupo = cobrancaGrupo;
	}

	/**
	 * @return Returns the indicadorCriterioComando.
	 */
	public String getIndicadorCriterioComando(){

		return indicadorCriterioComando;
	}

	/**
	 * @param indicadorCriterioComando
	 *            The indicadorCriterioComando to set.
	 */
	public void setIndicadorCriterioComando(String indicadorCriterioComando){

		this.indicadorCriterioComando = indicadorCriterioComando;
	}

	/**
	 * @return Retorna o campo cobrancaAtividadeIndicadorExecucao.
	 */
	public String getCobrancaAtividadeIndicadorExecucao(){

		return cobrancaAtividadeIndicadorExecucao;
	}

	/**
	 * @param cobrancaAtividadeIndicadorExecucao
	 *            O cobrancaAtividadeIndicadorExecucao a ser setado.
	 */
	public void setCobrancaAtividadeIndicadorExecucao(String cobrancaAtividadeIndicadorExecucao){

		this.cobrancaAtividadeIndicadorExecucao = cobrancaAtividadeIndicadorExecucao;
	}

	/**
	 * @return Retorna o campo descricaoAcaoCobranca.
	 */
	public String getDescricaoAcaoCobranca(){

		return descricaoAcaoCobranca;
	}

	/**
	 * @param descricaoAcaoCobranca
	 *            O descricaoAcaoCobranca a ser setado.
	 */
	public void setDescricaoAcaoCobranca(String descricaoAcaoCobranca){

		this.descricaoAcaoCobranca = descricaoAcaoCobranca;
	}

	public String getIdCriterioCobranca(){

		return idCriterioCobranca;
	}

	public void setIdCriterioCobranca(String idCriterioCobranca){

		this.idCriterioCobranca = idCriterioCobranca;
	}

	/**
	 * @return Retorna o campo unidadeNegocio.
	 */
	public String getUnidadeNegocio(){

		return unidadeNegocio;
	}

	/**
	 * @param unidadeNegocio
	 *            O unidadeNegocio a ser setado.
	 */
	public void setUnidadeNegocio(String unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public String getDescricaoSolicitacao(){

		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao){

		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public String getPrazoExecucao(){

		return prazoExecucao;
	}

	public void setPrazoExecucao(String prazoExecucao){

		this.prazoExecucao = prazoExecucao;
	}

	public String getTitulo(){

		return titulo;
	}

	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	public String getIndicadorGerarBoletimCadastro(){

		return indicadorGerarBoletimCadastro;
	}

	public void setIndicadorGerarBoletimCadastro(String indicadorGerarBoletimCadastro){

		this.indicadorGerarBoletimCadastro = indicadorGerarBoletimCadastro;
	}

	public String getIndicadorImoveisDebito(){

		return indicadorImoveisDebito;
	}

	public void setIndicadorImoveisDebito(String indicadorImoveisDebito){

		this.indicadorImoveisDebito = indicadorImoveisDebito;
	}

	public String getQuantidadeMaximaDocumentos(){

		return quantidadeMaximaDocumentos;
	}

	public void setQuantidadeMaximaDocumentos(String quantidadeMaximaDocumentos){

		this.quantidadeMaximaDocumentos = quantidadeMaximaDocumentos;
	}

	public String getDataRealizacao(){

		return dataRealizacao;
	}

	public void setDataRealizacao(String dataRealizacao){

		this.dataRealizacao = dataRealizacao;
	}

	public String getCodigoClienteSuperior(){

		return codigoClienteSuperior;
	}

	public void setCodigoClienteSuperior(String codigoClienteSuperior){

		this.codigoClienteSuperior = codigoClienteSuperior;
	}

	public String getNomeClienteSuperior(){

		return nomeClienteSuperior;
	}

	public void setNomeClienteSuperior(String nomeClienteSuperior){

		this.nomeClienteSuperior = nomeClienteSuperior;
	}

	/**
	 * @return the idAcao
	 */
	public String getIdAcao(){

		return idAcao;
	}

	/**
	 * @param idAcao
	 *            the idAcao to set
	 */
	public void setIdAcao(String idAcao){

		this.idAcao = idAcao;
	}

	/**
	 * @return the descricaoAcao
	 */
	public String getDescricaoAcao(){

		return descricaoAcao;
	}

	/**
	 * @param descricaoAcao
	 *            the descricaoAcao to set
	 */
	public void setDescricaoAcao(String descricaoAcao){

		this.descricaoAcao = descricaoAcao;
	}

	/**
	 * @return the idPrograma
	 */
	public String getIdPrograma(){

		return idPrograma;
	}

	/**
	 * @param idPrograma
	 *            the idPrograma to set
	 */
	public void setIdPrograma(String idPrograma){

		this.idPrograma = idPrograma;
	}

	/**
	 * @return the descricaoPrograma
	 */
	public String getDescricaoPrograma(){

		return descricaoPrograma;
	}

	/**
	 * @param descricaoPrograma
	 *            the descricaoPrograma to set
	 */
	public void setDescricaoPrograma(String descricaoPrograma){

		this.descricaoPrograma = descricaoPrograma;
	}

	/**
	 * @return the idEmpresa
	 */
	public String getIdEmpresa(){

		return idEmpresa;
	}

	/**
	 * @param idEmpresa
	 *            the idEmpresa to set
	 */
	public void setIdEmpresa(String idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return the dataPrevista
	 */
	public String getDataPrevista(){

		return dataPrevista;
	}

	/**
	 * @param dataPrevista
	 *            the dataPrevista to set
	 */
	public void setDataPrevista(String dataPrevista){

		this.dataPrevista = dataPrevista;
	}

	/**
	 * @return the dataPrazo
	 */
	public String getDataPrazo(){

		return dataPrazo;
	}

	/**
	 * @param dataPrazo
	 *            the dataPrazo to set
	 */
	public void setDataPrazo(String dataPrazo){

		this.dataPrazo = dataPrazo;
	}

	public void setIdClienteCombo(String idClienteCombo){

		this.idClienteCombo = idClienteCombo;
	}

	public String getIdClienteCombo(){

		return idClienteCombo;
	}

	public void setArquivoImoveis(FormFile arquivoImoveis){

		this.arquivoImoveis = arquivoImoveis;
	}

	public FormFile getArquivoImoveis(){

		return arquivoImoveis;
	}

	public void setIdentificadorInformacaoArquivo(String identificadorInformacaoArquivo){

		this.identificadorInformacaoArquivo = identificadorInformacaoArquivo;
	}

	public String getIdentificadorInformacaoArquivo(){

		return identificadorInformacaoArquivo;
	}

	public String getCobrancaCriterio(){

		return cobrancaCriterio;
	}

	public void setCobrancaCriterio(String cobrancaCriterio){

		this.cobrancaCriterio = cobrancaCriterio;
	}

	public String getNomeCobrancaCriterio(){

		return nomeCobrancaCriterio;
	}

	public void setNomeCobrancaCriterio(String nomeCobrancaCriterio){

		this.nomeCobrancaCriterio = nomeCobrancaCriterio;
	}

	public String getTituloComandoPrecedente(){

		return tituloComandoPrecedente;
	}

	public void setTituloComandoPrecedente(String tituloComandoPrecedente){

		this.tituloComandoPrecedente = tituloComandoPrecedente;
	}

	public String getTituloCompletoComandoPrecedente(){

		return tituloCompletoComandoPrecedente;
	}

	public void setTituloCompletoComandoPrecedente(String tituloCompletoComandoPrecedente){

		this.tituloCompletoComandoPrecedente = tituloCompletoComandoPrecedente;
	}

	public String getIndicadorGerarRelacaoDocumento(){

		return indicadorGerarRelacaoDocumento;
	}

	public void setIndicadorGerarRelacaoDocumento(String indicadorGerarRelacaoDocumento){

		this.indicadorGerarRelacaoDocumento = indicadorGerarRelacaoDocumento;
	}

	public String getFormatoArquivo(){

		return formatoArquivo;
	}

	public void setFormatoArquivo(String formatoArquivo){

		this.formatoArquivo = formatoArquivo;
	}

}
