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

package gcom.gui.relatorio.cobranca.parcelamento;

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.micromedicao.ArquivoTextoRoteiroEmpresa;
import gcom.micromedicao.FiltroArquivoTextoRoteiroEmpresa;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.parcelamento.RelatorioParcelamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de bairro manter
 * 
 * @author Rafael Correa
 * @created
 */
public class GerarRelatorioParcelamentoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @author Andre Nishimura
	 * @date 06/01/09
	 *       Valida��o para caso os objetos referencia (bairro, cep, logradouro) sem FK no banco,
	 *       venham vazios, sejam setados com null
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// ConsultarRegistroAtendimentoActionForm consultarRegistroAtendimentoActionForm =
		// (ConsultarRegistroAtendimentoActionForm) actionForm;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Integer idLogradouro = sistemaParametro.getLogradouro().getId();
		// Busca o logradouro para setar em sistema parametro
		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
		filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, idLogradouro));
		Collection colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());
		if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){
			for(Object object : colecaoLogradouro){
				sistemaParametro.setLogradouro((Logradouro) object);
			}
		}else{
			sistemaParametro.setLogradouro(null);
		}

		if(sistemaParametro.getBairro() != null){
			Integer idBairro = sistemaParametro.getBairro().getId();
			// Busca o bairro para setar em sistema parametro
			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(filtroBairro.ID, idBairro));
			Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());
			if(colecaoBairro != null && !colecaoBairro.isEmpty()){
				for(Object object : colecaoBairro){
					sistemaParametro.setBairro((Bairro) object);
				}
			}else{
				sistemaParametro.setBairro(null);
			}
		}

		if(sistemaParametro.getCep() != null){
			Integer idCep = sistemaParametro.getCep().getCepId();
			// Busca o cep para setar em sistema parametro
			FiltroCep filtroCep = new FiltroCep();
			filtroCep.adicionarParametro(new ParametroSimples(filtroCep.CEPID, idCep));
			Collection colecaoCep = fachada.pesquisar(filtroCep, Cep.class.getName());
			if(colecaoCep != null && !colecaoCep.isEmpty()){
				for(Object object : colecaoCep){
					sistemaParametro.setCep((Cep) object);
				}
			}else{
				sistemaParametro.setCep(null);
			}
		}

		Collection colecaoFaturasEmAberto = null;
		Collection colecaoGuiasPagamento = null;

		if(sessao.getAttribute("colecaoContaValores") != null){

			colecaoFaturasEmAberto = (Collection) sessao.getAttribute("colecaoContaValores");
			colecaoGuiasPagamento = (Collection) sessao.getAttribute("colecaoGuiaPagamentoValores");

		}else{
			colecaoFaturasEmAberto = (Collection) sessao.getAttribute("colecaoContaValoresImovel");
			colecaoGuiasPagamento = (Collection) sessao.getAttribute("colecaoGuiaPagamentoValoresImovel");
		}

		// Cole��o dos D�bitos a Cobrar do Parcelamento
		Collection colecaoServicosACobrar = (Collection) sessao.getAttribute("colecaoDebitoACobrar");

		Collection colecaoCreditoARealizar = (Collection) sessao.getAttribute("colecaoCreditoARealizar");

		String idParcelamento = (String) sessao.getAttribute("idParcelamento");

		// Pesquisa a unidade do usu�rio logado
		UnidadeOrganizacional unidadeUsuario = fachada.pesquisarUnidadeUsuario(usuario.getId());

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioParcelamento relatorioParcelamento = new RelatorioParcelamento((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));
		relatorioParcelamento.addParametro("idParcelamento", new Integer(idParcelamento));
		relatorioParcelamento.addParametro("unidadeUsuario", unidadeUsuario);
		relatorioParcelamento.addParametro("sistemaParametro", sistemaParametro);
		relatorioParcelamento.addParametro("colecaoFaturasEmAberto", colecaoFaturasEmAberto);
		relatorioParcelamento.addParametro("colecaoGuiasPagamento", colecaoGuiasPagamento);
		relatorioParcelamento.addParametro("colecaoServicosACobrar", colecaoServicosACobrar);
		relatorioParcelamento.addParametro("colecaoCreditoARealizar", colecaoCreditoARealizar);
		relatorioParcelamento.addParametro("usuario", usuario);

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioParcelamento.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioParcelamento, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
