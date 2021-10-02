package ru.school21.eleonard.menu.schoolInfo.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.data.network.api.models.UserInListResponse
import ru.school21.eleonard.databinding.FragmentSchoolInfoBinding
import ru.school21.eleonard.data.network.helpers.Status
import ru.school21.eleonard.helpers.VerticalMarginItemDecoration
import ru.school21.eleonard.helpers.base.BaseFragment
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.helpers.utils.ToastStates
import ru.school21.eleonard.helpers.utils.UtilsUI
import ru.school21.eleonard.menu.schoolInfo.adapters.UsersAdapter
import ru.school21.eleonard.menu.schoolInfo.viewModels.SchoolInfoViewModel

@AndroidEntryPoint
class SchoolInfoFragment : BaseFragment(R.layout.fragment_school_info) {
	override val binding by viewBinding(FragmentSchoolInfoBinding::bind)
	override val hasOptionMenu: Boolean = true
	override val toolbarState = ToolbarStates.STATE_SCHOOL_MAIN

	private lateinit var searchView: SearchView
	private lateinit var schoolInfoViewModel: SchoolInfoViewModel

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)

		val menuItemSearch = menu.findItem(R.id.menuItemSearch)
		searchView = menuItemSearch.actionView as SearchView
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				schoolInfoViewModel.getUserInfo(query)
				menuItemSearch.collapseActionView()
				return false
			}

			override fun onQueryTextChange(s: String): Boolean {
				return false
			}
		})
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		schoolInfoViewModel = ViewModelProvider(this).get(SchoolInfoViewModel::class.java)
		configureViews()
		initListeners()
		initObservers()
		loadData()
	}

	private fun loadData() {
		schoolInfoViewModel.getAllUsers()
	}

	private fun initObservers() {
		observerOnUserInfoLoadingResponse()
		observerOnUsersLoadingResponse()
	}

	private fun observerOnUsersLoadingResponse() {
		schoolInfoViewModel.usersGettingResponse.observe(viewLifecycleOwner,  {
			when (it.status) {
				Status.LOADING -> {
				}
				Status.SUCCESS -> {
					if (it.data == null) {
						UtilsUI.makeCoolToast(resources.getString(R.string.loading_message_error), ToastStates.ERROR)
					} else {
						setUsersAdapter(it.data)
					}
				}
				Status.ERROR -> {
					if (it.error == resources.getString(R.string.server_404))
						UtilsUI.makeCoolToast(resources.getString(R.string.response_no_such_user), ToastStates.ERROR)
					else
						UtilsUI.makeCoolToast(it.error ?: resources.getString(R.string.loading_message_error), ToastStates.ERROR)
				}
			}
		})
	}

	private fun setUsersAdapter(users: List<UserInListResponse>) {
		if (binding.rvUsers.adapter == null) {
			if (binding.rvUsers.itemDecorationCount == 0)
				binding.rvUsers.addItemDecoration(VerticalMarginItemDecoration(requireContext(), R.dimen.users_margin))
			binding.rvUsers.adapter = UsersAdapter(users)
		} else {
			(binding.rvUsers.adapter as? UsersAdapter)?.update(users)
		}
	}

	private fun observerOnUserInfoLoadingResponse() {
		schoolInfoViewModel.userInfoGettingResponse.observe(viewLifecycleOwner,  {
			when (it.status) {
				Status.LOADING -> {
				}
				Status.SUCCESS -> {
					if (it.data == null) {
						UtilsUI.makeCoolToast(resources.getString(R.string.loading_message_error), ToastStates.ERROR)
					} else {
						print("")
					}
				}
				Status.ERROR -> {
					if (it.error == resources.getString(R.string.server_404))
						UtilsUI.makeCoolToast(resources.getString(R.string.response_no_such_user), ToastStates.ERROR)
					else
						UtilsUI.makeCoolToast(it.error ?: resources.getString(R.string.loading_message_error), ToastStates.ERROR)
				}
			}
		})
	}

	private fun configureViews() {
	}

	private fun initListeners() {

	}
}