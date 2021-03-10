package ru.school21.eleonard.menu.schoolInfo.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentSchoolInfoBinding
import ru.school21.eleonard.helpers.requests.Status
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.menu.schoolInfo.viewModels.SchoolInfoViewModel

@AndroidEntryPoint
class SchoolInfoFragment : Fragment() {
	private lateinit var binding: FragmentSchoolInfoBinding
	private lateinit var searchView: SearchView
	private lateinit var schoolInfoViewModel: SchoolInfoViewModel

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)

		ToolbarConfigurator().configureToolbar(
			menu,
			inflater,
			requireActivity() as? ToolbarManager,
			ToolbarStates.STATE_SCHOOL_MAIN,
			resources.getString(R.string.bnv_companion)
		)
		val menuItemSearch = menu.findItem(R.id.menuItemSearch)
		searchView = menuItemSearch.actionView as SearchView
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				schoolInfoViewModel.loadUserInfo(query)
				menuItemSearch.collapseActionView()
				return false
			}

			override fun onQueryTextChange(s: String): Boolean {
				return false
			}
		})
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentSchoolInfoBinding.inflate(layoutInflater, container, false)
		schoolInfoViewModel = ViewModelProvider(this).get(SchoolInfoViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		configureViews()
		initListeners()
		initObservers()
	}

	private fun initObservers() {
		observerOnUserInfoLoadingResponse()
	}

	private fun observerOnUserInfoLoadingResponse() {
		schoolInfoViewModel.userInfoLoadingResponse.observe(viewLifecycleOwner, Observer {
			when (it.status) {
				Status.LOADING -> {

				}
				Status.SUCCESS -> {

				}
				Status.ERROR -> {

				}
			}
		})
	}

	private fun configureViews() {
		setHasOptionsMenu(true)
	}

	private fun initListeners() {

	}
}