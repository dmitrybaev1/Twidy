package com.example.twidy.ui.chats

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twidy.MainActivity
import com.example.twidy.R

class ChatsFragment : Fragment() {

    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var chatsRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatsViewModel =
                ViewModelProviders.of(this).get(ChatsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chats, container, false)
        chatsRecyclerView = root.findViewById(R.id.chats_view)
        val toolbar = (activity as MainActivity).toolbar
        toolbar.title = resources.getString(R.string.title_chats)
        setHasOptionsMenu(true)
        chatsViewModel.resultConfirmData.value = activity?.intent?.extras?.getParcelable("authConfirmData")
        chatsViewModel.loadChats()
        chatsViewModel.chatsAdapter.observe(this, Observer {
            chatsRecyclerView.adapter = it
            chatsRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_chats,menu)
        val searchView: SearchView = MenuItemCompat.getActionView(menu?.findItem(R.id.search_bar)) as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                chatsViewModel.search(p0.toString())
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.search_bar -> {
                MenuItemCompat.setOnActionExpandListener(item, object: MenuItem.OnActionExpandListener,
                    MenuItemCompat.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                        chatsViewModel.toSourceList()
                        return true
                    }

                })
            }
        }
        return true
    }
}