/*
 * Copyright (C) 2020 Asconn
 *
 * This file is part of CompiladorGameBattle.
 * CompiladorGameBattle is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * CompiladorGameBattle is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>
 */
package br.com.samuka.compiladorgamebattle.model;

import br.com.samuka.compiladorgamebattle.controller.PrincipalController;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class LanguageRules extends LanguageContansts {

    /**
     * Verifica se a string parâmetro é um símbolo.
     *
     * @param str String a ser verificada.
     * @return código do símbolo, ou -1 para um não símbolo.
     */
    public ItemToken isSimbol(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        String[] values = new String[SIMBOLS_CODE.length];

        int i = 0;
        for (int code : SIMBOLS_CODE) {
            values[i] = LMS_VALUE[code];
            i++;
        }

        i = 0;
        for (String simbol : values) {
            if (str.equals(simbol)) {
                retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
                retorno.setRow(new TableRow(SIMBOLS_CODE[i], LMS_VALUE[SIMBOLS_CODE[i]], LMS_DESCRIPTION[SIMBOLS_CODE[i]]));
            }
            i++;
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é uma palavra reservada.
     *
     * @param str String a ser verificada.
     * @return código da palavra reservada, ou -1 para uma palavra não
     * reservada.
     */
    public ItemToken isReservedWord(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        String[] values = new String[RESERVEDWORDS_CODE.length];

        int i = 0;
        for (int code : RESERVEDWORDS_CODE) {
            values[i] = LMS_VALUE[code];
            i++;
        }

        i = 0;
        for (String simbol : values) {
            if (str.equalsIgnoreCase(simbol)) {
                retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
                retorno.setRow(new TableRow(RESERVEDWORDS_CODE[i], LMS_VALUE[RESERVEDWORDS_CODE[i]], LMS_DESCRIPTION[RESERVEDWORDS_CODE[i]]));
            }
            i++;
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é um identificador.
     *
     * @param str String a ser verificada.
     * @return código do identificador, ou -1 para um não identificador.
     */
    public ItemToken isIndetifier(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        if (str.matches("[a-zA-Z]{1}[a-zA-Z_0-9]{0,29}")) {
            retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
            retorno.setRow(new TableRow(IDENTIFIER_CODE, str, LMS_DESCRIPTION[IDENTIFIER_CODE]));
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é um valor inteiro válido.
     *
     * @param str String a ser verificada.
     * @return código do valor inteiro, ou -1 para um inteiro inválido.
     */
    public ItemToken isIntNumber(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        try {
            int value = Integer.parseInt(str);

            if (Math.abs(value) < 32767) {
                retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
                retorno.setRow(new TableRow(INTEGER_CODE, str, LMS_DESCRIPTION[INTEGER_CODE]));
            } else {
                retorno.setDesOperacao("Número " + str + " inválido número máximo aceito é 32767");
            }

        } catch (NumberFormatException e) {
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é um literal válido.
     *
     * @param str String a ser verificada.
     * @return código do literal, ou -1 para um literal inválido.
     */
    public ItemToken isString(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        if (str.matches(STRING_SEPARATOR + ".{0,255}" + STRING_SEPARATOR)) {
            retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
            retorno.setRow(new TableRow(STRING_CODE, str, LMS_DESCRIPTION[STRING_CODE]));
        } else {
            retorno.setDesOperacao("Quantidade máxima de caracteres de 255 permitida");
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é um operador lógico.
     *
     * @param str String a ser verificada.
     * @return código do operador lógico, ou -1 para um operador nao lógico.
     */
    public ItemToken isLogicalOperator(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        String[] values = new String[LOGICAL_OPERATOR_CODE.length];

        int i = 0;
        for (int code : LOGICAL_OPERATOR_CODE) {
            values[i] = LMS_VALUE[code];
            i++;
        }

        i = 0;
        for (String simbol : values) {
            if (str.equals(simbol)) {
                retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
                retorno.setRow(new TableRow(LOGICAL_OPERATOR_CODE[i], LMS_VALUE[LOGICAL_OPERATOR_CODE[i]], LMS_DESCRIPTION[LOGICAL_OPERATOR_CODE[i]]));
            }
            i++;
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é um operador aritmético.
     *
     * @param str String a ser verificada.
     * @return código do operador aritmético, ou -1 para um operador não
     * aritmético.
     */
    public ItemToken isArithmeticOperator(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        String[] arithmeticOperators = new String[ARITHMETIC_OPERATOR_CODE.length];

        int i = 0;
        for (int code : ARITHMETIC_OPERATOR_CODE) {
            arithmeticOperators[i] = LMS_VALUE[code];
            i++;
        }

        i = 0;
        for (String simbol : arithmeticOperators) {
            if (str.equals(simbol)) {
                retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
                retorno.setRow(new TableRow(ARITHMETIC_OPERATOR_CODE[i], LMS_VALUE[ARITHMETIC_OPERATOR_CODE[i]], LMS_DESCRIPTION[ARITHMETIC_OPERATOR_CODE[i]]));
            }
            i++;
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é um operador relacional.
     *
     * @param str String a ser verificada.
     * @return código do operador relacional, ou -1 para um operador não
     * relacional.
     */
    public ItemToken isRelationOperator(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        String[] values = new String[RELATIONAL_OPERATOR_CODE.length];

        int i = 0;
        for (int code : RELATIONAL_OPERATOR_CODE) {
            values[i] = LMS_VALUE[code];
            i++;
        }

        i = 0;
        for (String simbol : values) {
            if (str.equals(simbol)) {
                retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
                retorno.setRow(new TableRow(RELATIONAL_OPERATOR_CODE[i], LMS_VALUE[RELATIONAL_OPERATOR_CODE[i]], LMS_DESCRIPTION[RELATIONAL_OPERATOR_CODE[i]]));
            }
            i++;
        }

        return retorno;
    }

    /**
     * Verifica se a string parâmetro é um literal válido.
     *
     * @param str String a ser verificada.
     * @return código do literal, ou -1 para um literal inválido.
     */
    public ItemToken isComment(String str) {

        ItemToken retorno = new ItemToken();
        retorno.setFlagOperacao(ItemToken.FLAG_ERROR);

        if ((COMMENT_SEPARATOR_1.contains(str) || str.contains(COMMENT_SEPARATOR_1)) || str.matches("[" + COMMENT_SEPARATOR_1 + "].[" + COMMENT_SEPARATOR_2 + "]")) {
            retorno.setFlagOperacao(ItemToken.FLAG_SUCCESS);
            retorno.setRow(new TableRow(COMMENT_CODE, str, LMS_DESCRIPTION[COMMENT_CODE]));
        }

        return retorno;
    }

    /**
     * Determina se código do token é terminal
     *
     * @param code Còdigo do token
     * @return true caso token seja terminal, false caso contrário.
     * @see
     */
    public boolean isTerminal(int code) {
        return code <= NUM_FIRST_NOM_TERMINAL && code >= NUM_FIRST_CODE;
    }

    /**
     * Determina se código do token é terminal
     *
     * @param code Còdigo do token
     * @return true caso token seja terminal, false caso contrário.
     * @see
     */
    public boolean isSemanticVerify(int code) {
        return code >= NUM_FIRST_SEMANTIC_VERIFY;
    }

    /**
     * Obtém o simbolo inicial e terminal.
     *
     * @return
     */
    public TableRow getStartEndToken() {
        return new TableRow(999, "$", "Inicio Fim");
    }

    /**
     * Busca uma regra de produção. Os não terminais iniciam em 46, ignorando o
     * token comentário.
     *
     * @param pilhaValue valor
     * @param enterValue
     * @return
     */
    public TableRow[] searchProduction(TableRow pilhaValue, TableRow enterValue) {

        int valueParseTable = PARSER_TABLE[Integer.parseInt(pilhaValue.getCodigo()) - NUM_FIRST_NOM_TERMINAL][enterValue.getCode() - 1];

        if (valueParseTable == -1) {

            if (PrincipalController.LR.isTerminal(enterValue.getCode())) {
                return new TableRow[]{new TableRow("0")};
            }

            return null;
        }

        int[] rule = RULES_PRODUCTIONS[valueParseTable];

        TableRow[] retorno = null;

        if (rule.length > 0) {

            retorno = new TableRow[rule.length];

            int i = rule.length - 1;
            for (int rul : rule) {

                String value = isTerminal(rul) ? LMS_VALUE[rul] : "N.T " + (rul - NUM_FIRST_NOM_TERMINAL);
                String descrition = isTerminal(rul) ? LMS_DESCRIPTION[rul] : "N.T " + (rul - NUM_FIRST_NOM_TERMINAL);

                retorno[i--] = new TableRow(rul, value, descrition);
            }
        }

        return retorno;

    }

    /**
     * Coloca os valores iniciais à pilha.
     *
     * @return Pilha já inicializada.
     */
    public Pilha getPilha() {

        Pilha pilha = new Pilha(new br.com.samuka.compiladorgamebattle.model.Node(getStartEndToken()));

        int[] rules = RULES_PRODUCTIONS[0];

        for (int i = rules.length - 1; i >= 0; i--) {

            String value = isTerminal(rules[i]) ? LMS_VALUE[rules[i]] : "N.T " + (rules[i] - NUM_FIRST_NOM_TERMINAL);
            String descrition = isTerminal(rules[i]) ? LMS_DESCRIPTION[rules[i]] : "N.T " + (rules[i] - NUM_FIRST_NOM_TERMINAL);

            pilha.stack(new Node(new TableRow(rules[i], value, descrition)));

        }

        return pilha;
    }

    /**
     * Obtém a descrição de erro sintático
     *
     * @param pilhaValue Valor no topo da pilha
     * @param enterValue Valor encontrado
     * @return
     */
    public String getSintaxError(TableRow pilhaValue, TableRow enterValue) {

        List<String> caracteres = new ArrayList<>();

        String retorno = "Erro sintático, era esperado ";

        if (Integer.parseInt(pilhaValue.getCodigo()) < NUM_FIRST_NOM_TERMINAL) {
            caracteres.add(PARSER_ERROR[Integer.parseInt(pilhaValue.getCodigo())]);
        } else {
            retorno = "Erro sintático";
        }

        if (Integer.parseInt(pilhaValue.getCodigo()) < NUM_FIRST_NOM_TERMINAL) {
            for (int i = 0; i < caracteres.size(); i++) {
                retorno += caracteres.get(i).replace("Era esperado ", "");
                if (i + 1 == caracteres.size()) {
                    //retorno += " ou ";
                } else {
                    retorno += ",";
                }
            }
        }

        retorno += " - Linha: " + pilhaValue.getLine();

        return retorno;

    }

}
