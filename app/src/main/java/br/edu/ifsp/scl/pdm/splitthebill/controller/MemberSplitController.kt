package br.edu.ifsp.scl.pdm.splitthebill.controller

import br.edu.ifsp.scl.pdm.splitthebill.model.Member

class MemberSplitController(val memberList: ArrayList<Member>) {

    fun getTotalPaid() : Double {
        var sum = 0.0
        for (member in memberList) {
            sum+= member.totalPaid
        }
        return sum
    }

    fun getTotalPerMember() : Double {
        return getTotalPaid()/memberList.size
    }
}