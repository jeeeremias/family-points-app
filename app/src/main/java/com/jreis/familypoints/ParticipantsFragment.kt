package com.jreis.familypoints

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.jreis.familypoints.adapter.UserAdapter
import com.jreis.familypoints.dto.User
import kotlinx.android.synthetic.main.fragment_participants.*

private const val REQUESTER_USER = "requesterUser"

class ParticipantsFragment : Fragment() {
    private val database = FirebaseDatabase.getInstance().getReference("users")
    private var requesterId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            requesterId = it.getLong(REQUESTER_USER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_participants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(usersSnapshot: DataSnapshot) {
                val users = ArrayList<User>()
                for (userSnapshot in usersSnapshot.children) {
                    userSnapshot.getValue(User::class.java)?.let { users.add(it) }
                }
                recyclerView_participants.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = UserAdapter(users)
                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param requesterId Id of user logged and wont be included to the list of participants.
         * @return A new instance of fragment ParticipantsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(requesterId: Long) =
            ParticipantsFragment().apply {
                arguments = Bundle().apply {
                    putLong(REQUESTER_USER, requesterId)
                }
            }
    }
}
