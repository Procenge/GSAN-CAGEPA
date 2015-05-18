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

package gcom.gui.micromedicao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRateioTipo;
import gcom.micromedicao.RateioTipo;
import gcom.micromedicao.Rota;
import gcom.micromedicao.hidrometro.FiltroHidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Estabelecer Vinculo
 * 
 * @author Rafael Santos
 * @since 11/01/2006
 */
public class ExibirEstabelecerVinculoPopupAction
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
	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador
	 * @date 21/03/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		ActionForward retorno = actionMapping.findForward("estabelecerVinculoPopup");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		EstabelecerVinculoPopupActionForm estabelecerVinculoPopupActionForm = (EstabelecerVinculoPopupActionForm) actionForm;

		estabelecerVinculoPopupActionForm.reset(actionMapping, httpServletRequest);

		String matriculaImovel = httpServletRequest.getParameter("MatriculaImovel").trim();

		// [FS0011] – Verificar ciclo de faturamento do imóvel
		Boolean imovelEmProcessoDeFaturamento = fachada.verificarImovelEmProcessoDeFaturamento(Integer.valueOf(matriculaImovel));

		if(imovelEmProcessoDeFaturamento){
			FaturamentoGrupo fg = fachada.pesquisarGrupoImovel(Integer.valueOf(matriculaImovel));
			throw new ActionServletException("atencao.imovel.em_processo_faturamento",
							new String[] {matriculaImovel, fg.getId().toString()});
		}

		// [FS0002] Verificar pré-requisitos para imóvel condomínio
		this.verificarPreRequisitosImovelCondominio(fachada, matriculaImovel);

		// Agua
		FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();

		filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.LIGACAO_AGUA_ID,
						new Integer(matriculaImovel)));

		Collection colecaoHidrometroInstalacaoHistorico = fachada.pesquisar(filtroHidrometroInstalacaoHistorico,
						HidrometroInstalacaoHistorico.class.getName());

		if(colecaoHidrometroInstalacaoHistorico != null && !colecaoHidrometroInstalacaoHistorico.isEmpty()){

			RateioTipo rateioTipo = ((HidrometroInstalacaoHistorico) colecaoHidrometroInstalacaoHistorico.iterator().next())
							.getRateioTipo();
			if(rateioTipo != null){
				estabelecerVinculoPopupActionForm.setRateioTipoAgua(rateioTipo.getId().toString());
			}else{
				estabelecerVinculoPopupActionForm.setRateioTipoAgua("");
			}

			estabelecerVinculoPopupActionForm.setBotao("Visualizar");
		}

		// poço
		FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistoricoPoco = new FiltroHidrometroInstalacaoHistorico();
		filtroHidrometroInstalacaoHistoricoPoco.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.IMOVEL_ID,
						new Integer(matriculaImovel)));

		Collection colecaoHidrometroInstalacaoHistoricoPoco = fachada.pesquisar(filtroHidrometroInstalacaoHistoricoPoco,
						HidrometroInstalacaoHistorico.class.getName());

		if(colecaoHidrometroInstalacaoHistoricoPoco != null && !colecaoHidrometroInstalacaoHistoricoPoco.isEmpty()){

			RateioTipo rateioTipo = ((HidrometroInstalacaoHistorico) colecaoHidrometroInstalacaoHistoricoPoco.iterator().next())
							.getRateioTipo();
			if(rateioTipo != null){
				estabelecerVinculoPopupActionForm.setRateioTipoPoco(rateioTipo.getId().toString());
			}else{
				estabelecerVinculoPopupActionForm.setRateioTipoPoco("");
			}
			estabelecerVinculoPopupActionForm.setBotao("Visualizar");
		}

		// Imóveis
		Collection<Imovel> colecaoImoveisASerVinculados = null;

		FiltroRateioTipo filtroRateioTipo = new FiltroRateioTipo();
		filtroRateioTipo.setCampoOrderBy(FiltroRateioTipo.DESCRICAO);
		filtroRateioTipo.adicionarParametro(new ParametroSimples(FiltroRateioTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<RateioTipo> colecaoRateioTipo = fachada.pesquisar(filtroRateioTipo, RateioTipo.class.getName());

		sessao.setAttribute("colecaoRateioTipo", colecaoRateioTipo);

		sessao.setAttribute("matriculaImovel", matriculaImovel);

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, new Integer(matriculaImovel)));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);

		Imovel imovelPesquisado = (Imovel) fachada.pesquisar(filtroImovel, Imovel.class.getName()).iterator().next();

		sessao.setAttribute("imovelCondominio", imovelPesquisado);

		filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.ID, imovelPesquisado.getId()));

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_ID, imovelPesquisado.getQuadra().getId()));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, imovelPesquisado.getLocalidade().getId()));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_ID, imovelPesquisado.getSetorComercial()
							.getId()));
			filtroImovel.setCampoOrderBy(FiltroImovel.LOCALIDADE_ID, FiltroImovel.SETOR_COMERCIAL_CODIGO, FiltroImovel.ROTA_ID,
							FiltroImovel.NUMERO_SEGMENTO, FiltroImovel.LOTE, FiltroImovel.SUBLOTE);
		}else{
			Rota rota = imovelPesquisado.getRota();

			if(rota != null){
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ROTA_ID, rota.getId()));
			}

			filtroImovel.setCampoOrderBy(FiltroImovel.LOCALIDADE_ID, FiltroImovel.SETOR_COMERCIAL_CODIGO, FiltroImovel.QUADRA_ID,
							FiltroImovel.LOTE, FiltroImovel.SUBLOTE);
		}
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial.municipio.unidadeFederacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAgua.hidrometroInstalacaoHistorico.rateioTipo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico.rateioTipo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelCondominio");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

		colecaoImoveisASerVinculados = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		sessao.setAttribute("colecaoImoveisASerVinculados", colecaoImoveisASerVinculados);

		HashMap mapRestrincoes = new HashMap();

		if(colecaoImoveisASerVinculados != null){
			Iterator<Imovel> itImovel = colecaoImoveisASerVinculados.iterator();

			while(itImovel.hasNext()){

				Imovel imovel = itImovel.next();

				// [FS0004] Verificar se o imóvel já é um condomínio
				if(imovel.getIndicadorImovelCondominio().equals(Short.valueOf("1"))){
					mapRestrincoes.put(imovel.getId(), ConstantesAplicacao.get("atencao.imovel.condominio"));

				}
				// [FS0003] Verificar exitência de vínculo com outro imóvel condomínio
				if(imovel.getImovelCondominio() != null && (imovel.getImovelCondominio().getId() != null)){
					mapRestrincoes.put(imovel.getId(), ConstantesAplicacao.get("atencao.imovel.vinculado").replace("{0}",
									imovel.getImovelCondominio().getId().toString()));
				}
				// [FS0002] Verificar pré-requisitos para imóvel condomínio
				// if(imovel.getLigacaoAguaSituacao().getId().intValue() !=
				// LigacaoAguaSituacao.LIGADO.intValue()
				// && imovel.getLigacaoEsgotoSituacao().getId().intValue() !=
				// LigacaoEsgotoSituacao.LIGADO.intValue()){
				// mapRestrincoes.put(imovel.getId(),
				// ConstantesAplicacao.get("atencao.imovel.condominio.nao.agua.esgoto"));
				// }
				if(Short.valueOf(ParametroMicromedicao.P_VERIF_SIT_LIG_AGUA_ESG_IMOVEL_VINCULADO.executar()).equals(ConstantesSistema.SIM)){
					// [FS0010] - Verificar pré-requisitos para imóvel vinculado
					List<Integer> condicao = Arrays.asList(LigacaoAguaSituacao.LIGADO, LigacaoAguaSituacao.CORTADO,
									LigacaoAguaSituacao.CORTADO_PEDIDO, LigacaoAguaSituacao.SUPRIMIDO);

					if(imovel.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.LIGADO.intValue()
									&& !condicao.contains(imovel.getLigacaoAguaSituacao().getId())){
						mapRestrincoes.put(imovel.getId(), ConstantesAplicacao.get("atencao.imovel.agua.incompativel.condominio"));
					}

					// trata se não tem ligação de agua
					if(imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.LIGADO.intValue()
									& imovel.getLigacaoAgua() == null){
						mapRestrincoes.put(imovel.getId(), ConstantesAplicacao.get("atencao.imovel.sem.ligacao_agua"));
					}
				}
			}
		}

		sessao.setAttribute("mapRestrincoes", mapRestrincoes);

		return retorno;
	}

	private void verificarPreRequisitosImovelCondominio(Fachada pFachada, String pMatriculaImovel) throws ControladorException{

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, pMatriculaImovel));

		Imovel imovelCondominio = (Imovel) pFachada.pesquisar(filtroImovel, Imovel.class.getName()).iterator().next();

		// [FS0003] Verificar exitência de vínculo com outro imóvel condomínio
		if(imovelCondominio.getImovelCondominio() != null && (imovelCondominio.getImovelCondominio().getId() != null)){
			throw new ActionServletException("atencao.imovel.vinculado", imovelCondominio.getImovelCondominio().getId().toString());
		}

		// [FS0002] Verificar pré-requisitos para imóvel condomínio
		if(imovelCondominio.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.LIGADO.intValue()
						&& imovelCondominio.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){

			throw new ActionServletException("atencao.imovel.condominio.nao.hidrometro.agua");

		}

		// [FS0002] Verificar pré-requisitos para imóvel condomínio
		if(imovelCondominio.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()
						&& imovelCondominio.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue()
						&& imovelCondominio.getHidrometroInstalacaoHistorico() == null){

			throw new ActionServletException("atencao.imovel.condominio.nao.hidrometro.poco");

		}

		// [FS0002] Verificar pré-requisitos para imóvel condomínio
		// VERIFICAR DÉBITOS DO IMÓVEL - [UC0067 – Obter Débito do Imóvel ou Cliente]
		if(Short.valueOf(ParametroMicromedicao.P_VERIFICAR_DEBITOS_IMOVEL_CONDOMINIO.executar()).equals(ConstantesSistema.SIM)){
			verificarDebitosImovelCondominio(pFachada, pMatriculaImovel);
		}
	}

	private void verificarDebitosImovelCondominio(Fachada pFachada, String pMatriculaImovel){

		String anoMesFaturamento = pFachada.pesquisarParametrosDoSistema().getAnoMesFaturamento() + "";

		Calendar dataFaturamento = new GregorianCalendar();
		dataFaturamento.set(Calendar.YEAR, new Integer(anoMesFaturamento.substring(0, 4)).intValue());
		dataFaturamento.set(Calendar.MONTH, new Integer(anoMesFaturamento.substring(4, 6)).intValue() - 1);
		dataFaturamento.set(Calendar.DATE, 30);

		// data inicio vencimento debito
		Calendar dataInicioVencimentoDebito = new GregorianCalendar();
		dataInicioVencimentoDebito.set(Calendar.YEAR, new Integer("0001").intValue());
		dataInicioVencimentoDebito.set(Calendar.MONTH, 0);
		dataInicioVencimentoDebito.set(Calendar.DATE, new Integer("01").intValue());

		// data final de vencimento de debito
		Calendar dataFimVencimentoDebito = new GregorianCalendar();
		dataFimVencimentoDebito.set(Calendar.YEAR, new Integer(anoMesFaturamento.substring(0, 4)).intValue());
		dataFimVencimentoDebito.set(Calendar.MONTH, new Integer(anoMesFaturamento.substring(4, 6)).intValue());
		dataFimVencimentoDebito.set(Calendar.DAY_OF_MONTH, dataFaturamento.getMaximum(Calendar.DAY_OF_MONTH));

		// data final referencia debito
		dataFaturamento.add(Calendar.MONTH, -1);
		StringBuffer dataFinalReferenciaDebito = new StringBuffer().append(dataFaturamento.get(Calendar.YEAR));
		if(dataFaturamento.get(Calendar.MONTH) < 10){
			dataFinalReferenciaDebito.append("0" + dataFaturamento.get(Calendar.MONTH));
		}else{
			dataFinalReferenciaDebito.append(dataFaturamento.get(Calendar.MONTH));
		}

		ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = pFachada.obterDebitoImovelOuCliente(1, pMatriculaImovel, null,
						null, "000101", dataFinalReferenciaDebito.toString(), dataInicioVencimentoDebito.getTime(),
						dataFimVencimentoDebito.getTime(), 1, 1, 1, 1, 1, 1, 2, null, null, null, null, null, ConstantesSistema.SIM,
						ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);

		boolean existeDebito = false;
		if(obterDebitoImovelOuClienteHelper != null){
			// contas
			if(obterDebitoImovelOuClienteHelper.getColecaoContasValores() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoContasValores().isEmpty()){
				existeDebito = true;
			}else
			// credito a realizar
			if(obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar().isEmpty()){
				existeDebito = true;
			}else
			// debito a cobrar
			if(obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar().isEmpty()){
				existeDebito = true;
			}else
			// guias pagamento
			if(obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores() != null
							&& !obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores().isEmpty()){
				existeDebito = true;
			}

		}

		// Se existir debito para o imovel
		if(existeDebito){
			throw new ActionServletException("atencao.imovel.condominio.possui_debito");
		}
	}
}
