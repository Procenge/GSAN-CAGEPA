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

package gcom.cobranca.bean;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cobranca.CobrancaDocumentoItem;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaGeral;
import gcom.seguranca.acesso.usuario.Usuario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * [UC0214] Efetuar Parcelamento de Débitos
 * 
 * @author Roberta Costa
 * @date 28/03/2006
 */
public class NegociacaoOpcoesParcelamentoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public NegociacaoOpcoesParcelamentoHelper() {

	}

	private Set<CobrancaDocumentoItem> cobrancaDocumentoItemConta;

	private Set<CobrancaDocumentoItem> cobrancaDocumentoItemGuia;

	private Collection<OpcoesParcelamentoHelper> opcoesParcelamento;

	private BigDecimal valorEntrada;

	private BigDecimal valorDescontoAcrecismosImpotualidade;

	private BigDecimal valorDescontoInatividade;

	private BigDecimal valorDescontoAntiguidade;

	private BigDecimal percentualDescontoAcrescimosImpontualidade;

	private BigDecimal percentualDescontoAntiguidadeDebito;

	private BigDecimal percentualDescontoInatividadeLigacaoAgua;

	private ParcelamentoPerfil parcelamentoPerfil;

	private BigDecimal valorDescontoSancoesRDEspecial;

	private BigDecimal valorDescontoTarifaSocialRDEspecial;

	private BigDecimal valorDescontoPagamentoAVistaRDEspecial;

	private BigDecimal valorDescontoAcrescimosImpontualidadeRDEspecial;

	private String inPagamentoCartaoCredito;

	private Usuario usuarioLogado;

	private BigDecimal valorEntradaMinima;

	private BigDecimal valorEstornoDescontos;

	/**
	 * Valor minímo da prestação para usar na aba de negociação
	 */
	private BigDecimal valorMinimoPrestacao;

	private Date dataVencimentoEntrada;

	private Integer numeroDiasVencimentoEntrada;

	/**
	 * @return Retorna o campo valorEntrada.
	 */
	public BigDecimal getValorEntrada(){

		return valorEntrada;
	}

	/**
	 * @param valorEntrada
	 *            O valorEntrada a ser setado.
	 */
	public void setValorEntrada(BigDecimal valorEntrada){

		this.valorEntrada = valorEntrada;
	}

	/**
	 * @return Retorna o campo valorDescontoAcrecismosImpotualidade.
	 */
	public BigDecimal getValorDescontoAcrecismosImpotualidade(){

		return valorDescontoAcrecismosImpotualidade;
	}

	/**
	 * @param valorDescontoAcrecismosImpotualidade
	 *            O valorDescontoAcrecismosImpotualidade a ser setado.
	 */
	public void setValorDescontoAcrecismosImpotualidade(BigDecimal valorDescontoAcrecismosImpotualidade){

		this.valorDescontoAcrecismosImpotualidade = valorDescontoAcrecismosImpotualidade;
	}

	/**
	 * @return Retorna o campo valorDescontoAntiguidade.
	 */
	public BigDecimal getValorDescontoAntiguidade(){

		return valorDescontoAntiguidade;
	}

	/**
	 * @param valorDescontoAntiguidade
	 *            O valorDescontoAntiguidade a ser setado.
	 */
	public void setValorDescontoAntiguidade(BigDecimal valorDescontoAntiguidade){

		this.valorDescontoAntiguidade = valorDescontoAntiguidade;
	}

	/**
	 * @return Retorna o campo valorDescontoInatividade.
	 */
	public BigDecimal getValorDescontoInatividade(){

		return valorDescontoInatividade;
	}

	/**
	 * @param valorDescontoInatividade
	 *            O valorDescontoInatividade a ser setado.
	 */
	public void setValorDescontoInatividade(BigDecimal valorDescontoInatividade){

		this.valorDescontoInatividade = valorDescontoInatividade;
	}

	/**
	 * @return Retorna o campo opcoesParcelamento.
	 */
	public Collection<OpcoesParcelamentoHelper> getOpcoesParcelamento(){

		return opcoesParcelamento;
	}

	/**
	 * @param opcoesParcelamento
	 *            O opcoesParcelamento a ser setado.
	 */
	public void setOpcoesParcelamento(Collection<OpcoesParcelamentoHelper> opcoesParcelamento){

		this.opcoesParcelamento = opcoesParcelamento;
	}

	/**
	 * @return Retorna o campo parcelamentoPerfil.
	 */
	public ParcelamentoPerfil getParcelamentoPerfil(){

		return parcelamentoPerfil;
	}

	/**
	 * @param parcelamentoPerfil
	 *            O parcelamentoPerfil a ser setado.
	 */
	public void setParcelamentoPerfil(ParcelamentoPerfil parcelamentoPerfil){

		this.parcelamentoPerfil = parcelamentoPerfil;
	}

	/**
	 * @return Retorna o campo percentualDescontoAcrescimosImpontualidade.
	 */
	public BigDecimal getPercentualDescontoAcrescimosImpontualidade(){

		return percentualDescontoAcrescimosImpontualidade;
	}

	/**
	 * @param percentualDescontoAcrescimosImpontualidade
	 *            O percentualDescontoAcrescimosImpontualidade a ser setado.
	 */
	public void setPercentualDescontoAcrescimosImpontualidade(BigDecimal percentualDescontoAcrescimosImpontualidade){

		this.percentualDescontoAcrescimosImpontualidade = percentualDescontoAcrescimosImpontualidade;
	}

	/**
	 * @return Retorna o campo percentualDescontoAntiguidadeDebito.
	 */
	public BigDecimal getPercentualDescontoAntiguidadeDebito(){

		return percentualDescontoAntiguidadeDebito;
	}

	/**
	 * @param percentualDescontoAntiguidadeDebito
	 *            O percentualDescontoAntiguidadeDebito a ser setado.
	 */
	public void setPercentualDescontoAntiguidadeDebito(BigDecimal percentualDescontoAntiguidadeDebito){

		this.percentualDescontoAntiguidadeDebito = percentualDescontoAntiguidadeDebito;
	}

	/**
	 * @return Retorna o campo percentualDescontoInatividadeLigacaoAgua.
	 */
	public BigDecimal getPercentualDescontoInatividadeLigacaoAgua(){

		return percentualDescontoInatividadeLigacaoAgua;
	}

	/**
	 * @param percentualDescontoInatividadeLigacaoAgua
	 *            O percentualDescontoInatividadeLigacaoAgua a ser setado.
	 */
	public void setPercentualDescontoInatividadeLigacaoAgua(BigDecimal percentualDescontoInatividadeLigacaoAgua){

		this.percentualDescontoInatividadeLigacaoAgua = percentualDescontoInatividadeLigacaoAgua;
	}

	/**
	 * @return Retorna o campo valorMinimoPrestacao.
	 */
	public BigDecimal getValorMinimoPrestacao(){

		return valorMinimoPrestacao;
	}

	/**
	 * @param valorMinimoPrestacao
	 *            O valorMinimoPrestacao a ser setado.
	 */
	public void setValorMinimoPrestacao(BigDecimal valorMinimoPrestacao){

		this.valorMinimoPrestacao = valorMinimoPrestacao;
	}

	public BigDecimal getValorDescontoSancoesRDEspecial(){

		return valorDescontoSancoesRDEspecial;
	}

	public void setValorDescontoSancoesRDEspecial(BigDecimal valorDescontoSancoesRDEspecial){

		this.valorDescontoSancoesRDEspecial = valorDescontoSancoesRDEspecial;
	}

	/**
	 * @return Retorna o campo valorDescontoTarifaSocialRDEspecial.
	 */
	public BigDecimal getValorDescontoTarifaSocialRDEspecial(){

		return valorDescontoTarifaSocialRDEspecial;
	}

	/**
	 * @param valorDescontoTarifaSocialRDEspecial
	 *            O valorDescontoTarifaSocialRDEspecial a ser setado.
	 */
	public void setValorDescontoTarifaSocialRDEspecial(BigDecimal valorDescontoTarifaSocialRDEspecial){

		this.valorDescontoTarifaSocialRDEspecial = valorDescontoTarifaSocialRDEspecial;
	}

	/**
	 * @return Retorna o campo valorDescontoPagamentoAVistaRDEspecial.
	 */
	public BigDecimal getValorDescontoPagamentoAVistaRDEspecial(){

		return valorDescontoPagamentoAVistaRDEspecial;
	}

	/**
	 * @param valorDescontoPagamentoAVistaRDEspecial
	 *            O valorDescontoPagamentoAVistaRDEspecial a ser setado.
	 */
	public void setValorDescontoPagamentoAVistaRDEspecial(BigDecimal valorDescontoPagamentoAVistaRDEspecial){

		this.valorDescontoPagamentoAVistaRDEspecial = valorDescontoPagamentoAVistaRDEspecial;
	}

	public Set<CobrancaDocumentoItem> getCobrancaDocumentoItemConta(){

		return cobrancaDocumentoItemConta;
	}

	public void setCobrancaDocumentoItemConta(Set<CobrancaDocumentoItem> cobrancaDocumentoItemConta){

		this.cobrancaDocumentoItemConta = cobrancaDocumentoItemConta;
	}

	public Set<CobrancaDocumentoItem> getCobrancaDocumentoItemGuia(){

		return cobrancaDocumentoItemGuia;
	}

	public void setCobrancaDocumentoItemGuia(Set<CobrancaDocumentoItem> cobrancaDocumentoItemGuia){

		this.cobrancaDocumentoItemGuia = cobrancaDocumentoItemGuia;
	}

	public void setCobrancaDocumentoItemConta(Collection<ContaValoresHelper> colecaoConta){

		Set<CobrancaDocumentoItem> documentoItemConta = new HashSet<CobrancaDocumentoItem>();
		if(colecaoConta != null && !colecaoConta.equals("")){
			for(ContaValoresHelper conta : colecaoConta){
				CobrancaDocumentoItem item = new CobrancaDocumentoItem();
				ContaGeral contaGeral = new ContaGeral();
				contaGeral.setConta(conta.getConta());
				item.setContaGeral(contaGeral);
				documentoItemConta.add(item);
			}
			this.cobrancaDocumentoItemConta = documentoItemConta;
		}
	}

	public void setCobrancaDocumentoItemGuia(Collection<GuiaPagamentoValoresHelper> colecaoGuiaHelper){

		Set<CobrancaDocumentoItem> documentoItemGuia = new HashSet<CobrancaDocumentoItem>();
		if(colecaoGuiaHelper != null){
			for(GuiaPagamentoValoresHelper guiaHelper : colecaoGuiaHelper){
				CobrancaDocumentoItem item = new CobrancaDocumentoItem();
				GuiaPagamentoGeral guiaGeral = new GuiaPagamentoGeral();
				GuiaPagamento guiaPagamento = new GuiaPagamento();
				guiaPagamento.setId(guiaHelper.getIdGuiaPagamento());
				guiaGeral.setGuiaPagamento(guiaPagamento);
				guiaGeral.setId(guiaHelper.getIdGuiaPagamento());
				item.setNumeroDaPrestacao(guiaHelper.getNumeroPrestacao());
				item.setGuiaPagamentoGeral(guiaGeral);
				documentoItemGuia.add(item);
			}
			this.cobrancaDocumentoItemGuia = documentoItemGuia;
		}
	}

	public BigDecimal incrementaDescontoMulta(ContaValoresHelper conta, BigDecimal valorDescontoMultaItem){

		BigDecimal valorDescontoMulta = BigDecimal.ZERO;

		if(valorDescontoMultaItem != null && conta != null){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
				if(cobrancaDocumentoItem != null){
					if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getConta().getId()) == 0){
						valorDescontoMulta = cobrancaDocumentoItem
										.getValorDescontoMulta()
										.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO)
										.add(valorDescontoMultaItem.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

						cobrancaDocumentoItem.setValorDescontoMulta(valorDescontoMulta);

						break;
					}
				}
			}
		}

		return valorDescontoMulta;
	}

	public BigDecimal incrementaDescontoMora(ContaValoresHelper conta, BigDecimal valorDescontoMoraItem){

		BigDecimal valorDescontoMora = BigDecimal.ZERO;

		if(valorDescontoMoraItem != null && conta != null){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
				if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getConta().getId()) == 0){
					valorDescontoMora = cobrancaDocumentoItem.getValorDescontoMora()
									.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO)
									.add(valorDescontoMoraItem.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

					cobrancaDocumentoItem.setValorDescontoMora(valorDescontoMora);

					break;
				}
			}
		}

		return valorDescontoMora;
	}

	public BigDecimal incrementaDescontoCorrecaoMonetaria(ContaValoresHelper conta, BigDecimal valorDescontoCorrecaoMonetariaItem){

		BigDecimal valorDescontoCorrecaoMonetaria = BigDecimal.ZERO;

		if(valorDescontoCorrecaoMonetariaItem != null && conta != null){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
				if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getConta().getId()) == 0){
					valorDescontoCorrecaoMonetaria = cobrancaDocumentoItem
									.getValorDescontoCorrecaoMonetaria()
									.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO)
									.add(valorDescontoCorrecaoMonetariaItem.setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));

					cobrancaDocumentoItem.setValorDescontoCorrecaoMonetaria(valorDescontoCorrecaoMonetaria);

					break;
				}
			}
		}

		return valorDescontoCorrecaoMonetaria;
	}

	public void incrementaDescontoMulta(GuiaPagamentoValoresHelper guia, BigDecimal valorDescontoMulta){

		if(valorDescontoMulta != null && guia != null){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){

				if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getIdGuiaPagamento()) == 0
								&& cobrancaDocumentoItem.getNumeroDaPrestacao().intValue() == guia.getNumeroPrestacao().intValue()){

					cobrancaDocumentoItem.setValorDescontoMulta(cobrancaDocumentoItem.getValorDescontoMulta().setScale(
									Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO).add(
									valorDescontoMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO)));
					break;
				}
			}
		}
	}

	public void incrementaDescontoMora(GuiaPagamentoValoresHelper guia, BigDecimal valorDescontoMoraItem){

		if(valorDescontoMoraItem != null && guia != null){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){

				if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getIdGuiaPagamento()) == 0
								&& cobrancaDocumentoItem.getNumeroDaPrestacao().intValue() == guia.getNumeroPrestacao().intValue()){

					cobrancaDocumentoItem.setValorDescontoMora(cobrancaDocumentoItem.getValorDescontoMulta().setScale(
									Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO).add(
									valorDescontoMoraItem.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO)));
					break;
				}
			}
		}
	}

	public void incrementaDescontoCorrecaoMonetaria(GuiaPagamentoValoresHelper guia, BigDecimal valorDescontoCorrecaoMonetariaItem){

		if(valorDescontoCorrecaoMonetariaItem != null && guia != null){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){

				if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getIdGuiaPagamento()) == 0
								&& cobrancaDocumentoItem.getNumeroDaPrestacao().intValue() == guia.getNumeroPrestacao().intValue()){

					cobrancaDocumentoItem.setValorDescontoCorrecaoMonetaria(cobrancaDocumentoItem.getValorDescontoCorrecaoMonetaria()
									.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO).add(
													valorDescontoCorrecaoMonetariaItem.setScale(Parcelamento.CASAS_DECIMAIS,
																	Parcelamento.TIPO_ARREDONDAMENTO)));
					break;
				}
			}
		}
	}

	public void incrementaDescontoValor(ContaValoresHelper conta, BigDecimal valorDescontoValorItem){

		if(valorDescontoValorItem != null && conta != null){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
				if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getConta().getId()) == 0){
					cobrancaDocumentoItem.setValorDescontoValor(cobrancaDocumentoItem.getValorDescontoValor().setScale(
									Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO).add(
									valorDescontoValorItem.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO)));
					break;
				}
			}
		}
	}

	public BigDecimal getValorDescontoMultaContaIndividual(Conta conta){

		if(conta != null && !conta.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
			if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoMulta();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoMoraContaIndividual(Conta conta){

		if(conta != null && !conta.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
			if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoMora();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoCorrecaoContaIndividual(Conta conta){

		if(conta != null && !conta.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
			if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoCorrecaoMonetaria();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoValorContaIndividual(Conta conta){

		if(conta != null && !conta.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
			if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoValor();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoDocumentoContaIndividual(Conta conta){

		if(conta != null && !conta.equals("")){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemConta){
				if(cobrancaDocumentoItem.getContaGeral().getConta().getId().compareTo(conta.getId()) == 0){
					return cobrancaDocumentoItem.getValorDescontoDocumento();
				}
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoMultaGuiaIndividual(GuiaPagamento guia){

		if(guia != null && !guia.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){
			if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoMulta();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoMoraGuiaIndividual(GuiaPagamento guia){

		if(guia != null && !guia.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){
			if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoMora();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoCorrecaoGuiaIndividual(GuiaPagamento guia){

		if(guia != null && !guia.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){
			if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoCorrecaoMonetaria();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoValorGuiaIndividual(GuiaPagamento guia){

		if(guia != null && !guia.equals("")) for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){
			if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getId()) == 0){
				return cobrancaDocumentoItem.getValorDescontoValor();
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoDocumentoGuiaIndividual(GuiaPagamento guia){

		if(guia != null && !guia.equals("")){
			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItemGuia){
				if(cobrancaDocumentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId().compareTo(guia.getId()) == 0){
					return cobrancaDocumentoItem.getValorDescontoDocumento();
				}
			}
		}
		return new BigDecimal("0.00");
	}

	public BigDecimal getValorDescontoAcrescimosImpontualidadeRDEspecial(){

		return valorDescontoAcrescimosImpontualidadeRDEspecial;
	}

	public void setValorDescontoAcrescimosImpontualidadeRDEspecial(BigDecimal valorDescontoAcrescimosImpontualidadeRDEspecial){

		this.valorDescontoAcrescimosImpontualidadeRDEspecial = valorDescontoAcrescimosImpontualidadeRDEspecial;
	}

	public Date getDataVencimentoEntrada(){

		return dataVencimentoEntrada;
	}

	public void setDataVencimentoEntrada(Date dataVencimentoEntrada){

		this.dataVencimentoEntrada = dataVencimentoEntrada;
	}

	public void setInPagamentoCartaoCredito(String inPagamentoCartaoCredito){

		this.inPagamentoCartaoCredito = inPagamentoCartaoCredito;
	}

	public String getInPagamentoCartaoCredito(){

		return inPagamentoCartaoCredito;
	}

	public void setUsuarioLogado(Usuario usuarioLogado){

		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuarioLogado(){

		return usuarioLogado;
	}

	public Integer getNumeroDiasVencimentoEntrada(){

		return numeroDiasVencimentoEntrada;
	}

	public void setNumeroDiasVencimentoEntrada(Integer numeroDiasVencimentoEntrada){

		this.numeroDiasVencimentoEntrada = numeroDiasVencimentoEntrada;
	}

	public BigDecimal getValorEntradaMinima(){

		return valorEntradaMinima;
	}

	public void setValorEntradaMinima(BigDecimal valorEntradaMinima){

		this.valorEntradaMinima = valorEntradaMinima;
	}

	public BigDecimal getValorEstornoDescontos(){

		return valorEstornoDescontos;
	}

	public void setValorEstornoDescontos(BigDecimal valorEstornoDescontos){

		this.valorEstornoDescontos = valorEstornoDescontos;
	}

}