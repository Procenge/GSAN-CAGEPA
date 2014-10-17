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
 * Interface Controlador Micromedição PADRÃO
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
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param sistemaParametro
	 *            Descrição do parâmetro
	 * @param esferaPoder
	 *            TODO
	 */
	public void consistirLeiturasCalcularConsumos(Imovel imovelRota, FaturamentoGrupo faturamentoGrupo, SistemaParametro sistemaParametro,
					EsferaPoder esferaPoder) throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param sistemaParametro
	 *            Descrição do parâmetro
	 * @param medicaoTipo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public int[] obterConsumoMedioHidrometro(Imovel imovel, SistemaParametro sistemaParametro, MedicaoTipo medicaoTipo)
					throws ControladorException;

	public int[] obterConsumoMedioImovel(Imovel imovel, SistemaParametro sistemaParametro) throws ControladorException;

	/**
	 * <<Descrição do método>>
	 * 
	 * @author Saulo Lima
	 * @date 03/03/2009
	 *       Alteração para inserir os hidrômetros e atualizar os que já existem na base;
	 *       Retornar a quantidade de hidrômetros inseridos.
	 * @param hidrometro
	 * @param fixo
	 * @param faixaInicial
	 * @param faixaFinal
	 * @return int
	 *         Quantidade de Hidrômetros inseridos
	 * @throws ControladorException
	 */
	public int inserirHidrometro(Hidrometro hidrometro, String fixo, Integer faixaInicial, Integer faixaFinal, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Remover Hidrometro
	 * 
	 * @author Hebert Falcão
	 * @date 18/02/2011
	 * @throws ControladorException
	 */
	public void removerHidrometro(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 03/03/2009
	 *       Alteração para colocar o Generics no retorno
	 * @param fixo
	 * @param faixaInicial
	 * @param faixaFinal
	 * @return Collection<Hidrometro>
	 * @throws ControladorException
	 */
	public Collection<Hidrometro> pesquisarNumeroHidrometroFaixa(String fixo, String faixaInicial, String faixaFinal)
					throws ControladorException;

	/**
	 * Pesquisa uma coleção de hidrômetros de acordo com fixo, faixa inicial e
	 * faixa final
	 * 
	 * @param fixo
	 *            Description of the Parameter
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public Collection pesquisarNumeroHidrometroFaixaRelatorio(String fixo, String faixaInicial, String faixaFinal)
					throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param fixo
	 *            Description of the Parameter
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public Collection pesquisarNumeroHidrometroFaixaPaginacao(String fixo, String faixaInicial, String faixaFinal, Integer numeroPagina)
					throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param fixo
	 *            Description of the Parameter
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
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
	 * < <Descrição do método>>
	 * 
	 * @param colecaoHidrometro
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public String verificarLocalArmazenagemSituacao(Collection colecaoHidrometro) throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param colecaoHidrometro
	 *            Descrição do parâmetro
	 * @param data
	 *            Descrição do parâmetro
	 * @param hora
	 *            Descrição do parâmetro
	 * @param idLocalArmazenagemDestino
	 *            Descrição do parâmetro
	 * @param idMotivoMovimentacao
	 *            Descrição do parâmetro
	 * @param parecer
	 *            Descrição do parâmetro
	 */
	public void inserirAtualizarMovimentacaoHidrometroIds(Collection colecaoHidrometro, String data, String hora,
					String idLocalArmazenagemDestino, String idMotivoMovimentacao, String parecer, Usuario usuario)
					throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param colecaoHidrometro
	 *            Descrição do parâmetro
	 * @param ids
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public Collection obterColecaoObjetosSelecionados(Collection colecaoHidrometro, String[] ids) throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param imovel
	 *            Descrição do parâmetro
	 */
	public void executarImovelTestesMedicaoConsumo(FaturamentoGrupo faturamentoGrupo, Imovel imovel) throws ControladorException;

	public int obterConsumoMinimoLigacao(Imovel imovel, Collection colecaoCategorias) throws ControladorException;

	public int obterConsumoMinimoLigacaoPeriodo(Imovel imovel, Collection colecaoCategorias, String mesAnoConta,
					Integer idConsumoTarifaConta) throws ControladorException;

	/**
	 * [UC0083] - Gerar Dados para Leitura
	 * [SF0001] - Gerar Arquivo Convencional
	 * ou Relação Autor: Sávio Luiz, Pedro Alexandre Data: 21/12/2005,
	 * 15/10/2007
	 * 
	 * @author eduardo henrique
	 * @date 05/09/2008 Alterado funcionamento para envio de e-mail após geração
	 *       do Arquivo Texto.
	 * @author eduardo henrique
	 * @date 07/01/2009 Paginação do hibernate desativada, por forma inadequada
	 *       de execução para o cache do Oracle.
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
	 * [UC0121] - Filtrar Exceções de Leituras e Consumos Flávio Leonardo
	 * Cavalcanti Cordeiro
	 */
	public Collection filtrarExcecoesLeiturasConsumos(FaturamentoGrupo faturamentoGrupo,
					FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql, Integer numeroPagina, boolean todosRegistros)
					throws ControladorException;

	/**
	 * [UC0121] - Filtrar Exceções de Leituras e Consumos Flávio Leonardo
	 * Cavalcanti Cordeiro
	 */
	public Collection pesquisarLigacoesMedicaoIndividualizada(Integer idImovel, String anoMes) throws ControladorException;

	/*
	 * [UC0121] - Filtrar Exceções de Leituras e Consumos Flávio Leonardo
	 * Cavalcanti Cordeiro
	 */
	public Integer filtrarExcecoesLeiturasConsumosCount(FaturamentoGrupo faturamentoGrupo,
					FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql) throws ControladorException;

	/**
	 * [UC0087] - Calcular Faixa de Leitura Falsa Autor: Sávio Luiz Data:
	 * 29/12/2005
	 */
	public Object[] calcularFaixaLeituraFalsa(Imovel imovel, int media, Integer leituraAnterior, MedicaoHistorico medicaoHistorico,
					boolean hidrometroSelecionado, Hidrometro hidrometro) throws ControladorException;

	public Collection pesquisarLeituraAnteriorTipoLigacaoAgua(Integer idImovel, Integer anoMesAnterior) throws ControladorException;

	/**
	 * [UC0098] Manter Vínculos de Imóveis para Rateio de Consumo Atualizar Tipo
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
	 * [UC0098] Manter Vínculos de Imóveis para Rateio de Consumo Atualizar Tipo
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
	 * [UC0098] Manter Vínculos de Imóveis para Rateio de Consumo Atualizar Tipo
	 * Rateio com Vinculo com o Imovel Author: Rafael Santos Data: 16/01/2006
	 * Desfazer Vinculo ao Imovel
	 * 
	 * @param Imovel
	 * @param ids
	 * @throws ControladorException
	 */
	public void desfazerVinculo(Imovel imovel, String[] ids, boolean desvincular, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 */
	public void registrarLeiturasAnormalidades(Collection<MedicaoHistorico> colecaoMedicaoHistorico, Integer idFaturamentoGrupo,
					Integer anoMesReferencia, Usuario usuario) throws ControladorException;

	public Collection pesquisarHidrometroPorHidrometroMovimentacao(Filtro filtro) throws ControladorException;

	/**
	 * Consultar Dados do Cliente Imovel Vinculado Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medição Indiviualizada
	 * 
	 * @param inscricaoImovel
	 *            Inscrição do Imovel
	 * @return Dados do Imovel Vinculado
	 * @throws ControladorException
	 */
	public Cliente consultarDadosClienteImovelUsuario(Imovel imovel) throws ControladorException;

	/**
	 * Consultar Historico Medição Individualizada Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medição Indiviualizada
	 * 
	 * @param imovelCondominio
	 *            Imovel Condominio
	 * @param anoMesFaturamento
	 *            Ano Mês Fauramento
	 * @return Coleção deDados do Historico Medição Individualizada
	 * @throws ControladorException
	 */
	public Collection consultarHistoricoMedicaoIndividualizada(Imovel imovelCondominio, String anoMesFaturamento)
					throws ControladorException;

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
	public Collection consultarConsumoHistoricoImoveisVinculados(ConsumoHistorico consumoHistorico) throws ControladorException;

	/**
	 * Consultar Dados Consumo Tipo Imovel Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medição Indiviualizada
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return
	 * @throws ControladorException
	 */
	public ConsumoTipo consultarDadosConsumoTipoConsumoHistorico(ConsumoHistorico consumoHistorico) throws ControladorException;

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
	 * Data:03/02/2006 Consultar Medicao Historico do Tipo Poço
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
	 * Atualizar Medicação Historico [UC0000] Alterar Dados Para Faturamento
	 * Auhtor: Rafael Corrêa Data: 07/03/2006
	 * 
	 * @param medicaoHistorico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarMedicaoHistorico(MedicaoHistorico medicaoHistorico) throws ControladorException;

	/**
	 * Consultar Imoveis com Medição Indiviualizada Auhtor: Sávio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medição Indiviualizada
	 * 
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public String pesquisarDescricaoRateioTipoLigacaoAgua(Integer idImovel) throws ControladorException;

	/**
	 * Consultar Imoveis com Medição Indiviualizada Auhtor: Sávio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medição Indiviualizada
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
	 * Método que efetua o Rateio do consumo para todos os imóveis de uma rota
	 * que sejam imóveil condominio
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
	 * Método que retorna o maior código de Rota de um Setor Comercial
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @param idSetorComercial
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarMaximoCodigoRota(Integer idSetorComercial) throws ControladorException;

	/**
	 * método que retorna o maior Código de Rota a partir de um Grupo de Faturamento
	 * 
	 * @author Virgínia Melo
	 * @date 18/02/2009
	 * @param idGrupoFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarMaximoCodigoRotaGrupoFaturamento(Integer idGrupoFaturamento) throws ControladorException;

	/**
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarImovelExcecoesApresentaDados(Integer idImovel, boolean ligacaoAgua) throws ControladorException;

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
					boolean ligacaoAgua) throws ControladorException;

	/**
	 * Retorna uma coleção com os dados das medicoes para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Collection carregarDadosMedicao(Integer idImovel, boolean ligacaoAgua) throws ControladorException;

	/**
	 * Retorna uma coleção com os dados das medicoes para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Collection carregarDadosMedicaoResumo(Integer idImovel, boolean ligacaoAgua) throws ControladorException;

	/**
	 * Retorna uma coleção com os dados das medicoes para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Collection carregarDadosConsumo(Integer idImovel, int anoMes, boolean ligacaoAgua) throws ControladorException;

	/**
	 * Método utilizado para pesquisar os consumo historicos a serem
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
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Flávio Cordeiro
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public ImovelMicromedicao pesquiarImovelExcecoesApresentaDadosResumido(Integer idImovel, boolean ligacaoAgua)
					throws ControladorException;

	/**
	 * Retorna um objeto com os dados das medicoes para apresentação
	 * Flávio
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
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
	public Object[] obterDadosConsumoConta(Integer idImovel, int anoMesReferencia, Integer idTipoLigacao) throws ControladorException;

	/**
	 * Método que retorna o número da leitura de retirada do hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarNumeroLeituraRetiradaLigacaoAgua(Integer idLigacaoAgua) throws ControladorException;

	/**
	 * Método que retorna o número da leitura de retirada do hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarNumeroLeituraRetiradaImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0000] - Efetuar Retirada de Hidrômetro
	 * Pesquisa todos os campos do Hidrometro e seus relacionamentos
	 * obrigatórios.
	 * 
	 * @author Thiago Tenório
	 * @date 28/09/2006
	 * @param idHidrometro
	 * @throws ControladorException
	 */
	public Hidrometro pesquisarHidrometroPeloId(Integer idHidrometro) throws ControladorException;

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
	 * @throws ControladorException
	 */
	public Collection<ImovelCobrarDoacaoHelper> pesquisarImovelDoacaoPorRota(Collection<Rota> rotas) throws ControladorException;

	/**
	 * Método que retorna o consumo médio do imóvel
	 * 
	 * @author Ana Maria
	 * @date 17/10/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarConsumoMedioImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarConsumoMedio(Integer idImovel, Integer tipoMedicao) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarMaiorConsumoFaturadoQuantidadeMeses(Integer idImovel, Integer tipoMedicao, short qtdMeses)
					throws ControladorException;

	/**
	 * Atualizar Hidrômetro
	 * Pesquisa o imóvel no qual o hidrômetro está instalado
	 * 
	 * @author Rafael Corrêa
	 * @date 23/11/2006
	 * @param idHidrometro
	 * @return String
	 * @throws ControladorException
	 */
	public String pesquisarImovelHidrometroInstalado(Integer idHidrometro) throws ControladorException;

	/**
	 * [UC0498] - Efetuar Ligação de Água com Instalaação de Hidrômetro
	 * Pesquisa o id do hidrômetro e a sua situação pelo número
	 * 
	 * @author Rafael Corrêa
	 * @date 29/11/2006
	 * @param numeroHidrometro
	 * @return Hidrometro
	 * @throws ControladorException
	 */
	public Hidrometro pesquisarHidrometroPeloNumero(String numeroHidrometro) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroAgua(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroPoco(Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoFaturaMes(Integer amReferencia, Integer idLigacao, Integer idImovel) throws ControladorException;

	/**
	 * Obtém os ids de todas as rotas cadastradas
	 * [UC0251] - Gerar Atividade de Ação de Cobrança
	 * [SB0002] - Gerar Atividade de Ação de Cobrança para os Imóveis da Lista
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
	 * @author Flávio Cordeiro
	 * @date 17/12/2006
	 * @param colecaoLigacoesMedicao
	 * @throws ControladorException
	 */
	public void atualizarLigacoesMedicaoIndividualizada(Collection colecaoLigacoesMedicao, Usuario usuarioLogado, Integer anoMes)
					throws ControladorException;

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
	public Integer pesquisarUltimoAnoMesConsumoFaturado(Integer idImovel, Integer tipoMedicao) throws ControladorException;

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Relação (TXT)
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
	 * [UC00083] Gerar Dados para Leitura Convencional(Relação de Leituras e Comprovantes em PDF)
	 * [SB0002] - Gerar Relação
	 * [SB0004] – Gera dados para leitura convencional DESO
	 * [SB0007] – Gera Relação Lista de Consumidores
	 * [SB0008] – Gera Comprovante de Leitura
	 * 
	 * @author Hebert Falcão
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
	 * Obtém os ids de todas as rotas cadastradas menos as rotas que tiverem o
	 * emp_cobranca = 1
	 * [UC0251] - Gerar Atividade de Ação de Cobrança
	 * [SB0002] - Gerar Atividade de Ação de Cobrança para os Imóveis da Lista
	 * de Rotas
	 * 
	 * @author Sávio Luiz
	 * @date 05/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotasEspecificas() throws ControladorException;

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
					boolean ligacaoAgua) throws ControladorException;

	/**
	 * Pesquisa todas as rotas do sistema. Metódo usado no [UC0302] Gerar Débito
	 * a Cobrar de Acréscimos por Impontualidade
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
	 * [UC0105] Obter Consumo Mínimo da Ligação por Subcategoria
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
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades
	 * Pesquisa as Anormalidades de Leitura e suas quantidades
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarAnormalidadesRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa,
					Integer anoMes) throws ControladorException;

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades
	 * Pesquisa os dados do relatório do comparativo de leituras e anormalidades
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarDadosRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa, Integer anoMes)
					throws ControladorException;

	public Object[] pesquisarDadosHidrometroTipoLigacaoAgua(Imovel imovel) throws ControladorException;

	/**
	 * [UC0100] Informar Leitura de Fiscalização
	 * 
	 * @author Rômulo Aurélio
	 * @date 19/05/2007
	 * @return
	 * @throws ControladorException
	 */

	public void informarLeituraFiscalizacao(Usuario usuarioLogado, LeituraFiscalizacao leituraFiscalizacao) throws ControladorException;

	/**
	 * Metódo responsável por inserir uma Anormalidade de Leitura
	 * [UC0000 - Inserir Anormalidade Leitura
	 * 
	 * @author Thiago Tenório
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
	 * @author Thiago Tenório
	 * @date 01/11/2006
	 * @author eduardo henrique
	 * @date 25/06/2008
	 *       Adição de Parâmetro de Usuário Logado
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
	 * relatório de regitro atendimento
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
	 * Pesquisa os imóveis com faixa falsa
	 * 
	 * @author Rafael Corrêa
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarImovelFaixaFalsa(Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0613] - Registrar Leituras e Anormalidades
	 * Retorna a quantidade de imóveis com faixa falsa
	 * 
	 * @author Rafael Corrêa
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarImovelFaixaFalsaCount(Integer anoMesReferencia) throws ControladorException;

	/**
	 * relatório de regitro atendimento
	 * 
	 * @author Vivianne Sousa
	 * @date 08/06/2007
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public HidrometroRelatorioOSHelper obterDadosHidrometro(Integer idImovel) throws ControladorException;

	/**
	 * Insere a marca de um hidrômetro
	 * 
	 * @param hidrometroMarca
	 *            Marca de hidrometro a ser inserida
	 * @return código da marca que foi inserida
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroMarca(HidrometroMarca hidrometroMarca, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Insere o diametro de um hidrômetro
	 * 
	 * @param hidrometroDiametro
	 *            Diametro do hidrometro a ser inserido
	 * @return código do diametro que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroDiametro(HidrometroDiametro hidrometroDiametro, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Metódo responsável por inserir uma Capacidade de hidrometro
	 * [UC0000 - Inserir Capacidade Hidrometro
	 * 
	 * @author Thiago Tenório
	 * @date 26/062007
	 * @param hidrometroCapacidade
	 */
	public Integer inserirCapacidadeHidrometro(HidrometroCapacidade hidrometroCapacidade, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Metódo responsável por inserir um Cliente Tipo
	 * [UC???? - Inserir Cliente Tipo
	 * 
	 * @author Thiago Tenório
	 * @date 18/06/2007
	 * @param ClienteTipo
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public void atualizarCapacidadeHidrometro(HidrometroCapacidade hidrometroCapacidade) throws ControladorException;

	/**
	 * relatório de extrato de debito
	 * 
	 * @author Vivianne Sousa
	 * @date 17/07/2007
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public String obterRotaESequencialRotaDoImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0595] Gerar Histórico de Medicao
	 * 
	 * @param medicaoTipo
	 *            Tipo de medição
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
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarConsumoFaturado(Integer idImovel, Integer tipoLigacao, Integer idConsumoTipoMediaImovel,
					Integer idConsumoTipoMediaHidrometro, Integer amArrecadacao) throws ControladorException;

	/**
	 * Metódo responsável por inserir um Leiturista
	 * [UC0588 - Inserir Leiturista
	 * 
	 * @author Thiago Tenório
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
	 * Pesquisa as roteiro empresas de acordo com os parâmetros informado pelo
	 * usuário
	 * [UC0370] - Filtrar Roteiro Empresa
	 * 
	 * @author Thiago Tenório
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
	 * @author Thiago Tenório
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
	 * [SB0005] Gerar Relação(ROL) em TXT - CAER
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalProtecaoTipoLigacaoAgua(Integer idImovel) throws ControladorException;

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
	public Collection<MedicaoHistorico> criarMedicoesHistoricoRegistrarLeituraAnormalidade(Integer idGrupoFaturamento,
					Integer anoMesReferencia, Collection<Rota> colecaoRota) throws ControladorException;

	/**
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
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
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
	 * Atualizar Situação do Arquivo Texto.
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
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
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
	 * Metódo responsável por Liberar um Arquivo Texto de Leitura
	 * [UC0000 - Inserir Anormalidade Leitura
	 * 
	 * @author Thiago Tenório
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
	 * @author Thiago Tenório
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
	 * @author Sávio Luiz
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
	 * Relatório Analise de Consumo
	 * Flávio Leonardo
	 * 26/12/2007
	 * 
	 * @param idImovel
	 * @param anomes
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarLeiturasImovel(String idImovel, String anoMes) throws ControladorException;

	/**
	 * Relatório Manter Hidrometro
	 * Flávio Leonardo
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
	 * [UC0099] - Selecionar Fiscalização de Leitura Autor: Sávio Luiz Data:
	 * 04/01/2006
	 * 
	 * @author eduardo henrique
	 * @date 19/09/2008
	 */
	public boolean selecionarFiscalizacaoLeitura(Imovel imovel, SistemaParametro sistemaParametro) throws ControladorException;

	/*
	 * [UC0083] - Gerar dados para Leitura Pesquisa os dados do hidrometro
	 * do tipo poço e rotorna os dados exigidos no caso de uso. Autor:Sávio
	 * Luiz. Data:27/12/2005
	 */
	public Object[] pesquisarDadosHidrometroTipoPoco(Imovel imovel) throws ControladorException;

	/**
	 * [UC0088] Registrar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 09/10/2008
	 * @param colecaoMedicaoHistorico
	 *            (Coleções de MediçõesHistórico para Inserção/Atualização
	 * @exception ControladorException
	 */
	public void inserirOuAtualizarMedicaoHistorico(Collection<MedicaoHistorico> colecaoMedicaoHistorico) throws ControladorException;

	/**
	 * [UC0088] Registrar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 15/10/2008
	 * @param colecaoConsumoHistorico
	 *            (Coleções de ConsumosHistórico para Inserção/Atualização
	 * @exception ControladorException
	 */
	public void inserirOuAtualizarConsumoHistorico(Collection<ConsumoHistorico> colecaoConsumoHistorico) throws ControladorException;

	/**
	 * Publicação do método PesquisarConsumoHistorico
	 * 
	 * @author eduardo henrique
	 * @date 21/10/2008
	 * @return Collection<Object[]>
	 * @exception ControladorException
	 */
	public Collection pesquisarConsumoHistorico(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia) throws ControladorException;

	/**
	 * Overload do método de CarregarDadosConsumo [UC0153], para carregar todos os Consumo_Histórico
	 * do Imóvel
	 * 
	 * @author eduardo henrique
	 * @date 31/12/2008
	 * @param idImovel
	 *            Id do Imóvel a ser consultado.
	 * @param tipoLigacao
	 *            Id do Tipo da Ligação do Imóvel.
	 */
	public Collection<ImovelMicromedicao> carregarDadosConsumo(Integer idImovel, Integer tipoLigacao) throws ControladorException;

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
	 * @throws ControladorException
	 */
	public Integer obterVolumeConsumoFixoFaixaImovel(Integer numeroPontosUtilizacao) throws ControladorException;

	public Collection pesquisarHidrometroProtecaoPorDescricaoAbreviada(String protecao) throws ControladorException;

	public Integer obterUltimaLeituraDoImovel(Integer id) throws ControladorException;

	/**
	 * Relatório Analise de Consumo
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
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 16/02/2011
	 *       Obter média de consumo do hidrômetro por imóvel
	 */
	public Integer pesquisarMediaConsumoHidrometro(Integer matricula) throws ControladorException;

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
	 * @throws ControladorException
	 */
	public Collection<LeituraConsumoHelper> obterDadosLeituraEConsumo(Integer idImovel, Integer maximoResultado)
					throws ControladorException;

	/**
	 * Relatório de Ordem de Serviço de Ligação de Água
	 * 
	 * @author Anderson Italo
	 * @date 25/05/2011
	 * @param
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public HidrometroRelatorioOSHelper obterDadosHidrometroPorImovel(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisar Medição Histório para Substituição de Leitura
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falcão
	 * @date 09/06/2011
	 */
	public Object[] pesquisarLeituraConsumoImovel(Integer matricula) throws ControladorException;

	/**
	 * Atualizar Leituras Anteriores
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falcão
	 * @date 09/06/2011
	 */
	public Collection<MedicaoHistorico> pesquisaMedicaoHistoricoSubstituirLeitura(Integer idImovel) throws ControladorException;

	/**
	 * Atualizar Leituras Anteriores
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falcão
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
	 * Consultar última medição histórico do imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 22/06/2011
	 */
	public MedicaoHistorico consultarUltimaMedicaoHistoricoDoImovel(Integer idImovel, Integer idHidrometro) throws ControladorException;

	/**
	 * Verifica Limites de Anormalidades Aceitavel para a empresa.
	 * 
	 * @author Péricles Tavares
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
	 * Método que gera o resumo das ligações e economias
	 * [UC0275] - Gerar Resumo das Ligações/Economias
	 * 
	 * @author Thiago Toscano, Bruno Barros
	 * @date 19/04/2006 17/04/2007
	 * @author Ailton Sousa
	 * @date 10/08/2011 Mudou o parâmetro recebido, antes era o Id do Setor Comercial. Mudou a forma
	 *       como é inserida/atualizada a entidade ResumoLigacoesEconomias. Antes era uma colecao,
	 *       agora é individulamente por imóvel.
	 */
	public void gerarResumoLigacoesEconomias(int imovelId, Integer referenciaFaturamento, Integer idRota, boolean eventualBatch)
					throws ControladorException;

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
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
	 * [UC0101] - Consistir Leituras e Calcular Consumos. Método que recebe um
	 * array com as informações de um imóvel para faturamento e retorna um
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
	 * @author Péricles Tavares
	 * @date 11/08/2011
	 * @param idFaturamentoGrupo
	 * @param arquivoLeitura
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void processarArquivoLeituraAnormalidades(Integer idFaturamentoGrupo, File arquivoLeitura, Usuario usuarioLogado,
					Integer idFucionalidadeIniciada) throws ControladorException;

	/**
	 * Método que verifica anormalidades de consumo nos movimentos do faturamento e gera relatorios
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
	 * Método que obtem os dados de um hidrometro a partir do tipo de medicao
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB9002] – Obter Dados do Hidrômetro
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
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * com a maior referencia para ano/mes de faturamento
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB9004] – Obter Dados de Consumo
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
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * com a maior referencia para ano/mes de faturamento
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB9003] - Obter Dados de Medição
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
	 * Consulta o historico de mediçoes de um imovel.
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
	 * Consulta o histórico de cosumo leitura de uma imóvel.
	 * 
	 * @author Marlos Ribeiro
	 * @param matricula
	 * @return Coleção de {@link ImovelConsumoLeituraHistorico}
	 */
	public Collection<ImovelConsumoLeituraHistorico> consultarConsumoLeituraHistorico(String matriculaImovel) throws ControladorException;

	/**
	 * Relação dos imóveis faturados/pré-faturados no grupo
	 * 
	 * @date 26/08/2012
	 * @author Hebert Falcão
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
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
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
	 * Método que gera o resumo das ligações economias
	 * [UC0275] - Gerar Resumo das Ligações conomias
	 * 
	 * @author Josenildo Neves
	 * @date 17/01/2013
	 */
	public void gerarResumoLigacoesEconomias(Integer idFaturamentoGrupo, String referenciaFaturamento, int idFuncionalidadeIniciada,
					Usuario usuario) throws ControladorException;

	/**
	 * Método utilizado obter o consumo histórico de um imóvel de acordo com o
	 * tipo de ligação e ano mês de referência
	 */

	public ConsumoHistorico obterConsumoHistorico(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia) throws ControladorException;

	/**
	 * Método utilizado obter o consumo histórico de um imóvel de acordo com o
	 * tipo de ligação e ano mês de referência
	 */

	public ConsumoHistorico obterConsumoHistoricoCompleto(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ControladorException;

	/**
	 * Obtêm consumo médio do imóvel a partir da tabela consumo_histórico como ligação tipo água
	 */
	public Integer obterConsumoMedio(Integer idImovel) throws ControladorException;

	/**
	 * Obtêm ConsumoHistórico para aterar dados do faturamento (consumoMedio).
	 */
	public ConsumoHistorico obterUltimoConsumoHistoricoImovel(String idImovel) throws ControladorException;

	/**
	 * Atualiza consumo médio do último consumo histórico faturado
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
	 * [UC3117] Gerar Relatório Auditoria leitura
	 * Seleciona os grupos de faturamento que já tiveram processo de retorno do faturamento
	 * imediato iniciado ou concluido na referência atual.
	 * 
	 * @author Felipe Rosacruz
	 * @date 29/09/2013
	 * @return Collection<FaturamentoGrupo>
	 * @throws FachadaException
	 */
	Collection<FaturamentoGrupo> pesquisarGrupoFatProcessoRetornoImedInicConc() throws ControladorException;

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometros(Date dataReferencia) throws ControladorException;

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosCount(Date dataReferencia) throws ControladorException;

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros por Ano de Instalação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosAnoInstalacao() throws ControladorException;

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros Ano Instalação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosAnoInstalacaoCount() throws ControladorException;

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros Situação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosSituacaoCount(Date dataInicial, Date dataFinal) throws ControladorException;

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros Situação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosSituacao(Date dataInicial, Date dataFinal) throws ControladorException;

}
