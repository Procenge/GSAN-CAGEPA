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

package gcom.micromedicao;

import gcom.atendimentopublico.ordemservico.bean.LeituraConsumoHelper;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.imovel.bean.ImovelCobrarDoacaoHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.FaturamentoAtividadeCriterio;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.gerencial.cadastro.bean.ResumoLigacaoEconomiaHelper;
import gcom.gui.micromedicao.DadosMovimentacao;
import gcom.micromedicao.bean.LigacaoMedicaoIndividualizadaHelper;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoAnormalidadeFaixa;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.FachadaException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <<Descrição da Interface>>
 * 
 * @author Administrador
 */
public interface IRepositorioMicromedicao {

	/**
	 * @author Saulo Lima
	 * @date 03/03/2009
	 *       Alteração para colocar o Generics no retorno
	 * @param faixaInicial
	 * @param faixaFinal
	 * @return Collection<Hidrometro>
	 * @exception ErroRepositorioException
	 */
	public Collection<Hidrometro> pesquisarNumeroHidrometroFaixa(String faixaInicial, String faixaFinal) throws ErroRepositorioException;

	/**
	 * Pesquisa uma coleção de hidrômetros de acordo com fixo, faixa inicial e
	 * faixa final
	 * 
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarNumeroHidrometroFaixaRelatorio(String faixaInicial, String faixaFinal) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarNumeroHidrometroFaixaPaginacao(String faixaInicial, String faixaFinal, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarNumeroHidrometroFaixaComLimite(String faixaInicial, String faixaFinal) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param periodoInformado
	 *            Descrição do parâmetro
	 * @param ligacaoTipo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarConsumoMedidoHidrometroPeriodoInformadoLigacaoAgua(Imovel imovel, int anoMesReferencia,
					int periodoInformado, LigacaoTipo ligacaoTipo) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param periodoInformado
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarConsumoMedidoHidrometroPeriodoInformadoPoco(Imovel imovel, int anoMesReferencia, int periodoInformado)
					throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarObterConsumoMedioImovel(Imovel imovel, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelFaturamentoGrupoObterIds(FaturamentoGrupo faturamentoGrupo) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelTesteMedicaoConsumoLigacaoAgua(FaturamentoGrupo faturamentoGrupo, Imovel imovel)
					throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelTesteMedicaoConsumoPoco(FaturamentoGrupo faturamentoGrupo, Imovel imovel)
					throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelTesteMedicaoConsumoLigacaoEsgoto(FaturamentoGrupo faturamentoGrupo, Imovel imovel)
					throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelLigacaoSituacao(Imovel imovel) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param objeto
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Object inserirBat(Object objeto) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param rotas
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param ligacaoAguaSituacaoLigado
	 *            Descrição do parâmetro
	 * @param ligacaoAguaSituacaoCortado
	 *            Descrição do parâmetro
	 * @param ligacaoEsgotoLigado
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	// public Collection pesquisarImoveisLigadosCortadosAguaLigadosEsgoto(Rota rota, int
	// anoMesReferencia, Integer ligacaoAguaSituacaoLigado,
	// Integer ligacaoAguaSituacaoCortado, Integer ligacaoEsgotoLigado) throws
	// ErroRepositorioException;
	public Collection pesquisarImoveisLigadosCortadosAguaLigadosEsgoto(Rota rota, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * Método Especifico Para CAERN
	 * 
	 * @author Raphael Rossiter, Flávio Cordeiro
	 * @date 20/03/2007
	 * @param rota
	 * @param anoMesReferencia
	 * @param ligacaoAguaSituacaoLigado
	 * @param ligacaoEsgotoLigado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisLigadosAguaLigadosEsgoto(Rota rota, int anoMesReferencia, Integer ligacaoAguaSituacaoLigado,
					Integer ligacaoEsgotoLigado) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param medicaoTipo
	 *            Descrição do parâmetro
	 * @param sistemaParametro
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarObterDadosHistoricoMedicao(Imovel imovel, MedicaoTipo medicaoTipo, SistemaParametro sistemaParametro)
					throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param ligacaoTipo
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Collection pesquisarConsumoHistoricoConsumoAnormalidade(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param ligacaoTipo
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarConsumoHistorico(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ErroRepositorioException;

	public Collection pesquisarAnormalidadeLeitura(LeituraAnormalidade leituraAnormalidadeFaturamento) throws ErroRepositorioException;

	public Collection pesquisarFaturamentoSituacaoTipo(FaturamentoSituacaoTipo faturamentoSituacaoTipo) throws ErroRepositorioException;

	public Collection pesquisarMaiorDataVigenciaConsumoTarifaImovel(Date dataCorrente, Integer idConsumoTarifa)
					throws ErroRepositorioException;

	public Collection pesquisarDataVigenciaConsumoTarifaPeriodo(Date dataCorrente, Integer idConsumoTarifaConta)
					throws ErroRepositorioException;

	public Object pesquisarConsumoMinimoTarifaCategoriaVigencia(Categoria categoria, ConsumoTarifaVigencia consumoTarifaVigencia)
					throws ErroRepositorioException;

	public void inseriOuAtualizaMedicaoHistorico(Collection colecaoMedicaoHistorico) throws ErroRepositorioException;

	public void inseriOuAtualizaConsumoHistorico(Collection colecaoConsumoHistorico) throws ErroRepositorioException;

	public Collection pesquisarImoveisPorRota(Collection colecaoRota, Integer idLeituraTipo, int inicioPesquisa)
					throws ErroRepositorioException;

	public Collection pesquisarDadosHidrometroTipoLigacaoAgua(Integer idImovel) throws ErroRepositorioException;

	public Collection pesquisarDadosHidrometroTipoPoco(Integer idImovel) throws ErroRepositorioException;

	public Collection pesquisarLeituraAnteriorTipoLigacaoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	public Collection pesquisarLeituraAnteriorTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	public Collection pesquisarIdConsumoTarifaCategoria(Integer idImovel) throws ErroRepositorioException;

	public Integer pesquisarMaiorDataVigencia(Integer idConsumoTarifa) throws ErroRepositorioException;

	public Integer pesquisarConsumoMinimoTarifaCategoria(Integer idConsumoTarifaVigencia, Integer idCategoria)
					throws ErroRepositorioException;

	public Integer verificarExistenciaHidrometroInstalacaoHistoricoTipoAgua(Integer idImovel) throws ErroRepositorioException;

	public Integer verificarExistenciaHidrometroInstalacaoHistoricoTipoPoco(Integer idImovel) throws ErroRepositorioException;

	public Integer verificarExistenciaMedicaoTipo(Integer idMedicaoTipo) throws ErroRepositorioException;

	public Object[] pesquisarLeituraAnormalidade(Integer idLeituraAnormalidade) throws ErroRepositorioException;

	public Object[] pesquisarMedicaoHistoricoAnteriorTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	public MedicaoHistorico pesquisarMedicaoHistoricoTipoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	public MedicaoHistorico pesquisarMedicaoHistoricoTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	public void inserirAtualizarMedicaoHistorico(Collection medicoesHistoricosParaRegistrar) throws ErroRepositorioException;

	public Date pesquisarDataInstalacaoHidrometro(Integer idHidrometroInstalacaoHistorico) throws ErroRepositorioException;

	/**
	 * Consultar Dados do Cliente Imovel Vinculado Auhtor: Rafael Santos Data:
	 * 23/01/2006
	 * 
	 * @param inscricaoImovel
	 *            Inscrição do Imovel
	 * @return Dados do Imovel Vinculado
	 * @throws ControladorException
	 */
	public Object[] consultarDadosClienteImovelUsuario(Imovel imovel) throws ErroRepositorioException;

	/**
	 * Consultar Matriculas dos Imoveis Vinculados do Imovel condominio Auhtor:
	 * Rafael Santos Data: 23/01/2006 [UC0179] Consultar Historico Medição
	 * Indiviualizada
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarConsumoHistoricoImoveisVinculados(ConsumoHistorico consumoHistorico) throws ErroRepositorioException;

	/**
	 * Consultar Consumo Tipo do Consumo Historico Auhtor: Rafael Santos Data:
	 * 23/01/2006
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return Dados do Consumo Tipo
	 * @throws ControladorException
	 */
	public Object[] consultarDadosConsumoTipoConsumoHistorico(ConsumoHistorico consumoHistorico) throws ErroRepositorioException;

	/**
	 * Consultar Consumo Historico da Medicao Individualizada Auhtor: Rafael
	 * Santos Data: 23/01/2006 [UC0179] Consultar Historico Medição
	 * Indiviualizada
	 * 
	 * @param imovel
	 *            Imovel
	 * @param ligcaoTipo
	 *            Tipo de Ligacação
	 * @param anoMesFaturamento
	 *            Ano Mes Faturamento
	 * @return
	 * @throws ControladorException
	 */
	public Object[] obterConsumoHistoricoMedicaoIndividualizada(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Método utilizado para pesquisar dados do maior histórico de medição
	 * existente para um imóvel [UC0101] Consistir Leituras e Calcular Consumos
	 * Autor: Leonardo Vieira Data: 20/02/2006
	 */

	public Object pesquisarObterDadosMaiorHistoricoMedicao(Imovel imovel, MedicaoTipo medicaoTipo, SistemaParametro sistemaParametro)
					throws ErroRepositorioException;

	/**
	 * Consultar Consumo Historico da Medicao Individualizada [UC0113] Faturar
	 * Grupo Faturamento Auhtor: Rafael Santos Data: 20/02/2006
	 * 
	 * @param idImovel
	 * @param idAnormalidade
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterConsumoHistoricoAnormalidade(Integer idImovel, Integer idAnormalidade, int anoMes) throws ErroRepositorioException;

	/**
	 * Consultar Imoveis com Medição Indiviualizada Auhtor: Sávio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medição Indiviualizada
	 * 
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarDescricaoRateioTipoLigacaoAgua(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consultar Imoveis com Medição Indiviualizada Auhtor: Sávio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medição Indiviualizada
	 * 
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarDescricaoRateioTipoLigacaoEsgoto(Integer idImovel) throws ErroRepositorioException;

	public Collection pesquisarImovelExcecoesLeituras(FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql, Integer numeroPagina,
					boolean todosRegistros, Integer anoMesReferencia) throws ErroRepositorioException;

	// esse método realizar a consulta do CASO DE USO - ANALISAR EXECOES DE
	// LEITURAS E CONSUMOS
	public Integer pesquisarImovelExcecoesLeiturasCount(FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Método que retorna os imoveis condominiais e esteja com ligados ou
	 * cortados a aqua e ou ligados com esgoto que possuam hidrometro no poço
	 * das rotas passadas
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Thiago Toscano
	 * @date 07/04/2006
	 * @param rotas
	 * @return Imoveis
	 */
	public Collection getImovelCondominioParaCalculoDoRateioDasRotas(Collection rotas) throws ErroRepositorioException;

	/**
	 * Método que retorna todos os imoveis veinculados a um imovel condominio
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Thiago Toscano
	 * @date 07/04/2006
	 * @param rotas
	 * @return Imoveis
	 */
	public Collection<Imovel> getImovelVinculadosImovelCondominio(Integer id) throws ErroRepositorioException;

	/**
	 * Método que retorna o consumo de ligacao da agua ou esgoto (tipo passado)
	 * de um imovel em um determinado anoMes do faturamento. Método utilizado
	 * pra saber a ligacao de
	 * 2.2.2.2 e 2.2.3.2 do [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author thiago toscano
	 * @date 07/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idLigacaoTipo
	 *            podendo ser agua ou esgoto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object obterConsumoLigacaoAguaOuEsgotoDoImovel(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * Método que retorna um consumoHistorico do imovel com o anoMes passado
	 * 
	 * @author thiago toscano
	 * @date 18/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ConsumoHistorico obterConsumoHistoricoImovel(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * Método que retorna o id da leitura anormalidade do faturamento no caso do
	 * tipo de ligação ser agua
	 * [UC0348] Emitir Contas
	 * [SB0003] Obter Dados do Consumo e Medicao Anterior
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLeituraAnormalidadeTipoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	/**
	 * Método que retorna o id da leitura anormalidade do faturamento no caso do
	 * tipo de ligação ser esgoto
	 * [UC0348] Emitir Contas
	 * [SB0003] Obter Dados do Consumo e Medicao Anterior
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLeituraAnormalidadeTipoEsgoto(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	/**
	 * Método que retorna um arrey de Object com informações do histórico de
	 * medição com tipo de medição agua
	 * [UC0348] Emitir Contas
	 * [SB0004] Obter Dados de medição da conta
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Object[] obterDadosMedicaoContaTipoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	/**
	 * Método que retorna um arrey de Object com informações do histórico de
	 * medição com tipo de medição poco
	 * [UC0348] Emitir Contas
	 * [SB0004] Obter Dados de medição da conta
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosMedicaoContaTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	/**
	 * Método que retorna um arrey de Object com informações do histórico de
	 * consumo com tipo de medição poco
	 * [UC0348] Emitir Contas
	 * [SB0006] Obter Dados de consumo da conta
	 * 
	 * @author Sávio Luiz
	 * @date 19/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idTipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosConsumoConta(Integer idImovel, int anoMesReferencia, Integer idTipoLigacao) throws ErroRepositorioException;

	/**
	 * Método que retorna o consumo de ligacao da agua ou esgoto (tipo passado)
	 * de um imovel em um determinado anoMes do faturamento. Método utilizado
	 * pra saber a ligacao de
	 * 2.2.2.2 e 2.2.3.2 do [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 07/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idLigacaoTipo
	 *            podendo ser agua ou esgoto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterConsumoLigacaoAguaOuEsgotoDoImovelPeloTipoLogacao(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Integer pesquisarNumeroHidrometroFaixaCount(String Fixo, String faixaInicial, String faixaFinal) throws ErroRepositorioException;

	/**
	 * Método que retorna o maior código de Rota de um Setor Comercial
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @param idSetorComercial
	 * @return
	 * @throws ControladorException
	 */

	public Short pesquisarMaximoCodigoRota(Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * método que retorna o maior Código de Rota a partir de um Grupo de Faturamento
	 * 
	 * @author Virgínia Melo
	 * @date 18/02/2009
	 * @param idGrupoFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarMaximoCodigoRotaGrupoFaturamento(Integer idGrupoFaturamento) throws ErroRepositorioException;

	/**
	 * Método que remove RotaAcaoCriterio
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @param id
	 * @param operacaoEfetuada
	 * @param acaoUsuarioHelper
	 * @return
	 * @throws ControladorException
	 */
	public void removerRotaAcaoCriterio(int id, OperacaoEfetuada operacaoEfetuada, Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper)
					throws ErroRepositorioException;

	/**
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarImovelExcecoesApresentaDados(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException;

	/**
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarMedicaoConsumoHistoricoExcecoesApresentaDados(FaturamentoGrupo faturamentoGrupo, Integer idImovel,
					boolean ligacaoAgua) throws ErroRepositorioException;

	/**
	 * Retorna uma coleção com os dados das medicoes para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Collection carregarDadosMedicao(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException;

	/**
	 * Retorna uma coleção com os dados das medicoes para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Collection carregarDadosMedicaoResumo(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException;

	/**
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Collection carregarDadosConsumo(Integer idImovel, int anoMes, boolean ligacaoAgua) throws ErroRepositorioException;

	/**
	 * Método utilizado para pesquisar os consumo historicos a serem
	 * substituidos pelo caso de uso [UC0106] Substituir Consumos Anteriores
	 */
	public Collection pesquisaConsumoHistoricoSubstituirConsumo(Integer idImovel, Integer anoMesInicial, Integer anoMesFinal)
					throws ErroRepositorioException;

	/**
	 * Atualiza o valor de cshi_nnconsumoFaturadomes consumo historico [UC0106] -
	 * Substituir Consumos Anteriores
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarConsumosAnteriores(ConsumoHistorico consumoHistorico) throws ErroRepositorioException;

	/**
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Flávio Cordeiro
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarImovelExcecoesApresentaDadosResumido(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException;

	/**
	 * Retorna um objeto com os dados das medicoes para apresentação
	 * Flávio
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Object[] carregarDadosMedicaoResumido(Integer idImovel, boolean ligacaoAgua, String anoMes) throws ErroRepositorioException;

	/**
	 * Atualizar Analise excecoes consumo resumido
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarLeituraConsumoResumido(MedicaoHistorico medicaoHistorico) throws ErroRepositorioException;

	/**
	 * Registrar leituras e anormalidades Autor:Sávio Luiz
	 * 
	 * @author eduardo henrique
	 * @date 19/01/2009
	 *       Alteraçao na forma de Consulta para montar coleção de retorno, para
	 *       utilizar todos os campos de índice disponível
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMedicaoHistorico(Collection idsImovel, Integer anoMes, Integer idMedicaoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 * 
	 * @author eduardo henrique
	 * @date 19/01/2009
	 *       Alteraçao na forma de Consulta para montar coleção de retorno, para
	 *       utilizar todos os campos de índice disponível
	 */

	public Collection pesquisarMedicaoHistoricoAnterior(Collection colecaoIdsImoveis, Integer anoMes, Integer idMedicaoTipo)
					throws ErroRepositorioException;

	public void inseriMedicaoHistorico(Collection colecaoMedicaoHistorico) throws ErroRepositorioException;

	// ----------Savio
	public void atualizarMedicaoHistorico(Collection medicaoHistoricoAtualizar) throws ErroRepositorioException;

	/**
	 * Registrar leituras e anormalidades Autor:Sávio Luiz
	 * 
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarHidrometroInstalacaoHistorico(Collection idsImovel) throws ErroRepositorioException;

	/**
	 * Método que retorna o número da leitura de retirada do hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarNumeroLeituraRetiradaLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * Método que retorna o número da leitura de retirada do hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarNumeroLeituraRetiradaImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Permite pesquisar imóvel doação baseando-se em rotas [UC0394] Gerar
	 * Débitos a Cobrar de Doações
	 * 
	 * @author César Araújo
	 * @date 05/08/2006
	 * @param Collection
	 *            <Rota> rotas - Coleção de rotas
	 * @return Collection<ImovelCobrarDoacaoHelper> - Coleção de
	 *         ImovelCobrarDoacaoHelper já com as informações necessárias para
	 *         registro da cobrança
	 * @throws ErroRepositorioException
	 */
	public Collection<ImovelCobrarDoacaoHelper> pesquisarImovelDoacaoPorRota(Collection<Rota> rotas) throws ErroRepositorioException;

	public void atualizarFaturamentoAtividadeCronograma(Integer idGrupoFaturemanto, int amReferencia) throws ErroRepositorioException;

	/**
	 * Método que retorna o consumo médio do imóvel
	 * 
	 * @author Ana Maria
	 * @date 17/10/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarConsumoMedioImovel(Integer idImovel) throws ErroRepositorioException;

	public void deletarConsumoHistorico(Integer idRota, int amFaturamento) throws ErroRepositorioException;

	public void deletarConsumoHistoricoCondominio(Integer idRota, int amFaturamento) throws ErroRepositorioException;

	// RETIRAR APOS OS TESTE DE NATAL
	public void deletarConsumoHistoricoRETIRAR(Integer idImovel, int amFaturamento) throws ErroRepositorioException;

	public void deletarConsumoHistoricoCondominioRETIRAR(Integer idImovel, int amFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoMedio(Integer idImovel, Integer tipoMedicao) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Collection pesquisarConsumoFaturadoQuantidadeMeses(Integer idImovel, Integer tipoMedicao, short qtdMeses)
					throws ErroRepositorioException;

	/**
	 * Atualizar Hidrômetro
	 * Pesquisa o imóvel no qual o hidrômetro está instalado
	 * 
	 * @author Rafael Corrêa
	 * @date 23/11/2006
	 * @param idHidrometro
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarImovelHidrometroInstalado(Integer idHidrometro) throws ErroRepositorioException;

	/**
	 * [UC0498] - Efetuar Ligação de Água com Instalaação de Hidrômetro
	 * Pesquisa o id do hidrômetro e a sua situação pelo número
	 * 
	 * @author Rafael Corrêa
	 * @date 29/11/2006
	 * @param numeroHidrometro
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarHidrometroPeloNumero(String numeroHidrometro) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroAgua(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroPoco(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoFaturaMes(int anoMesReferencia, Integer idMedicaoTipo, Integer idImovel) throws ErroRepositorioException;

	/**
	 * Analise Excecoes Leitura e Consumos
	 * 
	 * @author Flávio Leonardo
	 * @date 14/11/2006
	 * @param idImovel
	 *            , SistemaParametros
	 * @return count id
	 * @throws ErroRepositorioException
	 */
	public Collection pesqusiarLigacoesMedicaoIndividualizada(Integer idImovel, String anoMes) throws ErroRepositorioException;

	/**
	 * Obtém os ids de todas as rotas cadastradas
	 * [UC0251] - Gerar Atividade de Ação de Cobrança
	 * [SB0002] - Gerar Atividade de Ação de Cobrança para os Imóveis da Lista de Rotas
	 * 
	 * @author Leonardo Vieira
	 * @date 13/12/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotas() throws ErroRepositorioException;

	/**
	 * [] Ligacoes Medicao Individualizada
	 * 
	 * @author Flávio Cordeiro
	 * @date 17/12/2006
	 * @param colecaoLigacoesMedicao
	 * @throws ControladorException
	 */
	public void atualizarLigacoesMedicaoIndividualizada(LigacaoMedicaoIndividualizadaHelper ligacaoMedicaoIndividualizadaHelper,
					Integer anoMes) throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * Obter empresa da rota.
	 * [SB0006 - ]
	 * 
	 * @author Raphael Rossiter
	 * @date 09/01/2007
	 * @param rota
	 * @throws ErroRepositorioException
	 */
	public Integer obterIdEmpresaPorRota(Rota rota) throws ErroRepositorioException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param periodoInformado
	 *            Descrição do parâmetro
	 * @param ligacaoTipo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Integer pesquisarConsumoFaturadoMesPorConsumoHistorico(Integer idImovel, int anoMesReferencia, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0103] Efetuar Rateio de Consumo
	 * atualiza o consumo de água/esgoto a ser rateado e o consumo de água/esgoto dos
	 * imóveis vínculados do imóvel condomínio.
	 * 
	 * @author Pedro Alexandre
	 * @date 17/01/2007
	 * @param idConsumoHistoricoImovelCondominio
	 * @param consumoRateio
	 * @param consumoImovelVinculadosCondominio
	 * @throws ErroRepositorioException
	 */
	public void atualizarConsumoHistoricoImovelCondominio(Integer idConsumoHistoricoImovelCondominio, Integer consumoRateio,
					Integer consumoImovelVinculadosCondominio) throws ErroRepositorioException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Relação(ROL) em TXT
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 19/01/2007
	 * @param colecaoRota
	 * @param tipoOrdenacao
	 * @author eduardo henrique
	 * @date 06/01/2008 Alteração no método de consulta para não realizar paginação (Hibernate
	 *       utiliza rownum no Oracle como default).
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisParaLeituraPorColecaoRota(Collection colecaoRota, SistemaParametro sistemaParametro)
					throws ErroRepositorioException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Relação(ROL) em TXT - Registro 2 (DOIS)
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 22/01/2007
	 * @param idImovel
	 * @param anoMesReferencia
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarNumeroConsumoMedioImovel(Integer idImovel, int anoMesReferencia, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Raphael Rossiter
	 * @date 19/01/2007
	 * @param Integer
	 *            idImovel, Integer tipoMedicao
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarUltimoAnoMesConsumoFaturado(Integer idImovel, Integer tipoMedicao) throws ErroRepositorioException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Relação(ROL) em TXT - Registro 1 (HUM)
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 23/01/2007
	 * @param idImovel
	 * @param anoMesReferencia
	 * @param idTipoLigacao
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoConsumoTipo(Integer idImovel, Integer idTipoLigacao) throws ErroRepositorioException;

	public Object[] obterConsumoAnteriorAnormalidadeDoImovel(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * Atualiza o valor de cshi_nnconsumomedio, cshi_nnconsumomediohidrometro consumo historico
	 * [UC0106] -
	 * Substituir Consumos Anteriores
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarConsumosMedio(Integer idImovel, Integer anoMesGrupoFaturamento, int consumoMedioImovel,
					int consumoMedioHidrometroAgua, int consumoMedioHidrometroEsgoto) throws ErroRepositorioException;

	/**
	 * Obtém os ids de todas as rotas cadastradas menos as rotas que tiverem o emp_cobranca = 1
	 * [UC0251] - Gerar Atividade de Ação de Cobrança
	 * [SB0002] - Gerar Atividade de Ação de Cobrança para os Imóveis da Lista
	 * de Rotas
	 * 
	 * @author Sávio Luiz
	 * @date 05/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotasEspecificas() throws ErroRepositorioException;

	/**
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquisarMedicaoConsumoHistoricoExcecoesApresentaDadosConsultarImovel(Integer anoMesReferencia, Integer idImovel,
					boolean ligacaoAgua) throws ErroRepositorioException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * 
	 * @author Sávio Luiz
	 * @date 13/03/2007
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisPorRotaOrdenadoPorInscricao(Collection colecaoRota) throws ErroRepositorioException;

	/**
	 * Pesquisa todas as rotas do sistema.
	 * Metódo usado no [UC0302] Gerar Débito a Cobrar de Acréscimos por Impontualidade
	 * 
	 * @author Pedro Alexandre
	 * @date 20/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotasCarregadas() throws ErroRepositorioException;

	/**
	 * [UC0105] Obter Consumo Mínimo da Ligação por Subcategoria
	 * (CSTC_NNCONSUMOMINIMO da tarifa associada ao imóvel na tabela CONSUMO_TARIFA_CATEGORIA com
	 * SCAT_ID=Id
	 * da subcategoria e CSTV_ID = CSTV_ID da tabela CONSUMO_TARIFA_VIGENCIA com CSTF_ID=CSTF_ID da
	 * tabela
	 * IMOVEL e maior CSTV_DTVIGENCIA, que seja menor ou igual a data corrente)
	 * 
	 * @author Raphael Rossiter
	 * @date 11/04/2007
	 * @return subcategoria, consumoTarifaVigencia
	 * @throws ControladorException
	 */
	public Object pesquisarConsumoMinimoTarifaSubcategoriaVigencia(Subcategoria subcategoria, ConsumoTarifaVigencia consumoTarifaVigencia)
					throws ErroRepositorioException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades
	 * Pesquisa as Anormalidades de Leitura e suas quantidades
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnormalidadesRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa,
					Integer anoMes) throws ErroRepositorioException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades
	 * Pesquisa os dados do relatório do comparativo de leituras e anormalidades
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa, Integer anoMes)
					throws ErroRepositorioException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades Registradas
	 * Obter empresa do imóvel.
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @param idImovel
	 * @throws ErroRepositorioException
	 */
	public Integer obterIdEmpresaPorImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 06/06/2007
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosConsumoHistoricoDoImovel(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 07/06/2007
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosMedicaoHistoricoDoImovel(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 08/06/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterMaxAMFaturamentoConsumoHistoricoDoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 08/06/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosHidrometro(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0613] - Registrar Leituras e Anormalidades
	 * Pesquisa os imóveis com faixa falsa
	 * 
	 * @author Rafael Corrêa
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelFaixaFalsa(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0613] - Registrar Leituras e Anormalidades
	 * Retorna a quantidade de imóveis com faixa falsa
	 * 
	 * @author Rafael Corrêa
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarImovelFaixaFalsaCount(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 13/06/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterRotaESequencialRotaDoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0623] Gerar Resumo de Metas CAERN
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarConsumoFaturado(Integer idImovel, Integer tipoLigacao, Integer idConsumoTipoMediaImovel,
					Integer idConsumoTipoMediaHidrometro, Integer amArrecadacao) throws ErroRepositorioException;

	/**
	 * selecionar os movimentos roteiros empresas.
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * 
	 * @author Pedro Alexandre
	 * @date 03/08/2007
	 * @param idRoteiroEmpresa
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarMovimentoRoteiroEmpresa(Integer idRoteiroEmpresa, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * Pesquisa a quantidade de setores comercias por roteiro empresa.
	 * [FS0004] Verificar Quantidade de setores comercias.
	 * 
	 * @author Pedro Alexandre
	 * @date 06/08/2007
	 * @param idRoteiroEmpresa
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeSetorComercialPorRoteiroEmpresa(Integer idRoteiroEmpresa, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Relação(ROL) em TXT - CAER
	 * 
	 * @author Sávio Luiz
	 * @date 13/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisParaLeituraPorColecaoRotaCAER(Collection colecaoRota, Integer idLeituraTipo)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os roteiros empresa de acordo com os parâmetros informado pelo usuário
	 * [UC0370] - Filtrar Roteiro Empresa
	 * 
	 * @author Thiago Tenório
	 * @date 29/08/2007
	 * @param empresa
	 * @param idLocalidade
	 * @param idLeiturista
	 * @param idSetorComercial
	 * @param indicadorUso
	 * @param numeroPagina
	 * @return Collection
	 */
	public Collection pesquisarRoteiroEmpresa(String idEmpresa, String idLocalidade, String codigoSetorComercial, String idLeiturista,
					String indicadorUso, Integer numeroPagina) throws ErroRepositorioException;

	/**
	 * Verifica a quantidade de registros retornados da pesquisa de roteiro empresa
	 * [UC0370] - Filtrar Roteiro Empresa
	 * 
	 * @author Thiago Tenório
	 * @date 01/11/06
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @return int
	 */
	public int pesquisarRoteiroEmpresaCount(String idEmpresa, String idLocalidade, String codigoSetorComercial, String idLeiturista,
					String indicadorUso) throws ErroRepositorioException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Relação(ROL) em TXT - CAER
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalProtecaoTipoLigacaoAgua(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Relação(ROL) em TXT - CAER
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalProtecaoTipoTipoPoco(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC00082] Registrar Leituras e Anormalidades
	 * 
	 * @author Sávio Luiz
	 * @date 29/08/2007
	 * @author eduardo henrique
	 * @date 11/09/2008
	 *       Alteração na forma de Consulta dos Registros de Movimento a serem processados.(v0.05)
	 * @author eduardo henrique
	 * @date 10/12/2008
	 *       Inclusão do ano/mês referência na consulta dos Movimentos que serão processados.
	 * @param idGrupoFaturamento
	 * @param anoMesReferencia
	 * @throws ErroRepositorioException
	 */
	public Collection<MovimentoRoteiroEmpresa> pesquisarColecaoMovimentoRoteiroEmpresaRegistrarLeituraAnormalidade(
					Integer idGrupoFaturamento, Integer anoMesReferencia, Collection<Integer> idRotas) throws ErroRepositorioException;

	/**
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
	 * [SB0001] Baixar Arquivo Texto para o Leiturista.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param imei
	 * @return
	 * @throws ErroRepositorioException
	 */
	public byte[] baixarArquivoTextoParaLeitura(long imei) throws ErroRepositorioException;

	/**
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
	 * Atualizar Situação do Arquivo Texto.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param imei
	 * @param situacaoAnterior
	 * @param situacaoNova
	 * @throws ErroRepositorioException
	 */
	public void atualizarArquivoTextoEnviado(long imei, int situacaoAnterior, int situacaoNova) throws ErroRepositorioException;

	/**
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
	 * [SB0002] Atualizar o movimento roteiro empresa.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param dados
	 * @throws ErroRepositorioException
	 */
	public void atualiarRoteiro(Collection<DadosMovimentacao> dados) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 06/09/2007
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterConsumoMedioEmConsumoHistorico(Integer idImovel, Integer idLigacaoTipo) throws ErroRepositorioException;

	public void removerMovimentoRoteiroEmpresa(Integer anoMesCorrente, Integer idGrupoFaturamentoRota) throws ErroRepositorioException;

	/**
	 * Pesquisa os roteiros empresas pelo grupo de faturamento
	 * [UC0627] Gerar Arquivo Texto para Leitura
	 * 
	 * @author Pedro Alexandre
	 * @date 13/09/2007
	 * @param idGrupoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRoteiroEmpresaPorGrupoFaturamento(Integer idGrupoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos.
	 * Seleciona os imóveis pertencentes a rota, a partir das tabelas IMOVEL e ROTA.
	 * 
	 * @author Raphael Rossiter
	 * @date 17/09/2007
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarImoveisParaFaturamento(Rota rota, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [FS005] - Verificar existência do arquivo texto roteiro empresa.
	 * Caso já exista um arquivo texto para o mês de referência informado,
	 * mesmo roteiro empresa, mesmo grupo de faturamento e sua situação de leitura
	 * transmissão esteja disponível, exclui o arquivo correspondente e retorna pra o caso
	 * se uso que chamou esta funcionalidade.
	 * [UC0627] Gerar Arquivo Texto para Leiturista
	 * 
	 * @author Pedro Alexandre
	 * @date 17/09/2007
	 * @param anoMesReferencia
	 * @param idRoteiroEmpresa
	 * @param idGrupoFaturamento
	 * @throws ErroRepositorioException
	 */
	public void excluirArquivoTextoParaLeiturista(Integer anoMesReferencia, Integer idRoteiroEmpresa, Integer idGrupoFaturamento)
					throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String idLocalidade, String idCobrancaAcao) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao)
					throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao)
					throws ErroRepositorioException;

	/**
	 * Pesquisa as rotas pelo grupo de faturamento
	 * [UC0627] Gerar Arquivo Texto para Leitura
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param idGrupoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRotasPorGrupoFaturamento(Integer idGrupoFaturamento) throws ErroRepositorioException;

	/**
	 * [FS005] - Verificar existência do arquivo texto roteiro empresa por rota.
	 * Caso já exista um arquivo texto para o mês de referência informado,
	 * mesma rota, mesmo grupo de faturamento e sua situação de leitura
	 * transmissão esteja liberado, exclui o arquivo correspondente e retorna pra o caso
	 * se uso que chamou esta funcionalidade.
	 * [UC0627] Gerar Arquivo Texto para Leiturista
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param anoMesReferencia
	 * @param idRota
	 * @param idGrupoFaturamento
	 * @throws ErroRepositorioException
	 */
	public void excluirArquivoTextoParaLeituristaPorRota(Integer anoMesReferencia, Integer idRota, Integer idGrupoFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * Pesquisa a quantidade de setores comercias por roteiro empresa.
	 * [FS0004] Verificar Quantidade de setores comercias.
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param idRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeSetorComercialPorRota(Integer idRota, Integer anoMesFaturamento, Integer idFaturamentoGrupo)
					throws ErroRepositorioException;

	/**
	 * selecionar os movimentos roteiros empresas por rota.
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param idRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarMovimentoRoteiroEmpresaPorRota(Integer idRota, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao, String idCriterioCobranca) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal, String idLocalidade,
					String idCobrancaAcao) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ErroRepositorioException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * pesquisa o consumo historico passando o imovel e o anomes referencia e o
	 * consumo anormalidade correspondente ao faturameto antecipado.
	 * [UC0113] Faturar Grupo de Faturamento
	 * 
	 * @author Sávio Luiz
	 * @date 08/11/2007
	 * @param idRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarConsumoHistoricoAntecipado(Integer idImovel, Integer anoMes) throws ErroRepositorioException;

	/**
	 * Relatório Analise de Consumo
	 * Flávio Leonardo
	 * 26/12/2007
	 * 
	 * @param idImovel
	 * @param anomes
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarLeiturasImovel(String idImovel, String anoMes) throws ErroRepositorioException;

	/**
	 * Relatório Manter Hidrometro
	 * Flávio Leonardo
	 * pesquisa o id do imovel do hidrometro instalado
	 * 
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarImovelPeloHidrometro(Integer hidrometroId) throws ErroRepositorioException;

	/**
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Eduardo Henrique
	 * @date 30/12/2008
	 */
	public Collection<Object[]> carregarDadosConsumo(Integer idImovel, Integer tipoLigacao) throws ErroRepositorioException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * 
	 * @author eduardo henrique
	 * @date 18/01/2009
	 *       Método que retorna a quantidade de Referências de Consumos Históricos válidos para
	 *       Cálculo de Média.
	 */
	public Collection<Integer> obterQuantidadeReferenciasConsumoHistoricoImovelValidosMedia(Imovel imovel) throws ErroRepositorioException;

	/**
	 * [UC0353] Efetuar Ligação de Água.
	 * [SB0001] Gerar Ligação de Água
	 * 
	 * @author eduardo henrique
	 * @date 18/03/2009
	 * @param numeroPontosUtilizacao
	 *            Inteiro que representa os pontos de utilizacao do imovel (normalmente obtido por
	 *            imovel.getNumeroPontosUtilizacao)
	 * @return Integer volume do Consumo Fixo da Faixa baseada nos Pontos de Utilização.
	 * @throws ErroRepositorioException
	 */
	public Integer obterVolumeConsumoFixoFaixaImovel(Integer numeroPontosUtilizacao) throws ErroRepositorioException;

	/**
	 * @param imovel
	 *            [obrigatorio]
	 * @param anoMesReferencia
	 *            [obrigatorio]
	 * @return
	 * @throws ErroRepositorioException
	 *             NullPointer se Imovel ou AnoMesReferencia nulos
	 */
	public MovimentoRoteiroEmpresa obterMovimentoRoteiroPorImovel(Imovel imovel, Integer anoMesReferencia) throws ErroRepositorioException;

	public Collection pesquisarHidrometroProtecaoPorDescricaoAbreviada(String protecao) throws ErroRepositorioException;

	public Integer obterUltimaLeituraDoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Relatório Analise de Consumo
	 * 
	 * @autor Yara Souza
	 *        date 28/04/2010
	 */
	public Collection pesquisarConsumoPorQuantidadeMeses(Integer idImovel, Integer anoMesFaturamentoMinimo, Integer anoMesFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 16/02/2011
	 *       Obter média de consumo do hidrômetro por imóvel
	 */
	public Integer pesquisarMediaConsumoHidrometro(Integer matricula) throws ErroRepositorioException;

	/**
	 * @author isilva
	 * @date 23/05/2011
	 *       Obtem as últimas referências dos Dados de Leitura e Consumo do imóvel
	 * @param idImovel
	 *            Identificador do Imóvel
	 * @param maximoResultado
	 *            Resultado máximo, se informado null ou um número menor ou igual a zero, a consulta
	 *            retornará quantidades de linhas ilimitadas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<LeituraConsumoHelper> obterDadosLeituraEConsumo(Integer idImovel, Integer maximoResultado)
					throws ErroRepositorioException;

	/**
	 * @author Anderson Italo
	 * @date 25/05/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosHidrometroPorImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Relatório Ordem de Serviço de Substituição de Hidrômetro
	 * Obter dados da leitura e consumo do imóvel
	 * 
	 * @author Anderson Italo
	 * @date 26/05/2011
	 */
	public Object[] pesquisarLeituraConsumoImovel(Integer matricula) throws ErroRepositorioException;

	/**
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falcão
	 * @date 09/06/2011
	 */
	public Collection pesquisaMedicaoHistoricoSubstituirLeitura(Integer idImovel, Integer anoMesInicial, Integer anoMesFinal)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os Historicos de Consumo dos Imoveis pelas Referência de Faturamento
	 * 
	 * @author Isaac Silva
	 * @date 13/06/2011
	 * @param idImovel
	 * @param anoMesGrupoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConsumoHistoricoPorReferenciaFaturamentoImovel(Integer idImovel, int anoMesGrupoFaturamento)
					throws ErroRepositorioException;

	/**
	 * @author Isaac Silva
	 * @date 13/06/2011
	 * @param idImovel
	 * @param anoMesGrupoFaturamento
	 * @param consumoMedioHidrometroAgua
	 * @param ligacaoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMedicaoHistoricoAguaOuEsgotoPorImovelEReferencia(Integer idImovel, Integer anoMesGrupoFaturamento,
					int consumoMedioHidrometroAgua, Integer ligacaoTipo) throws ErroRepositorioException;

	/**
	 * Consultar última medição histórico do imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 22/06/2011
	 */
	public MedicaoHistorico consultarUltimaMedicaoHistoricoDoImovel(Integer idImovel, Integer idHidrometro) throws ErroRepositorioException;

	/**
	 * @author Isaac Silva
	 * @date 29/06/2011
	 * @param idFaturamentoAtividade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoAtividadeCriterio> pesquisarFaturamentoAtividadeCriterioPorIdFaturamentoAtividade(
					Integer idFaturamentoAtividade) throws ErroRepositorioException;

	/**
	 * [UC89962] Gerar Dados para leitura (Comprovantes)
	 * 
	 * @author Marcelo Revoredo
	 * @date 07/07/2011
	 * @param Integer
	 *            idImovel
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarUltimoAnoMesConsumoFaturado(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer quantidadeAnormalidadesGrupoFaturamento(Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer quantidadeLeiturasGrupoFaturamento(Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Collection<MovimentoRoteiroEmpresa>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> recuperarAnormalidadesGrupoFaturamento(Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> quantidadeAnormalidadesGrupoFaturamentoPorSetorComercial(Integer idFaturamentoGrupo)
					throws ErroRepositorioException;

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @param idLocalidade
	 * @param codigoSetorComercial
	 * @return Collection<MovimentoRoteiroEmpresa>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> recuperarAnormalidadesGrupoFaturamentoSetorComercial(Integer idFaturamentoGrupo, Integer idLocalidade,
					Integer codigoSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> quantidadeAnormalidadesGrupoFaturamentoPorRota(Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @param idRota
	 * @return Collection<MovimentoRoteiroEmpresa>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> recuperarAnormalidadesGrupoFaturamentoRota(Integer idFaturamentoGrupo, Integer idRota)
					throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Consulta Usada para retornar uma coleção de MovimentoRoteiroEmpresa de acordo com os
	 * parâmetros.
	 * 
	 * @author Ailton Sousa
	 * @date 18/08/2011
	 * @param idsRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMovimentoRoteiroEmpresaPorColecaoRotas(String idsRota, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo, Integer idEmpresa) throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * 
	 * @author Ailton Sousa
	 * @date 18/08/2011
	 * @param idLeituraAnormalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarDescricaoLeituraAnormalidade(Integer idLeituraAnormalidade) throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public List pesquisarTodasAnormalidadesLeitura() throws ErroRepositorioException;

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel id gerencia regional id licalidade id do setor comercial
	 * codigo do setor comercial id rota id guradra numero da quadra id perfil
	 * do imovel id situacao da ligacao de agua id situacao da ligacao de esgoto
	 * principal categoria do imovel esfera de poder do cliente responsavel
	 * indicador de existencia de hidrometro
	 * 
	 * @author Thiago Toscano, Bruno Barros
	 * @date 19/04/2006 17/04/2007
	 * @author Ailton Sousa
	 * @date 10/08/2011 Mudou o parâmetro recebido, antes era o Id do Setor Comercial
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLigacaoEconomias(int idImovel, Integer idRota) throws ErroRepositorioException;

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta se já existe registro correspondente na base.
	 * 
	 * @author Ailton Sousa
	 * @date 11/08/2011
	 */
	public Integer pesquisarExistenciaResumoLigacaoEconomia(ResumoLigacaoEconomiaHelper helper, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de ligações que já existe na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeLigacoesResumoLigacaoEconomia(Integer idResumoLigacaoEconomia) throws ErroRepositorioException;

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de economia que já existe na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeEconomiaResumoLigacaoEconomia(Integer idResumoLigacaoEconomia) throws ErroRepositorioException;

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de ligações novas de água que já existem na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeLigacoesNovasAguaResumoLigacaoEconomia(Integer idResumoLigacaoEconomia)
					throws ErroRepositorioException;

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de ligações novas de esgoto que já existem na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeLigacoesNovasEsgotoResumoLigacaoEconomia(Integer idResumoLigacaoEconomia)
					throws ErroRepositorioException;

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a entidade Resumo de Ligacoes/Economia
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public ResumoLigacoesEconomia pesquisarResumoLigacaoEconomia(Integer idResumoLigacaoEconomia) throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Consulta Usada para retornar o maior consumo grande usuario de Localidade.
	 * 
	 * @author Ailton Sousa
	 * @date 20/08/2011
	 * @param idsRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarMaiorConsumoGrandeUsuario(String idsRota, Integer anoMesFaturamento, Integer idFaturamentoGrupo,
					Integer idEmpresa) throws ErroRepositorioException;

	/**
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param local
	 * @return
	 * @throws ErroRepositorioException
	 */
	public HidrometroLocalInstalacao pesquisarHidrometroLocalInstalacaoPorDescricaoAbreviada(String local) throws ErroRepositorioException;

	/**
	 * Consulta que retorna a quantidade de Movimento Roteiro Empresa por FaturamentoGrupo e
	 * AnoMesReferencia.
	 * 
	 * @author Ailton Sousa
	 * @date 29/08/2011
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterQuantidadeMovimentoRoteiroPorGrupoAnoMes(Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 17/08/2011
	 * @param idFaturamentoAtividade
	 * @param idLeituraTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoAtividadeCriterio> pesquisarFaturamentoAtividadeCriterioPorLeituraTipo(Integer idFaturamentoAtividade,
					Collection collLeituraTipo) throws ErroRepositorioException;

	/**
	 * [UC0712] - Atualizar Leituras e Anormalidades
	 * 
	 * @author Péricles Tavares
	 * @date 25/08/2011
	 */
	public Collection pesquisarMaiorMedicaoHistoricoAnterior(Collection colecaoIdsImoveis, Integer idMedicaoTipo)
					throws ErroRepositorioException;

	/**
	 * - Retorna a quantidade de imóveis com hidrômetro na ligação de água no setor comercial
	 * [UC3033] Verificar Anormalidades de Consumo nos Movimentos do Faturamento
	 * 
	 * @author Hugo Lima
	 * @date 12/01/2012
	 * @param colecaoRotas
	 * @throws ControladorException
	 */
	public Collection<Integer[]> pesquisarImovelLigacaoAguaGrupoFaturamento(Integer idGrupoFaturamento, int controleAnormalidadeConsumo)
					throws ErroRepositorioException;

	/**
	 * - Retorna a quantidade de anormalidades de consumo no grupo de faturamento
	 * [UC3033] Verificar Anormalidades de Consumo nos Movimentos do Faturamento
	 * 
	 * @author Hugo Lima
	 * @date 13/01/2012
	 * @param idGrupoFaturamento
	 * @param idAnormalidade
	 * @param controleAnormalidadeConsumo
	 * @param idLocalidade
	 * @param cdSetorComercial
	 * @param idRota
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarAnormalidadesConsumoGrupoFaturamento(Integer idGrupoFaturamento, Integer idAnormalidade,
					int controleAnormalidadeConsumo, Integer idLocalidade, Integer cdSetorComercial, Integer idRota)
					throws ErroRepositorioException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * 
	 * @author Anderson Italo
	 * @date 20/03/2012
	 */
	public ConsumoAnormalidade pesquisarConsumoAnormalidade(Integer idAnormalidadeConsumo) throws ErroRepositorioException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [SB0014] - Verificar Estouro de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 23/03/2012
	 */
	public List<ConsumoAnormalidadeFaixa> pesquisarConsumoAnormalidadeFaixa() throws ErroRepositorioException;

	/**
	 * @author Hugo Lima
	 * @date 21/03/2012
	 * @param idImovel
	 * @param idTipoMedicao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosHidrometroPorTipoMedicao(Integer idImovel, Integer idTipoMedicao) throws ErroRepositorioException;

	/**
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * com a maior referencia para ano/mes de faturamento
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * 
	 * @param idImovel
	 * @param tipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosConsumoMaiorReferenciaFaturamento(Integer idImovel, Integer idTipoLigacao) throws ErroRepositorioException;

	/**
	 * Retorna uma coleção com os dados da medição para apresentação
	 * com a maior referencia para ano/mes de leitura
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * 
	 * @param idImovel
	 * @param tipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosMedicaoMaiorReferenciaLeitura(Integer idImovel, Integer idTipoLigacao) throws ErroRepositorioException;

	/**
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 22/05/2012
	 * @param idImovel
	 * @param idTipoMedicao
	 * @return RateioTipo
	 * @throws ErroRepositorioException
	 */
	public RateioTipo obterTipoRateioImovelPorTipoMedicao(Integer idImovel, Integer idTipoMedicao) throws ErroRepositorioException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * Método que limpa os dados de medição histórico já consistidos para o imóvel e referência de
	 * faturamento informada.
	 * 
	 * @author Anderson Italo
	 * @date 29/05/2012
	 * @throws ErroRepositorioException
	 */
	public void limparDadosFaturamentoConsitidosMedicaoHistorico(Integer idImovel, Integer anoMesReferenciaFaturamento)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param anoMes
	 * @param idMedicaoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarMedicaoHistorico(Integer idImovel, Integer anoMes, Integer idMedicaoTipo) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 1
	 * 
	 * @author Anderson Italo
	 * @date 05/06/2012
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterTotalValorRubricaRestante(Integer idMovimentoRoteiroEmpresa) throws ErroRepositorioException;

	/**
	 * Relação dos imóveis faturados/pré-faturados no grupo
	 * 
	 * @date 26/08/2012
	 * @author Hebert Falcão
	 */
	public Collection<Integer> pesquisarImoveisFaturadosOuPreFaturadosNoGrupo(Integer idFaturamentoGrupo, Integer anoMesFaturamento)
					throws ErroRepositorioException;

	/**
	 * Método que retorna os imóveis condominiais ligados de água e/ou
	 * ligados de esgoto que possuam hidrômetro no poço das quadras pertencentes às rotas da lista
	 * recebida, a partir das tabelas IMOVEL, QUADRA e ROTA
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 21/05/2012
	 * @param rotas
	 * @return Imoveis
	 */
	public Collection pesquisarImovelCondominioParaCalculoDoRateioDasRotasLigados(Collection colecaoRota) throws ErroRepositorioException;

	/**
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMovimentoRoteiroEmpresa(Integer anoMesFaturamento, Integer idFaturamentoGrupo,
					Collection colecaoDeDebitosCobrados) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisParaFaturamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0275] - Gerar Resumo das Ligaï¿½ï¿½es/Economias
	 * Método que deleta o resumo ligações economia
	 * 
	 * @author Josenildo Neves
	 * @date 16/01/2013
	 * @param idRota
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public void deletarResumoLigacoesEconomia(Integer idRota, int anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * @param referenciaFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<Integer> pesquisarRotasComAlteracaoNasLigacoesEconomiasPorReferencia(Integer referenciaFaturamento)
					throws ErroRepositorioException;

	/**
	 * Método que verifica se a Ligação de esgoto foi feita depois do último dia da referencia
	 * informada por imóvel.
	 * 
	 * @author Josenildo Neves
	 * @date 29/01/2013
	 */
	public boolean verificarDateLigacaoEsgotoPorImovel(Integer idImovel, Date referenciaInicial, Date referenciaFinal)
					throws ErroRepositorioException;

	/**
	 * Método que seleciona a Ligação água pelo imóvel.
	 * 
	 * @author Josenildo Neves
	 * @date 28/01/2013
	 */
	public Collection pesquisarLigacaoAguaPorImovel(Integer idImovel, Date referenciaInicial, Date referenciaFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0100] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 2
	 * 
	 * @author Anderson Italo
	 * @date 21/02/2013
	 */
	public Integer obterCodigoEmpresaConcessionariaLocalidadeVigente(Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 27/03/2013
	 */
	public Object[] pesquisarDadosHidrometroInstalacaoHistoricoPorId(Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Collection<Object[]> pesquisarDadosConsumoHistoricoRetroativos(Integer idImovel, Integer numeroMeses, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Object[] pesquisarDadosUltimaLeituraReal(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0007] - Gerar Movimento Roteiro de Empresa
	 * 
	 * @author Anderson Italo
	 * @date 11/04/2013
	 */
	public Object[] obterMedicaoHistoricoAnterior(Integer idImovel, Integer anoMesFaturamentoAtual, Integer idMedicaoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0007] - Gerar Movimento Roteiro de Empresa
	 * 
	 * @author Anderson Italo
	 * @date 11/04/2013
	 */
	public Object obterUltimoConsumoFaturadoImovel(Integer idImovel, Integer tipoLigacao) throws ErroRepositorioException;

	/**
	 * [FS0003] Obtêm Consumo Histórico Médio a partir da tabela consumo Histórico
	 * 
	 * @author Vicente Zarga
	 * @date 18/04/2013
	 */
	public Integer obterConsumoMedio(Integer idImovel) throws ErroRepositorioException;

	public Object[] obterIDReferenciaConsumoHistorico(String idImovel) throws ErroRepositorioException;

	public void atualizarUltimoConsumoHistorico(Integer idConsumoHistorico, Integer valor) throws ErroRepositorioException;

	/**
	 * [UC0105] - Obter Consumo Mínimo da Ligação
	 * 
	 * @author Anderson Italo
	 * @date 07/05/2013
	 */
	public ConsumoTarifa obterConsumoTarifaTemporariaImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [UC3010] - Verificar Atendimento do Critério de Faturamento pelo Imóvel
	 * 
	 * @author Anderson Italo
	 * @date 27/08/2013
	 */
	public Date obterDataUltimaLeituraReferenciaCortado(Integer idConsumoTipo, Integer idLigacaoTipo, Integer idMedicaoTipo,
					Integer idImovel, Integer consumoMedidoReferenciaCortado) throws ErroRepositorioException;

	public Integer pesquisarLeiturasImovelCount(String idImovel, String anoMes) throws ErroRepositorioException;

	/**
	 * [UC3112][UC3113] Atualizacao Cadastral Coletor de Dados
	 * 
	 * @author Victon Malcolm
	 * @since 08/10/2013
	 */
	public Collection pesquisarAtualizacaoCadastralColetorDados(Integer referenciaInicial,
 Integer referenciaFinal, Integer matricula,
					Integer localidade, Integer setorComercial, Integer rota, Boolean relatorio)
					throws ErroRepositorioException;

	/**
	 * [UC3113] Atualizacao Cadastral Coletor de Dados
	 * 
	 * @author Victon Malcolm
	 * @since 08/10/2013
	 */
	public Collection pesquisarConsultaAtualizacaoCadastralColetorDados(Integer id) throws ErroRepositorioException;

	/**
	 * [UC3117] Gerar Relatório Auditoria leitura
	 * Seleciona os grupos de faturamento que já tiveram processo de retorno do faturamento
	 * imediato iniciado ou concluido na referência atual.
	 * 
	 * @author Felipe Rosacruz
	 * @date 29/09/2013
	 * @return Collection<FaturamentoGrupo>
	 * @throws ErroRepositorioException
	 * @throws FachadaException
	 */
	public Collection<FaturamentoGrupo> pesquisarGrupoFatProcessoRetornoImedInicConc() throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * 
	 * @author Anderson Italo
	 * @date 07/11/2013
	 */
	public MedicaoHistorico consultarMedicaoHistoricoAnterior(Integer idImovel, Integer anoMesFaturamentoAtual, Integer idMedicaoTipo)
					throws ErroRepositorioException;

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometros(Date dataReferencia) throws ErroRepositorioException;

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosCount(Date dataReferencia) throws ErroRepositorioException;

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros por Ano Instalação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosAnoInstalacao() throws ErroRepositorioException;

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros Ano Instalação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosAnoInstalacaoCount() throws ErroRepositorioException;

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros Situação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosSituacaoCount(Date dataInicial, Date dataFinal) throws ErroRepositorioException;

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros Situação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosSituacao(Date dataInicial, Date dataFinal) throws ErroRepositorioException;

	/**
	 * [UC0113] Faturar Grupo de Faturamento
	 * [SB0002] - Determinar Valores para Faturamento de Água e/ou Esgoto
	 * 
	 * @author Anderson Italo
	 * @date 05/01/2014
	 */
	public Object[] obterConsumoHistoricoAnterior(Integer idImovel, Integer anoMesFaturamentoAtual, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * Pesquisar consumo e leitura anormalidade por consumo histórico
	 * 
	 * @author Hebert Falcão
	 * @date 26/01/2014
	 */
	public Object[] pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(Integer idImovel, int anoMesReferencia, Integer idLigacaoTipo)
					throws ErroRepositorioException;

	/**
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterImovelParaAjusteContaComValorAMenor(Integer referencia, String idsGrupos)
					throws ErroRepositorioException;

}