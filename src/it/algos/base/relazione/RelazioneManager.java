/**
 * Title:        RelazioneManager.java
 * Package:      it.algos.base.relazione
 * Description:  Metodi di getione delle relazioni del database
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 giugno 2003 alle 13.25
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.relazione;

import it.algos.base.albero.Albero;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

import java.util.ArrayList;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Raccogliere i metodi per la gestione delle relazioni tra i campi fisici <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 giugno 2003 ore 13.25
 */
public abstract class RelazioneManager extends Object {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public RelazioneManager() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * Restituisce un Percorso per andare da una tavola
     * a un'altra tavola.
     * Se esistono piu' percorsi possibili, tenta di effettuare la
     * riduzione dei percorsi per trovare quello richiesto
     *
     * @param tavolaPartenza tavola di partenza
     * @param tavolaDestinazione tavola di destinazione
     * @param unaTavolaRelazioni tavola di relazioni
     * @param unPacchettoRelazioniObbligate pacchetto di relazioni obbligate per tentare di ridurre i percorsi
     * in caso di ambiguita' nelle relazioni (piu' percorsi possibili).
     *
     * @return un oggetto RelazionePercorso contenente il percorso
     *         trovato (null se non trovato)
     */
    public static RelazionePercorso getPercorsoRelazione(String tavolaPartenza,
                                                         String tavolaDestinazione,
                                                         ArrayList unaTavolaRelazioni,
                                                         ArrayList unPacchettoRelazioniObbligate) {

        /** variabili e costanti locali di lavoro */
        RelazionePercorso unPercorso = null;

        /** recupera il pacchetto di percorsi tra le due tavole */
        ArrayList unPacchettoPercorsi = getPacchettoPercorsi(tavolaPartenza,
                tavolaDestinazione,
                unaTavolaRelazioni);

        /** se il pacchetto non contiene percorsi, ritorna null
         *  se il pacchetto contiene un solo percorso, ritorna il percorso trovato
         *  se il pacchetto contiene piu' percorsi, tenta la riduzione. */
        int unNumeroPercorsi = unPacchettoPercorsi.size();
        switch (unNumeroPercorsi) {
            case 0: // nessun percorso esistente
                break;
            case 1: // un percorso
                unPercorso =
                        (RelazionePercorso)unPacchettoPercorsi.get(0);    // prende il primo e unico percorso
                break;
            default:   // contiene piu' percorsi, tenta le riduzioni

                /*
                 * prima riduzione: percorsi obbligati da seguire (se ce ne sono)
                 */
                if (unPacchettoRelazioniObbligate != null) {
                    unPacchettoPercorsi = riduttorePercorsiObbligati(unPacchettoPercorsi,
                            unPacchettoRelazioniObbligate);
                }// fine del blocco if

                /*
                * se dopo la riduzione contiene ancora piu' di un percorso,
                * esegue la seconda riduzione in base ai percorsi preferiti
                */
                if (unPacchettoPercorsi.size() > 1) {
                    unPacchettoPercorsi = riduttorePercorsiPreferiti(unPacchettoPercorsi);
                } /* fine del blocco if-else */

                /*
                 * se dopo la riduzione contiene ancora piu' di un percorso,
                 * esegue la terza riduzione (usa il percorso pi� breve)
                 */
                if (unPacchettoPercorsi.size() > 1) {
                    unPacchettoPercorsi = riduttorePercorsiLunghezza(unPacchettoPercorsi);
                } /* fine del blocco if-else */

                /*
                 * Se dopo le precedenti riduzioni il pacchetto contiene un solo
                 * percorso, ritorna il percorso trovato.
                 * Se invece contiene ancora piu' di un percorso, presenta un
                 * messaggio di avviso e ritorna null
                 */
                if (unPacchettoPercorsi.size() == 1) {
                    unPercorso =
                            (RelazionePercorso)unPacchettoPercorsi.get(0);    // prende il primo e unico percorso
                } else {
                    new MessaggioAvviso("Relazione ambigua. Impossibile determinare un percorso da " +
                            tavolaPartenza +
                            " a " +
                            tavolaDestinazione);
                } /* fine del blocco if-else */

                break;
        } // fine del blocco switch

        return unPercorso;
    } /* fine del metodo */


    /**
     * Riduzione di un pacchetto percorsi utilizzando una serie di
     * relazioni obbligate.
     * In caso di relazione ambigua tra due tavole, i percorsi che
     * collegano le due tavole possono essere piu' di uno.
     * Per effettuare la riduzione dei percorsi, l'utente puo' fornire
     * una o piu' relazioni da utilizzare obbligatoriamente.
     * <p/>
     * In tal caso, per ogni percorso nel pacchetto, il riduttore controlla
     * che esistano tutte le relazioni obbligate fornite dall'utente.
     * Se il percorso non contiene tutte le relazioni obbligate, viene scartato
     * dal pacchetto.
     *
     * @param unPacchettoPercorsi pacchetto di oggetti RelazionePercorso da ridurre
     * @param unPacchettoRelazioniObbligate pacchetto di oggetti Relazione da seguire obbligatoriamente
     *
     * @return un pacchetto di oggetti RelazionePercorso ridotto
     */
    private static ArrayList riduttorePercorsiObbligati(ArrayList unPacchettoPercorsi,
                                                        ArrayList unPacchettoRelazioniObbligate) {
        /** variabili e costanti locali di lavoro */
        ArrayList unPacchettoPercorsiRidotto = new ArrayList();
        RelazionePercorso unPercorso = null;

        /** spazzola tutti i percorsi e scarta quelli che non
         *  contengono tutte le relazioni obbligate specificate */
        for (int k = 0; k < unPacchettoPercorsi.size(); k++) {

            // estrae il percorso
            unPercorso = (RelazionePercorso)unPacchettoPercorsi.get(k);

            // controlla se il percorso contiene tutte le relazioni obbligate
            if (percorsoContieneRelazioni(unPercorso, unPacchettoRelazioniObbligate)) {
                // aggiunge il percorso al pacchetto percorsi da ritornare
                unPacchettoPercorsiRidotto.add(unPercorso);
            } /* fine del blocco if */

        } /* fine del blocco for */

        return unPacchettoPercorsiRidotto;
    } /* fine del metodo */


    /**
     * Riduzione per eliminare le ambiguita' dovute a piu' relazioni possibili
     * tra due tavole (piu' campi di una tavola che puntano ad un'altra tavola).
     * Questa riduzione e' basata sul flag setRelazionePreferita della relazione,
     * ed avviene nel modo seguente:
     * Si identificano tutti i percorsi "uguali" nel pacchetto (quelli che
     * portano da una tavola all'altra nello stesso numero di passaggi,
     * passando attraverso le stesse tavole, indipendentemante pero' dai
     * campi usati per collegare le tavole, vedi equals() in RelazionePercorso),
     * e per ogni gruppo di percorsi uguali si tenta di ricavarne uno solo,
     * utilizzando le relazioni marcate come "preferite" nei casi ambigui.
     *
     * @param unPacchettoPercorsi pacchetto di oggetti RelazionePercorso da ridurre
     *
     * @return un pacchetto di oggetti RelazionePercorso ridotto
     */
    private static ArrayList riduttorePercorsiPreferiti(ArrayList unPacchettoPercorsi) {

        /** variabili e costanti locali di lavoro */
        ArrayList unPacchettoPercorsiRidotto = new ArrayList();
        ArrayList unElencoGruppiPercorsiUguali = null;
        ArrayList unGruppoPercorsiUguali = null;

        /** crea dei gruppi di percorsi "uguali"
         *  (percorsi con pari tavole)*/
        unElencoGruppiPercorsiUguali = raggruppaPercorsiPariTavola(unPacchettoPercorsi);

        /** spazzola i gruppi di percorsi uguali */
        for (int k = 0; k < unElencoGruppiPercorsiUguali.size(); k++) {

            // estrae il singolo gruppo
            unGruppoPercorsiUguali = (ArrayList)unElencoGruppiPercorsiUguali.get(k);

            // sostituisce le relazioni non preferite con quelle preferite
            sostituisceRelazioniPreferite(unGruppoPercorsiUguali);

            // scarta gli eventuali percorsi uguali risultanti dopo la sostituzione
            unGruppoPercorsiUguali = scartaPercorsiUguali(unGruppoPercorsiUguali);

            // aggiunge i percorsi del pacchetto risultante al pacchetto ridotto
            unPacchettoPercorsiRidotto.addAll(unGruppoPercorsiUguali);

        } /* fine del blocco for */

        return unPacchettoPercorsiRidotto;
    } /* fine del metodo */


    /**
     * Riduzione per eliminare le ambiguita' dovute a piu' relazioni possibili
     * tra due tavole.
     * Questa riduzione opera selezionando il percorso pi� breve di tutti.
     * Se nel pacchetto di percorsi un solo percorso e' piu' breve di tutti
     * gli altri, ritorna tale percorso, altrimenti ritorna il pacchetto originale.
     *
     * @param unPacchettoPercorsi un pacchetto di oggetti RelazionePercorso da ridurre
     *
     * @return un pacchetto di oggetti RelazionePercorso ridotto
     */
    private static ArrayList riduttorePercorsiLunghezza(ArrayList unPacchettoPercorsi) {

        /** variabili e costanti locali di lavoro */
        ArrayList unPacchettoPercorsiRidotto = new ArrayList();
        RelazionePercorso unPercorso = null;
        RelazionePercorso unPercorsoBreve = null;
        int lunghezzaPercorso = 0;
        int lunghezzaMinima = 0;
        int quantiPercorsiBrevi = 0;

        try {    // prova ad eseguire il codice

            /* determina la lunghezza del percorso piu' breve */
            for (int k = 0; k < unPacchettoPercorsi.size(); k++) {

                /* estrae il singolo percorso */
                unPercorso = (RelazionePercorso)unPacchettoPercorsi.get(k);
                lunghezzaPercorso = unPercorso.getLunghezza();

                /* la prima volta registra la lunghezza minima */
                if (k == 0) {
                    lunghezzaMinima = lunghezzaPercorso;
                }// fine del blocco if

                /* se la lunghezza e' inferiore alla minima corrente
                 * registra la nuova lunghezza minima */
                if (lunghezzaPercorso < lunghezzaMinima) {
                    lunghezzaMinima = lunghezzaPercorso;
                }// fine del blocco if

            } /* fine del blocco for */

            /* determina se esiste un solo percorso di lunghezza pari alla minima */
            for (int k = 0; k < unPacchettoPercorsi.size(); k++) {

                /* estrae il singolo percorso */
                unPercorso = (RelazionePercorso)unPacchettoPercorsi.get(k);
                lunghezzaPercorso = unPercorso.getLunghezza();

                /* se e' un percorso breve lo registra */
                if (lunghezzaPercorso == lunghezzaMinima) {
                    unPercorsoBreve = unPercorso;
                    ++quantiPercorsiBrevi;
                }// fine del blocco if

            } /* fine del blocco for */

            /* se esiste un solo percorso breve, ritorna un pacchetto con tale
             * percorso, altrimenti, ritorna il pacchetto percorsi originale */
            if (quantiPercorsiBrevi == 1) {
                unPacchettoPercorsiRidotto.add(unPercorsoBreve);
            } else {
                unPacchettoPercorsiRidotto.addAll(unPacchettoPercorsi);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        return unPacchettoPercorsiRidotto;
    } /* fine del metodo */


    /**
     * Controlla se un percorso contiene una serie di relazioni
     *
     * @param unPercorso percorso da controllare
     * @param unPacchettoRelazioniObbligate lista di relazioni
     *
     * @return true se il percorso contiene tutte le relazioni della lista
     */
    private static boolean percorsoContieneRelazioni(RelazionePercorso unPercorso,
                                                     ArrayList unPacchettoRelazioniObbligate) {
        /** variabili e costanti locali di lavoro */
        boolean contieneTutte = true;

        /** estrae l'elenco delle relazioni dal percorso */
        ArrayList unElencoRelazioniPercorso = unPercorso.getRelazioni();

        /** per ogni relazione obbligata nella lista, controlla
         *  se esiste nel percorso */
        for (int k = 0; k < unPacchettoRelazioniObbligate.size(); k++) {

            // estrae la relazione obbligata da cercare nel pacchetto
            Relazione unaRelazioneObbligata = (Relazione)unPacchettoRelazioniObbligate.get(k);

            // cerca la relazione obbligata nel pacchetto
            int unRisultato =
                    unElencoRelazioniPercorso.indexOf(unaRelazioneObbligata); // usa equals()

            // se non l'ha trovata, il pacchetto non contiene tutte le relazioni obbligate
            if (unRisultato == -1) {
                contieneTutte = false;
                break;
            } /* fine del blocco if-else */

        } /* fine del blocco for */

        return contieneTutte;
    } /* fine del metodo */


    /**
     * Restituisce un pacchetto di Percorsi per andare da una tavola
     * a un'altra tavola
     *
     * @param tavolaPartenza tavola di partenza
     * @param tavolaDestinazione tavola di destinazione
     * @param unaTavolaRelazioni tavola di relazioni
     *
     * @return una lista di oggetti PercorsoRelazione
     */
    private static ArrayList getPacchettoPercorsi(String tavolaPartenza,
                                                  String tavolaDestinazione,
                                                  ArrayList unaTavolaRelazioni) {

        /** variabili e costanti locali di lavoro */
        ArrayList unPacchettoPercorsiTrovati = new ArrayList();    // da ritornare
        ArrayList unoStackPercorsi = new ArrayList();   // stack di lavoro
        ArrayList unaListaRelazioni = null;
        RelazionePercorso unPercorsoStack = null;
        Relazione unaRelazione = null;
        String unaTavolaPartenza = null;
        int numeroCicli = 0;

        /** cerca tutte le relazioni che puntano alla tavolaDestinazione,
         *  per ognuna crea un Percorso e lo aggiunge allo stack */
        unaListaRelazioni = pacchettoRelazioniVersoTavola(tavolaDestinazione, unaTavolaRelazioni);
        for (int k = 0; k < unaListaRelazioni.size(); k++) {
            RelazionePercorso unPercorso =
                    new RelazionePercorso((Relazione)unaListaRelazioni.get(k));
            unoStackPercorsi.add(unPercorso);
        } /* fine del blocco for */

        /** Elabora ricorsivamente lo stack fino a quando non e' vuoto
         *  per estrarre tutti i percorsi che vanno dalla tavolaPartenza
         *  alla tavolaDestinazione */
        while (unoStackPercorsi.size() > 0) {

            /** contatore cicli per uscita di emergenza anti-loop */
            numeroCicli = numeroCicli + 1;

            /** crea un nuovo stack per il successivo livello di relazione
             *  spazzola tutti i percorsi nello stack corrente
             *  e riempie il nuovo stack con le relazioni al livello successivo */
            ArrayList unNuovoStackPercorsi = new ArrayList();
            for (int k = 0; k < unoStackPercorsi.size(); k++) {

                /** estrae il percorso da analizzare dallo stack */
                unPercorsoStack = (RelazionePercorso)unoStackPercorsi.get(k);

                /** estrae l'ultima relazione dal percorso (la piu' recente) */
                unaRelazione = unPercorsoStack.getUltimaRelazione();

                /** estrae la tavola di partenza dalla relazione */
                unaTavolaPartenza = unaRelazione.getTavolaPartenza();

                /** se la tavola di partenza e' quella che stavamo cercando,
                 *  aggiunge il percorso corrente al pacchetto di percorsi
                 *  da restituire */
                if (unaTavolaPartenza.equalsIgnoreCase(tavolaPartenza)) {
                    unPacchettoPercorsiTrovati.add(unPercorsoStack);
                } else {
                    /**  Se la tavola di partenza non e' quella che stavamo cercando,
                     *  prosegue l'analisi delle relazioni per questo Percorso */

                    /** cerca tutte le relazioni che puntano alla tavola */
                    unaListaRelazioni = pacchettoRelazioniVersoTavola(unaTavolaPartenza,
                            unaTavolaRelazioni);

                    /** per ogni Relazione trovata, costruisce un nuovo Percorso contenente
                     *  tutte le relazioni esistenti fino a questo punto piu' la nuova Relazione,
                     *  e aggiunge il percorso al nuovo stack dei percorsi */
                    for (int i = 0; i < unaListaRelazioni.size(); i++) {
                        Relazione unaNuovaRelazione = (Relazione)unaListaRelazioni.get(i);
                        RelazionePercorso unNuovoPercorso =
                                new RelazionePercorso(unPercorsoStack.getRelazioni());
                        unNuovoPercorso.addRelazione(unaNuovaRelazione);
                        unNuovoStackPercorsi.add(unNuovoPercorso);
                    } /* fine del blocco for */

                } /* fine del blocco if */

            } /* fine del blocco for */

            /** sostituisce il il vecchio stack con il nuovo stack */
            unoStackPercorsi = unNuovoStackPercorsi;

            /** controllo uscita di emergenza in caso di loop */
            if (unoStackPercorsi.size() > 100) {
                new MessaggioAvviso("I percorsi aumentano!. Uscita forzata.");
                System.exit(1);
                break;
            } /* fine del blocco if */

            /** controllo uscita di emergenza in caso di loop */
            if (numeroCicli > 1000) {
                new MessaggioAvviso("Ricerca relazioni in loop. Uscita forzata.");
                System.exit(1);
                break;
            } /* fine del blocco if */

        } /* fine del blocco while */

        return unPacchettoPercorsiTrovati;
    } /* fine del metodo */


    /**
     * Restituisce un pacchetto di tutte le Relazioni che raggiungono
     * una determinata tavola
     *
     * @param unaTavolaDestinazione tavola destinazione
     * @param unaTavolaRelazioni tavola di relazioni
     *
     * @return una lista di Relazioni che raggiungono la tavola
     */
    private static ArrayList pacchettoRelazioniVersoTavola(String unaTavolaDestinazione,
                                                           ArrayList unaTavolaRelazioni) {
        Relazione unaRelazione = null;
        ArrayList unaListaRelazioni = new ArrayList();

        /** spazzola la tavola relazioni ed estrae le Relazioni richieste */
        for (int k = 0; k < unaTavolaRelazioni.size(); k++) {
            unaRelazione = (Relazione)unaTavolaRelazioni.get(k);
            if (unaRelazione.getTavolaArrivo().equalsIgnoreCase(unaTavolaDestinazione)) {
                unaListaRelazioni.add(unaRelazione);
            } /* fine del blocco if */
        } /* fine del blocco for */

        return unaListaRelazioni;
    } /* fine del metodo */


    /**
     * Restituisce una lista di gruppi di percorsi con pari tavole
     *
     * @param unElencoPercorsi elenco di percorsi
     *
     * @return un elenco di gruppi di percorsi con pari tavole
     */
    private static ArrayList raggruppaPercorsiPariTavola(ArrayList unElencoPercorsi) {

        /** variabili e costanti locali di lavoro */
        ArrayList unElencoGruppiPercorsiUguali = new ArrayList();
        ArrayList unGruppoPercorsiUguali = null;
        RelazionePercorso unPercorso = null;
        RelazionePercorso unPercorsoGruppo = null;
        int indiceGruppo = 0;

        /** spazzola tutti i percorsi forniti e crea dei gruppi
         *  di percorsi "uguali" (percorsi con pari tavole)*/
        for (int k = 0; k < unElencoPercorsi.size(); k++) {

            // estrae il singolo percorso dall'elenco
            unPercorso = (RelazionePercorso)unElencoPercorsi.get(k);

            /** verifica nei gruppi esistenti se esiste gia' un gruppo
             *  con percorsi di pari tavole. */
            indiceGruppo = -1;
            for (int i = 0; i < unElencoGruppiPercorsiUguali.size(); i++) {

                // estrae il singolo gruppo
                unGruppoPercorsiUguali = (ArrayList)unElencoGruppiPercorsiUguali.get(i);

                /** estrae il primo percorso dal gruppo
                 *  (almeno un percorso dovrebbe sempre esistere perche' nel momento
                 *  in cui il gruppo viene creato vi viene aggiunto sempre un percorso) */
                if (unGruppoPercorsiUguali.size() > 0) {
                    unPercorsoGruppo = (RelazionePercorso)unGruppoPercorsiUguali.get(0);
                } else {
                    new MessaggioAvviso("Errore: rilevato gruppo senza percorsi");
                } /* fine del blocco if - else*/

                // controlla se i due percorsi sono pari tavole
                if (unPercorso.equalsTavole(unPercorsoGruppo)) {
                    indiceGruppo = i;
                    break;  // trovato, non occorre procedere oltre
                } /* fine del blocco if - else*/

            } /* fine del blocco for */

            /** aggiunge il percorso al gruppo appropriato */
            if (indiceGruppo < 0) {
                /** se non ha trovato gruppi dove mettere il percorso,
                 *  crea il gruppo ora, vi aggiunge il percorso,
                 *  e aggiunge il gruppo all'elenco dei gruppi. */
                ArrayList unNuovoGruppoPercorsiUguali = new ArrayList();
                unNuovoGruppoPercorsiUguali.add(unPercorso);
                unElencoGruppiPercorsiUguali.add(unNuovoGruppoPercorsiUguali);
            } else {
                /** se ha trovato un gruppo dove mettere il percorso,
                 *  aggiunge il percorso al gruppo esistente. */
                unGruppoPercorsiUguali = (ArrayList)unElencoGruppiPercorsiUguali.get(indiceGruppo);
                unGruppoPercorsiUguali.add(unPercorso);
            } /* fine del blocco if */

        } /* fine del blocco for */

        return unElencoGruppiPercorsiUguali;
    } /* fine del metodo */


    /**
     * Scarta i percorsi uguali da un elenco di percorsi
     *
     * @param unElencoPercorsi l'elenco di percorsi da verificare
     *
     * @return l'elenco di percorsi ridotto
     */
    private static ArrayList scartaPercorsiUguali(ArrayList unElencoPercorsi) {

        /** variabili e costanti locali di lavoro */
        ArrayList unElencoPercorsiRidotto = new ArrayList();
        RelazionePercorso unPercorsoA = null;
        RelazionePercorso unPercorsoB = null;
        boolean esistePercorso = false;

        /** scorre tutti i percorsi e li aggiunge alla lista
         *  ridotta solo se non ve ne sono gia' di uguali */
        for (int k = 0; k < unElencoPercorsi.size(); k++) {

            // estrae il singolo percorso
            unPercorsoA = (RelazionePercorso)unElencoPercorsi.get(k);

            // verifica se esiste un percorso uguale nella lista destinazione
            esistePercorso = false;
            for (int i = 0; i < unElencoPercorsiRidotto.size(); i++) {

                // estrae il singolo percorso
                unPercorsoB = (RelazionePercorso)unElencoPercorsiRidotto.get(i);

                // confronta i due percorsi
                if (unPercorsoA.equals(unPercorsoB)) {
                    esistePercorso = true;
                    break;
                } /* fine del blocco if */

            } /* fine del blocco for */

            // se non esiste, lo aggiunge
            if (esistePercorso == false) {
                unElencoPercorsiRidotto.add(unPercorsoA);
            } /* fine del blocco if */

        } /* fine del blocco for */

        return unElencoPercorsiRidotto;
    } /* fine del metodo */


    /**
     * Sostituisce le relazioni non preferite con quelle preferite
     * in un gruppo di percorsi uguali (pari tavole)
     *
     * @param unElencoPercorsiUguali il gruppo di percorsi da elaborare
     */
    private static void sostituisceRelazioniPreferite(ArrayList unElencoPercorsiUguali) {

        /** variabili e costanti locali di lavoro */
        RelazionePercorso primoPercorso = null;
        int numeroPassaggi = 0;
        int numeroPercorsi = 0;
        RelazionePercorso unPercorso = null;
        Relazione unaRelazione = null;
        Relazione unaRelazionePreferita = null;

        // recupera il numero di percorsi del gruppo
        if (unElencoPercorsiUguali != null) {
            numeroPercorsi = unElencoPercorsiUguali.size();
        } /* fine del blocco if */

        // recupera il numero di passaggi che compongono i percorsi
        if (numeroPercorsi > 0) {
            primoPercorso = (RelazionePercorso)unElencoPercorsiUguali.get(0);
            numeroPassaggi = primoPercorso.getRelazioni().size();
        } /* fine del blocco if */

        /** Spazzola orizzontalmete i gruppi di percorsi e sostituisce la
         *  relazione non preferita con la relazione preferita (se esiste)*/
        for (int k = 0; k < numeroPassaggi; k++) {

            // estrae le relazioni e le mette in una lista
            ArrayList unaListaRelazioni = new ArrayList();
            for (int i = 0; i < numeroPercorsi; i++) {
                unPercorso = (RelazionePercorso)unElencoPercorsiUguali.get(i);
                unaRelazione = unPercorso.getRelazione(k);
                unaListaRelazioni.add(unaRelazione);
            } /* fine del blocco for */

            // spazzola la lista alla ricerca di una eventuale relazione preferita
            unaRelazionePreferita = null;
            for (int i = 0; i < unaListaRelazioni.size(); i++) {
                unaRelazione = (Relazione)unaListaRelazioni.get(i);
                // Controlla se e' una relazione Preferita
                if (unaRelazione.isRelazionePreferita()) {
                    unaRelazionePreferita = unaRelazione;
                    break;  // non occorre procedere oltre
                } /* fine del blocco if */
            } /* fine del blocco for */

            // se trovata, la sostituisce nei relativi percorsi
            if (unaRelazionePreferita != null) {
                for (int i = 0; i < numeroPercorsi; i++) {
                    unPercorso = (RelazionePercorso)unElencoPercorsiUguali.get(i);
                    unPercorso.setRelazione(unaRelazione, k);
                } /* fine del blocco for */

            } /* fine del blocco if */

        } /* fine del blocco for */

    } /* fine del metodo */


    /**
     * Ritorna il campo linkato di un modulo Figlio che lo mette in relazione
     * diretta con il Modulo padre.
     * <p/>
     * Analizza la tavola relazioni e considera solo le relazioni dirette
     * (1 solo passaggio) tra il modulo Figlio e il modulo Padre.
     * Se c'e' piu' di una relazione diretta, usa la eventuale
     * relazione preferita.
     * NOTA: se questo metodo viene chiamato prima che il
     * modulo figlio sia relazionato, potrebbe non ritornare il campo di link
     * perche' la relazione non e' ancora stata stabilita.
     * Se si vuole un risultato sicuro, effettuare questa
     * chiamata solo dopo che il modulo figlio e' stato relazionato.
     *
     * @param mFiglio il modulo Figlio (righe)
     * @param mPadre il modulo Padre (testa)
     *
     * @return il campo di link del modulo figlio che lo mette
     *         in relazione con il modulo padre.
     *         null se non c'e' relazione diretta.
     */
    public static Campo getCampoLinkDiretto(Modulo mFiglio, Modulo mPadre) {
        /* variabili e costanti locali di lavoro */
        Campo campoLink = null;
        String tavolaPartenza = null;
        String tavolaDestinazione = null;
        ArrayList<Relazione> tavolaRelazioni = null;
        RelazionePercorso percorso = null;
        Relazione relazione = null;
        String nomeCampoPartenza = null;
        Campo campoPartenza = null;

        try {    // prova ad eseguire il codice

            tavolaPartenza = mFiglio.getModello().getTavolaArchivio();
            tavolaDestinazione = mPadre.getModello().getTavolaArchivio();
            tavolaRelazioni = Progetto.getTavolaRelazioni();

            /* Ricerca un percorso tra le due tavole.
             * questo metodo risolve automaticamente le
             * eventuali relazioni preferite */
            percorso = RelazioneManager.getPercorsoRelazione(tavolaPartenza,
                    tavolaDestinazione,
                    tavolaRelazioni,
                    null);

            if (percorso != null) {

                /* uso la relazione solo se e' diretta */
                if (percorso.getLunghezza() == 1) {
                    relazione = percorso.getRelazione(0);
                    nomeCampoPartenza = relazione.getNomeCampoPartenza();
                    campoPartenza = mFiglio.getCampo(nomeCampoPartenza);
                    if (campoPartenza != null) {
                        campoLink = campoPartenza;
                    }// fine del blocco if

                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoLink;
    }


    /**
     * Ritorna un albero contenente i moduli relazionati tra loro.
     * <p/>
     * Contiene solo i moduli che hanno relazioni.
     *
     * @return l'albero con i moduli relazionati
     */
    public static Albero getAlberoRelazioni() {
        /* variabili e costanti locali di lavoro */
        Albero albero = null;
        ArrayList<Relazione> tavola;
        AlberoNodo unNodo;
        AlberoNodo nodoPartenza;
        AlberoNodo nodoArrivo;
        Modulo moduloPartenza;
        Modulo moduloArrivo;

        try {    // prova ad eseguire il codice
            /* crea un albero in base alla tavola relazioni
             * (non contiene i moduli che non hanno relazioni) */
            albero = new Albero();
            tavola = Progetto.getTavolaRelazioni();
            for (Relazione rel : tavola) {
                moduloPartenza = rel.getModuloPartenza();
                moduloArrivo = rel.getModuloArrivo();
                unNodo = albero.getNodoOggetto(moduloArrivo);
                nodoPartenza = new AlberoNodo(moduloPartenza);
                nodoArrivo = new AlberoNodo(moduloArrivo);
                if (unNodo == null) {
                    nodoArrivo.add(nodoPartenza);
                    albero.addNodo(nodoArrivo, albero.getNodoRoot());
                } else {
                    albero.addNodo(nodoPartenza, unNodo);
                }// fine del blocco if-else
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return albero;
    }


}// fine della classe