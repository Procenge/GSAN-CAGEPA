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

package gcom.gerencial.cadastro;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.faturamento.HistogramaAguaEconomia;
import gcom.faturamento.HistogramaAguaLigacao;
import gcom.faturamento.HistogramaEsgotoEconomia;
import gcom.faturamento.HistogramaEsgotoLigacao;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.cadastro.bean.*;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public interface IRepositorioGerencialCadastro {

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel id gerencia regional id licalidade id do setor comercial
	 * codigo do setor comercial id rota id guradra numero da quadra id perfil
	 * do imovel id situacao da ligacao de agua id situacao da ligacao de esgoto
	 * principal categoria do imovel esfera de poder do cliente responsavel
	 * indicador de existencia de hidrometro
	 * 
	 * @author Thiago Toscano, Bruno Barros
	 * @date 19/04/2006 17/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLigacaoEconomias(int idLocalidade) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa os dados
	 * iniciais dos imoves selecionados da localidade, contendo os dados
	 * iniciais para o agrupamento Histograma de Agua e Esgoto
	 * 
	 * @author Bruno Barros
	 * @date 14/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getContasHistograma(int idLocalidade) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o agrupamento
	 * de resumo de consumo de agua
	 * 
	 * @author Bruno Barros
	 * @date 20/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getHistoricosConsumoAgua(int idLocalidade) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel, id da região, id da microrregião, id do município, id do bairro,
	 * id do perfil do imóvel, esfera de poder, id do tipo de cliente, id da situação da ligação
	 * água,
	 * id situacao da ligacao de esgoto, principal categoria do imovel, principal sub categoria do
	 * imovel
	 * perfil da ligação da água, perfil da ligação do esgoto
	 * 
	 * @author Ivan Sérgio
	 * @date 19/04/2007
	 * @param idLocalidade
	 *            id da localida a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLigacaoEconomiaRegiao(int idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0269] - Consultar Resumo das Ligacoes / Economia
	 * 
	 * @author Thiago Toscano
	 * @date 29/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoLigacoesEconomias(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException;

	public Long maximoIdImovel() throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel id gerencia regional id licalidade id do setor comercial
	 * codigo do setor comercial id rota id guradra numero da quadra id perfil
	 * do imovel id situacao da ligacao de agua id situacao da ligacao de esgoto
	 * principal categoria do imovel esfera de poder do cliente responsavel
	 * indicador de existencia de hidrometro
	 * 
	 * @author Marcio Roberto
	 * @date 04/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoParcelamento(int idLocalidade, int anoMes) throws ErroRepositorioException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param Descrição
	 *            do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Integer pesquisarObterQuantidadeContas(Integer parcelamentoId) throws ErroRepositorioException;

	/**
	 * pesquisarObterQuantidadeGuias
	 * 
	 * @author Marcio Roberto
	 * @date 07/05/2007
	 * @param parcelamentoId
	 *            id do parcelamento para relacionar-se com parcelamentoItem
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Integer pesquisarObterQuantidadeGuias(Integer parcelamentoId) throws ErroRepositorioException;

	/**
	 * pesquisarObterQuantidadeServicosIndiretos
	 * 
	 * @author Marcio Roberto
	 * @date 07/05/2007
	 * @param parcelamentoId
	 *            id do parcelamento para relacionar-se com parcelamentoItem
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Short pesquisarObterQuantidadeServicosIndiretos(Integer parcelamentoId) throws ErroRepositorioException;

	/**
	 * pesquisarObterValorServicosIndiretos
	 * 
	 * @author Marcio Roberto
	 * @date 07/05/2007
	 * @param parcelamentoId
	 *            id do parcelamento para relacionar-se com parcelamentoItem
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public BigDecimal pesquisarObterValorServicosIndiretos(Integer parcelamentoId, String condicao) throws ErroRepositorioException;

	/**
	 * pesquisaQuantidadeCategorias
	 * 
	 * @author Bruno Barros
	 * @date 14/05/2007
	 * @param conta
	 *            id da conta a qual procuraremos as categorias
	 * @return quantidade de categorias
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisaQuantidadeCategorias(Integer conta) throws ErroRepositorioException;

	/**
	 * pesquisaQuantidadeEconomias
	 * 
	 * @author Bruno Barros
	 * @date 14/05/2007
	 * @param idConta
	 *            id da conta a qual procuraremos as categorias
	 * @return quantidade de economias
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisaQuantidadeEconomias(Integer idConta, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Pesquisar Quantidade Economias
	 * 
	 * @author Saulo Lima, Virgínia Melo
	 * @date 01/09/2009
	 * @param idConta
	 *            - id da conta
	 * @return quantidade de economias
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeEconomias(Integer idConta) throws ErroRepositorioException;

	/**
	 * pesquisarEsferaPoderClienteResponsavelImovel
	 * 
	 * @author Bruno Barros
	 * @date 16/05/2007
	 * @param imovel
	 *            a ser pesquisado
	 * @return Esfera de poder do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * pesquisarTipoClienteClienteResponsavelImovel
	 * 
	 * @author Bruno Barros
	 * @date 16/05/2007
	 * @param imovel
	 *            a ser pesquisado
	 * @return Tipo de cliente do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarTipoClienteClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException;

	public void inserirResumoConsumoAgua(Integer anoMesReferencia, ResumoConsumoAguaHelper resConsumo) throws ErroRepositorioException;

	/**
	 * Verifica se o consumo do imóvel é real.
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terá seu consumo verificado *
	 * @return 1 se consumo real, 2 senão
	 * @throws ErroRepositorioException
	 */
	public Integer verificarConsumoReal(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Verifica se o consumo do imóvel é real.
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @author Saulo Lima
	 * @date 19/07/2013
	 *       Colocando a referência
	 * @param idImovel
	 *            id do imovel que terá seu consumo verificado
	 * @param referencia
	 *            referencia (ano-mês) da conta
	 * @return 1 se consumo real, 2 se não
	 * @throws ErroRepositorioException
	 */
	public Integer verificarConsumoRealNaReferencia(Integer idImovel, Integer referencia) throws ErroRepositorioException;

	/**
	 * Verifica se o imóvel possue hidrometro
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terá seu consumo verificado *
	 * @return 1 se possuir hidrimetro, 2 senão
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaHidrometro(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Verifica se o imovel possue posso.
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terá seu consumo verificado *
	 * @return 1 se consumo real, 2 senão
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaPoco(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Verifica se o imovel volume fixo de agua
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terá seu volume fixo verificado
	 * @return 1 se volume existe, 2 senão
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaVolumeFixoAgua(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Verifica se o imovel volume fixo de esgoto
	 * 
	 * @author Bruno Barros
	 * @date 31/05/2007
	 * @param idImovel
	 *            id do imovel que terá seu volume fixo verificado
	 * @return 1 se volume existe, 2 senão
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaVolumeFixoEsgoto(Integer idImovel) throws ErroRepositorioException;

	public void inserirHistogramaAguaLigacao(Integer anoMesReferencia, HistogramaAguaLigacaoHelper helper) throws ErroRepositorioException;

	public void inserirHistogramaAguaEconomima(Integer anoMesReferencia, HistogramaAguaEconomiaHelper helper)
					throws ErroRepositorioException;

	public void inserirHistogramaEsgotoLigacao(Integer anoMesReferencia, HistogramaEsgotoLigacaoHelper helper)
					throws ErroRepositorioException;

	public void inserirHistogramaEsgotoEconomia(Integer anoMesReferencia, HistogramaEsgotoEconomiaHelper helper)
					throws ErroRepositorioException;

	/**
	 * Atualiza um histogramaAguaLigacao
	 * 
	 * @param histogramaAguaLigacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarHistogramaAguaLigacao(HistogramaAguaLigacao histogramaAguaLigacao) throws ErroRepositorioException;

	/**
	 * Atualiza um histogramaAguaEconomia
	 * 
	 * @param histogramaAguaEconomia
	 * @throws ErroRepositorioException
	 */
	public void atualizarHistogramaAguaEconomia(HistogramaAguaEconomia histogramaAguaEconomia) throws ErroRepositorioException;

	/**
	 * Atualiza um histogramaEsgotoLigacao
	 * 
	 * @param histogramaEsgotoLigacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarHistogramaEsgotoLigacao(HistogramaEsgotoLigacao histogramaEsgotoLigacao) throws ErroRepositorioException;

	/**
	 * Atualiza um histogramaEsgotoEconomia
	 * 
	 * @param histogramaEsgotoEconomia
	 * @throws ErroRepositorioException
	 */
	public void atualizarHistogramaEsgotoEconomia(HistogramaEsgotoEconomia histogramaEsgotoEconomia) throws ErroRepositorioException;

	/**
	 * [UC0623] - Gerar Resumo de Metas (CAERN)
	 * Seleciona todos os imóveis, com o ano/mes arrecadação de sistema
	 * parametros de uma determinada gerência regional por imovel
	 * 
	 * @author Sávio Luiz
	 * @date 20/07/2007
	 * @param idLocalidade
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */
	public List pesquisarDadosResumoMetas(int idSetor, Date dataInicial, Date dataFinal) throws ErroRepositorioException;

	public void inserirResumoMetas(Integer anoMesReferencia, ResumoMetasHelper resMetas) throws ErroRepositorioException;

	/**
	 * [UC0623] - Gerar Resumo de Metas 2 (CAERN)
	 * Seleciona todos os imóveis, com o ano/mes arrecadação de sistema
	 * parametros de uma determinada gerência regional por imovel
	 * 
	 * @author Sávio Luiz
	 * @date 20/07/2007
	 * @param idLocalidade
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */
	public List pesquisarDadosResumoMetasAcumulado(int idSetor) throws ErroRepositorioException;

	public void inserirResumoMetasAcumulado(Integer anoMesReferencia, ResumoMetasHelper resMetas) throws ErroRepositorioException;

	/**
	 * Seleciona o percentual de consumo de esgoto
	 * 
	 * @author Bruno Barros
	 * @date 27/07/2007
	 * @param idImovel
	 *            id do imovel que terá seu volume fixo verificado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Float percentualColetaEsgoto(Integer idImovel) throws ErroRepositorioException;

	public List getConsumoHistoricoImoveisNaoFaturados(int idSetor) throws ErroRepositorioException;

	public List<Categoria> getCategoriasImovelDistintas(int idImovel) throws ErroRepositorioException;

	public Integer getConsumoMinimoImovelCategoria(int idImovel, int idCategoria) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o agrupamento
	 * de resumo de coleta esgoto
	 * 
	 * @author Marcio Roberto
	 * @date 20/09/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getHistoricosColetaEsgoto(int idSetor) throws ErroRepositorioException;

	/**
	 * Insere resumo Coleta Esgoto
	 * 
	 * @author Marcio Roberto
	 * @date 20/09/2007
	 * @param idSetor
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoColetaEsgoto(Integer anoMesReferencia, ResumoColetaEsgotoHelper resConsumo) throws ErroRepositorioException;

	/**
	 * Deleta resumo Coleta Esgoto antes de gerar
	 * 
	 * @author Marcio Roberto
	 * @date 27/09/2007
	 * @throws ErroRepositorioException
	 */
	public void deletaResumoColetaEsgoto(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Seleciona o percentual de consumo de esgoto
	 * 
	 * @author Marcio Roberto
	 * @date 02/10/2007
	 * @param idImovel
	 *            id do imovel que terá seu volume fixo verificado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public float percentualEsgoto(Integer idImovel) throws ErroRepositorioException;

	public Categoria obterPrincipalCategoriaConta(int idConta) throws ErroRepositorioException;

	/**
	 * Verifica se ja foi gerado o ano mes de referencia de faturamento
	 * para o resumo de consumo de agua
	 * 
	 * @author Bruno Leonardo Rodrigues Barros
	 * @return
	 * @throws ErroRepositorioException
	 */
	// public boolean resumoConsumoAguaGerado( int anomesreferencia, int idSetor ) throws
	// ErroRepositorioException;

	/**
	 * Apaga os dados do consumo de agua gerado para o mes de faturamento
	 * 
	 * @author Bruno Leonardo Rodrigues Barros
	 * @throws ErroRepositorioException
	 */
	// public void excluirResumoConsumoAguaJaGerado ( int anomesreferencia, int idSetor ) throws
	// ErroRepositorioException;

	/**
	 * Apaga os dados já gerados do resumo para o ano/mes referencia e o setor comercial / pode ser
	 * usado em todos os resumos
	 * 
	 * @author Bruno Leonardo Rodrigues Barros/ Roberto Barbalho
	 * @throws ErroRepositorioException
	 */

	public void excluirResumoGerencial(int anomesreferencia, String resumoGerencial, String resumoCampoAnoMes, int idSetor)
					throws ErroRepositorioException;

	public Integer consultarQtdRegistrosResumoLigacoesEconomias(
					InformarDadosGeracaoRelatorioConsultaHelper dadosGeracaoRelatorioConsultaHelper);

	/**
	 * Método consultarDetalhesLigacoesEconomiasGCS
	 * <p>
	 * Esse método implementa consulta dos detalhes para resumo de ligacoes economia.
	 * </p>
	 * RASTREIO: [OC897714][UC269]
	 * 
	 * @param parametrosPesquisa
	 * @return
	 * @author Marlos Ribeiro
	 * @param codigoEmpresa
	 * @since 21/11/2012
	 */
	public List<DetalheLigacaoEconomiaGCSHelper> consultarDetalhesLigacoesEconomiasGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa, Integer paginacao) throws ErroRepositorioException;

	/**
	 * Método consultarSumarioLigacoesPorCategoriaGCS
	 * <p>
	 * Esse método implementa consulta dos sumarios de {@link LigacaoAguaSituacao}.
	 * </p>
	 * RASTREIO: [OC897714][UC269]
	 * 
	 * @param parametrosPesquisa
	 * @return
	 * @author Marlos Ribeiro
	 * @since 21/11/2012
	 */
	public List<SumarioLigacaoPorCategoriaGCSHelper> consultarSumarioLigacoesPorCategoriaGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa) throws ErroRepositorioException;
}