package br.edu.ifsp.scl.pdm.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.pdm.splitthebill.adapter.MembersSplitAdapter
import br.edu.ifsp.scl.pdm.splitthebill.controller.MemberSplitController
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivitySplitBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Member
import br.edu.ifsp.scl.pdm.splitthebill.utils.Constants

class SplitActivity : AppCompatActivity() {
    private lateinit var controller: MemberSplitController
    private lateinit var membersAdapter: MembersSplitAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val asb: ActivitySplitBinding by lazy {
        ActivitySplitBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asb.root)
        val memberList = intent.getParcelableArrayListExtra<Member>(Constants.MEMBER_LIST)?: return

        controller = MemberSplitController(memberList)
        asb.totalValueTv.text = String.format("R$%.2f", controller.getTotalPaid())

        asb.valuePerMemberTv.text = String.format("R$%.2f", controller.getTotalPerMember())

        layoutManager = LinearLayoutManager(this)
        asb.splitRv.layoutManager = layoutManager

        membersAdapter = MembersSplitAdapter(this, controller)
        asb.splitRv.adapter = membersAdapter

        asb.doneBtn.setOnClickListener {
            val intent = Intent()
            val bundle = Bundle()
            bundle.putParcelableArrayList(Constants.MEMBER_LIST, controller.memberList)
            intent.putExtras(bundle)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}