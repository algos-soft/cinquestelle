/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      5-apr-2005
 */
package it.algos.base.form;

import it.algos.base.campo.base.Campo;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.interfaccia.ContenitoreCampi;
import it.algos.base.libro.Libro;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.portale.Portale;
import it.algos.base.wrapper.Campi;

import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Interfaccia Form.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 5-apr-2005 ore 15.25.34
 */
public interface Form extends ContenitoreCampi, GestioneEventi {

    /**
     * ritorna il portale proprietario del form
     */
    public abstract Portale getPortale();


    public abstract void setPortale(Portale portale);


    /**
     * ritorna il pannello del form
     */
    public abstract PannelloBase getPanFisso();


    /**
     * Aggiunge un componente alla prima pagina del dialogo.
     * <p/>
     *
     * @param comp il componente da aggiungere
     */
    public abstract void addComponente(Component comp);


    /**
     * Aggiunge un pannello.
     * <p/>
     * Aggiunge il pannello alla parte grafica <br>
     * Aggunge i campi eventualmente contenuti nel pannello alla collezione <br>
     *
     * @param pan pannello da aggiungere
     */
    public abstract void addPannello(Pannello pan);


    /**
     * Rimuove un pannello.
     * <p/>
     * Rimuove il pannello alla parte grafica <br>
     * Rimuove i campi eventualmente contenuti nel pannello dalla collezione <br>
     *
     * @param pan pannello da rimuovere
     */
    public void removePannello(Pannello pan);


    /**
     * Aggiunge un componente ad una specifica pagina del dialogo.
     * <p/>
     *
     * @param comp il componente da aggiungere
     * @param numero il numero della pagina (0 per la prima)
     */
    public abstract void addComponente(Component comp, int numero);


    /**
     * Ritorna la collezione di campi del form.
     * <p/>
     *
     * @return la collezione di campi del form
     */
    public abstract LinkedHashMap<String, Campo> getCampi();


    /**
     * Ritorna una lista dei campi fisici contenuti nel form.
     * <p/>
     *
     * @return la lista dei campi fisici contenuti nel form
     */
    public abstract ArrayList<Campo> getCampiFisici();


    /**
     * Ritorna l'elenco dei campi Navigatore del form.
     * <p/>
     *
     * @return l'elenco dei campi Navigatore
     */
    public abstract ArrayList<Campo> getCampiNavigatore();


    /**
     * Recupera un campo dalla collezione.
     * <p/>
     *
     * @param chiave per recuperare il campo
     *
     * @return il campo recuperato
     */
    public abstract Campo getCampo(String chiave);


    /**
     * Recupera un campo dalla collezione.
     * <p/>
     *
     * @param campo Enumeration dell'interfaccia
     *
     * @return il campo recuperato
     */
    public abstract Campo getCampo(Campi campo);


    /**
     * Ritorna il Libro contenuto nel form
     * <p/>
     *
     * @return il Libro contenuto nel form
     */
    public abstract Libro getLibro();


    /**
     * Restituisce le pagine del form.
     * <p/>
     * Recupera le pagine dal libro.
     *
     * @return le pagine del form
     */
    public abstract ArrayList<Pagina> getPagine();


    /**
     * Regola il bordo di tutte le pagine del form.
     * <p/>
     *
     * @param bordo da applicare a tutte le pagine
     */
    public abstract void setBordoPagine(Border bordo);


    /**
     * Controlla se il form è modificabile
     * <p/>
     *
     * @return true se è modificabile
     */
    public abstract boolean isModificabile();


    /**
     * Abilita o disabilita il form.
     * <p/>
     *
     * @param flag per abilitare o disabilitare
     */
    public abstract void setModificabile(boolean flag);


    /**
     * Aggiorna le liste dei campi Navigatore
     * contenuti nel form
     * <p/>
     */
    public abstract void aggiornaListeNavigatori();


    /**
     * Controlla se i campi sono stati modificati.
     * </p>
     * Aggiorna la memoria coi valori provenienti dai componenti GUI <br>
     * Aggiorna i componenti GUI coi valori provenienti dalla memoria <br>
     * Questo doppio passaggio in avanti ed indietro dei valori si rende
     * necessario perche': <ul>
     * <li> alcuni campi basano il loro valore sui valori della memoria di
     * altri campi (campi calcolati) </li>
     * <li> alcuni campi basano il valore di alcune componenti GUI accessorie
     * sul proprio valore in memoria (decoratori Legenda e Estratto) </li>
     * </ul>
     *
     * @return modificati true se anche uno solo campo e' stato modificato <br>
     */
    public abstract boolean isModificata();


    /**
     * flag - indica che il form è "attivo", cioè sta
     * visualizzando/modificando un record valido.
     * viene acceso al termine dell'avvio e spento alla
     * chiusura (dialogo) o dismissione (scheda)
     * <p/>
     *
     * @return true se il form è attivo
     */
    public abstract boolean isAttivo();


    /**
     * Ritorna l'elenco dei campi modificati.
     * <p/>
     *
     * @return l'elenco dei campi modificati
     */
    public abstract ArrayList<Campo> getCampiModificati();


    /**
     * Determina se il form e' registrabile.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     * Nel caso di Scheda, se la scheda e' registrabile
     * Nel caso di dialogo, se il dialogo e' confermabile o registrabile
     *
     * @return true se registrabile / confermabile
     */
    public abstract boolean isRegistrabile();


    /**
     * Regola il margine di tutte le pagine.
     * <p/>
     *
     * @param margine da utilizzare
     */
    public abstract void setMargine(Pagina.Margine margine);


    /**
     * Avanza ad un campo specifico.
     * <p/>
     * Regola il fuoco <br>
     *
     * @param nomeCampo interessato
     */
    public abstract void vaiCampo(String nomeCampo);


    /**
     * Avanza ad un campo specifico.
     * <p/>
     * Regola il fuoco <br>
     *
     * @param campo interessato
     */
    public abstract void vaiCampo(Campo campo);


    /**
     * posiziona il fuoco al primo campo del form.
     * <p/>
     */
    public abstract void vaiCampoPrimo();


    /**
     * Restituisce il valore di un campo.
     * <p/>
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     *
     * @param unCampo da interrogare
     *
     * @return valore memoria del campo (oggetto indifferenziato)
     *
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public abstract Object getValore(Campo unCampo);


    /**
     * Restituisce il valore di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna del Dialogo
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo (oggetto indifferenziato)
     *
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public abstract Object getValore(String nomeCampo);


    /**
     * Restituisce il valore stringa di un campo.
     * <p/>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo stringa
     */
    public abstract String getString(String nomeCampo);


    /**
     * Restituisce il valore intero di un campo.
     * <p/>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo intero
     */
    public abstract int getInt(String nomeCampo);


    /**
     * Restituisce il valore double di un campo.
     * <p/>
     * Recupera il campo dalla collezione interna del Dialogo
     * Sincronizza la memoria col valore video <br>
     * Recupera il valore della variabile memoria del Campodati <br>
     * Effettua il casting al tipo intero <br>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo intero
     */
    public abstract double getDouble(String nomeCampo);


    /**
     * Restituisce il valore booleano di un campo.
     * <p/>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo booleano
     */
    public abstract boolean getBool(String nomeCampo);


    /**
     * Restituisce il valore data di un campo.
     * <p/>
     *
     * @param nomeCampo da interrogare
     *
     * @return valore memoria del campo col casting al tipo data
     */
    public abstract Date getData(String nomeCampo);


    /**
     * Assegna un valore a un campo.
     * <p/>
     *
     * @param campo al quale asssegnare il valore
     * @param valore da assegnare
     */
    public abstract void setValore(Campo campo, Object valore);


    /**
     * Assegna un valore a un campo.
     * <p/>
     *
     * @param nome del campo al quale asssegnare il valore
     * @param valore da assegnare
     */
    public abstract void setValore(String nome, Object valore);


}// fine della interfaccia
