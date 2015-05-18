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

package gcom.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.DadosAtualizacaoOSParaInstalacaoHidrometroHelper;
import gcom.atendimentopublico.ordemservico.bean.FiltrarOrdemServicoHelper;
import gcom.atendimentopublico.ordemservico.bean.OrdemServicoSeletivaComandoHelper;
import gcom.atendimentopublico.ordemservico.bean.PesquisarOrdemServicoHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.atendimentopublico.registroatendimento.Tramite;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.imovel.PavimentoCalcada;
import gcom.cadastro.imovel.PavimentoRua;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.InfracaoPerfil;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.gui.atendimentopublico.ordemservico.RoteiroOSDadosProgramacaoHelper;
import gcom.operacional.DistritoOperacional;
import gcom.relatorio.atendimentopublico.FiltrarRelatorioResumoOrdensServicoPendentesHelper;
import gcom.relatorio.atendimentopublico.RelatorioResumoOrdensServicoPendentesHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IRepositorioOrdemServico {

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB001] Selecionar Ordem de Servico por Situação [SB002] Selecionar Ordem
	 * de Servico por Situação da Programação [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Município [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdemServico(PesquisarOrdemServicoHelper filtro, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0454] Obter Descrição da situação da OS
	 * Verificar a situação(ORSE_CDSITUACAO) da ordem de serviço
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idOS
	 * @throws ControladorException
	 */
	public Short verificaSituacaoOS(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0441] Consultar Dados da Ordem de Serviço
	 * 
	 * @author Leonardo Regis
	 * @date 15/08/2006
	 * @param idOrdemServico
	 * @exception ErroRepositorioExceptions
	 */
	public OrdemServico consultarDadosOS(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0427] Tramitar Registro Atendimento
	 * Verifica Existência de Ordem de Serviço Programada
	 * 
	 * @author Leonardo Regis
	 * @date 19/08/2006
	 * @param idOS
	 * @return boolean
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaOSProgramada(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Caso exista registro de atendimento abertos para a unidade de geracao
	 * informada (existe ocorrência na tabela ORDEM_SERVICO com
	 * ORDEM_SERVICO_UNIDADE=Id da Unidade de Atendimento e ATTP_ID=1 -
	 * ABRIR/REGISTRAR)
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param idUnidadeGeracao
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorUnidadeGeracao(Integer idUnidadeGeracao) throws ErroRepositorioException;

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Caso exista registro de atendimento abertos para a unidade de geracao
	 * informada (existe ocorrência na tabela ORDEM_SERVICO com
	 * ORDEM_SERVICO_UNIDADE=Id da Unidade de Atendimento e ATTP_ID=1 -
	 * ABRIR/REGISTRAR)
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param idUnidadeGeracao
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorUnidadeAtual(Integer idUnidadeAtual) throws ErroRepositorioException;

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Seleciona ordem de serviço por codigo do cliente atraves do RASolicitante
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorCodigoClienteRASolicitante(Integer codigoCliente) throws ErroRepositorioException;

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Seleciona ordem de serviço por codigo do cliente atraves do cliente
	 * imovel
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorCodigoCliente(Integer codigoCliente) throws ErroRepositorioException;

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Seleciona ordem de serviço por codigo do cliente atraves do documento
	 * cobrança
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorCodigoClienteCobrancaDocumento(Integer codigoCliente) throws ErroRepositorioException;

	/**
	 * Pesquisar Atividades
	 * Seleciona os atividades no array de servico tipo.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 19/08/2006
	 * @param Collection
	 *            <Integer>
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperarAtividadesServicoTipo(Collection<Integer> atividades) throws ErroRepositorioException;

	/**
	 * Pesquisar Materiais
	 * Seleciona os materiais no array de servico tipo.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 19/08/2006
	 * @param Collection
	 *            <Integer>
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperarMaterialServicoTipo(Collection<Integer> materiais) throws ErroRepositorioException;

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * select a.svtp_id from ATENDIMENTOPUBLICO.SERVICO_TIPO A,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_ATIVIDADE B,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_MATERIAL C WHERE A.SVTP_DSSERVICOTIPO
	 * LIKE '%DESC%' AND A.SVTP_DSABREVIADO LIKE '%DESC%' AND (A.SVTP_VLSERVICO >=
	 * 000000 AND A.SVTP_VLSERVICO <= 99999) AND A.SVTP_ICPAVIMENTO = 1 OU 2 and
	 * A.SVTP_ICATUALIZACOMERCIAL = 1 OU 2 AND A.SVTP_ICTERCEIRIZADO = 1 OU 2
	 * AND A.SVTP_CDSERVICOTIPO = ("O" OR "C") AND (A.SVTP_NNTEMPOMEDIOEXECUCAO >=
	 * 0000 AND A.SVTP_NNTEMPOMEDIOEXECUCAO <= 9999) AND DBTP_ID = ID INFORMADO
	 * AND AND CRTP_ID = ID INFORMADO AND STSG_ID = ID INFORMADO AND STRF_ID =
	 * ID INFORMADO AND STPR_ID = ID INFORMADO AND A.SVTP_ID = B.SVTP_ID AND
	 * B.ATIV_ID IN (ID's INFORMADOS) AND A.SVTP_ID = C.SVTP_ID AND C.MATE_ID IN
	 * (ID's INFORMADOS)
	 * 
	 * @author Leandro Cavalcanti
	 * @date 17/08/2006
	 */
	public Collection<ServicoTipo> filtrarST(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer numeroPaginasPesquisa, Integer idUnidadeOrganizacionalDestino)
					throws ErroRepositorioException;

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * Faz o count para saber q quantidade total de registros retornados
	 * 
	 * @author Leandro Cavalcanti
	 * @date 17/08/2006
	 */
	public Integer filtrarSTCount(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer idUnidadeOrganizacionalDestino)
					throws ErroRepositorioException;

	/**
	 * [UC413]- Pesquisar Tipo de Serviço
	 * Pesquisar o Objeto servicoTipo referente ao idTiposervico recebido na
	 * descrição da pesquisa, onde o mesmo sera detalhado.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 23/08/2006
	 * @param idImovel
	 * @param idSolicitacaoTipoEspecificacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarServicoTipo(Integer idTipoServico) throws ErroRepositorioException;

	/**
	 * Pesquisar Materiais pelo idServicoTipo na tabela de ServicoTipoMaterial
	 * Recupera os materiais do servico tipo material
	 * 
	 * @author Leandro Cavalcanti
	 * @date 24/08/2006
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection recuperarMaterialServicoTipoConsulta(Integer idServicoTipoMaterial) throws ErroRepositorioException;

	/**
	 * Pesquisar Atividades pelo idServicoTipo na tabela de ServicoTipoAtividade
	 * Recupera os Atividades do servico tipo Atividade
	 * 
	 * @author Leandro Cavalcanti
	 * @date 24/08/2006
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection recuperarAtividadeServicoTipoConsulta(Integer idServicoTipoMaterial) throws ErroRepositorioException;

	/**
	 * [UC0367] Atualizar Dados da Ligação Agua
	 * Consulta a ordem de servico pelo id
	 * 
	 * @author Rafael Pinto
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico recuperaOSPorId(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 14/08/2006
	 */
	public boolean existeOrdemServicoDiferenteEncerrado(Integer idregistroAtendimento) throws ErroRepositorioException;

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 14/08/2006
	 */
	public OrdemServico pesquisarOrdemServicoDiferenteEncerrado(Integer idregistroAtendimento, Integer idServicoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0430] Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 17/08/2006
	 */
	public ServicoTipo pesquisarSevicoTipo(Integer id) throws ErroRepositorioException;

	/**
	 * [UC0471] Obter Dados da Equipe
	 * 
	 * @author Raphael Rossiter
	 * @date 01/09/2006
	 * @return idQuipe
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEquipe(Integer idEquipe) throws ErroRepositorioException;

	/**
	 * [UC0471] Obter Dados da Equipe
	 * 
	 * @author Raphael Rossiter
	 * @date 01/09/2006
	 * @return idQuipe
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEquipeComponentes(Integer idEquipe) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<ServicoTipo> pesquisarServicoTipoPorUnidade(Integer unidadeLotacao) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<ServicoTipo> pesquisarServicoTipoPorRA(Integer unidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<ServicoPerfilTipo> pesquisarServicoPerfilTipoPorUnidade(Integer unidadeLotacao, Integer origemServico,
					Short permiteTramiteIndependente) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<Localidade> pesquisarLocalidadePorUnidade(Integer unidade) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<SetorComercial> pesquisarSetorComercialPorUnidade(Integer unidade) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 */
	public Collection<SetorComercial> pesquisarSetorComercialPorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 */
	public Collection<DistritoOperacional> pesquisarDistritoOperacionalPorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 */
	public Collection<UnidadeOrganizacional> pesquisarUnidadeOrganizacionalPorRA(Integer idUnidadeDestino,
 Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Saulo Lima
	 * @since 26/02/2009
	 * @param Integer
	 *            id da Unidade de Destino
	 * @return String
	 *         Query utilizada como subquery em outras consultas
	 */
	public String criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<DistritoOperacional> pesquisarDistritoOperacionalPorUnidade(Integer unidade) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 */
	public Collection<Localidade> pesquisarLocalidadePorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0465] Filtrar Ordem Serviço
	 * Recupera OS programadas
	 * 
	 * @author Leonardo Regis
	 * @date 05/09/2006
	 * @param situacaoProgramacao
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> recuperaOSPorProgramacao(short situacaoProgramacao) throws ErroRepositorioException;

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB003] Selecionar Ordem de Servico por Criterio de Seleção
	 * 
	 * @author Rafael Pinto
	 * @date 07/09/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdemServicoElaborarProgramacao(PesquisarOrdemServicoHelper filtro,
					Short permiteTramiteIndependente) throws ErroRepositorioException;

	/**
	 * [UC0465] Filtrar Ordem Serviço
	 * Recupera OS por Data de Programação
	 * 
	 * @author Leonardo Regis
	 * @date 05/09/2006
	 * @param dataProgramacaoInicial
	 * @param dataProgramacaoFinal
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> recuperaOSPorDataProgramacao(Date dataProgramacaoInicial, Date dataProgramacaoFinal)
					throws ErroRepositorioException;

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadeMaterialExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @return Collection<OsAtividadeMaterialExecucao>
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadeMaterialExecucao> obterOsAtividadeMaterialExecucaoPorOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @return Collection<OsExecucaoEquipe>
	 * @throws ErroRepositorioException
	 */
	public Collection<OsExecucaoEquipe> obterOsExecucaoEquipePorOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @param dataRoteiro
	 * @return collection
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiro(Date dataRoteiro) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author isilva
	 * @date 09/11/2010
	 * @param dataRoteiro
	 * @param equipe
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiro(Date dataRoteiro, Equipe equipe)
					throws ErroRepositorioException;

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadeMaterialExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param idOS
	 * @param idAtividade
	 * @return Collection<OsAtividadeMaterialExecucao>
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadeMaterialExecucao> obterOsAtividadeMaterialExecucaoPorOS(Integer idOS, Integer idAtividade)
					throws ErroRepositorioException;

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @param idAtividade
	 * @return OsExecucaoEquipe
	 * @throws ErroRepositorioException
	 */
	public Collection<OsExecucaoEquipe> obterOsExecucaoEquipePorOS(Integer idOS, Integer idAtividade) throws ErroRepositorioException;

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter programadações ativas por os e situacao
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param idOS
	 * @return Collection<OrdemServicoProgramacao>
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> obterProgramacoesAtivasPorOs(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter o somatório de horas para a OS informada e para o dia do roteiro
	 * solicitado
	 * 
	 * @author Leonardo Regis
	 * @date 15/09/2006
	 * @param idEquipe
	 * @param dataRoteiro
	 * @return OsExecucaoEquipe
	 * @throws ErroRepositorioException
	 */
	public Collection<OsExecucaoEquipe> obterOsExecucaoEquipePorEquipe(Integer idEquipe, Date dataRoteiro) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 */
	public Integer pesquisarRAOrdemServico(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * pesquisa se a OS tenha uma prgramação ativa com a data menor ou igual a
	 * data corrente e não tenha recebido a data de encerramento
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0006] - Verificar Origem do Encerramento da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public Integer pesquisarOSProgramacaoAtiva(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * pesquisa se a OS tenha uma prgramação ativa com a data menor ou igual a
	 * data corrente e não tenha recebido a data de encerramento
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0001] - Encerrar sem execução
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void atualizarParmsOS(Integer numeroOS, Integer idMotivoEncerramento, Date dataEncerramento, String parecerEncerramento,
					String codigoRetornoVistoriaOs, Date dataExecucao) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [SB0001] - Encerrar sem execução
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 */
	public Integer pesquisarOSReferencia(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0001] - Encerrar sem execução
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void atualizarParmsOSReferencia(Integer numeroOSReferencia) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public Collection pesquisarRoteiro(Integer numeroOS, Date dataExecucao) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public Integer verificarExistenciaProgramacaoRoteiroParaOSProgramacao(Integer idOSProgramacao, Integer idRoteiro)
					throws ErroRepositorioException;

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaComDataEncerramento(Integer numeroOS, Date dataExecucao)
					throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection<Atividade>
	 * @throws ErroRepositorioException
	 */
	public Collection<Atividade> obterAtividadesOrdemServico(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection<Atividade>
	 * @throws ErroRepositorioException
	 */
	public Collection<Atividade> obterEquipesProgramadas(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarOSAssociadaDocumentoCobranca(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarOSAssociadaRA(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @author Saulo Lima
	 * @date 04/06/2009
	 *       Correção + alteração para retornar a UnidadeOrganizacional
	 * @param numeroOS
	 * @return UnidadeOrganizacional
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeDestinoUltimoTramite(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param idUnidade
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterEquipesPorUnidade(Integer idUnidade) throws ErroRepositorioException;

	/**
	 * [UC0362] Efetuar Instalação de Hidrômetro
	 * 
	 * @author Leonardo Regis
	 * @date 19/09/2006
	 * @param dadosOS
	 * @throws ErroRepositorioException
	 */
	public void atualizarOSParaEfetivacaoInstalacaoHidrometro(DadosAtualizacaoOSParaInstalacaoHidrometroHelper dadosOS)
					throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @param dataRoteiro
	 *            ,idUnidadeOrganizacional
	 * @return collection
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiroUnidade(Date dataRoteiro, Integer idUnidadeOrganizacional)
					throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * [FS0008] - Verificar possibilidade da inclusão da ordem de serviço
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaPorOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoPorId(Integer idOrdemServicoProgramacao) throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaComDataRoteiroIdEquipe(Integer numeroOS, Date dataRoteiro, Integer idEquipe)
					throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComDataRoteiroIdEquipeDiferenteOS(Integer numeroOS, Date dataRoteiro,
					Integer idEquipe) throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComSequencialMaior(Integer numeroOS, Date dataRoteiro,
					Integer idEquipe, short sequencialReferencia) throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param dataRoteiro
	 * @param idEquipe
	 * @param ordernadoPorSequencial
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComDataRoteiroIdEquipe(Date dataRoteiro, Integer idEquipe,
					boolean ordernadoPorSequencial) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Object[] recuperarParametrosServicoTipo(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public Collection<Equipe> recuperaEquipeDaOSProgramacaoPorDataRoteiro(Integer idOs, Date dataRoteiro) throws ErroRepositorioException;

	/**
	 * Atualização Geral de OS
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param os
	 * @throws ErroRepositorioException
	 */
	public void atualizaOSGeral(OrdemServico os, boolean updateCorte, boolean updateSupressao) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarSolicitacoesServicoTipoOS(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRRepositorioException
	 */
	public Collection pesquisarServicoTipo(Collection idsServicoTipo) throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public boolean verificaExitenciaProgramacaoAtivaParaDiasAnteriores(Integer idOs, Date dataRoteiro) throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 26/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOrdemServicoProgramacaoPorDataRoteiro(Integer idOs, Date dataRoteiro)
					throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public UnidadeOrganizacional pesquisarUnidadeOrganizacionalPorUnidade(Integer idOs, Integer unidadeLotacao)
					throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 26/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdMotivoEncerramentoOSReferida(Integer idOsReferidaRetornoTipo) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 26/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarIndDiagnosticoServicoTipoReferencia(Integer idOrdemServicoReferencia) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 29/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Object[] verificarExistenciaOSEncerrado(Integer idRA, Integer idServicoTipo) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 29/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaServicoTipoReferencia(Integer idServicoTipo) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 02/10/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaUnidadeTerceirizada(Integer idUsuario) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 02/10/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection verificarOSparaRA(Integer idOrdemServico, Integer idRA) throws ErroRepositorioException;

	/**
	 * Pesquisa os campos da OS que serão impressos no relatório de Ordem de
	 * Servico
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idOrdemServico
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSRelatorio(Collection idsOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * Obtém as datas de encerramento e geração de uma ordem de serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 18/10/2006
	 * @param numeroOS
	 * @return Collection
	 */
	public Collection obterDatasGeracaoEncerramentoOS(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterMateriaisProgramados(Integer numeroOS) throws ErroRepositorioException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 *            ,
	 *            idMaterial
	 * @return BigDecimal
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterQuantidadePadraoMaterial(Integer numeroOS, Integer idMaterial) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * Recupera o id do imóvel do registro atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 19/10/2006
	 * @param numeroOS
	 * @return Collection
	 */
	public Integer recuperarIdImovelDoRA(Integer idRA) throws ErroRepositorioException;

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * Recupera o id do imóvel do registro atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 19/10/2006
	 * @param numeroOS
	 * @return Collection
	 */
	public Short recuperarSituacaoOSReferida(Integer idOSReferida) throws ErroRepositorioException;

	/**
	 * [UC0482] - Obter Endereço Abreviado da Ocorrência do RA
	 * Pesquisa os dados da OS usados para saber de onde será recebido o
	 * endereço abreviado
	 * 
	 * @author Rafael Corrêa
	 * @date 19/10/2006
	 * @param idOrdemServico
	 * @throws ErroRepositorioException
	 */

	public Object[] obterDadosPesquisaEnderecoAbreviadoOS(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * Imprimir OS
	 * Atualiza a data de emissão e a de última alteração de OS quando gerar o
	 * relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 26/10/2006
	 * @param colecaoIdsOrdemServico
	 * @throws ErroRepositorioException
	 */
	public void atualizarOrdemServicoRelatorio(Collection colecaoIdsOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Rafael Pinto
	 * @date 09/09/2006
	 * @param idOS
	 * @return OsAtividadePeriodoExecucao
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadePeriodoExecucao> obterOsAtividadePeriodoExecucaoPorOS(Integer idOS, Date dataRoteiro)
					throws ErroRepositorioException;

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Rafael Pinto
	 * @date 09/09/2006
	 * @param idEquipe
	 * @param dataRoteiro
	 * @return OsAtividadePeriodoExecucao
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadePeriodoExecucao> obterOsAtividadePeriodoExecucaoPorEquipe(Integer idEquipe, Date dataRoteiro)
					throws ErroRepositorioException;

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Serviço
	 * Pesquisa os dados da OrdemServicoAtividade
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServico
	 *            ,
	 *            idAtividade
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarOrdemServicoAtividade(Integer numeroOS, Integer idAtividade) throws ErroRepositorioException;

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Serviço
	 * Pesquisa os dados da OsAtividadeMaterialExecucao associada à
	 * OrdemServicoAtividade para a data informada
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServicoAtividade
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOsAtividadeMaterialExecucao(Integer idOrdemServicoAtividade) throws ErroRepositorioException;

	/**
	 * Cria a query de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return Collection
	 */
	public Collection pesquisarOSGerarRelatorioAcompanhamentoExecucao(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ErroRepositorioException;

	/**
	 * Pesquisa a equipe principal de uma OS de programação através do id da OS
	 * 
	 * @author Rafael Corrêa
	 * @date 07/11/2006
	 * @param idOS
	 * @throws ErroRepositorioException
	 */
	public Equipe pesquisarEquipePrincipalOSProgramacao(Integer idOS) throws ErroRepositorioException;

	/**
	 * Pesquisa a equipe principal de uma OS de execução da equipe através do id
	 * da OS
	 * 
	 * @author Rafael Corrêa
	 * @date 07/11/2006
	 * @param idOS
	 * @throws ErroRepositorioException
	 */
	public Equipe pesquisarEquipePrincipalOSExecucaoEquipe(Integer idOS) throws ErroRepositorioException;

	/**
	 * Cria o count de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return Collection
	 */
	public int pesquisarOSGerarRelatorioAcompanhamentoExecucaoCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException;

	/**
	 * Pesquisa as equipes de acordo com os parâmetros informado pelo usuário
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
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
	public Collection pesquisarEquipes(String idEquipe, String nome, String placa, String cargaTrabalho, String idUnidade,
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idTipoEquipe, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * Verifica a quantidade de registros retornados da pesquisa de equipe
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corrêa
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
	public int pesquisarEquipesCount(String idEquipe, String nome, String placa, String cargaTrabalho, String idUnidade,
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idTipoEquipe)
					throws ErroRepositorioException;

	/**
	 * Verifica se o serviço associado á ordem de serviço não corresponde a um serviço de
	 * fiscalização de infração
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 01/11/06
	 * @return Integer
	 */
	public Object[] pesquisarServicoTipoComFiscalizacaoInfracao(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoAgua(Integer idLigacaoAguaSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoEsgoto(Integer idLigacaoEsgotoSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * [SB0001] - Atualizar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarParmsOrdemFiscalizacao(Integer numeroOS, Integer idSituacaoEncontrada, String indicadorDocumentoEntregue)
					throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoAguaOS(Integer idFiscalizacaoSituacao, Integer idLigacaoAguaSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoEsgotoOS(Integer idFiscalizacaoSituacao, Integer idLigacaoEsgotoSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarHidormetroCapacidadeFiscalizacaoAgua(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarHidormetroCapacidadeFiscalizacaoPoco(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Collection pesquisarFiscalizacaoSituacaoServicoACobrar(Integer idFiscalizacaoSituacao) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 20/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsFiscalizacaoSituacaoServicoACobrar(Integer idFiscalizacaoSituacao, Integer idDebitoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0450] Pesquisar Ordem de Servico verifica o tamanho da consulta
	 * [SB001] Selecionar Ordem de Servico por Situação [SB002] Selecionar Ordem
	 * de Servico por Situação da Programação [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Município [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarOrdemServicoTamanho(PesquisarOrdemServicoHelper filtro, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdServicoTipoDaOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * Consulta a ordem de servico pelo id
	 * 
	 * @author Sávio Luiz
	 * @date 03/01/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico pesquisarDadosServicoTipoPrioridade(Integer idServicoTipo) throws ErroRepositorioException;

	/**
	 * Atualiza os imoveis da OS que refere a RA passada como parametro
	 * 
	 * @author Sávio Luiz
	 * @date 03/01/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarOsDaRA(Integer idRA, Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Raphael Rossiter
	 * @date 25/01/2007
	 * @param idOS
	 * @return fiscalizacaoSituacao
	 * @throws ControladorException
	 */
	public FiscalizacaoSituacao pesquisarFiscalizacaoSituacaoPorOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC0455] Exibir Calendário para Elaboração ou Acompanhamento de Roteiro
	 * 
	 * @author Raphael Rossiter
	 * @date 14/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaOSProgramacao(Integer idProgramacaoRoteiro) throws ErroRepositorioException;

	/**
	 * Pesquisa as Os do serviço tipo especifico com RA
	 * 
	 * @author Sávio Luiz
	 * @date 23/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSComServicoTipo(Integer idServicoTipo) throws ErroRepositorioException;

	/**
	 * Pesquisa as Os do serviço tipo especifico com RA
	 * 
	 * @author Sávio Luiz
	 * @date 23/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void atualizarColecaoOS(Collection colecaoIdsOS) throws ErroRepositorioException;

	/**
	 * Pesquisar data e equipe da programação da ordem serviço
	 * 
	 * @author Ana Maria
	 * @date 09/03/2007
	 */
	public OrdemServicoProgramacao pesquisarDataEquipeOSProgramacao(Integer idOs) throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Obtém o número de meses da fatura a partir da tabela FISCALIZACAO_SITUACAO_AGUA
	 * 
	 * @author Raphael Rossiter
	 * @date 16/03/2007
	 * @param idLigacaoAguaSituacao
	 *            , idSituacaoEncontrada
	 * @return numeroMesesFatura
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarNumeroMesesFaturaAgua(Integer idLigacaoAguaSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Obtém o número de meses da fatura a partir da tabela FISCALIZACAO_SITUACAO_ESGOTO
	 * 
	 * @author Raphael Rossiter
	 * @date 16/03/2007
	 * @param idLigacaoEsgotoSituacao
	 *            , idSituacaoEncontrada
	 * @return numeroMesesFatura
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarNumeroMesesFaturaEsgoto(Integer idLigacaoEsgotoSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException;

	/**
	 * Inserir Debitos
	 * Método Criado a pedido de Rossana Carvalho
	 * para inserção de debitos
	 */
	public Collection pesquisaParaCriarDebitosNaoGerados() throws ErroRepositorioException;

	/**
	 * Inserir Debitos Categoria
	 * Método Criado a pedido de Rossana Carvalho
	 * para inserção de debitos
	 */
	public Collection pesquisaParaCriarDebitosCategoriaNaoGerados() throws ErroRepositorioException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComDataRoteiroIdEquipeOrdenada(Date dataRoteiro, Integer idEquipe)
					throws ErroRepositorioException;

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 25/04/2007
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarServicoTipoOperacao(Integer idServicoTipo, Integer idOperacao) throws ErroRepositorioException;

	/**
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Ivan Sérgio
	 * @date 08/11/2007
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	public List filtrarImovelEmissaoOrdensSeletivas(OrdemServicoSeletivaHelper ordemSeletivaHelper, String anormalidadeHidrometro,
					Boolean substituicaoHidrometro, Boolean corte, Integer anoMesFaturamentoAtual,
					String situacaLigacaoAguaPermitidaManutencao, String situacaLigacaoAguaPermitidaCorte)
					throws ErroRepositorioException;

	/**
	 * [UC0732] Gerar Relatorio Acompanhamento de Oredem de Servico de Hidrometro
	 * 
	 * @author Ivan Sérgio
	 * @date 13/12/2007
	 * @param tipoOrdem
	 * @param situacaoOrdem
	 * @param idLocalidade
	 * @param dataEncerramentoInicial
	 * @param dataEncerramentoFinal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicoGerarRelatorioAcompanhamentoHidrometro(String idEmpresa, String tipoOrdem, Short situacaoOrdem,
					String idLocalidade, String dataEncerramentoInicial, String dataEncerramentoFinal, String tipoRelatorio)
					throws ErroRepositorioException;

	/**
	 * [UC0640] Gerar TXT Ação de Cobrança
	 * 
	 * @author Raphael Rossiter
	 * @date 04/01/2008
	 * @param idCobrancaDocumento
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarOrdemServicoPorCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Método retorna uma instância de ServicoTipo com a Collection de servicosAssociados populada.
	 * 
	 * @param id
	 *            Id do Tipo de Serviço
	 * @return ServicoTipo
	 * @throws ErroRepositorioException
	 * @author eduardo henrique
	 * @date 13/05/2009
	 */
	public ServicoTipo pesquisarServicoTipoPorId(Integer id) throws ErroRepositorioException;

	/**
	 * Método retorna uma instância de AtendimentoMotivoEncerramento
	 * 
	 * @param id
	 *            Id do Motivo de Encerramento
	 * @return AtendimentoMotivoEncerramento
	 * @throws ErroRepositorioException
	 * @author eduardo henrique
	 * @date 21/05/2009
	 */
	public AtendimentoMotivoEncerramento pesquisarAtendimentoMotivoEncerramentoPorId(Integer id) throws ErroRepositorioException;

	/**
	 * Método responsável por pesquisar os layouts de ordem de serviço.
	 * 
	 * @author Virgínia Melo
	 * @date 28/05/2009
	 * @return Coleção de OrdemServicoLayout.
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoLayout> pesquisarOrdemServicoLayouts() throws ErroRepositorioException;

	/**
	 * Recupera todos os possíveis motivos de interrupção(ativos).
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção com todos os motivos de interrupcao ativos.
	 */
	public Collection<MotivoInterrupcao> pesquisarMotivoInterrupcao() throws ErroRepositorioException;

	/**
	 * Recupera todos os locais de ocorrência;
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção com todos os locais de ocorrencia ativos.
	 */
	public Collection<LocalOcorrencia> pesquisarLocalOcorrencia() throws ErroRepositorioException;

	/**
	 * Recupera um local de ocorrência.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Local de Ocorrência.
	 */
	public LocalOcorrencia pesquisarLocalOcorrencia(Integer idLocalOcorrencia) throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de PavimentoRua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Rua
	 */
	public Collection<PavimentoRua> pesquisarPavimentoRua() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de PavimentoCalcada ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Calcada
	 */

	public Collection<PavimentoCalcada> pesquisarPavimentoCalcada() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de CausaVazamento ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Causa Vazamento
	 */
	public Collection<CausaVazamento> pesquisarCausaVazamento() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de Agente Externo ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Agente Externo
	 */
	public Collection<AgenteExterno> pesquisarAgenteExterno() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de Diametro Rede Agua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Rede Água
	 */
	public Collection<DiametroRedeAgua> pesquisarDiametroRedeAgua() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de Diametro Ramal Agua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Ramal Água
	 */
	public Collection<DiametroRamalAgua> pesquisarDiametroRamalAgua() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de Diametro Rede Esgoto ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Rede Esgoto
	 */
	public Collection<DiametroRedeEsgoto> pesquisarDiametroRedeEsgoto() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de Diametro Ramal Esgoto ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Ramal Esgoto
	 */
	public Collection<DiametroRamalEsgoto> pesquisarDiametroRamalEsgoto() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de MaterialRedeAgua
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRedeAgua
	 */
	public Collection<MaterialRedeAgua> pesquisarMaterialRedeAgua() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de MaterialRamalAgua
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRamalAgua
	 */
	public Collection<MaterialRamalAgua> pesquisarMaterialRamalAgua() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de MaterialRedeEsgoto
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRedeEsgoto
	 */
	public Collection<MaterialRedeEsgoto> pesquisarMaterialRedeEsgoto() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de MaterialRamalEsgoto
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRamalEsgoto
	 */
	public Collection<MaterialRamalEsgoto> pesquisarMaterialRamalEsgoto() throws ErroRepositorioException;

	/**
	 * Recupera um PavimentoRua.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Rua
	 */
	public PavimentoRua pesquisarPavimentoRua(Integer idPavimento) throws ErroRepositorioException;

	/**
	 * Recupera um PavimentoCalcada.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Calcada
	 */
	public PavimentoCalcada pesquisarPavimentoCalcada(Integer idPavimento) throws ErroRepositorioException;

	/**
	 * Pesquisar ordem de servico
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Ordem Servico
	 */
	public OrdemServico pesquisarOrdemServico(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de OrdemServicoValaPavimento a partir da Ordem de Serviço.
	 * 
	 * @author Virgínia Melo
	 * @date 22/06/2009
	 * @return Coleção de OrdemServicoValaPavimento
	 */
	public Collection<OrdemServicoValaPavimento> pesquisarOrdemServicoValaPavimento(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Virgínia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idProgramacaoRoteiro, Integer idOrdemServico, Integer idEquipe)
					throws ErroRepositorioException;

	/**
	 * Recupera uma OrdemServicoDeslocamento através do id da OS Programação.
	 * 
	 * @author Virgínia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoDeslocamento
	 */
	public OrdemServicoDeslocamento pesquisarOrdemServicoDeslocamento(Integer idOsProgramacao) throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de OSInterrupcaoDeslocamento a partir da OS Programação
	 * 
	 * @author Virgínia Melo
	 * @date 22/06/2009
	 * @return Coleção de OSInterrupcaoDeslocamento
	 */
	public Collection<OrdemServicoInterrupcaoDeslocamento> pesquisarOSInterrupcaoDeslocamento(Integer idOsProgramacao)
					throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de OSInterrupcaoExecucao a partir da OS Programação
	 * 
	 * @author Virgínia Melo
	 * @date 30/06/2009
	 * @return Coleção de OSInterrupcaoExecucao
	 */
	public Collection<OrdemServicoInterrupcaoExecucao> pesquisarOSInterrupcaoExecucao(Integer idOsProgramacao)
					throws ErroRepositorioException;

	/**
	 * Método responsável por retornar a unidade organizacional da OS
	 * 
	 * @author Virgínia Melo
	 * @date 12/06/2009
	 */
	public OrdemServicoUnidade pesquisarOrdemServicoUnidade(Integer idOS) throws ErroRepositorioException;

	/**
	 * Lista uma colecao de OrdemServicoFotoOcorrencia pesquisando por uma
	 * OrdemServicoFotoOcorrencia
	 * 
	 * @param OrdemServicoFotoOcorrencia
	 * @return Collection<OrdemServicoFotoOcorrencia>
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoFotoOcorrencia> listarOSFoto(OrdemServicoFotoOcorrencia osFoto) throws ErroRepositorioException;

	/**
	 * Pesquisa o último número sequencial das fotos, pelo id da ordem de servico
	 * 
	 * @param idOrdemServico
	 * @return numeroSequencia
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServico(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * Pesquisa o último número sequencial das fotos, pelo id da ordem de servico e o id da ordem
	 * servico programacao
	 * 
	 * @param idOrdemServico
	 * @param idOrdemServicoProgramacao
	 * @return numeroSequencia
	 * @throws ControladorException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServicoProgramacao(Integer idOrdemServico, Integer idOrdemServicoProgramacao)
					throws ErroRepositorioException;

	public Collection<ServicoTipo> pesquisarServicoTipoPorRegistroAtendimento(Integer idRegistroAtendimento, Integer idServicoTipo)
					throws ErroRepositorioException;

	public ServicoTipo pesquisarServicoTipo2(Integer idTipoServico) throws ErroRepositorioException;

	public Empresa recuperarAgenteOS(OrdemServico ordemServico);

	/**
	 * Recupera uma coleção de Diametro Cavalete Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 17/08/2010
	 * @return Coleção de Diametro Rede Água
	 */
	public Collection<DiametroCavaleteAgua> pesquisarDiametroCavaleteAgua() throws ErroRepositorioException;

	/**
	 * Recupera uma coleção de Material Cavalete Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 17/08/2010
	 * @return Coleção de Material Cavalete Água
	 */

	public Collection<MaterialCavaleteAgua> pesquisarMaterialCavaleteAgua() throws ErroRepositorioException;

	/**
	 * @author isilva
	 * @param idOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RoteiroOSDadosProgramacaoHelper> pesquisarProgramacaoOrdemServicoRoteiroEquipe(Integer idOrdemServico)
					throws ErroRepositorioException;

	/**
	 * Retorna o maior Sequêncial da Ordem de Serviço Programação, de uma Data para uma Equipe.
	 * 
	 * @param idsOS
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServico> pesquisarOrdensServicos(List<Integer> idsOS) throws ErroRepositorioException;

	/**
	 * Retorna o maior Sequêncial da Ordem de Serviço Programação, de uma Data para uma Equipe.
	 * 
	 * @param idEquipe
	 * @param dtRoteiro
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(Integer idEquipe, Date dtRoteiro) throws ErroRepositorioException;

	/**
	 * Verifica se já existe Ordem de Servico de
	 * Instalacao/Substituicao de Hidrometro para o Imovel
	 * 
	 * @author Ivan Sérgio
	 * @date 19/11/2007
	 * @author Andre Nishimura
	 * @date 13/03/2010
	 *       Modificaçao na emissao de ordens de serviço seletivas para acrescentar novos parametros
	 *       de filtro
	 */
	public Collection verificarOrdemServicoInstalacaoSubstituicaoImovel(Integer idImovel, String dataVencimento, Integer idServicoTipo)
					throws ErroRepositorioException;

	public Collection verificarImoveisSemOrdemPendentePorTipoServico(Collection colecaoImoveis, Integer idServicoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Anderson Lima
	 * @date 13/03/2010
	 *       Pesquisar o total de registros a serem processados
	 */
	public Integer filtrarImovelEmissaoOrdensSeletivasCount(OrdemServicoSeletivaHelper ordemSeletivaHelper, String anormalidadeHidrometro,
					Boolean substituicaoHidrometro, Boolean corte, Integer anoMesFaturamentoAtual,
					String situacaLigacaoAguaPermitidaManutencao, String situacaLigacaoAguaPermitidaCorte)
					throws ErroRepositorioException;

	/**
	 * @param idCobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public OrdemServico pesquisarOSPorDocumentoCobranca(Integer idCobrancaDocumento) throws ErroRepositorioException;

	public List<OrdemServico> pequisarOrdensServicosOrdenadasPorInscricao(List<Integer> blocoIds) throws ErroRepositorioException;

	/**
	 * Recupera o perfil de infração com o máximo de correspondência com os dados do imóvel.
	 * 
	 * @param infracaoTipoId
	 * @param categoriaId
	 * @param subCategoriaId
	 * @param perfilId
	 * @return InfracaoPerfil
	 * @throws ErroRepositorioException
	 */
	public InfracaoPerfil pesquisarInfracaoPerfil(Integer infracaoTipoId, Integer categoriaId, Integer subCategoriaId, Integer perfilId)
					throws ErroRepositorioException;

	/**
	 * Recupera os debitos tipos para o perfil daquela infração.
	 * 
	 * @param infracaoTipoId
	 * @param categoriaId
	 * @param subCategoriaId
	 * @param perfilId
	 * @return Collection<DebitoTipo>
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoTipo> pesquisarDebitosTipoInfracaoPerfil(Integer infracaoPerfilId) throws ErroRepositorioException;

	/**
	 * Recupera a Ocorrencia Infracao pelo nnDoctoInfracao e nnAnoDoctoInfracao.
	 * 
	 * @param nnDoctoInfracao
	 * @param nnAnoDoctoInfracao
	 * @return OcorrenciaInfracao
	 * @throws ControladorException
	 */
	public OcorrenciaInfracao recuperarOcorrenciaInfracao(Integer nnDoctoInfracao, Integer nnAnoDoctoInfracao)
					throws ErroRepositorioException;

	/**
	 * Recupera a Ocorrencia Infracao Item pelo nnDoctoInfracao e nnAnoDoctoInfracao da Ocorrencia
	 * Infranção.
	 * 
	 * @param nnDoctoInfracao
	 * @param nnAnoDoctoInfracao
	 * @return Collection<OcorrenciaInfracaoItem>
	 * @throws ControladorException
	 */
	public Collection<OcorrenciaInfracaoItem> recuperarOcorrenciaInfracaoItem(Integer nnDoctoInfracao, Integer nnAnoDoctoInfracao)
					throws ErroRepositorioException;

	public Collection pesquisarGerarRelatorioProdutividadeEquipe(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ErroRepositorioException;

	/**
	 * Cria o count de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório de Produtividade de Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 13/04/2011
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return Collection
	 */
	public int pesquisarGerarRelatorioProdutividadeEquipeCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException;

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço não Executados por Equipe"
	 * 
	 * @author Péricles TAvares
	 * @date 09/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return int
	 */
	public int pesquisarGerarRelatorioResumoOSNaoExecutadasEquipeCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException;

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço não Executados por Equipe"
	 * 
	 * @author Péricles Tavares
	 * @date 09/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return Collection
	 */
	public Collection pesquisarGerarRelatorioResumoOSNaoExecutadasEquipe(String origemServico, String situacaoOS,
					String[] idsServicosTipos, String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento,
					Date periodoAtendimentoInicial, Date periodoAtendimentoFinal, Date periodoEncerramentoInicial,
					Date periodoEncerramentoFinal, String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade)
					throws ErroRepositorioException;

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço Encerradas"
	 * 
	 * @author Anderson Italo
	 * @date 12/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return Collection
	 */
	public Collection pesquisaRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException;

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço Encerradas"
	 * 
	 * @author Anderson Italo
	 * @date 12/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return Integer
	 */
	public Integer pesquisaTotalRegistrosRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException;

	/**
	 * @author isilva
	 * @param idOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarEquipeEDataProgramacaoOrdemServico(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * Pesquisa os campos da OS que serão impressos no relatório de Ordem de
	 * Servico
	 * 
	 * @author isilva
	 * @date 19/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSRelatorioPadraoCabecalho(Collection idsOrdemServico) throws ErroRepositorioException;

	/**
	 * Pesquisa os campos da OS que serão impressos no relatório de Ordem de
	 * Servico
	 * 
	 * @author isilva
	 * @date 19/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSRelatorioCabecalho(Collection idsOrdemServico) throws ErroRepositorioException;

	/**
	 * @author isilva
	 * @param unidadeLotacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<UnidadeOrganizacional> pesquisarUnidadeOrganizacionalPorUnidade(Integer unidadeLotacao)
					throws ErroRepositorioException;

	/**
	 * [UC1015] Relatório Ordens de Serviço Pendentes
	 * Obter dados para geração relatório.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioResumoOrdensServicoPendentesHelper> pesquisaRelatorioResumoOSPendentes(
					FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC1015] Relatório Ordens de Serviço Pendentes
	 * Obtém o total de registros do relatório.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistrosRelatorioResumoOSPendentes(FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro)
					throws ErroRepositorioException;

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Hebert Falcão
	 * @date 19/07/2011
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idUnidadeOrganizacional, Integer idOrdemServico,
					Integer idEquipe, Date dataRoteiro) throws ErroRepositorioException;

	/**
	 * [UC3040] Obter Unidade Atual da OS
	 * Este método permite obter a unidade atual de uma ordem de serviço
	 * 
	 * @author Anderson Italo
	 * @date 08/02/2012
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeAtualOrdemServico(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0620] - Obter Indicador de Autorização para Manutenção da OS
	 * Este método permite obter a unidade de geração da ordem de serviço
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeGeracaoOrdemServico(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0620] - Obter Indicador de Autorização para Manutenção da OS
	 * Este método permite obter as empresas de cobranca das rotas de uma localidade
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterIdEmpresasCobrancaRotasPorLocalidade(Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0620] - Obter Indicador de Autorização para Manutenção da OS
	 * Este método permite obter a unidade superior por unidade
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeSuperiorPorUnidade(Integer idUnidade) throws ErroRepositorioException;

	/**
	 * [UC3039] Tramitar Ordem de Serviço
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param idOS
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Tramite pesquisarUltimaDataTramiteOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC3039] Tramitar Ordem de Serviço
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param idOS
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date verificarConcorrenciaOS(Integer idOS) throws ErroRepositorioException;

	/**
	 * [UC3036] Obter Unidade Tramite Ordem de Serviço
	 * 
	 * @author Hugo Lima
	 * @date 06/12/2006
	 * @param idServicoTipoTramite
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idBairro
	 * @param idUnidadeOrigem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarUnidadeTramiteOS(Integer idServicoTipoTramite, Integer idLocalidade, Integer idSetorComercial,
					Integer idBairro, Integer idUnidadeOrigem, Short indicadorPrimeiroTramite) throws ErroRepositorioException;

	/**
	 * Consultar Id de Serviço Tipo com Geração Automática filtrando pelo Id da Solicitação Tipo
	 * Especificação
	 * 
	 * @author Hebet Falcão
	 * @date 17/02/2012
	 */
	public Collection<Integer> consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(Integer idSolicitacaoTipoEspecificacao)
					throws ErroRepositorioException;

	/**
	 * [UC0430] Gerar Ordem de Serviço
	 * [FS0010] Verificar restrição de emissão da Ordem de Serviço
	 * 
	 * @author Hugo Lima
	 * @date 07/08/2012
	 * @param idImovel
	 * @param idServicoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date obterEncerramentoOrdemServicoTipoServicoRestricaoPrazoNaoExpirado(Integer idImovel, Integer idServicoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC3063] Efetuar Instalação/Substituição de Registro Magnético
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */
	public OrdemServico recuperaOrdemServicoPorId(Integer idOS) throws ErroRepositorioException;

	/**
	 * Recupera o tipo de relação do cliente e imóvel para exibição no relatório de ordem de
	 * serviço.
	 * 
	 * @param idOSLayout
	 * @date 26/12/2012
	 * @author Ítalo Almeida
	 */
	public String recuperaRelacaoOSClienteImovel(Integer idOSLayout) throws ErroRepositorioException;

	/**
	 * Filtra as ordem de serviço que estão de acordo com o filto.
	 * 
	 * @param filtro
	 * @date 14/06/2013
	 * @author André Lopes
	 */
	public Collection<Object[]> filtrarOrdemServicoWebService(FiltrarOrdemServicoHelper filtro) throws ErroRepositorioException;

	/**
	 * Filtra a ordem de serviço que está de acordo com o filto.
	 * numeroOS* Obrigatório e CodigoServicoOS
	 * 
	 * @param filtro
	 * @date 18/06/2013
	 * @author André Lopes
	 */
	public Object[] filtrarOrdemServicoDetalhesWebService(FiltrarOrdemServicoHelper filtro) throws ErroRepositorioException;

	/**
	 * Consulta para buscarar os Comandos de OS Seletiva.
	 * 
	 * @param filtro
	 * @date 04/10/2013
	 * @author Vicente Zarga
	 */
	public Collection filtrarComandoOSSeletiva(OrdemServicoSeletivaComandoHelper ordemServicoSeletivaComandoHelper)
					throws ErroRepositorioException;

	public Collection filtrarDadosRelatorioComandoOSSeletiva(OrdemServicoSeletivaComandoHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * Obter dados para gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 */
	public Collection pesquisaRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ErroRepositorioException;
					

	/**
	 * [UC0XXX] Gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * Obter dados para gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 */
	public Integer pesquisaTotalRegistrosRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ErroRepositorioException;
					

	/**
	 * @param idServicoTipoTramite
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idBairro
	 * @param idUnidadeOrigem
	 * @return
	 * @throws ErroRepositorioException
	 */
	Collection pesquisarServicoTipoTramite(Integer idServicoTipoTramite, Integer idLocalidade, Integer idSetorComercial, Integer idBairro,
					Integer idUnidadeOrigem) throws ErroRepositorioException;


	/**
	 * Recupera uma coleção de Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Coleção de EntulhoMedida
	 */
	public Collection<EntulhoMedida> pesquisarEntulhoMedida() throws ErroRepositorioException;

	/**
	 * Recupera um Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Entulho Medida
	 */
	public EntulhoMedida pesquisarEntulhoMedida(Integer idEntulhoMedida) throws ErroRepositorioException;

	/**
	 * Pesquisar ordem de serviço.
	 * 
	 * @author Genival Barbosa
	 * @date 30/09/2014
	 * @return Ordem Servico
	 */
	public OrdemServico pesquisarOrdemServicoPrincipal(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<Bairro> pesquisarBairroPorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<Bairro> pesquisarBairroPorUnidade(Integer idUnidade) throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<ServicoTipoPrioridade> pesquisarServicoTipoPrioridadePorRA(Integer idUnidadeDestino,
 Short permiteTramiteIndependente)
					throws ErroRepositorioException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<ServicoTipoPrioridade> pesquisarServicoTipoPrioridadePorUnidade(Integer idUnidade) throws ErroRepositorioException;

	public Integer recuperaQuantidadeDiasUnidade(Integer idOS, Short permiteTramiteIndependente) throws ErroRepositorioException;

}
