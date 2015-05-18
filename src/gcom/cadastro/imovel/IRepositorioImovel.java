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

package gcom.cadastro.imovel;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.bean.FiltrarImovelOutrosCriteriosHelper;
import gcom.cadastro.imovel.bean.ImovelArquivoAgenciaVirtualWebHelper;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.bean.EmitirDocumentoCobrancaBoletimCadastroHelper;
import gcom.cobranca.bean.SituacaoEspecialCobrancaHelper;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.conta.Conta;
import gcom.gui.faturamento.bean.FiltrarImovelInserirManterContaHelper;
import gcom.micromedicao.Rota;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.cadastro.cliente.FiltrarRelatorioClientesEspeciaisHelper;
import gcom.relatorio.cadastro.imovel.FiltrarRelatorioBoletimCadastroHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * < <Descrição da Interface>>
 * 
 * @author Administrador
 * @created 16 de Junho de 2004
 */
public interface IRepositorioImovel {

	/**
	 * Atualizar um os campos lastId,dataUltimaAtualização do imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarImovelExecucaoOrdemServicoLigacaoAgua(Imovel imovel, LigacaoAguaSituacao situacaoAgua)
					throws ErroRepositorioException;

	/**
	 * Atualizar um os campos lastId,dataUltimaAtualização do imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(Imovel imovel, LigacaoEsgotoSituacao situacaoEsgoto)
					throws ErroRepositorioException;

	/**
	 * Inseri um imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void inserirImovel(Imovel imovel) throws ErroRepositorioException;

	/**
	 * Atualiza um imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarImovel(Imovel imovel) throws ErroRepositorioException;

	/**
	 * Remove um cliente imovel economia
	 * 
	 * @param id
	 *            Description of the Parameter
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public void removerClienteImovelEconomia(Integer id) throws ErroRepositorioException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param objeto
	 *            Descrição do parâmetro
	 * @param condicional
	 *            Descrição do parâmetro
	 * @param id
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void removerTodos(String objeto, String condicional, Integer id) throws ErroRepositorioException;

	/**
	 * Pesquisa uma coleção de imóveis com uma query especifica
	 * 
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @param idSetorComercial
	 *            parametros para a consulta
	 * @param idQuadra
	 *            parametros para a consulta
	 * @param lote
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovel(Integer idLocalidade, Integer idSetorComercial, Integer idQuadra, Short lote, int indicadorExclusao)
					throws ErroRepositorioException;

	/**
	 * Atualiza apenas os dados (Localidade, Setor, Quadra e lote) do imóvel
	 * 
	 * @author Luciano Galvao
	 * @date 25/02/2013
	 */
	public void atualizarImovelInscricao(Integer imovelId, Integer localidadeId, Integer setorComercialId, Integer codigoSetorComercial,
					Integer quadraId, Integer numeroQuadra, short lote, short subLote, short testadaLote, Integer rotaId)
					throws ErroRepositorioException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovelSubcategoria
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarImovelSubCategoria(ImovelSubcategoria imovelSubcategoria) throws ErroRepositorioException;

	/**
	 * [UC0064 – Obter Quantidade Economias]
	 * 
	 * @param imovel
	 * @return Object
	 * @exception ErroRepositorioException
	 */
	public Object pesquisarObterQuantidadeEconomias(Imovel imovel) throws ErroRepositorioException;

	public Short pesquisarObterQuantidadeEconomias(Imovel imovel, Categoria categoria) throws ErroRepositorioException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarObterQuantidadeEconomiasCategoria(Integer idImovel, boolean construirObjetoCompleto)
					throws ErroRepositorioException;

	public Collection obterQuantidadeEconomiasCategoria(Integer idConta) throws ErroRepositorioException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection obterDescricoesCategoriaImovel(Imovel imovel) throws ErroRepositorioException;

	/**
	 * [UC0164] Filtrar Imovel Por Outros Criterios
	 * 
	 * @author Rhawi Dantas
	 * @created 29/12/2005
	 */
	public Collection<Imovel> pesquisarImovelParametrosClienteImovel(FiltroClienteImovel filtroClienteImovel)
					throws ErroRepositorioException;

	/**
	 * [UC0164] Filtrar Imovel Por Outros Criterios
	 * 
	 * @author Rhawi Dantas
	 * @created 29/12/2005
	 */

	public Collection pesquisarImovelOutrosCriterios(FiltrarImovelOutrosCriteriosHelper filtrarImovelOutrosCriteriosHelper)
					throws ErroRepositorioException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 */
	public Collection pesquisarImovelSituacaoEspecialFaturamento(String valor,
					SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper) throws ErroRepositorioException;

	/**
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @created 07/03/2006
	 */
	public Collection pesquisarImovelSituacaoEspecialCobranca(String valor, SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper)
					throws ErroRepositorioException;

	public Integer verificarExistenciaImovel(Integer idImovel) throws ErroRepositorioException;

	public Integer recuperarMatriculaImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 */
	public Integer validarMesAnoReferencia(SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper)
					throws ErroRepositorioException;

	/**
	 * Atualiza os ids do faturamento situação tipo da tabela imóvel com o id do
	 * faturamento escolhido pelo usuario
	 * [UC0156] Informar Situacao Especial de Faturamento
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public void atualizarFaturamentoSituacaoTipo(Collection colecaoIdsImoveis, Integer idFaturamentoTipo) throws ErroRepositorioException;

	/**
	 * Seta para null o id da cobrança situação tipo da tabela imóvel
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public void retirarSituacaoEspecialFaturamento(Collection colecaoIdsImoveis) throws ErroRepositorioException;

	public Collection<Integer> pesquisarImoveisIds(Integer rotaId) throws ErroRepositorioException;

	public Object pesquisarImovelIdComConta(Integer imovelId, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta se uma conta já existe no histórico como parcelada, evitando assim a geração da
	 * conta de novo
	 * 
	 * @param imovelId
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarImovelIdComContaHistoricoParcelado(Integer imovelId, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Pesquisa o maior ano mes de referencia da tabela de faturamento grupo
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public Integer validarMesAnoReferenciaCobranca(SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper)
					throws ErroRepositorioException;

	/**
	 * Atualiza o id da cobrança situação tipo da tabela imóvel com o id da
	 * situação escolhido pelo usuario
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */

	public void atualizarCobrancaSituacaoTipo(Collection colecaoIdsImoveis, Integer idCobrancaTipo) throws ErroRepositorioException;

	/**
	 * Seta para null o id da cobrança situação tipo da tabela imóvel
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public void retirarSituacaoEspecialCobranca(Collection colecaoIdsImoveis) throws ErroRepositorioException;

	/**
	 * Obtém o indicador de existência de hidrômetro para o imóvel, caso exista
	 * retorna 1(um) indicando SIM caso contrário retorna 2(dois) indicando NÃO
	 * [UC0307] Obter Indicador de Existência de Hidrômetro no Imóvel
	 * 
	 * @author Thiago Toscano
	 * @date 02/06/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */

	public Integer obterIndicadorExistenciaHidrometroImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador
	 * @date 07/07/2006
	 * @param imovel
	 * @param idLigacaoAguaSituacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelLigacaoAgua(Imovel imovel, Integer idLigacaoAguaSituacao) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Imóvel Outros Critérios
	 * 
	 * @author Rafael Corrêa
	 * @date 24/07/2006
	 */
	public Collection gerarRelatorioImovelOutrosCriterios(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,

					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal,

					String idImovelPerfil, String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo,
					String idSituacaoEspecialCobranca, String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal,
					String idCadastroOcorrencia, String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial,
					String idLocalidadeFinal, String setorComercialInicial, String setorComercialFinal, String quadraInicial,
					String quadraFinal, String loteOrigem, String loteDestno, String cep, String logradouro, String bairro,
					String municipio, String idTipoMedicao, String indicadorMedicao, String idSubCategoria, String idCategoria,
					String quantidadeEconomiasInicial, String quantidadeEconomiasFinal, String diaVencimento, String idCliente,
					String idClienteTipo, String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal,
					String numeroMoradoresInicial, String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio,
					String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ErroRepositorioException;

	public Collection pesquisarSubcategoriasImovelRelatorio(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0164] Filtrar Imoveis por Outros Criterios
	 * Filtra para saber a quantidade de imoveis antes de executar o filtro
	 * 
	 * @author Rafael Santos
	 * @date 01/08/2006
	 */
	public Integer obterQuantidadadeRelacaoImoveisDebitos(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, Integer relatorio,
					String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ErroRepositorioException;

	/**
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição para exibição.
	 * acima no controlador será montada a inscrição
	 */
	public Object[] pesquisarInscricaoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Esse método é usado para fzazer uma pesquisa na tabela imóvel e confirmar
	 * se o id passado é de um imóvel excluído(idExclusao)
	 * Flávio Cordeiro
	 */

	public Boolean confirmarImovelExcluido(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Dados de Economias do Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */
	public Collection gerarRelatorioDadosEconomiasImovelOutrosCriterios(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String segmentoInicial,
					String segmentoFinal, String rotaInicial, String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ErroRepositorioException;

	/**
	 * Obtem os dados da Subcategoria do Imovel para o Relatorio de Dados
	 * Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */
	public Collection gerarRelatorioDadosEconomiasImovelSubcategoria(String idImovel) throws ErroRepositorioException;

	/**
	 * Obtem os dados do Imovel Economia do Imovel para o Relatorio de Dados
	 * Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */
	public Collection gerarRelatorioDadosEconomiasImovelEconomia(String idImovel, String idSubCategoria) throws ErroRepositorioException;

	/**
	 * Obtem os dados do Cliente Imovel Economia do Imovel para o Relatorio de
	 * Dados Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */
	public Collection gerarRelatorioDadosEconomiasImovelClienteEconomia(String idImovelEconomia) throws ErroRepositorioException;

	/**
	 * [UC164] Filtrar Imoveis Outros Criterios
	 * Monta os inner join da query de acordo com os parametros informados
	 * 
	 * @author Rafael Santos
	 * @date 03/08/2006
	 * @return
	 */
	public String montarInnerJoinFiltrarImoveisOutrosCriterios(

	String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal, String intervaloMediaMinimaHidrometroInicial,
					String intervaloMediaMinimaHidrometroFinal, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo,
					String idSituacaoEspecialCobranca, String idEloAnormalidade, String idCadastroOcorrencia, String idConsumoTarifa,
					String idTipoMedicao, String indicadorMedicao, String idSubCategoria, String idCategoria, String idCliente,
					String idClienteTipo, String idClienteRelacaoTipo, Integer relatorio);

	/**
	 * Permite pesquisar entidade beneficente [UC0389] Inserir Autorização para
	 * Doação Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param filtroEntidadeBeneficente
	 *            -
	 *            Filtro com os valores para pesquisa
	 * @return Collection<EntidadeBeneficente> - Coleção de entidade(s)
	 *         beneficente(s)
	 * @throws ErroRepositorioException
	 */
	public Collection<EntidadeBeneficente> pesquisarEntidadeBeneficente(FiltroEntidadeBeneficente filtroEntidadeBeneficente)
					throws ErroRepositorioException;

	/**
	 * Permite pesquisar imóvel doação [UC0389] Inserir Autorização para Doação
	 * Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param filtroImoveldoacao
	 *            -
	 *            Filtro com os valores para pesquisa
	 * @return Collection<ImovelDoacao> - Coleção de imóvei(s) doação
	 * @throws ErroRepositorioException
	 */
	public Collection<ImovelDoacao> pesquisarImovelDoacao(FiltroImovelDoacao filtroImovelDoacao) throws ErroRepositorioException;

	/**
	 * Permite atualizar as informações referentes ao imóvel doação [UC0390]
	 * Manter Autorização para Doação Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param imovelDoacao
	 *            -
	 *            instância de imóvel doação que servirá de base para a
	 *            atualição
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelDoacao(ImovelDoacao imovelDoacao) throws ErroRepositorioException;

	/**
	 * Pesquisa um imóvel a partir do seu id.Retorna os dados que compõem a
	 * inscrição e o endereço do mesmo
	 * 
	 * @author Raphael Rossiter
	 * @date 01/08/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarImovelRegistroAtendimento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consutlar os Dados Cadastrais do Imovel [UC0472] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 07/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarImovelDadosCadastrais(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de clientes do imovel [UC0472] Consultar Cliente
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarClientesImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de categorias do imovel [UC0472] Consultar Imovel
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarCategoriasImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0475] Consultar Perfil Imovel
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idImovel
	 * @return Perfil do Imóvel
	 * @exception ErroRepositorioException
	 */
	public ImovelPerfil obterImovelPerfil(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consutlar os Dados Complementares do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarImovelDadosComplementares(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de vencimento alternativos do imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarVencimentoAlternativoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de Debitos Automaticos do imovel [UC0473] Consultar
	 * Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarDebitosAutomaticosImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarFaturamentosSituacaoHistorico(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel
	 * UC0069-ManterDadosTarifaSocial
	 * 
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarFaturamentoSituacaoHistorico(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel Ativos [UC0156]
	 * 
	 * @author Saulo Lima
	 * @date 22/08/2008
	 * @param Integer
	 *            idFaturamentoSituacaoTipo
	 * @return Collection
	 *         FaruramentoSituacaoHistorico
	 * @exception ErroRepositorioException
	 */
	// public Collection pesquisarFaturamentosSituacaoHistoricoAtivos(Integer
	// idFaturamentoSituacaoTipo)
	// throws ErroRepositorioException;

	public Collection pesquisarFaturamentosSituacaoHistoricoAtivos(SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de cobranças Situação Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarCobrancasSituacaoHistorico(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consutlar os Dados de Analise da Medição e Consumo do Imovel [UC0473]
	 * Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 12/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarImovelAnaliseMedicaoConsumo(Integer idImovel) throws ErroRepositorioException;

	// ----------Savio
	public void atualizarImovelLeituraAnormalidade(Map<Integer, MedicaoHistorico> mapAtualizarLeituraAnormalidadeImovel, Date dataAtual)
					throws ErroRepositorioException;

	/**
	 * Consutlar os Dados do Historico de Faturamento [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarImovelHistoricoFaturamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consutlar o cliente usuário do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public String consultarClienteUsuarioImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consultar as contas do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author eduardo henrique
	 * @date 02/10/2008
	 *       Alteração para incluir opção de Ordenação
	 * @param idImovel
	 * @param ordemAscendente
	 *            true -> Asc ; false -> Desc
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarContasImovel(Integer idImovel, boolean ordemAscendente) throws ErroRepositorioException;

	/**
	 * Consutlar as contas Historicos do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarContasHistoricosImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consultar os dados de parcelamentos do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 20/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarParcelamentosDebitosImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 27/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteUsuarioImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Atualiza apenas os dados (numeroParcelamento,
	 * numeroParcelamentoConsecutivo, numeroReparcelamentoConsecutivo) do imóvel
	 * 
	 * @param imovel
	 *            parametros para a consulta
	 *            Author: Fernanda Paiva
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public void atualizarDadosImovel(Integer codigoImovel, Integer numeroParcelamento, Integer numeroReparcelamentoConsecutivo,
					Integer numeroReparcelamento) throws ErroRepositorioException;

	public Collection<Categoria> pesquisarCategoriasImovel(Imovel imovel) throws ErroRepositorioException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização [SB0002] Atualizar
	 * Imóvel/Ligação de Água
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelLigacaoAguaEsgoto(Integer idImovel, Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização [SB0002] Atualizar
	 * Imóvel/Ligação de Água
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public void atualizarLigacaoAgua(Integer idLigacaoAgua, Integer consumoMinimo, short indiacadorConsumoFixado)
					throws ErroRepositorioException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização [SB0002] Atualizar
	 * Imóvel/Ligação de Água
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public void atualizarLigacaoEsgoto(Integer idLigacaoEsgoto, Integer consumoMinimo, short indicadorVolumeFixado)
					throws ErroRepositorioException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * 
	 * @date 20/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarConsumoTarifa(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Filtrar o Imovel pelos parametros informados
	 * 
	 * @author Rafael Santos
	 * @date 24/11/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovel(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometroHistInst, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelManterVinculo, boolean pesquisarImovelCondominio,
					Integer numeroPagina) throws ErroRepositorioException;

	/**
	 * Filtrar o Imovel pelos parametros informados, para saber a quantidade de imoveis
	 * 
	 * @author Rafael Santos
	 * @date 24/11/2006
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Inclusao do Filtro de Numero do Imovel.
	 * @param idImovel
	 * @param numeroHidrometroImovel
	 *            TODO
	 * @param numeroImovel
	 *            TODO
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarQuantidadeImovel(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometroHistInst, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelManterVinculo, boolean pesquisarImovelCondominio,
					String numeroHidrometroImovel, String numeroImovel, String pCriterioSelecaoVinculoRateio)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o Imovel pelos parametros informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelInscricao(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometro, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelCondominio, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligação de esgoto
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisaridLigacaoEsgotoSituacao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligacao de agua
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisaridLigacaoAguaSituacao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligacao de agua
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarTarifaImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Leonardo Vieira
	 * @created 12/12/2006
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisClientesRelacao(Cliente cliente, ClienteRelacaoTipo relacaoClienteImovel, Integer numeroInicial,
					Integer idSetorComercial) throws ErroRepositorioException;

	public Integer pesquisarExistenciaImovelSubCategoria(Integer idImovel, Integer idCategoria) throws ErroRepositorioException;

	public Collection pesquisarImoveisPorRota(Integer idRota) throws ErroRepositorioException;

	/**
	 * [UC0378] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurélio
	 * @created 20/12/2006
	 * @param dLocalidadeInicial
	 *            ,
	 *            idLocalidadeFinal, codigoSetorComercialInicial,
	 *            codigoSetorComercialFinal
	 * @param quadraInicial
	 *            ,
	 *            quadraFinal, String loteInicial, loteFinal, subLoteInicial,
	 *            subLoteFinal, idTarifaAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImoveisTarifaConsumo(String idLocalidadeInicial, String idLocalidadeFinal,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String quadraInicial, String quadraFinal,
					String loteInicial, String loteFinal, String subLoteInicial, String subLoteFinal, String idTarifaAnterior,
					String idsCategorias, String idsSubcategorias)
					throws ErroRepositorioException;

	/**
	 * Atualiza a tarifa de consumo de um ou mais imoveis
	 * [UC0378] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurelio
	 * @created 19/12/2006
	 * @author Hugo Lima
	 * @date 06/03/2012
	 *       Alteracao na consulta para atualizacao por mais dois novos campos (tarifaEspecial,
	 *       dataValidadeTarifaEspecial)
	 * @param matricula
	 * @param tarifaAtual
	 * @param tarifaEspecial
	 * @param dataValidadeTarifaEspecial
	 * @param colecaoImoveis
	 * @throws ErroRepositorioException
	 */
	public void atualizarImoveisTarifaConsumo(String matricula, String tarifaAtual, String tarifaEspecial, Date dataValidadeTarifaEspecial,
					Collection colecaoImoveis) throws ErroRepositorioException;

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Atualiza o perfil do imóvel para o perfil normal
	 * 
	 * @date 04/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelPerfilNormal(Integer idImovel, boolean manter) throws ErroRepositorioException;

	/**
	 * [UC0490] - Informar Situação de Cobrança
	 * Pesquisa o imóvel com a situação da ligação de água e a de esgoto
	 * 
	 * @date 13/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */
	public Imovel pesquisarImovelComSituacaoAguaEsgoto(Integer idImovel) throws ErroRepositorioException;

	public Collection pesquisarImoveisPorRotaComPaginacao(Integer idRota, Integer numeroInicial) throws ErroRepositorioException;

	/**
	 * @author Saulo Lima
	 * @date 01/10/2010
	 * @param idRota
	 * @param idCriterioCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarImoveisPorRotaCriterioCobranca(Integer idRota, Integer idCriterioCobranca)
					throws ErroRepositorioException;

	/**
	 * Usado pelo Pesquisar Imovel Retorno o Imovel, com o Nome do Cliente,
	 * Matricula e Endereço
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @param idImovel
	 * @param numeroImovel
	 *            TODO
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelInscricaoNew(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometro, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelCondominio, String numeroImovel, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0006] - Verificar número de IPTU
	 * Verifica se já existe outro imóvel ou economia com o mesmo número de IPTU
	 * no mesmo município
	 * 
	 * @date 13/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */
	public Integer verificarNumeroIptu(BigDecimal numeroIptu, Integer idImovel, Integer idImovelEconomia, Integer idMunicipio)
					throws ErroRepositorioException;

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0007] - Verificar número de contrato da companhia de energia elétrica
	 * Verifica se já existe outro imóvel ou economia com o mesmo número de
	 * contrato da companhia elétrica
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */
	public Integer verificarNumeroCompanhiaEletrica(Long numeroCompanhiaEletrica, Integer idImovel, Integer idImovelEconomia)
					throws ErroRepositorioException;

	/**
	 * Obtém o quantidade de economias da subCategoria por imovel
	 * 
	 * @param idImovel
	 *            O identificador do imóvel
	 * @return Coleção de SubCategorias
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection obterQuantidadeEconomiasSubCategoria(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Obtém o quantidade de economias da subCategoria por imovel
	 * 
	 * @autor Rômulo Aurélio
	 * @param idImovel
	 *            O identificador do imóvel
	 * @return Coleção de imovelSubCategorias
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 08/02/2007
	 */

	public Collection pesquisarImovelSubcategorias(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @date 21/02/2007
	 * @author Vivianne Sousa
	 * @throws ErroRepositorioException
	 */
	public Imovel pesquisarImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @date 28/01/2012
	 * @author Carlos Chrystian
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelPorInscricao(Integer idLocalidade, Integer idSetorComercial, Integer idQuadra, Short nnLote,
					int indicadorExclusao) throws ErroRepositorioException;

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ErroRepositorioException;

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ErroRepositorioException;

	/**
	 * [UC0302] Gerar Débitos a Cobrar de Acréscimos por Impontualidade
	 * Pequisa o identificador de cobranca de acréscimo pro impontualidade
	 * para o imóvel do cliente responsável.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/02/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short obterIndicadorGeracaoAcrescimosClienteImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * Obter o consumo médio como não medido para o imóvel passado
	 * 
	 * @author Raphael Rossiter
	 * @date 06/03/2007
	 */
	public Integer obterConsumoMedioNaoMedidoImovel(Imovel imovel) throws ErroRepositorioException;

	/**
	 * Filtra o Pagamento pelo seu id carregando os dados necessários
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author Kássia Albuquerque
	 * @date 12/07/2007
	 * @param idPagamentoHistorico
	 * @return Collection<PagamentoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoPeloId(Integer idPagamento) throws ErroRepositorioException;

	/**
	 * Filtra o Pagamento Historico pelo seu id carregando os dados necessários
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author Kássia Albuquerque
	 * @date 12/07/2007
	 * @param idPagamentoHistorico
	 * @return Collection<PagamentoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoPeloId(Integer idPagamentoHistorico) throws ErroRepositorioException;

	/**
	 * Obter a situação de cobrança para o imóvel passado
	 * 
	 * @author Vivianne Sousa
	 * @date 07/03/2007
	 */
	public String obterSituacaoCobrancaImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa uma coleção de imóveis
	 * 
	 * @author Ana Maria
	 * @date 16/03/2007
	 */

	public Collection pesquisarColecaoImovel(FiltrarImovelInserirManterContaHelper filtro) throws ErroRepositorioException;

	/**
	 * Pesquisa uma coleção de imóveis do cliente
	 * 
	 * @author Ana Maria
	 * @date 20/03/2007
	 */
	public Collection pesquisarColecaoImovelCliente(Integer codigoCliente, Integer relacaoTipo) throws ErroRepositorioException;

	public Integer pesquisarContaMotivoRevisao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 16/04/2007
	 */
	public Collection obterQuantidadeEconomiasCategoriaPorSubcategoria(Integer conta) throws ErroRepositorioException;

	/**
	 * Obtem as subcategorias de uma determinada categoria
	 * 
	 * @param id
	 *            Id da categoria a ser pesquisada
	 * @return Colecao de subcategorias
	 * @throws ErroRepositorioException
	 */
	public ImovelSubcategoria obterSubCategoriasPorCategoria(Integer idCategoria, Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa todos os ids do perfil do imóvel.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsPerfilImovel() throws ErroRepositorioException;

	/**
	 * Consutlar o cliente usuário do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Bruno Barros
	 * @date 27/04/2007
	 * @param imovel
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */
	public Cliente consultarClienteUsuarioImovel(Imovel imovel) throws ErroRepositorioException;

	/**
	 * [UC0591] - Gerar Relatório de Clientes Especiais
	 * Pesquisas os imóveis de acordo com os parâmetros da pesquisa
	 * 
	 * @author Rafael Corrêa
	 * @date 31/05/2007
	 */
	public Collection pesquisarImovelClientesEspeciaisRelatorio(String idUnidadeNegocio, String idGerenciaRegional,
					String idLocalidadeInicial, String idLocalidadeFinal, String[] idsPerfilImovel, String[] idsCategoria,
					String[] idsSubcategoria, String idSituacaoAgua, String idSituacaoEsgoto, String qtdeEconomiasInicial,
					String qtdeEconomiasFinal, String intervaloConsumoAguaInicial, String intervaloConsumoAguaFinal,
					String intervaloConsumoEsgotoInicial, String intervaloConsumoEsgotoFinal, String idClienteResponsavel,
					String intervaloConsumoResponsavelInicial, String intervaloConsumoResponsavelFinal,
					Date dataInstalacaoHidrometroInicial, Date dataInstalacaoHidrometroFinal, String[] idsCapacidadesHidrometro,
					String[] idsTarifasConsumo, Integer anoMesFaturamento, String idLeituraAnormalidade, String leituraAnormalidade,
					String idConsumoAnormalidade, String consumoAnormalidade, String[] idsClienteTipoEspecial)
					throws ErroRepositorioException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situação da ligação de esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoEsgotoSituacao
	 * @throws ErroRepositorioException
	 */
	public LigacaoEsgotoSituacao pesquisarLigacaoEsgotoSituacao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situação da ligação de agua
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoAguaSituacao
	 * @throws ErroRepositorioException
	 */
	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer idImovel) throws ErroRepositorioException;

	public FaturamentoGrupo pesquisarGrupoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Obtém a descrição de uma categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 04/06/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoSubcategoria(Integer idSubcategoria) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Imóvel Outros Critérios
	 * 
	 * @author Rafael Corrêa,Rafael Santos
	 * @date 24/07/2006,01/08/2006
	 */
	public Collection gerarRelatorioImovelEnderecoOutrosCriterios(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String indicadorOrdenacao,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Imóvel Outros Critérios
	 * 
	 * @author Rafael Corrêa,Rafael Santos
	 * @date 24/07/2006,01/08/2006
	 */
	public Collection gerarRelatorioCadastroConsumidoreInscricao(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String indicadorOrdenacao,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ErroRepositorioException;

	/**
	 * Obtém a o nome do cliente
	 * 
	 * @author Kassia Albuquerque
	 * @date 05/07/2007
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDescricaoIdCliente(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Obtém a o nome do arrecadador
	 * 
	 * @author Kassia Albuquerque
	 * @date 09/07/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public String pesquisarNomeAgenteArrecadador(Integer idPagamentoHistorico) throws ErroRepositorioException;

	/**
	 * Obtém a o 117º caracter de uma String
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/07/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public String pesquisarCaracterRetorno(Integer idConteudoRegistro) throws ErroRepositorioException;

	/**
	 * [UC0623] - GERAR RESUMO DE METAS EXECUTDO NO MÊS(CAERN)
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2007
	 */
	public Object[] obterSubCategoriasComCodigoGrupoPorCategoria(Integer idCategoria, Integer idImovel) throws ErroRepositorioException;

	/**
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição para exibição.
	 * acima no controlador será montada a inscrição
	 */
	public Object[] pesquisarLocalidadeSetorImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarSequencialRota(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Retorna o cep do imóvel
	 * 
	 * @param imovel
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 */
	public Cep pesquisarCepImovel(Imovel imovel) throws ErroRepositorioException;

	/**
	 * Gerar Boletim de Cadastro
	 * 
	 * @date 20/08/2007
	 * @deprecated
	 */
	public Collection<EmitirDocumentoCobrancaBoletimCadastroHelper> pesquisarBoletimCadastro(String idImovelCondominio,
					String idImovelPrincipal, String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,
					String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto,
					String consumoMinimoFinalEsgoto, String intervaloValorPercentualEsgotoInicial,
					String intervaloValorPercentualEsgotoFinal, String intervaloMediaMinimaImovelInicial,
					String intervaloMediaMinimaImovelFinal, String intervaloMediaMinimaHidrometroInicial,
					String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil, String idPocoTipo, String idFaturamentoSituacaoTipo,
					String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca, String idEloAnormalidade,
					String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia, String idConsumoTarifa,
					String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal, String setorComercialInicial,
					String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem, String loteDestno, String cep,
					String logradouro, String bairro, String municipio, String idTipoMedicao, String indicadorMedicao,
					String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,
					String diaVencimento, String idCliente, String idClienteTipo, String idClienteRelacaoTipo, String numeroPontosInicial,
					String numeroPontosFinal, String numeroMoradoresInicial, String numeroMoradoresFinal, String idAreaConstruidaFaixa,
					String idUnidadeNegocio, int quantidadeCobrancaDocumentoInicio) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 19/09/2007
	 * @return ImovelCobrancaSituacao
	 * @throws ErroRepositorioException
	 */
	public CobrancaSituacao pesquisarImovelCobrancaSituacao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisas todas as Situações Especiais de Cobrança ativas de um determinado imóvel
	 * 
	 * @author Saulo Lima
	 * @date 09/09/2013
	 * @param idImovel
	 * @return Collection<ImovelCobrancaSituacao>
	 * @throws ErroRepositorioException
	 */
	public Collection<ImovelCobrancaSituacao> pesquisarImovelCobrancaSituacaoAtivas(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0541] Emitir 2 Via de Conta Internet
	 * 
	 * @author Vivianne Sousa
	 * @date 02/09/2007
	 * @throws ErroRepositorioException
	 */
	public Imovel pesquisarDadosImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição e de rota para exibição.
	 * 
	 * @author Vivianne Sousa
	 * @date 06/11/2007
	 */

	public Collection pesquisarInscricaoImoveleRota(String idsImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Sávio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImoveisClientesRelacao(Collection idsCliente, Integer numeroInicial, Integer idSetorComercial)
					throws ErroRepositorioException;

	public Collection pesquisarSituacoesCobrancaImovel(Integer idImovel) throws ErroRepositorioException;

	public Collection<ImovelCadastroOcorrencia> pesquisarOcorrenciasCadastro(String idImovel) throws ErroRepositorioException;

	public Collection<ImovelEloAnormalidade> pesquisarEloAnormalidade(String idImovel) throws ErroRepositorioException;

	/**
	 * Overload do método original que consulta Todos os Imóveis de Determinada Rota, com atributos
	 * de
	 * Localidade carregados.
	 * 
	 * @author Sávio Luiz
	 * @date
	 * @param idRota
	 *            - Imóveis de determinada Rota
	 * @return Collection<Imovel>
	 * @throws ErroRepositorioException
	 */
	public Collection<Imovel> pesquisarImoveisPorRotaComLocalidade(Integer idRota) throws ErroRepositorioException;

	/**
	 * Método responsável por obter o proprietário do imóvel.
	 * 
	 * @author Virgínia Melo
	 * @date 04/06/2009
	 * @param idImovel
	 * @return ClienteImovel
	 */
	public ClienteImovel pesquisarClienteProprietarioImovel(Integer idImovel) throws ErroRepositorioException;

	public Integer verificarExistenciaImovelParaCliente(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Atualiza a situação de cobrança do imóvel
	 */

	public void atualizarSituacaoCobrancaImovel(Integer idSituacaoCobranca, Integer idImovel) throws ErroRepositorioException;

	public Imovel pesquisarImovelOrdemServico(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer consultarClienteRelacaoTipoPorImovel(Integer idImovel, Short idClienteRelacaoTipo) throws ErroRepositorioException;

	public Collection<Object[]> pesquisarImoveisPorSetorComercialCriterioCobranca(Integer idSetorComercial, Integer idCriterioCobranca,
					Date dataCortado, Integer idGrupoCobranca, Integer[] idsTipoDocumentoAIgnorar, Integer idAcaoCobranca,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	public HidrometroInstalacaoHistorico pesquisarHidrometroPorImovel(Integer idImovel) throws ErroRepositorioException;

	public HidrometroInstalacaoHistorico pesquisarHidrometroPorLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * Obtem o ConsumoFaixaAreaCategoria da categoria com a area informada
	 * 
	 * @author isilva
	 * @date 21/01/2011
	 * @param idCategoria
	 * @param area
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ConsumoFaixaAreaCategoria pesquisarConsumoFaixaAreaCategoriaPorCategoriaArea(Integer idCategoria, Integer area)
					throws ErroRepositorioException;

	/**
	 * [FS0003] - Verificar se imóvel já esta suprimido Judicialmente.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @return
	 * @throws ControladorException
	 */
	public Boolean verificarImovelSuprimidoJudicial(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @author Ailton Sousa
	 * @date 11/02/2011
	 *       Pesquisa uma coleção de imóveis com uma query especifica
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param rota
	 * @param segmento
	 * @param lote
	 * @param sublote
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarImovel(Integer idLocalidade, Integer idSetorComercial, Integer idRota, Short segmento, Short lote,
					Short sublote, int indicadorExclusao) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Relatório Boletim de Cadastro
	 * 
	 * @author Anderson Italo
	 * @date 19/04/2011
	 *       Obter dados dos Imóveis pelos parametros informados
	 */
	public Collection pesquisarRelatorioBoletimCadastro(FiltrarRelatorioBoletimCadastroHelper filtro, int totalPaginacao)
					throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Relatório Boletim de Cadastro
	 * 
	 * @author Anderson Italo
	 * @date 25/04/2011
	 *       Obter Total dos Imóveis pelos parametros informados
	 */
	public Integer pesquisarTotalRegistrosRelatorioBoletimCadastro(FiltrarRelatorioBoletimCadastroHelper filtro)
					throws ErroRepositorioException;

	/**
	 * Obterm o Funcionário que executou a operação em conta
	 * 
	 * @author isilva
	 * @date 02/05/2011
	 * @param idConta
	 * @param tipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Funcionario obterFuncionarioExecutouOperacaoEmConta(Integer idConta, String tipo) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Relatório Imóveis por Endereço
	 * Obter Total dos Imóveis pelos parametros informados
	 * 
	 * @date 14/06/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioImovelEndereco(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial,
					String consumoFixadoEsgotoPocoFinal) throws ErroRepositorioException;

	/**
	 * [UC0591] - Gerar Relatório de Clientes Especiais
	 * Pesquisas Total de Registros de acordo com os parâmetros da pesquisa
	 * 
	 * @author Anderson Italo
	 * @date 11/07/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioClientesEspeciais(FiltrarRelatorioClientesEspeciaisHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0146] Manter Conta
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 11/08/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Verifica se o Imovel é do tipo condomínio
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public boolean isImovelCondominio(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0003] – Obter Grupo do Imóvel
	 * Obtem o ImovelConsumoFaixaAreaCategoria da categoria pelo imovel e categoria.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param idCategoria
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelConsumoFaixaAreaCategoria pesquisarImovelConsumoFaixaAreaCategoriaPorCategoriaImovel(Integer idCategoria, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Verifica se o indicador de debito automático do imovel está ativo.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 */
	public boolean isIndicadorDebitoAutomaticoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Retorna true caso exista imoveis para a coleçao de rotas recebidas.
	 * 
	 * @author Ailton Sousa
	 * @date 15/09/2011
	 * @param colecaoRotas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean isImovelPorColecaoRotas(Collection<Rota> colecaoRotas) throws ErroRepositorioException;

	/**
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarNomeImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Retorna coleção de imoveis para os ids passados.
	 * 
	 * @date 13/10/2011
	 * @author Péricles Tavares
	 * @param idImoveis
	 * @param idSetorComercial
	 * @param idAcaoCobranca
	 * @param idsTipoDocumentoAIgnorar
	 * @return imoveis
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveis(Collection<Integer> idImoveis, Integer idSetorComercial, Integer idAcaoCobranca,
					Integer[] idsTipoDocumentoAIgnorar, CobrancaCriterio criterioCobranca, ClienteRelacaoTipo clienteRelacaoTipo)
					throws ErroRepositorioException;

	/**
	 * Retorna o ImovelSubcategoria com a maior quantidade de economia.
	 * 
	 * @author Ailton Sousa
	 * @date 28/12/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelSubcategoria pesquisarImovelSubcategoriaComMaiorQuantidadeEconomia(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudança de Rota da Quadra
	 * Método que obtém o total de imóveis com rota igual à rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisRotaIgualAnteriorQuadra(Integer idQuadra, Integer idRota) throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudança de Rota da Quadra
	 * Método que obtém o total de imóveis com rota diferente à rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisRotaDiferenteAnteriorQuadra(Integer idQuadra, Integer idRota) throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * Método que atualiza a rota dos imóveis pela quadra e rota(informada ou não).
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2012
	 * @throws ErroRepositorioException
	 */
	public void atualizarRotaImoveisPorQuadra(Integer idQuadra, Integer idRotaAtualizar, Integer idRotaAnterior)
					throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Collection pesquisarRelatorioImoveisPorQuadra(Integer idQuadra) throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioImoveisPorQuadra(Integer idQuadra) throws ErroRepositorioException;

	/**
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @autor Josenildo Neves
	 * @date 20/03/2012
	 */

	public Collection<ImovelArquivoAgenciaVirtualWebHelper> pesquisarImovelArquivoAgenciaVirtualWebPorSetorComercial(
					Integer idSetorComercial) throws ErroRepositorioException;

	public Object pesquisarImovelIdComContaHistorico(Integer imovelId, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Consultar Imóvel pelo Id
	 * 
	 * @author Anderson Italo
	 * @date 24/03/2012
	 */
	public Imovel pesquisarImovelPorId(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudança de Distrito Operacional da Quadra
	 * Método que obtém o total de imóveis com distrito operacional igual ao distrito operacional
	 * anterior da quadra.
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ErroRepositorioException
	 */

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Obtém informações do cliente responsável do imóvel
	 * 
	 * @author Anderson Italo
	 * @date 04/06/2012
	 */
	public Object[] pesquisarInformacoesClienteResponsavel(Integer idImovel) throws ErroRepositorioException;

	public Integer obterTotalImoveisDistritoOperacionalIgualAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudança de Distrito Operacional da Quadra
	 * Método que obtém o total de imóveis com distrito operacional diferente do distrito
	 * operacional anterior da quadra.
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisDistritoOperacionalDiferenteAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ErroRepositorioException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * Método que atualiza o distrito operacional dos imóveis pela quadra e distrito operacional
	 * anterior(informada ou não).
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ErroRepositorioException
	 */
	public void atualizarDistritoOperacionalImoveisPorQuadra(Integer idQuadra, Integer idDistritoOperacionalAtualizar,
					Integer idDistritoOperacionalAnterior) throws ErroRepositorioException;

	/**
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @autor Josenildo Neves
	 * @date 01/06/2012
	 */

	public Collection pesquisarClienteResponsavelArquivoAgenciaVirtualWeb(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarObterQuantidadeEconomias(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Recupera os Imoveis por Atividade Cronograma do faturamento.
	 * 18. Lista de Imóveis (selecionar os imóveis das rotas do comando (a partir da tabela
	 * IMOVEL com ROTA_ID=ROTA_ID da tabela FATURAMENTO_ATIV_CRON_ROTA com FTAC_ID=FTAC_ID da
	 * tabela FATURAMENTO_ATIVIDADE_CRONOGRAMA
	 * 
	 * @param idFaturamentoAtvCron
	 *            ID do {@link FaturamentoAtividadeCronograma}.
	 * @param idFaturamentoAtvCron
	 * @return uma lista de array.
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarImoveisPorFaturamentoAtividadeCronograma(Integer idSetor, Integer idFaturamentoAtvCron)
					throws ErroRepositorioException;

	public List<Integer> consultarSetoresPorFaturamentoAtividadeCronograma(Integer idFaturamentoAtividadeCronograma)
					throws ErroRepositorioException;

	/**
	 * @author Saulo Lima
	 * @date 27/06/2012
	 * @param Integer
	 *            idImovel
	 * @return Object[] idLocalidade, idSetorComercial, idBairro
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalizacaoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisar Rotas dos Imóveis
	 * 
	 * @author Hebert Falcão
	 * @date 12/11/2012
	 */
	public Collection<Rota> pesquisarRotasDosImoveis(Collection<Integer> idImoveis) throws ErroRepositorioException;

	/**
	 * Método responsável por obter o código da rota
	 * 
	 * @param idRota
	 * @return
	 */
	Short obterCodigoRota(Integer idRota) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Imóvel Outros Critérios Contador
	 * 
	 * @author Ítalo Almeida
	 * @date 10/01/2013
	 */

	public int gerarRelatorioImovelOutrosCriteriosCount(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ErroRepositorioException;

	public Collection pesquisarImoveisGerarProvisaoReceita(Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Cliente pesquisarClientePropietarioImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa todos os imóveis de uma determinada Localidade.
	 * 
	 * @author Josenildo Neves
	 * @date 02/07/2013
	 * @param idLocalidade
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	Collection<Integer> pesquisarImoveisPorLocalidade(Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 */
	public Integer retornarAnoMesReferenciaFaturamentoGrupoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 */
	public Boolean verificarFaturamentoGrupoImovelIniciado(Integer idImovel, String atividadeFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 */
	public Boolean verificarFaturamentoGrupoImovelEncerrado(Integer idImovel, String atividadeFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 */
	public Integer retornaTipoLeituraFaturamento(Integer idImovel) throws ErroRepositorioException;

	public void atualizarNumeroEmissaoContrato(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0102] –Gerar Arquivo Texto para Faturamento Imediato –Modelo 4
	 * 
	 * @author Anderson Italo
	 * @date 03/10/2013
	 */
	public Integer verificarImovelSubcategoriaReparticoesPublicasFederais(Integer idImovel, Integer idCategoria, String idSubcategorias)
					throws ErroRepositorioException;

	/**
	 * @autor Eduardo Oliveira
	 * @date 16/12/2013
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterQuantidadeAlteracoesImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public List<Object[]> pesquisarDadosImoveisComContaEmAtraso(boolean apenasPublicos) throws ErroRepositorioException;

	/**
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public Collection<Conta> pesquisarContasEmAtrasoPorImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Retorna o ImovelSubcategoria principal (O registro com maior quantidade de economias).
	 * 
	 * @author Anderson Italo
	 * @date 14/06/2014
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelSubcategoria pesquisarImovelSubcategoriaPrincipal(Integer idImovel) throws ErroRepositorioException;
	/**
	 * <p>
	 * [OC1372979]
	 * </p>
	 * 
	 * @author Magno Silveira (magno.silveira@procenge.com.br)
	 * @since 22/10/2014
	 * @param imovelCondominioId
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisSubcategoriasParaCondominio(Integer idImovel) throws ErroRepositorioException;



	/**
	 * 
	 */
	public void atualizarRotaImovel(Integer idImovel, Integer idRotaAtualizar) throws ErroRepositorioException;

}
