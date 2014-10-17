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

package gcom.gerencial.cadastro;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.cadastro.bean.DetalheLigacaoEconomiaGCSHelper;
import gcom.gerencial.cadastro.bean.ResumoLigacaoEconomiaConsultarHelper;
import gcom.gerencial.cadastro.bean.SumarioLigacaoPorCategoriaGCSHelper;
import gcom.util.ControladorException;

import java.util.Date;
import java.util.List;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public interface ControladorGerencialCadastroLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Método que gera o resumo das ligações e economias
	 * [UC0275] - Gerar Resumo das Ligações/Economias
	 * 
	 * @author Thiago Toscano
	 * @date 19/04/2006
	 */
	public void gerarResumoLigacoesEconomias(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0344] Consultar Resumo Anormalidade
	 * 
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List<ResumoLigacaoEconomiaConsultarHelper> consultarResumoLigacoesEconomias(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException;

	public void gerarResumoConsumoAgua(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Método que gera o resumo do Parcelamento
	 * [UC0565] - Gerar Resumo do Parcelamento
	 * 
	 * @author Marcio Roberto
	 * @param anoMes
	 * @date 04/05/2007
	 */
	public void gerarResumoParcelamento(int idLocalidade, int idFuncionalidadeIniciada, int anoMes) throws ControladorException;

	/**
	 * Método que gera o resumo dos histogramas de água e esgoto
	 * [UC0566] - Gerar Histograma de Água e Esgoto
	 * 
	 * @author Bruno Barros
	 * @date 09/05/2007
	 */
	public void gerarResumoHistograma(int anoMesReferencia, int idSetor, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Método que gera o resumo de Metas da CAERN
	 * [UC0623] - Gerar Resumo de Metas
	 * 
	 * @author Sávio Luiz
	 * @date 20/07/2007
	 */
	public void gerarResumoMetas(int idSetor, Date dataInicial, Date dataFinal, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Método que gera o resumo de Metas da CAERN
	 * [UC0623] - Gerar Resumo de Metas
	 * 
	 * @author Sávio Luiz
	 * @date 20/07/2007
	 */
	public void gerarResumoMetasAcumulado(int idSetor, Integer anoMesArrecadacao, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Método que gera o resumo do coleta esgoto
	 * [UC0573] - Gerar Resumo do coleta esgoto
	 * 
	 * @author Marcio Roberto
	 * @date 18/09/2007
	 */
	public void gerarResumoColetaEsgoto(int idSetor, int idFuncionalidadeIniciada) throws ControladorException;

	public Integer consultarQtdRegistrosResumoLigacoesEconomias(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException;

	/**
	 * Método consultarResumoLigacoesEconomiasGCS
	 * <p>
	 * Esse método implementa consulta dos dados para relatorio de resumo ligacoes economia tal como
	 * no GCS.
	 * </p>
	 * RASTREIO: [OC897714][UC269]
	 * 
	 * @param parametrosPesquisa
	 *            dados do formulario de pesquisa,
	 *            {@link InformarDadosGeracaoRelatorioConsultaHelper}.
	 * @return Lista de {@link DetalheLigacaoEconomiaGCSHelper}.
	 * @author Marlos Ribeiro
	 * @since 20/11/2012
	 */
	public List<DetalheLigacaoEconomiaGCSHelper> consultarDetalhesLigacoesEconomiasGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa, Integer paginacao) throws ControladorException;

	/**
	 * Método consultarResumoLigacoesEconomiasGCS
	 * <p>
	 * Esse método implementa consulta dos dados para relatorio de resumo ligacoes economia tal como
	 * no GCS.
	 * </p>
	 * RASTREIO: [OC897714][UC269]
	 * 
	 * @param parametrosPesquisa
	 *            dados do formulario de pesquisa,
	 *            {@link InformarDadosGeracaoRelatorioConsultaHelper}.
	 * @return Lista de {@link DetalheLigacaoEconomiaGCSHelper}.
	 * @author Marlos Ribeiro
	 * @since 21/11/2012
	 */
	public List<SumarioLigacaoPorCategoriaGCSHelper> consultarSumarioLigacoesPorCategoriaGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa) throws ControladorException;
}