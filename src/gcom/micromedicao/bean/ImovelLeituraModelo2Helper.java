package gcom.micromedicao.bean;

import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.conta.Conta;


public class ImovelLeituraModelo2Helper {

	private Imovel imovel;

	private Short indicadorAtendeCriterioFaturamento;

	private Conta contaGeradaReferenciaAnterior;

	private Integer idMedicaoTipo;

	private HidrometroArquivoLeituraModelo2Helper hidrometroAgua;

	private HidrometroArquivoLeituraModelo2Helper hidrometroPoco;

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public Short getIndicadorAtendeCriterioFaturamento(){

		return indicadorAtendeCriterioFaturamento;
	}

	public void setIndicadorAtendeCriterioFaturamento(Short indicadorAtendeCriterioFaturamento){

		this.indicadorAtendeCriterioFaturamento = indicadorAtendeCriterioFaturamento;
	}

	public Conta getContaGeradaReferenciaAnterior(){

		return contaGeradaReferenciaAnterior;
	}

	public void setContaGeradaReferenciaAnterior(Conta contaGeradaReferenciaAnterior){

		this.contaGeradaReferenciaAnterior = contaGeradaReferenciaAnterior;
	}

	public Integer getIdMedicaoTipo(){

		return idMedicaoTipo;
	}

	public void setIdMedicaoTipo(Integer idMedicaoTipo){

		this.idMedicaoTipo = idMedicaoTipo;
	}

	public HidrometroArquivoLeituraModelo2Helper getHidrometroAgua(){

		return hidrometroAgua;
	}

	public void setHidrometroAgua(HidrometroArquivoLeituraModelo2Helper hidrometroAgua){

		this.hidrometroAgua = hidrometroAgua;
	}

	public HidrometroArquivoLeituraModelo2Helper getHidrometroPoco(){

		return hidrometroPoco;
	}

	public void setHidrometroPoco(HidrometroArquivoLeituraModelo2Helper hidrometroPoco){

		this.hidrometroPoco = hidrometroPoco;
	}



}
