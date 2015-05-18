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

package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Esta classe tem por finalidade gerar o formulário que receberá os parâmetros
 * para realização da inserção de um R.A
 * 
 * @author Raphael Rossiter
 * @date 24/07/2006
 */
public class InserirRegistroAtendimentoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String clienteObrigatorio;

	// ABA Nº 01
	private String tipo;

	private String numeroAtendimentoManual;

	private String dataAtendimento;

	private String hora;

	private String tempoEsperaInicial;

	private String tempoEsperaFinal;

	private String unidade;

	private String descricaoUnidade;

	private String meioSolicitacao;

	private String senhaAtendimento;

	private String tipoSolicitacao;

	private String especificacao;

	private String dataPrevista;

	private String observacao;

	private String indicadorProcessoAdmJud;

	private String numeroProcessoAgencia;

	// ABA Nº 02
	private String imovelObrigatorio;

	private String pavimentoRuaObrigatorio;

	private String pavimentoCalcadaObrigatorio;

	private String idImovel;

	private String inscricaoImovel;

	private String pontoReferencia;

	private String idMunicipio;

	private String descricaoMunicipio;

	private String cdBairro;

	private String descricaoBairro;

	private String idBairro;

	private String idBairroArea;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String cdSetorComercial;

	private String descricaoSetorComercial;

	private String idSetorComercial;

	private String nnQuadra;

	private String idQuadra;

	private String idDivisaoEsgoto;

	private String idUnidadeDestino;

	private String descricaoUnidadeDestino;

	private String parecerUnidadeDestino;

	private String idLocalOcorrencia;

	private String idPavimentoRua;

	private String idPavimentoCalcada;

	private String descricaoLocalOcorrencia;

	private String coordenadaNorte;

	private String coordenadaLeste;

	// ABA Nº 03

	private String idImovelAssociacaoCliente;

	private String idCliente;

	private String nomeCliente;

	private String idUnidadeSolicitante;

	private String descricaoUnidadeSolicitante;

	private String idFuncionarioResponsavel;

	private String nomeFuncionarioResponsavel;

	private String nomeSolicitante;

	private String clienteEnderecoSelected;

	private String pontoReferenciaSolicitante;

	private String clienteFoneSelected;

	private String ddd;

	private String fone;

	private String indicadorClienteEspecificacao;

	private String indRuaLocalOcorrencia;

	private String indCalcadaLocalOcorrencia;

	private String indFaltaAgua;

	private String indMatricula;

	private String sequenceRA;

	private String nomeContato;

	private String motivoAtendimentoIncompleto;

	private String idRaReiteracao;

	private String descricaoRA;

	private String clienteTipo;

	private String numeroCpf;

	private String numeroRG;

	private String orgaoExpedidorRg;

	private String unidadeFederacaoRG;

	private String numeroCnpj;

	private String indicadorCpfCnpj;

	private String indicadorRg;

	private String sugerirUnidadeDestinoRA = "S";

	private String quantidadePrestacoesGuiaPagamento;

	public String getQuantidadePrestacoesGuiaPagamento(){

		return quantidadePrestacoesGuiaPagamento;
	}

	public void setQuantidadePrestacoesGuiaPagamento(String quantidadePrestacoesGuiaPagamento){

		this.quantidadePrestacoesGuiaPagamento = quantidadePrestacoesGuiaPagamento;
	}

	public String getDescricaoRA(){

		return descricaoRA;
	}

	public void setDescricaoRA(String descricaoRA){

		this.descricaoRA = descricaoRA;
	}

	public String getMotivoAtendimentoIncompleto(){

		return motivoAtendimentoIncompleto;
	}

	public void setMotivoAtendimentoIncompleto(String motivoAtendimentoIncompleto){

		this.motivoAtendimentoIncompleto = motivoAtendimentoIncompleto;
	}

	public String getNomeContato(){

		return nomeContato;
	}

	public void setNomeContato(String nomeContato){

		this.nomeContato = nomeContato;
	}

	public String getSequenceRA(){

		return sequenceRA;
	}

	public void setSequenceRA(String sequenceRA){

		this.sequenceRA = sequenceRA;
	}

	public String getIdImovelAssociacaoCliente(){

		return idImovelAssociacaoCliente;
	}

	public void setIdImovelAssociacaoCliente(String idImovelAssociacaoCliente){

		this.idImovelAssociacaoCliente = idImovelAssociacaoCliente;
	}

	public String getNumeroAtendimentoManual(){

		return numeroAtendimentoManual;
	}

	public void setNumeroAtendimentoManual(String numeroAtendimentoManual){

		this.numeroAtendimentoManual = numeroAtendimentoManual;
	}

	public String getIndFaltaAgua(){

		return indFaltaAgua;
	}

	public void setIndFaltaAgua(String indFaltaAgua){

		this.indFaltaAgua = indFaltaAgua;
	}

	public String getIndMatricula(){

		return indMatricula;
	}

	public void setIndMatricula(String indMatricula){

		this.indMatricula = indMatricula;
	}

	public String getIndCalcadaLocalOcorrencia(){

		return indCalcadaLocalOcorrencia;
	}

	public void setIndCalcadaLocalOcorrencia(String indCalcadaLocalOcorrencia){

		this.indCalcadaLocalOcorrencia = indCalcadaLocalOcorrencia;
	}

	public String getIndRuaLocalOcorrencia(){

		return indRuaLocalOcorrencia;
	}

	public void setIndRuaLocalOcorrencia(String indRuaLocalOcorrencia){

		this.indRuaLocalOcorrencia = indRuaLocalOcorrencia;
	}

	public String getIndicadorClienteEspecificacao(){

		return indicadorClienteEspecificacao;
	}

	public void setIndicadorClienteEspecificacao(String indicadorClienteEspecificacao){

		this.indicadorClienteEspecificacao = indicadorClienteEspecificacao;
	}

	public String getClienteEnderecoSelected(){

		return clienteEnderecoSelected;
	}

	public void setClienteEnderecoSelected(String clienteEnderecoSelected){

		this.clienteEnderecoSelected = clienteEnderecoSelected;
	}

	public String getClienteFoneSelected(){

		return clienteFoneSelected;
	}

	public void setClienteFoneSelected(String clienteFoneSelected){

		this.clienteFoneSelected = clienteFoneSelected;
	}

	public String getDescricaoUnidadeSolicitante(){

		return descricaoUnidadeSolicitante;
	}

	public void setDescricaoUnidadeSolicitante(String descricaoUnidadeSolicitante){

		this.descricaoUnidadeSolicitante = descricaoUnidadeSolicitante;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getIdFuncionarioResponsavel(){

		return idFuncionarioResponsavel;
	}

	public void setIdFuncionarioResponsavel(String idFuncionarioResponsavel){

		this.idFuncionarioResponsavel = idFuncionarioResponsavel;
	}

	public String getIdUnidadeSolicitante(){

		return idUnidadeSolicitante;
	}

	public void setIdUnidadeSolicitante(String idUnidadeSolicitante){

		this.idUnidadeSolicitante = idUnidadeSolicitante;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeFuncionarioResponsavel(){

		return nomeFuncionarioResponsavel;
	}

	public void setNomeFuncionarioResponsavel(String nomeFuncionarioResponsavel){

		this.nomeFuncionarioResponsavel = nomeFuncionarioResponsavel;
	}

	public String getNomeSolicitante(){

		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante){

		this.nomeSolicitante = nomeSolicitante;
	}

	public String getPontoReferenciaSolicitante(){

		return pontoReferenciaSolicitante;
	}

	public void setPontoReferenciaSolicitante(String pontoReferenciaSolicitante){

		this.pontoReferenciaSolicitante = pontoReferenciaSolicitante;
	}

	public String getPavimentoCalcadaObrigatorio(){

		return pavimentoCalcadaObrigatorio;
	}

	public void setPavimentoCalcadaObrigatorio(String pavimentoCalcadaObrigatorio){

		this.pavimentoCalcadaObrigatorio = pavimentoCalcadaObrigatorio;
	}

	public String getPavimentoRuaObrigatorio(){

		return pavimentoRuaObrigatorio;
	}

	public void setPavimentoRuaObrigatorio(String pavimentoRuaObrigatorio){

		this.pavimentoRuaObrigatorio = pavimentoRuaObrigatorio;
	}

	public String getImovelObrigatorio(){

		return imovelObrigatorio;
	}

	public void setImovelObrigatorio(String imovelObrigatorio){

		this.imovelObrigatorio = imovelObrigatorio;
	}

	public String getIdQuadra(){

		return idQuadra;
	}

	public void setIdQuadra(String idQuadra){

		this.idQuadra = idQuadra;
	}

	public String getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(String idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public String getIdBairro(){

		return idBairro;
	}

	public void setIdBairro(String idBairro){

		this.idBairro = idBairro;
	}

	public String getCdBairro(){

		return cdBairro;
	}

	public void setCdBairro(String cdBairro){

		this.cdBairro = cdBairro;
	}

	public String getCdSetorComercial(){

		return cdSetorComercial;
	}

	public void setCdSetorComercial(String cdSetorComercial){

		this.cdSetorComercial = cdSetorComercial;
	}

	public String getDescricaoBairro(){

		return descricaoBairro;
	}

	public void setDescricaoBairro(String descricaoBairro){

		this.descricaoBairro = descricaoBairro;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getDescricaoLocalOcorrencia(){

		return descricaoLocalOcorrencia;
	}

	public void setDescricaoLocalOcorrencia(String descricaoLocalOcorrencia){

		this.descricaoLocalOcorrencia = descricaoLocalOcorrencia;
	}

	public String getDescricaoMunicipio(){

		return descricaoMunicipio;
	}

	public void setDescricaoMunicipio(String descricaoMunicipio){

		this.descricaoMunicipio = descricaoMunicipio;
	}

	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	public String getDescricaoUnidadeDestino(){

		return descricaoUnidadeDestino;
	}

	public void setDescricaoUnidadeDestino(String descricaoUnidadeDestino){

		this.descricaoUnidadeDestino = descricaoUnidadeDestino;
	}

	public String getIdBairroArea(){

		return idBairroArea;
	}

	public void setIdBairroArea(String idBairroArea){

		this.idBairroArea = idBairroArea;
	}

	public String getIdDivisaoEsgoto(){

		return idDivisaoEsgoto;
	}

	public void setIdDivisaoEsgoto(String idDivisaoEsgoto){

		this.idDivisaoEsgoto = idDivisaoEsgoto;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getIdLocalOcorrencia(){

		return idLocalOcorrencia;
	}

	public void setIdLocalOcorrencia(String idLocalOcorrencia){

		this.idLocalOcorrencia = idLocalOcorrencia;
	}

	public String getIdMunicipio(){

		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio){

		this.idMunicipio = idMunicipio;
	}

	public String getIdPavimentoCalcada(){

		return idPavimentoCalcada;
	}

	public void setIdPavimentoCalcada(String idPavimentoCalcada){

		this.idPavimentoCalcada = idPavimentoCalcada;
	}

	public String getIdPavimentoRua(){

		return idPavimentoRua;
	}

	public void setIdPavimentoRua(String idPavimentoRua){

		this.idPavimentoRua = idPavimentoRua;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getNnQuadra(){

		return nnQuadra;
	}

	public void setNnQuadra(String nnQuadra){

		this.nnQuadra = nnQuadra;
	}

	public String getParecerUnidadeDestino(){

		return parecerUnidadeDestino;
	}

	public void setParecerUnidadeDestino(String parecerUnidadeDestino){

		this.parecerUnidadeDestino = parecerUnidadeDestino;
	}

	public String getPontoReferencia(){

		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia){

		this.pontoReferencia = pontoReferencia;
	}

	public String getIdUnidadeDestino(){

		return idUnidadeDestino;
	}

	public void setIdUnidadeDestino(String idUnidadeDestino){

		this.idUnidadeDestino = idUnidadeDestino;
	}

	public String getObservacao(){

		return observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public String getDataAtendimento(){

		return dataAtendimento;
	}

	public void setDataAtendimento(String dataAtendimento){

		this.dataAtendimento = dataAtendimento;
	}

	public String getDataPrevista(){

		return dataPrevista;
	}

	public void setDataPrevista(String dataPrevista){

		this.dataPrevista = dataPrevista;
	}

	public String getDescricaoUnidade(){

		return descricaoUnidade;
	}

	public void setDescricaoUnidade(String descricaoUnidade){

		this.descricaoUnidade = descricaoUnidade;
	}

	public String getEspecificacao(){

		return especificacao;
	}

	public void setEspecificacao(String especificacao){

		this.especificacao = especificacao;
	}

	public String getHora(){

		return hora;
	}

	public void setHora(String hora){

		this.hora = hora;
	}

	public String getMeioSolicitacao(){

		return meioSolicitacao;
	}

	public void setMeioSolicitacao(String meioSolicitacao){

		this.meioSolicitacao = meioSolicitacao;
	}

	public String getTempoEsperaFinal(){

		return tempoEsperaFinal;
	}

	public void setTempoEsperaFinal(String tempoEsperaFinal){

		this.tempoEsperaFinal = tempoEsperaFinal;
	}

	public String getTempoEsperaInicial(){

		return tempoEsperaInicial;
	}

	public void setTempoEsperaInicial(String tempoEsperaInicial){

		this.tempoEsperaInicial = tempoEsperaInicial;
	}

	public String getTipo(){

		return tipo;
	}

	public void setTipo(String tipo){

		this.tipo = tipo;
	}

	public String getTipoSolicitacao(){

		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String tipoSolicitacao){

		this.tipoSolicitacao = tipoSolicitacao;
	}

	public String getUnidade(){

		return unidade;
	}

	public void setUnidade(String unidade){

		this.unidade = unidade;
	}

	public String getClienteObrigatorio(){

		return clienteObrigatorio;
	}

	public void setClienteObrigatorio(String clienteObrigatorio){

		this.clienteObrigatorio = clienteObrigatorio;
	}

	public String getSenhaAtendimento(){

		return senhaAtendimento;
	}

	public void setSenhaAtendimento(String senhaAtendimento){

		this.senhaAtendimento = senhaAtendimento;
	}

	public String getCoordenadaNorte(){

		return coordenadaNorte;
	}

	public void setCoordenadaNorte(String coordenadaNorte){

		this.coordenadaNorte = coordenadaNorte;
	}

	public String getCoordenadaLeste(){

		return coordenadaLeste;
	}

	public void setCoordenadaLeste(String coordenadaLeste){

		this.coordenadaLeste = coordenadaLeste;
	}

	public String getIdRaReiteracao(){

		return idRaReiteracao;
	}

	public void setIdRaReiteracao(String idRaReiteracao){

		this.idRaReiteracao = idRaReiteracao;
	}

	public String getClienteTipo(){

		return clienteTipo;
	}

	public void setClienteTipo(String clienteTipo){

		this.clienteTipo = clienteTipo;
	}

	public String getNumeroCpf(){

		return numeroCpf;
	}

	public void setNumeroCpf(String numeroCpf){

		this.numeroCpf = numeroCpf;
	}

	public String getNumeroRG(){

		return numeroRG;
	}

	public void setNumeroRG(String numeroRG){

		this.numeroRG = numeroRG;
	}

	public String getOrgaoExpedidorRg(){

		return orgaoExpedidorRg;
	}

	public void setOrgaoExpedidorRg(String orgaoExpedidorRg){

		this.orgaoExpedidorRg = orgaoExpedidorRg;
	}

	public String getUnidadeFederacaoRG(){

		return unidadeFederacaoRG;
	}

	public void setUnidadeFederacaoRG(String unidadeFederacaoRG){

		this.unidadeFederacaoRG = unidadeFederacaoRG;
	}

	public String getNumeroCnpj(){

		return numeroCnpj;
	}

	public void setNumeroCnpj(String numeroCnpj){

		this.numeroCnpj = numeroCnpj;
	}

	public String getIndicadorCpfCnpj(){

		return indicadorCpfCnpj;
	}

	public void setIndicadorCpfCnpj(String indicadorCpfCnpj){

		this.indicadorCpfCnpj = indicadorCpfCnpj;
	}

	public String getIndicadorRg(){

		return indicadorRg;
	}

	public void setIndicadorRg(String indicadorRg){

		this.indicadorRg = indicadorRg;
	}

	public String getDdd(){

		return ddd;
	}

	public void setDddClienteFoneSelected(String ddd){

		this.ddd = ddd;
	}

	public String getFone(){

		return fone;
	}

	public void setFoneClienteFoneSelected(String fone){

		this.fone = fone;
	}

	/**
	 * @return the sugerirUnidadeDestinoRA
	 */
	public String getSugerirUnidadeDestinoRA(){

		return sugerirUnidadeDestinoRA;
	}

	/**
	 * @param sugerirUnidadeDestinoRA
	 *            the sugerirUnidadeDestinoRA to set
	 */
	public void setSugerirUnidadeDestinoRA(String sugerirUnidadeDestinoRA){

		this.sugerirUnidadeDestinoRA = sugerirUnidadeDestinoRA;
	}

	/**
	 * @return the indicadorProcessoAdmJud
	 */
	public String getIndicadorProcessoAdmJud(){

		return indicadorProcessoAdmJud;
	}

	/**
	 * @param indicadorProcessoAdmJud
	 *            the indicadorProcessoAdmJud to set
	 */
	public void setIndicadorProcessoAdmJud(String indicadorProcessoAdmJud){

		this.indicadorProcessoAdmJud = indicadorProcessoAdmJud;
	}

	/**
	 * @return the numeroProcessoAgencia
	 */
	public String getNumeroProcessoAgencia(){

		return numeroProcessoAgencia;
	}

	/**
	 * @param numeroProcessoAgencia
	 *            the numeroProcessoAgencia to set
	 */
	public void setNumeroProcessoAgencia(String numeroProcessoAgencia){

		this.numeroProcessoAgencia = numeroProcessoAgencia;
	}

}
