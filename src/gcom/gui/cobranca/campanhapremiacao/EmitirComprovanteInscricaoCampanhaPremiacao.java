/**
 * 
 */
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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.cadastro.cliente.ClienteFone;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.CampanhaCadastroFone;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaCadastro;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Hiroshi Goncalves
 * @date 09/09/2013
 */
public class EmitirComprovanteInscricaoCampanhaPremiacao
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// cria uma instância da classe do relatório
		RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1 relatorioComprovanteInscricaoCampanhaPremiacao = new RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1(
						usuarioLogado);

		String indicadorEnvioComprovanteEmail = httpServletRequest.getParameter("indicadorEnvioComprovanteEmail");

		CampanhaCadastro campanhaCadastro = (CampanhaCadastro) sessao.getAttribute("campanhaCadastro");
		
		if(campanhaCadastro == null){
			String idCampanhaCadastro = httpServletRequest.getParameter("idCampanhaCadastro");
			
			FiltroCampanhaCadastro filtroCampanhaCadastro = new FiltroCampanhaCadastro();
			filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("campanha");
			filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("orgaoExpedidorRG");
			filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
			filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacao");
			
			filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.ID, idCampanhaCadastro));
			
			campanhaCadastro = (CampanhaCadastro) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCampanhaCadastro,
							CampanhaCadastro.class.getName()));
		}

		relatorioComprovanteInscricaoCampanhaPremiacao.addParametro("indicadorEnvioComprovanteEmail", indicadorEnvioComprovanteEmail);

		relatorioComprovanteInscricaoCampanhaPremiacao.addParametro("campanhaCadastro", campanhaCadastro);

		// fachada.emitirComprovanteInscricaoCampanhaPremiacao(usuarioLogado, campanhaCadastro,
		// indicadorEnvioComprovanteEmail);

		relatorioComprovanteInscricaoCampanhaPremiacao.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		try{
			retorno = processarExibicaoRelatorio(relatorioComprovanteInscricaoCampanhaPremiacao, TarefaRelatorio.TIPO_PDF,
							httpServletRequest, httpServletResponse, actionMapping);
		}catch(SistemaException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}

	private Collection<CampanhaCadastroFone> montarCollectionCampanhaCadastroFone(Collection<ClienteFone> colClienteFone){

		Collection colCampanhaCadastroFone = new ArrayList();

		if(colClienteFone != null){
			for(ClienteFone clienteFone : colClienteFone){
				CampanhaCadastroFone campanhaCadastroFone = new CampanhaCadastroFone();

				campanhaCadastroFone.setFoneTipo(clienteFone.getFoneTipo());
				campanhaCadastroFone.setCodigoDDD(clienteFone.getDdd());
				campanhaCadastroFone.setNumeroFone(clienteFone.getTelefone());
				campanhaCadastroFone.setUltimaAlteracao(clienteFone.getUltimaAlteracao());

				if(clienteFone.getRamal() != null){
					campanhaCadastroFone.setNumeroFoneRamal(clienteFone.getRamal());
				}

				colCampanhaCadastroFone.add(campanhaCadastroFone);

			}
		}

		return colCampanhaCadastroFone;
	}

}
