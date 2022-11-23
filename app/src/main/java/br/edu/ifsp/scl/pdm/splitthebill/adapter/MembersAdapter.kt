package br.edu.ifsp.scl.pdm.splitthebill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.model.Member
import br.edu.ifsp.scl.pdm.splitthebill.view.OnMemberClickListener

class MembersAdapter(
    private val memberList: MutableList<Member>,
    private val onMemberClickListener: OnMemberClickListener,
): RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MembersAdapter.MemberViewHolder {
        val layoutMemberView: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_member, parent, false)
        return MemberViewHolder(layoutMemberView)
    }

    override fun onBindViewHolder(holder: MembersAdapter.MemberViewHolder, position: Int) {

        val member = memberList[position]

        holder.nameTv.text = member.name

        val totalPaidString = String.format("R$%.2f", member.totalPaid)
        holder.totalPaidTv.text = totalPaidString

        holder.itemView.setOnClickListener{
            onMemberClickListener.onMemberClick(position)
        }
    }

    override fun getItemCount(): Int {
        return memberList.size
    }


    inner class MemberViewHolder(layoutMemberView: View): RecyclerView.ViewHolder(layoutMemberView){

        val nameTv: TextView = layoutMemberView.findViewById(R.id.nameTv)
        val totalPaidTv: TextView = layoutMemberView.findViewById(R.id.totalValue)

    }
}

