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

package gcom.relatorio.micromedicao;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gcom.micromedicao.bean.RelatorioAnaliseConsumoHelper;
import gcom.relatorio.RelatorioBean;

/**
 * @author Vivianne Sousa
 * @date 06/11/2007
 * @author Saulo Lima
 * @date 11/03/2010 Criação do atributo codigoSetorComercial para quebra de páginas no PDF
 */
public class RelatorioAnaliseConsumoBean
				implements RelatorioBean {

	private String inscricaoImovel;

	private String matriculaImovel;

	private String rota;

	private String seqRota;

	private String endereco;

	private String qtdEconomias;

	private String categoria;

	private String usuario;

	private String hidrometro;

	private String leituraAnterior;

	private String leituraAtual;

	private String codigoSetorComercial;

	private String ligacaoAgua;

	private String situacaoLigacaoAgua;

	private String leiturista;

	private String numeroQuadra;

	private String anormalidadeLeitura;

	private String anormalidadeConsumo;

	private String situacaoLigacaoEsgoto;

	private String anormalidade;

	private JRBeanCollectionDataSource arrayJRDetail;

	private ArrayList arrayRelatorioAnaliseConsumoDetailBean;

	public RelatorioAnaliseConsumoBean(RelatorioAnaliseConsumoHelper relatorioAnaliseConsumoHelper) {

		this.inscricaoImovel = relatorioAnaliseConsumoHelper.getInscricaoImovelFormatada();
		this.matriculaImovel = relatorioAnaliseConsumoHelper.getMatriculaImovel();
		this.rota = relatorioAnaliseConsumoHelper.getRota();
		this.seqRota = relatorioAnaliseConsumoHelper.getSeqRota();
		this.endereco = relatorioAnaliseConsumoHelper.getEndereco();
		this.qtdEconomias = relatorioAnaliseConsumoHelper.getQtdEconomias();
		this.categoria = relatorioAnaliseConsumoHelper.getCategoria();
		this.usuario = relatorioAnaliseConsumoHelper.getUsuario();
		this.hidrometro = relatorioAnaliseConsumoHelper.getHidrometro();
		this.leituraAnterior = relatorioAnaliseConsumoHelper.getLeituraAnterior();
		this.leituraAtual = relatorioAnaliseConsumoHelper.getLeituraAtual();
		this.codigoSetorComercial = relatorioAnaliseConsumoHelper.getCodigoSetorComercial();
	}

	public RelatorioAnaliseConsumoBean(RelatorioAnaliseConsumoHelper relatorioAnaliseConsumoHelper, Collection colecaoDetail) {

		this.inscricaoImovel = relatorioAnaliseConsumoHelper.getInscricaoImovelFormatada();
		this.matriculaImovel = relatorioAnaliseConsumoHelper.getMatriculaImovel();
		this.rota = relatorioAnaliseConsumoHelper.getRota();
		this.seqRota = relatorioAnaliseConsumoHelper.getSeqRota();
		this.endereco = relatorioAnaliseConsumoHelper.getEndereco();
		this.qtdEconomias = relatorioAnaliseConsumoHelper.getQtdEconomias();
		this.categoria = relatorioAnaliseConsumoHelper.getCategoria();
		this.usuario = relatorioAnaliseConsumoHelper.getUsuario();
		this.hidrometro = relatorioAnaliseConsumoHelper.getHidrometro();
		this.leituraAnterior = relatorioAnaliseConsumoHelper.getLeituraAnterior();
		this.leituraAtual = relatorioAnaliseConsumoHelper.getLeituraAtual();
		this.codigoSetorComercial = relatorioAnaliseConsumoHelper.getCodigoSetorComercial();
		this.ligacaoAgua = relatorioAnaliseConsumoHelper.getLigacaoAgua();
		this.situacaoLigacaoAgua = relatorioAnaliseConsumoHelper.getSituacaoLigacaoAgua();
		this.leiturista = relatorioAnaliseConsumoHelper.getLeiturista();

		this.numeroQuadra = relatorioAnaliseConsumoHelper.getNumeroQuadra();
		this.anormalidadeLeitura = relatorioAnaliseConsumoHelper.getAnormalidadeLeitura();
		this.anormalidadeConsumo = relatorioAnaliseConsumoHelper.getAnormalidadeConsumo();
		this.situacaoLigacaoEsgoto = relatorioAnaliseConsumoHelper.getSituacaoLigacaoEsgoto();
		this.anormalidade = relatorioAnaliseConsumoHelper.getAnormalidade();

		this.arrayRelatorioAnaliseConsumoDetailBean = new ArrayList();
		this.arrayRelatorioAnaliseConsumoDetailBean.addAll(colecaoDetail);
		this.arrayJRDetail = new JRBeanCollectionDataSource(this.arrayRelatorioAnaliseConsumoDetailBean);

	}

	public String getUsuario(){

		return usuario;
	}

	public void setUsuario(String usuario){

		this.usuario = usuario;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getRota(){

		return rota;
	}

	public void setRota(String rota){

		this.rota = rota;
	}

	public String getSeqRota(){

		return seqRota;
	}

	public void setSeqRota(String seqRota){

		this.seqRota = seqRota;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getQtdEconomias(){

		return qtdEconomias;
	}

	public void setQtdEconomias(String qtdEconomias){

		this.qtdEconomias = qtdEconomias;
	}

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public String getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public String getLeituraAtual(){

		return leituraAtual;
	}

	public void setLeituraAtual(String leituraAtual){

		this.leituraAtual = leituraAtual;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public JRBeanCollectionDataSource getArrayJRDetail(){

		return arrayJRDetail;
	}

	public void setArrayJRDetail(JRBeanCollectionDataSource arrayJRDetail){

		this.arrayJRDetail = arrayJRDetail;
	}

	public ArrayList getArrayRelatorioAnaliseConsumoDetailBean(){

		return arrayRelatorioAnaliseConsumoDetailBean;
	}

	public void setArrayRelatorioAnaliseConsumoDetailBean(ArrayList arrayRelatorioAnaliseConsumoDetailBean){

		this.arrayRelatorioAnaliseConsumoDetailBean = arrayRelatorioAnaliseConsumoDetailBean;
	}

	public String getLigacaoAgua(){

		return ligacaoAgua;
	}

	public void setLigacaoAgua(String ligacaoAgua){

		this.ligacaoAgua = ligacaoAgua;
	}

	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	public String getLeiturista(){

		return leiturista;
	}

	public void setLeiturista(String leiturista){

		this.leiturista = leiturista;
	}

	public String getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(String numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public String getAnormalidadeLeitura(){

		return anormalidadeLeitura;
	}

	public void setAnormalidadeLeitura(String anormalidadeLeitura){

		this.anormalidadeLeitura = anormalidadeLeitura;
	}

	public String getAnormalidadeConsumo(){

		return anormalidadeConsumo;
	}

	public void setAnormalidadeConsumo(String anormalidadeConsumo){

		this.anormalidadeConsumo = anormalidadeConsumo;
	}

	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	public String getAnormalidade(){

		return anormalidade;
	}

	public void setAnormalidade(String anormalidade){

		this.anormalidade = anormalidade;
	}

}
