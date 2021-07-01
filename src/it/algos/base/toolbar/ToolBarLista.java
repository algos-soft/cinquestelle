/**
 * Title:        ToolBarLista.java
 * Package:      it.algos.base.toolbar
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 7 settembre 2003 alle 11.30
 */
package it.algos.base.toolbar;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.portale.Portale;

/**
 * ToolBar del </code>PortaleLista</code>.
 * <p/>
 * Bottoni <code>Azione</code> previsti: <ul>
 * <li> Nuovo record (opzionale) </li>
 * <li> Modifica record (opzionale) </li>
 * <li> Elimina record (opzionale) </li>
 * <li> Mostra tutti i record (sempre visibile) </li>
 * <li> Mostra solo i record selezionati (sempre visibile) </li>
 * <li> Nasconde i record selezionati (sempre visibile) </li>
 * <li> Sposta in alto il record (opzionale) </li>
 * <li> Sposta in basso il record (opzionale) </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  7 settembre 2003 ore 11.30
 * @see it.algos.base.portale.PortaleLista
 */
public final class ToolBarLista extends ToolBarBase {

    /**
     * default per l'uso del bottone nuovo record
     */
    private static final boolean USA_NUOVO = true;

    /**
     * default per l'uso del bottone modifica record
     */
    private static final boolean USA_MODIFICA = true;

    /**
     * default per l'uso del bottone elimina record
     */
    private static final boolean USA_ELIMINA = true;

    /**
     * default per l'uso dei bottoni di selezione
     */
    private static final boolean USA_SELEZIONE = true;

    /**
     * default per l'uso dei bottoni delle frecce su e giu
     */
    private static final boolean USA_FRECCE = false;

    /**
     * default per l'uso del bottone di stampa
     */
    private static final boolean USA_STAMPA = true;

    /**
     * default per l'uso del bottone di ricerca
     */
    private static final boolean USA_RICERCA = true;

    /**
     * default per l'uso del bottone proietta
     */
    private static final boolean USA_PROIETTA = false;

    /**
     * tipo standard di bottoni
     */
    private static final Lista.Bottoni TIPO_BOTTONI = Lista.Bottoni.creazione;


    /**
     * flag - usa il bottone nuovo record
     */
    private boolean isUsaNuovo = false;

    /**
     * flag - usa il bottone duplica record
     */
    private boolean isUsaDuplica = false;


    /**
     * flag - usa il bottone modifica record
     */
    private boolean isUsaModifica = false;

    /**
     * flag - usa il bottone elimina record
     */
    private boolean isUsaElimina = false;

    /**
     * flag - usa i bottoni di selezione
     */
    private boolean isUsaSelezione = false;

    /**
     * flag - usa i bottoni delle frecce di ordinamento amnuale
     */
    private boolean isUsaFrecce = false;

    /**
     * flag - usa il bottone stampa
     */
    private boolean isUsaStampa = false;

    /**
     * flag - usa il bottone ricerca
     */
    private boolean isUsaRicerca = false;

    /**
     * flag - usa il bottone proietta
     */
    private boolean isUsaProietta = false;

    /**
     * flag - usa il bottone preferito
     */
    private boolean isUsaPreferito = false;

    /**
     * bottoni di creazione (normale) o aggiunta (liste tavole incrocio ed altre)
     * bottoni: nuovo ed elimina
     * bottoni: aggiungi ed rimuovi
     */
    private Lista.Bottoni tipoBottoni;


    /**
     * Costruttore completo con parametri.
     *
     * @param unPortale portale proprietario di questa toolbar
     */
    public ToolBarLista(Portale unPortale) {
        /* rimanda al costruttore della superclasse */
        super(unPortale);

        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regolazioni di default */
        this.setUsaNuovo(USA_NUOVO);
        this.setUsaModifica(USA_MODIFICA);
        this.setUsaElimina(USA_ELIMINA);
        this.setUsaSelezione(USA_SELEZIONE);
        this.setUsaFrecce(USA_FRECCE);
        this.setUsaStampa(USA_STAMPA);
        this.setUsaRicerca(USA_RICERCA);
        this.setUsaProietta(USA_PROIETTA);
        this.setTipoBottoni(TIPO_BOTTONI);
    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     */
    public void inizializza() {
        super.inizializza();
    }

    /**
     * Aggiunge i bottoni standard.
     * <p/>
     */
    protected void addBottoniStandard () {
        if (this.isUsaNuovo()) {
            switch (this.getTipoBottoni()) {
                case creazione:
                    super.addBottone(Azione.NUOVO_RECORD);
                    break;
                case aggiunta:
                    super.addBottone(Azione.AGGIUNGI_RECORD);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
        }// fine del blocco if

        /* modifica di un record (di solito presente) */
        if (this.isUsaModifica()) {
            super.addBottone(Azione.MODIFICA_RECORD);
        }// fine del blocco if

        /* duplicazione di un record */
        if (this.isUsaDuplica()) {
            super.addBottone(Azione.DUPLICA_RECORD);
        }// fine del blocco if

        /* eliminazione di un record (di solito presente) */
        if (this.isUsaElimina()) {
            switch (this.getTipoBottoni()) {
                case creazione:
                    super.addBottone(Azione.ELIMINA_RECORD);
                    break;
                case aggiunta:
                    super.addBottone(Azione.RIMUOVI_RECORD);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
        }// fine del blocco if

        /* ricerca */
        if (this.isUsaRicerca()) {
            super.addBottone(Azione.RICERCA);
        }// fine del blocco if

        /* proietta */
        if (this.isUsaProietta()) {
            super.addBottone(Azione.PROIETTA);
        }// fine del blocco if

        /* selezione dei record (di solito presente) */
        if (this.isUsaSelezione()) {
            super.addBottone(Azione.CARICA_TUTTI);
            super.addBottone(Azione.SOLO_SELEZIONATI);
            super.addBottone(Azione.NASCONDE_SELEZIONATI);
        } /* fine del blocco if */

        /* preferito */
        if (this.isUsaPreferito()) {
            super.addBottone(Azione.PREFERITO);
        }// fine del blocco if

        /* frecce di spostamento su e giu dei record */
        if (this.isUsaFrecce()) {
            super.addBottone(Azione.RIGA_SU);
            super.addBottone(Azione.RIGA_GIU);
        } /* fine del blocco if */

        /* stampa della lista */
        if (this.isUsaStampa()) {
            super.addBottone(Azione.STAMPA);
        }// fine del blocco if

        /* aggiunge un separatore di modo che i comandi
         * aggiunti successivamente (quelli specifici) vengano separati
         * da quelli standard */
        this.addSeparator();
    }



    /**
     * Determina se è usato il bottone Nuovo Record nella lista
     * <p/>
     *
     * @return true se è usato
     */
    public boolean isUsaNuovo() {
        return isUsaNuovo;
    }


    public void setUsaNuovo(boolean usaNuovo) {
        isUsaNuovo = usaNuovo;
    }


    private boolean isUsaDuplica() {
        return isUsaDuplica;
    }


    public void setUsaDuplica(boolean usaDuplica) {
        isUsaDuplica = usaDuplica;
    }


    private boolean isUsaModifica() {
        return isUsaModifica;
    }


    public void setUsaModifica(boolean usaModifica) {
        isUsaModifica = usaModifica;
    }


    private boolean isUsaElimina() {
        return isUsaElimina;
    }


    public void setUsaElimina(boolean usaElimina) {
        isUsaElimina = usaElimina;
    }


    private boolean isUsaSelezione() {
        return isUsaSelezione;
    }


    public void setUsaSelezione(boolean usaSelezione) {
        isUsaSelezione = usaSelezione;
    }


    /**
     * Determina se la toolbar contiene i comandi per
     * l'ordinamento manuale sul campo Ordine
     * <p/>
     *
     * @return true se e' ordinabile manualmente
     */
    public boolean isUsaFrecce() {
        return isUsaFrecce;
    }


    public void setUsaFrecce(boolean usaFrecce) {
        isUsaFrecce = usaFrecce;
    }


    private boolean isUsaStampa() {
        return isUsaStampa;
    }


    public void setUsaStampa(boolean usaStampa) {
        isUsaStampa = usaStampa;
    }


    private boolean isUsaRicerca() {
        return isUsaRicerca;
    }


    /**
     * Abilita l'uso del pulsante Ricerca.
     * <p/>
     *
     * @param flag per abilitare il pulsante
     */
    public void setUsaRicerca(boolean flag) {
        isUsaRicerca = flag;
    }


    private boolean isUsaProietta() {
        return isUsaProietta;
    }


    /**
     * Abilita l'uso del pulsante Proietta.
     * <p/>
     *
     * @param flag per abilitare il pulsante
     */
    public void setUsaProietta(boolean flag) {
        isUsaProietta = flag;
    }


    /**
     * Controlla l'uso del pulsante Preferito nella lista
     * <p/>
     *
     * @return true se usa il pulsante Preferito
     */
    public boolean isUsaPreferito() {
        return isUsaPreferito;
    }


    /**
     * Abilita l'uso del pulsante Preferito nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaPreferito(boolean flag) {
        isUsaPreferito = flag;
    }


    private Lista.Bottoni getTipoBottoni() {
        return tipoBottoni;
    }


    /**
     * Determina il tipo di bottoni nuovo/elimina della lista
     * <p/>
     * Usa la coppia nuovo/elimina oppure la coppia aggiungi/rimuovi <br>
     *
     * @param tipoBottoni nuovo/elimina oppure aggiungi/rimuovi
     */
    @Override
    public void setTipoBottoni(Lista.Bottoni tipoBottoni) {
        this.tipoBottoni = tipoBottoni;
    }

}// fine della classe
