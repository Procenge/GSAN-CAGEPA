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

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.*;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.hidrometro.HidrometroProtecao;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Leandro Cavalcanti
 * @created 20 de Junho de 2006
 */
public class EfetuarLigacaoAguaComInstalacaoHidrometroAction
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

		EfetuarLigacaoAguaComInstalacaoHidrometroActionForm efetuarLigacaoAguaComInstalacaoHidrometroActionForm = (EfetuarLigacaoAguaComInstalacaoHidrometroActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String ordemServicoId = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getIdOrdemServico();

		LigacaoAgua ligacaoAgua = this.setDadosLigacaoAgua(efetuarLigacaoAguaComInstalacaoHidrometroActionForm);
		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();
		hidrometroInstalacaoHistorico = this.setDadosHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico,
						efetuarLigacaoAguaComInstalacaoHidrometroActionForm);

		Imovel imovel = null;

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		if(ordemServicoId != null && !ordemServicoId.equals("")){

			OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVIÇO INEXISTENTE");
			}

			if(sessao.getAttribute("imovel") != null){
				imovel = (Imovel) sessao.getAttribute("imovel");
				imovel.setUltimaAlteracao(new Date());
				ligacaoAgua.setImovel(imovel);
				// hidrometroInstalacaoHistorico.setImovel(imovel);

				ligacaoAgua.setId(imovel.getId());
				hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);
			}

			hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

			ordemServico = this.setDadosOrdemServico(ordemServico, efetuarLigacaoAguaComInstalacaoHidrometroActionForm);

			// Guarda o usuário logado, que realizou a operação
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			String qtdParcelas = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getQuantidadeParcelas();
			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

			integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
			integracaoComercialHelper.setImovel(imovel);
			integracaoComercialHelper.setOrdemServico(ordemServico);
			integracaoComercialHelper.setQtdParcelas(qtdParcelas);
			integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				try{

					fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(qtdParcelas));
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarLigacaoAguaComInstalacaoHidrometroAction.do");
					throw ex;
				}
			}

			if(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

				try{

					fachada.efetuarLigacaoAguaComInstalacaoHidrometro(integracaoComercialHelper, usuario);
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarLigacaoAguaComInstalacaoHidrometroAction.do");
					throw ex;

				}

			}else{
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);
				sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);

				if(sessao.getAttribute("semMenu") == null){
					retorno = actionMapping.findForward("encerrarOrdemServicoAction");
				}else{
					retorno = actionMapping.findForward("encerrarOrdemServicoPopupAction");
				}
				sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
			}
			if(retorno.getName().equalsIgnoreCase("telaSucesso")){
				// Monta a página de sucesso
				montarPaginaSucesso(httpServletRequest, "Ligação de Água com Instalação de Hidrômetro efetuada com Sucesso",
								"Efetuar outra Ligação de Água com Instalação de Hidrômetro",
								"exibirEfetuarLigacaoAguaComInstalacaoHidrometroAction.do?menu=sim");
			}

			return retorno;
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Ordem de Serviço");
		}
	}

	// [SB0001] - Gerar Ligação de Água
	//
	// Método responsável por setar os dados da ligação de água
	// de acordo com os dados selecionados pelo usuário e pelas exigências do
	// caso de uso
	public LigacaoAgua setDadosLigacaoAgua(
					EfetuarLigacaoAguaComInstalacaoHidrometroActionForm efetuarLigacaoAguaComInstalacaoHidrometroActionForm){

		String diametroLigacao = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getDiametroLigacao();
		String materialLigacao = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getMaterialLigacao();
		String perfilLigacao = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getPerfilLigacao();
		String ramalLocalInstalacao = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getRamalLocalInstalacao();
		String numeroLacre = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getNumeroLacre();
		String idFuncionario = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getIdFuncionario();

		LigacaoAgua ligacaoAgua = new LigacaoAgua();

		CorteRegistroTipo corteRegistroTipo = new CorteRegistroTipo();
		corteRegistroTipo.setId(CorteRegistroTipo.LIQ_NORMAL);
		ligacaoAgua.setCorteRegistroTipo(corteRegistroTipo);

		if(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getDataLigacao() != null
						&& !efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getDataLigacao().equals("")){
			Date data = Util.converteStringParaDate(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getDataLigacao());
			ligacaoAgua.setDataLigacao(data);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, " Data da Ligação");
		}

		if(diametroLigacao != null && !diametroLigacao.equals("")
						&& !diametroLigacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			LigacaoAguaDiametro ligacaoAguaDiametro = new LigacaoAguaDiametro();
			ligacaoAguaDiametro.setId(new Integer(diametroLigacao));
			ligacaoAgua.setLigacaoAguaDiametro(ligacaoAguaDiametro);
		}else{
			throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Diametro da Ligação");
		}

		if(materialLigacao != null && !materialLigacao.equals("")
						&& !materialLigacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			LigacaoAguaMaterial ligacaoAguaMaterialMaterial = new LigacaoAguaMaterial();
			ligacaoAguaMaterialMaterial.setId(new Integer(materialLigacao));
			ligacaoAgua.setLigacaoAguaMaterial(ligacaoAguaMaterialMaterial);
		}else{
			throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Material da Ligação");
		}

		if(perfilLigacao != null && !perfilLigacao.equals("")
						&& !perfilLigacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			LigacaoAguaPerfil ligacaoAguaPerfil = new LigacaoAguaPerfil();
			ligacaoAguaPerfil.setId(new Integer(perfilLigacao));
			ligacaoAgua.setLigacaoAguaPerfil(ligacaoAguaPerfil);
		}else{
			throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Perfil da Ligação");
		}

		if(ramalLocalInstalacao != null && !ramalLocalInstalacao.equals("")
						&& !ramalLocalInstalacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			RamalLocalInstalacao ramalLocal = new RamalLocalInstalacao();
			ramalLocal.setId(new Integer(ramalLocalInstalacao));

			ligacaoAgua.setRamalLocalInstalacao(ramalLocal);
		}

		if(numeroLacre != null && !numeroLacre.equals("")){
			ligacaoAgua.setNumeroLacre(numeroLacre);
		}

		if(idFuncionario != null && !idFuncionario.equals("")){
			Funcionario func = new Funcionario();
			func.setId(new Integer(idFuncionario));
			ligacaoAgua.setFuncionarioReligacaoAgua(func);
		}

		return ligacaoAgua;
	}

	// [SB0003] - Atualizar Ordem de Serviço
	//
	// Método responsável por setar os dados da ordem de serviço
	// de acordo com as exigências do caso de uso
	public OrdemServico setDadosOrdemServico(OrdemServico ordemServico,
					EfetuarLigacaoAguaComInstalacaoHidrometroActionForm efetuarLigacaoAguaComInstalacaoHidrometroActionForm){

		String idServicoMotivoNaoCobranca = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getMotivoNaoCobranca();
		String valorPercentual = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getPercentualCobranca();

		if(ordemServico != null && efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getIdTipoDebito() != null){

			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			if(idServicoMotivoNaoCobranca != null && !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}

			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);

			if(valorPercentual != null){
				ordemServico.setPercentualCobranca(new BigDecimal(efetuarLigacaoAguaComInstalacaoHidrometroActionForm
								.getPercentualCobranca()));
			}

		}

		BigDecimal valorAtual = new BigDecimal(0);
		if(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getValorDebito() != null){
			String valorDebito = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getValorDebito().toString().replace(".", "");

			valorDebito = valorDebito.replace(",", ".");

			valorAtual = new BigDecimal(valorDebito);

			ordemServico.setValorAtual(valorAtual);
		}

		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
		ordemServico.setUltimaAlteracao(new Date());

		return ordemServico;
	}

	// [SB0004] - Gerar Histórico de Instalação do Hidrômetro
	//
	// Método responsável por setar os dados do hidrômetro instalação histórico
	// de acordo com os dados selecionados pelo usuário e pelas exigências do
	// caso de uso
	public HidrometroInstalacaoHistorico setDadosHidrometroInstalacaoHistorico(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
					EfetuarLigacaoAguaComInstalacaoHidrometroActionForm efetuarLigacaoAguaComInstalacaoHidrometroActionForm){

		Fachada fachada = Fachada.getInstancia();

		String numeroHidrometro = efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getNumeroHidrometro();

		if(numeroHidrometro != null){
			// Pesquisa o Hidrômetro
			Hidrometro hidrometro = fachada.pesquisarHidrometroPeloNumero(numeroHidrometro);

			// FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
			// filtroHidrometro.adicionarParametro(new ParametroSimples(
			// FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));
			// // Realiza a pesquisa do Hidrômetro
			// Collection colecaoHidrometro = fachada.pesquisar(filtroHidrometro,
			// Hidrometro.class.getName());
			//
			// // verificar se o número do hidrômetro não está cadastrado
			// if (colecaoHidrometro == null || colecaoHidrometro.isEmpty()) {
			// throw new ActionServletException(
			// "atencao.hidrometro_inexistente");
			// }
			// Iterator iteratorHidrometro = colecaoHidrometro.iterator();
			// Hidrometro hidrometro = (Hidrometro) iteratorHidrometro.next();

			if(hidrometro == null){

				ActionServletException ex = new ActionServletException("atencao.hidrometro_inexistente", null, hidrometro
								.getHidrometroSituacao().getDescricao());
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarRestabelecimentoLigacaoAguaComInstalacaoHidrometroAction.do");
				throw ex;

			}

			hidrometroInstalacaoHistorico.setHidrometro(hidrometro);
			hidrometroInstalacaoHistorico.setNumeroHidrometro(hidrometro.getNumero());
		}

		// Data instalação
		if(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getDataInstalacao() != null
						&& !efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getDataInstalacao().equals("")){
			LigacaoAgua ligacaoAgua = fachada.pesquisarLigacaoAgua(Integer.valueOf(efetuarLigacaoAguaComInstalacaoHidrometroActionForm
							.getMatriculaImovel()));
			Date dataInstalacao = Util.converteStringParaDate(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getDataInstalacao());
			if(ligacaoAgua != null){
				if(dataInstalacao.before(ligacaoAgua.getDataLigacao())){
					hidrometroInstalacaoHistorico.setDataInstalacao(dataInstalacao);
				}else{
					throw new ActionServletException("atencao.data_instalacao_hidrometro_anterior_ligacao_agua");
				}
			}else{
				hidrometroInstalacaoHistorico.setDataInstalacao(dataInstalacao);
			}
		}

		// Medição tipo
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		medicaoTipo.setId(MedicaoTipo.LIGACAO_AGUA);
		hidrometroInstalacaoHistorico.setMedicaoTipo(medicaoTipo);

		// hidrômetro local instalação
		HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();
		hidrometroLocalInstalacao.setId(Integer.parseInt(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getLocalInstalacao()));
		hidrometroInstalacaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);

		// proteção
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
		hidrometroProtecao.setId(Integer.parseInt(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getProtecao()));
		hidrometroInstalacaoHistorico.setHidrometroProtecao(hidrometroProtecao);

		// leitura instalação
		if(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getLeituraInstalacao() != null
						&& !efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(Integer.parseInt(efetuarLigacaoAguaComInstalacaoHidrometroActionForm
							.getLeituraInstalacao()));
		}else{
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(0);
		}

		// cavalete
		hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete(Short.parseShort(efetuarLigacaoAguaComInstalacaoHidrometroActionForm
						.getSituacaoCavalete()));

		/*
		 * Campos opcionais
		 */

		// data da retirada
		hidrometroInstalacaoHistorico.setDataRetirada(null);
		// leitura retirada
		hidrometroInstalacaoHistorico.setNumeroLeituraRetirada(null);
		// leitura corte
		hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
		// leitura supressão
		hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(null);
		// numero selo
		if(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getNumeroSelo() != null
						&& !efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getNumeroSelo().equals("")){
			hidrometroInstalacaoHistorico.setNumeroSelo(efetuarLigacaoAguaComInstalacaoHidrometroActionForm.getNumeroSelo());
		}else{
			hidrometroInstalacaoHistorico.setNumeroSelo(null);
		}

		hidrometroInstalacaoHistorico.setDataImplantacaoSistema(new Date());

		// indicador instalação substituição
		hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("1"));

		// data última alteração
		hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());

		return hidrometroInstalacaoHistorico;

	}
}
