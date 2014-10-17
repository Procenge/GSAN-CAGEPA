
package gcom.util.parametrizacao;

public class ParametroGeral
				extends Parametro {

	public static final Parametro PARAMETRO_CALCULO_DV = new ParametroGeral("PARAMETRO_CALCULO_DV");

	public static final Parametro P_USUARIO_BATCH = new ParametroGeral("P_USUARIO_BATCH");

	public static final Parametro P_PORTA_SERVIDOR_SMTP = new ParametroGeral("P_PORTA_SERVIDOR_SMTP");

	public static final Parametro P_USUARIO_CONEXAO_SMTP = new ParametroGeral("P_USUARIO_CONEXAO_SMTP");

	public static final Parametro P_SENHA_CONEXAO_SMTP = new ParametroGeral("P_SENHA_CONEXAO_SMTP");

	public static final Parametro P_INSCRICAO_ESTADUAL_EMPRESA = new ParametroGeral("P_INSCRICAO_ESTADUAL_EMPRESA");

	public static final Parametro P_NOME_ESTADO_ABREVIADO_EMPRESA = new ParametroGeral("P_NOME_ESTADO_ABREVIADO_EMPRESA");

	public static final Parametro P_JUNTA_COMERCIAL_EMPRESA = new ParametroGeral("P_JUNTA_COMERCIAL_EMPRESA");

	public static final Parametro P_TIPO_BANCO_DE_DADOS = new ParametroGeral("P_TIPO_BANCO_DE_DADOS");

	private ParametroGeral(String parametro) {

		super(parametro);
	}
}
