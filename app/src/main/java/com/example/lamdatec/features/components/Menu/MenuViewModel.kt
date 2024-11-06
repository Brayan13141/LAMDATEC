package com.example.lamdatec.features.components.Menu

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2RepositoryImp
import com.example.lamdatec.features.pPrincipal.data.Worker.ActualizarHoraWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class MenuViewModel : ViewModel() {
    var selectedItem = MutableStateFlow<String>("")
        private set

    init {
        ActualizarselectedItem("INICIO")
    }

     fun ActualizarselectedItem(Valor:String) {
            selectedItem.value = Valor// Actualiza el estado de los puntos del gráfico
    }
}