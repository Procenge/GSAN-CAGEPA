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

package gcom.gui.faturamento.conta;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroVencimentoAlternativo;
import gcom.faturamento.SetorComercialVencimento;
import gcom.faturamento.VencimentoAlternativo;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.CalendarioForm;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInformarVencimentoAlternativoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("informarVencimentoAlternativo");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Instância do formulário que está sendo utilizado
		InformarVencimentoAlternativoActionForm informarVencimentoAlternativoActionForm = (InformarVencimentoAlternativoActionForm) actionForm;

		Integer novoDiaVencimento = 0;

		Imovel imovel = null;
		Cliente cliente = null;

		VencimentoAlternativo vencimentoAlternativo = null;

		informarVencimentoAlternativoActionForm.setDiaVencimentoAtual("");
		informarVencimentoAlternativoActionForm.setDataAlteracaoVencimento("");
		informarVencimentoAlternativoActionForm.setNovoDiaVencimento("");

		if(informarVencimentoAlternativoActionForm.getIdImovel() != null
						&& !informarVencimentoAlternativoActionForm.getIdImovel().equals("")){
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoAguaSituacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoEsgotoSituacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.rota");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.rota.faturamentoGrupo");
			// falta 3,7 - VENC_DTIMPLANTACAO
			// filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("");
			/*
			 * filtroClienteImovel
			 * .adicionarCaminhoParaCarregamentoEntidade("imovel.diaVencimento");
			 */
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel");

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID,
							informarVencimentoAlternativoActionForm.getIdImovel()));

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
							ClienteRelacaoTipo.USUARIO));

			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

			Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			if(colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
				httpServletRequest.setAttribute("corInscricao", "exception");
				informarVencimentoAlternativoActionForm.setIdImovel("");
				informarVencimentoAlternativoActionForm.setInscricaoImovel("Matrícula Inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}else{

				ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

				/*
				 * // [FS0001] - Verificar Existência de RA
				 * fachada.verificarExistenciaRegistroAtendimento(clienteImovel.getImovel().getId(),
				 * "atencao.vencimento_alternativo_existencia_registro_atendimento",
				 * EspecificacaoTipoValidacao.VENCIMENTO_ALTERNATIVO);
				 */

				informarVencimentoAlternativoActionForm.setNomeClienteUsuario(clienteImovel.getCliente().getNome());
				informarVencimentoAlternativoActionForm.setSituacaoAguaImovel(clienteImovel.getImovel().getLigacaoAguaSituacao()
								.getDescricao());
				informarVencimentoAlternativoActionForm.setSituacaoEsgotoImovel(clienteImovel.getImovel().getLigacaoEsgotoSituacao()
								.getDescricao());

				Short diaVencimento = clienteImovel.getImovel().getDiaVencimento();

				informarVencimentoAlternativoActionForm.setDiaVencimentoAtual((diaVencimento == null || diaVencimento == 0) ? "" : ""
								+ diaVencimento);
				imovel = clienteImovel.getImovel();
				cliente = clienteImovel.getCliente();
				String inscricao = imovel.getInscricaoFormatada();
				informarVencimentoAlternativoActionForm.setInscricaoImovel(inscricao);
				novoDiaVencimento = clienteImovel.getImovel().getRota().getFaturamentoGrupo().getDiaVencimento().intValue();

				informarVencimentoAlternativoActionForm.setDiaVencimentoGrupo((novoDiaVencimento == null || novoDiaVencimento == 0) ? ""
								: "" + novoDiaVencimento);

				FiltroVencimentoAlternativo filtroVencimentoAlternativo = new FiltroVencimentoAlternativo(
								FiltroVencimentoAlternativo.DATA_IMPLANTACAO);

				filtroVencimentoAlternativo.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.IMOVEL_ID, imovel.getId()));

				filtroVencimentoAlternativo
								.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.CLIENTE_ID, cliente.getId()));

				filtroVencimentoAlternativo.adicionarParametro(new ParametroNulo(FiltroVencimentoAlternativo.DATA_EXCLUSAO));

				Collection vencimentosAlternativos = fachada.pesquisar(filtroVencimentoAlternativo, VencimentoAlternativo.class.getName());

				if(vencimentosAlternativos != null && !vencimentosAlternativos.isEmpty()){

					vencimentoAlternativo = (VencimentoAlternativo) Util.retonarObjetoDeColecao(vencimentosAlternativos);

					Date dataVencimento = vencimentoAlternativo.getDataImplantacao();

					SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
					String dataVencimentoString = null;
					if(dataVencimento != null){
						dataVencimentoString = dataFormato.format(dataVencimento);
					}

					informarVencimentoAlternativoActionForm.setDataAlteracaoVencimento(dataVencimentoString == null ? "" : ""
									+ dataVencimentoString);

				}

				// Paramentro para diferenciar a companhia que o sistema está rodando
				if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
					// ********** Companhia - DESO ******************************
					// Monta calendário vencimentos Alternativos CASO DESO
					montaCalendarioVencimentoAlternativo(imovel, sessao);
				}
				// *******************************************************************

			}
		}

		sessao.setAttribute("imovel", imovel);
		sessao.setAttribute("vencimentoAlternativo", vencimentoAlternativo);
		return retorno;
	}

	/**
	 * Monta calendário vencimentos Alternativos CASO DESO
	 * 
	 * @author isilva
	 * @param imovel
	 * @param sessao
	 */
	private void montaCalendarioVencimentoAlternativo(Imovel imovel, HttpSession sessao){

		Collection<SetorComercialVencimento> colecaoSetorComercialVencimentos = Fachada.getInstancia()
						.pesquisarSetorComercialVencimentoPorLocalidadeSetorComercial(imovel.getLocalidade().getId(),
										imovel.getSetorComercial().getId(), ConstantesSistema.INDICADOR_USO_ATIVO);
		Collection<CalendarioForm> calendarioForms = new ArrayList<CalendarioForm>();

		int NUMERO_COLUNAS = 35;
		int QTD_DIAS_CALENDARIO = 31;

		if(colecaoSetorComercialVencimentos != null && !colecaoSetorComercialVencimentos.isEmpty()){

			for(int d = 1; d <= NUMERO_COLUNAS; d++){

				CalendarioForm calendario = new CalendarioForm();

				if(d == 1 || d == 8 || d == 15 || d == 22 || d == 29){
					calendario.setNovaLinha("S");
				}

				if(d <= QTD_DIAS_CALENDARIO){
					calendario.setDia("" + d);
				}else{
					calendario.setDia("");
				}

				for(SetorComercialVencimento setorComercialVencimento : colecaoSetorComercialVencimentos){
					if(d == setorComercialVencimento.getDiaVencimento().intValue()){
						calendario.setAtivo("S");
					}
				}

				if((d % 7) == 0){
					calendario.setQuebraLinha("S");
				}

				calendarioForms.add(calendario);
			}
		}else{
			for(int d = 1; d <= NUMERO_COLUNAS; d++){

				CalendarioForm calendario = new CalendarioForm();

				if(d == 1 || d == 8 || d == 15 || d == 22 || d == 29){
					calendario.setNovaLinha("S");
				}

				if(d <= QTD_DIAS_CALENDARIO){
					calendario.setDia("" + d);
				}else{
					calendario.setDia("");
				}

				if((d % 7) == 0){
					calendario.setQuebraLinha("S");
				}

				calendarioForms.add(calendario);
			}
		}

		sessao.setAttribute("calendarioForms", calendarioForms);
	}

}
