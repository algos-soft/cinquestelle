package it.algos.albergo.promemoria;

import it.algos.base.errore.Errore;
import it.algos.base.pref.Pref;
import it.algos.base.pref.PrefGruppi;
import it.algos.base.pref.PrefTipo;
import it.algos.base.pref.PrefWrap;

import java.util.ArrayList;
import java.util.Date;

/**
 * Preferenze specifiche del programma.
 * </p>
 * Parametri richiesti per costruire un oggetto preferenza:<ul>
 * <li> Alias </li>
 * <li> Sigla </li>
 * <li> Descrizione </li>
 * <li> Livello </li>
 * <li> Tipo di dati</li>
 * <li> Lista di valori (solo per il combo) </li>
 * <li> Valore standard </li>
 * <li> Nota </li>
 * <li> Nota programmatore </li>
 * <li> Visibile </li>
 * <li> Mostra valore di default </li>
 * <li> prima istallazione </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 20 mar 2009
 */
public final class PromemoriaPref extends Pref {


    /**
     * Classe interna Enumerazione.
     */
    public enum Promemoria implements PrefGruppi {

        /* il path per i file pdf della stampa Pubblica Sicurezza */
        frequenza("",
                "frequenza",
                "Frequenza di controllo per visualizzare gli avvisi",
                Utente.user,
                TipoDati.combo,
                Tempo.getLista(),
                Tempo.m30.getValore(),
                "Tempo espresso in minuti",
                "",
                true,
                true,
                false);


        /**
         * wrapper con tutti i valori
         */
        private PrefWrap wrap;


        /**
         * Costruttore completo.
         *
         * @param alias         della preferenza
         * @param sigla         della preferenza
         * @param descrizione   della preferenza
         * @param livello       di utente che può accedere
         * @param tipoDati      di dato utilizzato
         * @param lista         di valori
         * @param standard      valore standard
         * @param nota          per l'utente
         * @param notaProgr     per il programmatore
         * @param visibile      nel dialogo di modifica
         * @param mostraDefault nel dialogo di modifica
         * @param setup         nella prima istallazione
         */
        Promemoria(String alias,
                   String sigla,
                   String descrizione,
                   Utente livello,
                   TipoDati tipoDati,
                   ArrayList<PrefTipo> lista,
                   Object standard,
                   String nota,
                   String notaProgr,
                   boolean visibile,
                   boolean mostraDefault,
                   boolean setup) {

            /* variabili e costanti locali di lavoro */
            PrefWrap wrap;

            try { // prova ad eseguire il codice

                wrap = new PrefWrap();

                wrap.setAlias(alias);
                wrap.setSigla(sigla);
                wrap.setDescrizione(descrizione);
                wrap.setLivello(livello);
                wrap.setTipoDati(tipoDati);
                wrap.setLista(lista);
                wrap.setStandard(standard);
                wrap.setNota(nota);
                wrap.setNotaProg(notaProgr);
                wrap.setVisibile(visibile);
                wrap.setMostraDefault(mostraDefault);
                wrap.setSetup(setup);

                this.setWrap(wrap);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String str() {
            /* invoca il metodo delegato della classe */
            return Pref.str(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int intero() {
            /* invoca il metodo delegato della classe */
            return Pref.intero(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di booleano.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public boolean is() {
            /* invoca il metodo delegato della classe */
            return Pref.is(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di data.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Date data() {
            /* invoca il metodo delegato della classe */
            return Pref.data(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di intero.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public int comboInt() {
            /* invoca il metodo delegato della classe */
            return Pref.comboInt(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di stringa.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public String comboStr() {
            /* invoca il metodo delegato della classe */
            return Pref.comboStr(this);
        }


        /**
         * Restituisce il valore corrente sotto forma di oggetto.
         *
         * @return valore solo se il tipo dati è corretto
         */
        public Object comboOgg() {
            /* invoca il metodo delegato della classe */
            return Pref.comboOgg(this);
        }


        public PrefWrap getWrap() {
            return wrap;
        }


        private void setWrap(PrefWrap wrap) {
            this.wrap = wrap;
        }
    }// fine della classe


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public PromemoriaPref() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     * <p/>
     * crea il contenitore per tutte le Enumeration <br>
     * aggiunge le Enumeration del progetto base <br>
     * le sottoclassi possono aggiungere altre Enumeration <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.addGruppo(PromemoriaPref.Promemoria.class);
            Pref.carica();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Tempo implements PrefTipo {

        m15("15 minuti", 0),
        m30("30 minuti", 1),
        o1("1 ora", 2),
        o2("2 ore", 3),
        o4("4 ore", 4);


        /**
         * sigla
         */
        private String sigla;

        /**
         * valore
         */
        private int valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla  del database
         * @param valore del database
         */
        Tempo(String sigla, int valore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (Tempo ute : Tempo.values()) {
                    lista.add(ute);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public Object getValore() {
            return valore;
        }


        private void setValore(int valore) {
            this.valore = valore;
        }


        @Override
        public String toString() {
            return getSigla();
        }

    }// fine della classe

} // fine della classe
