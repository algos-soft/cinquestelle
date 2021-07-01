package it.algos.albergo.rubrica;

import it.algos.base.campo.base.Campo;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.rubrica.RubricaModello;

/**
 * Tracciato record della tavola rubrica (anagrafica), specializzata per Albergo.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) nel metodo <code>creaCampi</code> </li>
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * <li> Crea eventuali <strong>viste</strong> della <code>Lista</code>
 * (oltre a quella base) nel metodo <code>creaViste</code> </li>
 * <li> Regola eventualmente i valori delle viste nel metodo <code>regolaViste</code> </li>
 * <li> Crea eventuali <strong>set</strong> della <code>Scheda</code>
 * (oltre a quello base) nel metodo <code>creaSet</code> </li>
 * <li> Regola eventualmente i valori dei set nel metodo <code>regolaSet</code> </li>
 * <li> Regola eventualmente i valori da inserire in un <code>nuovoRecord</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Gac
 * @version 1.0 / 7-apr-2009 ore 14:09
 */
public final class RubricaAlbergoModello extends RubricaModello implements RubricaAlbergo {

    @Override
    protected void creaCampi() {

        super.creaCampi();

        /* campo note - rende ricercabile */
        Campo campo = super.getCampo(Anagrafica.Cam.note.get());
        campo.setRicercabile(true);


    }
}// fine della classe