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

package gcom.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.bean.ConsultarHistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ligacaoagua.bean.DadosEfetuacaoCorteLigacaoAguaHelper;
import gcom.atendimentopublico.ordemservico.bean.DadosUltimoCorteHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * Interface do RepositorioLigacaoAgua
 * 
 * @author Leonardo Regis
 * @date 09/09/2006
 */
public interface IRepositorioLigacaoAgua {

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarConsumoMinimoLigacaoAgua(LigacaoAgua ligacaoAgua) throws ErroRepositorioException;

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água/Histórico de Instalação de Hidrômetro
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param helper
	 * @exception ErroRepositorioExceptions
	 */
	public void efetuarCorteLigacaoAgua(DadosEfetuacaoCorteLigacaoAguaHelper helper) throws ErroRepositorioException;

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água/Histórico de Instalação de Hidrômetro
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param helper
	 * @exception ErroRepositorioExceptions
	 */
	public void efetuarCorteAdministrativoLigacaoAgua(DadosEfetuacaoCorteLigacaoAguaHelper helper) throws ErroRepositorioException;

	/**
	 * [UC0463] Efetuar Restabelecimento da Ligação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água
	 * 
	 * @author Flávio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaRestabelecimento(LigacaoAgua ligacaoAgua) throws ErroRepositorioException;

	/**
	 * [UC0357] Efetuar Religação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água
	 * 
	 * @author Flávio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaReligacao(LigacaoAgua ligacaoAgua) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da Ligacao de água
	 * 
	 * @author Sávio Luiz
	 * @date 20/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsLigacaoAgua(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0054] - Inserir Dados da Tarifa Social
	 * Recupera o consumo mínimo fixado do Imóvel
	 * 
	 * @author Rafael Corrêa
	 * @date 04/0/2006
	 * @param idImovel
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarConsumoMinimoFixado(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa o id do hidrometro
	 * 
	 * @author Sávio Luiz
	 * @date 19/02/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdHidrometroInstalacaoHistorico(Integer idImovel) throws ErroRepositorioException;

	public Collection verificaExistenciaLigacaoAgua(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @author eduardo henrique
	 * @date 19/05/2009
	 *       Método retorna uma LigacaoAgua, buscado por um Id
	 * @param id
	 *            - id da Ligação [obrigatório]
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoAgua pesquisarLigacaoAgua(Integer id) throws ErroRepositorioException;

	/**
	 * @author isilva
	 * @date 24/05/2011
	 *       Obtem Dados do último corte
	 * @param idImovel
	 *            Identificador do Imóvel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DadosUltimoCorteHelper obterDadosUltimoCorte(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @param id
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer id) throws ErroRepositorioException;
	
		/**
	 * Consulta os dados do Histórico da Manutenção da Ligação de Água
	 * {@link ConsultarHistoricoManutencaoLigacaoHelper}
	 * 
	 * @param {@link ConsultarHistoricoManutencaoLigacaoHelper}
	 * @param numeroPagina
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> consultarHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * [UC3076] Retorna a quantidade de registros retornados pela consulta do Histórico da
	 * Manutenção da Ligação de Água
	 * 
	 * @param {@link ConsultarHistoricoManutencaoLigacaoHelper}
	 * @return Integer
	 */
	public Integer consultarTotalRegistrosHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper)
					throws ErroRepositorioException;
}