/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-nov-2004
 */
package it.algos.base.query.filtro;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.db.CDBBase;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.Campi;
import it.algos.base.libreria.Libreria;

import java.util.ArrayList;

/**
 * Factory per la creazione di Filtri.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> <br>
 * <li> Fornisce i metodi statici di creazione degli oggetti di questo
 * package </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-nov-2004 ore 12.45.56
 */
public abstract class FiltroFactory extends Object {

    /**
     * Costruttore semplice senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    private FiltroFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Crea un filtro.
     * <p/>
     *
     * @param campo     l'oggetto Campo
     * @param operatore l'operatore (v. Operatore)
     * @param valore    il valore
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(Campo campo, String operatore, Object valore) {
        return new Filtro(campo, operatore, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * @param operatore l'operatore (v. Operatore)
     * @param valore    il valore
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(String nomeCampo, String operatore, Object valore) {
        return new Filtro(nomeCampo, operatore, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param campo  l'oggetto Campo
     * @param valore il valore
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(Campo campo, Object valore) {
        return new Filtro(campo, Operatore.UGUALE, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param nomeCampo il nome interno del campo
     * @param valore    il valore
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(String nomeCampo, Object valore) {
        return new Filtro(nomeCampo, Operatore.UGUALE, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     *
     * @param campo     l'oggetto Campo
     * @param operatore l'operatore (v. Operatore)
     * @param valore    il valore intero
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(Campo campo, String operatore, int valore) {
        return new Filtro(campo, operatore, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * @param operatore l'operatore (v. Operatore)
     * @param valore    il valore intero
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(String nomeCampo, String operatore, int valore) {
        return new Filtro(nomeCampo, operatore, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     *
     * @param campo     l'oggetto Campo
     * @param operatore l'operatore (v. Operatore)
     * @param valore    il valore booleano
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(Campo campo, String operatore, boolean valore) {
        return new Filtro(campo, operatore, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     * @param operatore l'operatore (v. Operatore)
     * @param valore    il valore booleano
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(String nomeCampo, String operatore, boolean valore) {
        return new Filtro(nomeCampo, operatore, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param campo  l'oggetto Campo
     * @param valore il valore intero
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(Campo campo, int valore) {
        return new Filtro(campo, Operatore.UGUALE, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param nomeCampo il nome interno del campo
     * @param valore    il valore intero
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(String nomeCampo, int valore) {
        return new Filtro(nomeCampo, Operatore.UGUALE, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param campo  l'oggetto Campo
     * @param valore il valore booleano
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(Campo campo, boolean valore) {
        return new Filtro(campo, Operatore.UGUALE, valore);
    }// fine del metodo


    /**
     * Crea un filtro.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param nomeCampo il nome interno del campo
     * @param valore    il valore booleano
     *
     * @return un filtro appena creato
     */
    public static Filtro crea(String nomeCampo, boolean valore) {
        return new Filtro(nomeCampo, Operatore.UGUALE, valore);
    }// fine del metodo


    /**
     * Crea un filtro di tutti i record veri.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return un filtro appena creato
     */
    public static Filtro creaVero(String nomeCampo) {
        return crea(nomeCampo, true);
    }// fine del metodo


    /**
     * Crea un filtro di tutti i record veri.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return un filtro appena creato
     */
    public static Filtro creaVero(Campi campo) {
        return crea(campo.get(), true);
    }// fine del metodo


    /**
     * Crea un filtro di tutti i record falsi.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return un filtro appena creato
     */
    public static Filtro creaFalso(String nomeCampo) {
        return crea(nomeCampo, false);
    }// fine del metodo


    /**
     * Crea un filtro di tutti i record veri.
     * <p/>
     * Usa l'operatore di default UGUALE
     *
     * @param campo della Enumeration dell'interfaccia
     *
     * @return un filtro appena creato
     */
    public static Filtro creaFalso(Campi campo) {
        return crea(campo.get(), false);
    }// fine del metodo


    /**
     * Crea un filtro per selezionare un singolo
     * record di un modulo dal codice.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     * @param codice valore della chiave
     *
     * @return un filtro appena creato
     */
    public static Filtro codice(Modulo modulo, int codice) {
        return crea(modulo.getCampoChiave(), codice);
    }// fine del metodo


    /**
     * Crea un filtro per non selezionare alcun record
     * <p/>
     *
     * @param modulo il modulo di riferimento
     *
     * @return il filtro per non selezionare alcun record
     */
    public static Filtro nessuno(Modulo modulo) {
        return crea(modulo.getCampoChiave(), -1);
    }// fine del metodo


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     *
     * @param campo       campo combo link in fase di creazione/regolazione
     * @param campoFiltro campo per la condizione di filtro
     * @param operatore   del filtro (stringa codificata nell'interfaccia Operatore)
     * @param valore      oggetto valore generico
     *
     * @see it.algos.base.database.util.Operatore
     */
    public static void link(Campo campo, Campo campoFiltro, String operatore, Object valore) {

        /* variabili e costanti locali di lavoro */
        CampoDB campoDB = null;
        Filtro filtro;

        try { // prova ad eseguire il codice
            /* crea il filtro */
            filtro = crea(campoFiltro, operatore, valore);

            /* recupera il CampoDB */
            if (campo instanceof CampoBase) {
                campoDB = campo.getCampoDB();
            } else if (campo instanceof CDBBase) {
                campoDB = (CDBBase)campo;
            }// fine del blocco if-else

            /* regola l'attributo del campoDb col filtro appena creato */
            if (campoDB != null) {
                campoDB.setFiltroCorrente(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     *
     * @param campo           campo combo link in fase di creazione/regolazione
     * @param nomeCampoFiltro nome del campo per la condizione di filtro
     * @param operatore       del filtro (stringa codificata nell'interfaccia Operatore)
     * @param valore          oggetto valore generico
     *
     * @see it.algos.base.database.util.Operatore
     */
    public static void link(Campo campo, String nomeCampoFiltro, String operatore, Object valore) {
        /* variabili e costanti locali di lavoro */
        Campo campoFiltro;
        Modulo modulo;

        try { // prova ad eseguire il codice
            modulo = campo.getModulo();

            campoFiltro = modulo.getCampo(nomeCampoFiltro);

            /* invoca il metodo sovrascritto della classe */
            link(campo, campoFiltro, operatore, valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Usa di default l'operatore uguale <br>
     *
     * @param campo           campo combo link in fase di creazione/regolazione
     * @param nomeCampoFiltro nome del campo per la condizione di filtro
     * @param valore          oggetto valore generico
     */
    public static void link(Campo campo, String nomeCampoFiltro, Object valore) {
        /* invoca il metodo sovrascritto della classe */
        link(campo, nomeCampoFiltro, Operatore.UGUALE, valore);
    }


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Usa di default l'operatore uguale <br>
     * Crea al volo un'istanza di Integer, per incapsulare il valore in una classe <br>
     *
     * @param campo           campo combo link in fase di creazione/regolazione
     * @param nomeCampoFiltro nome del campo per la condizione di filtro
     * @param valore          di tipo intero
     */
    public static void link(Campo campo, String nomeCampoFiltro, int valore) {
        /* invoca il metodo sovrascritto della classe */
        link(campo, nomeCampoFiltro, new Integer(valore));
    }


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Usa di default l'operatore uguale <br>
     * Crea al volo un'istanza di Boolean, per incapsulare il valore in una classe <br>
     *
     * @param campo           campo combo link in fase di creazione/regolazione
     * @param nomeCampoFiltro nome del campo per la condizione di filtro
     * @param valore          di tipo booleano
     */
    public static void link(Campo campo, String nomeCampoFiltro, boolean valore) {
        /* invoca il metodo sovrascritto della classe */
        link(campo, nomeCampoFiltro, new Boolean(valore));
    }


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Usa di default l'operatore uguale <br>
     *
     * @param campo       campo combo link in fase di creazione/regolazione
     * @param campoFiltro campo per la condizione di filtro
     * @param valore      oggetto valore generico
     */
    public static void link(Campo campo, Campo campoFiltro, Object valore) {
        /* invoca il metodo sovrascritto della classe */
        link(campo, campoFiltro, Operatore.UGUALE, valore);
    }


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Usa di default l'operatore uguale <br>
     * Crea al volo un'istanza di Integer, per incapsulare il valore in una classe <br>
     *
     * @param campo       campo combo link in fase di creazione/regolazione
     * @param campoFiltro campo per la condizione di filtro
     * @param valore      di tipo intero
     */
    public static void link(Campo campo, Campo campoFiltro, int valore) {
        /* invoca il metodo sovrascritto della classe */
        link(campo, campoFiltro, new Integer(valore));
    }


    /**
     * Crea un filtro per un campo comboLink.
     * <p/>
     * Riceve un campo di tipo combo link a cui applicare un filtro creato coi dati
     * ricevuti <br>
     * Il campo in ingresso può essere sia un CampoBase che un CampoDB <br>
     * Crea il filtro e regola l'attributo del CampoDB <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Usa di default l'operatore uguale <br>
     * Crea al volo un'istanza di Boolean, per incapsulare il valore in una classe <br>
     *
     * @param campo       campo combo link in fase di creazione/regolazione
     * @param campoFiltro campo per la condizione di filtro
     * @param valore      di tipo booleano
     */
    public static void link(Campo campo, Campo campoFiltro, boolean valore) {
        /* invoca il metodo sovrascritto della classe */
        link(campo, campoFiltro, new Boolean(valore));
    }


    /**
     * Crea un filtro per un campo con una serie di valori interi.
     * <p/>
     * Il filtro isola tutti i record dove il valore del campo specificato
     * è uno di quelli passati nell'array
     *
     * @param campo  campo di ricerca
     * @param valori interi da cercare
     *
     * @return il filtro creato
     */
    public static Filtro elenco(Campo campo, int[] valori) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice

            filtro = new Filtro(campo, Filtro.Op.IN, valori);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea un filtro per selezionare tutti i record di un modulo
     * con i dati codici chiave.
     * <p/>
     *
     * @param modulo di riferimento
     * @param codici codici dei record da selezionare
     *
     * @return il filtro creato
     */
    public static Filtro elenco(Modulo modulo, int[] codici) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Campo campo;

        try { // prova ad eseguire il codice

            campo = modulo.getCampoChiave();
            filtro = elenco(campo, codici);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea un filtro per selezionare tutti i record di un modulo
     * con i dati codici chiave.
     * <p/>
     *
     * @param modulo di riferimento
     * @param lista  di codici dei record da selezionare
     *
     * @return il filtro creato
     */
    public static Filtro elenco(Modulo modulo, ArrayList lista) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        boolean continua;
        int size;
        int[] codici = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (modulo != null && lista != null && lista.size() > 0);

            if (continua) {
                size = lista.size();
                codici = new int[size];

                for (int k = 0; k < lista.size(); k++) {
                    codici[k]= Libreria.getInt(lista.get(k));
                } // fine del ciclo for
            }// fine del blocco if

            filtro = elenco(modulo, codici);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


}// fine della classe
