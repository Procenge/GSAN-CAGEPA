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

import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * Interface para o repositório de cliente
 * 
 * @author Sávio Luiz
 * @created 22 de Abril de 2005
 */
public interface IRepositorioCliente {

	/**
	 * pesquisa uma coleção de responsavel(is) de acordo com critérios
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
	 *            Código do cliente que terá seus telefones apagados
	 * @exception ErroRepositorioException
	 *                Erro no BD
	 */
	public void removerTodosTelefonesPorCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Remove todos os endereços de um determinado cliente
	 * 
	 * @param idCliente
	 *            Código do cliente que terá seus endereços apagados
	 * @exception ErroRepositorioException
	 *                Erro no BD
	 */
	public void removerTodosEnderecosPorCliente(Integer idCliente) throws ErroRepositorioException;

	public Collection<Cliente> pesquisarClienteDadosClienteEndereco(FiltroCliente filtroCliente, Integer numeroPagina)
					throws ErroRepositorioException;

	public Collection<Cliente> pesquisarClienteDadosClienteEnderecoRelatorio(FiltroCliente filtroCliente) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarClienteDadosClienteEnderecoCount(FiltroCliente filtroCliente) throws ErroRepositorioException;

	public Collection<Cliente> pesquisarClienteOutrosCriterios(FiltroCliente filtroCliente) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores Author: Sávio Luiz Data:
	 * 01/02/2006
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Integer verificarExistenciaCliente(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa um cliente carregando os dados do relacionamento com ClienteTipo
	 * [UC0321] Emitir Fatura de Cliente Responsável
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
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @return Cliente
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarObjetoClienteRelatorio(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa as quantidades de imóveis e as quantidades de economias
	 * associadas a um cliente
	 * 
	 * @author Rafael Corrêa
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
	 * Pesquisa todos os endereços do cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecosCliente(Integer idCliente) throws ErroRepositorioException;

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
	 *            Adicionado à consulta
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
	 *       Adição do parâmetro de Inscrição Estadual para Consulta
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
	 * Pesquisa os dados do cliente para a emissão do boletim
	 * 
	 * @author Rafael Corrêa
	 * @date 16/05/2007
	 * @param idImovel
	 *            , clienteRelacaoTipo
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarClienteEmitirBoletimCadastro(Integer idImovel, Integer clienteRelacaoTipo) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 27/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterIdENomeCliente(String cpf) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
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
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @author Rafael Corrêa
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
	 * @author Hebert Falcão
	 * @date 20/05/2011
	 */
	public Collection pesquisarClientesContaPeloImovelEConta(Integer idImovel, Integer idConta) throws ErroRepositorioException;

}
