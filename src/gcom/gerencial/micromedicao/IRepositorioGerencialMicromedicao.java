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

package gcom.gerencial.micromedicao;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.relatorio.gerencial.micromedicao.RelatorioResumoAnormalidadeLeituraBean;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Flávio Cordeiro
 * @created 17/05/2006
 */
public interface IRepositorioGerencialMicromedicao {

	/**
	 * Método que consulta os ResumoAnormalidadeHelper
	 * 
	 * @author Flávio Cordeiro
	 * @date 17/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoAnormalidadeHelper(String anoMes) throws ErroRepositorioException;

	/**
	 * Método que após consultar os ResumoAnormalidadeHelper
	 * no metodo getResumoAnormalidadeHelper(String anoMes)
	 * pega os ids de ligacao agua e pesquisam o imovel apartir dele
	 * 
	 * @author Flávio Cordeiro
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
	 * Gera o resumo das instações de hidrômetro para o ano/mês
	 * de referência da arrecadação.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * Verificar existência de dados para o ano/mês de referência da arrecadação
	 * do Resumo das instalações de hidrômetros.
	 * [FS0001 – Verificar existência de dados para o ano/mês de referência da arrecadação]
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
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
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
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * Pesquisa a coleção de ids de quadras para o setor comercial informado.
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
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * Pesquisar dados do imóvel pela quadra.
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
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetros
	 * Pesquisa os dados do cliente responsável do imóvel informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosClienteResponsavelPorImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetros
	 * Pesquisa os dados da instalação do hidrômetro no histórico
	 * para o imóvel informado.
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
	 * Método que retona uma lista de objeto xxxxx que representa o
	 * UC0551 - Gerar Resumo Leitura Anormalidade
	 * 
	 * @author Ivan Sérgio
	 * @date 23/04/2007
	 * @param anoMesLeitura
	 *            - Ano Mes da Leitura
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLeituraAnormalidade(int localidade, int anoMesLeitura) throws ErroRepositorioException;

	/**
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetros
	 * Pesquisa os ids dos setores comercias dos imóveis
	 * que tem hidrometro instalado no histórico
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
	 * Método que retona uma lista de objeto xxxxx que representa o
	 * UC0586 - Gerar Resumo Hidrometro
	 * 
	 * @author Thiago Tenório
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
	 * @author Ivan Sérgio
	 * @date 27/07/2007, 08/08/2007
	 * @alteracao - Receber os outros campos da getImoveisResumoLeituraAnormalidade;
	 * @throws ErroRepositorioException
	 * @return List
	 */
	public List pesquisarLeituraAnormalidadeComplementar(Integer imovel, Integer dataFaturamento) throws ErroRepositorioException;

	/**
	 * calcula a quantidade de hidrometro instalados ramal de água
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetro
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2007
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeHidrometroInstalacaoRamalAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * calcula a quantidade de hidrometro instalados atualmente no poço
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetro
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeHidrometroInstalacaoPoco(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Método que retorna uma lista de registros de anormalidades de consulmo filtrados pelo tipo de
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
	 * Método que Verificar Existência de Dados para o Ano/Mês de Referência do Faturamento.
	 * 
	 * @author Josenildo Neves
	 * @date 24/07/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaExistenciaDadosParaAnoMesRefFaturamentoResumoAnormalidadeLeitura(String anoMes)
					throws ErroRepositorioException;

	/**
	 * Método que Verificar Existência de Dados para o Ano/Mês de Referência do Faturamento.
	 * 
	 * @author Josenildo Neves
	 * @date 24/07/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaExistenciaDadosParaAnoMesRefFaturamentoResumoAnormalidadeConsumo(String anoMes) throws ErroRepositorioException;

}