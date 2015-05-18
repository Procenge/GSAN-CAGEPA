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

import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * Interface para o reposit�rio de cliente
 * 
 * @author S�vio Luiz
 * @created 22 de Abril de 2005
 */
public interface IRepositorioCliente {

	/**
	 * pesquisa uma cole��o de responsavel(is) de acordo com crit�rios
	 * existentes no filtro
	 * 
	 * @param filtroCliente
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarResponsavelSuperior(FiltroCliente filtroCliente) throws ErroRepositorioException;

	/**
	 * Remove todos os telefones de um determinado cliente
	 * 
	 * @param idCliente
	 *            C�digo do cliente que ter� seus telefones apagados
	 * @exception ErroRepositorioException
	 *                Erro no BD
	 */
	public void removerTodosTelefonesPorCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Remove todos os endere�os de um determinado cliente
	 * 
	 * @param idCliente
	 *            C�digo do cliente que ter� seus endere�os apagados
	 * @exception ErroRepositorioException
	 *                Erro no BD
	 */
	public void removerTodosEnderecosPorCliente(Integer idCliente) throws ErroRepositorioException;

	public Collection<Cliente> pesquisarClienteDadosClienteEndereco(FiltroCliente filtroCliente, Integer numeroPagina)
					throws ErroRepositorioException;

	public Collection<Cliente> pesquisarClienteDadosClienteEnderecoRelatorio(FiltroCliente filtroCliente) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarClienteDadosClienteEnderecoCount(FiltroCliente filtroCliente) throws ErroRepositorioException;

	public Collection<Cliente> pesquisarClienteOutrosCriterios(FiltroCliente filtroCliente) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores Author: S�vio Luiz Data:
	 * 01/02/2006
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param anoMesReferencia
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */

	public Integer verificarExistenciaCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa um cliente carregando os dados do relacionamento com ClienteTipo
	 * [UC0321] Emitir Fatura de Cliente Respons�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2006
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa um cliente pelo id
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * @return Cliente
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarObjetoClienteRelatorio(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa as quantidades de im�veis e as quantidades de economias
	 * associadas a um cliente
	 * 
	 * @author Rafael Corr�a
	 * @date 23/08/2007
	 * @return Object[]
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarQtdeImoveisEEconomiasCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa todos os telefones de um cliente
	 * 
	 * @author Raphael Rossiter
	 * @date 23/08/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteFone(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados do Cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa todos os endere�os do cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecosCliente(Integer idCliente) throws ErroRepositorioException;

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

	public Object[] pesquisarClienteFonePrincipal(Integer idCliente) throws ErroRepositorioException;

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
					String indicadorContaBraille, String documentoValidado, String numeroBeneficio)
					throws ErroRepositorioException;

	/**
	 * Usado pelo Filtrar Cliente
	 * Filtra a quantidade de Clientes usando os paramentos informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 *       Adi��o do par�metro de Inscri��o Estadual para Consulta
	 * @param inscricaoEstadual
	 *            TODO
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object filtrarQuantidadeCliente(String codigo, String cpf, String rg, String cnpj, String nome, String nomeMae, String cep,
					String idMunicipio, String idBairro, String idLogradouro, String indicadorUso, String tipoPesquisa,
					String tipoPesquisaNomeMae, String clienteTipo, String inscricaoEstadual, String indicadorContaBraille,
					String documentoValidado, String numeroBeneficio)
					throws ErroRepositorioException;

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Pesquisa os dados do cliente para a emiss�o do boletim
	 * 
	 * @author Rafael Corr�a
	 * @date 16/05/2007
	 * @param idImovel
	 *            , clienteRelacaoTipo
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarClienteEmitirBoletimCadastro(Integer idImovel, Integer clienteRelacaoTipo) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Vivianne Sousa
	 * @date 27/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterIdENomeCliente(String cpf) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarCpfCnpjCliente(String cpfCnpj, Integer idCliente, String indicadorPessoaFisicaJuridica)
					throws ErroRepositorioException;

	/**
	 * @author Andre Nishimura
	 * @date 27/01/2011
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCadastroClientesRedundantes() throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesArrecadador(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesArrecadadorContrato(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesAtendimento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteConta(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteContaHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteEndereco(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteFone(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteGuiaPagamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteGuiaPagamentoHist(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteImovel(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesClienteImovelEconomia(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesCobrancaAcaoAtividadeComando(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesCobrancaDocumento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesContaImpressao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesDevolucao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesDevolucaoHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesDocumentoNaoEntregue(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesEntidadeBeneficente(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesFatura(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesGuiaDevolucao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesGuiaPagamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesGuiaPagamentoHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesImovelCobrancaSituacao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesLeiturista(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesLocalidade(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesNegativacaoCriterio(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesNegativador(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesNegativadorMovimentoReg(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesPagamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesPagamentoHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesParcelamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesRegistroAtendimentoSolicitante(Cliente clienteASerMantido,
					Collection clientesASeremSubstituidos) throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesSistemaParametro(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesVencimentoAlternativo(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	public Integer atualizaClientesRedundantesCliente(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException;

	/**
	 * [UC0864] Gerar Certid�o Negativa por Cliente
	 * 
	 * @author Rafael Corr�a
	 * @date 25/09/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarClientesAssociadosResponsavel(Integer idCliente) throws ErroRepositorioException;

	/**
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteFonePorCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * [UC0204]
	 * 
	 * @author Hebert Falc�o
	 * @date 20/05/2011
	 */
	public Collection pesquisarClientesContaPeloImovelEConta(Integer idImovel, Integer idConta) throws ErroRepositorioException;

}
