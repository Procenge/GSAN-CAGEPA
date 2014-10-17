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

package gcom.gerencial.micromedicao;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.relatorio.gerencial.micromedicao.RelatorioResumoAnormalidadeLeituraBean;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Fl�vio Cordeiro
 * @created 17/05/2006
 */
public interface IRepositorioGerencialMicromedicao {

	/**
	 * M�todo que consulta os ResumoAnormalidadeHelper
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 17/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoAnormalidadeHelper(String anoMes) throws ErroRepositorioException;

	/**
	 * M�todo que ap�s consultar os ResumoAnormalidadeHelper
	 * no metodo getResumoAnormalidadeHelper(String anoMes)
	 * pega os ids de ligacao agua e pesquisam o imovel apartir dele
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 19/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarImovelPorIdLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	public List getResumoAnormalidadeConsumoHelper(String anoMes) throws ErroRepositorioException;

	public String criarCondicionaisResumos(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper,
					String nomeColunaTabela);

	public List consultarResumoAnormalidadeLeitura(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper,
					Integer tipoLigacao)
					throws ErroRepositorioException;

	public Integer consultarTotalResumoSemAnormalidade(int opcaoTotalizacao,
					InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta, ResumoAnormalidadeConsultaHelper itemAnterior,
					RelatorioResumoAnormalidadeLeituraBean bean) throws ErroRepositorioException;

	/**
	 * Gera o resumo das insta��es de hidr�metro para o ano/m�s
	 * de refer�ncia da arrecada��o.
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * Verificar exist�ncia de dados para o ano/m�s de refer�ncia da arrecada��o
	 * do Resumo das instala��es de hidr�metros.
	 * [FS0001 � Verificar exist�ncia de dados para o ano/m�s de refer�ncia da arrecada��o]
	 * 
	 * @author Pedro Alexandre
	 * @date 24/04/2007
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaResumoInstalacaoHidrometroParaAnoMesReferenciaArrecadacao(Integer anoMesReferenciaArrecadacao,
					Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * Pesquisa os dados do setor comercial.
	 * 
	 * @author Pedro Alexandre
	 * @date 24/04/2007
	 * @param idSetorComercial
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosSetorComercial(Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * Pesquisa a cole��o de ids de quadras para o setor comercial informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 24/04/2007
	 * @param idSetorComercial
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDadosQuadrasPorSetorComercial(Integer idSetorComercial, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * Pesquisar dados do im�vel pela quadra.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/04/2007
	 * @param idQuadra
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDadosImovelResumoInstalacaoHidrometroPorQuadra(Integer idQuadra, Date dataInicio, Date dataFim)
					throws ErroRepositorioException;

	/**
	 * [UC0564] Gerar Resumo das Instala��es de Hidr�metros
	 * Pesquisa os dados do cliente respons�vel do im�vel informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosClienteResponsavelPorImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0564] Gerar Resumo das Instala��es de Hidr�metros
	 * Pesquisa os dados da instala��o do hidr�metro no hist�rico
	 * para o im�vel informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2007
	 * @param idImovel
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosHidrometroInstalacaoHistoricoPorImovel(Integer idImovel, Date dataInicio, Date dataFim)
					throws ErroRepositorioException;

	/**
	 * M�todo que retona uma lista de objeto xxxxx que representa o
	 * UC0551 - Gerar Resumo Leitura Anormalidade
	 * 
	 * @author Ivan S�rgio
	 * @date 23/04/2007
	 * @param anoMesLeitura
	 *            - Ano Mes da Leitura
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLeituraAnormalidade(int localidade, int anoMesLeitura) throws ErroRepositorioException;

	/**
	 * [UC0564] Gerar Resumo das Instala��es de Hidr�metros
	 * Pesquisa os ids dos setores comercias dos im�veis
	 * que tem hidrometro instalado no hist�rico
	 * 
	 * @author Pedro Alexandre
	 * @date 08/05/2007
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsSetorComercialParaGerarResumoInstalacaoHidrometro(Integer anoMesFaturamento)
					throws ErroRepositorioException;

	/**
	 * M�todo que retona uma lista de objeto xxxxx que representa o
	 * UC0586 - Gerar Resumo Hidrometro
	 * 
	 * @author Thiago Ten�rio
	 * @date 23/04/2007
	 * @param anoMesArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection getHidrometrosResumoHidrometro(Integer idLocalArmazenagem, int indice, int qtRegistros)
					throws ErroRepositorioException;

	/**
	 * [FS0003] - Verificar Existencia de conta para o mes de faturamento
	 * Metodo utilizado para auxiliar o [UC0551 - Gerar Resumo Leitura Anormalidade]
	 * para recuperar o valo da Situacao da Ligacao de Agua.
	 * 
	 * @author Ivan S�rgio
	 * @date 27/07/2007, 08/08/2007
	 * @alteracao - Receber os outros campos da getImoveisResumoLeituraAnormalidade;
	 * @throws ErroRepositorioException
	 * @return List
	 */
	public List pesquisarLeituraAnormalidadeComplementar(Integer imovel, Integer dataFaturamento) throws ErroRepositorioException;

	/**
	 * calcula a quantidade de hidrometro instalados ramal de �gua
	 * [UC0564] Gerar Resumo das Instala��es de Hidr�metro
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2007
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeHidrometroInstalacaoRamalAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * calcula a quantidade de hidrometro instalados atualmente no po�o
	 * [UC0564] Gerar Resumo das Instala��es de Hidr�metro
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeHidrometroInstalacaoPoco(Integer idImovel) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma lista de registros de anormalidades de consulmo filtrados pelo tipo de
	 * ligacao (Agua / Esgoto)
	 * [UC0344] Consultar Resumo de Anormalidades
	 * 
	 * @date 27/06/2012
	 * @author Marlos Ribeiro
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @param tipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoAnormalidadeConsumo(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper, Integer tipoLigacao)
					throws ErroRepositorioException;

	/**
	 * M�todo que Verificar Exist�ncia de Dados para o Ano/M�s de Refer�ncia do Faturamento.
	 * 
	 * @author Josenildo Neves
	 * @date 24/07/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaExistenciaDadosParaAnoMesRefFaturamentoResumoAnormalidadeLeitura(String anoMes)
					throws ErroRepositorioException;

	/**
	 * M�todo que Verificar Exist�ncia de Dados para o Ano/M�s de Refer�ncia do Faturamento.
	 * 
	 * @author Josenildo Neves
	 * @date 24/07/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaExistenciaDadosParaAnoMesRefFaturamentoResumoAnormalidadeConsumo(String anoMes) throws ErroRepositorioException;

}