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

import br.com.samuka.compiladorgamebattle.util.Uteis;
import javafx.scene.control.Alert;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class HypotheticalMachine {

    public int MaxInst = 1000;
    public int MaxList = 30;
    public int b; //base do segmento
    public int topo; //topo da pilha da base de dados
    public int p; //apontador de instruções
    public int l; //primeiro operando
    public int a; //segundo operando
    public int nv; //número de variáveis;
    public int np; //número de parâmetros;
    public int operador; //codigo da instrução
    public int k; //segundo operando
    public int i;
    public int num_impr;
    public int[] S = new int[1000];

    Instructions inst;
    Literais lit;

    public HypotheticalMachine() {
        startInstructions();
        startLiterais();
    }
    
    /**
     * Inicializa a área de instruções.
     * @param instructions
     */
    private void startInstructions() {
        inst = new Instructions();
        for (int j = 0; j < MaxInst; j++) {
            inst.value[j].codigo = -1;
            inst.value[j].op1 = -1;
            inst.value[j].op2 = -1;
        }
        inst.LC = 0;
    }

    /**
     * Inicializa a área de literais
     */
    private void startLiterais() {
        lit = new Literais();
        for (int j = 0; j < MaxList; j++) {
            lit.value[j] = "";
            lit.LIT = 0;
        }
    }

    /**
     * Inclui uma instrução na área de instruções utilizada pela máquina
     * hipotética.
     * @param code
     * @param op1
     * @param op2
     * @return 
     */
    public boolean addInstruction(int code, int op1, int op2) {
        boolean aux;
        if (inst.LC >= MaxInst) {
            aux = false;
        } else {
            aux = true;
            inst.value[inst.LC].codigo = code;

            if (op1 != -1) {
                inst.value[inst.LC].op1 = op1;
            }

            if (code == 24) {
                inst.value[inst.LC].op2 = op2;
            }

            if (op2 != -1) {
                inst.value[inst.LC].op2 = op2;
            }

            inst.LC = inst.LC + 1;
        }
        return aux;
    }

    /**
     * Altera uma instrução da área de instruções utilizada pela máquina
     * hipotética.
     * @param s
     * @param o1
     * @param o2
     */
    public void updateInstruction(int s, int o1, int o2) {

        if (o1 != -1) {
            inst.value[s].op1 = o1;
        }

        if (o2 != -1) {
            inst.value[s].op2 = o2;
        }
    }

    /**
     * Inclui um literal na área de literais utilizada pela máquina hipotética.
     * @param literal
     * @return 
     */
    public boolean addLit(String literal) {
        boolean aux;
        if (lit.LIT >= MaxList) {
            aux = false;
        } else {
            aux = true;
            lit.value[lit.LIT] = literal;
            lit.LIT = lit.LIT + 1;
        }
        return aux;
    }

    /**
     * Utilizada para determinar a base.
     * @return 
     */
    public int base() {//determina base
        int b1;
        b1 = b;
        while (l > 0) {
            b1 = S[b1];
            l = l - 1;
        }
        return b1;
    }

    /**
     * Responsável por interpretar as instruções.
     */
    public void interpreta() {

        topo = 0;
        b = 0; //registrador base
        p = 0; //aponta proxima instrução
        S[1] = 0; //SL
        S[2] = 0; //DL
        S[3] = 0; //RA
        operador = 0;

        String leitura;

        while (operador != 26) {//Enquanto instrução diferente de Pare

            operador = inst.value[p].codigo;

            l = inst.value[p].op1;
            a = inst.value[p].op2;
            p = p + 1;
            
            switch (operador) {
                case 1://RETU
                    p = S[b + 2];
                    topo = b - a;
                    b = S[b + 1];
                    break;

                case 2://CRVL
                    topo = topo + 1;
                    S[topo] = S[base() + a];
                    break;

                case 3: //CRCT
                    topo = topo + 1;
                    S[topo] = a;
                    break;

                case 4://ARMZ
                    S[base() + a] = S[topo];
                    topo = topo - 1;
                    break;

                case 5://SOMA
                    S[topo - 1] = S[topo - 1] + S[topo];
                    topo = topo - 1;
                    break;

                case 6://SUBT
                    S[topo - 1] = S[topo - 1] - S[topo];
                    topo = topo - 1;
                    break;

                case 7://MULT
                    S[topo - 1] = S[topo - 1] * S[topo];
                    topo = topo - 1;
                    break;

                case 8: //DIVI
                    if (S[topo] == 0) {
                        Uteis.showAlert(Alert.AlertType.ERROR, "Divisão por zero.", "Erro durante a execução");
                        S[topo - 1] = S[topo - 1] / S[topo];
                        topo = topo - 1;
                    }
                    break;

                case 9://INVR
                    S[topo] = -S[topo];
                    break;

                case 10: //NEGA
                    S[topo] = 1 - S[topo];
                    break;

                case 11://CONJ
                    if ((S[topo - 1] == 1) && (S[topo] == 1)) {
                        S[topo - 1] = 1; //A no material impresso está como "1" e aqui estava como "-1"
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 12://DISJ
                    if ((S[topo - 1] == 1 || S[topo] == 1)) {
                        S[topo - 1] = 1;
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 13://CMME
                    if (S[topo - 1] < S[topo]) {
                        S[topo - 1] = 1;
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 14://CMMA
                    if (S[topo - 1] > S[topo]) {
                        S[topo - 1] = 1;
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 15://CMIG
                    if (S[topo - 1] == S[topo]) {
                        S[topo - 1] = 1;
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 16://CMDF
                    if (S[topo - 1] != S[topo]) {
                        S[topo - 1] = 1;
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 17://CMEI
                    if (S[topo - 1] <= S[topo]) {
                        S[topo - 1] = 1;
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 18://CMAI
                    if (S[topo - 1] >= S[topo]) {
                        S[topo - 1] = 1;
                    } else {
                        S[topo - 1] = 0;
                    }
                    topo = topo - 1;
                    break;

                case 19://DSVS
                    p = a;
                    break;

                case 20://DSVF
                    if (S[topo] == 0) {
                        p = a;
                        //topo=topo-1; //A no material impresso esta linha está fora do "if"!
                    }
                    topo = topo - 1;

                    break;

                case 21://LEIT
                    topo = topo + 1;
                    leitura = Uteis.showInputDialog("Leitura", "", "Informe o valor:");
                    (S[topo]) = Integer.parseInt(leitura);
                    break;

                case 22://IMPR
                    Uteis.showAlert(Alert.AlertType.INFORMATION, "Informação", "" + S[topo]);
                    topo = topo - 1;
                    break;

                case 23://IMPRLIT
                    if (a >= lit.LIT) {
                        Uteis.showAlert(Alert.AlertType.ERROR, "Literal não encontrado na área dos literais.", "Erro durante a execução");
                    } else {
                        Uteis.showAlert(Alert.AlertType.INFORMATION, "Informação", "" + lit.value[a]);
                        //AL.LIT++;
                    }
                    break;

                case 24://AMEM
                    topo = topo + a;
                    break;

                case 25://CALL
                    S[topo + 1] = base();
                    S[topo + 2] = b;
                    S[topo + 3] = p;
                    b = topo + 1;
                    p = a;
                    break;

                case 26:
                    //System.exit(0);
                    //PARA
                    break;

                case 27:
                    //NADA
                    break;

                case 28://COPI
                    topo = topo + 1;
                    S[topo] = S[topo - 1];
                    break;

                case 29://DSVT
                    if (S[topo] == 1) {
                        p = a;
                        //topo=topo-1; //A no material impresso esta linha está fora do "if"!
                    }
                    topo = topo - 1;
                    break;
                case 30:
                    System.out.println("ahead(" + S[topo] + ")");
                    break;
                case 31:
                    System.out.println("jump(" + S[topo] + ")");
                    break;
                case 32:
                    System.out.println("run(" + S[topo] + ")");
                    break;
                case 33:
                    System.out.println("atta(" + S[topo] + ")");
                    break;
                case 34:
                    System.out.println("turn()");
                    break;
                case 35:
                    System.out.println("getEnemyPos()");
                    topo = topo + 1;
                    S[topo] = 120;
                    break;
                case 36:
                    System.out.println("getEnemyName()");
                    break;
                case 37:
                    System.out.println("getEnemyHealth()");
                    break;
                case 38:
                    System.out.println("getEnemyDirection()");
                    break;
                case 39:
                    System.out.println("getName()");
                    break;
                case 40:
                    System.out.println("getHealth()");
                    break;
                case 41:
                    System.out.println("getPos()");
                    topo = topo + 1;
                    S[topo] = 110;
                    break;
                case 42:
                    System.out.println("getDirection()");
                    break;
                case 43:
                    System.out.println("stop()");
                    break;
                            
            }//fim do case
        }//fim do while
    }//fim do procedimento interpreta

    class Instructions {

        public Item[] value = new Item[1000];
        public int LC;

        /**
         * Construtor sem parâmetros. Todos os atributos são inicializados com
         * valores padrões.
         */
        Instructions() {
            for (int i = 0; i < 1000; i++) {
                value[i] = new Item();
            }
        }
    }

    class Item {

        public int codigo = 0;
        public int op1 = 0;
        public int op2 = 0;
    }

    /**
     * Classe de literais
     */
    class Literais {

        public String[] value = new String[30];
        public int LIT;
    }

}
