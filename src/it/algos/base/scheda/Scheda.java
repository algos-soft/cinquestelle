/**
 * Title:     Scheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-lug-2004
 */
package it.algos.base.scheda;

import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.form.Form;
import it.algos.base.portale.Portale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Facade</b>  <br>
 * B - Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package. <br>
 * <br> <br>
 * Questo package comprende <ul>
 * <li> Scheda - interfaccia <br>
 * <li> SchedaBase - classe astratta <br>
 * <li> SchedaDefault - classe concreta <br>
 * <li> Schedafactory - classe concreta <br>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-lug-2004 ore 14.07.52
 */
public interface Scheda extends Form {

    /**
     * usata in fase di sviluppo per vedere l'oggetto facilmente
     */
    public static final String NOME_CHIAVE_DEFAULT = "schedadefault";

    /* Dimensione standard per una scheda piccola */ Dimension DIM_SCHEDA_PICCOLA = new Dimension(
            200,
            150);

    /* Dimensione standard per una scheda media */ Dimension DIM_SCHEDA_MEDIA = new Dimension(300,
            200);

    /* Dimensione standard per una scheda grande */ Dimension DIM_SCHEDA_GRANDE = new Dimension(500,
            400);

    /* Dimensione di default per una scheda generica */ Dimension DIM_SCHEDA_DEFAULT =
            DIM_SCHEDA_MEDIA;

    /**
     * codifica del bottone selezionato di default nel dialogo di chiusura scheda
     */
    public static final int BOTTONE_ANNULLA = 1;

    public static final int BOTTONE_ABBANDONA = 2;

    public static final int BOTTONE_REGISTRA = 3;

    /**
     * codifica dell'azione Annulla nel dialogo di chiusura
     */
    public static final int ANNULLA = 1;

    /**
     * codifica dell'azione Abbandona nel dialogo di chiusura
     */
    public static final int ABBANDONA = 2;

    /**
     * codifica dell'azione Registra nel dialogo di chiusura
     */
    public static final int REGISTRA = 3;

    /**
     * codifica per identificare la scheda
     */
    public static final String NOME_SET_DEFAULT = CostanteModulo.SET_BASE_DEFAULT;


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void inizializza();


    public abstract void avvia(int codice);


    public abstract void avvia();


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     */
    public abstract void sincronizza();


    /**
     * Posizionamento del cursore sul campo della pagina corrente
     * individuato dalla posizione.
     * <p/>
     *
     * @param pos posizione nella lista dei soli campi della pagina corrente
     * 1 per il primo campo
     */
    public abstract void posizionaCursore(int pos);


    /**
     * Posizionamento del cursore sul campo della scheda
     * individuato dal nome.
     * <p/>
     *
     * @param nome del campo
     */
    public abstract void posizionaCursore(String nome);

//    public abstract boolean controlloRegistrazione();


    /**
     * Abilita il flag visibile del record.
     * <p/>
     */
    public abstract void setRecordVisibile();


    public abstract JComponent getScheda();


    /**
     * Ritorna la connessione che la scheda deve utilizzare per il recupero dei dati.
     * <p/>
     * Se è stata assegnata una connessione, usa la connessione assegnata
     * Se non è stata assegnata una connessione, usa la connessione del Navigatore che la contiene
     * Altrimenti, usa la connessione del modulo di riferimento
     *
     * @return la connessione da utilizzare
     */
    public abstract Connessione getConnessione();

//    public abstract Libro getLibro();

//    public abstract void setLibro(Libro libro);


    public abstract Dimension getDimensione();

//    public abstract LinkedHashMap getCampi();

//    /**
//     * Recupera un campo dalla collezione di campi della scheda.
//     * <p>
//     * @param chiave per recuperare il campo
//     * @return il campo recuperato
//     */
//    public abstract Campo getCampo(String chiave);


    public abstract int getCodice();

//    /**
//     * Controlla se i campi sono stati modificati.
//     * </p>
//     * Aggiorna la memoria coi valori provenienti dai componenti GUI <br>
//     * Aggiorna i componenti GUI coi valori provenienti dalla memoria <br>
//     * Questo doppio passaggio in avanti ed indietro dei valori si rende
//     * necessario perche': <ul>
//     * <li> alcuni campi basano il loro valore sui valori della memoria di
//     * altri campi (campi calcolati) </li>
//     * <li> alcuni campi basano il valore di alcune componenti GUI accessorie
//     * sul proprio valore in memoria (decoratori Legenda e Estratto) </li>
//     * </ul>
//     *
//     * @return modificati true se anche uno solo campo e' stato modificato <br>
//     */
//    public abstract boolean isModificata();


    /**
     * Controlla se la scheda e' valida.
     * </p>
     * Tutti i campi visibili devono avere un valore valido (o  vuoto).
     * I campi obbligatori non devono essere vuoti.
     * E' condizione necessaria (ma non sufficiente) perche'
     * la scheda sia registrabile.
     *
     * @return true se i campi sono tutti validi <br>
     */
    public abstract boolean isValida();


    /**
     * Ritorna il motivo per il quale la scheda non è valida.
     * <p/>
     *
     * @return il motivo per il quale la scheda non è valida
     */
    public abstract String getMotivoNonValida();


    /**
     * Assegna alla scheda una connessione da utilizzare
     * per la lettura e la scrittura sul database
     * <p/>
     *
     * @param connessione da utilizzare
     */
    public abstract void setConnessione(Connessione connessione);


    public abstract boolean isNuovoRecord();


    /**
     * Regola il flag di Nuovo Record della scheda
     * <p/>
     *
     * @param flag true se la scheda sta presentando un  nuovo record
     */
    public abstract void setNuovoRecord(boolean flag);


    /**
     * Verifica se la scheda e' registrabile.
     * <p/>
     * Perche' la scheda sia registrabile deve essere valida
     * ed essere stata modificata rispetto al backup
     *
     * @return true se e' registrabile
     */
    public abstract boolean isRegistrabile();


    /**
     * Verifica se la scheda e' chiudibile.
     * <p/>
     *
     * @return true se e' chiudibile
     */
    public abstract boolean isChiudibile();


    /**
     * Ritorna l'elenco dei campi fisici modificati.
     * <p/>
     *
     * @return l'elenco dei campi fisici modificati
     */
    public abstract ArrayList<Campo> getCampiFisiciModificati();


    /**
     * Chiude la scheda con un dialogo di conferma per la registrazione.
     * <p/>
     * Il dialogo viene presentato solo se la scheda è modificata
     *
     * @param bottoneDefault codice del bottone preselezionato nel dialogo
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return codice di chiusura della scheda:
     *         - Scheda.ANNULLA = chiusura non avvenuta
     *         - Scheda.ABBANDONA = chiusura avvenuta con abbandono delle eventuali modifiche
     *         - Scheda.REGISTRA = chiusura avvenuta con registrazione delle eventuali modifiche
     */
    public abstract int richiediChiusuraConDialogo(int bottoneDefault, boolean dismetti);


    /**
     * Chiude la scheda senza dialogo di conferma per la registrazione.
     * <p/>
     *
     * @param registra true per registrare il record
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return true se la scheda e' stata chiusa
     */
    public abstract boolean richiediChiusuraNoDialogo(boolean registra, boolean dismetti);


    /**
     * Ricarica la scheda con i valori presenti sul database.
     * <p/>
     *
     * @return true se riuscito
     */
    public abstract boolean ricaricaScheda();


    /**
     * Ricarica la scheda con i valori di backup.
     * <p/>
     */
    public abstract void restoreBackupScheda();


    public abstract void setPortale(Portale portale);


    public abstract Portale getPortale();


    /**
     * Ritorna il numero della pagina correntemente visualizzata.
     * <p/>
     *
     * @return il numero della pagina correntemente visualizzata (0 per la prima)
     */
    public abstract int getNumeroPagina();


    /**
     * Regola il colore della spia modificato.
     * <p/>
     *
     * @param codice il codice del colore
     * puo' essere SPIA_ROSSA, SPIA_VERDE, SPIA_GIALLA
     *
     * @see SchedaStatusBar
     */
    public abstract void setColoreSpiaModificato(int codice);


    /**
     * Visualizza una data pagina della scheda.
     * <p/>
     *
     * @param pagina numero della pagina (0 per la prima)
     */
    public abstract void vaiPagina(int pagina);


    /**
     * Flag - ritorna true se la scheda usa la status bar.
     * <p/>
     *
     * @return true se usa la status bar
     */
    public abstract boolean isUsaStatusBar();


    public abstract void setUsaStatusBar(boolean usaStatusBar);


    /**
     * Ritorna la status bar della scheda.
     * <p/>
     *
     * @return la status bar della scheda
     */
    public abstract SchedaStatusBar getStatusBar();


    public abstract String getNomeChiave();


    public abstract void setNomeChiave(String nomeChiave);


    /*
    * ritorna true se il record è stato registrato
    */
    public abstract boolean isRegistrato();


//    /*
//    * Ritorna true se il record è stato abbandonato
//    * <p>
//    * @return true se abbandonato
//    */
//    public abstract boolean isAbbandonato();


    /**
     * Ritorna la scheda base.
     * <p/>
     */
    public abstract SchedaBase getSchedaBase();


    public interface Controlli {

        public abstract boolean isValido(Scheda scheda);


        public abstract String getMessaggio(Scheda scheda);


    }// fine della classe
}// fine della interfaccia
