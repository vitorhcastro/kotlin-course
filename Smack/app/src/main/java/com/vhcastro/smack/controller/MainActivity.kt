package com.vhcastro.smack.controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vhcastro.smack.R
import com.vhcastro.smack.adapters.MessageAdapter
import com.vhcastro.smack.model.Channel
import com.vhcastro.smack.model.Message
import com.vhcastro.smack.services.AuthService
import com.vhcastro.smack.services.MessageService
import com.vhcastro.smack.services.UserDataService
import com.vhcastro.smack.utilities.BROADCAST_USER_DATA_CHANGED
import com.vhcastro.smack.utilities.SOCKET_URL
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    val socket = IO.socket(SOCKET_URL)
    lateinit var channelAdapter: ArrayAdapter<Channel>
    lateinit var messageAdapter: MessageAdapter
    var selectedChannel : Channel? = null

    private fun setupAdapters() {
        channelAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MessageService.channels)
        channel_list.adapter = channelAdapter

        messageAdapter = MessageAdapter(this, MessageService.messages)
        messageListView.adapter = messageAdapter
        val layoutManager = LinearLayoutManager(this)
        messageListView.layoutManager = layoutManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        socket.connect()
        socket.on("channelCreated", onNewChannel)
        socket.on("messageCreated", onNewMessage)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setupAdapters()

        channel_list.setOnItemClickListener { _, _, position, _ ->
            selectedChannel = MessageService.channels[position]
            drawer_layout.closeDrawer(GravityCompat.START)
            updateWithChannel()
        }

        if(App.prefs.isLoggedIn){
            AuthService.findUserByEmail(this){}
        }
    }

    override fun onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangedReceiver, IntentFilter(BROADCAST_USER_DATA_CHANGED))

        super.onResume()
    }

    override fun onDestroy() {
        socket.disconnect()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(userDataChangedReceiver)
        super.onDestroy()
    }

    private val userDataChangedReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(App.prefs.isLoggedIn){
                usernameNavHeader.text = UserDataService.name
                userEmailNavHeader.text = UserDataService.email
                val resourceId = resources.getIdentifier(UserDataService.avatarName, "drawable", packageName)
                userImageNavHeader.setImageResource(resourceId)
                userImageNavHeader.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                loginBtnNavHeader.text = "Logout"
                sendMessageBtn.isClickable = true
                sendMessageBtn.visibility = View.VISIBLE
                messageTextField.visibility = View.VISIBLE

                MessageService.getChannels { complete ->
                    if (complete) {
                        if(MessageService.channels.any()){
                            selectedChannel = MessageService.channels.first()
                            channelAdapter.notifyDataSetChanged()
                            updateWithChannel()
                        }
                    }
                }
            } else {
                loginBtnNavHeader.text = "Login"
                usernameNavHeader.text = ""
                userEmailNavHeader.text = ""
                userImageNavHeader.setImageResource(R.drawable.profiledefault)
                userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
                sendMessageBtn.isClickable = false
                sendMessageBtn.visibility = View.INVISIBLE
                messageTextField.visibility = View.INVISIBLE
                selectedChannel = null
                mainChannelName.text = "Please Log In"
            }
        }
    }

    fun updateWithChannel(){
        MessageService.clearMessages()
        mainChannelName.text = "#${selectedChannel?.name}"
        if(selectedChannel != null){
            MessageService.getMessages(selectedChannel!!.id){ complete ->
                if (complete){
                    messageAdapter.notifyDataSetChanged()
                    if (messageAdapter.itemCount > 0){
                        messageListView.smoothScrollToPosition(messageAdapter.itemCount - 1)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginBtnNavClicked(view: View){
        if(App.prefs.isLoggedIn){
            UserDataService.logout(this)
            channelAdapter.notifyDataSetChanged()
            messageAdapter.notifyDataSetChanged()
        } else {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    fun addChannelClicked(view: View){
        if (App.prefs.isLoggedIn) {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog, null)

            builder.setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    val nameTextField = dialogView.findViewById<EditText>(R.id.addChannelNameTxt)
                    val descTextField = dialogView.findViewById<EditText>(R.id.addChannelDescTxt)
                    val channelName = nameTextField.text.toString()
                    val channelDesc = descTextField.text.toString()

                    socket.emit("newChannel", channelName, channelDesc)
                }
                .setNegativeButton("Cancel") { _, _ ->
                }
                .show()
        }
    }

    private val onNewChannel = Emitter.Listener { args ->
        runOnUiThread {
            if(App.prefs.isLoggedIn) {
                val channelName = args[0] as String
                val channelDescription = args[1] as String
                val channelId = args[2] as String

                val newChannel = Channel(channelName, channelDescription, channelId)
                MessageService.channels.add(newChannel)
                channelAdapter.notifyDataSetChanged()
            }
        }
    }

    private val onNewMessage = Emitter.Listener { args ->
        runOnUiThread {
            if(App.prefs.isLoggedIn){
                val channelId = args[2] as String
                if(channelId == selectedChannel?.id){
                    val message = args[0] as String
                    val username = args[3] as String
                    val userAvatar = args[4] as String
                    val userAvatarColor = args[5] as String
                    val id = args[6] as String
                    val timeStamp = args[7] as String

                    val newMessage = Message(message, username, channelId, userAvatar, userAvatarColor, id, timeStamp)
                    MessageService.messages.add(newMessage)
                    messageAdapter.notifyDataSetChanged()
                    messageListView.smoothScrollToPosition(messageAdapter.itemCount - 1)
                }
            }
        }
    }

    fun sendMessageBtnClicked(view: View){
        if(App.prefs.isLoggedIn && messageTextField.text.isNotEmpty() && selectedChannel != null){
            val userId = UserDataService.id
            val channel = selectedChannel!!.id
            val message = messageTextField.text.toString()
            socket.emit("newMessage", message, userId, channel, UserDataService.name, UserDataService.avatarName, UserDataService.avatarColor)
            messageTextField.text.clear()
        }
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
