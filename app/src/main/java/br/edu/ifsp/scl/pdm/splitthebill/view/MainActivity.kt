package br.edu.ifsp.scl.pdm.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.utils.Modes
import br.edu.ifsp.scl.pdm.splitthebill.adapter.MembersAdapter
import br.edu.ifsp.scl.pdm.splitthebill.controller.MembersController
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Member
import br.edu.ifsp.scl.pdm.splitthebill.utils.Constants

class MainActivity : AppCompatActivity(), OnMemberClickListener {
    private lateinit var membersAdapter: MembersAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var splitARL: ActivityResultLauncher<Intent>

    private lateinit var memberARL: ActivityResultLauncher<Intent>

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    companion object{
        private val controller : MembersController = MembersController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        mockMembers()
        validateSplitVisibility()

        layoutManager = LinearLayoutManager(this)
        amb.memberListRv.layoutManager = layoutManager

        membersAdapter = MembersAdapter(controller.memberList, this)
        amb.memberListRv.adapter = membersAdapter

        memberARL = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                val data = result.data ?: return@registerForActivityResult
                val member = data.getParcelableExtra<Member>(Constants.MEMBER) ?: return@registerForActivityResult
                val returnMode = data.getParcelableExtra<Modes>(Constants.MODE) ?: return@registerForActivityResult
                if(returnMode == Modes.INSERT) insert(member)
                if(returnMode == Modes.UPDATE) update(member)
                if(returnMode == Modes.REMOVE) remove(member)
                validateSplitVisibility()
            }
        }

        splitARL = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                Toast.makeText(this, getString(R.string.split_done),Toast.LENGTH_LONG).show()
            }
        }

        amb.splitBtn.setOnClickListener{
            val intent = Intent(Constants.SPLIT_ACTIVITY)
            intent.putParcelableArrayListExtra(Constants.MEMBER_LIST, controller.memberList)
            splitARL.launch(intent)
        }
        amb.addBtn.setOnClickListener{
            val intent = Intent(Constants.MEMBER_DATA_ACTIVITY)
            val bundle = Bundle()
            bundle.putParcelable(Constants.MODE, Modes.INSERT)
            bundle.putInt(Constants.NEXT_ID, getNextId())
            bundle.putParcelableArrayList(Constants.MEMBER_LIST, controller.memberList)
            intent.putExtras(bundle)
            memberARL.launch(intent)
        }

    }

    private fun mockMembers(){
//        val member = Member(id = 0, name = "Lucas", totalPaid = 60.20, itemsBought = "Picanha, Fraldinha")
//        memberList.add(member)
//        val member2 = Member(id = 1,name = "Aquiles", totalPaid = 10.22, itemsBought = "Suco, Farofa")
//        memberList.add(member2)
//        val member3 = Member(id = 2,name = "Gianluca", totalPaid = 30.22, itemsBought = "Teste, Farofa")
//        memberList.add(member3)
    }

    override fun onMemberClick(position: Int) {
        val member = controller.memberList[position]
        val intent = Intent(Constants.MEMBER_DATA_ACTIVITY)
        val bundle = Bundle()
        bundle.putParcelable(Constants.MODE, Modes.UPDATE)
        bundle.putParcelable(Constants.MEMBER, member)
        intent.putExtras(bundle)
        memberARL.launch(intent)
    }

    private fun validateSplitVisibility(){
        amb.splitBtn.visibility = if(controller.memberList.size <= 1) View.GONE else View.VISIBLE
    }

    private fun insert(member: Member){
        controller.insert(member)
        membersAdapter.notifyItemInserted(controller.memberList.lastIndex)
    }

    private fun update(member: Member){
        val index = controller.update(member) ?: return
        membersAdapter.notifyItemChanged(index)
    }

    private fun remove(member: Member){
        val index = controller.remove(member) ?: return
        membersAdapter.notifyItemRemoved(index)
    }

    private fun getNextId(): Int {
        val memberList = controller.memberList
        if(memberList.isEmpty()) return 0
        return memberList.last().id+1
    }
}