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

package gcom.relatorio.cadastro.localidade;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * [UC0025] Manter Quadra
 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
 * 
 * @author Anderson Italo
 * @date 24/01/2011
 */
public class RelatorioImoveisPorQuadra
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioImoveisPorQuadra(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEIS_POR_QUADRA);
	}

	@Deprecated
	public RelatorioImoveisPorQuadra() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		Integer totalImoveisDOIgualNovo = 0;
		Integer totalImoveisDODiferenteNovo = 0;
		Integer totalImoveisDOIgualAnterior = 0;
		Integer totalImoveisDODiferenteAnterior = 0;
		Fachada fachada = Fachada.getInstancia();

		// Obtêm os parâmetros passados.
		Quadra quadra = (Quadra) getParametro("quadraAlterada");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Integer totalImoveisRotaIgualAnterior = Integer.valueOf((String) getParametro("totalImoveisRotaIgualAnterior"));
		Integer totalImoveisRotaDiferenteAnterior = Integer.valueOf((String) getParametro("totalImoveisRotaDiferenteAnterior"));
		Integer totalImoveisRotaIgualNova = fachada.obterTotalImoveisRotaIgualAnteriorQuadra(quadra.getId(), quadra.getRota().getId());
		Integer totalImoveisRotaDiferenteNova = fachada.obterTotalImoveisRotaDiferenteAnteriorQuadra(quadra.getId(), quadra.getRota()
						.getId());

		if((String) getParametro("totalImoveisDistritoOperacionalIgualAnterior") != null){
			totalImoveisDOIgualAnterior = Integer.valueOf((String) getParametro("totalImoveisDistritoOperacionalIgualAnterior"));
		}
		if((String) getParametro("totalImoveisDistritoOperacionalDiferenteAnterior") != null){
			totalImoveisDODiferenteAnterior = Integer.valueOf((String) getParametro("totalImoveisDistritoOperacionalDiferenteAnterior"));
		}
		if(quadra.getDistritoOperacional() != null){
			totalImoveisDOIgualNovo = fachada.obterTotalImoveisDistritoOperacionalIgualAnteriorQuadra(quadra.getId(), quadra
							.getDistritoOperacional().getId());
			totalImoveisDODiferenteNovo = fachada.obterTotalImoveisDistritoOperacionalDiferenteAnteriorQuadra(quadra.getId(), quadra
							.getDistritoOperacional().getId());
		}
		// Monta a coleção de beans do relatório.
		List relatorioBeans = new ArrayList();

		// Obtém os imóveis da quadra informada.
		Collection colecaoImoveis = fachada.pesquisarRelatorioImoveisPorQuadra(quadra.getId());

		for(Iterator iterator = colecaoImoveis.iterator(); iterator.hasNext();){

			Object[] dados = (Object[]) iterator.next();

			RelatorioImoveisPorQuadraBean bean = new RelatorioImoveisPorQuadraBean();

			// Matrícula
			bean.setMatricula(dados[0].toString());

			// Inscrição
			Localidade localidade = new Localidade(Util.obterInteger(dados[1].toString()));
			SetorComercial setor = new SetorComercial();
			setor.setCodigo(Util.obterInteger(dados[2].toString()));
			Imovel imovel = new Imovel();
			imovel.setId(Util.obterInteger(bean.getMatricula()));
			imovel.setLocalidade(localidade);
			imovel.setSetorComercial(setor);
			imovel.setQuadra(quadra);

			if(dados[4] != null && !dados[4].toString().equals("")){

				imovel.setLote(Short.valueOf(dados[4].toString()));
			}

			if(dados[5] != null && !dados[5].toString().equals("")){

				imovel.setSubLote(Short.valueOf(dados[5].toString()));
			}

			bean.setInscricao(imovel.getInscricaoFormatada());

			// Endereço do Imóvel (<<Inclui>> [UC0085 - Obter Endereço])
			bean.setEndereco(fachada.pesquisarEndereco(imovel.getId()));

			// Rota Anterior
			bean.setRotaQuadra(quadra.getRota().getCodigo().toString());

			// Rota Atual
			if(dados[6] != null && !dados[6].toString().equals("")){

				bean.setRotaAtual(dados[6].toString());
			}

			if(StringUtils.isNotBlank((String) dados[7])){
				bean.setDistritoOperacionalImovel(dados[7].toString());
			}

			if(StringUtils.isNotBlank((String) dados[8])){
				bean.setDistritoOperacionalQuadra(dados[8].toString());
			}

			relatorioBeans.add(bean);

		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("nomeLocalidade", quadra.getSetorComercial().getLocalidade().getDescricao());
		parametros.put("codigoLocalidade", quadra.getSetorComercial().getLocalidade().getId().toString());
		parametros.put("codigoSetorComercial", String.valueOf(quadra.getSetorComercial().getCodigo()));
		parametros.put("descricaoSetorComercial", quadra.getSetorComercial().getDescricao());
		parametros.put("numeroQuadra", String.valueOf(quadra.getNumeroQuadra()));
		parametros.put("totalIgualRotaAnterior", totalImoveisRotaIgualAnterior.toString());
		parametros.put("totalDiferenteRotaAnterior", totalImoveisRotaDiferenteAnterior.toString());
		parametros.put("totalIgualNovaRota", totalImoveisRotaIgualNova.toString());
		parametros.put("totalDiferenteNovaRota", totalImoveisRotaDiferenteNova.toString());
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// NOVOS
		parametros.put("totalIgualDOAnterior", totalImoveisDOIgualAnterior.toString());
		parametros.put("totalDiferenteDOAnterior", totalImoveisDODiferenteAnterior.toString());
		parametros.put("totalIgualNovoDO", totalImoveisDOIgualNovo.toString());
		parametros.put("totalDiferenteNovoDO", totalImoveisDODiferenteNovo.toString());

		// ADD
		parametros.put("totalIgualNovaRota", totalImoveisRotaIgualNova.toString());

		if(tipoFormatoRelatorio == TIPO_PDF){

			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_IMOVEIS_POR_QUADRA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_IMOVEIS_POR_QUADRA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = 0;

		Quadra quadra = (Quadra) getParametro("quadraAlterada");
		Integer totalRegitros = Fachada.getInstancia().pesquisarTotalRegistrosRelatorioImoveisPorQuadra(quadra.getId());

		if(totalRegitros != null){

			retorno = totalRegitros;
		}

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioLogradouroGeral", this);
	}

}
