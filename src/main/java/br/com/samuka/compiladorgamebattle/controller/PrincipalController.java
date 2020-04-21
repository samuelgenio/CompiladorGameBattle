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
package br.com.samuka.compiladorgamebattle.controller;

import br.com.samuka.compiladorgamebattle.model.Exception.LexicException;
import br.com.samuka.compiladorgamebattle.model.Exception.SemanticException;
import br.com.samuka.compiladorgamebattle.model.Exception.SyntaticException;
import br.com.samuka.compiladorgamebattle.model.ItemToken;
import br.com.samuka.compiladorgamebattle.model.LanguageRules;
import br.com.samuka.compiladorgamebattle.model.Lexic;
import br.com.samuka.compiladorgamebattle.model.Sintatic;
import br.com.samuka.compiladorgamebattle.model.TableRow;
import br.com.samuka.compiladorgamebattle.util.Uteis;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class PrincipalController implements Initializable {

    public static final String LINE_BREAK = "¬";// AltGr + 6

    public static final String START_END = "$";

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private Text txQtdLinhas;

    @FXML
    private Button btInterpretar;

    @FXML
    private Text txQtdTokens;

    @FXML
    private Text txAviso;

    @FXML
    private TableView tbResult;

    @FXML
    private TableView<TableRow> tbCodigoIntermediario;

    @FXML
    private TextArea txaArquivo;

    @FXML
    private CheckBox ckSintatico, ckSemantico;

    public static LanguageRules LR = new LanguageRules();

    private int indexTokens = 0;

    private List<ItemToken> tokens;

    public static List<Integer> tokensBreakLine;

    private Sintatic sintatico;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fileChooser.setTitle("Selecione uma arquivo");
        try {
            if (new File(System.getProperty("user.dir") + "/temp").isDirectory()) {
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((TableColumn) tbResult.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("seq"));
        ((TableColumn) tbResult.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ((TableColumn) tbResult.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("token"));
        ((TableColumn) tbResult.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("descricao"));

        ((TableColumn) tbCodigoIntermediario.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("seq"));
        ((TableColumn) tbCodigoIntermediario.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ((TableColumn) tbCodigoIntermediario.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("token"));
        ((TableColumn) tbCodigoIntermediario.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("descricao"));

        ckSintatico.setSelected(true);
        ckSemantico.setSelected(true);
    }

    @FXML
    void onActionBtCarregarArquivo(ActionEvent ev) {

        File file = fileChooser.showOpenDialog(((Node) (ev.getSource())).getScene().getWindow());

        if (file != null) {

            if (!file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3).equalsIgnoreCase("txt")) {
                Uteis.showAlert(AlertType.INFORMATION, "Formato Inválido", "Selecione arquivos com extensão .txt");
                return;
            }

            String fileContent = "";

            FileReader reader = null;
            BufferedReader readered = null;
            try {

                reader = new FileReader(file);

                readered = new BufferedReader(reader);

                String line;

                while ((line = readered.readLine()) != null) {
                    fileContent += line + "\r\n";
                }

            } catch (IOException ex) {
                System.err.println("Falha na leitura do arquivo " + ex.getMessage());
            } finally {
                try {
                    readered.close();
                    reader.close();
                } catch (IOException e) {
                }
            }

            txaArquivo.setText(fileContent);
        }
    }

    @FXML
    void onActionBtExecutar(ActionEvent ev) {
        sintatico = null;
        btInterpretar.setDisable(true);

        tbResult.getItems().remove(0, tbResult.getItems().size());

        String fileContent = txaArquivo.getText();

        if (!fileContent.isEmpty()) {

            int line = 1;
            tokensBreakLine = new ArrayList<>();
            tokens = new ArrayList<>();

            String aviso = "";

            Lexic lexic = new Lexic(fileContent);
            try {
                tokens = lexic.execute();
            } catch (LexicException e) {
                if (e.getMessage() == null) {
                    aviso = "NullPointer";
                } else {
                    e.getMessage();
                    aviso = e.getMessage();
                }
            }

            int cont = 1;
            for (ItemToken token : tokens) {
                token.getRow().setSeq(cont++);
                tbResult.getItems().add(token.getRow());
                System.err.println(token);
            }

            txQtdLinhas.setText(String.valueOf(aviso.isEmpty() ? line - 1 : line));
            txQtdTokens.setText(String.valueOf(tokens.size()));
            txAviso.setText(aviso);

            ItemToken token = new ItemToken();
            token.setRow(LR.getStartEndToken());

            tokens.add(token);

            if (aviso.isEmpty() && ckSintatico.isSelected()) {
                sintatico = new Sintatic(tokens, ckSemantico.isSelected(), tokens);

                String message = "";

                try {
                    sintatico.execute();
                } catch (SemanticException | SyntaticException ex) {
                    if (ex != null) {
                        message = ex.getMessage();
                    }
                }

                if (!message.isEmpty()) {
                    txAviso.setText(message);
                } else {
                    btInterpretar.setDisable(false);
                }

                tbCodigoIntermediario.getItems().remove(0, tbCodigoIntermediario.getItems().size());
                if (ckSemantico.isSelected()) {
                    tbCodigoIntermediario.getItems().addAll(sintatico.getInstructions());
                }

            }

        }
    }

    @FXML
    void onActionBtInterpretar(ActionEvent ev) {

        if (sintatico != null && ckSemantico.isSelected()) {
            sintatico.executeMachine();
        }
    }

}
