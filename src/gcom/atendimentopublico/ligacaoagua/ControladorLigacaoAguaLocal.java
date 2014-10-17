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

package gcom.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.bean.ConsultarHistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ligacaoagua.bean.DadosEfetuacaoCorteLigacaoAguaHelper;
import gcom.atendimentopublico.ligacaoagua.bean.HistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.DadosUltimoCorteHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaDocumento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.List;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorLigacaoAgua
 * 
 * @author Leonardo Regis
 * @date 09/09/2006
 */
public interface ControladorLigacaoAguaLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * [UC0463] Atualizar Consumo M�nimo de Liga��o �gua
	 * Este m�todo se destina a validar todas as situa��es e particularidades
	 * da atualiza��o do consumo m�nimo da liga��o de agua
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param OrdemServico
	 * @param veioEncerrarOS
	 */
	public void validarExibirAtualizarConsumoMinimoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC0463] Atualizar Consumo M�nimo de Liga��o de �gua
	 * [FS004] Validar Consumo M�nimo
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param imovel
	 */
	public void validarConsumoMinimoLigacaoAgua(Imovel imovel) throws ControladorException;

	/**
	 * [UC0463] Atualizar Consumo M�nimo da Liga��o de �gua
	 * [SB0001] Atualizar Liga��o de �gua.
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param ligacaoAgua
	 * @throws ControladorException
	 */
	public void atualizarConsumoMinimoLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades
	 * do corte liga��o de agua
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirCorteLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException;

	/**
	 * [UC0355] Efetuar Corte de Liga��o de �gua.
	 * 
	 * @author Leonardo Regis.
	 * @date 25/09/2006
	 * @param helper
	 * @throws ControladorException
	 */
	public void efetuarCorteLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * [UC0365] Efetuar Corte Administrativo da Liga��o Agua
	 * 
	 * @author Thiago Ten�rio
	 * @date 30/06/2006
	 * @param helper
	 * @throws ControladorException
	 */
	public void efetuarCorteAdministrativoLigacaoAgua(DadosEfetuacaoCorteLigacaoAguaHelper helper, Usuario usuario)
					throws ControladorException;

	/**
	 * [UC0463] Efetuar Restabelecimento da Liga��o de �gua
	 * [SB0001] Atualizar Im�vel/Liga��o �gua
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaRestabelecimento(LigacaoAgua ligacaoAgua) throws ControladorException;

	/**
	 * [UC0357] Efetuar Religa��o de �gua
	 * [SB0001] Atualizar Im�vel/Liga��o �gua
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaReligacao(LigacaoAgua ligacaoAgua) throws ControladorException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * Recupera os par�metros necess�rios da Ligacao de �gua
	 * (id,dataCorte,dataSupressao)
	 * 
	 * @author S�vio Luiz
	 * @date 20/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public LigacaoAgua recuperaParametrosLigacaoAgua(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa o id do hidrometro
	 * 
	 * @author S�vio Luiz
	 * @date 19/02/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdHidrometroInstalacaoHistorico(Integer idImovel) throws ControladorException;

	/**
	 * [UC0054] - Inserir Dados da Tarifa Social
	 * Recupera o consumo m�nimo fixado do Im�vel
	 * 
	 * @author Rafael Corr�a
	 * @date 04/0/2006
	 * @param idImovel
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoMinimoFixado(Integer idImovel) throws ControladorException;

	public Collection verificaExistenciaLigacaoAgua(Integer idImovel);

	/**
	 * @author eduardo henrique
	 * @date 19/05/2009
	 *       M�todo retorna uma LigacaoAgua, buscado por um Id
	 * @param id
	 *            - id da Liga��o [obrigat�rio]
	 * @return
	 * @throws ControladorException
	 */
	public LigacaoAgua pesquisarLigacaoAgua(Integer id) throws ControladorException;

	/**
	 * [UC] Efetuar Corte de Liga��o de �gua com Instala��o de Hidrometro.
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @param helper
	 * @throws ControladorException
	 */
	public void efetuarCorteAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * @author jns
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void efetuarCorteAguaComSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException;

	/**
	 * @author isilva
	 * @date 24/05/2011
	 *       Obtem Dados do �ltimo corte
	 * @param idImovel
	 *            Identificador do Im�vel
	 * @return
	 * @throws ControladorException
	 */
	public DadosUltimoCorteHelper obterDadosUltimoCorte(Integer idImovel) throws ControladorException;

	/**
	 * @param idLigacaoAguaSituacao
	 * @return
	 * @throws ControladorException
	 */
	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer idLigacaoAguaSituacao) throws ControladorException;
	
	/**
	 * M�todo atualizarHistoricoManutencaoLicacao
	 * <p>
	 * Esse m�todo implementa o processo de atualizacao de historico de manutencao de ligacao de
	 * agua.
	 * </p>
	 * RASTREIO: [OC790655][UC0355][SB0003];
	 * 
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void atualizarHistoricoManutencaoLigacao(OrdemServico ordemServico, short fluxo) throws ControladorException;

	/**
	 * M�todo criarHistoricoManutencaoLigacao
	 * <p>
	 * Esse m�todo implementa processo de criar��o de um {@link HistoricoManutencaoLigacao} tendo
	 * como base em {@link OrdemServico}.
	 * </p>
	 * RASTREIO: [OC790655][UC0430][SB0004.7.1]
	 * 
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void criarHistoricoManutencaoLigacao(OrdemServico ordemServico) throws ControladorException;

	/**
	 * M�todo atualizarHistoricoManutencaoLicacao
	 * <p>
	 * Esse m�todo implementa o processo de atualizacao de historico de manutencao de ligacao de
	 * agua.
	 * </p>
	 * RASTREIO: [OC790655][UC3044][SB0001.1.3];
	 * 
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void atualizarHistoricoManutencaoLigacao(CobrancaDocumento cobrancaDocumentoBase, short fluxo) throws ControladorException;

	/**
	 * M�todo consultarHistoricoAcaoCobrancaPredecessora
	 * <p>
	 * Esse m�todo implementa consulta para recuperar o {@link HistoricoManutencaoLigacao} do
	 * {@link CobrancaAcao} Predecessora./p> RASTREIO: [OC790655][UC3052][SB0001.4.2.1]
	 * 
	 * @param imovel
	 * @param documentoTipo
	 * @param cobrancaAcaoSituacao
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public HistoricoManutencaoLigacao consultarHistoricoAcaoCobranca(CobrancaDocumento documentoCobranca,
					CobrancaAcao cobrancaAcao) throws ControladorException;

	/**
	 * M�todo atualizarHistoricoManutencaoLigacao
	 * <p>
	 * Esse m�todo implementa a atualizacao do {@link HistoricoManutencaoLigacao} da
	 * {@link CobrancaAcao} predecessora
	 * </p>
	 * RASTREIO: [OC790655][UC3052][SB0001.4.2.1]
	 * 
	 * @param historicoAcaoCobrancaPredecessora
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void atualizarHistoricoManutencaoLigacao(HistoricoManutencaoLigacao historicoAcaoCobrancaPredecessora, OrdemServico ordemServico)
					throws ControladorException;

	/**
	 * M�todo criarHistoricoManutencaoLigacao
	 * <p>
	 * Esse m�todo implementa fluxo de cria��o de {@link HistoricoManutencaoLigacao} com base no
	 * {@link CobrancaDocumento}
	 * </p>
	 * RASTREIO: [OC790655][UC3052][SB0001.5]
	 * 
	 * @param documentoCobranca
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void criarHistoricoManutencaoLigacao(CobrancaDocumento documentoCobranca) throws ControladorException;
	

	/**
	 * [UC3076] Consultar o Hist�rico da Manuten��o da Liga��o de �gua
	 * 
	 * @author Luciano Galvao
	 * @date 19/09/2012
	 * @throws ControladorException
	 */
	public List<HistoricoManutencaoLigacaoHelper> consultarHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper,
					Integer numeroPagina) throws ControladorException;

	/**
	 * [UC3076] Retorna a quantidade de registros retornados pela consulta do Hist�rico da
	 * Manuten��o da Liga��o de �gua
	 * 
	 * @author Luciano Galvao
	 * @date 19/09/2012
	 * @throws ControladorException
	 */
	public Integer consultarTotalRegistrosHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper)
					throws ControladorException;

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades
	 * do corte liga��o de agua com retirada de hidrometro
	 * 
	 * @author Eduardo Oliveira
	 * @date 22/04/2014
	 * @param OrdemServico
	 */
	public void validarExibirCorteLigacaoAguaComRetiradaHidrometro(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException;

	/**
	 * [UC3144] Efetuar Corte de Liga��o de �gua com Retirada de Hidr�metro
	 * 
	 * @author Eduardo Oliveira
	 * @date 22/04/2014
	 * @param helper
	 */
	public void efetuarCorteLigacaoAguaComRetiradaHidrometro(IntegracaoComercialHelper integracaoComercialHelper)
					throws ControladorException;
}