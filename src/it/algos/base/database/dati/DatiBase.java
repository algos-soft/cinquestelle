/**
 * Title:     Dati
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.CampoValore;

import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Contenitore generico di dati.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 15.49.16
 */
public abstract class DatiBase implements Dati {

    /**
     * Lista ordinata di informazioni sulle colonne di questo oggetto Dati.
     * E' sincronizzata con le colonne contenute nell'oggetto.
     */
    private ArrayList<InfoColonna> infoColonne = null;

    /**
     * Mappa campi -> infoColonna
     * Chiave: chiave del campo
     * Valore: oggetto InfoColonna corrispondente
     * Serve per velocizzare la ricerca della colonna
     * passando un campo
     */
    private HashMap<String, InfoColonna> mappaCampi = null;

    /**
     * posizione della colonna delle chiavi (0 per la prima).
     */
    private int colonnaChiavi = 0;


    /**
     * Costruttore completo.
     * <p/>
     */
    public DatiBase() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setInfoColonne(new ArrayList<InfoColonna>());
        this.setMappaCampi(new HashMap<String, InfoColonna>());

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola la lista di informazioni sulle colonne */
        this.regolaInfoColonne();
    }// fine del metodo inizia


    public void addTableModelListener(TableModelListener l) {
    }


    public void removeTableModelListener(TableModelListener l) {
    }


    public Class getColumnClass(int columnIndex) {
        /* variabili e costanti locali di lavoro */
        Class classe = null;
        TipoDati tipo;

        try { // prova ad eseguire il codice
            tipo = this.getTipoDatiColonna(columnIndex);
            if (tipo != null) {
                classe = tipo.getClasseBl();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return classe;
    }


    /**
     * Ritorna il nome della colonna
     * <p/>
     * Usato dalla JTable per visualizzare i titoli.
     * Usa il voce colonna del campo Lista
     * In mancanza, usa il nome interno del campo
     *
     * @param columnIndex l'indice della colonna
     *
     * @return il nome della colonna
     */
    public String getColumnName(int columnIndex) {
        /* variabili e costanti locali di lavoro */
        String nome = "";
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoColonna(columnIndex);
            if (campo != null) {
                nome = campo.getCampoLista().getTitoloColonna();
                if (!Lib.Testo.isValida(nome)) {
                    nome = campo.getNomeInterno();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


    /**
     * Regola le informazioni sulle colonne in base alla query.
     * <p/>
     * Crea un oggetto InfoColonna per ogni campo e lo aggiunge alla collezione
     * Aggiunge una entry alla mappa campi
     * Identifica la posizione della colonna chiave
     */
    protected void regolaInfoColonne() {
    }


    /**
     * Ritorna il tipo dati corrispondente a una data colonna.
     * <p/>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il tipo dati della colonna
     */
    private TipoDati getTipoDatiColonna(int colonna) {
        /* variabili e costanti locali di lavoro */
        TipoDati tipoDati = null;
        ArrayList<InfoColonna> lista;
        InfoColonna ic = null;

        try {    // prova ad eseguire il codice
            lista = this.getInfoColonne();
            if (lista != null) {
                if (colonna < lista.size()) {
                    ic = lista.get(colonna);
                    if (ic != null) {
                        tipoDati = ic.getTipoDati();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipoDati;
    }


    /**
     * Ritorna il campo corrispondente a una colonna.
     * <p/>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il campo
     */
    protected Campo getCampoColonna(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        InfoColonna ic = null;

        try {    // prova ad eseguire il codice
            ic = this.getInfoColonna(colonna);
            if (ic != null) {
                campo = ic.getCampo();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Object getValueAt(int riga, Campo campo) {
        return this.getValueAt(riga, this.getIndiceCampo(campo));
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Object getValueAt(int riga, String nome) {
        return this.getValueAt(riga, this.getIndiceCampo(nome));
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Object getValueAt(Campo campo) {
        return this.getValueAt(0, campo);
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Object getValueAt(String nome) {
        return this.getValueAt(0, nome);
    }


    /**
     * Ritorna il valore intero di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore dell'intero nella cella specificata
     */
    public int getIntAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        int intero = 0;
        Object valore = null;

        try { // prova ad eseguire il codice
            valore = this.getValueAt(riga, colonna);
            intero = Libreria.objToInt(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return intero;
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public int getIntAt(int riga, Campo campo) {
        return this.getIntAt(riga, this.getIndiceCampo(campo));
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public int getIntAt(int riga, String nome) {
        return this.getIntAt(riga, this.getIndiceCampo(nome));
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public int getIntAt(Campo campo) {
        return this.getIntAt(0, campo);
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public int getIntAt(String nome) {
        return this.getIntAt(0, nome);
    }


    /**
     * Ritorna il valore double di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore dell'intero nella cella specificata
     */
    public double getDoubleAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        double doppio = 0;
        Object valore;

        try { // prova ad eseguire il codice
            valore = this.getValueAt(riga, colonna);
            doppio = Libreria.getDouble(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return doppio;
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public double getDoubleAt(int riga, Campo campo) {
        return this.getDoubleAt(riga, this.getIndiceCampo(campo));
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public double getDoubleAt(int riga, String nome) {
        return this.getDoubleAt(riga, this.getIndiceCampo(nome));
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public double getDoubleAt(Campo campo) {
        return this.getDoubleAt(0, campo);
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public double getDoubleAt(String nome) {
        return this.getDoubleAt(0, nome);
    }


    /**
     * Ritorna il valore data di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore data nella cella specificata
     */
    public Date getDataAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Object valore;

        try { // prova ad eseguire il codice
            valore = this.getValueAt(riga, colonna);
            data = Libreria.getDate(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Date getDataAt(int riga, Campo campo) {
        return this.getDataAt(riga, this.getIndiceCampo(campo));
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Date getDataAt(int riga, String nome) {
        return this.getDataAt(riga, this.getIndiceCampo(nome));
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Date getDataAt(Campo campo) {
        return this.getDataAt(0, campo);
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public Date getDataAt(String nome) {
        return this.getDataAt(0, nome);
    }


    /**
     * Ritorna il valore stringa di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore della stringa nella cella specificata
     */
    public String getStringAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Object valore;

        try { // prova ad eseguire il codice
            valore = this.getValueAt(riga, colonna);
            stringa = Lib.Testo.getStringa(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public String getStringAt(int riga, Campo campo) {
        return this.getStringAt(riga, this.getIndiceCampo(campo));
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public String getStringAt(int riga, String nome) {
        return this.getStringAt(riga, this.getIndiceCampo(nome));
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public String getStringAt(Campo campo) {
        return this.getStringAt(0, campo);
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public String getStringAt(String nome) {
        return this.getStringAt(0, nome);
    }


    /**
     * Ritorna il valore booleano di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore del booleano nella cella specificata
     */
    public boolean getBoolAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        boolean booleano = false;
        Object valore;

        try { // prova ad eseguire il codice
            valore = this.getValueAt(riga, colonna);
            booleano = Libreria.objToBool(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return booleano;
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public boolean getBoolAt(int riga, Campo campo) {
        return this.getBoolAt(riga, this.getIndiceCampo(campo));
    }


    /**
     * Ritorna il valore di una cella.
     * <p/>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public boolean getBoolAt(int riga, String nome) {
        return this.getBoolAt(riga, this.getIndiceCampo(nome));
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public boolean getBoolAt(Campo campo) {
        return this.getBoolAt(0, campo);
    }


    /**
     * Ritorna il valore di una cella della prima riga.
     * <p/>
     *
     * @param nome del campo corrispondente alla cella richiesta
     *
     * @return il valore nella cella specificata
     */
    public boolean getBoolAt(String nome) {
        return this.getBoolAt(0, nome);
    }


    /**
     * Assegna un valore a una cella.
     * <p/>
     *
     * @param riga l'indice della riga, 0 per la prima
     * @param campo corrispondente alla colonna
     * @param valore da assegnare
     */
    public void setValueAt(int riga, Campo campo, Object valore) {
        this.setValueAt(valore, riga, this.getIndiceCampo(campo));
    }


    /**
     * Restituisce i valori per una singola colonna dei dati.
     * <p/>
     *
     * @param indice l'indice della colonna (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public ArrayList getValoriColonna(int indice) {
        /* variabili e costanti locali di lavoro */
        ArrayList valori = null;
        Object valore = null;

        try { // prova ad eseguire il codice
            valori = new ArrayList();
            for (int k = 0; k < this.getRowCount(); k++) {
                valore = this.getValueAt(k, indice);
                valori.add(valore);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;

    } /* fine del metodo */


    /**
     * Restituisce la colonna dei valori per una singolo campo.
     * <p/>
     *
     * @param campo il campo da cercare
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public ArrayList getValoriColonna(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int indice = 0;
        ArrayList valori = null;

        try { // prova ad eseguire il codice
            indice = this.getIndiceCampo(campo);
            if (indice != -1) {
                valori = this.getValoriColonna(indice);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;

    } /* fine del metodo */


    /**
     * Restituisce i valori per una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public ArrayList<Object> getValoriRiga(int indice) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> valori = null;
        Object valore;

        try { // prova ad eseguire il codice
            valori = new ArrayList<Object>();
            for (int k = 0; k < this.getColumnCount(); k++) {
                valore = this.getValueAt(indice, k);
                valori.add(valore);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;

    } /* fine del metodo */


    /**
     * Restituisce i CampiValore per una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti CampoValore
     */
    public ArrayList<CampoValore> getCampiValore(int indice) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> campi = null;
        Object valore;
        Campo campo;
        CampoValore cv;

        try { // prova ad eseguire il codice

            campi = new ArrayList<CampoValore>();
            for (int k = 0; k < this.getColumnCount(); k++) {
                campo = this.getCampoColonna(k);
                valore = this.getValueAt(indice, k);
                cv = new CampoValore(campo, valore);
                campi.add(cv);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campi;

    } /* fine del metodo */


    /**
     * Restituisce i valori stringa per una riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     *
     * @return una ArrayList contenente gli oggetti stringa
     */
    public ArrayList<String> getStringheRiga(int indice) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> valori = null;
        String valore;

        try { // prova ad eseguire il codice
            valori = new ArrayList<String>();
            for (int k = 0; k < this.getColumnCount(); k++) {
                valore = this.getStringAt(indice, k);
                valori.add(valore);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    } /* fine del metodo */


    /**
     * Restituisce la mappa di un record.
     * <p/>
     *
     * @return una ArrayList contenente gli oggetti stringa
     */
    public LinkedHashMap<String, String> getStrRecord() {
        /* invoca il metodo delegato della classe */
        return this.getStrRecord(0);

    } /* fine del metodo */


    /**
     * Restituisce la mappa di un record.
     * <p/>
     *
     * @param riga del result set
     *
     * @return una ArrayList contenente gli oggetti stringa
     */
    public LinkedHashMap<String, String> getStrRecord(int riga) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, String> mappa = null;
        String chiave;
        String valore;

        try { // prova ad eseguire il codice
            mappa = new LinkedHashMap<String, String>();

            for (int k = 0; k < this.getColumnCount(); k++) {
                chiave = this.getCampoColonna(k).getNomeInterno();
                valore = this.getStringAt(riga, k);
                mappa.put(chiave, valore);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mappa;
    } /* fine del metodo */


    /**
     * Restituisce una stringa di una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     * @param delim il carattere delimitatore di campo
     *
     * @return una stringa per la singola riga
     */
    public String getStringaRiga(int indice, char delim) {
        /* variabili e costanti locali di lavoro */
        String riga = "";
        ArrayList<String> valori;
        StringBuffer buffer;

        try { // prova ad eseguire il codice
            valori = this.getStringheRiga(indice);

            buffer = new StringBuffer();

            if (valori != null) {
                for (int k = 0; k < valori.size(); k++) {
                    buffer.append(valori.get(k));
                    if (k <= valori.size()) {
                        buffer.append(delim);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            riga = buffer.toString();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riga;
    } /* fine del metodo */


    /**
     * Restituisce una stringa di una singola riga dei dati.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     * @param delim la stringa delimitatore di campo
     *
     * @return una stringa per la singola riga
     */
    public String getStringaRiga(int indice, String delim) {
        /* variabili e costanti locali di lavoro */
        String riga = "";
        ArrayList<String> valori;
        StringBuffer buffer;

        try { // prova ad eseguire il codice
            valori = this.getStringheRiga(indice);

            buffer = new StringBuffer();

            if (valori != null) {
                for (int k = 0; k < valori.size(); k++) {
                    buffer.append(valori.get(k));
                    if (k <= valori.size()) {
                        buffer.append(delim);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            riga = buffer.toString();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riga;
    } /* fine del metodo */


    /**
     * Restituisce l'indice della colonna relativa a un campo nei dati.
     * <p/>
     *
     * @param campo il campo da cercare
     *
     * @return l'indice della posizione della colonna nei dati
     *         0 per la prima, -1 se non trovata
     */
    private int getIndiceCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int indice = -1;
        String chiaveRicerca = null;
        InfoColonna ic = null;

        try { // prova ad eseguire il codice
            chiaveRicerca = campo.getChiaveCampo();
            ic = (InfoColonna)this.getMappaCampi().get(chiaveRicerca);
            if (ic != null) {
                indice = ic.getPosizione();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return indice;
    }


    /**
     * Restituisce l'indice della colonna relativa a un campo nei dati.
     * <p/>
     *
     * @param nome del campo il campo da cercare
     *
     * @return l'indice della posizione della colonna nei dati
     *         0 per la prima, -1 se non trovata
     */
    private int getIndiceCampo(String nome) {
        /* variabili e costanti locali di lavoro */
        int indice = -1;
        Modulo mod;
        Campo campo=null;

        try { // prova ad eseguire il codice

            mod = this.getModulo();
            if (mod != null) {
                campo = mod.getCampo(nome);
            } else {
                InfoColonna info = this.getMappaCampi().get(nome.toLowerCase());
                if (info!=null) {
                    campo = info.getCampo();
                }// fine del blocco if
            }// fine del blocco if-else

            if (campo != null) {
                indice = this.getIndiceCampo(campo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return indice;
    }


    /**
     * Resituisce la posizione assoluta di una chiave all'interno dei dati.
     * <p/>
     *
     * @param codice il codice del record del quale individuare la posizione
     *
     * @return la posizione assoluta della chiave (0 per la prima, -1 se non trovato)
     */
    public int getRigaChiave(int codice) {
        /* valore di ritorno */
        return 0;
    }


    /**
     * Resituisce la posizione di una chiave all'interno dei dati.
     *
     * @param codice
     *
     * @return la posizione della chiave (vedi codifica in Dati)
     */
    public int posizioneRelativa(int codice) {
        return 0;
    }


    /**
     * Restituisce la codifica di posizione del record corrente nell'ambito del result set.
     *
     * @return la posizione (vedi codifica in Dati)
     */
    public int posizione() {
        return 0;
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
        return 0;
    }


    /**
     * Restituisce il campo corrispondente a una colonna.
     * <p/>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il campo corrispondente
     */
    public Campo getCampo(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        ArrayList<InfoColonna> infoColonne;

        try {    // prova ad eseguire il codice
            infoColonne = this.getInfoColonne();
            for (InfoColonna info : infoColonne) {
                if (info.getPosizione() == colonna) {
                    campo = info.getCampo();
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Restituisce l'elenco ordinato dei campi corrispondenti alle colonne.
     * <p/>
     *
     * @return l'elenco ordinato dei campi corrispondenti alle colonne
     */
    public ArrayList<Campo> getCampi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = new ArrayList<Campo>();
        Campo campo;
        ArrayList<InfoColonna> infoColonne;

        try {    // prova ad eseguire il codice
            infoColonne = this.getInfoColonne();
            for (InfoColonna info : infoColonne) {
                campo = info.getCampo();
                campi.add(campo);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Restituisce la colonna corrispondente a un campo.
     * <p/>
     *
     * @param campo il campo da cercare
     *
     * @return l'indice della colonna corrispondente
     *         (0 per la prima, -1 se non trovata)
     */
    public int getColonna(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int colonna = -1;
        ArrayList<InfoColonna> infoColonne;

        try {    // prova ad eseguire il codice
            infoColonne = this.getInfoColonne();
            for (InfoColonna info : infoColonne) {
                if (info.getCampo().equals(campo)) {
                    colonna = info.getPosizione();
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return colonna;
    }


    /**
     * Ritorna il valore di una cella.<p>
     * Il valore e' rappresentato a livello Archivio.
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore dell'oggetto nella cella specificata
     */
    public Object getDbValueAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il modulo di riferimento della query che ha originato questi dati.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return il modulo della query
     */
    protected Modulo getModulo() {
        return null;
    }


    /**
     * Recupera l'oggetto di informazioni su una colonna.
     * <p/>
     *
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return l'oggetto InfoColonna
     */
    protected InfoColonna getInfoColonna(int colonna) {
        /* variabili e costanti locali di lavoro */
        InfoColonna info = null;
        ArrayList<InfoColonna> infoColonne;

        try { // prova ad eseguire il codice
            infoColonne = this.getInfoColonne();
            if (infoColonne != null) {
                if (colonna < infoColonne.size()) {
                    info = infoColonne.get(colonna);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return info;
    }


    protected ArrayList<InfoColonna> getInfoColonne() {
        return infoColonne;
    }


    private void setInfoColonne(ArrayList<InfoColonna> infoColonne) {
        this.infoColonne = infoColonne;
    }


    protected HashMap<String, InfoColonna> getMappaCampi() {
        return mappaCampi;
    }


    private void setMappaCampi(HashMap<String, InfoColonna> mappaCampi) {
        this.mappaCampi = mappaCampi;
    }


    /**
     * Ritorna la posizione della colonna contenente le chiavi.
     * <p/>
     *
     * @return la posizione della colonna delle chiavi (0 per la prima)
     */
    public int getColonnaChiavi() {
        return colonnaChiavi;
    }


    protected void setColonnaChiavi(int colonnaChiavi) {
        this.colonnaChiavi = colonnaChiavi;
    }


    /**
     * Chiude l'oggetto dati e libera le risorse allocate.<p>
     * <p/>
     */
    public void close() {
    }


}// fine della classe
