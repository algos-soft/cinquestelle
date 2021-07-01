/**
 * Title:     MenuBarraBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.menu.barra;

import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.Menu;
import it.algos.base.menu.menu.*;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.PortaleBase;
import it.algos.base.progetto.Progetto;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Barra di menu astratta per la costruzione dei singoli menu.
 * </p>
 * Questa classe astratta: <ul>
 * <li> Estende le funzionalit&agrave della classe <code>JMenuBar</code></li>
 * <li> Serve da superclasse alle sottoclassi concrete </li>
 * <li> Mantiene le variabili di istanza </li>
 * <li> Mantiene i metodi comuni delle sottoclassi </li>
 * <li> Rende disponibili i singoli menu per modifiche, aggiunte
 * cancellazioni delle singole righe </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 16.24.40
 */
public abstract class MenuBarraBase extends JMenuBar implements MenuBarra {

    /**
     * riferimento alla collezione chiave-valore delle azioni del pannello. <br>
     * la finestre fanno riferimento a queste azioni per
     * costruire la propria barra menu
     */
    protected HashMap azioni = null;

    /**
     * riferimento al menu archivio
     */
    protected Menu menuArchivio = null;

    /**
     * riferimento al menu moduli
     */
    protected Menu menuModuli = null;

    /**
     * riferimento al menu composizione
     */
    protected Menu menuComposizione = null;

    /**
     * riferimento al menu strumenti
     */
    protected Menu menuStrumenti = null;

    /**
     * riferimento al menu tabelle
     */
    protected Menu menuTabelle = null;

    /**
     * riferimento al menu specifico
     */
    protected Menu menuSpecifico = null;

    /**
     * riferimento al menu help
     */
    protected Menu menuHelp = null;

    /**
     * riferimento al menu servizio
     */
    protected Menu menuServizio = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public MenuBarraBase() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     */
    public MenuBarraBase(HashMap azioni) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setAzioni(azioni);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     * Costruisce la barra completa, delegando ai vari metodi la creazione
     * di ogni singolo menu <br>
     * Alcuni metodi vengono sovrascritti dalle sottoclassi <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* costruisce la barra */
        this.creaArchivio();
        this.creaModuli();
        this.creaComposizione();
        this.creaStrumenti();
        this.creaTabelle();
//        this.creaSpecifico();
        this.creaHelp();
//        this.creaServizio();
    }// fine del metodo inizia


    /**
     * Aggiunge il menu alla barra di menu.
     * <p/>
     */
    protected void aggiunge(Menu unMenu) {
        super.add(unMenu.getMenu());
    } /* fine del metodo creaArchivio */


    /**
     * Crea il menu archivio.
     * <p/>
     * Metodo sovrascritto nella classe specifica <br>
     */
    protected void creaArchivio() {
    } /* fine del metodo creaArchivio */


    /**
     * Crea il menu moduli.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaModuli() {
        /* crea il menu moduli */
        this.menuModuli = new MenuModuli(azioni);

        /* aggiunge il menu alla barra */
        this.aggiunge(menuModuli);
    } /* fine del metodo */


    /**
     * Crea il menu composizione.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaComposizione() {
        /* crea il menu moduli */
        this.menuComposizione = new MenuComposizione(azioni);

        /* aggiunge il menu alla barra */
        this.aggiunge(menuComposizione);
    } /* fine del metodo */


    /**
     * Crea il menu strumenti.
     * <p/>
     * Metodo sovrascritto nella classe specifica <br>
     */
    protected void creaStrumenti() {
    } /* fine del metodo creaArchivio */


    /**
     * Crea il menu tabelle.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaTabelle() {
        /* crea il menu moduli */
        this.menuTabelle = new MenuTabelle(azioni);

        /* aggiunge il menu alla barra */
        this.aggiunge(menuTabelle);
    } /* fine del metodo */


    /**
     * Crea il menu specifico.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaSpecifico() {
        /* crea il menu moduli */
        this.menuSpecifico = new MenuSpecifico(azioni);

        /* aggiunge il menu alla barra */
        this.aggiunge(menuSpecifico);
    } /* fine del metodo */


    /**
     * Crea il menu aiuto.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaHelp() {
        /* crea il menu moduli */
        this.menuHelp = new MenuHelp(azioni);

        /* aggiunge il menu alla barra */
        this.aggiunge(menuHelp);
    } /* fine del metodo */


    /**
     * Crea il menu servizio.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaServizio() {
        /* crea il menu moduli */
        this.menuServizio = new MenuServizio(azioni);

        /* aggiunge il menu alla barra */
        this.aggiunge(menuServizio);
    } /* fine del metodo */


    /**
     * Crea i menu moduli e tabelle.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge ai menu Moduli e Tabelle i moduli desiderati da questo modulo <br>
     * Spazzola la lista dei moduli desiderati in menu da questo modulo.<br>
     * Aggiunge i moduli di tipo Tabella al menu Tabelle e gli altri <br>
     * al menu Moduli. <br>
     * Se i moduli desiderati in menu non sono presenti nel Progetto <br>
     * non vengono aggiunti ai menu. <br>
     */
    public void creaMenuModuli(ArrayList unaLista) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Action unAzione;
        String unaChiave;
        it.algos.base.menu.menu.Menu moduli;
        it.algos.base.menu.menu.Menu tabelle;
        JCheckBoxMenuItem checkBox;

        try {    // prova ad eseguire il codice

            if (unaLista != null) {
                /* recupera menu Moduli e Tabelle */
                moduli = this.getMenuModuli();
                tabelle = this.getMenuTabelle();

                /* pulisce i menu */
                moduli.getMenu().removeAll();
                tabelle.getMenu().removeAll();

                /* spazzola la lista dei moduli desiderati e li aggiunge */
                for (int k = 0; k < unaLista.size(); k++) {
                    unaChiave = (String)unaLista.get(k);
                    modulo = Progetto.getModulo(unaChiave);

                    if (modulo != null) {
                        /* recupera l'azione */
                        unAzione = modulo.getAzioneModulo();

                        /* switch per usare i due menu */
                        if (modulo.isTabella()) {
//                            tabelle.getMenu().add(unAzione);
                            checkBox = new JCheckBoxMenuItem(unAzione);
                            checkBox.setIcon(null);
                            tabelle.getMenu().add(checkBox);
                        } else {
                            moduli.getMenu().add(unAzione);

//                            checkBox = new JCheckBoxMenuItem(unAzione);
//                            checkBox.setIcon(null);
//                            moduli.getMenu().add(checkBox);
                        } /* fine del blocco if/else */
                    }// fine del blocco if

                } // fine del ciclo for
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Regola la visibilità del modulo.
     * </p>
     * Metodo invocato da un evento lanciato da un'altro modulo <br>
     * Regola la visibilità della riga di menu corrispondente al modulo <br>
     */
    public void regolaModulo(Modulo modulo, PortaleBase.Operazione operazione) {
        /* variabili e costanti locali di lavoro */
        Action azioneModulo;
        Action azioneMenu;
        Menu menuModuli;
        Component[] comps;
        MenuBase item;
        JCheckBoxMenuItem pippo;

        try { // prova ad eseguire il codice
            menuModuli = this.getMenuModuli();
            item = menuModuli.getMenu();
            comps = item.getMenuComponents();

            azioneModulo = modulo.getAzioneModulo();
            for (Component com : comps) {

                if (com instanceof JCheckBoxMenuItem) {
                    pippo = (JCheckBoxMenuItem)com;
                    azioneMenu = pippo.getAction();
                    if (azioneMenu == azioneModulo) {

                        switch (operazione) {
                            case nascosto:
                                pippo.setSelected(false);
                                break;
                            case visibile:
                                pippo.setSelected(true);
                                break;
                        } // fine del blocco switch
                    }// fine del blocco if

                }
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private void setAzioni(HashMap azioni) {
        this.azioni = azioni;
    }


    public Menu getMenuArchivio() {
        return menuArchivio;
    }


    public Menu getMenuModuli() {
        return menuModuli;
    }


    public Menu getMenuComposizione() {
        return menuComposizione;
    }


    public Menu getMenuStrumenti() {
        return menuStrumenti;
    }


    public Menu getMenuTabelle() {
        return menuTabelle;
    }


    public Menu getMenuSpecifico() {
        return menuSpecifico;
    }


    public Menu getMenuHelp() {
        return menuHelp;
    }


    public Menu getMenuServizio() {
        return menuServizio;
    }


    /**
     * restituisce una istanza concreta.
     *
     * @return istanza di <code>MenuBarraBase</code>
     */
    public MenuBarraBase getMenuBarra() {
        return this;
    } // fine del metodo


}// fine della classe
