package com.example.tester.fragment

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tester.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private val db = Firebase.firestore

    val DocID : String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment*
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_Cancel.setOnClickListener {
            var action = AddFragmentDirections.actionAddFragmentToMainFragment()
            findNavController().navigate(action)
        }

        btn_Add.setOnClickListener {
            var name = add_name.text.trim().toString()
            var age = add_age.text.trim().toString()

            val user = hashMapOf(
                "name" to name,
                "age" to age,
            )

            db.collection("Users").document(name)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(context,"Successful Upload!" , Toast.LENGTH_SHORT).show()
                    var action = AddFragmentDirections.actionAddFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Failed Upload!" , Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onPause() {
        super.onPause()
    }
}