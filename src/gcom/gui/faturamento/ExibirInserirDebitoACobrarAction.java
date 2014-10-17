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

package gcom.gui.faturamento;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir D�bito a Cobrar ao Imovel [UC0183] Inserir D�bito a Cobrar
 * 
 * @author Rafael Santos
 * @since 21/12/2005
 * @author Saulo Lima
 * @date 26/01/2009
 *       Altera��o para pegar o cliente usu�rio do imovel que tenha dataFimRelacao igual a nulo
 */
public class ExibirInserirDebitoACobrarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirDebitoACobrar");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		InserirDebitoACobrarActionForm inserirDebitoACobrarActionForm = (InserirDebitoACobrarActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String limparForm = (String) httpServletRequest.getParameter("limparForm");
		String idImovel = null;
		String idRegistroAtendimento = inserirDebitoACobrarActionForm.getRegistroAtendimento();
		String idOrdemServico = inserirDebitoACobrarActionForm.getOrdemServico();
		String idImovelInformado = inserirDebitoACobrarActionForm.getIdImovel();

		sessao.removeAttribute("travarValorServico");

		// String idRegistroAtendimento = inserirDebitoACobrarActionForm
		// .getRegistroAtendimento();

		String idDebitoTipo = inserirDebitoACobrarActionForm.getIdTipoDebito();

		BigDecimal percentualTaxaJurosFinanciamento = fachada.pesquisarParametrosDoSistema().getPercentualTaxaJurosFinanciamento();
		inserirDebitoACobrarActionForm.setTaxaJurosFinanciamento(percentualTaxaJurosFinanciamento.toString().replace('.', ','));
		inserirDebitoACobrarActionForm.setNumeroPrestacoes("1");

		if(httpServletRequest.getParameter("objetoConsulta") != null
						&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("1")){

			// pesquisar debito Tipo
			if(idDebitoTipo != null && !idDebitoTipo.trim().equals("")){

				DebitoTipo debitoTipo = fachada.pesquisarDebitoTipo(idDebitoTipo);

				if(debitoTipo != null){

					// [FS0008] - Verificar Exist�ncia de d�bito acobrar para o registro de
					// atendimento
					inserirDebitoACobrarActionForm.setIdTipoDebito(debitoTipo.getId().toString());
					inserirDebitoACobrarActionForm.setDescricaoTipoDebito(debitoTipo.getDescricao());

					BigDecimal valorPadraoDebitoTipo = BigDecimal.ZERO;
					// Inclui no valor total do servi�o o valor padr�o do d�bito tipo
					if(!Util.isVazioOuBranco(debitoTipo.getValorPadrao())){
						valorPadraoDebitoTipo = debitoTipo.getValorPadrao();
					}

					// [OC1213341] - Verificar se existe valor do Debito Tipo para a Localidade se
					// positivo substituir o valor padr�o pelo valor encontrado.
					BigDecimal valorDebitoLocalidade = fachada.verificarDebitoTipoValorLocalidade(
									Util.converterStringParaInteger(idImovelInformado), Util.converterStringParaInteger(idDebitoTipo));

					if(valorDebitoLocalidade != null){
						valorPadraoDebitoTipo = valorDebitoLocalidade;
					}


					inserirDebitoACobrarActionForm.setValorTotalServico(Util.formatarMoedaReal(valorPadraoDebitoTipo));

					httpServletRequest.setAttribute("corDebitoTipo", "valor");
					httpServletRequest.setAttribute("nomeCampo", "valorTotalServico");

				}else{

					inserirDebitoACobrarActionForm.setIdTipoDebito("");
					inserirDebitoACobrarActionForm.setDescricaoTipoDebito("Tipo de D�bito Inexistente");
					httpServletRequest.setAttribute("corDebitoTipo", null);
					httpServletRequest.setAttribute("nomeCampo", "idTipoDebito");

				}

			}

		}else if((idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals(""))
						|| (idOrdemServico != null && !idOrdemServico.trim().equals(""))
						|| (idImovelInformado != null && !idImovelInformado.equals(""))){

			// pesquisou pela RA
			if(httpServletRequest.getParameter("objetoConsulta") != null
							&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("2")){

				// pesquisa o imovel pelo registro de atendimento
				if(idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals("")){

					FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
					filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, idRegistroAtendimento));
					filtroRegistroAtendimento
									.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
					filtroRegistroAtendimento
									.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);

					Collection colecaoRegistroAtendimento = fachada.pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class
									.getName());

					if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

						RegistroAtendimento registroAtendimento = (RegistroAtendimento) colecaoRegistroAtendimento.iterator().next();

						// Registro de Atendimento n�o est� associado a um im�vel
						if(registroAtendimento.getImovel() == null){
							// FS0005 - Validar Registro de Atendimento
							throw new ActionServletException("atencao.registro_atendimento.nao.associado.imovel");
						}

						// Registro de Atendimento est� encerradp
						if(registroAtendimento.getAtendimentoMotivoEncerramento() != null){
							// FS0005 - Validar Registro de Atendimento
							throw new ActionServletException("atencao.registro_atendimento.esta.encerrado");
						}

						// Especifica��o do Tipo de Solicita��o do Registro de Atendimento n�o
						// permite cobran�a de d�bito
						if(registroAtendimento.getSolicitacaoTipoEspecificacao().getIndicadorGeracaoDebito() == 2){
							// FS0005 - Validar Registro de Atendimento
							throw new ActionServletException("atencao.registro_atendimento.nao.permite.cobranca.debito");
						}

						// caso tenha o imovel
						idImovel = registroAtendimento.getImovel().getId().toString();

						inserirDebitoACobrarActionForm.setRegistroAtendimento(registroAtendimento.getId().toString());
						inserirDebitoACobrarActionForm.setNomeRegistroAtendimento(registroAtendimento.getSolicitacaoTipoEspecificacao()
										.getDescricao());
						inserirDebitoACobrarActionForm.setIdImovel(idImovel);

						httpServletRequest.setAttribute("corRegistroAtendimento", "valor");
						httpServletRequest.setAttribute("nomeCampo", "ordemServico");

						sessao.setAttribute("travarRegistroAtendimento", "nao");
						sessao.setAttribute("travarOrdemServico", null);
						sessao.setAttribute("travarImovel", null);
						inserirDebitoACobrarActionForm.setOrdemServico("");
						inserirDebitoACobrarActionForm.setNomeOrdemServico("");

						sessao.setAttribute("travarDebitoTipo", "nao");
						// n�o encontrou a RA

					}else{

						// FS0004-Validar Registro de Atendimento
						inserirDebitoACobrarActionForm.setCodigoImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovel("");
						inserirDebitoACobrarActionForm.setNomeCliente("");
						inserirDebitoACobrarActionForm.setSituacaoAgua("");
						inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
						inserirDebitoACobrarActionForm.setNomeRegistroAtendimento("RA inexistente");
						httpServletRequest.setAttribute("corRegistroAtendimento", "exception");
						httpServletRequest.setAttribute("nomeCampo", "registroAtendimento");
						sessao.setAttribute("travarRegistroAtendimento", "nao");

					}
				}
			}

			// pesquisou pela ordem de seri�o
			if(httpServletRequest.getParameter("objetoConsulta") != null
							&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("3")){

				// pesquisa o imovel pela ordem de servi�o
				if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

					FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
					filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, idOrdemServico));
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("imovel");
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.REGISTRO_ATENDIMENTO);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DEBITO_TIPO);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SOLICITACAO_TIPO_ESPECIFICACAO);
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("cobrancaDocumento.imovel");

					Collection colecaoOrdemServico = fachada.pesquisar(filtroOrdemServico, OrdemServico.class.getName());

					if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){

						OrdemServico ordemServico = (OrdemServico) colecaoOrdemServico.iterator().next();

						// //Ordem de Servi�o n�o est� associada a um Registro de Atendimento
						// if(ordemServico.getRegistroAtendimento() == null){
						// //FS0005 - Validar Registro de Atendimento
						// throw new ActionServletException(
						// "atencao.ordem_servico.nao.esta.associado.registro_atendimento");
						// }

						// caso tenha o imovel do RA
						if(ordemServico.getRegistroAtendimento() != null){
							// Registro de Atendimento da Ordem de Servi�o n�o est� associado a um
							// im�vel
							if(ordemServico.getRegistroAtendimento().getImovel() == null){
								// FS0007 - Validar Ordem de Servi�o
								throw new ActionServletException("atencao.ordem_servico.imovel.registro_atendimento.nao.associado");

							}else{

								idImovel = ordemServico.getRegistroAtendimento().getImovel().getId().toString();

							}

						}else{

							if(ordemServico.getCobrancaDocumento() != null){

								idImovel = ordemServico.getCobrancaDocumento().getImovel().getId().toString();

							}else if(ordemServico.getImovel() != null){
								Imovel imovel = ordemServico.getImovel();
								idImovel = Integer.toString(imovel.getId());
							}

						}

						inserirDebitoACobrarActionForm.setOrdemServico(ordemServico.getId().toString());
						inserirDebitoACobrarActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());
						inserirDebitoACobrarActionForm.setIdImovel(idImovel);

						if(ordemServico.getRegistroAtendimento() != null){

							inserirDebitoACobrarActionForm.setRegistroAtendimento(ordemServico.getRegistroAtendimento().getId().toString());
							inserirDebitoACobrarActionForm.setNomeRegistroAtendimento(ordemServico.getRegistroAtendimento()
											.getSolicitacaoTipoEspecificacao().getDescricao());

						}else{

							inserirDebitoACobrarActionForm.setRegistroAtendimento("");
							inserirDebitoACobrarActionForm.setNomeRegistroAtendimento("");

						}

						// seta a RA
						sessao.setAttribute("travarRegistroAtendimento", null);
						sessao.setAttribute("travarImovel", null);

						httpServletRequest.setAttribute("corRegistroAtendimento", "valor");

						httpServletRequest.setAttribute("corNomeOrdemServico", "valor");
						httpServletRequest.setAttribute("nomeCampo", "idTipoDebito");

						sessao.setAttribute("travarOrdemServico", "nao");
						sessao.setAttribute("travarDebitoTipo", "nao");

						// validar debito tipo
						if(ordemServico.getServicoTipo().getDebitoTipo() != null){

							idDebitoTipo = ordemServico.getServicoTipo().getDebitoTipo().getId().toString();
							inserirDebitoACobrarActionForm.setIdTipoDebito(idDebitoTipo);
							inserirDebitoACobrarActionForm.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo()
											.getDescricao());
							sessao.setAttribute("travarDebitoTipo", null);

							// Caso OS com debito tipo: sugere o valor do servi�o.
							if(ordemServico.getServicoTipo().getValor() != null){
								// Verificar permiss�o especial
								boolean temPermissaoAlterarDebitoACobrarValorServico = fachada
												.verificarPermissaoAlterarDebitoACobrarValorServico(usuario);
								// exibe o valor do servi�o
								inserirDebitoACobrarActionForm.setValorTotalServico(Util.formatarMoedaReal(ordemServico.getServicoTipo()
												.getValor()));
								// habilita o campo apenas se o usu�rio tiver permiss�o especial,
								// caso contr�rio o campo permanece desabilitado.
								if(temPermissaoAlterarDebitoACobrarValorServico){

									sessao.setAttribute("travarValorServico", null);

								}else{

									sessao.setAttribute("travarValorServico", "valor");

								}

							}

						}else{

							sessao.setAttribute("travarDebitoTipo", "valor");
							sessao.setAttribute("travarValorServico", null);

						}

						// n�o encontrou a RA

					}else{

						// FS0004-Validar Ordem Servico
						inserirDebitoACobrarActionForm.setCodigoImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovel("");
						inserirDebitoACobrarActionForm.setNomeCliente("");
						inserirDebitoACobrarActionForm.setSituacaoAgua("");
						inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
						inserirDebitoACobrarActionForm.setNomeOrdemServico("OS inexistente");
						httpServletRequest.setAttribute("corNomeOrdemServico", "exception");
						httpServletRequest.setAttribute("nomeCampo", "ordemServico");
						sessao.setAttribute("travarOrdemServico", "nao");
						sessao.setAttribute("travarDebitoTipo", "nao");

					}
				}
			}

			// pesquisou pelo Im�vel
			if(httpServletRequest.getParameter("objetoConsulta") != null
							&& httpServletRequest.getParameter("objetoConsulta").equalsIgnoreCase("4")){

				// pesquisa o imovel pelo registro de atendimento
				if(idImovelInformado != null && !idImovelInformado.trim().equals("")){

					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovelInformado));

					Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

					if(colecaoImovel != null && !colecaoImovel.isEmpty()){

						Imovel imovel = (Imovel) colecaoImovel.iterator().next();

						// caso tenha o imovel
						idImovel = imovel.getId().toString();

						inserirDebitoACobrarActionForm.setIdImovel(imovel.getId().toString());

						httpServletRequest.setAttribute("corRegistroAtendimento", "valor");
						httpServletRequest.setAttribute("nomeCampo", "idImovel");

						sessao.setAttribute("travarRegistroAtendimento", null);
						sessao.setAttribute("travarOrdemServico", null);
						inserirDebitoACobrarActionForm.setOrdemServico("");
						inserirDebitoACobrarActionForm.setNomeOrdemServico("");

						sessao.setAttribute("travarDebitoTipo", "nao");

						sessao.setAttribute("travarImovel", "n�o");
						// n�o encontrou o im�vel

					}else{

						// [UC0183][FS0018] � Validar exist�ncia do im�vel
						inserirDebitoACobrarActionForm.setIdImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovelInformada("Im�vel Inexistente");
						inserirDebitoACobrarActionForm.setCodigoImovel("");
						inserirDebitoACobrarActionForm.setInscricaoImovel("");
						inserirDebitoACobrarActionForm.setNomeCliente("");
						inserirDebitoACobrarActionForm.setSituacaoAgua("");
						inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
						httpServletRequest.setAttribute("corInscricao", "exception");

					}
				}
			}

			// verifica o imovel para ser carregado
			if(idImovel != null && !idImovel.equals("")){

				// Pesquisa o imovel na base
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
				/*
				 * filtroImovel
				 * .adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao.id");
				 */
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
				filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO,
								Imovel.IMOVEL_EXCLUIDO, FiltroParametro.CONECTOR_OR, 2));
				filtroImovel.adicionarParametro(new ParametroNulo(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO));

				Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel

				if(imovelPesquisado == null || imovelPesquisado.isEmpty()){

					inserirDebitoACobrarActionForm.setCodigoImovel("");
					inserirDebitoACobrarActionForm.setInscricaoImovel("Matr�cula Inexistente");
					inserirDebitoACobrarActionForm.setNomeCliente("");
					inserirDebitoACobrarActionForm.setSituacaoAgua("");
					inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
					httpServletRequest.setAttribute("corMatriculaImovel", null);
					httpServletRequest.setAttribute("nomeCampo", "codigoImovel");

				}

				// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel
				// Excluido
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){

					Imovel imovel = imovelPesquisado.iterator().next();

					if(imovel.getIndicadorExclusao() == Imovel.IMOVEL_EXCLUIDO){

						throw new ActionServletException("atencao.pesquisa.imovel.excluido");

					}

				}

				// [FS0002 - Verificar usu�rio com d�bito em cobran�a
				// administrativa]
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){

					Imovel imovel = imovelPesquisado.iterator().next();

					FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
					filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
									CobrancaSituacao.COBRANCA_ADMINISTRATIVA));
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel
									.getId()));

					Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovelCobrancaSituacao,
									ImovelCobrancaSituacao.class.getName());

					// Verifica se o im�vel tem d�bito em cobran�a administrativa
					if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){
						// C�digo comentado para a customiza��o da cobran�a administrativa CASAL
						// throw new
						// ActionServletException("atencao.pesquisa.imovel.cobranca_administrativa");
					}

				}

				// [FS0003 - Verificar situa��o liga��o de �gua e esgoto]
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
					Imovel imovel = imovelPesquisado.iterator().next();

					if((imovel.getLigacaoAguaSituacao() != null)
									&& ((imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.POTENCIAL.intValue()) || (imovel
													.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.FACTIVEL.intValue()))
									&& (imovel.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.LIGADO.intValue())){

						/*
						 * Colocado por Raphael Rossiter em 03/10/2007
						 * OBJ: Inserir debito a cobrar independente da situacao da ligacao de agua
						 * e esgoto
						 * do imovel
						 */
						boolean temPermissaoInserirDebitoACobrarImovelSituacao = fachada
										.verificarPermissaoInserirDebitoACobrarImovelSituacao(usuario);

						if(!temPermissaoInserirDebitoACobrarImovelSituacao){
							throw new ActionServletException("atencao.pesquisa.imovel.inativo");
						}

					}

				}

				/*
				 * Fluxo Principal - PASSO 4 FiltroDebitoTipo filtroDebitoTipo = new
				 * FiltroDebitoTipo();
				 * filtroDebitoTipo.adicionarParametro(new ParametroSimples(
				 * FiltroDebitoTipo.INDICADOR_USO,
				 * ConstantesSistema.INDICADOR_USO_ATIVO));
				 * filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
				 * filtroDebitoTipo.adicionarParametro(new ParametroSimples(
				 * FiltroDebitoTipo.FINANCIAMENTO_TIPO, FinanciamentoTipo.NORMAL));
				 * filtroDebitoTipo.adicionarParametro( new ParametroSimples(
				 * filtroDebitoTipo.INDICADOR_GERACAO_AUTOMATICA, DebitoTipo.
				 * //COMPARAR COM 2 ))
				 */

				// Obtem o cliente imovel do imovel pesquisado
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){

					Imovel imovel = imovelPesquisado.iterator().next();

					sessao.setAttribute("imovelPesquisado", imovel);

					FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
					filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
					filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
					filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
									ClienteRelacaoTipo.USUARIO));
					filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

					Collection<ClienteImovel> clienteImovelPesquisado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class
									.getName());

					if(clienteImovelPesquisado != null && !clienteImovelPesquisado.isEmpty()){

						ClienteImovel clienteImovel = clienteImovelPesquisado.iterator().next();
						if(clienteImovel.getCliente() != null){
							inserirDebitoACobrarActionForm.setNomeCliente(clienteImovel.getCliente().getNome());
						}

					}

					if(imovel.getLigacaoAguaSituacao() != null){

						inserirDebitoACobrarActionForm.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());

					}

					if(imovel.getLigacaoEsgotoSituacao() != null){

						inserirDebitoACobrarActionForm.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());

					}

					inserirDebitoACobrarActionForm.setCodigoImovel(idImovel);
					inserirDebitoACobrarActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());

					if(idImovelInformado != null && !idImovelInformado.equals("")){

						inserirDebitoACobrarActionForm.setInscricaoImovelInformada(imovel.getInscricaoFormatada());

					}

					/*
					 * Sugere a data do In�cio de Cobran�a como sendo a
					 * "data refer�ncia" do grupo de faturamento do qual o
					 * im�vel faz parte.
					 * Saulo Lima 29.07.2008
					 */
					if(imovel.getRota() != null && imovel.getRota().getFaturamentoGrupo() != null){
						/*
						 * Quadra quadra = imovel.getQuadra();
						 * Rota rota = null;
						 * FiltroRota filtroRota = new FiltroRota();
						 * filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA,
						 * imovel.getRota().getId()));
						 * Collection colecaoRota = fachada.pesquisar(filtroRota,
						 * Rota.class.getName());
						 * if(colecaoRota != null && !colecaoRota.isEmpty()){
						 * rota = (Rota) colecaoRota.iterator().next();
						 * }
						 * if(rota != null){
						 */
						FaturamentoGrupo faturamentoGrupo = null;
						FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
						filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, imovel.getRota()
										.getFaturamentoGrupo().getId()));
						Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

						if(colecaoFaturamentoGrupo != null && !colecaoFaturamentoGrupo.isEmpty()){

							faturamentoGrupo = (FaturamentoGrupo) colecaoFaturamentoGrupo.iterator().next();

						}

						if(faturamentoGrupo != null){

							Integer anoMes = faturamentoGrupo.getAnoMesReferencia();
							inserirDebitoACobrarActionForm.setAnoMesCobrancaDebito(Util.formatarMesAnoReferencia(Integer.parseInt(""
											+ anoMes)));

						}

					}

					/*
					 * inserirDebitoACobrarActionForm.setRegistroAtendimento("");
					 * inserirDebitoACobrarActionForm.setOrdemServico("");
					 * inserirDebitoACobrarActionForm.setIdTipoDebito("");
					 * inserirDebitoACobrarActionForm.setDescricaoTipoDebito("");
					 * inserirDebitoACobrarActionForm.setValorPrestacao("");
					 * inserirDebitoACobrarActionForm.setValorEntrada("");
					 * inserirDebitoACobrarActionForm.setValorTotalServico("");
					 * inserirDebitoACobrarActionForm.setValorTotalServicoAParcelar("");
					 * inserirDebitoACobrarActionForm.setvalorTotalServicoAParcelar("");
					 * inserirDebitoACobrarActionForm.setValorJuros("");
					 * inserirDebitoACobrarActionForm.setPercentualAbatimento("");
					 * inserirDebitoACobrarActionForm.setNumeroPrestacoes("");
					 */

					httpServletRequest.setAttribute("corMatriculaImovel", "valor");
					httpServletRequest.setAttribute("nomeCampo", "idTipoDebito");

				}

			}// fim do imovel

		}else{

			sessao.setAttribute("travarRegistroAtendimento", "nao");
			sessao.setAttribute("travarDebitoTipo", "nao");
			sessao.setAttribute("travarOrdemServico", "nao");
			sessao.setAttribute("travarImovel", "nao");

		}

		if(limparForm != null && !limparForm.trim().equalsIgnoreCase("")){

			inserirDebitoACobrarActionForm.setRegistroAtendimento("");
			inserirDebitoACobrarActionForm.setOrdemServico("");
			inserirDebitoACobrarActionForm.setCodigoImovel("");
			inserirDebitoACobrarActionForm.setAnoMesCobrancaDebito("");
			inserirDebitoACobrarActionForm.setIdImovel("");
			inserirDebitoACobrarActionForm.setInscricaoImovel("");
			inserirDebitoACobrarActionForm.setInscricaoImovelInformada("");
			inserirDebitoACobrarActionForm.setNomeCliente("");
			inserirDebitoACobrarActionForm.setIdTipoDebito("");
			inserirDebitoACobrarActionForm.setSituacaoAgua("");
			inserirDebitoACobrarActionForm.setSituacaoEsgoto("");
			inserirDebitoACobrarActionForm.setDescricaoTipoDebito("");
			inserirDebitoACobrarActionForm.setValorPrestacao("");
			inserirDebitoACobrarActionForm.setValorEntrada("");
			inserirDebitoACobrarActionForm.setValorTotalServico("");
			inserirDebitoACobrarActionForm.setValorTotalServicoAParcelar("");
			inserirDebitoACobrarActionForm.setvalorTotalServicoAParcelar("");
			inserirDebitoACobrarActionForm.setValorJuros("");
			inserirDebitoACobrarActionForm.setPercentualAbatimento("");
			inserirDebitoACobrarActionForm.setNumeroPrestacoes("");
			inserirDebitoACobrarActionForm.setNumeroPrestacoes("1");

			if(sessao.getAttribute("imovelPesquisado") != null){

				sessao.removeAttribute("imovelPesquisado");

			}

		}

		sessao.removeAttribute("informarImovel");

		// -----------------------------------------------------------
		// Verificar permiss�o especial
		boolean temPermissaoInserirDebitoACobrarSemRa = fachada.verificarPermissaoInserirDebitoACobrarSemRa(usuario);
		// -----------------------------------------------------------
		if(temPermissaoInserirDebitoACobrarSemRa){

			sessao.setAttribute("informarImovel", "sim");

		}

		return retorno;
	}

}