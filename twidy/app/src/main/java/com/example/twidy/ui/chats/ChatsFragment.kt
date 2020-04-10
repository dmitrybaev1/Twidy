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
import com.example.twidy.databinding.PopupLayoutBinding

class ChatsFragment : Fragment() {

    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var chatsRecyclerView: RecyclerView
    private lateinit var activity: MainActivity
    lateinit var layout: LinearLayout
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

        chatsViewModel.chatsListLiveData.observe(this, Observer {
            var chatsAdapter = ChatsAdapter(it,activity)
            chatsRecyclerView.adapter = chatsAdapter
            chatsRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            if(activity.isInEditModeChats&&chatsAdapter.itemCount==0) {
                chatsAdapter.changeMode()
            }
        })
        chatsViewModel.error.observe(this, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })
        chatsViewModel.apiError.observe(this, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if(!activity.isInEditModeChats) {
            inflater?.inflate(R.menu.menu_chats, menu)
            val searchView: SearchView =
                MenuItemCompat.getActionView(menu?.findItem(R.id.search_bar)) as SearchView
            val editItem = menu?.getItem(0)
            val writeItem = menu?.getItem(1)
            val searchItem = menu?.getItem(2)
            editItem?.setOnMenuItemClickListener {
                val adapter = (chatsRecyclerView.adapter as ChatsAdapter)
                if(adapter.itemCount>0)
                    adapter.changeMode()
                true
            }
            writeItem?.setOnMenuItemClickListener(Popup())
            searchItem?.setOnMenuItemClickListener {
                MenuItemCompat.setOnActionExpandListener(
                    it,
                    object : MenuItem.OnActionExpandListener,
                        MenuItemCompat.OnActionExpandListener {
                        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                            activity.navView.visibility = View.GONE
                            editItem?.isVisible = false
                            writeItem?.isVisible = false
                            return true
                        }

                        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                            chatsViewModel.toSourceListChats()
                            activity.navView.visibility = View.VISIBLE
                            editItem?.isVisible=true
                            writeItem?.isVisible=true
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
            inflater?.inflate(R.menu.menu_chats_editmode, menu)
            val archiveItem = menu?.getItem(0)
            val checkAllItem = menu?.getItem(1)
            archiveItem?.setOnMenuItemClickListener {
                chatsViewModel.archiveChats()
                true
            }
            checkAllItem?.setOnMenuItemClickListener {
                chatsViewModel.checkAllChats()
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> (chatsRecyclerView.adapter as ChatsAdapter).changeMode()
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
            val binding: PopupLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.popup_layout,null,false)
            binding.lifecycleOwner = this@ChatsFragment
            binding.chats = chatsViewModel
            var view = binding.root
            var recyclerView: RecyclerView = view.findViewById(R.id.rv)
            var searchView: SearchView = view.findViewById(R.id.search_view_popup)
            var popupWindow = PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true)
            popupWindow.showAtLocation(layout,Gravity.CENTER,0,0)
            chatsViewModel.getFavorite("all")
            chatsViewModel.favoriteListLiveData.observe(this@ChatsFragment, Observer {
                recyclerView.adapter = FavoriteAdapter(it)
                recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            })
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