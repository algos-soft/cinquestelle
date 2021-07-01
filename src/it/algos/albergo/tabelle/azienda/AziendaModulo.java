/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.tabelle.azienda;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;

/**
 * Azienda - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
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
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public final class AziendaModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Azienda.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Azienda.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Azienda.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public AziendaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public AziendaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new AziendaModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);

    }


    /**
     * Ritorna la località di una azienda.
     * <p/>
     * @param codAzienda codice della azienda
     * @return il comune della azienda
     */
    public static String getLocalita (int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Object ogg;
        String stringa  = "";

        try {    // prova ad eseguire il codice
            ogg = AziendaModulo.getValoreCampo(Azienda.Cam.localita, codAzienda);
            stringa = Libreria.getString(ogg);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }

    /**
     * Ritorna il cap di una azienda.
     * <p/>
     * @param codAzienda codice della azienda
     * @return il cap della azienda
     */
    public static String getCap (int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Object ogg;
        String stringa  = "";

        try {    // prova ad eseguire il codice
            ogg = AziendaModulo.getValoreCampo(Azienda.Cam.cap, codAzienda);
            stringa = Libreria.getString(ogg);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la ragione sociale una azienda.
     * <p/>
     * @param codAzienda codice della azienda
     * @return la ragione sociale della azienda
     */
    public static String getRagioneSociale (int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Object ogg;
        String stringa  = "";

        try {    // prova ad eseguire il codice
            ogg = AziendaModulo.getValoreCampo(Azienda.Cam.descrizione, codAzienda);
            stringa = Libreria.getString(ogg);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il numero di stelle di una azienda.
     * <p/>
     * @param codAzienda codice della azienda
     * @return il numero di stelle della azienda
     */
    public static int getNumeroStelle (int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Object ogg;
        int numero=0;

        try {    // prova ad eseguire il codice
            ogg = AziendaModulo.getValoreCampo(Azienda.Cam.numStelle, codAzienda);
            numero = Libreria.getInt(ogg);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }





    /**
     * Ritorna il valore di un campo di una azienda.
     * <p/>
     * @param campo richiesto
     * @param codAzienda codice della azienda
     * @return l'oggetto valore richiesto
     */
    private static Object getValoreCampo (Azienda.Cam campo, int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Object oggetto=null;
        Modulo modAzienda;

        try {    // prova ad eseguire il codice
            modAzienda = AziendaModulo.get();
            oggetto = modAzienda.query().valoreCampo(campo.get(), codAzienda);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oggetto;
    }


    /**
     * Recupera il codice della azienda principale.
     * <p/>
     * Esiste sempre una e una sola azienda principale
     * Corrisponde al record preferito del modulo Azienda
     * @return il codice della azienda principale
     */
    public static int getCodAziendaPrincipale() {
        return AziendaModulo.get().getRecordPreferito();
    }





    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static AziendaModulo get() {
        return (AziendaModulo)ModuloBase.get(AziendaModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new AziendaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
