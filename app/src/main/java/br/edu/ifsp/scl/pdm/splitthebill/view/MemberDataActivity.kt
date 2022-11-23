package br.edu.ifsp.scl.pdm.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.utils.Modes
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivityMemberDataBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Member
import br.edu.ifsp.scl.pdm.splitthebill.utils.Constants

class MemberDataActivity : AppCompatActivity() {

    private val amdb: ActivityMemberDataBinding by lazy {
        ActivityMemberDataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amdb.root)

        val mode = intent.getParcelableExtra<Modes>(Constants.MODE)

        if(mode == Modes.INSERT) {
            amdb.removeBtn.visibility = View.GONE
        }else {
            val originalMember = intent.getParcelableExtra<Member>(Constants.MEMBER) ?: return
            amdb.memberNameTv.setText(originalMember.name)
            amdb.memberTotalPaidTv.setText(originalMember.totalPaid.toString())
            amdb.memberItemsBoughtTv.setText(originalMember.itemsBought)
            amdb.memberNameTv.isEnabled = false
        }

        amdb.saveBtn.setOnClickListener {
            if(mode == Modes.INSERT) finishInsert()
            else finishUpdate()
        }

        amdb.removeBtn.setOnClickListener{
            finishRemove()
        }

        amdb.cancelBtn.setOnClickListener {
            finish()
        }
    }
    private fun finishInsert(){
        if(!validateBeforeFinish()) return
        val nextId = intent.getIntExtra(Constants.NEXT_ID,-1)
        val intent = Intent()

        val name = amdb.memberNameTv.text.toString()
        val totalPaid = amdb.memberTotalPaidTv.text.toString().toDouble()
        val itemsBought = amdb.memberItemsBoughtTv.text.toString()

        val member = Member(id = nextId, name = name, totalPaid= totalPaid, itemsBought = itemsBought)
        val bundle = Bundle()
        bundle.putParcelable(Constants.MEMBER,member)
        bundle.putParcelable(Constants.MODE, Modes.INSERT)
        intent.putExtras(bundle)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun finishUpdate(){
        if(!validateBeforeFinish()) return
        val originalMember = intent.getParcelableExtra<Member>(Constants.MEMBER) ?: return
        val intent = Intent()

        val name = amdb.memberNameTv.text.toString()
        val totalPaid = amdb.memberTotalPaidTv.text.toString().toDouble()
        val itemsBought = amdb.memberItemsBoughtTv.text.toString()
        val member = Member(id = originalMember.id, name = name, totalPaid= totalPaid, itemsBought = itemsBought)
        val bundle = Bundle()
        bundle.putParcelable(Constants.MEMBER,member)
        bundle.putParcelable(Constants.MODE, Modes.UPDATE)
        intent.putExtras(bundle)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun finishRemove() {
        val originalMember = intent.getParcelableExtra<Member>(Constants.MEMBER) ?: return
        val intent = Intent()
        val bundle = Bundle()
        bundle.putParcelable(Constants.MEMBER,originalMember)
        bundle.putParcelable(Constants.MODE, Modes.REMOVE)
        intent.putExtras(bundle)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun validateBeforeFinish() : Boolean{
        if(validateFields()) return true
        Toast.makeText(this,getString(R.string.fill_all_fields),Toast.LENGTH_LONG).show()
        return false
    }


    private fun validateFields() : Boolean {
        val name = amdb.memberNameTv.text.toString()
        val totalPaid = amdb.memberTotalPaidTv.text.toString()
        val itemsBought = amdb.memberItemsBoughtTv.text.toString()
        if(name.isEmpty()) return false
        if(totalPaid.isEmpty()) return false
        if(itemsBought.isEmpty()) return false
        return true
    }

}