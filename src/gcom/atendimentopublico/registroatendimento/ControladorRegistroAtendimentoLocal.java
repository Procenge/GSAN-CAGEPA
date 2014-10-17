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
	 * Data Prevista = Data v�lida obtida a partir da Data do Atendimento +
	 * n�mero de dias previstos para a Especifica��o do tipo de Solicita��o
	 * (STEP_NNDIAPRAZO da tabela SOLICITACAO_TIPO_ESPECIFICACAO).
	 * Caso o sistema deva sugerir a unidade destino para o primeiro
	 * encaminhamento do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na
	 * tabela SISTEMA_PARAMETROS)
	 * Caso a Especifica��o esteja associada a uma unidade (UNID_ID da tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO com o valor diferente de nulo), definir a
	 * unidade destino a partir da Especifica��o (UNID_ID e UNID_DSUNIDADE da
	 * tabela UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com SETP_ID=Id da Especifica��o
	 * selecionada).
	 * [SB0003] Define Data Prevista e Unidade Destino da Especifica��o
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
	 * Caso a Especifica��o exija a matr�cula do im�vel (STEP_ICMATRICULA com o
	 * valor correspondente a um na tabela SOLICITACAO_TIPO_ESPECIFICACAO),
	 * obrigat�rio; caso contr�rio, opcional
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
	 * Obter e habilitar/desabilitar os Dados da Identifica��o do Local da
	 * Ocorr�ncia de acordo com as situa��es abaixo descritas no caso de uso
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
	 * Caso exista registro de atendimento pendente para o im�vel (existe
	 * Ocorr�ncia na tabela REGISTRO_ATENDIMENTO com IMOV_ID=matr�cula do im�vel
	 * e RGAT_CDSITUACAO=1)
	 * [SB0021] Verifica exist�ncia de Registro de Atendimento Pendente para o
	 * im�vel
	 * 
	 * @author Raphael Rossiter
	 * @date 31/07/2006
	 * @param idImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificaExistenciaRAPendenteImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0399] Inserir Tipo de Solicita��o com Especifica��o
	 * [SB0001] - Gerar Tipo Solicita��o com Especifica��es
	 * 
	 * @author S�vio Luiz
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
	 * [SB0002] Habilita/Desabilita Munic�pio, Bairro, �rea do Bairro e
	 * Divis�o de Esgoto
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
	 * acordo com as regras abaixo. Caso a Especifica��o n�o esteja associada a
	 * uma unidade (UNID_ID da tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor
	 * nulo): Caso o Tipo de Solicita��o n�o seja relativo � �rea de esgoto
	 * (SOTG_ICESGOTO da tabela SOLICITACAO_TIPO_GRUPO com o valor
	 * correspondente a dois para SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com
	 * SOTP_ID=Id do Tipo de Solicita��o selecionado), definir a unidade destino
	 * a partir da localidade informada/selecionada (UNID_ID e UNID_DSUNIDADE da
	 * tabela UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da
	 * tabela LOCALIDADE_SOLIC_TIPO_GRUPO com LOCA_ID=Id da Localidade e
	 * SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com SOTP_ID=Id do Tipo de
	 * Solicita��o selecionado) [FS0018 Verificar exist�ncia de unidade
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
	 * Caso o Tipo de Solicita��o seja relativo � �rea de esgoto (SOTG_ICESGOTO
	 * da tabela SOLICITACAO_TIPO_GRUPO com o valor correspondente a um para
	 * SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com SOTP_ID=Id do Tipo de
	 * Solicita��o selecionado). Caso a quadra esteja preenchida, obter a
	 * divis�o de esgoto da quadra (DVES_ID e DVES_DSDIVISAOESGOTO da tabela
	 * DIVISAO_ESGOTO com DVES_ID=DVES_ID da tabela SISTEMA_ESGOTO com
	 * SESG_ID=SESG_ID da tabela BACIA com BACI_ID=BACI_ID da tabela QUADRA com
	 * QDRA_ID=Id da quadra informada/selecionada).
	 * [SB0006] Obt�m divis�o de Esgoto
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
	 * Caso informe a divis�o de esgoto: Caso tenha informado a quadra e a mesma
	 * n�o perten�aa a divis�o de esgoto informada (Id da divis�o de esgoto �
	 * diferente de DVES_ID da tabela QUADRA com QDRA_ID=Id da quadra
	 * informada).
	 * Caso tenha informado o setor comercial sem a quadra e o setor comercial
	 * n�o perten�aa � divis�o de esgoto informada (Id da divis�o de esgoto �
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=Id do setor
	 * comercial informado).
	 * Caso tenha informado a localidade sem o setor comercial e a localidade
	 * n�o perten�a � divis�o de esgoto informada (Id da divis�o de esgoto �
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=STCM_ID da
	 * tabela SETOR_COMERCIAL com LOCA_ID=Id da localidade informada).
	 * [FS0013] Verificar compatibilidade entre divis�o de esgoto e
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
	 * Caso a Especifica��o n�o esteja associada a uma unidade (UNID_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor nulo):
	 * Caso o Tipo de Solicita��o n�o seja relativo �rea de esgoto
	 * (SOTG_ICESGOTO da tabela SOLICITACAO_TIPO_GRUPO com o valor
	 * correspondente a um para SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com
	 * SOTP_ID=Id do Tipo de Solicita��o selecionado).
	 * Definir a unidade destino a partir da divis�o de esgoto
	 * informada/selecionada (UNID_ID e UNID_DSUNIDADE da tabela
	 * UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da tabela
	 * DIVISAO_ESGOTO com DVES_ID=Id da divis�o selecionada) [FS0018 Verificar
	 * exist�ncia de unidade centralizadora].
	 * [SB0007] Define Unidade Destino da divis�o de Esgoto
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
	 * Caso a unidade destino informada n�o possa receber registros de
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
	 * [UC0399] Inserir Tipo de Solicita��o com Especifica��es
	 * Verifica se o Servi�o tipo tem como Servi�o automatico gera��o
	 * autom�tica.
	 * [SF0003] Validar Tipo de Servi�o
	 * 
	 * @author S�vio Luiz
	 * @date 08/08/2006
	 * @param idServicoTipo
	 * @throws ErroRepositorioException
	 */
	public void verificarServicoTipoReferencia(Integer idServicoTipo) throws ControladorException;

	/**
	 * [UC0399] Inserir Tipo de Solicita��o com Especifica��es
	 * Verifica se na cole��o existe algum ordem de execu��o .
	 * [SF0004] Validar valor ordem execu��o parte
	 * 
	 * @author S�vio Luiz
	 * @date 08/08/2006
	 * @param colecaoEspecificacaoServicoTipo
	 *            ,ordemExecucao
	 * @throws ErroRepositorioException
	 */
	public void verificarExistenciaOrdemExecucao(Collection colecaoEspecificacaoServicoTipo, Short ordemExecucao)
					throws ControladorException;

	/**
	 * [UC0399] Inserir Tipo de Solicita��o com Especifica��es
	 * Verifica se na cole��o existe algum ordem de execu��o fora da
	 * ordem(1,2,3,4,5,6).Ex.:n�o exista numero 2.
	 * [SF0004] Validar valor ordem execu��o 2 parte
	 * 
	 * @author S�vio Luiz
	 * @date 08/08/2006
	 * @param colecaoEspecificacaoServicoTipo
	 * @throws ErroRepositorioException
	 */
	public void verificarOrdemExecucaoForaOrdem(Collection colecaoEspecificacaoServicoTipo) throws ControladorException;

	/**
	 * [UC0420] Obter Descri��o da Situa��o do RA
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
	 * Caso exista registro de atendimento pendente para o im�vel com a mesma
	 * Especifica��o (existe Ocorr�ncia na tabela REGISTRO_ATENDIMENTO com
	 * IMOV_ID=matr�cula do im�vel e STEP_ID=Id da Especifica��o selecionada e
	 * RGAT_CDSITUACAO=1).
	 * [FS0020] - Verificar exist�ncia de registro de atendimento para o im�vel
	 * com a mesma Especifica��o
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
	 * [UC0419] Obter Indicador de Autoriza��o para Manuten��o do RA
	 * Este caso de uso obt�m o indicador de Autoriza��o para Manuten��o do RA,
	 * ou seja, se o usu�rio tem Autoriza��o para efetuar a Manuten��o do RA
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idUnidadeOrganizacional
	 * @param idUusuario
	 * @throws ControladorException
	 */
	public Short obterIndicadorAutorizacaoManutencaoRA(Integer idUnidadeOrganizacional, Integer idUsuario) throws ControladorException;

	/**
	 * [UC0420] Obter Descri��o da Situa��o da RA
	 * Este caso de uso permite obter a Descri��o de um registro de atendimento
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
	 * [UC0422] Obter Endere�o da Ocorr�ncia do RA
	 * Este caso de uso permite obter o Endere�o da Ocorr�ncia de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String obterEnderecoOcorrenciaRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0422] Obter endere�o da ocorr�ncia do RA Este caso de uso permite obter o endere�o da
	 * ocorr�ncia de um registro de
	 * atendimento
	 * 
	 * @author Andr� Lopes
	 * @date 17/06/2013
	 * @param idRegistroAtendimento
	 * @return [0] Endere�o Formatado (Caso poss�vel sem o n�mero do im�vel)
	 *         [1] N�mero do im�vel.
	 * @throws ControladorException
	 */
	public String[] obterEnderecoeNumeroOcorrenciaRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0423] Obter Endere�o do Solicitante do RA
	 * Este caso de uso permite obter o Endere�o do solicitante de um registro
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
	 * [SB0020] Verifica Situa��o do im�vel e Especifica��o
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
	 * Verificar existencia ordem de Servi�o para o registro atendimento
	 * pesquisado
	 * 
	 * @author S�vio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer verificarOrdemServicoParaRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Pesquisar os parametros para atualizar o registro atendimento escolhido
	 * pelo usu�rio
	 * 
	 * @author S�vio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsRegistroAtendimento(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0446] Consultar Tr�mites
	 * Retorna a cole��o de Tramites do registro de atendimento
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
	 * Retorna a cole��o de registro de atendimento solicitantes
	 * 
	 * @author Rafael Pinto
	 * @date 14/08/2006
	 * @param idRA
	 * @return Collection<RegistroAtendimentoSolicitante>
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimentoSolicitante> obterRASolicitante(Integer idRA) throws ControladorException;

	/**
	 * [UC0431] Consultar Ordens de Servi�o do Registro Atendimento
	 * Retorna a cole��o de OS's do registro de atendimento
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
	 * [FS0012] - Verificar possibilidade de atualiza��o do registro de
	 * atendimento
	 * 
	 * @author S�vio Luiz
	 * @date 14/08/2006
	 * @param idRA
	 *            ,idUsuarioLogado
	 * @throws ControladorException
	 */

	public UnidadeOrganizacional verificarPossibilidadeAtualizacaoRA(Integer idRA, Integer idUsuarioLogado) throws ControladorException;

	/**
	 * [UC0409] Obter Indicador de exist�ncia de Hidr�metro na Liga��o de �gua e
	 * no Po�o
	 * Este caso de uso obt�m o indicador de exist�ncia de Hidr�metro na liga��o
	 * de �gua e no po�o
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
	 * [SB0024] - Verificar registro de Atendimento Sem Identifica��o do Local
	 * de Ocorr�ncia
	 * 
	 * @author S�vio Luiz
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
	 * LGBR_ID=LGBR_ID do Endere�o da Ocorr�ncia e LGCP_ID=LGCP_ID do Endere�o
	 * da Ocorr�ncia e STEP_ID=Id da Especifica��o selecionada e STEP_ID=STEP_ID
	 * da tabela SOLICITACAO_TIPO_ESPECIFICACAO com SOTP_ID=SOTP_ID da tabela
	 * SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente a dois
	 * [SB0008] Verifica exist�ncia de Registro de Atendimento Pendente para o
	 * Local da Ocorr�ncia
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
	 * LGBR_ID=LGBR_ID do Endere�o da Ocorr�ncia e LGCP_ID=LGCP_ID do Endere�o
	 * da Ocorr�ncia e STEP_ID=Id da Especifica��o selecionada e STEP_ID=STEP_ID
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
	 * [UC0413] Pesquisar Tipo de Servi�o
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
	 * [SB0017] - Verificar registro de Atendimento de falta de �gua
	 * 
	 * @author S�vio Luiz
	 * @date 16/08/2006
	 * @param idRA
	 * @throws ControladorException
	 */
	public VerificarRAFaltaAguaHelper verificarRegistroAtendimentoFaltaAgua(Integer idRegistroAtendimento, Date dataAtendimento,
					Integer idBairroArea, Integer idBairro, Integer idEspecificacao, Short indFaltaAgua, Integer indMatricula,
					String continua) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 11/08/2006
	 */
	public RegistroAtendimento validarRegistroAtendimento(Integer idRA) throws ControladorException;

	/**
	 * [UC0427] Tramitar Registro de Atendimento
	 * Validar Tramita��o
	 * [FS0001] Verificar se o RA est� cancelado ou bloqueado. [FS0002]
	 * Verificar situa��es das OS(ordem de Servi�o) associadas ao RA [FS0003]
	 * Verificar se o tipo de Solicita��o � Tarifa Social [FS0008] Validar
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
	 * Caso exista registro de atendimento que est�o na unidade atual informada
	 * (existe Ocorr�ncia na tabela REGISTRO_ATENDIMENTO com TRAMITE=Id da
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
	 * [SB0019] - Exibe Registros de Atendimentos de falta de �gua no im�vel
	 * 
	 * @author S�vio Luiz
	 * @date 21/08/2006
	 * @param idRA
	 * @throws ControladorException
	 */
	public RAFaltaAguaPendenteHelper carregarObjetoRAFaltaAguaPendente(Integer idRegistroAtendimento, Integer idBairroArea,
					Integer idEspecificacao) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a matr�cula do im�vel da Aba Dados do Local da Ocorr�ncia tenha sido
	 * informada e o Cliente informado n�o seja um cliente do im�vel (inexiste
	 * Ocorr�ncia na tabela CLIENTE_IMOVEL com CLIE_ID=Id do cliente e
	 * IMOV_ID=matr�cula do im�vel e CLIM_DTRELACAOFIM com o valor nulo).
	 * [FS0027] Verificar informa��o do im�vel
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
	 * Caso esteja adicionando um novo solicitante e o cliente j� seja um
	 * solicitante do registro de atendimento (existe Ocorr�ncia na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado).
	 * [FS0012] Verificar exist�ncia do cliente solicitante
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
	 * Obter e habilitar/desabilitar os Dados da Identifica��o do Local da
	 * Ocorr�ncia de acordo com as situa��es abaixo descritas no caso de uso
	 * [SB0004] Obt�m e Habilita/Desabilita Dados da Identifica��o do Local da
	 * Ocorr�ncia e Dados do Solicitante
	 * 
	 * @author S�vio Luiz
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
	 * Caso esteja adicionando um novo solicitante e a unidade j� seja um
	 * solicitante do registro de atendimento (existe Ocorr�ncia na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada).
	 * [FS0026] Verificar exist�ncia da unidade solicitante
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
	 * @author S�vio Luiz
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
	 * Validar Pr�-Encerramento
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
	 * Validar Pr�-Encerramento
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
	 * [FS003] Validar RA de Refer�ncia
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
	 * [FS0030] Verificar preenchimento dos dados de Identifica��o do solicitante
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
	 * passa os parametros do registro atendimento solicitante e a cole��o de
	 * fones do solicitante e retorna um objeto de Registro Atendimento
	 * Solicitante
	 * 
	 * @author S�vio Luiz
	 * @date 02/09/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoSolicitante inserirDadosNoRegistroAtendimentoSolicitante(Integer idRegistroAtendimento, Integer idCliente,
					Collection colecaoEndereco, String pontoReferencia, String nomeSolicitante, Integer idUnidadeSolicitante,
					Integer idFuncionario, Collection colecaoFone, String fonePadrao, String tipoCliente, String numeroCpf,
					String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0017] - Verificar registro de Atendimento de falta de �gua
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
	 * ainda n�o foram inportado para o Gsan.
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
	 * [UC0440] Consultar Programa��o de Manuten��o
	 * Caso exista Programa��o de Manuten��o de uma determinada �rea de Bairro
	 * 
	 * @author R�mulo Aur�lio
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
	 * [UC0440] Consultar Programa��o de Abastecimento
	 * Caso exista Programa��o de Abastecimento de uma determinada �rea de
	 * Bairro
	 * 
	 * @author R�mulo Aur�lio
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
	 * Caso a Especifica��o esteja associada a um tipo de Servi�o (SVTP_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor diferente de nulo).
	 * (AUTOM�TICA)
	 * [SB0030] Gerar Ordem de Servi�o
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
	 * Caso a Especifica��o possa gerar alguma ordem de Servi�o
	 * (STEP_ICGERACAOORDEMSERVICO da tabela SOLICITACAO_TIPO_ESPECIFICACAO com
	 * o valor correspondente a um). (OPCIONAL) [SB0030] Gerar Ordem de
	 * Servi�o
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
	 * @author S�vio Luiz
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
	 * [UC0456] Elaborar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 06/09/2006
	 */
	public Tramite recuperarTramiteMaisAtualPorUnidadeDestino(Integer idUnidade) throws ControladorException;

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [FS0001]Valida possibilidade de reativa��o
	 * 
	 * @author Ana Maria
	 * @date 29/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public void validaPossibilidadeReativacaoRA(Integer idRegistroAtendimento, Integer idUsuario) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * pesquisa os fones do regsitro atendimento solicitante e joga na cole��o
	 * de ClientesFones
	 * 
	 * @author S�vio Luiz
	 * @date 05/09/2006
	 * @return idRASolicitante
	 * @throws ControladorException
	 */
	public Collection pesquisarParmsFoneRegistroAtendimentoSolicitante(Integer idRASolicitante) throws ControladorException;

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [FS0030] Verificar preenchimento dos dados de identifica��o do
	 * solicitante
	 * 
	 * @author S�vio Luiz
	 * @date 07/09/2006
	 * @throws ControladorException
	 */
	public void verificaDadosSolicitanteAtualizar(Integer idCliente, Integer idUnidadeSolicitante, Integer idFuncionario,
					String nomeSolicitante, Collection colecaoEndereco, Collection colecaoFone, Short indicadorClienteEspecificacao,
					Integer idImovel, Integer idRegistroAtendimento, Integer idRASolicitante) throws ControladorException;

	/**
	 * [UC0408] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e o cliente j� seja um
	 * solicitante do registro de atendimento (existe Ocorr�ncia na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado e o registro
	 * atendimento solicitante for diferente do que est� sendo atualizado).
	 * [FS0027] Verificar exist�ncia do cliente solicitante
	 * 
	 * @author S�vio Luiz
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
	 * Caso esteja adicionando um novo solicitante e a unidade j� seja um
	 * solicitante do registro de atendimento (existe Ocorr�ncia na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada e RASO_ID<>id
	 * do Registro atendimento solicitante).
	 * [FS0018] Verificar exist�ncia da unidade solicitante
	 * 
	 * @author S�vio Luiz
	 * @date 07/09/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idUnidade
	 * @throws ControladorException
	 */
	public void verificarExistenciaUnidadeSolicitanteAtualizar(Integer idRegistroAtendimento, Integer idUnidade, Integer idRASolicitante)
					throws ControladorException;

	/**
	 * [UC0456] Elaborar Roteiro de Programa��o de Ordens de Servi�o
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
	 * [SB0025] Verifica Registro de Atendimento de �gua Generalizada
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
	 * Ocorr�ncia
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoEncerradoLocalOcorrenciaHelper verificarRegistroAtendimentoEncerradoLocalOcorrencia(Integer idImovel,
					Integer idEspecificacao, Integer idLogradouroBairro, Integer idLogradouroCep) throws ControladorException;

	/**
	 * Pesquisa os dados do Registro Atendimento Solicitante para o Relat�rio de
	 * OS
	 * 
	 * @author Rafael Corr�a
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * Pesquisa o telefone principal do Registro Atendimento Solicitante para o
	 * Relat�rio de OS
	 * 
	 * @author Rafael Corr�a
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */

	public String pesquisarSolicitanteFonePrincipal(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * Pesquisar o Endere�o descritivo do RA a partir do seu id
	 * [UC0482] - Obter Endere�o Abreviado da Ocorr�ncia do RA
	 * 
	 * @author Rafael Corr�a
	 * @date 18/10/2006
	 * @param idRA
	 * @return String
	 * @throws ControladorException
	 */

	public String obterEnderecoDescritivoRA(Integer idRA) throws ControladorException;

	/**
	 * [UC0482] - Obter Endere�o Abreviado da Ocorr�ncia do RA
	 * Pesquisa o Endere�o abreviado da Ocorr�ncia do RA
	 * 
	 * @author Rafael Corr�a
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */

	public String obterEnderecoAbreviadoOcorrenciaRA(Integer idRA) throws ControladorException;

	/**
	 * Caso n�o exista para o im�vel RA encerrada por execu��o com Especifica��o
	 * da Solicita��o que permita a manunten��o de im�vel
	 * 
	 * @author Rafael Santos
	 * @since 26/10/2006
	 */
	public void verificarExistenciaRegistroAtendimento(Integer idImovel, String mensagemErro, char codigoConstante)
					throws ControladorException;

	/**
	 * Caso n�o exista para o im�vel RA encerrada por execu��o com Especifica��o
	 * da Solicita��o No caso de Tarifa Social
	 * 
	 * @author Rafael Santos
	 * @since 26/10/2006
	 */
	public void verificarExistenciaRegistroAtendimentoTarifaSocial(Integer idImovel, String mensagemErro) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 11/08/2006
	 */
	public void verificarUnidadeUsuario(RegistroAtendimento registroAtendimento, Usuario usuario) throws ControladorException;

	/**
	 * [UC0494] Gerar Numera��o de RA Manual
	 * 
	 * @author Raphael Rossiter
	 * @date 06/11/2006
	 * @throws ControladorException
	 */
	public GerarNumeracaoRAManualHelper gerarNumeracaoRAManual(Integer quantidade, Integer idUnidadeOrganizacional)
					throws ControladorException;

	/**
	 * [UC0404] - Manter Especifica��o da Situa��o do im�vel
	 * [SB0001] Atualizar Crit�rio de Cobran�a
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
	 * [UC0401] Atualizar Tipo de Solicita��o com Especifica��o
	 * [SB0001] - Atualizar Tipo Solicita��o com Especifica�es
	 * 
	 * @author R�mulo Aur�lio
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
	 * [FS0002] Verificar as situa��es das OS associadas ao RA
	 * [FS0003] Verificar se o tipo de Solicita��o � Tarifa Social
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public void validarRATramitacao(String[] ids, Integer idUsuario) throws ControladorException;

	/**
	 * [UC00069] Manter Dados Tarifa Social
	 * [FS00001] Verificar Registro Atendimento
	 * 
	 * @author Rafael Corr�a
	 * @date 05/02/2007
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificarRegistroAtendimentoManterTarifaSocial(String idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a especifica��o informada para o RA tem indicativo que � para verificar d�bito
	 * (STEP_ICVERIFICADEBITO da tabela SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1)),
	 * o sistema dever� verificar se o im�vel informado tenha d�bito <<incluir>>
	 * [UC0067] Obter D�bito do Im�vel ou Cliente (passando o im�vel).
	 * [FS0043] � Verifica im�vel com d�bito.
	 * [SB0032] � Verifica se o im�vel informado tem d�bito.
	 * [FS0043] � Verifica im�vel com d�bito
	 * 
	 * @author Raphael Rossiter
	 * @date 19/02/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public void verificarImovelComDebitos(Integer idSolicitacaoTipoEspecificacao, Integer idImovel) throws ControladorException;

	/**
	 * Atualiza logradouroCep de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ControladorException;

	/**
	 * Atualiza logradouroBairro de um ou mais im�veis
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
	 * Atualiza logradouroCep de um ou mais im�veis
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
	 * Atualiza logradouroBairro de um ou mais im�veis
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
	 * M�todo que gera o resumo dos Registro de Atendimento
	 * [UC0567] - Gerar Resumo Registro Atendimento
	 * 
	 * @author Thiago Ten�rio
	 * @date 30/04/2007
	 */
	public void gerarResumoRegistroAtendimento(int idLocalidade, int idFuncionalidadeIniciada, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * [UC0459] Informar Dados da Agencia Reguladora
	 * 
	 * @author K�ssia Albuquerque
	 * @date 09/04/2007
	 */

	public Integer informarDadosAgenciaReguladora(RaDadosAgenciaReguladora raDadosAgenciaReguladora,
					Collection collectionRaDadosAgenciaReguladoraFone, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Procura a quantidade de dias de prazo
	 * [UC0459] Informar Dados da Agencia Reguladora
	 * 
	 * @author K�ssia Albuquerque
	 * @date 19/04/2007
	 */

	public Integer procurarDiasPazo(Integer raId) throws ControladorException;

	/**
	 * Consultar os coment�rios do im�vel.
	 * 
	 * @author Virg�nia Melo
	 * @date 01/02/2009
	 * @param idImovel
	 *            - id do im�vel;
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
	 * @author Virg�nia Melo
	 * @date 04/06/2009
	 */
	public RegistroAtendimentoSolicitante obterRegistroAtendimentoSolicitanteRelatorioOS(Integer idRa) throws ControladorException;

	/**
	 * obt�m a ultima sequence
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
	 * Permite obter o Tipo de Solicita��o de um registro de atendimento
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2011
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer obterTipoSolicitacaoRA(Integer idRegistroAtendimento) throws ControladorException;

	/**
	 * [UC3002] Inserir Mensagem Tipo Solicita��o Especifica��o
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
	 * [UC3003] Manter Mensagem Tipo Solicita��o Especificacao
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
	 * [UC3003] Manter Mensagem Tipo Solicita��o Especificacao
	 * [SB0002]Excluir Mensagem Autom�tica
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 */
	public void removerMensagemTipoSolicitacaoEspecificacao(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Verificar se existe o v�nculo entre a localidade e o mun�cipio
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
	 * @author P�ricles Tavares
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
	 * [UC0XXX] Relat�rio Resumo de Registro de Atendimento
	 * Obter dados do Relat�rio Resumo de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @date 15/03/2011
	 */
	public Collection pesquisaRelatorioResumoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoAgrupamento) throws ControladorException;

	/**
	 * [UC0XXX] Relat�rio Estatistica Atendimento por Atendente
	 * Obter dados do Relat�rio Estatistica Atendimento por Atendente pelos parametros informados no
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
	 * [UC0XXX] Relat�rio Gest�o de Registro de Atendimento
	 * Obter dados do Relat�rio de Gest�o de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @param tipoRelatorio
	 *            (0 == Sint�tico e 1 == Anal�tico),
	 *            FiltrarRegistroAtendimentoHelper filtroRA
	 * @date 28/03/2011
	 */
	public Collection pesquisaRelatorioGestaoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoRelatorio) throws ControladorException;

	/**
	 * [UCXXXX] Inserir Tr�mite por Especifica��o
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
	 * Remover Tr�mite Especifica��o
	 * 
	 * @author Hebert Falc�o
	 * @date 01/04/2011
	 */
	public void removerTramiteEspecificacao(String[] ids) throws ControladorException;

	/**
	 * Atualizar Tr�mite Especifica��o
	 * 
	 * @author Hebert Falc�o
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
	 * Verifica se o imovel e/ou o cliente possuem d�bitos
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
	 * [SB0030] Gerar Ordem de Servi�o
	 * 
	 * @author Hebert Falc�o
	 * @date 15/12/2012
	 */
	public Collection<OrdemServico> gerarColecaoOrdemServicoAutomatica(Collection<Integer> idsServicoTipo, Integer idSolicitacaoTipo)
					throws ControladorException;

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Verifica se o meio da solicita��o permite libera��o do preenchimento do campo de documento da
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
	 * [SB0008] - Verificar Exig�ncia RA de Inclus�o de Conta Dados Cadastrais Divergentes do Im�vel
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
	 * [FS0053] - Verificar exist�ncia de servi�o em andamento para solicita��es do tipo religa��o
	 * ou restabelecimento
	 * 
	 * @author �talo Almeida
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
	 * [SB0041 - Verificar Titularidade do D�bito]
	 * [UC0251] Gerar Atividade de A��o de Cobran�a
	 * [SB0013] - Verificar Titularidade do D�bito
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