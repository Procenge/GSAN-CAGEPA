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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
import gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.SupressaoMotivo;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o processamento da página de atualizar ligação de água
 * 
 * @author Rafael Pinto
 * @date 18/07/2006
 */
public class AtualizarLigacaoAguaAction
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
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarLigacaoAguaActionForm atualizarLigacaoAguaActionForm = (AtualizarLigacaoAguaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

		if(ordemServico != null){

			Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();

			LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();

			// Data de Ligacao
			String dataLigacao = atualizarLigacaoAguaActionForm.getDataLigacao();
			if(dataLigacao != null && !dataLigacao.equals("")){
				ligacaoAgua.setDataLigacao(Util.converteStringParaDate(dataLigacao));
			}

			// Diametro Ligacao
			String diametroLigacaoId = atualizarLigacaoAguaActionForm.getDiametroLigacao();
			if(diametroLigacaoId != null && !diametroLigacaoId.equals("")
							&& !diametroLigacaoId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				LigacaoAguaDiametro ligacaoAguaDiametro = new LigacaoAguaDiametro();
				ligacaoAguaDiametro.setId(new Integer(diametroLigacaoId));
				ligacaoAgua.setLigacaoAguaDiametro(ligacaoAguaDiametro);

			}

			// Material Ligacao
			String materialLigacaoId = atualizarLigacaoAguaActionForm.getMaterialLigacao();
			if(materialLigacaoId != null && !materialLigacaoId.equals("")
							&& !materialLigacaoId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				LigacaoAguaMaterial ligacaoAguaMaterial = new LigacaoAguaMaterial();
				ligacaoAguaMaterial.setId(new Integer(materialLigacaoId));
				ligacaoAgua.setLigacaoAguaMaterial(ligacaoAguaMaterial);

			}

			// Perfil Ligacao
			String perfilLigacaoId = atualizarLigacaoAguaActionForm.getPerfilLigacao();
			if(perfilLigacaoId != null && !perfilLigacaoId.equals("")
							&& !perfilLigacaoId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				LigacaoAguaPerfil ligacaoAguaPerfil = new LigacaoAguaPerfil();
				ligacaoAguaPerfil.setId(new Integer(perfilLigacaoId));
				ligacaoAgua.setLigacaoAguaPerfil(ligacaoAguaPerfil);
			}

			// Ramal Local Instalacao
			String ramalLocaolInstalacaoId = atualizarLigacaoAguaActionForm.getRamalLocalInstalacao();
			if(ramalLocaolInstalacaoId != null && !ramalLocaolInstalacaoId.equals("")
							&& !ramalLocaolInstalacaoId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				RamalLocalInstalacao ramalLocalInstalacao = new RamalLocalInstalacao();
				ramalLocalInstalacao.setId(new Integer(ramalLocaolInstalacaoId));
				ligacaoAgua.setRamalLocalInstalacao(ramalLocalInstalacao);
			}

			// Motivo do Corte
			String motivoCorteId = atualizarLigacaoAguaActionForm.getMotivoCorte();
			if(motivoCorteId != null && !motivoCorteId.equals("")
							&& !motivoCorteId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				MotivoCorte motivoCorte = new MotivoCorte();
				motivoCorte.setId(new Integer(motivoCorteId));

				ligacaoAgua.setMotivoCorte(motivoCorte);

			}else if(motivoCorteId == null || motivoCorteId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				ligacaoAgua.setMotivoCorte(null);
			}

			// Tipo de Corte
			String tipoCorteId = atualizarLigacaoAguaActionForm.getTipoCorte();
			if(tipoCorteId != null && !tipoCorteId.equals("")
							&& !tipoCorteId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				CorteTipo corteTipo = new CorteTipo();
				corteTipo.setId(new Integer(tipoCorteId));

				ligacaoAgua.setCorteTipo(corteTipo);

			}else if(tipoCorteId == null || tipoCorteId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				ligacaoAgua.setCorteTipo(null);
			}

			// Numero Selo Corte
			String numSeloCorte = atualizarLigacaoAguaActionForm.getNumSeloCorte();
			if(numSeloCorte != null && !numSeloCorte.equals("")
							&& !numSeloCorte.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				ligacaoAgua.setNumeroSeloCorte(new Integer(numSeloCorte));

			}

			// Motivo Supressao
			String motivoSupressaoId = atualizarLigacaoAguaActionForm.getMotivoSupressao();
			if(motivoSupressaoId != null && !motivoSupressaoId.equals("")
							&& !motivoSupressaoId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				SupressaoMotivo supressaoMotivo = new SupressaoMotivo();
				supressaoMotivo.setId(new Integer(motivoSupressaoId));

				ligacaoAgua.setSupressaoMotivo(supressaoMotivo);

			}else if(motivoSupressaoId == null || motivoSupressaoId.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				ligacaoAgua.setSupressaoMotivo(null);
			}

			// Tipo Supressao
			String tipoSupressaoId = atualizarLigacaoAguaActionForm.getTipoSupressao();
			if(tipoSupressaoId != null && !tipoSupressaoId.equals("")
							&& !tipoSupressaoId.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				SupressaoTipo supressaoTipo = new SupressaoTipo();
				supressaoTipo.setId(new Integer(tipoSupressaoId));

				ligacaoAgua.setSupressaoTipo(supressaoTipo);

			}else if(tipoSupressaoId == null || tipoSupressaoId.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				ligacaoAgua.setSupressaoTipo(null);
			}

			// Selo Supressao
			String numSeloSupressao = atualizarLigacaoAguaActionForm.getNumSeloSupressao();
			if(numSeloSupressao != null && !numSeloSupressao.equals("")
							&& !numSeloSupressao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				ligacaoAgua.setNumeroSeloSupressao(new Integer(numSeloSupressao));

			}else if(numSeloSupressao == null || numSeloSupressao.equals("")
							|| numSeloSupressao.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				ligacaoAgua.setNumeroSeloSupressao(null);
			}

			String numeroLacre = atualizarLigacaoAguaActionForm.getNumeroLacre();
			ligacaoAgua.setNumeroLacre(numeroLacre);

			String idFuncionario = atualizarLigacaoAguaActionForm.getIdFuncionario();

			if(idFuncionario != null && !idFuncionario.equals("")){
				Funcionario func = new Funcionario();
				func.setId(new Integer(idFuncionario));
				ligacaoAgua.setFuncionarioReligacaoAgua(func);
			}

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = ligacaoAgua.getHidrometroInstalacaoHistorico();

			// Leitura Corte
			String leituraCorte = atualizarLigacaoAguaActionForm.getLeituraCorte();
			if(leituraCorte != null && !leituraCorte.equals("")
							&& !leituraCorte.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				hidrometroInstalacaoHistorico.setNumeroLeituraCorte(new Integer(leituraCorte));

			}else if(leituraCorte == null || leituraCorte.equals("")
							|| leituraCorte.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
			}

			// Leitura Supressao
			String leituraSupressao = atualizarLigacaoAguaActionForm.getLeituraSupressao();
			if(leituraSupressao != null && !leituraSupressao.equals("")
							&& !leituraSupressao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(new Integer(leituraSupressao));

			}else if(leituraSupressao == null || leituraSupressao.equals("")
							|| leituraSupressao.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(null);
			}

			if(hidrometroInstalacaoHistorico != null){
				hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			}

			// efetuar ligação de água
			ligacaoAgua.setImovel(imovel);

			Date dataConcorrencia = atualizarLigacaoAguaActionForm.getDataConcorrencia();
			ligacaoAgua.setUltimaAlteracao(dataConcorrencia);

			fachada.atualizarLigacaoAgua(ligacaoAgua);

			sessao.removeAttribute("imovel");
			// Monta a página de sucesso
			montarPaginaSucesso(httpServletRequest, "Atualização da Ligação de Água efetuada com Sucesso",
							"Efetuar outra Atualização Ligação de Água", "exibirAtualizarLigacaoAguaAction.do?menu=sim",
							"exibirAtualizarLigacaoAguaAction.do?idOrdemServico=" + ordemServico.getId(),
							"Atualização Ligação de Água efetuada");
		}else{

			throw new ActionServletException("atencao.matricula.imovel.inexistente", null, "IMÓVEL INEXISTENTE");

		}

		return retorno;
	}
}
