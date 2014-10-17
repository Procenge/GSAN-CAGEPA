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

package gcom.gui.cadastro.localidade;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroSetorComercialVencimento;
import gcom.faturamento.SetorComercialVencimento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
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

public class ExibirAtualizarSetorComercialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirAtualizarSetorComercial");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarAtualizarSetorComercialActionForm pesquisarAtualizarSetorComercialActionForm = (PesquisarAtualizarSetorComercialActionForm) actionForm;

		String setorComercialID = (String) httpServletRequest.getParameter("setorComercialID");

		if(setorComercialID == null){
			if(httpServletRequest.getAttribute("setorComercialID") != null){
				setorComercialID = (String) httpServletRequest.getAttribute("setorComercialID").toString();
			}
		}
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		Collection colecaoPesquisa = null;

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){

			String localidadeID = null;

			switch(Integer.parseInt(objetoConsulta)){
				// Localidade
				case 1:
					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

					// Recebe o valor do campo localidadeID do formulário.
					localidadeID = pesquisarAtualizarSetorComercialActionForm.getLocalidadeID();

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna localidade
					colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Localidade nao encontrada
						// Limpa os campos localidadeID e localidadeNome do
						// formulário
						httpServletRequest.setAttribute("corLocalidade", "exception");
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeID("");
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeNome("Localidade inexistente.");
					}else{
						httpServletRequest.setAttribute("corLocalidade", "valor");
						Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeID(String.valueOf(objetoLocalidade.getId()));
						pesquisarAtualizarSetorComercialActionForm.setLocalidadeNome(objetoLocalidade.getDescricao());
					}

					break;

				// Municipio
				case 2:
					FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

					// Recebe o valor do campo municipioID do formulário.
					String municipioID = pesquisarAtualizarSetorComercialActionForm.getMunicipioID();

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, municipioID));

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna municipio
					colecaoPesquisa = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Municipio nao encontrado
						// Limpa os campos municipioID e municipioNome do formulário
						httpServletRequest.setAttribute("corMunicipio", "exception");
						pesquisarAtualizarSetorComercialActionForm.setMunicipioID("");
						pesquisarAtualizarSetorComercialActionForm.setMunicipioNome("Município inexistente.");
					}else{
						httpServletRequest.setAttribute("corMunicipio", "valor");
						Municipio objetoMunicipio = (Municipio) Util.retonarObjetoDeColecao(colecaoPesquisa);
						pesquisarAtualizarSetorComercialActionForm.setMunicipioID(String.valueOf(objetoMunicipio.getId()));
						pesquisarAtualizarSetorComercialActionForm.setMunicipioNome(objetoMunicipio.getNome());
					}

					break;

				default:

					break;
			}
		}else{
			if(setorComercialID == null || setorComercialID.equalsIgnoreCase("")){
				// ID do setor comercial não informado
				throw new ActionServletException("atencao.codigo_setor_comercial_nao_informado");
			}else{

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

				// Objetos que serão retornados pelo hibernate
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("municipio");

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, setorComercialID));

				// Retorna setor comercial
				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor comercial não cadastrado
					throw new ActionServletException("atencao.processo.setorComercialNaoCadastrada");
				}else{
					SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);

					// Colocando o objeto na sessão
					sessao.setAttribute("setorComercialManter", setorComercial);

					pesquisarAtualizarSetorComercialActionForm.setSetorComercialID(setorComercialID);
					pesquisarAtualizarSetorComercialActionForm.setLocalidadeID(String.valueOf(setorComercial.getLocalidade().getId()));
					pesquisarAtualizarSetorComercialActionForm.setLocalidadeNome(setorComercial.getLocalidade().getDescricao());
					pesquisarAtualizarSetorComercialActionForm.setSetorComercialCD(String.valueOf(setorComercial.getCodigo()));
					pesquisarAtualizarSetorComercialActionForm.setSetorComercialNome(setorComercial.getDescricao());
					pesquisarAtualizarSetorComercialActionForm.setMunicipioID(String.valueOf(setorComercial.getMunicipio().getId()));
					pesquisarAtualizarSetorComercialActionForm.setMunicipioNome(setorComercial.getMunicipio().getNome());
					pesquisarAtualizarSetorComercialActionForm.setIndicadorUso(String.valueOf(setorComercial.getIndicadorUso()));

					FiltroRota filtroRota = new FiltroRota();
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, setorComercial.getId()));
					filtroRota.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
					filtroRota.adicionarCaminhoParaCarregamentoEntidade("leituraTipo");
					filtroRota.adicionarCaminhoParaCarregamentoEntidade("cobrancaGrupo");
					filtroRota.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupo");
					filtroRota.adicionarCaminhoParaCarregamentoEntidade("empresa");
					filtroRota.adicionarCaminhoParaCarregamentoEntidade("leiturista");

					Collection colecaoRota = getFachada().pesquisar(filtroRota, Rota.class.getName());

					sessao.setAttribute("colecaoRotaAtualizacaoSetorComercial", colecaoRota);
				}
			}
		}

		if(httpServletRequest.getAttribute("voltar") == null){
			if(sessao.getAttribute("manter") == null){
				httpServletRequest.setAttribute("voltar", "filtrarNovo");
				sessao.setAttribute("indicadorAtualizar", "1");
			}else{
				httpServletRequest.setAttribute("voltar", "manter");
			}
		}

		SistemaParametro sistemaParametro = (SistemaParametro) fachada.pesquisarParametrosDoSistema();

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
			pesquisarAtualizarSetorComercialActionForm.setQtdDiasVencimento(sistemaParametro.getQuantidadeDiasVencimentoSetor().toString());
			// Monta calendário vencimentos Alternativos CASO DESO

			FiltroSetorComercialVencimento filtroSetorComercialVencimento = new FiltroSetorComercialVencimento();
			filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.LOCALIDADE_ID,
							new Integer(pesquisarAtualizarSetorComercialActionForm.getLocalidadeID())));
			filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.SETOR_COMERCIAL_ID,
							new Integer(pesquisarAtualizarSetorComercialActionForm.getSetorComercialID())));
			filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection<SetorComercialVencimento> colecaoSetorComercialVencimentos = fachada.pesquisar(filtroSetorComercialVencimento,
							SetorComercialVencimento.class.getName());

			montaCalendarioDiasVencimento(sessao, colecaoSetorComercialVencimentos);
		}

		httpServletRequest.setAttribute("setorComercialID", setorComercialID);
		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * Monta calendário vencimentos Alternativos CASO DESO
	 * 
	 * @author isilva
	 * @param imovel
	 * @param sessao
	 */
	private void montaCalendarioDiasVencimento(HttpSession sessao, Collection<SetorComercialVencimento> colecaoSetorComercialVencimentos){

		Collection<CalendarioForm> calendarioForms = new ArrayList<CalendarioForm>();

		int NUMERO_COLUNAS = 31;
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

			for(SetorComercialVencimento setorComercialVencimento : colecaoSetorComercialVencimentos){
				if(d == setorComercialVencimento.getDiaVencimento().intValue()){
					calendario.setAtivo("S");
				}
			}

			if((d % 7) == 0){
				calendario.setQuebraLinha("S");
			}

			calendarioForms.add(calendario);
		}

		sessao.setAttribute("calendarioForms", calendarioForms);
	}

}
