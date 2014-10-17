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

package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar o relatório de imóveis por endereço
 * 
 * @author Carlos Chrystian Ramos
 * @created 28 de Janeiro de 2012
 */
public class RelatorioRelacaoImoveisComMudancaDaQuadra
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioRelacaoImoveisComMudancaDaQuadra(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA);
	}

	@Deprecated
	public RelatorioRelacaoImoveisComMudancaDaQuadra() {

		super(null, "");
	}

	/**
	 * <<Descrição do método>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */

	public Object executar() throws TarefaException{

		// [UC0074] Alterar Inscrição de Imóvel
		// [SB0002] - Emitir Relatório dos Imóveis da Inscrição Origem com Mudança da Quadra

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// Recupera os parâmetros utilizados na formação da consulta
		Imovel imovelParametros = (Imovel) getParametro("imovelParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Integer totalImovelMesmaRotaAntesAlteracao = 0;
		if(!Util.isVazioOuBranco(getParametro("totalImovelMesmaRotaAntesAlteracao"))){
			totalImovelMesmaRotaAntesAlteracao = Integer.valueOf((String) getParametro("totalImovelMesmaRotaAntesAlteracao"));
		}

		Integer totalImovelRotaDiferenteAntesAlteracao = 0;
		if(!Util.isVazioOuBranco(getParametro("totalImovelRotaDiferenteAntesAlteracao"))){
			totalImovelRotaDiferenteAntesAlteracao = Integer.valueOf((String) getParametro("totalImovelRotaDiferenteAntesAlteracao"));
		}

		String indicadorAlteracaoRota = (String) getParametro("indicadorAlteracaoRota");

		// informa os dados para consulta de imovel por inscrição
		Integer idLocalidade = imovelParametros.getLocalidade().getId();
		Integer codigoSetorComercial = imovelParametros.getSetorComercial().getCodigo();
		Integer nnQuadra = imovelParametros.getQuadra().getNumeroQuadra();
		Short nnLote = imovelParametros.getLote();

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioRelacaoImoveisComMudancaDaQuadraBean relatorioBean = null;

		// Instância da fachada
		Fachada fachada = Fachada.getInstancia();

		Collection<Imovel> imoveisRelatorios = new ArrayList<Imovel>();

		// consulta imóveis por inscrição
		imoveisRelatorios = fachada.pesquisarImovelPorInscricao(idLocalidade, codigoSetorComercial, nnQuadra, nnLote);

		Integer totalImovelMesmaRotaAposAlteracao = 0;
		Integer totalImovelRotaDiferenteAposAlteracao = 0;
		Integer totalImoveisComMudancaQuadra = 0;

		// Insere os imóveis no relatório
		if(!Util.isVazioOrNulo(imoveisRelatorios)){

			for(Imovel imovel : imoveisRelatorios){

				relatorioBean = new RelatorioRelacaoImoveisComMudancaDaQuadraBean();

				// 1.1.3.
				// Localidade;
				if(!Util.isVazioOuBranco(imovel.getLocalidade().getId())){
					relatorioBean.setIdLocalidade(imovel.getLocalidade().getId().toString());
				}

				// Nome Localidade
				if(!Util.isVazioOuBranco(imovel.getLocalidade().getDescricao())){
					relatorioBean.setNomeLocalidade(imovel.getLocalidade().getDescricao());
				}

				// Código do SetorComercial
				if(!Util.isVazioOuBranco(imovel.getSetorComercial().getCodigo())){
					relatorioBean.setCdSetorComercial(Integer.valueOf(imovel.getSetorComercial().getCodigo()).toString());
				}

				// Nome do SetorComercial
				if(!Util.isVazioOuBranco(imovel.getSetorComercial().getDescricao())){
					relatorioBean.setNomeSetorComercial(imovel.getSetorComercial().getDescricao());
				}

				// Número da Quadra
				if(!Util.isVazioOuBranco(imovel.getQuadra().getNumeroQuadra())){
					relatorioBean.setNumeroQuadra(Integer.valueOf(imovel.getQuadra().getNumeroQuadra()).toString());
				}

				// 1.2. Dados dos Imóveis:
				// 1.2.1. Matrícula
				// Id do Imovel
				if(!Util.isVazioOuBranco(imovel.getId())){
					relatorioBean.setMatricula(Integer.valueOf(imovel.getId()).toString());
				}

				// 1.2.2. Endereço do Imóvel
				if(!Util.isVazioOuBranco(imovel.getEnderecoFormatado())){
					relatorioBean.setEndereco(imovel.getEnderecoFormatado());
				}else{
					if(!Util.isVazioOuBranco(imovel.getId())){
						relatorioBean.setEndereco(fachada.pesquisarEndereco(imovel.getId()));
					}
				}

				// 1.2.3. Inscrição
				// 1.2.3.1. Localidade
				if(!Util.isVazioOuBranco(imovel.getLocalidade().getId())){
					relatorioBean.setIdLocalidadeInscricao(imovel.getLocalidade().getId().toString());
				}

				// 1.2.3.2. Setor Comercial
				if(!Util.isVazioOuBranco(imovel.getSetorComercial().getCodigo())){
					relatorioBean.setCdSetorComercialIncricao(Integer.valueOf(imovel.getSetorComercial().getCodigo()).toString());
				}

				// 1.2.3.3. Quadra
				if(!Util.isVazioOuBranco(imovel.getQuadra().getNumeroQuadra())){
					relatorioBean.setNumeroQuadraInscricao(Integer.valueOf(imovel.getQuadra().getNumeroQuadra()).toString());
				}

				// 1.2.3.4. Lote
				if(!Util.isVazioOuBranco(imovel.getLote())){
					relatorioBean.setNumeroLoteInscricao(Short.valueOf(imovel.getLote()).toString());
				}

				// 1.2.3.5. Sublote
				if(!Util.isVazioOuBranco(imovel.getSubLote())){
					relatorioBean.setNumeroSubLoteInscricao(Short.valueOf(imovel.getSubLote()).toString());
				}

				// 1.2.4. Rota
				if(!Util.isVazioOuBranco(imovel.getRota().getCodigo())){
					relatorioBean.setCodigoRotaInscricao(Short.valueOf(imovel.getRota().getCodigo()).toString());
				}

				// inclui o imóvel no relatório
				relatorioBeans.add(relatorioBean);

				// Inclui as informações referentes ao total de imóveis com mesma rota após
				// alteração
				// e os imóveis que possuem rotas diferentes após a alteração
				if(imovel.getRota() != null && imovel.getRota().getId() != null && imovel.getQuadra() != null && imovel.getRota() != null
								&& imovel.getQuadra().getRota() != null && imovel.getQuadra().getRota().getId() != null
								&& imovel.getRota().getId().equals(imovel.getQuadra().getRota().getId())){
					totalImovelMesmaRotaAposAlteracao = totalImovelMesmaRotaAposAlteracao + 1;
				}else{
					totalImovelRotaDiferenteAposAlteracao = totalImovelRotaDiferenteAposAlteracao + 1;
				}
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// Caso o indicador alteração de rota indique que todos os registros foram modificados
		// o total de imoveis com mudança de quadra será o total
		if(indicadorAlteracaoRota.equals("1")){
			totalImoveisComMudancaQuadra = totalImovelMesmaRotaAposAlteracao + totalImovelRotaDiferenteAposAlteracao;
		}

		// caso o indicador alteração de rota indique que somente os registros
		// com mesma rota sejam alterados, o total de imóveis com mudança de quadra será
		// os imóveis que possuem a mesma rota após alteração
		if(indicadorAlteracaoRota.equals("2")){
			totalImoveisComMudancaQuadra = totalImovelMesmaRotaAposAlteracao;
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("totalImoveisComMudancaQuadra", totalImoveisComMudancaQuadra);
		parametros.put("totalImovelMesmaRotaAntesAlteracao", totalImovelMesmaRotaAntesAlteracao);
		parametros.put("totalImovelRotaDiferenteAntesAlteracao", totalImovelRotaDiferenteAntesAlteracao);
		parametros.put("totalImovelMesmaRotaAposAlteracao", totalImovelMesmaRotaAposAlteracao);
		parametros.put("totalImovelRotaDiferenteAposAlteracao", totalImovelRotaDiferenteAposAlteracao);
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA, parametros, ds,
						tipoFormatoRelatorio);
		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// retorna o relatório gerado
		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioRelacaoImoveisComMudancaDaQuadra", this);
	}

}
