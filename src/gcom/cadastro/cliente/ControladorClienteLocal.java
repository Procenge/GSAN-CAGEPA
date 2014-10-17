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

package gcom.cadastro.cliente;

import gcom.agenciavirtual.cadastro.cliente.ClienteJSONHelper;
import gcom.cadastro.cliente.bean.ClienteEmitirBoletimCadastroHelper;
import gcom.cadastro.cliente.bean.ClienteImovelRelacaoHelper;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.imovel.Imovel;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorClienteLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Insere um cliente no sistema
	 * 
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ j� cadastrado desde que seja igual ao do cliente
	 *       respons�vel superior
	 * @param cliente
	 *            Cliente a ser inserido
	 * @param telefones
	 *            Telefones do cliente
	 * @param enderecos
	 *            Endere�os do cliente
	 * @param usuario
	 * @param responsavelCliente
	 * @param indDadosAdicionais
	 * @return Id do cliente inserido
	 * @throws ControladorException
	 */
	public Integer inserirCliente(Cliente cliente, Collection<ClienteFone> telefones, Collection<ClienteEndereco> enderecos,
					Usuario usuario, ClienteResponsavel responsavelCliente, String indDadosAdicionais) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param clienteImovel
	 * @param usuarioLogado
	 *            Descri��o do par�metro
	 */
	public void inserirClienteImovel(ClienteImovel clienteImovel, Usuario usuarioLogado) throws ControladorException;

	/**
	 * atualiza um cliente imovel economia com a data final da rela��o e o
	 * motivo.
	 * 
	 * @param clienteImovelEconomia
	 *            Description of the Parameter
	 * @exception RemoteException
	 *                Description of the Exception
	 */
	public void atualizarClienteImovelEconomia(Collection clientesImoveisEconomia) throws ControladorException;

	/**
	 * Atualiza um cliente no sistema
	 * 
	 * @author eduardo henrique
	 * @date 27/06/2008
	 *       Adi��o de Valida��o do CPF e RG de CLiente, caso exista
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ j� cadastrado desde que seja igual ao do cliente
	 *       respons�vel superior
	 * @param cliente
	 *            Cliente a ser atualizado
	 * @param telefones
	 *            Telefones do cliente
	 * @param enderecos
	 * @param responsavelCliente
	 * @param usuario
	 * @param indDadosAdicionais
	 * @param idRegistroAtualizacao
	 *            Endere�os do cliente
	 * @throws ControladorException
	 */
	public void atualizarCliente(Cliente cliente, Collection<ClienteFone> telefones, Collection<ClienteEndereco> enderecos,
					ClienteResponsavel responsavelCliente, Usuario usuario, String indDadosAdicionais, String idRegistroAtualizacao)
					throws ControladorException;

	/**
	 * M�todo utilizado para atualizar um cliente
	 * 
	 * @param clienteJSONHelper
	 * @throws ControladorException
	 */
	void atualizarCliente(ClienteJSONHelper clienteJSONHelper) throws ControladorException;

	/**
	 * Pesquisa uma cole��o de cliente imovel que estao na tarifa social
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 */
	public Collection pesquisarClienteImovelTarifaSocial(FiltroClienteImovel filtroClienteImovel, Integer numerpPagina)
					throws ControladorException;

	/**
	 * Pesquisa uma cole��o de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 */
	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel, Integer numeroPagina) throws ControladorException;

	/**
	 * Pesquisa uma cole��o de cliente endereco com uma query especifica
	 * 
	 * @param filtroClienteEndereco
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */

	public Collection pesquisarClienteEndereco(FiltroClienteEndereco filtroClienteEndereco) throws ControladorException;

	/**
	 * Metodo que retorno todos os clinte do filtro passado
	 * 
	 * @param filtroCliente
	 *            Descri��o do par�metro
	 * @return Description of the Return Value
	 * @autor thiago toscano
	 * @date 15/12/2005
	 * @throws ControladorException
	 */

	public Collection pesquisarCliente(FiltroCliente filtroCliente) throws ControladorException;

	public Collection<Cliente> pesquisarClienteEnderecoClienteImovel(FiltroClienteEndereco filtroClienteEndereco)
					throws ControladorException;

	public Collection<Cliente> pesquisarClienteDadosClienteEndereco(FiltroCliente filtroCliente, Integer numeroPagina)
					throws ControladorException;

	public Collection<Cliente> pesquisarClienteDadosClienteEnderecoRelatorio(FiltroCliente filtroCliente) throws ControladorException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * Retrona a quantidade de endere�os que existem para o Cliente
	 * pesquisarClienteDadosClienteEnderecoCount
	 * 
	 * @author Roberta Costa
	 * @date 29/06/2006
	 * @param filtroCliente
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarClienteDadosClienteEnderecoCount(FiltroCliente filtroCliente) throws ControladorException;

	public Collection<Cliente> pesquisarClienteOutrosCriterios(FiltroCliente filtroCliente) throws ControladorException;

	/**
	 * Inseri uma cole��o de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Este fluxo secund�rio tem como objetivo pesquisar o cliente digitado pelo
	 * usu�rio
	 * [FS0011] - Verificar exist�ncia do c�digo do cliente
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idClienteDigitado
	 * @return
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteDigitado(Integer idClienteDigitado) throws ControladorException;

	/**
	 * Pesquisa um cliente carregando os dados do relacionamento com ClienteTipo
	 * [UC0321] Emitir Fatura de Cliente Respons�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2006
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public Cliente pesquisarCliente(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa uma a quantidade de cliente imovel com uma query especifica
	 * [UC0015] Filtrar Imovel
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @author Rafael Santos
	 * @since 26/06/2006
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public int pesquisarQuantidadeClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ControladorException;

	/**
	 * Pesquisa uma cole��o de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ControladorException;

	/**
	 * Pesquisa uma cole��o de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection<Imovel> pesquisarClienteImovelRelatorio(FiltroClienteImovel filtroClienteImovel) throws ControladorException;

	/**
	 * Pesquisa um cliente pelo id
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * @return Cliente
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Cliente pesquisarObjetoClienteRelatorio(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa as quantidades de im�veis e as quantidades de economias
	 * associadas a um cliente
	 * 
	 * @author Rafael Corr�a
	 * @date 23/08/2007
	 * @return Object[]
	 * @exception ControladorException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarQtdeImoveisEEconomiasCliente(Integer idCliente) throws ControladorException;

	/**
	 * Verifica a existencia do cliente
	 * 
	 * @author Fernanda Paiva
	 * @date 01/09/2006
	 * @return id Cliente
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Integer verificarExistenciaCliente(Integer codigoCliente) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Pesquisar ClienteImovel
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 *            ,
	 *            idImovel
	 * @return Colletion
	 * @throws ControladorException
	 */
	public ClienteImovel pesquisarClienteImovel(Integer idCliente, Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa todos os telefones de um cliente
	 * 
	 * @author Raphael Rossiter
	 * @date 23/08/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection<ClienteFone> pesquisarClienteFone(Integer idCliente) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecosClienteAbreviado(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa os dados do Cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Cliente consultarCliente(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa todos os endere�os do cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecoCliente(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa o nome do cliente a partir do im�vel Autor: S�vio Luiz Data:
	 * 21/12/2005
	 */
	public String pesquisarNomeClientePorImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa o nome, cnpj e id do cliente a partir do im�vel Autor: Rafael
	 * Corr�a Data: 25/09/2006
	 */
	public Cliente pesquisarDadosClienteRelatorioParcelamentoPorImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0458] - Imprimir Ordem de Servi�o
	 * Pesquisa o telefone principal do Cliente para o
	 * Relat�rio de OS
	 * 
	 * @author Rafael Corr�a
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public String pesquisarClienteFonePrincipal(Integer idCliente) throws ControladorException;

	/**
	 * Usado pelo Filtrar Cliente
	 * Filtra o Cliente usando os paramentos informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 * @param inscricaoEstadual
	 *            Adicionado � consulta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarCliente(String codigo, String cpf, String rg, String cnpj, String nome, String nomeMae, String cep,
					String idMunicipio, String idBairro, String idLogradouro, String indicadorUso, String tipoPesquisa,
					String tipoPesquisaNomeMae, String clienteTipo, Integer numeroPagina, String inscricaoEstadual,
					String indicadorContaBraille)
					throws ControladorException;

	/**
	 * Usado pelo Filtrar Cliente
	 * Filtra a quantidade de Clientes usando os paramentos informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 * @param inscricaoEstadual
	 *            Adicionado para Filtro
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object filtrarQuantidadeCliente(String codigo, String cpf, String rg, String cnpj, String nome, String nomeMae, String cep,
					String idMunicipio, String idBairro, String idLogradouro, String indicadorUso, String tipoPesquisa,
					String tipoPesquisaNomeMae, String clienteTipo, String inscricaoEstadual, String indicadorContaBraille)
					throws ControladorException;

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Im�veis pelo id do Cliente, indicador de uso, motivo
	 * do fim da rela��o, pelo perfil do im�vel e pelo tipo da rela��o do cliente carregando o
	 * im�vel
	 * Autor: Rafael Corr�a
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloClienteTarifaSocial(Integer idCliente) throws ControladorException;

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Im�veis pelo id do Im�vel carregando o im�vel, o cliente, o perfil do
	 * im�vel,
	 * o org�o expedidor do RG e a unidade da federa��o
	 * Autor: Rafael Corr�a
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelTarifaSocial(Integer idImovel) throws ControladorException;

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Im�veis pelo id do Im�vel carregando os dados necess�rios para retornar
	 * o seu endere�o
	 * Autor: Rafael Corr�a
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelParaEndereco(Integer idImovel) throws ControladorException;

	/**
	 * Verifica se � usuario iquilino ou n�o
	 * 
	 * @author S�vio Luiz
	 * @date 08/01/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificaUsuarioinquilino(Integer idImovel) throws ControladorException;

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
	 * [UC0544] Gerar Arwuivo Texto do Faturamento
	 * Pesquisar ClienteImovel
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 4/04/2006
	 * @return Colletion
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteImovelGerarArquivoFaturamento() throws ControladorException;

	/**
	 * [UC0320] Gerar Fatura de Cliente Respons�vel
	 * 
	 * @author Saulo Lima
	 * @date 16/12/2008
	 * @return Colletion<Cliente>
	 * @throws ErroRepositorioException
	 */
	public Collection<Cliente> pesquisarClienteImovelGerarFaturaClienteResponsavel() throws ControladorException;

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Pesquisa os dados do cliente para a emiss�o do boletim
	 * 
	 * @author Rafael Corr�a
	 * @date 16/05/2007
	 * @param idImovel
	 *            , clienteRelacaoTipo
	 * @throws ControladorException
	 */
	public ClienteEmitirBoletimCadastroHelper pesquisarClienteEmitirBoletimCadastro(Integer idImovel, Integer clienteRelacaoTipo)
					throws ControladorException;

	/**
	 * [UC0864] Gerar Certid�o Negativa por Cliente
	 * 
	 * @author Rafael Corr�a
	 * @date 25/09/2008
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarClientesAssociadosResponsavel(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa o rg do cliente do parcelamento a partir do idParcelamento
	 * Autor: Vivianne Sousa
	 * Data: 20/06/2007
	 */
	public Cliente pesquisarDadosClienteDoParcelamentoRelatorioParcelamento(Integer idParcelamento) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Vivianne Sousa
	 * @date 27/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente obterIdENomeCliente(String cpf) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarCpfCnpjCliente(String cpfCnpj, Integer idCliente, String indicadorPessoaFisicaJuridica)
					throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @date 26/01/2011
	 *       Solicita�ao de Ada: buscar cpfs
	 * @throws ControladorException
	 */
	public Collection pesquisarCadastroClientesRedundantes() throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @date 27/01/2011
	 *       Solicitacao de ADA
	 */
	public void corrigirCadastroClientesRedundantes(Collection colecao) throws ControladorException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Cliente retornarDadosClienteUsuario(Integer idImovel) throws ControladorException;

	/**
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarClienteFonePorCliente(Integer idCliente) throws ControladorException;

	/**
	 * Remover Cliente
	 * 
	 * @author isilva
	 * @date 15/02/2011
	 */
	public void removerCliente(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0204]
	 * 
	 * @author Hebert Falc�o
	 * @date 20/05/2011
	 */
	public Collection<ClienteConta> pesquisarClientesContaPeloImovelEConta(Integer idImovel, Integer idConta) throws ControladorException;

	/**
	 * Retorna o cliente proprietario apartir do id do imovel
	 * 
	 * @author Hebert Falc�o
	 * @date 26/05/2011
	 */
	public Cliente retornarDadosClienteProprietario(Integer idImovel) throws ControladorException;

	/**
	 * Retorna a Cole��o de ClienteImovel que ser� setada no objeto Imovel.
	 * 
	 * @author Ailton Sousa
	 * @date 06/10/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Imovel obterClienteImovelResponsavel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0203] Consultar D�bitos
	 * [SB0004] � Apresenta Relacionamento do Cliente com os Im�veis
	 * 
	 * @author Hugo Lima
	 * @date 10/08/2012
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ClienteImovelRelacaoHelper> obterDadosImoveisClienteRelacao(Integer idCliente) throws ControladorException;

	/**
	 * Ordena uma cole��o de clientes a partir do nome de forma alfab�tica
	 * 
	 * @author Luciano Galvao
	 * @date 28/06/2013
	 */
	public Collection ordernarColecaoClientesPorNome(Collection<Cliente> colecaoCliente);

	/**
	 * Verifica se o cliente possui algum telefone de um determinado tipo.
	 * 
	 * @param idCliente
	 * @param idFoneTipo
	 * @return
	 * @throws ControladorException
	 * @date 12/09/2013
	 * @author Felipe Rosacruz
	 */
	public boolean verificarClientePossuiFoneTipo(Integer idCliente, Integer idFoneTipo) throws ControladorException;

	/**
	 * @param clienteJSONHelper
	 * @throws ControladorException
	 */

	public void inserirCliente(ClienteJSONHelper clienteJSONHelper) throws ControladorException;
}
