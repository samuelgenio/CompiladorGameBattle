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
import br.com.samuka.compiladorgamebattle.model.Exception.SyntaticException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Sintatic {

    private final String START_END = "$";

    List<ItemToken> lexicalTokens;

    private int indexTokens;

    public String fileContentFinal;

    private final boolean indSemantic;

    private Semantic semantic;

    private final List<ItemToken> tokens;

    private int lastTokenFound = 0;

    private int indexBreakLine = 0;

    private final Pilha stack = PrincipalController.LR.getPilha();

    private TableRow prevToken;

    private TableRow currentToken;

    public Sintatic(List<ItemToken> lexicalTokens, boolean indSemantic, List<ItemToken> tokens) {
        this.lexicalTokens = lexicalTokens;
        this.indSemantic = indSemantic;
        this.tokens = tokens;
        if (indSemantic) {
            semantic = new Semantic();
        }
    }

    public String execute() throws SyntaticException, SemanticException {

        stack.printPilha();

        System.out.println("tokens -----------------------------------");
        tokens.forEach((i) -> System.err.println(i));
        System.out.println("tokens -----------------------------------");

        currentToken = getNextToken();

        while (!goNextToken()) {
        };

        return null;
    }

    private boolean goNextToken() throws SyntaticException, SemanticException {

        if (currentToken == null) {
            int pos = 0;
            if (prevToken != null) {
                pos = prevToken.getPos() + prevToken.getToken().length();
            }

            currentToken = PrincipalController.LR.getStartEndToken();
            currentToken.setPos(pos);
        }

        stack.printPilha();
        Node topNode = stack.unstack();
        int stackNext = topNode.getRow().getCode();

        if (stack.empty()) {
            return true;
        } else if (PrincipalController.LR.isTerminal(stackNext)) {
            if (stackNext == currentToken.getCode()) {
                if (stack.empty()) {
                    return true;
                } else {
                    prevToken = currentToken;
                    currentToken = getNextToken();
                    return false;
                }
            } else {
                throw new SyntaticException(PrincipalController.LR.getSintaxError(prevToken, currentToken), currentToken.getPos());
            }
        } else if (!PrincipalController.LR.isSemanticVerify(stackNext)) {
            if (productionRule(topNode.getRow(), currentToken)) {
                return false;
            } else {
                throw new SyntaticException(PrincipalController.LR.getSintaxError(prevToken, currentToken), currentToken.getPos());
            }
        } else if (PrincipalController.LR.isSemanticVerify(stackNext)) {
            try {
                semantic.action(topNode.getRow(), prevToken.getToken());
            } catch (SemanticException ex) {
                throw ex;
            }
            return false;
        }

        return true;
    }

    public boolean productionRule(TableRow topToken, TableRow token) throws SyntaticException {

        try {

            TableRow[] rule = PrincipalController.LR.searchProduction(topToken, token);

            if (rule != null) {
                for (TableRow row : rule) {
                    if (row.getCode() != 0) {
                        /*
                                Valor da regra é 0 caso necessite ser removida da pilha, pois 
                                não contém determinados caracteres na instrução.
                         */
                        stack.stack(new br.com.samuka.compiladorgamebattle.model.Node(row));
                    }
                }

            } else {
                return false;
            }
        } catch (Exception e) {
            throw new SyntaticException("Erro[" + token + "] - indexError[" + indexTokens + "]", token.getPos());
        }

        return true;
    }

    private TableRow getNextToken() {
        TableRow nextToken = lexicalTokens.get(indexTokens++).getRow();

        if (nextToken.getCodigo().equals(PrincipalController.LR.COMMENT_CODE)) {
            indexTokens++;
            return getNextToken();
        }

        return nextToken;
    }

    public List<TableRow> getInstructions() {

        List<TableRow> retorno = new ArrayList<>();

        int i = 0;
        for (HypotheticalMachine.Item item : semantic.machine.inst.value) {
            if (item.codigo == -1) {
                break;
            }
            retorno.add(new TableRow(i, EnumInstruction.get(item.codigo).getName(),
                    item.op1 == Integer.MIN_VALUE ? "-" : String.valueOf(item.op1),
                    item.op2 == Integer.MIN_VALUE ? "-" : String.valueOf(item.op2)));
            i++;
        }

        return retorno;
    }

    public void executeMachine() {
        semantic.machine.interpreta();
    }

}
