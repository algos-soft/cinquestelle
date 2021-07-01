/**
 * Title:     ExportMethod
 * Copyright: Copyright (c) 2008
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-01-2008
 */
package it.algos.base.importExport.methods;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Metodo di esportazione Excel.
 * Basato su librerie Apache POI
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 03-01-2008
 */
public class ExcelExportMethod extends ExportMethod {

    /**
     * Oggetto Workbook di riferimento
     */
    private HSSFWorkbook workbook;

    /**
     * Oggetto Worksheet di riferimento
     */
    private HSSFSheet worksheet;

    /**
     * Riferimento alla riga corrente del worksheet
     */
    private HSSFRow currentRow;

    /**
     * Riferimento alla cella corrente della riga corrente
     */
    private HSSFCell currentCell;

    /**
     * stile cella per i titoli di colonna
     */
    private HSSFCellStyle stileTitoli;

    /**
     * stile cella per i campi testo
     */
    private HSSFCellStyle stileTesto;

    /**
     * stile cella per i campi testoArea
     */
    private HSSFCellStyle stileTestoArea;

    /**
     * stile cella per i campi numerici
     */
    private HSSFCellStyle stileNumero;

    /**
     * stile cella per i campi booleani
     */
    private HSSFCellStyle stileBooleano;

    /**
     * stile cella per i campi data
     */
    private HSSFCellStyle stileData;

    /**
     * flag per controllare se usa i titoli di colonna
     */
    private boolean usaTitoli;


    /**
     * Costruttore completo con parametri. <br>
     */
    public ExcelExportMethod() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni iniziali.
     * </p>
     *
     * @param campi corrispondenti alle colonne
     * @param stream per la scrittura su file
     */
    public void inizializza(ArrayList<Campo> campi, FileOutputStream stream) {
        /* variabili e costanti locali di lavoro */
        String titolo;
        HSSFWorkbook wb;
        HSSFSheet sheet;

        super.inizializza(campi, stream);

        try { // prova ad eseguire il codice

            // create a new workbook
            wb = new HSSFWorkbook();
            this.setWorkbook(wb);

            // create a new sheet
            titolo = this.getTitoloDati();
            if (!Lib.Testo.isValida(titolo)) {
                titolo = "Foglio1";
            }// fine del blocco if-else
            sheet = wb.createSheet(titolo);
            this.setWorksheet(sheet);

            /**
             * crea le istanze degli stili di cella
             * queste istanze sono condivise dalle varie celle se no
             * excel va in errore
             */
            this.createCellStyles();

            /* resetta il riferimento alla riga corrente */
            this.setCurrentRow(null);

            /* resetta il riferimento alla cella corrente */
            this.setCurrentCell(null);

            /* resetta la variabile Usati Titoli (uso interno)*/
            this.setUsaTitoli(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Crea e registra le istanze degli stili di cella.
     * <p/>
     * Queste istanze sono condivise dalle varie celle se no
     * excel va in errore dopo 4000 celle
     */
    private void createCellStyles() {
        /* variabili e costanti locali di lavoro */
        HSSFCellStyle stile;
        HSSFWorkbook workbook;
        HSSFFont font;

        try {    // prova ad eseguire il codice

            /* titoli */
            stile = this.createBaseStyle();
            workbook = this.getWorkbook();
            font = workbook.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            stile.setFont(font);
            this.setStileTitoli(stile);

            /* testo */
            stile = this.createBaseStyle();
            this.setStileTesto(stile);

            /* testo area */
            stile = this.createBaseStyle();
            stile.setWrapText(true);
            this.setStileTestoArea(stile);

            /* numerico */
            stile = this.createBaseStyle();
            this.setStileNumero(stile);

            /* booleano */
            stile = this.createBaseStyle();
            this.setStileBooleano(stile);

            /* data */
            stile = this.createBaseStyle();
            stile.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            this.setStileData(stile);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea uno stile cella di base con le caratteristiche
     * comuni a tutti gli stili.
     * <p/>
     *
     * @return lo stile creato
     */
    private HSSFCellStyle createBaseStyle() {
        /* variabili e costanti locali di lavoro */
        HSSFCellStyle stile = null;
        HSSFWorkbook workbook;

        try {    // prova ad eseguire il codice
            workbook = this.getWorkbook();
            stile = workbook.createCellStyle();
            stile.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stile;
    }


    /**
     * Scrive i titoli delle colonne.
     * <p/>
     *
     * @param titoli delle colonne
     */
    public void writeTitles(ArrayList<String> titoli) {
        /* variabili e costanti locali di lavoro */
        HSSFCell cell;
        HSSFRichTextString rString;

        try { // prova ad eseguire il codice

            /* crea una riga */
            this.creaRiga();

            /* crea le celle con il titolo */
            for (String titolo : titoli) {
                cell = this.creaCella(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(this.getStileTitoli());
                rString = new HSSFRichTextString(titolo);
                cell.setCellValue(rString);
            }

            /* registra il fatto che ha usato i titoli */
            this.setUsaTitoli(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Scrive una riga di dati.
     * <p/>
     *
     * @param valori da scrivere nelle celle
     */
    public void writeRow(ArrayList<Object> valori) {
        /* variabili e costanti locali di lavoro */
        HSSFCell cell;
        HSSFCellStyle stile;
        HSSFRichTextString rString;
        HSSFRow row;
        int colonna = 0;
        int tipo;
        Campo campo;
        String testo;
        double numero;
        boolean bool;
        Date data;

        try { // prova ad eseguire il codice

            /* crea una riga */
            this.creaRiga();

            /* crea una cella per ogni valore */
            for (Object valore : valori) {

                tipo = this.getColumnType(colonna);
                cell = this.creaCella(tipo);
                stile = this.getDataCellStyle(colonna);
                if (stile != null) {
                    cell.setCellStyle(stile);
                }// fine del blocco if

                campo = this.getCampo(colonna);
                if (campo != null) {

                    if (campo.isTesto()) {
                        testo = Lib.Testo.getStringa(valore);
                        rString = new HSSFRichTextString(testo);
                        cell.setCellValue(rString);
                    }// fine del blocco if

                    if (campo.isNumero()) {
                        numero = Libreria.getDouble(valore);
                        cell.setCellValue(numero);
                    }// fine del blocco if

                    if (campo.isBooleano()) {
                        bool = Libreria.getBool(valore);
                        cell.setCellValue(bool);
                    }// fine del blocco if

                    if (campo.isData()) {
                        data = Libreria.getDate(valore);
                        if (!Lib.Data.isVuota(data)) {
                            cell.setCellValue(data);
                        } else {
                            // se la data è vuota rimuove la cella se no viene 01-01-1900
                            row = this.getCurrentRow();
                            row.removeCell(cell);
                        }// fine del blocco if-else
                    }// fine del blocco if

                }// fine del blocco if

                colonna++;
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il tipo di cella per una colonna di dati.
     * <p/>
     *
     * @param column l'indice della colonna (0 per la prima)
     *
     * @return il tipo di cella
     */
    private int getColumnType(int column) {
        /* variabili e costanti locali di lavoro */
        int tipo = HSSFCell.CELL_TYPE_BLANK;
        Campo campo;

        try {    // prova ad eseguire il codice

            campo = this.getCampo(column);
            if (campo != null) {
                if (campo.isTesto()) {
                    tipo = HSSFCell.CELL_TYPE_STRING;
                }// fine del blocco if

                if (campo.isNumero()) {
                    tipo = HSSFCell.CELL_TYPE_NUMERIC;
                }// fine del blocco if

                if (campo.isBooleano()) {
                    tipo = HSSFCell.CELL_TYPE_BOOLEAN;
                }// fine del blocco if

                if (campo.isData()) {
                    tipo = HSSFCell.CELL_TYPE_NUMERIC;
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Ritorna lo stile di cella corrispondente a una colonna di dati.
     * <p/>
     *
     * @param column l'indice della colonna di dati (0 per la prima)
     *
     * @return lo stile di cella
     */
    private HSSFCellStyle getDataCellStyle(int column) {
        /* variabili e costanti locali di lavoro */
        HSSFCellStyle stile = null;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(column);
            if (campo != null) {

                if (campo.isTesto()) {
                    stile = this.getStileTesto();
                }// fine del blocco if

                if (campo.isTestoArea()) {
                    stile = this.getStileTestoArea();
                }// fine del blocco if

                if (campo.isNumero()) {
                    stile = this.getStileNumero();
                }// fine del blocco if

                if (campo.isBooleano()) {
                    stile = this.getStileBooleano();
                }// fine del blocco if

                if (campo.isData()) {
                    stile = this.getStileData();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stile;
    }


    /**
     * Crea una nuova riga per il worksheet e la rende corrente.
     * <p/>
     * Resetta il riferimento alla cella corrente
     *
     * @return la riga creata
     */
    private HSSFRow creaRiga() {
        /* variabili e costanti locali di lavoro */
        HSSFRow row = null;
        HSSFSheet sheet;

        int numero = 0;

        try {    // prova ad eseguire il codice

            /* determina il numero per la nuova riga, 0 se non esiste riga corrente */
            row = this.getCurrentRow();
            if (row != null) {
                numero = row.getRowNum();
                numero++;
            }// fine del blocco if-else

            /* crea la riga e la rende corrente */
            sheet = this.getWorksheet();
            row = sheet.createRow(numero);
            this.setCurrentRow(row);

            /* resetta il riferimento alla cella corrente */
            this.setCurrentCell(null);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return row;
    }


    /**
     * Crea una nuova cella per la riga corrente e la rende corrente.
     * <p/>
     * Incrementa il contatore della cella corrente
     *
     * @param tipo il tipo di cella (costanti in HSSFCell.CELL_TYPE...)
     *
     * @return la cella creata
     */
    private HSSFCell creaCella(int tipo) {
        /* variabili e costanti locali di lavoro */
        HSSFCell cell = null;
        HSSFRow row;
        short numero = 0;

        try {    // prova ad eseguire il codice

            /* determina il numero per la nuova cella, 0 se non esiste cella corrente */
            cell = this.getCurrentCell();
            if (cell != null) {
                numero = cell.getCellNum();
                numero++;
            }// fine del blocco if-else

            /* crea la cella e la rende corrente */
            row = this.getCurrentRow();
            cell = row.createCell(numero);
            cell.setCellType(tipo);
            this.setCurrentCell(cell);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cell;
    }


    /**
     * Eventuali operazioni di chiusura specifiche del metodo.
     * <p/>
     */
    public void close() {
        /* variabili e costanti locali di lavoro */
        FileOutputStream stream;
        HSSFWorkbook workbook;
        HSSFSheet sheet;
        int quanteColonne;

        try { // prova ad eseguire il codice

            /* regola le larghezze colonna */
            sheet = this.getWorksheet();
            quanteColonne = this.getCampi().size();
            for (short k = 0; k < quanteColonne; k++) {
                sheet.autoSizeColumn(k);
            } // fine del ciclo for

            /* se usa i titoli di colonna congela la prima riga */
            if (this.isUsaTitoli()) {
                sheet.createFreezePane(0, 1, 0, 1);
            }// fine del blocco if

            // write the workbook to the output stream
            stream = this.getStream();
            workbook = this.getWorkbook();
            workbook.write(stream);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private HSSFWorkbook getWorkbook() {
        return workbook;
    }


    private void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    private HSSFSheet getWorksheet() {
        return worksheet;
    }


    private void setWorksheet(HSSFSheet worksheet) {
        this.worksheet = worksheet;
    }


    /**
     * Ritorna la riga corrente.
     * <p/>
     *
     * @return la riga corrente
     */
    private HSSFRow getCurrentRow() {
        return currentRow;
    }


    private void setCurrentRow(HSSFRow currentRow) {
        this.currentRow = currentRow;
    }


    /**
     * Ritorna la cella corrente della riga corrente.
     * <p/>
     *
     * @return la cella corrente
     */
    private HSSFCell getCurrentCell() {
        return currentCell;
    }


    private void setCurrentCell(HSSFCell currentCell) {
        this.currentCell = currentCell;
    }


    private HSSFCellStyle getStileTitoli() {
        return stileTitoli;
    }


    private void setStileTitoli(HSSFCellStyle stileTitoli) {
        this.stileTitoli = stileTitoli;
    }


    private HSSFCellStyle getStileTesto() {
        return stileTesto;
    }


    private void setStileTesto(HSSFCellStyle stileTesto) {
        this.stileTesto = stileTesto;
    }


    private HSSFCellStyle getStileTestoArea() {
        return stileTestoArea;
    }


    private void setStileTestoArea(HSSFCellStyle stileTestoArea) {
        this.stileTestoArea = stileTestoArea;
    }


    private HSSFCellStyle getStileNumero() {
        return stileNumero;
    }


    private void setStileNumero(HSSFCellStyle stileNumero) {
        this.stileNumero = stileNumero;
    }


    private HSSFCellStyle getStileBooleano() {
        return stileBooleano;
    }


    private void setStileBooleano(HSSFCellStyle stileBooleano) {
        this.stileBooleano = stileBooleano;
    }


    private HSSFCellStyle getStileData() {
        return stileData;
    }


    private void setStileData(HSSFCellStyle stileData) {
        this.stileData = stileData;
    }


    private boolean isUsaTitoli() {
        return usaTitoli;
    }


    private void setUsaTitoli(boolean usaTitoli) {
        this.usaTitoli = usaTitoli;
    }
} // fine della classe