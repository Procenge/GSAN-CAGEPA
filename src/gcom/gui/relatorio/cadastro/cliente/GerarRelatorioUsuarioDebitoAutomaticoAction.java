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

package gcom.gui.relatorio.cadastro.cliente;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.FiltroBanco;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.cliente.RelatorioUsuarioDebitoAutomatico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.filtro.ParametroSimples;

/**
 * [UC0XXX] Relatório Usuários em Débito Automático
 * 
 * @author Anderson Italo
 * @date 24/02/2011
 */
public class GerarRelatorioUsuarioDebitoAutomaticoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;
		GerarRelatorioUsuarioDebitoAutomaticoActionForm formulario = (GerarRelatorioUsuarioDebitoAutomaticoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// verificar prenchimento dos campos
		if(formulario.getCodigoBancoInicial() == null || formulario.getCodigoBancoInicial().equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Código do Banco Inicial");
		}

		if(formulario.getCodigoBancoFinal() == null || formulario.getCodigoBancoFinal().equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Código do Banco Final");
		}

		// verificar existência do banco
		Banco bancoInicial = null;
		Banco bancoFinal = null;
		FiltroBanco filtroBanco = new FiltroBanco();

		filtroBanco.adicionarParametro(new ParametroSimples(FiltroBanco.ID, new Integer(formulario.getCodigoBancoInicial())));

		Collection colecaoBanco = fachada.pesquisar(filtroBanco, Banco.class.getName());

		if(colecaoBanco == null || colecaoBanco.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Código do Banco Inicial");
		}else{
			bancoInicial = (Banco) gcom.util.Util.retonarObjetoDeColecao(colecaoBanco);
		}

		filtroBanco = null;
		colecaoBanco = null;

		filtroBanco = new FiltroBanco();
		filtroBanco.adicionarParametro(new ParametroSimples(FiltroBanco.ID, new Integer(formulario.getCodigoBancoFinal())));

		colecaoBanco = fachada.pesquisar(filtroBanco, Banco.class.getName());

		if(colecaoBanco == null || colecaoBanco.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Código do Banco Final");
		}else{
			bancoFinal = (Banco) gcom.util.Util.retonarObjetoDeColecao(colecaoBanco);
		}

		// [FS0001] – Verificar Banco final menor que Banco Inicial
		if(bancoInicial.getId().intValue() > bancoFinal.getId().intValue()){
			throw new ActionServletException("atencao.bancoinicial.menor.bancofinal", null, "");
		}

		RelatorioUsuarioDebitoAutomatico relatorio = new RelatorioUsuarioDebitoAutomatico((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		relatorio.addParametro("idBancoInicial", bancoInicial.getId());
		relatorio.addParametro("idBancoFinal", bancoFinal.getId());
		relatorio.addParametro("nomeBancoInicial", bancoInicial.getDescricao());
		relatorio.addParametro("nomeBancoFinal", bancoFinal.getDescricao());

		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}
}
