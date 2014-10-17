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

package gcom.gui.relatorio.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioRAComProcessoAdmJud;
import gcom.relatorio.atendimentopublico.RelatorioRAComProcessoAdmJudHelper;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] Relat�rio Registro Atendimento Com Processo Adm Jud
 * 
 * @author Carlos Chrystian
 * @date 31/01/2014
 */
public class GerarRelatorioRAComProcessoAdmJudAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		GerarRelatorioRAComProcessoAdmJudActionForm form = (GerarRelatorioRAComProcessoAdmJudActionForm) actionForm;

		Date periodoAtendimentoInicial = null;
		Date periodoAtendimentoFinal = null;
		UnidadeOrganizacional unidadeSuperior = null;
		UnidadeOrganizacional unidadeAtendimento = null;
		Usuario usuario = null;
		Collection<SolicitacaoTipo> tiposSolicitacao = null;
		Collection<SolicitacaoTipoEspecificacao> especificacoes = null;
		UnidadeOrganizacional unidadeAtual = null;

		// Captura a data inicial do per�odo de atendimento
		if(!Util.isVazioOuBranco(form.getPeriodoAtendimentoInicial())){
			try{
				periodoAtendimentoInicial = Util.converteStringParaDate(form.getPeriodoAtendimentoInicial());

			}catch(IllegalArgumentException e){
				throw new ActionServletException("atencao.data.inicial.invalida");
			}
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", "Data Inicial");
		}

		// Captura a data final do per�odo de atendimento
		if(!Util.isVazioOuBranco(form.getPeriodoAtendimentoFinal())){
			try{
				periodoAtendimentoFinal = Util.converteStringParaDate(form.getPeriodoAtendimentoFinal());

			}catch(IllegalArgumentException e){
				throw new ActionServletException("atencao.data.final.invalida");
			}
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", "Data Final");
		}

		// Captura a unidade superior
		if(!Util.isVazioOuBranco(form.getUnidadeSuperiorId())){
			unidadeSuperior = getUnidadeOrganizacional(form.getUnidadeSuperiorId());
			// [FS008] Verificar exist�ncia de unidades subordinadas
			Fachada.getInstancia().verificarExistenciaUnidadesSubordinadas(unidadeSuperior);

		}

		// Captura a unidade de atendimento
		if(!Util.isVazioOuBranco(form.getUnidadeAtendimentoId())){
			unidadeAtendimento = getUnidadeOrganizacional(form.getUnidadeAtendimentoId());
		}

		// Captura o usu�rio
		if(!Util.isVazioOuBranco(form.getLoginUsuario())){
			usuario = getUsuario(form.getLoginUsuario());
		}

		// Captura os tipos de solicita��o
		if(!Util.isVazioOrNulo(form.getTipoSolicitacao())){
			tiposSolicitacao = getTiposSolicitacao(form.getTipoSolicitacao());
		}

		// Captura as especifica��es
		if(!Util.isVazioOrNulo(form.getEspecificacao())){
			especificacoes = getEspecificacoes(form.getEspecificacao());
		}

		// Captura a unidade atual
		if(!Util.isVazioOuBranco(form.getUnidadeAtualId())){
			unidadeAtual = getUnidadeOrganizacional(form.getUnidadeAtualId());
		}

		RelatorioRAComProcessoAdmJudHelper helper = new RelatorioRAComProcessoAdmJudHelper(periodoAtendimentoInicial,
						periodoAtendimentoFinal);

		helper.setUnidadeSuperior(unidadeSuperior);
		helper.setUnidadeAtendimento(unidadeAtendimento);
		helper.setUsuario(usuario);
		helper.setTiposSolicitacao(tiposSolicitacao);
		helper.setEspecificacoes(especificacoes);
		helper.setUnidadeAtual(unidadeAtual);
		helper.setSituacao(form.getSituacao());

		Fachada.getInstancia().validarCamposObrigatoriosHelperRA(helper);

		int qtdRegistros = Fachada.getInstancia().consultarQuantidadeRAComProcessoAdmJud(helper);

		if(qtdRegistros == 0){
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		RelatorioRAComProcessoAdmJud relatorio = new RelatorioRAComProcessoAdmJud(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorio.addParametro(RelatorioRAComProcessoAdmJud.FILTRO_INFORMADO, helper);
		relatorio.addParametro(RelatorioRAComProcessoAdmJud.FORMATO_RELATORIO, TarefaRelatorio.TIPO_PDF);
		relatorio.addParametro("FiltroSituacao", helper.getSituacao());

		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}

	/**
	 * Recupera uma Unidade Organizacional a partir do Id
	 * 
	 * @author Luciano Galvao
	 * @param unidadeOrganizacionalId
	 * @return
	 */
	private UnidadeOrganizacional getUnidadeOrganizacional(String unidadeOrganizacionalId){

		UnidadeOrganizacional unidade = null;
		try{
			FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer.parseInt(unidadeOrganizacionalId)));

			unidade = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro,
							UnidadeOrganizacional.class.getName()));

			if(unidade == null){
				throw new ActionServletException("atencao.unidadeOrganizacional.inexistente");
			}

		}catch(NumberFormatException e){
			throw new ActionServletException("atencao.numero.formato.invalido");
		}

		return unidade;
	}

	/**
	 * Recupera um Usu�rio a partir do Id
	 * 
	 * @author Luciano Galvao
	 * @param loginUsuario
	 * @return
	 */
	private Usuario getUsuario(String loginUsuario){

		Usuario usuario = null;
		FiltroUsuario filtro = new FiltroUsuario();
		filtro.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, loginUsuario));

		usuario = (Usuario) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro, Usuario.class.getName()));

		if(usuario == null){
			throw new ActionServletException("atencao.usuario.naoencontrado");
		}

		return usuario;
	}

	/**
	 * Retorna uma cole��o de tipos de solicita��o a partir de um conjunto de Ids
	 * 
	 * @author Luciano Galvao
	 * @param tiposSolicitacaoId
	 * @return
	 */
	private Collection<SolicitacaoTipo> getTiposSolicitacao(String[] tiposSolicitacaoId){

		// Tipo Solicita��o
		Collection<SolicitacaoTipo> tiposSolicitacao = null;

		for(int i = 0; i < tiposSolicitacaoId.length; i++){

			if(!Util.isVazioOuBranco(tiposSolicitacaoId[i]) && !tiposSolicitacaoId[i].equals("0") && !tiposSolicitacaoId[i].equals("-1")){

				try{

					FiltroSolicitacaoTipo filtro = new FiltroSolicitacaoTipo();
					filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, Integer.parseInt(tiposSolicitacaoId[i])));

					SolicitacaoTipo tipoSolicitacao = (SolicitacaoTipo) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
									filtro, SolicitacaoTipo.class.getName()));

					if(tipoSolicitacao == null){
						throw new ActionServletException("atencao.solicitacaotipo.inexistente");
					}

					if(tiposSolicitacao == null){
						tiposSolicitacao = new ArrayList();
					}

					tiposSolicitacao.add(tipoSolicitacao);

				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.numero.formato.invalido");
				}
			}
		}

		return tiposSolicitacao;
	}

	/**
	 * Retorna uma cole��o de tipos de solicita��o a partir de um conjunto de Ids
	 * 
	 * @author Luciano Galvao
	 * @param tiposSolicitacaoId
	 * @return
	 */
	private Collection<SolicitacaoTipoEspecificacao> getEspecificacoes(String[] especificacoesId){

		// Tipo Solicita��o
		Collection<SolicitacaoTipoEspecificacao> especificacoes = null;

		for(int i = 0; i < especificacoesId.length; i++){

			if(!Util.isVazioOuBranco(especificacoesId[i]) && !especificacoesId[i].equals("0") && !especificacoesId[i].equals("-1")){

				try{

					FiltroSolicitacaoTipoEspecificacao filtro = new FiltroSolicitacaoTipoEspecificacao();
					filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, Integer
									.parseInt(especificacoesId[i])));

					SolicitacaoTipoEspecificacao especificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(Fachada
									.getInstancia().pesquisar(filtro, SolicitacaoTipoEspecificacao.class.getName()));

					if(especificacao == null){
						throw new ActionServletException("atencao.especificacao.inexistente");
					}

					if(especificacoes == null){
						especificacoes = new ArrayList();
					}

					especificacoes.add(especificacao);

				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.numero.formato.invalido");
				}
			}
		}

		return especificacoes;
	}

}
