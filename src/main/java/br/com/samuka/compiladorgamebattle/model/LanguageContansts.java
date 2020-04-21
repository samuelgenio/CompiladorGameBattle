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

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class LanguageContansts {
    
    protected int NUM_FIRST_CODE = 2;
    public int NUM_FIRST_NOM_TERMINAL = 49;
    public int NUM_FIRST_SEMANTIC_VERIFY = 74;
    
    public int LITERAL_CODE = 23;
    
    int[] LMS_CODE = {
        0,1,
        2,3,4,5,6,
        7,8,9,10,11,
        12,13,14,15,16,
        17,18,19,20,21,
        22,LITERAL_CODE,24,25,26,
        27,28,29,30,31,
        32,33,34,35,36,
        37,38,39,40,41,
        42,43,44,45,46,
        47,48,49};
    
    String[] LMS_VALUE = {
        "","",
        "+","-","*","/","==",
        ">",">=","<","<=","<>",
        "=",";",":",",",".",
        "(",")", "{","}", "'", 
        //22,23,24,25,26
        "Identificador", "Inteiro","Literal", "CONTROLLER","ID",
        //27,28,29,30,31
        "_","IF","ELSE","WHILE","OR",
        //32,33,34,35,36
        "AND", "NOT","FOR","ahead","jump",
        //37,38,39,40,41
        "run", "attack","turn","getEnemyPos","getEnemyName",
        //42,43,44,45,46
        "getEnemyHealth", "getEnemyDirection", "getName", "getHealth", "getPos",
        //47,48,49
        "getDirection", "stop", "Comentário"
    };
    
    String[] LMS_DESCRIPTION = {
        "","",
        "Operador de adição","Operador de subtração","Operador de multiplicação","Operador de divisão","Operador relacional",
        "Operador relacional", "Operador relacional", "Operador relacional", "Operador relacional", "Operador relacional",
        "Atribuição","Ponto e vírgula","Dois Pontos","Vírgula","Ponto",
        "Parênteses","Parênteses","Chave", "Chaves", "aspas", 
        "Identificador", "Número inteiro","Literal","Palavra reservada","Palavra reservada",
        "Palavra reservada","Palavra reservada","Palavra reservada","Palavra reservada","Operador lógico",
        //32,33,34,35,36
        "Operador lógico", "Operador de negação", "Palavra reservada","Palavra reservada","Palavra reservada",
        //37,38,39,40,41
        "Palavra reservada","Palavra reservada","Palavra reservada","Palavra reservada","Palavra reservada",
        //32,43,44,45,46
        "Palavra reservada","Palavra reservada","Palavra reservada","Palavra reservada","Palavra reservada",
        //47,48,49
        "Palavra reservada", "Palavra reservada", "Comentário"
    };

    protected int[] ARITHMETIC_OPERATOR_CODE = new int[]{
        LMS_CODE[4], LMS_CODE[5], LMS_CODE[2], LMS_CODE[3]
    };
    
    protected int[] RELATIONAL_OPERATOR_CODE = new int[]{
        LMS_CODE[9], LMS_CODE[7], LMS_CODE[6], LMS_CODE[10], LMS_CODE[8], LMS_CODE[11]
    };
    
    protected int[] SIMBOLS_CODE = new int[]{
        LMS_CODE[15], LMS_CODE[14], LMS_CODE[16], LMS_CODE[13], LMS_CODE[12], 
        LMS_CODE[17], LMS_CODE[18], LMS_CODE[19], LMS_CODE[20], LMS_CODE[21]
    };

    protected int[] RESERVEDWORDS_CODE = new int[]{
        LMS_CODE[25], LMS_CODE[26], LMS_CODE[27], LMS_CODE[28], LMS_CODE[29], LMS_CODE[30],
        LMS_CODE[31], LMS_CODE[32], LMS_CODE[33], LMS_CODE[34], LMS_CODE[35], LMS_CODE[36], 
        LMS_CODE[37], LMS_CODE[38], LMS_CODE[39], LMS_CODE[40], LMS_CODE[41], LMS_CODE[42],
        LMS_CODE[43], LMS_CODE[44], LMS_CODE[45], LMS_CODE[46], LMS_CODE[47], LMS_CODE[48]
    };
    
    protected int[] LOGICAL_OPERATOR_CODE = new int[]{
        LMS_CODE[31], LMS_CODE[32], LMS_CODE[33]
    };
    
    protected int IDENTIFIER_CODE = LMS_CODE[22];
    
    protected int INTEGER_CODE = LMS_CODE[23];
    
    protected String STRING_SEPARATOR = "'";
    
    protected int STRING_CODE = LMS_CODE[24];
    
    protected String COMMENT_SEPARATOR_1 = "(*";
    
    protected String COMMENT_SEPARATOR_2 = "*)";
    
    public int COMMENT_CODE = LMS_CODE[49];
    
    int[][] PARSER_TABLE =
    {
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, -1, -1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1,  3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  6, -1, -1, -1, -1, -1,  5,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  7, -1,  8, -1, -1, -1, -1,  7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, -1,  9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, 18, 19, -1, 17, -1, -1, -1, -1, 20, 24, -1, 27, -1, -1, -1, 29, 56, 56, 56, 56, 56, 57, 57, 57, 57, 57, 57, 57, 57, 57 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, -1, -1, 22, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, 26, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 30, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, 30, 30, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, 30, 30, 30, 30, 30, 30, 30, 30, 30 },
        { -1, -1, -1, -1, -1, 32, 34, 35, 33, 36, 37, -1, 31, -1, 31, -1, -1, 31, 31, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 38, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, 40, 40, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1, -1, -1, 40, 40, 40, 40, 40, 40, 40, 40, 40 },
        { -1, 41, 42, -1, -1, 44, 44, 44, 44, 44, 44, -1, 44, -1, 44, -1, -1, 44, 44, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 43, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 45, -1, -1, -1, 45, 45, 45, -1, -1, -1, -1, -1, -1, -1, -1, -1, 45, -1, -1, -1, -1, -1, -1, 45, 45, 45, 45, 45, 45, 45, 45, 45 },
        { -1, 46, 46, 47, 48, 46, 46, 46, 46, 46, 46, -1, 46, -1, 46, -1, -1, 46, 46, 46, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 46, 49, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 52, -1, -1, -1, 51, 54, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, 53, -1, -1, -1, -1, -1, -1, 55, 55, 55, 55, 55, 55, 55, 55, 55 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 58, 59, 60, 61, 62, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 63, 64, 65, 66, 67, 68, 69, 70, 71 }
    };
    
    int[][] RULES_PRODUCTIONS = 
    {
        {  25,  22, 174,  19,  50,  20, 175 },
        {  53, 176,  56 },
        {  22, 178,  52 },
        {   0 },
        {  15,  22, 178,  52 },
        {  26, 181,  22, 178,  12,  55, 180,  13,  54 },
        {   0 },
        {   0 },
        {  22, 179,  12,  55, 180,  13,  54 },
        {  23 },
        {  21,  24,  21 },
        {  27,  22, 182,  57, 183,  58, 184,  56 },
        {   0 },
        {  17, 185,  51,  18 },
        {  19,  60,  59,  20 },
        {   0 },
        {  13,  60,  59 },
        {  22, 188,  12,  65, 189 },
        {  58 },
        {   0 },
        {  27,  22, 190,  61, 191 },
        {  17,  65, 192,  62,  18 },
        {   0 },
        {  15,  65, 192,  62 },
        {  28,  65, 194,  58,  63, 195 },
        {   0 },
        { 196,  29,  58 },
        {  30,  19, 197,  65, 198,  18,  58, 199 },
        {  22, 203 },
        {  34,  17,  22, 211,  12,  65, 212,  13,  65,  18, 213,  58, 214 },
        {  67,  66 },
        {   0 },
        {   6,  67, 215 },
        {   9,  67, 216 },
        {   7,  67, 217 },
        {   8,  67, 218 },
        {  10,  67, 219 },
        {  11,  67, 220 },
        {   2,  69,  68 },
        {   3,  69, 221,  68 },
        {  69,  68 },
        {   2,  69, 222,  68 },
        {   3,  69, 223,  68 },
        {  31,  69, 224,  68 },
        {   0 },
        {  71,  70 },
        {   0 },
        {   4,  71, 225,  70 },
        {   5,  71, 226,  70 },
        {  32,  71, 227,  70 },
        {  23, 228 },
        {  21,  24,  21, 228 },
        {  17,  65,  18 },
        {  33,  71, 229 },
        { 230,  64 },
        { 230,  73, 190,  17,  18, 191 },
        {  72, 190,  17,  65, 192,  18, 191 },
        {  73, 190,  17,  18, 191 },
        {  35 },
        {  36 },
        {  37 },
        {  38 },
        {  39 },
        {  40 },
        {  41 },
        {  42 },
        {  43 },
        {  44 },
        {  45 },
        {  46 },
        {  47 },
        {  48 }
    };
    
    String[] PARSER_ERROR =
    {
        "",
        "Era esperado fim de programa",
        "Era esperado " + LMS_VALUE[2],
        "Era esperado " + LMS_VALUE[3],
        "Era esperado " + LMS_VALUE[4],
        "Era esperado " + LMS_VALUE[5],
        "Era esperado " + LMS_VALUE[6],
        "Era esperado " + LMS_VALUE[7],
        "Era esperado " + LMS_VALUE[8],
        "Era esperado " + LMS_VALUE[9],
        "Era esperado " + LMS_VALUE[10],
        "Era esperado " + LMS_VALUE[11],
        "Era esperado " + LMS_VALUE[12],
        "Era esperado " + LMS_VALUE[13],
        "Era esperado " + LMS_VALUE[14],
        "Era esperado " + LMS_VALUE[15],
        "Era esperado " + LMS_VALUE[16],
        "Era esperado " + LMS_VALUE[17],
        "Era esperado " + LMS_VALUE[18],
        "Era esperado " + LMS_VALUE[19],
        "Era esperado " + LMS_VALUE[20],
        "Era esperado " + LMS_VALUE[21],
        "Era esperado " + LMS_VALUE[22],
        "Era esperado " + LMS_VALUE[23],
        "Era esperado " + LMS_VALUE[24],
        "Era esperado " + LMS_VALUE[25],
        "Era esperado " + LMS_VALUE[26],
        "Era esperado " + LMS_VALUE[27],
        "Era esperado " + LMS_VALUE[28],
        "Era esperado " + LMS_VALUE[29],
        "Era esperado " + LMS_VALUE[30],
        "Era esperado " + LMS_VALUE[31],
        "Era esperado " + LMS_VALUE[32],
        "Era esperado " + LMS_VALUE[33],
        "Era esperado " + LMS_VALUE[34],
        "Era esperado " + LMS_VALUE[35],
        "Era esperado " + LMS_VALUE[36],
        "Era esperado " + LMS_VALUE[37],
        "Era esperado " + LMS_VALUE[38],
        "Era esperado " + LMS_VALUE[39],
        "Era esperado " + LMS_VALUE[40],
        "Era esperado " + LMS_VALUE[41],
        "<PROGRAMA> inválido",
        "<BLOCO> inválido",
        "<LID> inválido",
        "<REPIDENT> inválido",
        "<DCLVAR> inválido",
        "<LDVAR> inválido",
        "<LTVAR> inválido",
        "<DCLEVENT> inválido",
        "<EVEPAR> inválido",
        "<BODY> inválido",
        "<REPCOMANDO> inválido",
        "<COMANDO> inválido",
        "<PARAMETROS> inválido",
        "<REPPAR> inválido",
        "<ELSEPARTE> inválido",
        "<VARIAVEL> inválido",
        "<EXPRESSAO> inválido",
        "<REPEXPSIMP> inválido",
        "<EXPSIMP> inválido",
        "<REPEXP> inválido",
        "<TERMO> inválido",
        "<REPTERMO> inválido",
        "<FATOR> inválido",
        "<ACTNAME> inválido",
        "<ACTNAMEMET> inválido"
    };
    
}
