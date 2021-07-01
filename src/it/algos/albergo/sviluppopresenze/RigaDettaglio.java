/**
 * Title:     RigaDettaglio
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      13-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Wrapper per una riga della tabella dettagli
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 13-feb-2009 ore 9.14.26
 */
final class RigaDettaglio {

    /* testo della colonna "sigla" */
    private String sigla;

    /* testo della colonna "descrizione" */
    private String descrizione;

    /* numero di adulti */
    private int presAdulti;

    /* numero di bambini */
    private int presBambini;

    /* valore della riga */
    private double valore;

    /* percentuale di Presenze sul totale delle righe nella mappa */
    private double percPresenze;

    /* percentuale di Valore sul totale delle righe nella mappa */
    private double percValore;



    /**
     * elenco univoco dei codici di periodo che
     * hanno concorso a formare questa riga
     */
    private ArrayList<Integer> periodiOrigine;


    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param sudd sigla della Suddivisione
     * @param descrizione descrizione della Suddivisione
     * @param adulti numero di presenze adulti
     * @param bambini numero di presenze bambini
     * @param valore valore della riga
     */
    public RigaDettaglio(String sudd, String descrizione, int adulti, int bambini, double valore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setSigla(sudd);
        this.setDescrizione(descrizione);
        this.setPresAdulti(adulti);
        this.setPresBambini(bambini);
        this.setValore(valore);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param sudd sigla della Suddivisione
     * @param descrizione descrizione della Suddivisione
     */
    public RigaDettaglio(String sudd, String descrizione) {
        this(sudd,descrizione, 0,0,0);
    }// fine del metodo costruttore completo



    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* lista iniziale vuota */
        this.setPeriodiOrigine(new ArrayList<Integer>());
    }


    @Override
    public String toString() {
        return this.getSigla()+", "+this.getPresAdulti()+", "+this.getPresBambini()+", "+this.getValore();
    }


    /**
     * Aggiunge un numero di presenze Adulti.
     * <p/>
     * @param quante presenze da aggiungere
     */
    public void addPresAdulti(int quante) {
        this.setPresAdulti(this.getPresAdulti()+quante);
    }

    /**
     * Aggiunge un numero di presenze Bambini.
     * <p/>
     * @param quante presenze da aggiungere
     */
    public void addPresBambini(int quante) {
        this.setPresBambini(this.getPresBambini()+quante);
    }

    /**
     * Aggiunge un valore economico.
     * <p/>
     * @param valore importo da aggiungere
     */
    public void addValore(double valore) {
        this.setValore(this.getValore()+valore);
    }

    /**
     * Ritorna il numero di presenze totali.
     * <p/>
     * @return il numero di presenze totali (somma A+B)
     */
    public int getPresTotali() {
        return this.getPresAdulti()+this.getPresBambini();
    }


    /**
     * Controlla se tutti i valori della riga sono vuoti.
     * <p/>
     * @return true se tutti i valori della riga sono vuoti
     */
    public boolean isValoriVuoti() {
        /* variabili e costanti locali di lavoro */
        boolean vuoti=true;

        if (vuoti) {
            if (this.getPresAdulti()!=0) {
                vuoti=false;
            }// fine del blocco if
        }// fine del blocco if

        if (vuoti) {
            if (this.getPresBambini()!=0) {
                vuoti=false;
            }// fine del blocco if
        }// fine del blocco if

        if (vuoti) {
            if (this.getValore()!=0) {
                vuoti=false;
            }// fine del blocco if
        }// fine del blocco if

        /* valore di ritorno */
        return vuoti;
    }


    /**
     * Aggiunge un codice periodo ai codici di periodo di origine.
     * <p/>
     * Il codice viene aggiunto solo se non esiste già nella lista
     * @param codPeriodo il codice periodo da aggiungere
     */
    public void addCodPeriodo (int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> codici;

        try {    // prova ad eseguire il codice
            codici = this.getPeriodiOrigine();
            if (!codici.contains(codPeriodo)) {
                codici.add(codPeriodo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    public String getSigla() {
        return sigla;
    }


    private void setSigla(String sigla) {
        this.sigla = sigla;
    }


    public String getDescrizione() {
        return descrizione;
    }


    private void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public int getPresAdulti() {
        return presAdulti;
    }


    private void setPresAdulti(int presAdulti) {
        this.presAdulti = presAdulti;
    }


    public int getPresBambini() {
        return presBambini;
    }


    private void setPresBambini(int presBambini) {
        this.presBambini = presBambini;
    }


    public double getValore() {
        return valore;
    }


    private void setValore(double valore) {
        this.valore = valore;
    }


    public ArrayList<Integer> getPeriodiOrigine() {
        return periodiOrigine;
    }


    private void setPeriodiOrigine(ArrayList<Integer> periodiOrigine) {
        this.periodiOrigine = periodiOrigine;
    }


    public double getPercPresenze() {
        return percPresenze;
    }


    public void setPercPresenze(double percPresenze) {
        this.percPresenze = percPresenze;
    }


    public double getPercValore() {
        return percValore;
    }


    public void setPercValore(double percValore) {
        this.percValore = percValore;
    }
}// fine della classe
