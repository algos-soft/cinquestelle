/**
 * Title:     PrefFatture
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-feb-2007
 */
package it.algos.albergo;

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
 * @version 1.0 / 6 ott 2006
 */
public final class AlbergoPref extends Pref {


    /**
     * Classe interna Enumerazione.
     */
    public enum Albergo implements PrefGruppi {

        /* il codice della azienda attiva */
        azienda("",
                "Azienda attiva",
                "Codice dell'azienda attiva",
                Utente.user,
                TipoDati.intero,
                null,
                1,
                "Codice dell'azienda attiva, 0 per tutte le aziende",
                "",
                true,
                true,
                false),

        /* il path per i file pdf della stampa Pubblica Sicurezza */
        pathPdfPS("",
                "PathPdfPS",
                "Percorso per il file .pdf delle stampe di P.S.",
                Utente.user,
                TipoDati.stringa,
                null,
                "",
                "Percorso completo della directory dove registrare i file .pdf delle stampe di Pubblica Sicurezza",
                "path dei pdf di p.s.",
                true,
                true,
                false),
        
        /* il path per i file txt di esportazione notifiche per Servizio Alloggiati PS e ISTAT */
        pathTxtPS("",
                "PathTxtPS",
                "Percorso per i file .txt per Servizio Alloggiati PS",
                Utente.user,
                TipoDati.stringa,
                null,
                "",
                "Percorso completo della directory dove registrare i file .txt esportati per il Servizio Alloggiati PS e ISTAT",
                "path dei file txt di p.s.",
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
         * @param alias della preferenza
         * @param sigla della preferenza
         * @param descrizione della preferenza
         * @param livello di utente che può accedere
         * @param tipoDati di dato utilizzato
         * @param lista di valori
         * @param standard valore standard
         * @param nota per l'utente
         * @param notaProgr per il programmatore
         * @param visibile nel dialogo di modifica
         * @param mostraDefault nel dialogo di modifica
         * @param setup nella prima istallazione
         */
        Albergo(String alias,
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
    public AlbergoPref() {
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
            this.addGruppo(AlbergoPref.Albergo.class);
            Pref.carica();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
