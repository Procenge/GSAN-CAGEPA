INSERT INTO PARAMETRO_SISTEMA (PASI_ID, PASI_CDPARAMETRO, PASI_VLPARAMETRO, PASI_DSPARAMETRO, PASI_NMCLASSEENTIDADE, PASI_CDTIPOPARAMETRO, PASI_NNVERSAO, PASI_ICUSO, PASI_TMULTIMAALTERACAO)
  select 10260 ,'P_NOME_EMPRESA_RELATORIO', 'COMPANHIA DE SANEAMENTO',
 'Indica o nome do relatorio a ser exibido.',
  NULL, 1, 0, 1, CURRENT_TIMESTAMP 
FROM DUAL
WHERE NOT EXISTS (SELECT 1
                  FROM PARAMETRO_SISTEMA
                  WHERE PASI_CDPARAMETRO = 'P_NOME_EMPRESA_RELATORIO');
                  
UPDATE PARAMETRO_SISTEMA 
SET PASI_VLPARAMETRO = 'COMPANHIA DE �GUA E ESGOTOS'
WHERE PASI_CDPARAMETRO = 'P_NOME_EMPRESA_RELATORIO'
  AND EXISTS (SELECT 1
              FROM SISTEMA_PARAMETROS
              WHERE PARM_NMABREVIADOEMPRESA = 'CAGEPA');
