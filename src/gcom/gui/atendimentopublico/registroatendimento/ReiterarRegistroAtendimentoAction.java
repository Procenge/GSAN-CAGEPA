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
 * <<Descrição da Classe>>
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

		// indicador de execução liberada
		raNovo.setIndicadorExecucaoLiberada(RegistroAtendimento.INDICADOR_EXECUCAO_LIBERADA);

		// Solicitaçao Tipo Especificação
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
		solicitacaoTipoEspecificacao.setId(sistemaParametro.getSolicitacaoTipoEspecificacaoReiteracao());
		raNovo.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);

		// Atendimento Motivo Encerramento
		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
		atendimentoMotivoEncerramento.setId(AtendimentoMotivoEncerramento.ENCERRAMENTO_AUTOMATICO.intValue());
		raNovo.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);

		// Meio Solicitação
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

		// gera a ordem de serviço
		fachada.reiterarRegistroAtendimento(registroAtendimento, usuario, raNovo);

		// Exibe a página de sucesso
		montarPaginaSucesso(httpServletRequest, "RA - Registro de Atendimento de número " + raNovo.getId() + " reiterado com sucesso.",
						"Atualizar outro RA - Registro de Atendimento", "exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
						"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + registroAtendimento.getId(),
						"Atualizar RA - Registro de Atendimento Reiterado");

		return retorno;
	}

}