
package gcom.atendimentopublico.ordemservico;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.IoUtil;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.sql.Blob;
import java.util.Date;

import org.hibernate.Hibernate;

public class OrdemServicoFotoOcorrencia
				extends ObjetoTransacao {

	private Integer id;

	private Integer idOrdemServico;

	private Integer idOrdemServicoProgramacao;

	private Integer numeroSequenciaFoto;

	private byte[] foto;

	private Blob fotoBlob;

	private Date ultimaAlteracao;

	@Override
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroOrdemServicoFotoOcorrencia filtroOrdemServicoFotoOcorrencia = new FiltroOrdemServicoFotoOcorrencia();
		filtroOrdemServicoFotoOcorrencia.adicionarCaminhoParaCarregamentoEntidade("numeroSequenciaFoto");
		filtroOrdemServicoFotoOcorrencia.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
		filtroOrdemServicoFotoOcorrencia.adicionarCaminhoParaCarregamentoEntidade("ordemServicoProgramacao");
		filtroOrdemServicoFotoOcorrencia.adicionarParametro(new ParametroSimples(FiltroOrdemServicoFotoOcorrencia.ID_OS, this
						.getIdOrdemServico()));
		filtroOrdemServicoFotoOcorrencia.adicionarParametro(new ParametroSimples(FiltroOrdemServicoFotoOcorrencia.ID_OS_PROGRAMACAO, this
						.getIdOrdemServicoProgramacao()));
		filtroOrdemServicoFotoOcorrencia.adicionarParametro(new ParametroSimples(FiltroOrdemServicoFotoOcorrencia.ID_SEQUENCIA, this
						.getNumeroSequenciaFoto()));

		return filtroOrdemServicoFotoOcorrencia;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public byte[] getFoto(){

		return this.foto;
	}

	public void setFoto(byte[] foto){

		this.foto = foto;
		this.popularFotoBlob();
	}

	public byte[] getFotoBlob(){

		return this.foto;
	}

	public void setFotoBlob(Blob fotoBlob){

		this.foto = IoUtil.toByteArray(fotoBlob);
	}

	public void setFotoBlob(byte[] fotoBlob){

		this.foto = fotoBlob;
	}

	private void popularFotoBlob(){

		if(getFotoBlob() != null){
			this.fotoBlob = Hibernate.createBlob(this.getFotoBlob());
		}else{
			this.fotoBlob = null;
		}
	}

	public Integer getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public Integer getIdOrdemServicoProgramacao(){

		return idOrdemServicoProgramacao;
	}

	public void setIdOrdemServicoProgramacao(Integer idOrdemServicoProgramacao){

		this.idOrdemServicoProgramacao = idOrdemServicoProgramacao;
	}

	public Integer getNumeroSequenciaFoto(){

		return numeroSequenciaFoto;
	}

	public void setNumeroSequenciaFoto(Integer numeroSequenciaFoto){

		this.numeroSequenciaFoto = numeroSequenciaFoto;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		return null; // TODO LEMBRAR DE IMPLEMENTAR
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}
}
