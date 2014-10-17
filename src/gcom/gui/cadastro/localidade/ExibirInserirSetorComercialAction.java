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

package gcom.gui.cadastro.localidade;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.CalendarioForm;
import gcom.util.ConstantesSistema;
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

public class ExibirInserirSetorComercialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirSetorComercial");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = (SistemaParametro) fachada.pesquisarParametrosDoSistema();

		HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarAtualizarSetorComercialActionForm pesquisarAtualizarSetorComercialActionForm = (PesquisarAtualizarSetorComercialActionForm) actionForm;

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){

			Collection colecaoPesquisa = null;

			switch(Integer.parseInt(objetoConsulta)){
				// Localidade
				case 1:
					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

					// Recebe o valor do campo localidadeID do formul�rio.
					String localidadeID = pesquisarAtualizarSetorComercialActionForm.getLocalidadeID();

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("municipio");

					// Retorna localidade
					colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Localidade nao encontrada
						// Limpa os campos localidadeID e localidadeNome do
						// formul�rio
						httpServletRequest.setAttribute("corLocalidade", "exception");
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeID("");
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeNome("Localidade inexistente");
						httpServletRequest.setAttribute("nomeCampo", "localidadeID");
					}else{
						Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeID(String.valueOf(objetoLocalidade.getId()));
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeNome(objetoLocalidade.getDescricao());
						httpServletRequest.setAttribute("corLocalidade", "valor");

						// Paramentro para diferenciar a companhia que o sistema est� rodando
						if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
							pesquisarAtualizarSetorComercialActionForm.setMunicipioID(String.valueOf(objetoLocalidade.getMunicipio()
											.getId()));
							pesquisarAtualizarSetorComercialActionForm.setMunicipioNome(objetoLocalidade.getMunicipio().getNome());
							httpServletRequest.setAttribute("corMunicipio", "valor");
						}

						int codigoSetorComercial = fachada.pesquisarMaximoCodigoSetorComercial(objetoLocalidade.getId());
						if(codigoSetorComercial < 999){
							codigoSetorComercial = codigoSetorComercial + 1;

							pesquisarAtualizarSetorComercialActionForm.setSetorComercialCD("" + codigoSetorComercial);
						}

						httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");
					}

					break;
				// Municipio
				case 2:
					FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

					// Recebe o valor do campo municipioID do formul�rio.
					String municipioID = pesquisarAtualizarSetorComercialActionForm.getMunicipioID();

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, municipioID));

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna municipio
					colecaoPesquisa = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Municipio nao encontrado
						// Limpa os campos municipioID e municipioNome do formul�rio
						httpServletRequest.setAttribute("corMunicipio", "exception");
						pesquisarAtualizarSetorComercialActionForm.setMunicipioID("");
						pesquisarAtualizarSetorComercialActionForm.setMunicipioNome("Munic�pio inexistente.");
						httpServletRequest.setAttribute("nomeCampo", "municipioID");
					}else{
						Municipio objetoMunicipio = (Municipio) Util.retonarObjetoDeColecao(colecaoPesquisa);
						pesquisarAtualizarSetorComercialActionForm.setMunicipioID(String.valueOf(objetoMunicipio.getId()));
						pesquisarAtualizarSetorComercialActionForm.setMunicipioNome(objetoMunicipio.getNome());
						httpServletRequest.setAttribute("corMunicipio", "valor");
						httpServletRequest.setAttribute("nomeCampo", "botaoInserir");
					}

					break;

				default:

					break;
			}
		}else{

			// Limpando o formul�rio
			pesquisarAtualizarSetorComercialActionForm.setLocalidadeID("");
			pesquisarAtualizarSetorComercialActionForm.setLocalidadeNome("");
			pesquisarAtualizarSetorComercialActionForm.setMunicipioID("");
			pesquisarAtualizarSetorComercialActionForm.setMunicipioNome("");
			pesquisarAtualizarSetorComercialActionForm.setSetorComercialCD("");
			pesquisarAtualizarSetorComercialActionForm.setSetorComercialNome("");
			// Paramentro para diferenciar a companhia que o sistema est� rodando
			if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
				String[] diasVencimento = {""};
				pesquisarAtualizarSetorComercialActionForm.setDiasVencimento(diasVencimento);
			}

		}

		// Paramentro para diferenciar a companhia que o sistema est� rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
			pesquisarAtualizarSetorComercialActionForm.setQtdDiasVencimento(sistemaParametro.getQuantidadeDiasVencimentoSetor().toString());
			// Monta calend�rio vencimentos Alternativos CASO DESO
			montaCalendarioDiasVencimento(sessao);
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * Monta calend�rio vencimentos Alternativos CASO DESO
	 * 
	 * @author isilva
	 * @param imovel
	 * @param sessao
	 */
	private void montaCalendarioDiasVencimento(HttpSession sessao){

		Collection<CalendarioForm> calendarioForms = new ArrayList<CalendarioForm>();

		int NUMERO_COLUNAS = 35;
		int QTD_DIAS_CALENDARIO = 31;

		for(int d = 1; d <= NUMERO_COLUNAS; d++){

			CalendarioForm calendario = new CalendarioForm();

			if(d == 1 || d == 8 || d == 15 || d == 22 || d == 29){
				calendario.setNovaLinha("S");
			}

			if(d <= QTD_DIAS_CALENDARIO){
				calendario.setDia("" + d);
			}else{
				calendario.setDia("");
			}

			// for (SetorComercialVencimento setorComercialVencimento :
			// colecaoSetorComercialVencimentos) {
			// if (d == setorComercialVencimento.getDiaVencimento().intValue()) {
			// calendario.setAtivo("S");
			// }
			// }

			if((d % 7) == 0){
				calendario.setQuebraLinha("S");
			}

			calendarioForms.add(calendario);
		}

		sessao.setAttribute("calendarioForms", calendarioForms);
	}

}
