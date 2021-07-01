/**
 * Title:     ImportExport
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      13-mar-2006
 */
package it.algos.base.importExport;

import it.algos.base.errore.Errore;
import it.algos.base.importExport.methods.CSVExportMethod;
import it.algos.base.importExport.methods.ExcelExportMethod;
import it.algos.base.importExport.methods.ExportMethod;
import it.algos.base.importExport.methods.TabTextExportMethod;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interfaccia di riferimento per Import/Export.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 13-mar-2006 ore 17.36.07
 */
public interface ImportExport {

    /**
     * Classe interna Enumerazione.
     * <p/>
     * Sorgenti disponibili per export
     */
    public enum Sorgente implements Serializable {

        tutti("Esporta tutti i record"),
        selezione("Esporta la selezione");

        /**
         * descrizione del formato
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         *
         * @param descrizione del formato
         */
        Sorgente(String descrizione) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setDescrizione(descrizione);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String toString() {
            return this.getDescrizione();
        }


        private String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
            char[] a = {'1', '2', '3'};
        }

    }// fine della classe


    /**
     * Formati di esportazione disponibili.
     * <p/>
     */
    public enum ExportFormats implements Serializable {

        tabText("Tab text", "Testo delimitato da tabulazione", "txt",new TabTextExportMethod()),
        CSV("CSV", "Comma separated values", "csv",new CSVExportMethod()),
        excel("Excel", "Microsoft Excel", "xls", new ExcelExportMethod());

        /**
         * nome breve del formato.
         * <p/>
         */
        private String nome;

        /**
         * descrizione estesa del formato.
         * <p/>
         */
        private String descrizione;

        /**
         * Metodo di esportazione
         */
        private ExportMethod metodo;

        /**
         * Estensione tipica del file
         */
        private String estensione;


        /**
         * Costruttore completo con parametri.
         *
         * @param nome breve del formato
         * @param descrizione estesa del formato
         * @param estensione estensione tipica del file (senza punto)
         * @param metodo di esportazione
         */
        ExportFormats(String nome, String descrizione, String estensione, ExportMethod metodo) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
                this.setDescrizione(descrizione);
                this.setEstensione(estensione);
                this.setMetodo(metodo);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        private String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        public String getEstensione() {
            return estensione;
        }


        private void setEstensione(String estensione) {
            this.estensione = estensione;
        }


        public ExportMethod getMetodo() {
            return metodo;
        }


        private void setMetodo(ExportMethod metodo) {
            this.metodo = metodo;
        }


        /**
         * Ritorna la lista degli elementi della Enum.
         * <p/>
         *
         * @return la lista degli elementi della Enum
         */
        public static ArrayList<ExportFormats> getElementi() {
            /* variabili e costanti locali di lavoro */
            ArrayList<ExportFormats> lista = null;

            try {    // prova ad eseguire il codice
                lista = new ArrayList<ExportFormats>();
                for (ExportFormats format : values()) {
                    lista.add(format);
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String toString() {
            return this.getNome();
        }

    }// fine della classe


}// fine della interfaccia
