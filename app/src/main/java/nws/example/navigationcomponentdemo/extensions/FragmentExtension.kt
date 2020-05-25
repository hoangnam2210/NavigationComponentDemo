package nws.example.navigationcomponentdemo.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(
    destinationId: Int,
    result: T,
    key: String,
    exception: ((Exception) -> Unit)? = null
) {
    try {
        findNavController().getBackStackEntry(destinationId).savedStateHandle.set(key, result)
    } catch (e: Exception) {
        exception?.let {
            it(e)
        }
    }
}

fun <T> Fragment.setNavigationResult(
    result: T,
    key: String,
    exception: ((Exception) -> Unit)? = null
) {
    try {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    } catch (e: Exception) {
        exception?.let {
            it(e)
        }
    }
}