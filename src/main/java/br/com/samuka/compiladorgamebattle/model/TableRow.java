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

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class TableRow {

    private SimpleIntegerProperty seq;
    
    private SimpleStringProperty codigo;
    
    private SimpleStringProperty token;
    
    private SimpleStringProperty descricao;

    /**
     * Posição inicial do lexico.
     */
    private int pos;

    private int line;
    
    public TableRow(String codigo) {
        this.codigo = new SimpleStringProperty(codigo);
        this.token = new SimpleStringProperty("");
        this.descricao = new SimpleStringProperty("");
    }
    
    public TableRow(int codigo, String token, String descricao) {
        this.codigo = new SimpleStringProperty(String.valueOf(codigo));
        this.token = new SimpleStringProperty(token);
        this.descricao = new SimpleStringProperty(descricao);
    }
    
    public TableRow(String codigo, String token, String descricao) {
        this.codigo = new SimpleStringProperty(codigo);
        this.token = new SimpleStringProperty(token);
        this.descricao = new SimpleStringProperty(descricao);
    }
    
    public TableRow(String codigo, String token, String descricao, int pos) {
        this.codigo = new SimpleStringProperty(codigo);
        this.token = new SimpleStringProperty(token);
        this.descricao = new SimpleStringProperty(descricao);
        this.pos = pos;
    }
    
    public TableRow(int seq, String codigo, String token, String descricao) {
        this.seq = new SimpleIntegerProperty(seq);
        this.codigo = new SimpleStringProperty(codigo);
        this.token = new SimpleStringProperty(token);
        this.descricao = new SimpleStringProperty(descricao);
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo.getValue();
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(SimpleStringProperty codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token.getValue();
    }

    /**
     * @param token the token to set
     */
    public void setToken(SimpleStringProperty token) {
        this.token = token;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao.getValue();
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(SimpleStringProperty descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return getCodigo() + " - " + getToken() + " - " + getDescricao();
    }

    /**
     * @return the seq
     */
    public int getSeq() {
        return seq.getValue();
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(int seq) {
        this.seq = new SimpleIntegerProperty(seq);
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
    }
    
    /**
     * @return código do token
     */
    public int getCode() {
        return Integer.parseInt(this.codigo.getValue());
    }
    
    /**
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(int line) {
        this.line = line;
    }
    
}
