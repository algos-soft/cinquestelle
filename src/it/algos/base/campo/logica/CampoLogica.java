/**
 * Title:        CampoLogica.java
 * Package:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 15.40
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoBaseEve;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;

import java.util.ArrayList;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Regola le funzionalita dei rapporti interni dei Campi <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 15.40
 */
public interface CampoLogica {

    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public abstract void inizializza();


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public abstract void avvia();


    /**
     * Regola l'azione.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Associa l'azione al componente dei campi osservati <br>
     */
    public abstract void regolaAzione();


    /**
     * Allinea le variabili del Campo: da Archivio verso Memoria.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Archivio (gia' regolata), e regola
     * di conseguenza Memoria, Backup <br>
     * Alla fine viene aggiornato l'oggetto GUI del CampoVideo <br>
     */
    public abstract void archivioMemoria();


    /**
     * Allinea le variabili del Campo: da Memoria verso GUI.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Video <br>
     * In CampoVideo vengono aggiornati i componenti GUI <br>
     * I componenti GUI sono allineati coi valori della memoria
     * dopo eventuali elaborazioni con altri campi <br>
     */
    public abstract void memoriaGui();


    /**
     * Allinea le variabili del Campo: da Archivio verso GUI.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Archivio (gia' regolata), e regola
     * di conseguenza Memoria, Backup <br>
     * In CampoVideo vengono aggiornati i componenti GUI <br>
     * I componenti GUI sono allineati coi valori della memoria
     * dopo eventuali elaborazioni con altri campi <br>
     * Alla fine viene aggiornato l'oggetto GUI del CampoVideo <br>
     */
    public abstract void archivioGui();


    /**
     * Allinea le variabili del Campo: da GUI verso video.
     * <p/>
     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
     * variabili Video di CampoDati <br>
     * Recupera il valore attuale del componente GUI in CampoVideo <br>
     * Aggiorna il valore del video in CampoDati <br>
     */
    public abstract void guiVideo();


    /**
     * Allinea le variabili del Campo: da GUI verso Memoria.
     * <p/>
     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
     * variabili Video e Memoria di CampoDati <br>
     * Recupera il valore attuale del componente GUI in CampoVideo <br>
     * Aggiorna il valore del video e della memoria in CampoDati <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     */
    public abstract void guiMemoria();


    /**
     * Allinea le variabili del Campo: da Memoria verso Archivio.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Archivio <br>
     * La variabile archivio e' allineata per la registrazione <br>
     */
    public abstract void memoriaArchivio();


    /**
     * Allinea le variabili del Campo: da GUI verso Archivio.
     * <p/>
     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
     * variabili Video e Memoria di CampoDati <br>
     * Recupera il valore attuale del componente GUI in CampoVideo <br>
     * Aggiorna il valore del video e della memoria in CampoDati <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     * La variabile archivio e' allineata per la registrazione <br>
     */
    public abstract void guiArchivio();


    /**
     * Svuota il valore del campo.
     * <p/>
     * Assegna alla variabile Memoria il proprio valore vuoto <br>
     * Allinea la variabile Backup col nuovo valore <br>
     * Fa risalire i valori fino alla GUI <br>.
     */
    public abstract void reset();


    /**
     * Gestione evento gui modificata.
     * <p/>
     * Allinea le variabili del Campo: da GUI verso Memoria <br>
     * Invoca il metodo delegato del campo video <br>
     */
    public abstract void guiModificata();


    public void campoModificato(CampoBaseEve unEvento);


    public abstract void setCampoParente(Campo unCampoParente);


    public abstract void setModuloInterno(Modulo unModuloInterno);


    public abstract Modulo getModuloInterno();

//    public abstract boolean isInizializzato();


    public abstract void aggiornaCampo();


    public abstract void setNomeCampoOriginale(String nomeCampoOriginale);


    public abstract void esegui();


    /**
     * Esegue l'operazione prevista.
     * <p/>
     * Riceve una lista di valori arbitrari (devono essere dello stesso tipo) <br>
     * Recupera dal campo il tipo di operazione da eseguire <br>
     * Esegue l'operazione su tutti i valori <br>
     * restituisce il valore risultante <br>
     *
     * @param valori in ingresso
     *
     * @return risultato dell'operazione
     */
    public abstract Object esegueOperazione(ArrayList<Object> valori);


    /**
     * Ritorna l'elenco dei nomi dei campi osservati
     * <p/>
     * I campi sono dello stesso modulo di questo campo.
     *
     * @return i nomi dei campi osservati
     */
    public abstract ArrayList<String> getCampiOsservati();


    public abstract Navigatore getNavigatore();


    /**
     * Assegna il nome del modulo per recuperare il navigatore.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     */
    public abstract void setNomeModulo(String nomeModulo);


    /**
     * Assegna una scheda per l'editing del record esterno.
     * <p/>
     * (significativo per campi di tipo Combo o Estratto)
     *
     * @param nomeSchedaPop nella collezione schede del modulo esterno
     */
    public abstract void setNomeSchedaPop(String nomeSchedaPop);


    /**
     * Flag per identificare un campo calcolato.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * La variabile d'istanza viene mantenuta nella sottoclasse specifica <br>
     *
     * @return vero se è un campo calcolato
     */
    public abstract boolean isCalcolato();


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     *
     * @param campoParente CampoBase che contiene questo CampoLogica
     *
     * @return campo logica clonato
     */
    public abstract CampoLogica clonaCampo(Campo campoParente);


    /**
     * Classe interna Enumerazione.
     */
    public enum Calcolo {

        sommaIntero(Calcolo.Tipo.intero),
        sommaReale(Calcolo.Tipo.reale),
        sommaValuta(Calcolo.Tipo.valuta),
        sommaTesto(Calcolo.Tipo.testo),
        differenzaIntero(Calcolo.Tipo.intero),
        differenzaReale(Calcolo.Tipo.reale),
        differenzaValuta(Calcolo.Tipo.valuta),
        differenzaDate(Calcolo.Tipo.intero),
        prodottoIntero(Calcolo.Tipo.intero),
        prodottoReale(Calcolo.Tipo.reale),
        prodottoValuta(Calcolo.Tipo.valuta),
        inverso(Calcolo.Tipo.valuta),
        copia(Calcolo.Tipo.testo),
        ricopia(null),
        sommatoriaReale(Calcolo.Tipo.reale),
        sommatoriaValuta(Calcolo.Tipo.valuta),
        giornoDelMese(Calcolo.Tipo.intero),
        meseDellAnno(Calcolo.Tipo.intero),
        indiceGiornoDellAnno(Calcolo.Tipo.intero),
        anno(Calcolo.Tipo.intero);

        /**
         * flag per la tipologia degli operatori (numero o testo).
         */
        private Calcolo.Tipo tipo;


        /**
         * Costruttore completo con parametri.
         *
         * @param tipo flag per la tipologia degli operatori (numero o testo)
         */
        Calcolo(Calcolo.Tipo tipo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTipo(tipo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Tipo getTipo() {
            return tipo;
        }


        private void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }


        /**
         * Classe interna Enumerazione.
         */
        public enum Tipo {

            intero(),
            reale(),
            valuta(),
            testo()
        }// fine della classe

    }// fine della classe

}// fine della interfaccia


