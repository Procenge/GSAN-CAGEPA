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
 * 
 * GSANPCG
 * Eduardo Henrique
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
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.bean.SituacaoEspecialCobrancaHelper;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.VencimentoAlternativo;
import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.gui.faturamento.bean.FiltrarImovelInserirManterContaHelper;
import gcom.micromedicao.Rota;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.cadastro.cliente.FiltrarRelatorioClientesEspeciaisHelper;
import gcom.relatorio.cadastro.imovel.FiltrarRelatorioBoletimCadastroHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.AlteracaoInscricaoImovelException;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.procenge.comum.exception.NegocioException;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Administrador
 * @created 7 de Junho de 2004
 */
public interface ControladorImovelLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Atualizar um imovel na base
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 */
	public void atualizarImovelLigacaoAgua(Imovel imovel, Integer idLigacaoAguaSituacao) throws ControladorException;

	/**
	 * Insere um imovel na base
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 */
	public void inserirImovel(Imovel imovel) throws ControladorException;

	/**
	 * Retorna a quantidade de economias de um im�vel
	 * 
	 * @param imovel
	 *            Im�vel que ser� consultado
	 * @return Quantidade de economias
	 */
	public int obterQuantidadeEconomias(Imovel imovel) throws ControladorException;

	/**
	 * @return Quantidade de categorias cadastradas no BD
	 * @throws RemoteException
	 */
	public Object pesquisarObterQuantidadeCategoria() throws ControladorException;

	/**
	 * Retorna a quantidade de economias de um im�vel
	 * 
	 * @param imovel
	 *            Im�vel que ser� consultado
	 * @return Quantidade de economias
	 */
	public Collection obterColecaoImovelSubcategorias(Imovel imovel, Integer quantidadeMinimaEconomia) throws ControladorException;

	/**
	 * Retorna a quantidade de categorias de um im�vel
	 * 
	 * @param imovel
	 *            Im�vel que ser� consultado
	 * @return uma cole��o de categorias
	 */
	public Collection<Categoria> obterQuantidadeEconomiasCategoria(Imovel imovel) throws ControladorException;

	/**
	 * Retorna a quantidade de categorias de um im�vel
	 * 
	 * @param imovel
	 *            Im�vel que ser� consultado
	 * @return uma cole��o de categorias
	 */
	public Collection obterQuantidadeEconomiasContaCategoria(Conta conta) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param subCategorias
	 *            Description of the Parameter
	 * @param enderecoImoveis
	 *            Description of the Parameter
	 * @param clientes
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 */
	public Integer inserirImovelRetorno(Imovel imovel, Collection subCategorias, Collection consumoFaixaAreaCategoria,
					Collection enderecoImoveis, Collection clientes, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0011] Inserir Im�vel
	 * [UC0014] Manter Im�vel
	 * 
	 * @date 21/01/2014
	 * @author Saulo Lima
	 * @param idLocalidade
	 * @param codigoSetorComercial
	 * @param numeroQuadra
	 * @param idRota
	 */
	public void verificarAlteracaoRota(Integer idLocalidade, Integer codigoSetorComercial, Integer numeroQuadra, Integer idRota)
					throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovelSubcategoria
	 *            Descri��o do par�metro
	 */
	public void inserirImovelSubCategoria(ImovelSubcategoria imovelSubcategoria, Usuario usuarioLogado) throws ControladorException;

	/**
	 * inseri o im�vel economia e o cliente imovel economia do im�vel
	 * subcategoria
	 * 
	 * @param imoveisEconomias
	 *            Description of the Parameter
	 */
	public void informarImovelEconomias(Collection imoveisEconomias, Usuario usuarioLogado) throws ControladorException;

	/**
	 * remove o im�vel economia e o cliente imovel economia do im�vel
	 * subcategoria
	 * 
	 * @param imovelEconomia
	 *            Description of the Parameter
	 */
	public void removerImovelEconomia(ImovelEconomia imovelEconomia, Usuario usuarioLogado) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public void atualizarImovel(Imovel imovel, Usuario usuario) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param subcategorias
	 *            Descri��o do par�metro
	 * @param enderecoImovel
	 *            Descri��o do par�metro
	 * @param clientes
	 *            Descri��o do par�metro
	 */
	public void atualizarImovel(Imovel imovel, Collection subcategorias, Collection consumoFaixaAreaCategoria, Collection enderecoImovel,
					Collection clientes, Collection colecaoClientesImoveisRemovidos, Collection colecaoImovelSubcategoriasRemovidas,
					Usuario usuarioLogado, boolean prepararAlteracaoInscricao) throws ControladorException;

	/**
	 * verifica se existe algum iptu no imovel ou imovelEconomia
	 * 
	 * @param imoveisEconomia
	 *            Descri��o do par�metro
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param numeroIptu
	 *            Description of the Parameter
	 * @param dataUltimaAlteracao
	 *            Descri��o do par�metro
	 */
	public void verificarExistenciaIPTU(Collection imoveisEconomia, Imovel imovel, String numeroIptu, Date dataUltimaAlteracao)
					throws ControladorException;

	/**
	 * verifica se existe algum numero da celpe no imovel ou imovelEconomia
	 * 
	 * @param imoveisEconomia
	 *            Descri��o do par�metro
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param numeroCelpe
	 *            Description of the Parameter
	 * @param dataUltimaAlteracao
	 *            Descri��o do par�metro
	 */
	public void verificarExistenciaCelpe(Collection imoveisEconomia, Imovel imovel, String numeroCelpe, Date dataUltimaAlteracao)
					throws ControladorException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/11/2006
	 * @param ids
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void removerImovel(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Pesquisa uma cole��o de im�veis com uma query especifica
	 * 
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @param idSetorComercial
	 *            parametros para a consulta
	 * @param idQuadra
	 *            parametros para a consulta
	 * @param lote
	 *            Descri��o do par�metro
	 * @return Description of the Return Value
	 */
	public Collection pesquisarImovel(Integer idLocalidade, Integer idSetorComercial, Integer idQuadra, Short lote)
					throws ControladorException;

	/**
	 * @author Carlos Chrystian
	 * @date 28/01/2012
	 *       Consulta do im�vel pela inscri��o.
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @param codigoSetorComercial
	 *            parametros para a consulta
	 * @param idQuadra
	 *            parametros para a consulta
	 * @param lote
	 *            Descri��o do par�metro
	 * @return Description of the Return Value
	 */
	public Collection pesquisarImovelPorInscricao(Integer idLocalidade, Integer codigoSetorComercial, Integer nnQuadra, Short nnLote)
					throws ControladorException;

	/**
	 * Atualiza apenas os dados (Localidade, Setor, Quadra e lote) do im�vel
	 * 
	 * @author Ailton Sousa
	 * @date 09/02/2011
	 *       Altera��o para permitir o controle de transa��o.
	 * @param imovel
	 *            parametros para a consulta
	 */
	public void atualizarImovelInscricao(Map<Integer, Collection<Imovel>> mapaRotaImoveis, Usuario usuarioLogado,
					String indicadorAlteracaoRota, boolean usuarioConfirmou) throws ControladorException, AlteracaoInscricaoImovelException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovelSubcategoria
	 *            Descri��o do par�metro
	 */
	public void atualizarImovelSubCategoria(ImovelSubcategoria imovelSubcategoria) throws ControladorException;

	public Collection filtrarImovelOutrosCriterios(String tipoMedicao) throws ControladorException;

	public Collection<Imovel> pesquisarImovelParametrosClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ControladorException;

	// ----- Metodo Para Carregar o Objeto ImovelMicromedicao
	// ----- Fl�vio Leonardo
	public Collection carregarImovelMicromedicao(Collection imoveisMicromedicao) throws ControladorException;

	public Categoria obterDescricoesCategoriaImovel(Imovel imovel) throws ControladorException;

	/**
	 * [UC0185] Obter VAlor por Categoria Author: Rafael Santos Data: 29/12/2005
	 * Rateia um daterminado valore entre as categorias do im�vel
	 * 
	 * @param colecaoCategorias
	 *            Cole��o de Categorias
	 * @param valor
	 *            Valor
	 * @return Cole��o com os valores por categorias
	 */
	public Collection obterValorPorCategoria(Collection<Categoria> colecaoCategorias, BigDecimal valor);

	/**
	 * Atualiza uma categoria no sistema
	 * 
	 * @author Roberta Costa
	 * @date 29/12/2005
	 * @param categoria
	 *            Categoria a ser atualizada
	 */
	public void atualizarCategoria(Categoria categoria) throws ControladorException;

	/**
	 * Insere uma categoria no sistema
	 * 
	 * @author Roberta Costa
	 * @date 04/01/2006
	 * @param categoria
	 *            Categoria a ser inserida
	 */
	public Integer inserirCategoria(Categoria categoria) throws ControladorException;

	/**
	 * Atualiza uma subcategoria no sistema
	 * 
	 * @author Fernanda Paiva
	 * @date 09/01/2006
	 * @param subcategoria
	 *            Subcategoria a ser atualizada
	 */
	public void atualizarSubcategoria(Subcategoria subcategoria, Subcategoria subcategoriaVelha) throws ControladorException;

	/**
	 * Pesquisar Imovel Outros Criterios
	 * 
	 * @author Rhawi Dantas
	 * @date 09/01/2006
	 * @author Eduardo Henrique
	 * @date 24/04/2008
	 */

	public Collection pesquisarImovelOutrosCriterios(FiltrarImovelOutrosCriteriosHelper filtrarImovelOutrosCriteriosHelper)
					throws ControladorException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 */
	public Collection pesquisarImovelSituacaoEspecialFaturamento(String valor,
					SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper) throws ControladorException;

	/**
	 * [UC0177] Informar Situacao Especial de Cobranca
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 */
	public Collection pesquisarImovelSituacaoEspecialCobranca(String valor, SituacaoEspecialCobrancaHelper situacaoEspecialFaturamentoHelper)
					throws ControladorException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 */
	public int validarMesAnoReferencia(SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper) throws ControladorException;

	/**
	 * Pesquisa uma cole��o de categorias
	 * 
	 * @return Cole��o de Categorias
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<Categoria> pesquisarCategoria() throws ControladorException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 16/01/2006
	 * @author Felipe Rosacruz
	 * @date 16/04/2014
	 * @param collectionFaturmentoSituaoHistorico
	 *            Altera��o para inserir o registro de transa��o
	 * @param usuarioLogado
	 * @param helperParaSeremRemovidos
	 */
	public void atualizarFaturamentoSituacaoTipo(Integer idFaturamentoTipo,
					Collection<SituacaoEspecialFaturamentoHelper> helperParaSeremInserirdos, Usuario usuarioLogado,
					Collection<SituacaoEspecialFaturamentoHelper> helperParaSeremRemovidos)
					throws ControladorException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 16/01/2006
	 */
	public void retirarSituacaoEspecialFaturamento(Collection<SituacaoEspecialFaturamentoHelper> colecaoSituacaoEspecialFaturamentoHelper,
					Usuario usuarioLogado)
					throws ControladorException;


	public Collection<Integer> pesquisarImoveisIds(Integer rotaId) throws ControladorException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [FS0005] - Verificar exist�ncia da conta
	 * 
	 * @author Anderson Italo
	 * @date 02/08/2011
	 * @return Object
	 * @exception ErroRepositorioException
	 */
	public Object pesquisarImovelIdComConta(Integer imovelId, Integer anoMesReferencia) throws ControladorException;

	/**
	 * Consulta se uma conta j� existe no hist�rico como parcelada, evitando assim a gera��o da
	 * conta de novo
	 * 
	 * @param imovelId
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarImovelIdComContaHistoricoParcelado(Integer imovelId, Integer anoMesReferencia) throws ControladorException;

	/**
	 * Verifica se o ClienteImovel existe na Cole��o do Cliente Imovel do Imovel
	 * Author: Rafael Santos Data: 01/02/2006
	 * 
	 * @param colecaoClienteImovel
	 *            Cole��o Cliente Imovel
	 * @param clienteImovel
	 *            Cliente Imovel
	 * @return true se o cliente imovel existe, false caso contr�rio
	 */
	public boolean verificarExistenciaClienteImovel(Collection colecaoClienteImovel, ClienteImovel clienteImovel);

	/**
	 * Verifica se ImovelSubCategoria existe na Cole��o de SubCategoria do
	 * Imovel Author: Rafael Santos Data: 01/02/2006
	 * 
	 * @param colecaoSubCategoria
	 *            Cole��o Imovel Sub Categoria
	 * @param imovelSubcategoria
	 *            ImovelSubcategoria
	 * @return true se a Imovel SubCategoria existe, false caso contr�rio
	 */
	public boolean verificarExistenciaImovelSubcategoria(Collection colecaoImovelSubCategoria, ImovelSubcategoria imovelSubcategoria);

	/**
	 * Metodo que verifica se ja tem um imovel ou um imovel economia com o
	 * numero do iptu passado caso a pessoa passe o idSetorComercial verifica
	 * apenas no mesmo municipio
	 * 
	 * @param numeroIptu
	 * @param idSetorComercial
	 * @throws ControladorException
	 */
	public void verificarExistenciaIPTU(String numeroIptu, Integer idSetorComercial) throws ControladorException;

	/**
	 * Metodo que verifica se ja tem um imovel ou um imovel economia com o
	 * numero do iptu passado caso a pessoa passe o idSetorComercial verifica
	 * apenas no mesmo municipio
	 * 
	 * @param numeroIptu
	 * @param idSetorComercial
	 * @throws ControladorException
	 */
	public void verificarExistenciaCELPE(String numeroCelp, Integer idSetorComercial) throws ControladorException;

	public Integer verificarExistenciaImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa o maior ano mes de referencia da tabela de faturamento grupo
	 * [UC0177] Informar Situacao Especial de Cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public Integer validarMesAnoReferenciaCobranca(SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper)
					throws ControladorException;

	/**
	 * Atualiza o id da cobran�a situa��o tipo da tabela im�vel com o id da
	 * situa��o escolhido pelo usuario
	 * [UC0177] Informar Situacao Especial de Cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public void atualizarCobrancaSituacaoTipo(Collection colecaoIdsImoveis, Integer idCobrancaTipo) throws ControladorException;

	/**
	 * Seta para null o id da cobran�a situa��o tipo da tabela im�vel
	 * [UC0177] Informar Situacao Especial de Cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public void retirarSituacaoEspecialCobranca(SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper, Usuario usuarioExclusao)
					throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos d�bitos de um im�vel
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * Obt�m as Op��es de Parcelamento do D�bito do Im�vel
	 * [SF0002] Obter Op��es Parcelamento
	 * Obt�m o perfil do im�vel
	 * 
	 * @author Roberta Costa
	 * @date 21/03/2006
	 * @param situacaoAguaId
	 * @param situacaoEsgotoId
	 * @return ImovelSituacao
	 */

	public ImovelSituacao obterSituacaoImovel(Integer situacaoAguaId, Integer situacaoEsgotoId) throws ControladorException;

	/**
	 * Inseri uma cole��o de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Este fluxo secund�rio tem como objetivo pesquisar o im�vel digitado pelo
	 * usu�rio
	 * [FS0008] - Verificar exist�ncia da matr�cula do im�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovelDigitado
	 * @return
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelDigitado(Integer idImovelDigitado) throws ControladorException;

	/**
	 * Inseri uma cole��o de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se a localidade informada � a mesma do im�vel informado
	 * [FS0009] - Verificar localidade da matr�cula do im�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idLocalidadeInformada
	 * @param imovelInformado
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarLocalidadeMatriculaImovel(String idLocalidadeInformada, Imovel imovelInformado) throws ControladorException;

	/**
	 * Inseri uma cole��o de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se o usu�rio informou o im�vel ou o cliente, n�o pode existir os
	 * doi nem nenhum
	 * [FS0010] Verificar preenchimento do im�vel e do cliente
	 * 
	 * @author Pedro Alexandre
	 * @date 24/03/2006
	 * @param idImovel
	 * @param idCliente
	 * @throws ControladorException
	 */
	public void verificarPreeenchimentoImovelECliente(String idImovel, String idCliente) throws ControladorException;

	/**
	 * [UC0224] Inserir Situa��o do im�vel
	 * Verifica se o usu�rio informou o tipo da situa��o do im�vel
	 * [FS0001] Verificar preenchimento do im�vel
	 * 
	 * @author R�mulo Aur�lio
	 * @date 29/03/2006
	 * @param idImovelSituacaoTipo
	 * @param idLigacaoAguaSituacao
	 * @param idLigacaoEsgotoSituacao
	 * @throws ControladorException
	 */
	public Integer inserirSituacaoImovel(String idImovelSituacaoTipo, String idLigacaoAguaSituacao, String idLigacaoEsgotoSituacao)
					throws ControladorException;

	/**
	 * Obt�m o indicador de exist�ncia de hidr�metro para o im�vel, caso exista
	 * retorna 1(um) indicando SIM caso contr�rio retorna 2(dois) indicando N�O
	 * [UC0307] Obter Indicador de Exist�ncia de Hidr�metro no Im�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 18/04/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public int obterIndicadorExistenciaHidrometroImovel(Integer idImovel) throws ControladorException;

	/**
	 * Obt�m a principal categoria do im�vel
	 * [UC0306] Obter Principal Categoria do Im�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 18/04/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Categoria obterPrincipalCategoriaImovel(Integer idImovel) throws ControladorException;

	/**
	 * Atualiza campos de imovel na execu��o de ordem Servi�o
	 * 
	 * @author Leandro Cavalcanti
	 * @throws ControladorException
	 */
	public void atualizarImovelExecucaoOrdemServicoLigacaoAgua(Imovel imovel, LigacaoAguaSituacao situacaoAgua) throws ControladorException;

	/**
	 * Atualiza campos de imovel na execu��o de ordem Servi�o
	 * 
	 * @author Leandro Cavalcanti
	 * @throws ControladorException
	 */
	public void atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(Imovel imovel, LigacaoEsgotoSituacao situacaoEsgoto)
					throws ControladorException;

	/**
	 * Gerar Relat�rio de Im�veis Outros Crit�rios
	 * 
	 * @author Rafael Corr�a
	 * @date 25/07/2006
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
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
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal) throws ControladorException;

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
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal) throws ControladorException;

	/**
	 * O m�todo abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necess�rios para a cria��o da inscri��o do im�vel para exibi��o.
	 * Aqui � montada a inscri��o formatada e com os pontos, caso o parametro "inscricaoFormatada"
	 * seja true. Caso seja false, a inscri��o nao vir� formatada.
	 */
	public String pesquisarInscricaoImovel(Integer idImovel, boolean inscricaoFormatada) throws ControladorException;

	/**
	 * Gerar Relat�rio de Dados das Economias do Im�vel
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioDadosEconomiaImovel(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto,
					String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto, String intervaloValorPercentualEsgotoInicial,
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
					String idUnidadeNegocio, String segmentoInicial, String segmentoFinal, String rotaInicial, String rotaFinal,
 String sequencialRotaInicial, String sequencialRotaFinal,
					String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ControladorException;

	/**
	 * Esse m�todo � usado para fzazer uma pesquisa na tabela im�vel e confirmar
	 * se o id passado � de um im�vel exclu�do(idExclusao)
	 * Fl�vio Cordeiro
	 * 
	 * @throws ErroRepositorioException
	 */

	public Boolean confirmarImovelExcluido(Integer idImovel);

	/**
	 * Permite pesquisar entidades beneficentes
	 * [UC0389] Inserir Autoriza��o para Doa��o Mensal
	 * 
	 * @author C�sar Ara�jo
	 * @date 30/08/2006
	 * @param idEntidadeBeneficente
	 *            - C�digo da entidade beneficente
	 * @return Collection<EntidadeBeneficente> - Cole��o de entidades beneficentes
	 * @throws ControladorException
	 */
	public Collection<EntidadeBeneficente> pesquisarEntidadeBeneficente(Integer idEntidadeBeneficente) throws ControladorException;

	/**
	 * Permite pesquisar im�veis doa��o
	 * [UC0389] Inserir Autoriza��o para Doa��o Mensal
	 * 
	 * @author C�sar Ara�jo
	 * @date 30/08/2006
	 * @param idImovelDoacao
	 *            - C�digo do im�vel doa��o
	 * @return Collection<ImovelDoacao> - Cole��o de im�veis doa��o
	 * @throws ControladorException
	 */
	public Collection<ImovelDoacao> pesquisarImovelDoacao(FiltroImovelDoacao filtroImovelDoacao) throws ControladorException;

	/**
	 * Permite verificar se existe um determinado im�vel doa��o
	 * [UC0389] Inserir Autoriza��o para Doa��o Mensal
	 * 
	 * @author C�sar Ara�jo
	 * @date 30/08/2006
	 * @param idImovel
	 *            - C�digo do im�vel
	 * @param idEntidadeBeneficente
	 *            - C�digo da entidade beneficente
	 * @return ImovelDoacao - Retorna um im�vel doa��o caso a combina��o de im�vel e entidade
	 *         beneficente exista.
	 * @throws ControladorException
	 */
	public ImovelDoacao verificarExistenciaImovelDoacao(Integer idImovel, Integer idEntidadeBeneficente) throws ControladorException;

	/**
	 * Permite atualizar as informa��es do im�vel doa��o
	 * [UC0390] Manter Autoriza��o para Doa��o Mensal
	 * 
	 * @author C�sar Ara�jo,Pedro Alexandre
	 * @date 30/08/2006, 17/11/2006
	 * @param imovelDoacao
	 *            - C�digo do im�veo doa��o
	 * @param usuarioLogado
	 *            - Usu�rio logado no sistema
	 * @throws ControladorException
	 */
	public void atualizarImovelDoacao(ImovelDoacao imovelDoacao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Pesquisa um im�vel a partir do seu id.Retorna os dados que comp�em a
	 * inscri��o e o endere�o do mesmo
	 * 
	 * @author Raphael Rossiter
	 * @date 01/08/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelRegistroAtendimento(Integer idImovel) throws ControladorException;

	/**
	 * Verifica a exist�ncia do hid�metro de acordo com tipo de medi��o
	 * informado (MedicaoTipo.LIGACAO_AGUA ou MedicaoTipo.POCO).
	 * [UC0368] Atualizar Instala��o do Hidr�metro
	 * [FS0003] - Validar exist�ncia do hidr�metro
	 * 
	 * @author lms
	 * @created 24/07/2006
	 * @throws ControladorException
	 */
	public void validarExistenciaHidrometro(Imovel imovel, Integer medicaoTipo) throws ControladorException;

	/**
	 * Verifica a exist�ncia da matr�cula do im�vel. Caso exista, verifica se o
	 * im�vel est� ativo.
	 * [UC0368] Atualizar Instala��o do Hidr�metro
	 * [FS0001] - Verificar a exist�ncia da matr�cula do im�vel [FS0002] -
	 * Verificar a situa��o do im�vel
	 * 
	 * @author lms
	 * @created 19/07/2006
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelSituacaoAtiva(FiltroImovel filtroImovel) throws ControladorException;

	/**
	 * Faz o controle de concorrencia do imovel
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	public void verificarImovelControleConcorrencia(Imovel imovel) throws ControladorException;

	/**
	 * Consulta os Dados Cadastrais do Imovel
	 * 
	 * @author Rafael Santos
	 * @date 07/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarImovelDadosCadastrais(Integer idImovel) throws ControladorException;

	/**
	 * Consulta os Clientes do Imovel [UC0472] Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 07/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarClientesImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa a cole��o de categorias do imovel [UC0472] Consultar Imovel
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarCategoriasImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0475] Consultar Perfil Imovel
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idImovel
	 * @return Perfil do Im�vel
	 * @exception ControladorException
	 */
	public ImovelPerfil obterImovelPerfil(Integer idImovel) throws ControladorException;

	/**
	 * Consulta os Dados Complementares do Imovel
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarImovelDadosComplementares(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa a cole��o de vencimento alternativos do imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarVencimentoAlternativoImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa a cole��o de Debitos Automaticos do imovel [UC0473] Consultar
	 * Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarDebitosAutomaticosImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa a cole��o de Faturamento Situa��o Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarFaturamentosSituacaoHistorico(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa a cole��o de Faturamento Situa��o Historico do Imovel
	 * UC0069-ManterDadosTarifaSocial
	 * 
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarFaturamentoSituacaoHistorico(Integer idImovel) throws ControladorException;

	/**
	 * // * Pesquisa a cole��o de Faturamento Situa��o Historico do Imovel Ativos [UC0156]
	 * // *
	 * // * @author Saulo Lima
	 * // * @date 22/08/2008
	 * // * @param Integer
	 * // * idFaturamentoSituacaoTipo
	 * // *
	 * // * @return Collection
	 * // * FaruramentoSituacaoHistorico
	 * // *
	 * // * @exception ControladorException
	 * //
	 */
	// public Collection pesquisarFaturamentosSituacaoHistoricoAtivos(Integer
	// idFaturamentoSituacaoTipo)
	// throws ControladorException;

	/**
	 * Pesquisa a cole��o de Faturamento Situa��o Historico do Imovel Ativos [UC0156]
	 * 
	 * @author Saulo Lima
	 * @date 22/08/2008
	 * @param Integer
	 *            idFaturamentoSituacaoTipo
	 * @return Collection
	 *         FaruramentoSituacaoHistorico
	 * @exception ControladorException
	 */
	public Collection pesquisarFaturamentosSituacaoHistoricoAtivos(SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper)
					throws ControladorException;

	/**
	 * Pesquisa a cole��o de cobran�as Situa��o Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarCobrancasSituacaoHistorico(Integer idImovel) throws ControladorException;

	/**
	 * Consutlar os Dados de Analise da Medi��o e Consumo do Imovel [UC0473]
	 * Consultar Im�vel
	 * 
	 * @author Rafael Santos
	 * @date 12/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarImovelAnaliseMedicaoConsumo(Integer idImovel) throws ControladorException;

	/**
	 * Consultar os Dados do Historico de Faturamento
	 * [UC0473] Consultar Im�vel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarImovelHistoricoFaturamento(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisas todas as Situa��es Especiais de Cobran�a ativas de um determinado im�vel
	 * 
	 * @author Saulo Lima
	 * @date 09/09/2013
	 * @param idImovel
	 * @return Collection<ImovelCobrancaSituacao>
	 * @throws ControladorException
	 */
	public Collection<ImovelCobrancaSituacao> pesquisarImovelCobrancaSituacaoAtivas(Integer idImovel) throws ControladorException;

	/**
	 * Consultar o cliente usu�rio do Imovel
	 * [UC0473] Consultar Im�vel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public String consultarClienteUsuarioImovel(Integer idImovel) throws ControladorException;

	/**
	 * Consultar o cliente usu�rio do Imovel
	 * [UC 0275] Gerar Resumo Ligacao Agua
	 * 
	 * @author Bruno Barros
	 * @date 27/04/2007
	 * @param imovel
	 * @return Cliente
	 * @throws ControladorException
	 */
	public Cliente consultarClienteUsuarioImovel(Imovel imovel) throws ControladorException;

	/**
	 * Consultar as contas do Imovel
	 * [UC0473] Consultar Im�vel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author eduardo henrique
	 * @date 02/10/2008
	 *       Altera��o para op��o de Ordena��o da Refer�ncias das Contas
	 * @author Saulo Lima
	 * @date 10/08/2009
	 *       Novo atributo preenchido na colecao de contas: valorImposto
	 * @param idImovel
	 * @param ordemAscendente
	 *            true -> ASC ; false -> DESC
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection<Conta> consultarContasImovel(Integer idImovel, boolean ordemAscendente) throws ControladorException;

	/**
	 * Consultar as contas Historicos do Imovel [UC0473] Consultar Im�vel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author Saulo Lima
	 * @date 10/08/2009
	 *       Novo atributo preenchido na colecao de ContaHistorico: valorImposto
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection<ContaHistorico> consultarContasHistoricosImovel(Integer idImovel) throws ControladorException;

	/**
	 * Registrar leituras e anormalidades
	 * 
	 * @author S�vio Luiz
	 * @date 12/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public void atualizarImovelLeituraAnormalidade(Map<Integer, MedicaoHistorico> mapAtualizarLeituraAnormalidadeImovel, Date dataAtual)
					throws ControladorException;

	/**
	 * Consultar os dados de parcelamentos do Imovel
	 * [UC0473] Consultar Im�vel
	 * 
	 * @author Rafael Santos
	 * @date 20/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarParcelamentosDebitosImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 27/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteUsuarioImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0146] Manter Conta
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 11/08/2011
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteResponsavelImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscaliza��o [SB0002] Atualizar
	 * Im�vel/Liga��o de �gua
	 * 
	 * @date 14/11/2006
	 * @author S�vio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelLigacaoAguaEsgoto(Integer idImovel, Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao,
					short indicadorLigacaoAtualizada, short indiacadorConsumoFixado, Integer consumoDefinido, short indicadorVolumeFixado)
					throws ControladorException;

	/**
	 * Respons�vel pela inser��o de um Im�vel Doa��o
	 * [UC0389] - Inserir Imovel Doacao
	 * 
	 * @author C�sar Ara�jo, Pedro Alexandre
	 * @date 03/08/2006, 16/11/2006
	 * @param imovelDoacao
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirImovelDoacaoRetorno(ImovelDoacao imovelDoacao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @date 14/11/2006
	 * @author S�vio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public Integer recuperarIdConsumoTarifa(Integer idImovel) throws ControladorException;

	/**
	 * Filtrar o Imovel pelos parametros informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovel(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometroHistInst, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelManterVinculo, boolean pesquisarImovelCondominio,
					Integer numeroPagina) throws ControladorException;

	/**
	 * Pesquisa a quantidade de Imoveis
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 16/12/2008
	 *       Altera��o no filtro de Num. de Hidr�metro do Pesquisar Im�vel.
	 * @param idImovel
	 * @param numeroHidrometroImvoel
	 *            TODO
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeImovel(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometroHistInst, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelManterVinculo, boolean pesquisarImovelCondominio,
					String numeroHidrometroImovel, String numeroImovel)
					throws ControladorException;

	/**
	 * Pesquisa o Imovel pelos parametros informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Corre��o no m�todo para desativa��o definitiva da Classe filtro e
	 *       inclusao do numero do imovel na consulta.
	 * @param idImovel
	 * @param numeroImovel
	 *            TODO
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelInscricao(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometro, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelCondominio, String numeroImovel, Integer numeroPagina)
					throws ControladorException;

	/**
	 * [UC0490] Informar Ocorrencia de Cadastro e/ou Anormalidade de Elo
	 * 
	 * @author Tiago Moreno
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 21/07/2008 adi��o dos atributos de funcion�rio em OcorrenciaCadastro e AnormalidadeElo
	 * @param idImovel
	 * @param idOcorrenciaCadastro
	 * @param idAnormalidadeElo
	 * @param dataOcorrenciaCadastro
	 * @param dataAnormalidadeElo
	 * @param uploadPictureCadastro
	 * @param uploadPictureAnormalidade
	 * @param idFuncionarioOcorrencia
	 * @param idFuncionarioAnormalidade
	 * @param uploadPictureImovel
	 * @return
	 * @throws ControladorException
	 */
	public void informarOcorrenciaCadastroAnormalidadeElo(String idImovel, String idOcorrenciaCadastro, String idAnormalidadeElo,
					String dataOcorrenciaCadastro, String dataAnormalidadeElo, byte[] uploadPictureCadastro,
					byte[] uploadPictureAnormalidade, String idFuncionarioOcorrencia, String idFuncionarioAnormalidade,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscaliza��o
	 * Recupera o id da situa��o da liga��o de agua
	 * 
	 * @author S�vio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdLigacaoAguaSituacao(Integer idImovel) throws ControladorException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscaliza��o
	 * Recupera o id da situa��o da liga��o de esgoto
	 * 
	 * @author S�vio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdLigacaoEsgotoSituacao(Integer idImovel) throws ControladorException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscaliza��o
	 * Recupera o id da situa��o da ligacao de agua
	 * 
	 * @author S�vio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarTarifaImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0490] Informar Situa��o de Cobran�a
	 * 
	 * @author Tiago Moreno
	 * @date 09/12/2006
	 * @param imovel
	 * @param situacaoCobranca
	 * @param cliente
	 * @param dataImplanta��o
	 * @param anoMesInicio
	 * @param anoMesFim
	 * @return
	 * @throws ControladorException
	 */
	public void inserirImovelSitucaoCobranca(Imovel imovel, CobrancaSituacao cobrancaSituacao, Cliente cliente, Cliente clienteEscritorio,
					Cliente clienteAdvogado, Date dataImplantacao, Integer anoMesInicio, Integer anoMesFim, Usuario usuarioLogado)
					throws ControladorException;

	public void retirarImovelSitucaoCobranca(String[] idRemocao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Pesquisa os im�veis do cliente de acordo com o tipo de rela��o
	 * [UC0251] Gerar Atividade de A��o de Cobran�a [SB0001] Gerar Atividade de
	 * A��o de Cobran�a para os Im�veis do Cliente
	 * 
	 * @author Leonardo Vieira
	 * @created 12/12/2006
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisClientesRelacao(Cliente cliente, ClienteRelacaoTipo relacaoClienteImovel, Integer numeroInicial,
					Integer idSetorComercial) throws ControladorException;

	public Integer pesquisarExistenciaImovelSubCategoria(Integer idImovel, Integer idCategoria) throws ControladorException;

	public Collection pesquisarImoveisPorRota(Integer idRota) throws ControladorException;

	/**
	 * [UC0378] Associar Tarifa de Consumo a Im�veis
	 * 
	 * @author R�mulo Aur�lio
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
	 * @throws ControladorException
	 */

	public Collection pesquisarImoveisTarifaConsumo(String idLocalidadeInicial, String idLocalidadeFinal,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String quadraInicial, String quadraFinal,
					String loteInicial, String loteFinal, String subLoteInicial, String subLoteFinal, String idTarifaAnterior,
					String idsCategorias, String idsSubcategorias)
					throws ControladorException;

	/**
	 * Atualiza a tarifa de consumo de um ou mais imoveis
	 * [UC0378] Associar Tarifa de Consumo a Im�veis
	 * 
	 * @author R�mulo Aurelio
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
	 * @throws ControladorException
	 */

	public void atualizarImoveisTarifaConsumo(String matricula, String tarifaAtual, String tarifaEspecial, Date dataValidadeTarifaEspecial,
					Collection colecaoImoveis) throws ControladorException;

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Atualiza o perfil do im�vel para o perfil normal
	 * 
	 * @date 04/01/2007
	 * @author Rafael Corr�a
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelPerfilNormal(Integer idImovel, boolean manter) throws ControladorException;

	/**
	 * [UC0490] - Informar Situa��o de Cobran�a
	 * Pesquisa o im�vel com a situa��o da liga��o de �gua e a de esgoto
	 * 
	 * @date 13/01/2007
	 * @author Rafael Corr�a
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelComSituacaoAguaEsgoto(Integer idImovel) throws ControladorException;

	public Collection pesquisarImoveisPorRotaComPaginacao(Integer idRota, int numeroInicial) throws ControladorException;

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0006] - Verificar n�mero de IPTU
	 * Verifica se j� existe outro im�vel ou economia com o mesmo n�mero de IPTU
	 * no mesmo munic�pio
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corr�a
	 * @throws ControladorException
	 */
	public Integer verificarNumeroIptu(BigDecimal numeroIptu, Integer idImovel, Integer idImovelEconomia, Integer idMunicipio)
					throws ControladorException;

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0007] - Verificar n�mero de contrato da companhia de energia el�trica
	 * Verifica se j� existe outro im�vel ou economia com o mesmo n�mero de
	 * contrato da companhia el�trica
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corr�a
	 * @throws ControladorException
	 */
	public Integer verificarNumeroCompanhiaEletrica(Long numeroCompanhiaEletrica, Integer idImovel, Integer idImovelEconomia)
					throws ControladorException;

	/**
	 * Usado pelo Pesquisar Imovel Retorno o Imovel, com o Nome do Cliente,
	 * Matricula e Endere�o
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 */
	/*
	 * public Collection pesquisarImovelInscricaoNew(String idImovel,
	 * String idLocalidade, String codigoSetorComercial,
	 * String numeroQuadra, String lote, String subLote,
	 * String codigoCliente, String idMunicipio, String cep,
	 * String idBairro, String idLogradouro,
	 * boolean pesquisarImovelCondominio, Integer numeroPagina)
	 * throws ControladorException ;
	 */

	/**
	 * Obt�m o quantidade de economias da subCategoria por imovel
	 * 
	 * @param idImovel
	 *            O identificador do im�vel
	 * @return Cole��o de SubCategorias
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Collection<Subcategoria> obterQuantidadeEconomiasSubCategoria(Integer idImovel) throws ControladorException;

	/**
	 * @date 21/02/2007
	 * @author Vivianne Sousa
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovel(Integer idImovel) throws ControladorException;

	/**
	 * Atualiza logradouroCep de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ControladorException;

	/**
	 * Atualiza logradouroBairro de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException;

	/**
	 * [UC0302] Gerar D�bitos a Cobrar de Acr�scimos por Impontualidade
	 * Pequisa o identificador de cobranca de acr�scimo pro impontualidade
	 * para o im�vel do cliente respons�vel.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/02/2007
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Short obterIndicadorGeracaoAcrescimosClienteImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscaliza��o
	 * Obter o consumo m�dio como n�o medido para o im�vel passado
	 * 
	 * @author Raphael Rossiter
	 * @date 06/03/2007
	 */
	public Integer obterConsumoMedioNaoMedidoImovel(Imovel imovel) throws ControladorException;

	/**
	 * Obter a situa��o de cobran�a para o im�vel passado
	 * 
	 * @author Vivianne Sousa
	 * @date 07/03/2007
	 */
	public String obterSituacaoCobrancaImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa cole��o de im�veis
	 * 
	 * @author Ana Maria
	 * @date 16/03/206
	 */
	public Collection pesquisarColecaoImovel(FiltrarImovelInserirManterContaHelper filtro) throws ControladorException;

	/**
	 * Pesquisa cole��o de im�veis do cliente
	 * 
	 * @author Ana Maria
	 * @date 16/03/206
	 */
	public Collection pesquisarColecaoImovelCliente(Integer codigoCliente, Integer relacaoTipo) throws ControladorException;

	/**
	 * Seleciona a Subcategoria principal de uma categoria
	 * 
	 * @author Bruno Barros
	 * @date 16/04/2007
	 * @param idCategoria
	 * @return
	 * @throws ControladorException
	 */
	public ImovelSubcategoria obterPrincipalSubcategoria(Integer idCategoria, Integer idImovel) throws ControladorException;

	/**
	 * [UC0150] - Retificar Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 16/04/2007
	 */
	public Collection obterQuantidadeEconomiasContaCategoriaPorSubcategoria(Conta conta) throws ControladorException;

	/**
	 * [UC0150] - Retificar Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 16/04/2007
	 */
	public Collection obterQuantidadeEconomiasContaCategoriaPorSubcategoria(Integer conta) throws ControladorException;

	/**
	 * Autor: Raphael Rossiter
	 * Data: 18/04/2007
	 * 
	 * @return Quantidade de subcategorias
	 * @throws ControladorException
	 */
	public Object pesquisarObterQuantidadeSubcategoria() throws ControladorException;

	/**
	 * Pesquisa todos os ids do perfil do im�vel.
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsPerfilImovel() throws ControladorException;

	/**
	 * [UC0591] - Gerar Relat�rio de Clientes Especiais
	 * Pesquisas os im�veis de acordo com os par�metros da pesquisa
	 * 
	 * @author Rafael Corr�a
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
					String idConsumoAnormalidade, String consumoAnormalidade, String[] idsClienteTipoEspecial) throws ControladorException;

	/**
	 * Pesquisar Grupo do Imovel
	 * 
	 * @author Flavio Cordeiro
	 * @date 18/05/2007
	 * @param idImovel
	 * @return
	 */
	public FaturamentoGrupo pesquisarGrupoImovel(Integer idImovel);

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situa��o da liga��o de esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoEsgotoSituacao
	 * @throws ErroRepositorioException
	 */
	public LigacaoEsgotoSituacao pesquisarLigacaoEsgotoSituacao(Integer idImovel) throws ControladorException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situa��o da liga��o de agua
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoAguaSituacao
	 * @throws ErroRepositorioException
	 */
	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer idImovel) throws ControladorException;

	/**
	 * Gerar Relat�rio de Im�veis Outros Crit�rios
	 * 
	 * @author Rafael Corr�a
	 * @date 25/07/2006
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
					String segmentoFinal, String subloteInicial, String subloteFinal, Short tipoEmpresa, String indicadorOrdenacao,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ControladorException;

	/**
	 * Gerar Relat�rio de Im�veis Outros Crit�rios
	 * 
	 * @author Rafael Corr�a
	 * @date 25/07/2006
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioCadastroConsumidoresInscricao(String idImovelCondominio, String idImovelPrincipal,
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
					throws ControladorException;

	/**
	 * Filtra o Pagamento Historico pelo seu id carregando os dados necess�rios
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author K�ssia Albuquerque
	 * @date 12/07/2007
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoPeloId(Integer idPagamentoHistorico) throws ControladorException;

	/**
	 * Filtra o Pagamento pelo seu id carregando os dados necess�rios
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author K�ssia Albuquerque
	 * @date 12/07/2007
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoPeloId(Integer idPagamento) throws ControladorException;

	/**
	 * [UC0549] Consultar Dados de um Pagamento
	 * 
	 * @author Kassia Albuquerque
	 * @date 05/07/2007
	 * @throws ErroRepositorioException
	 */
	public Cliente obterDescricaoIdCliente(Integer idImovel) throws ControladorException;

	/**
	 * [UC0549] Consultar Dados de um Pagamento
	 * 
	 * @author Kassia Albuquerque
	 * @date 09/07/2007
	 * @throws ErroRepositorioException
	 */
	public String pesquisarNomeAgenteArrecadador(Integer idPagamentoHistorico) throws ControladorException;

	/**
	 * Retorna o cep do im�vel
	 * 
	 * @param imovel
	 * @return Descri��o do retorno
	 * @exception ControladorException
	 */
	public Cep pesquisarCepImovel(Imovel imovel) throws ControladorException;

	/**
	 * Obt�m a o 117� caracter de uma String
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/07/2007
	 * @return String
	 * @throws ControladorException
	 */

	public String pesquisarCaracterRetorno(Integer idConteudoRegistro) throws ControladorException;

	/**
	 * [UC0623] - GERAR RESUMO DE METAS EXECUTDO NO M�S(CAERN)
	 * 
	 * @author S�vio Luiz
	 * @date 08/08/2007
	 */
	public ImovelSubcategoria obterPrincipalSubcategoriaComCodigoGrupo(Integer idCategoria, Integer idImovel) throws ControladorException;

	/**
	 * Obt�m a principal categoria da Conta
	 * [UC0000] Obter Principal Categoria da Conta
	 * 
	 * @author Ivan S�rgio
	 * @date 08/08/2007
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */
	public Categoria obterPrincipalCategoriaConta(Integer idConta) throws ControladorException;

	/**
	 * O m�todo abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necess�rios para a cria��o da inscri��o do im�vel para exibi��o.
	 * aki � montada a inscri��o
	 */
	public Object[] pesquisarLocalidadeSetorImovel(Integer idImovel) throws ControladorException;

	/**
	 * @author S�vio Luiz
	 * @date 24/08/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarSequencialRota(Integer idImovel) throws ControladorException;

	/**
	 * [UC0541] Emitir 2 Via de Conta Internet
	 * 
	 * @author Vivianne Sousa
	 * @date 02/09/2007
	 * @throws ErroRepositorioException
	 */

	public Imovel pesquisarDadosImovel(Integer idImovel) throws ControladorException;

	/**
	 * Permite Pesquisar as categorias do Im�vel [UC0394] Gerar D�bitos a Cobrar
	 * de Doa��es
	 * 
	 * @author C�sar Ara�jo
	 * @date 10/09/2006
	 * @param Imovel
	 *            imovel - objeto imovel
	 * @return Collection<Categoria> - Cole��o de categorias
	 * @throws ErroRepositorioException
	 */

	public Collection<Categoria> pesquisarCategoriasImovel(Imovel imovel) throws ControladorException;

	/**
	 * O m�todo abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necess�rios para a cria��o da inscri��o ,de rota e endere�o para exibi��o.
	 * 
	 * @author Vivianne Sousa
	 * @date 06/11/2007
	 */

	public Collection pesquisarDadosImovel(String idsImovel) throws ControladorException;

	/**
	 * Pesquisa os im�veis do cliente de acordo com o tipo de rela��o
	 * [UC0251] Gerar Atividade de A��o de Cobran�a [SB0001] Gerar Atividade de
	 * A��o de Cobran�a para os Im�veis do Cliente
	 * 
	 * @author S�vio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImoveisClientesRelacao(Collection idsCliente, Integer numeroInicial, Integer idSetorComercial)
					throws ControladorException;

	/**
	 * Pesquisa a cole��o de situa��es de cobran�a do imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Vitor Hora
	 * @date 15/07/2008
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarSituacoesCobrancaImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa a cole��o de ocorr�ncias a do imovel [UC0473]
	 * Consultar Imovel Cadastro Ocorr�ncia
	 * 
	 * @author Vitor Hora
	 * @date 17/07/2008
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection<ImovelCadastroOcorrencia> pesquisarOcorrenciasCadastro(String idImovel) throws ControladorException;

	/**
	 * Pesquisa a cole��o de ocorr�ncias a do imovel [UC0473]
	 * Consultar Imovel Elo Anormalidade
	 * 
	 * @author Vitor Hora
	 * @date 18/07/2008
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection<ImovelCadastroOcorrencia> pesquisarEloAnormalidade(String idImovel) throws ControladorException;

	/**
	 * Pesquisa todos os im�veis de determinada rota;
	 * 
	 * @author eduardo henrique
	 * @date 15/10/2008
	 * @param idRota
	 * @param anoMesFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Imovel> pesquisarImoveisPorRotaComLocalidade(Integer idRota) throws ControladorException;

	/**
	 * M�todo respons�vel por obter o propriet�rio do im�vel.
	 * 
	 * @author Virg�nia Melo
	 * @date 04/06/2009
	 * @param idImovel
	 * @return ClienteImovel
	 */
	public ClienteImovel pesquisarClienteProprietarioImovel(Integer idImovel) throws ControladorException;

	public Integer verificarExistenciaImovelParaCliente(Integer idImovel) throws ControladorException;

	/**
	 * Atualiza a situa��o de cobran�a do im�vel
	 */
	public void atualizarSituacaoCobrancaImovel(Integer idSituacaoCobranca, Integer idImovel) throws ControladorException;

	/**
	 * @author eduardo henrique
	 * @date 17/08/2009
	 *       Retorna uma inst�ncia de Imovel, com todos os atributos preenchidos
	 * @param idImovel
	 * @return Imovel
	 * @throws ControladorException
	 */
	public Imovel consultarImovel(Integer idImovel) throws ControladorException;

	/**
	 * @param idRota
	 * @param idCriterioCobranca
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Object[]> pesquisarImoveisPorRotaCriterioCobranca(Integer idRota, Integer idCriterioCobranca)
					throws ControladorException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer consultarClienteRelacaoTipoPorImovel(Integer idImovel, Short idClienteRelacaoTipo) throws ControladorException;

	public Collection<Object[]> pesquisarImoveisPorSetorComercialCriterioCobranca(Integer idSetorComercial, Integer idCriterioCobranca,
					Date dataCortado, Integer idGrupo, Integer[] idsTipoDocumentoAIgnorar, Integer idAcaoCobranca,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando)
					throws ControladorException;

	/**
	 * Obtem o ConsumoFaixaAreaCategoria da categoria com a area informada
	 * 
	 * @author isilva
	 * @date 21/01/2011
	 * @param idCategoria
	 * @param area
	 * @return
	 * @throws ControladorException
	 */
	public ConsumoFaixaAreaCategoria pesquisarConsumoFaixaAreaCategoriaPorCategoriaArea(Integer idCategoria, Integer area)
					throws ControladorException;

	/**
	 * [FS0003] - Verificar se im�vel j� esta suprimido Judicialmente.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @return
	 * @throws ControladorException
	 */
	public Boolean verificarImovelSuprimidoJudicial(Integer idImovel) throws ControladorException;

	/**
	 * [FS0003] - Suprimido Judicialmente a Liga��o de Esgoto de um Im�vel.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @param idImovel
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public Integer efetuarSuprimirImovelEsgotoJudicial(Integer idImovel, Usuario usuario) throws ControladorException;

	/**
	 * [FS0003] - Religacao Suprimido Judicialmente a Liga��o de Esgoto de um Im�vel.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @param idImovel
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public void efetuarReligacaoSuprimirImovelEsgotoJudicial(Integer idImovel, Usuario usuario) throws ControladorException;

	/**
	 * @author Ailton Sousa
	 * @date 11/02/2011
	 *       Pesquisa uma cole��o de im�veis com uma query especifica
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param rota
	 * @param segmento
	 * @param lote
	 * @param sublote
	 * @return Description of the Return Value
	 * @exception ControladorException
	 */
	public Collection pesquisarImovel(Integer idLocalidade, Integer idSetorComercial, Integer idRota, Short segmento, Short lote,
					Short sublote) throws ControladorException;

	/**
	 * Inserir Vencimento Alternativo
	 * 
	 * @author Hebert Falc�o
	 * @created 18/02/2010
	 * @param vencimentoAlternativo
	 * @param imovel
	 * @param cliente
	 * @param novoDiaVencimento
	 * @param usuario
	 * @exception ControladorException
	 */
	public void inserirVencimentoAlternativo(VencimentoAlternativo vencimentoAlternativo, Imovel imovel, Cliente cliente,
					Short novoDiaVencimento, Usuario usuario) throws ControladorException;

	/**
	 * Excluir Vencimento Alternativo
	 * 
	 * @author Hebert Falc�o
	 * @created 18/02/2010
	 * @param vencimentoAlternativo
	 * @param imovel
	 * @param usuario
	 * @exception ControladorException
	 */
	public void excluirVencimentoAlternativo(VencimentoAlternativo vencimentoAlternativo, Imovel imovel, Usuario usuario)
					throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio Boletim de Cadastro
	 * 
	 * @author Anderson Italo
	 * @date 25/04/2011
	 *       Obter Total dos Im�veis pelos parametros informados
	 */
	public Integer pesquisarTotalRegistrosRelatorioBoletimCadastro(FiltrarRelatorioBoletimCadastroHelper filtro)
					throws ControladorException;

	/**
	 * Obterm o Funcion�rio que executou a opera��o em conta
	 * 
	 * @author isilva
	 * @date 02/05/2011
	 * @param idConta
	 * @param tipo
	 * @return
	 * @throws ControladorException
	 */
	public Funcionario obterFuncionarioExecutouOperacaoEmConta(Integer idConta, String tipo) throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio Im�veis por Endere�o
	 * Obter Total dos Im�veis pelos parametros informados
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
					String consumoFixadoEsgotoPocoFinal) throws ControladorException;

	/**
	 * [UC0591] - Gerar Relat�rio de Clientes Especiais
	 * Pesquisas Total de Registros de acordo com os par�metros da pesquisa
	 * 
	 * @author Anderson Italo
	 * @date 11/07/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioClientesEspeciais(FiltrarRelatorioClientesEspeciaisHelper filtro)
					throws ControladorException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0003] � Obter Grupo do Im�vel
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
					throws ControladorException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * Verifica se o indicador de debito autom�tico do imovel est� ativo.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 */
	public boolean isIndicadorDebitoAutomaticoImovel(Integer idImovel) throws ControladorException;

	/**
	 * Retorna true caso exista imoveis para a cole�ao de rotas recebidas.
	 * 
	 * @author Ailton Sousa
	 * @date 15/09/2011
	 * @param colecaoRotas
	 * @return
	 * @throws ControladorException
	 */
	public boolean isImovelPorColecaoRotas(Collection<Rota> colecaoRotas) throws ControladorException;

	/**
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public String pesquisarNomeImovel(Integer idImovel) throws ControladorException;

	/**
	 * Obtem os dados da Subcategoria do Imovel para o Relatorio de Dados
	 * Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioDadosEconomiasImovelSubcategoria(String idImovel) throws ControladorException;

	/**
	 * Retorna o ImovelSubcategoria com a maior quantidade de economia.
	 * 
	 * @author Ailton Sousa
	 * @date 28/12/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public ImovelSubcategoria pesquisarImovelSubcategoriaComMaiorQuantidadeEconomia(Integer idImovel) throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudan�a de Rota da Quadra
	 * M�todo que obt�m o total de im�veis com rota igual � rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisRotaIgualAnteriorQuadra(Integer idQuadra, Integer idRota) throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudan�a de Rota da Quadra
	 * M�todo que obt�m o total de im�veis com rota diferente � rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisRotaDiferenteAnteriorQuadra(Integer idQuadra, Integer idRota) throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * M�todo que atualiza a rota dos im�veis pela quadra e rota(informada ou n�o).
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2012
	 * @throws ErroRepositorioException
	 */
	public void atualizarRotaImoveisPorQuadra(Integer idQuadra, Integer idRotaAtualizar, Integer idRotaAnterior)
					throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relat�rio dos Im�veis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Collection pesquisarRelatorioImoveisPorQuadra(Integer idQuadra) throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relat�rio dos Im�veis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioImoveisPorQuadra(Integer idQuadra) throws ControladorException;

	public Object pesquisarImovelIdComContaHistorico(Integer imovelId, Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudan�a de Distrito Operacional da Quadra
	 * M�todo que obt�m o total de im�veis com distrito operacional igual ao distrito operacional
	 * anterior da quadra.
	 * 
	 * @author Luciano Galv�o
	 * @date 31/05/2012
	 * @throws ControladorException
	 */
	public Integer obterTotalImoveisDistritoOperacionalIgualAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudan�a de Distrito Operacional da Quadra
	 * M�todo que obt�m o total de im�veis com distrito operacional diferente do distrito
	 * operacional anterior da quadra.
	 * 
	 * @author Luciano Galv�o
	 * @date 31/05/2012
	 * @throws ControladorException
	 */
	public Integer obterTotalImoveisDistritoOperacionalDiferenteAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ControladorException;

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * M�todo que atualiza o distrito operacional dos im�veis pela quadra e distrito operacional
	 * anterior(informada ou n�o).
	 * 
	 * @author Luciano Galv�o
	 * @date 31/05/2012
	 * @throws ControladorException
	 */
	public void atualizarDistritoOperacionalImoveisPorQuadra(Integer idQuadra, Integer idDistritoOperacionalAtualizar,
					Integer idDistritoOperacionalAnterior) throws ControladorException;

	public Collection pesquisarObterQuantidadeEconomias(Integer idImovel) throws ControladorException;

	public Collection<Object[]> consultarImoveisAvisoCorte(Integer idSetor, Integer idFaturamentoAtividadeCronograma)
					throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 27/06/2012
	 * @param Integer
	 *            idImovel
	 * @return Object[] idLocalidade, idSetorComercial, idBairro
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosLocalizacaoImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC3062] Validar Autoriza��o Acesso Im�vel - Cobran�a Administrativa
	 * Valida a autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa e valida a autoriza��o de acesso ao im�vel em cobran�a administrativa pelos
	 * usu�rios da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param usuario
	 * @param idImovel
	 * @param cdTipoValidacaoIdentificado
	 * @return
	 */
	public Short obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(Usuario usuario, Integer idImovel,
					Short cdTipoValidacaoIdentificado) throws ControladorException;

	/**
	 * Este m�todo � chamado por 2 Casos de Uso diferentes:
	 * [UC0203] Consultar D�bitos
	 * [SB0005] - Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa
	 * e
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB0024] - Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa
	 * 
	 * @author Saulo Lima
	 * @date 25/07/2013
	 * @param usuario
	 * @param idImovel
	 * @param colecaoDebitoImovel
	 * @return 0 = (Permite tudo) N�o identificou nenhum dos casos
	 *         1 = (Bloqueia tudo) Se tem usuario N�O tem autoriza��o, im�vel possui d�bito com
	 *         remunera��o Cob. Administ. e �ltimo Comando de A��o de Cobran�a N�O � da empresa do
	 *         usu�rio
	 *         2 = (Bloqueia parcial) Se tem usuario N�O tem autoriza��o, im�vel possui d�bito com
	 *         remunera��o Cob. Administ. e �ltimo Comando de A��o de Cobran�a � da empresa do
	 *         usu�rio
	 *         3 = (Bloqueia tudo) Se tem usuario N�O tem autoriza��o e im�vel N�O possui d�bito com
	 *         remunera��o Cob. Administ.
	 *         4 = (Bloqueia parcial) Se tem usuario tem autoriza��o e empresa do usu�rio tem
	 *         contrato de cobran�a
	 * @throws ControladorException
	 */
	public Integer validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, Integer idImovel,
					ObterDebitoImovelOuClienteHelper colecaoDebitoImovel) throws ControladorException;

	/**
	 * [UC0204] Consultar Conta
	 * [SB0001] � Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa
	 * 
	 * @author Saulo Lima
	 * @date 14/08/2013
	 * @param usuario
	 * @param idImovel
	 * @param conta
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, Integer idImovel, Conta conta)
					throws ControladorException;

	/**
	 * Este m�todo � chamado por 2 Casos de Uso diferentes:
	 * [UC0203] Consultar D�bitos
	 * [SB0006] - Validar autoriza��o de acesso ao im�vel em cobran�a administrativa pelos usu�rios
	 * da empresa contratante
	 * e
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB0025] - Validar autoriza��o de acesso ao im�vel em cobran�a administrativa pelos usu�rios
	 * da empresa contratante
	 * 
	 * @return Retorna:
	 *         1 - Caso o usu�rio n�o tenha autoriza��o de acesso ao im�vel
	 *         2 - Caso o usu�rio logado no sistema n�o possua permiss�o especial
	 *         para acesso aos dados do im�vel em cobran�a administrativa
	 *         null - Caso o usu�rio tenha permiss�o
	 * @author Saulo Lima
	 * @date 31/07/2013
	 * @param usuario
	 * @param idImovel
	 * @param colecaoDebitoImovel
	 * @throws ControladorException
	 */
	public Integer validarAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante(Usuario usuario, Integer idImovel,
					ObterDebitoImovelOuClienteHelper colecaoDebitoImovel) throws ControladorException;

	/**
	 * [UC0203] Consultar D�bitos
	 * [SB0005] Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa
	 * 
	 * @author Saulo Lima
	 * @date 25/07/2013
	 * @param colecaoDebitoImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarIndicadorRemuneraCobrancaAdministrativa(ObterDebitoImovelOuClienteHelper colecaoDebitoImovel)
					throws ControladorException;

	/**
	 * Verifica se um cliente representado pelo CNPJ/CPF tem rela��o ativa com o im�vel representado
	 * pela matr�cula.
	 * 
	 * @author Marlos Ribeiro
	 * @param cpfCnpjCliente
	 * @param matriculaImovel
	 * @exception ControladorException
	 *                se o cliente nao tiver rela��es com o im�vel.
	 */
	public void validarPermissaoClienteImovel(String cpfCnpjCliente, String matriculaImovel) throws ControladorException, NegocioException;

	public void validarPermissaoClienteImovel(String matriculaImovel) throws ControladorException, NegocioException;
	/**
	 * M�todo respons�vel por verificar se o usu�rio tem acesso a impressa�o de extrato de d�bito
	 * 
	 * @param usuarioLogado
	 * @param idImovel
	 * @param helper
	 * @return
	 * @throws ControladorException
	 */
	Boolean isImpressaoExtratoDebitoLiberada(Usuario usuarioLogado, Integer idImovel, ObterDebitoImovelOuClienteHelper helper)
					throws ControladorException;

	/**
	 * M�todo respons�vel por exibir a mensagem de procedimento de corte do imovel, caso exista
	 * 
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	String obterMsgImovelProcessoCorte(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisar Rotas dos Im�veis
	 * 
	 * @author Hebert Falc�o
	 * @date 12/11/2012
	 */
	public Collection<Rota> pesquisarRotasDosImoveis(Collection<Integer> idImoveis) throws ControladorException;

	/**
	 * M�todo respons�vel por obter o c�digo de uma rota
	 * 
	 * @param idRota
	 * @return
	 */
	Short obterCodigoRota(Integer idRota) throws ControladorException;

	/**
	 * Gerar Relat�rio de Im�veis Outros Crit�rios Contador
	 * 
	 * @author �talo Almeida
	 * @date 10/01/2013
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
	 */
	public int gerarRelatorioImovelOutrosCriteriosCount(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto,
					String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto, String intervaloValorPercentualEsgotoInicial,
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
					String idUnidadeNegocio, String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial,
 String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial,
					String consumoFixadoEsgotoPocoFinal)
					throws ControladorException;

	/**
	 * Obt�m a principal categoria do im�vel
	 * [UC0306] Obter Principal Categoria do Im�vel
	 * 
	 * @author Pedro Alexandre, Ivan S�rgio
	 * @date 18/04/2006, 14/08/2007
	 * @alteracao - Correcao no [FS0001 - Verificar mais de uma categoria...]
	 * @author Luciano Galvao - Este m�todo j� recebe o c�digo da empresa Febraban, par�metro do
	 *         sistema necess�rio neste caso de uso. Foi mantido o m�todo
	 *         obterPrincipalCategoriaImovel(Integer idImovel), que consulta os par�metros do
	 *         sistema e passa o c�digo para este m�todo. Esta separa��o foi feita para possibilitar
	 *         o uso deste m�todo com o consulta pr�via dos par�metros do sistema em uma �nica vez.
	 *         Se este m�todo � chamado dentro de um la�o, � interessante utiliz�-lo para evitar
	 *         consultas desnecess�rias a tabela sistema_parametros
	 * @param idImovel
	 * @param construirObjetoCompleto
	 *            <true> se for necess�rio carregar o objeto Categoria completo. <false> caso seja
	 *            necess�rio apenas o Id e a quantidade de economias
	 * @return
	 * @throws ControladorException
	 */
	public Categoria obterPrincipalCategoriaImovel(Integer idImovel, Short codigoEmpresaFebraban, boolean construirObjetoCompleto)
					throws ControladorException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] � Gerar Integra��o para Faturamento Imediato � Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Cliente pesquisarClientePropietarioImovel(Integer idImovel) throws ControladorException;

	/**
	 * @param idRota
	 * @return
	 * @throws ControladorException
	 */
	public String obterRota(Integer idRota) throws ControladorException;
	
	/**
	 * Pesquisa todos os im�veis de uma determinada Localidade.
	 * 
	 * @author Josenildo Neves
	 * @date 02/07/2013
	 * @param idLocalidade
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarImoveisPorLocalidade(Integer idLocalidade) throws ControladorException;

	/**
	 * [UC0098] � Manter Vinculos Rateio Consumo
	 * [FS0011] � Verificar ciclo de faturamento do im�vel
	 * 
	 * @author �talo Almeida
	 */
	public Boolean verificarImovelEmProcessoDeFaturamento(Integer idImovel) throws ControladorException;

	/**
	 * Retorna <true> se existir altera��o de inscri��o pendente para o im�vel ou para o im�vel
	 * condom�nio do dado im�vel, caso exista. Retorna <false> caso contr�rio.
	 * 
	 * @author Luciano Galvao
	 * @date 18/01/2013
	 */
	public boolean existeAlteracaoInscricaoPendente(Integer imovelId) throws ControladorException;

	/**
	 * Verifica se a altera��o da inscri��o/rota do im�vel impacta no grupo de faturamento.
	 * 
	 * @author Luciano Galv�o
	 * @date 22/01/2013
	 * @throws AlteracaoInscricaoImovelException
	 */
	public void verificarAlteracaoInscricaoImovel(Integer imovelId, Rota rotaAtual) throws ControladorException,
					AlteracaoInscricaoImovelException;

	/**
	 * [UC3090] Efetivar Alterar Inscri��o de Im�vel
	 * Efetiva a altera��o da inscri��o dos im�veis que no momento da altera��o on-line n�o puderam
	 * ter a inscri��o alterada em raz�o de j� estarem em processo de faturamento ficando a
	 * atualiza��o para o fechamento do faturamento do grupo
	 * 
	 * @author Luciano Galv�o
	 * @throws ControladorException
	 * @date 18/02/2013
	 */
	public void efetivarAlteracaoInscricaoImovel(int idFuncionalidadeIniciada, Usuario usuarioLogado) throws ControladorException;

	public void atualizarNumeroEmissaoContrato(Integer idImovel) throws ControladorException;

	/**
	 * [UC0084] Gerar Faturamento Imediato
	 * [SB0007] Gerar Movimento Roteiro da Empresa
	 * Verifica se o im�vel � uma Reparti��o P�blica Federal, levando em considera��o sua categoria
	 * e subcategoria
	 * 
	 * @author Luciano Galvao
	 * @date 16/10/2013
	 */
	public boolean verificarImovelReparticaoPublicaFederal(Integer idImovel) throws ControladorException;

	/**
	 * @autor Eduardo Oliveira
	 * @date 16/12/2013
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterQuantidadeAlteracoesImovel(Integer idImovel) throws ControladorException;

	/**
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public List<Object[]> pesquisarDadosImoveisComContaEmAtraso(boolean apenasPublicos) throws ControladorException;

	/**
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public Collection<Conta> pesquisarContasEmAtrasoPorImovel(Integer idImovel) throws ControladorException;

	/**
	 * @author Gicevalter Couto
	 * @date 17/09/2014
	 */
	public boolean verificarTipoRelacaoUsuariaAtivo(Collection<ClienteImovel> colecaoClientesImovel) throws ControladorException;

	/**
	 * @author Gicevalter Couto
	 * @date 21/09/2014
	 * @throws ControladorException
	 */
	public void ajustarRelacaoUsuarioClienteImovel(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * <p>
	 * [OC1372979]
	 * </p>
	 * 
	 * @author Magno Silveira (magno.silveira@procenge.com.br)
	 * @since 22/10/2014
	 * @param imovelCondominioId
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarImoveisSubcategoriasParaCondominio(Integer imovelCondominioId) throws ControladorException;


	/**
	 * @author Magno Silveira { @literal <magno.silveira@procenge.com.br> }
	 * @since 07/10/2014
	 * @param idFuncionalidadeIniciada
	 */
	public void atualizarImoveisComSupressaoDefinitiva(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * @param idImovel
	 * @throws ControladorException
	 */
	public Collection pesquisarImovelSubcategorias(Integer idImovel) throws ControladorException;

	/**
	 * @param idImovel
	 * @throws ControladorException
	 */
	public void atualizarRotaImovel(Integer idImovel, Integer idRotaAtualizar) throws ControladorException;

	/**
	 * @param idImovel
	 * @param anoMesMovimento
	 * @throws ControladorException
	 */
	public Collection<Categoria> obterCategorias(Integer idImovel, Integer anoMesMovimento);
	
	public Integer retornarAnoMesReferenciaFaturamentoGrupoImovel(Integer idImovel) throws ControladorException;
}