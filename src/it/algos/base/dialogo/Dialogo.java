/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * Author:    gac
 * Date:      2-nov-2003
 */
package it.algos.base.dialogo;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.form.Form;
import it.algos.base.gestore.GestoreDialogo;
import it.algos.base.pagina.Pagina;
import it.algos.base.portale.Portale;
import it.algos.base.pref.PrefTipo;

import javax.swing.JDialog;
import java.awt.Component;
import java.util.ArrayList;

/**
 * Interfaccia per il package Template.
 * <p/>
 * Costanti pubbliche, codifiche e metodi (astratti) di questo package <br>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un'interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Gestisce un dialogo </li>
 * <li> I dialoghi <b>devono</b> essere modali - cioe' interrompere il <i>thread</i>
 * in esecuzione - perche altrimenti non intercetto piu i
 * <i><b>ritorni</b></i>: </li>
 * <li> bottone premuto e valore selezionato nel dialogo </li>
 * </ul>
 * La finestra si divide in 3 parti: <ul>
 * <li> in alto una scritta di spiegazione, </li>
 * <li> al centro uno o piu' componenti, </li>
 * <li> in basso uno o piu' bottoni (di default <i>Conferma</i>) </li>
 * </ul>
 * Altri bottoni possono essere <i>Annulla, Cancella, Ripristina</i>, ecc <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public interface Dialogo extends Form {

    /**
     * codifica per il dimensionamento dei bottoni.
     * usa la stessa dimensione (esterna) per tutti i bottoni
     */
    public static final int DIMENSIONE_FISSA = 874;

    /**
     * codifica per il dimensionamento dei bottoni.
     * usa una dimensione proporzionale al testo del bottone
     */
    public static final int DIMENSIONE_PROPORZIONALE = 838;

    /**
     * codifica per il dimensionamento dei bottoni.
     * usa per tutti i bottoni la dimensione del testo più lungo
     */
    public static final int DIMENSIONE_MASSIMA = 859;

    /**
     * testo standard per il voce della finestra dialogo avviso.
     */
    public static final String TITOLO_AVVISO = "Avviso";

    /**
     * codifica per l'azione interna Annulla.
     * (di solito a sinistra di Conferma)
     */
    public static final int AZIONE_ANNULLA = 58;

    /**
     * codifica per l'azione interna Conferma.
     * (di solito a destra di Annulla)
     */
    public static final int AZIONE_CONFERMA = 57;

    /**
     * testo standard per l'azione interna Annulla.
     */
    public static final String TESTO_AZIONE_ANNULLA = "Annulla";

    /**
     * testo standard per l'azione interna Conferma.
     * (per modificare il testo, usare un'azione esterna)
     */
    public static final String TESTO_AZIONE_CONFERMA = "Conferma";


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
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
     * Aggiunge al libro (del dialogo) una pagina vuota .
     * <p/>
     *
     * @param titolo il voce della pagina
     *
     * @see it.algos.base.libro.Libro#addPagina(String)
     */
    public abstract Pagina addPagina(String titolo);


    /**
     * Aggiunge al libro (del dialogo) una pagina con un contenuto .
     * <p/>
     *
     * @param titolo il voce della pagina
     * @param contenuto l'oggetto con i contenuti
     *
     * @see it.algos.base.libro.Libro#addPagina(String,Object)
     */
    public abstract Pagina addPagina(String titolo, Object contenuto);


    /**
     * Regola il tipo di dimensionamento dei bottoni.
     * <p/>
     * Codifica in Dialogo <br>
     *
     * @param dimensionamentoBottoni
     *
     * @see Dialogo#DIMENSIONE_FISSA
     * @see Dialogo#DIMENSIONE_MASSIMA
     * @see Dialogo#DIMENSIONE_PROPORZIONALE
     */
    public abstract void setDimensionamentoBottoni(int dimensionamentoBottoni);


    /**
     * Aggiunge un campo alla prima pagina.
     * <p/>
     * Aggiunge un singolo Campo alla pagina iniziale <br>
     * Recupera la pagina iniziale <br>
     * Invoca il metodo sovrascritto della classe <br>
     *
     * @param unCampo da aggiungere
     *
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public abstract void addCampo(Campo unCampo);


    /**
     * Crea un campo testo e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see it.algos.base.campo.base.CampoFactory#testo(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampo(String nomeCampo);


    /**
     * Crea un campo testo e lo aggiunge alla prima pagina, col valore indicato.
     * <p/>
     * Invoca il metodo sovrascritto della classe <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Regola il valore di default <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see it.algos.base.campo.base.CampoFactory#testo(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampo(String nomeCampo, String valore);


    /**
     * Crea un campo testo e lo aggiunge ad una pagina.
     * <p/>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param unaPagina a cui aggiungerlo
     *
     * @return Campo appena creato
     *
     * @see it.algos.base.campo.base.CampoFactory#testo(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public abstract Campo creaCampo(String nomeCampo, Pagina unaPagina);


    /**
     * Crea un campo intero e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#intero(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampoIntero(String nomeCampo);


    /**
     * Crea un campo intero e lo aggiunge ad una pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#intero(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public abstract Campo creaCampoIntero(String nomeCampo, Object valore);


    /**
     * Crea un campo check e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see it.algos.base.campo.base.CampoFactory#checkBox(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampoCheck(String nomeCampo);


    /**
     * Crea un campo check e lo aggiunge ad una pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see it.algos.base.campo.base.CampoFactory#checkBox(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public abstract Campo creaCampoCheck(String nomeCampo, Object valore);


    /**
     * Crea un campo data e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#data(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampoData(String nomeCampo);


    /**
     * Crea un campo data e lo aggiunge ad una pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#data(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public abstract Campo creaCampoData(String nomeCampo, Object valore);


    /**
     * Crea un campo combo e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#comboInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampoCombo(String nomeCampo);


    /**
     * Crea un campo combo e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola il valore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param valore suggerito nel campo
     * @param lista di valori del combo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#comboInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public Campo creaCampoCombo(String nomeCampo, Object valore, ArrayList<PrefTipo> lista);


    /**
     * Crea un campo radioBox e lo aggiunge ad una pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param unaPagina a cui aggiungerlo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioBox(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public abstract Campo creaCampoRadio(String nomeCampo, Pagina unaPagina);


    /**
     * Crea un campo radioBox e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioBox(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampoRadio(String nomeCampo);


    /**
     * Crea un campo di radio bottoni e lo aggiunge ad una pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola i valori hardcoded <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param elencoValori stringa di valori separati da virgola
     * @param unaPagina a cui aggiungerlo
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo,it.algos.base.pagina.Pagina)
     */
    public abstract Campo creaCampoRadioGruppo(String nomeCampo,
                                               String elencoValori,
                                               Pagina unaPagina);


    /**
     * Crea un campo di radio bottoni e lo aggiunge alla prima pagina.
     * <p/>
     * Crea un Campo e lo aggiunge alla prima Pagina <br>
     * Crea un campo da CampoFactory <br>
     * Regola i valori hardcoded <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param nomeCampo nome del campo da creare
     * @param elencoValori stringa di valori separati da virgola
     *
     * @return Campo appena creato
     *
     * @see CampoFactory#radioInterno(String)
     * @see DialogoBase#addCampo(it.algos.base.campo.base.Campo)
     */
    public abstract Campo creaCampoRadioGruppo(String nomeCampo, String elencoValori);


    /**
     * Regola il valore di un campo.
     * <p/>
     * Invoca il metodo della classe delegata <br>
     *
     * @param unCampo da regolare
     * @param unValore da visualizzare a video
     *
     * @see it.algos.base.campo.dati.CampoDati#setMemoria(Object)
     */
    public abstract void setValore(Campo unCampo, Object unValore);


    /**
     * Regola il valore di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna del Dialogo <br>
     * Invoca il metodo sovrascritto della classe <br>
     *
     * @param nomeCampo da regolare
     * @param unValore da visualizzare a video
     *
     * @see DialogoBase#getCampo(String)
     * @see it.algos.base.campo.dati.CampoDati#setMemoria(Object)
     */
    public abstract void setValore(String nomeCampo, Object unValore);


    public abstract Portale getPortale();


    public abstract void setPortale(Portale portale);


    public abstract JDialog getDialogo();


    public abstract String setTitolo(String titolo);


    /**
     * Controlla la ridimensionabilita' della finestra dialogo
     * <p/>
     *
     * @param flag true per rendere la finestra ridimensionabile
     */
    public abstract void setResizable(boolean flag);


    /**
     * Assegna una dimensione fissa al dialogo.
     * <p/>
     * La dimensione si riferisce al contenuto della intera finestra.
     *
     * @param larghezza la larghezza
     * @param altezza l'altezza
     */
    public abstract void setPreferredSize(int larghezza, int altezza);


    public abstract void setMessaggio(String messaggio);


    /**
     * Aggiunge un componente al pannello comandi.
     * <p/>
     *
     * @param comp da aggiungere
     */
    public abstract void addComponenteComando(Component comp);


    public abstract String getBottonePremuto();


    public abstract Object getRisposta();


    /**
     * Inserimento di un carattere a video in un campo testo.
     * </p>
     * Metodo invocato dal Gestore Eventi <br>
     * Controlla se il carattere e' compatibile col Campo <br>
     * Sincronizza il dialogo <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void carattereTestoPremuto(Campo unCampo);


    /**
     * Inserimento del carattere Enter in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Controlla se il carattere e' compatibile col Campo <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void carattereEnterPremuto(Campo unCampo);


    /**
     * Inserimento di un carattere
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void caratterePremuto(Campo unCampo);


    /**
     * Annullamento del dialogo
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void annullaDialogo();


    /**
     * Azione generica del dialogo
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void azioneDialogo(Azione unAzione);


    /**
     * Ripristino del dialogo alla situazione iniziale
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void ripristinaDialogo();


    /**
     * ritorna true se il dialogo � stato confermato
     */
    public abstract boolean isConfermato();


    public abstract void setRidimensionabile(boolean ridimensionabile);


    public abstract GestoreDialogo getGestore();


    /**
     * Invocato quando si preme il bottone conferma.
     * <p/>
     */
    public abstract void eventoConferma();


    /**
     * Invocato quando si preme il bottone conferma o registra.
     * <p/>
     */
    public abstract void confermaRegistra();


    /**
     * Invocato quando si preme il bottone registra.
     * <p/>
     */
    public abstract void eventoRegistra();


    /**
     * Costruisce un bottone con azione associata.
     * <p/>
     * Costruisce un bottone da BottoneFactory <br>
     * Utilizza l'azione interna come listener standard per i bottoni <br>
     * Regola il flag di conferma <br>
     *
     * @param testo visibile del bottone
     * @param flagDismetti se deve dismettere il dialogo quando premuto
     * @param flagConferma se all'uscita deve impostare il flag confermato
     * del dialogo a true (significativo solo se flagDismetti=true)
     *
     * @return il bottone aggiunto
     *
     * @see it.algos.base.componente.bottone.BottoneFactory#creaDialogo(String)
     */
    public abstract BottoneDialogo addBottone(String testo,
                                              boolean flagDismetti,
                                              boolean flagConferma);


    /**
     * Aggiunge un bottone al dialogo.
     * <p/>
     * Recupera il testo del bottone e lo usa come chiave della collezione <br>
     *
     * @param unBottone da aggiungere
     */
    public void addBottone(BottoneDialogo unBottone);


    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.dialogo.Dialogo.java

//-----------------------------------------------------------------------------

