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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.micromedicao;

import gcom.batch.DadoComplementarEnumerator;
import gcom.batch.Processo;
import gcom.batch.ProcessoIniciado;
import gcom.batch.ProcessoIniciadoDadoComplementarHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável para dar um onLoad no arquivo e importar os ceps.
 * 
 * @author Sávio Luiz
 * @created 24 de Agosto de 2005
 * @author eduardo henrique
 * @date 12/09/2008
 *       Alteração na chamada para execução de Registro por tipo Celular/Mobile
 */
public class RegistrarLeiturasAnormalidadesAction
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

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		try{

			String idFaturamentoGrupo = null;
			DiskFileUpload upload = new DiskFileUpload();
			Integer anoMesReferenciaGrupo = null;

			// Parse the request
			List items = upload.parseRequest(httpServletRequest);

			Fachada fachada = Fachada.getInstancia();
			Collection<Rota> colecaoRotasRegistrarLeiturasAnormalidades = new ArrayList<Rota>();

			FileItem item = null;

			// pega uma lista de itens do form
			Iterator iter = items.iterator();
			while(iter.hasNext()){

				item = (FileItem) iter.next();

				if(item.getFieldName().equals("idFaturamentoGrupo")){
					idFaturamentoGrupo = item.getString();
				}

				if(idFaturamentoGrupo != null){
					Map<ControladorException, Collection<Object[]>> mapaErrosMovimentoEmpresa = fachada
									.verificarLimiteAnormalidadesAceitavel(Integer.valueOf(idFaturamentoGrupo));
					if(!mapaErrosMovimentoEmpresa.isEmpty()){
						retorno = actionMapping.findForward("telaErroAnormalidadeAceitavel");
						ControladorException controladorException = (ControladorException) Util
										.retonarObjetoDeColecao(mapaErrosMovimentoEmpresa.keySet());
						String[] parametrosMensagem = new String[controladorException.getParametroMensagem().size()];
						parametrosMensagem = controladorException.getParametroMensagem().toArray(parametrosMensagem);
						ActionServletException exception = new ActionServletException(controladorException.getMessage(),
										controladorException, parametrosMensagem);
						httpServletRequest.getSession().setAttribute("exception", exception);
						httpServletRequest.getSession().setAttribute("movimentosRoteiroEmpresaAnormalidade",
										mapaErrosMovimentoEmpresa.get(controladorException));
						return retorno;
					}

					FiltroRota filtroRota = new FiltroRota();
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, idFaturamentoGrupo));
					filtroRota.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroRota.LEITURA_TIPO_ID,
									LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA));
					colecaoRotasRegistrarLeiturasAnormalidades = fachada.pesquisar(filtroRota, Rota.class.getName());

					anoMesReferenciaGrupo = fachada.pesquisarAnoMesPorIdFaturamentoGrupo(Integer.valueOf(idFaturamentoGrupo));
					if(anoMesReferenciaGrupo == null){
						throw new ActionServletException("atencao.anomes.faturamento.invalido");
					}

					if(Util.isVazioOrNulo(colecaoRotasRegistrarLeiturasAnormalidades)){

						ActionServletException actionServletException = new ActionServletException(
										"atencao.grupo_nao_permite_registrar_leituras_anormalidades", idFaturamentoGrupo);

						actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarLeiturasAnormalidadesAction.do");
						throw actionServletException;
					}
				}
			}

			ProcessoIniciado processoIniciado = new ProcessoIniciado();

			Processo processo = new Processo();
			processo.setId(Processo.REGISTRA_LEITURAS_ANORMALIDADES);
			processoIniciado.setDataHoraAgendamento(new Date());

			// Verificar restrição de execução simultânea de processos
			if(fachada.isProcessoComRestricaoExecucaoSimultanea(processo.getId())){

				throw new ActionServletException("atencao.processo_restricao_execucao");
			}

			processoIniciado.setProcesso(processo);
			processoIniciado.setUsuario(this.getUsuarioLogado(httpServletRequest));

			if(idFaturamentoGrupo != null){
				processoIniciado.setCodigoGrupoProcesso(Util.obterInteger(idFaturamentoGrupo));
			}

			List<Object> colecaoParametros = new ArrayList<Object>();
			colecaoParametros.add(colecaoRotasRegistrarLeiturasAnormalidades);
			colecaoParametros.add(Util.obterInteger(idFaturamentoGrupo));
			colecaoParametros.add(anoMesReferenciaGrupo);
			colecaoParametros.add(this.getUsuarioLogado(httpServletRequest));

			ProcessoIniciadoDadoComplementarHelper helper = new ProcessoIniciadoDadoComplementarHelper();
			helper.adcionarDadoComplementar(DadoComplementarEnumerator.GRUPO_DESCRICAO, idFaturamentoGrupo);
			helper.adcionarDadoComplementar(DadoComplementarEnumerator.ANO_MES_REFERENCIA, anoMesReferenciaGrupo.toString());
			processoIniciado.setDescricaoDadosComplementares(helper.getStringFormatoPesistencia());

			Integer codigoProcessoIniciadoGerado = (Integer) fachada.inserirProcessoIniciadoOnline(processoIniciado, colecaoParametros);

			if(codigoProcessoIniciadoGerado > 0){

				// montando página de sucesso
				montarPaginaSucesso(httpServletRequest, "Leituras e Anormalidades do Grupo " + idFaturamentoGrupo + " da Referência "
								+ anoMesReferenciaGrupo.toString() + " Enviado para Processamento em batch. Processo "
								+ Processo.REGISTRA_LEITURAS_ANORMALIDADES, "Voltar",
								"exibirRegistrarLeiturasAnormalidadesAction.do");
			}else{

				ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
								String.valueOf(Processo.REGISTRA_LEITURAS_ANORMALIDADES) + " - REGISTRAR LEITURAS E ANORMALIDADES");

				actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarLeiturasAnormalidadesAction.do");
				throw actionServletException;
			}

		}catch(FileUploadException e){

			e.printStackTrace();
			throw new ActionServletException("erro.sistema", e);
		}

		return retorno;
	}

}
