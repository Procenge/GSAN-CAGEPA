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

package gcom.gui.cadastro.localidade;

import gcom.arrecadacao.Concessionaria;
import gcom.arrecadacao.FiltroConcessionaria;
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

public class InserirLocalidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirLocalidadeActionForm inserirLocalidadeActionForm = (InserirLocalidadeActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String localidadeID = inserirLocalidadeActionForm.getLocalidadeID();
		String localidadeNome = inserirLocalidadeActionForm.getLocalidadeNome();
		Collection colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");
		String telefone = inserirLocalidadeActionForm.getTelefone();
		String ramal = inserirLocalidadeActionForm.getRamal();
		String fax = inserirLocalidadeActionForm.getFax();
		String email = inserirLocalidadeActionForm.getEmail();
		String menorConsumo = inserirLocalidadeActionForm.getMenorConsumo();
		String icms = inserirLocalidadeActionForm.getIcms();

		String centroCusto = inserirLocalidadeActionForm.getCentroCusto();

		String eloID = inserirLocalidadeActionForm.getEloID();
		// String gerenciaID = inserirLocalidadeActionForm
		// .getGerenciaID();
		String idUnidadeNegocio = inserirLocalidadeActionForm.getIdUnidadeNegocio();
		String classeID = inserirLocalidadeActionForm.getClasseID();
		String porteID = inserirLocalidadeActionForm.getPorteID();
		String informatizada = inserirLocalidadeActionForm.getInformatizada();
		String gerenteLocalidade = inserirLocalidadeActionForm.getGerenteLocalidade();

		// Parâmetros DESO
		String gerenciaRegionalID = inserirLocalidadeActionForm.getGerenciaRegionalID();
		String municipioID = inserirLocalidadeActionForm.getMunicipioID();
		String localidadeCEF = inserirLocalidadeActionForm.getLocalidadeCEF();
		boolean indicadorAbastecimentoSuspenso = inserirLocalidadeActionForm.isIndicadorAbastecimentoSuspenso();
		boolean indicadorAbastecimentoMinimo = inserirLocalidadeActionForm.isIndicadorAbastecimentoMinimo();
		String ultimaQuadra = inserirLocalidadeActionForm.getUltimaQuadra();
		String localidadeContabil = inserirLocalidadeActionForm.getLocalidadeContabil();
		Integer concessionariaId = inserirLocalidadeActionForm.getConcessionariaId();

		Concessionaria concessionaria = null;
		Localidade localidadeInserir = new Localidade();
		Collection colecaoPesquisa = null;

		sessao.removeAttribute("tipoPesquisaRetorno");
		// O código da localidade é obrigatório.
		if(localidadeID == null || localidadeID.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.required", null, "Código");
		}

		// O nome da localidade é obrigatório.
		if(localidadeNome == null || localidadeNome.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.required", null, "Nome");
		}

		// O endereço da localidade é obrigatório.
		/*
		 * if (colecaoEnderecos == null || colecaoEnderecos.isEmpty()) {
		 * throw new ActionServletException(
		 * "atencao.endereco_localidade_nao_informado");
		 * } else {
		 * localidadeInserir = (Localidade) Util
		 * .retonarObjetoDeColecao(colecaoEnderecos);
		 * localidadeInserir.setId(new Integer(localidadeID));
		 * localidadeInserir.setDescricao(localidadeNome);
		 * }
		 */

		if(colecaoEnderecos != null && !colecaoEnderecos.isEmpty()){
			localidadeInserir = (Localidade) Util.retonarObjetoDeColecao(colecaoEnderecos);
		}

		localidadeInserir.setId(Util.obterInteger(localidadeID));
		localidadeInserir.setDescricao(localidadeNome);

		// O telefone é obrigatório caso o ramal tenha sido informado.
		if(ramal != null && !ramal.equalsIgnoreCase("")){
			localidadeInserir.setRamalfone(ramal);
			if(telefone == null || telefone.equalsIgnoreCase("")){
				throw new ActionServletException("atencao.telefone_localidade_nao_informado");
			}else if(telefone.length() < 7){
				throw new ActionServletException("atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Telefone");
			}
		}

		// Telefone.
		if(telefone != null && !telefone.equalsIgnoreCase("")){
			if(telefone.length() < 7){
				throw new ActionServletException("atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Telefone");
			}else{
				localidadeInserir.setFone(telefone);
			}
		}

		// Fax.
		if(fax != null && !fax.equalsIgnoreCase("")){
			if(fax.length() < 7){
				throw new ActionServletException("atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Fax");
			}else{
				localidadeInserir.setFax(fax);
			}
		}

		// E-mail.
		if(email != null && !email.equalsIgnoreCase("")){
			localidadeInserir.setEmail(email);
		}

		// Menor Consumo.
		if(menorConsumo != null && !menorConsumo.equalsIgnoreCase("")){
			localidadeInserir.setConsumoGrandeUsuario(Util.obterInteger(menorConsumo));
		}

		// ICMS
		if(icms != null && !icms.equalsIgnoreCase("")){
			localidadeInserir.setCodigoICMS(Integer.parseInt(icms));
		}

		// Centro Custo
		if(centroCusto != null && !centroCusto.equalsIgnoreCase("")){
			localidadeInserir.setCodigoCentroCusto(centroCusto);
		}

		// Unidade de Negócio
		if(idUnidadeNegocio == null || idUnidadeNegocio.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			if(SistemaParametro.INDICADOR_EMPRESA_ADA.equals(getParametroCompanhia(httpServletRequest))){
				throw new ActionServletException("atencao.required", null, "Unidade de Negócio");
			}
		}else{

			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, Integer.valueOf(idUnidadeNegocio)));
			UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUnidadeNegocio,
							UnidadeNegocio.class.getName()));

			if(unidadeNegocio == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade de Negócio");
			}

			localidadeInserir.setUnidadeNegocio(unidadeNegocio);
		}

		// Elo
		Localidade localidadeElo = new Localidade();

		if(eloID != null && !eloID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			FiltroLocalidade filtroLocalidadeElo = new FiltroLocalidade();
			filtroLocalidadeElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_UNIDADE_NEGOCIO, localidadeInserir
							.getUnidadeNegocio().getId()));
			filtroLocalidadeElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, eloID));
			filtroLocalidadeElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade - Elo
			colecaoPesquisa = fachada.pesquisar(filtroLocalidadeElo, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// O código do Elo não existe na tabela Localidade
				throw new ActionServletException("atencao.pesquisa_elo_nao_inexistente");
			}else{
				localidadeElo = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				if(localidadeElo.getLocalidade() != null
								&& localidadeElo.getId().intValue() != localidadeElo.getLocalidade().getId().intValue()){
					// A localidade escolhida não é um Elo
					throw new ActionServletException("atencao.localidade_nao_e_elo");
				}else{
					localidadeInserir.setLocalidade(localidadeElo);
				}
			}
		}else{
			localidadeInserir.setLocalidade(localidadeInserir);
		}

		// Classe
		if(classeID == null || classeID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			// Informe Classe
			throw new ActionServletException("atencao.required", null, "Classe");
		}else{

			FiltroLocalidadeClasse filtroLocalidadeClasse = new FiltroLocalidadeClasse();
			filtroLocalidadeClasse.adicionarParametro(new ParametroSimples(FiltroLocalidadeClasse.ID, Util.obterInteger(classeID)));

			LocalidadeClasse classe = (LocalidadeClasse) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidadeClasse,
							LocalidadeClasse.class.getName()));

			if(classe == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Classe");
			}

			localidadeInserir.setLocalidadeClasse(classe);
		}

		// Porte
		if(porteID == null || porteID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			// Informe Porte
			throw new ActionServletException("atencao.required", null, "Porte");
		}else{

			FiltroLocalidadePorte filtroLocalidadePorte = new FiltroLocalidadePorte();
			filtroLocalidadePorte.adicionarParametro(new ParametroSimples(FiltroLocalidadePorte.ID, Util.obterInteger(porteID)));

			LocalidadePorte porte = (LocalidadePorte) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidadePorte,
							LocalidadePorte.class.getName()));

			if(porte == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Porte");
			}

			localidadeInserir.setLocalidadePorte(porte);
		}


		String indicadorConcessionaria = null;
		try{
			indicadorConcessionaria = ParametroCadastro.P_INDICADOR_CONCESSIONARIA.executar();
		}catch(ControladorException e){
			throw new IllegalArgumentException("erro.sistema");
		}

		if((Util.isVazioOuBrancoOuZero(concessionariaId) || ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(String
						.valueOf(concessionariaId))) && !ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){
				throw new ActionServletException("atencao.required", null, "Concessionária");
		}else{
			FiltroConcessionaria filtroConcessionaria = new FiltroConcessionaria();
			filtroConcessionaria.adicionarParametro(new ParametroSimples(FiltroConcessionaria.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			if(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){
				// caso o indicador de concessionária seja -1, só irá existir apenas um com o
				// indicador VALOR_INDICADOR_EMPRESA_CONCEDENTE = 1.
				filtroConcessionaria.adicionarParametro(new ParametroSimples(FiltroConcessionaria.INDICADOR_EMPRESA_CONCEDENTE,
								FiltroConcessionaria.VALOR_INDICADOR_EMPRESA_CONCEDENTE));
			}else{
				filtroConcessionaria.adicionarParametro(new ParametroSimples(FiltroConcessionaria.ID, concessionariaId));
			}

			colecaoPesquisa = this.getFachada().pesquisar(filtroConcessionaria, Concessionaria.class.getName());
			concessionaria = (Concessionaria) Util.retonarObjetoDeColecao(colecaoPesquisa);

		}

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
				localidadeInserir.setGerenciaRegional(gerenciaRegional);
			}
		}else{
			if(gerenciaRegionalID == null || gerenciaRegionalID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				throw new ActionServletException("atencao.required", null, "Gerência Regional");
			}else{
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Util
								.obterInteger(gerenciaRegionalID)));

				GerenciaRegional gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(fachada.pesquisar(
								filtroGerenciaRegional, GerenciaRegional.class.getName()));

				if(gerenciaRegional == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Gerência Regional");
				}

				localidadeInserir.setGerenciaRegional(gerenciaRegional);
			}
		}

		if(municipioID == null || municipioID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.required", null, "Município");
		}else{

			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, Util.obterInteger(municipioID)));

			Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroMunicipio, Municipio.class.getName()));

			if(municipio == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Município");
			}

			localidadeInserir.setMunicipio(municipio);
		}

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
			if(localidadeCEF != null && !localidadeCEF.equalsIgnoreCase("")){
				localidadeInserir.setCodigoLocalidadeCEF(Util.obterInteger(localidadeCEF));
			}

			if(ultimaQuadra != null && !ultimaQuadra.equalsIgnoreCase("")){
				localidadeInserir.setNumeroUltimaQuadraCadastrada(Util.obterInteger(ultimaQuadra));
			}

			if(localidadeContabil != null && !localidadeContabil.equalsIgnoreCase("")){
				localidadeInserir.setCodigoLocalidadeContabil(Util.obterInteger(localidadeContabil));
			}

			if(indicadorAbastecimentoSuspenso){
				localidadeInserir.setIndicardoAbastecimentoSuspenso(ConstantesSistema.SIM.intValue());
			}else{
				localidadeInserir.setIndicardoAbastecimentoSuspenso(ConstantesSistema.NAO.intValue());
			}

			if(indicadorAbastecimentoMinimo){
				localidadeInserir.setIndicadorAbastecimentoMinimo(ConstantesSistema.SIM.intValue());
			}else{
				localidadeInserir.setIndicadorAbastecimentoMinimo(ConstantesSistema.NAO.intValue());
			}
		}

		// Informatizada
		if(informatizada == null || informatizada.equals("")){

			// Informatizada
			throw new ActionServletException("atencao.required", null, "Informatizada");
		}else{
			localidadeInserir.setIndicadorLocalidadeInformatizada(Util.obterShort(informatizada));
		}

		if(gerenteLocalidade != null && !gerenteLocalidade.equals("")){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Util.obterInteger(gerenteLocalidade)));

			Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroFuncionario, Funcionario.class
							.getName()));

			if(funcionario == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Gerente da Localidade");
			}

			localidadeInserir.setFuncionario(funcionario);
		}

		// Verificar existência da Localidade
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeInserir.getId()));
		colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			// Localidade já existe
			throw new ActionServletException("atencao.pesquisa_localidade_ja_cadastrada", null, localidadeID);
		}else{
			Integer idLocalidade = null;

			idLocalidade = fachada.inserirLocalidadeRetorno(localidadeInserir, usuario, concessionaria);

			montarPaginaSucesso(httpServletRequest, "Localidade de código  " + localidadeInserir.getId().intValue()
							+ " inserida com sucesso.", "Inserir outra Localidade", "exibirInserirLocalidadeAction.do?menu=sim",
							"exibirAtualizarLocalidadeAction.do?idRegistroInseridoAtualizar=" + idLocalidade,
							"Atualizar Localidade Inserida");
		}

		sessao.removeAttribute("colecaoEnderecos");

		// devolve o mapeamento de retorno
		return retorno;
	}

}
