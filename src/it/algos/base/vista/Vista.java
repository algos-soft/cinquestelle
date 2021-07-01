/**
 * Title:        Vista.java
 * Package:      it.algos.base.wrapper
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 22 luglio 2003 alle 11.37
 */

package it.algos.base.vista;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.Campi;

import java.awt.*;
import java.util.ArrayList;

/**
 * Questa classe concreta e' responsabile di: <br>
 * Definire un modello di dati per una Vista <br>
 * Una Vista rappresenta una collezione ordinata di oggetti VistaElemento<br>
 * In fase di creazione della Vista tali oggetti possono essere:
 * - nomi di campo, campi, nomi di vista, viste.
 * In fase di inizializzazione tutti gli elementi vengono risolti a Campo.
 * Dopo la inizializzazione, la Vista contiene solo elementi di tipo Campo.
 * Le Viste rappresentano il modello dati per la creazione delle Liste.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 luglio 2003 ore 11.37
 */
public final class Vista extends Object {

    /**
     * codifica tipo elemento Nome Interno Campo del modulo della Vista
     */
    public static final int NOME_CAMPO = 1;

    /**
     * codifica tipo elemento Campo
     */
    public static final int CAMPO = 2;

    /**
     * codifica tipo elemento Nome di Vista
     */
    public static final int NOME_VISTA = 3;

    /**
     * codifica tipo elemento Vista
     */
    public static final int VISTA = 4;

    /**
     * Riferimento al Modulo proprietario di questa Vista
     */
    private Modulo modulo = null;

    /**
     * Nome chiave di questa Vista
     */
    private String nomeVista = null;

    /**
     * Elenco di Elementi di questa Vista.<br>
     * Oggetti di tipo VistaElemento.<br>
     * un Elemento puo' contenere contiene oggetti di tipo:
     * - Nome Campo (di questo Modulo),<br>
     * - Campo<br>
     * - Nome Vista(di questo modulo)<br>
     * - Vista<br>
     * Viene risolto in un elenco di soli oggetti Campo in fase
     * di inizializzazione.
     */
    private ArrayList<VistaElemento> elementi = null;

    /**
     * dimensione di default di una lista (che usa questa vista)
     */
    public static final Dimension DIMENSIONE = new Dimension(423, 500);

    /**
     * dimensione corrente della lista (che usa questa vista)
     */
    private Dimension dimensione = null;

    /**
     * campo di ordinamento di default della Lista associata a questa Vista
     * (utilizzato sempre alla prima apertura della Lista)
     */
    private Campo unCampoOrdinamento = null;

    /**
     * Flag - indica se la Vista e' stata inizializzata
     */
    private boolean inizializzato = false;

    /**
     * flag - per mostrare i totali dei campi totalizzabili
     */
    private boolean mostraTotali = false;


    /**
     * Costruttore base senza parametri
     */
    public Vista() {
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo <br>
     *
     * @param unModulo il modulo al quale la Vista appartiene
     */
    public Vista(Modulo unModulo) {
        this("", unModulo);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo <br>
     *
     * @param unNome il nome di questa Vista
     * @param unModulo il modulo al quale la Vista appartiene
     */
    public Vista(String unNome, Modulo unModulo) {
        /** rimanda al costruttore della superclasse */
        super();

        try {                                   // prova ad eseguire il codice
            /* regola le variabili di istanza con i parametri */
            this.setNomeVista(unNome);
            this.setModulo(unModulo);

            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setElementi(new ArrayList<VistaElemento>());
        this.setDimensione(DIMENSIONE);
        this.setMostraTotali(true);
    } /* fine del metodo inizia */


    /**
     * Inizializzazione della Vista.<br>
     * Esegue solo se non gia' inizializzata<br>
     * Espande la Vista (risolve gli oggetti che non sono Campi
     * fino ad avere nella collezione solo oggetti Campo)<br>
     *
     * @return true se gia' inizializzata o inizializzata correttamente
     */
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;

        /* Esegue solo se non gia' inizializzato */
        if (!this.isInizializzato()) {

            /* regola il riferimento al Modulo per gli elementi
             * (solo per sicurezza, tutti gli elementi dovrebbero avere
             * il riferimento al proprio modulo fin dalla creazione) */
            this.regolaModuliElemento();

            /* espande la Vista */
            if (riuscito) {
                if (!this.espandi()) {
                    riuscito = false;
                }// fine del blocco if
            }// fine del blocco if

            /* clona i campi */
            if (riuscito) {
                if (!this.clonaContenuti()) {
                    riuscito = false;
                }// fine del blocco if
            }// fine del blocco if

            /* regola l'ordine di default */
            if (riuscito) {
                if (!this.regolaOrdineDefault()) {
                    riuscito = false;
                }// fine del blocco if
            }// fine del blocco if

            /* regola il flag inizializzato della Vista */
            if (riuscito) {
                this.setInizializzato(true);
            }// fine del blocco if

        }// fine del blocco if

        /* valore di ritorno */
        return riuscito;
    } /* fine del metodo */


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     */
    public void avvia() {
    }


    /**
     * Espansione della lista Elementi della Vista.
     * - Sostituisce i nomi di campo con campi del Modello
     * - Sostituisce ricorsivamente tutti i riferimenti a oggetti Vista
     * con i corrispondenti oggetti Campo.
     * - Sostituisce i campi linkati con eventuali viste associate.
     * Elabora fino ad avere solo oggetti Campo non ulteriormente espandibili
     *
     * @return true se la vista e' stata espansa correttamente
     */
    private boolean espandi() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        VistaElemento unElemento = null;
        ArrayList<VistaElemento> listaElementi = null;
        ArrayList<VistaElemento> elementi = null;
        ArrayList<VistaElemento> listaEspansa = null;
        boolean fineCiclo = false;
        String unTestoErrore = null;

        /* controllo anti-loop */
        int maxLoop = 1000;
        int countLoop = 0;

        try {    // prova ad eseguire il codice

            while (!fineCiclo) {

                /* controllo anti-loop */
                countLoop++;
                if (countLoop >= maxLoop) {
                    riuscito = false;
                    fineCiclo = true;
                    unTestoErrore =
                            "Espansione vista " + this.getNomeVista() + " in loop: " + countLoop;
                    throw new Exception(unTestoErrore);
                }// fine del blocco if

                /* controlla se la lista necessita di ulteriore espansione */
                if (!this.isListaElementiEspansa()) {

                    /* recupera la lista Elementi della Vista*/
                    listaElementi = this.getElementi();

                    /* spazzola la lista originale e ne crea una nuova (espansa)*/
                    listaEspansa = new ArrayList<VistaElemento>();
                    for (int k = 0; k < listaElementi.size(); k++) {

                        /* recupera il singolo elemento */
                        unElemento = (VistaElemento)listaElementi.get(k);

                        /* - se l'elemento e' gia' espanso lo aggiunge alla lista destinazione
                         * - se non e' espanso lo espande e aggiunge gli elementi risultanti
                         * alla lista destinazione */
                        if (!unElemento.isEspanso()) {

                            elementi = unElemento.espandi();

                            /* - se l'elenco contiene elementi, significa che
                             * e' riucito ad espandere l'elemento aggiunge
                             * gli elementi espansi alla lista risultante.
                             * - se l'elenco e' vuoto, significa che non e' riuscito
                             * a espandere l'elemento e il metodo deve fallire */
                            if (elementi.size() > 0) {
                                listaEspansa.addAll(elementi);
                            } else {    // non ha potuto espandere l'elemento
                                riuscito = false;
                                break;
                            }// fine del blocco if-else

                        } else {    // gia' espanso
                            listaEspansa.add(unElemento);
                        }// fine del blocco if-else

                    } /* fine del blocco for */

                    /* se riuscito, assegna la nuova lista di elementi alla Vista*/
                    if (riuscito) {
                        this.setElementi(listaEspansa);
                    } else {
                        fineCiclo = true;
                    }// fine del blocco if-else

                } else { // e' completamente espansa
                    fineCiclo = true;
                }// fine del blocco if-else

            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } /* fine del metodo */


    /**
     * Clona tutti gli oggetti contenuti negli elementi di questa Vista. <br>
     */
    private boolean clonaContenuti() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        ArrayList unaLista = null;
        VistaElemento unElemento = null;

        try {    // prova ad eseguire il codice
            unaLista = this.getElementi();

            /* spazzola gli elementi e ne clona i contenuti */
            for (int k = 0; k < unaLista.size(); k++) {
                unElemento = (VistaElemento)unaLista.get(k);
                unElemento.clonaContenuto();    // per ora opera solo se Campo
            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo

//    /**
//     * Inizializza tutti i campi non ancora inizializzati.<br>
//     *
//     * @return true se riuscito.
//     */
//    private boolean inizializzaCampi() {
//        /* variabili e costanti locali di lavoro */
//        boolean riuscito = true;
//        Campo unCampo = null;
//
//        try {	// prova ad eseguire il codice
//            for (int k = 0; k < this.getCampi().size(); k++) {
//                unCampo = (Campo)this.getCampi().get(k);
//                if (!unCampo.isInizializzato()) {
//                    riuscito = unCampo.inizializza();
//                    if (!riuscito) {
//                        break;
//                    }// fine del blocco if
//                }// fine del blocco if
//            } // fine del ciclo for
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return riuscito;
//    }


    /**
     * Controlla se la lista elementi della Vista e' completamente espansa.<br>
     * La lista elementi e' completamente espansa se:
     * - contiene solo oggetti di tipo Campo
     * - gli eventuali i campi linkati non hanno Viste associate
     *
     * @return true se la lista elementi e' completamente espansa
     */
    private boolean isListaElementiEspansa() {
        /** variabili e costanti locali di lavoro */
        boolean espansa = true;
        VistaElemento unElemento = null;
        ArrayList unaLista = null;

        unaLista = this.getElementi();

        /* spazzola gli elementi e controlla se sono tutti espansi */
        for (int k = 0; k < unaLista.size(); k++) {

            /* recupera il singolo oggetto dalla lista */
            unElemento = (VistaElemento)unaLista.get(k);

            /* verifica se e' espanso */
            if (!unElemento.isEspanso()) {
                espansa = false;
                break;
            }// fine del blocco if-else

        } /* fine del blocco for */

        /** valore di ritorno */
        return espansa;
    } /* fine del metodo */


    /**
     * Aggiunge un nuovo elemento alla collezione elementi.
     * <p/>
     * L'elemento viene aggiunto in ultima posizione.
     *
     * @param e l'elemento da aggiungere
     *
     * @return il riferimento all'elemento aggiunto
     */
    public VistaElemento addElemento(VistaElemento e) {

        try {    // prova ad eseguire il codice
            /* aggiunge l'Elemento alla lista elementi */
            this.getElementi().add(e);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return e;

    } /* fine del metodo */


    /**
     * Inserisce un nuovo elemento alla collezione elementi
     * in una data posizione.
     * <p/>
     *
     * @param e l'elemento da aggiungere
     * @param i l'indice della posizione (0 per il primo)
     *
     * @return il riferimento all'elemento inserito
     */
    public VistaElemento addElemento(VistaElemento e, int i) {

        try {    // prova ad eseguire il codice
            /* aggiunge l'Elemento alla lista elementi */
            this.getElementi().add(i, e);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return e;

    } /* fine del metodo */


    /**
     * Assegna il campo ordine di default della Vista.
     * Se c'è già un campo Ordine di default, lascia quello che trova.
     * Se non esiste un campo Ordine di default:
     * - Se la Vista contiene il campo Ordine e questo è visibile,
     * usa il campo Ordine.<br>
     * - Altrimenti usa il primo campo visibile.
     *
     * @return true se riuscito
     */
    private boolean regolaOrdineDefault() {
        /** variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        boolean continua2 = true;
        Campo unCampoOrdineModulo = null;
        VistaElemento unElemento = null;
        Campo unCampoOrdine = null;
        ArrayList unaLista = null;

        try {    // prova ad eseguire il codice

            /* se esiste già un campo Ordine di default,
             * lascia quello che trova */
            if (continua) {
                if (this.getCampoOrdineDefault() != null) {
                    unCampoOrdine = this.getCampoOrdineDefault();
                    continua = false;  //trovato
                }// fine del blocco if
            }// fine del blocco if

            /* prova col campo Ordine visibile */
            if (continua) {

                continua2 = true;

                /* controlla che la Vista abbia il modulo */
                if (this.getModulo() == null) {
                    continua2 = false;
                }// fine del blocco if

                /* recupera l'eventuale elemento della Vista contenente
                 * il campo Ordine */
                if (continua2) {
                    unCampoOrdineModulo = this.getModulo().getCampoOrdine();
                    unElemento = this.getElementoCampo(unCampoOrdineModulo);
                    continua2 = unElemento != null;
                }// fine del blocco if

                /* controlla che il campo Ordine sia visibile */
                if (continua2) {
                    continua2 = unElemento.isVisibile();
                }// fine del blocco if

                if (continua2) {
                    unCampoOrdine = (Campo)unElemento.getContenuto();
                    continua = false;   //trovato
                }// fine del blocco if

            }// fine del blocco if

            /* prova con il primo visibile */
            if (continua) {
                unaLista = this.getCampiVisibili();
                if (unaLista.size() > 0) {
                    unCampoOrdine = (Campo)unaLista.get(0);
                    continua = false;  //trovato
                }// fine del blocco if
            }// fine del blocco if

            /* prova con il primo anche se non visibile */
            if (continua) {
                unaLista = this.getCampi();
                if (unaLista.size() > 0) {
                    unCampoOrdine = (Campo)unaLista.get(0);
                    continua = false;    //trovato
                }// fine del blocco if
            }// fine del blocco if

            /* alla fine, se trovato, assegna il campo */
            if (unCampoOrdine != null) {
                this.setCampoOrdineDefault(unCampoOrdine);
                riuscito = true;
            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return riuscito;

    } /* fine del metodo */


    /**
     * Regola il riferimento al modulo per tutti gli elementi.<br>
     * Assegna all'elemento il riferimento al modulo del proprio contenuto
     * Nel caso di elementi di tipo nome che non hanno un modulo
     * gia' assegnato, assegna il modulo della Vista proprietaria.
     */
    private void regolaModuliElemento() {
        /* variabili e costanti locali di lavoro */
        ArrayList elementi = null;
        VistaElemento unElemento = null;

        try {    // prova ad eseguire il codice
            elementi = this.getElementi();
            for (int k = 0; k < elementi.size(); k++) {
                unElemento = (VistaElemento)elementi.get(k);
                unElemento.regolaModulo();
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setCampoOrdineDefault(Campo unCampoOrdinamento) {
        this.unCampoOrdinamento = unCampoOrdinamento;
    } /* fine del metodo setter */


    public String getNomeVista() {
        return nomeVista;
    }


    public void setNomeVista(String nomeVista) {
        this.nomeVista = nomeVista;
    }


    public Modulo getModulo() {
        return modulo;
    }


    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    /**
     * Restituisce tutti e soli gli oggetti Campo di questa Vista.<br>
     * Spazzola l'elenco ed estrae solo gli oggetti di tipo Campo.
     *
     * @return un elenco di oggetti Campo
     */
    public ArrayList<Campo> getCampi() {
        /** variabili e costanti locali di lavoro */
        ArrayList<VistaElemento> unElencoElementi = null;
        ArrayList<Campo> unElencoCampi = new ArrayList<Campo>();
        VistaElemento unElemento = null;
        Campo unCampo = null;

        /* recupera gli elementi della Vista */
        unElencoElementi = this.getElementi();

        /** Spazzola l'elenco ed estrae solo gli oggetti di tipo Campo */
        for (int k = 0; k < unElencoElementi.size(); k++) {
            // recupera l'elemento e controlla se e' un campo
            unElemento = (VistaElemento)unElencoElementi.get(k);
            if (unElemento.getTipo() == Vista.CAMPO) {
                unCampo = (Campo)unElemento.getContenuto();
                unElencoCampi.add(unCampo);
            } /* fine del blocco if */
        } /* fine del blocco for */

        /** Valore di ritorno */
        return unElencoCampi;
    } /* fine del metodo getter */


    /**
     * Restituisce tutti e soli i campi fisici di questa Vista.<br>
     *
     * @return un elenco di oggetti Campo
     */
    public ArrayList<Campo> getCampiFisici() {
        /** variabili e costanti locali di lavoro */
        ArrayList<Campo> campiFisici = new ArrayList<Campo>();
        ArrayList<Campo> campi = new ArrayList<Campo>();

        try { // prova ad eseguire il codice
            campi = this.getCampi();
            for (Campo campo : campi) {
                if (campo.getCampoDB().isCampoFisico()) {
                    campiFisici.add(campo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /** Valore di ritorno */
        return campiFisici;
    } /* fine del metodo getter */


    /**
     * Restituisce tutti e soli gli oggetti Campo visibili di questa Vista.<br>
     *
     * @return un elenco di oggetti visibili di tipo Campo
     */
    public ArrayList<Campo> getCampiVisibili() {
        /* variabili e costanti locali di lavoro */
        ArrayList unElencoElementi = null;
        ArrayList<Campo> unElencoCampi = new ArrayList<Campo>();
        VistaElemento unElemento = null;
        Campo unCampo = null;

        /* recupera gli elementi della Vista */
        unElencoElementi = this.getElementi();
        try { // prova ad eseguire il codice
            /* Spazzola l'elenco ed estrae solo i campi visibili */
            for (int k = 0; k < unElencoElementi.size(); k++) {
                // recupera l'elemento e controlla se e' un campo visibile
                unElemento = (VistaElemento)unElencoElementi.get(k);
                if (unElemento.getTipo() == Vista.CAMPO) {
                    if (unElemento.isVisibile()) {
                        unCampo = (Campo)unElemento.getContenuto();
                        unElencoCampi.add(unCampo);
                    }// fine del blocco if
                } /* fine del blocco if */
            } /* fine del blocco for */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* Valore di ritorno */
        return unElencoCampi;
    } /* fine del metodo getter */


    /**
     * Restituisce tutti e soli gli oggetti Campo totalizzabili di questa Vista.<br>
     *
     * @return un elenco di oggetti visibili di tipo Campo
     */
    public ArrayList<Campo> getCampiTotalizzabili() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> unElencoCampi = new ArrayList<Campo>();

        try { // prova ad eseguire il codice

            /* traverso tutta la collezione */
            ArrayList<Campo> campi = this.getCampiVisibili();
            for (Campo campo : campi) {
                if (campo.isTotalizzabile()) {
                    unElencoCampi.add(campo);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* Valore di ritorno */
        return unElencoCampi;
    } /* fine del metodo getter */


    /**
     * Restituisce tutti e soli gli Elementi visibili di questa Vista.<br>
     *
     * @return un elenco di oggetti visibili di tipo VistaElemento
     */
    public ArrayList<VistaElemento> getElementiVisibili() {
        /* variabili e costanti locali di lavoro */
        ArrayList<VistaElemento> unElencoElementi = null;
        ArrayList<VistaElemento> unElencoElementiOut = null;
        VistaElemento unElemento = null;

        /* recupera gli elementi della Vista */
        unElencoElementi = this.getElementi();
        unElencoElementiOut = new ArrayList<VistaElemento>();
        try { // prova ad eseguire il codice
            /* Spazzola l'elenco ed estrae solo gli elementi visibili */
            for (int k = 0; k < unElencoElementi.size(); k++) {
                // recupera l'elemento e controlla se e' un campo visibile
                unElemento = unElencoElementi.get(k);
                if (unElemento.isVisibile()) {
                    unElencoElementiOut.add(unElemento);
                }// fine del blocco if
            } /* fine del blocco for */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* Valore di ritorno */
        return unElencoElementiOut;
    } /* fine del metodo getter */


    /**
     * Controlla la visibilit� della colonna.<br>
     *
     * @param posizione tra gli elementi visibili
     *
     * @return vero se la colonna � visibile
     */
    public boolean isColonnaVisibile(int posizione) {
        /* variabili e costanti locali di lavoro */
        boolean visibile = false;
        VistaElemento unElemento = null;

        try { // prova ad eseguire il codice

            unElemento = this.getElemento(posizione);

            if (unElemento != null) {
                visibile = unElemento.isVisibile();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* Valore di ritorno */
        return visibile;
    }


    /**
     * Restituisce i nomi delle colonne visibili.<br>
     *
     * @return un elenco di colonne visibili sotto forma di voce
     */
    public ArrayList getColonneVisibili() {
        /* variabili e costanti locali di lavoro */
        ArrayList unElencoCampi = null;
        ArrayList<String> unElencoTitoli = new ArrayList<String>();
        String titolo = "";
        Campo unCampo = null;

        /* recupera i campi visibili */
        unElencoCampi = this.getCampiVisibili();

        try { // prova ad eseguire il codice
            /* Spazzola l'elenco ed estrae i titoli */
            for (int k = 0; k < unElencoCampi.size(); k++) {
                // recupera l'elemento e controlla se e' un campo visibile
                unCampo = (Campo)unElencoCampi.get(k);
                titolo = unCampo.getCampoLista().getTitoloColonna();
                unElencoTitoli.add(titolo);
            } /* fine del blocco for */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* Valore di ritorno */
        return unElencoTitoli;
    }


    /**
     * Restituisce l'elenco dei nomi chiave di tutti i campi della Vista.
     * <p/>
     *
     * @return l'elenco dei nomi chiave dei campi (oggetti String)
     */
    public ArrayList getChiaviCampo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> chiavi = null;
        ArrayList campi = null;
        Campo unCampo = null;
        String unaChiave = null;

        try {    // prova ad eseguire il codice
            chiavi = new ArrayList<String>();
            campi = this.getCampi();
            for (int k = 0; k < campi.size(); k++) {
                unCampo = (Campo)campi.get(k);
                unaChiave = unCampo.getChiaveCampo();
                chiavi.add(unaChiave);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiavi;
    }


    /**
     * Ritorna il numero di elementi contenuti nella Vista
     */
    private int getSize() {
        try {    // prova ad eseguire il codice
            return this.getElementi().size();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
            return 0;
        } /* fine del blocco try-catch */
    } /* fine del metodo getter */


    /**
     * Ritorna un elenco di tutti gli elementi di questa Vista
     *
     * @return un elenco di oggetti VistaElemento
     */
    public ArrayList<VistaElemento> getElementi() {
        return this.elementi;
    } /* fine del metodo */


    public void setElementi(ArrayList<VistaElemento> elementi) {
        this.elementi = elementi;
    }


    /**
     * Recupera un elemento di questa Vista (puo' essere Campo o Vista)
     *
     * @param unIndice l'indice dell'elemento da recuperare
     *
     * @return l'elemento recuperato (VistaElemento)
     */
    public VistaElemento getElemento(int unIndice) {
        /** variabili e costanti locali di lavoro */
        VistaElemento unElementoVista = null;

        // per sicurezza controlla se e' nel range, poi estrae l'oggetto
        if ((unIndice >= 0) && (this.getSize() > unIndice)) {
            unElementoVista = this.getElementi().get(unIndice);
        } /* fine del blocco if */

        return unElementoVista;
    } /* fine del metodo */


    /**
     * Recupera un campo della Vista dato il nome completo del file sul DB. <p>
     *
     * @param unaChiave la chiave del campo da recuperare
     *
     * @return il Campo richiesto, null se non trovato
     */
    public Campo getCampoAlex(String unaChiave) {
        /** variabili e costanti locali di lavoro */
        ArrayList unSetCampi;
        Campo unCampo;
        Campo unCampoRichiesto = null;
        String chiaveCampo;

        try {    // prova ad eseguire il codice
            /** recupera tutti e soli gli oggetti campo */
            unSetCampi = this.getCampi();

            /** spazzola tutti i campi */
            for (int k = 0; k < unSetCampi.size(); k++) {
                /** recupera il campo e la sua chiave */
                unCampo = (Campo)unSetCampi.get(k);
                chiaveCampo = unCampo.getChiaveCampo();

                /** se lo trova esce dal ciclo for */
                if (chiaveCampo.equalsIgnoreCase(unaChiave)) {
                    unCampoRichiesto = unCampo;
                    break;
                } /* fine del blocco if */

            } /* fine del blocco for */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unCampoRichiesto;
    } /* fine del metodo */


    /**
     * Recupera un campo della Vista dato il nome interno. <p>
     *
     * @param nomeCampo il nome interno del campo da recuperare
     *
     * @return il Campo richiesto, null se non trovato
     */
    public Campo getCampo(String nomeCampo) {
        /** variabili e costanti locali di lavoro */
        ArrayList campi;
        Campo unCampo;
        Campo unCampoRichiesto = null;
        String nomeInterno;

        try {    // prova ad eseguire il codice
            /* recupera tutti e soli gli oggetti campo */
            campi = this.getCampi();

            /* spazzola tutti i campi */
            for (int k = 0; k < campi.size(); k++) {
                /** recupera il campo e la sua chiave */
                unCampo = (Campo)campi.get(k);
                nomeInterno = unCampo.getNomeInterno();

                /* se lo trova esce dal ciclo for */
                if (nomeInterno.equalsIgnoreCase(nomeCampo)) {
                    unCampoRichiesto = unCampo;
                    break;
                } /* fine del blocco if */

            } /* fine del blocco for */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampoRichiesto;
    } /* fine del metodo */


    /**
     * Recupera un campo della Vista dato il nome interno. <p>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il Campo richiesto, null se non trovato
     */
    public Campo getCampo(Campi campo) {
        /* invoca il metodo sovrascritto della superclasse */
        return this.getCampo(campo.get());
    } /* fine del metodo */


    /**
     * Recupera un campo dalla Vista in base a un Campo
     * (la ricerca viene effettuata sul nome chiave del campo)
     *
     * @param unCampoRichiesto il campo da recuperare
     *
     * @return il campo recuperato (null se non trovato)
     */
    public Campo getCampo(Campo unCampoRichiesto) {
        /** variabili e costanti locali di lavoro */
        String chiaveRichiesta;
        String chiave;
        ArrayList unSetCampi;
        Campo unCampo;
        Campo unCampoTrovato = null;

        try {    // prova ad eseguire il codice

            /** recupera la chiave del campo richiesto */
            chiaveRichiesta = unCampoRichiesto.getChiaveCampo();

            /** recupera tutti e soli gli oggetti campo */
            unSetCampi = this.getCampi();

            /** spazzola tutti i campi */
            for (int k = 0; k < unSetCampi.size(); k++) {
                /** recupera il campo e la sua chiave */
                unCampo = (Campo)unSetCampi.get(k);
                chiave = unCampo.getChiaveCampo();

                /** se lo trova esce dal ciclo for */
                if (chiave.equalsIgnoreCase(chiaveRichiesta)) {
                    unCampoTrovato = unCampo;
                    break;
                } /* fine del blocco if */
            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unCampoTrovato;
    } /* fine del metodo */


    /**
     * Ritorna la posizione di un campo visibile nella Vista.
     * <p/>
     *
     * @param campo il campo da cercare
     *
     * @return la posizione nella Vista (0 per il promo, -1 se non trovato)
     *         la posizione e' relativa all'elenco dei soli campi visibili
     */
    public int getIndiceCampoVisibile(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int posizione = -1;
        String chiaveCampo = null;
        String chiave = null;
        ArrayList campi = null;
        Campo unCampo = null;

        try {    // prova ad eseguire il codice
            chiaveCampo = campo.getChiaveCampo();
            campi = this.getCampiVisibili();
            for (int k = 0; k < campi.size(); k++) {
                unCampo = (Campo)campi.get(k);
                chiave = unCampo.getChiaveCampo();
                if (chiave.equals(chiaveCampo)) {
                    posizione = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return posizione;
    }


    /**
     * Ritorna la posizione di un campo nella Vista.
     * <p/>
     *
     * @param campo il campo da cercare
     *
     * @return la posizione nella Vista (0 per il primo, -1 se non trovato)
     */
    public int getIndiceCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int posizione = -1;
        String chiaveCampo = null;
        String chiave = null;
        ArrayList campi = null;
        Campo unCampo = null;

        try {    // prova ad eseguire il codice
            chiaveCampo = campo.getChiaveCampo();
            campi = this.getCampi();
            for (int k = 0; k < campi.size(); k++) {
                unCampo = (Campo)campi.get(k);
                chiave = unCampo.getChiaveCampo();
                if (chiave.equals(chiaveCampo)) {
                    posizione = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return posizione;
    }


    /**
     * Controlla se nella Vista esiste un campo con una data chiave.
     * <p/>
     *
     * @param chiave la chiave del campo.
     */
    public boolean isEsisteCampo(String chiave) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Campo unCampo = null;

        try {    // prova ad eseguire il codice
            unCampo = this.getCampoAlex(chiave);
            if (unCampo != null) {
                esiste = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Aggiunge un ordine pubblico a un campo della Vista. <br>
     * Per essere sicuri di trovare il campo, va chiamato solo dopo
     * che la vista e' stata inizializzata.
     *
     * @param nomeCampo il nome del campo della vista al quale
     * aggiungere l'ordine pubblico
     * @param campoOrdine il campo da aggiungere all'ordine pubblico
     */
    public void addOrdinePubblico(String nomeCampo, Campo campoOrdine) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoLista unCampoLista = null;
        String errore = null;

        try {    // prova ad eseguire il codice
            /* recupera il campo al quale aggiungere l'ordine */
            unCampo = this.getCampoAlex(nomeCampo);

            /* controlla se lo ha trovato */
            if (unCampo == null) {
                errore = "Il campo ";
                errore += nomeCampo;
                errore += " non esiste nella vista ";
                errore += this.getNomeVista();
                throw new Exception(errore);
            }// fine del blocco if

            /* aggiunge l'ordine */
            unCampoLista = unCampo.getCampoLista();
            unCampoLista.addOrdinePubblico(campoOrdine);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return;
    } // fine del metodo


    /**
     * Recupera un Elemento dalla Vista in base a un campo contenuto
     * (la ricerca viene effettuata sul nome completo Sql del campo)
     *
     * @param unCampoRichiesto il campo da recuperare
     *
     * @return il campo recuperato (null se non trovato)
     */
    private VistaElemento getElementoCampo(Campo unCampoRichiesto) {
        /** variabili e costanti locali di lavoro */
        VistaElemento unElementoTrovato = null;
        String chiaveRichiesto = null;
        String chiave = null;
        ArrayList elementi = null;
        VistaElemento elementoCampo = null;
        Campo unCampo = null;
        try {    // prova ad eseguire il codice

            /* recupera il la chiave del campo richiesto */
            chiaveRichiesto = unCampoRichiesto.getChiaveCampo();

            /* recupera tutti e soli gli elementi di tipo campo */
            elementi = this.getElementiCampo();

            /* spazzola gli elementi campo e confronta */
            for (int k = 0; k < elementi.size(); k++) {
                elementoCampo = (VistaElemento)elementi.get(k);
                unCampo = (Campo)elementoCampo.getContenuto();
                chiave = unCampo.getChiaveCampo();

                /* al primo che trova esce dal ciclo for */
                if (chiave.equalsIgnoreCase(chiaveRichiesto)) {
                    unElementoTrovato = elementoCampo;
                    break;
                } /* fine del blocco if */
            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElementoTrovato;
    } /* fine del metodo */


    /**
     * Recupera un elenco di tutti gli Elementi di tipo Campo
     *
     * @return l'elenco di elementi campo della Vista (oggetti VistaElemento)
     */
    private ArrayList getElementiCampo() {
        /** variabili e costanti locali di lavoro */
        ArrayList<VistaElemento> elementiCampo = null;
        ArrayList<VistaElemento> elementi = null;
        VistaElemento unElemento = null;

        try {    // prova ad eseguire il codice

            elementiCampo = new ArrayList<VistaElemento>();

            /** recupera tutti gli elementi */
            elementi = this.getElementi();

            /* filtra solo quelli di tipo Campo */
            for (int k = 0; k < elementi.size(); k++) {
                unElemento = (VistaElemento)elementi.get(k);
                if (unElemento.getTipo() == Vista.CAMPO) {
                    elementiCampo.add(unElemento);
                }// fine del blocco if
            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return elementiCampo;
    } /* fine del metodo */


    /**
     * Restituisce il campo di ordinamento di default per questa Vista.<br>
     */
    public Campo getCampoOrdineDefault() {
        return this.unCampoOrdinamento;
    } /* fine del metodo getter */


    public Dimension getDimensione() {
        return dimensione;
    }


    public void setDimensione(Dimension dimensione) {
        this.dimensione = dimensione;
    }


    public boolean isInizializzato() {
        return inizializzato;
    }


    private void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    /**
     * Ritorna un Campo della Vista.<br>
     * Se l'elemento richiesto e' fuori range o non e'
     * un Campo, ritorna null<br>
     * L'indice si riferisce a tutte le colonne e non solo a quelle visibili <br>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il Campo richiesto
     */
    public Campo getCampo(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        VistaElemento unElemento = null;

        try { // prova ad eseguire il codice

            unElemento = this.getElemento(colonna);
            if (unElemento != null) {
                /* controlla che l'elemento sia un campo */
                if (unElemento.getTipo() == Vista.CAMPO) {
                    unCampo = (Campo)unElemento.getContenuto();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } // fine del metodo


    /**
     * Ritorna il campo originale dal quale è derivata una colonna della Vista.
     * <p/>
     * L'indice si riferisce a tutte le colonne e non solo a quelle visibili <br>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il Campo originale
     */
    public Campo getCampoOriginale(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        VistaElemento unElemento = null;

        try { // prova ad eseguire il codice

            unElemento = this.getElemento(colonna);
            if (unElemento != null) {
                unCampo = unElemento.getCampoOriginale();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } // fine del metodo


    /**
     * Ritorna un Campo visibile della Vista.<br>
     * Se l'elemento richiesto e' fuori range o non e'
     * un Campo, ritorna null<br>
     * L'indice si riferisce solo alle colonne visibili <br>
     * Le colonne visibili partono da zero <br>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il Campo richiesto
     */
    public Campo getCampoVisibile(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        ArrayList<Campo> campiVisibili = null;

        try { // prova ad eseguire il codice

            /* elenco dei campi visibili */
            campiVisibili = this.getCampiVisibili();

            if ((colonna >= 0) && (colonna < campiVisibili.size())) {
                campo = campiVisibili.get(colonna);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    } // fine del metodo


    /**
     * Ritorna il numero di colonne
     * Attenzione! se chiamato prima della inizializzazione (espansione),
     * il numero non e' quello definitivo.
     *
     * @return il numero di elementi della Vista
     */
    public int getColumnCount() {
        return this.getSize();
    } /* fine del metodo */


    /**
     * Ritorna il numero di colonne visibili
     * Attenzione! se chiamato prima della inizializzazione (espansione),
     * il numero non e' quello definitivo.
     *
     * @return il numero di elementi della Vista
     */
    public int getVisibleColumnCount() {
        return this.getCampiVisibili().size();
    } /* fine del metodo */


    /**
     * Ritorna il nome di una colonna
     *
     * @param colonna indice della colonna di cui si vuole il nome
     *
     * @return nome della colonna specificata
     */
    public String getColumnName(int colonna) {
        /* variabili e costanti locali di lavoro */
        String unNome = "";
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            unCampo = this.getCampo(colonna);
            if (unCampo != null) {
                unNome = unCampo.getCampoLista().getTitoloColonna();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        return unNome;
    } /* fine del metodo */


    /**
     * Assegna il voce a una colonna
     *
     * @param colonna indice della colonna
     * @param titolo nome della colonna
     */
    public void setColumnName(int colonna, String titolo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            unCampo = this.getCampo(colonna);
            if (unCampo != null) {
                unCampo.setTitoloColonna(titolo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Ritorna il voce di una colonna visibile.
     * <p/>
     *
     * @param indice della colonna visibile (0 per la prima)
     *
     * @return il voce della colonna, preso dal campo
     */
    public String getVisibleColumnName(int indice) {
        /* variabili e costanti locali di lavoro */
        String titolo = null;
        Campo campo;

        try {    // prova ad eseguire il codice

            campo = this.getCampoVisibile(indice);
            if (campo != null) {
                titolo = campo.getCampoLista().getTitoloColonna();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return titolo;
    }


    /**
     * Assegna il voce a una colonna visibile
     *
     * @param colonna indice della colonna visibile (0 per la prima)
     * @param nome nome della colonna
     */
    public void setVisibleColumnName(int colonna, String nome) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice

            campo = this.getCampoVisibile(colonna);
            if (campo != null) {
                campo.setTitoloColonna(nome);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Ritorna la classe dei dati corrispondente a una colonna.
     * <p/>
     *
     * @param colonna indice della colonna (0 per la prima)
     *
     * @return la classe dei dati alla colonna specificata
     */
    public Class getColumnClass(int colonna) {

        /* variabili e costanti locali di lavoro */
        Class unaClasse = null;
        Campo unCampo;

        try {    // prova ad eseguire il codice

            unCampo = this.getCampo(colonna);
            if (unCampo != null) {
                unaClasse = unCampo.getCampoDati().getTipoMemoria().getClasse();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unaClasse;

    } /* fine del metodo */


    /**
     * Aggiunge un campo a questa vista nella posizione data.
     * <p/>
     * Regola le caratteristiche dell'Elemento in base alle
     * caratteristiche del Campo
     *
     * @param campo da aggiungere
     * @param pos la posizione (0 per la prima)
     *
     * @return l'Elemento creato
     */
    public VistaElemento addCampo(Campo campo, int pos) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        VistaElemento elemento = null;
        int tipo=0;
        Modulo modulo=null;

        try {    // prova ad eseguire il codice

            continua = ((campo != null) && (pos >= 0));

            /* crea un nuovo elemento */
            if (continua) {

                tipo = Vista.CAMPO;

                modulo = campo.getModulo();

                elemento = new VistaElemento(this, campo, tipo, modulo);

                /*
                 * se il campo esiste gia' nella collezione del modulo,
                 * regola le caratteristiche dell'elemento in base a quelle
                 * del campo
                 */
                elemento.regolaDaCampo();

                /* aggiunge l'elemento alla Vista */
                this.addElemento(elemento, pos);

            } // fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;

    }// fine del metodo


    /**
     * Aggiunge un campo a questa vista.
     * <p/>
     *
     * @param campo da aggiungere
     *
     * @return l'Elemento creato
     */
    public VistaElemento addCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        VistaElemento elemento = null;
        int pos;

        try {    // prova ad eseguire il codice

            /* recupera l'ultima posizione */
            pos = this.getElementi().size();

            /* aggiunge il campo */
            elemento = this.addCampo(campo, pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;

    }// fine del metodo


    /**
     * Aggiunge un campo dello stesso modulo della vista.
     * <p/>
     *
     * @param nomeCampo da aggiungere
     *
     * @return l'Elemento creato
     */
    public VistaElemento addCampo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        VistaElemento elemento = null;
        Campo campo;
        Modulo mod;

        try {    // prova ad eseguire il codice

            mod = this.getModulo();

            if (mod != null) {
                campo = mod.getCampo(nomeCampo);
                if (campo != null) {
                    elemento = this.addCampo(campo);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;
    }// fine del metodo


    /**
     * Aggiunge un campo dello stesso modulo della vista.
     * <p/>
     *
     * @param unCampo oggetto dell'interfaccia da aggiungere
     *
     * @return l'Elemento creato
     */
    public VistaElemento addCampo(Campi unCampo) {
        /* variabili e costanti locali di lavoro */
        VistaElemento elemento = null;

        try {    // prova ad eseguire il codice

            elemento = this.addCampo(unCampo.get());

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;
    }// fine del metodo


    /**
     * Aggiunge una vista a questa vista. <br>
     *
     * @param vista da aggiungere
     *
     * @return l'Elemento creato
     */
    public VistaElemento addVista(Vista vista) {
        /* variabili e costanti locali di lavoro */
        VistaElemento elemento = null;
        int tipo;
        Modulo modulo;

        try {    // prova ad eseguire il codice

            tipo = Vista.VISTA;
            modulo = vista.getModulo();

            /* crea un nuovo elemento */
            elemento = new VistaElemento(this, vista, tipo, modulo);

            /* aggiunge l'elemento alla Vista */
            this.addElemento(elemento);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;

    }// fine del metodo


    /**
     * Aggiunge una vista dello stesso modulo di questa vista.
     * <p/>
     *
     * @param nomeVista da aggiungere
     *
     * @return l'Elemento creato
     */
    public VistaElemento addVista(String nomeVista) {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        VistaElemento elemento = null;

        try {    // prova ad eseguire il codice

            vista = this.getModulo().getVista(nomeVista);
            elemento = this.addVista(vista);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;

    }// fine del metodo


    public boolean isMostraTotali() {
        return mostraTotali;
    }


    private void setMostraTotali(boolean mostraTotali) {
        this.mostraTotali = mostraTotali;
    }
}