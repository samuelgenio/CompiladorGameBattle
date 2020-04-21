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
package br.com.samuka.compiladorgamebattle.util;

import br.com.samuka.compiladorgamebattle.model.ItemToken;
import br.com.samuka.compiladorgamebattle.model.LanguageRules;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Automato {

    LanguageRules lms = new LanguageRules();

    public ItemToken getItemToken(String str) {

        ItemToken item = lms.isLogicalOperator(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isRelationOperator(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isReservedWord(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isSimbol(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isArithmeticOperator(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isIndetifier(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isComment(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isString(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        item = lms.isIntNumber(str);

        if (item.getFlagOperacao() == ItemToken.FLAG_SUCCESS) {
            return item;
        }

        return item;
    }

}
