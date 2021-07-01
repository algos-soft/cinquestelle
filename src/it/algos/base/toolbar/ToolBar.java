/**
 * Title:        ToolBar.java
 * Package:      it.algos.base.toolbar
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 7 settembre 2003 alle 10.09
 */
package it.algos.base.toolbar;

import it.algos.base.azione.Azione;
import it.algos.base.costante.CostanteColore;
import it.algos.base.lista.Lista;

import javax.swing.*;
import java.awt.*;

/**
 * Interfaccia per la barra di comando con bottoni.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  7 settembre 2003 ore 10.09
 */
public interface ToolBar {


    public static final Color TESTA = CostanteColore.SFONDO_SCHEDA_ALTO;

    public static final Color CORPO = CostanteColore.SFONDO_SCHEDA_CENTRO;

    public static final Color COLORE_DEFAULT = CORPO;

    /**
     * costante per individuare l'icona piccola
     */
    public static final int ICONA_PICCOLA = 1;

    /**
     * costante per individuare l'icona media
     */
    public static final int ICONA_MEDIA = 2;

    /**
     * costante per individuare l'icona grande
     */
    public static final int ICONA_GRANDE = 3;


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Viene chiamato una volta sola <br>
     */
    public abstract void inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void avvia();


    /**
     * Aggiunge un bottone alla toolbar.
     * <p/>
     * Costruisce il bottone con l'azione<br>
     * Regola la visibilita' del testo secondo il flag <br>
     * Regola l'icona da mostrare secondo il flag <br>
     * Inserisce il separatore secondo il flag <br>
     * Invoca il metodo delegato per regolare i caratteri dell'azione <br>
     *
     * @param azione da aggiungere
     *
     * @return il bottone aggiunto
     */
    public abstract JButton addBottone(Azione azione);


    /**
     * Aggiunge un bottone alla toolbar alla posizione desiderata (0 per la prima).
     * <p/>
     * Costruisce il bottone con l'azione<br>
     *
     * @param azione da aggiungere
     * @param pos posizione nella lista di componenti
     *
     * @return il bottone aggiunto
     */
    public abstract JButton addBottone(Azione azione, int pos);

//    /**
//     * Aggiunge un'azione specifica alla toolbar.
//     * <p/>
//     * Costruisce il bottone con l'azione<br>
//     * Regola la visibilit&agrave; del testo secondo il flag <br>
//     * Regola l'icona da mostrare secondo il flag <br>
//     * Inserisce il separatore secondo il flag <br>
//     * Invoca il metodo delegato per regolare i caratteri dell'azione <br>
//     *
//     * @param azione istanza dell'azione da aggiungere
//     */
//    public abstract void aggiungeAzioneSpecifica(Azione azione) ;

//    /**
//     * Recupera l'azione dal <code>Portale</code>.
//     * <p/>
//     *
//     * @param unaChiave nome interno dell'azione
//     *
//     * @return l'azione da restituire
//     */
//    public abstract Azione getAzione(String unaChiave);


    /**
     * Ritorna true se la toolbar e' verticale.
     * <p/>
     *
     * @return true se la toolbar e' verticale
     */
    public abstract boolean isVerticale();


    /**
     * Regola l'orientamento della toolbar, orizzontale o verticale.
     * <p/>
     *
     * @param flag true per verticale, false per orizzontale
     */
    public abstract void setVerticale(boolean flag);


    /**
     * Regola l'orientamento della toolbar, orizzontale o verticale.
     * <p/>
     *
     * @param orientamento JToolBar.HORIZONTAL o JToolBar.VERTICAL
     */
    public abstract void setOrientamento(int orientamento);


    /**
     * flag - visualizza il testo dell'azione oltre all'icona
     */
    public abstract void setMostraTesto(boolean verofalso);


    /**
     * Ritorna il tipo di icone utilizzate
     * <p/>
     *
     * @return il tipo, ToolBar.ICONA_PICCOLA, ToolBar.ICONA_MEDIA, ToolBar.ICONA_GRANDE
     */
    public abstract int getTipoIcona();


    /**
     * Assegna il tipo di icone utilizzate
     * <p/>
     *
     * @param tipo, ToolBar.ICONA_PICCOLA, ToolBar.ICONA_MEDIA, ToolBar.ICONA_GRANDE
     */
    public abstract void setTipoIcona(int tipo);


    /**
     * Determina se è usato il bottone Nuovo Record nella lista
     * <p/>
     *
     * @return true se è usato
     */
    public boolean isUsaNuovo();


    public void setUsaNuovo(boolean usaNuovo);


    /**
     * Determina il tipo di bottoni nuovo/elimina della lista
     * <p/>
     * Usa la coppia nuovo/elimina oppure la coppia aggiungi/rimuovi <br>
     *
     * @param tipoBottoni nuovo/elimina oppure aggiungi/rimuovi
     */
    public void setTipoBottoni(Lista.Bottoni tipoBottoni);


    public void setUsaModifica(boolean usaModifica);


    public void setUsaElimina(boolean usaElimina);


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public abstract void setUsaSelezione(boolean usaSelezione);


    /**
     * flag - usa i bottoni delle frecce su e giu <br>
     * flag - usa i bottoni delle frecce di spostamento record <br>
     */
    public abstract void setUsaFrecce(boolean usaFrecce);


    /**
     * Determina se la toolbar contiene i comandi per
     * l'ordinamento manuale sul campo Ordine
     * <p/>
     *
     * @return true se e' ordinabile manualmente
     */
    public abstract boolean isUsaFrecce();


    /**
     * Abilita l'uso delle del bottone Duplica.
     * <p/>
     *
     * @param flag per usare il bottone Duplica
     */
    public abstract void setUsaDuplica(boolean flag);


    /**
     * flag - usa il bottone stampa<br>
     */
    public abstract void setUsaStampa(boolean usaStampa);


    /**
     * Abilita l'uso del pulsante Ricerca.
     * <p/>
     *
     * @param flag per abilitare il pulsante
     */
    public abstract void setUsaRicerca(boolean flag);


    /**
     * Abilita l'uso del pulsante Proietta.
     * <p/>
     *
     * @param flag per abilitare il pulsante
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


    /**
     * Restituisce l'oggetto concreto della classe principale.
     *
     * @return oggetto grafico JToolBar restituito dall'interfaccia
     */
    public abstract JToolBar getToolBar();


    public abstract JButton getBotEscape();


    public abstract JButton getBotEnter();


    /**
     * Recupera un bottone dalla mappa bottoni tramite la chiave
     * <p/>
     *
     * @param chiave nome chiave del bottone
     * Se il bottone è stato costruito con un'azione che aveva la chiave, è la chiave dell'azione
     * Se l'azione non aveva chiave, è il nome della classe dell'azione
     *
     * @return il bottone richiesto
     */
    public abstract JButton getBottone(String chiave);


    /**
     * Rimuove un bottone dalla toolbar.
     * <p/>
     *
     * @param chiave nome chiave del bottone
     * Se il bottone è stato costruito con un'azione che aveva la chiave, è la chiave dell'azione
     * Se l'azione non aveva chiave, è il nome della classe dell'azione
     */
    public abstract void removeBottone(String chiave);


    /**
     * Classe interna Enumerazione
     * <p/>
     * Possibilità di posizionamento di una Toolbar in un Portale
     */
    public enum Pos {

        nord(),
        sud(),
        est(),
        ovest(),
        nessuna(),;

    }// fine della classe


}// fine della interfaccia
