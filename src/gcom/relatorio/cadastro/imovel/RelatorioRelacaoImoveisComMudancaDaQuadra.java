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
 * classe respons�vel por criar o relat�rio de im�veis por endere�o
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
	 * <<Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException{

		// [UC0074] Alterar Inscri��o de Im�vel
		// [SB0002] - Emitir Relat�rio dos Im�veis da Inscri��o Origem com Mudan�a da Quadra

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// Recupera os par�metros utilizados na forma��o da consulta
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

		// informa os dados para consulta de imovel por inscri��o
		Integer idLocalidade = imovelParametros.getLocalidade().getId();
		Integer codigoSetorComercial = imovelParametros.getSetorComercial().getCodigo();
		Integer nnQuadra = imovelParametros.getQuadra().getNumeroQuadra();
		Short nnLote = imovelParametros.getLote();

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioRelacaoImoveisComMudancaDaQuadraBean relatorioBean = null;

		// Inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		Collection<Imovel> imoveisRelatorios = new ArrayList<Imovel>();

		// consulta im�veis por inscri��o
		imoveisRelatorios = fachada.pesquisarImovelPorInscricao(idLocalidade, codigoSetorComercial, nnQuadra, nnLote);

		Integer totalImovelMesmaRotaAposAlteracao = 0;
		Integer totalImovelRotaDiferenteAposAlteracao = 0;
		Integer totalImoveisComMudancaQuadra = 0;

		// Insere os im�veis no relat�rio
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

				// C�digo do SetorComercial
				if(!Util.isVazioOuBranco(imovel.getSetorComercial().getCodigo())){
					relatorioBean.setCdSetorComercial(Integer.valueOf(imovel.getSetorComercial().getCodigo()).toString());
				}

				// Nome do SetorComercial
				if(!Util.isVazioOuBranco(imovel.getSetorComercial().getDescricao())){
					relatorioBean.setNomeSetorComercial(imovel.getSetorComercial().getDescricao());
				}

				// N�mero da Quadra
				if(!Util.isVazioOuBranco(imovel.getQuadra().getNumeroQuadra())){
					relatorioBean.setNumeroQuadra(Integer.valueOf(imovel.getQuadra().getNumeroQuadra()).toString());
				}

				// 1.2. Dados dos Im�veis:
				// 1.2.1. Matr�cula
				// Id do Imovel
				if(!Util.isVazioOuBranco(imovel.getId())){
					relatorioBean.setMatricula(Integer.valueOf(imovel.getId()).toString());
				}

				// 1.2.2. Endere�o do Im�vel
				if(!Util.isVazioOuBranco(imovel.getEnderecoFormatado())){
					relatorioBean.setEndereco(imovel.getEnderecoFormatado());
				}else{
					if(!Util.isVazioOuBranco(imovel.getId())){
						relatorioBean.setEndereco(fachada.pesquisarEndereco(imovel.getId()));
					}
				}

				// 1.2.3. Inscri��o
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

				// inclui o im�vel no relat�rio
				relatorioBeans.add(relatorioBean);

				// Inclui as informa��es referentes ao total de im�veis com mesma rota ap�s
				// altera��o
				// e os im�veis que possuem rotas diferentes ap�s a altera��o
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

		// Caso o indicador altera��o de rota indique que todos os registros foram modificados
		// o total de imoveis com mudan�a de quadra ser� o total
		if(indicadorAlteracaoRota.equals("1")){
			totalImoveisComMudancaQuadra = totalImovelMesmaRotaAposAlteracao + totalImovelRotaDiferenteAposAlteracao;
		}

		// caso o indicador altera��o de rota indique que somente os registros
		// com mesma rota sejam alterados, o total de im�veis com mudan�a de quadra ser�
		// os im�veis que possuem a mesma rota ap�s altera��o
		if(indicadorAlteracaoRota.equals("2")){
			totalImoveisComMudancaQuadra = totalImovelMesmaRotaAposAlteracao;
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
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

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA, parametros, ds,
						tipoFormatoRelatorio);
		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// retorna o relat�rio gerado
		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioRelacaoImoveisComMudancaDaQuadra", this);
	}

}
