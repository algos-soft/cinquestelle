/**
 * Title:     PanEditExportSettings
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-feb-2009
 */
package it.algos.base.importExport;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.Bottone;
import it.algos.base.componente.bottone.BottoneBase;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Pannello per editing di un ExportSettings
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-feb-2009 ore 12.30.00
 */
public final class PanEditExportSettings extends PannelloFlusso {

    /* oggetto ExportSettings da editare */
    private ExportSettings settings;

    /**
     * campo titoli colonna
     */
    private Campo campoTitoliColonna;

    /**
     * campo numeri di di riga
     */
    private Campo campoNumeriRiga;

    /**
     * campo formato
     */
    private Campo campoFormato;

    /**
     * campo che mantiene il percorso del file di uscita
     */
    private Campo campoFile;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param settings l'oggetto ExportSettings da editare
     */
    public PanEditExportSettings(ExportSettings settings) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        /* regola le variabili di istanza coi parametri */
        this.setSettings(settings);

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
        /* variabili e costanti locali di lavoro */
        Pannello panFile;
        Pannello panFormato;
        Pannello panHeader;
        Pannello pan2;

        try { // prova ad eseguire il codice

            /* crea il pannello file e lo aggiunge */
            panFile = this.creaPanFile();

            /* crea il pannello formato e lo aggiunge */
            panFormato = this.creaPanFormato();

            /* crea il pannello header e lo aggiunge */
            panHeader = this.creaPanHeader();

            pan2 = PannelloFactory.orizzontale(null);
            pan2.add(panFormato);
            pan2.add(panHeader);

            this.add(panFile);
            this.add(pan2);

            this.inizializza();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Crea il pannello header.
     * <p/>
     *
     * @return il pannello header
     */
    private Pannello creaPanHeader() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setGapPreferito(10);
            pan.setGapMassimo(15);
            pan.creaBordo("Header");

            campo = CampoFactory.checkBox("titoli colonna");
            this.setCampoTitoliColonna(campo);
            campo.avvia();
            pan.add(campo);

            campo = CampoFactory.checkBox("numeri di riga");
            this.setCampoNumeriRiga(campo);
            campo.avvia();
            pan.add(campo);

            /* dopo aver aggiunto i componenti, sblocco l'altezza */
            pan.sbloccaAltMax();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello Formato.
     * <p/>
     *
     * @return il pannello Formato
     */
    private Pannello creaPanFormato() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;
        ArrayList<ImportExport.ExportFormats> valori;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setGapPreferito(10);
            pan.setGapMassimo(15);
            pan.creaBordo("Formato");

            campo = CampoFactory.comboInterno("formato");
            this.setCampoFormato(campo);
            valori = ImportExport.ExportFormats.getElementi();
            campo.setValoriInterni(valori);
            campo.setUsaNonSpecificato(false);
            campo.decora().eliminaEtichetta();
            campo.setLarScheda(100);

            campo.setValore(1); //il primo valore

            campo.inizializza();

            /* aggiunge un listener per la modifica */
            campo.addListener(new AzPopFormatoModificato());

            campo.avvia();
            pan.add(campo);

            /* questo e' l'ultimo della fila, sblocco anche la larghezza */
            pan.sbloccaDimMax();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello file.
     * <p/>
     *
     * @return il pannello file
     */
    private Pannello creaPanFile() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = null;
        Campo campo;
        Bottone bot;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setGapPreferito(10);
            pan.setGapMassimo(15);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.creaBordo("File destinazione");

            campo = CampoFactory.testo("percorso");
            this.setCampoFile(campo);
            campo.decora().eliminaEtichetta();
            campo.setLarScheda(380);
            campo.setAbilitato(false); // fatto dopo l'inizializzazione
            campo.avvia();
            pan.add(campo);

            /* crea e aggiunge il bottone */
            bot = new BottoneBase("Seleziona...");
            bot.addActionListener(new AzSelezionaFile());
            pan.add(bot);

            /* dopo aver aggiunto i componenti, sblocco la dimensione */
            pan.sbloccaDimMax();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Inizializzazione.
     * <p/>
     */
    private void inizializza() {
        /* variabili e costanti locali di lavoro */
        ExportSettings settings;

        try {    // prova ad eseguire il codice
            settings = this.getSettings();

            this.setFile(settings.getPath());
            this.getCampoFormato().setValoreDaElenco(settings.getFormato());
            this.getCampoTitoliColonna().setValore(settings.isUsaTitoliColonna());
            this.getCampoNumeriRiga().setValore(settings.isUsaNumeriRiga());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Seleziona o crea un file per l'export.
     * <p/>
     */
    private void selezionaFile() {
        /* variabili e costanti locali di lavoro */
        File file;
        String path;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* seleziona il file di uscita */
            file = LibFile.creaFile();
            continua = (file != null);

            /* registra il percorso */
            if (continua) {
                path = file.getPath();
                this.setFile(path);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Assegna un percorso per il file di uscita.
     * <p/>
     *
     * @param path il percorso
     */
    private void setFile(String path) {
        /* variabili e costanti locali di lavoro */
        Campo campoFile;

        try {    // prova ad eseguire il codice
            campoFile = this.getCampoFile();
            campoFile.setValore(path);
            campoFile.getCampoLogica().memoriaGui();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il percorso del file di uscita.
     * <p/>
     *
     * @return il percorso del file di uscita.
     */
    public String getPath() {
        return this.getCampoFile().getString();
    }


    /**
     * Ritorna il formato di esportazione.
     * <p/>
     *
     * @return il percorso del file di uscita.
     */
    public ImportExport.ExportFormats getFormato() {
        /* variabili e costanti locali di lavoro */
        ImportExport.ExportFormats formato = null;
        boolean continua;
        Object ogg;

        try { // prova ad eseguire il codice

            /* recupera il valore selezionato */
            ogg = this.getCampoFormato().getValoreElenco();
            continua = (ogg != null);

            /* recupera l'oggetto specifico */
            if (continua) {
                if (ogg instanceof ImportExport.ExportFormats) {
                    formato = (ImportExport.ExportFormats)ogg;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return formato;
    }


    /**
     * Ritorna l'uso dei titoli di colonna.
     * <p/>
     *
     * @return l'uso dei titoli di colonna.
     */
    public boolean isUsaTitoliColonna() {
        return this.getCampoTitoliColonna().getBool();
    }


    /**
     * Ritorna l'uso dei numeri di riga.
     * <p/>
     *
     * @return l'uso dei numeri di riga.
     */
    public boolean isUsaNumeriRiga() {
        return this.getCampoNumeriRiga().getBool();
    }


    /**
     * Controlla se questo oggetto contiene impostazioni
     * valide e sufficienti per effettuare una esportazione.
     * <p/>
     *
     * @return true se è valido
     */
    public boolean isValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        String stringa;
        Object valore;

        try {    // prova ad eseguire il codice

            /* nome del file */
            stringa = this.getPath();
            valido = Lib.Testo.isValida(stringa);

            /* formato di esportazione */
            if (valido) {
                valore = this.getFormato();
                valido = (valore != null);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Invocato quando si modifica il popup formato.
     * <p/>
     * Se c'è il nome del file:
     * - se non ha estensione aggiunge quella appropriata
     * - se ha estensione la cambia con quella appropriata
     */
    private void popFormatoModificato() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String path;
        ImportExport.ExportFormats formato = null;
        String extDesiderata = "";
        String extCorrente;

        try {    // prova ad eseguire il codice

            /* esegue solo se c'è il nome dl file */
            path = this.getPath();
            continua = Lib.Testo.isValida(path);

            /* recupera il formato selezionato */
            if (continua) {
                formato = this.getFormato();
                continua = (formato != null);
            }// fine del blocco if

            /* recupera l'estensione desiderata */
            if (continua) {
                extDesiderata = formato.getEstensione();
                continua = Lib.Testo.isValida(extDesiderata);
            }// fine del blocco if

            /* controlla se il file ha già l'estensione e in tal caso la toglie */
            if (continua) {
                extCorrente = Lib.File.getEstensione(path);
                if (Lib.Testo.isValida(extCorrente)) {  // ha già estenzione, la leva
                    path = Lib.File.stripEstensione(path);
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge la nuova estensione desiderata e scrive il nuovo valore nel campo  */
            if (continua) {
                path += "." + extDesiderata;
                this.setFile(path);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    private Campo getCampoTitoliColonna() {
        return campoTitoliColonna;
    }


    private void setCampoTitoliColonna(Campo campoTitoliColonna) {
        this.campoTitoliColonna = campoTitoliColonna;
    }


    private Campo getCampoNumeriRiga() {
        return campoNumeriRiga;
    }


    private void setCampoNumeriRiga(Campo campoNumeriRiga) {
        this.campoNumeriRiga = campoNumeriRiga;
    }


    private Campo getCampoFormato() {
        return campoFormato;
    }


    private void setCampoFormato(Campo campoFormato) {
        this.campoFormato = campoFormato;
    }


    private Campo getCampoFile() {
        return campoFile;
    }


    private void setCampoFile(Campo campoFile) {
        this.campoFile = campoFile;
    }


    private ExportSettings getSettings() {
        return settings;
    }


    private void setSettings(ExportSettings settings) {
        this.settings = settings;
    }


    /**
     * Azione del bottone 'seleziona output file' </p>
     */
    private class AzSelezionaFile implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            selezionaFile();
        }
    } // fine della classe 'interna'

    /**
     * Azione popup formato modificato
     */
    private final class AzPopFormatoModificato extends CampoMemoriaAz {

        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            popFormatoModificato();
        }
    } // fine della classe 'interna'

}// fine della classe
