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
public enum EnumInstruction {

    INS_RETU(1, "RETU", "Retorno de procedure"),
    INS_CRVL(2, "CRVL", "Carrega valor na pilha"),
    INS_CRCT(3, "CRCT", "Carrega constante na pilha"),
    INS_ARMZ(4, "ARMZ", "Armazena conteúdo da pilha(topo) no endereço dado"),
    INS_SOMA(5, "SOMA", "Operação soma com elementos do topo e sub-topo"),
    INS_SUBT(6, "SUBT", "Operação de subtração"),
    INS_MULT(7, "MULT", "Operação de multiplicação"),
    INS_DIVI(8, "DIVI", "Operação de divisão"),
    INS_INVR(9, "INVR", "Inverte sinal."),
    INS_NEGA(10, "NEGA", "Operação de negação"),
    INS_CONJ(11, "CONJ", "Operação AND"),
    INS_DISJ(12, "DISJ", "Operação de OR"),
    INS_CMME(13, "CMME", "Compara menor"),
    INS_CMMA(14, "CMMA", "Compara maior"),
    INS_CMIG(15, "CMIG", "Compara igual"),
    INS_CMDF(16, "CMDF", "compara diferente"),
    INS_CMEI(17, "CMEI", "compara menor igual"),
    INS_CMAI(18, "CMAI", "compara maior igual"),
    INS_DSVS(19, "DSVS", "Desviar sempre"),
    INS_DSVF(20, "DSVF", "Desviar se falso"),
    INS_LEIT(21, "LEIT", "Leitura."),
    INS_IMPR(22, "IMPR", "Imprimir topo da pilha"),
    INS_IMPRL(23, "IMPRL", "Imprimir literal extraído da área de literais"),
    INS_AMEM(24, "AMEM", "Alocar espaço na área de dados"),
    INS_CALL(25, "CALL", "Chamada de procedure"),
    INS_PARA(26, "PARA", "Finaliza a execução"),
    INS_NADA(27, "NADA", "Nada faz, continua a execução"),
    INS_COPI(28, "COPI", "Duplica o topo da pilha"),
    INS_DSVT(29, "DSVT", "Desvia se verdadeiro"),
    INS_AHEA(30, "AHEA", "Move em frente"),
    INS_JUMP(31, "JUMP", "Pula"),
    INS_RUN(32, "RUN", "Correr"),
    INS_ATTA(33, "ATTA", "Atacar"),
    INS_TURN(34, "TURN", "Altera posição do personagem"),
    INS_GEENPOS(35, "GEENPOS", "Obtém a posição do inimigo"),
    INS_GEENNAM(36, "GEENNAM", "Obtém nome do inimigo"),
    INS_GEENHEA(37, "GEENHEA", "Obtém vida do inimigo"),
    INS_GEENDIR(38, "GEENDIR", "Obtém direção do inimigo"),
    INS_GENAM(39, "GENAM", "Obtém nome"),
    INS_GEHEA(40, "GEHEA", "Obtém vida"),
    INS_GEPOS(41, "GEPOS", "Obtém a posição"), 
    INS_GEDIR(42, "GEDIR", "Obtém direção"),
    INS_STOP(43, "STOP", "para de movimentar");
    
    private final int code;
    
    private final String name;
    
    private final String description;

    private EnumInstruction(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    public static EnumInstruction get(int code) {
    
        for (EnumInstruction value : EnumInstruction.values()) {
            if (value.getCode() == code)
                return value;
        }
    
        return null;
        
    }
    
    
}
