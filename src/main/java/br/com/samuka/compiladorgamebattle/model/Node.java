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
public class Node {
    
    private TableRow row;

    /**
     * Próximo item da pilha
     */
    private Node next;

    public Node() {
    }
    
    public Node(TableRow row) {
        this.row = row;
    }

    /**
     * @return the next
     */
    public Node getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Node next) {
        this.next = next;
    }

    /**
     * @return the row
     */
    public TableRow getRow() {
        return row;
    }

    @Override
    public String toString() {
        return getRow().getCodigo() + " - " + getRow().getToken() + " - " + getRow().getDescricao();
    }

    @Override
    public boolean equals(Object objectToCompare) {
        
        if (objectToCompare instanceof TableRow) {
            return this.getRow().getCodigo().equals(((TableRow) objectToCompare).getCodigo());
        }
        
        return false;
    }
}
