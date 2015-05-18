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

package gcom.atendimentopublico.ordemservico;

import gcom.acquagis.atendimento.OrdemServicoDetalhesJSONHelper;
import gcom.acquagis.atendimento.OrdemServicoJSONHelper;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.atendimentopublico.ordemservico.bean.*;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.atendimentopublico.registroatendimento.Tramite;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.PavimentoCalcada;
import gcom.cadastro.imovel.PavimentoRua;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.bean.CriticaOSLoteHelper;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.gui.atendimentopublico.ordemservico.RoteiroOSDadosProgramacaoHelper;
import gcom.relatorio.atendimentopublico.FiltrarRelatorioResumoOrdensServicoPendentesHelper;
import gcom.relatorio.atendimentopublico.RelatorioResumoOrdensServicoPendentesHelper;
import gcom.relatorio.ordemservico.OSRelatorioEstruturaHelper;
import gcom.relatorio.ordemservico.OSRelatorioPadraoComOcorrenciaHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ControladorOrdemServicoLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * [UC0454] Obter Descri��o da situa��o da OS
	 * Este caso de uso permite obter a descri��o de uma ordem de servi�o
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idOrdemServico
	 * @throws ControladorException
	 */
	public ObterDescricaoSituacaoOSHelper obterDescricaoSituacaoOS(Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC0441] Consultar Dados da Ordem de Servi�o
	 * 
	 * @author Leonardo Regis
	 * @date 15/08/2006
	 * @param idOrdemServico
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico consultarDadosOrdemServico(Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC0427] Tramitar Registro Atendimento
	 * Verifica Exist�ncia de Ordem de Servi�o Programada
	 * 
	 * @author Leonardo Regis
	 * @date 19/08/2006
	 * @param idOS
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarExistenciaOSProgramada(Integer idOS) throws ControladorException;

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB001] Selecionar Ordem de Servico por Situa��o [SB002] Selecionar Ordem
	 * de Servico por Situa��o da Programa��o [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Munic�pio [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdemServico(PesquisarOrdemServicoHelper filtro) throws ControladorException;

	/**
	 * Pesquisar ordem de servi�o.
	 * 
	 * @author Genival Barbosa
	 * @date 30/09/2014
	 * @return Ordem Servico
	 */
	public OrdemServico pesquisarOrdemServicoPrincipal(Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC413]- Pesquisar Tipo de Servi�o
	 * Pesquisar o Objeto servicoTipo referente ao idTiposervico recebido na
	 * descri��o da pesquisa, onde o mesmo sera detalhado.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 23/08/2006
	 * @param idTipoServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarServicoTipo(Integer idTipoServico) throws ControladorException;

	public ServicoTipo pesquisarServicoTipoObjeto(Integer idTipoServico) throws ControladorException;

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
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer numeroPaginasPesquisa, Integer idUnidadeOrganizacionalDestino)
					throws ControladorException;

	/**
	 * [UC0413] Pesquisar Tipo de Servi�o
	 * Count
	 * 
	 * @author Fl�vio
	 * @date 17/08/2006
	 */
	public Integer filtrarSTCount(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer idUnidadeOrganizacionalDestino) throws ControladorException;

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
	public Collection recuperarMaterialServicoTipoConsulta(Integer idServicoTipoMaterial) throws ControladorException;

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
	public Collection recuperarAtividadeServicoTipoConsulta(Integer idServicoTipoAtividade) throws ControladorException;

	/**
	 * [UC0367] Atualizar Dados da Liga��o Agua
	 * Consulta a ordem de servico pelo id
	 * 
	 * @author Rafael Pinto
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico recuperaOSPorId(Integer idOS) throws ControladorException;

	/**
	 * [UC0430] Gerar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 17/08/2006
	 */
	public ServicoTipo pesquisarSevicoTipo(Integer id) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 18/08/2006
	 */
	public void validarOrdemServico(OrdemServico ordemServico) throws ControladorException;

	/**
	 * [UC0430] Gerar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 17/08/2006
	 */
	public OrdemServico pesquisarOrdemServico(Integer id) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 18/08/2006
	 * @author eduardo henrique
	 * @date 15/09/2009
	 *       Alteracao na assinatura do m�todo de Gera��o de Ordens de Servi�o para receber list de
	 *       Servicos Autorizados pelo usu�rio (somente aqueles que precisam de autorizacao)
	 */
	public Integer gerarOrdemServico(OrdemServico ordemServico, Usuario usuario,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos, Integer idLocalidade, Integer idSetorComercial,
					Integer idBairro, Integer idUnidadeOrigem, Integer idUnidadeDestino, String parecerUnidadeDestino,
					String idOSPrincipal, Boolean forcarGerarOS, Short qtdPrestacaoGuiaPagamento)
					throws ControladorException;

	public Collection<Integer> gerarOrdemServicoAPartirGuiaPagamento(Collection<GuiaPagamento> colecaoGuiaPagamento)
					throws ControladorException;

	/**
	 * [UC0471] Obter Dados da Equipe
	 * 
	 * @author Raphael Rossiter
	 * @date 01/09/2006
	 * @return idQuipe
	 * @throws ControladorException
	 */
	public ObterDadosEquipe obterDadosEquipe(Integer idEquipe) throws ControladorException;

	/**
	 * [UC0456] Elaborar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection pesquisarTipoServicoDisponivelPorCriterio(UnidadeOrganizacional unidadeLotacao, int tipoCriterio, int origemServico)
					throws ControladorException;

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB0003] Seleciona Ordem de Servico por Criterio de Sele��o [SB0004]
	 * Seleciona Ordem de Servico por Situacao de Diagnostico [SB0005] Seleciona
	 * Ordem de Servico por Situacao de Acompanhamento pela Agencia [SB0006]
	 * Seleciona Ordem de Servico por Crit�rio Geral
	 * 
	 * @author Rafael Pinto
	 * @date 07/09/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdemServicoElaborarProgramacao(PesquisarOrdemServicoHelper filtro)
					throws ControladorException;

	/**
	 * [UC0371]Inserir Equipe no sistema.
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipe
	 *            a ser atualizada
	 * @throws ControladorException
	 */
	public long inserirEquipe(Equipe equipe) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * inser��o de equipe.
	 * [FS0007] Verificar quantidade de indicador de respons�vel Validar Carga
	 * Hor�ria M�xima Validar Placa do Ve�culo
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipe
	 * @throws ControladorException
	 */
	public void validarInsercaoEquipe(Equipe equipe) throws ControladorException;

	/**
	 * Inserir Componentes da Equipe no sistema.
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipeComponentes
	 *            a ser atualizada
	 * @throws ControladorException
	 */
	public long inserirEquipeComponentes(EquipeComponentes equipeComponentes) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * exibi��o da inser��o de componentes da equipe.
	 * [FS0003] Validar equipe componente j� existente [FS0004] Verificar
	 * exist�ncia do funcion�rio [FS0007] Verificar quantidade de indicador de
	 * respons�vel
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipeComponentes
	 * @throws ControladorException
	 */
	public void validarExibirInsercaoEquipeComponentes(Collection colecaoEquipeComponentes, EquipeComponentes equipeComponentes)
					throws ControladorException;

	/**
	 * [UC] Este m�todo se destina a validar todas as situa��es e
	 * particularidades da inser��o de componentes da equipe.
	 * [FS0006] Verificar quantidade de componentes da equipe em Tipo Perfil
	 * Servi�o Validar se possui algum respons�vel
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param equipeComponentes
	 * @throws ControladorException
	 */
	public void validarInsercaoEquipeComponentes(Collection colecaoEquipeComponentes, Equipe equipe) throws ControladorException;

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @return Collection<ObterDadosAtividadesOSHelper>
	 * @throws ControladorException
	 */
	public Collection<ObterDadosAtividadesOSHelper> obterDadosAtividadesOS(Integer idOS) throws ControladorException;

	/**
	 * [UC0456] Elaborar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @param dataRoteiro
	 * @return collection
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiro(Date dataRoteiro) throws ControladorException;

	/**
	 * [UC0479] Gerar D�bito da Ordem de Servi�o
	 * [FS0001] Verificar Exist�ncia da Ordem de Servi�o [FS0002] Verificar
	 * Exist�ncia do Tipo de D�bito [FS0003] Validar Valor do D�bito [FS0004]
	 * Validar Quantidade de Parcelas
	 * 
	 * @author Leonardo Regis
	 * @date 11/09/2006
	 * @param ordemServicoId
	 * @param tipoDebitoId
	 * @param valorDebito
	 * @param qtdeParcelas
	 * @throws ControladorException
	 */
	public void gerarDebitoOrdemServico(Integer ordemServicoId, Integer tipoDebitoId, BigDecimal valorDebito, int qtdeParcelas)
					throws ControladorException;

	/**
	 * [UC0456] Elaborar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 13/09/2006
	 * @param colecaoOrdemServicoProgramacao
	 * @throws ControladorException
	 */
	public void elaboraRoteiro(Collection<OSProgramacaoHelper> colecaoOrdemServicoProgramacao, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param idOS
	 * @param idAtividade
	 * @param tipoAtividade
	 * @return Collection<ObterDadosAtividadesOSHelper>
	 * @throws ControladorException
	 */
	public Collection<ObterDadosAtividadeOSHelper> obterDadosAtividadeOS(Integer idOS, Integer idAtividade, int tipoAtividade)
					throws ControladorException;

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param equipeId
	 * @param colecaoIdOSProgramadas
	 * @param colecaoOSDistribuidasPorEquipe
	 * @param dataFinalProgramacao
	 * @param dataRoteiro
	 * @return valor da carga de trabalho da equipe
	 * @throws ControladorException
	 */
	public ObterCargaTrabalhoEquipeHelper obterCargaTrabalhoEquipe(Integer equipeId, Collection<Integer> colecaoIdOSProgramadas,
					Collection<ObterOSDistribuidasPorEquipeHelper> colecaoOSDistribuidasPorEquipe, Date dataRoteiro)
					throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * [FS0001] - Verificar Unidade do Usu�rio
	 * 
	 * @author S�vio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void verificarUnidadeUsuario(Integer numeroOS, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * [FS0006] - Verificar Origem do Encerramento da Ordem de Servi�o
	 * 
	 * @author S�vio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void verificarOrigemEncerramentoOS(Integer numeroOS, Date dataEncerramento) throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * [SB0001] - Encerrar sem execu��o
	 * 
	 * @author S�vio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void encerrarOSSemExecucao(OSEncerramentoHelper helper, Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos,
					OrigemEncerramentoOrdemServico origemEncerramento,
					DadosAtividadesOrdemServicoHelper dadosAtividadeorAtividadesOrdemServicoHelper) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection<Atividade>
	 * @throws ErroRepositorioException
	 */
	public Collection<Atividade> obterAtividadesOrdemServico(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 18/09/2006
	 * @param numeroOS
	 * @return Collection<Equipe>
	 * @throws ErroRepositorioException
	 */
	public Collection<Equipe> obterEquipesProgramadas(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0467] - Atualizar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 18/09/2006
	 */
	public void atualizarOrdemServico(OrdemServico ordemServico, Usuario usuario) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarOSAssociadaDocumentoCobranca(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarOSAssociadaRA(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @author Saulo Lima
	 * @date 04/06/2009
	 *       Corre��o + altera��o para retornar a UnidadeOrganizacional
	 * @param numeroOS
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeDestinoUltimoTramite(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return Collection<Equipe>
	 * @throws ControladorException
	 */
	public Collection<Equipe> obterEquipesPorUnidade(Integer idUnidade) throws ControladorException;

	/**
	 * [UC0362] Efetuar Instala��o de Hidr�metro
	 * 
	 * @author Leonardo Regis
	 * @date 19/09/2006
	 * @param dadosOS
	 * @throws ControladorException
	 */
	public void atualizarOSParaEfetivacaoInstalacaoHidrometro(DadosAtualizacaoOSParaInstalacaoHidrometroHelper dadosOS)
					throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 20/09/2006
	 * @param dataExecucao
	 *            ,
	 *            horaInicio, horaFim, idEquipe
	 * @return void
	 * @throws ControladorException
	 */
	public void verificaDadosAdicionarPeriodoEquipe(String dataExecucao, String horaInicio, String horaFim, Integer idEquipe,
					String dataEncerramentoOS, Integer numeroOS) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @param dataRoteiro
	 *            ,idUnidadeOrganizacional
	 * @return collection<OrdemServicoProgramacao>
	 * @throws ControladorException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiroUnidade(Date dataRoteiro, Integer idUnidadeOrganizacional)
					throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o [FS0008] -
	 * Verificar possibilidade da inclus�o da ordem de servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void validarInclusaoOsNaProgramacao(OrdemServico ordemServico, Integer unidadeLotacao) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o [FS0012] -
	 * Reordena Sequencial de Programa��o - Inclus�o de Ordem de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void reordenaSequencialProgramacaoInclusaoOrdemServico(Date dataRoteiro, short sequencialAlterado) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o [FS0012] -
	 * Reordena Sequencial de Programa��o por Equipe e por dia - Inclus�o de Ordem de Servi�o
	 * 
	 * @author isilva
	 * @date 09/11/2010
	 * @param dataRoteiro
	 * @param equipe
	 * @param sequencialAlterado
	 * @throws ControladorException
	 */
	public void reordenaSequencialProgramacaoInclusaoOrdemServico(Date dataRoteiro, Equipe equipe, short sequencialAlterado)
					throws ControladorException;

	/**
	 * [UC0455] Exibir Calend�rio para Elabora��o ou Acompanhamento de Roteiro
	 * 
	 * @author R�mulo Aur�lio
	 * @date 21/09/2006
	 * @param mesAnoReferencia
	 * @return Collection<ProgramacaoRoteiro>
	 * @throws ControladorException
	 */

	public Collection consultarProgramacaoRoteiro(String mesAnoReferencia, Integer unidadeOrganizacional) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */
	public void alocaEquipeParaOs(Integer numeroOS, Date dataRoteiro, Integer idEquipe) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */
	public void incluirOrdemServicoProgramacao(OrdemServicoProgramacao ordemServicoProgramacao, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Acompanhar Roteiro de Programacao de Ordens de Servico
	 * com sequencial informado
	 * 
	 * @author isilva
	 * @date 16/11/2011
	 * @param ordemServicoProgramacao
	 * @param usuarioLogado
	 * @param verificaVazio
	 * @throws ControladorException
	 */
	public void incluirOrdemServicoProgramacaoComSequencialInformado(OrdemServicoProgramacao ordemServicoProgramacao,
					Usuario usuarioLogado, boolean verificaVazio) throws ControladorException;

	/**
	 * M�todo que valida a ordem de servi�o utilizado por diversos outros
	 * m�todos do atendimento ao p�blico
	 * 
	 * @author Leonardo Regis
	 * @date 22/09/2006
	 * @throws ControladorException
	 */
	public void validaOrdemServico(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public Collection<Equipe> recuperaEquipeDaOSProgramacaoPorDataRoteiro(Integer idOs, Date dataRoteiro) throws ControladorException;

	/**
	 * Atualiza��o Geral de OS
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param os
	 * @param updateCorte
	 * @param updateSupressao
	 * @throws ControladorException
	 */
	public void atualizaOSGeral(OrdemServico os, boolean updateCorte, boolean updateSupressao) throws ControladorException;

	/**
	 * [UC0457] Encerrar Ordem de Servi�o
	 * 
	 * @author S�vio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarColecaoServicoTipo(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public void verificaExitenciaProgramacaoAtivaParaDiasAnteriores(Integer idOs, Date dataRoteiro) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */
	public void atualizarOrdemServicoProgramacaoSituacaoOs(Integer numeroOS, Date dataRoteiro, short situacaoFechamento,
					Integer idOsProgramNaoEncerMotivo, Date dataHoraVisita, Usuario usuario) throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * [SB0002] - Encerrar com execu��o e sem refer�ncia
	 * 
	 * @author S�vio Luiz
	 * @date 25/09/2006
	 * @author Saulo Lima
	 * @date 18/05/2009
	 *       Altera��o para receber como par�metro um OSEncerramentoHelper (OS's Associadas)
	 * @throws ControladorException
	 */
	public void encerrarOSComExecucaoSemReferencia(OSEncerramentoHelper helper,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos,
					OrigemEncerramentoOrdemServico origemEncerramento,
					DadosAtividadesOrdemServicoHelper dadosAtividadeorAtividadesOrdemServicoHelper) throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * [SB0003] - Encerrar com execu��o e com refer�ncia
	 * 
	 * @author S�vio Luiz
	 * @date 27/09/2006
	 * @author Saulo Lima
	 * @date 18/05/2009
	 *       Altera��o para receber como par�metro um OSEncerramentoHelper (OS's Associadas)
	 * @throws ControladorException
	 */
	public Integer encerrarOSComExecucaoComReferencia(OSEncerramentoHelper osEncerramentoHelper,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos,
					OrigemEncerramentoOrdemServico origemEncerramento,
					DadosAtividadesOrdemServicoHelper dadosAtividadeorAtividadesOrdemServicoHelper) throws ControladorException;

	/**
	 * Acompanhar Roteiro de Programa��o de Ordens de Servi�o
	 * Reordena Sequencial de Programa��o
	 * 
	 * @author isilva
	 * @date 12/11/2010
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void reordenaSequencialOrdemServicoProgramacao(Date dataRoteiro, Integer idEquipe) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o [FS0012]
	 * Reordena Sequencial de Programa��o - Nova Ordem para Ordem de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param dataRoteiro
	 * @param sequencialInformado
	 * @param sequencialAtual
	 * @param idEquipe
	 * @param verificaVazio
	 * @throws ControladorException
	 */
	public void reordenaSequencialProgramacaoNovaOrdem(Date dataRoteiro, short sequencialInformado, short sequencialAtual,
					Integer idEquipe, boolean verificaVazio) throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * [FS0002] - Validar Tipo Servi�o
	 * [FS0004] - Verificar preenchimento dos campos
	 * [FS0007] - Validar Data de Encerramento
	 * [FS0008] - Validar Data do roteiro
	 * 
	 * @author S�vio Luiz
	 * @date 29/09/2006
	 *       Corre�ao: Critica de encerrar OS com data de encerramento anterior a data de gera��o
	 * @author Andre Nishimura.
	 * @throws ControladorException
	 */
	public void validarCamposEncerrarOS(String indicadorExecucao, String numeroOS, String motivoEncerramento, String dataEncerramento,
					String tipoServicoReferenciaId, String indicadorPavimento, String pavimento, String idTipoRetornoOSReferida,
					String indicadorDeferimento, String indicadorTrocaServicoTipo, String idServicoTipo, String dataRoteiro, String idRA,
					String indicadorVistoriaServicoTipo, String codigoRetornoVistoriaOs, String horaEncerramento, Usuario usuarioLogado,
					String indicadorAfericaoHidrometro, String idHidrometroCondicao, String indicadorResultado, String idFuncionario,
					String indicadorClienteAcompanhou)
					throws ControladorException;

	public void verificarOrdemServicoControleConcorrencia(OrdemServico ordemServico) throws ControladorException;

	/**
	 * [UC0391] Inserir Valor de Cobran�a de Servi�o.
	 * Permite a inclus�o de um novo valor de cobran�a de servi�o na tabela
	 * SERVICO_COBRANCA_VALOR.
	 * 
	 * @author Leonardo Regis
	 * @date 29/09/2006
	 * @param servicoCobrancaValor
	 * @throws ControladorException
	 */
	public void inserirValorCobrancaServico(ServicoCobrancaValor servicoCobrancaValor) throws ControladorException;

	/**
	 * [UC0391] Atualizar Valor de Cobran�a de Servi�o.
	 * Permite a atualiza��o de um novo valor de cobran�a de servi�o na tabela
	 * SERVICO_COBRANCA_VALOR.
	 * 
	 * @author R�mulo Aur�lio
	 * @date 01/11/2006
	 * @param servicoCobrancaValor
	 * @throws ControladorException
	 */
	public void atualizarValorCobrancaServico(ServicoCobrancaValor servicoCobrancaValor) throws ControladorException;

	/**
	 * [UC0391] Atualizar Tipo de Retorno da OS_Referida.
	 * 
	 * @author Thiago Ten�rio
	 * @date 01/11/2006
	 * @param
	 * @throws ControladorException
	 */
	public void atualizarTipoRetornoOrdemServicoReferida(OsReferidaRetornoTipo osReferidaRetornoTipo) throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * 
	 * @author S�vio Luiz
	 * @date 27/09/2006
	 * @throws ControladorException
	 */
	public boolean tramitarOuEncerrarRADaOSEncerrada(Integer numeroOS, Usuario usuarioLogado, String idMotivoEncerramento, String idRA,
					String dataRoteiro) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * 
	 * @author lms
	 * @date 14/08/2006
	 */
	public void validarServicoTipo(Integer idRegistroAtendimento, Integer idServicoTipo) throws ControladorException;

	/**
	 * M�todo que valida a ordem de servi�o utilizado por diversos outros
	 * m�todos do atendimento ao p�blico sem a valida��o de indicador comercial
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 12/10/2006
	 * @throws ControladorException
	 */
	public void validaOrdemServicoAtualizacao(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0458] - Imprimir Ordem de Servi�o
	 * Pesquisa os campos da OS que ser�o impressos no relat�rio de Ordem de
	 * Servico
	 * 
	 * @author Rafael Corr�a
	 * @date 17/10/2006
	 * @param idOrdemServico
	 * @return OSRelatorioHelper
	 * @throws ControladorException
	 */
	public Collection pesquisarOSRelatorio(Collection idsOrdemServico) throws ControladorException;

	/**
	 * [UC0482] - Obter Endere�o Abreviado da Ocorr�ncia do RA
	 * Pesquisa o Endereco Abreviado da OS
	 * 
	 * @author Rafael Corr�a
	 * @date 19/10/2006
	 * @param idOrdemServico
	 * @throws ControladorException
	 */
	public String obterEnderecoAbreviadoOS(Integer idOrdemServico) throws ControladorException;

	/**
	 * Pesquisa o endereco completo da OS
	 * 
	 * @author Saulo Lima
	 * @date 07/04/2010
	 * @param idOrdemServico
	 * @return String
	 * @throws ControladorException
	 */
	public String obterEnderecoCompletoOS(Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection<Material> obterMateriaisProgramados(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 *            ,
	 *            idMaterial
	 * @return BigDecimal
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterQuantidadePadraoMaterial(Integer numeroOS, Integer idMaterial) throws ControladorException;

	/**
	 * Imprimir OS
	 * Atualiza a data de emiss�o e a de �ltima altera��o de OS quando gerar o
	 * relat�rio
	 * 
	 * @author Rafael Corr�a
	 * @date 26/10/2006
	 * @param colecaoIdsOrdemServico
	 * @throws ControladorException
	 */
	public void atualizarOrdemServicoRelatorio(Collection colecaoIdsOrdemServico) throws ControladorException;

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Servi�o
	 * Pesquisa os dados da OrdemServicoAtividade
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServico
	 *            ,
	 *            idAtividade
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoAtividade pesquisarOrdemServicoAtividade(Integer numeroOS, Integer idAtividade) throws ControladorException;

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Servi�o
	 * Pesquisa os dados da OsAtividadeMaterialExecucao associada �
	 * OrdemServicoAtividade para a data informada
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServicoAtividade
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOsAtividadeMaterialExecucao(Integer idOrdemServicoAtividade) throws ControladorException;

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relat�rio Produtividade de Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 13/04/2011
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
	 * @param tipoOrdenacao
	 * @return Collection
	 */
	public Collection pesquisarGerarRelatorioProdutividadeEquipe(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ControladorException;

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relat�rio Acompanhamento de Execu��o de Ordem de Servi�o
	 * 
	 * @author Rafael Corr�a
	 * @date 01/11/06
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
	 * @param tipoOrdenacao
	 * @return Collection
	 */
	public Collection pesquisarOSGerarRelatorioAcompanhamentoExecucao(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ControladorException;

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relat�rio Acompanhamento de Execu��o de Ordem de Servi�o
	 * 
	 * @author Rafael Corr�a
	 * @date 01/11/06
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
	public int pesquisarOSGerarRelatorioAcompanhamentoExecucaoCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException;

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relat�rio de Produtividade de Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 13/04/2011
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
	public int pesquisarGerarRelatorioProdutividadeEquipeCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException;

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relat�rio Resumo de Ordens de Servi�o n�o Executados por Equipe"
	 * 
	 * @author P�ricles TAvares
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
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException;

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relat�rio Resumo de Ordens de Servi�o n�o Executados por Equipe"
	 * 
	 * @author P�ricles TAvares
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
					throws ControladorException;

	/**
	 * Pesquisa as equipes de acordo com os par�metros informado pelo usu�rio
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corr�a
	 * @date 09/11/06
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
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idEquipeTipo, Integer numeroPagina)
					throws ControladorException;

	/**
	 * Verifica a quantidade de registros retornados da pesquisa de equipe
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corr�a
	 * @date 09/11/06
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
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idEquipeTipo) throws ControladorException;

	/**
	 * Remove as equipes selecionadas pelo usu�rio e as equipes componentes
	 * associadas a ela
	 * [UC0372] - Manter Equipe
	 * 
	 * @author Rafael Corr�a
	 * @date 09/11/06
	 * @param idsEquipes
	 * @throws ControladorException
	 */
	public void removerEquipes(String[] idsEquipes, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Valida a ordem de servi�o
	 * [UC0488] - Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 01/11/06
	 * @return Integer
	 */
	public void validarOrdemServico(Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscaliza��o
	 * Recupera os par�metros necess�rios da OS
	 * 
	 * @author S�vio Luiz
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsOS(Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC0372] - Manter Equipe
	 * Atualiza a equipe e seus componentes na base
	 * 
	 * @author Rafael Corr�a
	 * @date 14/11/2006
	 * @param equipe
	 * @throws ControladorException
	 */
	public void atualizarEquipe(Equipe equipe, Collection colecaoEquipeComponentes, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0383] Manter Material [FS0001] Atualizar Material [FS0002] Atualizar
	 * Material
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/11/2006
	 * @param material
	 * @throws ControladorException
	 */

	// [FS0001] VERIFICAR EXISTENCIA DA DESCRI��O
	// [FS0002] VERIFICAR EXISTENCIA DA DESCRI��O ABREVIADA
	public void verificarExistenciaDescricaoMaterial(Material material) throws ControladorException;

	/**
	 * [UC0450] Pesquisar Ordem de Servico verifica o tamanho da consulta
	 * [SB001] Selecionar Ordem de Servico por Situa��o [SB002] Selecionar Ordem
	 * de Servico por Situa��o da Programa��o [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Munic�pio [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarOrdemServicoTamanho(PesquisarOrdemServicoHelper filtro) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return Integer[]
	 * @throws ControladorException
	 */
	public Integer[] informarRetornoOSFiscalizacao(Integer idOrdemServico, FiscalizacaoSituacao fiscalizacaoSituacao,
					String indicadorDocumentoEntregue, Integer idLigacaoAguaSituacaoImovel, Integer idLigacaoEsgotoSituacaoImovel,
					Integer idImovel, String indicadorMedicaoTipo, String indicadorGeracaoDebito, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * M�todo que � chamado pelo [UC0251] Gerar Atividade de A��o de Cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 18/08/2006
	 */
	public Integer gerarOrdemServicoSemValidacao(OrdemServico ordemServico, Integer idLocalidade, Usuario usuario)
					throws ControladorException;

	/**
	 * Atualiza os imoveis da OS que refere a RA passada como parametro
	 * 
	 * @author S�vio Luiz
	 * @date 03/01/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarOsDaRA(Integer idRA, Integer idImovel) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author Raphael Rossiter
	 * @date 25/01/2007
	 * @param idOS
	 * @return fiscalizacaoSituacao
	 * @throws ControladorException
	 */
	public void verificarOSJaFiscalizada(Integer idOS) throws ControladorException;

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscaliza��o
	 * [FS0001] - Validar Ordem de Servi�o
	 * 
	 * @author Raphael Rossiter
	 * @date 29/01/2007
	 */
	public void validarOrdemServicoInformarRetornoOrdemFiscalizacao(OrdemServico ordemServico) throws ControladorException;

	/**
	 * [UC0539] Manter Prioridade do Tipo de Servi�o
	 * Remove um ou mais objeto do tipo ServicoTipoPrioridade no BD
	 * 
	 * @author Vivianne Sousa
	 * @date 12/02/2007
	 * @param ids
	 * @return void
	 * @throws ControladorException
	 */
	public void removerPrioridadeTipoServico(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0455] Exibir Calend�rio para Elabora��o ou Acompanhamento de Roteiro
	 * 
	 * @author Raphael Rossiter
	 * @date 14/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer verificarExistenciaOSProgramacao(Integer idProgramacaoRoteiro) throws ControladorException;

	/**
	 * Rotina Batch que encerra todas as OS de um servi�o tipo especifico que
	 * n�o tenha RA
	 * 
	 * @author S�vio Luiz
	 * @date 23/02/2007
	 * @throws ControladorException
	 */
	public void encerrarOSDoServicoTipoSemRA(Usuario usuarioLogado, Integer idServicoTipo) throws ControladorException;

	/**
	 * Pesquisar data e equipe da programa��o da ordem servi�o
	 * 
	 * @author Ana Maria
	 * @date 09/03/2007
	 */
	public OrdemServicoProgramacao pesquisarDataEquipeOSProgramacao(Integer idOs) throws ControladorException;

	/**
	 * [UC0470] Acompanhar Roteiro de Programa��o de Ordens de Servi�o [FS0012]
	 * Reordena Sequencial de Programa��o
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void reordenarSequencialProgramacao(Date dataRoteiro, Integer idEquipe) throws ControladorException;

	/**
	 * [UC0513] Manter Tipo Retorno OS Referida
	 * Remover Tipo Retorno OS Referida
	 * 
	 * @author Thiago Ten�rio
	 * @date 19/03/2007
	 */
	public void removerTipoRetornoOrdemServicoReferida(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0457] Encerra Ordem de Servi�o
	 * Pesquisa a opera��o que faz parte da associa��o com o SERVICO_TIPO na
	 * tabela SERVICO_TIPO_OPERACAO
	 * 
	 * @author Raphael Rossiter
	 * @date 26/04/2007
	 * @param idServicoTipo
	 *            , integracaoComercialHelper
	 * @throws ControladorException
	 */
	public Integer pesquisarServicoTipoOperacao(Integer idServicoTipo, Integer idOperacao) throws ControladorException;

	/**
	 * Filtra os Imoveis
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Ivan S�rgio
	 * @date 08/11/2007
	 * @author Andre Nishimura
	 * @date 13/03/2010
	 *       Modifica�ao na emissao de ordens de servi�o seletivas para acrescentar novos parametros
	 *       de filtro
	 */
	/*
	 * public Collection filtrarImovelEmissaoOrdensSeletivas(
	 * String tipoOrdem, String quantidadeMaxima, String elo,
	 * String localidadeInicial, String localidadeFinal, String setorComercialInicial,
	 * String setorComercialFinal, String quadraInicial, String quadraFinal,
	 * String rotaInicial, String rotaFinal, String perfilImovel,
	 * String categoria, String subCategoria, String quantidadeEconomiasInicial,
	 * String quantidadeEconomiasFinal, String quantidadeDocumentosInicial, String
	 * quantidadeDocumentosFinal,
	 * String numeroMoradoresInicial, String numeroMoradoresFinal, String areaConstruidaInicial,
	 * String areaConstruidaFinal, String imovelCondominio, String mediaImovel,
	 * String consumoEconomia, String tipoMedicao, String capacidadeHidrometro,
	 * String marcaHidrometro, String[] anormalidadeHidrometro, String
	 * numeroOcorrenciasAnormalidade,
	 * String mesAnoInstalacaoHidrometro) throws ControladorException;
	 */
	public Collection<Integer> filtrarImovelEmissaoOrdensSeletivas(OrdemServicoSeletivaHelper ordemServicoSeletivaHelper)
					throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * M�todo que � chamado pelo [UC0713] Emitir Ordem de Servico Seletiva
	 * 
	 * @author Willian Pereira
	 * @date 18/03/2010
	 */
	public Map<Integer, Integer> gerarOrdemServicoSeletiva(List<Integer> idsImoveis, ServicoTipo servicoTipo, Empresa empresa,
					Usuario usuario, Integer idComandoOSSeletiva) throws ControladorException;

	/**
	 * [UC0430] - Gerar Ordem de Servi�o
	 * M�todo que � chamado pelo [UC0713] Emitir Ordem de Servico Seletiva
	 * 
	 * @author Ivan S�rgio
	 * @date 27/11/2007
	 */
	public Integer gerarOrdemServicoSeletiva(OrdemServico ordemServico, Imovel imovel, Usuario usuario) throws ControladorException;

	/**
	 * [UC0732] - Gerar Relatorio Acompanhamento de Ordens de Servico Hidrometro
	 * 
	 * @author Ivan S�rgio
	 * @date 27/11/2007
	 * @param tipoOrdem
	 * @param situacaoOrdemServico
	 * @param idLocalidade
	 * @param dataEncerramentoInicial
	 * @param dataEncerramentoFinal
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOrdemServicoGerarRelatorioAcompanhamentoHidrometro(String idEmpresa, String tipoOrdem,
					String situacaoOrdemServico, String idLocalidade, String dataEncerramentoInicial, String dataEncerramentoFinal,
					String tipoRelatorio) throws ControladorException;

	/**
	 * [UC0640] Gerar TXT A��o de Cobran�a
	 * 
	 * @author Raphael Rossiter
	 * @date 04/01/2008
	 * @param idCobrancaDocumento
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarOrdemServicoPorCobrancaDocumento(Integer idCobrancaDocumento) throws ControladorException;

	/**
	 * M�todo respons�vel por retornar a Lista de Servi�os associados a um determinado servi�o, que
	 * necessitem de autoriza��o.
	 * 
	 * @author Saulo Lima
	 * @date 04/06/2009
	 *       Novo par�metro numeroOS
	 * @param servicoTipo
	 *            [obrigat�rio]
	 * @param evento
	 *            - enum dos Tipos de Eventos a serem verificados [obrigat�rio]
	 * @param origemEncerramento
	 *            - informa a Origem de Encerramento (qual funcionalidade) da a��o do usu�rio.
	 * @param numeroOS
	 *            - utizado para pesquisar as equipes, caso necess�rio.
	 * @return List<ServicoAssociadoAutorizacaoHelper>
	 *         Deve ser utilizada apenas na verifica��o para encerramento.
	 * @throws ControladorException
	 *             se houver erro na consulta de servicos Associados
	 */
	public List<ServicoAssociadoAutorizacaoHelper> verificarServicosAssociadosParaAutorizacao(ServicoTipo servicoTipo,
					EventoGeracaoServico evento, OrigemEncerramentoOrdemServico origemEncerramento, Integer numeroOS)
					throws ControladorException;

	// /**
	// * M�todo respons�vel por validar os dados da autoriza��o dos servi�os associados.
	// * @author Virg�nia Melo
	// * @param mapServicosAutorizados Map com os servi�os que est�o sendo autorizados.
	// * @param listaAutorizacao List com os servi�os a serem autorizados.
	// */
	// public void validarAutorizacaoServicosAssociados(Map<Integer,
	// ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados,
	// List<ServicoAssociadoAutorizacaoHelper> listaAutorizacao) throws ControladorException;

	/**
	 * M�todo retorna uma inst�ncia de AtendimentoMotivoEncerramento
	 * 
	 * @author Saulo Lima
	 * @date 21/05/2009
	 * @param idAtendimentoMotivoEncerramento
	 * @return AtendimentoMotivoEncerramento
	 * @throws ControladorException
	 */
	public AtendimentoMotivoEncerramento pesquisarAtendimentoMotivoEncerramentoPorId(Integer idAtendimentoMotivoEncerramento)
					throws ControladorException;

	/**
	 * M�todo respons�vel por pesquisar os layouts de ordem de servi�o.
	 * 
	 * @author Virg�nia Melo
	 * @date 28/05/2009
	 * @return Cole��o de OrdemServicoLayout.
	 * @throws ControladorException
	 */
	public Collection<OrdemServicoLayout> pesquisarOrdemServicoLayouts() throws ControladorException;

	/**
	 * Recupera todos os poss�veis motivos de interrup��o(ativos).
	 * 
	 * @author Virg�nia Melo
	 * @date 10/06/2009
	 * @return Cole��o com todos os motivos de interrupcao ativos.
	 */
	public Collection<MotivoInterrupcao> pesquisarMotivoInterrupcao() throws ControladorException;

	/**
	 * Recupera todos os locais de ocorr�ncia;
	 * 
	 * @author Virg�nia Melo
	 * @date 10/06/2009
	 * @return Cole��o com todos os locais de ocorrencia ativos.
	 */
	public Collection<LocalOcorrencia> pesquisarLocalOcorrencia() throws ControladorException;

	/**
	 * Recupera um local de ocorr�ncia.
	 * 
	 * @author Virg�nia Melo
	 * @date 10/06/2009
	 * @return Local de Ocorr�ncia
	 */
	public LocalOcorrencia pesquisarLocalOcorrencia(Integer idLocalOcorrencia) throws ControladorException;

	/**
	 * Recupera uma cole��o de PavimentoRua ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 10/06/2009
	 * @return Pavimento Rua
	 */
	public Collection<PavimentoRua> pesquisarPavimentoRua() throws ControladorException;

	/**
	 * Recupera uma cole��o de PavimentoCalcada ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 10/06/2009
	 * @return Pavimento Calcada
	 */
	public Collection<PavimentoCalcada> pesquisarPavimentoCalcada() throws ControladorException;

	/**
	 * Recupera uma cole��o de CausaVazamento ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Causa Vazamento
	 */
	public Collection<CausaVazamento> pesquisarCausaVazamento() throws ControladorException;

	/**
	 * Recupera uma cole��o de Agente Externo ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Agente Externo
	 */
	public Collection<AgenteExterno> pesquisarAgenteExterno() throws ControladorException;

	/**
	 * Recupera uma cole��o de Diametro Rede Agua ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de Diametro Rede �gua
	 */
	public Collection<DiametroRedeAgua> pesquisarDiametroRedeAgua() throws ControladorException;

	/**
	 * Recupera uma cole��o de Diametro Ramal Agua ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de Diametro Ramal �gua
	 */
	public Collection<DiametroRamalAgua> pesquisarDiametroRamalAgua() throws ControladorException;

	/**
	 * Recupera uma cole��o de Diametro Rede Esgoto ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de Diametro Rede Esgoto
	 */
	public Collection<DiametroRedeEsgoto> pesquisarDiametroRedeEsgoto() throws ControladorException;

	/**
	 * Recupera uma cole��o de Diametro Ramal Esgoto ativos.
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de Diametro Ramal Esgoto
	 */
	public Collection<DiametroRamalEsgoto> pesquisarDiametroRamalEsgoto() throws ControladorException;

	/**
	 * Recupera uma cole��o de MaterialRedeAgua
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de MaterialRedeAgua
	 */
	public Collection<MaterialRedeAgua> pesquisarMaterialRedeAgua() throws ControladorException;

	/**
	 * Recupera uma cole��o de MaterialRamalAgua
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de MaterialRamalAgua
	 */
	public Collection<MaterialRamalAgua> pesquisarMaterialRamalAgua() throws ControladorException;

	/**
	 * Recupera uma cole��o de MaterialRedeEsgoto
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de MaterialRedeEsgoto
	 */
	public Collection<MaterialRedeEsgoto> pesquisarMaterialRedeEsgoto() throws ControladorException;

	/**
	 * Recupera uma cole��o de MaterialRamalEsgoto
	 * 
	 * @author Virg�nia Melo
	 * @date 17/06/2009
	 * @return Cole��o de MaterialRamalEsgoto
	 */
	public Collection<MaterialRamalEsgoto> pesquisarMaterialRamalEsgoto() throws ControladorException;

	/**
	 * Recupera um PavimentoRua.
	 * 
	 * @author Virg�nia Melo
	 * @date 10/06/2009
	 * @return Pavimento Rua
	 */
	public PavimentoRua pesquisarPavimentoRua(Integer idPavimento) throws ControladorException;

	/**
	 * Recupera um PavimentoCalcada.
	 * 
	 * @author Virg�nia Melo
	 * @date 10/06/2009
	 * @return Pavimento Calcada
	 */
	public PavimentoCalcada pesquisarPavimentoCalcada(Integer idPavimento) throws ControladorException;

	/**
	 * Recupera uma cole��o de OrdemServicoValaPavimento a partir da Ordem de Servi�o.
	 * 
	 * @author Virg�nia Melo
	 * @date 22/06/2009
	 * @return Cole��o de OrdemServicoValaPavimento
	 */
	public Collection<OrdemServicoValaPavimento> pesquisarOrdemServicoValaPavimento(Integer idOrdemServico) throws ControladorException;

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Virg�nia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idProgramacaoRoteiro, Integer idOrdemServico, Integer idEquipe)
					throws ControladorException;

	/**
	 * Recupera uma OrdemServicoDeslocamento atrav�s do id da OS Programa��o.
	 * 
	 * @author Virg�nia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoDeslocamento
	 */
	public OrdemServicoDeslocamento pesquisarOrdemServicoDeslocamento(Integer idOsProgramacao) throws ControladorException;

	/**
	 * Recupera uma cole��o de OSInterrupcaoDeslocamento a partir da OS Programa��o
	 * 
	 * @author Virg�nia Melo
	 * @date 22/06/2009
	 * @return Cole��o de OSInterrupcaoDeslocamento
	 */
	public Collection<OrdemServicoInterrupcaoDeslocamento> pesquisarOSInterrupcaoDeslocamento(Integer idOsProgramacao)
					throws ControladorException;

	/**
	 * Recupera uma cole��o de OSInterrupcaoExecucao a partir da OS Programa��o
	 * 
	 * @author Virg�nia Melo
	 * @date 30/06/2009
	 * @return Cole��o de OSInterrupcaoExecucao
	 */
	public Collection<OrdemServicoInterrupcaoExecucao> pesquisarOSInterrupcaoExecucao(Integer idOsProgramacao) throws ControladorException;

	/**
	 * Salva as fotos da ordem de servico
	 * 
	 * @param foto
	 * @throws ControladorException
	 */
	public void salvarFotoOrdemServico(OrdemServicoFotoOcorrencia foto) throws ControladorException;

	/**
	 * Lista uma colecao de OrdemServicoFotoOcorrencia pesquisando por uma
	 * OrdemServicoFotoOcorrencia
	 * 
	 * @param OrdemServicoFotoOcorrencia
	 * @return Collection<OrdemServicoFotoOcorrencia>
	 * @throws ControladorException
	 */
	public Collection<OrdemServicoFotoOcorrencia> listarOSFoto(OrdemServicoFotoOcorrencia osFoto) throws ControladorException;

	/**
	 * Pesquisa o �ltimo n�mero sequencial das fotos, pelo id da ordem de servico
	 * 
	 * @param idOrdemServico
	 * @return numeroSequencia
	 * @throws ControladorException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServico(Integer idOrdemServico) throws ControladorException;

	/**
	 * Pesquisa o �ltimo n�mero sequencial das fotos, pelo id da ordem de servico e o id da ordem
	 * servico programacao
	 * 
	 * @param idOrdemServico
	 * @param idOrdemServicoProgramacao
	 * @return numeroSequencia
	 * @throws ControladorException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServicoProgramacao(Integer idOrdemServico, Integer idOrdemServicoProgramacao)
					throws ControladorException;

	/**
	 * Compara se o servicoTipo, pertence ao subgrupo de substitui��o
	 * 
	 * @param idServicoTipo
	 * @return true se pertencer
	 * @throws ControladorException
	 */
	public boolean comparaServicoTipoSubgrupoSubstituicaoHidrometro(String idServicoTipo) throws ControladorException;

	/**
	 * Gerar Ordens Servico Seletiva
	 * M�todo usado em Emitir Ordem Servi�o Seletiva
	 * 
	 * @throws ControladorException
	 */
	public byte[] gerarOrdensServicoSeletiva(Collection colecaoDadosRelatorio, OrdemServicoSeletivaHelper helper,
					int idFuncionalidadeIniciada, String nomeRelatorio) throws ControladorException;

	/**
	 * Recupera uma cole��o de Diametro Cavalete Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 30/08/2010
	 * @return Cole��o de Diametro Cavalete Agua.
	 */
	public Collection<DiametroCavaleteAgua> pesquisarDiametroCavaleteAgua() throws ControladorException;

	/**
	 * Recupera uma cole��o de Material Rede Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 17/06/2009
	 * @return Cole��o de Material Rede �gua
	 */
	public Collection<MaterialCavaleteAgua> pesquisarMaterialCavaleteAgua() throws ControladorException;

	/**
	 * @author isilva
	 * @param idsOS
	 * @return
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdensServicos(List<Integer> idsOS) throws ControladorException;

	/**
	 * @author isilva
	 * @param idOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<RoteiroOSDadosProgramacaoHelper> pesquisarProgramacaoOrdemServicoRoteiroEquipe(Integer idOrdemServico)
					throws ControladorException;

	/**
	 * Retorna o maior Sequ�ncial da Ordem de Servi�o Programa��o, de uma Data para uma Equipe.
	 * 
	 * @param idEquipe
	 * @param dtRoteiro
	 * @return
	 * @throws ControladorException
	 */
	public Integer maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(Integer idEquipe, Date dtRoteiro) throws ControladorException;

	/**
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Anderson Lima
	 * @date 13/03/2010
	 *       Pesquisar o total de registros a serem processados
	 */
	public Integer filtrarImovelEmissaoOrdensSeletivasCount(OrdemServicoSeletivaHelper ordemServicoSeletivaHelper)
					throws ControladorException;

	/**
	 * Faz a reprograma��o das ordens de servi�o passadas como par�metro para a data e equipe
	 * passadas tamb�m.
	 * 
	 * @param ordensServico
	 * @param novaEquipe
	 * @param dataRoteiro
	 *            - a data atual do roteiro
	 * @param novaDataRoteiro
	 *            - a nova data do roteiro
	 * @param usuario
	 *            - usu�rio do sistema que fez a reprograma��o
	 * @throws ControladorException
	 * @author Rodrigo Oliveira
	 */
	public void reprogramarOrdensServico(List<OrdemServico> ordensServico, Equipe novaEquipe, Date dataRoteiro, Date novaDataRoteiro,
					Usuario usuario) throws ControladorException;

	/**
	 * @param idCobrancaDocumento
	 * @return
	 * @throws ControladorException
	 */
	public OrdemServico pesquisarOSPorDocumentoCobranca(Integer idCobrancaDocumento) throws ControladorException;

	public List<OrdemServico> listarOrdensServicoOrdenadasPorInscricao(List<Integer> listaIdentificadoresOS) throws ControladorException;

	/**
	 * Permite inserir os Dados para o processo de Encerramento de O.S
	 * 
	 * @author Ailton Sousa
	 * @date 13/05/2011
	 */
	public Collection<OcorrenciaInfracaoItem> inserirOcorrenciaInfracao(OcorrenciaInfracao ocorrenciaInfracao, Integer[] infracaoTipo)
					throws ControladorException;

	/**
	 * [UC0947] - Gerar San��o por Infra��o
	 * 
	 * @param numeroOS
	 * @param listaInfracoesItem
	 * @throws ControladorException
	 */
	public void gerarSancaoInfracao(int numeroOS, Collection<OcorrenciaInfracaoItem> listaInfracoesItem) throws ControladorException;

	/**
	 * Recupera a Ocorrencia Infracao pelo nnDoctoInfracao e nnAnoDoctoInfracao.
	 * 
	 * @param nnDoctoInfracao
	 * @param nnAnoDoctoInfracao
	 * @return OcorrenciaInfracao
	 * @throws ControladorException
	 */
	public OcorrenciaInfracao recuperarOcorrenciaInfracao(Integer nnDoctoInfracao, Integer nnAnoDoctoInfracao) throws ControladorException;

	/**
	 * Gera a Sansao para a Baixa em Lote da Ordem de Servi�o
	 * 
	 * @param criticaOSLoteHelper
	 * @throws ControladorException
	 */
	public void gerarSansaoBaixaEmLoteOS(CriticaOSLoteHelper criticaOSLoteHelper) throws ControladorException;

	/**
	 * @author isilva
	 * @date 19/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<OSRelatorioPadraoComOcorrenciaHelper> pesquisarOSRelatorioPadraoComOcorrencia(Collection idsOrdemServico)
					throws ControladorException;

	/**
	 * Cabe�alhos dos Relat�rios
	 * 
	 * @author isilva
	 * @date 24/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<OSRelatorioEstruturaHelper> pesquisarOSRelatorioEstruturaCabecalho(Collection idsOrdemServico)
					throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio Resumo de Ordem de Servi�o Encerradas
	 * Obter dados para gerar Relat�rio Resumo de Ordem de Servi�o Encerradas
	 * 
	 * @author Anderson Italo
	 * @date 13/05/2011
	 */
	public Collection pesquisaRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio Resumo de Ordem de Servi�o Encerradas
	 * Obter total de registros do Relat�rio Resumo de Ordem de Servi�o Encerradas
	 * 
	 * @author Anderson Italo
	 * @date 13/05/2011
	 */
	public Integer pesquisaTotalRegistrosRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException;

	/**
	 * [UC1015] Relat�rio Ordens de Servi�o Pendentes
	 * Obter dados para gera��o relat�rio.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioResumoOrdensServicoPendentesHelper> pesquisaRelatorioResumoOSPendentes(
					FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro) throws ControladorException;

	/**
	 * [UC1015] Relat�rio Ordens de Servi�o Pendentes
	 * Obt�m o total de registros do relat�rio.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistrosRelatorioResumoOSPendentes(FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro)
					throws ControladorException;

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Hebert Falc�o
	 * @date 19/07/2011
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idUnidadeOrganizacional, Integer idOrdemServico,
					Integer idEquipe, Date dataRoteiro) throws ControladorException;

	/**
	 * [UC3040] Obter Unidade Atual da OS
	 * Este m�todo permite obter a unidade atual de uma ordem de servi�o
	 * 
	 * @author Anderson Italo
	 * @date 08/02/2012
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeAtualOrdemServico(Integer idOrdemServico) throws ControladorException;

	/**
	 * @author Ailton Sousa
	 * @date 10/02/2012
	 * @param numeroOS
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarOSProgramacaoAtiva(Integer numeroOS) throws ControladorException;

	/**
	 * [UC0620] - Obter Indicador de Autoriza��o para Manuten��o da OS
	 * Este m�todo permite obter o indicador de autoriza��o para manuten��o da OS
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ControladorException
	 */
	public Short obterIndicadorAutorizacaoParaManutencaoOrdemServico(Usuario usuario, Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC3039] Tramitar Ordem de Servi�o
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param idOS
	 * @return
	 * @throws ControladorException
	 */
	public Tramite pesquisarUltimaDataTramiteOS(Integer idOS) throws ControladorException;

	/**
	 * [UC3039] Tramitar Ordem de Servi�o
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param tramite
	 * @param dataConcorrente
	 * @throws ControladorException
	 */
	public void tramitarOS(Tramite tramite, Date dataConcorrente) throws ControladorException;

	/**
	 * [UC3039] Tramitar Ordem de Servi�o
	 * Faz as valida��es para realizar o Tr�mite da OS.
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param tramite
	 * @param usuario
	 * @throws ControladorException
	 */
	public void validarTramitacaoOS(Tramite tramite, Usuario usuario) throws ControladorException;

	/**
	 * [UC3036] Obter Unidade Tramite Ordem de Servi�o
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
					Integer idBairro, Integer idUnidadeOrigem) throws ControladorException;

	/**
	 * [UC00503]Tramitar Conjunto de Ordem de Servico
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2012
	 */
	public void tramitarConjuntoOSRA(Collection tramites, Usuario usuario) throws ControladorException;

	/**
	 * [UC0427] Tramitar Ordem de Servi�o a partir do Registro de Atendimento
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2016
	 * @param tramite
	 * @param dataConcorrente
	 */
	public void tramitarOSRA(Tramite tramite, Usuario usuario) throws ControladorException;

	/**
	 * Consultar Id de Servi�o Tipo com Gera��o Autom�tica filtrando pelo Id da Solicita��o Tipo
	 * Especifica��o
	 * 
	 * @author Hebet Falc�o
	 * @date 17/02/2012
	 */
	public Collection<Integer> consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(Integer idSolicitacaoTipoEspecificacao)
					throws ControladorException;

	/**
	 * [UC0458] Imprimir Ordem de Servi�o em Txt
	 * 
	 * @author Hebert Falc�o
	 * @date 19/03/2012
	 */
	public StringBuilder imprimirOrdemServicoTxt(Collection<OrdemServico> colecaoOrdemServico, String nomeMetodoGeracaoArquivoTxt)
					throws ControladorException;

	/**
	 * [UC0430] Gerar Ordem de Servi�o
	 * [FS0010] - Verificar restri��o de emiss�o da Ordem de Servi�o
	 * 
	 * @author Hugo Lima
	 * @date 06/08/2012
	 * @param usuario
	 * @param ordemServico
	 * @param indicadorFluxoPassaParaProximoImovel
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarRestricaoEmissaoOrdemServico(Usuario usuario, OrdemServico ordemServico,
					Short indicadorFluxoPassaParaProximoImovel) throws ControladorException;

	/**
	 * [UC3063] Religacao instalacao/Substituicao Registro Magnetico
	 * 
	 * @author Leonardo Angelim
	 * @date 27/08/2012
	 */
	public void validarOrdemServicoRegistroMagnetico(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC3064] Religacao instalacao/Substituicao Tubete Magnetico
	 * 
	 * @author Leonardo Angelim
	 * @date 27/08/2012
	 */
	public void validarOrdemServicoTubeteMagnetico(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC3063] Efetuar Instala��o/Substitui��o de Registro Magn�tico
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */
	public OrdemServico recuperaOSPorIdRegistroMagnetico(Integer idOS) throws ControladorException;

	/**
	 * [UC3064] Religacao Agua com Instalacao de Tubete Magnetico
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */
	public OrdemServico recuperaOSPorIdTubeteMagnetico(Integer idOS) throws ControladorException;

	/**
	 * [UC0468] Consultar Ordem de Servi�o
	 * [SB0002] � Habilitar Emiss�o da OS
	 * Retorna <true> se a op��o de Imprimir OS deve estar habilitada. Retorna <false>, caso
	 * contr�rio
	 * 
	 * @author Luciano Galvao
	 * @throws ControladorException
	 * @date 17/12/2012
	 */
	public boolean verificarHabilitacaoEmissaoOS(OrdemServico ordemServico) throws ControladorException;

	/**
	 * Recupera o tipo de rela��o do cliente e im�vel para exibi��o no relat�rio de ordem de
	 * servi�o.
	 * 
	 * @param idOSLayout
	 * @date 26/12/2012
	 * @author �talo Almeida
	 */
	public String recuperaRelacaoOSClienteImovel(Integer idOSLayout) throws ControladorException;

	/**
	 * [UC3096] AcquaGIS GetOS
	 * 
	 * @param filtro
	 * @return
	 * @throws ControladorException
	 */
	public Collection<OrdemServicoJSONHelper> pesquisarOrdemServicoWebService(FiltrarOrdemServicoHelper filtro) throws ControladorException;

	/**
	 * [UC3096] AcquaGIS GetDetalheOS
	 * 
	 * @param filtro
	 * @return
	 * @throws ControladorException
	 */
	public OrdemServicoDetalhesJSONHelper pesquisarOrdemServicoDetalhesWebService(FiltrarOrdemServicoHelper filtro)
					throws ControladorException;

	/**
	 * [UC3116] Filtrar Comando OS Seletiva
	 * 
	 * @param filtro
	 * @return
	 * @throws ControladorException
	 */
	public Collection filtrarComandoOSSeletiva(OrdemServicoSeletivaComandoHelper ordemServicoSeletivaComandoHelper)
					throws ControladorException;

	public Collection filtrarDadosRelatorioComandoOSSeletiva(OrdemServicoSeletivaComandoHelper ordemServicoSeletivaComandoHelper)
					throws ControladorException;

	/**
	 * [UC0454] Obter Descri��o da situa��o da OS
	 * Verificar a situa��o(ORSE_CDSITUACAO) da ordem de servi�o
	 * 
	 * @author Vicente Zarga
	 * @date 25/11/2013
	 * @param idOS
	 * @throws ControladorException
	 */
	public Short verificaSituacaoOS(Integer idOrdemServico) throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio de Ordem de Servi�o Encerradas Dentro e Fora do Prazo
	 * Obter dados para gerar Relat�rio de Ordem de Servi�o Encerradas Dentro e Fora do Prazo
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 */
	public Collection pesquisaRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ControladorException;

	/**
	 * [UC0XXX] Gerar Relat�rio de Ordem de Servi�o Encerradas Dentro e Fora do Prazo
	 * Obter dados para gerar Relat�rio de Ordem de Servi�o Encerradas Dentro e Fora do Prazo
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 */
	public Integer pesquisaTotalRegistrosRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ControladorException;

	/**
	 * @param imovelEmissaoOrdensSeletivaHelper
	 * @param usuarioLogado
	 * @return
	 */

	public Object[] gerarRelatorioOrdemSeletiva(ImovelEmissaoOrdensSeletivaHelper imovelEmissaoOrdensSeletivaHelper, Usuario usuarioLogado)
					throws ControladorException;


	/**
	 * Recupera a cole��o Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Cole��o de EntuhoMedida
	 */
	public Collection<EntulhoMedida> pesquisarEntulhoMedida() throws ControladorException;

	/**
	 * Recupera um Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Entulho Medida
	 */
	public EntulhoMedida pesquisarEntulhoMedida(Integer idEntulhoMedida) throws ControladorException;

	public Boolean validarGerarGuiaPagamentoOS(Integer idServicoTipo, Integer idRegistroAtendimento) throws ControladorException;

	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaPorOS(Integer idOS) throws ControladorException;

	public Integer recuperaQuantidadeDiasUnidade(Integer idOS, Short permiteTramiteIndependente) throws ControladorException;
}
