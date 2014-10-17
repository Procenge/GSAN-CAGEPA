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

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * @author Ailton Sousa
 * @date 18/01/2012
 */
public class ExibirTotalizadoresAlterarImovelInscricaoAction
				extends GcomAction
				implements ExecutorParametro {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm alterarImovelInscricaoActionForm = (DynaValidatorForm) sessao.getAttribute("AlterarImovelInscricaoActionForm");

		String parametroCompanhia = this.getParametroCompanhia(httpServletRequest).toString();

		// Dados da inscrição de origem
		String localidadeOrigemID = (String) alterarImovelInscricaoActionForm.get("localidadeOrigemID");
		String setorComercialOrigemCD = (String) alterarImovelInscricaoActionForm.get("setorComercialOrigemCD");
		String quadraOrigemNM = (String) alterarImovelInscricaoActionForm.get("quadraOrigemNM");
		String loteOrigemTemp = (String) alterarImovelInscricaoActionForm.get("loteOrigem");

		boolean flagContinuar = true;

		Localidade localidadeOrigem = (Localidade) validarCampo(localidadeOrigemID, null, 1);
		SetorComercial setorComercialOrigem = null;
		Quadra quadraOrigem = null;

		// Validação dos campos da inscrição de origem (FS0002, FS0003, FS0004)
		if(localidadeOrigem == null){
			// Nenhuma localidade encontrada
			flagContinuar = false;
			throw new ActionServletException("atencao.pesquisa.localidade_origem_inexistente");
		}else{
			if(setorComercialOrigemCD != null && !setorComercialOrigemCD.equalsIgnoreCase("")){

				setorComercialOrigem = (SetorComercial) validarCampo(localidadeOrigem.getId().toString(), setorComercialOrigemCD, 2);

				if(setorComercialOrigem == null){
					// Nenhum Setor Comercial encontrado
					flagContinuar = false;
					throw new ActionServletException("atencao.pesquisa.setor_origem_inexistente");
				}else{
					if(quadraOrigemNM != null && !quadraOrigemNM.equalsIgnoreCase("")){

						quadraOrigem = (Quadra) validarCampo(setorComercialOrigem.getId().toString(), quadraOrigemNM, 3);

						if(quadraOrigem == null){
							// Nenhuma Quadra encontrada
							flagContinuar = false;
							throw new ActionServletException("atencao.pesquisa.quadra_origem_inexistente");
						}
					}
				}
			}
		}

		// Dados da inscrição de destino
		String localidadeDestinoID = (String) alterarImovelInscricaoActionForm.get("localidadeDestinoID");
		String setorComercialDestinoCD = (String) alterarImovelInscricaoActionForm.get("setorComercialDestinoCD");
		String quadraDestinoNM = (String) alterarImovelInscricaoActionForm.get("quadraDestinoNM");
		String loteDestinoTemp = (String) alterarImovelInscricaoActionForm.get("loteDestino");

		short loteDestino = 0;

		if(loteDestinoTemp != null && !loteDestinoTemp.equalsIgnoreCase("")){
			loteDestino = Short.parseShort(loteDestinoTemp);
		}

		Localidade localidadeDestino = (Localidade) validarCampo(localidadeDestinoID, null, 1);
		SetorComercial setorComercialDestino = null;
		Quadra quadraDestino = null;

		// Valida se a localidade destino e igual à de origem ou pelo menos
		// pertencer à Localidade Origem (ser vinculada à localidade origem,
		// ou seja, LOCA_CDELO da Localidade Destino Igual à Localidade Origem)
		if(!localidadeOrigemID.equals(localidadeDestinoID) && localidadeDestino.getLocalidade() == null){
			flagContinuar = false;
			throw new ActionServletException("atencao.localidade_destino_diferente_origem");
		}else if(!localidadeOrigemID.equals(localidadeDestinoID)
						&& (localidadeDestino.getLocalidade() != null && !localidadeOrigem.getId().equals(
										localidadeDestino.getLocalidade().getId()))){
			flagContinuar = false;
			throw new ActionServletException("atencao.localidade_destino_diferente_origem");
		}

		if(flagContinuar){

			// Validação dos campos da inscrição de destino (FS0002, FS0003,
			// FS0004)
			if(localidadeDestino == null){
				// Nenhuma localidade encontrada
				flagContinuar = false;
				throw new ActionServletException("atencao.pesquisa.localidade_destino_inexistente");
			}else{
				if(setorComercialDestinoCD != null && !setorComercialDestinoCD.equalsIgnoreCase("")){

					setorComercialDestino = (SetorComercial) validarCampo(localidadeDestino.getId().toString(), setorComercialDestinoCD, 2);

					if(setorComercialDestino == null){
						// Nenhum Setor Comercial encontrado
						flagContinuar = false;
						throw new ActionServletException("atencao.pesquisa.setor_destino_inexistente");
					}else{
						if(quadraDestinoNM != null && !quadraDestinoNM.equalsIgnoreCase("")){

							quadraDestino = (Quadra) validarCampo(setorComercialDestino.getId().toString(), quadraDestinoNM, 3);

							if(quadraDestino == null){
								// Nenhuma Quadra encontrada
								flagContinuar = false;
								throw new ActionServletException("atencao.pesquisa.quadra_destino_inexistente");
							}
						}
					}
				}
			}
		}

		// [FS0005 - Verificar preenchimento dos campos]
		if(flagContinuar){

			if(setorComercialOrigem == null && setorComercialDestino == null){
				flagContinuar = compararObjetos(localidadeOrigem, localidadeDestino, 1, parametroCompanhia);
				if(!flagContinuar){
					throw new ActionServletException("atencao.pesquisa.setorcomercial_nao_correspondente");
				}

			}else if(setorComercialOrigem == null && setorComercialDestino != null){
				if(quadraDestino == null){
					// Verificar se o setor de destino existe na localidade de origem
					setorComercialOrigem = (SetorComercial) pesquisarRetornarObjeto(setorComercialDestino, localidadeOrigem, 1);
					// Verificar se existe as mesmas quadras do setor de destino no setor de origem
					if(setorComercialOrigem == null){
						flagContinuar = false;
						throw new ActionServletException("atencao.pesquisa.setorcomercial_nao_correspondente");
					}else{
						flagContinuar = compararObjetos(setorComercialOrigem, setorComercialDestino, 2, parametroCompanhia);
						// Valor original do setor de origem
						setorComercialOrigem = null;
						if(!flagContinuar){
							throw new ActionServletException("atencao.pesquisa.quadra_nao_correspondente");
						}
					}
				}else{
					// Verificar se o setor de destino existe na localidade de origem
					setorComercialOrigem = (SetorComercial) pesquisarRetornarObjeto(setorComercialDestino, localidadeOrigem, 1);
					// Verificar se a quadra de destino existe na localidade de origem
					if(setorComercialOrigem == null){
						flagContinuar = false;
						throw new ActionServletException("atencao.pesquisa.setorcomercial_nao_correspondente");
					}else{
						flagContinuar = pesquisarObjeto(quadraDestino, setorComercialOrigem, 2);
						// Valor original do setor de origem
						setorComercialOrigem = null;
						if(!flagContinuar){
							throw new ActionServletException("atencao.pesquisa.quadra_nao_correspondente");
						}
					}
				}
			}else{
				if(quadraOrigem == null && quadraDestino == null){
					// Verificar se o setor de origem existe na localidade de destino
					flagContinuar = pesquisarObjeto(setorComercialOrigem, localidadeDestino, 1);
					// Verificar se existe as mesmas quadras do setor de origem no setor de destino
					if(flagContinuar){
						flagContinuar = compararObjetos(setorComercialOrigem, setorComercialDestino, 2, parametroCompanhia);

						if(!flagContinuar){
							throw new ActionServletException("atencao.pesquisa.quadra_nao_correspondente");
						}
					}else{
						throw new ActionServletException("atencao.pesquisa.setorcomercial_nao_correspondente");
					}
				}else if(quadraOrigem == null && quadraDestino != null){
					// Verificar se o setor de destino existe na localidade de origem
					flagContinuar = pesquisarObjeto(setorComercialDestino, localidadeOrigem, 1);
					// Verificar se a quadra de destino existe no setor de origem
					if(flagContinuar){
						flagContinuar = pesquisarObjeto(quadraDestino, setorComercialOrigem, 2);
						if(!flagContinuar){
							throw new ActionServletException("atencao.pesquisa.quadra_nao_correspondente");
						}

					}else{
						throw new ActionServletException("atencao.pesquisa.setorcomercial_nao_correspondente");
					}

				}else if(quadraOrigem != null && quadraDestino == null){
					// Verificar se o setor de destino existe na localidade de origem
					flagContinuar = pesquisarObjeto(setorComercialDestino, localidadeOrigem, 1);
					// Verificar se a quadra de origem existe no setor de destino
					if(flagContinuar){
						flagContinuar = pesquisarObjeto(quadraOrigem, setorComercialDestino, 2);
						if(!flagContinuar){
							throw new ActionServletException("atencao.pesquisa.quadra_nao_correspondente");
						}
					}else{
						throw new ActionServletException("atencao.pesquisa.setorcomercial_nao_correspondente");
					}
				}
			}

			try{
				Short indicadorAlteracaoRestritaMesmaLocalidade = Short
								.valueOf((String) ParametroCadastro.P_INDICADOR_ALTERACAO_INSCRICAO_RESTRITA_LOCALIDADE.executar(this));
				// Caso só seja possível a alteração da inscrição dentro da localidade
				if(indicadorAlteracaoRestritaMesmaLocalidade.shortValue() == 1){
					// Caso a Localidade Destino seja diferente da Localidade Origem,
					// exibir a mensagem
					if(localidadeOrigem.getLocalidade() != null && localidadeDestino.getLocalidade() != null
									&& !localidadeOrigem.getLocalidade().getId().equals(localidadeDestino.getLocalidade().getId())){
						flagContinuar = false;
						throw new ActionServletException("atencao.imovel.alteracao_inscricao_restrita_dentro_localidade");
					}
				}

			}catch(NumberFormatException e){
				throw new ActionServletException("erro.sistema");
			}catch(ControladorException e){
				throw new ActionServletException("erro.sistema");
			}

		}

		// [FS0006 – Verificar pré-requisitos para alteração da inscrição]
		if(flagContinuar){
			Collection colecaoSetorOrigem = null;
			Iterator iteratorSetorOrigem = null;
			Collection colecaoQuadraOrigem = null;
			Iterator iteratorQuadraOrigem = null;

			if(setorComercialOrigem == null && setorComercialDestino == null){

				colecaoSetorOrigem = pesquisarDependentes(localidadeOrigem, 1);
				if(colecaoSetorOrigem != null && !colecaoSetorOrigem.isEmpty()){
					iteratorSetorOrigem = (colecaoSetorOrigem).iterator();
					while(iteratorSetorOrigem.hasNext()){
						setorComercialOrigem = (SetorComercial) iteratorSetorOrigem.next();

						colecaoQuadraOrigem = pesquisarDependentes(setorComercialOrigem, 2);
						if(colecaoQuadraOrigem != null && !colecaoQuadraOrigem.isEmpty()){
							iteratorQuadraOrigem = (colecaoQuadraOrigem).iterator();
							while(iteratorQuadraOrigem.hasNext()){
								quadraOrigem = (Quadra) iteratorQuadraOrigem.next();
								if(!verificarSituacaoRota(quadraOrigem)){
									setorComercialDestino = (SetorComercial) pesquisarRetornarObjeto(setorComercialOrigem,
													localidadeDestino, 1);
									quadraDestino = (Quadra) pesquisarRetornarObjeto(quadraOrigem, setorComercialDestino, 2);
									if(verificarSituacaoRota(quadraDestino)){
										flagContinuar = false;
										throw new ActionServletException("atencao.pesquisa.quadra_rota_nao_faturada");
									}
								}
							}
						}
					}
				}
				setorComercialOrigem = null;
				setorComercialDestino = null;
				quadraOrigem = null;
				quadraDestino = null;
			}else if(setorComercialOrigem == null && setorComercialDestino != null){

				setorComercialOrigem = (SetorComercial) pesquisarRetornarObjeto(setorComercialDestino, localidadeOrigem, 1);

				if(quadraDestino == null){

					colecaoQuadraOrigem = pesquisarDependentes(setorComercialOrigem, 2);
					if(colecaoQuadraOrigem != null && !colecaoQuadraOrigem.isEmpty()){
						iteratorQuadraOrigem = (colecaoQuadraOrigem).iterator();
						while(iteratorQuadraOrigem.hasNext()){
							quadraOrigem = (Quadra) iteratorQuadraOrigem.next();
							if(!verificarSituacaoRota(quadraOrigem)){
								quadraDestino = (Quadra) pesquisarRetornarObjeto(quadraOrigem, setorComercialDestino, 2);
								if(verificarSituacaoRota(quadraDestino)){
									flagContinuar = false;
									throw new ActionServletException("atencao.pesquisa.quadra_rota_nao_faturada");
								}
							}
						}
					}
					quadraOrigem = null;
					quadraDestino = null;

				}else{

					quadraOrigem = (Quadra) pesquisarRetornarObjeto(quadraDestino, setorComercialOrigem, 2);
					if(!verificarSituacaoRota(quadraOrigem)){
						if(verificarSituacaoRota(quadraDestino)){
							flagContinuar = false;
							throw new ActionServletException("atencao.pesquisa.quadra_rota_nao_faturada");
						}
					}
					quadraOrigem = null;

				}

				setorComercialOrigem = null;
			}else{
				if(quadraOrigem == null && quadraDestino == null){

					colecaoQuadraOrigem = pesquisarDependentes(setorComercialOrigem, 2);
					if(colecaoQuadraOrigem != null && !colecaoQuadraOrigem.isEmpty()){
						iteratorQuadraOrigem = (colecaoQuadraOrigem).iterator();
						while(iteratorQuadraOrigem.hasNext()){
							quadraOrigem = (Quadra) iteratorQuadraOrigem.next();
							if(!verificarSituacaoRota(quadraOrigem)){
								quadraDestino = (Quadra) pesquisarRetornarObjeto(quadraOrigem, setorComercialDestino, 2);
								if(verificarSituacaoRota(quadraDestino)){
									flagContinuar = false;
									throw new ActionServletException("atencao.pesquisa.quadra_rota_nao_faturada");
								}
							}
						}
					}
					quadraOrigem = null;
					quadraDestino = null;

				}else if(quadraOrigem == null && quadraDestino != null){

					quadraOrigem = (Quadra) pesquisarRetornarObjeto(quadraDestino, setorComercialOrigem, 2);
					if(!verificarSituacaoRota(quadraOrigem)){
						if(verificarSituacaoRota(quadraDestino)){
							flagContinuar = false;
							throw new ActionServletException("atencao.pesquisa.quadra_rota_nao_faturada");
						}
					}
					quadraOrigem = null;

				}else{

					if(!verificarSituacaoRota(quadraOrigem)){
						if(verificarSituacaoRota(quadraDestino)){
							flagContinuar = false;
							throw new ActionServletException("atencao.pesquisa.quadra_rota_nao_faturada");
						}
					}

				}
			}
		}

		// Continuaçao [FS0006 – Verificar pré-requisitos para alteração da
		// inscrição]
		Map<Integer, Collection<Imovel>> imoveisAlteracao = null;

		if(flagContinuar){

			/*
			 * Prepara os objetos para a pesquisa dos imoveis que estão
			 * localizados na inscrição de origem
			 */
			Collection colecaoImovel = null;
			if(setorComercialOrigem != null){
				// Verifica se a Empresa é ADA
				if(quadraOrigem != null){
					if(!loteOrigemTemp.equals("")){
						colecaoImovel = fachada.pesquisarImovel(localidadeOrigem.getId(), setorComercialOrigem.getId(), quadraOrigem
										.getId(), new Short(loteOrigemTemp));
					}else{
						colecaoImovel = fachada.pesquisarImovel(localidadeOrigem.getId(), setorComercialOrigem.getId(), quadraOrigem
										.getId(), null);
					}
				}else{
					colecaoImovel = fachada.pesquisarImovel(localidadeOrigem.getId(), setorComercialOrigem.getId(), null, null);
				}
			}else{
				colecaoImovel = fachada.pesquisarImovel(localidadeOrigem.getId(), null, null, null);
			}

			Imovel imovel = new Imovel();

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				flagContinuar = false;
				throw new ActionServletException("atencao.pesquisa.imovel_inexistente");
			}else{
				Iterator itImovel = (colecaoImovel).iterator();

				// Validando a situação do imóvel
				while(itImovel.hasNext()){
					imovel = (Imovel) itImovel.next();
					if(imovel.getFaturamentoSituacaoTipo() != null){
						flagContinuar = false;
						throw new ActionServletException("atencao.pesquisa.imovel_situacao_especial_faturamento");
					}else if(imovel.getCobrancaSituacaoTipo() != null){
						flagContinuar = false;
						throw new ActionServletException("atencao.pesquisa.imovel_situacao_especial_cobranca");
					}
				}

				// Preparação dos objetos do tipo Imovel
				// ===========================
				itImovel = (colecaoImovel).iterator();

				SetorComercial setorComercialDestinoAuxiliar = new SetorComercial();
				Quadra quadraDestinoAuxiliar = new Quadra();

				// Array onde serão armazenados os imóveis que sofrerão
				// alterações.
				imoveisAlteracao = new HashMap<Integer, Collection<Imovel>>();

				while(itImovel.hasNext()){
					imovel = (Imovel) itImovel.next();

					imovel.setLocalidade(localidadeDestino);

					setorComercialDestinoAuxiliar = null;
					quadraDestinoAuxiliar = null;

					if(setorComercialDestino != null){
						imovel.setSetorComercial(setorComercialDestino);

						if(quadraDestino != null){
							imovel.setQuadra(quadraDestino);
							if(loteDestinoTemp != null && !loteDestinoTemp.equalsIgnoreCase("")){
								imovel.setLote(loteDestino);
							}
						}else{
							// Pesquisa os objetos equivalentes na localidade de
							// destino
							quadraDestinoAuxiliar = (Quadra) pesquisarRetornarObjeto(imovel.getQuadra(), setorComercialDestino, 2);
							imovel.setQuadra(quadraDestinoAuxiliar);
							if(loteDestinoTemp != null && !loteDestinoTemp.equalsIgnoreCase("")){
								imovel.setLote(loteDestino);
							}
						}
					}else{
						// Pesquisa os objetos equivalentes na localidade de
						// destino
						setorComercialDestinoAuxiliar = (SetorComercial) pesquisarRetornarObjeto(imovel.getSetorComercial(),
										localidadeDestino, 1);

						imovel.setSetorComercial(setorComercialDestinoAuxiliar);

						quadraDestinoAuxiliar = (Quadra) pesquisarRetornarObjeto(imovel.getQuadra(), setorComercialDestinoAuxiliar, 2);

						imovel.setQuadra(quadraDestinoAuxiliar);

						if(loteDestinoTemp != null && !loteDestinoTemp.equalsIgnoreCase("")){
							imovel.setLote(loteDestino);
						}
					}

					// Adiciona o imóvel ao Mapa (RotaId -> Collection<Imovel>) que será guardado
					// para alteração da inscrição do imóvel
					adicionarImovelAoMapa(imovel, imoveisAlteracao);
				}
			}
		}

		if(this.verificarMudancaDaQuadra(localidadeOrigemID, setorComercialOrigemCD, quadraOrigemNM, loteOrigemTemp, localidadeDestinoID,
						setorComercialDestinoCD, quadraDestinoNM, fachada, alterarImovelInscricaoActionForm)){
			// Seta o retorno para a tela de totalizadores, caso a alteração provoque mudança de
			// quadra.
			retorno = actionMapping.findForward("exibirTotalizadoresAlterarImovelInscricao");
			sessao.setAttribute("provocaMudancaQuadra", "S");
			sessao.setAttribute("imoveisAlteracao", imoveisAlteracao);
		}else{
			// Seta o retorno para a Action do Alterar a Inscrição Efetivamente, sem ir para tela de
			// totalizadores (Caso não haja mudança de quadra).
			retorno = actionMapping.findForward("alterarImovelInscricao");
			sessao.setAttribute("provocaMudancaQuadra", "N");
			sessao.setAttribute("imoveisAlteracao", imoveisAlteracao);
		}

		return retorno;
	}

	/**
	 * Adiciona um imóvel ao Map (RotaId -> Collection<Imovel>)
	 * 
	 * @author Luciano Galvao
	 * @date 29/01/2013
	 */
	private void adicionarImovelAoMapa(Imovel imovel, Map<Integer, Collection<Imovel>> imoveisAlteracao){

		Integer chave = Integer.parseInt("-1");
		Collection<Imovel> imoveisRota = null;

		if(imovel.getRota() != null && imovel.getRota().getId() != null){
			chave = imovel.getRota().getId();
		}

		if(imoveisAlteracao.containsKey(chave)){
			imoveisRota = imoveisAlteracao.get(chave);
			imoveisRota.add(imovel);
		}else{
			imoveisRota = new ArrayList<Imovel>();
			imoveisRota.add(imovel);
			imoveisAlteracao.put(chave, imoveisRota);
		}
	}

	/**
	 * @author Ailton Sousa
	 * @date 18/01/2012
	 * @param localidade
	 * @param setorComercial
	 * @param quadra
	 * @param lote
	 */
	private boolean verificarMudancaDaQuadra(String localidadeOrigem, String setorComercialOrigem, String quadraOrigem, String loteOrigem,
					String localidadeDestino, String setorComercialDestino, String quadraDestino, Fachada fachada, DynaValidatorForm form){

		boolean retorno = false;

		if(!localidadeOrigem.equals(localidadeDestino) || !setorComercialOrigem.equals(setorComercialDestino)
						|| !quadraOrigem.equals(quadraDestino)){

			retorno = true;

			FiltroImovel filtroImovel = new FiltroImovel();

			if((localidadeOrigem != null && !localidadeOrigem.equals(""))
							&& (setorComercialOrigem != null && !setorComercialOrigem.equals(""))
							&& (quadraOrigem != null && !quadraOrigem.equals("")) && (loteOrigem != null && !loteOrigem.equals(""))){
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, Util
								.converterStringParaInteger(localidadeOrigem)));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, Util
								.converterStringParaInteger(setorComercialOrigem)));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_NUMERO, Util
								.converterStringParaInteger(quadraOrigem)));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, Util.converterStringParaShort(loteOrigem)));
			}else if((localidadeOrigem != null && !localidadeOrigem.equals(""))
							&& (setorComercialOrigem != null && !setorComercialOrigem.equals(""))
							&& (quadraOrigem != null && !quadraOrigem.equals("")) && (loteOrigem == null || loteOrigem.equals(""))){
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, Util
								.converterStringParaInteger(localidadeOrigem)));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, Util
								.converterStringParaInteger(setorComercialOrigem)));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_NUMERO, Util
								.converterStringParaInteger(quadraOrigem)));
			}else if((localidadeOrigem != null && !localidadeOrigem.equals(""))
							&& (setorComercialOrigem != null && !setorComercialOrigem.equals(""))
							&& (quadraOrigem == null || quadraOrigem.equals("")) && (loteOrigem == null || loteOrigem.equals(""))){
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, Util
								.converterStringParaInteger(localidadeOrigem)));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, Util
								.converterStringParaInteger(setorComercialOrigem)));
			}else if((localidadeOrigem != null && !localidadeOrigem.equals(""))
							&& (setorComercialOrigem == null || setorComercialOrigem.equals(""))
							&& (quadraOrigem == null || quadraOrigem.equals("")) && (loteOrigem == null || loteOrigem.equals(""))){
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, Util
								.converterStringParaInteger(localidadeOrigem)));
			}

			filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, Imovel.IMOVEL_EXCLUIDO,
							FiltroParametro.CONECTOR_OR, 2));

			filtroImovel.adicionarParametro(new ParametroNulo(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO));

			filtroImovel.setCampoOrderBy(FiltroImovel.LOCALIDADE_ID, FiltroImovel.SETOR_COMERCIAL_CODIGO, FiltroImovel.QUADRA_NUMERO,
							FiltroImovel.LOTE);

			// Objetos que serão retornados pelo hibernate.
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA_ROTA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			Integer totalImovelMesmaRota = null;
			Integer totalImovelRotaDiferente = null;

			if(colecaoImovel != null){
				Iterator itImovel = colecaoImovel.iterator();
				Imovel imovel = null;

				totalImovelMesmaRota = 0;
				totalImovelRotaDiferente = 0;

				while(itImovel.hasNext()){
					imovel = (Imovel) itImovel.next();

					if(imovel.getRota() != null && imovel.getRota().getId() != null && imovel.getQuadra() != null
									&& imovel.getRota() != null && imovel.getQuadra().getRota().getId() != null
									&& imovel.getRota().getId().equals(imovel.getQuadra().getRota().getId())){
						totalImovelMesmaRota = totalImovelMesmaRota + 1;
					}else{
						totalImovelRotaDiferente = totalImovelRotaDiferente + 1;
					}
				}
			}

			if(totalImovelMesmaRota != null){
				form.set("totalImovelMesmaRota", totalImovelMesmaRota.toString());
				form.set("totalImovelRotaDiferente", totalImovelRotaDiferente.toString());
			}else{
				form.set("totalImovelMesmaRota", "");
				form.set("totalImovelRotaDiferente", "");
			}
		}

		return retorno;
	}

	/**
	 * Verifica a situação em que se encontra a rota que pertence a quadra
	 * passada como parâmetro - não faturada = false e faturada = true
	 * 
	 * @param quadra
	 * @return um boleano
	 */

	private boolean verificarSituacaoRota(Quadra quadra){

		boolean retorno = true;
		Collection colecaoPesquisa = null;
		SistemaParametro sistemaParametro = null;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Retorna o único objeto da tabela sistemaParametro
		sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(sistemaParametro == null){
			retorno = false;
			throw new ActionServletException("atencao.pesquisa.sistemaparametro_inexistente");
		}else{

			FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new FiltroFaturamentoAtividadeCronograma();

			filtroFaturamentoAtividadeCronograma.adicionarParametro(new ParametroSimples(
							FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_FATURAMENTO_GRUPO_ID, quadra.getRota()
											.getFaturamentoGrupo().getId()));

			filtroFaturamentoAtividadeCronograma.adicionarParametro(new ParametroSimples(
							FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA, new Integer(
											sistemaParametro.getAnoMesFaturamento())));

			// O valor do ID será fixo
			// =============================================
			filtroFaturamentoAtividadeCronograma.adicionarParametro(new ParametroSimples(
							FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID, FaturamentoAtividade.FATURAR_GRUPO));
			// =====================================================================

			colecaoPesquisa = fachada.pesquisar(filtroFaturamentoAtividadeCronograma, FaturamentoAtividadeCronograma.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				/*
				 * retorno = false;
				 * throw new ActionServletException(
				 * "atencao.pesquisa.faturamento_atividade_cronograma_inexistente");
				 */
			}else{
				FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = (FaturamentoAtividadeCronograma) Util
								.retonarObjetoDeColecao(colecaoPesquisa);

				// Local da verificação da situação da Rota (Faturada ou não
				// Faturada)
				if(faturamentoAtividadeCronograma.getDataRealizacao() == null){
					retorno = false;
				}
			}
		}

		return retorno;
	}

	/**
	 * @param objetoPesquisa
	 * @param objetoPai
	 * @param tipoObjeto
	 * @return
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	private boolean pesquisarObjeto(Object objetoPesquisa, Object objetoPai, int tipoObjeto){

		boolean retorno = true;
		Collection colecaoPesquisa = null;
		SetorComercial setorComercial = null;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		switch(tipoObjeto){
			// Setor Comercial
			case 1:

				Localidade localidade = (Localidade) objetoPai;
				setorComercial = (SetorComercial) objetoPesquisa;

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, String
								.valueOf(setorComercial.getCodigo())));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					retorno = false;
				}

				break;
			// Quadra
			case 2:

				setorComercial = (SetorComercial) objetoPai;
				Quadra quadra = (Quadra) objetoPesquisa;

				FiltroQuadra filtroQuadra = new FiltroQuadra();

				filtroQuadra
								.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, String.valueOf(setorComercial
												.getId())));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, String.valueOf(quadra.getNumeroQuadra())));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					retorno = false;
				}

				break;
			default:
				break;
		}

		return retorno;
	}

	/**
	 * @param objetoPesquisa
	 * @param objetoPai
	 * @param tipoObjeto
	 * @return
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	private Object pesquisarRetornarObjeto(Object objetoPesquisa, Object objetoPai, int tipoObjeto){

		Object retorno = null;
		Collection colecaoPesquisa = null;
		SetorComercial setorComercial = null;
		Quadra quadra = null;
		Imovel imovel = null;
		FiltroImovel filtroImovel = null;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		switch(tipoObjeto){
			// Setor Comercial
			case 1:

				Localidade localidade = (Localidade) objetoPai;
				setorComercial = (SetorComercial) objetoPesquisa;

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, String
								.valueOf(setorComercial.getCodigo())));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}

				break;
			// Quadra
			case 2:

				setorComercial = (SetorComercial) objetoPai;
				quadra = (Quadra) objetoPesquisa;

				FiltroQuadra filtroQuadra = new FiltroQuadra();

				// Objetos que serão retornados pelo hibernate
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");

				filtroQuadra
								.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, String.valueOf(setorComercial
												.getId())));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, String.valueOf(quadra.getNumeroQuadra())));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}

				break;

			// Imovel
			case 3:

				quadra = (Quadra) objetoPai;
				imovel = (Imovel) objetoPesquisa;

				filtroImovel = new FiltroImovel();

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_ID, quadra.getId()));

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, new Short(imovel.getLote())));

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SUBLOTE, new Short(imovel.getSubLote())));

				colecaoPesquisa = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}

				break;

			// Imovel
			case 4:

				setorComercial = (SetorComercial) objetoPai;
				imovel = (Imovel) objetoPesquisa;

				filtroImovel = new FiltroImovel();

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_ID, setorComercial.getId()));

				Rota rota = imovel.getRota();

				if(rota != null){
					Integer idRota = rota.getId();
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ROTA_ID, idRota));
				}

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.NUMERO_SEGMENTO, new Short(imovel.getNumeroSegmento())));

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, new Short(imovel.getLote())));

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SUBLOTE, new Short(imovel.getSubLote())));

				colecaoPesquisa = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}

				break;

			default:
				break;
		}

		return retorno;
	}

	/**
	 * Compara os objetos para validar suas referencias
	 * 
	 * @param origem
	 * @param destino
	 * @param tipoObjeto
	 * @return
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	private boolean compararObjetos(Object origem, Object destino, int tipoObjeto, String parametroCompanhia){

		boolean retorno = true;
		// Collection colecaoImoveis;

		switch(tipoObjeto){
			// Localidade
			case 1:
				Collection colecaoSetorOrigem = pesquisarDependentes(origem, 1);

				// Comparação de setores
				if(colecaoSetorOrigem != null && !colecaoSetorOrigem.isEmpty()){
					Collection colecaoSetorDestino = pesquisarDependentes(destino, 1);

					if(colecaoSetorDestino != null && !colecaoSetorDestino.isEmpty()){

						Iterator itSetorOrigem = (colecaoSetorOrigem).iterator();
						Iterator itSetorDestino = null;
						Iterator itQuadraOrigem = null;
						Iterator itQuadraDestino = null;

						SetorComercial setorComercialOrigem = null;
						SetorComercial setorComercialDestino = null;
						Quadra quadraOrigem = null;
						Quadra quadraDestino = null;

						while(itSetorOrigem.hasNext()){
							if(!retorno){
								// Verifica a existência de imóveis no setor
								// comercial
								if(existeImovel(setorComercialOrigem, 1)){
									break;
								}
							}

							setorComercialOrigem = (SetorComercial) itSetorOrigem.next();

							// Colocar o índice para o primeiro registro
							itSetorDestino = (colecaoSetorDestino).iterator();

							// flag auxiliar para controlar a saída da rotina de
							// repetição
							boolean sairLoop = false;
							while(itSetorDestino.hasNext() && sairLoop == false){

								setorComercialDestino = (SetorComercial) itSetorDestino.next();

								if(setorComercialDestino.getCodigo() == setorComercialOrigem.getCodigo()){

									retorno = true;
									sairLoop = true;

									// Comparação de quadras
									Collection colecaoQuadraOrigem = pesquisarDependentes(setorComercialOrigem, 2);

									if(colecaoQuadraOrigem == null || colecaoQuadraOrigem.isEmpty()){
										retorno = true;
										break;
									}else{

										Collection colecaoQuadraDestino = pesquisarDependentes(setorComercialDestino, 2);

										if(colecaoQuadraDestino != null && !colecaoQuadraDestino.isEmpty()){

											itQuadraOrigem = (colecaoQuadraOrigem).iterator();

											while(itQuadraOrigem.hasNext()){
												if(!retorno){
													// Verifica a existência de
													// imóveis na quadra
													if(existeImovel(quadraOrigem, 2)){
														break;
													}
												}

												quadraOrigem = (Quadra) itQuadraOrigem.next();

												// Colocar o índice para o primeiro
												// registro
												itQuadraDestino = (colecaoQuadraDestino).iterator();

												while(itQuadraDestino.hasNext()){

													quadraDestino = (Quadra) itQuadraDestino.next();

													if(quadraDestino.getNumeroQuadra() == quadraOrigem.getNumeroQuadra()){
														retorno = true;
														break;
													}else{
														retorno = false;
													}
												}

											}
										}else{
											retorno = false;
										}
									}
								}else{
									retorno = false;
								}
							}
						}
					}else{
						retorno = false;
					}
				}

				break;
			// Setor Comercial
			case 2:
				Collection colecaoQuadraOrigem = pesquisarDependentes(origem, 2);

				// Comparação de quadras
				if(colecaoQuadraOrigem != null && !colecaoQuadraOrigem.isEmpty()){
					Collection colecaoQuadraDestino = pesquisarDependentes(destino, 2);

					if(colecaoQuadraDestino != null && !colecaoQuadraDestino.isEmpty()){
						Iterator itQuadraOrigem = (colecaoQuadraOrigem).iterator();
						Iterator itQuadraDestino = null;

						while(itQuadraOrigem.hasNext()){
							if(!retorno){
								break;
							}
							Quadra quadraOrigem = (Quadra) itQuadraOrigem.next();

							// Colocar o índice para o primeiro registro
							itQuadraDestino = (colecaoQuadraDestino).iterator();

							while(itQuadraDestino.hasNext()){
								Quadra quadraDestino = (Quadra) itQuadraDestino.next();
								if(quadraDestino.getNumeroQuadra() == quadraOrigem.getNumeroQuadra()){
									retorno = true;
									break;
								}else{
									retorno = false;
								}
							}
						}
					}else{
						retorno = false;
					}
				}
				break;
			default:
				break;
		}

		return retorno;
	}

	/**
	 * Retorna os dependentes do objeto passado (Localidade ou Setor Comercial)
	 * 
	 * @param objetoPai
	 * @param tipoObjeto
	 * @return uma colecao com objetos dependentes do objeto passado
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	private Collection pesquisarDependentes(Object objetoPai, int tipoObjeto){

		Collection colecaoPesquisa = null;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		switch(tipoObjeto){
			// Localidade - retorna uma coleção de setor comercial
			case 1:
				Localidade localidade = (Localidade) objetoPai;

				// Indicador de uso considerado como ATIVO
				colecaoPesquisa = fachada.pesquisarSetorComercial(localidade.getId().intValue());

				break;
			// Setor Comercial - retorna uma coleção de quadra
			case 2:
				SetorComercial setorComercial = (SetorComercial) objetoPai;

				// Indicador de uso considerado como ATIVO
				colecaoPesquisa = fachada.pesquisarQuadra(setorComercial.getId().intValue());

				break;
			default:
				break;
		}

		return colecaoPesquisa;
	}

	/**
	 * Valida os valores digitados pelo usuário
	 * 
	 * @param campoDependencia
	 * @param dependente
	 * @param tipoObjeto
	 * @return Object
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	private Object validarCampo(String campoDependencia, String dependente, int tipoObjeto){

		Object objeto = null;
		Collection colecaoPesquisa;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		if(campoDependencia != null && !campoDependencia.equalsIgnoreCase("")){

			if(dependente == null || tipoObjeto == 1){
				// Localidade
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, campoDependencia));

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					objeto = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}
			}else if(dependente != null && !dependente.equalsIgnoreCase("") && tipoObjeto > 1){
				switch(tipoObjeto){
					// Setor Comercial
					case 2:
						FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, campoDependencia));

						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
										new Integer(dependente)));

						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

						if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
							objeto = Util.retonarObjetoDeColecao(colecaoPesquisa);
						}

						break;
					// Quadra
					case 3:
						FiltroQuadra filtroQuadra = new FiltroQuadra();

						// Objetos que serão retornados pelo hibernate
						filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");

						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, campoDependencia));

						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(dependente)));

						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

						if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
							objeto = Util.retonarObjetoDeColecao(colecaoPesquisa);
						}

						break;
					default:
						break;
				}
			}
		}

		return objeto;
	}

	/**
	 * Verifica a existência de imóveis no objeto passado como parâmetro Os
	 * objetos passados podem ser do tipo SetorComercial = 1 e Quadra = 2
	 * 
	 * @param objetoCondicao
	 * @param tipoObjeto
	 * @return um boleano
	 */
	private boolean existeImovel(Object objetoCondicao, int tipoObjeto){

		boolean retorno = false;
		Collection colecaoPesquisa;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		if(tipoObjeto == 1){
			SetorComercial setorComercial = (SetorComercial) objetoCondicao;

			colecaoPesquisa = fachada.pesquisarImovel(null, setorComercial.getId(), null, null);

			if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
				retorno = true;
			}
		}else{
			Quadra quadra = (Quadra) objetoCondicao;

			colecaoPesquisa = fachada.pesquisarImovel(null, null, quadra.getId(), null);

			if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
				retorno = true;
			}
		}

		return retorno;
	}

}
