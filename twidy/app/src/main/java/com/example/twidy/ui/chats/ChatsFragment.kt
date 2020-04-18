package com.example.twidy.ui.chats

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twidy.*
import com.example.twidy.databinding.FavoriteLayoutBinding
import com.example.twidy.ui.main.MainActivity
import com.example.twidy.ui.chats.adapters.ChatsAdapter
import com.example.twidy.ui.chats.adapters.FavoriteSection
import com.example.twidy.ui.chats.items.FavoriteItem
import com.example.twidy.ui.chats.vm.ChatsViewModel
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

class ChatsFragment : Fragment() {

    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var chatsRecyclerView: RecyclerView
    private lateinit var activity: MainActivity
    lateinit var layout: LinearLayout
    private lateinit var popupWindow: PopupWindow
    private lateinit var popupRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatsViewModel =
                ViewModelProviders.of(this).get(ChatsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chats, container, false)
        layout = root.findViewById(R.id.layout_chats)
        chatsRecyclerView = root.findViewById(R.id.chats_view)
        activity = getActivity() as MainActivity
        setHasOptionsMenu(true)
        chatsViewModel.resultConfirmData.value = activity.intent?.extras?.getParcelable("authConfirmData")
        //если есть интернет, нужно подгружать чаты периодически и сравнивать изменения с локальной версией, если есть изменения, то обновлять
        //если в оффлайне, то загрузить один раз локальную копию
        chatsViewModel.getChats()
        chatsViewModel.chatsListLiveData.observe(viewLifecycleOwner, Observer {
            var chatsAdapter = ChatsAdapter(
                it,
                activity,
                chatsViewModel
            )
            chatsRecyclerView.adapter = chatsAdapter
            chatsRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
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
            var adapter = SectionedRecyclerViewAdapter()
            val distinctList = it.distinctBy { item -> item.personName[0] }
            for(titleItem in distinctList) {
                var title = titleItem.personName[0]
                val subList = it.filter { item -> item.personName[0] == title} as ArrayList<FavoriteItem>
                adapter.addSection(
                    FavoriteSection(
                        subList,
                        title.toString()
                    )
                )
            }
            popupRecyclerView.adapter = adapter
            popupRecyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        })
        chatsViewModel.closePopupLiveData.observe(viewLifecycleOwner, Observer {
            popupWindow.dismiss()
        })
        return root
    }

    fun changeToolbarMode(adapter: ChatsAdapter){
        if (!chatsViewModel.isToolbarInEditMode) {
            chatsViewModel.isToolbarInEditMode = true
            activity.navView.visibility = View.GONE
            activity.setSupportActionBar(activity.toolbar)
            activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            adapter.setItemsCheckedMode()
        } else {
            chatsViewModel.isToolbarInEditMode = false
            activity.navView.visibility = View.VISIBLE
            activity.setSupportActionBar(activity.toolbar)
            activity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
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
            var inflater = LayoutInflater.from(activity)
            val binding: FavoriteLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.favorite_layout,null,false)
            binding.lifecycleOwner = this@ChatsFragment
            binding.chats = chatsViewModel
            var view = binding.root
            var searchView: SearchView = view.findViewById(R.id.search_view_popup)
            popupRecyclerView = view.findViewById(R.id.rv)
            popupWindow = PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true)
            popupWindow.showAtLocation(layout,Gravity.TOP,0,0)
            chatsViewModel.getFavorite("all")
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    chatsViewModel.searchFavorite(newText.toString())
                    return true
                }

            })
            searchView.setOnCloseListener {
                Toast.makeText(activity,"close",Toast.LENGTH_SHORT).show()
                true
            }
        }
    }
}