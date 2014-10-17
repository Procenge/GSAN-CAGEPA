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

package gcom.gui.relatorio.cadastro.imovel;

import gcom.gui.ActionServletException;
import gcom.gui.cadastro.imovel.ImovelOutrosCriteriosActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.imovel.EmissaoBoletimCadastro;
import gcom.relatorio.cadastro.imovel.FiltrarRelatorioBoletimCadastroHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Este caso de uso permite a emiss�o de boletins de cadastro
 * [UC0582] Emitir Boletim de Cadastro pelo
 * [UC0164] - Filtrar Im�vel por Outros Crit�rios
 * 
 * @author Anderson Italo
 * @date 20/04/2011
 */

public class GerarEmissaoBoletimCadastroAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		ImovelOutrosCriteriosActionForm form = (ImovelOutrosCriteriosActionForm) actionForm;
		FiltrarRelatorioBoletimCadastroHelper filtro = new FiltrarRelatorioBoletimCadastroHelper();

		// Verifica��o de par�metros informados no filtro

		// Aba Localiza��o

		// Gerencia Regional
		if(Util.verificarIdNaoVazio(form.getIdGerenciaRegional())){

			filtro.setIdGerenciaRegional(form.getIdGerenciaRegional());
			filtro.setInformouDadosLocalizacao("true");
		}

		// Unidade de Neg�cio
		if(Util.verificarIdNaoVazio(form.getUnidadeNegocio())){

			filtro.setIdUnidadeNegocio(form.getUnidadeNegocio());
			filtro.setInformouDadosLocalizacao("true");
		}

		// Inscri��o
		if(!Util.isVazioOuBranco(form.getLocalidadeOrigemID()) && !Util.isVazioOuBranco(form.getLocalidadeDestinoID())){

			filtro.setInformouDadosLocalizacao("true");
			filtro.setIdLocalidadeInicial(form.getLocalidadeOrigemID());
			filtro.setIdLocalidadeFinal(form.getLocalidadeDestinoID());

			// Setor Comercial
			if(!Util.isVazioOuBranco(form.getSetorComercialOrigemCD()) && !Util.isVazioOuBranco(form.getSetorComercialDestinoCD())){

				filtro.setCodigoSetorInicial(form.getSetorComercialOrigemCD());
				filtro.setCodigoSetorFinal(form.getSetorComercialDestinoCD());
			}

			// Quadra
			if(!Util.isVazioOuBranco(form.getQuadraOrigemNM()) && !Util.isVazioOuBranco(form.getQuadraDestinoNM())){

				filtro.setQuadraInicial(form.getQuadraOrigemNM());
				filtro.setQuadraFinal(form.getQuadraDestinoNM());
			}

			// Rota
			if(!Util.isVazioOuBranco(form.getCdRotaInicial()) && !Util.isVazioOuBranco(form.getCdRotaFinal())){

				filtro.setCodigoRotaInicial(form.getCdRotaInicial());
				filtro.setCodigoRotaFinal(form.getCdRotaFinal());
			}

			// Segmento
			if(!Util.isVazioOuBranco(form.getSegmentoInicial()) && !Util.isVazioOuBranco(form.getSegmentoFinal())){

				filtro.setSegmentoInicial(form.getSegmentoInicial());
				filtro.setSegmentoFinal(form.getSegmentoFinal());
			}

			// Lote
			if(!Util.isVazioOuBranco(form.getLoteOrigem()) && !Util.isVazioOuBranco(form.getLoteDestino())){

				filtro.setLoteInicial(form.getLoteOrigem());
				filtro.setLoteFinal(form.getLoteDestino());
			}

			// SubLote
			if(!Util.isVazioOuBranco(form.getSubloteInicial()) && !Util.isVazioOuBranco(form.getSubloteFinal())){

				filtro.setSubLoteInicial(form.getSubloteInicial());
				filtro.setSubLoteFinal(form.getSubloteFinal());
			}
		}

		// Munic�pio
		if(!Util.isVazioOuBranco(form.getIdMunicipio())){
			filtro.setIdMunicipio(form.getIdMunicipio());
		}

		// Bairro
		if(!Util.isVazioOuBranco(form.getIdBairro())){
			filtro.setCodigoBairro(form.getIdBairro());
		}

		// Logradouro
		if(!Util.isVazioOuBranco(form.getIdLogradouro())){
			filtro.setIdLogradouro(form.getIdLogradouro());
		}

		// CEP
		if(!Util.isVazioOuBranco(form.getCEP())){
			filtro.setCep(form.getCEP());
		}

		// Dados do Cliente
		// Cliente
		if(!Util.isVazioOuBranco(form.getIdCliente())){

			filtro.setIdCliente(form.getIdCliente());
			filtro.setInformouDadosCliente("true");
		}

		// Tipo da Rela��o
		if(Util.verificarIdNaoVazio(form.getTipoRelacao())){

			filtro.setIdClienteRelacaoTipo(form.getTipoRelacao());
			filtro.setInformouDadosCliente("true");
		}

		// Tipo do Cliente
		if(Util.verificarIdNaoVazio(form.getDescricao())){

			filtro.setIdClienteTipo(form.getDescricao());
			filtro.setInformouDadosCliente("true");
		}

		// Dados da Liga��o
		// Situa��o da Liga��o de �gua
		if(Util.verificarIdNaoVazio(form.getSituacaoAgua())){

			filtro.setIdSituacaoLigacaoAgua(form.getSituacaoAgua());
			filtro.setInformouDadosLigacao("true");
		}

		// Situa��o da Liga��o de Esgoto
		if(Util.verificarIdNaoVazio(form.getSituacaoLigacaoEsgoto())){

			filtro.setIdSituacaoLigacaoEsgoto(form.getSituacaoLigacaoEsgoto());
			filtro.setInformouDadosLigacao("true");
		}

		// Dados das Caracter�sticas
		// Perfil do Im�vel
		if(Util.verificarIdNaoVazio(form.getPerfilImovel())){

			filtro.setIdImovelPerfil(form.getPerfilImovel());
			filtro.setInformouDadosCaracteristicas("true");
		}

		// Categoria
		if(Util.verificarIdNaoVazio(form.getCategoriaImovel())){

			filtro.setIdCategoria(form.getCategoriaImovel());
			filtro.setInformouDadosCaracteristicas("true");
		}

		// SubCategoria
		if(Util.verificarIdNaoVazio(form.getSubcategoria())){

			filtro.setIdSubCategoria(form.getSubcategoria());
			filtro.setInformouDadosCaracteristicas("true");
		}

		EmissaoBoletimCadastro boletimCadastro = new EmissaoBoletimCadastro((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		boletimCadastro.addParametro("filtrarRelatorioBoletimCadastroHelper", filtro);

		String tipoRelatorio = null;

		try{
			if(ConstantesSistema.BOLETIM_CADASTRAL_MODELO_1.equals(ParametroCadastro.P_LAYOUT_BOLETIM_CADASTRAL.executar())){
				tipoRelatorio = TarefaRelatorio.TIPO_HTML + "";
			}else{
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}

			boletimCadastro.addParametro("tipoFormatoRelatorio", Integer.valueOf(tipoRelatorio));

			retorno = processarExibicaoRelatorio(boletimCadastro, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

			montarPaginaSucesso(httpServletRequest, "Boletim de Cadastro emitido com sucesso.", "Realizar outra Manuten��o",
							"exibirFiltrarUnidadeOrganizacionalAction.do?menu=sim");

		}catch(ControladorException e){
			throw new ActionServletException("Erro ao se consultar o par�metro P_LAYOUT_BOLETIM_CADASTRAL", e);
		}catch(RelatorioVazioException ex){
			throw new ActionServletException("atencao.relatorio.vazio");

		}

		return retorno;
	}
}
