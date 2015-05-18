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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.Quadra;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 20/03/2007
 */
public class ExibirAlterarSituacaoLigacaoAction
				extends GcomAction {

	/**
	 * [UC0555] Alterar Situacao da Ligacao
	 * Este caso de uso permite alterar a situacao da ligacao de agua e/ou
	 * esgoto de acordo com o indicadorde rede e Ordem de Servico gerada.
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("alterarSituacaoLigacao");

		AlterarSituacaoLigacaoActionForm form = (AlterarSituacaoLigacaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario usuarioLogado = (Usuario)
		// sessao.getAttribute("usuarioLogado");

		String indicadorRedeAgua = null;

		String indicadorRedeEsgoto = null;

		String idOrdemServico = null;

		String indicadorEsgoto = null;

		idOrdemServico = form.getIdOrdemServico();

		try{

			indicadorEsgoto = ((ParametroSistema) Fachada.getInstancia().obterParametroSistema("P_CRITICAR_ESGOTO_QUADRA")).getValor();

		}catch(Exception e){

			throw new ActionServletException("erro.sistema");

		}

		this.pesquisarSelectObrigatorio(httpServletRequest, form, indicadorRedeAgua, indicadorRedeEsgoto, indicadorEsgoto, null);

		/*
		 * // Verifica se o id da Ordem de servico vem da sessao. if
		 * (sessao.getAttribute("idOrdemServico") != null) { idOrdemServico =
		 * (String) sessao.getAttribute("idOrdemServico"); } else {
		 * idOrdemServico = efetuarSupressaoLigacaoAguaActionForm
		 * .getIdOrdemServico(); }
		 */

		OrdemServico ordemServico = null;

		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if(ordemServico != null){
				boolean veioEncerrarOS = false;

				fachada.validarOrdemServicoAlterarSituacaoLigacao(ordemServico, veioEncerrarOS);

				form.setIdOrdemServico(idOrdemServico);

				form.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				sessao.setAttribute("ordemServico", ordemServico);

				// Comentado por Raphael Rossiter em 28/02/2007
				// Imovel imovel =
				// ordemServico.getRegistroAtendimento().getImovel();
				Imovel imovel = ordemServico.getImovel();

				String matriculaImovel = imovel.getId().toString();
				form.setMatriculaImovel("" + matriculaImovel);

				/*-------------- Início dados do Imóvel---------------*/

				// Comentado por Raphael Rossiter em 28/02/2007
				// sessao.setAttribute("imovel",
				// ordemServico.getRegistroAtendimento().getImovel());
				sessao.setAttribute("imovel", ordemServico.getImovel());

				if(imovel != null){

					// Matricula Imóvel
					form.setMatriculaImovel(imovel.getId().toString());

					// Inscrição Imóvel
					String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
					form.setInscricaoImovel(inscricaoImovel);

					// Situação da Ligação de Agua

					String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
					form.setSituacaoLigacaoAguaAtual(situacaoLigacaoAgua);

					indicadorRedeAgua = imovel.getQuadra().getIndicadorRedeAgua().toString();

					// Situação da Ligação de Esgoto
					String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
					form.setSituacaoLigacaoEsgotoAtual(situacaoLigacaoEsgoto);

					indicadorRedeEsgoto = imovel.getQuadra().getIndicadorRedeEsgoto().toString();

					this.pesquisarSelectObrigatorio(httpServletRequest, form, indicadorRedeAgua, indicadorRedeEsgoto, indicadorEsgoto,
									imovel);

					this.pesquisarCliente(form, new Integer(matriculaImovel));
				}

			}else{

				httpServletRequest.setAttribute("OrdemServicoInexistente", true);
				form.setIdOrdemServico("");
				form.setNomeOrdemServico("ORDEM DE SERVIÇO INEXISTENTE");

			}
		}
		return retorno;
	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 22/08/2006
	 */
	private void pesquisarCliente(AlterarSituacaoLigacaoActionForm form, Integer matriculaImovel){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, matriculaImovel));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, new Integer(
						ClienteRelacaoTipo.USUARIO)));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();

			Cliente cliente = clienteImovel.getCliente();

			String documento = "";

			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			form.setClienteUsuario(cliente.getNome());
			form.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	// Dados da Geração de Débito
	/*
	 * private void pesquisarDadosGeracaoDebito(
	 * EfetuarSupressaoLigacaoAguaActionForm
	 * efetuarSupressaoLigacaoAguaActionForm, OrdemServico ordemServico) { }
	 */

	/**
	 * Pesquisa o local de instalação Pesquisa hidrometro proteção
	 */
	private void pesquisarSelectObrigatorio(HttpServletRequest httpServletRequest, AlterarSituacaoLigacaoActionForm form,
					String indicadorRedeAgua, String indicadorRedeEsgoto, String indicadorEsgoto, Imovel imovel){

		// Mudar isso quando tiver esquema de segurança
		// HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// Vericacao dequal o tipo de supressao informada
		// pelo usuario para
		// habilitar no combo box as opcoes correspondentes

		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		httpServletRequest.setAttribute("colecaoLigacaoAguaSituacao", new ArrayList());

		httpServletRequest.setAttribute("colecaoLigacaoEsgotoSituacao", new ArrayList());

		if(form.getIndicadorTipoLigacao() != null){

			if(form.getIndicadorTipoLigacao().equals("1") || form.getIndicadorTipoLigacao().equals("3")){

				if(indicadorRedeAgua != null){

					String parametroReligarImovelFactivel = null;

					try{
						parametroReligarImovelFactivel = (String) ParametroAtendimentoPublico.P_PERMITE_RELIGAR_IMOVEL_FACTIVEL.executar();
					}catch(ControladorException e){
						throw new ActionServletException("erro.sistema");
					}

					// Combo vira obrigatorio
					httpServletRequest.setAttribute("comboLigacaoAgua", "sim");
					Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = null;

					if(indicadorRedeAgua.equalsIgnoreCase("" + Quadra.SEM_REDE)){

						filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

						filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
										LigacaoAguaSituacao.POTENCIAL));

						colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,
										LigacaoAguaSituacao.class.getName());

					}else if(indicadorRedeAgua.equalsIgnoreCase("" + Quadra.COM_REDE)){

						if(parametroReligarImovelFactivel != null && !parametroReligarImovelFactivel.equals("")
										&& parametroReligarImovelFactivel.equals("" + ConstantesSistema.SIM)
										&& imovel != null
										&& imovel.getLigacaoAguaSituacao() != null
										&& imovel.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.FACTIVEL)
										&& imovel.getImovelPerfil() != null
										&& imovel.getImovelPerfil().getId().equals(ImovelPerfil.FACTIVEL_EM_ESPERA)){
							filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

							filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
											LigacaoAguaSituacao.FACTIVEL, ConectorOr.CONECTOR_OR));
							filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
											LigacaoAguaSituacao.LIGADO, ConectorOr.CONECTOR_OR));

							colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

						}else{
							filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

							filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
											LigacaoAguaSituacao.FACTIVEL));

							colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
						}

					}else if(indicadorRedeAgua.equalsIgnoreCase("" + Quadra.REDE_PARCIAL)){

						filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

						filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
										LigacaoAguaSituacao.POTENCIAL, ConectorOr.CONECTOR_OR, 2));

						filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
										LigacaoAguaSituacao.FACTIVEL, ConectorOr.CONECTOR_OR, 2));

						colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,
										LigacaoAguaSituacao.class.getName());
					}

					if(colecaoLigacaoAguaSituacao == null || colecaoLigacaoAguaSituacao.isEmpty()){
						throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Ligacao Agua Situacao ");
					}

					// form.setIndicadorTipoLigacao("1");
					httpServletRequest.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);
				}
			}

			if(form.getIndicadorTipoLigacao().equals("2") || form.getIndicadorTipoLigacao().equals("3")){

				if(indicadorRedeEsgoto != null){

					// Combo vira obrigatorio
					httpServletRequest.setAttribute("comboLigacaoEsgoto", "sim");
					Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = null;

					if(indicadorEsgoto.equals(ConstantesSistema.NAO.toString())){

						colecaoLigacaoEsgotoSituacao = fachada
										.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

					}else{
						String parametroReligarImovelFactivelFaturado = null;

						try{
							parametroReligarImovelFactivelFaturado = (String) ParametroAtendimentoPublico.P_PERMITE_RELIGAR_IMOVEL_FACTIVEL_FATURAVEL
											.executar();
						}catch(ControladorException e){
							throw new ActionServletException("erro.sistema");
						}

						if(indicadorRedeEsgoto.equalsIgnoreCase("" + Quadra.SEM_REDE)){// valor
																						// sem_rede
																						// = 1

							filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
											LigacaoEsgotoSituacao.POTENCIAL));

							colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
											LigacaoEsgotoSituacao.class.getName());

						}else if(indicadorRedeEsgoto.equalsIgnoreCase("" + Quadra.COM_REDE)){// valor
																								// sem_rede
																								// =
																								// 2
							
							filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
											LigacaoEsgotoSituacao.FACTIVEL, ConectorOr.CONECTOR_OR));

							if(imovel != null && parametroReligarImovelFactivelFaturado != null
											&& !parametroReligarImovelFactivelFaturado.equals("")
											&& parametroReligarImovelFactivelFaturado.equals("" + ConstantesSistema.SIM)){
								
								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId()
																.equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA)){
									
									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.LIGADO, ConectorOr.CONECTOR_OR));
								}
								
								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.LIGADO)){

									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.FACTIVEL_FATURADA, ConectorOr.CONECTOR_OR));
									
								}

								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.FACTIVEL)){

									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.FACTIVEL_FATURADA, ConectorOr.CONECTOR_OR));

								}

								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId()
																.equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA)){

									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.FACTIVEL, ConectorOr.CONECTOR_OR));

								}
							}
							
							colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
											LigacaoEsgotoSituacao.class.getName());

						}else if(indicadorRedeEsgoto.equalsIgnoreCase("" + Quadra.REDE_PARCIAL)){

							filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
											LigacaoEsgotoSituacao.POTENCIAL, ConectorOr.CONECTOR_OR));

							filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
											LigacaoEsgotoSituacao.FACTIVEL, ConectorOr.CONECTOR_OR));

							if(imovel != null && parametroReligarImovelFactivelFaturado != null
											&& !parametroReligarImovelFactivelFaturado.equals("")
											&& parametroReligarImovelFactivelFaturado.equals("" + ConstantesSistema.SIM)){

								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId()
																.equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA)
												&& imovel.getImovelPerfil() != null
												&& imovel.getImovelPerfil().getId().equals(ImovelPerfil.FACTIVEL_FATURADO)){
									
									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.LIGADO, ConectorOr.CONECTOR_OR));

								}
								
								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.LIGADO)){

									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.FACTIVEL_FATURADA, ConectorOr.CONECTOR_OR));
								}
								
								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.FACTIVEL)){

									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.FACTIVEL_FATURADA, ConectorOr.CONECTOR_OR));

								}

								if(imovel.getLigacaoEsgotoSituacao() != null
												&& imovel.getLigacaoEsgotoSituacao().getId()
																.equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA)){

									filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
													LigacaoEsgotoSituacao.FACTIVEL, ConectorOr.CONECTOR_OR));

								}

							}

							colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
											LigacaoEsgotoSituacao.class.getName());
						}
					}


					if(colecaoLigacaoEsgotoSituacao == null || colecaoLigacaoEsgotoSituacao.isEmpty()){
						throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Ligacao Esgoto Situacao");
					}

					// form.setIndicadorTipoLigacao("2");
					httpServletRequest.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);
				}
			}
		}
	}
}
