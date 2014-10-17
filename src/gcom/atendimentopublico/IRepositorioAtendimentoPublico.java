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

package gcom.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade;
import gcom.atendimentopublico.ordemservico.bean.ObterValorDebitoHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Interfe, que disponibiliza os serviços do Repositório Atendimento Público
 * 
 * @author Leandro Cavalcanti
 * @date 10/07/2006
 */
public interface IRepositorioAtendimentoPublico {

	/**
	 * [UC-0355] - Efetuar Corte de Ligaçã de Àgua
	 * [SB001] Atualizar Hidrometro - (corte de ligação de água)
	 * Atualizar um os campos lastId,dataUltimaAtualização do imovel na base
	 * 
	 * @author Leandro Cavalcanti
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarLigacaoAgua(Integer idImovel, Integer idLigacaoAguaSituacao, Integer numeroSeloCorte)
					throws ErroRepositorioException;

	/**
	 * [UC-0355] - Efetuar Corte de Ligaçã de Àgua
	 * [SB001] Atualizar Hidrometro - (corte de ligação de água)
	 * Atualizar os campos hidi_nnleituracorte e hidi_tmultimaalteracao de
	 * HidrometroInstalacaoHistorico
	 * 
	 * @author Leandro Cavalcanti
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarHidrometroLIgacaoAgua(Integer imovelId, Integer numeroLeituraCorte) throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro
	 * [SB002] Atualizar Ligação de Água
	 * Atualizar os campos hidi_id e lagu_tmultimaalteracao de LigacaoAgua
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param idLigacaoAgua
	 * @param idHidrometroInstalacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroInstalacaoHistoricoLigacaoAgua(Integer idLigacaoAgua, Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro
	 * [SB002] Atualizar Imóvel
	 * Atualizar os campos hidi_id e imov_tmultimaalteracao de Imovel
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param idImovel
	 * @param idHidrometroInstalacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroIntalacaoHistoricoImovel(Integer idImovel, Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro
	 * [SB003] Atualizar Hidrômetro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoHidrometro(Integer idHidrometro, Integer situacaoHidrometro) throws ErroRepositorioException;

	/**
	 * [UC0396] - Inserir Tipo de retorno da OS Referida
	 * [FS0005] Validar indicador de deferimento
	 * 
	 * @author lms
	 * @date 31/07/2006
	 * @throws ErroRepositorioException
	 */
	public int consultarTotalIndicadorDeferimentoAtivoPorServicoTipoReferencia(OsReferidaRetornoTipo osReferidaRetornoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarConsumoMinimoLigacaoAgua(LigacaoAgua ligacaoAgua) throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro
	 * [SB003] Atualizar Hidrômetro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarLocalArmazanagemHidrometro(Integer idHidrometro, Integer localArmazanagemHidrometro)
					throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro
	 * [SB003] Atualizar Hidrômetro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroInstalacoHistorico(HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro na ligação de água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro no imóvel.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmImovel(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Obter Capacidade de Hidrômetro pela Ligação de Água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Obter Capacidade de Hidrômetro pelo Imóvel.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmImovel(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Obter Valor do Debito pelos parâmtros passados.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param obterValorDebitoHelper
	 * @return o valor do débito
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterValorDebito(ObterValorDebitoHelper params) throws ErroRepositorioException;

	/**
	 * Método que retorna o número do hidrômetro da ligação de água
	 * 
	 * @author Ana Maria
	 * @date 12/09/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */

	public String pesquisarNumeroHidrometroLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * Método que retorna o tipo da ligação de água e a data do corte da ligação de água
	 * 
	 * @author Ana Maria
	 * @date 18/08/2006
	 * @param idLigacaoAgua
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * Consulta os dados das ordens de serviço para a geração do relatório
	 * 
	 * @author Rafael Corrêa
	 * @created 07/10/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicoProgramacaoRelatorio(Integer idEquipe, Date dataRoteiro) throws ErroRepositorioException;

	/**
	 * [UC0404] Manter Especificação da Situação do Imovel
	 * Este caso de uso remove a especificação e os critério
	 * [SB0002] Remover Especificação da situacao
	 * 
	 * @author Rafael Pinto
	 * @created 08/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerEspecificacaoSituacaoImovelCriterio(String[] idsEspecificacaoSituacaoImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa todos os ids das situações de ligação de água.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoAgua() throws ErroRepositorioException;

	/**
	 * Pesquisa todos os ids das situações de ligação de esgoto.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoEsgoto() throws ErroRepositorioException;

	/**
	 * Este cso de uso permite efetuar a ligação de água e eventualmente
	 * a instalação de hidrômetro, sem informação de RA sendo chamado direto pelo menu.
	 * [UC0579] - Efetuar Ligação de Água com Intalação de Hidrômetro
	 * 
	 * @author Flávio Leonardo
	 * @date 25/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEfetuarLigacaoAguaHidrometroSemRA(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Contrato de Prestação de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 03/05/2007
	 * @throws ErroRepositorioException
	 */
	public Collection obterDadosContratoPrestacaoServico(Integer idImovel) throws ErroRepositorioException;

	public void atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(Integer idImovel, Integer idHidrometro) throws ErroRepositorioException;

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Obtém os dados necessário da ligação de água, de esgoto e do hidrômetro
	 * instalado na ligação de água
	 * 
	 * @author Rafael Corrêa
	 * @date 17/05/2007
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosLigacaoAguaEsgoto(Integer idImovel) throws ErroRepositorioException;

	// *********************************************************
	// ****************CONTRATO PESSOA JURIDICA*****************

	public Cliente pesquisaClienteContrato(Integer idCliente) throws ErroRepositorioException;

	public ClienteImovel pesquisarDadosContratoJuridica(Integer idImovel) throws ErroRepositorioException;

	public String pesquisarMunicipio(Integer idImovel) throws ErroRepositorioException;

	// *********************************************************

	/**
	 * Substituicao de hidrometro
	 */
	public void atualizarSubstituicaoHidrometroInstalacoHistorico(HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC0482]Emitir 2ª Via de Conta
	 *obter numero do hidrômetro na ligação de água.
	 * 
	 * @author Vivianne Sousa
	 * @date 11/09/2007
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public String obterNumeroHidrometroEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException;

	/**
	 * Método responsável por retornar um servicoTipoPrioridade a partir do seu id.
	 * 
	 * @param idServicoTipoPrioridade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ServicoTipoPrioridade pesquisarServicoTipoPrioridade(Integer idServicoTipoPrioridade) throws ErroRepositorioException;

	/**
	 * [UC0738] Retorna as informações para o relatório de certidão negativa
	 * 
	 * @param imo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioCertidaoNegativa(Imovel imo) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necessários para a geração do relatório
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioCertidaoNegativaCliente(Collection<Integer> idsClientes) throws ErroRepositorioException;

	/**
	 * [UC3135] Gerar Relatório de Materiais Aplicados
	 * 
	 * @author Felipe Rosacruz
	 * @date 03/02/2014
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterDadosRelatorioMateriaisAplicados(Integer idLocalidade, Integer cdSetorComercial,
					Date dataExecucaoInicial,
					Date dataExecucaoFinal, Collection<Integer> colecaoIdServicoTipo, Collection<Integer> colecaoIdMaterial,
					Collection<Integer> colecaoIdEquipe) throws ErroRepositorioException;
}
