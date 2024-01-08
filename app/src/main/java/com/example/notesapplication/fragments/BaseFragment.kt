package com.example.notesapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {
    private lateinit var job: Job // Zmienna do zarządzania korutynami

    // Kontekst korutyny, który używa głównego wątku (Dispatchers.Main)
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job() // Inicjalizacja obiektu Job podczas tworzenia aktywności
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel() // Anulowanie wszystkich korutyn związanych z tą aktywnością podczas jej zniszczenia
    }

}