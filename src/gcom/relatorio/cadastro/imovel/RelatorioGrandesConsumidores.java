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
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.Rota;
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

/**
 * [UC0XXX] Gerar Relat�rio Grandes Consumidores
 * 
 * @author Anderson Italo
 * @date 15/02/2011
 */
public class RelatorioGrandesConsumidores
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioGrandesConsumidores(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GRANDES_CONSUMIDORES);
	}

	@Deprecated
	public RelatorioGrandesConsumidores() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obt�m os par�metros passados
		Integer idLocalidadeInicial = (Integer) getParametro("idLocalidadeInicial");
		Integer idLocalidadeFinal = (Integer) getParametro("idLocalidadeFinal");
		String nomeLocalidadeInicial = (String) getParametro("nomeLocalidadeInicial");
		String nomeLocalidadeFinal = (String) getParametro("nomeLocalidadeFinal");
		Integer idGerenciaRegional = (Integer) getParametro("idGerenciaRegional");
		String nomeGerenciaRegional = (String) getParametro("nomeGerencia");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// obt�m os dados dos Grandes Consumidores
		Collection colecaoGrandesConsumidores = fachada.pesquisaRelatorioGrandesConsumidores(idGerenciaRegional, idLocalidadeInicial,
						idLocalidadeFinal);

		// vari�veis auxiliares de quebra de p�gina
		int totalRegistros = 0;

		for(Iterator iterator = colecaoGrandesConsumidores.iterator(); iterator.hasNext();){

			Object[] dados = (Object[]) iterator.next();

			RelatorioGrandesConsumidoresBean bean = new RelatorioGrandesConsumidoresBean();

			// Cabe�alho

			// 5.1.1. C�digo da Regional
			bean.setCodigoGerencia(dados[1].toString());

			// 5.1.2. Nome da Regional
			bean.setNomeGerencia(dados[2].toString());

			// 5.1.3. C�digo da Localidade
			bean.setCodigoLocalidade(dados[3].toString());
			Localidade localidade = new Localidade();
			localidade.setId(new Integer(dados[3].toString()));

			// 5.1.4. Nome da Localidade
			bean.setNomeLocalidade(dados[4].toString());

			// Linha Detalhe

			// 5.2.7. Matr�cula do Im�vel
			bean.setMatriculaImovel(dados[0].toString());

			// Inscri��o formatada do im�vel
			Imovel imovel = new Imovel();
			imovel.setId(new Integer(dados[0].toString()));
			imovel.setLocalidade(localidade);
			imovel.setQuadra(new Quadra(0));
			imovel.getQuadra().setNumeroQuadra(0);

			// ?.?.?. Setor Comercial
			if(!dados[14].toString().equals("0")){
				SetorComercial setor = new SetorComercial();
				setor.setId(new Integer(dados[14].toString()));
				setor.setCodigo(new Integer(dados[6].toString()));
				imovel.setSetorComercial(setor);
			}else{
				SetorComercial setor = new SetorComercial();
				setor.setId(0);
				setor.setCodigo(0);
				imovel.setSetorComercial(setor);
			}

			// 5.2.3. Rota
			if(dados[7] != null && !dados[7].toString().equals("")){
				Integer idRota = Integer.parseInt(dados[7].toString());

				Rota rota = new Rota();
				rota.setId(idRota);

				imovel.setRota(rota);
			}

			// 5.2.4. N�mero do Segmento
			if(dados[8] != null && !dados[8].toString().equals("")){
				imovel.setNumeroSegmento(new Short(dados[8].toString()));
			}else{
				imovel.setNumeroSegmento(new Short("0"));
			}

			// 5.2.5. N�mero do Lote
			if(!dados[9].toString().equals("0")){
				imovel.setLote(new Short(dados[9].toString()));
			}else{
				imovel.setLote(new Short("0"));
			}

			// 5.2.6. N�mero do Sublote
			if(!dados[10].toString().equals("0")){
				imovel.setSubLote(new Short(dados[10].toString()));
			}else{
				imovel.setSubLote(new Short("0"));
			}

			bean.setInscricao(imovel.getInscricaoFormatada());

			// Retira localidade da inscri��o, pois a localidade j� � exibida no cabe�alho
			if(bean.getInscricao() != null && !bean.getInscricao().equals("")){
				bean.setInscricao(bean.getInscricao().substring(4));
			}

			// 5.2.8. Nome do Cliente Usu�rio
			bean.setCliente(dados[13].toString());

			// 5.2.1. Situa��o da Liga��o de �gua
			if(!dados[11].toString().equals("")){
				bean.setSituacaoLigacaoAgua(dados[11].toString());
			}

			// 5.2.2. Situa��o da Liga��o de Esgoto
			if(!dados[12].toString().equals("")){
				bean.setSituacaoLigacaoEsgoto(dados[12].toString());
			}

			// 5.2.9. M�dia
			Integer media = fachada.pesquisarMediaConsumoHidrometro(new Integer(bean.getMatriculaImovel()));

			if(media != null){
				bean.setMedia(media.toString());
			}

			// 5.2.10. Economias:
			// R-000 C-000 I-000 P-000, onde o R � Categoria Residencial, C � Categoria Comercial,
			// I � Categoria Industrial e P � Categoria P�blica e 000 � N�mero de economias da
			// Categoria
			Collection colecaoCategoria = fachada.obterQuantidadeEconomiasCategoria(imovel);
			Iterator colecaoCategoriaIterator = colecaoCategoria.iterator();
			String economias = "";

			while(colecaoCategoriaIterator.hasNext()){

				Categoria categoria = (Categoria) colecaoCategoriaIterator.next();
				String quantidadeEconomias = categoria.getQuantidadeEconomiasCategoria().toString();

				if(categoria.getId().intValue() == Categoria.COMERCIAL_INT){
					economias += " C-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
				}else if(categoria.getId().intValue() == Categoria.PUBLICO_INT){
					economias += " P-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
				}else if(categoria.getId().intValue() == Categoria.RESIDENCIAL_INT){
					economias += " R-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
				}else if(categoria.getId().intValue() == Categoria.INDUSTRIAL_INT){
					economias += " I-" + Util.completarStringZeroEsquerda(quantidadeEconomias, 3);
				}
			}

			bean.setEconomias(economias);

			// 5.2.11. Grande Consumidor no M�s (Se IPER_ID da tabela
			// im�vel = 2 � Grande no m�s � formatar campo com SIM)
			if(!dados[5].toString().equals(ImovelPerfil.GRANDE_NO_MES.toString())){
				bean.setGrandeConsumidorMes("SIM");
			}

			totalRegistros++;

			if(!iterator.hasNext()){

				// �ltima p�gina finaliza totalizando tudo
				bean.setTotalRegistros(totalRegistros);
			}

			relatorioBeans.add(bean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("nomeLocalidadeInicial", nomeLocalidadeInicial);
		parametros.put("codigoLocalidadeInicial", idLocalidadeInicial.toString());
		parametros.put("nomeLocalidadeFinal", nomeLocalidadeFinal);
		parametros.put("codigoLocalidadeFinal", idLocalidadeFinal.toString());
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("codigoGerencia", idGerenciaRegional.toString());
		parametros.put("nomeGerencia", nomeGerenciaRegional.toString());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GRANDES_CONSUMIDORES, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_GRANDES_CONSUMIDORES, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		// obt�m os par�metros passados
		Integer idLocalidadeInicial = (Integer) getParametro("idLocalidadeInicial");
		Integer idLocalidadeFinal = (Integer) getParametro("idLocalidadeFinal");
		Integer idGerenciaRegional = (Integer) getParametro("idGerenciaRegional");

		retorno = Fachada.getInstancia().pesquisaTotalRegistrosRelatorioGrandesConsumidores(idGerenciaRegional, idLocalidadeInicial,
						idLocalidadeFinal).intValue();

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioGrandesConsumidores", this);
	}

}
