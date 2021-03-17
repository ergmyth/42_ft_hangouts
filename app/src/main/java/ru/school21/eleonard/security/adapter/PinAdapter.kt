package ru.school21.eleonard.security.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.ItemPinBinding
import ru.school21.eleonard.security.ui.PinFragment.Companion.PIN_SIZE

class PinAdapter(
	var coloredElementsCount: Int
) : RecyclerView.Adapter<PinAdapter.PinViewHolder>() {

	override fun onBindViewHolder(holder: PinViewHolder, position: Int) {
		if (position < coloredElementsCount)
			holder.ivPin.setColorFilter(ContextCompat.getColor(holder.ivPin.context, R.color.colorPrimary))
		else
			holder.ivPin.setColorFilter(ContextCompat.getColor(holder.ivPin.context, R.color.cadet_blue))
	}

	fun repaintElements(coloredSize: Int) {
		this.coloredElementsCount = coloredSize
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PinViewHolder {
		val binding = ItemPinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return PinViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return PIN_SIZE
	}

	class PinViewHolder(binding: ItemPinBinding) : RecyclerView.ViewHolder(binding.root) {
		val ivPin: ImageView = binding.ivPin
	}
}

