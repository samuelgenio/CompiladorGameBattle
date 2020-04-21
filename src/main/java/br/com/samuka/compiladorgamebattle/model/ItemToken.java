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
public class ItemToken {
    
    public static int FLAG_SUCCESS = 0;
    
    public static int FLAG_ERROR = 1;
    
    private int flagOperacao;
    
    private String desOperacao = "";
    
    /**
     * Posição inicial do lexico.
     */
    private int pos;
    
    private TableRow row;

    /**
     * @return the flagOperacao
     */
    public int getFlagOperacao() {
        return flagOperacao;
    }

    /**
     * @param flagOperacao the flagOperacao to set
     */
    public void setFlagOperacao(int flagOperacao) {
        this.flagOperacao = flagOperacao;
    }

    /**
     * @return the desOperacao
     */
    public String getDesOperacao() {
        return desOperacao;
    }

    /**
     * @param desOperacao the desOperacao to set
     */
    public void setDesOperacao(String desOperacao) {
        this.desOperacao = desOperacao;
    }

    /**
     * @return the row
     */
    public TableRow getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(TableRow row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return row.getCodigo() + " - " + row.getToken() + " - " + row.getDescricao() + "";
    }

    /**
     * @return the pos
     */
    public int getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(int pos) {
        this.pos = pos;
        this.row.setPos(pos);
    }
    
    /**
     * @param line
     */
    public void setLine(int line) {
        this.row.setLine(line);
    }
    
}
