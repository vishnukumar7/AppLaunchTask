package com.app.applaunchtask.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.applaunchtask.R
import com.app.applaunchtask.databinding.FragmentHomeBinding
import com.app.applaunchtask.databinding.UserListItemBinding
import com.app.applaunchtask.model.User


class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    var userList = ArrayList<User>()
    lateinit var adapter: UserListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclcerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclcerView.setHasFixedSize(false)
        adapter = UserListAdapter(requireActivity(),userList)
        binding.recyclcerView.adapter = adapter
        binding.add.setOnClickListener { (requireActivity() as HomeActivity).switch(UserFormFragment())  }
        (requireActivity() as HomeActivity).userViewModel.userList.observe(viewLifecycleOwner) { list ->
            list?.let {
                userList.clear()
                userList.addAll(it)
                if(userList.size==0)
                    binding.nullLay.visibility=View.VISIBLE
                else binding.nullLay.visibility=View.GONE
                Log.d(TAG, "onViewCreated: " + userList.size)
                adapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper=ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclcerView)


    }

    class UserListAdapter(private var activity: Activity, private var userList: List<User>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val userListItemBinding = DataBindingUtil.inflate<UserListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.user_list_item,
                parent,
                false
            )
            return UserListViewHolder(userListItemBinding)
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is UserListViewHolder) {
                holder.itemBinding.user = userList[position]
                holder.itemBinding.mainLay.setOnClickListener {
                    (activity as HomeActivity).switch(WeatherScreenFragment())
                }
            }
        }


        override fun getItemCount(): Int {
            return userList.size
        }

        class UserListViewHolder(view: UserListItemBinding) : RecyclerView.ViewHolder(view.root) {
            val itemBinding = view
        }

    }

    val simpleItemTouchCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                (requireActivity() as HomeActivity).userViewModel.delete(userList.removeAt(position))
            }

        }


}