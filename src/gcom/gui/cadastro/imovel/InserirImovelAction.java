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

package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.endereco.*;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;
import gcom.operacional.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class InserirImovelAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author eduardo henrique
	 * @date 16/04/2009
	 *       Alteração no carregamento das opções de Situação de Ligação de Água (Virtual)
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Parâmtro utilizado para definir se na aba de subcategoria o botão CADASTRO IMOVEL CONSUMO
		// FAIXA AREA CATG será exibido ou não.
		String indicadorImovelConsumoFaixaAreaCatg = (String) sessao.getAttribute("indicadorImovelConsumoFaixaAreaCatg");

		DynaValidatorForm inserirImovelActionForm = (DynaValidatorForm) sessao.getAttribute("InserirImovelActionForm");

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria Variaveis
		String idLocalidade = (String) inserirImovelActionForm.get("idLocalidade");
		String idSetorComercial = (String) inserirImovelActionForm.get("idSetorComercial");
		String idQuadra = (String) inserirImovelActionForm.get("idQuadra");
		String lote = (String) inserirImovelActionForm.get("lote");
		String subLote = (String) inserirImovelActionForm.get("subLote");
		String testadaLote = (String) inserirImovelActionForm.get("testadaLote");
		String idRotaForm = (String) inserirImovelActionForm.get("idRota");
		String nnSegmento = (String) inserirImovelActionForm.get("nnSegmento");
		String idDistritoOperacional = (String) inserirImovelActionForm.get("idDistritoOperacional");

		FormFile fotoFachadaForm = (FormFile) inserirImovelActionForm.get("fotoFachada");
		String areaConstruida = (String) inserirImovelActionForm.get("areaConstruida");
		String faixaAreaConstruida = (String) inserirImovelActionForm.get("faixaAreaConstruida");
		String faixaRendaFamiliar = (String) inserirImovelActionForm.get("faixaRendaFamiliar");
		String volumeReservatorioSuperior = (String) inserirImovelActionForm.get("reservatorioSuperior");
		String padraoConstrucao = (String) inserirImovelActionForm.get("padraoConstrucao");
		String esgotamento = (String) inserirImovelActionForm.get("esgotamento");
		String faixaVolumeReservatorioSuperior = (String) inserirImovelActionForm.get("faixaResevatorioSuperior");
		String volumeReservatorioInferior = (String) inserirImovelActionForm.get("reservatorioInferior");
		String faixaVolumeReservaotirio = (String) inserirImovelActionForm.get("faixaReservatorioInferior");
		String piscina = (String) inserirImovelActionForm.get("piscina");
		String jardim = (String) inserirImovelActionForm.get("jardim");
		String faixaPiscina = (String) inserirImovelActionForm.get("faixaPiscina");
		String pavimentoCalcada = (String) inserirImovelActionForm.get("pavimentoCalcada");
		String pavimentoRua = (String) inserirImovelActionForm.get("pavimentoRua");
		String fonteAbastecimento = (String) inserirImovelActionForm.get("fonteAbastecimento");
		String situacaoLigacaoAgua = (String) inserirImovelActionForm.get("situacaoLigacaoAgua");
		String situacaoLigacaoEsgoto = (String) inserirImovelActionForm.get("situacaoLigacaoEsgoto");
		String perfilImovel = (String) inserirImovelActionForm.get("perfilImovel");
		String poco = (String) inserirImovelActionForm.get("poco");
		String pontoUtilizaco = (String) inserirImovelActionForm.get("numeroPontos");
		String numeroQuarto = (String) inserirImovelActionForm.get("numeroQuarto");
		String numeroBanheiro = (String) inserirImovelActionForm.get("numeroBanheiro");
		String numeroAdulto = (String) inserirImovelActionForm.get("numeroAdulto");
		String numeroCrianca = (String) inserirImovelActionForm.get("numeroCrianca");
		String numeroMoradorTrabalhador = (String) inserirImovelActionForm.get("numeroMoradorTrabalhador");
		String numeroMoradores = (String) inserirImovelActionForm.get("numeroMoradores");
		String numeroIptu = (String) inserirImovelActionForm.get("numeroIptu");
		String numeroContratoCelpe = (String) inserirImovelActionForm.get("numeroContratoCelpe");
		String imovelContaEnvioForm = (String) inserirImovelActionForm.get("imovelContaEnvio");
		String url = verificarUrlRetorno(inserirImovelActionForm);
		ActionServletException actionServletException = null;
		String cordenadasX = (String) inserirImovelActionForm.get("cordenadasUtmX");
		if(cordenadasX != null && !cordenadasX.trim().equalsIgnoreCase("")){
			if(!GenericValidator.isDouble(cordenadasX)){
				actionServletException = new ActionServletException("atencao.valor_coordenada_invalido", null, "X");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		String cordenadasY = (String) inserirImovelActionForm.get("cordenadasUtmY");
		if(cordenadasY != null && !cordenadasY.trim().equalsIgnoreCase("")){
			if(!GenericValidator.isDouble(cordenadasY)){
				actionServletException = new ActionServletException("atencao.valor_coordenada_invalido", null, "Y");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		String extratoResponsavel = (String) inserirImovelActionForm.get("extratoResponsavel");
		// String nomeContaForm = (String) inserirImovelActionForm
		// .get("tipoOcupacao");
		String tipoDespejo = (String) inserirImovelActionForm.get("tipoDespejo");
		String idImovelPrincipal = (String) inserirImovelActionForm.get("idImovel");
		String sequencialRota = (String) inserirImovelActionForm.get("sequencialRota");
		String idFuncionario = (String) inserirImovelActionForm.get("idFuncionario");
		Boolean indicadorContratoConsumo = (Boolean) inserirImovelActionForm.get("indicadorContratoConsumo");

		Short jardimShort = null;

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){
			// ********** Companhia - ADA ******************************
			if(Util.isVazioOuBranco(jardim)){
				actionServletException = new ActionServletException("atencao.required", null, "Jardim");
				setarUrlLevantarExcecao(url, actionServletException);
			}else{
				jardimShort = Short.valueOf(jardim);
			}
			// ********** Companhia - ADA ******************************
		}

		byte[] fotoFachada = null;
		if(fotoFachadaForm != null){
			try{
				fotoFachada = fotoFachadaForm.getFileData();
			}catch(FileNotFoundException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if(idImovelPrincipal != null && !idImovelPrincipal.equals("")){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovelPrincipal));

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				actionServletException = new ActionServletException("atencao.imovel.inexistente");
				setarUrlLevantarExcecao(url, actionServletException);
			}

			filtroImovel = new FiltroImovel();
			filtroImovel.limparListaParametros();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovelPrincipal));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, Integer.valueOf(idLocalidade)));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, Integer.valueOf(idSetorComercial)));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_NUMERO, Integer.valueOf(idQuadra)));

			colecaoImovel = null;
			colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				actionServletException = new ActionServletException("atencao.imovel_principal.inexistente.quadra");
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}

		// Obtem os valores q vem na colecao de pesquisa de endereco para
		// montajem do imovel
		Collection enderecoImovel = (Collection) sessao.getAttribute("colecaoEnderecos");

		if(enderecoImovel == null){
			actionServletException = new ActionServletException("atencao.required", null, "Endereço do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}else if(enderecoImovel != null && enderecoImovel.isEmpty()){
			actionServletException = new ActionServletException("atencao.required", null, "Endereço do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		// Obtem os valores q vem na colecao de cliente para cadastrar em
		// Cliente imovel
		Collection clientes = (Collection) sessao.getAttribute("imovelClientesNovos");
		if(clientes == null){
			actionServletException = new ActionServletException("atencao.required", null, "um cliente do tipo usúario");
			setarUrlLevantarExcecao(url, actionServletException);
		}else if(clientes != null && clientes.isEmpty()){
			actionServletException = new ActionServletException("atencao.required", null, "um cliente do tipo usúario");
			setarUrlLevantarExcecao(url, actionServletException);
		}else{
			Iterator iteratorClientes = clientes.iterator();

			// verifica se entre os cliente existe o cliente usuario
			boolean existeClienteUsuario = false;
			boolean existeClienteProprietario = false;
			while(iteratorClientes.hasNext()){
				ClienteImovel clienteImovel = (ClienteImovel) iteratorClientes.next();
				if(clienteImovel.getClienteRelacaoTipo() != null){
					if(clienteImovel.getClienteRelacaoTipo().getId().toString().equals(ClienteRelacaoTipo.USUARIO.toString())){
						existeClienteUsuario = true;
					}else if(clienteImovel.getClienteRelacaoTipo().getId().toString().equals(ClienteRelacaoTipo.PROPRIETARIO.toString())){
						existeClienteProprietario = true;
					}
				}

				/*
				 * if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.
				 * INDICADOR_EMPRESA_DESO)) {
				 * if (clienteImovel.getImovelContaEnvio() != null
				 * && clienteImovel.getImovelContaEnvio().getId() ==
				 * ConstantesSistema.NUMERO_NAO_INFORMADO) {
				 * clienteImovel.setImovelContaEnvio(null);
				 * }
				 * if
				 * (ConstantesSistema.SIM.equals(clienteImovel.getIndicadorEmissaoExtratoFaturamento
				 * ())) {
				 * extratoResponsavel = ConstantesSistema.SIM.toString();
				 * } else {
				 * extratoResponsavel = ConstantesSistema.NAO.toString();
				 * }
				 * }
				 */
			}

			if(!existeClienteUsuario){
				actionServletException = new ActionServletException("atencao.required", null, "um cliente do tipo usúario");
				setarUrlLevantarExcecao(url, actionServletException);
			}

			if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
				if(!existeClienteProprietario){
					actionServletException = new ActionServletException("atencao.required", null, "um cliente do tipo proprietário");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}
		}

		// Obtem os valores que vem na coleção de subCategorias(economia)para

		// cadastrar em Imovel_Subcategoria
		Collection subcategorias = (Collection) sessao.getAttribute("colecaoImovelSubCategorias");

		if(subcategorias == null){
			actionServletException = new ActionServletException("atencao.required", null, " a Subcategoria do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}else if(subcategorias != null && subcategorias.isEmpty()){
			actionServletException = new ActionServletException("atencao.required", null, " a Subcategoria do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}
		int quantidadeEconomias = 0;
		Iterator iteratorSubcategorias = subcategorias.iterator();
		while(iteratorSubcategorias.hasNext()){
			ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) iteratorSubcategorias.next();
			quantidadeEconomias = quantidadeEconomias + imovelSubcategoria.getQuantidadeEconomias();
		}
		Collection consumoFaixaAreaCategoria = null;
		// if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){

		// cadastrar em Imovel_CONSUMO_FAIXA_AREA_CATEGORIA
		consumoFaixaAreaCategoria = (Collection) sessao.getAttribute("colecaoImovelConsumoFaixaAreaCategoria");

		if(indicadorImovelConsumoFaixaAreaCatg.equals("1")){
			if(consumoFaixaAreaCategoria == null || consumoFaixaAreaCategoria.isEmpty()){
				actionServletException = new ActionServletException("atencao.nenhum_consumo_faixa_area_categoria");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		Iterator iteratorEnderecoImovel = enderecoImovel.iterator();
		Imovel imovelEndereco = (Imovel) iteratorEnderecoImovel.next();
		Logradouro logradouro = null;

		String numeroImovel = imovelEndereco.getNumeroImovel();
		Integer cep = null;
		if(imovelEndereco.getLogradouroCep() != null && imovelEndereco.getLogradouroCep().getCep() != null){
			cep = imovelEndereco.getLogradouroCep().getCep().getCepId();
		}
		String complemento = imovelEndereco.getComplementoEndereco();

		EnderecoReferencia enderecoReferencia = null;

		if(imovelEndereco.getEnderecoReferencia() != null){
			Integer idEnderecoReferencia = imovelEndereco.getEnderecoReferencia().getId();

			if(idEnderecoReferencia != null && !idEnderecoReferencia.toString().trim().equalsIgnoreCase("")){
				FiltroEnderecoReferencia filtroEnderecoReferencia = new FiltroEnderecoReferencia();
				filtroEnderecoReferencia.adicionarParametro(new ParametroSimples(FiltroEnderecoReferencia.ID, Integer
								.valueOf(idEnderecoReferencia)));

				enderecoReferencia = (EnderecoReferencia) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroEnderecoReferencia,
								EnderecoReferencia.class.getName()));

				if(enderecoReferencia == null){
					actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Endereço de Referência");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}
		}
		Integer idLogradouro = null;

		if(imovelEndereco.getLogradouroCep() != null && imovelEndereco.getLogradouroCep().getLogradouro() != null
						&& !imovelEndereco.getLogradouroCep().getLogradouro().equals("")){
			idLogradouro = imovelEndereco.getLogradouroCep().getLogradouro().getId();
			logradouro = new Logradouro();
			logradouro.setId(idLogradouro);
		}else{

			// ALTERAÇÃO FEITTA PARA FUNCIONAMENTO DA APLICAÇÃO, APAGAR
			// A CONDIÇÃO DO "SE NÃO",
			// ALTERAÇÃO DO VALOR DO LOGRADOURO ESTA SENDO ANALIZADA POR ARYED,
			// ANA E LEO NARDO NO DIA 16/03/2006
			logradouro = new Logradouro();
			logradouro.setId(Integer.valueOf("0"));
		}

		// Cria objetos para jogalos no imovel
		// objeto Localidade
		Localidade localidade = null;

		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){
			localidade = (Localidade) sessao.getAttribute("localidade");
			// localidade = new Localidade();
			// localidade.setId(new Integer(idLocalidade));
		}

		// objeto Setor Comercial
		SetorComercial setorComercial = null;

		if(idSetorComercial != null && !idSetorComercial.trim().equalsIgnoreCase("")){
			setorComercial = (SetorComercial) sessao.getAttribute("setorComercial");
			// setorComercial = new SetorComercial();
			// setorComercial.setId(new Integer(idSetorComercial));
		}

		// objeto Quadra
		Quadra quadra = null;

		if(idQuadra != null && !idQuadra.trim().equalsIgnoreCase("")){
			quadra = (Quadra) sessao.getAttribute("quadra");
			FiltroQuadra filtroQuadra = new FiltroQuadra();
			// filtroQuadra.adicionarParametro(new
			// ParametroSimples(FiltroQuadra.ID_LOCALIDADE,localidade.getId()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, quadra.getId()));
			Collection quadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());
			if(quadras != null && !quadras.isEmpty()){
				quadra = ((Quadra) ((List) quadras).get(0));
			}
			// quadra = new Quadra();
			// quadra.setId(new Integer(idQuadra));
		}

		// objeto Imovel(Principal)
		Imovel imovelPrincipal = null;

		if(idImovelPrincipal != null && !idImovelPrincipal.trim().equalsIgnoreCase("")){
			imovelPrincipal = new Imovel();
			imovelPrincipal.setId(Integer.valueOf(idImovelPrincipal));
		}

		// objeto faixa area construida
		AreaConstruidaFaixa areaConstruidaFaixa = null;

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){
			// ********** Companhia - ADA ******************************

			if(faixaAreaConstruida != null && !faixaAreaConstruida.trim().equalsIgnoreCase("")
							&& !faixaAreaConstruida.trim().equalsIgnoreCase("-1")){
				areaConstruidaFaixa = new AreaConstruidaFaixa();
				areaConstruidaFaixa.setId(Integer.valueOf(faixaAreaConstruida));
			}
			if((areaConstruida == null || areaConstruida.trim().equalsIgnoreCase(""))
							&& (faixaAreaConstruida == null || faixaAreaConstruida.trim().equalsIgnoreCase("") || faixaAreaConstruida
											.trim().equalsIgnoreCase("-1"))){
				actionServletException = new ActionServletException("atencao.required", null, "Área Construída");
				setarUrlLevantarExcecao(url, actionServletException);
			}
			// ********** Companhia - ADA ******************************
		}

		// objeto faixa renda familiar
		RendaFamiliarFaixa rendaFamiliarFaixa = null;

		if(faixaRendaFamiliar != null && !faixaRendaFamiliar.trim().equalsIgnoreCase("")
						&& !faixaRendaFamiliar.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			rendaFamiliarFaixa = new RendaFamiliarFaixa();
			rendaFamiliarFaixa.setId(Integer.valueOf(faixaRendaFamiliar));
		}

		// objeto Pavimento calçada
		PavimentoCalcada pavimentoCalcadaObj = null;

		if(pavimentoCalcada != null && !pavimentoCalcada.trim().equalsIgnoreCase("")
						&& !pavimentoCalcada.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroPavimentoCalcada filtroPavimentoCalcada = new FiltroPavimentoCalcada();
			filtroPavimentoCalcada.adicionarParametro(new ParametroSimples(FiltroPavimentoCalcada.ID, Integer.valueOf(pavimentoCalcada)));

			pavimentoCalcadaObj = (PavimentoCalcada) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPavimentoCalcada,
							PavimentoCalcada.class.getName()));

			if(pavimentoCalcadaObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Pavimento Calçada");
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}else{
			// QDO O COMBOBOX FOR PARA SER O PRIMEIRO VAZIO, NÃO INFORMAR
			// MAIS ESSA MENSAGEM E SIM DE QUE É OBRIGADO PARA INFORMAR
			actionServletException = new ActionServletException("atencao.required", null, "Pavimento Calçada");
			setarUrlLevantarExcecao(url, actionServletException);
			// throw new
			// ActionServletException("atencao.informe.caracteristica");
		}

		// objeto Pavimento Rua
		PavimentoRua pavimentoRuaObj = null;

		if(pavimentoRua != null && !pavimentoRua.trim().equalsIgnoreCase("")
						&& !pavimentoRua.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){

			FiltroPavimentoRua filtroPavimentoRua = new FiltroPavimentoRua();
			filtroPavimentoRua.adicionarParametro(new ParametroSimples(FiltroPavimentoRua.ID, Integer.valueOf(pavimentoRua)));

			pavimentoRuaObj = (PavimentoRua) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroPavimentoRua, PavimentoRua.class.getName()));

			if(pavimentoRuaObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Pavimento Rua");
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}else{
			actionServletException = new ActionServletException("atencao.required", null, "Pavimento Rua");
			setarUrlLevantarExcecao(url, actionServletException);

		}

		// objeto Revservatorio Volume Faixa Superior
		ReservatorioVolumeFaixa reservatorioVolumeFaixaSup = null;

		if(faixaVolumeReservatorioSuperior != null && !faixaVolumeReservatorioSuperior.trim().equalsIgnoreCase("")
						&& !faixaVolumeReservatorioSuperior.trim().equalsIgnoreCase("-1")){
			FiltroReservatorioVolumeFaixa filtroReservatorioVolumeFaixa = new FiltroReservatorioVolumeFaixa();
			filtroReservatorioVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroReservatorioVolumeFaixa.ID, Integer
							.valueOf(faixaVolumeReservatorioSuperior)));

			reservatorioVolumeFaixaSup = (ReservatorioVolumeFaixa) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName()));

			if(reservatorioVolumeFaixaSup == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Volume Reservatório Superior");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto Reservatorio Padrao de construcao
		PadraoConstrucao padraoConstrucaoObj = null;

		if(padraoConstrucao != null && !padraoConstrucao.trim().equalsIgnoreCase("") && !padraoConstrucao.trim().equalsIgnoreCase("-1")){
			FiltroPadraoConstrucao filtroPadraoConstrucao = new FiltroPadraoConstrucao();
			filtroPadraoConstrucao.adicionarParametro(new ParametroSimples(FiltroPadraoConstrucao.ID, Integer.valueOf(padraoConstrucao)));

			padraoConstrucaoObj = (PadraoConstrucao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPadraoConstrucao,
							PadraoConstrucao.class.getName()));

			if(padraoConstrucaoObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Padrão de Construção");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto Reservatorio Esgotamento
		Esgotamento esgotamentoObj = null;

		if(esgotamento != null && !esgotamento.trim().equalsIgnoreCase("") && !esgotamento.trim().equalsIgnoreCase("-1")){
			FiltroEsgotamento filtroEsgotamento = new FiltroEsgotamento();
			filtroEsgotamento.adicionarParametro(new ParametroSimples(FiltroEsgotamento.ID, Integer.valueOf(esgotamento)));

			esgotamentoObj = (Esgotamento) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroEsgotamento, Esgotamento.class.getName()));

			if(esgotamentoObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Esgotamento");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto Reservatorio Volume Faixa Inferior
		ReservatorioVolumeFaixa reservatorioVolumeFaixaInf = null;

		if(faixaVolumeReservaotirio != null && !faixaVolumeReservaotirio.trim().equalsIgnoreCase("")
						&& !faixaVolumeReservaotirio.trim().equalsIgnoreCase("-1")){
			FiltroReservatorioVolumeFaixa filtroReservatorioVolumeFaixa = new FiltroReservatorioVolumeFaixa();
			filtroReservatorioVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroReservatorioVolumeFaixa.ID, Integer
							.valueOf(faixaVolumeReservaotirio)));

			reservatorioVolumeFaixaInf = (ReservatorioVolumeFaixa) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName()));

			if(reservatorioVolumeFaixaInf == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Volume Reservatório Inferior");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto Fonte Abastecimento
		FonteAbastecimento fonteAbastecimentoObj = null;

		if(fonteAbastecimento != null && !fonteAbastecimento.trim().equalsIgnoreCase("")
						&& !fonteAbastecimento.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){

			FiltroFonteAbastecimento filtroFonteAbastecimento = new FiltroFonteAbastecimento();
			filtroFonteAbastecimento.adicionarParametro(new ParametroSimples(FiltroFonteAbastecimento.ID, Integer
							.valueOf(fonteAbastecimento)));

			fonteAbastecimentoObj = (FonteAbastecimento) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroFonteAbastecimento,
							FonteAbastecimento.class.getName()));

			if(fonteAbastecimentoObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Fonde de Abastecimento");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}else{
			if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_ADA.toString())){
				actionServletException = new ActionServletException("atencao.required", null, "Fonte de Abastecimento");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto Despejo
		Despejo despejo = null;

		if(tipoDespejo != null && !tipoDespejo.trim().equalsIgnoreCase("") && !tipoDespejo.trim().equalsIgnoreCase("-1")){
			FiltroDespejo filtroDespejo = new FiltroDespejo();
			filtroDespejo.adicionarParametro(new ParametroSimples(FiltroDespejo.ID, Integer.valueOf(tipoDespejo)));

			despejo = (Despejo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroDespejo, Despejo.class.getName()));

			if(despejo == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de Despejo");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// Setor de Abastecimento
		SetorAbastecimento setorAbastecimento = null;
		String idSetorAbastecimento = (String) inserirImovelActionForm.get("setorAbastecimento");

		if(!Util.isVazioOuBranco(idSetorAbastecimento) && !idSetorAbastecimento.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroSetorAbastecimento filtroSetorAbastecimento = new FiltroSetorAbastecimento();
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ID, idSetorAbastecimento));
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			setorAbastecimento = (SetorAbastecimento) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSetorAbastecimento,
							SetorAbastecimento.class.getName()));

			if(Util.isVazioOuBranco(setorAbastecimento)){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Setor de Abastecimento");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// Sub-bacia
		SubBacia subBacia = null;
		String idSubBacia = (String) inserirImovelActionForm.get("subBacia");

		if(!Util.isVazioOuBranco(idSubBacia) && !idSubBacia.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroSubBacia filtroSubBacia = new FiltroSubBacia();
			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.ID, idSubBacia));
			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			subBacia = (SubBacia) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSubBacia, SubBacia.class.getName()));

			if(Util.isVazioOuBranco(subBacia)){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Sub-bacia");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto cep
		Cep cepObj = null;

		if(cep != null){
			FiltroCep filtroCep = new FiltroCep();
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, Integer.valueOf(cep)));

			cepObj = (Cep) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCep, Cep.class.getName()));

			if(cepObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "CEP");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto DistritoOperacional
		DistritoOperacional distritoOperacional = null;
		if(!Util.isVazioOuBranco(idDistritoOperacional)){
			distritoOperacional = (DistritoOperacional) sessao.getAttribute("distritoOperacional");
		}

		// objeto Situacao ligacao Agua
		LigacaoAguaSituacao ligacaoAguaSituacao = null;

		if(situacaoLigacaoAgua != null && !situacaoLigacaoAgua.trim().equalsIgnoreCase("")
						&& !situacaoLigacaoAgua.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, Integer
							.valueOf(situacaoLigacaoAgua)));

			ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLigacaoAguaSituacao,
							LigacaoAguaSituacao.class.getName()));

			if(ligacaoAguaSituacao == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Ligação Esgoto");
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}else{
			actionServletException = new ActionServletException("atencao.required", null, "Situação Ligação Água");
			setarUrlLevantarExcecao(url, actionServletException);

		}

		// objeto Situacao ligcaoa esgoto
		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

		if(situacaoLigacaoEsgoto != null && !situacaoLigacaoEsgoto.trim().equalsIgnoreCase("")
						&& !situacaoLigacaoEsgoto.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, Integer
							.valueOf(situacaoLigacaoEsgoto)));

			ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLigacaoEsgotoSituacao,
							LigacaoEsgotoSituacao.class.getName()));

			if(ligacaoEsgotoSituacao == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Ligação Esgoto");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}else{
			actionServletException = new ActionServletException("atencao.required", null, "Situação Ligação Esgoto");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		// objeto Perfil Imovel
		ImovelPerfil imovelPerfil = null;

		if(perfilImovel != null && !perfilImovel.trim().equalsIgnoreCase("")
						&& !perfilImovel.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, Integer.valueOf(perfilImovel)));

			imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName()));

			if(imovelPerfil == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Perfil do Imóvel");
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}else{
			actionServletException = new ActionServletException("atencao.required", null, "Perfil do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);

		}

		// objeto PocoTipo
		PocoTipo pocoTipo = null;

		if(poco != null && !poco.trim().equalsIgnoreCase("") && !poco.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroPocoTipo filtroPocoTipo = new FiltroPocoTipo();
			filtroPocoTipo.adicionarParametro(new ParametroSimples(FiltroPocoTipo.ID, Integer.valueOf(poco)));

			pocoTipo = (PocoTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPocoTipo, PocoTipo.class.getName()));

			if(pocoTipo == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Poço");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}else{
			// throw new ActionServletException(
			// "atencao.required", null, "Poço");

		}

		// objeto PiscinaVolumeFaixa
		PiscinaVolumeFaixa piscinaVolumeFaixa = null;

		if(faixaPiscina != null && !faixaPiscina.trim().equalsIgnoreCase("") && !faixaPiscina.trim().equalsIgnoreCase("-1")){
			FiltroPiscinaVolumeFaixa filtroPiscinaVolumeFaixa = new FiltroPiscinaVolumeFaixa();
			filtroPiscinaVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroPiscinaVolumeFaixa.ID, Integer.valueOf(faixaPiscina)));

			piscinaVolumeFaixa = (PiscinaVolumeFaixa) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPiscinaVolumeFaixa,
							PiscinaVolumeFaixa.class.getName()));

			if(piscinaVolumeFaixa == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Volume Piscina");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		// objeto tipo ocupacao
		/*
		 * NomeConta nomeConta = null;
		 * if (nomeContaForm != null &&
		 * !nomeContaForm.trim().equalsIgnoreCase("") &&
		 * !nomeContaForm.trim().equalsIgnoreCase("-1") ) { nomeConta = new
		 * NomeConta(); nomeConta.setId(new Integer(nomeContaForm)); }
		 */
		ImovelContaEnvio imovelContaEnvio = null;

		if(imovelContaEnvioForm != null && !imovelContaEnvioForm.trim().equalsIgnoreCase("")
						&& !imovelContaEnvioForm.trim().equalsIgnoreCase("-1")){
			FiltroImovelContaEnvio filtroImovelContaEnvio = new FiltroImovelContaEnvio();
			filtroImovelContaEnvio
							.adicionarParametro(new ParametroSimples(FiltroImovelContaEnvio.ID, Integer.valueOf(imovelContaEnvioForm)));

			imovelContaEnvio = (ImovelContaEnvio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroImovelContaEnvio,
							ImovelContaEnvio.class.getName()));

			if(imovelContaEnvio == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Imóvel Conta Envio");
				setarUrlLevantarExcecao(url, actionServletException);
			}

			boolean achouClienteDoMesmoTipo = false;
			for(Object objeto : clientes){
				ClienteImovel clienteImovel = (ClienteImovel) objeto;
				if(imovelContaEnvio.getId().equals(ImovelContaEnvio.ENVIAR_CLIENTE_RESPONSAVEL)
								&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(imovelContaEnvio.getId().equals(ImovelContaEnvio.ENVIAR_PARA_CLIENTE_PROPRIETARIO)
								&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.PROPRIETARIO)){
					achouClienteDoMesmoTipo = true;
				}else if(imovelContaEnvio.getId().equals(ImovelContaEnvio.ENVIAR_PARA_CLIENTE_USUARIO)
								&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.USUARIO)){
					achouClienteDoMesmoTipo = true;
				}else if(imovelContaEnvio.getId().equals(ImovelContaEnvio.NAO_PAGAVEL_IMOVEL_PAGAVEL_RESPONSAVEL)
								&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(imovelContaEnvio.getId().equals(ImovelContaEnvio.PAGAVEL_PARA_IMOVEL_E_NAO_PAGAVEL_PARA_RESPOSAVEL)
								&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(imovelContaEnvio.getId().equals(ImovelContaEnvio.PAGAVEL_PARA_IMOVEL_E_PAGAVEL_PARA_RESPONSAVEL)
								&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(imovelContaEnvio.getId().equals(ImovelContaEnvio.ENVIAR_IMOVEL)){
					achouClienteDoMesmoTipo = true;
				}
			}
			if(!achouClienteDoMesmoTipo){
				actionServletException = new ActionServletException("atencao.imovel.endereco.tipo_cliente");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}else{
			actionServletException = new ActionServletException("atencao.informe_campo", "Envio da Conta");
			setarUrlLevantarExcecao(url, actionServletException);
		}
		if(extratoResponsavel != null && !extratoResponsavel.trim().equalsIgnoreCase("")){
			if(extratoResponsavel.equals("1")){
				boolean achouResponsavel = false;
				for(Object objeto : clientes){
					ClienteImovel clienteImovel = (ClienteImovel) objeto;
					if(clienteImovel.getClienteRelacaoTipo().equals(ClienteRelacaoTipo.RESPONSAVEL)){
						achouResponsavel = true;
					}
				}
				if(!achouResponsavel){
					actionServletException = new ActionServletException("atencao.imovel.endereco.envio.clienteResponsavel");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}
		}

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, idLocalidade));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, quadra.getId()));
		Collection quadrasNaBase = fachada.pesquisar(filtroQuadra, Quadra.class.getName());
		// VERIFICAR A SITUAÇÃO DE AGUA E ESGOTO PARA A QUADRA, CASO ELA TENHA
		// SITDO ALTERADO AO MESMO TEMPO EM QUE O IMOVEL ESTEJA SENDO ATERADO
		if(quadrasNaBase != null && !quadrasNaBase.isEmpty()){

			Quadra quadraNaBase = ((Quadra) ((List) quadrasNaBase).get(0));
			// SITUAÇÃO DE LIGAÇÃO DE AGUA
			if(quadraNaBase.getIndicadorRedeAgua() != null && quadraNaBase.getIndicadorRedeAgua().equals(Quadra.SEM_REDE)){
				if(!(ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.POTENCIAL.toString()))
								&& !(ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.VIRTUAL.toString()))){
					actionServletException = new ActionServletException("atencao.imovel.ligacao_agua.incompativel");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}
			if(quadraNaBase.getIndicadorRedeAgua() != null && quadraNaBase.getIndicadorRedeAgua().equals(Quadra.COM_REDE)){
				if(!(ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.FACTIVEL.toString()))
								&& !(ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.VIRTUAL.toString()))){
					actionServletException = new ActionServletException("atencao.imovel.ligacao_agua.incompativel");
					setarUrlLevantarExcecao(url, actionServletException);
				}

			}
			if(quadraNaBase.getIndicadorRedeAgua() != null && quadraNaBase.getIndicadorRedeAgua().equals(Quadra.REDE_PARCIAL)){
				if(!(((ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.POTENCIAL.toString()))) && !(ligacaoAguaSituacao
								.getId().toString().equals(LigacaoAguaSituacao.FACTIVEL.toString())))
								&& !(ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.VIRTUAL.toString()))){
					ActionServletException ex = new ActionServletException("atencao.imovel.ligacao_agua.incompativel");
					setarUrlLevantarExcecao(url, ex);
				}
			}

			// SITUAÇÃO DE LIGAÇÃO ESGOTO
			try{
				// 2.25.1.Caso o parâmetro P_CRITICAR_ESGOTO_QUADRA tenha valor 1.
				if(ParametroAtendimentoPublico.P_CRITICAR_ESGOTO_QUADRA.executar().equals(ConstantesSistema.SIM.toString())){

					if(quadraNaBase.getIndicadorRedeEsgoto() != null && quadraNaBase.getIndicadorRedeEsgoto().equals(Quadra.SEM_REDE)){
						if(!(ligacaoEsgotoSituacao.getId().toString().equals(LigacaoEsgotoSituacao.POTENCIAL.toString()))){
							actionServletException = new ActionServletException("atencao.imovel.ligacao_esgoto.incompativel");
							setarUrlLevantarExcecao(url, actionServletException);
						}
					}

					if(quadraNaBase.getIndicadorRedeEsgoto() != null && quadraNaBase.getIndicadorRedeEsgoto().equals(Quadra.COM_REDE)){
						if(!(ligacaoEsgotoSituacao.getId().toString().equals(LigacaoEsgotoSituacao.FACTIVEL.toString()))){
							actionServletException = new ActionServletException("atencao.imovel.ligacao_esgoto.incompativel");
							setarUrlLevantarExcecao(url, actionServletException);
						}

					}

					if(quadraNaBase.getIndicadorRedeEsgoto() != null && quadraNaBase.getIndicadorRedeEsgoto().equals(Quadra.REDE_PARCIAL)){
						if(!(((ligacaoEsgotoSituacao.getId().toString().equals(LigacaoEsgotoSituacao.POTENCIAL.toString()))) || ((ligacaoEsgotoSituacao
										.getId().toString().equals(LigacaoEsgotoSituacao.FACTIVEL.toString()))))){
							actionServletException = new ActionServletException("atencao.imovel.ligacao_esgoto.incompativel");
							setarUrlLevantarExcecao(url, actionServletException);
						}
					}
				}
			}catch(ControladorException e){
				e.printStackTrace();
			}
		}

		LogradouroCep logradouroCep = fachada.pesquisarAssociacaoLogradouroCep(cepObj.getCepId(), logradouro.getId());

		Imovel imovel = new Imovel(lote != null && !lote.trim().equalsIgnoreCase("") ? (Short.valueOf(lote)).shortValue() : 0,
						subLote != null && !subLote.trim().equalsIgnoreCase("") ? (Short.valueOf(subLote)).shortValue() : 0,
						testadaLote != null && !testadaLote.trim().equals("") ? (Short.valueOf(testadaLote)).shortValue() : null,
						numeroImovel, complemento, fotoFachada,
						areaConstruida != null && !areaConstruida.trim().equalsIgnoreCase("") ? Util
										.formatarMoedaRealparaBigDecimal(areaConstruida) : null, null, volumeReservatorioSuperior != null
										&& !volumeReservatorioSuperior.trim().equalsIgnoreCase("") ? Util
										.formatarMoedaRealparaBigDecimal(volumeReservatorioSuperior) : null,
						volumeReservatorioInferior != null && !volumeReservatorioInferior.trim().equalsIgnoreCase("") ? Util
										.formatarMoedaRealparaBigDecimal(volumeReservatorioInferior) : null, piscina != null
										&& !piscina.trim().equalsIgnoreCase("") ? Util.formatarMoedaRealparaBigDecimal(piscina) : null,
						null, null, pontoUtilizaco != null && !pontoUtilizaco.trim().equalsIgnoreCase("") ? (Short.valueOf(pontoUtilizaco))
										.shortValue() : null, numeroQuarto != null && !numeroQuarto.trim().equalsIgnoreCase("") ? (Short
										.valueOf(numeroQuarto)).shortValue() : null, numeroBanheiro != null
										&& !numeroBanheiro.trim().equalsIgnoreCase("") ? (Short.valueOf(numeroBanheiro)).shortValue()
										: null, numeroAdulto != null && !numeroAdulto.trim().equalsIgnoreCase("") ? (Short
										.valueOf(numeroAdulto)).shortValue() : null, numeroCrianca != null
										&& !numeroCrianca.trim().equalsIgnoreCase("") ? (Short.valueOf(numeroCrianca)).shortValue() : null,
						numeroMoradorTrabalhador != null && !numeroMoradorTrabalhador.trim().equalsIgnoreCase("") ? (Short
										.valueOf(numeroMoradorTrabalhador)).shortValue() : null,

						numeroMoradores != null && !numeroMoradores.trim().equalsIgnoreCase("") ? (Short.valueOf(numeroMoradores))
										.shortValue() : null, null, null, null, null, null, numeroIptu != null
										&& !numeroIptu.trim().equalsIgnoreCase("") ? new BigDecimal(numeroIptu) : null,
						numeroContratoCelpe != null && !numeroContratoCelpe.trim().equalsIgnoreCase("") ? Long.valueOf(numeroContratoCelpe)
										: null, null, extratoResponsavel != null && !extratoResponsavel.trim().equalsIgnoreCase("") ? Short
										.valueOf(extratoResponsavel) : null, null, Short.valueOf("2"),
						!Util.isVazioOuBranco(cordenadasX) ? Util.formatarMoedaRealparaBigDecimal(cordenadasX, 8) : null,
						!Util.isVazioOuBranco(cordenadasY) ? Util.formatarMoedaRealparaBigDecimal(cordenadasY, 8) : null, null,
						null, null, imovelPrincipal, null, ligacaoEsgotoSituacao, null, null, null, setorComercial, areaConstruidaFaixa,
						rendaFamiliarFaixa, pavimentoCalcadaObj, imovelPerfil, reservatorioVolumeFaixaSup, reservatorioVolumeFaixaInf,
						localidade, quadra,/* Hidrometro */null, null, esgotamentoObj, pavimentoRuaObj, enderecoReferencia, null,
						pocoTipo, ligacaoAguaSituacao, despejo, null, fonteAbastecimentoObj, piscinaVolumeFaixa, new Date(), logradouroCep);
		imovel.setFotoFachada(fotoFachada);
		imovel.setLogradouroBairro(imovelEndereco.getLogradouroBairro());
		imovel.setImovelContaEnvio(imovelContaEnvio);
		imovel.setIndicadorJardim(jardimShort);
		imovel.setSetorAbastecimento(setorAbastecimento);
		imovel.setSubBacia(subBacia);
		imovel.setDistritoOperacional(distritoOperacional);
		imovel.setCodigoSetorComercial(setorComercial.getCodigo());
		imovel.setNumeroQuadra(quadra.getNumeroQuadra());

		if(numeroMoradores == null || numeroMoradores.equals("")){

			if(sessao.getAttribute("indicadorCamposObrigatorios") != null){
				actionServletException = new ActionServletException("atencao.required", null, "Número de Moradores");
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}

		if(!Util.isVazioOuBranco(idRotaForm)){
			Rota rota = (Rota) sessao.getAttribute("rota");
			imovel.setRota(rota);
		}else{
			actionServletException = new ActionServletException("atencao.rota_inexistente");
			setarUrlLevantarExcecao(url, actionServletException);
		}
		if(nnSegmento != null && !nnSegmento.trim().equalsIgnoreCase("")){
			imovel.setNumeroSegmento(Short.valueOf(nnSegmento));
		}

		if(indicadorContratoConsumo != null){
			if(indicadorContratoConsumo){
				imovel.setIndicadorContratoConsumo(ConstantesSistema.SIM);
			}else{
				imovel.setIndicadorContratoConsumo(ConstantesSistema.NAO);
			}
		}

		// Sequencial na Rota
		if(sequencialRota != null && !sequencialRota.equals("")){
			imovel.setNumeroSequencialRota(Integer.valueOf(sequencialRota));
		}else{
			imovel.setNumeroSequencialRota(null);
		}

		// quantidade de economias
		imovel.setQuantidadeEconomias(Short.valueOf(quantidadeEconomias + ""));

		// indicador imovel condominio
		imovel.setIndicadorImovelCondominio(ConstantesSistema.INDICADOR_USO_DESATIVO);

		// indicador debito conta
		imovel.setIndicadorDebitoConta(ConstantesSistema.INDICADOR_USO_DESATIVO);

		if(idFuncionario != null && !idFuncionario.equals("")){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Integer.valueOf(idFuncionario)));

			Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroFuncionario, Funcionario.class
							.getName()));

			if(funcionario == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Funcionário");
				setarUrlLevantarExcecao(url, actionServletException);
			}

			imovel.setFuncionario(funcionario);

		}else{
			imovel.setFuncionario(null);
		}

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, idLocalidade));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_ID, setorComercial.getId()));

		if(quadra != null){
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_ID, quadra.getId()));
		}

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, lote));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SUBLOTE, subLote));

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, new Integer("2")));

		Collection imoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		// valida para saber se o imovel com os dados informados ja existe
		if(!Util.isVazioOrNulo(imoveis)){
			Imovel imovelExistente = (Imovel) imoveis.iterator().next();
			actionServletException = new ActionServletException("atencao.imovel_ja_cadastrado", null, imovelExistente.getId().toString());
			setarUrlLevantarExcecao(url, actionServletException);
		}

		ConsumoTarifa consumoTarifa = null;

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
			consumoTarifa = sistemaParametro.getConsumoTarifaDefault();

			if(consumoTarifa == null){
				actionServletException = new ActionServletException("atencao.consumo_tarifa_default_nao_cadastrado", null, sistemaParametro
								.getParmId().toString());
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}else{
			consumoTarifa = new ConsumoTarifa();
			consumoTarifa.setId(ConsumoTarifa.CONSUMO_NORMAL);
		}

		imovel.setConsumoTarifa(consumoTarifa);

		/**
		 * alterado por pedro alexandre dia 17/11/2006 Recupera o usuário logado
		 * para passar no metódo de inserir imóvel para verificar se o usuário
		 * tem abrangência para inserir o imóvel na localidade informada.
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
		Integer id = fachada
						.inserirImovelRetorno(imovel, subcategorias, consumoFaixaAreaCategoria, enderecoImovel, clientes, usuarioLogado);

		sessao.removeAttribute("colecaoImovelSubCategorias");
		sessao.removeAttribute("imovelClientesNovos");
		sessao.removeAttribute("distritoOperacional");
		sessao.removeAttribute("colecaoDistritoOperacional");

		montarPaginaSucesso(httpServletRequest, "Imóvel de matrícula " + id + " incluído com sucesso.", "Inserir outro Imóvel",
						"exibirInserirImovelAction.do?menu=sim", "exibirAtualizarImovelAction.do?idRegistroAtualizacao=" + id
										+ "&sucesso=sucesso", "Atualizar Imóvel Inserido", "Informar Ocorrências / Anormalidades",
						"exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?idImovel=" + id);

		return retorno;
	}

	/**
	 * Metódo criado para setar a url de retorno e levantar a exceção criada.
	 * 
	 * @param url
	 * @param actionServletException
	 */
	private void setarUrlLevantarExcecao(String url, ActionServletException actionServletException){

		actionServletException.setUrlBotaoVoltar(url);
		throw actionServletException;
	}

	/**
	 * Metódo criado para verificar qual a página que chamou o Concluir.
	 * Devido a um erro no IE, não se pode usar o history.back, por isso é necessário guardar a
	 * página para chama-la no retorno da exceção.
	 * 
	 * @param inserirImovelActionForm
	 * @return
	 */
	private String verificarUrlRetorno(DynaValidatorForm inserirImovelActionForm){

		int codigoUrl = Util.converterStringParaInteger((String) inserirImovelActionForm.get("url"));
		String url = null;
		switch(codigoUrl){
			case 1:
				url = "/gsan/inserirImovelWizardAction.do?destino=1&action=inserirImovelLocalidadeAction";
				break;
			case 2:
				url = "/gsan/inserirImovelWizardAction.do?destino=2&action=inserirImovelLocalidadeAction";
				break;
			case 3:
				url = "/gsan/inserirImovelWizardAction.do?destino=3&action=inserirImovelClienteAction";
				break;
			case 4:
				url = "/gsan/inserirImovelWizardAction.do?destino=4&action=inserirImovelSubCategoriaAction";
				break;
			case 5:
				url = "/gsan/inserirImovelWizardAction.do?destino=5&action=inserirImovelCaracteristicasAction";
				break;
			case 6:
				url = "/gsan/inserirImovelWizardAction.do?destino=6&action=inserirImovelCaracteristicasAction";
				break;

		}
		return url;
	}

}
