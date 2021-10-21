package pro.enaza.materialdesign.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pro.enaza.materialdesign.data.model.Card

class DashboardViewModel : ViewModel() {

    private val mutableCardList = MutableStateFlow(emptyList<Card>())
    val observableCardList: StateFlow<List<Card>> = mutableCardList

    private val mutableSnackBar = MutableStateFlow(false)
    val observableSnackBar: StateFlow<Boolean> = mutableSnackBar

    init {
        var cardList = emptyList<Card>().toMutableList()
        cardList.add(Card(1, "Pesto pasta", 15, "Made with out homemade basil pine nuts pesto sauce. Gluten free pasta available upon request.", "https://www.redstar-pizza.fr/AMBIANCE_1FWKIMF7A7_GrandeRiserva/template/img/01.jpg"))
        cardList.add(Card(2, "Limon", 15, "Made with out homemade basil pine nuts pesto sauce. Gluten free pasta available upon request.", "https://datchnik.ru/wp-content/uploads/2/5/7/257f94859d9d7d8fd371e5de6bc92cca.jpeg"))
        cardList.add(Card(3, "Persik", 15, "Made with out homemade basil pine nuts pesto sauce. Gluten free pasta available upon request.", "https://img5.goodfon.ru/original/2880x1800/b/3d/derevo-vetki-plod-persik.jpg"))
        cardList.add(Card(4, "Malina", 20, "Made with out homemade basil pine nuts pesto sauce. Gluten free pasta available upon request.", "https://s1.1zoom.me/b5050/175/Berry_Raspberry_Closeup_496895_1920x1200.jpg"))

        viewModelScope.launch {
            mutableCardList.emit(cardList)
        }
    }

    fun postSnackBar() {
        viewModelScope.launch {
            mutableSnackBar.emit(true)
        }
    }
}