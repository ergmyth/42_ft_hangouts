package ru.school21.eleonard.helpers.toolbar

enum class ToolbarStates(
	val isBackBtnVisible: Boolean,
	val isSearchVisible: Boolean,
	val isFilterVisible: Boolean,
	val isSortVisible: Boolean,
) {
	STATE_CONTACTS_MAIN(
		isBackBtnVisible = false,
		isSearchVisible = true,
		isFilterVisible = true,
		isSortVisible = true,
	),
	STATE_CONTACT_INFO(
		isBackBtnVisible = true,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
	),
	STATE_SCHOOL_MAIN(
		isBackBtnVisible = false,
		isSearchVisible = true,
		isFilterVisible = false,
		isSortVisible = false,
	),
	STATE_GRAPHICS_MAIN(
		isBackBtnVisible = false,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
	),
	STATE_OTHER_MAIN (
		isBackBtnVisible = false,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
	),
	STATE_PIN (
		isBackBtnVisible = true,
		isSearchVisible = false,
		isFilterVisible = false,
		isSortVisible = false,
	),
}