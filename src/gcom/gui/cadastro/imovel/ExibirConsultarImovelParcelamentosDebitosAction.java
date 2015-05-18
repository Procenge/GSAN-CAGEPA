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

package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.registroatendimento.bean.ObterIndicadorExistenciaHidrometroHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 9° Aba - Parcelamento efetuados para o imóvel
 * 
 * @author Rafael Santos
 * @since 20/09/2006
 */
public class ExibirConsultarImovelParcelamentosDebitosAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarImovelParcelamentosDebitos");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba documento de cobranca
		String idImovelParcelamentosDebitos = consultarImovelActionForm.getIdImovelParcelamentosDebitos();
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		if(limparForm != null && !limparForm.equals("")){
			// limpar os dados
			httpServletRequest.setAttribute("idImovelParcelamentosDebitosNaoEncontrado", null);

			sessao.removeAttribute("imovelParcelamentosDebitos");
			sessao.removeAttribute("colecaoParcelamento");
			sessao.removeAttribute("idImovelPrincipalAba");
			sessao.removeAttribute("imovelClientes");

			consultarImovelActionForm.setIdImovelDadosComplementares(null);
			consultarImovelActionForm.setIdImovelDadosCadastrais(null);
			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setIdImovelDebitos(null);
			consultarImovelActionForm.setIdImovelPagamentos(null);
			consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
			consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
			consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setIdImovelRegistroAtendimento(null);

			consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setMatriculaImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setDigitoVerificadorImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setSituacaoAguaParcelamentosDebitos(null);
			consultarImovelActionForm.setSituacaoEsgotoParcelamentosDebitos(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setParcelamento(null);
			consultarImovelActionForm.setReparcelamento(null);
			consultarImovelActionForm.setReparcelamentoConsecutivo(null);

			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);

		}
		if((idImovelParcelamentosDebitos != null && !idImovelParcelamentosDebitos.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelParcelamentosDebitos != null && !idImovelParcelamentosDebitos.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){
						consultarImovelActionForm.setIdImovelParcelamentosDebitos(idImovelParcelamentosDebitos);
						// idImovelParcelamentosDebitos = idImovelParcelamentosDebitos;

					}else if(!(idImovelParcelamentosDebitos.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelParcelamentosDebitos(idImovelPrincipalAba);
						idImovelParcelamentosDebitos = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelParcelamentosDebitos = idImovelPrincipalAba;
			}

			// Obtém a instância da Fachada
			Fachada fachada = Fachada.getInstancia();
			Imovel imovel = null;
			// verifica se o objeto imovel é o mesmo ja pesquisado, para não precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;
			if(sessao.getAttribute("imovelParcelamentosDebitos") != null){
				imovel = (Imovel) sessao.getAttribute("imovelParcelamentosDebitos");
				if(!(imovel.getId().toString().equals(idImovelParcelamentosDebitos.trim()))){
					imovel = fachada.consultarParcelamentosDebitosImovel(new Integer(idImovelParcelamentosDebitos.trim()));
					imovelNovoPesquisado = true;
				}
			}else{
				imovel = fachada.consultarParcelamentosDebitosImovel(new Integer(idImovelParcelamentosDebitos.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){
				sessao.setAttribute("imovelParcelamentosDebitos", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelParcelamentosDebitos(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado anterior ou seja a primeira
				// vez que se esteja pesquisando
				if(imovelNovoPesquisado){
					// seta na tela a inscrição do imovel
					httpServletRequest.setAttribute("idImovelParcelamentosDebitosNaoEncontrado", null);

					consultarImovelActionForm.setMatriculaImovelParcelamentosDebitos(fachada.pesquisarInscricaoImovel(new Integer(
									idImovelParcelamentosDebitos.trim()), true));

					try{
						if(ParametroCadastro.P_MATRICULA_COM_DIGITO_VERIFICADOR.executar().toString()
										.equals(ConstantesSistema.NAO.toString())){
							if(ParametroCadastro.P_METODO_CALCULO_DIGITO_VERIFICADOR.executar().toString().equals("1")){
								consultarImovelActionForm.setDigitoVerificadorImovelParcelamentosDebitos(Imovel
												.getDigitoVerificadorMatricula(idImovelParcelamentosDebitos.trim()));
							}
						}
					}catch(ControladorException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new ActionServletException(e1.getMessage(), e1);
					}

					// seta a situação de agua
					if(imovel.getLigacaoAguaSituacao() != null){
						consultarImovelActionForm.setSituacaoAguaParcelamentosDebitos(imovel.getLigacaoAguaSituacao().getDescricao());
					}
					// seta a situação de esgoto
					if(imovel.getLigacaoEsgotoSituacao() != null){
						consultarImovelActionForm.setSituacaoEsgotoParcelamentosDebitos(imovel.getLigacaoEsgotoSituacao().getDescricao());
					}
					// seta o tipo de ligação
					if(idImovelParcelamentosDebitos != null || idImovelParcelamentosDebitos != ""){
						boolean tipoLigacaoBoolean = false;
						ObterIndicadorExistenciaHidrometroHelper obterIndicadorExistenciaHidrometroHelper = fachada
										.obterIndicadorExistenciaHidrometroLigacaoAguaPoco(Util.obterInteger(idImovelParcelamentosDebitos),
														tipoLigacaoBoolean);
						if(obterIndicadorExistenciaHidrometroHelper.getIndicadorLigacaoAgua().intValue() == 1
										|| obterIndicadorExistenciaHidrometroHelper.getIndicadorPoco().intValue() == 1){
							consultarImovelActionForm.setTipoLigacao("Hidrometrado");
						}else{
							consultarImovelActionForm.setTipoLigacao("Consumo Fixo");
						}

					}
					// numero de parcelamentos
					if(imovel.getNumeroParcelamento() != null){
						consultarImovelActionForm.setParcelamento("" + imovel.getNumeroParcelamento());
					}else{
						consultarImovelActionForm.setParcelamento(null);
					}

					// numero de reparcelamento
					if(imovel.getNumeroReparcelamento() != null){
						consultarImovelActionForm.setReparcelamento("" + imovel.getNumeroReparcelamento());
					}else{
						consultarImovelActionForm.setReparcelamento(null);
					}

					// numero de reparcelamento consecutivo
					if(imovel.getNumeroReparcelamentoConsecutivos() != null){
						consultarImovelActionForm.setReparcelamentoConsecutivo("" + imovel.getNumeroReparcelamentoConsecutivos());
					}else{
						consultarImovelActionForm.setReparcelamentoConsecutivo(null);
					}

					Collection<Parcelamento> colecaoParcelamento = fachada.consultarImovelParcelamentoDebito(Integer
									.valueOf(idImovelParcelamentosDebitos.trim()));

					if(colecaoParcelamento != null && !colecaoParcelamento.isEmpty()){
						sessao.setAttribute("colecaoParcelamento", colecaoParcelamento);
					}else{
						/*
						 * if (colecaoParcelamento == null || colecaoParcelamento.isEmpty()){
						 * httpServletRequest.setAttribute(
						 * "idImovelParcelamentosDebitosNaoEncontrado", null);
						 * sessao.removeAttribute("imovelParcelamentosDebitos");
						 * sessao.removeAttribute("colecaoParcelamento");
						 * sessao.removeAttribute("idImovelPrincipalAba");
						 * consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
						 * consultarImovelActionForm.setMatriculaImovelParcelamentosDebitos(null);
						 * consultarImovelActionForm.setSituacaoAguaParcelamentosDebitos(null);
						 * consultarImovelActionForm.setSituacaoEsgotoParcelamentosDebitos(null);
						 * consultarImovelActionForm.setParcelamento(null);
						 * consultarImovelActionForm.setReparcelamento(null);
						 * consultarImovelActionForm.setReparcelamentoConsecutivo(null);
						 * throw new ActionServletException("atencao.parcelamento.inexistente");
						 * }
						 */

						// Colocado por Raphael Rossiter em 12/01/2007
						sessao.removeAttribute("colecaoParcelamento");
					}

				}
			}else{
				httpServletRequest.setAttribute("idImovelParcelamentosDebitosNaoEncontrado", "true");
				consultarImovelActionForm.setMatriculaImovelParcelamentosDebitos("IMÓVEL INEXISTENTE");

				// limpar os dados pesquisados
				sessao.removeAttribute("imovelParcelamentosDebitos");
				sessao.removeAttribute("colecaoParcelamento");

				consultarImovelActionForm.setDigitoVerificadorImovelParcelamentosDebitos(null);
				consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
				consultarImovelActionForm.setSituacaoAguaParcelamentosDebitos(null);
				consultarImovelActionForm.setSituacaoEsgotoParcelamentosDebitos(null);
				consultarImovelActionForm.setTipoLigacao(null);
				consultarImovelActionForm.setParcelamento(null);
				consultarImovelActionForm.setReparcelamento(null);
				consultarImovelActionForm.setReparcelamentoConsecutivo(null);

			}
		}else{
			consultarImovelActionForm.setIdImovelParcelamentosDebitos(idImovelParcelamentosDebitos);
			httpServletRequest.setAttribute("idImovelParcelamentosDebitosNaoEncontrado", null);

			sessao.removeAttribute("imovelParcelamentosDebitos");
			sessao.removeAttribute("colecaoParcelamento");
			sessao.removeAttribute("idImovelPrincipalAba");

			consultarImovelActionForm.setMatriculaImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setDigitoVerificadorImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setSituacaoAguaParcelamentosDebitos(null);
			consultarImovelActionForm.setSituacaoEsgotoParcelamentosDebitos(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setParcelamento(null);
			consultarImovelActionForm.setReparcelamento(null);
			consultarImovelActionForm.setReparcelamentoConsecutivo(null);

		}

		try{
			if(ParametroCadastro.P_MATRICULA_COM_DIGITO_VERIFICADOR.executar().toString().equals(ConstantesSistema.NAO.toString())){
				if(ParametroCadastro.P_METODO_CALCULO_DIGITO_VERIFICADOR.executar().toString().equals("1")){
					httpServletRequest.setAttribute("matriculaSemDigitoVerificador", '1');
				}else{
					throw new ControladorException("erro.parametro.nao.informado", null, "P_METODO_CALCULO_DIGITO_VERIFICADOR");
				}

			}else{
				httpServletRequest.setAttribute("matriculaSemDigitoVerificador", '0');
			}
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ActionServletException(e.getMessage(), e);
		}

		return retorno;
	}

}
