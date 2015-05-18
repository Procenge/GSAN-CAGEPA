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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.ImovelEmissaoOrdensSeletivaHelper;
import gcom.util.IoUtil;

import java.sql.Blob;
import java.util.Collection;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;
import org.hibernate.Hibernate;

/**
 * Description of the Class
 * 
 * @author Ivan Sérgio
 * @created 29/10/2007
 */
public class ImovelEmissaoOrdensSeletivasActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	// public static final String TIPO_ORDEM_INSTALACAO = "INSTALACAO";
	// public static final String TIPO_ORDEM_SUBSTITUICAO = "SUBSTITUICAO";

	// Parametros
	private String simulacao;

	private String firma;

	private String nomeFirma;

	private String quantidadeMaxima;

	private String elo;

	private String nomeElo;

	private String inscricaoTipo;

	private String servicoTipo;

	private String servicoTipoDescricao;

	private String faturamentoGrupo;

	private String gerenciaRegional;

	private String[] localidade;

	private String[] bairro;

	private String[] logradouro;

	private String[] setor;

	private String[] rota;

	private String[] quadra;

	private String[] lote;

	// Caracteristicas
	private String[] perfilImovel;

	private String perfilImovelDescricao;

	private String[] categoria;

	private String categoriaDescricao;

	private String[] subCategoria;

	private String subCategoriaDescricao;

	private String intervaloQuantidadeEconomiasInicial;

	private String intervaloQuantidadeEconomiasFinal;

	private String intervaloQuantidadeDocumentosInicial;

	private String intervaloQuantidadeDocumentosFinal;

	private String intervaloNumeroMoradoresInicial;

	private String intervaloNumeroMoradoresFinal;

	private String intervaloAreaConstruidaInicial;

	private String intervaloAreaConstruidaFinal;

	private String intervaloAreaConstruidaPredefinida;

	private String imovelCondominio;

	private String mediaImovel;

	private String consumoPorEconomia;

	private String tipoMedicao;

	private String tipoMedicaoDescricao;

	private String[] ligacaoAguaSituacao;

	private String[] ligacaoEsgotoSituacao;

	private String intervaloDataCorteInicial;

	private String intervaloDataCorteFinal;

	private String dataCorteInicial;

	private String dataCorteFinal;

	private String intervaloNumeroConsumoMesInicial;

	private String intervaloNumeroConsumoMesFinal;

	private String intervaloQuantidadeContasVencidasInicial;

	private String intervaloQuantidadeContasVencidasFinal;

	private String valorTotalDebitoVencido;

	private String intervaloNumeroPontosUtilizacaoInicial;

	private String intervaloNumeroPontosUtilizacaoFinal;

	private Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel;

	// Hidrometro
	private String capacidade;

	private String capacidadeDescricao;

	private String[] marca;

	private String marcaDescricao;

	private String[] hidrometroClasseMetrologica;

	private String[] hidrometroProtecao;

	private String[] hidrometroLocalInstalacao;

	private String[] anormalidadeHidrometro;

	private String numeroOcorrenciasConsecutivas;

	private String mesAnoInstalacao;

	private FormFile arquivoMatricula;

	private String arquivoDownload;

	private Collection<Integer> idsImoveis;

	private String enderecoArquivoDownload;

	private Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro;

	private String arquivoCarregado;

	private String titulo;

	private byte[] arquivo;

	private Blob ArquivoBlob;

	private String idComandoOsServicoSeletiva;

	private String referenciaUltimaAfericaoAnterior;

	public FormFile getArquivoMatricula(){

		return arquivoMatricula;
	}

	public byte[] getArquivo(){

		return this.arquivo;
	}

	public void setArquivo(byte[] arquivo){

		this.arquivo = arquivo;
		this.popularArquivoBlob();
	}

	public Blob getArquivoBlob(){

		if(this.arquivo != null){
			return Hibernate.createBlob(this.arquivo);
		}else{
			return null;
		}
	}

	public void setArquivoBlob(Blob ArquivoBlob){

		this.arquivo = IoUtil.toByteArray(ArquivoBlob);
	}

	private void popularArquivoBlob(){

		this.ArquivoBlob = (this.getArquivoBlob());
	}

	public void setArquivoMatricula(FormFile arquivoMatricula){

		this.arquivoMatricula = arquivoMatricula;
	}

	public String[] getAnormalidadeHidrometro(){

		return anormalidadeHidrometro;
	}

	public void setAnormalidadeHidrometro(String[] anormalidadeHidrometro){

		this.anormalidadeHidrometro = anormalidadeHidrometro;
	}

	public String getCapacidade(){

		return capacidade;
	}

	public void setCapacidade(String capacidade){

		this.capacidade = capacidade;
	}

	public String[] getCategoria(){

		return categoria;
	}

	public void setCategoria(String[] categoria){

		this.categoria = categoria;
	}

	public String getConsumoPorEconomia(){

		return consumoPorEconomia;
	}

	public void setConsumoPorEconomia(String consumoPorEconomia){

		this.consumoPorEconomia = consumoPorEconomia;
	}

	public String getElo(){

		return elo;
	}

	public void setElo(String elo){

		this.elo = elo;
	}

	public String getFirma(){

		return firma;
	}

	public void setFirma(String firma){

		this.firma = firma;
	}

	public String getImovelCondominio(){

		return imovelCondominio;
	}

	public void setImovelCondominio(String imovelCondominio){

		this.imovelCondominio = imovelCondominio;
	}

	public String getIntervaloAreaConstruidaFinal(){

		return intervaloAreaConstruidaFinal;
	}

	public void setIntervaloAreaConstruidaFinal(String intervaloAreaConstruidaFinal){

		this.intervaloAreaConstruidaFinal = intervaloAreaConstruidaFinal;
	}

	public String getIntervaloAreaConstruidaInicial(){

		return intervaloAreaConstruidaInicial;
	}

	public void setIntervaloAreaConstruidaInicial(String intervaloAreaConstruidaInicial){

		this.intervaloAreaConstruidaInicial = intervaloAreaConstruidaInicial;
	}

	public String getIntervaloAreaConstruidaPredefinida(){

		return intervaloAreaConstruidaPredefinida;
	}

	public void setIntervaloAreaConstruidaPredefinida(String intervaloAreaConstruidaPredefinida){

		this.intervaloAreaConstruidaPredefinida = intervaloAreaConstruidaPredefinida;
	}

	public String getIntervaloNumeroMoradoresFinal(){

		return intervaloNumeroMoradoresFinal;
	}

	public void setIntervaloNumeroMoradoresFinal(String intervaloNumeroMoradoresFinal){

		this.intervaloNumeroMoradoresFinal = intervaloNumeroMoradoresFinal;
	}

	public String getIntervaloNumeroMoradoresInicial(){

		return intervaloNumeroMoradoresInicial;
	}

	public void setIntervaloNumeroMoradoresInicial(String intervaloNumeroMoradoresInicial){

		this.intervaloNumeroMoradoresInicial = intervaloNumeroMoradoresInicial;
	}

	public String getIntervaloQuantidadeDocumentosInicial(){

		return intervaloQuantidadeDocumentosInicial;
	}

	public void setIntervaloQuantidadeDocumentosInicial(String intervaloQuantidadeDocumentosInicial){

		this.intervaloQuantidadeDocumentosInicial = intervaloQuantidadeDocumentosInicial;
	}

	public String getIntervaloQuantidadeDocumentosFinal(){

		return intervaloQuantidadeDocumentosFinal;
	}

	public void setIntervaloQuantidadeDocumentosFinal(String intervaloQuantidadeDocumentosFinal){

		this.intervaloQuantidadeDocumentosFinal = intervaloQuantidadeDocumentosFinal;
	}

	public String getIntervaloQuantidadeEconomiasFinal(){

		return intervaloQuantidadeEconomiasFinal;
	}

	public void setIntervaloQuantidadeEconomiasFinal(String intervaloQuantidadeEconomiasFinal){

		this.intervaloQuantidadeEconomiasFinal = intervaloQuantidadeEconomiasFinal;
	}

	public String getIntervaloQuantidadeEconomiasInicial(){

		return intervaloQuantidadeEconomiasInicial;
	}

	public void setIntervaloQuantidadeEconomiasInicial(String intervaloQuantidadeEconomiasInicial){

		this.intervaloQuantidadeEconomiasInicial = intervaloQuantidadeEconomiasInicial;
	}

	public String[] getMarca(){

		return marca;
	}

	public void setMarca(String[] marca){

		this.marca = marca;
	}

	public String getMediaImovel(){

		return mediaImovel;
	}

	public void setMediaImovel(String mediaImovel){

		this.mediaImovel = mediaImovel;
	}

	public String getMesAnoInstalacao(){

		return mesAnoInstalacao;
	}

	public void setMesAnoInstalacao(String mesAnoInstalacao){

		this.mesAnoInstalacao = mesAnoInstalacao;
	}

	public String getNumeroOcorrenciasConsecutivas(){

		return numeroOcorrenciasConsecutivas;
	}

	public void setNumeroOcorrenciasConsecutivas(String numeroOcorrenciasConsecutivas){

		this.numeroOcorrenciasConsecutivas = numeroOcorrenciasConsecutivas;
	}

	public String[] getPerfilImovel(){

		return perfilImovel;
	}

	public void setPerfilImovel(String[] perfilImovel){

		this.perfilImovel = perfilImovel;
	}

	public String getQuantidadeMaxima(){

		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(String quantidadeMaxima){

		this.quantidadeMaxima = quantidadeMaxima;
	}

	public String[] getSubCategoria(){

		return subCategoria;
	}

	public void setSubCategoria(String[] subCategoria){

		this.subCategoria = subCategoria;
	}

	public String getSimulacao(){

		return simulacao;
	}

	public void setSimulacao(String simulacao){

		this.simulacao = simulacao;
	}

	public String getTipoMedicao(){

		return tipoMedicao;
	}

	public void setTipoMedicao(String tipoMedicao){

		this.tipoMedicao = tipoMedicao;
	}

	public String getInscricaoTipo(){

		return inscricaoTipo;
	}

	public void setInscricaoTipo(String inscricaoTipo){

		this.inscricaoTipo = inscricaoTipo;
	}

	public String getNomeElo(){

		return nomeElo;
	}

	public void setNomeElo(String nomeElo){

		this.nomeElo = nomeElo;
	}

	public void limparCamposHidrometro(){

		this.capacidade = null;
		this.marca = null;
		this.anormalidadeHidrometro = null;
		this.numeroOcorrenciasConsecutivas = null;
		this.mesAnoInstalacao = null;
		this.hidrometroClasseMetrologica = null;
		this.hidrometroProtecao = null;
		this.hidrometroLocalInstalacao = null;
		this.colecaoDadosDoHidrometro = null;
	}

	public void limparCombos(){

		this.bairro = null;
		this.rota = null;
		this.quadra = null;
		this.logradouro = null;
		this.setor = null;
		this.lote = null;
	}

	public String getNomeFirma(){

		return nomeFirma;
	}

	public void setNomeFirma(String nomeFirma){

		this.nomeFirma = nomeFirma;
	}

	public String getCapacidadeDescricao(){

		return capacidadeDescricao;
	}

	public void setCapacidadeDescricao(String capacidadeDescricao){

		this.capacidadeDescricao = capacidadeDescricao;
	}

	public String getCategoriaDescricao(){

		return categoriaDescricao;
	}

	public void setCategoriaDescricao(String categoriaDescricao){

		this.categoriaDescricao = categoriaDescricao;
	}

	public String getMarcaDescricao(){

		return marcaDescricao;
	}

	public void setMarcaDescricao(String marcaDescricao){

		this.marcaDescricao = marcaDescricao;
	}

	public String getPerfilImovelDescricao(){

		return perfilImovelDescricao;
	}

	public void setPerfilImovelDescricao(String perfilImovelDescricao){

		this.perfilImovelDescricao = perfilImovelDescricao;
	}

	public String getSubCategoriaDescricao(){

		return subCategoriaDescricao;
	}

	public void setSubCategoriaDescricao(String subCategoriaDescricao){

		this.subCategoriaDescricao = subCategoriaDescricao;
	}

	public String getTipoMedicaoDescricao(){

		return tipoMedicaoDescricao;
	}

	public void setTipoMedicaoDescricao(String tipoMedicaoDescricao){

		this.tipoMedicaoDescricao = tipoMedicaoDescricao;
	}

	public String getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(String servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String[] getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String[] localidade){

		this.localidade = localidade;
	}

	public String[] getSetor(){

		return setor;
	}

	public void setSetor(String[] setor){

		this.setor = setor;
		this.bairro = new String[] {"-1"};
		this.logradouro = new String[] {"-1"};
		if(setor.length > 1){
			this.quadra = new String[] {"-1"};
			this.rota = new String[] {"-1"};
			this.lote = new String[] {"-1"};
		}
	}

	public String[] getQuadra(){

		return quadra;
	}

	public void setQuadra(String[] quadra){

		this.quadra = quadra;
		this.rota = new String[] {"-1"};

		if(quadra.length > 1){
			this.lote = new String[] {"-1"};
		}
	}

	public void setRota(String[] rota){

		this.rota = rota;
		this.quadra = new String[] {"-1"};
		this.lote = new String[] {"-1"};
	}

	public void setBairro(String[] bairro){

		this.bairro = bairro;
		this.logradouro = new String[] {"-1"};
		this.setor = new String[] {"-1"};
		this.quadra = new String[] {"-1"};
		this.lote = new String[] {"-1"};
		this.rota = new String[] {"-1"};
	}

	public void setLogradouro(String[] logradouro){

		this.logradouro = logradouro;
	}

	public String[] getRota(){

		return rota;
	}

	public String[] getBairro(){

		return bairro;
	}

	public String[] getLogradouro(){

		return logradouro;
	}

	public String getServicoTipoDescricao(){

		return servicoTipoDescricao;
	}

	public void setServicoTipoDescricao(String servicoTipoDescricao){

		this.servicoTipoDescricao = servicoTipoDescricao;
	}

	public void setArquivoDownload(String arquivoDownload){

		this.arquivoDownload = arquivoDownload;
	}

	public String getArquivoDownload(){

		return arquivoDownload;
	}

	public void setIdsImoveis(Collection<Integer> idsImoveis){

		this.idsImoveis = idsImoveis;
	}

	public Collection<Integer> getIdsImoveis(){

		return idsImoveis;
	}

	public void setEnderecoArquivoDownload(String enderecoArquivoDownload){

		this.enderecoArquivoDownload = enderecoArquivoDownload;
	}

	public String getEnderecoArquivoDownload(){

		return enderecoArquivoDownload;
	}

	public String getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(String faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public String getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(String gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public String[] getLote(){

		return lote;
	}

	public void setLote(String[] lote){

		this.lote = lote;
	}

	public String[] getHidrometroClasseMetrologica(){

		return hidrometroClasseMetrologica;
	}

	public void setHidrometroClasseMetrologica(String[] hidrometroClasseMetrologica){

		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
	}

	public String[] getHidrometroProtecao(){

		return hidrometroProtecao;
	}

	public void setHidrometroProtecao(String[] hidrometroProtecao){

		this.hidrometroProtecao = hidrometroProtecao;
	}

	public String[] getHidrometroLocalInstalacao(){

		return hidrometroLocalInstalacao;
	}

	public void setHidrometroLocalInstalacao(String[] hidrometroLocalInstalacao){

		this.hidrometroLocalInstalacao = hidrometroLocalInstalacao;
	}

	public String[] getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(String[] ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public String[] getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(String[] ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public String getIntervaloDataCorteInicial(){

		return intervaloDataCorteInicial;
	}

	public void setIntervaloDataCorteInicial(String intervaloDataCorteInicial){

		this.intervaloDataCorteInicial = intervaloDataCorteInicial;
	}

	public String getIntervaloDataCorteFinal(){

		return intervaloDataCorteFinal;
	}

	public void setIntervaloDataCorteFinal(String intervaloDataCorteFinal){

		this.intervaloDataCorteFinal = intervaloDataCorteFinal;
	}

	public String getDataCorteInicial(){

		return dataCorteInicial;
	}

	public void setDataCorteInicial(String dataCorteInicial){

		this.dataCorteInicial = dataCorteInicial;
	}

	public String getDataCorteFinal(){

		return dataCorteFinal;
	}

	public void setDataCorteFinal(String dataCorteFinal){

		this.dataCorteFinal = dataCorteFinal;
	}

	public String getIntervaloNumeroConsumoMesInicial(){

		return intervaloNumeroConsumoMesInicial;
	}

	public void setIntervaloNumeroConsumoMesInicial(String intervaloNumeroConsumoMesInicial){

		this.intervaloNumeroConsumoMesInicial = intervaloNumeroConsumoMesInicial;
	}

	public String getIntervaloNumeroConsumoMesFinal(){

		return intervaloNumeroConsumoMesFinal;
	}

	public void setIntervaloNumeroConsumoMesFinal(String intervaloNumeroConsumoMesFinal){

		this.intervaloNumeroConsumoMesFinal = intervaloNumeroConsumoMesFinal;
	}

	public String getIntervaloQuantidadeContasVencidasInicial(){

		return intervaloQuantidadeContasVencidasInicial;
	}

	public void setIntervaloQuantidadeContasVencidasInicial(String intervaloQuantidadeContasVencidasInicial){

		this.intervaloQuantidadeContasVencidasInicial = intervaloQuantidadeContasVencidasInicial;
	}

	public String getIntervaloQuantidadeContasVencidasFinal(){

		return intervaloQuantidadeContasVencidasFinal;
	}

	public void setIntervaloQuantidadeContasVencidasFinal(String intervaloQuantidadeContasVencidasFinal){

		this.intervaloQuantidadeContasVencidasFinal = intervaloQuantidadeContasVencidasFinal;
	}

	public String getValorTotalDebitoVencido(){

		return valorTotalDebitoVencido;
	}

	public void setValorTotalDebitoVencido(String valorTotalDebitoVencido){

		this.valorTotalDebitoVencido = valorTotalDebitoVencido;
	}

	public String getIntervaloNumeroPontosUtilizacaoInicial(){

		return intervaloNumeroPontosUtilizacaoInicial;
	}

	public void setIntervaloNumeroPontosUtilizacaoInicial(String intervaloNumeroPontosUtilizacaoInicial){

		this.intervaloNumeroPontosUtilizacaoInicial = intervaloNumeroPontosUtilizacaoInicial;
	}

	public String getIntervaloNumeroPontosUtilizacaoFinal(){

		return intervaloNumeroPontosUtilizacaoFinal;
	}

	public void setIntervaloNumeroPontosUtilizacaoFinal(String intervaloNumeroPontosUtilizacaoFinal){

		this.intervaloNumeroPontosUtilizacaoFinal = intervaloNumeroPontosUtilizacaoFinal;
	}

	public Collection<ConsumoMedioImovelHelper> getColecaoConsumoMedioImovel(){

		return colecaoConsumoMedioImovel;
	}

	public void setColecaoConsumoMedioImovel(Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel){

		this.colecaoConsumoMedioImovel = colecaoConsumoMedioImovel;
	}

	public Collection<DadosDoHidrometroHelper> getColecaoDadosDoHidrometro(){

		return colecaoDadosDoHidrometro;
	}

	public void setColecaoDadosDoHidrometro(Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro){

		this.colecaoDadosDoHidrometro = colecaoDadosDoHidrometro;
	}

	public String getArquivoCarregado(){

		return arquivoCarregado;
	}

	public void setArquivoCarregado(String arquivoCarregado){

		this.arquivoCarregado = arquivoCarregado;
	}

	public String getTitulo(){

		return titulo;
	}

	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	public String getIdComandoOsServicoSeletiva(){

		return idComandoOsServicoSeletiva;
	}

	public void setIdComandoOsServicoSeletiva(String idComandoOsServicoSeletiva){

		this.idComandoOsServicoSeletiva = idComandoOsServicoSeletiva;
	}

	public String getReferenciaUltimaAfericaoAnterior(){

		return referenciaUltimaAfericaoAnterior;
	}

	public void setReferenciaUltimaAfericaoAnterior(String referenciaUltimaAfericaoAnterior){

		this.referenciaUltimaAfericaoAnterior = referenciaUltimaAfericaoAnterior;
	}

	public ImovelEmissaoOrdensSeletivaHelper obterImovelEmissaoOrdensSeletivaHelper(){

		ImovelEmissaoOrdensSeletivaHelper imovelEmissaoOrdensSeletivaHelper = new ImovelEmissaoOrdensSeletivaHelper();
		imovelEmissaoOrdensSeletivaHelper.setAnormalidadeHidrometro(anormalidadeHidrometro);
		imovelEmissaoOrdensSeletivaHelper.setArquivo(arquivo);
		imovelEmissaoOrdensSeletivaHelper.setArquivoBlob(ArquivoBlob);
		imovelEmissaoOrdensSeletivaHelper.setArquivoCarregado(arquivoCarregado);
		imovelEmissaoOrdensSeletivaHelper.setArquivoDownload(arquivoDownload);
		imovelEmissaoOrdensSeletivaHelper.setArquivoMatricula(arquivoMatricula);
		imovelEmissaoOrdensSeletivaHelper.setBairro(bairro);
		imovelEmissaoOrdensSeletivaHelper.setCapacidade(capacidade);
		imovelEmissaoOrdensSeletivaHelper.setCategoriaDescricao(categoriaDescricao);
		imovelEmissaoOrdensSeletivaHelper.setCategoria(categoria);
		imovelEmissaoOrdensSeletivaHelper.setCategoriaDescricao(categoriaDescricao);
		imovelEmissaoOrdensSeletivaHelper.setColecaoConsumoMedioImovel(colecaoConsumoMedioImovel);
		imovelEmissaoOrdensSeletivaHelper.setColecaoDadosDoHidrometro(colecaoDadosDoHidrometro);
		imovelEmissaoOrdensSeletivaHelper.setColecaoDadosDoHidrometro(colecaoDadosDoHidrometro);
		imovelEmissaoOrdensSeletivaHelper.setConsumoPorEconomia(consumoPorEconomia);
		imovelEmissaoOrdensSeletivaHelper.setDataCorteFinal(dataCorteFinal);
		imovelEmissaoOrdensSeletivaHelper.setDataCorteInicial(dataCorteInicial);
		imovelEmissaoOrdensSeletivaHelper.setElo(elo);
		imovelEmissaoOrdensSeletivaHelper.setEnderecoArquivoDownload(enderecoArquivoDownload);
		imovelEmissaoOrdensSeletivaHelper.setFaturamentoGrupo(faturamentoGrupo);
		imovelEmissaoOrdensSeletivaHelper.setFirma(firma);
		imovelEmissaoOrdensSeletivaHelper.setGerenciaRegional(gerenciaRegional);
		imovelEmissaoOrdensSeletivaHelper.setHidrometroClasseMetrologica(hidrometroClasseMetrologica);
		imovelEmissaoOrdensSeletivaHelper.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);
		imovelEmissaoOrdensSeletivaHelper.setHidrometroProtecao(hidrometroProtecao);
		imovelEmissaoOrdensSeletivaHelper.setIdComandoOsServicoSeletiva(idComandoOsServicoSeletiva);
		imovelEmissaoOrdensSeletivaHelper.setIdsImoveis(idsImoveis);
		imovelEmissaoOrdensSeletivaHelper.setImovelCondominio(imovelCondominio);
		imovelEmissaoOrdensSeletivaHelper.setInscricaoTipo(inscricaoTipo);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloAreaConstruidaFinal(intervaloAreaConstruidaFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloAreaConstruidaInicial(intervaloAreaConstruidaInicial);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloDataCorteFinal(intervaloDataCorteFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloDataCorteInicial(intervaloDataCorteInicial);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloNumeroConsumoMesFinal(intervaloNumeroConsumoMesFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloNumeroConsumoMesInicial(intervaloNumeroConsumoMesInicial);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloNumeroMoradoresFinal(intervaloNumeroMoradoresFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloNumeroMoradoresInicial(intervaloNumeroMoradoresInicial);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloNumeroPontosUtilizacaoFinal(intervaloNumeroPontosUtilizacaoFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloNumeroPontosUtilizacaoInicial(intervaloNumeroPontosUtilizacaoInicial);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloQuantidadeContasVencidasFinal(intervaloQuantidadeContasVencidasFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloQuantidadeContasVencidasInicial(intervaloQuantidadeContasVencidasInicial);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloQuantidadeDocumentosFinal(intervaloQuantidadeDocumentosFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloQuantidadeDocumentosInicial(intervaloQuantidadeDocumentosInicial);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloQuantidadeEconomiasFinal(intervaloQuantidadeEconomiasFinal);
		imovelEmissaoOrdensSeletivaHelper.setIntervaloQuantidadeEconomiasInicial(intervaloQuantidadeEconomiasInicial);
		imovelEmissaoOrdensSeletivaHelper.setLigacaoAguaSituacao(ligacaoAguaSituacao);
		imovelEmissaoOrdensSeletivaHelper.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
		imovelEmissaoOrdensSeletivaHelper.setLocalidade(localidade);
		imovelEmissaoOrdensSeletivaHelper.setLogradouro(logradouro);
		imovelEmissaoOrdensSeletivaHelper.setLote(lote);
		imovelEmissaoOrdensSeletivaHelper.setMarca(marca);
		imovelEmissaoOrdensSeletivaHelper.setMarcaDescricao(marcaDescricao);
		imovelEmissaoOrdensSeletivaHelper.setMediaImovel(mediaImovel);
		imovelEmissaoOrdensSeletivaHelper.setMesAnoInstalacao(mesAnoInstalacao);
		imovelEmissaoOrdensSeletivaHelper.setNomeElo(nomeElo);
		imovelEmissaoOrdensSeletivaHelper.setNomeFirma(nomeFirma);
		imovelEmissaoOrdensSeletivaHelper.setNumeroOcorrenciasConsecutivas(numeroOcorrenciasConsecutivas);
		imovelEmissaoOrdensSeletivaHelper.setPerfilImovel(perfilImovel);
		imovelEmissaoOrdensSeletivaHelper.setPerfilImovelDescricao(perfilImovelDescricao);
		imovelEmissaoOrdensSeletivaHelper.setQuadra(quadra);
		imovelEmissaoOrdensSeletivaHelper.setQuantidadeMaxima(quantidadeMaxima);
		imovelEmissaoOrdensSeletivaHelper.setRota(rota);
		imovelEmissaoOrdensSeletivaHelper.setServicoTipo(servicoTipo);
		imovelEmissaoOrdensSeletivaHelper.setServicoTipoDescricao(servicoTipoDescricao);
		imovelEmissaoOrdensSeletivaHelper.setSetor(setor);
		imovelEmissaoOrdensSeletivaHelper.setSimulacao(simulacao);
		imovelEmissaoOrdensSeletivaHelper.setSubCategoria(subCategoria);
		imovelEmissaoOrdensSeletivaHelper.setTipoMedicao(tipoMedicao);
		imovelEmissaoOrdensSeletivaHelper.setTipoMedicaoDescricao(tipoMedicaoDescricao);
		imovelEmissaoOrdensSeletivaHelper.setTitulo(titulo);
		imovelEmissaoOrdensSeletivaHelper.setValorTotalDebitoVencido(valorTotalDebitoVencido);

		return imovelEmissaoOrdensSeletivaHelper;

	}

}