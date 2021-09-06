package com.example.tester.fragment

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tester.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_data_list.*
import kotlinx.android.synthetic.main.item_data_list.view.*

class MainFragment : Fragment() {

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Query the users collection
        val query = db.collection("Users")

        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java).setLifecycleOwner(this).build()

        class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(name: String, age: String) {
                itemView.txt_name.text = name
                itemView.txt_age.text = age
            }
        }

        val adapter = object: FirestoreRecyclerAdapter<User, UserHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
                val item_view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_list, parent, false)
                return UserHolder(item_view)
            }

            override fun onBindViewHolder(holder: UserHolder, position: Int, model: User) {
                holder.bind(model.name,model.age)

                holder.itemView.setOnClickListener {
                    var action = MainFragmentDirections.actionMainFragmentToUpdateDeleteFragment(model.name,model.age)
                    findNavController().navigate(action)
                }
            }
        }


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_user.layoutManager = layoutManager
        recyclerview_user.adapter = adapter


        btn_to_Add.setOnClickListener {
            var action = MainFragmentDirections.actionMainFragmentToAddFragment()
            findNavController().navigate(action)
        }
    }
}