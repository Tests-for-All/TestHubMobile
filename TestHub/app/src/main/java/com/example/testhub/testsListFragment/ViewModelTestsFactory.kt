import com.example.testhub.testsListFragment.ViewModelTests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testhub.repository.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelTestsFactory (
    private val repo: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelTests::class.java -> ViewModelTests (repo)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}