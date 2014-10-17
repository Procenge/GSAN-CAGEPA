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

package gcom.gui.cadastro.cliente;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.cadastro.cliente.*;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * Description of the Class
 * 
 * @author Rodrigo
 */
public class InserirClienteAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return Description of the Return Value
	 * @author eduardo henrique
	 * @date 03/06/2008
	 *       Alterações : - CPF e RG obrigatórios para Pessoa Física
	 *       - CNPJ obrigatório para Pessoa Jurídica. Se inscricaoEstadual foi informada, UF é
	 *       obrigatória
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Pega o form do cliente
		DynaValidatorForm clienteActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Código do cliente que será inserido
		Integer codigoCliente = null;

		// Pega a coleção de endereços do cliente
		Collection<ClienteEndereco> colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

		// Pega a coleção de telefones do cliente
		Collection<ClienteFone> colecaoFones = (Collection) sessao.getAttribute("colecaoClienteFone");

		// Cria o objeto do cliente para ser inserido
		String nome = (String) clienteActionForm.get("nome");
		String nomeAbreviado = (String) clienteActionForm.get("nomeAbreviado");
		String rg = (String) clienteActionForm.get("rg");
		String cpf = (String) clienteActionForm.get("cpf");
		String dataEmissao = (String) clienteActionForm.get("dataEmissao");
		String dataNascimento = (String) clienteActionForm.get("dataNascimento");
		String cnpj = (String) clienteActionForm.get("cnpj");
		String email = (String) clienteActionForm.get("email");
		String indicadorContaBraille = (String) clienteActionForm.get("indicadorContaBraille");



		String inscricaoEstadual = (String) clienteActionForm.get("inscricaoEstadual");

		OrgaoExpedidorRg orgaoExpedidorRg = null;
		if(clienteActionForm.get("idOrgaoExpedidor") != null && ((Integer) clienteActionForm.get("idOrgaoExpedidor")).intValue() > 0){
			FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
			filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, (Integer) clienteActionForm
							.get("idOrgaoExpedidor")));

			orgaoExpedidorRg = (OrgaoExpedidorRg) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroOrgaoExpedidorRg,
							OrgaoExpedidorRg.class.getName()));

			if(orgaoExpedidorRg == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Orgão Expedidor");
			}
		}

		PessoaSexo pessoaSexo = null;
		if(clienteActionForm.get("idPessoaSexo") != null && ((Integer) clienteActionForm.get("idPessoaSexo")).intValue() > 0){
			FiltroPessoaSexo filtroPessoaSexo = new FiltroPessoaSexo();
			filtroPessoaSexo.adicionarParametro(new ParametroSimples(FiltroPessoaSexo.ID, (Integer) clienteActionForm.get("idPessoaSexo")));

			pessoaSexo = (PessoaSexo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPessoaSexo, PessoaSexo.class.getName()));

			if(pessoaSexo == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Sexo");
			}
		}

		Profissao profissao = null;
		if(clienteActionForm.get("idProfissao") != null && ((Integer) clienteActionForm.get("idProfissao")).intValue() > 0){
			profissao = new Profissao();
			profissao.setId((Integer) clienteActionForm.get("idProfissao"));
		}

		UnidadeFederacao unidadeFederacao = null;
		if(clienteActionForm.get("idUnidadeFederacao") != null && ((Integer) clienteActionForm.get("idUnidadeFederacao")).intValue() > 0){
			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
			filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, (Integer) clienteActionForm
							.get("idUnidadeFederacao")));

			unidadeFederacao = (UnidadeFederacao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUnidadeFederacao,
							UnidadeFederacao.class.getName()));

			if(unidadeFederacao == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade da Federação");
			}
		}

		// Seta o clienteTipo
		ClienteTipo clienteTipo = new ClienteTipo();
		// Short tipoPessoa = (Short) clienteActionForm.get("tipoPessoa");
		// String tipoPessoaForm = tipoPessoa.toString() ;

		FiltroClienteTipo filtroClienteTipo1 = new FiltroClienteTipo();
		filtroClienteTipo1.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, Integer.valueOf(
						(Short) clienteActionForm.get("tipoPessoa")).intValue()));

		clienteTipo = (ClienteTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteTipo1, ClienteTipo.class.getName()));

		if(clienteTipo == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de Cliente");
		}

		// Faz a verificação se o tipo do cliente é pessoa jurídica e se o cnpj foi preenchido
		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, clienteTipo.getId().toString()));

		Short tipoPessoa = ((ClienteTipo) fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName()).iterator().next())
						.getIndicadorPessoaFisicaJuridica();

		Integer clienteTipoEspecial = (Integer) clienteActionForm.get("clienteTipoEspecial");

		ClienteTipoEspecial clienteTE = null;

		if(clienteTipoEspecial != null){

			String clienteTipoEspecialForm = clienteTipoEspecial.toString();

			FiltroClienteTipoEspecial filtroClienteTipoEspecial = new FiltroClienteTipoEspecial();

			filtroClienteTipoEspecial.adicionarParametro(new ParametroSimples(FiltroClienteTipoEspecial.ID, clienteTipoEspecialForm));

			Collection colClienteTE = fachada.pesquisar(filtroClienteTipoEspecial, ClienteTipoEspecial.class.getName());

			Iterator itClienteTE = colClienteTE.iterator();

			if(itClienteTE.hasNext()){
				clienteTE = (ClienteTipoEspecial) itClienteTE.next();
			}
		}

		if(tipoPessoa != null){
			if(tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){

				if(cnpj == null || cnpj.trim().equalsIgnoreCase("")){ // O CNPJ é obrigatório caso o
					// tipo de pessoa seja
					// jurídica
					throw new ActionServletException("atencao.informe_campo", null, "CNPJ");
				}else{

					// Verifica Existência de CNPJ
					existeCnpf(cnpj, fachada, clienteTipo);

				}

				if(inscricaoEstadual != null && !inscricaoEstadual.equalsIgnoreCase("") && unidadeFederacao == null){
					throw new ActionServletException("atencao.informe_campo", null, "Estado");
				}

			}else if(tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){

				if(clienteActionForm.get("idPessoaSexo").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
					// O Sexo é obrigatório caso o tipo de pessoa seja física
					throw new ActionServletException("atencao.informe_campo", null, "Sexo");
				}

				if(rg == null || rg.trim().equalsIgnoreCase("")){
					throw new ActionServletException("atencao.informe_campo", null, "RG");
				}

				if(cpf == null || cpf.trim().equalsIgnoreCase("")){
					throw new ActionServletException("atencao.informe_campo", null, "CPF");
				}else{
					// [FS0002] – Validar CPF

					// Verifica Existencia de CPF
					existeCpf(cpf, fachada);
				}

				this.validarObrigatoriedadeCampos(httpServletRequest, clienteActionForm);

			}

		}

		// Verifica se pelo menos um endereço de correspondência foi informado
		if(colecaoEnderecos == null || colecaoEnderecos.isEmpty()){
			throw new ActionServletException("atencao.informe_campo", null, "Endereço(s) do Cliente");
		}

		RamoAtividade ramoAtividade = null;
		if(clienteActionForm.get("idRamoAtividade") != null && ((Integer) clienteActionForm.get("idRamoAtividade")).intValue() > 0){
			FiltroRamoAtividade filtroRamoAtividade = new FiltroRamoAtividade();
			filtroRamoAtividade.adicionarParametro(new ParametroSimples(FiltroRamoAtividade.ID, (Integer) clienteActionForm
							.get("idRamoAtividade")));

			ramoAtividade = (RamoAtividade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroRamoAtividade, RamoAtividade.class
							.getName()));

			if(ramoAtividade == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Ramo de Atividade");
			}
		}

		Cliente clienteResponsavel = null;
		if(clienteActionForm.get("codigoClienteResponsavel") != null
						&& !((String) clienteActionForm.get("codigoClienteResponsavel")).trim().equalsIgnoreCase("")){

			// Cria o objeto do cliente responsável
			if(clienteActionForm.get("codigoClienteResponsavel") != null
							&& !((String) clienteActionForm.get("codigoClienteResponsavel")).trim().equalsIgnoreCase("")){

				// Cria o objeto do cliente responsável
				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteActionForm.get("codigoClienteResponsavel")));

				clienteResponsavel = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCliente, Cliente.class.getName()));

				if(clienteResponsavel == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cliente Responsável");
				}
			}
		}

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		try{
			Cliente cliente = new Cliente(nome, nomeAbreviado, cpf, rg,
							dataEmissao != null && !dataEmissao.trim().equalsIgnoreCase("") ? formatoData.parse(dataEmissao) : null,
							dataNascimento != null && !dataNascimento.trim().equalsIgnoreCase("") ? formatoData.parse(dataNascimento)
											: null, cnpj, email, ConstantesSistema.INDICADOR_USO_ATIVO, new Date(), orgaoExpedidorRg,
							clienteResponsavel, pessoaSexo, profissao, unidadeFederacao, clienteTipo, ramoAtividade, null,
							inscricaoEstadual, Short.valueOf(indicadorContaBraille));

			// Insere o indicador para Cobranca Acrescimos
			cliente.setIndicadorCobrancaAcrescimos(new Short("1"));

			cliente.setClienteTipoEspecial(clienteTE);

			if(clienteActionForm.get("diaVencimento") != null && !(clienteActionForm.get("diaVencimento").equals(""))){
				String diaVencimento = (String) clienteActionForm.get("diaVencimento");
				cliente.setDataVencimento(new Short(diaVencimento));
			}else{
				cliente.setDataVencimento(null);
			}

			// Nome da Mãe
			if(clienteActionForm.get("nomeMae") != null && (!(clienteActionForm.get("nomeMae").equals("")))){
				cliente.setNomeMae((String) clienteActionForm.get("nomeMae"));
			}

			ClienteResponsavel responsavelCliente = null;
			String indDadosAdicionais = (String) httpServletRequest.getParameter("indDadosAdicionais");

			if(!Util.isVazioOuBranco((String) httpServletRequest.getParameter("indDadosAdicionais"))
							&& new Boolean(httpServletRequest.getParameter("indDadosAdicionais").toString()).booleanValue()){
				// Inserir Cliente Responsavel
				responsavelCliente = new ClienteResponsavel();
				Short indMulta = !Util.isVazioOuBranco((String) httpServletRequest.getParameter("indMulta"))
								&& new Boolean(httpServletRequest.getParameter("indMulta").toString()).booleanValue() ? ConstantesSistema.SIM
								: ConstantesSistema.NAO;
				Short indJuros = !Util.isVazioOuBranco((String) httpServletRequest.getParameter("indJuros"))
								&& new Boolean(httpServletRequest.getParameter("indJuros").toString()).booleanValue() ? ConstantesSistema.SIM
								: ConstantesSistema.NAO;
				Short indCorrecao = !Util.isVazioOuBranco((String) httpServletRequest.getParameter("indCorrecao"))
								&& new Boolean(httpServletRequest.getParameter("indCorrecao").toString()).booleanValue() ? ConstantesSistema.SIM
								: ConstantesSistema.NAO;
				Short indImpostoFederal = !Util.isVazioOuBranco((String) httpServletRequest.getParameter("indImpostoFederal")) ? Short
								.valueOf(httpServletRequest.getParameter("indImpostoFederal")) : 2;
				Integer agencia = (Integer) clienteActionForm.get("agencia");
				String contaBancaria = (String) clienteActionForm.get("contaBancaria");

				responsavelCliente.setCliente(cliente);
				responsavelCliente.setIndMulta(indMulta);
				responsavelCliente.setIndJuros(indJuros);
				responsavelCliente.setIndCorrecao(indCorrecao);
				responsavelCliente.setIndImpostoFederal(indImpostoFederal);
				responsavelCliente.setIndUso(ConstantesSistema.INDICADOR_USO_ATIVO);
				responsavelCliente.setUltimaAlteracao(new Date());

				FiltroAgencia filtroAgencia = new FiltroAgencia();
				filtroAgencia.adicionarParametro(new ParametroSimples(FiltroAgencia.ID, agencia));

				Collection colecaoAgencia = fachada.pesquisarTabelaAuxiliar(filtroAgencia, Agencia.class.getName());

				if(!colecaoAgencia.isEmpty()){

					responsavelCliente.setAgencia((Agencia) colecaoAgencia.iterator().next());
					responsavelCliente.setContaBancaria(contaBancaria);
				}

			}

			Integer idRaca = (Integer) clienteActionForm.get("idRaca");

			Raca raca = null;
			if(idRaca != null && !idRaca.equals(Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO))){
				FiltroRaca filtroRaca = new FiltroRaca();
				filtroRaca.adicionarParametro(new ParametroSimples(FiltroRaca.INDICADOR_USO, ConstantesSistema.SIM));
				filtroRaca.adicionarParametro(new ParametroSimples(FiltroRaca.ID, idRaca));

				raca = (Raca) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroRaca, Raca.class.getName()));
				if(raca == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Raça");
				}
			}
			cliente.setRaca(raca);

			// Nacionalidade
			Integer idNacionalidade = (Integer) clienteActionForm.get("idNacionalidade");

			if(idNacionalidade != null && idNacionalidade != Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO)){
				FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.INDICADOR_USO, ConstantesSistema.ATIVO));
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.ID, clienteActionForm
								.get("idNacionalidade")));

				Collection<Nacionalidade> colecaoNacionalidades = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());
				if(!Util.isVazioOrNulo(colecaoNacionalidades)){
					Nacionalidade nacionalidade = (Nacionalidade) Util.retonarObjetoDeColecao(colecaoNacionalidades);
					cliente.setNacionalidade(nacionalidade);
				}

			}
			// Estado Civil
			Integer idEstadoCivil = (Integer) clienteActionForm.get("idEstadoCivil");

			EstadoCivil estadoCivil = null;
			if(idEstadoCivil != null && !idEstadoCivil.equals(Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO))){
				FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.INDICADOR_USO, ConstantesSistema.SIM));
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.ID, idEstadoCivil));

				estadoCivil = (EstadoCivil) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName()));
				if(estadoCivil == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Estado Civil");
				}
			}
			cliente.setEstadoCivil(estadoCivil);

			// Insere o cliente
			codigoCliente = Fachada.getInstancia().inserirCliente(cliente, colecaoFones, colecaoEnderecos, usuario, responsavelCliente,
							indDadosAdicionais);

			// limpa a sessão
			sessao.removeAttribute("colecaoClienteFone");
			sessao.removeAttribute("colecaoEnderecos");
			sessao.removeAttribute("foneTipos");
			sessao.removeAttribute("municipios");
			sessao.removeAttribute("colecaoResponsavelSuperiores");
			sessao.removeAttribute("InserirEnderecoActionForm");
			sessao.removeAttribute("ClienteActionForm");
			sessao.removeAttribute("tipoPesquisaRetorno");

		}catch(ParseException ex){
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, "Cliente de código " + codigoCliente + " inserido com sucesso.",
							"Inserir outro Cliente", "exibirInserirClienteAction.do",
							"exibirAtualizarClienteAction.do?idRegistroAtualizacao=" + codigoCliente, "Atualizar Cliente Inserido",
							"Atualizar Imóvel", "exibirFiltrarImovelAction.do?menu=nao");
		}

		return retorno;
	}

	private void validarObrigatoriedadeCampos(HttpServletRequest httpServletRequest, DynaValidatorForm dynaValidatorForm){

		Boolean permiteInformarCamposSemValor = this.getFachada().verificarPermissaoEspecial(
						PermissaoEspecial.PERMITIR_INFORMAR_CAMPO_OBRIGATORIO_SEM_VALOR, this.getUsuarioLogado(httpServletRequest));

		if(!permiteInformarCamposSemValor){

			Short dataNascClienteObrigatoria = null;
			Short nomeMaeClienteObrigatorio = null;

			try{

				dataNascClienteObrigatoria = Short.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_DATA_NASC_CLIENTE_OBRIGATORIO")).getValor());

				nomeMaeClienteObrigatorio = Short.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_NOME_MAE_CLIENTE_OBRIGATORIO")).getValor());

			}catch(Exception e){

				throw new ActionServletException("erro.sistema");

			}

			if(dataNascClienteObrigatoria != null && dataNascClienteObrigatoria.equals(ConstantesSistema.SIM)){

				if(StringUtils.isBlank((String) dynaValidatorForm.get("nomeMae"))){

					throw new ActionServletException("atencao.informe_campo", null, "Nome da Mãe");

				}

			}

			if(nomeMaeClienteObrigatorio != null && nomeMaeClienteObrigatorio.equals(ConstantesSistema.SIM)){

				if(StringUtils.isBlank((String) dynaValidatorForm.get("dataNascimento"))){

					throw new ActionServletException("atencao.informe_campo", null, "Data de Nascimento");

				}

			}

		}

	}

	private void existeCpf(String cpf, Fachada fachada){

		FiltroCliente filtroCpf = new FiltroCliente();
		filtroCpf.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, cpf));

		Cliente clienteCpf = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCpf, Cliente.class.getName()));

		if(clienteCpf != null){
			throw new ActionServletException("atencao.cpf.cliente.ja_cadastrado", null, clienteCpf.getId().toString());
		}

	}

	private void existeCnpf(String cnpj, Fachada fachada, ClienteTipo clienteTipo) throws ActionServletException{

		FiltroCliente filtroCnpj = new FiltroCliente();
		filtroCnpj.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, cnpj));
		boolean permiteDuplicidade = false;

		Cliente clienteCnpj = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCnpj, Cliente.class.getName()));

		try{
			if(ParametroCadastro.P_PERMITE_DUPLICIDADE_CNPJ.executar().equals(ConstantesSistema.SIM.toString())
							&& !EsferaPoder.PARTICULAR.toString().equals(clienteTipo.getEsferaPoder().getId().toString())){

				permiteDuplicidade = true;

			}
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e);
		}

		if(clienteCnpj != null && !permiteDuplicidade){

			throw new ActionServletException("atencao.cnpj.cliente.ja_cadastrado", null, clienteCnpj.getId().toString());
		}
	}
}
