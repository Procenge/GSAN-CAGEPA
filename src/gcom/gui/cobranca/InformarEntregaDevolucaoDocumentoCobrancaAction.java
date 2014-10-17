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

package gcom.gui.cobranca;

import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.comum.exception.PCGException;

/**
 * InformarEntregaDevolucaoDocumentoCobrancaActionForm
 * [UC3044] Informar Entrega/Devolu��o de Documentos de Cobran�a
 * 
 * @author Carlos Chrystian
 * @created 29 de Fevereiro de 2012
 */

public class InformarEntregaDevolucaoDocumentoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws PCGException{

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		InformarEntregaDevolucaoDocumentoCobrancaActionForm form = (InformarEntregaDevolucaoDocumentoCobrancaActionForm) actionForm;

		Collection<CobrancaDocumento> colecaoCobrancaDocumentosSelecionados = new ArrayList<CobrancaDocumento>();

		Collection<InformarDadosEntregaDevolucaoDocumentoCobrancaHelper> colecaoDocumentos = new ArrayList<InformarDadosEntregaDevolucaoDocumentoCobrancaHelper>();

		ArrayList verificarDuplicidadeGrid = new ArrayList();

		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper1 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper2 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper3 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper4 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper5 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper6 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper7 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper8 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper9 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();
		InformarDadosEntregaDevolucaoDocumentoCobrancaHelper helper10 = new InformarDadosEntregaDevolucaoDocumentoCobrancaHelper();

		// Armazena o id da cobran�a a��o para recuperar
		// Descri��o para a tela de sucesso
		Integer idCobrancaAcao = Integer.valueOf(form.getCobrancaAcaoID());

		// Recupera a descri��o da cobran�a a��o
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();

		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, idCobrancaAcao));

		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoCobrancaAcao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

		String descricaoCobrancaAcao = "";
		if(!Util.isVazioOrNulo(colecaoCobrancaAcao)){
			CobrancaAcao cobrancaAcao = (CobrancaAcao) Util.retonarObjetoDeColecao(colecaoCobrancaAcao);
			descricaoCobrancaAcao = cobrancaAcao.getDescricaoCobrancaAcao();
		}

		// Recupera as informa��es preenchidas pelo usu�rio no grid
		helper1.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper1.setMatriculaImovel(form.getMatriculaImovel().trim());
		helper1.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao());
		helper1.setDataEntregaDevolucao(form.getDataEntregaDevolucao());
		helper1.setMotivoNaoEntrega(form.getMotivoNaoEntrega());
		helper1.setParecer(form.getParecerDescricao());

		helper2.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper2.setMatriculaImovel(form.getMatriculaImovel2().trim());
		helper2.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao2());
		helper2.setDataEntregaDevolucao(form.getDataEntregaDevolucao2());
		helper2.setMotivoNaoEntrega(form.getMotivoNaoEntrega2());
		helper2.setParecer(form.getParecerDescricao2());

		helper3.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper3.setMatriculaImovel(form.getMatriculaImovel3().trim());
		helper3.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao3());
		helper3.setDataEntregaDevolucao(form.getDataEntregaDevolucao3());
		helper3.setMotivoNaoEntrega(form.getMotivoNaoEntrega3());
		helper3.setParecer(form.getParecerDescricao3());

		helper4.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper4.setMatriculaImovel(form.getMatriculaImovel4().trim());
		helper4.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao4());
		helper4.setDataEntregaDevolucao(form.getDataEntregaDevolucao4());
		helper4.setMotivoNaoEntrega(form.getMotivoNaoEntrega4());
		helper4.setParecer(form.getParecerDescricao4());

		helper5.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper5.setMatriculaImovel(form.getMatriculaImovel5().trim());
		helper5.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao5());
		helper5.setDataEntregaDevolucao(form.getDataEntregaDevolucao5());
		helper5.setMotivoNaoEntrega(form.getMotivoNaoEntrega5());
		helper5.setParecer(form.getParecerDescricao5());

		helper6.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper6.setMatriculaImovel(form.getMatriculaImovel6().trim());
		helper6.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao6());
		helper6.setDataEntregaDevolucao(form.getDataEntregaDevolucao6());
		helper6.setMotivoNaoEntrega(form.getMotivoNaoEntrega6());
		helper6.setParecer(form.getParecerDescricao6());

		helper7.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper7.setMatriculaImovel(form.getMatriculaImovel7().trim());
		helper7.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao7());
		helper7.setDataEntregaDevolucao(form.getDataEntregaDevolucao7());
		helper7.setMotivoNaoEntrega(form.getMotivoNaoEntrega7());
		helper7.setParecer(form.getParecerDescricao7());

		helper8.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper8.setMatriculaImovel(form.getMatriculaImovel8().trim());
		helper8.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao8());
		helper8.setDataEntregaDevolucao(form.getDataEntregaDevolucao8());
		helper8.setMotivoNaoEntrega(form.getMotivoNaoEntrega8());
		helper8.setParecer(form.getParecerDescricao8());

		helper9.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper9.setMatriculaImovel(form.getMatriculaImovel9().trim());
		helper9.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao9());
		helper9.setDataEntregaDevolucao(form.getDataEntregaDevolucao9());
		helper9.setMotivoNaoEntrega(form.getMotivoNaoEntrega9());
		helper9.setParecer(form.getParecerDescricao9());

		helper10.setCobrancaAcaoID(form.getCobrancaAcaoID());
		helper10.setMatriculaImovel(form.getMatriculaImovel10().trim());
		helper10.setIndicadorEntregaDevolucao(form.getIndicadorEntregaDevolucao10());
		helper10.setDataEntregaDevolucao(form.getDataEntregaDevolucao10());
		helper10.setMotivoNaoEntrega(form.getMotivoNaoEntrega10());
		helper10.setParecer(form.getParecerDescricao10());

		// Inclui os documentos informados para serem validados
		// [FS0003 � Verificar exist�ncia do documento de cobran�a no grid]
		if(!Util.isVazioOuBranco(helper1.getMatriculaImovel()) && !Util.isVazioOuBranco(helper1.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper1.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper1.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper1.getMatriculaImovel());
				colecaoDocumentos.add(helper1);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper1.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper2.getMatriculaImovel()) && !Util.isVazioOuBranco(helper2.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper2.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper2.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper2.getMatriculaImovel());
				colecaoDocumentos.add(helper2);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper2.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper3.getMatriculaImovel()) && !Util.isVazioOuBranco(helper3.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper3.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper3.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper3.getMatriculaImovel());
				colecaoDocumentos.add(helper3);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper3.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper4.getMatriculaImovel()) && !Util.isVazioOuBranco(helper4.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper4.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper4.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper4.getMatriculaImovel());
				colecaoDocumentos.add(helper4);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper4.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper5.getMatriculaImovel()) && !Util.isVazioOuBranco(helper5.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper5.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper5.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper5.getMatriculaImovel());
				colecaoDocumentos.add(helper5);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper5.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper6.getMatriculaImovel()) && !Util.isVazioOuBranco(helper6.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper6.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper6.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper6.getMatriculaImovel());
				colecaoDocumentos.add(helper6);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper6.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper7.getMatriculaImovel()) && !Util.isVazioOuBranco(helper7.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper7.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper7.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper7.getMatriculaImovel());
				colecaoDocumentos.add(helper7);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper7.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper8.getMatriculaImovel()) && !Util.isVazioOuBranco(helper8.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper8.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper8.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper8.getMatriculaImovel());
				colecaoDocumentos.add(helper8);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper8.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper9.getMatriculaImovel()) && !Util.isVazioOuBranco(helper9.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper9.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper9.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper9.getMatriculaImovel());
				colecaoDocumentos.add(helper9);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper9.getMatriculaImovel());
			}
		}
		if(!Util.isVazioOuBranco(helper10.getMatriculaImovel()) && !Util.isVazioOuBranco(helper10.getIndicadorEntregaDevolucao())
						&& !Util.isVazioOuBranco(helper10.getDataEntregaDevolucao())){
			if(!verificarDuplicidadeGrid.contains(helper10.getMatriculaImovel())){
				verificarDuplicidadeGrid.add(helper10.getMatriculaImovel());
				colecaoDocumentos.add(helper10);
			}else{
				throw new ActionServletException("atencao.documento_ja_existe_grid", descricaoCobrancaAcao, helper10.getMatriculaImovel());
			}
		}

		// Valida��o dos campos informados pelo usu�rio
		if(!Util.isVazioOrNulo(colecaoDocumentos)){

			for(InformarDadosEntregaDevolucaoDocumentoCobrancaHelper dados : colecaoDocumentos){
				// 2.2.1. Matr�cula (obrigat�rio)
				// [FS0001 � Verificar exist�ncia da matr�cula do im�vel];
				if(!Util.isVazioOuBranco(dados.getMatriculaImovel())){
					if(fachada.verificarExistenciaImovel(Integer.valueOf(dados.getMatriculaImovel())) == 0){
						throw new ActionServletException("atencao.pesquisa.imovel.inexistente", dados.getMatriculaImovel());
					}
				}

				// [FS0002 � Verificar exist�ncia do documento de cobran�a para entrega/devolu��o]
				Collection colecaoCobrancaDocumento = null;
				if(!Util.isVazioOuBranco(dados.getMatriculaImovel())){

					Integer idImovel = Integer.valueOf(dados.getMatriculaImovel());
					Integer idDocumentoTipo = Integer.valueOf(dados.getCobrancaAcaoID());
					
					Integer idCobrancaDocumento = fachada.pesquisarIdDocumentoCobrancaEntregaDevolucao(idImovel, idDocumentoTipo);

					if(idCobrancaDocumento != null){
						FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
						filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID, idCobrancaDocumento));

						colecaoCobrancaDocumento = fachada.pesquisar(filtroCobrancaDocumento, CobrancaDocumento.class.getName());
					}

					if(Util.isVazioOrNulo(colecaoCobrancaDocumento)){
						FiltroCobrancaAcao filtroCobrancaAcaoMsg = new FiltroCobrancaAcao();

						filtroCobrancaAcaoMsg.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, dados.getCobrancaAcaoID()));

						Collection colecaoCobrancaAcaoMsg = fachada.pesquisar(filtroCobrancaAcaoMsg, CobrancaAcao.class.getName());

						String dsCobrancaAcao = "";

						if(!Util.isVazioOrNulo(colecaoCobrancaAcaoMsg)){
							CobrancaAcao cobrancaAcao = (CobrancaAcao) Util.retonarObjetoDeColecao(colecaoCobrancaAcaoMsg);
							dsCobrancaAcao = cobrancaAcao.getDescricaoCobrancaAcao();
						}

						throw new ActionServletException("atencao.documento_nao_existe_para_imovel", dsCobrancaAcao, dados
										.getMatriculaImovel());

					}
				}

				// 2.2.3. Data (obrigat�rio)
				// (exibir com a data corrente e permitir altera��o)
				Date dataAtual = new Date();

				// [FS0004 � Validar data entrega/devolu��o].
				// Caso o usu�rio informe uma data de entrega/devolu��o menor que a data de emiss�o
				// do
				// documento
				if(!Util.isVazioOrNulo(colecaoCobrancaDocumento)){
					CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) Util.retonarObjetoDeColecao(colecaoCobrancaDocumento);

					if(Util.converteStringParaDate(dados.getDataEntregaDevolucao()).before(
									Util.formatarDataSemHora(cobrancaDocumento.getEmissao()))){
						throw new ActionServletException("atencao.data_entrega_devolucao_maior_data_emissao", Util
										.formatarData(cobrancaDocumento.getEmissao()));
					}

					// Caso o usu�rio informe uma data de entrega/devolu��o maior que a data
					// corrente
					if(Util.converteStringParaDate(dados.getDataEntregaDevolucao()).after(Util.formatarDataSemHora(dataAtual))){
						throw new ActionServletException("atencao.data_entrega_devolucao_menor_data_corrente", Util.formatarData(dataAtual));
					}

					// [SB0001] � Efetuar Entrega/Devolu��o

					// 1. Para cada documento de cobran�a informado,
					// o sistema efetua a entrega/devolu��o de acordo com as seguintes regras
					CobrancaAcaoSituacao cobrancaAcaoSituacao = new CobrancaAcaoSituacao();

					// 1.1. Caso o usu�rio tenha efetuado a "ENTREGA" do documento
					if(dados.getIndicadorEntregaDevolucao().equals(ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO)){
						cobrancaAcaoSituacao.setId(CobrancaAcaoSituacao.ENTREGUE);

						cobrancaDocumento.setCobrancaAcaoSituacao(cobrancaAcaoSituacao);
						cobrancaDocumento.setDataSituacaoAcao(Util.converteStringParaDate(dados.getDataEntregaDevolucao()));
						cobrancaDocumento.setDescricaoParecer(dados.getParecer());
						cobrancaDocumento.setUltimaAlteracao(new Date());
					}

					// Recupera o id do Motivo da N�o Entrega para atualizar o documento de cobran�a
					FiltroMotivoNaoEntregaDocumento filtroMotivoNaoEntrega = new FiltroMotivoNaoEntregaDocumento();

					filtroMotivoNaoEntrega.adicionarParametro(new ParametroSimples(FiltroMotivoNaoEntregaDocumento.ID, dados
									.getMotivoNaoEntrega()));

					filtroMotivoNaoEntrega.adicionarParametro(new ParametroSimples(FiltroMotivoNaoEntregaDocumento.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoMotivosNaoEntrega = fachada.pesquisar(filtroMotivoNaoEntrega, MotivoNaoEntregaDocumento.class
									.getName());

					MotivoNaoEntregaDocumento motivoNaoEntregaDocumento = new MotivoNaoEntregaDocumento();

					if(!Util.isVazioOrNulo(colecaoMotivosNaoEntrega)){
						motivoNaoEntregaDocumento = (MotivoNaoEntregaDocumento) Util.retonarObjetoDeColecao(colecaoMotivosNaoEntrega);
					}

					// 1.2. Caso o usu�rio tenha efetuado a "DEVOLU��O" do documento
					if(dados.getIndicadorEntregaDevolucao().equals(ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO)){
						cobrancaAcaoSituacao.setId(CobrancaAcaoSituacao.NAO_ENTREGUE);

						cobrancaDocumento.setCobrancaAcaoSituacao(cobrancaAcaoSituacao);
						cobrancaDocumento.setDataSituacaoAcao(Util.converteStringParaDate(dados.getDataEntregaDevolucao()));
						cobrancaDocumento.setDescricaoParecer(dados.getParecer());
						cobrancaDocumento.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento);
						cobrancaDocumento.setUltimaAlteracao(new Date());
					}

					colecaoCobrancaDocumentosSelecionados.add(cobrancaDocumento);

				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoCobrancaDocumentosSelecionados)){
			for(CobrancaDocumento cobrancaDocumento : colecaoCobrancaDocumentosSelecionados){
				/*
				 * 1.3. Registra a transa��o de informar entrega/devolu��o do documento de cobran�a
				 * <<Inclui>> [UC0107 � Registrar Transa��o].
				 */
				// ------------ REGISTRAR TRANSA��O ----------------
				RegistradorOperacao registradorOperacao = new RegistradorOperacao(
								Operacao.OPERACAO_INFORMAR_ENTREGA_DEVOLUCAO_DOCUMENTO_COBRANCA, cobrancaDocumento
												.getCobrancaAcaoSituacao().getId(), cobrancaDocumento.getCobrancaAcaoSituacao().getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_INFORMAR_ENTREGA_DEVOLUCAO_DOCUMENTO_COBRANCA);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				cobrancaDocumento.setOperacaoEfetuada(operacaoEfetuada);
				cobrancaDocumento.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(cobrancaDocumento);
				// ------------ REGISTRAR TRANSA��O ----------------

				// atualiza Cobranca Documento
				fachada.atualizarCobrancaDocumento(cobrancaDocumento);
			}
		}

		// [FS0006 - Verificar sucesso da transa��o]:
		montarPaginaSucesso(httpServletRequest, colecaoCobrancaDocumentosSelecionados.size() + " documento(s) de " + descricaoCobrancaAcao
						+ " com entrega/devolu��o realizada com sucesso.", "Realizar outra Manuten��o de Documentos de Cobran�a",
						"exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do?limparForm=OK");

		return retorno;
	}
}
