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
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirSetorComercialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		// HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarAtualizarSetorComercialActionForm pesquisarAtualizarSetorComercialActionForm = (PesquisarAtualizarSetorComercialActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		String localidadeID = pesquisarAtualizarSetorComercialActionForm.getLocalidadeID();
		String setorComercialCD = pesquisarAtualizarSetorComercialActionForm.getSetorComercialCD();
		String setorComercialNome = pesquisarAtualizarSetorComercialActionForm.getSetorComercialNome();
		String municipioID = pesquisarAtualizarSetorComercialActionForm.getMunicipioID();

		if(localidadeID != null && !localidadeID.equalsIgnoreCase("")){

			Collection colecaoPesquisa = null;

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade inexistente
				throw new ActionServletException("atencao.pesquisa.localidade_inexistente");
			}else{
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

				if(setorComercialCD == null || setorComercialCD.equalsIgnoreCase("")){
					// C�digo do setor comercial n�o informado.
					throw new ActionServletException("atencao.codigo_setor_comercial_nao_informado");
				}else if(setorComercialNome == null || setorComercialNome.equalsIgnoreCase("")){
					// Nome do setor comercial n�o informado.
					throw new ActionServletException("atencao.nome_setor_comercial_nao_informado");
				}else if(municipioID == null || municipioID.equalsIgnoreCase("")){
					// C�digo do munic�pio n�o informado.
					throw new ActionServletException("atencao.municipio_nao_informado");
				}else{
					FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, municipioID));

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna municipio
					colecaoPesquisa = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Municipio inexistente
						throw new ActionServletException("atencao.pesquisa.municipio_inexistente");
					}else{
						Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoPesquisa);

						// Cria o objeto setorComercial que ser�
						// inserido na base
						SetorComercial setorComercial = new SetorComercial();

						setorComercial.setCodigo(Integer.parseInt(setorComercialCD));
						setorComercial.setDescricao(setorComercialNome);
						setorComercial.setLocalidade(localidade);
						setorComercial.setMunicipio(municipio);
						setorComercial.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
						setorComercial.setUltimaAlteracao(new Date());

						Integer codigoSetorComercialInserido;

						// Paramentro para diferenciar a companhia que o sistema est� rodando
						if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
							String[] diasVencimento = pesquisarAtualizarSetorComercialActionForm.getDiasVencimento();

							/*
							 * if (diasVencimento == null || diasVencimento.length < 1) {
							 * //Dia de Vencimento n�o informado.
							 * throw new
							 * ActionServletException("atencao.dia_vencimento_nao_informado");
							 * }
							 */

							codigoSetorComercialInserido = (Integer) fachada.inserirSetorComercialComDiasVencimento(setorComercial,
											diasVencimento, usuarioLogado);
						}else{
							codigoSetorComercialInserido = (Integer) fachada.inserirSetorComercial(setorComercial, usuarioLogado);
						}

						montarPaginaSucesso(httpServletRequest, "Setor Comercial de c�digo " + setorComercial.getCodigo()
										+ " da localidade " + localidade.getId() + " - " + localidade.getDescricao().toUpperCase()
										+ " inserido com sucesso.", "Inserir outro Setor Comercial",
										"exibirInserirSetorComercialAction.do", "exibirAtualizarSetorComercialAction.do?setorComercialID="
														+ codigoSetorComercialInserido, "Atualizar Setor Comercial Inserido");
					}
				}
			}
		}else{
			// Campo localidadeID n�o informado.
			throw new ActionServletException("atencao.localidade_nao_informada");
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

}
