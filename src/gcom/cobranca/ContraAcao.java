
package gcom.cobranca;

import java.util.Date;

import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.SupressaoMotivo;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

public class ContraAcao
				extends ObjetoTransacao {

	private Integer id;

	private AcaoCobrancaEfeito acaoCobrancaEfeito;

	private LigacaoAguaSituacao ligacaoAguaSituacao;

	private LigacaoAguaDiametro ligacaoAguaDiametro;

	private LigacaoAguaMaterial ligacaoAguaMaterial;

	private CorteTipo corteTipo;

	private MotivoCorte motivoCorte;

	private SupressaoTipo supressaoTipo;

	private SupressaoMotivo supressaoMotivo;

	private ServicoTipo servicoTipoAcao;

	private ServicoTipo servicoTipoContraAcao;

	private UnidadeOrganizacional unidadeOrganizacional;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	public final static int CONTRA_ACAO_EFETUAR_PARCELAMENTO = 1;

	public final static int CONTRA_ACAO_PAGAMENTO_ENTRADA_PARCELAMENTO = 2;

	private static final long serialVersionUID = 1L;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public AcaoCobrancaEfeito getAcaoCobrancaEfeito(){

		return acaoCobrancaEfeito;
	}

	public void setAcaoCobrancaEfeito(AcaoCobrancaEfeito acaoCobrancaEfeito){

		this.acaoCobrancaEfeito = acaoCobrancaEfeito;
	}

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public LigacaoAguaDiametro getLigacaoAguaDiametro(){

		return ligacaoAguaDiametro;
	}

	public void setLigacaoAguaDiametro(LigacaoAguaDiametro ligacaoAguaDiametro){

		this.ligacaoAguaDiametro = ligacaoAguaDiametro;
	}

	public LigacaoAguaMaterial getLigacaoAguaMaterial(){

		return ligacaoAguaMaterial;
	}

	public void setLigacaoAguaMaterial(LigacaoAguaMaterial ligacaoAguaMaterial){

		this.ligacaoAguaMaterial = ligacaoAguaMaterial;
	}

	public CorteTipo getCorteTipo(){

		return corteTipo;
	}

	public void setCorteTipo(CorteTipo corteTipo){

		this.corteTipo = corteTipo;
	}

	public MotivoCorte getMotivoCorte(){

		return motivoCorte;
	}

	public void setMotivoCorte(MotivoCorte motivoCorte){

		this.motivoCorte = motivoCorte;
	}

	public SupressaoTipo getSupressaoTipo(){

		return supressaoTipo;
	}

	public void setSupressaoTipo(SupressaoTipo supressaoTipo){

		this.supressaoTipo = supressaoTipo;
	}

	public SupressaoMotivo getSupressaoMotivo(){

		return supressaoMotivo;
	}

	public void setSupressaoMotivo(SupressaoMotivo supressaoMotivo){

		this.supressaoMotivo = supressaoMotivo;
	}

	public ServicoTipo getServicoTipoAcao(){

		return servicoTipoAcao;
	}

	public void setServicoTipoAcao(ServicoTipo servicoTipoAcao){

		this.servicoTipoAcao = servicoTipoAcao;
	}

	public ServicoTipo getServicoTipoContraAcao(){

		return servicoTipoContraAcao;
	}

	public void setServicoTipoContraAcao(ServicoTipo servicoTipoContraAcao){

		this.servicoTipoContraAcao = servicoTipoContraAcao;
	}

	public UnidadeOrganizacional getUnidadeOrganizacional(){

		return unidadeOrganizacional;
	}

	public void setUnidadeOrganizacional(UnidadeOrganizacional unidade){

		this.unidadeOrganizacional = unidade;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return new String[0];
	}

}
