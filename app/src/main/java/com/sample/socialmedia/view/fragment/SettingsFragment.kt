package com.sample.socialmedia.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.sample.socialmedia.BuildConfig
import com.sample.socialmedia.R
import com.sample.socialmedia.util.Constants
import com.sample.socialmedia.util.Util
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nightModeFlags = requireActivity().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                switch_dark_mode.isChecked=true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                switch_dark_mode.isChecked=false
            }
        }

        txt_version.text=  BuildConfig.VERSION_NAME

        switch_dark_mode.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            setNightMode(isChecked)
        })

        about_us_container.setOnClickListener {
            Util.openCustomTab(requireActivity(), Constants.ABOUT_US_URL, true)
        }

        contact_us_container.setOnClickListener {
            Util.openCustomTab(requireActivity(), Constants.CONTACT_US_URL, true)
        }

    }

    //to handle night mode
    private fun setNightMode(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }

    }

}