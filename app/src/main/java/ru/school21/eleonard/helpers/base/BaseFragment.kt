package ru.school21.eleonard.helpers.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.KeyboardManager
import ru.school21.eleonard.mainWindow.ToolbarManager

abstract class BaseFragment(@LayoutRes val contentLayoutId: Int) : Fragment() {
    abstract val binding: ViewBinding
    abstract val hasOptionMenu: Boolean
    abstract val toolbarState: ToolbarStates

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        ToolbarConfigurator().configureToolbar(
            menu,
            inflater,
            requireActivity() as? ToolbarManager,
            toolbarState
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(hasOptionMenu)
        return inflater.inflate(contentLayoutId, container, false)
    }

    protected fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
        FragmentViewBindingDelegate(this, viewBindingFactory)

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? KeyboardManager)?.hideKeyboard()
    }
}