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

package gcom.micromedicao;

import gcom.atendimentopublico.ordemservico.bean.LeituraConsumoHelper;
import gcom.batch.FuncionalidadeIniciada;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.FaturamentoAtividadeCriterio;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.micromedicao.DadosMovimentacao;
import gcom.micromedicao.bean.AnaliseConsumoRelatorioOSHelper;
import gcom.micromedicao.bean.GerarDadosParaLeituraHelper;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraFiscalizacao;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.FachadaException;
import gcom.util.filtro.Filtro;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.comum.exception.NegocioException;

/**
 * Interface Controlador Micromedi��o PADR�O
 * 
 * @author Raphael Rossiter
 * @date 20/12/2006
 */
public interface IControladorMicromedicao {

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos.
	 * Permite consistir um conjunto de leituras e anormalidades e calcular os consumos para
	 * faturamento.
	 * 
	 * @author eduardo henrique
	 * @date 03/10/2008
	 */
	public void consistirLeiturasCalcularConsumosBatch(FaturamentoGrupo faturamentoGrupo, SistemaParametro sistemaParametro,
					Collection<Rota> colecaoRotas, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param faturamentoGrupo
	 *            Descri��o do par�metro
	 * @param sistemaParametro
	 *            Descri��o do par�metro
	 * @param esferaPoder
	 *            TODO
	 */
	public void consistirLeiturasCalcularConsumos(Imovel imovelRota, FaturamentoGrupo faturamentoGrupo, SistemaParametro sistemaParametro,
					EsferaPoder esferaPoder) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param sistemaParametro
	 *            Descri��o do par�metro
	 * @param medicaoTipo
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public int[] obterConsumoMedioHidrometro(Imovel imovel, SistemaParametro sistemaParametro, MedicaoTipo medicaoTipo)
					throws ControladorException;

	public int[] obterConsumoMedioImovel(Imovel imovel, SistemaParametro sistemaParametro) throws ControladorException;

	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @author Saulo Lima
	 * @date 03/03/2009
	 *       Altera��o para inserir os hidr�metros e atualizar os que j� existem na base;
	 *       Retornar a quantidade de hidr�metros inseridos.
	 * @param hidrometro
	 * @param fixo
	 * @param faixaInicial
	 * @param faixaFinal
	 * @return int
	 *         Quantidade de Hidr�metros inseridos
	 * @throws ControladorException
	 */
	public int inserirHidrometro(Hidrometro hidrometro, String fixo, Integer faixaInicial, Integer faixaFinal, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Remover Hidrometro
	 * 
	 * @author Hebert Falc�o
	 * @date 18/02/2011
	 * @throws ControladorException
	 */
	public void removerHidrometro(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 03/03/2009
	 *       Altera��o para colocar o Generics no retorno
	 * @param fixo
	 * @param faixaInicial
	 * @param faixaFinal
	 * @return Collection<Hidrometro>
	 * @throws ControladorException
	 */
	public Collection<Hidrometro> pesquisarNumeroHidrometroFaixa(String fixo, String faixaInicial, String faixaFinal)
					throws ControladorException;

	/**
	 * Pesquisa uma cole��o de hidr�metros de acordo com fixo, faixa inicial e
	 * faixa final
	 * 
	 * @param fixo
	 *            Description of the Parameter
	 * @param faixaInicial
	 *            Descri��o do par�metro
	 * @param faixaFinal
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public Collection pesquisarNumeroHidrometroFaixaRelatorio(String fixo, String faixaInicial, String faixaFinal)
					throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param fixo
	 *            Description of the Parameter
	 * @param faixaInicial
	 *            Descri��o do par�metro
	 * @param faixaFinal
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public Collection pesquisarNumeroHidrometroFaixaPaginacao(String fixo, String faixaInicial, String faixaFinal, Integer numeroPagina)
					throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param fixo
	 *            Description of the Parameter
	 * @param faixaInicial
	 *            Descri��o do par�metro
	 * @param faixaFinal
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public Collection pesquisarNumeroHidrometroFaixaComLimite(String fixo, String faixaInicial, String faixaFinal)
					throws ControladorException;

	/**
	 * Description of the Method
	 * 
	 * @param hidrometro
	 *            Description of the Parameter
	 */
	public void atualizarHidrometro(Hidrometro hidrometro, Usuario usuarioLogado) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param colecaoHidrometro
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public String verificarLocalArmazenagemSituacao(Collection colecaoHidrometro) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param colecaoHidrometro
	 *            Descri��o do par�metro
	 * @param data
	 *            Descri��o do par�metro
	 * @param hora
	 *            Descri��o do par�metro
	 * @param idLocalArmazenagemDestino
	 *            Descri��o do par�metro
	 * @param idMotivoMovimentacao
	 *            Descri��o do par�metro
	 * @param parecer
	 *            Descri��o do par�metro
	 */
	public void inserirAtualizarMovimentacaoHidrometroIds(Collection colecaoHidrometro, String data, String hora,
					String idLocalArmazenagemDestino, String idMotivoMovimentacao, String parecer, Usuario usuario)
					throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param colecaoHidrometro
	 *            Descri��o do par�metro
	 * @param ids
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public Collection obterColecaoObjetosSelecionados(Collection colecaoHidrometro, String[] ids) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param faturamentoGrupo
	 *            Descri��o do par�metro
	 * @param imovel
	 *            Descri��o do par�metro
	 */
	public void executarImovelTestesMedicaoConsumo(FaturamentoGrupo faturamentoGrupo, Imovel imovel) throws ControladorException;

	public int obterConsumoMinimoLigacao(Imovel imovel, Collection colecaoCategorias) throws ControladorException;

	public int obterConsumoMinimoLigacaoPeriodo(Imovel imovel, Collection colecaoCategorias, String mesAnoConta,
					Integer idConsumoTarifaConta) throws ControladorException;

	/**
	 * [UC0083] - Gerar Dados para Leitura
	 * [SF0001] - Gerar Arquivo Convencional
	 * ou Rela��o Autor: S�vio Luiz, Pedro Alexandre Data: 21/12/2005,
	 * 15/10/2007
	 * 
	 * @author eduardo henrique
	 * @date 05/09/2008 Alterado funcionamento para envio de e-mail ap�s gera��o
	 *       do Arquivo Texto.
	 * @author eduardo henrique
	 * @date 07/01/2009 Pagina��o do hibernate desativada, por forma inadequada
	 *       de execu��o para o cache do Oracle.
	 * @param colecaoRota
	 * @param anoMesCorrente
	 * @param idGrupoFaturamentoRota
	 * @param idFuncionalidadeIniciada
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarDadosPorLeituraMicroColetor(Collection colecaoRota, Integer anoMesCorrente, Integer idGrupoFaturamentoRota,
					FuncionalidadeIniciada funcionalidade, Date dataPrevistaAtividadeLeitura, Collection collLeituraTipo)
					throws ControladorException;

	/*
	 * [UC0121] - Filtrar Exce��es de Leituras e Consumos Fl�vio Leonardo
	 * Cavalcanti Cordeiro
	 */
	public Collection filtrarExcecoesLeiturasConsumos(FaturamentoGrupo faturamentoGrupo,
					FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql, Integer numeroPagina, boolean todosRegistros)
					throws ControladorException;

	/**
	 * [UC0121] - Filtrar Exce��es de Leituras e Consumos Fl�vio Leonardo
	 * Cavalcanti Cordeiro
	 */
	public Collection pesquisarLigacoesMedicaoIndividualizada(Integer idImovel, String anoMes) throws ControladorException;

	/*
	 * [UC0121] - Filtrar Exce��es de Leituras e Consumos Fl�vio Leonardo
	 * Cavalcanti Cordeiro
	 */
	public Integer filtrarExcecoesLeiturasConsumosCount(FaturamentoGrupo faturamentoGrupo,
					FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql) throws ControladorException;

	/**
	 * [UC0087] - Calcular Faixa de Leitura Falsa Autor: S�vio Luiz Data:
	 * 29/12/2005
	 */
	public Object[] calcularFaixaLeituraFalsa(Imovel imovel, int media, Integer leituraAnterior, MedicaoHistorico medicaoHistorico,
					boolean hidrometroSelecionado, Hidrometro hidrometro) throws ControladorException;

	public Collection pesquisarLeituraAnteriorTipoLigacaoAgua(Integer idImovel, Integer anoMesAnterior) throws ControladorException;

	/**
	 * [UC0098] Manter V�nculos de Im�veis para Rateio de Consumo Atualizar Tipo
	 * Rateio com Vinculo com o Imovel Author: Rafael Santos Data: 12/01/2006
	 * 
	 * @param imovel
	 * @param hidrometroInstalacaoHistoricoAgua
	 * @param hidrometroInstalacaoHistoricoPoco
	 * @throws ControladorException
	 */
	public void atualizarTipoRateio(Imovel imovel, HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoAgua,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoPoco, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0098] Manter V�nculos de Im�veis para Rateio de Consumo Atualizar Tipo
	 * Rateio com Vinculo com o Imovel Author: Rafael Santos Data: 16/01/2006
	 * Estabelecer Vinculo ao Imovel
	 * 
	 * @param Imovel
	 * @param imoveisASerVinculados
	 * @param imoveisASerDesvinculados
	 * @param hidrometroInstalacaoHistoricoAgua
	 * @param hidrometroInstalacaoHistoricoPoco
	 * @throws ControladorException
	 */
	public void estabelecerVinculo(Imovel imovel, Collection imoveisASerVinculados, Collection imoveisASerDesvinculados,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoAgua,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoPoco, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0098] Manter V�nculos de Im�veis para Rateio de Consumo Atualizar Tipo
	 * Rateio com Vinculo com o Imovel Author: Rafael Santos Data: 16/01/2006
	 * Desfazer Vinculo ao Imovel
	 * 
	 * @param Imovel
	 * @param ids
	 * @throws ControladorException
	 */
	public void desfazerVinculo(Imovel imovel, String[] ids, boolean desvincular, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: S�vio Luiz Data:
	 * 04/01/2006
	 */
	public void registrarLeiturasAnormalidades(Collection<MedicaoHistorico> colecaoMedicaoHistorico, Integer idFaturamentoGrupo,
					Integer anoMesReferencia, Usuario usuario) throws ControladorException;

	public Collection pesquisarHidrometroPorHidrometroMovimentacao(Filtro filtro) throws ControladorException;

	/**
	 * Consultar Dados do Cliente Imovel Vinculado Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medi��o Indiviualizada
	 * 
	 * @param inscricaoImovel
	 *            Inscri��o do Imovel
	 * @return Dados do Imovel Vinculado
	 * @throws ControladorException
	 */
	public Cliente consultarDadosClienteImovelUsuario(Imovel imovel) throws ControladorException;

	/**
	 * Consultar Historico Medi��o Individualizada Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medi��o Indiviualizada
	 * 
	 * @param imovelCondominio
	 *            Imovel Condominio
	 * @param anoMesFaturamento
	 *            Ano M�s Fauramento
	 * @return Cole��o deDados do Historico Medi��o Individualizada
	 * @throws ControladorException
	 */
	public Collection consultarHistoricoMedicaoIndividualizada(Imovel imovelCondominio, String anoMesFaturamento)
					throws ControladorException;

	/**
	 * Consultar Matriculas dos Imoveis Vinculados do Imovel condominio Auhtor:
	 * Rafael Santos Data: 23/01/2006 [UC0179] Consultar Historico Medi��o
	 * Indiviualizada
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarConsumoHistoricoImoveisVinculados(ConsumoHistorico consumoHistorico) throws ControladorException;

	/**
	 * Consultar Dados Consumo Tipo Imovel Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medi��o Indiviualizada
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return
	 * @throws ControladorException
	 */
	public ConsumoTipo consultarDadosConsumoTipoConsumoHistorico(ConsumoHistorico consumoHistorico) throws ControladorException;

	/**
	 * Consultar Consumo Historico da Medicao Individualizada Auhtor: Rafael
	 * Santos Data: 23/01/2006 [UC0179] Consultar Historico Medi��o
	 * Indiviualizada
	 * 
	 * @param imovel
	 *            Imovel
	 * @param ligcaoTipo
	 *            Tipo de Ligaca��o
	 * @param anoMesFaturamento
	 *            Ano Mes Faturamento
	 * @return
	 * @throws ControladorException
	 */
	public ConsumoHistorico obterConsumoHistoricoMedicaoIndividualizada(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ControladorException;

	/**
	 * [UC0113] Faturar Grupo de Faturamento Author: Rafael Santos
	 * Data:03/02/2006 Consultar Medicao Historico do Tipo Agua
	 * 
	 * @param imovel
	 *            Id do Imovel Imovel
	 * @param anoMes
	 *            Ano Mes
	 * @return Medicao Historico
	 * @exception ControladorException
	 */
	public MedicaoHistorico pesquisarMedicaoHistoricoTipoAgua(Integer imovel, Integer anoMes) throws ControladorException;

	/**
	 * [UC0113] Faturar Grupo de Faturamento Author: Rafael Santos
	 * Data:03/02/2006 Consultar Medicao Historico do Tipo Po�o
	 * 
	 * @param imovel
	 *            Id do Imovel Imovel
	 * @param anoMes
	 *            Ano Mes
	 * @return Medicao Historico
	 * @exception ControladorException
	 */
	public MedicaoHistorico pesquisarMedicaoHistoricoTipoPoco(Integer imovel, Integer anoMes) throws ControladorException;

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
	public ConsumoHistorico obterConsumoHistoricoAnormalidade(Integer idImovel, Integer idAnormalidade, int anoMes)
					throws ControladorException;

	/**
	 * Atualizar Medica��o Historico [UC0000] Alterar Dados Para Faturamento
	 * Auhtor: Rafael Corr�a Data: 07/03/2006
	 * 
	 * @param medicaoHistorico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarMedicaoHistorico(MedicaoHistorico medicaoHistorico) throws ControladorException;

	/**
	 * Consultar Imoveis com Medi��o Indiviualizada Auhtor: S�vio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medi��o Indiviualizada
	 * 
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public String pesquisarDescricaoRateioTipoLigacaoAgua(Integer idImovel) throws ControladorException;

	/**
	 * Consultar Imoveis com Medi��o Indiviualizada Auhtor: S�vio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medi��o Indiviualizada
	 * 
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public String pesquisarDescricaoRateioTipoLigacaoEsgoto(Integer idImovel) throws ControladorException;

	/**
	 * [UC0038] Inserir Rota e [UC0039]Manter Rota
	 * Validar Inserir Rota e Alterar Rota
	 * 
	 * @author Vivianne Sousa
	 * @date 24/03/2006
	 * @param idLocalidade
	 *            ,
	 * @param idSetorComercial
	 *            ,
	 * @param codigoRota
	 *            ,
	 * @param idCobrancaGrupo
	 *            ,
	 * @param idFaturamentoGrupo
	 *            ,
	 * @param idLeituraTipo
	 *            ,
	 * @param idEmpresaLeituristica
	 *            ,
	 * @param dataAjusteLeitura
	 *            ,
	 * @param indicadorAjusteConsumo
	 *            ,
	 * @param indicadorFiscalizarCortado
	 *            ,
	 * @param indicadorFiscalizarSuprimido
	 *            ,
	 * @param indicadorGerarFalsaFaixa
	 *            ,
	 * @param percentualGeracaoFaixaFalsa
	 *            ,
	 * @param indicadorGerarFiscalizacao
	 *            ,
	 * @param percentualGeracaoFiscalizacao
	 *            ,
	 * @param indicadorUso
	 *            ,
	 * @param acao
	 *            ,
	 * @param collectionRotaAcaoCriterio
	 *            return void
	 * @throws ControladorException
	 */
	public void validacaoFinalRota(String idLocalidade, String idSetorComercial, String codigoRota, String idCobrancaGrupo,
					String idFaturamentoGrupo, String idLeituraTipo, String idEmpresaLeituristica, String indicadorFiscalizarCortado,
					String indicadorFiscalizarSuprimido, String indicadorGerarFalsaFaixa, String percentualGeracaoFaixaFalsa,
					String indicadorGerarFiscalizacao, String percentualGeracaoFiscalizacao, String indicadorUso, String idLeiturista,
					String acao, Collection collectionRotaAcaoCriterio, boolean criticar) throws ControladorException;

	/**
	 * [UC0039] Manter Rota
	 * Altera um objeto do tipo rota no BD
	 * 
	 * @author Vivianne Sousa
	 * @date 06/04/2006
	 * @param rota
	 * @param collectionRotaAcaoCriterio
	 * @return void
	 * @throws ControladorException
	 */
	public void atualizarRota(Rota rota, String idLocalidade, Collection collectionRotaAcaoCriterio, Usuario usuarioLogado, boolean criticar)
					throws ControladorException;

	/**
	 * [UC0038] Inserir Rota
	 * Insere um objeto do tipo rota no BD
	 * 
	 * @author Vivianne Sousa
	 * @date 17/04/2006
	 * @param rota
	 * @param idLocalidade
	 * @param collectionRotaAcaoCriterio
	 * @return idRota
	 * @throws ControladorException
	 */
	public Integer inserirRota(Rota rota, String idLocalidade, Collection collectionRotaAcaoCriterio, Usuario usuarioLogado,
					boolean criticar) throws ControladorException;

	/**
	 * [UC0039] Manter Rota
	 * Remove um objeto do tipo rota no BD
	 * 
	 * @author Vivianne Sousa
	 * @date 05/05/2006
	 * @param ids
	 * @return void
	 * @throws ControladorException
	 */
	public void removerRota(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * M�todo que efetua o Rateio do consumo para todos os im�veis de uma rota
	 * que sejam im�veil condominio
	 * [UC0103] - Efetuar Rateio de Consumo
	 * 
	 * @author Thiago Toscano
	 * @date 07/04/2006
	 * @param colecaoRotas
	 * @param anoMesFaturamento
	 */
	public void efetuarRateioDeConsumo(Collection rotas, Integer anoMesFaturamento, int idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * Filtrar Hidrometro
	 * Faz a pesquisa da quantidade que o filtro vai retornar
	 * 
	 * @author Fernanda Paiva
	 * @date
	 * @throws ControladorException
	 */
	public Integer pesquisarNumeroHidrometroFaixaCount(String fixo, String faixaInicial, String faixaFinal) throws ControladorException;

	/**
	 * M�todo que retorna o maior c�digo de Rota de um Setor Comercial
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @param idSetorComercial
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarMaximoCodigoRota(Integer idSetorComercial) throws ControladorException;

	/**
	 * m�todo que retorna o maior C�digo de Rota a partir de um Grupo de Faturamento
	 * 
	 * @author Virg�nia Melo
	 * @date 18/02/2009
	 * @param idGrupoFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarMaximoCodigoRotaGrupoFaturamento(Integer idGrupoFaturamento) throws ControladorException;

	/**
	 * M�todo que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 * 
	 * @author S�vio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarImovelExcecoesApresentaDados(Integer idImovel, boolean ligacaoAgua) throws ControladorException;

	/**
	 * M�todo que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 * 
	 * @author S�vio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarMedicaoConsumoHistoricoExcecoesApresentaDados(FaturamentoGrupo faturamentoGrupo, Integer idImovel,
					boolean ligacaoAgua) throws ControladorException;

	/**
	 * Retorna uma cole��o com os dados das medicoes para apresenta��o
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 */
	public Collection carregarDadosMedicao(Integer idImovel, boolean ligacaoAgua) throws ControladorException;

	/**
	 * Retorna uma cole��o com os dados das medicoes para apresenta��o
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 */
	public Collection carregarDadosMedicaoResumo(Integer idImovel, boolean ligacaoAgua) throws ControladorException;

	/**
	 * Retorna uma cole��o com os dados das medicoes para apresenta��o
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 */
	public Collection carregarDadosConsumo(Integer idImovel, int anoMes, boolean ligacaoAgua) throws ControladorException;

	/**
	 * M�todo utilizado para pesquisar os consumo historicos a serem
	 * substituidos pelo caso de uso [UC0106] Substituir Consumos Anteriores
	 */
	public Collection<ImovelMicromedicao> pesquisaConsumoHistoricoSubstituirConsumo(Integer idImovel, Integer anoMesInicial,
					Integer anoMesFinal) throws ControladorException;

	/**
	 * Atualiza o valor de cshi_nnconsumoFaturadomes consumo historico [UC0106] -
	 * Substituir Consumos Anteriores
	 * 
	 * @param consumoHistorico
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void atualizarConsumosAnteriores(ConsumoHistorico consumoHistorico, Usuario usuarioLogado) throws ControladorException;

	/**
	 * M�todo que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public ImovelMicromedicao pesquiarImovelExcecoesApresentaDadosResumido(Integer idImovel, boolean ligacaoAgua)
					throws ControladorException;

	/**
	 * Retorna um objeto com os dados das medicoes para apresenta��o
	 * Fl�vio
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 */
	public ImovelMicromedicao carregarDadosMedicaoResumido(Integer idImovel, boolean ligacaoAgua, String anoMes)
					throws ControladorException;

	/**
	 * Atualizar Analise excecoes consumo resumido
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarLeituraConsumoResumido(Integer idImovel, String mesAno, String dataLeituraAnteriorFaturamento,
					String leituraAnteriorFaturamento, String dataLeituraAtualInformada, String leituraAtualInformada, String consumo,
					boolean ligacaoAgua, Integer idAnormalidade, Integer idConfirmacaoLeitura, Usuario usuario) throws ControladorException;

	/**
	 * M�todo que retorna um arrey de Object com informa��es do hist�rico de
	 * consumo com tipo de medi��o poco
	 * [UC0348] Emitir Contas
	 * [SB0006] Obter Dados de consumo da conta
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idTipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosConsumoConta(Integer idImovel, int anoMesReferencia, Integer idTipoLigacao) throws ControladorException;

	/**
	 * M�todo que retorna o n�mero da leitura de retirada do hidr�metro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarNumeroLeituraRetiradaLigacaoAgua(Integer idLigacaoAgua) throws ControladorException;

	/**
	 * M�todo que retorna o n�mero da leitura de retirada do hidr�metro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarNumeroLeituraRetiradaImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0000] - Efetuar Retirada de Hidr�metro
	 * Pesquisa todos os campos do Hidrometro e seus relacionamentos
	 * obrigat�rios.
	 * 
	 * @author Thiago Ten�rio
	 * @date 28/09/2006
	 * @param idHidrometro
	 * @throws ControladorException
	 */
	public Hidrometro pesquisarHidrometroPeloId(Integer idHidrometro) throws ControladorException;

	/**
	 * Permite pesquisar im�vel doa��o baseando-se em rotas [UC0394] Gerar
	 * D�bitos a Cobrar de Doa��es
	 * 
	 * @author C�sar Ara�jo
	 * @date 05/08/2006
	 * @param Collection
	 *            <Rota> rotas - Cole��o de rotas
	 * @return Collection<ImovelCobrarDoacaoHelper> - Cole��o de
	 *         ImovelCobrarDoacaoHelper j� com as informa��es necess�rias para
	 *         registro da cobran�a
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	public Collection<ImovelCobrarDoacaoHelper> pesquisarImovelDoacaoPorRota(Collection<Rota> rotas) throws ControladorException;

	/**
	 * M�todo que retorna o consumo m�dio do im�vel
	 * 
	 * @author Ana Maria
	 * @date 17/10/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarConsumoMedioImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarConsumoMedio(Integer idImovel, Integer tipoMedicao) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarMaiorConsumoFaturadoQuantidadeMeses(Integer idImovel, Integer tipoMedicao, short qtdMeses)
					throws ControladorException;

	/**
	 * Atualizar Hidr�metro
	 * Pesquisa o im�vel no qual o hidr�metro est� instalado
	 * 
	 * @author Rafael Corr�a
	 * @date 23/11/2006
	 * @param idHidrometro
	 * @return String
	 * @throws ControladorException
	 */
	public String pesquisarImovelHidrometroInstalado(Integer idHidrometro) throws ControladorException;

	/**
	 * [UC0498] - Efetuar Liga��o de �gua com Instalaa��o de Hidr�metro
	 * Pesquisa o id do hidr�metro e a sua situa��o pelo n�mero
	 * 
	 * @author Rafael Corr�a
	 * @date 29/11/2006
	 * @param numeroHidrometro
	 * @return Hidrometro
	 * @throws ControladorException
	 */
	public Hidrometro pesquisarHidrometroPeloNumero(String numeroHidrometro) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroAgua(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroPoco(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoFaturaMes(Integer amReferencia, Integer idLigacao, Integer idImovel) throws ControladorException;

	/**
	 * Obt�m os ids de todas as rotas cadastradas
	 * [UC0251] - Gerar Atividade de A��o de Cobran�a
	 * [SB0002] - Gerar Atividade de A��o de Cobran�a para os Im�veis da Lista
	 * de Rotas
	 * 
	 * @author Leonardo Vieira
	 * @date 13/12/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotas() throws ControladorException;

	/**
	 * [] Ligacoes Medicao Individualizada
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 17/12/2006
	 * @param colecaoLigacoesMedicao
	 * @throws ControladorException
	 */
	public void atualizarLigacoesMedicaoIndividualizada(Collection colecaoLigacoesMedicao, Usuario usuarioLogado, Integer anoMes)
					throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author Raphael Rossiter
	 * @date 19/01/2007
	 * @param Integer
	 *            idImovel, Integer tipoMedicao
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarUltimoAnoMesConsumoFaturado(Integer idImovel, Integer tipoMedicao) throws ControladorException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Rela��o (TXT)
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 19/01/2007
	 * @param Rota
	 * @param ano
	 *            e mes corrente
	 * @param id
	 *            grupo faturamento
	 * @param id
	 *            funcionalidade iniciada
	 * @throws ControladorException
	 */
	public void gerarDadosPorLeituraConvencional(Collection<Rota> colecaoRota, Integer anoMesCorrente, Integer idGrupoFaturamentoRota,
					FuncionalidadeIniciada funcionalidade, Date dataPrevistaAtividadeLeitura, Collection collLeituraTipo)
					throws ControladorException;

	/**
	 * [UC00083] Gerar Dados para Leitura Convencional(Rela��o de Leituras e Comprovantes em PDF)
	 * [SB0002] - Gerar Rela��o
	 * [SB0004] � Gera dados para leitura convencional DESO
	 * [SB0007] � Gera Rela��o Lista de Consumidores
	 * [SB0008] � Gera Comprovante de Leitura
	 * 
	 * @author Hebert Falc�o
	 * @date 15/02/2011
	 * @param colecaoRota
	 * @param anoMesCorrente
	 * @param idGrupoFaturamentoRota
	 * @param idFuncionalidadeIniciada
	 * @param dataPrevistaAtividadeLeitura
	 * @throws ControladorException
	 */
	public void gerarDadosPorLeituraConvencionalPdf(Collection<Rota> colecaoRota, Integer anoMesCorrente, Integer idGrupoFaturamentoRota,
					FuncionalidadeIniciada funcionalidade, Date dataPrevistaAtividadeLeitura, Collection collLeituraTipo)
					throws ControladorException;

	/**
	 * M�todo que retorna o consumo de ligacao da agua ou esgoto (tipo passado)
	 * de um imovel em um determinado anoMes do faturamento. M�todo utilizado
	 * pra saber a ligacao de
	 * 2.2.2.2 e 2.2.3.2 do [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author S�vio Luiz
	 * @date 07/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idLigacaoTipo
	 *            podendo ser agua ou esgoto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterConsumoAnteriorAnormalidadeDoImovel(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ControladorException;

	/**
	 * Atualiza o valor de cshi_nnconsumomedio, cshi_nnconsumomediohidrometro
	 * consumo historico [UC0106] - Substituir Consumos Anteriores
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarConsumosMedio(Integer idImovel, Integer anoMesGrupoFaturamento, int consumoMedioImovel,
					int consumoMedioHidrometroAgua, int consumoMedioHidrometroEsgoto, Usuario usuario);

	/**
	 * Obt�m os ids de todas as rotas cadastradas menos as rotas que tiverem o
	 * emp_cobranca = 1
	 * [UC0251] - Gerar Atividade de A��o de Cobran�a
	 * [SB0002] - Gerar Atividade de A��o de Cobran�a para os Im�veis da Lista
	 * de Rotas
	 * 
	 * @author S�vio Luiz
	 * @date 05/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotasEspecificas() throws ControladorException;

	/**
	 * M�todo que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medi��o e Consumo
	 * 
	 * @author S�vio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquisarMedicaoConsumoHistoricoExcecoesApresentaDadosConsultarImovel(Integer anoMesReferencia, Integer idImovel,
					boolean ligacaoAgua) throws ControladorException;

	/**
	 * Pesquisa todas as rotas do sistema. Met�do usado no [UC0302] Gerar D�bito
	 * a Cobrar de Acr�scimos por Impontualidade
	 * 
	 * @author Pedro Alexandre
	 * @date 20/03/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarListaRotasCarregadas() throws ControladorException;

	/**
	 * Description of the Method
	 * 
	 * @param hidrometros
	 *            Description of the Parameter
	 * @param hidrometroAtualizado
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void atualizarConjuntoHidrometro(Collection hidrometros, Hidrometro hidrometroAtualizado, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0105] Obter Consumo M�nimo da Liga��o por Subcategoria
	 * 
	 * @author Raphael Rossiter
	 * @date 11/04/2007
	 * @return imovel, colecaoSubcategoria
	 * @throws ControladorException
	 */
	/*
	 * public int obterConsumoMinimoLigacaoPorSubcategoria(Imovel imovel,
	 * Collection colecaoSubcategoria) throws ControladorException;
	 */

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relat�rio Resumo das Leituras e Anormalidades
	 * Pesquisa as Anormalidades de Leitura e suas quantidades
	 * 
	 * @author Rafael Corr�a
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarAnormalidadesRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa,
					Integer anoMes) throws ControladorException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relat�rio Resumo das Leituras e Anormalidades
	 * Pesquisa os dados do relat�rio do comparativo de leituras e anormalidades
	 * 
	 * @author Rafael Corr�a
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarDadosRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa, Integer anoMes)
					throws ControladorException;

	public Object[] pesquisarDadosHidrometroTipoLigacaoAgua(Imovel imovel) throws ControladorException;

	/**
	 * [UC0100] Informar Leitura de Fiscaliza��o
	 * 
	 * @author R�mulo Aur�lio
	 * @date 19/05/2007
	 * @return
	 * @throws ControladorException
	 */

	public void informarLeituraFiscalizacao(Usuario usuarioLogado, LeituraFiscalizacao leituraFiscalizacao) throws ControladorException;

	/**
	 * Met�do respons�vel por inserir uma Anormalidade de Leitura
	 * [UC0000 - Inserir Anormalidade Leitura
	 * 
	 * @author Thiago Ten�rio
	 * @date 27/06/2006, 16/11/2006
	 * @param leituraAnormalidade
	 *            leituraAnormalidade
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirAnormalidadeLeitura(LeituraAnormalidade leituraAnormalidade, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0391] Atualizar Anormalidade de Leitura.
	 * 
	 * @author Thiago Ten�rio
	 * @date 01/11/2006
	 * @author eduardo henrique
	 * @date 25/06/2008
	 *       Adi��o de Par�metro de Usu�rio Logado
	 * @param usuarioLogado
	 * @param
	 * @throws ControladorException
	 */
	public void atualizarAnormalidadeLeitura(LeituraAnormalidade leituraAnormalidade, Usuario usuarioLogado) throws ControladorException;

	// valida anoMes para caso de uso anlise excecoes leituras
	public boolean validaDataFaturamentoIncompativel(String anoMesReferencia, String anoMesAtual);

	// valida anoMes para caso de uso anlise excecoes leituras
	public boolean validaDataFaturamentoIncompativelInferior(String anoMesReferencia, String anoMesAnterior);

	/**
	 * relat�rio de regitro atendimento
	 * 
	 * @author Vivianne Sousa
	 * @date 07/06/2007
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public AnaliseConsumoRelatorioOSHelper obterDadosAnaliseConsumo(Integer idImovel) throws ControladorException;

	/**
	 * [UC0613] - Registrar Leituras e Anormalidades
	 * Pesquisa os im�veis com faixa falsa
	 * 
	 * @author Rafael Corr�a
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarImovelFaixaFalsa(Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0613] - Registrar Leituras e Anormalidades
	 * Retorna a quantidade de im�veis com faixa falsa
	 * 
	 * @author Rafael Corr�a
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarImovelFaixaFalsaCount(Integer anoMesReferencia) throws ControladorException;

	/**
	 * relat�rio de regitro atendimento
	 * 
	 * @author Vivianne Sousa
	 * @date 08/06/2007
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public HidrometroRelatorioOSHelper obterDadosHidrometro(Integer idImovel) throws ControladorException;

	/**
	 * Insere a marca de um hidr�metro
	 * 
	 * @param hidrometroMarca
	 *            Marca de hidrometro a ser inserida
	 * @return c�digo da marca que foi inserida
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroMarca(HidrometroMarca hidrometroMarca, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Insere o diametro de um hidr�metro
	 * 
	 * @param hidrometroDiametro
	 *            Diametro do hidrometro a ser inserido
	 * @return c�digo do diametro que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroDiametro(HidrometroDiametro hidrometroDiametro, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Met�do respons�vel por inserir uma Capacidade de hidrometro
	 * [UC0000 - Inserir Capacidade Hidrometro
	 * 
	 * @author Thiago Ten�rio
	 * @date 26/062007
	 * @param hidrometroCapacidade
	 */
	public Integer inserirCapacidadeHidrometro(HidrometroCapacidade hidrometroCapacidade, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Met�do respons�vel por inserir um Cliente Tipo
	 * [UC???? - Inserir Cliente Tipo
	 * 
	 * @author Thiago Ten�rio
	 * @date 18/06/2007
	 * @param ClienteTipo
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public void atualizarCapacidadeHidrometro(HidrometroCapacidade hidrometroCapacidade) throws ControladorException;

	/**
	 * relat�rio de extrato de debito
	 * 
	 * @author Vivianne Sousa
	 * @date 17/07/2007
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public String obterRotaESequencialRotaDoImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0595] Gerar Hist�rico de Medicao
	 * 
	 * @param medicaoTipo
	 *            Tipo de medi��o
	 * @param imovel
	 *            Imovel a ter a medicao gerada
	 * @param faturamentoGrupo
	 *            Grupo de faturamento
	 * @return
	 * @throws ControladorException
	 */
	public MedicaoHistorico gerarHistoricoMedicao(MedicaoTipo medicaoTipo, Imovel imovel, FaturamentoGrupo faturamentoGrupo,
					SistemaParametro sistemaParametro) throws ControladorException;

	/**
	 * [UC0623] Gerar Resumo de Metas CAERN
	 * 
	 * @author S�vio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarConsumoFaturado(Integer idImovel, Integer tipoLigacao, Integer idConsumoTipoMediaImovel,
					Integer idConsumoTipoMediaHidrometro, Integer amArrecadacao) throws ControladorException;

	/**
	 * Met�do respons�vel por inserir um Leiturista
	 * [UC0588 - Inserir Leiturista
	 * 
	 * @author Thiago Ten�rio
	 * @date 22/07/2007
	 * @param leiturista
	 *            leiturista
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirLeiturista(Leiturista leiturista, Usuario usuarioLogado) throws ControladorException;

	// /**
	// * [UC0083] Gerar Dados para Leitura [SB0004] Gerar Movimento do Roteiro
	// * Empresa
	// *
	// * @author Rodrigo Silveira
	// * @date 25/07/2007
	// * @author eduardo henrique
	// * @date 08/09/2008
	// * @param colecaoRota
	// * @param anoMesCorrente
	// * @param idGrupoFaturamento
	// * @param dataPrevistaAtividadeLeitura
	// * TODO
	// * @param idUnidadeIniciada
	// * @throws ControladorException
	// */
	// public void gerarDadosPorLeituraCelularMobile(Collection colecaoRota, Integer anoMesCorrente,
	// Integer idGrupoFaturamento,
	// Integer idFuncionalidadeIniciada, Date dataPrevistaAtividadeLeitura) throws
	// ControladorException;

	/**
	 * Permite gerar o arquivo texto do roteiro empresa para o leiturista.
	 * [UC0627] Gerar Arquivo Texto para Leitura
	 * 
	 * @author Pedro Alexandre, Pedro Alexandre
	 * @date 02/08/2007, 15/10/2007
	 * @param colecaoRoteirosEmpresa
	 * @param anoMesFaturamento
	 * @param faturamentoGrupo
	 * @param idFuncionalidadeIniciada
	 * @param colecaoRotas
	 * @throws ControladorException
	 */
	public void gerarArquivoTextoParaLeiturista(Collection colecaoRoteirosEmpresa, Collection colecaoRotas, Integer anoMesFaturamento,
					FaturamentoGrupo faturamentoGrupo, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Pesquisa as roteiro empresas de acordo com os par�metros informado pelo
	 * usu�rio
	 * [UC0370] - Filtrar Roteiro Empresa
	 * 
	 * @author Thiago Ten�rio
	 * @date 29/08/2007
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @param numeroPagina
	 * @return Collection
	 */
	public Collection pesquisarRoteiroEmpresa(String idEmpresa, String idLocalidade, String codigoSetorComercial, String idLeiturista,
					String indicadorUso, Integer numeroPagina) throws ControladorException;

	/**
	 * Verifica a quantidade de registros retornados da pesquisa de roteiro
	 * empresa
	 * [UC0370] - Filtrar Roteiro Empresa
	 * 
	 * @author Thiago Ten�rio
	 * @date 29/08/2007
	 * @param empresa
	 * @param idLocalidade
	 * @param idLeiturista
	 * @param idSetorComercial
	 * @param indicadorUso
	 * @param numeroPagina
	 */
	public int pesquisarRoteiroEmpresaCount(String idEmpresa, String idLocalidade, String codigoSetorComercial, String idLeiturista,
					String indicadorUso) throws ControladorException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Rela��o(ROL) em TXT - CAER
	 * 
	 * @author S�vio Luiz
	 * @date 24/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalProtecaoTipoLigacaoAgua(Integer idImovel) throws ControladorException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Rela��o(ROL) em TXT - CAER
	 * 
	 * @author S�vio Luiz
	 * @date 24/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalProtecaoTipoTipoPoco(Integer idImovel) throws ControladorException;

	/**
	 * [UC0583] Inserir Roteiro Empresa
	 * Insere um objeto do tipo roteiro empresa no BD
	 * 
	 * @author Francisco Nascimento
	 * @param roteiroEmpresa
	 * @param idLocalidade
	 * @param collectionRoteiroEmpresaAcaoCriterio
	 * @return idRota
	 * @throws ControladorException
	 */
	public Integer inserirRoteiroEmpresa(RoteiroEmpresa roteiroEmpresa, String[] quadras, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC00082] Registrar Leituras e Anormalidades
	 * 
	 * @author S�vio Luiz
	 * @date 29/08/2007
	 * @author eduardo henrique
	 * @date 11/09/2008
	 *       Altera��o na forma de Consulta dos Registros de Movimento a serem processados.(v0.05)
	 * @author eduardo henrique
	 * @date 10/12/2008
	 *       Inclus�o do ano/m�s refer�ncia na consulta dos Movimentos que ser�o processados.
	 * @param idGrupoFaturamento
	 * @param anoMesReferencia
	 * @throws ErroRepositorioException
	 */
	public Collection<MedicaoHistorico> criarMedicoesHistoricoRegistrarLeituraAnormalidade(Integer idGrupoFaturamento,
					Integer anoMesReferencia, Collection<Rota> colecaoRota) throws ControladorException;

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * [SB0001] Baixar Arquivo Texto para o Leiturista.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param imei
	 * @return
	 * @throws ControladorException
	 */
	public byte[] baixarArquivoTextoParaLeitura(long imei) throws ControladorException;

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * Atualizar Situa��o do Arquivo Texto.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param imei
	 * @param situacaoAnterior
	 * @param situacaoNova
	 * @throws ControladorException
	 */
	public void atualizarArquivoTextoEnviado(long imei, int situacaoAnterior, int situacaoNova) throws ControladorException;

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * [SB0002] Atualizar o movimento roteiro empresa.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param dados
	 * @throws ControladorException
	 */
	public void atualiarRoteiro(Collection<DadosMovimentacao> dados) throws ControladorException;

	/**
	 * @author Vivianne Sousa
	 * @date 06/09/2007
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterConsumoMedioEmConsumoHistorico(Integer idImovel, Integer idLigacaoTipo) throws ControladorException;

	/**
	 * Pesquisa os roteiros empresas pelo grupo de faturamento
	 * [UC0627] Gerar Arquivo Texto para Leitura
	 * 
	 * @author Pedro Alexandre
	 * @date 13/09/2007
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarRoteiroEmpresaPorGrupoFaturamento(Integer idFaturamentoGrupo) throws ControladorException;

	/**
	 * Atualizar um roteiro empresa e as quadras associadas
	 * 
	 * @author Francisco do Nascimento
	 * @date 20/09/07
	 * @param roteiroEmpresa
	 * @param idQuadras
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void atualizarRoteiroEmpresa(RoteiroEmpresa roteiroEmpresa, String[] idQuadras, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Remover roteiros empresa
	 * 
	 * @author Francisco do Nascimento
	 * @date 20/09/07
	 * @param ids
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void removerRoteiroEmpresa(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Met�do respons�vel por Liberar um Arquivo Texto de Leitura
	 * [UC0000 - Inserir Anormalidade Leitura
	 * 
	 * @author Thiago Ten�rio
	 * @date 18/09/2007
	 * @param liberarArquivoTextoLeitura
	 *            liberarArquivoTextoLeitura
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer liberarArquivoTextoLeitura(ArquivoTextoRoteiroEmpresa arquivoTextoRoteiroEmpresa) throws ControladorException;

	/**
	 * [UC0391] Atualizar Leiturista.
	 * 
	 * @author Thiago Ten�rio
	 * @date 01/11/2006
	 * @param
	 * @throws ControladorException
	 */
	public void atualizarLeiturista(Leiturista leiturista) throws ControladorException;

	/**
	 * Pesquisa as rotas pelo grupo de faturamento
	 * [UC0627] Gerar Arquivo Texto para Leitura
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarRotaPorGrupoFaturamento(Integer idFaturamentoGrupo) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao, String idCriterioCobranca) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal, String idLocalidade,
					String idCobrancaAcao) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao) throws ControladorException;

	/**
	 * pesquisa o consumo historico passando o imovel e o anomes referencia e o
	 * consumo anormalidade correspondente ao faturameto antecipado.
	 * [UC0113] Faturar Grupo de Faturamento
	 * 
	 * @author S�vio Luiz
	 * @date 08/11/2007
	 * @param idRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarConsumoHistoricoAntecipado(Integer idImovel, Integer anoMes) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String idLocalidade, String idCobrancaAcao) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ControladorException;

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao)
					throws ControladorException;

	/**
	 * Relat�rio Analise de Consumo
	 * Fl�vio Leonardo
	 * 26/12/2007
	 * 
	 * @param idImovel
	 * @param anomes
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarLeiturasImovel(String idImovel, String anoMes) throws ControladorException;

	/**
	 * Relat�rio Manter Hidrometro
	 * Fl�vio Leonardo
	 * pesquisa o id do imovel do hidrometro instalado
	 * 
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarImovelPeloHidrometro(Integer hidrometroId) throws ControladorException;

	/**
	 * [UC0589] Manter Leiturista (Remover)
	 * 
	 * @author Eduardo Henrique
	 * @date 13/06/2008
	 */
	public void removerLeiturista(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0583] - Inserir Roteiro de Empresa
	 * Realiza a consulta de Setores Comerciais cujas quadras possuam rotas contidas num determinado
	 * Grupo de Faturamento
	 * e determinada localidade.
	 * 
	 * @author eduardo henrique
	 * @date 30/06/2008
	 */
	public Collection pesquisarSetoresComerciaisPorLocalidadeGrupoFaturamento(Integer idLocalidade, Integer grupoFaturamento)
					throws ControladorException;

	/**
	 * [UC0099] - Selecionar Fiscaliza��o de Leitura Autor: S�vio Luiz Data:
	 * 04/01/2006
	 * 
	 * @author eduardo henrique
	 * @date 19/09/2008
	 */
	public boolean selecionarFiscalizacaoLeitura(Imovel imovel, SistemaParametro sistemaParametro) throws ControladorException;

	/*
	 * [UC0083] - Gerar dados para Leitura Pesquisa os dados do hidrometro
	 * do tipo po�o e rotorna os dados exigidos no caso de uso. Autor:S�vio
	 * Luiz. Data:27/12/2005
	 */
	public Object[] pesquisarDadosHidrometroTipoPoco(Imovel imovel) throws ControladorException;

	/**
	 * [UC0088] Registrar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 09/10/2008
	 * @param colecaoMedicaoHistorico
	 *            (Cole��es de Medi��esHist�rico para Inser��o/Atualiza��o
	 * @exception ControladorException
	 */
	public void inserirOuAtualizarMedicaoHistorico(Collection<MedicaoHistorico> colecaoMedicaoHistorico) throws ControladorException;

	/**
	 * [UC0088] Registrar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 15/10/2008
	 * @param colecaoConsumoHistorico
	 *            (Cole��es de ConsumosHist�rico para Inser��o/Atualiza��o
	 * @exception ControladorException
	 */
	public void inserirOuAtualizarConsumoHistorico(Collection<ConsumoHistorico> colecaoConsumoHistorico) throws ControladorException;

	/**
	 * Publica��o do m�todo PesquisarConsumoHistorico
	 * 
	 * @author eduardo henrique
	 * @date 21/10/2008
	 * @return Collection<Object[]>
	 * @exception ControladorException
	 */
	public Collection pesquisarConsumoHistorico(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia) throws ControladorException;

	/**
	 * Overload do m�todo de CarregarDadosConsumo [UC0153], para carregar todos os Consumo_Hist�rico
	 * do Im�vel
	 * 
	 * @author eduardo henrique
	 * @date 31/12/2008
	 * @param idImovel
	 *            Id do Im�vel a ser consultado.
	 * @param tipoLigacao
	 *            Id do Tipo da Liga��o do Im�vel.
	 */
	public Collection<ImovelMicromedicao> carregarDadosConsumo(Integer idImovel, Integer tipoLigacao) throws ControladorException;

	/**
	 * [UC0353] Efetuar Liga��o de �gua.
	 * [SB0001] Gerar Liga��o de �gua
	 * 
	 * @author eduardo henrique
	 * @date 18/03/2009
	 * @param numeroPontosUtilizacao
	 *            Inteiro que representa os pontos de utilizacao do imovel (normalmente obtido por
	 *            imovel.getNumeroPontosUtilizacao)
	 * @return Integer volume do Consumo Fixo da Faixa baseada nos Pontos de Utiliza��o.
	 * @throws ControladorException
	 */
	public Integer obterVolumeConsumoFixoFaixaImovel(Integer numeroPontosUtilizacao) throws ControladorException;

	public Collection pesquisarHidrometroProtecaoPorDescricaoAbreviada(String protecao) throws ControladorException;

	public Integer obterUltimaLeituraDoImovel(Integer id) throws ControladorException;

	/**
	 * Relat�rio Analise de Consumo
	 * Yara Souza
	 * 28/04/2010
	 * 
	 * @param idImovel
	 * @param qtdMeses
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConsumoPorQuantidadeMeses(Integer idImovel, Integer anoMesFaturamentoMinimo, Integer anoMesFaturamento)
					throws ControladorException;

	/**
	 * @author Ailton Sousa
	 * @date 16/02/2011
	 * @param imovel
	 * @param colecaoCategorias
	 * @return HashMap
	 * @throws ControladorException
	 */
	public HashMap obterConsumoMinimoLigacaoPorCategoria(Imovel imovel, Collection colecaoCategorias) throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 16/02/2011
	 *       Obter m�dia de consumo do hidr�metro por im�vel
	 */
	public Integer pesquisarMediaConsumoHidrometro(Integer matricula) throws ControladorException;

	/**
	 * @author isilva
	 * @date 23/05/2011
	 *       Obtem as �ltimas refer�ncias dos Dados de Leitura e Consumo do im�vel
	 * @param idImovel
	 *            Identificador do Im�vel
	 * @param maximoResultado
	 *            Resultado m�ximo, se informado null ou um n�mero menor ou igual a zero, a consulta
	 *            retornar� quantidades de linhas ilimitadas
	 * @return
	 * @throws ControladorException
	 */
	public Collection<LeituraConsumoHelper> obterDadosLeituraEConsumo(Integer idImovel, Integer maximoResultado)
					throws ControladorException;

	/**
	 * Relat�rio de Ordem de Servi�o de Liga��o de �gua
	 * 
	 * @author Anderson Italo
	 * @date 25/05/2011
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public HidrometroRelatorioOSHelper obterDadosHidrometroPorImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisar Medi��o Hist�rio para Substitui��o de Leitura
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falc�o
	 * @date 09/06/2011
	 */
	public Object[] pesquisarLeituraConsumoImovel(Integer matricula) throws ControladorException;

	/**
	 * Atualizar Leituras Anteriores
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falc�o
	 * @date 09/06/2011
	 */
	public Collection<MedicaoHistorico> pesquisaMedicaoHistoricoSubstituirLeitura(Integer idImovel) throws ControladorException;

	/**
	 * Atualizar Leituras Anteriores
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falc�o
	 * @date 09/06/2011
	 */
	public void atualizarLeiturasAnteriores(MedicaoHistorico medicaoHistorico, Usuario usuarioLogado, Integer consumoMedio)
					throws ControladorException;

	/**
	 * @author Isaac Silva
	 * @date 10/06/2011
	 * @param colecaoImovelMicromedicao
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void atualizarConsumosAnterioresEConsumosMedio(Collection<ImovelMicromedicao> colecaoImovelMicromedicao, Usuario usuarioLogado,
					boolean atualizarConsumoMedio) throws ControladorException;

	/**
	 * @author Isaac Silva
	 * @date 13/06/2011
	 * @param idImovel
	 * @param anoMesGrupoFaturamento
	 * @param consumoMedioImovel
	 * @param consumoMedioHidrometroAgua
	 * @param consumoMedioHidrometroEsgoto
	 * @param usuario
	 * @throws ControladorException
	 */
	public void atualizarConsumosMedioHistoricoConsumos(Integer idImovel, Integer anoMesGrupoFaturamento, int consumoMedioImovel,
					int consumoMedioHidrometroAgua, int consumoMedioHidrometroEsgoto, Usuario usuario) throws ControladorException;

	/**
	 * Consultar �ltima medi��o hist�rico do im�vel
	 * 
	 * @author Hebert Falc�o
	 * @date 22/06/2011
	 */
	public MedicaoHistorico consultarUltimaMedicaoHistoricoDoImovel(Integer idImovel, Integer idHidrometro) throws ControladorException;

	/**
	 * Verifica Limites de Anormalidades Aceitavel para a empresa.
	 * 
	 * @author P�ricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @throws ControladorException
	 */
	public Map<ControladorException, Collection<Object[]>> verificarLimiteAnormalidadesAceitavel(Integer idFaturamentoGrupo)
					throws ControladorException;

	/**
	 * @param idFaturamentoAtividade
	 * @param idLeituraTipo
	 * @return
	 * @throws ControladorException
	 */
	public Collection<FaturamentoAtividadeCriterio> pesquisarFaturamentoAtividadeCriterioPorLeituraTipo(Integer idFaturamentoAtividade,
					Collection collLeituraTipo) throws ControladorException;

	/**
	 * @param arrayImoveis
	 * @return
	 */

	public Imovel criarImovelApartirDadosPorLeituraConvencional(Object[] arrayImoveis);

	/**
	 * @param imovel
	 * @param anoMesReferencia
	 * @return
	 * @throws ControladorException
	 */
	public boolean validarImovelGerarDadosLeituraTxt(Imovel imovel, Integer anoMesReferencia, Collection colecaoFaturamentoAtividadeCriterio)
					throws ControladorException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaHidrometroInstalacaoHistoricoTipoAgua(Integer idImovel) throws ControladorException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaHidrometroInstalacaoHistoricoTipoPoco(Integer idImovel) throws ControladorException;

	/**
	 * @param imovel
	 * @param colecaoFaturamentoAtividadeCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public short verificarAtendimentoCriterioFaturamentoImovel(Imovel imovel,
					Collection<FaturamentoAtividadeCriterio> colecaoFaturamentoAtividadeCriterio) throws ErroRepositorioException;

	/**
	 * [UC0083] Gerar Dados para Leitura
	 * [SB0003] - Gerar Movimento Roteiro da Empresa
	 * 
	 * @date 17/08/2011
	 */
	public void inserirMovimentoRoteiroEmpresa(SistemaParametro sistemaParametro, Imovel imovelParaSerGerado, Integer anoMesCorrente,
					FuncionalidadeIniciada funcionalidade, Date dataPrevistaAtividadeLeitura, Integer idGrupoFaturamentoRota)
					throws ControladorException, ErroRepositorioException;

	/**
	 * M�todo que gera o resumo das liga��es e economias
	 * [UC0275] - Gerar Resumo das Liga��es/Economias
	 * 
	 * @author Thiago Toscano, Bruno Barros
	 * @date 19/04/2006 17/04/2007
	 * @author Ailton Sousa
	 * @date 10/08/2011 Mudou o par�metro recebido, antes era o Id do Setor Comercial. Mudou a forma
	 *       como � inserida/atualizada a entidade ResumoLigacoesEconomias. Antes era uma colecao,
	 *       agora � individulamente por im�vel.
	 */
	public void gerarResumoLigacoesEconomias(int imovelId, Integer referenciaFaturamento, Integer idRota, boolean eventualBatch)
					throws ControladorException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * 
	 * @author Ailton Sousa
	 * @date 18/08/2011
	 * @param colecaoRotas
	 * @param anoMesFaturamento
	 * @param idGrupoFaturamento
	 * @throws ControladorException
	 */
	public void gerarArquivoTextoFaturamentoImediato(Collection colecaoRotas, Integer anoMesFaturamento, Integer idFaturamentoGrupo,
					Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos. M�todo que recebe um
	 * array com as informa��es de um im�vel para faturamento e retorna um
	 * objeto Imovel.
	 * 
	 * @date 02/08/2011
	 */
	public Imovel obterImovelParaFaturamento(Object[] arrayImovel) throws ControladorException;

	/**
	 * @param imovel
	 * @param anoMesReferencia
	 * @param periodoInformado
	 * @param ligacaoTipo
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarConsumoMedidoHidrometroPeriodoInformadoLigacaoAgua(Imovel imovel, int anoMesReferencia,
					int periodoInformado, LigacaoTipo ligacaoTipo) throws ControladorException;

	/**
	 * @param imovel
	 * @param anoMesReferencia
	 * @param periodoInformado
	 * @param ligacaoTipo
	 * @return
	 * @throws ControladorException
	 */

	public Collection pesquisarConsumoMedidoHidrometroPeriodoInformadoPoco(Imovel imovel, int anoMesReferencia, int periodoInformado)
					throws ControladorException;

	/**
	 * @param imovel
	 *            [obrigatorio]
	 * @param anoMesReferencia
	 *            [obrigatorio]
	 * @return
	 * @throws ErroRepositorioException
	 *             NullPointer se Imovel ou AnoMesReferencia nulos
	 */
	public MovimentoRoteiroEmpresa obterMovimentoRoteiroPorImovel(Imovel imovel, Integer anoMesReferencia) throws ControladorException;

	/**
	 * Consulta que retorna a quantidade de Movimento Roteiro Empresa por FaturamentoGrupo e
	 * AnoMesReferencia.
	 * 
	 * @author Ailton Sousa
	 * @date 29/08/2011
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterQuantidadeMovimentoRoteiroPorGrupoAnoMes(Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * Processa o arquivo de leitura
	 * 
	 * @author P�ricles Tavares
	 * @date 11/08/2011
	 * @param idFaturamentoGrupo
	 * @param arquivoLeitura
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void processarArquivoLeituraAnormalidades(Integer idFaturamentoGrupo, File arquivoLeitura, Usuario usuarioLogado,
					Integer idFucionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo que verifica anormalidades de consumo nos movimentos do faturamento e gera relatorios
	 * [UC3033] Verificar Anormalidades de Consumo nos Movimentos do Faturamento
	 * 
	 * @author Hugo Lima
	 * @date 11/01/2012
	 * @param colecaoRotas
	 * @param anoMesFaturamento
	 * @param idFuncionalidadeIniciada
	 */
	public void verificarAnormalidadesConsumo(Collection rotas, Integer anoMesFaturamento, FaturamentoGrupo faturamentoGrupo,
					int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo que obtem os dados de um hidrometro a partir do tipo de medicao
	 * [UC0713] Emitir Ordem de Servi�o Seletiva
	 * [SB9002] � Obter Dados do Hidr�metro
	 * 
	 * @author Hugo Lima
	 * @date 21/03/2012
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public HidrometroRelatorioOSHelper obterDadosHidrometroPorTipoMedicao(Integer idImovel, Integer idTipoMedicao)
					throws ControladorException;

	/**
	 * Retorna uma cole��o com os dados dos Consumos para apresenta��o
	 * com a maior referencia para ano/mes de faturamento
	 * [UC0713] Emitir Ordem de Servi�o Seletiva
	 * [SB9004] � Obter Dados de Consumo
	 * 
	 * @author Hugo Lima
	 * @date 22/03/2012
	 * @param idImovel
	 * @param tipoLigacao
	 * @return
	 * @throws ControladorException
	 */
	public ImovelDadosConsumoHistoricoHelper obterDadosConsumoMaiorReferenciaFaturamento(Imovel imovel, Integer tipoLigacao)
					throws ControladorException;

	/**
	 * Retorna uma cole��o com os dados dos Consumos para apresenta��o
	 * com a maior referencia para ano/mes de faturamento
	 * [UC0713] Emitir Ordem de Servi�o Seletiva
	 * [SB9003] - Obter Dados de Medi��o
	 * 
	 * @author Hugo Lima
	 * @date 22/03/2012
	 * @param idImovel
	 * @param tipoLigacao
	 * @return
	 * @throws ControladorException
	 */
	public ImovelDadosMedicaoHistoricoHelper obterDadosMedicaoMaiorReferenciaLeitura(Imovel imovel, Integer tipoLigacao)
					throws ControladorException;

	/**
	 * @param idImovel
	 * @param anoMes
	 * @param idMedicaoTipo
	 * @return
	 * @throws ControladorException
	 */
	public MedicaoHistorico pesquisarMedicaoHistorico(Integer idImovel, Integer anoMes, Integer idMedicaoTipo) throws ControladorException;

	/**
	 * [UC0086] - Calcular Faixa de Leitura Esperada
	 */
	public int[] calcularFaixaLeituraEsperada(int media, MedicaoHistorico medicaoHistorico, Hidrometro hidrometro,
					Integer leituraAnteriorPesquisada);

	/**
	 * Consulta o historico de medi�oes de um imovel.
	 * 
	 * @author Marlos Ribeiro
	 * @param matriculaImovel
	 * @return colecao de {@link MedicaoHistorico}
	 */
	public Collection<MedicaoHistorico> consultarMedicaoHistorio(String matriculaImovel) throws ControladorException;

	/**
	 * Consulta o Historio de medicao de consumo de um imovel.
	 * 
	 * @author Marlos Ribeiro
	 * @param matriculaImovel
	 * @return colecao de {@link ImovelMicromedicao}.
	 */
	public Collection<ImovelMicromedicao> consultarConsumoHistorio(String matriculaImovel) throws ControladorException;

	/**
	 * Consulta o hist�rico de cosumo leitura de uma im�vel.
	 * 
	 * @author Marlos Ribeiro
	 * @param matricula
	 * @return Cole��o de {@link ImovelConsumoLeituraHistorico}
	 */
	public Collection<ImovelConsumoLeituraHistorico> consultarConsumoLeituraHistorico(String matriculaImovel) throws ControladorException;

	/**
	 * Rela��o dos im�veis faturados/pr�-faturados no grupo
	 * 
	 * @date 26/08/2012
	 * @author Hebert Falc�o
	 */
	public Collection<Integer> pesquisarImoveisFaturadosOuPreFaturadosNoGrupo(Integer idFaturamentoGrupo, Integer anoMesFaturamento)
					throws ControladorException;

	/**
	 * [UC0337] Calculo Consumo Estimado
	 * 
	 * @author Marlos Ribeiro
	 * @param dataLeituraAnterior
	 * @param dataLeituraAtual
	 * @param leituraAnterior
	 * @param leituraAtual
	 * @return Mapa com as chaves: qtdDiasConsumo, consumidoEstimado, totalConsumidoPeriodo
	 */
	public Map<String, BigDecimal> calcularConsumoEstimado(Date dataLeituraAnterior, Date dataLeituraAtual, Integer leituraAnterior,
					Integer leituraAtual) throws ControladorException, NegocioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * 
	 * @author Josenildo Neves
	 * @date 07/02/2013
	 * @param idRota
	 * @param referenciaFaturamento
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void encerrarFaturamentoGerarResumoLigacoesEconomias(Integer idRota, Integer referenciaFaturamento,
					Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo que gera o resumo das liga��es economias
	 * [UC0275] - Gerar Resumo das Liga��es conomias
	 * 
	 * @author Josenildo Neves
	 * @date 17/01/2013
	 */
	public void gerarResumoLigacoesEconomias(Integer idFaturamentoGrupo, String referenciaFaturamento, int idFuncionalidadeIniciada,
					Usuario usuario) throws ControladorException;

	/**
	 * M�todo utilizado obter o consumo hist�rico de um im�vel de acordo com o
	 * tipo de liga��o e ano m�s de refer�ncia
	 */

	public ConsumoHistorico obterConsumoHistorico(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia) throws ControladorException;

	/**
	 * M�todo utilizado obter o consumo hist�rico de um im�vel de acordo com o
	 * tipo de liga��o e ano m�s de refer�ncia
	 */

	public ConsumoHistorico obterConsumoHistoricoCompleto(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ControladorException;

	/**
	 * Obt�m consumo m�dio do im�vel a partir da tabela consumo_hist�rico como liga��o tipo �gua
	 */
	public Integer obterConsumoMedio(Integer idImovel) throws ControladorException;

	/**
	 * Obt�m ConsumoHist�rico para aterar dados do faturamento (consumoMedio).
	 */
	public ConsumoHistorico obterUltimoConsumoHistoricoImovel(String idImovel) throws ControladorException;

	/**
	 * Atualiza consumo m�dio do �ltimo consumo hist�rico faturado
	 */
	public void atualizarUltimoConsumoHistorico(Integer idConsumoHistorico, Integer valor) throws ControladorException;

	/**
	 * @param imovelParaSerGerado
	 * @param anoMesCorrente
	 * @return
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	public GerarDadosParaLeituraHelper montarGerarDadosParaLeituraHelper(Imovel imovelParaSerGerado, Integer anoMesCorrente,
					SistemaParametro sistemaParametro)
					throws ErroRepositorioException, ControladorException;

	public Integer pesquisarLeiturasImovelCount(String idImovel, String anoMes) throws ControladorException;

	/**
	 * [UC3112][UC3113] Atualizacao Cadastral Coletor de Dados
	 * 
	 * @author Victon Malcolm
	 * @since 08/10/2013
	 */
	public Collection pesquisarAtualizacaoCadastralColetorDados(Integer referenciaInicial,
					Integer referenciaFinal, Integer matricula,
					Integer localidade, Integer setorComercial, Integer rota, Boolean relatorio) throws ControladorException;

	/**
	 * [UC3113] Atualizacao Cadastral Coletor de Dados
	 * 
	 * @author Victon Malcolm
	 * @since 08/10/2013
	 */
	public Collection pesquisarConsultaAtualizacaoCadastralColetorDados(Integer id) throws ControladorException;

	/**
	 * [UC3117] Gerar Relat�rio Auditoria leitura
	 * Seleciona os grupos de faturamento que j� tiveram processo de retorno do faturamento
	 * imediato iniciado ou concluido na refer�ncia atual.
	 * 
	 * @author Felipe Rosacruz
	 * @date 29/09/2013
	 * @return Collection<FaturamentoGrupo>
	 * @throws FachadaException
	 */
	Collection<FaturamentoGrupo> pesquisarGrupoFatProcessoRetornoImedInicConc() throws ControladorException;

	/**
	 * [OC0791503] - Relat�rio Quadro de Hidr�metros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometros(Date dataReferencia) throws ControladorException;

	/**
	 * [OC0791503] - Count Relat�rio Quadro de Hidr�metros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosCount(Date dataReferencia) throws ControladorException;

	/**
	 * [OC0791503] - Relat�rio Quadro de Hidr�metros por Ano de Instala��o
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosAnoInstalacao() throws ControladorException;

	/**
	 * [OC0791503] - Count Relat�rio Quadro de Hidr�metros Ano Instala��o
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosAnoInstalacaoCount() throws ControladorException;

	/**
	 * [OC0791503] - Count Relat�rio Quadro de Hidr�metros Situa��o
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosSituacaoCount(Date dataInicial, Date dataFinal) throws ControladorException;

	/**
	 * [OC0791503] - Count Relat�rio Quadro de Hidr�metros Situa��o
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosSituacao(Date dataInicial, Date dataFinal) throws ControladorException;

}
