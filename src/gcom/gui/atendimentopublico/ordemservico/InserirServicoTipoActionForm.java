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
 * GSANPCG
 * Saulo Lima
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.*;
import gcom.cadastro.localidade.Localidade;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.DebitoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * InserirServicoTipoActionForm
 * 
 * @author lms
 * @date 01/08/2006
 * @author Virgínia Melo
 * @date 11/12/2008
 *       Alterações no [UC0410] para a v0.07
 */
public class InserirServicoTipoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	ServicoTipo servicoTipo = new ServicoTipo();

	private String descricao;

	private String abreviatura;

	private String subgrupo;

	private String valor;

	private String pavimento;

	private String atualizacaoComercial;

	private String servicoTerceirizado;

	private String codigoServico;

	private String tempoMedioExecucao;

	private String idTipoDebito;

	private String descricaoTipoDebito;

	private String tipoCredito;

	private String prioridadeServico;

	private String idPerfilServico;

	private String descricaoPerfilServico;

	private String idTipoServicoReferencia;

	private String descricaoTipoServicoReferencia;

	private String atividadeUnica;

	private String method;

	private String atividadeId;

	private String servicoTipoAtividadeId;

	private String servicoTipoAtividadeDescricao;

	private String servicoTipoAtividadeOrdemExecucao;

	private String valorLocalidadeId;

	private String servicoTipoValorLocalidadeId;

	private String servicoTipoValorLocalidadeDescricao;

	private String servicoTipoValorLocalidadeValor;

	private String materialId;

	private String servicoTipoMaterialId;

	private String servicoTipoMaterialDescricao;

	private String servicoTipoMaterialQuantidadePadrao;

	private String indicadorVistoria;

	private String indicadorFiscalizacaoInfracao;

	private String valorRemuneracao;

	private String percentualRemuneracao;

	private String prazoExecucao;

	private String tipoRemuneracao;

	private String idOrdemServicoLayout;

	private String indicadorDeslocamento;

	private String indicadorHorariosExecucao;

	private String indicadorCausaOcorrencia;

	private String indicadorRedeRamalAgua;

	private String indicadorRedeRamalEsgoto;

	private String indicadorMaterial;

	private String indicadorVala;

	private String indicadorOrdemSeletiva;

	private String indicadorServicoCritico;

	Collection<ServicoTipoAtividade> servicoTipoAtividades = new ArrayList<ServicoTipoAtividade>();

	Collection<ServicoTipoMaterial> servicoTipoMateriais = new ArrayList<ServicoTipoMaterial>();

	Collection<ServicoTipoValorLocalidade> servicoTipoValorLocalidade = new ArrayList<ServicoTipoValorLocalidade>();

	public String getAbreviatura(){

		return abreviatura;
	}

	public void setAbreviatura(String abreviatura){

		this.abreviatura = abreviatura;
	}

	public String getAtividadeUnica(){

		return atividadeUnica;
	}

	public void setAtividadeUnica(String atividadeUnica){

		this.atividadeUnica = atividadeUnica;
	}

	public String getAtualizacaoComercial(){

		return atualizacaoComercial;
	}

	public void setAtualizacaoComercial(String atualizacaoComercial){

		this.atualizacaoComercial = atualizacaoComercial;
	}

	public String getCodigoServico(){

		return codigoServico;
	}

	public void setCodigoServico(String codigoServico){

		this.codigoServico = codigoServico;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoPerfilServico(){

		return descricaoPerfilServico;
	}

	public void setDescricaoPerfilServico(String descricaoPerfilServico){

		this.descricaoPerfilServico = descricaoPerfilServico;
	}

	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	public String getDescricaoTipoServicoReferencia(){

		return descricaoTipoServicoReferencia;
	}

	public void setDescricaoTipoServicoReferencia(String descricaoTipoServicoReferencia){

		this.descricaoTipoServicoReferencia = descricaoTipoServicoReferencia;
	}

	public String getIdPerfilServico(){

		return idPerfilServico;
	}

	public void setIdPerfilServico(String idPerfilServico){

		this.idPerfilServico = idPerfilServico;
	}

	public String getIdTipoDebito(){

		return idTipoDebito;
	}

	public void setIdTipoDebito(String idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	public String getIdTipoServicoReferencia(){

		return idTipoServicoReferencia;
	}

	public void setIdTipoServicoReferencia(String idTipoServicoReferencia){

		this.idTipoServicoReferencia = idTipoServicoReferencia;
	}

	public String getPavimento(){

		return pavimento;
	}

	public void setPavimento(String pavimento){

		this.pavimento = pavimento;
	}

	public String getPrioridadeServico(){

		return prioridadeServico;
	}

	public void setPrioridadeServico(String prioridadeServico){

		this.prioridadeServico = prioridadeServico;
	}

	public String getServicoTerceirizado(){

		return servicoTerceirizado;
	}

	public void setServicoTerceirizado(String servicoTerceirizado){

		this.servicoTerceirizado = servicoTerceirizado;
	}

	public String getSubgrupo(){

		return subgrupo;
	}

	public void setSubgrupo(String subgrupo){

		this.subgrupo = subgrupo;
	}

	public String getTempoMedioExecucao(){

		return tempoMedioExecucao;
	}

	public void setTempoMedioExecucao(String tempoMedioExecucao){

		this.tempoMedioExecucao = tempoMedioExecucao;
	}

	public String getTipoCredito(){

		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito){

		this.tipoCredito = tipoCredito;
	}

	public String getValor(){

		return valor;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public ServicoTipo getServicoTipo(){

		return setFormValues(servicoTipo);
	}

	public void setServicoTipo(ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String getServicoTipoAtividadeDescricao(){

		return servicoTipoAtividadeDescricao;
	}

	public String getServicoTipoValorLocalidadeDescricao(){

		return servicoTipoValorLocalidadeDescricao;
	}

	public String getValorLocalidadeId(){

		return valorLocalidadeId;
	}

	public void setValorLocalidadeId(String valorLocalidadeId){

		this.valorLocalidadeId = valorLocalidadeId;
	}

	public void setServicoTipoValorLocalidadeDescricao(String servicoTipoValorLocalidadeDescricao){

		this.servicoTipoValorLocalidadeDescricao = servicoTipoValorLocalidadeDescricao;
	}

	public String getServicoTipoValorLocalidadeValor(){

		return servicoTipoValorLocalidadeValor;
	}

	public void setServicoTipoValorLocalidadeValor(String servicoTipoValorLocalidadeValor){

		this.servicoTipoValorLocalidadeValor = servicoTipoValorLocalidadeValor;
	}

	public void setServicoTipoAtividadeDescricao(String servicoTipoAtividadeDescricao){

		this.servicoTipoAtividadeDescricao = servicoTipoAtividadeDescricao;
	}

	public String getServicoTipoAtividadeId(){

		return servicoTipoAtividadeId;
	}

	public void setServicoTipoAtividadeId(String servicoTipoAtividadeId){

		this.servicoTipoAtividadeId = servicoTipoAtividadeId;
	}

	public String getServicoTipoAtividadeOrdemExecucao(){

		return servicoTipoAtividadeOrdemExecucao;
	}

	public void setServicoTipoAtividadeOrdemExecucao(String servicoTipoAtividadeOrdemExecucao){

		this.servicoTipoAtividadeOrdemExecucao = servicoTipoAtividadeOrdemExecucao;
	}

	public String getMethod(){

		return method;
	}

	public void setMethod(String method){

		this.method = method;
	}

	public Collection<ServicoTipoAtividade> getServicoTipoAtividades(){

		return servicoTipoAtividades;
	}

	public Collection<ServicoTipoMaterial> getServicoTipoMateriais(){

		return servicoTipoMateriais;
	}

	public Collection<ServicoTipoValorLocalidade> getServicoTipoValorLocalidade(){

		return servicoTipoValorLocalidade;
	}


	public void setServicoTipoValorLocalidade(Collection<ServicoTipoValorLocalidade> servicoTipoValorLocalidade){

		this.servicoTipoValorLocalidade = servicoTipoValorLocalidade;
	}

	public String getAtividadeId(){

		return atividadeId;
	}

	public void setAtividadeId(String atividadeId){

		this.atividadeId = atividadeId;
	}

	public String getMaterialId(){

		return materialId;
	}

	public void setMaterialId(String materialId){

		this.materialId = materialId;
	}

	public String getServicoTipoMaterialDescricao(){

		return servicoTipoMaterialDescricao;
	}

	public void setServicoTipoMaterialDescricao(String servicoTipoMaterialDescricao){

		this.servicoTipoMaterialDescricao = servicoTipoMaterialDescricao;
	}

	public String getServicoTipoMaterialId(){

		return servicoTipoMaterialId;
	}

	public void setServicoTipoMaterialId(String servicoTipoMaterialId){

		this.servicoTipoMaterialId = servicoTipoMaterialId;
	}

	public String getServicoTipoMaterialQuantidadePadrao(){

		return servicoTipoMaterialQuantidadePadrao;
	}

	public void setServicoTipoMaterialQuantidadePadrao(String servicoTipoMaterialQuantidadePadrao){

		this.servicoTipoMaterialQuantidadePadrao = servicoTipoMaterialQuantidadePadrao;
	}

	public void addServicoTipoAtividade(){

		Atividade atv;
		ServicoTipoAtividade stp;
		atv = new Atividade();
		atv.setId(Integer.parseInt(getAtividadeId()));
		atv.setDescricao(getServicoTipoAtividadeDescricao());
		stp = new ServicoTipoAtividade();
		stp.setNumeroExecucao(Short.parseShort(getServicoTipoAtividadeOrdemExecucao()));
		stp.setAtividade(atv);
		servicoTipoAtividades.add(stp);
	}

	public void removeServicoTipoAtividade(){

		for(Iterator iter = getServicoTipoAtividades().iterator(); iter.hasNext();){
			ServicoTipoAtividade stp = (ServicoTipoAtividade) iter.next();
			String string = "$" + stp.getAtividade().getId() + "$" + stp.getNumeroExecucao() + "$";
			if(string.equals(getServicoTipoAtividadeId())){
				iter.remove();
			}
		}
	}

	public void removeAllServicoTipoAtividade(){

		for(Iterator iter = getServicoTipoAtividades().iterator(); iter.hasNext();){
			iter.next();
			iter.remove();
		}
	}

	public void addServicoTipoValorLocalidade(){

		Localidade loca = new Localidade();
		ServicoTipoValorLocalidade stvl = new ServicoTipoValorLocalidade();

		loca.setId(Util.converterStringParaInteger(getValorLocalidadeId()));
		loca.setDescricao(getServicoTipoValorLocalidadeDescricao());

		if(getServicoTipoValorLocalidadeValor() != null){
			stvl.setValorServico(Util.formatarStringParaBigDecimal(getServicoTipoValorLocalidadeValor(), 2, true));
		}

		stvl.setLocalidade(loca);
		stvl.setIndicadorUso(ConstantesSistema.SIM);
		stvl.setUltimaAlteracao(new Date());

		servicoTipoValorLocalidade.add(stvl);
	}

	public void removeServicoTipoValorLocalidade(){

		for(Iterator iter = getServicoTipoValorLocalidade().iterator(); iter.hasNext();){
			ServicoTipoValorLocalidade stvl = (ServicoTipoValorLocalidade) iter.next();
			String string = stvl.getLocalidade().getId().toString();
			if(string.equals(getValorLocalidadeId())){
				iter.remove();
			}
		}
	}

	public void addServicoTipoMaterial(){

		Material mat;
		ServicoTipoMaterial stm;
		mat = new Material();
		mat.setId(Integer.parseInt(getMaterialId()));
		mat.setDescricao(getServicoTipoMaterialDescricao());
		stm = new ServicoTipoMaterial();
		try{
			stm.setQuantidadePadrao(new BigDecimal(getServicoTipoMaterialQuantidadePadrao()));
		}catch(NumberFormatException e){
		}
		stm.setMaterial(mat);
		servicoTipoMateriais.add(stm);
	}

	public void removeServicoTipoMaterial(){

		for(Iterator iter = getServicoTipoMateriais().iterator(); iter.hasNext();){
			ServicoTipoMaterial stm = (ServicoTipoMaterial) iter.next();
			String string = "$" + stm.getMaterial().getId() + "$";
			if(string.equals(getServicoTipoMaterialId())){
				iter.remove();
			}
		}
	}

	private ServicoTipo setFormValues(ServicoTipo servicoTipo){

		// descricao
		servicoTipo.setDescricao(getDescricao());

		// abreviatura
		servicoTipo.setDescricaoAbreviada(getAbreviatura());

		// subgrupo
		Integer idSubgrupo = Util.converterStringParaInteger(getSubgrupo());
		if(Util.validarNumeroMaiorQueZERO(idSubgrupo)){
			ServicoTipoSubgrupo servicoTipoSubgrupo = new ServicoTipoSubgrupo();
			servicoTipoSubgrupo.setId(idSubgrupo);
			servicoTipo.setServicoTipoSubgrupo(servicoTipoSubgrupo);
		}

		// indicador atualização comercial
		if(Util.validarNumeroMaiorQueZERO(getAtualizacaoComercial())){
			servicoTipo.setIndicadorAtualizaComercial(Short.parseShort(getAtualizacaoComercial()));
		}

		// indicador pavimento
		if(Util.validarNumeroMaiorQueZERO(getPavimento())){
			servicoTipo.setIndicadorPavimento(Short.parseShort(getPavimento()));
		}

		// indicador serviço terceirizado
		if(Util.validarNumeroMaiorQueZERO(getServicoTerceirizado())){
			servicoTipo.setIndicadorTerceirizado(Short.parseShort(getServicoTerceirizado()));
		}

		// código tipo de serviço
		servicoTipo.setCodigoServicoTipo(getCodigoServico());

		// valor do serviço
		if(getValor() != null){
			try{
				servicoTipo.setValor(Util.formatarMoedaRealparaBigDecimal(getValor()));
			}catch(NumberFormatException e){
			}
		}

		// tempo médio execução
		if(Util.validarNumeroMaiorQueZERO(getTempoMedioExecucao())){
			servicoTipo.setTempoMedioExecucao(Short.parseShort(getTempoMedioExecucao()));
		}

		// tipo débito
		Integer idDebitoTipo = Util.converterStringParaInteger(getIdTipoDebito());
		if(Util.validarNumeroMaiorQueZERO(idDebitoTipo)){
			DebitoTipo debitoTipo = new DebitoTipo();
			debitoTipo.setId(idDebitoTipo);
			servicoTipo.setDebitoTipo(debitoTipo);
		}

		// tipo crébito
		Integer idCreditoTipo = Util.converterStringParaInteger(getTipoCredito());
		if(Util.validarNumeroMaiorQueZERO(idCreditoTipo)){
			CreditoTipo creditoTipo = new CreditoTipo();
			creditoTipo.setId(idCreditoTipo);
			servicoTipo.setCreditoTipo(creditoTipo);
		}

		// prioridade
		Integer idPrioridade = Util.converterStringParaInteger(getPrioridadeServico());
		if(Util.validarNumeroMaiorQueZERO(idPrioridade)){
			ServicoTipoPrioridade servicoTipoPrioridade = new ServicoTipoPrioridade();
			servicoTipoPrioridade.setId(idPrioridade);
			servicoTipo.setServicoTipoPrioridade(servicoTipoPrioridade);
		}

		// perfil
		Integer idServicoTipoPerfil = Util.converterStringParaInteger(getIdPerfilServico());
		if(Util.validarNumeroMaiorQueZERO(idServicoTipoPerfil)){
			ServicoPerfilTipo servicoPerfilTipo = new ServicoPerfilTipo();
			servicoPerfilTipo.setId(idServicoTipoPerfil);
			servicoTipo.setServicoPerfilTipo(servicoPerfilTipo);
		}

		// tipo serviço referência
		Integer idServicoTipoReferencia = Util.converterStringParaInteger(getIdTipoServicoReferencia());
		if(Util.validarNumeroMaiorQueZERO(idServicoTipoReferencia)){
			ServicoTipoReferencia servicoTipoReferencia = new ServicoTipoReferencia();
			servicoTipoReferencia.setId(idServicoTipoReferencia);
			servicoTipo.setServicoTipoReferencia(servicoTipoReferencia);
		}

		// indicador Vistoria
		if(Util.validarNumeroMaiorQueZERO(getIndicadorVistoria())){
			servicoTipo.setIndicadorVistoria(Short.parseShort(getIndicadorVistoria()));
		}

		// indicador Fiscalizacao Infração
		if(Util.validarNumeroMaiorQueZERO(getIndicadorFiscalizacaoInfracao())){
			servicoTipo.setIndicadorFiscalizacaoInfracao(Short.parseShort(getIndicadorFiscalizacaoInfracao()));
		}

		// data última alteração
		servicoTipo.setUltimaAlteracao(new Date());

		// atividades
		servicoTipo.setServicoTipoAtividades(getServicoTipoAtividades());

		// materiais
		servicoTipo.setServicoTipoMateriais(getServicoTipoMateriais());

		// Adicionar campos referente a remuneração (09/12/2008)

		// Valor Remuneração
		if(getValorRemuneracao() != null && !getValorRemuneracao().equals("")){
			try{
				servicoTipo.setValorRemuneracao(Util.formatarMoedaRealparaBigDecimal(getValorRemuneracao()));
			}catch(NumberFormatException e){
			}
		}

		// Percentual de Remuneração
		if(getPercentualRemuneracao() != null && !getPercentualRemuneracao().equals("")){
			try{
				servicoTipo.setPercentualRemuneracao(Util.formatarMoedaRealparaBigDecimal(getPercentualRemuneracao()));
			}catch(NumberFormatException e){
			}
		}

		// Prazo de Execução de Serviço
		if(getPrazoExecucao() != null){
			try{
				servicoTipo.setPrazoExecucao(Integer.valueOf(getPrazoExecucao()));
			}catch(NumberFormatException e){
			}
		}

		// Indicador Tipo Remuneração
		if(Util.validarNumeroMaiorQueZERO(getTipoRemuneracao())){
			servicoTipo.setTipoRemuneracao(Short.parseShort(getTipoRemuneracao()));
		}

		// Layout Ordem Servico
		Integer idOrdemServicoLayout = Util.converterStringParaInteger(getIdOrdemServicoLayout());
		if(Util.validarNumeroMaiorQueZERO(idOrdemServicoLayout)){
			OrdemServicoLayout ordemServicoLayout = new OrdemServicoLayout();
			ordemServicoLayout.setId(idOrdemServicoLayout);
			servicoTipo.setOrdemServicoLayout(ordemServicoLayout);
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorDeslocamento())){
			servicoTipo.setIndicadorDeslocamento(Integer.parseInt(getIndicadorDeslocamento()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorHorariosExecucao())){
			servicoTipo.setIndicadorHorariosExecucao(Integer.parseInt(getIndicadorHorariosExecucao()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorCausaOcorrencia())){
			servicoTipo.setIndicadorCausaOcorrencia(Integer.parseInt(getIndicadorCausaOcorrencia()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorRedeRamalAgua())){
			servicoTipo.setIndicadorRedeRamalAgua(Integer.parseInt(getIndicadorRedeRamalAgua()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorRedeRamalEsgoto())){
			servicoTipo.setIndicadorRedeRamalEsgoto(Integer.parseInt(getIndicadorRedeRamalEsgoto()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorMaterial())){
			servicoTipo.setIndicadorMaterial(Integer.parseInt(getIndicadorMaterial()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorVala())){
			servicoTipo.setIndicadorVala(Integer.parseInt(getIndicadorVala()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorOrdemSeletiva())){
			servicoTipo.setIndicadorOrdemSeletiva(Integer.parseInt(getIndicadorOrdemSeletiva()));
		}

		if(Util.validarNumeroMaiorQueZERO(getIndicadorServicoCritico())){
			servicoTipo.setIndicadorServicoCritico(Integer.parseInt(getIndicadorServicoCritico()));
		}

		return servicoTipo;
	}

	public void reset(){

		servicoTipo = new ServicoTipo();

		descricao = null;
		abreviatura = null;
		subgrupo = null;
		valor = null;
		pavimento = null;
		atualizacaoComercial = null;
		servicoTerceirizado = null;
		codigoServico = null;
		tempoMedioExecucao = null;
		idTipoDebito = null;
		descricaoTipoDebito = null;
		tipoCredito = null;
		prioridadeServico = null;
		idPerfilServico = null;
		descricaoPerfilServico = null;
		idTipoServicoReferencia = null;
		descricaoTipoServicoReferencia = null;
		atividadeUnica = null;
		indicadorFiscalizacaoInfracao = null;
		indicadorVistoria = null;

		valorRemuneracao = null;
		percentualRemuneracao = null;
		prazoExecucao = null;
		tipoRemuneracao = null;

		method = null;

		atividadeId = null;
		servicoTipoAtividadeId = null;
		servicoTipoAtividadeDescricao = null;
		servicoTipoAtividadeOrdemExecucao = null;

		valorLocalidadeId = null;
		servicoTipoValorLocalidadeId = null;
		servicoTipoValorLocalidadeDescricao = null;
		servicoTipoValorLocalidadeValor = null;

		materialId = null;
		servicoTipoMaterialId = null;
		servicoTipoMaterialDescricao = null;
		servicoTipoMaterialQuantidadePadrao = null;

		servicoTipoAtividades = new ArrayList<ServicoTipoAtividade>();
		servicoTipoMateriais = new ArrayList<ServicoTipoMaterial>();
		servicoTipoValorLocalidade = new ArrayList<ServicoTipoValorLocalidade>();

		idOrdemServicoLayout = null;
		indicadorDeslocamento = null;
		indicadorHorariosExecucao = null;
		indicadorCausaOcorrencia = null;
		indicadorRedeRamalAgua = null;
		indicadorRedeRamalEsgoto = null;
		indicadorMaterial = null;
		indicadorVala = null;
		indicadorOrdemSeletiva = null;
		indicadorServicoCritico = null;

	}

	public String getIndicadorFiscalizacaoInfracao(){

		return indicadorFiscalizacaoInfracao;
	}

	public void setIndicadorFiscalizacaoInfracao(String indicadorFiscalizacaoInfracao){

		this.indicadorFiscalizacaoInfracao = indicadorFiscalizacaoInfracao;
	}

	public String getIndicadorVistoria(){

		return indicadorVistoria;
	}

	public void setIndicadorVistoria(String indicadorVistoria){

		this.indicadorVistoria = indicadorVistoria;
	}

	public String getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(String valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public String getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(String percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public String getPrazoExecucao(){

		return prazoExecucao;
	}

	public void setPrazoExecucao(String prazoExecucao){

		this.prazoExecucao = prazoExecucao;
	}

	public String getTipoRemuneracao(){

		return tipoRemuneracao;
	}

	public void setTipoRemuneracao(String tipoRemuneracao){

		this.tipoRemuneracao = tipoRemuneracao;
	}

	public String getIdOrdemServicoLayout(){

		return idOrdemServicoLayout;
	}

	public void setIdOrdemServicoLayout(String idOrdemServicoLayout){

		this.idOrdemServicoLayout = idOrdemServicoLayout;
	}

	public String getIndicadorDeslocamento(){

		return indicadorDeslocamento;
	}

	public void setIndicadorDeslocamento(String indicadorDeslocamento){

		this.indicadorDeslocamento = indicadorDeslocamento;
	}

	public String getIndicadorHorariosExecucao(){

		return indicadorHorariosExecucao;
	}

	public void setIndicadorHorariosExecucao(String indicadorHorariosExecucao){

		this.indicadorHorariosExecucao = indicadorHorariosExecucao;
	}

	public String getIndicadorCausaOcorrencia(){

		return indicadorCausaOcorrencia;
	}

	public void setIndicadorCausaOcorrencia(String indicadorCausaOcorrencia){

		this.indicadorCausaOcorrencia = indicadorCausaOcorrencia;
	}

	public String getIndicadorRedeRamalAgua(){

		return indicadorRedeRamalAgua;
	}

	public void setIndicadorRedeRamalAgua(String indicadorRedeRamalAgua){

		this.indicadorRedeRamalAgua = indicadorRedeRamalAgua;
	}

	public String getIndicadorRedeRamalEsgoto(){

		return indicadorRedeRamalEsgoto;
	}

	public void setIndicadorRedeRamalEsgoto(String indicadorRedeRamalEsgoto){

		this.indicadorRedeRamalEsgoto = indicadorRedeRamalEsgoto;
	}

	public String getIndicadorMaterial(){

		return indicadorMaterial;
	}

	public void setIndicadorMaterial(String indicadorMaterial){

		this.indicadorMaterial = indicadorMaterial;
	}

	public String getIndicadorVala(){

		return indicadorVala;
	}

	public void setIndicadorVala(String indicadorVala){

		this.indicadorVala = indicadorVala;
	}

	public String getIndicadorOrdemSeletiva(){

		return indicadorOrdemSeletiva;
	}

	public void setIndicadorOrdemSeletiva(String indicadorOrdemSeletiva){

		this.indicadorOrdemSeletiva = indicadorOrdemSeletiva;
	}

	public String getIndicadorServicoCritico(){

		return indicadorServicoCritico;
	}

	public void setIndicadorServicoCritico(String indicadorServicoCritico){

		this.indicadorServicoCritico = indicadorServicoCritico;
	}

	public String getServicoTipoValorLocalidadeId(){

		return servicoTipoValorLocalidadeId;
	}

	public void setServicoTipoValorLocalidadeId(String servicoTipoValorLocalidadeId){

		this.servicoTipoValorLocalidadeId = servicoTipoValorLocalidadeId;
	}
}
