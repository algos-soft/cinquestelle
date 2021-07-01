/**
 * Title:     ListaModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-set-2004
 */
package it.algos.base.lista;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.Tavola;
import it.algos.base.vista.Vista;

import javax.swing.table.AbstractTableModel;

/**
 * Modello dati di una JTable.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-set-2004 ore 10.52.01
 */

public class TavolaModello extends AbstractTableModel {


    /**
     * tavola di riferimento
     */
    private Tavola tavola;

    /**
     * flag globale di modificabilità della tavola (editing in lista)
     */
    private boolean editabile = true;

    /**
     * Vista di riferimento
     */
    private Vista vista = null;


    /**
     * Contenitore effettivo dei dati
     */
    private Dati dati = null;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param vista di riferimento
     */
    public TavolaModello(Vista vista) {
        /* rimanda al costruttore della superclasse */
        this(null, vista);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param tavola di riferimento
     * @param vista di riferimento
     */
    public TavolaModello(Tavola tavola, Vista vista) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setTavola(tavola);
            this.setVista(vista);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice
            vista = this.getVista();
            if (vista != null) {
                vista.inizializza();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * getColumnName, from TableModel.
     * <p/>
     * I titoli vengono gestiti direttamente dalla Vista <br>
     *
     * @param colonna indice della colonna di cui si vuole il nome
     *
     * @return nome della colonna specificata
     */
    public String getColumnName(int colonna) {
        /* variabili e costanti locali di lavoro */
        String nome = "";

        try {    // prova ad eseguire il codice
            if (this.getVista() != null) {
                nome = this.getVista().getColumnName(colonna);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    } /* fine del metodo */


    /**
     * getColumnClass, from TableModel.
     * <p/>
     * La classe di una colonna viene gestita direttamente dalla Vista <br>
     *
     * @param colonna indice della colonna di cui si vuole l'oggetto
     *
     * @return classe dei dati della colonna specificata
     */
    public Class getColumnClass(int colonna) {
        /* variabili e costanti locali di lavoro */
        Class unaClasse = null;

        try {    // prova ad eseguire il codice
            if (this.getVista() != null) {
                unaClasse = this.getVista().getColumnClass(colonna);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unaClasse;
    } /* fine del metodo */


    /**
     * Ritorna il nome di una colonna visibile.
     * <p/>
     * I titoli vengono gestiti direttamente dalla Vista <br>
     *
     * @param colonna indice della colonna visibile
     *
     * @return nome della colonna specificata
     */
    public String getVisibleColumnName(int colonna) {
        /* variabili e costanti locali di lavoro */
        String nome = "";

        try {    // prova ad eseguire il codice
            if (this.getVista() != null) {
                nome = this.getVista().getVisibleColumnName(colonna);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    } /* fine del metodo */


    /**
     * Assegna il voce a una colonna visibile
     *
     * @param colonna indice della colonna visibile (0 per la prima)
     * @param nome nome della colonna
     */
    public void setVisibleColumnName(int colonna, String nome) {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try {    // prova ad eseguire il codice
            vista = this.getVista();
            if (vista != null) {
                vista.setVisibleColumnName(colonna, nome);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il numero di righe del Modello.
     * <p/>
     * Se il Modello ha un oggetto dati, rimanda all'oggetto dati.
     * Se il Modello non ha un oggetto dati, ritorna zero.
     *
     * @return il numero di righe
     */
    public int getRowCount() {
        /* variabili e costanti locali di lavoro */
        int numero = 0;
        Dati dati = null;

        try { // prova ad eseguire il codice
            dati = this.getDati();
            if (dati != null) {
                numero = dati.getRowCount();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return numero;
    } /* fine del metodo */


    /**
     * Ritorna il numero di colonne del Modello.
     * <p/>
     * Il numero di colonne viene gestito direttamente dalla Vista <br>
     *
     * @return il numero di colonne
     */
    public int getColumnCount() {
        /* variabili e costanti locali di lavoro */
        int numero = 0;
        Vista vista = null;

        try {    // prova ad eseguire il codice
            vista = this.getVista();
            if (vista != null) {
                numero = vista.getColumnCount();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    /**
     * getValueAt, from TableModel
     *
     * @param riga numero di riga dell'oggetto richiesto
     * @param colonna numero di colonna dell'oggetto richiesto
     *
     * @return l'oggetto della cella specificata
     */
    public Object getValueAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Dati dati;
        int quanteColonne;

        try { // prova ad eseguire il codice

            dati = this.getDati();
            if (dati != null) {
                quanteColonne = dati.getColumnCount();
                if (colonna < quanteColonne) {
                    valore = dati.getValueAt(riga, colonna);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    } /* fine del metodo */


    /**
     * Registra un valore nei dati.
     * <p/>
     *
     * @param aValue il valore memoria da registrare
     * @param rowIndex the row whose value is to be changed
     * @param columnIndex the column whose value is to be changed
     *
     * @see #getValueAt
     * @see #isCellEditable
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        int codice;
        Campo campo;
        Modulo modulo;
        Object valore;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera la Vista */
            vista = this.getVista();

            /* identifica il campo da scrivere: usa il campo originale della vista,
             * e in mancanza di questo il campo della vista */
            campo = vista.getCampoOriginale(columnIndex);
            if (campo == null) {
                campo = vista.getCampo(columnIndex);
            }// fine del blocco if

            /* controlla che il campo non sia nullo */
            if (campo == null) {
                continua = false;
            }// fine del blocco if

            /* inserisce il valore nel campo */
            if (continua) {
                campo.setValore(aValue);
            }// fine del blocco if

            /* controlla che il campo sia valido */
            if (continua) {
                if (!campo.isValido()) {
                    new MessaggioAvviso("Il campo non è valido");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se il campo è obbligatorio controlla che non sia vuoto */
            if (continua) {
                if (campo.isObbligatorio()) {
                    if (campo.isVuoto()) {
                        new MessaggioAvviso("Questo campo è obbligatorio.");
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* scrive il valore nel database */
            if (continua) {

                /* recupera il codice del record */
                codice = this.getChiave(rowIndex);

                /* recupera dal campo il valore memoria da registrare */
                valore = campo.getValore();

                /* esegue una query sul modulo del campo da scrivere */
                modulo = campo.getModulo();
                modulo.query().registraRecordValore(codice, campo, valore);

                /* aggiorna la lista col valore video */
                this.getTavola().getLista().setValueAt(aValue, codice, campo);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * isCellEditable, from TableModel
     *
     * @param riga numero di riga di cui si chiede la modificabilita'
     * @param colonna numero di colonna di cui si chiede la modificabilita'
     *
     * @return vero se la cella e' modificabile, falso altrimenti
     */
    public boolean isCellEditable(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        boolean modificabile = false;
        Campo campo;

        if (this.isModificabile()) {
            campo = this.getVista().getCampoOriginale(colonna);
            modificabile = campo.isModificabileLista();
        }// fine del blocco if-else

        /* valore di ritorno */
        return modificabile;
    }


    /**
     * Restituisce il codice chiave di una riga.
     *
     * @param riga indice della riga (0 per la prima)
     *
     * @return il codice chiave della riga (-1 se non valido)
     */
    public int getChiave(int riga) {
        /* variabili e costanti locali di lavoro */
        int chiave = -1;
        int colonnaChiave;
        Object valore;

        try { // prova ad eseguire il codice

            colonnaChiave = this.getColonnaChiavi();
            if (riga < this.getRowCount()) {
                valore = this.getValueAt(riga, colonnaChiave);
                if (valore instanceof Integer) {
                    chiave = (Integer)valore;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Restituisce il campo corrispondente a una colonna.
     *
     * @param colonna indice della colonna (0 per la prima)
     *
     * @return il campo corrispondente alla colonna
     */
    public Campo getCampo(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Dati dati;

        try { // prova ad eseguire il codice
            dati = this.getDati();
            if (dati != null) {
                campo = dati.getCampo(colonna);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Restituisce la posizione nella lista di un codice chiave.
     *
     * @param chiave il codice chiave da cercare
     *
     * @return la posizione della riga (0 per la prima, -1 se non trovata)
     */
    public int getPosizione(int chiave) {
        /* variabili e costanti locali di lavoro */
        int pos = 0;
        Dati dati = null;

        try { // prova ad eseguire il codice
            dati = this.getDati();
            if (dati != null) {
                pos = dati.getRigaChiave(chiave);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pos;
    }


    /**
     * Controlla che un valore sia della classe appropriata
     * per una data colonna.
     * <p/>
     * Invocato dai metodi getValueAt delle sottoclassi.
     * Se il valore non e' della classe appropriata
     * ritorna null.
     * <p/>
     * Serve a non mandare in errore la JTable se arrivano
     * valori di classe diversa dal previsto.
     *
     * @param val oggetto valore da controllare
     * @param colonna numero di colonna del modello a fronte
     * della quale controllare (0 per la prima)
     *
     * @return l'oggetto valore originale o null se non appropriato
     */
    protected Object checkClasseValore(Object val, int colonna) {
        /* variabili e costanti locali di lavoro */
        Object unValore = null;
        Class classeColonna = null;
        Class classeValore = null;

        try { // prova ad eseguire il codice

            unValore = val;

            if (unValore != null) {
                /* recupera la classe del valore */
                classeValore = unValore.getClass();
                /* recupera la classe della colonna */
                classeColonna = this.getColumnClass(colonna);
                /* confronta */
                if (classeColonna != classeValore) {
                    unValore = null;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unValore;
    } /* fine del metodo */


    /**
     * Restituisce la posizione relativa di una chiave all'interno dei dati.
     *
     * @param codice record di riferimento
     *
     * @return la posizione relativa della chiave (vedi codifica in Dati)
     */
    public int posizioneRelativa(int codice) {
        /* valore di ritorno */
        return this.getDati().posizioneRelativa(codice);
    }


    /**
     * Restituisce la codifica di posizione del record corrente nell'ambito del result set.
     *
     * @return la posizione (vedi codifica in Dati)
     */
    public int posizione() {
        /* valore di ritorno */
        return this.getDati().posizione();
    }


    /**
     * Restituisce il codice-chiave di un record relativo ad un altro record.
     *
     * @param codice record di riferimento
     * @param spostamento codifica della posizione (vedi codifica in PortaleScheda)
     *
     * @return codice del record nella posizione relativa richiesta
     */
    public int getCodiceRelativo(int codice, Dati.Spostamento spostamento) {
        /* valore di ritorno */
        return this.getDati().getCodiceRelativo(codice, spostamento);
    }


    /**
     * Ritorna la posizione della colonna contenente le chiavi.
     * <p/>
     *
     * @return la posizione della colonna delle chiavi (0 per la prima)
     */
    public int getColonnaChiavi() {
        /* variabili e costanti locali di lavoro */
        int colonna = 0;
        Dati dati;

        try { // prova ad eseguire il codice
            dati = this.getDati();
            if (dati != null) {
                colonna = dati.getColonnaChiavi();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return colonna;
    }


    private Tavola getTavola() {
        return tavola;
    }


    private void setTavola(Tavola tavola) {
        this.tavola = tavola;
    }


    public Vista getVista() {
        return vista;
    }


    private void setVista(Vista vista) {
        this.vista = vista;
    }


    public Dati getDati() {
        return dati;
    }


    /**
     * Assegna un oggetto Dati al modello.
     * <p/>
     *
     * @param dati oggetto Dati da assegnare
     */
    public void setDati(Dati dati) {
        /* variabili e costanti locali di lavoro */
        Dati datiCorrenti;

        try { // prova ad eseguire il codice

            /* chiude l'oggetto dati eventualmente in uso
             * se diverso da quello che si sta assegnando */
            datiCorrenti = this.getDati();
            if (datiCorrenti != null) {
                if (!datiCorrenti.equals(dati)) {
                    datiCorrenti.close();
                }// fine del blocco if
            }// fine del blocco if

            /* assegna l'oggetto */
            this.dati = dati;

            /* notifica al modello il cambiamento del contenuto dei dati */
            this.fireTableDataChanged();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private boolean isModificabile() {
        return editabile;
    }


    /**
     * Controlla la modificabilità globale della tavola (editing in lista)
     */
    public void setModificabile(boolean modificabile) {
        editabile = modificabile;
    }

}// fine della classe
