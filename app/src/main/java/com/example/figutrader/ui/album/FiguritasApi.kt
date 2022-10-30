package com.example.figutrader.ui.album

class FiguritasApi {
    fun getFiguritas(): MutableList<Figurita> {
        val dataset = mutableListOf<Figurita>()
        for (i in 0..21) {
            if (i % 11 == 0) {
                if (i == 0) {
                    dataset.add(Figurita(i ,"Brasil", 0, "",true))
                } else {
                    dataset.add(Figurita(i ,"Argentina", 0, "",true))
                }
                continue
            }
            if (i < 11)
                dataset.add(Figurita(i, "BRA $i", 0, "Brasil", false))
            else
                dataset.add(Figurita(i, "ARG " + (i-11), 0, "Argentina", false))
        }
        return dataset
    }
}