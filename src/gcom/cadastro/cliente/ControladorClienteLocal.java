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
 * Declaração pública de serviços do Session Bean de ControladorCliente
 * 
 * @author Sávio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorClienteLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Insere um cliente no sistema
	 * 
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ já cadastrado desde que seja igual ao do cliente
	 *       responsável superior
	 * @param cliente
	 *            Cliente a ser inserido
	 * @param telefones
	 *            Telefones do cliente
	 * @param enderecos
	 *            Endereços do cliente
	 * @param usuario
	 * @param responsavelCliente
	 * @param indDadosAdicionais
	 * @return Id do cliente inserido
	 * @throws ControladorException
	 */
	public Integer inserirCliente(Cliente cliente, Collection<ClienteFone> telefones, Collection<ClienteEndereco> enderecos,
					Usuario usuario, ClienteResponsavel responsavelCliente, String indDadosAdicionais) throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param clienteImovel
	 * @param usuarioLogado
	 *            Descrição do parâmetro
	 */
	public void inserirClienteImovel(ClienteImovel clienteImovel, Usuario usuarioLogado) throws ControladorException;

	/**
	 * atualiza um cliente imovel economia com a data final da relação e o
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
	 *       Adição de Validação do CPF e RG de CLiente, caso exista
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ já cadastrado desde que seja igual ao do cliente
	 *       responsável superior
	 * @param cliente
	 *            Cliente a ser atualizado
	 * @param telefones
	 *            Telefones do cliente
	 * @param enderecos
	 * @param responsavelCliente
	 * @param usuario
	 * @param indDadosAdicionais
	 * @param idRegistroAtualizacao
	 *            Endereços do cliente
	 * @throws ControladorException
	 */
	public void atualizarCliente(Cliente cliente, Collection<ClienteFone> telefones, Collection<ClienteEndereco> enderecos,
					ClienteResponsavel responsavelCliente, Usuario usuario, String indDadosAdicionais, String idRegistroAtualizacao)
					throws ControladorException;

	/**
	 * Método utilizado para atualizar um cliente
	 * 
	 * @param clienteJSONHelper
	 * @throws ControladorException
	 */
	void atualizarCliente(ClienteJSONHelper clienteJSONHelper) throws ControladorException;

	/**
	 * Pesquisa uma coleção de cliente imovel que estao na tarifa social
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 */
	public Collection pesquisarClienteImovelTarifaSocial(FiltroClienteImovel filtroClienteImovel, Integer numerpPagina)
					throws ControladorException;

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 */
	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel, Integer numeroPagina) throws ControladorException;

	/**
	 * Pesquisa uma coleção de cliente endereco com uma query especifica
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
	 *            Descrição do parâmetro
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
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * Retrona a quantidade de endereços que existem para o Cliente
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
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Este fluxo secundário tem como objetivo pesquisar o cliente digitado pelo
	 * usuário
	 * [FS0011] - Verificar existência do código do cliente
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
	 * [UC0321] Emitir Fatura de Cliente Responsável
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
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ControladorException;

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
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
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @return Cliente
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Cliente pesquisarObjetoClienteRelatorio(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa as quantidades de imóveis e as quantidades de economias
	 * associadas a um cliente
	 * 
	 * @author Rafael Corrêa
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
	 * Pesquisa todos os endereços do cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecoCliente(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa o nome do cliente a partir do imóvel Autor: Sávio Luiz Data:
	 * 21/12/2005
	 */
	public String pesquisarNomeClientePorImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa o nome, cnpj e id do cliente a partir do imóvel Autor: Rafael
	 * Corrêa Data: 25/09/2006
	 */
	public Cliente pesquisarDadosClienteRelatorioParcelamentoPorImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * Pesquisa o telefone principal do Cliente para o
	 * Relatório de OS
	 * 
	 * @author Rafael Corrêa
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
	 *            Adicionado à consulta
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
	 * Pesquisa os Clientes Imóveis pelo id do Cliente, indicador de uso, motivo
	 * do fim da relação, pelo perfil do imóvel e pelo tipo da relação do cliente carregando o
	 * imóvel
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloClienteTarifaSocial(Integer idCliente) throws ControladorException;

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Imóvel carregando o imóvel, o cliente, o perfil do
	 * imóvel,
	 * o orgão expedidor do RG e a unidade da federação
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelTarifaSocial(Integer idImovel) throws ControladorException;

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Imóvel carregando os dados necessários para retornar
	 * o seu endereço
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelParaEndereco(Integer idImovel) throws ControladorException;

	/**
	 * Verifica se é usuario iquilino ou não
	 * 
	 * @author Sávio Luiz
	 * @date 08/01/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificaUsuarioinquilino(Integer idImovel) throws ControladorException;

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ControladorException;

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
					throws ControladorException;

	/**
	 * [UC0544] Gerar Arwuivo Texto do Faturamento
	 * Pesquisar ClienteImovel
	 * 
	 * @author Flávio Cordeiro
	 * @date 4/04/2006
	 * @return Colletion
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteImovelGerarArquivoFaturamento() throws ControladorException;

	/**
	 * [UC0320] Gerar Fatura de Cliente Responsável
	 * 
	 * @author Saulo Lima
	 * @date 16/12/2008
	 * @return Colletion<Cliente>
	 * @throws ErroRepositorioException
	 */
	public Collection<Cliente> pesquisarClienteImovelGerarFaturaClienteResponsavel() throws ControladorException;

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Pesquisa os dados do cliente para a emissão do boletim
	 * 
	 * @author Rafael Corrêa
	 * @date 16/05/2007
	 * @param idImovel
	 *            , clienteRelacaoTipo
	 * @throws ControladorException
	 */
	public ClienteEmitirBoletimCadastroHelper pesquisarClienteEmitirBoletimCadastro(Integer idImovel, Integer clienteRelacaoTipo)
					throws ControladorException;

	/**
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @author Rafael Corrêa
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
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 27/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente obterIdENomeCliente(String cpf) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
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
	 *       Solicitaçao de Ada: buscar cpfs
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
	 * @author Hebert Falcão
	 * @date 20/05/2011
	 */
	public Collection<ClienteConta> pesquisarClientesContaPeloImovelEConta(Integer idImovel, Integer idConta) throws ControladorException;

	/**
	 * Retorna o cliente proprietario apartir do id do imovel
	 * 
	 * @author Hebert Falcão
	 * @date 26/05/2011
	 */
	public Cliente retornarDadosClienteProprietario(Integer idImovel) throws ControladorException;

	/**
	 * Retorna a Coleção de ClienteImovel que será setada no objeto Imovel.
	 * 
	 * @author Ailton Sousa
	 * @date 06/10/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Imovel obterClienteImovelResponsavel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0004] – Apresenta Relacionamento do Cliente com os Imóveis
	 * 
	 * @author Hugo Lima
	 * @date 10/08/2012
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ClienteImovelRelacaoHelper> obterDadosImoveisClienteRelacao(Integer idCliente) throws ControladorException;

	/**
	 * Ordena uma coleção de clientes a partir do nome de forma alfabética
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
