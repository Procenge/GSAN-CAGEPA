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

package gcom.cadastro;

import gcom.cadastro.aguaparatodos.ImovelAguaParaTodos;
import gcom.cadastro.atendimento.*;
import gcom.cadastro.atendimento.bean.AtendimentoDocumentacaoInformadaHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteResponsavel;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.Profissao;
import gcom.cadastro.dadocensitario.LocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.MunicipioDadosCensitario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.PadraoConstrucao;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.sistemaparametro.SistemaAlteracaoHistorico;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.sistemaparametro.bean.DadosEnvioEmailHelper;
import gcom.cadastro.unidadeoperacional.UnidadeOperacional;
import gcom.cobranca.*;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.EmitirDocumentoCobrancaBoletimCadastroHelper;
import gcom.cobranca.bean.FiltroRelatorioDebitoPorResponsavelHelper;
import gcom.cobranca.campanhapremiacao.Campanha;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.CampanhaCadastroFone;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.relatorio.cadastro.imovel.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.FachadaException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Thiago Tenório
 * @created 6 de Dezembro de 2006
 * @version 1.0
 */

public interface ControladorCadastroLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Permite inserir um Histórico Alteração de Sistema
	 * [UC0217] Inserir Historico Alteracao Sistema
	 * 
	 * @author Thiago Tenorio
	 * @date 30/03/2006
	 */

	public Integer inserirHistoricoAlteracaoSistema(SistemaAlteracaoHistorico sistemaAlteracaoHistorico) throws ControladorException;

	/**
	 * Metódo responsável por inserir uma Gerência Regional
	 * [UC0000 - Inserir Gerencia Regional
	 * 
	 * @author Thiago Tenório
	 * @date 27/06/2006, 16/11/2006
	 * @param agencia
	 *            bancaria
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirGerenciaRegional(GerenciaRegional gerenciaRegional) throws ControladorException;

	/**
	 * [UC0391] Atualizar Gerência Regional.
	 * 
	 * @author Thiago Tenório
	 * @date 01/11/2006
	 * @param
	 * @throws ControladorException
	 */
	public void atualizarGerenciaRegional(GerenciaRegional gerenciaRegional) throws ControladorException;

	/**
	 * Pesquisa as empresas que serão processadas no emitir contas
	 * 
	 * @author Sávio Luiz
	 * @date 09/01/2007
	 */

	public Collection pesquisarIdsEmpresa() throws ControladorException;

	/**
	 * Informar Parametros do Sistema
	 * 
	 * @author Rômulo Aurélio
	 * @date 09/01/2007
	 */

	public void informarParametrosSistema(SistemaParametro sistemaParametro, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0534] Inserir Feriado
	 * 
	 * @author Kassia Albuquerque
	 * @date 17/01/2007
	 */
	public Integer inserirFeriado(NacionalFeriado nacionalFeriado, MunicipioFeriado municipioFeriado, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Pesquisa os feriados(nacionais e municipais)
	 * 
	 * @author Kássia Albuquerque
	 * @date 22/01/2007
	 */
	public Collection pesquisarFeriado(Short tipoFeriado, String descricao, Date dataFeriado, Date dataFeriadoFim, Integer idMunicipio,
					Integer numeroPagina) throws ControladorException;

	/**
	 * Pesquisar quantidade de registro dos feriados(nacionais e municipais)
	 * 
	 * @author Kássia Albuquerque
	 * @date 22/01/2007
	 */
	public Integer pesquisarFeriadoCount(Short tipoFeriado, String descricao, Date dataFeriadoInicio, Date dataFeriadoFim,
					Integer idMunicipio) throws ControladorException;

	/**
	 * [UC0535] Manter Feriado [SB0001] Atualizar Feriado
	 * 
	 * @author Kassia Albuquerque
	 * @date 27/01/2006
	 * @pparam feriado
	 * @throws ControladorException
	 */

	public void atualizarFeriado(NacionalFeriado nacionalFeriado, MunicipioFeriado municipioFeriado, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0535] Manter Feriado
	 * Remover Feriado
	 * 
	 * @author Kassia Albuquerque
	 * @date 29/01/2007
	 * @pparam municipio
	 * @throws ControladorException
	 */
	public void removerFeriado(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Pesquisar os ids do Setor comercial pela localidade
	 * 
	 * @author Ana Maria
	 * @date 07/02/2007
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsSetorComercial(Integer idLocalidade) throws ControladorException;

	/**
	 * Informar Mensagem do Sistema
	 * 
	 * @author Kássia Albuquerque
	 * @date 02/03/2007
	 */
	public void atualizarMensagemSistema(SistemaParametro sistemaParametro, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Pesquisa os dados do email do batch para ser enviado
	 * 
	 * @author Sávio Luiz
	 * @date 13/03/2007
	 */
	public EnvioEmail pesquisarEnvioEmail(Integer idEnvioEmail) throws ControladorException;

	public DadosEnvioEmailHelper pesquisarDadosEmailSistemaParametros() throws ControladorException;

	/**
	 * [UC????] Inserir Funcionario
	 * 
	 * @author Rômulo Aurélio
	 * @date 12/04/2007
	 */
	public void inserirFuncionario(Funcionario funcionario, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC????] Atualizar Funcionario
	 * 
	 * @author Rômulo Aurélio
	 * @date 17/04/2007
	 * @param funcionario
	 *            , usuarioLogado, idFuncionario
	 */
	public void atualizarFuncionario(Funcionario funcionario, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Pesquisar todos ids dos setores comerciais.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSetorComercial() throws ControladorException;

	/**
	 * Este caso de uso permite a emissão de boletins de cadastro
	 * [UC0582] Emitir Boletim de Cadastro
	 * 
	 * @author Rafael Corrêa
	 * @data 15/05/2007
	 * @param
	 * @return void
	 */
	public void emitirBoletimCadastro(CobrancaAcaoAtividadeCronograma cronogramaAtividadeAcaoCobranca,
					CobrancaAcaoAtividadeComando comandoAtividadeAcaoCobranca, Date dataAtualPesquisa, CobrancaAcao cobrancaAcao,
					int idFuncionalidadeIniciada) throws ControladorException;

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
	public Integer inserirClienteTipo(ClienteTipo clienteTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Metódo responsável por inserir um Cliente Responsavel
	 * [UC???? - Inserir Cliente Responsavel
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 20/01/2011
	 * @param ClienteResponsavel
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirClienteResponsavel(ClienteResponsavel clienteResponsavel, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 * @throws ErroRepositorioException
	 */
	public Integer inserirProfissao(Profissao profissao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 * @throws ErroRepositorioException
	 */

	public void removerProfissao(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 * @throws ErroRepositorioException
	 */
	public void atualizarProfissao(Profissao profissao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 * @throws ErroRepositorioException
	 */
	public Integer inserirImovelPerfil(ImovelPerfil imovelPerfil, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 * @throws ErroRepositorioException
	 */

	public void removerImovelPerfil(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelPerfil(ImovelPerfil imovelPerfil, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 17/07/2013
	 * @throws ErroRepositorioException
	 */
	public Integer inserirPadraoConstrucao(PadraoConstrucao padraoConstrucao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 17/07/2013
	 * @throws ErroRepositorioException
	 */

	public void removerPadraoConstrucao(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 17/07/2013
	 * @throws ErroRepositorioException
	 */
	public void atualizarPadraoConstrucao(PadraoConstrucao padraoConstrucao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Metódo responsável por inserir um Imovel Agua para Todos
	 * [UC???? - Inserir Cliente Responsavel
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 25/01/2011
	 * @param ImovelAguaParaTodos
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirImovelAguaParaTodos(ImovelAguaParaTodos imovelAguaParaTodos, Usuario usuarioLogado) throws ControladorException;

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
	public void atualizarClienteTipo(ClienteTipo clienteTipo) throws ControladorException;

	/**
	 * Metódo responsável por inserir um Cliente Responsável
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 24/01/2011
	 * @param ClienteResponsável
	 * @param clienteResponsavel
	 * @param usuario
	 * @throws ControladorException
	 */
	public void atualizarClienteResponsavel(ClienteResponsavel clienteResponsavel, Usuario usuario) throws ControladorException;

	/**
	 * Metódo responsável por inserir um Imovel Agua para Todos
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 25/01/2011
	 * @param ImovelAguaParaTodos
	 * @return
	 * @throws ControladorException
	 */
	public void atualizarImovelAguaParaTodos(ImovelAguaParaTodos imovelAguaParaTodos, Imovel imovel, Usuario usuario)
					throws ControladorException;

	/**
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Sávio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarClientesSubordinados(Integer idCliente)

	throws ControladorException;

	/**
	 * [UC0725] Gerar Relatório de Imóveis por Situação da Ligação de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 03/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisSituacaoLigacaoAguaHelper> pesquisarRelatorioImoveisSituacaoLigacaoAgua(
					FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro) throws ControladorException;

	/**
	 * [UC0725] Gerar Relatório de Imóveis por Situação da Ligação de Agua
	 * Pesquisa o Total Registro
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisSituacaoLigacaoAgua(FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro)
					throws ControladorException;

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Collection<RelatorioImoveisFaturasAtrasoHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisFaturasAtrasoHelper> pesquisarRelatorioImoveisFaturasAtraso(
					FiltrarRelatorioImoveisFaturasAtrasoHelper filtro) throws ControladorException;

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * Pesquisa o Total Registro
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisFaturasAtraso(FiltrarRelatorioImoveisFaturasAtrasoHelper filtro)
					throws ControladorException;

	/**
	 * [UC0727] Gerar Relatório de Imóveis por Consumo Medio
	 * 
	 * @author Bruno Barros
	 * @date 17/12/2007
	 * @param FiltrarRelatorioImoveisConsumoMedioHelper
	 * @return Collection<RelatorioImoveisConsumoMedioHelper>
	 * @throws ControladorException
	 */
	public Collection<RelatorioImoveisConsumoMedioHelper> pesquisarRelatorioImoveisConsumoMedio(
					FiltrarRelatorioImoveisConsumoMedioHelper filtro) throws ControladorException;

	/**
	 * Pesquisa a quantidade de imoveis para o relatorio de imoveis por consumo medio
	 * 
	 * @author Bruno Barros
	 * @data 17/12/2007
	 * @param filtro
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisConsumoMedio(FiltrarRelatorioImoveisConsumoMedioHelper filtro)
					throws ControladorException;

	/**
	 * [UC0731] Gerar Relatório de Imóveis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 18/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisUltimosConsumosAguaHelper> pesquisarRelatorioImoveisUltimosConsumosAgua(
					FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro) throws ControladorException;

	/**
	 * [UC0731] Gerar Relatório de Imóveis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 19/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisUltimosConsumosAgua(FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro)
					throws ControladorException;

	/**
	 * [UC00728] Gerar Relatório de Imóveis Ativos e Não Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Collection<RelatorioImoveisAtivosNaoMedidosHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisAtivosNaoMedidosHelper> pesquisarRelatorioImoveisAtivosNaoMedidos(
					FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro) throws ControladorException;

	/**
	 * [UC00728] Gerar Relatório de Imóveis Ativos e Não Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisAtivosNaoMedidos(FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro)
					throws ControladorException;

	/**
	 * [UC00728] Gerar Relatório de Imóveis Excluidos
	 * 
	 * @author Alcira Rocha
	 * @date 31/01/2013
	 * @param FiltrarRelatorioImoveisExcluidossHelper
	 * @return Collection<RelatorioImoveisExcluidosHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisExcluidosHelper> pesquisarRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ControladorException;

	/**
	 * [UC00728] Gerar Relatório de Imóveis Excluidos
	 * 
	 * @author Alcira Rocha
	 * @date 31/01/2013
	 * @param FiltrarRelatorioImoveisExcluidosHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ControladorException;

	/**
	 * [UC00730] Gerar Relatório de Imóveis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Collection<RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper> pesquisarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ControladorException;

	/**
	 * [UC00730] Gerar Relatório de Imóveis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ControladorException;

	/**
	 * [UC0729] Gerar Relatório de Imóveis por Tipo de Consumo
	 * 
	 * @author Bruno Barros
	 * @date 10/01/2008
	 * @param RelatorioImoveisTipoConsumoHelper
	 * @return Collection<RelatorioImoveisTipoConsumoHelper>
	 * @throws FachadaException
	 */
	public Collection<RelatorioImoveisTipoConsumoHelper> pesquisarRelatorioImoveisTipoConsumo(
					FiltrarRelatorioImoveisTipoConsumoHelper filtro) throws ControladorException;

	/**
	 * [UC0729] Gerar Relatório de Imóveis por Tipo de Consumo
	 * 
	 * @author Bruno Barros
	 * @date 10/01/2008
	 * @param RelatorioImoveisTipoConsumoHelper
	 * @return Collection<RelatorioImoveisTipoConsumoHelper>
	 * @throws FachadaException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisTipoConsumo(FiltrarRelatorioImoveisTipoConsumoHelper filtro)
					throws ControladorException;

	/**
	 * Método que retorna o próximo ID para cadastrar uma Empresa.
	 * 
	 * @author Virgínia Melo
	 * @date 05/08/2008
	 */
	public int pesquisarProximoIdEmpresa() throws ControladorException;

	/**
	 * Método responsável por obter os dados da empresa
	 * 
	 * @author Virgínia Melo
	 * @date 09/06/2009
	 * @return endereço da empresa
	 */
	public String[] obterDadosEmpresa() throws ControladorException;

	/**
	 * Método responsável por inserir uma unidade operacional.
	 * 
	 * @author Péricles Tavares
	 * @date 28/01/2011
	 * @return id inserido
	 */
	public Integer inserirUnidadeOperacional(UnidadeOperacional unidadeOperacional, Usuario usuario) throws ControladorException;

	/**
	 * Método responsável por atualizar uma unidade operacional.
	 * 
	 * @author Péricles Tavares
	 * @date 28/01/2011
	 * @return id inserido
	 */
	public void atualizarUnidadeOperacional(UnidadeOperacional unidadeOperacional, Usuario usuario) throws ControladorException;

	public void removerUnidadeOperacional(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0053] Inserir Dados Censitários
	 * Método inserção dos Dados Censitários da Localidade
	 * 
	 * @author Anderson Italo
	 * @date 09/02/2011
	 * @param localidadeDadosCensitario
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirLocalidadeDadosCensitario(LocalidadeDadosCensitario localidadeDadosCensitario, Usuario usuario)
					throws ControladorException;

	/**
	 * [UC0053] Inserir Dados Censitários
	 * Método inserção dos Dados Censitários de Município
	 * 
	 * @author Anderson Italo
	 * @date 09/02/2011
	 * @param municipioDadosCensitario
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirMunicipioDadosCensitario(MunicipioDadosCensitario municipioDadosCensitario, Usuario usuario)
					throws ControladorException;

	/**
	 * [UCXXXX] Manter Dados Censitarios
	 * [SB0002] - Excluir Dados Censitarios
	 * 
	 * @author Anderson Italo
	 * @date 11/02/2011
	 * @param ids
	 *            , usuarioLogado, dadosLocalidade
	 * @throws ControladorException
	 */
	public void removerDadosCensitarios(String[] ids, Usuario usuarioLogado, boolean dadosLocalidade) throws ControladorException;

	/**
	 * [UCXXXX] Manter Dados Censitarios
	 * [SB0003] - Atualizar Dados Censitarios
	 * 
	 * @author Anderson Italo
	 * @date 14/02/2011
	 * @param dadosCensitarios
	 *            , usuario
	 * @throws ControladorException
	 */
	public void atualizarDadosCensitarios(Object dadosCensitarios, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UCXXXX] Manter Cliente Responsável
	 * [SB] - Excluir Cliente Responsável
	 * 
	 * @author isilva
	 * @date 15/02/2011
	 * @param ids
	 *            , usuarioLogado
	 * @throws ControladorException
	 */
	public void removerClienteResponsavel(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2011
	 *       Obter dados dos Grandes Consumidores pelos parametros informados
	 */
	public Collection pesquisaRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial, Integer idLocalidadeFinal)
					throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 18/02/2011
	 *       Obter total de registros do Relatório Grandes
	 *       Consumidores
	 */
	public Integer pesquisaTotalRegistrosRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial,
					Integer idLocalidadeFinal) throws ControladorException;

	/**
	 * [UC0XXX] Relatório Usuários em Débito Automático
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2011 Obter dados dos Usuários com Débito Automático
	 */
	public Collection pesquisaRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal) throws ControladorException;

	/**
	 * [UC0XXX] Relatório Usuários em Débito Automático
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2011 Obter Total de Registros do Relatório Usuários com
	 *       Débito Automático
	 */
	public Integer pesquisarTotalRegistrosRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal)
					throws ControladorException;

	/**
	 * Permite inserir um Consumo por Faixa de Área e Categoria
	 * [UCXXXX] Inserir Consumo por Faixa de Área e Categoria
	 * 
	 * @author Ailton Sousa
	 * @date 01/03/2011
	 */
	public int inserirConsumoFaixaAreaCategoria(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Verifica se já existe a faixa inserida no intervalo de faixas já cadastradas por Categoria
	 * 
	 * @author Ailton Sousa
	 * @data 01/03/2011
	 * @param consumoFaixaAreaCategoria
	 * @return true (caso a faixa esteja entre um intervalo ja cadastrado) ou false (caso contrario)
	 * @throws ControladorException
	 */
	public boolean validarInclusaoFaixaCategoria(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria) throws ControladorException;

	/**
	 * [UC3006] Manter Consumo por Faixa de Área e Categoria [SB0001]Atualizar Consumo por Faixa de
	 * Área e Categoria
	 * 
	 * @author Ailton Sousa
	 * @date 02/03/2011
	 */
	public void atualizarConsumoFaixaAreaCategoria(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC3006] Manter Consumo por Faixa de Área e Categoria[SB0002]Remover Consumo por Faixa de
	 * Área e Categoria
	 * 
	 * @author Ailton Sousa
	 * @date 02/03/2011
	 */
	public void removerConsumoFaixaAreaCategoria(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoLocalidade(Integer idLocalidade) throws ControladorException;

	/**
	 * Este caso de uso gera os arquivos necessarios a execução da agencia virtual WEB.
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @author Josenildo Neves
	 * @data 19/03/2012
	 * @param
	 * @return void
	 */
	public void gerarArquivoAgenciaVirtualWeb(String anoBase, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Consulta os motivos de exclusão do programa Água Para Todos
	 * 
	 * @author Luciano Galvão
	 * @date 20/03/2012
	 * @throws ControladorException
	 */
	public List consultarAguaParaTodosMotivosExclusao() throws ControladorException;

	/**
	 * Habilita um imóvel no programa água para todos
	 * 
	 * @author Luciano Galvão
	 * @date 21/03/2012
	 * @param imovelAguaParaTodos
	 * @param imovel
	 * @param usuario
	 * @throws ControladorException
	 */
	public void habilitarImovelAguaParaTodos(ImovelAguaParaTodos imovelAguaParaTodos, Imovel imovel, Usuario usuario)
					throws ControladorException;

	public List<Integer> consultarSetoresPorFaturamentoAtividadeCronograma(Integer idFaturamentoAtividadeCronograma)
					throws ControladorException;

	/**
	 * Este metódo gera um arquivo de logradouro para agencia virtual web.
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @autor Josenildo Neves
	 * @date 04/09/2012
	 */
	public void gerarDadosArquivoLogradouro(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Este metódo gera um arquivo de Quitação de débitos para agencia virtual web.
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @autor Josenildo Neves
	 * @date 04/09/2012
	 */
	public void gerarDadosArquivoQuitacaoDebitoTxt(String anoBase, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Este caso de uso permite Gerar Arquivo Agencia Virtual WEB
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * Gera os dados do arquivo de imoveis.
	 * 
	 * @autor Josenildo Neves
	 * @date 21/03/2012
	 * @throws ControladorException
	 */
	public void gerarDadosArquivoImoveisTxt(Integer idSetorComrcial, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Este caso de uso permite Gerar Arquivo Agencia Virtual WEB
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * Gera os dados do arquivo de debitos dos imoveis.
	 * 
	 * @autor Josenildo Neves
	 * @date 22/03/2012
	 * @throws ControladorException
	 */
	public void gerarDadosArquivoDebitosImoveisTxt(Integer idSetorComrcial, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * @param collImoveis
	 * @param anoBase
	 * @param dtVencimentoInicial
	 * @param dtVencimentoFinal
	 * @param mesReferenciaInicial
	 * @param mesReferenciaFinal
	 * @param arquivoTxtLinha
	 * @throws ControladorException
	 */
	public Collection<ContaValoresHelper> obterColecaoContasParaArquivo(String idImovel, Date dtVencimentoInicial, Date dtVencimentoFinal,
					String anoMesReferenciaInicial, String anoMesReferenciaFinal) throws ControladorException;

	public void concatenarTodosArquivosAgenciaVirtualZip(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0XXX] Relatório Débito por Reponsável
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Collection<Object[]> pesquisarRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ControladorException;

	/**
	 * [UC0XXX] Relatório Débito por Reponsável
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Integer pesquisarTotalRegistrosRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ControladorException;

	public void gerarProvisaoReceita(int idFuncionalidadeIniciada, Integer idSetorComercial) throws ControladorException;

	public void criarDadosTxtBoletimCadastroModelo1(StringBuilder boletimCadastroTxt,
					EmitirDocumentoCobrancaBoletimCadastroHelper emitirDocumentoCobrancaBoletimCadastroHelper) throws ControladorException;

	public byte[] emitirBoletimCadastroModelo1(FiltrarRelatorioBoletimCadastroHelper filtro)
					throws ControladorException;

	public Collection<RelatorioBoletimCadastralModelo2Bean> consultarDadosBoletimCadastralModelo2(
					FiltrarRelatorioBoletimCadastroHelper filtro) throws ControladorException;

	public Collection verificarInscricaoImovelCampanha(String idImovel, Campanha campanha) throws ControladorException;
	
	public CampanhaCadastro efetuarInscricaoCampanhaPremiacaoAction(Usuario usuario, CampanhaCadastro campanhaCadastro,
					Collection<CampanhaCadastroFone> colecaoCampanhaCadastroFone) throws ControladorException;

	public void emitirComprovanteInscricaoCampanhaPremiacao(Usuario usuarioLogado, CampanhaCadastro campanhaCadastro,
					String indicadorEnvioComprovanteEmail) throws ControladorException;

	public Integer verificarDocumentoImpedido(Integer idCampanha, String nuCPF, String nuCNPJ) throws ControladorException;

	public void enviarEmailComprovanteInscricaoCampanhaPremiacao(String emailReceptor, String emailRemetente, String dsTituloEmail,
					byte[] relatorioGerado) throws ControladorException;

	public String verificarInscricaoBloqueadaImovel(Imovel imovel, Campanha campanha) throws ControladorException;
	
	public Integer efetuarSorteioCampanha(Usuario usuarioLogado, Campanha campanha) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @param sequence
	 * @return
	 * @date 30/10/2013
	 */
	public Integer obterNextValSequence(String sequence) throws ControladorException;

	/**
	 * @param cliente
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarClienteDebitoACobrar(Cliente cliente) throws ControladorException;

	/**
	 * @param cliente
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarClienteGuiaPagamento(Cliente cliente) throws ControladorException;

	/**
	 * [UC0534] Inserir Feriado
	 * [SB0002] - Importar Feriado
	 * 
	 * @author Anderson Italo
	 * @throws IOException
	 * @throws ControladorException
	 * @date 18/08/2014
	 */
	public File importarFeriado(File arquivoFeriados, Usuario usuarioLogado) throws IOException, ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * 
	 * @author Gicevalter Couto
	 * @date 05/09/2014
	 */
	public Short existeProcessoExecucaoFiscal() throws ControladorException;

	/**
	 * @author Gicevalter Couto
	 * @date 05/09/2014
	 */
	public int pesquisarProximoIdNormaProcedimental() throws ControladorException;
	
	public Integer inserirAtendimentoProcedimento(AtendimentoProcedimento atendimentoProcedimento,
					Collection<AtendProcDocumentoPessoaTipo> colecaoAtendProcDocumentoPessoaTipo,
					Collection<AtendProcNormaProcedimental> colecaoAtendProcNormaProcedimental, Usuario usuarioLogado)
					throws ControladorException;
	
	public void atualizarAtendimentoProcedimento(AtendimentoProcedimento atendimentoProcedimento,
					Collection<AtendProcDocumentoPessoaTipo> colecaoAtendProcDocumentoPessoaTipo,
					Collection<AtendProcNormaProcedimental> colecaoAtendProcNormaProcedimental, Usuario usuarioLogado)
					throws ControladorException;

	public Integer inserirAtendimento(Atendimento atendimento,
					Collection<AtendimentoDocumentacaoInformadaHelper> colecaoAtendimentoDocumentacaoInformadaHelper)
					throws ControladorException;

	public void inserirDocumentoEletronico(Collection<DocumentoEletronico> colecaoDocumentoEletronico) throws ControladorException;
}
