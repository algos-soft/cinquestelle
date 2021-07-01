/**
 * Title:        CostanteAzione.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.00
 */
package it.algos.base.costante;

import it.algos.base.azione.Azione;

/**
 * Interfaccia per le costanti delle azioni<br>
 * <br>
 * Costanti 'chiave' per recuperare un oggetto (di tipo AzioneBase)
 * dalla collezione azioni (di tipo HashMap)<br>
 * <p/>
 * La 'chiave' � di tipo stringa e pu� essere usata direttamente;
 * uso le costanti perch� sono pi� comode da ricordare
 * I valori di queste costanti devono essere tutti diversi tra loro;
 * la classe HashMap non accetta due volte lo stesso oggetto<br>
 * <p/>
 * Se inserisco nella classe per due (o tre) volte lo stesso oggetto,
 * non mi da errore, ma mi ritrovo una sola istanza dell'oggetto<br>
 * Nei singoli pacchetti verranno eventualmente (se esistono classi
 * azione specifiche di quel pacchetto) dichiarate delle ulteriori
 * costanti il cui valore sia DIVERSO da ogni valore utilizzato in
 * questa interfaccia<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.00
 */
public interface CostanteAzione {

//    public static final String AZIONE_ABOUT = Azione.AZIONE_ABOUT;

    public static final String ANNULLA_MODIFICHE = Azione.ANNULLA_MODIFICHE;

    public static final String ANNULLA_OPERAZIONE = Azione.ANNULLA_OPERAZIONE;

    public static final String ELIMINA_TESTO = Azione.ELIMINA_TESTO;

    public static final String CARICA_LISTA = Azione.CARICA_LISTA;

    public static final String CARICA_TUTTI = Azione.CARICA_TUTTI;

    public static final String CHIUDE_LISTA = Azione.CHIUDE_LISTA;

    public static final String CHIUDE_SCHEDA = Azione.CHIUDE_SCHEDA;

    public static final String COPIA_TESTO = Azione.COPIA_TESTO;

    public static final String DESELEZIONA_LISTA = Azione.DESELEZIONA_LISTA;

    public static final String ELIMINA_SCHEDA = Azione.ELIMINA_SCHEDA;

    public static final String ELIMINA_SELEZIONE = Azione.ELIMINA_SELEZIONE;

    public static final String INCOLLA_TESTO = Azione.INCOLLA_TESTO;

    public static final String MODIFICA_RECORD = Azione.MODIFICA_RECORD;

    public static final String MOSTRA_APPUNTI = Azione.MOSTRA_APPUNTI;

    public static final String MOSTRA_TUTTI = Azione.MOSTRA_TUTTI;

    public static final String NUOVO_RECORD = Azione.NUOVO_RECORD;

    public static final String ORDINA_LISTA = Azione.ORDINA_LISTA;

    public static final String PALETTE = Azione.PALETTE;

//    public static final String RECORD_PRIMO = Azione.RECORD_PRIMO;

    public static final String RECORD_PRECEDENTE = Azione.RECORD_PRECEDENTE;

    public static final String RECORD_SUCCESSIVO = Azione.RECORD_SUCCESSIVO;

//    public static final String RECORD_ULTIMO = Azione.RECORD_ULTIMO;

    public static final String REGISTRA_RECORD = Azione.REGISTRA_SCHEDA;

    public static final String REGISTRA_LISTA = Azione.REGISTRA_LISTA;

//    public static final String RIMUOVE_SELEZIONATI = Azione.RIMUOVE_SELEZIONATI;

//    public static final String SELEZIONA_TUTTI_RECORDS = Azione.SELEZIONA_TUTTI_RECORDS;

    public static final String SELEZIONA_TUTTO = Azione.SELEZIONA_TUTTO;

    public static final String SOLO_SELEZIONATI = Azione.SOLO_SELEZIONATI;

    public static final String TAGLIA_TESTO = Azione.TAGLIA_TESTO;

//    public static final String ESCE_PROGRAMMA = Azione.ESCE_PROGRAMMA;

    public static final String RIPRISTINA_OPERAZIONE = Azione.RIPRISTINA_OPERAZIONE;

    public static final String APRE_LISTA = Azione.APRE_LISTA;

    public static final String USCITA_CAMPO = Azione.USCITA_CAMPO;

//    public static final String AZ_LOOK_MAC = Azione.AZ_LOOK_MAC;

//    public static final String AZ_LOOK_METAL = Azione.AZ_LOOK_METAL;

//    public static final String AZ_LOOK_MOTIF = Azione.AZ_LOOK_MOTIF;

    public static final String CHIUDE_FINESTRA = Azione.CHIUDE_FINESTRA;


    public static final String ELIMINA_RECORD = Azione.ELIMINA_RECORD;

//    public static final String HELP = Azione.HELP;

    public static final String HELP_PROGRAMMATORE = Azione.HELP_PROGRAMMATORE;

    public static final String RIGA_SU = Azione.RIGA_SU;

    public static final String RIGA_GIU = Azione.RIGA_GIU;

    /**
     * Costante per identificare la risorsa base (booleana)
     */
    public static final String AZIONE_BASE = "azionebase";

    /**
     * Costante per identificare l'azione abilitata alla partenza (booleana)
     */
    public static final String ABILITATA = "abilitata";

    /**
     * Costante per il comando seleziona lista
     */
    public static final int SELEZIONA_LISTA = 0;

    /**
     * Costante per il comando campo selezionato
     */
    public static final int CAMPO_SELEZIONATO = 1;

    /**
     * Stato dei bottoni (abilitato = acceso)
     */
    public static final boolean ACCESO = true;

    /**
     * Stato dei bottoni (disabilitato = spento)
     */
    public static final boolean SPENTO = false;

}// fine della interfaccia
