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

import gcom.atendimentopublico.bean.ImovelComentarioHelper;
import gcom.atendimentopublico.imovelcomentario.FiltroImovelComentario;
import gcom.atendimentopublico.imovelcomentario.ImovelComentario;
import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterIndicadorExistenciaHidrometroHelper;
import gcom.cadastro.atendimento.Atendimento;
import gcom.cadastro.atendimento.FiltroAtendimento;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ConsultarImovelRegistroAtendimentoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 10° Aba - Registro de Atendimento
 * 
 * @author Rafael Santos
 * @since 21/09/2006
 */
public class ExibirConsultarImovelRegistroAtendimentoAction
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

		ActionForward retorno = actionMapping.findForward("consultarImovelRegistroAtendimento");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba documento de cobranca
		String idImovelRegistroAtendimento = consultarImovelActionForm.getIdImovelRegistroAtendimento();
		String limparForm = httpServletRequest.getParameter("limparForm");

		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		if(limparForm != null && !limparForm.equals("")){
			// limpar os dados
			httpServletRequest.setAttribute("idImovelRegistroAtendimentoNaoEncontrado", null);

			sessao.removeAttribute("imovelRegistroAtendimento");
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

			consultarImovelActionForm.setIdImovelRegistroAtendimento(null);
			consultarImovelActionForm.setMatriculaImovelRegistroAtendimento(null);
			consultarImovelActionForm.setDigitoVerificadorImovelRegistroAtendimento(null);
			consultarImovelActionForm.setSituacaoAguaRegistroAtendimento(null);
			consultarImovelActionForm.setSituacaoEsgotoRegistroAtendimento(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setColecaoOS(null);
			consultarImovelActionForm.setColecaoAtendimento(null);
			sessao.removeAttribute("colecaoConsultarImovelRegistroAtendimentoHelper");
			sessao.removeAttribute("colecaoImovelComentarioHelper");
			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);

		}

		if((idImovelRegistroAtendimento != null && !idImovelRegistroAtendimento.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			// if( idImovelRegistroAtendimento != null && idImovelPrincipalAba != null
			// && idImovelPrincipalAba.equals(idImovelRegistroAtendimento)){

			if(idImovelRegistroAtendimento != null && !idImovelRegistroAtendimento.equalsIgnoreCase("")){
				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
					if(indicadorNovo != null && !indicadorNovo.equals("")){
						consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelRegistroAtendimento);
						// idImovelRegistroAtendimento = idImovelRegistroAtendimento;
					}else if(!(idImovelRegistroAtendimento.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
						idImovelRegistroAtendimento = idImovelPrincipalAba;
					}
				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelRegistroAtendimento = idImovelPrincipalAba;
			}

			// Obtém a instância da Fachada
			Fachada fachada = Fachada.getInstancia();
			Imovel imovel = null;
			// verifica se o objeto imovel é o mesmo ja pesquisado, para não precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;
			if(sessao.getAttribute("imovelRegistroAtendimento") != null){
				imovel = (Imovel) sessao.getAttribute("imovelRegistroAtendimento");
				if(idImovelRegistroAtendimento != null && !(imovel.getId().toString().equals(idImovelRegistroAtendimento.trim()))){
					imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelRegistroAtendimento.trim()));
					imovelNovoPesquisado = true;
				}
			}else if(idImovelRegistroAtendimento != null){
				imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelRegistroAtendimento.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){
				sessao.setAttribute("imovelRegistroAtendimento", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelRegistroAtendimento(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado anterior ou seja a primeira
				// vez que se esteja pesquisando
				if(imovelNovoPesquisado){
					// seta na tela a inscrição do imovel
					httpServletRequest.setAttribute("idImovelRegistroAtendimentoNaoEncontrado", null);

					consultarImovelActionForm.setMatriculaImovelRegistroAtendimento(fachada.pesquisarInscricaoImovel(imovel.getId(), true));

					try{
						if(ParametroCadastro.P_MATRICULA_COM_DIGITO_VERIFICADOR.executar().toString()
										.equals(ConstantesSistema.NAO.toString())){
							if(ParametroCadastro.P_METODO_CALCULO_DIGITO_VERIFICADOR.executar().toString().equals("1")){
								consultarImovelActionForm.setDigitoVerificadorImovelRegistroAtendimento(Imovel
												.getDigitoVerificadorMatricula(imovel.getId().toString().trim()));
							}
						}
					}catch(ControladorException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new ActionServletException(e1.getMessage(), e1);
					}

					// seta a situação de agua
					if(imovel.getLigacaoAguaSituacao() != null){
						consultarImovelActionForm.setSituacaoAguaRegistroAtendimento(imovel.getLigacaoAguaSituacao().getDescricao());
					}
					// seta a situação de esgoto
					if(imovel.getLigacaoEsgotoSituacao() != null){
						consultarImovelActionForm.setSituacaoEsgotoRegistroAtendimento(imovel.getLigacaoEsgotoSituacao().getDescricao());
					}
					// seta o tipo de ligação
					if(idImovelRegistroAtendimento != null || idImovelRegistroAtendimento != ""){
						boolean tipoLigacaoBoolean = false;
						ObterIndicadorExistenciaHidrometroHelper obterIndicadorExistenciaHidrometroHelper = fachada
										.obterIndicadorExistenciaHidrometroLigacaoAguaPoco(Util.obterInteger(idImovelRegistroAtendimento),
														tipoLigacaoBoolean);
						if(obterIndicadorExistenciaHidrometroHelper.getIndicadorLigacaoAgua().intValue() == 1
										|| obterIndicadorExistenciaHidrometroHelper.getIndicadorPoco().intValue() == 1){
							consultarImovelActionForm.setTipoLigacao("Hidrometrado");
						}else{
							consultarImovelActionForm.setTipoLigacao("Consumo Fixo");
						}

					}
					// Imovel Comentário
					FiltroImovelComentario filtroImovelComentario = new FiltroImovelComentario();
					filtroImovelComentario.adicionarParametro(new ParametroSimples(FiltroImovelComentario.IMOVEL_ID, imovel.getId()));
					filtroImovelComentario.adicionarParametro(new ParametroSimples(FiltroImovelComentario.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroImovelComentario.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelComentario.USUARIO);
					filtroImovelComentario.setCampoOrderByDesc(FiltroImovelComentario.SEQUENCIAL);

					Collection<ImovelComentario> colecaoImovelComentario = fachada.pesquisar(filtroImovelComentario,
									ImovelComentario.class.getName());

					Collection colecaoImovelComentarioHelper = null;

					if(colecaoImovelComentario != null && !colecaoImovelComentario.isEmpty()){

						Iterator iteratorColecaoImovelComentario = colecaoImovelComentario.iterator();
						colecaoImovelComentarioHelper = new ArrayList();
						ImovelComentarioHelper imovelComentarioHelper = null;

						while(iteratorColecaoImovelComentario.hasNext()){

							ImovelComentario imovelComentario = (ImovelComentario) iteratorColecaoImovelComentario.next();
							imovelComentarioHelper = new ImovelComentarioHelper();

							// Id
							imovelComentarioHelper.setId(imovelComentario.getId().toString());

							// Comentário
							imovelComentarioHelper.setDescricao(imovelComentario.getDescricao().toString());

							// Sequencial Inclusão
							if(imovelComentario.getSequencial() != null){

								imovelComentarioHelper.setSequencialInclusao(imovelComentario.getSequencial().toString() + "º");
							}else{

								imovelComentarioHelper.setSequencialInclusao("");
							}

							// Data Inclusão
							imovelComentarioHelper.setDataInclusao(Util.formatarData(imovelComentario.getDataInclusao()));

							// Usuário
							imovelComentarioHelper.setUsuario(imovelComentario.getUsuario().getNomeUsuario());

							if(imovelComentario.getUsuario().getId().equals(this.getUsuarioLogado(httpServletRequest).getId())){

								imovelComentarioHelper.setUsuarioPossuiPermissaoAlteracao(ConstantesSistema.SIM.toString());
							}else{

								imovelComentarioHelper.setUsuarioPossuiPermissaoAlteracao(ConstantesSistema.NAO.toString());
							}

							colecaoImovelComentarioHelper.add(imovelComentarioHelper);
						}
					}

					sessao.setAttribute("colecaoImovelComentarioHelper", colecaoImovelComentarioHelper);
					// Fim Imovel Comentário
				}

				// Carrega as RA's do Imóvel
				Collection colecaoRegistroAtendimento = fachada.consultarRegistroAtendimentoImovel(imovel.getId(), null);

				Collection colecaoConsultarImovelRegistroAtendimentoHelper = null;

				if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

					Iterator iteratorColecaoRegistroAtendimento = colecaoRegistroAtendimento.iterator();

					colecaoConsultarImovelRegistroAtendimentoHelper = new ArrayList();

					while(iteratorColecaoRegistroAtendimento.hasNext()){
						RegistroAtendimento registroAtendimento = (RegistroAtendimento) iteratorColecaoRegistroAtendimento.next();

						ConsultarImovelRegistroAtendimentoHelper consultarImovelRegistroAtendimentoHelper = new ConsultarImovelRegistroAtendimentoHelper();

						// id registro atendimento
						if(registroAtendimento != null && registroAtendimento.getId() != null){
							consultarImovelRegistroAtendimentoHelper.setIdRegistroAtendimento(registroAtendimento.getId().toString());
						}

						// tipo de solicitação
						if(registroAtendimento != null && registroAtendimento.getSolicitacaoTipoEspecificacao() != null
										&& registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo() != null){

							consultarImovelRegistroAtendimentoHelper.setTipoSolicitacao(registroAtendimento
											.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getDescricao());
						}

						// especificação
						if(registroAtendimento != null && registroAtendimento.getSolicitacaoTipoEspecificacao() != null){
							consultarImovelRegistroAtendimentoHelper.setEspecificacao(registroAtendimento.getSolicitacaoTipoEspecificacao()
											.getDescricao());
						}

						// data de atendimento
						if(registroAtendimento != null && registroAtendimento.getRegistroAtendimento() != null){
							consultarImovelRegistroAtendimentoHelper.setDataAtendimento(Util.formatarData(registroAtendimento
											.getRegistroAtendimento()));
						}

						if(registroAtendimento != null && registroAtendimento.getId() != null){
							// situacao
							ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRAHelper = fachada
											.obterDescricaoSituacaoRA(registroAtendimento.getId());
							consultarImovelRegistroAtendimentoHelper.setSituacao(obterDescricaoSituacaoRAHelper
											.getDescricaoAbreviadaSituacao());

							// Unidade de Atendimento
							FiltroRegistroAtendimentoUnidade filtroRegistroAtendimentoUnidade = new FiltroRegistroAtendimentoUnidade();
							filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(
											FiltroRegistroAtendimentoUnidade.REGISTRO_ATENDIMENTO_ID, registroAtendimento.getId()));
							filtroRegistroAtendimentoUnidade
											.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoUnidade.UNIDADE_ORGANIZACIONAL);

							Collection colecaoRegistroAtendimentoUnidade = fachada.pesquisar(filtroRegistroAtendimentoUnidade,
											RegistroAtendimentoUnidade.class.getName());

							if(colecaoRegistroAtendimentoUnidade != null){
								RegistroAtendimentoUnidade registroAtendimentoUnidade = (RegistroAtendimentoUnidade) Util
												.retonarObjetoDeColecao(colecaoRegistroAtendimentoUnidade);

								if(registroAtendimentoUnidade != null && registroAtendimentoUnidade.getUnidadeOrganizacional() != null){
									consultarImovelRegistroAtendimentoHelper.setUnidadeAtendimento(registroAtendimentoUnidade
													.getUnidadeOrganizacional().getSigla());
								}

							}
						}

						colecaoConsultarImovelRegistroAtendimentoHelper.add(consultarImovelRegistroAtendimentoHelper);
					}

					// Track No. 644 : Consultar Imóvel - Aba RA - Ordenação
					Collections.sort((List) colecaoConsultarImovelRegistroAtendimentoHelper, new Comparator() {

						public int compare(Object a, Object b){

							String data1 = ((ConsultarImovelRegistroAtendimentoHelper) a).getDataAtendimento();
							String data2 = ((ConsultarImovelRegistroAtendimentoHelper) b).getDataAtendimento();

							data1 = data1.substring(6, 10) + data1.substring(3, 5) + data1.substring(0, 2);
							data2 = data2.substring(6, 10) + data2.substring(3, 5) + data2.substring(0, 2);

							Integer dtAtendimento1 = Integer.decode(data1);
							Integer dtAtendimento2 = Integer.decode(data2);

							return dtAtendimento2.compareTo(dtAtendimento1);
						}
					});
				}

				sessao.setAttribute("colecaoConsultarImovelRegistroAtendimentoHelper", colecaoConsultarImovelRegistroAtendimentoHelper);

				// Colecao de Ordens de Servico
				FiltroOrdemServico filtroOS = new FiltroOrdemServico();
				filtroOS.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID_IMOVEL, String.valueOf(imovel.getId())));
				filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
				filtroOS.setCampoOrderBy(FiltroOrdemServico.DATA_GERACAO + " desc");
				Collection colecaoOS = fachada.pesquisar(filtroOS, OrdemServico.class.getName());
				consultarImovelActionForm.setColecaoOS(colecaoOS);

				// Colecao de Atendimentos
				FiltroAtendimento filtroAtendimento = new FiltroAtendimento();
				filtroAtendimento.adicionarParametro(new ParametroSimples(FiltroAtendimento.ID_IMOVEL, String.valueOf(imovel.getId())));
				filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATENDIMENTO);
				filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATEND_FUNCIONALIDADE);
				filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.USUARIO);

				filtroAtendimento.setCampoOrderByDesc(FiltroAtendimento.DATA_INICIO_ATENDIMENTO);

				Collection<Atendimento> colecaoAtendimento = fachada.pesquisar(filtroAtendimento, Atendimento.class.getName());
				consultarImovelActionForm.setColecaoAtendimento(colecaoAtendimento);

			}else{
				httpServletRequest.setAttribute("idImovelRegistroAtendimentoNaoEncontrado", "true");
				consultarImovelActionForm.setMatriculaImovelRegistroAtendimento("IMÓVEL INEXISTENTE");

				// limpar os dados pesquisados
				sessao.removeAttribute("imovelRegistroAtendimento");

				consultarImovelActionForm.setDigitoVerificadorImovelRegistroAtendimento(null);
				consultarImovelActionForm.setIdImovelRegistroAtendimento(null);
				consultarImovelActionForm.setSituacaoAguaRegistroAtendimento(null);
				consultarImovelActionForm.setSituacaoEsgotoRegistroAtendimento(null);
				consultarImovelActionForm.setTipoLigacao(null);
				sessao.removeAttribute("colecaoConsultarImovelRegistroAtendimentoHelper");
				sessao.removeAttribute("colecaoImovelComentarioHelper");
			}
		}else{
			consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelRegistroAtendimento);

			httpServletRequest.setAttribute("idImovelRegistroAtendimentoNaoEncontrado", null);

			sessao.removeAttribute("imovelRegistroAtendimento");
			sessao.removeAttribute("idImovelPrincipalAba");

			consultarImovelActionForm.setMatriculaImovelRegistroAtendimento(null);
			consultarImovelActionForm.setDigitoVerificadorImovelRegistroAtendimento(null);
			consultarImovelActionForm.setSituacaoAguaRegistroAtendimento(null);
			consultarImovelActionForm.setSituacaoEsgotoRegistroAtendimento(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setColecaoOS(null);
			consultarImovelActionForm.setColecaoAtendimento(null);
			sessao.removeAttribute("colecaoConsultarImovelRegistroAtendimentoHelper");
			sessao.removeAttribute("colecaoImovelComentarioHelper");
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
