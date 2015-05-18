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

package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.bean.ImovelRelatorioHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.text.NumberFormat;
import java.util.*;

/**
 * classe respons�vel por criar o relat�rio de im�veis por endere�o
 * 
 * @author Rafael Corr�a
 * @created 11 de Julho de 2005
 */
public class RelatorioImovelEndereco
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioImovelEndereco(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEL_ENDERECO);
	}

	@Deprecated
	public RelatorioImovelEndereco() {

		super(null, "");
	}

	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// Recupera os par�metros utilizados na forma��o da query

		// id da genrencia regional
		String gerenciaRegionalPesquisa = (String) getParametro("gerenciaRegional");

		// id da unidade negocio
		String idUnidadeNegocio = (String) getParametro("unidadeNegocio");

		// numero da quadra origem
		String qudraOrigem = (String) getParametro("quadraOrigem");

		// numero quadra destino
		String quadraDestino = (String) getParametro("quadraDestino");

		// lote origem
		String loteOrigem = (String) getParametro("loteOrigem");

		// lote destino
		String loteDestino = (String) getParametro("loteDestino");

		// cep
		String cep = (String) getParametro("cep");

		// id localidade origem
		String localidadeOrigem = (String) getParametro("localidadeOrigem");

		// id localidade destino
		String localidadeDestino = (String) getParametro("localidadeDestino");

		// setor comercial origem CD
		String setorComercialOrigemCD = (String) getParametro("setorComercialOrigemCD");

		// setor comercial destino CD
		String setorComercialDestinoCD = (String) getParametro("setorComercialDestinoCD");

		// cliente ID
		String clienteID = (String) getParametro("clienteID");

		// municipio ID
		String municipioID = (String) getParametro("municipioID");

		// bairro ID
		String bairroID = (String) getParametro("bairroID");

		// logradouro ID
		String logradouroID = (String) getParametro("logradouroID");

		// cliente relacao tipo ID
		String clienteRelacaoTipoID = (String) getParametro("clienteRelacaoTipoID");

		// cliente tipo ID
		String clienteTipoID = (String) getParametro("clienteTipoID");

		// imovel condominio ID
		String imovelCondominioID = (String) getParametro("imovelCondominioID");

		// imovel Principal ID
		String imovelPrincipalID = (String) getParametro("imovelPrincipalID");

		// situacao Agua
		String[] situacaoAgua = (String[]) getParametro("situacaoAgua");

		// situacao Ligacao Esgoto
		String[] situacaoLigacaoEsgoto = (String[]) getParametro("situacaoLigacaoEsgoto");

		// consumo Minimo Inicial
		String consumoMinimoInicial = (String) getParametro("consumoMinimoInicial");

		// consumo Minimo Final
		String consumoMinimoFinal = (String) getParametro("consumoMinimoFinal");

		// consumo Minimo Fixado Esgoto Inicial
		String consumoMinimoFixadoEsgotoInicial = (String) getParametro("consumoMinimoFixadoEsgotoInicial");

		// consumo Minimo Fixado Esgoto Final
		String consumoMinimoFixadoEsgotoFinal = (String) getParametro("consumoMinimoFixadoEsgotoFinal");

		// consumo Fixado Esgoto do Po�o Inicial
		String consumoFixadoEsgotoPocoInicial = (String) getParametro("consumoFixadoEsgotoPocoInicial");

		// consumo Fixado Esgoto do Po�o Final
		String consumoFixadoEsgotoPocoFinal = (String) getParametro("consumoFixadoEsgotoPocoFinal");

		// intervalo Percentual Esgoto Inicial
		String intervaloPercentualEsgotoInicial = (String) getParametro("intervaloPercentualEsgotoInicial");

		// intervalor Percentual Esgoto Final
		String intervaloPercentualEsgotoFinal = (String) getParametro("intervaloPercentualEsgotoFinal");

		// indicador Medicao
		String indicadorMedicaoPesquisa = (String) getParametro("indicadorMedicao");

		// tipo Medicao ID
		String tipoMedicaoID = (String) getParametro("tipoMedicaoID");

		// intervalo Media Minima Imovel Inicial
		String intervaloMediaMinimaImovelInicial = (String) getParametro("intervaloMediaMinimaImovelInicial");

		// intervalo Media Minima Imovel Final
		String intervaloMediaMinimaImoveFinal = (String) getParametro("intervaloMediaMinimaImoveFinal");

		// intervalo Media Minima Hidrometro Inicial
		String intervaloMediaMinimaHidrometroInicial = (String) getParametro("intervaloMediaMinimaHidrometroInicial");

		// intervalo Media Minima Hidrometro Final
		String intervaloMediaMinimaHidrometroFinal = (String) getParametro("intervaloMediaMinimaHidrometroFinal");

		// perfil Imovel ID
		String perfilImovelID = (String) getParametro("perfilImovelID");

		// categoria Imovel ID
		String categoriaImovelID = (String) getParametro("categoriaImovelID");

		// sub categoria ID
		String subCategoriaID = (String) getParametro("subCategoriaID");

		// quantidade Economias Inicial
		String quantidadeEconomiasInicial = (String) getParametro("quantidadeEconomiasInicial");

		// quantidade Economias Final
		String quantidadeEconomiasFinal = (String) getParametro("quantidadeEconomiasFinal");

		// numero Pontos Inicial
		String numeroPontosInicial = (String) getParametro("numeroPontosInicial");

		// numero Pontos Final
		String numeroPontosFinal = (String) getParametro("numeroPontosFinal");

		// numero Moradores Inicial
		String numeroMoradoresInicial = (String) getParametro("numeroMoradoresInicial");

		// numero Moradoras Final
		String numeroMoradoresFinal = (String) getParametro("numeroMoradoresFinal");

		// area Construida Inicial
		String areaConstruidaInicial = (String) getParametro("areaConstruidaInicial");

		// area Construida Final
		String areaConstruidaFinal = (String) getParametro("areaConstruidaFinal");

		// area Construida Faixa
		String areaConstruidaFaixa = (String) getParametro("areaConstruidaFaixa");

		// poco Tipo ID
		String pocoTipoID = (String) getParametro("pocoTipoID");

		// tipo Situacao Faturamento ID
		String tipoSituacaoFaturamentoID = (String) getParametro("tipoSituacaoFaturamentoID");

		// tipo Situacao Especial Cobranca ID
		String tipoSituacaoEspecialCobrancaID = (String) getParametro("tipoSituacaoEspecialCobrancaID");

		// situacao Cobranca ID
		String situacaoCobrancaID = (String) getParametro("situacaoCobrancaID");

		// dia Vencimento Alternativo
		String diaVencimentoAlternativo = (String) getParametro("diaVencimentoAlternativo");

		// ocorrencia Cadastro
		String ocorrenciaCadastro = (String) getParametro("ocorrenciaCadastro");

		// tarifa Consumo
		String tarifaConsumo = (String) getParametro("tarifaConsumo");

		// anormalidade Elo
		String anormalidadeElo = (String) getParametro("anormalidadeElo");

		// Tipo de empresa
		Short tipoEmpresa = (Short) getParametro("tipoEmpresa");

		String rotaInicial = (String) getParametro("rotaInicial");
		String rotaFinal = (String) getParametro("rotaFinal");
		String segmentoInicial = (String) getParametro("segmentoInicial");
		String segmentoFinal = (String) getParametro("segmentoFinal");
		String sequencialRotaInicial = (String) getParametro("sequencialRotaInicial");
		String sequencialRotaFinal = (String) getParametro("sequencialRotaFinal");
		String subloteInicial = (String) getParametro("subloteInicial");
		String subloteFinal = (String) getParametro("subloteFinal");
		String indicadorOrdenacao = (String) getParametro("indicadorOrdenacao");

		Fachada fachada = Fachada.getInstancia();

		Collection imoveisRelatoriosHelper = fachada.gerarRelatorioImovelEnderecoOutrosCriterios(imovelCondominioID, imovelPrincipalID,
						situacaoAgua, consumoMinimoInicial, consumoMinimoFinal, situacaoLigacaoEsgoto, consumoMinimoFixadoEsgotoInicial,
						consumoMinimoFixadoEsgotoFinal, intervaloPercentualEsgotoInicial, intervaloPercentualEsgotoFinal,
						intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImoveFinal, intervaloMediaMinimaHidrometroInicial,
						intervaloMediaMinimaHidrometroFinal, perfilImovelID, pocoTipoID, tipoSituacaoFaturamentoID, situacaoCobrancaID,
						tipoSituacaoEspecialCobrancaID, anormalidadeElo, areaConstruidaInicial, areaConstruidaFinal, ocorrenciaCadastro,
						tarifaConsumo, gerenciaRegionalPesquisa, localidadeOrigem, localidadeDestino, setorComercialOrigemCD,
						setorComercialDestinoCD, qudraOrigem, quadraDestino, loteOrigem, loteDestino, cep, logradouroID, bairroID,
						municipioID, tipoMedicaoID, indicadorMedicaoPesquisa, subCategoriaID, categoriaImovelID,
						quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimentoAlternativo, clienteID, clienteTipoID,
						clienteRelacaoTipoID, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
						areaConstruidaFaixa, idUnidadeNegocio, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal,
						segmentoInicial, segmentoFinal, subloteInicial, subloteFinal, tipoEmpresa, indicadorOrdenacao,
						consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal);

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioImovelEnderecoBean relatorioBean = null;

		// se a cole��o de par�metros da analise n�o for vazia
		if(imoveisRelatoriosHelper != null && !imoveisRelatoriosHelper.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator imovelRelatorioIterator = imoveisRelatoriosHelper.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(imovelRelatorioIterator.hasNext()){

				ImovelRelatorioHelper helper = (ImovelRelatorioHelper) imovelRelatorioIterator.next();

				relatorioBean = new RelatorioImovelEnderecoBean();

				// C�digo da Ger�ncia Regional
				relatorioBean.setIdGerenciaRegional(X(helper.getIdGerenciaRegional()));

				// Descri��o da Ger�ncia Regional
				relatorioBean.setNomeGerenciaRegional(X(helper.getDescricaoGerenciaRegional()));

				// C�digo da Localidade
				relatorioBean.setIdLocalidade(X(helper.getIdLocalidade()));

				// Descri��o da Localidade
				relatorioBean.setNomeLocalidade(X(helper.getDescricaoLocalidade()));

				// C�digo do Setor Comercial
				relatorioBean.setIdSetorComercial(X(helper.getCodigoSetorComercial()));

				// Descri��o do Setor Comercial
				relatorioBean.setNomeSetorComercial(X(helper.getDescricaoSetorComercial()));

				// Endere�o
				relatorioBean.setEndereco(helper.getEndereco());

				// Matr�cula do Im�vel
				Integer matriculaImovel = helper.getMatriculaImovel();
				relatorioBean.setMatricula(X(matriculaImovel));

				// Nome do Cliente Usu�rio
				relatorioBean.setNomeCliente(X(helper.getClienteUsuarioNome()));

				// Quadra.Lote.SubLote
				relatorioBean.setQuadraLoteSubLote(format(helper.getNumeroQuadra(), 3) + "." + format(helper.getNumeroLote(), 4) + "."
								+ format(helper.getNumeroSubLote(), 3));
				// Categoria
				relatorioBean.setCategoria(X(helper.getDescricaoAbreviadaCategoria()));

				// Economias
				relatorioBean.setEconomias(X(helper.getQuantidadeEconomia()));

				// Situa��o da Liga��o de �gua
				relatorioBean.setSituacaoAgua(X(helper.getLigacaoAguaSituacao()));

				// Situa��o da Liga��o de Esgoto
				relatorioBean.setSituacaoEsgoto(X(helper.getLigacaoEsgotoSituacao()));

				// Indicador Medido de Esgoto
				relatorioBean.setIndicadorMedidoEsgoto(X(helper.getIndicadorExistenciaHidrometro() == 1 ? "SIM" : "N�O"));

				// Percentual de Esgoto
				relatorioBean.setPercentualEsgoto(X(helper.getPercentual()));

				// Consumo M�nimo Fixado de Esgoto
				relatorioBean.setConsumoMinimoEsgoto(X(helper.getConsumoMinimoFixadoLigacaoEsgoto()));

				// Inscri��o do Im�vel
				relatorioBean.setInscricaoFormatada(helper.getInscricaoImovel());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("tipoFormatoRelatorio", "R0610");

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		// Verifica o relat�rio que deve ser executado
		String relatorio = "";

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(tipoEmpresa)){
			relatorio = ConstantesRelatorios.RELATORIO_IMOVEL_ENDERECO_COM_INSCRICAO;
		}else{
			relatorio = ConstantesRelatorios.RELATORIO_IMOVEL_ENDERECO;
		}

		// exporta o relat�rio em pdf e retorna o array de bytes
		retorno = gerarRelatorio(relatorio, parametros, ds, tipoFormatoRelatorio);

		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.IMOVEL_ENDERECO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = 0;

		// id da genrencia regional
		String gerenciaRegionalPesquisa = (String) getParametro("gerenciaRegional");

		// id da unidade negocio
		String idUnidadeNegocio = (String) getParametro("unidadeNegocio");

		// numero da quadra origem
		String qudraOrigem = (String) getParametro("quadraOrigem");

		// numero quadra destino
		String quadraDestino = (String) getParametro("quadraDestino");

		// lote origem
		String loteOrigem = (String) getParametro("loteOrigem");

		// lote destino
		String loteDestino = (String) getParametro("loteDestino");

		// cep
		String cep = (String) getParametro("cep");

		// id localidade origem
		String localidadeOrigem = (String) getParametro("localidadeOrigem");

		// id localidade destino
		String localidadeDestino = (String) getParametro("localidadeDestino");

		// setor comercial origem CD
		String setorComercialOrigemCD = (String) getParametro("setorComercialOrigemCD");

		// setor comercial destino CD
		String setorComercialDestinoCD = (String) getParametro("setorComercialDestinoCD");

		// cliente ID
		String clienteID = (String) getParametro("clienteID");

		// municipio ID
		String municipioID = (String) getParametro("municipioID");

		// bairro ID
		String bairroID = (String) getParametro("bairroID");

		// logradouro ID
		String logradouroID = (String) getParametro("logradouroID");

		// cliente relacao tipo ID
		String clienteRelacaoTipoID = (String) getParametro("clienteRelacaoTipoID");

		// cliente tipo ID
		String clienteTipoID = (String) getParametro("clienteTipoID");

		// imovel condominio ID
		String imovelCondominioID = (String) getParametro("imovelCondominioID");

		// imovel Principal ID
		String imovelPrincipalID = (String) getParametro("imovelPrincipalID");

		// situacao Agua
		String[] situacaoAgua = (String[]) getParametro("situacaoAgua");

		// situacao Ligacao Esgoto
		String[] situacaoLigacaoEsgoto = (String[]) getParametro("situacaoLigacaoEsgoto");

		// consumo Minimo Inicial
		String consumoMinimoInicial = (String) getParametro("consumoMinimoInicial");

		// consumo Minimo Final
		String consumoMinimoFinal = (String) getParametro("consumoMinimoFinal");

		// consumo Minimo Fixado Esgoto Inicial
		String consumoMinimoFixadoEsgotoInicial = (String) getParametro("consumoMinimoFixadoEsgotoInicial");

		// consumo Minimo Fixado Esgoto Final
		String consumoMinimoFixadoEsgotoFinal = (String) getParametro("consumoMinimoFixadoEsgotoFinal");

		// consumo Fixado Esgoto do Po�o Inicial
		String consumoFixadoEsgotoPocoInicial = (String) getParametro("consumoFixadoEsgotoPocoInicial");

		// consumo Fixado Esgoto do Po�o Final
		String consumoFixadoEsgotoPocoFinal = (String) getParametro("consumoFixadoEsgotoPocoFinal");

		// intervalo Percentual Esgoto Inicial
		String intervaloPercentualEsgotoInicial = (String) getParametro("intervaloPercentualEsgotoInicial");

		// intervalor Percentual Esgoto Final
		String intervaloPercentualEsgotoFinal = (String) getParametro("intervaloPercentualEsgotoFinal");

		// indicador Medicao
		String indicadorMedicaoPesquisa = (String) getParametro("indicadorMedicao");

		// tipo Medicao ID
		String tipoMedicaoID = (String) getParametro("tipoMedicaoID");

		// intervalo Media Minima Imovel Inicial
		String intervaloMediaMinimaImovelInicial = (String) getParametro("intervaloMediaMinimaImovelInicial");

		// intervalo Media Minima Imovel Final
		String intervaloMediaMinimaImoveFinal = (String) getParametro("intervaloMediaMinimaImoveFinal");

		// intervalo Media Minima Hidrometro Inicial
		String intervaloMediaMinimaHidrometroInicial = (String) getParametro("intervaloMediaMinimaHidrometroInicial");

		// intervalo Media Minima Hidrometro Final
		String intervaloMediaMinimaHidrometroFinal = (String) getParametro("intervaloMediaMinimaHidrometroFinal");

		// perfil Imovel ID
		String perfilImovelID = (String) getParametro("perfilImovelID");

		// categoria Imovel ID
		String categoriaImovelID = (String) getParametro("categoriaImovelID");

		// sub categoria ID
		String subCategoriaID = (String) getParametro("subCategoriaID");

		// quantidade Economias Inicial
		String quantidadeEconomiasInicial = (String) getParametro("quantidadeEconomiasInicial");

		// quantidade Economias Final
		String quantidadeEconomiasFinal = (String) getParametro("quantidadeEconomiasFinal");

		// numero Pontos Inicial
		String numeroPontosInicial = (String) getParametro("numeroPontosInicial");

		// numero Pontos Final
		String numeroPontosFinal = (String) getParametro("numeroPontosFinal");

		// numero Moradores Inicial
		String numeroMoradoresInicial = (String) getParametro("numeroMoradoresInicial");

		// numero Moradoras Final
		String numeroMoradoresFinal = (String) getParametro("numeroMoradoresFinal");

		// area Construida Inicial
		String areaConstruidaInicial = (String) getParametro("areaConstruidaInicial");

		// area Construida Final
		String areaConstruidaFinal = (String) getParametro("areaConstruidaFinal");

		// area Construida Faixa
		String areaConstruidaFaixa = (String) getParametro("areaConstruidaFaixa");

		// poco Tipo ID
		String pocoTipoID = (String) getParametro("pocoTipoID");

		// tipo Situacao Faturamento ID
		String tipoSituacaoFaturamentoID = (String) getParametro("tipoSituacaoFaturamentoID");

		// tipo Situacao Especial Cobranca ID
		String tipoSituacaoEspecialCobrancaID = (String) getParametro("tipoSituacaoEspecialCobrancaID");

		// situacao Cobranca ID
		String situacaoCobrancaID = (String) getParametro("situacaoCobrancaID");

		// dia Vencimento Alternativo
		String diaVencimentoAlternativo = (String) getParametro("diaVencimentoAlternativo");

		// ocorrencia Cadastro
		String ocorrenciaCadastro = (String) getParametro("ocorrenciaCadastro");

		// tarifa Consumo
		String tarifaConsumo = (String) getParametro("tarifaConsumo");

		// anormalidade Elo
		String anormalidadeElo = (String) getParametro("anormalidadeElo");

		String rotaInicial = (String) getParametro("rotaInicial");
		String rotaFinal = (String) getParametro("rotaFinal");
		String segmentoInicial = (String) getParametro("segmentoInicial");
		String segmentoFinal = (String) getParametro("segmentoFinal");
		String sequencialRotaInicial = (String) getParametro("sequencialRotaInicial");
		String sequencialRotaFinal = (String) getParametro("sequencialRotaFinal");
		String subloteInicial = (String) getParametro("subloteInicial");
		String subloteFinal = (String) getParametro("subloteFinal");

		retorno = Fachada.getInstancia().pesquisarTotalRegistrosRelatorioImovelEndereco(imovelCondominioID, imovelPrincipalID,
						situacaoAgua, consumoMinimoInicial, consumoMinimoFinal, situacaoLigacaoEsgoto, consumoMinimoFixadoEsgotoInicial,
						consumoMinimoFixadoEsgotoFinal, intervaloPercentualEsgotoInicial, intervaloPercentualEsgotoFinal,
						intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImoveFinal, intervaloMediaMinimaHidrometroInicial,
						intervaloMediaMinimaHidrometroFinal, perfilImovelID, pocoTipoID, tipoSituacaoFaturamentoID, situacaoCobrancaID,
						tipoSituacaoEspecialCobrancaID, anormalidadeElo, areaConstruidaInicial, areaConstruidaFinal, ocorrenciaCadastro,
						tarifaConsumo, gerenciaRegionalPesquisa, localidadeOrigem, localidadeDestino, setorComercialOrigemCD,
						setorComercialDestinoCD, qudraOrigem, quadraDestino, loteOrigem, loteDestino, cep, logradouroID, bairroID,
						municipioID, tipoMedicaoID, indicadorMedicaoPesquisa, subCategoriaID, categoriaImovelID,
						quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimentoAlternativo, clienteID, clienteTipoID,
						clienteRelacaoTipoID, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
						areaConstruidaFaixa, idUnidadeNegocio, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal,
						segmentoInicial, segmentoFinal, subloteInicial, subloteFinal, consumoFixadoEsgotoPocoInicial,
						consumoFixadoEsgotoPocoFinal);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioImovelEndereco", this);
	}

	private String X(Object o){

		return o == null ? "" : o.toString();
	}

	private String format(Number number, int integer){

		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setMinimumIntegerDigits(integer);
		format.setMaximumIntegerDigits(integer);
		return format.format(number).replace(".", "");
	}

}
