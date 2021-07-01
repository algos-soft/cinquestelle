/**
 * Title:     FatturaNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-dic-2006
 */
package it.algos.gestione.fattura;

import com.wildcrest.j2printerworks.J2Printer;
import it.algos.base.azione.AzModulo;
import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;

import javax.print.PrintService;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.print.Pageable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Navigatore specifico per le fatture.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-feb-2005 ore 12.28.15
 */
public final class FattBaseNavigatore extends NavigatoreLS {

    /* Azione stampa fatture selezionate nella lista */
    private Azione azStampaFatture;

    /* bottone stampa fatture selezionate nella lista */
    private JButton bStampaFatture;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public FattBaseNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     */
    public FattBaseNavigatore(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.setNomeChiave("navigatorefattura");
            this.setUsaPannelloUnico(true);
            this.setUsaTransazioni(true);
            this.setNomeVista(FattBase.Vis.standard.toString());
            this.addSchedaCorrente(new FattBaseScheda(this.getModulo()));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        FattBaseModulo modulo;
        Azione azione;
        Filtro filtro;
        FattBase.TipoDoc tipoDoc;

        super.inizializza();

        try { // prova ad eseguire il codice

            modulo = (FattBaseModulo)this.getModulo();

            /* aggiunge e registra l'azione stampa fatture alla toolbar */
            azione = new AzStampaFatture(modulo);
            this.setAzStampaFatture(azione);
            this.addAzione(azione);

            /* regola il filtro base della lista */
            /* in funzione del tipo di documento (fattura/rf/ddt) */
            tipoDoc = modulo.getTipoDocumento();
            filtro = FiltroFactory.crea(FattBase.Cam.tipodoc.get(), tipoDoc.getValore());
            this.getLista().setFiltroBase(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizializza


    /**
     * Sincronizza lo stato del Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di ogni conseguenza (menu, bottoni, ecc) <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void sincronizza() {

        /* invoca il metodo sovrascritto della superclasse */
        super.sincronizza();

        try { // prova ad eseguire il codice
            this.abilitaBottoni();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Abilita i bottoni specifici di questo navigatore.
     * <p/>
     */
    private void abilitaBottoni() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        int quanti;
        boolean abilita = false;
        Azione azione;

        try {    // prova ad eseguire il codice

            /* abilita l'azione Stampa Fatture */
            azione = this.getAzStampaFatture();
            if (azione != null) {
                lista = this.getLista();
                quanti = lista.getQuanteRigheSelezionate();
                abilita = (quanti > 0);
                azione.setEnabled(abilita);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Stampa le fatture correntemente selezionate in lista.
     * <p/>
     */
    private void stampaSelezionate() {
        /* variabili e costanti locali di lavoro */
        int tipoSel;
        boolean continua = true;
        int[] chiavi;
        int chiave;
        int quante;
        Pageable pageable;
        PrinterJob pj = null;
        PrintService ps = null;
        FattBaseStampa stampa;
        boolean eseguiStampa = false;
        boolean confermate = false;
        DialogoStampa dialogo = null;
        FattBaseModello modello;
        boolean confermabile;
        String testo;
        int nonstampate = 0;
        PrintDevice device;
        J2Printer jPrinter = null;
        File dirPdf = null;
        String nomefile;
        String jobName;

        try {    // prova ad eseguire il codice

            /* controlla che il flag confermato sia omogeneo sulle fatture selezionate */
            chiavi = this.getLista().getChiaviSelezionate();
            quante = chiavi.length;
            tipoSel = this.checkConfermatoOmogeneo(chiavi);

            /* controlla il risultato */
            switch (tipoSel) {
                case -1: // non omogeneo
                    new MessaggioAvviso(
                            "Non è possibile stampare insieme fatture confermate e non confermate.");
                    continua = false;
                    break;
                case 0: // tutte non confermate
                    confermate = false;
                    break;
                case 1: // tutte confermate
                    confermate = true;
                    break;
                default: // caso non definito
                    continua = false;
                    break;
            } // fine del blocco switch

            /* presenta il dialogo specifico di preparazione stampa fatture */
            if (continua) {
                dialogo = new DialogoStampa(confermate);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            /* Se sono più di una, Presenta il dialogo nativo di stampa,
             * permette all'utente di modificarlo e memorizza il PrintService
             * eventualmente modificato da utilizzare per i successivi PrintJob */
            if (continua) {
                switch (dialogo.getDevice()) {
                    case printer:
                        /* se sono + di 1 registra le impostazioni per tutte */
                        if (quante > 1) {
                            pj = PrinterJob.getPrinterJob();
                            continua = pj.printDialog();
                            if (continua) {
                                ps = pj.getPrintService();
                            }// fine del blocco if
                        }// fine del blocco if
                        break;
                    case preview:
                        /* crea un J2Printer per il preview interno */
                        jPrinter = new J2Printer(Progetto.getPrintLicense());
                        break;
                    case pdf:
                        /* seleziona una cartella */
                        dirPdf = LibFile.getDir("Seleziona la cartella per i file .pdf");
                        continua = dirPdf != null;
                        /* crea un J2Printer per generare i pdf */
                        if (continua) {
                            jPrinter = new J2Printer(Progetto.getPrintLicense());
                        }// fine del blocco if
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            }// fine del blocco if

            /* stampa una ad una */
            if (continua) {
                for (int k = 0; k < quante; k++) {
                    chiave = chiavi[k];

                    /* se definitivo, e non confermata, tenta di confermare la fattura */
                    eseguiStampa = true;
                    if (!confermate) {
                        if (dialogo.isDefinitivo()) {
                            modello = this.getModello();
                            confermabile = modello.isConfermabile(chiave);
                            if (confermabile) {
                                this.confermaFattura(chiave);
                            } else {
                                eseguiStampa = false;
                                if (quante == 1) { // una sola
                                    testo = "Impossibile confermare la fattura.\n";
                                    testo += modello.checkConfermabile(chiave);
                                    new MessaggioAvviso(testo);
                                } else { // tante
                                    nonstampate++;
                                }// fine del blocco if-else
                            }// fine del blocco if-else
                        }// fine del blocco if
                    }// fine del blocco if

                    /* esegue la stampa */
                    if (eseguiStampa) {

                        stampa = this.getStampa();
                        pageable = stampa.creaPageable(chiave);
                        jobName = this.getPrintJobName(chiave);

                        device = dialogo.getDevice();
                        switch (device) {
                            case printer: // uscita su stampante
                                pj = PrinterJob.getPrinterJob();
                                pj.setJobName(jobName);
                                pj.setPageable(pageable);
                                if (quante == 1) {
                                    continua = pj.printDialog();
                                } else {
                                    pj.setPrintService(ps);
                                }// fine del blocco if-else
                                if (continua) {
                                    pj.print();
                                }// fine del blocco if
                                break;

                            case preview:  // uscita su preview
                                jPrinter.setPageable(pageable);
                                jPrinter.showPrintPreviewDialog(pageable);
                                break;

                            case pdf:    // uscita su pdf
                                nomefile = dirPdf + "/" + jobName + ".pdf";
                                jPrinter.setPageable(pageable);
                                jPrinter.printToPDF(nomefile);
                                break;

                            default: // caso non definito
                                break;
                        } // fine del blocco switch

                    }// fine del blocco if


                } // fine del ciclo for

                /* messaggio finale se era una stampa multipla e alcune non sono
                 * state stampate */
                if (nonstampate > 0) {
                    testo = "Attenzione!\n";
                    testo += nonstampate + " fatture non sono state stampate perché ";
                    testo += "non sono confermabili.\n";
                    testo += "Per maggiori informazioni prova a stamparle singolarmente.";
                    new MessaggioAvviso(testo);
                }// fine del blocco if

                /* se ha stampato le non confermate, aggiorna la
                 * lista perché è cambiato il flag Confermata */
                if (!confermate) {
                    this.aggiornaLista();
                }// fine del blocco if

                /* messaggio di stampa terminata, solo se erano più di una */
                if (quante > 1) {
                    new MessaggioAvviso("Stampa terminata.");
                }// fine del blocco if


            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il modello specifico delle fatture.
     * <p/>
     *
     * @return il modello specifico
     */
    private FattBaseModello getModello() {
        /* variabili e costanti locali di lavoro */
        FattBaseModello modello = null;

        try {    // prova ad eseguire il codice
            modello = (FattBaseModello)this.getModulo().getModello();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modello;
    }


    /**
     * Conferma una fattura senza effettuare controlli.
     * <p/>
     */
    private void confermaFattura(int chiave) {
        try {    // prova ad eseguire il codice
            this.query().registraRecordValore(chiave, FattBase.Cam.confermato.get(), true);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla che tutte le fatture da stampare abbiano
     * la stessa impostazione del flag Confermato.
     * <p/>
     *
     * @param chiavi delle fatture da controllare
     *
     * @return -1 se hanno impostazioni diverse,
     *         0 se sono tutte non confermate,
     *         1 se sono tutte confermate
     */
    private int checkConfermatoOmogeneo(int[] chiavi) {
        /* variabili e costanti locali di lavoro */
        int risultato = -1;
        Modulo modFat;
        Campo campo;
        Filtro filtro = null;
        ArrayList valori;
        boolean bool;
        boolean firstBool = false;
        boolean misti = false;
        Object valore;

        try {    // prova ad eseguire il codice
            modFat = this.getModulo();
            campo = modFat.getCampoChiave();
            filtro = FiltroFactory.elenco(campo, chiavi);
            valori = this.query().valoriCampo(FattBase.Cam.confermato.get(), filtro);
            for (int k = 0; k < valori.size(); k++) {
                valore = valori.get(k);
                if (valore instanceof Boolean) {
                    bool = (Boolean)valore;
                    if (k == 0) {
                        firstBool = bool;
                    } else {
                        if (bool != firstBool) {
                            misti = true;
                            break;
                        }// fine del blocco if
                    }// fine del blocco if-else
                }// fine del blocco if
            } // fine del ciclo for

            /* regola il risultato */
            if (misti) {
                risultato = -1;
            } else {
                if (firstBool) {
                    risultato = 1;
                } else {
                    risultato = 0;
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }


    /**
     * Ritorna il job name per la stampa di una fattura.
     * <p/>
     *
     * @param codice della fattura
     *
     * @return il job name per la stampa
     */
    private String getPrintJobName(int codice) {
        /* variabili e costanti locali di lavoro */
        String jobName = "";
        Query query;
        Filtro filtro;
        Modulo modFat;
        Modulo modAnag;
        Campo campoNum;
        Campo campoData;
        Campo campoSoggetto;
        int numero;
        Date data;
        String soggetto;
        Dati dati;

        try {    // prova ad eseguire il codice
            modFat = this.getModulo();
            modAnag = AnagraficaModulo.get();
            campoNum = modFat.getCampo(FattBase.Cam.numeroDoc.get());
            campoData = modFat.getCampo(FattBase.Cam.dataDoc.get());
            campoSoggetto = modAnag.getCampo(Anagrafica.Cam.soggetto.get());
            query = new QuerySelezione(modFat);
            filtro = FiltroFactory.codice(modFat, codice);
            query.setFiltro(filtro);
            query.addCampo(campoNum);
            query.addCampo(campoData);
            query.addCampo(campoSoggetto);
            dati = this.query().querySelezione(query);
            numero = dati.getIntAt(0, campoNum);
            data = dati.getDataAt(0, campoData);
            soggetto = dati.getStringAt(0, campoSoggetto);
            dati.close();

            jobName = "Fattura ";
            jobName += numero;
            jobName += " del ";
            jobName += Lib.Data.getStringa(data);
            jobName += " " + soggetto;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jobName;
    }


    private Azione getAzStampaFatture() {
        return azStampaFatture;
    }


    private void setAzStampaFatture(Azione azStampaFatture) {
        this.azStampaFatture = azStampaFatture;
    }


    private JButton getbStampaFatture() {
        return bStampaFatture;
    }


    private void setbStampaFatture(JButton bStampaFatture) {
        this.bStampaFatture = bStampaFatture;
    }


    /**
     * Ritorna l'oggetto delegato alla stampa della
     * singola fattura.
     * <p/>
     *
     * @return l'oggetto delegato alla stampa
     */
    private FattBaseStampa getStampa() {
        /* variabili e costanti locali di lavoro */
        FattBaseStampa stampa = null;
        FattBaseModulo modulo;

        try { // prova ad eseguire il codice
            modulo = (FattBaseModulo)this.getModulo();
            stampa = modulo.getStampa();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stampa;
    }


    /**
     * Invocata quando si preme il bottone Stampa Fatture nella lista.
     * <p/>
     */
    private class AzStampaFattureOld extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            stampaSelezionate();
        }
    }


    /**
     * Stampa le fatture correntemente selezionate in lista.
     * <p/>
     * Invocata quando si preme il bottone Stampa Fatture nella lista.
     */
    private final class AzStampaFatture extends AzModulo {

        /**
         * Costruttore completo.
         * <p/>
         *
         * @param modulo di riferimento
         */
        public AzStampaFatture(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo);

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
                super.setUsoLista(true);
                super.setIconaMedia("StampaFattura24");
                super.setTooltip("Stampa le fatture selezionate");
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                stampaSelezionate();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'interna'


    /**
     * Classe 'interna'. </p>
     */
    public final class DialogoStampa extends DialogoAnnullaConferma {

        private boolean confermate;

        private static final String NOME_DEF = "def";

        private static final String NOME_OUT = "oputput";


        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param confermate true per fatture confermate false per non confermate
         */
        public DialogoStampa(boolean confermate) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.confermate = confermate;

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
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
            String titolo;
            Campo campoDef;
            Campo campoOutput;

            try { // prova ad eseguire il codice

                /* regola il voce */
                if (this.confermate) {
                    titolo = "Stampa fatture confermate";
                } else {
                    titolo = "Stampa fatture non confermate";
                }// fine del blocco if-else
                this.setTitolo(titolo);

                /* se non sono confermate, opzione provvisiorio / definitivo */
                if (!this.confermate) {
                    campoDef = CampoFactory.radioInterno(NOME_DEF);
                    campoDef.decora().etichetta("Tipo di stampa");
                    campoDef.setValoriInterni("Definitivo,Provvisorio");
                    this.addCampo(campoDef);
                }// fine del blocco if

                /* opzione di uscita */
                campoOutput = CampoFactory.radioInterno(NOME_OUT);
                campoOutput.decora().etichetta("Uscita su:");
                campoOutput.setValoriInterni(PrintDevice.getLista());
                this.addCampo(campoOutput);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        public void inizializza() {
            super.inizializza();
            this.setValore(NOME_DEF, 0);
            this.setValore(NOME_OUT, 1);
        } /* fine del metodo */


        /**
         * Ritorna l'opzione definitivo/provvisorio.
         * <p/>
         *
         * @return true se è stata scelta l'opzione Definitivo, Fase se Provvisorio
         */
        public boolean isDefinitivo() {
            /* variabili e costanti locali di lavoro */
            boolean definitivo = false;
            Object valore;
            int scelta;

            try {    // prova ad eseguire il codice
                valore = this.getValore(NOME_DEF);
                scelta = Libreria.getInt(valore);
                if (scelta == 1) {
                    definitivo = true;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return definitivo;
        }


        /**
         * Controlla se il dialogo è confermabile
         * <p/>
         * Se si stanno stampando le non confermate, bisogna aver
         * scelto se provvisoria o definitiva.
         */
        public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile = false;
            int valOpzione;

            try { // prova ad eseguire il codice
                confermabile = super.isConfermabile();
                if (confermabile) {
                    if (!this.confermate) {
                        valOpzione = Libreria.getInt(this.getValore(NOME_DEF));
                        confermabile = valOpzione != 0;
                    }// fine del blocco if
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;
        }


        /**
         * Ritorna il device di uscita selezionato.
         * <p/>
         *
         * @return il device di uscita
         */
        public PrintDevice getDevice() {
            /* variabili e costanti locali di lavoro */
            PrintDevice device = null;
            int valOpzione;

            try {    // prova ad eseguire il codice
                valOpzione = Libreria.getInt(this.getValore(NOME_OUT));
                device = PrintDevice.getElemento(valOpzione);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return device;
        }


    } // fine della classe 'interna'


    /**
     * Classe interna Enumerazione
     * <p/>
     * Elenco di possibili valori per l'opzione uscita di stampa
     */
    enum PrintDevice {

        printer("stampante"),
        preview("anteprima"),
        pdf("pdf"),;

        /**
         * descrizione dell'elemento
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         *
         * @param descrizione dell'elemento
         */
        PrintDevice(String descrizione) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setDescrizione(descrizione);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        /**
         * Ritorna la lista degli elementi della enum
         * <p/>
         *
         * @return la lista degli elementi
         */
        public static ArrayList<PrintDevice> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrintDevice> lista = new ArrayList<PrintDevice>();

            try { // prova ad eseguire il codice
                for (PrintDevice unElem : values()) {
                    lista.add(unElem);
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna un elemento della enum dal valore
         * <p/>
         *
         * @param valore dell'elemento
         *
         * @return l'elemento corrispondente
         */
        public static PrintDevice getElemento(int valore) {
            /* variabili e costanti locali di lavoro */
            PrintDevice elem = null;
            int pos;

            try { // prova ad eseguire il codice
                pos = valore - 1;
                for (PrintDevice unElem : values()) {
                    if (unElem.ordinal() == pos) {
                        elem = unElem;
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return elem;
        }

//            /**
//             * Ritorna l'elemento di default
//             * <p/>
//             *
//             * @return l'elemento di default
//             */
//            public static ValoriOpzione getDefault() {
//                return standard;
//            }
//

//            /**
//             * Ritorna il valore dell'elemento di default
//             * <p/>
//             *
//             * @return il valore dell'elemento di default
//             */
//            public static int getValoreDefault() {
//                return getDefault().ordinal() + 1;
//            }


        public String toString() {
            return this.getDescrizione();
        }

    }// fine della enum

}// fine della classe
