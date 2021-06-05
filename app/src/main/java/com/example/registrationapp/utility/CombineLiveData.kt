package com.example.registrationapp.utility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


object CombineLiveData {

    /**
     * Combine live data into pair using MediatorLiveData
     */

    fun <A, B, C, D> combinesLiveData(a: LiveData<A>, b: LiveData<B>, c: LiveData<C>,
                                      d: LiveData<D>): LiveData<Pair<D, Triple<A, B, C>>> {
        return MediatorLiveData<Pair<D, Triple<A, B, C>>>().apply {
            var observerA: A? = null
            var observerB: B? = null
            var observerC: C? = null
            var observerD: D? = null

            fun update() {
                val localLastA = observerA
                val localLastB = observerB
                val localLastC = observerC
                val localLastD = observerD
                if (localLastA != null && localLastB != null && localLastC != null && localLastD != null)
                    this.value = Pair(localLastD, Triple(localLastA, localLastB, localLastC))
            }

            addSource(a) {
                observerA = it
                update()
            }
            addSource(b) {
                observerB = it
                update()
            }

            addSource(c){
                observerC = it
                update()
            }

            addSource(d){
                observerD = it
                update()
            }
        }
    }
}