/**
 * Title:     Lista
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      7-set-2004
 */
package it.algos.base.lista;

import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2TablePrinter;
import it.algos.base.campo.base.Campo;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.PortaleLista;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.tavola.Tavola;
import it.algos.base.vista.Vista;

import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

/**
 * Presentazione di dati sotto forma di elenco.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package.</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 7-set-2004 ore 7.09.01
 */
public interface Lista extends GestioneEventi {

    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche. <br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se
     * non riescono a portare a termine la propria inizializzazione specifica.<br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     */
    public abstract void avvia();


    /**
     * Sincronizzazione della lista.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la lista.
     */
    public abstract void sincronizza();


    /**
     * Carica tutti i records.
     * <p/>
     * Ripristina al valore di default il filtroOld corrente <br>
     * Ricarica i records <br>
     */
    public abstract void caricaTutti();


    /**
     * Mostra solo i records selezionati.
     */
    public abstract void soloSelezionati();


    /**
     * Nasconde i records selezionati.
     */
    public abstract void nascondeSelezionati();


    /**
     * carica i i records specificati.
     * <p/>
     * Crea un filtro basato sui records specificati <br>
     * Regola la variabile filtro <br>
     * Ricarica i records <br>
     */
    public abstract void caricaSelezione(int[] codici);


    /**
     * Porta una riga in area visibile.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     */
    public abstract void setRigaVisibile(int indice);


    /**
     * Seleziona una riga nella lista.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     */
    public abstract void setRigaSelezionata(int indice);


    /**
     * Seleziona un gruppo di righe nella lista.
     * <p/>
     *
     * @param indici l'elenco delle righe da selezionare
     */
    public abstract void setRigheSelezionate(int[] indici);


    /**
     * Porta una riga in area visibile e la seleziona.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     */
    public abstract void setRigaVisibileSelezionata(int indice);


    /**
     * Porta un record in area visibile.
     * <p/>
     *
     * @param codice il codice chiave del record
     */
    public abstract void setRecordVisibile(int codice);


    /**
     * Seleziona un record.
     * <p/>
     * Se il record non e' in area visibile, rimane non visibile.
     *
     * @param codice il codice chiave del record
     */
    public abstract void setRecordSelezionato(int codice);


    /**
     * Seleziona uno o piu' records nella lista.
     * <p/>
     * Trasforma l'elenco di codici nelle relative posizioni
     * Delega alla tavola la selezione
     *
     * @param codici l'elenco dei codici dei record
     */
    public abstract void setRecordsSelezionati(int[] codici);


    /**
     * Seleziona un record e lo porta in area visibile.
     * <p/>
     *
     * @param codice il codice chiave del record
     */
    public abstract void setRecordVisibileSelezionato(int codice);


    /**
     * Porta l'ultima riga in area visibile.
     * <p/>
     */
    public abstract void setUltimaRigaVisibile();


    /**
     * Seleziona tutti i records della <code>Tavola</code>.
     */
    public abstract void selezionaTutti();


    /**
     * Elimina la selezione dei records della <code>Tavola</code>.
     */
    public abstract void eliminaSelezione();


    /**
     * Carica la selezione di records.
     * <p/>
     * Opera col filtro base piu quello corrente <br>
     * Recupera l'ordinamento dalla tavola <br>
     * Recupera i campi dalla Vista <br>
     * Costruisce la Query <br>
     * Delega al Db il caricamento dei dati con la query preparata <br>
     * Assegna il risultato ai modello dati della lista <br>
     */
    public abstract void caricaSelezione();

    /**
     * Aggiorna i valori dei totali.
     * <p/>
     */
    public void aggiornaTotali();

    /**
     * Assegna un valore a una cella dei totali.
     * <p/>
     *
     * @param campo totalizzato
     * @param valore da assegnare
     */
    public abstract void setTotale(Campo campo, Number valore);


    public abstract PortaleLista getPortale();


    public abstract void setPortale(PortaleLista portale);


    public abstract void setTavola(Tavola tavola);

    /* Ritorna la tavola che gestisce i totali */
    public Tavola getTavolaTotali();

    /**
     * recupera dal modello i numeri delle righe selezionate.
     * costruisce il valore della chiave per ogni riga selezionata
     *
     * @return int[] un array delle chiavi selezionate
     */
    public abstract int[] getChiaviSelezionate();


    /**
     * recupera dal modello i numeri delle righe visualizzate.
     * costruisce il valore della chiave per ogni riga visualizzata
     *
     * @return int[] un array delle chiavi visualizzate
     */
    public abstract int[] getChiaviVisualizzate();


    /**
     * Restituisce la posizione della riga selezionata nella lista.
     * <p/>
     *
     * @return la posizione della riga selezionata (0 per la prima)
     *         (-1 se non ci sono righe selezionate)
     */
    public abstract int getRigaSelezionata();


    /**
     * Restituisce la posizione delle righe selezionate nella lista.
     * <p/>
     *
     * @return elenco delle posizioni delle righe selezionate (la prima è zero)
     */
    public abstract int[] getRigheSelezionate();


    /**
     * Restituisce la posizione nella lista di un dato codice chiave.
     * <p/>
     *
     * @param chiave il codice chiave da cercare
     *
     * @return la posizione della riga (0 per la prima, -1 se non trovata)
     */
    public abstract int getPosizione(int chiave);


    /**
     * Restituisce il valore di una cella.
     * <p/>
     *
     * @param riga della tavola (0 per la prima)
     * @param campo del quale recuperare il valore
     *
     * @return il valore della cella richiesta
     */
    public abstract Object getValueAt(int riga, Campo campo);


    /**
     * Restituisce il valore di una cella della riga selezionata.
     * <p/>
     *
     * @param campo del quale recuperare il valore
     *
     * @return il valore della cella richiesta
     */
    public abstract Object getSelectedValueAt(Campo campo);


    /**
     * Assegna il voce a una colonna.
     * <p/>
     *
     * @param campo della colonna
     * @param titolo da assegnare
     */
    public abstract void setTitoloColonna(Campo campo, String titolo);


    /**
     * Restituisce l'oggetto concreto dell'interfaccia.
     * <p/>
     *
     * @return oggetto base concreto
     */
    public abstract ListaBase getListaBase();


    /**
     * Restituisce l'oggetto concreto dell'interfaccia.
     * <p/>
     *
     * @return componente concreto
     */
    public abstract JComponent getLista();


    /**
     * Ritorna il nome della Vista della Lista.
     * <p/>
     *
     * @return il nome della Vista
     */
    public abstract String getNomeVista();


    /**
     * Ritorna la Vista della Lista.
     * <p/>
     *
     * @return vista la Vista da assegnata alla lista
     */
    public abstract Vista getVista();


    /**
     * Regola la Vista per la Lista.
     * <p/>
     *
     * @param vista la Vista da assegnare alla lista
     */
    public abstract void setVista(Vista vista);


    /**
     * Regola la Vista per la Lista.
     * <p/>
     *
     * @param nomeVista nome della Vista
     */

    public abstract void setNomeVista(String nomeVista);


    public abstract TavolaModello getModello();


    public abstract void setModello(TavolaModello modello);


    /**
     * Attiva l'aggiornamento continuo dei totali.
     * <p/>
     * Se attivato, i totali vengono aggiornati ad ogni caricamento dei
     * dati nella lista.
     * Di default i totali vengono aggiornati solo quando
     * cambiano i filtri.
     *
     * @param flag per attivare l'aggiornamento continuo dei totali
     */
    public abstract void setAggiornamentoTotaliContinuo(boolean flag);


    /**
     * Restituisce il codice chiave della riga selezionata nella lista.
     * <p/>
     *
     * @return codice chiave
     */
    public abstract int getRecordSelezionato();


    /**
     * Restituisce il codice chiave di una riga della lista alla posizione data.
     * <p/>
     *
     * @param posizione la posizione nella lista (0 per la prima)
     *
     * @return il codice chiave della riga alla posizione data
     */
    public abstract int getChiave(int posizione);


    /**
     * Restituisce il codice chiave della riga selezionata.
     * <p/>
     * Controlla che ci sia una ed una sola riga selezionata <br>
     *
     * @return il codice chiave della riga alla posizione data (-1 se non valido)
     */
    public abstract int getChiaveSelezionata();


    /**
     * Restituisce il numero di righe selezionate nella lista
     * <p/>
     *
     * @return il numero di righe selezionate
     */
    public abstract int getQuanteRigheSelezionate();

//    /**
//     * Restituisce una mappa di valori per un dato codice chiave.
//     *
//     * @param chiave il codice chiave
//     *
//     * @return una HashMap chiave(String) - valore(Object)
//     */
//    public abstract HashMap getMappaValori(int chiave);
//
//
//    /**
//     * Restituisce una mappa di valori per la riga selezionata.
//     *
//     * @return una HashMap chiave(String) - valore(Object)
//     */
//    public abstract HashMap getMappaValori();


    /**
     * Comando del mouse cliccato nei titoli.
     * <p/>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     */
    public abstract void colonnaCliccata(MouseEvent unEvento);


    /**
     * Ordina sulla colonna a sinistra.
     * <p/>
     * Sposta l'ordinamento sulla colonna a sinistra di quella attuale <br>
     * Se l'ordinamento era sulla prima colonna, non esegue nulla <br>
     * Aggiorna il colore del voce <br>
     */
    public abstract void colonnaSinistra();


    /**
     * Ordina sulla colonna a destra.
     * <p/>
     * Sposta l'ordinamento sulla colonna a destra di quella attuale <br>
     * Se l'ordinamento era sull'colonna, non esegue nulla <br>
     * Aggiorna il colore del voce <br>
     */
    public abstract void colonnaDestra();


    /**
     * Ordina la lista su un campo.
     * <p/>
     * Invoca il metodo sovrascritto della classe <br>
     * Ricarica la selezione <br>
     *
     * @param campo il campo sul quale ordinare la lista
     */
    public abstract void ordina(Campo campo);


    /**
     * Ordina la lista su un campo.
     * <p/>
     * Ricarica la selezione
     *
     * @param campo il campo sul quale ordinare la lista
     * @param verso VERSO_ASCENDENTE, VERSO_DISCENDENTE, VERSO_DEL_CAMPO
     */
    public abstract void ordina(Campo campo, int verso);

    /**
     * Ritorna il campo corrispondente a una colonna visibile
     * nella lista.
     * <p/>
     *
     * @param colonna l'indice della colonna visibile (0 per la prima)
     *
     * @return il campo corrispondente
     */
    public abstract Campo getCampo(int colonna);

    /**
     * Ritorna l'indice della colonna della Tavola corrispondente a un campo della Lista.
     * <p/>
     *
     * @param campo il campo per il quale ricavare l'indice di colonna
     *
     * @return l'indice della colonna corrispondente
     */
    public abstract int getColonna(Campo campo);


    /**
     * Recupera il campo della colonna corrente.
     * <p/>
     *
     * @return il campo della colonna corrente (selezionata)
     */
    public abstract Campo getCampoCorrente();


    /**
     * Ritorna il Modulo al quale la Lista si riferisce
     */
    public abstract Modulo getModulo();


    public abstract Filtro getFiltroBase();


    public abstract void setFiltroBase(Filtro filtroBase);


    public abstract Filtro getFiltroCorrente();


    /**
     * Ritorna un filtro che isola i record correntemente selezionati.
     *
     * @return un filtro corrispondente ai record selezionati
     */
    public Filtro getFiltroSelezionati();


    /**
     * Ritorna il filtro completo della lista.
     * <p/>
     * E' composto da filtro base + filtro corrente
     *
     * @return il filtro completo
     */
    public abstract Filtro getFiltro();


    public abstract void setFiltroCorrente(Filtro filtroCorrente);


    /**
     * Aggiunge un filtro al filtro corrente della lista.
     * <p/>
     * Se il filtro corrente non esiste, imposta il filtro
     * appena passato come filtro corrente
     *
     * @param unione la clausola di unione del filtro nuovo al corrente
     * @param filtro il filtro da aggiungere
     */
    public abstract void addFiltroCorrente(String unione, Filtro filtro);


    /**
     * Aggiunge un filtro al filtro corrente della lista.
     * <p/>
     * Se il filtro corrente non esiste, imposta il filtro
     * appena passato come filtro corrente
     * Il nuovo filtro viene aggiunto con la clausola AND
     *
     * @param filtro il filtro da aggiungere
     */
    public abstract void addFiltroCorrente(Filtro filtro);


    public abstract void setCampoOrdinamento(Campo campoOrdinamento);


    public abstract Campo getCampoVisibile();


    public abstract Campo getCampoOrdine();


    public abstract Tavola getTavola();


    public abstract boolean isOrdinataCampoOrdine();


    /**
     * Ritorna lo stato di modificabilità di una colonna.
     * <p/>
     *
     * @param colonna (0 per la prima)
     */
    public abstract boolean isColonnaModificabile(int colonna);


    /**
     * Invocato quando si modifica il valore di una cella nella lista.
     * <p/>
     *
     * @param valore il valore memoria proveniente dall'editing
     * @param codice codice chiave del record editato
     * @param campo campo della colonna editata
     */
    public abstract void setValueAt(Object valore, int codice, Campo campo);


    /**
     * Controlla se � selezionata una riga.
     * <p/>
     *
     * @return vero se &egrave; selezionata una ed una sola riga
     *         falso se non ci sono righe selezionate, oppure se ce n'� pi� di una
     */
    public abstract boolean isRigaSelezionata();


    /**
     * Regola la modalità di selezione.
     * <p/>
     *
     * @param modo di selezione
     * valori ammessi;
     * ListSelectionModel.SINGLE_SELECTION
     * ListSelectionModel.SINGLE_INTERVAL_SELECTION
     * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
     */
    public abstract void setSelectionMode(int modo);


    /**
     * Controlla se il carattere &egrave; compreso tra quelli della collezione.
     * <p/>
     *
     * @param codiceCarattere codifica del carattere
     *
     * @return vero se &egrave; nella collezione
     */
    public abstract boolean isCarattere(int codiceCarattere);


    public abstract boolean isNuovoRecordAbilitato();


    /**
     * Controlla la possibilita' di creare nuovi record per questa Lista.
     * <p/>
     *
     * @param flag true per abilitare la creazione, false per disabilitarla
     */
    public abstract void setNuovoRecordAbilitato(boolean flag);


    public abstract void setModificaRecordAbilitata(boolean modificaRecordAbilitata);


    public abstract boolean isModificaRecordAbilitata();


    public abstract boolean isDeleteAbilitato();


    /**
     * Controlla la possibilita' di eliminare records per questa Lista.
     * <p/>
     *
     * @param flag true per abilitare la eliminazione, false per disabilitarla
     */
    public abstract void setDeleteAbilitato(boolean flag);


    /**
     * Ritorna true se la lista e' ordinabile cliccando sulle colonne.
     * <p/>
     *
     * @return true se ordinabile
     */
    public abstract boolean isOrdinabile();


    /**
     * Abilita l'ordinamento cliccando sulle colonne.
     * <p/>
     *
     * @param flag true per abilitare l'ordinamento, false per disabilitarlo
     */
    public abstract void setOrdinabile(boolean flag);


    /**
     * Ritorna true se la lista e' ordinabile manualmente.
     * <p/>
     * L'ordinamento manuale si effettua con gli appositi pulsanti
     * nella toolbar della lista ed e' basato sul campo Ordine.
     *
     * @return true se ordinabile manualmente
     */
    public abstract boolean isOrdinamentoManuale();


    /**
     * Ritorna la posizione della colonna chiave
     * nella Vista di questa Lista.
     *
     * @return la posizione della colonna chiave (0 per la prima)
     */
    public abstract int getPosColonnaCodice();


    /**
     * Recupera l'ordine corrente della lista.
     *
     * @return l'ordine
     */
    public abstract Ordine getOrdine();


    /**
     * Regola l'ordine della lista.
     * <p/>
     *
     * @param ordine l'oggetto Ordine
     */
    public abstract void setOrdine(Ordine ordine);


    /**
     * Ritorna la dimensione preferita della Lista.
     * <p/>
     *
     * @return la dimensione preferita della Lista
     */
    public abstract Dimension getPreferredSize();


    /**
     * Ritorna l'altezza preferita della lista.
     * <p/>
     *
     * @return il numero di righe visualizzate nella lista
     */
    public abstract int getAltezzaPreferita();


    /**
     * Regola l'altezza preferita della lista.
     * <p/>
     *
     * @param altezzaPreferita il numero di righe da visualizzare
     */
    public abstract void setAltezzaPreferita(int altezzaPreferita);


    /**
     * Ritorna il flag di uso della status bar.
     * <p/>
     *
     * @return true se la lista usa la status bar
     */
    public abstract boolean isUsaStatusBar();


    /**
     * Abilita o disabilita  l'uso della status bar.
     * <p/>
     *
     * @param usaStatusBar true per usare la status bar, false per non usarla
     */
    public abstract void setUsaStatusBar(boolean usaStatusBar);


    /**
     * Ritorna la Status Bar della lista.
     * <p/>
     *
     * @return la Status Bar della lista
     */
    public abstract ListaStatusBar getStatusBar();


    /**
     * Visualizza un testo nel componente custom (centrale).
     * <p/>
     *
     * @param testo da visualizzare nel componente custom (centrale)
     */
    public abstract void setCustom(String testo);


    /**
     * Abilita o disabilita  l'uso della barra dei totali nella Lista.
     * <p/>
     *
     * @param flag true per usare la barra dei totali, false per non usarla
     */
    public abstract void setUsaTotali(boolean flag);


    /**
     * Ritorna il numero totale di records selezionati dal filtro base.
     * <p/>
     * Corrisponde al numero di records virtualmente disponibili
     * per questa lista.<br>
     * Aggiornato ad ogni caricamento dei record.<br>
     *
     * @return il numero di records selezionati dal filtro base
     */
    public abstract int getNumRecordsDisponibili();


    /**
     * Ritorna il numero totale di records visualizzati nella lista.
     * <p/>
     * Corrisponde al numero di records selezionati dal filtro
     * base piu' il filtro corrente.<br>
     * Aggiornato ad ogni caricamento dei record.
     *
     * @return il numero di records attualmente visualizzati.
     */
    public abstract int getNumRecordsVisualizzati();


    public abstract boolean isUsaCarattereFiltro();


    /**
     * Abilita l'uso dei caratteri per filtrare la lista.
     */
    public abstract void setUsaCarattereFiltro(boolean usaCarattereFiltro);


    /**
     * Ritorna l'uso dei filtri pop
     * <p/>
     *
     * @return true se la lista usa i filtri pop
     */
    public abstract boolean isUsaFiltriPop();


    /**
     * Regola l'uso dei filtri pop
     * <p/>
     *
     * @param flag per attivare l'uso dei filtri pop
     */
    public abstract void setUsaFiltriPop(boolean flag);


    /**
     * Aggiorna il valore video di un campo.
     * <p/>
     * Metodo invocato quando il campo prende il fuoco <br>
     * Metodo  invocato solo se il campo (colonna) � modificabile <br>
     *
     * @return l'oggetto Campo per ulteriori usi
     */
    public abstract Campo aggiornaCampoInfuocato();


    /**
     * Intercetta prepareRenderer di JTable.
     * <p/>
     * Prepara il renderer per una cella prima che venga disegnato.
     * Sovrascritto dalle sottoclassi. <br>
     * Permette alla sottoclasse di modificare il componente
     * da visualizzare nella cella. <br>
     *
     * @param comp il componente da visualizzare della cella
     * @param riga l'indice della riga (0 per la prima)
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il componente da visualizzare nella cella
     */
    public abstract Component prepareRenderer(Component comp, int riga, int colonna);


    /**
     * Prende il fuoco sulla tavola.
     * <p/>
     */
    public abstract void grabFocus();


    /**
     * Stampa la lista.
     * <p/>
     *
     * @param titolo eventuale della stampa
     */
    public abstract void stampa(String titolo);

    /**
     * Ritorna un flow printer per la lista completa (dati e totali).
     * <p/>
     *
     * @return il flow Printer creato
     */
    public J2FlowPrinter getFlowPrinterLista();


    /**
     * Ritorna un printer per la stampa della tavola dati.
     * <p/>
     *
     * @return il Printer creato
     */
    public abstract J2TablePrinter getPrinterTavolaDati();

    /**
     * Ritorna un printer per la stampa della tavola totali.
     * <p/>
     *
     * @return il Printer creato
     */
    public abstract J2TablePrinter getPrinterTavolaTotali();



    /**
     * Ritorna il totale di un campo totalizzato nella lista.
     * <p/>
     *
     * @param campo totalizzato
     *
     * @return il valore del totale
     */
    public abstract double getTotale(Campo campo);


    /**
     * Ritorna il totale di un campo totalizzato nella lista.
     * <p/>
     *
     * @param nome del campo totalizzato (campo del modulo della lista)
     *
     * @return il valore del totale
     */
    public abstract double getTotale(String nome);

    /**
     * Classe interna Enumerazione.
     */
    public enum Bottoni {

        creazione(), // nuovo ed elimina
        aggiunta()   // aggiungi ed rimuovi

    }// fine della classe

}// fine della interfaccia
