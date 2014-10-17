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
 * Declaração pública de serviços do Session Bean de ControladorCliente
 * 
 * @author Sávio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorAtendimentoPublicoLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * [UC353]Efetuar Ligação de Esgoto no sistema. [SB002] Atualizar dados do
	 * imóvel.
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
	 * [UC0367]Atualizar Ligação de Agua no sistema.
	 * [SB002] Atualiza ligação de agua.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param ligacaoAgua
	 * @throws ControladorException
	 */
	public void atualizarLigacaoAgua(LigacaoAgua ligacaoAgua) throws ControladorException;

	/**
	 * [UC353]Efetuar Ligação Esgoto no sistema.
	 * [SB002] Atualizar dados doimóvel.
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
	 * Este método se destina a validar todas as situações e particularidades da
	 * inserção da especificacao situacao criterio imovel.
	 * [FS0001] Validar especificação da situaçãoo já existente [FS0002] Validar
	 * existência de hidrômetro na ligação água [FS0003] Validar existência de
	 * hidrômetro no poço
	 * 
	 * @author Rafael Pinto
	 * @date 04/08/2006
	 * @param equipeComponentes
	 */
	public void validarExibirInsercaoEspecificacaoImovSitCriterio(Collection colecaoEspecificacaoImovSitCriterio,
					EspecificacaoImovSitCriterio especImovSitCriterio) throws ControladorException;

	/**
	 * [UC0365] Efetuar Remanejamento de Hidrômetro [SB0001] Atualizar Hitórico
	 * de instalação do hidrômetro
	 * 
	 * @author Rômulo Aurélio
	 * @date 30/06/2006
	 * @param hidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */

	public void efetuarRemanejamentoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0365] Efetuar Retirada de Hidrômetro [SB0001] Atualizar Hitórico de
	 * instalação do hidrômetro
	 * 
	 * @author Thiago Tenório
	 * @date 30/06/2006
	 * @param hidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */
	public void efetuarRetiradaHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0362] Efetuar Instalação de Hidrômetro
	 * [SB0001] Gerar Hitórico de instalação do hidrômetro [SB0002] Atualizar
	 * Imóvel/Ligação de Água [SB0003] Atualizar situação de hidrômetro na
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
	 *             efetua a instalaçao de varios hidrometros
	 */
	public Collection[] efetuarInstalacaoHidrometroEmLote(InputStream inputStream, Usuario usuario) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * ligação de agua do imóvel no momento da exibição.
	 * [FS0001] Verificar existência da matrícula do imóvel. [FS0002] Verificar
	 * Situação do Imovel. [FS0003] Validar Situação de Esgoto do imóvel.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param Imovel
	 */
	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * ligação de agua do imóvel no momento da exibição.
	 * [FS0001] Verificar existência da matrícula do imóvel. [FS0002] Verificar
	 * Situação do Imovel. [FS0003] Validar Situação de Esgoto do imóvel.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param Imovel
	 */
	public void validarExibirLigacaoAguaImovel(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0356] Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto
	 * Permite Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto .
	 * [FS0001]- Validar Ordem de Serviço [FS0002] Verificar Situação do Imovel
	 * [FS0002] Verificar Situação do Imovel [FS0003]- Validar Situação da
	 * Ligação de Esgoto do imóvel * [FS0001]- Validar Ordem de Serviço [FS0002]
	 * Verificar Situação do Imovel [FS0002] Verificar Situação do Imovel
	 * [FS0003]- Validar Situação da Ligação de Esgoto do imóvel
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
	 * [UC0356]- Efetuar mudança de Faturamento na Ligação de Água
	 * [FS0006]-Atualizar Ligação de Esgoto
	 * Permite atualizar a Tabele de Ligação Esdoto . Update LIGACAO_ESGOTO
	 * LESG_NNCONSUMOMINIMOESGOTO (volume mínimo fixado) LESG_TMULTIMAALTERADAO
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
	 * [UC0356]- Efetuar mudança de Faturamento na Ligação de Água
	 * [FS0007]- Validar Situação da Ligação de Água do imóvel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param imovel
	 * @param volumeMinimoFixado
	 * @throws ControladorException
	 */
	public String validarSituacaoAguaImovel(Imovel imovel, Integer tipoServico) throws ControladorException;

	/**
	 * [UC0356] Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto
	 * Permite Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto .
	 * [FS0001]- Validar Ordem de Serviço [FS0002] Verificar Situação do Imovel
	 * [FS0002] Verificar Situação do Imovel [FS0003]- Validar Situação da
	 * Ligação de Esgoto do imóvel [FS0007]- Validar Situação da Ligação de Água
	 * do imóvel
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
	 * [UC0364] Efetuar Substituição de Hidrômetro
	 * [SB0001] Atualiza o Histórico da instalação com os dados do hidrômetro
	 * substituido [SB0002] Gerar Hitórico de instalação do hidrômetro [SB0003]
	 * Atualizar Imóvel/Ligação de Água [SB0004] Atualizar situação de
	 * hidrômetro na tabela HIDROMETRO [SB0005] Atualizar situação do hidrômetro
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
	 * [UC0360]- Efetuar Supressão da Ligação de Água
	 * [SB0001]- Atualizar Ligação de água [SB0002]- Atualizar Imóvel [SB0004]-
	 * Atualizar Histótico de Instalação de Hidrômetro
	 * 
	 * @author Rômulo Aurélio
	 * @date 28/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void efetuarSupressaoLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper, boolean retirarHidrometro)
					throws ControladorException;

	/**
	 * [UC0368] Atualizar Instalação do Hidrômetro
	 * [FS0001] - Verificar a existência da matrícula do imóvel [FS0002] -
	 * Verificar a situação do imóvel [FS0003] - Validar existência do
	 * hidrômetro [FS0004] - Validar leitura instalação hidrômetro [FS0005] -
	 * Validar leitura retirada hidrômetro [FS0006] - Validar leitura retirada
	 * corte [FS0007] - Validar Leitura Supressão [FS0009] - Verificar sucesso
	 * da transação
	 * 
	 * @author lms
	 * @created 21/07/2006
	 * @throws ControladorException
	 */
	public void atualizarInstalacaoHidrometro(Imovel imovel, Integer medicaoTipo) throws ControladorException;

	public void atualizarInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0359] Efetuar Restabelecimento Ligação de Água
	 * [SB0001] Atualizar Imóvel/Ligação de Água/Ligação de Esgoto
	 * 
	 * @author Rômulo Aurélio
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
	 * [FS0002] - Solicitar o indicador de troca de serviço, situação e motivo
	 * de encerramento [FS0003] - Validar atendimento do motivo de encerramento
	 * [FS0005] - Validar indicador de deferimento [FS0006] - Validar indicador
	 * de deferimento x indicador de troca de serviço [FS0007] - Verificar
	 * sucesso da transação
	 * 
	 * @author lms
	 * @created 21/07/2006
	 * @throws ControladorException
	 */
	public Integer inserirOSReferidaRetornoTipo(OsReferidaRetornoTipo osReferidaRetornoTipo) throws ControladorException;

	/**
	 * [UC0354] Efetuar Ligação de Água.
	 * Permite validar ligação de Água Exibir ou pelo menu ou pela
	 * funcionalidade encerrar a execução da ordem de serço.
	 * [FS0008] Verificar Situação Rede de Água na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Água do Imóvel
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0353] Efetuar Ligação de Esgoto.
	 * Permite validar ligação de esgoto Exibir ou pelo menu ou pela
	 * funcionalidade encerrar a execução da ordem de serço.
	 * [FS0008] Verificar Situação Rede de Esgoto na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Esgoto do Imóvel
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoEsgotoExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0354] Efetuar Ligação de Água.
	 * Permite validar ligação de Água Efetuar ou pelo menu ou pela
	 * funcionalidade encerrar a execução da ordem de serço.
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
	 * @author Rômulo Aurélio
	 * @date 31/07/2006
	 * @param descricao
	 * @param descricaoAbreviada
	 * @param unidadeMaterial
	 * @throws ControladorException
	 */

	public Integer inserirMaterial(String descricao, String descricaoAbreviada, String unidadeMaterial, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0385] Inserir Tipo Perfil Serviço
	 * 
	 * @author Ana Maria
	 * @date 01/08/2006
	 * @param servicoPerfilTipo
	 * @throws ControladorException
	 */
	public Integer inserirServicoTipoPerfil(ServicoPerfilTipo servicoPerfilTipo) throws ControladorException;

	/**
	 * [UC0436] Inserir Tipo de Serviço de Referência.
	 * Permite a inclusão de um tipo de serviço de referência.
	 * [FS0003] Validar indicador de existencia x Situação da Os de referencia
	 * 
	 * @author Rômulo Aurélio.
	 * @date 05/08/2006
	 * @param servicoTipoReferencia
	 * @throws ControladorException
	 */

	public Integer inserirTipoServicoReferencia(ServicoTipoReferencia servicoTipoReferencia, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0004] - Validar Perfil do Serviço
	 * 
	 * @author lms
	 * @date 01/08/2006
	 */
	public ServicoPerfilTipo pesquisarServicoPerfilTipo(Integer idServicoPerfilTipo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0005] - Validar Tipo de Serviço de Referência
	 * 
	 * @author lms
	 * @date 02/08/2006
	 */
	public ServicoTipoReferencia pesquisarServicoTipoReferencia(Integer idServicoTipoReferencia) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0009] - Validar Atividade
	 * 
	 * @author lms
	 * @date 05/08/2006
	 */
	public Atividade pesquisarAtividade(Integer idAtividade, String atividadeUnica) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0006] - Validar Ordem de Execução
	 * 
	 * @author lms
	 * @date 05/08/2006
	 */
	public void validarOrdemExecucao(Collection colecaoServicoTipoAtividade, Short ordemExecucao) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public Integer inserirServicoTipo(ServicoTipo servicoTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0412] - Atualizar Tipo de Serviço
	 * 
	 * @author vsm
	 * @date 10/12/2008
	 */
	public Integer atualizarServicoTipo(ServicoTipo servicoTipo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public ServicoTipoSubgrupo pesquisarServicoTipoSubgrupo(Integer idServicoTipoSubgrupo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public ServicoTipoPrioridade pesquisarServicoTipoPrioridade(Integer idServicoTipoPrioridade) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0010] - Validar Material
	 * 
	 * @author lms
	 * @date 08/08/2006
	 */
	public Material pesquisarMaterial(Integer idMaterial) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public void validarAdicionarAtividade(Collection colecaoServicoTipoAtividade, Integer idAtividade) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public void validarAdicionarMaterial(Collection colecaoServicoTipoMaterial, Integer idMaterial) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço - Validar Adicionar Serviço Associado
	 * 
	 * @author Virgínia Melo
	 * @date 14/05/2009
	 */
	public void validarAdicionarServicoAssociado(Collection colecaoServicoAssociado, Integer idServico) throws ControladorException;

	/**
	 * [UC0449] Inserir Prioridade do Tipo de Serviço
	 * Permite a inclusão de uma prioridade do tipo de serviço.
	 * [FS0001] Verificar existencia da descrição [FS0003]- Verificar existência
	 * da descrição abreviada [FS0002] Validar quantidade de horas início e
	 * quantidade de horas fim
	 * 
	 * @author Rômulo Aurélio.
	 * @date 11/08/2006
	 * @param servicoTipoPrioridade
	 * @throws ControladorException
	 */
	public Integer inserirPrioridadeTipoServico(ServicoTipoPrioridade servicoTipoPrioridade, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades do
	 * supressao ligação de agua
	 * 
	 * @author Rafael Pinto
	 * @date 28/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirSupressaoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades do
	 * restabelecimento ligação de agua
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirRestabelecimentoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades de
	 * religação de água
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirReligacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades de
	 * corte adimistrativo de ligação de água
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirCorteAdministrativoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades do
	 * substituição de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 31/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirSubstituicaoHidrometro(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * retirada de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirRetiradaHidrometroAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * remanejamento de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirRemanejmentoHidrometroAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0362] Efetuar Instalacao de Hidrômetro
	 * Validar Instalacao de Hidrômetro
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
	 * Este método se destina a validar todas as situações e particularidades da
	 * atualização da instalação de hidrômetro do imóvel no momento da exibição.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirAtualizarInstalacaoHidrometro(OrdemServico ordemServico, boolean menu) throws ControladorException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param servicoTipoId
	 * @param imovelId
	 * @param tipoMedicao
	 * @return valor do débito
	 * @throws ControladorException
	 */
	public BigDecimal obterValorDebito(Integer servicoTipoId, Integer imovelId, Short tipoMedicao) throws ControladorException;

	/**
	 * Método que retorna o número do hidrômetro da ligação de água
	 * 
	 * @throws ErroRepositorioException
	 */
	public String pesquisarNumeroHidrometroLigacaoAgua(Integer idLigacaoAgua) throws ControladorException;

	/**
	 * Método que retorna o tipo da ligação de água e a data do corte da ligação
	 * de água
	 * 
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLigacaoAgua(Integer idLigacaoAgua) throws ControladorException;

	/**
	 * [UC0357] Efetuar Religação de Água
	 * Permite efetuar religação da ligação de água ou pelo menu ou pela
	 * funcionalidade encerrar a execução da ordem de serviço.
	 * [SB0001] Atualizar Imóvel/Ligação de Água/Ligação de Esgoto
	 * 
	 * @author Rômulo Aurélio
	 * @date 07/07/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * Consulta os dados das ordens de serviço para a geração do relatório
	 * 
	 * @author Rafael Corrêa
	 * @created 07/10/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOrdemServicoProgramacaoRelatorio(Integer idEquipe, Date dataRoteiro) throws ControladorException;

	/**
	 * [UC0364] Efetuar Substituição de Hidrômetro
	 * Validar Substituição de Hidrômetro
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
	 * [UC0362] Efetuar Instalacao de Hidrômetro
	 * Validar Instalacao de Hidrômetro
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
	 * [UC0387] Manter Tipo Perfil Serviço
	 * [SB0001] Atualizar Tipo Perfil Serviço
	 * 
	 * @author Kassia Albuquerque
	 * @date 01/11/2006
	 * @pparam servicoPerfilTipo
	 * @throws ControladorException
	 */
	public void atualizarServicoTipoPerfil(ServicoPerfilTipo servicoPerfilTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Este método valida os dados que são necessarios para a
	 * inserção do serviço tipo referencia.
	 * 
	 * @author Flávio Leonardo
	 * @date 31/10/2006
	 * @param servicoTipoReferencia
	 * @return
	 * @throws ControladorException
	 */
	public void validarTipoServicoReferenciaParaInsercao(ServicoTipoReferencia servicoTipoReferencia) throws ControladorException;

	/**
	 * [UC0387] Manter Tipo Perfil Serviço
	 * [SB0002] Remover Tipo Perfil Serviço
	 * 
	 * @author Kassia Albuquerque
	 * @date 08/11/2006
	 * @pparam servicoPerfilTipo
	 * @throws ControladorException
	 */
	public void removerServicoTipoPerfil(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0404] Manter Especificação da Situação do Imovel
	 * Este caso de uso remove a especificação e os critério
	 * [SB0002] Remover Especificação da situacao
	 * 
	 * @author Rafael Pinto
	 * @created 08/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerEspecificacaoSituacaoImovel(String[] idsEspecificacaoSituacaoImovel, Usuario usuario, Date ultimaAlteracao)
					throws ControladorException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro na ligação de água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmLigacaoAgua(Integer imovelId) throws ControladorException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro na ligação de água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
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
	 * [UC0498] Efetuar Ligação de Água com Instalação de Hidrômetro.
	 * Permite validar o efetuar ligação de Água com Instalação de Hidrômetro Exibir ou pelo menu ou
	 * pela
	 * funcionalidade encerrar a execução da ordem de serço.
	 * [FS0008] Verificar Situação Rede de Água na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Água do Imóvel
	 * 
	 * @author Rafael Corrêa
	 * @date 28/11/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0498] Efetuar Ligação de Água com Instalação de Hidrômetro.
	 * Permite efetuar ligação de Água com Instalação de Hidrômetr ou pelo menu
	 * ou pela funcionalidade encerrar a execução da ordem de serço.
	 * 
	 * @author Rafael Corrêa
	 * @date 29/11/2006
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarLigacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Permite efetuar a Ligação de Água com Instalação de Hidrometro sem RA
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
	 * @author Thiago Tenório
	 * @date 25/05/2006
	 * @param Prioridade
	 *            Tipo Servico
	 * @throws ControladorException
	 */
	public void atualizarPrioridadeTipoServico(ServicoTipoPrioridade servicoTipoPrioridade, Collection colecaoServicoTipoPrioridade)
					throws ControladorException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * 
	 * @author Rafael Pinto
	 * @date 22/02/2007
	 * @param servicoTipoId
	 * @param imovelId
	 * @param tipoMedicao
	 * @param idHidrometroCapacidade
	 * @return valor do Débito
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
	 * [UC0540] Efetuar Restabelecimento da Ligação de Água com Instalação de hidrômetro.
	 * Permite validar o Efetuar Restabelecimento Ligação de Água com Instalação de hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0008] Verificar Situação Rede de Água na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Água do Imóvel
	 * 
	 * @author Rafael Corrêa
	 * @date 18/04/2007
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarRestabelecimentoLigacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC0540] Efetuar Restabelecimento da Ligação de Água com Instalação de hidrômetro.
	 * Permite efetuar o Restabelecimento Ligação de Água com Instalação de Hidrômetro ou pelo menu
	 * ou pela funcionalidade encerrar a Execução da ordem de serviço.
	 * 
	 * @author Rafael Corrêa
	 * @date 19/04/2007
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarRestabelecimentoLigacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper,
					Usuario usuario) throws ControladorException;

	/**
	 * Pesquisa todos os ids das situações de ligação de água.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoAgua() throws ControladorException;

	/**
	 * Pesquisa todos os ids das situações de ligação de esgoto.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoEsgoto() throws ControladorException;

	/**
	 * Este cso de uso permite efetuar a ligação de água e eventualmente a
	 * instalação de hidrômetro, sem informação de RA sendo chamado direto pelo
	 * menu.
	 * [UC0579] - Efetuar Ligação de Água com Intalação de Hidrômetro
	 * 
	 * @author Flávio Leonardo
	 * @date 25/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public EfetuarLigacaoAguaComInstalacaoHidrometroSemRAHelper pesquisarEfetuarLigacaoAguaHidrometroSemRA(Integer idImovel)
					throws ControladorException;

	/**
	 * [UC0XXX] Gerar Contrato de Prestação de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 03/05/2007
	 * @throws ControladorException
	 */
	public Collection obterDadosContratoPrestacaoServico(Integer idImovel) throws ControladorException;

	public void atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(Integer idImovel, Integer idHidrometro);

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Obtém os dados necessário da ligação de água, de esgoto e do hidrômetro
	 * instalado na ligação de água
	 * 
	 * @author Rafael Corrêa
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
	 * [UC0608] Efetuar Ligação de Esgoto sem RA.
	 * [FS0001] Verificar existência da matrícula do Imovel.
	 * [FS0007] Verificar situação do imóvel.
	 * [FS0008] Verificar Situação Rede de Esgoto da Quadra.
	 * 
	 * @author Sávio Luiz.
	 * @date 10/09/2007
	 * @param imovel
	 * @throws ControladorException
	 */
	public String validarMatriculaImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0482]Emitir 2ª Via de Conta
	 *obter numero do hidrômetro na ligação de água.
	 * 
	 * @author Vivianne Sousa
	 * @date 11/09/2007
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public String obterNumeroHidrometroEmLigacaoAgua(Integer imovelId) throws ControladorException;

	/**
	 * [UC0475] Obter Valor do Débito
	 * Obter Capacidade de Hidrômetro pela Ligação de Água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmLigacaoAgua(Integer imovelId) throws ControladorException;

	/**
	 * [UC0353] Efetuar Ligação de Água.
	 * Permite efetuar Ligação de Água ou pelo menu ou pela funcionalidade
	 * encerrar a Execução da ordem de serviço.
	 * [SB0001] Gerar Ligação de Água
	 * 
	 * @author eduardo henrique
	 * @date 18/03/2009
	 *       Alteração no [SB0001] para determinação automática do Consumo mínimo da Ligação,
	 *       caso não tenha sido informado hidrômetro.
	 * @param ligacaoAgua
	 *            Instancia da nova Ligacao de Agua que será adicionada. (Deve vir com a instancia
	 *            de Imovel populada e HidromInstHistorico, caso exista)
	 * @throws ControladorException
	 */
	public void determinarConsumoMinimoNovaLigacaoAgua(LigacaoAgua ligacaoAgua) throws ControladorException;

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Collection<RelatorioImoveisSituacaoLigacaoAguaHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioCertidaoNegativaHelper> pesquisarRelatorioCertidaoNegativa(Imovel imo) throws ControladorException;

	/**
	 * Pesquisa os dados necessários para a geração do relatório
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioCertidaoNegativaClienteBean> pesquisarRelatorioCertidaoNegativaCliente(Collection<Integer> idsClientes,
					Cliente clienteInformado) throws ControladorException;

	/**
	 * [UC0498] Efetuar Religação de Água com Instalação de hidrômetro.
	 * Permite efetuar religação de Água com Instalação de Hidrômetro ou pelo
	 * menu ou pela funcionalidade encerrar a Execução da ordem de serviço.
	 * 
	 * @author Sávio Luiz
	 * @date 29/01/2008
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Efetuar Religação de Água com Substituição de hidrômetro.
	 * Permite efetuar religação de Água com Substituição de Hidrômetro ou pelo
	 * menu ou pela funcionalidade encerrar a Execução da ordem de serviço.
	 * 
	 * @author Luiz César
	 * @date 22/01/2008
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAguaComSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Validar se o local de armazenagem é OFICINA (HILA_ICOFICINA = 1) e se a situação atual do
	 * hidrômetro for diferente de "EM MANUTENÇÃO"
	 * 
	 * @param idLocalArmazenagem
	 * @param idSituacaoHidrometro
	 * @throws ControladorException
	 */
	public void validarLocalArmazenagemSituacaoHidrometro(String idLocalArmazenagem, String idSituacaoHidrometro)
					throws ControladorException;

	/**
	 * [UC0747] Efetuar Religação de Água com Instalação de hidrômetro.
	 * Permite validar o efetuar religação de Água com Instalação de hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
	 * 
	 * @author Sávio Luiz
	 * @date 29/01/2008
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarReligacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [] Efetuar Religação de Água com Substituição de Hidrômetro.
	 * Permite validar o efetuar religação de Água com Substituição de Hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
	 * 
	 * @author Luiz César
	 * @date 09/06/2010
	 * @param ordem
	 *            ,veioEncerrarOS
	 * @throws ControladorException
	 */
	public void validarReligacaoAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0747] Efetuar Corte de Água com Instalação de hidrômetro.
	 * Permite validar o efetuar corte de Água com Instalação de hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
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
	 * [] Efetuar Corte de Água com Substituição de Hidrômetro.
	 * Permite validar o efetuar Corte de Água com Substituição de Hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
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
	 * OBS: Não Atualiza a OS, Efetua os Calculos,
	 * ném Gera/acumula dados relativos aos documentos gerados
	 * 
	 * @author isilva
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public IntegracaoComercialHelper atualizarHidrometroInstalacaoHistoricoSemAtualizarOSEEfetuarCalculos(
					IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC] Efetuar Restabelecimento da Ligação de Água com Substituição de hidrômetro.
	 * Permite validar o Efetuar Restabelecimento Ligação de Água com Substituição de hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS] Verificar Situação Rede de Água na Quadra. [FS] Verificar
	 * Situação do Imovel. [FS] Validar Situação de Água do Imóvel
	 * 
	 * @author isilva
	 * @date 22/12/2010
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC0540] Efetuar Restabelecimento da Ligação de Água com Substituição de hidrômetro.
	 * Permite efetuar o Restabelecimento Ligação de Água com Substituição de Hidrômetro ou pelo
	 * menu
	 * ou pela funcionalidade encerrar a Execução da ordem de serviço.
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
	 * Efetuar Instalação/Substituição de Registro Magnetico.
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void efetuarInstalacaoSubstituicaoRegistroMagnetico(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Efetuar Instalação/Substituição de Tubete Magnetico.
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void efetuarInstalacaoSubstituicaoTubeteMagnetico(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException;

	/**
	 * Efetuar Instalação/Substituição de Registro Magnetico.
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void validarInstalacaoSubstituicaoRegistroMagneticoExibir(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC3064] Efetuar Instalação/Substituição de Tubete Magnético
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
	 * [UC3135] Gerar Relatório de Materiais Aplicados
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
	 * OC1213341 - Verificar se existe valor do Serviço Tipo para a Localidade informada
	 * 
	 * @author Ado Rocha
	 * @date 23/04/2014
	 **/
	public BigDecimal verificarServicoTipoValorLocalidade(Integer idImovel, Integer idDebitoTipo) throws ControladorException;

}