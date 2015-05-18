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

package gcom.gui.cobranca.parcelamento;

import gcom.cobranca.parcelamento.FiltroParcelamentoTermoTestemunhas;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoTermoTestemunhas;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirParcelamentoTermoTestemunhasAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [UC0252] Consultar Parcelamentos de D�bitos
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @author Saulo Lima
	 * @date 27/08/2013
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = actionMapping.findForward("informarParcelamentoTermoTestemunhas");
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		String atualizar = httpServletRequest.getParameter("atualizar");
		String idParcelamento = (String) sessao.getAttribute("idParcelamento");
		String idImovel = (String) (sessao.getAttribute("idImovel"));
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		boolean indicadorTermoParcelamentoPreview = false;
		if(sessao.getAttribute("TermoParcelamentoPreview") != null
						&& ((String) sessao.getAttribute("TermoParcelamentoPreview")).equals("True")){
			indicadorTermoParcelamentoPreview = true;		
		}
		
		ParcelamentoTermoTestemunhasActionForm form = (ParcelamentoTermoTestemunhasActionForm) actionForm;

		if(!Util.isVazioOuBranco(atualizar) && atualizar.equals("sim")){

			// Veio do bot�o de atualizar/emitir

			// [FS0004] - Verificar CPFs testemunhas parcelamento
			if(form.getCpfTestemunha1().equals(form.getCpfTestemunha2())){
				throw new ActionServletException("atencao.parcelamento.testemunhas_mesmo_cpf");
			}

			ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = new ParcelamentoTermoTestemunhas();

			if(!indicadorTermoParcelamentoPreview){
				Parcelamento parcelamento = new Parcelamento(Integer.valueOf(idParcelamento));
				parcelamentoTermoTestemunhas.setParcelamento(parcelamento);
				
				FiltroParcelamentoTermoTestemunhas filtro = new FiltroParcelamentoTermoTestemunhas();
				filtro.adicionarParametro(new ParametroSimples(FiltroParcelamentoTermoTestemunhas.PARCELAMENTO_ID, idParcelamento));
				Collection<ParcelamentoTermoTestemunhas> colecao = fachada.pesquisar(filtro, ParcelamentoTermoTestemunhas.class.getName());

				if(!Util.isVazioOrNulo(colecao)){
					parcelamentoTermoTestemunhas.setId(((ParcelamentoTermoTestemunhas) colecao.iterator().next()).getId());
					form.setId(String.valueOf(parcelamentoTermoTestemunhas.getId()));
				}				
			}else{
				parcelamentoTermoTestemunhas.setParcelamento(null);
			}
			
			parcelamentoTermoTestemunhas.setNomeTestemunha1(form.getNomeTestemunha1());
			parcelamentoTermoTestemunhas.setNomeTestemunha2(form.getNomeTestemunha2());
			parcelamentoTermoTestemunhas.setCpfTestemunha1(form.getCpfTestemunha1());
			parcelamentoTermoTestemunhas.setCpfTestemunha2(form.getCpfTestemunha2());
			parcelamentoTermoTestemunhas.setUltimaAlteracao(GregorianCalendar.getInstance().getTime());

			if (!indicadorTermoParcelamentoPreview) {
				if(form.getId() != null && !Util.isVazioOuBranco(form.getId())){
					// [SB0007] � Atualizar Dados Testemunhas Parcelamento
					parcelamentoTermoTestemunhas.setId(Integer.valueOf(form.getId()));
					fachada.atualizar(parcelamentoTermoTestemunhas);
				}else{
					// [SB0009] � Inserir Dados Testemunhas Parcelamento
					form.setId(String.valueOf(fachada.inserir(parcelamentoTermoTestemunhas)));
					parcelamentoTermoTestemunhas.setId(Integer.valueOf(form.getId()));
				}				
			}

			sessao.setAttribute("atualizarEmitirTermoTestemunha", "true");
			sessao.setAttribute("parcelamentoTermoTestemunhas", parcelamentoTermoTestemunhas);
			retorno = actionMapping.findForward("atualizarParcelamentoTermoTestemunhasAction");

		}else{

			// Primeira vez que entra na tela

			FiltroParcelamentoTermoTestemunhas filtro = new FiltroParcelamentoTermoTestemunhas();
			filtro.adicionarParametro(new ParametroSimples(FiltroParcelamentoTermoTestemunhas.PARCELAMENTO_ID, idParcelamento));
			Collection<ParcelamentoTermoTestemunhas> colecao = fachada.pesquisar(filtro, ParcelamentoTermoTestemunhas.class.getName());

			ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = null;
			if(!Util.isVazioOrNulo(colecao)){
				parcelamentoTermoTestemunhas = colecao.iterator().next();
			}else{
				parcelamentoTermoTestemunhas = (ParcelamentoTermoTestemunhas) sessao.getAttribute("parcelamentoTermoTestemunhas");
			}

			// 1. Caso existam dados das testemunhas do parcelamento
			if(parcelamentoTermoTestemunhas != null){



				// 1.1. Caso o usu�rio tenha permiss�o especial para alterar os dados das
				// testemunhas
				if(fachada.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_DADOS_PARCELAMENTO_TERMO_TESTEMUNHAS, usuario)){

					if(parcelamentoTermoTestemunhas.getId() != null){
						form.setId(parcelamentoTermoTestemunhas.getId().toString());
					}
					
					if(parcelamentoTermoTestemunhas.getParcelamento() != null
									&& parcelamentoTermoTestemunhas.getParcelamento().getId() != null){
						form.setParcelamentoId(parcelamentoTermoTestemunhas.getParcelamento().getId().toString());	
					}

					if(idImovel != null && !idImovel.equals("")){
						form.setIdImovel(idImovel);
					}

					form.setNomeTestemunha1(parcelamentoTermoTestemunhas.getNomeTestemunha1());
					form.setNomeTestemunha2(parcelamentoTermoTestemunhas.getNomeTestemunha2());
					form.setCpfTestemunha1(parcelamentoTermoTestemunhas.getCpfTestemunha1());
					form.setCpfTestemunha2(parcelamentoTermoTestemunhas.getCpfTestemunha2());
				}else{

					// 1.2. Caso contr�rio, ou seja, o usu�rio n�o tenha permiss�o especial para
					// alterar os dados das testemunhas:

					// 1.2.1. O sistema retorna para o passo seguinte ao que chamou este subfluxo
					// para a emiss�o do termo, sem a atualiza��o dos dados das testemunhas.
					sessao.setAttribute("parcelamentoTermoTestemunhas", parcelamentoTermoTestemunhas);
					retorno = actionMapping.findForward("atualizarParcelamentoTermoTestemunhasAction");
				}
			}else{

				// 2. Caso contr�rio, ou seja, n�o existam dados das testemunhas do parcelamento:
				// 2.1. O sistema solicita os dados das testemunhas do parcelamento
				// [SB0008 � Solicitar Dados Testemunhas Parcelamento]
				if(idParcelamento != null && !idParcelamento.equals("")){
					form.setParcelamentoId(idParcelamento);
				}

				if(idImovel != null && !idImovel.equals("")){
					form.setIdImovel(idImovel);
				}

				form.setNomeTestemunha1("");
				form.setNomeTestemunha2("");
				form.setCpfTestemunha1("");
				form.setCpfTestemunha2("");
			}
		}

		return retorno;
	}
}
