/**
 * Title:     DialogoEsporta
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-set-2008
 */
package it.algos.albergo.clientealbergo.compleanni;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.importExport.ImportExport;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioDialogo;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Dialogo di impostazione della esportazione compleanni
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-set-2008 ore 15.20.34
 */
final class DialogoEsporta extends DialogoAnnullaConferma {

    /**
     * Costruttore completo con parametri.
     */
    DialogoEsporta() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            this.setTitolo("Esportazione compleanni");
            this.creaCampi();
            this.impagina();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void inizializza() {
        super.inizializza();
    } /* fine del metodo */


    public void avvia() {
        super.avvia();
    }// fine del metodo avvia


    /**
     * Crea i campi per il dialogo e li aggiunge alla collezione.
     * <p/>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        JButton bot;

        try {    // prova ad eseguire il codice

            /* campo formato */
            campo = CampoFactory.radioInterno(Campi.formato.get());
            campo.setValoriInterni("Excel,Testo");
            campo.setValore(1);
            campo.decora().obbligatorio();
            campo.setLarScheda(100);
            this.addCampoCollezione(campo);

            /* campo destinazione */
            bot = new JButton("Seleziona file..");
            bot.addActionListener(new AzSelezionaFile());

            campo = CampoFactory.testo(Campi.destinazione.get());
            campo.decora().obbligatorio();
            campo.decora().bottone(bot);
            campo.setLarScheda(350);
            this.addCampoCollezione(campo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Impagina il dialogo.
     * <p/>
     * Aggiunge i componenti
     */
    private void impagina() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            this.addCampo(this.getCampo(Campi.formato.get()));
            this.addCampo(this.getCampo(Campi.destinazione.get()));

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Seleziona il file di destinazione.
     * <p/>
     */
    private void selezionaFile() {
        /* variabili e costanti locali di lavoro */
        File file = null;
        JFileChooser chooser;
        int risultato;
        boolean continua;
        String path;
        String estensione;

        try {    // prova ad eseguire il codice

            /* recupera l'eventuale valore esistente e crea un File */
            path = this.getDestinazione();
            if (Lib.Testo.isValida(path)) {
                file = new File(path);
            } else {
                file = new File("compleanni");
            }// fine del blocco if-else


//            if (Lib.Testo.isValida(path)) {
//                file = new File(path);
//            }// fine del blocco if

            /* crea regola e presenta il chooser */
            chooser = new JFileChooser();
            chooser.setDialogTitle("Seleziona il file destinazione");
            chooser.setSelectedFile(file);
            risultato = chooser.showSaveDialog(this);
            continua = (risultato == JFileChooser.APPROVE_OPTION);

            if (continua) {
                file = chooser.getSelectedFile();
                continua = (file != null);
            }// fine del blocco if

            /* recupera il path */
            if (continua) {
                path = file.getPath();
            }// fine del blocco if


            /* registra il path nel campo */
            if (continua) {
                this.setDestinazione(path);
            }// fine del blocco if

            /**
             * se il file è nuovo e l'estensione manca,
             * aggiunge automaticamente l'estensione in base al tipo di formato scelto
             */
            if (continua) {
                if (!Lib.File.isEsisteFile(path)) {
                    estensione = Lib.File.getEstensione(path);
                    if (Lib.Testo.isVuota(estensione)) {
                        this.syncEstensione();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizza l'estensione del file in base al formato scelto.
     * <p/>
     */
    private void syncEstensione() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String path;
        String strip;
        String nuova;

        try {    // prova ad eseguire il codice

            path = this.getDestinazione();
            continua = Lib.Testo.isValida(path);

            if (continua) {
                strip = Lib.File.stripEstensione(path);
                if (this.isExcel()) {
                    nuova=strip+".xls";
                } else {
                    nuova=strip+".txt";
                }// fine del blocco if-else
                this.setDestinazione(nuova);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }





    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice

            confermabile = super.isConfermabile();

            if (confermabile) {

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        String path;
        MessaggioDialogo messaggio;
        String testo;

        try { // prova ad eseguire il codice

            /* controlla se il file è già esistente e in tal caso chiede conferma */
            path = this.getDestinazione();
            if (Lib.File.isEsisteFile(path)) {
                testo = "Il file " + path + " esiste già\n";
                testo += "Lo vuoi sovrascrivere?";
                messaggio = new MessaggioDialogo(testo);
                continua = messaggio.isConfermato();
            }// fine del blocco if

            if (continua) {
                super.eventoConferma();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String nome;

        try { // prova ad eseguire il codice

            nome = campo.getNomeInterno();

            if (nome.equals(Campi.formato.get())) {
                this.syncEstensione();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        super.eventoMemoriaModificata(campo);    //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     * Ritorna il formato di esportazione codificato
     * in base all'interfaccia standard.
     * <p/>
     * @return il formato di esportazione
     */
    public ImportExport.ExportFormats getFormatoExport () {
        /* variabili e costanti locali di lavoro */
        ImportExport.ExportFormats formato  = null;

        try {    // prova ad eseguire il codice
            if (this.isExcel()) {
                formato = ImportExport.ExportFormats.excel;
            } else {
                formato = ImportExport.ExportFormats.tabText;
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return formato;
    }





    /**
     * Ritorna true se il formato scelto è Excel, false se è testo.
     * <p/>
     *
     * @return il percorso completo del file
     */
    private boolean isExcel() {
        return (this.getOpzione()==1);
    }


    /**
     * Ritorna il valore del campo Destinazione.
     * <p/>
     *
     * @return il percorso completo del file
     */
    public String getDestinazione() {
        return this.getString(Campi.destinazione.get());
    }


    /**
     * Assegna il valore al campo Destinazione.
     * <p/>
     *
     * @param destinazione il percorso completo del file
     */
    private void setDestinazione(String destinazione) {
        this.setValore(Campi.destinazione.get(), destinazione);
    }

    /**
     * Ritorna il valore dell'opzione formato selezionata.
     * (1=excel, 2=testo)
     * <p/>
     *
     * @return il percorso completo del file
     */
    private int getOpzione() {
        return this.getInt(Campi.formato.get());
    }


    /**
     * Listener bottone Seleziona File
     * </p>
     */
    private final class AzSelezionaFile implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            selezionaFile();
        }
    } // fine della classe 'interna'




    /**
     * Codifica dei campi del dialogo.
     */
    private enum Campi {

        formato("formato"), // formato di esportazione
        destinazione("destinazione"); // file di destinazione

        /**
         * titolo del popup.
         */
        private String nome;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo del popup utilizzato per il tooltiptext
         */
        Campi(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        private String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        public String get() {
            return getNome();
        }
    }// fine della classe

}// fine della classe
