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

package gcom.cobranca.ajustetarifa;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.transacao.RegistradorLogTransacoes;

import java.math.BigDecimal;
import java.util.Date;

public class AjusteTarifa
				extends RegistradorLogTransacoes {

	// private static final long serialVersionUID = 1L;

	private Integer id;

	private BigDecimal valorAnterior;

	private BigDecimal valorAtual;

	private BigDecimal valorCredito;

	private BigDecimal valorSaldo;

	private Date dataCalculo;

	private Integer numeroParcelas;

	private String indicadorAdimplente;

	private String indicadorProcessado;

	private String descricaoProcessado;

	private String descricaoLog;

	private BigDecimal valorUtilizado;

	private String indicadorLancado;

	private BigDecimal valorResidual;

	private Date ultimaAlteracao;

	private Imovel imovel;

	private LigacaoAguaSituacao ligacaoAguaSituacao;

	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	private Categoria categoria;

	private Usuario usuario;

	private CreditoARealizar creditoARealizar;

	private ConsumoTarifa consumoTarifa;


	public AjusteTarifa() {
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public BigDecimal getValorAnterior(){

		return valorAnterior;
	}

	public void setValorAnterior(BigDecimal valorAnterior){

		this.valorAnterior = valorAnterior;
	}

	public BigDecimal getValorAtual(){

		return valorAtual;
	}

	public void setValorAtual(BigDecimal valorAtual){

		this.valorAtual = valorAtual;
	}

	public BigDecimal getValorCredito(){

		return valorCredito;
	}

	public void setValorCredito(BigDecimal valorCredito){

		this.valorCredito = valorCredito;
	}

	public BigDecimal getValorSaldo(){

		return valorSaldo;
	}

	public void setValorSaldo(BigDecimal valorSaldo){

		this.valorSaldo = valorSaldo;
	}

	public Date getDataCalculo(){

		return dataCalculo;
	}

	public void setDataCalculo(Date dataCalculo){

		this.dataCalculo = dataCalculo;
	}

	public Integer getNumeroParcelas(){

		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas){

		this.numeroParcelas = numeroParcelas;
	}

	public String getIndicadorAdimplente(){

		return indicadorAdimplente;
	}

	public void setIndicadorAdimplente(String indicadorAdimplente){

		this.indicadorAdimplente = indicadorAdimplente;
	}

	public String getIndicadorProcessado(){

		return indicadorProcessado;
	}

	public void setIndicadorProcessado(String indicadorProcessado){

		this.indicadorProcessado = indicadorProcessado;
	}

	public String getDescricaoProcessado(){

		return descricaoProcessado;
	}

	public void setDescricaoProcessado(String descricaoProcessado){

		this.descricaoProcessado = descricaoProcessado;
	}

	public BigDecimal getValorUtilizado(){

		return valorUtilizado;
	}

	public void setValorUtilizado(BigDecimal valorUtilizado){

		this.valorUtilizado = valorUtilizado;
	}

	public String getIndicadorLancado(){

		return indicadorLancado;
	}

	public void setIndicadorLancado(String indicadorLancado){

		this.indicadorLancado = indicadorLancado;
	}

	public BigDecimal getValorResidual(){

		return valorResidual;
	}

	public void setValorResidual(BigDecimal valorResidual){

		this.valorResidual = valorResidual;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public CreditoARealizar getCreditoARealizar(){

		return creditoARealizar;
	}

	public void setCreditoARealizar(CreditoARealizar creditoARealizar){

		this.creditoARealizar = creditoARealizar;
	}

	public ConsumoTarifa getConsumoTarifa(){

		return consumoTarifa;
	}

	public void setConsumoTarifa(ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	@Override
	public String getDescricaoLog(){

		return descricaoLog;
	}

	public void setDescricaoLog(String descricaoLog){

		this.descricaoLog = descricaoLog;
	}

}
