-- grupo funcionalidade operacao
DELETE FROM GRUPO_FUNCIONALIDADE_OPERACAO WHERE FNCD_ID IN (1096, 1097, 2105, 2104);
--tabela linha coluna operacao
DELETE FROM TABELA_LINHA_COLUNA_ALTERACAO WHERE TBLA_ID IN (SELECT TBLA_ID FROM TABELA_LINHA_ALTERACAO WHERE TREF_ID IN (SELECT OPEF_ID FROM OPERACAO_EFETUADA WHERE OPER_ID IN (SELECT OPER_ID FROM OPERACAO WHERE FNCD_ID IN (1096, 1097, 2105, 2104))));
--tabela linha operacao
DELETE FROM TABELA_LINHA_ALTERACAO WHERE TREF_ID IN (SELECT OPEF_ID FROM OPERACAO_EFETUADA WHERE OPER_ID IN (SELECT OPER_ID FROM OPERACAO WHERE FNCD_ID IN (1096, 1097, 2105, 2104)));
-- usuario alteracao
DELETE FROM USUARIO_ALTERACAO WHERE TREF_ID IN (SELECT OPEF_ID FROM OPERACAO_EFETUADA WHERE OPER_ID IN (SELECT OPER_ID FROM OPERACAO WHERE FNCD_ID IN (1096, 1097, 2105, 2104)));
--operacao efetuada
DELETE FROM OPERACAO_EFETUADA WHERE OPER_ID IN (SELECT OPER_ID FROM OPERACAO WHERE FNCD_ID IN (1096, 1097, 2105, 2104));
--operacao tabela
DELETE FROM OPERACAO_TABELA WHERE OPER_ID IN (SELECT OPER_ID FROM OPERACAO WHERE FNCD_ID IN (1096, 1097, 2105, 2104));
--operacao
DELETE FROM OPERACAO WHERE FNCD_ID IN (1096, 1097, 2105, 2104);
--funcionalidades
DELETE FROM FUNCIONALIDADE WHERE FNCD_ID IN (1096, 1097, 2105, 2104);
--grupo funcionalidade operacao
DELETE FROM GRUPO_FUNCIONALIDADE_OPERACAO WHERE FNCD_ID IN (2104, 2105, 1096, 1097);