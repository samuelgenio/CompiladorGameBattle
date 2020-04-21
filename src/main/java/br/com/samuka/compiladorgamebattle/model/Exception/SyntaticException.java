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
package br.com.samuka.compiladorgamebattle.model.Exception;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class SyntaticException extends Exception {
    
    public SyntaticException(String msg, int position) {
        super(msg + " próximo à posição " + position, new Throwable("Erro sintático, próximo à posição " + position));
    }

    public SyntaticException(String msg) {
        super(msg);
    }
    
}
