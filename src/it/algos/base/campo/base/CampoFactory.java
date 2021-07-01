/**
 * Title:        CampoFactory.java
 * Package:      it.algos.base.campo.base
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 5 luglio 2003 alle 9.40
 */
package it.algos.base.campo.base;

import it.algos.base.campo.dati.*;
import it.algos.base.campo.db.*;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.campo.logica.*;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.*;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.validatore.ValidatoreFactory;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.Navigatori;

import java.util.ArrayList;

/**
 * Classe astratta che fornisce i <i>Factory Method</i> per la creazione
 * dei <code>Campi</code>.
 * <br>
 * Questa classe statica crea i seguenti tipi di <code>Campi</code>: <br>
 * <li> campo testo </li>
 * <li> campo sigla - una specializzazione del tipo testo </li>
 * <li> campo descrizione - una specializzazione del tipo testo </li>
 * <li> campo comboLink - un combo box con valori esterni </li>
 * <li> campo comboInterno - un combo box con valori interni </li>
 * <li> campo check - un campo spunta di tipo booleano </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  5 luglio 2003 ore 9.40
 */
public abstract class CampoFactory {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Uso il modificatore <i>private<i/> perche' la classe e' astratta <br>
     */
    private CampoFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * Crea un campo di tipo testo.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <p/>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * </ul>
     *
     * @param nomeCampo        nome interno per recuperare il campo dalla collezione
     * @param larghezzaColonna larghezza della colonna della lista
     * @param larghezzaScheda  larghezza del pannello campo nella scheda
     * @param larghezzaMinima  larghezza minima della colonna
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#testo
     * @see CampoFactory#sigla
     * @see CampoFactory#descrizione
     * @see CampoFactory#testoArea
     * @see CampoBase
     * @see CampoLista
     * @see CampoScheda
     * @since 11-5-04
     */
    private static Campo creaTesto(String nomeCampo,
                                   int larghezzaColonna,
                                   int larghezzaScheda,
                                   int larghezzaMinima) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice
            /* invoca il metodo delegato di questa classe */
            unCampo = testo(nomeCampo);

            /* regola le dimensioni */
            unCampo.setLarLista(larghezzaColonna);
            unCampo.setLarScheda(larghezzaScheda);
            unCampo.getCampoLista().setLarghezzaMinima(larghezzaMinima);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo testo.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 80 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default la larghezza del campo in scheda e' di 150 </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @since 11-5-04
     */
    public static Campo testo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo campoVideo;
        CampoLogica campoLogica;

        try { // prova ad eseguire il codice
            /* crea l'instanza */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVTestoField(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* crea il campo logica e lo sostituisce */
            campoLogica = new CLTesto(unCampo);
            unCampo.setCampoLogica(campoLogica);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo testo.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 80 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default la larghezza del campo in scheda e' di 150 </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @since 11-5-04
     */
    public static Campo testo(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.testo(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo testo.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 80 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default la larghezza del campo in scheda e' di 150 </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @since 11-5-04
     */
    public static Campo testos(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.testo(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo password.
     * <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo password(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo campoVideo;

        try { // prova ad eseguire il codice

            unCampo = testo(nomeCampo);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVPasswordField(unCampo);
            unCampo.setCampoVideo(campoVideo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo password.
     * <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo password(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.password(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo Codice Fiscale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un campo testo </li>
     * <li> assegna il validatore per il codice fiscale </li>
     * <p/>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo codiceFiscale(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati cd;

        try { // prova ad eseguire il codice

            /* crea l'instanza */
            unCampo = CampoFactory.testo(nomeCampo);

            unCampo.setLarLista(130);
            unCampo.setLarScheda(130);

            /* assegna il validatore codice fiscale */
            cd = unCampo.getCampoDati();
            cd.setValidatore(ValidatoreFactory.codiceFiscale());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo Codice Fiscale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un campo testo </li>
     * <li> assegna il validatore per il codice fiscale </li>
     * <p/>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo codiceFiscale(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.codiceFiscale(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo Partita IVA.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un campo testo </li>
     * <li> assegna il validatore per il codice fiscale </li>
     * <p/>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo partitaIVA(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati cd;

        try { // prova ad eseguire il codice

            /* crea l'instanza */
            unCampo = CampoFactory.testo(nomeCampo);

            unCampo.setLarLista(90);
            unCampo.setLarScheda(90);

            /* assegna il validatore codice fiscale */
            cd = unCampo.getCampoDati();
            cd.setValidatore(ValidatoreFactory.partitaIVA());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo Partita IVA.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un campo testo </li>
     * <li> assegna il validatore per il codice fiscale </li>
     * <p/>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo partitaIVA(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.partitaIVA(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Regola alcune caratteristiche del campo.
     * <p/>
     * Utilizza quanto stabilito nella Enumeration dell'interfaccia <br>
     *
     * @param campo   della Enumeration dell'interfaccia
     * @param unCampo campo già esistente da regolare
     */
    private static void regola(Campi campo, Campo unCampo) {

        try { // prova ad eseguire il codice
            CampoFactory.regolaLista(campo, unCampo);
            CampoFactory.regolaScheda(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola alcune caratteristiche del campo.
     * <p/>
     * Utilizza quanto stabilito nella Enumeration dell'interfaccia <br>
     *
     * @param campo   della Enumeration dell'interfaccia
     * @param unCampo campo già esistente da regolare
     */
    private static void regolaLista(Campi campo, Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        boolean visibileLista;
        String titoloColonna;

        try { // prova ad eseguire il codice

            /* recupera alcuni parametri dalla Enumeration */
            visibileLista = campo.isVisibileLista();
            titoloColonna = campo.getTitoloColonna();

            /* regola la visibilità nella lista */
            unCampo.setVisibileVistaDefault(visibileLista);

            /* regola il voce della colonna */
            if (Lib.Testo.isValida(titoloColonna)) {
                unCampo.setTitoloColonna(titoloColonna);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola alcune caratteristiche del campo.
     * <p/>
     * Utilizza quanto stabilito nella Enumeration dell'interfaccia <br>
     *
     * @param campo   della Enumeration dell'interfaccia
     * @param unCampo campo già esistente da regolare
     */
    private static void regolaScheda(Campi campo, Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        String etichettaScheda;
        String legenda;

        try { // prova ad eseguire il codice
            /* recupera alcuni parametri dalla Enumeration */
            etichettaScheda = campo.getEtichettaScheda();
            legenda = campo.getLegenda();

            /* regola l'etichetta della scheda */
            unCampo.decora().eliminaEtichetta(); // elimina se già presente
            if (Lib.Testo.isValida(etichettaScheda)) {
                unCampo.decora().etichetta(etichettaScheda);
            } else {
                etichettaScheda = unCampo.getNomeInterno();
                unCampo.decora().etichetta(etichettaScheda);
            }// fine del blocco if-else

            /* regola la legenda della scheda */
            unCampo.decora().eliminaLegenda();  // elimina se già presente
            if (Lib.Testo.isValida(legenda)) {
                unCampo.decora().legenda(legenda);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un campo testo di tipo sigla.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <li> di default il campo non può essere vuoto </li>
     * <p/>
     * <li> di default il campo e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la colonna della Lista e' larga 100 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 50 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 100 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see Modello
     * @see CampoFactory#creaTesto
     * @see CampoLista
     * @since 11-5-04
     */
    public static Campo sigla(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        int LAR_LISTA = 80;
        int LAR_SCHEDA = 100;
        int LAR_MINIMA = 50;
        String legenda = "sigla come appare nelle liste di altri moduli";

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = creaTesto(nomeCampo, LAR_LISTA, LAR_SCHEDA, LAR_MINIMA);

            /* regola alcuni parametri di default del campo sigla */
            unCampo.setVisibileVistaDefault(true);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.decora().legenda(legenda);
            unCampo.decora().obbligatorio();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo testo di tipo sigla.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <li> di default regola il nome interno del campo con la costante "sigla" </li>
     * <li> di default il campo non può essere vuoto </li>
     * <p/>
     * <li> di default il campo e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la colonna della Lista e' larga 100 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 50 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 100 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see Modello
     * @see CampoFactory#creaTesto
     * @see CampoLista
     * @since 11-5-04
     */
    public static Campo sigla() {
        /* invoca il metodo sovrascritto */
        return CampoFactory.sigla(Modello.NOME_CAMPO_SIGLA);
    } /* fine del metodo */


    /**
     * Crea un campo testo di tipo sigla.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <li> di default il campo non può essere vuoto </li>
     * <p/>
     * <li> di default il campo e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la colonna della Lista e' larga 100 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 50 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 100 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see Modello
     * @see CampoFactory#creaTesto
     * @see CampoLista
     * @since 11-5-04
     */
    public static Campo sigla(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = sigla(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo testo di tipo descrizione.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 150 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 100 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 250 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see Modello
     * @see CampoFactory#creaTesto
     * @since 11-5-04
     */
    public static Campo descrizione(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        int LAR_LISTA = 200;
        int LAR_SCHEDA = 250;
        int LAR_MINIMA = 100;

        String legenda = "descrizione completa";
        try { // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = creaTesto(nomeCampo, LAR_LISTA, LAR_SCHEDA, LAR_MINIMA);

            /* regola alcuni parametri di default del campo sigla */
            unCampo.decora().legenda(legenda);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo testo di tipo descrizione.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <li> di default regola il nome interno del campo con la costante "descrizione" </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 150 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 100 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 250 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see Modello
     * @see CampoFactory#creaTesto
     * @since 11-5-04
     */
    public static Campo descrizione() {
        /* invoca il metodo sovrascritto */
        return CampoFactory.descrizione(Modello.NOME_CAMPO_DESCRIZIONE);
    } /* fine del metodo */


    /**
     * Crea un campo testo di tipo descrizione.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 150 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 100 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 250 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see Modello
     * @see CampoFactory#creaTesto
     * @since 11-5-04
     */
    public static Campo descrizione(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = descrizione(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo testo su piu' righe.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo-area </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 150 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 100 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 250 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaTesto
     * @see CVTestoArea
     * @since 6-6-04
     */
    public static Campo testoArea(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoVideo unCampoVideo;
        CampoLista campoLista;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = testo(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDTestoArea(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVTestoArea(unCampo);
            unCampo.setCampoVideo(unCampoVideo);

            /* regola alcuni parametri del CampoLista di default */
            campoLista = unCampo.getCampoLista();
            unCampo.setLarLista(40);
            campoLista.setRidimensionabile(true);
            campoLista.setLarghezzaMinima(40);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo testo su piu' righe.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo-area </li>
     * <li> crea un CampoDati di tipo testo </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 150 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 100 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 250 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaTesto
     * @see CVTestoArea
     * @since 6-6-04
     */
    public static Campo testoArea(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.testoArea(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo numero.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <p/>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 40 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 40 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CampoDB
     * @see CampoLista
     * @see CampoScheda
     * @since 6-6-04
     */
    private static Campo creaNumero(String nomeCampo) {
        /** variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo campoVideo;
        CampoLogica campoLogica;
        CampoLista unCampoLista;

        try {    // prova ad eseguire il codice
            /* crea l'instanza del campo */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVNumero(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* crea il campo logica e lo sostituisce */
            campoLogica = new CLNumero(unCampo);
            unCampo.setCampoLogica(campoLogica);

//            /* regola alcuni parametri del CampoDB di default */
//            unCampo.getCampoDB().setFunzioneOrdinamento(null);

            /* regola alcuni parametri del CampoLista di default */
            unCampoLista = unCampo.getCampoLista();
            unCampoLista.setPresenteVistaDefault(true);
            unCampo.setVisibileVistaDefault(false);
            unCampo.setLarLista(40);
            unCampoLista.setRidimensionabile(false);
            unCampoLista.setLarghezzaMinima(40);

//            /* regola alcuni parametri del CampoScheda di default */
//            unCampo.setLarghezzaComponentiScheda(50);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo numero intero.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo intero </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 40 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 20 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDIntero
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo intero(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;

        try {    // prova ad eseguire il codice
            /* invoca il metodo delegato di questa classe */
            unCampo = creaNumero(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDIntero(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* larghezza minima della colonna */
            unCampo.getCampoLista().setLarghezzaMinima(20);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo numero intero.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo intero </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 40 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 20 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDIntero
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo intero(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.intero(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo Estratto Interno.
     * Mostra a video un estratto di un altro modulo.
     * E' un campo fisico e registra sul database un intero
     * corrispondente al codice record del modulo esterno.
     * Usa come modulo esterno il modulo dell'estratto.
     * Permette di creare, modificare e cancellare il record del modulo esterno.
     * Di default utilizza per l'editing la scheda pop del modulo esterno.
     * Permette di specificare la scheda da utilizzare per l'editing (campo.setSchedaPop("laMiaScheda")).
     *
     * @param campo    della Enumeration dell'interfaccia
     * @param estratto da visualizzare
     *
     * @return il campo creato
     */
    public static Campo estrattoInterno(Campi campo, Estratti estratto) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo campoVideo;
        CLEstratto campoLogica;
        CampoDati unCampoDati;
        CDBLinkato unCampoDB;

        try { // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = new CampoBase(campo.get());

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDEstrattoInterno(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* crea il campo db e lo sostituisce */
            unCampoDB = new CDBLinkato(unCampo);
            unCampoDB.setLinkato(false);
            unCampoDB.setNomeModuloLinkato(estratto.getNomeModulo());
            unCampo.setCampoDB(unCampoDB);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVEstratto(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* crea il campo logica e lo sostituisce */
            campoLogica = new CLEstrattoInterno(unCampo);
            campoLogica.setEstratto(estratto);
            unCampo.setCampoLogica(campoLogica);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo Estratto Link.
     * Mostra a video un estratto di un altro modulo.
     * Non è un campo fisico e non registra nulla sul database.
     * Usa come modulo esterno il modulo dell'estratto.
     * Implementa un link 1:1 tra la tavola del campo e la tavola dell'estratto.
     * Permette di creare, modificare e cancellare il record del modulo esterno.
     * Mantiene un intero in memoria fornito nella implementazione specifica (scheda/dialogo)
     * Il valore memoria rappresenta il valore del campo link esterno.
     * Usa il valore memoria per recuperare l'estratto dal modulo collegato
     * Di default utilizza per l'editing la scheda pop del modulo esterno.
     * Permette di specificare la scheda da utilizzare per l'editing (campo.setSchedaPop("laMiaScheda")).
     *
     * @param campo     della Enumeration dell'interfaccia
     * @param estratto  da visualizzare
     * @param campoLink del modulo esterno (il modulo dell'Estratto)
     *
     * @return il campo creato
     */
    public static Campo estrattoEsterno(Campi campo, Estratti estratto, Campi campoLink) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo campoVideo;
        CLEstratto campoLogica;
        CampoDati unCampoDati;
        CDBLinkato unCampoDB;

        try { // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = new CampoBase(campo.get());

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDEstrattoEsterno(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* crea il campo db e lo sostituisce */
            unCampoDB = new CDBLinkato(unCampo);
            unCampoDB.setLinkato(false);
            unCampoDB.setCampoFisico(false);
            unCampoDB.setNomeModuloLinkato(estratto.getNomeModulo());
            unCampoDB.setNomeCampoValoriLinkato(campoLink.get());
            unCampo.setCampoDB(unCampoDB);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVEstratto(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* crea il campo logica e lo sostituisce */
            campoLogica = new CLEstrattoEsterno(unCampo);
            campoLogica.setEstratto(estratto);
            unCampo.setCampoLogica(campoLogica);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

//            /* crea l'instanza del campo */
//            unCampo = new CampoBase(campo.get());
//
//
//            /* crea il campo dati e lo sostituisce */
//            unCampoDati = new CDEstratto(unCampo);
//            unCampo.setCampoDati(unCampoDati);
//
//            /* crea il campo db e lo sostituisce */
//            unCampoDB = new CDBEstratto(unCampo);
//            unCampoDB.setEstratto(estratto);
//            unCampo.setCampoDB(unCampoDB);
//
//            /* crea il campo video e lo sostituisce */
//            campoVideo = new CVEstratto(unCampo);
//            unCampo.setCampoVideo(campoVideo);
//
//            /* crea il campo logica e lo sostituisce */
//            campoLogica = new CLEstratto(unCampo);
//            unCampo.setCampoLogica(campoLogica);
//
//            /* recupera alcuni parametri dalla Enumeration */
//            CampoFactory.regola(campo, unCampo);

//            unCampo = CampoFactory.estrattoInterno(campo, estratto);
//
//            /* regola il campo Db*/
//            unCampoDB = (CDBEstratto)unCampo.getCampoDB();
//            unCampoDB.setCampoFisico(false);
//            unCampoDB.setNomeCampoLink(campoLink.get());
//
//            /* crea il campo dati e lo sostituisce */
//            unCampoDati = new CDIntero(unCampo);
//            unCampo.setCampoDati(unCampoDati);
//
//            /* crea il campo logica e lo sostituisce */
//            campoLogica = new CLEstrattoLink(unCampo);
//            unCampo.setCampoLogica(campoLogica);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo numero reale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo numero reale </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 40 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 20 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDIntero
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo reale(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = creaNumero(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDReale(unCampo);
            unCampo.setCampoDati(unCampoDati);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo numero reale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo numero reale </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 40 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 20 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDIntero
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo reale(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.reale(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo numero valuta.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo valuta (big decimal) </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 75 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza variabile </li>
     * <li> di default la larghezza minima della colonna e' di 30 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDValuta
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo valuta(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = reale(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDValuta(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* regola alcuni parametri per la lista */
            unCampo.setLarghezza(70);
            unCampo.setRidimensionabile(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo numero valuta.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo valuta (big decimal) </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 75 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza variabile </li>
     * <li> di default la larghezza minima della colonna e' di 30 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDValuta
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo valuta(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.valuta(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo percentuale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo percentuale </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 50 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDValuta
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo percentuale(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoVideo campoVideo;
        CampoLista campoLista;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del campo */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVPercentuale(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* regola alcuni parametri del CampoLista di default */
            campoLista = unCampo.getCampoLista();
            campoLista.setPresenteVistaDefault(true);
            unCampo.setVisibileVistaDefault(false);
            campoLista.setRidimensionabile(false);

            /* regola alcuni parametri di default */
            unCampo.setLarLista(60);
            unCampo.setLarScheda(50);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDPercentuale(unCampo);
            unCampo.setCampoDati(unCampoDati);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo percentuale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo percentuale </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 50 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 50 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDValuta
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo percentuale(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.percentuale(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo sconto percentuale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo sconto percentuale </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 30 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 30 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDValuta
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo sconto(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del campo */
            unCampo = CampoFactory.percentuale(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDSconto(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* regola alcuni parametri di default */
            unCampo.setLarLista(30);
            unCampo.setLarScheda(20);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo sconto percentuale.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo sconto percentuale </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo non e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 30 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 30 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoFactory#creaNumero
     * @see CDValuta
     * @see CampoLista
     * @since 6-6-04
     */
    public static Campo sconto(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.sconto(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo data.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo data </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 70 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 70 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 100 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CDData
     * @see CampoDB
     */
    public static Campo data(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoLista unCampoLista;
        CampoVideo campoVideo;
        int lar = 75;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = testo(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDData(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVData(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* di default suggerisce la data attuale */
            unCampo.setInit(InitFactory.dataAttuale());

            /* regola alcuni parametri del CampoLista di default */
            unCampoLista = unCampo.getCampoLista();
            unCampo.setVisibileVistaDefault(true);
            unCampo.setLarLista(lar);
            unCampoLista.setRidimensionabile(false);
            unCampoLista.setLarghezzaMinima(lar);

            /* regola alcuni parametri del CampoScheda di default */
            unCampo.setLarScheda(lar);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo data.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo data </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <br>
     * <li> di default il campo e' visibile in lista </li>
     * <li> di default la colonna della Lista e' larga 70 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 70 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 100 pixel </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CDData
     * @see CampoDB
     */
    public static Campo data(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.data(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo Timestamp.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo timestamp </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see it.algos.base.campo.dati.CDTimestamp
     * @see CampoDB
     */
    public static Campo timestamp(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoVideo campoVideo;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVTimestamp(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDTimestamp(unCampo);
            unCampo.setCampoDati(unCampoDati);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo Time.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo time </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see it.algos.base.campo.dati.CDTimestamp
     * @see CampoDB
     */
    public static Campo ora(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoVideo campoVideo;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo video e lo sostituisce */
            campoVideo = new CVOra(unCampo);
            unCampo.setCampoVideo(campoVideo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDOra(unCampo);
            unCampo.setCampoDati(unCampoDati);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo Time.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDati di tipo time </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see it.algos.base.campo.dati.CDTimestamp
     * @see CampoDB
     */
    public static Campo ora(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.ora(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo controllo singolo di tipo booleano (checkbox o radiobottone).
     * <br>
     * Questo metodo factory: <ul>
     * <li> non necessita di ulteriori regolazioni obbligatorie </li>
     * <li> crea un CampoVideo standard da modificare </li>
     * <li> crea un CampoDati di tipo booleano </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' larga 30 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 30 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default elimina l'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in scheda dipende dal testo </li>
     * <li> di default regola il testo del checkbox/radiobox col nome del campo </li>
     * </ul>
     * Se si rende il campo visibile in Lista, occorre modificare il voce
     * della colonna (visto che e' larga solo 30) <br>
     * Eventualmente regolare il testo con setTestoControllo (per avere una
     * descrizione piu' accurata) <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CDBooleano
     * @see CDBBase
     * @since 7-5-04
     */
    private static Campo controlloBooleano(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoLista unCampoLista;

        try {    // prova ad eseguire il codice
            /* crea l'instanza del campo */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDBooleano(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* regola alcuni parametri del CampoLista di default */
            unCampoLista = unCampo.getCampoLista();
            unCampo.setLarLista(30);
            unCampo.setRidimensionabile(false);
            unCampoLista.setLarghezzaMinima(30);

            /*
             * pone la larghezza di default a zero
             * di modo che venga determinata in base ai componenti stessi
             */
            unCampo.getCampoScheda().setLarghezzaComponenti(0);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo checkbox singolo.
     * <br>
     * Questo metodo factory: <ul>
     * <li> non necessita di ulteriori regolazioni obbligatorie </li>
     * <li> crea un CampoVideo di tipo checkbox </li>
     * <li> crea un CampoDati di tipo booleano </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' larga 30 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 30 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default elimina l'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in scheda dipende dal testo </li>
     * <li> di default regola il testo del checkbox col nome del campo </li>
     * </ul>
     * Se si rende il campo visibile in Lista, occorre modificare il voce
     * della colonna (visto che e' larga solo 30) <br>
     * Eventualmente regolare il testo con setTestoControllo (per avere una
     * descrizione piu' accurata) <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CDBooleano
     * @see CDBBase
     * @see CVCheckBox
     * @since 7-5-04
     */
    public static Campo checkBox(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo unCampoVideo;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del campo booleano */
            unCampo = controlloBooleano(nomeCampo);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVCheckBox(unCampo);
            unCampo.setCampoVideo(unCampoVideo);

            /* regolazioni finali per i CheckBox */
            regolaCheckBox(unCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo checkbox singolo.
     * <br>
     * Questo metodo factory: <ul>
     * <li> non necessita di ulteriori regolazioni obbligatorie </li>
     * <li> crea un CampoVideo di tipo checkbox </li>
     * <li> crea un CampoDati di tipo booleano </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' larga 30 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 30 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default elimina l'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in scheda dipende dal testo </li>
     * <li> di default regola il testo del checkbox col nome del campo </li>
     * </ul>
     * Se si rende il campo visibile in Lista, occorre modificare il voce
     * della colonna (visto che e' larga solo 30) <br>
     * Eventualmente regolare il testo con setTestoControllo (per avere una
     * descrizione piu' accurata) <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CDBooleano
     * @see CDBBase
     * @see CVCheckBox
     * @since 7-5-04
     */
    public static Campo checkBox(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.checkBox(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

            /* usa l'etichetta scheda come testo del componente */
            unCampo.setTestoComponente(campo.getEtichettaScheda());

            /* di default elimina l'etichetta */
            unCampo.decora().eliminaEtichetta();

            /* regolazioni finali per i CheckBox */
//            regolaCheckBox(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Ulteriori regolazioni finali per il campo check box.
     * <p/>
     *
     * @param unCampo campo check box da regolare
     */
    private static void regolaCheckBox(Campo unCampo) {

        try { // prova ad eseguire il codice

            /* di default elimina l'etichetta */
            unCampo.decora().eliminaEtichetta();

            /* di default non prende il fuoco */
            unCampo.setFocusable(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un campo di tipo gruppo di check box con valori interni.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> i valori hardcoded della lista da mostrare -
     * usando i metodi setValoriInterni, presenti nell'interfaccia Campo </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo base  </li>
     * <li> crea un CampoDati di tipo gruppo </li>
     * <li> crea un CampoVideo di tipo gruppo check </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa  </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' interna </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @since 9-5-04
     */
    public static Campo checkInterno(String nomeCampo) {
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoVideo unCampoVideo;
        CampoLogica unCampoLogica;

        try { // prova ad eseguire il codice

            /* crea l'instanza del campo booleano */
            unCampo = controlloBooleano(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDGruppo(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVCheckGruppo(unCampo);
            unCampo.setCampoVideo(unCampoVideo);

            /* crea il campo logica e lo sostituisce */
            unCampoLogica = new CLGruppo(unCampo);
            unCampo.setCampoLogica(unCampoLogica);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo gruppo di check box con valori interni.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> i valori hardcoded della lista da mostrare -
     * usando i metodi setValoriInterni, presenti nell'interfaccia Campo </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo base  </li>
     * <li> crea un CampoDati di tipo gruppo </li>
     * <li> crea un CampoVideo di tipo gruppo check </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa  </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' interna </li>
     * </ul>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @since 9-5-04
     */
    public static Campo checkInterno(Campi campo) {
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* crea l'instanza del campo booleano */
            unCampo = checkInterno(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo radio (un singolo radiobottone).
     * <p/>
     * Questo metodo factory: <ul>
     * <li> non necessita di ulteriori regolazioni obbligatorie </li>
     * <li> crea un CampoVideo di tipo radio bottone </li>
     * <li> crea un CampoDati di tipo booleano </li>
     * <li> di default regola il CampoDB con funzione ordinamento nulla </li>
     * <p/>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' larga 30 pixel </li>
     * <li> di default la colonna della Lista e' a larghezza fissa </li>
     * <li> di default la larghezza minima della colonna e' di 30 pixel </li>
     * <p/>
     * <li> di default il campo e' visibile in scheda </li>
     * <li> di default elimina l'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in scheda dipende dal testo </li>
     * <li> di default regola il testo del checkbox col nome del campo </li>
     * </ul>
     * Se si rende il campo visibile in Lista, occorre modificare il voce
     * della colonna (visto che e' larga solo 30) <br>
     * Eventualmente regolare il testo con setTestoControllo (per avere una
     * descrizione piu' accurata) <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CDBooleano
     * @see CDBBase
     * @see CVRadioBox
     * @since 7-5-04
     */
    public static Campo radioBox(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo unCampoVideo;

        try {    // prova ad eseguire il codice
            /* crea l'instanza del campo booleano */
            unCampo = controlloBooleano(nomeCampo);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVRadioBox(unCampo);
//            unCampoVideo.setLarghezzaEsterna(false);
            unCampo.setCampoVideo(unCampoVideo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo combo/radio.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo elenco </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default il campo e' visibile in Scheda </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CLElenco
     * @since 10-6-04
     */
    private static Campo comboRadio(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoLogica unCampoLogica;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del campo */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo logica e lo sostituisce */
            unCampoLogica = new CLElenco(unCampo);
            unCampo.setCampoLogica(unCampoLogica);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Regola il campo per l'acquisizione di valori da un metodo.<br>
     *
     * @param unCampo campo già esistente da regolare
     *
     * @see CampoFactory#comboMetodo
     * @see CampoFactory#radioMetodo
     * @since 16-06-04
     */
    private static void comboRadioMetodo(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CampoDB unCampoDB;
        CampoDati unCampoDati;

        try { // prova ad eseguire il codice

            /* crea il campo DB e lo sostituisce */
            unCampoDB = new CDBComboRadioMetodo(unCampo);
            unCampo.setCampoDB(unCampoDB);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDElencoMetodo(unCampo);
            unCampo.setCampoDati(unCampoDati);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Regola il campo per l'acquisizione di valori da link.
     *
     * @param unCampo campo già esistente da regolare
     *
     * @see CampoFactory#comboMetodo
     * @see CampoFactory#radioMetodo
     * @since 16-06-04
     */
    private static void comboRadioLink(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CampoDB unCampoDB;
        CampoDati unCampoDati;

        try { // prova ad eseguire il codice

            /* crea il campo DB e lo sostituisce */
            unCampoDB = new CDBComboRadioLink(unCampo);
            unCampo.setCampoDB(unCampoDB);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDElencoLink(unCampo);
            unCampo.setCampoDati(unCampoDati);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Regola il campo per l'acquisizione di valori interni.<br>
     *
     * @param unCampo campo già esistente da regolare
     *
     * @see CampoFactory#comboMetodo
     * @see CampoFactory#radioMetodo
     * @since 16-06-04
     */
    private static void comboRadioInterno(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CampoDati unCampoDati;

        try { // prova ad eseguire il codice

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDElencoInterno(unCampo);
            unCampo.setCampoDati(unCampoDati);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Crea un campo di tipo lista.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo lista </li>
     * <li> di default crea un CampoLogica di tipo elenco </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 80 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CLElenco
     * @see CampoScheda
     * @see CVComboLista
     * @since 9-5-04
     */
    private static Campo lista(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo unCampoVideo;

        try {    // prova ad eseguire il codice
            /* crea l'instanza del campo */
            unCampo = comboRadio(nomeCampo);

            /* regola alcuni parametri del CampoScheda di default */
            unCampo.setLarScheda(150);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVLista(unCampo);

            /* altezza in righe di default per il campo */
            unCampoVideo.setNumeroRighe(5);

            unCampo.setCampoVideo(unCampoVideo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo radio.
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo radio </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default la colonna della Lista e' larga 80 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CampoBase
     * @see CLElenco
     * @see CampoScheda
     * @see CVComboLista
     * @see CampoFactory#comboInterno
     * @see CampoFactory#comboLinkPop
     * @since 9-5-04
     */
    private static Campo radioBase(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo unCampoVideo;

        try {    // prova ad eseguire il codice
            /* crea l'instanza del campo */
            unCampo = comboRadio(nomeCampo);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVRadioGruppo(unCampo);
            unCampo.setCampoVideo(unCampoVideo);

            /*
             * pone la larghezza di default a zero
             * di modo che venga determinata in base ai componenti stessi
             */
            unCampo.getCampoScheda().setLarghezzaComponenti(0);
            
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Regolazioni specifiche di un campo combo.
     *
     * @param unCampo campo già esistente da regolare
     */
    private static void regolaCombo(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CampoDati unCampoDati;

        try {    // prova ad eseguire il codice
            /* recupera il campo dati */
            unCampoDati = unCampo.getCampoDati();

            /* regolazioni specifiche */
            unCampo.setUsaNonSpecificato(true);
            unCampoDati.setNonSpecificatoIniziale(true);
//            unCampoDati.setUsaNuovo(false);
            unCampoDati.setNuovoIniziale(false);
            unCampoDati.setUsaSeparatore(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Regolazioni specifiche di un campo radio.
     *
     * @param unCampo campo già esistente da regolare
     */
    private static void regolaRadio(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CampoDati unCampoDati;

        try {    // prova ad eseguire il codice
            /* recupera il campo dati */
            unCampoDati = unCampo.getCampoDati();

            /* regolazioni specifiche */
            unCampo.setUsaNonSpecificato(true);
            unCampoDati.setNonSpecificatoIniziale(false);
//            unCampoDati.setUsaNuovo(false);
            unCampoDati.setNuovoIniziale(false);
            unCampoDati.setUsaSeparatore(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea un campo di tipo combobox con valori interni.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> i valori hardcoded della lista da mostrare -
     * usando i metodi setValoriInterni, presenti nell'interfaccia Campo </li>
     * </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo elenco  </li>
     * <li> crea un CampoDati di tipo elenco interno </li>
     * <li> crea un CampoVideo di tipo combo </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa  </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CDElencoInterno
     * @see CVComboLista
     * @see CampoBase#setTestoEtichetta
     * @see CampoFactory#comboRadio
     * @since 9-5-04
     */
    public static Campo comboInterno(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoVideo cv;

        try { // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = comboRadio(nomeCampo);

            /* crea il campo video e lo sostituisce */
            cv = new CVComboLista(unCampo);
            unCampo.setCampoVideo(cv);

            /* invoca il metodo delegato di questa classe */
            comboRadioInterno(unCampo);

            /* invoca il metodo delegato di questa classe */
            regolaCombo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo combobox con valori interni.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> i valori hardcoded della lista da mostrare -
     * usando i metodi setValoriInterni, presenti nell'interfaccia Campo </li>
     * </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo elenco  </li>
     * <li> crea un CampoDati di tipo elenco interno </li>
     * <li> crea un CampoVideo di tipo combo </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa  </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CDElencoInterno
     * @see CVComboLista
     * @see CampoBase#setTestoEtichetta
     * @see CampoFactory#comboRadio
     * @since 9-5-04
     */
    public static Campo comboInterno(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.comboInterno(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo lista con valori interni.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> i valori hardcoded della lista da mostrare -
     * usando i metodi setValoriInterni, presenti nell'interfaccia Campo </li>
     * </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo elenco  </li>
     * <li> crea un CampoDati di tipo elenco interno </li>
     * <li> crea un CampoVideo di tipo combo </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa  </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CDElencoInterno
     * @see CVComboLista
     * @see CampoBase#setTestoEtichetta
     * @see CampoFactory#comboRadio
     * @since 9-5-04
     */
    public static Campo listaInterna(String nomeCampo) {
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = lista(nomeCampo);

            /* invoca il metodo delegato di questa classe */
            comboRadioInterno(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo lista con valori esterni.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> il nome del Modulo verso cui linkarsi -
     * usando il metodo setNomeModuloLinkato, presente nell'interfaccia Campo </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> presuppone che nel Progetto esista il Modulo linkato </li>
     * <li> crea un CampoVideo di tipo combo </li>
     * <li> crea un CampoDB di tipo comboRadioLink </li>
     * <li> crea un CampoDati di tipo elencoLink - se si
     * vogliono usare dei valori hardcoded, usare il metodo comboInterno</li>
     * <li> crea un CampoLogica di tipo elenco </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default usa come colonna linkata il campo sigla del modulo
     * linkato - se si vuole usare una diversa colonna linkata, inserirne il
     * nome col metodo setNomeColonnaListaLinkata - se si vuole usare una Vista
     * linkata, inserirne il nome col metodo setNomeVistaLinkata </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default usa come Campo linkato da mostrare nella Scheda,
     * il campo sigla del modulo linkato - se si vuole usare un campo
     * diverso, inserirne il nome col metodo setNomeCampoSchedaLinkato </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     * Normalmente si usa setTestoEtichettaScheda perch&egrave il testo del
     * campo solitamente &egrave <i>link...</i> </li>
     * <br>
     * Nella lista esiste comunque la colonna originaria di questa tavola,
     * di tipo numero, che solitamente (di default) non viene mostrata <br>
     * <br>
     * Se si vogliono dei valori aggiuntivi del record (estratti), chiamare il
     * metodo accessorio <br>
     * Se il valore del campo nomeEstratto e' significativo, aggiungera' il
     * decoratore Estratto nella fase di inizializzazione del campo <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CDBLinkato
     * @see CampoBase#setTestoEtichetta
     * @see CDBComboRadioLink#setNomeColonnaListaLinkata
     * @see CDBLinkato#setNomeVistaLinkata
     * @see CampoFactory#comboInterno
     * @since 9-5-04
     */
    public static Campo listaLink(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = lista(nomeCampo);

            /* invoca il metodo delegato di questa classe */
            comboRadioLink(unCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo combobox che acquisisce i valori da un metodo.<br>
     * <br>
     * Per questo tipo di campo e' obbligatorio implementare in un Modulo<br>
     * un metodo che fornisca i valori per il campo.<br>
     * Tale metodo deve obbligatoriamente ritornare un array Object[].<br>
     * <br>
     * <li> di default il modulo che implementa il metodo fornitore<br>
     * dei valori e' il modulo stesso del campo, altrimenti va regolato<br>
     * tramite una chiamata al metodo setNomeModuloValori del campoDB</li>
     * <li> di default il nome del metodo che fornisce i valori<br>
     * e' uguale al nome del campo, altrimenti va regolato tramite una<br>
     * chiamata al metodo setNomeMetodoValori del campoDB</li>
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo CLElenco  </li>
     * <li> crea un CampoDB di tipo CDBComboRadioMetodo </li>
     * <li> crea un CampoDati di tipo CDElencoMetodo </li>
     * <li> crea un CampoVideo di tipo CVCombo </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato
     *
     * @see CampoFactory#comboRadioMetodo
     * @see CVComboLista
     * @since 16-06-2004
     */
    public static Campo comboMetodo(String nomeCampo) {
        Campo unCampo = null;
        CampoVideo cv;

        try { // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = comboRadio(nomeCampo);

            /* crea il campo video e lo sostituisce */
            cv = new CVComboLista(unCampo);
            unCampo.setCampoVideo(cv);

            /* invoca il metodo delegato di questa classe */
            comboRadioMetodo(unCampo);

            /* invoca il metodo delegato di questa classe */
            regolaCombo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo radio con valori interni.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> i valori hardcoded della lista da mostrare -
     * usando i metodi setValoriInterni, presenti nell'interfaccia Campo </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo elenco  </li>
     * <li> crea un CampoDati di tipo elenco interno </li>
     * <li> crea un CampoVideo di tipo radio </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa  </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' interna </li>
     * <br>
     * <li> obbligatoriamente l'elenco usa la riga <i>non specificato</i> </li>
     * <li> obbligatoriamente l'elenco posiziona alla fine la riga
     * <i>non specificato</i> </li>
     * <li> obbligatoriamente l'elenco non usa il separatore </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CVComboLista
     * @see CampoFactory#comboRadio
     * @since 9-5-04
     */
    public static Campo radioInterno(String nomeCampo) {
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = radioBase(nomeCampo);

            /* invoca il metodo delegato di questa classe */
            comboRadioInterno(unCampo);

            /* invoca il metodo delegato di questa classe */
            regolaRadio(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    public static Campo radioInterno(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.radioInterno(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo radio con valori esterni.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> il nome del Modulo verso cui linkarsi -
     * usando il metodo setNomeModuloLinkato, presente nell'interfaccia Campo </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> presuppone che nel Progetto esista il Modulo linkato </li>
     * <li> crea un CampoVideo di tipo radio </li>
     * <li> crea un CampoDB di tipo comboRadioLink </li>
     * <li> crea un CampoDati di tipo elencoLink - se si
     * vogliono usare dei valori hardcoded, usare il metodo radioInterno</li>
     * <li> crea un CampoLogica di tipo elenco </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default usa come colonna linkata il campo sigla del modulo
     * linkato - se si vuole usare una diversa colonna linkata, inserirne il
     * nome col metodo setNomeColonnaListaLinkata - se si vuole usare una Vista
     * linkata, inserirne il nome col metodo setNomeVistaLinkata </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default usa come Campo linkato da mostrare nella Scheda,
     * il campo sigla del modulo linkato - se si vuole usare un campo
     * diverso, inserirne il nome col metodo setNomeCampoSchedaLinkato </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     * Normalmente si usa setTestoEtichettaScheda perch&egrave il testo del
     * campo solitamente &egrave <i>link...</i> </li>
     * <br>
     * Nella lista esiste comunque la colonna originaria di questa tavola,
     * di tipo numero, che solitamente (di default) non viene mostrata <br>
     * <br>
     * Se si vogliono dei valori aggiuntivi del record (estratti), chiamare il
     * metodo accessorio <br>
     * Se il valore del campo nomeEstratto e' significativo, aggiungera' il
     * decoratore Estratto nella fase di inizializzazione del campo <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CDBLinkato
     * @see CampoBase#setTestoEtichetta
     * @see CDBComboRadioLink#setNomeColonnaListaLinkata
     * @see CDBLinkato#setNomeVistaLinkata
     * @see CampoFactory#comboInterno
     * @since 9-5-04
     */
    public static Campo radioLink(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = radioBase(nomeCampo);

            /* invoca il metodo delegato di questa classe */
            comboRadioLink(unCampo);

            /* invoca il metodo delegato di questa classe */
            regolaRadio(unCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo radio bottoni che acquisisce i valori da un metodo.<br>
     * <br>
     * Per questo tipo di campo e' obbligatorio implementare in un Modulo<br>
     * un metodo che fornisca i valori per il campo.<br>
     * Tale metodo deve obbligatoriamente ritornare un array Object[].<br>
     * <br>
     * <li> di default il modulo che implementa il metodo fornitore<br>
     * dei valori e' il modulo stesso del campo, altrimenti va regolato<br>
     * tramite una chiamata al metodo setNomeModuloValori del campoDB</li>
     * <li> di default il nome del metodo che fornisce i valori<br>
     * e' uguale al nome del campo, altrimenti va regolato tramite una<br>
     * chiamata al metodo setNomeMetodoValori del campoDB</li>
     * <br>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo CLElenco  </li>
     * <li> crea un CampoDB di tipo CDBComboRadioMetodo </li>
     * <li> crea un CampoDati di tipo CDElencoMetodo </li>
     * <li> crea un CampoVideo di tipo CVRadio </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza fissa  </li>
     * <li> di default la colonna della Lista e' larga 100 pixel </li>
     * <li> di default la larghezza minima della colonna e' di 70 pixel </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 150 pixel </li>
     * <br>
     * <li> di default l'elenco non usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco non usa la riga <i>separatore</i> </li>
     * </ul>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato
     *
     * @see CampoFactory#comboRadioMetodo
     * @since 16-06-2004
     */
    public static Campo radioMetodo(String nomeCampo) {
        Campo unCampo = null;
        CampoLista unCampoLista;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato di questa classe */
            unCampo = radioBase(nomeCampo);

            /* invoca il metodo delegato di questa classe */
            comboRadioMetodo(unCampo);

            /* invoca il metodo delegato di questa classe */
            regolaRadio(unCampo);

            /* regola alcuni parametri del CampoLista di default */
            unCampoLista = unCampo.getCampoLista();
            unCampoLista.setRidimensionabile(false);
            unCampoLista.setLarghezzaColonna(100);
            unCampoLista.setLarghezzaMinima(70);

//            /* regola alcuni parametri del CampoScheda di default */
//            unCampo.setLarghezzaComponentiScheda(150);

//            unCampo.getCampoDati().setNascondeNonSpecificato(false);   //@todo da mettere true
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo      */

//    /**
//     * Crea un campo di tipo sub-lista che usa un Modulo di Progetto.
//     * <br>
//     * Questo metodo factory richiede obbligatoriamente: <ul>
//     * <li> il nome del Campo del modulo righe che e' linkato al modulo testa -
//     * usando il metodo setNomeCampoLinkRighe </li>
//     * </ul>
//     * Questo metodo factory: <ul>
//     * <li> crea un CampoVideo di tipo sub </li>
//     * <li> crea un CampoDB di tipo sub </li>
//     * <li> crea un CampoDati di tipo sub </li>
//     * <br>
//     * <li> il campo non e' visibile in Lista </li>
//     * <br>
//     * <li> il campo e' visibile in Scheda </li>
//     * <li> di default crea un'etichetta col nome del campo </li>
//     * <li> di default la larghezza del campo in Scheda e' di 500 pixel </li>
//     * <br>
//     * <li> di default la sub-lista ha un'altezza di 9 righe </li>
//     * <li> di default la sub-lista � ordinabile </li>
//     * <li> di default la sub-lista non usa i bottono di spostamento </li>
//     * </ul>
//     * Normalmente si usa setTestoEtichettaScheda
//     * <br>
//     *
//     * @param nomeCampo nome interno per recuperare il campo dalla collezione
//     *
//     * @return il campo creato (non ancora inizializzato)
//     *
//     * @see CampoBase
//     * @see CDBSub
//     * @see CDSub
//     * @see CVSub
//     * @since 10-6-04
//     * @deprecated
//     */
//    private static Campo subLista(String nomeCampo) {
//        /* variabili e costanti locali di lavoro */
//        Campo unCampo = null;
//        CampoDB unCampoDB = null;
//        CampoDati unCampoDati = null;
//        CampoScheda unCampoScheda = null;
//        CampoVideo unCampoVideo = null;
//        CVSub unCampoVideoSub = null;
//
//        try {	// prova ad eseguire il codice
//            /* crea l'istanza del campo */
//            unCampo = new CampoBase(nomeCampo);
//
//            /* crea il campo db e lo sostituisce */
//            unCampoDB = new CDBSub(unCampo);
//            unCampo.setCampoDB(unCampoDB);
//
//            /* crea il campo dati e lo sostituisce */
//            unCampoDati = new CDSub(unCampo);
//            unCampo.setCampoDati(unCampoDati);
//
//            /* regola alcuni parametri del CampoScheda di default */
//            unCampoScheda = unCampo.getCampoScheda();
//            unCampoScheda.setLarghezzaPannelloCampo(LARGHEZZA_SCHEDA_SUB);
//
//            /* crea il campo video e lo sostituisce */
//            unCampoVideo = new CVSub(unCampo);
//            unCampoVideoSub = (CVSub)unCampoVideo;
//
//            // va fatto qui o e' perche' ho disabilitato lancia?
//            unCampoVideoSub.setListaModello(new ListaModelloDefaultOld());
//            unCampo.setCampoVideo(unCampoVideo);
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return unCampo;
//    } /* fine del metodo */

//    /**
//     * Crea un campo di tipo sub-lista che usa un Modulo di Progetto.
//     * <br>
//     * Questo metodo factory richiede obbligatoriamente: <ul>
//     * <li> il nome del Campo del modulo righe che e' linkato al modulo testa -
//     * usando il metodo setNomeCampoLinkRighe </li>
//     * </ul>
//     * Questo metodo factory: <ul>
//     * <li> crea un CampoVideo di tipo sub </li>
//     * <li> crea un CampoDB di tipo sub </li>
//     * <li> crea un CampoDati di tipo sub </li>
//     * <br>
//     * <li> il campo non e' visibile in Lista </li>
//     * <br>
//     * <li> il campo e' visibile in Scheda </li>
//     * <li> di default crea un'etichetta col nome del campo </li>
//     * <li> di default la larghezza del campo in Scheda e' di 500 pixel </li>
//     * <br>
//     * <li> di default la sub-lista ha un'altezza di 9 righe </li>
//     * <li> di default la sub-lista � ordinabile </li>
//     * <li> di default la sub-lista non usa i bottono di spostamento </li>
//     * </ul>
//     * Normalmente si usa setTestoEtichettaScheda
//     * <br>
//     *
//     * @param nomeCampo       nome interno per recuperare il campo dalla collezione
//     * @param nomeModuloRighe nome interno del modulo linkato
//     *
//     * @return il campo creato (non ancora inizializzato)
//     *
//     * @see CampoFactory#subLista
//     * @since 10-6-04
//     * @deprecated
//     */
//    public static Campo subLista(String nomeCampo, String nomeModuloRighe) {
//        /* variabili e costanti locali di lavoro */
//        Campo unCampo = null;
//        Modulo unModuloRighe = null;
//
//        try {	// prova ad eseguire il codice
//            /* invoca il metodo delegato di questa classe */
//            unCampo = subLista(nomeCampo);
//
//            /* recupera il Modulo dal progetto e lo registra nel Campo */
//            if (Progetto.isEsisteModulo(nomeModuloRighe)) {
//                unModuloRighe = Progetto.getModulo(nomeModuloRighe);
//                unCampo.getCampoLogica().setModuloInterno(unModuloRighe);
//            } else {
//                throw new Exception("Non esiste il modulo: " + nomeModuloRighe);
//            }// fine del blocco if-else
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return unCampo;
//    } /* fine del metodo */

//    /**
//     * Crea un campo di tipo sub-lista che crea un nuovo Modulo righe.
//     * <br>
//     * Questo metodo factory richiede obbligatoriamente: <ul>
//     * <li> il nome del Campo del modulo righe che e' linkato al modulo testa -
//     * usando il metodo setNomeCampoLinkRighe </li>
//     * </ul>
//     * Questo metodo factory: <ul>
//     * <li> crea un CampoVideo di tipo sub </li>
//     * <li> crea un CampoDB di tipo sub </li>
//     * <li> crea un CampoDati di tipo sub </li>
//     * <br>
//     * <li> il campo non e' visibile in Lista </li>
//     * <br>
//     * <li> il campo e' visibile in Scheda </li>
//     * <li> di default crea un'etichetta col nome del campo </li>
//     * <li> di default la larghezza del campo in Scheda e' di 500 pixel </li>
//     * <br>
//     * <li> di default la sub-lista ha un'altezza di 9 righe </li>
//     * <li> di default la sub-lista � ordinabile </li>
//     * <li> di default la sub-lista non usa i bottono di spostamento </li>
//     * </ul>
//     * Normalmente si usa setTestoEtichettaScheda
//     * <br>
//     *
//     * @param nomeCampo   nome interno per recuperare il campo dalla collezione
//     * @param moduloPadre modulo  padre nell'albero moduli
//     * @param moduloRighe modulo provvisorio per la creazione del definitivo
//     *
//     * @return il campo creato (non ancora inizializzato)
//     *
//     * @see CampoFactory#subLista
//     * @since 10-6-04
//     * @deprecated
//     */
//    public static Campo subLista(
//            String nomeCampo, Modulo moduloPadre, Modulo moduloRighe) {
//        /* variabili e costanti locali di lavoro */
//        Campo unCampo = null;
//        Modulo unModuloRighe = null;
//        String nomeNuovoModulo = "";
//
//        try {	// prova ad eseguire il codice
//            /* invoca il metodo delegato di questa classe */
//            unCampo = subLista(nomeCampo);
//
//            /* crea un Modulo definitivo da quello provvisorio */
//            nomeNuovoModulo
//                    = Progetto.getIstanza().creaModulo(moduloPadre, moduloRighe);
//
//            /* recupera dal Progetto il nuovo modulo creato al volo */
//            unModuloRighe = Progetto.getModulo(nomeNuovoModulo);
//
//            /* crea la parte statica del Modulo che di solito viene fatta in progetto */
//            unModuloRighe.getModello().prepara(unModuloRighe);
//
//            /* registra il Modulo  nel campo */
//            unCampo.getCampoLogica().setModuloInterno(unModuloRighe);
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return unCampo;
//    } /* fine del metodo */


    public static Campo navMemoria(String nomeCampo, ArrayList<Campo> campi, boolean avvia) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        Navigatore nav;
        ModuloMemoria memoria;
        CampoDati campoDati;

        try { // prova ad eseguire il codice
            memoria = new ModuloMemoria(nomeCampo, campi);


            if (avvia) {
                memoria.avvia(); // todo raddoppiato il metodo, così si può chiamare con o senza avvia/gac
            }// fine del blocco if

            nav = memoria.getNavigatoreDefault();

            unCampo = navigatore(nomeCampo, nav);

            /* crea il campo dati e lo sostituisce */
            campoDati = new CDNavMemoria(unCampo);
            unCampo.setCampoDati(campoDati);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    public static Campo navMemoria(String nomeCampo, ArrayList<Campo> campi) {
        /* invoca il metodo delegato della classe */
        return navMemoria(nomeCampo, campi, true);
    }

//    public static Campo navMemoriaLista(String nomeCampo, ArrayList<Campo> campi) {
//        /* variabili e costanti locali di lavoro */
//        Campo unCampo = null;
//        Navigatore nav;
//        ModuloMemoria memoria;
//
//        try { // prova ad eseguire il codice
//            memoria = new ModuloMemoria(nomeCampo, campi);
//
//            nav = NavigatoreFactory.lista(memoria);
//
//            memoria.addNavigatoreCorrente(nav);
//
//            unCampo = navigatore(nomeCampo, nav);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return unCampo;
//    }


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente:
     * <li>il nome del campo da creare</li>
     * <li>il nome del modulo per il quale creare un Navigatore</li>
     * <li>il nome del Campo del modulo righe che e' linkato al modulo testa</li>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo CLNavigatore </li>
     * <li> crea un CampoDB di tipo CDBNavigatore </li>
     * <li> crea un CampoVideo di tipo CVNavigatore </li>
     * <br>
     * <li> il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <br>
     * <li> di default il Navigatore ha un'altezza di 10 righe </li>
     * <li> di default il Navigatore usa le frecce di spostamento </li>
     * <li> di default il Navigatore non usa i comandi di selezione nella lista</li>
     * </ul>
     * <br>
     *
     * @param nomeCampo  nome interno del campo da costruire
     * @param navigatore un oggetto Navigatore da regolare e utilizzare
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatore(String nomeCampo, Navigatore navigatore) {

        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CLNavigatore campoLogica;
        CampoDB unCampoDB;
        CampoDati campoDati;
        CampoVideo unCampoVideo;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = new CampoBase(nomeCampo);

            /* crea il campo logica e lo sostituisce */
            campoLogica = new CLNavigatore(unCampo);
            campoLogica.setNavigatore(navigatore);
            unCampo.setCampoLogica(campoLogica);

            /* crea il campo db e lo sostituisce */
            unCampoDB = new CDBNavigatore(unCampo);
            unCampo.setCampoDB(unCampoDB);

            /* crea il campo dati e lo sostituisce */
            campoDati = new CDNavigatore(unCampo);
            unCampo.setCampoDati(campoDati);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVNavigatore(unCampo);
            unCampo.setCampoVideo(unCampoVideo);

            /* Esegue le regolazioni di default sul Navigatore.
             * Rappresentano le impostazioni comunemente desiderate
             * per un Navigatore inserito in un campo.*/
            nav = campoLogica.getNavigatore();
            nav.setUsaCarattereFiltro(false);

            /**todo - no! non voglio sovrascrivere le regolazioni che
             * todo - ho già effettuato sul navigatore
             * todo - alex feb-2008 */
//            nav.setIconePiccole();

            nav.setUsaSelezione(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente:
     * <li>il nome del campo da creare</li>
     * <li>il nome del modulo per il quale creare un Navigatore</li>
     * <li>il nome del Campo del modulo righe che e' linkato al modulo testa</li>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo CLNavigatore </li>
     * <li> crea un CampoDB di tipo CDBNavigatore </li>
     * <li> crea un CampoVideo di tipo CVNavigatore </li>
     * <br>
     * <li> il campo e' visibile in Scheda </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <br>
     * <li> di default il Navigatore ha un'altezza di 10 righe </li>
     * <li> di default il Navigatore usa le frecce di spostamento </li>
     * <li> di default il Navigatore non usa i comandi di selezione nella lista</li>
     * </ul>
     * <br>
     *
     * @param campo      della Enumeration dell'interfaccia
     * @param navigatore un oggetto Navigatore da regolare e utilizzare
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatore(Campi campo, Navigatore navigatore) {

        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.navigatore(campo.get(), navigatore);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regolaScheda(campo, unCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     * Usa la relazione da Progetto se esistente
     *
     * @param nomeCampo          nome interno del campo da costruire
     * @param nomeModuloPilotato modulo dipendente collegato (righe)
     * @param nomeNavigatore     specifico nel modulo dipendente
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatore(String nomeCampo,
                                   String nomeModuloPilotato,
                                   String nomeNavigatore) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Navigatore nav;
        Modulo moduloPilotato;

        try { // prova ad eseguire il codice

            /* recupera il modulo pilotato dal Progetto */
            moduloPilotato = Progetto.getModulo(nomeModuloPilotato);

            /* recupera lo specifico navigatore dal modulo pilotato */
            if (Lib.Testo.isValida(nomeNavigatore)) {
                nav = moduloPilotato.getNavigatore(nomeNavigatore);
            } else {
                nav = moduloPilotato.getNavigatoreDefault();
            }// fine del blocco if-else

            campo = navigatore(nomeCampo, nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     * Usa la relazione da Progetto se esistente
     *
     * @param campo              della Enumeration dell'interfaccia
     * @param nomeModuloPilotato modulo dipendente collegato (righe)
     * @param nomeNavigatore     specifico nel modulo dipendente
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatore(Campi campo, String nomeModuloPilotato, String nomeNavigatore) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.navigatore(campo.get(), nomeModuloPilotato, nomeNavigatore);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

            /* forza la visibilità (false) del campo nella lista
             * recupera così un'eventuale errore di impostazione nell'interfaccia specifica
             *
             * @todo ATTENZIONE - c'è un problema col metodo CampoListaBase.setVisibileVistaDefault
             * se ci passo due volte, tiene comunque la prima regolazione
             */
            unCampo.setVisibileVistaDefault(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     * Usa la relazione da Progetto se esistente
     *
     * @param campo              della Enumeration dell'interfaccia
     * @param nomeModuloPilotato modulo dipendente collegato (righe)
     * @param nav                specifico nel modulo dipendente
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatore(Campi campo, String nomeModuloPilotato, Navigatori nav) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (campo != null && Lib.Testo.isValida(nomeModuloPilotato) && nav != null);

            if (continua) {
                unCampo = CampoFactory.navigatore(campo, nomeModuloPilotato, nav.toString());
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     * Usa la relazione da Progetto se esistente
     *
     * @param nomeCampo          nome interno del campo da costruire
     * @param nomeModuloPilotato modulo dipendente collegato (righe)
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatore(String nomeCampo, String nomeModuloPilotato) {
        /* invoca il metodo delegato della classe */
        return CampoFactory.navigatore(nomeCampo, nomeModuloPilotato, "");
    }


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     * Usa la relazione da Progetto se esistente
     *
     * @param campo              della Enumeration dell'interfaccia
     * @param nomeModuloPilotato modulo dipendente collegato (righe)
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatore(Campi campo, String nomeModuloPilotato) {
        /* invoca il metodo delegato della classe */
        return CampoFactory.navigatore(campo.get(), nomeModuloPilotato);
    }


    /**
     * Crea un campo di tipo Navigatore.
     * <p/>
     *
     * @param nomeCampo          nome interno del campo da costruire
     * @param nomeModuloPilotato modulo dipendente collegato (righe)
     * @param chiaveNavPilotato  nome interno del Navigatore (nel modulo pilotato)
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo navigatoreComposto(String nomeCampo,
                                           String nomeModuloPilotato,
                                           String chiaveNavPilotato) {

        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Navigatore nav;
        Modulo moduloPilotato;

        try { // prova ad eseguire il codice

            /* recupera il modulo pilotato dal Progetto */
            moduloPilotato = Progetto.getModulo(nomeModuloPilotato);

            /* recupera lo specifico navigatore dal modulo pilotato */
            nav = moduloPilotato.getNavigatore(chiaveNavPilotato);

            /* crea un campo col navigatore */
            campo = navigatore(nomeCampo, nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }

//    /**
//     * Crea un campo di tipo label che copia i valori da un altro campo.
//     * <p/>
//     * Questo campo &egrave; una copia statica e non modificabile di un'altro campo <br>
//     * Serve per mostrare le stesse informazioni del campo principale <br>
//     * <p/>
//     * Questo metodo factory: <ul>
//     * <li> non necessita di ulteriori regolazioni obbligatorie </li>
//     * <li> crea un CampoVideo di tipo testo </li>
//     * <li> crea un CampoDati di tipo testo </li>
//     * <li> crea un CampoLogica di tipo label </li>
//     * <br>
//     * <li> il campo non &egrave; <strong>mai</strong> visibile in Lista </li>
//     * <br>
//     * <li> il campo &egrave; <strong>sempre</strong> visibile in Scheda </li>
//     * <li> il campo non &egrave; <strong>mai</strong> modificabile nella Scheda </li>
//     * <li> di default crea un'etichetta col nome del campo principale </li>
//     * <li> di default la larghezza del campo in Scheda e' uguale a quella del campo
//     * principale </li>
//     * <br>
//     * </ul>
//     *
//     * @param nomeCampo           nome interno per recuperare il campo dalla collezione
//     * @param nomeCampoPrincipale nome del campo da cui recuperare il valore
//     *
//     * @return il campo creato
//     */
//    public static Campo copia(String nomeCampo, String nomeCampoPrincipale) {
//        Campo unCampo = null;
//        CampoLogica unCampoLogica = null;
//        CampoDB unCampoDB = null;
//
//        try { // prova ad eseguire il codice
//
//            /* invoca il metodo delegato di questa classe */
//            unCampo = testo(nomeCampo);
//
//            /* crea il campo logica e lo sostituisce */
//            unCampoLogica = new CLCopia(unCampo);
//            unCampoLogica.setNomeCampoOriginale(nomeCampoPrincipale);
//            unCampo.setCampoLogica(unCampoLogica);
//
//            /* regola alcuni parametri del CampoDB di default */
//            unCampoDB = unCampo.getCampoDB();
//            unCampoDB.setCampoFisico(false);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return unCampo;
//    }

//    /**
//     * Crea un campo di tipo estratto (derivato da sub-lista).
//     * crea un CampoDB di tipo sub
//     * crea un CampoVideo di tipo sub
//     *
//     * @param unNomeInternoCampo   nome interno per rintracciare il campo nella collezione
//     * @param unModuloRighe        un modulo per la gestione delle righe
//     * @param unNomeCampoLinkRighe il nome del campo del modulo righe che e' linkato al modulo testa
//     *
//     * @return unCampo il campo creato
//     */
//    public static Campo estratto(
//            String unNomeInternoCampo,
//            Modulo unModuloRighe,
//            String unNomeCampoLinkRighe) {
//        /** variabili e costanti locali di lavoro */
//        Campo unCampo = null;
//        CampoDB unCampoDB = null;
//        CampoDati unCampoDati = null;
//        CampoScheda unCampoScheda = null;
//        CampoVideo unCampoVideo = null;
//        CVEstratto unCampoVideoEstratto = null;
//
//        try {	// prova ad eseguire il codice
//            /** crea l'istanza del campo */
//            unCampo = new CampoBase(unNomeInternoCampo);
//
//            /** crea il campo db e lo sostituisce */
//            unCampoDB = new CDBSub(unCampo);
//            unCampo.setCampoDB(unCampoDB);
//
//            /** crea il campo dati e lo sostituisce */
//            unCampoDati = new CDSub(unCampo);
//            unCampo.setCampoDati(unCampoDati);
//
//            /** regola alcuni parametri del CampoScheda di default */
//            unCampoScheda = unCampo.getCampoScheda();
//            unCampoScheda.setLarghezzaPannelloCampo(LARGHEZZA_SCHEDA_SUB);
//
//            /** crea il campo video e lo sostituisce */
//            unCampoVideo = new CVEstratto(unCampo);
//            unCampoVideoEstratto = (CVEstratto)unCampoVideo;
//
//            // va fatto qui o e' perche' ho disabilitato lancia?
//            unCampoVideoEstratto.setListaModello(new ListaModelloDefaultOld());
//            unCampo.setCampoVideo(unCampoVideo);
//
//            /** assegna al campo il riferimento al modulo */
//            unCampo.getCampoLogica().setModuloInterno(unModuloRighe);
//
//            /** assegna al gestore il riferimento a questo campo Sub */
//            unModuloRighe.getGestoreOld().setCampoSub(unCampo);
//
//            /** assegna al campo sub il nome del campo linkato del modulo righe */
//            unCampoDB.setNomeCampoLinkRighe(unNomeCampoLinkRighe);
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /** valore di ritorno */
//        return unCampo;
//    } /* fine del metodo */


    /**
     * Crea un campo di tipo scheda.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente:
     * <li>il nome del campo da creare</li>
     * <li>il nome del modulo di cui usare la scheda</li>
     * Questo metodo factory: <ul>
     * <li> crea un CampoLogica di tipo CLNavigatore </li>
     * <li> crea un CampoDB di tipo CDBNavigatore </li>
     * <li> crea un CampoVideo di tipo CVNavigatore </li>
     * <br>
     * <li> il campo e' visibile in Scheda </li>
     * <li> di default usa la scheda standard </li>
     * </ul>
     * <br>
     *
     * @param nomeCampo nome interno del campo da costruire
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo scheda(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoLogica campoLogica;
        CampoDB unCampoDB;
        CampoDati campoDati;
        CampoVideo unCampoVideo;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = new CampoBase(nomeCampo);

//            /* crea il campo logica e lo sostituisce */
//            campoLogica = new CLNavigatore(unCampo);
//            unCampo.setCampoLogica(campoLogica);

            /* crea il campo db e lo sostituisce */
            unCampoDB = new CDBLinkato(unCampo);
            unCampo.setCampoDB(unCampoDB);
            unCampoDB.setCampoFisico(false);

//            /* crea il campo dati e lo sostituisce */
//            campoDati = new CDNavigatore(unCampo);
//            unCampo.setCampoDati(campoDati);

            /* crea il campo video e lo sostituisce */
            unCampoVideo = new CVScheda(unCampo);
            unCampo.setCampoVideo(unCampoVideo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo scheda.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente:
     * <li>il nome del campo da creare</li>
     * <li>il nome del modulo di cui usare la scheda</li>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo CVScheda </li>
     * <br>
     * <li> il campo e' visibile in Scheda </li>
     * <li> di default usa la scheda standard </li>
     * </ul>
     * <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo scheda(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.scheda(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regolaScheda(campo, unCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo link.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDB di tipo ??? </li>
     * <li> crea un CampoDati di tipo ???? </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default usa come colonna linkata il campo sigla del modulo
     * linkato - se si vuole usare una diversa colonna linkata, inserirne il
     * nome col metodo setNomeColonnaListaLinkata - se si vuole usare una Vista
     * linkata, inserirne il nome col metodo setNomeVistaLinkata </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default usa come Campo linkato da mostrare nella Scheda,
     * il campo sigla del modulo linkato - se si vuole usare un campo
     * diverso, inserirne il nome col metodo setNomeCampoSchedaLinkato </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     * Normalmente si usa setTestoEtichettaScheda perch&egrave il testo del
     * campo solitamente &egrave <i>link...</i> </li>
     * <br>
     * Nella lista esiste comunque la colonna originaria di questa tavola,
     * di tipo numero, che solitamente (di default) non viene mostrata <br>
     * <br>
     * Se si vogliono dei valori aggiuntivi del record (estratti), chiamare il
     * metodo accessorio <br>
     * Se il valore del campo nomeEstratto e' significativo, aggiungera' il
     * decoratore Estratto nella fase di inizializzazione del campo <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @since 9-5-04
     */
    public static Campo link(String nomeCampo) {
        /** variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDati unCampoDati;
        CampoDB unCampoDB;
        CampoLista unCampoLista;

        try {    // prova ad eseguire il codice
            /* crea l'instanza del campo */
            unCampo = creaNumero(nomeCampo);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDLink(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* crea il campo db e lo sostituisce */
            unCampoDB = new CDBLinkato(unCampo);
            unCampoDB.setAzioneDelete(Db.Azione.cascade);
            unCampo.setCampoDB(unCampoDB);

            /* regola alcuni parametri del CampoLista di default */
            unCampoLista = unCampo.getCampoLista();
            unCampoLista.setLarghezzaColonna(50);
            unCampoLista.setRidimensionabile(false);

            /* regola alcuni parametri del CampoScheda di default */
            unCampo.setLarScheda(50);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo link.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> crea un CampoVideo di tipo testo </li>
     * <li> crea un CampoDB di tipo ??? </li>
     * <li> crea un CampoDati di tipo ???? </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default usa come colonna linkata il campo sigla del modulo
     * linkato - se si vuole usare una diversa colonna linkata, inserirne il
     * nome col metodo setNomeColonnaListaLinkata - se si vuole usare una Vista
     * linkata, inserirne il nome col metodo setNomeVistaLinkata </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default usa come Campo linkato da mostrare nella Scheda,
     * il campo sigla del modulo linkato - se si vuole usare un campo
     * diverso, inserirne il nome col metodo setNomeCampoSchedaLinkato </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     * Normalmente si usa setTestoEtichettaScheda perch&egrave il testo del
     * campo solitamente &egrave <i>link...</i> </li>
     * <br>
     * Nella lista esiste comunque la colonna originaria di questa tavola,
     * di tipo numero, che solitamente (di default) non viene mostrata <br>
     * <br>
     * Se si vogliono dei valori aggiuntivi del record (estratti), chiamare il
     * metodo accessorio <br>
     * Se il valore del campo nomeEstratto e' significativo, aggiungera' il
     * decoratore Estratto nella fase di inizializzazione del campo <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @since 9-5-04
     */
    public static Campo link(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.link(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo combobox con valori esterni.
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> il nome del Modulo verso cui linkarsi -
     * usando il metodo setNomeModuloLinkato, presente nell'interfaccia Campo </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> presuppone che nel Progetto esista il Modulo linkato </li>
     * <li> crea un CampoVideo di tipo combo </li>
     * <li> crea un CampoDB di tipo comboRadioLink </li>
     * <li> crea un CampoDati di tipo elencoLink - se si
     * vogliono usare dei valori hardcoded, usare il metodo comboInterno</li>
     * <li> crea un CampoLogica di tipo elenco </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default usa come colonna linkata il campo sigla del modulo
     * linkato - se si vuole usare una diversa colonna linkata, inserirne il
     * nome col metodo setNomeColonnaListaLinkata - se si vuole usare una Vista
     * linkata, inserirne il nome col metodo setNomeVistaLinkata </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default usa come Campo linkato da mostrare nella Scheda,
     * il campo sigla del modulo linkato - se si vuole usare un campo
     * diverso, inserirne il nome col metodo setNomeCampoSchedaLinkato </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     * Normalmente si usa setTestoEtichettaScheda perch&egrave il testo del
     * campo solitamente &egrave <i>link...</i> </li>
     * <br>
     * Nella lista esiste comunque la colonna originaria di questa tavola,
     * di tipo numero, che solitamente (di default) non viene mostrata <br>
     * <br>
     * Se si vogliono dei valori aggiuntivi del record (estratti), chiamare il
     * metodo accessorio <br>
     * Se il valore del campo nomeEstratto e' significativo, aggiungera' il
     * decoratore Estratto nella fase di inizializzazione del campo <br>
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CDBLinkato
     * @see CampoBase#setTestoEtichetta
     * @see CDBComboRadioLink#setNomeColonnaListaLinkata
     * @see CDBLinkato#setNomeVistaLinkata
     * @see CampoFactory#comboInterno
     * @since 9-5-04
     */
    public static Campo comboLinkPop(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.comboLinkPop(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    public static Campo comboLinkSel(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.comboLinkSel(campo.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo combobox con valori esterni.
     * Presentati con popup o lista a seconda del numero dei record <br>
     * <br>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> il nome del Modulo verso cui linkarsi -
     * usando il metodo setNomeModuloLinkato, presente nell'interfaccia Campo </li>
     * </ul>
     * Questo metodo factory: <ul>
     * <li> presuppone che nel Progetto esista il Modulo linkato </li>
     * <li> crea un CampoVideo di tipo combo </li>
     * <li> crea un CampoDB di tipo comboRadioLink </li>
     * <li> crea un CampoDati di tipo elencoLink - se si
     * vogliono usare dei valori hardcoded, usare il metodo comboInterno</li>
     * <li> crea un CampoLogica di tipo elenco </li>
     * <br>
     * <li> di default il campo non e' visibile in Lista </li>
     * <li> di default la colonna della Lista e' a larghezza variabile  </li>
     * <li> di default usa come colonna linkata il campo sigla del modulo
     * linkato - se si vuole usare una diversa colonna linkata, inserirne il
     * nome col metodo setNomeColonnaListaLinkata - se si vuole usare una Vista
     * linkata, inserirne il nome col metodo setNomeVistaLinkata </li>
     * <br>
     * <li> di default il campo e' visibile in Scheda </li>
     * <li> di default usa come Campo linkato da mostrare nella Scheda,
     * il campo sigla del modulo linkato - se si vuole usare un campo
     * diverso, inserirne il nome col metodo setNomeCampoSchedaLinkato </li>
     * <li> di default crea un'etichetta col nome del campo </li>
     * <li> di default la larghezza del campo in Scheda e' di 200 pixel </li>
     * <br>
     * <li> di default l'elenco usa la riga <i>non specificato</i> </li>
     * <li> di default l'elenco posiziona all'inizio la riga
     * <i>non specificato</i> </li>
     * <li> di default l'elenco non usa la riga <i>nuovo</i> </li>
     * <li> di default l'elenco posiziona alla fine la riga
     * <i>nuovo</i> </li>
     * <li> di default l'elenco usa la riga <i>separatore</i> </li>
     * </ul>
     * Normalmente si usa setTestoEtichettaScheda perch&egrave il testo del
     * campo solitamente &egrave <i>link...</i> </li>
     * <br>
     * Nella lista esiste comunque la colonna originaria di questa tavola,
     * di tipo numero, che solitamente (di default) non viene mostrata <br>
     * <br>
     * Se si vogliono dei valori aggiuntivi del record (estratti), chiamare il
     * metodo accessorio <br>
     * Se il valore del campo nomeEstratto e' significativo, aggiungera' il
     * decoratore Estratto nella fase di inizializzazione del campo <br>
     *
     * @param nomeCampo nome interno per recuperare il campo dalla collezione
     *
     * @return il campo creato (non ancora inizializzato)
     *
     * @see CDBLinkato
     * @see CampoBase#setTestoEtichetta
     * @see CDBComboRadioLink#setNomeColonnaListaLinkata
     * @see CDBLinkato#setNomeVistaLinkata
     * @see CampoFactory#comboInterno
     * @since 9-5-04
     */
    public static Campo comboLinkPop(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDB unCampoDB;
        CampoDati unCampoDati;
        CampoVideo cv;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = comboRadio(nomeCampo);

            /* crea il campo Video e lo sostituisce */
            cv = new CVComboLista(unCampo);
            unCampo.setCampoVideo(cv);

            /* crea il campo DB e lo sostituisce */
            unCampoDB = new CDBComboRadioLink(unCampo);
            unCampoDB.setAzioneDelete(Db.Azione.setNull);
            unCampo.setCampoDB(unCampoDB);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDElencoLink(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* invoca il metodo delegato di questa classe */
            regolaCombo(unCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di rit orno */
        return unCampo;
    } /* fine del metodo */


    public static Campo comboLinkSel(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoDB unCampoDB;
        CampoDati unCampoDati;
        CampoVideo cv;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del campo */
            unCampo = comboRadio(nomeCampo);

            /* crea il campo Video e lo sostituisce */
            cv = new CVComboTavola(unCampo);
            unCampo.setCampoVideo(cv);

            /* crea il campo DB e lo sostituisce */
            unCampoDB = new CDBComboRadioLink(unCampo);
            unCampoDB.setAzioneDelete(Db.Azione.setNull);
            unCampo.setCampoDB(unCampoDB);

            /* crea il campo dati e lo sostituisce */
            unCampoDati = new CDElencoSet(unCampo);
            unCampo.setCampoDati(unCampoDati);

            /* invoca il metodo delegato di questa classe */
            regolaCombo(unCampo);
            
            /*
             * pone la larghezza di default a zero
             * di modo che venga determinata automaticamente
             */
            unCampo.getCampoScheda().setLarghezzaComponenti(0);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Crea un campo di tipo suggerito, con risultato testo.
     *
     * @param nomeCampo      nome interno del campo da costruire
     * @param campoOsservato della stessa scheda/modulo e che genera l'evento
     * @param campoSuggerito del modulo linkato
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo suggerito(String nomeCampo, String campoOsservato, String campoSuggerito) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CLSuggerito campoLog;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del campo */
            unCampo = testo(nomeCampo);

            /* crea il campo logica e lo sostituisce */
            campoLog = new CLSuggerito(unCampo);
            unCampo.setCampoLogica(campoLog);

            /* regola le variabili di logica */
            campoLog.setCampoOsservato(campoOsservato);
            campoLog.setCampoSuggerito(campoSuggerito);

            /* di default non si vede in lista */
            unCampo.getCampoLista().setPresenteVistaDefault(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo suggerito, con risultato testo.
     *
     * @param campo          della Enumeration dell'interfaccia
     * @param campoOsservato della stessa scheda/modulo e che genera l'evento
     * @param campoSuggerito del modulo linkato
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo suggerito(Campi campo, String campoOsservato, String campoSuggerito) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = suggerito(campo.get(), campoOsservato, campoSuggerito);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Restituisce un campo base, col campo dati specifico.
     * <p/>
     *
     * @param nomeCampo nome interno del campo da costruire
     * @param tipo      di campo dati richiesto
     *
     * @return il campo creato (non ancora inizializzato)
     */
    private static Campo regolaCampoDati(String nomeCampo, CampoLogica.Calcolo.Tipo tipo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        boolean continua;

        try {    // prova ad eseguire il codice
            continua = (Lib.Testo.isValida(nomeCampo));

            /* crea l'instanza del campo */
            if (continua) {
                if (tipo != null) {
                    switch (tipo) {
                        case intero:
                            unCampo = intero(nomeCampo);
                            break;
                        case reale:
                            unCampo = reale(nomeCampo);
                            break;
                        case valuta:
                            unCampo = valuta(nomeCampo);
                            break;
                        case testo:
                            unCampo = testo(nomeCampo);
                            break;
                        default: // caso non definito
                            break;
                    } // fine del blocco switch
                } else {
                    unCampo = testo(nomeCampo);
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Regola un campo di tipo calcolato.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> la logica da applicare di tipo CampoLogica.Calcolo </li>
     * <li> una lista di campi da calcolare </li>
     * </ul>
     * <br>
     * Questo metodo factory: <ul>
     * <li> di default il campo non è visibile in Lista </li>
     * <li> di default il campo  è visibile in Scheda </li>
     * <li> di default non è un campo fisico </li>
     * <li> di default non è modificabile nella scheda </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param unCampo    calcolato da regolare
     * @param operazione da applicare ai campi
     * @param lista      dei campi utilizzati per l'operazione
     */
    private static void regolaCalcolato(Campo unCampo,
                                        CampoLogica.Calcolo operazione,
                                        ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        CLCalcolato campoLog;

        try {    // prova ad eseguire il codice

            /* crea il campo logica e lo sostituisce */
            campoLog = new CLCalcolato(unCampo);
            unCampo.setCampoLogica(campoLog);

            /* di default non va sul database */
            unCampo.getCampoDB().setCampoFisico(false);

            /* di default non si vede in lista */
            unCampo.getCampoLista().setPresenteVistaDefault(false);

            /* di default non è abilitato */
            unCampo.setAbilitato(false);

            /* regola le variabili di logica */
            campoLog.setOperazione(operazione);
            campoLog.setCampiOsservati(lista);

            /* regolazioni di default */
            unCampo.setVisibileVistaDefault(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Crea un campo di tipo calcolato.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> la logica da applicare di tipo CampoLogica.Calcolo </li>
     * <li> una lista di campi da calcolare </li>
     * </ul>
     * <br>
     * Questo metodo factory: <ul>
     * <li> di default il campo non è visibile in Lista </li>
     * <li> di default il campo  è visibile in Scheda </li>
     * <li> di default non è un campo fisico </li>
     * <li> di default non è modificabile nella scheda </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo  nome interno del campo da costruire
     * @param operazione da applicare ai campi
     * @param lista      dei campi utilizzati per l'operazione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo calcola(String nomeCampo,
                                CampoLogica.Calcolo operazione,
                                ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del campo, col campo dati specificato */
            unCampo = regolaCampoDati(nomeCampo, operazione.getTipo());

            /* regolazioni dei campi calcolati */
            CampoFactory.regolaCalcolato(unCampo, operazione, lista);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo calcolato.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> la logica da applicare di tipo CampoLogica.Calcolo </li>
     * <li> una lista di campi da calcolare </li>
     * </ul>
     * <br>
     * Questo metodo factory: <ul>
     * <li> di default il campo non è visibile in Lista </li>
     * <li> di default il campo  è visibile in Scheda </li>
     * <li> di default non è un campo fisico </li>
     * <li> di default non è modificabile nella scheda </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo      calcolato da costruire
     * @param operazione da applicare ai campi
     * @param osservato  da utilizzare
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo calcola(Campi campo, CampoLogica.Calcolo operazione, Campi osservato) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        ArrayList<String> lista;

        try {    // prova ad eseguire il codice

            /* crea l'instanza della lista */
            lista = new ArrayList<String>();

            /* aggiunge alla lista */
            lista.add(osservato.get());

            unCampo = CampoFactory.calcola(campo.get(), operazione, lista);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea una lista da due campi.
     * <p/>
     *
     * @param primo   campo utilizzato per l'operazione
     * @param secondo campo utilizzato per l'operazione
     *
     * @return lista costruita
     */
    private static ArrayList<String> getLista(String primo, String secondo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;

        try {    // prova ad eseguire il codice

            /* crea la lista di campi da memorizzare */
            lista = new ArrayList<String>();
            lista.add(primo);
            lista.add(secondo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return lista;
    }


    /**
     * Crea un campo di tipo calcolato.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> la logica da applicare di tipo CampoLogica.Calcolo </li>
     * <li> due campi da calcolare </li>
     * </ul>
     * <br>
     * Questo metodo factory: <ul>
     * <li> di default il campo non è visibile in Lista </li>
     * <li> di default il campo  è visibile in Scheda </li>
     * <li> di default non è un campo fisico </li>
     * <li> di default non è modificabile nella scheda </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param nomeCampo  nome interno del campo da costruire
     * @param operazione da applicare ai campi
     * @param primo      campo utilizzato per l'operazione
     * @param secondo    campo utilizzato per l'operazione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo calcola(String nomeCampo,
                                CampoLogica.Calcolo operazione,
                                String primo,
                                String secondo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        ArrayList<String> lista;

        try {    // prova ad eseguire il codice

            /* crea la lista di campi da memorizzare */
            lista = getLista(primo, secondo);

            /* invoca il metodo sovrascritto */
            unCampo = calcola(nomeCampo, operazione, lista);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo calcolato.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> la logica da applicare di tipo CampoLogica.Calcolo </li>
     * <li> una lista di campi da calcolare </li>
     * </ul>
     * <br>
     * Questo metodo factory: <ul>
     * <li> di default il campo non è visibile in Lista </li>
     * <li> di default il campo  è visibile in Scheda </li>
     * <li> di default non è un campo fisico </li>
     * <li> di default non è modificabile nella scheda </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo      della Enumeration dell'interfaccia
     * @param operazione da applicare ai campi
     * @param lista      dei campi utilizzati per l'operazione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo calcola(Campi campo,
                                CampoLogica.Calcolo operazione,
                                ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;


        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.calcola(campo.get(), operazione, lista);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo calcolato.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> la logica da applicare di tipo CampoLogica.Calcolo </li>
     * <li> due campi da calcolare </li>
     * </ul>
     * <br>
     * Questo metodo factory: <ul>
     * <li> di default il campo non è visibile in Lista </li>
     * <li> di default il campo  è visibile in Scheda </li>
     * <li> di default non è un campo fisico </li>
     * <li> di default non è modificabile nella scheda </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campo      della Enumeration dell'interfaccia
     * @param operazione da applicare ai campi
     * @param primo      campo utilizzato per l'operazione
     * @param secondo    campo utilizzato per l'operazione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo calcola(Campi campo,
                                CampoLogica.Calcolo operazione,
                                String primo,
                                String secondo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.calcola(campo.get(), operazione, primo, secondo);

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campo, unCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea un campo di tipo calcolato.
     * <p/>
     * Questo metodo factory richiede obbligatoriamente: <ul>
     * <li> la logica da applicare di tipo CampoLogica.Calcolo </li>
     * <li> due campi da calcolare </li>
     * </ul>
     * <br>
     * Questo metodo factory: <ul>
     * <li> di default il campo non è visibile in Lista </li>
     * <li> di default il campo  è visibile in Scheda </li>
     * <li> di default non è un campo fisico </li>
     * <li> di default non è modificabile nella scheda </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     *
     * @param campoCalcolato        della Enumeration dell'interfaccia
     * @param operazione            da applicare ai campi
     * @param primoCampoOsservato   campo utilizzato per l'operazione
     * @param secondoCampoOsservato campo utilizzato per l'operazione
     *
     * @return il campo creato (non ancora inizializzato)
     */
    public static Campo calcola(Campi campoCalcolato,
                                CampoLogica.Calcolo operazione,
                                Campi primoCampoOsservato,
                                Campi secondoCampoOsservato) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            unCampo = CampoFactory.calcola(campoCalcolato.get(),
                    operazione,
                    primoCampoOsservato.get(),
                    secondoCampoOsservato.get());

            /* recupera alcuni parametri dalla Enumeration */
            CampoFactory.regola(campoCalcolato, unCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unCampo;
    }


}// fine della classe
