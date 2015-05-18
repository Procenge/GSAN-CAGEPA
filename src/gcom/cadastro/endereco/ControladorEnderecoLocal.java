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
 * Saulo Vasconcelos de Lima
 */

package gcom.cadastro.endereco;

import gcom.agenciavirtual.cadastro.endereco.LogradouroJSONHelper;
import gcom.arrecadacao.pagamento.bean.PagamentoHistoricoAdmiteDevolucaoHelper;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.endereco.bean.AtualizarLogradouroBairroHelper;
import gcom.cadastro.endereco.bean.AtualizarLogradouroCepHelper;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.Imovel;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.List;

/**
 * Description of the Interface
 * 
 * @author S�vio Luiz
 * @created 20 de Julho de 2005
 */
public interface ControladorEnderecoLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * inseri um logradouro na base e se tiver um bairro inseri na tabela de
	 * liga��o logradouroBairro
	 * 
	 * @param logradouro
	 *            Description of the Parameter
	 * @param bairro
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public Integer inserirLogradouro(Logradouro logradouro, Collection<Bairro> colecaoBairros, Collection<Cep> colecaoCeps)
					throws ControladorException;

	/**
	 * atualiza um logradouro na base e se tiver um bairro inseri na tabela de
	 * liga��o logradouroBairro
	 * 
	 * @param logradouro
	 *            Description of the Parameter
	 * @param bairro
	 *            Description of the Parameter
	 */
	public void atualizarLogradouro(Logradouro logradouro, Collection colecaoBairros, Collection colecaoCeps,
					Collection<AtualizarLogradouroBairroHelper> colecaoBairrosAtualizacao,
					Collection<AtualizarLogradouroCepHelper> colecaoCepsAtualizacao) throws ControladorException;

	/**
	 * remove um logradouro e o bairro ta tabela liga��o logradouroBairro do
	 * logradouro removido.
	 * 
	 * @param ids
	 *            Description of the Parameter
	 * @param pacoteLogradouro
	 *            Description of the Parameter
	 */
	public void removerLogradouro(String[] ids, String pacoteLogradouro, OperacaoEfetuada operacaoEfetuada,
					Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper) throws ControladorException;

	/**
	 * inseri os ceps importados
	 * 
	 * @param cepsImportados
	 *            Cole��o contendo todos os CEPs lidos no arquivo
	 * @return retorno
	 *         Array de String contendo nas posi��es:
	 *         0- Total de CEPs
	 *         1- CEPs Inseridos
	 *         2- CEPs Atualizados
	 *         3- CEPs Desprezados
	 */
	public String[] inserirCepImportados(Collection cepsImportados) throws ControladorException;

	public String pesquisarEndereco(Integer idImovel) throws ControladorException;

	public String pesquisarEnderecoFormatado(Integer idImovel) throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 26/03/2010
	 *       Mudan�a no tipo de retorno para Object[]:
	 *       0 - endereco (String)
	 *       1 - idLogradouro (Integer)
	 *       2 - numeroImovel (String)
	 *       3 - cepImovel (String)
	 *       4 - imovel (Imovel)
	 */
	public Object[] pesquisarEnderecoFormatadoLista(Integer idImovel) throws ControladorException;

	/**
	 * Obt�m o CEP PADR�O para um determinado munic�pio
	 * 
	 * @author Raphael Rossiter
	 * @date 04/05/2006
	 * @param municipio
	 * @return Cep
	 */
	public Cep obterCepInicialMunicipio(Municipio municipio) throws ControladorException;

	/**
	 * Verifica se o CEP � �nico de munic�pio
	 * 
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param cep
	 * @return boolean
	 */
	public boolean verificarCepUnicoMunicipio(Cep cep) throws ControladorException;

	/**
	 * Verifica se o Bairro � do tipo "BAIRRO NAO INFORMADO"
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param bairro
	 * @return boolean
	 */
	public boolean verificarBairroTipoBairroNaoInformado(Bairro bairro) throws ControladorException;

	/**
	 * Verificar se o CEP est� associado a outro logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 04/05/2006
	 * @param cep
	 * @return Logradouro
	 */
	public Logradouro verificarCepAssociadoOutroLogradouro(Cep cep) throws ControladorException;

	/**
	 * Seleciona os bairros em que o logradouro est� contido
	 * 
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param Logradouro
	 * @return Collection<Bairro>
	 */
	public Collection<Bairro> obterBairrosPorLogradouro(Logradouro logradouro) throws ControladorException;

	/**
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param Logradouro
	 * @return Integer
	 */
	public Integer inserirAssociacaoLogradouroCep(LogradouroCep logradouroCep) throws ControladorException;

	/**
	 * [UC0003] Informar Endere�o
	 * Pesquisar associa��o de LogradouroCep apenas por logradouro
	 * 
	 * @author Raphael Rossiter
	 * @data 12/05/2006
	 * @param idLogradouro
	 * @return LogradouroCep
	 */
	public Collection<LogradouroCep> pesquisarAssociacaoLogradouroCepPorLogradouro(Logradouro logradouro) throws ControladorException;

	/**
	 * Verifica se o logradouro j� est� associado a CEPs do tipo logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 17/05/2006
	 * @param logradouro
	 * @return boolean
	 */
	public boolean verificarLogradouroAssociadoCepTipoLogradouro(Logradouro logradouro) throws ControladorException;

	/**
	 * Obt�m o CEP �nico para um determinado munic�pio
	 * 
	 * @author Raphael Rossiter
	 * @date 23/05/2006
	 * @param municipio
	 * @return Cep
	 */
	public Cep obterCepUnicoMunicipio(Municipio municipio) throws ControladorException;

	/**
	 * [UC0003] Informar Endere�o
	 * Pesquisar associa��o de LogradouroBairro j� existente
	 * 
	 * @author Raphael Rossiter
	 * @data 24/05/2006
	 * @param idBairro
	 *            , idLogradouro
	 * @return LogradouroBairro
	 */
	public LogradouroBairro pesquisarAssociacaoLogradouroBairro(Integer idBairro, Integer idLogradouro) throws ControladorException;

	/**
	 * [UC0003] Informar Endere�o
	 * Pesquisar associa��o de LogradouroCep j� existente
	 * 
	 * @author Raphael Rossiter
	 * @data 24/05/2006
	 * @param idCep
	 *            , idLogradouro
	 * @return LogradouroBairro
	 */
	public LogradouroCep pesquisarAssociacaoLogradouroCep(Integer idCep, Integer idLogradouro) throws ControladorException;

	public Collection<Logradouro> pesquisarLogradouro(FiltroLogradouro filtroLogradouro, Integer numeroPaginas) throws ControladorException;

	public Integer pesquisarLogradouroCount(FiltroLogradouro filtroLogradouro) throws ControladorException;

	/**
	 * [UC0085] - Obter Endere�o
	 * 
	 * @author S�vio Luiz
	 * @date 14/06/2006
	 * @param idCliente
	 * @return String
	 */
	public String pesquisarEnderecoClienteAbreviado(Integer idCliente) throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 24/03/2010
	 * @param idCliente
	 * @return Object[]:
	 *         0 - endereco (String)
	 *         1 - idLogradouro (Integer)
	 *         2 - numeroImovel (String)
	 */
	public Object[] pesquisarEnderecoClienteAbreviadoLista(Integer idCliente, boolean indicadorAbreviado) throws ControladorException;

	// metodo que serve para fazer a pesquisa do logradouro
	// apartir dos parametros informados
	public Collection pesquisarLogradouroCompleto(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular, Integer numeroPaginas) throws ControladorException;

	public Collection pesquisarLogradouroCompletoRelatorio(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular) throws ControladorException;

	public Integer pesquisarLogradouroCompletoCount(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular)
					throws ControladorException;

	/**
	 * Verifica se o CEP � inicial de munic�pio
	 * 
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param cep
	 * @return boolean
	 */
	public boolean verificarCepInicialMunicipio(Cep cep) throws ControladorException;

	/**
	 * [UC0085] - Obter Endere�o Autor: Ana Maria
	 */
	public String pesquisarEnderecoRegistroAtendimentoFormatado(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0085] - Obter Endere�o - refatora��o: Andr� Lopes
	 */
	public RegistroAtendimento pesquisarRegistroAtendimentoDadosEnderecoFormatado(Integer idRegistroAtendimento)
					throws ControladorException;

	/**
	 * [UC0085] - Obter Endere�o Autor: Ana Maria
	 */

	public String pesquisarEnderecoRegistroAtendimentoSolicitanteFormatado(Integer idRegistroAtendimentoSolicitante)
					throws ControladorException;

	/**
	 * Obter o objeto de registro atendimento para recuperar Endere�o Autor:
	 * S�vio Luiz
	 */

	public RegistroAtendimento pesquisarRegistroAtendimentoEndereco(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * Obter os parametros de logradouroCep para o endere�o Autor: S�vio Luiz
	 */

	public LogradouroCep pesquisarLogradouroCepEndereco(Integer idLogradouroCep) throws ControladorException;

	/**
	 * Obter os parametros de logradouroBairro para o endere�o Autor: S�vio Luiz
	 */

	public LogradouroBairro pesquisarLogradouroBairroEndereco(Integer idLogradouroBairro) throws ControladorException;

	/**
	 * Obter os campos necess�rio para o endere�o do im�vel Autor:S�vio Luiz
	 */

	public Imovel pesquisarImovelParaEndereco(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisar os Endere�os do Cliente
	 * [UC0474] Consultar Im�vel
	 * 
	 * @author Rafael Santos
	 * @date 19/09/2006
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClientesEnderecosAbreviado(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisar o endere�o abreviado a partir do id do im�vel
	 * [UC0483] - Obter Endere�o Abreviado
	 * 
	 * @author Rafael Corr�a
	 * @date 18/10/2006
	 * @param idImovel
	 * @return String
	 * @throws ControladorException
	 */

	public String obterEnderecoAbreviadoImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisar o endere�o abreviado a partir do id do ra
	 * [UC0483] - Obter Endere�o Abreviado
	 * 
	 * @author Rafael Corr�a
	 * @date 18/10/2006
	 * @param idRA
	 * @return String
	 * @throws ControladorException
	 */

	public String obterEnderecoAbreviadoRA(Integer idRA) throws ControladorException;

	/**
	 * [UC0085] - Obter Endere�o Autor: S�vio Luiz Data: 09/04/2007 Recupera o
	 * endere�o em 3 partes:o endere�o abreviado formatado sem o municipio e a
	 * unidade federa��o,a descri��o do municipio e a terceira parte a sigla da
	 * unidade federa��o
	 */

	public String[] pesquisarEnderecoClienteAbreviadoDividido(Integer idCliente) throws ControladorException;

	/**
	 * [UC0210] - Obter Endere�o Autor: S�vio Luiz
	 */

	public String[] pesquisarEnderecoFormatadoDividido(Integer idImovel) throws ControladorException;

	public String[] pesquisarEnderecoFormatadoDividido(Integer idImovel, int limiteEndereco) throws ControladorException;

	/**
	 * [UC0348] Emitir Contas por cliente responsavel CAERN
	 * Pesquisar endereco formatado para cliente
	 * 
	 * @author Raphael Rossiter
	 * @data 22/05/2007
	 * @param idCliente
	 *            ,
	 * @return Collection
	 */
	public String[] pesquisarEnderecoFormatadoClienteDividido(Integer idCliente) throws ControladorException;

	/**
	 * [UC0210] - Obter Endere�o Autor: S�vio Luiz
	 */

	public String pesquisarEnderecoAbreviadoCAER(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisar o cep pelo codigo do cep
	 * 
	 * @author S�vio Luiz
	 * @date 05/11/2007
	 */

	public Cep pesquisarCep(Integer codigoCep) throws ControladorException;

	/**
	 * Verifica a exist�ncia do endere�o de correspond�ncia do cliente pelo seu id
	 */
	public boolean verificarExistenciaClienteEndereco(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisar Logradouro Bairro
	 * 
	 * @author Virg�nia Melo
	 * @date 24/07/2009
	 * @param idLogradouroBairro
	 * @return LogradouroBairro
	 * @throws ControladorException
	 */
	public LogradouroBairro pesquisarLogradouroBairro(Integer idLogradouroBairro) throws ControladorException;

	public String pesquisarEnderecoClienteAbreviado(Integer idCliente, boolean indicadorAbreviado) throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio Logradouro Geral
	 * 
	 * @author Anderson Italo
	 * @date 26/01/2011 Obter dados dos Logradouros pelos parametros informados
	 */
	public Collection pesquisarLogradourosPorMunicipio(Integer idMunicipio) throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio Logradouro Geral
	 * 
	 * @author Anderson Italo
	 * @date 26/01/2011
	 *       Obter total dos Logradouros pelos por Munic�pio
	 */
	public Integer calcularTotalLogradourosPorMunicipio(Integer idMunicipio) throws ControladorException;

	/**
	 * Obtem o Bairro pelo id do im�vel (se informado), caso n�o informado o id do im�vel ou n�o
	 * encontrado bairro para aquele im�vel, ent�o procura o bairro pelo id do RA
	 * 
	 * @author isilva
	 * @param idImovel
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ControladorException
	 */
	public Bairro obterBairroPorImovelOuRA(Integer idImovel, Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * Inserir o cep
	 * 
	 * @param codigo
	 * @param sigla
	 * @param municipio
	 * @param bairro
	 * @param logradouro
	 * @param descricaoTipoLogradouro
	 * @return id da entidade inserida
	 * @throws ControladorException
	 */
	public Integer inserirCep(Integer codigo, String sigla, String municipio, String bairro, String logradouro,
					String descricaoTipoLogradouro) throws ControladorException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Object[] obterDadosEndereco(Integer idImovel) throws ControladorException;

	/**
	 * [UC0085] - Obter Endere�o Autor: S�vio Luiz Data: 26/12/2005
	 * Para atender o Caso de Uso [UC0349], foi criado este m�todo que retorna o endere�o quebrado
	 * em duas Strings, de acordo com o tamanho limite informado
	 * 
	 * @param idImovel
	 * @param limiteTamanhoEndereco
	 */

	public String[] pesquisarEndereco(Integer idImovel, int limiteTamanhoEndereco) throws ControladorException;

	/**
	 * [UC0210] - Obter Endere�o
	 * 
	 * @author Saulo Lima
	 *         Alterado por Luciano Galv�o em 22/06/2012
	 * @date 26/03/2010
	 * @param idImovel
	 * @return Object[]
	 *         0 - endereco (String)
	 *         1 - idLogradouro (Integer)
	 *         2 - numeroImovel (String)
	 *         3 - cepImovel (String)
	 *         4 - imovel (Imovel)
	 *         5 - municipio (String)
	 *         6 - enderecoParticionado1 (String)
	 *         7 - enderecoParticionado2 (String)
	 */
	public Object[] pesquisarEnderecoFormatadoLista(Integer idImovel, int limiteTamanhoEndereco) throws ControladorException;

	/**
	 * Pesquisa na View logradouro por mun�cipio, bairro, noemLogradouro e retorna uma lista de
	 * logradouros
	 * 
	 * @author Josenildo Neves
	 * @date 08/08/2012
	 */
	public List<LogradouroJSONHelper> pesquisarViewLogradouro(Integer idMunicipio, Integer idBairro, String nomeLogradouro)
					throws ControladorException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterMunicipio(Integer idImovel) throws ControladorException;

	public Integer pesquisarCepFiltroCount(String nomeLogradouro, String codigoLado, String faixa, String nomeMunicipio)
					throws ControladorException;

	public Collection pesquisarCepFiltro(String nomeLogradouro, String codigoLado, String faixa, Integer numeroPaginas, String nomeMunicipio)
					throws ControladorException;

	/**
	 * @author Felipe rosacruz
	 * @date 22/08/2013
	 * @throws ControladorException
	 */
	public String pesquisarEnderecoFormatadoEmpresa() throws ControladorException;

	public String pesquisarEnderecoComDetalhamento(Integer idImovel) throws ControladorException;

	public String pesquisarEnderecoClienteAbreviadoComDetalhamento(Integer idCliente) throws ControladorException;

	/**
	 * FIXME - MOVER PARA CONTROLADOR FATURAMENTO APOS IMPLEMENTA��O
	 * 
	 * @return
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistoricoAdmiteDevolucaoHelper> consultarPagamentosHistoricoAdmiteDevolucao(Integer idImovel,
					boolean creditoARealizar)
					throws ControladorException;

	/**
	 * FIXME - MOVER PARA CONTROLADOR FATURAMENTO APOS IMPLEMENTA��O
	 */
	public Integer pesquisarQuantidadePagamentosHistoricoCount(PagamentoHistoricoAdmiteDevolucaoHelper pagamento, String matriculaImovel)
					throws ControladorException;

	/**
	 * Pesquisar endere�o sem refer�ncia
	 * 
	 * @author Hebert Falc�o
	 * @date 12/11/2013
	 */
	public String pesquisarEnderecoSemReferencia(Integer idImovel) throws ControladorException;

	public String obterLogradouroTipoImovel(Integer idImovel) throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 24/03/2010
	 * @param idCliente
	 * @return Object[]:
	 *         0 - endereco (String)
	 *         1 - idLogradouro (Integer)
	 *         2 - numeroImovel (String)
	 */
	public Object[] pesquisarEnderecoClienteAbreviadoListaComDetalhamento(Integer idCliente, boolean indicadorAbreviado)
					throws ControladorException;

	/**
	 * Agencia Virtual - Consultar Cep Por Logradouro e Bairro
	 * 
	 * @author Anderson Italo
	 * @date 16/03/2014
	 */
	public List<Object[]> pesquisarCepPorLogradouroEBairro(Integer idLogradouro, Integer idBairro) throws ControladorException;

	/**
	 * [UC0083] Gerar Dados para Leitura
	 * [SB0001] - Gerar Arquivo Convencional
	 * [SB0010] - Gerar Arquivo - Modelo 2
	 * <<Inclui>> [UC3148 - Obter Endere�o de Entrega]
	 * 
	 * @author Anderson Italo
	 * @throws ControladorException
	 * @date 27/05/2014
	 */
	public Object[] obterEnderecoEntrega(Integer idImovel, Integer idImovelContaEnvio) throws ControladorException;

}
