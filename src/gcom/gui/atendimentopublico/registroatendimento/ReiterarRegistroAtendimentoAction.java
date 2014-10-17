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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <<Descri��o da Classe>>
 * 
 * @author lms
 * @date 20/09/2006
 */
public class ReiterarRegistroAtendimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		ReiterarRegistroAtendimentoActionForm form = (ReiterarRegistroAtendimentoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		RegistroAtendimento registroAtendimento = (RegistroAtendimento) sessao.getAttribute("registroAtendimento");

		// cria o novo RA para salvar
		RegistroAtendimento raNovo = new RegistroAtendimento();
		if(registroAtendimento.getCoordenadaLeste() == null){
			registroAtendimento.setCoordenadaLeste(BigDecimal.ZERO);
		}
		if(registroAtendimento.getCoordenadaNorte() == null){
			registroAtendimento.setCoordenadaNorte(BigDecimal.ZERO);
		}
		try{
			PropertyUtils.copyProperties(raNovo, registroAtendimento);
		}catch(IllegalAccessException e){
			throw new ActionServletException("erro.sistema", e);
		}catch(InvocationTargetException e){
			throw new ActionServletException("erro.sistema", e);
		}catch(NoSuchMethodException e){
			throw new ActionServletException("erro.sistema", e);
		}

		try{
			raNovo.setId(fachada.obterSequenceRA());
		}catch(ControladorException e){
			throw new ActionServletException("erro.sistema");
		}

		// indicador de execu��o liberada
		raNovo.setIndicadorExecucaoLiberada(RegistroAtendimento.INDICADOR_EXECUCAO_LIBERADA);

		// Solicita�ao Tipo Especifica��o
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
		solicitacaoTipoEspecificacao.setId(sistemaParametro.getSolicitacaoTipoEspecificacaoReiteracao());
		raNovo.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);

		// Atendimento Motivo Encerramento
		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
		atendimentoMotivoEncerramento.setId(AtendimentoMotivoEncerramento.ENCERRAMENTO_AUTOMATICO.intValue());
		raNovo.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);

		// Meio Solicita��o
		MeioSolicitacao meioSolicitacao = new MeioSolicitacao();
		meioSolicitacao.setId(Integer.valueOf(form.getMeioSolicitacao()));
		raNovo.setMeioSolicitacao(meioSolicitacao);

		raNovo.setCodigoSituacao(RegistroAtendimento.SITUACAO_ENCERRADO);
		raNovo.setReiteracao(registroAtendimento.getId());
		raNovo.setRegistroAtendimento(new Date());
		raNovo.setQuantidadeReiteracao(null);
		raNovo.setUltimaReiteracao(null);
		raNovo.setObservacao(form.getObservacaoNova());
		raNovo.setUltimaAlteracao(new Date());
		raNovo.setDataInicioEspera(null);
		raNovo.setDataFimEspera(null);
		raNovo.setUltimaEmissao(null);
		raNovo.setDataEncerramento(new Date());
		raNovo.setManual(null);
		raNovo.setSenhaAtendimento(null);
		raNovo.setCoordenadaLeste(null);
		raNovo.setCoordenadaNorte(null);

		// gera a ordem de servi�o
		fachada.reiterarRegistroAtendimento(registroAtendimento, usuario, raNovo);

		// Exibe a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "RA - Registro de Atendimento de n�mero " + raNovo.getId() + " reiterado com sucesso.",
						"Atualizar outro RA - Registro de Atendimento", "exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
						"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + registroAtendimento.getId(),
						"Atualizar RA - Registro de Atendimento Reiterado");

		return retorno;
	}

}