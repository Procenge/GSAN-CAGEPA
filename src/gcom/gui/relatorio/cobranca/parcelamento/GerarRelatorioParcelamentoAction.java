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
 * action responsável pela exibição do relatório de bairro manter
 * 
 * @author Rafael Correa
 * @created
 */
public class GerarRelatorioParcelamentoAction
				extends ExibidorProcessamentoTarefaRelatorio {

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
	 * @author Andre Nishimura
	 * @date 06/01/09
	 *       Validação para caso os objetos referencia (bairro, cep, logradouro) sem FK no banco,
	 *       venham vazios, sejam setados com null
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// ConsultarRegistroAtendimentoActionForm consultarRegistroAtendimentoActionForm =
		// (ConsultarRegistroAtendimentoActionForm) actionForm;

		// Mudar isso quando tiver esquema de segurança
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

		// Coleção dos Débitos a Cobrar do Parcelamento
		Collection colecaoServicosACobrar = (Collection) sessao.getAttribute("colecaoDebitoACobrar");

		Collection colecaoCreditoARealizar = (Collection) sessao.getAttribute("colecaoCreditoARealizar");

		String idParcelamento = (String) sessao.getAttribute("idParcelamento");

		// Pesquisa a unidade do usuário logado
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

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

}
