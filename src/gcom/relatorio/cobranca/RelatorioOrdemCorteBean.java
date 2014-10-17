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

package gcom.relatorio.cobranca;

import gcom.cobranca.bean.EmitirOrdemCorteCobrancaHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author isilva
 * @date 14/09/2010
 */
public class RelatorioOrdemCorteBean
				implements RelatorioBean {

	private static final int RELATORIO_DETAIL_3_COLUNAS = 3;

	// Primeira Aba

	private JRBeanCollectionDataSource arrayJRDetail1;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail1Bean;

	private String inscricao1;

	private String matricula1;

	private String roteiro1;

	private String hm1;

	private String programa1;

	private String cliente1;

	private String endereco1;

	private String bairro1;

	private String numero1;

	private String nomeCliente1;

	private String endereco1Linha2;

	private String endereco1Linha3;

	private String rotaGrupo1;

	private String dataEmissao1;

	private String mesAno1;

	private String situacao1;

	private String executor1;

	private String dataCorte1;

	private String horaCorte1;

	private String numeroHidrometro1;

	private String leituraHidrometro1;

	private String localizacaoHidrometro1;

	private String dataVencimento1;

	private String valor1;

	private String dataComparecimento1;

	private String dataDebitoAnterior1;

	private String debitosAnteriores1;

	private String valorTotalDebito1;

	private String representacaoNumericaCodBarraFormatada1;

	private String representacaoNumericaCodBarraSemDigito1;

	private String categoriaPrincipal1;

	private String situacaoLigacaoAgua1;

	private String situacaoLigacaoEsgoto1;

	private String dataPermanenciaRegistro1;

	// Segunda Aba

	private JRBeanCollectionDataSource arrayJRDetail2;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail2Bean;

	private String inscricao2;

	private String matricula2;

	private String roteiro2;

	private String hm2;

	private String programa2;

	private String cliente2;

	private String endereco2;

	private String bairro2;

	private String numero2;

	private String nomeCliente2;

	private String endereco2Linha2;

	private String endereco2Linha3;

	private String rotaGrupo2;

	private String dataEmissao2;

	private String mesAno2;

	private String situacao2;

	private String executor2;

	private String dataCorte2;

	private String horaCorte2;

	private String numeroHidrometro2;

	private String leituraHidrometro2;

	private String localizacaoHidrometro2;

	private String dataVencimento2;

	private String valor2;

	private String dataComparecimento2;

	private String dataDebitoAnterior2;

	private String debitosAnteriores2;

	private String valorTotalDebito2;

	private String representacaoNumericaCodBarraFormatada2;

	private String representacaoNumericaCodBarraSemDigito2;

	private String categoriaPrincipal2;

	private String situacaoLigacaoAgua2;

	private String situacaoLigacaoEsgoto2;

	private String dataPermanenciaRegistro2;

	public RelatorioOrdemCorteBean(EmitirOrdemCorteCobrancaHelper emitirAvisoCobrancaHelper1,
									EmitirOrdemCorteCobrancaHelper emitirAvisoCobrancaHelper2) {

		if(emitirAvisoCobrancaHelper1 != null){
			this.inscricao1 = emitirAvisoCobrancaHelper1.getInscricao() != null ? emitirAvisoCobrancaHelper1.getInscricao() : "";
			this.matricula1 = emitirAvisoCobrancaHelper1.getMatricula() != null ? emitirAvisoCobrancaHelper1.getMatricula() : "";
			this.roteiro1 = emitirAvisoCobrancaHelper1.getRoteiro() != null ? emitirAvisoCobrancaHelper1.getRoteiro() : "";
			this.hm1 = emitirAvisoCobrancaHelper1.getHidrometro() != null ? emitirAvisoCobrancaHelper1.getHidrometro() : "";
			this.programa1 = emitirAvisoCobrancaHelper1.getAcaoCobranca() != null ? emitirAvisoCobrancaHelper1.getAcaoCobranca() : "";
			this.cliente1 = emitirAvisoCobrancaHelper1.getNomeCliente() != null ? emitirAvisoCobrancaHelper1.getNomeCliente() : "";
			this.endereco1 = emitirAvisoCobrancaHelper1.getEndereco() != null ? emitirAvisoCobrancaHelper1.getEndereco() : "";
			this.bairro1 = emitirAvisoCobrancaHelper1.getBairro() != null ? emitirAvisoCobrancaHelper1.getBairro() : "";
			this.numero1 = "1"; // Retirar isso
			this.dataDebitoAnterior1 = emitirAvisoCobrancaHelper1.getDataDebitoAnterior() != null ? emitirAvisoCobrancaHelper1
							.getDataDebitoAnterior() : "";
			this.debitosAnteriores1 = emitirAvisoCobrancaHelper1.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirAvisoCobrancaHelper1.getValorDebitosAnteriores()) : "";
			this.valorTotalDebito1 = emitirAvisoCobrancaHelper1.getValorTotal() != null ? Util.formatarMoedaReal(emitirAvisoCobrancaHelper1
							.getValorTotal()) : "";
			this.dataComparecimento1 = emitirAvisoCobrancaHelper1.getDataComparecimento().toString();
			this.representacaoNumericaCodBarraFormatada1 = emitirAvisoCobrancaHelper1.getRepresentacaoNumericaCodBarraFormatada();
			this.representacaoNumericaCodBarraSemDigito1 = emitirAvisoCobrancaHelper1.getRepresentacaoNumericaCodBarraSemDigito();
			this.arrayRelatorioOrdemCorteDetail1Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail1Bean.addAll(this.gerarDetail(emitirAvisoCobrancaHelper1.getMesAno(),
							emitirAvisoCobrancaHelper1.getVencimento(), emitirAvisoCobrancaHelper1.getValor()));
			this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail1Bean);
			this.dataPermanenciaRegistro1 = emitirAvisoCobrancaHelper1.getDataImpressao() != null ? emitirAvisoCobrancaHelper1
							.getDataImpressao() : "";
		}

		if(emitirAvisoCobrancaHelper2 != null){
			this.inscricao2 = emitirAvisoCobrancaHelper2.getInscricao() != null ? emitirAvisoCobrancaHelper2.getInscricao() : "";
			this.matricula2 = emitirAvisoCobrancaHelper2.getMatricula() != null ? emitirAvisoCobrancaHelper2.getMatricula() : "";
			this.roteiro2 = emitirAvisoCobrancaHelper2.getRoteiro() != null ? emitirAvisoCobrancaHelper2.getRoteiro() : "";
			this.hm2 = emitirAvisoCobrancaHelper2.getHidrometro() != null ? emitirAvisoCobrancaHelper2.getHidrometro() : "";
			this.programa2 = emitirAvisoCobrancaHelper2.getAcaoCobranca() != null ? emitirAvisoCobrancaHelper2.getAcaoCobranca() : "";
			this.cliente2 = emitirAvisoCobrancaHelper2.getNomeCliente() != null ? emitirAvisoCobrancaHelper2.getNomeCliente() : "";
			this.endereco2 = emitirAvisoCobrancaHelper2.getEndereco() != null ? emitirAvisoCobrancaHelper2.getEndereco() : "";
			this.bairro2 = emitirAvisoCobrancaHelper2.getBairro() != null ? emitirAvisoCobrancaHelper2.getBairro() : "";
			this.numero2 = "2";
			this.dataDebitoAnterior2 = emitirAvisoCobrancaHelper2.getDataDebitoAnterior() != null ? emitirAvisoCobrancaHelper2
							.getDataDebitoAnterior() : "";
			this.debitosAnteriores2 = emitirAvisoCobrancaHelper2.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirAvisoCobrancaHelper2.getValorDebitosAnteriores()) : "";
			this.valorTotalDebito2 = emitirAvisoCobrancaHelper2.getValorTotal() != null ? Util.formatarMoedaReal(emitirAvisoCobrancaHelper2
							.getValorTotal()) : "";
			this.dataComparecimento2 = emitirAvisoCobrancaHelper2.getDataComparecimento().toString();
			this.representacaoNumericaCodBarraFormatada2 = emitirAvisoCobrancaHelper2.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirAvisoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraFormatada()
							: "";
			this.representacaoNumericaCodBarraSemDigito2 = emitirAvisoCobrancaHelper2.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirAvisoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraSemDigito()
							: "000000";
			this.arrayRelatorioOrdemCorteDetail2Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail2Bean.addAll(this.gerarDetail(emitirAvisoCobrancaHelper2.getMesAno(),
							emitirAvisoCobrancaHelper2.getVencimento(), emitirAvisoCobrancaHelper2.getValor()));
			this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail2Bean);
			this.dataPermanenciaRegistro2 = emitirAvisoCobrancaHelper2.getDataImpressao() != null ? emitirAvisoCobrancaHelper2
							.getDataImpressao() : "";
		}
	}

	public String getDataComparecimento1(){

		return dataComparecimento1;
	}

	public void setDataComparecimento1(String dataComparecimento1){

		this.dataComparecimento1 = dataComparecimento1;
	}

	public String getDataComparecimento2(){

		return dataComparecimento2;
	}

	public void setDataComparecimento2(String dataComparecimento2){

		this.dataComparecimento2 = dataComparecimento2;
	}

	public String getInscricao1(){

		return inscricao1;
	}

	public void setInscricao1(String inscricao1){

		this.inscricao1 = inscricao1;
	}

	public String getMatricula1(){

		return matricula1;
	}

	public void setMatricula1(String matricula1){

		this.matricula1 = matricula1;
	}

	public String getRoteiro1(){

		return roteiro1;
	}

	public void setRoteiro1(String roteiro1){

		this.roteiro1 = roteiro1;
	}

	public String getHm1(){

		return hm1;
	}

	public void setHm1(String hm1){

		this.hm1 = hm1;
	}

	public String getPrograma1(){

		return programa1;
	}

	public void setPrograma1(String programa1){

		this.programa1 = programa1;
	}

	public String getCliente1(){

		return cliente1;
	}

	public void setCliente1(String cliente1){

		this.cliente1 = cliente1;
	}

	public String getEndereco1(){

		return endereco1;
	}

	public void setEndereco1(String endereco1){

		this.endereco1 = endereco1;
	}

	public String getBairro1(){

		return bairro1;
	}

	public void setBairro1(String bairro1){

		this.bairro1 = bairro1;
	}

	public String getNumero1(){

		return numero1;
	}

	public void setNumero1(String numero1){

		this.numero1 = numero1;
	}

	public String getInscricao2(){

		return inscricao2;
	}

	public void setInscricao2(String inscricao2){

		this.inscricao2 = inscricao2;
	}

	public String getMatricula2(){

		return matricula2;
	}

	public void setMatricula2(String matricula2){

		this.matricula2 = matricula2;
	}

	public String getRoteiro2(){

		return roteiro2;
	}

	public void setRoteiro2(String roteiro2){

		this.roteiro2 = roteiro2;
	}

	public String getHm2(){

		return hm2;
	}

	public void setHm2(String hm2){

		this.hm2 = hm2;
	}

	public String getPrograma2(){

		return programa2;
	}

	public void setPrograma2(String programa2){

		this.programa2 = programa2;
	}

	public String getCliente2(){

		return cliente2;
	}

	public void setCliente2(String cliente2){

		this.cliente2 = cliente2;
	}

	public String getEndereco2(){

		return endereco2;
	}

	public void setEndereco2(String endereco2){

		this.endereco2 = endereco2;
	}

	public String getBairro2(){

		return bairro2;
	}

	public void setBairro2(String bairro2){

		this.bairro2 = bairro2;
	}

	public String getNumero2(){

		return numero2;
	}

	public void setNumero2(String numero2){

		this.numero2 = numero2;
	}

	public JRBeanCollectionDataSource getArrayJRDetail1(){

		return arrayJRDetail1;
	}

	public void setArrayJRDetail1(JRBeanCollectionDataSource arrayJRDetail1){

		this.arrayJRDetail1 = arrayJRDetail1;
	}

	public JRBeanCollectionDataSource getArrayJRDetail2(){

		return arrayJRDetail2;
	}

	public void setArrayJRDetail2(JRBeanCollectionDataSource arrayJRDetail2){

		this.arrayJRDetail2 = arrayJRDetail2;
	}

	public String getDebitosAnteriores1(){

		return debitosAnteriores1;
	}

	public void setDebitosAnteriores1(String debitosAnteriores1){

		this.debitosAnteriores1 = debitosAnteriores1;
	}

	public String getValorTotalDebito1(){

		return valorTotalDebito1;
	}

	public void setValorTotalDebito1(String valorTotalDebito1){

		this.valorTotalDebito1 = valorTotalDebito1;
	}

	public String getDebitosAnteriores2(){

		return debitosAnteriores2;
	}

	public void setDebitosAnteriores2(String debitosAnteriores2){

		this.debitosAnteriores2 = debitosAnteriores2;
	}

	public String getValorTotalDebito2(){

		return valorTotalDebito2;
	}

	public void setValorTotalDebito2(String valorTotalDebito2){

		this.valorTotalDebito2 = valorTotalDebito2;
	}

	public String getRepresentacaoNumericaCodBarraFormatada1(){

		return representacaoNumericaCodBarraFormatada1;
	}

	public ArrayList<Object> getArrayRelatorioOrdemCorteDetail1Bean(){

		return arrayRelatorioOrdemCorteDetail1Bean;
	}

	public void setArrayRelatorioOrdemCorteDetail1Bean(ArrayList<Object> arrayRelatorioOrdemCorteDetail1Bean){

		this.arrayRelatorioOrdemCorteDetail1Bean = arrayRelatorioOrdemCorteDetail1Bean;
	}

	public ArrayList<Object> getArrayRelatorioOrdemCorteDetail2Bean(){

		return arrayRelatorioOrdemCorteDetail2Bean;
	}

	public void setArrayRelatorioOrdemCorteDetail2Bean(ArrayList<Object> arrayRelatorioOrdemCorteDetail2Bean){

		this.arrayRelatorioOrdemCorteDetail2Bean = arrayRelatorioOrdemCorteDetail2Bean;
	}

	public void setRepresentacaoNumericaCodBarraFormatada1(String representacaoNumericaCodBarraFormatada1){

		this.representacaoNumericaCodBarraFormatada1 = representacaoNumericaCodBarraFormatada1;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito1(){

		return representacaoNumericaCodBarraSemDigito1;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito1(String representacaoNumericaCodBarraSemDigito1){

		this.representacaoNumericaCodBarraSemDigito1 = representacaoNumericaCodBarraSemDigito1;
	}

	public String getRepresentacaoNumericaCodBarraFormatada2(){

		return representacaoNumericaCodBarraFormatada2;
	}

	public void setRepresentacaoNumericaCodBarraFormatada2(String representacaoNumericaCodBarraFormatada2){

		this.representacaoNumericaCodBarraFormatada2 = representacaoNumericaCodBarraFormatada2;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito2(){

		return representacaoNumericaCodBarraSemDigito2;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito2(String representacaoNumericaCodBarraSemDigito2){

		this.representacaoNumericaCodBarraSemDigito2 = representacaoNumericaCodBarraSemDigito2;
	}

	public String getDataDebitoAnterior1(){

		return dataDebitoAnterior1;
	}

	public void setDataDebitoAnterior1(String dataDebitoAnterior1){

		this.dataDebitoAnterior1 = dataDebitoAnterior1;
	}

	public String getDataDebitoAnterior2(){

		return dataDebitoAnterior2;
	}

	public void setDataDebitoAnterior2(String dataDebitoAnterior2){

		this.dataDebitoAnterior2 = dataDebitoAnterior2;
	}

	public String getDataPermanenciaRegistro1(){

		return dataPermanenciaRegistro1;
	}

	public void setDataPermanenciaRegistro1(String dataPermanenciaRegistro1){

		this.dataPermanenciaRegistro1 = dataPermanenciaRegistro1;
	}

	public String getDataPermanenciaRegistro2(){

		return dataPermanenciaRegistro2;
	}

	public void setDataPermanenciaRegistro2(String dataPermanenciaRegistro2){

		this.dataPermanenciaRegistro2 = dataPermanenciaRegistro2;
	}

	/**
	 * @return the nomeCliente1
	 */
	public String getNomeCliente1(){

		return nomeCliente1;
	}

	/**
	 * @param nomeCliente1
	 *            the nomeCliente1 to set
	 */
	public void setNomeCliente1(String nomeCliente1){

		this.nomeCliente1 = nomeCliente1;
	}

	/**
	 * @return the endereco1Linha2
	 */
	public String getEndereco1Linha2(){

		return endereco1Linha2;
	}

	/**
	 * @param endereco1Linha2
	 *            the endereco1Linha2 to set
	 */
	public void setEndereco1Linha2(String endereco1Linha2){

		this.endereco1Linha2 = endereco1Linha2;
	}

	/**
	 * @return the endereco1Linha3
	 */
	public String getEndereco1Linha3(){

		return endereco1Linha3;
	}

	/**
	 * @param endereco1Linha3
	 *            the endereco1Linha3 to set
	 */
	public void setEndereco1Linha3(String endereco1Linha3){

		this.endereco1Linha3 = endereco1Linha3;
	}

	/**
	 * @return the rotaGrupo1
	 */
	public String getRotaGrupo1(){

		return rotaGrupo1;
	}

	/**
	 * @param rotaGrupo1
	 *            the rotaGrupo1 to set
	 */
	public void setRotaGrupo1(String rotaGrupo1){

		this.rotaGrupo1 = rotaGrupo1;
	}

	/**
	 * @return the dataEmissao1
	 */
	public String getDataEmissao1(){

		return dataEmissao1;
	}

	/**
	 * @param dataEmissao1
	 *            the dataEmissao1 to set
	 */
	public void setDataEmissao1(String dataEmissao1){

		this.dataEmissao1 = dataEmissao1;
	}

	/**
	 * @return the mesAno1
	 */
	public String getMesAno1(){

		return mesAno1;
	}

	/**
	 * @param mesAno1
	 *            the mesAno1 to set
	 */
	public void setMesAno1(String mesAno1){

		this.mesAno1 = mesAno1;
	}

	/**
	 * @return the situacao1
	 */
	public String getSituacao1(){

		return situacao1;
	}

	/**
	 * @param situacao1
	 *            the situacao1 to set
	 */
	public void setSituacao1(String situacao1){

		this.situacao1 = situacao1;
	}

	/**
	 * @return the executor1
	 */
	public String getExecutor1(){

		return executor1;
	}

	/**
	 * @param executor1
	 *            the executor1 to set
	 */
	public void setExecutor1(String executor1){

		this.executor1 = executor1;
	}

	/**
	 * @return the dataCorte1
	 */
	public String getDataCorte1(){

		return dataCorte1;
	}

	/**
	 * @param dataCorte1
	 *            the dataCorte1 to set
	 */
	public void setDataCorte1(String dataCorte1){

		this.dataCorte1 = dataCorte1;
	}

	/**
	 * @return the horaCorte1
	 */
	public String getHoraCorte1(){

		return horaCorte1;
	}

	/**
	 * @param horaCorte1
	 *            the horaCorte1 to set
	 */
	public void setHoraCorte1(String horaCorte1){

		this.horaCorte1 = horaCorte1;
	}

	/**
	 * @return the numeroHidrometro1
	 */
	public String getNumeroHidrometro1(){

		return numeroHidrometro1;
	}

	/**
	 * @param numeroHidrometro1
	 *            the numeroHidrometro1 to set
	 */
	public void setNumeroHidrometro1(String numeroHidrometro1){

		this.numeroHidrometro1 = numeroHidrometro1;
	}

	/**
	 * @return the leituraHidrometro1
	 */
	public String getLeituraHidrometro1(){

		return leituraHidrometro1;
	}

	/**
	 * @param leituraHidrometro1
	 *            the leituraHidrometro1 to set
	 */
	public void setLeituraHidrometro1(String leituraHidrometro1){

		this.leituraHidrometro1 = leituraHidrometro1;
	}

	/**
	 * @return the localizacaoHidrometro1
	 */
	public String getLocalizacaoHidrometro1(){

		return localizacaoHidrometro1;
	}

	/**
	 * @param localizacaoHidrometro1
	 *            the localizacaoHidrometro1 to set
	 */
	public void setLocalizacaoHidrometro1(String localizacaoHidrometro1){

		this.localizacaoHidrometro1 = localizacaoHidrometro1;
	}

	/**
	 * @return the dataVencimento1
	 */
	public String getDataVencimento1(){

		return dataVencimento1;
	}

	/**
	 * @param dataVencimento1
	 *            the dataVencimento1 to set
	 */
	public void setDataVencimento1(String dataVencimento1){

		this.dataVencimento1 = dataVencimento1;
	}

	/**
	 * @return the valor1
	 */
	public String getValor1(){

		return valor1;
	}

	/**
	 * @param valor1
	 *            the valor1 to set
	 */
	public void setValor1(String valor1){

		this.valor1 = valor1;
	}

	/**
	 * @return the categoriaPrincipal1
	 */
	public String getCategoriaPrincipal1(){

		return categoriaPrincipal1;
	}

	/**
	 * @param categoriaPrincipal1
	 *            the categoriaPrincipal1 to set
	 */
	public void setCategoriaPrincipal1(String categoriaPrincipal1){

		this.categoriaPrincipal1 = categoriaPrincipal1;
	}

	/**
	 * @return the situacaoLigacaoAgua1
	 */
	public String getSituacaoLigacaoAgua1(){

		return situacaoLigacaoAgua1;
	}

	/**
	 * @param situacaoLigacaoAgua1
	 *            the situacaoLigacaoAgua1 to set
	 */
	public void setSituacaoLigacaoAgua1(String situacaoLigacaoAgua1){

		this.situacaoLigacaoAgua1 = situacaoLigacaoAgua1;
	}

	/**
	 * @return the situacaoLigacaoEsgoto1
	 */
	public String getSituacaoLigacaoEsgoto1(){

		return situacaoLigacaoEsgoto1;
	}

	/**
	 * @param situacaoLigacaoEsgoto1
	 *            the situacaoLigacaoEsgoto1 to set
	 */
	public void setSituacaoLigacaoEsgoto1(String situacaoLigacaoEsgoto1){

		this.situacaoLigacaoEsgoto1 = situacaoLigacaoEsgoto1;
	}

	/**
	 * @return the nomeCliente2
	 */
	public String getNomeCliente2(){

		return nomeCliente2;
	}

	/**
	 * @param nomeCliente2
	 *            the nomeCliente2 to set
	 */
	public void setNomeCliente2(String nomeCliente2){

		this.nomeCliente2 = nomeCliente2;
	}

	/**
	 * @return the endereco2Linha2
	 */
	public String getEndereco2Linha2(){

		return endereco2Linha2;
	}

	/**
	 * @param endereco2Linha2
	 *            the endereco2Linha2 to set
	 */
	public void setEndereco2Linha2(String endereco2Linha2){

		this.endereco2Linha2 = endereco2Linha2;
	}

	/**
	 * @return the endereco2Linha3
	 */
	public String getEndereco2Linha3(){

		return endereco2Linha3;
	}

	/**
	 * @param endereco2Linha3
	 *            the endereco2Linha3 to set
	 */
	public void setEndereco2Linha3(String endereco2Linha3){

		this.endereco2Linha3 = endereco2Linha3;
	}

	/**
	 * @return the rotaGrupo2
	 */
	public String getRotaGrupo2(){

		return rotaGrupo2;
	}

	/**
	 * @param rotaGrupo2
	 *            the rotaGrupo2 to set
	 */
	public void setRotaGrupo2(String rotaGrupo2){

		this.rotaGrupo2 = rotaGrupo2;
	}

	/**
	 * @return the dataEmissao2
	 */
	public String getDataEmissao2(){

		return dataEmissao2;
	}

	/**
	 * @param dataEmissao2
	 *            the dataEmissao2 to set
	 */
	public void setDataEmissao2(String dataEmissao2){

		this.dataEmissao2 = dataEmissao2;
	}

	/**
	 * @return the mesAno2
	 */
	public String getMesAno2(){

		return mesAno2;
	}

	/**
	 * @param mesAno2
	 *            the mesAno2 to set
	 */
	public void setMesAno2(String mesAno2){

		this.mesAno2 = mesAno2;
	}

	/**
	 * @return the situacao2
	 */
	public String getSituacao2(){

		return situacao2;
	}

	/**
	 * @param situacao2
	 *            the situacao2 to set
	 */
	public void setSituacao2(String situacao2){

		this.situacao2 = situacao2;
	}

	/**
	 * @return the executor2
	 */
	public String getExecutor2(){

		return executor2;
	}

	/**
	 * @param executor2
	 *            the executor2 to set
	 */
	public void setExecutor2(String executor2){

		this.executor2 = executor2;
	}

	/**
	 * @return the dataCorte2
	 */
	public String getDataCorte2(){

		return dataCorte2;
	}

	/**
	 * @param dataCorte2
	 *            the dataCorte2 to set
	 */
	public void setDataCorte2(String dataCorte2){

		this.dataCorte2 = dataCorte2;
	}

	/**
	 * @return the horaCorte2
	 */
	public String getHoraCorte2(){

		return horaCorte2;
	}

	/**
	 * @param horaCorte2
	 *            the horaCorte2 to set
	 */
	public void setHoraCorte2(String horaCorte2){

		this.horaCorte2 = horaCorte2;
	}

	/**
	 * @return the numeroHidrometro2
	 */
	public String getNumeroHidrometro2(){

		return numeroHidrometro2;
	}

	/**
	 * @param numeroHidrometro2
	 *            the numeroHidrometro2 to set
	 */
	public void setNumeroHidrometro2(String numeroHidrometro2){

		this.numeroHidrometro2 = numeroHidrometro2;
	}

	/**
	 * @return the leituraHidrometro2
	 */
	public String getLeituraHidrometro2(){

		return leituraHidrometro2;
	}

	/**
	 * @param leituraHidrometro2
	 *            the leituraHidrometro2 to set
	 */
	public void setLeituraHidrometro2(String leituraHidrometro2){

		this.leituraHidrometro2 = leituraHidrometro2;
	}

	/**
	 * @return the localizacaoHidrometro2
	 */
	public String getLocalizacaoHidrometro2(){

		return localizacaoHidrometro2;
	}

	/**
	 * @param localizacaoHidrometro2
	 *            the localizacaoHidrometro2 to set
	 */
	public void setLocalizacaoHidrometro2(String localizacaoHidrometro2){

		this.localizacaoHidrometro2 = localizacaoHidrometro2;
	}

	/**
	 * @return the dataVencimento2
	 */
	public String getDataVencimento2(){

		return dataVencimento2;
	}

	/**
	 * @param dataVencimento2
	 *            the dataVencimento2 to set
	 */
	public void setDataVencimento2(String dataVencimento2){

		this.dataVencimento2 = dataVencimento2;
	}

	/**
	 * @return the valor2
	 */
	public String getValor2(){

		return valor2;
	}

	/**
	 * @param valor2
	 *            the valor2 to set
	 */
	public void setValor2(String valor2){

		this.valor2 = valor2;
	}

	/**
	 * @return the categoriaPrincipal2
	 */
	public String getCategoriaPrincipal2(){

		return categoriaPrincipal2;
	}

	/**
	 * @param categoriaPrincipal2
	 *            the categoriaPrincipal2 to set
	 */
	public void setCategoriaPrincipal2(String categoriaPrincipal2){

		this.categoriaPrincipal2 = categoriaPrincipal2;
	}

	/**
	 * @return the situacaoLigacaoAgua2
	 */
	public String getSituacaoLigacaoAgua2(){

		return situacaoLigacaoAgua2;
	}

	/**
	 * @param situacaoLigacaoAgua2
	 *            the situacaoLigacaoAgua2 to set
	 */
	public void setSituacaoLigacaoAgua2(String situacaoLigacaoAgua2){

		this.situacaoLigacaoAgua2 = situacaoLigacaoAgua2;
	}

	/**
	 * @return the situacaoLigacaoEsgoto2
	 */
	public String getSituacaoLigacaoEsgoto2(){

		return situacaoLigacaoEsgoto2;
	}

	/**
	 * @param situacaoLigacaoEsgoto2
	 *            the situacaoLigacaoEsgoto2 to set
	 */
	public void setSituacaoLigacaoEsgoto2(String situacaoLigacaoEsgoto2){

		this.situacaoLigacaoEsgoto2 = situacaoLigacaoEsgoto2;
	}

	private Collection<Object> gerarDetail(Collection<String> collMesAno, Collection<String> collVencimento,
					Collection<BigDecimal> collValor){

		Collection<Object> colecaoDetail = new ArrayList<Object>();
		Collection<String> collStringValor = new ArrayList<String>();

		for(BigDecimal valor : collValor){
			collStringValor.add(Util.formatarMoedaReal(valor));
		}

		Object[] linhasMesAno = this.gerarLinhasDetail(collMesAno).toArray();
		Object[] linhasVencimento = this.gerarLinhasDetail(collVencimento).toArray();
		Object[] linhasValor = this.gerarLinhasDetail(collStringValor).toArray();

		for(int i = 0; i < linhasMesAno.length; i++){

			String[] linhaMesAno = (String[]) linhasMesAno[i];
			String[] linhaVencimento = (String[]) linhasVencimento[i];
			String[] linhaValor = (String[]) linhasValor[i];

			Object relatorio = new RelatorioDadosContaDetailBean(linhaMesAno, linhaVencimento, linhaValor);
			colecaoDetail.add(relatorio);

		}

		return colecaoDetail;
	}

	private Collection<String[]> gerarLinhasDetail(Collection<String> collString){

		Collection<String[]> retorno = new ArrayList<String[]>();
		String[] item = new String[RELATORIO_DETAIL_3_COLUNAS];
		int countLine = 0;
		boolean preencheuTodos = false;

		for(String valor : collString){
			if((countLine + 1) <= RELATORIO_DETAIL_3_COLUNAS){
				item[countLine] = valor;
			}

			if((countLine + 1) == RELATORIO_DETAIL_3_COLUNAS){
				String[] linhaValor = new String[RELATORIO_DETAIL_3_COLUNAS];

				for(int i = 1; i <= RELATORIO_DETAIL_3_COLUNAS; i++){
					linhaValor[i - 1] = item[i - 1];
				}

				retorno.add(linhaValor);
				item[0] = null;
				item[1] = null;
				item[2] = null;
				countLine = 0;
				preencheuTodos = true;
			}else{
				countLine++;
			}
		}

		if(countLine != 0){

			if(item[0] == null){
				item[0] = "";
			}
			if(item[1] == null){
				item[1] = "";
			}
			if(item[2] == null){
				item[2] = "";
			}
			retorno.add(item);
		}

		return retorno;
	}

}
