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

package gcom.atendimentopublico;

import gcom.atendimentopublico.bean.DadosLigacoesBoletimCadastroHelper;
import gcom.atendimentopublico.bean.EfetuarLigacaoAguaComInstalacaoHidrometroSemRAHelper;
import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.bean.PercentualCobrancaHelper;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.atendimentopublico.registroatendimento.EspecificacaoImovSitCriterio;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.relatorio.atendimentopublico.RelatorioCertidaoNegativaClienteBean;
import gcom.relatorio.atendimentopublico.RelatorioCertidaoNegativaHelper;
import gcom.relatorio.atendimentopublico.RelatorioContratoPrestacaoServicoJuridicoBean;
import gcom.relatorio.cadastro.imovel.RelatorioMateriaisAplicadosHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorAtendimentoPublicoLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * [UC353]Efetuar Liga��o de Esgoto no sistema. [SB002] Atualizar dados do
	 * im�vel.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 14/06/2006
	 * @param ligacaoEsgoto
	 *            a ser enserido
	 * @param imovel
	 *            a ser atualizado
	 * @throws ControladorException
	 */

	public void efetuarLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0367]Atualizar Liga��o de Agua no sistema.
	 * [SB002] Atualiza liga��o de agua.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param ligacaoAgua
	 * @throws ControladorException
	 */
	public void atualizarLigacaoAgua(LigacaoAgua ligacaoAgua) throws ControladorException;

	/**
	 * [UC353]Efetuar Liga��o Esgoto no sistema.
	 * [SB002] Atualizar dados doim�vel.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 20/06/2006
	 * @param ligacaoEsgoto
	 *            a ser enserido
	 * @param imovel
	 *            a ser atualizado
	 * @throws ControladorException
	 */
	public void inserirLigacaoEsgoto(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * inser��o da especificacao situacao criterio imovel.
	 * [FS0001] Validar especifica��o da situa��oo j� existente [FS0002] Validar
	 * exist�ncia de hidr�metro na liga��o �gua [FS0003] Validar exist�ncia de
	 * hidr�metro no po�o
	 * 
	 * @author Rafael Pinto
	 * @date 04/08/2006
	 * @param equipeComponentes
	 */
	public void validarExibirInsercaoEspecificacaoImovSitCriterio(Collection colecaoEspecificacaoImovSitCriterio,
					EspecificacaoImovSitCriterio especImovSitCriterio) throws ControladorException;

	/**
	 * [UC0365] Efetuar Remanejamento de Hidr�metro [SB0001] Atualizar Hit�rico
	 * de instala��o do hidr�metro
	 * 
	 * @author R�mulo Aur�lio
	 * @date 30/06/2006
	 * @param hidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */

	public void efetuarRemanejamentoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0365] Efetuar Retirada de Hidr�metro [SB0001] Atualizar Hit�rico de
	 * instala��o do hidr�metro
	 * 
	 * @author Thiago Ten�rio
	 * @date 30/06/2006
	 * @param hidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */
	public void efetuarRetiradaHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0362] Efetuar Instala��o de Hidr�metro
	 * [SB0001] Gerar Hit�rico de instala��o do hidr�metro [SB0002] Atualizar
	 * Im�vel/Liga��o de �gua [SB0003] Atualizar situa��o de hidr�metro na
	 * tabela HIDROMETRO
	 * 
	 * @author Ana Maria
	 * @date 12/07/2006
	 * @param hidrometroInstalacaoHistorico
	 * @param materialImovel
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	public void efetuarInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * @author anishimura
	 * @author lmedeiros
	 * @param inputStream
	 * @param usuario
	 * @throws ControladorException
	 *             efetua a instala�ao de varios hidrometros
	 */
	public Collection[] efetuarInstalacaoHidrometroEmLote(InputStream inputStream, Usuario usuario) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * liga��o de agua do im�vel no momento da exibi��o.
	 * [FS0001] Verificar exist�ncia da matr�cula do im�vel. [FS0002] Verificar
	 * Situa��o do Imovel. [FS0003] Validar Situa��o de Esgoto do im�vel.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param Imovel
	 */
	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * liga��o de agua do im�vel no momento da exibi��o.
	 * [FS0001] Verificar exist�ncia da matr�cula do im�vel. [FS0002] Verificar
	 * Situa��o do Imovel. [FS0003] Validar Situa��o de Esgoto do im�vel.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param Imovel
	 */
	public void validarExibirLigacaoAguaImovel(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0356] Efetuar Mudan�a de Situa��o de Faturamento da Liga��o de Esgoto
	 * Permite Efetuar Mudan�a de Situa��o de Faturamento da Liga��o de Esgoto .
	 * [FS0001]- Validar Ordem de Servi�o [FS0002] Verificar Situa��o do Imovel
	 * [FS0002] Verificar Situa��o do Imovel [FS0003]- Validar Situa��o da
	 * Liga��o de Esgoto do im�vel * [FS0001]- Validar Ordem de Servi�o [FS0002]
	 * Verificar Situa��o do Imovel [FS0002] Verificar Situa��o do Imovel
	 * [FS0003]- Validar Situa��o da Liga��o de Esgoto do im�vel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param ordemServicoId
	 * @param imovel
	 * @param dataMudanca
	 * @param volumeMinimoFixado
	 * @param novaSituacaoEsgoto
	 * @throws ControladorException
	 */
	public void efetuarMudancaSituacaoFaturamentoLiagacaoEsgoto(IntegracaoComercialHelper integracaoComercialHelper)
					throws ControladorException;

	/**
	 * [UC0356]- Efetuar mudan�a de Faturamento na Liga��o de �gua
	 * [FS0006]-Atualizar Liga��o de Esgoto
	 * Permite atualizar a Tabele de Liga��o Esdoto . Update LIGACAO_ESGOTO
	 * LESG_NNCONSUMOMINIMOESGOTO (volume m�nimo fixado) LESG_TMULTIMAALTERADAO
	 * (data e hora correntes) Where LESG_ID=IMOV_ID da tabela IMOVEL
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param imovel
	 * @param volumeMinimoFixado
	 * @throws ControladorException
	 */
	public void atualizarLigacaoEsgoto(Imovel imovel, String volumeMinimoFixado) throws ControladorException;

	/**
	 * [UC0356]- Efetuar mudan�a de Faturamento na Liga��o de �gua
	 * [FS0007]- Validar Situa��o da Liga��o de �gua do im�vel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param imovel
	 * @param volumeMinimoFixado
	 * @throws ControladorException
	 */
	public String validarSituacaoAguaImovel(Imovel imovel, Integer tipoServico) throws ControladorException;

	/**
	 * [UC0356] Efetuar Mudan�a de Situa��o de Faturamento da Liga��o de Esgoto
	 * Permite Efetuar Mudan�a de Situa��o de Faturamento da Liga��o de Esgoto .
	 * [FS0001]- Validar Ordem de Servi�o [FS0002] Verificar Situa��o do Imovel
	 * [FS0002] Verificar Situa��o do Imovel [FS0003]- Validar Situa��o da
	 * Liga��o de Esgoto do im�vel [FS0007]- Validar Situa��o da Liga��o de �gua
	 * do im�vel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param ordemServicoId
	 * @param imovel
	 * @param dataMudanca
	 * @param volumeMinimoFixado
	 * @param novaSituacaoEsgoto
	 * @throws ControladorException
	 */
	public String validarMudancaSituacaoFaturamentoLigacaoesgotoExibir(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC0364] Efetuar Substitui��o de Hidr�metro
	 * [SB0001] Atualiza o Hist�rico da instala��o com os dados do hidr�metro
	 * substituido [SB0002] Gerar Hit�rico de instala��o do hidr�metro [SB0003]
	 * Atualizar Im�vel/Liga��o de �gua [SB0004] Atualizar situa��o de
	 * hidr�metro na tabela HIDROMETRO [SB0005] Atualizar situa��o do hidr�metro
	 * substituido na tabela HIDROMETRO
	 * 
	 * @author Ana Maria
	 * @date 24/07/2006
	 * @param hidrometroInstalacaoHistorico
	 * @param materialImovel
	 * @param hidrometroSubstituicaoHistorico
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void efetuarSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0360]- Efetuar Supress�o da Liga��o de �gua
	 * [SB0001]- Atualizar Liga��o de �gua [SB0002]- Atualizar Im�vel [SB0004]-
	 * Atualizar Hist�tico de Instala��o de Hidr�metro
	 * 
	 * @author R�mulo Aur�lio
	 * @date 28/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void efetuarSupressaoLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper, boolean retirarHidrometro)
					throws ControladorException;

	/**
	 * [UC0368] Atualizar Instala��o do Hidr�metro
	 * [FS0001] - Verificar a exist�ncia da matr�cula do im�vel [FS0002] -
	 * Verificar a situa��o do im�vel [FS0003] - Validar exist�ncia do
	 * hidr�metro [FS0004] - Validar leitura instala��o hidr�metro [FS0005] -
	 * Validar leitura retirada hidr�metro [FS0006] - Validar leitura retirada
	 * corte [FS0007] - Validar Leitura Supress�o [FS0009] - Verificar sucesso
	 * da transa��o
	 * 
	 * @author lms
	 * @created 21/07/2006
	 * @throws ControladorException
	 */
	public void atualizarInstalacaoHidrometro(Imovel imovel, Integer medicaoTipo) throws ControladorException;

	public void atualizarInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0359] Efetuar Restabelecimento Liga��o de �gua
	 * [SB0001] Atualizar Im�vel/Liga��o de �gua/Liga��o de Esgoto
	 * 
	 * @author R�mulo Aur�lio
	 * @date 12/07/2006
	 * @param idImovel
	 *            ,idOrdemServico
	 * @throws ControladorException
	 */

	public void efetuarRestabelecimentoLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0396] Inserir Tipo de Retorno da OS Referida
	 * [FS0003] - Validar atendimento do motivo de encerramento.
	 * 
	 * @author lms
	 * @created 21/07/2006
	 * @throws ControladorException
	 */
	public void validarAtendimentoMotivoEncerramento(OsReferidaRetornoTipo osReferidaRetornoTipo) throws ControladorException;

	/**
	 * [UC0396] Inserir Tipo de Retorno da OS Referida
	 * [FS0002] - Solicitar o indicador de troca de servi�o, situa��o e motivo
	 * de encerramento [FS0003] - Validar atendimento do motivo de encerramento
	 * [FS0005] - Validar indicador de deferimento [FS0006] - Validar indicador
	 * de deferimento x indicador de troca de servi�o [FS0007] - Verificar
	 * sucesso da transa��o
	 * 
	 * @author lms
	 * @created 21/07/2006
	 * @throws ControladorException
	 */
	public Integer inserirOSReferidaRetornoTipo(OsReferidaRetornoTipo osReferidaRetornoTipo) throws ControladorException;

	/**
	 * [UC0354] Efetuar Liga��o de �gua.
	 * Permite validar liga��o de �gua Exibir ou pelo menu ou pela
	 * funcionalidade encerrar a execu��o da ordem de ser�o.
	 * [FS0008] Verificar Situa��o Rede de �gua na Quadra. [FS0007] Verificar
	 * Situa��o do Imovel. [FS0002] Validar Situa��o de �gua do Im�vel
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0353] Efetuar Liga��o de Esgoto.
	 * Permite validar liga��o de esgoto Exibir ou pelo menu ou pela
	 * funcionalidade encerrar a execu��o da ordem de ser�o.
	 * [FS0008] Verificar Situa��o Rede de Esgoto na Quadra. [FS0007] Verificar
	 * Situa��o do Imovel. [FS0002] Validar Situa��o de Esgoto do Im�vel
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoEsgotoExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0354] Efetuar Liga��o de �gua.
	 * Permite validar liga��o de �gua Efetuar ou pelo menu ou pela
	 * funcionalidade encerrar a execu��o da ordem de ser�o.
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaEfetuar(Imovel imovel, LigacaoAgua ligacaoAgua) throws ControladorException;

	/**
	 * [UC0381] Inserir Material com Unidade
	 * Permite a inclusao de um novo material
	 * [SB0001] Gerar Material com Unidade
	 * 1.1Inclui o material na tabela Material
	 * 
	 * @author R�mulo Aur�lio
	 * @date 31/07/2006
	 * @param descricao
	 * @param descricaoAbreviada
	 * @param unidadeMaterial
	 * @throws ControladorException
	 */

	public Integer inserirMaterial(String descricao, String descricaoAbreviada, String unidadeMaterial, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0385] Inserir Tipo Perfil Servi�o
	 * 
	 * @author Ana Maria
	 * @date 01/08/2006
	 * @param servicoPerfilTipo
	 * @throws ControladorException
	 */
	public Integer inserirServicoTipoPerfil(ServicoPerfilTipo servicoPerfilTipo) throws ControladorException;

	/**
	 * [UC0436] Inserir Tipo de Servi�o de Refer�ncia.
	 * Permite a inclus�o de um tipo de servi�o de refer�ncia.
	 * [FS0003] Validar indicador de existencia x Situa��o da Os de referencia
	 * 
	 * @author R�mulo Aur�lio.
	 * @date 05/08/2006
	 * @param servicoTipoReferencia
	 * @throws ControladorException
	 */

	public Integer inserirTipoServicoReferencia(ServicoTipoReferencia servicoTipoReferencia, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * [FS0004] - Validar Perfil do Servi�o
	 * 
	 * @author lms
	 * @date 01/08/2006
	 */
	public ServicoPerfilTipo pesquisarServicoPerfilTipo(Integer idServicoPerfilTipo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * [FS0005] - Validar Tipo de Servi�o de Refer�ncia
	 * 
	 * @author lms
	 * @date 02/08/2006
	 */
	public ServicoTipoReferencia pesquisarServicoTipoReferencia(Integer idServicoTipoReferencia) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * [FS0009] - Validar Atividade
	 * 
	 * @author lms
	 * @date 05/08/2006
	 */
	public Atividade pesquisarAtividade(Integer idAtividade, String atividadeUnica) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * [FS0006] - Validar Ordem de Execu��o
	 * 
	 * @author lms
	 * @date 05/08/2006
	 */
	public void validarOrdemExecucao(Collection colecaoServicoTipoAtividade, Short ordemExecucao) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public Integer inserirServicoTipo(ServicoTipo servicoTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0412] - Atualizar Tipo de Servi�o
	 * 
	 * @author vsm
	 * @date 10/12/2008
	 */
	public Integer atualizarServicoTipo(ServicoTipo servicoTipo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public ServicoTipoSubgrupo pesquisarServicoTipoSubgrupo(Integer idServicoTipoSubgrupo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public ServicoTipoPrioridade pesquisarServicoTipoPrioridade(Integer idServicoTipoPrioridade) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * [FS0010] - Validar Material
	 * 
	 * @author lms
	 * @date 08/08/2006
	 */
	public Material pesquisarMaterial(Integer idMaterial) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public void validarAdicionarAtividade(Collection colecaoServicoTipoAtividade, Integer idAtividade) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public void validarAdicionarMaterial(Collection colecaoServicoTipoMaterial, Integer idMaterial) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Servi�o - Validar Adicionar Servi�o Associado
	 * 
	 * @author Virg�nia Melo
	 * @date 14/05/2009
	 */
	public void validarAdicionarServicoAssociado(Collection colecaoServicoAssociado, Integer idServico) throws ControladorException;

	/**
	 * [UC0449] Inserir Prioridade do Tipo de Servi�o
	 * Permite a inclus�o de uma prioridade do tipo de servi�o.
	 * [FS0001] Verificar existencia da descri��o [FS0003]- Verificar exist�ncia
	 * da descri��o abreviada [FS0002] Validar quantidade de horas in�cio e
	 * quantidade de horas fim
	 * 
	 * @author R�mulo Aur�lio.
	 * @date 11/08/2006
	 * @param servicoTipoPrioridade
	 * @throws ControladorException
	 */
	public Integer inserirPrioridadeTipoServico(ServicoTipoPrioridade servicoTipoPrioridade, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades do
	 * supressao liga��o de agua
	 * 
	 * @author Rafael Pinto
	 * @date 28/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirSupressaoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades do
	 * restabelecimento liga��o de agua
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirRestabelecimentoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades de
	 * religa��o de �gua
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirReligacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades de
	 * corte adimistrativo de liga��o de �gua
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirCorteAdministrativoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades do
	 * substitui��o de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 31/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirSubstituicaoHidrometro(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * retirada de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirRetiradaHidrometroAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * remanejamento de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirRemanejmentoHidrometroAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0362] Efetuar Instalacao de Hidr�metro
	 * Validar Instalacao de Hidr�metro
	 * 
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param matriculaImovel
	 *            ,
	 * @param numeroHidrometro
	 *            ,
	 * @param tipoMedicao
	 *            return void
	 * @throws ControladorException
	 */
	public void validarExibirInstalacaoHidrometro(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * atualiza��o da instala��o de hidr�metro do im�vel no momento da exibi��o.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirAtualizarInstalacaoHidrometro(OrdemServico ordemServico, boolean menu) throws ControladorException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param servicoTipoId
	 * @param imovelId
	 * @param tipoMedicao
	 * @return valor do d�bito
	 * @throws ControladorException
	 */
	public BigDecimal obterValorDebito(Integer servicoTipoId, Integer imovelId, Short tipoMedicao) throws ControladorException;

	/**
	 * M�todo que retorna o n�mero do hidr�metro da liga��o de �gua
	 * 
	 * @throws ErroRepositorioException
	 */
	public String pesquisarNumeroHidrometroLigacaoAgua(Integer idLigacaoAgua) throws ControladorException;

	/**
	 * M�todo que retorna o tipo da liga��o de �gua e a data do corte da liga��o
	 * de �gua
	 * 
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLigacaoAgua(Integer idLigacaoAgua) throws ControladorException;

	/**
	 * [UC0357] Efetuar Religa��o de �gua
	 * Permite efetuar religa��o da liga��o de �gua ou pelo menu ou pela
	 * funcionalidade encerrar a execu��o da ordem de servi�o.
	 * [SB0001] Atualizar Im�vel/Liga��o de �gua/Liga��o de Esgoto
	 * 
	 * @author R�mulo Aur�lio
	 * @date 07/07/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * Consulta os dados das ordens de servi�o para a gera��o do relat�rio
	 * 
	 * @author Rafael Corr�a
	 * @created 07/10/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOrdemServicoProgramacaoRelatorio(Integer idEquipe, Date dataRoteiro) throws ControladorException;

	/**
	 * [UC0364] Efetuar Substitui��o de Hidr�metro
	 * Validar Substitui��o de Hidr�metro
	 * 
	 * @author Ana Maria
	 * @date 25/07/2006
	 * @param matriculaImovel
	 *            ,
	 * @param numeroHidrometro
	 *            ,
	 * @param situacaoHidrometroSubstituido
	 *            return void
	 * @throws ControladorException
	 */
	public void validacaoSubstituicaoHidrometro(String matriculaImovel, String numeroHidrometro, String situacaoHidrometroSubstituido)
					throws ControladorException;

	/**
	 * [UC0362] Efetuar Instalacao de Hidr�metro
	 * Validar Instalacao de Hidr�metro
	 * 
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param matriculaImovel
	 *            ,
	 * @param numeroHidrometro
	 *            ,
	 * @param tipoMedicao
	 *            return void
	 * @throws ControladorException
	 */
	public void validacaoInstalacaoHidrometro(String numeroHidrometro) throws ControladorException;

	/**
	 * [UC0387] Manter Tipo Perfil Servi�o
	 * [SB0001] Atualizar Tipo Perfil Servi�o
	 * 
	 * @author Kassia Albuquerque
	 * @date 01/11/2006
	 * @pparam servicoPerfilTipo
	 * @throws ControladorException
	 */
	public void atualizarServicoTipoPerfil(ServicoPerfilTipo servicoPerfilTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Este m�todo valida os dados que s�o necessarios para a
	 * inser��o do servi�o tipo referencia.
	 * 
	 * @author Fl�vio Leonardo
	 * @date 31/10/2006
	 * @param servicoTipoReferencia
	 * @return
	 * @throws ControladorException
	 */
	public void validarTipoServicoReferenciaParaInsercao(ServicoTipoReferencia servicoTipoReferencia) throws ControladorException;

	/**
	 * [UC0387] Manter Tipo Perfil Servi�o
	 * [SB0002] Remover Tipo Perfil Servi�o
	 * 
	 * @author Kassia Albuquerque
	 * @date 08/11/2006
	 * @pparam servicoPerfilTipo
	 * @throws ControladorException
	 */
	public void removerServicoTipoPerfil(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0404] Manter Especifica��o da Situa��o do Imovel
	 * Este caso de uso remove a especifica��o e os crit�rio
	 * [SB0002] Remover Especifica��o da situacao
	 * 
	 * @author Rafael Pinto
	 * @created 08/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerEspecificacaoSituacaoImovel(String[] idsEspecificacaoSituacaoImovel, Usuario usuario, Date ultimaAlteracao)
					throws ControladorException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Verificar exist�ncia de hidr�metro na liga��o de �gua.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmLigacaoAgua(Integer imovelId) throws ControladorException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Verificar exist�ncia de hidr�metro na liga��o de �gua.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmImovel(Integer imovelId) throws ControladorException;

	/**
	 * [UC0383] Manter Material
	 * [SB0003] Remover Material
	 * 
	 * @author Kassia Albuquerque
	 * @date 16/11/2006
	 * @pparam material
	 * @throws ControladorException
	 */
	public void removerMaterial(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0383] Manter Material
	 * [SB0001] Atualizar Material
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/11/2006
	 * @pparam material
	 * @throws ControladorException
	 */
	public void atualizarMaterial(Material material, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0498] Efetuar Liga��o de �gua com Instala��o de Hidr�metro.
	 * Permite validar o efetuar liga��o de �gua com Instala��o de Hidr�metro Exibir ou pelo menu ou
	 * pela
	 * funcionalidade encerrar a execu��o da ordem de ser�o.
	 * [FS0008] Verificar Situa��o Rede de �gua na Quadra. [FS0007] Verificar
	 * Situa��o do Imovel. [FS0002] Validar Situa��o de �gua do Im�vel
	 * 
	 * @author Rafael Corr�a
	 * @date 28/11/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0498] Efetuar Liga��o de �gua com Instala��o de Hidr�metro.
	 * Permite efetuar liga��o de �gua com Instala��o de Hidr�metr ou pelo menu
	 * ou pela funcionalidade encerrar a execu��o da ordem de ser�o.
	 * 
	 * @author Rafael Corr�a
	 * @date 29/11/2006
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarLigacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Permite efetuar a Liga��o de �gua com Instala��o de Hidrometro sem RA
	 * 
	 * @author Saulo Lima
	 * @since 12/02/2009
	 * @param LigacaoAgua
	 * @param HidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */
	public void efetuarLigacaoAguaComInstalacaoHidrometroSemRA(LigacaoAgua ligacaoAgua,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico) throws ControladorException;

	/**
	 * [UC0294] Prioridade Tipo Servico [] Atualizar Prioridade Tipo Servico
	 * 
	 * @author Thiago Ten�rio
	 * @date 25/05/2006
	 * @param Prioridade
	 *            Tipo Servico
	 * @throws ControladorException
	 */
	public void atualizarPrioridadeTipoServico(ServicoTipoPrioridade servicoTipoPrioridade, Collection colecaoServicoTipoPrioridade)
					throws ControladorException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * 
	 * @author Rafael Pinto
	 * @date 22/02/2007
	 * @param servicoTipoId
	 * @param imovelId
	 * @param tipoMedicao
	 * @param idHidrometroCapacidade
	 * @return valor do D�bito
	 * @throws ControladorException
	 */
	public BigDecimal obterValorDebito(Integer servicoTipoId, Integer imovelId, HidrometroCapacidade hidrometroCapacidade)
					throws ControladorException;

	/**
	 * [UC0555] Alterar Situacao da Ligacao
	 * 
	 * @author Romulo Aurelio
	 * @date 27/03/2007
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarOrdemServicoAlterarSituacaoLigacao(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0555] Alterar Situacao da Ligacao
	 * 
	 * @author Romulo Aurelio
	 * @date 27/03/2007
	 * @author Saulo Lima
	 * @date 20/05/2009
	 *       Novo parametro 'mapServicosAutorizados'
	 * @param imovel
	 * @param indicadorTipoLigacao
	 * @param idSituacaoLigacaoAguaNova
	 * @param idSituacaoLigacaoEsgotoNova
	 * @param idOrdemServico
	 * @param usuarioLogado
	 * @param usuarioLogado
	 * @return idImovel
	 * @throws ControladorException
	 */
	public Integer alterarSituacaoLigacao(Imovel imovel, String indicadorTipoLigacao, String idSituacaoLigacaoAguaNova,
					String idSituacaoLigacaoEsgotoNova, String idOrdemServico, Usuario usuarioLogado,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados) throws ControladorException;

	/**
	 * [UC0540] Efetuar Restabelecimento da Liga��o de �gua com Instala��o de hidr�metro.
	 * Permite validar o Efetuar Restabelecimento Liga��o de �gua com Instala��o de hidr�metro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execu��o da ordem
	 * de servi�o.
	 * [FS0008] Verificar Situa��o Rede de �gua na Quadra. [FS0007] Verificar
	 * Situa��o do Imovel. [FS0002] Validar Situa��o de �gua do Im�vel
	 * 
	 * @author Rafael Corr�a
	 * @date 18/04/2007
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarRestabelecimentoLigacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC0540] Efetuar Restabelecimento da Liga��o de �gua com Instala��o de hidr�metro.
	 * Permite efetuar o Restabelecimento Liga��o de �gua com Instala��o de Hidr�metro ou pelo menu
	 * ou pela funcionalidade encerrar a Execu��o da ordem de servi�o.
	 * 
	 * @author Rafael Corr�a
	 * @date 19/04/2007
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarRestabelecimentoLigacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper,
					Usuario usuario) throws ControladorException;

	/**
	 * Pesquisa todos os ids das situa��es de liga��o de �gua.
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoAgua() throws ControladorException;

	/**
	 * Pesquisa todos os ids das situa��es de liga��o de esgoto.
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoEsgoto() throws ControladorException;

	/**
	 * Este cso de uso permite efetuar a liga��o de �gua e eventualmente a
	 * instala��o de hidr�metro, sem informa��o de RA sendo chamado direto pelo
	 * menu.
	 * [UC0579] - Efetuar Liga��o de �gua com Intala��o de Hidr�metro
	 * 
	 * @author Fl�vio Leonardo
	 * @date 25/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public EfetuarLigacaoAguaComInstalacaoHidrometroSemRAHelper pesquisarEfetuarLigacaoAguaHidrometroSemRA(Integer idImovel)
					throws ControladorException;

	/**
	 * [UC0XXX] Gerar Contrato de Presta��o de Servi�o
	 * 
	 * @author Rafael Corr�a
	 * @date 03/05/2007
	 * @throws ControladorException
	 */
	public Collection obterDadosContratoPrestacaoServico(Integer idImovel) throws ControladorException;

	public void atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(Integer idImovel, Integer idHidrometro);

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Obt�m os dados necess�rio da liga��o de �gua, de esgoto e do hidr�metro
	 * instalado na liga��o de �gua
	 * 
	 * @author Rafael Corr�a
	 * @date 17/05/2007
	 * @throws ControladorException
	 */
	public DadosLigacoesBoletimCadastroHelper obterDadosLigacaoAguaEsgoto(Integer idImovel) throws ControladorException;

	/**
	 * [UC0587] Emitir Contrato de Prestacao de servico
	 * 
	 * @param idImovel
	 * @param idClienteEmpresa
	 * @return
	 * @throws ControladorException
	 */
	public RelatorioContratoPrestacaoServicoJuridicoBean gerarContratoJuridica(Integer idImovel, Integer idClienteEmpresa)
					throws ControladorException;

	/**
	 * [UC0608] Efetuar Liga��o de Esgoto sem RA.
	 * [FS0001] Verificar exist�ncia da matr�cula do Imovel.
	 * [FS0007] Verificar situa��o do im�vel.
	 * [FS0008] Verificar Situa��o Rede de Esgoto da Quadra.
	 * 
	 * @author S�vio Luiz.
	 * @date 10/09/2007
	 * @param imovel
	 * @throws ControladorException
	 */
	public String validarMatriculaImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0482]Emitir 2� Via de Conta
	 *obter numero do hidr�metro na liga��o de �gua.
	 * 
	 * @author Vivianne Sousa
	 * @date 11/09/2007
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public String obterNumeroHidrometroEmLigacaoAgua(Integer imovelId) throws ControladorException;

	/**
	 * [UC0475] Obter Valor do D�bito
	 * Obter Capacidade de Hidr�metro pela Liga��o de �gua.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou n�o
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmLigacaoAgua(Integer imovelId) throws ControladorException;

	/**
	 * [UC0353] Efetuar Liga��o de �gua.
	 * Permite efetuar Liga��o de �gua ou pelo menu ou pela funcionalidade
	 * encerrar a Execu��o da ordem de servi�o.
	 * [SB0001] Gerar Liga��o de �gua
	 * 
	 * @author eduardo henrique
	 * @date 18/03/2009
	 *       Altera��o no [SB0001] para determina��o autom�tica do Consumo m�nimo da Liga��o,
	 *       caso n�o tenha sido informado hidr�metro.
	 * @param ligacaoAgua
	 *            Instancia da nova Ligacao de Agua que ser� adicionada. (Deve vir com a instancia
	 *            de Imovel populada e HidromInstHistorico, caso exista)
	 * @throws ControladorException
	 */
	public void determinarConsumoMinimoNovaLigacaoAgua(LigacaoAgua ligacaoAgua) throws ControladorException;

	/**
	 * [UC0726] Gerar Relat�rio de Im�veis com Faturas em Atraso
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Collection<RelatorioImoveisSituacaoLigacaoAguaHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioCertidaoNegativaHelper> pesquisarRelatorioCertidaoNegativa(Imovel imo) throws ControladorException;

	/**
	 * Pesquisa os dados necess�rios para a gera��o do relat�rio
	 * [UC0864] Gerar Certid�o Negativa por Cliente
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioCertidaoNegativaClienteBean> pesquisarRelatorioCertidaoNegativaCliente(Collection<Integer> idsClientes,
					Cliente clienteInformado) throws ControladorException;

	/**
	 * [UC0498] Efetuar Religa��o de �gua com Instala��o de hidr�metro.
	 * Permite efetuar religa��o de �gua com Instala��o de Hidr�metro ou pelo
	 * menu ou pela funcionalidade encerrar a Execu��o da ordem de servi�o.
	 * 
	 * @author S�vio Luiz
	 * @date 29/01/2008
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Efetuar Religa��o de �gua com Substitui��o de hidr�metro.
	 * Permite efetuar religa��o de �gua com Substitui��o de Hidr�metro ou pelo
	 * menu ou pela funcionalidade encerrar a Execu��o da ordem de servi�o.
	 * 
	 * @author Luiz C�sar
	 * @date 22/01/2008
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAguaComSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Validar se o local de armazenagem � OFICINA (HILA_ICOFICINA = 1) e se a situa��o atual do
	 * hidr�metro for diferente de "EM MANUTEN��O"
	 * 
	 * @param idLocalArmazenagem
	 * @param idSituacaoHidrometro
	 * @throws ControladorException
	 */
	public void validarLocalArmazenagemSituacaoHidrometro(String idLocalArmazenagem, String idSituacaoHidrometro)
					throws ControladorException;

	/**
	 * [UC0747] Efetuar Religa��o de �gua com Instala��o de hidr�metro.
	 * Permite validar o efetuar religa��o de �gua com Instala��o de hidr�metro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execu��o da ordem
	 * de servi�o.
	 * [FS0002] Verificar Situa��o do Imovel. [FS0003] Validar Situa��o de �gua
	 * 
	 * @author S�vio Luiz
	 * @date 29/01/2008
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarReligacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [] Efetuar Religa��o de �gua com Substitui��o de Hidr�metro.
	 * Permite validar o efetuar religa��o de �gua com Substitui��o de Hidr�metro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execu��o da ordem
	 * de servi�o.
	 * [FS0002] Verificar Situa��o do Imovel. [FS0003] Validar Situa��o de �gua
	 * 
	 * @author Luiz C�sar
	 * @date 09/06/2010
	 * @param ordem
	 *            ,veioEncerrarOS
	 * @throws ControladorException
	 */
	public void validarReligacaoAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0747] Efetuar Corte de �gua com Instala��o de hidr�metro.
	 * Permite validar o efetuar corte de �gua com Instala��o de hidr�metro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execu��o da ordem
	 * de servi�o.
	 * [FS0002] Verificar Situa��o do Imovel. [FS0003] Validar Situa��o de �gua
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @param ordem
	 * @param veioEncerrarOS
	 * @throws ControladorException
	 */
	public void validarCorteAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * @author isilva
	 * @date 16/12/2010
	 * @param idHidrometro
	 * @param situacaoHidrometro
	 * @throws ControladorException
	 */
	public void atualizarSituacaoHidrometro(Integer idHidrometro, Integer situacaoHidrometro) throws ControladorException;

	/**
	 * [] Efetuar Corte de �gua com Substitui��o de Hidr�metro.
	 * Permite validar o efetuar Corte de �gua com Substitui��o de Hidr�metro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execu��o da ordem
	 * de servi�o.
	 * [FS0002] Verificar Situa��o do Imovel. [FS0003] Validar Situa��o de �gua
	 * 
	 * @author isilva
	 * @date 20/12/2010
	 * @param ordem
	 * @param veioEncerrarOS
	 * @throws ControladorException
	 */
	public void validarCorteAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Insere o HidrometroInstalacaoHistorico com HidrometroSituacao INSTALADO
	 * OBS: N�o Atualiza a OS, Efetua os Calculos,
	 * n�m Gera/acumula dados relativos aos documentos gerados
	 * 
	 * @author isilva
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public IntegracaoComercialHelper atualizarHidrometroInstalacaoHistoricoSemAtualizarOSEEfetuarCalculos(
					IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC] Efetuar Restabelecimento da Liga��o de �gua com Substitui��o de hidr�metro.
	 * Permite validar o Efetuar Restabelecimento Liga��o de �gua com Substitui��o de hidr�metro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execu��o da ordem
	 * de servi�o.
	 * [FS] Verificar Situa��o Rede de �gua na Quadra. [FS] Verificar
	 * Situa��o do Imovel. [FS] Validar Situa��o de �gua do Im�vel
	 * 
	 * @author isilva
	 * @date 22/12/2010
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC0540] Efetuar Restabelecimento da Liga��o de �gua com Substitui��o de hidr�metro.
	 * Permite efetuar o Restabelecimento Liga��o de �gua com Substitui��o de Hidr�metro ou pelo
	 * menu
	 * ou pela funcionalidade encerrar a Execu��o da ordem de servi�o.
	 * 
	 * @author isilva
	 * @date 28/12/2010
	 * @param integracaoComercialHelper
	 * @param usuario
	 * @throws ControladorException
	 */
	public void efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper,
					Usuario usuario) throws ControladorException;

	public Atividade verificarExistenciaAtividadeUnica(String atividadeUnica) throws ControladorException;

	/**
	 * Efetuar Instala��o/Substitui��o de Registro Magnetico.
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void efetuarInstalacaoSubstituicaoRegistroMagnetico(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Efetuar Instala��o/Substitui��o de Tubete Magnetico.
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void efetuarInstalacaoSubstituicaoTubeteMagnetico(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Efetuar Instala��o/Substitui��o de Registro Magnetico.
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void validarInstalacaoSubstituicaoRegistroMagneticoExibir(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC3064] Efetuar Instala��o/Substitui��o de Tubete Magn�tico
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void validarOSInstalacaoSubstituicaoTubeteMagnetico(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException;

	public Collection<PercentualCobrancaHelper> obterPercentuaisCobranca() throws ControladorException;

	public void verificarQuantidadeParcelas(Usuario usuario, Short qtdParcelas) throws ControladorException;

	public Short obterQuantidadeParcelasMaxima() throws ControladorException;

	public void validarExibirAtualizarPerfilLigacaoEsgoto(OrdemServico ordemServico, Boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC3135] Gerar Relat�rio de Materiais Aplicados
	 * 
	 * @author Felipe Rosacruz
	 * @date 03/02/2014
	 * @throws ControladorException
	 */
	public Collection<RelatorioMateriaisAplicadosHelper> obterDadosRelatorioMateriaisAplicados(Integer idLocalidade,
					Integer cdSetorComercial,
					Date dataExecucaoInicial,
					Date dataExecucaoFinal, Collection<Integer> colecaoIdServicoTipo, Collection<Integer> colecaoIdMaterial,
					Collection<Integer> colecaoIdEquipe) throws ControladorException;

	/**
	 * OC1213341 - Verificar se existe valor do Servi�o Tipo para a Localidade informada
	 * 
	 * @author Ado Rocha
	 * @date 23/04/2014
	 **/
	public BigDecimal verificarServicoTipoValorLocalidade(Integer idImovel, Integer idDebitoTipo) throws ControladorException;

}