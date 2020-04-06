package com.example.twidy.ui.chats

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twidy.ChatsAdapter
import com.example.twidy.MainActivity
import com.example.twidy.R

class ChatsFragment : Fragment() {

    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var chatsRecyclerView: RecyclerView
    private lateinit var activity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatsViewModel =
                ViewModelProviders.of(this).get(ChatsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chats, container, false)
        chatsRecyclerView = root.findViewById(R.id.chats_view)
        activity = getActivity() as MainActivity
        setHasOptionsMenu(true)
        chatsViewModel.resultConfirmData.value = activity.intent?.extras?.getParcelable("authConfirmData")
        chatsViewModel.loadChats()
        chatsViewModel.listLiveData.observe(this, Observer {
            var chatsAdapter = ChatsAdapter(it,activity)
            chatsRecyclerView.adapter = chatsAdapter
            chatsRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            if(activity.isInEditModeChats&&chatsAdapter.itemCount==0) {
                chatsAdapter.changeMode()
            }
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if(!activity.isInEditModeChats) {
            inflater?.inflate(R.menu.menu_chats, menu)
            val searchView: SearchView =
                MenuItemCompat.getActionView(menu?.findItem(R.id.search_bar)) as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {

                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    chatsViewModel.search(p0.toString())
                    return true
                }

            })
        }
        else{
            inflater?.inflate(R.menu.menu_chats_editmode, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(!activity.isInEditModeChats) {
            when (item?.itemId) {
                R.id.search_bar -> {
                    MenuItemCompat.setOnActionExpandListener(
                        item,
                        object : MenuItem.OnActionExpandListener,
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
        }
        else{
            when (item?.itemId) {
                android.R.id.home -> (chatsRecyclerView.adapter as ChatsAdapter).changeMode()
                R.id.delete -> chatsViewModel.delete()
                R.id.archive -> chatsViewModel.archive()
                R.id.checkAll -> chatsViewModel.checkAll()
            }
        }
        return true
    }
}