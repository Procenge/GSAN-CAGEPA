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

package gcom.atendimentopublico.registroatendimento;

import gcom.acquagis.atendimento.RegistroAtendimentoJSONHelper;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.bean.*;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm;
import gcom.integracao.acquagis.DadosAcquaGis;
import gcom.operacional.DivisaoEsgoto;
import gcom.relatorio.atendimentopublico.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.procenge.comum.exception.NegocioException;

public interface ControladorRegistroAtendimentoLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Data Prevista = Data válida obtida a partir da Data do Atendimento +
	 * número de dias previstos para a Especificação do tipo de Solicitação
	 * (STEP_NNDIAPRAZO da tabela SOLICITACAO_TIPO_ESPECIFICACAO).
	 * Caso o sistema deva sugerir a unidade destino para o primeiro
	 * encaminhamento do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na
	 * tabela SISTEMA_PARAMETROS)
	 * Caso a Especificação esteja associada a uma unidade (UNID_ID da tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO com o valor diferente de nulo), definir a
	 * unidade destino a partir da Especificação (UNID_ID e UNID_DSUNIDADE da
	 * tabela UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com SETP_ID=Id da Especificação
	 * selecionada).
	 * [SB0003] Define Data Prevista e Unidade Destino da Especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper definirDataPrevistaUnidadeDestinoEspecificacao(Date dataAtendimento,
					Integer idSolicitacaoTipoEspecificacao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Valida os dados gerais do atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 27/07/2006
	 * @param dataAtendimento
	 *            ,
	 *            horaAtendimento, tempoEsperaInicial, tempoEsperaFinal,
	 *            idUnidadeOrganizacional
	 * @return void
	 * @throws ControladorException
	 */
	public void validarInserirRegistroAtendimentoDadosGerais(String dataAtendimento, String horaAtendimento, String tempoEsperaInicial,
					String tempoEsperaFinal, String idUnidadeOrganizacional, String numeroRAManual, String especificacao,
					String idRaReiteracao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a Especificação exija a matrícula do imóvel (STEP_ICMATRICULA com o
	 * valor correspondente a um na tabela SOLICITACAO_TIPO_ESPECIFICACAO),
	 * obrigatório; caso contrário, opcional
	 * 
	 * @author Raphael Rossiter
	 * @date 28/07/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarExigenciaImovelPelaEspecificacao(Integer idSolicitacaoTipoEspecificacao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Obter e habilitar/desabilitar os Dados da Identificação do Local da
	 * Ocorrência de acordo com as situações abaixo descritas no caso de uso
	 * 
	 * @author Raphael Rossiter
	 * @date 28/07/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao, idSolicitacaoTipo,
	 *            levantarExceptionImovelInexistente
	 * @return ObterDadosIdentificacaoLocalOcorrenciaHelper
	 * @throws ControladorException
	 */
	public ObterDadosIdentificacaoLocalOcorrenciaHelper obterDadosIdentificacaoLocalOcorrencia(Integer idImovel,
					Integer idSolicitacaoTipoEspecificacao, Integer idSolicitacaoTipo, boolean levantarExceptionImovelInexistente,
					Usuario usuario) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o imóvel (existe
	 * Ocorrência na tabela REGISTRO_ATENDIMENTO com IMOV_ID=matrícula do imóvel
	 * e RGAT_CDSITUACAO=1)
	 * [SB0021] Verifica existência de Registro de Atendimento Pendente para o
	 * imóvel
	 * 
	 * @author Raphael Rossiter
	 * @date 31/07/2006
	 * @param idImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificaExistenciaRAPendenteImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificação
	 * [SB0001] - Gerar Tipo Solicitação com Especificações
	 * 
	 * @author Sávio Luiz
	 * @date 01/08/2006
	 * @param solicitacaoTipo
	 *            ,
	 *            colecaoSolicitacaoTipoEspecificacao, usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer inserirTipoSolicitacaoEspecificacao(SolicitacaoTipo solicitacaoTipo, Collection colecaoSolicitacaoTipoEspecificacao,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param filtroRA
	 * @return void
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> filtrarRegistroAtendimento(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ControladorException;

	/**
	 * [UC3096] AcquaGIS
	 * 
	 * @return Objeto JSON
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimentoJSONHelper> filtrarRegistroAtendimentoWebService(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0002] Habilita/Desabilita Município, Bairro, Área do Bairro e
	 * Divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 02/08/2006
	 * @param idSolicitacaoTipo
	 * @return ObterDadosIdentificacaoLocalOcorrenciaHelper
	 * @throws ControladorException
	 */
	public ObterDadosIdentificacaoLocalOcorrenciaHelper habilitarGeograficoDivisaoEsgoto(Integer idSolicitacaoTipo)
					throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso o sistema deva sugerir a unidade destino para o primeiro
	 * encaminhamento do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na
	 * tabela SISTEMA_PARAMETROS), definir a Unidade Destino da Localidade de
	 * acordo com as regras abaixo. Caso a Especificação não esteja associada a
	 * uma unidade (UNID_ID da tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor
	 * nulo): Caso o Tipo de Solicitação não seja relativo á Área de esgoto
	 * (SOTG_ICESGOTO da tabela SOLICITACAO_TIPO_GRUPO com o valor
	 * correspondente a dois para SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com
	 * SOTP_ID=Id do Tipo de Solicitação selecionado), definir a unidade destino
	 * a partir da localidade informada/selecionada (UNID_ID e UNID_DSUNIDADE da
	 * tabela UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da
	 * tabela LOCALIDADE_SOLIC_TIPO_GRUPO com LOCA_ID=Id da Localidade e
	 * SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com SOTP_ID=Id do Tipo de
	 * Solicitação selecionado) [FS0018 Verificar existência de unidade
	 * centralizadora].
	 * [SB0005] Define Unidade Destino da Localidade
	 * 
	 * @author Raphael Rossiter
	 * @date 04/08/2006
	 * @param idLocalidade
	 *            ,
	 *            idSolicitacaoTipo, idSolicitacaoTipoEspecificacao
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	// public UnidadeOrganizacional definirUnidadeDestinoLocalidade(
	// Integer idSolicitacaoTipoEspecificacao, Integer idLocalidade,
	// Integer idSolicitacaoTipo, boolean solicitacaoTipoRelativoAreaEsgoto)
	// throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso o Tipo de Solicitação seja relativo á Área de esgoto (SOTG_ICESGOTO
	 * da tabela SOLICITACAO_TIPO_GRUPO com o valor correspondente a um para
	 * SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com SOTP_ID=Id do Tipo de
	 * Solicitação selecionado). Caso a quadra esteja preenchida, obter a
	 * divisão de esgoto da quadra (DVES_ID e DVES_DSDIVISAOESGOTO da tabela
	 * DIVISAO_ESGOTO com DVES_ID=DVES_ID da tabela SISTEMA_ESGOTO com
	 * SESG_ID=SESG_ID da tabela BACIA com BACI_ID=BACI_ID da tabela QUADRA com
	 * QDRA_ID=Id da quadra informada/selecionada).
	 * [SB0006] Obtém divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/08/2006
	 * @param solicitacaoTipoRelativoAreaEsgoto
	 *            ,
	 *            idQuadra
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public DivisaoEsgoto obterDivisaoEsgoto(Integer idQuadra, boolean solicitacaoTipoRelativoAreaEsgoto) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso informe a divisão de esgoto: Caso tenha informado a quadra e a mesma
	 * não pertençaa a divisão de esgoto informada (Id da divisão de esgoto é
	 * diferente de DVES_ID da tabela QUADRA com QDRA_ID=Id da quadra
	 * informada).
	 * Caso tenha informado o setor comercial sem a quadra e o setor comercial
	 * não pertençaa á divisão de esgoto informada (Id da divisão de esgoto é
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=Id do setor
	 * comercial informado).
	 * Caso tenha informado a localidade sem o setor comercial e a localidade
	 * não pertença á divisão de esgoto informada (Id da divisão de esgoto é
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=STCM_ID da
	 * tabela SETOR_COMERCIAL com LOCA_ID=Id da localidade informada).
	 * [FS0013] Verificar compatibilidade entre divisão de esgoto e
	 * localidade/setor/quadra
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param idLocalidade
	 *            ,
	 *            idSetorComercial, idQuadra, idDivisaoEsgoto
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarCompatibilidadeDivisaoEsgotoLocalidadeSetorQuadra(Integer idLocalidade, Integer idSetorComercial,
					Integer idQuadra, int idDivisaoEsgoto) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso o sistema deva sugerir a unidade destino para o primeiro
	 * encaminhamento do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na
	 * tabela SISTEMA_PARAMETROS).
	 * Caso a Especificação não esteja associada a uma unidade (UNID_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor nulo):
	 * Caso o Tipo de Solicitação não seja relativo Área de esgoto
	 * (SOTG_ICESGOTO da tabela SOLICITACAO_TIPO_GRUPO com o valor
	 * correspondente a um para SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com
	 * SOTP_ID=Id do Tipo de Solicitação selecionado).
	 * Definir a unidade destino a partir da divisão de esgoto
	 * informada/selecionada (UNID_ID e UNID_DSUNIDADE da tabela
	 * UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da tabela
	 * DIVISAO_ESGOTO com DVES_ID=Id da divisão selecionada) [FS0018 Verificar
	 * existência de unidade centralizadora].
	 * [SB0007] Define Unidade Destino da divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param idDivisaoEsgoto
	 *            ,
	 *            idSolicitacaoTipoEspecificacao,
	 *            solicitacaoTipoRelativoAreaEsgoto
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	// public UnidadeOrganizacional definirUnidadeDestinoDivisaoEsgoto(
	// Integer idSolicitacaoTipoEspecificacao, Integer idDivisaoEsgoto,
	// boolean solicitacaoTipoRelativoAreaEsgoto)
	// throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a unidade destino informada não possa receber registros de
	 * atendimento (UNID_ICTRAMITE=2 na tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ID=Id da unidade destino informada).
	 * [FS0021] - Verificar possibilidade de encaminhamento para a unidade
	 * destino
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param UnidadeOrganizacional
	 * @return void
	 * @throws ControladorException
	 */
	public void verificaPossibilidadeEncaminhamentoUnidadeDestino(UnidadeOrganizacional unidadeDestino) throws ControladorException;

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificações
	 * Verifica se o Serviço tipo tem como Serviço automatico geração
	 * automática.
	 * [SF0003] Validar Tipo de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param idServicoTipo
	 * @throws ErroRepositorioException
	 */
	public void verificarServicoTipoReferencia(Integer idServicoTipo) throws ControladorException;

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificações
	 * Verifica se na coleção existe algum ordem de execução .
	 * [SF0004] Validar valor ordem execução parte
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param colecaoEspecificacaoServicoTipo
	 *            ,ordemExecucao
	 * @throws ErroRepositorioException
	 */
	public void verificarExistenciaOrdemExecucao(Collection colecaoEspecificacaoServicoTipo, Short ordemExecucao)
					throws ControladorException;

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificações
	 * Verifica se na coleção existe algum ordem de execução fora da
	 * ordem(1,2,3,4,5,6).Ex.:não exista numero 2.
	 * [SF0004] Validar valor ordem execução 2 parte
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param colecaoEspecificacaoServicoTipo
	 * @throws ErroRepositorioException
	 */
	public void verificarOrdemExecucaoForaOrdem(Collection colecaoEspecificacaoServicoTipo) throws ControladorException;

	/**
	 * [UC0420] Obter Descrição da Situação do RA
	 * 
	 * @author Leonardo Regis
	 * @date 09/08/2006
	 * @param idRa
	 * @return String
	 * @throws ControladorException
	 */
	/*
	 * public String obterDescricaoSituacaoRA(Integer idRa) throws
	 * ControladorException;
	 */

	/**
	 * [UC0418] Obter Unidade Atual do RA
	 * 
	 * @author Leonardo Regis
	 * @date 09/08/2006
	 * @param idRa
	 * @return String
	 * @throws ControladorException
	 */
	/*
	 * public UnidadeOrganizacional obterUnidadeAtualRA(Integer idRa) throws
	 * ControladorException;
	 */

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o imóvel com a mesma
	 * Especificação (existe Ocorrência na tabela REGISTRO_ATENDIMENTO com
	 * IMOV_ID=matrícula do imóvel e STEP_ID=Id da Especificação selecionada e
	 * RGAT_CDSITUACAO=1).
	 * [FS0020] - Verificar existência de registro de atendimento para o imóvel
	 * com a mesma Especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 31/07/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarExistenciaRAImovelMesmaEspecificacao(Integer idImovel, Integer idSolicitacaoTipoEspecificacao)
					throws ControladorException;

	/**
	 * [UC0424] Consultar Registro Atendimento
	 * Retorno o Tramite mais atual a partir
	 * 
	 * @author Rafael Pinto
	 * @date 10/08/2006
	 * @param idRA
	 * @return Tramite
	 * @throws ErroRepositorioException
	 */
	public Tramite recuperarTramiteMaisAtualPorRA(Integer idRA) throws ControladorException;

	/**
	 * [UC0418] Obter Unidade Atual do RA
	 * Este caso de uso permite obter a unidade atual de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 03/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeAtualRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0419] Obter Indicador de Autorização para Manutenção do RA
	 * Este caso de uso obtém o indicador de Autorização para Manutenção do RA,
	 * ou seja, se o usuário tem Autorização para efetuar a Manutenção do RA
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idUnidadeOrganizacional
	 * @param idUusuario
	 * @throws ControladorException
	 */
	public Short obterIndicadorAutorizacaoManutencaoRA(Integer idUnidadeOrganizacional, Integer idUsuario) throws ControladorException;

	/**
	 * [UC0420] Obter Descrição da Situação da RA
	 * Este caso de uso permite obter a Descrição de um registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 04/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0421] Obter Unidade de Atendimento do RA
	 * Este caso de uso permite obter a unidade de atendimento de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 04/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeAtendimentoRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0422] Obter Endereço da Ocorrência do RA
	 * Este caso de uso permite obter o Endereço da Ocorrência de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String obterEnderecoOcorrenciaRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0422] Obter endereço da ocorrência do RA Este caso de uso permite obter o endereço da
	 * ocorrência de um registro de
	 * atendimento
	 * 
	 * @author André Lopes
	 * @date 17/06/2013
	 * @param idRegistroAtendimento
	 * @return [0] Endereço Formatado (Caso possível sem o número do imóvel)
	 *         [1] Número do imóvel.
	 * @throws ControladorException
	 */
	public String[] obterEnderecoeNumeroOcorrenciaRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0423] Obter Endereço do Solicitante do RA
	 * Este caso de uso permite obter o Endereço do solicitante de um registro
	 * de atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String obterEnderecoSolicitanteRA(Integer idRegistroAtendimentoSolicitante) throws ControladorException;

	/**
	 * [UC0433] Obter Registro de Atendimento Associado
	 * Este caso de uso permite obter o registro de atendimento associado a
	 * outro
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public ObterRAAssociadoHelper obterRAAssociado(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0434] Obter Unidade de Encerramento do RA
	 * Este caso de uso permite obter a unidade de encerramento de um registro
	 * de atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeEncerramentoRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0445] Obter Nome do Solicitante do RA
	 * Este caso de uso permite obter o nome do solicitante de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 09/08/2006
	 * @param idRegistroAtendimentoSolicitante
	 * @throws ControladorException
	 */
	public String obterNomeSolicitanteRA(Integer idRegistroAtendimentoSolicitante) throws ControladorException;

	/**
	 * [UC0425] - Reiterar Registro de Atendimento
	 * 
	 * @author lms
	 * @date 10/08/2006
	 * @param raNovo
	 *            TODO
	 */
	public Integer reiterarRegistroAtendimento(RegistroAtendimento registroAtendimento, Usuario usuario, RegistroAtendimento raNovo)
					throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0020] Verifica Situação do imóvel e Especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 10/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarSituacaoImovelEspecificacao(Imovel imovel, Integer idSolicitacaoTipoEspecificacao) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Verificar existencia ordem de Serviço para o registro atendimento
	 * pesquisado
	 * 
	 * @author Sávio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer verificarOrdemServicoParaRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Pesquisar os parametros para atualizar o registro atendimento escolhido
	 * pelo usuário
	 * 
	 * @author Sávio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsRegistroAtendimento(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0446] Consultar Trâmites
	 * Retorna a coleção de Tramites do registro de atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idRA
	 * @return Collection<Tramite>
	 * @throws ControladorException
	 */
	public Collection<Tramite> obterTramitesRA(Integer idRA) throws ControladorException;

	/**
	 * [UC0447] Consultar RA Solicitantes
	 * Retorna a coleção de registro de atendimento solicitantes
	 * 
	 * @author Rafael Pinto
	 * @date 14/08/2006
	 * @param idRA
	 * @return Collection<RegistroAtendimentoSolicitante>
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimentoSolicitante> obterRASolicitante(Integer idRA) throws ControladorException;

	/**
	 * [UC0431] Consultar Ordens de Serviço do Registro Atendimento
	 * Retorna a coleção de OS's do registro de atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idRA
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> obterOSRA(Integer idRA) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [FS0012] - Verificar possibilidade de atualização do registro de
	 * atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 14/08/2006
	 * @param idRA
	 *            ,idUsuarioLogado
	 * @throws ControladorException
	 */

	public UnidadeOrganizacional verificarPossibilidadeAtualizacaoRA(Integer idRA, Integer idUsuarioLogado) throws ControladorException;

	/**
	 * [UC0409] Obter Indicador de existência de Hidrômetro na Ligação de Água e
	 * no Poço
	 * Este caso de uso obtém o indicador de existência de Hidrômetro na ligação
	 * de Água e no poço
	 * 
	 * @author Ana Maria
	 * @date 14/08/2006
	 * @param idImovel
	 * @throws ControladorException
	 */
	public ObterIndicadorExistenciaHidrometroHelper obterIndicadorExistenciaHidrometroLigacaoAguaPoco(Integer idImovel,
					boolean considerarSituacaoLigacao) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0024] - Verificar registro de Atendimento Sem Identificação do Local
	 * de Ocorrência
	 * 
	 * @author Sávio Luiz
	 * @date 15/08/2006
	 * @param idRA
	 *            ,idUsuarioLogado
	 * @throws ControladorException
	 */

	public int verificarRASemIdentificacaoLO(Integer idImovel, Integer idRA) throws ControladorException;

	/**
	 * [UC0452] Obter Dados do Registro de Atendimento
	 * Este caso de uso permite obter dados de um registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 14/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public ObterDadosRegistroAtendimentoHelper obterDadosRegistroAtendimento(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * REGISTRO_ATENDIMENTO com IMOV_ID=nulo e RGAT_CDSITUACAO=1 e
	 * LGBR_ID=LGBR_ID do Endereço da Ocorrência e LGCP_ID=LGCP_ID do Endereço
	 * da Ocorrência e STEP_ID=Id da Especificação selecionada e STEP_ID=STEP_ID
	 * da tabela SOLICITACAO_TIPO_ESPECIFICACAO com SOTP_ID=SOTP_ID da tabela
	 * SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente a dois
	 * [SB0008] Verifica existência de Registro de Atendimento Pendente para o
	 * Local da Ocorrência
	 * 
	 * @author Raphael Rossiter
	 * @date 15/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 *            ,
	 *            idLogradouroBairro, idLogradouroCep
	 * @return RegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimento verificaExistenciaRAPendenteLocalOcorrencia(Integer idSolicitacaoTipoEspecificacao,
					Integer idLogradouroBairro, Integer idLogradouroCep) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * REGISTRO_ATENDIMENTO com IMOV_ID=nulo e RGAT_CDSITUACAO=1 e
	 * LGBR_ID=LGBR_ID do Endereço da Ocorrência e LGCP_ID=LGCP_ID do Endereço
	 * da Ocorrência e STEP_ID=Id da Especificação selecionada e STEP_ID=STEP_ID
	 * da tabela SOLICITACAO_TIPO_ESPECIFICACAO com SOTP_ID=SOTP_ID da tabela
	 * SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente a dois
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 *            ,
	 *            idLogradouroCep, idLogradouroBairro
	 * @return RegistroAtendimentoPendenteLocalOcorrenciaHelper
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimentoPendenteLocalOcorrenciaHelper pesquisarRAPendenteLocalOcorrencia(Integer idSolicitacaoTipoEspecificacao,
					Integer idLogradouroCep, Integer idLogradouroBairro) throws ControladorException;

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
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal)
					throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0017] - Verificar registro de Atendimento de falta de Água
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRA
	 * @throws ControladorException
	 */
	public VerificarRAFaltaAguaHelper verificarRegistroAtendimentoFaltaAgua(Integer idRegistroAtendimento, Date dataAtendimento,
					Integer idBairroArea, Integer idBairro, Integer idEspecificacao, Short indFaltaAgua, Integer indMatricula,
					String continua) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 11/08/2006
	 */
	public RegistroAtendimento validarRegistroAtendimento(Integer idRA) throws ControladorException;

	/**
	 * [UC0427] Tramitar Registro de Atendimento
	 * Validar Tramitação
	 * [FS0001] Verificar se o RA está cancelado ou bloqueado. [FS0002]
	 * Verificar situações das OS(ordem de Serviço) associadas ao RA [FS0003]
	 * Verificar se o tipo de Solicitação é Tarifa Social [FS0008] Validar
	 * Unidade de Destino
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param tramite
	 * @throws ControladorException
	 */
	public void validarTramitacao(Tramite tramite, Usuario usuario) throws ControladorException;

	/**
	 * [UC0427] Tramitar Registro de Atendimento
	 * Tramitar
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param tramite
	 * @param dataConcorrente
	 * @throws ControladorException
	 */
	public void tramitar(Tramite tramite, Date dataConcorrente) throws ControladorException;

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * Caso exista registro de atendimento que estão na unidade atual informada
	 * (existe Ocorrência na tabela REGISTRO_ATENDIMENTO com TRAMITE=Id da
	 * Unidade Atual e maior TRAM_TMTRAMITE)
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param unidadeOrganizacional
	 * @return Collection<RegistroAtendimento>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaRAPorUnidadeAtual(UnidadeOrganizacional unidadeOrganizacional) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0019] - Exibe Registros de Atendimentos de falta de Água no imóvel
	 * 
	 * @author Sávio Luiz
	 * @date 21/08/2006
	 * @param idRA
	 * @throws ControladorException
	 */
	public RAFaltaAguaPendenteHelper carregarObjetoRAFaltaAguaPendente(Integer idRegistroAtendimento, Integer idBairroArea,
					Integer idEspecificacao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a matrícula do imóvel da Aba Dados do Local da Ocorrência tenha sido
	 * informada e o Cliente informado não seja um cliente do imóvel (inexiste
	 * Ocorrência na tabela CLIENTE_IMOVEL com CLIE_ID=Id do cliente e
	 * IMOV_ID=matrícula do imóvel e CLIM_DTRELACAOFIM com o valor nulo).
	 * [FS0027] Verificar informação do imóvel
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 *            ,
	 *            idImovel
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */
	public Cliente verificarInformacaoImovel(Integer idCliente, Integer idImovel, boolean levantarException) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e o cliente já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado).
	 * [FS0012] Verificar existência do cliente solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idCliente
	 * @throws ErroRepositorioException
	 */
	public void verificarExistenciaClienteSolicitante(Integer idRegistroAtendimento, Integer idCliente) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Obter e habilitar/desabilitar os Dados da Identificação do Local da
	 * Ocorrência de acordo com as situações abaixo descritas no caso de uso
	 * [SB0004] Obtém e Habilita/Desabilita Dados da Identificação do Local da
	 * Ocorrência e Dados do Solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 28/07/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao, idSolicitacaoTipo,
	 *            levantarExceptionImovelInexistente
	 * @return ObterDadosIdentificacaoLocalOcorrenciaHelper
	 * @throws ControladorException
	 */
	public ObterDadosIdentificacaoLocalOcorrenciaHelper obterDadosIdentificacaoLocalOcorrenciaAtualizar(Integer idImovel,
					Integer idSolicitacaoTipoEspecificacao, Integer idSolicitacaoTipo, Integer idRegistroAtendimento,
					boolean levantarExceptionImovelInexistente, Usuario usuario) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e a unidade já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada).
	 * [FS0026] Verificar existência da unidade solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 23/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idUnidade
	 * @throws ControladorException
	 */
	public void verificarExistenciaUnidadeSolicitante(Integer idRegistroAtendimento, Integer idUnidade) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento e [UC0408] Atualizar Registro de
	 * Atendimento
	 * [FS0040] Verificar preenchimento campos. 2 ABA
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public void validarCamposObrigatoriosRA_2ABA(String idImovel, String pontoReferencia, String idMunicipio, String descricaoMunicipio,
					String cdBairro, String descricaoBairro, String idAreaBairro, String idlocalidade, String descricaoLocalidade,
					String cdSetorComercial, String descricaoSetorComercial, String numeroQuadra, String idDivisaoEsgoto, String idUnidade,
					String descricaoUnidade, String idLocalOcorrencia, String idPavimentoRua, String idPavimentoCalcada,
					String descricaoLocalOcorrencia, String imovelObrigatorio, String pavimentoRuaObrigatorio,
					String pavimentoCalcadaObrigatorio, String solicitacaoTipoRelativoFaltaAgua, String solicitacaoTipoRelativoAreaEsgoto,
					String desabilitarMunicipioBairro, String indRuaLocalOcorrencia, String indCalcadaLocalOcorrencia,
					Integer idEspecificacao, Integer idRAAtualizacao, Collection colecaoEndereco, String idCliente, String IdUnidadeDestino)
					throws ControladorException;

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Validar Pré-Encerramento
	 * [FS0001] Verificar possibilidade de encerramento do registro de
	 * atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 25/08/2006
	 * @param registroAtendimento
	 * @param usuarioLogado
	 * @param indicadorAutorizacaoManutencaoRA
	 * @throws ControladorException
	 */
	public void validarPreEncerramentoRA(RegistroAtendimento registroAtendimento, Usuario usuarioLogado,
					Short indicadorAutorizacaoManutencaoRA) throws ControladorException;

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Validar Pré-Encerramento
	 * [FS0001] Verificar possibilidade de encerramento do registro de
	 * atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2007
	 * @param registroAtendimento
	 * @param usuarioLogado
	 * @param indicadorAutorizacaoManutencaoRA
	 * @throws ControladorException
	 */
	public void validarPreEncerramentoRASemTarifaSocial(RegistroAtendimento registroAtendimento, Usuario usuarioLogado,
					Short indicadorAutorizacaoManutencaoRA) throws ControladorException;

	/**
	 * [UC0435] - Encerrar Registro de Atendimento
	 * [FS003] Validar RA de Referência
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 */
	public RegistroAtendimento validarRAReferencia(Integer idRA, Integer idRAReferencia) throws ControladorException;

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Validar Encerramento
	 * [FS0004] Verificar data do encerramento [FS0005] Verificar hora do
	 * encerramento
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 * @param registroAtendimento
	 * @throws ControladorException
	 */
	public void validarEncerramentoRA(RegistroAtendimento registroAtendimento) throws ControladorException;

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 * @param registroAtendimento
	 * @param registroAtendimentoUnidade
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void encerrarRegistroAtendimento(RegistroAtendimento registroAtendimento, RegistroAtendimentoUnidade registroAtendimentoUnidade,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [FS0030] Verificar preenchimento dos dados de Identificação do solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public void verificaDadosSolicitante(Integer idCliente, Integer idUnidadeSolicitante, Integer idFuncionario, String nomeSolicitante,
					Collection colecaoEndereco, Collection colecaoFone, Short indicadorClienteEspecificacao, Integer idImovel,
					Integer idRegistroAtendimento, Integer idEspecificacao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0027] Inclui Solicitante do Registro de Atendimento
	 * (REGISTRO_ATENDIMENTO_SOLICITANTE)
	 * 
	 * @author Raphael Rossiter
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public void inserirRegistroAtendimentoSolicitante(Integer idRegistroAtendimento, Integer idCliente, Collection colecaoEndereco,
					String pontoReferencia, String nomeSolicitante, boolean novoSolicitante, Integer idUnidadeSolicitante,
					Integer idFuncionario, Collection colecaoFone, String tipoCliente, String numeroCpf, String numeroRg,
					String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj) throws ControladorException;

	/**
	 * passa os parametros do registro atendimento solicitante e a coleção de
	 * fones do solicitante e retorna um objeto de Registro Atendimento
	 * Solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 02/09/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoSolicitante inserirDadosNoRegistroAtendimentoSolicitante(Integer idRegistroAtendimento, Integer idCliente,
					Collection colecaoEndereco, String pontoReferencia, String nomeSolicitante, Integer idUnidadeSolicitante,
					Integer idFuncionario, Collection colecaoFone, String fonePadrao, String tipoCliente, String numeroCpf,
					String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0017] - Verificar registro de Atendimento de falta de Água
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @throws ControladorException
	 */
	public VerificarRAFaltaAguaHelper verificarRegistroAtendimentoFaltaAguaInserir(Date dataAtendimento, Integer idBairroArea,
					Integer idBairro, Integer idEspecificacao, Short indFaltaAgua, Integer indMatricula, String continua)
					throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0028] Inclui Registro de Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public Integer[] inserirRegistroAtendimento(short indicadorAtendimentoOnLine, String dataAtendimento, String horaAtendimento,
					String tempoEsperaInicial, String tempoEsperaFinal, Integer idMeioSolicitacao, Integer senhaAtendimento,
					Integer idSolicitacaoTipoEspecificacao, String dataPrevista, String observacao, Integer idImovel,
					String descricaoLocalOcorrencia, Integer idSolicitacaoTipo, Collection colecaoEndereco,
					String pontoReferenciaLocalOcorrencia, Integer idBairroArea, Integer idLocalidade, Integer idSetorComercial,
					Integer idQuadra, Integer idDivisaoEsgoto, Integer idLocalOcorrencia, Integer idPavimentoRua,
					Integer idPavimentoCalcada, Integer idUnidadeAtendimento, Integer idUsuarioLogado, Integer idCliente,
					String pontoReferenciaSolicitante, String nomeSolicitante, boolean novoSolicitante, Integer idUnidadeSolicitante,
					Integer idFuncionario, Collection colecaoFone, Collection colecaoEnderecoSolicitante, Integer idUnidadeDestino,
					String parecerUnidadeDestino, Collection<Integer> colecaoIdServicoTipo, String numeroRAManual, Integer idRAJAGerado,
					BigDecimal coordenadaNorte, BigDecimal coordenadaLeste, Integer sequenceRA, Integer idRaReiterada, String tipoCliente,
					String numeroCpf, String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj,
					Collection<Conta> colecaoContas, String identificadores, ContaMotivoRevisao contaMotivoRevisao,
					String indicadorProcessoAdmJud, String numeroProcessoAgencia)
					throws ControladorException;

	/**
	 * Retorna todos os Registros de Atendimento feito por os coleitores, que
	 * ainda não foram inportado para o Gsan.
	 * 
	 * @author isilva
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarRegistroAtendimentoColetor() throws ControladorException;

	/**
	 * Gera todas os Registros de Atendimento vindo dos coletores
	 * 
	 * @author isilva
	 * @throws ControladorException
	 */
	public void gerarRAColetor() throws ControladorException;

	/**
	 * [UC0440] Consultar Programação de Manutenção
	 * Caso exista Programação de Manutenção de uma determinada Área de Bairro
	 * 
	 * @author Rômulo Aurélio
	 * @date 28/08/2006
	 * @param idMunicipio
	 * @param idBairro
	 * @param areaBairro
	 * @param mesAnoReferencia
	 * @return Collection<ManutencaoProgramacao>
	 * @throws ControladorException
	 * @throws ControladorException
	 */

	public Collection consultarProgramacaoManutencao(String idMunicipio, String idBairro, String areaBairro, String mesAnoReferencia)
					throws ControladorException;

	/**
	 * [UC0440] Consultar Programação de Abastecimento
	 * Caso exista Programação de Abastecimento de uma determinada Área de
	 * Bairro
	 * 
	 * @author Rômulo Aurélio
	 * @date 28/08/2006
	 * @param idMunicipio
	 * @param idBairro
	 * @param areaBairro
	 * @param mesAnoReferencia
	 * @return Collection<AbastecimentoProgramacao>
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Collection consultarProgramacaoAbastecimento(String idMunicipio, String idBairro, String areaBairro, String mesAnoReferencia)
					throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a Especificação esteja associada a um tipo de Serviço (SVTP_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor diferente de nulo).
	 * (AUTOMÁTICA)
	 * [SB0030] Gerar Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 29/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean gerarOrdemServicoAutomatica(Integer idSolicitacaoTipoEspecificacao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a Especificação possa gerar alguma ordem de Serviço
	 * (STEP_ICGERACAOORDEMSERVICO da tabela SOLICITACAO_TIPO_ESPECIFICACAO com
	 * o valor correspondente a um). (OPCIONAL) [SB0030] Gerar Ordem de
	 * Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 29/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean gerarOrdemServicoOpcional(Integer idSolicitacaoTipoEspecificacao) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0028] Atualizar Registro de Atendimento (REGISTRO_ATENDIMENTO)
	 * 
	 * @author Sávio Luiz
	 * @date 30/08/2006
	 * @throws ControladorException
	 */
	public Integer[] atualizarRegistroAtendimento(Integer idRA, short indicadorAtendimentoOnLine, String dataAtendimento,
					String horaAtendimento, String tempoEsperaInicial, String tempoEsperaFinal, Integer idMeioSolicitacao,
					Integer senhaAtendimento, Integer idSolicitacaoTipoEspecificacao, String dataPrevista, String observacao,
					Integer idImovel, String descricaoLocalOcorrencia, Integer idSolicitacaoTipo, Collection colecaoEndereco,
					String pontoReferenciaLocalOcorrencia, Integer idBairroArea, Integer idLocalidade, Integer idSetorComercial,
					Integer idQuadra, Integer idDivisaoEsgoto, Integer idLocalOcorrencia, Integer idPavimentoRua,
					Integer idPavimentoCalcada, Integer idUnidadeAtendimento, Usuario usuarioLogado, Integer imovelObrigatorio,
					Date ultimaAlteracao, Collection colecaoRASolicitante, Collection colecaoRASolicitanteRemovida,
					Collection<Integer> colecaoIdServicoTipo, Integer especificacaoNaBase, Collection<Conta> colecaoContas,
					String identificadores, ContaMotivoRevisao contaMotivoRevisao, String indicadorProcessoAdmJud,
					String numeroProcessoAgencia) throws ControladorException;

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [SB0001] Define Data Prevista
	 * 
	 * @author Ana Maria
	 * @date 24/08/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public Date definirDataPrevistaRA(Date dataAtendimento, Integer idSolicitacaoTipoEspecificacao) throws ControladorException;

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [SB0001],[SB0002],[SB0003] Define Unidade Destino
	 * 
	 * @author Ana Maria
	 * @date 24/08/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	// public UnidadeOrganizacional definirUnidadeDestino(
	// Integer idSolicitacaoTipoEspecificacao, Integer idLocalidade,
	// Integer idSolicitacaoTipo,
	// boolean solicitacaoTipoRelativoAreaEsgoto, Integer idDivisaoEsgoto)
	// throws ControladorException;

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [SB0006]Incluir Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 29/08/2006
	 * @throws ControladorException
	 */
	public Integer[] reativarRegistroAtendimento(RegistroAtendimento ra, Integer idUnidadeAtendimento, Integer idUsuarioLogado,
					Integer idCliente, Integer idRaSolicitante, Integer idUnidadeDestino, String parecerUnidadeDestino,
					Integer idSolicitacaoTipo) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * SOTP_ICFALTAAGUA da tabela SOLICITACAO_TIPO com o valor correspondente a
	 * um e STEP_ICMATRICULA com o valor correspondente a dois na tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO).
	 * 
	 * @author Raphael Rossiter
	 * @date 29/08/2006
	 * @return SolicitacaoTipoEspecificacao
	 * @throws ControladorException
	 */
	public SolicitacaoTipoEspecificacao pesquisarTipoEspecificacaoFaltaAguaGeneralizada() throws ControladorException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 06/09/2006
	 */
	public Tramite recuperarTramiteMaisAtualPorUnidadeDestino(Integer idUnidade) throws ControladorException;

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [FS0001]Valida possibilidade de reativação
	 * 
	 * @author Ana Maria
	 * @date 29/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public void validaPossibilidadeReativacaoRA(Integer idRegistroAtendimento, Integer idUsuario) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * pesquisa os fones do regsitro atendimento solicitante e joga na coleção
	 * de ClientesFones
	 * 
	 * @author Sávio Luiz
	 * @date 05/09/2006
	 * @return idRASolicitante
	 * @throws ControladorException
	 */
	public Collection pesquisarParmsFoneRegistroAtendimentoSolicitante(Integer idRASolicitante) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [FS0030] Verificar preenchimento dos dados de identificação do
	 * solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 07/09/2006
	 * @throws ControladorException
	 */
	public void verificaDadosSolicitanteAtualizar(Integer idCliente, Integer idUnidadeSolicitante, Integer idFuncionario,
					String nomeSolicitante, Collection colecaoEndereco, Collection colecaoFone, Short indicadorClienteEspecificacao,
					Integer idImovel, Integer idRegistroAtendimento, Integer idRASolicitante) throws ControladorException;

	/**
	 * [UC0408] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e o cliente já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado e o registro
	 * atendimento solicitante for diferente do que está sendo atualizado).
	 * [FS0027] Verificar existência do cliente solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 21/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idCliente
	 * @throws ErroRepositorioException
	 */
	public void verificarExistenciaClienteSolicitanteAtualizar(Integer idRegistroAtendimento, Integer idCliente, Integer idRASolicitante)
					throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e a unidade já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada e RASO_ID<>id
	 * do Registro atendimento solicitante).
	 * [FS0018] Verificar existência da unidade solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 07/09/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idUnidade
	 * @throws ControladorException
	 */
	public void verificarExistenciaUnidadeSolicitanteAtualizar(Integer idRegistroAtendimento, Integer idUnidade, Integer idRASolicitante)
					throws ControladorException;

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 13/09/2006
	 * @param idRa
	 * @return dataPrevisaoAtual
	 * @throws ControladorException
	 */
	public Date obterDataAgenciaReguladoraPrevisaoAtual(Integer idRa) throws ControladorException;

	/**
	 * Consultar os registros de atendimento do Imovel [UC0472] Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 25/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarRegistroAtendimentoImovel(Integer idImovel, String situacao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0025] Verifica Registro de Atendimento de Água Generalizada
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoFaltaAguaGeneralizadaHelper verificarRegistroAtendimentoFaltaAguaGeneralizafa(Integer idEspecificacao,
					Integer idBairroArea) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0015] Verifica Registro de Atendimento Encerrado para o Local da
	 * Ocorrência
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoEncerradoLocalOcorrenciaHelper verificarRegistroAtendimentoEncerradoLocalOcorrencia(Integer idImovel,
					Integer idEspecificacao, Integer idLogradouroBairro, Integer idLogradouroCep) throws ControladorException;

	/**
	 * Pesquisa os dados do Registro Atendimento Solicitante para o Relatório de
	 * OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * Pesquisa o telefone principal do Registro Atendimento Solicitante para o
	 * Relatório de OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */

	public String pesquisarSolicitanteFonePrincipal(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * Pesquisar o Endereço descritivo do RA a partir do seu id
	 * [UC0482] - Obter Endereço Abreviado da Ocorrência do RA
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idRA
	 * @return String
	 * @throws ControladorException
	 */

	public String obterEnderecoDescritivoRA(Integer idRA) throws ControladorException;

	/**
	 * [UC0482] - Obter Endereço Abreviado da Ocorrência do RA
	 * Pesquisa o Endereço abreviado da Ocorrência do RA
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */

	public String obterEnderecoAbreviadoOcorrenciaRA(Integer idRA) throws ControladorException;

	/**
	 * Caso não exista para o imóvel RA encerrada por execução com Especificação
	 * da Solicitação que permita a manuntenção de imóvel
	 * 
	 * @author Rafael Santos
	 * @since 26/10/2006
	 */
	public void verificarExistenciaRegistroAtendimento(Integer idImovel, String mensagemErro, char codigoConstante)
					throws ControladorException;

	/**
	 * Caso não exista para o imóvel RA encerrada por execução com Especificação
	 * da Solicitação No caso de Tarifa Social
	 * 
	 * @author Rafael Santos
	 * @since 26/10/2006
	 */
	public void verificarExistenciaRegistroAtendimentoTarifaSocial(Integer idImovel, String mensagemErro) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 11/08/2006
	 */
	public void verificarUnidadeUsuario(RegistroAtendimento registroAtendimento, Usuario usuario) throws ControladorException;

	/**
	 * [UC0494] Gerar Numeração de RA Manual
	 * 
	 * @author Raphael Rossiter
	 * @date 06/11/2006
	 * @throws ControladorException
	 */
	public GerarNumeracaoRAManualHelper gerarNumeracaoRAManual(Integer quantidade, Integer idUnidadeOrganizacional)
					throws ControladorException;

	/**
	 * [UC0404] - Manter Especificação da Situação do imóvel
	 * [SB0001] Atualizar Critério de Cobrança
	 * 
	 * @author Rafael Pinto
	 * @created 09/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarEspecificacaoSituacaoImovel(EspecificacaoImovelSituacao especificacaoImovelSituacao,
					Collection colecaoEspecificacaoCriterios, Collection colecaoEspecificacaoCriteriosRemovidas, Usuario usuario)
					throws ControladorException;

	/**
	 * [UC00054] Inserir Tarifa Social
	 * [FS00001] Verificar Registro Atendimento
	 * 
	 * @author Rafael Santos
	 * @date 10/11/2006
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificarRegistroAtendimentoTarifaSocial(String idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0401] Atualizar Tipo de Solicitação com Especificação
	 * [SB0001] - Atualizar Tipo Solicitação com Especificaões
	 * 
	 * @author Rômulo Aurélio
	 * @date 01/08/2006
	 * @param solicitacaoTipo
	 *            ,
	 *            colecaoSolicitacaoTipoEspecificacao, usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */

	public Integer atualizarTipoSolicitacaoEspecificacao(SolicitacaoTipo solicitacaoTipo, Collection colecaoSolicitacaoTipoEspecificacao,
					Usuario usuarioLogado, Collection colecaoSolicitacaoTipoEspecificacaoRemovidos) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0027] Inclui Solicitante do Registro de Atendimento
	 * (REGISTRO_ATENDIMENTO_SOLICITANTE)
	 * 
	 * @author Raphael Rossiter
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public boolean clienteObrigatorioInserirRegistroAtendimento(Integer idEspecificacao) throws ControladorException;

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public void tramitarConjuntoRA(Collection tramites) throws ControladorException;

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * [FS0006] Valida Data
	 * [FS0007] Valida Hora
	 * [FS0008] Valida Unidade Destino
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public void validarConjuntoTramitacao(String[] ids, Date dataHoraTramitacao, Integer idUnidadeDestino) throws ControladorException;

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * [FS0002] Verificar as situações das OS associadas ao RA
	 * [FS0003] Verificar se o tipo de Solicitação é Tarifa Social
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public void validarRATramitacao(String[] ids, Integer idUsuario) throws ControladorException;

	/**
	 * [UC00069] Manter Dados Tarifa Social
	 * [FS00001] Verificar Registro Atendimento
	 * 
	 * @author Rafael Corrêa
	 * @date 05/02/2007
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificarRegistroAtendimentoManterTarifaSocial(String idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a especificação informada para o RA tem indicativo que é para verificar débito
	 * (STEP_ICVERIFICADEBITO da tabela SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1)),
	 * o sistema deverá verificar se o imóvel informado tenha débito <<incluir>>
	 * [UC0067] Obter Débito do Imóvel ou Cliente (passando o imóvel).
	 * [FS0043] – Verifica imóvel com débito.
	 * [SB0032] – Verifica se o imóvel informado tem débito.
	 * [FS0043] – Verifica imóvel com débito
	 * 
	 * @author Raphael Rossiter
	 * @date 19/02/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public void verificarImovelComDebitos(Integer idSolicitacaoTipoEspecificacao, Integer idImovel) throws ControladorException;

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ControladorException;

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException;

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCepSolicitante(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo)
					throws ControladorException;

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairroSolicitante(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException;

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param filtroRA
	 * @return integer - tamanho
	 * @throws ControladorException
	 */
	public Integer filtrarRegistroAtendimentoTamanho(FiltrarRegistroAtendimentoHelper filtroRA) throws ControladorException;

	/**
	 * Consultar Observacao Registro Atendimento
	 * Solicitacao da CAER
	 * 
	 * @author Rafael Pinto
	 * @date 14/03/2007
	 */
	public Collection<RegistroAtendimento> pesquisarObservacaoRegistroAtendimento(Integer matriculaImovel, Date dataInicialAtendimento,
					Date dataFinalAtendimento) throws ControladorException;

	/**
	 * Método que gera o resumo dos Registro de Atendimento
	 * [UC0567] - Gerar Resumo Registro Atendimento
	 * 
	 * @author Thiago Tenório
	 * @date 30/04/2007
	 */
	public void gerarResumoRegistroAtendimento(int idLocalidade, int idFuncionalidadeIniciada, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * [UC0459] Informar Dados da Agencia Reguladora
	 * 
	 * @author Kássia Albuquerque
	 * @date 09/04/2007
	 */

	public Integer informarDadosAgenciaReguladora(RaDadosAgenciaReguladora raDadosAgenciaReguladora,
					Collection collectionRaDadosAgenciaReguladoraFone, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Procura a quantidade de dias de prazo
	 * [UC0459] Informar Dados da Agencia Reguladora
	 * 
	 * @author Kássia Albuquerque
	 * @date 19/04/2007
	 */

	public Integer procurarDiasPazo(Integer raId) throws ControladorException;

	/**
	 * Consultar os comentários do imóvel.
	 * 
	 * @author Virgínia Melo
	 * @date 01/02/2009
	 * @param idImovel
	 *            - id do imóvel;
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarImovelComentario(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa um Registro de Atendimento
	 * 
	 * @author eduardo henrique
	 * @date 20/05/2009
	 */
	public RegistroAtendimento pesquisarRegistroAtendimento(Integer idRa) throws ControladorException;

	/**
	 * Pesquisa um Registro de Atendimento Solicitante
	 * 
	 * @author Virgínia Melo
	 * @date 04/06/2009
	 */
	public RegistroAtendimentoSolicitante obterRegistroAtendimentoSolicitanteRelatorioOS(Integer idRa) throws ControladorException;

	/**
	 * obtêm a ultima sequence
	 * 
	 * @return sequence
	 * @throws ControladorException
	 */
	public Integer obterSequenceRA() throws ControladorException;

	public Collection listarMotivoAtendimentoIncompleto() throws ControladorException;

	/**
	 * Lista RA's que foram reiteradas pelo id de uma RA.
	 * 
	 * @param registroAtendimento
	 * @return colecao de RegistroAtendimento
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> listarRAsReiteradas(RegistroAtendimento registroAtendimento) throws ControladorException;

	/**
	 * Lista duas ultimas RA's que foram reiteradas pelo id de uma RA.
	 * 
	 * @param registroAtendimento
	 * @return colecao de RegistroAtendimento
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> listarDuasUltimasRAsReiteradas(RegistroAtendimento registroAtendimento)
					throws ControladorException;

	public Collection filtrarRegistroAtendimentoIncompleto(FiltrarRegistroAtendimentoIncompletoHelper ra) throws ControladorException;

	public AtendimentoIncompleto pesquisarRAIncompleta(Integer idRAi) throws ControladorException;

	public void validarInserirRegistroAtendimentoDadosGeraisIncompleto(String dataAtendimento, String horaAtendimento,
					String tempoEsperaInicial, String tempoEsperaFinal, String idUnidadeOrganizacional, String numeroRAManual,
					String especificacao, String idRaReiteracao) throws ControladorException;

	/**
	 * Permite obter o Tipo de Solicitação de um registro de atendimento
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2011
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer obterTipoSolicitacaoRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC3002] Inserir Mensagem Tipo Solicitação Especificação
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 * @param solicitacaoTipoEspecificacaoMensagem
	 *            , usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer inserirMensagemTipoSolicitacaoEspecificacao(SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC3003] Manter Mensagem Tipo Solicitação Especificacao
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 * @param solicitacaoTipoEspecificacaoMensagem
	 *            , usuarioLogado
	 * @return void
	 * @throws ControladorException
	 */
	public void atualizarMensagemTipoSolicitacaoEspecificacao(SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC3003] Manter Mensagem Tipo Solicitação Especificacao
	 * [SB0002]Excluir Mensagem Automática
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 */
	public void removerMensagemTipoSolicitacaoEspecificacao(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Verificar se existe o vínculo entre a localidade e o munícipio
	 * 
	 * @author isilva
	 * @date 23/02/2011
	 * @param idLocalidade
	 * @param idMunicipio
	 * @return
	 * @throws ControladorException
	 */
	public boolean existeVinculoLocalidadeMunicipio(Integer idLocalidade, Integer idMunicipio) throws ControladorException;

	/**
	 * Verifica os Dados Solicitantes de Rg, Cpf e Cnpj
	 * 
	 * @author Péricles Tavares
	 * @created 17/03/2011
	 * @param tipoCliente
	 * @param solicitacaoTipoEspecificacaoId
	 * @param numeroCpf
	 * @param numeroRg
	 * @param orgaoExpedidorRg
	 * @param unidadeFederacaoRG
	 * @param numeroCnpj
	 * @throws ControladorException
	 */
	public void verificarDadosSolicitanteRgCpfCnpj(String tipoCliente, String solicitacaoTipoEspecificacaoId, String numeroCpf,
					String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj) throws ControladorException;

	public void verificarDadosSolicitanteRgCpfCnpj(String tipoCliente, String solicitacaoTipoEspecificacaoId, String numeroCpf,
					String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj, String idCliente)
					throws ControladorException;

	/**
	 * [UC0XXX] Relatório Resumo de Registro de Atendimento
	 * Obter dados do Relatório Resumo de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @date 15/03/2011
	 */
	public Collection pesquisaRelatorioResumoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoAgrupamento) throws ControladorException;

	/**
	 * [UC0XXX] Relatório Estatistica Atendimento por Atendente
	 * Obter dados do Relatório Estatistica Atendimento por Atendente pelos parametros informados no
	 * filtro
	 * [0] - idUnidade;
	 * [1] - descricaoUnidade;
	 * [2] - idUsuario;
	 * [3] - nomeUsuario;
	 * [4] - dataRegistroAtendimento;
	 * [5] - ultimaAlteracaoUnidade
	 * 
	 * @author isilva
	 * @date 24/03/2011
	 */
	public Collection pesquisaRelatorioEstatisticaAtendimentoPorAtendente(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ControladorException;

	/**
	 * [UC0XXX] Relatório Gestão de Registro de Atendimento
	 * Obter dados do Relatório de Gestão de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @param tipoRelatorio
	 *            (0 == Sintético e 1 == Analítico),
	 *            FiltrarRegistroAtendimentoHelper filtroRA
	 * @date 28/03/2011
	 */
	public Collection pesquisaRelatorioGestaoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoRelatorio) throws ControladorException;

	/**
	 * [UCXXXX] Inserir Trâmite por Especificação
	 * 
	 * @author Ailton Sousa
	 * @date 04/04/2011
	 * @param colecaoTramiteEspecificacao
	 *            , usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer inserirTramiteEspecificacao(Collection colecaoTramiteEspecificacao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Remover Trâmite Especificação
	 * 
	 * @author Hebert Falcão
	 * @date 01/04/2011
	 */
	public void removerTramiteEspecificacao(String[] ids) throws ControladorException;

	/**
	 * Atualizar Trâmite Especificação
	 * 
	 * @author Hebert Falcão
	 * @date 01/04/2011
	 */
	public void atualizarTramiteEspecificacao(EspecificacaoTramite especificacaoTramite) throws ControladorException;

	/**
	 * Pesquisar Unidades de Destino por EspecificacaoTramite
	 * 
	 * @author isilva
	 * @date 13/04/2011
	 * @param especificacaoTramite
	 * @return
	 * @throws ControladorException
	 */
	public Collection<UnidadeOrganizacional> obterUnidadeDestinoPorEspecificacao(EspecificacaoTramite especificacaoTramite)
					throws ControladorException;

	/**
	 * @author isilva
	 * @date 04/05/2011
	 * @param idsRegistrosRemocao
	 * @throws ControladorException
	 */
	public void removerSolicitacaoTipoEDependencias(String[] idsRegistrosRemocao) throws ControladorException;

	/**
	 * Verifica se o imovel e/ou o cliente possuem débitos
	 * 
	 * @author Isaac Silva
	 * @date 20/06/2011
	 * @param idSolicitacaoTipoEspecificacao
	 * @param idImovel
	 * @throws ControladorException
	 */
	public void verificarDebitosImovelCliente(Integer idSolicitacaoTipoEspecificacao, Integer idImovel, Integer idCliente)
					throws ControladorException;

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento e Ordem de servico
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2012
	 */

	public void tramitarRAOS(Tramite tramite, Date dataConcorrente, Usuario usuario) throws ControladorException;

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento e Ordem de servico
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2012
	 */

	public void tramitarColecaoRAOS(Collection tramites, Usuario usuario) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0030] Gerar Ordem de Serviço
	 * 
	 * @author Hebert Falcão
	 * @date 15/12/2012
	 */
	public Collection<OrdemServico> gerarColecaoOrdemServicoAutomatica(Collection<Integer> idsServicoTipo, Integer idSolicitacaoTipo)
					throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Verifica se o meio da solicitação permite liberação do preenchimento do campo de documento da
	 * aba solicitante do caso de uso
	 * 
	 * @author Hugo Lima
	 * @date 24/04/2012
	 * @param idMeioSolicitacao
	 * @return
	 * @throws ControladorException
	 */
	public boolean isMeioSolicitacaoLiberaDocumentoIdentificacaoRA(Integer idMeioSolicitacao) throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 27/06/2012
	 * @param Integer
	 *            idRA
	 * @return Object[] idLocalidade, idSetorComercial, idBairro
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosLocalizacaoRegistroAtendimento(Integer idRA) throws ControladorException;

	public Integer inserirRegistroAtendimento(Integer matricula, String cpfcnpj, String pontoReferencia, String nomeSolicitante,
					String foneSolicitante, String emailSolicitante, Integer idSolicitacaoTipoEspecificacao, Integer idMunicio,
					Integer idBairro, Integer idLogradouro, Integer numero, String descricao, Integer pavimentoTipo)
					throws ControladorException, NegocioException;

	/**
	 * @param cliente
	 * @param idSolicitacaoTipoEspecificacao
	 * @param usuarioLogado
	 * @param telefonesCliente
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirRegistroAtendimentoContaBraille(Cliente cliente, Integer idSolicitacaoTipoEspecificacao, Usuario usuarioLogado,
					Collection<ClienteFone> telefonesCliente) throws ControladorException;

	/**
	 * Cosulta o total de RA que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * @return
	 */
	public int consultarQuantidadeRA(RelatorioEstatisticoRegistroAtendimentoHelper registroAtendimentoHelper) throws ControladorException,
					NegocioException;

	/**
	 * Valida o {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * 
	 * @param registroAtendimentoHelper
	 * @throws NegocioException
	 */
	public void validarCamposObrigatoriosHalperRA(RelatorioEstatisticoRegistroAtendimentoHelper registroAtendimentoHelper)
					throws ControladorException, NegocioException;

	/**
	 * Consulta os dados estatisticos de RA filtrados pelo
	 * {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * 
	 * @param relatorioEstatisticoRegistroAtendimentoHelper
	 * @return {@link List} de {@link RelatorioEstatisticoRegistroAtendimentoBean}
	 */
	public List<RelatorioEstatisticoRegistroAtendimentoBean> consultarDadosEstatisticosRegistroAtendimento(
					RelatorioEstatisticoRegistroAtendimentoHelper relatorioEstatisticoRegistroAtendimentoHelper)
					throws ControladorException, NegocioException;

	/**
	 * Metodo que consulta o detalhe do registro de atendimento.
	 * 
	 * @param numeroRA
	 * @return
	 */
	public RegistroAtendimentoJSONHelper buscarDetalheRAWebService(Integer numeroRA) throws ControladorException, NegocioException;

	/**
	 * [UC0145] Inserir Conta
	 * [SB0008] - Verificar Exigência RA de Inclusão de Conta Dados Cadastrais Divergentes do Imóvel
	 * 
	 * @author Ado Rocha
	 * @date 31/10/2013
	 * @param idImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificaExistenciaRAInclusaoContaDadosCadastraisDivergentesImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro Atendimento
	 * [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo religação
	 * ou restabelecimento
	 * 
	 * @author Ítalo Almeida
	 * @date 19/11/2013
	 * @param idImovel
	 */
	public void verificarExistenciaServicoReligacaoRestabelecimento(Integer idImovel, Integer idSolicitacaoTipo)
					throws ControladorException;

	/**
	 * @param dadosAcquaGis
	 * @throws ControladorException
	 */
	public void atualizarCoordenadasGis(DadosAcquaGis dadosAcquaGis) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0041 - Verificar Titularidade do Débito]
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0013] - Verificar Titularidade do Débito
	 * 
	 * @author Anderson Italo
	 * @created 16/01/2014
	 */
	public void verificarTitularidadeDebito(Integer idImovel, Short indicadorConsiderarApenasDebitoTitularAtual,
					ClienteRelacaoTipo clienteRelacaoTipo, ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper)
					throws ControladorException;

	/**
	 * Valida o {@link RelatorioRAComProcessoAdmJudHelper}
	 * 
	 * @param registroAtendimentoHelper
	 * @throws NegocioException
	 */
	public void validarCamposObrigatoriosHelperRA(RelatorioRAComProcessoAdmJudHelper registroAtendimentoHelper)
					throws ControladorException, NegocioException;

	/**
	 * Cosulta o total de RA que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioRAComProcessoAdmJudHelper}
	 * @return
	 */
	public int consultarQuantidadeRAComProcessoAdmJud(RelatorioRAComProcessoAdmJudHelper registroAtendimentoHelper)
					throws ControladorException, NegocioException;

	/**
	 * Consulta os dados RA com Processo Adm Judfiltrados pelo
	 * {@link RelatorioRAComProcessoAdmJudHelper}
	 * 
	 * @param relatorioRAComProcessoAdmJudHelper
	 * @return {@link List} de {@link RelatorioRAComProcessoAdmJudBean}
	 */
	public List<RelatorioRAComProcessoAdmJudBean> consultarDadosRAComProcessoAdmJud(
					RelatorioRAComProcessoAdmJudHelper relatorioRAComProcessoAdmJudHelper) throws ControladorException, NegocioException;

	public List<RelatorioEstatisticoAtendimentoPorRacaCorBean> pesquisarDadosRelatorioEstatisticoAtendimentoPorRacaCor(
					GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm form)
					throws ControladorException;

}