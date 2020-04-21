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
import br.com.samuka.compiladorgamebattle.model.Exception.SemanticException;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Semantic {

    private final String CONTEXT_READLN = "READLN";
    private final String CONTEXT_EXPRESSAO = "EXPRESSAO";

    private final String MAIN_METHOD = "onAlive";

    private final int DEFAULT_DESLOCATION = 3;

    /*
        Área de literais já inclusa na máquina.
     */
    public HypotheticalMachine machine;

    /*
     * Pilhas IF, WHILE, REPEAT, PROCEDURE, CASE, FOR.
     */
    private Pilha stackIF, stackWHILE, stackREPEAT, stackPROCEDURE, stackCASE, stackFOR;

    private Pilha stackPARAMETRO;

    private TableSymbol tableSymbols;

    /**
     * Nível atual.
     */
    private int currentLevel;

    /**
     * Valor de deslocamento em relação a base.
     */
    private int currentDeslocation = DEFAULT_DESLOCATION;

    /**
     * Tipo do identificador.
     */
    private String identifierType;

    /**
     * Nome do identificador.
     */
    private String identifierName;

    /**
     * Nome do comando.
     */
    private String identifierContext;

    /**
     * Quantidade de parâmetros.
     */
    private int qtdPar;

    /**
     * Quantidade de parâmetros efetivos.
     */
    private int qtdParEfe;

    /**
     * Armazena o último index de inserção na tabela de simbolos.
     */
    private int lastTableSymbolIndex;

    /**
     * Armazena o último index do identificador da esquerda.
     */
    private int lastTableSymbolIndexLeft;

    private int lastTableSymbolLevel;

    private final LanguageRules languageRules = new LanguageRules();

    /**
     * Executa ação semantica.
     *
     * @param row Token com ação semântica
     * @param semanticValue Valor utilizado na ação semântica.
     * @return Mensagem de erro, vazio caso processo ocorra normalmente.
     * @throws
     * br.com.samuka.compiladorgamebattle.model.Exception.SemanticException
     */
    public String action(TableRow row, String semanticValue) throws SemanticException {

        String messageReturn = "";

        Object[] value = null;
        Node node = null;
        int index = -1;
        int pos = 0;

        int code = Integer.parseInt(row.getCodigo()) - PrincipalController.LR.NUM_FIRST_SEMANTIC_VERIFY;

        switch (code) {

            case 100://Reconhecendo o nome do programa.

                action100();

                break;
            case 101://Final do programa.
                tableSymbols.showData();

                int indexMainMethod = tableSymbols.getIndex(TableSymbol.IND_PRO, 0, MAIN_METHOD);

                if (indexMainMethod == -1) {
                    throw new SemanticException("Método onAlive não encontrado");
                }

                value = (Object[]) tableSymbols.get(indexMainMethod);

                machine.addInstruction(EnumInstruction.INS_DSVS.getCode(), Integer.MIN_VALUE, Integer.parseInt(value[1].toString()));

                machine.addInstruction(EnumInstruction.INS_PARA.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                index = tableSymbols.getIndex(TableSymbol.IND_ROL, currentLevel, TableSymbol.IND_ROL);

                if (index != -1) {
                    throw new SemanticException("Rótulo(s) ainda existentes, Linha: " + row.getLine(), row.getPos());
                }

                break;
            case 102://Após declaração de variável.

                machine.addInstruction(EnumInstruction.INS_AMEM.getCode(), Integer.MIN_VALUE, currentDeslocation);

                break;
            case 103://Após a palavra

                identifierType = TableSymbol.IND_ROL;

                break;
            case 104://Encontrado o nome de rótulo, de variável, ou de parâmetro de procedure em declaração

                switch (identifierType) {
                    case TableSymbol.IND_ROL:

                        if (tableSymbols.find(TableSymbol.IND_ROL, currentLevel, TableSymbol.IND_ROL) != null) {
                            throw new SemanticException("Rótulo já incluído, Linha: " + row.getLine(), row.getPos());
                        } else {
                            try {
                                tableSymbols.insert(TableSymbol.IND_ROL, currentLevel, TableSymbol.IND_ROL, "0", "");
                            } catch (Exception ex) {
                                throw new SemanticException("Erro na inserção na tabela de símbolos. Registro[" + TableSymbol.IND_ROL + "], Linha: " + row.getLine(), row.getPos());
                            }
                        }

                        break;
                    case TableSymbol.IND_VAR:

                        if (tableSymbols.find(TableSymbol.IND_VAR, currentLevel, semanticValue, false) != null) {
                            throw new SemanticException("Váriavel " + semanticValue + " Duplicada, Linha: " + row.getLine(), row.getPos());
                        } else {
                            try {
                                lastTableSymbolIndex = tableSymbols.insert(TableSymbol.IND_VAR, currentLevel, semanticValue, currentDeslocation, "");
                                currentDeslocation++;
                            } catch (Exception ex) {
                                throw new SemanticException("Erro na inserção na tabela de símbolos. Registro[" + semanticValue + "], Linha: " + row.getLine(), row.getPos());
                            }
                        }

                        break;
                    case TableSymbol.IND_PAR:

                        if (tableSymbols.find(TableSymbol.IND_PAR, currentLevel, semanticValue, false) != null) {
                            throw new SemanticException("Parâmetro " + semanticValue + " Duplicado, Linha: " + row.getLine(), row.getPos());
                        } else {

                            if (stackPARAMETRO == null) {
                                stackPARAMETRO = new Pilha();
                            }

                            try {
                                index = tableSymbols.insert(TableSymbol.IND_PAR, currentLevel, semanticValue, currentDeslocation, "");
                                tableSymbols.get(index);
                                //Facilita para a ação #109 armazenando os parâmetros.
                                stackPARAMETRO.stack(new Node(new TableRow(String.valueOf(index), "", "")));

                                qtdPar++;
                            } catch (Exception ex) {
                                throw new SemanticException("Erro na inserção na tabela de símbolos. Registro[" + semanticValue + "], Linha: " + row.getLine(), row.getPos());
                            }
                        }

                        break;
                }

                break;

            case 105://Reconhecido nome de constante em declaração

                if (tableSymbols.find(TableSymbol.IND_VAR, currentLevel, semanticValue, false) != null) {
                    throw new SemanticException("Constante " + semanticValue + " Duplicada, Linha: " + row.getLine(), row.getPos());
                } else {
                    try {
                        lastTableSymbolIndex = tableSymbols.insert(TableSymbol.IND_VAR, currentLevel, semanticValue, currentDeslocation, "");
                        currentDeslocation++;
                    } catch (Exception ex) {
                        throw new SemanticException("Erro na inserção na tabela de símbolos. Registro[" + semanticValue + "], Linha: " + row.getLine(), row.getPos());
                    }
                }

                break;

            case 106://Reconhecido valor de constante em declaração
                tableSymbols.showData();
                try {
                    tableSymbols.update(lastTableSymbolIndex, false, false, semanticValue);
                } catch (Exception ex) {
                    throw new SemanticException("Erro na atualização na tabela de símbolos. Registro[" + semanticValue + "], Linha: " + row.getLine(), row.getPos());
                }
                //tableSymbols.showData();
                break;

            case 107://Antes de lista de identificadores em declaração de variáveis

                identifierType = TableSymbol.IND_VAR;

                break;

            case 108://Após nome de procedure, em declaração

                try {

                    lastTableSymbolIndex = tableSymbols.insert(TableSymbol.IND_PRO, currentLevel, semanticValue, machine.inst.LC + 1, "0");

                    qtdPar = 0;
                    currentDeslocation = DEFAULT_DESLOCATION;
                    currentLevel++;
                } catch (Exception ex) {
                    throw new SemanticException("Erro na inserção na tabela de símbolos. Registro[" + semanticValue + "], Linha: " + row.getLine(), row.getPos());
                }

                break;

            case 109://Após declaração de procedure

                if (qtdPar > 0) {

                    try {
                        tableSymbols.update(lastTableSymbolIndex, false, true, qtdPar);

                        node = null;
                        int qtdParamentros = 1;
                        //Percorre os parâmetros
                        while ((node = stackPARAMETRO.unstack()) != null) {
                            tableSymbols.update(Integer.parseInt(node.getRow().getCodigo()), true, false, Math.negateExact(qtdParamentros), "");
                            qtdParamentros++;
                        }

                    } catch (NumberFormatException ex) {
                        throw new SemanticException("Erro na atualização na tabela de símbolos. Registro[" + semanticValue + "], Linha: " + row.getLine(), row.getPos());
                    }

                }

                machine.addInstruction(EnumInstruction.INS_DSVS.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                if (stackPROCEDURE == null) {
                    stackPROCEDURE = new Pilha();
                }

                stackPROCEDURE.stack(new Node(new TableRow(String.valueOf(machine.inst.LC - 1), String.valueOf(qtdPar), "")));

                break;

            case 110://Fim de procedure

                node = stackPROCEDURE.unstack();
                tableSymbols.showData();
                machine.addInstruction(EnumInstruction.INS_RETU.getCode(), Integer.MIN_VALUE, Integer.parseInt(node.getRow().getToken()));

                if (tableSymbols.find(TableSymbol.IND_ROL, currentLevel, TableSymbol.IND_ROL) != null) {
                    throw new SemanticException("Rótulo já incluído, Linha: " + row.getLine(), row.getPos());
                }

                machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC);

                currentLevel--;

                break;

            case 111://Antes de parâmetros formais de procedures
                identifierType = TableSymbol.IND_PAR;
                break;
            case 112://Identificador de instrução rotulada ou comando de atribuição
                identifierName = row.getToken();
                break;
            case 113://Instrução rotulada

                if (tableSymbols.find(TableSymbol.IND_ROL, currentLevel, identifierName) == null) {
                    /*
                    Não implementado
                     */
                } else {
                    throw new SemanticException("Rótulo inexistente, Linha: " + row.getLine(), row.getPos());
                }

                break;
            case 114://Atribuição parte esquerda

                index = tableSymbols.getIndex(TableSymbol.IND_VAR, currentLevel, semanticValue);

                if (index != -1) {
                    lastTableSymbolIndex = lastTableSymbolIndexLeft = index;
                } else {
                    throw new SemanticException("Identificador " + semanticValue + " não encontrado, Linha: " + row.getLine(), row.getPos());
                }

                break;
            case 115://Após expressão em atribuição

                value = (Object[]) tableSymbols.get(lastTableSymbolIndexLeft);

                pos = Math.abs(currentLevel - (int) value[3]);

                machine.addInstruction(EnumInstruction.INS_ARMZ.getCode(), pos, Integer.parseInt(value[1].toString()));

                break;
            case 116://Chamada de procedure
                tableSymbols.showData();

                index = tableSymbols.getIndex(TableSymbol.IND_PRO, currentLevel, semanticValue);

                /*
                    Pode ser que a procedure não tenha sido lida ainda mas exista no escopo.
                    Armazena ela na lista de simbolos para utilização mais tarde.
                 */
                if (index != -1) {
                    lastTableSymbolIndex = index;
                    qtdParEfe = 0;
                } else {
                    throw new SemanticException("Procedure " + semanticValue + " não encontrada, Linha: " + row.getLine(), row.getPos());
                }

                break;
            case 117://Após comando call

                value = (Object[]) tableSymbols.get(lastTableSymbolIndex);

                ItemToken itemReserved = languageRules.isReservedWord(value[0].toString());

                int qtdParReal = qtdParEfe - 1;

                int codeInst = EnumInstruction.INS_CALL.getCode();

                if (itemReserved != null
                        && itemReserved.getRow() != null
                        && itemReserved.getRow().getCode() >= 35
                        && itemReserved.getRow().getCode() <= 48) {

                    switch (itemReserved.getRow().getCode()) {
                        case 35:
                            codeInst = EnumInstruction.INS_AHEA.getCode();
                            break;
                        case 36:
                            codeInst = EnumInstruction.INS_JUMP.getCode();
                            break;
                        case 37:
                            codeInst = EnumInstruction.INS_RUN.getCode();
                            break;
                        case 38:
                            codeInst = EnumInstruction.INS_ATTA.getCode();
                            break;
                        case 39:
                            codeInst = EnumInstruction.INS_TURN.getCode();
                            break;
                        case 40:
                            codeInst = EnumInstruction.INS_GEENPOS.getCode();
                            break;
                        case 41:
                            codeInst = EnumInstruction.INS_GEENNAM.getCode();
                            break;
                        case 42:
                            codeInst = EnumInstruction.INS_GEENHEA.getCode();
                            break;
                        case 43:
                            codeInst = EnumInstruction.INS_GEENDIR.getCode();
                            break;
                        case 44:
                            codeInst = EnumInstruction.INS_GENAM.getCode();
                            break;
                        case 45:
                            codeInst = EnumInstruction.INS_GEHEA.getCode();
                            break;
                        case 46:
                            codeInst = EnumInstruction.INS_GEPOS.getCode();
                            break;
                        case 47:
                            codeInst = EnumInstruction.INS_GEDIR.getCode();
                            break;
                        case 48:
                            codeInst = EnumInstruction.INS_STOP.getCode();
                            break;
                    }
                    qtdParReal = qtdParEfe;
                }

                if (Integer.parseInt(value[2].toString()) == qtdParReal) {

                    pos = Math.abs(currentLevel - (int) value[3]);

                    machine.addInstruction(codeInst, pos, Integer.parseInt(value[1].toString()));
                } else {
                    throw new SemanticException("Quantidade de parâmetros diferente da quantidade esperada, Linha: " + row.getLine(), row.getPos());
                }

                break;
            case 118://Após expressão, em comando call
                qtdParEfe++;
                break;
            case 120://Após expressão num comando IF

                machine.addInstruction(EnumInstruction.INS_DSVF.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                if (stackIF == null) {
                    stackIF = new Pilha();
                }

                stackIF.stack(new Node(new TableRow(String.valueOf(machine.inst.LC - 1), "", "")));

                break;
            case 121://Após instrução IF

                node = stackIF.unstack();

                machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC);

                break;
            case 122://Após domínio do THEN, antes do ELSE

                node = stackIF.unstack();

                machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC + 1);

                machine.addInstruction(EnumInstruction.INS_DSVS.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                stackIF.stack(new Node(new TableRow(String.valueOf(machine.inst.LC - 1), "", "")));

                break;
            case 123://Comando WHILE antes da expressão

                if (stackWHILE == null) {
                    stackWHILE = new Pilha();
                }

                stackWHILE.stack(new Node(new TableRow(String.valueOf(machine.inst.LC))));

                break;
            case 124://Comando WHILE depois da expressão

                machine.addInstruction(EnumInstruction.INS_DSVF.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                stackWHILE.stack(new Node(new TableRow(String.valueOf(machine.inst.LC - 1))));

                break;
            case 125://Após comando WHILE

                node = stackWHILE.unstack();

                machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC + 1);

                node = stackWHILE.unstack();

                machine.addInstruction(EnumInstruction.INS_DSVS.getCode(), Integer.MIN_VALUE, Integer.parseInt(node.getRow().getCodigo()));

                break;
            case 126://Comando REPEAT – início

                if (stackREPEAT == null) {
                    stackREPEAT = new Pilha();
                }

                stackREPEAT.stack(new Node(new TableRow(String.valueOf(machine.inst.LC))));

                break;
            case 127://Comando REPEAT – fim

                node = stackREPEAT.unstack();

                machine.addInstruction(EnumInstruction.INS_DSVF.getCode(), Integer.MIN_VALUE, Integer.parseInt(node.getRow().getCodigo()));

                break;
            case 128://Comando READLN início
                identifierContext = CONTEXT_READLN;
                break;
            case 129://Identificador de variável

                switch (identifierContext) {
                    case CONTEXT_READLN:

                        value = (Object[]) tableSymbols.find(TableSymbol.IND_VAR, currentLevel, semanticValue);

                        if (value != null) {
                            machine.addInstruction(EnumInstruction.INS_LEIT.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                            pos = Math.abs(currentLevel - (int) value[3]);

                            machine.addInstruction(EnumInstruction.INS_ARMZ.getCode(), pos, Integer.parseInt(value[1].toString()));
                        } else {
                            throw new SemanticException("Identificador inválido, Linha: " + row.getLine(), row.getPos());
                        }

                        break;
                    case CONTEXT_EXPRESSAO:

                        value = (Object[]) tableSymbols.find(TableSymbol.IND_VAR, currentLevel, semanticValue);

                        if (value != null) {

                            pos = Math.abs(currentLevel - (int) value[3]);

                            machine.addInstruction(EnumInstruction.INS_CRVL.getCode(), pos, Integer.parseInt(value[1].toString()));

                            break;
                        }

                        value = (Object[]) tableSymbols.find(TableSymbol.IND_PAR, currentLevel, semanticValue);

                        if (value != null) {

                            pos = Math.abs(currentLevel - (int) value[3]);

                            machine.addInstruction(EnumInstruction.INS_CRVL.getCode(), pos, Integer.parseInt(value[1].toString()));

                            break;
                        }

                        throw new SemanticException("Identificador inválido, Linha: " + row.getLine(), row.getPos());
                }

                break;
            case 130://WRITELN - após literal na instrução WRITELN

                machine.addLit(semanticValue);

                machine.addInstruction(EnumInstruction.INS_IMPRL.getCode(), Integer.MIN_VALUE, machine.lit.LIT - 1);

                break;
            case 131://WRITELN após expressão
                machine.addInstruction(EnumInstruction.INS_IMPR.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 132://Após palavra reservada CASE

                if (stackCASE == null) {
                    stackCASE = new Pilha();
                }

                break;
            case 133://Após comando CASE

                node = stackCASE.unstack();

                do {
                    machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC);
                } while ((node = stackCASE.unstack()) != null);

                machine.addInstruction(EnumInstruction.INS_AMEM.getCode(), Integer.MIN_VALUE, -1);

                break;
            case 134://Ramo do CASE após inteiro, último da lista

                machine.addInstruction(EnumInstruction.INS_COPI.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                machine.addInstruction(EnumInstruction.INS_CRCT.getCode(), Integer.MIN_VALUE, Integer.parseInt(semanticValue));

                machine.addInstruction(EnumInstruction.INS_CMIG.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                do {
                    node = null;
                    if (stackCASE.getTopo() != null && stackCASE.getTopo().getRow().getToken().equals(String.valueOf(EnumInstruction.INS_DSVT.getCode()))) {
                        node = stackCASE.unstack();
                        machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC + 1);
                    }

                } while (node != null);

                stackCASE.stack(new Node(new TableRow(String.valueOf(machine.inst.LC))));

                machine.addInstruction(EnumInstruction.INS_DSVF.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                break;
            case 135://Após comando em CASE

                node = stackCASE.unstack();

                machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC + 1);

                stackCASE.stack(new Node(new TableRow(String.valueOf(machine.inst.LC), String.valueOf(EnumInstruction.INS_DSVS.getCode()), EnumInstruction.INS_DSVS.getName())));

                machine.addInstruction(EnumInstruction.INS_DSVS.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                break;
            case 136://Ramo do CASE: após inteiro

                machine.addInstruction(EnumInstruction.INS_COPI.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                machine.addInstruction(EnumInstruction.INS_CRCT.getCode(), Integer.MIN_VALUE, Integer.parseInt(semanticValue));

                machine.addInstruction(EnumInstruction.INS_CMIG.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                stackCASE.stack(new Node(new TableRow(String.valueOf(machine.inst.LC), String.valueOf(EnumInstruction.INS_DSVT.getCode()), EnumInstruction.INS_DSVT.getName())));

                machine.addInstruction(EnumInstruction.INS_DSVT.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                break;
            case 137://Após variável controle comando FOR

                if (stackFOR == null) {
                    stackFOR = new Pilha();
                }

                lastTableSymbolIndex = tableSymbols.getIndex(TableSymbol.IND_VAR, currentLevel, semanticValue);

                stackFOR.stack(new Node(new TableRow(String.valueOf(lastTableSymbolIndex))));

                if (lastTableSymbolIndex == -1) {
                    throw new SemanticException("Identificador inválido, Linha: " + row.getLine(), row.getPos());
                }

                break;
            case 138://Após expressão valor inicial

                value = (Object[]) tableSymbols.get(lastTableSymbolIndex);

                machine.addInstruction(EnumInstruction.INS_ARMZ.getCode(), lastTableSymbolLevel, Integer.parseInt(value[1].toString()));

                break;
            case 139://Após expressão – valor final

                if (stackFOR == null) {
                    stackFOR = new Pilha();
                }

                stackFOR.stack(new Node(new TableRow(String.valueOf(machine.inst.LC))));

                value = (Object[]) tableSymbols.get(lastTableSymbolIndex);

                machine.addInstruction(EnumInstruction.INS_COPI.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                pos = Math.abs(currentLevel - Integer.parseInt(value[3].toString()));

                machine.addInstruction(EnumInstruction.INS_CRVL.getCode(), lastTableSymbolLevel, Integer.parseInt(value[1].toString()));

                machine.addInstruction(EnumInstruction.INS_CMAI.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                machine.addInstruction(EnumInstruction.INS_DSVF.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                stackFOR.stack(new Node(new TableRow(String.valueOf(machine.inst.LC - 1))));

                /*
                    NAO FEITO
                    armazena na pilha de controle o endereço do nome da variável de controle relativo à tabela de símbolos.
                 */
                break;
            case 140://Após comando em FOR

                /*
                    Ainda não se tem a variável de controle pois ela está na base
                    da pilha, é necessário atualizar o valor depois.
                 */
                int valIndexIncCrvl = machine.inst.LC;
                machine.addInstruction(EnumInstruction.INS_CRVL.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                machine.addInstruction(EnumInstruction.INS_CRCT.getCode(), Integer.MIN_VALUE, 1);

                machine.addInstruction(EnumInstruction.INS_SOMA.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                /*
                    Necessário atualizar posteriormente
                 */
                int valIndexIncArmz = machine.inst.LC;
                machine.addInstruction(EnumInstruction.INS_ARMZ.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);

                node = stackFOR.unstack();

                machine.updateInstruction(Integer.parseInt(node.getRow().getCodigo()), Integer.MIN_VALUE, machine.inst.LC + 1);

                node = stackFOR.unstack();

                machine.addInstruction(EnumInstruction.INS_DSVS.getCode(), Integer.MIN_VALUE, Integer.parseInt(node.getRow().getCodigo()));

                machine.addInstruction(EnumInstruction.INS_AMEM.getCode(), Integer.MIN_VALUE, -1);

                /*
                    Ajustando o local da váriavel de controle.
                 */
                node = stackFOR.unstack();

                value = (Object[]) tableSymbols.get(Integer.parseInt(node.getRow().getCodigo()));

                pos = Math.abs(currentLevel - Integer.parseInt(value[3].toString()));

                machine.updateInstruction(valIndexIncCrvl, pos, Integer.parseInt(value[1].toString()));

                machine.updateInstruction(valIndexIncArmz, pos, Integer.parseInt(value[1].toString()));

                break;
            case 141://=
                machine.addInstruction(EnumInstruction.INS_CMIG.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 142://<
                machine.addInstruction(EnumInstruction.INS_CMME.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 143://>
                machine.addInstruction(EnumInstruction.INS_CMMA.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 144://>=
                machine.addInstruction(EnumInstruction.INS_CMAI.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 145://<=
                machine.addInstruction(EnumInstruction.INS_CMEI.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 146://<>
                machine.addInstruction(EnumInstruction.INS_CMDF.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 147://Expressão – operando com sinal unário
                machine.addInstruction(EnumInstruction.INS_INVR.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 148://Expressão – soma
                machine.addInstruction(EnumInstruction.INS_SOMA.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 149://Expressão – subtração
                machine.addInstruction(EnumInstruction.INS_SUBT.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 150://Expressão – or
                machine.addInstruction(EnumInstruction.INS_DISJ.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 151://Expressão – multiplicação
                machine.addInstruction(EnumInstruction.INS_MULT.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 152://Expressão – divisão
                machine.addInstruction(EnumInstruction.INS_DIVI.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 153://Expressão – and
                machine.addInstruction(EnumInstruction.INS_CONJ.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 154://Expressão – inteiro
                machine.addInstruction(EnumInstruction.INS_CRCT.getCode(), Integer.MIN_VALUE, Integer.parseInt(semanticValue));
                break;
            case 155://Expresso – not
                machine.addInstruction(EnumInstruction.INS_NEGA.getCode(), Integer.MIN_VALUE, Integer.MIN_VALUE);
                break;
            case 156://Expressão – variável
                identifierContext = CONTEXT_EXPRESSAO;
                break;
        }

        return messageReturn;
    }

    /**
     * Inicializa variáveis.
     */
    private void action100() {
        stackIF = new Pilha();
        stackWHILE = new Pilha();
        stackREPEAT = new Pilha();
        stackPROCEDURE = new Pilha();
        stackCASE = new Pilha();
        stackFOR = new Pilha();
        tableSymbols = new TableSymbol();
        machine = new HypotheticalMachine();
        currentLevel = 0;

        try {
            for (int i = 35; i < 40; i++) {
                tableSymbols.insert(TableSymbol.IND_PRO, currentLevel, languageRules.LMS_VALUE[i], 0, "1");
            }

            for (int i = 40; i < 48; i++) {
                tableSymbols.insert(TableSymbol.IND_PRO, currentLevel, languageRules.LMS_VALUE[i], 0, "0");
            }

            tableSymbols.insert(TableSymbol.IND_PRO, currentLevel, languageRules.LMS_VALUE[48], 0, "0");

        } catch (Exception e) {
        }

    }

}
