/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      20-feb-2004
 */
package it.algos.albergo.ristorante.lingua;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;

/**
 * Lingua - Contenitore dei riferimenti agli oggetti del package.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 20-feb-2004 ore 14.10.28
 */
public final class LinguaModulo extends ModuloBase {

    /**
     * nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Lingua.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Lingua.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Lingua.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;

    /**
     * codice chiave della lingua principale.
     * <p/>
     * letto dal db all'avvio del programma.
     * per cambiarlo bisogna riavviare.
     * Uso una variabile di istanza per non leggere continuamente
     * dal db un dato che e' sempre lo stesso
     * (E' usato dal RendererPiattoContorno!)
     */
    private int chiaveLinguaPrincipale = 0;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public LinguaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public LinguaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

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
        /* selezione del modello (obbligatorio) */
        super.setModello(new LinguaModello());

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* selezione della scheda (facoltativo) */
        super.addSchedaNavCorrente(new LinguaScheda(this));

        /* crea e assegna un gestore per la logica specifica */
        this.setLogicaSpecifica(new LinguaLogica(this));

        this.setIcona("About24", Azione.ICONA_MEDIA, false);


    }// fine del metodo inizia


    /**
     * Inizializza il modulo.
     * <p/>
     */
    public boolean inizializza() {
        super.inizializza();
        this.regolaChiaveLinguaPrincipale();
        return true;
    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Regola il Navigatore di default e crea altri Navigatori.
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* modifica i bottoni della toolbar della lista */
            nav = this.getNavigatoreDefault();
            nav.setUsaFrecceSpostaOrdineLista(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna i nomi dei pasti nella lingua principale.
     * <p/>
     * Invocato da MenuModello.radioMetodo (campo Pasto)<br>
     *
     * @return un array di valori testo
     */
    public Object[] getNomiPasto() {
        /* variabili e costanti locali di lavoro */
        Object[] ritorno = null;
        int codRecord = 0;

        /* codice della lingua principale */
        codRecord = 1;

        try {    // prova ad eseguire il codice

            ritorno = new Object[3];

            ritorno[0] = this.query().valoreCampo(Lingua.CAMPO_COLAZIONE, codRecord);
            ritorno[1] = this.query().valoreCampo(Lingua.CAMPO_PRANZO, codRecord);
            ritorno[2] = this.query().valoreCampo(Lingua.CAMPO_CENA, codRecord);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ritorno;
    }


    /**
     * Restituisce la chiave della lingua principale.
     * <p/>
     *
     * @return la chiave della lingua principale nella tavola Lingua.
     */
    public int getChiaveLinguaPrincipale() {
        return this.chiaveLinguaPrincipale;
    }


    /**
     * Regola la chiave della lingua principale.
     * <p/>
     *
     * @param chiave la chiave della lingua principale nella tavola Lingua.
     */
    private void setChiaveLinguaPrincipale(int chiave) {
        this.chiaveLinguaPrincipale = chiave;
    }


    /**
     * Regola la chiave della lingua principale.
     * <p/>
     * Legge la chiave dal db e la scrive nella variabile di istanza
     */
    private void regolaChiaveLinguaPrincipale() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Filtro filtro = null;
        String nomeLinguaMain = "";
        String nomeCampoChiave = null;
        ArrayList valori = null;
        Object valore = null;

        try {    // prova ad eseguire il codice
            nomeLinguaMain = Lingua.LINGUA[Lingua.LINGUA_PRINCIPALE];
            filtro = FiltroFactory.crea(Lingua.CAMPO_NOME, nomeLinguaMain);
            nomeCampoChiave = this.getCampoChiave().getNomeInterno();
            valori = this.query().valoriCampo(nomeCampoChiave, filtro);
            if (valori.size() > 0) {
                valore = valori.get(0);
                if (valore instanceof Integer) {
                    codice = ((Integer)valore).intValue();
                    this.setChiaveLinguaPrincipale(codice);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la logica specifica del modulo.
     * <p/>
     *
     * @return la logica specifica del modulo
     */
    public LinguaLogica getLogica() {
        return (LinguaLogica)this.getLogicaSpecifica();
    }


    /**
     * Restituisce il codice record della lingua preferita.
     * <p/>
     *
     * @return il codice record della lingua preferita
     */
    public static int getCodLinguaPreferita() {
        return LinguaModulo.get().getRecordPreferito();
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static LinguaModulo get() {
        return (LinguaModulo)ModuloBase.get(LinguaModulo.NOME_CHIAVE);
    }


    /**
     * Main method
     * </p>
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new LinguaModulo();

        /*
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    }// fine del metodo main

}// fine della classe
