package ru.school21.eleonard.security.ui

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.Constants
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentPinBinding
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.helpers.utils.ToastStates
import ru.school21.eleonard.helpers.utils.UtilsUI
import ru.school21.eleonard.mainWindow.MainActivityViewPagerManager
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.security.adapter.PinAdapter
import ru.school21.eleonard.security.helpers.EncryptionUtils
import ru.school21.eleonard.security.helpers.PinState
import ru.school21.eleonard.security.viewModels.SecurityViewModel

@AndroidEntryPoint
class PinFragment : Fragment() {
	//todo починить отображение при скрытой навигации
	private lateinit var binding: FragmentPinBinding
	private lateinit var securityViewModel: SecurityViewModel
	private lateinit var pinAdapter: PinAdapter

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		ToolbarConfigurator().configureToolbar(
			menu,
			inflater,
			requireActivity() as? ToolbarManager,
			ToolbarStates.STATE_PIN,
			resources.getString(R.string.pin_toolbar_title)
		)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentPinBinding.inflate(layoutInflater, container, false)
		securityViewModel = ViewModelProvider(requireActivity()).get(SecurityViewModel::class.java)
		securityViewModel.attemptsLeft = PIN_ATTEMPTS
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initAdapter()
		configureViews()
		initListeners()
		setHasOptionsMenu(true)
	}

	private fun initAdapter() {
		binding.rvPinContainer.setHasFixedSize(true)
		pinAdapter = PinAdapter(securityViewModel.pinCode.length)
		binding.rvPinContainer.adapter = pinAdapter
	}

	private fun configureViews() {
		binding.tvTitle.visibility = View.VISIBLE
		when (securityViewModel.currentPinState) {
			PinState.CREATE -> {
				binding.tvTitle.text = resources.getString(R.string.pin_title_create)
			}
			PinState.CONFIRM -> {
				binding.tvTitle.text = resources.getString(R.string.pin_title_confirm)
			}
			PinState.DELETE -> {
				binding.tvTitle.text = resources.getString(R.string.pin_title_delete)
			}
			PinState.LOGIN -> {
				binding.tvTitle.visibility = View.GONE
			}
		}
	}

	private fun initListeners() {
		binding.ivRemoveSymbol.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			securityViewModel.pinCode = removeLastSymbol(securityViewModel.pinCode)
			pinAdapter.repaintElements(securityViewModel.pinCode.length)
		}
		binding.tvNum0.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(0)
		}
		binding.tvNum1.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(1)
		}
		binding.tvNum2.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(2)
		}
		binding.tvNum3.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(3)
		}
		binding.tvNum4.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(4)
		}
		binding.tvNum5.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(5)
		}
		binding.tvNum6.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(6)
		}
		binding.tvNum7.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(7)
		}
		binding.tvNum8.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(8)
		}
		binding.tvNum9.setOnClickListener {
			it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.anim_btn_click))
			addNumberToPinCode(9)
		}
	}

	private fun removeLastSymbol(curPinCode: String): String {
		if (curPinCode.isNotEmpty())
			return curPinCode.substring(0, curPinCode.length - 1)
		return curPinCode
	}

	private fun addNumberToPinCode(number: Int) {
		if (securityViewModel.pinCode.length < PIN_SIZE) {
			securityViewModel.pinCode += number.toString()
			pinAdapter.repaintElements(securityViewModel.pinCode.length)
		}
		if (securityViewModel.pinCode.length == PIN_SIZE)
			handlePin()
	}

	private fun handlePin() {
		when (securityViewModel.currentPinState) {
			PinState.CREATE -> handlePinCreating()
			PinState.CONFIRM -> handlePinConfirmation()
			PinState.DELETE -> handlePinDeleting()
			PinState.LOGIN -> handlePinChecking()
		}
	}

	private fun handlePinCreating() {
		securityViewModel.confirmPin = securityViewModel.pinCode
		securityViewModel.currentPinState = PinState.CONFIRM
		clearPin()
		configureViews()
	}

	private fun handlePinConfirmation() {
		if (securityViewModel.confirmPin == securityViewModel.pinCode) {
			securityViewModel.currentPinState = PinState.DELETE
			val encryptedPin: String? = EncryptionUtils.encrypt(requireContext(), securityViewModel.pinCode)
			BaseApp.getSharedPref().edit().putString(Constants.SP_PIN_ENCODED, encryptedPin).apply()
			UtilsUI.makeCoolToast(resources.getString(R.string.pin_created), ToastStates.SUCCESS)
			clearPin()
			parentFragmentManager.popBackStack()
		} else
			handleInvalidPin()
	}

	private fun handlePinDeleting() {
		if (isPinCorrect()) {
			securityViewModel.currentPinState = PinState.CREATE
			BaseApp.getSharedPref().edit().remove(Constants.SP_PIN_ENCODED).apply()
			UtilsUI.makeCoolToast(resources.getString(R.string.pin_deleted), ToastStates.SUCCESS)
			clearPin()
			parentFragmentManager.popBackStack()
		} else
			handleInvalidPin()
	}

	private fun handlePinChecking() {
		if (isPinCorrect()) {
			securityViewModel.currentPinState = PinState.DELETE
			clearPin()
			parentFragmentManager.popBackStack()
			(requireActivity() as? MainActivityViewPagerManager)?.configureActivityForAuthorizedUser()
		} else
			handleInvalidPin()
	}

	private fun isPinCorrect(): Boolean {
		return EncryptionUtils.decrypt(requireContext(), BaseApp.getSharedPref().getString(Constants.SP_PIN_ENCODED, "")) == securityViewModel.pinCode
	}

	private fun handleInvalidPin() {
		clearPin()
		securityViewModel.attemptsLeft--
		when (securityViewModel.attemptsLeft) {
			0 -> {
				securityViewModel.resetData()
				(requireActivity() as? MainActivityViewPagerManager)?.configureActivityForAuthorizedUser()
			}
			1 -> UtilsUI.makeCoolToast(resources.getString(R.string.pin_1_try_left), ToastStates.ERROR)
			2 -> UtilsUI.makeCoolToast(resources.getString(R.string.pin_2_tries_left), ToastStates.ERROR)
			else -> UtilsUI.makeCoolToast(resources.getString(R.string.pin_not_valid), ToastStates.ERROR)
		}
	}

	private fun clearPin() {
		securityViewModel.pinCode = ""
		pinAdapter.repaintElements(securityViewModel.pinCode.length)
	}

	companion object {
		const val PIN_SIZE = 5
		private const val PIN_ATTEMPTS = 10
	}
}