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

package gcom.gerencial.cobranca;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaAcumuladoHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.List;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public interface ControladorGerencialCobrancaLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Método que gera o resumo Resumo Situacao Especial Faturamento
	 * [UC0341]
	 * 
	 * @author Thiago Toscano
	 * @date 19/04/2006
	 */
	public void gerarResumoSituacaoEspecialCobranca(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Método que gera o resumo da pendencia
	 * [UC0335] - Gerar Resumo do Parcelamento
	 * 
	 * @author Bruno Barros
	 * @date 19/07/2007
	 */
	public void gerarResumoPendencia(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de impressão da
	 * consulta.
	 * Dependendo da opção de totalização sempre é gerado o relatório, sem a feração da consulta.
	 * [UC0338] Consultar Resumo da Pendência
	 * Gera a lista de pendências das Contas e Guias de Pagamento
	 * consultarResumoPendencia
	 * 
	 * @author Roberta Costa
	 * @date 24/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException;

	public Collection recuperaResumoSituacaoEspecialCobranca(Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo)
					throws ControladorException;

	/**
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de impressão da
	 * consulta.
	 * Dependendo da opção de totalização sempre é gerado o relatório, sem a feração da consulta.
	 * [UC0338] Consultar Resumo da Pendência
	 * Retorna os registro de resumo pendência dividindo em coleções de categoria RESIDENCIAL,
	 * COMERCIAL,
	 * INDUSTRIAL e PUBLICA
	 * retornaConsultaResumoPendencia
	 * 
	 * @author Roberta Costa
	 * @date 31/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResumoPendenciaAcumuladoHelper> retornaConsultaResumoPendencia(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException;

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return CobrancaAcaoHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcao(InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ControladorException;

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return CobrancaAcaoHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoPerfil(int anoMesReferencia, Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException;

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * Pesquisa as situações de débito da situação da ação de acordo com o
	 * indicador antesApos
	 * 
	 * @author Sávio Luiz
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, Integer idCobrancaAcaoDebito) throws ControladorException;

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException;

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoEventualPerfil(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException;

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao, Integer idCobrancaAcaoDebito) throws ControladorException;

	public Collection consultarResumoCobrancaAcaoEventual(Integer idCobrancaAcao,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException;

}