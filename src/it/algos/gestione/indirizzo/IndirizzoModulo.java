/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.via.Via;
import it.algos.gestione.indirizzo.tabelle.via.ViaModulo;

/**
 * Indirizzo - Contenitore dei riferimenti agli oggetti del package.
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
 * @version 1.0 / 3-4-05
 */
public class IndirizzoModulo extends ModuloBase implements Indirizzo {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Indirizzo.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Indirizzo.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Indirizzo.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public IndirizzoModulo() {
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
    public IndirizzoModulo(AlberoNodo unNodo) {
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
     * Costruttore completo <br>
     *
     * @param unNomeModulo nome interno del modulo
     * @param unNodo nodo dell'albero moduli
     */
    public IndirizzoModulo(String unNomeModulo, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(unNomeModulo, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new IndirizzoModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);

//        /* selezione della scheda (facoltativo) */
//        super.addScheda(new IndirizzoViaScheda(this));

        this.setSchedaPop(new IndirizzoViaScheda(this));
    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* navigatore per gli indirizzi nella scheda anagrafica */
            nav = new NavInAnagrafica(this);
            this.addNavigatore(nav, Nav.indirizziAnagrafica.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void regolaNavigatori() {
        this.getNavigatoreDefault().setUsaPreferito(false);
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new ViaModulo());
            super.creaModulo(new CittaModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(Citta.NOME_MODULO);
            super.addModuloVisibile(Via.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'indirizzo della sede principale di una anagrafica.
     * <p/>
     * Se Privato, ritorna la residenza
     * Se Società, ritorna la sede legale
     *
     * @param codAnag dell'anagrafica
     *
     * @return il codice di record dell'indirizzo principale,
     *         0 se non ha indirizzo principale
     */
    public int getIndirizzoPrincipale(int codAnag) {
        /* variabili e costanti locali di lavoro */
        int codInd = 0;
        Modulo modAnag;
        int codTipo;
        Anagrafica.Tipi tipo;
        boolean privato = false;
        TipiSede tipoPrincipale;
        Filtro filtroAnag;
        Filtro filtroTipo;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modAnag = AnagraficaModulo.get();
            codTipo = modAnag.query().valoreInt(Anagrafica.Cam.privatosocieta.get(), codAnag);
            if (codTipo != 0) {
                tipo = Anagrafica.Tipi.getTipo(codTipo);
                switch (tipo) {
                    case privato:
                        privato = true;
                        break;
                    case societa:
                        privato = false;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                tipoPrincipale = TipiSede.getPrincipale(privato);
                filtroAnag = FiltroFactory.crea(Cam.anagrafica.get(), codAnag);
                filtroTipo = tipoPrincipale.getFiltro();
                filtro = new Filtro();
                filtro.add(filtroAnag);
                filtro.add(filtroTipo);
                codInd = this.query().valoreChiave(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codInd;
    }


    /**
     * Ritorna l'etichetta documento relativa a un indirizzo.
     * <p/>
     *
     * @param codice dell'indirizzo
     *
     * @return l'etichetta documento
     */
    public String getEtichettaDocumento(int codice) {
        /* variabili e costanti locali di lavoro */
        String etichetta = "";
        try {    // prova ad eseguire il codice
            // todo da implementare
            etichetta += "Spettabile\n";
            etichetta += "Acme spa\n";
            etichetta += "Via Soderini 15\n";
            etichetta += "20100 Milano";
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return etichetta;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static IndirizzoModulo get() {
        return (IndirizzoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new IndirizzoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
