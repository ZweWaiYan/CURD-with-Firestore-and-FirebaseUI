package com.example.tester.fragment

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tester.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.WriteResult
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update_delete_.*


class UpdateDelete_Fragment : Fragment() {

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_delete_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var Message = arguments?.let { UpdateDelete_FragmentArgs.fromBundle(it) }
        var name : String = Message!!.name.toString()
        var age : String = Message.age.toString()

        edt_name.setText(name)
        edt_age.setText(age)

        btn_update.setOnClickListener {
            var edtName = edt_name.text.trim().toString()
            var edtAge = edt_age.text.trim().toString()

            val user = hashMapOf(
                "name" to edtName,
                "age" to edtAge,
            )

            db.collection("Users").document(name).delete().addOnSuccessListener {}.addOnFailureListener {}

            db.collection("Users").document(edtName)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(context,"Successful Update!" , Toast.LENGTH_SHORT).show()
                    var action = UpdateDelete_FragmentDirections.actionUpdateDeleteFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Failed Update!" , Toast.LENGTH_SHORT).show()
                }
        }

        btn_delete.setOnClickListener {
            db.collection("Users").document(name).delete().addOnSuccessListener {
                Toast.makeText(context,"Delete Successful!",Toast.LENGTH_SHORT).show()
                var action = UpdateDelete_FragmentDirections.actionUpdateDeleteFragmentToMainFragment()
                findNavController().navigate(action)
            }
                .addOnFailureListener {
                    Toast.makeText(context,"Delete Failed!",Toast.LENGTH_SHORT).show()
                }
        }

    }

}