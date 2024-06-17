package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.preference.EditTextPreference
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import edu.oregonstate.cs492.eventsearchwithnavui.R

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var homePageZip: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        /*
         * Hook a custom summary provider up to the "language" setting.  This custom summery
         * provider displays "Not set" if no languages are selected, and otherwise it uses a
         * "quantity string" to display an appropriate string to represent the number of languages
         * selected, e.g. "1 language" or "3 languages" (note the plural "languages" required when
         * multiple languages are selected).  For more on quantity strings in Android, see these
         * docs:
         *
         * https://developer.android.com/guide/topics/resources/string-resource#Plurals
         */
        // Enter user preferences zip code here
        //97221 - Portland
        //97301

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val preferenceZipCode = findPreference<EditTextPreference>("@string/pref_zip_key")
        val zipCodeValue = preferenceZipCode?.text
//        homePageZip = view.findViewById(R.id.et_search_box)
//        homePageZip.text = preferenceZipCode
        val textInputEditText: TextInputEditText? = view?.findViewById(R.id.et_search_box)
        textInputEditText?.setText(zipCodeValue)

        Log.d("HelpHelp", "zipCodeValue: $zipCodeValue")
        Log.d("HelpHelp", "preferenceZipCode: $preferenceZipCode")
        Log.d("HelpHelp", "textInputEditText: $textInputEditText")


        //onlistener if the preference is updated
//        preferenceZipCode?.setOnPreferenceChangeListener{ preference, newValue ->
//            //if so, update the zip?
//            true
//        }

    }
}