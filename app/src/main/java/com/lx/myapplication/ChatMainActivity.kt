package com.lx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lx.myapplication.CommonUtil.userName
import com.lx.myapplication.databinding.ActivityChatMainBinding
import com.lx.myapplication.databinding.DialogLoginBinding
import kotlinx.coroutines.Job

class ChatMainActivity: AppCompatActivity() {
    companion object {
        const val TAG = "ChatMainActivity"
    }

    private var viewModel: ChatMainViewModel = ChatMainViewModel()
    lateinit var binding: ActivityChatMainBinding
    private lateinit var fetchJob: Job

    lateinit var chatListAdapter: ChatListAdapter

    var boolLogin: Boolean = false  // 로그인 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observeData()
    }


    private fun initView() {

        // Toolbar 사용 설정
        setSupportActionBar(binding.tbTop)
        binding.tbTop.title = ""

        // Toolbar 의 navigation icon 클릭 이벤트 시 뒤로가기
        binding.tbTop.setNavigationOnClickListener {
            onBackPressed()
        }

        // 로그인 여부 설정
        boolLogin = !userName.isNullOrEmpty()
        if(boolLogin) {
            binding.tvToolbarUser.text = "($userName)"
        }

        // RecyclerView 설정
        chatListAdapter = ChatListAdapter()
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@ChatMainActivity)
            adapter = chatListAdapter
        }
        binding.refreshLayout.isEnabled = false

        // 메세지 보내기
        binding.btnSend.setOnClickListener {
            if(userName.isNullOrEmpty()) {
                Toast.makeText(this, "Press topright button and make nickname.", Toast.LENGTH_LONG).show()
            }
            if(binding.etInput.text.isNullOrEmpty()) {
                Toast.makeText(this, "Write what you want!.", Toast.LENGTH_LONG).show()
            } else {
                viewModel.fetchWriteData(userName, binding.etInput.text.toString())
            }
        }

        // 데이터 통신하기
        fetchJob = viewModel.fetchReadData()

    }


    /*
    * Toolbar 의 OptionsMenu 가 최초로 생상될 때 호출
    * Toolbar 에 menu_chat.xml 을 inflate 함
    */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    /*
    * Toolbar 의 OptionsMenu 가 화면에 보여질 때마다 호출
    * */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        // item(0):login, item(1):logout
        if(boolLogin) {  //로그인 O -> 로그인 안보이게, 로그아웃 보이게

            menu?.getItem(0)?.isVisible = false  //login
            menu?.getItem(1)?.isVisible = true  //logout
        } else {  //로그인 X -> 로그인 보이게, 로그아웃 안보이게
            menu?.getItem(0)?.isVisible = true
            menu?.getItem(1)?.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    /*
    * Toolbar 의 OptionsMenu 아이템이 클릭될 때 호출
    * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_login -> {
                showLoginDialog()
                true
            }
            R.id.menu_logout -> {
                userName = ""
                initView()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    /*
    * viewModel.chatMainLiveData 관찰하는 메소드
    * */
    private fun observeData() = viewModel.chatMainLiveData.observe(this) {
        viewModel.chatMainLiveData.observe(this) {

            when(it) {
                is ChatMainState.UnInitialized -> initView()
                is ChatMainState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE  // 프로그래스 바 보여주기(로딩중)
                }
                is ChatMainState.Success -> {
                    handleSuccessState(it)
                }
                is ChatMainState.Error -> {
                    handleErrorState()
                }
                else -> {}
            }
        }
    }

    private fun handleSuccessState(state: ChatMainState.Success) {

        binding.progressBar.visibility = View.GONE //로딩 치우기?
        if(state.chatList.isEmpty()) {
            binding.tvEmptyList.visibility = View.VISIBLE
        } else {
            binding.tvEmptyList.visibility = View.GONE
            chatListAdapter.setChatItems(state.chatList)
        }
        // 키보드 내리기
        val inputManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.etInput.windowToken, 0)
        binding.recyclerView.scrollToPosition(chatListAdapter.itemCount -1)  //스크롤 맨 아래로
        binding.etInput.text.clear()
    }
    private fun handleErrorState() {
        binding.tvEmptyList.visibility = View.VISIBLE
        Toast.makeText(this, "Error detected.", Toast.LENGTH_SHORT).show()
    }


    /*
    * 로그인하는 Dialog 띄우기
    * */
    private fun showLoginDialog() {

        var dialogViewBinding = DialogLoginBinding.inflate(layoutInflater)
        var builder = AlertDialog.Builder(this)
        var dialog = builder.setView(dialogViewBinding.root).create()

        dialogViewBinding.btnConfirm.setOnClickListener {
            userName = dialogViewBinding.etName.text.toString()
            initView()
            dialog.dismiss()
        }
        dialogViewBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}