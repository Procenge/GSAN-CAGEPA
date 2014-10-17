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
import gcom.atendimentopublico.ligacaoagua.FiltroCorteTipo;
import gcom.atendimentopublico.ligacaoagua.FiltroDiametroLigacao;
import gcom.atendimentopublico.ligacaoagua.FiltroMaterialLigacao;
import gcom.atendimentopublico.ligacaoagua.FiltroMotivoCorte;
import gcom.atendimentopublico.ligacaoagua.FiltroPerfilLigacao;
import gcom.atendimentopublico.ligacaoagua.FiltroRamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoagua.FiltroSupressaoTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
import gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ordemservico.FiltroSupressaoMotivo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.SupressaoMotivo;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela pre-exibição da pagina de atualizar ligação de
 * água
 * 
 * @author Rafael Pinto
 * @created 18/07/2006
 */
public class ExibirAtualizarLigacaoAguaAction
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

		HttpSession sessao = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("atualizarLigacaoAgua");

		AtualizarLigacaoAguaActionForm atualizarLigacaoAguaActionForm = (AtualizarLigacaoAguaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Pesquisar Funcionario ENTER
		if((atualizarLigacaoAguaActionForm.getIdFuncionario() != null && !atualizarLigacaoAguaActionForm.getIdFuncionario().equals(""))
						&& (atualizarLigacaoAguaActionForm.getDescricaoFuncionario() == null || atualizarLigacaoAguaActionForm
										.getDescricaoFuncionario().equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, atualizarLigacaoAguaActionForm
							.getIdFuncionario()));

			Collection colecaoFuncionario = getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				atualizarLigacaoAguaActionForm.setIdFuncionario("");
				atualizarLigacaoAguaActionForm.setDescricaoFuncionario("FUNCIONÁRIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				atualizarLigacaoAguaActionForm.setIdFuncionario(funcionario.getId().toString());
				atualizarLigacaoAguaActionForm.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Pesquisar Funcionário POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			veioEncerrarOS = Boolean.FALSE;
		}

		String idOrdemServico = atualizarLigacaoAguaActionForm.getIdOrdemServico();

		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			OrdemServico ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if(ordemServico != null){

				fachada.validarExibirLigacaoAguaImovel(ordemServico, veioEncerrarOS);

				atualizarLigacaoAguaActionForm.setVeioEncerrarOS("" + veioEncerrarOS);
				atualizarLigacaoAguaActionForm.setDataConcorrencia(new Date());

				Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();

				LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();

				sessao.setAttribute("ordemServico", ordemServico);

				// Validação do select
				boolean habilitaCorte = true;
				boolean habilitaSupressao = true;

				atualizarLigacaoAguaActionForm.setIdOrdemServico(idOrdemServico);
				atualizarLigacaoAguaActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());
				atualizarLigacaoAguaActionForm.setServicoTipo("" + ordemServico.getServicoTipo().getId());

				/*-------------- Início dados do Imóvel---------------*/

				String matriculaImovel = "" + imovel.getId();
				String inscricaoImovel = imovel.getInscricaoFormatada();

				atualizarLigacaoAguaActionForm.setMatriculaImovel(matriculaImovel);
				atualizarLigacaoAguaActionForm.setInscricaoImovel(inscricaoImovel);

				// Situação da Ligação de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				atualizarLigacaoAguaActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				// Situação da Ligação de Esgoto
				String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
				atualizarLigacaoAguaActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				/*-------------- Início dados da LigacaoAgua---------------*/

				// Data de Ligacao
				String dataLigacao = Util.formatarData(ligacaoAgua.getDataLigacao());
				atualizarLigacaoAguaActionForm.setDataLigacao(dataLigacao);

				// Diametro da Ligação
				String diametroLigacao = "" + ligacaoAgua.getLigacaoAguaDiametro().getId();
				atualizarLigacaoAguaActionForm.setDiametroLigacao(diametroLigacao);

				// Material da Ligação
				String materialLigacao = "" + ligacaoAgua.getLigacaoAguaMaterial().getId();
				atualizarLigacaoAguaActionForm.setMaterialLigacao(materialLigacao);

				// Perfil da Ligação
				String perfilLigacao = "";
				LigacaoAguaPerfil ligacaoAguaPerfil = ligacaoAgua.getLigacaoAguaPerfil();

				if(ligacaoAguaPerfil != null){
					perfilLigacao = ligacaoAguaPerfil.getId().toString();
				}

				atualizarLigacaoAguaActionForm.setPerfilLigacao(perfilLigacao);

				// Local de Instalação do Ramal
				if(ligacaoAgua.getRamalLocalInstalacao() != null){
					String ramal = "" + ligacaoAgua.getRamalLocalInstalacao().getId();
					atualizarLigacaoAguaActionForm.setRamalLocalInstalacao(ramal);
				}

				// Tipo Corte
				if(ligacaoAgua.getCorteTipo() != null){
					String tipoCorte = "" + ligacaoAgua.getCorteTipo().getId();
					atualizarLigacaoAguaActionForm.setTipoCorte(tipoCorte);
				}

				// Motivo Corte
				if(ligacaoAgua.getMotivoCorte() != null){
					String motivoCorte = "" + ligacaoAgua.getMotivoCorte().getId();
					atualizarLigacaoAguaActionForm.setMotivoCorte(motivoCorte);
				}

				// Selo Corte
				if(ligacaoAgua.getNumeroSeloCorte() != null){
					String seloCorte = "" + ligacaoAgua.getNumeroSeloCorte();
					atualizarLigacaoAguaActionForm.setNumSeloCorte(seloCorte);
				}

				// Motivo Supressao
				if(ligacaoAgua.getSupressaoMotivo() != null){
					String supressaoMotivo = "" + ligacaoAgua.getSupressaoMotivo().getId();
					atualizarLigacaoAguaActionForm.setMotivoSupressao(supressaoMotivo);
				}

				// Tipo Supressao
				if(ligacaoAgua.getSupressaoTipo() != null){
					String tipoSupressao = "" + ligacaoAgua.getSupressaoTipo().getId();
					atualizarLigacaoAguaActionForm.setTipoSupressao(tipoSupressao);
				}

				// Selo Supressao
				if(ligacaoAgua.getNumeroSeloSupressao() != null){
					String seloSupressao = "" + ligacaoAgua.getNumeroSeloSupressao();
					atualizarLigacaoAguaActionForm.setNumSeloSupressao(seloSupressao);
				}

				// Lacre
				if(ligacaoAgua.getNumeroLacre() != null){
					atualizarLigacaoAguaActionForm.setAceitaLacre("" + ConstantesSistema.INDICADOR_USO_ATIVO);
					atualizarLigacaoAguaActionForm.setNumeroLacre(ligacaoAgua.getNumeroLacre());

				}else{
					atualizarLigacaoAguaActionForm.setAceitaLacre("" + ConstantesSistema.INDICADOR_USO_DESATIVO);
					atualizarLigacaoAguaActionForm.setNumeroLacre("");
				}

				// Cliente
				this.pesquisarCliente(atualizarLigacaoAguaActionForm);

				if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()){
					habilitaCorte = false;
				}

				if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
								&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC.intValue()){

					habilitaSupressao = false;
				}

				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico();

				if(hidrometroInstalacaoHistorico != null){
					atualizarLigacaoAguaActionForm.setLeituraCorte(Util.converterObjetoParaString(hidrometroInstalacaoHistorico
									.getNumeroLeituraCorte()));

					atualizarLigacaoAguaActionForm.setLeituraSupressao(Util.converterObjetoParaString(hidrometroInstalacaoHistorico
									.getNumeroLeituraSupressao()));
				}

				httpServletRequest.setAttribute("hidrometroInstalacaoHistorico", hidrometroInstalacaoHistorico);

				atualizarLigacaoAguaActionForm.setHabilitaCorte("" + habilitaCorte);
				atualizarLigacaoAguaActionForm.setHabilitaSupressao("" + habilitaSupressao);

				// Filtro para o campo Supressao Tipo
				if(habilitaSupressao){

					FiltroSupressaoTipo filtroSupressaoTipo = new FiltroSupressaoTipo();

					String indicador = null;

					if(imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.SUPRIMIDO.intValue()){
						indicador = FiltroSupressaoTipo.INDICADOR_TOTAL;
					}else if(imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.SUPR_PARC.intValue()){
						indicador = FiltroSupressaoTipo.INDICADOR_PARCIAL;
					}

					filtroSupressaoTipo.adicionarParametro(new ParametroSimples(FiltroSupressaoTipo.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					filtroSupressaoTipo.adicionarParametro(new ParametroSimples(indicador, ConstantesSistema.SIM));
					filtroSupressaoTipo.setCampoOrderBy(FiltroSupressaoTipo.DESCRICAO);

					Collection colecaoSupressaoTipo = fachada.pesquisar(filtroSupressaoTipo, SupressaoTipo.class.getName());

					if(colecaoSupressaoTipo != null && !colecaoSupressaoTipo.isEmpty()){
						sessao.setAttribute("colecaoSupressaoTipo", colecaoSupressaoTipo);
					}else{
						throw new ActionServletException("atencao.naocadastrado", null, "Motivo Supressao");
					}
				}// habilitaSupressao

				// fim do if colecaoImovel != null
			}else{
				atualizarLigacaoAguaActionForm.setHabilitaCorte("true");
				atualizarLigacaoAguaActionForm.setHabilitaSupressao("true");

				atualizarLigacaoAguaActionForm.setNomeOrdemServico("Ordem de Serviço inexistente");
				atualizarLigacaoAguaActionForm.setIdOrdemServico("");
			}
		}

		this.consultaSelectObrigatorio(sessao);

		this.setaRequest(httpServletRequest, atualizarLigacaoAguaActionForm);

		return retorno;
	}

	private void consultaSelectObrigatorio(HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		// Filtro para o campo Diametro Ligação Água
		Collection colecaoDiametroLigacao = (Collection) sessao.getAttribute("colecaoDiametroLigacaoAgua");

		if(colecaoDiametroLigacao == null){

			FiltroDiametroLigacao filtroDiametroLigacao = new FiltroDiametroLigacao();

			filtroDiametroLigacao.adicionarParametro(new ParametroSimples(FiltroDiametroLigacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroDiametroLigacao.setCampoOrderBy(FiltroDiametroLigacao.DESCRICAO);

			colecaoDiametroLigacao = fachada.pesquisar(filtroDiametroLigacao, LigacaoAguaDiametro.class.getName());

			if(colecaoDiametroLigacao != null && !colecaoDiametroLigacao.isEmpty()){
				sessao.setAttribute("colecaoDiametroLigacaoAgua", colecaoDiametroLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Diametro da Ligação");
			}
		}

		// Filtro para o campo Material da Ligação
		Collection colecaoMaterialLigacao = (Collection) sessao.getAttribute("colecaoMaterialLigacao");

		if(colecaoMaterialLigacao == null){

			FiltroMaterialLigacao filtroMaterialLigacao = new FiltroMaterialLigacao();
			filtroMaterialLigacao.adicionarParametro(new ParametroSimples(FiltroMaterialLigacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroMaterialLigacao.setCampoOrderBy(FiltroMaterialLigacao.DESCRICAO);

			colecaoMaterialLigacao = fachada.pesquisar(filtroMaterialLigacao, LigacaoAguaMaterial.class.getName());

			if(colecaoMaterialLigacao != null && !colecaoMaterialLigacao.isEmpty()){
				sessao.setAttribute("colecaoMaterialLigacao", colecaoMaterialLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Material da Ligação");
			}
		}

		// Filtro para o campo Perfil da Ligação
		Collection colecaoPerfilLigacao = (Collection) sessao.getAttribute("colecaoPerfilLigacao");

		if(colecaoPerfilLigacao == null){

			FiltroPerfilLigacao filtroPerfilLigacao = new FiltroPerfilLigacao();
			filtroPerfilLigacao.adicionarParametro(new ParametroSimples(FiltroPerfilLigacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroPerfilLigacao.setCampoOrderBy(FiltroPerfilLigacao.DESCRICAO);

			colecaoPerfilLigacao = fachada.pesquisar(filtroPerfilLigacao, LigacaoAguaPerfil.class.getName());

			if(colecaoPerfilLigacao != null && !colecaoPerfilLigacao.isEmpty()){
				sessao.setAttribute("colecaoPerfilLigacao", colecaoPerfilLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Material da Ligação");
			}
		}

		// Filtro para o campo Ramal local instalacao
		Collection colecaoRamalLocalInstalacao = (Collection) sessao.getAttribute("colecaoRamalLocalInstalacao");

		if(colecaoRamalLocalInstalacao == null){

			FiltroRamalLocalInstalacao filtroRamalLocalInstalacao = new FiltroRamalLocalInstalacao();
			filtroRamalLocalInstalacao.adicionarParametro(new ParametroSimples(FiltroRamalLocalInstalacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroRamalLocalInstalacao.setCampoOrderBy(FiltroPerfilLigacao.DESCRICAO);

			colecaoRamalLocalInstalacao = fachada.pesquisar(filtroRamalLocalInstalacao, RamalLocalInstalacao.class.getName());

			if(colecaoRamalLocalInstalacao != null && !colecaoRamalLocalInstalacao.isEmpty()){
				sessao.setAttribute("colecaoRamalLocalInstalacao", colecaoRamalLocalInstalacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Local de Instalação do Ramal");
			}
		}

		// Filtro para o campo Motivo Corte
		Collection colecaoMotivoCorte = (Collection) sessao.getAttribute("colecaoMotivoCorte");

		if(colecaoMotivoCorte == null){

			FiltroMotivoCorte filtroMotivoCorte = new FiltroMotivoCorte();
			filtroMotivoCorte.adicionarParametro(new ParametroSimples(FiltroMotivoCorte.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroMotivoCorte.setCampoOrderBy(FiltroMotivoCorte.DESCRICAO);

			colecaoMotivoCorte = fachada.pesquisar(filtroMotivoCorte, MotivoCorte.class.getName());

			if(colecaoMotivoCorte != null && !colecaoMotivoCorte.isEmpty()){
				sessao.setAttribute("colecaoMotivoCorte", colecaoMotivoCorte);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Motivo Corte");
			}
		}

		// Filtro para o campo Tipo Corte
		Collection colecaoTipoCorte = (Collection) sessao.getAttribute("colecaoTipoCorte");

		if(colecaoTipoCorte == null){

			FiltroCorteTipo filtroCorteTipo = new FiltroCorteTipo();
			filtroCorteTipo.adicionarParametro(new ParametroSimples(FiltroCorteTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroCorteTipo.setCampoOrderBy(FiltroCorteTipo.DESCRICAO);

			colecaoTipoCorte = fachada.pesquisar(filtroCorteTipo, CorteTipo.class.getName());

			if(colecaoTipoCorte != null && !colecaoTipoCorte.isEmpty()){
				sessao.setAttribute("colecaoTipoCorte", colecaoTipoCorte);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Tipo Corte");
			}
		}

		// Filtro para o campo Supressao Motivo
		Collection colecaoSupressaoMotivo = (Collection) sessao.getAttribute("colecaoSupressaoMotivo");

		if(colecaoSupressaoMotivo == null){

			FiltroSupressaoMotivo filtroSupressaoMotivo = new FiltroSupressaoMotivo();
			filtroSupressaoMotivo.adicionarParametro(new ParametroSimples(FiltroSupressaoMotivo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroSupressaoMotivo.setCampoOrderBy(FiltroSupressaoMotivo.DESCRICAO);

			colecaoSupressaoMotivo = fachada.pesquisar(filtroSupressaoMotivo, SupressaoMotivo.class.getName());

			if(colecaoSupressaoMotivo != null && !colecaoSupressaoMotivo.isEmpty()){
				sessao.setAttribute("colecaoSupressaoMotivo", colecaoSupressaoMotivo);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Motivo Supressao");
			}
		}

	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 25/08/2006
	 */
	private void pesquisarCliente(AtualizarLigacaoAguaActionForm atualizarLigacaoAguaActionForm){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, atualizarLigacaoAguaActionForm
						.getMatriculaImovel()));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();

			Cliente cliente = clienteImovel.getCliente();

			String documento = "";

			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			atualizarLigacaoAguaActionForm.setClienteUsuario(cliente.getNome());
			atualizarLigacaoAguaActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, AtualizarLigacaoAguaActionForm atualizarLigacaoAguaActionForm){

		// Imovel
		if(atualizarLigacaoAguaActionForm.getMatriculaImovel() != null && !atualizarLigacaoAguaActionForm.getMatriculaImovel().equals("")
						&& atualizarLigacaoAguaActionForm.getInscricaoImovel() != null
						&& !atualizarLigacaoAguaActionForm.getInscricaoImovel().equals("")){

			httpServletRequest.setAttribute("numeroOsEncontrada", "true");
		}
	}

}