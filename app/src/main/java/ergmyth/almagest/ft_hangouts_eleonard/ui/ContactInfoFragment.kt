package ergmyth.almagest.ft_hangouts_eleonard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ergmyth.almagest.ft_hangouts_eleonard.databinding.FragmentContactInfoBinding

class ContactInfoFragment: Fragment() {
    private lateinit var binding: FragmentContactInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContactInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViews()
        initListenters()
    }

    private fun initListenters() {
        TODO("Not yet implemented")
    }

    private fun configureViews() {
        TODO("Not yet implemented")
    }
}