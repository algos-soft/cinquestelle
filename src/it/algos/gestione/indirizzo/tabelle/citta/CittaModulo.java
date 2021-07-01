/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo.tabelle.citta;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.relazione.Relazione;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;
import it.algos.gestione.indirizzo.tabelle.regione.Regione;

import java.util.ArrayList;

/**
 * Citta - Contenitore dei riferimenti agli oggetti del package.
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
public class CittaModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Citta.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Citta.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Citta.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public CittaModulo() {
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
    public CittaModulo(AlberoNodo unNodo) {
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
    public CittaModulo(String unNomeModulo, AlberoNodo unNodo) {
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
        super.setModello(new CittaModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);

        this.addNavigatoreCorrente(new CittaNavigatore(this));

//        /* utilizzo di un solo pannello nel navigatore */
//        this.getNavigatoreDefault().setUsaPannelloUnico(true);

        /* sostituisce la scheda nel navigatore di default */
        this.addSchedaNavCorrente(new CittaScheda(this));

        /* assegna la scheda popup */
        this.setSchedaPop(new CittaScheda(this));
    }


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
    public boolean inizializza() {
        return super.inizializza();
    } // fine del metodo


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

//            /* crea il navigatore default */
//            nav = new CittaNavigatore(this);
//            this.addNavigatoreCorrente(nav);

            nav = NavigatoreFactory.listaNoTool(this);
            nav.setNomeVista(Citta.Vis.citta.toString());
            this.addNavigatore(nav, Citta.Nav.citta.toString());

            /* crea il navigatore per il dialogo di riclassificazione */
            nav = NavigatoreFactory.listaNoTool(this);
            nav.setUsaStatusBarLista(false);
            nav.setUsaFinestra(false);
            nav.getLista().getTavola().setRowSelectionAllowed(false);
            nav.getLista().getTavola().setCellSelectionEnabled(false);
            nav.setRigheLista(10);
            nav.setNomeVista(Citta.Vis.riclassificazione.toString());
            this.addNavigatore(nav, Citta.Nav.riclassificazione.toString());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
        try { // prova ad eseguire il codice
            super.creaModulo(new ProvinciaModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
        try { // prova ad eseguire il codice
            super.addModuloVisibile(Provincia.NOME_MODULO);
            super.addModuloVisibile(Regione.NOME_MODULO);
            super.addModuloVisibile(Nazione.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Crea eventuali azioni specifiche del modulo.
     * <p/>
     * Le azioni vengono aggiunte al navigatore corrente <br>
     * Le azioni vengono aggiunte alla
     * toolbar della lista oppure al menu Archivio <br>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void creaAzioni() {
        try { // prova ad eseguire il codice
            super.addAzione(new CittaAzImport(this), Navigatore.Tool.menu);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Prima istallazione.
     * <p/>
     * Metodo invocato da Progetto.lancia() <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * <p/>
     * Esegue il metodo iniziale di istallazione per ogni modulo <br>
     */
    @Override
    public void setup() {
        try { // prova ad eseguire il codice
            new CittaImport(this, false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esegue la riclassificazione di un elenco di città verso un'altra città.
     * <p/>
     * Modifica i riferimenti in tutte le tavole collegate a Città
     * Elimina le città da riclassificare
     * Opera sotto transazione
     *
     * @param daSostituire elenco dei codici delle città da sostituire
     * @param sostitutiva codice della città sostitutiva
     *
     * @return true se riuscito
     */
    public static boolean riclassifica(int[] daSostituire, int sostitutiva) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Modulo modCitta;
        ArrayList<Relazione> relazioni;
        Campo campo;
        Campo campoCodCitta;
        ArrayList<Campo> campiLinkati;
        int sostituire;
        Modulo modLinkato;
        int[] codiciLinkati;
        Connessione conn;
        Filtro filtro;
        int codRecLinkato;

        try {    // prova ad eseguire il codice

            modCitta = CittaModulo.get();

            /* crea una lista di tutti i campi del progetto linkati al codice città */
            campoCodCitta = modCitta.getCampoChiave();
            campiLinkati = new ArrayList<Campo>();
            relazioni = Progetto.getTavolaRelazioni();
            for (Relazione rel : relazioni) {
                campo = rel.getCampoArrivo();
                if (campo.equals(campoCodCitta)) {
                    campiLinkati.add(rel.getCampoPartenza());
                }// fine del blocco if
            }

            /* recupera la connessione e apre una transazione */
            conn = modCitta.getConnessione();
            conn.startTransaction();

            /* sostituisce le città una per una */
            for (int k = 0; k < daSostituire.length; k++) {

                sostituire = daSostituire[k];

                /* modifica i riferimenti su tutti i campi linkati */
                for (Campo campoLink : campiLinkati) {
                    modLinkato = campoLink.getModulo();
                    filtro = FiltroFactory.crea(campoLink, sostituire);
                    codiciLinkati = modLinkato.query().valoriChiave(filtro, conn);

                    for (int j = 0; j < codiciLinkati.length; j++) {
                        codRecLinkato = codiciLinkati[j];
                        riuscito = modLinkato.query().registra(codRecLinkato,
                                campoLink.getNomeInterno(),
                                sostitutiva,
                                conn);
                        if (!riuscito) {
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for
                } // fine del ciclo for

                /* elimina la città sostituita */
                if (riuscito) {
                    riuscito = modCitta.query().eliminaRecord(sostituire, conn);
                }// fine del blocco if

                /* se non riesce forza l'uscita dal ciclo */
                if (!riuscito) {
                    break;
                }// fine del blocco if


            } // fine del ciclo for

            /* conclude la transazione */
            if (riuscito) {
                conn.commit();
            } else {
                conn.rollback();
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static CittaModulo get() {
        return (CittaModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new CittaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


} // fine della classe
