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

package gcom.gerencial.faturamento;

import gcom.faturamento.ResumoFaturamentoSituacaoEspecial;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.List;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public interface IRepositorioGerencialFaturamento {

	/**
	 * Método que consulta os ResumoFaturamentoSituacaoEspecialHelper
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoFaturamentoSituacaoEspecialHelper(Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Método que insere o ResumoFaturamentoSituacaoEspecial em batch
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @param listResumoFaturamentoSituacaoEspecialHelper
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoFaturamentoSituacaoEspecial(List<ResumoFaturamentoSituacaoEspecial> list) throws ErroRepositorioException;

	/**
	 * Método que exclui todos os ResumoFaturamentoSituacaoEspecial
	 * [CU0341] - Gerar Resumo de Situacao Especial de Faturamento
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @throws ErroRepositorioException
	 */
	public void excluirTodosResumoFaturamentoSituacaoEspecial(Integer idLocalidade) throws ErroRepositorioException;

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaHelper(Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo)
					throws ErroRepositorioException;

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaGerenciaRegionalHelper(Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException;
	
	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper(Integer idGerencia, Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper(Integer idGerenciaRegional,
					Integer idUnidadeNegocio, Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper(Integer idGerencia, Integer IdLocalidade,
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaMotivoHelper(Integer idGerencia, Integer idLocalidade,
					Integer idTipo, Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de impressão da
	 * consulta.
	 * Dependendo da opção de totalização sempre é gerado o relatório, sem a feração da consulta.
	 * [UC0305] Consultar Análise Faturamento
	 * consultarResumoAnaliseFaturamento
	 * 
	 * @author Fernanda Paiva
	 * @date 31/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoAnaliseFaturamento(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do faturamento
	 * 
	 * @author Marcio Roberto
	 * @date 12/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoFaturamentoAguaEsgoto(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa os imoveis com debitos acobrar
	 * 
	 * @author Roberto Barbalho
	 * @date 26/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoFaturamento(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o debito cobrado
	 * 
	 * @author Marcio Roberto
	 * @date 17/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPesquisaDebitoCobrado(int idConta, int idImovel, int anoMes) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa os impostos deduzidos
	 * 
	 * @author Roberto Barbalho
	 * @date 28/08/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPesquisaCreditoRealizado(int idSetor, int mesAno) throws ErroRepositorioException;

	/**
	 * pesquisarEsferaPoderClienteResponsavelImovel
	 * 
	 * @author Marcio Roberto
	 * @date 05/06/2007
	 * @param imovel
	 *            a ser pesquisado
	 * @return Esfera de poder do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * pesquisarTipoClienteClienteResponsavelImovel
	 * 
	 * @author Marcio Roberto
	 * @date 05/06/2007
	 * @param imovel
	 *            a ser pesquisado
	 * @return Tipo de cliente do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarTipoClienteClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * Re-faturamento
	 * 
	 * @author Marcio Roberto
	 * @date 12/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoReFaturamento(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa as contas do resumo do faturamento
	 * 
	 * @author Marcio Roberto
	 * @date 03/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getContasResumoFaturamentoAguaEsgoto(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException;

	public Collection<Integer> pesquisarIdsSetores() throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento Debito a Cobrar
	 * 
	 * @author Marcio Roberto
	 * @date 17/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPesquisaDebitoACobrar(int idSetor, int anoMes) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento Debito a Cobrar
	 * 
	 * @author Roberto Barbalho
	 * @date 28/08/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPesquisaImpostos(int idConta) throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento (Guia de Pagamento)
	 * 
	 * @author Marcio Roberto
	 * @date 05/09/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPesquisaGuiaPagamento(int idSetor, int anoMes) throws ErroRepositorioException;
}