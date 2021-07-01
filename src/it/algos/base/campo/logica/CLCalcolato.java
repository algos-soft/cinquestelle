/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      6-apr-2006
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.form.Form;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;

import java.util.ArrayList;
import java.util.Date;

/**
 * Logica di un campo calcolato.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Mantiene una lista di campi su cui eseguire dei calcoli </li>
 * <li> Tipicamente i campi sono uno o due </li>
 * <li> Mantiene una lista di campi su cui eseguire dei calcoli </li>
 * <li> Mantiene una tipologia di operazioni da eseguire sui campi,
 * stabilita da una Enumeration </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 6-apr-2006 ore 14.51.57
 */
public class CLCalcolato extends CLBase {

    /**
     * campi di riferimento
     */
    private ArrayList<String> campi;

    /**
     * tipologia operazione
     */
    private CampoLogica.Calcolo operazione;

    /**
     * flag per indicare se il campo è calcolato
     */
    private boolean calcolato = false;


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLCalcolato(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola la variabile locale di questa sottoclasse */
        this.setCalcolato(true);
    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice

            /* crea l'azione da associare al campo osservato */
            this.setAzione(new AzioneCalcolata());

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'azione.
     * <p/>
     * Metodo invocato dal ciclo inizializza del Form <br>
     * Associa l'azione al componente dei campi osservati <br>
     */
    public void regolaAzione() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        BaseListener azione;
        Campo campoOsservato;

        try {    // prova ad eseguire il codice
            /* recupera l'azione interna */
            azione = this.getAzione();
            continua = (azione != null);

            /* recupera i nomi dei campi interessati */
            /* recupera i campi */
            /* aggiunge l'azione */
            if (continua) {
                for (String nome : this.getCampiOsservati()) {
                    campoOsservato = this.getCampo(nome);
                    if (campoOsservato != null) {
                        campoOsservato.addListener(azione);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Esegue l'operazione prevista.
     */
    public void esegui() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Object valore = null;
        CampoLogica.Calcolo operazione;
        Calcolo.Tipo tipo = null;

        try { // prova ad eseguire il codice

            /* recupero operazione */
            operazione = this.getOperazione();
            continua = operazione != null;

            /* recupero tipologia di operazione */
            if (continua) {
                tipo = operazione.getTipo();
                continua = tipo != null;
            }// fine del blocco if

            /* switch tipo operazione */
            if (continua) {
                switch (tipo) {
                    case intero:
                        valore = this.esegueNumeri();
                        valore = Libreria.getInt(valore);
                        break;
                    case reale:
                    case valuta:
                        valore = this.esegueNumeri();
                        break;
                    case testo:
                        valore = this.esegueTesto();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            /* registra il valore ottenuto */
            if (continua) {
                this.getCampoParente().setValore(valore);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esegue l'operazione prevista.
     * <p/>
     * Operazioni eseguite su numeri <br>
     *
     * @return valore numerico (intero, double) restituito dal calcolo
     */
    private Number esegueNumeri() {
        /* variabili e costanti locali di lavoro */
        Number valore = null;
        boolean continua;
        ArrayList<String> nomiCampi;
        Double valFinale = 0.0;
        CampoLogica.Calcolo operazione = null;
        CampoDati campoDati;
        int numDec;
        ArrayList<Object> valori = null;
        Object ogg;
        int intero;

        try { // prova ad eseguire il codice
            /* recupera la lista di campi interessati */
            nomiCampi = this.getCampiOsservati();

            /* controllo di congruità */
            continua = nomiCampi != null;

            /* ulteriore controllo di congruità */
            if (continua) {
                continua = nomiCampi.size() >= 1;
            }// fine del blocco if

            /* costruisce una lista di valori */
            if (continua) {
                valori = new ArrayList<Object>();
            }// fine del blocco if

            /* recupera i valori dei campi */
            if (continua) {
                valori = this.getValoriOsservati();
            }// fine del blocco if

//            /* primo valore */
//            primo = this.getCampo(nomiCampi.get(0));
//            continua = (primo != null);
//
//            if (continua) {
//                oggetto = primo.getValore();
//                continua = (oggetto != null);
//            }// fine del blocco if
//
//            if (continua) {
//                numero = (Number)oggetto;
//                valPrimo = numero.doubleValue();
//                continua = (valPrimo != null);
//            }// fine del blocco if
//
//            /* secondo valore */
//            if (continua) {
//                secondo = this.getCampo(nomiCampi.get(1));
//                continua = (secondo != null);
//            }// fine del blocco if
//
//            if (continua) {
//                oggetto = secondo.getValore();
//                continua = (oggetto != null);
//            }// fine del blocco if
//
//            if (continua) {
//                numero = (Number)oggetto;
//                valSecondo = numero.doubleValue();
//                continua = (valSecondo != null);
//            }// fine del blocco if

            /* operazione */
            if (continua) {
                operazione = this.getOperazione();
                continua = (operazione != null);
            }// fine del blocco if

            if (continua) {
                switch (operazione) {
                    case sommaIntero:
                        ogg = this.esegueOperazione(valori);
                        valore = Libreria.getInt(ogg);
                        continua = false;
                        break;
                    case sommaReale:
                    case sommaValuta:
                        valFinale = (Double) this.esegueOperazione(valori);
//                        /* traverso tutta la collezione */
//                        for (String nome : nomiCampi) {
//                            campo = this.getCampo(nome);
//                            oggetto = campo.getValore();
//                            numero = (Number)oggetto;
//                            valPrimo = numero.doubleValue();
//                            valFinale += valPrimo;
//                        } // fine del ciclo for-each
                        break;
                    case differenzaIntero:
                    case differenzaReale:
                    case differenzaValuta:
                        valFinale = (Double) this.esegueOperazione(valori);
//                        valFinale = valPrimo - valSecondo;
                        break;
                    case differenzaDate:
                        ogg = this.esegueOperazione(valori);
                        if (ogg instanceof Integer) {
                            intero = Libreria.getInt(ogg);
                            valFinale = Libreria.getDouble(intero);
                        }// fine del blocco if

                        break;
                    case prodottoIntero:
                    case prodottoReale:
                    case prodottoValuta:
                        valFinale = Libreria.getDouble(this.esegueOperazione(valori));
//                        valFinale = (Double)this.esegueOperazione(valori);
//                        valFinale = valPrimo * valSecondo;
                        break;
                    case inverso:
                        valFinale = (Double) this.esegueOperazione(valori);
                        break;
                    case sommatoriaReale:
                    case sommatoriaValuta:
                        valFinale = (Double) this.esegueOperazione(valori);
                        break;

                    case giornoDelMese:
                        ogg = this.esegueOperazione(valori);
                        if (ogg != null) {
                            if (ogg instanceof Integer) {
                                valFinale = Libreria.getDouble(ogg);
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case meseDellAnno:
                        ogg = this.esegueOperazione(valori);
                        if (ogg != null) {
                            if (ogg instanceof Integer) {
                                valFinale = Libreria.getDouble(ogg);
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case indiceGiornoDellAnno:
                        ogg = this.esegueOperazione(valori);
                        if (ogg != null) {
                            if (ogg instanceof Integer) {
                                valFinale = Libreria.getDouble(ogg);
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case anno:
                        ogg = this.esegueOperazione(valori);
                        if (ogg != null) {
                            if (ogg instanceof Integer) {
                                valFinale = Libreria.getDouble(ogg);
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            if (continua) {
                campoDati = this.getCampoParente().getCampoDati();
                numDec = campoDati.getNumDecimali();
                if ((valFinale != null) && (valFinale != 0)) {
                    valore = Lib.Mat.arrotonda(valFinale, numDec);
                } else {
                    valore = 0.0;
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Esegue l'operazione prevista.
     * <p/>
     * Operazioni eseguite su testo <br>
     *
     * @return valore stringa restituito dal calcolo
     */
    private String esegueTesto() {
        /* variabili e costanti locali di lavoro */
        String valore = "";
        boolean continua;
        ArrayList<String> nomiCampi;
        Campo campo;
        Campo primo;
        Object oggetto = null;
        String testo = "";
        CampoLogica.Calcolo operazione = null;
        String sep = " ";

        try { // prova ad eseguire il codice
            nomiCampi = getCampiOsservati();

            /* primo valore */
            primo = this.getCampo(nomiCampi.get(0));
            continua = (primo != null);

            if (continua) {
                oggetto = primo.getValore();
                continua = (oggetto != null);
            }// fine del blocco if

            if (continua) {
                testo = oggetto.toString();
                continua = Lib.Testo.isValida(testo);
            }// fine del blocco if

            /* operazione */
            if (continua) {
                operazione = this.getOperazione();
                continua = (operazione != null);
            }// fine del blocco if

            /* tipologia compatibile con operazioni tra testi */
            if (continua) {
                operazione = this.getOperazione();
                continua = (operazione.getTipo() == Calcolo.Tipo.testo);
            }// fine del blocco if

            if (continua) {
                switch (operazione) {
                    case copia:
                        valore = testo;
                        break;
                    case sommaTesto:
                        /* traverso tutta la collezione */
                        for (String nome : nomiCampi) {
                            campo = this.getCampo(nome);
                            oggetto = campo.getValore();
                            testo = oggetto.toString();
                            valore += testo + sep;
                        } // fine del ciclo for-each
                        valore = Lib.Testo.levaCoda(valore, sep);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Esegue l'operazione prevista.
     * <p/>
     * Riceve una lista di valori arbitrari (devono essere dello stesso tipo) <br>
     * Recupera dal campo il tipo di operazione da eseguire <br>
     * Esegue l'operazione su tutti i valori <br>
     * restituisce il valore risultante <br>
     *
     * @param valori in ingresso
     * @return risultato dell'operazione
     */
    public Object esegueOperazione(ArrayList<Object> valori) {
        /* variabili e costanti locali di lavoro */
        Object risultato = null;
        Object valore;
        Object valUno;
        Object valDue;
        int intVal;
        int intValProg = 0;
        int intValUno;
        int intValDue;
        double doubleVal = 0;
        double doubleValProg = 0;
        double doubleValUno;
        double doubleValDue;
        Date data1;
        Date data2;
        boolean continua;
        CampoLogica.Calcolo operazione;
        int numDec;
        Date data;

        try { // prova ad eseguire il codice

            /* tipologia di operazione */
            operazione = this.getOperazione();

            /* precisione del campo numerico */
            numDec = this.getCampoParente().getCampoDati().getNumDecimali();

            /* controllo di congruità */
            continua = valori != null;

            /* ulteriore controllo di congruità */
            /* ci dovrebbero essere almeno due valori */
            if (continua) {
                continua = valori.size() >= 1;
            }// fine del blocco if

            if (continua) {
                switch (operazione) {

                    case sommaIntero:
                        /* traverso tutta la collezione */
                        for (Object val : valori) {
                            if (val instanceof Integer) {
                                intVal = (Integer) val;
                                intValProg += intVal;
                            }// fine del blocco if
                        } // fine del ciclo for-each
                        risultato = intValProg;
                        break;

                    case sommaReale:

                    case sommaValuta:

                        /* traverso tutta la collezione */
                        for (Object val : valori) {
                            if (val instanceof Integer) {
                                intVal = (Integer) val;
                                doubleValProg += intVal;
                            }// fine del blocco if
                            if (val instanceof Double) {
                                doubleVal = (Double) val;
                                doubleValProg += doubleVal;
                            }// fine del blocco if
                        } // fine del ciclo for-each

                        /* arrotonda il valore in uscita alla precisione del campo*/
                        doubleValProg = Lib.Mat.arrotonda(doubleValProg, numDec);
                        risultato = doubleValProg;
                        break;

                    case differenzaIntero:
                        /* utilizzo solo i primi due valori dela lista */
                        if (valori.size() == 2) {
                            valUno = valori.get(0);
                            valDue = valori.get(1);
                            if ((valUno instanceof Integer) && (valDue instanceof Integer)) {
                                intValUno = (Integer) valUno;
                                intValDue = (Integer) valDue;

                                risultato = intValUno - intValDue;
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case differenzaReale:

                    case differenzaValuta:
                        /* utilizzo solo i primi due valori dela lista */
                        if (valori.size() == 2) {
                            valUno = valori.get(0);
                            valDue = valori.get(1);
                            if ((valUno instanceof Double) && (valDue instanceof Double)) {
                                doubleValUno = (Double) valUno;
                                doubleValDue = (Double) valDue;
                                doubleValProg = doubleValUno - doubleValDue;
                                /* arrotonda il valore in uscita alla precisione del campo*/
                                doubleValProg = Lib.Mat.arrotonda(doubleValProg, numDec);
                                risultato = doubleValProg;
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case differenzaDate:
                        risultato=0;
                        /* utilizzo solo i primi due valori dela lista */
                        if (valori.size() == 2) {
                            valUno = valori.get(0);
                            valDue = valori.get(1);
                                if ((valUno instanceof Date) && (valDue instanceof Date)) {
                                    data1 = (Date) valUno;
                                    data2 = (Date) valDue;
                                    intVal = Lib.Data.diff(data2, data1);
                                    risultato = intVal;
                                }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case prodottoIntero:
                        /* primo elemento */
                        valore = valori.get(0);
                        if (valore instanceof Integer) {
                            intValProg = (Integer) valore;
                        }// fine del blocco if

                        /* traverso la collezione iniziando dal secondo elemento */
                        if (valore instanceof Integer) {
                            for (int k = 1; k < valori.size(); k++) {
                                valore = valori.get(k);
                                if (valore instanceof Integer) {
                                    intVal = (Integer) valore;
                                    intValProg *= intVal;
                                }// fine del blocco if
                            } // fine del ciclo for
                        }// fine del blocco if
                        risultato = intValProg;
                        break;

                    case prodottoReale:

                    case prodottoValuta:
                        /* primo elemento */
                        valore = valori.get(0);
                        if (valore instanceof Integer) {
                            intVal = (Integer) valore;
                            doubleValProg = intVal;
                        }// fine del blocco if
                        if (valore instanceof Double) {
                            doubleValProg = (Double) valore;
                        }// fine del blocco if

                        /* traverso la collezione iniziando dal secondo elemento */
                        for (int k = 1; k < valori.size(); k++) {
                            valore = valori.get(k);
                            if (valore instanceof Integer) {
                                intVal = (Integer) valore;
                                doubleVal = intVal;
                            }// fine del blocco if
                            if (valore instanceof Double) {
                                doubleVal = (Double) valore;
                            }// fine del blocco if
                            doubleValProg *= doubleVal;
                        } // fine del ciclo for
                        risultato = doubleValProg;

                        /* arrotonda il valore in uscita alla precisione del campo*/
                        doubleValProg = Lib.Mat.arrotonda(doubleValProg, numDec);
                        risultato = doubleValProg;
                        break;
                    case inverso:
                        /* primo elemento */
                        valore = valori.get(0);
                        if (valore instanceof Double) {
                            doubleVal = (Double) valore;
                            if (doubleVal != 0) {
                                doubleValProg = 1 / doubleVal;
                                risultato = doubleValProg;
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case sommatoriaReale:
                    case sommatoriaValuta:
                        risultato = this.getSommatoria();
                        break;

                    case giornoDelMese:
                        valore = valori.get(0);
                        if (valore != null) {
                            if (valore instanceof Date) {
                                data = (Date) valore;
                                intVal = 0;
                                if (!Lib.Data.isVuota(data)) {
                                    intVal = Lib.Data.getNumeroGiorno(data);
                                }// fine del blocco if
                                risultato = intVal;
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case meseDellAnno:
                        valore = valori.get(0);
                        if (valore != null) {
                            if (valore instanceof Date) {
                                data = (Date) valore;
                                intVal = 0;
                                if (!Lib.Data.isVuota(data)) {
                                    intVal = Lib.Data.getNumeroMese(data);
                                }// fine del blocco if
                                risultato = intVal;
                            }// fine del blocco if
                        }// fine del blocco if
                        break;

                    case indiceGiornoDellAnno:
                        if (valori.size() >= 1) {
                            valore = valori.get(0);
                            data = Libreria.getDate(valore);
                            intValUno = Lib.Data.getNumeroGiorno(data);
                            intValDue = Lib.Data.getNumeroMese(data);
                            risultato = Lib.Data.getIndiceGiornoDellAnno(intValUno, intValDue);
                        }// fine del blocco if
                        break;

                    case anno:
                        if (valori.size() >= 1) {
                            valore = valori.get(0);
                            data = Libreria.getDate(valore);
                            risultato = Lib.Data.getAnno(data);
                        }// fine del blocco if
                        break;


                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }


    /**
     * Elabora la sommatoria del valore del campo per tutti i recods linkati.
     * <p/>
     * Il primo valore dei campi osservati è il nome del modulo <br>
     * Il secondo valore dei campi osservati è il nome del campo da sommare <br>
     *
     * @return la sommatoria
     */
    private double getSommatoria() {
        /* variabili e costanti locali di lavoro */
        double sommatoria = 5;
        boolean continua;
        Campo campoOsservato = null;
        Form form;
        ArrayList<String> nomi;
        String nomeModulo;
        String nomeCampo;

        try {    // prova ad eseguire il codice
            /* recupera la lista di nomi interessati */
            nomi = this.getCampiOsservati();
            continua = (nomi != null && nomi.size() == 2);

            if (continua) {
                nomeModulo = nomi.get(0);
                nomeCampo = nomi.get(1);
                continua = (Lib.Testo.isValida(nomeModulo) && Lib.Testo.isValida(nomeCampo));
            }// fine del blocco if

            if (continua) {
                ;
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return sommatoria;
    }


    /**
     * Restituisce un campo dal Form.
     * <p/>
     *
     * @param nome del campo richiesto
     * @return campo richiesto
     */
    protected Campo getCampo(String nome) {
        /* variabili e costanti locali di lavoro */
        Campo campoOsservato = null;
        Form form;

        try {    // prova ad eseguire il codice

            form = this.getCampoParente().getForm();
            campoOsservato = form.getCampo(nome);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoOsservato;
    }


    /**
     * Ritorna l'elenco dei valori dei campi osservati.
     * <p/>
     *
     * @return i valori dei campi osservati
     */
    protected ArrayList<Object> getValoriOsservati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> valori = null;
        ArrayList<String> nomiCampi;
        Campo campo;
        Object oggetto;

        try {    // prova ad eseguire il codice
            nomiCampi = this.getCampiOsservati();
            valori = new ArrayList<Object>();
            for (String nome : nomiCampi) {
                campo = this.getCampo(nome);
                oggetto = campo.getValore();
                valori.add(oggetto);
            } // fine del ciclo for-each
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    }


    /**
     * Ritorna l'elenco dei nomi dei campi osservati
     * <p/>
     * I campi sono dello stesso modulo di questo campo.
     *
     * @return i nomi dei campi osservati
     */
    public ArrayList<String> getCampiOsservati() {
        return campi;
    }


    public void setCampiOsservati(ArrayList<String> campi) {
        this.campi = campi;
    }


    public CampoLogica.Calcolo getOperazione() {
        return operazione;
    }


    public void setOperazione(CampoLogica.Calcolo operazione) {
        this.operazione = operazione;
    }


    /**
     * Flag per identificare un campo calcolato.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * La variabile d'istanza viene mantenuta nella sottoclasse specifica <br>
     *
     * @return vero se è un campo calcolato
     */
    public boolean isCalcolato() {
        return this.calcolato;
    }


    public void setCalcolato(boolean calcolato) {
        this.calcolato = calcolato;
    }

}// fine della classe
