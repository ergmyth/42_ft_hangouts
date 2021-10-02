package ru.school21.eleonard.helpers.toolbar

import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.R

enum class ToolbarStates(
	val isBackBtnVisible: Boolean,
	val isSearchVisible: Boolean,
	val isFilterVisible: Boolean,
	val isSortVisible: Boolean,
	val title: String,
) {
	STATE_CONTACTS_MAIN(
		isBackBtnVisible = false,
		//todo
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.bnv_hangouts),
	),
	STATE_CONTACT_INFO(
		isBackBtnVisible = true,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.toolbar_title_contact_info_new),
	),
	STATE_CONTACT_INFO_CREATED(
		isBackBtnVisible = true,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.toolbar_title_contact_info),
	),
	STATE_SCHOOL_MAIN(
		isBackBtnVisible = false,
		isSearchVisible = true,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.bnv_companion),
	),
	STATE_GRAPHICS_MAIN(
		isBackBtnVisible = false,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.bnv_proteins),
	),
	STATE_OTHER_MAIN (
		isBackBtnVisible = false,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.bnv_other),
	),
	STATE_CHAT_MAIN (
		isBackBtnVisible = true,
		isSearchVisible = true,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.toolbar_title_chat),
	),
	STATE_PIN (
		isBackBtnVisible = true,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
		title = BaseApp.getInstance().getString(R.string.pin_toolbar_title),
	),
}