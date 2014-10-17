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

package gcom.gui;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoSubgrupo;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.cadastro.imovel.ImovelCobrancaMotivoRetirada;
import gcom.cobranca.CobrancaSituacao;
import gcom.contabil.EventoComercial;
import gcom.contabil.ProvisaoDevedoresDuvidososMotivoBaixa;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.DebitoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesConfig;
import gcom.util.HibernateUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * Servlet Class
 * 
 * @web.servlet name="InicializadorSistema" display-name="Name for
 *              InicializadorSistema" description="Description for
 *              InicializadorSistema"
 * @web.servlet-mapping url-pattern="/InicializadorSistema"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class InicializadorSistema
				extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(InicializadorSistema.class);

	public InicializadorSistema() {

		String simNao = ConstantesConfig.getAbortarJbossParametrosAusente();

		try{
			HibernateUtil.inicializarSessionFactory();
			// TimeZone.setDefault(TimeZone.getTimeZone("America/Recife"));
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar inicializarSessionFactory", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			Usuario.inicializarUsuariosPadroes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar Usuario.inicializarUsuariosPadroes", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			// Carregar constantes com valores conflitantes para diferentes clientes
			LigacaoEsgotoSituacao.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: LigacaoEsgotoSituacao", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			ServicoTipo.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ServicoTipo", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			ConsumoTipo.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ConsumoTipo", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			ConsumoAnormalidade.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ConsumoAnormalidade", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			// Carregar constantes com valores cadastrados no banco para cada empresa
			LigacaoAguaSituacao.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: LigacaoAguaSituacao", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		ServicoTipoSubgrupo.inicializarConstantes();

		try{
			ContaMotivoRevisao.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ContaMotivoRevisao", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			AtendimentoMotivoEncerramento.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: AtendimentoMotivoEncerramento", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			EventoComercial.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: EventoComercial", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			ProvisaoDevedoresDuvidososMotivoBaixa.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ProvisaoDevedoresDuvidososMotivoBaixa", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			DebitoTipo.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: DebitoTipo", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			LeituraAnormalidade.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: LeituraAnormalidade", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			ContaMotivoCancelamento.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ContaMotivoCancelamento", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			ImovelCobrancaMotivoRetirada.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ImovelCobrancaMotivoRetirada", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			LancamentoItemContabil.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: ImovelCobrancaMotivoRetirada", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			String agendadorAtivo = ConstantesConfig.getAgendador();
			if(agendadorAtivo == null || agendadorAtivo.equals("1")){
				AgendadorTarefas.initAgendador();
			}
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: AgendadorTarefas.initAgendador", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			CreditoTipo.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: CreditoTipo", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}

		try{
			CobrancaSituacao.inicializarConstantes();
		}catch(Exception e){
			LOGGER.info("Erro ao inicializar constantes em: CobrancaSituacao", e);
			LOGGER.error(e);
			abortarAplicacao(simNao);
		}
	}

	/**
	 * Metodo que vai para a inicializacao do sitema.
	 * 
	 * @param simNao
	 */
	private void abortarAplicacao(String simNao){

		if(simNao == null || simNao.equals("1")){
			System.exit(0);
		}
	}

}
