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

import br.com.samuka.compiladorgamebattle.model.Exception.LexicException;
import br.com.samuka.compiladorgamebattle.util.Automato;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Lexic extends LanguageContansts {

    private final String input;

    private int pos;

    private final Automato automato = new Automato();

    /**
     * Armazena o último erro.
     */
    public String lastError;

    public int line = 1;

    public Lexic(String input) {
        this.input = input;

        if (LanguageLexicConstraints.TOKEN_SCANNER_TABLE == null) {
            loadScannerTableFromFile();
        }
    }

    /**
     *
     * @return @throws
     * br.com.samuka.compiladorgamebattle.model.Exception.LexicException
     */
    public List<ItemToken> execute() throws LexicException {

        List<ItemToken> listTokens = new ArrayList<>();

        ItemToken lastToken;

        do {

            lastToken = nextToken();

            if (lastToken != null && lastToken.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
                listTokens.add(lastToken);
            }

        } while (lastToken != null);

        return listTokens;
    }

    private ItemToken nextToken() throws LexicException {
        if (!hasInput()) {
            return null;
        }

        int start = pos;

        int state = 0;
        int lastState = 0;
        int endState = -1;
        int end = -1;
        boolean isBreakLine;

        int prevLine = line;

        while (hasInput()) {
            lastState = state;

            char lastChar = nextChar();
            state = nextState(lastChar, state);

            if ((int) lastChar == 10) {
                isBreakLine = true;
                if (isBreakLine) {
                    line++;
                }
            }

            if (state < 0) {
                break;
            } else {
                if (tokenForState(state) >= 0) {
                    endState = state;
                    end = pos;
                }
            }
        }
        if (endState < 0 || (endState != state && tokenForState(lastState) == -2)) {
            LexicException exception = new LexicException("Erro lexico, linha " + line, start);
            this.lastError = exception.getMessage();
            throw new LexicException("Erro lexico, linha " + line, start);
        }

        pos = end;

        int token = tokenForState(endState);

        if (token == 0) {
            return nextToken();
        } else {
            String lexeme = input.substring(start, end);

            ItemToken tokenFound = automato.getItemToken(lexeme);
            if (tokenFound != null) {
                tokenFound.setPos(start);
                if (prevLine == line) {
                    tokenFound.setLine(prevLine);
                } else {
                    line--;
                }
            }

            return tokenFound;
        }
    }

    private int nextState(char c, int state) {

        if (c == '\'') {
            System.err.println("ola");
        }

        int next = LanguageLexicConstraints.TOKEN_SCANNER_TABLE[state][c];
        return next;
    }

    private int tokenForState(int state) {
        if (state < 0 || state >= LanguageLexicConstraints.TOKEN_STATE.length) {
            return -1;
        }

        return LanguageLexicConstraints.TOKEN_STATE[state];
    }

    private boolean hasInput() {
        return pos < input.length();
    }

    private char nextChar() {
        if (hasInput()) {
            return input.charAt(pos++);
        } else {
            return (char) -1;
        }
    }

    private void loadScannerTableFromFile() {

        try {
            File file = new File(getClass().getResource("/files/token_scanner_table.txt").toURI());

            List<String[]> list;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                list = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    String[] replaced = line.split(",");
                    list.add(replaced);
                }
            }

            LanguageLexicConstraints.TOKEN_SCANNER_TABLE = new int[list.size()][list.get(0).length];

            int i = 0;
            for (String[] values : list) {

                int j = 0;
                for (String value : values) {
                    LanguageLexicConstraints.TOKEN_SCANNER_TABLE[i][j] = Integer.parseInt(value.trim());
                    j++;
                }
                i++;
            }

        } catch (IOException | NumberFormatException | URISyntaxException ex) {
        }

    }

}
