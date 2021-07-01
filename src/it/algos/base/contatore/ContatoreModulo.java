/**
 * Title:     ContatoreModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-dic-2006
 */
package it.algos.base.contatore;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.modulo.NodoModuloOggetto;
import it.algos.base.progetto.Progetto;

import java.util.ArrayList;

/**
 * ContatoreModulo - Contenitore dei riferimenti agli oggetti del package.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-dic-2006 ore 6.55.30
 */
public final class ContatoreModulo extends ModuloBase implements Contatore {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Contatore.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Contatore.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Contatore.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = ContatoreModulo.TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public ContatoreModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(ContatoreModulo.NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(ContatoreModulo.NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ContatoreModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(ContatoreModulo.NOME_CHIAVE, unNodo);

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
        /* selezione del modello (obbligatorio) */
        super.setModello(new ContatoreModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(ContatoreModulo.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(ContatoreModulo.TITOLO_MENU);

    }// fine del metodo inizia


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    @Override public boolean inizializza() {
        return super.inizializza();
    } // fine del metodo


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
    }// fine del metodo


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ContatoreModulo get() {
        return (ContatoreModulo)ModuloBase.get(ContatoreModulo.NOME_CHIAVE);
    }


    /**
     * Ritorna il campo Tavola.
     * <p/>
     *
     * @return il campo Tavola
     */
    public Campo getCampoTavola() {
        return this.getCampo(Cam.tavola.get());
    }


    /**
     * Ritorna il campo Prossimo ID.
     * <p/>
     *
     * @return il campo Prossimo ID
     */
    public Campo getCampoNextID() {
        return this.getCampo(Cam.nextID.get());
    }


    /**
     * Rilascia un numero di ID per un dato contatore.
     * <p/>
     * Aggiorna il contatore prima del rilascio.
     *
     * @param codCont l'id del contatore desiderato
     *
     * @return il prossimo ID per il contatore, 0 se non riuscito
     */
    public int releaseID(int codCont) {
        /* variabili e costanti locali di lavoro */
        int currID = 0;
        int nextID = 0;
        Campo campoID;
        boolean riuscito;

        try {    // prova ad eseguire il codice
            campoID = this.getCampoNextID();
            currID = this.query().valoreInt(campoID, codCont);
            if (currID > 0) {
                nextID = currID + 1;
                riuscito = this.query().registraRecordValore(codCont,
                        this.getCampoNextID(),
                        nextID);
                if (!riuscito) {
                    currID = 0;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return currID;
    }


    /**
     * Aggiunge un modulo Contatori all'albero sotto ad ogni
     * modulo che necessita di Contatori.
     * <p/>
     * L'istanza del modulo Contatori è una sola.
     * Il modulo è gia Preparato.
     * Il nodo può essere aggiunto in più di una posizione.
     * Il modulo Contatori è necessario se un modello usa
     * un inizializzatore di tipo InitContatore per un campo.
     */
    public void addAlbero() {
        /* variabili e costanti locali di lavoro */
        Progetto progetto;
        ArrayList<AlberoNodo> nodi;
        Object oggetto;
        Modulo modulo;
        Modello modello;
        NodoModuloOggetto oggettoNodo;

        try {    // prova ad eseguire il codice

            progetto = Progetto.getIstanza();
            nodi = progetto.getAlberoModuli().getNodi();
            for (AlberoNodo nodo : nodi) {
                oggetto = nodo.getUserObject();
                if (oggetto != null) {
                    if (oggetto instanceof NodoModuloOggetto) {
                        oggettoNodo = (NodoModuloOggetto)oggetto;
                        modulo = oggettoNodo.getModulo();

                        if (modulo != null) {
                            modello = modulo.getModello();
                            if (modello.isUsaContatori()) {
                                progetto.addModuloAlbero(this, nodo);
                            }// fine del blocco if
                        } else {
                            int a = 87;
                        }// fine del blocco if-else

                    }// fine del blocco if
                }// fine del blocco if

            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ContatoreModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}// fine della classe
