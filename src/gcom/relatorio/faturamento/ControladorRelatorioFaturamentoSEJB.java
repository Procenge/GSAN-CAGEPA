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

package gcom.relatorio.faturamento;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ControladorClienteLocal;
import gcom.cadastro.cliente.ControladorClienteLocalHome;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.*;
import gcom.faturamento.bean.*;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.faturamento.conta.Fatura;
import gcom.faturamento.histograma.IRepositorioHistograma;
import gcom.faturamento.histograma.RepositorioHistogramaHBM;
import gcom.seguranca.acesso.ControladorAcessoSEJB;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.log4j.Logger;

/**
 * Foi criador esse controlador para relatorio especificos para faturamento
 * 
 * @author Rafael Pinto
 * @created 16/06/2007
 */

public class ControladorRelatorioFaturamentoSEJB
				implements SessionBean {

	private static final String VOLUME_FATURADO_NAO_MEDIDO = "VOLUME_FATURADO_NAO_MEDIDO";

	private static final String VOLUME_FATURADO_MEDIDO = "VOLUME_FATURADO_MEDIDO";

	private static final String TOTAL_NAO_MEDIDO = "TOTAL_NAO_MEDIDO";

	private static final String TOTAL_MEDIDO = "TOTAL_MEDIDO";

	private static Logger LOGGER = Logger.getLogger(ControladorRelatorioFaturamentoSEJB.class);

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	private IRepositorioFaturamento repositorioFaturamento;

	private IRepositorioHistograma repositorioHistograma;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
		repositorioHistograma = RepositorioHistogramaHBM.getInstancia();

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * Retorna o valor de ControladorClienteLocal
	 * 
	 * @return O valor de ControladorClienteLocal
	 */
	protected ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * [UC0604] Emitir Histograma de Água por Economia
	 * 
	 * @author Rafael Pinto
	 * @date 08/11/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	public Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> pesquisarEmitirHistogramaAguaEconomia(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		Collection<Integer> colecao = filtro.getColecaoTarifa();

		if(colecao != null && !colecao.isEmpty()){

			Iterator itera = colecao.iterator();

			while(itera.hasNext()){
				Integer tarifa = (Integer) itera.next();

				FiltrarEmitirHistogramaAguaEconomiaHelper novoFiltro = new FiltrarEmitirHistogramaAguaEconomiaHelper(filtro);

				FiltrarEmitirHistogramaAguaEconomiaHelper novoFiltroClone = new FiltrarEmitirHistogramaAguaEconomiaHelper(filtro);

				novoFiltro.setTarifa(tarifa);
				novoFiltroClone.setTarifa(tarifa);

				colecaoEmitirHistogramaAguaEconomia.addAll(this.montarSwitchHistogramaAguaEconomia(novoFiltro, novoFiltroClone));
			}
		}
		FiltrarEmitirHistogramaAguaEconomiaHelper filtroClone2 = new FiltrarEmitirHistogramaAguaEconomiaHelper(filtro);

		colecaoEmitirHistogramaAguaEconomia.addAll(this.montarSwitchHistogramaAguaEconomia(filtro, filtroClone2));

		if(Util.isVazioOrNulo(colecaoEmitirHistogramaAguaEconomia)){
			throw new ControladorException("atencao.relatorio.vazio");
		}

		return colecaoEmitirHistogramaAguaEconomia;
	}

	/**
	 * [UC0604] Emitir Histograma de Agua Por Economia
	 * Monta switch
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> montarSwitchHistogramaAguaEconomia(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, FiltrarEmitirHistogramaAguaEconomiaHelper filtroClone)
					throws ControladorException{

		int opcaoTotalizacao = filtro.getOpcaoTotalizacao();

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		switch(opcaoTotalizacao){

			// Estado
			case 1:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEstado(filtro, Boolean.TRUE));
				break;

			// Estado por Gerencia Regional
			case 2:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.TRUE));
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Estado por Unidade Negocio
			case 3:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocioGerenciaRegional(filtro,
								Boolean.TRUE));
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Estado por Elo Polo
			case 4:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPoloUnidadeNegocioGerenciaRegional(filtro,
								Boolean.TRUE));
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Estado por Localidade
			case 5:
				colecaoEmitirHistogramaAguaEconomia.addAll(this
								.emitirHistogramaAguaEconomiaLocalidadeEloPoloUnidadeNegocioGerenciaRegional(filtro, Boolean.TRUE));
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Gerência Regional
			case 6:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.TRUE));
				break;

			// Gerência Regional por Unidade Negocio
			case 7:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocioGerenciaRegional(filtro,
								Boolean.TRUE));
				break;

			// Gerência Regional por Elo
			case 8:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPoloUnidadeNegocioGerenciaRegional(filtro,
								Boolean.TRUE));
				break;

			// Gerência Regional por Localidade
			case 9:
				colecaoEmitirHistogramaAguaEconomia.addAll(this
								.emitirHistogramaAguaEconomiaLocalidadeEloPoloUnidadeNegocioGerenciaRegional(filtro, Boolean.TRUE));
				break;

			// Unidade de Negocio
			case 10:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.TRUE));
				break;

			// Unidade de Negocio por Elo
			case 11:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPoloUnidadeNegocio(filtro, Boolean.TRUE));
				break;

			// Unidade de Negocio por Localidade
			case 12:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidadeEloPoloUnidadeNegocio(filtro,
								Boolean.TRUE));
				break;

			// Elo Polo
			case 13:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.TRUE));
				break;

			// Elo Polo Por Localidade
			case 14:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidadeEloPolo(filtro, Boolean.TRUE));
				break;

			// Elo Polo Por Setor Comercial
			case 15:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaSetorComercialLocalidadeEloPolo(filtro,
								Boolean.TRUE));
				break;

			// Localidade
			case 16:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidade(filtro, Boolean.TRUE));
				break;

			// Localidade Por Setor Comercial
			case 17:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaSetorComercialLocalidade(filtro, Boolean.TRUE));
				break;

			// Localidade Por Quadra
			case 18:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaQuadraSetorComercialLocalidade(filtro,
								Boolean.TRUE));
				break;

			// Setor Comercial
			case 19:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaSetorComercial(filtro, Boolean.TRUE));
				break;

			// Setor Comercial Por Quadra
			case 20:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaQuadraSetorComercial(filtro, Boolean.TRUE));
				break;

			// Quadra
			case 21:
				colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaQuadra(filtro, Boolean.TRUE));
				break;

		}

		return colecaoEmitirHistogramaAguaEconomia;
	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Estado
	 * 
	 * @author Rafael Pinto
	 * @date 15/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaEstado(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
		Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
		Collection colecaoConsumoFaixaCategoria = null;
		EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
		Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
		List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

		Categoria categoria = null;

		FiltroCategoria filtroCategoria = null;
		Collection colecaoConsulta = null;

		Iterator itera = colecaoCategorias.iterator();

		String descricaoOpcaoTotalizacao = this.getControladorUtil().pesquisarParametrosDoSistema().getNomeEstado();

		Long totalGeralEconomiasMedido = new Long("0");
		Long totalGeralVolumeConsumoMedido = new Long("0");
		Long totalGeralVolumeFaturadoMedido = new Long("0");
		BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
		Long totalGeralLigacaoMedido = new Long("0");

		Long totalGeralEconomiasNaoMedido = new Long("0");
		Long totalGeralVolumeConsumoNaoMedido = new Long("0");
		Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
		BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
		Long totalGeralLigacaoNaoMedido = new Long("0");

		int quantidadeCategoriaComValores = 0;

		while(itera.hasNext()){

			String idCategoria = (String) itera.next();

			emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
			emitirHistograma.setDescricaoTarifa(descricaoTarifa);

			listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

			colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

			Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();

			int quantidadeFaixaComValores = 0;
			int limiteSuperiorFaixaAnterior = 0;

			Long totalEconomiasMedido = new Long("0");
			Long totalVolumeConsumoMedido = new Long("0");
			Long totalVolumeFaturadoMedido = new Long("0");
			Long totalLigacaoMedido = new Long("0");
			BigDecimal totalReceitaMedido = BigDecimal.ZERO;

			Long totalEconomiasNaoMedido = new Long("0");
			Long totalVolumeConsumoNaoMedido = new Long("0");
			Long totalVolumeFaturadoNaoMedido = new Long("0");
			Long totalLigacaoNaoMedido = new Long("0");
			BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

			filtroCategoria = new FiltroCategoria();
			filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

			// Recupera Categoria
			colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

			categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

			String descricaoCategoria = categoria.getDescricao();

			filtro.setCategoria(categoria);

			while(iteraFaixas.hasNext()){

				ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

				EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(filtro,
								consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

				emitirHistograma.setDescricaoCategoria(descricaoCategoria);
				emitirHistograma.setIdCategoria(categoria.getId());
				limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

				listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

				totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
				totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
				// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
				// detalhe.getVolumeFaturadoFaixaMedido();
				// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
				totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

				totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
				totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
				// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
				// detalhe.getVolumeFaturadoFaixaNaoMedido();
				// totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
				totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();

				if(detalhe.isExisteHistograma()){
					quantidadeFaixaComValores++;
				}
			}

			if(quantidadeFaixaComValores != 0){
				quantidadeCategoriaComValores++;
			}

			colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
listEmitirHistogramaAguaEconomiaDetalhe,
											filtro.getMesAnoFaturamento(), 1, filtro.getIdFuncionalidadeIniciada());

			for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

				totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
				totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
				totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
				totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

			}

			emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

			emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
			emitirHistograma.setDescricaoFaixa("TOTAL");
			emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

			emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
			emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
			emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
			emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
			emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

			emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
			emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
			emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
			emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
			emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

			colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

			totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
			totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
			totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
			totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
			totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

			totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
			totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
			totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido + emitirHistograma.getTotalVolumeFaturadoNaoMedido();
			totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
			totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

		}

		if(quantidadeCategoriaComValores != 0){

			emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();

			emitirHistograma.setDescricaoTarifa(descricaoTarifa);
			emitirHistograma.setDescricaoCategoria(null);
			emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
			emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

			// Medido(COM HIDROMETRO)
			emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
			emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
			emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
			emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
			emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

			// Não Medido(SEM HIDROMETRO)
			emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
			emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
			emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
			emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
			emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
			emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

			// TOTAL GERAL
			colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);
		}else{
			colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();
		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia - Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 15/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaGerenciaRegional(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setEloPolo(null);
		filtro.setUnidadeNegocio(null);
		filtro.setLocalidade(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		filtro.setTipoGroupBy("histograma.gerenciaRegional.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			GerenciaRegional gerencia = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroGerenciaRegional filtroGerencia = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String IdGerencia = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroGerencia = new FiltroGerenciaRegional();
				filtroGerencia.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, IdGerencia));

				// Recupera Gerencia Regional
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroGerencia, GerenciaRegional.class.getName());

				gerencia = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = gerencia.getId() + " - " + gerencia.getNome();

				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);
					emitirHistograma.setIdGerenciaRegional(gerencia.getId());
					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(IdGerencia);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Unidade de Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.unidadeNegocio.id,histograma.gerenciaRegional.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			UnidadeNegocio unidadeNegocio = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroUnidadeNegocio filtroUnidadeNegocio = null;
			Collection colecaoConsulta = null;

			// Pai da unidade Negocioa(unidade -> gerencia)
			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				GerenciaRegional gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf((arrayNumeracao[1])));

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					filtro.setOpcaoTotalizacao(2);

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setGerenciaRegional(gereAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.FALSE));
				}

				filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, arrayNumeracao[0]));
				filtroUnidadeNegocio.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");

				// Recupera Unidade Negocio
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

				unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = unidadeNegocio.getGerenciaRegional().getId() + "-"
								+ unidadeNegocio.getGerenciaRegional().getNomeAbreviado() + " / " + unidadeNegocio.getId() + "-"
								+ unidadeNegocio.getNome();

				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					if(unidadeNegocio.getGerenciaRegional() != null){

						emitirHistograma.setIdGerenciaRegional(unidadeNegocio.getGerenciaRegional().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				gerenciaAnterior = gerencia.getId();
			}

			filtro.setOpcaoTotalizacao(2);

			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;


	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Elo Polo e Unidade de Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaEloPoloUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.localidadeElo.id," + "histograma.unidadeNegocio.id,histograma.gerenciaRegional.id ");

		filtro.setTipoOrderBy("2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Localidade eloPolo = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade -> gerencia)
			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[1]));

				GerenciaRegional gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[2]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.FALSE));
				}

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					filtro.setOpcaoTotalizacao(2);

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setGerenciaRegional(gereAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					emitirHistograma.setIdGerenciaRegional(eloPolo.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(eloPolo.getId());

					if(eloPolo.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(eloPolo.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				gerenciaAnterior = gerencia.getId();
				unidadeNegocioAnterior = unidadeNegocio.getId();
			}
			// Unidade de Negocio
			filtro.setOpcaoTotalizacao(10);

			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);

			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

			// Gerencia Regional
			filtro.setOpcaoTotalizacao(2);

			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Elo Polo e Unidade de Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaLocalidadeEloPoloUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id,"
						+ "histograma.unidadeNegocio.id,histograma.gerenciaRegional.id ");

		filtro.setTipoOrderBy("4,3,2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Localidade localidade = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade -> gerencia)
			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[2]));

				GerenciaRegional gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[3]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);

					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);

					filtro.setOpcaoTotalizacao(10);
					filtro.setUnidadeNegocio(uniAnterior);

					Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> coll = this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro,
									Boolean.FALSE);

					colecaoEmitirHistogramaAguaEconomia.addAll(coll);

				}

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);

					filtro.setOpcaoTotalizacao(2);
					filtro.setGerenciaRegional(gereAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(localidade.getId());

					if(localidade.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				gerenciaAnterior = gerencia.getId();
				unidadeNegocioAnterior = unidadeNegocio.getId();
				eloPoloAnterior = eloPolo.getId();
			}

			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);

			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));

			// Unidade de Negocio
			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);

			filtro.setOpcaoTotalizacao(10);
			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

			// Gerencia Regional
			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);

			filtro.setOpcaoTotalizacao(2);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaGerenciaRegional(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia - Unidade de Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaUnidadeNegocio(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setEloPolo(null);
		filtro.setGerenciaRegional(null);
		filtro.setLocalidade(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.unidadeNegocio.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			UnidadeNegocio unidadeNegocio = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroUnidadeNegocio filtroUnidadeNegocio = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String idUnidadeNegocio = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, idUnidadeNegocio));
				filtroUnidadeNegocio.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");

				// Recupera Unidade Negocio
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

				unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = unidadeNegocio.getGerenciaRegional().getId() + "-"
								+ unidadeNegocio.getGerenciaRegional().getNomeAbreviado() + " / " + unidadeNegocio.getId() + "-"
								+ unidadeNegocio.getNome();

				filtro.setUnidadeNegocio(unidadeNegocio);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					if(unidadeNegocio.getGerenciaRegional() != null){

						emitirHistograma.setIdGerenciaRegional(unidadeNegocio.getGerenciaRegional().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(idUnidadeNegocio);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Elo Polo e Unidade de Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaEloPoloUnidadeNegocio(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.localidadeElo.id,histograma.unidadeNegocio.id");
		filtro.setTipoOrderBy("2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Localidade eloPolo = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade)
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[1]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.TRUE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);
					emitirHistograma.setIdGerenciaRegional(eloPolo.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(eloPolo.getId());

					if(eloPolo.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(eloPolo.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				unidadeNegocioAnterior = unidadeNegocio.getId();
			}
			// Unidade de Negocio
			filtro.setOpcaoTotalizacao(10);

			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);

			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Localidade ,Elo Polo e Unidade de Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaLocalidadeEloPoloUnidadeNegocio(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id," + "histograma.unidadeNegocio.id");

		filtro.setTipoOrderBy("3,2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Localidade localidade = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade -> gerencia)
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[2]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);

					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);

					filtro.setOpcaoTotalizacao(10);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.TRUE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(localidade.getId());

					if(localidade.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				unidadeNegocioAnterior = unidadeNegocio.getId();
				eloPoloAnterior = eloPolo.getId();
			}

			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);

			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));

			// Unidade de Negocio
			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);

			filtro.setOpcaoTotalizacao(10);
			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;
	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Elo Polo
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaEloPolo(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setGerenciaRegional(null);
		filtro.setUnidadeNegocio(null);
		filtro.setLocalidade(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.localidadeElo.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Localidade eloPolo = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String idEloPolo = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idEloPolo));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

				// Recupera Elo Polo
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				filtro.setEloPolo(eloPolo);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);
					emitirHistograma.setIdGerenciaRegional(eloPolo.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(eloPolo.getId());

					if(eloPolo.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(eloPolo.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(idEloPolo);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Localidade e Elo Polo
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaLocalidadeEloPolo(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id");
		filtro.setTipoOrderBy("1,2");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Localidade localidade = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade -> gerencia)
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);

					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(localidade.getId());

					if(localidade.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				eloPoloAnterior = eloPolo.getId();
			}

			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);

			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Setor Comercial ,Localidade e Elo
	 * 
	 * @author Rafael Pinto
	 * @date 20/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaSetorComercialLocalidadeEloPolo(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.codigoSetorComercial,histograma.localidade.id,histograma.localidadeElo.id");
		filtro.setTipoOrderBy("1,2,3");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			SetorComercial setorComercial = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoConsulta = null;

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[1]));

				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[2]));

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setOpcaoTotalizacao(16);
					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidade(filtro, Boolean.FALSE));
				}

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);

					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, arrayNumeracao[0]));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.gerenciaRegional");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.unidadeNegocio");

				// Recupera Setor Comercial
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getGerenciaRegional().getId() + "-"
								+ setorComercial.getLocalidade().getGerenciaRegional().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getUnidadeNegocio().getId() + "-"
								+ setorComercial.getLocalidade().getUnidadeNegocio().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getLocalidade().getDescricao() + " / "
								+ setorComercial.getLocalidade().getId() + "-" + setorComercial.getLocalidade().getDescricao() + " / "
								+ setorComercial.getCodigo() + "-" + setorComercial.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					Localidade loca = setorComercial.getLocalidade();

					if(loca != null){

						emitirHistograma.setIdLocalidade(loca.getId());

						if(loca.getGerenciaRegional() != null){

							emitirHistograma.setIdGerenciaRegional(loca.getGerenciaRegional().getId());

						}

						if(loca.getLocalidade() != null){

							emitirHistograma.setIdLocalidadeVinculada(loca.getLocalidade().getId());

						}

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				localidadeAnterior = localidade.getId();
				eloPoloAnterior = eloPolo.getId();
			}

			// Localidade
			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setOpcaoTotalizacao(16);
			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidade(filtro, Boolean.FALSE));

			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);

			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaEloPolo(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Setor Comercial ,Localidade
	 * 
	 * @author Rafael Pinto
	 * @date 20/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaSetorComercialLocalidade(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.codigoSetorComercial,histograma.localidade.id");
		filtro.setTipoOrderBy("1,2");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			SetorComercial setorComercial = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoConsulta = null;

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[1]));

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setOpcaoTotalizacao(16);
					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidade(filtro, Boolean.FALSE));
				}

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, arrayNumeracao[0]));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.gerenciaRegional");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.unidadeNegocio");

				// Recupera Setor Comercial
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getGerenciaRegional().getId() + "-"
								+ setorComercial.getLocalidade().getGerenciaRegional().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getUnidadeNegocio().getId() + "-"
								+ setorComercial.getLocalidade().getUnidadeNegocio().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getLocalidade().getDescricao() + " / "
								+ setorComercial.getLocalidade().getId() + "-" + setorComercial.getLocalidade().getDescricao() + " / "
								+ setorComercial.getId() + "-" + setorComercial.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					Localidade loca = setorComercial.getLocalidade();

					if(loca != null){

						emitirHistograma.setIdLocalidade(loca.getId());

						if(loca.getGerenciaRegional() != null){

							emitirHistograma.setIdGerenciaRegional(loca.getGerenciaRegional().getId());

						}

						if(loca.getLocalidade() != null){

							emitirHistograma.setIdLocalidadeVinculada(loca.getLocalidade().getId());

						}

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				localidadeAnterior = localidade.getId();
			}

			// Localidade
			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setOpcaoTotalizacao(16);
			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidade(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Localidade
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaLocalidade(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setSetorComercial(null);
		filtro.setEloPolo(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.localidade.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Localidade localidade = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String idLocalidade = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

				// Recupera Elo Polo
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				filtro.setLocalidade(localidade);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);
					emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(localidade.getId());

					if(localidade.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(idLocalidade);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Quadra e Setor Comercial
	 * 
	 * @author Rafael Pinto
	 * @date 20/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaQuadraSetorComercialLocalidade(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.quadra.id,histograma.setorComercial.id,histograma.localidade.id");
		filtro.setTipoOrderBy("1,2,3");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Quadra quadra = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroQuadra filtroQuadra = null;
			Collection colecaoConsulta = null;

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int setorComercialAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setId(Integer.valueOf(arrayNumeracao[1]));

				Localidade localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[2]));

				if(setorComercialAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					setorComercialAnterior = setorComercial.getId();
				}

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				// Mudou de Setor Comercial
				if(setorComercialAnterior != setorComercial.getId().intValue()){

					SetorComercial setorAnterior = new SetorComercial();
					setorAnterior.setId(setorComercialAnterior);

					filtro.setOpcaoTotalizacao(19);
					filtro.setSetorComercial(setorAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaSetorComercial(filtro, Boolean.FALSE));
				}

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setOpcaoTotalizacao(16);
					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidade(filtro, Boolean.FALSE));
				}

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, arrayNumeracao[0]));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.gerenciaRegional");

				// Recupera Quadra
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				filtro.setQuadra(quadra);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					Localidade loca = quadra.getSetorComercial().getLocalidade();

					if(loca != null){

						emitirHistograma.setIdLocalidade(loca.getId());

						if(loca.getGerenciaRegional() != null){

							emitirHistograma.setIdGerenciaRegional(loca.getGerenciaRegional().getId());

						}

						if(loca.getLocalidade() != null){

							emitirHistograma.setIdLocalidadeVinculada(loca.getLocalidade().getId());

						}

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				localidadeAnterior = localidade.getId();
				setorComercialAnterior = setorComercial.getId();
			}

			// Setor Comercial
			SetorComercial setorAnterior = new SetorComercial();
			setorAnterior.setId(setorComercialAnterior);

			filtro.setOpcaoTotalizacao(19);
			filtro.setSetorComercial(setorAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaSetorComercial(filtro, Boolean.FALSE));

			// Mudou Localidade
			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setOpcaoTotalizacao(16);
			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaLocalidade(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Setor Comercial
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaSetorComercial(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setQuadra(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.setorComercial.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			SetorComercial setorComercial = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String idSetorComercial = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, idSetorComercial));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.gerenciaRegional");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.unidadeNegocio");

				// Recupera Setor Comercial
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getGerenciaRegional().getId() + "-"
								+ setorComercial.getLocalidade().getGerenciaRegional().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getUnidadeNegocio().getId() + "-"
								+ setorComercial.getLocalidade().getUnidadeNegocio().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getLocalidade().getDescricao() + " / "
								+ setorComercial.getLocalidade().getId() + "-" + setorComercial.getLocalidade().getDescricao() + " / "
								+ setorComercial.getId() + "-" + setorComercial.getDescricao();

				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					Localidade loca = setorComercial.getLocalidade();

					if(loca != null){

						emitirHistograma.setIdLocalidade(loca.getId());

						if(loca.getGerenciaRegional() != null){

							emitirHistograma.setIdGerenciaRegional(loca.getGerenciaRegional().getId());

						}

						if(loca.getLocalidade() != null){

							emitirHistograma.setIdLocalidadeVinculada(loca.getLocalidade().getId());

						}

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(idSetorComercial);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaAguaEconomia;


	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Quadra e Setor Comercial
	 * 
	 * @author Rafael Pinto
	 * @date 20/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaQuadraSetorComercial(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.quadra.id,histograma.setorComercial.id");
		filtro.setTipoOrderBy("2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Quadra quadra = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroQuadra filtroQuadra = null;
			Collection colecaoConsulta = null;

			int setorComercialAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setId(Integer.valueOf(arrayNumeracao[1]));

				if(setorComercialAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					setorComercialAnterior = setorComercial.getId();
				}

				// Mudou de Setor Comercial
				if(setorComercialAnterior != setorComercial.getId().intValue()){

					SetorComercial setorAnterior = new SetorComercial();
					setorAnterior.setId(setorComercialAnterior);

					filtro.setOpcaoTotalizacao(19);
					filtro.setSetorComercial(setorAnterior);

					colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaSetorComercial(filtro, Boolean.FALSE));
				}

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, arrayNumeracao[0]));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.gerenciaRegional");

				// Recupera Quadra
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				filtro.setQuadra(quadra);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					Localidade loca = quadra.getSetorComercial().getLocalidade();

					if(loca != null){

						emitirHistograma.setIdLocalidade(loca.getId());

						if(loca.getGerenciaRegional() != null){

							emitirHistograma.setIdGerenciaRegional(loca.getGerenciaRegional().getId());

						}

						if(loca.getLocalidade() != null){

							emitirHistograma.setIdLocalidadeVinculada(loca.getLocalidade().getId());

						}

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

				setorComercialAnterior = setorComercial.getId();
			}

			// Setor Comercial
			SetorComercial setorAnterior = new SetorComercial();
			setorAnterior.setId(setorComercialAnterior);

			filtro.setOpcaoTotalizacao(19);
			filtro.setSetorComercial(setorAnterior);

			colecaoEmitirHistogramaAguaEconomia.addAll(this.emitirHistogramaAguaEconomiaSetorComercial(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaAguaEconomia;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Quadra
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<EmitirHistogramaAguaEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaAguaEconomiaQuadra(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaAguaEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoTarifa = "TOTALIZADOR";
		if(filtro.getTarifa() != null){

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

			Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

			descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
		}

		filtro.setTipoGroupBy("histograma.quadra.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaAguaEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listEmitirHistogramaAguaEconomiaDetalhe = null;

			Quadra quadra = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroQuadra filtroQuadra = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String idQuadra = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, idQuadra));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.gerenciaRegional");

				// Recupera Quadra
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				filtro.setQuadra(quadra);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listEmitirHistogramaAguaEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaAguaEconomiaDetalheHelper(
										filtro,
										consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						emitirHistograma.setIdCategoria(categoria.getId());
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listEmitirHistogramaAguaEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaAguaEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listEmitirHistogramaAguaEconomiaDetalhe, filtro.getMesAnoFaturamento(), 1,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaAguaEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaAguaEconomiaDetalhe);

					Localidade loca = quadra.getSetorComercial().getLocalidade();

					if(loca != null){

						emitirHistograma.setIdLocalidade(loca.getId());

						if(loca.getGerenciaRegional() != null){

							emitirHistograma.setIdGerenciaRegional(loca.getGerenciaRegional().getId());

						}

						if(loca.getLocalidade() != null){

							emitirHistograma.setIdLocalidadeVinculada(loca.getLocalidade().getId());

						}

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

					colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(idQuadra);

				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaAguaEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaAguaEconomia;


	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Monta o objeto EmitirHistogramaAguaEconomiaDetalheHelper
	 * 
	 * @author Rafael Pinto
	 * @date 19/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @param ConsumoFaixaCategoria
	 * @return EmitirHistogramaAguaEconomiaDetalheHelper
	 * @throws ControladorException
	 */

	private EmitirHistogramaAguaEsgotoEconomiaDetalheHelper montarEmitirHistogramaAguaEconomiaDetalheHelper(
					FiltrarEmitirHistogramaAguaEconomiaHelper filtro, ConsumoFaixaCategoria consumoFaixaCategoria,
					int limiteSuperiorFaixaAnterior)
					throws ControladorException{

		EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = new EmitirHistogramaAguaEsgotoEconomiaDetalheHelper();

		detalhe.setFaixa(consumoFaixaCategoria);

		BigDecimal consumoMedio = BigDecimal.ZERO;
		BigDecimal consumoExcedente = BigDecimal.ZERO;

		filtro.setConsumoFaixaCategoria(consumoFaixaCategoria);

		detalhe.setDescricaoFaixa(consumoFaixaCategoria.getNumeroFaixaInicio() + " a " + consumoFaixaCategoria.getNumeroFaixaFim());

		// Pesquisa com indicador de Hidrometro SIM
		filtro.setMedicao(ConstantesSistema.INDICADOR_USO_ATIVO);

		Object[] valoresSomatorio = null;
		boolean temMedido = false;
		boolean temNaoMedido = false;

		try{
			valoresSomatorio = this.repositorioFaturamento.pesquisarEmitirHistogramaAguaEconomia(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(valoresSomatorio != null){

			detalhe.setEconomiasMedido((Long) valoresSomatorio[2]);
			detalhe.setVolumeConsumoFaixaMedido((Long) valoresSomatorio[3]);
			detalhe.setVolumeFaturadoFaixaMedido((Long) valoresSomatorio[4]);
			detalhe.setReceitaMedido((BigDecimal) valoresSomatorio[5]);
			detalhe.setLigacoesMedido((Long) valoresSomatorio[6]);

			if(detalhe.getEconomiasMedido() > 0){
				consumoMedio = new BigDecimal(detalhe.getVolumeConsumoFaixaMedido()).divide(new BigDecimal(detalhe.getEconomiasMedido()),
								4, BigDecimal.ROUND_HALF_UP);

			}

			detalhe.setConsumoMedioMedido(consumoMedio);

			if(detalhe.getConsumoMedioMedido() != null && limiteSuperiorFaixaAnterior > 0){
				consumoExcedente = detalhe.getConsumoMedioMedido().subtract(new BigDecimal(limiteSuperiorFaixaAnterior));
			}

			detalhe.setConsumoExcedenteMedido(consumoExcedente);
			temMedido = true;
		}else{
			detalhe.setConsumoMedioMedido(BigDecimal.ZERO);
		}

		// Pesquisa com indicador de Hidrometro Nao
		filtro.setMedicao(ConstantesSistema.INDICADOR_USO_DESATIVO);

		try{
			valoresSomatorio = this.repositorioFaturamento.pesquisarEmitirHistogramaAguaEconomia(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(valoresSomatorio != null){

			detalhe.setEconomiasNaoMedido((Long) valoresSomatorio[2]);
			detalhe.setVolumeConsumoFaixaNaoMedido((Long) valoresSomatorio[3]);
			detalhe.setVolumeFaturadoFaixaNaoMedido((Long) valoresSomatorio[4]);
			detalhe.setReceitaNaoMedido((BigDecimal) valoresSomatorio[5]);
			detalhe.setLigacoesNaoMedido((Long) valoresSomatorio[6]);

			if(detalhe.getEconomiasNaoMedido() != null && detalhe.getEconomiasNaoMedido() > 0){
				consumoMedio = new BigDecimal(detalhe.getVolumeConsumoFaixaNaoMedido()).divide(
								new BigDecimal(detalhe.getEconomiasNaoMedido()), 4, BigDecimal.ROUND_HALF_UP);
			}

			detalhe.setConsumoMedioNaoMedido(consumoMedio);

			if(limiteSuperiorFaixaAnterior > 0){
				consumoExcedente = detalhe.getConsumoMedioNaoMedido().subtract(new BigDecimal(limiteSuperiorFaixaAnterior));
			}
			detalhe.setConsumoExcedenteNaoMedido(consumoExcedente);

			temNaoMedido = true;

		}else{
			detalhe.setConsumoMedioNaoMedido(BigDecimal.ZERO);
		}

		// Caso tenha algum valor então exite histograma para adcionar na colecao
		detalhe.setExisteHistograma(temMedido || temNaoMedido);

		return detalhe;

	}

	/**
	 * [UC0605] Emitir Histograma de Água Por Economia
	 * Agrupa as chaves para totalizacao
	 * 
	 * @author Rafael Pinto
	 * @date 18/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return LinkedHashMap
	 * @throws ControladorException
	 */
	private LinkedHashMap montarEmitirHistogramaAguaEconomiaAgruparChaves(FiltrarEmitirHistogramaAguaEconomiaHelper filtro)
					throws ControladorException{

		LinkedHashMap hashMapTotalizacao = new LinkedHashMap();
		Collection<Object[]> colecao = null;

		try{

			// filtro.setMedicao(ConstantesSistema.INDICADOR_USO_ATIVO);

			colecao = this.repositorioFaturamento.pesquisarEmitirHistogramaAguaEconomiaChavesAgrupadas(filtro);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecao != null && !colecao.isEmpty()){

			Iterator iterator = colecao.iterator();

			while(iterator.hasNext()){

				String tipoTotalizacao = "0";

				int chaveQuadra = 0;
				int chaveSetorComercial = 0;
				int chaveLocalidade = 0;
				int chaveElo = 0;
				int chaveUnidade = 0;
				int chaveGerencia = 0;

				if(filtro.getOpcaoTotalizacao() == 2 || filtro.getOpcaoTotalizacao() == 6){

					chaveGerencia = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveGerencia;

				}else if(filtro.getOpcaoTotalizacao() == 3 || filtro.getOpcaoTotalizacao() == 7){

					Object[] objeto = (Object[]) iterator.next();

					chaveUnidade = (Integer) objeto[0];
					chaveGerencia = (Integer) objeto[1];

					tipoTotalizacao = chaveUnidade + ";" + chaveGerencia;

				}else if(filtro.getOpcaoTotalizacao() == 4 || filtro.getOpcaoTotalizacao() == 8){

					Object[] objeto = (Object[]) iterator.next();

					chaveElo = (Integer) objeto[0];
					chaveUnidade = (Integer) objeto[1];
					chaveGerencia = (Integer) objeto[2];

					tipoTotalizacao = chaveElo + ";" + chaveUnidade + ";" + chaveGerencia;

				}else if(filtro.getOpcaoTotalizacao() == 5 || filtro.getOpcaoTotalizacao() == 9){

					Object[] objeto = (Object[]) iterator.next();

					chaveLocalidade = (Integer) objeto[0];
					chaveElo = (Integer) objeto[1];
					chaveUnidade = (Integer) objeto[2];
					chaveGerencia = (Integer) objeto[3];

					tipoTotalizacao = chaveLocalidade + ";" + chaveElo + ";" + chaveUnidade + ";" + chaveGerencia;

				}else if(filtro.getOpcaoTotalizacao() == 10){

					chaveUnidade = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveUnidade;

				}else if(filtro.getOpcaoTotalizacao() == 11){

					Object[] objeto = (Object[]) iterator.next();

					chaveElo = (Integer) objeto[0];
					chaveUnidade = (Integer) objeto[1];

					tipoTotalizacao = chaveElo + ";" + chaveUnidade;

				}else if(filtro.getOpcaoTotalizacao() == 12){

					Object[] objeto = (Object[]) iterator.next();

					chaveLocalidade = (Integer) objeto[0];
					chaveElo = (Integer) objeto[1];
					chaveUnidade = (Integer) objeto[2];

					tipoTotalizacao = chaveLocalidade + ";" + chaveElo + ";" + chaveUnidade;

				}else if(filtro.getOpcaoTotalizacao() == 13){

					chaveElo = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveElo;

				}else if(filtro.getOpcaoTotalizacao() == 14){

					Object[] objeto = (Object[]) iterator.next();

					chaveLocalidade = (Integer) objeto[0];
					chaveElo = (Integer) objeto[1];

					tipoTotalizacao = chaveLocalidade + ";" + chaveElo;

				}else if(filtro.getOpcaoTotalizacao() == 15){

					Object[] objeto = (Object[]) iterator.next();

					chaveSetorComercial = (Integer) objeto[0];
					chaveLocalidade = (Integer) objeto[1];
					chaveElo = (Integer) objeto[2];

					tipoTotalizacao = chaveSetorComercial + ";" + chaveLocalidade + ";" + chaveElo;

				}else if(filtro.getOpcaoTotalizacao() == 16){

					chaveLocalidade = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveLocalidade;

				}else if(filtro.getOpcaoTotalizacao() == 17){
					Object[] objeto = (Object[]) iterator.next();

					chaveSetorComercial = (Integer) objeto[0];
					chaveLocalidade = (Integer) objeto[1];

					tipoTotalizacao = chaveSetorComercial + ";" + chaveLocalidade;

				}else if(filtro.getOpcaoTotalizacao() == 18){
					Object[] objeto = (Object[]) iterator.next();

					chaveQuadra = (Integer) objeto[0];
					chaveSetorComercial = (Integer) objeto[1];
					chaveLocalidade = (Integer) objeto[2];

					tipoTotalizacao = chaveQuadra + ";" + chaveSetorComercial + ";" + chaveLocalidade;

				}else if(filtro.getOpcaoTotalizacao() == 19){

					chaveSetorComercial = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveSetorComercial;

				}else if(filtro.getOpcaoTotalizacao() == 20){

					Object[] objeto = (Object[]) iterator.next();

					chaveQuadra = (Integer) objeto[0];
					chaveSetorComercial = (Integer) objeto[1];

					tipoTotalizacao = chaveQuadra + ";" + chaveSetorComercial;

				}else if(filtro.getOpcaoTotalizacao() == 21){

					chaveQuadra = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveQuadra;

				}

				EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();

				hashMapTotalizacao.put(tipoTotalizacao, emitirHistograma);
			}
		}

		return hashMapTotalizacao;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	public Collection<EmitirHistogramaEsgotoHelper> pesquisarEmitirHistogramaEsgoto(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		int opcaoTotalizacao = filtro.getOpcaoTotalizacao();

		FiltrarEmitirHistogramaEsgotoHelper filtroClone = new FiltrarEmitirHistogramaEsgotoHelper(filtro);

		switch(opcaoTotalizacao){

			// Estado
			case 1:

				colecaoEmitirHistogramaEsgoto = this.pesquisarEmitirHistogramaEsgotoEstado(filtro);
				break;

			// Estado por Gerencia Regional
			case 2:

				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoGerenciaRegional(filtro);
				colecaoEmitirHistogramaEsgoto.addAll(this.pesquisarEmitirHistogramaEsgotoEstado(filtroClone));
				break;

			// Estado por Unidade Negocio
			case 3:

				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoUnidadeNegocioGerenciaRegional(filtro);
				colecaoEmitirHistogramaEsgoto.addAll(this.pesquisarEmitirHistogramaEsgotoEstado(filtroClone));
				break;

			// Estado por Elo Polo
			case 4:

				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoEloUnidadeNegocioGerenciaRegional(filtro);
				colecaoEmitirHistogramaEsgoto.addAll(this.pesquisarEmitirHistogramaEsgotoEstado(filtroClone));
				break;

			// Estado por Localidade
			case 5:

				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoLocalidadeEloUnidadeNegocioGerenciaRegional(filtro);
				colecaoEmitirHistogramaEsgoto.addAll(this.pesquisarEmitirHistogramaEsgotoEstado(filtroClone));
				break;

			// Gerência Regional
			case 6:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoGerenciaRegional(filtro);
				break;

			// Gerência Regional por Unidade Negocio
			case 7:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoUnidadeNegocioGerenciaRegional(filtro);
				break;

			// Gerência Regional por Elo
			case 8:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoEloUnidadeNegocioGerenciaRegional(filtro);
				break;

			// Gerência Regional por Localidade
			case 9:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoLocalidadeEloUnidadeNegocioGerenciaRegional(filtro);
				break;

			// Unidade de Negocio
			case 10:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoUnidadeNegocio(filtro);
				break;

			// Unidade de Negocio por Elo
			case 11:

				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoEloUnidadeNegocio(filtro);
				break;

			// Unidade de Negocio por Localidade
			case 12:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoLocalidadeEloUnidadeNegocio(filtro);
				break;

			// Elo Polo
			case 13:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoElo(filtro);
				break;

			// Elo Polo Por Localidade
			case 14:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoLocalidadeElo(filtro);
				break;

			// Elo Polo Por Setor Comercial
			case 15:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoSetorComercialLocalidadeElo(filtro);
				break;

			// Localidade
			case 16:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoLocalidade(filtro);
				break;

			// Localidade Por Setor Comercial
			case 17:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoSetorComercialLocalidade(filtro);
				break;

			// Localidade Por Quadra
			case 18:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoQuadraLocalidade(filtro);
				break;

			// Setor Comercial
			case 19:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoSetorComercial(filtro);
				break;

			// Setor Comercial Por Quadra
			case 20:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoQuadraSetorComercial(filtro);
				break;

			// Quadra
			case 21:
				colecaoEmitirHistogramaEsgoto = this.emitirHistogramaEsgotoQuadra(filtro);
				break;

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto - ESTADO
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> pesquisarEmitirHistogramaEsgotoEstado(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		filtro.setOpcaoTotalizacao(1);

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoOpcaoTotalizacao = this.getControladorUtil().pesquisarParametrosDoSistema().getNomeEstado();

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String estado = (String) iter.next();

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(estado);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

			}
		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto - Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoGerenciaRegional(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.gerenciaRegional.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		filtro.setEloPolo(null);
		filtro.setUnidadeNegocio(null);
		filtro.setLocalidade(null);
		filtro.setConsumoFaixaLigacao(null);

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			FiltroGerenciaRegional filtroGerencia = null;
			Collection colecaoGerencia = null;
			GerenciaRegional gerenciaRegional = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String gerencia = (String) iter.next();

				gerenciaRegional = new GerenciaRegional();
				gerenciaRegional.setId(Integer.valueOf(gerencia));

				filtro.setGerenciaRegional(gerenciaRegional);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(gerencia);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroGerencia = new FiltroGerenciaRegional();
				filtroGerencia.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, gerencia));

				// Recupera Gerencia Regional
				colecaoGerencia = this.getControladorUtil().pesquisar(filtroGerencia, GerenciaRegional.class.getName());

				gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerencia);

				String descricaoOpcaoTotalizacao = gerenciaRegional.getId() + " - " + gerenciaRegional.getNome();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

			}

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Unidade Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaEsgotoHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.unidadeNegocio.id,histograma.gerenciaRegional.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			FiltroUnidadeNegocio filtroUnidade = null;
			Collection colecaoUnidade = null;
			UnidadeNegocio unidade = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[0]));

				GerenciaRegional gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[1]));

				filtro.setUnidadeNegocio(unidadeNegocio);

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					filtro.setOpcaoTotalizacao(2);

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setGerenciaRegional(gereAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoGerenciaRegional(filtro));
				}

				filtro.setOpcaoTotalizacao(3);
				filtro.setGerenciaRegional(gerencia);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroUnidade = new FiltroUnidadeNegocio();
				filtroUnidade.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, unidadeNegocio.getId()));

				filtroUnidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");

				// Recupera Unidade Negocio
				colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidade, UnidadeNegocio.class.getName());

				unidade = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoUnidade);

				String descricaoOpcaoTotalizacao = unidade.getGerenciaRegional().getId() + "-"
								+ unidade.getGerenciaRegional().getNomeAbreviado() + " / " + unidade.getId() + " - " + unidade.getNome();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				filtro.setMedicao(indicadorMedicao);

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				gerenciaAnterior = gerencia.getId();

			}

			filtro.setOpcaoTotalizacao(2);

			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoGerenciaRegional(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Elo e Unidade Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoEloUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaEsgotoHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.localidadeElo.id," + "histograma.unidadeNegocio.id,histograma.gerenciaRegional.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoLocalidade = null;

			Localidade eloPolo = null;
			UnidadeNegocio unidadeNegocio = null;
			GerenciaRegional gerencia = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[0]));

				unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[1]));

				gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[2]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));
				}

				filtro.setMedicao(indicadorMedicao);

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setGerenciaRegional(gereAnterior);

					filtro.setOpcaoTotalizacao(2);
					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoGerenciaRegional(filtro));
				}

				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);
				filtro.setMedicao(indicadorMedicao);
				filtro.setOpcaoTotalizacao(4);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, eloPolo.getId()));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoLocalidade = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				filtro.setMedicao(indicadorMedicao);

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				gerenciaAnterior = gerencia.getId();
				unidadeNegocioAnterior = unidadeNegocio.getId();

			}

			filtro.setMedicao(indicadorMedicao);

			filtro.setOpcaoTotalizacao(10);
			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);
			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));

			filtro.setMedicao(indicadorMedicao);

			filtro.setOpcaoTotalizacao(2);
			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoGerenciaRegional(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Total Geral
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 *            ,Collection<Object[]>
	 * @return LinkedHashMap
	 * @throws ControladorException
	 */
	private LinkedHashMap montarEmitirHistogramaEsgotoTotalGeral(FiltrarEmitirHistogramaEsgotoHelper filtro, Collection<Object[]> colecao)
					throws ControladorException{

		LinkedHashMap hashMapTotalizacao = new LinkedHashMap();

		if(colecao != null && !colecao.isEmpty()){

			Iterator iterator = colecao.iterator();

			Collection<EmitirHistogramaEsgotoDetalheHelper> colecaoEmitirHistogramaEsgotoDetalhe = null;

			while(iterator.hasNext()){
				Object[] objeto = (Object[]) iterator.next();

				EmitirHistogramaEsgotoDetalheHelper detalhe = new EmitirHistogramaEsgotoDetalheHelper();

				detalhe.setDescricaoCategoria((String) objeto[1]);

				if(objeto[2] != null){
					Number quantidadeLigacoes = (Number) objeto[2];
					detalhe.setQuantidadeLigacoes(quantidadeLigacoes.intValue());
				}

				if(objeto[3] != null){
					Number quantidadeEconomias = (Number) objeto[3];
					detalhe.setQuantidadeEconomias(quantidadeEconomias.intValue());
				}

				detalhe.setValorFaturado((BigDecimal) objeto[4]);

				String tipoTotalizacao = "0";

				GerenciaRegional gerencia = null;
				UnidadeNegocio unidadeNegocio = null;
				Localidade eloPolo = null;
				Localidade localidade = null;
				SetorComercial setorComercial = null;
				Quadra quadra = null;

				int chaveQuadra = 0;
				int chaveSetorComercial = 0;
				int chaveLocalidade = 0;
				int chaveElo = 0;
				int chaveUnidade = 0;
				int chaveGerencia = 0;

				if(filtro.getOpcaoTotalizacao() == 2 || filtro.getOpcaoTotalizacao() == 6){

					tipoTotalizacao = ((Integer) objeto[5]).toString();

					gerencia = new GerenciaRegional();
					gerencia.setId(Integer.valueOf(tipoTotalizacao));

					filtro.setGerenciaRegional(gerencia);

				}else if(filtro.getOpcaoTotalizacao() == 3 || filtro.getOpcaoTotalizacao() == 7){

					chaveUnidade = (Integer) objeto[5];
					chaveGerencia = (Integer) objeto[6];

					tipoTotalizacao = chaveUnidade + ";" + chaveGerencia;

					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(chaveUnidade);
					filtro.setUnidadeNegocio(unidadeNegocio);

					gerencia = new GerenciaRegional();
					gerencia.setId(chaveGerencia);
					filtro.setGerenciaRegional(gerencia);

				}else if(filtro.getOpcaoTotalizacao() == 4 || filtro.getOpcaoTotalizacao() == 8){

					chaveElo = (Integer) objeto[5];
					chaveUnidade = (Integer) objeto[6];
					chaveGerencia = (Integer) objeto[7];

					tipoTotalizacao = chaveElo + ";" + chaveUnidade + ";" + chaveGerencia;

					eloPolo = new Localidade();
					eloPolo.setId(chaveElo);
					filtro.setEloPolo(eloPolo);

					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(chaveUnidade);
					filtro.setUnidadeNegocio(unidadeNegocio);

					gerencia = new GerenciaRegional();
					gerencia.setId(chaveGerencia);
					filtro.setGerenciaRegional(gerencia);

				}else if(filtro.getOpcaoTotalizacao() == 5 || filtro.getOpcaoTotalizacao() == 9){

					chaveLocalidade = (Integer) objeto[5];
					chaveElo = (Integer) objeto[6];
					chaveUnidade = (Integer) objeto[7];
					chaveGerencia = (Integer) objeto[8];

					tipoTotalizacao = chaveLocalidade + ";" + chaveElo + ";" + chaveUnidade + ";" + chaveGerencia;

					localidade = new Localidade();
					localidade.setId(chaveLocalidade);
					filtro.setLocalidade(localidade);

					eloPolo = new Localidade();
					eloPolo.setId(chaveElo);
					filtro.setEloPolo(eloPolo);

					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(chaveUnidade);
					filtro.setUnidadeNegocio(unidadeNegocio);

					gerencia = new GerenciaRegional();
					gerencia.setId(chaveGerencia);
					filtro.setGerenciaRegional(gerencia);

				}else if(filtro.getOpcaoTotalizacao() == 10){

					chaveUnidade = (Integer) objeto[5];
					tipoTotalizacao = "" + chaveUnidade;

					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(chaveUnidade);
					filtro.setUnidadeNegocio(unidadeNegocio);

				}else if(filtro.getOpcaoTotalizacao() == 11){

					chaveElo = (Integer) objeto[5];
					chaveUnidade = (Integer) objeto[6];

					tipoTotalizacao = chaveElo + ";" + chaveUnidade;

					eloPolo = new Localidade();
					eloPolo.setId(chaveElo);
					filtro.setEloPolo(eloPolo);

					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(chaveUnidade);
					filtro.setUnidadeNegocio(unidadeNegocio);

				}else if(filtro.getOpcaoTotalizacao() == 12){

					chaveLocalidade = (Integer) objeto[5];
					chaveElo = (Integer) objeto[6];
					chaveUnidade = (Integer) objeto[7];

					tipoTotalizacao = chaveLocalidade + ";" + chaveElo + ";" + chaveUnidade;

					localidade = new Localidade();
					localidade.setId(chaveLocalidade);
					filtro.setLocalidade(localidade);

					eloPolo = new Localidade();
					eloPolo.setId(chaveElo);
					filtro.setEloPolo(eloPolo);

					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(chaveUnidade);
					filtro.setUnidadeNegocio(unidadeNegocio);

				}else if(filtro.getOpcaoTotalizacao() == 13){

					chaveElo = (Integer) objeto[5];
					tipoTotalizacao = "" + chaveElo;

					eloPolo = new Localidade();
					eloPolo.setId(chaveElo);
					filtro.setEloPolo(eloPolo);

				}else if(filtro.getOpcaoTotalizacao() == 14){

					chaveLocalidade = (Integer) objeto[5];
					chaveElo = (Integer) objeto[6];

					tipoTotalizacao = chaveLocalidade + ";" + chaveElo;

					localidade = new Localidade();
					localidade.setId(chaveLocalidade);
					filtro.setLocalidade(localidade);

					eloPolo = new Localidade();
					eloPolo.setId(chaveElo);
					filtro.setEloPolo(eloPolo);

				}else if(filtro.getOpcaoTotalizacao() == 15){

					chaveSetorComercial = (Integer) objeto[5];
					chaveLocalidade = (Integer) objeto[6];
					chaveElo = (Integer) objeto[7];

					tipoTotalizacao = chaveSetorComercial + ";" + chaveLocalidade + ";" + chaveElo;

					filtro.setCodigoSetorComercial(chaveSetorComercial);

					localidade = new Localidade();
					localidade.setId(chaveLocalidade);
					filtro.setLocalidade(localidade);

					eloPolo = new Localidade();
					eloPolo.setId(chaveElo);
					filtro.setEloPolo(eloPolo);

				}else if(filtro.getOpcaoTotalizacao() == 16){

					chaveLocalidade = (Integer) objeto[5];
					tipoTotalizacao = "" + chaveLocalidade;

					localidade = new Localidade();
					localidade.setId(chaveLocalidade);
					filtro.setLocalidade(localidade);

				}else if(filtro.getOpcaoTotalizacao() == 17){

					chaveSetorComercial = (Integer) objeto[5];
					chaveLocalidade = (Integer) objeto[6];

					tipoTotalizacao = chaveSetorComercial + ";" + chaveLocalidade;

					filtro.setCodigoSetorComercial(chaveSetorComercial);

					localidade = new Localidade();
					localidade.setId(chaveLocalidade);
					filtro.setLocalidade(localidade);

				}else if(filtro.getOpcaoTotalizacao() == 18){

					chaveQuadra = (Integer) objeto[5];
					chaveSetorComercial = (Integer) objeto[6];
					chaveLocalidade = (Integer) objeto[7];

					tipoTotalizacao = chaveQuadra + ";" + chaveSetorComercial + ";" + chaveLocalidade;

					filtro.setNumeroQuadra(chaveQuadra);

					setorComercial = new SetorComercial();
					setorComercial.setId(chaveSetorComercial);
					filtro.setSetorComercial(setorComercial);

					localidade = new Localidade();
					localidade.setId(chaveLocalidade);
					filtro.setLocalidade(localidade);

				}else if(filtro.getOpcaoTotalizacao() == 19){

					chaveSetorComercial = (Integer) objeto[5];
					tipoTotalizacao = "" + chaveSetorComercial;

					setorComercial = new SetorComercial();
					setorComercial.setId(chaveSetorComercial);

					filtro.setSetorComercial(setorComercial);

				}else if(filtro.getOpcaoTotalizacao() == 20){

					chaveQuadra = (Integer) objeto[5];
					chaveSetorComercial = (Integer) objeto[6];

					tipoTotalizacao = chaveQuadra + ";" + chaveSetorComercial;

					filtro.setNumeroQuadra(chaveQuadra);

					setorComercial = new SetorComercial();
					setorComercial.setId(chaveSetorComercial);
					filtro.setSetorComercial(setorComercial);

				}else if(filtro.getOpcaoTotalizacao() == 21){

					chaveQuadra = (Integer) objeto[5];
					tipoTotalizacao = "" + chaveQuadra;

					quadra = new Quadra();
					quadra.setId(chaveQuadra);

					filtro.setQuadra(quadra);

				}

				try{
					Object[] somatorio = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoTotalGeral(filtro, null);

					Categoria categ = new Categoria();
					categ.setId((Integer) objeto[0]);

					Integer quantidadeVolumeMedido = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoVolumeConsumo(filtro,
									ConstantesSistema.INDICADOR_USO_ATIVO, categ, null);

					Integer quantidadeVolumeEstimado = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoVolumeConsumo(filtro,
									ConstantesSistema.INDICADOR_USO_DESATIVO, categ, null);

					if(quantidadeVolumeMedido != null){
						detalhe.setQuantidadeVolumeMedido(quantidadeVolumeMedido);
					}

					if(quantidadeVolumeEstimado != null){
						detalhe.setQuantidadeVolumeEstimado(quantidadeVolumeEstimado);
					}

					BigDecimal percentualParcialLigacao = BigDecimal.ZERO;
					BigDecimal percentualParcialFaturamento = new BigDecimal(0.0);
					BigDecimal percentualParcialConsumo = BigDecimal.ZERO;

					if(((Number) somatorio[0]).intValue() != 0){

						percentualParcialLigacao = Util.calcularPercentualBigDecimal("" + detalhe.getQuantidadeLigacoes(), ""
										+ somatorio[0]);

					}

					if(((Number) somatorio[1]).intValue() != 0){

						percentualParcialConsumo = Util.calcularPercentualBigDecimal("" + detalhe.getQuantidadeVolumeTotal(), ""
										+ somatorio[1]);

					}

					if(((BigDecimal) somatorio[2]).compareTo(new BigDecimal(0.0)) > 0){

						percentualParcialFaturamento = Util.calcularPercentualBigDecimal(detalhe.getValorFaturado(), new BigDecimal(""
										+ somatorio[2]));
					}

					detalhe.setPercentualParcialLigacao(percentualParcialLigacao.doubleValue());
					detalhe.setPercentualParcialConsumo(percentualParcialConsumo.doubleValue());
					detalhe.setPercentualParcialFaturamento(percentualParcialFaturamento.doubleValue());

				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				if(hashMapTotalizacao.get(tipoTotalizacao) != null){

					EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelper = (EmitirHistogramaEsgotoHelper) hashMapTotalizacao
									.get(tipoTotalizacao);

					int totalLigacoes = emitirHistogramaEsgotoHelper.getTotalQuantidadeLigacoes();
					int totalEconomias = emitirHistogramaEsgotoHelper.getTotalQuantidadeEconomias();
					int volumeEstimado = emitirHistogramaEsgotoHelper.getTotalQuantidadeVolumeEstimado();
					int volumeMedido = emitirHistogramaEsgotoHelper.getTotalQuantidadeVolumeMedido();

					BigDecimal totalValorFaturado = emitirHistogramaEsgotoHelper.getTotalValorFaturado();

					emitirHistogramaEsgotoHelper.setTotalQuantidadeLigacoes(totalLigacoes + detalhe.getQuantidadeLigacoes());
					emitirHistogramaEsgotoHelper.setTotalQuantidadeEconomias(totalEconomias + detalhe.getQuantidadeEconomias());
					emitirHistogramaEsgotoHelper.setTotalValorFaturado(totalValorFaturado.add(detalhe.getValorFaturado()));

					emitirHistogramaEsgotoHelper.setTotalQuantidadeVolumeMedido(volumeMedido + detalhe.getQuantidadeVolumeMedido());
					emitirHistogramaEsgotoHelper.setTotalQuantidadeVolumeEstimado(volumeEstimado + detalhe.getQuantidadeVolumeEstimado());

					colecaoEmitirHistogramaEsgotoDetalhe = emitirHistogramaEsgotoHelper.getColecaoEmitirHistogramaEsgotoDetalhe();

					colecaoEmitirHistogramaEsgotoDetalhe.add(detalhe);
					emitirHistogramaEsgotoHelper.setColecaoEmitirHistogramaEsgotoDetalhe(colecaoEmitirHistogramaEsgotoDetalhe);

					hashMapTotalizacao.put(tipoTotalizacao, emitirHistogramaEsgotoHelper);

				}else{
					EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelper = new EmitirHistogramaEsgotoHelper();
					emitirHistogramaEsgotoHelper.setDescricaoTitulo("TOTAL GERAL");

					emitirHistogramaEsgotoHelper.setTotalQuantidadeLigacoes(detalhe.getQuantidadeLigacoes());
					emitirHistogramaEsgotoHelper.setTotalQuantidadeEconomias(detalhe.getQuantidadeEconomias());
					emitirHistogramaEsgotoHelper.setTotalQuantidadeVolumeMedido(detalhe.getQuantidadeVolumeMedido());
					emitirHistogramaEsgotoHelper.setTotalQuantidadeVolumeEstimado(detalhe.getQuantidadeVolumeEstimado());

					emitirHistogramaEsgotoHelper.setTotalValorFaturado(detalhe.getValorFaturado());

					colecaoEmitirHistogramaEsgotoDetalhe = new ArrayList();
					colecaoEmitirHistogramaEsgotoDetalhe.add(detalhe);

					emitirHistogramaEsgotoHelper.setColecaoEmitirHistogramaEsgotoDetalhe(colecaoEmitirHistogramaEsgotoDetalhe);

					hashMapTotalizacao.put(tipoTotalizacao, emitirHistogramaEsgotoHelper);
				}

			}
		}
		return hashMapTotalizacao;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Total Geral Por Categoria
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param Collection
	 *            <EmitirHistogramaEsgotoDetalheHelper>
	 * @return LinkedHashMap
	 * @throws ControladorException
	 */
	private LinkedHashMap montarEmitirHistogramaEsgotoTotalGeralCategoria(Collection<EmitirHistogramaEsgotoDetalheHelper> colecao)
					throws ControladorException{

		LinkedHashMap hashMapTotalizacao = new LinkedHashMap();

		if(colecao != null && !colecao.isEmpty()){

			Iterator iterator = colecao.iterator();

			while(iterator.hasNext()){
				EmitirHistogramaEsgotoDetalheHelper detalhe = (EmitirHistogramaEsgotoDetalheHelper) iterator.next();

				Object[] objeto = new Object[3];

				objeto[0] = detalhe.getQuantidadeLigacoes();
				objeto[1] = detalhe.getQuantidadeVolumeTotal();
				objeto[2] = detalhe.getValorFaturado();

				hashMapTotalizacao.put(detalhe.getDescricaoCategoria(), objeto);
			}
		}

		return hashMapTotalizacao;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Gera as linhas Por Faixa de Consumo
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param Collection
	 *            <Object[]>
	 * @throws ControladorException
	 */
	private void montarEmitirHistogramaEsgotoFaixaConsumo(Collection<ConsumoFaixaLigacao> colecao,
					FiltrarEmitirHistogramaEsgotoHelper filtro, int totalQuantidadeLigacoesTotalGeral, BigDecimal totalValorFaturado,
					int totalQuantidadeVolumeTotal, Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto,
					String descricaoOpcaoTotalizacao, Short indicadorMedicao, HashMap mapTotalizacaoGategoria) throws ControladorException{

		// Gera os a linhas com a Faixa (PAI)
		// depois os totalizadores por categoria(FILHO)
		if(colecao != null && !colecao.isEmpty()){

			Collection<Object[]> colecaoRetorno = null;
			EmitirHistogramaEsgotoHelper emitirTotalizadorIndicador = null;

			String descricaoTitulo = "TOTAL C/HID.";
			if(!ConstantesSistema.INDICADOR_USO_ATIVO.equals(indicadorMedicao)){
				descricaoTitulo = "TOTAL S/HID.";
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(indicadorMedicao));
				filtro.setConsumoFaixaLigacaoIntervaloMedido(null);
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(null);
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(indicadorMedicao));
			}

			try{

				filtro.setConsumoFaixaLigacao(null);

				filtro.setMedicao(indicadorMedicao);

				colecaoRetorno = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

				filtro.setMedicao(null);

				emitirTotalizadorIndicador = this.montarEmitirHistogramaEsgotoDetalhe(colecaoRetorno, descricaoTitulo, filtro,
								descricaoOpcaoTotalizacao, totalQuantidadeLigacoesTotalGeral, totalValorFaturado,
								totalQuantidadeVolumeTotal, indicadorMedicao, mapTotalizacaoGategoria, null, null);

			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			if(emitirTotalizadorIndicador != null){

				// chave(Id categoria),valores(BigDecimal[3] percentuais)
				LinkedHashMap mapPercentualParcialPorCategoriaAnterior = new LinkedHashMap();
				LinkedHashMap mapPercentualParcialPorTotalAnterior = new LinkedHashMap();

				filtro.setMedicao(indicadorMedicao);

				Iterator itera = colecao.iterator();
				while(itera.hasNext()){
					ConsumoFaixaLigacao consumo = (ConsumoFaixaLigacao) itera.next();

					filtro.setConsumoFaixaLigacao(consumo);

					try{
						descricaoTitulo = consumo.getNumeroFaixaInicio() + " a " + consumo.getNumeroFaixaFim();

						colecaoRetorno = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

						EmitirHistogramaEsgotoHelper emitirFaixa = this.montarEmitirHistogramaEsgotoDetalhe(colecaoRetorno,
										descricaoTitulo, filtro, descricaoOpcaoTotalizacao, emitirTotalizadorIndicador
														.getTotalQuantidadeLigacoes(), emitirTotalizadorIndicador.getTotalValorFaturado(),
										emitirTotalizadorIndicador.getTotalQuantidadeVolumeTotal(), indicadorMedicao, null,
										mapPercentualParcialPorCategoriaAnterior, mapPercentualParcialPorTotalAnterior);

						if(emitirFaixa != null){
							colecaoEmitirHistogramaEsgoto.add(emitirFaixa);
						}

					}catch(ErroRepositorioException e){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", e);
					}
				}
				if(emitirTotalizadorIndicador != null){
					colecaoEmitirHistogramaEsgoto.add(emitirTotalizadorIndicador);
				}

			}// fim do emitir totalizador
		}
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Linha Faixa
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @return EmitirHistogramaEsgotoHelper
	 * @throws ControladorException
	 */
	private EmitirHistogramaEsgotoHelper montarEmitirHistogramaEsgotoDetalhe(Collection<Object[]> colecao, String descricaoTitulo,
					FiltrarEmitirHistogramaEsgotoHelper filtro, String descricaoOpcaoTotalizacao, int quantidadeLigacaoIndicador,
					BigDecimal valorFaturado, int totalQuantidadeVolumeTotal, Short indicadorHidrometro, HashMap mapTotalizacaoGategoria,
					LinkedHashMap mapPercentualParcialPorCategoriaAnterior, LinkedHashMap mapPercentualParcialPorTotalAnterior)
					throws ControladorException{

		EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelper = null;
		if(colecao != null && !colecao.isEmpty()){

			Iterator iterator = colecao.iterator();

			Collection<EmitirHistogramaEsgotoDetalheHelper> colecaoEmitirHistogramaEsgotoDetalhe = new ArrayList();

			EmitirHistogramaEsgotoDetalheHelper detalhe = null;

			int totalLigacoes = 0;
			int totalEconomias = 0;
			int volumeMedido = 0;
			int volumeEstimado = 0;

			BigDecimal totalValorFaturado = new BigDecimal("0.0");

			while(iterator.hasNext()){
				Object[] objeto = (Object[]) iterator.next();

				detalhe = new EmitirHistogramaEsgotoDetalheHelper();

				detalhe.setDescricaoCategoria((String) objeto[1]);

				if(objeto[2] != null){
					Number quantidadeLigacoes = (Number) objeto[2];
					detalhe.setQuantidadeLigacoes(quantidadeLigacoes.intValue());
				}

				if(objeto[3] != null){
					Number quantidadeEconomias = (Number) objeto[3];
					detalhe.setQuantidadeEconomias(quantidadeEconomias.intValue());
				}

				detalhe.setValorFaturado((BigDecimal) objeto[4]);

				try{

					Categoria categ = new Categoria();
					categ.setId((Integer) objeto[0]);
					Object[] somatorio = null;

					if(mapTotalizacaoGategoria != null){
						somatorio = (Object[]) mapTotalizacaoGategoria.get(detalhe.getDescricaoCategoria());
					}else{
						somatorio = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoTotalGeral(filtro, categ);
					}

					if(somatorio == null){
						somatorio = new Object[3];
						somatorio[0] = 0;
						somatorio[1] = 0;
						somatorio[2] = BigDecimal.ZERO;
					}

					BigDecimal percentualParcialLigacao = BigDecimal.ZERO;
					BigDecimal percentualParcialFaturamento = new BigDecimal(0.0);
					BigDecimal percentualParcialConsumo = BigDecimal.ZERO;

					if(((Number) somatorio[0]).intValue() != 0){

						percentualParcialLigacao = Util.calcularPercentualBigDecimal("" + detalhe.getQuantidadeLigacoes(), ""
										+ somatorio[0]);

					}

					detalhe.setPercentualParcialLigacao(percentualParcialLigacao.doubleValue());

					if(((BigDecimal) somatorio[2]).compareTo(new BigDecimal(0.0)) > 0){

						percentualParcialFaturamento = Util.calcularPercentualBigDecimal(detalhe.getValorFaturado(), new BigDecimal(""
										+ somatorio[2]));
					}

					detalhe.setPercentualParcialFaturamento(percentualParcialFaturamento.doubleValue());

					if(ConstantesSistema.INDICADOR_USO_ATIVO.equals(indicadorHidrometro)){

						Integer quantidadeVolumeMedido = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoVolumeConsumo(filtro,
										ConstantesSistema.INDICADOR_USO_ATIVO, categ, indicadorHidrometro);

						Integer quantidadeVolumeEstimado = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoVolumeConsumo(filtro,
										ConstantesSistema.INDICADOR_USO_DESATIVO, categ, indicadorHidrometro);

						if(quantidadeVolumeMedido != null){
							detalhe.setQuantidadeVolumeMedido(quantidadeVolumeMedido);
						}
						if(quantidadeVolumeEstimado != null){
							detalhe.setQuantidadeVolumeEstimado(quantidadeVolumeEstimado);
						}

					}else{
						Integer quantidadeVolumeEstimado = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoVolumeConsumo(filtro,
										ConstantesSistema.INDICADOR_USO_DESATIVO, categ, indicadorHidrometro);

						if(quantidadeVolumeEstimado != null){
							detalhe.setQuantidadeVolumeEstimado(quantidadeVolumeEstimado);
						}
					}

					if(((Number) somatorio[1]).intValue() != 0){

						percentualParcialConsumo = Util.calcularPercentualBigDecimal("" + detalhe.getQuantidadeVolumeTotal(), ""
										+ somatorio[1]);
					}

					detalhe.setPercentualParcialConsumo(percentualParcialConsumo.doubleValue());

					if(mapPercentualParcialPorCategoriaAnterior != null){

						BigDecimal[] valoresPercentual = (BigDecimal[]) mapPercentualParcialPorCategoriaAnterior.get(categ.getId());

						BigDecimal percentualAcumuladoLigacao = new BigDecimal(0.0);
						BigDecimal percentualAcumuladoConsumo = new BigDecimal(0.0);
						BigDecimal percentualAcumuladoFaturamento = new BigDecimal(0.0);

						if(valoresPercentual != null){

							percentualAcumuladoLigacao = percentualParcialLigacao.add(valoresPercentual[0]);
							percentualAcumuladoConsumo = percentualParcialConsumo.add(valoresPercentual[1]);
							percentualAcumuladoFaturamento = percentualParcialFaturamento.add(valoresPercentual[2]);

						}else{

							percentualAcumuladoLigacao = percentualParcialLigacao;
							percentualAcumuladoConsumo = percentualParcialConsumo;
							percentualAcumuladoFaturamento = percentualParcialFaturamento;

						}

						detalhe.setPercentualAcumuladoLigacao(percentualAcumuladoLigacao.doubleValue());
						detalhe.setPercentualAcumuladoConsumo(percentualAcumuladoConsumo.doubleValue());
						detalhe.setPercentualAcumuladoFaturamento(percentualAcumuladoFaturamento.doubleValue());

						valoresPercentual = new BigDecimal[3];

						valoresPercentual[0] = percentualAcumuladoLigacao;
						valoresPercentual[1] = percentualAcumuladoConsumo;
						valoresPercentual[2] = percentualAcumuladoFaturamento;

						mapPercentualParcialPorCategoriaAnterior.put(categ.getId(), valoresPercentual);

					}

				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				totalLigacoes += detalhe.getQuantidadeLigacoes();
				totalEconomias += detalhe.getQuantidadeEconomias();
				volumeMedido += detalhe.getQuantidadeVolumeMedido();
				volumeEstimado += detalhe.getQuantidadeVolumeEstimado();
				totalValorFaturado = totalValorFaturado.add(detalhe.getValorFaturado());

				colecaoEmitirHistogramaEsgotoDetalhe.add(detalhe);

			}

			emitirHistogramaEsgotoHelper = new EmitirHistogramaEsgotoHelper();
			emitirHistogramaEsgotoHelper.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
			emitirHistogramaEsgotoHelper.setDescricaoTitulo(descricaoTitulo);
			emitirHistogramaEsgotoHelper.setColecaoEmitirHistogramaEsgotoDetalhe(colecaoEmitirHistogramaEsgotoDetalhe);
			emitirHistogramaEsgotoHelper.setTotalQuantidadeLigacoes(totalLigacoes);
			emitirHistogramaEsgotoHelper.setTotalQuantidadeEconomias(totalEconomias);
			emitirHistogramaEsgotoHelper.setTotalQuantidadeVolumeMedido(volumeMedido);
			emitirHistogramaEsgotoHelper.setTotalQuantidadeVolumeEstimado(volumeEstimado);
			emitirHistogramaEsgotoHelper.setTotalValorFaturado(totalValorFaturado);

			BigDecimal percentualParcialLigacao = BigDecimal.ZERO;
			BigDecimal percentualParcialFaturamento = BigDecimal.ZERO;
			BigDecimal percentualParcialConsumo = BigDecimal.ZERO;

			if(quantidadeLigacaoIndicador != 0){

				percentualParcialLigacao = Util.calcularPercentualBigDecimal(
								"" + emitirHistogramaEsgotoHelper.getTotalQuantidadeLigacoes(), "" + quantidadeLigacaoIndicador);
			}

			if(totalQuantidadeVolumeTotal != 0){

				percentualParcialConsumo = Util.calcularPercentualBigDecimal(""
								+ emitirHistogramaEsgotoHelper.getTotalQuantidadeVolumeTotal(), "" + totalQuantidadeVolumeTotal);

			}

			if(valorFaturado.compareTo(BigDecimal.ZERO) > 0){

				percentualParcialFaturamento = Util.calcularPercentualBigDecimal(emitirHistogramaEsgotoHelper.getTotalValorFaturado(),
								valorFaturado);
			}

			emitirHistogramaEsgotoHelper.setTotalPercentualParcialLigacao(percentualParcialLigacao.doubleValue());
			emitirHistogramaEsgotoHelper.setTotalPercentualParcialConsumo(percentualParcialConsumo.doubleValue());
			emitirHistogramaEsgotoHelper.setTotalPercentualParcialFaturamento(percentualParcialFaturamento.doubleValue());

			if(mapPercentualParcialPorTotalAnterior != null){

				BigDecimal[] valoresPercentual = (BigDecimal[]) mapPercentualParcialPorTotalAnterior.get("TOTAL");

				BigDecimal percentualAcumuladoLigacao = BigDecimal.ZERO;
				BigDecimal percentualAcumuladoConsumo = BigDecimal.ZERO;
				BigDecimal percentualAcumuladoFaturamento = BigDecimal.ZERO;

				if(valoresPercentual != null){

					percentualAcumuladoLigacao = percentualParcialLigacao.add(valoresPercentual[0]);
					percentualAcumuladoConsumo = percentualParcialConsumo.add(valoresPercentual[1]);
					percentualAcumuladoFaturamento = percentualParcialFaturamento.add(valoresPercentual[2]);

				}else{

					percentualAcumuladoLigacao = percentualParcialLigacao;
					percentualAcumuladoConsumo = percentualParcialConsumo;
					percentualAcumuladoFaturamento = percentualParcialFaturamento;

				}

				emitirHistogramaEsgotoHelper.setTotalPercentualAcumuladoLigacao(percentualAcumuladoLigacao.doubleValue());
				emitirHistogramaEsgotoHelper.setTotalPercentualAcumuladoConsumo(percentualAcumuladoConsumo.doubleValue());
				emitirHistogramaEsgotoHelper.setTotalPercentualAcumuladoFaturamento(percentualAcumuladoFaturamento.doubleValue());

				valoresPercentual = new BigDecimal[3];

				valoresPercentual[0] = percentualAcumuladoLigacao;
				valoresPercentual[1] = percentualAcumuladoConsumo;
				valoresPercentual[2] = percentualAcumuladoFaturamento;

				mapPercentualParcialPorTotalAnterior.put("TOTAL", valoresPercentual);

			}

		}

		return emitirHistogramaEsgotoHelper;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Elo e Unidade Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 12/06/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoEloUnidadeNegocio(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.localidadeElo.id,histograma.unidadeNegocio.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoLocalidade = null;

			Localidade eloPolo = null;
			UnidadeNegocio unidadeNegocio = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[0]));

				unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[1]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));
				}

				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setMedicao(indicadorMedicao);
				filtro.setOpcaoTotalizacao(4);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, eloPolo.getId()));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoLocalidade = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				filtro.setMedicao(indicadorMedicao);

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				unidadeNegocioAnterior = unidadeNegocio.getId();

			}

			filtro.setMedicao(indicadorMedicao);

			filtro.setOpcaoTotalizacao(10);
			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);
			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Água
	 * Localidade Elo e Unidade Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 12/06/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoLocalidadeEloUnidadeNegocio(
					FiltrarEmitirHistogramaEsgotoHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id," + "histograma.unidadeNegocio.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoLocalidade = null;

			Localidade localidade = null;
			Localidade eloPolo = null;
			UnidadeNegocio unidadeNegocio = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[0]));

				eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[2]));

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					filtro.setOpcaoTotalizacao(13);
					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));
				}

				filtro.setMedicao(indicadorMedicao);

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);
					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));
				}

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setMedicao(indicadorMedicao);
				filtro.setOpcaoTotalizacao(4);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidade.getId()));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoLocalidade = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				unidadeNegocioAnterior = unidadeNegocio.getId();
				eloPoloAnterior = eloPolo.getId();

			}

			filtro.setOpcaoTotalizacao(13);
			filtro.setMedicao(indicadorMedicao);

			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));

			filtro.setOpcaoTotalizacao(10);
			filtro.setMedicao(indicadorMedicao);

			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);
			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Localidade Elo e Unidade Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoLocalidadeEloUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaEsgotoHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id,"
						+ "histograma.unidadeNegocio.id,histograma.gerenciaRegional.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoLocalidade = null;

			Localidade localidade = null;
			Localidade eloPolo = null;
			UnidadeNegocio unidadeNegocio = null;
			GerenciaRegional gerencia = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[0]));

				eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[2]));

				gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[3]));

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					filtro.setOpcaoTotalizacao(13);
					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));
				}

				filtro.setMedicao(indicadorMedicao);

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);
					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));
				}
				filtro.setMedicao(indicadorMedicao);

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setGerenciaRegional(gereAnterior);

					filtro.setOpcaoTotalizacao(2);
					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoGerenciaRegional(filtro));
				}

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);
				filtro.setMedicao(indicadorMedicao);
				filtro.setOpcaoTotalizacao(4);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidade.getId()));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoLocalidade = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				gerenciaAnterior = gerencia.getId();
				unidadeNegocioAnterior = unidadeNegocio.getId();
				eloPoloAnterior = eloPolo.getId();

			}

			filtro.setOpcaoTotalizacao(13);
			filtro.setMedicao(indicadorMedicao);

			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));

			filtro.setOpcaoTotalizacao(10);
			filtro.setMedicao(indicadorMedicao);

			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);
			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoUnidadeNegocio(filtro));

			filtro.setOpcaoTotalizacao(2);
			filtro.setMedicao(indicadorMedicao);

			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoGerenciaRegional(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto -
	 * Unidade Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoUnidadeNegocio(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.unidadeNegocio.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		filtro.setEloPolo(null);
		filtro.setGerenciaRegional(null);
		filtro.setLocalidade(null);
		filtro.setConsumoFaixaLigacao(null);

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			FiltroUnidadeNegocio filtroUnidade = null;
			Collection colecaoUnidade = null;
			UnidadeNegocio unidadeNegocio = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(chave));

				filtro.setUnidadeNegocio(unidadeNegocio);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroUnidade = new FiltroUnidadeNegocio();
				filtroUnidade.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, chave));

				filtroUnidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");

				// Recupera Unidade Negocio
				colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidade, UnidadeNegocio.class.getName());

				unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoUnidade);

				String descricaoOpcaoTotalizacao = unidadeNegocio.getGerenciaRegional().getId() + "-"
								+ unidadeNegocio.getGerenciaRegional().getNomeAbreviado() + " / " + unidadeNegocio.getId() + "-"
								+ unidadeNegocio.getNome();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}
				filtro.setMedicao(indicadorMedicao);

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

			}

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto -
	 * Elo Polo
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoElo(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.localidadeElo.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		filtro.setLocalidade(null);
		filtro.setUnidadeNegocio(null);
		filtro.setGerenciaRegional(null);
		filtro.setCodigoSetorComercial(null);
		filtro.setConsumoFaixaLigacao(null);

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoLocalidade = null;
			Localidade localidade = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(chave));

				filtro.setEloPolo(localidade);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, chave));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Unidade Negocio
				colecaoLocalidade = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}
				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

			}

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Localidade, Elo e Unidade Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 12/06/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoLocalidadeElo(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoLocalidade = null;

			Localidade localidade = null;
			Localidade eloPolo = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[0]));

				eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					filtro.setOpcaoTotalizacao(13);
					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));
				}

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setMedicao(indicadorMedicao);
				filtro.setOpcaoTotalizacao(4);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidade.getId()));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoLocalidade = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				eloPoloAnterior = eloPolo.getId();

			}

			filtro.setOpcaoTotalizacao(13);
			filtro.setMedicao(indicadorMedicao);

			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Água
	 * Setor Comercial ,Localidade e Elo
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoSetorComercialLocalidadeElo(
					FiltrarEmitirHistogramaEsgotoHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.codigoSetorComercial,histograma.localidade.id," + "histograma.localidadeElo.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoSetorComercial = null;

			Integer codigoSetorComercial = null;
			SetorComercial setorComercial = null;
			Localidade localidade = null;
			Localidade eloPolo = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				codigoSetorComercial = Integer.valueOf(arrayNumeracao[0]);

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[1]));

				eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[2]));

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setCodigoSetorComercial(codigoSetorComercial);
				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					filtro.setOpcaoTotalizacao(16);

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoLocalidade(filtro));
				}

				filtro.setCodigoSetorComercial(codigoSetorComercial);
				filtro.setLocalidade(localidade);
				filtro.setMedicao(indicadorMedicao);

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					filtro.setOpcaoTotalizacao(13);
					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));
				}

				filtro.setCodigoSetorComercial(codigoSetorComercial);
				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);

				filtro.setMedicao(indicadorMedicao);
				filtro.setOpcaoTotalizacao(4);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
								codigoSetorComercial));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");

				// Recupera Setor Comercial
				colecaoSetorComercial = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getDescricao() + " / " + setorComercial.getCodigo() + "-"
								+ setorComercial.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				localidadeAnterior = localidade.getId();
				eloPoloAnterior = eloPolo.getId();

			}

			filtro.setOpcaoTotalizacao(16);
			filtro.setMedicao(indicadorMedicao);

			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoLocalidade(filtro));

			filtro.setOpcaoTotalizacao(13);
			filtro.setMedicao(indicadorMedicao);

			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoElo(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Setor Comercial ,Localidade e Elo
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoSetorComercialLocalidade(
					FiltrarEmitirHistogramaEsgotoHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.codigoSetorComercial,histograma.localidade.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoSetorComercial = null;

			Integer codigoSetorComercial = null;
			SetorComercial setorComercial = null;
			Localidade localidade = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				codigoSetorComercial = Integer.valueOf(arrayNumeracao[0]);

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[1]));

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setCodigoSetorComercial(codigoSetorComercial);
				filtro.setLocalidade(localidade);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
								codigoSetorComercial));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");

				// Recupera Setor Comercial
				colecaoSetorComercial = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getDescricao() + " / " + setorComercial.getCodigo() + "-"
								+ setorComercial.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				localidadeAnterior = localidade.getId();

			}

			filtro.setOpcaoTotalizacao(16);
			filtro.setMedicao(indicadorMedicao);

			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoLocalidade(filtro));
		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Setor Comercial e Quadra
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoQuadraSetorComercial(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.numeroQuadra,histograma.setorComercial.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int setorComercialAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroQuadra filtroQuadra = null;
			Collection colecaoQuadra = null;

			Integer numeroQuadra = null;
			SetorComercial setorComercial = null;
			Quadra quadra = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				numeroQuadra = Integer.valueOf(arrayNumeracao[0]);

				setorComercial = new SetorComercial();
				setorComercial.setId(Integer.valueOf(arrayNumeracao[1]));

				if(setorComercialAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					setorComercialAnterior = setorComercial.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setNumeroQuadra(numeroQuadra);
				filtro.setSetorComercial(setorComercial);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, numeroQuadra));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");

				// Recupera Quadra
				colecaoQuadra = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				setorComercialAnterior = setorComercial.getId();

			}

			SetorComercial setorAnterior = new SetorComercial();
			setorAnterior.setId(setorComercialAnterior);

			filtro.setOpcaoTotalizacao(19);
			filtro.setMedicao(indicadorMedicao);
			filtro.setSetorComercial(setorAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoSetorComercial(filtro));
		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * Setor Comercial ,Localidade e Elo
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoQuadraLocalidade(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.numeroQuadra,histograma.setorComercial.id," + "histograma.localidade.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		Short indicadorMedicao = filtro.getMedicao();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int setorComercialAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			FiltroQuadra filtroQuadra = null;
			Collection colecaoQuadra = null;

			Integer numeroQuadra = null;

			Quadra quadra = null;
			SetorComercial setorComercial = null;
			Localidade localidade = null;

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				numeroQuadra = Integer.valueOf(arrayNumeracao[0]);

				setorComercial = new SetorComercial();
				setorComercial.setId(Integer.valueOf(arrayNumeracao[1]));

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[2]));

				if(setorComercialAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					setorComercialAnterior = setorComercial.getId();
				}

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				filtro.setMedicao(indicadorMedicao);
				filtro.setSetorComercial(setorComercial);
				filtro.setLocalidade(localidade);

				// Mudou de Setor Comercial
				if(setorComercialAnterior != setorComercial.getId().intValue()){

					filtro.setOpcaoTotalizacao(19);

					SetorComercial setorAnterior = new SetorComercial();
					setorAnterior.setId(setorComercialAnterior);
					filtro.setSetorComercial(setorAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoSetorComercial(filtro));
				}

				filtro.setMedicao(indicadorMedicao);

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					filtro.setOpcaoTotalizacao(16);

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoLocalidade(filtro));
				}

				filtro.setNumeroQuadra(numeroQuadra);
				filtro.setSetorComercial(setorComercial);
				filtro.setLocalidade(localidade);
				filtro.setMedicao(indicadorMedicao);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, numeroQuadra));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");

				// Recupera Quadra
				colecaoQuadra = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}

				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

				localidadeAnterior = localidade.getId();
				setorComercialAnterior = setorComercial.getId();

			}

			SetorComercial setorAnterior = new SetorComercial();
			setorAnterior.setId(setorComercialAnterior);

			filtro.setOpcaoTotalizacao(19);
			filtro.setMedicao(indicadorMedicao);
			filtro.setSetorComercial(setorAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoSetorComercial(filtro));

			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setOpcaoTotalizacao(16);
			filtro.setMedicao(indicadorMedicao);
			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaEsgoto.addAll(this.emitirHistogramaEsgotoLocalidade(filtro));

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto -
	 * Localidade
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoLocalidade(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.localidade.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		filtro.setCodigoSetorComercial(null);
		filtro.setSetorComercial(null);
		filtro.setEloPolo(null);
		filtro.setConsumoFaixaLigacao(null);

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoLocalidade = null;
			Localidade localidade = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				localidade = new Localidade();
				localidade.setId(Integer.valueOf(chave));

				filtro.setLocalidade(localidade);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, chave));

				// Recupera Localidade
				colecaoLocalidade = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				String descricaoOpcaoTotalizacao = localidade.getId() + "-" + localidade.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}
				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

			}

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto -
	 * Setor Comercial
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoSetorComercial(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.setorComercial.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		filtro.setConsumoFaixaLigacao(null);

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoSetorComercial = null;
			SetorComercial setorComercial = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				setorComercial = new SetorComercial();
				setorComercial.setId(Integer.valueOf(chave));

				filtro.setSetorComercial(setorComercial);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, chave));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");

				// Recupera Setor Comercial
				colecaoSetorComercial = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getDescricao() + " / " + setorComercial.getCodigo() + "-"
								+ setorComercial.getDescricao();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}
				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

			}

		}

		return colecaoEmitirHistogramaEsgoto;
	}

	/**
	 * [UC0600] Emitir Histograma de Esgoto -
	 * Quadra
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<EmitirHistogramaEsgotoHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaEsgotoHelper> emitirHistogramaEsgotoQuadra(FiltrarEmitirHistogramaEsgotoHelper filtro)
					throws ControladorException{

		Collection<EmitirHistogramaEsgotoHelper> colecaoEmitirHistogramaEsgoto = new ArrayList<EmitirHistogramaEsgotoHelper>();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoMedido = filtro.getColecaoConsumoFaixaLigacaoMedido();

		Collection<ConsumoFaixaLigacao> colecaoConsumoFaixaLigacaoNaoMedido = filtro.getColecaoConsumoFaixaLigacaoNaoMedido();

		filtro.setTipoGroupBy("histograma.quadra.id ");

		LinkedHashMap hashMapTotalGeral = new LinkedHashMap();

		try{

			if(filtro.getMedicao() != null){
				if(filtro.getMedicao().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO){
					filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}else{
					filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro.retornaOLimiteConsultaTotal(filtro.getMedicao()));
				}
			}else{
				filtro.setConsumoFaixaLigacaoIntervaloMedido(filtro.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.setConsumoFaixaLigacaoIntervaloNaoMedido(filtro
								.retornaOLimiteConsultaTotal(ConstantesSistema.INDICADOR_USO_DESATIVO));
			}

			Collection<Object[]> colecaoTotalGeral = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgoto(filtro);

			hashMapTotalGeral = this.montarEmitirHistogramaEsgotoTotalGeral(filtro, colecaoTotalGeral);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			FiltroQuadra filtroQuadra = null;
			Collection colecaoQuadra = null;
			Quadra quadra = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				quadra = new Quadra();
				quadra.setId(Integer.valueOf(chave));

				filtro.setQuadra(quadra);

				EmitirHistogramaEsgotoHelper emitirHistogramaEsgotoHelperTotalGeral = (EmitirHistogramaEsgotoHelper) hashMapTotalGeral
								.get(chave);

				HashMap mapTotalizacaoCategoria = this
								.montarEmitirHistogramaEsgotoTotalGeralCategoria(emitirHistogramaEsgotoHelperTotalGeral
												.getColecaoEmitirHistogramaEsgotoDetalhe());

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, chave));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");

				// Recupera Setor Comercial
				colecaoQuadra = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				// Vai gerar Faixa para medido
				if(colecaoConsumoFaixaLigacaoMedido != null && !colecaoConsumoFaixaLigacaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_ATIVO, mapTotalizacaoCategoria);

				}

				// Vai gerar Faixa para Não medido
				if(colecaoConsumoFaixaLigacaoNaoMedido != null && !colecaoConsumoFaixaLigacaoNaoMedido.isEmpty()){

					this.montarEmitirHistogramaEsgotoFaixaConsumo(colecaoConsumoFaixaLigacaoNaoMedido, filtro,
									emitirHistogramaEsgotoHelperTotalGeral.getTotalQuantidadeLigacoes(),
									emitirHistogramaEsgotoHelperTotalGeral.getTotalValorFaturado(), emitirHistogramaEsgotoHelperTotalGeral
													.getTotalQuantidadeVolumeTotal(), colecaoEmitirHistogramaEsgoto,
									descricaoOpcaoTotalizacao, ConstantesSistema.INDICADOR_USO_DESATIVO, mapTotalizacaoCategoria);
				}
				// Setar Total Geral
				emitirHistogramaEsgotoHelperTotalGeral.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialLigacao(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialConsumo(100.0);
				emitirHistogramaEsgotoHelperTotalGeral.setTotalPercentualParcialFaturamento(100.0);
				colecaoEmitirHistogramaEsgoto.add(emitirHistogramaEsgotoHelperTotalGeral);

			}

		}

		return colecaoEmitirHistogramaEsgoto;
	}


	/** 
	 * Método pesquisarHistogramaEsgotoEconomiaDTO
	 * <p>Esse método implementa a pesquisa dos dados para o relatorio de histograma esgoto economia</p> 
	 * RASTREIO: [OC1073038][UC0606]
	 * @param filtro
	 * @return
	 * @throws ControladorException
	 * @author Marlos Ribeiro 
	 * @since 11/06/2013
	 */
	public Collection<HistogramaEsgotoEconomiaDTO> pesquisarHistogramaEsgotoEconomiaDTO(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro) throws ControladorException{

		Collection<HistogramaEsgotoEconomiaDTO> linhasRelatorio;
		try{
			linhasRelatorio = repositorioHistograma.pesquisarDadosRelatorioHistogramaEsgoto(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Erro ao consultar dados Histograma");
		}
		return linhasRelatorio;
	}

	/**
	 * [UC0606] Emitir Histograma de Água por Economia
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	public Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> pesquisarEmitirHistogramaEsgotoEconomia(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();
		Collection<BigDecimal> colecao = filtro.getColecaoPercentualLigacaoEsgoto();

		if(colecao != null && !colecao.isEmpty()){

			Iterator itera = colecao.iterator();

			while(itera.hasNext()){

				BigDecimal percentual = (BigDecimal) itera.next();
				FiltrarEmitirHistogramaEsgotoEconomiaHelper novoFiltro = new FiltrarEmitirHistogramaEsgotoEconomiaHelper(filtro);
				FiltrarEmitirHistogramaEsgotoEconomiaHelper novoFiltroClone = new FiltrarEmitirHistogramaEsgotoEconomiaHelper(filtro);
				novoFiltro.setPercentualLigacaoEsgoto(percentual);
				novoFiltroClone.setPercentualLigacaoEsgoto(percentual);
				Collection<Integer> colecaoTarifa = filtro.getColecaoTarifa();

				if(colecaoTarifa != null && !colecaoTarifa.isEmpty()){

					Iterator iteraTarifa = colecaoTarifa.iterator();

					while(iteraTarifa.hasNext()){
						Integer tarifa = (Integer) iteraTarifa.next();
						novoFiltro.setTarifa(tarifa);
						novoFiltroClone.setTarifa(tarifa);
						colecaoEmitirHistogramaEsgotoEconomia
										.addAll(this.montarSwitchHistogramaEsgotoEconomia(novoFiltro, novoFiltroClone));
					}
				}else{
					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.montarSwitchHistogramaEsgotoEconomia(novoFiltro, novoFiltroClone));
				}
			}

		}else{

			Collection<Integer> colecaoTarifa = filtro.getColecaoTarifa();

			if(colecaoTarifa != null && !colecaoTarifa.isEmpty()){

				Iterator iteraTarifa = colecaoTarifa.iterator();

				while(iteraTarifa.hasNext()){
					Integer tarifa = (Integer) iteraTarifa.next();

					FiltrarEmitirHistogramaEsgotoEconomiaHelper novoFiltro = new FiltrarEmitirHistogramaEsgotoEconomiaHelper(filtro);

					FiltrarEmitirHistogramaEsgotoEconomiaHelper novoFiltroClone = new FiltrarEmitirHistogramaEsgotoEconomiaHelper(filtro);

					novoFiltro.setTarifa(tarifa);
					novoFiltroClone.setTarifa(tarifa);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.montarSwitchHistogramaEsgotoEconomia(novoFiltro, novoFiltroClone));
				}
			}
		}

		FiltrarEmitirHistogramaEsgotoEconomiaHelper filtroClone2 = new FiltrarEmitirHistogramaEsgotoEconomiaHelper(filtro);

		colecaoEmitirHistogramaEsgotoEconomia.addAll(this.montarSwitchHistogramaEsgotoEconomia(filtro, filtroClone2));

		return colecaoEmitirHistogramaEsgotoEconomia;
	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Monta switch
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> montarSwitchHistogramaEsgotoEconomia(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, FiltrarEmitirHistogramaEsgotoEconomiaHelper filtroClone)
					throws ControladorException{

		int opcaoTotalizacao = filtro.getOpcaoTotalizacao();

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		switch(opcaoTotalizacao){

			// Estado
			case 1:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEstado(filtro, Boolean.TRUE));
				break;

			// Estado por Gerencia Regional
			case 2:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.TRUE));
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Estado por Unidade Negocio
			case 3:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocioGerenciaRegional(filtro,
								Boolean.TRUE));
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Estado por Elo Polo
			case 4:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPoloUnidadeNegocioGerenciaRegional(
								filtro, Boolean.TRUE));
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Estado por Localidade
			case 5:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this
								.emitirHistogramaEsgotoEconomiaLocalidadeEloPoloUnidadeNegocioGerenciaRegional(filtro, Boolean.TRUE));
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEstado(filtroClone, Boolean.FALSE));
				break;

			// Gerência Regional
			case 6:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.TRUE));
				break;

			// Gerência Regional por Unidade Negocio
			case 7:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocioGerenciaRegional(filtro,
								Boolean.TRUE));
				break;

			// Gerência Regional por Elo
			case 8:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPoloUnidadeNegocioGerenciaRegional(
								filtro, Boolean.TRUE));
				break;

			// Gerência Regional por Localidade
			case 9:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this
								.emitirHistogramaEsgotoEconomiaLocalidadeEloPoloUnidadeNegocioGerenciaRegional(filtro, Boolean.TRUE));
				break;

			// Unidade de Negocio
			case 10:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.TRUE));
				break;

			// Unidade de Negocio por Elo
			case 11:
				colecaoEmitirHistogramaEsgotoEconomia
								.addAll(this.emitirHistogramaEsgotoEconomiaEloPoloUnidadeNegocio(filtro, Boolean.TRUE));
				break;

			// Unidade de Negocio por Localidade
			case 12:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidadeEloPoloUnidadeNegocio(filtro,
								Boolean.TRUE));
				break;

			// Elo Polo
			case 13:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.TRUE));
				break;

			// Elo Polo Por Localidade
			case 14:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidadeEloPolo(filtro, Boolean.TRUE));
				break;

			// Elo Polo Por Setor Comercial
			case 15:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaSetorComercialLocalidadeEloPolo(filtro,
								Boolean.TRUE));
				break;

			// Localidade
			case 16:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidade(filtro, Boolean.TRUE));
				break;

			// Localidade Por Setor Comercial
			case 17:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaSetorComercialLocalidade(filtro,
								Boolean.TRUE));
				break;

			// Localidade Por Quadra
			case 18:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaQuadraSetorComercialLocalidade(filtro,
								Boolean.TRUE));
				break;

			// Setor Comercial
			case 19:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaSetorComercial(filtro, Boolean.TRUE));
				break;

			// Setor Comercial Por Quadra
			case 20:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaQuadraSetorComercial(filtro, Boolean.TRUE));
				break;

			// Quadra
			case 21:
				colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaQuadra(filtro, Boolean.TRUE));
				break;

		}

		return colecaoEmitirHistogramaEsgotoEconomia;
	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Estado
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaEstado(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
		Collection<ConsumoFaixaCategoria> colecaoConsumoFaixaCategoria = null;
		EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
		Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
		List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;
		Categoria categoria = null;
		FiltroCategoria filtroCategoria = null;
		Collection colecaoConsulta = null;
		String descricaoOpcaoTotalizacao = this.getControladorUtil().pesquisarParametrosDoSistema().getNomeEstado();


		String descricaoTarifa = "";
		String descricaoPercentual = "";

		// Montando os grupos de perfil de ligacao de esgoto
		Collection<LigacaoEsgotoPerfil> percentuaisEsgoto = listarLigacaoEsgotoPerfil();
		Collection<BigDecimal> grupoPercentuaisEsgoto = new ArrayList<BigDecimal>();
		for(LigacaoEsgotoPerfil ligacaoEsgotoPerfil : percentuaisEsgoto){
			if(Util.isVazioOrNulo(filtro.getColecaoPercentualLigacaoEsgoto())
							|| filtro.getColecaoPercentualLigacaoEsgoto().contains(
											ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada())) grupoPercentuaisEsgoto
							.add(ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada());
		}
		filtro.setColecaoPercentualLigacaoEsgoto(null);

		// GRUPO PERFIL LIGACAO ESGOTO
		for(BigDecimal perfilLigacaoEsgoto : grupoPercentuaisEsgoto){
			descricaoTarifa = "TOTALIZADOR";
			descricaoPercentual = Util.formataBigDecimal(perfilLigacaoEsgoto, 2, true) + "%";
			filtro.setPercentualLigacaoEsgoto(perfilLigacaoEsgoto);

			Long totalGeralEconomiasMedido = new Long("0");
			Long totalGeralVolumeConsumoMedido = new Long("0");
			Long totalGeralVolumeFaturadoMedido = new Long("0");
			BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
			Long totalGeralLigacaoMedido = new Long("0");
			Long totalGeralEconomiasNaoMedido = new Long("0");
			Long totalGeralVolumeConsumoNaoMedido = new Long("0");
			Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
			BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
			Long totalGeralLigacaoNaoMedido = new Long("0");
			int quantidadeCategoriaComValores = 0;

			if(filtro.getTarifa() != null){
				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();
				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));
				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());
				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);
				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}
			for(Object idCategoria : linkedHashMapConsumoFaixaCategoria.keySet()){

				emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);

				listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

				int quantidadeFaixaComValores = 0;
				int limiteSuperiorFaixaAnterior = 0;

				Long totalEconomiasMedido = new Long("0");
				Long totalVolumeConsumoMedido = new Long("0");
				Long totalVolumeFaturadoMedido = new Long("0");
				Long totalLigacaoMedido = new Long("0");
				BigDecimal totalReceitaMedido = BigDecimal.ZERO;

				Long totalEconomiasNaoMedido = new Long("0");
				Long totalVolumeConsumoNaoMedido = new Long("0");
				Long totalVolumeFaturadoNaoMedido = new Long("0");
				Long totalLigacaoNaoMedido = new Long("0");
				BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

				filtroCategoria = new FiltroCategoria();
				filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

				// Recupera Categoria
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

				categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoCategoria = categoria.getDescricao();

				filtro.setCategoria(categoria);

				colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);
				if(colecaoConsumoFaixaCategoria != null){
					// LOGGER.info("-------------------");
					for(ConsumoFaixaCategoria consumoFaixaCategoria : colecaoConsumoFaixaCategoria){

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);
						// LOGGER.info("DETALHE % ->" + detalhe.getPercentualEsgoto());
						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = (Long) totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();

						if(detalhe.isExisteHistograma()){
							quantidadeFaixaComValores++;
						}
					}
					// LOGGER.info("-------------------");
				}

				if(quantidadeFaixaComValores != 0){
					quantidadeCategoriaComValores++;
				}

				colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
								listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
								filtro.getIdFuncionalidadeIniciada());

				for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

					totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
					totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
					totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
					totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

				}
				emitirHistograma.setIdCategoria(categoria.getId());

				emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);

				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
				emitirHistograma.setDescricaoFaixa("TOTAL");

				emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);

				emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);

				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
				totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
				totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
				totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
				totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

				totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
				totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
				totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido + emitirHistograma.getTotalVolumeFaturadoNaoMedido();
				totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
				totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
			}

		if(quantidadeCategoriaComValores != 0){

			emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();

			emitirHistograma.setDescricaoPercentual(descricaoPercentual);
			emitirHistograma.setDescricaoTarifa(descricaoTarifa);
			emitirHistograma.setDescricaoCategoria(null);
			emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
			emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

			// Medido(COM HIDROMETRO)
			emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
			emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
			emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
			emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
			emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

			// Não Medido(SEM HIDROMETRO)
			emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
			emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
			emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
			emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
			emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
			emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

			// TOTAL GERAL
			colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
		}else{
			colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();
		}
		} // FIM GRUPO PERFIL LIGACAO ESGOTO
		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaGerenciaRegional(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		LOGGER.info("EMITIR - GERENCIA REGIONAL");
		filtro.setEloPolo(null);
		filtro.setUnidadeNegocio(null);
		filtro.setLocalidade(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		filtro.setTipoGroupBy("histograma.gerenciaRegional.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			GerenciaRegional gerencia = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroGerenciaRegional filtroGerencia = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();
				String[] chaveArray = chave.split(";");
				String idGerencia = chaveArray[0];
				String descricaoPercentual = Util.formataBigDecimal(new BigDecimal(chaveArray[1]), 2, true) + "%";
				filtro.setPercentualLigacaoEsgoto(new BigDecimal(chaveArray[1]));

				Iterator itera = colecaoCategorias.iterator();

				filtroGerencia = new FiltroGerenciaRegional();
				filtroGerencia.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, idGerencia));

				// Recupera Gerencia Regional
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroGerencia, GerenciaRegional.class.getName());

				gerencia = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = gerencia.getId() + " - " + gerencia.getNome();

				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(gerencia.getId());
					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Unidade de Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.unidadeNegocio.id,histograma.gerenciaRegional.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			UnidadeNegocio unidadeNegocio = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroUnidadeNegocio filtroUnidadeNegocio = null;
			Collection colecaoConsulta = null;

			// Pai da unidade Negocioa(unidade -> gerencia)
			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				GerenciaRegional gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[1]));

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					filtro.setOpcaoTotalizacao(2);

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setGerenciaRegional(gereAnterior);

					colecaoEmitirHistogramaEsgotoEconomia
									.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.FALSE));
				}

				filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, arrayNumeracao[0]));
				filtroUnidadeNegocio.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");

				// Recupera Unidade Negocio
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

				unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = unidadeNegocio.getGerenciaRegional().getId() + "-"
								+ unidadeNegocio.getGerenciaRegional().getNomeAbreviado() + " / " + unidadeNegocio.getId() + "-"
								+ unidadeNegocio.getNome();

				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(unidadeNegocio.getGerenciaRegional().getId());
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");

					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				gerenciaAnterior = gerencia.getId();
			}

			filtro.setOpcaoTotalizacao(2);

			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Elo Polo e Unidade de Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaEloPoloUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.localidadeElo.id," + "histograma.unidadeNegocio.id,histograma.gerenciaRegional.id ");

		filtro.setTipoOrderBy("2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Localidade eloPolo = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade -> gerencia)
			int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[1]));

				GerenciaRegional gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[2]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					gerenciaAnterior = gerencia.getId();
				}

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));
				}

				// Mudou de Gerencia
				if(gerenciaAnterior != gerencia.getId().intValue()){

					filtro.setOpcaoTotalizacao(2);

					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setGerenciaRegional(gereAnterior);

					colecaoEmitirHistogramaEsgotoEconomia
									.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(eloPolo.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(eloPolo.getId());

					if(eloPolo.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(eloPolo.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				gerenciaAnterior = gerencia.getId();
				unidadeNegocioAnterior = unidadeNegocio.getId();
			}
			// Unidade de Negocio
			filtro.setOpcaoTotalizacao(10);

			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);

			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

			// Gerencia Regional
			filtro.setOpcaoTotalizacao(2);

			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setGerenciaRegional(gereAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Elo Polo e Unidade de Negocio e Gerencia Regional
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaLocalidadeEloPoloUnidadeNegocioGerenciaRegional(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		LOGGER.info("EMITIR - PRINCIPAL");
		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();
		String descricaoPercentual;
		String descricaoTarifa;
		filtro.setTipoGroupBy("histograma.localidade.id, histograma.localidadeElo.id, histograma.unidadeNegocio.id, histograma.gerenciaRegional.id ");
		filtro.setTipoOrderBy("4,3,2,1");
		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);
		LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
		Collection<ConsumoFaixaCategoria> colecaoConsumoFaixaCategoria = null;
		EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
		Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
		List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;
		Localidade localidade = null;
		Categoria categoria = null;
		FiltroCategoria filtroCategoria = null;
		FiltroLocalidade filtroLocalidade = null;
		// Pai do elo Polo(eloPolo -> unidade -> gerencia)
		int gerenciaAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
		int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
		int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
		BigDecimal perfilLigacaoEsgoto;

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			// QUEBRAS POR LOCALIDADE
			for(Object chave : hashMapTotalGeral.keySet()){
				LOGGER.info("CHAVE -> " + chave);
				String[] arrayNumeracao = String.valueOf(chave).split(";");
				// --------------
				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));
				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[2]));
				GerenciaRegional gerencia = new GerenciaRegional();
				gerencia.setId(Integer.valueOf(arrayNumeracao[3]));
				perfilLigacaoEsgoto = new BigDecimal(arrayNumeracao[4]);

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO) unidadeNegocioAnterior = unidadeNegocio.getId();
				if(gerenciaAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO) gerenciaAnterior = gerencia.getId();
				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO) eloPoloAnterior = eloPolo.getId();

				descricaoPercentual = Util.formataBigDecimal(perfilLigacaoEsgoto, 2, true) + "%";
				filtro.setPercentualLigacaoEsgoto(perfilLigacaoEsgoto);

				// Mudou de Elo Polo
				if(!eloPolo.getId().equals(eloPoloAnterior)){
					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);
					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);
					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				// Mudou de Unidade
				if(!unidadeNegocio.getId().equals(unidadeNegocioAnterior)){
					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setOpcaoTotalizacao(10);
					filtro.setUnidadeNegocio(uniAnterior);
					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));
				}

				// Mudou de Gerencia
				if(!gerencia.getId().equals(gerenciaAnterior)){
					GerenciaRegional gereAnterior = new GerenciaRegional();
					gereAnterior.setId(gerenciaAnterior);
					filtro.setOpcaoTotalizacao(2);
					filtro.setGerenciaRegional(gereAnterior);
					colecaoEmitirHistogramaEsgotoEconomia
									.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				localidade = (Localidade) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroLocalidade,
								Localidade.class.getName()));
				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-" + //
								localidade.getGerenciaRegional().getNomeAbreviado() + " / " + //
								localidade.getUnidadeNegocio().getId() + "-" + //
								localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + //
								localidade.getLocalidade().getId() + "-" + //
								localidade.getLocalidade().getDescricao() + " / " + //
								localidade.getId() + "-" + //
								localidade.getDescricao();

				LOGGER.info(descricaoOpcaoTotalizacao + " - " + descricaoPercentual);

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);
				filtro.setGerenciaRegional(gerencia);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");
				descricaoTarifa = "TOTALIZADOR";
				// --------------

				// GRUPO PERFIL LIGACAO ESGOTO
				// for(BigDecimal perfilLigacaoEsgoto : grupoPercentuaisEsgoto){

					if(filtro.getTarifa() != null){
						FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();
						filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
						filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

						ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
										filtroConsumoTarifa, ConsumoTarifa.class.getName()));
						descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
					}

					for(Object idCategoria : linkedHashMapConsumoFaixaCategoria.keySet()){
						emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
						emitirHistograma.setDescricaoPercentual(descricaoPercentual);
						emitirHistograma.setDescricaoTarifa(descricaoTarifa);
						listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();
						colecaoConsumoFaixaCategoria = Collections.checkedCollection(
										(Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria), ConsumoFaixaCategoria.class);
						int limiteSuperiorFaixaAnterior = 0;

						Long totalEconomiasMedido = new Long("0");
						Long totalVolumeConsumoMedido = new Long("0");
						Long totalVolumeFaturadoMedido = new Long("0");
						Long totalLigacaoMedido = new Long("0");
						BigDecimal totalReceitaMedido = BigDecimal.ZERO;

						Long totalEconomiasNaoMedido = new Long("0");
						Long totalVolumeConsumoNaoMedido = new Long("0");
						Long totalVolumeFaturadoNaoMedido = new Long("0");
						Long totalLigacaoNaoMedido = new Long("0");
						BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

						filtroCategoria = new FiltroCategoria();
						filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

						// Recupera Categoria
						categoria = (Categoria) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroCategoria,
										Categoria.class.getName()));
						String descricaoCategoria = categoria.getDescricao();
						filtro.setCategoria(categoria);

						for(ConsumoFaixaCategoria consumoFaixaCategoria : colecaoConsumoFaixaCategoria){
							EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
											filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

							emitirHistograma.setDescricaoCategoria(descricaoCategoria);
							limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

							listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

							totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
							totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
							totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

							totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
							totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
							totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
						}

						colecaoEmitirHistogramaEsgotoEconomiaDetalhe = atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
										listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
										filtro.getIdFuncionalidadeIniciada());

						for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

							totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
							totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
							totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
							totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						}

						emitirHistograma.setIdCategoria(categoria.getId());
						emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
						emitirHistograma.setIdLocalidade(localidade.getId());

						if(localidade.getLocalidade() != null) emitirHistograma
										.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

						emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
						emitirHistograma.setDescricaoFaixa("TOTAL");
						emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
						emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
						emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
						emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
						emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
						emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
						emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
						emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
						emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
						emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
						emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
						emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

						colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

						totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
						totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
						totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
						totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
						totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

						totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
						totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido
										+ emitirHistograma.getTotalVolumeConsumoNaoMedido();
						totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
										+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
						totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
						totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
					}

					gerenciaAnterior = gerencia.getId();
					unidadeNegocioAnterior = unidadeNegocio.getId();
					eloPoloAnterior = eloPolo.getId();

					// --------------------------
					emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					emitirHistograma.setDescricaoCategoria(null);
					emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

					// Medido(COM HIDROMETRO)
					emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

					// Não Medido(SEM HIDROMETRO)
					emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
					emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

					// TOTAL GERAL
					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
					// --------------------------
				// } FIM PERFIL ESGOTO



			}
			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);
			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);
			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));

			// Unidade de Negocio
			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);
			filtro.setOpcaoTotalizacao(10);
			filtro.setUnidadeNegocio(uniAnterior);
			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

			// Gerencia Regional
			GerenciaRegional gereAnterior = new GerenciaRegional();
			gereAnterior.setId(gerenciaAnterior);
			filtro.setOpcaoTotalizacao(2);
			filtro.setGerenciaRegional(gereAnterior);
			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaGerenciaRegional(filtro, Boolean.FALSE));
		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia - Unidade de Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaUnidadeNegocio(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		LOGGER.info("EMITIR - UNIDADE NEGOCIO");
		filtro.setEloPolo(null);
		filtro.setGerenciaRegional(null);
		filtro.setLocalidade(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		filtro.setTipoGroupBy("histograma.unidadeNegocio.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			UnidadeNegocio unidadeNegocio = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroUnidadeNegocio filtroUnidadeNegocio = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();
				String[] chaveArray = chave.split(";");
				String idUnidadeNegocio = chaveArray[0];
				String descricaoPercentual = Util.formataBigDecimal(new BigDecimal(chaveArray[1]), 2, true) + "%";
				filtro.setPercentualLigacaoEsgoto(new BigDecimal(chaveArray[1]));

				Iterator itera = colecaoCategorias.iterator();

				filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, idUnidadeNegocio));
				filtroUnidadeNegocio.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");

				// Recupera Unidade Negocio
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

				unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = unidadeNegocio.getGerenciaRegional().getId() + "-"
								+ unidadeNegocio.getGerenciaRegional().getNomeAbreviado() + " / " + unidadeNegocio.getId() + "-"
								+ unidadeNegocio.getNome();

				filtro.setUnidadeNegocio(unidadeNegocio);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());
					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);
					String descricaoCategoria = categoria.getDescricao();
					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(unidadeNegocio.getGerenciaRegional().getId());

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Elo Polo e Unidade de Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaEloPoloUnidadeNegocio(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.localidadeElo.id,histograma.unidadeNegocio.id");
		filtro.setTipoOrderBy("2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Localidade eloPolo = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade)
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[1]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					filtro.setOpcaoTotalizacao(10);

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(eloPolo.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(eloPolo.getId());

					if(eloPolo.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(eloPolo.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				unidadeNegocioAnterior = unidadeNegocio.getId();
			}
			// Unidade de Negocio
			filtro.setOpcaoTotalizacao(10);

			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);

			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Localidade ,Elo Polo e Unidade de Negocio
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaLocalidadeEloPoloUnidadeNegocio(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id," + "histograma.unidadeNegocio.id");

		filtro.setTipoOrderBy("3,2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Localidade localidade = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade -> gerencia)
			int unidadeNegocioAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
				unidadeNegocio.setId(Integer.valueOf(arrayNumeracao[2]));

				if(unidadeNegocioAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					unidadeNegocioAnterior = unidadeNegocio.getId();
				}

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);

					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				// Mudou de Unidade
				if(unidadeNegocioAnterior != unidadeNegocio.getId().intValue()){

					UnidadeNegocio uniAnterior = new UnidadeNegocio();
					uniAnterior.setId(unidadeNegocioAnterior);

					filtro.setOpcaoTotalizacao(10);
					filtro.setUnidadeNegocio(uniAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setUnidadeNegocio(unidadeNegocio);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(localidade.getId());

					if(localidade.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				unidadeNegocioAnterior = unidadeNegocio.getId();
				eloPoloAnterior = eloPolo.getId();
			}

			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);

			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));

			// Unidade de Negocio
			UnidadeNegocio uniAnterior = new UnidadeNegocio();
			uniAnterior.setId(unidadeNegocioAnterior);

			filtro.setOpcaoTotalizacao(10);
			filtro.setUnidadeNegocio(uniAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaUnidadeNegocio(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Elo Polo
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaEloPolo(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		LOGGER.info("EMITIR - ELO/POLO");
		filtro.setGerenciaRegional(null);
		filtro.setUnidadeNegocio(null);
		filtro.setLocalidade(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		filtro.setTipoGroupBy("histograma.localidadeElo.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();
				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));
				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());
				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);
				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Localidade eloPolo = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();
			String chave;
			while(iter.hasNext()){

				chave = (String) iter.next();
				String[] chaveArray = chave.split(";");
				String idEloPolo = chaveArray[0];

				String descricaoPercentual = "";
					descricaoPercentual = Util.formataBigDecimal(new BigDecimal(chaveArray[1]), 2, true) + "%";
				filtro.setPercentualLigacaoEsgoto(new BigDecimal(chaveArray[1]));

				Iterator itera = colecaoCategorias.iterator();

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idEloPolo));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

				// Recupera Elo Polo
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = eloPolo.getGerenciaRegional().getId() + "-"
								+ eloPolo.getGerenciaRegional().getNomeAbreviado() + " / " + eloPolo.getUnidadeNegocio().getId() + "-"
								+ eloPolo.getUnidadeNegocio().getNomeAbreviado() + " / " + eloPolo.getId() + "-" + eloPolo.getDescricao();

				filtro.setEloPolo(eloPolo);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);

					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(eloPolo.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(eloPolo.getId());

					if(eloPolo.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(eloPolo.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Localidade e Elo Polo
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaLocalidadeEloPolo(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.localidade.id,histograma.localidadeElo.id");

		filtro.setTipoOrderBy("1,2");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Localidade localidade = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;

			// Pai do elo Polo(eloPolo -> unidade -> gerencia)
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[1]));

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);

					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, arrayNumeracao[0]));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

				// Recupera Localidade
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(localidade.getId());

					if(localidade.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				eloPoloAnterior = eloPolo.getId();
			}

			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);

			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Setor Comercial ,Localidade e Elo
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaSetorComercialLocalidadeEloPolo(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.codigoSetorComercial,histograma.localidade.id,histograma.localidadeElo.id");
		filtro.setTipoOrderBy("1,2,3");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			SetorComercial setorComercial = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoConsulta = null;

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int eloPoloAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[1]));

				Localidade eloPolo = new Localidade();
				eloPolo.setId(Integer.valueOf(arrayNumeracao[2]));

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				if(eloPoloAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					eloPoloAnterior = eloPolo.getId();
				}

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setOpcaoTotalizacao(16);
					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidade(filtro, Boolean.FALSE));
				}

				// Mudou de Elo Polo
				if(eloPoloAnterior != eloPolo.getId().intValue()){

					Localidade eloAnterior = new Localidade();
					eloAnterior.setId(eloPoloAnterior);

					filtro.setOpcaoTotalizacao(13);
					filtro.setEloPolo(eloAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));
				}

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, arrayNumeracao[0]));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.gerenciaRegional");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.unidadeNegocio");

				// Recupera Setor Comercial
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getGerenciaRegional().getId() + "-"
								+ setorComercial.getLocalidade().getGerenciaRegional().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getUnidadeNegocio().getId() + "-"
								+ setorComercial.getLocalidade().getUnidadeNegocio().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getLocalidade().getDescricao() + " / "
								+ setorComercial.getLocalidade().getId() + "-" + setorComercial.getLocalidade().getDescricao() + " / "
								+ setorComercial.getCodigo() + "-" + setorComercial.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setEloPolo(eloPolo);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(setorComercial.getLocalidade().getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(setorComercial.getLocalidade().getId());

					if(setorComercial.getLocalidade().getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(setorComercial.getLocalidade().getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				localidadeAnterior = localidade.getId();
				eloPoloAnterior = eloPolo.getId();
			}

			// Localidade
			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setOpcaoTotalizacao(16);
			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidade(filtro, Boolean.FALSE));

			// Elo Polo
			Localidade eloAnterior = new Localidade();
			eloAnterior.setId(eloPoloAnterior);

			filtro.setOpcaoTotalizacao(13);
			filtro.setEloPolo(eloAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaEloPolo(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Setor Comercial ,Localidade
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaSetorComercialLocalidade(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.codigoSetorComercial,histograma.localidade.id");
		filtro.setTipoOrderBy("1,2");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			SetorComercial setorComercial = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoConsulta = null;

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				Localidade localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[1]));

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setOpcaoTotalizacao(16);
					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidade(filtro, Boolean.FALSE));
				}

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, arrayNumeracao[0]));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.gerenciaRegional");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.unidadeNegocio");

				// Recupera Setor Comercial
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getGerenciaRegional().getId() + "-"
								+ setorComercial.getLocalidade().getGerenciaRegional().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getUnidadeNegocio().getId() + "-"
								+ setorComercial.getLocalidade().getUnidadeNegocio().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getLocalidade().getDescricao() + " / "
								+ setorComercial.getLocalidade().getId() + "-" + setorComercial.getLocalidade().getDescricao() + " / "
								+ setorComercial.getId() + "-" + setorComercial.getDescricao();

				filtro.setLocalidade(localidade);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(setorComercial.getLocalidade().getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(setorComercial.getLocalidade().getLocalidade().getId());

					if(setorComercial.getLocalidade().getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(setorComercial.getLocalidade().getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				localidadeAnterior = localidade.getId();
			}

			// Localidade
			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setOpcaoTotalizacao(16);
			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidade(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Localidade
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaLocalidade(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setSetorComercial(null);
		filtro.setEloPolo(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		filtro.setTipoGroupBy("histograma.localidade.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Localidade localidade = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroLocalidade filtroLocalidade = null;
			Collection colecaoConsulta = null;
			Iterator iter = hashMapTotalGeral.keySet().iterator();
			String chave;
			while(iter.hasNext()){
				chave = (String) iter.next();
				String[] chaveArray = chave.split(";");
				String idLocalidade = chaveArray[0];
				Iterator itera = colecaoCategorias.iterator();

				String descricaoPercentual = "";
				descricaoPercentual = Util.formataBigDecimal(new BigDecimal(chaveArray[1]), 2, true) + "%";
				filtro.setPercentualLigacaoEsgoto(new BigDecimal(chaveArray[1]));

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

				// Recupera Elo Polo
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = localidade.getGerenciaRegional().getId() + "-"
								+ localidade.getGerenciaRegional().getNomeAbreviado() + " / " + localidade.getUnidadeNegocio().getId()
								+ "-" + localidade.getUnidadeNegocio().getNomeAbreviado() + " / " + localidade.getLocalidade().getId()
								+ "-" + localidade.getLocalidade().getDescricao() + " / " + localidade.getId() + "-"
								+ localidade.getDescricao();

				filtro.setLocalidade(localidade);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(localidade.getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(localidade.getId());

					if(localidade.getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(localidade.getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Quadra e Setor Comercial
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaQuadraSetorComercialLocalidade(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.quadra.id,histograma.setorComercial.id,histograma.localidade.id");
		filtro.setTipoOrderBy("1,2,3");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Quadra quadra = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroQuadra filtroQuadra = null;
			Collection colecaoConsulta = null;

			int localidadeAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;
			int setorComercialAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setId(Integer.valueOf(arrayNumeracao[1]));

				Localidade localidade = new Localidade();
				localidade.setId(Integer.valueOf(arrayNumeracao[2]));

				if(setorComercialAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					setorComercialAnterior = setorComercial.getId();
				}

				if(localidadeAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					localidadeAnterior = localidade.getId();
				}

				// Mudou de Setor Comercial
				if(setorComercialAnterior != setorComercial.getId().intValue()){

					SetorComercial setorAnterior = new SetorComercial();
					setorAnterior.setId(setorComercialAnterior);

					filtro.setOpcaoTotalizacao(19);
					filtro.setSetorComercial(setorAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaSetorComercial(filtro, Boolean.FALSE));
				}

				// Mudou de Localidade
				if(localidadeAnterior != localidade.getId().intValue()){

					Localidade localAnterior = new Localidade();
					localAnterior.setId(localidadeAnterior);

					filtro.setOpcaoTotalizacao(16);
					filtro.setLocalidade(localAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidade(filtro, Boolean.FALSE));
				}

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, arrayNumeracao[0]));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.gerenciaRegional");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.localidade");

				// Recupera Quadra
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				filtro.setQuadra(quadra);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(quadra.getSetorComercial().getLocalidade().getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(quadra.getSetorComercial().getLocalidade().getId());

					if(quadra.getSetorComercial().getLocalidade().getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(quadra.getSetorComercial().getLocalidade().getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				localidadeAnterior = localidade.getId();
				setorComercialAnterior = setorComercial.getId();
			}

			// Setor Comercial
			SetorComercial setorAnterior = new SetorComercial();
			setorAnterior.setId(setorComercialAnterior);

			filtro.setOpcaoTotalizacao(19);
			filtro.setSetorComercial(setorAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaSetorComercial(filtro, Boolean.FALSE));

			// Mudou Localidade
			Localidade localAnterior = new Localidade();
			localAnterior.setId(localidadeAnterior);

			filtro.setOpcaoTotalizacao(16);
			filtro.setLocalidade(localAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaLocalidade(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Setor Comercial
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaSetorComercial(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setQuadra(null);
		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.setorComercial.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			SetorComercial setorComercial = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroSetorComercial filtroSetorComercial = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String idSetorComercial = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, idSetorComercial));

				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.gerenciaRegional");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");
				filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade.unidadeNegocio");

				// Recupera Setor Comercial
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = setorComercial.getLocalidade().getGerenciaRegional().getId() + "-"
								+ setorComercial.getLocalidade().getGerenciaRegional().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getUnidadeNegocio().getId() + "-"
								+ setorComercial.getLocalidade().getUnidadeNegocio().getNomeAbreviado() + " / "
								+ setorComercial.getLocalidade().getLocalidade().getId() + "-"
								+ setorComercial.getLocalidade().getLocalidade().getDescricao() + " / "
								+ setorComercial.getLocalidade().getId() + "-" + setorComercial.getLocalidade().getDescricao() + " / "
								+ setorComercial.getId() + "-" + setorComercial.getDescricao();

				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(setorComercial.getLocalidade().getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(setorComercial.getLocalidade().getId());

					if(setorComercial.getLocalidade().getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(setorComercial.getLocalidade().getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();

				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(idSetorComercial);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Quadra e Setor Comercial
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaQuadraSetorComercial(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.quadra.id,histograma.setorComercial.id");
		filtro.setTipoOrderBy("2,1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Quadra quadra = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroQuadra filtroQuadra = null;
			Collection colecaoConsulta = null;

			int setorComercialAnterior = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String chave = (String) iter.next();

				String[] arrayNumeracao = chave.split(";");

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setId(Integer.valueOf(arrayNumeracao[1]));

				if(setorComercialAnterior == ConstantesSistema.NUMERO_NAO_INFORMADO){
					setorComercialAnterior = setorComercial.getId();
				}

				// Mudou de Setor Comercial
				if(setorComercialAnterior != setorComercial.getId().intValue()){

					SetorComercial setorAnterior = new SetorComercial();
					setorAnterior.setId(setorComercialAnterior);

					filtro.setOpcaoTotalizacao(19);
					filtro.setSetorComercial(setorAnterior);

					colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaSetorComercial(filtro, Boolean.FALSE));
				}

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, arrayNumeracao[0]));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.gerenciaRegional");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.localidade");

				// Recupera Quadra
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				filtro.setQuadra(quadra);
				filtro.setSetorComercial(setorComercial);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				Iterator itera = colecaoCategorias.iterator();

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(quadra.getSetorComercial().getLocalidade().getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(quadra.getSetorComercial().getLocalidade().getId());

					if(quadra.getSetorComercial().getLocalidade().getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(quadra.getSetorComercial().getLocalidade().getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(chave);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

				setorComercialAnterior = setorComercial.getId();
			}

			// Setor Comercial
			SetorComercial setorAnterior = new SetorComercial();
			setorAnterior.setId(setorComercialAnterior);

			filtro.setOpcaoTotalizacao(19);
			filtro.setSetorComercial(setorAnterior);

			colecaoEmitirHistogramaEsgotoEconomia.addAll(this.emitirHistogramaEsgotoEconomiaSetorComercial(filtro, Boolean.FALSE));

		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Quadra
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<EmitirHistogramaEsgotoEconomiaHelper>
	 * @throws ControladorException
	 */
	private Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> emitirHistogramaEsgotoEconomiaQuadra(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, Boolean exibirNoArquivoTxt) throws ControladorException{

		filtro.setMedicao(null);
		filtro.setConsumoFaixaCategoria(null);

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecaoEmitirHistogramaEsgotoEconomia = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();

		String descricaoPercentual = "";
		if(filtro.getPercentualLigacaoEsgoto() != null){
			descricaoPercentual = Util.formataBigDecimal(filtro.getPercentualLigacaoEsgoto(), 2, true) + "%";
		}

		filtro.setTipoGroupBy("histograma.quadra.id");
		filtro.setTipoOrderBy("1");

		LinkedHashMap hashMapTotalGeral = this.montarEmitirHistogramaEsgotoEconomiaAgruparChaves(filtro);

		if(hashMapTotalGeral != null && !hashMapTotalGeral.isEmpty()){

			String descricaoTarifa = "TOTALIZADOR";
			if(filtro.getTarifa() != null){

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
				filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, filtro.getTarifa()));

				Collection colecaoTarifa = this.getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

				ConsumoTarifa consumoTarifa = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifa);

				descricaoTarifa = consumoTarifa.getId() + " " + consumoTarifa.getDescricao().trim();
			}

			LinkedHashMap linkedHashMapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
			Collection colecaoCategorias = linkedHashMapConsumoFaixaCategoria.keySet();
			Collection colecaoConsumoFaixaCategoria = null;
			EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = null;
			Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaEsgotoEconomiaDetalhe = null;
			List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> listaEmitirHistogramaEsgotoEconomiaDetalhe = null;

			Quadra quadra = null;
			Categoria categoria = null;

			FiltroCategoria filtroCategoria = null;
			FiltroQuadra filtroQuadra = null;
			Collection colecaoConsulta = null;

			Iterator iter = hashMapTotalGeral.keySet().iterator();

			while(iter.hasNext()){

				String idQuadra = (String) iter.next();

				Iterator itera = colecaoCategorias.iterator();

				filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, idQuadra));

				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.localidade");
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade.gerenciaRegional");

				// Recupera Quadra
				colecaoConsulta = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoConsulta);

				String descricaoOpcaoTotalizacao = quadra.getSetorComercial().getLocalidade().getId() + "-"
								+ quadra.getSetorComercial().getLocalidade().getDescricao() + " / " + quadra.getSetorComercial().getId()
								+ "-" + quadra.getSetorComercial().getDescricao() + " / " + quadra.getNumeroQuadra();

				filtro.setQuadra(quadra);

				Long totalGeralEconomiasMedido = new Long("0");
				Long totalGeralVolumeConsumoMedido = new Long("0");
				Long totalGeralVolumeFaturadoMedido = new Long("0");
				BigDecimal totalGeralReceitaMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoMedido = new Long("0");

				Long totalGeralEconomiasNaoMedido = new Long("0");
				Long totalGeralVolumeConsumoNaoMedido = new Long("0");
				Long totalGeralVolumeFaturadoNaoMedido = new Long("0");
				BigDecimal totalGeralReceitaNaoMedido = BigDecimal.ZERO;
				Long totalGeralLigacaoNaoMedido = new Long("0");

				while(itera.hasNext()){
					String idCategoria = (String) itera.next();

					emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					emitirHistograma.setDescricaoPercentual(descricaoPercentual);
					emitirHistograma.setDescricaoTarifa(descricaoTarifa);
					listaEmitirHistogramaEsgotoEconomiaDetalhe = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();

					colecaoConsumoFaixaCategoria = (Collection) linkedHashMapConsumoFaixaCategoria.get(idCategoria);

					Iterator iteraFaixas = colecaoConsumoFaixaCategoria.iterator();
					int limiteSuperiorFaixaAnterior = 0;

					Long totalEconomiasMedido = new Long("0");
					Long totalVolumeConsumoMedido = new Long("0");
					Long totalVolumeFaturadoMedido = new Long("0");
					Long totalLigacaoMedido = new Long("0");
					BigDecimal totalReceitaMedido = BigDecimal.ZERO;

					Long totalEconomiasNaoMedido = new Long("0");
					Long totalVolumeConsumoNaoMedido = new Long("0");
					Long totalVolumeFaturadoNaoMedido = new Long("0");
					Long totalLigacaoNaoMedido = new Long("0");
					BigDecimal totalReceitaNaoMedido = BigDecimal.ZERO;

					filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

					// Recupera Categoria
					colecaoConsulta = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoConsulta);

					String descricaoCategoria = categoria.getDescricao();

					filtro.setCategoria(categoria);

					while(iteraFaixas.hasNext()){

						ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) iteraFaixas.next();

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = this.montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
										filtro, consumoFaixaCategoria, limiteSuperiorFaixaAnterior);

						emitirHistograma.setDescricaoCategoria(descricaoCategoria);
						limiteSuperiorFaixaAnterior = consumoFaixaCategoria.getNumeroFaixaFim();

						listaEmitirHistogramaEsgotoEconomiaDetalhe.add(detalhe);

						totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();
						totalVolumeConsumoMedido = totalVolumeConsumoMedido + detalhe.getVolumeConsumoFaixaMedido();
						// totalVolumeFaturadoMedido = totalVolumeFaturadoMedido +
						// detalhe.getVolumeFaturadoFaixaMedido();
						// totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalLigacaoMedido = totalLigacaoMedido + detalhe.getLigacoesMedido();

						totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();
						totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido + detalhe.getVolumeConsumoFaixaNaoMedido();
						// totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido +
						// detalhe.getVolumeFaturadoFaixaNaoMedido();
						// totalReceitaNaoMedido =
						// totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());
						totalLigacaoNaoMedido = totalLigacaoNaoMedido + detalhe.getLigacoesNaoMedido();
					}

					colecaoEmitirHistogramaEsgotoEconomiaDetalhe = this
.atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
									listaEmitirHistogramaEsgotoEconomiaDetalhe, filtro.getMesAnoFaturamento(), 2,
									filtro.getIdFuncionalidadeIniciada());

					for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : colecaoEmitirHistogramaEsgotoEconomiaDetalhe){

						totalVolumeFaturadoMedido = totalVolumeFaturadoMedido + detalhe.getVolumeFaturadoFaixaMedido();
						totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido + detalhe.getVolumeFaturadoFaixaNaoMedido();
						totalReceitaMedido = totalReceitaMedido.add(detalhe.getReceitaMedido());
						totalReceitaNaoMedido = totalReceitaNaoMedido.add(detalhe.getReceitaNaoMedido());

					}

					emitirHistograma.setIdCategoria(categoria.getId());
					emitirHistograma.setIdGerenciaRegional(quadra.getSetorComercial().getLocalidade().getGerenciaRegional().getId());
					emitirHistograma.setIdLocalidade(quadra.getSetorComercial().getLocalidade().getId());

					if(quadra.getSetorComercial().getLocalidade().getLocalidade() != null){

						emitirHistograma.setIdLocalidadeVinculada(quadra.getSetorComercial().getLocalidade().getLocalidade().getId());

					}

					emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);
					emitirHistograma.setDescricaoFaixa("TOTAL");
					emitirHistograma.setExibirNoArquivoTxt(exibirNoArquivoTxt);
					emitirHistograma.setTotalEconomiasMedido(totalEconomiasMedido);
					emitirHistograma.setTotalVolumeConsumoMedido(totalVolumeConsumoMedido);
					emitirHistograma.setTotalVolumeFaturadoMedido(totalVolumeFaturadoMedido);
					emitirHistograma.setTotalReceitaMedido(totalReceitaMedido);
					emitirHistograma.setTotalLigacoesMedido(totalLigacaoMedido);
					emitirHistograma.setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(colecaoEmitirHistogramaEsgotoEconomiaDetalhe);
					emitirHistograma.setTotalEconomiasNaoMedido(totalEconomiasNaoMedido);
					emitirHistograma.setTotalVolumeConsumoNaoMedido(totalVolumeConsumoNaoMedido);
					emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalVolumeFaturadoNaoMedido);
					emitirHistograma.setTotalReceitaNaoMedido(totalReceitaNaoMedido);
					emitirHistograma.setTotalLigacoesNaoMedido(totalLigacaoNaoMedido);

					colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);

					totalGeralEconomiasMedido = totalGeralEconomiasMedido + emitirHistograma.getTotalEconomiasMedido();
					totalGeralVolumeConsumoMedido = totalGeralVolumeConsumoMedido + emitirHistograma.getTotalVolumeConsumoMedido();
					totalGeralVolumeFaturadoMedido = totalGeralVolumeFaturadoMedido + emitirHistograma.getTotalVolumeFaturadoMedido();
					totalGeralReceitaMedido = totalGeralReceitaMedido.add(emitirHistograma.getTotalReceitaMedido());
					totalGeralLigacaoMedido = totalGeralLigacaoMedido + emitirHistograma.getTotalLigacoesMedido();

					totalGeralEconomiasNaoMedido = totalGeralEconomiasNaoMedido + emitirHistograma.getTotalEconomiasNaoMedido();
					totalGeralVolumeConsumoNaoMedido = totalGeralVolumeConsumoNaoMedido + emitirHistograma.getTotalVolumeConsumoNaoMedido();
					totalGeralVolumeFaturadoNaoMedido = totalGeralVolumeFaturadoNaoMedido
									+ emitirHistograma.getTotalVolumeFaturadoNaoMedido();
					totalGeralReceitaNaoMedido = totalGeralReceitaNaoMedido.add(emitirHistograma.getTotalReceitaNaoMedido());
					totalGeralLigacaoNaoMedido = totalGeralLigacaoNaoMedido + emitirHistograma.getTotalLigacoesNaoMedido();
				}

				emitirHistograma = (EmitirHistogramaAguaEsgotoEconomiaHelper) hashMapTotalGeral.get(idQuadra);

				emitirHistograma.setDescricaoPercentual(descricaoPercentual);
				emitirHistograma.setDescricaoTarifa(descricaoTarifa);
				emitirHistograma.setDescricaoCategoria(null);
				emitirHistograma.setDescricaoFaixa("TOTAL GERAL");
				emitirHistograma.setOpcaoTotalizacao(descricaoOpcaoTotalizacao);

				// Medido(COM HIDROMETRO)
				emitirHistograma.setTotalEconomiasMedido(totalGeralEconomiasMedido);
				emitirHistograma.setTotalVolumeConsumoMedido(totalGeralVolumeConsumoMedido);
				emitirHistograma.setTotalVolumeFaturadoMedido(totalGeralVolumeFaturadoMedido);
				emitirHistograma.setTotalReceitaMedido(totalGeralReceitaMedido);
				emitirHistograma.setTotalLigacoesMedido(totalGeralLigacaoMedido);

				// Não Medido(SEM HIDROMETRO)
				emitirHistograma.setTotalEconomiasNaoMedido(totalGeralEconomiasNaoMedido);
				emitirHistograma.setTotalVolumeConsumoNaoMedido(totalGeralVolumeConsumoNaoMedido);
				emitirHistograma.setTotalVolumeFaturadoNaoMedido(totalGeralVolumeFaturadoNaoMedido);
				emitirHistograma.setTotalReceitaNaoMedido(totalGeralReceitaNaoMedido);
				emitirHistograma.setTotalLigacoesNaoMedido(totalGeralLigacaoNaoMedido);
				emitirHistograma.setExibirNoArquivoTxt(Boolean.FALSE);

				// TOTAL GERAL
				colecaoEmitirHistogramaEsgotoEconomia.add(emitirHistograma);
			}
		}

		return colecaoEmitirHistogramaEsgotoEconomia;

	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Monta o objeto EmitirHistogramaEsgotoEconomiaDetalheHelper
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @param ConsumoFaixaCategoria
	 * @return EmitirHistogramaEsgotoEconomiaDetalheHelper
	 * @throws ControladorException
	 */
	private EmitirHistogramaAguaEsgotoEconomiaDetalheHelper montarEmitirHistogramaEsgotoEconomiaDetalheHelper(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro, ConsumoFaixaCategoria consumoFaixaCategoria,
					int limiteSuperiorFaixaAnterior) throws ControladorException{

		EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = new EmitirHistogramaAguaEsgotoEconomiaDetalheHelper();

		detalhe.setFaixa(consumoFaixaCategoria);

		BigDecimal consumoMedio = null;
		BigDecimal consumoExcedente = null;

		filtro.setConsumoFaixaCategoria(consumoFaixaCategoria);

		detalhe.setDescricaoFaixa(consumoFaixaCategoria.getNumeroFaixaInicio() + " a " + consumoFaixaCategoria.getNumeroFaixaFim());

		// Pesquisa com indicador de Hidrometro SIM
		filtro.setMedicao(ConstantesSistema.INDICADOR_USO_ATIVO);

		Object[] valoresSomatorio = null;

		boolean temMedido = false;
		boolean temNaoMedido = false;

		try{
			valoresSomatorio = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoEconomia(filtro); // MGRB
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(valoresSomatorio != null){

			detalhe.setEconomiasMedido((Long) valoresSomatorio[2]);
			detalhe.setVolumeConsumoFaixaMedido((Long) valoresSomatorio[3]);
			detalhe.setVolumeFaturadoFaixaMedido((Long) valoresSomatorio[4]);
			detalhe.setReceitaMedido((BigDecimal) valoresSomatorio[5]);
			detalhe.setLigacoesMedido((Long) valoresSomatorio[6]);
			detalhe.setPercentualEsgoto((Short) valoresSomatorio[7]);

			if(detalhe.getEconomiasMedido() > 0){
				consumoMedio = new BigDecimal(detalhe.getVolumeConsumoFaixaMedido()).divide(new BigDecimal(detalhe.getEconomiasMedido()),
								4, BigDecimal.ROUND_HALF_UP);

				detalhe.setConsumoMedioMedido(consumoMedio);
			}else{
				detalhe.setConsumoMedioMedido(BigDecimal.ZERO);
			}



			if(limiteSuperiorFaixaAnterior > 0){
				consumoExcedente = detalhe.getConsumoMedioMedido().subtract(new BigDecimal(limiteSuperiorFaixaAnterior));
			}

			detalhe.setConsumoExcedenteMedido(consumoExcedente);
			temMedido = true;
		}else{
			detalhe.setConsumoMedioMedido(BigDecimal.ZERO);
		}

		// Pesquisa com indicador de Hidrometro Nao
		filtro.setMedicao(ConstantesSistema.INDICADOR_USO_DESATIVO);

		try{
			valoresSomatorio = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoEconomia(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(valoresSomatorio != null){

			detalhe.setEconomiasNaoMedido((Long) valoresSomatorio[2]);
			detalhe.setVolumeConsumoFaixaNaoMedido((Long) valoresSomatorio[3]);
			detalhe.setVolumeFaturadoFaixaNaoMedido((Long) valoresSomatorio[4]);
			detalhe.setReceitaNaoMedido((BigDecimal) valoresSomatorio[5]);
			detalhe.setLigacoesNaoMedido((Long) valoresSomatorio[6]);
			detalhe.setPercentualEsgoto((Short) valoresSomatorio[7]);

			if(detalhe.getEconomiasNaoMedido() > 0){
				consumoMedio = new BigDecimal(detalhe.getVolumeConsumoFaixaNaoMedido()).divide(
								new BigDecimal(detalhe.getEconomiasNaoMedido()), 4, BigDecimal.ROUND_HALF_UP);

				detalhe.setConsumoMedioNaoMedido(consumoMedio);
			}else{
				detalhe.setConsumoMedioNaoMedido(BigDecimal.ZERO);
			}

			if(limiteSuperiorFaixaAnterior > 0){
				consumoExcedente = detalhe.getConsumoMedioNaoMedido().subtract(new BigDecimal(limiteSuperiorFaixaAnterior));
			}

			detalhe.setConsumoExcedenteNaoMedido(consumoExcedente);
			temNaoMedido = true;
		}else{
			detalhe.setConsumoMedioNaoMedido(BigDecimal.ZERO);
		}

		// Caso tenha algum valor então exite histograma para adcionar na colecao
		detalhe.setExisteHistograma(temMedido || temNaoMedido);

		return detalhe;
	}

	/**
	 * [UC0606] Emitir Histograma de Esgoto Por Economia
	 * Agrupa as chaves para totalizacao
	 * 
	 * @author Rafael Pinto
	 * @param grupoPercentuaisEsgoto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return LinkedHashMap
	 * @throws ControladorException
	 */
	private LinkedHashMap montarEmitirHistogramaEsgotoEconomiaAgruparChaves(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro)
					throws ControladorException{

		LinkedHashMap hashMapTotalizacao = new LinkedHashMap();
		Collection<Object[]> colecao = null;

		try{
			// filtro.setMedicao(ConstantesSistema.INDICADOR_USO_ATIVO);
			colecao = this.repositorioFaturamento.pesquisarEmitirHistogramaEsgotoEconomiaChavesAgrupadas(filtro);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// Montando os grupos de perfil de ligacao de esgoto
		Collection<LigacaoEsgotoPerfil> percentuaisEsgoto = listarLigacaoEsgotoPerfil();
		Collection<BigDecimal> grupoPercentuaisEsgoto = new ArrayList<BigDecimal>();
		for(LigacaoEsgotoPerfil ligacaoEsgotoPerfil : percentuaisEsgoto){
			if(Util.isVazioOrNulo(filtro.getColecaoPercentualLigacaoEsgoto())
							|| filtro.getColecaoPercentualLigacaoEsgoto().contains(
											ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada())) grupoPercentuaisEsgoto
							.add(ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada());
		}

		filtro.setColecaoPercentualLigacaoEsgoto(null);

		if(colecao != null && !colecao.isEmpty()){
			Iterator iterator = colecao.iterator();
			while(iterator.hasNext()){
				String tipoTotalizacao = "0";
				int chaveQuadra = 0;
				int chaveSetorComercial = 0;
				int chaveLocalidade = 0;
				int chaveElo = 0;
				int chaveUnidade = 0;
				int chaveGerencia = 0;

				if(filtro.getOpcaoTotalizacao() == 2 || filtro.getOpcaoTotalizacao() == 6){
					chaveGerencia = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveGerencia;
				}else if(filtro.getOpcaoTotalizacao() == 3 || filtro.getOpcaoTotalizacao() == 7){
					Object[] objeto = (Object[]) iterator.next();
					chaveUnidade = (Integer) objeto[0];
					chaveGerencia = (Integer) objeto[1];
					tipoTotalizacao = chaveUnidade + ";" + chaveGerencia;
				}else if(filtro.getOpcaoTotalizacao() == 4 || filtro.getOpcaoTotalizacao() == 8){
					Object[] objeto = (Object[]) iterator.next();
					chaveElo = (Integer) objeto[0];
					chaveUnidade = (Integer) objeto[1];
					chaveGerencia = (Integer) objeto[2];
					tipoTotalizacao = chaveElo + ";" + chaveUnidade + ";" + chaveGerencia;
				}else if(filtro.getOpcaoTotalizacao() == 5 || filtro.getOpcaoTotalizacao() == 9){
					Object[] objeto = (Object[]) iterator.next();
					chaveLocalidade = (Integer) objeto[0];
					chaveElo = (Integer) objeto[1];
					chaveUnidade = (Integer) objeto[2];
					chaveGerencia = (Integer) objeto[3];
					tipoTotalizacao = chaveLocalidade + ";" + chaveElo + ";" + chaveUnidade + ";" + chaveGerencia;
				}else if(filtro.getOpcaoTotalizacao() == 10){
					chaveUnidade = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveUnidade;
				}else if(filtro.getOpcaoTotalizacao() == 11){
					Object[] objeto = (Object[]) iterator.next();
					chaveElo = (Integer) objeto[0];
					chaveUnidade = (Integer) objeto[1];
					tipoTotalizacao = chaveElo + ";" + chaveUnidade;
				}else if(filtro.getOpcaoTotalizacao() == 12){
					Object[] objeto = (Object[]) iterator.next();
					chaveLocalidade = (Integer) objeto[0];
					chaveElo = (Integer) objeto[1];
					chaveUnidade = (Integer) objeto[2];
					tipoTotalizacao = chaveLocalidade + ";" + chaveElo + ";" + chaveUnidade;
				}else if(filtro.getOpcaoTotalizacao() == 13){
					chaveElo = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveElo;
				}else if(filtro.getOpcaoTotalizacao() == 14){
					Object[] objeto = (Object[]) iterator.next();
					chaveLocalidade = (Integer) objeto[0];
					chaveElo = (Integer) objeto[1];
					tipoTotalizacao = chaveLocalidade + ";" + chaveElo;
				}else if(filtro.getOpcaoTotalizacao() == 15){
					Object[] objeto = (Object[]) iterator.next();
					chaveSetorComercial = (Integer) objeto[0];
					chaveLocalidade = (Integer) objeto[1];
					chaveElo = (Integer) objeto[2];
					tipoTotalizacao = chaveSetorComercial + ";" + chaveLocalidade + ";" + chaveElo;
				}else if(filtro.getOpcaoTotalizacao() == 16){
					chaveLocalidade = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveLocalidade;
				}else if(filtro.getOpcaoTotalizacao() == 17){
					Object[] objeto = (Object[]) iterator.next();
					chaveSetorComercial = (Integer) objeto[0];
					chaveLocalidade = (Integer) objeto[1];
					tipoTotalizacao = chaveSetorComercial + ";" + chaveLocalidade;
				}else if(filtro.getOpcaoTotalizacao() == 18){
					Object[] objeto = (Object[]) iterator.next();
					chaveQuadra = (Integer) objeto[0];
					chaveSetorComercial = (Integer) objeto[1];
					chaveLocalidade = (Integer) objeto[2];
					tipoTotalizacao = chaveQuadra + ";" + chaveSetorComercial + ";" + chaveLocalidade;
				}else if(filtro.getOpcaoTotalizacao() == 19){
					chaveSetorComercial = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveSetorComercial;
				}else if(filtro.getOpcaoTotalizacao() == 20){
					Object[] objeto = (Object[]) iterator.next();
					chaveQuadra = (Integer) objeto[0];
					chaveSetorComercial = (Integer) objeto[1];
					tipoTotalizacao = chaveQuadra + ";" + chaveSetorComercial;
				}else if(filtro.getOpcaoTotalizacao() == 21){
					chaveQuadra = (Integer) iterator.next();
					tipoTotalizacao = "" + chaveQuadra;
				}
				for(BigDecimal vlPercet : grupoPercentuaisEsgoto){
					EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistograma = new EmitirHistogramaAguaEsgotoEconomiaHelper();
					hashMapTotalizacao.put(tipoTotalizacao.concat(";").concat(vlPercet.toString()), emitirHistograma);
				}
			}
		}

		return hashMapTotalizacao;
	}

	/**
	 * [UC0099] Emitir Relação Sintética de Faturas
	 * 
	 * @author Rafael Pinto
	 * @date 19/11/2007
	 * @param colecaoFatura
	 * @throws ControladorException
	 */
	public void emitirRelacaoSinteticaFaturas(Collection<Fatura> colecaoFatura, Integer anoMesFaturamento) throws ControladorException{

		Collection<FaturaClienteResponsavelHelper> colecaoFaturaHelper = new ArrayList();

		if(colecaoFatura != null){

			for(Fatura fatura : colecaoFatura){

				FaturaClienteResponsavelHelper faturaHelper = new FaturaClienteResponsavelHelper();

				Cliente cliente = this.getControladorCliente().pesquisarCliente(fatura.getCliente().getId());

				int anoMes = fatura.getAnoMesReferencia();

				faturaHelper.setCodigoCliente(cliente.getId().toString());
				faturaHelper.setNomeCliente(cliente.getNome());
				faturaHelper.setMesAno(Util.formatarAnoMesParaMesAno(anoMes));

				faturaHelper.setValorTotalAPagar(Util.formatarMoedaReal(fatura.getDebito()));

				colecaoFaturaHelper.add(faturaHelper);
			}

			RelatorioRelacaoSinteticaFaturas relatorio = new RelatorioRelacaoSinteticaFaturas(Usuario.USUARIO_BATCH);

			relatorio.addParametro("colecaoFaturas", colecaoFaturaHelper);
			relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

			byte[] relatorioGerado = (byte[]) relatorio.executar();

			try{

				String mesReferencia = anoMesFaturamento.toString().substring(4, 6);
				String anoReferencia = anoMesFaturamento.toString().substring(0, 4);

				String nomeRelatorio = "RELATORIO_RELACAO_SINTETICA_FATURA_" + mesReferencia + "_" + anoReferencia + ".PDF";

				File leitura = new File(nomeRelatorio);
				FileOutputStream out = new FileOutputStream(leitura.getAbsolutePath());
				out.write(relatorioGerado);
				out.flush();
				out.close();

			}catch(IOException e){
				throw new ControladorException("erro.sistema", e);
			}

		}

	}

	private Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> atualizarVolumeFaturadoEValorAguaHistogramaAguaEsgotoEconomia(
					List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> detalhes, Integer anoMesReferencia, int icAguaEsgoto,
					Integer idFuncionalidadeInciada)
					throws ControladorException{

		LOGGER.debug("=> PAGINA ******************************************");
		Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> retorno = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper>();
		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		
		
		if(detalhes != null){

			Map<String, Long> totalEconomiasFaixa = this.obterTotalEconomiasFaixa(detalhes);

			Map<String, Long> volumeFaturadoFaixa = null;

			ConsumoFaixaCategoria faixa = null;
			Integer difConsumoFaixa = null;

			StringBuffer buffer = new StringBuffer();
			for(int index = 0; index < detalhes.size(); index++){
				EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = detalhes.get(index);
				faixa = detalhe.getFaixa();
				difConsumoFaixa = this.obterDiferencaConsumoFaixa(faixa, index);
				volumeFaturadoFaixa = this.obterVolumeFaturadoPelaFaixa(index, totalEconomiasFaixa, difConsumoFaixa, detalhes);
				detalhe.setVolumeFaturadoFaixaMedido(volumeFaturadoFaixa.get(VOLUME_FATURADO_MEDIDO));
				detalhe.setVolumeFaturadoFaixaNaoMedido(volumeFaturadoFaixa.get(VOLUME_FATURADO_NAO_MEDIDO));
				retorno.add(detalhe);
				BigDecimal valorTarifaVigente = this.getControladorFaturamento().obterValorTarifaVigentePorCategoria(
								sistemaParametro.getConsumoTarifaDefault().getId(), anoMesReferencia, faixa.getCategoria().getId(),
								faixa.getNumeroFaixaInicio(), faixa.getNumeroFaixaFim(), index == 0);

				// GARANTE A EXISTENCIA DE VALOR DE TARIFA PARA A FAIXA
				verificarExistenciaValorTarifaParaFaixa(anoMesReferencia, idFuncionalidadeInciada, faixa, valorTarifaVigente);

				BigDecimal valorReceitaMedido = BigDecimal.ZERO;
				BigDecimal valorReceitaNaoMedido = BigDecimal.ZERO;

				// SE FAIXA 01: Pegar a qtd de economias SENAO volumeFaturado
				if(index == 0){
					valorReceitaMedido = BigDecimal.valueOf(totalEconomiasFaixa.get(TOTAL_MEDIDO));
					valorReceitaNaoMedido = BigDecimal.valueOf(totalEconomiasFaixa.get(TOTAL_NAO_MEDIDO));
				}else{
					valorReceitaMedido = BigDecimal.valueOf(detalhe.getVolumeFaturadoFaixaMedido());
					valorReceitaNaoMedido = BigDecimal.valueOf(detalhe.getVolumeFaturadoFaixaNaoMedido());
				}

				// valor da receita é multiplicado pela TARIFA da faixa
				valorReceitaMedido = valorReceitaMedido.multiply(valorTarifaVigente);
				valorReceitaNaoMedido = valorReceitaNaoMedido.multiply(valorTarifaVigente);

				// SE esgoto: multiplica pelo % esgoto.
				if(!Integer.valueOf(1).equals(icAguaEsgoto)){
					valorReceitaMedido = valorReceitaMedido.multiply(BigDecimal.valueOf(detalhe.getPercentualEsgoto()));
					valorReceitaNaoMedido = valorReceitaNaoMedido.multiply(BigDecimal.valueOf(detalhe.getPercentualEsgoto()));
				}

				detalhe.setReceitaMedido(valorReceitaMedido);
				detalhe.setReceitaNaoMedido(valorReceitaNaoMedido);

				buffer.append("[PARA]=====>");
				buffer.append("\n\t\t\t ConsumoTarifaDefault => ");
				buffer.append(sistemaParametro.getConsumoTarifaDefault().getId());
				buffer.append("\n\t\t\t REFERENCIA => ");
				buffer.append(anoMesReferencia);
				buffer.append("\n\t\t\t CATEGORIA  => ");
				buffer.append(faixa.getCategoria().getId());
				buffer.append('-');
				buffer.append(faixa.getCategoria().getDescricao());
				buffer.append("\n\t\t\t FAIXA_INI  => ");
				buffer.append(faixa.getNumeroFaixaInicio());
				buffer.append("\n\t\t\t FAIXA_FIM  => ");
				buffer.append(faixa.getNumeroFaixaFim());
				buffer.append("\n------------------------------");
				buffer.append("\n\t\t\t TARIFA  => ");
				buffer.append(valorTarifaVigente);
				if(index == 0){
					buffer.append("\n\t\t\t QTD_ECON_MED  => ");
					buffer.append(detalhe.getEconomiasMedido());
					buffer.append("\n\t\t\t QTD_ECON_N_MED  => ");
					buffer.append(detalhe.getEconomiasNaoMedido());
				}else{
					buffer.append("\n\t\t\t VOL_FAT_MED  => ");
					buffer.append(detalhe.getVolumeFaturadoFaixaMedido());
					buffer.append("\n\t\t\t VOL_FAT_N_MED  => ");
					buffer.append(detalhe.getVolumeFaturadoFaixaNaoMedido());
				}
				buffer.append("\n\t\t\t RECEITA MEDIDO  => ");
				buffer.append(valorReceitaMedido);
				buffer.append("\n\t\t\t RECEITA NÃO MEDIDO  => ");
				buffer.append(valorReceitaNaoMedido);
				buffer.append("\n------------------------------\n");
				LOGGER.debug(buffer.toString());
				// LOGGER.info("ESGOTO--->" + detalhe.getPercentualEsgoto());
				buffer.delete(0, buffer.length());
			}

		}

		return retorno;

	}

	private void verificarExistenciaValorTarifaParaFaixa(Integer anoMesReferencia, Integer idFuncionalidadeInciada,
					ConsumoFaixaCategoria faixa, BigDecimal ValorTarifaVigente) throws ControladorException{

		if(ValorTarifaVigente == null){

			ControladorAcessoSEJB controladorAcesso = new ControladorAcessoSEJB();
			String mensagem = ConstantesAplicacao.get("atencao_histograma_valor_tarifa_vigente_nao_encontrado");
			mensagem = mensagem.replace("{0}", Util.formatarAnoMesSemBarraParaMesAnoComBarra(anoMesReferencia));
			mensagem = mensagem.replace("Não", "Nao");
			mensagem = mensagem.replace("referência", "referencia");

			if(faixa.getCategoria().getDescricao() == null){

				FiltroCategoria filtroCategoria = new FiltroCategoria();
				filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, faixa.getCategoria().getId()));
				Collection categorias = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());
				Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(categorias);

				mensagem = mensagem.replace("{1}", categoria.getDescricao());
			}else{

				mensagem = mensagem.replace("{1}", faixa.getCategoria().getDescricao());
			}
			mensagem = mensagem.replace("{2}", faixa.getNumeroFaixaInicio() + " a " + faixa.getNumeroFaixaFim());
			controladorAcesso.registrarLogExecucaoProcesso(idFuncionalidadeInciada, mensagem);
			throw new ControladorException("atencao_histograma_valor_tarifa_vigente_nao_encontrado", null,
							Util.formatarAnoMesSemBarraParaMesAnoComBarra(anoMesReferencia), faixa.getCategoria().getDescricao(),
							faixa.getNumeroFaixaInicio() + " a " + faixa.getNumeroFaixaFim());
		}
	}
	
	public Map<String, Long> obterVolumeFaturadoPelaFaixa(int index, Map<String, Long> totalEconomiasFaixa, Integer difConsumoFaixa,
					List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> detalhes) throws ControladorException{

		Map<String, Long> volumeFaturadoPelaFaixa = new HashMap<String, Long>();
		Long volumeFaturadoMedido = Long.valueOf(0);
		Long volumeFaturadoNaoMedido = Long.valueOf(0);

		if(index == 0){

			volumeFaturadoMedido = (totalEconomiasFaixa.get(TOTAL_MEDIDO) * difConsumoFaixa);

			volumeFaturadoNaoMedido = (totalEconomiasFaixa.get(TOTAL_NAO_MEDIDO) * difConsumoFaixa);

		}else{

			int i = index;

			i = (i + 1);

			if(i < detalhes.size()){

				EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = null;

				while(i < detalhes.size()){

					detalhe = detalhes.get(i);

					volumeFaturadoMedido = (volumeFaturadoMedido + detalhe.getEconomiasMedido());

					volumeFaturadoNaoMedido = (volumeFaturadoNaoMedido + detalhe.getEconomiasNaoMedido());

					i++;

				}

				detalhe = detalhes.get(index);

				Map<String, Long> volumeASerReduzido = this.obterVolumeASerReduzido(index, detalhes);

				volumeFaturadoMedido = ((volumeFaturadoMedido * difConsumoFaixa) + volumeASerReduzido.get("VOLUME_REDUZIDO_MEDIDO"));

				volumeFaturadoNaoMedido = (volumeFaturadoNaoMedido * difConsumoFaixa)
								+ volumeASerReduzido.get("VOLUME_REDUZIDO_NAO_MEDIDO");

			}else{

				Map<String, Long> volumeASerReduzido = this.obterVolumeASerReduzido(index, detalhes);

				volumeFaturadoMedido = ((volumeFaturadoMedido * difConsumoFaixa) + volumeASerReduzido.get("VOLUME_REDUZIDO_MEDIDO"));

				volumeFaturadoNaoMedido = (volumeFaturadoNaoMedido * difConsumoFaixa)
								+ volumeASerReduzido.get("VOLUME_REDUZIDO_NAO_MEDIDO");

			}

		}

		volumeFaturadoPelaFaixa.put(VOLUME_FATURADO_MEDIDO, volumeFaturadoMedido);

		volumeFaturadoPelaFaixa.put(VOLUME_FATURADO_NAO_MEDIDO, volumeFaturadoNaoMedido);

		return volumeFaturadoPelaFaixa;

	}

	public Map<String, Long> obterVolumeASerReduzido(int index, List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> detalhes){

		Map<String, Long> volumeReduzido = new HashMap<String, Long>();

		Long volumeReduzidoMedido = Long.valueOf(0);

		Long volumeReduzidoNaoMedido = Long.valueOf(0);

		Integer difConsumoFaixa = null;

		EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalheTratado = detalhes.get(index);
		
		volumeReduzidoMedido = detalheTratado.getVolumeConsumoFaixaMedido();
		
		volumeReduzidoNaoMedido = detalheTratado.getVolumeConsumoFaixaNaoMedido();

		int i = 0;

		for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : detalhes){

			difConsumoFaixa = this.obterDiferencaConsumoFaixa(detalhe.getFaixa(), i);
			
			volumeReduzidoMedido = (volumeReduzidoMedido - (detalheTratado.getEconomiasMedido() * difConsumoFaixa));

			if(detalheTratado.getEconomiasNaoMedido() != null){

				volumeReduzidoNaoMedido = (volumeReduzidoNaoMedido - (detalheTratado.getEconomiasNaoMedido() * difConsumoFaixa));

			}

			if(i == (index - 1)){

				break;

			}

			i++;

		}

		volumeReduzido.put("VOLUME_REDUZIDO_MEDIDO", volumeReduzidoMedido);

		volumeReduzido.put("VOLUME_REDUZIDO_NAO_MEDIDO", volumeReduzidoNaoMedido);

		return volumeReduzido;

	}

	private Map<String, Long> obterTotalEconomiasFaixa(List<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> detalhes){

		Map<String, Long> retorno = new HashMap<String, Long>();

		Long totalEconomiasMedido = Long.valueOf(0);

		Long totalEconomiasNaoMedido = Long.valueOf(0);

		if(detalhes != null){

			for(EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe : detalhes){

				totalEconomiasMedido = totalEconomiasMedido + detalhe.getEconomiasMedido();

				if(detalhe.getEconomiasNaoMedido() != null){

					totalEconomiasNaoMedido = totalEconomiasNaoMedido + detalhe.getEconomiasNaoMedido();

				}

			}

		}

		retorno.put(TOTAL_MEDIDO, totalEconomiasMedido);

		retorno.put(TOTAL_NAO_MEDIDO, totalEconomiasNaoMedido);

		return retorno;

	}

	private Integer obterDiferencaConsumoFaixa(ConsumoFaixaCategoria faixa, int index){

		Integer difConsumoFaixa = null;

		if(index == 0){

			difConsumoFaixa = faixa.getNumeroFaixaFim();

		}else{

			difConsumoFaixa = ((faixa.getNumeroFaixaFim() - faixa.getNumeroFaixaInicio()) + 1);

		}

		return difConsumoFaixa;

	}

	public StringBuffer obterDadosHistogramaAguaEsgotoEconomia(FiltrarEmitirHistogramaAguaEconomiaHelper filtroAgua,
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtroEsgoto) throws ControladorException{
		
		StringBuffer sb = new StringBuffer();
		List<LinhaArquivoHistogramaAguaEsgoto> linhas = new ArrayList<LinhaArquivoHistogramaAguaEsgoto>();
		Integer tipoArquivo = filtroAgua.getTipoArquivo();

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> collHistogramaAgua = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();
		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> collHistogramaEsgoto = new ArrayList<EmitirHistogramaAguaEsgotoEconomiaHelper>();
		
		if(tipoArquivo == null){

			collHistogramaAgua = this.pesquisarEmitirHistogramaAguaEconomia(filtroAgua);
			collHistogramaEsgoto = this.pesquisarEmitirHistogramaEsgotoEconomia(filtroEsgoto);

		}else if(tipoArquivo.equals(Integer.valueOf(1))){

			collHistogramaAgua = this.pesquisarEmitirHistogramaAguaEconomia(filtroAgua);

		}else if(tipoArquivo.equals(Integer.valueOf(2))){

			collHistogramaEsgoto = this.pesquisarEmitirHistogramaEsgotoEconomia(filtroEsgoto);

		}

		Integer anoMesFaturamento = filtroAgua.getMesAnoFaturamento();
		BigDecimal totalLigacoes = null;
		BigDecimal totalEconomias = null;

		for(EmitirHistogramaAguaEsgotoEconomiaHelper helper : collHistogramaAgua){

			if(helper.getExibirNoArquivoTxt()){

				Object[] totais = this.getControladorFaturamento().obterTotalEconomiasETotalLigacoesHistogramaAgua(
								helper.getIdGerenciaRegional(), helper.getIdLocalidade(), helper.getIdCategoria());

				totalLigacoes = totais[0] != null ? (BigDecimal) totais[0] : BigDecimal.ZERO;
				totalEconomias = totais[1] != null ? (BigDecimal) totais[1] : BigDecimal.ZERO;

				linhas.add(new LinhaArquivoHistogramaAguaEsgoto(helper, anoMesFaturamento,
								helper.getIdGerenciaRegional(),
								helper.getIdLocalidadeVinculada(), helper.getIdLocalidade(), 1, helper.getIdCategoria(), totalLigacoes,
										totalEconomias));

			}

		}
		
		for(EmitirHistogramaAguaEsgotoEconomiaHelper helper : collHistogramaEsgoto){

			Short percentualEsgoto = null;

			if(helper.getExibirNoArquivoTxt()){

				if(helper.getColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe() != null
								&& !helper.getColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe().isEmpty()){

					percentualEsgoto = helper.getColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe().iterator().next().getPercentualEsgoto();

					helper.setPercentualEsgoto(percentualEsgoto);

					Object[] totais = this.getControladorFaturamento().obterTotalEconomiasETotalLigacoesHistogramaAgua(
									helper.getIdGerenciaRegional(), helper.getIdLocalidade(), helper.getIdCategoria());

					totalLigacoes = totais[0] != null ? (BigDecimal) totais[0] : BigDecimal.ZERO;
					totalEconomias = totais[1] != null ? (BigDecimal) totais[1] : BigDecimal.ZERO;

					linhas.add(new LinhaArquivoHistogramaAguaEsgoto(helper, anoMesFaturamento, helper.getIdGerenciaRegional(), helper
									.getIdLocalidadeVinculada(), helper.getIdLocalidade(), 2, helper.getIdCategoria(), totalLigacoes,
									totalEconomias));

				}

			}

		}

		sb.append(" LAYOUT DO ARQUIVO.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" ==================");
		sb.append(System.getProperty("line.separator"));
		sb.append(" Obs: As informações estao separadas pelo caracter (;) e descritas sequencialmente abaixo.");
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		sb.append("  1 - ANOMES do faturamento.");
		sb.append(System.getProperty("line.separator"));
		sb.append("  2 - Codigo da Regional.");
		sb.append(System.getProperty("line.separator"));
		sb.append("  3 - Codigo da Localidade Vinculada");
		sb.append(System.getProperty("line.separator"));
		sb.append("  4 - Codigo da Localidade.");
		sb.append(System.getProperty("line.separator"));
		sb.append("  5 - Tipo do Histograma. Com os valores 1.Agua   2.Esgoto.");
		sb.append(System.getProperty("line.separator"));
		sb.append("  6 - Quando do tipo ESGOTO reprenta o Percentual de Esgoto.");
		sb.append(System.getProperty("line.separator"));
		sb.append("  7 - Codigo da Categoria.");
		sb.append(System.getProperty("line.separator"));
		sb.append("  8 - Quantidade de Economias Com Hidrometros.");
		sb.append(System.getProperty("line.separator"));
		sb.append("  9 - Volume Consumido com Hidrometro.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 10 - Volume faturado com Hidrometro.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 11 - Valor da Receita com Hidrometro.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 12 - Quantidade de Economias sem Hidrometros.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 13 - Volume Consumido sem Hidrometro.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 14 - Volume Faturado sem Hidrometro.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 15 - Valor da Receita sem Hidrometro.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 16 - Quantidade de Ligacoes com Hidrometros.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 17 - Quantidade de Ligacoes sem Hidrometros.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 18 - Quantidade de Ligacoes Cadastradas.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" 19 - Quantidade de Economias Cadastradas.");
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		sb.append(" Obs: As informações de sequencais 18 e 19 se referem ao cadastro de usuarios na data da");
		sb.append(System.getProperty("line.separator"));
		sb.append("      geracao deste arquivo.");
		sb.append(System.getProperty("line.separator"));
		sb.append(" DATA DA GERACAO: " + Util.formatarData(new Date()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));

		this.ordenarLinhasArquivoHistogramaAguaEsgoto(linhas);

		for(LinhaArquivoHistogramaAguaEsgoto linha : linhas){

			sb.append(linha.getLinhaFormatada());
			sb.append(System.getProperty("line.separator"));

		}

		return sb;

	}
	
	private void ordenarLinhasArquivoHistogramaAguaEsgoto(List<LinhaArquivoHistogramaAguaEsgoto> linhas){

		List sortFields = new ArrayList();

		sortFields.add(new BeanComparator("anoMesRef"));
		sortFields.add(new BeanComparator("regional"));
		sortFields.add(new BeanComparator("localVinc"));
		sortFields.add(new BeanComparator("local"));
		sortFields.add(new BeanComparator("categoria"));
		sortFields.add(new BeanComparator("tipoAguaEsg"));

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort(linhas, multiSort);

	}

	public Collection<LigacaoEsgotoPerfil> listarLigacaoEsgotoPerfil() throws ControladorException{

		FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();

		filtroLigacaoEsgotoPerfil.setConsultaSemLimites(true);
		filtroLigacaoEsgotoPerfil.setCampoOrderBy(FiltroLigacaoEsgotoPerfil.PERCENTUAL);
		filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		return ServiceLocator.getInstancia().getControladorUtil().pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());
	}
}