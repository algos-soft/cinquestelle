/**
 * Title:     Portale
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.portale;

import it.algos.base.azione.Azione;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.finestra.Finestra;
import it.algos.base.form.Form;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.stato.StatoPortale;
import it.algos.base.scheda.Scheda;
import it.algos.base.toolbar.ToolBar;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;

/**
 * Gestisce il pannello di <CODE>Lista o Scheda/Dialogo</CODE>.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Ogni pannello </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 14.00.13
 */
public interface Portale extends GestioneEventi {

    /**
     * dimensione di default di un portale (raramente usata)
     */
    public static final Dimension DIMENSIONE_DEFAULT = new Dimension(100, 100);

    /**
     * codici di posizionamento della toolbar nel Portale
     * - Se la toolbar e' orizzontale:
     * TOOLBAR_PRIMA significa in alto
     * TOOLBAR_DOPO significa in basso
     * - Se la toolbar e' verticale:
     * TOOLBAR_PRIMA significa a sinistra
     * TOOLBAR_DOPO significa a destra
     */
    public static final int TOOLBAR_PRIMA = 1;

    public static final int TOOLBAR_DOPO = 2;

    /**
     * codifica per individuare il primo componente
     * di un Portale Navigatore
     */
    public static final int COMP_A = 1;

    /**
     * codifica per individuare il secondo componente
     * di un Portale Navigatore
     */
    public static final int COMP_B = 2;

    /**
     * tipo tabella: gestione dei dati tramite jdbc
     */
    public static final int DATI_JDBC = 48;

    /**
     * tipo tabella: gestione dei dati mantenuti in memoria
     */
    public static final int DATI_MEMORIA = 92;

    /**
     * tipo tabella: gestione dei dati mantenuti su file xml
     */
    public static final int DATI_XML = 35;


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     *
     * @param codice del record da caricare
     */
    public abstract void avvia(int codice);


    /**
     * Regolazioni di ri-avvio. <br>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public abstract void avvia();


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * <p/>
     * Metodo chiamato dalla classe che crea questo oggetto dopo che sono
     * stati regolati dalla sottoclasse i parametri indispensabili <br>
     * Viene eseguito una sola volta <br>
     */
    public abstract void inizializza();


    /**
     * Sincronizza la GUI del portale.
     * <p/>
     */
    public abstract void sincronizza();


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     * Regola la scheda appena aggiunta come corrente <br>
     *
     * @param unaScheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda
     */
    public abstract String addScheda(Scheda unaScheda);


    /**
     * Aggiunge un set alla collezione interna.
     * <p/>
     *
     * @param nomeSet da recuperare dal modello
     *
     * @return lo stesso nome del set passato come parametro
     */
    public abstract String addSet(String nomeSet);


    /**
     * Ritorna il pacchetto di informazioni sullo stato del portale.
     * <p/>
     * Il pacchetto puo' non essere aggiornato.<br>
     * Per aggiornarlo chiamare il metodo avvia() del pacchetto.<br>
     *
     * @return un pacchetto con le informazioni
     */
    public abstract Info getInfoStato();


    /**
     * Regola lo stato del portale.
     * <p/>
     * Sincronizza la GUI del Portale <br>
     *
     * @param info il pacchetto di informazioni
     */
    public abstract void setInfoStato(Info info);

//    /**
//     * Uso della toolbar.
//     *
//     * @param tipo della toolbar da utilizzare
//     */
//    public abstract void usaToolBar(TipoToolbar tipo);


    /**
     * Crea una azione specifica del portale.
     * <p/>
     * Usa il nome chiave dell'azione per recuperarla dal Progetto <br>
     * Se la trova, clona l'azione <br>
     * Se creata, aggiunge l'azione alla collezione (chiave-valore) di Azioni
     * del Portale <br>
     *
     * @param chiave chiave per recuperare l'azione dal Progetto
     */
    public abstract void addAzioneProgetto(String chiave);


    /**
     * Aggiunge un bottone alla Toolbar.
     * <p/>
     * Aggiunge l'azione alla collezione del Portale <br>
     *
     * @param azione specifica
     */
    public abstract void addBottoneToolbar(Azione azione);


    /**
     * Aggiunge un bottone alla toolbar.
     * <p/>
     *
     * @param icona per il bottone
     * @param tooltip per il bottone
     * @param azione da invocare quando il bottone viene premuto
     *
     * @return il bottone aggiunto
     */
    public abstract JButton addBottone(Icon icona, String tooltip, Action azione);


    /**
     * Aggiunge una singola azione al portale.
     * <p/>
     * Aggiunge l'azione alla collezione (chiave-valore) di Azioni
     * del Portale <br>
     *
     * @param chiave per la collezione
     * @param unAzione specifica
     */
    public abstract void addAzione(String chiave, Azione unAzione);


    public abstract Navigatore getNavigatore();


    public abstract void setNomeVista(String unNomeVista);


    /* Assegna un voce alla finestra del Portale.
    * <p/>
    * Il voce viene assegnato alla finestre e al dialogo
    * @param voce da assegnare
    */
    public abstract void setTitoloFinestra(String titolo);


    public abstract LinkedHashMap<String, Azione> getAzioni();


    public abstract Azione getAzione(String nomeChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia ActionListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract ActionListener getAzAction(String unaChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia FocusListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract FocusListener getAzFocus(String unaChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia ItemListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract ItemListener getAzItem(String unaChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia KeyListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract KeyListener getAzKey(String unaChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia ListSelectionListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract ListSelectionListener getAzListSelection(String unaChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia MouseListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract MouseListener getAzMouse(String unaChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia PopupMenuListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract PopupMenuListener getAzPopupMenu(String unaChiave);


    /**
     * Restituisce un'azione che implementa l'interfaccia WindowListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public abstract WindowListener getAzWindow(String unaChiave);


    public abstract Lista getLista();


    /**
     * Assegna una Lista al portale <p>
     * Regola il riferimento al Portale nella Lista
     *
     * @param lista la lista da assegnare
     */
    public abstract void setLista(Lista lista);


    /**
     * Ritorna la scheda corrente.
     * <p/>
     * E' la scheda attualmente visualizzata da questo Portale.
     *
     * @return la scheda corrente
     */
    public abstract Scheda getSchedaCorrente();


    /**
     * restituisce una istanza concreta.
     *
     * @return istanza di <code>PortaleBase</code>
     */
    public abstract PortaleBase getPortale();


    /**
     * Ritorna la toolbar contenuta nel portale.
     * <p/>
     *
     * @return la toolbar
     */
    public abstract ToolBar getToolBar();


    public abstract void setToolBar(ToolBar toolBar);


    public abstract JComponent getCompMain();


    /**
     * Posizione della toolbar all'interno del Portale.
     * <p/>
     *
     * @param pos la posizione
     *
     * @see ToolBar.Pos
     */
    public abstract void setPosToolbar(ToolBar.Pos pos);


    /**
     * Assegna il compnente principale al portale.
     *
     * @param compMain componente grafico
     */
    public abstract void setCompMain(JComponent compMain);


    /**
     * Controlla se sono usate le frecce di spostamento ordine nella lista.
     * <p/>
     *
     * @return true se sono usate
     */
    public abstract boolean isUsaFrecceSpostaOrdineLista();


    /**
     * Abilita l'uso delle dei bottoni di spostamento di ordine
     * del record su e giu nella lista.
     * <p/>
     *
     * @param usaFrecce per usare i bottoni di spostamento
     */
    public abstract void setUsaFrecceSpostaOrdineLista(boolean usaFrecce);


    /**
     * Abilita l'uso delle del bottone Duplica Record nella lista.
     * <p/>
     *
     * @param flag per usare il bottone Duplica Record
     */
    public abstract void setUsaDuplicaRecord(boolean flag);


    /**
     * Abilita l'uso del pulsante ricerca nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public abstract void setUsaRicerca(boolean flag);


    /**
     * Abilita l'uso del pulsante Proietta nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public abstract void setUsaProietta(boolean flag);


    /**
     * Controlla l'uso del pulsante Preferito nella lista
     * <p/>
     *
     * @return true se usa il pulsante Preferito
     */
    public abstract boolean isUsaPreferito();


    /**
     * Abilita l'uso del pulsante Preferito nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public abstract void setUsaPreferito(boolean flag);


    public abstract StatoPortale getStato();


    public abstract void setStato(StatoPortale stato);


    /**
     * Ritorna il Modulo del Portale.
     * <p/>
     *
     * @return il modulo del Portale
     */
    public abstract Modulo getModulo();


    /**
     * Ritorna il form contenuto nel Portale.
     * <p/>
     *
     * @return il form contenuto nel Portale, null se il portale
     *         non contiene un form
     */
    public abstract Form getForm();


    /**
     * Ritorna la connessione di questo portale.
     * <p/>
     *
     * @return la connessione utilizzata dal Portale
     */
    public abstract Connessione getConnessione();


    /**
     * Abilita l'uso della toolbar.
     * <p/>
     *
     * @param flag per usare la toolbar
     */
    public abstract void setUsaToolbar(boolean flag);


    /**
     * Abilita o disabilita  l'uso della status bar nella Lista.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param usaStatusBar true per usare la status bar, false per non usarla
     */
    public abstract void setUsaStatusBar(boolean usaStatusBar);


    /**
     * Flag - seleziona l'icona piccola, media o grande.
     * <p/>
     *
     * @param unTipoIcona codice tipo icona (Codifica in ToolBar)
     *
     * @see it.algos.base.toolbar.ToolBar
     */
    public abstract void setTipoIcona(int unTipoIcona);


    /**
     * Abilita l'uso dei pulsanti standard nella toolbar
     * <p/>
     * Il dettaglio su quali pulsanti usare si regola con i metodi specifici per ogni pulsante.
     *
     * @return flag di uso dei pulsanti standard
     */
    public abstract boolean isUsaPulsantiStandard();

    /**
     * Abilita l'uso dei pulsanti standard nella toolbar
     * <p/>
     * Se false, i pulsanti standard non vengono aggiunti alla toolbar
     * Il dettaglio su quali pulsanti usare si regola con i metodi specifici per ogni pulsante.
     *
     * @param usa per usare i pulsanti standard
     */
    public abstract void setUsaPulsantiStandard(boolean usa);

    /**
     * Determina se è usato il bottone Nuovo Record nella lista
     * <p/>
     *
     * @return true se è usato
     */
    public abstract boolean isUsaNuovo();


    /**
     * flag - usa il bottone Nuovo Record.
     *
     * @param flag true per usare il bottone Nuovo Record
     */
    public abstract void setUsaNuovo(boolean flag);


    /**
     * flag - usa il bottone Modifica Record.
     *
     * @param flag true per usare il bottone Modifica Record
     */
    public abstract void setUsaModifica(boolean flag);


    /**
     * Determina il tipo di bottoni nuovo/elimina della lista
     * <p/>
     * Usa la coppia nuovo/elimina oppure la coppia aggiungi/rimuovi <br>
     *
     * @param tipoBottoni nuovo/elimina oppure aggiungi/rimuovi
     */
    public abstract void setTipoBottoni(Lista.Bottoni tipoBottoni);


    /**
     * flag - usa il bottone elimina.
     *
     * @param flag true per usare il bottone elimina
     */
    public abstract void setUsaElimina(boolean flag);


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public abstract void setUsaSelezione(boolean usaSelezione);


    /**
     * flag - usa il bottone di stampa lista.
     *
     * @param flag true per usare il bottone di stampa lista <br>
     */
    public abstract void setUsaStampa(boolean flag);


    /**
     * Inserisce graficamente il portale nella propria finestra.
     */
    public abstract void entraInFinestra();


    /**
     * Rende visibile o invisibile la finestra / dialogo del Portale.
     * <p/>
     * Opera sulla finestra o sul dialogo a seconda del flag usaDialogo del portale
     *
     * @param flag per regolare la visibilità
     */
    public abstract void setFinestraVisibile(boolean flag);


    /**
     * Determina se il Portale usa una finestra propria
     * <p/>
     *
     * @return true se usa una finestra propria
     */
    public abstract boolean isUsaFinestra();


    /**
     * Determina se il Portale deve usare una finestra propria
     * <p/>
     *
     * @param usa true se deve usare una finestra propria
     */
    public abstract void setUsaFinestra(boolean usa);


    /**
     * Determina se il Portale usa una finestra di tipo dialogo
     * <p/>
     *
     * @return true se usa una finestra propria di tipo dialogo
     */
    public abstract boolean isUsaFinestraDialogo();


    /**
     * Ritorna la finestra di questo portale.
     *
     * @return la finestra del portale
     */
    public abstract Finestra getFinestra();


    /**
     * Ritorna la finestra dialogo di questo portale.
     *
     * @return la finestra dialogo del portale
     */
    public abstract JDialog getFinestraDialogo();


    /**
     * Determina se la finestra deve essere di tipo dialogo modale
     * anziché di tipo Finestra
     * <p/>
     * Significativo solo se usaFinestra è true
     *
     * @param usaDialogo true per usare una finestra di tipo dialogo modale
     */
    public abstract void setUsaDialogo(boolean usaDialogo);


    /**
     * Costruisce il menu tabelle specifico.
     * </p>
     */
    public abstract void setMenuTabelle(JMenu menu);


    /**
     * Ritorna la eventuale finestra usata dal portale.
     * <p/>
     * La finestra può essere un JFrame o un JDialog
     */
    public abstract Window getWindow();

//    /**
//     * Classe interna Enumerazione.
//     */
//    public enum TipoToolbar {
//
//        standard(),
//        alternativa()
//
//    }// fine della classe

}// fine della interfaccia
