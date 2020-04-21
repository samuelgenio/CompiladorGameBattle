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

import java.util.Hashtable;

/**
 * Valores armazenados na tabela hash.
 * <pre>
 * 0 -> Nome
 * 1 -> GeralA
 * 2 -> GeralB
 * 3 -> Level
 * </pre>
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class TableSymbol {

    /**
     * Indicador de Rótulo.
     */
    public static final String IND_ROL = "ROL";

    /**
     * Indicador de Variável.
     */
    public static final String IND_VAR = "VAR";

    /**
     * Indicador de Procedure
     */
    public static final String IND_PRO = "PRO";

    /**
     * Indicador de Parâmetro.
     */
    public static final String IND_PAR = "PAR";

    /**
     * Tabela Hash
     */
    private final Hashtable hashTable;

    /**
     * Valor usado como base na formula de HORNER
     */
    private final int VLR_BASE = 255;//255

    private int maxLevel = 0;

    public int lastLevel = 0;

    public TableSymbol() {
        this.hashTable = new Hashtable();
    }

    public Object find(String type, int nivel, Object value) {
        return find(type, nivel, value, true);
    }

    /**
     * Encontra um registro na tabela hash
     *
     * @param type Tipo do registro a ser encontrado.
     * @param nivel Nível onde foi declarado.
     * @param value Valor a ser encontrado.
     * @param changeLevel Indica se deve procurar instância do valor em níveis
     * inferiores.
     * @return Objeto encontrado.
     * @see IND_VAR, IND_PRO, IND_PAR
     */
    public Object find(String type, int nivel, Object value, boolean changeLevel) {

        int index = getIndex(type, nivel, value, changeLevel);
        if (index != -1) {
            return hashTable.get(index);
        }

        return null;
    }

    /**
     * Encontra um registro na tabela hash
     *
     * @param index index doa procedure.
     * @return Objeto encontrado.
     */
    public Object get(int index) {

        if (!hashTable.isEmpty()) {
            return hashTable.get(index);
        }

        return null;
    }

    public int getIndex(String type, int nivel, Object value) {
        return getIndex(type, nivel, value, true);
    }

    /**
     * Encontra um registro na tabela hash
     *
     * @param type Tipo do registro a ser encontrado.
     * @param nivel Nível onde foi declarado.
     * @param value Valor a ser encontrado.
     * @param changeLevel Indica se deve procurar instância do valor em níveis
     * inferiores.
     * @return Index encontrado.
     * @see IND_VAR, IND_PRO, IND_PAR
     */
    public int getIndex(String type, int nivel, Object value, boolean changeLevel) {

        if (!hashTable.isEmpty()) {
            int index = getHornerValue(value + type + nivel);
            if (hashTable.get(index) != null) {
                return index;
            } else if (nivel > 0 && changeLevel) {
                return getIndex(type, --nivel, value, changeLevel);
            }
        }

        return -1;
    }

    /**
     * insere um registro na tabela hash
     *
     * @param type Tipo do registro a ser encontrado.
     * @param nivel Nível onde foi declarado.
     * @param value Valor a ser encontrado.
     * @return Objeto encontrado.
     * @throws java.lang.Exception
     * @see IND_VAR, IND_PRO, IND_PAR
     */
    public int insert(String type, int nivel, Object... value) throws Exception {

        if (value == null) {
            throw new Exception("Não é possivel inserir valor nulo!");
        }

        Object objKey = (value.length > 0) ? value[0] : value;

        if (find(type, nivel, objKey, false) != null) {
            return -1;
        }

        int index = getHornerValue(objKey + type + nivel);

        Object[] valueToInsert = new Object[4];

        int i = 0;
        valueToInsert[i++] = objKey;
        valueToInsert[i++] = value.length >= 2 ? value[1] : "";
        valueToInsert[i++] = value.length >= 3 ? value[2] : "";
        valueToInsert[i] = nivel;

        hashTable.put(index, valueToInsert);

        if (nivel > maxLevel) {
            maxLevel = nivel;
        }

        return index;

    }

    /**
     * Remove registro da tabela
     *
     * @param type Tipo do registro a ser encontrado.
     * @param nivel Nível onde foi declarado.
     * @param value Valor a ser encontrado.
     */
    public void remove(String type, int nivel, Object value) {
        hashTable.remove(getHornerValue(value + type + nivel));
    }

    /**
     * Altera registro da tabela
     *
     * @param type Tipo do registro a ser encontrado.
     * @param nivel Nível onde foi declarado.
     * @param value Valor a ser encontrado.
     * @throws java.lang.Exception
     */
    public void update(String type, int nivel, Object... value) throws Exception {

        if (value == null) {
            throw new Exception("Não é possivel alterar valor nulo!");
        }

        int index = getHornerValue(value[0] + type + nivel);
        hashTable.replace(index, value);
    }

    public boolean update(int index, boolean updateGeralA, boolean updateGeralB, Object... value) {

        Object item = hashTable.get(index);

        Object geralA = "";
        Object geralB = "";

        if (value.length > 0) {

            int i = 0;

            if (updateGeralA) {
                geralA = ((Object[]) value)[i++];
            }

            if (updateGeralB) {
                geralB = ((Object[]) value)[i++];
            }
        } else {
            geralA = value;
        }

        Object[] oldItem = (Object[]) item;

        Object[] newItem = new Object[4];
        newItem[0] = oldItem[0];
        newItem[1] = updateGeralA ? geralA : oldItem[1];
        newItem[2] = oldItem.length >= 3 && updateGeralB ? geralB : oldItem[2];
        newItem[3] = oldItem[3];

        hashTable.put(index, newItem);

        return true;
    }

    public void showData() {

        hashTable.values();
        System.out.println("----------------------------");
        for (Object value : hashTable.values()) {
            if (value instanceof String) {
                System.out.println(value.toString());
            } else {

                String show = "";

                for (Object object : (Object[]) value) {
                    show += object.toString() + " - ";
                }
                System.out.println(show);
            }
        }

        System.out.println("----------------------------");

    }

    /**
     * Efetua calculo de HORNER.
     *
     * @param value valor a ser calculado.
     * @return index calcudado
     */
    private int getHornerValue(Object value) {

        int[] p = new int[value.toString().length()];

        for (int i = 0; i < p.length; i++) {
            p[i] = (int) value.toString().charAt(i);
        }

        int result = 0;
        for (int i = p.length - 1; i >= 0; i--) {
            result = p[i] + (VLR_BASE * result);
        }
        return result;
    }

    public int size() {
        return this.hashTable.size();
    }

}
