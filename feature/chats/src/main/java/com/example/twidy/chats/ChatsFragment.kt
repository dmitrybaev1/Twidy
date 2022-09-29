package com.example.twidy.chats

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twidy.*
import com.example.twidy.databinding.FavoriteLayoutBinding
import com.example.twidy.MainActivity
import com.example.twidy.chats.adapters.ChatsAdapter
import com.example.twidy.chats.adapters.FavoriteSection
import com.example.twidy.chats.entities.FavoriteItem
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

class ChatsFragment : Fragment() {
    private val chatsViewModel: ChatsViewModel by viewModels()
    private lateinit var chatsRecyclerView: RecyclerView
    private lateinit var activity: MainActivity
    lateinit var layout: LinearLayout
    private lateinit var popupWindow: PopupWindow
    private lateinit var popupRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(chatsViewModel)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity = getActivity() as MainActivity
        val root = inflater.inflate(R.layout.fragment_chats, container, false)
        layout = root.findViewById(R.id.layout_chats)
        chatsRecyclerView = root.findViewById(R.id.chats_view)
        setHasOptionsMenu(true)
        activity.authData?.let {
            chatsViewModel.resultConfirmData = it
        }
        chatsViewModel.fetchChats()


        chatsViewModel.chatsListLiveData.observe(viewLifecycleOwner, Observer {
            val chatsAdapter = ChatsAdapter(it, activity, chatsViewModel)
            chatsRecyclerView.apply {
                adapter = chatsAdapter
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            }
            if(chatsViewModel.isToolbarInEditMode&&chatsAdapter.itemCount==0) {
                changeToolbarMode(chatsAdapter)
            }
        })
        chatsViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        })
        chatsViewModel.apiError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        })
        chatsViewModel.favoriteListLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = SectionedRecyclerViewAdapter()
            val distinctList = it.distinctBy { item -> item.personName[0] }
            for(titleItem in distinctList) {
                val title = titleItem.personName[0]
                val subList = it.filter { item -> item.personName[0] == title} as ArrayList<FavoriteItem>
                adapter.addSection(FavoriteSection( subList, title.toString()))
            }
            popupRecyclerView.apply {
                setAdapter(adapter)
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
        })
        chatsViewModel.closePopupLiveData.observe(viewLifecycleOwner, Observer {
            popupWindow.dismiss()
        })
        return root
    }


    private fun changeToolbarMode(adapter: ChatsAdapter){
        if (!chatsViewModel.isToolbarInEditMode) {
            chatsViewModel.isToolbarInEditMode = true
            activity.apply {
                navView.visibility = View.GONE
                setSupportActionBar(activity.toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            adapter.setItemsCheckedMode()
        } else {
            chatsViewModel.isToolbarInEditMode = false
            activity.apply {
                navView.visibility = View.VISIBLE
                setSupportActionBar(activity.toolbar)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            }
            adapter.unsetItemsCheckedMode()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if(!chatsViewModel.isToolbarInEditMode) {
            inflater.inflate(R.menu.menu_chats, menu)
            val searchView: SearchView =
                MenuItemCompat.getActionView(menu.findItem(R.id.search_bar)) as SearchView
            val editItem = menu.getItem(0)
            val writeItem = menu.getItem(1)
            val searchItem = menu.getItem(2)
            val editButton = editItem.actionView.findViewById<TextView>(R.id.edit_button)
            editButton.setOnClickListener {
                val adapter = (chatsRecyclerView.adapter as ChatsAdapter)
                if(adapter.itemCount>0)
                    changeToolbarMode(adapter)
            }
            /*editItem.setOnMenuItemClickListener {
                val adapter = (chatsRecyclerView.adapter as ChatsAdapter)
                if(adapter.itemCount>0)
                    adapter.changeMode()
                true
            }*/
            writeItem.setOnMenuItemClickListener(Popup())
            searchItem.setOnMenuItemClickListener {
                MenuItemCompat.setOnActionExpandListener(
                    it,
                    object : MenuItem.OnActionExpandListener,
                        MenuItemCompat.OnActionExpandListener {
                        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                            activity.navView.visibility = View.GONE
                            editItem.isVisible = false
                            writeItem.isVisible = false
                            return true
                        }

                        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                            chatsViewModel.toSourceListChats()
                            activity.navView.visibility = View.VISIBLE
                            editItem.isVisible=true
                            writeItem.isVisible=true
                            return true
                        }

                    })
                true
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    chatsViewModel.searchChats(p0.toString())
                    return true
                }

            })
        }
        else{
            inflater.inflate(R.menu.menu_chats_editmode, menu)
            val archiveItem = menu.getItem(0)
            val checkAllItem = menu.getItem(1)
            val editButton = archiveItem.actionView.findViewById<TextView>(R.id.archive_button)
            editButton.setOnClickListener {
                chatsViewModel.archiveChats()
                changeToolbarMode(chatsRecyclerView.adapter as ChatsAdapter)
            }
            /*archiveItem.setOnMenuItemClickListener {
                chatsViewModel.archiveChats()
                true
            }*/
            checkAllItem.setOnMenuItemClickListener {
                chatsViewModel.checkAllChats()
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> changeToolbarMode(chatsRecyclerView.adapter as ChatsAdapter)
        }
        return true
    }
    inner class Popup : MenuItem.OnMenuItemClickListener{
        override fun onMenuItemClick(p0: MenuItem?): Boolean {
            init()
            return true
        }
        private fun init(){
            val inflater = LayoutInflater.from(activity)
            val binding: FavoriteLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.favorite_layout,null,false)
            binding.lifecycleOwner = this@ChatsFragment
            binding.chats = chatsViewModel
            val view = binding.root
            val searchView: SearchView = view.findViewById(R.id.search_view_popup)
            popupRecyclerView = view.findViewById(R.id.rv)
            popupWindow = PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true)
            popupWindow.showAtLocation(layout,Gravity.TOP,0,0)
            chatsViewModel.fetchFavorites(FavoritesType.ALL)
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    chatsViewModel.searchFavorite(newText.toString())
                    return true
                }

            })
        }
    }
}