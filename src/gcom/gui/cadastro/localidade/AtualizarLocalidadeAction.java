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

package gcom.gui.cadastro.localidade;

import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroLocalidadeClasse;
import gcom.cadastro.localidade.FiltroLocalidadePorte;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.LocalidadeClasse;
import gcom.cadastro.localidade.LocalidadePorte;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para a atualiza��o do logradouro
 * 
 * @author S�vio Luiz
 * @date 30/06/2006
 */

public class AtualizarLocalidadeAction
				extends GcomAction {

	// Obt�m a inst�ncia da fachada
	Fachada fachada = Fachada.getInstancia();

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarLocalidadeActionForm atualizarLocalidadeActionForm = (AtualizarLocalidadeActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String localidadeID = atualizarLocalidadeActionForm.getLocalidadeID();
		String localidadeNome = atualizarLocalidadeActionForm.getLocalidadeNome();
		Collection colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");
		String telefone = atualizarLocalidadeActionForm.getTelefone();
		String ramal = atualizarLocalidadeActionForm.getRamal();
		String fax = atualizarLocalidadeActionForm.getFax();
		String email = atualizarLocalidadeActionForm.getEmail();
		String icms = atualizarLocalidadeActionForm.getIcms();
		String centroCusto = atualizarLocalidadeActionForm.getCentroCusto();
		String informatizada = atualizarLocalidadeActionForm.getInformatizada();
		String gerenteLocalidade = atualizarLocalidadeActionForm.getGerenteLocalidade();

		String menorConsumo = atualizarLocalidadeActionForm.getMenorConsumo();
		String eloID = atualizarLocalidadeActionForm.getEloID();
		// String gerenciaID = atualizarLocalidadeActionForm
		// .getGerenciaID();
		String idUnidadeNegocio = atualizarLocalidadeActionForm.getIdUnidadeNegocio();
		String classeID = atualizarLocalidadeActionForm.getClasseID();
		String porteID = atualizarLocalidadeActionForm.getPorteID();
		String indicadorUso = atualizarLocalidadeActionForm.getIndicadorUso();

		// Par�metros DESO
		String gerenciaRegionalID = atualizarLocalidadeActionForm.getGerenciaRegionalID();
		String municipioID = atualizarLocalidadeActionForm.getMunicipioID();
		String localidadeCEF = atualizarLocalidadeActionForm.getLocalidadeCEF();
		boolean indicadorAbastecimentoSuspenso = atualizarLocalidadeActionForm.isIndicadorAbastecimentoSuspenso();
		boolean indicadorAbastecimentoMinimo = atualizarLocalidadeActionForm.isIndicadorAbastecimentoMinimo();
		String ultimaQuadra = atualizarLocalidadeActionForm.getUltimaQuadra();
		String localidadeContabil = atualizarLocalidadeActionForm.getLocalidadeContabil();

		Localidade localidadeAlterar = (Localidade) sessao.getAttribute("localidadeManter");
		Collection colecaoPesquisa = null;

		Integer concessionariaID = atualizarLocalidadeActionForm.getConcessionariaId();
		Integer concessionariaIdOriginal = atualizarLocalidadeActionForm.getConcessionariaIdOriginal();
		String dataInicioVigencia = atualizarLocalidadeActionForm.getDataVigenciaInicioConcessionaria();
		String dataInicioVigenciaOriginal = atualizarLocalidadeActionForm.getDataVigenciaInicioConcessionariaOriginal();

		// O id da localidade � obrigat�rio.
		if(localidadeID == null || localidadeID.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.required", null, "C�digo");
		}

		// O nome da localidade � obrigat�rio.
		if(localidadeNome == null || localidadeNome.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.required", null, "Nome");
		}

		// O endere�o da localidade � obrigat�rio.
		/*
		 * if (colecaoEnderecos == null || colecaoEnderecos.isEmpty()) {
		 * throw new ActionServletException(
		 * "atencao.endereco_localidade_nao_informado");
		 * } else {
		 * localidadeAlterar = (Localidade) Util
		 * .retonarObjetoDeColecao(colecaoEnderecos);
		 * localidadeAlterar.setId(new Integer(localidadeID));
		 * localidadeAlterar.setDescricao(localidadeNome);
		 * }
		 */

		if(colecaoEnderecos != null && !colecaoEnderecos.isEmpty()){
			localidadeAlterar = (Localidade) Util.retonarObjetoDeColecao(colecaoEnderecos);
		}else{
			localidadeAlterar.setEnderecoReferencia(null);
			localidadeAlterar.setNumeroImovel(null);
			localidadeAlterar.setComplementoEndereco(null);
			localidadeAlterar.setLogradouroCep(null);
			localidadeAlterar.setLogradouroBairro(null);
		}

		localidadeAlterar.setId(Util.obterInteger(localidadeID));
		localidadeAlterar.setDescricao(localidadeNome);

		// O telefone � obrigat�rio caso o ramal tenha sido informado.
		if(ramal != null && !ramal.equalsIgnoreCase("")){
			if(telefone == null || telefone.equalsIgnoreCase("")){
				throw new ActionServletException("atencao.telefone_localidade_nao_informado");
			}else if(telefone.length() < 7){
				throw new ActionServletException("atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Telefone");
			}
		}
		localidadeAlterar.setRamalfone(ramal);

		// Telefone.
		if(telefone != null && !telefone.equalsIgnoreCase("")){
			if(telefone.length() < 7){
				throw new ActionServletException("atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Telefone");
			}
		}
		localidadeAlterar.setFone(telefone);

		// Fax.
		if(fax != null && !fax.equalsIgnoreCase("")){
			if(fax.length() < 7){
				throw new ActionServletException("atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Fax");
			}
		}
		localidadeAlterar.setFax(fax);

		// E-mail.
		// if (email != null && !email.equalsIgnoreCase("")) {
		localidadeAlterar.setEmail(email);
		// }

		// Menor Consumo.
		if(menorConsumo != null && !menorConsumo.equalsIgnoreCase("")){
			localidadeAlterar.setConsumoGrandeUsuario(Util.obterInteger(menorConsumo));
		}else{
			localidadeAlterar.setConsumoGrandeUsuario(null);
		}

		// ICMS
		if(icms != null && !icms.equalsIgnoreCase("")){
			localidadeAlterar.setCodigoICMS(Integer.parseInt(icms));
		}

		// Centro de Custo
		if(centroCusto != null && !centroCusto.equalsIgnoreCase("")){
			localidadeAlterar.setCodigoCentroCusto(centroCusto);
		}else{
			localidadeAlterar.setCodigoCentroCusto(null);
		}

		// Elo.
		Localidade localidadeElo = new Localidade();
		if(eloID != null && !eloID.equalsIgnoreCase("") && !eloID.equalsIgnoreCase("-1")){

			FiltroLocalidade filtroLocalidadeElo = new FiltroLocalidade();

			filtroLocalidadeElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, eloID));

			if(!eloID.equalsIgnoreCase(localidadeID)){
				filtroLocalidadeElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}

			// Retorna localidade - Elo
			colecaoPesquisa = fachada.pesquisar(filtroLocalidadeElo, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// O c�digo do Elo n�o existe na tabela Localidade
				throw new ActionServletException("atencao.pesquisa_elo_nao_inexistente");
			}else{
				localidadeElo = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				if(localidadeElo.getLocalidade() != null
								&& localidadeElo.getId().intValue() != localidadeElo.getLocalidade().getId().intValue()){
					// A localidade escolhida n�o � um Elo
					throw new ActionServletException("atencao.localidade_nao_e_elo");
				}else{
					localidadeAlterar.setLocalidade(localidadeElo);
				}
			}
		}else{
			localidadeElo.setId(Util.obterInteger(localidadeID));
			localidadeAlterar.setLocalidade(localidadeElo);
		}

		// Unidade de Neg�cio
		if(idUnidadeNegocio == null || idUnidadeNegocio.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			if(SistemaParametro.INDICADOR_EMPRESA_ADA.equals(getParametroCompanhia(httpServletRequest))){
				throw new ActionServletException("atencao.required", null, "Unidade de Neg�cio");
			}else{
				localidadeAlterar.setUnidadeNegocio(null);
			}
		}else{

			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, Integer.valueOf(idUnidadeNegocio)));
			UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUnidadeNegocio,
							UnidadeNegocio.class.getName()));

			if(unidadeNegocio == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade de Neg�cio");
			}

			localidadeAlterar.setUnidadeNegocio(unidadeNegocio);
		}

		// Classe
		if(classeID == null || classeID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			// Informe Classe
			throw new ActionServletException("atencao.required", null, "Classe");
		}else{
			FiltroLocalidadeClasse filtroLocalidadeClasse = new FiltroLocalidadeClasse();
			filtroLocalidadeClasse.adicionarParametro(new ParametroSimples(FiltroLocalidadeClasse.ID, Integer.valueOf(classeID)));

			LocalidadeClasse classe = (LocalidadeClasse) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidadeClasse,
							LocalidadeClasse.class.getName()));

			if(classe == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Classe");
			}

			localidadeAlterar.setLocalidadeClasse(classe);
		}

		// Porte
		if(porteID == null || porteID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			// Informe Porte
			throw new ActionServletException("atencao.required", null, "Porte");
		}else{

			FiltroLocalidadePorte filtroLocalidadePorte = new FiltroLocalidadePorte();
			filtroLocalidadePorte.adicionarParametro(new ParametroSimples(FiltroLocalidadePorte.ID, Integer.valueOf(porteID)));

			LocalidadePorte porte = (LocalidadePorte) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidadePorte,
							LocalidadePorte.class.getName()));

			if(porte == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Porte");
			}

			localidadeAlterar.setLocalidadePorte(porte);
		}

		// Informatizada
		if(informatizada == null || informatizada.equals("")){

			// Informatizada
			throw new ActionServletException("atencao.required", null, "Informatizada");
		}else{
			localidadeAlterar.setIndicadorLocalidadeInformatizada(Util.obterShort(informatizada));
		}

		if(gerenteLocalidade != null && !gerenteLocalidade.equals("")){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Integer.valueOf(gerenteLocalidade)));

			Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroFuncionario, Funcionario.class
							.getName()));

			if(funcionario == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Gerente da Localidade");
			}

			localidadeAlterar.setFuncionario(funcionario);
		}else{
			localidadeAlterar.setFuncionario(null);
		}

		// Indicador de Uso
		localidadeAlterar.setIndicadorUso(Util.obterShort(indicadorUso));

		// Seta o Gerencia Regional de acordo com a Unidade de Negocio
		if(SistemaParametro.INDICADOR_EMPRESA_ADA.equals(getParametroCompanhia(httpServletRequest))){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, idUnidadeNegocio));
			filtroUnidadeNegocio.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeNegocio.GERENCIA);
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			UnidadeNegocio unidadeNegocio = (UnidadeNegocio) colecaoUnidadeNegocio.iterator().next();

			if(unidadeNegocio.getGerenciaRegional() != null){
				GerenciaRegional gerenciaRegional = new GerenciaRegional();
				gerenciaRegional.setId(unidadeNegocio.getGerenciaRegional().getId());
				localidadeAlterar.setGerenciaRegional(gerenciaRegional);
			}
		}else{
			if(gerenciaRegionalID == null || gerenciaRegionalID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				throw new ActionServletException("atencao.required", null, "Ger�ncia Regional");
			}else{
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Integer
								.valueOf(gerenciaRegionalID)));

				GerenciaRegional gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(fachada.pesquisar(
								filtroGerenciaRegional, GerenciaRegional.class.getName()));

				if(gerenciaRegional == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Ger�ncia Regional");
				}

				localidadeAlterar.setGerenciaRegional(gerenciaRegional);
			}
		}

		if(municipioID == null || municipioID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.required", null, "Munic�pio");
		}else{

			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, Integer.valueOf(municipioID)));

			Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroMunicipio, Municipio.class.getName()));

			if(municipio == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Munic�pio");
			}

			localidadeAlterar.setMunicipio(municipio);
		}

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
			if(localidadeCEF != null && !localidadeCEF.equalsIgnoreCase("")){
				localidadeAlterar.setCodigoLocalidadeCEF(Util.obterInteger(localidadeCEF));
			}else{
				localidadeAlterar.setCodigoLocalidadeCEF(null);
			}

			if(ultimaQuadra != null && !ultimaQuadra.equalsIgnoreCase("")){
				localidadeAlterar.setNumeroUltimaQuadraCadastrada(Util.obterInteger(ultimaQuadra));
			}else{
				localidadeAlterar.setNumeroUltimaQuadraCadastrada(null);
			}

			if(localidadeContabil != null && !localidadeContabil.equalsIgnoreCase("")){
				localidadeAlterar.setCodigoLocalidadeContabil(Util.obterInteger(localidadeContabil));
			}else{
				localidadeAlterar.setCodigoLocalidadeContabil(null);
			}

			if(indicadorAbastecimentoSuspenso){
				localidadeAlterar.setIndicardoAbastecimentoSuspenso(ConstantesSistema.SIM.intValue());
			}else{
				localidadeAlterar.setIndicardoAbastecimentoSuspenso(ConstantesSistema.NAO.intValue());
			}

			if(indicadorAbastecimentoMinimo){
				localidadeAlterar.setIndicadorAbastecimentoMinimo(ConstantesSistema.SIM.intValue());
			}else{
				localidadeAlterar.setIndicadorAbastecimentoMinimo(ConstantesSistema.NAO.intValue());
			}
		}

		// Concession�ria
		try{
			String indicadorConcessionaria = ParametroCadastro.P_INDICADOR_CONCESSIONARIA.executar();

			if(!ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){

				if(concessionariaID == null || concessionariaID.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
					throw new ActionServletException("atencao.required", null, "Concession�ria");
				}
				if(Util.isVazioOuBrancoOuZero(dataInicioVigencia)){
					throw new ActionServletException("atencao.required", null, "Data de In�cio da Vig�ncia");
				}else{
					if(concessionariaID.intValue() != concessionariaIdOriginal.intValue()){

						if(dataInicioVigencia.compareTo(dataInicioVigenciaOriginal) < 1){
							/* [FS0006] � Validar data de in�cio da vig�ncia */
							throw new ActionServletException("atencao.alterar.localidade.data.concessionaria", null,
											dataInicioVigenciaOriginal);
						}
					}else{
						// Caso seja o mesmo passa nulo para n�o executar a altera��o de
						// concession�ria.
						concessionariaID = null;
						dataInicioVigencia = null;
					}
				}
			}

		}catch(ControladorException e){
			throw new IllegalArgumentException("erro.sistema");
		}


		fachada.atualizarLocalidade(localidadeAlterar, usuario, concessionariaID, dataInicioVigencia);

		montarPaginaSucesso(httpServletRequest, "Localidade de c�digo  " + localidadeAlterar.getId() + "  atualizada com sucesso.",
						"Realizar outra Manuten��o de Localidade", "exibirFiltrarLocalidadeAction.do?desfazer=S");

		sessao.removeAttribute("localidadeManter");
		sessao.removeAttribute("colecaoLocalidade");
		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("colecaoGerenciaRegional");
		sessao.removeAttribute("colecaoClasse");
		sessao.removeAttribute("colecaoPorte");
		sessao.removeAttribute("colecaoGerenciaRegional");
		sessao.removeAttribute("colecaoMunicipio");

		sessao.removeAttribute("tipoPesquisaRetorno");

		/*
		 * //Limpando o formulario
		 * atualizarLocalidadeActionForm.setEloID("");
		 * atualizarLocalidadeActionForm.setEmail("");
		 * atualizarLocalidadeActionForm.setFax("");
		 * atualizarLocalidadeActionForm.setLocalidadeID("");
		 * atualizarLocalidadeActionForm.setLocalidadeNome("");
		 * atualizarLocalidadeActionForm.setMenorConsumo("");
		 * atualizarLocalidadeActionForm.setRamal("");
		 * atualizarLocalidadeActionForm.setTelefone("");
		 * // Campos do tipo lista no formul�rio
		 * atualizarLocalidadeActionForm.setClasseID("");
		 * atualizarLocalidadeActionForm.setGerenciaID("");
		 * atualizarLocalidadeActionForm.setPorteID("");
		 */

		// devolve o mapeamento de retorno
		return retorno;
	}
}
