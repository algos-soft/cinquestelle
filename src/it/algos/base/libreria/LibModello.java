/**
 * Title:     LibModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-gen-2005
 */
package it.algos.base.libreria;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.sql.DbSql;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.QueryFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe astratta con metodi statici. </p> Tutti i metodi sono statici <br> La classe contiene
 * metodi di utilità specifici del Modello <br> I metodi non hanno modificatore così sono visibili
 * all'esterno del package solo utilizzando l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-gen-2005 ore 10.47.03
 */
public abstract class LibModello {

    /**
     * Restituisce il campo valore richiesto.
     * <p/>
     *
     * @param lista array coppia campo-valore
     * @param campo da ricercare
     *
     * @return campo-valore richiesto
     */
    static CampoValore getCampoValore(ArrayList<CampoValore> lista, Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore campoValore = null;
        boolean continua;
        Campo unCampo;

        try { // prova ad eseguire il codice
            continua = campo != null;

            if (continua) {
                /* traverso tutta la collezione */
                for (CampoValore cv : lista) {
                    /* recupera il campo valore */
                    unCampo = cv.getCampo();

                    /* controlla se è quello richiesto */
                    if (unCampo.equals(campo)) {
                        campoValore = cv;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoValore;
    }


    /**
     * Cancella dal db gli eventuali campi non contenuti nel Modello.
     * <p/>
     *
     * @param modello il modello di riferimento
     *
     * @return true se ha eliminato correttamente i campi superflui o se non ci sono campi superflui
     *         da eliminare
     */
    static boolean eliminaCampiSuperflui(Modello modello) {

        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        Db db;
        Modulo modulo;
        Connessione conn;
        String tavola;
        ArrayList<String> elencoCampiModello;
        ArrayList<String> elencoCampiDB;
        ArrayList<String> elencoCampiSuperflui;
        String unNomeCampoDB;
        Campo unCampo;
        String stringa;
        boolean elimina;

        try {    // prova ad eseguire il codice

            /* recupera il dbatabase */
            db = modello.getDb();

            /* recupera il modulo */
            modulo = modello.getModulo();

            /* recupera la connessione del modulo */
            conn = modulo.getConnessione();

            /* recupera la tavola */
            tavola = modello.getTavolaArchivio();

            /* recupera l'elenco dei campi fisicamente presenti sul database */
            elencoCampiDB = db.getCampiTavola(tavola, conn);

            /* recupera l'elenco dei nomi dei campi fisici del Modello */
            elencoCampiModello = new ArrayList<String>();
            HashMap<String, Campo> campi = modello.getCampiFisici();
            /* traverso tutta la collezione */
            for (Campo campo : campi.values()) {
                stringa = campo.getNomeInterno();
                elencoCampiModello.add(stringa);
            } // fine del ciclo for-each

            /*
            * confronta i due elenchi ed estrae i campi che appaiono sul DB
            * ma non sul Modello
            */
            elencoCampiSuperflui = new ArrayList<String>();
            for (String stringaDB : elencoCampiDB) {

                stringaDB = stringaDB.toLowerCase();

                /* traverso tutta la collezione */
                boolean trovato = false;
                for (String unaStringa : elencoCampiModello) {
                    unaStringa = unaStringa.toLowerCase();
                    if (unaStringa.equals(stringaDB)) {
                        trovato = true;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

                if (!trovato) {
                    elencoCampiSuperflui.add(stringaDB);
                }// fine del blocco if
            } // fine del ciclo for-each

            /* elimina fisicamente i campi superflui dal DB */
            for (int k = 0; k < elencoCampiSuperflui.size(); k++) {

                unNomeCampoDB = elencoCampiSuperflui.get(k);

                /*
                 * Ora lavoriamo su un campo presente nel DB ma non
                 * nel Modello, e dobbiamo effettuare delle Query
                 * su questo campo.
                 * Percio' creiamo un oggetto Campo provvisorio
                 * per costruire le Query.
                 */
                unCampo = db.creaCampoColonna(modulo, tavola, unNomeCampoDB, conn);

                /*
                 * controlla se il campo e' vuoto
                 * - se e' vuoto, da' il permesso di eliminarlo
                 * - se non e' vuoto, acquisisce il permesso dalle preferenze
                 */
                elimina = false;
                if (conn.isCampoVuoto(unCampo)) {
                    elimina = true;
                } else {
                    /* legge la preferenza per controllare il permesso
                     * non elimina se non vuoti */
                    boolean eliminaLoc = false;

                    if (Pref.DB.superflui.is()) {
                        elimina = eliminaLoc;
                    } /* fine del blocco if */

                }// fine del blocco if-else

                /* se ha ottenuto il permesso, elimina il campo */
                if (elimina) {
                    eseguito = db.eliminaColonna(unCampo, conn);
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Restituisce i nomi per il db dei campi fisici del modello
     * <p/>
     *
     * @param modello il modello di riferimento
     *
     * @return la lista dei nomi
     */
    static ArrayList getNomiDbCampiFisiciModello(Modello modello) {
        /* variabili e costanti locali di lavoro */
        ArrayList listaNomiDb = null;
        Db db;
        Iterator unIteratore;
        Campo unCampo;
        String unNomeDb;

        try {    // prova ad eseguire il codice

            /* crea la lista da ritornare */
            listaNomiDb = new ArrayList();

            /* recupera il database */
            db = modello.getModulo().getDb();

            /*
             * traversa tutta la collezione dei campi fisici del modello
             * e chiede al Db i nomi dei campi
             */
            unIteratore = modello.getCampiFisici().values().iterator();
            while (unIteratore.hasNext()) {
                unCampo = (Campo)unIteratore.next();
                unNomeDb = db.getStringaCampo(unCampo);
                listaNomiDb.add(unNomeDb);
            } /* fine del blocco while */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaNomiDb;
    }


    /**
     * Registra i dati di default.
     * <p/>
     * Registra sul database dei dati di default i dati contenuti nella tavola di un modello,
     * prendendoli dal database in uso al Modulo <br> I dati di default vengono completamente
     * sostituiti <br>
     *
     * @param modello il modello di riferimento
     *
     * @return true se eseguito correttamente
     */
    static boolean registraDatiDefault(Modello modello) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        Modulo modulo;
        MessaggioDialogo dialogo;
        String testo = "";
        int risposta;
        DbSql dbDefault = null;
        String tavola;
        Connessione conn = null;
        boolean continua;
        boolean connAperta = false;
        ArrayList<Campo> campiDaCopiare = null;
        Campo campo;
        Query query;
        Dati dati = null;
        CampoValore campoValore;
        CampoValore[] campiValore;
        ArrayList<CampoValore> listaValori;
        Object valore;
        int codice;
        int count = 0;

        try {    // prova ad eseguire il codice
            continua = true;
            modulo = modello.getModulo();
            tavola = modello.getTavolaArchivio();

            /* chiede conferma all'utente */
            if (continua) {
                testo += "Vuoi registrare i dati correnti del modulo ";
                testo += modulo.getNomeChiave() + "\n";
                testo += "come dati di default?";
                dialogo = new MessaggioDialogo(testo);
                risposta = dialogo.getRisposta();
                if (risposta == 1) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il db di default*/
            if (continua) {
                Progetto.creaDbDefault();
                dbDefault = (DbSql)Progetto.getIstanza().getDbDefault();
                if (dbDefault == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* crea e apre una connessione al db dati default */
            if (continua) {
                conn = dbDefault.creaConnessione();
                if (conn != null) {
                    conn.open();
                    connAperta = true;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* se esiste, elimina la tavola dal db di default */
            if (continua) {
                if (conn.isEsisteTavola(tavola)) {
                    if (!conn.eliminaTavola(tavola)) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* crea la tavola col campo chiave provvisorio (obbligatorio)*/
            if (continua) {
                if (!conn.creaTavola(modulo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* elimina il campo chiave creato con la tavola */
            if (continua) {
                campo = modello.getCampoChiave();
                if (!conn.eliminaColonna(campo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* Crea l'elenco dei campi da copiare.
             * Crea un clone del campo chiave e gli annulla
             * l'inizializzatore in modo da non incrementare
             * il contatore del database dei dati.
             * Il codice del record nei dati default sarà zero
             * (nei dati default non serve) */
            if (continua) {
                HashMap<String, Campo> campiMod = modello.getCampiFisici();
                campiDaCopiare = new ArrayList<Campo>();
                Campo campoChiave = modello.getCampoChiave();
                Campo cloneChiave = campoChiave.clonaCampo();
                cloneChiave.setInit(null);
                campiDaCopiare.add(cloneChiave);
                for (Campo unCampo : campiMod.values()) {
                    if (!unCampo.equals(modello.getCampoChiave())) {
                        campiDaCopiare.add(unCampo);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            /* crea tutti i campi fisici da copiare sul db default */
            if (continua) {
                for (Campo unCampo : campiDaCopiare) {
                    if (!conn.creaColonna(unCampo)) {
                        continua = false;
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

            /* recupera tutti i dati da copiare del modulo */
            if (continua) {
                query = new QuerySelezione(modulo);
                query.setCampi(campiDaCopiare);
                dati = modulo.query().querySelezione(query);
                if (dati == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* spazzola i record e li copia sul db default */
            if (continua) {
                int quantiCampi = campiDaCopiare.size();
                campiValore = new CampoValore[quantiCampi];

                /* crea un array di oggetti CampoValore con i campi */
                for (int k = 0; k < quantiCampi; k++) {
                    campo = (Campo)campiDaCopiare.get(k);
                    campiValore[k] = new CampoValore(campo, null);
                } // fine del ciclo for

                /* spazzola i dati e crea i record */
                for (int k = 0; k < dati.getRowCount(); k++) {

                    /* regola i valori */
                    for (int j = 0; j < dati.getColumnCount(); j++) {
                        valore = dati.getValueAt(k, j);
                        campoValore = campiValore[j];
                        campoValore.setValore(valore);
                    } // fine del ciclo for
                    listaValori = Libreria.objectArrayToArrayList(campiValore);

                    /* crea il nuovo record con i valori */
                    codice = modulo.query().nuovoRecord(listaValori, conn);

                    /* controlla se riuscito */
                    if (codice != -1) {
                        count++;
                    } else {
                        continua = false;
                        break;
                    }// fine del blocco if-else

                } // fine del ciclo for

            }// fine del blocco if

            /* regola il valore di ritorno */
            if (continua) {
                eseguito = true;
            }// fine del blocco if

            /* visualizza un messaggio al termine */
            if (eseguito) {
                new MessaggioAvviso("Operazione eseguita (" + count + " records)");
            } else {
                new MessaggioAvviso("Operazione non eseguita.");
            }// fine del blocco if-else

            /* chiude la connessione e spegne il database */
            if (connAperta) {
                conn.close();
                dbDefault.shutdown();
            }// fine del blocco if

            /* chiude l'oggetto dati */
            if (dati != null) {
                dati.close();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Carica i dati di default per questo Modulo/Modello.
     * <p/>
     *
     * @param modello il modello di riferimento
     */
    static void caricaDatiDefault(Modello modello) {
        /* variabili e costanti locali di lavoro */
        Db dbDefault;
        Modulo modulo;
        Connessione conn;
        Dati datiDefault;
        ArrayList<Campo> campiModello;
        ArrayList<Campo> campiQuery;
        Campo campo;
        Query qs;
        ArrayList<CampoValore> listaCV;

        try {    // prova ad eseguire il codice

            /* recupera dal Progetto il Db contenente i dati di default */
            dbDefault = Progetto.getIstanza().getDbDefault();

            /* recupera il modulo */
            modulo = modello.getModulo();

            /* crea e apre una connessione */
            conn = dbDefault.creaConnessione();
            conn.open();

            /* crea una query per caricare i dati di default */
            qs = new QuerySelezione(modulo);

            /*
             * Spazzola i campi fisici del Modello.
             * Se esistono anche sul database di default
             * li aggiunge all'elenco dei campi per la query
             */
            campiModello = Libreria.hashMapToArrayList(modello.getCampiFisici());
            campiQuery = new ArrayList<Campo>();
            for (int k = 0; k < campiModello.size(); k++) {
                campo = campiModello.get(k);
                if (dbDefault.isEsisteCampo(campo, conn)) {
                    campiQuery.add(campo);
                }// fine del blocco if
            } // fine del ciclo for

            /* assegna i campi trovati alla query */
            qs.setCampi(campiQuery);

            /* ordina la query sul campo chiave */
            if (dbDefault.isEsisteCampo(modello.getCampoChiave(), conn)) {
                qs.addOrdine(modello.getCampoChiave());
            }// fine del blocco if

            /* esegue la query e recupera il risultato */
            datiDefault = conn.querySelezione(qs);

            /* spazzola tutte le righe dei dati default
             * per ognuna crea la corrispondente riga sul database del Modulo */
            for (int k = 0; k < datiDefault.getRowCount(); k++) {
                listaCV = datiDefault.getCampiValore(k);
                modulo.query().nuovoRecord(listaCV);
            } // fine del ciclo for

            /* chiude l'oggetto dati */
            datiDefault.close();

            /* chiude la connessione al database dati default */
            conn.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se esistono dei dati di default per un Modello.
     * <p/>
     * Cerca nel database di default la tavola del modello e controlla che ci siano dei dati <br>
     *
     * @param modello il modello di riferimento
     *
     * @return true se esistono dei dati di default
     */
    static boolean isEsistonoDatiStandard(Modello modello) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean continua;
        DbSql dbDefault;
        String tavola;
        Connessione conn = null;
        boolean connAperta = false;
        Query query;
        Dati dati = null;
        int quanti;

        try {    // prova ad eseguire il codice

            continua = true;
            dbDefault = (DbSql)Progetto.getIstanza().getDbDefault();
            tavola = modello.getTavolaArchivio();

            /* controlla se esiste un Db di default */
            if (continua) {
                if (dbDefault == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controlla se il database e' disponibile alla connessione */
            if (continua) {
                if (dbDefault.isDisponibileConnessioni() == false) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* crea e apre una connessione al db dati default */
            if (continua) {
                conn = dbDefault.creaConnessione();
                if (conn != null) {
                    conn.open();
                    connAperta = true;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* controlla se esiste la tavola */
            if (continua) {
                if (conn.isEsisteTavola(tavola) == false) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* conta i records sulla tavola di default */
            if (continua) {
                query = QueryFactory.conta(modello.getModulo());
                dati = conn.querySelezione(query);
                if (dati == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controlla se c'e' almeno 1 record */
            if (continua) {
                if (dati.getRowCount() > 0) {
                    quanti = ((Integer)dati.getValueAt(0, 0)).intValue();
                    if (quanti > 0) {
                        esistono = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* chiude l'oggetto dati */
            if (dati != null) {
                dati.close();
            }// fine del blocco if

            /* chiude la connessione */
            if (connAperta) {
                conn.close();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


}// fine della classe
