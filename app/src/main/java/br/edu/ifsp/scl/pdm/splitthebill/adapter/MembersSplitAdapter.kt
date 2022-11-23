package br.edu.ifsp.scl.pdm.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.controller.MemberSplitController

class MembersSplitAdapter(
    private val context: Context,
    private val memberSplitController: MemberSplitController,
) : RecyclerView.Adapter<MembersSplitAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MembersSplitAdapter.MemberViewHolder {
        val layoutMemberView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_split_member, parent, false)
        return MemberViewHolder(layoutMemberView)
    }

    override fun onBindViewHolder(holder: MembersSplitAdapter.MemberViewHolder, position: Int) {

        val member = memberSplitController.memberList[position]

        holder.nameSplitTv.text = member.name
        val totalPerMember = memberSplitController.getTotalPerMember()
        val mustPay = member.mustPay(totalPerMember)
        val mustReceive = member.mustReceive(totalPerMember)
        var value = 0.0
        if(mustPay > 0){
            holder.totalSplitTv.text = context.getString(R.string.must_pay)
            value = mustPay
        }else if (mustReceive > 0){
            holder.totalSplitTv.text =  context.getString(R.string.must_receive)
            value = mustReceive
        } else {
            holder.totalSplitTv.text =  context.getString(R.string.nothing_to_pay_or_receive)
            holder.totalSplitValueTv.visibility = View.GONE
        }
        val totalPaidString = String.format("R$%.2f", value)
        holder.totalSplitValueTv.text = totalPaidString
        val totalPaid = String.format("R$%.2f", member.totalPaid)
        holder.splitMemberTotalPaid.text = totalPaid
    }

    override fun getItemCount(): Int {
        return memberSplitController.memberList.size
    }


    inner class MemberViewHolder(layoutMemberView: View) :
        RecyclerView.ViewHolder(layoutMemberView) {

        val nameSplitTv: TextView = layoutMemberView.findViewById(R.id.nameSplitTv)
        val totalSplitValueTv: TextView = layoutMemberView.findViewById(R.id.totalSplitValueTv)
        val totalSplitTv: TextView = layoutMemberView.findViewById(R.id.totalPaidSplitTv)
        val splitMemberTotalPaid: TextView = layoutMemberView.findViewById(R.id.splitMemberTotalPaid)

    }
}

