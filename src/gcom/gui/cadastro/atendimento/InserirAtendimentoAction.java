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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.Atendimento;
import gcom.cadastro.atendimento.AtendimentoProcedimento;
import gcom.cadastro.atendimento.bean.AtendimentoDocumentacaoInformadaHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Gicevalter Couto
 * @date 30/09/2014
 */
public class InserirAtendimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = new ActionForward();


		InserirAtendimentoActionForm form = (InserirAtendimentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		retorno.setName("atendimentoPreenchimentoOK");
		retorno.setPath(sessao.getAttribute("enderecoURLChamada").toString());
		retorno.setRedirect(true);

		sessao.setAttribute(sessao.getAttribute("enderecoURLChamada").toString(), "OK");

		Atendimento atendimento = new Atendimento();

		if(form.getIndicadorClienteImovel() != null && form.getIndicadorClienteImovel().equals("I")){
			if(form.getIdImovel() != null && !form.getIdImovel().equals("")){

				Imovel imovel = new Imovel();
				imovel.setId(Integer.valueOf(form.getIdImovel()));
				
				atendimento.setImovel(imovel);
			} else {
				throw new ActionServletException("atencao.atendimento_sem_informar_imovel");
			}
		}else if(form.getIndicadorClienteImovel() != null && form.getIndicadorClienteImovel().equals("C")){
			if(form.getIdCliente() != null && !form.getIdCliente().equals("")){

				Cliente cliente = new Cliente();
				cliente.setId(Integer.valueOf(form.getIdCliente()));
				
				atendimento.setCliente(cliente);
			} else {
				throw new ActionServletException("atencao.atendimento_sem_informar_cliente");
			}
		} else {
			throw new ActionServletException("atencao.atendimento_sem_informar_tipo");
		}

		if(form.getIdFuncionalidade() == null || form.getIdFuncionalidade().equals("")){
			throw new ActionServletException("atencao.atendimento_sem_informar_funcionalidade");
		}

		if(form.getIdAtendimentoProcedimento() == null || form.getIdAtendimentoProcedimento().equals("")){
			throw new ActionServletException("atencao.atendimento_sem_informar_atendimento_procedimento");
		}else{

			AtendimentoProcedimento atendimentoProcedimento = new AtendimentoProcedimento();
			atendimentoProcedimento.setId(Integer.valueOf(form.getIdAtendimentoProcedimento()));

			atendimento.setAtendimentoProcedimento(atendimentoProcedimento);
		}

		if(form.getDataInicioAtendimento() == null || form.getDataInicioAtendimento().equals("")){
			throw new ActionServletException("atencao.atendimento_sem_informar_data_inicio");
		}


		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try{
			atendimento.setDataInicioAtendimento(dataFormat.parse(form.getDataInicioAtendimento()));
		}catch(ParseException e){
			throw new ActionServletException("erro.sistema", e);
		}
		atendimento.setDataFimAtendimento(new Date());
		atendimento.setUsuario(usuarioLogado);


		Collection<AtendimentoDocumentacaoInformadaHelper> colecaoAtendimentoDocumentacaoInformadaHelper = null;
		if(form.getColecaoAtendimentoDocumentacao() != null){
			colecaoAtendimentoDocumentacaoInformadaHelper = (Collection<AtendimentoDocumentacaoInformadaHelper>) form
							.getColecaoAtendimentoDocumentacao();

			boolean bFlagPrenchidoDocumentoObrigatorios = true;
			for(AtendimentoDocumentacaoInformadaHelper atendimentoDocumentacaoInformadaHelper : colecaoAtendimentoDocumentacaoInformadaHelper){

				if(atendimentoDocumentacaoInformadaHelper.getIndicadorDocumentoObrigatorio().equals(ConstantesSistema.SIM)){
					if(atendimentoDocumentacaoInformadaHelper.getIdentificadorDocumentoApresentado() == null
									|| atendimentoDocumentacaoInformadaHelper.getIdentificadorDocumentoApresentado().equals("")
									|| atendimentoDocumentacaoInformadaHelper.getIndicadorDocumentoApresentado().toString()
													.equals(ConstantesSistema.NAO.toString())){
						bFlagPrenchidoDocumentoObrigatorios = false;
					}
				}
			}

			if(!bFlagPrenchidoDocumentoObrigatorios
							&& form.getIndicadorDispensadoDocumentacaoObrigatoria().equals(ConstantesSistema.NAO.toString())){
				throw new ActionServletException("atencao.atendimento_sem_preencher_documento_obrigatorios");
			}else{
				if(!bFlagPrenchidoDocumentoObrigatorios){
					sessao.setAttribute("atendimentoSemPreencherDocumentosObrigatorios", "S");
				}else{
					sessao.removeAttribute("atendimentoSemPreencherDocumentosObrigatorios");
				}
			}
		}

		fachada.inserirAtendimento(atendimento, colecaoAtendimentoDocumentacaoInformadaHelper);

		return retorno;
	}
}