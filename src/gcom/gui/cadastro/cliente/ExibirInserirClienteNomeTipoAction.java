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

package gcom.gui.cadastro.cliente;

import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.SolicitanteFone;
import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action que inicializa a primeira p�gina do processo de inserir cliente
 * 
 * @author Rodrigo
 */
public class ExibirInserirClienteNomeTipoAction
				extends GcomAction {

	/**
	 * Description of the Method
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("inserirClienteNomeTipo");

		HttpSession sessao = httpServletRequest.getSession(false);
		DynaValidatorForm clienteActionForm = null;

		if(httpServletRequest.getParameter("idRA") != null){

			Integer idRA = Util.converterStringParaInteger(httpServletRequest.getParameter("idRA"));

			Collection colRegAtend = null;

			if(idRA != null){
				colRegAtend = Fachada.getInstancia().obterRASolicitante(idRA);
			}

			if(colRegAtend != null){

				Integer tipoSolicitacaoRA = Fachada.getInstancia().obterTipoSolicitacaoRA(idRA);

				if(!(ServicoTipo.LIGACAO_AGUA.contains(tipoSolicitacaoRA))){
					throw new ActionServletException("atencao.ra.diferente_ligacao_agua");
				}

				Iterator itRegAtend = colRegAtend.iterator();
				RegistroAtendimentoSolicitante regAtend = null;
				if(itRegAtend.hasNext()){
					regAtend = (RegistroAtendimentoSolicitante) itRegAtend.next();
				}
				if(regAtend != null){
					ModuleConfig module = actionMapping.getModuleConfig();
					FormBeanConfig formBeanConfig = module.findFormBeanConfig("ClienteActionForm");
					DynaActionFormClass dynaClass = DynaActionFormClass.createDynaActionFormClass(formBeanConfig);
					try{
						clienteActionForm = (DynaValidatorForm) dynaClass.newInstance();
					}catch(IllegalAccessException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch(InstantiationException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
					filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimentoSolicitante.ID,
									regAtend.getID()));
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("solicitanteFones");
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro");
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro");
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro");
					filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.municipio");
					filtroRegistroAtendimentoSolicitante
									.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.municipio.unidadeFederacao");
					filtroRegistroAtendimentoSolicitante
									.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
					filtroRegistroAtendimentoSolicitante
									.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
					colRegAtend = Fachada.getInstancia().pesquisar(filtroRegistroAtendimentoSolicitante,
									RegistroAtendimentoSolicitante.class.getName());

					regAtend = (RegistroAtendimentoSolicitante) colRegAtend.iterator().next();

					clienteActionForm.set("nome", regAtend.getSolicitante());

					Collection enderecosCliente = new Vector();

					ClienteEndereco clienteEndereco = new ClienteEndereco();
					clienteEndereco.setId(new Integer(1));
					clienteEndereco.setComplemento(regAtend.getComplementoEndereco());
					clienteEndereco.setEnderecoTipo(new EnderecoTipo());
					clienteEndereco.getEnderecoTipo().setId(new Integer(1));
					clienteEndereco.getEnderecoTipo().setDescricao("RESIDENCIAL");
					clienteEndereco.setLogradouroBairro(regAtend.getLogradouroBairro());
					clienteEndereco.setLogradouroCep(regAtend.getLogradouroCep());
					clienteEndereco.setNumero(regAtend.getNumeroImovel());
					clienteEndereco.setIndicadorEnderecoCorrespondencia(ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA);
					clienteEndereco.setUltimaAlteracao(new Date());
					enderecosCliente.add(clienteEndereco);

					clienteActionForm.set("enderecoCorrespondenciaSelecao", new Long(obterTimestampIdObjeto(clienteEndereco)));
					sessao.setAttribute("colecaoEnderecos", enderecosCliente);

					Set telefonesCliente = regAtend.getSolicitanteFones();

					Collection clienteFones = new Vector();

					Iterator iterator = null;

					if(telefonesCliente != null){

						iterator = telefonesCliente.iterator();

						// Percorrer a cole��o dos telefones para setar no form qual o
						// telefone do cliente que
						// foi selecionado como o principal

						while(iterator.hasNext()){
							SolicitanteFone solicitanteFone = (SolicitanteFone) iterator.next();

							ClienteFone clienteFone = new ClienteFone();
							clienteFone.setDdd(solicitanteFone.getDdd().toString());
							FiltroFoneTipo filtroFoneTipo = new FiltroFoneTipo();
							filtroFoneTipo
											.adicionarParametro(new ParametroSimples(FiltroFoneTipo.ID, solicitanteFone.getFoneTipo()
															.getId()));
							FoneTipo foneTipo = (FoneTipo) Fachada.getInstancia().pesquisar(filtroFoneTipo, FoneTipo.class.getName())
											.iterator().next();
							clienteFone.setFoneTipo(foneTipo);
							clienteFone.setId(solicitanteFone.getId());
							clienteFone.setIndicadorTelefonePadrao(solicitanteFone.getIndicadorPadrao());
							clienteFone.setRamal(solicitanteFone.getRamal());
							clienteFone.setTelefone(solicitanteFone.getFone());
							clienteFone.setUltimaAlteracao(new Date());

							if(clienteFone != null){
								if(clienteFone.getIndicadorTelefonePadrao() != null
												&& clienteFone.getIndicadorTelefonePadrao().equals(
																ConstantesSistema.INDICADOR_TELEFONE_PRINCIPAL)){
									clienteActionForm.set("indicadorTelefonePadrao", new Long(obterTimestampIdObjeto(clienteFone)));
								}
							}
							clienteFones.add(clienteFone);
						}
					}

					// Seta a cole��o de telefones do usu�rio
					sessao.setAttribute("colecaoClienteFone", clienteFones);
				}
			}else{
				throw new ActionServletException("atencao.ra.inexistente");
			}
		}

		// Pesquisa os Tipos de Clientes para a p�gina
		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroClienteTipo.setCampoOrderBy(FiltroClienteTipo.DESCRICAO);

		SistemaParametro param = Fachada.getInstancia().pesquisarParametrosDoSistema();
		String parametroEmpresa = param.getParmId().toString();

		sessao.setAttribute("parametroEmpresa", parametroEmpresa);

		httpServletRequest.setAttribute("colecaoTipoPessoa", Fachada.getInstancia().pesquisar(filtroClienteTipo,
						ClienteTipo.class.getName()));

		FiltroClienteTipoEspecial filtroClienteTipoEspecial = new FiltroClienteTipoEspecial();
		filtroClienteTipoEspecial.setCampoOrderBy(FiltroClienteTipoEspecial.DESCRICAO);

		httpServletRequest.setAttribute("colecaoClienteTipoEspecial", Fachada.getInstancia().pesquisar(filtroClienteTipoEspecial,
						ClienteTipoEspecial.class.getName()));
		if(clienteActionForm != null){
			sessao.setAttribute("ClienteActionForm", clienteActionForm);
		}

		return retorno;
	}
}
