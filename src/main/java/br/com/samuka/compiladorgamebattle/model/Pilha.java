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
public final class Pilha {

    private int tamanho;
    private Node topo;

    public Pilha() {
    }

    public Pilha(Node topo) {
        stack(topo);
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the tamanho to set
     */
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * @return the topo
     */
    public Node getTopo() {
        return topo;
    }

    /**
     * @param topo the topo to set
     */
    public void setTopo(Node topo) {
        this.topo = topo;
    }

    public int length() {

        return this.getTamanho();
    }

    public boolean empty() {

        return this.getTamanho() == 0;
    }

    public void stack(Node card) {

        if (this.empty()) {

            this.setTopo(card);

        } else {
            //this.getTopo().setNext(card);
            card.setNext(this.getTopo());

            this.setTopo(card);
        }

        System.err.println("[topo] " + card + " [next] " + (card.getNext() != null ? card.getNext() : ""));

        tamanho++;

    }

    public Node unstack() {

        if (empty()) {
            return null;
        }

        //pega o topo atual para retorna-lo no fnal do metodo
        Node cartaoRetorno = this.getTopo();

        //seta o proximo do cartaoetorno como novo topo
        this.setTopo(cartaoRetorno.getNext());

        tamanho--;

        //retorna o cartao retorno (antigo topo)
        return cartaoRetorno;
    }

    @Override
    public String toString() {
        return getTopo().getRow().getCodigo() + " - " + getTopo().getRow().getToken() + " - " + getTopo().getRow().getDescricao();
    }

    /**
     * mostra como está a pilha atualmente.
     */
    public void printPilha() {

        String retorno = "|         |\r\n";

        Node node = getTopo();

        while (node != null) {
            retorno += "|" + node.getRow() + "|\r\n";
            node = node.getNext();
        }

        retorno += "|_________|";

        System.err.println(retorno);
    }

}
