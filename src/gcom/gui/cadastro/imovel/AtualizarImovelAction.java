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
 * 
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
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;
import gcom.operacional.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.AlteracaoInscricaoImovelException;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
public class AtualizarImovelAction
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
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		String indicadorImovelConsumoFaixaAreaCatg = ParametroCadastro.P_INDICADOR_IMOVEL_CONSUMO_FAIXA_AREA_CATG.executar();

		DynaValidatorForm inserirImovelActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		if(httpServletRequest.getParameter("confirmado") != null && httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){
			sessao.setAttribute("confirmou", "sim");
		}

		// Cria Variaveis
		Imovel imovelAtualizar = (Imovel) sessao.getAttribute("imovelAtualizacao");
		String idLocalidade = (String) inserirImovelActionForm.get("idLocalidade");
		String idSetorComercial = (String) inserirImovelActionForm.get("idSetorComercial");
		String idQuadra = (String) inserirImovelActionForm.get("idQuadra");
		String lote = (String) inserirImovelActionForm.get("lote");
		String subLote = (String) inserirImovelActionForm.get("subLote");
		String testadaLote = (String) inserirImovelActionForm.get("testadaLote");
		String idFuncionario = (String) inserirImovelActionForm.get("idFuncionario");
		String idRota = (String) inserirImovelActionForm.get("idRota");
		String nnSegmento = (String) inserirImovelActionForm.get("nnSegmento");
		String idDistritoOperacional = (String) inserirImovelActionForm.get("idDistritoOperacional");
		Boolean indicadorContratoConsumo = (Boolean) inserirImovelActionForm.get("indicadorContratoConsumo");

		Short indicadorEnvioCorreio = null;
		if(Util.isNaoNuloBrancoZero(inserirImovelActionForm.get("indicadorEnvioCorreio"))){
			indicadorEnvioCorreio = Short.valueOf(inserirImovelActionForm.get("indicadorEnvioCorreio").toString());
		}

		String url = verificarUrlRetorno(inserirImovelActionForm);
		Map<String, String[]> mensagensConfirmacao = new HashMap<String, String[]>();
		boolean prepararAlteracaoInscricao = false;

		Collection enderecoImovel = (Collection) sessao.getAttribute("colecaoEnderecos");

		FormFile fotoFachadaForm = (FormFile) inserirImovelActionForm.get("fotoFachada");
		ActionServletException actionServletException = null;
		if(enderecoImovel == null){
			actionServletException = new ActionServletException("atencao.required", null, "Endereço do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}else if(enderecoImovel != null && enderecoImovel.isEmpty()){
			actionServletException = new ActionServletException("atencao.required", null, "Endereço do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		Collection setorComerciais = (Collection) sessao.getAttribute("setorComerciais");

		// Cria coleção
		Collection colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

		Imovel imovelEnderecos = (Imovel) colecaoEnderecos.iterator().next();

		// --Alteração realizada por Leonardo Vieira 24/02
		// --Dúvida em relação a setor sem município associado

		Rota rotaAtual = null;
		if(!Util.isVazioOuBranco(idRota)){
			rotaAtual = (Rota) sessao.getAttribute("rota");
		}else{
			actionServletException = new ActionServletException("atencao.rota_inexistente");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		if(imovelEnderecos.getLogradouroBairro() != null
						&& imovelEnderecos.getLogradouroBairro().getLogradouro() != null
						&& imovelEnderecos.getLogradouroBairro().getLogradouro().getMunicipio() != null
						&& setorComerciais != null
						&& !setorComerciais.isEmpty()
						&& (!(imovelEnderecos.getLogradouroBairro().getLogradouro().getMunicipio().getId().intValue() == (((SetorComercial) ((List) setorComerciais)
										.get(0)).getMunicipio().getId().intValue())))){

			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

			if(!fachada.verificarPermissaoAtualizarImovelMunicipioLogradouroDiferenteSetor(usuario)){
				actionServletException = new ActionServletException(
								"atencao.usuario.sem.permissao.inserir_logradouro_municipio_diferente_setor_comercial");
				setarUrlLevantarExcecao(url, actionServletException);
			}

			if(sessao.getAttribute("confirmou") == null){
				mensagensConfirmacao.put("atencao.usuario.sem.permissao.inserir_logradouro_municipio_diferente_setor_comercial", null);
			}
		}

		// [SB0004] – Verificar Alteração da Inscrição do Imóvel
		if(sessao.getAttribute("confirmou") == null){
			try{
				fachada.verificarAlteracaoInscricaoImovel(imovelAtualizar.getId(), rotaAtual);

			}catch(AlteracaoInscricaoImovelException e){

				sessao.setAttribute("prepararAlteracaoInscricao", "sim");
				mensagensConfirmacao.put(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
			}
		}else if(sessao.getAttribute("prepararAlteracaoInscricao") != null
						&& sessao.getAttribute("prepararAlteracaoInscricao").equals("sim")){
			prepararAlteracaoInscricao = true;
			sessao.removeAttribute("prepararAlteracaoInscricao");
		}else{
			prepararAlteracaoInscricao = false;
		}

		if(!mensagensConfirmacao.isEmpty()){
			sessao.removeAttribute("confirmou");
			retorno = montarPaginaConfirmacaoWizard(mensagensConfirmacao, httpServletRequest, actionMapping);

			return retorno;
		}



		// Obtem os valores q vem na colecao de cliente para cadastrar em
		// Cliente imovel
		Collection clientes = (Collection) sessao.getAttribute("imovelClientesNovos");
		if(clientes == null || clientes.isEmpty()){
			actionServletException = new ActionServletException("atencao.required", null, "Cliente do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		Boolean temUsuario = false;
		Boolean temProprietario = false;
		Boolean existeIndicadorNomeConta = false;
		Boolean temResponsavel = false;

		Integer tipoRelacaoTitularDebito = null;

		try{
			tipoRelacaoTitularDebito = Integer.valueOf(ParametroFaturamento.P_TIPO_RELACAO_ATUAL_TITULAR_DEBITO_IMOVEL.executar()
							.toString());
		}catch(NumberFormatException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(ControladorException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(clientes != null && !clientes.isEmpty()){

			Iterator clientesIteator = clientes.iterator();
			while(clientesIteator.hasNext()){
				ClienteImovel clienteImovel = new ClienteImovel();
				clienteImovel = (ClienteImovel) clientesIteator.next();

				if(clienteImovel.getClienteRelacaoTipo() != null){
					if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.USUARIO.intValue()){
						temUsuario = true;
					}else if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.PROPRIETARIO.intValue()){
						temProprietario = true;
					}else if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.RESPONSAVEL.intValue()){
						temResponsavel = true;
					}
				}
			}
			if(temUsuario == false){
				actionServletException = new ActionServletException("atencao.informe.cliente_imovel_usuario");
				setarUrlLevantarExcecao(url, actionServletException);
			}
			if(temResponsavel == false && tipoRelacaoTitularDebito.equals(ClienteRelacaoTipo.RESPONSAVEL)){
				actionServletException = new ActionServletException("atencao.informe.cliente_imovel_responsavel");
				setarUrlLevantarExcecao(url, actionServletException);
			}
			if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
				if(!temProprietario.booleanValue()){
					actionServletException = new ActionServletException("atencao.required", null, "um cliente do tipo proprietário");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}

			if(!existeIndicadorNomeConta){

				clientesIteator = clientes.iterator();

				while(clientesIteator.hasNext()){
					ClienteImovel clienteImovel = (ClienteImovel) clientesIteator.next();
					if(clienteImovel.getClienteRelacaoTipo().getId().toString().equals(ClienteRelacaoTipo.USUARIO.toString())){

						clienteImovel.setIndicadorNomeConta(ConstantesSistema.SIM);
					}

				}

			}

		}
		if(temUsuario == false){
			throw new ActionServletException("atencao.informe.cliente_imovel_usuario");
		}

		if(temResponsavel == false && tipoRelacaoTitularDebito.equals(ClienteRelacaoTipo.RESPONSAVEL)){
			throw new ActionServletException("atencao.informe.cliente_imovel_responsavel");
		}

		// Obtem os valores que vem na coleção de subCategorias(economia)para
		// cadastrar em Imovel_Subcategoria
		Collection subcategorias = (Collection) sessao.getAttribute("colecaoImovelSubCategorias");
		if(subcategorias == null || subcategorias.isEmpty()){
			actionServletException = new ActionServletException("atencao.required", null, " a Subcategoria do Imóvel");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		// Para cliente DESO = Se não houver um Consumo por faixa de area e categoria Informado,
		// levanta exceção
		Collection colecaoConsumoFaixaAreaCategoria = null;
		if(indicadorImovelConsumoFaixaAreaCatg.equals("1")){
			Set<Integer> setCategorias = new TreeSet<Integer>(new TreeSet(new Comparator() {

				public int compare(Object a, Object b){

					Integer v1 = (Integer) a;
					Integer v2 = (Integer) b;

					return v1.compareTo(v2);

				}
			}));

			Iterator it = subcategorias.iterator();

			while(it.hasNext()){
				setCategorias.add((((ImovelSubcategoria) it.next()).getComp_id().getSubcategoria().getCategoria().getId()));
			}

			colecaoConsumoFaixaAreaCategoria = (Collection) sessao.getAttribute("colecaoImovelConsumoFaixaAreaCategoria");

			if(colecaoConsumoFaixaAreaCategoria == null || colecaoConsumoFaixaAreaCategoria.isEmpty()){
				actionServletException = new ActionServletException("atencao.nenhum_consumo_faixa_area_categoria");
				setarUrlLevantarExcecao(url, actionServletException);
			}else if(setCategorias.size() > colecaoConsumoFaixaAreaCategoria.size()){
				actionServletException = new ActionServletException("atencao.nenhum_consumo_faixa_area_categoria");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		byte[] fotoFachada = null;
		// Se houver foto parar atualizar
		if(fotoFachadaForm != null && !fotoFachadaForm.getFileName().equals("")){
			try{
				fotoFachada = fotoFachadaForm.getFileData();
			}catch(FileNotFoundException e){
				actionServletException = new ActionServletException("erro.sistema", e);
				setarUrlLevantarExcecao(url, actionServletException);

			}catch(IOException e){
				actionServletException = new ActionServletException("erro.sistema", e);
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}else{
			// Pesquisa a foto atual
			String imovelCaracteristicas = (String) inserirImovelActionForm.get("matricula");
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelCaracteristicas));
			Collection cImovelCaracteristicas = fachada.pesquisar(filtroImovel, Imovel.class.getName());
			Imovel imovel = (Imovel) cImovelCaracteristicas.iterator().next();
			fotoFachada = imovel.getFotoFachada();
		}

		imovelAtualizar.setLote(Short.valueOf("0"));
		if(lote != null && !lote.trim().equalsIgnoreCase("")){
			imovelAtualizar.setLote(Short.valueOf(lote).shortValue());
		}

		imovelAtualizar.setSubLote(Short.valueOf("0"));
		if(subLote != null && !subLote.trim().equalsIgnoreCase("")){
			imovelAtualizar.setSubLote(Short.valueOf(subLote).shortValue());
		}

		if(testadaLote != null && !testadaLote.trim().equalsIgnoreCase("")){
			imovelAtualizar.setTestadaLote(Short.valueOf(testadaLote).shortValue());
		}

		// objeto DistritoOperacional
		if(!Util.isVazioOuBranco(idDistritoOperacional)){
			DistritoOperacional distritoOperacional = (DistritoOperacional) sessao.getAttribute("distritoOperacional");
			imovelAtualizar.setDistritoOperacional(distritoOperacional);
		}

		if(nnSegmento != null && !nnSegmento.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroSegmento(Short.valueOf(nnSegmento));
		}

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			if(indicadorContratoConsumo != null){
				if(indicadorContratoConsumo){
					imovelAtualizar.setIndicadorContratoConsumo(ConstantesSistema.SIM);
				}else{
					imovelAtualizar.setIndicadorContratoConsumo(ConstantesSistema.NAO);
				}
			}
		}
		if(indicadorEnvioCorreio != null){
			imovelAtualizar.setIndicadorEnvioCorreio(indicadorEnvioCorreio);
		}else{
			imovelAtualizar.setIndicadorEnvioCorreio((short) 2);
		}

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){

			// ********** Companhia - ADA ******************************
			// boolean areaConstruidaInformada = true;
			String areaConstruida = null;
			if(inserirImovelActionForm.get("areaConstruida") != null
							&& !inserirImovelActionForm.get("areaConstruida").toString().equalsIgnoreCase("")
							&& !inserirImovelActionForm.get("areaConstruida").toString().equalsIgnoreCase("null")){
				areaConstruida = inserirImovelActionForm.get("areaConstruida").toString();
			}
			if(areaConstruida != null && !areaConstruida.trim().equalsIgnoreCase("")){
				imovelAtualizar.setAreaConstruida(Util.formatarMoedaRealparaBigDecimal(areaConstruida));
			}

			// boolean areaConstruidaFaixaInformada = true;
			String faixaAreaConstruida = null;
			if(!inserirImovelActionForm.get("faixaAreaConstruida").toString().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				faixaAreaConstruida = (String) inserirImovelActionForm.get("faixaAreaConstruida");
			}

			AreaConstruidaFaixa areaConstruidaFaixa = null;

			if(faixaAreaConstruida != null && !faixaAreaConstruida.trim().equalsIgnoreCase("")
							&& !faixaAreaConstruida.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				FiltroAreaConstruidaFaixa filtroAreaConstruidaFaixa = new FiltroAreaConstruidaFaixa();
				filtroAreaConstruidaFaixa.adicionarParametro(new ParametroSimples(FiltroAreaConstruidaFaixa.CODIGO, Integer
								.valueOf(faixaAreaConstruida)));

				areaConstruidaFaixa = (AreaConstruidaFaixa) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroAreaConstruidaFaixa,
								AreaConstruidaFaixa.class.getName()));

				if(areaConstruidaFaixa == null){
					actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Area Construida");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}
			imovelAtualizar.setAreaConstruidaFaixa(areaConstruidaFaixa);

			// ********** Companhia - ADA ******************************
		}

		String faixaRendaFamiliar = null;
		if(!inserirImovelActionForm.get("faixaRendaFamiliar").toString().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			faixaRendaFamiliar = (String) inserirImovelActionForm.get("faixaRendaFamiliar");
		}

		String volumeReservatorioSuperior = null;
		if(inserirImovelActionForm.get("reservatorioSuperior") != null
						&& !inserirImovelActionForm.get("reservatorioSuperior").toString().equalsIgnoreCase("")
						&& !inserirImovelActionForm.get("reservatorioSuperior").toString().equalsIgnoreCase("null")){
			volumeReservatorioSuperior = inserirImovelActionForm.get("reservatorioSuperior").toString().replace(',', '.');
		}
		if(volumeReservatorioSuperior != null && !volumeReservatorioSuperior.trim().equalsIgnoreCase("")){
			imovelAtualizar.setVolumeReservatorioSuperior(Util.formatarMoedaRealparaBigDecimal(volumeReservatorioSuperior));
		}else{
			imovelAtualizar.setVolumeReservatorioSuperior(null);
		}

		String faixaVolumeReservatorioSuperior = null;
		if(!inserirImovelActionForm.get("faixaResevatorioSuperior").toString()
						.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			faixaVolumeReservatorioSuperior = (String) inserirImovelActionForm.get("faixaResevatorioSuperior");
		}

		String volumeReservatorioInferior = null;
		if(inserirImovelActionForm.get("reservatorioInferior") != null
						&& !inserirImovelActionForm.get("reservatorioInferior").toString().equalsIgnoreCase("")
						&& !inserirImovelActionForm.get("reservatorioInferior").toString().equalsIgnoreCase("null")){
			volumeReservatorioInferior = inserirImovelActionForm.get("reservatorioInferior").toString().replace(',', '.');
		}
		if(volumeReservatorioInferior != null && !volumeReservatorioInferior.trim().equalsIgnoreCase("")){
			imovelAtualizar.setVolumeReservatorioInferior(Util.formatarMoedaRealparaBigDecimal(volumeReservatorioInferior));
		}else{
			imovelAtualizar.setVolumeReservatorioInferior(null);
		}

		String faixaVolumeReservaotirio = null;
		if(!inserirImovelActionForm.get("faixaReservatorioInferior").toString().equalsIgnoreCase(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			faixaVolumeReservaotirio = (String) inserirImovelActionForm.get("faixaReservatorioInferior");
		}

		String piscina = null;
		if(inserirImovelActionForm.get("piscina") != null && !inserirImovelActionForm.get("piscina").toString().equalsIgnoreCase("")
						&& !inserirImovelActionForm.get("piscina").toString().equalsIgnoreCase("null")){
			piscina = inserirImovelActionForm.get("piscina").toString().replace(',', '.');
		}
		if(piscina != null && !piscina.trim().equalsIgnoreCase("")){
			imovelAtualizar.setVolumePiscina(Util.formatarMoedaRealparaBigDecimal(piscina));
		}else{
			imovelAtualizar.setVolumePiscina(null);
		}

		// jardim
		String jardim = (String) inserirImovelActionForm.get("jardim");

		Short jardimShort = null;

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){
			// ********** Companhia - ADA ******************************
			jardimShort = Short.valueOf(jardim);
			// ********** Companhia - ADA ******************************
		}

		String sequencialRota = (String) inserirImovelActionForm.get("sequencialRota");
		// Sequencial na Rota
		if(sequencialRota != null && !sequencialRota.equals("")){
			imovelAtualizar.setNumeroSequencialRota(Integer.valueOf(sequencialRota));
		}else{
			imovelAtualizar.setNumeroSequencialRota(null);
		}

		String faixaPiscina = null;
		if(!inserirImovelActionForm.get("faixaPiscina").toString().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			faixaPiscina = (String) inserirImovelActionForm.get("faixaPiscina");
		}

		String pavimentoCalcada = null;
		if(!inserirImovelActionForm.get("pavimentoCalcada").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			pavimentoCalcada = (String) inserirImovelActionForm.get("pavimentoCalcada");
		}

		String padraoConstrucao = null;
		if(!inserirImovelActionForm.get("padraoConstrucao").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			padraoConstrucao = (String) inserirImovelActionForm.get("padraoConstrucao");
		}

		String esgotamento = null;
		if(!inserirImovelActionForm.get("esgotamento").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			esgotamento = (String) inserirImovelActionForm.get("esgotamento");
		}

		String pavimentoRua = null;
		if(!inserirImovelActionForm.get("pavimentoRua").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			pavimentoRua = (String) inserirImovelActionForm.get("pavimentoRua");
		}

		String fonteAbastecimento = null;
		if((!inserirImovelActionForm.get("fonteAbastecimento").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + ""))
						&& (!inserirImovelActionForm.get("fonteAbastecimento").toString().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			fonteAbastecimento = (String) inserirImovelActionForm.get("fonteAbastecimento");
		}

		String situacaoLigacaoAgua = null;
		if(!inserirImovelActionForm.get("situacaoLigacaoAgua").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			situacaoLigacaoAgua = (String) inserirImovelActionForm.get("situacaoLigacaoAgua");
		}else{
			actionServletException = new ActionServletException("atencao.required", null, "Situação Ligação Água");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		String situacaoLigacaoEsgoto = null;
		if(!inserirImovelActionForm.get("situacaoLigacaoEsgoto").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			situacaoLigacaoEsgoto = (String) inserirImovelActionForm.get("situacaoLigacaoEsgoto");
		}else{
			actionServletException = new ActionServletException("atencao.required", null, "Situação Ligação Esgoto");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		String perfilImovel = null;
		if(!inserirImovelActionForm.get("perfilImovel").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			perfilImovel = (String) inserirImovelActionForm.get("perfilImovel");
		}else{
			if(!(imovelAtualizar.getImovelPerfil() != null && imovelAtualizar.getImovelPerfil().getId().equals(
							ConstantesSistema.INDICADOR_TARIFA_SOCIAL))){
				actionServletException = new ActionServletException("atencao.required", null, "Perfil do Imóvel");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		String poco = null;
		if((!inserirImovelActionForm.get("poco").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + ""))){
			poco = (String) inserirImovelActionForm.get("poco");
		}

		String pontoUtilizaco = null;
		if(!inserirImovelActionForm.get("numeroPontos").toString().equalsIgnoreCase("0")){
			pontoUtilizaco = (String) inserirImovelActionForm.get("numeroPontos");
		}
		if(pontoUtilizaco != null && !pontoUtilizaco.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroPontosUtilizacao(Short.valueOf(pontoUtilizaco));
		}else{
			imovelAtualizar.setNumeroPontosUtilizacao(null);
		}

		String numeroQuarto = null;
		if(!inserirImovelActionForm.get("numeroQuarto").toString().equalsIgnoreCase("0")){
			numeroQuarto = (String) inserirImovelActionForm.get("numeroQuarto");
		}
		if(numeroQuarto != null && !numeroQuarto.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroQuarto(Short.valueOf(numeroQuarto));
		}

		String numeroBanheiro = null;
		if(!inserirImovelActionForm.get("numeroBanheiro").toString().equalsIgnoreCase("0")){
			numeroBanheiro = (String) inserirImovelActionForm.get("numeroBanheiro");
		}
		if(numeroBanheiro != null && !numeroBanheiro.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroBanheiro(Short.valueOf(numeroBanheiro));
		}

		String numeroAdulto = null;
		if(!inserirImovelActionForm.get("numeroAdulto").toString().equalsIgnoreCase("0")){
			numeroAdulto = (String) inserirImovelActionForm.get("numeroAdulto");
		}
		if(numeroAdulto != null && !numeroAdulto.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroAdulto(Short.valueOf(numeroAdulto));
		}

		String numeroCrianca = null;
		if(!inserirImovelActionForm.get("numeroCrianca").toString().equalsIgnoreCase("0")){
			numeroCrianca = (String) inserirImovelActionForm.get("numeroCrianca");
		}
		if(numeroCrianca != null && !numeroCrianca.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroCrianca(Short.valueOf(numeroCrianca));
		}

		String numeroMoradorTrabalhador = null;
		if(!inserirImovelActionForm.get("numeroMoradorTrabalhador").toString().equalsIgnoreCase("0")){
			numeroMoradorTrabalhador = (String) inserirImovelActionForm.get("numeroMoradorTrabalhador");
		}
		if(numeroMoradorTrabalhador != null && !numeroMoradorTrabalhador.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroMoradorTrabalhador(Short.valueOf(numeroMoradorTrabalhador));
		}

		String numeroMoradores = null;
		if(!inserirImovelActionForm.get("numeroMoradores").toString().equalsIgnoreCase("0")){
			numeroMoradores = (String) inserirImovelActionForm.get("numeroMoradores");
		}
		if(numeroMoradores != null && !numeroMoradores.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroMorador(Short.valueOf(numeroMoradores));
		}else if(sessao.getAttribute("indicadorCamposObrigatorios") != null){
			if(numeroMoradores == null || numeroMoradores.trim().equals("")){
				actionServletException = new ActionServletException("atencao.required", null, "Número de Moradores");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		String numeroIptu = null;
		if(!inserirImovelActionForm.get("numeroIptu").toString().equalsIgnoreCase("0")){
			numeroIptu = (String) inserirImovelActionForm.get("numeroIptu");
		}
		if(numeroIptu != null && !numeroIptu.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroIptu(BigDecimal.valueOf(Double.valueOf(numeroIptu)));
		}

		String numeroContratoCelpe = null;
		if(!inserirImovelActionForm.get("numeroContratoCelpe").toString().equalsIgnoreCase("0")){
			numeroContratoCelpe = (String) inserirImovelActionForm.get("numeroContratoCelpe");
		}
		if(numeroContratoCelpe != null && !numeroContratoCelpe.trim().equalsIgnoreCase("")){
			imovelAtualizar.setNumeroCelpe(Long.valueOf(numeroContratoCelpe));
		}

		String envioConta = null;
		if(!inserirImovelActionForm.get("imovelContaEnvio").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			envioConta = (String) inserirImovelActionForm.get("imovelContaEnvio");

			boolean achouClienteDoMesmoTipo = false;
			Integer relacaoTipo;
			for(Object objeto : clientes){
				ClienteImovel clienteImovel = (ClienteImovel) objeto;
				relacaoTipo = clienteImovel.getClienteRelacaoTipo().getId();
				if(envioConta.equals(ImovelContaEnvio.ENVIAR_CLIENTE_RESPONSAVEL + "")
								&& relacaoTipo.equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(envioConta.equals(ImovelContaEnvio.ENVIAR_PARA_CLIENTE_PROPRIETARIO + "")
								&& relacaoTipo.equals(ClienteRelacaoTipo.PROPRIETARIO)){
					achouClienteDoMesmoTipo = true;
				}else if(envioConta.equals(ImovelContaEnvio.ENVIAR_PARA_CLIENTE_USUARIO + "")
								&& relacaoTipo.equals(ClienteRelacaoTipo.USUARIO)){
					achouClienteDoMesmoTipo = true;
				}else if(envioConta.equals(ImovelContaEnvio.NAO_PAGAVEL_IMOVEL_PAGAVEL_RESPONSAVEL + "")
								&& relacaoTipo.equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(envioConta.equals(ImovelContaEnvio.PAGAVEL_PARA_IMOVEL_E_NAO_PAGAVEL_PARA_RESPOSAVEL + "")
								&& relacaoTipo.equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(envioConta.equals(ImovelContaEnvio.PAGAVEL_PARA_IMOVEL_E_PAGAVEL_PARA_RESPONSAVEL + "")
								&& relacaoTipo.equals(ClienteRelacaoTipo.RESPONSAVEL)){
					achouClienteDoMesmoTipo = true;
				}else if(envioConta.equals(ImovelContaEnvio.ENVIAR_IMOVEL + "")){
					achouClienteDoMesmoTipo = true;
				}
			}
			if(!achouClienteDoMesmoTipo){
				actionServletException = new ActionServletException("atencao.imovel.endereco.tipo_cliente");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		String coordenadasX = null;
		imovelAtualizar.setCoordenadaX(null);
		if(inserirImovelActionForm.get("cordenadasUtmX") != null
						&& !inserirImovelActionForm.get("cordenadasUtmX").toString().equalsIgnoreCase("")
						&& !inserirImovelActionForm.get("cordenadasUtmX").toString().equalsIgnoreCase("null")){
			coordenadasX = inserirImovelActionForm.get("cordenadasUtmX").toString().replace(',', '.');
		}
		if(coordenadasX != null && !coordenadasX.trim().equalsIgnoreCase("")){
			if(GenericValidator.isDouble(coordenadasX)){
				imovelAtualizar.setCoordenadaX(Util.formatarMoedaRealparaBigDecimal(coordenadasX, 8));
			}else{
				actionServletException = new ActionServletException("atencao.valor_coordenada_invalido", null, "X");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		String coordenadasY = null;
		imovelAtualizar.setCoordenadaY(null);
		if(inserirImovelActionForm.get("cordenadasUtmY") != null
						&& !inserirImovelActionForm.get("cordenadasUtmY").toString().equalsIgnoreCase("")
						&& !inserirImovelActionForm.get("cordenadasUtmY").toString().equalsIgnoreCase("null")){
			coordenadasY = inserirImovelActionForm.get("cordenadasUtmY").toString().replace(',', '.');
		}
		if(coordenadasY != null && !coordenadasY.trim().equalsIgnoreCase("")){
			if(GenericValidator.isDouble(coordenadasY)){
				imovelAtualizar.setCoordenadaY(Util.formatarMoedaRealparaBigDecimal(coordenadasY, 8));
			}else{
				actionServletException = new ActionServletException("atencao.valor_coordenada_invalido", null, "Y");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}

		String extratoResponsavel = null;
		if(inserirImovelActionForm.get("extratoResponsavel") != null
						&& !inserirImovelActionForm.get("extratoResponsavel").toString().equalsIgnoreCase("")
						&& !inserirImovelActionForm.get("extratoResponsavel").toString().equalsIgnoreCase("null")){
			extratoResponsavel = (String) inserirImovelActionForm.get("extratoResponsavel");
		}
		if(extratoResponsavel != null && !extratoResponsavel.trim().equalsIgnoreCase("")){
			if(extratoResponsavel.equals("1")){
				boolean achouResponsavel = false;
				for(Object objeto : clientes){
					ClienteImovel clienteImovel = (ClienteImovel) objeto;
					if(clienteImovel.getClienteRelacaoTipo().getId() == ClienteRelacaoTipo.RESPONSAVEL.intValue()){
						achouResponsavel = true;
					}
				}
				if(!achouResponsavel){
					actionServletException = new ActionServletException("atencao.imovel.endereco.envio.clienteResponsavel");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}
			imovelAtualizar.setIndicadorEmissaoExtratoFaturamento(Short.valueOf(extratoResponsavel));
		}

		String tipoDespejo = null;
		if(!inserirImovelActionForm.get("tipoDespejo").toString().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			tipoDespejo = (String) inserirImovelActionForm.get("tipoDespejo");
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

		imovelAtualizar.setSetorAbastecimento(setorAbastecimento);

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

		imovelAtualizar.setSubBacia(subBacia);

		String idImovelPrincipal = (String) inserirImovelActionForm.get("idImovel");

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

			Imovel imovelAtualizado = (Imovel) sessao.getAttribute("imovelAtualizacao");

			// testa se o imovel a ser principal é o mesmo que esta sendo
			// atualizado
			if(idImovelPrincipal.equals(imovelAtualizado.getId().toString())){
				actionServletException = new ActionServletException("atencao.imovel.principal.igual.atualizado");
				setarUrlLevantarExcecao(url, actionServletException);
			}

		}

		// Obtem os valores q vem na colecao de pesquisa de endereco para
		// montajem do imovel
		Iterator iteratorEnderecoImovel = enderecoImovel.iterator();
		Imovel imovelEndereco = (Imovel) iteratorEnderecoImovel.next();
		Logradouro logradouro = null;

		String numeroImovel = imovelEndereco.getNumeroImovel();
		Integer cep = null;
		if(imovelEndereco.getLogradouroCep().getCep() != null){
			cep = imovelEndereco.getLogradouroCep().getCep().getCepId();
		}
		String complemento = imovelEndereco.getComplementoEndereco();
		imovelAtualizar.setNumeroImovel(numeroImovel);
		imovelAtualizar.setComplementoEndereco(complemento);

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
					actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Endereço Referência");
					setarUrlLevantarExcecao(url, actionServletException);
				}
			}
		}
		imovelAtualizar.setEnderecoReferencia(enderecoReferencia);

		Integer idLogradouro = null;

		if(imovelEndereco.getLogradouroCep().getLogradouro() != null){
			idLogradouro = imovelEndereco.getLogradouroCep().getLogradouro().getId();
			logradouro = new Logradouro();
			logradouro.setId(idLogradouro);
		}else{
			// ALTERAÇÃO FEITA PARA FUNCIONAMENTO DA APLICAÇÃO, APAGAR
			// A CONDIÇÃO DO "SE NÃO", ALTERAÇÃO DO VALOR DO LOGRADOURO ESTA
			// SENDO ANALIZADA POR ARYED, ANA E LEO NARDO NO DIA 16/03/2006
			logradouro = new Logradouro();
			logradouro.setId(Integer.valueOf("0"));
		}

		// Cria objetos para jogalos no imovel
		// objeto Localidade
		Localidade localidade = (Localidade) sessao.getAttribute("localidade");
		imovelAtualizar.setLocalidade(localidade);

		// objeto Setor Comercial
		SetorComercial setorComercial = (SetorComercial) sessao.getAttribute("setorComercial");
		imovelAtualizar.setSetorComercial(setorComercial);

		// objeto Quadra
		Quadra quadra = (Quadra) sessao.getAttribute("quadra");
		imovelAtualizar.setQuadra(quadra);

		// objeto Imovel(Principal)
		Imovel imovelPrincipal = null;

		if(idImovelPrincipal != null && !idImovelPrincipal.trim().equalsIgnoreCase("")){
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(idImovelPrincipal)));

			imovelPrincipal = (Imovel) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroImovel, Imovel.class.getName()));

			if(imovelPrincipal == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Imóvel Principal");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setImovelPrincipal(imovelPrincipal);

		// objeto faixa rendaFamiliar
		RendaFamiliarFaixa rendaFamiliarFaixa = null;

		if(faixaRendaFamiliar != null && !faixaRendaFamiliar.trim().equalsIgnoreCase("")
						&& !faixaRendaFamiliar.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						&& !faixaRendaFamiliar.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroRendaFamiliarFaixa filtroRendaFamiliarFaixa = new FiltroRendaFamiliarFaixa();
			filtroRendaFamiliarFaixa.adicionarParametro(new ParametroSimples(FiltroRendaFamiliarFaixa.CODIGO, Integer
							.valueOf(faixaRendaFamiliar)));

			rendaFamiliarFaixa = (RendaFamiliarFaixa) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroRendaFamiliarFaixa,
							RendaFamiliarFaixa.class.getName()));

			if(rendaFamiliarFaixa == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Renda Família");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setRendaFamiliarFaixa(rendaFamiliarFaixa);

		// objeto Pavimento calçada
		PavimentoCalcada pavimentoCalcadaObj = null;

		if(pavimentoCalcada != null && !pavimentoCalcada.trim().equalsIgnoreCase("")
						&& !pavimentoCalcada.trim().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroPavimentoCalcada filtroPavimentoCalcada = new FiltroPavimentoCalcada();
			filtroPavimentoCalcada.adicionarParametro(new ParametroSimples(FiltroPavimentoCalcada.ID, Integer.valueOf(pavimentoCalcada)));

			pavimentoCalcadaObj = (PavimentoCalcada) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPavimentoCalcada,
							PavimentoCalcada.class.getName()));

			if(pavimentoCalcadaObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Pavimento Calçada");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setPavimentoCalcada(pavimentoCalcadaObj);

		// objeto Padrao Construcao
		PadraoConstrucao padraoConstrucaoObj = null;

		if(padraoConstrucao != null && !padraoConstrucao.trim().equalsIgnoreCase("")
						&& !padraoConstrucao.trim().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !padraoConstrucao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroPadraoConstrucao filtroPadraoConstrucao = new FiltroPadraoConstrucao();
			filtroPadraoConstrucao.adicionarParametro(new ParametroSimples(FiltroPadraoConstrucao.ID, Integer.valueOf(padraoConstrucao)));

			padraoConstrucaoObj = (PadraoConstrucao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPadraoConstrucao,
							PadraoConstrucao.class.getName()));

			if(padraoConstrucaoObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Padrão Construição");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setPadraoConstrucao(padraoConstrucaoObj);

		// objeto Esgotamento
		Esgotamento esgotamengoObj = null;

		if(esgotamento != null && !esgotamento.trim().equalsIgnoreCase("")){
			FiltroEsgotamento filtroEsgotamento = new FiltroEsgotamento();
			filtroEsgotamento.adicionarParametro(new ParametroSimples(FiltroEsgotamento.ID, Integer.valueOf(esgotamento)));

			esgotamengoObj = (Esgotamento) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroEsgotamento, Esgotamento.class.getName()));

			if(esgotamengoObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Esgotamento");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setEsgotamento(esgotamengoObj);

		// objeto Pavimento Rua
		PavimentoRua pavimentoRuaObj = null;

		if(pavimentoRua != null && !pavimentoRua.trim().equalsIgnoreCase("")
						&& !pavimentoRua.trim().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			FiltroPavimentoRua filtroPavimentoRua = new FiltroPavimentoRua();
			filtroPavimentoRua.adicionarParametro(new ParametroSimples(FiltroPavimentoRua.ID, Integer.valueOf(pavimentoRua)));

			pavimentoRuaObj = (PavimentoRua) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroPavimentoRua, PavimentoRua.class.getName()));

			if(pavimentoRuaObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Pavimento Rua");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setPavimentoRua(pavimentoRuaObj);

		// objeto Perfil Imovel
		ImovelPerfil imovelPerfil = null;

		if(perfilImovel != null && !perfilImovel.trim().equalsIgnoreCase("")){
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, Integer.valueOf(perfilImovel)));

			imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName()));

			if(imovelPerfil == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Perfil do Imóvel");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setImovelPerfil(imovelPerfil);

		// objeto Revservatorio Volume Faixa Superior
		ReservatorioVolumeFaixa reservatorioVolumeFaixaSup = null;

		if(faixaVolumeReservatorioSuperior != null && !faixaVolumeReservatorioSuperior.trim().equalsIgnoreCase("")
						&& !faixaVolumeReservatorioSuperior.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
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
		imovelAtualizar.setReservatorioVolumeFaixaSuperior(reservatorioVolumeFaixaSup);

		// objeto Reservatorio Volume Faixa Inferior
		ReservatorioVolumeFaixa reservatorioVolumeFaixaInf = null;

		if(faixaVolumeReservaotirio != null && !faixaVolumeReservaotirio.trim().equalsIgnoreCase("")
						&& !faixaVolumeReservaotirio.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
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
		imovelAtualizar.setReservatorioVolumeFaixaInferior(reservatorioVolumeFaixaInf);

		// objeto Despejo
		Despejo despejo = null;

		if(tipoDespejo != null && !tipoDespejo.trim().equalsIgnoreCase("")
						&& !tipoDespejo.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroDespejo filtroDespejo = new FiltroDespejo();
			filtroDespejo.adicionarParametro(new ParametroSimples(FiltroDespejo.ID, Integer.valueOf(tipoDespejo)));

			despejo = (Despejo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroDespejo, Despejo.class.getName()));

			if(despejo == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Despejo");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setDespejo(despejo);

		// objeto PocoTipo
		PocoTipo pocoTipo = null;

		if(poco != null && !poco.trim().equalsIgnoreCase("")){
			FiltroPocoTipo filtroPocoTipo = new FiltroPocoTipo();
			filtroPocoTipo.adicionarParametro(new ParametroSimples(FiltroPocoTipo.ID, Integer.valueOf(poco)));

			pocoTipo = (PocoTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPocoTipo, PocoTipo.class.getName()));

			if(pocoTipo == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Poço");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setPocoTipo(pocoTipo);

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

		// objeto Situacao ligacao Agua
		LigacaoAguaSituacao ligacaoAguaSituacao = null;

		if(situacaoLigacaoAgua != null && !situacaoLigacaoAgua.trim().equalsIgnoreCase("")){
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, Integer
							.valueOf(situacaoLigacaoAgua)));

			ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLigacaoAguaSituacao,
							LigacaoAguaSituacao.class.getName()));

			if(ligacaoAguaSituacao == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Situação Ligação de Água");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setLigacaoAguaSituacao(ligacaoAguaSituacao);

		// objeto Situacao ligcaoa esgoto
		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

		if(situacaoLigacaoEsgoto != null && !situacaoLigacaoEsgoto.trim().equalsIgnoreCase("")){
			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, Integer
							.valueOf(situacaoLigacaoEsgoto)));

			ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLigacaoEsgotoSituacao,
							LigacaoEsgotoSituacao.class.getName()));

			if(ligacaoEsgotoSituacao == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Situação Ligação de Esgoto");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);

		// objeto Fonte Abastecimento
		FonteAbastecimento fonteAbastecimentoObj = null;

		if(fonteAbastecimento != null && !fonteAbastecimento.trim().equalsIgnoreCase("")){
			FiltroFonteAbastecimento filtroFonteAbastecimento = new FiltroFonteAbastecimento();
			filtroFonteAbastecimento.adicionarParametro(new ParametroSimples(FiltroFonteAbastecimento.ID, Integer
							.valueOf(fonteAbastecimento)));

			fonteAbastecimentoObj = (FonteAbastecimento) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroFonteAbastecimento,
							FonteAbastecimento.class.getName()));

			if(fonteAbastecimentoObj == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Fonte de Abastecimento");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setFonteAbastecimento(fonteAbastecimentoObj);

		// objeto PiscinaVolumeFaixa
		PiscinaVolumeFaixa piscinaVolumeFaixa = null;

		if(faixaPiscina != null && !faixaPiscina.trim().equalsIgnoreCase("")
						&& !faixaPiscina.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroPiscinaVolumeFaixa filtroPiscinaVolumeFaixa = new FiltroPiscinaVolumeFaixa();
			filtroPiscinaVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroPiscinaVolumeFaixa.ID, Integer.valueOf(faixaPiscina)));

			piscinaVolumeFaixa = (PiscinaVolumeFaixa) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPiscinaVolumeFaixa,
							PiscinaVolumeFaixa.class.getName()));

			if(piscinaVolumeFaixa == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Volume Piscina");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}
		imovelAtualizar.setPiscinaVolumeFaixa(piscinaVolumeFaixa);

		ImovelContaEnvio imovelContaEnvio = null;

		if(envioConta != null && !envioConta.trim().equalsIgnoreCase("")
						&& !envioConta.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroImovelContaEnvio filtroImovelContaEnvio = new FiltroImovelContaEnvio();
			filtroImovelContaEnvio.adicionarParametro(new ParametroSimples(FiltroImovelContaEnvio.ID, Integer.valueOf(envioConta)));

			imovelContaEnvio = (ImovelContaEnvio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroImovelContaEnvio,
							ImovelContaEnvio.class.getName()));

			if(imovelContaEnvio == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Envio de Conta");
				setarUrlLevantarExcecao(url, actionServletException);
			}
		}else{
			actionServletException = new ActionServletException("atencao.informe_campo", "Envio da Conta");
			setarUrlLevantarExcecao(url, actionServletException);
		}

		// Caso não seja a situação do fluxo [FS008] - VERIFICAR SITUAÇÃO DA
		// LIGAÇÃO AGUA - FACTIVEL e POTENCIAL
		// verifica a situação primeiro do imovel para depois caso não satisfaça
		// a condição faz em relação a quadra
		if(!(imovelAtualizar.getLigacaoAguaSituacao() != null && (imovelAtualizar.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.FACTIVEL
						.intValue() & imovelAtualizar.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.POTENCIAL
						.intValue()))){
			FiltroQuadra filtroQuadra = new FiltroQuadra();
			if(localidade != null){
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, localidade.getId()));
			}
			if(setorComercial != null){
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));
			}
			if(quadra != null){
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, quadra.getId()));
			}
			Collection quadrasNaBase = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			// VERIFICAR A SITUAÇÃO DE AGUA PARA A QUADRA, CASO ELA
			// TENHA SITDO ALTERADO AO MESMO TEMPO EM QUE O IMOVEL ESTEJA SENDO
			// ATERADO
			if(quadrasNaBase != null && !quadrasNaBase.isEmpty()){

				Quadra quadraNaBase = ((Quadra) ((List) quadrasNaBase).get(0));
				// SITUAÇÃO DE LIGAÇÃO DE AGUA
				if(ligacaoAguaSituacao != null){
					if(quadraNaBase.getIndicadorRedeAgua() != null && quadraNaBase.getIndicadorRedeAgua().equals(Quadra.SEM_REDE)){
						if(!(ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.POTENCIAL.toString()))){
							actionServletException = new ActionServletException("atencao.imovel.ligacao_agua.incompativel");
							setarUrlLevantarExcecao(url, actionServletException);
						}
					}
					if(quadraNaBase.getIndicadorRedeAgua() != null && quadraNaBase.getIndicadorRedeAgua().equals(Quadra.COM_REDE)){
						if(!(ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.FACTIVEL.toString()))){
							actionServletException = new ActionServletException("atencao.imovel.ligacao_agua.incompativel");
							setarUrlLevantarExcecao(url, actionServletException);
						}

					}
					if(quadraNaBase.getIndicadorRedeAgua() != null && quadraNaBase.getIndicadorRedeAgua().equals(Quadra.REDE_PARCIAL)){

						if(!((ligacaoAguaSituacao.getId().toString().equals(LigacaoAguaSituacao.POTENCIAL.toString())) || (ligacaoAguaSituacao
										.getId().toString().equals(LigacaoAguaSituacao.FACTIVEL.toString())))){
							actionServletException = new ActionServletException("atencao.imovel.ligacao_agua.incompativel");
							setarUrlLevantarExcecao(url, actionServletException);
						}
					}
				}
			}
		}


		imovelAtualizar.setRota(rotaAtual);

		// Caso não seja a situação do fluxo [FS009] - VERIFICAR SITUAÇÃO DA
		// LIGAÇÃO ESGOTO - FACTIVEL e
		// POTENCIA
		// verifica a situação primeiro do imovel para depois caso não
		// satisfaça a condição faz em relação a quadra
		if(!(imovelAtualizar.getLigacaoEsgotoSituacao() != null && (imovelAtualizar.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.FACTIVEL
						.intValue() & imovelAtualizar.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.POTENCIAL
						.intValue()))){

			FiltroQuadra filtroQuadra = new FiltroQuadra();
			if(localidade != null){
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, localidade.getId()));
			}
			if(setorComercial != null){
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));
			}
			if(quadra != null){
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, quadra.getId()));
			}
			Collection quadrasNaBase = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			// VERIFICAR A SITUAÇÃO DE ESGOTO PARA A QUADRA, CASO ELA
			// TENHA SITDO ALTERADO AO MESMO TEMPO EM QUE O IMOVEL ESTEJA SENDO
			// ATERADO
			if(quadrasNaBase != null && !quadrasNaBase.isEmpty()){

				Quadra quadraNaBase = ((Quadra) ((List) quadrasNaBase).get(0));

				// SITUAÇÃO DE LIGAÇÃO ESGOTO
				if(ligacaoEsgotoSituacao != null){
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

						if(!((ligacaoEsgotoSituacao.getId().toString().equals(LigacaoEsgotoSituacao.POTENCIAL.toString())) || (ligacaoEsgotoSituacao
										.getId().toString().equals(LigacaoEsgotoSituacao.FACTIVEL.toString())))){
							actionServletException = new ActionServletException("atencao.imovel.ligacao_esgoto.incompativel");
							setarUrlLevantarExcecao(url, actionServletException);
						}
					}
				}
			}

		}

		LogradouroCep logradouroCep = null;
		if(cepObj != null){
			logradouroCep = fachada.pesquisarAssociacaoLogradouroCep(cepObj.getCepId(), logradouro.getId());
		}
		imovelAtualizar.setLogradouroCep(logradouroCep);
		imovelAtualizar.setFotoFachada(fotoFachada);
		imovelAtualizar.setLogradouroBairro(imovelEndereco.getLogradouroBairro());
		imovelAtualizar.setImovelContaEnvio(imovelContaEnvio);
		imovelAtualizar.setNumeroSequencialRota(imovelAtualizar.getNumeroSequencialRota());

		imovelAtualizar.setCodigoSetorComercial(setorComercial.getCodigo());
		imovelAtualizar.setNumeroQuadra(quadra.getNumeroQuadra());

		int quantidadeEconomias = 0;
		Iterator iteratorSubcategorias = subcategorias.iterator();
		while(iteratorSubcategorias.hasNext()){
			ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) iteratorSubcategorias.next();
			quantidadeEconomias = quantidadeEconomias + imovelSubcategoria.getQuantidadeEconomias();
		}

		imovelAtualizar.setQuantidadeEconomias(Short.valueOf(quantidadeEconomias + ""));

		if(idFuncionario != null && !idFuncionario.equals("")){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Integer.valueOf(idFuncionario)));

			Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroFuncionario, Funcionario.class
							.getName()));

			if(funcionario == null){
				actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Fucionário");
				setarUrlLevantarExcecao(url, actionServletException);
			}

			imovelAtualizar.setFuncionario(funcionario);

		}else{
			imovelAtualizar.setFuncionario(null);
		}

		Collection colecaoImovelSubcategoriasRemovidas = (Collection) sessao.getAttribute("colecaoImovelSubcategoriasRemoviadas");

		Collection colecaoClientesImoveisRemovidos = (Collection) sessao.getAttribute("colecaoClientesImoveisRemovidos");

		// jardim
		imovelAtualizar.setIndicadorJardim(jardimShort);

		/**
		 * alterado por pedro alexandre dia 17/11/2006 Recupera o usuário logado
		 * para passar no metódo de atualizar imóvel para verificar se o usuário
		 * tem abrangência para atualizar o imóvel informado.
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		fachada.atualizarImovel(imovelAtualizar, subcategorias, colecaoConsumoFaixaAreaCategoria, enderecoImovel, clientes,
						colecaoClientesImoveisRemovidos, colecaoImovelSubcategoriasRemovidas, usuarioLogado, prepararAlteracaoInscricao);

		montarPaginaSucesso(httpServletRequest, "Imóvel de matrícula " + imovelAtualizar.getId().toString() + " atualizado com sucesso.",
						"Realizar outra Manutenção de Imóvel", "exibirFiltrarImovelAction.do?menu=sim",
						"exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?idImovel=" + imovelAtualizar.getId().toString(),
						"Informar Ocorrências / Anormalidades");

		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("imovelAtualizacao");
		sessao.removeAttribute("distritoOperacional");
		sessao.removeAttribute("localidade");
		sessao.removeAttribute("setorComercial");
		sessao.removeAttribute("quadra");
		sessao.removeAttribute("colecaoImovelSubCategorias");
		sessao.removeAttribute("colecaoDistritoOperacional");

		if(sessao.getAttribute("colecaoClientesImoveisRemovidos") != null){
			sessao.removeAttribute("colecaoClientesImoveisRemovidos");
		}
		if(sessao.getAttribute("colecaoImovelSubcategoriasRemoviadas") != null){
			sessao.removeAttribute("colecaoImovelSubcategoriasRemoviadas");
		}

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
				url = "/gsan/atualizarImovelWizardAction.do?destino=1&action=atualizarImovelLocalidadeAction";
				break;
			case 2:
				url = "/gsan/atualizarImovelWizardAction.do?destino=2&action=atualizarImovelLocalidadeAction";
				break;
			case 3:
				url = "/gsan/atualizarImovelWizardAction.do?destino=3&action=atualizarImovelClienteAction";
				break;
			case 4:
				url = "/gsan/atualizarImovelWizardAction.do?destino=4&action=atualizarImovelSubCategoriaAction";
				break;
			case 5:
				url = "/gsan/atualizarImovelWizardAction.do?destino=5&action=atualizarImovelCaracteristicasAction";
				break;
			case 6:
				url = "/gsan/atualizarImovelWizardAction.do?destino=6&action=atualizarImovelCaracteristicasAction";
				break;

		}
		return url;
	}
}
