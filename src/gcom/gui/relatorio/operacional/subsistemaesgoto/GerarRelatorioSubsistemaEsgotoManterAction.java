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

package gcom.gui.relatorio.operacional.subsistemaesgoto;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.operacional.abastecimento.FiltrarSubsistemaEsgotoActionForm;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubsistemaEsgoto;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.operacional.subsistemaesgoto.RelatorioManterSubsistemaEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de subsistema de esgoto manter
 * 
 * @author S�vio Luiz
 * @created 31 de Janeiro de 2011
 */
public class GerarRelatorioSubsistemaEsgotoManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSubsistemaEsgotoActionForm filtrarSubsistemaEsgotoActionForm = (FiltrarSubsistemaEsgotoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = (FiltroSubsistemaEsgoto) sessao.getAttribute("filtroSubsistemaEsgotoSessao");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		SubsistemaEsgoto subsistemaEsgotoParametros = new SubsistemaEsgoto();

		String idSistemaEsgoto = (String) filtrarSubsistemaEsgotoActionForm.getSistemaEsgoto();

		SistemaEsgoto sistemaEsgoto = null;

		if(idSistemaEsgoto != null && !idSistemaEsgoto.equals("") && !idSistemaEsgoto.equals("-1")){
			FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();

			filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.ID, idSistemaEsgoto));
			filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection sistemasEsgotos = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

			if(sistemasEsgotos != null && !sistemasEsgotos.isEmpty()){
				// O Sistema de Esgoto foi encontrado
				Iterator sistemaEsgotoIterator = sistemasEsgotos.iterator();

				sistemaEsgoto = (SistemaEsgoto) sistemaEsgotoIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Sistema de Esgoto");
			}

		}else{
			sistemaEsgoto = new SistemaEsgoto();
		}

		int codigo = 0;

		String codigoPesquisar = (String) filtrarSubsistemaEsgotoActionForm.getCodigo();

		if(codigoPesquisar != null && !codigoPesquisar.equals("")){
			codigo = Integer.parseInt(codigoPesquisar);
		}

		Short indicadorDeUso = null;

		if(filtrarSubsistemaEsgotoActionForm.getIndicadorUso() != null && !filtrarSubsistemaEsgotoActionForm.getIndicadorUso().equals("")){

			indicadorDeUso = new Short("" + filtrarSubsistemaEsgotoActionForm.getIndicadorUso());
		}

		// seta os parametros que ser�o mostrados no relat�rio
		subsistemaEsgotoParametros.setSistemaEsgoto(sistemaEsgoto);
		subsistemaEsgotoParametros.setCodigo(codigo);
		subsistemaEsgotoParametros.setDescricao("" + filtrarSubsistemaEsgotoActionForm.getDescricaoSubsistemaEsgoto());
		subsistemaEsgotoParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterSubsistemaEsgoto relatorio = new RelatorioManterSubsistemaEsgoto((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));
		relatorio.addParametro("filtroSubsistemaEsgoto", filtroSubsistemaEsgoto);
		relatorio.addParametro("subsistemaEsgotoParametros", subsistemaEsgotoParametros);
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
