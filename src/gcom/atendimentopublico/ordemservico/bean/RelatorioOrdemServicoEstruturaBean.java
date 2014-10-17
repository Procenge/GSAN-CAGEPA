
package gcom.atendimentopublico.ordemservico.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelatorioOrdemServicoEstruturaBean
				implements Serializable {

	// artificio tecnico de caráter emergencial, retirar depois - OC1252308

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

	private String tipoHidrometro;

	private String numeroHidrometro;

	private String marcaHidrometro;

	private String dataInstalacaoHidrometro;

	private String localInstalacaoHidrometro;

	private String capacidadeHidrometro;

	private String tipoProtecaoHidrometro;

	private String diametroHidrometro;

	// Dados do Corte

	private String numeroSeloCorte;

	private String numeroLeituraCorte;

	private String idDescricaoCorteTipo;

	// Dados Ligação

	private String qtdEconomiasComercial;

	private String qtdEconomiasPublica;

	private String qtdEconomiasIndustrial;

	private String reservatorio;

	private String piscina;

	private String fonteAbastecimento;

	private String qtdOcupantes;

	private String qtdPontosUtilizados;

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

	public String getNumeroPagina1(){

		return numeroPagina1;
	}

	public void setNumeroPagina1(String numeroPagina1){

		this.numeroPagina1 = numeroPagina1;
	}

	public String getNumeroPagina2(){

		return numeroPagina2;
	}

	public void setNumeroPagina2(String numeroPagina2){

		this.numeroPagina2 = numeroPagina2;
	}

	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		this.numeroOS = numeroOS;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
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

	public String getTipoLigacao2(){

		return tipoLigacao2;
	}

	public void setTipoLigacao2(String tipoLigacao2){

		this.tipoLigacao2 = tipoLigacao2;
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

	public String getTipoRelacao(){

		return tipoRelacao;
	}

	public void setTipoRelacao(String tipoRelacao){

		this.tipoRelacao = tipoRelacao;
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

	public String getTipoHidrometro(){

		return tipoHidrometro;
	}

	public void setTipoHidrometro(String tipoHidrometro){

		this.tipoHidrometro = tipoHidrometro;
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

	public String getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	public void setDataInstalacaoHidrometro(String dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	public String getLocalInstalacaoHidrometro(){

		return localInstalacaoHidrometro;
	}

	public void setLocalInstalacaoHidrometro(String localInstalacaoHidrometro){

		this.localInstalacaoHidrometro = localInstalacaoHidrometro;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	public String getTipoProtecaoHidrometro(){

		return tipoProtecaoHidrometro;
	}

	public void setTipoProtecaoHidrometro(String tipoProtecaoHidrometro){

		this.tipoProtecaoHidrometro = tipoProtecaoHidrometro;
	}

	public String getDiametroHidrometro(){

		return diametroHidrometro;
	}

	public void setDiametroHidrometro(String diametroHidrometro){

		this.diametroHidrometro = diametroHidrometro;
	}

	public String getNumeroSeloCorte(){

		return numeroSeloCorte;
	}

	public void setNumeroSeloCorte(String numeroSeloCorte){

		this.numeroSeloCorte = numeroSeloCorte;
	}

	public String getNumeroLeituraCorte(){

		return numeroLeituraCorte;
	}

	public void setNumeroLeituraCorte(String numeroLeituraCorte){

		this.numeroLeituraCorte = numeroLeituraCorte;
	}

	public String getIdDescricaoCorteTipo(){

		return idDescricaoCorteTipo;
	}

	public void setIdDescricaoCorteTipo(String idDescricaoCorteTipo){

		this.idDescricaoCorteTipo = idDescricaoCorteTipo;
	}

	public String getQtdEconomiasComercial(){

		return qtdEconomiasComercial;
	}

	public void setQtdEconomiasComercial(String qtdEconomiasComercial){

		this.qtdEconomiasComercial = qtdEconomiasComercial;
	}

	public String getQtdEconomiasPublica(){

		return qtdEconomiasPublica;
	}

	public void setQtdEconomiasPublica(String qtdEconomiasPublica){

		this.qtdEconomiasPublica = qtdEconomiasPublica;
	}

	public String getQtdEconomiasIndustrial(){

		return qtdEconomiasIndustrial;
	}

	public void setQtdEconomiasIndustrial(String qtdEconomiasIndustrial){

		this.qtdEconomiasIndustrial = qtdEconomiasIndustrial;
	}

	public String getReservatorio(){

		return reservatorio;
	}

	public void setReservatorio(String reservatorio){

		this.reservatorio = reservatorio;
	}

	public String getPiscina(){

		return piscina;
	}

	public void setPiscina(String piscina){

		this.piscina = piscina;
	}

	public String getFonteAbastecimento(){

		return fonteAbastecimento;
	}

	public void setFonteAbastecimento(String fonteAbastecimento){

		this.fonteAbastecimento = fonteAbastecimento;
	}

	public String getQtdOcupantes(){

		return qtdOcupantes;
	}

	public void setQtdOcupantes(String qtdOcupantes){

		this.qtdOcupantes = qtdOcupantes;
	}

	public String getQtdPontosUtilizados(){

		return qtdPontosUtilizados;
	}

	public void setQtdPontosUtilizados(String qtdPontosUtilizados){

		this.qtdPontosUtilizados = qtdPontosUtilizados;
	}

	public String getImagem(){

		return imagem;
	}

	public void setImagem(String imagem){

		this.imagem = imagem;
	}

	public String getEquipe(){

		return equipe;
	}

	public void setEquipe(String equipe){

		this.equipe = equipe;
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

	public String getCategoria2(){

		return categoria2;
	}

	public void setCategoria2(String categoria2){

		this.categoria2 = categoria2;
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

	public String getAgenteAssociado(){

		return agenteAssociado;
	}

	public void setAgenteAssociado(String agenteAssociado){

		this.agenteAssociado = agenteAssociado;
	}

	public String getQtdEconomiasResidencial(){

		return qtdEconomiasResidencial;
	}

	public void setQtdEconomiasResidencial(String qtdEconomiasResidencial){

		this.qtdEconomiasResidencial = qtdEconomiasResidencial;
	}

	public String getQuantidadeFaturasDebito(){

		return quantidadeFaturasDebito;
	}

	public void setQuantidadeFaturasDebito(String quantidadeFaturasDebito){

		this.quantidadeFaturasDebito = quantidadeFaturasDebito;
	}

	public String getDebitosValorTotal(){

		return debitosValorTotal;
	}

	public void setDebitosValorTotal(String debitosValorTotal){

		this.debitosValorTotal = debitosValorTotal;
	}

	public String getInscricao1(){

		return inscricao1;
	}

	public void setInscricao1(String inscricao1){

		this.inscricao1 = inscricao1;
	}

	public String getMatricula1(){

		return matricula1;
	}

	public void setMatricula1(String matricula1){

		this.matricula1 = matricula1;
	}

	public String getRoteiro1(){

		return roteiro1;
	}

	public void setRoteiro1(String roteiro1){

		this.roteiro1 = roteiro1;
	}

	public String getHm1(){

		return hm1;
	}

	public void setHm1(String hm1){

		this.hm1 = hm1;
	}

	public String getPrograma1(){

		return programa1;
	}

	public void setPrograma1(String programa1){

		this.programa1 = programa1;
	}

	public String getCliente1(){

		return cliente1;
	}

	public void setCliente1(String cliente1){

		this.cliente1 = cliente1;
	}

	public String getEndereco1(){

		return endereco1;
	}

	public void setEndereco1(String endereco1){

		this.endereco1 = endereco1;
	}

	public String getBairro1(){

		return bairro1;
	}

	public void setBairro1(String bairro1){

		this.bairro1 = bairro1;
	}

	public String getNumero1(){

		return numero1;
	}

	public void setNumero1(String numero1){

		this.numero1 = numero1;
	}

	public String getDataComparecimento1(){

		return dataComparecimento1;
	}

	public void setDataComparecimento1(String dataComparecimento1){

		this.dataComparecimento1 = dataComparecimento1;
	}

	public String getDataDebitoAnterior1(){

		return dataDebitoAnterior1;
	}

	public void setDataDebitoAnterior1(String dataDebitoAnterior1){

		this.dataDebitoAnterior1 = dataDebitoAnterior1;
	}

	public String getDebitosAnteriores1(){

		return debitosAnteriores1;
	}

	public void setDebitosAnteriores1(String debitosAnteriores1){

		this.debitosAnteriores1 = debitosAnteriores1;
	}

	public String getValorTotalDebito1(){

		return valorTotalDebito1;
	}

	public void setValorTotalDebito1(String valorTotalDebito1){

		this.valorTotalDebito1 = valorTotalDebito1;
	}

	public String getRepresentacaoNumericaCodBarraFormatada1(){

		return representacaoNumericaCodBarraFormatada1;
	}

	public void setRepresentacaoNumericaCodBarraFormatada1(String representacaoNumericaCodBarraFormatada1){

		this.representacaoNumericaCodBarraFormatada1 = representacaoNumericaCodBarraFormatada1;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito1(){

		return representacaoNumericaCodBarraSemDigito1;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito1(String representacaoNumericaCodBarraSemDigito1){

		this.representacaoNumericaCodBarraSemDigito1 = representacaoNumericaCodBarraSemDigito1;
	}

	public String getDataPermanenciaRegistro1(){

		return dataPermanenciaRegistro1;
	}

	public void setDataPermanenciaRegistro1(String dataPermanenciaRegistro1){

		this.dataPermanenciaRegistro1 = dataPermanenciaRegistro1;
	}

	public String getTipoRelacao1(){

		return tipoRelacao1;
	}

	public void setTipoRelacao1(String tipoRelacao1){

		this.tipoRelacao1 = tipoRelacao1;
	}

	public String getInscricao2(){

		return inscricao2;
	}

	public void setInscricao2(String inscricao2){

		this.inscricao2 = inscricao2;
	}

	public String getMatricula2(){

		return matricula2;
	}

	public void setMatricula2(String matricula2){

		this.matricula2 = matricula2;
	}

	public String getRoteiro2(){

		return roteiro2;
	}

	public void setRoteiro2(String roteiro2){

		this.roteiro2 = roteiro2;
	}

	public String getHm2(){

		return hm2;
	}

	public void setHm2(String hm2){

		this.hm2 = hm2;
	}

	public String getPrograma2(){

		return programa2;
	}

	public void setPrograma2(String programa2){

		this.programa2 = programa2;
	}

	public String getCliente2(){

		return cliente2;
	}

	public void setCliente2(String cliente2){

		this.cliente2 = cliente2;
	}

	public String getEndereco2(){

		return endereco2;
	}

	public void setEndereco2(String endereco2){

		this.endereco2 = endereco2;
	}

	public String getBairro2(){

		return bairro2;
	}

	public void setBairro2(String bairro2){

		this.bairro2 = bairro2;
	}

	public String getNumero2(){

		return numero2;
	}

	public void setNumero2(String numero2){

		this.numero2 = numero2;
	}

	public String getDataComparecimento2(){

		return dataComparecimento2;
	}

	public void setDataComparecimento2(String dataComparecimento2){

		this.dataComparecimento2 = dataComparecimento2;
	}

	public String getDataDebitoAnterior2(){

		return dataDebitoAnterior2;
	}

	public void setDataDebitoAnterior2(String dataDebitoAnterior2){

		this.dataDebitoAnterior2 = dataDebitoAnterior2;
	}

	public String getDebitosAnteriores2(){

		return debitosAnteriores2;
	}

	public void setDebitosAnteriores2(String debitosAnteriores2){

		this.debitosAnteriores2 = debitosAnteriores2;
	}

	public String getValorTotalDebito2(){

		return valorTotalDebito2;
	}

	public void setValorTotalDebito2(String valorTotalDebito2){

		this.valorTotalDebito2 = valorTotalDebito2;
	}

	public String getRepresentacaoNumericaCodBarraFormatada2(){

		return representacaoNumericaCodBarraFormatada2;
	}

	public void setRepresentacaoNumericaCodBarraFormatada2(String representacaoNumericaCodBarraFormatada2){

		this.representacaoNumericaCodBarraFormatada2 = representacaoNumericaCodBarraFormatada2;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito2(){

		return representacaoNumericaCodBarraSemDigito2;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito2(String representacaoNumericaCodBarraSemDigito2){

		this.representacaoNumericaCodBarraSemDigito2 = representacaoNumericaCodBarraSemDigito2;
	}

	public String getDataPermanenciaRegistro2(){

		return dataPermanenciaRegistro2;
	}

	public void setDataPermanenciaRegistro2(String dataPermanenciaRegistro2){

		this.dataPermanenciaRegistro2 = dataPermanenciaRegistro2;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public String getAcaoCobranca(){

		return acaoCobranca;
	}

	public void setAcaoCobranca(String acaoCobranca){

		this.acaoCobranca = acaoCobranca;
	}

	public String getRoteiro(){

		return roteiro;
	}

	public void setRoteiro(String roteiro){

		this.roteiro = roteiro;
	}

	public String getHoraImpressao(){

		return horaImpressao;
	}

	public void setHoraImpressao(String horaImpressao){

		this.horaImpressao = horaImpressao;
	}

	public String getDataImpressao(){

		return dataImpressao;
	}

	public void setDataImpressao(String dataImpressao){

		this.dataImpressao = dataImpressao;
	}

	public String getDataComparecimento(){

		return dataComparecimento;
	}

	public void setDataComparecimento(String dataComparecimento){

		this.dataComparecimento = dataComparecimento;
	}

	public String getDataDebitoAnterior(){

		return dataDebitoAnterior;
	}

	public void setDataDebitoAnterior(String dataDebitoAnterior){

		this.dataDebitoAnterior = dataDebitoAnterior;
	}

	public BigDecimal getValorDebitosAnteriores(){

		return valorDebitosAnteriores;
	}

	public void setValorDebitosAnteriores(BigDecimal valorDebitosAnteriores){

		this.valorDebitosAnteriores = valorDebitosAnteriores;
	}

	public BigDecimal getValorTotal(){

		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal){

		this.valorTotal = valorTotal;
	}

	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getRa1(){

		return ra1;
	}

	public void setRa1(String ra1){

		this.ra1 = ra1;
	}

	public String getCodDescricaoServico1(){

		return codDescricaoServico1;
	}

	public void setCodDescricaoServico1(String codDescricaoServico1){

		this.codDescricaoServico1 = codDescricaoServico1;
	}

	public String getRa2(){

		return ra2;
	}

	public void setRa2(String ra2){

		this.ra2 = ra2;
	}

	public String getCodDescricaoServico2(){

		return codDescricaoServico2;
	}

	public void setCodDescricaoServico2(String codDescricaoServico2){

		this.codDescricaoServico2 = codDescricaoServico2;
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

	public String getTipoRelacao2(){

		return tipoRelacao2;
	}

	public void setTipoRelacao2(String tipoRelacao2){

		this.tipoRelacao2 = tipoRelacao2;
	}

	public String getDataPermanenciaRegistro(){

		return dataPermanenciaRegistro;
	}

	public void setDataPermanenciaRegistro(String dataPermanenciaRegistro){

		this.dataPermanenciaRegistro = dataPermanenciaRegistro;
	}

	public String getValorTotalDebito(){

		return valorTotalDebito;
	}

	public void setValorTotalDebito(String valorTotalDebito){

		this.valorTotalDebito = valorTotalDebito;
	}

	public String getImagemFaltaAgua(){

		return imagemFaltaAgua;
	}

	public void setImagemFaltaAgua(String imagemFaltaAgua){

		this.imagemFaltaAgua = imagemFaltaAgua;
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

	public String getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(String ordemServico){

		this.ordemServico = ordemServico;
	}

	public Integer getCobrancaSituacao(){

		return cobrancaSituacao;
	}

	public void setCobrancaSituacao(Integer cobrancaSituacao){

		this.cobrancaSituacao = cobrancaSituacao;
	}

	public String getLocal(){

		return local;
	}

	public void setLocal(String local){

		this.local = local;
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

}
