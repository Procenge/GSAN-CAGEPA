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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.micromedicao.leitura;

import gcom.atendimentopublico.ordemservico.FiltroTipoServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidadeLeitura;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.LeituraAnormalidadeLeitura;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirAnormalidadeLeituraAction
				extends GcomAction {

	/**
	 * Este caso de uso permite inserir uma Anormalidade de Leitura
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @author Thiago Tenório
	 * @author eduardo henrique [UC0190]
	 * @date 16/06/2008
	 *       Inclusão dos atributos: Mensagem de Leitura , Mensagem de manutenção, Mensagem de
	 *       prevenção de acidentes
	 *       Incidência da anormalidade para a geração da ordem de serviço,
	 *       Emissão automática de documento, Indicador de que a ocorrência aceita leitura,
	 *       Indicador de que a ocorrência deve ser listada nos relatórios de crítica/fiscalização
	 *       de leitura
	 *       Indicador de retenção de conta , Indicador de concessão de crédito de consumo
	 *       Indicador de isenção de cobrança de água, Indicador de isenção de cobrança de esgoto
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		InserirAnormalidadeLeituraActionForm inserirAnormalidadeLeituraActionForm = (InserirAnormalidadeLeituraActionForm) actionForm;

		String descricao = inserirAnormalidadeLeituraActionForm.getDescricao();
		String abreviatura = inserirAnormalidadeLeituraActionForm.getAbreviatura();
		String indicadorRelativoHidrometro = inserirAnormalidadeLeituraActionForm.getIndicadorRelativoHidrometro();
		String indicadorImovelSemHidrometro = inserirAnormalidadeLeituraActionForm.getIndicadorImovelSemHidrometro();
		String usoRestritoSistema = inserirAnormalidadeLeituraActionForm.getUsoRestritoSistema();
		String perdaTarifaSocial = inserirAnormalidadeLeituraActionForm.getPerdaTarifaSocial();
		String osAutomatico = inserirAnormalidadeLeituraActionForm.getOsAutomatico();
		String tipoServico = inserirAnormalidadeLeituraActionForm.getTipoServico();
		String consumoLeituraNaoInformado = inserirAnormalidadeLeituraActionForm.getConsumoLeituraNaoInformado();
		String consumoLeituraInformado = inserirAnormalidadeLeituraActionForm.getConsumoLeituraInformado();
		String leituraLeituraNaoturaInformado = inserirAnormalidadeLeituraActionForm.getLeituraLeituraNaoturaInformado();
		String leituraLeituraInformado = inserirAnormalidadeLeituraActionForm.getLeituraLeituraInformado();

		// A Anormalidade Relativa a Hidrômetro é obrigatório.
		if(indicadorRelativoHidrometro == null || indicadorRelativoHidrometro.trim().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Anormalidade Relativa a Hidrômetro");
		}

		// Anormalidade Emite OS Automática é obrigatório.
		if(osAutomatico == null || osAutomatico.trim().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Anormalidade Emite OS Automática");
		}

		LeituraAnormalidade anormalidadeLeituraInserir = new LeituraAnormalidade();
		Collection colecaoPesquisa = null;

		sessao.removeAttribute("tipoPesquisaRetorno");

		if(Util.validarNumeroMaiorQueZERO(inserirAnormalidadeLeituraActionForm.getTipoServico())){
			// Constrói o filtro para pesquisa
			FiltroTipoServico filtroTipoServico = new FiltroTipoServico();
			filtroTipoServico.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID_TIPO_SERVICO,
							inserirAnormalidadeLeituraActionForm.getTipoServico()));
		}

		if(inserirAnormalidadeLeituraActionForm.getConsumoLeituraNaoInformado() != null){

			Integer idConsumoLeituraNaoInformado = new Integer(inserirAnormalidadeLeituraActionForm.getConsumoLeituraNaoInformado());

			if(idConsumoLeituraNaoInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				anormalidadeLeituraInserir.setLeituraAnormalidadeConsumoSemleitura(null);
			}else{
				FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumo = new FiltroLeituraAnormalidadeConsumo();
				filtroLeituraAnormalidadeConsumo.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeConsumo.ID,
								inserirAnormalidadeLeituraActionForm.getConsumoLeituraNaoInformado().toString()));
				Collection colecaoConsumoLeituraNaoInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeConsumo,
								LeituraAnormalidadeConsumo.class.getName());

				// setando
				anormalidadeLeituraInserir
								.setLeituraAnormalidadeConsumoSemleitura((LeituraAnormalidadeConsumo) colecaoConsumoLeituraNaoInformado
												.iterator().next());
			}
		}

		if(inserirAnormalidadeLeituraActionForm.getConsumoLeituraInformado() != null){

			Integer idConsumoLeituraInformado = new Integer(inserirAnormalidadeLeituraActionForm.getConsumoLeituraInformado());

			if(idConsumoLeituraInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				anormalidadeLeituraInserir.setLeituraAnormalidadeConsumoComleitura(null);
			}else{
				FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumo = new FiltroLeituraAnormalidadeConsumo();
				filtroLeituraAnormalidadeConsumo.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeConsumo.ID,
								inserirAnormalidadeLeituraActionForm.getConsumoLeituraInformado().toString()));
				Collection colecaoConsumoLeituraInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeConsumo,
								LeituraAnormalidadeConsumo.class.getName());

				// setando
				anormalidadeLeituraInserir
								.setLeituraAnormalidadeConsumoComleitura((LeituraAnormalidadeConsumo) colecaoConsumoLeituraInformado
												.iterator().next());
			}
		}

		if(inserirAnormalidadeLeituraActionForm.getLeituraLeituraNaoturaInformado() != null){

			Integer idLeituraLeituraNaoturaInformado = new Integer(inserirAnormalidadeLeituraActionForm.getLeituraLeituraNaoturaInformado());

			if(idLeituraLeituraNaoturaInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				anormalidadeLeituraInserir.setLeituraAnormalidadeLeituraSemleitura(null);
			}else{
				FiltroLeituraAnormalidadeLeitura filtroLeituraAnormalidadeLeitura = new FiltroLeituraAnormalidadeLeitura();
				filtroLeituraAnormalidadeLeitura.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeLeitura.ID,
								inserirAnormalidadeLeituraActionForm.getLeituraLeituraNaoturaInformado().toString()));
				Collection colecaoLeituraLeituraNaoturaInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeLeitura,
								LeituraAnormalidadeLeitura.class.getName());

				// setando
				anormalidadeLeituraInserir
								.setLeituraAnormalidadeLeituraSemleitura((LeituraAnormalidadeLeitura) colecaoLeituraLeituraNaoturaInformado
												.iterator().next());
			}
		}

		if(inserirAnormalidadeLeituraActionForm.getLeituraLeituraInformado() != null){

			Integer idLeituraLeituraInformado = new Integer(inserirAnormalidadeLeituraActionForm.getLeituraLeituraInformado());

			if(idLeituraLeituraInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				anormalidadeLeituraInserir.setLeituraAnormalidadeLeituraComleitura(null);
			}else{
				FiltroLeituraAnormalidadeLeitura filtroLeituraAnormalidadeLeitura = new FiltroLeituraAnormalidadeLeitura();
				filtroLeituraAnormalidadeLeitura.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeLeitura.ID,
								inserirAnormalidadeLeituraActionForm.getLeituraLeituraInformado().toString()));
				Collection colecaoLeituraLeituraInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeLeitura,
								LeituraAnormalidadeLeitura.class.getName());

				// setando
				anormalidadeLeituraInserir
								.setLeituraAnormalidadeLeituraComleitura((LeituraAnormalidadeLeitura) colecaoLeituraLeituraInformado
												.iterator().next());
			}
		}

		anormalidadeLeituraInserir.setDescricao(descricao);

		if(abreviatura != null && !abreviatura.equalsIgnoreCase("")){
			anormalidadeLeituraInserir.setDescricaoAbreviada(abreviatura);
		}

		anormalidadeLeituraInserir.setIndicadorRelativoHidrometro(new Short(indicadorRelativoHidrometro));

		if(indicadorImovelSemHidrometro != null && !indicadorImovelSemHidrometro.equalsIgnoreCase("")){
			anormalidadeLeituraInserir.setIndicadorImovelSemHidrometro(new Short(indicadorImovelSemHidrometro));
		}

		if(usoRestritoSistema != null && !usoRestritoSistema.equalsIgnoreCase("")){
			anormalidadeLeituraInserir.setIndicadorSistema(new Short(usoRestritoSistema));
		}

		if(perdaTarifaSocial != null && !perdaTarifaSocial.equalsIgnoreCase("")){
			anormalidadeLeituraInserir.setIndicadorPerdaTarifaSocial(new Short(perdaTarifaSocial));
		}

		anormalidadeLeituraInserir.setIndicadorEmissaoOrdemServico(new Short(osAutomatico));
		// Adicinado em 16/06/2008 - Eduardo Henrique
		if(inserirAnormalidadeLeituraActionForm.getNumeroIncidenciasGeracaoOS() != null
						&& !inserirAnormalidadeLeituraActionForm.getNumeroIncidenciasGeracaoOS().equals("")){
			anormalidadeLeituraInserir.setNumeroIncidenciasGeracaoOrdemServico(new Short(inserirAnormalidadeLeituraActionForm
							.getNumeroIncidenciasGeracaoOS()));
		}

		if(inserirAnormalidadeLeituraActionForm.getAceiteLeitura() != null
						&& !inserirAnormalidadeLeituraActionForm.getAceiteLeitura().equals("")){
			anormalidadeLeituraInserir.setIndicadorAceiteLeitura(new Short(inserirAnormalidadeLeituraActionForm.getAceiteLeitura()));
		}

		if(inserirAnormalidadeLeituraActionForm.getImpressaoRelatorioCriticaFiscalizacao() != null
						&& !inserirAnormalidadeLeituraActionForm.getImpressaoRelatorioCriticaFiscalizacao().equals("")){
			anormalidadeLeituraInserir.setIndicadorListagemAnormalidadeRelatorios(new Short(inserirAnormalidadeLeituraActionForm
							.getImpressaoRelatorioCriticaFiscalizacao()));
		}

		if(inserirAnormalidadeLeituraActionForm.getIndicadorRetencaoConta() != null
						&& !inserirAnormalidadeLeituraActionForm.getIndicadorRetencaoConta().equals("")){
			anormalidadeLeituraInserir
							.setIndicadorRetencaoConta(new Short(inserirAnormalidadeLeituraActionForm.getIndicadorRetencaoConta()));
		}

		if(inserirAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaAgua() != null
						&& !inserirAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaAgua().equals("")){
			anormalidadeLeituraInserir.setIndicadorIsencaoAgua(new Short(inserirAnormalidadeLeituraActionForm
							.getIndicadorIsencaoCobrancaAgua()));
		}

		if(inserirAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaEsgoto() != null
						&& !inserirAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaEsgoto().equals("")){
			anormalidadeLeituraInserir.setIndicadorIsencaoEsgoto(new Short(inserirAnormalidadeLeituraActionForm
							.getIndicadorIsencaoCobrancaEsgoto()));
		}

		if(inserirAnormalidadeLeituraActionForm.getIndicadorConcessaoCreditoConsumo() != null
						&& !inserirAnormalidadeLeituraActionForm.getIndicadorConcessaoCreditoConsumo().equals("")){
			anormalidadeLeituraInserir.setIndicadorCreditoConsumo(new Short(inserirAnormalidadeLeituraActionForm
							.getIndicadorConcessaoCreditoConsumo()));
		}

		if(inserirAnormalidadeLeituraActionForm.getMensagemImpressaoConta() != null
						&& !inserirAnormalidadeLeituraActionForm.getMensagemImpressaoConta().equals("")){
			anormalidadeLeituraInserir
							.setMensagemContaLeituraAnormalidade(inserirAnormalidadeLeituraActionForm.getMensagemImpressaoConta());
		}

		if(inserirAnormalidadeLeituraActionForm.getSugestaoAgenteManutencao() != null
						&& !inserirAnormalidadeLeituraActionForm.getSugestaoAgenteManutencao().equals("")){
			anormalidadeLeituraInserir.setMensagemSugestaoManutencaoLeituraAnormalidade(inserirAnormalidadeLeituraActionForm
							.getSugestaoAgenteManutencao());
		}

		if(inserirAnormalidadeLeituraActionForm.getSugestaoAgentePrevencao() != null
						&& !inserirAnormalidadeLeituraActionForm.getSugestaoAgentePrevencao().equals("")){
			anormalidadeLeituraInserir.setMensagemSugestaoPrevencaoLeituraAnormalidade(inserirAnormalidadeLeituraActionForm
							.getSugestaoAgentePrevencao());
		}

		if(inserirAnormalidadeLeituraActionForm.getTipoDocumento() != null
						&& !inserirAnormalidadeLeituraActionForm.getTipoDocumento().equals("")
						&& !(new Integer(inserirAnormalidadeLeituraActionForm.getTipoDocumento())
										.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(new Integer(inserirAnormalidadeLeituraActionForm.getTipoDocumento()));

			anormalidadeLeituraInserir.setDocumentoTipo(documentoTipo);
		}
		//

		if(tipoServico != null && !tipoServico.equals("") && !(new Integer(tipoServico).equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			ServicoTipo servicoTipo = new ServicoTipo();
			servicoTipo.setId(new Integer(tipoServico));
			anormalidadeLeituraInserir.setServicoTipo(servicoTipo);
		}

		LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemLeitua = new LeituraAnormalidadeConsumo();

		leituraAnormalidadeConsumoSemLeitua.setId(new Integer(consumoLeituraNaoInformado));
		anormalidadeLeituraInserir.setLeituraAnormalidadeConsumoSemleitura(leituraAnormalidadeConsumoSemLeitua);

		LeituraAnormalidadeConsumo leituraAnormalidadeConsumo = new LeituraAnormalidadeConsumo();

		leituraAnormalidadeConsumo.setId(new Integer(consumoLeituraInformado));
		anormalidadeLeituraInserir.setLeituraAnormalidadeConsumoComleitura(leituraAnormalidadeConsumo);

		LeituraAnormalidadeLeitura leituraAnormalidadeNaoLeitura = new LeituraAnormalidadeLeitura();

		leituraAnormalidadeNaoLeitura.setId(new Integer(leituraLeituraNaoturaInformado));
		anormalidadeLeituraInserir.setLeituraAnormalidadeLeituraSemleitura(leituraAnormalidadeNaoLeitura);

		LeituraAnormalidadeLeitura leituraAnormalidadeLeitura = new LeituraAnormalidadeLeitura();

		leituraAnormalidadeLeitura.setId(new Integer(leituraLeituraInformado));
		anormalidadeLeituraInserir.setLeituraAnormalidadeLeituraComleitura(leituraAnormalidadeLeitura);

		// Ultima alteração
		anormalidadeLeituraInserir.setUltimaAlteracao(new Date());
		// Indicador de uso
		Short iu = 1;
		anormalidadeLeituraInserir.setIndicadorUso(iu);

		Integer idAnormalidadeLeitura = fachada.inserirAnormalidadeLeitura(anormalidadeLeituraInserir, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Anormalidade de Leitura de código  " + anormalidadeLeituraInserir.getId()
						+ " inserida com sucesso.", "Inserir outra Anormalidade de Leitura",
						"exibirInserirAnormalidadeLeituraAction.do?menu=sim",
						"exibirAtualizarAnormalidadeLeituraAction.do?idRegistroAtualizacao=" + idAnormalidadeLeitura,
						"Atualizar Anormalidade de Leitura");

		// devolve o mapeamento de retorno
		return retorno;
	}
}
