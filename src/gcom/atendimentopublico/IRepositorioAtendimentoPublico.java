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
 * Interfe, que disponibiliza os servi�os do Reposit�rio Atendimento P�blico
 * 
 * @author Leandro Cavalcanti
 * @date 10/07/2006
 */
public interface IRepositorioAtendimentoPublico {

	/**
	 * [UC-0355] - Efetuar Corte de Liga�� de �gua
	 * [SB001] Atualizar Hidrometro - (corte de liga��o de �gua)
	 * Atualizar um os campos lastId,dataUltimaAtualiza��o do imovel na base
	 * 
	 * @author Leandro Cavalcanti
	 * @param imovel
	 *            Descri��o do par�metro
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public void atualizarLigacaoAgua(Integer idImovel, Integer idLigacaoAguaSituacao, Integer numeroSeloCorte)
					throws ErroRepositorioException;

	/**
	 * [UC-0355] - Efetuar Corte de Liga�� de �gua
	 * [SB001] Atualizar Hidrometro - (corte de liga��o de �gua)
	 * Atualizar os campos hidi_nnleituracorte e hidi_tmultimaalteracao de
	 * HidrometroInstalacaoHistorico
	 * 
	 * @author Leandro Cavalcanti
	 * @param imovel
	 *            Descri��o do par�metro
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public void atualizarHidrometroLIgacaoAgua(Integer imovelId, Integer numeroLeituraCorte) throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instala��o de Hidr�metro
	 * [SB002] Atualizar Liga��o de �gua
	 * Atualizar os campos hidi_id e lagu_tmultimaalteracao de LigacaoAgua
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descri��o da exce��o
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param idLigacaoAgua
	 * @param idHidrometroInstalacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroInstalacaoHistoricoLigacaoAgua(Integer idLigacaoAgua, Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instala��o de Hidr�metro
	 * [SB002] Atualizar Im�vel
	 * Atualizar os campos hidi_id e imov_tmultimaalteracao de Imovel
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descri��o da exce��o
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param idImovel
	 * @param idHidrometroInstalacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroIntalacaoHistoricoImovel(Integer idImovel, Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instala��o de Hidr�metro
	 * [SB003] Atualizar Hidr�metro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descri��o da exce��o
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
	 * [UC0463] Atualizar Consumo M�nimo da Liga��o de �gua
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarConsumoMinimoLigacaoAgua(LigacaoAgua ligacaoAgua) throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instala��o de Hidr�metro
	 * [SB003] Atualizar Hidr�metro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descri��o da exce��o
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarLocalArmazanagemHidrometro(Integer idHidrometro, Integer localArmazanagemHidrometro)
					throws ErroRepositorioException;

	/**
	 * [UC-0362] - Efetuar Instala��o de Hidr�metro
	 * [SB003] Atualizar Hidr�metro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descri��o da exce��o
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroInstalacoHistorico(HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Verificar exist�ncia de hidr�metro na liga��o de �gua.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Verificar exist�ncia de hidr�metro no im�vel.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmImovel(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Obter Capacidade de Hidr�metro pela Liga��o de �gua.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Obter Capacidade de Hidr�metro pelo Im�vel.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmImovel(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Obter Valor do Debito pelos par�mtros passados.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param obterValorDebitoHelper
	 * @return o valor do d�bito
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterValorDebito(ObterValorDebitoHelper params) throws ErroRepositorioException;

	/**
	 * M�todo que retorna o n�mero do hidr�metro da liga��o de �gua
	 * 
	 * @author Ana Maria
	 * @date 12/09/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */

	public String pesquisarNumeroHidrometroLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * M�todo que retorna o tipo da liga��o de �gua e a data do corte da liga��o de �gua
	 * 
	 * @author Ana Maria
	 * @date 18/08/2006
	 * @param idLigacaoAgua
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * Consulta os dados das ordens de servi�o para a gera��o do relat�rio
	 * 
	 * @author Rafael Corr�a
	 * @created 07/10/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicoProgramacaoRelatorio(Integer idEquipe, Date dataRoteiro) throws ErroRepositorioException;

	/**
	 * [UC0404] Manter Especifica��o da Situa��o do Imovel
	 * Este caso de uso remove a especifica��o e os crit�rio
	 * [SB0002] Remover Especifica��o da situacao
	 * 
	 * @author Rafael Pinto
	 * @created 08/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerEspecificacaoSituacaoImovelCriterio(String[] idsEspecificacaoSituacaoImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa todos os ids das situa��es de liga��o de �gua.
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoAgua() throws ErroRepositorioException;

	/**
	 * Pesquisa todos os ids das situa��es de liga��o de esgoto.
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoEsgoto() throws ErroRepositorioException;

	/**
	 * Este cso de uso permite efetuar a liga��o de �gua e eventualmente
	 * a instala��o de hidr�metro, sem informa��o de RA sendo chamado direto pelo menu.
	 * [UC0579] - Efetuar Liga��o de �gua com Intala��o de Hidr�metro
	 * 
	 * @author Fl�vio Leonardo
	 * @date 25/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEfetuarLigacaoAguaHidrometroSemRA(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Contrato de Presta��o de Servi�o
	 * 
	 * @author Rafael Corr�a
	 * @date 03/05/2007
	 * @throws ErroRepositorioException
	 */
	public Collection obterDadosContratoPrestacaoServico(Integer idImovel) throws ErroRepositorioException;

	public void atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(Integer idImovel, Integer idHidrometro) throws ErroRepositorioException;

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Obt�m os dados necess�rio da liga��o de �gua, de esgoto e do hidr�metro
	 * instalado na liga��o de �gua
	 * 
	 * @author Rafael Corr�a
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
	 * [UC0482]Emitir 2� Via de Conta
	 *obter numero do hidr�metro na liga��o de �gua.
	 * 
	 * @author Vivianne Sousa
	 * @date 11/09/2007
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public String obterNumeroHidrometroEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por retornar um servicoTipoPrioridade a partir do seu id.
	 * 
	 * @param idServicoTipoPrioridade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ServicoTipoPrioridade pesquisarServicoTipoPrioridade(Integer idServicoTipoPrioridade) throws ErroRepositorioException;

	/**
	 * [UC0738] Retorna as informa��es para o relat�rio de certid�o negativa
	 * 
	 * @param imo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioCertidaoNegativa(Imovel imo) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rios para a gera��o do relat�rio
	 * [UC0864] Gerar Certid�o Negativa por Cliente
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioCertidaoNegativaCliente(Collection<Integer> idsClientes) throws ErroRepositorioException;

	/**
	 * [UC3135] Gerar Relat�rio de Materiais Aplicados
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
