/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.micromedicao;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class LeituraConsumoActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String tipoApresentacao;

	private String localidade;

	private String nomeLocalidade;

	private String setorComercialID;

	private String setorComercial;

	private String setorComercialNome;

	private String quadraInicial;

	private String quadraInicialNome;

	private String quadraInicialID;

	private String quadraInicialMensagem;

	private String quadraFinal;

	private String quadraFinalNome;

	private String quadraFinalID;

	private String quadraFinalMensagem;

	private String imovel;

	private String imovelCondominio;

	private String grupoFaturamento;

	private String empresaLeitura;

	private String indicadorImovelCondominio;

	private String perfilImovel;

	private String categoriaImovel;

	private String quantidadeEconomia;

	private String tipoMedicao;

	private String idTipoMedicao;

	private String tipoLigacao;

	private String tipoAnormalidade;

	private String anormalidadeLeituraInformada;

	private String anormalidadeLeituraFaturada;

	private String anormalidadeConsumo;

	private String consumoFaturamdoMinimo;

	private String consumoMedidoMinimo;

	private String consumoMedioMinimo;

	private String inscricaoTipo;

	private String mesAnoFaturamentoCorrente;

	private String inscricaoImovel;

	private String enderecoFormatado;

	private String ligacaoAguaSituacao;

	private String ligacaoEsgotoSituacao;

	private String clienteNome;

	private String clienteCpfCnpj;

	private String numeroHidrometro;

	private String capacidadeHidrometro;

	private String tipoHidrometro;

	private String marcaHidrometro;

	private String diametroHidrometro;

	private String instalacaoHidrometro;

	private String localInstalacaoHidrometro;

	private String anoFabricacao;

	private String protecaoHidrometro;

	private String indicadorCavalete;

	private String dtLeituraAnterior;

	private String leituraAnterior;

	private String dtLeituraInformada;

	private String leituraAtualInformada;

	private String situacaoLeituraAtual;

	private String codigoFuncionario;

	private String dtLeituraFaturada;

	private String leituraAtualFaturada;

	private String consumoRateio;

	private String consumoFaturado;

	private String consumoInformado;

	private String consumoMedido;

	private String percentualVariacao;

	private String consumoMedioHidrometro;

	private String diasConsumo;

	private String consumoTipo;

	private String idImovelSubstituirConsumo;

	private String habilitaLupa;

	private String idEmpresa;

	private String idGrupoFaturamento;

	private String localidadeFiltro;

	private String nomeLocalidadeFiltro;

	private String setorComercialFiltro;

	private String quadraInicialFiltro;

	private String quadraFinalFiltro;

	private String rotaInicial;

	private String rotaInicialFiltro;

	private String rotaInicialNome;

	private String rotaInicialID;

	private String rotaInicialMensagem;

	private String rotaFinal;

	private String rotaFinalFiltro;

	private String rotaFinalNome;

	private String rotaFinalID;

	private String rotaFinalMensagem;

	private String imovelFiltro;

	private String imovelCondominioFiltro;

	private String imovelMatriculaFiltro;

	private String imovelMatriculaCondominioFiltro;

	private String dataLigacaoAgua;

	private String dataCorteAgua;

	private String dataReligacaoAgua;

	private String dataSupressaoAgua;

	private String dataRestabelecimentoAgua;

	private String descricaoLigacaoAguaDiametro;

	private String descricaoLigacaoAguaMaterial;

	private String descricaoligacaoAguaPerfil;

	private String numeroConsumominimoAgua;

	private String idLigacaoEsgoto;

	private String consumoMesEsgoto;

	private String dataLigacaoEsgoto;

	private String descricaoLigacaoEsgotoDiametro;

	private String descricaoLigacaoEsgotoMaterial;

	private String descricaoligacaoEsgotoPerfil;

	private String numeroConsumominimoEsgoto;

	private String percentualEsgoto;

	private String percentualAguaConsumidaColetada;

	private String descricaoPocoTipo;

	private String consumoFixoPoco;

	private String idGrupoFaturamentoFiltro;

	private String idEmpresaFiltro;

	private String indicadorImovelCondominioFiltro;

	private String consumoMedioImovel;

	private String perfilImovelFiltro;

	private String categoriaImovelFiltro;

	private String quantidadeEconomiaFiltro;

	private String tipoMedicaoFiltro;

	private String idTipoMedicaoFiltro;

	private String tipoLigacaoFiltro;

	private String tipoAnormalidadeFiltro;

	private String anormalidadeLeituraInformadaFiltro;

	private String anormalidadeLeituraFaturadaFiltro;

	private String anormalidadeConsumoFiltro;

	private String consumoFaturamdoMinimoFiltro;

	private String consumoMedidoMinimoFiltro;

	private String consumoMedioMinimoFiltro;

	private String idLigacaoAguaSituacao;

	private String idLigacaoAgua;

	private String idAnormalidade;

	private String descricaoAnormalidade;

	private String dataLeituraAnteriorFaturamento;

	private String leituraAnteriorFaturamento;

	private String dataLeituraAtualInformada;

	private String consumo;

	private String confirmacao;

	private String dataLeituraAtualFaturamento;

	private String varConsumo;

	private String leituraSituacaoAtual;

	private String idFuncionario;

	private String consumoAnormalidadeDescricao;

	private String consumoAnormalidadeAbreviada;

	private String indicadorDebitoAutomatico;

	private String rateio;

	private String leituraSituacaoAtualFiltro;

	private String medido;

	private String consumoMedidoHidrometro;

	private String rota;

	private String seqRota;

	private String creditoFaturado;

	private String creditoGerado;

	private String[] idRegistrosImovel;

	private Boolean relatorio;

	private String nomeFuncionarioHint;

	private String caminhoReload;

	private String indicadorRateio;

	public String getNomeFuncionarioHint(){

		return nomeFuncionarioHint;
	}

	public void setNomeFuncionarioHint(String nomeFuncionarioHint){

		this.nomeFuncionarioHint = nomeFuncionarioHint;
	}

	public Boolean getRelatorio(){

		return relatorio;
	}

	public void setRelatorio(Boolean relatorio){

		this.relatorio = relatorio;
	}

	public String getConsumoMedidoHidrometro(){

		return consumoMedidoHidrometro;
	}

	public void setConsumoMedidoHidrometro(String consumoMedidoHidrometro){

		this.consumoMedidoHidrometro = consumoMedidoHidrometro;
	}

	public String getMedido(){

		return medido;
	}

	public void setMedido(String medido){

		this.medido = medido;
	}

	public String getRateio(){

		return rateio;
	}

	public void setRateio(String rateio){

		this.rateio = rateio;
	}

	public String getIndicadorDebitoAutomatico(){

		return indicadorDebitoAutomatico;
	}

	public void setIndicadorDebitoAutomatico(String indicadorDebitoAutomatico){

		this.indicadorDebitoAutomatico = indicadorDebitoAutomatico;
	}

	public String getConsumoAnormalidadeAbreviada(){

		return consumoAnormalidadeAbreviada;
	}

	public void setConsumoAnormalidadeAbreviada(String consumoAnormalidadeAbreviada){

		this.consumoAnormalidadeAbreviada = consumoAnormalidadeAbreviada;
	}

	public String getIdFuncionario(){

		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario){

		this.idFuncionario = idFuncionario;
	}

	public String getLeituraSituacaoAtual(){

		return leituraSituacaoAtual;
	}

	public void setLeituraSituacaoAtual(String leituraSituacaoAtual){

		this.leituraSituacaoAtual = leituraSituacaoAtual;
	}

	public String getVarConsumo(){

		return varConsumo;
	}

	public void setVarConsumo(String varConsumo){

		this.varConsumo = varConsumo;
	}

	public String getDataLeituraAtualFaturamento(){

		return dataLeituraAtualFaturamento;
	}

	public void setDataLeituraAtualFaturamento(String dataLeituraAtualFaturamento){

		this.dataLeituraAtualFaturamento = dataLeituraAtualFaturamento;
	}

	public String getConfirmacao(){

		return confirmacao;
	}

	public void setConfirmacao(String confirmacao){

		this.confirmacao = confirmacao;
	}

	public String getConsumo(){

		return consumo;
	}

	public void setConsumo(String consumo){

		this.consumo = consumo;
	}

	public String getDataLeituraAnteriorFaturamento(){

		return dataLeituraAnteriorFaturamento;
	}

	public void setDataLeituraAnteriorFaturamento(String dataLeituraAnteriorFaturamento){

		this.dataLeituraAnteriorFaturamento = dataLeituraAnteriorFaturamento;
	}

	public String getDataLeituraAtualInformada(){

		return dataLeituraAtualInformada;
	}

	public void setDataLeituraAtualInformada(String dataLeituraAtualInformada){

		this.dataLeituraAtualInformada = dataLeituraAtualInformada;
	}

	public String getLeituraAnteriorFaturamento(){

		return leituraAnteriorFaturamento;
	}

	public void setLeituraAnteriorFaturamento(String leituraAnteriorFaturamento){

		this.leituraAnteriorFaturamento = leituraAnteriorFaturamento;
	}

	public String getIdLigacaoAgua(){

		return idLigacaoAgua;
	}

	public void setIdLigacaoAgua(String idLigacaoAgua){

		this.idLigacaoAgua = idLigacaoAgua;
	}

	public String getIdEmpresa(){

		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	public String getIdGrupoFaturamento(){

		return idGrupoFaturamento;
	}

	public void setIdGrupoFaturamento(String idGrupoFaturamento){

		this.idGrupoFaturamento = idGrupoFaturamento;
	}

	public String getHabilitaLupa(){

		return habilitaLupa;
	}

	public void setHabilitaLupa(String habilitaLupa){

		this.habilitaLupa = habilitaLupa;
	}

	public String getIdImovelSubstituirConsumo(){

		return idImovelSubstituirConsumo;
	}

	public void setIdImovelSubstituirConsumo(String idImovelSubstituirConsumo){

		this.idImovelSubstituirConsumo = idImovelSubstituirConsumo;
	}

	public String getConsumoTipo(){

		return consumoTipo;
	}

	public void setConsumoTipo(String consumoTipo){

		this.consumoTipo = consumoTipo;
	}

	public String getDiasConsumo(){

		return diasConsumo;
	}

	public void setDiasConsumo(String diasConsumo){

		this.diasConsumo = diasConsumo;
	}

	public String getConsumoMedioHidrometro(){

		return consumoMedioHidrometro;
	}

	public void setConsumoMedioHidrometro(String consumoMedioHidrometro){

		this.consumoMedioHidrometro = consumoMedioHidrometro;
	}

	public String getPercentualVariacao(){

		return percentualVariacao;
	}

	public void setPercentualVariacao(String percentualVariacao){

		this.percentualVariacao = percentualVariacao;
	}

	public String getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(String consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}

	public String getConsumoMedido(){

		return consumoMedido;
	}

	public void setConsumoMedido(String consumoMedido){

		this.consumoMedido = consumoMedido;
	}

	public String getConsumoRateio(){

		return consumoRateio;
	}

	public void setConsumoRateio(String consumoRateio){

		this.consumoRateio = consumoRateio;
	}

	public String getLeituraAtualFaturada(){

		return leituraAtualFaturada;
	}

	public void setLeituraAtualFaturada(String leituraAtualFaturada){

		this.leituraAtualFaturada = leituraAtualFaturada;
	}

	public String getDtLeituraFaturada(){

		return dtLeituraFaturada;
	}

	public void setDtLeituraFaturada(String dtLeituraFaturada){

		this.dtLeituraFaturada = dtLeituraFaturada;
	}

	public String getCodigoFuncionario(){

		return codigoFuncionario;
	}

	public void setCodigoFuncionario(String codigoFuncionario){

		this.codigoFuncionario = codigoFuncionario;
	}

	public String getSituacaoLeituraAtual(){

		return situacaoLeituraAtual;
	}

	public void setSituacaoLeituraAtual(String situacaoLeituraAtual){

		this.situacaoLeituraAtual = situacaoLeituraAtual;
	}

	public String getLeituraAtualInformada(){

		return leituraAtualInformada;
	}

	public void setLeituraAtualInformada(String leituraAtualInformada){

		this.leituraAtualInformada = leituraAtualInformada;
	}

	public String getDtLeituraInformada(){

		return dtLeituraInformada;
	}

	public void setDtLeituraInformada(String dtLeituraInformada){

		this.dtLeituraInformada = dtLeituraInformada;
	}

	public String getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public String getDtLeituraAnterior(){

		return dtLeituraAnterior;
	}

	public void setDtLeituraAnterior(String dtLeituraAnterior){

		this.dtLeituraAnterior = dtLeituraAnterior;
	}

	public String getIndicadorCavalete(){

		return indicadorCavalete;
	}

	public void setIndicadorCavalete(String indicadorCavalete){

		this.indicadorCavalete = indicadorCavalete;
	}

	public String getProtecaoHidrometro(){

		return protecaoHidrometro;
	}

	public void setProtecaoHidrometro(String protecaoHidrometro){

		this.protecaoHidrometro = protecaoHidrometro;
	}

	public String getAnoFabricacao(){

		return anoFabricacao;
	}

	public void setAnoFabricacao(String anoFabricacao){

		this.anoFabricacao = anoFabricacao;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	public String getDiametroHidrometro(){

		return diametroHidrometro;
	}

	public void setDiametroHidrometro(String diametroHidrometro){

		this.diametroHidrometro = diametroHidrometro;
	}

	public String getMarcaHidrometro(){

		return marcaHidrometro;
	}

	public void setMarcaHidrometro(String marcaHidrometro){

		this.marcaHidrometro = marcaHidrometro;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getTipoHidrometro(){

		return tipoHidrometro;
	}

	public void setTipoHidrometro(String tipoHidrometro){

		this.tipoHidrometro = tipoHidrometro;
	}

	public String getClienteCpfCnpj(){

		return clienteCpfCnpj;
	}

	public void setClienteCpfCnpj(String clienteCpfCnpj){

		this.clienteCpfCnpj = clienteCpfCnpj;
	}

	public String getClienteNome(){

		return clienteNome;
	}

	public void setClienteNome(String clienteNome){

		this.clienteNome = clienteNome;
	}

	public String getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(String ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public String getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(String ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public String getEnderecoFormatado(){

		return enderecoFormatado;
	}

	public void setEnderecoFormatado(String enderecoFormatado){

		this.enderecoFormatado = enderecoFormatado;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getInscricaoTipo(){

		return inscricaoTipo;
	}

	public void setInscricaoTipo(String inscricaoTipo){

		this.inscricaoTipo = inscricaoTipo;
	}

	public String getAnormalidadeLeituraFaturada(){

		return anormalidadeLeituraFaturada;
	}

	public void setAnormalidadeLeituraFaturada(String anormalidadeLeituraFaturada){

		this.anormalidadeLeituraFaturada = anormalidadeLeituraFaturada;
	}

	public String getAnormalidadeLeituraInformada(){

		return anormalidadeLeituraInformada;
	}

	public void setAnormalidadeLeituraInformada(String anormalidadeLeituraInformada){

		this.anormalidadeLeituraInformada = anormalidadeLeituraInformada;
	}

	public String getCategoriaImovel(){

		return categoriaImovel;
	}

	public void setCategoriaImovel(String categoriaImovel){

		this.categoriaImovel = categoriaImovel;
	}

	public String getConsumoFaturamdoMinimo(){

		return consumoFaturamdoMinimo;
	}

	public void setConsumoFaturamdoMinimo(String consumoFaturamdoMinimo){

		this.consumoFaturamdoMinimo = consumoFaturamdoMinimo;
	}

	public String getConsumoMedidoMinimo(){

		return consumoMedidoMinimo;
	}

	public void setConsumoMedidoMinimo(String consumoMedidoMinimo){

		this.consumoMedidoMinimo = consumoMedidoMinimo;
	}

	public String getConsumoMedioMinimo(){

		return consumoMedioMinimo;
	}

	public void setConsumoMedioMinimo(String consumoMedioMinimo){

		this.consumoMedioMinimo = consumoMedioMinimo;
	}

	public String getEmpresaLeitura(){

		return empresaLeitura;
	}

	public void setEmpresaLeitura(String empresaLeitura){

		this.empresaLeitura = empresaLeitura;
	}

	public String getGrupoFaturamento(){

		return grupoFaturamento;
	}

	public void setGrupoFaturamento(String grupoFaturamento){

		this.grupoFaturamento = grupoFaturamento;
	}

	public String getImovel(){

		return imovel;
	}

	public void setImovel(String imovel){

		this.imovel = imovel;
	}

	public String getImovelCondominio(){

		return imovelCondominio;
	}

	public String getIndicadorImovelCondominio(){

		return indicadorImovelCondominio;
	}

	public void setIndicadorImovelCondominio(String indicadorImovelCondominio){

		this.indicadorImovelCondominio = indicadorImovelCondominio;
	}

	public String getPerfilImovel(){

		return perfilImovel;
	}

	public void setPerfilImovel(String perfilImovel){

		this.perfilImovel = perfilImovel;
	}

	public String getQuantidadeEconomia(){

		return quantidadeEconomia;
	}

	public void setQuantidadeEconomia(String quantidadeEconomia){

		this.quantidadeEconomia = quantidadeEconomia;
	}

	public String getTipoAnormalidade(){

		return tipoAnormalidade;
	}

	public void setTipoAnormalidade(String tipoAnormalidade){

		this.tipoAnormalidade = tipoAnormalidade;
	}

	public String getTipoLigacao(){

		return tipoLigacao;
	}

	public void setTipoLigacao(String tipoLigacao){

		this.tipoLigacao = tipoLigacao;
	}

	public String getTipoMedicao(){

		return tipoMedicao;
	}

	public void setTipoMedicao(String tipoMedicao){

		this.tipoMedicao = tipoMedicao;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		return null;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 */
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(String setorComercial){

		this.setorComercial = setorComercial;
	}

	public String getSetorComercialID(){

		return setorComercialID;
	}

	public void setSetorComercialID(String setorComercialID){

		this.setorComercialID = setorComercialID;
	}

	public String getSetorComercialNome(){

		return setorComercialNome;
	}

	public void setSetorComercialNome(String setorComercialNome){

		this.setorComercialNome = setorComercialNome;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getQuadraFinal(){

		return quadraFinal;
	}

	public void setQuadraFinal(String quadraFinal){

		this.quadraFinal = quadraFinal;
	}

	public String getQuadraInicial(){

		return quadraInicial;
	}

	public void setQuadraInicial(String quadraInicial){

		this.quadraInicial = quadraInicial;
	}

	public String getQuadraFinalNome(){

		return quadraFinalNome;
	}

	public void setQuadraFinalNome(String quadraFinalNome){

		this.quadraFinalNome = quadraFinalNome;
	}

	public String getQuadraInicialNome(){

		return quadraInicialNome;
	}

	public void setQuadraInicialNome(String quadraInicialNome){

		this.quadraInicialNome = quadraInicialNome;
	}

	public String getQuadraFinalMensagem(){

		return quadraFinalMensagem;
	}

	public void setQuadraFinalMensagem(String quadraFinalMensagem){

		this.quadraFinalMensagem = quadraFinalMensagem;
	}

	public String getQuadraInicialMensagem(){

		return quadraInicialMensagem;
	}

	public void setQuadraInicialMensagem(String quadraInicialMensagem){

		this.quadraInicialMensagem = quadraInicialMensagem;
	}

	public String getQuadraFinalID(){

		return quadraFinalID;
	}

	public void setQuadraFinalID(String quadraFinalID){

		this.quadraFinalID = quadraFinalID;
	}

	public String getQuadraInicialID(){

		return quadraInicialID;
	}

	public void setQuadraInicialID(String quadraInicialID){

		this.quadraInicialID = quadraInicialID;
	}

	public String getAnormalidadeConsumo(){

		return anormalidadeConsumo;
	}

	public void setAnormalidadeConsumo(String anormalidadeConsumo){

		this.anormalidadeConsumo = anormalidadeConsumo;
	}

	public void setImovelCondominio(String imovelCondominio){

		this.imovelCondominio = imovelCondominio;
	}

	public String getMesAnoFaturamentoCorrente(){

		return mesAnoFaturamentoCorrente;
	}

	public void setMesAnoFaturamentoCorrente(String mesAnoFaturamentoCorrente){

		this.mesAnoFaturamentoCorrente = mesAnoFaturamentoCorrente;
	}

	public String getInstalacaoHidrometro(){

		return instalacaoHidrometro;
	}

	public void setInstalacaoHidrometro(String instalacaoHidrometro){

		this.instalacaoHidrometro = instalacaoHidrometro;
	}

	public String getLocalInstalacaoHidrometro(){

		return localInstalacaoHidrometro;
	}

	public void setLocalInstalacaoHidrometro(String localInstalacaoHidrometro){

		this.localInstalacaoHidrometro = localInstalacaoHidrometro;
	}

	public String getIdTipoMedicao(){

		return idTipoMedicao;
	}

	public void setIdTipoMedicao(String idTipoMedicao){

		this.idTipoMedicao = idTipoMedicao;
	}

	public String getAnormalidadeConsumoFiltro(){

		return anormalidadeConsumoFiltro;
	}

	public void setAnormalidadeConsumoFiltro(String anormalidadeConsumoFiltro){

		this.anormalidadeConsumoFiltro = anormalidadeConsumoFiltro;
	}

	public String getAnormalidadeLeituraFaturadaFiltro(){

		return anormalidadeLeituraFaturadaFiltro;
	}

	public void setAnormalidadeLeituraFaturadaFiltro(String anormalidadeLeituraFaturadaFiltro){

		this.anormalidadeLeituraFaturadaFiltro = anormalidadeLeituraFaturadaFiltro;
	}

	public String getAnormalidadeLeituraInformadaFiltro(){

		return anormalidadeLeituraInformadaFiltro;
	}

	public void setAnormalidadeLeituraInformadaFiltro(String anormalidadeLeituraInformadaFiltro){

		this.anormalidadeLeituraInformadaFiltro = anormalidadeLeituraInformadaFiltro;
	}

	public String getCategoriaImovelFiltro(){

		return categoriaImovelFiltro;
	}

	public void setCategoriaImovelFiltro(String categoriaImovelFiltro){

		this.categoriaImovelFiltro = categoriaImovelFiltro;
	}

	public String getConsumoFaturamdoMinimoFiltro(){

		return consumoFaturamdoMinimoFiltro;
	}

	public void setConsumoFaturamdoMinimoFiltro(String consumoFaturamdoMinimoFiltro){

		this.consumoFaturamdoMinimoFiltro = consumoFaturamdoMinimoFiltro;
	}

	public String getConsumoMedidoMinimoFiltro(){

		return consumoMedidoMinimoFiltro;
	}

	public void setConsumoMedidoMinimoFiltro(String consumoMedidoMinimoFiltro){

		this.consumoMedidoMinimoFiltro = consumoMedidoMinimoFiltro;
	}

	public String getConsumoMedioMinimoFiltro(){

		return consumoMedioMinimoFiltro;
	}

	public void setConsumoMedioMinimoFiltro(String consumoMedioMinimoFiltro){

		this.consumoMedioMinimoFiltro = consumoMedioMinimoFiltro;
	}

	public String getIdEmpresaFiltro(){

		return idEmpresaFiltro;
	}

	public void setIdEmpresaFiltro(String idEmpresaFiltro){

		this.idEmpresaFiltro = idEmpresaFiltro;
	}

	public String getIdGrupoFaturamentoFiltro(){

		return idGrupoFaturamentoFiltro;
	}

	public void setIdGrupoFaturamentoFiltro(String idGrupoFaturamentoFiltro){

		this.idGrupoFaturamentoFiltro = idGrupoFaturamentoFiltro;
	}

	public String getIdTipoMedicaoFiltro(){

		return idTipoMedicaoFiltro;
	}

	public void setIdTipoMedicaoFiltro(String idTipoMedicaoFiltro){

		this.idTipoMedicaoFiltro = idTipoMedicaoFiltro;
	}

	public String getImovelCondominioFiltro(){

		return imovelCondominioFiltro;
	}

	public void setImovelCondominioFiltro(String imovelCondominioFiltro){

		this.imovelCondominioFiltro = imovelCondominioFiltro;
	}

	public String getImovelFiltro(){

		return imovelFiltro;
	}

	public void setImovelFiltro(String imovelFiltro){

		this.imovelFiltro = imovelFiltro;
	}

	public String getIndicadorImovelCondominioFiltro(){

		return indicadorImovelCondominioFiltro;
	}

	public void setIndicadorImovelCondominioFiltro(String indicadorImovelCondominioFiltro){

		this.indicadorImovelCondominioFiltro = indicadorImovelCondominioFiltro;
	}

	public String getLocalidadeFiltro(){

		return localidadeFiltro;
	}

	public void setLocalidadeFiltro(String localidadeFiltro){

		this.localidadeFiltro = localidadeFiltro;
	}

	public String getNomeLocalidadeFiltro(){

		return nomeLocalidadeFiltro;
	}

	public void setNomeLocalidadeFiltro(String nomeLocalidadeFiltro){

		this.nomeLocalidadeFiltro = nomeLocalidadeFiltro;
	}

	public String getPerfilImovelFiltro(){

		return perfilImovelFiltro;
	}

	public void setPerfilImovelFiltro(String perfilImovelFiltro){

		this.perfilImovelFiltro = perfilImovelFiltro;
	}

	public String getQuadraFinalFiltro(){

		return quadraFinalFiltro;
	}

	public void setQuadraFinalFiltro(String quadraFinalFiltro){

		this.quadraFinalFiltro = quadraFinalFiltro;
	}

	public String getQuadraInicialFiltro(){

		return quadraInicialFiltro;
	}

	public void setQuadraInicialFiltro(String quadraInicialFiltro){

		this.quadraInicialFiltro = quadraInicialFiltro;
	}

	public String getRotaInicialFiltro(){

		return rotaInicialFiltro;
	}

	public String getRotaInicial(){

		return rotaInicial;
	}

	public void setRotaInicial(String rotaInicial){

		this.rotaInicial = rotaInicial;
	}

	public String getRotaInicialNome(){

		return rotaInicialNome;
	}

	public void setRotaInicialNome(String rotaInicialNome){

		this.rotaInicialNome = rotaInicialNome;
	}

	public String getRotaInicialID(){

		return rotaInicialID;
	}

	public void setRotaInicialID(String rotaInicialID){

		this.rotaInicialID = rotaInicialID;
	}

	public String getRotaInicialMensagem(){

		return rotaInicialMensagem;
	}

	public void setRotaInicialMensagem(String rotaInicialMensagem){

		this.rotaInicialMensagem = rotaInicialMensagem;
	}

	public String getRotaFinal(){

		return rotaFinal;
	}

	public void setRotaFinal(String rotaFinal){

		this.rotaFinal = rotaFinal;
	}

	public String getRotaFinalNome(){

		return rotaFinalNome;
	}

	public void setRotaFinalNome(String rotaFinalNome){

		this.rotaFinalNome = rotaFinalNome;
	}

	public String getRotaFinalID(){

		return rotaFinalID;
	}

	public void setRotaFinalID(String rotaFinalID){

		this.rotaFinalID = rotaFinalID;
	}

	public String getRotaFinalMensagem(){

		return rotaFinalMensagem;
	}

	public void setRotaFinalMensagem(String rotaFinalMensagem){

		this.rotaFinalMensagem = rotaFinalMensagem;
	}

	public void setRotaInicialFiltro(String rotaInicialFiltro){

		this.rotaInicialFiltro = rotaInicialFiltro;
	}

	public String getRotaFinalFiltro(){

		return rotaFinalFiltro;
	}

	public void setRotaFinalFiltro(String rotaFinalFiltro){

		this.rotaFinalFiltro = rotaFinalFiltro;
	}

	public String getQuantidadeEconomiaFiltro(){

		return quantidadeEconomiaFiltro;
	}

	public void setQuantidadeEconomiaFiltro(String quantidadeEconomiaFiltro){

		this.quantidadeEconomiaFiltro = quantidadeEconomiaFiltro;
	}

	public String getSetorComercialFiltro(){

		return setorComercialFiltro;
	}

	public void setSetorComercialFiltro(String setorComercialFiltro){

		this.setorComercialFiltro = setorComercialFiltro;
	}

	public String getTipoAnormalidadeFiltro(){

		return tipoAnormalidadeFiltro;
	}

	public void setTipoAnormalidadeFiltro(String tipoAnormalidadeFiltro){

		this.tipoAnormalidadeFiltro = tipoAnormalidadeFiltro;
	}

	public String getTipoLigacaoFiltro(){

		return tipoLigacaoFiltro;
	}

	public void setTipoLigacaoFiltro(String tipoLigacaoFiltro){

		this.tipoLigacaoFiltro = tipoLigacaoFiltro;
	}

	public String getTipoMedicaoFiltro(){

		return tipoMedicaoFiltro;
	}

	public void setTipoMedicaoFiltro(String tipoMedicaoFiltro){

		this.tipoMedicaoFiltro = tipoMedicaoFiltro;
	}

	public String getImovelMatriculaCondominioFiltro(){

		return imovelMatriculaCondominioFiltro;
	}

	public void setImovelMatriculaCondominioFiltro(String imovelMatriculaCondominioFiltro){

		this.imovelMatriculaCondominioFiltro = imovelMatriculaCondominioFiltro;
	}

	public String getImovelMatriculaFiltro(){

		return imovelMatriculaFiltro;
	}

	public void setImovelMatriculaFiltro(String imovelMatriculaFiltro){

		this.imovelMatriculaFiltro = imovelMatriculaFiltro;
	}

	public String getDataCorteAgua(){

		return dataCorteAgua;
	}

	public void setDataCorteAgua(String dataCorteAgua){

		this.dataCorteAgua = dataCorteAgua;
	}

	public String getDataLigacaoAgua(){

		return dataLigacaoAgua;
	}

	public void setDataLigacaoAgua(String dataLigacaoAgua){

		this.dataLigacaoAgua = dataLigacaoAgua;
	}

	public String getDataLigacaoEsgoto(){

		return dataLigacaoEsgoto;
	}

	public void setDataLigacaoEsgoto(String dataLigacaoEsgoto){

		this.dataLigacaoEsgoto = dataLigacaoEsgoto;
	}

	public String getDataReligacaoAgua(){

		return dataReligacaoAgua;
	}

	public void setDataReligacaoAgua(String dataReligacaoAgua){

		this.dataReligacaoAgua = dataReligacaoAgua;
	}

	public String getDataSupressaoAgua(){

		return dataSupressaoAgua;
	}

	public void setDataSupressaoAgua(String dataSupressaoAgua){

		this.dataSupressaoAgua = dataSupressaoAgua;
	}

	public String getDescricaoLigacaoAguaDiametro(){

		return descricaoLigacaoAguaDiametro;
	}

	public void setDescricaoLigacaoAguaDiametro(String descricaoLigacaoAguaDiametro){

		this.descricaoLigacaoAguaDiametro = descricaoLigacaoAguaDiametro;
	}

	public String getDescricaoLigacaoAguaMaterial(){

		return descricaoLigacaoAguaMaterial;
	}

	public void setDescricaoLigacaoAguaMaterial(String descricaoLigacaoAguaMaterial){

		this.descricaoLigacaoAguaMaterial = descricaoLigacaoAguaMaterial;
	}

	public String getDescricaoLigacaoEsgotoDiametro(){

		return descricaoLigacaoEsgotoDiametro;
	}

	public void setDescricaoLigacaoEsgotoDiametro(String descricaoLigacaoEsgotoDiametro){

		this.descricaoLigacaoEsgotoDiametro = descricaoLigacaoEsgotoDiametro;
	}

	public String getDescricaoLigacaoEsgotoMaterial(){

		return descricaoLigacaoEsgotoMaterial;
	}

	public void setDescricaoLigacaoEsgotoMaterial(String descricaoLigacaoEsgotoMaterial){

		this.descricaoLigacaoEsgotoMaterial = descricaoLigacaoEsgotoMaterial;
	}

	public String getDescricaoligacaoEsgotoPerfil(){

		return descricaoligacaoEsgotoPerfil;
	}

	public void setDescricaoligacaoEsgotoPerfil(String descricaoligacaoEsgotoPerfil){

		this.descricaoligacaoEsgotoPerfil = descricaoligacaoEsgotoPerfil;
	}

	public String getDescricaoPocoTipo(){

		return descricaoPocoTipo;
	}

	public void setDescricaoPocoTipo(String descricaoPocoTipo){

		this.descricaoPocoTipo = descricaoPocoTipo;
	}

	public String getNumeroConsumominimoAgua(){

		return numeroConsumominimoAgua;
	}

	public void setNumeroConsumominimoAgua(String numeroConsumominimoAgua){

		this.numeroConsumominimoAgua = numeroConsumominimoAgua;
	}

	public String getNumeroConsumominimoEsgoto(){

		return numeroConsumominimoEsgoto;
	}

	public void setNumeroConsumominimoEsgoto(String numeroConsumominimoEsgoto){

		this.numeroConsumominimoEsgoto = numeroConsumominimoEsgoto;
	}

	public String getPercentualAguaConsumidaColetada(){

		return percentualAguaConsumidaColetada;
	}

	public void setPercentualAguaConsumidaColetada(String percentualAguaConsumidaColetada){

		this.percentualAguaConsumidaColetada = percentualAguaConsumidaColetada;
	}

	public String getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(String percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	public String getDataRestabelecimentoAgua(){

		return dataRestabelecimentoAgua;
	}

	public void setDataRestabelecimentoAgua(String dataRestabelecimentoAgua){

		this.dataRestabelecimentoAgua = dataRestabelecimentoAgua;
	}

	public String getDescricaoligacaoAguaPerfil(){

		return descricaoligacaoAguaPerfil;
	}

	public void setDescricaoligacaoAguaPerfil(String descricaoligacaoAguaPerfil){

		this.descricaoligacaoAguaPerfil = descricaoligacaoAguaPerfil;
	}

	public String getConsumoMedioImovel(){

		return consumoMedioImovel;
	}

	public void setConsumoMedioImovel(String consumoMedioImovel){

		this.consumoMedioImovel = consumoMedioImovel;
	}

	public String getIdLigacaoEsgoto(){

		return idLigacaoEsgoto;
	}

	public void setIdLigacaoEsgoto(String idLigacaoEsgoto){

		this.idLigacaoEsgoto = idLigacaoEsgoto;
	}

	public String getConsumoMesEsgoto(){

		return consumoMesEsgoto;
	}

	public void setConsumoMesEsgoto(String consumoMesEsgoto){

		this.consumoMesEsgoto = consumoMesEsgoto;
	}

	public String getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(String idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public String getTipoApresentacao(){

		return tipoApresentacao;
	}

	public void setTipoApresentacao(String tipoApresentacao){

		this.tipoApresentacao = tipoApresentacao;
	}

	public String getDescricaoAnormalidade(){

		return descricaoAnormalidade;
	}

	public void setDescricaoAnormalidade(String descricaoAnormalidade){

		this.descricaoAnormalidade = descricaoAnormalidade;
	}

	public String getIdAnormalidade(){

		return idAnormalidade;
	}

	public void setIdAnormalidade(String idAnormalidade){

		this.idAnormalidade = idAnormalidade;
	}

	public String getConsumoInformado(){

		return consumoInformado;
	}

	public void setConsumoInformado(String consumoInformado){

		this.consumoInformado = consumoInformado;
	}

	public String getLeituraSituacaoAtualFiltro(){

		return leituraSituacaoAtualFiltro;
	}

	public void setLeituraSituacaoAtualFiltro(String leituraSituacaoAtualFiltro){

		this.leituraSituacaoAtualFiltro = leituraSituacaoAtualFiltro;
	}

	public String getRota(){

		return rota;
	}

	public void setRota(String rota){

		this.rota = rota;
	}

	public String getSeqRota(){

		return seqRota;
	}

	public void setSeqRota(String seqRota){

		this.seqRota = seqRota;
	}

	public String[] getIdRegistrosImovel(){

		return idRegistrosImovel;
	}

	public void setIdRegistrosImovel(String[] idRegistrosImovel){

		this.idRegistrosImovel = idRegistrosImovel;
	}

	public String getCreditoFaturado(){

		return creditoFaturado;
	}

	public void setCreditoFaturado(String creditoFaturado){

		this.creditoFaturado = creditoFaturado;
	}

	public String getConsumoAnormalidadeDescricao(){

		return consumoAnormalidadeDescricao;
	}

	public void setConsumoAnormalidadeDescricao(String consumoAnormalidadeDescricao){

		this.consumoAnormalidadeDescricao = consumoAnormalidadeDescricao;
	}

	public String getCaminhoReload(){

		return caminhoReload;
	}

	public void setCaminhoReload(String caminhoReload){

		this.caminhoReload = caminhoReload;
	}

	public String getIndicadorRateio(){

		return indicadorRateio;
	}

	public void setIndicadorRateio(String indicadorRateio){

		this.indicadorRateio = indicadorRateio;
	}

	public String getConsumoFixoPoco(){

		return consumoFixoPoco;
	}

	public void setConsumoFixoPoco(String consumoFixoPoco){

		this.consumoFixoPoco = consumoFixoPoco;
	}

	public String getCreditoGerado(){

		return creditoGerado;
	}

	public void setCreditoGerado(String creditoGerado){

		this.creditoGerado = creditoGerado;
	}

}
